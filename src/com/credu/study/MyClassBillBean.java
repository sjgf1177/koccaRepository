//**********************************************************
//  1. 제      목: MYCLASS BILL USER BEAN
//  2. 프로그램명: MyClassBillBean.java
//  3. 개      요: 나의강의실 수강료 조회/납부 bean
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.24
//  7. 수      정:
//**********************************************************
package com.credu.study;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

public class MyClassBillBean {
    private ConfigSet config;
    private int row;

    public MyClassBillBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 결제내역 리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 결제내역 리스트
     */
    public ArrayList<DataBox> selectMyClassBillList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String v_userid = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");

        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql += "\n SELECT   grcode, tid, userid, usernm, goodname, inputdate, price, ";
            sql += "\n          SUM (biyong) biyong, resultcode, paymethod, buyername, pgauthdate, ";
            sql += "\n          cancelyn, canceldate, canceltime, paystatus, ldate ";
            sql += "\n     FROM (SELECT a.grcode, a.tid, a.userid, a.usernm, a.goodname, ";
            sql += "\n                  NVL (TRIM (a.inputdate), a.ldate) inputdate, a.price, ";
            sql += "\n                  d.biyong, a.resultcode, a.paymethod, buyername, a.pgauthdate, ";
            sql += "\n                  a.cancelyn, a.canceldate, a.canceltime,";
            sql += "\n                  CASE ";
            sql += "\n                     WHEN a.cancelyn = 'Y' ";
            sql += "\n                        THEN 'R' ";
            sql += "\n                     WHEN a.cancelyn = 'N' AND a.resultcode = '00' ";
            sql += "\n                        THEN 'Y' ";
            sql += "\n                     WHEN a.cancelyn = 'N' AND a.resultcode = '99' ";
            sql += "\n                        THEN 'N' ";
            sql += "\n                  END paystatus, ";
            sql += "\n                  a.ldate ";
            sql += "\n             FROM tz_billinfo a INNER JOIN tz_member b ON a.userid = b.userid and a.grcode=b.grcode ";
            sql += "\n                  INNER JOIN tz_propose c ON a.tid = c.tid ";
            sql += "\n                  INNER JOIN tz_subjseq d ";
            sql += "\n                  ON c.subj = d.subj ";
            sql += "\n                AND c.YEAR = d.YEAR ";
            sql += "\n                AND c.subjseq = d.subjseq ";
            sql += "\n            WHERE a.resultcode <> '01' ";
            sql += "\n		      AND a.userid =" + SQLString.Format(v_userid);
            sql += "\n		      AND a.grcode =" + SQLString.Format(v_grcode);
            sql += "\n           ) ";
            sql += "\n GROUP BY grcode, ";
            sql += "\n          tid, ";
            sql += "\n          userid, ";
            sql += "\n          usernm, ";
            sql += "\n          goodname, ";
            sql += "\n          inputdate, ";
            sql += "\n          price, ";
            sql += "\n          resultcode, ";
            sql += "\n          paymethod, ";
            sql += "\n          buyername, ";
            sql += "\n          pgauthdate, ";
            sql += "\n          cancelyn, ";
            sql += "\n          canceldate, ";
            sql += "\n          canceltime, ";
            sql += "\n          paystatus, ";
            sql += "\n          ldate ";
            sql += "\n ORDER BY ldate DESC, pgauthdate DESC, tid DESC ";

            System.out.println("sql_myclassbilllist============>" + sql);

