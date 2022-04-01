//**********************************************************
//  1. 제      목: 용어사전 관리
//  2. 프로그램명: DicSubjBean.java
//  3. 개      요: 용어사전 관리(과정)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 14
//  7. 수      정:
//**********************************************************

package com.credu.course;

import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

public class DicSubjBean {
    private ConfigSet config;
    private int row;

    public DicSubjBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 용어사전 화면 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 용어사전 리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectListDicSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;
        int v_pageno = box.getInt("p_pageno");

        String v_gubun = "1";

        // String ss_upperclass = box.getString("s_upperclass"); // 과정분류
        // String ss_middleclass = box.getString("s_middleclass"); // 과정분류
        // sString ss_lowerclass = box.getString("s_lowerclass"); // 과정분류
        String ss_subj = box.getString("s_subjcourse"); // 과정코드
        String v_searchtext = box.getString("p_searchtext");

        String v_groups = box.getStringDefault("p_group", ""); // ㄱ,ㄴ,ㄷ ....
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select a.seq seq, a.subj subj, b.subjnm subjnm, a.words words,           ";
            sql += "        a.descs descs, a.groups groups, a.luserid luserid, a.ldate ldate ";
            sql += "   from TZ_DIC a, TZ_SUBJ b, TZ_DICGROUP c                               ";
            sql += "  where a.subj = b.subj                                                  ";
            sql += "    and a.groups = c.groups                                              ";
            sql += "    and a.gubun = " + StringManager.makeSQL(v_gubun);

            if (!ss_subj.equals("")) { // 과정이 있으면
                sql += "  and a.subj   = " + StringManager.makeSQL(ss_subj);
            }

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                sql += " and a.words like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }

            if (!v_groups.equals("")) { //    용어분류로 검색할때
                sql += " and a.groups = " + StringManager.makeSQL(v_groups);
            }
            sql += " order by a.subj asc, a.groups asc, a.words asc ";

            ls = connMgr.executeQuery(sql);

