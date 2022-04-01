//**********************************************************
//  1. 제      목: 관리자권한
//  2. 프로그램명 : GadminAdminBean.java
//  3. 개      요: 관리자권한 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 16
//  7. 수      정:
//**********************************************************

package com.credu.system;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.StringManager;

/**
 * 회사조직분류 관리(ADMIN)
 * 
 * @date : 2003. 7
 * @author : s.j Jung
 */
public class GadminAdminBean {

    public GadminAdminBean() {
    }

    /**
     * 권한코드 검색 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 권한코드 검색 리스트
     */
    /*
     * public ArrayList searchGadmin(RequestBox box) throws Exception {
     * DBConnectionManager connMgr = null; ListSet ls = null; ArrayList list =
     * null; String sql = ""; GadminData data = null;
     * 
     * String v_search = box.getString("p_gubun"); String v_searchtext =
     * box.getString("p_key1"); int v_pageno = box.getInt("p_pageno");
     * 
     * try { connMgr = new DBConnectionManager();
     * 
     * list = new ArrayList();
     * 
     * sql =
     * " select gadmin, control, gadminnm, comments, isneedsubj, isneedcomp from TZ_GADMIN   "
     * ;
     * 
     * if ( !v_searchtext.equals("")) { // 검색어가 있으면 if
     * (v_search.equals("gadmin")) { // 코드로 검색할때 sql += " where comp like   " +
     * StringManager.makeSQL("%" + v_searchtext + "%"); } else if
     * (v_search.equals("gadminnm")) { // 명칭으로 검색할때 sql += " where deptnm like "
     * + StringManager.makeSQL("%" + v_searchtext + "%"); } } sql +=
     * "   order by gadmin asc     ";
     * 
     * ls = connMgr.executeQuery(sql);
     * 
     * ls.setPageSize(10); // 페이지당 row 갯수를 세팅한다 ls.setCurrentPage(v_pageno); //
     * 현재페이지번호를 세팅한다. int total_page_count = ls.getTotalPage(); // 전체 페이지 수를
     * 반환한다 int total_row_count = ls.getTotalCount(); // 전체 row 수를 반환한다
     * 
     * while (ls.next()) { data = new GadminData();
     * 
     * data.setGadmin(ls.getString("gadmin"));
     * data.setGadminnm(ls.getString("gadminnm"));
     * data.setControl(ls.getString("control"));
     * data.setComments(ls.getString("comments"));
     * data.setIsneedsubj(ls.getString("isneedsubj"));
     * data.setIsneedcomp(ls.getString("isneedcomp"));
     * data.setDispnum(total_row_count - ls.getRowNum() + 1);
     * data.setTotalPageCount(total_page_count);
     * 
     * list.add(data); } } catch (Exception ex) {
     * ErrorManager.getErrorStackTrace(ex, box, sql); throw new
     * Exception("sql = " + sql + "\r\n" + ex.getMessage()); } finally { if(ls
     * != null) { try { ls.close(); }catch (Exception e) {} } if(connMgr !=
     * null) { try { connMgr.freeConnection(); }catch (Exception e10) {} } }
     * return list; }
     */

    /**
     * 권한코드 셀렉트박스
     * 
     * @param name,selected,event,allcheck 셀렉트박스명,선택값,이벤트명,전체유무(0 -전체없음, 1 -
     *        전체있음)
     * @return result 권한코드 + "," + 교육그룹필요여부 + "," + 과정코드필요여부 + "," + 회사코드필요여부 +
     *         "," + 부서코드필요여부
     */
    public static String getGadminSelect(String name, String selected, String event) throws Exception {
        return getGadminSelect(name, selected, event, 1);
    }

    public static String getGadminSelect(String name, String selected, String event, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;
        String v_value = "";

        result = "  <SELECT name=" + name + " " + event + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>=== 전체 ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadmin, gadminnm, isneedgrcode, isneedsubj, isneedcomp,isneeddept,seq from tz_gadmin  ";
            sql += " where isview = 'Y'";
            sql += " order by seq asc,gadmin asc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GadminData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                data.setIsneedgrcode(ls.getString("isneedgrcode"));
                data.setIsneedsubj(ls.getString("isneedsubj"));
                data.setIsneedcomp(ls.getString("isneedcomp"));
                data.setIsneeddept(ls.getString("isneeddept"));
                v_value = data.getGadmin() + "," + data.getIsneedgrcode() + "," + data.getIsneedsubj() + "," + data.getIsneedcomp() + "," + data.getIsneeddept();
                result += " <option value=\"" + v_value + "\"";
                if (selected.equals(v_value)) {
                    result += " selected ";
                }
                result += ">" + data.getGadminnm() + "</option> \n";
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        result += "  </SELECT> \n";
        return result;
    }

    /**
     * 권한코드 셀렉트박스(강사제외)
     * 
     * @param name,selected,event,allcheck 셀렉트박스명,선택값,이벤트명,전체유무(0 -전체없음, 1 -
     *        전체있음)
     * @return result 권한코드 + "," + 교육주관필요여부 + "," + 과정코드필요여부 + "," + 회사코드필요여부 +
     *         "," + 부서코드필요여부
     */
    public static String getGadminSelectNop(String name, String selected, String event) throws Exception {
        return getGadminSelectNop(name, selected, event, 1);
    }

