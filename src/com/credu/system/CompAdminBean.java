//**********************************************************
//  1. 제      목: 회사조직분류
//  2. 프로그램명 : CompAdminBean.java
//  3. 개      요: 회사조직분류 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 16
//  7. 수      정:
//**********************************************************

package com.credu.system;

import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * 회사조직분류 관리(ADMIN)
 *
 * @date   : 2003. 7
 * @author : s.j Jung
 */
public class CompAdminBean {

    public CompAdminBean() {}

    /**
    조직코드 검색 리스트
    @param box          receive from the form object and session
    @return ArrayList   조직코드 검색 리스트
    */
    @SuppressWarnings("unchecked")
    public ArrayList searchDept(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";	
		String head_sql = "";
		String body_sql = "";
		String order_sql = "";
		String count_sql = "";
        CompData data = null;

        String v_search     = box.getString("p_gubun");
        String v_searchtext = box.getString("p_key1");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            head_sql  = " select comp, groupsnm, companynm, gpmnm, deptnm   ";
            body_sql =  " from TZ_COMP  where comptype = 4                    ";
            body_sql += " and isUsed   = 'Y'       ";

            if ( !v_searchtext.equals("")) {                            //    검색어가 있으면
                if (v_search.equals("comp")) {                          //    코드로 검색할때
                    body_sql += " and comp like   " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("deptnm")) {                 //    명칭으로 검색할때
                    body_sql += " and deptnm like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }
            order_sql += "   order by comp asc     ";
			
			sql = head_sql + body_sql + order_sql;
			count_sql = " select  count(*) " + body_sql ;

            ls = connMgr.executeQuery(sql);
            ls.setPageSize(10);                         //  페이지당 row 갯수를 세팅한다

			// 수정: lyh  일자: 2005-11-17  내용: setCurrentPage에 total count적용  
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); 
            ls.setCurrentPage(v_pageno, total_row_count);                //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();   //     전체 페이지 수를 반환한다
            // int total_row_count = ls.getTotalCount();   //     전체 row 수를 반환한다

            while (ls.next()) {
                data = new CompData();

                data.setComp(ls.getString("comp"));
                data.setGroupsnm(ls.getString("groupsnm"));
                data.setCompanynm(ls.getString("companynm"));
                data.setGpmnm(ls.getString("gpmnm"));
                data.setDeptnm(ls.getString("deptnm"));
                data.setDispnum(total_row_count - ls.getRowNum() + 1);
                data.setTotalPageCount(total_page_count);

                list.add(data);
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
     * 회사코드셀렉트박스
	 * @param group         그룹코드
	 * @param selected      선택값
	 * @return result       select box 구성 String
	 * @throws Exception
	 */
    public static String getCompanySelect (String group, String selected) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";

        String value = "";
        String name  = "";

        result  = "  <SELECT name='company' onChange=\"companyChange()\" > \n";
        result += " <option value=''>ALL</option> \n";

        try {
            connMgr = new DBConnectionManager();

            sql  = " select company,companynm from TZ_COMP   ";
            sql += " where groups    = " + StringManager.makeSQL(group);
            sql += "   and comptype  = 2                     ";
            sql += "   and isUsed    = 'Y'                   ";
            sql += " order by company asc                    ";
            System.out.println(sql);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                value = ls.getString("company");
                name  = ls.getString("companynm");

                result += " <option value=" + value;
                if (selected.equals(value)) {
                    result += " selected ";
                }
                
                result += ">" + name + "</option> \n";
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        result += "  </SELECT> \n";
        return result;
    } 


    /**
     * 사업부코드셀렉트박스
	 * @param group         그룹코드
	 * @param company       회사코드
	 * @param selected      선택값
	 * @return result       select box 구성 String
	 * @throws Exception
	 */
    public static String getGpmSelect (String group, String company, String selected) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";

        String value = "";
        String name  = "";

        result  = "  <SELECT name='gpm' onChange=\"gpmChange()\" > \n";
        result += " <option value=''>ALL</option> \n";

        try {
            connMgr = new DBConnectionManager();

            sql  = " select gpm, gpmnm from TZ_COMP    ";
            sql += " where groups   = " + StringManager.makeSQL(group);
            sql += "   and company  = " + StringManager.makeSQL(company);
            sql += "   and comptype = 3                    ";
            sql += "   and isUsed   = 'Y'                  ";
            sql += " order by gpm asc                      ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                value = ls.getString("gpm");
                name  = ls.getString("gpmnm");

                result += " <option value=" + value;
                if (selected.equals(value)) {
                    result += " selected ";
                }
                
                result += ">" + name + "</option> \n";
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        result += "  </SELECT> \n";
        return result;
    } 

    /**
     * 부서코드셀렉트박스
	 * @param group         그룹코드
	 * @param company       회사코드
	 * @param gpm           사업부코드
	 * @param selected      선택값
	 * @return result       select box 구성 String
	 * @throws Exception
	 */
    public static String getDeptSelect (String group, String company, String gpm, String selected) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";

        String value = "";
        String name  = "";

        result  = "  <SELECT name='dept' > \n";
        result += " <option value=''>ALL</option> \n";

        try {
            connMgr = new DBConnectionManager();

            sql  = " select dept, deptnm from TZ_COMP   ";
            sql += " where groups   = " + StringManager.makeSQL(group);
            sql += "   and company  = " + StringManager.makeSQL(company);
            sql += "   and gpm      = " + StringManager.makeSQL(gpm);
            sql += "   and comptype = 4                    ";
            sql += "   and isUsed   = 'Y'                  ";
            sql += " order by dept asc                     ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                value = ls.getString("dept");
                name  = ls.getString("deptnm");

                result += " <option value=" + value;
                if (selected.equals(value)) {
                    result += " selected ";
                }
                
                result += ">" + name + "</option> \n";
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        result += "  </SELECT> \n";
        return result;
    } 

    /**
     * 직급코드셀렉트박스
	 * @param selected      선택값
	 * @return result       select box 구성 String
	 * @throws Exception
	 */
    public static String getJikupSelect (String selected) throws Exception {
        return getJikupSelect("", selected);
    }

    /**
     * 직급코드셀렉트박스
	 * @param grpcomp       grpcomp
	 * @param selected      선택값
	 * @return result       select box 구성 Stringresult
	 * @throws Exception
	 */
    public static String getJikupSelect (String grpcomp, String selected) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";

        String value = "";
        String name  = "";

        result  = "  <SELECT name='jikup' > \n";
        result += " <option value=''>ALL</option> \n";

        try {
            connMgr = new DBConnectionManager();

            sql  = " select jikup, jikupnm from TZ_JIKUP   ";
            if (!grpcomp.equals("")) {
                sql += " where grpcomp   = " + StringManager.makeSQL(grpcomp);
            }
            sql += " order by jikup asc                      ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                value = ls.getString("jikup");
                name  = ls.getString("jikupnm");

                result += " <option value=" + value;
                if (selected.equals(value)) {
                    result += " selected ";
                }
                
                result += ">" + name + "</option> \n";
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        result += "  </SELECT> \n";
        return result;
    } 

    /**
     * 직군코드셀렉트박스
	 * @param selected      선택값
	 * @return result       select box 구성 String
	 * @throws Exception
	 */
    public static String getJikunSelect (String selected) throws Exception {
        return getJikunSelect("", selected);
    }

    /** 
     * 직군코드셀렉트박스
	 * @param grpcomp       grpcomp 
	 * @param selected      선택값
	 * @return result       select box 구성 String
	 * @throws Exception
	 */
    public static String getJikunSelect (String grpcomp, String selected) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";

        String value = "";
        String name  = "";

        result  = "  <SELECT name='jikun' > \n";
        result += " <option value=''>ALL</option> \n";


        try {
            connMgr = new DBConnectionManager();

            sql  = " select jikun, jikunnm from TZ_JIKUN   ";
            if (!grpcomp.equals("")) {
                sql += " where grpcomp   = " + StringManager.makeSQL(grpcomp);
            }
            sql += " order by jikun asc                      ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                value = ls.getString("jikun");
                name  = ls.getString("jikunnm");

                result += " <option value=" + value;
                if (selected.equals(value)) {
                    result += " selected ";
                }
                
                result += ">" + name + "</option> \n";
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        result += "  </SELECT> \n";
        return result;
    } 

	/**
	 * 조직이름(그룹,회사,사업부,부서,팀)
	 * @param code          comp code
	 * @param comptype      comp type
	 * @return result       comp name
	 * @throws Exception
	 */
	public static String getCompName (String code, int comptype) throws Exception {
		 DBConnectionManager connMgr = null;
		 ListSet ls = null;
		 String result = "";
		 String sql = "";
         String strcol = "";
        
         switch (comptype){
         	case 1  : strcol = "groupsnm"; break;          
			case 2  : strcol = "companynm"; break;
			case 3  : strcol = "gpmnm"; break;			         	
			case 4  : strcol = "deptnm"; break;
			case 5  : strcol = "partnm"; break;
			default : strcol = "compnm";
         }
		 try {
			 connMgr = new DBConnectionManager();

			 sql  = " select " + strcol + " from TZ_COMP   ";
			 sql += " where comp   = " + StringManager.makeSQL(code);
			 ls = connMgr.executeQuery(sql);

			 if (ls.next()) {
				 result = ls.getString(strcol);
			 }
		 }
		 catch (Exception ex) {
			 ErrorManager.getErrorStackTrace(ex);
			 throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		 }
		 finally {
			 if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			 if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		 }

		 return result;
	 } 
    

}
