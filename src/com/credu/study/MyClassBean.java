//**********************************************************
//  1. 제      목: MYCLASS USER BEAN
//  2. 프로그램명: MyClassBean.java
//  3. 개      요: 나의학습실 사용자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 8. 19
//  7. 수      정: 이나연 05.11.18 _ selectStudyHistoryTotList 메소드 수정
//  8. 수      정: 조재형 08.11.20
//**********************************************************
package com.credu.study;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Vector;

import com.credu.common.GetCodenm;
import com.credu.common.SubjComBean;
import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.propose.ProposeBean;
import com.inicis.inipay.INIpay50;

public class MyClassBean {

    private ConfigSet config;
    private int row;

    ConfigSet cs = null;

    public MyClassBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 찜한과정 목록 조회 (정규과정)
     */
    public ArrayList<DataBox> selectFavorSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        StringBuilder sql = new StringBuilder();

        String grcode = box.getSession("tem_grcode");
        String userid = box.getSession("userid");
        try {
            connMgr = new DBConnectionManager();

            sql.append("/* (찜한과정 목록 조회) */ \n");
            sql.append("SELECT  A.USERID                                                                    \n");
            sql.append("    ,   B.SUBJNM                                                                    \n");
            sql.append("    ,   B.SUBJ                                                                      \n");
            sql.append("    ,   NVL(B.MOBILE_USE_YN, 'N') AS MOBILE_USE_YN                                  \n");
            sql.append("    ,   C.YEAR                                                                      \n");
            sql.append("    ,   C.SUBJSEQ                                                                   \n");
            sql.append("    ,   TO_CHAR( TO_DATE( C.PROPSTART, 'YYYYMMDDHH24'), 'YYYY.MM.DD') AS PROPSTART  \n");
            sql.append("    ,   TO_CHAR( TO_DATE( C.PROPEND, 'YYYYMMDDHH24'), 'YYYY.MM.DD') AS PROPEND      \n");
            sql.append("    ,   TO_CHAR( TO_DATE( C.EDUSTART, 'YYYYMMDDHH24'), 'YYYY.MM.DD') AS EDUSTART    \n");
            sql.append("    ,   TO_CHAR( TO_DATE( C.EDUEND, 'YYYYMMDDHH24'), 'YYYY.MM.DD') AS EDUEND        \n");
            sql.append("    ,   (                                                                           \n");
            sql.append("        SELECT  CLASSNAME                                                           \n");
            sql.append("          FROM  TZ_SUBJATT                                                          \n");
            sql.append("         WHERE  UPPERCLASS = B.UPPERCLASS                                           \n");
            sql.append("           AND  MIDDLECLASS = '000'                                                 \n");
            sql.append("           AND  LOWERCLASS = '000'                                                  \n");
            sql.append("        ) AS CLASSNAME                                                              \n");
            sql.append("  FROM  TZ_SUBJ_FAVOR A                                                             \n");
            sql.append("    ,   TZ_SUBJ B                                                                   \n");
            sql.append("    ,   TZ_SUBJSEQ C                                                                \n");
            sql.append(" WHERE  A.GRCODE = '").append(grcode).append("'                                     \n");
            sql.append("   AND  A.USERID = '").append(userid).append("'                                     \n");
            sql.append("   AND  A.CLASS_TYPE = '01'                                                         \n");
            sql.append("   AND  B.ISUSE = 'Y'                                                               \n");
            sql.append("   AND  A.SUBJ = B.SUBJ                                                             \n");
            sql.append("   AND  A.SUBJ = C.SUBJ                                                             \n");
            sql.append("   AND  A.GRCODE = C.GRCODE                                                         \n");
            sql.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN C.PROPSTART AND C.PROPEND          \n");

            ls = connMgr.executeQuery(sql.toString());

            list = ls.getAllDataList();
        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("sql1 = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                ls.close();
                ls = null;
            }
            if (connMgr != null) {
                connMgr.freeConnection();
                connMgr = null;
            }
        }
        return list;
    }

    /**
     * 찜한과정 목록 조회 (열린강좌)
     */
    public ArrayList<DataBox> selectFavorOpenClassList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;
        ArrayList<DataBox> list = null;
        StringBuilder sql = new StringBuilder();

        String grcode = box.getSession("tem_grcode");
        String userid = box.getSession("userid");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* ccom.credu.study.MyClassBean selectOpenClassFavorList(열린강좌 찜한 목록 조회) */ \n");
            sql.append("SELECT  *                           \n");
            sql.append("  FROM  (                           \n");
            sql.append("        SELECT  B.SEQ               \n");
            sql.append("            ,   B.LECNM             \n");
            sql.append("            ,   B.LECTURE_CLS       \n");
            sql.append("            ,   GET_CODENM('0101', B.AREA) AS AREA_NM   \n");
            sql.append("            ,   GET_CODENM('0118', B.LECTURE_CLS) AS LECTURE_CLS_NM   \n");
            sql.append("            ,   RANK() OVER( ORDER BY A.REG_DT DESC, B.SEQ DESC) AS RNK \n");
            sql.append("            ,   COUNT(A.SUBJ) OVER() AS TOT_CNT \n");
            sql.append("          FROM  TZ_SUBJ_FAVOR A     \n");
            sql.append("            ,   TZ_GOLDCLASS B      \n");
            sql.append("         WHERE  A.CLASS_TYPE = '02' \n");
            sql.append("           AND  A.GRCODE = '").append(grcode).append("' \n");
            sql.append("           AND  A.USERID = '").append(userid).append("' \n");
            sql.append("           AND  A.SUBJ = B.SEQ      \n");
            sql.append("        )                            \n");
            sql.append(" WHERE  RNK < 31    \n");
            sql.append(" ORDER  BY  RNK \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("sql1 = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                ls.close();
                ls = null;
            }
            if (connMgr != null) {
                connMgr.freeConnection();
                connMgr = null;
            }
        }
        return list;
    }

    /**
     * 과정 찜하기 등록
     *
     * @param box
     * @return
     */
    public int registerSubjFavor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        String grcode = box.getSession("tem_grcode");
        String userid = box.getSession("userid");
        String classType = box.getString("classType");
        String subj = box.getString("subj");
        String subjseq = box.getStringDefault("subjseq", "");
        String year = box.getStringDefault("year", "");

        int index = 1;
        int resultCnt = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.setLength(0);
            sql.append("/* com.credu.study.MyClassBean.registerSubjFavor (찜한 강좌 30번째 목록 삭제) */   \n");
            sql.append("DELETE  TZ_SUBJ_FAVOR   \n");
            sql.append(" WHERE  USERID = ?      \n");
            sql.append("   AND  CLASS_TYPE = ?  \n");
            sql.append("   AND  SUBJ IN (       \n");
            sql.append("            SELECT  SUBJ    \n");
            sql.append("              FROM  (       \n");
            sql.append("                    SELECT  USERID  \n");
            sql.append("                        ,   SUBJ    \n");
            sql.append("                        ,   RANK() OVER( ORDER BY REG_DT DESC) AS RNK   \n");
            sql.append("                      FROM  TZ_SUBJ_FAVOR   \n");
            sql.append("                     WHERE  USERID = ?      \n");
            sql.append("                       AND  CLASS_TYPE = ?  \n");
            sql.append("                    )   \n");
            sql.append("             WHERE  RNK > 29    \n");
            sql.append("        )   \n");

            // resultCnt = connMgr.executeUpdate(sql.toString());

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, userid);
            pstmt.setString(index++, classType);
            pstmt.setString(index++, userid);
            pstmt.setString(index++, classType);

            resultCnt = pstmt.executeUpdate();
            pstmt.close();
            pstmt = null;

            index = 1;
            sql.setLength(0);

            sql.append("/* com.credu.study.MyClassBean.registerSubjFavor (과정 찜하기 등록) */   \n");
            sql.append("INSERT  INTO  TZ_SUBJ_FAVOR \n");
            sql.append("    (               \n");
            sql.append("        GRCODE      \n");
            sql.append("    ,   USERID      \n");
            sql.append("    ,   CLASS_TYPE  \n");
            sql.append("    ,   SUBJ        \n");
            sql.append("    ,   SUBJSEQ     \n");
            sql.append("    ,   YEAR        \n");
            sql.append("    ,   REG_DT      \n");
            sql.append("    ,   MOD_DT      \n");
            sql.append("    )               \n");
            sql.append(" VALUES             \n");
            sql.append("    (               \n");
            sql.append("        ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   ?   \n");
            sql.append("    ,   SYSDATE \n");
            sql.append("    ,   SYSDATE \n");
            sql.append("    )   \n");

            // resultCnt += connMgr.executeUpdate(sql.toString());

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, grcode);
            pstmt.setString(index++, userid);
            pstmt.setString(index++, classType);
            pstmt.setString(index++, subj);
            pstmt.setString(index++, subjseq);
            pstmt.setString(index++, year);
            resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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
        return resultCnt;
    }

    /**
     * 과정 찜하기 취소
     *
     * @param box
     * @return
     * @throws Exception
     */
    public int cancelSubjFavor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        String grcode = box.getSession("tem_grcode");
        String userid = box.getSession("userid");
        String classType = box.getString("classType");
        String subj = box.getString("subj");

        int index = 1;
        int resultCnt = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* SubjFavorBean.cancelSubjFavor (과정 찜하기 취소) */ \n");
            sql.append("DELETE  FROM TZ_SUBJ_FAVOR \n");
            sql.append(" WHERE  GRCODE = ?  \n");
            sql.append("   AND  USERID = ?  \n");
            sql.append("   AND  CLASS_TYPE = ?  \n");
            sql.append("   AND  SUBJ = ?    \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, grcode);
            pstmt.setString(index++, userid);
            pstmt.setString(index++, classType);
            pstmt.setString(index++, subj);

            resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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
        return resultCnt;

    }

    /**
     * 수강중인 과정 리스트 on-line
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectEducationSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;

        StringBuffer sql1 = new StringBuffer();

        StringBuffer head_sql1 = new StringBuffer();
        StringBuffer body_sql1 = new StringBuffer();
        StringBuffer order_sql1 = new StringBuffer();
        StringBuffer count_sql1 = new StringBuffer();

        int v_pageno = box.getInt("p_pageno");

        DataBox dbox = null;

        String v_user_id = box.getSession("userid");

        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");

        String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            list1 = new ArrayList<DataBox>();

            head_sql1.append(" SELECT\n");
            head_sql1.append("		A.SCUPPERCLASS, a.introducefilenamenew, A.ISONOFF, A.COURSE, A.CYEAR, A.COURSESEQ,\n");
            head_sql1.append("		A.COURSENM, A.GRCODE,A.ISBELONGCOURSE, A.SUBJCNT, A.SUBJ,\n");
            head_sql1.append("		A.YEAR, A.SUBJSEQ, A.SUBJSEQGR, A.CPSUBJ, A.CPSUBJSEQ,\n");
            head_sql1.append("		A.SUBJNM, A.EDUSTART, A.EDUEND, A.PREURL, A.EDUURL,\n");
            head_sql1.append("		A.SUBJTARGET, A.ISOUTSOURCING, 'Y' CHKFNAL, B.COMP COMPANY, A.CONTENTTYPE,\n");
            head_sql1.append("		(SELECT CLASSNAME FROM TZ_SUBJATT\n");
            head_sql1.append("		  WHERE UPPERCLASS = A.SCUPPERCLASS AND MIDDLECLASS = '000' AND LOWERCLASS = '000'\n");
            head_sql1.append("		) UPPERCLASSNM,\n");
            head_sql1.append("		(SELECT CLASSNAME FROM TZ_SUBJATT\n");
            head_sql1.append("		  WHERE UPPERCLASS = A.SCUPPERCLASS AND MIDDLECLASS = A.SCMIDDLECLASS AND LOWERCLASS = '000'\n");
            head_sql1.append("		) MIDDLECLASSNM ,\n");
            head_sql1.append("		ISNULL(D.PAYMONEY, 0) AS  BIYONG  , ISNULL(C.LDATE , 'N') AS ISUSEYN,\n");
            head_sql1.append("		CASE WHEN TO_CHAR(SYSDATE,'YYYYMMDD')||'00' BETWEEN A.EDUSTART AND A.EDUEND\n");
            head_sql1.append("             THEN 'Y' ELSE 'N' END ISSTUDYYN, \n");
            head_sql1.append("     A.WJ_CLASSKEY,\n");
            head_sql1.append("     (SELECT X.SCORE FROM TZ_STUDENT X WHERE A.SUBJ=X.SUBJ AND A.YEAR=X.YEAR AND A.SUBJSEQ=X.SUBJSEQ AND B.USERID = X.USERID) SCORE \n");

            body_sql1.append("   FROM VZ_SCSUBJSEQIMGMOBILE A, TZ_PROPOSE B, TZ_TAX C, TZ_BILLING D\n");
            body_sql1.append("  WHERE A.SUBJ=B.SUBJ AND A.YEAR=B.YEAR AND A.SUBJSEQ=B.SUBJSEQ \n");
            body_sql1.append("	  AND A.SCSUBJ=C.SUBJ(+) AND A.YEAR=C.YEAR(+) AND A.SUBJSEQ = C.SUBJSEQ(+)\n");
            body_sql1.append("	  AND A.SCSUBJ = D.SUBJ(+) AND A.YEAR = D.YEAR(+) AND A.SUBJSEQ = D.SUBJSEQ(+)\n");
            body_sql1.append("    AND b.chkfinal='Y'  ");
            body_sql1.append("    AND B.USERID=" + SQLString.Format(v_user_id));
            body_sql1.append("    AND A.grcode=" + SQLString.Format(v_grcode));

            if (!v_upperclass.equals("ALL"))
                body_sql1.append(" AND A.SCUPPERCLASS = " + SQLString.Format(v_upperclass) + "\n");

            body_sql1.append("    AND TO_CHAR(SYSDATE,'YYYYMMDDHH24') <= A.EDUEND\n");

            order_sql1.append(" ORDER BY A.EDUSTART DESC, A.COURSE, A.SCUPPERCLASS, A.SCMIDDLECLASS, A.SUBJNM, A.SUBJ,A.YEAR,A.SUBJSEQ,A.EDUEND\n");

            sql1.append(head_sql1.toString() + body_sql1.toString() + order_sql1);

            ls1 = connMgr.executeQuery(sql1.toString());

            count_sql1.append(" SELECT COUNT(*) " + body_sql1.toString());

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1.toString()); // 전체 row 수를 반환한다

            ls1.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls1.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.

            int total_page_count = ls1.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1.toString());
            throw new Exception("sql1 = " + sql1.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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

        return list1;
    }

    /**
     * 정규과정 수강중인 과정 목록을 조회한다.
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjectList3(RequestBox box, String gubun) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;

        StringBuffer sql = new StringBuffer();

        DataBox dbox = null;

        String v_user_id = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            list1 = new ArrayList<DataBox>();

            sql.append(" /* com.credu.study.MyClassBean selectSubjectList3 (정규과정 수강과정목록 조회) */  \n");
            sql.append("SELECT  A.SCUPPERCLASS                          \n");
            sql.append("    ,   A.introducefilenamenew                  \n");
            sql.append("    ,   A.ISONOFF                               \n");
            sql.append("    ,   A.COURSE                                \n");
            sql.append("    ,   A.CYEAR                                 \n");
            sql.append("    ,   A.COURSESEQ                             \n");
            sql.append("    ,   A.COURSENM                              \n");
            sql.append("    ,   A.GRCODE                                \n");
            sql.append("    ,   A.ISBELONGCOURSE                        \n");
            sql.append("    ,   A.SUBJCNT                               \n");
            sql.append("    ,   A.SUBJ                                  \n");
            sql.append("    ,   A.YEAR                                  \n");
            sql.append("    ,   A.SUBJSEQ                               \n");
            sql.append("    ,   A.SUBJSEQGR                             \n");
            sql.append("    ,   A.CPSUBJ                                \n");
            sql.append("    ,   A.CPSUBJSEQ                             \n");
            sql.append("    ,   A.SUBJNM                                \n");
            sql.append("    ,   A.EDUSTART                              \n");
            sql.append("    ,   A.EDUEND                                \n");
            sql.append("    ,   A.PREURL                                \n");
            sql.append("    ,   A.EDUURL                                \n");
            sql.append("    ,   A.SUBJTARGET                            \n");
            sql.append("    ,   A.ISOUTSOURCING                         \n");
            sql.append("    ,   'Y' CHKFNAL                             \n");
            sql.append("    ,   B.COMP COMPANY                          \n");
            sql.append("    ,   A.CONTENTTYPE                           \n");
            sql.append("    ,   (                                       \n");
            sql.append("        SELECT  CLASSNAME                       \n");
            sql.append("          FROM  TZ_SUBJATT                      \n");
            sql.append("         WHERE  UPPERCLASS = A.SCUPPERCLASS     \n");
            sql.append("           AND  MIDDLECLASS = '000'             \n");
            sql.append("           AND  LOWERCLASS = '000'              \n");
            sql.append("        ) AS UPPERCLASSNM                       \n");
            sql.append("    ,   (                                       \n");
            sql.append("        SELECT  CLASSNAME                       \n");
            sql.append("          FROM  TZ_SUBJATT                      \n");
            sql.append("         WHERE  UPPERCLASS = A.SCUPPERCLASS     \n");
            sql.append("           AND  MIDDLECLASS = A.SCMIDDLECLASS   \n");
            sql.append("           AND  LOWERCLASS = '000'              \n");
            sql.append("        ) AS MIDDLECLASSNM                      \n");
            sql.append("    ,   A.WJ_CLASSKEY                           \n");
            sql.append("    ,   B.APPDATE                               \n");
            sql.append("    ,   B.TID                                   \n");
            sql.append("  FROM  VZ_SCSUBJSEQIMGMOBILE A                 \n");
            sql.append("    ,   TZ_PROPOSE B                            \n");
            sql.append(" WHERE  A.SUBJ = B.SUBJ                         \n");
            sql.append("   AND  A.YEAR = B.YEAR                         \n");
            sql.append("   AND  A.SUBJSEQ = B.SUBJSEQ                   \n");
            sql.append("   AND  b.chkfinal = 'Y'                        \n");
            sql.append("   AND  B.USERID = '").append(v_user_id).append("'  \n");
            sql.append("   AND  A.grcode = '").append(v_grcode).append("'   \n");
            sql.append("   AND  TO_CHAR (SYSDATE, 'YYYYMMDDHH24') || '23' <= A.EDUEND                     \n");

            if (gubun.equals("I")) {
                sql.append("   AND  TO_CHAR (SYSDATE, 'YYYYMMDDHH24') || 00 BETWEEN A.EDUSTART AND A.EDUEND     \n");
            } else if (gubun.equals("P")) {
                sql.append("   AND  TO_CHAR (SYSDATE, 'YYYYMMDDHH24') || 00 BETWEEN A.PROPSTART AND A.PROPEND   \n");
            }
            sql.append(" ORDER  BY GRSEQ, APPDATE, SUBJNM   \n");