    public static String getGadminSelectNop(String name, String selected, String event, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;
        String v_value = "";

        result = "  <SELECT name=" + name + " " + event + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>=== 전체 ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadmin, gadminnm, isneedgrcode, isneedsubj, isneedcomp,isneeddept, isneedoutcomp from tz_gadmin  ";
            sql += " where gadmin != 'P1'                                                          ";
            sql += " and  isview = 'Y'";
            sql += " order by gadmin asc                                                           ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GadminData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                data.setIsneedgrcode(ls.getString("isneedgrcode"));
                data.setIsneedsubj(ls.getString("isneedsubj"));
                data.setIsneedcomp(ls.getString("isneedcomp"));
                data.setIsneeddept(ls.getString("isneeddept"));
                data.setIsneedoutcomp(ls.getString("isneedoutcomp"));
                v_value = data.getGadmin() + "," + data.getIsneedgrcode() + "," + data.getIsneedsubj() + "," + data.getIsneedcomp() + "," + data.getIsneeddept() + "," + data.getIsneedoutcomp();
                result += " <option value=\"" + v_value + "\"";
                if (selected.equals(v_value)) {
                    result += " selected ";
                }
                result += ">" + data.getGadminnm() + "</option> \n";
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        result += "  </SELECT> \n";
        return result;
    }

    /**
     * 권한코드 셀렉트박스(강사제외)
     * 
     * @param name,selected,event,allcheck 셀렉트박스명,선택값,이벤트명)
     * @return result 권한코드
     */
    public static String getGadminSelectTop(String name, String gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;
        String v_value = "";
        String v_padmin = "";

        result = "  <SELECT name=" + name + "> \n";

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadmin,";
            sql += "        gadminnm,";
            sql += "        isneedgrcode,";
            sql += "        isneedsubj,";
            sql += "        isneedcomp,";
            sql += "        isneeddept,";
            sql += "        padmin";
            sql += "   from tz_gadmin  ";
            sql += " where gadmin = 'A1'";
            sql += "    or gadmin='A2'";
            sql += "    or gadmin='C1'";
            sql += "    or gadmin='C2'";
            sql += "    or gadmin='F1'";
            sql += "    or gadmin='H1'";
            sql += "    or gadmin='K2'";
            sql += "    or gadmin='P1'";
            sql += " order by gadmin asc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GadminData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                data.setIsneedgrcode(ls.getString("isneedgrcode"));
                data.setIsneedsubj(ls.getString("isneedsubj"));
                data.setIsneedcomp(ls.getString("isneedcomp"));
                data.setIsneeddept(ls.getString("isneeddept"));
                data.setPadmin(ls.getString("padmin"));
                v_value = data.getGadmin() + "," + data.getIsneedgrcode() + "," + data.getIsneedsubj() + "," + data.getIsneedcomp() + "," + data.getIsneeddept();
                v_padmin = data.getPadmin();

                result += " <option value=\"" + v_value + "\"";
                if (gadmin.equals(v_padmin)) {
                    result += " selected ";
                }
                result += ">" + data.getGadminnm() + "</option> \n";
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        result += "  </SELECT> \n";
        return result;
    }

    /**
     * 권한코드 셀렉트박스-권한코드만 조회(강사제외)
     * 
     * @param name,selected 셀렉트박스명,선택값,이벤트명)
     * @return result 권한코드
     */
    public static String getGadminSelectOnly(String name, String gadmin, String event) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;
        // String v_value  = "";
        String v_gadmin = "";

        result = "  <SELECT name=" + name + " " + event + "> \n";
        result += " <option value=userid";
        if (name.equals("") || name.equals("userid")) {
            result += " selected ";
        }
        result += ">운영자메뉴권한</option>\r\n";

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadmin, gadminnm from tz_gadmin  ";
            sql += " where isview = 'Y'";
            sql += " order by gadmin asc                     ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GadminData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                v_gadmin = data.getGadmin();

