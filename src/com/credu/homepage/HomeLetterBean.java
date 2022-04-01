// **********************************************************
// 1. 제 목: 홈페이지 공지사항
// 2. 프로그램명: HomeLetterBean.java
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
import com.credu.library.ConfigSet;
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
 */
public class HomeLetterBean {
    private ConfigSet config;
    private static int row = 10;

    // private static String v_type = "PQ";
    // private static final String FILE_TYPE = "p_file"; // 파일업로드되는 tag name
    // private static final int FILE_LIMIT = 1; // 페이지에 세팅된 파일첨부 갯수

    public HomeLetterBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다
            row = 10; // 강제로 지정
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

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
        String head_sql = "";
        String body_sql = "";
        String order_sql = "";
        String count_sql = "";
        DataBox dbox = null;

        int v_pageno = box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? row : box.getInt("p_pagesize");
        int v_tabseq = box.getInt("p_tabseq");
        String v_login = "";
        String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_select");
        String v_process = box.getStringDefault("p_process", "List");

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

            head_sql = "Select   \n";

            /*
            if (false && v_process.equals("mainList")) {
                if (tem_type.equals("GA")) { // 게임이라면 3개
                    head_sql += "   top 3  \n";
                } else { // 문화콘텐츠라면 4개
                    head_sql += "   top 4  \n";
                }
            }
            */

            head_sql += "    rownum,      \n";
            head_sql += "    seq,         \n";
            head_sql += "    addate,      \n";
            head_sql += "    adtitle,     \n";
            head_sql += "    adname,      \n";
            head_sql += "    cnt,         \n";
            head_sql += "    luserid,     \n";
            head_sql += "    ldate,       \n";
            head_sql += "    isall,       \n";
            head_sql += "    useyn,       \n";
            head_sql += "    popup,       \n";
            head_sql += "    loginyn,     \n";
            head_sql += "    gubun,       \n";
            head_sql += "    uselist,      \n";
            head_sql += "    aduserid,     \n";
            head_sql += "    filecnt       \n";
            body_sql += " from             \n";
            body_sql += " (select          \n";
            body_sql += "    rownum,        \n";
            body_sql += "    x.seq,         \n";
            body_sql += "    x.addate,      \n";
            body_sql += "    x.adtitle,     \n";
            body_sql += "    x.adname,      \n";
            body_sql += "    x.cnt,         \n";
            body_sql += "    x.luserid,     \n";
            body_sql += "    x.ldate,       \n";
            body_sql += "    x.isall,       \n";
            body_sql += "    x.useyn,       \n";
            body_sql += "    x.popup,       \n";
            body_sql += "    x.loginyn,     \n";
            body_sql += "    x.gubun,       \n";
            body_sql += "    x.uselist,     \n";
            body_sql += "    x.tabseq,      \n";
            body_sql += "    x.adcontent,   \n";
            body_sql += "    x.aduserid,    \n";
            body_sql += "    x.grcodecd,    \n";
            body_sql += "	(select count(realfile) from tz_boardfile where tabseq = x.TABSEQ and seq = x.seq) filecnt ";
            body_sql += "  from       \n";
            body_sql += "    TZ_LETTER x ) a";
            body_sql += "  where ";
            // body_sql += "  isall = 'N' ";
            body_sql += "      tabseq = " + v_tabseq;
            body_sql += "      and useyn= 'Y'";
            body_sql += "      and (popup = 'N' or (popup = 'Y' and uselist='Y') )";

