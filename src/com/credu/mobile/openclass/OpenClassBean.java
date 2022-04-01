package com.credu.mobile.openclass;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * 열린강좌 관리 기능을 담당한다.
 * 
 * @author saderaser
 * 
 */
public class OpenClassBean {

    public OpenClassBean() {
    }

    /**
     * 열린강좌 인기별 목록을 조회한다. 최대 30개까지만 조회된다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectOpenClassPopuplarList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        
        String grcode = box.getSession("grcode");
        grcode = grcode.equals("") ? "N000001" : grcode;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* com.credu.mobile.openclass.OpenClassBean() selectOpenClassPopuplarList (열린강좌 인기별 목록 조회) */ \n");
            sql.append("SELECT  V.RNK                                                                                   \n");
            sql.append("    ,   V.MONTHLY_CNT                                                                           \n");
            sql.append("    ,   D.SEQ                                                                                   \n");
            sql.append("    ,   D.VODIMG                                                                                \n");
            sql.append("    ,   D.VODURL                                                                                \n");
            sql.append("    ,   D.LECNM                                                                                 \n");
            sql.append("    ,   D.TUTORNM                                                                               \n");
            sql.append("    ,   D.INTRO                                                                                 \n");
            sql.append("    ,   D.CONTENTS                                                                              \n");
            sql.append("  FROM  (                                                                                       \n");
            // sql.append("        SELECT  RANK() OVER(ORDER BY DECODE(B.AREA, 'G0', 1, 2 ), SUM(A.CNT) DESC, COUNT(A.SEQ) DESC, A.SEQ DESC ) AS RNK   \n");
            sql.append("        SELECT  RANK() OVER(ORDER BY SUM(A.CNT) DESC, COUNT(A.SEQ) DESC, A.SEQ DESC ) AS RNK    \n");
            sql.append("            ,   A.SEQ                                                                           \n");
            sql.append("            ,   SUM(A.CNT) AS MONTHLY_CNT                                                       \n");
            sql.append("            ,   COUNT(A.SEQ)                                                                    \n");
            sql.append("          FROM  TZ_VIEWCOUNT A                                                                  \n");
            sql.append("            ,   TZ_GOLDCLASS B                                                                  \n");
            sql.append("            ,   TZ_GOLDCLASS_GRMNG C                                                            \n");
            sql.append("         WHERE  A.VIEWDATE BETWEEN ADD_MONTHS(SYSDATE, -1) AND TO_CHAR(SYSDATE, 'yyyymmdd')     \n");
            sql.append("           AND  A.SEQ = B.SEQ                                                                   \n");
            sql.append("           AND  B.USEYN = 'Y'                                                                   \n");
            sql.append("           AND  C.GRCODE = '").append(grcode).append("'                                         \n");
            sql.append("           AND  B.SEQ = C.SEQ                                                                   \n");
            //신규 과정이 모바일이 지원이 되지 않어 조회가 안 되도록 처리.
            //모바일 포팅 이후 풀어줘야 함
            //sql.append("           AND a.seq < '434'                                                                   \n");
            
