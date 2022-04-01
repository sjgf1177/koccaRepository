//**********************************************************
//1. 제      목: 문콘 파워인터뷰 (관리자, 사용자)
//2. 프로그램명: KOpenPowerBean
//3. 개      요: 파워인터뷰
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성:
//7. 수      정:
//**********************************************************
package com.credu.homepage;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

public class KOpenPowerBean {

    public KOpenPowerBean() {
    }

    /**
     * 리스트화면 select (관리자)
     * 
     * @param box receive from the form object and session
     * @return ArrayList 자료실 리스트
     * @throws Exception
     */
    public ArrayList<DataBox> selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        ListSet ls = null;
        DataBox dbox = null;

        String sql = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String countSql = "";
        String orderSql = "";

        int v_pageno = box.getInt("p_pageno");
        String v_process = box.getString("p_process");
        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        //String  tem_grcode   = box.getStringDefault("tem_grcode", box.getString("tem_grcode"));
        //String  v_gubun   		= box.getStringDefault("p_gubun","K");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            headSql.append(" SELECT  A.SEQ, A.TITLE, A.COMPTEXT, A.USERID, A.LNAME  \n ");
            headSql.append("         , A.SAVEFILE, A.REALFILE, A.INDATE, A.TARGET  \n ");
            headSql.append("         , A.USEYN, A.CNT, B.CODENM TARGETNM           \n ");
            bodySql.append(" FROM    TZ_SUPERIORITY A, TZ_CODE B                   \n ");
            bodySql.append(" WHERE   A.TARGET    = B.CODE(+)                       \n ");
            bodySql.append(" AND     B.GUBUN(+)  = '0090'                          \n ");

            if (!v_searchtext.equals("")) { //    검색어가 있으면

                if (v_search.equals("name")) { //    이름으로 검색할때
                    bodySql.append(" AND LNAME LIKE LOWER(" + StringManager.makeSQL("%" + v_searchtext + "%") + ") \n ");
                } else if (v_search.equals("title")) { //    제목으로 검색할때
                    bodySql.append(" AND TITLE LIKE LOWER(" + StringManager.makeSQL("%" + v_searchtext + "%") + ") \n ");
                } else if (v_search.equals("contents")) { //    내용으로 검색할때
                    bodySql.append(" AND CONTENTS LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + "  \n ");
                }
            }

            orderSql += " order by seq desc";
            if (v_process.equals("main")) {
                orderSql += " WHERE RNUM < 2";
            }
            sql = headSql.toString() + bodySql.toString() + orderSql;
            ls = connMgr.executeQuery(sql);

            countSql = "select count(*) " + bodySql.toString();
            int total_row_count = BoardPaging.getTotalRow(connMgr, countSql);

            ls.setPageSize(10); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); // 현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage(); // 전체 페이지 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(10));
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
     * 등록하기
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;
        Statement stmt1 = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        StringBuffer sql = new StringBuffer();
        String sql1 = "";
        int isOk1 = 0;
        int v_seq = 0;

        String v_gubun = box.getStringDefault("p_gubun", "K");
        //String tem_grcode   = box.getStringDefault("tem_grcode", box.getString("tem_grcode"));
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        //String v_singleword = box.getString("p_singleword");
        String v_useyn = box.getString("p_useyn");
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        String v_comptext = box.getString("p_comptext");
        String v_realfile = box.getRealFileName("p_file1");
        String v_savefile = box.getNewFileName("p_file1");
        String v_target = box.getString("p_target");
        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("name");

        if (v_gubun.equals("") || v_gubun.equals("K")) {
            v_gubun = "K";
        } else {
            v_gubun = "G";
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
            sql1 = "select NVL(max(seq), 0) from TZ_SUPERIORITY ";
            rs1 = stmt1.executeQuery(sql1);
            if (rs1.next()) {
                v_seq = rs1.getInt(1) + 1;
            }
            rs1.close();
            //-------------------------------------------------------------------------

            /*********************************************************************************************/
            // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다. 
            try {
                v_content = (String) NamoMime.setNamoContent(v_content);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            //----------------------   게시판 table 에 입력  --------------------------
            sql.append(" INSERT INTO TZ_SUPERIORITY                                     \n ");
            sql.append(" (                                                              \n ");
            sql.append("     SEQ, TITLE, CONTENTS, SAVEFILE, REALFILE, USEYN            \n ");
            sql.append("     , COMPTEXT, TARGET, USERID, LUSERID, LNAME, INDATE, LDATE  \n ");
            sql.append(" )                                                              \n ");
            sql.append(" VALUES                                                         \n ");
            sql.append(" (                                                              \n ");
            sql.append("     ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?                            \n ");
            sql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')                     \n ");
            sql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')                     \n ");
            sql.append(" )                                                              \n ");

            int index = 1;
            pstmt1 = connMgr.prepareStatement(sql.toString());
            pstmt1.setInt(index++, v_seq);
            pstmt1.setString(index++, v_title);
            pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            pstmt1.setString(index++, v_savefile);
            pstmt1.setString(index++, v_realfile);
            pstmt1.setString(index++, v_useyn);
            pstmt1.setString(index++, v_comptext);
            pstmt1.setString(index++, v_target);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);

            isOk1 = pstmt1.executeUpdate();

            if (isOk1 > 0)
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
        return isOk1;
    }

