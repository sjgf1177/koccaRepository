// **********************************************************
// 1. 제 목: 접속통계
// 2. 프로그램명: MainStatusticBean
// 3. 개 요: 접속통계
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성:
// 7. 수 정:
// **********************************************************

package com.credu.statistics;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class MainStatusticBean {

    // private static final String CONFIG_NAME = null;

    public MainStatusticBean() {
    }

    /**
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_grcode = box.getString("param1");
        String v_gyear = box.getString("param2");
        String v_grseq = box.getString("param3");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            sql.setLength(0);
            sql.append("/* com.credu.statistics.MainStatusticBean selectList */ \n");
            sql.append("SELECT  *   \n");
            sql.append("  FROM  (   \n");
            sql.append("        SELECT  A.GRCODE AS GRCODE  \n");
            sql.append("            ,   A.GYEAR AS GYEAR    \n");
            sql.append("            ,   A.GRSEQ AS GRSEQ    \n");
            sql.append("            ,   A.GRSEQNM AS GRSEQNM    \n");
            sql.append("            ,   DECODE(E.AREA,NULL,'A0',E.AREA) AS AREA \n");
            sql.append("            ,   COUNT (DISTINCT B.SUBJ) AS SUBJCNT      \n");
            sql.append("            ,   COUNT ( * ) AS PROPOSECNT               \n");
            sql.append("            ,   SUM (DECODE (D.ISGRADUATED, NULL, 0, 1)) AS STUDENTCNT  \n");
            sql.append("            ,   SUM (DECODE (C.CHKFINAL,'N', 1)) AS CANCELCNT           \n");
            sql.append("            ,   SUM (DECODE (D.ISGRADUATED, 'Y', 1)) AS ISGRADUATED_Y   \n");
            sql.append("            ,   SUM (DECODE (D.ISGRADUATED, 'N', 1)) AS ISGRADUATED_N   \n");
            sql
                    .append("            ,   ROUND (SUM (DECODE (D.ISGRADUATED, 'Y', 1))/SUM (DECODE (D.ISGRADUATED, NULL, 0, 1))*100,1) AS ISGRADUATED_RATE   \n");
            sql.append("          FROM  TZ_GRSEQ A      \n");
            sql.append("            ,   TZ_SUBJSEQ B    \n");
            sql.append("            ,   TZ_PROPOSE C    \n");
            sql.append("            ,   TZ_STUDENT D    \n");
            sql.append("            ,   TZ_SUBJ E       \n");
            sql.append("         WHERE  A.GRCODE = '").append(v_grcode).append("'   \n");
            sql.append("           AND  A.GYEAR = '").append(v_gyear).append("'     \n");
            sql.append("           AND  A.STAT = 'Y'            \n");
            sql.append("           AND  A.HOMEPAGEYN = 'Y'      \n");
            sql.append("           AND  A.GRCODE = B.GRCODE     \n");
            sql.append("           AND  A.GYEAR = B.GYEAR       \n");
            sql.append("           AND  A.GRSEQ = B.GRSEQ       \n");
            sql.append("           AND  C.SUBJ = B.SUBJ         \n");
            sql.append("           AND  C.YEAR = B.YEAR         \n");
            sql.append("           AND  C.SUBJSEQ = B.SUBJSEQ   \n");
            sql.append("           AND  C.SUBJ = D.SUBJ(+)      \n");
            sql.append("           AND  C.SUBJSEQ = D.SUBJSEQ(+)\n");
            sql.append("           AND  C.YEAR = D.YEAR(+)      \n");
            sql.append("           AND  C.USERID = D.USERID(+)  \n");
            sql.append("           AND  B.SUBJ = E.SUBJ         \n");

            if (!v_grseq.equals("")) {
                sql.append("           AND A.GRSEQ = '").append(v_grseq).append("'  \n");
            }
            sql.append("         GROUP  BY A.GRCODE \n");
            sql.append("            ,   A.GYEAR     \n");
            sql.append("            ,   A.GRSEQ     \n");
            sql.append("            ,   A.GRSEQNM   \n");
            sql.append("            ,   ROLLUP(E.AREA)  \n");
            sql.append("        )   \n");
            sql.append(" ORDER  BY GYEAR DESC, GYEAR, GRSEQ, AREA   \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("isgraduatedrate", new Double(ls.getDouble("isgraduated_rate")));
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectViewCountTotListGoldClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_search_year = box.getString("param1");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            sql.setLength(0);
            sql.append("/* com.credu.statistics.MainStatusticBean selectViewCountTotListGoldClass */ \n");
            sql.append("SELECT  AREA                                    \n");
            sql.append("    ,   SUM(CNT) TOT                            \n");
            sql.append("    ,   SUM(DECODE(MON, '01', CNT,0)) AS JAN    \n");
            sql.append("    ,   SUM(DECODE(MON, '02', CNT,0)) AS FEB    \n");
            sql.append("    ,   SUM(DECODE(MON, '03', CNT,0)) AS MAR    \n");
            sql.append("    ,   SUM(DECODE(MON, '04', CNT,0)) AS APR    \n");
            sql.append("    ,   SUM(DECODE(MON, '05', CNT,0)) AS MAY    \n");
            sql.append("    ,   SUM(DECODE(MON, '06', CNT,0)) AS JUN    \n");
            sql.append("    ,   SUM(DECODE(MON, '07', CNT,0)) AS JUL    \n");
            sql.append("    ,   SUM(DECODE(MON, '08', CNT,0)) AS AUG    \n");
            sql.append("    ,   SUM(DECODE(MON, '09', CNT,0)) AS SEP    \n");
            sql.append("    ,   SUM(DECODE(MON, '10', CNT,0)) AS OCT    \n");
            sql.append("    ,   SUM(DECODE(MON, '11', CNT,0)) AS NOV    \n");
            sql.append("    ,   SUM(DECODE(MON, '12', CNT,0)) AS DEC    \n");
            sql.append("  FROM  (                                       \n");
            sql.append("        SELECT  A.SEQ                           \n");
            sql.append("            ,   A.LECNM                         \n");
            sql.append("            ,   A.AREA                          \n");
            sql.append("            ,   SUBSTR(B.VIEWDATE, 5, 2) AS MON \n");
            sql.append("            ,   SUBSTR(B.VIEWDATE, 1, 4) AS YEAR\n");
            sql.append("            ,   B.CNT                           \n");
            sql.append("          FROM  TZ_GOLDCLASS A                  \n");
            sql.append("            ,   TZ_VIEWCOUNT B                  \n");
            sql.append("         WHERE  A.SEQ = B.SEQ                   \n");
            sql.append("           AND  B.GUBUN = 'GCTB'                \n");
            sql.append("        )                                       \n");
            sql.append(" WHERE  YEAR = '").append(v_search_year).append("'  \n");
            sql.append(" GROUP  BY ROLLUP(AREA)                         \n");
            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectTutorStatus(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_search_year = box.getString("param1");
        String v_gubun = box.getStringDefault("param2", "I");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            sql.setLength(0);
            sql.append("/* com.credu.statistic.MainStatusticBean selectTutotrStatus */  \n");
            sql.append("SELECT  A.SUBJ                                                      \n");
            sql.append("    ,   (                                                           \n");
            sql.append("        SELECT  SUBJNM                                              \n");
            sql.append("          FROM  TZ_SUBJ                                             \n");
            sql.append("         WHERE  SUBJ = A.SUBJ                                       \n");
            sql.append("        ) AS SUBJNM                                                 \n");
            sql.append("    ,   (                                                           \n");
            sql.append("        SELECT  AREA                                                \n");
            sql.append("          FROM  TZ_SUBJ                                             \n");
            sql.append("         WHERE  SUBJ = A.SUBJ                                       \n");
            sql.append("        ) AS AREA                                                   \n");
            sql.append("    ,   COUNT(*) TOT                                                \n");
            sql.append("    ,   SUM(DECODE(SUBSTRING(A.INDATE,5,2), '01', 1,0)) AS JAN      \n");
            sql.append("    ,   SUM(DECODE(SUBSTRING(A.INDATE,5,2), '02', 1,0)) AS FEB      \n");
            sql.append("    ,   SUM(DECODE(SUBSTRING(A.INDATE,5,2), '03', 1,0)) AS MAR      \n");
            sql.append("    ,   SUM(DECODE(SUBSTRING(A.INDATE,5,2), '04', 1,0)) AS APR      \n");
            sql.append("    ,   SUM(DECODE(SUBSTRING(A.INDATE,5,2), '05', 1,0)) AS MAY      \n");
            sql.append("    ,   SUM(DECODE(SUBSTRING(A.INDATE,5,2), '06', 1,0)) AS JUN      \n");
            sql.append("    ,   SUM(DECODE(SUBSTRING(A.INDATE,5,2), '07', 1,0)) AS JUL      \n");
            sql.append("    ,   SUM(DECODE(SUBSTRING(A.INDATE,5,2), '08', 1,0)) AS AUG      \n");
            sql.append("    ,   SUM(DECODE(SUBSTRING(A.INDATE,5,2), '09', 1,0)) AS SEP      \n");
            sql.append("    ,   SUM(DECODE(SUBSTRING(A.INDATE,5,2), '10', 1,0)) AS OCT      \n");
            sql.append("    ,   SUM(DECODE(SUBSTRING(A.INDATE,5,2), '11', 1,0)) AS NOV      \n");
            sql.append("    ,   SUM(DECODE(SUBSTRING(A.INDATE,5,2), '12', 1,0)) AS DEC      \n");
            sql.append("  FROM  (                                                           \n");
            sql.append("        SELECT  AA.INDATE                                           \n");
            sql.append("            ,   AA.USERID                                           \n");
            sql.append("            ,   AA.SUBJ                                             \n");
            sql.append("          FROM  (                                                   \n");

                        if (v_gubun.equals("I")) {

            sql.append("                SELECT  INDATE                                      \n");
            sql.append("                    ,   INUSERID AS USERID                          \n");
            sql.append("                    ,   CATEGORYCD AS SUBJ                          \n");
            sql.append("                  FROM  TZ_HOMEQNA T                                \n");
            sql.append("                 WHERE  TABSEQ = '7'                                \n");
                        } else if (v_gubun.equals("Q")) {

            sql.append("                SELECT  INDATE                                      \n");
            sql.append("                    ,   INUSERID AS USERID                          \n");
            sql.append("                    ,   SUBJ                                        \n");
            sql.append("                  FROM  TZ_QNA T                                    \n");
            sql.append("                 WHERE  KIND != '0'                                 \n");
            }
            sql.append("                ) AA                                                \n");
            sql.append("            ,   TZ_MANAGER BB                                       \n");
            sql.append("         WHERE  SUBSTRING(AA.INDATE,1,4) = '").append(v_search_year).append("'  \n");
            sql.append("           AND  AA.USERID = BB.USERID                               \n");
            sql.append("           AND  BB.GADMIN = 'P1'                                    \n");
            sql.append("        ) A                                                         \n");
            sql.append(" GROUP  BY ROLLUP (A.SUBJ)                                          \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectSetStatList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_grcode = box.getString("param1");
        String v_gyear = box.getString("param2");

        if (box.getString("param_gubun").equals("U")) {
            String v_grseq = box.getString("param3");
            String v_stat = box.getString("param4");
            update_stat(v_grcode, v_gyear, v_grseq, v_stat, box);
        }

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();
            sql.setLength(0);
            sql.append("SELECT  GRCODE  \n");
            sql.append("    ,   GYEAR   \n");
            sql.append("    ,   GRSEQ   \n");
            sql.append("    ,   GRSEQNM \n");
            sql.append("    ,   STAT    \n");
            sql.append("  FROM  TZ_GRSEQ \n");
            sql.append(" WHERE  GRCODE = '").append(v_grcode).append("' \n");
            sql.append("   AND  GYEAR = '").append(v_gyear).append("' \n");
            sql.append(" ORDER BY GRSEQ \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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

    public int update_stat(String grcode, String gyear, String grseq, String stat, RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer updateSql = new StringBuffer();
        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            updateSql.append("UPDATE  TZ_GRSEQ SET  \n ");
            updateSql.append("        STAT = ?      \n ");
            updateSql.append(" WHERE  GRCODE = ?    \n ");
            updateSql.append("   AND  GYEAR = ?	    \n ");
            updateSql.append("   AND  GRSEQ = ?	    \n ");

            pstmt = connMgr.prepareStatement(updateSql.toString());

            int index = 1;
            pstmt.setString(index++, stat);
            pstmt.setString(index++, grcode);
            pstmt.setString(index++, gyear);
            pstmt.setString(index++, grseq);
            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, updateSql.toString());
            throw new Exception("sql = " + updateSql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

}