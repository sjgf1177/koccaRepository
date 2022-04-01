package com.credu.homepage;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * 2014년 홈페이지 개편에 따라 생성된 클래스이다. 홈페이지 메인에 필요한 정보들을 조회 관리한다.
 * 
 * @author kocca
 * 
 */
public class KoccaMainBean {
    /**
     * 홈페이지 메인에 노출될 배너 목록을 조회한다.
     * 
     * @param box receive from the form object and session
     * @return list ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectBannerList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuffer sql = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.homepage.KoccaMainBean selectBannerList (배너 목록 조회) */ \n");
            sql.append("SELECT  SEQ         \n");
            sql.append("    ,   TITLE       \n");
            sql.append("    ,   USE_YN      \n");
            sql.append("    ,   SORT_ORDER  \n");
            sql.append("    ,   SAVE_IMG_NM \n");
            sql.append("    ,   IMG_SIZE    \n");
            sql.append("    ,   URL         \n");
            sql.append("    ,   URL_TARGET  \n");
            sql.append("    ,   EXPLAIN     \n");
            sql.append("    ,   FIXED_FLAG  \n");
            sql.append("  FROM  TZ_BANNER   \n");
            sql.append(" WHERE  USE_YN = 'Y'    \n");
            sql.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN START_DT AND END_DT    \n");
            sql.append(" ORDER BY SORT_ORDER                                \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("selectBannerList sql = " + sql + "\r\n" + ex.getMessage());
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
     * 홈페이지 메인에 노출될 공지사항 목록을 조회한다. 공지사항 목록에는 이벤트 목록도 포함되며, 날짜순으로 조회한다.
     * 
     * @param box receive from the form object and session
     * @return list ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectNoticeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuffer sql = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.homepage.KoccaMainBean selectNoticeList (공지사항 / 이벤트 목록 조회) */ \n");
            sql.append("SELECT  BOARD_TYPE                                                                                                                  \n");
            sql.append("    ,   SEQ                                                                                                                         \n");
            sql.append("    ,   GUBUN AS GUBUN_CODE                                                                                                         \n");
            sql.append("    ,   TITLE                                                                                                                       \n");
            sql.append("    ,   REG_DT                                                                                                                      \n");
            sql.append("    ,   CASE WHEN GAP < 6 THEN 'Y'                                                                                                  \n");
            sql.append("        ELSE 'N' END AS NEW_YN                                                                                                      \n");
            sql.append("    ,   RNK                                                                                                                         \n");
            sql.append("  FROM  (                                                                                                                           \n");
            sql.append("        SELECT  *                                                                                                                   \n");
            sql.append("          FROM  (                                                                                                                   \n");
            sql.append("                SELECT  'NOTICE' AS BOARD_TYPE                                                                                      \n");
            sql.append("                    ,   SEQ                                                                                                         \n");
            sql.append("                    ,   GUBUN                                                                                                       \n");
            sql.append("                    ,   ADTITLE AS TITLE                                                                                            \n");
            sql.append("                    ,   ADDATE AS REG_DT                                                                                            \n");
            sql.append("                    ,   TO_DATE(TO_CHAR(SYSDATE, 'YYYYMMDD'), 'YYYYMMDD') - TO_DATE( SUBSTR(ADDATE, 0, 8), 'YYYYMMDD') AS GAP       \n");
            sql.append("                    ,   RANK() OVER (ORDER BY SEQ DESC) AS RNK                                                                      \n");
            sql.append("                  FROM  TZ_NOTICE                                                                                                   \n");
            sql.append("                 WHERE  TABSEQ = 12                                                                                                 \n");
            sql.append("                   AND  USEYN = 'Y'                                                                                                 \n");
            sql.append("                   AND  GRCODECD LIKE '%N000001%'                                                                                   \n");
            sql.append("                   AND  (POPUP = 'N' OR (POPUP = 'Y' AND USELIST='Y') )                                                             \n");
            sql.append("                )                                                                                                                   \n");
            sql.append("         WHERE  RNK < 6                                                                                                             \n");
            sql.append("        UNION ALL                                                                                                                   \n");
            sql.append("        SELECT  *                                                                                                                   \n");
            sql.append("          FROM  (                                                                                                                   \n");
            sql.append("                SELECT  'EVENT' AS BOARD_TYPE                                                                                       \n");
            sql.append("                    ,   SEQ                                                                                                         \n");
            sql.append("                    ,   CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN STRDATE AND ENDDATE THEN 'O'                             \n");
            sql.append("                        ELSE 'E' END AS GUBUN                                                                                       \n");
            sql.append("                    ,   TITLE                                                                                                       \n");
            sql.append("                    ,   INDATE AS REG_DT                                                                                            \n");
            sql.append("                    ,   TO_DATE(TO_CHAR(SYSDATE, 'YYYYMMDD'), 'YYYYMMDD') - TO_DATE( SUBSTR(INDATE, 0, 8), 'YYYYMMDD') AS GAP       \n");
            sql.append("                    ,   RANK() OVER (ORDER BY SEQ DESC) AS RNK                                                                      \n");
            sql.append("                  FROM  TZ_EVENT                                                                                                    \n");
            sql.append("                 WHERE  USEYN = 'Y'                                                                                                 \n");
            sql.append("                )                                                                                                                   \n");
            sql.append("         WHERE  RNK < 6                                                                                                             \n");
            sql.append("        )                                                                                                                           \n");
            sql.append(" ORDER  BY REG_DT DESC                                                                                                              \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("selectNoticeList sql = " + sql + "\r\n" + ex.getMessage());
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
     * 홈페이지 메인에 노출될 인기검색어 목록을 조회한다.
     * 
     * @param box receive from the form object and session
     * @return list ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectPopularKeywordList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuffer sql = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.homepage.KoccaMainBean selectPopularKeywordList (인기검색어 목록 조회) */ \n");
            sql.append("SELECT  SORT_SEQ            \n");
            sql.append("    ,   KEYWORD             \n");
            sql.append("  FROM  TZ_KEYWORDMANAGER   \n");
            sql.append(" ORDER  BY SORT_SEQ         \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("selectPopularKeywordList sql = " + sql + "\r\n" + ex.getMessage());
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
     * 홈페이지 메인에 노출될 아카데미 이야기 목록을 조회한다.
     * 
     * @param box receive from the form object and session
     * @return list ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectAcademyStoryList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuffer sql = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.homepage.KoccaMainBean selectAcademyStoryList (아카데미 이야기 목록 조회) */ \n");
            sql.append("SELECT  *                                                                  \n");
            sql.append("  FROM  (                                                                  \n");
            sql.append("        SELECT  A.SEQ                                                      \n");
            sql.append("            ,   A.TITLE                                                    \n");
            sql.append("            ,   A.CONT                                                     \n");
            sql.append("            ,   B.FILE_NUM                                                 \n");
            sql.append("            ,   B.IMG_NM                                                   \n");
            sql.append("            ,   B.SAVE_IMG_NM                                              \n");
            sql.append("            ,   RANK() OVER( ORDER BY A.SEQ DESC, A.REG_DT DESC ) AS RNK   \n");
            sql.append("          FROM  TZ_BOARD_TONG A                                            \n");
            sql.append("            ,   TZ_BOARDFILE_TONG B                                        \n");
            sql.append("         WHERE  A.USE_YN = 'Y'                                             \n");
            sql.append("           AND  A.SEQ = B.SEQ(+)                                           \n");
            sql.append("        )                                                                  \n");
            sql.append(" WHERE  RNK < 5                                                            \n");
            sql.append(" ORDER  BY RNK                                                             \n");
            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("selectAcademyStoryList sql = " + sql + "\r\n" + ex.getMessage());
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
     * 메인 화면 카테고리 목록을 조회한다.
     * 
     * @param box
     * @return
     */
    public ArrayList<DataBox> selectMainCategoryList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuffer sql = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.homepage.KoccaMainBean selectMainCategoryList (메인 카테고리 목록 조회) */ \n");
            sql.append("SELECT  A.CATEGORY_SEQ                                                  \n");
            sql.append("    ,   A.CATEGORY_NM                                                   \n");
            sql.append("    ,   A.CATEGORY_TYPE                                                 \n");
            sql.append("    ,   A.LAYOUT_TYPE                                                   \n");
            sql.append("    ,   A.SORT_ORDER                                                    \n");
            sql.append("    ,   COUNT(B.SEQ) OVER(PARTITION BY A.CATEGORY_SEQ) AS ITEM_CNT      \n");
            sql.append("    ,   RANK() OVER( PARTITION BY B.CATEGORY_SEQ ORDER BY B.SEQ) AS RNK \n");
            sql.append("    ,   B.SEQ                                                           \n");
            sql.append("    ,   B.POSITION_NUM                                                  \n");
            sql.append("    ,   B.ITEM_NM                                                       \n");
            sql.append("    ,   B.ITEM_URL                                                      \n");
            sql.append("    ,   B.THUMB_FILE_NM                                                 \n");
            sql.append("    ,   B.BOARD_CONT                                                    \n");
            sql.append("  FROM  TZ_MAIN_CATEGORY A                                              \n");
            sql.append("    ,   TZ_MAIN_CATEGORY_DETAIL B                                       \n");
            sql.append(" WHERE  A.USE_YN = 'Y'                                                  \n");
            sql.append("   AND  B.USE_YN(+) = 'Y'                                               \n");
            sql.append("   AND  A.CATEGORY_SEQ = B.CATEGORY_SEQ(+)                              \n");
            sql.append(" ORDER  BY A.SORT_ORDER, B.POSITION_NUM                                 \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("selectMainCategoryList sql = " + sql + "\r\n" + ex.getMessage());
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

}
