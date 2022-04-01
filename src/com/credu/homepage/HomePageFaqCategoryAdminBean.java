//**********************************************************
//  1. 제      목: HomePage FAQ 카테고리 관리
//  2. 프로그램명 : HomePageFaqCategoryAdminBean.java
//  3. 개      요: HomePage FAQ 카테고리 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 6. 27
//  7. 수      정: 이나연 05.11.16 _ connMgr.setOracleCLOB 수정
//**********************************************************

package com.credu.homepage;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.system.CodeConfigBean;
import com.dunet.common.util.StringUtil;
import com.namo.SmeNamoMime;

/**
 * FAQ 카테고리 관리(ADMIN)
 * 
 * @date : 2003. 7
 * @author : s.j Jung
 */
public class HomePageFaqCategoryAdminBean {

    public HomePageFaqCategoryAdminBean() {
    }

    /**
     * FAQ 카테고리화면 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 대분류 코드 리스트
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectListHomePageFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_faqcategorynm = box.getString("p_faqcategorynm2");
        String s_userid = box.getSession("userid");
        String v_grcode = box.getStringDefault("p_grcode", "N000001");
        String v_order = box.getString("p_order");
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            if (v_grcode != null) {

                sql.append(" select faqcategory, faqcategorynm, luserid, ldate from TZ_FAQ_CATEGORY   \n ");
                sql.append(" where faqgubun = '5'  \n ");
                if (!v_faqcategorynm.equals("")) { //    검색어가 있으면
                    sql.append(" and faqcategorynm like " + SQLString.Format("%" + v_faqcategorynm + "%") + " \n ");
                }
                if (!"ALL".equals(v_grcode)) {
                    sql.append(" and grcode = " + StringManager.makeSQL(v_grcode) + " \n ");
                } else {
                    sql.append(" and luserid = " + StringManager.makeSQL(s_userid) + " \n ");
                }

                if (v_order.equals("faqcategory"))
                    v_order = "faqcategory";
                if (v_order.equals("faqcategorynm"))
                    v_order = "faqcategorynm";
                if (v_order.equals("")) {
                    sql.append(" order by faqcategory asc  ");
                } else {
                    sql.append("order by " + v_order + v_orderType);
                }

                ls = connMgr.executeQuery(sql.toString());

                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
        return list;
    }

    /**
     * 카테고리 코드를 구함 - VLC
     */
    public DataBox HomePageFaqCategorySetting(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        String sql = "";

        String v_faqcategory = "";

        try {
            connMgr = new DBConnectionManager();

            //----------------------카테고리 코드를 구함 ----------------------------
            sql = "select NVL(max(faqcategory),'0000') as faqcategory from TZ_FAQ_CATEGORY ";

            ls = connMgr.executeQuery(sql);
            ls.next();
            v_faqcategory = ls.getString("faqcategory");
            if (v_faqcategory == "") {
                v_faqcategory = "0000";
            }
            int v_faqcategory_int = Integer.parseInt(v_faqcategory); //카테고리코드를 int형으로 변환

            v_faqcategory_int = v_faqcategory_int + 1; //변환된 카테고리코드에 + 1

            v_faqcategory = "" + v_faqcategory_int; //카테고리코드를 다시 String형으로 변환

            v_faqcategory = CodeConfigBean.addZero(StringManager.toInt(v_faqcategory), 4); // '0'을 붙여주는 메소드 (4자리)

            //------------------------------------------------------------------------------------
            dbox = ls.getDataBox();

            dbox.put("d_faqcategory", v_faqcategory);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return dbox;
    }

    /**
     * HomePage FAQ 카테고리화면 상세보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList 조회한 상세정보
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public DataBox selectHomePageFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "", sql1 = "";
        DataBox dbox = null;

        String v_faqcategory = box.getString("p_faqcategory");

        try {
            connMgr = new DBConnectionManager();

            //			 ---------------------------- 교육그룹 코드 구하기 ----------------------------------------------------
            sql1 = "select b.grcode, b.grcodenm from TZ_FAQ_CATEGORY a, tz_grcode b " + "where a.grcode = b.grcode and  a.faqcategory = " + StringManager.makeSQL(v_faqcategory);
            ls = connMgr.executeQuery(sql1);
            ls.next();

            String v_grcode = ls.getString("grcode");
            String v_grcodenm = ls.getString("grcodenm");

            box.put("p_grcode", v_grcode);
            box.put("p_grcodenm", v_grcodenm);

            // -----------------------------------------------------------------------------------------------------

            sql = " select faqcategory, faqcategorynm, luserid, ldate  from TZ_FAQ_CATEGORY  ";
            sql += "  where faqcategory  = " + SQLString.Format(v_faqcategory);

            ls = connMgr.executeQuery(sql);

            ls.next();

            dbox = ls.getDataBox();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return dbox;
    }

    /**
     * FAQ 카테고리 등록할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertHomePageFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        // String sql = "";
        String sql1 = "";
        int isOk = 0;

        String v_faqcategory = box.getString("p_faqcategory");
        String v_faqcategorynm = box.getString("p_faqcategorynm");
        String s_userid = box.getSession("userid");
        String v_grcode = box.getString("p_grcode");

        try {
            connMgr = new DBConnectionManager();

            sql1 = "insert into TZ_FAQ_CATEGORY(faqcategory, faqcategorynm, luserid, ldate, faqgubun, grcode)           ";
            sql1 += "            values (?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), '5', ?)  ";

            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setString(1, v_faqcategory);
            pstmt.setString(2, v_faqcategorynm);
            pstmt.setString(3, s_userid);
            pstmt.setString(4, v_grcode);

            isOk = pstmt.executeUpdate();
            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
        return isOk;
    }

    /**
     * FAQ 카테고리 수정하여 저장할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int updateHomePageFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String s_userid = box.getSession("userid");

        String v_faqcategory = box.getString("p_faqcategory");
        String v_faqcategorynm = box.getString("p_faqcategorynm");
        String v_grcode = box.getString("p_grcode");

        try {
            connMgr = new DBConnectionManager();

            sql = " update TZ_FAQ_CATEGORY set faqcategorynm = ? , luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS'), grcode =? ";
            sql += "  where faqcategory = ?                                                                                    ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_faqcategorynm);
            pstmt.setString(2, s_userid);
            pstmt.setString(3, v_grcode);
            pstmt.setString(4, v_faqcategory);

            isOk = pstmt.executeUpdate();
            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return isOk;
    }

    /**
     * FAQ 카테고리 삭제할때 - 하위 FAQ 삭제
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteHomePageFaqCategory(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";

        int isOk1 = 0;
        // int isOk2 = 0;

        String v_faqcategory = box.getString("p_faqcategory");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql1 = " delete from TZ_FAQ_CATEGORY  ";
            sql1 += "   where faqcategory = ?             ";

            sql2 = " delete from TZ_FAQ ";
            sql2 += "   where faqcategory = ?   ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setString(1, v_faqcategory);
            isOk1 = pstmt1.executeUpdate();

            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setString(1, v_faqcategory);
            pstmt2.executeUpdate();

            if (isOk1 > 0)
                connMgr.commit(); //하위 분류의 로우가 없을경우 isOk2 가 0이므로 isOk2 >0 조건 제외
            else
                connMgr.rollback();
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1 + "\r\n" + sql2);
            throw new Exception("sql = " + sql1 + "\r\n" + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
                }
            ;
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        //        return isOk1*isOk2;
        return isOk1;
    }

    /**
     * FAQ 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectListHomePageFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        String v_faqcategory = box.getString("p_faqcategory");
        String v_order = box.getString("p_order");
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        try {
            connMgr = new DBConnectionManager();

            // ---------------------------- 교육그룹 코드 구하기 ----------------------------------------------------
            /*
             * sql1 =
             * " select b.grcode, b.grcodenm from TZ_FAQ_CATEGORY a, tz_grcode b "
             * +
             * " where a.grcode = b.grcode and  a.faqcategory = "+StringManager
             * .makeSQL(v_faqcategory); ls = connMgr.executeQuery(sql1);
             * ls.next(); if(ls.next()) { v_grcode = ls.getString("grcode");
             * v_grcodenm = ls.getString("grcodenm");
             * 
             * box.put("p_grcode", v_grcode); box.put("p_grcodenm", v_grcodenm);
             * }
             */

            // -----------------------------------------------------------------------------------------------------
            list = new ArrayList();

            sql = " select fnum, title, contents, indate, luserid, ldate  ";
            sql += "   from TZ_FAQ                                         ";
            sql += "  where faqcategory   = " + SQLString.Format(v_faqcategory);

            if (!v_searchtext.equals("")) { //    검색어가 있으면

                if (v_search.equals("title")) { //    제목으로 검색할때
                    sql += " and lower(title) like lower(" + SQLString.Format("%" + v_searchtext + "%") + ")";
                } else if (v_search.equals("contents")) { //    내용으로 검색할때
                    sql += " and contents like lower(" + SQLString.Format("%" + v_searchtext + "%") + ")";
                }
            }
            if (v_order.equals("fnum"))
                v_order = "fnum";
            if (v_order.equals("title"))
                v_order = "title";
            if ((v_order.equals("name")) || (v_order.equals(""))) {
                sql += " order by fnum desc ";
            } else {
                sql += " order by " + v_order + v_orderType;
            }

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                dbox = ls.getDataBox();

                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return list;
    }

