//**********************************************************
//  1. 제      목: 업체 관리(베타테스트시스템)
//  2. 프로그램명 : ContentCPBean.java
//  3. 개      요: 업체 관리(베타테스트시스템)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 26
//  7. 수      정:
//**********************************************************

package com.credu.beta;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;

/**
 * 업체 관리(ADMIN)
 *
 * @date   : 2004. 12
 * @author : S.W.Kang
 */
public class ContentCPBean {

    public ContentCPBean() {}


    /**
    운영자화면 리스트
    @param box          receive from the form object and session
    @return ArrayList   운영자 리스트
    */
    public ArrayList selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        String s_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql  = " select betaseq, ";
            sql += "        betacpnm, ";
            sql += "        userid, ";
            sql += "        usernm, ";
            sql += "        email, ";
            sql += "        tel ";
            sql += "   from tz_betacpinfo ";
            if (s_gadmin.equals("A1")){
                sql += "  order by betacpnm desc ";
            }
            else {
                sql += "  where userid='"+s_userid+"'";
                sql += "  order by betacpnm desc ";
            }

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
    컨텐츠 제공업체 등록할때
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int insertComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
		String v_count = "";

        int isOk = 0;
        int v_cnt = 0;       // 중복체크
		int v_applevel = 3;

        String v_compno		= box.getString("p_compno");
        String v_compnm		= box.getString("p_compnm");
        String v_userid		= box.getString("p_userid");
        String v_usernm		= box.getString("p_usernm");
        String v_passwd		= box.getString("p_passwd");
        String v_homesite	= box.getString("p_homesite");
        String v_address	= box.getString("p_address");
        String v_email		= box.getString("p_email");
        String v_tel		= box.getString("p_tel");
        
        String s_userid    = box.getSession("userid");

