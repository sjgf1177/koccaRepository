//**********************************************************
//1. 제      목: Faq 관리
//2. 프로그램명 : FaqAdminBean.java
//3. 개      요: Faq 관리
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 정상진 2003. 7. 13
//7. 수      정: 이나연 _ 05.11.17 _ upper 수정
//**********************************************************

package com.credu.homepage;

//import javax.servlet.http.*;
//import java.io.*;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * Faq 관리(ADMIN)
 * 
 * @date : 2003. 7
 * @author : s.j Jung
 */

public class HomePageFAQBean {
    private final int gubun = 5;

    /**
     * Faq화면 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList Faq 리스트
     * 
     *         public ArrayList selectListFaq(RequestBox box) throws Exception {
     *         DBConnectionManager connMgr = null; ListSet ls = null; ArrayList
     *         list = null; String sql = ""; DataBox dbox = null;
     * 
     *         String v_faqcategory = box.getString("p_faqcategory"); String
     *         v_searchtxt = box.getString("p_searchtxt");
     * 
     *         //System.out.println("v_faqcategory =-===> " + v_faqcategory);
     * 
     *         String v_searchtext = box.getString("p_searchtext"); String
     *         v_search = box.getString("p_search"); int v_pageno =
     *         box.getInt("p_pageno"); try { connMgr = new
     *         DBConnectionManager();
     * 
     *         list = new ArrayList();
     * 
     *         sql =
     *         " select a.fnum, a.title, a.contents, a.indate, a.luserid, a.ldate  "
     *         ; sql +=
     *         "   from TZ_FAQ  a,tz_faq_category b                                       "
     *         ; sql += "	where a.faqcategory = b.faqcategory"; sql +=
     *         "  and  b.faqgubun   = " + gubun;
     * 
     *         if (v_faqcategory.equals("select") || v_faqcategory.equals("")){
     * 
     *         }else{ sql += " and a.faqcategory   = " +
     *         SQLString.Format(v_faqcategory); }
     * 
     *         if(!v_searchtxt.equals("")){ sql += " and a.title   like '%" +
     *         v_searchtxt +"%'"; }
     * 
     * 
     *         sql += " order by a.faqcategory desc"; System.out.println(
     *         "faq value================================================="
     *         +sql); ls = connMgr.executeQuery(sql); ls.setPageSize(row); //
     *         페이지당 row 갯수를 세팅한다 ls.setCurrentPage(v_pageno); // 현재페이지번호를 세팅한다.
     *         int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다 int
     *         total_row_count = ls.getTotalCount(); // 전체 row 수를 반환한다
     * 
     *         while (ls.next()) { //System.out.println("while돌면서 data에 담기");
     *         dbox = ls.getDataBox();
     * 
     *         dbox.put("d_totalpage", new Integer(total_page_count));
     *         dbox.put("d_rowcount", new Integer(row));
     * 
     *         list.add(dbox); // System.out.println("list에 add하기");
     * 
     *         } } catch (Exception ex) { ErrorManager.getErrorStackTrace(ex,
     *         box, sql); throw new Exception("sql = " + sql + "\r\n" +
     *         ex.getMessage()); } finally { if(ls != null) { try { ls.close();
     *         }catch (Exception e) {} } if(connMgr != null) { try {
     *         connMgr.freeConnection(); }catch (Exception e10) {} } } return
     *         list; }
     */
    /**
     * FAQ 카테고리 셀렉트박스
     * 
     * @param name 셀렉트박스명
     * @param selected 선택값
     * @param event 이벤트명
     * @param allcheck 전체유무
     * @return
     * @throws Exception
     */
    public static String homepageGetFaqCategorySelecct(String name, String selected, String event, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        FaqCategoryData data = null;
        //System.out.println("name ==>"  + name);
        //System.out.println("selected ==>" + selected);
        //System.out.println("event ==>" + event);
        //System.out.println("allcheck ==>" + allcheck);

        result = "  <SELECT name= " + "\"" + name + "\"" + " " + event + " > \n";
        if (allcheck == 1) {
            //System.out.println(" if (allcheck == 1) {실행하다");
            result += "    <option value='select'>ALL</option> \n";
        }
        try {
            connMgr = new DBConnectionManager();

            sql = " select faqcategory,faqcategorynm from TZ_FAQ_CATEGORY where faqgubun = '5' ";
            sql += " order by faqcategory asc                        ";

            ls = connMgr.executeQuery(sql);
            //System.out.println(sql);

            while (ls.next()) {

                data = new FaqCategoryData();
                data.setFaqCategory(ls.getString("faqcategory"));
                data.setFaqCategorynm(ls.getString("faqcategorynm"));

                result += "    <option value=" + "\"" + data.getFaqCategory() + "\"";
                if (selected.equals(data.getFaqCategory())) {
                    result += " selected ";

                }

                result += ">" + data.getFaqCategorynm() + "</option> \n";

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
        //System.out.println("result =============>" + result);
        return result;

    }

    /**
     * Faq화면 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList Faq 리스트
     */
    public ArrayList<DataBox> selectListFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String head_sql = "";
        String body_sql = "";
        String order_sql = "";
        DataBox dbox = null;

        String v_faqcategory = box.getString("faqcategory");
        String v_searchtxt = box.getString("p_searchtxt");
        String v_grcode = box.getString("p_grcode");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql = " select a.fnum, a.title, a.contents, a.indate, a.luserid, a.ldate ";
            body_sql = " from TZ_FAQ  a,tz_faq_category b ";
            body_sql += "	where a.faqcategory = b.faqcategory ";
            body_sql += " and  b.grcode   = " + StringManager.makeSQL(v_grcode);
            body_sql += " and  b.faqgubun   = " + gubun;

            if (v_faqcategory.equals("ALL") || v_faqcategory.equals("")) {

            } else {
                body_sql += " and a.faqcategory   = " + v_faqcategory;
            }

            if (!v_searchtxt.equals("")) {
                body_sql += " and ( upper(a.title)   like upper('%'||" + StringManager.makeSQL(v_searchtxt) + "||'%') or a.contents  like upper('%'||" + StringManager.makeSQL(v_searchtxt) + "||'%') " + "or a.contents  like lower('%" + v_searchtxt
                        + "%') ) ";
            }

            order_sql += " order by a.faqcategory desc ";

            ls = connMgr.executeQuery(head_sql + body_sql + order_sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, head_sql + body_sql + order_sql);
            throw new Exception("sql = " + head_sql + body_sql + order_sql + "\r\n" + ex.getMessage());
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

        return list;
    }

    /**
     * Faq화면 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList Faq 리스트
     **/
    public ArrayList<DataBox> selectListBestFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList<DataBox> list = null;

        StringBuffer head_sql = new StringBuffer();
        StringBuffer body_sql = new StringBuffer();
        StringBuffer order_sql = new StringBuffer();

        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        if (v_grcode.equals("")) {
            v_grcode = "N000002";
        }

        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql.append(" SELECT B.FAQCATEGORY, A.FNUM, A.TITLE, A.CONTENTS, A.INDATE, A.LUSERID, A.LDATE \n");

            body_sql.append(" FROM TZ_FAQ  A,TZ_FAQ_CATEGORY B \n");
            body_sql.append("	WHERE A.FAQCATEGORY = B.FAQCATEGORY \n");
            body_sql.append(" AND  B.GRCODE   = " + StringManager.makeSQL(v_grcode) + " \n");
            body_sql.append(" AND  B.FAQGUBUN   = " + gubun + " \n");
            body_sql.append(" AND ROWNUM < 6 \n");

            order_sql.append(" ORDER BY A.FAQCATEGORY DESC ");

            ls = connMgr.executeQuery(head_sql.toString() + body_sql.toString() + order_sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, head_sql.toString() + body_sql.toString() + order_sql.toString());
            throw new Exception("sql = " + head_sql + body_sql + order_sql + "\r\n" + ex.getMessage());
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

        return list;
    }

