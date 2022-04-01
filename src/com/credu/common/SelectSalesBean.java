// **********************************************************
// 1. 제 목: 영업 관련 SELECT BOX
// 2. 프로그램명: SelectSalesBean.java
// 3. 개 요: 영업 관련 SELECT BOX
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 이정한 2003. 4. 26
// 7. 수 정: 이정한 2003. 4. 26
// **********************************************************

package com.credu.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

/**
 * 영업 관련 SELECT BOX Class
 * 
 * @date : 2005. 8
 * @author : j.h. lee
 */
public class SelectSalesBean {

    /**
     * 대분류 SELECT
     * 
     * @param box receive from the form object and session
     * @param isChange onChage 함수 사용유무
     * @param isALL 전체 유무
     * @return result 대분류 SELECT BOX 구성 문자
     */
    public static String getUpperClass(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "대분류 ";
        // boolean isVisible = false;

        try {
            String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); // 1단계 영업분류

            connMgr = new DBConnectionManager();

            sql = "select distinct upperclass, classname";
            sql += " from tz_salescode";
            sql += " where middleclass = '000'";
            sql += " and lowerclass = '000'";
            sql += " order by upperclass";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            result += getSelectTag(ls, isChange, isALL, "s_upperclass", ss_upperclass);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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
     * 중분류 SELECT
     * 
     * @param box receive from the form object and session
     * @param isChange onChage 함수 사용유무
     * @param isALL 전체 유무
     * @return result 중분류 SELECT BOX 구성 문자
     */
    public static String getMiddleClass(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "중분류 ";
        // boolean isVisible = false;

        try {
            String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); // 1단계 영업분류
            String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); // 2단계 영업분류
            // System.out.println("ss_middleclass" + ss_middleclass);
            connMgr = new DBConnectionManager();

            sql = "select distinct middleclass, classname";
            sql += " from tz_salescode";
            sql += " where upperclass = " + SQLString.Format(ss_upperclass);
            sql += " and middleclass != '000'";
            sql += " and lowerclass = '000'";
            sql += " order by middleclass";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            result += getSelectTag(ls, isChange, isALL, "s_middleclass", ss_middleclass);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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
     * 소분류 SELECT
     * 
     * @param box receive from the form object and session
     * @param isChange onChage 함수 사용유무
     * @param isALL 전체 유무
     * @return result 소분류 SELECT BOX 구성 문자
     */
    public static String getLowerClass(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "소분류 ";
        // boolean isVisible = false;

        try {
            String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); // 1단계 영업분류
            String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); // 2단계 영업분류
            String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL"); // 3단계 영업분류

            connMgr = new DBConnectionManager();

            sql = "select distinct lowerclass, classname";
            sql += " from tz_salescode";
            sql += " where upperclass = " + SQLString.Format(ss_upperclass);
            sql += " and middleclass = " + SQLString.Format(ss_middleclass);
            sql += " and lowerclass != '000'";
            sql += " order by lowerclass";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            result += getSelectTag(ls, isChange, isALL, "s_lowerclass", ss_lowerclass);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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
     * 회사 SELECT
     * 
     * @param box receive from the form object and session
     * @param isChange onChage 함수 사용유무
     * @param isALL 전체 유무
     * @return result 회사 SELECT BOX 구성 문자
     */
    public static String getComp(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "회사 ";
        // boolean isVisible = false;

        try {
            // String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); // 1단계 영업분류
            // String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); // 2단계 영업분류
            // String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL"); // 3단계 영업분류
            String ss_comp = box.getStringDefault("s_comp", "ALL"); // 회사

            connMgr = new DBConnectionManager();

            sql = " select distinct a.comp, b.compnm   ";
            sql += "  from tz_salesproject a, tz_comp b ";
            sql += " where a.comp = b.comp              ";
            sql += "   and b.comptype ='2'              ";
            sql += " order by a.comp                    ";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            result += getSelectTag(ls, isChange, isALL, "s_comp", ss_comp);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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
     * 영업담당 SELECT
     * 
     * @param box receive from the form object and session
     * @param isChange onChage 함수 사용유무
     * @param isALL 전체 유무
     * @return result 영업담당 SELECT BOX 구성 문자
     */
    public static String getSuserid(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "영업담당 ";
        // boolean isVisible = false;

        try {
            // String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); // 1단계 영업분류
            // String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); // 2단계 영업분류
            // String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL"); // 3단계 영업분류
            String ss_comp = box.getStringDefault("s_comp", "ALL"); // 회사
            String ss_suserid = box.getStringDefault("s_suserid", "ALL"); // 영업담당

            connMgr = new DBConnectionManager();

            sql = " select distinct a.suserid, b.name    ";
            sql += "  from tz_salesproject a, tz_member b ";
            sql += " where a.suserid = b.userid           ";
            if (!ss_comp.equals("ALL"))
                sql += " and a.comp = " + SQLString.Format(ss_comp);
            sql += " order by a.suserid                   ";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            result += getSelectTag(ls, isChange, isALL, "s_suserid", ss_suserid);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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
     * 프로젝트 SELECT
     * 
     * @param box receive from the form object and session
     * @param isChange onChage 함수 사용유무
     * @param isALL 전체 유무
     * @return result 프로젝트 SELECT BOX 구성 문자
     */
    public static String getProject(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "프로젝트 ";
        // boolean isVisible = false;

        try {
            String ss_project = box.getStringDefault("s_project", "ALL"); // 프로젝트

            connMgr = new DBConnectionManager();

            sql = " select distinct project, projectnm   ";
            sql += "  from VZ_SALESPROJECT                ";
            sql += " order by projectnm                   ";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            result += getSelectTag(ls, isChange, isALL, "s_project", ss_project);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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
     * 년도 SELECT
     * 
     * @param box receive from the form object and session
     * @param isChange onChage 함수 사용유무
     * @param isALL 전체 유무
     * @return result 년도 SELECT BOX 구성 문자
     */
    public static String getYear(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "년도 ";
        // boolean isVisible = false;

        try {
            String ss_project_year = box.getStringDefault("s_project_year", FormatDate.getDate("yyyy")); // 년도

            connMgr = new DBConnectionManager();

            sql = " select distinct project_year   ";
            sql += "  from TZ_SALESPLAN             ";
            sql += " union             ";
            sql = " select distinct project_year   ";
            sql += "  from TZ_SALESACTUAL           ";
            sql += " order by project_year desc     ";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            result += getSelectTag(ls, isChange, isALL, "s_project_year", ss_project_year);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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
     * SELECT BOX 문자 구성
     * 
     * @param box receive from the form object and session
     * @param isChange onChage 함수 사용유무
     * @param isALL 전체 유무
     * @param selname SELECT BOX 이름
     * @param optionselected 선택값
     * @return result SELECT BOX 문자
     */
    public static String getSelectTag(ListSet ls, boolean isChange, boolean isALL, String selname, String optionselected) throws Exception {
        StringBuffer sb = null;
        // System.out.println("isChange" + isChange);System.out.println("isALL" + isALL);System.out.println("selname" +
        // selname);System.out.println("optionselected" + optionselected);
        try {
            sb = new StringBuffer();

            sb.append("<select name = \"" + selname + "\"");
            if (isChange)
                sb.append(" onChange = \"javascript:whenSelection('change')\"");
            sb.append(" >\r\n");
            if (isALL) {
                sb.append("<option value = \"ALL\">ALL</option>\r\n");
            } else if (isChange) {
                if (selname.indexOf("year") == -1)
                    sb.append("<option value = \"----\">== 선택 ==</option>\r\n");
            }

            while (ls.next()) {
                ResultSetMetaData meta = ls.getMetaData();
                int columnCount = meta.getColumnCount();

                sb.append("<option value = \"" + ls.getString(1) + "\"");

                if (optionselected.equals(ls.getString(1)))
                    sb.append(" selected");

                sb.append(">" + ls.getString(columnCount) + "</option>\r\n");
            }
            sb.append("</select>\r\n");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return sb.toString();
    }
}
