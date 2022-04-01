//**********************************************************
//1. ��      ��: �������� �����ᳳ�ε�� (����)
//2. ���α׷���: OffBillRegistAdminBean.java
//3. ��      ��: ��������
//4. ȯ      ��: JDK 1.5
//5. ��      ��: 1.0
//6. ��      ��: 2009.12.22
//7. ��      ��:
//**********************************************************
package com.credu.off;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;


@SuppressWarnings("unchecked")
public class OffBillRegistAdminBean {

	private ConfigSet config;
	private int row;

	public OffBillRegistAdminBean() {
	  try{
	      config = new ConfigSet();
	      row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
	  }
	  catch(Exception e) {
	      e.printStackTrace();
	  }
}

	/**
    ������ ����  ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
	public ArrayList selectList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls         = null;
        ArrayList list     = null;
        DataBox dbox = null;

        int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");
        
		String v_searchtext = box.getString("s_subjsearchkey");
        
        String  ss_year     = box.getStringDefault("s_year","ALL");         //�⵵
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //�����з�
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //�����з�
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //�����з�
        String  ss_subj     = box.getStringDefault("s_subjcode","ALL");     //����
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");      //��������
        String  ss_action   = box.getString("s_action");
        
    	String  v_startdate     = box.getString("s_startdate");
    	String  v_enddate       = box.getString("s_enddate");
    			v_startdate		= v_startdate.replace("-", "");
    			v_enddate		= v_enddate.replace("-", "");
    	
    	String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
        String  v_orderType     = box.getString("p_orderType");           		//������ ����

        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";
        String group_sql  = "";
        String order_sql  = "";

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

		    head_sql  = " SELECT a.subj, a.year, ";
		    head_sql +=	"       (SELECT subjnm ";
            head_sql += "          FROM tz_offsubj ";
            head_sql += "         WHERE subj = a.subj) subjnm, a.subjseq, a.seq, a.billreqnm, a.biyong, ";
		    head_sql += "        a.realbiyong, a.billbegindt, a.billenddt, ";
		    head_sql += "        (SELECT COUNT (userid) ";
		    head_sql += "           FROM tz_offbillrequser ";
		    head_sql += "          WHERE subj = a.subj AND YEAR = a.YEAR ";
		    head_sql += "                AND subjseq = a.subjseq AND seq = a.seq) usercnt ";
		    body_sql += "   FROM tz_offbillreq a INNER JOIN tz_offsubj c ON a.subj = c.subj ";
		    body_sql += "  WHERE (1=1) ";

			if (!ss_uclass.equals("ALL")) {
				body_sql += "                                          AND c.upperclass = "+SQLString.Format(ss_uclass);
            }
            if (!ss_mclass.equals("ALL")) {
				body_sql += "                                          AND c.middleclass = "+SQLString.Format(ss_mclass);
            }
            if (!ss_lclass.equals("ALL")) {
				body_sql += "                                          AND c.lowerclass = "+SQLString.Format(ss_lclass);
            }
			if (!ss_year.equals("ALL")) {
				body_sql += "                                          AND a.year = "+SQLString.Format(ss_year);
            }

			if (!ss_subj.equals("ALL")) {
				body_sql += "                                          AND a.subj = "+SQLString.Format(ss_subj);
            }
			if (!ss_subjseq.equals("ALL")) {
				body_sql += "                                          AND a.subjseq = "+SQLString.Format(ss_subjseq);
            }
			
    		if(!v_startdate.equals("") ){
    			body_sql += "  AND a.billbegindt >="+SQLString.Format(v_startdate);
    		}
   			if(!v_enddate.equals("") ){
   				body_sql += "  AND a.billbegindt <="+SQLString.Format(v_enddate);
   			}
   			
			if ( !v_searchtext.equals("")) {      //    �˻�� ������
				body_sql +=" and c.subjnm like " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n";                      			                      			
			}

            if(v_orderColumn.equals("")) {
				order_sql += " order by a.ldate desc, a.billbegindt desc";
            } else {
				order_sql += " order by " + v_orderColumn + v_orderType + ", a.ldate desc";
            }

			sql= head_sql+ body_sql+group_sql+ order_sql;
			
			//System.out.println("#############"+sql);
			
			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ body_sql;

	        int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);	//     ��ü row ���� ��ȯ�Ѵ�
            int total_page_count = ls.getTotalPage();       //     ��ü ������ ���� ��ȯ�Ѵ�
            ls.setPageSize(v_pagesize);             		//  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count);   //     ������������ȣ�� �����Ѵ�.

	        while (ls.next()) {
	            dbox = ls.getDataBox();

	            dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount",	new Integer(total_row_count));
	            list.add(dbox);

		    }
        }
	    catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, box, sql);
	        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
	    }
	    finally {
	        if(ls != null) { try { ls.close(); }catch (Exception e) {} }
	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	    }
		return list;
	}

	 /**
    ������ ���� ����Ʈ(Excel)
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectExcelList(RequestBox box) throws Exception {

         DBConnectionManager connMgr = null;
         ListSet ls         = null;
         ArrayList list     = null;
         DataBox dbox = null;

         int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
         int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");
         
 		 String v_searchtext = box.getString("s_subjsearchkey");
         
         String  ss_year     = box.getStringDefault("s_year","ALL");         //�⵵
         String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //�����з�
         String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //�����з�
         String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //�����з�
         String  ss_subj     = box.getStringDefault("s_subjcode","ALL");     //����
         String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");      //��������
         String  ss_action   = box.getString("s_action");
         
     	 String  v_startdate     = box.getString("s_startdate");
     	 String  v_enddate       = box.getString("s_enddate");
     			v_startdate		= v_startdate.replace("-", "");
     			v_enddate		= v_enddate.replace("-", "");
     	
     	 String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
         String  v_orderType     = box.getString("p_orderType");           		//������ ����

         String sql    	  = "";
         String count_sql  = "";
         String head_sql   = "";
 		 String body_sql   = "";
         String group_sql  = "";
         String order_sql  = "";

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

		    head_sql  = " SELECT a.subj, a.year, ";
		    head_sql +=	"       (SELECT subjnm ";
            head_sql += "          FROM tz_offsubj ";
            head_sql += "         WHERE subj = a.subj) subjnm, a.subjseq, a.seq, a.billreqnm, a.biyong, ";
		    head_sql += "        a.realbiyong, a.billbegindt, a.billenddt, ";
		    head_sql += "        (SELECT COUNT (userid) ";
		    head_sql += "           FROM tz_offbillrequser ";
		    head_sql += "          WHERE subj = a.subj AND YEAR = a.YEAR ";
		    head_sql += "                AND subjseq = a.subjseq AND seq = a.seq) usercnt ";
		    body_sql += "   FROM tz_offbillreq a INNER JOIN tz_offsubj c ON a.subj = c.subj ";
		    body_sql += "  WHERE (1=1) ";

			if (!ss_uclass.equals("ALL")) {
				body_sql += "                                          AND c.upperclass = "+SQLString.Format(ss_uclass);
            }
            if (!ss_mclass.equals("ALL")) {
				body_sql += "                                          AND c.middleclass = "+SQLString.Format(ss_mclass);
            }
            if (!ss_lclass.equals("ALL")) {
				body_sql += "                                          AND c.lowerclass = "+SQLString.Format(ss_lclass);
            }
			if (!ss_year.equals("ALL")) {
				body_sql += "                                          AND a.year = "+SQLString.Format(ss_year);
            }

			if (!ss_subj.equals("ALL")) {
				body_sql += "                                          AND a.subj = "+SQLString.Format(ss_subj);
            }
			if (!ss_subjseq.equals("ALL")) {
				body_sql += "                                          AND a.subjseq = "+SQLString.Format(ss_subjseq);
            }
			
    		if(!v_startdate.equals("") ){
    			body_sql += "  AND a.billbegindt >="+SQLString.Format(v_startdate);
    		}
   			if(!v_enddate.equals("") ){
   				body_sql += "  AND a.billbegindt <="+SQLString.Format(v_enddate);
   			}
   			
			if ( !v_searchtext.equals("")) {      //    �˻�� ������
				body_sql +=" and b.subjnm like " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n";                      			                      			
			}

            if(v_orderColumn.equals("")) {
				order_sql += " order by a.ldate desc, a.billbegindt desc";
            } else {
				order_sql += " order by " + v_orderColumn + v_orderType + ", a.ldate desc";
            }

			sql= head_sql+ body_sql+group_sql+ order_sql;

			ls = connMgr.executeQuery(sql);

	        while (ls.next()) {
	            dbox = ls.getDataBox();

	            list.add(dbox);

		    }
        }
	    catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, box, sql);
	        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
	    }
	    finally {
	        if(ls != null) { try { ls.close(); }catch (Exception e) {} }
	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	    }
		return list;
	}

  	/** ������ ���� ������������� ��ϵ� ������ ���� ��������
     @param box      receive from the form object and session
     @return ArrayList
     */
      public DataBox selectSubjseqInfo(RequestBox box) throws Exception {
         DBConnectionManager connMgr = null;
         ListSet ls         = null;
         DataBox dbox = null;

 		String sql    	 = "";
 		String v_year	 = box.getString("p_year");
 		String v_subj	 = box.getString("p_subj");
 		String v_subjseq = box.getString("p_subjseq");

         try {
             connMgr = new DBConnectionManager();

	         sql  = " SELECT subj, subjnm, year, subjseq ";
	         sql += "   FROM tz_offsubjseq ";
	         sql += "  WHERE subj    = " + SQLString.Format(v_subj);
	         sql += "    AND YEAR    = " + SQLString.Format(v_year);
	         sql += "    AND subjseq = " + SQLString.Format(v_subjseq);

        	 //System.out.println("sql = " + sql);
 			
        	 ls = connMgr.executeQuery(sql);
        	 while (ls.next()) {
 	            dbox = ls.getDataBox();
        	 }
         }
 	    catch (Exception ex) {
 	        ErrorManager.getErrorStackTrace(ex, box, sql);
 	        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
 	    }
 	    finally {
 	        if(ls != null) { try { ls.close(); }catch (Exception e) {} }
 	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
 	    }
 		return dbox;
 	}