            if (v_login.equals("Y")) {
                body_sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )";
                body_sql += "      and ( gubun = 'N' and grcodecd like '%" + tem_grcode + "%') ";
            } else {
                body_sql += "      and ( ( loginyn = 'AL' or loginyn = 'N' ) and (grcodecd like '%" + tem_grcode + "%') )";
            }

            if (!v_searchtext.equals("")) {

                if (v_search.equals("adtitle")) {
                    body_sql += " and adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("adcontents")) {
                    body_sql += " and adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("adname")) {
                    body_sql += " and adname like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("ALL")) {
                    body_sql += " and ( adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
                    body_sql += " or adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%");
                    body_sql += " or adname like " + StringManager.makeSQL("%" + v_searchtext + "%");
                    body_sql += "     ) ";
                }

            }
            // body_sql += "    and rownum < 4";
            // body_sql += " From TZ_LETTER a,  	 \n";
            // body_sql += " Where  ";
            // body_sql += "      tabseq = " + v_tabseq;
            // body_sql += "      and useyn= 'Y'";
            // body_sql += "      and (popup = 'N' or (popup = 'Y' and uselist='Y') )";
            //
            // if(v_login.equals("Y")){ //로그인을 한경우
            // body_sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )";
            // body_sql += "      and ( gubun = 'N' and grcodecd like '%"+tem_grcode+"%') ";
            // }else{ //로그인하지 않은경우
            // //로그인 전선택되거나 전체가 선택된경우
            // body_sql += "      and ( ( loginyn = 'AL' or loginyn = 'N' ) and grcodecd like '%"+tem_grcode+"%' )";
            // }
            order_sql += "    order by isAll desc, seq desc";

            sql = head_sql + body_sql + order_sql;

            ls = connMgr.executeQuery(sql);
            count_sql = "select count(*) " + body_sql;

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
     *             try { connMgr = new DBConnectionManager(box);
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
     *             += "    TZ_LETTER x ) a"; body_sql += "  where "; body_sql += "  isall = 'N' "; body_sql += "      and tabseq = " + v_tabseq; body_sql +=
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
        String sql = "";
        DataBox dbox = null;
        int v_tabseq = box.getInt("p_tabseq");
        String v_seq = box.getString("p_seq");
        System.out.println("v_seq " + v_seq);
        String v_process = box.getString("p_process");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql += " select  \n";
            sql += "   a.seq,      \n";
            sql += "   a.gubun,    \n";
            sql += "   ltrim(rtrim(startdate)) startdate,  \n";
            sql += "   ltrim(rtrim(enddate)) enddate,      \n";
            sql += "   a.addate,                     \n";
            sql += "   a.adtitle,                    \n";
            sql += "   a.adname,                     \n";
            sql += "   a.adcontent,                  \n";
            sql += "   a.cnt,                        \n";
            sql += "   a.luserid,                    \n";
            sql += "   a.ldate,                      \n";
            sql += "   a.loginyn,                    \n";
            sql += "   a.useyn,                      \n";
            sql += "   a.popwidth,                   \n";
            sql += "   a.popheight,                  \n";
            sql += "   a.popxpos,                    \n";
            sql += "   a.popypos,                    \n";
            sql += "   a.popup,                      \n";
            sql += "   a.uselist,                    \n";
            sql += "   a.useframe,                   \n";
            sql += "   a.isall,                      \n";
            sql += "   a.aduserid,                   \n";
            sql += "   a.grcodecd,                   \n";
            sql += "   b.realfile,                   \n";
            sql += "   b.savefile,                   \n";
            sql += "   b.fileseq                     \n";
            sql += " from TZ_LETTER a , TZ_BOARDFILE B   \n";
            sql += "  where a.seq    = " + StringManager.makeSQL(v_seq);
            sql += "    and a.tabseq = " + v_tabseq;
            sql += "    and a.tabseq  =  b.tabseq(+) ";
            sql += "    and a.seq  =  b.seq(+) ";

            ls = connMgr.executeQuery(sql);
            System.out.println("공지 상세 sql" + sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                realfileVector.addElement(dbox.getString("d_realfile"));
                savefileVector.addElement(dbox.getString("d_savefile"));
                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
            }

            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);

            // 조회수 증가
            if (!v_process.equals("popupview")) {
                connMgr.executeUpdate("update TZ_LETTER set cnt = cnt + 1 where seq = " + v_seq);
            }

            connMgr.commit();
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
     *             try { connMgr = new DBConnectionManager(box);
     * 
     *             list = new ArrayList(); sql += " select        \n"; sql += "   rownum,      \n"; sql += "   a.seq,         \n"; sql +=
     *             "   a.addate,      \n"; sql += "   a.adtitle,     \n"; sql += "   a.adname,      \n"; sql += "   a.cnt,         \n"; sql +=
     *             "   a.luserid,     \n"; sql += "   a.ldate,       \n"; sql += "   a.isall,       \n"; sql += "   a.useyn,       \n"; sql +=
     *             "   a.popup,       \n"; sql += "   a.loginyn,     \n"; sql += "   a.gubun,       \n"; sql += "   a.aduserid,     \n"; sql +=
     *             "		(select count(realfile) from tz_boardfile where tabseq = a.TABSEQ and seq = a.seq) filecnt "; sql += " from TZ_LETTER  a"; sql +=
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