    /**
     * FAQ 카테고리 테이블 셀렉트
     * 
     * @param name 셀렉트박스명
     * @param selected 선택값
     * @param event 이벤트명
     * @param allcheck 전체유무
     * @return
     * @throws Exception
     */
    public static String koccaFaqCategoryTable(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ListSet ls3 = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String result = "";
        int count = 0;
        FaqCategoryData data = null;
        int i = 2;

        String v_grcode = box.getString("p_grcode");

        try {
            connMgr = new DBConnectionManager();

            sql3 = "select count(*) categoryTotCnt    from TZ_FAQ a , TZ_FAQ_CATEGORY b ";
            sql3 += "  where a.FAQCATEGORY = b.FAQCATEGORY and b.grcode ='" + v_grcode + "' and faqgubun = '5'";
            ls3 = connMgr.executeQuery(sql3);
            ls3.next();
            int categoryTotCnt = ls3.getInt(1);
            ls3.close();

            result = "<table width='649' cellspacing='0' cellpadding='0'>" + "\n  <!--tr><td><table width='216' border='0' cellspacing='0' cellpadding='0'-->" + "\n 	  <tr>"
                    + "\n 		<td width='13' height='23'><div align='right'><img src='/images/user/kocca/customer/bl_cata.gif' width='6' height='6'></div></td>" + "\n    <td width='203' id='Out" + i
                    + "' valign='middle' class='tbl_gleft01'><a href=\"javascript:changeCategory('ALL')\">&nbsp;전체</a>(" + categoryTotCnt + ")</td> ";

            sql1 = "select count(*) as cnt from TZ_FAQ_CATEGORY where faqgubun = '5' and grcode ='" + v_grcode + "'";
            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                count = ls1.getInt("cnt");
            }
            ls1.close();

            sql = " select faqcategory,faqcategorynm from TZ_FAQ_CATEGORY where faqgubun = '5' and grcode ='" + v_grcode + "' ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new FaqCategoryData();
                data.setFaqCategory(ls.getString("faqcategory"));
                data.setFaqCategorynm(ls.getString("faqcategorynm"));

                sql2 = "select count(*) categoryCnt from TZ_FAQ a, TZ_FAQ_CATEGORY b where a.FAQCATEGORY = b.FAQCATEGORY " + "  and b.faqcategory = " + SQLString.Format(data.getFaqCategory()) + "  and b.grcode ='" + v_grcode + "' and b.faqgubun = '5'  ";
                ls2 = connMgr.executeQuery(sql2);
                ls2.next();
                int categoryCnt = ls2.getInt(1);
                ls2.close();

                if (i != 1 && i % 3 == 1) {
                    result += "\n <tr> ";
                }
                result += "\n     <td width='13' height='23'><div align='right'><img src='/images/user/kocca/customer/bl_cata.gif' width='6' height='6'></div></td> " + "\n       <td width='203' id='Out" + i
                        + "' valign='middle' class='tbl_gleft01'><a href=\"javascript:changeCategory('" + data.getFaqCategory() + "')\">" + data.getFaqCategorynm() + "</a>(" + categoryCnt + ")</td>";

                if (i % 3 == 0 && i != (count + 1)) {
                    result += "\n  </tr> " + "\n   <tr background='/images/user/kocca/customer/line_dot.gif'> " + "\n    <td height='1' colspan='6'></td>" + "\n    </tr>";
                }

                i++;

            }
            int mod_cnt = (count + 1) % 3;

