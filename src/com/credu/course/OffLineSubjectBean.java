//**********************************************************
//  1. ��      ��: ����OPERATION BEAN
//  2. ���α׷���: SubjectBean.java
//  3. ��      ��: ����OPERATION BEAN
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ���� 2004. 11. 14
//  7. ��      ��:
//**********************************************************
package com.credu.course;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.dunet.common.util.StringUtil;
import com.namo.SmeNamoMime;

public class OffLineSubjectBean {

    public final static String LANGUAGE_GUBUN = "0017";
    public final static String ONOFF_GUBUN = "0004";

    public OffLineSubjectBean() {}

    /**
    ��������Ʈ ��ȸ
    @param box          receive from the form object and session
    @return ArrayList   ��������Ʈ
    */
    public ArrayList SelectSubjectList(RequestBox box) throws Exception {
		PreparedStatement pstmt = null;  
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
	String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
		
	int row = 10;
	int v_pageno = box.getInt("p_pageno");

        String  ss_grcode       = box.getStringDefault("s_grcode","ALL");          //�����ְ�
        String  ss_subjgubun    = box.getStringDefault("s_subjgubun","ALL");          //�����ְ�
        String  ss_upperclass   = box.getStringDefault("s_upperclass","ALL");    //�����з�
        String  ss_middleclass  = box.getStringDefault("s_middleclass","ALL");   //�����з�
        String  ss_lowerclass   = box.getStringDefault("s_lowerclass","ALL");    //�����з�
        String  ss_subjcourse   = box.getStringDefault("s_subjcourse","ALL");    //����&�ڽ�

        String  v_orderColumn   = box.getString("p_orderColumn");               //������ �÷���
        String  v_orderType     = box.getString("p_orderType");                 //������ ����

        try {
			
		connMgr = new DBConnectionManager();

		list = new ArrayList();
			
		head_sql += "Select seq, subjgubun, subj, subjseq, propStart, propEnd, subjNm, tUserid, tName, ";
		head_sql += " dday, startTime, endTime, place, b.codenm as gubunNm";
		body_sql +=" From TZ_OFFLINESUBJ a, tz_code b";
		body_sql += " Where a.subjgubun = code and b.gubun = '0061' ";
			
			
			
		//���� ����
			
		
            if (!ss_subjgubun.equals("----")) {
				body_sql+= "         and a.subjgubun = '" + ss_subjgubun + "' ";
            }
            
            //�����׷�
            
            
            //if (!ss_grcode.equals("----")) {
//				body_sql+= "         and a.grcode = '" + ss_grcode + "' ";
  //          }

            
            /*
            
            if (!ss_subjcourse.equals("ALL")) {
				body_sql+= "   and a.subj = " + SQLString.Format(ss_subjcourse);
            } else {
                if (!ss_upperclass.equals("ALL")) {
                    if (!ss_upperclass.equals("ALL")) {
						body_sql += " and a.upperclass = "+SQLString.Format(ss_upperclass);
                    }
                    if (!ss_middleclass.equals("ALL")) {
						body_sql += " and a.middleclass = "+SQLString.Format(ss_middleclass);
                    }
                    if (!ss_lowerclass.equals("ALL")) {
						body_sql += " and a.lowerclass = "+SQLString.Format(ss_lowerclass);
                    }
                }
            }
            */
            
            
           if(v_orderColumn.equals("")) {
                order_sql += " order by a.seq DESC ";
            } else {
				order_sql += " order by " + v_orderColumn + v_orderType;
            }
			sql= head_sql+ body_sql+group_sql+ order_sql;
System.out.println("sql = " + sql);
			ls = connMgr.executeQuery(sql);	   	
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);
			
			ls.setPageSize(row);                       //  �������� row ������ �����Ѵ�
			ls.setCurrentPage(v_pageno, total_row_count);	// ������������ȣ�� �����Ѵ�.
			int totalpagecount = ls.getTotalPage();    		// ��ü ������ ���� ��ȯ�Ѵ�
			
			while (ls.next()) {
				dbox = ls.getDataBox();
				
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(totalpagecount));
				dbox.put("d_rowcount", new Integer(row));
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
	   * ���õ�  �Խù� �󼼳��� select
	   * @param box          receive from the form object and session
	   * @return ArrayList   ��ȸ�� ������
	   * @throws Exception
	   */
	   public DataBox selectBoard(RequestBox box) throws Exception {
	        DBConnectionManager connMgr = null;
	        ListSet ls = null;
	        String sql = "";
	        DataBox dbox = null;
	        String v_upcnt = "Y";
			String v_grcode = box.getString("s_grcode");
			
	        int v_seq       = box.getInt("p_seq");

	        Vector realfileVector = new Vector();
	        Vector savefileVector = new Vector();
	        Vector fileseqVector  = new Vector();
	        try {
	            connMgr = new DBConnectionManager();

	            sql  = " Select ";
				sql += " 	seq, subjgubun, subj, subjseq, propStart, propEnd, subjNm, tUserid, tName, dday, startTime, endTime, place,";
				sql += " 	b.codenm as gubunNm, useyn, target, limitmember, content, grcode ";
				sql += " From TZ_OFFLINESUBJ a, tz_code b ";
				sql += " Where ";
				sql += "	a.subjgubun = code and b.gubun = '0061' ";
				sql += "	and seq = " + v_seq ;
System.out.println("sql = " + sql);
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
	            if(ls != null) {try {ls.close();} catch(Exception e){}}
	            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	        }
	        return dbox;
	    }

    /**
    ���ο� Off-Line �����ڵ� ��� 
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
     public int InsertSubject(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1   = null;

        PreparedStatement pstmt = null;
        Statement stmt1 = null;
        String sql = "";
		String v_gubunnm = "";
		
        int v_seq     = 0;	
        int isOk = 0;

        String v_grcode   = box.getString("s_grcode");  //��������Ʈ������ SELECTBOX �����ְ� �ڵ�
		if(v_grcode.equals("N000001"))
		{
			v_gubunnm = "kocca";
		}
		else
		{
			v_gubunnm = "game";
		}

        String v_subjgubun	= box.getString("s_subjgubun");
        String v_subj     	= box.getString("p_subj");
        String v_subjseq    = box.getString("p_subjseq");
        String v_propstart  = box.getString("p_propstart");
        String v_propend    = box.getString("p_propend");
        String v_subjnm     = box.getString("p_subjnm");
        String v_name	    = box.getString("p_tname");
        String v_dday     	= box.getString("p_dday");
        String v_starttime  = box.getString("p_starttime");
        String v_endtime    = box.getString("p_endtime");
        String v_place     	= box.getString("p_place");
        String v_limitmember= box.getString("p_limitmember");
        String v_target     = box.getString("p_target");
        String v_content    = StringUtil.removeTag(box.getString("p_content"));
        String v_useyn     	= box.getString("p_useyn");
        String v_luserid  	= box.getSession("userid");
		 
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
			
			stmt1 = connMgr.createStatement();
			
            //----------------------   �Խ��� ��ȣ �����´� ----------------------------
            sql = "select NVL(max(seq), 0) from tz_OffLineSubj ";//where grcode = '" + v_grcode + "'";
            rs1 = stmt1.executeQuery(sql);
            if (rs1.next()) {
                v_seq = rs1.getInt(1) + 1;
            }
            rs1.close();
/*
            //�����ְ��� �����ϰ� �߰��ϴ� ���� �ش� �����ְ��� ���� TZ_PREVIEW�� INSERT�Ѵ�.
            if (!v_grcode.equals("ALL")) {
                box.put("p_grcode",v_grcode);
                box.put("p_subj",v_subj);
                isOk2 = InsertPreview(box);
            }

            //�����׷� �����ڴ� �ش� �����׷����� INSERT�ϰ� �Ѵ�.
            if (StringManager.substring(box.getSession("gadmin"),0,1).equals("H")) {
                int isOk3 = this.SaveGrSubj(connMgr, v_grcode, v_subj, v_luserid);
            }
            //������ �ϰ�� N000001 (���信��) �����׷����� �ڵ� INSERT �Ѵ�
            else if (StringManager.substring(box.getSession("gadmin"),0,1).equals("A")) {
                int isOk4 = this.SaveGrSubj(connMgr, "N000001", v_subj, v_luserid);
            }
			*/
			
			/*********************************************************************************************/
            // ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
            ConfigSet conf = new ConfigSet();
            SmeNamoMime namo = new SmeNamoMime(v_content); // ��ü���� 
            boolean result = namo.parse(); // ���� �Ľ� ���� 
            if ( !result ) { // �Ľ� ���н� 
                System.out.println( namo.getDebugMsg() ); // ����� �޽��� ��� 
                return 0;
            }
            if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ� 
                String v_server = conf.getProperty(v_gubunnm +".url.value");
                String fPath  = conf.getProperty("dir.namo");   // ���� ���� ��� ����
                String refUrl = conf.getProperty("url.namo");; // ������ ����� ������ �����ϱ� ���� ���
                String prefix = "superiority_" + v_dday + "_" + v_subjnm;         // ���ϸ� ���ξ�
                result = namo.saveFile(fPath, v_server+refUrl, prefix); // ���� ���� ���� 
            }
            if ( !result ) { // �������� ���н� 
                System.out.println( namo.getDebugMsg() ); // ����� �޽��� ��� 
                return 0;
            }
            v_content = namo.getContent(); // ���� ����Ʈ ���
            /*********************************************************************************************/