            // sql.append("         GROUP  BY B.AREA, A.SEQ                                                                \n");
            sql.append("         GROUP  BY A.SEQ                                                                \n");
            sql.append("        ) V                                                                                     \n");
            sql.append("    ,   TZ_GOLDCLASS D                                                                          \n");
            sql.append(" WHERE  V.RNK < 31                                                                              \n");
            sql.append("   AND  V.SEQ = D.SEQ                                                                           \n");
            sql.append("   AND  D.USEYN = 'Y'                                                                           \n");
            sql.append(" ORDER  BY V.RNK                                                                                \n");

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
     * 열린강좌 분류별 목록을 조회힌다.
     * 
     * @param box
     * @return
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectOpenClassCategoryList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        String lectureCls = box.getString("lectureCls");
        String step = box.getString("step");
        String grcode = box.getSession("grcode");
        grcode = grcode.equals("") ? "N000001" : grcode;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* OpenClassBean.selectOpenClassCategoryList (열린강좌 분류별 목록 조회) */    \n");
            sql.append("SELECT  *       \n");
            sql.append("  FROM  (       \n");
            sql.append("        SELECT  \n");
            sql.append("                RANK() OVER(ORDER BY DECODE(A.NEW_YN, 'Y', 1, 2), A.LECNM, A.SEQ) AS RNK    \n");
            sql.append("            ,   COUNT(A.SEQ) OVER() AS CNT              \n");
            sql.append("            ,   A.SEQ                                     \n");
            sql.append("            ,   A.LECNM                                   \n");
            sql.append("            ,   REPLACE(A.VODIMG, '\\', '/') AS VODIMG    \n");
            sql.append("            ,   A.TUTORNM                                 \n");
            sql.append("            ,   DECODE(A.LECTURE_CLS, 'GC06', 'GC05', 'GC07', 'GC05', 'GC08', 'GC05', A.LECTURE_CLS) AS LECTURE_CLS \n");
            sql.append("            ,   A.INTRO                                   \n");
            sql.append("            ,   A.CONTENTS                                \n");
            sql.append("            ,   NVL(A.NEW_YN, 'N') AS NEW_YN              \n");
            sql.append("          FROM  TZ_GOLDCLASS A                            \n");
            sql.append("            ,   TZ_GOLDCLASS_GRMNG B                    \n");
            sql.append("         WHERE  A.USEYN = 'Y'                             \n");
            sql.append("           AND  DECODE(A.LECTURE_CLS, 'GC06', 'GC05', 'GC07', 'GC05', 'GC08', 'GC05', A.LECTURE_CLS) = '").append(lectureCls).append("'\n");
            sql.append("           AND  B.GRCODE = '").append(grcode).append("'   \n");
            sql.append("           AND  A.SEQ = B.SEQ                           \n");
            //신규 과정이 모바일이 지원이 되지 않어 조회가 안 되도록 처리.
            //모바일 포팅 이후 풀어줘야 함
            //sql.append("           AND a.seq < '434'                                                                   \n");

            sql.append("        )       \n");

            if (step.equals("step1")) {
                sql.append(" WHERE  RNK < 4 \n");
            } else if (step.equals("step2")) {
                sql.append(" WHERE  RNK > 3 AND RNK < 9 ");
            }
            sql.append(" ORDER  BY RNK  \n");

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
     * 열린강좌 테마 목록을 조회힌다.
     * 
     * @param box
     * @return
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectOpenClassThemeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        String grcode = box.getSession("grcode");
        grcode = (grcode.equals("")) ? "N000001" : grcode;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* OpenClassBean.selectOpenClassThemeList (열린강좌 테마 목록 조회) */\n");
            sql.append("SELECT  B.CODE                                  \n");
            sql.append("    ,   B.CODENM                                \n");
            sql.append("    ,   COUNT(A.SEQ) AS CNT                     \n");
            sql.append("  FROM  TZ_GOLDCLASS A                          \n");
            sql.append("    ,   TZ_CODE B                               \n");
            sql.append("    ,   TZ_GOLDCLASS_GRMNG C                    \n");
            sql.append(" WHERE  A.USEYN(+) = 'Y'                        \n");
            sql.append("   AND  B.GUBUN = '0119'                        \n");
            sql.append("   AND  C.GRCODE = '").append(grcode).append("' \n");
            sql.append("   AND  A.SEQ = C.SEQ                           \n");
            //신규 과정이 모바일이 지원이 되지 않어 조회가 안 되도록 처리.
            //모바일 포팅 이후 풀어줘야 함
            //sql.append("           AND a.seq < '434'                                                                   \n");

            sql.append("   AND  B.CODE = A.LECTURE_THEME(+)             \n");
            sql.append(" GROUP  BY A.LECTURE_THEME, B.CODE, B.CODENM    \n");
            sql.append(" ORDER  BY B.CODE                               \n"); 

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
     * 열린강좌 테마별 상세 목록을 조회힌다.
     * 
     * @param box
     * @return
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectOpenClassThemeDetailList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        String lectureTheme = box.getString("lectureTheme");
        String grcode = box.getSession("grcode");
        grcode = grcode.equals("") ? "N000001" : grcode;
        
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* OpenClassBean.selectOpenClassThemeDetailList (열린강좌 테마별 상세 목록) */ \n");
            sql.append("SELECT                          \n");
            sql.append("        A.SEQ                     \n");
            sql.append("    ,   A.VODIMG                  \n");
            sql.append("    ,   A.VODURL                  \n");
            sql.append("    ,   A.LECNM                   \n");
            sql.append("    ,   A.TUTORNM                 \n");
            sql.append("    ,   A.INTRO                   \n");
            sql.append("    ,   A.CONTENTS                \n");
            sql.append("    ,   A.LECTURE_THEME           \n");
            sql.append("    ,   NVL(A.NEW_YN, 'N') AS NEW_YN  \n");
            sql.append("  FROM  TZ_GOLDCLASS A            \n");
            sql.append("    ,   TZ_GOLDCLASS_GRMNG B      \n");
            sql.append(" WHERE  A.USEYN = 'Y'             \n");
            sql.append("   AND  B.GRCODE = '").append(grcode).append("'       \n");
            sql.append("   AND  A.SEQ = B.SEQ   \n");
            //신규 과정이 모바일이 지원이 되지 않어 조회가 안 되도록 처리.
            //모바일 포팅 이후 풀어줘야 함
            //sql.append("           AND a.seq < '434'                 \n");

