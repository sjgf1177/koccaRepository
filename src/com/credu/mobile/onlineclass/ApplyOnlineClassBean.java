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
     * ���԰��� �з��� ����� ��ȸ�Ѵ�.
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

            if (grcode.equals("N000001")) { // ������û�� ������ ��� ������û ���� ���
                
                resultCnt = registerApplyInfo(connMgr, box);

                if (resultCnt > 0) {
                    resultMsg = "apply.ok"; // ������û �Ϸ�

                } else {
                    resultMsg = "apply.fail"; // ������û ����
                }
                
            } else {
                
                isPossilbeByDate = this.isPossibleSubjApplyByDate(connMgr, box); // ������û ���� ���� Ȯ��
    
                if (isPossilbeByDate) { // ������û ������ ������ ���
    
                    isPossibleByStudentLimit = this.isPossibleSubjApplyByStudentLimit(connMgr, box); // ������û �ο� �� ���� Ȯ��
    
                    if (isPossibleByStudentLimit) { // ������û �ο��� ���� �������� ���� ���
    
                        isAlreayApply = this.isAlreadyApply(connMgr, box); // ������û ���� Ȯ��
    
                        if (!isAlreayApply) { // ���� ������û�� ���� ���� ���
    
                            for (int i = 0; i < limitedGrcodeArr.length; i++) {
                                if (limitedGrcodeArr[i].indexOf(grcode) > -1) {
                                    isGrcodeLimited = true;
                                    break;
                                }
                            }
    
                            if (isGrcodeLimited) { // ���� �׷��� ���� ������û ���� �Ǽ��� �ִ� �׷��� ���
    
                                isMonthlyLimitOver = this.isMonthlyLimitOver(connMgr, box); // ������� ���� ������ �н��������� ���� �Ǽ��� ���� ������ ������û�� �Ǽ��� ���� ��ü �Ǽ� Ȯ�� 
    
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
     * �н��ڰ� ������û�� �� ��, �ش� ������ ������û ���ɿ��θ� Ȯ���Ѵ�. �н��ڰ� ������û â�� ��ð� ���� ���Ƶ� ������ �������
     * �ʾ� ������û�Ⱓ�� ���� �Ŀ��� ��û�� �Ǿ� �ִ� ��찡 �߻��ϰ� �ִ�. �̸� �̿��� �����ϰ��� ������û ���μ����� �����ϱ� ����
     * ���� ���ڷ� ������û ���θ� Ȯ���Ѵ�. ����� ����Ʈ ���� ���� �ð��� �������� ���� ������ ���δ�.
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

            sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() isPossibleSubjApplyByDate (������û ���� ���� Ȯ��) */ \n");
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
     * ���������� ������û�� �ο� ���� ���Ͽ� ������û �ο��� ������������ �ʰ��Ͽ������� ���Ѵ�.
     * 
     * @param connMgr
     * @param box
     * @return isPossibleByStudentLimited boolean true�̸� ���� �ʰ�, false�̸� ���� �̴�
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
            sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() isPossibleSubjApplyByStudentLimit (���� ����, ��û�ο� ��ȸ) */ \n");
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
     * �̹� ������û�� �Ǿ� �ִ� ���� �Ǵ��Ѵ�.
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
            sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() isAlreadyApply (������û ����) */ \n");
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
     * �� 3���� �̻� �н� Ȥ�� ������û�� �����ϴ�. �� ������ �ʰ� ���θ� Ȯ���Ѵ�.
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
        int availableApplyCnt = 0; // �׷��ڵ庰 ���� ������ ���� ��
        String grcode = box.getSession("grcode");
        String userid = box.getSession("userid");

        boolean isLimitOver = false;

        try {

            if (grcode.equals("N000001")) { // B2C
                availableApplyCnt = 5;
            } else if (grcode.equals("N000022")) { // ��ȭü��������
                availableApplyCnt = 5;
            } else if (grcode.equals("N000083")) { // �ѱ���ȭ����������б�
                availableApplyCnt = 3;
            } else {
                availableApplyCnt = 2; // �� �� ���� �ɸ� ��� ASP ����Ʈ
            }

            sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() isMonthlyLimitOver (������û�Ǽ� ��ȸ) */  \n");
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
                // B2C�� ��� 2014�� 7���� �������� �� 2���� ���� � ����
                // �н��ڴ� ���� ������ ���� ������ ���Ͽ� 3�������� ��û ����
                // sql.append("   AND  ( ( V1.PREV_GYEAR = C.GYEAR AND  V1.PREV_GRSEQ = C.GRSEQ)   \n");
                // sql.append("        OR ( V1.GYEAR = C.GYEAR AND  V1.GRSEQ = C.GRSEQ) )          \n");
                sql.append("   AND  V1.PROPSTART = C.PROPSTART  \n");
                sql.append("   AND  V1.PROPEND = C.PROPEND      \n");
            } else {
                // B2B�� ���� ��ϴ� ������� ����
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
     * ������û ������ ����Ѵ�.
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

            sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() registerApplyInfo (������û ���� ��� - TZ_PROPOSE ) */      \n");
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
                sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() registerApplyInfo (������û ���� ��� - TZ_STUDENT ) */");
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
                // �߽Ź�ȣ properites ���� ������ ����
                ConfigSet conf = new ConfigSet();
                
                String p_toPhone = bean.getNumber(box); //"010-3613-1539";
                String p_fromPhone = box.getStringDefault("from", conf.getProperty("sms.admin.comptel"));
                String p_msg = "�н��� ���۵Ǿ����ϴ�. ������ �ð��ǽñ� �ٶ��ϴ�.";
                boolean result = smsBean.sendSMSMsg(p_toPhone, p_fromPhone, p_msg, "");
                
            }

            if (resultCnt == 2) { // 2���� ���̺� �����ϹǷ� 2�� ���;� ��.
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
     * ������û ������ �����Ѵ�.
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

            sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() deleteApplyInfo (������û ���� ���� - TZ_PROPOSE ) */      \n");
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
                sql.append("/* com.credu.mobile.onlineclass.ApplyOnlineClassBean() deleteApplyInfo (������û ���� ���� - TZ_STUDENT ) */    \n");
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

            if (resultCnt == 2) { // 2���� ���̺� �����ϹǷ� 2�� ���;� ��.
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
