//**********************************************************
//  1. 제	  목:  관리
//  2. 프로그램명 : OffStudentManageBean.java
//  3. 개	  요:  관리
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: __누구__ 2009. 10. 19
//  7. 수	  정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.off;

import java.sql.PreparedStatement;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import com.credu.Bill.BillBean;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.Constants;
import com.dunet.common.util.EncryptUtil;

/**
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * 
 * @author Administrator
 */

public class OffStudentManageBean {

    public OffStudentManageBean() {
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
        int isOk = 0;

        Vector<?> s_checked = box.getVector("p_checks");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            for (int i = 0; i < s_checked.size(); i++) {
                sql.setLength(0);
                String tmp = (String) s_checked.elementAt(i);
                String[] tmp1 = tmp.split(",");

                String v_userid = tmp1[0];
                String v_subj = tmp1[1];
                String v_year = tmp1[2];
                String v_subjseq = tmp1[3];

                sql.append(" delete from TZ_OFFSTUDENT A	\n");
                sql.append("where subj = " + StringManager.makeSQL(v_subj));
                sql.append(" and year = " + StringManager.makeSQL(v_year));
                sql.append(" and subjseq = " + StringManager.makeSQL(v_subjseq));
                sql.append(" and userid = " + StringManager.makeSQL(v_userid));

                pstmt = connMgr.prepareStatement(sql.toString());
                isOk = pstmt.executeUpdate();
            }

            if (isOk > 0) {
                connMgr.commit();
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            isOk = 0;
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
     * 강제입과
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int dinsert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        int isOk = 0;
        int isOk1 = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        int isOk4 = 0;

        String v_oldyear = box.getString("s_year");
        String v_oldsubj = box.getString("s_subjcode");
        String v_oldsubjseq = box.getString("s_subjseq");

        String v_newyear = box.getString("p_newyear");
        String v_newsubj = box.getString("p_newsubj");
        String v_newsubjseq = box.getString("p_newsubjseq");

        String v_studentno = box.getString("p_checks");
        String v_userid = box.getString("p_newuserid");

        String v_dinsertreason = box.getString("p_dinsertreason");

        String s_userid = box.getString("userid");

        int v_seq = 0;
        String v_edustart = "";
        String v_eduend = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            /* 새로운 과정 정보 조회 */
            sql.append(" SELECT  COUNT(USERID) CNT                           \n");
            sql.append(" FROM    TZ_OFFSTUDENT                              \n");
            sql.append(" WHERE   SUBJ    = '").append(v_newsubj).append("' \n");
            sql.append(" AND     YEAR    = '").append(v_newyear).append("' \n");
            sql.append(" AND     SUBJSEQ = '").append(v_newsubjseq).append("' \n");
            sql.append(" AND     USERID  = '").append(v_userid).append("'");

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                if (ls.getInt("cnt") > 0) {
                    ls.close();
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
                    return -1;
                }
            }

            sql.setLength(0);
            ls.close();

            /* 강제 입과 내역 저장 */
            sql.append("select max(historyno) from TZ_OFFDINSERT_HISTORY");
            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                v_seq = ls.getInt(1) + 1;
            } else {
                v_seq = 1;
            }

            sql.setLength(0);
            ls.close();

            sql.append(" INSERT INTO TZ_OFFDINSERT_HISTORY            \n");
            sql.append(" (                                            \n");
            sql.append("     HISTORYNO, SUBJ, YEAR, SUBJSEQ, USERID   \n");
            sql.append("     , LUSERID, LDATE                         \n");
            sql.append(" )                                            \n");
            sql.append(" VALUES                                       \n");
            sql.append(" (                                            \n");
            sql.append("     ?, ?, ?, ?, ?, ?                         \n");
            sql.append("     , to_char(sysdate, 'YYYYMMDDHH24MISS')   \n");
            sql.append(" ) ");

            pstmt = connMgr.prepareStatement(sql.toString());