                result += " <option value=\"" + v_gadmin + "\"";
                if (gadmin.equals(v_gadmin)) {
                    result += " selected ";
                }
                result += ">" + data.getGadminnm() + "</option> \n";
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        result += "  </SELECT> \n";
        return result;
    }

    /**
     * 권한코드 + 필요코드여부
     * 
     * @param gadmin gadmin code
     * @return result 권한코드 + "," + 교육그룹필요여부 + "," + 과정코드필요여부 + "," + 회사코드필요여부 +
     *         "," + 부서코드필요여부
     */
    public static String getGadminIsNeed(String gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;

        result = gadmin;

        try {
            connMgr = new DBConnectionManager();

            sql = " select isneedgrcode, isneedsubj, isneedcomp,isneeddept, isneedoutcomp from tz_gadmin  ";
            sql += "  where gadmin = " + StringManager.makeSQL(gadmin);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new GadminData();

                data.setIsneedgrcode(ls.getString("isneedgrcode"));
                data.setIsneedsubj(ls.getString("isneedsubj"));
                data.setIsneedcomp(ls.getString("isneedcomp"));
                data.setIsneeddept(ls.getString("isneeddept"));
                data.setIsneedoutcomp(ls.getString("isneedoutcomp"));

                result += "," + data.getIsneedgrcode() + "," + data.getIsneedsubj() + "," + data.getIsneedcomp() + "," + data.getIsneeddept() + "," + data.getIsneedoutcomp();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return result;
    }

    /**
     * 권한명
     * 
     * @param gadmin gadmin code
     * @return result 권한명
     */
    public static String getGadminName(String gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        // GadminData data = null;

        result = gadmin;

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadminnm from tz_gadmin ";
            sql += "  where gadmin = " + StringManager.makeSQL(gadmin);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getString("gadminnm");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return result;
    }

    /**
     * 권한코드 셀렉트박스(자기 하위 권한만)
     * 
     * @param name,selected,event,allcheck 셀렉트박스명,선택값,이벤트명, 권한, 전체유무(0 -전체없음, 1
     *        - 전체있음)
     * @return result 권한코드 + "," + 교육그룹필요여부 + "," + 과정코드필요여부 + "," + 회사코드필요여부 +
     *         "," + 부서코드필요여부
     */
    public static String getGadminSelectGrtype(String name, String selected, String event, String gadmin) throws Exception {
        return getGadminSelectGrtype(name, selected, event, gadmin, 1);
    }

    public static String getGadminSelectGrtype(String name, String selected, String event, String gadmin, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;
        String v_value = "";

        result = "  <SELECT name=" + name + " " + event + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>=== 전체 ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadmin, gadminnm, isneedgrcode, isneedsubj, isneedcomp,isneeddept,seq from tz_gadmin  ";
            sql += " where isview = 'Y'";
            if (!StringManager.substring(gadmin, 0, 1).equals("A")) {
                sql += "   and (gadmin = " + StringManager.makeSQL(gadmin);
                sql += "        or substring(gadmin,1,1)  > " + StringManager.makeSQL(StringManager.substring(gadmin, 0, 1)) + ") ";
            }

            sql += " order by seq asc,gadmin asc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GadminData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                data.setIsneedgrcode(ls.getString("isneedgrcode"));
                data.setIsneedsubj(ls.getString("isneedsubj"));
                data.setIsneedcomp(ls.getString("isneedcomp"));
                data.setIsneeddept(ls.getString("isneeddept"));
                v_value = data.getGadmin() + "," + data.getIsneedgrcode() + "," + data.getIsneedsubj() + "," + data.getIsneedcomp() + "," + data.getIsneeddept();
                result += " <option value=\"" + v_value + "\"";
                if (selected.equals(v_value)) {
                    result += " selected ";
                }
                result += ">" + data.getGadminnm() + "</option> \n";
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        result += "  </SELECT> \n";
        return result;
    }

    /**
     * 권한코드 셀렉트박스(강사제외)-(자기 하위 권한만)
     * 
     * @param name,selected,event,allcheck 셀렉트박스명,선택값,이벤트명,권한,전체유무(0 -전체없음, 1 -
     *        전체있음)
     * @return result 권한코드 + "," + 교육주관필요여부 + "," + 과정코드필요여부 + "," + 회사코드필요여부 +
     *         "," + 부서코드필요여부
     */
    public static String getGadminSelectNopGrtype(String name, String selected, String event, String gadmin) throws Exception {
        return getGadminSelectNopGrtype(name, selected, event, gadmin, 1);
    }

    public static String getGadminSelectNopGrtype(String name, String selected, String event, String gadmin, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;
        String v_value = "";

        result = "  <SELECT name=" + name + " " + event + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>=== 전체 ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadmin, gadminnm, isneedgrcode, isneedsubj, isneedcomp,isneeddept, isneedoutcomp from tz_gadmin  ";
            sql += " where gadmin != 'P1'                                                          ";
            sql += " and  isview = 'Y'";
            if (!StringManager.substring(gadmin, 0, 1).equals("A")) {
                sql += "   and (gadmin = " + StringManager.makeSQL(gadmin);
                sql += "        or substring(gadmin,1,1)  > " + StringManager.makeSQL(StringManager.substring(gadmin, 0, 1)) + ") ";
            }
            sql += " order by gadmin asc                                                           ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GadminData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                data.setIsneedgrcode(ls.getString("isneedgrcode"));
                data.setIsneedsubj(ls.getString("isneedsubj"));
                data.setIsneedcomp(ls.getString("isneedcomp"));
                data.setIsneeddept(ls.getString("isneeddept"));
                data.setIsneedoutcomp(ls.getString("isneedoutcomp"));
                v_value = data.getGadmin() + "," + data.getIsneedgrcode() + "," + data.getIsneedsubj() + "," + data.getIsneedcomp() + "," + data.getIsneeddept() + "," + data.getIsneedoutcomp();
                result += " <option value=\"" + v_value + "\"";
                if (selected.equals(v_value)) {
                    result += " selected ";
                }
                result += ">" + data.getGadminnm() + "</option> \n";
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        result += "  </SELECT> \n";
        return result;
    }

}
