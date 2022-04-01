//**********************************************************
//1. 제      목: 오프라인 수강료납부등록 (어드민)
//2. 프로그램명: OffBillRegistAdminBean.java
//3. 개      요: 행정서비스
//4. 환      경: JDK 1.5
//5. 버      젼: 1.0
//6. 작      성: 2009.12.22
//7. 수      정:
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
	      row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
	  }
	  catch(Exception e) {
	      e.printStackTrace();
	  }
}

	/**
    수강료 납부  리스트
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
        
        String  ss_year     = box.getStringDefault("s_year","ALL");         //년도
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //과정분류
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //과정분류
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //과정분류
        String  ss_subj     = box.getStringDefault("s_subjcode","ALL");     //과정
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");      //과정차수
        String  ss_action   = box.getString("s_action");
        
    	String  v_startdate     = box.getString("s_startdate");
    	String  v_enddate       = box.getString("s_enddate");
    			v_startdate		= v_startdate.replace("-", "");
    			v_enddate		= v_enddate.replace("-", "");
    	
    	String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
        String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서

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
   			
			if ( !v_searchtext.equals("")) {      //    검색어가 있으면
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

	        int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);	//     전체 row 수를 반환한다
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize);             		//  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.

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
    수강료 납부 리스트(Excel)
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
         
         String  ss_year     = box.getStringDefault("s_year","ALL");         //년도
         String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //과정분류
         String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //과정분류
         String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //과정분류
         String  ss_subj     = box.getStringDefault("s_subjcode","ALL");     //과정
         String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");      //과정차수
         String  ss_action   = box.getString("s_action");
         
     	 String  v_startdate     = box.getString("s_startdate");
     	 String  v_enddate       = box.getString("s_enddate");
     			v_startdate		= v_startdate.replace("-", "");
     			v_enddate		= v_enddate.replace("-", "");
     	
     	 String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
         String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서

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
   			
			if ( !v_searchtext.equals("")) {      //    검색어가 있으면
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

  	/** 수강료 납부 등록페이지에서 등록될 과정의 정보 가져오기
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
   * 수강생 조회
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
       * 납부대상자 조회
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
      
      // 수강료 납부 등록
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
	           
		           //수강료 납부대상자 테이블
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
	           
	           //수강료납부 테이블
	           
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

  	/** 수강료 납부등록  세부정보 정보 가져오기
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

     //수강료 납부 등록 내용 수정
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

 	           //수강료 납부대상자 테이블 삭제
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

	           //수강료 납부대상자 테이블 insert
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

 	           //수강료 납부 등록 테이블
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