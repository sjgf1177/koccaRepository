//**********************************************************
//1. 제      목: 수강료결제 관리 (어드민)
//2. 프로그램명: SchoolfeeAdminBean.java
//3. 개      요: 행정서비스
//4. 환      경: JDK 1.5
//5. 버      젼: 1.0
//6. 작      성: 2009.12.17
//7. 수      정:
//**********************************************************
package com.credu.polity;

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
import com.credu.library.StringManager;

public class SchoolfeeAdminBean {

    private ConfigSet config;
    private int row;

    public SchoolfeeAdminBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 수강료 결제관리 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;

        DataBox dbox = null;

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        String v_searchtext = box.getString("p_searchtext");

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류
        String ss_paystatus = box.getStringDefault("s_paystatus", "ALL"); //결제상태
        String ss_paymethod = box.getStringDefault("s_paymethod", "ALL"); //결제방법
        String ss_membergubun = box.getStringDefault("s_membergubun", "ALL"); //결제방법

        String v_startdate = box.getString("s_startdate");
        String v_enddate = box.getString("s_enddate");
        v_startdate = v_startdate.replace("-", "");
        v_enddate = v_enddate.replace("-", "");

        String v_startappdate = box.getString("s_startappdate");
        String v_endappdate = box.getString("s_endappdate");
        v_startappdate = v_startappdate.replace("-", "");
        v_endappdate = v_endappdate.replace("-", "");

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        StringBuffer sql = new StringBuffer();
        StringBuffer count_sql = new StringBuffer();
        StringBuffer head_sql = new StringBuffer();
        StringBuffer body_sql = new StringBuffer();
        StringBuffer order_sql = new StringBuffer();

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            head_sql.append(" SELECT A.GRCODE, A.TID, A.USERID, A.USERNM, B.MEMBERGUBUN, C.CODENM, \n");
            head_sql.append("        A.GOODNAME, NVL (TRIM (A.INPUTDATE), A.LDATE) INPUTDATE, A.PRICE, \n");
            head_sql.append("        A.RESULTCODE, A.PAYMETHOD, BUYERNAME, A.PGAUTHDATE, A.CANCELYN,\n");
            if (box.getBoolean("isSubject")) {
                head_sql.append("        F.AREA, (SELECT CODENM FROM TZ_CODE WHERE CODE = F.AREA AND GUBUN='0101') AREANM, E.SUBJNM, E.BIYONG, A.DISCOUNTPERCENT, NVL(E.BIYONG, 0)*(100 - NVL(A.DISCOUNTPERCENT, 0))/100 PAYMENT, \n");
            }
            head_sql.append(" CASE \n");
            head_sql.append("    WHEN A.CANCELYN = 'Y' \n");
            head_sql.append("       THEN 'R' \n");
            head_sql.append("    WHEN A.CANCELYN = 'N' AND A.RESULTCODE = '00' \n");
            head_sql.append("       THEN 'Y' \n");
            head_sql.append("    WHEN A.CANCELYN = 'N' AND A.RESULTCODE = '99' \n");
            head_sql.append("       THEN 'N' \n");
            head_sql.append(" END PAYSTATUS, \n");
            head_sql.append(" (SELECT SUM(USEPOINT) USEPOINT FROM TZ_POINTUSE WHERE TID = A.TID AND USERID = A.USERID) USEPOINT \n");