        try {
           connMgr = new DBConnectionManager();

           sql  = "select count(*) from TZ_BETACPINFO ";
           sql += " where betacpno  = " + StringManager.makeSQL(v_compno);
           ls = connMgr.executeQuery(sql);
           if (ls.next()) {
              v_cnt = ls.getInt(1);
           }
           ls.close();
			System.out.println("창훈아="+sql);
          if (v_cnt >0) {     // 기존 등록되어있으면
              isOk = 0;
          } else {
		   sql  =  "select 	to_char(max(isnull(betaseq,0)+1),'00000') count from TZ_BETACPINFO ";
		   ls = connMgr.executeQuery(sql);
		   if (ls.next()) {
		   		v_count = ls.getString(1);
		   	}
		   	ls.close();
		   
		   if (v_count.equals("")) {
		   	 v_count = "00001";
		   }
		   	
           sql1 =  "insert into TZ_BETACPINFO";	
           sql1 += "( betaseq,  ";
           sql1 += "  userid,   ";
           sql1 += "  usernm,   ";
           sql1 += "  betacpno, ";
           sql1 += "  betacpnm, ";
           sql1 += "  homesite, ";
           sql1 += "  address,  ";
           sql1 += "  email, 	";
           sql1 += "  luserid,  ";
           sql1 += "  ldate)    ";
           sql1 += "  values(?,?,?,?,?,?,?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'))               ";

           pstmt = connMgr.prepareStatement(sql1);
           pstmt.setString(1, v_count);
           pstmt.setString(2, v_userid);
           pstmt.setString(3, v_usernm);
           pstmt.setString(4, v_compno);
           pstmt.setString(5, v_compnm);
           pstmt.setString(6, v_homesite);
           pstmt.setString(7, v_address);
           pstmt.setString(8, v_email);
           pstmt.setString(9, s_userid);

           isOk = pstmt.executeUpdate();
          }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
    컨텐츠 제공업체 담당자 TZ_MEMBER테이블에 등록할때
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int insertMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
		String v_count = "";

        int isOk = 0;
        int v_cnt = 0;       // 중복체크

	    String v_userid		= box.getString("p_userid");
        String v_usernm		= box.getString("p_usernm");
        String v_passwd		= box.getString("p_passwd");
        String v_address	= box.getString("p_address");
        String v_email		= box.getString("p_email");
        String v_tel		= box.getString("p_tel");
        
        String s_userid    = box.getSession("userid");

        try {
           connMgr = new DBConnectionManager();

           sql  = "select count(*) from TZ_MEMBER ";
           sql += " where userid  = " + StringManager.makeSQL(v_userid);
           ls = connMgr.executeQuery(sql);
           if (ls.next()) {
              v_cnt = ls.getInt(1);
           }
           ls.close();

          if (v_cnt >0) {     // 기존 등록되어있으면
          	  
          	  sql  = "update TZ_MEMBER set pwd=?,email=?,addr=?,comptel=?,usergubun='O' where userid= "+ StringManager.makeSQL(v_userid);
          	  
          	  pstmt = connMgr.prepareStatement(sql);
          	  pstmt.setString(1, v_passwd);
          	  pstmt.setString(2, v_email);
          	  pstmt.setString(3, v_address);
          	  pstmt.setString(4, v_tel);
          	  isOk = pstmt.executeUpdate();
          	  
          } else {
			
           sql1 =  "insert into TZ_MEMBER";	
           sql1 += "( userid,  ";
           sql1 += "  name,   ";
           sql1 += "  pwd,   ";
           sql1 += "  email, ";
           sql1 += "  addr, ";
           sql1 += "  comptel, ";
           sql1 += "  usergubun) ";
           sql1 += "  values(?,?,?,?,?,?,'O')";

           pstmt = connMgr.prepareStatement(sql1);
           pstmt.setString(1, v_userid);
           pstmt.setString(2, v_usernm);
           pstmt.setString(3, v_passwd);
           pstmt.setString(4, v_email);
           pstmt.setString(5, v_address);
           pstmt.setString(6, v_tel);

           isOk = pstmt.executeUpdate();
          }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

   /**
    컨텐츠 제공업체 담당자 TZ_MANAGER테이블에 등록할때
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int insertManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
		String v_count = "";

        int isOk = 0;
        int v_cnt = 0;       // 중복체크

	    String v_userid		= box.getString("p_userid");
        String s_userid    = box.getSession("userid");

        try {
           connMgr = new DBConnectionManager();

           sql  = "select count(*) from TZ_MANAGER ";
           sql += " where userid  = " + StringManager.makeSQL(v_userid);
           ls = connMgr.executeQuery(sql);
           if (ls.next()) {
              v_cnt = ls.getInt(1);
           }
           ls.close();

          if (v_cnt >0) {     // 기존 등록되어있으면
              isOk = 1;
          } else {
			
           sql1 =  "insert into TZ_MANAGER";	
           sql1 += "( userid,  ";
           sql1 += "  gadmin,   ";
           sql1 += "  isdeleted,   ";
           sql1 += "  commented, ";
           sql1 += "  luserid, ";
           sql1 += "  ldate) ";
           sql1 += "  values(?,'Y1','N','베타테스트시스템 담당자',?, to_char(sysdate,'YYYYMMDDHH24MISS'))";

           pstmt = connMgr.prepareStatement(sql1);
           pstmt.setString(1, v_userid);
           pstmt.setString(2, s_userid);

           isOk = pstmt.executeUpdate();
          }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
	
	/**
	베타테스트 업체/담당자 정보 수정할때
	@param box
	@return isOk
	*/
	public DataBox selectViewComp(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
        DataBox dbox       = null;
        ListSet ls         = null;
        String sql         = "";
        String  v_betaseq  = box.getString("p_seq");
        
        try
        {
        	connMgr = new DBConnectionManager();
        	
        	sql  = "select a.betaseq, ";
        	sql += "       a.userid, ";
        	sql += "       a.usernm, ";
        	sql += "       b.pwd, ";
        	sql += "       a.betacpno, ";
        	sql += "       a.betacpnm, ";
        	sql += "       a.homesite, ";
        	sql += "       a.address, ";
        	sql += "       a.email, ";
        	sql += "       a.tel ";
        	sql += "  from TZ_BETACPINFO a,";
        	sql += "       TZ_MEMBER b ";
        	sql += " where a.userid=b.userid ";
        	sql += "   and a.betaseq= "+ StringManager.makeSQL(v_betaseq);
System.out.println("sql="+sql);        	
        	ls = connMgr.executeQuery(sql);
        	if (ls.next()) {
        		dbox = ls.getDataBox();
        	}
        	ls.close();
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
        	if (ls != null) { try { ls.close(); } catch (Exception e) {}}
        	if (connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) {} }
        }
        return dbox;
	}
	
    /**
    운영자 수정하여 저장할때
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail
    */
     public int updateManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        String sql = "";
        String v_betaseq	= box.getString("p_betaseq");
		String v_betacpnm 	= box.getString("p_betacpnm");
		String v_usernm		= box.getString("p_usernm");
		String v_homesite	= box.getString("p_homesite");
		String v_address	= box.getString("p_address");
		String v_email		= box.getString("p_email");
		String v_tel		= box.getString("p_tel");

		int v_count = 0;

        int isOk = 0;

        String s_userid    = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            
            sql  = "select count(betaseq) ";
            sql += "  from TZ_BETACPINFO ";
            sql += " where betaseq='"+v_betaseq+"'";
            ls = connMgr.executeQuery(sql);

            if (ls.next()){
            	v_count = ls.getInt(1);
            }
            ls.close();	
            
            System.out.println("v_count="+v_count);
           
           if (v_count > 0) { 
           
            	sql  = " update TZ_BETACPINFO set betacpnm = ? ,";
            	sql += "                      usernm = ?,";
            	sql += "                      homesite = ?,";
            	sql += "                      email = ?,";
            	sql += "					  address = ?,";
            	sql += "                      tel = ?,";
            	sql += "                      luserid= ? ,";
            	sql += "                      ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            	sql += "  where betaseq  = ?";
            	
            	pstmt = connMgr.prepareStatement(sql);
            	
            	pstmt.setString(1, v_betacpnm);
            	pstmt.setString(2, v_usernm);
            	pstmt.setString(3, v_homesite);
            	pstmt.setString(4, v_email);
            	pstmt.setString(5, v_address);
            	pstmt.setString(6, v_tel);
            	pstmt.setString(7, s_userid);
            	pstmt.setString(8, v_betaseq);
            	
            	isOk = pstmt.executeUpdate();
            }
            else {
            	isOk = 0;
            }
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
    운영자 수정하여 저장할때
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail
    */
     public int updateMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        String sql = "";
        String v_betaseq	= box.getString("p_betaseq");
		String v_passwd		= box.getString("p_passwd");
		String v_usernm		= box.getString("p_usernm");
		String v_address	= box.getString("p_address");
		String v_email		= box.getString("p_email");
		String v_tel		= box.getString("p_tel");
		String v_userid = "";
		
		int v_count = 0;

        int isOk = 0;

        String s_userid    = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            
            sql  = "select a.userid ";
            sql += "  from TZ_BETACPINFO a,";
            sql += "       TZ_MEMBER b";
            sql += " where a.userid=b.userid";
            sql += "   and a.betaseq='"+v_betaseq+"'";
            ls = connMgr.executeQuery(sql);

            if (ls.next()){
            	v_userid = ls.getString("userid");
            }
            ls.close();	
            
           
           if (v_userid != "") { 
           
            	sql  = " update TZ_MEMBER set name = ? ,";
            	sql += "                      pwd = ?,";
            	sql += "                      addr = ?,";
            	sql += "                      email = ?,";
            	sql += "					  comptel = ?";
            	sql += "  where userid  = ?";
            	
            	pstmt = connMgr.prepareStatement(sql);
            	
            	pstmt.setString(1, v_usernm);
            	pstmt.setString(2, v_passwd);
            	pstmt.setString(3, v_address);
            	pstmt.setString(4, v_email);
            	pstmt.setString(5, v_tel);
            	pstmt.setString(6, v_userid);
            	
            	isOk = pstmt.executeUpdate();
            }
            else {
            	isOk = 0;
            }
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
    권한 삭제할때
    @param box      receive from the form object and session
    @return isOk    1:delete success,0:delete fail
    */
    public int deleteManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_gadmin     = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);  

    				// 권한관련 테이블에서 삭제(기본 권한은 삭제할 수 없다.)
            sql  = " delete from TZ_GADMIN      ";
            sql += "  where gadmin  = ?          ";            

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_gadmin);
            isOk = pstmt.executeUpdate();

            if(isOk > 0 ) connMgr.commit();
            else connMgr.rollback();

        }
        catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }


}
