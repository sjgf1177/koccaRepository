// **********************\************************************
// 1. 제 목: 홈페이지 공지n사항
// 2. 프로그램명: HomeNoticeBean.java
// 3. 개 요: 홈페이지 공지사항
// 4. 환 경: JDK 1.0
// 5. 버 젼: 0.1
// 6. 작 성: Administrator 2005.12.18
// 7. 수 정:
//
// **********************************************************

package com.credu.homepage;

import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 * 
 */

public class HomeNoticeBean {

    private final int tabseq = 12; // 테이블 번호
    // private final String type = "HN"; // 테이블 타입

    /**
     * 자료실 테이블번호
     * 
     * @param box receive from the form object and session
     * @return int 자료실 테이블번호
     * @throws Exception
     */
    public int selectTableseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_type = box.getStringDefault("p_type", "");
        String v_grcode = box.getStringDefault("p_grcode", "0000000");
        String v_subj = box.getStringDefault("p_subj", "0000000000");
        String v_year = box.getStringDefault("p_year", "0000");
        String v_subjseq = box.getStringDefault("p_subjseq", "0000");

        try {
            connMgr = new DBConnectionManager();

            sql = " select tabseq from TZ_BDS      ";
            sql += "  where type    = " + StringManager.makeSQL(v_type);
            sql += "    and grcode  = " + StringManager.makeSQL(v_grcode);
            sql += "    and subj    = " + StringManager.makeSQL(v_subj);
            sql += "    and year    = " + StringManager.makeSQL(v_year);
            sql += "    and subjseq = " + StringManager.makeSQL(v_subjseq);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("tabseq");
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
        return result;
    }

