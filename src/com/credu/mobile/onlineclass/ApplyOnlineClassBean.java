package com.credu.mobile.onlineclass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SmsBean;
import com.credu.propose.ProposeCourseBean;

/**
 * 
 * @author saderaser
 * 
 */
public class ApplyOnlineClassBean {
    /**
     * 정규과정 분류별 목록을 조회한다.
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public String applyOnlineClass(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;

        String resultMsg = "";
        String grcode = box.getSession("grcode");

        String[] limitedGrcodeArr = { "N000001", "N000055", "N000030", "N000058", "N000060", "N000061", "N000063", "N000018", "N000064", "N000022", "N000081", "N000083" };

        boolean isPossilbeByDate = false;
        boolean isPossibleByStudentLimit = false;
        boolean isAlreayApply = false;
        boolean isMonthlyLimitOver = false;

        boolean isGrcodeLimited = false;

        int resultCnt = 0;
        try {
            connMgr = new DBConnectionManager();

            if (grcode.equals("N000001")) { // 수강신청이 가능한 경우 수강신청 정보 등록
                
                resultCnt = registerApplyInfo(connMgr, box);

                if (resultCnt > 0) {
                    resultMsg = "apply.ok"; // 수강신청 완료

                } else {
                    resultMsg = "apply.fail"; // 수강신청 실패
                }
                
            } else {
                
                isPossilbeByDate = this.isPossibleSubjApplyByDate(connMgr, box); // 수강신청 가능 일자 확인
    
                if (isPossilbeByDate) { // 수강신청 가능한 일자일 경우
    
                    isPossibleByStudentLimit = this.isPossibleSubjApplyByStudentLimit(connMgr, box); // 수강신청 인원 및 정원 확인
    
                    if (isPossibleByStudentLimit) { // 수강신청 인원이 모집 정원보다 적을 경우
    
                        isAlreayApply = this.isAlreadyApply(connMgr, box); // 수강신청 여부 확인
    
                        if (!isAlreayApply) { // 아직 수강신청이 되지 않은 경우
    
                            for (int i = 0; i < limitedGrcodeArr.length; i++) {
                                if (limitedGrcodeArr[i].indexOf(grcode) > -1) {
                                    isGrcodeLimited = true;
                                    break;
                                }
                            }
    
                            if (isGrcodeLimited) { // 교육 그룹이 월별 수강신청 제한 건수가 있는 그룹일 경우
    
                                isMonthlyLimitOver = this.isMonthlyLimitOver(connMgr, box); // 사용자의 현재 차수의 학습진행중인 과정 건수와 다음 차수의 수강신청한 건수를 더한 전체 건수 확인 
    
                                if (isMonthlyLimitOver) {
                                    resultMsg = "monthly.limit.over";
    
                                } else {
                                    resultMsg = "possible.apply";
    
                                }
                            } else {
                                resultMsg = "possible.apply";
    
                            }
    
                        } else {
                            resultMsg = "already.apply.ok";
                        }
    
                    } else {
                        resultMsg = "student.limit.full";
                    }
    
                } else {
                    //resultMsg = "apply.time.over";
                    resultMsg = "apply.fail";
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("\r\n" + ex.getMessage());
        } finally {

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return resultMsg;
    }

    /**
     * 학습자가 수강신청을 할 때, 해당 과정의 수강신청 가능여부를 확인한다. 학습자가 수강신청 창을 장시간 열어 놓아도 세션이 종료되지
     * 않아 수강신청기간이 지난 후에도 신청이 되어 있는 경우가 발생하고 있다. 이를 미연에 방지하고자 수강신청 프로세스를 진행하기 전에
     * 현재 일자로 수강신청 여부를 확인한다. 참고로 사이트 세션 만료 시간이 설정되지 않은 것으로 보인다.
     * 
     * @param grCode
     * @param gYear
     * @param grSeq
     * @return
     * @throws Exception
     */
    private boolean isPossibleSubjApplyByDate(DBConnectionManager connMgr, RequestBox box) throws Exception {
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuilder sql = new StringBuilder();

        // int index = 1;
        boolean isApplyTimeOk = false;
        String grcode = box.getSession("grcode");
        String year = box.getString("year");
        String subj = box.getString("subj");
        String subjseq = box.getString("subjseq");
        try {
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() isPossibleSubjApplyByDate (수강신청 가능 일자 확인) */ \n");
            sql.append("SELECT  COUNT(A.GRSEQ) AS POSSIBLE_CNT      \n");
            sql.append("  FROM  TZ_GRSEQ A                          \n");
            sql.append("    ,   TZ_SUBJSEQ B                        \n");
            sql.append(" WHERE  A.GRCODE = '").append(grcode).append("'     \n");
            sql.append("   AND  A.GYEAR = '").append(year).append("'        \n");
            sql.append("   AND  B.SUBJ = '").append(subj).append("'         \n");
            sql.append("   AND  B.SUBJSEQ = '").append(subjseq).append("'   \n");
            sql.append("   AND  A.HOMEPAGEYN = 'Y'                  \n");
            sql.append("   AND  A.STAT = 'Y'                        \n");
            sql.append("   AND  A.GRCODE = B.GRCODE                 \n");
            sql.append("   AND  A.GYEAR = B.GYEAR                   \n");
            sql.append("   AND  A.GRSEQ = B.GRSEQ                   \n");
            sql.append("   AND  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') < B.PROPEND     \n");

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //
            //            pstmt.setString(index++, box.getSession("grcode"));
            //            pstmt.setString(index++, box.getString("year"));
            //            pstmt.setString(index++, box.getString("subj"));
            //            pstmt.setString(index++, box.getString("subjseq"));

            //            ls = new ListSet(pstmt);
            ls = connMgr.executeQuery(sql.toString());
            ls.next();

            isApplyTimeOk = (ls.getInt("possible_cnt") > 0) ? true : false;

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
            //                    pstmt = null;
            //                } catch (Exception e10) {
            //                }
            //            }
        }

        return isApplyTimeOk;
    }

