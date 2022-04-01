//**********************************************************
//1. 제      목: 게시판(어드민)
//2. 프로그램명: HomePageSuperiortyMemberBean
//3. 개      요: 게시판
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: mscho 2004.01.15
//7. 수      정:
//**********************************************************
package com.credu.homepage;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.namo.SmeNamoMime;

public class HomePageSuperiorityMemberBean {

    private ConfigSet config;
    private int row;
    private static final String FILE_TYPE = "p_file"; //      파일업로드되는 tag name
    private static final int FILE_LIMIT = 1; //    페이지에 세팅된 파일첨부 갯수

    public HomePageSuperiorityMemberBean() {
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
     * @return ArrayList 자료실 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectBoardList(RequestBox box) throws Exception {
        // PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        //			2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        int v_pageno = box.getInt("p_pageno");
        String tem_grcode = box.getSession("tem_grcode");
        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        String v_gubun = box.getStringDefault("p_gubun", "G");

        if (v_gubun.equals("")) {
            v_gubun = "K";
        }
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql += " Select seq, userid, lname, title, singleword, contents, savefile, realfile, ldate, cnt, useyn, lmonth  ";
            body_sql += " From TZ_SUPERIORITY ";
            body_sql += " Where gubun = '" + v_gubun + "' and grcode = '" + tem_grcode + "' ";

            if (!v_searchtext.equals("")) { //    검색어가 있으면
                //v_pageno = 1;   //      검색할 경우 첫번째 페이지가 로딩된다

                if (v_search.equals("name")) { //    이름으로 검색할때
                    body_sql += " and lname like lower(" + StringManager.makeSQL("%" + v_searchtext + "%") + ")";
                } else if (v_search.equals("title")) { //    제목으로 검색할때
                    body_sql += " and title like lower(" + StringManager.makeSQL("%" + v_searchtext + "%") + ")";
                } else if (v_search.equals("contents")) { //    내용으로 검색할때
                    //sql += " and dbms_lob.instr(a.content, " + StringManager.makeSQL(v_searchtext) + ",1,1) <> 0";
                    body_sql += " and contents like " + StringManager.makeSQL("%" + v_searchtext + "%"); //   Oracle 9i 용
                }
            }

            order_sql += " order by seq desc ";

            sql = head_sql + body_sql + group_sql + order_sql;
            System.out.println(" sql : " + sql);
            ls = connMgr.executeQuery(sql);

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
     * 새로운 자료실 내용 등록
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;
        Statement stmt1 = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql1 = "";
        // String sql2 = "";
        // String v_type = "";
        int isOk1 = 1;
        int isOk2 = 1;
        int v_seq = 0;

        String tem_grcode = box.getStringDefault("tem_grcode", "N000002");
        String v_gubun = box.getString("p_gubun");
        String v_title = box.getString("p_title");
        String v_useyn = box.getString("p_useyn");
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        String v_singleword = box.getString("p_singleword");
        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("name");
        String v_gubunnm = "";
        String v_month = box.getString("p_month");

        if (v_gubun.equals("") || v_gubun.equals("G")) {
            v_gubun = "G";
            v_gubunnm = "game";
        } else {
            v_gubun = "K";
            v_gubunnm = "kocca";
        }

        if (box.getSession("gadmin").substring(0, 1).equals("A1")) {
            s_userid = "운영자";
        } else {
            s_userid = box.getSession("userid");
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            stmt1 = connMgr.createStatement();

            //----------------------   게시판 번호 가져온다 ----------------------------
            sql = "select NVL(max(seq), 0) from TZ_SUPERIORITY where gubun = '" + v_gubun + "'";
            rs1 = stmt1.executeQuery(sql);
            if (rs1.next()) {
                v_seq = rs1.getInt(1) + 1;
            }
            rs1.close();
            //-------------------------------------------------------------------------

            /*********************************************************************************************/
            // 나모에디터 본문 처리  (Mime Document Parsing 및 이미지 업로드)
            ConfigSet conf = new ConfigSet();
            SmeNamoMime namo = new SmeNamoMime(v_content); // 객체생성
            boolean result = namo.parse(); // 실제 파싱 수행
            if (!result) { // 파싱 실패시
                System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
                return 0;
            }
            if (namo.isMultipart()) { // 문서가 멀티파트인지 판단
                String v_server = conf.getProperty(v_gubunnm + ".url.value");
                String fPath = conf.getProperty("dir.namo"); // 파일 저장 경로 지정
                String refUrl = conf.getProperty("url.namo");
                ; // 웹에서 저장된 파일을 접근하기 위한 경로
                String prefix = "superiority_" + v_seq; // 파일명 접두어
                result = namo.saveFile(fPath, v_server + refUrl, prefix); // 실제 파일 저장
            }
            if (!result) { // 파일저장 실패시
                System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
                return 0;
            }
            v_content = namo.getContent(); // 최종 컨텐트 얻기
            /*********************************************************************************************/

            //----------------------   게시판 table 에 입력  --------------------------
            sql1 = " insert into TZ_SUPERIORITY (seq, userid, lname, title, singleword, contents, ldate, gubun, useyn,lmonth, grcode, cnt)  ";
            //	            sql1 += " values (?, ?, ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?, 0)    ";
            sql1 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?, 0)    ";

            int index = 1;
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);
            pstmt1.setString(index++, v_title);
            pstmt1.setString(index++, v_singleword);
            pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            pstmt1.setString(index++, v_gubun);
            pstmt1.setString(index++, v_useyn);
            pstmt1.setString(index++, v_month);
            pstmt1.setString(index++, tem_grcode);

            isOk1 = pstmt1.executeUpdate();

            //				 파일업로드
            isOk2 = this.insertUpFile(connMgr, v_seq, box);

            if (v_useyn.equals("Y")) {
                sql = "Update TZ_SUPERIORITY set useyn = 'N' where seq <> ? ";

                pstmt2 = connMgr.prepareStatement(sql);
                pstmt2.setInt(1, v_seq);
                isOk1 = pstmt2.executeUpdate();

            }
            //				sql1 = "select contents from TZ_SUPERIORITY where seq = '"+v_seq+"' ";
            //				connMgr.setOracleCLOB(sql1, v_content);
            connMgr.commit();

        }

        catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
            }
            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
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
        return isOk1 * isOk2;
    }

    /**
     * 선택된 게시물 상세내용 select
     * 
     * @param box receive from the form object and session
     * @return ArrayList 조회한 상세정보
     * @throws Exception
     */
    public DataBox selectBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_upcnt = "Y";
        String v_gubun = box.getStringDefault("p_gubun", "G");

        if (v_gubun.equals("")) {
            v_gubun = "K";
        }

        int v_seq = box.getInt("p_seq");

        // Vector realfileVector = new Vector();
        // Vector savefileVector = new Vector();
        // Vector fileseqVector = new Vector();
        try {
            connMgr = new DBConnectionManager();

            sql = " Select ";
            sql += " 	seq, userid, lname, title, singleword, contents, savefile, realfile, ";
            sql += "	ldate, cnt , useyn, gubun, lmonth ";
            sql += " From TZ_SUPERIORITY ";
            sql += " Where gubun = '" + v_gubun + "' and seq = " + v_seq;

            ls = connMgr.executeQuery(sql);

            for (int i = 0; ls.next(); i++) {

                dbox = ls.getDataBox();

            }

            if (!v_upcnt.equals("N")) {
                connMgr.executeUpdate("update TZ_SUPERIORITY set cnt = cnt + 1 where  gubun = '" + v_gubun + "' and seq = " + v_seq);
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
     * 새로운 자료실 내용 등록
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int updateBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;
        Statement stmt1 = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql1 = "";
        // String sql2 = "";
        // String v_type = "";
        int isOk1 = 1;
        int isOk2 = 1;
        // int isOk3 = 1;

        int v_seq = box.getInt("p_seq");
        String v_gubun = box.getString("p_gubun");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_useyn = box.getString("p_useyn");
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        String v_singleword = box.getString("p_singleword");
        String v_month = box.getString("p_month");
        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("name");
        String v_gubunnm = "";

        int v_filecnt = box.getInt("p_filecnt");

        String v_server = "";
        String fPath = "";
        String refUrl = "";
        String prefix = "";

        if (v_gubun.equals("") || v_gubun.equals("K")) {
            v_gubun = "K";
            v_gubunnm = "kocca";
        } else {
            v_gubun = "G";
            v_gubunnm = "game";
        }

        if (box.getSession("gadmin").substring(0, 1).equals("A1")) {
            s_userid = "운영자";
        } else {
            s_userid = box.getSession("userid");
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //-------------------------------------------------------------------------
            // 파일 관련

            /*********************************************************************************************/
            // 나모에디터 본문 처리  (Mime Document Parsing 및 이미지 업로드)
            ConfigSet conf = new ConfigSet();
            SmeNamoMime namo = new SmeNamoMime(v_content); // 객체생성
            boolean result = namo.parse(); // 실제 파싱 수행

            if (!result) { // 파싱 실패시
                System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
                return 0;
            }
            if (namo.isMultipart()) { // 문서가 멀티파트인지 판단
                v_server = conf.getProperty(v_gubunnm + ".url.value");
                fPath = conf.getProperty("dir.namo"); // 파일 저장 경로 지정
                refUrl = conf.getProperty("url.namo");
                ; // 웹에서 저장된 파일을 접근하기 위한 경로
                prefix = "superiority_" + v_seq; // 파일명 접두어
                result = namo.saveFile(fPath, v_server + refUrl, prefix); // 실제 파일 저장
            }
            if (!result) { // 파일저장 실패시
                System.out.println(namo.getDebugMsg()); // 디버깅 메시지 출력
                return 0;
            }
            v_content = namo.getContent(); // 최종 컨텐트 얻기
            /*********************************************************************************************/

            //----------------------   게시판 table 에 입력  --------------------------
            sql1 = " Update TZ_SUPERIORITY Set userid = ?,  lname = ?, title = ?,";
            //				sql1 += " singleword=?, contents=empty_clob(), ldate=to_char(sysdate, 'YYYYMMDDHH24MISS')";
            sql1 += " singleword=?, contents=?, ldate=to_char(sysdate, 'YYYYMMDDHH24MISS')";
            sql1 += " ,useyn = ? , lmonth = ? Where seq = ?";

            int index = 1;
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);
            pstmt1.setString(index++, v_title);
            pstmt1.setString(index++, v_singleword);
            pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            //pstmt1.setString(index++, v_content);
            pstmt1.setString(5, v_useyn);
            pstmt1.setString(index++, v_month);
            pstmt1.setInt(index++, v_seq);

            isOk1 = pstmt1.executeUpdate();
            if (v_filecnt > 0) {
                //기존파일 삭제
                // isOk3 = this.deleteUpFile(connMgr, box, v_seq);
                this.deleteUpFile(connMgr, box, v_seq);
                //파일업로드
                isOk2 = this.insertUpFile(connMgr, v_seq, box);
            }

            if (v_useyn.equals("Y")) {
                sql = "Update TZ_SUPERIORITY set useyn = 'N' where seq <> ? ";

                pstmt2 = connMgr.prepareStatement(sql);
                pstmt2.setInt(1, v_seq);
                isOk1 = pstmt2.executeUpdate();
            }
            connMgr.commit();
        }

        catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
            }
            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
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
        return isOk1 * isOk2;
    }

    /**
     * 선택된 게시물 삭제
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int deleteBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        // String sql2 = "";
        int isOk1 = 0;
        int isOk2 = 0;

        int v_seq = box.getInt("p_seq");
        String v_gubun = box.getString("p_gubun");

        Vector v_savefile = box.getVector("p_savefile");
        int v_upfilecnt = v_savefile.size(); //  서버에 저장되있는 파일수
        // System.out.println("savefile =======>" + v_savefile + " size ===>" + v_savefile.size());

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            isOk1 = 1;
            isOk2 = 1;
            sql1 = "delete from TZ_SUPERIORITY where gubun = ? and seq = ? ";
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setString(1, v_gubun);
            pstmt1.setInt(2, v_seq);
            isOk1 = pstmt1.executeUpdate();

            if (isOk1 > 0 && isOk2 > 0) {
                connMgr.commit();
                if (v_upfilecnt > 0) {
                    FileManager.deleteFile(v_savefile); //   첨부파일 삭제
                }
            } else
                connMgr.rollback();

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
        return isOk1 * isOk2;

    }

    /**
     * 선택된 게시물 상세내용 select
     * 
     * @param box receive from the form object and session
     * @return ArrayList 조회한 상세정보
     * @throws Exception
     */
    public int MainSelect(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;
        // Statement stmt1 = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        // String v_type = "";
        int isOk1 = 1;
        int isOk2 = 1;
        // int isOk3 = 1;

        int v_seq = box.getInt("p_select_seq");
        // String v_gubun = box.getString("p_gubun");

        try {
            connMgr = new DBConnectionManager();

            sql = "Update TZ_SUPERIORITY set useyn = 'Y' where seq = ? ";

            pstmt1 = connMgr.prepareStatement(sql);
            pstmt1.setInt(1, v_seq);
            isOk1 = pstmt1.executeUpdate();

            sql2 = "Update TZ_SUPERIORITY set useyn = 'N' where seq <> ? ";

            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setInt(1, v_seq);
            isOk1 = pstmt2.executeUpdate();
        }

        catch (Exception ex) {
            // if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
            //if(connMgr != null) { try { connMgr.setAutoCommit(true); } catch (Exception e) {} }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk1 * isOk2;
    }

    ///////////////////////////////////////////////////////  파일 테이블   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * 새로운 자료파일 등록
     * 
     * @param connMgr DB Connection Manager
     * @param p_seq 게시물 일련번호
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertUpFile(DBConnectionManager connMgr, int p_seq, RequestBox box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        // String sql = "";
        String sql2 = "";
        int isOk2 = 1;
        //----------------------   업로드되는 파일의 형식을 알고 코딩해야한다  --------------------------------
        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];

        for (int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));
        }

        // String s_userid = box.getSession("userid");

        try {

            //////////////////////////////////   파일 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql2 = "Update  TZ_SUPERIORITY set realfile = ? , savefile = ? ";
            sql2 += " Where seq = ?";

            pstmt2 = connMgr.prepareStatement(sql2);

            if (!v_realFileName[0].equals("")) { //      실제 업로드 되는 파일만 체크해서 db에 입력한다
                System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@" + v_realFileName[0]);
                pstmt2.setString(1, v_realFileName[0]);
                pstmt2.setString(2, v_newFileName[0]);
                pstmt2.setInt(3, p_seq);

                isOk2 = pstmt2.executeUpdate();
            }

        } catch (Exception ex) {
            FileManager.deleteFile(v_newFileName, FILE_LIMIT); //  일반파일, 첨부파일 있으면 삭제..
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
    @SuppressWarnings("null")
    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box, int v_seq) throws Exception {
        PreparedStatement pstmt3 = null;
        Statement stmt1 = null;
        String sql = "";
        String sql3 = "";
        // ListSet ls = null;
        ResultSet rs1 = null;
        int isOk3 = 1;

        String v_gubun = box.getString("p_gubun");
        String[] v_delfile = null;

        try {
            stmt1 = connMgr.createStatement();

            //----------------------   게시판 번호 가져온다 ----------------------------
            sql = "select savefile from TZ_SUPERIORITY where gubun = '" + v_gubun + "' and seq = " + v_seq;
            rs1 = stmt1.executeQuery(sql);

            if (rs1.next()) {
                v_delfile[0] = rs1.getString(1);
            }
            rs1.close();

            sql = "Update  TZ_SUPERIORITY set realfile = '' , savefile = '' ";
            sql += " Where seq = ?";

            pstmt3 = connMgr.prepareStatement(sql);
            pstmt3.setInt(1, v_seq);
            isOk3 = pstmt3.executeUpdate();

            FileManager.deleteFile(v_delfile, FILE_LIMIT); //  일반파일, 첨부파일 있으면 삭제..
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

    //================================================================================================================

    /**
     * 선택된 게시물 상세내용 select ( 메인 )
     * 
     * @param box receive from the form object and session
     * @return ArrayList 조회한 상세정보
     * @throws Exception
     */
    public DataBox selectBoardHome(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "", sql1 = "";
        DataBox dbox = null;
        String v_upcnt = "Y";
        String v_gubun = box.getString("p_gubun");
        // String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
        String v_process = box.getString("p_process");
        System.out.println("v_process :" + v_process);

        if (v_gubun.equals("")) {
            v_gubun = "K";
        }

        int v_seq = box.getInt("p_seq");

        // Vector realfileVector = new Vector();
        // Vector savefileVector = new Vector();
        // Vector fileseqVector = new Vector();
        try {
            connMgr = new DBConnectionManager();

            if (v_process.equals("main")) {
                sql = " select * from ( select rownum rnum, ";
            } else {
                sql = "Select ";
            }
            sql += " 	seq, userid, lname, title, singleword, contents, savefile, realfile, ";
            sql += "	ldate, cnt , useyn, gubun, lmonth ";
            sql += " From TZ_SUPERIORITY ";
            sql += " Where useyn = 'Y'";
            if (v_process.equals("main"))
                sql = " ) where rnum < 2 ";

            //			if(tem_grcode.equals("N000002")){
            //				sql += " 	and gubun = 'G'";
            //			}else{
            //				sql += " 	and gubun = 'K'";
            //			}
            System.out.println("sql :" + sql);
            ls = connMgr.executeQuery(sql);

            for (int i = 0; ls.next(); i++) {
                dbox = ls.getDataBox();
            }

            sql1 = "update TZ_SUPERIORITY set cnt = cnt + 1 where seq = " + v_seq;
            //			sql1 +=" gubun = '" + v_gubun + "' ";

            if (!v_upcnt.equals("N")) {
                connMgr.executeUpdate(sql1);
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