            body_sql.append("   FROM TZ_BILLINFO A INNER JOIN TZ_MEMBER B ON A.USERID = B.USERID and a.grcode=b.grcode\n");
            body_sql.append("        INNER JOIN TZ_CODE C ON B.MEMBERGUBUN = C.CODE AND C.GUBUN = '0029' \n");
            if (box.getBoolean("isSubject")) {
                body_sql.append("        INNER JOIN TZ_PROPOSE D ON A.TID = D.TID \n INNER JOIN TZ_SUBJSEQ E ON D.SUBJ = E.SUBJ AND D.SUBJSEQ = E.SUBJSEQ AND D.YEAR = E.YEAR \n INNER JOIN TZ_SUBJ F ON E.SUBJ = F.SUBJ \n");
            }
            body_sql.append("  WHERE A.RESULTCODE <> '01' AND A.GRCODE = B.GRCODE \n");
            body_sql.append("    AND (A.GRCODE, A.TID) IN (SELECT SS.GRCODE, PP.TID \n");
            body_sql.append("                              FROM TZ_SUBJ SB INNER JOIN TZ_SUBJSEQ SS ON SB.SUBJ = SS.SUBJ \n");
            body_sql.append("                               INNER JOIN TZ_PROPOSE PP ON SS.SUBJ = PP.SUBJ AND SS.YEAR = PP.YEAR AND SS.SUBJSEQ = PP.SUBJSEQ \n");
            body_sql.append("                              WHERE (1=1)  \n");

            if (!ss_uclass.equals("ALL")) {
                body_sql.append("                           AND SB.UPPERCLASS = " + SQLString.Format(ss_uclass));
            }
            if (!ss_mclass.equals("ALL")) {
                body_sql.append("                           AND SB.MIDDLECLASS = " + SQLString.Format(ss_mclass));
            }
            if (!ss_lclass.equals("ALL")) {
                body_sql.append("                           AND SB.LOWERCLASS = " + SQLString.Format(ss_lclass));
            }
            if (!ss_grcode.equals("ALL")) {
                body_sql.append("                           AND SS.GRCODE = " + SQLString.Format(ss_grcode));
            }
            if (!(ss_gyear.equals("ALL") || ss_gyear.equals(""))) {
                body_sql.append("                           AND SS.GYEAR = " + SQLString.Format(ss_gyear));
            }
            if (!ss_grseq.equals("ALL")) {
                body_sql.append("                           AND SS.GRSEQ = " + SQLString.Format(ss_grseq));
            }
            if (!v_startappdate.equals("")) {
                body_sql.append("                           AND PP.APPDATE >= " + SQLString.Format(v_startappdate));
            }
            if (!v_endappdate.equals("")) {
                body_sql.append("                           AND PP.APPDATE <= " + SQLString.Format(v_endappdate));
            }

            body_sql.append("                               UNION ALL \n");
            body_sql.append("                               SELECT TB.GRCODE, TB.TID \n");
            body_sql.append("                                 FROM TZ_TAXBILL TB INNER JOIN TZ_SUBJSEQ SS ON TB.GRCODE = SS.GRCODE AND TB.GYEAR = SS.GYEAR AND TB.GRSEQ = SS.GRSEQ \n");
            body_sql.append("                                                    INNER JOIN TZ_SUBJ SJ ON SJ.SUBJ = SS.SUBJ \n");
            body_sql.append("                                WHERE (1 = 1) \n");

            if (!ss_uclass.equals("ALL")) {
                body_sql.append("                           AND SJ.UPPERCLASS = " + SQLString.Format(ss_uclass));
            }
            if (!ss_mclass.equals("ALL")) {
                body_sql.append("                           AND SJ.MIDDLECLASS = " + SQLString.Format(ss_mclass));
            }
            if (!ss_lclass.equals("ALL")) {
                body_sql.append("                           AND SJ.LOWERCLASS = " + SQLString.Format(ss_lclass));
            }
            if (!ss_grcode.equals("ALL")) {
                body_sql.append("                           AND TB.GRCODE = " + SQLString.Format(ss_grcode));
            }
            if (!ss_gyear.equals("ALL")) {
                body_sql.append("                           AND TB.GYEAR = " + SQLString.Format(ss_gyear));
            }
            if (!ss_grseq.equals("ALL")) {
                body_sql.append("                           AND TB.GRSEQ = " + SQLString.Format(ss_grseq));
            }

            body_sql.append("                                )  ");

            if (box.getBoolean("isSubject")) {
                if (!ss_membergubun.equals("ALL")) {
                    body_sql.append("  AND F.AREA = " + SQLString.Format(ss_membergubun));
                }
            } else {
                if (!ss_membergubun.equals("ALL")) {
                    body_sql.append("  AND B.MEMBERGUBUN = " + SQLString.Format(ss_membergubun));
                }
            }