    /**
     * 과정정원과 수강신청한 인원 수를 비교하여 수강신청 인원이 과정정원수를 초과하였는지를 비교한다.
     * 
     * @param connMgr
     * @param box
     * @return isPossibleByStudentLimited boolean true이면 정원 초과, false이면 정원 미달
     * @throws Exception
     */
    public boolean isPossibleSubjApplyByStudentLimit(DBConnectionManager connMgr, RequestBox box) throws Exception {
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        // int index = 1;
        int studentLimit = 0;
        int totalApplyCnt = 0;

        String grcode = box.getSession("grcode");
        String year = box.getString("year");
        String subj = box.getString("subj");
        String subjseq = box.getString("subjseq");

        boolean isPossibleByStudentLimit = false;
        try {
            sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() isPossibleSubjApplyByStudentLimit (과정 정원, 신청인원 조회) */ \n");
            sql.append("SELECT  A.STUDENTLIMIT              \n");
            sql.append("    ,   COUNT(B.USERID) APPLY_CNT   \n");
            sql.append("  FROM  TZ_SUBJSEQ A                \n");
            sql.append("    ,   TZ_PROPOSE B                \n");
            sql.append(" WHERE  A.GRCODE = '").append(grcode).append("'     \n");
            sql.append("   AND  A.SUBJ = '").append(subj).append("'         \n");
            sql.append("   AND  A.YEAR = '").append(year).append("'         \n");
            sql.append("   AND  A.SUBJSEQ = '").append(subjseq).append("'   \n");
            sql.append("   AND  A.SUBJ = B.SUBJ(+)          \n");
            sql.append("   AND  A.YEAR = B.YEAR(+)          \n");
            sql.append("   AND  A.SUBJSEQ = B.SUBJSEQ(+)    \n");
            sql.append(" GROUP BY A.STUDENTLIMIT            \n");

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setString(index++, box.getSession("grcode"));
            //            pstmt.setString(index++, box.getString("subj"));
            //            pstmt.setString(index++, box.getString("year"));
            //            pstmt.setString(index++, box.getString("subjseq"));
            //
            //            ls = new ListSet(pstmt);
            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                studentLimit = ls.getInt("studentlimit");
                totalApplyCnt = ls.getInt("apply_cnt");
            }

            isPossibleByStudentLimit = ((studentLimit - totalApplyCnt) > 0) ? true : false;

            ls.close();
            ls = null;
            //            pstmt.close();
            //            pstmt = null;

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                } catch (Exception e) {
            //                }
            //            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return isPossibleByStudentLimit;
    }