  /**
   * ������ ��ȸ
    @param box      receive from the form object and session
    @return ArrayList
   */
    @SuppressWarnings("unchecked")
	public ArrayList selectSearchStudent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
		DataBox dbox = null;

		String sql       = "";
 		String v_year	 = box.getString("p_year");
 		String v_subj	 = box.getString("p_subj");
 		String v_subjseq = box.getString("p_subjseq");
        
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();
            
	        sql  = " SELECT a.userid, b.NAME, a.studentno, a.confirmdate, c.codenm ";
	        sql += "   FROM tz_offstudent a INNER JOIN tz_member b ON a.userid = b.userid ";
	        sql += "        INNER JOIN tz_code c ON a.stustatus = c.code AND c.gubun = '0089' ";
	        sql += "  WHERE a.subj = "+SQLString.Format(v_subj);
	        sql += "    AND a.YEAR = "+SQLString.Format(v_year);
	        sql += "    AND a.subjseq = "+SQLString.Format(v_subjseq);

			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
			    dbox = ls.getDataBox();
			
			    list.add(dbox);
			}

        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }
    
      /**
       * ���δ���� ��ȸ
        @param box      receive from the form object and session
        @return ArrayList
       */
        @SuppressWarnings("unchecked")
    	public ArrayList selectOffBillStudent(RequestBox box) throws Exception {
    		DBConnectionManager connMgr = null;
  		DataBox dbox                = null;
  		ListSet ls                  = null;
  		ArrayList list              = null;
  		String sql                  = "";

		String v_subj    = box.getString("p_subj");
		String v_year    = box.getString("p_year");
		String v_subjseq = box.getString("p_subjseq");
		String v_seq	 = box.getString("p_seq");

  		try {
  			connMgr = new DBConnectionManager();
  			list = new ArrayList();

	  		sql  = " SELECT a.userid, c.NAME, b.studentno ";
	  		sql += "   FROM tz_offbillrequser a INNER JOIN tz_offstudent b ";
	  		sql += "        ON a.userid = b.userid ";
	  		sql += "      AND a.subj = b.subj ";
	  		sql += "      AND a.YEAR = b.YEAR ";
	  		sql += "      AND a.subjseq = b.subjseq ";
	  		sql += "        INNER JOIN tz_member c ON a.userid = c.userid ";
	  		sql += "  WHERE a.subj    = "+SQLString.Format(v_subj);
	  		sql += "    AND a.YEAR    = "+SQLString.Format(v_year);
	  		sql += "    AND a.subjseq = "+SQLString.Format(v_subjseq);
	  		sql += "    AND a.seq     = "+SQLString.Format(v_seq);

  			ls = connMgr.executeQuery(sql);
  			while (ls.next()) {
  			    dbox = ls.getDataBox();
  			
  			    list.add(dbox);
  			}

  		}
  		catch (Exception ex) {
  			ErrorManager.getErrorStackTrace(ex, box, sql);
  			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
  		}
  		finally {
  			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
  			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
  		}
  		return list;

        }
      
      // ������ ���� ���
    public int insert(RequestBox box) throws Exception {
	    DBConnectionManager connMgr = null;
	    PreparedStatement pstmt = null;
	    ListSet ls = null;
	    ArrayList list = null;
	    String sql         = "";	
	    int isOk            = 0;
	
	    String v_userid = box.getSession("userid");
	    String v_name   = box.getSession("name");
	
	    String v_year        = box.getString("p_year");
	    String v_subj        = box.getString("p_subj");
	    String v_subjseq     = box.getString("p_subjseq");
	    String v_billreqnm   = box.getString("p_billreqnm");
	    double v_biyong      = box.getDouble("p_biyong");
	    double v_realbiyong  = box.getDouble("p_realbiyong");
	    String v_comments    = box.getString("p_comments");

	    String v_billbegindt = box.getString("p_billbegindt");
	    String v_billenddt   = box.getString("p_billenddt");
	    	   v_billbegindt = v_billbegindt.replace("-", "");
    		   v_billenddt	 = v_billenddt.replace("-", "");

	    String v_billstudents= box.getString("p_billstudents");

    	String[] v_billstudentarr	= null;
    	         v_billstudentarr   = v_billstudents.split(","); 

    	try {
	           connMgr = new DBConnectionManager();
	           connMgr.setAutoCommit(false);//// 

	           for (int i = 1; i < v_billstudentarr.length; i++) {
	           
		           //������ ���δ���� ���̺�
		           sql  = " INSERT INTO tz_offbillrequser (subj, year, subjseq, seq, userid, tid, biyong, billstatus, billbegindt, billenddt, luserid, ldate)";
		           sql += " SELECT ?, ?, ?, ";
		           sql += "        NVL ((SELECT MAX (seq) + 1 ";
		           sql += "                FROM tz_offbillreq ";
		           sql += "               WHERE subj = ?  ";
		           sql += "                 AND YEAR = ?  ";
		           sql += "                 AND subjseq = ?), 1), ";
		           sql += "        userid, NULL, ?, '99', ?, ?, ?, ";
		           sql += "        TO_CHAR (SYSDATE, 'yyyymmddhh24miss') ";
		           sql += "   FROM tz_member ";
		           sql += "  WHERE userid = ? ";
		           
		           pstmt = connMgr.prepareStatement(sql);
		           pstmt.setString(1,  v_subj);
		           pstmt.setString(2,  v_year);
		           pstmt.setString(3,  v_subjseq);
		           pstmt.setString(4,  v_subj);
		           pstmt.setString(5,  v_year);
		           pstmt.setString(6,  v_subjseq);
		           pstmt.setDouble(7,  v_biyong);
		           pstmt.setString(8,  v_billbegindt);
		           pstmt.setString(9,  v_billenddt);
		           pstmt.setString(10,  v_userid);
		           pstmt.setString(11,  v_billstudentarr[i]);
	
		           isOk = pstmt.executeUpdate();

	           }
	           
	           //�����ᳳ�� ���̺�
	           
			   sql  = " INSERT INTO tz_offbillreq ";
			   sql += "          (subj, YEAR, subjseq, seq, billreqnm, biyong, realbiyong, ";
			   sql += "           billbegindt, billenddt, comments, luserid, ldate) ";
			   sql += " SELECT ?, ?, ?, ";
			   sql += "        NVL ((SELECT MAX (seq) + 1 ";
			   sql += "                FROM tz_offbillreq ";
			   sql += "               WHERE subj = ? AND YEAR = ? AND subjseq = ?), 1), ";
			   sql += "        ?, ?, ?, ?, ?, ?, ?, ";
			   sql += "        TO_CHAR (SYSDATE, 'yyyymmddhh24miss') ";
			   sql += "   FROM DUAL ";

	           pstmt = connMgr.prepareStatement(sql);
	           pstmt.setString(1,  v_subj);
	           pstmt.setString(2,  v_year);
	           pstmt.setString(3,  v_subjseq);
	           pstmt.setString(4,  v_subj);
	           pstmt.setString(5,  v_year);
	           pstmt.setString(6,  v_subjseq);
	           pstmt.setString(7,  v_billreqnm);
	           pstmt.setDouble(8,  v_biyong);
	           pstmt.setDouble(9,  v_realbiyong);
	           pstmt.setString(10,  v_billbegindt);
	           pstmt.setString(11,  v_billenddt);
	           pstmt.setString(12,  v_comments);
	           pstmt.setString(13,  v_userid);

	           isOk = pstmt.executeUpdate();

	           if(isOk > 0 ) {
	                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
	            }
	
	        }
	        catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, box, sql);
	            throw new Exception("sql ->"+ sql + "\r\n" + ex.getMessage());
	        }
	        finally {
	            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
	            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
	            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
	            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	        }
	    return isOk;
	}

  	/** ������ ���ε��  �������� ���� ��������
    @param box      receive from the form object and session
    @return ArrayList
    */
     public DataBox selectOffBillRegistInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls         = null;
        DataBox dbox = null;

		String sql       = "";
		String v_subj    = box.getString("p_subj");
		String v_year    = box.getString("p_year");
		String v_subjseq = box.getString("p_subjseq");
		String v_seq	 = box.getString("p_seq");

        try {
            connMgr = new DBConnectionManager();

		    sql += " SELECT a.subj, b.subjnm, a.YEAR, a.subjseq, a.seq, a.billreqnm, a.biyong, ";
		    sql += "        a.realbiyong, a.billbegindt, a.billenddt, comments ";
		    sql += "   FROM tz_offbillreq a INNER JOIN tz_offsubj b ON a.subj = b.subj ";
		    sql += "  WHERE a.subj    = "+ SQLString.Format(v_subj);
		    sql += "    AND a.year    = "+ SQLString.Format(v_year);
		    sql += "    AND a.subjseq = "+ SQLString.Format(v_subjseq);
		    sql += "    AND a.seq     = "+ SQLString.Format(v_seq);
		    			
		    ls = connMgr.executeQuery(sql);
       	 	while (ls.next()) {
       	 		dbox = ls.getDataBox();
       	 		
       	 	}
        }
	    catch (Exception ex) {
	        ErrorManager.getErrorStackTrace(ex, box, sql);
	        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
	    }
	    finally {
	        if(ls != null) { try { ls.close(); }catch (Exception e) {} }
	        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	    }
		return dbox;
	}

     //������ ���� ��� ���� ����
     public int update(RequestBox box) throws Exception {
 	    DBConnectionManager connMgr = null;
 	    PreparedStatement pstmt = null;
 	    ListSet ls = null;
 	    ArrayList list = null;
 	    String sql         = "";	
 	    int isOk            = 0;
 	
	    String v_userid = box.getSession("userid");
	    String v_name   = box.getSession("name");
	
	    String v_year        = box.getString("p_year");
	    String v_subj        = box.getString("p_subj");
	    String v_subjseq     = box.getString("p_subjseq");
	    String v_seq         = box.getString("p_seq");
	    String v_billreqnm   = box.getString("p_billreqnm");
	    double v_biyong      = box.getDouble("p_biyong");
	    double v_realbiyong  = box.getDouble("p_realbiyong");
	    String v_comments    = box.getString("p_comments");

	    String v_billbegindt = box.getString("p_billbegindt");
	    String v_billenddt   = box.getString("p_billenddt");
	    	   v_billbegindt = v_billbegindt.replace("-", "");
    		   v_billenddt	 = v_billenddt.replace("-", "");

	    String v_billstudents= box.getString("p_billstudents");

    	String[] v_billstudentarr	= null;
    	         v_billstudentarr   = v_billstudents.split(","); 
 	    
 		try {
 	           connMgr = new DBConnectionManager();
 	           connMgr.setAutoCommit(false);//// 

 	           //������ ���δ���� ���̺� ����
 	           sql  = " DELETE tz_offbillrequser ";
 	           sql += "  WHERE subj    = ? ";
 	           sql += "    AND year    = ? ";
 	           sql += "    AND subjseq = ? ";
 	           sql += "    AND seq     = ? ";
 	           
	           pstmt = connMgr.prepareStatement(sql);
	           pstmt.setString(1,  v_subj);
	           pstmt.setString(2,  v_year);
	           pstmt.setString(3,  v_subjseq);
	           pstmt.setString(4,  v_seq);
	           
	           isOk = pstmt.executeUpdate();

	           //������ ���δ���� ���̺� insert
	           for (int i = 1; i < v_billstudentarr.length; i++) {

		           sql  = " INSERT INTO tz_offbillrequser (subj, year, subjseq, seq, userid, tid, biyong, billstatus, billbegindt, billenddt, luserid, ldate) ";
		           sql += " SELECT ?, ?, ?, ";
		           sql += "        ?, ";
		           sql += "        userid, NULL, ?, '99', ?, ?, ?, ";
		           sql += "        TO_CHAR (SYSDATE, 'yyyymmddhh24miss') ";
		           sql += "   FROM tz_member ";
		           sql += "  WHERE userid = ? ";
	
		           pstmt = connMgr.prepareStatement(sql);
		           pstmt.setString(1,  v_subj);
		           pstmt.setString(2,  v_year);
		           pstmt.setString(3,  v_subjseq);
		           pstmt.setString(4,  v_seq);
		           pstmt.setDouble(5,  v_biyong);
		           pstmt.setString(6,  v_billbegindt);
		           pstmt.setString(7,  v_billenddt);
		           pstmt.setString(8,  v_userid);
		           pstmt.setString(9,  v_billstudentarr[i]);
	
		           isOk = pstmt.executeUpdate();

	           }

 	           //������ ���� ��� ���̺�
		 	   sql  = " UPDATE tz_offbillreq ";
		 	   sql += "    SET ";
		 	   sql += "        billreqnm   = ?, ";
		 	   sql += "        biyong      = ?, ";
		 	   sql += "        realbiyong  = ?, ";
		 	   sql += "        billbegindt = ?, ";
		 	   sql += "        billenddt   = ?, ";
		 	   sql += "        comments    = ?, ";
		 	   sql += "        ldate       = TO_CHAR (SYSDATE, 'yyyymmddhh24miss') ";
		 	   sql += "  WHERE subj    = ?  ";
		 	   sql += "    AND year    = ? ";
		 	   sql += "    AND subjseq = ? ";
		 	   sql += "    AND seq     = ? ";

 	           pstmt = connMgr.prepareStatement(sql);
 	           pstmt.setString(1,  v_billreqnm);
 	           pstmt.setDouble(2,  v_biyong);
 	           pstmt.setDouble(3,  v_realbiyong);
 	           pstmt.setString(4,  v_billbegindt);
 	           pstmt.setString(5,  v_billenddt);
 	           pstmt.setString(6,  v_comments);
 	           pstmt.setString(7,  v_subj);
 	           pstmt.setString(8,  v_year);
 	           pstmt.setString(9,  v_subjseq);
 	           pstmt.setString(10, v_seq);
 	           
 	           isOk = pstmt.executeUpdate();

 	           if(isOk > 0 ) {
 	                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
 	            }
 	
 	        }
 	        catch (Exception ex) {
 	            ErrorManager.getErrorStackTrace(ex, box, sql);
 	            throw new Exception("sql ->"+ sql + "\r\n" + ex.getMessage());
 	        }
 	        finally {
 	            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
 	            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
 	            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
 	            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
 	        }
 	    return isOk;
 	}

}