//
//            head_sql1.append("/* com.credu.study.MyClassBean selectSubjectList3 (정규과정 수강과정목록 조회) */\n");
//            head_sql1.append("SELECT    A.SCUPPERCLASS, a.introducefilenamenew, A.ISONOFF, A.COURSE, A.CYEAR, A.COURSESEQ,\n");
//            head_sql1.append("		A.COURSENM, A.GRCODE,A.ISBELONGCOURSE, A.SUBJCNT, A.SUBJ,\n");
//            head_sql1.append("		A.YEAR, A.SUBJSEQ, A.SUBJSEQGR, A.CPSUBJ, A.CPSUBJSEQ,\n");
//            head_sql1.append("		A.SUBJNM, A.EDUSTART, A.EDUEND, A.PREURL, A.EDUURL,\n");
//            head_sql1.append("		A.SUBJTARGET, A.ISOUTSOURCING, 'Y' CHKFNAL, B.COMP COMPANY, A.CONTENTTYPE,\n");
//            head_sql1.append("		(SELECT CLASSNAME FROM TZ_SUBJATT\n");
//            head_sql1.append("		  WHERE UPPERCLASS = A.SCUPPERCLASS AND MIDDLECLASS = '000' AND LOWERCLASS = '000'\n");
//            head_sql1.append("		) UPPERCLASSNM,\n");
//            head_sql1.append("		(SELECT CLASSNAME FROM TZ_SUBJATT\n");
//            head_sql1.append("		  WHERE UPPERCLASS = A.SCUPPERCLASS AND MIDDLECLASS = A.SCMIDDLECLASS AND LOWERCLASS = '000'\n");
//            head_sql1.append("		) MIDDLECLASSNM ,\n");
//            head_sql1.append("     A.WJ_CLASSKEY, B.APPDATE, B.TID \n");
//            body_sql1.append("   FROM VZ_SCSUBJSEQIMGMOBILE A, TZ_PROPOSE B \n");
//            body_sql1.append("  WHERE A.SUBJ=B.SUBJ AND A.YEAR=B.YEAR AND A.SUBJSEQ=B.SUBJSEQ \n");
//            body_sql1.append("    AND b.chkfinal='Y' ");
//            body_sql1.append("    AND B.USERID=" + SQLString.Format(v_user_id));
//            body_sql1.append("    AND A.grcode=" + SQLString.Format(v_grcode));
//            body_sql1.append("    AND TO_CHAR(SYSDATE,'YYYYMMDDHH24')||'23' <= A.EDUEND\n");
//
//            if (gubun.equals("I")) {
//                body_sql1.append("    AND TO_CHAR (SYSDATE, 'YYYYMMDDHH24') || 00 BETWEEN A.EDUSTART AND  A.EDUEND");
//            } else if (gubun.equals("P")) {
//                body_sql1.append("    AND TO_CHAR (SYSDATE, 'YYYYMMDDHH24') || 00 BETWEEN A.PROPSTART AND  A.PROPEND");
//            }

            ls1 = connMgr.executeQuery(sql.toString());

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {

            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql1 = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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

        return list1;
    }

    /**
     * 수강중인 과정 리스트 모바일 on-line
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectEducationSubjectListMobile(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;

        StringBuffer sql1 = new StringBuffer();

        StringBuffer head_sql1 = new StringBuffer();
        StringBuffer body_sql1 = new StringBuffer();
        StringBuffer order_sql1 = new StringBuffer();
        StringBuffer count_sql1 = new StringBuffer();

        int v_pageno = box.getInt("p_pageno");

        DataBox dbox = null;

        String v_user_id = box.getSession("userid");

        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");

        String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            list1 = new ArrayList<DataBox>();

            head_sql1.append(" SELECT\n");
            head_sql1.append("		A.SCUPPERCLASS, a.introducefilenamenew, A.ISONOFF, A.COURSE, A.CYEAR, A.COURSESEQ,\n");
            head_sql1.append("		A.COURSENM, A.GRCODE,A.ISBELONGCOURSE, A.SUBJCNT, A.SUBJ,\n");
            head_sql1.append("		A.YEAR, A.SUBJSEQ, A.SUBJSEQGR, A.CPSUBJ, A.CPSUBJSEQ,\n");
            head_sql1.append("		A.SUBJNM, A.EDUSTART, A.EDUEND, A.PREURL, A.EDUURL,\n");
            head_sql1.append("		A.SUBJTARGET, A.ISOUTSOURCING, 'Y' CHKFNAL, B.COMP COMPANY, A.CONTENTTYPE,\n");
            head_sql1.append("		(SELECT CLASSNAME FROM TZ_SUBJATT\n");
            head_sql1.append("		  WHERE UPPERCLASS = A.SCUPPERCLASS AND MIDDLECLASS = '000' AND LOWERCLASS = '000'\n");
            head_sql1.append("		) UPPERCLASSNM,\n");
            head_sql1.append("		(SELECT CLASSNAME FROM TZ_SUBJATT\n");
            head_sql1.append("		  WHERE UPPERCLASS = A.SCUPPERCLASS AND MIDDLECLASS = A.SCMIDDLECLASS AND LOWERCLASS = '000'\n");
            head_sql1.append("		) MIDDLECLASSNM ,\n");
            head_sql1.append("		ISNULL(D.PAYMONEY, 0) AS  BIYONG  , ISNULL(C.LDATE , 'N') AS ISUSEYN,\n");
            head_sql1.append("		CASE WHEN TO_CHAR(SYSDATE,'YYYYMMDD')||00 BETWEEN A.EDUSTART AND A.EDUEND\n");
            head_sql1.append("             THEN 'Y' ELSE 'N' END ISSTUDYYN, \n");
            head_sql1.append("     A.WJ_CLASSKEY\n");

            body_sql1.append("   FROM VZ_SCSUBJSEQIMGMOBILE A, TZ_PROPOSE B, TZ_TAX C, TZ_BILLING D\n");
            body_sql1.append("  WHERE A.SUBJ=B.SUBJ AND A.YEAR=B.YEAR AND A.SUBJSEQ=B.SUBJSEQ \n");
            body_sql1.append("	  AND A.SCSUBJ=C.SUBJ(+) AND A.YEAR=C.YEAR(+) AND A.SUBJSEQ = C.SUBJSEQ(+)\n");
            body_sql1.append("	  AND A.SCSUBJ = D.SUBJ(+) AND A.YEAR = D.YEAR(+) AND A.SUBJSEQ = D.SUBJSEQ(+)\n");
            body_sql1.append("    AND b.chkfinal='Y' and mobile_preurl ='Y' ");
            body_sql1.append("    AND B.USERID=" + SQLString.Format(v_user_id));
            body_sql1.append("    AND A.grcode=" + SQLString.Format(v_grcode));

            if (!v_upperclass.equals("ALL"))
                body_sql1.append(" AND A.SCUPPERCLASS = " + SQLString.Format(v_upperclass) + "\n");

            body_sql1.append("    AND TO_CHAR(SYSDATE,'YYYYMMDDHH24') <= A.EDUEND\n");

            order_sql1.append(" ORDER BY A.EDUSTART DESC, A.COURSE, A.SCUPPERCLASS, A.SCMIDDLECLASS, A.SUBJNM, A.SUBJ,A.YEAR,A.SUBJSEQ,A.EDUEND\n");

            sql1.append(head_sql1.toString() + body_sql1.toString() + order_sql1);

            ls1 = connMgr.executeQuery(sql1.toString());

            count_sql1.append(" SELECT COUNT(*) " + body_sql1.toString());

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1.toString()); // 전체 row 수를 반환한다

            ls1.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls1.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.

            int total_page_count = ls1.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1.toString());
            throw new Exception("sql1 = " + sql1.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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

        return list1;
    }

    /**
     * 수강중인 과정 리스트 off-line
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectEducationOffSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        String head_sql1 = "";
        String body_sql1 = "";
        String order_sql1 = "";
        String count_sql1 = "";
        int v_pageno = box.getInt("p_pageno");

        DataBox dbox = null;

        String v_user_id = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();
            head_sql1 = " SELECT b.subj, b.year, b.subjseq, a.upperclass,\n";
            head_sql1 += "       (SELECT classname\n";
            head_sql1 += "          FROM tz_offsubjatt\n";
            head_sql1 += "         WHERE upperclass = a.upperclass AND middleclass = '000') upperclassnm,\n";
            head_sql1 += "       a.subjnm, b.edustart, b.eduend, a.isterm, c.stustatus,\n";
            head_sql1 += "       (SELECT codenm\n";
            head_sql1 += "          FROM tz_code\n";
            head_sql1 += "         WHERE gubun = '0089' AND code = c.stustatus) stustatusnm\n";
            body_sql1 += "  FROM tz_offsubj a, tz_offsubjseq b, tz_offstudent c\n";
            body_sql1 += " WHERE a.subj = b.subj\n";
            body_sql1 += "   AND a.subj = c.subj\n";
            body_sql1 += "   AND b.year = c.year\n";
            body_sql1 += "   AND b.subjseq = c.subjseq\n";
            body_sql1 += "   AND b.seq = '1'\n";
            body_sql1 += "   AND TO_CHAR (SYSDATE, 'YYYYMMDDHH24') BETWEEN b.edustart AND b.eduend\n";
            body_sql1 += "   AND c.userid =" + SQLString.Format(v_user_id);
            order_sql1 += " order by edustart desc, upperclass, subj, subjseq\n";

            sql1 = head_sql1 + body_sql1 + order_sql1;

            System.out.println("selectEducationOffSubjectList.sql = " + sql1);
            ls1 = connMgr.executeQuery(sql1);

            count_sql1 = "select count(*) " + body_sql1;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); // 전체 row 수를 반환한다

            ls1.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls1.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int total_page_count = ls1.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 수강중인 과정 리스트 off-line 학기별 점수
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectEducationOffTermScoreList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        DataBox dbox = null;

        String v_userid = box.getSession("userid");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = " SELECT a.subj, a.YEAR, a.subjseq, a.term,\n";
            sql1 += "       (SELECT COUNT (*)\n";
            sql1 += "          FROM tz_offlecturescore\n";
            sql1 += "         WHERE subj    = a.subj\n";
            sql1 += "           AND YEAR    = a.YEAR\n";
            sql1 += "           AND subjseq = a.subjseq\n";
            sql1 += "           AND userid  = " + SQLString.Format(v_userid) + ") lecturecnt,\n";
            sql1 += "       score, RANK, ldate\n";
            sql1 += "  FROM tz_offtermstudent a\n";
            sql1 += " WHERE subj    = " + SQLString.Format(v_subj);
            sql1 += "   AND YEAR    = " + SQLString.Format(v_year);
            sql1 += "   AND subjseq = " + SQLString.Format(v_subjseq);
            sql1 += "   AND userid  = " + SQLString.Format(v_userid);

            System.out.println("selectEducationOffTermScoreList.sql = " + sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * off-line 단기 점수
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public DataBox selectEducationOffScoreList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        DataBox dbox = null;

        String v_userid = box.getSession("userid");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();

            sql1 = " SELECT a.subj, a.YEAR, a.subjseq, a.term,\n";
            sql1 += "       tstep, mtest, ftest, htest, report, etc1, etc2,\n";
            sql1 += "       avtstep, avmtest, avftest, avhtest, avreport, avetc1, avetc2,\n";
            sql1 += "       score, RANK, ldate\n";
            sql1 += "  FROM tz_offtermstudent a\n";
            sql1 += " WHERE subj    = " + SQLString.Format(v_subj);
            sql1 += "   AND YEAR    = " + SQLString.Format(v_year);
            sql1 += "   AND subjseq = " + SQLString.Format(v_subjseq);
            sql1 += "   AND userid  = " + SQLString.Format(v_userid);
            sql1 += "   AND ROWNUM < 2\n";

            System.out.println("selectEducationOffScoreList.sql = " + sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
     * 수강 신청중인 과정 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectProposeSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        DataBox dbox = null;
        String sql1 = "";
        String v_user_id = box.getSession("userid");
        String v_tem_grcode = box.getSession("tem_grcode");
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 += " select b.subj,  b.year, b.subjseq, b.userid, a.subjseqgr, a.scupperclass, a.scmiddleclass, a.subj, a.year,\n";
            sql1 += "        a.subjseq, a.isonoff, a.course, a.coursenm, a.isbelongcourse, a.subjcnt, a.cyear, a.courseseq, a.coursenm,\n";
            sql1 += "        a.subjnm, a.propstart, a.propend, a.edustart, a.eduend, a.eduurl, a.subjtarget, b.chkfinal,\n";
            sql1 += "        b.cancelkind, a.contenttype,\n";
            sql1 += "        (select classname from tz_subjatt\n";
            sql1 += "          where upperclass = a.scupperclass and middleclass = a.scmiddleclass and lowerclass = '000'\n";
            sql1 += "        ) middleclassnm\n";
            sql1 += "   from VZ_SCSUBJSEQ a, TZ_PROPOSE b\n";
            sql1 += "  where a.subj = b.subj and a.year = b.year and a.subjseq = b.subjseq\n";
            sql1 += "    and b.userid = '" + v_user_id + "'\n";
            sql1 += "    and A.grcode='" + v_tem_grcode + "'";
            sql1 += "    and to_char(sysdate,'YYYYMMDDHH24') between a.propstart and a.edustart\n";
            sql1 += "    and ((b.chkfinal = 'Y' and not(to_char(sysdate,'YYYYMMDDHH24') between a.edustart and a.eduend)) or b.chkfinal='N' or b.chkfinal='B')\n";
            sql1 += "    and isnull(b.cancelkind,' ') not in ('P','F')\n";
            sql1 += "  order by A.course,A.scupperclass, a.scmiddleclass, A.subjnm, A.subj,A.year,A.subjseq,A.edustart,A.eduend\n";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 수강 완료한 과정 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectGraduationSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";
        DataBox dbox = null;
        String v_user_id = box.getSession("userid");
        String v_tem_grcode = box.getSession("tem_grcode");

        //		ProposeBean probean = new ProposeBean();
        //		Hashtable outdata = new Hashtable();

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            //			outdata = probean.getMeberInfo(v_user_id);
            //			sql1  = " (\n";
            sql1 += " select  '' pkey, B.serno, A.isonoff, get_jikwinm(B.jik, B.comp) jikwinm, A.subj, A.year, A.subjseq,\n";
            sql1 += "          A.subjseqgr, A.subjnm,  A.edustart, A.eduend, B.score,  B.credit,  B.creditexam,\n";
            sql1 += "          A.place, '1' kind, deptname, B.isgraduated, A.course, A.coursenm, A.isbelongcourse, A.subjcnt,\n";
            // 2005.11.04_하경태 : decode -> case & When
            //sql1 += "          decode(B.isgraduated, 'Y', '이수', '미이수') graduatxt,\n";
            sql1 += "	case B.isgraduated\n";
            sql1 += "		When 'Y' Then '이수'\n";
            sql1 += "		Else '미이수'\n";
            sql1 += "	End as graduatxt,\n";
            sql1 += " a.isablereview, a.reviewdays, a.reviewtype,\n";
            sql1 += "          a.cpsubj,  a.cpsubjseq,  a.eduurl,  b.comp company, a.contenttype,\n";
            sql1 += "          'N' iscareer,  A.isoutsourcing,\n";
            sql1 += "         (select classname from tz_subjatt\n";
            sql1 += "           where upperclass = A.scupperclass and middleclass = A.scmiddleclass and lowerclass = '000'\n";
            sql1 += "         ) middleclassnm, '' gubun\n";
            sql1 += "     from VZ_SCSUBJSEQ A,TZ_STOLD B\n";
            sql1 += "    where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq\n";
            sql1 += "      and A.isclosed='Y'\n";
            sql1 += "      and to_char(sysdate, 'YYYYMMDDHH') between NVL(TRIM(A.eduend),0) and NVL(TRIM(A.eduend),0)+1000000\n";
            sql1 += "      and B.userid=" + SQLString.Format(v_user_id);
            sql1 += " 	  and A.grcode='" + v_tem_grcode + "'";
            sql1 += "	Order by A.course";

            Log.info.println("sql1============>완료" + sql1);
            //System.out.println("sql1============>완료"+sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 취소신청가능 과정리스트 조회
     *
     * @param box receive from the form object and session
     * @return ArrayList 취소신청가능 과정리스트
     */
    public ArrayList<DataBox> selectCancelPossibleList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        //String  v_grcode     = box.getSession("p_grcode");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql += "	select\n";
            sql += "		b.course, b.coursenm, b.isbelongcourse, b.subjcnt, a.userid, b.subj, b.year, b.subjseq, b.subjseqgr, b.subjnm,\n";
            sql += "        b.propstart, b.propend, b.edustart, b.eduend, b.isonoff, a.chkfinal,\n";
            sql += "		a.cancelkind, b.canceldays , c.resultcode, isnull(c.price, 0) as paymoney , c.paymethod,\n";
            sql += "		TO_CHAR(TO_DATE(substr(edustart, 1,8), 'YYYYMMDD')+ canceldays, 'YYYYMMDD') as refundmentday\n";
            sql += "	from TZ_PROPOSE a, VZ_SCSUBJSEQ b , tz_billinfo c\n";
            sql += "    where a.subj = b.subj and a.year = b.year and a.subjseq = b.subjseq\n";
            sql += "		and a.tid = c.tid                                                                                                                      (+)\n";
            //sql += "        and a.userid = '"+v_userid +"' and b.grcode = '" + v_grcode + "'\n";
            sql += "        and a.userid = 'cyber21'\n";
            sql += "        and a.chkfinal != 'N' and nvl(a.cancelkind, '-') not in ('P','F')\n";
            //sql += "        and to_char(sysdate,'YYYYMMDDHH24') between b.propstart and\n";
            //sql += "           (TO_CHAR(TO_DATE(substr(nvl(trim(b.edustart),'2000010100'),1,8),'YYYYMMDD')+ b.canceldays, 'YYYYMMDD') || '00')\n";
            sql += "	order by b.scupperclass, b.scmiddleclass, b.subjnm, b.edustart desc\n";

            System.out.println("sql_cancellist============>" + sql);

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
     * 수강신청 이력 리스트 조회 - 수강신청 확인 취소 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList 수강신청 확인 취소 리스트
     */
    public ArrayList<DataBox> selectProposeHistoryList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        String v_userid = box.getSession("userid");
        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");

        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql.append(" SELECT TID, SUBJ, YEAR, SUBJSEQ, SUBJNM, EDUSTART,BIYONG, introducefilenamenew, \n");
            sql.append("		EDUEND, APPDATE, REFUNDDATE, CHKFIRST, CHKFINAL,\n");
            sql.append("         (SELECT SA.CLASSNAME\n");
            sql.append("            FROM TZ_SUBJ SB, TZ_SUBJATT SA\n");
            sql.append("           WHERE SB.UPPERCLASS = SA.UPPERCLASS\n");
            sql.append("             AND SA.MIDDLECLASS = '000'\n");
            sql.append("             AND SA.LOWERCLASS = '000'\n");
            sql.append("             AND SB.SUBJ = A.SUBJ) CLASSNAME,\n");
            sql.append("          REFUNDABLEDATE, REFUNDABLEYN, CANCELABLEYN, REFUNDYN, CANCELDATE, PAYMETHOD,\n");
            sql.append("          ACCEPTYN, RANK,\n");
            sql.append("          CASE\n");
            sql.append("             WHEN RANK = 1\n");
            sql.append("                THEN COUNT (*) OVER (PARTITION BY TID)\n");
            sql.append("             ELSE 0\n");
            sql.append("          END AS ROWSPAN\n");
            sql.append("     FROM (SELECT D.introducefilenamenew, A.TID, B.SUBJ, B.YEAR, B.SUBJSEQ, B.SUBJNM, B.EDUSTART,\n");
            sql.append("                  B.EDUEND, A.APPDATE, 'Y' ACCEPTYN, 'N' REFUNDYN, A.CHKFIRST, A.CHKFINAL,\n");
            sql.append("                  A.CANCELDATE, C.PAYMETHOD, D.UPPERCLASS, '' REFUNDDATE,\n");
            sql.append("                  TO_CHAR (  TO_DATE (SUBSTR (EDUSTART, 1, 8), 'YYYYMMDD')\n");
            sql.append("                           + B.CANCELDAYS,\n");
            sql.append("                           'YYYYMMDD'\n");
            sql.append("                          ) AS REFUNDABLEDATE,\n");