    /**
     * 이미 수강신청이 되어 있는 지를 판단한다.
     * 
     * @param connMgr
     * @param box
     * @return
     * @throws Exception
     */
    public boolean isAlreadyApply(DBConnectionManager connMgr, RequestBox box) throws Exception {
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        // int index = 1;
        int applyCnt = 0;

        String grcode = box.getSession("grcode");
        String userid = box.getSession("userid");
        String year = box.getString("year");
        String subj = box.getString("subj");
        String subjseq = box.getString("subjseq");

        boolean isAlready = false;
        try {
            sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() isAlreadyApply (수강신청 여부) */ \n");
            sql.append("SELECT  COUNT(B.USERID) APPLY_CNT   \n");
            sql.append("  FROM  TZ_SUBJSEQ A                \n");
            sql.append("    ,   TZ_PROPOSE B                \n");
            sql.append(" WHERE  A.GRCODE = '").append(grcode).append("' \n");
            sql.append("   AND  A.SUBJ = '").append(subj).append("'     \n");
            sql.append("   AND  A.YEAR = '").append(year).append("'     \n");
            sql.append("   AND  A.SUBJSEQ = '").append(subjseq).append("'   \n");
            sql.append("   AND  B.USERID = '").append(userid).append("' \n");
            sql.append("   AND  A.SUBJ = B.SUBJ             \n");
            sql.append("   AND  A.YEAR = B.YEAR             \n");
            sql.append("   AND  A.SUBJSEQ = B.SUBJSEQ       \n");
            sql.append(" GROUP BY A.STUDENTLIMIT            \n");

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setString(index++, box.getSession("grcode"));
            //            pstmt.setString(index++, box.getString("subj"));
            //            pstmt.setString(index++, box.getString("year"));
            //            pstmt.setString(index++, box.getString("subjseq"));
            //            pstmt.setString(index++, box.getSession("userid"));
            //
            //            ls = new ListSet(pstmt);

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                applyCnt = ls.getInt("apply_cnt");
            }

            isAlready = (applyCnt > 0) ? true : false;

            ls.close();
            ls = null;
            //            pstmt.close();
            //            pstmt = null;

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                } catch (Exception e) {
            //                }
            //            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return isAlready;
    }