            if (mod_cnt != 0) {
                for (int j = 0; j < (3 - mod_cnt); j++) {
                    result += "\n     <td width='13' height='23'>&nbsp;</td> " + "\n       <td width='203'>&nbsp;</td>";
                }
            }

            result += "</tr>";

        }

        catch (Exception ex) {
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

        result += "\n   </table>";

        return result;

    }

    /**
     * FAQ 카테고리 테이블 셀렉트 (game)
     * 
     * @param name 셀렉트박스명
     * @param selected 선택값
     * @param event 이벤트명
     * @param allcheck 전체유무
     * @return
     * @throws Exception
     */
    public static String gameFaqCategoryTable(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;

        FaqCategoryData data = null;

        StringBuffer sql = new StringBuffer();

        StringBuffer result = new StringBuffer();
        StringBuffer sub_result = new StringBuffer();

        int categoryTotCnt = 0;

        String v_grcode = box.getString("p_grcode");
        String v_faqcategory = box.getString("faqcategory");

        try {

            connMgr = new DBConnectionManager();

            sql.append(" SELECT B.FAQCATEGORY,B.FAQCATEGORYNM,COUNT(A.FNUM) CATEGORYCNT FROM TZ_FAQ A, TZ_FAQ_CATEGORY B \n");
            sql.append("  WHERE  B.FAQGUBUN = '5' AND B.GRCODE = '" + v_grcode + "' \n");
            sql.append("    AND A.FAQCATEGORY = B.FAQCATEGORY \n");
            sql.append("  GROUP BY B.FAQCATEGORY,B.FAQCATEGORYNM ");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                data = new FaqCategoryData();

                data.setFaqCategory(ls.getString("faqcategory"));
                data.setFaqCategorynm(ls.getString("faqcategorynm"));
                data.setCategorycnt(ls.getInt("categorycnt"));

                categoryTotCnt = categoryTotCnt + data.getCategorycnt();

                sub_result.append("         <li ").append(v_faqcategory.equals(data.getFaqCategory()) ? "class=\"on\"" : "").append("><a href=\"javascript:changeCategory('").append(data.getFaqCategory()).append("')\"><strong>").append(
                        data.getFaqCategorynm()).append(" (").append(data.getCategorycnt()).append(")</strong> <img src=\"/images/portal/ico/ico_linquiry_arrow.gif\" alt=\"\" /></a></li> \n");
            }

            result.append(" <div class=\"faq_list_box\">  \n");
            result.append("     <ul>                      \n");
            // ALL
            result.append("         <li ").append(v_faqcategory.equals("ALL") || v_faqcategory.equals("") ? "class=\"on\"" : "").append("><a href=\"javascript:changeCategory('ALL')\">전체 <strong>(").append(categoryTotCnt).append(
                    ")</strong> <img src=\"/images/portal/ico/ico_linquiry_arrow.gif\" alt=\"\" /></a></li> \n ");
            // CATEGORY
            result.append(sub_result.toString());
            result.append(" 	   </ul>  \n");
            result.append(" </div> \n");

            sql.setLength(0);

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

        return result.toString();
    }

    /**
     * FAQ 카테고리 리스트
     * 
     * @param name 셀렉트박스명
     * @param selected 선택값
     * @param event 이벤트명
     * @param allcheck 전체유무
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectFaqCategoryList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();

        String v_grcode = box.getSession("tem_grcode");
        //if(v_grcode.equals("")) v_grcode = "N000002";

        try {
            connMgr = new DBConnectionManager();
            sql.append(" SELECT  B.FAQCATEGORY, B.FAQCATEGORYNM, COUNT(A.FAQCATEGORY) CNT \n ");
            sql.append(" FROM    TZ_FAQ A , TZ_FAQ_CATEGORY B                             \n ");
            sql.append(" WHERE   A.FAQCATEGORY   = B.FAQCATEGORY(+)                       \n ");
            sql.append(" AND     B.GRCODE          = '" + v_grcode + "'                       \n ");
            sql.append(" AND     FAQGUBUN(+)        = '5'                                 \n ");
            sql.append(" GROUP BY B.FAQCATEGORY, B.FAQCATEGORYNM                          \n ");

            ls = connMgr.executeQuery(sql.toString());

            list = new ArrayList<DataBox>();

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            System.out.println(5);
        }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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
        return list;
    }

    /**
     * FAQ 카테고리 테이블 셀렉트 (renewal)
     * 
     * @param name 셀렉트박스명
     * @param selected 선택값
     * @param event 이벤트명
     * @param allcheck 전체유무
     * @return
     * @throws Exception
     */
    public static String renewalFaqCategoryTable(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;

        FaqCategoryData data = null;

        StringBuffer sql = new StringBuffer();

        StringBuffer result = new StringBuffer();
        StringBuffer sub_result = new StringBuffer();

        int categoryTotCnt = 0;

        String v_grcode = box.getString("p_grcode");
        String v_faqcategory = box.getString("faqcategory");

        try {

            connMgr = new DBConnectionManager();

            sql.append(" SELECT B.FAQCATEGORY,B.FAQCATEGORYNM,COUNT(A.FNUM) CATEGORYCNT FROM TZ_FAQ A, TZ_FAQ_CATEGORY B \n");
            sql.append("  WHERE  B.FAQGUBUN = '5' AND B.GRCODE = '" + v_grcode + "' \n");
            sql.append("    AND A.FAQCATEGORY = B.FAQCATEGORY \n");
            sql.append("  GROUP BY B.FAQCATEGORY,B.FAQCATEGORYNM ");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                data = new FaqCategoryData();

                data.setFaqCategory(ls.getString("faqcategory"));
                data.setFaqCategorynm(ls.getString("faqcategorynm"));
                data.setCategorycnt(ls.getInt("categorycnt"));

                categoryTotCnt = categoryTotCnt + data.getCategorycnt();

                sub_result.append("         <td ").append(v_faqcategory.equals(data.getFaqCategory()) ? "class=\"on\"" : "").append("><a href=\"javascript:changeCategory('").append(data.getFaqCategory()).append("')\"><strong>").append(
                        data.getFaqCategorynm()).append(" (").append(data.getCategorycnt()).append(")</strong> <img src=\"/images/portal/ico/ico_linquiry_arrow.gif\" alt=\"\" /></a></td> \n");
            }

            //result.append("     <ul>                      \n");
            // ALL
            result.append("         <td ").append(v_faqcategory.equals("ALL") || v_faqcategory.equals("") ? "class=\"on\"" : "").append(
                    "><a href=\"javascript:changeCategory('ALL')\"><img src=\"/images/portal/homepage_renewal/support/tab_on_01.gif\" alt=\"\" name=\"Image44\" width=\"84\" height=\"39\" border=\"0\" id=\"Image44\" /></a></td> \n ");
            // CATEGORY
            result.append(sub_result.toString());
            //result.append(" 	   </ul>  \n");
            //result.append(" </div> \n");

            sql.setLength(0);

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

        return result.toString();
    }
}
