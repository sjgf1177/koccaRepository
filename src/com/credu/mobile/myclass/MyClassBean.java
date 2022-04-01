package com.credu.mobile.myclass;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.EduEtc1Bean;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;

/**
 * 나의 강의실 관련된 기능을 담당한다.
 * 
 * @author saderaser
 * 
 */
public class MyClassBean {

    /**
     * 지난 과정 목록을 조회한다. 과정차수 기본 설정값이 복습가능 여부, 수료여부, 설정된 복습기간이 지났는지 여부를 같이 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectLastSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;

        DataBox dbox = null;
        ArrayList<DataBox> list = null;

        StringBuffer sql = new StringBuffer();

        int currentListCnt = box.getInt("currentListCnt");

        int startNum = 0, endNum = 0;
        startNum = currentListCnt + 1;
        endNum = currentListCnt + 10;

        String userid = box.getSession("userid");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.mobile.myclass.MyClassBean() selectLastSubjectList (지난 강의 목록 조회 )*/ \n");
            sql.append("SELECT  *                                                                                                            \n");
            sql.append("  FROM  (                                                                                                            \n");
            sql.append("        SELECT  C.SUBJ                                                                                               \n");
            sql.append("            ,   C.SUBJNM                                                                                             \n");
            sql.append("            ,   C.INTRODUCEFILENAMENEW                                                                               \n");
            sql.append("            ,   C.MOBILE_USE_YN                                                                                      \n");
            sql.append("            ,   A.YEAR                                                                                               \n");
            sql.append("            ,   A.SUBJSEQ                                                                                            \n");
            sql.append("            ,   A.PROPSTART                                                                                          \n");
            sql.append("            ,   A.PROPEND                                                                                            \n");
            sql.append("            ,   A.EDUSTART                                                                                           \n");
            sql.append("            ,   A.EDUEND                                                                                             \n");
            // 다음 라인의 쿼리는 기존에 사용하는 쿼리를 참조하였으나 왜 그렇게 되어 있는지 알 수가 없다.
            sql.append("            ,   CASE WHEN ( A.EDUEND < '2010047' AND E.ISGRADUATED IS NULL ) THEN                                    \n");
            sql.append("                    'A'                                                                                              \n");
            sql.append("                    WHEN (E.ISGRADUATED IS NULL ) THEN                                                               \n");
            sql.append("                    'B'                                                                                              \n");
            sql.append("                    ELSE                                                                                             \n");
            sql.append("                    D.ISGRADUATED                                                                                    \n");
            sql.append("                    END AS ISGRADUATED                                                                               \n");
            sql.append("            ,   D.SCORE                                                                                              \n");
            sql.append("            ,   A.ISABLEREVIEW                                                                                       \n");
            sql.append("            ,   A.REVIEWTYPE                                                                                         \n");
            sql.append("            ,   A.REVIEWDAYS                                                                                         \n");
            sql.append("            ,   CASE WHEN C.ISABLEREVIEW = 'Y' THEN                                                                  \n");
            sql.append("                    CASE WHEN A.REVIEWTYPE = 'D' THEN                                                                \n");
            sql.append("                        TO_CHAR(TO_DATE(A.EDUEND, 'YYYYMMDDHH24') + 1, 'YYYY/MM/DD') || ' ~ ' ||                       \n");
            sql.append("                        TO_CHAR( (TO_DATE(A.EDUEND, 'YYYYMMDDHH24') + TO_NUMBER(A.REVIEWDAYS) ), 'YYYY/MM/DD')       \n");
            sql.append("                    WHEN A.REVIEWTYPE = 'W' THEN                                                                     \n");
            sql.append("                        TO_CHAR(TO_DATE(A.EDUEND, 'YYYYMMDDHH24') + 1, 'YYYY/MM/DD') || ' ~ ' ||                       \n");
            sql.append("                        TO_CHAR( (TO_DATE(A.EDUEND, 'YYYYMMDDHH24') + TO_NUMBER(A.REVIEWDAYS) * 7 ), 'YYYY/MM/DD')   \n");
            sql.append("                    WHEN A.REVIEWTYPE = 'M' THEN                                                                     \n");
            sql.append("                        TO_CHAR(TO_DATE(A.EDUEND, 'YYYYMMDDHH24') + 1, 'YYYY/MM/DD') || ' ~ ' ||                       \n");
            sql.append("                        TO_CHAR(ADD_MONTHS(TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), A.REVIEWDAYS), 'YYYY/MM/DD')           \n");
            sql.append("                    WHEN A.REVIEWTYPE = 'Y' THEN                                                                     \n");
            sql.append("                        TO_CHAR(TO_DATE(A.EDUEND, 'YYYYMMDDHH24') + 1, 'YYYY/MM/DD') || ' ~ ' ||                       \n");
            sql.append("                        TO_CHAR(ADD_MONTHS(TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), A.REVIEWDAYS * 12), 'YYYY/MM/DD')      \n");
            sql.append("                    END                                                                                              \n");
            sql.append("                END AS REVIEW_FMT_DATE                                                                               \n");
            sql.append("            ,   CASE WHEN C.ISABLEREVIEW = 'Y' THEN                                                                                     \n");
            sql.append("                    CASE WHEN A.REVIEWTYPE = 'D' THEN                                                                                   \n");
            sql.append("                        CASE WHEN (TO_DATE(A.EDUEND, 'YYYYMMDDHH24') + TO_NUMBER(A.REVIEWDAYS) ) > SYSDATE THEN 'Y' ELSE 'N' END        \n");
            sql.append("                    WHEN A.REVIEWTYPE = 'W' THEN                                                                                        \n");
            sql.append("                        CASE WHEN ( TO_DATE(A.EDUEND, 'YYYYMMDDHH24') + TO_NUMBER( A.REVIEWDAYS) * 7 ) > SYSDATE THEN 'Y' ELSE 'N' END  \n");
            sql.append("                    WHEN A.REVIEWTYPE = 'M' THEN                                                                                        \n");
            sql.append("                        CASE WHEN ADD_MONTHS(TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), A.REVIEWDAYS) > SYSDATE THEN 'Y' ELSE 'N' END           \n");
            sql.append("                    WHEN A.REVIEWTYPE = 'Y' THEN                                                                                        \n");
            sql.append("                        CASE WHEN ADD_MONTHS(TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), A.REVIEWDAYS * 12) > SYSDATE THEN 'Y' ELSE 'N' END      \n");
            sql.append("                    END                                                                                                                 \n");
            sql.append("                END AS ISPOSSIBLE_BY_DATE                                                                                               \n");
            sql.append("            ,   RANK() OVER( ORDER  BY A.EDUSTART DESC, C.SUBJNM ) AS RNK                                            \n");
            sql.append("            ,   COUNT(C.SUBJ) OVER() AS TOT_CNT                                                                      \n");
            sql.append("          FROM  TZ_SUBJSEQ A                                \n");
            sql.append("            ,   TZ_PROPOSE B                                \n");
            sql.append("            ,   TZ_SUBJ C                                   \n");
            sql.append("            ,   TZ_STUDENT D                                \n");
            sql.append("            ,   TZ_STOLD E                                  \n");
            sql.append("         WHERE  B.USERID = '").append(userid).append("'     \n");
            sql.append("           AND  NVL(D.EDUSTART,A.EDUSTART) < TO_CHAR(SYSDATE, 'YYYYMMDDHH24')  \n");
            sql.append("           AND  B.YEAR = A.YEAR                             \n");
            sql.append("           AND  B.SUBJ = A.SUBJ                             \n");
            sql.append("           AND  B.SUBJSEQ = A.SUBJSEQ                       \n");
            sql.append("           AND  B.SUBJ = C.SUBJ                             \n");
            sql.append("           AND  B.YEAR = D.YEAR                             \n");
            sql.append("           AND  B.SUBJ = D.SUBJ                             \n");
            sql.append("           AND  B.SUBJSEQ = D.SUBJSEQ                       \n");
            sql.append("           AND  B.USERID = D.USERID                         \n");
            sql.append("           AND  B.YEAR = E.YEAR(+)                          \n");
            sql.append("           AND  B.SUBJ = E.SUBJ(+)                          \n");
            sql.append("           AND  B.SUBJSEQ = E.SUBJSEQ(+)                    \n");
            sql.append("           AND  B.USERID = E.USERID(+)                      \n");
            sql.append("        )                                                   \n");
            sql.append(" WHERE  RNK BETWEEN ").append(startNum).append(" AND ").append(endNum).append(" \n");
            sql.append(" ORDER  BY RNK                                              \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);

            }

        } catch (Exception e) {
            Log.err.println(box.getSession("userid"), sql.toString(), e.getMessage());
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("Sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e) {
                }
            }
        }

        return list;
    }

    /**
     * 복습 과정 기본 정보를 조회한다. 과정명과 복습기간을 조회한다.
     * 
     * @param box
     * @return
     */
    public DataBox selectReviewSubjectInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;

        DataBox dbox = null;

        StringBuffer sql = new StringBuffer();
        String subj = box.getString("subj");
        String year = box.getString("year");
        String subjseq = box.getString("subjseq");
        // String userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.mobile.myclass.MyClassBean() selectReviewSubjectInfo (복습과정 기본정보 조회)*/\n");
            sql.append("SELECT  A.SUBJ                                                                                              \n");
            sql.append("    ,   A.SUBJNM                                                                                            \n");
            sql.append("    ,   CASE WHEN B.ISABLEREVIEW = 'Y' THEN                                                                 \n");
            sql.append("            CASE WHEN B.REVIEWTYPE = 'D' THEN                                                               \n");
            sql.append("                TO_CHAR(TO_DATE(B.EDUEND, 'YYYYMMDDHH24') + 1, 'YYYY/MM/DD') || ' ~ ' ||                    \n");
            sql.append("                TO_CHAR( (TO_DATE(B.EDUEND, 'YYYYMMDDHH24') + TO_NUMBER(B.REVIEWDAYS) ), 'YYYY/MM/DD')      \n");
            sql.append("            WHEN A.REVIEWTYPE = 'W' THEN                                                                    \n");
            sql.append("                TO_CHAR(TO_DATE(B.EDUEND, 'YYYYMMDDHH24') + 1, 'YYYY/MM/DD') || ' ~ ' ||                    \n");
            sql.append("                TO_CHAR( (TO_DATE(B.EDUEND, 'YYYYMMDDHH24') + TO_NUMBER(B.REVIEWDAYS) * 7 ), 'YYYY/MM/DD')  \n");
            sql.append("            WHEN A.REVIEWTYPE = 'M' THEN                                                                    \n");
            sql.append("                TO_CHAR(TO_DATE(B.EDUEND, 'YYYYMMDDHH24') + 1, 'YYYY/MM/DD') || ' ~ ' ||                    \n");
            sql.append("                TO_CHAR(ADD_MONTHS(TO_DATE(B.EDUEND, 'YYYYMMDDHH24'), B.REVIEWDAYS), 'YYYY/MM/DD')          \n");
            sql.append("            WHEN A.REVIEWTYPE = 'Y' THEN                                                                    \n");
            sql.append("                TO_CHAR(TO_DATE(B.EDUEND, 'YYYYMMDDHH24') + 1, 'YYYY/MM/DD') || ' ~ ' ||                    \n");
            sql.append("                TO_CHAR(ADD_MONTHS(TO_DATE(B.EDUEND, 'YYYYMMDDHH24'), B.REVIEWDAYS * 12), 'YYYY/MM/DD')     \n");
            sql.append("            END                                                                                             \n");
            sql.append("        END AS REVIEW_FMT_DATE                                                                              \n");
            sql.append("  FROM  TZ_SUBJ A       \n");
            sql.append("    ,   TZ_SUBJSEQ B    \n");
            sql.append(" WHERE  B.SUBJ = '").append(subj).append("' \n");
            sql.append("   AND  B.YEAR = '").append(year).append("' \n");
            sql.append("   AND  B.SUBJSEQ = '").append(subjseq).append("' \n");
            sql.append("   AND  B.SUBJ = A.SUBJ \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                dbox = ls.getDataBox();
            }

        } catch (Exception e) {
            Log.err.println(box.getSession("userid"), sql.toString(), e.getMessage());
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("Sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e) {
                }
            }
        }

        return dbox;
    }

    /**
     * 복습 과정의 차시 정보를 조회한다. 차시명 및 URL 정보를 가져온다.
     * 
     * @param box
     * @return
     */
    public ArrayList<DataBox> selectLessonList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;

        DataBox dbox = null;
        ArrayList<DataBox> list = null;

        StringBuffer sql = new StringBuffer();
        String subj = box.getString("subj");
        String year = box.getString("year");
        String subjseq = box.getString("subjseq");
        String userid = box.getSession("userid");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.mobile.myclass.MyClassBean() selectLessonList (과정별 차시 정보 조회 )*/ \n");
            sql.append("SELECT  A.SUBJ                                          \n");
            sql.append("    ,   A.LESSON                                        \n");
            sql.append("    ,   A.MODULE                                        \n");
            sql.append("    ,   A.SDESC                                         \n");
            sql.append("    ,   A.STARTING                                      \n");
            sql.append("    ,   A.MOBILE_URL                                    \n");
            sql.append("    ,   A.NEW_MOBILE_URL                                \n");
            sql.append("    ,   DECODE( B.LESSON, NULL, 'N', 'Y') AS STUDY_YN   \n");
            sql.append("  FROM  TZ_SUBJLESSON A                                 \n");
            sql.append("    ,   TZ_PROGRESS B                                   \n");
            sql.append(" WHERE  A.SUBJ = '").append(subj).append("'             \n");
            sql.append("   AND  B.YEAR(+) = '").append(year).append("'          \n");
            sql.append("   AND  B.SUBJSEQ(+) = '").append(subjseq).append("'    \n");
            sql.append("   AND  B.USERID(+) = '").append(userid).append("'      \n");
            sql.append("   AND  A.SUBJ = B.SUBJ(+)                              \n");
            sql.append("   AND  A.LESSON = B.LESSON(+)                          \n");
            sql.append(" ORDER  BY A.LESSON                                     \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception e) {
            Log.err.println(box.getSession("userid"), sql.toString(), e.getMessage());
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("Sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e) {
                }
            }
        }

        return list;
    }

    /**
     * 다음 강의 목록을 조회한다. 현재일이 수강신청 기간 사이에 있는 차수의 수강신청한 과정 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectNextSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        DataBox dbox = null;
        ArrayList<DataBox> list = null;

        StringBuffer sql = new StringBuffer();
        String userid = box.getSession("userid");
        String grcode = box.getSession("grcode");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.mobile.myclass.MyClassBean selectNextSubjectList (다음 강의 목록 조회) */\n");
            sql.append("SELECT                                                                          \n");
            sql.append("        A.SUBJ                                                                  \n");
            sql.append("    ,   A.YEAR                                                                  \n");
            sql.append("    ,   A.SUBJSEQ                                                               \n");
            sql.append("    ,   A.SUBJNM                                                                \n");
            sql.append("    ,   TO_CHAR(TO_DATE(A.EDUSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUSTART  \n");
            sql.append("    ,   TO_CHAR(TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUEND      \n");
            sql.append("    ,   B.APPDATE                                                               \n");
            sql.append("    ,   B.TID                                                                   \n");
            sql.append("    ,   C.INTRODUCEFILENAMENEW                                                  \n");
            sql.append("    ,   C.MOBILE_USE_YN                                                         \n");
            sql.append("  FROM  TZ_SUBJSEQ A                                                            \n");
            sql.append("    ,   TZ_PROPOSE B                                                            \n");
            sql.append("    ,   TZ_SUBJ C                                                               \n");
            sql.append(" WHERE  B.USERID = '").append(userid).append("'                                 \n");
            sql.append("   AND  A.GRCODE = '").append(grcode).append("'                                 \n");
            sql.append("   AND  B.CHKFINAL = 'Y'                                                        \n");
            sql.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN A.PROPSTART AND A.PROPEND      \n");
            sql.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') NOT BETWEEN A.EDUSTART AND A.EDUEND    \n");
            sql.append("   AND  A.SUBJ = B.SUBJ                                                         \n");
            sql.append("   AND  A.YEAR = B.YEAR                                                         \n");
            sql.append("   AND  A.SUBJSEQ = B.SUBJSEQ                                                   \n");
            sql.append("   AND  A.SUBJ = C.SUBJ                                                         \n");
            sql.append(" ORDER  BY A.SUBJNM                                                             \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception e) {
            Log.err.println(box.getSession("userid"), sql.toString(), e.getMessage());
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("Sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e) {
                }
            }
        }

        return list;
    }

    /**
     * 정규과정 찜한 강의 목록을 조회한다. 찜한 강의 목록에 있더라도 폐강된 과정은 조회하지 않는다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectFavorSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        DataBox dbox = null;
        ArrayList<DataBox> list = null;

        StringBuffer sql = new StringBuffer();

        String userid = box.getSession("userid");
        String grcode = box.getSession("grcode");
        String step = box.getStringDefault("step", "step1");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.mobile.myclass.MyClassBean selectFavorSubjectList (찜한 강의 목록 조회) */\n");
            sql.append("SELECT                                                                                      \n");
            sql.append("        V.*                                                                                 \n");
            sql.append("    ,   DECODE(D.USERID, NULL, 'N', 'Y') AS APPLY_YN                                        \n");
            sql.append("  FROM  (                                                                                   \n");
            sql.append("        SELECT                                                                              \n");
            sql.append("                A.USERID                                                                    \n");
            sql.append("            ,   B.SUBJNM                                                                    \n");
            sql.append("            ,   B.SUBJ                                                                      \n");
            sql.append("            ,   NVL(B.MOBILE_USE_YN, 'N') AS MOBILE_USE_YN                                  \n");
            sql.append("            ,   B.MOBILE_PREURL                                                             \n");
            sql.append("            ,   C.YEAR                                                                      \n");
            sql.append("            ,   C.SUBJSEQ                                                                   \n");
            sql.append("            ,   B.INTRODUCEFILENAMENEW                                                      \n");
            sql.append("            ,   TO_CHAR( TO_DATE( C.PROPSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPSTART  \n");
            sql.append("            ,   TO_CHAR( TO_DATE( C.PROPEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS PROPEND      \n");
            sql.append("            ,   TO_CHAR( TO_DATE( C.EDUSTART, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUSTART    \n");
            sql.append("            ,   TO_CHAR( TO_DATE( C.EDUEND, 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUEND        \n");
            sql.append("            ,   (                                                                           \n");
            sql.append("                SELECT  NEW_MOBILE_URL                                                      \n");
            sql.append("                  FROM  TZ_SUBJLESSON                                                       \n");
            sql.append("                 WHERE  SUBJ = A.SUBJ                                                       \n");
            sql.append("                   AND  LESSON = '001'                                                      \n");
            sql.append("                ) AS MOBILE_PREURL2                                                         \n");
            sql.append("            ,   RANK() OVER( ORDER BY A.REG_DT DESC, B.SUBJNM ASC) AS RNK                   \n");
            sql.append("            ,   COUNT(A.SUBJ) OVER() AS TOT_CNT                                             \n");
            sql.append("          FROM  TZ_SUBJ_FAVOR A                                                             \n");
            sql.append("            ,   TZ_SUBJ B                                                                   \n");
            sql.append("            ,   TZ_SUBJSEQ C                                                                \n");
            sql.append("         WHERE  A.GRCODE = '").append(grcode).append("'                                     \n");
            sql.append("           AND  A.USERID = '").append(userid).append("'                                     \n");
            sql.append("           AND  A.CLASS_TYPE = '01'                                                         \n");
            sql.append("           AND  B.ISUSE = 'Y'                                                               \n");
            sql.append("           AND  A.SUBJ = B.SUBJ                                                             \n");
            sql.append("           AND  A.SUBJ = C.SUBJ                                                             \n");
            sql.append("           AND  A.GRCODE = C.GRCODE                                                         \n");
            sql.append("           AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN C.PROPSTART AND C.PROPEND          \n");
            sql.append("        ) V                         \n");
            sql.append("    ,   TZ_PROPOSE D                \n");
            sql.append(" WHERE  V.USERID = D.USERID(+)      \n");
            sql.append("   AND  V.SUBJ = D.SUBJ(+)          \n");
            sql.append("   AND  V.YEAR = D.YEAR(+)          \n");
            sql.append("   AND  V.SUBJSEQ = D.SUBJSEQ(+)    \n");
            if (step.equals("step1")) {
                sql.append("   AND  RNK < 11    \n");

            } else if (step.equals("step2")) {
                sql.append("   AND  RNK < 21    \n");

            } else if (step.equals("step3")) {
                sql.append("   AND  RNK < 31    \n");

            }
            sql.append(" ORDER  BY RNK  \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception e) {
            Log.err.println(box.getSession("userid"), sql.toString(), e.getMessage());
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("Sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e) {
                }
            }
        }

        return list;
    }

    /**
     * 현재 수강중인 과정 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectStudySubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        DataBox dbox = null;
        ArrayList<DataBox> list = null;

        StringBuffer sql = new StringBuffer();

        String grcode = box.getSession("grcode");
        String userid = box.getSession("userid");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* com.credu.mobile.myclass.MyClassBean selectStudySujectList (수강중인 과정 목록 조회) */\n");
            sql.append("SELECT  C.SUBJ                                                                  \n");
            sql.append("    ,   C.SUBJNM                                                                \n");
            sql.append("    ,   NVL(C.MOBILE_USE_YN, 'N') AS MOBILE_USE_YN                              \n");
            sql.append("    ,   C.MOBILE_PREURL                                                         \n");
            sql.append("    ,   C.INTRODUCEFILENAMENEW                                                  \n");
            sql.append("    ,   TO_CHAR( TO_DATE(NVL(D.EDUSTART,A.EDUSTART), 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUSTART  \n");
            sql.append("    ,   TO_CHAR( TO_DATE(NVL(D.EDUEND, A.EDUEND ), 'YYYYMMDDHH24'), 'YYYY/MM/DD') AS EDUEND      \n");
            sql.append("    ,   A.GRCODE                                                                \n");
            sql.append("    ,   A.GRSEQ                                                                 \n");
            sql.append("    ,   A.YEAR                                                                  \n");
            sql.append("    ,   A.SUBJSEQ                                                               \n");
            sql.append("  FROM  TZ_SUBJSEQ A                                                            \n");
            sql.append("    ,   TZ_PROPOSE B                                                            \n");
            sql.append("    ,   TZ_SUBJ C                                                               \n");
            sql.append("    ,   TZ_STUDENT D                                                            \n");
            sql.append(" WHERE  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN NVL(D.EDUSTART,A.EDUSTART) AND NVL(D.EDUEND, A.EDUEND )        \n");
            sql.append("   AND  A.GRCODE = '").append(grcode).append("'                                 \n");
            sql.append("   AND  B.USERID = '").append(userid).append("'                                 \n");
            sql.append("   AND  B.CHKFINAL = 'Y'                                                        \n");
            sql.append("   AND  C.ISUSE = 'Y'                                                           \n");
            sql.append("   AND  A.SUBJ = B.SUBJ                                                         \n");
            sql.append("   AND  A.YEAR = B.YEAR                                                         \n");
            sql.append("   AND  A.SUBJSEQ = B.SUBJSEQ                                                   \n");
            sql.append("   AND  A.SUBJ = C.SUBJ                                                         \n");
            sql.append("   AND  B.USERID = D.USERID                                                     \n");
            sql.append("   AND  B.SUBJ = D.SUBJ                                                         \n");
            sql.append("   AND  B.SUBJSEQ = D.SUBJSEQ                                                   \n");
            sql.append("   AND  B.YEAR = D.YEAR                                     					\n");
            
            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception e) {
            Log.err.println(box.getSession("userid"), sql.toString(), e.getMessage());
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("Sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e) {
                }
            }
        }

        return list;
    }

    public static String encodeURIComponent(String s)
    {
      String result = null;
   
      try
      {
        result = URLEncoder.encode(s, "UTF-8")
                           .replaceAll("\\+", "%20")
                           .replaceAll("\\%21", "!")
                           .replaceAll("\\%27", "'")
                           .replaceAll("\\%28", "(")
                           .replaceAll("\\%29", ")")
                           .replaceAll("\\%7E", "~");
      }
   
      // This exception should never occur.
      catch (UnsupportedEncodingException e)
      {
        result = s;
      }
   
      return result;
    } 
  
    /**
     * 권장진도율 ( 권장진도율=(현재까지 일수*100)/전체학습기간일수 )
     * 
     * @param box receive from the form object and session
     * @return String 권장진도율
     */
    public String selectRecommendProgress(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        float percent = (float) 0.0;
        String result = "0";
        String today = "";
        String eduStart = "";
        String eudEnd = "";
        String subjnm = "";
        int diffToToday = 0;
        int diffWholeDay = 0;

        String subj = box.getString("subj");
        String year = box.getString("year");
        String subjseq = box.getString("subjseq");
        
        try {

            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.mobile.myclass.MyClassBean() selectRecommendProgress (권장진도율 조회 )*/ \n");
            sql.append("SELECT  \n");
            sql.append("        TO_CHAR(SYSDATE,'YYYYMMDD') TODAY   \n");
            sql.append("    ,   SUBSTRING(EDUSTART,1,8) EDUSTART    \n");
            sql.append("    ,   SUBSTRING(EDUEND,1,8) EDUEND        \n");
            sql.append("  FROM  TZ_SUBJSEQ \n");
            sql.append(" WHERE  SUBJ = '").append(subj).append("'   \n");
            sql.append("   AND  YEAR = '").append(year).append("'    \n");
            sql.append("   AND  SUBJSEQ = '").append(subjseq).append("' \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                today = ls.getString("today");
                eduStart = ls.getString("edustart");
                eudEnd = ls.getString("eduend");
                diffToToday = FormatDate.datediff("date", eduStart, today);
                diffWholeDay = FormatDate.datediff("date", eduStart, eudEnd);
                
                if (diffWholeDay != 0) {
                    percent = (float) ((diffToToday * 100) / (float) diffWholeDay);
                    if (percent <= 0.0) {
                        percent = 0;
                    } else if (percent > 100.0) {
                        percent = 100;
                    }

                    result = new DecimalFormat("0.00").format(percent);
                }
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

        return result;
    }

    /**
     * 평균진도율
     * 
     * @param box receive from the form object and session
     * @return String 평균진도율
     */
    public String selectAverageProgress(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        float percent = (float) 0.0;
        String result = "0";

        String subj = box.getString("subj");
        String year = box.getString("year");
        String subjseq = box.getString("subjseq");
        try {
            connMgr = new DBConnectionManager();

            sql.append("/* selectAverageProgress (평균 진도율 조회) */ \n");
            sql.append("SELECT  ISNULL(AVG(TSTEP),0.0) AS TSTEP \n");
            sql.append("  FROM  TZ_STUDENT                      \n");
            sql.append(" WHERE SUBJ = '").append(subj).append("'\n");
            sql.append("   AND YEAR = '").append(year).append("'\n");
            sql.append("   AND SUBJSEQ = '").append(subjseq).append("'\n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                percent = ls.getFloat("tstep");
                result = String.valueOf(percent);
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
        return result;
    }

    /**
     * 자기진도율
     * 
     * @param box receive from the form object and session
     * @return String 자기진도율
     */
    public String selectMyProgress(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        float percent = (float) 0.0;
        String result = "0";

        String subj = box.getString("subj");
        String year = box.getString("year");
        String subjseq = box.getString("subjseq");
        String userid = box.getSession("userid");

        try {

            connMgr = new DBConnectionManager();

            // CalcUtil.getInstance().calc_score(connMgr, CalcUtil.ALL, subj, year, subjseq, userid);

            sql.append("/* com.credu.mobile.myclass.MyClassBean() selectMyProgress (학습자 진도율 조회 )*/ \n");
            sql.append("SELECT  TSTEP       \n");
            sql.append("  FROM  TZ_STUDENT  \n");
            sql.append(" WHERE  SUBJ = '").append(subj).append("'\n");
            sql.append("   AND  YEAR = '").append(year).append("'\n");
            sql.append("   AND  SUBJSEQ = '").append(subjseq).append("'\n");
            sql.append("   AND  USERID = '").append(userid).append("'\n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                percent = ls.getFloat("tstep");
                result = String.valueOf(percent);
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

        return result;
    }

    /**
     * 점수 정보를 갱신한다. TZ_STUDENT 테이블에 자료가 있다.
     */
    public int updateProgressRateInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        String subj = box.getString("subj");
        String year = box.getString("year");
        String subjseq = box.getString("subjseq");
        String userid = box.getSession("userid");
        String grcode = box.getSession("grcode");

        int lessonCnt = 0;
        int studyCnt = 0;
        double gradStep = 0; // 수료기준 진도율
        double progStep = 0; // 학습 진도율

        int resultCnt = 0;
        //        int idx = 1;

        String gradeYn = "N";

        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("/* com.credu.mobile.myclass.MyClassBean() updateProgressRateInfo(진도율 갱신을 위한 기본 정보 조회) */ \n");
            sql.append("SELECT  V1.*                                                        \n");
            sql.append("    ,   V2.LESSON_CNT                                               \n");
            sql.append("    ,   V2.STUDY_CNT                                                \n");
            sql.append("  FROM  (                                                           \n");
            sql.append("        SELECT  SUBJ                                                \n");
            sql.append("            ,   YEAR                                                \n");
            sql.append("            ,   SUBJSEQ                                             \n");
            sql.append("            ,   GRADSTEP                                            \n");
            sql.append("            ,   GRADSCORE                                           \n");
            sql.append("          FROM  TZ_SUBJSEQ                                          \n");
            sql.append("         WHERE  GRCODE = '").append(grcode).append("'               \n");
            sql.append("           AND  SUBJ = '").append(subj).append("'                   \n");
            sql.append("           AND  YEAR = '").append(year).append("'                   \n");
            sql.append("           AND  SUBJSEQ = '").append(subjseq).append("'             \n");
            sql.append("        ) V1,                                                       \n");
            sql.append("        (                                                           \n");
            sql.append("        SELECT  A.SUBJ                                              \n");
            sql.append("            ,   COUNT(A.LESSON) AS LESSON_CNT                       \n");
            sql.append("            ,   SUM( DECODE( B.LESSON , NULL, 0 , 1 )) AS STUDY_CNT \n");
            sql.append("          FROM  TZ_SUBJLESSON A                                     \n");
            sql.append("            ,   TZ_PROGRESS B                                       \n");
            sql.append("         WHERE  A.SUBJ(+) = '").append(subj).append("'              \n");
            sql.append("           AND  B.YEAR(+) = '").append(year).append("'              \n");
            sql.append("           AND  B.SUBJSEQ(+) = '").append(subjseq).append("'        \n");
            sql.append("           AND  B.USERID(+) = '").append(userid).append("'          \n");
            sql.append("           AND  A.SUBJ = B.SUBJ(+)                                  \n");
            sql.append("           AND  A.LESSON = B.LESSON(+)                              \n");
            sql.append("         GROUP  BY A.SUBJ                                           \n");
            sql.append("        ) V2                                                        \n");
            sql.append(" WHERE  V1.SUBJ = V2.SUBJ                                           \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                lessonCnt = ls.getInt("lesson_cnt");
                studyCnt = ls.getInt("study_cnt");
                gradStep = ls.getDouble("gradstep");
            }

            if (studyCnt > 0) {
                progStep = (double) Math.round((double) studyCnt / lessonCnt * 100 * 100) / 100;
            }

            if (progStep >= gradStep) { // 학습한 진도율이 수료기준 진도율보다 높을 경우 수료여부 값을 'Y'로 설정
                gradeYn = "Y";
            }
            ls.close();

            sql.setLength(0);
            sql.append("/* com.credu.mobile.myclass.MyClassBean() updateProgressRateInfo (진도율 정보 갱신)*/\n");
            sql.append("UPDATE  TZ_STUDENT  \n");

            //            sql.append("   SET  SCORE = ?   \n");
            //            sql.append("    ,   TSTEP = ?   \n");
            //            sql.append("    ,   AVTSTEP = ? \n");
            //            sql.append("    ,   ISGRADUATED = ? \n");
            //            sql.append("    ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            //            sql.append(" WHERE  USERID = ?  \n");
            //            sql.append("   AND  SUBJ = ?    \n");
            //            sql.append("   AND  YEAR = ?    \n");
            //            sql.append("   AND  SUBJSEQ = ? \n");
            sql.append("   SET  SCORE = ").append(progStep).append("   \n");
            sql.append("    ,   TSTEP = ").append(progStep).append("   \n");
            sql.append("    ,   AVTSTEP = ").append(progStep).append(" \n");
            sql.append("    ,   ISGRADUATED = '").append(gradeYn).append("' \n");
            sql.append("    ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            sql.append(" WHERE  USERID = '").append(userid).append("'  \n");
            sql.append("   AND  SUBJ = '").append(subj).append("'    \n");
            sql.append("   AND  YEAR = '").append(year).append("'    \n");
            sql.append("   AND  SUBJSEQ = '").append(subjseq).append("' \n");

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            
            //            pstmt.setDouble(idx++, progStep);
            //            pstmt.setDouble(idx++, progStep);
            //            pstmt.setDouble(idx++, progStep);
            //            pstmt.setString(idx++, gradeYn);
            //            pstmt.setString(idx++, userid);
            //            pstmt.setString(idx++, subj);
            //            pstmt.setString(idx++, year);
            //            pstmt.setString(idx++, subjseq);
            //
            //            resultCnt = pstmt.executeUpdate();

            resultCnt = connMgr.executeUpdate(sql.toString());

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e10) {
                }
            }
            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                    pstmt = null;
            //                } catch (Exception e10) {
            //                }
            //            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return resultCnt;
    }

    /**
     * 학습창 혹은 콘텐츠를 열 때, 학습 이력 정보를 등록/갱신한다.
     * 
     * @param box
     * @return
     */
    public int updateProgressStart(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        String subj = box.getString("subj");
        String year = box.getString("year");
        String subjseq = box.getString("subjseq");
        String lesson = box.getString("lesson");
        String userid = box.getSession("userid");
        String oid = box.getStringDefault("oid", "1");
        // String grcode = box.getSession("grcode");

        int studyCnt = 0;
        int resultCnt = 0;
        //        int index = 1;
        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("/* com.credu.mobile.myclass.MyClassBean() updateProgressStart (기존 학습 여부 체크)*/\n");
            sql.append("SELECT  COUNT(LESSON) AS STUDY_CNT              \n");
            sql.append("  FROM  TZ_PROGRESS                             \n");
            sql.append(" WHERE  SUBJ = '").append(subj).append("'       \n");
            sql.append("   AND  YEAR = '").append(year).append("'       \n");
            sql.append("   AND  SUBJSEQ = '").append(subjseq).append("' \n");
            sql.append("   AND  USERID = '").append(userid).append("'   \n");
            sql.append("   AND  LESSON = '").append(lesson).append("'   \n");

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                studyCnt = ls.getInt("study_cnt");
            }
            ls.close();
            ls = null;

            if (studyCnt > 0) {
                sql.setLength(0);
                //                sql.append("UPDATE  TZ_PROGRESS                                     \n");
                //                sql.append("   SET  LESSON = LESSON + 1                             \n");
                //                sql.append("    ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
                //                sql.append(" WHERE  SUBJ = '?       \n");
                //                sql.append("   AND  YEAR = ?        \n");
                //                sql.append("   AND  SUBJSEQ = ?     \n");
                //                sql.append("   AND  USERID = ?      \n");
                //                sql.append("   AND  LESSON = ?      \n");
                //
                //                pstmt = connMgr.prepareStatement(sql.toString());
                //                pstmt.setString(index++, subj);
                //                pstmt.setString(index++, year);
                //                pstmt.setString(index++, subjseq);
                //                pstmt.setString(index++, userid);
                //                pstmt.setString(index++, lesson);
                
                sql.append("/* com.credu.mobile.myclass.MyClassBean() updateProgressStart (차시 진도정보 갱신)*/\n");
                sql.append("UPDATE  TZ_PROGRESS                                     \n");
                sql.append("   SET  LESSON_COUNT = LESSON_COUNT + 1                 \n");
                sql.append("    ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
                sql.append(" WHERE  SUBJ = '").append(subj).append("'               \n");
                sql.append("   AND  YEAR = '").append(year).append("'               \n");
                sql.append("   AND  SUBJSEQ = '").append(subjseq).append("'         \n");
                sql.append("   AND  USERID = '").append(userid).append("'           \n");
                sql.append("   AND  LESSON = '").append(lesson).append("'           \n");

            } else {
                sql.setLength(0);
                //                sql.append("INSERT  INTO    TZ_PROGRESS (                   \n");
                //                sql.append("            SUBJ                                \n");
                //                sql.append("        ,   YEAR                                \n");
                //                sql.append("        ,   SUBJSEQ                             \n");
                //                sql.append("        ,   LESSON                              \n");
                //                sql.append("        ,   OID                                 \n");
                //                sql.append("        ,   USERID                              \n");
                //                sql.append("        ,   SESSION_TIME                        \n");
                //                sql.append("        ,   TOTAL_TIME                          \n");
                //                sql.append("        ,   FIRST_EDU                           \n");
                //                sql.append("        ,   FIRST_END                           \n");
                //                sql.append("        ,   LESSON_COUNT                        \n");
                //                sql.append("        ,   LDATE                               \n");
                //                sql.append(" ) VALUES (                                     \n");
                //                sql.append("            ?                                   \n");
                //                sql.append("        ,   ?                                   \n");
                //                sql.append("        ,   ?                                   \n");
                //                sql.append("        ,   ?                                   \n");
                //                sql.append("        ,   ?                                   \n");
                //                sql.append("        ,   ?                                   \n");
                //                sql.append("        ,   '00:00:00.00'                       \n");
                //                sql.append("        ,   '00:00:00.00'                       \n");
                //                sql.append("        ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') \n");
                //                sql.append("        ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') \n");
                //                sql.append("        ,   1                                   \n");
                //                sql.append("        ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') \n");
                //                sql.append(")                                               \n");
                //                pstmt = connMgr.prepareStatement(sql.toString());
                //                pstmt.setString(index++, subj);
                //                pstmt.setString(index++, year);
                //                pstmt.setString(index++, subjseq);
                //                pstmt.setString(index++, lesson);
                //                pstmt.setString(index++, oid);
                //                pstmt.setString(index++, userid);

                sql.append("/* com.credu.mobile.myclass.MyClassBean() updateProgressStart (차시 진도정보 입력)*/\n");
                sql.append("INSERT  INTO    TZ_PROGRESS (                   \n");
                sql.append("            SUBJ                                \n");
                sql.append("        ,   YEAR                                \n");
                sql.append("        ,   SUBJSEQ                             \n");
                sql.append("        ,   LESSON                              \n");
                sql.append("        ,   OID                                 \n");
                sql.append("        ,   USERID                              \n");
                sql.append("        ,   SESSION_TIME                        \n");
                sql.append("        ,   TOTAL_TIME                          \n");
                sql.append("        ,   FIRST_EDU                           \n");
                sql.append("        ,   FIRST_END                           \n");
                sql.append("        ,   LESSON_COUNT                        \n");
                sql.append("        ,   LDATE                               \n");
                sql.append(" ) VALUES (                                     \n");
                sql.append("            '").append(subj).append("'          \n");
                sql.append("        ,   '").append(year).append("'          \n");
                sql.append("        ,   '").append(subjseq).append("'       \n");
                sql.append("        ,   '").append(lesson).append("'        \n");
                sql.append("        ,   '").append(oid).append("'           \n");
                sql.append("        ,   '").append(userid).append("'        \n");
                sql.append("        ,   '00:00:00.00'                       \n");
                sql.append("        ,   '00:00:00.00'                       \n");
                sql.append("        ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') \n");
                sql.append("        ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') \n");
                sql.append("        ,   1                                   \n");
                sql.append("        ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') \n");
                sql.append(")                                               \n");

            }

            //            resultCnt = pstmt.executeUpdate();
            resultCnt = connMgr.executeUpdate(sql.toString());

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e10) {
                }
            }
            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                    pstmt = null;
            //                } catch (Exception e10) {
            //                }
            //            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return resultCnt;
    }

    /**
     * 학습창 혹은 콘텐츠를 닫을 때, 학습 이력 정보를 추가 등록/갱신한다.
     * 
     * @param box
     * @return
     */
    public int updateProgressEnd(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        String subj = box.getString("subj");
        String year = box.getString("year");
        String subjseq = box.getString("subjseq");
        String lesson = box.getString("lesson");
        String userid = box.getSession("userid");
        String oid = box.getStringDefault("oid", "1");
        String userip = box.getString("userip");

        String ldate = "";
        String nowTime = "";
        String sessionTime = "";
        String totalTime = "";

        int resultCnt = 0;
        //        int index = 1;
        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* com.credu.mobile.myclass.MyClassBean() updateProgressEnd (차시 진도정보 갱신 - 종료시점)*/\n");
            sql.append("SELECT                                                   \n");
            sql.append("        TOTAL_TIME                                       \n");
            sql.append("    ,   LDATE                                            \n");
            sql.append("    ,   TO_CHAR (SYSDATE, 'YYYYMMDDHH24MISS') NOWTIME    \n");
            sql.append("    ,   OID                                              \n");
            sql.append("  FROM  TZ_PROGRESS                                      \n");
            sql.append(" WHERE  SUBJ =    '").append(subj).append("'             \n");
            sql.append("   AND  YEAR =    '").append(year).append("'             \n");
            sql.append("   AND  SUBJSEQ = '").append(subjseq).append("'          \n");
            sql.append("   AND  USERID =  '").append(userid).append("'           \n");
            sql.append("   AND  LESSON =  '").append(lesson).append("'           \n");
            sql.append("   AND  FIRST_END IS NOT NULL                            \n");

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                ldate = ls.getString("ldate");
                nowTime = ls.getString("nowtime");
                totalTime = ls.getString("total_time");

                sessionTime = EduEtc1Bean.get_duringtime(ldate, nowTime);
                totalTime = EduEtc1Bean.add_duringtime(totalTime, sessionTime);

            }
            ls.close();
            ls = null;

            sql.setLength(0);
            //            sql.append("UPDATE  TZ_PROGRESS                                     \n");
            //            sql.append("   SET  SESSION_TIME = ?                                \n");
            //            sql.append("    ,   TOTAL_TIME = ?                                  \n");
            //            sql.append("    ,   FIRST_END = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')\n");
            //            sql.append("    ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            //            sql.append(" WHERE  SUBJ = ?       \n");
            //            sql.append("   AND  YEAR = ?        \n");
            //            sql.append("   AND  SUBJSEQ = ?     \n");
            //            sql.append("   AND  USERID = ?      \n");
            //            sql.append("   AND  LESSON = ?      \n");
            //
            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setString(index++, sessionTime);
            //            pstmt.setString(index++, totalTime);
            //            pstmt.setString(index++, subj);
            //            pstmt.setString(index++, year);
            //            pstmt.setString(index++, subjseq);
            //            pstmt.setString(index++, userid);
            //            pstmt.setString(index++, lesson);
            //            resultCnt = pstmt.executeUpdate();

            sql.append("UPDATE  TZ_PROGRESS                                         \n");
            sql.append("   SET  SESSION_TIME = '").append(sessionTime).append("'    \n");
            sql.append("    ,   TOTAL_TIME = '").append(totalTime).append("'        \n");
            sql.append("    ,   FIRST_END = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            sql.append("    ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')        \n");
            sql.append(" WHERE  SUBJ = '").append(subj).append("'                   \n");
            sql.append("   AND  YEAR = '").append(year).append("'                   \n");
            sql.append("   AND  SUBJSEQ = '").append(subjseq).append("'             \n");
            sql.append("   AND  USERID = '").append(userid).append("'               \n");
            sql.append("   AND  LESSON = '").append(lesson).append("'               \n");

            resultCnt = connMgr.executeUpdate(sql.toString());

            if (resultCnt > 0) {
                //                index = 1;
                sql.setLength(0);
                //                sql.append("INSERT  INTO    TZ_PROGRESS_HISTORY (   \n");
                //                sql.append("        SUBJ    \n");
                //                sql.append("    ,   YEAR    \n");
                //                sql.append("    ,   SUBJSEQ \n");
                //                sql.append("    ,   LESSON  \n");
                //                sql.append("    ,   OID     \n");
                //                sql.append("    ,   USERID  \n");
                //                sql.append("    ,   SESSION_TIME    \n");
                //                sql.append("    ,   FIRST_EDU   \n");
                //                sql.append("    ,   FIRST_END   \n");
                //                sql.append("    ,   REMOTE_IP   \n");
                //                sql.append(" ) VALUES (     \n");
                //                sql.append("        ?       \n");
                //                sql.append("    ,   ?       \n");
                //                sql.append("    ,   ?       \n");
                //                sql.append("    ,   ?       \n");
                //                sql.append("    ,   ?       \n");
                //                sql.append("    ,   ?       \n");
                //                sql.append("    ,   ?       \n");
                //                sql.append("    ,   ?       \n");
                //                sql.append("    ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') \n");
                //                sql.append("    ,   ?)      \n");

                //                pstmt = connMgr.prepareStatement(sql.toString());
                //                pstmt.setString(index++, subj);
                //                pstmt.setString(index++, year);
                //                pstmt.setString(index++, subjseq);
                //                pstmt.setString(index++, lesson);
                //                pstmt.setString(index++, oid);
                //                pstmt.setString(index++, userid);
                //                pstmt.setString(index++, sessionTime);
                //                pstmt.setString(index++, ldate);
                //                pstmt.setString(index++, userip);

                //                resultCnt += pstmt.executeUpdate();

                sql.append("INSERT  INTO    TZ_PROGRESS_HISTORY (   \n");
                sql.append("        SUBJ    \n");
                sql.append("    ,   YEAR    \n");
                sql.append("    ,   SUBJSEQ \n");
                sql.append("    ,   LESSON  \n");
                sql.append("    ,   OID     \n");
                sql.append("    ,   USERID  \n");
                sql.append("    ,   SESSION_TIME    \n");
                sql.append("    ,   FIRST_EDU   \n");
                sql.append("    ,   FIRST_END   \n");
                sql.append("    ,   REMOTE_IP   \n");
                sql.append(" ) VALUES (     \n");
                sql.append("        '").append(subj).append("'          \n");
                sql.append("    ,   '").append(year).append("'          \n");
                sql.append("    ,   '").append(subjseq).append("'       \n");
                sql.append("    ,   '").append(lesson).append("'        \n");
                sql.append("    ,   '").append(oid).append("'           \n");
                sql.append("    ,   '").append(userid).append("'        \n");
                sql.append("    ,   '").append(sessionTime).append("'   \n");
                sql.append("    ,   '").append(ldate).append("'         \n");
                sql.append("    ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') \n");
                sql.append("    ,   '").append(userip).append("'  )     \n");

                resultCnt += connMgr.executeUpdate(sql.toString());

            }

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e10) {
                }
            }
            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                    pstmt = null;
            //                } catch (Exception e10) {
            //                }
            //            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return resultCnt;
    }

}