    /**
     * faq 코드를 구함 - VLC
     */
    public DataBox HomePageFaqSetting(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        String sql = "";

        int v_fnum = 0;
        String v_faqcategory = box.getString("p_faqcategory");

        try {
            connMgr = new DBConnectionManager();

            //----------------------카테고리 코드를 구함 ----------------------------
            sql = "select NVL(max(fnum),'0000') as fnum from TZ_FAQ ";

            sql += " where faqcategory  = " + SQLString.Format(v_faqcategory);

            ls = connMgr.executeQuery(sql);
            ls.next();

            v_fnum = ls.getInt("fnum") + 1;

            dbox = ls.getDataBox();

            dbox.put("d_fnum", "" + v_fnum);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return dbox;
    }

    /**
     * FAQ 등록할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertHomePageFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        int isOk = 0;

        String v_faqcategory = box.getString("p_faqcategory");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        int v_fnum = box.getInt("p_fnum");
        String s_userid = box.getSession("userid");

        /*********************************************************************************************/
        // 나모에디터 본문 처리  (Mime Document Parsing 및 이미지 업로드)
        ConfigSet conf = new ConfigSet();
        SmeNamoMime namo = new SmeNamoMime(v_contents); // 객체생성
        boolean result = namo.parse(); // 실제 파싱 수행
        if (!result) { // 파싱 실패시
            System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
            return 0;
        }
        if (namo.isMultipart()) { // 문서가 멀티파트인지 판단
            String v_server = conf.getProperty("autoever.url.value");
            String fPath = conf.getProperty("dir.namo"); // 파일 저장 경로 지정
            String refUrl = conf.getProperty("url.namo");
            ; // 웹에서 저장된 파일을 접근하기 위한 경로
            String prefix = "faq" + v_faqcategory; // 파일명 접두어
            result = namo.saveFile(fPath, v_server + refUrl, prefix); // 실제 파일 저장
        }
        if (!result) { // 파일저장 실패시
            System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
            return 0;
        }
        v_contents = namo.getContent(); // 최종 컨텐트 얻기
        /*********************************************************************************************/

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sql1 = "insert into TZ_FAQ(faqcategory, fnum, title, contents, indate, luserid, ldate) ";
            //         sql1 += "values (?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))  ";
            sql1 += "values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))  ";

            int index = 1;
            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setString(index++, v_faqcategory);
            pstmt.setInt(index++, v_fnum);
            pstmt.setString(index++, v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            //		   pstmt.setString(index++, v_contents);
            pstmt.setString(index++, s_userid);

            isOk = pstmt.executeUpdate();
            /* 05.11.15 이나연 */
            //           sql2 = "select contents from TZ_FAQ";
            //           sql2 += "  where faqcategory    = " + StringManager.makeSQL(v_faqcategory);
            //           sql2 += "    and fnum           = " + v_fnum;

            //           connMgr.setOracleCLOB(sql2, v_contents);       //      (기타 서버 경우)

            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
                }
            ;
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * HomePage FAQ 화면 상세보기
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public DataBox selectHomePageFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_faqcategory = box.getString("p_faqcategory");
        // String v_faqcategorynm = box.getString("p_faqcategorynm");
        int v_fnum = box.getInt("p_fnum");

        try {
            connMgr = new DBConnectionManager();

            sql = " select title, contents  from TZ_FAQ  ";
            sql += "  where faqcategory  = " + SQLString.Format(v_faqcategory);
            sql += "  and fnum  = " + v_fnum;

            ls = connMgr.executeQuery(sql);

            ls.next();

            dbox = ls.getDataBox();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return dbox;
    }

    /**
     * FAQ 수정하여 저장할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int updateHomePageFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String s_userid = box.getSession("userid");

        String v_faqcategory = box.getString("p_faqcategory");
        int v_fnum = box.getInt("p_fnum");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_content"));

        /*********************************************************************************************/
        // 나모에디터 본문 처리  (Mime Document Parsing 및 이미지 업로드)
        ConfigSet conf = new ConfigSet();
        SmeNamoMime namo = new SmeNamoMime(v_contents); // 객체생성
        boolean result = namo.parse(); // 실제 파싱 수행
        if (!result) { // 파싱 실패시
            System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
            return 0;
        }
        if (namo.isMultipart()) { // 문서가 멀티파트인지 판단
            String v_server = conf.getProperty("autoever.url.value");
            String fPath = conf.getProperty("dir.namo"); // 파일 저장 경로 지정
            String refUrl = conf.getProperty("url.namo");
            ; // 웹에서 저장된 파일을 접근하기 위한 경로
            String prefix = "faq" + v_faqcategory; // 파일명 접두어
            result = namo.saveFile(fPath, v_server + refUrl, prefix); // 실제 파일 저장
        }
        if (!result) { // 파일저장 실패시
            System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
            return 0;
        }
        v_contents = namo.getContent(); // 최종 컨텐트 얻기
        /*********************************************************************************************/

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //          sql  = " update TZ_FAQ set title = ? , contents = empty_clob(), luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql = " update TZ_FAQ set title = ? , contents = ?, luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where faqcategory = ? and fnum = ? ";

            int index = 1;
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(index++, v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_faqcategory);
            pstmt.setInt(index++, v_fnum);

            isOk = pstmt.executeUpdate();
            /* 05.11.16 이나연 */
            //            sql = "select contents from TZ_FAQ";
            //            sql += "  where faqcategory = "+ v_faqcategory +" and fnum = "+ v_fnum  ;

            //            connMgr.setOracleCLOB(sql, v_contents);       //      (ORACLE 9i 서버)

            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return isOk;
    }

