//**********************************************************
//  1. 제	  목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개	  요:  관리
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: __누구__ 2009. 10. 19
//  7. 수	  정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.off;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import com.credu.Bill.BillBean;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class OffApprovalBean {

    public OffApprovalBean() {
    }

    /**
     * 등록여부 체크
     * 
     * @param box receive from the form object and session
     * @return String 1:insert success,0:insert fail
     */
    private boolean checkSubjcode(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq, String v_userid) throws Exception {
        boolean result = true;
        int cnt = 0;
        ListSet ls = null;
        String sql = "";
        try {
            sql = " select count(*) cnt ";
            sql += "   from TZ_OFFSTUDENT	 \n";
            sql += " where subj = " + StringManager.makeSQL(v_subj);
            sql += " and year = " + StringManager.makeSQL(v_year);
            sql += " and subjseq = " + StringManager.makeSQL(v_subjseq);
            //			sql+= " and seq = " + StringManager.makeSQL(v_seq);
            sql += " and userid = " + StringManager.makeSQL(v_userid);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                cnt = ls.getInt("cnt");
            }

            if (cnt == 0) {
                result = false;
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
     * 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int delete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();
        ;
        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();

            sql.append(" delete from TZ_OFFSTUDENT A	\n");
            sql.append("WHERE SUBJ = ':d_subj' \n");
            sql.append("AND YEAR = ?\n");
            sql.append("AND SUBJSEQ = ?\n");
            sql.append("AND SEQ = ?\n");
            sql.append("AND USERID = ?\n");

            pstmt = connMgr.prepareStatement(connMgr.replaceParam(sql.toString(), box, true));
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql + "\r\n");
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
        return isOk;
    }

    /**
     * 새로운 차수 등록 - 오프라인
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    @SuppressWarnings("unchecked")
    public int runAccept(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmtPropose = null;
        PreparedStatement pstmtStudentInsert = null;
        PreparedStatement pstmtSchedule = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            int i = 1;
            box.put("s_userid", box.getSession("userid"));
            String[] p_acceptResult = box.getStringArray("p_acceptResult");
            String[] p_subj = box.getStringArray("p_subj");
            String[] p_year = box.getStringArray("p_year");
            String[] p_subjseq = box.getStringArray("p_subjseq");
            String[] p_seq = box.getStringArray("p_seq");
            String[] p_userid = box.getStringArray("p_userid");

            sql.append("UPDATE TZ_OFFPROPOSE SET\n");
            sql.append("CHKFIRST = ?, CHKFINAL = ?\n");
            sql.append("WHERE SUBJ = ?\n");
            sql.append("AND YEAR = ?\n");
            sql.append("AND SUBJSEQ = ?\n");
            sql.append("AND SEQ = ?\n");
            sql.append("AND USERID = ?\n");
            pstmtPropose = connMgr.prepareStatement(sql.toString(), box);

            sql.delete(0, sql.length());
            sql.append("INSERT INTO TZ_OFFSTUDENT(\n");
            sql.append("		SUBJ,YEAR,SUBJSEQ,USERID,\n");
            sql.append("		ISDINSERT,ISGRADUATED,CONFIRMDATE,NOTGRADUETC,\n");
            sql.append("		STUSTATUS,SERNO,EDUSTART,EDUEND,ISGOYONG,DINSERTREASON,\n");
            sql.append("		STUDENTNO,\n");
            sql.append("		LUSERID,LDATE\n");
            sql.append(")\n");
            sql.append("SELECT\n");
            sql.append("	B.SUBJ, B.YEAR, B.SUBJSEQ, ? USERID, /* 1 : p_userid */\n");
            sql.append("	'N' ISDINSERT, '' ISGRADUATED, TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS') CONFIRMDATE, '' NOTGRADUETC,\n");
            sql.append("	'1' STUSTATUS, '' SERNO, B.EDUSTART, B.EDUEND, B.ISGOYONG, '정시입학' DINSERTREASON,\n");
            sql.append("	(B.SUBJ || lpad(B.SUBJSEQ, 2, '0') || lpad(SUBSTR(NVL(MAX( A.STUDENTNO ), '00000000000000000000' ), LENGTH(NVL(MAX( A.STUDENTNO ), '00000000000000000000' ))-1)+1, 2, '0')) STUDENTNO,\n");
            sql.append("	':s_userid' LUSERID, TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS') LDATE\n");
            sql.append("FROM TZ_OFFSTUDENT A, TZ_OFFSUBJSEQ B\n");
            sql.append("WHERE A.SUBJ(+) = B.SUBJ\n");
            sql.append("AND A.SUBJSEQ(+) = B.SUBJSEQ\n");
            sql.append("AND A.YEAR(+) = B.YEAR\n");
            sql.append("AND B.SUBJ = ?			/* 2 : p_subj */\n");
            sql.append("AND B.YEAR = ?			/* 4 : p_year */\n");
            sql.append("AND B.SUBJSEQ = ?	/* 3 : p_subjseq */\n");
            sql.append("AND B.SEQ = ?			/* 5 : p_seq */\n");
            sql.append("GROUP BY B.SUBJ, B.YEAR, B.SUBJSEQ, B.EDUSTART, B.EDUEND, B.ISGOYONG\n");
            pstmtStudentInsert = connMgr.prepareStatement(sql.toString(), box);

            sql.delete(0, sql.length());
            sql.append("INSERT INTO TZ_OFFSCHEDULE(\n");
            sql.append("		SEQ,SUBJ,YEAR,SUBJSEQ,USERID,STATE,CHANGEDATE,CHANGEREASON,LUSERID,LDATE\n");
            sql.append(")\n");
            sql.append("SELECT\n");
            sql.append("	(SELECT NVL(MAX(SEQ), 0) +1 FROM TZ_OFFSCHEDULE WHERE SUBJ = A.SUBJ AND YEAR = A.YEAR AND SUBJSEQ = A.SUBJSEQ AND USERID = A.USERID) SEQ, A.*\n");
            sql.append("FROM (SELECT\n");
            sql.append("		? SUBJ, ? YEAR, ? SUBJSEQ, ? USERID, '1' STATE,\n");
            sql.append("		TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS') CHANGEDATE, '입학' CHANGEREASON,\n");
            sql.append("		':s_userid' LUSERID, TO_CHAR(SYSDATE, 'YYYYMMDDHHMISS')LDATE\n");
            sql.append("		FROM DUAL) A\n");
            pstmtSchedule = connMgr.prepareStatement(sql.toString(), box);

            for (int j = 0; j < p_subj.length; j++) {
                pstmtPropose.setString(i++, String.valueOf(p_acceptResult[j].charAt(0)));
                pstmtPropose.setString(i++, String.valueOf(p_acceptResult[j].charAt(1)));
                pstmtPropose.setString(i++, p_subj[j]);
                pstmtPropose.setString(i++, p_year[j]);
                pstmtPropose.setString(i++, p_subjseq[j]);
                pstmtPropose.setString(i++, p_seq[j]);
                pstmtPropose.setString(i++, p_userid[j]);

                pstmtPropose.addBatch();
                if (p_acceptResult[j].equals("YY") && !checkSubjcode(connMgr, p_subj[j], p_year[j], p_subjseq[j], p_userid[j])) {
                    i = 1;
                    pstmtStudentInsert.setString(i++, p_userid[j]);
                    pstmtStudentInsert.setString(i++, p_subj[j]);
                    pstmtStudentInsert.setString(i++, p_year[j]);
                    pstmtStudentInsert.setString(i++, p_subjseq[j]);
                    pstmtStudentInsert.setString(i++, p_seq[j]);
                    pstmtStudentInsert.addBatch();

                    i = 1;
                    pstmtSchedule.setString(i++, p_subj[j]);
                    pstmtSchedule.setString(i++, p_year[j]);
                    pstmtSchedule.setString(i++, p_subjseq[j]);
                    pstmtSchedule.setString(i++, p_userid[j]);
                    pstmtSchedule.addBatch();
                }
                i = 1;
            }
            pstmtPropose.executeBatch();
            pstmtStudentInsert.executeBatch();
            pstmtSchedule.executeBatch();
            connMgr.commit();
            isOk = 1;
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, connMgr.replaceParam(sql.toString(), box, true));
            throw new Exception("sql = " + connMgr.replaceParam(sql.toString(), box, true) + "\r\n" + ex.getMessage());
        } finally {
            if (pstmtPropose != null) {
                try {
                    pstmtPropose.close();
                } catch (Exception e) {
                }
            }
            if (pstmtStudentInsert != null) {
                try {
                    pstmtStudentInsert.close();
                } catch (Exception e) {
                }
            }
            if (pstmtSchedule != null) {
                try {
                    pstmtSchedule.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
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
     * 설명 :
     * 
     * @param box
     * @return
     * @throws Exception
     * @author swchoi
     */
    @SuppressWarnings("unchecked")
    public List<DataBox> listPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        List<DataBox> result = null;
        String t = null;
        DataBox payMethod = BillBean.getPayMethod();

        try {
            // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

            connMgr = new DBConnectionManager();

            sql.append("/* 오프라인과정|신청승인관리 */\n");
            sql.append("SELECT  SUBSTR(D.RESNO,1,6) || '-' || SUBSTR(D.RESNO,7,14) AS RESNO \n");
            sql.append("    ,   D.RESNO1, D.RESNO2  \n");
            sql.append("    ,   DECODE(D.sex,'1','남','2','여','미등록 ') AS SEX \n");
            sql.append("    ,   crypto.dec('normal',D.HANDPHONE) AS HANDPHONE   \n");
            sql.append("    ,   D.GRCODE  , D.JIKCHAEKNM, D.DEPTNAM  \n");
            sql.append("    ,   A.SUBJ, A.YEAR, A.SUBJSEQ, A.SEQ, C.SUBJNM  \n");
            sql.append("    ,   DECODE(C.ISTERM, 'Y', F.STUDENTNO, '-') AS STUDENTNO    \n");
            sql.append("    ,   nvl(I.CANCELDATE,' ') AS CANCELDATE \n");
            sql.append("    ,   A.USERID, D.NAME, D.MEMBERGUBUN, E.CODENM MEMBERGUBUNNM \n");
            sql.append("    ,   A.APPLY_NAME, A.APPLY_BELONG_TITLE  \n");
            sql.append("    ,   '세션' || REPLACE(A.APPLY_SESSION, ',', ', 세션') AS APPLY_SESSION  \n");
            sql.append("    ,   A.NICKNAME, A.PRIVATE_SNS   \n");
            sql.append("    ,   nvl(A.CHKFIRST || A.CHKFINAL, 'UU') AS CHKSTATUS    \n");
            sql.append("    ,   A.CHKFIRST, A.CHKFINAL, A.APPDATE, G.CODENM AS CHKSTATUSNM  \n");
            sql.append("    ,   A.CANCELKIND, A.CANCELDATE, A.CANCELREASON, A.REFUNDDATE, A.COMPTEXT    \n");
            sql.append("    ,   (CASE WHEN NVL(A.CHKFIRST || A.CHKFINAL,'UU') = 'UU' THEN '미처리'   \n");
            sql.append("            WHEN NVL(A.CHKFIRST || A.CHKFINAL,'UU') = 'YU' THEN '1차승인'      \n");
            sql.append("            WHEN NVL(A.CHKFIRST || A.CHKFINAL,'UU') = 'YN' THEN '최종불합격' \n");
            sql.append("            WHEN NVL(A.CHKFIRST || A.CHKFINAL,'UU') = 'YY' THEN '최종승인'  \n");
            sql.append("            ELSE '미승인' END) AS CHK_NAME \n");
            sql.append("    ,   H.STUDENTLIMIT, NVL(H.STUDENTWAIT, 0) AS STUDENTWAIT    \n"); //총정원,대기자수
            sql.append("    ,   (CASE WHEN NVL(i.CANCELDATE,' ') != ' ' THEN '결제취소' \n");
            sql.append("            WHEN B.RESULTCODE = '00' THEN '결제완료'    \n");
            sql.append("            ELSE '결제대기' END) AS RESULTCODE  \n");
            sql.append("    ,   B.TID, B.PGAUTHDATE, B.PAYMETHOD, B.PRICE, B.PGAUTHTIME \n");
            sql.append("    ,   CRYPTO.DEC('normal',D.EMAIL) AS EMAIL   \n");
            sql.append("    ,   J.TCAREERYEAR, J.TCAREERMONTH, J.INTRO, J.MOTIVE    \n");
            sql.append("    ,   D.MEMBERYEAR || D.MEMBERMONTH || D.MEMBERDAY AS MEMBERREG   \n");
            sql.append("    ,   K.SAVEFILENM, K.REALFILENM \n");
            sql.append("  FROM  TZ_OFFPROPOSE A \n");
            sql.append("    ,   TZ_OFFBILLINFO B    \n");
            sql.append("    ,   TZ_OFFSUBJ C    \n");
            sql.append("    ,   TZ_MEMBER D     \n");
            sql.append("    ,   TZ_CODE E       \n");
            sql.append("    ,   TZ_OFFSTUDENT F \n");
            sql.append("    ,   TZ_CODE G       \n");
            sql.append("    ,   TZ_OFFSUBJSEQ H \n");
            sql.append("    ,   TZ_OFFCANCEL i  \n");
            sql.append("    ,   TZ_OFFPROPOSE_ADDINFO J \n");
            sql.append("    ,   TZ_OFFPROPOSEFILE K \n");
            sql.append(" WHERE  1=1 \n");
            sql.append("   AND  1 = DECODE(NVL(:s_subjcode, 'ALL'), A.SUBJ, 1, 'ALL', 1, 0)	\n");
            sql.append("   AND  1 = DECODE(NVL(:s_year, 'ALL'), A.YEAR, 1, 'ALL', 1, 0)	\n");
            sql.append("   AND  1 = DECODE(NVL(:s_subjseq, 'ALL'), A.SUBJSEQ, 1, 'ALL', 1, 0)	\n");
            sql.append("   AND  1 = DECODE(NVL(:s_upperclass, UPPERCLASS), UPPERCLASS, 1, 'ALL', 1, 0)	\n");
            sql.append("   AND  1 = DECODE(NVL(:s_middleclass, MIDDLECLASS), MIDDLECLASS, 1, 'ALL', 1, 0)	\n");
            sql.append("   AND  1 = DECODE(NVL(:s_lowerclass, LOWERCLASS), LOWERCLASS, 1, 'ALL', 1, 0)	\n");
            sql.append("   AND  C.SUBJNM LIKE '%' || nvl(:s_subjsearchkey, C.SUBJNM) || '%'\n");
            sql.append("   AND  A.TID = B.TID(+)\n");
            sql.append("   AND  A.SUBJ = C.SUBJ\n");
            sql.append("   AND  A.USERID = D.USERID\n");
            sql.append("   AND  D.MEMBERGUBUN = E.CODE\n");
            sql.append("   AND  E.GUBUN = '0029'\n");
            sql.append("   AND  G.GUBUN = '0098'\n");
            sql.append("   AND  G.CODE = nvl(A.CHKFIRST || A.CHKFINAL, 'UU')\n");
            sql.append("   AND  A.SUBJ = F.SUBJ(+)\n");
            sql.append("   AND  A.YEAR = F.YEAR(+)\n");
            sql.append("   AND  D.GRCODE = 'N000001'\n");
            sql.append("   AND  A.SUBJSEQ = F.SUBJSEQ(+)\n");
            sql.append("   AND  A.USERID = F.USERID(+)\n");
            sql.append("   AND  A.SUBJ = H.SUBJ(+)\n");
            sql.append("   AND  A.YEAR = H.YEAR(+)\n");
            sql.append("   AND  A.SUBJSEQ = H.SUBJSEQ(+)\n");
            sql.append("   AND  b.tid=i.tid(+)\n");
            sql.append("   AND  A.SUBJ = J.SUBJ \n");
            sql.append("   AND  A.SUBJSEQ = J.SUBJSEQ \n");
            sql.append("   AND  A.YEAR = J.YEAR \n");
            sql.append("   AND  A.USERID = J.USERID \n");
            sql.append("   AND  A.SUBJ = K.SUBJ(+) \n");
            sql.append("   AND  A.SUBJSEQ = K.SUBJSEQ(+) \n");
            sql.append("   AND  A.YEAR = K.YEAR(+) \n");
            sql.append("   AND  A.USERID = K.USERID(+) \n");
            sql.append("   AND  A.SEQ = K.SEQ(+) \n");
            if (box.getString("p_orderColumn").length() > 0) {
                box.put("p_orderType", box.getStringDefault("p_orderType", " asc"));
                sql.append("ORDER BY ");
                sql.append(box.getString("p_orderColumn"));
                sql.append(box.getString("p_orderType"));
            } else {
                sql.append(" order by a.APPDATE");
            }

            t = connMgr.replaceParam(sql.toString(), box);
            ls = connMgr.executeQuery(t);
            result = new ArrayList<DataBox>();
            while (ls.next()) {
                DataBox entity = ls.getDataBox();
                entity.put("d_paymethod", payMethod.get(entity.getString("d_paymethod")));

                //====================================================
                // 개인정보 복호화
                /*
                 * SeedCipher seed = new SeedCipher(); if
                 * (!entity.getString("d_resno2").equals(""))
                 * entity.put("d_resno2"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_resno2")), seed.key.getBytes(), "UTF-8")); if
                 * (!entity.getString("d_email").equals(""))
                 * entity.put("d_email"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_email")), seed.key.getBytes(), "UTF-8")); if
                 * (!entity.getString("d_hometel").equals(""))
                 * entity.put("d_hometel"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_hometel")), seed.key.getBytes(), "UTF-8")); if
                 * (!entity.getString("d_handphone").equals(""))
                 * entity.put("d_handphone"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_handphone")), seed.key.getBytes(), "UTF-8"));
                 * if (!entity.getString("d_addr2").equals(""))
                 * entity.put("d_addr2"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_addr2")), seed.key.getBytes(), "UTF-8"));
                 */

                /*
                 * if (!entity.getString("d_resno2").equals(""))
                 * entity.put("d_resno2",
                 * encryptUtil.decrypt(entity.getString("d_resno2"))); if
                 * (!entity.getString("d_email").equals(""))
                 * entity.put("d_email",
                 * encryptUtil.decrypt(entity.getString("d_email"))); if
                 * (!entity.getString("d_hometel").equals(""))
                 * entity.put("d_hometel",
                 * encryptUtil.decrypt(entity.getString("d_hometel"))); if
                 * (!entity.getString("d_handphone").equals(""))
                 * entity.put("d_handphone",
                 * encryptUtil.decrypt(entity.getString("d_handphone"))); if
                 * (!entity.getString("d_addr2").equals(""))
                 * entity.put("d_addr2",
                 * encryptUtil.decrypt(entity.getString("d_addr2")));
                 */
                //====================================================
                entity.put("d_resno", payMethod.get(entity.getString("d_resno1")) + "-" + payMethod.get(entity.getString("d_resno2")));

                result.add(entity);
            }
            //			result = ls.getAllDataList();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, t);
            throw new Exception("sql = " + t + "\r\n" + ex.getMessage());
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
     * 개인 수강신청 이력 정보
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public List<DataBox> listPageMember(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        List<DataBox> result = null;
        String t = null;
        DataBox payMethod = BillBean.getPayMethod();

        try {
            // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

            connMgr = new DBConnectionManager();

            sql.append("SELECT	/*	**오프라인과정|신청승인관리**	*/\n");
            sql.append("SUBSTR(D.RESNO,1,6) || '-' || SUBSTR(D.RESNO,7,14) AS RESNO , D.RESNO1, D.RESNO2, crypto.dec('normal',D.HANDPHONE) HADNPHONE ,D.GRCODE,/*주민번호,핸드폰*/\n");
            sql.append("A.SUBJ, A.YEAR, A.SUBJSEQ, A.SEQ, C.SUBJNM, DECODE(C.ISTERM, 'Y', F.STUDENTNO, '-') STUDENTNO, A.CANCELDATE,/*과정코드, 연도, 차수, 순번, 과정명, 학번, 취소일*/\n");
            sql.append("A.USERID, D.NAME, D.MEMBERGUBUN, E.CODENM MEMBERGUBUNNM,/*ID, 이름, 회원구분, 회원구분명*/\n");
            sql.append("nvl(A.CHKFIRST || A.CHKFINAL, 'UU') chkstatus, A.CHKFIRST, A.CHKFINAL, A.APPDATE, G.CODENM chkstatusnm,/*1차승인, 최종승인, 신청일*/\n");
            sql.append("A.CANCELKIND, A.CANCELDATE, A.CANCELREASON, A.REFUNDDATE, A.COMPTEXT, /*	취소구분, 취소신청일, 취소사유, 환불일, 회사명	*/\n");
            sql.append("B.TID, DECODE(B.RESULTCODE, '00', '결제완료', '99', '결제대기', B.RESULTCODE) RESULTCODE, B.PGAUTHDATE, B.PAYMETHOD, B.PRICE, B.PGAUTHTIME/*결제번호, 결제상태, 결제일, 결제방법, 결제금액, 결제시간*/\n");
            sql.append("FROM TZ_OFFPROPOSE A, TZ_OFFBILLINFO B, TZ_OFFSUBJ C, TZ_MEMBER D, TZ_CODE E, TZ_OFFSTUDENT F, TZ_CODE G\n");
            sql.append("WHERE 1=1\n");
            sql.append("AND A.TID = B.TID(+)\n");
            sql.append("AND A.SUBJ = C.SUBJ\n");
            sql.append("AND A.USERID = D.USERID\n");
            sql.append("AND D.MEMBERGUBUN = E.CODE\n");
            sql.append("AND E.GUBUN = '0029'\n");
            sql.append("AND G.GUBUN = '0098'\n");
            sql.append("AND G.CODE = nvl(A.CHKFIRST || A.CHKFINAL, 'UU')\n");
            sql.append("AND A.SUBJ = F.SUBJ(+)\n");
            sql.append("AND A.YEAR = F.YEAR(+)\n");
            sql.append("AND D.GRCODE = 'N000001'\n");
            sql.append("AND A.SUBJSEQ = F.SUBJSEQ(+)\n");
            sql.append("AND A.USERID = F.USERID(+)\n");
            sql.append("AND A.USERID = :p_userid\n");
            sql.append("ORDER BY YEAR DESC,SUBJ,SUBJSEQ");

            t = connMgr.replaceParam(sql.toString(), box);
            ls = connMgr.executeQuery(t);
            result = new ArrayList<DataBox>();
            while (ls.next()) {
                DataBox entity = ls.getDataBox();
                entity.put("d_paymethod", payMethod.get(entity.getString("d_paymethod")));

                //====================================================
                // 개인정보 복호화
                /*
                 * SeedCipher seed = new SeedCipher(); if
                 * (!entity.getString("d_resno2").equals(""))
                 * entity.put("d_resno2"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_resno2")), seed.key.getBytes(), "UTF-8")); if
                 * (!entity.getString("d_email").equals(""))
                 * entity.put("d_email"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_email")), seed.key.getBytes(), "UTF-8")); if
                 * (!entity.getString("d_hometel").equals(""))
                 * entity.put("d_hometel"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_hometel")), seed.key.getBytes(), "UTF-8")); if
                 * (!entity.getString("d_handphone").equals(""))
                 * entity.put("d_handphone"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_handphone")), seed.key.getBytes(), "UTF-8"));
                 * if (!entity.getString("d_addr2").equals(""))
                 * entity.put("d_addr2"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_addr2")), seed.key.getBytes(), "UTF-8"));
                 */

                /*
                 * if (!entity.getString("d_resno2").equals(""))
                 * entity.put("d_resno2",
                 * encryptUtil.decrypt(entity.getString("d_resno2"))); if
                 * (!entity.getString("d_email").equals(""))
                 * entity.put("d_email",
                 * encryptUtil.decrypt(entity.getString("d_email"))); if
                 * (!entity.getString("d_hometel").equals(""))
                 * entity.put("d_hometel",
                 * encryptUtil.decrypt(entity.getString("d_hometel"))); if
                 * (!entity.getString("d_handphone").equals(""))
                 * entity.put("d_handphone",
                 * encryptUtil.decrypt(entity.getString("d_handphone"))); if
                 * (!entity.getString("d_addr2").equals(""))
                 * entity.put("d_addr2",
                 * encryptUtil.decrypt(entity.getString("d_addr2")));
                 */
                //====================================================
                entity.put("d_resno", payMethod.get(entity.getString("d_resno1")) + "-" + payMethod.get(entity.getString("d_resno2")));

                result.add(entity);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, t);
            throw new Exception("sql = " + t + "\r\n" + ex.getMessage());
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
     * 해당연도 전체인원 엑셀
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public List<DataBox> listAllPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        List<DataBox> result = null;
        String t = null;
        DataBox payMethod = BillBean.getPayMethod();

        try {
            // EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

            connMgr = new DBConnectionManager();
            box.sync("s_year");
            sql.append("SELECT	/*	**오프라인과정|신청승인관리**	*/\n");
            sql.append("SUBSTR(D.RESNO,1,6) || '-' || SUBSTR(D.RESNO,7,14) AS RESNO , D.RESNO1, D.RESNO2,DECODE(D.sex,'1','남','2','여','미등록 ') SEX, crypto.dec('normal',D.HANDPHONE) HANDPHONE,/*주민번호,핸드폰*/\n");
            sql.append("A.SUBJ, A.YEAR, A.SUBJSEQ, A.SEQ, C.SUBJNM, DECODE(C.ISTERM, 'Y', F.STUDENTNO, '-') STUDENTNO, A.CANCELDATE,/*과정코드, 연도, 차수, 순번, 과정명, 학번, 취소일*/\n");
            sql.append("A.USERID, D.NAME, D.MEMBERGUBUN, E.CODENM MEMBERGUBUNNM, D.JIKCHAEKNM, D.DEPTNAM, /*ID, 이름, 회원구분, 회원구분명,직책명, 소속부서 */\n");
            sql.append("nvl(A.CHKFIRST || A.CHKFINAL, 'UU') chkstatus, A.CHKFIRST, A.CHKFINAL, A.APPDATE, G.CODENM chkstatusnm,/*1차승인, 최종승인, 신청일*/\n");
            sql.append("A.CANCELKIND, A.CANCELDATE, A.CANCELREASON, A.REFUNDDATE, A.COMPTEXT, /*	취소구분, 취소신청일, 취소사유, 환불일, 회사명	*/\n");
            sql.append("(case when nvl(A.CHKFIRST || A.CHKFINAL,'UU')='UU' then '미처리' when nvl(A.CHKFIRST || A.CHKFINAL,'UU')='YU' then '1차승인' when nvl(A.CHKFIRST || A.CHKFINAL,'UU')='YN' then '최종불합격' when nvl(A.CHKFIRST || A.CHKFINAL,'UU')='YY' then '최종승인' else '미승인' end) as chk_name,");
            sql.append("B.TID, DECODE(B.RESULTCODE, '00', '결제완료', '99', '결제대기', B.RESULTCODE) RESULTCODE, B.PGAUTHDATE, B.PAYMETHOD, B.PRICE, B.PGAUTHTIME, crypto.dec('normal',D.EMAIL) EMAIL/*결제번호, 결제상태, 결제일, 결제방법, 결제금액, 결제시간,e-mail*/\n");
            sql.append("FROM TZ_OFFPROPOSE A, TZ_OFFBILLINFO B, TZ_OFFSUBJ C, TZ_MEMBER D, TZ_CODE E, TZ_OFFSTUDENT F, TZ_CODE G\n");
            sql.append("WHERE 1=1\n");
            sql.append("AND 1 = DECODE(NVL(:s_upperclass, UPPERCLASS), UPPERCLASS, 1, 'ALL', 1, 0)	\n");
            sql.append("AND 1 = DECODE(NVL(:s_middleclass, MIDDLECLASS), MIDDLECLASS, 1, 'ALL', 1, 0)	\n");
            sql.append("AND 1 = DECODE(NVL(:s_lowerclass, LOWERCLASS), LOWERCLASS, 1, 'ALL', 1, 0)	\n");
            sql.append("AND 1 = DECODE(NVL(:s_year, 'ALL'), A.YEAR, 1, 'ALL', 1, 0)	\n");
            sql.append("AND C.SUBJNM LIKE '%' || nvl(:s_subjsearchkey, C.SUBJNM) || '%'\n");
            sql.append("AND A.TID = B.TID(+)\n");
            sql.append("AND A.SUBJ = C.SUBJ\n");
            sql.append("AND A.USERID = D.USERID\n");
            sql.append("AND D.MEMBERGUBUN = E.CODE\n");
            sql.append("AND E.GUBUN = '0029'\n");
            sql.append("AND G.GUBUN = '0098'\n");
            sql.append("AND G.CODE = nvl(A.CHKFIRST || A.CHKFINAL, 'UU')\n");
            sql.append("AND A.SUBJ = F.SUBJ(+)\n");
            sql.append("AND A.YEAR = F.YEAR(+)\n");
            sql.append("AND D.GRCODE = 'N000001'\n");
            sql.append("AND A.SUBJSEQ = F.SUBJSEQ(+)\n");
            sql.append("AND A.USERID = F.USERID(+)\n");
            sql.append("AND A.YEAR = " + StringManager.makeSQL(box.get("s_year")) + " \n");
            sql.append("ORDER BY YEAR,SUBJ,SUBJSEQ");

            t = connMgr.replaceParam(sql.toString(), box);
            ls = connMgr.executeQuery(t);
            result = new ArrayList<DataBox>();

            while (ls.next()) {
                DataBox entity = ls.getDataBox();
                entity.put("d_paymethod", payMethod.get(entity.getString("d_paymethod")));

                //====================================================
                // 개인정보 복호화
                /*
                 * SeedCipher seed = new SeedCipher(); if
                 * (!entity.getString("d_resno2").equals(""))
                 * entity.put("d_resno2"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_resno2")), seed.key.getBytes(), "UTF-8")); if
                 * (!entity.getString("d_email").equals(""))
                 * entity.put("d_email"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_email")), seed.key.getBytes(), "UTF-8")); if
                 * (!entity.getString("d_hometel").equals(""))
                 * entity.put("d_hometel"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_hometel")), seed.key.getBytes(), "UTF-8")); if
                 * (!entity.getString("d_handphone").equals(""))
                 * entity.put("d_handphone"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_handphone")), seed.key.getBytes(), "UTF-8"));
                 * if (!entity.getString("d_addr2").equals(""))
                 * entity.put("d_addr2"
                 * ,seed.decryptAsString(Base64.decode(entity
                 * .getString("d_addr2")), seed.key.getBytes(), "UTF-8"));
                 */

                /*
                 * if (!entity.getString("d_resno2").equals(""))
                 * entity.put("d_resno2",
                 * encryptUtil.decrypt(entity.getString("d_resno2"))); if
                 * (!entity.getString("d_email").equals(""))
                 * entity.put("d_email",
                 * encryptUtil.decrypt(entity.getString("d_email"))); if
                 * (!entity.getString("d_hometel").equals(""))
                 * entity.put("d_hometel",
                 * encryptUtil.decrypt(entity.getString("d_hometel"))); if
                 * (!entity.getString("d_handphone").equals(""))
                 * entity.put("d_handphone",
                 * encryptUtil.decrypt(entity.getString("d_handphone"))); if
                 * (!entity.getString("d_addr2").equals(""))
                 * entity.put("d_addr2",
                 * encryptUtil.decrypt(entity.getString("d_addr2")));
                 */
                //====================================================
                entity.put("d_resno", payMethod.get(entity.getString("d_resno1")) + "-" + payMethod.get(entity.getString("d_resno2")));

                result.add(entity);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, t);
            throw new Exception("sql = " + t + "\r\n" + ex.getMessage());
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
     * 개인 수강신청 부가 정보
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public DataBox ProposeInfo(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;

        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        DataBox dbox = null;

        try {

            connMgr = new DBConnectionManager();

            sql.append(" SELECT INTRO, VISION, ETC, MOTIVE \n");
            sql.append("   FROM TZ_OFFPROPOSE_ADDINFO \n");
            sql.append("  WHERE USERID = " + StringManager.makeSQL(box.getString("p_userid")) + " \n");
            sql.append("    AND SUBJ = " + StringManager.makeSQL(box.getString("p_subj")) + " \n");
            sql.append("    AND SUBJSEQ = " + StringManager.makeSQL(box.getString("p_subjseq")) + " \n");
            sql.append("    AND YEAR = " + StringManager.makeSQL(box.getString("p_YEAR")) + " \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
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

        return dbox;
    }

    /**
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public List<DataBox> acceptResultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        List<DataBox> result = null;
        String t = null;

        try {
            connMgr = new DBConnectionManager();
            // sql.append("SELECT CODE value, CODENM name FROM TZ_CODE WHERE GUBUN = '0098'");
            sql.append("SELECT CODE value, CODENM name FROM TZ_CODE WHERE GUBUN = '0102'");
            ls = connMgr.executeQuery(sql.toString());
            result = ls.getAllDataList();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, t);
            throw new Exception("sql = " + connMgr.replaceParam(sql, box) + "\r\n" + ex.getMessage());
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

    public DataBox selectNeedInput(String subj, String year, String subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        StringBuilder sql = new StringBuilder();
        ListSet ls = null;
        DataBox result = null;
        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  NEEDINPUT   \n");
            sql.append("  FROM  TZ_OFFSUBJSEQ   \n");
            sql.append(" WHERE  SUBJ = '").append(subj).append("' \n");
            sql.append("   AND  YEAR = '").append(year).append("' \n");
            sql.append("   AND  SUBJSEQ = '").append(subjseq).append("' \n");
            ls = connMgr.executeQuery(sql.toString());
            
            if ( ls.next() ) {
                result = ls.getDataBox();
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
        return result;

    }
}