            int index = 1;
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, v_oldsubj);
            pstmt.setString(index++, v_oldyear);
            pstmt.setString(index++, v_oldsubjseq);
            pstmt.setString(index++, v_userid);
            pstmt.setString(index++, s_userid);

            isOk = pstmt.executeUpdate();

            sql.setLength(0);

            /* 새로운 과정 정보 조회 */
            sql.append(" SELECT  EDUSTART, EDUEND                           \n");
            sql.append(" FROM    TZ_OFFSUBJSEQ                              \n");
            sql.append(" WHERE   SUBJ    = '").append(v_newsubj).append("' \n");
            sql.append(" AND     YEAR    = '").append(v_newyear).append("' \n");
            sql.append(" AND     SUBJSEQ = '").append(v_newsubjseq).append("' \n");
            sql.append(" AND     ROWNUM  = 1");

            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                v_edustart = ls.getString(1);
                v_eduend = ls.getString(2);
            }

            sql.setLength(0);
            ls.close();

            /* 새로운 수강생 정보 저장 */
            sql.append(" SELECT (B.SUBJ || LPAD(B.SUBJSEQ, 2, '0')         \n");
            sql.append("        || LPAD(SUBSTR(NVL(MAX( A.STUDENTNO )      \n");
            sql.append("        , '00000000000000000000' )                 \n");
            sql.append("        , LENGTH(NVL(MAX( A.STUDENTNO )            \n");
            sql.append("        , '00000000000000000000' ))-1)+1, 2, '0')) \n");
            sql.append("        studentno                                  \n");
            sql.append(" FROM TZ_OFFSTUDENT A, TZ_OFFSUBJSEQ B             \n");
            sql.append(" WHERE A.SUBJ(+) = B.SUBJ                          \n");
            sql.append(" AND A.SUBJSEQ(+) = B.SUBJSEQ                      \n");
            sql.append(" AND A.YEAR(+) = B.YEAR                            \n");
            sql.append(" AND B.SUBJ = ").append(StringManager.makeSQL(v_newsubj)).append("\n");
            sql.append(" AND B.SUBJSEQ = ").append(StringManager.makeSQL(v_newsubjseq)).append("\n");
            sql.append(" GROUP BY B.SUBJ, B.SUBJSEQ");
            ls = connMgr.executeQuery(sql.toString());
            if (ls.next()) {
                v_studentno = ls.getString(1);
            }

            sql.setLength(0);

            sql.append(" INSERT INTO TZ_OFFSTUDENT                    \n");
            sql.append(" (                                            \n");
            sql.append("     SUBJ, YEAR, SUBJSEQ, USERID, ISDINSERT   \n");
            sql.append("     , DINSERTREASON, STUSTATUS, EDUSTART     \n");
            sql.append("     , EDUEND, STUDENTNO, HISTORYNO, LUSERID  \n");
            sql.append("     , CONFIRMDATE, LDATE                     \n");
            sql.append(" )                                            \n");
            sql.append(" VALUES                                       \n");
            sql.append(" (                                            \n");
            sql.append("     ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?       \n");
            sql.append("     , to_char(sysdate, 'YYYYMMDDHH24MISS')   \n");
            sql.append("     , to_char(sysdate, 'YYYYMMDDHH24MISS')   \n");
            sql.append(" ) ");

            pstmt = connMgr.prepareStatement(sql.toString());
            index = 1;
            pstmt.setString(index++, v_newsubj);
            pstmt.setString(index++, v_newyear);
            pstmt.setString(index++, v_newsubjseq);
            pstmt.setString(index++, v_userid);
            pstmt.setString(index++, "Y");
            pstmt.setString(index++, v_dinsertreason);
            pstmt.setString(index++, "11"); // 재학상태로 변경
            pstmt.setString(index++, v_edustart);
            pstmt.setString(index++, v_eduend);
            pstmt.setString(index++, v_studentno);
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, s_userid);

            isOk1 = pstmt.executeUpdate();

            /* 기존 학적 변경 */
            box.put("p_subj", v_oldsubj);
            box.put("p_year", v_oldyear);
            box.put("p_subjseq", v_oldsubjseq);
            box.put("p_stustate", "2"); // 기존 학적 휴학으로 변경
            box.put("p_changereason", v_dinsertreason);
            box.put("p_userid", v_userid);

            OffSchRegBean offSchRegBean = new OffSchRegBean();
            isOk2 = offSchRegBean.updateState(box, connMgr);

            /* 새 학적이력 등록 */
            box.put("p_subj", v_newsubj);
            box.put("p_year", v_newyear);
            box.put("p_subjseq", v_newsubjseq);
            box.put("p_stustate", "3");
            box.put("p_changereason", v_dinsertreason);
            box.put("p_userid", v_userid);

            //OffSchRegBean offSchRegBean = new OffSchRegBean();
            isOk3 = offSchRegBean.updateState(box, connMgr); // 복학으로 변경

            box.put("p_stustate", "11");
            isOk4 = offSchRegBean.updateState(box, connMgr); // 재학으로 변경

            System.out.println("isOk : " + isOk);
            System.out.println("isOk1 : " + isOk1);
            System.out.println("isOk2 : " + isOk2);
            System.out.println("isOk3 : " + isOk3);
            System.out.println("isOk4 : " + isOk4);
            if (isOk > 0 && isOk1 > 0 && isOk2 > 0 && isOk3 > 0 && isOk4 > 0) {
                connMgr.commit();
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
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
            sql.append("	(B.SUBJ || lpad(B.SUBJSEQ, 2, '0') || lpad(NVL(MAX(A.STUDENTNO), 0)+1, 3, '0')) STUDENTNO,\n");
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
                }
                i = 1;
            }
            pstmtPropose.executeBatch();
            pstmtStudentInsert.executeBatch();
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
     * 수료/미수료 처리
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int updateGraduated(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        StringBuffer sql = new StringBuffer();

        int isOk = 0;

        try {

            connMgr = new DBConnectionManager();

            String p_subj = box.getString("p_subj");
            String p_subjseq = box.getString("p_subjseq");
            String p_year = box.getString("p_year");
            String p_graduated = box.getString("p_graduated");
            String[] p_userid = box.getString("p_userid").split("[|]");

            sql.append(" UPDATE TZ_OFFSTUDENT SET \n");
            sql.append(" ISGRADUATED = " + StringManager.makeSQL(p_graduated) + " \n");
            if (p_graduated.equals("Y")) {
                sql.append(" ,SERNO = ? ");
            } else {
                sql.append(" ,SERNO = '' ");
            }
            sql.append(" WHERE SUBJ = " + StringManager.makeSQL(p_subj) + " \n");
            sql.append(" AND YEAR = " + StringManager.makeSQL(p_year) + " \n");
            sql.append(" AND SUBJSEQ = " + StringManager.makeSQL(p_subjseq) + " \n");
            sql.append(" AND USERID = ? \n");

            pstmt = connMgr.prepareStatement(sql.toString(), box);

            for (int j = 0; j < p_userid.length; j++) {
                int i = 1;

                if (p_graduated.equals("Y")) {
                    String v_serno = getMaxCompleteCode(connMgr, p_subj, p_year, p_subjseq);
                    pstmt.setString(i, v_serno);
                    i++;
                }
                pstmt.setString(i, p_userid[j]);

                pstmt.executeUpdate();
            }

            isOk = 1;

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, connMgr.replaceParam(sql.toString(), box, true));
            throw new Exception("sql = " + connMgr.replaceParam(sql.toString(), box, true) + "\r\n" + ex.getMessage());
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
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.off.OffStudentManageBean listPage (오프라인관리 | 수강생관리) */  \n");
            sql.append("SELECT   A.SUBJ  \n");
            sql.append("    ,    A.SUBJNM    \n");
            sql.append("    ,    B.SUBJSEQ   \n");
            sql.append("    ,    B.YEAR      \n");
            sql.append("    ,    B.USERID    \n");
            sql.append("    ,    C.NAME      \n");
            sql.append("    ,    B.STUDENTNO \n");
            sql.append("    ,    A.ISTERM    \n");
            sql.append("    ,    B.CONFIRMDATE   \n");
            sql.append("    ,    B.EDUSTART  \n");
            sql.append("    ,    B.EDUEND    \n");
            sql.append("    ,    B.STUSTATUS \n");
            sql.append("    ,    TO_CHAR( TO_DATE( B.CONFIRMDATE, 'YYYYMMDDHH24MISS'), 'YYYY.MM.DD') AS CONFIRMDATE2 \n");
            sql.append("    ,    TO_CHAR( TO_DATE( B.EDUSTART, 'YYYYMMDDHH24MISS'), 'YYYY.MM.DD') || '~' || TO_CHAR( TO_DATE( B.EDUEND, 'YYYYMMDDHH24MISS'), 'YYYY.MM.DD') AS EDU    \n");
            // sql.append("DECODE(A.ISTERM, 'Y', D.CODENM, 'N', (SELECT CODENM FROM TZ_CODE WHERE GUBUN(+) = '100' AND CODE(+) = B.STUSTATUS), '미처리') STUSTATUSNM\n");
            // sql.append("  (SELECT NVL(CODENM, '미처리') FROM TZ_CODE WHERE GUBUN(+) = '0100' AND CODE(+) = B.STUSTATUS) \n");
            // sql.append("(CASE WHEN A.ISTERM = 'Y' THEN D.CODENM \n");
            // sql.append("     WHEN A.ISTERM = 'N' THEN \n");
            // sql.append("  (SELECT NVL(CODENM, '미처리') FROM TZ_CODE WHERE GUBUN(+) = '0100' AND CODE(+) = B.ISGRADUATED) \n");
            sql.append("    ,   (CASE WHEN B.ISGRADUATED = 'Y' THEN '수료' ELSE '미수료'  END ) AS STUSTATUSNM   \n");
            sql.append("    ,   DECODE(C.SEX, '1', '남', '여') AS SEX \n");
            sql.append("    ,   CRYPTO.DEC('normal', C.HOMETEL) AS HOMETEL  \n");
            sql.append("    ,   CRYPTO.DEC('normal', C.HANDPHONE) AS HANDPHONE  \n");
            sql.append("    ,   C.ADDR  \n");
            sql.append("    ,   C.ADDR2 \n");
            sql.append("    ,   E.COMPTEXT    \n");
            sql.append("    ,   C.MEMBERYEAR || LPAD(C.MEMBERMONTH, 2,'0') || LPAD(C.MEMBERDAY, 2, '0') AS MEMBERREG \n");
            sql.append("    ,   DECODE( C.MEMBERYEAR, NULL, 0, FLOOR(MONTHS_BETWEEN(SYSDATE,TO_DATE(C.MEMBERYEAR || LPAD(C.MEMBERMONTH, 2,'0') || LPAD(C.MEMBERDAY, 2, '0'), 'YYYYMMDD'))/12) ) AS USERAGE \n");
            sql.append("    ,   F.TCAREERYEAR \n");
            sql.append("    ,   F.TCAREERMONTH    \n");
            sql.append("  FROM  TZ_OFFSUBJ A    \n");
            sql.append("    ,   TZ_OFFSTUDENT B \n");
            sql.append("    ,   TZ_MEMBER C \n");
            sql.append("    ,   TZ_CODE D   \n");
            sql.append("    ,   TZ_OFFPROPOSE E \n");
            sql.append("    ,   TZ_OFFPROPOSE_ADDINFO F    \n");
            sql.append(" WHERE  A.SUBJ = B.SUBJ \n");
            sql.append("   AND  1 = DECODE(NVL(:s_subjcode, 'ALL'), A.SUBJ, 1, 'ALL', 1, 0) \n");
            sql.append("   AND  1 = DECODE(NVL(:s_upperclass, UPPERCLASS), UPPERCLASS, 1, 'ALL', 1, 0)  \n");
            sql.append("   AND  1 = DECODE(NVL(:s_middleclass, MIDDLECLASS), MIDDLECLASS, 1, 'ALL', 1, 0)   \n");
            sql.append("   AND  1 = DECODE(NVL(:s_lowerclass, LOWERCLASS), LOWERCLASS, 1, 'ALL', 1, 0)  \n");
            sql.append("   AND  1 = DECODE(NVL(:s_subjseq, B.SUBJSEQ), B.SUBJSEQ, 1, 'ALL', 1, 0)   \n");
            sql.append("   AND  A.SUBJNM LIKE '%' || nvl(:s_subjsearchkey, A.SUBJNM) || '%' \n");
            sql.append("   AND  B.USERID = C.USERID \n");
            sql.append("   AND  D.GUBUN(+) = '0089' \n");
            sql.append("   AND  B.STUSTATUS = D.CODE(+) \n");
            sql.append("   AND  B.SUBJ = E.SUBJ        \n");
            sql.append("   AND  B.YEAR = E.YEAR        \n");
            sql.append("   AND  B.SUBJSEQ = E.SUBJSEQ  \n");
            sql.append("   AND  B.USERID = E.USERID    \n ");
            sql.append("   AND  E.SUBJ = F.SUBJ        \n");
            sql.append("   AND  E.SUBJSEQ = F.SUBJSEQ  \n");
            sql.append("   AND  E.YEAR = F.YEAR        \n");
            sql.append("   AND  E.USERID = F.USERID    \n");
            sql.append("   AND  E.CHKFINAL = 'Y'       \n");
            sql.append("   AND  C.GRCODE = 'N000001'   \n");

            if (box.getString("p_orderColumn").length() > 0) {
                box.put("p_orderType", box.getStringDefault("p_orderType", " asc"));
                sql.append("ORDER BY ");
                sql.append(box.getString("p_orderColumn"));
                sql.append(box.getString("p_orderType"));
            }

            t = connMgr.replaceParam(sql.toString(), box);
            ls = connMgr.executeQuery(t);

            result = new ArrayList<DataBox>();
            EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
            while (ls.next()) {
                DataBox entity = ls.getDataBox();
                entity.put("d_paymethod", payMethod.get(entity.getString("d_paymethod")));

                //====================================================
                // 개인정보 복호화 - HTJ
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

                //if (!entity.getString("d_handphone").equals("")) entity.put("d_handphone", encryptUtil.decrypt(entity.getString("d_handphone")));
                //if (!entity.getString("d_hometel").equals("")) entity.put("d_hometel", encryptUtil.decrypt(entity.getString("d_hometel")));
                //                if (!entity.getString("d_email").equals("")) entity.put("d_email", encryptUtil.decrypt(entity.getString("d_email")));
                //               if (!entity.getString("d_hometel").equals("")) entity.put("d_hometel", encryptUtil.decrypt(entity.getString("d_hometel")));
                //                if (!entity.getString("d_handphone").equals("")) entity.put("d_handphone", encryptUtil.decrypt(entity.getString("d_handphone")));
                if (!entity.getString("d_addr2").equals(""))
                    entity.put("d_addr2", encryptUtil.decrypt(entity.getString("d_addr2")));
                //====================================================
                //entity.put("d_addr3", payMethod.get(entity.getString("d_addr"))+"-"+payMethod.get(entity.getString("d_addr2")));

                result.add(entity);
            }

            //	result = ls.getAllDataList();
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

    public List<DataBox> acceptResultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        List<DataBox> result = null;
        String t = null;

        try {
            connMgr = new DBConnectionManager();
            sql.append("SELECT CODE value, CODENM name FROM TZ_CODE WHERE GUBUN = '0098'");
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

    /**
     * 오프라인관리 | 대상자 추가 - 목록 조회
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public List<DataBox> studentList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        List<DataBox> result = null;
        String t = null;

        try {
            connMgr = new DBConnectionManager();
            sql.append("SELECT	/*	오프라인관리 | 대상자 추가 - 목록 조회	*/\n");
            sql.append("	A.USERID, A.NAME, B.STUDENTNO, B.CONFIRMDATE, B.STUSTATUS, C.CODENM STUSTATUSNM\n");
            sql.append("FROM TZ_MEMBER A, TZ_OFFSTUDENT B, TZ_CODE C\n");
            sql.append("WHERE A.USERID = B.USERID\n");
            sql.append("AND 1 = DECODE(NVL(':s_year', B.YEAR), B.YEAR, 1, 0)	\n");
            sql.append("AND 1 = DECODE(NVL(':s_subjcode', B.SUBJ), B.SUBJ, 1, 'ALL', 1, 0)	\n");
            sql.append("AND 1 = DECODE(NVL(':s_subjseq', B.SUBJSEQ), B.SUBJSEQ, 1, 'ALL', 1, 0)	\n");
            sql.append("AND 1 =\n");
            sql.append("	CASE\n");
            sql.append("		WHEN ':p_param1' = 'name' AND 1 = DECODE(NVL(':p_param2', A.NAME), A.NAME, 1, 0) THEN 1\n");
            sql.append("		WHEN ':p_param1' = 'id' AND 1 = DECODE(NVL(':p_param2', A.USERID), A.USERID, 1, 0) THEN 1\n");
            sql.append("		ELSE 2\n");
            sql.append("	END\n");
            sql.append("AND C.GUBUN = '0089'\n");
            sql.append("AND B.STUSTATUS = C.CODE\n");
            ls = connMgr.executeQuery(sql.toString(), box);
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

    /**
     * 오프라인 수료증번호 구하기
     * 
     * @param connMgr
     * @param v_subj
     * @param v_year
     * @param v_subjseq
     * @return
     * @throws Exception
     */
    public String getMaxCompleteCode(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq) throws Exception {

        String v_completecode = "";
        String v_maxcompletecode = "";
        int v_maxno = 0;

        ListSet ls = null;
        String sql = "";

        String curryear = FormatDate.getDate("yy");

        try {

            sql = "select NVL(SUBSTR(year,3,2),to_char(sysdate, 'YY')) yy from TZ_OFFSUBJSEQ ";
            sql += "where  subj = '" + v_subj + "' and ";
            sql += "		  year = '" + v_year + "' and ";
            sql += "		  subjseq = '" + v_subjseq + "'";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                curryear = ls.getString("yy");
            }

            ls.close();

            //수료증 번호 규칙 : 년+월+일련번호(5)(yyyy-MM-00001)
            //수료증 번호 규칙 : 년월(YYYYMM)-과정코드(4)-차수(2)-일련번호(4)
            //sql = "select max(substr(serno,9,13)) maxno ";
            //sql+= "from tz_stold ";
            //sql+= "where		  substr(serno,1,8) = '" + currdate + "-' ";

            sql = "select 	max(substr(serno,13,3)) maxno ";
            sql += "from 	TZ_OFFSTUDENT ";
            sql += "where	subj = '" + v_subj + "' and ";
            sql += "			year = '" + v_year + "' and ";
            sql += "			subjseq = '" + v_subjseq + "' and ";
            if (v_subjseq.length() == 1)
                v_subjseq = "000" + v_subjseq;
            else if (v_subjseq.length() == 2)
                v_subjseq = "00" + v_subjseq;
            sql += "		   SUBSTR(serno,1,11) = '" + curryear + v_subj + v_subjseq.substring(2, 4) + "'";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_maxcompletecode = ls.getString("maxno");
            }

            if (v_maxcompletecode.equals("")) {
                v_completecode = curryear + v_subj + v_subjseq.substring(2, 4) + "0001";
            } else {
                v_maxno = Integer.valueOf(v_maxcompletecode).intValue();
                v_completecode = curryear + v_subj + v_subjseq.substring(2, 4) + new DecimalFormat("0000").format(v_maxno + 1);
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

        return v_completecode;
    }

    public ArrayList<DataBox> selectApplicantPrint(RequestBox box) throws Exception {
        ListSet ls = null;
        String sql = "";
        String tmp = box.getVector("p_checks").toString().replace("[", "").replace("]", "");
        String[] tmp1 = tmp.split(",,");
        String tmpId = "";
        for (int i = 0; i < tmp1.length; i++) {
            String[] tmp2 = tmp1[i].split(",");
            tmpId += "'" + tmp2[0].trim() + "'";
            if (i < tmp1.length - 1)
                tmpId += ",";
        }

        String v_subj = box.getString("s_subjcode");
        String v_year = box.getString("s_year");
        String v_subjseq = box.getString("s_subjseq");
        ArrayList<DataBox> list = new ArrayList<DataBox>();

        DBConnectionManager connMgr = null;

        try {
            connMgr = new DBConnectionManager();

            sql = "select 	 \n";
            sql += "  b.subjnm,substr(b.edustart,1,4)||'.'||substr(b.edustart,5,2)||'.'||substr(b.edustart,8,2) as start_dt, \n ";
            sql += "  substr(b.eduend,1,4)||'.'||substr(b.eduend,5,2)||'.'||substr(b.eduend,8,2) as end_dt, \n ";
            sql += "  to_date(substr(b.eduend,1,8),'yyyy-mm-dd')-to_date(substr(b.edustart,1,8),'yyyy-mm-dd')+1 as days,\n ";
            sql += "  (select max(sub_time) from tz_subjseq_detail z where a.subj=z.subj and a.year=z.year and a.subjseq=z.subjseq ) as sub_time,\n ";
            sql += "  c.name,substr(c.resno,1,6)||'-'||substr(c.resno,7) as resno,c.COMPTEXT,c.DEPTNAM,crypto.dec('normal',c.HANDPHONE) as handphone\n ";
            sql += "  from tz_offpropose a \n ";
            sql += "  left join tz_offsubjseq b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq \n ";
            sql += "  left join tz_member c on a.userid=c.userid \n ";
            sql += "where	a.subj = '" + v_subj + "' and ";
            sql += "			a.year = '" + v_year + "' and ";
            sql += "			a.subjseq = '" + v_subjseq + "' and ";
            sql += "         a.userid in (" + tmpId + ")  ";
            //            sql+= "     and   CHKFINAL='Y'";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                DataBox box1 = ls.getDataBox();
                list.add(box1);
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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return list;
    }

    public ArrayList<DataBox> selectCompletePrint(RequestBox box) throws Exception {
        ListSet ls = null;
        String sql = "";
        String tmp = box.getVector("p_checks").toString().replace("[", "").replace("]", "");
        String[] tmp1 = tmp.split(",,");
        String tmpId = "";
        for (int i = 0; i < tmp1.length; i++) {
            String[] tmp2 = tmp1[i].split(",");
            tmpId += "'" + tmp2[0].trim() + "'";
            if (i < tmp1.length - 1)
                tmpId += ",";
        }

        String v_subj = box.getString("s_subjcode");
        String v_year = box.getString("s_year");
        String v_subjseq = box.getString("s_subjseq");
        ArrayList<DataBox> list = new ArrayList<DataBox>();

        DBConnectionManager connMgr = null;

        try {
            connMgr = new DBConnectionManager();

            sql = "select 	 \n";
            sql += "  b.subjnm,substr(b.edustart,1,4)||'.'||substr(b.edustart,5,2)||'.'||substr(b.edustart,8,2) as start_dt, \n ";
            sql += "  substr(b.eduend,1,4)||'.'||substr(b.eduend,5,2)||'.'||substr(b.eduend,8,2) as end_dt, \n ";
            sql += "  to_date(substr(b.eduend,1,8),'yyyy-mm-dd')-to_date(substr(b.edustart,1,8),'yyyy-mm-dd')+1 as days,\n ";
            sql += "  (select max(sub_time) from tz_subjseq_detail z where a.subj=z.subj and a.year=z.year and a.subjseq=z.subjseq ) as sub_time,\n ";
            sql += "  c.name,substr(c.resno,1,6)||'-'||substr(c.resno,7) as resno,c.COMPTEXT,c.DEPTNAM,crypto.dec('normal',c.HANDPHONE) handphone\n ";
            sql += "  from tz_offstudent a \n ";
            sql += "  left join tz_offsubjseq b on a.subj=b.subj and a.year=b.year and a.subjseq=b.subjseq \n ";
            sql += "  left join tz_member c on a.userid=c.userid \n ";
            sql += "where	a.subj = '" + v_subj + "' and ";
            sql += "			a.year = '" + v_year + "' and ";
            sql += "			a.subjseq = '" + v_subjseq + "' and ";
            sql += "         a.userid in (" + tmpId + ")  ";
            //            sql+= "      and  a.ISGRADUATED='Y'";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                DataBox box1 = ls.getDataBox();
                list.add(box1);
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
