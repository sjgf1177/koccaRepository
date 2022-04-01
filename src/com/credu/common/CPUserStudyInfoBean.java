//**********************************************************
//  1. 제      목: PROPOSE STATUS ADMIN BEAN
//  2. 프로그램명: EduInfoBean.java
//  3. 개      요: 신청 현황 관리자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//**********************************************************
package com.credu.common;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;

public class CPUserStudyInfoBean {

    public CPUserStudyInfoBean() {
    }

    /**
     * CP 학습자 진도정보를 조회한다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectCPUserStudyInfo(String grcode) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuilder sql = new StringBuilder();

        // String grcode = box.getStringDefault("grcode", "N000096"); // default는 CJ

        int updateResultCnt = 0;

        try {
            connMgr = new DBConnectionManager();

            updateResultCnt = this.updateCPUserStudyInfo(grcode);

            System.out.println("학습자 진도 정보 갱신 결과 : " + updateResultCnt);

            list = new ArrayList<DataBox>();

            // 학습자 학습 정보 조회
            sql.setLength(0);
            sql.append("/* cj 교육자료 연동을 위한 학습자 학습정보 조회  */\n");
            sql.append("SELECT  B.CS_YEAR || '/' ||                     \n");
            sql.append("        B.CS_SUBJ || '/' ||                     \n");
            sql.append("        B.CS_SUBJSEQ || '/' ||                  \n");
            sql.append("        REPLACE(A.USERID, 'CJW', '') || '/' ||  \n");
            sql.append("        C.TSTEP || '/' ||                       \n");
            sql.append("        C.AVTSTEP || '/' ||                     \n");
            sql.append("        C.MTEST || '/' ||                       \n");
            sql.append("        C.AVMTEST || '/' ||                     \n");
            sql.append("        C.FTEST || '/' ||                       \n");
            sql.append("        C.AVFTEST || '/' ||                     \n");
            sql.append("        C.REPORT || '/' ||                      \n");
            sql.append("        C.AVREPORT || '/' ||                    \n");
            sql.append("        C.ACT || '/' ||                         \n");
            sql.append("        C.AVACT || '/' ||                       \n");
            sql.append("        C.SCORE || '/' ||                       \n");
            sql.append("        NVL(C.ISGRADUATED, 'N') || '/' ||       \n");
            sql.append("        NVL(C.NOTGRADUETC, '''''') AS EDURESULT \n");
            sql.append("    ,   B.CS_SUBJ   \n");
            sql.append("    ,   B.SUBJ      \n");
            sql.append("    ,   B.YEAR      \n");
            sql.append("    ,   B.SUBJSEQ   \n");
            sql.append("  FROM  TZ_MEMBER A                                                     \n");
            sql.append("    ,   TZ_SUBJSEQ B                                                    \n");
            sql.append("    ,   TZ_STUDENT C                                                    \n");
            sql.append(" WHERE  A.GRCODE = '").append(grcode).append("'                         \n");
            sql.append("   AND  A.STATE = 'Y'                                                   \n");
            sql.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN B.EDUSTART AND B.EDUEND\n");
            sql.append("   AND  A.GRCODE = B.GRCODE                                             \n");
            sql.append("   AND  A.USERID = C.USERID                                             \n");
            sql.append("   AND  B.SUBJ = C.SUBJ                                                 \n");
            sql.append("   AND  B.YEAR = C.YEAR                                                 \n");
            sql.append("   AND  B.SUBJSEQ = C.SUBJSEQ                                           \n");

            ls = connMgr.executeQuery(sql.toString());
            