    /**
     * 선택된 게시물 상세내용 select
     * 
     * @param box receive from the form object and session
     * @return ArrayList 조회한 상세정보
     * @throws Exception
     */
    public DataBox selectView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_process = box.getString("p_process");

        try {
            connMgr = new DBConnectionManager();

            sql.append(" SELECT  A.SEQ, A.TITLE, A.CONTENTS, A.SAVEFILE             \n ");
            sql.append("         , A.REALFILE, A.USEYN, A.COMPTEXT, A.TARGET        \n ");
            sql.append("         , A.USERID, A.LNAME, A.INDATE, B.CODENM TARGETNM   \n ");
            sql.append(" FROM    TZ_SUPERIORITY A, TZ_CODE B                        \n ");
            sql.append(" WHERE   A.TARGET    = B.CODE(+)                            \n ");
            sql.append(" AND     B.GUBUN(+)  = '0090'                               \n ");
            sql.append(" AND     SEQ         = " + v_seq);

            ls = connMgr.executeQuery(sql.toString());

            for (int i = 0; ls.next(); i++) {

                dbox = ls.getDataBox();

            }

            if (v_process.equals("select")) {
                connMgr.executeUpdate("update TZ_SUPERIORITY set cnt = cnt + 1 where seq = " + v_seq);
            }
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return dbox;
    }

    /**
     * 수정하기
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int update(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        StringBuffer sql = new StringBuffer();

        int isOk1 = 1;
        int isOk2 = 1;

        int v_seq = box.getInt("p_seq");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_useyn = box.getString("p_useyn");
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        String v_comptext = box.getString("p_comptext");
        String v_realfile = box.getRealFileName("p_file1");
        String v_savefile = box.getNewFileName("p_file1");
        String v_target = box.getString("p_target");
        String s_userid = box.getSession("userid");

        s_userid = box.getSession("userid");

        String v_fileDel = box.getString("p_fildel");
        String delfile = "";

        if (v_realfile.equals("")) {
            if (!v_fileDel.equals(""))
                delfile = box.getString("p_savefile");
        } else {

            if (!v_fileDel.equals(""))
                delfile = box.getString("p_savefile");
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //-------------------------------------------------------------------------
            // 파일 관련

            /*********************************************************************************************/
            // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다. 

            try {
                v_content = (String) NamoMime.setNamoContent(v_content);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            sql.append(" UPDATE TZ_SUPERIORITY SET                               \n ");
            sql.append("     TITLE       = ?, CONTENTS  = ?                      \n ");
            sql.append("     , TARGET    = ?, SAVEFILE   = ?                     \n ");
            sql.append("     , REALFILE  = ?, USEYN      = ?                     \n ");
            sql.append("     , COMPTEXT  = ?, LUSERID    = ?                     \n ");
            sql.append("     , LDATE     = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  \n ");
            sql.append(" WHERE   SEQ     = ?                                     \n ");

            int index = 1;
            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setString(index++, v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            pstmt.setString(index++, v_target);
            pstmt.setString(index++, v_savefile);
            pstmt.setString(index++, v_realfile);
            pstmt.setString(index++, v_useyn);
            pstmt.setString(index++, v_comptext);
            pstmt.setString(index++, s_userid);
            pstmt.setInt(index++, v_seq);

            isOk1 = pstmt.executeUpdate();

            if (isOk1 > 0) {
                if (!delfile.equals("")) {
                    FileManager.deleteFile(delfile); //	 DB	에서 모든처리가	완료되면 해당 첨부파일 삭제
                }
                connMgr.commit();
            }

        }

        catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
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
    public int delete(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        int isOk1 = 0;

        int v_seq = box.getInt("p_seq");

        String v_savefile = box.getString("p_savefile");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            isOk1 = 1;
            sql1 = "delete from TZ_SUPERIORITY where seq = ? ";
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, v_seq);
            isOk1 = pstmt1.executeUpdate();

            if (isOk1 > 0) {
                connMgr.commit();
                if (!v_savefile.equals("")) {
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
        return isOk1;

    }
}