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
 * To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * 
 * @author Administrator
 * 
 */
public class KnowBoardUserBean {

    public KnowBoardUserBean() {

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
        DataBox dbox = null;

        String v_userid = box.getSession("userid");

        /* sql 작성 변수 */
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String where_sql = "";
        String order_sql = "";
        String count_sql = "";

        /* 페이지링 변수 */
        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 15 : box.getInt("p_pagesize");

        /* 검색 조건 변수 */
        String v_area = box.getStringDefault("p_area", "ALL");
        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");

        String ss_tgubun = box.getStringDefault("ss_tgubun", "ALL");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql += "SELECT \n";
            head_sql += "    A.TGUBUN, \n";
            head_sql += "    A.TABSEQ, \n";
            head_sql += "    A.SEQ, \n";
            head_sql += "    A.TYPES, \n";
            head_sql += "    A.TITLE, \n";
            head_sql += "    A.INDATE, \n";
            head_sql += "    A.INUSERID, \n";
            head_sql += "    A.CNT, \n";
            head_sql += "    M.NAME, \n";
            head_sql += "    A.SUBJ, \n";
            head_sql += "    A.YEAR, \n";
            head_sql += "    A.SUBJSEQ, \n";
            head_sql += "    A.LESSON, \n";
            head_sql += "    S.SUBJNM, \n";
            head_sql += "    NVL (S.AREA, DECODE(A.TGUBUN,'N','A0','C0')) AS AREA, \n";
            head_sql += "    S.UPPERCLASS, \n";
            head_sql += "    S.MIDDLECLASS, \n";
            head_sql += "    S.LOWERCLASS, \n";
            head_sql += "    A.ANSWERCNT, \n";
            head_sql += "    A.COMMENTCNT, \n";
            head_sql += "	 TO_CHAR(TO_DATE(SYSDATE-1),'YYYYMMDDHH24MISS') AS AGODATE \n";
            body_sql += "FROM \n";
            body_sql += "    TZ_MEMBER M, \n";
            body_sql += "    ( \n";

            /* 공지사항 tz_notice : community_yn = y */
            body_sql += "    SELECT 'N' AS TGUBUN, TABSEQ, SEQ, '0' AS TYPES, ADTITLE AS TITLE, ADDATE AS INDATE, '1' AS TOPDATA, ADUSERID AS INUSERID, CNT, '' AS SUBJ, '' AS YEAR, '' AS SUBJSEQ, '' AS LESSON, ";
            body_sql += "		0 AS ANSWERCNT,(SELECT COUNT(1) FROM TZ_COMMENTQNA WHERE TABSEQ = '12' AND SEQ = T.SEQ) AS COMMENTCNT ";
            body_sql += "	 FROM TZ_NOTICE T WHERE TABSEQ = '12' AND COMMUNITY_YN = 'Y' \n";

            /*
             * 묻고답하기 tz_homeqna : tabseq = 5 body_sql += "    UNION ALL \n"; body_sql +=
             * "    SELECT 'R' AS TGUBUN, TABSEQ, SEQ, TYPES, TITLE, INDATE, '2' AS TOPDATA, INUSERID, CNT, '' AS SUBJ, '' AS YEAR, '' AS SUBJSEQ, '' AS LESSON, "
             * ; body_sql +=
             * "		(SELECT COUNT(1) FROM TZ_HOMEQNA WHERE TABSEQ = '5' AND TYPES != '0' AND SEQ = T.SEQ) AS ANSWERCNT,(SELECT COUNT(1) FROM TZ_COMMENTQNA WHERE TABSEQ = '5' AND SEQ = T.SEQ) AS COMMENTCNT "
             * ; body_sql += "	 FROM TZ_HOMEQNA T WHERE TABSEQ = '5' AND TYPES = '0' \n";
             */

            /* 지식팩토리 (정보글) tz_homeqna tabseq = 7 */
            body_sql += "    UNION ALL \n";
            body_sql += "    SELECT 'I' AS TGUBUN, TABSEQ, SEQ, TYPES, TITLE, INDATE, '2' AS TOPDATA, INUSERID, CNT, CATEGORYCD AS SUBJ, '' AS YEAR, '' AS SUBJSEQ, '' AS LESSON, ";
            body_sql += "		(SELECT COUNT(1) FROM TZ_HOMEQNA WHERE TABSEQ = '7' AND TYPES != '0' AND SEQ = T.SEQ) AS ANSWERCNT,(SELECT COUNT(1) FROM TZ_COMMENTQNA WHERE TABSEQ = '7' AND SEQ = T.SEQ) AS COMMENTCNT ";
            body_sql += "	 FROM TZ_HOMEQNA T WHERE TABSEQ = '7' AND TYPES = '0' \n";

            /* 과정질문방 ta_qna */
            body_sql += "    UNION ALL \n";
            body_sql += "    SELECT 'Q' AS TGUBUN, 0 AS TABSEQ, SEQ, KIND AS TYPES, TITLE, INDATE, '2' AS TOPDATA, INUSERID, CNT, SUBJ, YEAR, SUBJSEQ, LESSON, ";
            body_sql += "		(SELECT COUNT(1) FROM TZ_QNA WHERE SUBJ = T.SUBJ AND SUBJSEQ = T.SUBJSEQ AND YEAR = T.YEAR AND LESSON = T.LESSON AND KIND != '0' AND SEQ = T.SEQ) AS ANSWERCNT, (SELECT COUNT(1) FROM TZ_COMMENTQNA WHERE TABSEQ = '0' AND SEQ = T.SEQ AND SUBJ=T.SUBJ AND YEAR = T.YEAR AND SUBJSEQ = T.SUBJSEQ AND LESSON = T.LESSON) AS COMMENTCNT ";
            body_sql += "	 FROM TZ_QNA T WHERE KIND = '0' \n";

            /* 과정자료실 tz_board, tz_bds : type = 'SD' */
            body_sql += "    UNION ALL \n";
            body_sql += "    SELECT 'D' AS TGUBUN, SA.TABSEQ, SA.SEQ, '0' AS TYPES, SA.TITLE, SA.INDATE, '2' AS TOPDATA, SA.USERID AS INUSERID, SA.CNT, SB.SUBJ, SB.YEAR, SB.SUBJSEQ, '' AS LESSON, ";
            body_sql += "		(SELECT COUNT(1) FROM TZ_BOARD WHERE TABSEQ = SA.TABSEQ AND LEVELS != '1' AND SEQ = SA.SEQ) AS ANSWERCNT, (SELECT COUNT(1) FROM TZ_COMMENTQNA WHERE TABSEQ = SA.TABSEQ AND SEQ = SA.SEQ) AS COMMENTCNT ";
            body_sql += "	 FROM TZ_BOARD SA, TZ_BDS SB WHERE SB.TYPE = 'SD' AND SA.TABSEQ = SB.TABSEQ AND SA.LEVELS = '1' \n";

            /*
             * 과정 일반게시판 tz_board, tz_bds : type = 'SB' body_sql += "    UNION ALL \n"; body_sql +=
             * "    SELECT 'C' AS TGUBUN, SA.TABSEQ, SA.SEQ, '0' AS TYPES, SA.TITLE, SA.INDATE, '2' AS TOPDATA, SA.USERID AS INUSERID, SA.CNT, SB.SUBJ, SB.YEAR, SB.SUBJSEQ, '' AS LESSON, "
             * ; body_sql +=
             * "		(SELECT COUNT(1) FROM TZ_BOARD WHERE TABSEQ = SA.TABSEQ AND LEVELS != '1' AND SEQ = SA.SEQ) AS ANSWERCNT, (SELECT COUNT(1) FROM TZ_COMMENTQNA WHERE TABSEQ = SA.TABSEQ AND SEQ = SA.SEQ) AS COMMENTCNT "
             * ; body_sql += "	 FROM TZ_BOARD SA, TZ_BDS SB WHERE SB.TYPE = 'SB' AND SA.TABSEQ = SB.TABSEQ AND SA.LEVELS = '1' \n";
             */

            /* 커뮤니티 토론게시판 tz_toroncummunity : tabseq = 35 */
            body_sql += "    UNION ALL \n";
            body_sql += "    SELECT 'T' AS TGUBUN, TABSEQ, SEQ, TYPES, TITLE, INDATE, '2' AS TOPDATA, INUSERID, CNT, SUBJ, '' AS YEAR, '' AS SUBJSEQ, '' AS LESSON, ";
            body_sql += "		(SELECT COUNT(1) FROM TZ_TORONCOMMUNITY WHERE TYPES != '0' AND SEQ = T.SEQ) AS ANSWERCNT,(SELECT COUNT(1) FROM TZ_COMMENTQNA WHERE TABSEQ = '35' AND SEQ = T.SEQ) AS COMMENTCNT ";
            body_sql += "	 FROM TZ_TORONCOMMUNITY T WHERE TYPES = '0' \n";

            body_sql += "    ) A, \n";
            body_sql += "    TZ_SUBJ S \n";
            where_sql += "WHERE \n";
            where_sql += "	M.USERID = A.INUSERID \n";
            where_sql += "	AND A.SUBJ = S.SUBJ(+) \n";
            if (!v_area.equals("ALL")) {
                if (!v_area.equals("MINE")) {
                    where_sql += "	AND NVL (AREA, DECODE(TGUBUN,'N','A0','C0')) IN ('A0'," + StringManager.makeSQL(v_area) + ")\n";
                } else {
                    where_sql += "	AND A.INUSERID = " + StringManager.makeSQL(v_userid) + "\n";
                }
            }

            if (!v_searchtext.equals("")) {
                if (v_search.equals("TITL")) { // 검색어가 있으면
                    where_sql += " and  (lower(title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%") + ")) ";
                } else if (v_search.equals("SUBJ")) { // 검색어가 있으면
                    where_sql += " and  (lower(subjnm) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%") + ")) ";
                }
            }

            if (!ss_tgubun.equals("ALL")) {
                where_sql += "AND TGUBUN = " + StringManager.makeSQL(ss_tgubun) + "\n";
            }
            order_sql += "ORDER BY A.TOPDATA ASC, A.INDATE DESC \n";

            sql = head_sql + body_sql + where_sql + order_sql;

            ls = connMgr.executeQuery(sql);

            count_sql = "select count(1) " + body_sql + where_sql;

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); // 전체 row 수를 반환한다
            ls.setPageSize(v_pagesize); // 페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(total_row_count));
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

        DataBox dbox = null;

        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        int v_pageno = 1;

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

        DataBox dbox = null;

        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        int v_pageno = 1;

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

            // 2005.11.09_하경태 : Oracle -> Mssql
            /*
             * sql = " select "; sql += " subjclass, classname, upperclass + Replicate( '0', 9 - len(upperclass ) ) root, "; sql += " 	(case "; sql +=
             * " 	when middleclass = '000' and lowerclass  = '000' then subjclass "; sql +=
             * " 	when middleclass != '000' and lowerclass = '000' then (upperclass + Replicate( '0', 9 - len(upperclass ) ) ) "; sql +=
             * " 	else (upperclass + middleclass + Replicate( '0', 9 - len(upperclass + middleclass) ))" ; sql += " end) parent, "; sql += " (case "; sql +=
             * " 	when middleclass = '000' and lowerclass='000' then 0 "; sql += " 	when middleclass != '000' and lowerclass = '000' then 1 "; sql +=
             * " 	else 2 "; sql += "  end) levels, "; sql += " (case "; sql += " 	when a.middleclass='000' and a.lowerclass='000' then "; sql +=
             * " 		(select count(*) from tz_knowcode "; sql +=
             * " 		where upperclass=a.upperclass and middleclass!='000' and lowerclass='000' and grcode='"+s_grcode+"') "; sql +=
             * " 	when a.middleclass != '000' and a.lowerclass='000' then "; sql += " 		(select count(*) from tz_knowcode "; sql +=
             * " 		where upperclass=a.upperclass and middleclass=a.middleclass and lowerclass != '000' and grcode='"+s_grcode+"') "; sql += " 	else 0 "; sql
             * += " end ) subcnt, "; sql += " (case "; sql += " 	when a.middleclass='000' and a.lowerclass='000'then a.classname "; sql +=
             * " 	when a.middleclass!='000' and a.lowerclass='000' then "; sql +=
             * " 		(select classname from tz_knowcode where grcode='"+s_grcode+"' and subjclass=a.upperclass + '000000') + '>' + a.classname "; sql +=
             * " 	else( (select classname from tz_knowcode "; sql += " 		where grcode='"+s_grcode+"' and subjclass=a.upperclass + '000000') + '>' + "; sql
             * += " 			(select classname from tz_knowcode where grcode='"+s_grcode+"' and subjclass=a.upperclass + a.middleclass + '000') + '>' + "; sql +=
             * " 			a.classname ) "; sql += " end ) viewname "; sql += " from tz_knowcode a "; sql += " where a.grcode='"+s_grcode+"' order by subjclass ";
             */

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

            /*
             * 2005.11.09_하경태 : Oracle -> Mssql sql = " select a.subjclass, a.classname, (a.upperclass + Replicate( '0', 9 - len(a.upperclass ) )) root, ";
             * sql+= "       (case when a.middleclass='000' and  a.lowerclass='000' then a.subjclass  "; sql+=
             * "        when a.middleclass!='000' and  a.lowerclass='000' then (a.upperclass + Replicate( '0', 9 - len(a.upperclass )) ) "; sql+=
             * "        else (a.upperclass + a.middleclass + Replicate( '0', 9 - len(a.upperclass + a.middleclass ) ))  "; sql+= "        end) parent, ";
             * sql+= "       (case when a.middleclass='000' and  a.lowerclass='000' then 0 "; sql+=
             * "        when a.middleclass!='000' and  a.lowerclass='000' then 1 "; sql+= "        else 2 "; sql+= "        end) levels, "; sql+=
             * "       (case when a.middleclass='000' and  a.lowerclass='000'  "; //--대분류 sql+=
             * "         then (select count(*) from tz_knowcode where upperclass=a.upperclass and middleclass!='000' and lowerclass='000' and grcode='"
             * +s_grcode+"')  "; sql+= "        when a.middleclass!='000' and  a.lowerclass='000'      "; //--중분류 sql+=
             * "         then (select count(*) from tz_knowcode where upperclass=a.upperclass and middleclass=a.middleclass and lowerclass != '000' and grcode='"
             * +s_grcode+"')  "; sql+= "        else 0                                                 "; //--소분류 sql+= "        end "; sql+=
             * "       ) subcnt, "; sql+= "       (case when a.middleclass='000' and  a.lowerclass='000'then a.classname  "; sql+=
             * "        when a.middleclass!='000' and  a.lowerclass='000'  "; sql+=
             * "         then (select classname from tz_knowcode where  grcode='"+s_grcode+"' and subjclass=a.upperclass + '000000') + '>' + a.classname  ";
             * sql+= "        else  "; sql+=
             * "         (select classname from tz_knowcode where  grcode='"+s_grcode+"' and subjclass=a.upperclass + '000000') + '>' + "; sql+=
             * " (select classname from tz_knowcode where  grcode='"+s_grcode+"' and subjclass=a.upperclass + a.middleclass + '000') + '>' + a.classname  ";
             * sql+= "        end  "; sql+= "        ) viewname  "; sql+= " from tz_knowcode a  where a.grcode='"+s_grcode+"' "; sql+=
             * " order by subjclass ";
             */

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
        ListSet ls2 = null;
        PreparedStatement pstmt1 = null;
        String bsql = "";
        String sql = "";
        String sql1 = "";
        int isOk1 = 1;
        int v_cnt = 0;
        String v_title = box.getString("p_title");
        String v_contents = StringManager.replace(box.getString("content"), "<br>", "\n");
        String v_types = "0";
        String s_userid = box.getSession("userid");
        String s_grcode = box.getSession("tem_grcode");
        String v_isopen = "Y";
        String v_tgubun = box.getString("p_tgubun");
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_lesson = box.getStringDefault("p_lesson", "000");
        String v_category = box.getString("p_category");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ---------------------- 게시판 번호 가져온다 ----------------------------
            if (v_tgubun.equals("I")) {
                sql = "select isnull(max(seq), 0) from TZ_HOMEQNA where tabseq = 7";
            } else if (v_tgubun.equals("R")) {
                sql = "select isnull(max(seq), 0) from TZ_HOMEQNA where tabseq = 5";
            } else if (v_tgubun.equals("T")) {
                sql = "select isnull(max(seq), 0) from TZ_TORONCOMMUNITY where tabseq = 35";
            } else if (v_tgubun.equals("Q")) {
                sql = "SELECT MAX(seq) FROM TZ_QNA WHERE SUBJ='" + v_subj + "' AND SUBJSEQ='" + v_subjseq + "' AND YEAR='" + v_year + "' AND LESSON='"
                        + v_lesson + "'";
            } else if (v_tgubun.equals("C")) {
                bsql = "SELECT COUNT(1) FROM TZ_BDS WHERE SUBJ='" + v_subj + "' AND SUBJSEQ='" + v_subjseq + "' AND YEAR='" + v_year + "'";
                ls2 = connMgr.executeQuery(bsql);
                ls2.next();
                int v_cheboard = ls2.getInt(1);
                ls2.close();

                if (v_cheboard == 0) {
                    System.out.println("============BDS생성=========");
                    insertBDS(v_subj, v_subjseq, v_year, s_userid);
                } else {
                    System.out.println("============BDS스킵=========");
                }

                sql = "SELECT NVL(MAX(A.SEQ),0) FROM TZ_BDS B,TZ_BOARD A WHERE B.TABSEQ = A.TABSEQ(+) AND B.SUBJ='" + v_subj + "' AND B.SUBJSEQ='"
                        + v_subjseq + "' AND B.YEAR='" + v_year + "'";
            }

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_seq = ls.getInt(1) + 1;
            ls.close();

            // //////////////////////////////// 게시판 table 에 입력 ///////////////////////////////////////////////////////////////////
            if (v_tgubun.equals("I")) {
                sql1 = "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate, grcode, cnt, categorycd, okyn1)                      ";
                sql1 += "                values (7, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?, '4') ";
                int index = 1;
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(index++, v_seq);
                pstmt1.setString(index++, v_types);
                pstmt1.setString(index++, v_title);
                pstmt1.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
                pstmt1.setString(index++, s_userid);
                pstmt1.setString(index++, v_isopen);
                pstmt1.setString(index++, s_userid);
                pstmt1.setString(index++, s_grcode);
                pstmt1.setInt(index++, v_cnt);
                pstmt1.setString(index++, v_subj);

            } else if (v_tgubun.equals("R")) {
                sql1 = "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate, grcode, cnt, categorycd)                      ";
                sql1 += "                values (5, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?) ";

                int index = 1;
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(index++, v_seq);
                pstmt1.setString(index++, v_types);
                pstmt1.setString(index++, v_title);
                pstmt1.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
                pstmt1.setString(index++, s_userid);
                pstmt1.setString(index++, v_isopen);
                pstmt1.setString(index++, s_userid);
                pstmt1.setString(index++, s_grcode);
                pstmt1.setInt(index++, v_cnt);
                pstmt1.setString(index++, v_category);

            } else if (v_tgubun.equals("T")) {
                sql1 = "insert into TZ_TORONCOMMUNITY (tabseq, seq, types, title, contents, indate, inuserid, luserid, ldate, cnt, subj, grcode)	";
                sql1 += "	values(35,?,?,?,?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?, to_char(sysdate, 'YYYYMMDDHH24MISS'),0,?,?) ";

                int index = 1;
                System.out.println("v_seq:" + v_seq + ", v_types:" + v_types + ", v_title" + v_title + ", v_contents:" + v_contents + ", userid:"
                        + s_userid);
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(index++, v_seq);
                pstmt1.setString(index++, v_types);
                pstmt1.setString(index++, v_title);
                pstmt1.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
                pstmt1.setString(index++, s_userid);
                pstmt1.setString(index++, s_userid);
                pstmt1.setString(index++, v_subj);
                pstmt1.setString(index++, s_grcode);

            } else if (v_tgubun.equals("Q")) {
                sql1 = "insert into TZ_QNA(subj, year, subjseq, lesson, seq, kind, title, indate, inuserid, isopen, luserid, ldate, togubun, grcode, categorycd, okyn1, contents, cnt)  ";
                sql1 += "	values(?,?,?,?,?,'0',?,to_char(sysdate, 'YYYYMMDDHH24MISS'),?,'Y',?,to_char(sysdate, 'YYYYMMDDHH24MISS'),'1',?,'00','1',?,0) ";

                int index = 1;
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(index++, v_subj);
                pstmt1.setString(index++, v_year);
                pstmt1.setString(index++, v_subjseq);
                pstmt1.setString(index++, v_lesson);
                pstmt1.setInt(index++, v_seq);
                pstmt1.setString(index++, v_title);
                pstmt1.setString(index++, s_userid);
                pstmt1.setString(index++, s_userid);
                pstmt1.setString(index++, s_grcode);
                pstmt1.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());

            } else if (v_tgubun.equals("C")) {
                sql1 = "insert into TZ_BOARD(tabseq, seq, title, userid, name, content, indate, refseq, levels, position, luserid, ldate) ";
                sql1 += "	values((select tabseq from tz_bds where subj=? and year=? and subjseq=?),?,?,?,(select name from tz_member where userid=?),?,to_char(sysdate, 'YYYYMMDDHH24MISS'),?,1,1,?,to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

                int index = 1;
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(index++, v_subj);
                pstmt1.setString(index++, v_year);
                pstmt1.setString(index++, v_subjseq);
                pstmt1.setInt(index++, v_seq);
                pstmt1.setString(index++, v_title);
                pstmt1.setString(index++, s_userid);
                pstmt1.setString(index++, s_userid);
                pstmt1.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
                pstmt1.setInt(index++, v_seq);
                pstmt1.setString(index++, s_userid);
            }

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
            if (ls2 != null) {
                try {
                    ls2.close();
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
     * 게시판 없을경우 생성한다
     */
    public void insertBDS(String subj, String subjseq, String year, String userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;

        String v_sdesc = subj + "과정" + subjseq + "차수게시판";

        String sql = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql += "INSERT INTO TZ_BDS(TABSEQ, TYPE, GRCODE, COMP, SUBJ, YEAR, SUBJSEQ, SDESC, LUSERID, LDATE) ";
            sql += " VALUES((SELECT MAX(TABSEQ)+1 FROM TZ_BDS), 'SB', '0000000', '0000000000', ?, ?, ?, ?, ?, TO_CHAR(SYSDATE,'YYYYMMSSHH24MISS'))";

            int index = 1;
            pstmt1 = connMgr.prepareStatement(sql);
            pstmt1.setString(index++, subj);
            pstmt1.setString(index++, year);
            pstmt1.setString(index++, subjseq);
            pstmt1.setString(index++, v_sdesc);
            pstmt1.setString(index++, userid);

            pstmt1.executeUpdate();

            if (connMgr != null) {
                try {
                    connMgr.commit();
                } catch (Exception e10) {
                }
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
        ListSet ls2 = null;
        String sql = "";
        DataBox dbox = null;
        String upsql = "";

        int v_seq = box.getInt("p_seq");
        int v_tabseq = box.getInt("p_tabseq");
        String v_tgubun = box.getString("p_tgubun");
        String v_types = box.getString("p_types");
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_lesson = box.getString("p_lesson");

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        try {
            connMgr = new DBConnectionManager();

            if (v_tabseq == 7 || v_tabseq == 5) { /* TGUBUN 이 정보(I) 일 경우 TZ_HOMEQNA - TABSEQ = "7" */
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	A.TABSEQ,\n";
                sql += "	A.SEQ, \n";
                sql += "	A.TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME, \n";
                sql += "	S.SUBJNM, \n";
                sql += "	S.AREA \n";
                sql += "FROM \n";
                sql += "	TZ_HOMEQNA A, \n";
                sql += "	TZ_HOMEFILE B, \n";
                sql += "	TZ_MEMBER M, \n";
                sql += "	TZ_SUBJ S \n";
                sql += "WHERE \n";
                sql += "	A.TABSEQ = " + v_tabseq + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND A.TYPES = " + StringManager.makeSQL(v_types) + " \n";
                sql += "	AND A.TABSEQ = B.TABSEQ(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.TYPES = B.TYPES(+) \n";
                sql += "	AND A.INUSERID = M.USERID \n";
                sql += "	AND A.CATEGORYCD = S.SUBJ(+) \n";

                upsql = "UPDATE TZ_HOMEQNA SET CNT = CNT+1 WHERE  \n";
                upsql += "	TABSEQ = " + v_tabseq + " \n";
                upsql += "	AND SEQ = " + v_seq + " \n";
            } else if (v_tabseq == 0) {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	'0' AS TABSEQ, \n";
                sql += "	A.SEQ, \n";
                sql += "	A.KIND AS TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	A.SUBJ, \n";
                sql += "	A.SUBJSEQ, \n";
                sql += "	A.YEAR, \n";
                sql += "	A.LESSON, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME, \n";
                sql += "	S.SUBJNM, \n";
                sql += "	S.AREA \n";
                sql += "FROM \n";
                sql += "	TZ_QNA A, \n";
                sql += "	TZ_QNAFILE B, \n";
                sql += "	TZ_MEMBER M, \n";
                sql += "	TZ_SUBJ S \n";
                sql += "WHERE \n";
                sql += "	A.SUBJ = " + StringManager.makeSQL(v_subj) + " \n";
                sql += "	AND A.SUBJSEQ = " + StringManager.makeSQL(v_subjseq) + " \n";
                sql += "	AND A.YEAR = " + StringManager.makeSQL(v_year) + " \n";
                sql += "	AND A.LESSON = " + StringManager.makeSQL(v_lesson) + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND A.KIND = " + StringManager.makeSQL(v_types) + " \n";
                sql += "	AND A.SUBJ = B.SUBJ(+) \n";
                sql += "	AND A.YEAR = B.YEAR(+) \n";
                sql += "	AND A.SUBJSEQ = B.SUBJSEQ(+) \n";
                sql += "	AND A.LESSON = B.LESSON(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.KIND = B.KIND(+) \n";
                sql += "	AND A.INUSERID = M.USERID \n";
                sql += "	AND A.SUBJ = S.SUBJ(+) \n";

                upsql = "UPDATE TZ_QNA SET CNT = CNT+1 WHERE  \n";
                upsql += "	SUBJ = " + StringManager.makeSQL(v_subj) + " \n";
                upsql += "	AND SUBJSEQ = " + StringManager.makeSQL(v_subjseq) + " \n";
                upsql += "	AND YEAR = " + StringManager.makeSQL(v_year) + " \n";
                upsql += "	AND LESSON = " + StringManager.makeSQL(v_lesson) + " \n";
                upsql += "	AND SEQ = " + v_seq + " \n";
            } else if (v_tabseq == 12) {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	A.TABSEQ, \n";
                sql += "	A.SEQ, \n";
                sql += "	A.ADTITLE AS TITLE, \n";
                sql += "	A.ADCONTENT AS CONTENTS, \n";
                sql += "	A.ADUSERID AS INUSERID, \n";
                sql += "	A.ADNAME AS NAME \n";
                sql += "FROM \n";
                sql += "	TZ_NOTICE A \n";
                sql += "WHERE \n";
                sql += "	A.TABSEQ = " + v_tabseq + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND COMMUNITY_YN = 'Y' \n";

                upsql = "UPDATE TZ_NOTICE SET CNT = CNT+1 WHERE  \n";
                upsql += "	TABSEQ = " + v_tabseq + " \n";
                upsql += "	AND SEQ = " + v_seq + " \n";
            } else if (v_tabseq == 35) {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	A.TABSEQ,\n";
                sql += "	A.SEQ, \n";
                sql += "	A.TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME, \n";
                sql += "	S.SUBJNM, \n";
                sql += "	S.AREA \n";
                sql += "FROM \n";
                sql += "	TZ_TORONCOMMUNITY A, \n";
                sql += "	TZ_BOARDFILE B, \n";
                sql += "	TZ_MEMBER M, \n";
                sql += "	TZ_SUBJ S \n";
                sql += "WHERE \n";
                sql += "	A.TABSEQ = " + v_tabseq + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND A.TYPES = " + StringManager.makeSQL(v_types) + " \n";
                sql += "	AND A.TABSEQ = B.TABSEQ(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.INUSERID = M.USERID \n";
                sql += "	AND A.SUBJ = S.SUBJ(+) \n";

                upsql = "UPDATE TZ_TORONCOMMUNITY SET CNT = CNT+1 WHERE  \n";
                upsql += "	TABSEQ = " + v_tabseq + " \n";
                upsql += "	AND SEQ = " + v_seq + " \n";
            } else {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	A.TABSEQ, \n";
                sql += "	A.SEQ, \n";
                sql += "	A.LEVELS AS TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENT AS CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.USERID AS INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME, \n";
                sql += "	S.SUBJNM, \n";
                sql += "	S.AREA \n";
                sql += "FROM \n";
                sql += "	TZ_BOARD A, \n";
                sql += "	TZ_BOARDFILE B, \n";
                sql += "	TZ_MEMBER M, \n";
                sql += "	TZ_BDS D, \n";
                sql += "	TZ_SUBJ S \n";
                sql += "WHERE \n";
                sql += "	A.TABSEQ = " + v_tabseq + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND A.LEVELS = '1' \n";
                sql += "	AND A.TABSEQ = B.TABSEQ(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.USERID = M.USERID \n";
                sql += "	AND A.TABSEQ = D.TABSEQ \n";
                sql += "	AND D.SUBJ = S.SUBJ(+) \n";

                upsql = "UPDATE TZ_BOARD SET CNT = CNT+1 WHERE  \n";
                upsql += "	TABSEQ = " + v_tabseq + " \n";
                upsql += "	AND SEQ = " + v_seq + " \n";
            }
            ls = connMgr.executeQuery(sql);
            ls2 = connMgr.executeQuery(upsql);

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

    @SuppressWarnings("unchecked")
    public ArrayList selectAnswerList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;

        String v_tgubun = box.getString("p_tgubun");
        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_lesson = box.getString("p_lesson");

        Vector realfileVector = new Vector();
        Vector savefileVector = new Vector();
        Vector fileseqVector = new Vector();

        /* sql 작성 변수 */
        String sql = "";

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            if (v_tabseq == 7 || v_tabseq == 5) {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	A.TABSEQ,\n";
                sql += "	A.SEQ, \n";
                sql += "	A.TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME \n";
                sql += "FROM \n";
                sql += "	TZ_HOMEQNA A, \n";
                sql += "	TZ_HOMEFILE B, \n";
                sql += "	TZ_MEMBER M \n";
                sql += "WHERE \n";
                sql += "	A.TABSEQ = " + v_tabseq + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND A.TYPES != " + StringManager.makeSQL(v_types) + " \n";
                sql += "	AND A.TABSEQ = B.TABSEQ(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.TYPES = B.TYPES(+) \n";
                sql += "	AND A.INUSERID = M.USERID \n";
            } else if (v_tabseq == 35) {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	A.TABSEQ,\n";
                sql += "	A.SEQ, \n";
                sql += "	A.TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME \n";
                sql += "FROM \n";
                sql += "	TZ_TORONCOMMUNITY A, \n";
                sql += "	TZ_BOARDFILE B, \n";
                sql += "	TZ_MEMBER M \n";
                sql += "WHERE \n";
                sql += "	A.TABSEQ = " + v_tabseq + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND A.TYPES != " + StringManager.makeSQL(v_types) + " \n";
                sql += "	AND A.TABSEQ = B.TABSEQ(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.INUSERID = M.USERID \n";
            } else if (v_tabseq == 0) {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	'0' AS TABSEQ, \n";
                sql += "	A.SEQ, \n";
                sql += "	A.KIND AS TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	A.SUBJ, \n";
                sql += "	A.SUBJSEQ, \n";
                sql += "	A.YEAR, \n";
                sql += "	A.LESSON, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME \n";
                sql += "FROM \n";
                sql += "	TZ_QNA A, \n";
                sql += "	TZ_QNAFILE B, \n";
                sql += "	TZ_MEMBER M \n";
                sql += "WHERE \n";
                sql += "	A.SUBJ = " + StringManager.makeSQL(v_subj) + " \n";
                sql += "	AND A.SUBJSEQ = " + StringManager.makeSQL(v_subjseq) + " \n";
                sql += "	AND A.YEAR = " + StringManager.makeSQL(v_year) + " \n";
                sql += "	AND A.LESSON = " + StringManager.makeSQL(v_lesson) + " \n";
                sql += "	AND A.SEQ = " + v_seq + " \n";
                sql += "	AND A.KIND != " + StringManager.makeSQL(v_types) + " \n";
                sql += "	AND A.SUBJ = B.SUBJ(+) \n";
                sql += "	AND A.YEAR = B.YEAR(+) \n";
                sql += "	AND A.SUBJSEQ = B.SUBJSEQ(+) \n";
                sql += "	AND A.LESSON = B.LESSON(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.KIND = B.KIND(+) \n";
                sql += "	AND A.INUSERID = M.USERID \n";
            } else {
                sql += "SELECT \n";
                sql += "	'" + v_tgubun + "' AS TGUBUN, \n";
                sql += "	A.TABSEQ, \n";
                sql += "	A.SEQ, \n";
                sql += "	A.LEVELS AS TYPES, \n";
                sql += "	A.TITLE, \n";
                sql += "	A.CONTENT AS CONTENTS, \n";
                sql += "	A.INDATE, \n";
                sql += "	A.USERID AS INUSERID, \n";
                sql += "	A.CNT, \n";
                sql += "	B.FILESEQ, \n";
                sql += "	B.REALFILE, \n";
                sql += "	B.SAVEFILE, \n";
                sql += "	M.NAME \n";
                sql += "FROM \n";
                sql += "	TZ_BOARD A, \n";
                sql += "	TZ_BOARDFILE B, \n";
                sql += "	TZ_MEMBER M \n";
                sql += "WHERE \n";
                sql += "	A.TABSEQ = " + v_tabseq + " \n";
                sql += "	AND A.REFSEQ = " + v_seq + " \n";
                sql += "	AND A.LEVELS != '1' \n";
                sql += "	AND A.TABSEQ = B.TABSEQ(+) \n";
                sql += "	AND A.SEQ = B.SEQ(+) \n";
                sql += "	AND A.USERID = M.USERID \n";
            }

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                if (!dbox.getString("d_realfile").equals("")) {
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
        String order_sql = "";

        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        int v_tabseq = box.getInt("p_tabseq");
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_lesson = box.getString("p_lesson");
        String v_tgubun = box.getString("p_tgubun");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();
            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate
            head_sql += " select a.seq,a.types,a.commentseq,a.inuserid,a.commentqna,a.cdate, b.name, a.gender ";
            body_sql += "   from TZ_COMMENTQNA a, tz_member b ";

            // 수정일 : 05.11.16 수정자 : 이나연 _ (+) 수정

            body_sql += "  where a.inuserid  =  b.userid(+) ";
            body_sql += "  and tabseq = " + v_tabseq;
            body_sql += "  and seq = " + v_seq;
            if (v_tgubun.equals("Q")) {
                body_sql += "  and subj = '" + v_subj + "'";
                body_sql += "  and year = '" + v_year + "'";
                body_sql += "  and subjseq = '" + v_subjseq + "'";
                body_sql += "  and lesson = '" + v_lesson + "'";
            }
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
    public int deleteBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 0;

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");
        int v_types = box.getInt("p_types");
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_lesson = box.getString("p_lesson");

        try {
            connMgr = new DBConnectionManager();
            if (v_tabseq == 5 || v_tabseq == 7) {
                if (v_types == 0) {
                    sql1 += "DELETE FROM TZ_HOMEQNA \n";
                    sql1 += "WHERE TABSEQ = ? \n";
                    sql1 += "	AND SEQ = ? \n";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setInt(1, v_tabseq);
                    pstmt1.setInt(2, v_seq);
                } else {
                    sql1 += "DELETE FROM TZ_HOMEQNA \n";
                    sql1 += "WHERE TABSEQ = ? \n";
                    sql1 += "	AND SEQ = ? \n";
                    sql1 += "	AND TYPES = ? \n";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setInt(1, v_tabseq);
                    pstmt1.setInt(2, v_seq);
                    pstmt1.setInt(2, v_types);
                }
            } else if (v_tabseq == 35) {
                if (v_types == 0) {
                    sql1 += "DELETE FROM TZ_TORONCOMMUNITY \n";
                    sql1 += "WHERE TABSEQ = ? \n";
                    sql1 += "	AND SEQ = ? \n";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setInt(1, v_tabseq);
                    pstmt1.setInt(2, v_seq);
                } else {
                    sql1 += "DELETE FROM TZ_TORONCOMMUNITY \n";
                    sql1 += "WHERE TABSEQ = ? \n";
                    sql1 += "	AND SESQ = ? \n";
                    sql1 += "	AND TYPES = ? \n";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setInt(1, v_tabseq);
                    pstmt1.setInt(2, v_seq);
                    pstmt1.setInt(3, v_types);
                }
            } else if (v_tabseq == 0) {
                if (v_types == 0) {
                    sql1 += "DELETE FROM TZ_QNA \n";
                    sql1 += "WHERE SUBJ = ? \n";
                    sql1 += "	AND SUBJSEQ = ? \n";
                    sql1 += "	AND YEAR = ? \n";
                    sql1 += "	AND LESSON = ? \n";
                    sql1 += "	AND SEQ = ? \n";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setString(1, v_subj);
                    pstmt1.setString(2, v_subjseq);
                    pstmt1.setString(3, v_year);
                    pstmt1.setString(4, v_lesson);
                    pstmt1.setInt(5, v_seq);
                } else {
                    sql1 += "DELETE FROM TZ_QNA \n";
                    sql1 += "WHERE SUBJ = ? \n";
                    sql1 += "	AND SUBJSEQ = ? \n";
                    sql1 += "	AND YEAR = ? \n";
                    sql1 += "	AND LESSON = ? \n";
                    sql1 += "	AND SEQ = ? \n";
                    sql1 += "	AND KIND = ? \n";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setString(1, v_subj);
                    pstmt1.setString(2, v_subjseq);
                    pstmt1.setString(3, v_year);
                    pstmt1.setString(4, v_lesson);
                    pstmt1.setInt(5, v_seq);
                    pstmt1.setInt(5, v_types);
                }
            } else {
                if (v_types == 1) {
                    sql1 = "DELETE FROM TZ_BOARD ";
                    sql1 += "WHERE TABSEQ = ? ";
                    sql1 += "	AND SEQ = ? ";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setInt(1, v_tabseq);
                    pstmt1.setInt(2, v_seq);
                } else {
                    sql1 = "DELETE FROM TZ_BOARD ";
                    sql1 += "WHERE TABSEQ = ? ";
                    sql1 += "	AND SEQ = ? ";
                    sql1 += "	AND LEVELS = ? ";
                    pstmt1 = connMgr.prepareStatement(sql1);
                    pstmt1.setInt(1, v_tabseq);
                    pstmt1.setInt(2, v_seq);
                    pstmt1.setInt(2, v_types);
                }
            }

            sql2 += "DELETE FROM TZ_COMMENTQNA \n";
            sql2 += "WHERE TABSEQ = ? \n";
            sql2 += "	AND SEQ = ? \n";
            if (v_types == 0) {
                sql2 += "	AND SUBJ = ? \n";
                sql2 += "	AND SUBJSEQ = ? \n";
                sql2 += "	AND YEAR = ? \n";
                sql2 += "	AND LESSON = ? \n";
            }
            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setInt(1, v_tabseq);
            pstmt2.setInt(2, v_seq);
            if (v_types == 0) {
                pstmt2.setString(3, v_subj);
                pstmt2.setString(4, v_subjseq);
                pstmt2.setString(5, v_year);
                pstmt2.setString(6, v_lesson);

            }

            isOk1 = pstmt1.executeUpdate();
            pstmt2.executeUpdate();
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
        return isOk1;
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
        int isOk1 = 1;

        String v_commentqna = box.getString("commentqna");
        int v_seq = box.getInt("p_seq");
        String v_types = box.getStringDefault("p_types", "0");
        int v_tabseq = box.getInt("p_tabseq");

        String s_userid = "";
        String s_gender = box.getSession("gender");

        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_lesson = box.getString("p_lesson");

        // if (s_gadmin.equals("A1")){
        // s_userid = "운영자";
        // }else{
        s_userid = box.getSession("userid");
        // }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            // ---------------------- 게시판 꼬릿말 번호를 가져온다 ----------------------------
            sql = "select isnull(max(commentseq), 0) from TZ_COMMENTQNA";
            sql += " where tabseq=" + v_tabseq + " and seq = " + v_seq + " ";

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_commentseq = ls.getInt(1) + 1;
            ls.close();
            // ------------------------------------------------------------------------------------

            // //////////////////////////////// 게시판 table 에 입력 ///////////////////////////////////////////////////////////////////
            sql1 = "insert into TZ_COMMENTQNA(tabseq, seq, types, commentseq, inuserid, commentqna, cdate, gender, subj, year, subjseq, lesson)";
            sql1 += "                values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?,?,?) ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, v_tabseq);
            pstmt1.setInt(2, v_seq);
            pstmt1.setString(3, v_types);
            pstmt1.setInt(4, v_commentseq);
            pstmt1.setString(5, s_userid);
            pstmt1.setString(6, v_commentqna);
            pstmt1.setString(7, s_gender);
            pstmt1.setString(8, v_subj);
            pstmt1.setString(9, v_year);
            pstmt1.setString(10, v_subjseq);
            pstmt1.setString(11, v_lesson);

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
        String v_types = box.getStringDefault("p_types", "0");
        int v_commentseq = box.getInt("p_commentseq");
        int v_tabseq = box.getInt("p_tabseq");

        try {
            connMgr = new DBConnectionManager();

            sql = " delete from TZ_COMMENTQNA    ";
            sql += "  where tabseq = ? and seq = ? and types = ? and commentseq = ? ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, v_tabseq);
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
     * 댓글 수정할때
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int updateComment(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk1 = 1;
        int v_seq = box.getInt("p_seq");
        String v_types = box.getStringDefault("p_types", "0");
        int v_commentseq = box.getInt("p_commentseq");
        int v_tabseq = box.getInt("p_tabseq");
        String v_commentqna = box.getString("p_commentqna");

        try {
            connMgr = new DBConnectionManager();

            sql = " UPDATE TZ_COMMENTQNA  \n";
            sql += " SET COMMENTQNA = ?  \n";
            sql += " WHERE TABSEQ = ? AND SEQ = ? AND TYPES = ? AND COMMENTSEQ = ? ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_commentqna);
            pstmt.setInt(2, v_tabseq);
            pstmt.setInt(3, v_seq);
            pstmt.setString(4, v_types);
            pstmt.setInt(5, v_commentseq);
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

        int v_tabseq = box.getInt("p_tabseq");
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        String v_year = box.getString("p_year");
        String v_lesson = box.getString("p_lesson");

        String s_userid = box.getSession("userid");
        String s_grcode = box.getSession("tem_grcode");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            if (v_tabseq == 7 || v_tabseq == 5) {
                sql = " update TZ_HOMEQNA set title = ? , contents = ?, luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
                sql += "  where tabseq = ? and seq = ? and types = ? and grcode = ?                       ";
                int index = 1;
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(index++, v_title);
                pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
                pstmt.setString(index++, s_userid);
                pstmt.setInt(index++, v_tabseq);
                pstmt.setInt(index++, v_seq);
                pstmt.setString(index++, v_types);
                pstmt.setString(index++, s_grcode);
            } else if (v_tabseq == 0) {
                sql = " update TZ_QNA set title = ?, contents = ?, luserid = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
                sql += " where subj = ? and year = ? and subjseq = ? and lesson = ? and seq = ? and kind = ?	";
                int index = 1;
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(index++, v_title);
                pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
                pstmt.setString(index++, s_userid);
                pstmt.setString(index++, v_subj);
                pstmt.setString(index++, v_year);
                pstmt.setString(index++, v_subjseq);
                pstmt.setString(index++, v_lesson);
                pstmt.setInt(index++, v_seq);
                pstmt.setString(index++, v_types);
            } else if (v_tabseq == 35) {
                sql = " update TZ_TORONCOMMUNITY set title = ?, contents = ?, luserid = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
                sql += " where tabseq = ? and seq = ? and types =? ";
                int index = 1;
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(index++, v_title);
                pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
                pstmt.setString(index++, s_userid);
                pstmt.setInt(index++, v_tabseq);
                pstmt.setInt(index++, v_seq);
                pstmt.setString(index++, v_types);
            } else {
                sql = " update TZ_BOARD set title = ?, content = ?, luserid = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
                sql += " where tabseq = ? and seq = ?	";
                int index = 1;
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(index++, v_title);
                pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
                pstmt.setString(index++, s_userid);
                pstmt.setInt(index++, v_tabseq);
                pstmt.setInt(index++, v_seq);
            }

            isOk = pstmt.executeUpdate();

            // isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq,v_types, box); // 파일첨부했다면 파일table에 insert

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
            if (p_tabseq == 5 || p_tabseq == 7) {
                sql = "select isnull(max(fileseq),0) from tz_homefile	where tabseq = " + p_tabseq + " and seq = " + p_seq + " and types = '" + types + "' ";
            }
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