    /**
     * 월 3과정 이상 학습 혹은 수강신청이 가능하다. 이 과정수 초과 여부를 확인한다.
     * 
     * @param connMgr
     * @param box
     * @return
     * @throws Exception
     */
    public boolean isMonthlyLimitOver(DBConnectionManager connMgr, RequestBox box) throws Exception {
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        // int index = 1;
        int applyCnt = 0;
        int availableApplyCnt = 0; // 그룹코드별 수강 가능한 과정 수
        String grcode = box.getSession("grcode");
        String userid = box.getSession("userid");

        boolean isLimitOver = false;

        try {

            if (grcode.equals("N000001")) { // B2C
                availableApplyCnt = 5;
            } else if (grcode.equals("N000022")) { // 문화체육관광부
                availableApplyCnt = 5;
            } else if (grcode.equals("N000083")) { // 한국문화콘텐츠고등학교
                availableApplyCnt = 3;
            } else {
                availableApplyCnt = 2; // 그 외 제한 걸린 모든 ASP 사이트
            }

            sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() isMonthlyLimitOver (수강신청건수 조회) */  \n");
            sql.append("SELECT  COUNT(D.USERID) AS APPLY_CNT                                            \n");
            sql.append("  FROM  (                                                                       \n");
            sql.append("        SELECT                                                                  \n");
            sql.append("                LAG(A.GYEAR) OVER(ORDER BY A.GYEAR, A.GRSEQ) PREV_GYEAR         \n");
            sql.append("            ,   LAG(A.GRSEQ) OVER(ORDER BY A.GYEAR, A.GRSEQ) PREV_GRSEQ         \n");
            sql.append("            ,   A.GRCODE                                                        \n");
            sql.append("            ,   A.GYEAR                                                         \n");
            sql.append("            ,   A.GRSEQ                                                         \n");
            sql.append("            ,   A.GRSEQNM                                                       \n");
            sql.append("            ,   B.PROPSTART                                                     \n");
            sql.append("            ,   B.PROPEND                                                       \n");
            sql.append("            ,   LEAD(A.GYEAR) OVER(ORDER BY A.GYEAR, A.GRSEQ) NEXT_GYEAR        \n");
            sql.append("            ,   LEAD(A.GRSEQ) OVER(ORDER BY A.GYEAR, A.GRSEQ) NEXT_GRSEQ        \n");
            sql.append("          FROM  TZ_GRSEQ A                                                      \n");
            sql.append("            ,   (                                                               \n");
            sql.append("                SELECT  GRCODE                                                  \n");
            sql.append("                    ,   GYEAR                                                   \n");
            sql.append("                    ,   GRSEQ                                                   \n");
            sql.append("                    ,   PROPSTART                                               \n");
            sql.append("                    ,   PROPEND                                                 \n");
            sql.append("                  FROM  TZ_SUBJSEQ                                              \n");
            sql.append("                 WHERE  GRCODE = '").append(grcode).append("'                   \n");
            sql.append("                 GROUP BY GRCODE, GYEAR, GRSEQ, PROPSTART, PROPEND              \n");
            sql.append("                ) B                                                             \n");
            sql.append("         WHERE  A.GRCODE = '").append(grcode).append("'                         \n");
            sql.append("           AND  A.HOMEPAGEYN = 'Y'                                              \n");
            sql.append("           AND  A.STAT = 'Y'                                                    \n");
            sql.append("           AND  A.GYEAR = B.GYEAR                                               \n");
            sql.append("           AND  A.GRCODE = B.GRCODE                                             \n");
            sql.append("           AND  A.GRSEQ = B.GRSEQ                                               \n");
            sql.append("         ORDER  BY A.GYEAR, A.GRSEQ                                             \n");
            sql.append("        ) V1                                                                    \n");
            sql.append("    ,   TZ_SUBJSEQ C                                                            \n");
            sql.append("    ,   TZ_PROPOSE D                                                            \n");
            sql.append(" WHERE  TO_CHAR(SYSDATE, 'YYYYMMDDHH24') BETWEEN V1.PROPSTART AND V1.PROPEND    \n");

            if (grcode.equals("N000001")) {
                // B2C의 경우 2014년 7월을 기점으로 월 2차수 교육 운영 시작
                // 학습자는 이전 차수와 현재 차수를 합하여 3개까지만 신청 가능
                // sql.append("   AND  ( ( V1.PREV_GYEAR = C.GYEAR AND  V1.PREV_GRSEQ = C.GRSEQ)   \n");
                // sql.append("        OR ( V1.GYEAR = C.GYEAR AND  V1.GRSEQ = C.GRSEQ) )          \n");
                sql.append("   AND  V1.PROPSTART = C.PROPSTART  \n");
                sql.append("   AND  V1.PROPEND = C.PROPEND      \n");
            } else {
                // B2B는 이전 운영하던 방식으로 진행
                sql.append("   AND  V1.GYEAR = C.GYEAR AND  V1.GRSEQ = C.GRSEQ  \n");
            }
            sql.append("   AND  C.YEAR = D.YEAR                             \n");
            sql.append("   AND  C.SUBJ = D.SUBJ                             \n");
            sql.append("   AND  C.SUBJSEQ = D.SUBJSEQ                       \n");
            sql.append("   AND  D.USERID = '").append(userid).append("'     \n");
            sql.append("   AND  (D.CANCELKIND IS NULL OR D.CANCELKIND = '') \n");

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //
            //            pstmt.setString(index++, grcode);
            //            pstmt.setString(index++, grcode);
            //            pstmt.setString(index++, userid);
            //
            //            ls = new ListSet(pstmt);

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                applyCnt = ls.getInt("apply_cnt");
            }

            isLimitOver = (applyCnt >= availableApplyCnt) ? true : false;

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            //            if (pstmt != null) {
            //                try {
            //                    pstmt.close();
            //                } catch (Exception e) {
            //                }
            //            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return isLimitOver;
    }

