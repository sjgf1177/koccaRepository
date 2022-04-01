//**********************************************************
//1. 제      목: 무통장입금 관리 (어드민)
//2. 프로그램명: AccountAdminBean.java
//3. 개      요: 게시판
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 하경태 2006.01.10
//7. 수      정:
//**********************************************************
package com.credu.polity;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.credu.common.GetCodenm;
import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

public class AccountAdminBean {

    private ConfigSet config;
    private int row;
    // private static final String FILE_TYPE = "p_file"; //      파일업로드되는 tag name
    // private static final int FILE_LIMIT = 1; //    페이지에 세팅된 파일첨부 갯수

    public AccountAdminBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 리스트화면 select
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectBoardList(RequestBox box) throws Exception {
        // PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        //		2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        int v_pageno = box.getInt("p_pageno");

        String v_grcode = box.getString("s_grcode");
        String v_year = box.getString("s_gyear");
        String v_grseq = box.getStringDefault("s_grseq", "ALL");
        String v_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String v_mclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String v_lclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류
        String v_subjcourse = box.getStringDefault("s_subjcourse", "ALL");
        String v_subjseq = box.getString("s_subjseq");
        // String v_subj		= box.getString("s_subj");
        String v_accountstart = box.getString("s_accountstart");
        String v_accountend = box.getString("s_accountend");
        String v_accountstat = box.getString("s_accountstat");
        System.out.println("v_subjcourse = " + v_subjcourse);
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql += " Select ";
            head_sql += "	b.subj, v.subjnm, b.year, b.subjseq, b.userid, b.subjprice, b.usepoint, b.discountrate, b.paymoney,  ";
            head_sql += "	b.realpaymoney, b.userid, b.accountname, b.paydate, b.paytype, b.paystat,	b.refundstat, b.refundregdate, ";
            head_sql += "	b.refundbank, b.refundaccount, b.refundmoney, b.refunddate, v.propstart, v.propend, ";
            head_sql += "	v.edustart, v.eduend, m.name, p.appdate, v.course, v.coursenm, v.isbelongcourse, v.subjcnt ";
            body_sql += " From TZ_BILLING b ";
            body_sql += "	join VZ_SCSUBJSEQ v on v.scsubj = b.subj and v.year = b.year and v.subjseq = b.subjseq ";
            body_sql += " 	join TZ_MEMBER M on m.userid = b.userid ";
            body_sql += " 	join TZ_PROPOSE P on p.subj = v.subj and p.year = v.year ";
            body_sql += "		and p.subjseq = v.subjseq and p.userid = b.userid ";
            body_sql += " Where paytype = 'C' ";
            body_sql += "	and v.grcode = " + SQLString.Format(v_grcode);
            body_sql += " 	and v.gyear = " + SQLString.Format(v_year);

            if (!v_grseq.equals("ALL")) {
                sql += "    and v.grseq=" + SQLString.Format(v_grseq);
            }

            //				검색방식에 따른 과정 검색조건 분기
            if (!v_uclass.equals("ALL")) {
                body_sql += " and v.scupperclass ='" + v_uclass + "' ";
            }
            if (!v_mclass.equals("ALL")) {
                body_sql += " and v.scmiddleclass='" + v_mclass + "' ";
            }
            if (!v_lclass.equals("ALL")) {
                body_sql += " and v.sclowerclass ='" + v_lclass + "' ";
            }
            if (!v_subjcourse.equals("ALL")) {
                body_sql += " and v.scsubj='" + v_subjcourse + "' ";
            }
            if (!v_subjseq.equals("ALL")) {
                body_sql += " and v.scsubjseq='" + v_subjseq + "' ";
            }
            if (!v_accountstat.equals("ALL")) {
                body_sql += " and b.paystat='" + v_accountstat + "' ";
            }
            if (!v_accountstart.equals("")) {
                body_sql += " and substring(b.ldate , 1,8)  >= to_date(" + v_accountstart + ",'YYYYMMDD')  ";
            }
            if (!v_accountend.equals("")) {
                body_sql += " and substring(b.ldate , 1,8) <=  to_date(" + v_accountend + ",'YYYYMMDD') ";
            }

            order_sql += " order by b.ldate desc ";

            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);