            if (!ss_paymethod.equals("ALL")) {
                body_sql.append("  AND A.PAYMETHOD =" + SQLString.Format(ss_paymethod));
            }

            if (!ss_paystatus.equals("ALL")) {
                body_sql.append(" AND (CASE \n");
                body_sql.append("         WHEN A.CANCELYN = 'Y' \n");
                body_sql.append("            THEN 'R' \n");
                body_sql.append("         WHEN A.CANCELYN = 'N' AND A.RESULTCODE = '00' \n");
                body_sql.append("            THEN 'Y' \n");
                body_sql.append("         WHEN A.CANCELYN = 'N' AND A.RESULTCODE = '99' \n");
                body_sql.append("            THEN 'N' \n");
                body_sql.append("      END) = " + SQLString.Format(ss_paystatus));
            }

            if (!v_startdate.equals("")) {
                body_sql.append("  AND A.PGAUTHDATE >=" + SQLString.Format(v_startdate));
            }
            if (!v_enddate.equals("")) {
                body_sql.append("   AND A.PGAUTHDATE <=" + SQLString.Format(v_enddate));
            }

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                body_sql.append("  AND A.GOODNAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
            }

            if (v_orderColumn.equals("")) {
                order_sql.append(" ORDER BY A.GOODNAME, A.USERID, A.LDATE DESC, A.PGAUTHDATE DESC, A.TID DESC ");
            } else {
                order_sql.append(" ORDER BY " + v_orderColumn + v_orderType + ", A.LDATE DESC, A.TID DESC ");
            }

            sql.append(head_sql.toString() + body_sql.toString() + order_sql.toString());

            ls = connMgr.executeQuery(sql.toString());

            count_sql.append(" SELECT COUNT(*) " + body_sql.toString());

            int total_row_count = 0;

            if (box.getString("excelchk").equals("N")) {
                total_row_count = BoardPaging.getTotalRow(connMgr, count_sql.toString()); //     전체 row 수를 반환한다
                ls.setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다
                ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.
            }