            ls.setPageSize(row); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno); //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //     전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount(); //     전체 row 수를 반환한다
            box.put("total_row_count", String.valueOf(total_row_count)); // 총 갯수를 BOX에 세팅

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
     * 용어사전 화면 리스트(컨텐츠 과정 용어사전)
     * 
     * @param box receive from the form object and session
     * @return ArrayList 용어사전 리스트
     */
    public ArrayList<DataBox> selectListDicSubjStudy(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // 2005.12.06_하경태 :  totalrowcount 세기 위해 쿼리변경
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        // String count_sql = "";
        DataBox dbox = null;
        // int v_pageno = box.getInt("p_pageno");

        String v_gubun = "1";

        // String p_subj = box.getString("p_subj"); // 과정코드
        String v_searchtext = box.getString("p_searchtext");

        String v_groups = box.getStringDefault("p_group", ""); // ㄱ,ㄴ,ㄷ ....

        String p_grocde = box.getString("p_grcode"); // 그룹코드 2010.1.12 원래로직은 p_subj 에 grcode 값을 받아서 쿼리하는 것으로 되어있어서
        // 실제로 subj 코드를 쓰는 로직에서 오류가 발생하여 grcode는 grcode 로 subj 는 subj 로
        // 쓰도록 변경함, 그러나 tz_dic 테이블에 subj 칼럼값은 여전히 grcode 가 들어 있음

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            // 2005.12.06_하경태 : 용어사전 수정 (tz_subj -> tz_grcode 로 교체.)
            head_sql = " select a.seq seq, a.subj subj, b.grcodenm grcodenm, a.words words,  ";
            head_sql += "        a.descs as descs, a.groups groups, a.luserid luserid, a.ldate ldate ";
            body_sql += "   from TZ_DIC a, tz_grcode b, TZ_DICGROUP c ";
            body_sql += "  where a.subj = b.grcode ";
            body_sql += "    and a.groups = c.groups  ";
            body_sql += "    and a.gubun  = " + StringManager.makeSQL(v_gubun);
            body_sql += "    and a.subj   = " + StringManager.makeSQL(p_grocde); //p_subj --> p_grcode

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                body_sql += " and a.words like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }

            if (!v_groups.equals("")) { //    용어분류로 검색할때
                body_sql += " and a.groups = " + StringManager.makeSQL(v_groups);
            }
            order_sql += " order by a.subj asc, a.groups asc, a.words asc ";

            sql = head_sql + body_sql + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            /*
             * 2010.1.12 페이징 삭제 count_sql= "select count(*) " + body_sql; int
             * total_row_count= BoardPaging.getTotalRow(connMgr, count_sql); //
             * 전체 row 수를 반환한다
             * 
             * ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다
             * ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
             * int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다 //int
             * total_row_count = ls.getTotalCount(); // 전체 row 수를 반환한다
             * box.put("total_row_count", String.valueOf(total_row_count)); // 총
             * 갯수를 BOX에 세팅
             */

            while (ls.next()) {
                dbox = ls.getDataBox();

                /*
                 * 2010.1.12 페이징 삭제 dbox.put("d_dispnum", new
                 * Integer(total_row_count - ls.getRowNum() + 1));
                 * dbox.put("d_totalpage", new Integer(total_page_count));
                 * dbox.put("d_rowcount", new Integer(row));
                 */

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
     * 용어사전 화면 리스트(전체 용어사전)
     * 
     * @param box receive from the form object and session
     * @return ArrayList 용어사전 리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectListDicTotal(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;
        int v_pageno = box.getInt("p_pageno");

        String v_gubun = "1";

        // String p_subj = box.getString("p_subj"); // 과정코드
        String v_searchtext = box.getString("p_searchtext");

        String v_groups = box.getStringDefault("p_group", ""); // ㄱ,ㄴ,ㄷ ....
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            //sql  = " select a.seq seq, a.subj subj, b.subjnm subjnm, a.words words,        ";
            sql = " select a.seq seq, a.subj subj, a.words words,                           ";
            sql += "        a.descs descs, a.groups groups, a.luserid luserid, a.ldate ldate ";
            //sql += "   from TZ_DIC a, TZ_SUBJ b, TZ_DICGROUP c                             ";
            sql += "   from TZ_DIC a, TZ_DICGROUP c                                          ";
            sql += "  where                                                                  ";
            //sql += "    a.subj = b.subj                                                    ";
            sql += "    a.groups = c.groups                                                  ";
            sql += "    and a.gubun  = " + StringManager.makeSQL(v_gubun);
            //sql += "    and a.subj   = " + StringManager.makeSQL(p_subj);

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                sql += " and a.words like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }

            if (!v_groups.equals("")) { //    용어분류로 검색할때
                sql += " and a.groups = " + StringManager.makeSQL(v_groups);
            }
            sql += " order by a.groups asc, a.words asc ";
            System.out.println("sql_dictionary=" + sql);

            ls = connMgr.executeQuery(sql);

            ls.setPageSize(row); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno); //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); //     전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount(); //     전체 row 수를 반환한다
            box.put("total_row_count", String.valueOf(total_row_count)); // 총 갯수를 BOX에 세팅

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
     * 용어사전 팝업상세(전체 용어사전)
     * 
     * @param box receive from the form object and session
     * @return ArrayList 용어사전 리스트
     */
    public DataBox selectWordContent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String p_subj = box.getString("p_subj");
        String p_gubun = box.getString("p_gubun");
        String p_seq = box.getString("p_seq");

        try {
            connMgr = new DBConnectionManager();

            sql = " select words, descs from tz_dic ";
            sql += " where ";
            sql += " subj  = " + SQLString.Format(p_subj);
            sql += " and gubun= " + SQLString.Format(p_gubun);
            sql += " and seq = " + SQLString.Format(p_seq);
            System.out.println(sql);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
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

}
