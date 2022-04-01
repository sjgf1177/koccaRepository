package com.credu.mobile.onlineclass;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class OnlineClassBean {
    /**
     * 정규과정 분류별 목록을 조회한다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectCategoryList(String clsCd) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.mobile.onlineclass.OnlineClassBean() selectCategoryList (정규과정 분류별 목록 조회) */ \n");
            sql.append("SELECT  A.CLS_CD                            \n");
            sql.append("    ,   A.UPPER_CLS_CD                      \n");
            sql.append("    ,   A.CLS_NM                            \n");
            sql.append("    ,   A.USE_YN                            \n");
            sql.append("    ,   A.SORT_ORDER                        \n");
            sql.append("    ,   (                                   \n");
            sql.append("        SELECT  COUNT(SUBJ)                 \n");
            sql.append("          FROM  TZ_SUBJ_CLS_MNG B           \n");
            sql.append("         WHERE  A.CLS_CD = B.CLS_CD(+)      \n");
            sql.append("        ) AS CNT                            \n");
            sql.append("  FROM  TZ_SUBJ_CLS_MASTER A                \n");
            sql.append(" WHERE  A.UPPER_CLS_CD = '").append(clsCd).append("'  \n");
            sql.append("   AND  A.USE_YN = 'Y'  					\n");
            sql.append(" ORDER  BY A.UPPER_CLS_CD, A.SORT_ORDER     \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception ex) {
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
     * 정규과정 분류별 등록된 과정 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectClassifySubjectList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String clsCd = box.getString("clsCd");
        String step = box.getString("step");
        String grcode = box.getSession("grcode");
        grcode = (grcode == null || grcode.equals("")) ? "N000001" : grcode;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.mobile.onlineclass.OnlineClassBean() selectClassifySubjectList (분류별 과정 목록 조회) */ \n");
            sql.append("SELECT  *                                                                                      \n");
            sql.append("  FROM  (                                                                                      \n");
            sql.append("        SELECT  V.*                                                                            \n");
            sql.append("            ,   RANK() OVER( ORDER BY REC_YN DESC, HIT_YN DESC, NEW_YN DESC, SUBJNM ) AS RNK   \n");
            sql.append("          FROM  (                                                                              \n");
            sql.append("                SELECT                                                                         \n");
            sql.append("                        COUNT(V.SUBJ) OVER() AS CNT                                            \n");
            sql.append("                    ,   V.SUBJ                                                                 \n");
            sql.append("                    ,   V.YEAR                                                                 \n");
            sql.append("                    ,   V.SUBJSEQ                                                              \n");
            sql.append("                    ,   V.SUBJNM                                                               \n");
            sql.append("                    ,   V.TUTOR_NM                                                             \n");
            sql.append("                    ,   V.INTRO                                                                \n");
            sql.append("                    ,   V.MOBILE_USE_YN                                                        \n");
            sql.append("                    ,   SUBSTR(V.SPECIALS, 1, 1) AS NEW_YN                                     \n");
            sql.append("                    ,   SUBSTR(V.SPECIALS, 2, 1) AS HIT_YN                                     \n");
            sql.append("                    ,   SUBSTR(V.SPECIALS, 3, 1) AS REC_YN                                     \n");
            sql.append("                    ,   REPLACE(V.SUBJFILENAMENEW, '\', '/') AS SUBJFILENAMENEW                \n");
            sql.append("                  FROM  VZ_SUBJINFO V                                                          \n");
            sql.append("                    ,   TZ_SUBJ_CLS_MNG B                                                      \n");
            sql.append("                 WHERE  V.SUBJ_FLAG = 'ONLINE'                                                 \n");
            sql.append("                   AND  V.GRCODE = '").append(grcode).append("'                                \n");
            sql.append("                   AND  B.CLS_CD = '").append(clsCd).append("'                                 \n");
            sql.append("                   AND  V.SUBJ = B.SUBJ                                                        \n");
            sql.append("                ) V                                                                            \n");
            sql.append("        )                                                                                      \n");

            if (step.equals("step1")) {
                sql.append(" WHERE  RNK < 4 \n");
            } else if (step.equals("step2")) {
                sql.append(" WHERE  RNK > 3 AND RNK < 9 ");
            }
            sql.append(" ORDER  BY REC_YN DESC, HIT_YN DESC, NEW_YN DESC,  RNK                                         \n");

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setString(1, grcode);
            //            pstmt.setString(2, clsCd);
            //
            //            ls = new ListSet(pstmt);
            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                } catch (Exception e) {
            //                }
            //            }

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
     * 분야별로 등록된 세부(전체) 과정 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectCategoryDetailList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String clsCd = box.getString("clsCd");
        String grcode = box.getSession("grcode");
        grcode = (grcode == null || grcode.equals("")) ? "N000001" : grcode;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.mobile.onlineclass.OnlineClassBean() selectCategoryDetailList (분야별 과정 상세(전체) 목록 조회) */ \n");
            sql.append("SELECT  *                           \n");
            sql.append("  FROM  (                           \n");
            sql.append("        SELECT  V2.SUBJ_FLAG        \n");
            sql.append("            ,   V2.SUBJ             \n");
            sql.append("            ,   V2.SUBJNM           \n");
            sql.append("            ,   V2.YEAR             \n");
            sql.append("            ,   V2.GRSEQ            \n");
            sql.append("            ,   V2.SUBJSEQ          \n");
            sql.append("            ,   V2.SUBJFILENAMENEW  \n");
            sql.append("            ,   V2.AREA             \n");
            sql.append("            ,   V2.AREANM           \n");
            sql.append("            ,   V2.BIZ_TYPE         \n");
            sql.append("            ,   V2.MOBILE_USE_YN    \n");
            sql.append("            ,   TO_CHAR(TO_DATE(V2.PROPSTART, 'yyyymmddhh24'), 'yyyy/MM/dd') AS PROPSTART       \n");
            sql.append("            ,   TO_CHAR(TO_DATE(V2.PROPEND, 'yyyymmddhh24'), 'yyyy/MM/dd') AS PROPEND           \n");
            sql.append("            ,   TO_CHAR(TO_DATE(V2.EDUSTART, 'yyyymmddhh24'), 'yyyy/MM/dd') AS EDUSTART         \n");
            sql.append("            ,   TO_CHAR(TO_DATE(V2.EDUEND, 'yyyymmddhh24'), 'yyyy/MM/dd') AS EDUEND             \n");
            sql.append("            ,   NVL(DECODE(HIT_RNK, 1,1,0), 0) AS HIT_RNK                                       \n");
            sql.append("            ,   SUBSTR(V2.SPECIALS, 1, 1) AS NEW_YN                                             \n");
            sql.append("            ,   SUBSTR(V2.SPECIALS, 2, 1) AS HIT_YN                                             \n");
            sql.append("            ,   SUBSTR(V2.SPECIALS, 3, 1) AS REC_YN                                             \n");
            sql.append("            ,   APPLY_CNT                                                                       \n");
            sql.append("          FROM  (                                                                               \n");
            sql.append("                SELECT  D.SUBJ                                                                  \n");
            sql.append("                    ,   NVL( COUNT(D.USERID), 0) AS APPLY_CNT                                   \n");
            sql.append("                    ,   RANK() OVER(ORDER BY COUNT(D.USERID) DESC ) AS HIT_RNK                  \n");
            sql.append("                  FROM  (                                                                       \n");
            sql.append("                        SELECT                                                                  \n");
            sql.append("                                LAG(A.GYEAR) OVER(ORDER BY A.GYEAR, A.GRSEQ) PREV_GYEAR         \n");
            sql.append("                            ,   LAG(A.GRSEQ) OVER(ORDER BY A.GYEAR, A.GRSEQ) PREV_GRSEQ         \n");
            sql.append("                            ,   A.GRCODE                                                        \n");
            sql.append("                            ,   A.GYEAR                                                         \n");
            sql.append("                            ,   A.GRSEQ                                                         \n");
            sql.append("                            ,   A.GRSEQNM                                                       \n");
            sql.append("                            ,   B.PROPSTART                                                     \n");
            sql.append("                            ,   B.PROPEND                                                       \n");
            sql.append("                          FROM  TZ_GRSEQ A                                                      \n");
            sql.append("                            ,   (                                                               \n");
            sql.append("                                SELECT  GRCODE                                                  \n");
            sql.append("                                    ,   GYEAR                                                   \n");
            sql.append("                                    ,   GRSEQ                                                   \n");
            sql.append("                                    ,   PROPSTART                                               \n");
            sql.append("                                    ,   PROPEND                                                 \n");
            sql.append("                                  FROM  TZ_SUBJSEQ                                              \n");
            sql.append("                                 WHERE  GRCODE = '").append(grcode).append("'                   \n");
            sql.append("                                   AND  YEAR BETWEEN TO_CHAR( ADD_MONTHS(SYSDATE, -12), 'YYYY') \n");
            sql.append("                                                 AND TO_CHAR( SYSDATE, 'YYYY' )                 \n");
            sql.append("                                 GROUP BY GRCODE, GYEAR, GRSEQ, PROPSTART, PROPEND              \n");
            sql.append("                                ) B                                                             \n");
            sql.append("                         WHERE  A.GRCODE = '").append(grcode).append("'                         \n");
            sql.append("                           AND  A.HOMEPAGEYN = 'Y'                                              \n");
            sql.append("                           AND  A.STAT = 'Y'                                                    \n");
            sql.append("                           AND  A.GYEAR = B.GYEAR                                               \n");
            sql.append("                           AND  A.GRCODE = B.GRCODE                                             \n");
            sql.append("                           AND  A.GRSEQ = B.GRSEQ                                               \n");
            sql.append("                         ORDER  BY A.GYEAR, A.GRSEQ                                             \n");
            sql.append("                         ) V                                                                    \n");
            sql.append("                    ,   TZ_SUBJSEQ C                                                            \n");
            sql.append("                    ,   TZ_PROPOSE D                                                            \n");
            sql.append("                 WHERE  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN V.PROPSTART AND V.PROPEND      \n");
            sql.append("                   AND  V.PREV_GYEAR = C.GYEAR                                                  \n");
            sql.append("                   AND  V.PREV_GRSEQ = C.GRSEQ                                                  \n");
            sql.append("                   AND  C.YEAR = D.YEAR                                                         \n");
            sql.append("                   AND  C.SUBJ = D.SUBJ                                                         \n");
            sql.append("                   AND  C.SUBJSEQ = D.SUBJSEQ                                                   \n");
            sql.append("                   AND  (D.CANCELKIND IS NULL OR D.CANCELKIND = '')                             \n");
            sql.append("                 GROUP  BY D.SUBJ                                                               \n");
            sql.append("                )  V1                                                                           \n");
            sql.append("            ,   VZ_SUBJINFO V2                                                                  \n");
            sql.append("            ,   TZ_SUBJ_CLS_MNG K                                                               \n");
            sql.append("         WHERE  V2.SUBJ = V1.SUBJ(+)                                                            \n");
            sql.append("           AND  V2.SUBJ = K.SUBJ                                                                \n");
            sql.append("           AND  V2.GRCODE = '").append(grcode).append("'                                        \n");
            sql.append("           AND  V2.ISUSE = 'Y'                                                                  \n");
            sql.append("           AND  K.CLS_CD = '").append(clsCd).append("'                                          \n");
            sql.append("        )                                                                                       \n");
            sql.append(" ORDER  BY REC_YN DESC, HIT_YN DESC, NEW_YN DESC, SUBJNM, HIT_RNK DESC                          \n");

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setString(1, grcode);
            //            pstmt.setString(2, grcode);
            //            pstmt.setString(3, grcode);
            //            pstmt.setString(4, clsCd);
            //
            //            ls = new ListSet(pstmt);

            ls = connMgr.executeQuery(sql.toString());
            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                } catch (Exception e) {
            //                }
            //            }

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
     * 직업별 등록된 세부(전체) 과정 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectJobDetailList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String clsCd = box.getString("clsCd");
        String grcode = box.getSession("grcode");
        grcode = (grcode == null || grcode.equals("")) ? "N000001" : grcode;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.mobile.onlineclass.OnlineClassBean() selectJobDetailList (직업별 과정 상세(전체) 목록 조회) */ \n");
            sql.append("SELECT  *                                                                                   \n");
            sql.append("  FROM  (                                                                                   \n");
            sql.append("        SELECT  V.SUBJ                                                                      \n");
            sql.append("            ,   V.SUBJNM                                                                    \n");
            sql.append("            ,   V.SUBJFILENAMENEW                                                           \n");
            sql.append("            ,   SUBSTR(V.SPECIALS, 1, 1) AS NEW_YN                                          \n");
            sql.append("            ,   SUBSTR(V.SPECIALS, 2, 1) AS HIT_YN                                          \n");
            sql.append("            ,   SUBSTR(V.SPECIALS, 3, 1) AS REC_YN                                          \n");
            sql.append("            ,   TO_CHAR(TO_DATE(V.PROPSTART, 'yyyymmddhh24'), 'yyyy/MM/dd') AS PROPSTART    \n");
            sql.append("            ,   TO_CHAR(TO_DATE(V.PROPEND, 'yyyymmddhh24'), 'yyyy/MM/dd') AS PROPEND        \n");
            sql.append("            ,   TO_CHAR(TO_DATE(V.EDUSTART, 'yyyymmddhh24'), 'yyyy/MM/dd') AS EDUSTART      \n");
            sql.append("            ,   TO_CHAR(TO_DATE(V.EDUEND, 'yyyymmddhh24'), 'yyyy/MM/dd') AS EDUEND          \n");
            sql.append("            ,   V.YEAR                                                                      \n");
            sql.append("            ,   V.GRSEQ                                                                     \n");
            sql.append("            ,   V.SUBJSEQ                                                                   \n");
            sql.append("            ,   V.BIZ_TYPE                                                                  \n");
            sql.append("            ,   V.MOBILE_USE_YN                                                             \n");
            sql.append("          FROM  VZ_SUBJINFO V                                                               \n");
            sql.append("            ,   TZ_SUBJ_CLS_MNG A                                                           \n");
            sql.append("         WHERE  V.GRCODE = '").append(grcode).append("'                                     \n");
            sql.append("           AND  A.CLS_CD = '").append(clsCd).append("'                                      \n");
            sql.append("           AND  A.SUBJ = V.SUBJ                                                             \n");
            sql.append("        )                                                                                   \n");
            sql.append(" ORDER  BY NEW_YN DESC, SUBJNM                                                              \n");

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setString(1, grcode);
            //            pstmt.setString(2, clsCd);
            //
            //            ls = new ListSet(pstmt);

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }

            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                } catch (Exception e) {
            //                }
            //            }

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
     * 과정 정보 조회
     * 
     * @param box
     * @return
     */
    public DataBox selectSubjectInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        DataBox dbox = null;
        StringBuffer sql = new StringBuffer();

        String grcode = box.getSession("grcode");
        grcode = (grcode == null || grcode.equals("")) ? "N000001" : grcode;

        // int index = 1;
        String subj = box.getString("subj");
        String year = box.getString("year");
        String subjseq = box.getString("subjseq");
        String userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            if (subjseq.equals("") || year.equals("")) {
                sql.setLength(0);
                sql.append("/* com.credu.mobile.onlineclass.OnlineClassBean selectSubjectInfo (과정차수정보 조회) */  \n");
                sql.append("SELECT  SUBJ                                    \n");
                sql.append("    ,   YEAR                                    \n");
                sql.append("    ,   SUBJSEQ                                 \n");
                sql.append("  FROM  TZ_SUBJSEQ                              \n");
                sql.append(" WHERE  GRCODE = '").append(grcode).append("'   \n");
                sql.append("   AND  SUBJ = '").append(subj).append("'       \n");
                sql.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24')        \n");
                sql.append("        BETWEEN PROPSTART AND PROPEND           \n");

                ls = connMgr.executeQuery(sql.toString());
                if (ls.next()) {
                    year = ls.getString("year");
                    subjseq = ls.getString("subjseq");
                }
            }

            sql.setLength(0);
            sql.append("/* com.credu.mobile.onlineclass.OnlineClassBean selectSubjectInfo (과정정보 조회) */  \n");
            sql.append("SELECT  A.SUBJ                      \n");
            sql.append("    ,   A.SUBJNM      ,B.year     , B.subjseq                 \n");
            sql.append("    ,   NVL(A.MOBILE_USE_YN, 'N') AS MOBILE_USE_YN  \n");
            sql.append("    ,   A.MOBILE_PREURL \n");
            sql.append("    ,   (   \n");
            sql.append("        SELECT  NEW_MOBILE_URL      \n");
            sql.append("          FROM  TZ_SUBJLESSON       \n");
            sql.append("         WHERE  SUBJ = A.SUBJ       \n");
            sql.append("           AND  LESSON = '001'      \n");
            sql.append("        ) AS MOBILE_PREURL2         \n");
            sql.append("    ,   A.INTRO                     \n");
            sql.append("    ,   A.EXPLAIN                   \n");
            sql.append("    ,   A.INTRODUCEFILENAMENEW      \n");
            sql.append("    ,   A.BIZ_TYPE                  \n");
            sql.append("    ,   SUBSTR(A.SPECIALS, 1, 1) AS NEW_YN  \n");
            sql.append("    ,   TO_CHAR( TO_DATE(B.PROPSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPSTART   \n");
            sql.append("    ,   TO_CHAR( TO_DATE(B.PROPEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPEND       \n");
            sql.append("    ,   TO_CHAR( TO_DATE(B.EDUSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUSTART     \n");
            sql.append("    ,   TO_CHAR( TO_DATE(B.EDUEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUEND         \n");
            sql.append("    ,   CASE WHEN C.USERID IS NULL THEN 'N' ELSE 'Y' END AS PROP_YN                 \n");
            sql.append("    ,   DECODE( (SELECT COUNT(USERID) FROM TZ_SUBJ_FAVOR WHERE SUBJ = '").append(subj).append("' AND USERID = '").append(userid).append("'), 0, 'N', 'Y') AS FAVOR_YN \n");
            sql.append("    ,   CASE WHEN (TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN B.EDUSTART AND B.EDUEND) THEN 'Y' ELSE 'N' END AS EDU_YN \n");
            sql.append("  FROM  TZ_SUBJ A                  \n");
            sql.append("    ,   TZ_SUBJSEQ B                \n");
            sql.append("    ,   TZ_PROPOSE C                \n");
            sql.append(" WHERE  A.ISUSE = 'Y'               \n");
            sql.append("   AND  A.ISONOFF = 'ON'            \n");
            sql.append("   AND  A.SUBJ = B.SUBJ             \n");
            sql.append("   AND  B.GRCODE = '").append(grcode).append("'     \n");
            sql.append("   AND  B.YEAR = '").append(year).append("'         \n");
            sql.append("   AND  B.SUBJ = '").append(subj).append("'         \n");
            sql.append("   AND  B.SUBJSEQ = '").append(subjseq).append("'   \n");
            sql.append("   AND  C.USERID(+) = '").append(userid).append("'  \n");
            sql.append("   AND  B.YEAR = C.YEAR(+)          \n");
            sql.append("   AND  B.SUBJ = C.SUBJ(+)          \n");
            sql.append("   AND  B.SUBJSEQ = C.SUBJSEQ(+)    \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                dbox = ls.getDataBox();
            }

        } catch (Exception ex) {
            System.out.println("Exception occured : " + ex.getMessage());
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                } catch (Exception e) {
            //                }
            //            }
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
     * 과정별 차시 정보를 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectSubjLessonList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String subj = box.getString("subj");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.mobile.onlineclass.OnlineClassBean selectSubjLessonList (과정별 차시 목록 조회) */\n");
            sql.append("SELECT  SUBJ            \n");
            sql.append("    ,   LESSON          \n");
            sql.append("    ,   SDESC           \n");
            sql.append("  FROM  TZ_SUBJLESSON   \n");
            sql.append(" WHERE  SUBJ = '").append(subj).append("'   \n");
            sql.append(" ORDER  BY LESSON       \n");

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setString(1, subj);
            //
            //            ls = new ListSet(pstmt);

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                } catch (Exception e) {
            //                }
            //            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * 연관 과정 목록을 조회한다. 연관 과정이란 수강 신청을 해 놓은 과정을 의미한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectNextProposeSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String grcode = box.getSession("grcode");
        String subj = box.getString("subj");
        String searchNm = "";
        String[] searchNmArr = null;
        int rnkLimit = 0;

        grcode = grcode.equals("") ? "N000001" : grcode;

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  SEARCH_NM   \n");
            sql.append("  FROM  TZ_SUBJ     \n");
            sql.append(" WHERE  SUBJ = '").append(subj).append("'  \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                searchNm = ls.getString("search_nm");
            }

            ls.close();
            ls = null;

            if (searchNm != null && !searchNm.equals("")) {

                searchNmArr = searchNm.split(",");
                rnkLimit = (searchNmArr.length < 3) ? searchNmArr.length - 1 : 2;

                list = new ArrayList();

                sql.setLength(0);
                sql.append("/* com.credu.mobile.onlineclass.OnlineClassBean selectNextProposeSubjList (과정별 연관과정 목록 조회) */\n");
                sql.append("SELECT  *   \n");
                sql.append("  FROM  (   \n");
                sql.append("        SELECT  A.SUBJ      \n");
                sql.append("            ,   A.YEAR      \n");
                sql.append("            ,   A.SUBJSEQ   \n");
                sql.append("            ,   C.SUBJNM    \n");
                sql.append("            ,   C.INTRODUCEFILENAMENEW                                                      \n");
                sql.append("            ,   TO_CHAR( TO_DATE(A.PROPSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPSTART   \n");
                sql.append("            ,   TO_CHAR( TO_DATE(A.PROPEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPEND       \n");
                sql.append("            ,   TO_CHAR( TO_DATE(A.EDUSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUSTART     \n");
                sql.append("            ,   TO_CHAR( TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUEND         \n");

                for (int i = 0; i < searchNmArr.length; i++) {
                    sql.append("            ,   CASE WHEN INSTR (C.SEARCH_NM, '").append(searchNmArr[i]).append("') > 0 THEN 1 ELSE 0 END RNK").append(i).append(" \n");
                }

                sql.append("          FROM  TZ_SUBJSEQ A                                                                \n");
                sql.append("            ,   TZ_SUBJ C                                                                   \n");
                sql.append("         WHERE  A.GRCODE = '").append(grcode).append("'                                     \n");
                sql.append("           AND  C.SUBJ <> '").append(subj).append("'                                        \n");
                sql.append("           AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN A.PROPSTART AND A.PROPEND          \n");
                sql.append("           AND  A.SUBJ = C.SUBJ                                                             \n");

                sql.append("           AND  (       \n");
                for (int i = 0; i < searchNmArr.length; i++) {
                    if (i == 0) {
                        sql.append("                C.SEARCH_NM LIKE '%' || '").append(searchNmArr[i]).append("' || '%' \n");
                    } else {
                        sql.append("                OR C.SEARCH_NM LIKE '%' || '").append(searchNmArr[i]).append("' || '%' \n");
                    }
                }
                sql.append("                )   \n");
                sql.append("    )   \n");
                sql.append(" WHERE  ( ");

                for (int i = 0; i < searchNmArr.length; i++) {
                    if (i < searchNmArr.length - 1) {
                        sql.append("RNK").append(i).append(" + ");
                    } else {
                        sql.append("RNK").append(i);
                    }
                }
                sql.append(" ) > ").append(rnkLimit).append("   \n");

                sql.append(" ORDER  BY  ");
                for (int i = 0; i < searchNmArr.length; i++) {
                    if (i == 0) {
                        sql.append(" RNK").append(i);
                    } else {
                        sql.append(" || RNK").append(i);
                    }
                }
                sql.append(" DESC   \n");

                //            sql.append("/* com.credu.mobile.onlineclass.OnlineClassBean selectNextProposeSubjList (과정별 다음과정 목록 조회) */\n");
                //            sql.append("SELECT  A.SUBJ                                                                      \n");
                //            sql.append("    ,   C.SUBJNM                                                                    \n");
                //            sql.append("    ,   C.INTRODUCEFILENAMENEW                                                      \n");
                //            sql.append("    ,   TO_CHAR( TO_DATE(A.PROPSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPSTART   \n");
                //            sql.append("    ,   TO_CHAR( TO_DATE(A.PROPEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPEND       \n");
                //            sql.append("    ,   TO_CHAR( TO_DATE(A.EDUSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUSTART     \n");
                //            sql.append("    ,   TO_CHAR( TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUEND         \n");
                //            sql.append("  FROM  TZ_SUBJSEQ A                                                                \n");
                //            sql.append("    ,   TZ_PROPOSE B                                                                \n");
                //            sql.append("    ,   TZ_SUBJ C                                                                   \n");
                //            sql.append(" WHERE  B.USERID = '").append(userid).append("'                                     \n");
                //            sql.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN A.PROPSTART AND A.PROPEND          \n");
                //            sql.append("   AND  A.YEAR = B.YEAR                                                             \n");
                //            sql.append("   AND  A.SUBJ = B.SUBJ                                                             \n");
                //            sql.append("   AND  A.SUBJSEQ = B.SUBJSEQ                                                       \n");
                //            sql.append("   AND  A.SUBJ = B.SUBJ                                                             \n");
                //            sql.append("   AND  A.SUBJ = C.SUBJ                                                             \n");

                //            pstmt = connMgr.prepareStatement(sql.toString());
                //            pstmt.setString(1, userid);
                //
                //            ls = new ListSet(pstmt);

                ls = connMgr.executeQuery(sql.toString());

                while (ls.next()) {
                    dbox = ls.getDataBox();

                    list.add(dbox);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                } catch (Exception e) {
            //                }
            //            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

}