            while (ls.next()) {
                dbox = ls.getDataBox();

                if (box.getString("excelchk").equals("N")) {
                    dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                    dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                    dbox.put("d_rowcount", new Integer(row));
                    dbox.put("d_totalrowcount", new Integer(total_row_count));
                }
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
     * 수강료 결제관리 리스트(Excel)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectExcelList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류
        String ss_paystatus = box.getStringDefault("s_paystatus", "ALL"); //결제상태

        String v_startdate = box.getString("s_startdate");
        String v_enddate = box.getString("s_enddate");
        v_startdate = v_startdate.replace("-", "");
        v_enddate = v_enddate.replace("-", "");

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        String sql = "";

        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            head_sql = " SELECT   a.grcode, a.gyear, a.grseq, c.grseqnm, a.tid, b.goodname, ";
            head_sql += "          (SELECT MIN (edustart) ";
            head_sql += "             FROM tz_subjseq ";
            head_sql += "            WHERE grcode = a.grcode ";
            head_sql += "              AND gyear = a.gyear ";
            head_sql += "              AND grseq = a.grseq) edustart, ";
            head_sql += "          (SELECT MAX (eduend) ";
            head_sql += "             FROM tz_subjseq ";
            head_sql += "            WHERE grcode = a.grcode ";
            head_sql += "              AND gyear = a.gyear ";
            head_sql += "              AND grseq = a.grseq) eduend, ";
            head_sql += "          a.studentcnt, a.price, ";
            head_sql += "          CASE ";
            head_sql += "             WHEN b.cancelyn = 'Y' ";
            head_sql += "                THEN 'R' ";
            head_sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '00' ";
            head_sql += "                THEN 'Y' ";
            head_sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '99' ";
            head_sql += "                THEN 'N' ";
            head_sql += "          END paystatus, ";
            head_sql += "          b.userid, (SELECT NAME ";
            head_sql += "                       FROM tz_member ";
            head_sql += "                      WHERE userid = b.userid and grcode=a.grcode) NAME, b.pgauthdate ";
            body_sql = "     FROM tz_taxbill a INNER JOIN tz_billinfo b ";
            body_sql += "          ON a.grcode = b.grcode AND a.tid = b.tid ";
            body_sql += "          INNER JOIN tz_grseq c ";
            body_sql += "          ON a.grcode = c.grcode AND a.gyear = c.gyear AND a.grseq = c.grseq ";
            body_sql += "    WHERE b.resultcode <> '01' ";
            body_sql += "      AND (a.grcode, a.gyear, a.grseq) IN (SELECT ss.grcode, ss.gyear, ss.grseq ";
            body_sql += "                                             FROM tz_subj sb INNER JOIN tz_subjseq ss ";
            body_sql += "                                                  ON sb.subj = ss.subj ";
            body_sql += "                                            WHERE (1=1) ";

            if (!ss_uclass.equals("ALL")) {
                body_sql += "                                          AND sb.upperclass = " + SQLString.Format(ss_uclass);
            }
            if (!ss_mclass.equals("ALL")) {
                body_sql += "                                          AND sb.middleclass = " + SQLString.Format(ss_mclass);
            }
            if (!ss_lclass.equals("ALL")) {
                body_sql += "                                          AND sb.lowerclass = " + SQLString.Format(ss_lclass);
            }

            body_sql += "                                           ) ";

            if (!ss_grcode.equals("ALL")) {
                body_sql += " AND a.grcode = " + SQLString.Format(ss_grcode);
            }
            if (!ss_gyear.equals("ALL")) {
                body_sql += " AND a.gyear = " + SQLString.Format(ss_gyear);
            }
            if (!ss_grseq.equals("ALL")) {
                body_sql += " AND a.grseq = " + SQLString.Format(ss_grseq);
            }
            if (!ss_paystatus.equals("ALL")) {
                body_sql += " AND (CASE ";
                body_sql += "             WHEN b.cancelyn = 'Y' ";
                body_sql += "                THEN 'R' ";
                body_sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '00' ";
                body_sql += "                THEN 'Y' ";
                body_sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '99' ";
                body_sql += "                THEN 'N' ";
                body_sql += "          END) = " + SQLString.Format(ss_paystatus);
            }

            if (!v_startdate.equals("")) {
                body_sql += "  AND b.pgauthdate >=" + SQLString.Format(v_startdate);
            }
            if (!v_enddate.equals("")) {
                body_sql += "  AND b.pgauthdate <=" + SQLString.Format(v_enddate);
            }

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                body_sql += " and b.goodname like " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n";
            }

            if (v_orderColumn.equals("")) {
                order_sql += " order by ldate desc, b.pgauthdate desc, a.tid desc";
            } else {
                order_sql += " order by " + v_orderColumn + v_orderType + ", ldate desc, a.tid desc ";
            }

            sql = head_sql + body_sql + group_sql + order_sql;

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
     * 결제 정보 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectPayInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";