			sql =  " insert into tz_OffLineSubj (seq, grcode, subjgubun, subj, subjseq, propstart, propend, subjnm, ";
			sql += " tname, dday, starttime, endtime, place, limitmember, target, content, luserid, ldate, useyn) ";
			sql += " values (?, ?, ?, ?, ?, TO_CHAR(to_date(?, 'YYYY-MM-DDHH24MISS'),'YYYYMMDDHH24MISS'), TO_CHAR(to_date(?, 'YYYY-MM-DDHH24MISS'),'YYYYMMDDHH24MISS'), ?, ?,";
			sql += " TO_CHAR(to_date(?, 'YYYY-MM-DDHH24MISS'),'YYYYMMDDHH24MISS'), ?, ?, ? , ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?)";

	        pstmt = connMgr.prepareStatement(sql);

	        pstmt.setInt(1, v_seq);
	        pstmt.setString(2, v_grcode);
	        pstmt.setString(3, v_subjgubun);
	        pstmt.setString(4, v_subj);
	        pstmt.setString(5, v_subjseq);		

	        System.out.println(v_propstart + " ------ " + v_propend + " ------ " + v_dday);
	        pstmt.setString(6, v_propstart);
	        pstmt.setString(7, v_propend);
	        pstmt.setString(8, v_subjnm);
	        pstmt.setString(9, v_name);
	        pstmt.setString(10, v_dday);
	        pstmt.setString(11, v_starttime);
	        pstmt.setString(12, v_endtime);
	        pstmt.setString(13, v_place);
	        pstmt.setString(14, v_limitmember);
	        pstmt.setString(15, v_target);
	        pstmt.setString(16, v_content);
	        pstmt.setString(17, v_luserid);
	        pstmt.setString(18, v_useyn);

