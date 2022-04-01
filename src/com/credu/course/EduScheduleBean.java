package com.credu.course;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.credu.library.BoardPaging;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

public class EduScheduleBean {

    public EduScheduleBean() {
    }

    /**
     * 교육그룹리스트 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList 교육그룹리스트
     */
    public ArrayList<DataBox> selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null, ls2 = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String countSql = "";
        DataBox dbox = null;

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        String v_month = box.getStringDefault("p_month", "01");
        String v_year = box.getString("p_year");

        if (v_year.equals("")) {
            GregorianCalendar calendar = new GregorianCalendar();
            v_year = Integer.toString(calendar.get(Calendar.YEAR));
        }

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); // 교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); // 년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); // 교육차수
        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); // 과정분류(대)
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); // 과정분류(중)
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); // 과정분류(소)

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            headSql.append(" SELECT  A.SUBJ, B.SUBJNM, A.EDUSTART, A.EDUEND, A.PROPSTART                                 \n");
            headSql.append("         , A.PROPEND, A.BIYONG, A.STUDENTLIMIT                                               \n");
            headSql.append("         , CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.PROPSTART AND A.PROPEND THEN 'Y' \n");
            headSql.append("                ELSE 'N'                                                                     \n");
            headSql.append("         END STATUS                                                                          \n");
            headSql.append("                                                                                             \n");
            bodySql.append(" FROM    TZ_SUBJSEQ A, TZ_SUBJ B                                                             \n");
            bodySql.append(" WHERE   A.SUBJ = B.SUBJ                                                                     \n");
            // bodySql.append(" AND     A.YEAR = ").append(StringManager.makeSQL(v_year));
            bodySql.append(" AND     SUBSTR(A.EDUSTART, 5, 2) = ").append(StringManager.makeSQL(v_month));

            if (!ss_grcode.equals("ALL")) { // 교육그룹
                bodySql.append(" and A.grcode = ").append(SQLString.Format(ss_grcode));
            }
            if (!ss_uclass.equals("ALL")) {
                bodySql.append(" and B.upperclass = ").append(SQLString.Format(ss_uclass));
            }
            if (!ss_mclass.equals("ALL")) {
                bodySql.append(" and B.middleclass = ").append(SQLString.Format(ss_mclass));
            }
            if (!ss_lclass.equals("ALL")) {
                bodySql.append(" and B.lowerclass = ").append(SQLString.Format(ss_mclass));
            }
            if (!ss_gyear.equals("ALL")) {
                bodySql.append(" and A.gyear = ").append(SQLString.Format(ss_gyear));
            }
            if (!ss_grseq.equals("ALL")) {
                bodySql.append(" and A.grseq = ").append(SQLString.Format(ss_grseq));
            }

            sql = headSql.toString() + bodySql.toString();

            ls = connMgr.executeQuery(sql);

            countSql = "select count(*) " + bodySql.toString();

            int total_row_count = BoardPaging.getTotalRow(connMgr, countSql); // 전체 row 수를 반환한다
            // int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(10));
                dbox.put("d_totalrowcount", new Integer(total_row_count));
                list.add(dbox);
            }
        } catch (Exception ex) {
            System.out.println(ex);
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
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
