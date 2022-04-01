
//**********************************************************
//  1. ��      ��: Faq ����
//  2. ���α׷��� : FaqAdminBean.java
//  3. ��      ��: Faq ����
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 13
//  7. ��      ��: �̳��� 05.11.17 _ totalcount �ϱ����� ���� ����
//**********************************************************

package com.credu.homepage;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.MailSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;

/**
 * Faq ����(ADMIN)
 *
 * @date   : 2003. 7
 * @author : s.j Jung
 */
public class HomePageAdminBean {
	private ConfigSet config;
    private int row;
	
	public HomePageAdminBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }



    /**
    Faqȭ�� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   Faq ����Ʈ
    */
    public ArrayList<DataBox> selectListAdminhyundai(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
//		 ������ : 05.11.17 ������ : �̳��� _ totalcount �ϱ����� ��������
		String sql = "";
		String head_sql = "";
		String body_sql = "";
		String group_sql = "";
		String order_sql = "";
		String count_sql = "";
		
        DataBox dbox = null;

        //String v_faqcategory = box.getString("p_faqcategory");
        String v_searchtext = box.getString("p_searchtext");
        // String v_search = box.getString("p_select");
		int v_pageno        = box.getInt("p_pageno");
		String v_gcode = box.getString("s_grcode");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
			/*
			SELECT a.userid, a.name, a.jikwinm,
				   a.jikmunm, a.handphone, a.comptel,
				   b.gadmin, a.email, a.orga_ename
			FROM tz_member a, tz_manager b, tz_grcodeman c
			WHERE a.userid = b.userid
			  AND b.gadmin = c.gadmin
			  AND ( b.gadmin = 'H1'
			   OR b.gadmin = 'A1'
			   OR b.gadmin = 'A2' )
			  AND c.grcode = 'N000001'			
			*/
			head_sql  = " SELECT DISTINCT a.userid, a.name, get_jikwinm(a.jikwi, a.comp) as jikwinm, a.handphone, a.comptel,   ";
			head_sql += " c.gadmin, a.email, a.orga_ename "  ;
			body_sql += "FROM tz_member a, tz_manager b, tz_grcodeman c   ";
			body_sql += "WHERE a.userid = b.userid  ";
			// ������ : 05.11.17 ������ : �̳��� _substr ����
//			body_sql += "AND a.userid = c.userid and substr(b.comp,1,4) = '0101' ";
			body_sql += "AND a.userid = c.userid and substring(b.comp,1,4) = '0101' ";
			body_sql += " and a.jikwi >= '28'";
//			body_sql += "AND ( (substr(c.gadmin,1,2) = 'H1') OR (substr(c.gadmin,1,2) = 'A1') OR (substr(c.gadmin,1,2) = 'A2' ) )" ;
			body_sql += "AND ( (SUBSTR(c.gadmin,1,2) = 'H1') OR (SUBSTR(c.gadmin,1,2) = 'A1') OR (SUBSTR(c.gadmin,1,2) = 'A2' ) )" ;

			if (!v_gcode.equals("") && !v_gcode.equals("----")) {
							
				body_sql += "  and c.grcode   = " + SQLString.Format(v_gcode);				
            }
            if ( !v_searchtext.equals("")) {                            //    �˻�� ������
//               
                                      //    �̸����� �˻��Ҷ�
				body_sql += " and a.name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                
            }
            order_sql += " order by jikwinm desc";

			sql= head_sql+ body_sql+ group_sql+ order_sql;
			count_sql= "select count(*) "+ body_sql;
            ls = connMgr.executeQuery(sql);
			
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);    //     ��ü row ���� ��ȯ�Ѵ�
			int total_page_count = ls.getTotalPage();       //     ��ü ������ ���� ��ȯ�Ѵ�
			
			ls.setPageSize(row);             				//  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count);   //     ������������ȣ�� �����Ѵ�.

           while (ls.next()) {
                dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
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

	public ArrayList<DataBox> selectListAdminkia(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
		// ������ : 05.11.17 ������ : �̳��� _ totalcount �ϱ����� ��������
		String sql = "";
		String head_sql = "";
		String body_sql = "";
		String group_sql = "";
		String order_sql = "";
		String count_sql = "";
		
		DataBox dbox = null;
		
        //String v_faqcategory = box.getString("p_faqcategory");
        String v_searchtext = box.getString("p_searchtext");
        // String v_search = box.getString("p_select");
		int v_pageno        = box.getInt("p_pageno");
		String v_gcode = box.getString("s_grcode");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
			/*
			SELECT a.userid, a.name, a.jikwinm,
				   a.jikmunm, a.handphone, a.comptel,
				   b.gadmin, a.email, a.orga_ename
			FROM tz_member a, tz_manager b, tz_grcodeman c
			WHERE a.userid = b.userid
			  AND b.gadmin = c.gadmin
			  AND ( b.gadmin = 'H1'
			   OR b.gadmin = 'A1'
			   OR b.gadmin = 'A2' )
			  AND c.grcode = 'N000001'			
			*/
            			
			head_sql  = " SELECT DISTINCT a.userid, a.name, get_jikwinm(a.jikwi, a.comp) as jikwinm, a.handphone, a.comptel,   ";
			head_sql += "   c.gadmin, a.email, a.orga_ename "  ;
			body_sql += " FROM tz_member a, tz_manager b, tz_grcodeman c   ";
			body_sql += " WHERE a.userid = b.userid  ";
			// ������ : 05.11.17 ������ : �̳��� _ substr ����
//			sql += "AND a.userid = c.userid and substr(b.comp,1,4) = '0102' ";
			body_sql += "AND a.userid = c.userid and substring(b.comp,1,4) = '0102' ";
			body_sql += " and a.jikwi >= '28'";
//			sql += "AND ( (substr(c.gadmin,1,2) = 'H1') OR (substr(c.gadmin,1,2) = 'A1') OR (substr(c.gadmin,1,2) = 'A2' ) )" ;
			body_sql += "AND ( (SUBSTR(c.gadmin,1,2) = 'H1') OR (SUBSTR(c.gadmin,1,2) = 'A1') OR (SUBSTR(c.gadmin,1,2) = 'A2' ) )" ;

			if (!v_gcode.equals("") && !v_gcode.equals("----")) {						
				body_sql += "  and c.grcode   = " + SQLString.Format(v_gcode);				
            }
            if ( !v_searchtext.equals("")) {                            //    �˻�� ������
//               
                                     //    �̸����� �˻��Ҷ�
				body_sql += " and a.name like " + StringManager.makeSQL("%" + v_searchtext + "%");               
            }
            order_sql += " order by jikwinm desc";
			
			sql= head_sql+ body_sql+ group_sql+ order_sql;
			count_sql= "select count(*) "+ body_sql;
            ls = connMgr.executeQuery(sql);
			
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);    //     ��ü row ���� ��ȯ�Ѵ�
			int total_page_count = ls.getTotalPage();       //     ��ü ������ ���� ��ȯ�Ѵ�
			
			ls.setPageSize(row);             				//  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count);   //     ������������ȣ�� �����Ѵ�.

           while (ls.next()) {
                dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
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



/*
���Ϻ�����
*/
	public boolean selectPds(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		String sql1 = "";
		String sql2 = "";
        // DataBox dbox = null;
		boolean isMailed = false;
		PreparedStatement pstmt1 = null; 
		int isOk1 = 0;
        
		String v_toEmail = box.getString("p_toemail");
		String v_toCono = box.getString("p_cono");       
		String v_mailTitle = StringUtil.removeTag(box.getString("p_title"));		
		String v_mailContent1 = StringUtil.removeTag(box.getString("p_content"));
		String v_mailContent2 = StringManager.replace(v_mailContent1,"<br>","\n");
		String v_mailContent = "Ȩ������ [��ڿ���]�� ���� �����Դϴ� \n\r" + v_mailContent2;
		
		String s_userid = box.getSession("userid");
		String v_gubun = "05";
		
////////////////////  �������� �߼� //////////////////////////////////////////////////////////////////////////////////////////////////
		//String v_sendhtml = "freeMailForm.html";
		//FormMail fmail = new FormMail(v_sendhtml);
        MailSet mset = new MailSet(box);        //      ���� ���� �� �߼�
        //mset.setSender(fmail);     //  ���Ϻ����� ��� ����
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        
	try {
		 connMgr = new DBConnectionManager();
		//----------------------   �Խ��� ��ȣ �����´� ----------------------------
		sql2 = "select NVL(max(seq), 0) from tz_contact";
		ls = connMgr.executeQuery(sql2);
		ls.next();
		int v_seq = ls.getInt(1) + 1;
		ls.close();
		//------------------------------------------------------------------------------------

		sql1 =  "insert into TZ_CONTACT(seq, addate, queid, quetitle, quecontent, gubun)                                         ";
		sql1 += "            values (?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?)  ";

		pstmt1 = connMgr.prepareStatement(sql1);
		pstmt1.setInt(1,v_seq);
		pstmt1.setString(2, s_userid);
		pstmt1.setString(3, v_mailTitle);
		pstmt1.setString(4, v_mailContent);
		pstmt1.setString(5, v_gubun);
		
		//pstmt1.setInt(6, 0);
		isOk1 = pstmt1.executeUpdate();  

	   if (isOk1 == 1){
	
		isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, "1", ""); 						
		}
		ls.close();  
	   }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isMailed;
    }   
  

    /**
    Faqȭ�� �󼼺���
    @param box          receive from the form object and session
    @return FaqData     ��ȸ�� ������
   */
    public ArrayList<DataBox> selectMail(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_touserid = box.getString("p_touserid");
		String v_fromuserid = box.getString("p_formuserid");
        

        try {
			list = new ArrayList<DataBox>();
            connMgr = new DBConnectionManager();

            sql  = " select name, email, orga_ename, get_jikwinm(jikwi, comp) as jikwinm,  cono  ";
            sql += "   from TZ_member                                         ";
            sql += "  where userid  = " + SQLString.Format(v_touserid);
            sql += "   or userid  = " + SQLString.Format(v_fromuserid);

            ls = connMgr.executeQuery(sql);

             while(ls.next()) {
				dbox = ls.getDataBox();
				list.add(dbox);
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
        return list;
    }


    
}