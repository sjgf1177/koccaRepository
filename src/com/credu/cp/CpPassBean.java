/**
*외주관리시스템의 비밀번호 빈
*<p>제목:CpPassBean.java</p>
*<p>설명:비밀번호 빈</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 박준현
*@version 1.0
*/
package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;


public class CpPassBean {
  private ConfigSet config;

  public CpPassBean() {

  }

/**
    * 사용자의 id가 있는지 검색 
    * @param box          receive from the form object and session
    * @return isId	  검색한 id가 있으면 true를 리턴한다
    */	
  public boolean selectid(RequestBox box) throws Exception {
	DBConnectionManager connMgr = null;
    ListSet ls = null;
    String sql = "";
    DataBox dbox = null;
    boolean isId = false;
	String userid = "";

    String v_userid = box.getString("p_userid");
    String v_resno = box.getString("p_resno");
	  try{
		connMgr = new DBConnectionManager();
		 sql = "select userid";
		 sql += " from tz_member where userid = " + SQLString.Format(v_userid) ;
		
		 ls = connMgr.executeQuery(sql);
		 while (ls.next()) {
			userid = ls.getString("userid");
			if (v_userid.equals(userid)){
				isId = true;
			}
			
		}
		ls.close();
	  }
		catch (Exception ex) {
		  ErrorManager.getErrorStackTrace(ex, box, sql);
		  throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
		  if (ls != null) {
			try {
			  ls.close();
			}
			catch (Exception e) {}
		  }
		  if (connMgr != null) {
			try {
			  connMgr.freeConnection();
			}
			catch (Exception e10) {}
		  }
		}
	
		return isId;
	}

/**
    * 사용자의 이름이 있는지 검색 
    * @param box          receive from the form object and session
    * @return isId	  검색한 이름이 있으면 true를 리턴한다
    */	
  public boolean selectname(RequestBox box) throws Exception {
	DBConnectionManager connMgr = null;
    ListSet ls = null;
    String sql = "";
    DataBox dbox = null;
    boolean isId = false;
	String userid = "";
	String name   = "";

    String v_userid = box.getString("p_userid");
    String v_name = box.getString("p_name");

	  try{
		connMgr = new DBConnectionManager();
		 sql = "select userid, name";
		 sql += " from tz_member where userid = " + SQLString.Format(v_userid) +" and name = " +  SQLString.Format(v_name);
		
		 ls = connMgr.executeQuery(sql);
		 while (ls.next()) {
			userid = ls.getString("userid");
			name  = ls.getString("name");
			if (v_userid.equals(userid)){
				isId = true;
			}
			
		}
		ls.close();
	  }
		catch (Exception ex) {
		  ErrorManager.getErrorStackTrace(ex, box, sql);
		  throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
		  if (ls != null) {
			try {
			  ls.close();
			}
			catch (Exception e) {}
		  }
		  if (connMgr != null) {
			try {
			  connMgr.freeConnection();
			}
			catch (Exception e10) {}
		  }
		}
	
		return isId;
	}


	/**
    * 사용자의 주민번호가 맞는지 검색 
    * @param box          receive from the form object and session
    * @return isPwd	  검색한 값이 모두 일치하면 true를 리턴한다
    */	
  public boolean selectpwd(RequestBox box) throws Exception {
	DBConnectionManager connMgr = null;
    ListSet ls = null;
    String sql = "";
    DataBox dbox = null;
    boolean isPwd = false;
	String userid = "";
	String resno  = "";
	String name   = "";

    String v_userid = box.getString("p_userid");
    String v_resno  = box.getString("p_resno");
    String v_name   = box.getString("p_name");
	  try{
		connMgr = new DBConnectionManager();
		sql = "select userid, name, resno";
		sql += " from tz_member where userid = " + SQLString.Format(v_userid) + " and name = " +  SQLString.Format(v_name) +" and resno = " + SQLString.Format(v_resno);

		 ls = connMgr.executeQuery(sql);
		 while (ls.next()) {
			userid = ls.getString("userid");
			resno = ls.getString("resno");
			name = ls.getString("name");
			if (v_userid.equals(userid)  && v_resno.equals(resno)&& v_name.equals(name)){
				isPwd = true;
			}
			
		}
		ls.close();
	  }
		catch (Exception ex) {
		  ErrorManager.getErrorStackTrace(ex, box, sql);
		  throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
		  if (ls != null) {
			try {
			  ls.close();
			}
			catch (Exception e) {}
		  }
		  if (connMgr != null) {
			try {
			  connMgr.freeConnection();
			}
			catch (Exception e10) {}
		  }
		}
		return isPwd;
	}
   /**
    * 검색한 비밀번호를 사용자화면에 뿌려준다.
    * @param box          receive from the form object and session
    * @return isMailed	  


  public boolean selectPds(RequestBox box) throws Exception {
    DBConnectionManager connMgr = null;
    ListSet ls = null;
    String sql = "";
    DataBox dbox = null;
    boolean isMailed = false;

    String v_userid = box.getString("p_userid");
    String v_resno = box.getString("p_resno");
    String v_mailTitle = "HKMC운영자입니다! ";

    try {
      connMgr = new DBConnectionManager();
      sql = "select pwd ";
      sql += " from tz_member where userid = " + SQLString.Format(v_userid) +
          " and resno = " + SQLString.Format(v_resno);
      ls = connMgr.executeQuery(sql);

	  
	  while (ls.next()) {
		 dbox = ls.getDataBox();
      }
      ls.close();

    }
    catch (Exception ex) {
      ErrorManager.getErrorStackTrace(ex, box, sql);
      throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
    }
    finally {
      if (ls != null) {
        try {
          ls.close();
        }
        catch (Exception e) {}
      }
      if (connMgr != null) {
        try {
          connMgr.freeConnection();
        }
        catch (Exception e10) {}
      }
    }
    return isMailed;
  }
    */
    /**
    * 검색한 비밀번호를 사용자화면에 뿌려준다.
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */

    public DataBox selectPds(RequestBox box) throws Exception {

		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		DataBox dbox = null;

		String v_userid = box.getString("p_userid");
		String v_resno = box.getString("p_resno");
        try {
			connMgr = new DBConnectionManager();
			sql = "select pwd, userid, name ";
			sql += " from tz_member where userid = " + SQLString.Format(v_userid) +
			  " and resno = " + SQLString.Format(v_resno);
			ls = connMgr.executeQuery(sql);

			
			while (ls.next()) {
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

}