            sql.append("   AND  A.LECTURE_THEME = '").append(lectureTheme).append("'  \n");
            sql.append(" ORDER  BY A.LDATE DESC, A.SEQ DESC, A.LECNM ASC  \n");

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
     * 열린강좌 상세 정보를 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectOpenClassDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        StringBuilder sql = new StringBuilder();

        int seq = box.getInt("seq");
        String userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql.append("/* OpenClassBean.selectOpenClassDetail (열린강좌정보 조회) */ \n");
            sql.append("SELECT                                                      \n");
            sql.append("        A.SEQ                                               \n");
            sql.append("    ,   A.LECNM                                             \n");
            sql.append("    ,   A.TUTORNM                                           \n");
            sql.append("    ,   A.TUTORCAREER                                       \n");
            sql.append("    ,   A.LECTIME                                           \n");
            sql.append("    ,   A.GENRE                                             \n");
            sql.append("    ,   A.CREATOR                                           \n");
            sql.append("    ,   A.INTRO                                             \n");
            sql.append("    ,   A.CONTENTS                                          \n");
            sql.append("    ,   A.TUTORAUTHOR                                       \n");
            sql.append("    ,   A.HEIGHT_S                                          \n");
            sql.append("    ,   A.WIDTH_S                                           \n");
            sql.append("    ,   A.OPENYN                                            \n");
            sql.append("    ,   A.VODURL                                            \n");
            sql.append("    ,   A.VODIMG                                            \n");
            sql.append("    ,   A.TUTORIMG                                          \n");
            sql.append("    ,   A.USEYN                                             \n");
            sql.append("    ,   A.MOBILE_URL                                        \n");
            sql.append("    ,   C.NUM                                               \n");
            sql.append("    ,   C.MOBILEURL                                         \n");
            sql.append("    ,   C.CONTENT                                           \n");
            sql.append("    ,   A.CREATYEAR                                         \n");
            sql.append("    ,   A.LECTURE_TYPE                                      \n");
            sql.append("    ,   A.VOD_PATH                                          \n");
            sql.append("    ,   A.AREA                                              \n");
            sql.append("    ,   A.LECTURE_CLS                                       \n");
            sql.append("    ,   A.LECTURE_THEME                                     \n");
            sql.append("    ,   A.TAGS                                              \n");
            sql.append("    ,   A.NEW_YN                                            \n");
            sql.append("    ,   DECODE( NVL(B.SUBJ, ''), '', 'N', 'Y') AS FAVOR_YN  \n");
            sql.append("  FROM  TZ_GOLDCLASS A                                      \n");
            sql.append("    ,   (                                                   \n");
            sql.append("        SELECT  SUBJ                                        \n");
            sql.append("          FROM  TZ_SUBJ_FAVOR B                             \n");
            sql.append("         WHERE  CLASS_TYPE = '02'                           \n");
            sql.append("           AND  USERID = '").append(userid).append("'       \n");
            sql.append("        ) B                                                 \n");
            sql.append("    ,   TZ_GOLDCLASSMOBILE C                                \n");
            sql.append(" WHERE  A.SEQ = ").append(seq).append("                     \n");
            sql.append("   AND  TO_CHAR(A.SEQ) = B.SUBJ(+)                          \n");
            sql.append("   AND  A.SEQ = C.SEQ(+)                                    \n");
            //신규 과정이 모바일이 지원이 되지 않어 조회가 안 되도록 처리.
            //모바일 포팅 이후 풀어줘야 함
            //sql.append("           AND a.seq < '434'                                                                   \n");

