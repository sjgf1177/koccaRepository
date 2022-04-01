// **********************************************************
// 1. 제 목: 지식공유 게시판 관리
// 2. 프로그램명: KnowBoardUserBean.java
// 3. 개 요: 지식공유 관리
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0 QnA
// 6. 작 성: 정은년 2005. 9. 1
// 7. 수 정: 이나연 05.11.16 _ connMgr.setOracleCLOB 수정
// **********************************************************
package com.credu.homepage;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class _KnowBoardUserBean {

    public _KnowBoardUserBean() {

        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // 이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ConfigSet config;
    // private static final String BOARD_TYPE = "KB";
    private static final int BOARD_TABSEQ = 7;
    private static final String FILE_TYPE = "p_file"; // 파일업로드되는 tag name
    private static final int FILE_LIMIT = 5; // 페이지에 세팅된 파일첨부 갯수
    private int row;

    /**
     * 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 지식공유 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        String count_sql = "";

        // String sql1 = "";
        DataBox dbox = null;

        // String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        String v_listgubun = box.getString("p_listgubun"); // 리스트 구분
        String v_categorycd = box.getString("p_categorycd"); // 카테고리별
        String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        int v_pageno = box.getInt("p_pageno");
        // String v_cate = box.getString("tab"); // 인기지식 카테고리보기

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            head_sql += " select * ";
            body_sql += " from vz_factory where (1=1) ";
            /*
             * head_sql +=
             * " select a.seq, a.types, a.title, a.contents, a.indate, a.inuserid, a.ldate, b.name,a.cnt, c.subjnm, isnull(a.recommend, 0) recommend , OKYN1,  "
             * ; head_sql += "		(select classname from tz_knowcode where subjclass=a.categorycd  and grcode=a.grcode) categorynm,  "; head_sql +=
             * "		(select count(realfile) from tz_homefile where tabseq = a.TABSEQ and seq = a.seq ) filecnt, "; head_sql +=
             * "        (select count(commentseq) ccnt from TZ_COMMENTQNA where tabseq='7' and seq=a.seq) commentcnt    "; body_sql +=
             * "   from TZ_HOMEQNA a, tz_member b, tz_subj c "; body_sql +=
             * "  where a.inuserid  =  b.userid (+) and a.grcode='N000001' and a.categorycd = c.subj  "; body_sql += "  and a.types=0 and tabseq = " +
             * BOARD_TABSEQ;
             */

            // 최근지식 리스트
            if (!v_categorycd.equals("")) {
                if (v_categorycd.equals("MINE")) {
                    body_sql += "  and userid = '" + box.getSession("userid") + "' ";
                } else {
                    body_sql += "  and area = '" + v_categorycd + "' ";
                }
            }

            // 인기지식 리스트
            if (v_listgubun.equals("")) {
                if (!v_searchtext.equals("")) { // 검색어가 있으면
                    // v_pageno = 1; // 검색할 경우 첫번째 페이지가 로딩된다
                    if (v_select.equals("title")) { // 제목으로 검색할때
                        body_sql += " and  (lower(title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%") + ") or ";
                        body_sql += " subjnm like " + StringManager.makeSQL("%" + v_searchtext + "%") + ")";
                    } else if (v_select.equals("subj")) { // 내용으로 검색할때
                        body_sql += " and subjnm like " + StringManager.makeSQL("%" + v_searchtext + "%"); // Oracle 9i 용
                    } else if (v_select.equals("name")) { // 이름으로 검색할때
                        body_sql += " and lower(name) like lower (" + StringManager.makeSQL("%" + v_searchtext + "%") + ")"; // Oracle 9i 용
                    }
                }
                order_sql += " order by indate desc ";

            }

            /*
             * else if(v_listgubun.equals("popcnt_list")){ //*** 최다조회 order_sql += " order by cnt desc "; }else if(v_listgubun.equals("poprec_list")){ //***
             * 최다추천 order_sql += " order by recommend desc "; }else if(v_listgubun.equals("popcom_list")){ //*** 최다댓글 order_sql +=
             * " order by commentcnt desc "; }
             */
            sql = head_sql + body_sql + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);
            System.out.println("sql : " + sql);

            count_sql = "select count(*) " + body_sql;
            System.out.println("sql : " + count_sql);

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); // 전체 row 수를 반환한다

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
     * 리스트 추천 순서
     * 
     * @param box receive from the form object and session
     * @return ArrayList 지식공유 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectListRecTop(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        String count_sql = "";

        // String sql1 = "";
        DataBox dbox = null;

        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        // String v_listgubun = box.getString("p_listgubun"); // 리스트 구분
        // String v_categorycd = box.getString("p_categorycd"); // 카테고리별
        // String v_searchtext = box.getString("p_searchtext");
        // String v_select = box.getString("p_select");
        int v_pageno = 1;
        // String v_cate = box.getString("tab"); // 인기지식 카테고리보기

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            head_sql += " select a.seq, a.types, a.title, a.contents, a.indate, a.inuserid, a.ldate, b.name,a.cnt, isnull(a.recommend, 0) recommend , OKYN1,  ";
            head_sql += "		(select classname from tz_knowcode where subjclass=a.categorycd  and grcode=a.grcode) categorynm,  ";
            head_sql += "		(select count(realfile) from tz_homefile where tabseq = a.TABSEQ and seq = a.seq ) filecnt, ";
            head_sql += "        (select count(commentseq) ccnt from TZ_COMMENTQNA where tabseq='" + BOARD_TABSEQ + "' and seq=a.seq) commentcnt    ";
            body_sql += "   from TZ_HOMEQNA a, tz_member b";
            body_sql += "  where a.inuserid  =  b.userid (+) and a.grcode='" + s_grcode + "'  ";
            body_sql += "  and a.types=0 and tabseq = " + BOARD_TABSEQ;
            order_sql += " order by recommend desc ";

            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);
            System.out.println("sql : " + sql);

            count_sql = "select count(*) " + body_sql;
            System.out.println("scount_Sql : " + count_sql);

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); // 전체 row 수를 반환한다

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
     * 리스트 추천 순서
     * 
     * @param box receive from the form object and session
     * @return ArrayList 지식공유 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectListReplyTop(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        String count_sql = "";

        // String sql1 = "";
        DataBox dbox = null;

        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        // String v_listgubun = box.getString("p_listgubun"); // 리스트 구분
        // String v_categorycd = box.getString("p_categorycd"); // 카테고리별
        // String v_searchtext = box.getString("p_searchtext");
        // String v_select = box.getString("p_select");
        int v_pageno = 1;
        // String v_cate = box.getString("tab"); // 인기지식 카테고리보기

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            head_sql += " select a.seq, a.types, a.title, a.contents, a.indate, a.inuserid, a.ldate, b.name,a.cnt, isnull(a.recommend, 0) recommend , OKYN1,  ";
            head_sql += "		(select classname from tz_knowcode where subjclass=a.categorycd  and grcode=a.grcode) categorynm,  ";
            head_sql += "		(select count(realfile) from tz_homefile where tabseq = a.TABSEQ and seq = a.seq ) filecnt, ";
            head_sql += "        (select count(commentseq) ccnt from TZ_COMMENTQNA where tabseq='" + BOARD_TABSEQ + "' and seq=a.seq) commentcnt    ";
            body_sql += "   from TZ_HOMEQNA a, tz_member b";
            body_sql += "  where a.inuserid  =  b.userid (+) and a.grcode='" + s_grcode + "'  ";
            body_sql += "  and a.types=0 and tabseq = " + BOARD_TABSEQ;
            order_sql += " order by commentcnt desc ";

            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);
            System.out.println("sql : " + sql);

            count_sql = "select count(*) " + body_sql;
            System.out.println("sql : " + count_sql);

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); // 전체 row 수를 반환한다

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
     * 카테고리 트리 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     */
    public ArrayList<DataBox> SelectCategoryTreeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list1 = null;
        String sql = "";
        DataBox dbox = null;
        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
        try {
            // dir :디렉토리여부[true/false]
            // menuno :코드 subjclass
            // target :대상프레임
            // desc :코드명 classname
            // root :ROOT코드
            // parent :PARENT코드
            // level :트리위치(0부터)
            // subcnt :자식갯수
            // viewname :카테고리선택 뷰

            sql = " select a.subjclass, a.classname, RPAD(a.upperclass,9,'0') root, ";
            sql += "       (case when a.middleclass='000' and  a.lowerclass='000' then a.subjclass  ";
            sql += "        when a.middleclass!='000' and  a.lowerclass='000' then RPAD(a.upperclass,9,'0') ";
            sql += "        else RPAD(a.upperclass||a.middleclass,9,'0') ";
            sql += "        end) parent, ";
            sql += "       (case when a.middleclass='000' and  a.lowerclass='000' then 0 ";
            sql += "        when a.middleclass!='000' and  a.lowerclass='000' then 1 ";
            sql += "        else 2 ";
            sql += "        end) levels, ";
            sql += "       (case when a.middleclass='000' and  a.lowerclass='000'  "; // --대분류
            sql += "         then (select count(*) from tz_knowcode where upperclass=a.upperclass and middleclass!='000' and lowerclass='000' and grcode='"
                    + s_grcode + "')  ";
            sql += "        when a.middleclass!='000' and  a.lowerclass='000'      "; // --중분류
            sql += "         then (select count(*) from tz_knowcode where upperclass=a.upperclass and middleclass=a.middleclass and lowerclass != '000' and grcode='"
                    + s_grcode + "')  ";
            sql += "        else 0                                                 "; // --소분류
            sql += "        end ";
            sql += "       ) subcnt, ";
            sql += "       (case when a.middleclass='000' and  a.lowerclass='000' then a.classname  ";
            sql += "        when a.middleclass!='000' and  a.lowerclass='000'  ";
            sql += "         then (select classname from tz_knowcode where  grcode='" + s_grcode
                    + "' and subjclass=a.upperclass||'000000')||'>'||a.classname  ";
            sql += "        else  ";
            sql += "         (select classname from tz_knowcode where  grcode='" + s_grcode
                    + "' and subjclass=a.upperclass||'000000')||'>'||(select classname from tz_knowcode where  grcode='" + s_grcode
                    + "' and subjclass=a.upperclass||a.middleclass||'000')||'>'||a.classname  ";
            sql += "        end  ";
            sql += "        ) viewname  ";
            sql += "from tz_knowcode a where a.grcode='" + s_grcode + "' order by subjclass ";

            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list1.add(dbox);
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
        return list1;
    }

    /**
     * 메뉴 카테고리 트리 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 리스트
     */
    public static ArrayList<DataBox> SelectMenuCategoryTreeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list1 = null;
        String sql = "";
        DataBox dbox = null;

        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        try {
            // dir :디렉토리여부[true/false]
            // menuno :코드 subjclass
            // target :대상프레임
            // desc :코드명 classname
            // root :ROOT코드
            // parent :PARENT코드
            // level :트리위치(0부터)
            // subcnt :자식갯수
            // viewname :카테고리선택 뷰

            sql = " select a.subjclass, a.classname, RPAD(a.upperclass,9,'0') root, ";
            sql += "       (case when a.middleclass='000' and  a.lowerclass='000' then a.subjclass  ";
            sql += "        when a.middleclass!='000' and  a.lowerclass='000' then RPAD(a.upperclass,9,'0') ";
            sql += "        else RPAD(a.upperclass||a.middleclass,9,'0') ";
            sql += "        end) parent, ";
            sql += "       (case when a.middleclass='000' and  a.lowerclass='000' then 0 ";
            sql += "        when a.middleclass!='000' and  a.lowerclass='000' then 1 ";
            sql += "        else 2 ";
            sql += "        end) levels, ";
            sql += "       (case when a.middleclass='000' and  a.lowerclass='000'  "; // --대분류
            sql += "         then (select count(*) from tz_knowcode where upperclass=a.upperclass and middleclass!='000' and lowerclass='000' and grcode='"
                    + s_grcode + "')  ";
            sql += "        when a.middleclass!='000' and  a.lowerclass='000'      "; // --중분류
            sql += "         then (select count(*) from tz_knowcode where upperclass=a.upperclass and middleclass=a.middleclass and lowerclass != '000' and grcode='"
                    + s_grcode + "')  ";
            sql += "        else 0                                                 "; // --소분류
            sql += "        end ";
            sql += "       ) subcnt, ";
            sql += "       (case when a.middleclass='000' and  a.lowerclass='000'then a.classname  ";
            sql += "        when a.middleclass!='000' and  a.lowerclass='000'  ";
            sql += "         then (select classname from tz_knowcode where  grcode='" + s_grcode
                    + "' and subjclass=a.upperclass||'000000')||'>'||a.classname  ";
            sql += "        else  ";
            sql += "         (select classname from tz_knowcode where  grcode='" + s_grcode
                    + "' and subjclass=a.upperclass||'000000')||'>'||(select classname from tz_knowcode where  grcode='" + s_grcode
                    + "' and subjclass=a.upperclass||a.middleclass||'000')||'>'||a.classname  ";
            sql += "        end  ";
            sql += "        ) viewname  ";
            sql += "from tz_knowcode a  where a.grcode='" + s_grcode + "' ";
            sql += " order by subjclass ";

            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            // ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return list1;
    }

    /**
     * 등록할때(질문)
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertKnowBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        // String sql2 = "";
        int isOk1 = 1;
        // int isOk2 = 1;
        int v_cnt = 0;
        String v_catecd = box.getString("p_catecd"); // 카테고리
        String v_title = box.getString("p_title");
        String v_contents = StringManager.replace(box.getString("content"), "<br>", "\n");
        String v_types = "0";
        String s_userid = "";
        // String s_usernm = box.getSession("name");
        // String s_gadmin = box.getSession("gadmin");
        // String v_type = box.getStringDefault("p_type", "HQ");
        String s_grcode = box.getSession("tem_grcode");
        // if (s_gadmin.equals("A1")){
        // s_userid = "운영자";
        // }else{
        s_userid = box.getSession("userid");
        // }
        String v_isopen = "Y";
        // Vector newFileNames = box.getNewFileNames("p_file");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ---------------------- 어떤게시판인지정보를 가져와 tabseq를 리턴한다 ----------------------------
            // sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            // ls = connMgr.executeQuery(sql);
            // ls.next();
            // int v_tabseq = ls.getInt(1);
            // ls.close();
            // ------------------------------------------------------------------------------------
            // ---------------------- 게시판 번호 가져온다 ----------------------------
            sql = "select isnull(max(seq), 0) from TZ_HOMEQNA where tabseq = '" + BOARD_TABSEQ + "'";
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_seq = ls.getInt(1) + 1;
            ls.close();

            // //////////////////////////////// 게시판 table 에 입력 ///////////////////////////////////////////////////////////////////
            sql1 = "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate, grcode, cnt, categorycd)                      ";
            // sql1 +=
            // "                values (?, ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?) ";
            sql1 += "                values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?) ";

            int index = 1;
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(index++, BOARD_TABSEQ);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setString(index++, v_types);
            pstmt1.setString(index++, v_title);
            pstmt1.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, v_isopen);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_grcode);
            pstmt1.setInt(index++, v_cnt);
            pstmt1.setString(index++, v_catecd);

            isOk1 = pstmt1.executeUpdate();
            this.insertUpFile(connMgr, BOARD_TABSEQ, v_seq, v_types, box);
            // sql1 = "select contents from TZ_HOMEQNA where tabseq = '"+BOARD_TABSEQ+"' and seq = '"+v_seq+"' and types = '" + v_types+ "' ";
            // connMgr.setOracleCLOB(sql1, v_contents);

            if (isOk1 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1;
    }

    /**
     * 선택된 자료실 게시물 상세내용 select
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public DataBox SelectView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        int v_tabseq = box.getInt("p_tabseq");
        String v_types = box.getString("p_types");
        String v_indate = box.getString("p_indate");
        String v_userid = box.getString("p_userid");
        // String v_fileseq = box.getString("p_fileseq");
        // int v_upfilecnt = (box.getInt("p_upfilecnt") > 0 ? box.getInt("p_upfilecnt") : 1);

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        // int[] fileseq = new int[v_upfilecnt];

        try {
            connMgr = new DBConnectionManager();

            if (v_types.equals("A")) { // 자료방
                sql = "select a.tabseq, a.seq, a.userid, a.title, a.content, d.area, b.fileseq, b.realfile, b.savefile, a.indate, a.okyn1 ,a.name, a.cnt, d.subjnm ";
                sql += " from tz_board a, tz_boardfile b,  tz_subj d , tz_bds e ";
                sql += " where a.tabseq  =  b.tabseq(+) and a.seq  =  b.seq(+) and a.tabseq = e.tabseq  ";
                // sql += " and a.userid  =  c.userid ";
                sql += " and e.subj  =  d.subj ";
                sql += " and  a.seq = " + v_seq + " and a.tabseq=" + v_tabseq;
                /*
                 * sql =
                 * "select a.tabseq, a.seq, a.userid, a.title, a.content, d.area, b.fileseq, b.realfile, b.savefile, a.indate, a.okyn1 ,c.name, a.cnt, d.subjnm "
                 * ; sql += " from tz_board a, tz_boardfile b, tz_member c, tz_subj d , tz_bds e "; sql +=
                 * " where a.tabseq  =  b.tabseq(+) and a.seq  =  b.seq(+) and a.tabseq = e.tabseq  "; sql += " and a.userid  =  c.userid "; sql +=
                 * " and e.subj  =  d.subj "; sql += " and  a.seq = "+ v_seq +" and a.tabseq="+ v_tabseq ;
                 */
                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    dbox = ls.getDataBox();
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }

                String s_categorynm = SelectCategoryPathnm(connMgr, dbox.getString("d_categorycd"));
                dbox.put("d_categorynm", s_categorynm);

                if (realfileVector != null)
                    dbox.put("d_realfile", realfileVector);
                if (savefileVector != null)
                    dbox.put("d_savefile", savefileVector);
                if (fileseqVector != null)
                    dbox.put("d_fileseq", fileseqVector);

                sql = "update tz_board set cnt = cnt + 1 where tabseq = " + v_tabseq + " and seq = " + v_seq;
                connMgr.executeUpdate(sql);

            } else if (v_types.equals("B")) { // 지식팩토리 게시물
                sql = "select a.types, a.seq, a.inuserid, a.title, a.contents content, d.area, b.fileseq, b.realfile, b.savefile, a.indate, a.okyn1 ,c.name, a.cnt, isnull(a.recommend,0) recommend, d.subjnm ";
                sql += " from tz_homeqna a, tz_homefile b, tz_member c, tz_subj d ";
                sql += " where a.tabseq  =  b.tabseq(+) and a.seq  =  b.seq(+) and a.types  =  b.types(+) ";
                sql += " and a.inuserid  =  c.userid(+) ";
                sql += " and a.categorycd  =  d.subj ";
                sql += "and a.tabseq = '" + BOARD_TABSEQ + "' and a.seq = " + v_seq;

                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    dbox = ls.getDataBox();
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }

                String s_categorynm = SelectCategoryPathnm(connMgr, dbox.getString("d_categorycd"));
                dbox.put("d_categorynm", s_categorynm);

                if (realfileVector != null)
                    dbox.put("d_realfile", realfileVector);
                if (savefileVector != null)
                    dbox.put("d_savefile", savefileVector);
                if (fileseqVector != null)
                    dbox.put("d_fileseq", fileseqVector);

                sql = "update tz_homeqna set cnt = cnt + 1 where tabseq = " + BOARD_TABSEQ + " and seq = " + v_seq;
                connMgr.executeUpdate(sql);
            } else if (v_types.equals("C")) { // Q/A 과정 질문방
                sql = "select  a.seq, a.inuserid, a.title, a.contents content, d.area, b.fileseq, b.realfile, b.savefile, a.indate, a.okyn1 ,c.name, a.cnt,  d.subjnm ";
                sql += " from tz_qna a, tz_qnafile b, tz_member c, tz_subj d ";
                sql += " where a.subjseq  =  b.subjseq(+) and a.seq  =  b.seq(+) and a.year  =  b.year(+) and a.subj = b.subj(+) ";
                sql += " and a.inuserid  =  c.userid(+) ";
                sql += " and a.subj  =  d.subj ";
                sql += " and a.indate = '" + v_indate + "' and a.inuserid ='" + v_userid + "'";

                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    dbox = ls.getDataBox();
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }

                String s_categorynm = SelectCategoryPathnm(connMgr, dbox.getString("d_categorycd"));
                dbox.put("d_categorynm", s_categorynm);

                if (realfileVector != null)
                    dbox.put("d_realfile", realfileVector);
                if (savefileVector != null)
                    dbox.put("d_savefile", savefileVector);
                if (fileseqVector != null)
                    dbox.put("d_fileseq", fileseqVector);

                sql = "update tz_qna set cnt = cnt + 1 where indate = " + v_indate + " and seq = " + v_seq;
                connMgr.executeUpdate(sql);
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

    /**
     * 선택된 자료실 게시물 상세내용 select
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public DataBox selectAns(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        StringBuffer selectSql = new StringBuffer();
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        String s_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();
            // ---------------------- 어떤게시판인지정보를 가져와 tabseq를 리턴한다 ----------------------------

            // ------------------------------------------------------------------------------------
            selectSql.append("select a.types, a.seq, a.inuserid, a.title, a.contents, a.categorycd, a.indate ,c.name, a.cnt, ");
            selectSql.append(" a.isopen, b.realfile, b.savefile, b.fileseq ");
            selectSql.append(" from tz_homeqna a, tz_homefile b, tz_member c ");

            // 수정일 : 05.11.09 수정자 : 이나연 _(+) 수정
            selectSql.append(" where a.tabseq  =  b.tabseq(+) and a.seq  =  b.seq(+) and a.types  =  b.types(+) ");
            selectSql.append(" and a.inuserid  =  c.userid(+) ");
            selectSql.append(" and a.tabseq ='7' and a.grcode  = " + SQLString.Format(s_grcode));
            selectSql.append(" and  a.seq = " + v_seq + " and a.types = 1 ");

            sql = selectSql.toString();

            ls = connMgr.executeQuery(sql);

            System.out.println(sql);

            while (ls.next()) {
                // ------------------- 2003.12.25 변경 -------------------------------------------------------------------
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

    /**
     * 선택된 자료실 게시물 상세내용 과정질문방
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public DataBox selectAns1(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        StringBuffer selectSql = new StringBuffer();
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_subjyear = box.getString("p_subjyear");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        // String s_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();
            // ---------------------- 어떤게시판인지정보를 가져와 tabseq를 리턴한다 ----------------------------

            // ------------------------------------------------------------------------------------
            selectSql.append("select a.inuserid, a.title, a.contents,  a.indate , ");
            selectSql.append(" b.realfile, b.savefile, b.fileseq ");
            selectSql.append(" from tz_qna a, tz_qnafile b ");

            // 수정일 : 05.11.09 수정자 : 이나연 _(+) 수정
            selectSql.append(" where a.subj  =  b.subj(+) and a.subjseq  =  b.subjseq(+) and a.year  =  b.year(+) and a.seq  =  b.seq(+)  ");
            selectSql.append(" and  a.subj||a.subjseq||a.year = '" + v_subjyear + "' and a.seq=" + v_seq + " and a.kind = 1 ");

            sql = selectSql.toString();

            ls = connMgr.executeQuery(sql);

            System.out.println(sql);

            while (ls.next()) {
                // ------------------- 2003.12.25 변경 -------------------------------------------------------------------
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

    /**
     * 선택된 자료실 게시물 상세내용 select
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws String
     */
    public String SelectCategoryPathnm(DBConnectionManager connMgr, String categorycd) throws Exception {
        ListSet ls = null;
        String sql = "";
        String upper = "";
        String middle = "";
        String lower = "";
        String result = "";

        try {
            upper = categorycd.substring(0, 3);
            middle = categorycd.substring(3, 6);
            lower = categorycd.substring(6, 9);

            // 대분류
            sql = "select classname from tz_knowcode where upperclass='" + upper + "' and middleclass='000'";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getString("classname");
                System.out.println(">>>>>" + result);
            }

            // 중분류
            if (!middle.equals("000")) {
                ls.close();
                result = result + ">";
                sql = "select classname from tz_knowcode where upperclass='" + upper + "' and middleclass='" + middle + "' and lowerclass='000' ";
                ls = connMgr.executeQuery(sql);
                if (ls.next()) {
                    result += ls.getString("classname");
                }
            }

            // 소분류
            if (!lower.equals("000")) {
                ls.close();
                result = result + ">";
                sql = "select classname from tz_knowcode where upperclass='" + upper + "' and middleclass='" + middle + "' and lowerclass='" + lower + "' ";
                ls = connMgr.executeQuery(sql);
                if (ls.next()) {
                    result += ls.getString("classname");
                }
            }

        } catch (Exception ex) {
            // ErrorManager.getErrorStackTrace(ex, box, sql);
            // throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
            System.out.println(ex.getMessage());
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
     * 꼬릿말 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     * @throws Exception
     */
    public ArrayList<DataBox> selectcommentList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // 수정일 : 05.11.16 수정자 : 이나연 _ totalcount 하기위한 쿼리수정
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        // String group_sql = "";
        String order_sql = "";
        // String count_sql = "";

        DataBox dbox = null;

        // String v_searchtext = box.getString("p_searchtext");
        // String v_select = box.getString("p_select");
        // int v_pageno = box.getInt("p_pageno");
        int v_seq = box.getInt("p_seq");
        int v_tabseq = box.getInt("p_tabseq");
        // String v_types = box.getString("p_types");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();
            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate
            head_sql += " select a.seq,a.types,a.commentseq,a.inuserid,a.commentqna,a.cdate, b.name, a.gender ";
            body_sql += "   from TZ_COMMENTQNA a, tz_member b ";

            // 수정일 : 05.11.16 수정자 : 이나연 _ (+) 수정
            // sql += "  where a.inuserid = b.userid(+) ";
            body_sql += "  where a.inuserid  =  b.userid(+) ";
            body_sql += "  and tabseq = " + v_tabseq;
            body_sql += "  and seq = " + v_seq;
            // body_sql += "  and types = " + v_types;
            order_sql += " order by seq desc, types asc, commentseq asc ";

            sql = head_sql + body_sql + order_sql;
            ls = connMgr.executeQuery(sql);
            /*
             * count_sql= "select count(*) "+ body_sql; int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); // 전체 row 수를 반환한다 int
             * total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다 ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다
             * ls.setCurrentPage(v_pageno,total_page_count); // 현재페이지번호를 세팅한다.
             */
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
     * 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int deleteBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql3 = "";
        int isOk1 = 1;
        int isOk2 = 1;

        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");
        Vector savefile = box.getVector("p_savefile");
        String v_savemotion = box.getString("p_savemotion");

        try {
            connMgr = new DBConnectionManager();

            if (v_types.equals("0")) { // 질문삭제시 답변동시삭제
                sql1 = " delete from TZ_HOMEQNA    ";
                sql1 += "  where tabseq = ? and seq = ?";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(1, BOARD_TABSEQ);
                pstmt1.setInt(2, v_seq);

            } else {
                sql1 = " delete from TZ_HOMEQNA";
                sql1 += "  where tabseq = ? and seq = ? and types = ?  ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(1, BOARD_TABSEQ);
                pstmt1.setInt(2, v_seq);
                pstmt1.setString(3, v_types);
            }

            isOk1 = pstmt1.executeUpdate();

            sql3 = " delete from TZ_COMMENTQNA    ";
            sql3 += "  where tabseq = ? and seq = ? and types = ?  ";
            pstmt2 = connMgr.prepareStatement(sql3);
            pstmt2.setInt(1, BOARD_TABSEQ);
            pstmt2.setInt(2, v_seq);
            pstmt2.setString(3, v_types);

            pstmt2.executeUpdate();

            for (int i = 0; i < savefile.size(); i++) {
                String str = (String) savefile.elementAt(i);
                if (!str.equals("")) {
                    // isOk2 = this.deleteUpFile(connMgr, box);
                }
            }
            if (isOk1 > 0 && isOk2 > 0) {
                if (savefile != null) {
                    FileManager.deleteFile(savefile); // 첨부파일 삭제
                }
                if (v_savemotion != null) {
                    FileManager.deleteFile(v_savemotion);
                }
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            }

        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
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
        return isOk1 * isOk2;
    }

    /**
     * 추천하기
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertRecommend(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        String v_seq = box.getString("p_seq");
        int isOk = 0;

        try {
            connMgr = new DBConnectionManager();
            sql = " update tz_homeqna set recommend = isnull(recommend,0) + 1 where tabseq = " + BOARD_TABSEQ + " and seq = " + v_seq + " ";
            isOk = connMgr.executeUpdate(sql);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 꼬릿말 등록할때(질문)
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertComment(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        // String sql2 = "";
        int isOk1 = 1;
        // int isOk2 = 1;
        // int v_cnt = 0;

        String v_commentqna = box.getString("commentqna");
        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");
        // String v_fileseq = box.getString("p_fileseq");

        String s_userid = "";
        // String s_usernm = box.getSession("name");
        // String s_gadmin = box.getSession("gadmin");
        String s_gender = box.getSession("gender");

        // if (s_gadmin.equals("A1")){
        // s_userid = "운영자";
        // }else{
        s_userid = box.getSession("userid");
        // }
        // String v_isopen = "Y";
        // Vector newFileNames = box.getNewFileNames("p_file");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            // ---------------------- 게시판 꼬릿말 번호를 가져온다 ----------------------------
            sql = "select isnull(max(commentseq), 0) from TZ_COMMENTQNA";
            sql += " where tabseq=" + BOARD_TABSEQ + " and seq = " + v_seq + " ";

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_commentseq = ls.getInt(1) + 1;
            ls.close();
            // ------------------------------------------------------------------------------------

            // //////////////////////////////// 게시판 table 에 입력 ///////////////////////////////////////////////////////////////////
            sql1 = "insert into TZ_COMMENTQNA(tabseq, seq, types, commentseq, inuserid, commentqna, cdate, gender)";
            sql1 += "                values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?) ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, BOARD_TABSEQ);
            pstmt1.setInt(2, v_seq);
            pstmt1.setString(3, v_types);
            pstmt1.setInt(4, v_commentseq);
            pstmt1.setString(5, s_userid);
            pstmt1.setString(6, v_commentqna);
            pstmt1.setString(7, s_gender);

            isOk1 = pstmt1.executeUpdate();

            if (isOk1 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1;
    }

    /**
     * 댓글 삭제할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteComment(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk1 = 1;

        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");
        int v_commentseq = box.getInt("p_commentseq");

        try {
            connMgr = new DBConnectionManager();

            sql = " delete from TZ_COMMENTQNA    ";
            sql += "  where tabseq = ? and seq = ? and types = ? and commentseq = ? ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, BOARD_TABSEQ);
            pstmt.setInt(2, v_seq);
            pstmt.setString(3, v_types);
            pstmt.setInt(4, v_commentseq);
            isOk1 = pstmt.executeUpdate();
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
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
        return isOk1;
    }

    /**
     * 답변 등록할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertKnowBoardAns(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;
        int isOk2 = 0;
        int v_cnt = 0;
        String s_grcode = box.getSession("tem_grcode");
        int v_seq = box.getInt("p_seq");
        String v_types = "";
        String v_title = box.getString("p_title");
        String v_contents = StringManager.replace(box.getString("content"), "<br>", "\n");
        String v_isopen = "Y";
        String s_userid = box.getSession("userid");
        String v_catecd = box.getString("p_catecd"); // 카테고리

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = " select max(to_number(types)) from TZ_HOMEQNA  ";
            sql += "  where tabseq = " + BOARD_TABSEQ + " and seq = " + v_seq;
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_types = String.valueOf((ls.getInt(1) + 1));
            } else {
                v_types = "1";
            }
            // //////////////////////////////// 게시판 table 에 입력 ///////////////////////////////////////////////////////////////////
            sql1 = "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate, cnt, grcode,categorycd)                ";
            // sql1 +=
            // "                values (?, ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?) ";
            sql1 += "                values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?) ";

            int index = 1;
            pstmt = connMgr.prepareStatement(sql1);
            pstmt.setInt(index++, BOARD_TABSEQ);
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, v_types);
            pstmt.setString(index++, v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_isopen);
            pstmt.setString(index++, s_userid);
            pstmt.setInt(index++, v_cnt);
            pstmt.setString(index++, s_grcode);
            pstmt.setString(index++, v_catecd);

            isOk = pstmt.executeUpdate();

            isOk2 = this.insertUpFile(connMgr, BOARD_TABSEQ, v_seq, v_types, box); // 파일첨부했다면 파일table에 insert

            // sql2 = "select contents from tz_HOMEQNA where tabseq = " + BOARD_TABSEQ + " and  seq = " + v_seq+ " and types = '"+v_types+"'";
            // connMgr.setOracleCLOB(sql2, v_contents); // (기타 서버 경우)
            /*
             * 05.11.15 이나연
             */
            // isOk3 = this.deleteUpFile(connMgr, box);

            if (isOk > 0 && isOk2 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * 수정하여 저장할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int updateKnowBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;

        int v_seq = box.getInt("p_seq");

        String v_types = box.getString("p_types");
        String v_title = box.getString("p_title");
        String v_contents = StringManager.replace(box.getString("content"), "<br>", "\n");

        String s_userid = "";
        String s_grcode = box.getSession("tem_grcode");

        // if (s_gadmin.equals("A1")){
        // s_userid = "운영자";
        // }else{
        s_userid = box.getSession("userid");
        // }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // sql =
            // " update TZ_HOMEQNA set title = ? , contents = empty_clob(), categorycd = ?,luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql = " update TZ_HOMEQNA set title = ? , contents = ?, luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where tabseq = ? and seq = ? and types = ? and grcode = ?                       ";

            int index = 1;
            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(index++, v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            pstmt.setString(index++, s_userid);
            pstmt.setInt(index++, BOARD_TABSEQ);
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, v_types);
            pstmt.setString(index++, s_grcode);

            isOk = pstmt.executeUpdate();

            this.insertUpFile(connMgr, BOARD_TABSEQ, v_seq, v_types, box); // 파일첨부했다면 파일table에 insert
            // sql2 = "select contents from tz_HOMEQNA where tabseq = " + BOARD_TABSEQ + " and  seq = " + v_seq+ " and types = '"+v_types+"'";
            // connMgr.setOracleCLOB(sql2, v_contents); // (기타 서버 경우)

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
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
     * QNA 새로운 자료파일 등록
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int insertUpFile(DBConnectionManager connMgr, int p_tabseq, int p_seq, String types, RequestBox box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null, pstmt1 = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        String v_process = box.getString("p_process");

        int isOk2 = 0;

        // ---------------------- 업로드되는 파일의 형식을 알고 코딩해야한다 --------------------------------
        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];

        for (int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));
        }
        // ----------------------------------------------------------------------------------------------------------------------------

        String s_userid = box.getSession("userid");

        try {
            // ---------------------- 자료 번호 가져온다 ----------------------------
            sql = "select isnull(max(fileseq),	0) from	tz_homefile	where tabseq = " + p_tabseq + " and seq = " + p_seq + "and types = '" + types + "' ";
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1);
            ls.close();
            // ------------------------------------------------------------------------------------

            // //////////////////////////////// 파일 table 에 입력 ///////////////////////////////////////////////////////////////////

            if (v_process.equals("update")) {
                sql1 = "update TZ_HOMEFILE set realfile = ?, savefile = ?, luserid = ?  where tabseq = ?  and seq = ? and fileseq =?";
                pstmt1 = connMgr.prepareStatement(sql1);
            } else {
                sql2 = "insert	into tz_homefile(tabseq, seq, fileseq, types, realfile, savefile, luserid,	ldate)";
                sql2 += " values (?, ?, ?, ?, ?, ?,?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";
                pstmt2 = connMgr.prepareStatement(sql2);
            }

            for (int i = 0; i < FILE_LIMIT; i++) {
                if (!v_realFileName[i].equals("")) { // 실제 업로드 되는 하나의 파일만 체크해서 db에 입력한다
                    if (!v_realFileName[0].equals("")) {
                        if (v_process.equals("update")) {
                            pstmt1.setString(1, v_realFileName[0]);
                            pstmt1.setString(2, v_newFileName[0]);
                            pstmt1.setString(3, s_userid);
                            pstmt1.setInt(4, p_tabseq);
                            pstmt1.setInt(5, p_seq);
                            pstmt1.setInt(6, v_fileseq);
                            isOk2 = pstmt1.executeUpdate();
                        } else {
                            pstmt2.setInt(1, p_tabseq);
                            pstmt2.setInt(2, p_seq);
                            pstmt2.setInt(3, v_fileseq);
                            pstmt2.setString(4, types);
                            pstmt2.setString(5, v_realFileName[0]);
                            pstmt2.setString(6, v_newFileName[0]);
                            pstmt2.setString(7, s_userid);
                            isOk2 = pstmt2.executeUpdate();
                        }
                    }
                }
            }
        } catch (Exception ex) {
            FileManager.deleteFile(v_newFileName, FILE_LIMIT); // 일반파일, 첨부파일 있으면 삭제..
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
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
                } catch (Exception e1) {
                }
            }
        }
        return isOk2;
    }

    /**
     * 선택된 자료파일 DB에서 삭제
     * 
     * @param connMgr DB Connection Manager
     * @param box receive from the form object and session
     * @param p_filesequence 선택 파일 갯수
     * @return
     * @throws Exception
     */
    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box, Vector<String> p_filesequence) throws Exception {
        PreparedStatement pstmt3 = null;
        String sql3 = "";
        int isOk3 = 1;
        int v_seq = box.getInt("p_seq");

        try {
            sql3 = "delete from tz_homefile where tabseq = " + BOARD_TABSEQ + " and seq =? and fileseq = ?";
            pstmt3 = connMgr.prepareStatement(sql3);
            for (int i = 0; i < p_filesequence.size(); i++) {
                int v_fileseq = Integer.parseInt((String) p_filesequence.elementAt(i));

                pstmt3.setInt(1, v_seq);
                pstmt3.setInt(2, v_fileseq);
                isOk3 = pstmt3.executeUpdate();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql = " + sql3 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk3;
    }

    /**
     * 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 지식공유 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectListCate(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        String count_sql = "";

        DataBox dbox = null;

        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        int v_pageno = box.getInt("p_pageno");
        String v_cate = box.getString("p_cateSelect"); // 인기지식 카테고리보기

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            head_sql += " select a.seq, a.types, a.title, a.contents, a.indate, a.inuserid, a.ldate, b.name,a.cnt, isnull(a.recommend, 0) recommend , c.subjclass, c.classname, ";
            head_sql += "		(select classname from tz_knowcode where subjclass=a.categorycd  and grcode=a.grcode) categorynm,  ";
            head_sql += "		(select count(realfile) from tz_homefile where tabseq = a.TABSEQ and seq = a.seq ) filecnt, ";
            head_sql += "        (select count(commentseq) ccnt from TZ_COMMENTQNA where tabseq='" + BOARD_TABSEQ + "' and seq=a.seq) commentcnt    ";

            body_sql += "   from TZ_HOMEQNA a, tz_member b, tz_knowcode c ";
            body_sql += "  where a.inuserid  =  b.userid(+) and c.subjclass=a.categorycd  and a.grcode='" + s_grcode + "'  ";
            body_sql += "  and tabseq = " + BOARD_TABSEQ;

            if (v_cate != null) {
                body_sql += " and c.subjclass ='" + v_cate + "'";
            }

            order_sql += " order by seq desc ";

            sql = head_sql + body_sql + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); // 전체 row 수를 반환한다
            int total_page_count = ls.getTotalPage(); // 전체 페이지 수를 반환한다
            int row = 7;
            ls.setPageSize(row); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.

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

}
