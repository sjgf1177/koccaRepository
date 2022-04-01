package schedule;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.credu.library.ErrorManager;

public class CSUserStudyInfoBean {


    /**
     * CP 학습자 진도정보를 조회한다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<HashMap<String, String>> selectCPUserStudyInfo(String grcode) throws Exception {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        System.out.println("grcode : " + grcode);

        ArrayList<HashMap<String, String>> list = null;
        HashMap<String, String> map = null;

        StringBuilder sql = new StringBuilder();

        int updateResultCnt = 0;
        
        // String dbURL = "jdbc:oracle:thin:@172.16.80.76:1521:ACADEMY";
        String dbURL = "jdbc:oracle:thin:@211.201.145.100:1521:ACADEMY";

        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            
            conn = DriverManager.getConnection(dbURL, "kocca", "kocca2");
            
            if ( conn == null ) {
                System.out.println("DB Connection fail !!");
            } else {
                System.out.println("DB Connection success !!");
            }

            updateResultCnt = this.updateCPUserStudyInfo(conn, grcode);

            System.out.println("학습자 진도 정보 갱신 결과 : " + updateResultCnt);

            list = new ArrayList<HashMap<String, String>>();

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
            sql.append("  FROM  TZ_MEMBER A     \n");
            sql.append("    ,   TZ_SUBJSEQ B    \n");
            sql.append("    ,   TZ_STUDENT C    \n");
            sql.append(" WHERE  A.GRCODE = ?    \n");
            sql.append("   AND  A.STATE = 'Y'   \n");
            sql.append("   AND  TO_CHAR(SYSDATE - 1, 'YYYYMMDDHH24') BETWEEN B.EDUSTART AND B.EDUEND\n");
            sql.append("   AND  A.GRCODE = B.GRCODE     \n");
            sql.append("   AND  A.USERID = C.USERID     \n");
            sql.append("   AND  B.SUBJ = C.SUBJ         \n");
            sql.append("   AND  B.YEAR = C.YEAR         \n");
            sql.append("   AND  B.SUBJSEQ = C.SUBJSEQ   \n");

            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, grcode);
            
            rs = pstmt.executeQuery();
            
            while ( rs.next() ) {
                map = new HashMap<String, String>();
                map.put("eduresult", rs.getString("eduresult"));
                map.put("cs_subj", rs.getString("cs_subj"));
                map.put("subj", rs.getString("subj"));
                map.put("year", rs.getString("year"));
                map.put("subjseq", rs.getString("subjseq"));

                list.add(map);
            }

        } catch (Exception ex) {
            System.out.println("schedule selectCPUserStudyInfo Exception : " + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                    rs = null;
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                    pstmt = null;
                } catch (Exception e) {
                }
            }
            if (conn != null) {
                try {
                    conn.close();
                    conn = null;
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    public int updateCPUserStudyInfo(Connection conn, String grcode) throws Exception {
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int resultCnt[] = null;

        StringBuilder sql = new StringBuilder();

        ArrayList<HashMap<String, String>> userStudyInfoList = null;
        HashMap<String, String> userStudyInfoMap = null;

        int lessonCnt = 0, studyCnt = 0;
        double gradStep = 0d, progStep = 0d;
        String gradeYn = "";

        try {
            conn.setAutoCommit(false);

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
            sql.append(" WHERE  A.GRCODE = ?                        \n");
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

            pstmt = conn.prepareStatement(sql.toString());
            pstmt.setString(1, grcode);
            rs = pstmt.executeQuery();

            userStudyInfoList = new ArrayList<HashMap<String, String>>();

            while (rs.next()) {
                progStep = 0d;

                lessonCnt = rs.getInt("lesson_cnt");
                studyCnt = rs.getInt("study_cnt");
                gradStep = rs.getDouble("gradstep");

                if (studyCnt > 0) {
                    progStep = (double) Math.round((double) studyCnt / lessonCnt * 100 * 100) / 100;

                    progStep = (progStep > 100) ? 100 : progStep;

                    if (progStep >= gradStep) { // 학습한 진도율이 수료기준 진도율보다 높을 경우 수료여부 값을 'Y'로 설정
                        gradeYn = "Y";
                    } else {
                        gradeYn = "N";
                    }

                    userStudyInfoMap = new HashMap<String, String>();

                    userStudyInfoMap.put("userid", rs.getString("userid"));
                    userStudyInfoMap.put("subj", rs.getString("subj"));
                    userStudyInfoMap.put("year", rs.getString("year"));
                    userStudyInfoMap.put("subjseq", rs.getString("subjseq"));
                    userStudyInfoMap.put("progStep", String.valueOf(progStep));
                    userStudyInfoMap.put("gradeYn", gradeYn);
                    userStudyInfoList.add(userStudyInfoMap);
                }
            }

            pstmt.close();
            pstmt = null;
            rs.close();
            rs = null;
            

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

            pstmt = conn.prepareStatement(sql.toString());

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
                conn.commit();
            }

        } catch (Exception ex) {
            conn.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql1 = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return resultCnt.length;
    }
}