            ls = connMgr.executeQuery(sql);

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
     * off-line 결제내역 리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 결제내역 리스트
     */
    public ArrayList<DataBox> selectMyOffClassBillList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String v_userid = box.getSession("userid");

        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql += "   SELECT   * ";
            sql += "     FROM (SELECT 'BILL' listgubun, a.tid, a.userid, a.usernm, a.goodname, a.price, a.resultcode, ";
            sql += "                  a.paymethod, buyername, a.pgauthdate, a.cancelyn, ";
            sql += "                  a.canceldate, a.canceltime, ";
            sql += "                  (SELECT MIN (billbegindt) ";
            sql += "                     FROM tz_offpropose pp, tz_offsubjseq ss ";
            sql += "                    WHERE pp.subj = ss.subj ";
            sql += "                      AND pp.YEAR = ss.YEAR ";
            sql += "                      AND pp.subjseq = ss.subjseq ";
            sql += "                      AND pp.seq = ss.seq ";
            sql += "                      AND pp.tid = a.tid) billbegindt, ";
            sql += "                  (SELECT MAX (billenddt) ";
            sql += "                     FROM tz_offpropose pp, tz_offsubjseq ss ";
            sql += "                    WHERE pp.subj = ss.subj ";
            sql += "                      AND pp.YEAR = ss.YEAR ";
            sql += "                      AND pp.subjseq = ss.subjseq ";
            sql += "                      AND pp.seq = ss.seq ";
            sql += "                      AND pp.tid = a.tid) billenddt, ";
            sql += "                  (SELECT ss.biyong ";
            sql += "                     FROM tz_offpropose pp, tz_offsubjseq ss ";
            sql += "                    WHERE pp.subj = ss.subj ";
            sql += "                      AND pp.YEAR = ss.YEAR ";
            sql += "                      AND pp.seq = ss.seq ";
            sql += "                      AND pp.subjseq = ss.subjseq ";
            sql += "                      AND pp.tid = a.tid) biyong, ";
            sql += "                  CASE ";
            sql += "                     WHEN a.cancelyn = 'Y' ";
            sql += "                        THEN 'R' ";
            sql += "                     WHEN a.cancelyn = 'N' AND a.resultcode = '00' ";
            sql += "                        THEN 'Y' ";
            sql += "                     WHEN a.cancelyn = 'N' AND a.resultcode = '99' ";
            sql += "                        THEN 'N' ";
            sql += "                  END paystatus, ";
            sql += "                  a.ldate, NULL subj, NULL year, NULL subjseq, 0 seq ";
            sql += "             FROM tz_offbillinfo a INNER JOIN tz_member b ON a.userid = b.userid  AND b.grcode = " + SQLString.Format(box.getSession("tem_grcode"));
            sql += "            WHERE a.resultcode <> '01' AND a.userid = " + SQLString.Format(v_userid);
            sql += "           UNION ALL ";
            sql += "           SELECT 'REQ' listgubun, b.tid, b.userid, (SELECT NAME ";
            sql += "                                      FROM tz_member ";
            sql += "                                     WHERE userid = b.userid and grcode = " + SQLString.Format(box.getSession("tem_grcode")) + " ) usernm, ";
            sql += "                  a.billreqnm, a.realbiyong, b.billstatus, NULL, ";
            sql += "                  (SELECT NAME ";
            sql += "                     FROM tz_member ";
            sql += "                    WHERE userid = b.userid and grcode = " + SQLString.Format(box.getSession("tem_grcode")) + " ), NULL, 'N', NULL, NULL, ";
            sql += "                  b.billbegindt, b.billenddt, b.biyong,";
            sql += "                  CASE ";
            sql += "                     WHEN b.billstatus = '99' ";
            sql += "                        THEN 'N' ";
            sql += "                     WHEN b.billstatus = '00' ";
            sql += "                        THEN 'Y' ";
            sql += "                  END paystatus, ";
            sql += "                  b.ldate, a.subj, a.year, a.subjseq, a.seq ";
            sql += "             FROM tz_offbillreq a INNER JOIN tz_offbillrequser b ";
            sql += "                  ON a.subj = b.subj ";
            sql += "                AND a.YEAR = b.YEAR ";
            sql += "                AND a.subjseq = b.subjseq ";
            sql += "                AND a.seq = b.seq ";
            sql += "            WHERE b.userid = " + SQLString.Format(v_userid);
            sql += "              AND b.billstatus = '99'";
            sql += "           UNION ALL ";
            sql += "           SELECT 'PROP' listgubun, a.tid, a.userid, (SELECT NAME ";
            sql += "                                      FROM tz_member ";
            sql += "                                     WHERE userid = a.userid and grcode = " + SQLString.Format(box.getSession("tem_grcode")) + " ) usernm, b.subjnm, b.biyong, ";
            sql += "                  '99' billstatus, NULL, (SELECT NAME ";
            sql += "                                            FROM tz_member ";
            sql += "                                           WHERE userid = a.userid and grcode = " + SQLString.Format(box.getSession("tem_grcode")) + " ), NULL, 'N', NULL, ";
            sql += "                  NULL, b.billbegindt, b.billenddt, b.biyong, 'N' paystatus, b.ldate, ";
            sql += "                  a.subj, a.YEAR, a.subjseq, a.seq ";
            sql += "             FROM tz_offpropose a INNER JOIN tz_offsubjseq b ";
            sql += "               ON a.subj = b.subj ";
            sql += "              AND a.YEAR = b.YEAR ";
            sql += "              AND a.subjseq = b.subjseq ";
            sql += "              AND a.seq = b.seq ";
            sql += "            WHERE a.chkfirst = 'Y' ";
            sql += "              AND a.chkfinal = 'U' ";
            sql += "              AND a.tid IS NULL ";
            sql += "              AND a.userid = " + SQLString.Format(v_userid);
            sql += "                 ) ";
            sql += " ORDER BY ldate DESC, pgauthdate DESC, tid DESC ";