//            sql.append("                  CASE\n");
//            sql.append("                     WHEN (TO_CHAR (  TO_DATE (SUBSTR (EDUSTART, 1, 8),\n");
//            sql.append("                                               'YYYYMMDD'\n");
//            sql.append("                                              )\n");
//            sql.append("                                    + CANCELDAYS,\n");
//            sql.append("                                    'YYYYMMDD'\n");
//            sql.append("                                   )\n");
//            sql.append("                          ) > TO_CHAR (SYSDATE, 'YYYYMMDD')\n");
//            sql.append("                        THEN 'Y'\n");
//            sql.append("                     ELSE 'N'\n");
//            sql.append("                  END REFUNDABLEYN,\n");
            sql.append("                  CASE WHEN (    SUBSTR (REPLACE(STARTCANCELDATE,'-'), 1, 8) <= TO_CHAR (SYSDATE, 'YYYYMMDD')\n");
            sql.append("                             AND SUBSTR (REPLACE(ENDCANCELDATE,'-'), 1, 8) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )\n");
            sql.append("                       THEN 'Y'\n");
            sql.append("                       ELSE 'N'\n");
            sql.append("                  END REFUNDABLEYN,\n");

            //			sql.append("                  CASE\n");
            //			sql.append("                     WHEN (TO_CHAR (  TO_DATE (SUBSTR (EDUSTART, 1, 8),\n");
            //			sql.append("                                               'YYYYMMDD'\n");
            //			sql.append("                                              ),\n");
            //			sql.append("                                    'YYYYMMDD'\n");
            //			sql.append("                                   )\n");
            //			sql.append("                          ) > TO_CHAR (SYSDATE, 'YYYYMMDD')\n");
            //			sql.append("                        THEN 'Y'\n");
            //			sql.append("                     ELSE 'N'\n");
            //			sql.append("                  END CANCELABLEYN,\n");
            sql.append("                  CASE WHEN (    SUBSTR (REPLACE(STARTCANCELDATE,'-'), 1, 8) <= TO_CHAR (SYSDATE, 'YYYYMMDD')\n");
            sql.append("                             AND SUBSTR (REPLACE(ENDCANCELDATE,'-'), 1, 8) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )\n");
            sql.append("                       THEN 'Y'\n");
            sql.append("                       ELSE 'N'\n");
            sql.append("                  END CANCELABLEYN,\n");
            sql.append("                  RANK () OVER (PARTITION BY A.TID ORDER BY A.TID,\n");
            sql.append("                   B.SUBJ) RANK, B.BIYONG\n");
            //			sql.append("             FROM TZ_PROPOSE A, TZ_SUBJSEQ B, TZ_BILLINFO C, TZ_SUBJ D\n");
            sql.append("             FROM TZ_PROPOSE A\n");
            sql.append("             left join TZ_SUBJSEQ B on A.SUBJ = B.SUBJ and A.YEAR = B.YEAR and A.SUBJSEQ = B.SUBJSEQ\n");
            sql.append("             left join TZ_BILLINFO C on A.TID = C.TID\n");
            sql.append("             left join  TZ_SUBJ D on A.SUBJ = D.SUBJ\n");
            sql.append("            WHERE \n");
            sql.append("               A.USERID = ").append(SQLString.Format(v_userid)).append("\n");
            sql.append("              AND b.grcode = ").append(SQLString.Format(box.getSession("tem_grcode"))).append("\n");

            if (!v_upperclass.equals("ALL"))
                sql.append(" AND D.UPPERCLASS = ").append(SQLString.Format(v_upperclass)).append("\n");

            sql.append("           UNION ALL\n");
            sql.append("           SELECT d.introducefilenamenew, A.TID, B.SUBJ, B.YEAR, B.SUBJSEQ, B.SUBJNM, B.EDUSTART,  \n");
            sql.append("                  B.EDUEND, A.APPDATE, 'N' ACCEPTYN, 'Y' REFUNDYN, '' CHKFIRST, '' CHKFINAL,\n");
            sql.append("                  A.CANCELDATE, C.PAYMETHOD, D.UPPERCLASS, A.REFUNDDATE,\n");
            sql.append("                  TO_CHAR (  TO_DATE (SUBSTR (EDUSTART, 1, 8), 'YYYYMMDD')\n");
            sql.append("                           + B.CANCELDAYS,\n");
            sql.append("                           'YYYYMMDD'\n");
            sql.append("                          ) AS REFUNDABLEDATE,\n");
            sql.append("                  'N' REFUNDABLEYN,\n");
            sql.append("                  'N' CALCELABLEYN,\n");
            sql.append("                  RANK () OVER (PARTITION BY A.TID ORDER BY A.TID,\n");
            sql.append("                   B.SUBJ) RANK, B.BIYONG\n");
            sql.append("             FROM TZ_CANCEL A, TZ_SUBJSEQ B, TZ_BILLINFO C, TZ_SUBJ D\n");
            sql.append("            WHERE (1 = 1)\n");
            sql.append("              AND A.TID = C.TID\n");
            sql.append("              AND A.USERID = ").append(SQLString.Format(v_userid)).append("\n");
            sql.append("              AND A.SUBJ = B.SUBJ\n");
            sql.append("              AND A.YEAR = B.YEAR\n");
            sql.append("              AND A.SUBJSEQ = B.SUBJSEQ\n");
            sql.append("              AND B.grcode = ").append(SQLString.Format(box.getSession("tem_grcode"))).append("\n");
            sql.append("              AND A.SUBJ = D.SUBJ\n");

            if (!v_upperclass.equals("ALL"))
                sql.append(" AND D.UPPERCLASS = ").append(SQLString.Format(v_upperclass)).append("\n");

            sql.append("		    ) A\n");
            sql.append(" ORDER BY EDUSTART DESC \n");

            ls = connMgr.executeQuery(sql.toString());

            String count_sql1 = "";
            count_sql1 = "select count(*) from ( " + sql.toString() + ")";
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); // 전체 row 수를 반환한다

            ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

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
     * 취소신청 과정 목록조회
     *
     * @param box receive from the form object and session
     * @return ArrayList 취소신청 과정 목록 조회
     */
    public ArrayList<DataBox> selectSubjnmList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();

        String v_tid = box.getStringDefault("p_tid", "");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql.append(" SELECT B.SUBJ, B.SUBJNM\n");
            sql.append("   FROM TZ_PROPOSE A, TZ_SUBJSEQ B\n");
            sql.append("  WHERE A.SUBJSEQ = B.SUBJSEQ\n");
            sql.append("    AND A.YEAR = B.YEAR\n");
            sql.append("    AND A.SUBJ = B.SUBJ\n");
            sql.append("    AND A.TID = ").append(SQLString.Format(v_tid)).append("\n");

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
     * 수강신청취소 신청
     *
     * @param box receive from the form object and session
     * @return int
     */
    public int updateProposeCancelApply(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;

        String v_tid = box.getString("p_tid");
        String v_refundbank = box.getString("p_refundbank");
        String v_refundaccount = box.getString("p_refundaccount");
        String v_refundname = box.getString("p_refundname");
        String v_cancelreason = box.getString("p_cancelreason");
        String v_user_id = box.getSession("userid");
        String v_biyong = "";
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        // String v_temcode = "";

        try {
            connMgr = new DBConnectionManager();

            //===========무료인과정과 유료인 과정을 검색후 무료이면 삭제 유료이면 관리자로 승인처리 기다림
            sql1 = "select a.subj,a.year,a.subjseq,a.ASP_GUBUN,b.biyong \n";
            sql1 += "from tz_propose a\n";
            sql1 += "left join tz_subjseq b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq \n";
            sql1 += "where a.tid='" + v_tid + "'";

            ls = connMgr.executeQuery(sql1);
            while (ls.next()) {
                v_subj = ls.getString("subj");
                v_year = ls.getString("year");
                v_subjseq = ls.getString("subjseq");
                v_biyong = ls.getString("biyong");
                // v_temcode = ls.getString("ASP_GUBUN");
            }

            if (v_biyong.equals("0")) {
                box.setSession("msg", "propcancel.delete.ok"); // 취소가 완료되면 나오는 메시지를 구분하기 위해서

                sql2 = "delete from TZ_STUDENT where subj = ? and year = ? and subjseq = ? and userid = ?  ";
                pstmt2 = connMgr.prepareStatement(sql2);
                pstmt2.setString(1, v_subj);
                pstmt2.setString(2, v_year);
                pstmt2.setString(3, v_subjseq);
                pstmt2.setString(4, v_user_id);
                isOk = pstmt2.executeUpdate();

                sql2 = "delete from TZ_PROPOSE where subj = ? and year = ? and subjseq = ? and userid = ? ";

                pstmt2 = connMgr.prepareStatement(sql2);
                pstmt2.setString(1, v_subj);
                pstmt2.setString(2, v_year);
                pstmt2.setString(3, v_subjseq);
                pstmt2.setString(4, v_user_id);
                isOk += pstmt2.executeUpdate();
            } else {
                sql2 = " UPDATE tz_propose\n";
                sql2 += "    SET cancelkind    = 'P',\n";
                sql2 += "        refundbank    = ?,\n";
                sql2 += "        refundaccount = ?,\n";
                sql2 += "        refundname    = ?,\n";
                sql2 += "        cancelreason  = ?,\n";
                sql2 += "        canceldate    = TO_CHAR (SYSDATE, 'yyyymmddhh24miss'),\n";
                sql2 += "        luserid       = ?,\n";
                sql2 += "        ldate         = TO_CHAR (SYSDATE, 'yyyymmddhh24miss')\n";
                sql2 += "  WHERE tid = ?\n";

                pstmt2 = connMgr.prepareStatement(sql2);

                pstmt2.setString(1, v_refundbank);
                pstmt2.setString(2, v_refundaccount);
                pstmt2.setString(3, v_refundname);
                pstmt2.setCharacterStream(4, new StringReader(v_cancelreason), v_cancelreason.length());
                pstmt2.setString(5, v_user_id);
                pstmt2.setString(6, v_tid);
                isOk = pstmt2.executeUpdate();
            }

        } catch (Exception ex) {
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
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
     * off-line 수강신청 이력 리스트 조회 - 수강신청 확인 취소 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList off-line 수강신청 확인 취소 리스트
     */
    public ArrayList<DataBox> selectProposeOffHistoryList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String v_userid = box.getSession("userid");
        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");

        //int	v_pageno		= box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT   tid, subj, YEAR, subjseq, subjnm, edustart, eduend, appdate, seq,";
            sql += "          (SELECT sa.classname\n";
            sql += "             FROM tz_offsubj sb, tz_offsubjatt sa\n";
            sql += "            WHERE sb.upperclass = sa.upperclass\n";
            sql += "              AND sa.middleclass = '000'\n";
            sql += "              AND sa.lowerclass = '000'\n";
            sql += "              AND sb.subj = a.subj) classname,\n";
            sql += "          refundabledate, refundableyn, refundyn, canceldate, paymethod, cancelableyn, refunddate,";
            sql += "          chkfirst, chkfinal, RANK,\n";
            sql += "          CASE\n";
            sql += "             WHEN RANK = 1\n";
            sql += "                THEN COUNT (*) OVER (PARTITION BY tid)\n";
            sql += "             ELSE 0\n";
            sql += "          END AS rowspan, BIYONG\n";
            sql += "     FROM (SELECT a.tid, b.subj, b.YEAR, b.subjseq, d.subjnm, b.edustart,\n";
            sql += "                  b.eduend, a.appdate, a.chkfirst, a.chkfinal, a.seq,\n";
            sql += "                  CASE WHEN REFUNDDATE IS NOT NULL\n";
            sql += "                       THEN 'Y'\n";
            sql += "                       ELSE 'N'\n";
            sql += "                  END refundyn,\n";
            sql += "                  a.canceldate, c.paymethod, d.upperclass, '' refunddate,";
            sql += "                  endcanceldate AS refundabledate,\n";
            //sql += "                  CASE WHEN (    TO_CHAR(TO_DATE(substr(startcanceldate,1,8))) <= TO_CHAR (SYSDATE, 'YYYYMMDD')\n";
            //sql += "                             AND TO_CHAR(TO_DATE(substr(endcanceldate,1,8))) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )\n";
            sql += "                  CASE WHEN (    SUBSTR (startcanceldate, 1, 8) <= TO_CHAR (SYSDATE, 'YYYYMMDD')\n";
            sql += "                             AND SUBSTR (endcanceldate, 1, 8) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )\n";
            sql += "                       THEN 'Y'\n";
            sql += "                       ELSE 'N'\n";
            sql += "                  END refundableyn,\n";
            sql += "                  CASE WHEN (    SUBSTR (startcanceldate, 1, 8) <= TO_CHAR (SYSDATE, 'YYYYMMDD')\n";
            sql += "                             AND SUBSTR (endcanceldate, 1, 8) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )\n";
            sql += "                       THEN 'Y'\n";
            sql += "                       ELSE 'N'\n";
            sql += "                  END cancelableyn,\n";
            //sql += "                  CASE WHEN  SUBSTR (edustart, 1, 8) > TO_CHAR (SYSDATE, 'YYYYMMDD')\n";
            //sql += "                       THEN 'Y'\n";
            //sql += "                       ELSE 'N'\n";
            //sql += "                  END cancelableyn,\n";
            sql += "                  RANK () OVER (PARTITION BY a.tid ORDER BY a.tid,\n";
            sql += "                  b.subj) RANK, B.BIYONG, B.PROPSTART\n";
            sql += "             FROM tz_offpropose a, tz_offsubjseq b, tz_offbillinfo c, tz_offsubj d\n";
            sql += "            WHERE (1 = 1)\n";
            sql += "              AND a.tid = c.tid(+)\n";
            sql += "              AND a.userid = " + SQLString.Format(v_userid);
            sql += "              AND a.subj = b.subj\n";
            sql += "              AND a.YEAR = b.YEAR\n";
            sql += "              AND a.subjseq = b.subjseq\n";
            sql += "              AND b.seq = '1'\n";

            if (!v_upperclass.equals("ALL"))
                sql += " and d.upperclass = " + SQLString.Format(v_upperclass) + "\n";

            sql += "              AND a.subj = d.subj\n";
            sql += "            UNION ALL\n";
            sql += "           SELECT a.tid, b.subj, b.YEAR, b.subjseq, d.subjnm, b.edustart,\n";
            sql += "                  b.eduend, a.appdate, '' chkfirst, '' chkfinal, a.seq,\n";
            sql += "                  CASE WHEN REFUNDDATE IS NOT NULL\n";
            sql += "                       THEN 'Y'\n";
            sql += "                       ELSE 'N'\n";
            sql += "                  END refundyn,\n";
            sql += "                  a.canceldate, c.paymethod, d.upperclass, a.refunddate,";
            sql += "                  endcanceldate AS refundabledate,\n";
            //sql += "                  CASE WHEN (    TO_CHAR(TO_DATE(substr(startcanceldate,1,8))) <= TO_CHAR (SYSDATE, 'YYYYMMDD')\n";
            //sql += "                             AND TO_CHAR(TO_DATE(substr(endcanceldate,1,8))) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )\n";
            sql += "                  'N' refundableyn, 'N' cancelableyn,\n";
            sql += "                  RANK () OVER (PARTITION BY a.tid ORDER BY a.tid,\n";
            sql += "                  b.subj) RANK, B.BIYONG, B.PROPSTART\n";
            sql += "             FROM tz_offcancel a, tz_offsubjseq b, tz_offbillinfo c, tz_offsubj d\n";
            sql += "            WHERE (1 = 1)\n";
            sql += "              AND a.tid = c.tid(+)\n";
            sql += "              AND a.userid = " + SQLString.Format(v_userid);
            sql += "              AND a.subj = b.subj\n";
            sql += "              AND a.YEAR = b.YEAR\n";
            sql += "              AND a.subjseq = b.subjseq\n";
            sql += "              AND b.seq = '1'\n";

            if (!v_upperclass.equals("ALL"))
                sql += " and d.upperclass = " + SQLString.Format(v_upperclass) + "\n";

            sql += "              AND a.subj = d.subj) a\n";
            sql += " WHERE TO_CHAR (SYSDATE, 'YYYYMMDDHH24') BETWEEN A.PROPSTART AND  A.EDUEND \n";
            sql += " ORDER BY edustart DESC, tid, subj\n";

            System.out.println("sql_proposeoffhostorylist============>" + sql);

            ls = connMgr.executeQuery(sql);

            // String count_sql1 = "";
            // count_sql1 = "select count(*) from ( " + sql.toString() + ")";
            // int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); // 전체 row 수를 반환한다

            //ls.setPageSize(row);                            // 페이지당 row 갯수를 세팅한다
            //ls.setCurrentPage(v_pageno,total_row_count);                    // 현재페이지번호를 세팅한다.
            //int total_page_count = ls.getTotalPage();       // 전체 페이지 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();

                //dbox.put("d_dispnum",   new Integer(total_row_count - ls.getRowNum() + 1));
                //dbox.put("d_totalpage", new Integer(total_page_count));
                //dbox.put("d_rowcount",  new Integer(row));

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
     * off-line 취소신청 과정 목록조회
     *
     * @param box receive from the form object and session
     * @return ArrayList off-line 취소신청 과정 목록 조회
     */
    public ArrayList<DataBox> selectOffSubjnmList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String v_tid = box.getStringDefault("p_tid", "");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT b.subj, b.subjnm\n";
            sql += "   FROM tz_offpropose a, tz_offsubjseq b\n";
            sql += "  WHERE a.subjseq = b.subjseq\n";
            sql += "    AND a.YEAR = b.YEAR\n";
            sql += "    AND a.subj = b.subj\n";
            sql += "    AND b.seq  = '1'";
            sql += "    AND a.tid = " + SQLString.Format(v_tid);

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
     * 수강신청취소 신청
     *
     * @param box receive from the form object and session
     * @return int
     */
    public int updateProposeOffCancelApply(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        StringBuffer sql2 = new StringBuffer();
        int isOk = 0;

        String v_tid = box.getString("p_tid");
        String v_refundbank = box.getString("p_refundbank");
        String v_refundaccount = box.getString("p_refundaccount");
        String v_refundname = box.getString("p_refundname");
        String v_cancelreason = box.getString("p_cancelreason");
        String v_user_id = box.getSession("userid");

        String v_paymethod = box.getString("p_paymethod");

        if ("FreePay|BankBook".indexOf(v_paymethod) < 0) {
            v_refundbank = "";
            v_refundaccount = "";
            v_refundname = "";
        }

        try {
            connMgr = new DBConnectionManager();
            sql2.append(" UPDATE TZ_OFFPROPOSE\n");
            sql2.append("    SET CANCELKIND    = 'P',\n");
            sql2.append("        REFUNDBANK    = ?,\n");
            sql2.append("        REFUNDACCOUNT = ?,\n");
            sql2.append("        REFUNDNAME    = ?,\n");
            sql2.append("        CANCELREASON  = ?,\n");
            sql2.append("        CANCELDATE    = TO_CHAR (SYSDATE, 'yyyymmddhh24miss'),\n");
            sql2.append("        LUSERID       = ?,\n");
            sql2.append("        LDATE         = TO_CHAR (SYSDATE, 'yyyymmddhh24miss')\n");
            sql2.append("  WHERE TID = ?\n");

            pstmt2 = connMgr.prepareStatement(sql2.toString());

            pstmt2.setString(1, v_refundbank);
            pstmt2.setString(2, v_refundaccount);
            pstmt2.setString(3, v_refundname);
            pstmt2.setCharacterStream(4, new StringReader(v_cancelreason), v_cancelreason.length());
            pstmt2.setString(5, v_user_id);
            pstmt2.setString(6, v_tid);
            isOk = pstmt2.executeUpdate();

        } catch (Exception ex) {
            throw new Exception("sql2 = " + sql2.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
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
     * 수강신청취소
     *
     * @param box receive from the form object and session
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int updateProposeCancel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt4 = null;
        PreparedStatement pstmt5 = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ListSet ls3 = null;
        String sql1 = "";
        String sql3 = "";
        String sql4 = "";
        String sql5 = "";
        String sql0 = "";
        int cancel_cnt = 0;
        int bill_cnt = 0;
        int isOk1 = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        int isOk5 = 0;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_user_id = box.getSession("userid");
        String v_course = box.getString("p_course");
        String v_iscourseYn = box.getString("p_iscourseyn");
        String v_eduterm = "";

        String v_grtype = "";
        String v_subj_value = "";
        String v_grcode = box.getSession("tem_grcode");

        v_grtype = GetCodenm.get_grtype(box, v_grcode);

        Hashtable insertData = new Hashtable();
        ProposeBean probean = new ProposeBean();

        SubjComBean scbean = new SubjComBean();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            if (v_iscourseYn.equals("Y")) {
                v_subj_value = v_course;
            } else {
                v_subj_value = v_subj;
            }

            sql0 = " Select subj From VZ_SCSUBJSEQ\n";
            sql0 += " Where	scsubj = '" + v_subj_value + "' and\n";
            sql0 += " 	subjseq = '" + v_subjseq + "'  and year = '" + v_year + "'\n";

            ls2 = connMgr.executeQuery(sql0);

            while (ls2.next()) {
                v_subj = ls2.getString("subj");
                System.out.println("v_subj = " + v_subj);
                //update TZ_PROPOSE table
                v_eduterm = scbean.getEduTerm(v_subj, v_subjseq, v_year);

                insertData.clear();
                insertData.put("connMgr", connMgr);
                insertData.put("subj", v_subj);
                insertData.put("subjseq", v_subjseq);
                insertData.put("year", v_year);
                insertData.put("userid", v_user_id);
                insertData.put("cancelkind", "P");
                insertData.put("chkfinal", "N");
                insertData.put("rejectedkind", "01");
                insertData.put("rejectedreason", "본인요구");
                insertData.put("gubun", "2");
                insertData.put("luserid", v_user_id);

                if (v_eduterm.equals("1") || v_eduterm.equals("2") || v_eduterm.equals("3")) {
                    isOk1 = probean.deletePropose(insertData);
                    isOk2 = probean.deleteStudent(insertData);
                    isOk3 = 1;

                    System.out.println("delete : isOk1 = " + isOk1);
                    System.out.println("delete : isOk2 = " + isOk2);
                } else if (v_eduterm.equals("4")) {
                    isOk1 = probean.updatePropose(insertData);
                    isOk2 = probean.updateStudent(insertData);

                    System.out.println("update : isOk1 = " + isOk1);
                    System.out.println("update : isOk2 = " + isOk2);
                    sql3 = "select count(subj) cnt from TZ_CANCEL where subj=" + SQLString.Format(v_subj) + " and year=" + SQLString.Format(v_year);
                    sql3 += " and subjseq=" + SQLString.Format(v_subjseq) + " and userid=" + SQLString.Format(v_user_id);
                    ls3 = connMgr.executeQuery(sql3);

                    if (ls3.next()) {
                        cancel_cnt = ls3.getInt("cnt");
                    } //취소한 횟수

                    if (cancel_cnt == 0) { //취소이력이 없는 경우
                        //insert TZ_CANCEL table
                        sql4 = "insert into TZ_CANCEL (subj,year,subjseq,userid,seq,cancelkind,canceldate,luserid,ldate)\n";
                        sql4 += " values(?,?,?,?,1,'P',to_char(sysdate,'YYYYMMDDHH24MISS'),?,to_char(sysdate,'YYYYMMDDHH24MISS'))";
                        pstmt4 = connMgr.prepareStatement(sql4);
                        pstmt4.setString(1, v_subj);
                        pstmt4.setString(2, v_year);
                        pstmt4.setString(3, v_subjseq);
                        pstmt4.setString(4, v_user_id);
                        pstmt4.setString(5, v_user_id);

                        isOk3 = pstmt4.executeUpdate();
                        System.out.println("isOk3 = " + isOk3);
                    } else if (cancel_cnt > 0) {
                        //update TZ_CANCEL table
                        sql4 = "update TZ_CANCEL set seq=?\n";
                        sql4 += " where subj=? and year=? and subjseq=? and userid=?\n";
                        pstmt4 = connMgr.prepareStatement(sql4);
                        pstmt4.setInt(1, cancel_cnt + 1);
                        pstmt4.setString(2, v_subj);
                        pstmt4.setString(3, v_year);
                        pstmt4.setString(4, v_subjseq);
                        pstmt4.setString(5, v_user_id);

                        isOk3 = pstmt4.executeUpdate();
                        System.out.println("isOk3 = " + isOk3);
                    }
                }
                if (isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                    connMgr.commit();
                } else {
                    connMgr.rollback();
                }
            }

            // 유료 과정일 경우 TZ_Billing 테이블 수정.
            sql3 = "select count(*) cnt from TZ_BILLING where subj=" + SQLString.Format(v_subj_value) + " and year=" + SQLString.Format(v_year);
            sql3 += " and subjseq=" + SQLString.Format(v_subjseq) + " and userid=" + SQLString.Format(v_user_id);
            sql3 += " and grtype=" + SQLString.Format(v_grtype);
            ls3 = connMgr.executeQuery(sql3);

            if (ls3.next()) {
                bill_cnt = ls3.getInt("cnt");
            } //취소한 횟수

            if (bill_cnt != 0) {
                sql5 = " Update TZ_BILLING\n";
                sql5 += " Set paystat = 'CW' Where subj=? and year=? and subjseq=? and userid=? and grtype=?";

                pstmt5 = connMgr.prepareStatement(sql5);
                pstmt5.setString(1, v_subj);
                pstmt5.setString(2, v_year);
                pstmt5.setString(3, v_subjseq);
                pstmt5.setString(4, v_user_id);
                pstmt5.setString(5, v_grtype);
                isOk5 = pstmt5.executeUpdate();
            } else {
                isOk5 = 1;
            }
            System.out.println("isOk3 = " + isOk3);

            if (isOk1 > 0 && isOk2 > 0 && isOk3 > 0 && isOk5 > 0)
                connMgr.commit();
            else
                connMgr.rollback();
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls3.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt4 != null) {
                try {
                    pstmt4.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt5 != null) {
                try {
                    pstmt5.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
                }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1 * isOk2 * isOk3;
    }

    /**
     * 수강신청취소 2010.01.26
     *
     * @param box receive from the form object and session
     * @return int
     */
    public int updateCancelPropose(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;

        cs = new ConfigSet();

        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        PreparedStatement pstmt5 = null;
        PreparedStatement pstmt6 = null;
        PreparedStatement pstmt7 = null;
        PreparedStatement pstmt8 = null;
        PreparedStatement pstmt9 = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ListSet ls3 = null;
        ListSet ls4 = null;
        ListSet ls5 = null;
        ListSet ls6 = null;
        ListSet ls7 = null;
        ListSet ls8 = null;
        ListSet ls9 = null;
        StringBuffer sql1 = new StringBuffer();
        StringBuffer sql2 = new StringBuffer();
        StringBuffer sql3 = new StringBuffer();
        StringBuffer sql4 = new StringBuffer();
        StringBuffer sql5 = new StringBuffer();
        StringBuffer sql6 = new StringBuffer();
        StringBuffer sql7 = new StringBuffer();
        StringBuffer sql8 = new StringBuffer();
        StringBuffer sql9 = new StringBuffer();
        int cancel_cnt = 0;
        int bill_cnt = 0;
        int isOk1 = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        int isOk4 = 0;
        int isOk5 = 0;
        int isOk6 = 0;
        int isOk7 = 0;
        int isOk8 = 0;
        int isOk9 = 0;
        String v_tid = box.getString("p_tid");
        String v_reason = box.getString("p_reason");

        if ("".equals(v_reason)) {
            v_reason = "본인수강취소";
        }

        String v_grtype = "";
        String v_grcode = box.getSession("tem_grcode");

        String v_paymethod = box.getString("p_paymethod");

        v_grtype = GetCodenm.get_grtype(box, v_grcode);

        String mid = null;
        String resultCode = null;
        String resultMsg = null;
        String cancelDate = null;
        String cancelTime = null;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            //1.수강신청 데이터 처리

            //1.1 tid 를 이용하여 수강신청 데이터를 조회한다
            sql1.append(" SELECT subj, YEAR, subjseq, userid, tid, refundbank, refundaccount,\n");
            sql1.append("        refundname, cancelreason, canceldate, cancelkind\n");
            sql1.append("   FROM tz_propose\n");
            sql1.append("  WHERE tid = ").append(SQLString.Format(v_tid));

            ls1 = connMgr.executeQuery(sql1.toString());

            sql1.setLength(0);

            isOk1 = 1;

            //1.2 조회된 데이터를 사용하여 수강신청 취소 테이블 insert or update
            while (ls1.next()) {
                String v_subj = ls1.getString("subj");
                String v_year = ls1.getString("year");
                String v_subjseq = ls1.getString("subjseq");
                String v_user_id = ls1.getString("userid");

                sql2.append("select count(subj) cnt from TZ_CANCEL where subj=" + SQLString.Format(v_subj) + " and year=" + SQLString.Format(v_year));
                sql2.append(" and subjseq=" + SQLString.Format(v_subjseq) + " and userid=" + SQLString.Format(v_user_id));

                ls2 = connMgr.executeQuery(sql2.toString());

                sql2.setLength(0);

                isOk2 = 1;

                if (ls2.next()) {
                    cancel_cnt = ls2.getInt("cnt");
                } //취소한 횟수

                if (cancel_cnt == 0) { //취소이력이 없는 경우
                    //insert TZ_CANCEL table
                    sql3.append("insert into TZ_CANCEL (subj,year,subjseq,userid,seq,cancelkind,canceldate,luserid,ldate,reason,tid,refunddate)\n");
                    sql3.append(" values(?,?,?,?,1,'P',to_char(sysdate,'YYYYMMDDHH24MISS'),?,to_char(sysdate,'YYYYMMDDHH24MISS'),?,?,?)\n");

                    pstmt3 = connMgr.prepareStatement(sql3.toString());

                    pstmt3.setString(1, v_subj);
                    pstmt3.setString(2, v_year);
                    pstmt3.setString(3, v_subjseq);
                    pstmt3.setString(4, v_user_id);
                    pstmt3.setString(5, v_user_id);
                    pstmt3.setString(6, v_reason);
                    pstmt3.setString(7, v_tid);
                    pstmt3.setString(8, FormatDate.getDate("yyyyMMddHHmmss"));

                    isOk3 = pstmt3.executeUpdate();

                    sql3.setLength(0);

                    System.out.println("isOk3 = " + isOk3);

                } else if (cancel_cnt > 0) {
                    //update TZ_CANCEL table
                    sql3.append("update TZ_CANCEL set seq=? , tid=?\n");
                    sql3.append(" where subj=? and year=? and subjseq=? and userid=?\n");

                    pstmt3 = connMgr.prepareStatement(sql3.toString());

                    pstmt3.setInt(1, cancel_cnt + 1);
                    pstmt3.setString(2, v_tid);
                    pstmt3.setString(3, v_subj);
                    pstmt3.setString(4, v_year);
                    pstmt3.setString(5, v_subjseq);
                    pstmt3.setString(6, v_user_id);

                    isOk3 = pstmt3.executeUpdate();

                    sql3.setLength(0);

                    System.out.println("isOk3 = " + isOk3);
                }
            }
            ls1.moveFirst();

            //1.3 조회된 데이터를 사용하여 수강신청 테이블 삭제
            while (ls1.next()) {
                String v_subj = ls1.getString("subj");
                String v_year = ls1.getString("year");
                String v_subjseq = ls1.getString("subjseq");
                String v_user_id = ls1.getString("userid");

                sql4.append("delete from TZ_PROPOSE where subj = ? and year = ? and subjseq = ? and userid = ?\n");

                pstmt4 = connMgr.prepareStatement(sql4.toString());

                pstmt4.setString(1, v_subj);
                pstmt4.setString(2, v_year);
                pstmt4.setString(3, v_subjseq);
                pstmt4.setString(4, v_user_id);

                //				System.out.println("######################");
                //				System.out.println("##sql4##" + sql4);
                //				System.out.println("##v_subj##" + v_subj);
                //				System.out.println("##v_year##" + v_year);
                //				System.out.println("##v_subjseq##" + v_subjseq);
                //				System.out.println("##v_user_id##" + v_user_id);
                //				System.out.println("######################");

                isOk4 = pstmt4.executeUpdate();

                sql4.setLength(0);
            }
            ls1.moveFirst();

            //1.4 조회된 데이터를 사용하여 수강생 테이블 삭제
            while (ls1.next()) {
                String v_subj = ls1.getString("subj");
                String v_year = ls1.getString("year");
                String v_subjseq = ls1.getString("subjseq");
                String v_user_id = ls1.getString("userid");

                sql5.append("delete from TZ_STUDENT where subj = ? and year = ? and subjseq = ? and userid = ?\n");

                pstmt5 = connMgr.prepareStatement(sql5.toString());

                pstmt5.setString(1, v_subj);
                pstmt5.setString(2, v_year);
                pstmt5.setString(3, v_subjseq);
                pstmt5.setString(4, v_user_id);

                isOk5 = pstmt5.executeUpdate();

                sql5.setLength(0);
            }
            isOk5 = 1;
            ls1.moveFirst();

            //2.유료과정 처리

            //2.1 유료과정인지 확인하여 유료과정 데이터 처리
            // 유료 과정일 경우 TZ_Billing 테이블 수정.
            while (ls1.next()) {
                String v_subj = ls1.getString("subj");
                String v_year = ls1.getString("year");
                String v_subjseq = ls1.getString("subjseq");
                String v_user_id = ls1.getString("userid");

                sql6.append("select count(*) cnt from TZ_BILLING where subj=" + SQLString.Format(v_subj) + " and year=" + SQLString.Format(v_year));
                sql6.append(" and subjseq=" + SQLString.Format(v_subjseq) + " and userid=" + SQLString.Format(v_user_id));
                sql6.append(" and grtype=" + SQLString.Format(v_grtype));

                ls6 = connMgr.executeQuery(sql6.toString());

                if (ls6.next()) {
                    bill_cnt = ls6.getInt("cnt");
                } //취소한 횟수

                sql6.setLength(0);

                if (bill_cnt != 0) {
                    sql6.append(" Update TZ_BILLING\n");
                    sql6.append(" Set paystat = 'CW' Where subj=? and year=? and subjseq=? and userid=? and grtype=?\n");

                    pstmt6 = connMgr.prepareStatement(sql6.toString());

                    pstmt6.setString(1, v_subj);
                    pstmt6.setString(2, v_year);
                    pstmt6.setString(3, v_subjseq);
                    pstmt6.setString(4, v_user_id);
                    pstmt6.setString(5, v_grtype);

                    isOk6 = pstmt6.executeUpdate();

                    sql6.setLength(0);
                } else {
                    isOk6 = 1;
                }
            }
            ls1.moveFirst();

            //2.2 포인트 처리
            int usepoint = 0;

            sql9.append("select usepoint from TZ_POINTUSE where tid=" + SQLString.Format(v_tid));

            ls9 = connMgr.executeQuery(sql9.toString());

            if (ls9.next()) {
                usepoint = ls9.getInt("usepoint"); //사용포인트
            }

            sql9.setLength(0);

            String expiredate = null; // 보유 포인트중 최종 사용기한일

            sql9.append(" SELECT expiredate FROM TZ_POINTGET A WHERE userid = " + SQLString.Format(box.getSession("userid")));
            sql9.append(" AND getpoint > nvl(usepoint, 0) and to_char(sysdate, 'YYYYMMDD') <= nvl(expiredate,TO_CHAR(to_date(substr(getdate,1,8),'yyyymmdd') + 365,'yyyymmdd'))\n");
            sql9.append(" AND ROWNUM = 1 ORDER BY EXPIREDATE DESC\n");

            ls9 = connMgr.executeQuery(sql9.toString());

            if (ls9.next()) {
                expiredate = ls9.getString("expiredate");
            }

            sql9.setLength(0);

            if (usepoint != 0) { //사용포인트가 있으면
                //insert TZ_POINTGET table
                sql9.append("insert into TZ_POINTGET (tid,userid,getpoint,getdate,title,subj,year,subjseq,luserid,ldate,usepoint,expiredate)\n");
                sql9.append(" values(to_char(sysdate,'YYYYMMDDHH24MISS'),?,?,to_char(sysdate,'YYYYMMDDHH24MISS'),'환불로인한 환원',null,null,null,?,to_char(sysdate,'YYYYMMDDHH24MISS'),null,?)\n");

                pstmt9 = connMgr.prepareStatement(sql9.toString());

                pstmt9.setString(1, box.getSession("userid"));
                pstmt9.setInt(2, usepoint);
                pstmt9.setString(3, box.getSession("userid"));
                pstmt9.setString(4, expiredate);

                isOk9 = pstmt9.executeUpdate();

                sql9.setLength(0);

            } else {
                isOk9 = 1;
            }

            // 무료결제가 아닐경우 inipay 결제취소 요청
            if (!v_paymethod.equals("FreePay")) {
                if (isOk3 * isOk4 * isOk5 * isOk6 * isOk9 != 0) {

                    //3.결제 정보 처리

                    //3.1  환불 프로세스 처리(결제취소)
                    System.out.println("##결제취소 시작##");

                    /**************************************
                     * 1. INIpay41 클래스의 인스턴스 생성 *
                     **************************************/
                    INIpay50 inipay = new INIpay50();

                    /*********************
                     * 2. 지불 정보 설정 *
                     *********************/
                    inipay.SetField("inipayhome", cs.getProperty("inipay.inipayHome")); // INIpay Home (절대경로로 적절히 수정) - C:/INIpay41_JAVA
                    inipay.SetField("type", "cancel"); // 고정 (절대 수정 불가)
                    inipay.SetField("debug", cs.getProperty("inipay.debug")); // 로그모드("true"로 설정하면 상세한 로그가 생성됨)

                    // 상점아이디 : test.kocca.or.kr일 경우 테스트 상점아이디를 가져온다.
                    //					if(1==1 || box.get("request.serverName").indexOf("test.kocca.or.kr") != -1)
                    //						mid = cs.getProperty("inipay.mid.test");	// 테스트 상점아이디
                    //					else

                    mid = cs.getProperty("inipay.mid.real"); // 실제 상점아이디

                    inipay.SetField("mid", mid);

                    inipay.SetField("admin", cs.getProperty("inipay.keyPw")); // 키패스워드(상점아이디에 따라 변경) - 1111
                    inipay.SetField("tid", v_tid); // 취소할 거래의 거래아이디
                    inipay.SetField("cancelmsg", "수강취소"); // 취소사유

                    /****************
                     * 3. 취소 요청 *
                     ****************/
                    inipay.startAction();

                    /****************************************************************
                     * 4. 취소 결과
                     *
                     * 결과코드 : inipay.GetResult("ResultCode") ("00"이면 취소 성공) 결과내용
                     * : inipay.GetResult("ResultMsg") (취소결과에 대한 설명) 취소날짜 :
                     * inipay.GetResult("CancelDate") (YYYYMMDD) 취소시각 :
                     * inipay.GetResult("CancelTime") (HHMMSS) 현금영수증 취소 승인번호 :
                     * inipay.GetResult("CSHR_CancelNum") (현금영수증 발급 취소시에만 리턴됨)
                     ****************************************************************/
                    resultCode = inipay.GetResult("ResultCode"); // 결과코드 ("00"이면 취소 성공)
                    resultMsg = inipay.GetResult("ResultMsg"); // 결과내용 (취소결과에 대한 설명)
                    cancelDate = inipay.GetResult("CancelDate"); // (YYYYMMDD)
                    cancelTime = inipay.GetResult("CancelTime"); // (HHMMSS)

                    System.out.println("##resultCode##" + resultCode);
                    System.out.println("##resultMsg##" + resultMsg);
                    System.out.println("##cancelDate##" + cancelDate);
                    System.out.println("##cancelTime##" + cancelTime);
                    System.out.println("##결제취소 끝##");

                    /**************************
                     * 취소 실패시 롤백
                     **************************/
                    if (!"00".equals(resultCode)) {
                        connMgr.rollback();
                    }

                } else {
                    connMgr.rollback();

                    mid = cs.getProperty("inipay.mid.real");
                    resultCode = "33";
                    resultMsg = "결제취소 내부ERROR";
                }

            } else {

                if (isOk3 * isOk4 * isOk5 * isOk6 * isOk9 == 0) { // 무료결제 취소 실패
                    connMgr.rollback();

                    mid = cs.getProperty("inipay.mid.real");
                    resultCode = "44";
                    resultMsg = "무료 결제 취소 실패";
                } else { // 성공
                    mid = cs.getProperty("inipay.mid.real");
                    resultCode = "00";
                    resultMsg = "무료 결제 취소 성공";
                }
            }

            /****************
             * 5. 취소결과 저장 *
             ****************/
            //일련번호 구하기
            sql7.append(" select isnull(max(seq),0) + 1 maxseq from tz_billcancel where tid = " + SQLString.Format(v_tid));

            int maxseq = 0;

            ls7 = connMgr.executeQuery(sql7.toString());
            if (ls7.next()) {
                maxseq = ls7.getInt("maxseq");
            }

            sql7.setLength(0);

            //tb_billcancel에 저장
            sql7.append(" insert into tz_billcancel(\n");
            sql7.append(" 				tid,\n");
            sql7.append(" 				seq,\n");
            sql7.append(" 				mid,\n");
            sql7.append(" 				resultmsg,\n");
            sql7.append(" 				cancelyn,\n");
            sql7.append(" 				cancelresult,\n");
            sql7.append(" 				canceldate,\n");
            sql7.append(" 				canceltime,\n");
            sql7.append(" 				rcashcancelno,\n");
            sql7.append(" 				luserid,\n");
            sql7.append(" 				ldate )\n");
            sql7.append(" values(		?,?,?,?,?,?,?,?,?,?,?)\n");

            pstmt7 = connMgr.prepareStatement(sql7.toString());

            pstmt7.setString(1, v_tid); //거래번호
            pstmt7.setInt(2, maxseq); //취소일련번호
            pstmt7.setString(3, mid); //상점ID
            pstmt7.setString(4, resultMsg); //결과내용
            pstmt7.setString(5, "Y"); //취소여부
            pstmt7.setString(6, resultCode); //결과코드
            if (isOk3 * isOk4 * isOk5 * isOk6 * isOk9 != 0 && v_paymethod.equals("FreePay")) { // 무료 결제 취소 성공
                pstmt7.setString(7, FormatDate.getDate("yyyyMMdd")); //취소날짜
            } else {
                pstmt7.setString(7, cancelDate); //취소날짜
            }
            pstmt7.setString(8, cancelTime); //취소시간
            pstmt7.setString(9, ""); //현금영수증 번호
            pstmt7.setString(10, box.getSession("userid")); //사용자 ID
            pstmt7.setString(11, FormatDate.getDate("yyyyMMddHHmmss")); //날짜

            isOk7 = pstmt7.executeUpdate();

            sql7.setLength(0);

            if ("00".equals(resultCode)) { //취소 성공이면
                //tb_billinfo에 정보 UPdate
                //CANCELYN='Y',CANCELRESULT,CANCELDATE,CANCELTIME,RCASHCANCELNO
                sql8.append(" update 	tz_billinfo\n");
                sql8.append(" set		cancelyn 		= ?,\n");
                sql8.append(" 			cancelresult 	= ?,\n");
                sql8.append(" 			canceldate 		= ?,\n");
                sql8.append(" 			canceltime 		= ?,\n");
                sql8.append(" 			rcashcancelno 	= ?\n");
                sql8.append(" where	tid 			= ?\n");

                pstmt8 = connMgr.prepareStatement(sql8.toString());

                pstmt8.setString(1, "Y"); //취소여부
                pstmt8.setString(2, resultCode); //취소결과
                if (isOk3 * isOk4 * isOk5 * isOk6 * isOk9 != 0 && v_paymethod.equals("FreePay")) { // 무료 결제 취소 성공
                    pstmt8.setString(3, FormatDate.getDate("yyyyMMdd")); //취소날짜
                } else {
                    pstmt8.setString(3, cancelDate); //취소날짜
                }
                pstmt8.setString(4, cancelTime); //취소시간
                pstmt8.setString(5, ""); //현금영수증 번호
                pstmt8.setString(6, v_tid); //거래번호

                isOk8 = pstmt8.executeUpdate();

                sql8.setLength(0);

            } else {
                isOk8 = 0;
            }

            //			System.out.println("##isOk1##" + isOk1);
            //			System.out.println("##isOk2##" + isOk2);
            //			System.out.println("##isOk3##" + isOk3);
            //			System.out.println("##isOk4##" + isOk4);
            //			System.out.println("##isOk5##" + isOk5);
            //			System.out.println("##isOk6##" + isOk6);
            //			System.out.println("##isOk7##" + isOk7);
            //			System.out.println("##isOk8##" + isOk8);
            //			System.out.println("##isOk9##" + isOk9);

            if (isOk1 * isOk2 * isOk3 * isOk4 * isOk5 * isOk6 * isOk9 > 0) {
                //isOk7,isOk8 은 결제 후 정보기 때문에 실패결과를 저장하고 return 할때 0 을 리턴하여야 실패 alert 이 발생됨
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1.toString());
            throw new Exception("sql1 = " + sql1.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls3.close();
                } catch (Exception e) {
                }
            }
            if (ls4 != null) {
                try {
                    ls4.close();
                } catch (Exception e) {
                }
            }
            if (ls5 != null) {
                try {
                    ls5.close();
                } catch (Exception e) {
                }
            }
            if (ls6 != null) {
                try {
                    ls6.close();
                } catch (Exception e) {
                }
            }
            if (ls7 != null) {
                try {
                    ls7.close();
                } catch (Exception e) {
                }
            }
            if (ls8 != null) {
                try {
                    ls8.close();
                } catch (Exception e) {
                }
            }
            if (ls9 != null) {
                try {
                    ls9.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt4 != null) {
                try {
                    pstmt4.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt5 != null) {
                try {
                    pstmt5.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt6 != null) {
                try {
                    pstmt6.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt7 != null) {
                try {
                    pstmt7.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt8 != null) {
                try {
                    pstmt8.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt9 != null) {
                try {
                    pstmt9.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
                }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return isOk1 * isOk2 * isOk3 * isOk4 * isOk5 * isOk6 * isOk7 * isOk8 * isOk9;
    }

    /**
     * off-line 수강신청취소 2010.01.27
     *
     * @param box receive from the form object and session
     * @return int
     */
    public int updateCancelOffPropose(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;

        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        PreparedStatement pstmt5 = null;
        PreparedStatement pstmt6 = null;
        PreparedStatement pstmt7 = null;
        PreparedStatement pstmt8 = null;
        PreparedStatement pstmt9 = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ListSet ls3 = null;
        ListSet ls4 = null;
        ListSet ls5 = null;
        ListSet ls6 = null;
        ListSet ls7 = null;
        ListSet ls8 = null;
        ListSet ls9 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        // String sql4 = "";
        // String sql5 = "";
        String sql7 = "";
        String sql8 = "";
        String area = "";
        int cancel_cnt = 0;
        int isOk1 = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        int isOk4 = 0;
        int isOk5 = 0;
        // int isOk6 = 0;
        int isOk7 = 0;
        int isOk8 = 0;
        // int isOk9 = 0;
        String v_tid = box.getString("p_tid");
        String v_reason = box.getString("p_reason");

        if ("".equals(v_reason)) {
            v_reason = "본인수강취소";
        }

        //		String v_grtype =  "";
        //		String v_grcode = box.getSession("tem_grcode");

        //		v_grtype = GetCodenm.get_grtype(box,v_grcode);

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            //1.수강신청 데이터 처리

            //1.1 tid 를 이용하여 수강신청 데이터를 조회한다
            sql1 = " SELECT subj, YEAR, subjseq, seq, userid, tid, refundbank, refundaccount,\n";
            sql1 += "        refundname, cancelreason, canceldate, cancelkind, (SELECT AREA FROM TZ_OFFSUBJ WHERE SUBJ = A.SUBJ) AREA\n";
            sql1 += "   FROM tz_offpropose A\n";
            sql1 += "  WHERE tid = " + SQLString.Format(v_tid);

            ls1 = connMgr.executeQuery(sql1);

            isOk1 = 1;

            //1.2 조회된 데이터를 사용하여 수강신청 취소 테이블 insert or update
            while (ls1.next()) {
                area = ls1.getString("area");
                String v_subj = ls1.getString("subj");
                String v_year = ls1.getString("year");
                String v_subjseq = ls1.getString("subjseq");
                String v_seq = ls1.getString("seq");
                String v_user_id = ls1.getString("userid");

                sql2 = "select count(subj) cnt from TZ_OFFCANCEL where subj=" + SQLString.Format(v_subj) + " and year=" + SQLString.Format(v_year);
                sql2 += " and subjseq=" + SQLString.Format(v_subjseq) + " and propseq=" + SQLString.Format(v_seq) + " and userid=" + SQLString.Format(v_user_id);
                ls2 = connMgr.executeQuery(sql2);

                isOk2 = 1;

                if (ls2.next()) {
                    cancel_cnt = ls2.getInt("cnt");
                } //취소한 횟수

                if (cancel_cnt == 0) { //취소이력이 없는 경우
                    //insert TZ_CANCEL table
                    sql3 = "insert into TZ_OFFCANCEL (subj,year,subjseq,propseq,userid,seq,cancelkind,canceldate,luserid,ldate,reason,tid,refunddate)\n";
                    sql3 += " values(?,?,?,?,?,1,'P',to_char(sysdate,'YYYYMMDDHH24MISS'),?,to_char(sysdate,'YYYYMMDDHH24MISS'),?,?,?)";

                    pstmt3 = connMgr.prepareStatement(sql3);
                    pstmt3.setString(1, v_subj);
                    pstmt3.setString(2, v_year);
                    pstmt3.setString(3, v_subjseq);
                    pstmt3.setString(4, v_seq);
                    pstmt3.setString(5, v_user_id);
                    pstmt3.setString(6, v_user_id);
                    pstmt3.setString(7, v_reason);
                    pstmt3.setString(8, v_tid);
                    pstmt3.setString(9, FormatDate.getDate("yyyyMMddHHmmss"));

                    isOk3 = pstmt3.executeUpdate();
                    System.out.println("isOk3 = " + isOk3);

                } else if (cancel_cnt > 0) {
                    //update TZ_CANCEL table
                    sql3 = "update TZ_OFFCANCEL set seq=? , tid=?\n";
                    sql3 += " where subj=? and year=? and subjseq=? and propseq=? and userid=?\n";
                    pstmt3 = connMgr.prepareStatement(sql3);
                    pstmt3.setInt(1, cancel_cnt + 1);
                    pstmt3.setString(2, v_tid);
                    pstmt3.setString(3, v_subj);
                    pstmt3.setString(4, v_year);
                    pstmt3.setString(5, v_subjseq);
                    pstmt3.setString(6, v_seq);
                    pstmt3.setString(7, v_user_id);

                    isOk3 = pstmt3.executeUpdate();
                    System.out.println("isOk3 = " + isOk3);
                }
            }
            ls1.moveFirst();

            //1.3 조회된 데이터를 사용하여 수강신청 테이블 삭제
            /*
             * while(ls1.next()){ String v_subj = ls1.getString("subj"); String
             * v_year = ls1.getString("year"); String v_subjseq =
             * ls1.getString("subjseq"); String v_seq = ls1.getString("seq");
             * String v_user_id = ls1.getString("userid");
             *
             * sql4 =
             * "delete from TZ_OFFPROPOSE where subj = ? and year = ? and subjseq = ? and seq = ? and userid = ?\n"
             * ; pstmt4 = connMgr.prepareStatement(sql4); pstmt4.setString(1,
             * v_subj); pstmt4.setString(2, v_year); pstmt4.setString(3,
             * v_subjseq); pstmt4.setString(4, v_seq); pstmt4.setString(5,
             * v_user_id);
             *
             * System.out.println("######################");
             * System.out.println("##sql4##" + sql4);
             * System.out.println("##v_subj##" + v_subj);
             * System.out.println("##v_year##" + v_year);
             * System.out.println("##v_subjseq##" + v_subjseq);
             * System.out.println("##v_seq##" + v_seq);
             * System.out.println("##v_user_id##" + v_user_id);
             * System.out.println("######################");
             *
             * isOk4 = pstmt4.executeUpdate(); } ls1.moveFirst();
             *
             * //1.4 조회된 데이터를 사용하여 수강생 테이블 삭제 while(ls1.next()){ String v_subj =
             * ls1.getString("subj"); String v_year = ls1.getString("year");
             * String v_subjseq = ls1.getString("subjseq"); String v_user_id =
             * ls1.getString("userid");
             *
             * sql5 =
             * "delete from TZ_OFFSTUDENT where subj = ? and year = ? and subjseq = ? and userid = ?\n"
             * ; pstmt5 = connMgr.prepareStatement(sql5); pstmt5.setString(1,
             * v_subj); pstmt5.setString(2, v_year); pstmt5.setString(3,
             * v_subjseq); pstmt5.setString(4, v_user_id);
             *
             * isOk5 = pstmt5.executeUpdate(); }
             */
            isOk5 = 1;
            ls1.moveFirst();

            //2.유료과정 처리

            //2.1 유료과정인지 확인하여 유료과정 데이터 처리
            // 유료 과정일 경우 TZ_Billing 테이블 수정.
            //while(ls1.next()){
            //	String	v_subj 		= ls1.getString("subj");
            //	String	v_year 		= ls1.getString("year");
            //	String	v_subjseq 	= ls1.getString("subjseq");
            //	String	v_user_id	= ls1.getString("userid");
            //
            //	sql6 = "select count(*) cnt from TZ_BILLING where subj="+SQLString.Format(v_subj)+" and year="+SQLString.Format(v_year);
            //    sql6+= " and subjseq="+SQLString.Format(v_subjseq)+" and userid="+SQLString.Format(v_user_id);
            //	sql6+= " and grtype="+SQLString.Format(v_grtype);
            //    ls6 = connMgr.executeQuery(sql6);
            //
            //    if (ls6.next()){ bill_cnt = ls6.getInt("cnt"); }   //취소한 횟수
            //
            //	if(bill_cnt != 0){
            //		sql6 = " Update TZ_BILLING\n";
            //		sql6 += " Set paystat = 'CW' Where subj=? and year=? and subjseq=? and userid=? and grtype=?";
            //
            //		pstmt6 = connMgr.prepareStatement(sql6);
            //		pstmt6.setString(1, v_subj);
            //		pstmt6.setString(2, v_year);
            //		pstmt6.setString(3, v_subjseq);
            //		pstmt6.setString(4, v_user_id);
            //		pstmt6.setString(5, v_grtype);
            //        isOk6 = pstmt6.executeUpdate();
            //	}
            //	else
            //	{
            //		isOk6 = 1;
            //	}
            //}
            //ls1.moveFirst();

            //2.2 포인트 처리
            //int	usepoint = 0;
            //sql9 = "select usepoint from TZ_POINTUSE where tid="+SQLString.Format(v_tid);
            //ls9 = connMgr.executeQuery(sql9);
            //
            //if (ls9.next()){ usepoint = ls9.getInt("usepoint"); }   //사용포인트
            //
            //if(usepoint != 0){         //사용포인트가 있으면
            //   //insert TZ_GETPOINT table
            //   sql9 = "insert into TZ_POINTGET (tid,userid,getpoint,getdate,title,subj,year,subjseq,luserid,ldate,usepoint,expiredate)\n";
            //   sql9+= " values(to_char(sysdate,'YYYYMMDDHH24MISS'),?,?,to_char(sysdate,'YYYYMMDDHH24MISS'),'환불로인한 환원',null,null,null,?,to_char(sysdate,'YYYYMMDDHH24MISS'),null,null)";
            //
            //	pstmt9 = connMgr.prepareStatement(sql9);
            //	pstmt9.setString(1, box.getSession("userid"));
            //	pstmt9.setInt(2, usepoint);
            //	pstmt9.setString(3, box.getSession("userid"));
            //    isOk9 = pstmt9.executeUpdate();
            //
            //} else {
            //	isOk9 = 1;
            //}

            //3.결제 정보 처리

            //3.1  환불 프로세스 처리(결제취소)
            System.out.println("##결제취소 시작##");
            cs = new ConfigSet();
            /**************************************
             * 1. INIpay41 클래스의 인스턴스 생성 *
             **************************************/
            INIpay50 inipay = new INIpay50();

            //			int isOk = 0;

            /*********************
             * 2. 지불 정보 설정 *
             *********************/
            inipay.SetField("inipayhome", cs.getProperty("inipay.inipayHome")); // INIpay Home (절대경로로 적절히 수정) - C:/INIpay41_JAVA
            inipay.SetField("type", "cancel"); // 고정 (절대 수정 불가)
            inipay.SetField("debug", cs.getProperty("inipay.debug")); // 로그모드("true"로 설정하면 상세한 로그가 생성됨)

            String mid = null;
            // 상점아이디 : test.kocca.or.kr일 경우 테스트 상점아이디를 가져온다.
            //			if(1==1 || box.get("request.serverName").indexOf("test.kocca.or.kr") != -1)
            //				mid = cs.getProperty("inipay.mid.test");	// 테스트 상점아이디
            //			else
            //			mid = cs.getProperty("inipay.mid.real");	// 실제 상점아이디
            //			inipay.SetField("mid", mid);
            if (area.equals("G0")) { // 게임 mallId kocca00000
                mid = cs.getProperty("inipay.mid.realgame");
            } else if (area.equals("K0")) { // 문콘 mallId kocca00002
                mid = cs.getProperty("inipay.mid.realcontent");
            } else if (area.equals("B0")) { // 방송 mallId kocca00004
                mid = cs.getProperty("inipay.mid.realbroad");
            } else {
                mid = cs.getProperty("inipay.mid.realgame"); // 실제 상점아이디
            }

            inipay.SetField("mid", mid);
            inipay.SetField("admin", cs.getProperty("inipay.keyPw")); // 키패스워드(상점아이디에 따라 변경) - 1111
            inipay.SetField("tid", v_tid); // 취소할 거래의 거래아이디
            inipay.SetField("cancelmsg", "수강취소"); // 취소사유

            /****************
             * 3. 취소 요청 *
             ****************/
            inipay.startAction();

            /****************************************************************
             * 4. 취소 결과
             *
             * 결과코드 : inipay.GetResult("ResultCode") ("00"이면 취소 성공) 결과내용 :
             * inipay.GetResult("ResultMsg") (취소결과에 대한 설명) 취소날짜 :
             * inipay.GetResult("CancelDate") (YYYYMMDD) 취소시각 :
             * inipay.GetResult("CancelTime") (HHMMSS) 현금영수증 취소 승인번호 :
             * inipay.GetResult("CSHR_CancelNum") (현금영수증 발급 취소시에만 리턴됨)
             ****************************************************************/
            String resultCode = inipay.GetResult("ResultCode"); // 결과코드 ("00"이면 취소 성공)
            String resultMsg = inipay.GetResult("ResultMsg"); // 결과내용 (취소결과에 대한 설명)
            String cancelDate = inipay.GetResult("CancelDate"); // (YYYYMMDD)
            String cancelTime = inipay.GetResult("CancelTime"); // (HHMMSS)

            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd hhmmdd", Locale.KOREA);
            Date currentTime = new Date();

            if (cancelDate.equals("")) {
                cancelDate = sdf.format(currentTime).substring(0, 8);
                cancelTime = sdf.format(currentTime).substring(9);
            }
            System.out.println("##resultCode##" + resultCode);
            System.out.println("##resultMsg##" + resultMsg);
            System.out.println("##cancelDate##" + cancelDate);
            System.out.println("##cancelTime##" + cancelTime);
            System.out.println("##결제취소 끝##");

            /**************************
             * 취소 실패시 롤백
             **************************/
            //			if (!"00".equals(resultCode)) {
            //				connMgr.rollback();
            //			}

            /****************
             * 5. 취소결과 저장 *
             ****************/
            //일련번호 구하기
            sql7 = "select isnull(max(seq),0) + 1 maxseq from tz_offbillcancel where tid = " + SQLString.Format(v_tid);

            int maxseq = 0;

            ls7 = connMgr.executeQuery(sql7);
            if (ls7.next()) {
                maxseq = ls7.getInt("maxseq");
            }

            //tb_billcancel에 저장
            sql7 = "insert into tz_offbillcancel(\n";
            sql7 += "				tid,\n";
            sql7 += "				seq,\n";
            sql7 += "				mid,\n";
            sql7 += "				resultmsg,\n";
            sql7 += "				cancelyn,\n";
            sql7 += "				cancelresult,\n";
            sql7 += "				canceldate,\n";
            sql7 += "				canceltime,\n";
            sql7 += "				rcashcancelno,\n";
            sql7 += "				luserid,\n";
            sql7 += "				ldate )\n";
            sql7 += "values(		?,?,?,?,?,?,?,?,?,?,?)\n";

            pstmt7 = connMgr.prepareStatement(sql7);

            pstmt7.setString(1, v_tid); //거래번호
            pstmt7.setInt(2, maxseq); //취소일련번호
            pstmt7.setString(3, mid); //상점ID
            pstmt7.setString(4, resultMsg); //결과내용
            pstmt7.setString(5, "Y"); //취소여부
            pstmt7.setString(6, resultCode); //결과코드
            pstmt7.setString(7, cancelDate); //취소날짜
            pstmt7.setString(8, cancelTime); //취소시간
            pstmt7.setString(9, ""); //현금영수증 번호
            pstmt7.setString(10, box.getSession("userid")); //사용자 ID
            pstmt7.setString(11, FormatDate.getDate("yyyyMMddHHmmss")); //날짜

            isOk7 = pstmt7.executeUpdate();

            //if (resultMsg.equals("[501626|기 취소 거래]")) {
            //	//이미 취소된 결제는 tb_billinfo에 Update하지 않는다.(코드 1225)
            //	isOk8 = 1;
            //	//System.out.println("이미 취소됨");
            //	if(isOk1*isOk2*isOk3*isOk4*isOk5*isOk6 > 0) connMgr.commit(); //isOk7,isOk8 은 결제 후 정보기 때문에 실패결과를 저장하고 return 할때 0 을 리턴하여야 실패 alert 이 발생됨
            //	else connMgr.rollback();
            //} else {
            if ("00".equals(resultCode)) { //취소 성공이면
                //tb_billinfo에 정보 UPdate
                //CANCELYN='Y',CANCELRESULT,CANCELDATE,CANCELTIME,RCASHCANCELNO
                sql8 = "update 	tz_offbillinfo\n";
                sql8 += "set		cancelyn 		= ?,\n";
                sql8 += "			cancelresult 	= ?,\n";
                sql8 += "			canceldate 		= ?,\n";
                sql8 += "			canceltime 		= ?,\n";
                sql8 += "			rcashcancelno 	= ?\n";
                sql8 += "where	tid 			= ?\n";

                pstmt8 = connMgr.prepareStatement(sql8);

                pstmt8.setString(1, "Y"); //취소여부
                pstmt8.setString(2, resultCode); //취소결과
                pstmt8.setString(3, cancelDate); //취소날짜
                pstmt8.setString(4, cancelTime); //취소시간
                pstmt8.setString(5, ""); //현금영수증 번호
                pstmt8.setString(6, v_tid); //거래번호

                isOk8 = pstmt8.executeUpdate();
            } else {
                isOk8 = 0;
            }
            //}

            connMgr.commit();
            //			if(isOk1*isOk2*isOk3*isOk4*isOk5 > 0) connMgr.commit(); //isOk7,isOk8 은 결제 후 정보기 때문에 실패결과를 저장하고 return 할때 0 을 리턴하여야 실패 alert 이 발생됨
            //			else connMgr.rollback();
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls3.close();
                } catch (Exception e) {
                }
            }
            if (ls4 != null) {
                try {
                    ls4.close();
                } catch (Exception e) {
                }
            }
            if (ls5 != null) {
                try {
                    ls5.close();
                } catch (Exception e) {
                }
            }
            if (ls6 != null) {
                try {
                    ls6.close();
                } catch (Exception e) {
                }
            }
            if (ls7 != null) {
                try {
                    ls7.close();
                } catch (Exception e) {
                }
            }
            if (ls8 != null) {
                try {
                    ls8.close();
                } catch (Exception e) {
                }
            }
            if (ls9 != null) {
                try {
                    ls9.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt4 != null) {
                try {
                    pstmt4.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt5 != null) {
                try {
                    pstmt5.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt6 != null) {
                try {
                    pstmt6.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt7 != null) {
                try {
                    pstmt7.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt8 != null) {
                try {
                    pstmt8.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt9 != null) {
                try {
                    pstmt9.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
                }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1 * isOk2 * isOk3 * isOk4 * isOk5 * isOk7 * isOk8;
    }

    /**
     * off-line 수강신청취소 2010.10.11
     *
     * @param box receive from the form object and session
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int updateCancelOffPropose2(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;

        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        String sql1 = "";
        String sql2 = "";
        // String area = "";
        // int cancel_cnt = 0;
        int isOk1 = 0;
        int isOk2 = 0;

        String v_tid = box.getString("p_tid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            //1.수강신청 데이터 처리

            //1.1 tid 를 이용하여 수강신청 데이터를 조회한다
            sql1 = " SELECT subj, YEAR, subjseq, seq, userid, tid, refundbank, refundaccount,\n";
            sql1 += "        refundname, cancelreason, canceldate, cancelkind, (SELECT AREA FROM TZ_OFFSUBJ WHERE SUBJ = A.SUBJ) AREA\n";
            sql1 += "   FROM tz_offpropose A\n";
            sql1 += "  WHERE tid = " + SQLString.Format(v_tid);

            ls1 = connMgr.executeQuery(sql1);

            isOk1 = 1;

            //1.3 조회된 데이터를 사용하여 수강신청 테이블 삭제
            while (ls1.next()) {
                String v_subj = ls1.getString("subj");
                String v_year = ls1.getString("year");
                String v_subjseq = ls1.getString("subjseq");
                String v_seq = ls1.getString("seq");
                String v_user_id = ls1.getString("userid");

                sql2 = "update  TZ_OFFPROPOSE \n";
                sql2 += "set	     tid = null \n";
                sql2 += "where	subj = ? and year = ? and subjseq = ? and seq = ? and userid = ?\n";

                pstmt2 = connMgr.prepareStatement(sql2);
                pstmt2.setString(1, v_subj);
                pstmt2.setString(2, v_year);
                pstmt2.setString(3, v_subjseq);
                pstmt2.setString(4, v_seq);
                pstmt2.setString(5, v_user_id);

                System.out.println("######################");
                System.out.println("결제취소후 재결제(수강신청)");
                System.out.println("##sql2##" + sql2);
                System.out.println("##v_subj##" + v_subj);
                System.out.println("##v_year##" + v_year);
                System.out.println("##v_subjseq##" + v_subjseq);
                System.out.println("##v_seq##" + v_seq);
                System.out.println("##v_user_id##" + v_user_id);
                System.out.println("######################");

                isOk2 = pstmt2.executeUpdate();

                if (isOk2 > 0) {
                    box.put("p_listgubun", "PROP");
                    box.put("p_subj", v_subj);
                    box.put("p_year", v_year);
                    box.put("p_subjseq", v_subjseq);
                    box.put("p_seq", v_seq);
                    box.setSession("userid", v_user_id);
                }
            }

            connMgr.commit();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
                }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1 * isOk2;
    }

    /**
     * 교육개인교육이력
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectStudyHistoryTotList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list1 = null;
        StringBuffer sql = new StringBuffer();

        String v_user_id = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");

        int v_pageno = box.getInt("p_pageno");

        DataBox dbox = null;
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql.append("/* com.credu.study.MyClassBean selectStudyHistoryTotList (지난과정 목록 조회) */    \n");
            sql.append("SELECT  C.SUBJ          \n");
            sql.append("    ,   C.SUBJNM        \n");
            sql.append("    ,   C.ISUSE         \n");
            sql.append("    ,   C.EDUURL        \n");
            sql.append("    ,   C.CONTENTTYPE   \n");
            sql.append("    ,   C.ISONOFF		\n");
            sql.append("    ,   A.YEAR          \n");
            sql.append("    ,   A.SUBJSEQ       \n");
            sql.append("    ,   A.PROPSTART     \n");
            sql.append("    ,   A.PROPEND       \n");
            sql.append("    ,   D.EDUSTART  , A.EDUSTART COURSEEDUSTART    \n");
            sql.append("    ,   D.EDUEND    , A.EDUEND  COURSEEDUEND      \n");
            sql.append("    ,   A.REVIEWTYPE    \n");
            sql.append("    ,   A.ISABLEREVIEW  \n");
            sql.append("    ,   A.REVIEWDAYS    \n");
            sql.append("    ,   D.TSTEP    , A.CPSUBJSEQ, C.CPSUBJ, B.COMP COMPANY      \n");
            sql.append("    ,   CASE WHEN ( A.EDUEND < '2010047' AND E.ISGRADUATED IS NULL ) THEN 'A'   \n");
            sql.append("            WHEN (E.ISGRADUATED IS NULL ) THEN 'B'                              \n");
            sql.append("            ELSE D.ISGRADUATED                                                  \n");
            sql.append("        END AS ISGRADUATED                                                      \n");
            sql.append("    ,   CASE WHEN C.ISABLEREVIEW = 'Y' THEN                                                                                     \n");
            sql.append("            CASE WHEN A.REVIEWTYPE = 'D' THEN                                                                                   \n");
            sql.append("                CASE WHEN (TO_DATE(A.EDUEND, 'YYYYMMDDHH24') + TO_NUMBER(A.REVIEWDAYS) ) > SYSDATE THEN 'Y' ELSE 'N' END        \n");
            sql.append("            WHEN A.REVIEWTYPE = 'W' THEN                                                                                        \n");
            sql.append("                CASE WHEN ( TO_DATE(A.EDUEND, 'YYYYMMDDHH24') + TO_NUMBER( A.REVIEWDAYS) * 7 ) > SYSDATE THEN 'Y' ELSE 'N' END  \n");
            sql.append("            WHEN A.REVIEWTYPE = 'M' THEN                                                                                        \n");
            sql.append("                CASE WHEN ADD_MONTHS(TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), A.REVIEWDAYS) > SYSDATE THEN 'Y' ELSE 'N' END           \n");
            sql.append("            WHEN A.REVIEWTYPE = 'Y' THEN                                                                                        \n");
            sql.append("                CASE WHEN ADD_MONTHS(TO_DATE(A.EDUEND, 'YYYYMMDDHH24'), A.REVIEWDAYS * 12) > SYSDATE THEN 'Y' ELSE 'N' END      \n");
            sql.append("            END                                                                                                                 \n");
            sql.append("        END AS ISPOSSIBLE_BY_DATE           \n");
            sql.append("    ,   (                                   \n");
            sql.append("        SELECT  CLASSNAME                   \n");
            sql.append("          FROM  TZ_SUBJATT X                \n");
            sql.append("         WHERE  X.UPPERCLASS = C.UPPERCLASS \n");
            sql.append("           AND  X.MIDDLECLASS = '000'       \n");
            sql.append("           AND  X.LOWERCLASS = '000'        \n");
            sql.append("        ) AS UPPERCLASSNM                   \n");
            // sql.append("    ,   RANK() OVER( ORDER  BY A.EDUSTART DESC, C.SUBJNM ) AS RNK    \n");
            sql.append("    ,   COUNT(C.SUBJ) OVER() AS TOT_CNT     \n");
            sql.append("    ,   (                                   \n");
            sql.append("        SELECT  COUNT(TS.SUBJ)              \n");
            sql.append("          FROM  TZ_SUBJSEQ TS               \n");
            sql.append("         WHERE  TS.SUBJ = C.SUBJ            \n");
            sql.append("           AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN TS.EDUSTART AND TS.EDUEND  \n");
            sql.append("           AND  TS.GRCODE = '").append(v_grcode).append("'  \n");
            sql.append("        ) AS POSSIBLE_CNT   \n");
            sql.append("    ,   C.INTRODUCEFILENAMENEW   \n");
            sql.append("  FROM  TZ_SUBJSEQ A        \n");
            sql.append("    ,   TZ_PROPOSE B        \n");
            sql.append("    ,   TZ_SUBJ C           \n");
            sql.append("    ,   TZ_STUDENT D        \n");
            sql.append("    ,   TZ_STOLD E          \n");
            sql.append(" WHERE  B.USERID = '").append(v_user_id).append("'  \n");
            sql.append("   AND  A.GRCODE = '").append(v_grcode).append("'   \n");
            sql.append("  --  AND  A.EDUEND < TO_CHAR(SYSDATE, 'YYYYMMDDHH24')  \n");
            //sql.append("  -- AND  A.EDUEND BETWEEN (TO_CHAR(ADD_MONTHS(SYSDATE, -60), 'YYYYMMDDHH24')) AND TO_CHAR(SYSDATE, 'YYYYMMDDHH24')   \n");
            //sql.append("   AND  ( (A.EDUEND BETWEEN (TO_CHAR(ADD_MONTHS(SYSDATE, -60), 'YYYYMMDDHH24')) AND TO_CHAR(SYSDATE, 'YYYYMMDDHH24') ) OR E.ISGRADUATED = 'Y' ) \n");
            sql.append("   AND  B.YEAR = A.YEAR         \n");
            sql.append("   AND  B.SUBJ = A.SUBJ         \n");
            sql.append("   AND  B.SUBJSEQ = A.SUBJSEQ   \n");
            sql.append("   AND  B.SUBJ = C.SUBJ         \n");
            sql.append("   AND  B.YEAR = D.YEAR         \n");
            sql.append("   AND  B.SUBJ = D.SUBJ         \n");
            sql.append("   AND  B.SUBJSEQ = D.SUBJSEQ   \n");
            sql.append("   AND  B.USERID = D.USERID     \n");
            sql.append("   AND  B.YEAR = E.YEAR(+)      \n");
            sql.append("   AND  B.SUBJ = E.SUBJ(+)      \n");
            sql.append("   AND  B.SUBJSEQ = E.SUBJSEQ(+)\n");
            sql.append("   AND  B.USERID = E.USERID(+)  \n");
            sql.append(" ORDER  BY A.EDUSTART DESC, C.SUBJNM    \n");

            ls = connMgr.executeQuery(sql.toString());

            int total_row_count = 0;
            // int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); // 전체 row 수를 반환한다
            if ( ls.next() ) {
                total_row_count = ls.getInt("TOT_CNT");
                ls.moveFirst();
            }

            ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_reviewstart", FormatDate.getDateAdd(FormatDate.getFormatDate(dbox.getString("d_courseeduend"),"yyyyMMdd"),"yyyyMMdd", "date", 1));
                if(dbox.getString("d_reviewtype").equals("D")){
                    dbox.put("d_reviewend", FormatDate.getDateAdd(FormatDate.getFormatDate(dbox.getString("d_reviewstart"),"yyyyMMdd"),"yyyyMMdd", "date", dbox.getInt("d_reviewdays") - 1));
                }else if(dbox.getString("d_reviewtype").equals("W")){
                    dbox.put("d_reviewend", FormatDate.getDateAdd(FormatDate.getFormatDate(dbox.getString("d_reviewstart"),"yyyyMMdd"),"yyyyMMdd", "date", dbox.getInt("d_reviewdays") * 7 - 1));
                }else if(dbox.getString("d_reviewtype").equals("M")){
                    dbox.put("d_reviewend", FormatDate.getDateAdd(FormatDate.getFormatDate(dbox.getString("d_reviewstart"),"yyyyMMdd"),"yyyyMMdd", "month", dbox.getInt("d_reviewdays")));
                }else if(dbox.getString("d_reviewtype").equals("Y")){
                    dbox.put("d_reviewend", FormatDate.getDateAdd(FormatDate.getFormatDate(dbox.getString("d_reviewstart"),"yyyyMMdd"),"yyyyMMdd", "year", dbox.getInt("d_reviewdays")));
                }

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql1 = " + sql.toString() + "\r\n" + ex.getMessage());
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
        return list1;
    }

    /**
     * 교육개인교육이력 off-line
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectStudyHistoryOffTotList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        String head_sql1 = "";
        String body_sql1 = "";
        String order_sql1 = "";
        String count_sql1 = "";
        int v_pageno = box.getInt("p_pageno");

        //		MyClassData data1	 = null;

        String v_user_id = box.getSession("userid");

        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");

        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            head_sql1 = " SELECT b.subj, b.YEAR, b.subjseq, a.upperclass,\n";
            head_sql1 += "       (SELECT classname\n";
            head_sql1 += "          FROM tz_offsubjatt\n";
            head_sql1 += "         WHERE upperclass = a.upperclass AND middleclass = '000')\n";
            head_sql1 += "                                                                 upperclassnm,\n";
            head_sql1 += "       a.subjnm, b.edustart, b.eduend, a.isterm, c.stustatus,\n";
            head_sql1 += "       (SELECT codenm\n";
            head_sql1 += "          FROM tz_code\n";
            head_sql1 += "         WHERE gubun = '0089' AND code = c.stustatus) stustatusnm,\n";
            head_sql1 += "       (SELECT score\n";
            head_sql1 += "          FROM tz_offtermstudent\n";
            head_sql1 += "         WHERE ROWNUM < 2\n";
            head_sql1 += "           AND subj = b.subj\n";
            head_sql1 += "           AND YEAR = b.YEAR\n";
            head_sql1 += "           AND subjseq = b.subjseq\n";
            head_sql1 += "           AND userid = c.userid) score,\n";
            head_sql1 += "       c.isgraduated, \n";
            head_sql1 += "		(SELECT APPDATE FROM TZ_OFFPROPOSE WHERE SUBJ = C.SUBJ AND YEAR = C.YEAR AND SUBJSEQ = C.SUBJSEQ AND USERID = C.USERID) AS APPDATE \n";
            body_sql1 = "  FROM tz_offsubj a, tz_offsubjseq b, tz_offstudent c\n";
            body_sql1 += " WHERE a.subj = b.subj\n";
            body_sql1 += "   AND a.subj = c.subj\n";
            body_sql1 += "   AND b.YEAR = c.YEAR\n";
            body_sql1 += "   AND b.subjseq = c.subjseq\n";
            body_sql1 += "   AND b.seq = '1'\n";
            body_sql1 += "   AND TO_CHAR (SYSDATE, 'YYYYMMDDHH24') >= b.eduend\n";
            order_sql1 += " order by edustart desc, upperclass, subj, subjseq\n";

            if (!v_upperclass.equals("ALL"))
                body_sql1 += " and a.upperclass = " + SQLString.Format(v_upperclass);

            body_sql1 += "   AND c.userid = " + SQLString.Format(v_user_id);

            sql1 = head_sql1 + body_sql1 + order_sql1;

            System.out.println("selectStudyHistoryOffTotList.sql = " + sql1);

            ls1 = connMgr.executeQuery(sql1);

            count_sql1 = "select count(*) " + body_sql1;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); // 전체 row 수를 반환한다

            ls1.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls1.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int total_page_count = ls1.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 수료증 정보 리턴
     *
     * @param box receive from the form object and session
     * @return TutorData
     */
    public DataBox getSuryoInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_userid = box.getString("p_userid");
        String v_resno = "";
        String v_name = "";

        ProposeBean probean = new ProposeBean();
        Hashtable<String, String> outdata = new Hashtable<String, String> ();

        try {
            connMgr = new DBConnectionManager();

            outdata = probean.getMeberInfo(connMgr, v_userid);
            v_resno = (String) outdata.get("resno");
            v_name = (String) outdata.get("name");

            //            sql = " (\n";
            sql += " select B.serno, A.isonoff, get_jikwinm(B.jik, B.comp) jikwinm,\n";
            sql += "        A.subj, A.year, A.subjseq, A.subjseqgr, A.subjnm,  A.edustart, A.eduend, B.score,\n";
            sql += "        B.credit,  B.creditexam,  A.place, '1' kind, B.comp, B.isgraduated\n";
            sql += "   from VZ_SCSUBJSEQ A,TZ_STOLD B\n";
            sql += " where  A.subj=B.subj\n";
            sql += "  and A.year=B.year\n";
            sql += "  and A.subjseq=B.subjseq\n";
            sql += "  and B.userid=" + SQLString.Format(v_userid);
            //            sql+= " )";
            /*
             * sql+= " union\n"; sql+= " (select\n"; sql+=
             * "   completeno serno,\n"; sql+= "   edukind isonoff,\n"; sql+=
             * "   empposnm jikwinm,\n"; sql+= "   educscd subj,\n"; sql+=
             * "   eduyear year,\n"; sql+= "   degree subjseq,\n"; sql+=
             * "   degree subjseqgr,\n"; sql+= "   educsnm subjnm,\n"; sql+=
             * "   startdate edustart,\n"; sql+= "   enddate eduend,\n"; sql+=
             * "   score score,\n"; sql+= "   point_real credit ,\n"; sql+=
             * "   pointtest creditexam,\n"; sql+= "   '' place,\n"; sql+=
             * "   '1' kind,\n"; sql+= "   company comp,\n"; sql+=
             * "   passed isgraduated\n"; sql+= " from\n"; sql+=
             * "   tacareer\n"; sql+= " where\n"; sql+=
             * " socialno = '"+v_resno+"'\n"; sql+= " and iscareer = 'Y'\n";
             * sql+= " )";
             */
            sql += " order by edustart desc\n";
            System.out.println(sql);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                if (v_subj.equals(ls.getString("subj")) && v_subjseq.equals(ls.getString("subjseq")) && v_year.equals(ls.getString("year"))) {
                    dbox = ls.getDataBox();
                    dbox.put("d_resno", v_resno);
                    dbox.put("d_name", v_name);
                }
            }

            ls = connMgr.executeQuery(sql);

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
     * HOMEPAGE 메인 날자표시값 리턴
     *
     * @param edustart 교육시작일
     * @param eduend 교육종료일
     * @return Vector
     */
    public Vector<String> getDateInfo(String edustart, String eduend) throws Exception {
        Vector<String> result = new Vector<String>();
        int v_datediff = 0; // 교육기간
        int v_period = 0; // 교육기간 / 4
        String v_predate = ""; // 이전 기준일자
        String v_printDate = ""; // 화면 표시 날자

        v_datediff = FormatDate.datediff("date", edustart, eduend); // 교육일수
        v_period = v_datediff / 4; // 교육일수 / 4

        v_predate = edustart;
        v_printDate = FormatDate.getFormatDate(v_predate, "MM/dd");
        result.addElement(v_printDate);

        for (int i = 1; i < 4; i++) {
            v_printDate = FormatDate.getRelativeDate(v_predate, v_period);
            v_predate = v_printDate;

            v_printDate = FormatDate.getFormatDate(v_printDate, "MM/dd");
            result.addElement(v_printDate);
        }

        return result;
    }

    /**
     * 나의 강의실
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public String selectMyCourse(RequestBox box, String v_userid, String v_subj, String v_year, String v_subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        String sql1 = "";
        String ret = "";

        String v_user_id = box.getSession("userid");
        String v_tem_grcode = box.getSession("tem_grcode");
        try {
            connMgr = new DBConnectionManager();

            sql1 = " select A.scupperclass, A.isonoff, A.course, A.cyear, A.courseseq, A.coursenm, A.grcode,\n";
            sql1 += "        A.subj, A.year, A.subjseq, A.subjseqgr, A.cpsubj, A.cpsubjseq, A.subjnm, A.edustart, A.eduend,\n";
            sql1 += "        A.preurl, A.eduurl, A.subjtarget, A.isoutsourcing, 'Y' chkfnal, b.comp company, A.contenttype,\n";
            sql1 += "        (select classname from tz_subjatt\n";
            sql1 += "          where upperclass = A.scupperclass and middleclass = A.scmiddleclass and lowerclass = '000'\n";
            sql1 += "        ) middleclassnm\n";
            sql1 += "   from VZ_SCSUBJSEQ A,TZ_STUDENT B\n";
            sql1 += "  where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq\n";
            sql1 += "    and B.userid=" + SQLString.Format(v_user_id);
            sql1 += "    and A.grcode='" + v_tem_grcode + "'";
            sql1 += "    and to_char(sysdate,'YYYYMMDDHH24') between A.edustart and A.eduend\n";
            sql1 += "  order by A.scupperclass, a.scmiddleclass, A.subjnm, A.subj,A.year,A.subjseq,A.edustart,A.eduend\n";

            ls = connMgr.executeQuery(sql1);

            while (ls.next()) {
                if (ls.getString("subj").equals(v_subj) && ls.getString("year").equals(v_year) && ls.getString("subjseq").equals(v_subjseq)) {
                    ret += " <option value='" + ls.getString("eduurl") + "||" + ls.getString("isonoff") + "||" + ls.getString("subjseq") + "' selected>" + ls.getString("subjnm") + "</option>";
                } else {
                    ret += " <option value='" + ls.getString("eduurl") + "||" + ls.getString("isonoff") + "||" + ls.getString("subjseq") + "' >" + ls.getString("subjnm") + "</option>";
                }
            }

            ls.close();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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
        return ret;
    }

    /**
     * 나의 강의실
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public String selectMyCourseMenu(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        String sql1 = "";
        String ret = "";
        String v_server = "";
        String v_port = "";
        String v_eduurl = "";
        String results = "";

        String v_user_id = box.getSession("userid");
        String v_tem_grcode = box.getSession("tem_grcode");
        try {
            connMgr = new DBConnectionManager();

            sql1 = " select A.scupperclass, A.isonoff, A.course, A.cyear, A.courseseq, A.coursenm, A.grcode,\n";
            sql1 += "        A.subj, A.year, A.subjseq, A.subjseqgr, A.cpsubj, A.cpsubjseq, A.subjnm, A.edustart, A.eduend,\n";
            sql1 += "        A.preurl, A.eduurl, A.subjtarget, A.isoutsourcing, 'Y' chkfnal, b.comp company, A.contenttype,\n";
            sql1 += "        (select classname from tz_subjatt\n";
            sql1 += "          where upperclass = A.scupperclass and middleclass = A.scmiddleclass and lowerclass = '000'\n";
            sql1 += "        ) middleclassnm,\n";
            sql1 += "		 C.server, C.port, C.contenttype, C.eduurl, C.dir\n";
            sql1 += "   from VZ_SCSUBJSEQ A,TZ_STUDENT B ,tz_subj C\n";
            sql1 += "  where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq and C.subj = A.subj 					";
            sql1 += "    and B.userid=" + SQLString.Format(v_user_id);
            sql1 += "    and A.grcode='" + v_tem_grcode + "'";
            sql1 += "    and to_char(sysdate,'YYYYMMDDHH24') between A.edustart and A.eduend\n";
            sql1 += "  order by A.scupperclass, a.scmiddleclass, A.subjnm, A.subj,A.year,A.subjseq,A.edustart,A.eduend\n";

            ls = connMgr.executeQuery(sql1);

            while (ls.next()) {
                v_server = ls.getString("server");
                v_port = ls.getString("port");
                v_eduurl = ls.getString("eduurl");

                if (v_eduurl.equals("")) {
                    if (!v_server.equals(""))
                        v_server = v_server + ".";
                    if (!v_port.equals("80") && !v_port.equals(""))
                        v_port = ":" + v_port;
                    results = "/servlet/controller.contents.EduStart?p_subj=" + ls.getString("subj") + "&p_year=" + ls.getString("year") + "&p_subjseq=" + ls.getString("subjseq");
                } else {
                    results = v_eduurl;
                }
                ret += " <option value='" + results + "' >" + ls.getString("subjnm") + "</option>";
            }

            ls.close();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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
        return ret;
    }

    ///////////////////////////////////////////////수료증 일련번호 생성//////////////////////////////////////////////////////
    public String getIsunum(String p_subj, String p_subjseq, String p_year, String p_userid) throws Exception {
        DBConnectionManager connMgr = null;
        String v_isunum = "";
        try {
            connMgr = new DBConnectionManager();
            v_isunum = getIsunum(connMgr, p_subj, p_subjseq, p_year, p_userid);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return v_isunum;
    }

    public String getIsunum(DBConnectionManager connMgr, String p_subj, String p_subjseq, String p_year, String p_userid) throws Exception {
        ListSet ls = null;
        String v_isunum = "";

        String v_startdate = "";
        String v_educscd = "";
        String v_empno = "";
        String v_degree = "";
        int i = 1;

        String sql = "";

        try {
            sql = "select substring(startdate,0,6) startdate,\n";
            sql += "       educscd,\n";
            sql += "       to_char(degree,'00') degree,\n";
            sql += "       empno,\n";
            sql += "       empnm\n";
            sql += "  from tacareer\n";
            sql += " where educscd=" + SQLString.Format(p_subj);
            sql += "   and eduyear=" + SQLString.Format(p_year);
            sql += "   and degree=" + p_subjseq;
            sql += " order by empnm asc\n";

            System.out.println(sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                i++;

                v_startdate = ls.getString("startdate");
                v_educscd = ls.getString("educscd");
                v_degree = ls.getString("degree");
                v_empno = ls.getString("empno");

                if (p_userid.equals(v_empno)) {
                    v_isunum = v_startdate + "-" + v_educscd + "-" + v_degree + "-" + i;
                }
            }
            ls.close();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return v_isunum;
    }

    /**
     * 나의 교육이력 건수
     *
     * @param box receive from the form object and session
     * @return int 나의 교육이력 건수
     * @throws Exception
     */
    public int selectGetStudyHistoryCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");
        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT COUNT (userid) cnt\n";
            sql += "   FROM (SELECT a.userid\n";
            sql += "           FROM tz_stold a, tz_subjseq b\n";
            sql += "          WHERE a.subj = b.subj\n";
            sql += "            AND a.YEAR = b.YEAR\n";
            sql += "            AND a.subjseq = b.subjseq\n";
            sql += "            AND b.grcode = " + SQLString.Format(v_grcode);
            sql += "            AND a.userid = " + SQLString.Format(v_user_id);
            sql += "         UNION ALL\n";
            sql += "         SELECT userid\n";
            sql += "           FROM tz_stoldhst\n";
            sql += "          WHERE userid = " + SQLString.Format(v_user_id);
            sql += "         UNION ALL\n";
            sql += "         SELECT c.userid\n";
            sql += "           FROM tz_offsubj a, tz_offsubjseq b, tz_offstudent c\n";
            sql += "          WHERE a.subj = b.subj\n";
            sql += "            AND a.subj = c.subj\n";
            sql += "            AND b.YEAR = c.YEAR\n";
            sql += "            AND b.subjseq = c.subjseq\n";
            sql += "            AND b.seq = '1'";
            sql += "            AND TO_CHAR (SYSDATE, 'YYYYMMDDHH24') >= b.eduend\n";
            sql += "            AND c.userid = " + SQLString.Format(v_user_id);
            sql += "        )\n";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * 나의 온라인 수강 건수
     *
     * @param box receive from the form object and session
     * @return int 나의 온라인 수강 건수
     * @throws Exception
     */
    public int selectGetOnStudyCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT COUNT (a.subj) cnt\n";
            sql += "   FROM vz_scsubjseq a, tz_student b\n";
            sql += "  WHERE TO_CHAR (SYSDATE, 'YYYYMMDDHH24') BETWEEN a.edustart AND a.eduend\n";
            sql += "    AND a.subj = b.subj\n";
            sql += "    AND a.YEAR = b.YEAR\n";
            sql += "    AND a.subjseq = b.subjseq\n";
            sql += "    AND a.grcode = " + SQLString.Format(v_grcode);
            sql += "    AND b.userid = " + SQLString.Format(v_user_id);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * 나의 오프라인 수강 건수
     *
     * @param box receive from the form object and session
     * @return int 나의 오프라인 수강 건수
     * @throws Exception
     */
    public int selectGetOffStudyCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT count(a.subj) cnt";
            sql += "   FROM tz_offsubj a, tz_offsubjseq b, tz_offstudent c\n";
            sql += "  WHERE a.subj = b.subj\n";
            sql += "    AND a.subj = c.subj\n";
            sql += "    AND b.year = c.year\n";
            sql += "    AND b.subjseq = c.subjseq\n";
            sql += "    AND b.seq = '1'\n";
            sql += "    AND TO_CHAR (SYSDATE, 'YYYYMMDDHH24') BETWEEN b.edustart AND b.eduend\n";
            sql += "    AND c.userid = " + SQLString.Format(v_user_id);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * 오늘의 명언
     *
     * @param box receive from the form object and session
     * @return String 오늘의 명언
     * @throws Exception
     */
    public String selectGetMessage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT content\n";
            sql += "   FROM tz_message\n";
            sql += "  WHERE (CASE\n";
            sql += "            WHEN TYPE = 'A1'\n";
            sql += "            AND TO_CHAR (SYSDATE, 'YYYYMMDD') BETWEEN startdate AND enddate\n";
            sql += "               THEN 'Y'\n";
            sql += "            WHEN TYPE = 'B1' AND TO_CHAR (SYSDATE, 'YYYYMM') = MONTH\n";
            sql += "               THEN 'Y'\n";
            sql += "            ELSE 'N'\n";
            sql += "         END\n";
            sql += "        ) = 'Y'\n";
            sql += " UNION ALL\n";
            sql += " SELECT content\n";
            sql += "   FROM (SELECT   content\n";
            sql += "             FROM tz_message\n";
            sql += "         ORDER BY ldate DESC)\n";
            sql += "  WHERE ROWNUM < 2\n";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getString("content");
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
        return result;
    }

    /**
     * 수강중인 과정 (on + off)
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectStudyingList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        String v_user_id = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");

        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = " SELECT *\n";
            sql1 += "      FROM (SELECT 'ON' GUBUN, A.SUBJ, A.YEAR, A.SUBJSEQ, A.SUBJNM, A.EDUSTART,\n";
            sql1 += "                   A.EDUEND, A.CONTENTTYPE, A.EDUURL, A.WJ_CLASSKEY \n";
            sql1 += "              FROM VZ_SCSUBJSEQ A, TZ_PROPOSE B\n";
            sql1 += "             WHERE TO_CHAR (SYSDATE, 'YYYYMMDDHH24') BETWEEN A.EDUSTART AND A.EDUEND\n";
            sql1 += "               AND A.SUBJ = B.SUBJ\n";
            sql1 += "               AND A.YEAR = B.YEAR\n";
            sql1 += "               AND A.SUBJSEQ = B.SUBJSEQ\n";
            sql1 += "               and b.chkfinal='Y' AND B.USERID = " + SQLString.Format(v_user_id) + "\n";
            sql1 += "               and a.grcode = " + SQLString.Format(v_grcode) + "\n";
            sql1 += "            UNION ALL\n";
            sql1 += "            SELECT 'OFF' GUBUN, B.SUBJ, B.YEAR, B.SUBJSEQ, A.SUBJNM, B.EDUSTART,\n";
            sql1 += "                   B.EDUEND, '' CONTENTTYPE, '' EDUURL, '' WJ_CLASSKEY \n";
            sql1 += "              FROM TZ_OFFSUBJ A, TZ_OFFSUBJSEQ B, TZ_OFFPROPOSE C\n";
            sql1 += "             WHERE A.SUBJ = B.SUBJ\n";
            sql1 += "               AND A.SUBJ = C.SUBJ\n";
            sql1 += "               AND B.YEAR = C.YEAR\n";
            sql1 += "               AND B.SUBJSEQ = C.SUBJSEQ\n";
            sql1 += "               AND B.SEQ = '1'\n";
            sql1 += "               AND TO_CHAR (SYSDATE, 'YYYYMMDDHH24') BETWEEN B.EDUSTART AND B.EDUEND\n";
            sql1 += "               AND C.USERID = " + SQLString.Format(v_user_id) + "\n";
            sql1 += "               AND C.CHKFINAL = 'Y'\n"; //오프라인 과정 승인 처리 안된 과정은 리스트에 미노출
            sql1 += "           )\n";
            //			sql1 += "  WHERE rownum < 5 ORDER BY EDUSTART DESC\n";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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

        return list1;
    }

    /**
     * 수강료조회/납부 과정 (on + off)
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectBillList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql1 = "";

        String v_user_id = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");

        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = " SELECT   'ON' GUBUN, 'ON' LISTGUBUN,TID, GOODNAME, PRICE, RESULTCODE, PGAUTHDATE, CANCELYN,\n";
            sql1 += "          PAYSTATUS, LDATE, SUBJ, YEAR, SUBJSEQ, SEQ\n";
            sql1 += "     FROM (SELECT A.TID, A.GOODNAME, A.PRICE, A.RESULTCODE, A.PGAUTHDATE,\n";
            sql1 += "                  A.CANCELYN,\n";
            sql1 += "                  CASE\n";
            sql1 += "                     WHEN A.CANCELYN = 'Y'\n";
            sql1 += "                        THEN 'R'\n";
            sql1 += "                     WHEN A.CANCELYN = 'N' AND A.RESULTCODE = '00'\n";
            sql1 += "                        THEN 'Y'\n";
            sql1 += "                     WHEN A.CANCELYN = 'N' AND A.RESULTCODE = '99'\n";
            sql1 += "                        THEN 'N'\n";
            sql1 += "                  END PAYSTATUS,\n";
            sql1 += "                  A.LDATE, '' SUBJ, '' YEAR, '' SUBJSEQ, NULL SEQ\n";
            sql1 += "             FROM TZ_BILLINFO A INNER JOIN TZ_MEMBER B ON A.USERID = B.USERID\n";
            sql1 += "                  INNER JOIN TZ_PROPOSE C ON A.TID = C.TID \n";
            sql1 += "                  INNER JOIN TZ_SUBJSEQ D\n";
            sql1 += "                  ON C.SUBJ = D.SUBJ\n";
            sql1 += "                AND C.YEAR = D.YEAR\n";
            sql1 += "                AND C.SUBJSEQ = D.SUBJSEQ\n";
            sql1 += "            WHERE A.RESULTCODE <> '01' AND A.USERID = " + SQLString.Format(v_user_id) + "\n";
            sql1 += "            AND d.grcode = " + SQLString.Format(v_grcode) + "\n";
            sql1 += "          )\n";
            sql1 += " GROUP BY TID, GOODNAME, PRICE, RESULTCODE, PGAUTHDATE, CANCELYN, PAYSTATUS, LDATE\n";
            sql1 += " UNION ALL\n";
            sql1 += " SELECT   'OFF' GUBUN, LISTGUBUN, TID, GOODNAME, PRICE, RESULTCODE, PGAUTHDATE, CANCELYN,\n";
            sql1 += "          PAYSTATUS, LDATE, SUBJ, YEAR, SUBJSEQ, SEQ\n";
            sql1 += "     FROM (SELECT 'BILL' LISTGUBUN, A.TID, A.GOODNAME, A.PRICE, A.RESULTCODE, A.PGAUTHDATE,\n";
            sql1 += "                  A.CANCELYN,\n";
            sql1 += "                  CASE\n";
            sql1 += "                     WHEN A.CANCELYN = 'Y'\n";
            sql1 += "                        THEN 'R'\n";
            sql1 += "                     WHEN A.CANCELYN = 'N' AND A.RESULTCODE = '00'\n";
            sql1 += "                        THEN 'Y'\n";
            sql1 += "                     WHEN A.CANCELYN = 'N' AND A.RESULTCODE = '99'\n";
            sql1 += "                        THEN 'N'\n";
            sql1 += "                  END PAYSTATUS,\n";
            sql1 += "                  A.LDATE, '' SUBJ, '' YEAR, '' SUBJSEQ, NULL SEQ\n";
            sql1 += "             FROM TZ_OFFBILLINFO A INNER JOIN TZ_MEMBER B ON A.USERID = B.USERID\n";
            sql1 += "            WHERE A.RESULTCODE <> '01' AND A.USERID = " + SQLString.Format(v_user_id) + "\n";
            sql1 += "            AND a.usernm = b.name and B.grcode = " + SQLString.Format(v_grcode) + "\n";
            sql1 += "           UNION ALL\n";
            sql1 += "           SELECT 'REQ' LISTGUBUN, B.TID, A.BILLREQNM, A.REALBIYONG, B.BILLSTATUS, NULL, 'N',\n";
            sql1 += "                  CASE\n";
            sql1 += "                     WHEN B.BILLSTATUS = '99'\n";
            sql1 += "                        THEN 'N'\n";
            sql1 += "                     WHEN B.BILLSTATUS = '00'\n";
            sql1 += "                        THEN 'Y'\n";
            sql1 += "                  END PAYSTATUS,\n";
            sql1 += "                  B.LDATE, A.SUBJ, A.YEAR, A.SUBJSEQ, A.SEQ\n";
            sql1 += "             FROM TZ_OFFBILLREQ A INNER JOIN TZ_OFFBILLREQUSER B\n";
            sql1 += "                  ON A.SUBJ = B.SUBJ\n";
            sql1 += "                AND A.YEAR = B.YEAR\n";
            sql1 += "                AND A.SUBJSEQ = B.SUBJSEQ\n";
            sql1 += "                AND A.SEQ = B.SEQ\n";
            sql1 += "            WHERE B.USERID = " + SQLString.Format(v_user_id) + "\n";
            sql1 += "              AND B.BILLSTATUS = '99'\n";
            sql1 += "           UNION ALL\n";
            sql1 += "           SELECT 'PROP' LISTGUBUN, A.TID, B.SUBJNM, B.BIYONG, '99' BILLSTATUS, NULL, 'N',\n";
            sql1 += "                  'N' PAYSTATUS,\n";
            sql1 += "                  B.LDATE, A.SUBJ, A.YEAR, A.SUBJSEQ, A.SEQ\n";
            sql1 += "             FROM TZ_OFFPROPOSE A INNER JOIN TZ_OFFSUBJSEQ B\n";
            sql1 += "                  ON A.SUBJ = B.SUBJ\n";
            sql1 += "                AND A.YEAR = B.YEAR\n";
            sql1 += "                AND A.SUBJSEQ = B.SUBJSEQ\n";
            sql1 += "                AND A.SEQ = B.SEQ\n";
            sql1 += "            WHERE A.CHKFIRST = 'Y'\n";
            sql1 += "              AND A.CHKFINAL = 'U'\n";
            sql1 += "              AND A.TID IS NULL\n";
            sql1 += "              AND A.USERID = " + SQLString.Format(v_user_id) + "\n";
            sql1 += "        )\n";
            sql1 += " ORDER BY LDATE DESC, PGAUTHDATE DESC, TID DESC\n";

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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

        return list1;
    }

    /**
     * 관심과정
     *
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectInterestList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        StringBuffer sql = new StringBuffer();

        String v_user_id = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");
        // String v_year = FormatDate.getDate("yyyy");

        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql.append(" select A.USERID, E.NAME, A.SUBJ, '' GRCODE, B.SUBJNM, B.UPPERCLASS,b.PREURL, \n");
            sql.append("         GET_NAME (MUSERID) MUSERNM, D.CLASSNAME, A.INDATE, B.SPHERE, \n");
            sql.append("         F.SUBJSEQ, F.YEAR, F.PROPSTART, F.PROPEND, F.EDUSTART, F.EDUEND, \n");
            sql.append("         F.STUDENTLIMIT, F.SUBJSEQGR, F.ISONOFF, F.BIYONG, \n");
            sql.append("         (SELECT COUNT (*) CNT FROM TZ_PROPOSE z WHERE z.SUBJ = f.SUBJ AND z.YEAR = f.YEAR AND z.SUBJSEQ = f.SUBJSEQ AND z.USERID = a.userid AND z.asp_gubun = a.grcode AND ISNULL (z.CANCELKIND, ' ') NOT IN ('F', 'P')) PROPCNT \n");
            sql.append("    FROM TZ_INTEREST A \n");
            sql.append("    left join  TZ_SUBJ B on a.subj=b.subj \n");
            sql.append("    left join  TZ_GRSUBJ C on a.subj=C.SUBJCOURSE and A.GRCODE = c.GRCODE \n");
            sql.append("    left join  TZ_SUBJATT D on B.UPPERCLASS = D.UPPERCLASS AND D.MIDDLECLASS = '000' AND D.LOWERCLASS = '000' \n");
            sql.append("    left join  TZ_MEMBER E on A.USERID = E.USERID and A.GRCODE = E.GRCODE \n");
            sql.append("    left join VZ_SCSUBJSEQ f on a.subj=f.subj and A.GRCODE = f.GRCODE and TO_CHAR (SYSDATE, 'YYYYMMDDHH24') <= f.PROPEND \n");
            sql.append("    where a.userid=" + SQLString.Format(v_user_id) + " AND A.GRCODE = " + SQLString.Format(v_grcode));
            sql.append("ORDER BY SUBJ, SUBJSEQ ");

            ls1 = connMgr.executeQuery(sql.toString());

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql1 = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

    /**
     * 과정질문방 건수
     *
     * @param box receive from the form object and session
     * @return int 과정질문방 건수
     * @throws Exception
     */
    public int selectGetSubjQnaCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT COUNT (subj) cnt";
            sql += "   FROM tz_qna a\n";
            sql += "  WHERE a.inuserid = " + SQLString.Format(v_user_id);
            sql += "    AND a.kind = '0'\n";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * Q&A 건수
     *
     * @param box receive from the form object and session
     * @return int Q&A 건수
     * @throws Exception
     */
    public int selectGetQnaCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT COUNT (*) cnt\n";
            sql += "   FROM tz_homeqna a, tz_bds b\n";
            sql += "  WHERE a.inuserid = " + SQLString.Format(v_user_id);
            sql += "    AND a.TYPES = '0'\n";
            sql += "    AND a.tabseq = b.tabseq\n";
            sql += "    AND b.TYPE = 'PQ'\n";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * 1vs1 건수
     *
     * @param box receive from the form object and session
     * @return int 1vs1 수강 건수
     * @throws Exception
     */
    public int selectGetCounselCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT COUNT (*) cnt\n";
            sql += "   FROM tz_homeqna a, tz_bds b\n";
            sql += "  WHERE a.inuserid = " + SQLString.Format(v_user_id);
            sql += "    AND a.TYPES = '0'\n";
            sql += "    AND a.tabseq = b.tabseq\n";
            sql += "    AND b.TYPE = 'MM'\n";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * 나의 이벤트 건수
     *
     * @param box receive from the form object and session
     * @return int 나의 이벤트 수강 건수
     * @throws Exception
     */
    public int selectGetMyEventCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT COUNT (*) cnt\n";
            sql += "   FROM tz_event a, tz_event_apply b\n";
            sql += "  WHERE b.userid = " + SQLString.Format(v_user_id);
            sql += "    AND a.seq = b.seq\n";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * 나의 당첨이벤트 건수
     *
     * @param box receive from the form object and session
     * @return int 나의 당첨이벤트 수강 건수
     * @throws Exception
     */
    public int selectGetMyWinEventCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT COUNT (*) cnt\n";
            sql += "   FROM tz_event a, tz_event_apply b\n";
            sql += "  WHERE b.userid = " + SQLString.Format(v_user_id);
            sql += "    AND a.seq = b.seq\n";
            sql += "    AND b.win_yn = 'Y'\n";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * 나의 워크샵 수강 건수
     *
     * @param box receive from the form object and session
     * @return int 나의 워크샵 수강 건수
     * @throws Exception
     */
    public int selectGetMyWorkshopCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT COUNT (*) cnt\n";
            sql += "   FROM tz_seminar a, tz_seminar_apply b\n";
            sql += "  WHERE b.userid = " + SQLString.Format(v_user_id);
            sql += "    AND a.seq = b.seq\n";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    ///////////////////////////////////////////////progress 존재갯수//////////////////////////////////////////////////////

    /**
     * 오프라인 수강취소(승인전 혹은 결재전)
     *
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int deleteOffLinePropose(RequestBox box) throws Exception {
        int isOk = 0;
        boolean v_CreateConnManager = false;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        // ListSet ls = null;
        String sql = "";

        String v_subj = (String) box.get("p_subj");
        String v_year = (String) box.get("p_year");
        String v_subjseq = (String) box.get("p_subjseq");
        String v_seq = (String) box.get("p_seq");
        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            sql = "DELETE FROM TZ_OFFPROPOSE WHERE SUBJ = ? AND YEAR = ? AND SUBJSEQ = ? AND USERID = ? AND SEQ=? \n";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_subj);
            pstmt.setString(2, v_year);
            pstmt.setString(3, v_subjseq);
            pstmt.setString(4, v_userid);
            pstmt.setString(5, v_seq);

            isOk = pstmt.executeUpdate();

            if (v_subj.equals("SB13033")) {
                sql = "DELETE FROM TZ_OFFPROPOSEFILE WHERE SUBJ = ? AND YEAR = ? AND SUBJSEQ = ? AND USERID = ? AND SEQ=? \n";
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, v_subj);
                pstmt.setString(2, v_year);
                pstmt.setString(3, v_subjseq);
                pstmt.setString(4, v_userid);
                pstmt.setString(5, v_seq);

                isOk = pstmt.executeUpdate();
            }
        } catch (Exception ex) {

            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (v_CreateConnManager) {
                if (connMgr != null) {
                    try {
                        connMgr.freeConnection();
                    } catch (Exception e10) {
                    }
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    /**
     * 회원등록
     *
     * @param box receive from the form object and session
     * @return String
     * @throws Exception
     */
    public int insertEduService(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_onlineanswer1 = box.getString("p_onlineanswer1");
        String v_onlineanswer2 = box.getString("p_onlineanswer2");
        String v_onlineanswer3 = box.getString("p_onlineanswer3");
        String v_onlineanswer4 = box.getString("p_onlineanswer4");
        String v_onlineanswer5 = box.getString("p_onlineanswer5");
        String v_serviceanswer1 = box.getString("p_serviceanswer1");
        String v_serviceanswer2 = box.getString("p_serviceanswer2");
        String v_serviceanswer3 = box.getString("p_serviceanswer3");
        String v_serviceanswer4 = box.getString("p_serviceanswer4");
        String v_serviceanswer5 = box.getString("p_serviceanswer5");
        String v_webanswer1 = box.getString("p_webanswer1");
        String v_webanswer2 = box.getString("p_webanswer2");
        String v_webanswer3 = box.getString("p_webanswer3");
        String v_webanswer4 = box.getString("p_webanswer4");
        String v_webanswer5 = box.getString("p_webanswer5");
        String v_webanswer6 = box.getString("p_webanswer6");
        String v_contributeanswer1 = box.getString("p_contributeanswer1");
        String v_contributeanswer2 = box.getString("p_contributeanswer2");
        String v_contributeanswer3 = box.getString("p_contributeanswer3");
        String v_contributeanswer4 = box.getString("p_contributeanswer4");
        String v_contributeanswer5 = box.getString("p_contributeanswer5");
        String v_userid = box.getString("p_userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = " INSERT INTO TZ_EDUSERVICESUL                                                                       \n";
            sql += "   ( ONLINEANSWER1, ONLINEANSWER2, ONLINEANSWER3, ONLINEANSWER4, ONLINEANSWER5,                     \n";
            sql += "     SERVICEANSWER1, SERVICEANSWER2, SERVICEANSWER3, SERVICEANSWER4, SERVICEANSWER5,                \n";
            sql += "     WEBANSWER1, WEBANSWER2, WEBANSWER3, WEBANSWER4, WEBANSWER5, WEBANSWER6,                        \n";
            sql += "     CONTRIBUTEANSWER1, CONTRIBUTEANSWER2, CONTRIBUTEANSWER3, CONTRIBUTEANSWER4, CONTRIBUTEANSWER5, \n";
            sql += "     USERID, INUSERID, INDATE ) VALUES                                                              \n";
            sql += "   ( ?, ?, ?, ?, ?,                                                                                 \n";
            sql += "     ?, ?, ?, ?, ?,                                                                                 \n";
            sql += "     ?, ?, ? ,?, ?, ?,                                                                              \n";
            sql += "     ?, ?, ?, ?, ?,                                                                                 \n";
            sql += "     ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS') )                                                     ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_onlineanswer1);
            pstmt.setString(2, v_onlineanswer2);
            pstmt.setString(3, v_onlineanswer3);
            pstmt.setString(4, v_onlineanswer4);
            pstmt.setString(5, v_onlineanswer5);
            pstmt.setString(6, v_serviceanswer1);
            pstmt.setString(7, v_serviceanswer2);
            pstmt.setString(8, v_serviceanswer3);
            pstmt.setString(9, v_serviceanswer4);
            pstmt.setString(10, v_serviceanswer5);
            pstmt.setString(11, v_webanswer1);
            pstmt.setString(12, v_webanswer2);
            pstmt.setString(13, v_webanswer3);
            pstmt.setString(14, v_webanswer4);
            pstmt.setString(15, v_webanswer5);
            pstmt.setString(16, v_webanswer6);
            pstmt.setString(17, v_contributeanswer1);
            pstmt.setString(18, v_contributeanswer2);
            pstmt.setString(19, v_contributeanswer3);
            pstmt.setString(20, v_contributeanswer4);
            pstmt.setString(21, v_contributeanswer5);
            pstmt.setString(22, v_userid);
            pstmt.setString(23, v_userid);

            System.out.println(v_onlineanswer1 + "/" + v_onlineanswer2 + "/" + v_onlineanswer3 + "/" + v_onlineanswer4 + "/" + v_onlineanswer5);
            System.out.println(v_serviceanswer1 + "/" + v_serviceanswer2 + "/" + v_serviceanswer3 + "/" + v_serviceanswer4 + "/" + v_serviceanswer5);
            System.out.println(v_webanswer1 + "/" + v_webanswer2 + "/" + v_webanswer3 + "/" + v_webanswer4 + "/" + v_webanswer5 + "/" + v_webanswer6);
            System.out.println(v_contributeanswer1 + "/" + v_contributeanswer2 + "/" + v_contributeanswer3 + "/" + v_contributeanswer4 + "/" + v_contributeanswer5);
            System.out.println(v_userid);
            isOk = pstmt.executeUpdate();

            /*
             * if (isOk != 0) { box.put("isOk",String.valueOf(isOk));
             * box.setSession("userid", v_userid); box.setSession("name" ,
             * v_name); }
             */

            if (isOk == 1) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->" + sql + "\r\n" + ex.getMessage());
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
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 나의 수강신청 이력건수(온라인/최종승인)
     *
     * @param box receive from the form object and session
     * @return int 나의 워크샵 수강 건수
     * @throws Exception
     */
    public int selectGetProposeHistoryYCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT  COUNT(CHKFINAL) cnt\n";
            sql += "   FROM TZ_PROPOSE A\n";
            sql += "     left join TZ_SUBJSEQ B on A.SUBJ = B.SUBJ and A.YEAR = B.YEAR and A.SUBJSEQ = B.SUBJSEQ\n";
            sql += "     left join TZ_BILLINFO C on A.TID = C.TID\n";
            sql += "     left join  TZ_SUBJ D on A.SUBJ = D.SUBJ\n";
            sql += "  WHERE a.userid = " + SQLString.Format(v_user_id);
            sql += "     And b.GRCODE=" + SQLString.Format(v_grcode) + "\n";
            sql += "    AND a.CHKFINAL = 'Y'\n";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * 나의 수강신청 이력건수(온라인/승인대기)
     *
     * @param box receive from the form object and session
     * @return int 나의 워크샵 수강 건수
     * @throws Exception
     */
    public int selectGetProposeHistoryBCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT  COUNT(CHKFINAL) cnt\n";
            sql += "   FROM TZ_PROPOSE A\n";
            sql += "     left join TZ_SUBJSEQ B on A.SUBJ = B.SUBJ and A.YEAR = B.YEAR and A.SUBJSEQ = B.SUBJSEQ\n";
            sql += "     left join TZ_BILLINFO C on A.TID = C.TID\n";
            sql += "     left join  TZ_SUBJ D on A.SUBJ = D.SUBJ\n";
            sql += "  WHERE a.userid = " + SQLString.Format(v_user_id);
            sql += "     And b.GRCODE=" + SQLString.Format(v_grcode) + "\n";
            sql += "    AND a.CHKFINAL = 'B'\n";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * 나의 수강신청 이력건수(오프라인/승인대기)
     *
     * @param box receive from the form object and session
     * @return int 나의 워크샵 수강 건수
     * @throws Exception
     */
    public int selectGetOffProposeHistoryUCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");
        // String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT  COUNT(CHKFINAL) cnt\n";
            sql += "   FROM tz_offpropose a, tz_offsubjseq b, tz_offbillinfo c, tz_offsubj d\n";
            sql += "  WHERE a.tid = c.tid(+)\n";
            sql += "    AND a.userid = " + SQLString.Format(v_user_id);
            sql += "    AND a.subj = b.subj\n";
            sql += "    AND a.YEAR = b.YEAR\n";
            sql += "    AND a.subjseq = b.subjseq\n";
            sql += "    AND a.subj = d.subj\n";
            sql += "    And b.seq = '1'\n";
            sql += "    AND CHKFINAL = 'U'\n";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * 나의 수강신청 이력건수(오프라인/최종승인)
     *
     * @param box receive from the form object and session
     * @return int 나의 워크샵 수강 건수
     * @throws Exception
     */
    public int selectGetOffProposeHistoryYCnt(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_user_id = box.getSession("userid");
        // String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();

            sql = " SELECT  COUNT(CHKFINAL) cnt\n";
            sql += "   FROM tz_offpropose a, tz_offsubjseq b, tz_offbillinfo c, tz_offsubj d\n";
            sql += "  WHERE a.tid = c.tid(+)\n";
            sql += "    AND a.userid = " + SQLString.Format(v_user_id);
            sql += "    AND a.subj = b.subj\n";
            sql += "    AND a.YEAR = b.YEAR\n";
            sql += "    AND a.subjseq = b.subjseq\n";
            sql += "    AND a.subj = d.subj\n";
            sql += "    And b.seq = '1'\n";
            sql += "    AND CHKFINAL = 'Y'\n";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
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
        return result;
    }

    /**
     * 수강신청리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList 수강신청리스트
     */
    public ArrayList<DataBox> selectProposeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        String v_userid = box.getSession("userid");
        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");

        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql.append(" SELECT TID, SUBJ, YEAR, SUBJSEQ, SUBJNM, EDUSTART,BIYONG, introducefilenamenew, \n");
            sql.append("		EDUEND, APPDATE, REFUNDDATE, CHKFIRST, CHKFINAL,\n");
            sql.append("         (SELECT SA.CLASSNAME\n");
            sql.append("            FROM TZ_SUBJ SB, TZ_SUBJATT SA\n");
            sql.append("           WHERE SB.UPPERCLASS = SA.UPPERCLASS\n");
            sql.append("             AND SA.MIDDLECLASS = '000'\n");
            sql.append("             AND SA.LOWERCLASS = '000'\n");
            sql.append("             AND SB.SUBJ = A.SUBJ) CLASSNAME,\n");
            sql.append("          REFUNDABLEDATE, REFUNDABLEYN, CANCELABLEYN, REFUNDYN, CANCELDATE, PAYMETHOD,\n");
            sql.append("          ACCEPTYN, RANK,\n");
            sql.append("          CASE\n");
            sql.append("             WHEN RANK = 1\n");
            sql.append("                THEN COUNT (*) OVER (PARTITION BY TID)\n");
            sql.append("             ELSE 0\n");
            sql.append("          END AS ROWSPAN\n");
            sql.append("     FROM (SELECT D.introducefilenamenew, A.TID, B.SUBJ, B.YEAR, B.SUBJSEQ, B.SUBJNM, B.EDUSTART,\n");
            sql.append("                  B.EDUEND, A.APPDATE, 'Y' ACCEPTYN, 'N' REFUNDYN, A.CHKFIRST, A.CHKFINAL,\n");
            sql.append("                  A.CANCELDATE, C.PAYMETHOD, D.UPPERCLASS, '' REFUNDDATE,\n");
            sql.append("                  TO_CHAR (  TO_DATE (SUBSTR (EDUSTART, 1, 8), 'YYYYMMDD')\n");
            sql.append("                           + B.CANCELDAYS,\n");
            sql.append("                           'YYYYMMDD'\n");
            sql.append("                          ) AS REFUNDABLEDATE,\n");
            sql.append("                  CASE WHEN (    SUBSTR (REPLACE(STARTCANCELDATE,'-'), 1, 8) <= TO_CHAR (SYSDATE, 'YYYYMMDD')\n");
            sql.append("                             AND SUBSTR (REPLACE(ENDCANCELDATE,'-'), 1, 8) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )\n");
            sql.append("                       THEN 'Y'\n");
            sql.append("                       ELSE 'N'\n");
            sql.append("                  END REFUNDABLEYN,\n");
            sql.append("                  CASE WHEN (    SUBSTR (REPLACE(STARTCANCELDATE,'-'), 1, 8) <= TO_CHAR (SYSDATE, 'YYYYMMDD')\n");
            sql.append("                             AND SUBSTR (REPLACE(ENDCANCELDATE,'-'), 1, 8) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )\n");
            sql.append("                       THEN 'Y'\n");
            sql.append("                       ELSE 'N'\n");
            sql.append("                  END CANCELABLEYN,\n");
            sql.append("                  RANK () OVER (PARTITION BY A.TID ORDER BY A.TID,\n");
            sql.append("                   B.SUBJ) RANK, B.BIYONG\n");
            sql.append("             FROM TZ_PROPOSE A\n");
            sql.append("             left join TZ_SUBJSEQ B on A.SUBJ = B.SUBJ and A.YEAR = B.YEAR and A.SUBJSEQ = B.SUBJSEQ\n");
            sql.append("             left join TZ_BILLINFO C on A.TID = C.TID\n");
            sql.append("             left join  TZ_SUBJ D on A.SUBJ = D.SUBJ\n");
            sql.append("            WHERE \n");
            sql.append("               A.USERID = ").append(SQLString.Format(v_userid)).append("\n");
            sql.append("              AND b.grcode = ").append(SQLString.Format(box.getSession("tem_grcode"))).append("\n");

            if (!v_upperclass.equals("ALL"))
                sql.append(" AND D.UPPERCLASS = ").append(SQLString.Format(v_upperclass)).append("\n");

            sql.append("           UNION ALL\n");
            sql.append("           SELECT d.introducefilenamenew, A.TID, B.SUBJ, B.YEAR, B.SUBJSEQ, B.SUBJNM, B.EDUSTART,  \n");
            sql.append("                  B.EDUEND, A.APPDATE, 'N' ACCEPTYN, 'Y' REFUNDYN, '' CHKFIRST, '' CHKFINAL,\n");
            sql.append("                  A.CANCELDATE, C.PAYMETHOD, D.UPPERCLASS, A.REFUNDDATE,\n");
            sql.append("                  TO_CHAR (  TO_DATE (SUBSTR (EDUSTART, 1, 8), 'YYYYMMDD')\n");
            sql.append("                           + B.CANCELDAYS,\n");
            sql.append("                           'YYYYMMDD'\n");
            sql.append("                          ) AS REFUNDABLEDATE,\n");
            sql.append("                  'N' REFUNDABLEYN,\n");
            sql.append("                  'N' CALCELABLEYN,\n");
            sql.append("                  RANK () OVER (PARTITION BY A.TID ORDER BY A.TID,\n");
            sql.append("                   B.SUBJ) RANK, B.BIYONG\n");
            sql.append("             FROM TZ_CANCEL A, TZ_SUBJSEQ B, TZ_BILLINFO C, TZ_SUBJ D\n");
            sql.append("            WHERE (1 = 1)\n");
            sql.append("              AND A.TID = C.TID\n");
            sql.append("              AND A.USERID = ").append(SQLString.Format(v_userid)).append("\n");
            sql.append("              AND A.SUBJ = B.SUBJ\n");
            sql.append("              AND A.YEAR = B.YEAR\n");
            sql.append("              AND A.SUBJSEQ = B.SUBJSEQ\n");
            sql.append("              AND B.grcode = ").append(SQLString.Format(box.getSession("tem_grcode"))).append("\n");
            sql.append("              AND A.SUBJ = D.SUBJ\n");

            if (!v_upperclass.equals("ALL"))
                sql.append(" AND D.UPPERCLASS = ").append(SQLString.Format(v_upperclass)).append("\n");

            sql.append("		    ) A\n");
            sql.append(" ORDER BY EDUSTART DESC \n");

            ls = connMgr.executeQuery(sql.toString());

            String count_sql1 = "";
            count_sql1 = "select count(*) from ( " + sql.toString() + ")";
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); // 전체 row 수를 반환한다

            ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

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
     * 대시보드 수강 종합 갯 수 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList 대시보드 수강 종합 갯 수 리스트
     */
    public ArrayList<DataBox> selectDashboardCntList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql.append(" SELECT proposeCnt                                                                                                  \n");
            sql.append("      , graduatedCnt                                                                                                \n");
            sql.append("      , proposeCnt - graduatedCnt ungraduatedCnt                                                                    \n");
            sql.append("   FROM (                                                                                                           \n");
            sql.append("            SELECT (                                                                                                \n");
            sql.append("                       SELECT COUNT(*)                                                                              \n");
            sql.append("                         FROM TZ_PROPOSE A                                                                          \n");
            sql.append("                            , TZ_SUBJSEQ B                                                                          \n");
            sql.append("                        WHERE A.SUBJ     = B.SUBJ(+)                                                                 \n");
            sql.append("                          AND A.YEAR     = B.YEAR(+)                                                                 \n");
            sql.append("                          AND A.SUBJSEQ  = B.SUBJSEQ(+)                                                             \n");
            sql.append("                          AND A.USERID   = ").append(SQLString.Format(v_userid)).append("                           \n");
            sql.append("                          AND B.GRCODE   = ").append(SQLString.Format(box.getSession("tem_grcode"))).append("   \n");
            sql.append("                   ) proposeCnt                                                                                     \n");
            sql.append("                 , (                                                                                                \n");
            sql.append("                       SELECT COUNT(*)                                                                              \n");
            sql.append("                         FROM TZ_PROPOSE A                                                                          \n");
            sql.append("                            , TZ_STUDENT B                                                                          \n");
            sql.append("                            , TZ_SUBJSEQ C                                                                          \n");
            sql.append("                        WHERE A.SUBJ     = B.SUBJ(+)                                                                \n");
            sql.append("                          AND A.YEAR     = B.YEAR(+)                                                                \n");
            sql.append("                          AND A.SUBJSEQ  = B.SUBJSEQ(+)                                                             \n");
            sql.append("                          AND A.USERID   = B.USERID(+)                                                              \n");
            sql.append("                          AND A.SUBJ     = C.SUBJ(+)                                                                \n");
            sql.append("                          AND A.YEAR     = C.YEAR(+)                                                                \n");
            sql.append("                          AND A.SUBJSEQ  = C.SUBJSEQ(+)                                                             \n");
            sql.append("                          AND A.USERID   = ").append(SQLString.Format(v_userid)).append("                           \n");
            sql.append("                          AND C.GRCODE   = ").append(SQLString.Format(box.getSession("tem_grcode"))).append("   \n");
            sql.append("                          AND B.ISGRADUATED = 'Y'                                                                   \n");
            sql.append("                          AND B.SERNO IS NOT NULL                                                                   \n");
            sql.append("                   ) graduatedCnt                                                                                   \n");
            sql.append("              FROM DUAL                                                                                             \n");
            sql.append("        )                                                                                                           \n");

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
     * 대시보드 수강 중인 과정 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList 대시보드 수강 중인 과정 리스트
     */
    public ArrayList<DataBox> dashBoardStudyList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql.append("SELECT A.INTRODUCEFILENAMENEW                                                                                       \n");
            sql.append("     , A.SUBJ                                                                                                       \n");
            sql.append("     , A.SUBJNM                                                                                                     \n");
            sql.append("     , A.ISONOFF                                                                                                    \n");
            sql.append("     , A.EDUSTART                                                                                                   \n");
            sql.append("     , A.EDUEND                                                                                                     \n");
            sql.append("     , (SELECT X.SCORE                                                                                              \n");
            sql.append("          FROM TZ_STUDENT X                                                                                         \n");
            sql.append("         WHERE A.SUBJ    = X.SUBJ                                                                                   \n");
            sql.append("           AND A.YEAR    = X.YEAR                                                                                   \n");
            sql.append("           AND A.SUBJSEQ = X.SUBJSEQ                                                                                \n");
            sql.append("           AND B.USERID  = X.USERID) SCORE                                                                          \n");
            sql.append("  FROM VZ_SCSUBJSEQIMGMOBILE A                                                                                      \n");
            sql.append("     , TZ_PROPOSE            B                                                                                      \n");
            sql.append("     , TZ_TAX                C                                                                                      \n");
            sql.append("     , TZ_BILLING            D                                                                                      \n");
            sql.append(" WHERE A.SUBJ     = B.SUBJ                                                                                          \n");
            sql.append("   AND A.YEAR     = B.YEAR                                                                                          \n");
            sql.append("   AND A.SUBJSEQ  = B.SUBJSEQ                                                                                       \n");
            sql.append("   AND A.SCSUBJ   = C.SUBJ(+)                                                                                       \n");
            sql.append("   AND A.YEAR     = C.YEAR(+)                                                                                       \n");
            sql.append("   AND A.SUBJSEQ  = C.SUBJSEQ(+)                                                                                    \n");
            sql.append("   AND A.SCSUBJ   = D.SUBJ(+)                                                                                       \n");
            sql.append("   AND A.YEAR     = D.YEAR(+)                                                                                       \n");
            sql.append("   AND A.SUBJSEQ  = D.SUBJSEQ(+)                                                                                    \n");
            sql.append("   AND B.CHKFINAL = 'Y'                                                                                             \n");
            sql.append("   AND B.USERID   = ").append(SQLString.Format(v_userid)).append("                                                  \n");
            sql.append("   AND A.GRCODE   = ").append(SQLString.Format(box.getSession("tem_grcode"))).append("                          \n");
            sql.append("   AND TO_CHAR(SYSDATE, 'YYYYMMDDHH24') <= A.EDUEND                                                                 \n");
            sql.append(" ORDER BY A.EDUSTART DESC, A.COURSE, A.SCUPPERCLASS, A.SCMIDDLECLASS, A.SUBJNM, A.SUBJ,A.YEAR,A.SUBJSEQ,A.EDUEND    \n");

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
     * 대시보드 카테고리 리스트
     *
     * @param box receive from the form object and session
     * @return ArrayList 대시보드 카테고리 리스트
     */
    public ArrayList<DataBox> selectDashboardCateList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        StringBuffer sql = new StringBuffer();
        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql.append("SELECT NVL(C.AREA, 'XX') AREA                                                               \n");
            sql.append("     , NVL((SELECT CODENM                                                                   \n");
            sql.append("              FROM TZ_CODE                                                                  \n");
            sql.append("             WHERE GUBUN = '0101'                                                           \n");
            sql.append("               AND CODE  = NVL(C.AREA, 'XX')), '기타') AREANAME                               \n");
            sql.append("     , COUNT(NVL(C.AREA, 'XX')) CNT                                                          \n");
            sql.append("  FROM TZ_PROPOSE A                                                                         \n");
            sql.append("     , TZ_SUBJSEQ B                                                                         \n");
            sql.append("     , TZ_SUBJ    C                                                                         \n");
            sql.append(" WHERE A.SUBJ    = B.SUBJ(+)                                                                \n");
            sql.append("   AND A.YEAR    = B.YEAR(+)                                                                \n");
            sql.append("   AND A.SUBJSEQ = B.SUBJSEQ(+)                                                             \n");
            sql.append("   AND B.SUBJ    = C.SUBJ                                                                  \n");
            sql.append("   AND A.USERID  = ").append(SQLString.Format(v_userid)).append("                          \n");
            sql.append("   AND B.GRCODE  = ").append(SQLString.Format(box.getSession("tem_grcode"))).append(" \n");
            sql.append("   AND C.ISUSE   = 'Y'                                                                     \n");
            sql.append("   AND NVL(C.AREA, 'XX') != 'W0'                                                            \n");
            sql.append(" GROUP BY NVL(C.AREA, 'XX')                                                                 \n");
            sql.append(" ORDER BY NVL(C.AREA, 'XX')                                                                 \n");

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