    /**
     * 수강신청 정보를 등록한다.
     * 
     * @param connMgr
     * @param box
     * @return
     * @throws Exception
     */
    public int registerApplyInfo(DBConnectionManager connMgr, RequestBox box) throws Exception {
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        // int index = 1;
        int resultCnt = 0;

        Date today = new Date();
        SimpleDateFormat d1 = new SimpleDateFormat("yyyyMMddHHmmss");
        String now = d1.format(today);

        String grcode = box.getSession("grcode");
        String userid = box.getSession("userid");
        String year = box.getString("year");
        String subj = box.getString("subj");
        String subjseq = box.getString("subjseq");

        String tid = box.getSession("userid") + "_" + now + System.currentTimeMillis();

        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        Date dt = new Date();
        
        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);
        cal.add(Calendar.MONTH,1);
        cal.add(Calendar.DATE,-1);
        String eduend = df.format(cal.getTime());
        
        try {
            connMgr.setAutoCommit(false);

            sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() registerApplyInfo (수강신청 정보 등록 - TZ_PROPOSE ) */      \n");
            sql.append("INSERT  INTO    TZ_PROPOSE  (                                   \n");
            sql.append("        SUBJ                                                    \n");
            sql.append("    ,   YEAR                                                    \n");
            sql.append("    ,   SUBJSEQ                                                 \n");
            sql.append("    ,   USERID                                                  \n");
            sql.append("    ,   APPDATE                                                 \n");
            sql.append("    ,   CHKFIRST                                                \n");
            sql.append("    ,   CHKFINAL                                                \n");
            sql.append("    ,   LUSERID                                                 \n");
            sql.append("    ,   LDATE                                                   \n");
            sql.append("    ,   TID                                                     \n");
            sql.append("    ,   ASP_GUBUN                                               \n");
            sql.append(") VALUES (                                                      \n");
            sql.append("        '").append(subj).append("'                              \n");
            sql.append("    ,   '").append(year).append("'                              \n");
            sql.append("    ,   '").append(subjseq).append("'                           \n");
            sql.append("    ,   '").append(userid).append("'                            \n");
            sql.append("    ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')                     \n");
            sql.append("    ,   'Y'                                                     \n");
            sql.append("    ,   (                                                       \n");
            sql.append("        SELECT  DECODE(NVL(TRIM(AUTOCONFIRM), 'N'), 'Y', 'Y', 'B')    \n");
            sql.append("          FROM  TZ_SUBJSEQ                                      \n");
            sql.append("         WHERE  SUBJ = '").append(subj).append("'               \n");
            sql.append("           AND  YEAR = '").append(year).append("'               \n");
            sql.append("           AND  SUBJSEQ = '").append(subjseq).append("'         \n");
            sql.append("        )                                                       \n");
            sql.append("    ,   '").append(userid).append("'                            \n");
            sql.append("    ,   TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS')                     \n");
            sql.append("    ,   '").append(tid).append("'                               \n");
            sql.append("    ,   '").append(grcode).append("'                            \n");
            sql.append(")                                                               \n");
            
            resultCnt = connMgr.executeUpdate(sql.toString());

//            pstmt = connMgr.prepareStatement(sql.toString());
//
//            pstmt.setString(index++, box.getString("subj"));
//            pstmt.setString(index++, box.getString("year"));
//            pstmt.setString(index++, box.getString("subjseq"));
//            pstmt.setString(index++, box.getSession("userid"));
//            pstmt.setString(index++, box.getString("subj"));
//            pstmt.setString(index++, box.getString("year"));
//            pstmt.setString(index++, box.getString("subjseq"));
//            pstmt.setString(index++, box.getSession("userid"));
//            pstmt.setString(index++, tid);
//            pstmt.setString(index++, box.getSession("grcode"));
//
//            resultCnt = pstmt.executeUpdate();
//
//            pstmt.close();
//            pstmt = null;

            if (resultCnt == 1) {
                // index = 1;
                sql.setLength(0);
                sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() registerApplyInfo (수강신청 정보 등록 - TZ_STUDENT ) */");
                sql.append("INSERT  INTO    TZ_STUDENT  (                   \n");
                sql.append("        SUBJ                                    \n");
                sql.append("    ,   YEAR                                    \n");
                sql.append("    ,   SUBJSEQ                                 \n");
                sql.append("    ,   USERID                                  \n");
                sql.append("    ,   CLASS                                   \n");
                sql.append("    ,   COMP                                    \n");
                sql.append("    ,   ISDINSERT                               \n");
                sql.append("    ,   SCORE                                   \n");
                sql.append("    ,   TSTEP                                   \n");
                sql.append("    ,   MTEST                                   \n");
                sql.append("    ,   FTEST                                   \n");
                sql.append("    ,   REPORT                                  \n");
                sql.append("    ,   ACT                                     \n");
                sql.append("    ,   ETC1                                    \n");
                sql.append("    ,   ETC2                                    \n");
                sql.append("    ,   AVTSTEP                                 \n");
                sql.append("    ,   AVMTEST                                 \n");
                sql.append("    ,   AVFTEST                                 \n");
                sql.append("    ,   AVREPORT                                \n");
                sql.append("    ,   AVACT                                   \n");
                sql.append("    ,   AVETC1                                  \n");
                sql.append("    ,   AVETC2                                  \n");
                sql.append("    ,   ISGRADUATED                             \n");
                sql.append("    ,   ISRESTUDY                               \n");
                sql.append("    ,   ISB2C                                   \n");
                sql.append("    ,   EDUSTART                                \n");
                sql.append("    ,   EDUEND                                  \n");
                sql.append("    ,   BRANCH                                  \n");
                sql.append("    ,   CONFIRMDATE                             \n");
                sql.append("    ,   EDUNO                                   \n");
                sql.append("    ,   LUSERID                                 \n");
                sql.append("    ,   LDATE                                   \n");
                sql.append("    ,   STUSTATUS                               \n");
                sql.append(") VALUES (                                      \n");
                sql.append("        '").append(subj).append("'              \n");
                sql.append("    ,   '").append(year).append("'              \n");
                sql.append("    ,   '").append(subjseq).append("'           \n");
                sql.append("    ,   '").append(userid).append("'            \n");
                sql.append("    ,   ''                                      \n");
                sql.append("    ,   '").append(grcode).append("'            \n");
                sql.append("    ,   'Y'                                     \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   'N'                                     \n");
                sql.append("    ,   'N'                                     \n");
                sql.append("    ,   'N'                                     \n");
                sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDD')    \n");
                sql.append("    ,   '").append(eduend).append("'     \n");
                sql.append("    ,   99                                      \n");
                sql.append("    ,   ''                                      \n");
                sql.append("    ,   0                                       \n");
                sql.append("    ,   '").append(userid).append("'            \n");
                sql.append("    ,   TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')    \n");
                sql.append("    ,   'Y'                                     \n");
                sql.append(")                                               \n");
                
                resultCnt += connMgr.executeUpdate(sql.toString());

//                pstmt = connMgr.prepareStatement(sql.toString());
//
//                pstmt.setString(index++, box.getString("subj"));
//                pstmt.setString(index++, box.getString("year"));
//                pstmt.setString(index++, box.getString("subjseq"));
//                pstmt.setString(index++, box.getSession("userid"));
//                pstmt.setString(index++, box.getSession("grcode"));
//                pstmt.setString(index++, box.getSession("userid"));
//
//                resultCnt += pstmt.executeUpdate();
                ProposeCourseBean bean = new ProposeCourseBean();
                SmsBean smsBean = new SmsBean();
                // 발신번호 properites 에서 가지고 오기
                ConfigSet conf = new ConfigSet();
                
                String p_toPhone = bean.getNumber(box); //"010-3613-1539";
                String p_fromPhone = box.getStringDefault("from", conf.getProperty("sms.admin.comptel"));
                String p_msg = "학습이 시작되었습니다. 유익한 시간되시길 바랍니다.";
                boolean result = smsBean.sendSMSMsg(p_toPhone, p_fromPhone, p_msg, "");
                
            }

            if (resultCnt == 2) { // 2개의 테이블에 갱신하므로 2가 나와야 함.
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
//            if (pstmt != null) {
//                try {
//                    pstmt.close();
//                } catch (Exception e) {
//                }
//            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        return resultCnt;
    }

    /**
     * 수강신청 정보를 삭제한다.
     * 
     * @param connMgr
     * @param box
     * @return
     * @throws Exception
     */
    public String deleteApplyInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();

        // int index = 1;
        int resultCnt = 0;
        String resultMsg = "";

        String subj = box.getString("subj");
        String year = box.getString("year");
        String subjseq = box.getString("subjseq");
        String userid = box.getSession("userid");
        String grcode = box.getSession("grcode");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() deleteApplyInfo (수강신청 정보 삭제 - TZ_PROPOSE ) */      \n");
            sql.append("DELETE                  \n");
            sql.append("  FROM  TZ_PROPOSE      \n");
            sql.append(" WHERE  SUBJ = '").append(subj).append("'   \n");
            sql.append("   AND  YEAR = '").append(year).append("'\n");
            sql.append("   AND  SUBJSEQ = '").append(subjseq).append("'\n");
            sql.append("   AND  USERID = '").append(userid).append("'\n");
            sql.append("   AND  ASP_GUBUN = '").append(grcode).append("'\n");

            resultCnt = connMgr.executeUpdate(sql.toString());

            // pstmt = connMgr.prepareStatement(sql.toString());

            // pstmt.setString(index++, box.getString("subj"));
            // pstmt.setString(index++, box.getString("year"));
            // pstmt.setString(index++, box.getString("subjseq"));
            // pstmt.setString(index++, box.getSession("userid"));
            // pstmt.setString(index++, box.getSession("grcode"));

            // resultCnt = pstmt.executeUpdate();

            // pstmt.close();
            // pstmt = null;

            if (resultCnt == 1) {
                // index = 1;
                sql.setLength(0);
                sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() deleteApplyInfo (수강신청 정보 삭제 - TZ_STUDENT ) */    \n");
                sql.append("DELETE              \n");
                sql.append("  FROM  TZ_STUDENT  \n");
                sql.append(" WHERE  SUBJ = '").append(subj).append("'   \n");
                sql.append("   AND  YEAR = '").append(year).append("'\n");
                sql.append("   AND  SUBJSEQ = '").append(subjseq).append("'\n");
                sql.append("   AND  USERID = '").append(userid).append("'\n");
                sql.append("   AND  COMP = '").append(grcode).append("'\n");

                resultCnt += connMgr.executeUpdate(sql.toString());

                // pstmt = connMgr.prepareStatement(sql.toString());

                // pstmt.setString(index++, box.getString("subj"));
                // pstmt.setString(index++, box.getString("year"));
                // pstmt.setString(index++, box.getString("subjseq"));
                // pstmt.setString(index++, box.getSession("userid"));
                // pstmt.setString(index++, box.getSession("grcode"));

                // resultCnt += pstmt.executeUpdate();
            }

            if (resultCnt == 2) { // 2개의 테이블에 갱신하므로 2가 나와야 함.
                connMgr.commit();
                resultMsg = "cancel.apply.ok";
            } else {
                connMgr.rollback();
                resultMsg = "cancel.apply.fail";
            }
        } catch (Exception ex) {
            Log.err.println(box.getSession("userid"), sql.toString(), ex.getMessage());
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            // if (pstmt != null) {
            // try {
            // pstmt.close();
            // } catch (Exception e) {
            // }
            // }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e) {
                }
            }
        }

        return resultMsg;
    }

}