            while ( ls.next() ) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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
        return list;
    }

    public int updateCPUserStudyInfo(String grcode) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        int resultCnt[] = null;

        StringBuilder sql = new StringBuilder();

        // String grcode = box.getStringDefault("grcode", "N000096"); // default는 CJ

        ArrayList<Map<String, String>> userStudyInfoList = null;
        HashMap<String, String> userStudyInfoMap = null;

        int lessonCnt = 0, studyCnt = 0;
        double gradStep = 0d, progStep = 0d;
        String gradeYn = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // 학습 정보 조회 전 학습자의 점수 정보를 갱신해야 한다.
            sql.setLength(0);
            sql.append("SELECT  A.USERID                            \n");
            sql.append("    ,   B.SUBJ                              \n");
            sql.append("    ,   B.YEAR                              \n");
            sql.append("    ,   B.SUBJSEQ                           \n");
            sql.append("    ,   B.GRADSTEP                          \n");
            sql.append("    ,   COUNT(D.LESSON) AS LESSON_CNT       \n");
            sql.append("    ,   (                                   \n");
            sql.append("        SELECT  COUNT(LESSON)               \n");
            sql.append("          FROM  TZ_PROGRESS                 \n");
            sql.append("         WHERE  USERID = A.USERID           \n");
            sql.append("           AND  SUBJ = B.SUBJ               \n");
            sql.append("           AND  YEAR = B.YEAR               \n");
            sql.append("           AND  SUBJSEQ = B.SUBJSEQ         \n");
            sql.append("        ) AS STUDY_CNT                      \n");
            sql.append("  FROM  TZ_MEMBER A                         \n");
            sql.append("    ,   TZ_SUBJSEQ B                        \n");
            sql.append("    ,   TZ_PROPOSE C                        \n");
            sql.append("    ,   TZ_SUBJLESSON D                     \n");
            sql.append(" WHERE  A.GRCODE = '").append(grcode).append("' \n");
            sql.append("   AND  A.GRCODE = B.GRCODE                 \n");
            sql.append("   AND  A.STATE = 'Y'                       \n");
            sql.append("   AND  A.USERID = C.USERID                 \n");
            sql.append("   AND  TO_CHAR(SYSDATE - 1, 'YYYYMMDDHH24')\n");
            sql.append("        BETWEEN B.EDUSTART                  \n");
            sql.append("            AND B.EDUEND                    \n");
            sql.append("   AND  B.SUBJ = C.SUBJ                     \n");
            sql.append("   AND  B.YEAR = C.YEAR                     \n");
            sql.append("   AND  B.SUBJSEQ = C.SUBJSEQ               \n");
            sql.append("   AND  B.SUBJ = D.SUBJ                     \n");
            sql.append(" GROUP  BY A.USERID                         \n");
            sql.append("    ,   B.SUBJ                              \n");
            sql.append("    ,   B.YEAR                              \n");
            sql.append("    ,   B.SUBJSEQ                           \n");
            sql.append("    ,   B.GRADSTEP                          \n");

            ls = connMgr.executeQuery(sql.toString());

            userStudyInfoList = new ArrayList<Map<String, String>>();

            while (ls.next()) {
                progStep = 0d;

                lessonCnt = ls.getInt("lesson_cnt");
                studyCnt = ls.getInt("study_cnt");
                gradStep = ls.getDouble("gradstep");

                if (studyCnt > 0) {
                    progStep = (double) Math.round((double) studyCnt / lessonCnt * 100 * 100) / 100;

                    progStep = (progStep > 100) ? 100 : progStep;

                    if (progStep >= gradStep) { // 학습한 진도율이 수료기준 진도율보다 높을 경우 수료여부 값을 'Y'로 설정
                        gradeYn = "Y";
                    } else {
                        gradeYn = "N";
                    }

                    userStudyInfoMap = new HashMap<String, String>();

                    userStudyInfoMap.put("userid", ls.getString("userid"));
                    userStudyInfoMap.put("subj", ls.getString("subj"));
                    userStudyInfoMap.put("year", ls.getString("year"));
                    userStudyInfoMap.put("subjseq", ls.getString("subjseq"));
                    userStudyInfoMap.put("progStep", String.valueOf(progStep));
                    userStudyInfoMap.put("gradeYn", gradeYn);
                    userStudyInfoList.add(userStudyInfoMap);
                }
            }
            
            ls.close();
            ls = null;
            

            sql.setLength(0);
            sql.append("UPDATE  TZ_STUDENT  \n");
            sql.append("   SET  SCORE = ?   \n");
            sql.append("    ,   TSTEP = ?   \n");
            sql.append("    ,   AVTSTEP = ? \n");
            sql.append("    ,   ISGRADUATED = ? \n");
            sql.append("    ,   LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
            sql.append(" WHERE  USERID = ?  \n");
            sql.append("   AND  SUBJ = ?    \n");
            sql.append("   AND  YEAR = ?    \n");
            sql.append("   AND  SUBJSEQ = ? \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            for (int i = 0; i < userStudyInfoList.size(); i++) {
                userStudyInfoMap = new HashMap<String, String>();
                userStudyInfoMap = (HashMap<String, String>) userStudyInfoList.get(i);

                pstmt.setDouble(1, Double.parseDouble(userStudyInfoMap.get("progStep")));
                pstmt.setDouble(2, Double.parseDouble(userStudyInfoMap.get("progStep")));
                pstmt.setDouble(3, Double.parseDouble(userStudyInfoMap.get("progStep")));
                pstmt.setString(4, userStudyInfoMap.get("gradeYn"));
                pstmt.setString(5, userStudyInfoMap.get("userid"));
                pstmt.setString(6, userStudyInfoMap.get("subj"));
                pstmt.setString(7, userStudyInfoMap.get("year"));
                pstmt.setString(8, userStudyInfoMap.get("subjseq"));

                pstmt.addBatch();

                System.out.print("userid : " + userStudyInfoMap.get("userid"));
                System.out.print(" / subj : " + userStudyInfoMap.get("subj"));
                System.out.print(" / year : " + userStudyInfoMap.get("year"));
                System.out.print(" / subjseq : " + userStudyInfoMap.get("subjseq"));
                System.out.print(" / progStep : " + userStudyInfoMap.get("progStep"));
                System.out.println(" / gradeYn : " + userStudyInfoMap.get("gradeYn"));
            }

            resultCnt = pstmt.executeBatch();
            
            if ( resultCnt.length > 0 ) {
                connMgr.commit();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql1 = " + sql.toString() + "\r\n" + ex.getMessage());
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
        return resultCnt.length;
    }
}