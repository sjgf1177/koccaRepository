package com.credu.statistics;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class OpenClassStatisticsBean {

    /**
     * 열린강좌 년도별 통계 자료를 조회한다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList 전문가 관리 리스트 뷰카운트
     * @throws Exception
     */
    public ArrayList<DataBox> selectViewCountTotListGoldClass(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        String v_search_year = box.getString("search_year");
        if (v_search_year.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            v_search_year = sdf.format(dt);
        }

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            //            sql.append("/* com.credu.infomation.GoldClassBean selectViewCountTotListGoldClass( 열린강좌 년도별 통계 조회 )*/ \n");
            //            sql.append("SELECT AREA,\n ");
            //            sql.append("SUM(CNT) TOT,\n ");
            //            sql.append("SUM(DECODE(SUBSTRING(VIEWDATE,5,2), '01', CNT,0)) AS JAN,\n ");
            //            sql.append("SUM(DECODE(SUBSTRING(VIEWDATE,5,2), '02', CNT,0)) AS FEB,\n ");
            //            sql.append("SUM(DECODE(SUBSTRING(VIEWDATE,5,2), '03', CNT,0)) AS MAR,\n ");
            //            sql.append("SUM(DECODE(SUBSTRING(VIEWDATE,5,2), '04', CNT,0)) AS APR,\n ");
            //            sql.append("SUM(DECODE(SUBSTRING(VIEWDATE,5,2), '05', CNT,0)) AS MAY,\n ");
            //            sql.append("SUM(DECODE(SUBSTRING(VIEWDATE,5,2), '06', CNT,0)) AS JUN,\n ");
            //            sql.append("SUM(DECODE(SUBSTRING(VIEWDATE,5,2), '07', CNT,0)) AS JUL,\n ");
            //            sql.append("SUM(DECODE(SUBSTRING(VIEWDATE,5,2), '08', CNT,0)) AS AUG,\n ");
            //            sql.append("SUM(DECODE(SUBSTRING(VIEWDATE,5,2), '09', CNT,0)) AS SEP,\n ");
            //            sql.append("SUM(DECODE(SUBSTRING(VIEWDATE,5,2), '10', CNT,0)) AS OCT,\n ");
            //            sql.append("SUM(DECODE(SUBSTRING(VIEWDATE,5,2), '11', CNT,0)) AS NOV,\n ");
            //            sql.append("SUM(DECODE(SUBSTRING(VIEWDATE,5,2), '12', CNT,0)) AS DEC\n ");
            //            sql.append("FROM\n ");
            //            sql.append("(SELECT A.SEQ, A.LECNM, A.AREA, B.VIEWDATE, B.CNT FROM TZ_GOLDCLASS A, TZ_VIEWCOUNT B WHERE A.SEQ = B.SEQ AND B.GUBUN='GCTB')\n ");
            //            sql.append("WHERE SUBSTRING(VIEWDATE,1,4) = '").append(v_search_year).append("' \n");
            //            sql.append("GROUP BY ROLLUP(AREA)\n ");

            sql.append("/* com.credu.infomation.GoldClassBean selectViewCountTotListGoldClass( 열린강좌 년도별 통계 조회 )*/ \n");
            sql.append("SELECT  AREA                                                \n");
            sql.append("    ,   NVL( GET_CODENM('0101', AREA), '합계') AS AREANM    \n");
            sql.append("    ,   NVL( SUM( DECODE(MON, '01', CNT) ), 0) AS JAN       \n");
            sql.append("    ,   NVL( SUM( DECODE(MON, '02', CNT) ), 0) AS FEB       \n");
            sql.append("    ,   NVL( SUM( DECODE(MON, '03', CNT) ), 0) AS MAR       \n");
            sql.append("    ,   NVL( SUM( DECODE(MON, '04', CNT) ), 0) AS APR       \n");
            sql.append("    ,   NVL( SUM( DECODE(MON, '05', CNT) ), 0) AS MAY       \n");
            sql.append("    ,   NVL( SUM( DECODE(MON, '06', CNT) ), 0) AS JUN       \n");
            sql.append("    ,   NVL( SUM( DECODE(MON, '07', CNT) ), 0) AS JUL       \n");
            sql.append("    ,   NVL( SUM( DECODE(MON, '08', CNT) ), 0) AS AUG       \n");
            sql.append("    ,   NVL( SUM( DECODE(MON, '09', CNT) ), 0) AS SEP       \n");
            sql.append("    ,   NVL( SUM( DECODE(MON, '10', CNT) ), 0) AS OCT       \n");
            sql.append("    ,   NVL( SUM( DECODE(MON, '11', CNT) ), 0) AS NOV       \n");
            sql.append("    ,   NVL( SUM( DECODE(MON, '12', CNT) ), 0) AS DEC       \n");
            sql.append("    ,   SUM(CNT) AS TOT_CNT                                 \n");
            sql.append("  FROM  (                                                   \n");
            sql.append("        SELECT  A.SEQ                                       \n");
            sql.append("            ,   A.AREA                                      \n");
            sql.append("            ,   A.LECTURE_CLS                               \n");
            sql.append("            ,   A.LECNM                                     \n");
            sql.append("            ,   SUBSTR(B.VIEWDATE, 5, 2) AS MON             \n");
            sql.append("            ,   B.CNT                                       \n");
            sql.append("          FROM  TZ_GOLDCLASS A                              \n");
            sql.append("            ,   TZ_VIEWCOUNT B                              \n");
            sql.append("         WHERE  SUBSTRING(B.VIEWDATE,1,4) = '").append(v_search_year).append("'\n");
            sql.append("           AND  B.GUBUN = 'GCTB'                            \n");
            sql.append("           AND  A.SEQ = B.SEQ                               \n");
            sql.append("        )                                                   \n");
            sql.append(" GROUP  BY ROLLUP( AREA)                                    \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
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
     * 열린강좌 조회건수를 검색기간내 일별로 조회한다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectViewCountListGoldClass(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        String searchStartDate = box.getString("searchStartDate");
        String searchEndDate = box.getString("searchEndDate");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.infomation.GoldClassBean selectViewCountListGoldClass( 열린강좌 기간별 조회건수 조회 ) */ \n");
            sql.append("SELECT  A.SEQ       \n");
            sql.append("    ,   GET_CODENM('0101', B.AREA) AS AREANM    \n");
            sql.append("    ,   GET_CODENM('0118', B.LECTURE_CLS) AS LECTURE_CLSNM  \n");
            sql.append("    ,   B.LECNM     \n");
            sql.append("    ,   A.VIEWDATE  \n");
            sql.append("    ,   SUM (A.CNT) AS CNT  \n ");
            sql.append("  FROM  (   \n");
            sql.append("        SELECT  DECODE (SEQ, NULL, 0, SEQ) AS SEQ   \n");
            sql.append("            ,   VIEWDATE            \n");
            sql.append("            ,   SUM (CNT) AS CNT    \n");
            sql.append("          FROM  TZ_VIEWCOUNT        \n");
            sql.append("         WHERE  GUBUN = 'GCTB'      \n");
            sql.append("         GROUP  BY CUBE (SEQ, VIEWDATE) \n");
            sql.append("        ) A \n");
            sql.append("    ,   TZ_GOLDCLASS B      \n");
            sql.append(" WHERE  A.SEQ = B.SEQ(+)    \n ");
            sql.append("   AND  A.VIEWDATE BETWEEN '").append(searchStartDate).append("' \n");
            sql.append("                    AND  '").append(searchEndDate).append("'   \n");
            sql.append(" GROUP  BY A.SEQ, B.AREA, B.LECTURE_CLS, B.LECNM, A.VIEWDATE   \n");
            sql.append(" ORDER  BY A.VIEWDATE DESC, B.AREA, B.LECTURE_CLS, SEQ         \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
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
     * 검색기간내 열린강좌별 조회건수의 합계를 조회한다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectGoldClassTotalCountInPeriod(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        String searchCondition = box.getStringDefault("searchCondition", "year");
        String searchYear = box.getString("searchYear");
        String startDate = box.getString("searchStartDate");
        String endDate = box.getString("searchEndDate");

        if (searchYear.equals("")) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
            Date dt = new Date();
            searchYear = sdf.format(dt);
        }

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.infomation.GoldClassBean selectViewCountListGoldClass( 열린강좌 기간별 조회건수 합계 조회 ) */ \n");

            sql.append("SELECT  AREA                                                                    \n");
            sql.append("    ,   GET_CODENM('0101', AREA) AS AREANM                                      \n");
            sql.append("    ,   LECTURE_CLS                                                             \n");
            sql.append("    ,   GET_CODENM('0118', LECTURE_CLS) AS LECTURE_CLSNM                        \n");
            sql.append("    ,   SEQ                                                                     \n");
            sql.append("    ,   CASE WHEN (LECNM = 'N1' AND LECTURE_CLS = 'N2' AND AREA IS NOT NULL)    \n");
            sql.append("                THEN GET_CODENM('0101', AREA) || ' 합계'                        \n");
            sql.append("             WHEN (LECNM = 'N1' AND LECTURE_CLS = 'N2' AND AREA IS NULL)        \n");
            sql.append("                THEN '총계'                                                     \n");
            sql.append("             ELSE LECNM                                                         \n");
            sql.append("        END AS LECNM                                                            \n");
            sql.append("    ,   CREATYEAR                                                               \n");
            sql.append("    ,   JAN                                                                     \n");
            sql.append("    ,   FEB                                                                     \n");
            sql.append("    ,   MAR                                                                     \n");
            sql.append("    ,   APR                                                                     \n");
            sql.append("    ,   MAY                                                                     \n");
            sql.append("    ,   JUN                                                                     \n");
            sql.append("    ,   JUL                                                                     \n");
            sql.append("    ,   AUG                                                                     \n");
            sql.append("    ,   SEP                                                                     \n");
            sql.append("    ,   OCT                                                                     \n");
            sql.append("    ,   NOV                                                                     \n");
            sql.append("    ,   DEC                                                                     \n");
            sql.append("    ,   TOT_CNT                                                                 \n");
            sql.append("    ,   SORT_ORDER                                                              \n");
            sql.append("  FROM  (                                                                       \n");
            sql.append("        SELECT  AREA                                                            \n");
            sql.append("            ,   NVL(SEQ, 99999) AS SEQ                                          \n");
            sql.append("            ,   NVL(LECNM, 'N1') AS LECNM                                       \n");
            sql.append("            ,   NVL(LECTURE_CLS, 'N2') AS LECTURE_CLS                           \n");
            sql.append("            ,   NVL( MAX(CREATYEAR), 0) AS CREATYEAR		                    \n");
            sql.append("            ,   NVL( SUM( DECODE(MON, '01', CNT) ), 0) AS JAN                   \n");
            sql.append("            ,   NVL( SUM( DECODE(MON, '02', CNT) ), 0) AS FEB                   \n");
            sql.append("            ,   NVL( SUM( DECODE(MON, '03', CNT) ), 0) AS MAR                   \n");
            sql.append("            ,   NVL( SUM( DECODE(MON, '04', CNT) ), 0) AS APR                   \n");
            sql.append("            ,   NVL( SUM( DECODE(MON, '05', CNT) ), 0) AS MAY                   \n");
            sql.append("            ,   NVL( SUM( DECODE(MON, '06', CNT) ), 0) AS JUN                   \n");
            sql.append("            ,   NVL( SUM( DECODE(MON, '07', CNT) ), 0) AS JUL                   \n");
            sql.append("            ,   NVL( SUM( DECODE(MON, '08', CNT) ), 0) AS AUG                   \n");
            sql.append("            ,   NVL( SUM( DECODE(MON, '09', CNT) ), 0) AS SEP                   \n");
            sql.append("            ,   NVL( SUM( DECODE(MON, '10', CNT) ), 0) AS OCT                   \n");
            sql.append("            ,   NVL( SUM( DECODE(MON, '11', CNT) ), 0) AS NOV                   \n");
            sql.append("            ,   NVL( SUM( DECODE(MON, '12', CNT) ), 0) AS DEC                   \n");
            sql.append("            ,   SUM(CNT) AS TOT_CNT                                             \n");
            sql.append("            ,   CASE WHEN ( LECNM IS NULL AND AREA IS NOT NULL ) THEN 999998    \n");
            sql.append("                     WHEN ( LECNM IS NULL AND AREA IS NULL ) THEN 999999        \n");
            sql.append("                     ELSE 1			                                            \n");
            sql.append("                END AS SORT_ORDER                                               \n");
            sql.append("          FROM  (                                                               \n");
            sql.append("                SELECT  A.SEQ                                                   \n");
            sql.append("                    ,   A.AREA                                                  \n");
            sql.append("                    ,   NVL(A.LECTURE_CLS, '0000') AS LECTURE_CLS               \n");
            sql.append("                    ,   A.LECNM                                                 \n");
            sql.append("                    ,   A.CREATYEAR                                                     \n");
            sql.append("                    ,   MON                                                     \n");
            sql.append("                    ,   NVL(B.CNT, 0) AS CNT                                    \n");
            sql.append("                  FROM  TZ_GOLDCLASS A                                          \n");
            sql.append("                    ,   (                                                       \n");
            sql.append("                        SELECT  SEQ                                             \n");
            sql.append("                            ,   SUBSTR(VIEWDATE, 5, 2) AS MON                   \n");
            sql.append("                            ,   SUM(CNT) AS CNT                                 \n");
            sql.append("                          FROM  TZ_VIEWCOUNT                                    \n");

            if (searchCondition.equals("year")) {
                sql.append("                         WHERE  SUBSTR(VIEWDATE, 0, 4) = '").append(searchYear).append("'  \n");
            } else {
                sql.append("                         WHERE  VIEWDATE BETWEEN '").append(startDate).append("' AND '").append(endDate).append("'  \n");
            }

            sql.append("                           AND  GUBUN = 'GCTB'                                  \n");
            sql.append("                         GROUP  BY SEQ, SUBSTR(VIEWDATE, 5, 2)                  \n");
            sql.append("                        ) B                                                     \n");
            sql.append("                 WHERE  A.SEQ = B.SEQ(+)                                        \n");
            sql.append("                   AND  A.SEQ <> 44                                             \n");
            sql.append("                 ORDER  BY A.SEQ                                                \n");
            sql.append("                )                                                               \n");
            sql.append("         GROUP  BY ROLLUP( AREA, SEQ, LECNM, LECTURE_CLS )                      \n");
            sql.append("        )                                                                       \n");
            sql.append(" WHERE  ( LECNM <> 'N1' AND LECTURE_CLS <> 'N2' )                               \n");
            sql.append("    OR  ( SEQ = 99999 )                                                         \n");
            sql.append(" ORDER  BY SORT_ORDER DESC, AREA, LECTURE_CLS, SEQ                              \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
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
}
