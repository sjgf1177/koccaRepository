//1. ��      ��: GatePage��ڿ��԰��� ��
//2. ���α׷���: GatePageContactAdminBean.java
//3. ��      ��: GatePage��ڿ��԰��� ��
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: ������ 2004.
//7. ��      ��: �̳��� 05.11.17 _ totalcount �ϱ����� ���� ����
//**********************************************************

package com.credu.gatepage;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;

/**
*GatePage��ڿ��԰��� ��
*<p>����:GatePageContactAdminBean.java</p>
*<p>����:GatePage��ڿ��԰��� ��</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author ������
*@version 1.0
*/
public class GatePageContactAdminBean {
    private ConfigSet config;
    private int row; 
	
	
	
    public GatePageContactAdminBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

   

    /**
    * ȭ�� ����Ʈ
    * @param box          receive from the form object and session
    * @return ArrayList   ��ڿ��� ����Ʈ
    
    */
    public ArrayList selectListContact(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
//		 ������ : 05.11.17 ������ : �̳��� _ totalcount �ϱ����� ��������
		String sql = "";
		String head_sql = "";
		String body_sql = "";
		String group_sql = "";
		String order_sql = "";
		String count_sql = "";
		
		String sql1 = "";
        DataBox dbox = null;
		
		String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        int v_pageno        = box.getInt("p_pageno");
        //int v_tabseq = box.getInt("p_tabseq");
		String v_gubun = box.getString("p_gubun");
        try {
            connMgr = new DBConnectionManager();
			
            list = new ArrayList();
            
			head_sql += " SELECT a.seq, b.name, b.email, b.jikmunm, get_jikwinm(b.jikwi, b.comp) as jikwinm, b.cono, " ;
			head_sql += "        a.quetitle,get_compnm(comp, 2, 4) as post, a.ismail, a.gubun ";
			body_sql += "  FROM TZ_CONTACT a, TZ_MEMBER b ";
			body_sql += "  WHERE  (a.QUEID = b.USERID) ";
			
			if (!v_gubun.equals("") || !v_gubun.equals("00")){
				body_sql +=	"and gubun = " + SQLString.Format(v_gubun);
			}else{
				body_sql += "and (gubun = '01' or gubun = '02' or gubun = '03' or gubun = '04')"	;
			}
			if ( !v_searchtext.equals("")) {      //    �˻�� ������
                v_pageno = 1;   //      �˻��� ��� ù��° �������� �ε��ȴ�
               
			    //    �������� �˻��Ҷ�
                    sql += " and a.quetitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }
            order_sql += " order by addate desc ";
			
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
            ls.close();
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
    * ȭ�� �󼼺���
    * @param box          receive from the form object and session
    * @return HomePageQnaData     ��ȸ�� ������
    * @throws Exception
    */
   public DataBox selectViewContact(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		
        DataBox dbox = null;
      
        int    v_seq   = box.getInt("p_seq");
		String v_cono = box.getString("p_cono");
        String v_gubun = box.getString("p_gubun");

        try {
            connMgr = new DBConnectionManager();
			
            sql = " SELECT a.seq, b.name, b.email, a.QUEID, ";
			sql += " get_jikwinm(b.jikwi, b.comp) as jikwinm, a.quecontent, a.gubun,";
			sql += " a.quetitle, get_compnm( comp, 2, 4 ) AS post, a.ANSTITLE, a.ANSCONTENT ";
			sql += " FROM TZ_CONTACT a, TZ_MEMBER b ";
			sql += " WHERE  (a.QUEID = b.USERID)  and b.cono = " + SQLString.Format(v_cono) + " and a.seq = " + v_seq ;
			//sql += " and gubun = " + SQLString.Format(v_gubun);
            ls = connMgr.executeQuery(sql);

            while(ls.next()) {
            //-------------------   2003.12.25  ����     -------------------------------------------------------------------
                dbox = ls.getDataBox();
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
        return dbox;
    }



    /**
    *  �����Ҷ�
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */
    public int deleteContact(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
		ListSet ls = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null;        
        
		String sql = "";
      
        int isOk1 = 1;
        int    v_seq     = box.getInt("p_seq");
		
        try {
            connMgr = new DBConnectionManager();
	            sql  = " delete from TZ_CONTACT";
                sql += "  where  seq = " + v_seq ;
                pstmt1 = connMgr.prepareStatement(sql);
            isOk1 = pstmt1.executeUpdate();
		 }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }  
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1 ;
    }	    


	 /**
    ��ڿ��� �亯�Ͽ� �����Ҷ�
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail
    */
     public int updateContact(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int v_seq = box.getInt("p_seq");

        //String v_ansid      = box.getString("p_ansid");
        String v_anstitle   = box.getString("p_anstitle");
        String v_anscontent = box.getString("p_anscontent");

        String v_ismail     = "Y";             // �亯 �ۼ��Ǹ鼭 Y �� ����

        String s_userid = box.getSession("userid");
		String v_gubun = box.getString("p_gubun");

        try {
            connMgr = new DBConnectionManager();

            sql  = " update TZ_CONTACT set ansid = ? , anstitle = ?, anscontent = ?, ismail = ?,      ";
            sql += "                       luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where seq = ? ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, s_userid);
            pstmt.setString(2, v_anstitle);
            pstmt.setString(3, v_anscontent);
            pstmt.setString(4, v_ismail);
            pstmt.setString(5, s_userid);
            pstmt.setInt(6, v_seq);

            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


	/**
	 * �ۼ��ڿ��� ������ �߼�
	 * @param box       receive from the form object and session
	 * @return is_Ok    1 : send ok      2 : send fail
	 * @throws Exception
	 */
	public int sendFormMail(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		Connection conn = null;
		ListSet ls = null;
		String sql = "";
		int is_Ok  = 0;    //  ���Ϲ߼� ���� ����

		int v_seq = box.getInt("p_seq");
		

		String v_ansid      = box.getSession("p_userid");
		String v_anstitle   = box.getString("p_anstitle");
		String v_anscontent = box.getString("p_anscontent");
		
		
		try {
			connMgr = new DBConnectionManager();

			sql  = " select a.seq seq, a.queid queid, a.quetitle quetitle, a.quecontent quecontent, ";
			sql += "        b.cono cono, b.name name, b.email email                 ";
			sql += "   from TZ_CONTACT a, TZ_MEMBER b                                               ";
			sql += "  where a.queid = b.userid                                                      ";
			sql += "    and a.seq  = " + v_seq ;

			ls = connMgr.executeQuery(sql);

////////////////////  ������ �߼� //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			String v_sendhtml = "contactus.html";

			FormMail fmail = new FormMail(v_sendhtml);     //      �����Ϲ߼��� ���

			MailSet mset = new MailSet(box);               //      ���� ���� �� �߼�


/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			if (ls.next()) {
				String v_queid      = ls.getString("queid");
				String v_quetitle   = ls.getString("quetitle");
				String v_quecontent = ls.getString("quecontent");
				String v_name       =  ls.getString("name");
				String v_toEmail    =  ls.getString("email");
				String v_toCono     =  ls.getString("cono");

				v_quecontent = StringManager.replace(v_quecontent,"\n","<br>");
				v_anscontent = StringManager.replace(v_anscontent,"\n","<br>");
				
				String v_mailTitle = v_anstitle;
				
				mset.setSender(fmail);     //  ���Ϻ����� ��� ����

				fmail.setVariable("quetitle", v_quetitle);
				fmail.setVariable("quecontent", v_quecontent);
				fmail.setVariable("anscontent", v_anscontent);				
//				fmail.setVariable("v_name", v_name);

				String v_mailContent = fmail.getNewMailContent();

				boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, "1", v_sendhtml);

				if(isMailed) is_Ok = 1;     //      ���Ϲ߼ۿ� �����ϸ�
			}
			ls.close();
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return is_Ok;
	}
}
