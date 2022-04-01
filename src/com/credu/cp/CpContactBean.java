/**
*����Ʈ�ý����� �����ϱ� ��
*<p>����:CpContactBean.java</p>
*<p>����:�����ϱ� ��</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author ��â��
*@version 1.0
*/
package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.dunet.common.util.StringUtil;


public class CpContactBean {
    private ConfigSet config;
    private int row;
	
    	
    public CpContactBean() {
        try{
            config = new ConfigSet();  
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
	/**
    * ����ڰ� �α����ߴٸ� ������� �̸�,����,����� �˻��Ѵ�
    * @param s_userid          �α����� ������� id
    * @return DataBox   ������� �̸�,����,����� ����
    */
	public static DataBox selectMail(String s_userid) throws Exception {

        DBConnectionManager connMgr = null;
        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        
	
		try {
			list = new ArrayList();
            connMgr = new DBConnectionManager();

            sql  = " select name, email, cono  ";
            sql += " from TZ_member                                         ";
            sql += " where userid  = " + SQLString.Format(s_userid);
           
            ls = connMgr.executeQuery(sql);

            
				 while(ls.next()){
					dbox = ls.getDataBox();
				 }
           
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception( ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }
    



	/**
    * ����ڰ� ��ڿ��� ������ ������
    * @param box          receive from the form object and session
    * @return isMailed   ���Ϲ߼ۿ� �����ߴٸ� TRUE�� �����Ѵ�
    */
	public boolean sendMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
		
        DataBox dbox = null;
		boolean isMailed = false;
		ArrayList list = null;
        
		String v_toEmail = "";
		String v_name = box.getString("p_name");
		String v_email = box.getString("p_email");
		String v_toCono = box.getString("p_cono");
		String v_mailTitle = StringUtil.removeTag(box.getString("p_title"));
		String v_content = StringUtil.removeTag(box.getString("p_content"));
		String v_hyuncono = box.getString("p_hyuncono");
		String v_kiacono = box.getString("p_kiacono");

		 MailSet mset = new MailSet(box);        //      ���� ���� �� �߼�
    
	try {
		list = new ArrayList();
		 connMgr = new DBConnectionManager();
		//----------------------   �޴� ����� �̸����� �����´� ----------------------------
		sql = "select email from tz_member where (cono =" + SQLString.Format(v_hyuncono) + "or  cono = " + SQLString.Format(v_kiacono) + ")";
		ls = connMgr.executeQuery(sql);
		while(ls.next()){
			dbox = ls.getDataBox();
			list.add(dbox);
		}
		
		
		//------------------------------------------------------------------------------------
		
		String v_mailContent = v_content + "�亯������ " + v_name +"���� "+v_email + "�� �����ּ���!!";

		
		
			 for(int i = 0; i < list.size(); i++) {
				dbox = (DataBox)list.get(i);
				v_toEmail = dbox.getString("d_email");
				
				
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

  
 
	    
}
