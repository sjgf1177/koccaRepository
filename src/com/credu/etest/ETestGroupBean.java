//**********************************************************
//1. 제      목: 온라인테스트 그룹관리
//2. 프로그램명: ETestGroupBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
//
//**********************************************************

package com.credu.etest;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;
import com.credu.etest.*;
import java.text.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ETestGroupBean {


    public ETestGroupBean() {}

    /**
    그룹 리스트
    @param box          receive from the form object and session
    @return ArrayList   그룹 리스트
    */
    public ArrayList selectETestGroupList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;

        String v_upperclass  = box.getStringDefault("s_upperclass","ALL");
        String v_grcode      = box.getString("s_grcode");
        String v_action      = box.getStringDefault("p_action","change");

        String v_search        = box.getString("p_search");
        String v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
        String v_orderType     = box.getString("p_orderType");              //정렬할 순서 
        
        try {
            connMgr = new DBConnectionManager();
            if (v_action.equals("go")) {
                list = getETestGroupList(connMgr, v_grcode, v_upperclass, v_search, v_orderColumn, v_orderType);
            } else {
                list = new ArrayList();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

    /**
    그룹 리스트
    @param box          receive from the form object and session
    @return ArrayList   그룹 리스트
    */
    public ArrayList getETestGroupList(DBConnectionManager connMgr, String p_grcode, String p_upperclass, String p_search, String p_orderColumn, String p_orderType) throws Exception {
        ArrayList list = new ArrayList();
        ListSet ls = null;
        String sql  = "";
        DataBox dbox    = null;

        try {
            sql = "select a.etestsubj, a.grcode, a.etestsubjnm, a.upperclass, b.codenm upperclassnm, a.indate ";
            sql+= "  from TZ_ETESTSUBJ a, TZ_CODE b                                                   ";
            sql+= " where a.upperclass = b.code                                       ";
            sql+= "   and b.gubun    = " + SQLString.Format(ETestBean.ETSUBJ_CLASS);
            sql+= "   and a.grcode       = " + SQLString.Format(p_grcode);

			if (!p_upperclass.equals("ALL")) {
                sql += " and a.upperclass = " + SQLString.Format(p_upperclass);
            }         
			if (!p_search.equals("")) {
                //sql+= "   and a.etestsubjnm like " + SQLString.Format("%"+p_search+"%");
                sql+= "  and (lower(a.etestsubjnm) like " + SQLString.Format("%"+p_search+"%") + " or upper(a.etestsubjnm) like "+ SQLString.Format("%"+p_search+"%") +")";
            }
            //sql+= " order by a.etestsubj    ";
            
			if(p_orderColumn.equals("")) {
            	sql+= " order by a.etestsubj  ";
			} else {
			    sql+= " order by " + p_orderColumn + p_orderType;
			}            

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {

                dbox = ls.getDataBox();
                    list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
        }
        return list;
    }


    /**
    그룹 상세보기
    @param box                receive from the form object and session
    @return ETestGroupData  조회한 그룹정보
    */
    public DataBox selectETestGroupData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";
        DataBox dbox = null;

        String v_etestsubj = box.getString("p_etestsubj");

        try {
            connMgr = new DBConnectionManager();

            sql = "select a.etestsubj, a.grcode, a.etestsubjnm, a.upperclass, b.codenm upperclassnm, a.indate ";
            sql+= "  from TZ_ETESTSUBJ a, TZ_CODE b                                                   ";
            sql+= " where a.upperclass = b.code                                      ";
            sql+= "   and b.gubun    = " + SQLString.Format(ETestBean.ETSUBJ_CLASS);
            sql+= "   and a.etestsubj     = " + SQLString.Format(v_etestsubj);

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
		dbox = ls.getDataBox();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }

    /**
    그룹등록
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
    */
    public int insertETestGroup(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_grcode  = box.getString("p_grcode");
        String  v_etestsubj     = "";
        String  v_etestsubjnm   = box.getString("p_etestsubjnm");
        String  v_upperclass= box.getString("s_upperclass");
        String  v_indate   = FormatDate.getDate("yyyyMMddHHmmss");
        String  v_luserid  = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            v_etestsubj = getMaxSubjcode(connMgr);

            //insert TZ_ETESTSUBJ table
            sql =  " insert into TZ_ETESTSUBJ(etestsubj, grcode, etestsubjnm, upperclass, indate, luserid, ldate  ) ";
            sql+=  "                values(?,    ?,      ?,          ?,    ?,      ?,      ?           ) ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString( 1, v_etestsubj);
            pstmt.setString( 2, v_grcode);
            pstmt.setString( 3, v_etestsubjnm);
            pstmt.setString( 4, v_upperclass);
            pstmt.setString( 5, v_indate);
            pstmt.setString( 6, v_luserid);
            pstmt.setString( 7, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();

        }
        catch(Exception ex) {
            isOk = 0;
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
    그룹 수정할때
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail
    */
    public int updateETestGroup(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        ListSet ls = null;

        String  v_etestsubj       = box.getString("p_etestsubj");
        String  v_etestsubjnm     = box.getString("p_etestsubjnm");
        String  v_upperclass = box.getString("s_upperclass");
        String  v_luserid    = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            // 삭제체크
            sql = " select userid from tz_etestresult where etestsubj='"+v_etestsubj+"' ";
            ls = connMgr.executeQuery(sql);
		    		if (ls.next()) {
		    		    isOk = -2;
		    		}
		    		
		    		if(isOk==0){
	            //update TZ_ETESTSUBJ table
	            sql =  " update TZ_ETESTSUBJ        ";
	            sql+=  "    set etestsubjnm    =  ?, ";
	            sql+=  "        upperclass =  ?, ";
	            sql+=  "        luserid    =  ?, ";
	            sql+=  "        ldate      =  ?  ";
	            sql+=  "  where etestsubj       =  ?  ";
	
	            pstmt = connMgr.prepareStatement(sql);
	            pstmt.setString( 1, v_etestsubjnm);
	            pstmt.setString( 2, v_upperclass);
	            pstmt.setString( 3, v_luserid);
	            pstmt.setString( 4, FormatDate.getDate("yyyyMMddHHmmss"));
	            pstmt.setString( 5, v_etestsubj);
	
	            isOk = pstmt.executeUpdate();
	            
	         }
        }
        catch(Exception ex) {
            isOk = 0;
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }        	
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
    그룹 삭제할때
    @param box      receive from the form object and session
    @return isOk    1:delete success,0:delete fail
    */
    public int deleteETestGroup(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls = null;
        int isOk = 0;

        String  v_etestsubj       = box.getString("p_etestsubj");
        String  v_duserid    = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            // 삭제체크
            sql = " select userid from tz_etestresult where etestsubj='"+v_etestsubj+"' ";
            ls = connMgr.executeQuery(sql);
		    		if (ls.next()) {
		    		    isOk = -2;
		    		}
    		
    		if(isOk==0){ 
    		
                //delete TZ_ETESTSUBJ table
                sql =  " delete from  TZ_ETESTSUBJ        ";
                sql+=  "  where etestsubj       =  ?  ";
    
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString( 1, v_etestsubj);
    
                isOk = pstmt.executeUpdate();
            }
        }
        catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }            
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
    그룹코드구하기
    @param box           receive from the form object and session
    @return v_subjcode   그룹코드
    */
    public String getMaxSubjcode(DBConnectionManager connMgr) throws Exception {
        String v_subjcode = "";
        String v_maxsubj = "";
        int    v_maxno   = 0;

        ListSet ls = null;
        String sql  = "";
        try {
            sql = " select max(etestsubj) maxsubj from TZ_ETESTSUBJ ";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                 v_maxsubj = ls.getString("maxsubj");
            }

            if (v_maxsubj.equals("")) {
                v_subjcode = "ET00001";
            } else {
                v_maxno = Integer.valueOf(v_maxsubj.substring(2)).intValue();
                v_subjcode = "ET" + new DecimalFormat("00000").format(v_maxno+1);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_subjcode;
    }

    /**
    그룹명
    @param box       receive from the form object and session
    @return result   그룹명
    */
     public static String get_subjnm(String subj) throws Exception{
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";
        String result ="";

         try {
             connMgr = new DBConnectionManager();
             sql = " select  subjnm  from TZ_ETESTSUBJ where subj="+SQLString.Format(subj);
             ls = connMgr.executeQuery(sql);
             if(ls.next())  result = ls.getString("subjnm");
         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex);
             throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
         }finally {
             if(ls != null) { try { ls.close(); }catch (Exception e) {} }
             if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
         }

         return result;
    }

}