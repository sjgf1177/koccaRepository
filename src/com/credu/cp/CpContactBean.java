/**
*게이트시스템의 문의하기 빈
*<p>제목:CpContactBean.java</p>
*<p>설명:문의하기 빈</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
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
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
	/**
    * 사용자가 로그인했다면 사용자의 이름,메일,사번을 검색한다
    * @param s_userid          로그인한 사용자의 id
    * @return DataBox   사용자의 이름,메일,사번을 리턴
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
    * 사용자가 운영자에게 메일을 보낸다
    * @param box          receive from the form object and session
    * @return isMailed   메일발송에 성공했다면 TRUE를 리턴한다
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

		 MailSet mset = new MailSet(box);        //      메일 세팅 및 발송
    
	try {
		list = new ArrayList();
		 connMgr = new DBConnectionManager();
		//----------------------   받는 사람의 이메일을 가져온다 ----------------------------
		sql = "select email from tz_member where (cono =" + SQLString.Format(v_hyuncono) + "or  cono = " + SQLString.Format(v_kiacono) + ")";
		ls = connMgr.executeQuery(sql);
		while(ls.next()){
			dbox = ls.getDataBox();
			list.add(dbox);
		}
		
		
		//------------------------------------------------------------------------------------
		
		String v_mailContent = v_content + "답변메일은 " + v_name +"님의 "+v_email + "로 보내주세요!!";

		
		
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