            sql.append(" ORDER  BY C.NUM                                            \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("Sql = " + sql.toString() + "\r\n" + ex.getMessage());
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
     * 열린강좌 상세 화면에서 연관강좌 목록을 조회한다. 연관강좌란 연관된 강좌로 tags 항목에 등록된 내용을 기반으로 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectRelatedLecutreList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        int seq = box.getInt("seq");

        String tags = "";
        String[] tagsArr = null;
        String grcode = box.getSession("grcode");
        grcode = grcode.equals("") ? "N000001" : grcode;

        int rnkLimit = 0;

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  TAGS            \n");
            sql.append("  FROM  TZ_GOLDCLASS    \n");
            sql.append(" WHERE  SEQ = ").append(seq).append("  \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                tags = ls.getString("tags");
            }

            ls.close();
            ls = null;

            if (tags != null && !tags.equals("")) {

                tagsArr = tags.split(",");
                rnkLimit = (tagsArr.length < 3) ? tagsArr.length - 1 : 2;

                sql.setLength(0);
                sql.append("/* OpenClassBean.selectRelatedLecutreList (열린강좌 연관 강좌 목록 조회) */ \n");
                sql.append("SELECT  *               \n");
                sql.append("  FROM  (               \n");
                sql.append("        SELECT  A.SEQ     \n");
                sql.append("            ,   A.LECNM   \n");
                sql.append("            ,   A.VODIMG  \n");
                sql.append("            ,   A.TAGS    \n");
                sql.append("            ,   A.TUTORNM \n");
                sql.append("            ,   A.INTRO   \n");
                sql.append("            ,   A.CONTENTS \n");

                for (int i = 0; i < tagsArr.length; i++) {
                    sql.append("            ,   CASE WHEN INSTR (A.TAGS, '").append(tagsArr[i]).append("') > 0 THEN 1 ELSE 0 END RNK").append(i).append(" \n");
                }
                sql.append("          FROM  TZ_GOLDCLASS A   \n");
                sql.append("            ,   TZ_GOLDCLASS_GRMNG B \n");
                sql.append("         WHERE  A.SEQ <> ").append(seq).append("  \n");
                sql.append("           AND  B.GRCODE = '").append(grcode).append("'     \n");
                sql.append("           AND  A.SEQ = B.SEQ   \n");
                //신규 과정이 모바일이 지원이 되지 않어 조회가 안 되도록 처리.
                //모바일 포팅 이후 풀어줘야 함
                //sql.append("           AND a.seq < '434'                                                                   \n");

                sql.append("           AND  (       \n");
                for (int i = 0; i < tagsArr.length; i++) {
                    if (i == 0) {
                        sql.append("                A.TAGS LIKE '%' || '").append(tagsArr[i]).append("' || '%' \n");
                    } else {
                        sql.append("                OR A.TAGS LIKE '%' || '").append(tagsArr[i]).append("' || '%' \n");
                    }
                }
                sql.append("                )   \n");
                sql.append("        )           \n");
                sql.append(" WHERE ( ");

                for (int i = 0; i < tagsArr.length; i++) {
                    if (i < tagsArr.length - 1) {
                        sql.append("RNK").append(i).append(" + ");
                    } else {
                        sql.append("RNK").append(i);
                    }
                }
                sql.append(" ) > ").append(rnkLimit).append(" \n");

                sql.append(" ORDER  BY  ");
                for (int i = 0; i < tagsArr.length; i++) {
                    if (i == 0) {
                        sql.append(" RNK").append(i);
                    } else {
                        sql.append(" || RNK").append(i);
                    }
                }
                sql.append(" DESC   \n");

                ls = connMgr.executeQuery(sql.toString());

                list = new ArrayList();
                while (ls.next()) {
                    dbox = ls.getDataBox();

                    list.add(dbox);
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
        return list;
    }

    /**
     * 열린강좌 후기 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectOpenClassReviewList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;
        int seq = box.getInt("seq");
        String step = box.getStringDefault("step", "step1");
        String userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql.setLength(0);
            sql.append("/* selectOpenClassReviewList (열린강좌 후기목록 조회) */\n");
            sql.append("SELECT  V1.*                \n");
            sql.append("  FROM  (                   \n");
            sql.append("        SELECT  A.NUM       \n");
            sql.append("            ,   A.CONT      \n");
            sql.append("            ,   A.CHECKPOIN \n");
            sql.append("            ,   DECODE(A.REGID, '").append(userid).append("', 1, 0) AS USER_RNK  \n");
            sql.append("            ,   TO_CHAR( TO_DATE(A.REGDT, 'YYYYMMDDHH24MISS'), 'YYYY/MM/DD') AS REGDT   \n");
            sql.append("            ,   A.REGID                 \n");
            sql.append("            ,   NVL(B.NAME, '') AS NAME \n");
            sql.append("            ,   RANK() OVER ( ORDER BY DECODE(A.REGID, '").append(userid).append("', 1, 0) DESC, NUM DESC) AS RNK    \n");
            sql.append("            ,   COUNT(NUM) OVER() AS CNT    \n");
            sql.append("          FROM  TZ_GOLDCLASSREPL A      \n");
            sql.append("            ,   TZ_MEMBER B             \n");
            sql.append("         WHERE  A.SEQ = ").append(seq).append(" \n");
            sql.append("           AND  A.REGID = B.USERID(+)   \n");
            sql.append("           AND  B.STATE = 'Y'           \n");
            sql.append("          ORDER BY USER_RNK DESC, NUM DESC  \n");
            sql.append("        ) V1                            \n");

            if (step.equals("step1")) {
                sql.append(" WHERE  RNK < 6 \n");
            } else if (step.equals("step2")) {
                sql.append(" WHERE  RNK < 11 \n");
            }

            sql.append(" ORDER  BY RNK                          \n");

            ls = connMgr.executeQuery(sql.toString());

            list = new ArrayList();
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
     * 열린강좌 후기 내용을 등록한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int registerOpenClassReview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;

        int resultCnt = 0;
        StringBuilder sql = new StringBuilder();

        // int index = 1;
        int seq = box.getInt("seq");
        int checkPoin = box.getInt("checkpoin");
        String cont = box.getString("cont");
        String userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("INSERT  INTO    TZ_GOLDCLASSREPL                \n");
            sql.append("        (                                       \n");
            sql.append("            SEQ                                 \n");
            sql.append("        ,   NUM                                 \n");
            sql.append("        ,   CONT                                \n");
            sql.append("        ,   CHECKPOIN                           \n");
            sql.append("        ,   REGID                               \n");
            sql.append("        ,   REGDT                               \n");
            sql.append("        )                                       \n");
            sql.append("VALUES  (                                       \n");
            sql.append("            ").append(seq).append("             \n");
            sql.append("        ,   (SELECT NVL(MAX(NUM), 0) + 1        \n");
            sql.append("               FROM TZ_GOLDCLASSREPL            \n");
            sql.append("              WHERE SEQ = ").append(seq).append(")  \n");
            sql.append("        ,   '").append(cont).append("'          \n");
            sql.append("        ,   ").append(checkPoin).append("       \n");
            sql.append("        ,   '").append(userid).append("'        \n");
            sql.append("        ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')\n");
            sql.append("        )                                       \n");

            resultCnt = connMgr.executeUpdate(sql.toString());

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setInt(index++, seq);
            //            pstmt.setInt(index++, seq);
            //            pstmt.setString(index++, cont);
            //            pstmt.setInt(index++, checkPoin);
            //            pstmt.setString(index++, box.getSession("userid"));
            //
            //            resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
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
     * 열린강좌 후기 내용을 수정한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int modifyOpenClassReview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;

        int resultCnt = 0;
        StringBuilder sql = new StringBuilder();

        // int index = 1;
        int seq = box.getInt("seq");
        int num = box.getInt("num");
        int checkPoin = box.getInt("checkpoin");
        String cont = box.getString("cont");
        String userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("UPDATE  TZ_GOLDCLASSREPL    \n");
            sql.append("   SET  CONT = '").append(cont).append("'   \n");
            sql.append("    ,   CHECKPOIN = ").append(checkPoin).append("   \n");
            sql.append("    ,   MODDT = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            sql.append(" WHERE  SEQ = ").append(seq).append("   \n");
            sql.append("   AND  NUM = ").append(num).append("   \n");
            sql.append("   AND  REGID = '").append(userid).append("'    \n");

            resultCnt = connMgr.executeUpdate(sql.toString());

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setString(index++, cont);
            //            pstmt.setInt(index++, checkPoin);
            //            pstmt.setInt(index++, seq);
            //            pstmt.setInt(index++, num);
            //            pstmt.setString(index++, box.getSession("userid"));
            //
            //            resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
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
     * 열린강좌 후기 내용을 삭제한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int deleteOpenClassReview(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;

        int resultCnt = 0;
        StringBuilder sql = new StringBuilder();

        // int index = 1;
        int seq = box.getInt("seq");
        int num = box.getInt("num");
        String userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("DELETE  FROM TZ_GOLDCLASSREPL    \n");
            sql.append(" WHERE  SEQ = ").append(seq).append("   \n");
            sql.append("   AND  NUM = ").append(num).append("   \n");
            sql.append("   AND  REGID =  '").append(userid).append("'   \n");

            resultCnt = connMgr.executeUpdate(sql.toString());

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setInt(index++, seq);
            //            pstmt.setInt(index++, num);
            //            pstmt.setString(index++, box.getSession("userid"));
            //
            //            resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
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
     * 열린강좌 조회수 정보를 갱신하다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public void updateOpenClassViewCount(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        // PreparedStatement pstmt = null;

        int resultCnt = 0;
        StringBuilder sql = new StringBuilder();

        int seq = box.getInt("seq");
        // int num = box.getInt("num");
        // String mobileURL = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //            sql.append("SELECT  MOBILEURL   \n");
            //            sql.append("  FROM  TZ_GOLDCLASSMOBILE  \n");
            //            sql.append(" WHERE  SEQ = ").append(seq).append(" \n");
            //            sql.append("   AND  NUM = ").append(num).append(" \n");
            //
            //            ls = connMgr.executeQuery(sql.toString());
            //            if (ls.next()) {
            //                mobileURL = ls.getString("mobileurl");
            //            }
            //
            //            if (!mobileURL.equals("")) {

            sql.setLength(0);
            sql.append("UPDATE  TZ_GOLDCLASS            \n");
            sql.append("   SET  VIEWCNT = VIEWCNT + 1   \n");
            sql.append(" WHERE  SEQ = ").append(seq).append("   \n");

            resultCnt = connMgr.executeUpdate(sql.toString());

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setInt(1, seq);

            //            resultCnt = pstmt.executeUpdate();
            //            }

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e10) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }

        }
        // return mobileURL;
    }

    /**
     * 열린강좌 찜한 강좌 목록을 조회힌다.
     * 
     * @param box
     * @return
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectOpenClassFavorList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        String step = box.getStringDefault("step", "step1");
        String userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.setLength(0);
            sql.append("/* com.credu.mobile.openclass.OpenClassBean.selectOpenClassFavorList(열린강좌 찜한 목록 조회) */ \n");
            sql.append("SELECT  *                           \n");
            sql.append("  FROM  (                           \n");
            sql.append("        SELECT  B.SEQ               \n");
            sql.append("            ,   B.LECNM             \n");
            sql.append("            ,   REPLACE(B.VODIMG, '\\', '/') AS VODIMG  \n");
            sql.append("            ,   B.TUTORNM           \n");
            sql.append("            ,   B.INTRO             \n");
            sql.append("            ,   B.CONTENTS          \n");
            sql.append("            ,   B.MOBILE_URL        \n");
            sql.append("            ,   B.VOD_PATH          \n");
            sql.append("            ,   RANK() OVER( ORDER BY A.REG_DT DESC, B.SEQ DESC) AS RNK \n");
            sql.append("            ,   COUNT(A.SUBJ) OVER() AS TOT_CNT \n");
            sql.append("          FROM  TZ_SUBJ_FAVOR A     \n");
            sql.append("            ,   TZ_GOLDCLASS B      \n");
            sql.append("         WHERE  A.CLASS_TYPE = '02' \n");
            sql.append("           AND  A.USERID = '").append(userid).append("'     \n");
            sql.append("           AND  A.SUBJ = B.SEQ      \n");
            sql.append("        )                            \n");

            if (step.equals("step1")) {
                sql.append(" WHERE  RNK < 11    \n");
            } else if (step.equals("step2")) {
                sql.append(" WHERE  RNK < 21    \n");
            } else if (step.equals("step3")) {
                sql.append(" WHERE  RNK < 31    \n");
            }
            sql.append(" ORDER  BY  RNK \n");

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
}