    /**
     * 홈페이지 공지사항 메인사용 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 공지사항 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectDirectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        StringBuffer head_sql = new StringBuffer();
        StringBuffer body_sql = new StringBuffer();
        String order_sql = "";
        String order_sql2 = "";
        String body_sql2 = "";
        String count_sql = "";
        DataBox dbox = null;

        int v_pageno = box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        String v_login = "";
        String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        String v_process = box.getStringDefault("p_process", "mainList");
        String v_onoffgubun = box.getStringDefault("p_onoffgubun", "ALL");
        String v_gubun = box.getStringDefault("p_gubun_view", "");

        if (v_process.equals("HELPDESK") || v_process.equals("ONLINE_COURSE") || v_process.equals("OFFLINE_COURSE")) {
            v_pagesize = 5;
        }

        if (box.getSession("userid").equals("")) {
            v_login = "N";
        } else {
            v_login = "Y";
        }

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            if (v_process.equals("mainList")) {
                head_sql.append("SELECT * FROM ( SELECT ROWNUM RNUM, \n");
                order_sql += "    ORDER BY ISALL DESC, SEQ DESC)  WHERE RNUM < 6";

                // 2010.11.04 add
                body_sql2 += " FROM ( SELECT * ";
                order_sql2 += "    ORDER BY ISALL DESC, SEQ DESC) ";

            } else {
                head_sql.append("Select \n");
                order_sql += "     ORDER BY ISALL DESC, SEQ DESC ";
            }

            head_sql.append(" SEQ, ADDATE, ADTITLE, ADCONTENT, ADNAME, CNT, LUSERID, \n");
            head_sql.append(" LDATE, ISALL, USEYN, POPUP, LOGINYN, GUBUN, \n");
            head_sql.append(" USELIST, ADUSERID, FILECNT, ONOFFGUBUN \n");

            body_sql.append(" FROM \n");
            body_sql.append(" (SELECT X.SEQ, X.ADDATE, X.ADTITLE, X.ADNAME, X.CNT, X.LUSERID, \n");
            body_sql.append("		X.LDATE, X.ISALL, X.USEYN, X.POPUP, X.LOGINYN, X.GUBUN, \n");
            body_sql.append("		X.USELIST, X.TABSEQ, X.ADCONTENT, X.ADUSERID, X.GRCODECD, X.ONOFFGUBUN, \n");
            body_sql.append("		(SELECT COUNT(REALFILE) FROM TZ_BOARDFILE WHERE TABSEQ = X.TABSEQ AND SEQ = X.SEQ) FILECNT \n");
            body_sql.append("	 FROM TZ_NOTICE X ) \n");
            body_sql.append("WHERE tabseq = " + tabseq + " \n");
            body_sql.append("  AND USEYN= 'Y' \n");
            body_sql.append("  AND (POPUP = 'N' OR (POPUP = 'Y' AND USELIST='Y') ) ");

            if (v_login.equals("Y")) {
                body_sql.append("      AND ( LOGINYN = 'AL' OR LOGINYN = 'Y' ) \n");
                body_sql.append("      AND GRCODECD LIKE '%" + tem_grcode + "%'    \n");
            } else {
                body_sql.append("      AND ( ( LOGINYN = 'AL' OR LOGINYN = 'N' ) AND (GRCODECD LIKE '%" + tem_grcode + "%') ) \n");
            }

            if (!v_gubun.equals("")) {
                body_sql.append("  AND GUBUN = '" + v_gubun + "'     \n");
            }

            if (!v_searchtext.equals("")) {
                if (v_search.equals("adtitle")) {
                    body_sql.append(" AND ADTITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                } else if (v_search.equals("adcontent")) {
                    body_sql.append(" AND ADCONTENT LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                } else if (v_search.equals("adname")) {
                    body_sql.append(" AND ADNAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                } else {
                    body_sql.append(" AND ADTITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                }
            }

            if (box.get("onoff").equals("1")) {
                body_sql.append(" AND ONOFFGUBUN = 'C' \n");
            } else if (box.get("onoff").equals("0")) {
                body_sql.append(" AND ONOFFGUBUN = 'S' \n");
            }

            if (v_onoffgubun.equals("ON")) {
                body_sql.append(" AND ONOFFGUBUN = 'C' \n");
            } else if (v_onoffgubun.equals("OFF")) {
                body_sql.append(" AND ONOFFGUBUN = 'S' \n");
            }

            // sql= head_sql.toString() + body_sql.toString() + order_sql;
            sql = head_sql.toString() + body_sql2 + body_sql.toString() + order_sql2 + order_sql;

            ls = connMgr.executeQuery(sql);
            count_sql = " SELECT COUNT(*) " + body_sql;

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);
            ls.setPageSize(v_pagesize); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls.next()) {

                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(v_pagesize));
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
     * 홈페이지 공지사항 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 공지사항 리스트
     * @throws Exception
     * 
     *             public ArrayList selectListNoticeHome(RequestBox box) throws Exception { DBConnectionManager connMgr = null; ListSet ls = null; ArrayList
     *             list = null; // 수정일 : 05.11.16 수정자 : 이나연 _ totalcount 하기위한 쿼리수정 String sql = ""; String head_sql = ""; String body_sql = ""; String
     *             group_sql = ""; String order_sql = ""; String count_sql = "";
     * 
     *             NoticeData data = null; DataBox dbox = null;
     * 
     *             int v_tabseq = box.getInt("p_tabseq");
     * 
     *             String v_login = ""; String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode")); String v_searchtext =
     *             box.getString("p_searchtext"); String v_search = box.getString("p_search"); int v_pageno = box.getInt("p_pageno");
     * 
     *             System.out.println(" tmp_grcode box : "+ box.getString("tem_grcode")); System.out.println(" tmp_grcode session : "+
     *             box.getSession("tem_grcode"));
     * 
     *             if(box.getSession("userid").equals("")){ v_login = "N"; }else{ v_login = "Y"; }
     * 
     *             try { connMgr = new DBConnectionManager();
     * 
     *             list = new ArrayList();
     * 
     *             //2005.11.18_하경태 : Oracle -> Mssql rownum 제거 head_sql += " select  \n"; //head_sql += "    rownum,      \n"; head_sql +=
     *             "    seq,         \n"; head_sql += "    addate,      \n"; head_sql += "    adtitle,     \n"; head_sql += "    adname,      \n"; head_sql
     *             += "    cnt,         \n"; head_sql += "    luserid,     \n"; head_sql += "    ldate,       \n"; head_sql += "    isall,       \n";
     *             head_sql += "    useyn,       \n"; head_sql += "    popup,       \n"; head_sql += "    loginyn,     \n"; head_sql +=
     *             "    gubun,       \n"; head_sql += "    uselist,      \n"; head_sql += "    aduserid,     \n"; head_sql += "    filecnt       \n";
     *             body_sql += " from             \n"; body_sql += " (select          \n"; //body_sql += "    rownum,        \n"; body_sql +=
     *             "    x.seq,         \n"; body_sql += "    x.addate,      \n"; body_sql += "    x.adtitle,     \n"; body_sql += "    x.adname,      \n";
     *             body_sql += "    x.cnt,         \n"; body_sql += "    x.luserid,     \n"; body_sql += "    x.ldate,       \n"; body_sql +=
     *             "    x.isall,       \n"; body_sql += "    x.useyn,       \n"; body_sql += "    x.popup,       \n"; body_sql += "    x.loginyn,     \n";
     *             body_sql += "    x.gubun,       \n"; body_sql += "    x.uselist,     \n"; body_sql += "    x.tabseq,      \n"; body_sql +=
     *             "    x.adcontent,   \n"; body_sql += "    x.aduserid,    \n"; body_sql +=
     *             "	(select count(realfile) from tz_boardfile where tabseq = x.TABSEQ and seq = x.seq) filecnt "; body_sql += "  from       \n"; body_sql
     *             += "    TZ_NOTICE x ) a"; body_sql += "  where "; body_sql += "  isall = 'N' "; body_sql += "      and tabseq = " + v_tabseq; body_sql +=
     *             "      and useyn= 'Y'"; body_sql += "      and (popup = 'N' or (popup = 'Y' and uselist='Y') )";
     * 
     * 
     *             if(v_login.equals("Y")){ body_sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )"; body_sql +=
     *             "      and ( gubun = 'N' and grcodecd like '%"+tem_grcode+"%') "; }else{ body_sql +=
     *             "      and ( ( loginyn = 'AL' or loginyn = 'N' ) and (grcodecd like '%"+tem_grcode+"%') )"; }
     * 
     *             if ( !v_searchtext.equals("")) { v_pageno = 1; if (v_search.equals("adtitle")) { body_sql += " and adtitle like " +
     *             StringManager.makeSQL("%" + v_searchtext + "%"); } else if (v_search.equals("adcontents")) { body_sql += " and adcontent like " +
     *             StringManager.makeSQL("%" + v_searchtext + "%"); } else if (v_search.equals("adname")) { body_sql += " and adname like " +
     *             StringManager.makeSQL("%" + v_searchtext + "%"); } } order_sql += " order by seq desc "; sql= head_sql+ body_sql+ group_sql+ order_sql;
     *             System.out.println("홈페이지 공지사항 리스트 sql "+sql);
     * 
     *             ls = connMgr.executeQuery(sql); count_sql= "select count(*) "+ body_sql;
     * 
     *             System.out.println("count_sql "+count_sql);
     * 
     *             int total_row_count = BoardPaging.getTotalRow(connMgr,count_sql); // 전체 row 수를 반환한다 int total_page_count = ls.getTotalPage(); // 전체 페이지
     *             수를 반환한다
     * 
     *             row = 7; ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다 ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
     * 
     *             while (ls.next()) { dbox = ls.getDataBox(); dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
     *             dbox.put("d_totalpage", new Integer(total_page_count)); dbox.put("d_rowcount", new Integer(row)); list.add(dbox); } } catch (Exception
     *             ex) { ErrorManager.getErrorStackTrace(ex, box, sql); throw new Exception("sql = " + sql + "\r\n" + ex.getMessage()); } finally { if(ls !=
     *             null) { try { ls.close(); }catch (Exception e) {} } if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} } }
     *             return list; }
     * 
     * 
     * 
     *             /** 홈페이지 공지사항 상세내용 조회
     * @param box receive from the form object and session
     * @return ArrayList 공지사항 조회
     * @throws Exception
     */
    public DataBox selectViewNotice(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_process = box.getString("p_process");
        String v_seq = box.getString("p_seq");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append(" select                                \n");
            sql.append("   a.seq,                              \n");
            sql.append("   a.gubun,                            \n");
            sql.append("   ltrim(rtrim(startdate)) startdate,  \n");
            sql.append("   ltrim(rtrim(enddate)) enddate,      \n");
            sql.append("   a.addate,                           \n");
            sql.append("   a.adtitle,                          \n");
            sql.append("   a.adname,                           \n");
            sql.append("   a.adcontent,                        \n");
            sql.append("   a.cnt,                              \n");
            sql.append("   a.luserid,                          \n");
            sql.append("   a.ldate,                            \n");
            sql.append("   a.loginyn,                          \n");
            sql.append("   a.useyn,                            \n");
            sql.append("   a.popwidth,                         \n");
            sql.append("   a.popheight,                        \n");
            sql.append("   a.popxpos,                          \n");
            sql.append("   a.popypos,                          \n");
            sql.append("   a.popup,                            \n");
            sql.append("   a.uselist,                          \n");
            sql.append("   a.useframe,                         \n");
            sql.append("   a.isall,                            \n");
            sql.append("   a.aduserid,                         \n");
            sql.append("   a.grcodecd,                         \n");
            sql.append("   b.realfile,                         \n");
            sql.append("   b.savefile,                         \n");
            sql.append("   b.fileseq,                          \n");
            sql.append("   a.onoffgubun                        \n");
            sql.append(" from TZ_NOTICE a , TZ_BOARDFILE B     \n");
            sql.append("  where a.seq    = " + StringManager.makeSQL(v_seq) + " \n");
            sql.append("    and a.tabseq = " + tabseq + " \n");
            sql.append("    and a.tabseq  =  b.tabseq(+) " + " \n");
            sql.append("    and a.seq  =  b.seq(+) ");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                if (!dbox.getString("d_realfile").equals("")) {
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }
            }

            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);

            // 조회수 증가
            if (!v_process.equals("popupview")) {
                connMgr.executeUpdate("update TZ_NOTICE set cnt = cnt + 1 where seq = " + v_seq);
            }

            connMgr.commit();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
        return dbox;
    }

    /**
     * 전체공지 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 전체공지 리스트
     * @throws Exception
     * 
     *             public ArrayList selectListNoticeAll(RequestBox box) throws Exception { DBConnectionManager connMgr = null; ListSet ls = null; ArrayList
     *             list = null; String sql = ""; NoticeData data = null; DataBox dbox = null;
     * 
     *             String v_search = box.getString("p_search"); String v_searchtext = box.getString("p_searchtext"); String s_gadmin =
     *             box.getSession("gadmin"); String v_gadmin = "";
     * 
     *             if(!s_gadmin.equals("")){ v_gadmin = s_gadmin.substring(0, 1); }
     * 
     *             System.out.println(" v_gadmin : "+v_gadmin);
     * 
     *             int v_tabseq = box.getInt("p_tabseq");
     * 
     *             try { connMgr = new DBConnectionManager();
     * 
     *             list = new ArrayList(); sql += " select        \n"; sql += "   rownum,      \n"; sql += "   a.seq,         \n"; sql +=
     *             "   a.addate,      \n"; sql += "   a.adtitle,     \n"; sql += "   a.adname,      \n"; sql += "   a.cnt,         \n"; sql +=
     *             "   a.luserid,     \n"; sql += "   a.ldate,       \n"; sql += "   a.isall,       \n"; sql += "   a.useyn,       \n"; sql +=
     *             "   a.popup,       \n"; sql += "   a.loginyn,     \n"; sql += "   a.gubun,       \n"; sql += "   a.aduserid,     \n"; sql +=
     *             "		(select count(realfile) from tz_boardfile where tabseq = a.TABSEQ and seq = a.seq) filecnt "; sql += " from TZ_NOTICE  a"; sql +=
     *             "    where isall = 'Y'                                                                    ";
     * 
     *             if ( !v_searchtext.equals("")) { if (v_search.equals("adtitle")) { sql += " and adtitle like " + StringManager.makeSQL("%" + v_searchtext
     *             + "%"); } else if (v_search.equals("adcontents")) { sql += " and adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%"); } }
     * 
     *             sql += "      and tabseq = " + v_tabseq; sql +=
     *             "    order by seq desc                                                                    "; ls = connMgr.executeQuery(sql);
     * 
     *             while (ls.next()) { dbox = ls.getDataBox(); list.add(dbox); } } catch (Exception ex) { ErrorManager.getErrorStackTrace(ex, box, sql);
     *             throw new Exception("sql = " + sql + "\r\n" + ex.getMessage()); } finally { if(ls != null) { try { ls.close(); }catch (Exception e) {} }
     *             if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} } } return list; }
     */
}
