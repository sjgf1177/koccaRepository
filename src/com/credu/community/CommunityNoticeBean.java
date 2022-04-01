//**********************************************************
//  1. 제      목: 커뮤니티 공지사항
//  2. 프로그램명: CommunityNoticeBean.java
//  3. 개      요: 공지사항
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 02. 16
//  7. 수      정:
//**********************************************************

package com.credu.community;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;

public class CommunityNoticeBean {
//    private static final String FILE_TYPE = "p_file";           //      파일업로드되는 tag name
//    private static final int FILE_LIMIT = 5;                    //    페이지에 세팅된 파일첨부 갯수
    private ConfigSet config;
    private int row;

    public CommunityNoticeBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * 리스트화면 select
    * @param    box          receive from the form object and session
    * @return ArrayList  공지사항 리스트
    * @throws Exception
    */
    public ArrayList selectBoardList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        int v_commid = box.getInt("p_commId");
        int v_pageno = box.getInt("p_pageno");
        String v_searchtext = box.getString("p_searchtext");
        String v_search     = box.getString("p_search");
        int    v_row        = box.getInt("p_row");
        if(v_row > 0)     row = v_row;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql  = " select a.seq seq, a.userid userid, a.name name, a.title title,                            ";
            sql += "        a.indate indate, a.cnt cnt, a.refseq refseq, a.levels levels, a.position position  ";
            sql += "   from TZ_COMMUNITY_NOTICE a                                                               ";
            sql += "  where a.commid = " + v_commid;
            if ( !v_searchtext.equals("")) {                //    검색어가 있으면
                if (v_search.equals("name")) {              //    이름으로 검색할때
                    sql += " and a.name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_search.equals("title")) {        //    제목으로 검색할때
                    sql += " and a.title like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
                else if (v_search.equals("content")) {     //    내용으로 검색할때
                    sql += " and dbms_lob.instr(a.content, " + StringManager.makeSQL(v_searchtext) + ",1,1) <> 0";
                }

            }
            sql += " group by a.seq, a.userid, a.name, a.title, a.indate, a.cnt, a.refseq, a.levels, a.position ";
            sql += " order by a.refseq desc, position asc                                                       ";

System.out.println("sql =>" + sql );
            ls = connMgr.executeQuery(sql);

            ls.setPageSize(row);                       //     페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno);               //     현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage();    //     전체 페이지 수를 반환한다
            int totalrowcount = ls.getTotalCount();    //     전체 row 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpagecount", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(row));

                list.add(dbox);
            }
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

   /**
   * 선택된 자료실 게시물 상세내용 select
   * @param box          receive from the form object and session
   * @return ArrayList   조회한 상세정보
   * @throws Exception
   */
   public DataBox selectBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        int v_commid    = box.getInt("p_commId");
        int v_seq       = box.getInt("p_seq");
        int v_upfilecnt = (box.getInt("p_upfilecnt")>0?box.getInt("p_upfilecnt"):1);

       // Vector realfileVector = new Vector();
       // Vector savefileVector = new Vector();
       // Vector fileseqVector  = new Vector();

        try {
            connMgr = new DBConnectionManager();


            sql  = " select a.seq seq, a.userid userid, a.name name, a.title title,a.content content,  ";
            sql += " a.indate indate, a.cnt cnt, a.refseq refseq, a.levels levels, a.position position              ";
            sql += " from TZ_COMMUNITY_NOTICE a                                                                                      ";
            sql += " where a.commid  = " + v_commid;
            sql += "    and a.seq    = " + v_seq;
System.out.println("sql selectBOard==============" + sql);
            ls = connMgr.executeQuery(sql);

            for (int i = 0; ls.next(); i++) {
                dbox=ls.getDataBox();
            }

            connMgr.executeUpdate("update TZ_COMMUNITY_NOTICE set cnt = cnt + 1 where  commid = " + v_commid + " and seq = "+ v_seq);
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }


    /**
    * 새로운 자료실 내용 등록
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
     public int insertBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;
        Statement stmt1 = null;
        PreparedStatement pstmt1  = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int v_seq = 0;

        int    v_commid  = box.getInt("p_commId");
        String v_title   = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));

        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("name");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            stmt1 = connMgr.createStatement();

            //----------------------   게시판 번호 가져온다 ----------------------------
            sql = "select NVL(max(seq), 0) from TZ_COMMUNITY_NOTICE where commid = " + v_commid;
            rs1 = stmt1.executeQuery(sql);
            if (rs1.next()) {
              v_seq = rs1.getInt(1) + 1;
            }
            //------------------------------------------------------------------------------------

            //////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql1 =  " insert into TZ_COMMUNITY_NOTICE(commid, seq, userid, name, indate, title, content, cnt, refseq, levels, position, luserid, ldate)  ";
            //2005.11.16_하경태 : Oracle -> Mssql empty_clob() 제거 
//			sql1 += " values (?, ?, ?, ?, to_char(sysdate,  'YYYYMMDDHH24MISS'), ?, empty_clob(), ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))    ";
			sql1 += " values (?, ?, ?, ?, to_char(sysdate,  'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))    ";

int index = 1;
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setInt(index++, v_commid);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);
            pstmt1.setString(index++, v_title);
            pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			//pstmt1.setString(index++, v_content);
            pstmt1.setInt(index++, 0);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setInt(index++, 1);
            pstmt1.setInt(index++, 1);
            pstmt1.setString(index++, s_userid);

            isOk1 = pstmt1.executeUpdate();

//            sql2 = "select content from TZ_COMMUNITY_NOTICE where commid= " + v_commid + " and seq = " + v_seq + " for update";
//            connMgr.setOracleCLOB(sql2, v_content);       //      clob 필드에 스트림을 이용,  data 를 넣는다(Weblogic 인 경우)       
			/*2005.11.16_하경태 : Oracle -> Mssql empty_clob() 제거 
        
            if(isOk1 > 0 ) {    
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }  
            }else {
                if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }  
            }
            */
            if(isOk1 > 0 ) {    
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }  
            }else {
                if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }  
            }
        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }  
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(rs1 != null) { try { rs1.close(); } catch (Exception e) {} }
            if(stmt1 != null) { try { stmt1.close(); } catch (Exception e1) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1;
    }

    /**
    * 선택된 자료 상세내용 수정
    * @param box      receive from the form object and session
    * @return isOk    1:update success,0:update fail
    * @throws Exception
    */
     public int updateBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;
        PreparedStatement pstmt1  = null;
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;

        int    v_commid    = box.getInt("p_commId");
        int    v_seq       = box.getInt("p_seq");
        String v_title     = StringUtil.removeTag(box.getString("p_title"));
        String v_content   = StringUtil.removeTag(box.getString("p_content"));

        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("name");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //2005.11.16_하경태 : Oracle -> Mssql empty_clob() 제거 
//			sql1 = "update TZ_COMMUNITY_NOTICE set title = ?, content=empty_clob(), userid = ?, name = ?, luserid = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
			sql1 = "update TZ_COMMUNITY_NOTICE set title = ?, content=?, userid = ?, name = ?, luserid = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
            sql1 += "  where commid = ? and seq = ?";

int index = 1;
            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setString(index++, v_title);
            pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
//			pstmt1.setString(index++, v_content);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);
            pstmt1.setString(index++, s_userid);
            pstmt1.setInt(index++, v_commid);
            pstmt1.setInt(index++, v_seq);

            isOk1 = pstmt1.executeUpdate();

//            sql2 = "select content from TZ_COMMUNITY_NOTICE where commid= " + v_commid + " and seq = " + v_seq + " for update";
//            connMgr.setOracleCLOB(sql2, v_content);       //      clob 필드에 스트림을 이용,  data 를 넣는다(Weblogic 인 경우)       
            //WebLogic 6.1인경우 
			/*2005.11.16_하경태 : Oracle -> Mssql empty_clob() 제거 

            */
            if(isOk1 > 0) connMgr.commit();
            else connMgr.rollback();
        }catch(Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(rs1 != null) { try { rs1.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1;
    }


    /**
    * 선택된 게시물 삭제
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */
    public int deleteBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 0;
        int isOk2 = 0;

        int v_commid = box.getInt("p_commId");
        int v_seq = box.getInt("p_seq");

        // 답변 유무 체크(답변 있을시 삭제불가)
        if (this.selectBoard(v_commid, v_seq) == 0) {

            try {
                connMgr = new DBConnectionManager();

                isOk1 = 1;
                isOk2 = 1;
                sql1 = "delete from TZ_COMMUNITY_NOTICE where commid = ? and seq = ? ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(1, v_commid);
                pstmt1.setInt(2, v_seq);
                isOk1 = pstmt1.executeUpdate();

            }catch (Exception ex) {
                ErrorManager.getErrorStackTrace(ex, box,    sql1);
                throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
            }
            finally {
                if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
        }
        return isOk1;
    }

   /**
   * 삭제시 하위 답변 유무 체크
   * @param seq          게시판 번호
   * @return result      0 : 답변 없음,    1 : 답변 있음
   * @throws Exception
   */
   public int selectBoard(int commid, int seq) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        try {
            connMgr = new DBConnectionManager();

            sql  = "  select count(*) cnt                         ";
            sql += "  from                                        ";
            sql += "    (select commid, refseq, levels, position  ";
            sql += "       from TZ_COMMUNITY_NOTICE                          ";
            sql += "      where commid = " + commid;
            sql += "        and seq = " + seq;
            sql += "     ) a, TZ_COMMUNITY_NOTICE b                          ";
            sql += " where a.commid = b.commid                    ";
            sql += "   and a.refseq = b.refseq                    ";
            sql += "   and b.levels = (a.levels+1)                ";
            sql += "   and b.position = (a.position+1)            ";


            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return result;
    }

    public static String convertBody(String contents) throws Exception {
        String result = "";

        result = StringManager.replace(contents, "<HTML>","");
        result = StringManager.replace(result, "<HEAD>","");
        result = StringManager.replace(result, "<META NAME=\"GENERATOR\" Content=\"Microsoft DHTML Editing Control\">","");
        result = StringManager.replace(result, "<TITLE>","");
        result = StringManager.replace(result, "</TITLE>","");
        result = StringManager.replace(result, "</HEAD>","");
        result = StringManager.replace(result, "<BODY>","");
        result = StringManager.replace(result, "</BODY>","");
        result = StringManager.replace(result, "</HTML>","");

        return result;
    }
}