    /**
     * FAQ 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteHomePageFaq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_faqcategory = box.getString("p_faqcategory");
        int v_fnum = box.getInt("p_fnum");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = " delete from TZ_FAQ ";
            sql += "   where faqcategory = ? and fnum = ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_faqcategory);
            pstmt.setInt(2, v_fnum);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        //        return isOk1*isOk2;
        return isOk;
    }

    /************************************* 공통함수들 ***********************************************************/

    /**
     * FAQ 카테고리명
     * 
     * @param faqcategory 카테고리 코드
     * @return result 카테고리 코드명
     * @throws Exception
     */
    public static String getFaqCategoryName(String faqcategory) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        FaqCategoryData data = null;

        try {
            connMgr = new DBConnectionManager();

            sql = " select faqcategorynm from TZ_FAQ_CATEGORY        ";
            sql += "  where faqcategory = " + SQLString.Format(faqcategory);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new FaqCategoryData();
                data.setFaqCategorynm(ls.getString("faqcategorynm"));
                result = data.getFaqCategorynm();
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
     * FAQ 카테고리 셀렉트박스
     * 
     * @param name 셀렉트박스명
     * @param selected 선택값
     * @param event 이벤트명
     * @param allcheck 전체유무
     * @return
     * @throws Exception
     */
    public static String getFaqCategorySelecct(String name, String selected, String event, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        FaqCategoryData data = null;

        result = "  <SELECT name= " + "\"" + name + "\"" + " " + event + " > \n";
        if (allcheck == 1) {
            result += "    <option value=''>== 선택하세요 ==</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select faqcategory,faqcategorynm from TZ_FAQ_CATEGORY where faqgubun = '5' ";
            sql += " order by faqcategory asc                        ";

            ls = connMgr.executeQuery(sql);

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
        return result;

    }

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
    public static String getFaqCategorySelecctNew(String v_grcode, String name, String selected, String event, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        FaqCategoryData data = null;

        result = "  <SELECT name= " + "\"" + name + "\"" + " " + event + " > \n";
        if (allcheck == 1) {
            result += "    <option value=''>== 선택하세요 ==</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " Select faqcategory,faqcategorynm From TZ_FAQ_CATEGORY where faqgubun = '5' ";
            if (!v_grcode.equals("")) {
                sql += " and grcode = '" + v_grcode + "' ";
            }
            sql += " order by faqcategory asc ";
            //System.out.println("sql = " + sql);
            ls = connMgr.executeQuery(sql);

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
        return result;

    }

}