            System.out.println("sql_myoffclassbilllist============>" + sql);

            ls = connMgr.executeQuery(sql);

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
     * off-line 결제 정보 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectOffPayInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String v_listgubun = box.getString("p_listgubun"); //BILL 기결제, REQ 결제등록, PROP 수강신청
        String v_tid = box.getString("p_tid");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_seq = box.getString("p_seq");
        String v_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            if ("BILL".equals(v_listgubun)) {
                sql = " SELECT (SELECT sa.classname ";
                sql += "           FROM tz_offsubjatt sa, tz_offsubj sj ";
                sql += "          WHERE sa.upperclass = sj.upperclass ";
                sql += "            AND sa.middleclass = '000' ";
                sql += "            AND sa.lowerclass = '000' ";
                sql += "            AND sj.subj = b.subj) classname, ";
                sql += " 		(SELECT sj.area ";
                sql += "           FROM tz_offsubjatt sa, tz_offsubj sj ";
                sql += "          WHERE sa.upperclass = sj.upperclass ";
                sql += "            AND sa.middleclass = '000' ";
                sql += "            AND sa.lowerclass = '000' ";
                sql += "            AND sj.subj = b.subj) area, ";
                sql += "        (SELECT edustart FROM tz_offsubjseq WHERE subj = b.subj and year = b.year and subjseq = b.subjseq and seq = '1') edustart,";
                sql += "        (SELECT eduend FROM tz_offsubjseq WHERE subj = b.subj and year = b.year and subjseq = b.subjseq and seq = '1') eduend,";
                sql += "        a.tid, c.subj, a.goodname subjnm, c.YEAR, c.subjseq, c.biyong, a.price, ";
                sql += "        a.paymethod, a.pgauthdate, a.pgauthtime, a.buyername, a.buyeremail, ";
                sql += "        a.buyertel, a.resultcode, a.cancelyn, a.canceldate, a.canceltime, ";
                sql += "        a.resultmsg, ";
                sql += "        CASE ";
                sql += "           WHEN a.cancelyn = 'Y' ";
                sql += "              THEN 'R' ";
                sql += "           WHEN a.cancelyn = 'N' AND a.resultcode = '00' ";
                sql += "              THEN 'Y' ";
                sql += "           WHEN a.cancelyn = 'N' AND a.resultcode = '99' ";
                sql += "              THEN 'N' ";
                sql += "        END paystatus ";
                sql += "   FROM tz_offbillinfo a INNER JOIN tz_offpropose b ON a.tid = b.tid ";
                sql += "        INNER JOIN tz_offsubjseq c ";
                sql += "        ON b.subj = c.subj ";
                sql += "      AND b.YEAR = c.YEAR ";
                sql += "      AND b.subjseq = c.subjseq ";
                sql += "      AND b.seq = c.seq ";
                sql += "  WHERE a.tid = " + SQLString.Format(v_tid);
            } else if ("REQ".equals(v_listgubun)) {

                sql = " SELECT (SELECT sa.classname ";
                sql += "           FROM tz_offsubjatt sa, tz_offsubj sj ";
                sql += "          WHERE sa.upperclass = sj.upperclass ";
                sql += "            AND sa.middleclass = '000' ";
                sql += "            AND sa.lowerclass = '000' ";
                sql += "            AND sj.subj = b.subj) classname, ";
                sql += " 		(SELECT sj.area ";
                sql += "           FROM tz_offsubjatt sa, tz_offsubj sj ";
                sql += "          WHERE sa.upperclass = sj.upperclass ";
                sql += "            AND sa.middleclass = '000' ";
                sql += "            AND sa.lowerclass = '000' ";
                sql += "            AND sj.subj = b.subj) area, ";
                sql += "        (SELECT edustart FROM tz_offsubjseq WHERE subj = b.subj and year = b.year and subjseq = b.subjseq and seq = '1') edustart,";
                sql += "        (SELECT eduend FROM tz_offsubjseq WHERE subj = b.subj and year = b.year and subjseq = b.subjseq and seq = '1') eduend,";
                sql += "        b.tid, a.subj, (SELECT subjnm ";
                sql += "                          FROM tz_offsubj ";
                sql += "                         WHERE subj = a.subj) subjnm, a.YEAR, a.subjseq, ";
                sql += "        a.biyong biyong, a.realbiyong price, NULL paymethod, NULL pgauthdate, NULL pgauthtime, ";
                sql += "        (SELECT NAME ";
                sql += "           FROM tz_member ";
                sql += "          WHERE userid = b.userid and grcode='N000001') buyername, (SELECT email ";
                sql += "                                              FROM tz_member ";
                sql += "                                             WHERE userid = b.userid and grcode='N000001') buyeremail, ";
                sql += "        (SELECT handphone ";
                sql += "           FROM tz_member ";
                sql += "          WHERE userid = b.userid and grcode='N000001') buyertel, '99' resultcode, 'N' cancelyn, NULL canceldate, NULL canceltime, NULL resultmsg, 'N' paystatus ";
                sql += "   FROM tz_offbillreq a INNER JOIN tz_offbillrequser b ";
                sql += "        ON a.subj = b.subj ";
                sql += "      AND a.YEAR = b.YEAR ";
                sql += "      AND a.subjseq = b.subjseq ";
                sql += "      AND a.seq = b.seq ";
                sql += "  WHERE b.userid = " + SQLString.Format(v_userid);
                sql += "    AND a.subj = " + SQLString.Format(v_subj);
                sql += "    AND a.YEAR = " + SQLString.Format(v_year);
                sql += "    AND a.subjseq = " + SQLString.Format(v_subjseq);
                sql += "    AND a.seq = " + SQLString.Format(v_seq);
            } else if ("PROP".equals(v_listgubun)) {

                sql = " SELECT (SELECT sa.classname ";
                sql += "           FROM tz_offsubjatt sa, tz_offsubj sj ";
                sql += "          WHERE sa.upperclass = sj.upperclass ";
                sql += "            AND sa.middleclass = '000' ";
                sql += "            AND sa.lowerclass = '000' ";
                sql += "            AND sj.subj = b.subj) classname, ";
                sql += " 		(SELECT sj.area ";
                sql += "           FROM tz_offsubjatt sa, tz_offsubj sj ";
                sql += "          WHERE sa.upperclass = sj.upperclass ";
                sql += "            AND sa.middleclass = '000' ";
                sql += "            AND sa.lowerclass = '000' ";
                sql += "            AND sj.subj = b.subj) area, ";
                sql += "       b.edustart,";
                sql += "       b.eduend,";
                sql += "       a.tid, a.subj, (SELECT subjnm ";
                sql += "                         FROM tz_offsubj ";
                sql += "                        WHERE subj = a.subj) subjnm, a.YEAR, a.subjseq, ";
                sql += "       b.biyong, b.biyong price, NULL paymethod, NULL pgauthdate, ";
                sql += "       NULL pgauthtime, (SELECT NAME ";
                sql += "                           FROM tz_member ";
                sql += "                          WHERE userid = a.userid and grcode='N000001' ) buyername, ";
                sql += "       (SELECT email ";
                sql += "          FROM tz_member ";
                sql += "         WHERE userid = a.userid and grcode='N000001' ) buyeremail, ";
                sql += "       (SELECT handphone ";
                sql += "          FROM tz_member ";
                sql += "         WHERE userid = a.userid and grcode='N000001' ) buyertel, '99' resultcode, 'N' cancelyn, ";
                sql += "       NULL canceldate, NULL canceltime, NULL resultmsg, 'N' paystatus ";
                sql += "  FROM tz_offpropose a INNER JOIN tz_offsubjseq b ";
                sql += "       ON a.subj = b.subj AND a.YEAR = b.YEAR AND a.subjseq = b.subjseq ";
                sql += " WHERE  a.subj    = " + SQLString.Format(v_subj);
                sql += "    AND a.YEAR    = " + SQLString.Format(v_year);
                sql += "    AND a.subjseq = " + SQLString.Format(v_subjseq);
                sql += "    AND a.seq     = " + SQLString.Format(v_seq);
                sql += "    AND a.userid  = " + SQLString.Format(v_userid);
                sql += "    AND b.seq     = '1'";

            }

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
        String sql = "";