	        isOk = pstmt.executeUpdate();	
			connMgr.commit();
        }
        catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
	 
	 /**
	    ���ο� Off-Line �����ڵ� ��� 
	    @param box      receive from the form object and session
	    @return isOk    1:insert success,0:insert fail
	    */
	     public int updateBoard(RequestBox box) throws Exception {
	        DBConnectionManager connMgr = null;

	        ResultSet rs1   = null;

	        PreparedStatement pstmt = null;
	        Statement stmt1 = null;
	        String sql = "";
			String v_gubunnm = "";
			
	        int isOk = 0;

	        String v_grcode   = box.getString("s_grcode");  //��������Ʈ������ SELECTBOX �����ְ� �ڵ�
			if(v_grcode.equals("N000001"))
			{
				v_gubunnm = "kocca";
			}
			else
			{
				v_gubunnm = "game";
			}

	        String v_subjgubun	= box.getString("s_subjgubun");
	        String v_subj     	= box.getString("p_subj");
	        String v_subjseq    = box.getString("p_subjseq");
	        String v_propstart  = box.getString("p_propstart");
	        String v_propend    = box.getString("p_propend");
	        String v_subjnm     = box.getString("p_subjnm");
	        String v_name	    = box.getString("p_tname");
	        String v_dday     	= box.getString("p_dday");
	        String v_starttime  = box.getString("p_starttime");
	        String v_endtime    = box.getString("p_endtime");
	        String v_place     	= box.getString("p_place");
	        String v_limitmember= box.getString("p_limitmember");
	        String v_target     = box.getString("p_target");
	        String v_content    = StringUtil.removeTag(box.getString("p_content"));
	        String v_useyn     	= box.getString("p_useyn");
	        String v_luserid  	= box.getSession("userid");
	        int v_seq    		= box.getInt("p_seq");
			 
	        try {
	            connMgr = new DBConnectionManager();
	            connMgr.setAutoCommit(false);
				
				stmt1 = connMgr.createStatement();
				
				
				/*********************************************************************************************/
	            // ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
	            ConfigSet conf = new ConfigSet();
	            SmeNamoMime namo = new SmeNamoMime(v_content); // ��ü���� 
	            boolean result = namo.parse(); // ���� �Ľ� ���� 
	            if ( !result ) { // �Ľ� ���н� 
	                System.out.println( namo.getDebugMsg() ); // ����� �޽��� ��� 
	                return 0;
	            }
	            if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ� 
	                String v_server = conf.getProperty(v_gubunnm +".url.value");
	                String fPath  = conf.getProperty("dir.namo");   // ���� ���� ��� ����
	                String refUrl = conf.getProperty("url.namo");; // ������ ����� ������ �����ϱ� ���� ���
	                String prefix = "superiority_" + v_dday + "_" + v_subjnm;         // ���ϸ� ���ξ�
	                result = namo.saveFile(fPath, v_server+refUrl, prefix); // ���� ���� ���� 
	            }
	            if ( !result ) { // �������� ���н� 
	                System.out.println( namo.getDebugMsg() ); // ����� �޽��� ��� 
	                return 0;
	            }
	            v_content = namo.getContent(); // ���� ����Ʈ ���
	            /*********************************************************************************************/

				sql =  " Update tz_OffLineSubj Set subjgubun = ?, subj = ?, subjseq = ?, ";
				sql += " 	propstart = to_date(?, 'YYYYMMDDHH24MISS'), propend = to_date(?, 'YYYYMMDDHH24MISS'), subjnm = ?, ";
				sql += " 	tname = ?, dday = to_date(?, 'YYYYMMDDHH24MISS'), starttime = ?, endtime = ? , place = ?, limitmember = ?, target = ?, content = ?, luserid = ?, ";
				sql += " 	ldate =to_char(sysdate, 'YYYYMMDDHH24MISS'), useyn = ? ";
				sql += " Where seq = ? and grcode = ? ";

		        pstmt = connMgr.prepareStatement(sql);

		        pstmt.setString(1, v_subjgubun);
		        pstmt.setString(2, v_subj);
		        pstmt.setString(3, v_subjseq);		
		        pstmt.setString(4, v_propstart);
		        pstmt.setString(5, v_propend);
		        pstmt.setString(6, v_subjnm);
		        pstmt.setString(7, v_name);
		        pstmt.setString(8, v_dday);
		        pstmt.setString(9, v_starttime);
		        pstmt.setString(10, v_endtime);
		        pstmt.setString(11, v_place);
		        pstmt.setString(12, v_limitmember);
		        pstmt.setString(13, v_target);
		        pstmt.setString(14, v_content);
		        pstmt.setString(15, v_luserid);
		        pstmt.setString(16, v_useyn);

		        pstmt.setInt(17, v_seq);
		        pstmt.setString(18, v_grcode);

		        isOk = pstmt.executeUpdate();
				connMgr.commit();
	        }
	        catch(Exception ex) {
	            connMgr.rollback();
	            ErrorManager.getErrorStackTrace(ex, box, sql);
	            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
	        }
	        finally {
	            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
	            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	        }
	        return isOk;
	    }
		 
		 /**
	   * �������� �з�  SELECT
	   * @param box          receive from the form object and session
	   * @return ArrayList   �������� �з�
	   */
		public static ArrayList getOffGubun(RequestBox box) throws Exception {
	    
	        DBConnectionManager connMgr = null;
	        PreparedStatement pstmt = null;        
	        ListSet ls = null;
	        ArrayList list = null;
	        String sql = "";

	        String result = "OffLine�з� ";
	        DataBox dbox = null;
				        
	        try {            
	            connMgr = new DBConnectionManager();            
	            
	            list = new ArrayList();

	            sql = "Select code, codenm From TZ_CODEGUBUN  cg ";
				sql += "	join tz_code c on c.gubun = cg.gubun ";
				sql += " Where cg.gubun ='0061'";          
	            
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
}