            System.out.println("selectBoardList.sql = " + sql);
            count_sql = "select count(*) " + body_sql;

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_total_rowcount", new Integer(total_row_count));
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
     * 리스트화면 select
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectExcelBoardList(RequestBox box) throws Exception {
        // PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        // 2005.11.15_하경태 : TotalCount 관련 쿼리 수정 
        String sql = "";
        // String count_sql  = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        // int 	v_pageno 	= box.getInt("p_pageno");

        String v_grcode = box.getString("s_grcode");
        String v_year = box.getString("s_gyear");
        String v_grseq = box.getStringDefault("s_grseq", "ALL");
        String v_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String v_mclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String v_lclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류
        String v_subjcourse = box.getStringDefault("s_subjcourse", "ALL");
        String v_subjseq = box.getString("s_subjseq");
        // String v_subj = box.getString("s_subj");
        String v_accountstart = box.getString("s_accountstart");
        String v_accountend = box.getString("s_accountend");
        String v_accountstat = box.getString("s_accountstat");
        System.out.println("v_subjcourse = " + v_subjcourse);
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql += " Select ";
            head_sql += "	b.subj, v.subjnm, b.year, b.subjseq, b.userid, b.subjprice, b.usepoint, b.discountrate, b.paymoney,  ";
            head_sql += "	b.realpaymoney, b.userid, b.accountname, b.paydate, b.paytype, b.paystat,	b.refundstat, b.refundregdate, ";
            head_sql += "	b.refundbank, b.refundaccount, b.refundmoney, b.refunddate, v.propstart, v.propend, ";
            head_sql += "	v.edustart, v.eduend, m.name, p.appdate ";
            body_sql += " From TZ_BILLING b ";
            body_sql += "	join VZ_SCSUBJSEQ v on v.scsubj = b.subj and v.year = b.year and v.subjseq = b.subjseq ";
            body_sql += " 	join TZ_MEMBER M on m.userid = b.userid ";
            body_sql += " 	join TZ_PROPOSE P on p.subj = v.subj and p.year = v.year ";
            body_sql += "		and p.subjseq = v.subjseq and p.userid = b.userid ";
            body_sql += " Where paytype = 'C' ";
            body_sql += "	and v.grcode = " + SQLString.Format(v_grcode);
            body_sql += " 	and v.gyear = " + SQLString.Format(v_year);

            if (!v_grseq.equals("ALL")) {
                sql += "    and v.grseq=" + SQLString.Format(v_grseq);
            }

            // 				검색방식에 따른 과정 검색조건 분기
            if (!v_uclass.equals("ALL")) {
                body_sql += " and v.scupperclass ='" + v_uclass + "' ";
            }
            if (!v_mclass.equals("ALL")) {
                body_sql += " and v.scmiddleclass='" + v_mclass + "' ";
            }
            if (!v_lclass.equals("ALL")) {
                body_sql += " and v.sclowerclass ='" + v_lclass + "' ";
            }
            if (!v_subjcourse.equals("ALL")) {
                body_sql += " and v.scsubj='" + v_subjcourse + "' ";
            }
            if (!v_subjseq.equals("ALL")) {
                body_sql += " and v.scsubjseq='" + v_subjseq + "' ";
            }
            if (!v_accountstat.equals("ALL")) {
                body_sql += " and b.paystat='" + v_accountstat + "' ";
            }
            if (!v_accountstart.equals("")) {
                body_sql += " and substring(b.ldate , 1,8)  >= to_date(" + v_accountstart + ",'YYYYMMDD')  ";
            }
            if (!v_accountend.equals("")) {
                body_sql += " and substring(b.ldate , 1,8) <=  to_date(" + v_accountend + ",'YYYYMMDD') ";
            }

            order_sql += " order by b.ldate desc ";

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
     * 집단 환불 처리
     * 
     * @param box receive from the form object and session
     * @return ArrayList 자료실 리스트
     * @throws Exception
     */
    public int AccountAdminInsert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        String v_paydate = "";
        String v_paystat = "";
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";
        String v_userid = "";
        String v_grtype = "";

        String v_chkvalue = box.getString("p_chkvalue");
        String v_grcode = box.getString("s_grcode");
        // int v_chknum = box.getInt("p_chknum");

        //String[] arr_tmp	= null;
        //String[] arr_value	= null;

        // int i = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////////

            v_grtype = GetCodenm.get_grtype(box, v_grcode);

            StringTokenizer arr_tmp = new StringTokenizer(v_chkvalue, "|");

            sql = " Update TZ_BILLING ";
            sql += " Set paydate = ?, paystat = ? ";
            sql += " Where subj = ? and year = ? and subjseq = ? and userid = ? and grtype = ? ";

            pstmt = connMgr.prepareStatement(sql);

            while (arr_tmp.hasMoreTokens() && arr_tmp.hasMoreTokens()) {
                StringTokenizer arr_value = new StringTokenizer(arr_tmp.nextToken(), ",");
                while (arr_value.hasMoreTokens() && arr_value.hasMoreTokens()) {
                    v_paydate = arr_value.nextToken();
                    v_paystat = arr_value.nextToken();
                    v_userid = arr_value.nextToken();
                    v_subj = arr_value.nextToken();
                    v_year = arr_value.nextToken();
                    v_subjseq = arr_value.nextToken();

                    pstmt.setString(1, v_paydate);
                    pstmt.setString(2, v_paystat);
                    pstmt.setString(3, v_subj);
                    pstmt.setString(4, v_year);
                    pstmt.setString(5, v_subjseq);
                    pstmt.setString(6, v_userid);
                    pstmt.setString(7, v_grtype);

                    isOk = pstmt.executeUpdate();
                }
            }
            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
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