        String v_tid = box.getString("p_tid");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql += " SELECT a.tid, a.goodname, a.buyername, a.pgauthdate, a.price, c.subjnm, ";
            sql += "        c.biyong, SUM (c.biyong) OVER (PARTITION BY a.tid) total_biyong, a.paymethod,";
            sql += "        (SUM (c.biyong) OVER (PARTITION BY a.tid) - a.price) discount ";
            sql += "   FROM tz_billinfo a INNER JOIN tz_propose b ON a.tid = b.tid ";
            sql += "        INNER JOIN tz_subjseq c ";
            sql += "        ON b.subj = c.subj AND b.YEAR = c.YEAR AND b.subjseq = c.subjseq ";
            sql += "  WHERE a.tid = " + SQLString.Format(v_tid);

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
     * 수강료 결제관리 세부정보 정보 가져오기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public DataBox selectSchoolfeeInfo(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        DataBox dbox = null;

        String sql = "";
        String v_grcode = box.getString("p_grcode");
        String v_tid = box.getString("p_tid");

        try {
            connMgr = new DBConnectionManager();

            sql += " SELECT   a.grcode, a.gyear, a.grseq, c.grseqnm, a.tid, b.goodname, ";
            sql += "          (SELECT MIN (edustart) ";
            sql += "             FROM tz_subjseq ";
            sql += "            WHERE grcode = a.grcode ";
            sql += "              AND gyear = a.gyear ";
            sql += "              AND grseq = a.grseq) edustart, ";
            sql += "          (SELECT MAX (eduend) ";
            sql += "             FROM tz_subjseq ";
            sql += "            WHERE grcode = a.grcode ";
            sql += "              AND gyear = a.gyear ";
            sql += "              AND grseq = a.grseq) eduend, ";
            sql += "          a.studentcnt, a.price, ";
            sql += "          a.note, ";
            sql += "          CASE ";
            sql += "             WHEN b.cancelyn = 'Y' ";
            sql += "                THEN 'R' ";
            sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '00' ";
            sql += "                THEN 'Y' ";
            sql += "             WHEN b.cancelyn = 'N' AND b.resultcode = '99' ";
            sql += "                THEN 'N' ";
            sql += "          END paystatus, ";
            sql += "          b.userid, (SELECT NAME ";
            sql += "                       FROM tz_member ";
            sql += "                      WHERE userid = b.userid and grcode=a.grcode) NAME, b.pgauthdate, b.pgauthtime, ";
            sql += "          b.cancelyn, b.canceldate, b.canceltime,";
            sql += "          b.buyeremail, b.buyertel, b.paymethod ";
            sql += "     FROM tz_taxbill a INNER JOIN tz_billinfo b ";
            sql += "          ON a.grcode = b.grcode AND a.tid = b.tid ";
            sql += "          INNER JOIN tz_grseq c ";
            sql += "          ON a.grcode = c.grcode AND a.gyear = c.gyear AND a.grseq = c.grseq ";
            sql += "    WHERE a.tid = " + SQLString.Format(v_tid);
            sql += "      and a.grcode = " + SQLString.Format(v_grcode);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
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
        return dbox;
    }

    public int update(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        int isOk = 0;

        String v_userid = box.getSession("userid");
        String v_grcode = box.getString("p_grcode");
        String v_tid = box.getString("p_tid");
        String v_paystatus = box.getString("p_paystatus");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            //수강료 결제관리 테이블

            sql = " UPDATE tz_billinfo ";
            sql += "    SET ";

            if ("N".equals(v_paystatus)) { //미결제
                sql += "        resultcode = '99', ";
                sql += "        resultmsg = '[Schoolfee|미결제]', ";
                sql += "        pgauthdate = NULL, ";
                sql += "        pgauthtime = NULL, ";
                sql += "        cancelyn = 'N', ";
                sql += "        cancelresult = NULL, ";
                sql += "        canceldate = NULL, ";
                sql += "        canceltime = NULL, ";
            } else if ("Y".equals(v_paystatus)) {
                sql += "        resultcode = '00', ";
                sql += "        resultmsg = '[Schoolfee|성공]', ";
                sql += "        pgauthdate = TO_CHAR (SYSDATE, 'yyyymmdd'), ";
                sql += "        pgauthtime = TO_CHAR (SYSDATE, 'hh24miss'), ";
                sql += "        cancelyn = 'N', ";
                sql += "        cancelresult = NULL, ";
                sql += "        canceldate = NULL, ";
                sql += "        canceltime = NULL, ";
            } else if ("R".equals(v_paystatus)) {
                sql += "        cancelyn = 'Y', ";
                sql += "        cancelresult = '00', ";
                sql += "        canceldate = TO_CHAR (SYSDATE, 'yyyymmdd'), ";
                sql += "        canceltime = TO_CHAR (SYSDATE, 'hh24miss'), ";
            }

            sql += "        ldate = TO_CHAR (SYSDATE, 'yyyymmddhh24miss'), ";
            sql += "        luserid = ? ";
            sql += "  WHERE grcode = ?  ";
            sql += "    AND tid    = ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_userid);
            pstmt.setString(2, v_grcode);
            pstmt.setString(3, v_tid);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
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
        return isOk;
    }

}