        String v_tid = box.getStringDefault("p_tid", "");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT b.subj, b.subjnm ";
            sql += "   FROM tz_propose a, tz_subjseq b ";
            sql += "  WHERE a.subjseq = b.subjseq ";
            sql += "    AND a.YEAR = b.YEAR ";
            sql += "    AND a.subj = b.subj ";
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
        // String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();
            sql2 = " UPDATE tz_propose ";
            sql2 += "    SET cancelkind    = 'P', ";
            sql2 += "        refundbank    = ?, ";
            sql2 += "        refundaccount = ?, ";
            sql2 += "        refundname    = ?, ";
            sql2 += "        cancelreason  = ?, ";
            sql2 += "        canceldate    = TO_CHAR (SYSDATE, 'yyyymmddhh24miss'), ";
            sql2 += "        luserid       = ?, ";
            sql2 += "        ldate         = TO_CHAR (SYSDATE, 'yyyymmddhh24miss') ";
            sql2 += "  WHERE tid = ? ";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_refundbank);
            pstmt2.setString(2, v_refundaccount);
            pstmt2.setString(3, v_refundname);
            pstmt2.setCharacterStream(4, new StringReader(v_cancelreason), v_cancelreason.length());
            pstmt2.setString(5, v_user_id);
            pstmt2.setString(6, v_tid);
            isOk = pstmt2.executeUpdate();

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
        // String v_grcode = box.getSession("tem_grcode");
        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT   tid, subj, YEAR, subjseq, subjnm, edustart, eduend, appdate, ";
            sql += "          (SELECT sa.classname ";
            sql += "             FROM tz_offsubj sb, tz_offsubjatt sa ";
            sql += "            WHERE sb.upperclass = sa.upperclass ";
            sql += "              AND sa.middleclass = '000' ";
            sql += "              AND sa.lowerclass = '000' ";
            sql += "              AND sb.subj = a.subj) classname, ";
            sql += "          refundabledate, refundableyn, refundyn, canceldate, paymethod, ";
            sql += "          chkfirst, chkfinal, RANK, ";
            sql += "          CASE ";
            sql += "             WHEN RANK = 1 ";
            sql += "                THEN COUNT (*) OVER (PARTITION BY tid) ";
            sql += "             ELSE 0 ";
            sql += "          END AS rowspan ";
            sql += "     FROM (SELECT a.tid, b.subj, b.YEAR, b.subjseq, b.subjnm, b.edustart, ";
            sql += "                  b.eduend, a.appdate, a.chkfirst, a.chkfinal,  ";
            sql += "                  CASE WHEN REFUNDDATE IS NOT NULL ";
            sql += "                       THEN 'Y' ";
            sql += "                       ELSE 'N' ";
            sql += "                  END refundyn, ";
            sql += "                  a.canceldate, c.paymethod, d.upperclass, ";
            sql += "                  endcanceldate AS refundabledate, ";
            sql += "                  CASE WHEN (    TO_CHAR(TO_DATE(substr(startcanceldate,1,8))) <= TO_CHAR (SYSDATE, 'YYYYMMDD') ";
            sql += "                             AND TO_CHAR(TO_DATE(substr(endcanceldate,1,8))) >= TO_CHAR (SYSDATE, 'YYYYMMDD') )   ";
            sql += "                       THEN 'Y' ";
            sql += "                       ELSE 'N' ";
            sql += "                  END refundableyn, ";
            sql += "                  RANK () OVER (PARTITION BY a.tid ORDER BY a.tid, ";
            sql += "                  b.subj) RANK ";
            sql += "             FROM tz_offpropose a, tz_offsubjseq b, tz_offbillinfo c, tz_offsubj d ";
            sql += "            WHERE (1 = 1) ";
            sql += "              AND a.tid = c.tid ";
            sql += "              AND a.userid = " + SQLString.Format(v_userid);
            sql += "              AND a.subj = b.subj ";
            sql += "              AND a.YEAR = b.YEAR ";
            sql += "              AND a.subjseq = b.subjseq ";

            if (!v_upperclass.equals("ALL"))
                sql += " and d.upperclass = " + SQLString.Format(v_upperclass) + "\n";

            sql += "              AND a.subj = d.subj) a ";
            sql += " ORDER BY appdate DESC, tid, subj ";

            System.out.println("sql_proposeoffhostorylist============>" + sql);

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

            sql = " SELECT b.subj, b.subjnm ";
            sql += "   FROM tz_offpropose a, tz_offsubjseq b ";
            sql += "  WHERE a.subjseq = b.subjseq ";
            sql += "    AND a.YEAR = b.YEAR ";
            sql += "    AND a.subj = b.subj ";
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
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;

        String v_tid = box.getString("p_tid");
        String v_refundbank = box.getString("p_refundbank");
        String v_refundaccount = box.getString("p_refundaccount");
        String v_refundname = box.getString("p_refundname");
        String v_cancelreason = box.getString("p_cancelreason");
        String v_user_id = box.getSession("userid");
        // String v_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();
            sql2 = " UPDATE tz_offpropose ";
            sql2 += "    SET cancelkind    = 'P', ";
            sql2 += "        refundbank    = ?, ";
            sql2 += "        refundaccount = ?, ";
            sql2 += "        refundname    = ?, ";
            sql2 += "        cancelreason  = ?, ";
            sql2 += "        canceldate    = TO_CHAR (SYSDATE, 'yyyymmddhh24miss'), ";
            sql2 += "        luserid       = ?, ";
            sql2 += "        ldate         = TO_CHAR (SYSDATE, 'yyyymmddhh24miss') ";
            sql2 += "  WHERE tid = ? ";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_refundbank);
            pstmt2.setString(2, v_refundaccount);
            pstmt2.setString(3, v_refundname);
            pstmt2.setCharacterStream(4, new StringReader(v_cancelreason), v_cancelreason.length());
            pstmt2.setString(5, v_user_id);
            pstmt2.setString(6, v_tid);
            isOk = pstmt2.executeUpdate();

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

}
