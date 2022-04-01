//**********************************************************
//  1. 제      목: HomePage QNA 관리
//  2. 프로그램명: HomePageQnaBean.java
//  3. 개      요: QNA 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 6.  23
//  7. 수      정: 이나연 05.11.16 _ connMgr.setOracleCLOB 수정
//**********************************************************
package com.credu.infomation;

import java.io.StringReader;
import java.sql.Connection;
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
import com.credu.library.PageList;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.system.CodeData;
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OpenBean {
    private ConfigSet config;
    private int row;
    private String v_type = "PQ";
	private	static final String	FILE_TYPE =	"p_file";			//		파일업로드되는 tag name
	private	static final int FILE_LIMIT	= 5;					//	  페이지에 세팅된 파일첨부 갯수

    public OpenBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    public OpenBean(String type) {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
            this.v_type = type;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
    * QNA화면 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   QNA 리스트
    * @throws Exception
    */
    public ArrayList selectCourseList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls 				= null;
        ArrayList list 			= null;
        String sql 				= "";
        String sql1 		 	= "";
		String head_sql 		= "";
		String body_sql 		= "";
		String order_sql 		= "";
		String count_sql 		= "";
        DataBox dbox 			= null;

        String v_searchtext 	= box.getString("p_searchtext");
        String v_select 		= box.getString("p_select");
        String v_categorycd 	= box.getStringDefault("p_categorycd", "00");
        String s_grcode         = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        int v_pageno        	= box.getInt("p_pageno");
        //int v_tabseq 			  = box.getInt("p_tabseq");

        try {
            connMgr 			= new DBConnectionManager();
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
                sql1 			= "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
                ls 				= connMgr.executeQuery(sql1);
                ls.next();
                int v_tabseq 	= ls.getInt(1);
                ls.close();
            //------------------------------------------------------------------------------------
            list 				= new ArrayList();
            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate

			head_sql += " select a.seq , a.types, a.title, a.contents, a.indate, a.inuserid, ";
			head_sql += "        a.upfile, a.isopen, a.luserid, a.ldate, b.name,a.cnt, a.categorycd,";
			head_sql += "		(select count(realfile) from tz_homefile where tabseq = a.TABSEQ and seq = a.seq and types = a.types) filecnt, ";
            head_sql += "        (select count(*) from TZ_HOMEQNA where tabseq ="+v_tabseq+" and seq = seq and types > 0) replystate ";

            body_sql += "   from TZ_HOMEQNA a, tz_member b";
			// 수정일 : 05.11.09 수정자 : 이나연 _(+)  변경
			//sql += "  where a.inuserid = b.userid(+)";
			body_sql += "  where a.inuserid  =  b.userid(+) ";
            body_sql += "  and tabseq = " + v_tabseq;
            body_sql += "  and a.grcode = " + SQLString.Format(s_grcode);


            if ( !v_searchtext.equals("")) {      //    검색어가 있으면
                v_pageno = 1;   //      검색할 경우 첫번째 페이지가 로딩된다

               if (v_select.equals("title")) {     //    제목으로 검색할때
				   body_sql += " and lower(title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%")+")";
                }
                else if (v_select.equals("content")) {     //    내용으로 검색할때
					body_sql += " and contents like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i 용
                }
                else if (v_select.equals("name")) {     //    이름으로 검색할때
					body_sql += " and lower(name) like lower (" +  StringManager.makeSQL("%" + v_searchtext + "%")+")";            //   Oracle 9i 용
                }
            }

            if(!v_categorycd.equals("") ){
				body_sql +=" and categorycd = '"+v_categorycd+"'";
            }

            order_sql += " order by seq desc, types asc ";
            ls = connMgr.executeQuery(head_sql + body_sql + order_sql);

			count_sql= "select count(*) "+ body_sql;
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(row);          				   // 		페이지당 row 갯수를 세팅한다
		    ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
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
    * QNA 등록할때(질문)
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */

     public int insertQnaQue(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk = 1;
        int isOk1 = 1;
        int isOk2 = 1;
        int v_cnt = 0;
        String v_title = box.getString("p_title");
        String v_contents =  StringManager.replace(box.getString("content"),"<br>","\n");
        String v_types   = "0";
        String s_userid = "";
        String s_usernm = box.getSession("name");
        String s_gadmin = box.getSession("gadmin");
        //String v_type    = box.getStringDefault("p_type", "HQ");
        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
        String v_isopen  = "Y";

        //  if (s_gadmin.equals("A1")){
        //      s_userid = "운영자";
        //  }else{
                s_userid = box.getSession("userid");
        //  }
        // Vector newFileNames = box.getNewFileNames("p_file");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            //------------------------------------------------------------------------------------
            //----------------------   게시판 번호 가져온다 ----------------------------
            sql = "select NVL(max(seq), 0) from TZ_HOMEQNA where tabseq = '" +v_tabseq+ "'";
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_seq = ls.getInt(1) + 1;
            ls.close();

            //////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql1 =  "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate, grcode, cnt, categorycd)                      ";
//          sql1 += "                values (?, ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,'00') ";
			sql1 += "                values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,'00') ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt   (1, v_tabseq);
            pstmt1.setInt   (2, v_seq);
            pstmt1.setString(3, v_types);
            pstmt1.setString(4, v_title);
			pstmt1.setCharacterStream(5,  new StringReader(v_contents), v_contents.length());
            pstmt1.setString(6,  s_userid);
            pstmt1.setString(7,  v_isopen);
            pstmt1.setString(8,  s_userid);
            pstmt1.setString(9,  s_grcode);
            pstmt1.setInt   (10, v_cnt);

            isOk1 = pstmt1.executeUpdate();     //      먼저 해당 content 에 empty_clob()을 적용하고 나서 값을 스트림으로 치환한다.
			isOk2 = this.insertUpFile(connMgr,v_tabseq, v_seq,v_types, box);
//            sql2 = "select contents from tz_HOMEQNA where tabseq = " + v_tabseq + " and  seq = " + v_seq + "and types = "+ v_types ;
//            connMgr.setOracleCLOB(sql2, v_contents);       //      (기타 서버 경우)
			/* 05.11.16 이나연
            */
            if(isOk1 > 0 && isOk2 > 0) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
                isOk = 1;
            }else{
                if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
                isOk = 0;
            }

        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
    * QNA 등록할때(답변)
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
    public int insertQnaAns(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql   = "";
        String sql1  = "";
        String sql2  = "";
        int    isOk  = 0;
        int    isOk1 = 0;
        int    isOk2 = 0;
        int    isOk3 = 0;
        int    v_cnt = 0;
        String s_grcode  = box.getSession("tem_grcode");
        int    v_seq     = box.getInt("p_seq");
        String v_types   = "";
        String v_title   = box.getString("p_title");
        String v_contents =  StringManager.replace(box.getString("content"),"<br>","\n");
        String v_isopen  = "Y";
        String s_userid = "";
        String s_usernm = box.getSession("name");
        String s_gadmin = box.getSession("gadmin");
        String v_categorycd   = box.getString("p_categorycd");

    //  if (s_gadmin.equals("A1")){
    //      s_userid = "운영자";
    //  }else{
            s_userid = box.getSession("userid");
    //  }
        Vector newFileNames = box.getNewFileNames("p_file");

        try {
           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);
           //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
                sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
                ls = connMgr.executeQuery(sql1);
                ls.next();
                int v_tabseq = ls.getInt(1);
                ls.close();
            //------------------------------------------------------------------------------------
           sql  = " select max(to_number(types)) from TZ_HOMEQNA  ";
           sql += "  where tabseq = " + v_tabseq + " and seq = " + v_seq;
           ls = connMgr.executeQuery(sql);
           if (ls.next()) {
               v_types = String.valueOf((ls.getInt(1) + 1));
           } else {
                v_types = "1";
           }
            //////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql1 =  "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate, cnt, grcode,categorycd)                ";
//          sql1 += "                values (?, ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?) ";
          sql1 += "                values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?) ";

int index = 1;
            pstmt = connMgr.prepareStatement(sql1);
            pstmt.setInt   (index++, v_tabseq);
            pstmt.setInt   (index++, v_seq);
            pstmt.setString(index++, v_types);
            pstmt.setString(index++, v_title);
pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            pstmt.setString(index++,  s_userid);
            pstmt.setString(index++,  v_isopen);
            pstmt.setString(index++, s_userid);
            pstmt.setInt   (index++, v_cnt);
            pstmt.setString(index++, s_grcode);
            pstmt.setString(index++,v_categorycd);

           isOk = pstmt.executeUpdate();
		   isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq,v_types, box);       //      파일첨부했다면 파일table에  insert
//		   sql2 = "select contents from tz_HOMEQNA where tabseq = " + v_tabseq + " and  seq = " + v_seq+ " and types = '"+v_types+"'";
//           connMgr.setOracleCLOB(sql2, v_contents);       //      (기타 서버 경우)
		   /* 05.11.16 이나연

           //isOk3 = this.deleteUpFile(connMgr, box);
           */

            if(isOk > 0 && isOk2 > 0){
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
                isOk =1;
            }else{
                if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
                isOk =0;
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }

    /**
    * QNA 수정하여 저장할때
    * @param box      receive from the form object and session
    * @return isOk    1:update success,0:update fail
    * @throws Exception
    */
     public int updateQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        ListSet ls = null;
        int isOk = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        int isOk5 = 0;
        int v_seq             = box.getInt("p_seq");
		int	v_upfilecnt       = box.getInt("p_upfilecnt");	//	서버에 저장되있는 파일수
		Vector v_savefile     =	new	Vector();
		Vector v_filesequence =	new	Vector();

        String v_types     = box.getString("p_types");
        String v_title     = box.getString("p_title");
        String v_contents  =  StringManager.replace(box.getString("content"),"<br>","\n");
        String v_isopen    = "Y";
        String s_userid    = "";
        String s_usernm    = box.getSession("name");
        String s_gadmin    = box.getSession("gadmin");
        String s_grcode    = box.getSession("tem_grcode");


		for(int	i =	0; i < v_upfilecnt;	i++) {
			if(	!box.getString("p_fileseq" + i).equals(""))	{

				v_savefile.addElement(box.getString("p_savefile" + i));			//		서버에 저장되있는 파일명 중에서	삭제할 파일들
				v_filesequence.addElement(box.getString("p_fileseq"	+ i));		 //		서버에	저장되있는 파일번호	중에서 삭제할 파일들

			}
		}

    //  if (s_gadmin.equals("A1")){
            s_userid = "운영자";
    //  }else{
            s_userid = box.getSession("userid");
    //  }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
                sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
                ls = connMgr.executeQuery(sql1);
                ls.next();
                int v_tabseq = ls.getInt(1);
                ls.close();
            //------------------------------------------------------------------------------------

              sql  = " update TZ_HOMEQNA set title = ? , contents = ?, isopen = ? ,          ";
//				sql  = " update TZ_HOMEQNA set title = ? , contents = ?, isopen = ? ,          ";
                sql += "                       luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
                sql += "  where tabseq = ? and seq = ? and types = ? and grcode = ?                       ";

                pstmt = connMgr.prepareStatement(sql);

                pstmt.setString(1,  v_title);
				//pstmt.setString(2,  v_contents);
                pstmt.setCharacterStream(2,  new StringReader(v_contents), v_contents.length());
                pstmt.setString(3,  v_isopen);
                pstmt.setString(4,  s_userid);
                pstmt.setInt   (5,  v_tabseq);
                pstmt.setInt   (6,  v_seq);
                pstmt.setString(7,  v_types);
                pstmt.setString(8,  s_grcode);

			    isOk = pstmt.executeUpdate();
				isOk2 =	this.insertUpFile(connMgr, v_tabseq, v_seq,v_types,	box);		//		파일첨부했다면 파일table에	insert

				isOk3 =	this.deleteUpFile(connMgr, box,	v_filesequence);		//	   삭제할 파일이 있다면	파일table에서 삭제
//			    sql3 = "select contents from TZ_HOMEQNA where seq = " + v_seq + " and tabseq = " + v_tabseq + " and types = '"+v_types+"'";
//				connMgr.setOracleCLOB(sql3, v_contents);       //      (기타 서버 경우)

			if(isOk > 0 &&	isOk2 >	0 && isOk3 > 0)	{
				connMgr.commit();
				if (v_savefile != null)	{
					FileManager.deleteFile(v_savefile);			//	 DB	에서 모든처리가	완료되면 해당 첨부파일 삭제
				}
			} else connMgr.rollback();
		}
		catch(Exception	ex)	{
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex,	box, sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {}	}
            if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk*isOk2*isOk3;
	}
    /**
    * QNA 삭제할때
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */
    public int deleteQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        Connection conn = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;

        int    v_seq     = box.getInt("p_seq");
        String v_types   = box.getString("p_types");
        Vector savefile  = box.getVector("p_savefile");
        String v_savemotion = box.getString("p_savemotion");


        try {
            connMgr = new DBConnectionManager();
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------
            if (v_types.equals("0")) {                 // 질문삭제시 답변동시삭제
                sql1  = " delete from TZ_HOMEQNA    ";
                sql1 += "  where tabseq = ? and seq = ?";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(1, v_tabseq);
                pstmt1.setInt(2, v_seq);


            } else {
                sql1  = " delete from TZ_HOMEQNA";
                sql1 += "  where tabseq = ? and seq = ? and types = ?  ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(1, v_tabseq);
                pstmt1.setInt(2, v_seq);
                pstmt1.setString(3, v_types);

           }

            isOk1 = pstmt1.executeUpdate();

            sql3  = " delete from TZ_COMMENTQNA    ";
            sql3 += "  where tabseq = ? and seq = ? and types = ?  ";
            pstmt2 = connMgr.prepareStatement(sql3);
            pstmt2.setInt(1, v_tabseq);
            pstmt2.setInt(2, v_seq);
            pstmt2.setString(3, v_types);

            isOk3 = pstmt2.executeUpdate();

            for (int i = 0; i < savefile.size() ;i++ ){
                String str = (String)savefile.elementAt(i);
                if (!str.equals("")){
               //     isOk2 = this.deleteUpFile(connMgr, box);
                }
            }
            if (isOk1 > 0 && isOk2 > 0) {
                if (savefile != null) {
                    FileManager.deleteFile(savefile);         //     첨부파일 삭제
                }
                if (v_savemotion != null) {
                    FileManager.deleteFile(v_savemotion);
                }
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
            }
        }

        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1 * isOk2;
    }

    /**
    * QNA 새로운 자료파일 등록
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */

	 public	int	insertUpFile(DBConnectionManager connMgr, int p_tabseq, int p_seq, String types, RequestBox	box) throws	Exception {
		ListSet	ls = null;
		PreparedStatement pstmt2 = null;
		String sql = "";
		String sql2	= "";
		int	isOk2 =	1;

		//----------------------   업로드되는 파일의 형식을 알고 코딩해야한다  --------------------------------

		String [] v_realFileName = new String [FILE_LIMIT];
		String [] v_newFileName	= new String [FILE_LIMIT];

		for(int	i =	0; i < FILE_LIMIT; i++)	{
			v_realFileName [i] = box.getRealFileName(FILE_TYPE + (i+1));
			v_newFileName [i] =	box.getNewFileName(FILE_TYPE + (i+1));
		}
		//----------------------------------------------------------------------------------------------------------------------------

		String s_userid	= box.getSession("userid");

		try	{
			 //----------------------	자료 번호 가져온다 ----------------------------
			sql	= "select NVL(max(fileseq),	0) from	tz_homefile	where tabseq = "+p_tabseq+" and seq = " +	p_seq + "and types = " + types ;
			ls = connMgr.executeQuery(sql);
			ls.next();
			int	v_fileseq =	ls.getInt(1) + 1;
			ls.close();
			//------------------------------------------------------------------------------------

			//////////////////////////////////	 파일 table	에 입력	 ///////////////////////////////////////////////////////////////////
			sql2 =	"insert	into tz_homefile(tabseq, seq, fileseq, types, realfile, savefile, luserid,	ldate)";
			sql2 +=	" values (?, ?, ?, ?, ?, ?,?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

			pstmt2 = connMgr.prepareStatement(sql2);

			for(int	i =	0; i < FILE_LIMIT; i++)	{
				if(	!v_realFileName	[i].equals(""))	{		//		실제 업로드	되는 파일만	체크해서 db에 입력한다
					pstmt2.setInt   (1, p_tabseq);
					pstmt2.setInt   (2, p_seq);
					pstmt2.setInt   (3, v_fileseq);
					pstmt2.setString(4, types);
					pstmt2.setString(5,	v_realFileName[i]);
					pstmt2.setString(6,	v_newFileName[i]);
					pstmt2.setString(7,	s_userid);
					isOk2 =	pstmt2.executeUpdate();
					v_fileseq++;
				}
			}
		}
		catch (Exception ex) {
			FileManager.deleteFile(v_newFileName, FILE_LIMIT);		//	일반파일, 첨부파일 있으면 삭제..
			ErrorManager.getErrorStackTrace(ex,	box, sql2);
			throw new Exception("sql = " + sql2	+ "\r\n" + ex.getMessage());
		}
		finally	{
		    if(ls != null) { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
		}
		return isOk2;
	}

	/**
	 * 선택된 자료파일 DB에서 삭제
	 * @param connMgr			DB Connection Manager
	 * @param box				receive from the form object and session
	 * @param p_filesequence    선택 파일 갯수
	 * @return
	 * @throws Exception
	 */
	public int deleteUpFile(DBConnectionManager	connMgr, RequestBox box, Vector p_filesequence)	throws Exception {
		PreparedStatement pstmt3 = null;
		String sql	= "";
		String sql3	= "";
        ListSet ls = null;
		int	isOk3 =	1;
        String v_types   = box.getString("p_types");
		int	v_seq =	box.getInt("p_seq");

		try	{

            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------

			sql3 = "delete from tz_homefile where tabseq = "+ v_tabseq +" and seq =? and fileseq = ?";
			pstmt3 = connMgr.prepareStatement(sql3);
			for(int	i =	0; i < p_filesequence.size(); i++) {
				int	v_fileseq =	Integer.parseInt((String)p_filesequence.elementAt(i));

				pstmt3.setInt(1, v_seq);
				pstmt3.setInt(2, v_fileseq);
				isOk3 =	pstmt3.executeUpdate();
			}
		}
		catch (Exception ex) {
			   ErrorManager.getErrorStackTrace(ex, box,	sql3);
			throw new Exception("sql = " + sql3	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {}	}
		}
		return isOk3;
	}




    /**
    * 선택된 자료실 게시물 상세내용 select
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */

   public DataBox selectQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
		ResultSet rs = null;

        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");
        String v_fileseq = box.getString("p_fileseq");
		int	v_upfilecnt	 = (box.getInt("p_upfilecnt")>0?box.getInt("p_upfilecnt"):1);

		Vector realfileVector = new Vector();
		Vector savefileVector = new Vector();
		Vector fileseqVector  = new Vector();

		int	[] fileseq = new int [v_upfilecnt];

        try {
            connMgr = new DBConnectionManager();
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------
            sql  = "select a.types, a.seq, a.inuserid, a.title, a.contents, a.categorycd, a.indate ,c.name, a.cnt, ";
			sql += " b.realfile, b.savefile, b.fileseq ";
            sql += " from tz_homeqna a, tz_homefile b, tz_member c ";

			// 수정일 : 05.11.09 수정자 : 이나연 _(+)  수정
//          sql += " where a.tabseq = b.tabseq(+) and a.seq = b.seq(+) and a.types = b.types(+) ";
//          sql += " and a.inuserid = c.userid(+)";
		    sql += " where a.tabseq  =  b.tabseq(+) and a.seq  =  b.seq(+) and a.types  =  b.types(+) ";
	        sql += " and a.inuserid  =  c.userid(+) ";
            sql += " and a.tabseq = " + v_tabseq + " and a.seq = "+v_seq + " and a.types = " + SQLString.Format(v_types);

            ls = connMgr.executeQuery(sql);
			System.out.println(sql);

            while(ls.next()) {
            //-------------------   2003.12.25  변경     -------------------------------------------------------------------
                dbox = ls.getDataBox();
                realfileVector.addElement(dbox.getString("d_realfile"));
                savefileVector.addElement(dbox.getString("d_savefile"));
				fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));

            }
			if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
			if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
			if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);

			sql = "update tz_homeqna set cnt = cnt + 1 where tabseq = " + v_tabseq + " and seq = "+v_seq + " and types = " + SQLString.Format(v_types);
            connMgr.executeUpdate(sql);

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
    * 게시판 번호달기
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */

     public static String printPageList(int totalPage, int currPage, int blockSize) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str="";
        if(totalPage > 0) {
            PageList pagelist = new PageList(totalPage,currPage,blockSize);

            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            //str += "    <td width='100%' align='center' valign='middle'>";

            if (pagelist.previous()) {
                str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><img src=\"/images/user/button/pre.gif\" border=\"0\" align=\"middle\"></a></td>  ";
            }else{
                str += "<td align='center' valign='middle'><img src=\"/images/user/button/pre.gif\" border=\"0\" align=\"middle\"></td>";
            }


            for (int i=pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<td align='center' valign='middle'><strong>" + i + "</strong>" + "</td>";
                } else {
                    str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + i + "')\">" + i + "</a></td> ";
                }
            }

            if (pagelist.next()) {
                str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\"><img src=\"/images/user/button/next.gif\"  border=\"0\" align=\"middle\"></a></td>";
            }else{
                str += "<td align='center' valign='middle'><img src=\"/images/user/button/next.gif\" border=\"0\" align=\"middle\"></td>";
            }

           /* if (str.equals("")) {
                str += "<자료가 없습니다.";
            }
			*/
           // str += "    </td>";
           // str += "    <td width='15%' align='center'>";
           // str += "    </td>";
            str += "</tr>";
            str += "</table>";
        }
        return str;
    }


    /**
    * 게시판 번호달기 2 (나의질문방 - 탭으로 구분할때)
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception


     public static String printPageList(int totalPage, int currPage, int blockSize, int tab) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str="";
        if(totalPage > 0) {
            PageList pagelist = new PageList(totalPage,currPage,blockSize);


            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            str += "    <td width='100%' align='center' valign='middle'>";

            if (pagelist.previous()) {
                str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "', "','"+tab+"')\"><img src=\"/images/board/prev.gif\" border=\"0\" align=\"middle\"></a>  ";
            }else{
                str += "<img src=\"/images/board/prev.gif\" border=\"0\" align=\"middle\">";
            }

            for (int i=pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<B>" + i + "</B>" + " ";
                } else {
                    str += "<a href=\"javascript:goPage('" + i + "', "','"+tab+"')\">" + i + "</a> ";
                }
            }

            if (pagelist.next()) {
                str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "', "','"+tab+"')\"> <img src=\"/images/board/next.gif\"  border=\"0\" align=\"middle\"></a>";
            }else{
                str += "<img src=\"/images/board/next.gif\" border=\"0\" align=\"middle\">";
            }

            if (str.equals("")) {
                str += "자료가 없습니다.";
            }

            str += "    </td>";
            str += "    <td width='15%' align='center'>";



            str += "    </td>";
            str += "</tr>";
            str += "</table>";
        }
        return str;
    }
      */

    /**
    * QNA 꼬릿말 등록할때(질문)
    * @param box      receive from the form object and session
    * @return isOk    1:insert success,0:insert fail
    * @throws Exception
    */
   /**
   * 새로운 자료실 내용 등록
   */
     public int insertCommentQnaQue(RequestBox box) throws Exception {
       DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;
        int v_cnt = 0;

        String v_commentqna =  box.getString("commentqna");
        int v_seq = box.getInt("p_seq");

        String v_types = box.getString("p_types");
        String v_fileseq = box.getString("p_fileseq");

        String s_userid = "";
        String s_usernm = box.getSession("name");
        String s_gadmin = box.getSession("gadmin");

    //  if (s_gadmin.equals("A1")){
            s_userid = "운영자";
    //  }else{
            s_userid = box.getSession("userid");
    //  }
        String v_isopen  = "Y";
        Vector newFileNames = box.getNewFileNames("p_file");


        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();


            //----------------------   게시판 꼬릿말 번호를 가져온다 ----------------------------
            sql = "select NVL(max(commentseq), 0) from TZ_COMMENTQNA";
            sql += " where tabseq="+ v_tabseq +" and seq = "+v_seq+" and types = " + v_types;

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_commentseq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------
            //////////////////////////////////   게시판 table 에 입력  ///////////////////////////////////////////////////////////////////
            sql1 =  "insert into TZ_COMMENTQNA(tabseq, seq, types, commentseq, inuserid, commentqna, cdate)";
            sql1 += "                values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, v_tabseq);
            pstmt1.setInt(2, v_seq);
            pstmt1.setString(3, v_types);
            pstmt1.setInt(4, v_commentseq);
            pstmt1.setString(5,  s_userid);
            pstmt1.setString(6,  v_commentqna);

            isOk1 = pstmt1.executeUpdate();     //      먼저 해당 content 에 empty_clob()을 적용하고 나서 값을 스트림으로 치환한다.

            if(isOk1 > 0 ) {
                if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}
			else {
	            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
	          }
        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(ls != null) { try { ls.close(); } catch (Exception e) {} }
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1;
    }



    /**
    * QNA화면 꼬릿말 리스트
    * @param box          receive from the form object and session
    * @return ArrayList   QNA 리스트
    * @throws Exception
    */
    public ArrayList selectcommentListQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql1 = "";
		// 수정일 : 05.11.16 수정자 : 이나연 _ totalcount 하기위한 쿼리수정
        String sql = "";
		String head_sql = "";
		String body_sql = "";
		String group_sql = "";
		String order_sql = "";
		String count_sql = "";

        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_select = box.getString("p_select");
        int v_pageno        = box.getInt("p_pageno");
        int v_seq        = box.getInt("p_seq");
        //int v_tabseq = box.getInt("p_tabseq");
        String v_types = box.getString("p_types");

        try {
            connMgr = new DBConnectionManager();
            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
                sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
                ls = connMgr.executeQuery(sql1);
                ls.next();
                int v_tabseq = ls.getInt(1);
                ls.close();
            //------------------------------------------------------------------------------------
            list = new ArrayList();
            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate
            head_sql += " select a.seq,a.types,a.commentseq,a.inuserid,a.commentqna,a.cdate, b.name ";
            body_sql += "   from TZ_COMMENTQNA a, tz_member b ";

			// 수정일 : 05.11.09 수정일 : 이나연 _(+)  수정
//          sql += "  where a.inuserid = b.userid(+) ";
			body_sql += "  where a.inuserid  =  b.userid(+) ";
            body_sql += "  and tabseq = " +v_tabseq;
            body_sql += "  and seq = " + v_seq;
            body_sql += "  and types = " + v_types;
            order_sql += " order by seq desc, types asc, commentseq asc ";

			sql= head_sql+ body_sql+ group_sql+ order_sql;
			count_sql= "select count(*) "+ body_sql;
            ls = connMgr.executeQuery(sql);
			int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
	        int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);    //     전체 row 수를 반환한다

            ls.setPageSize(row);             				//  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);                    //     현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();

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
    * QNA 코드 리스트 (config명,레벨)
    * @param box          receive from the form object and session
    * @return ArrayList   QNA 리스트
    * @throws Exception
    */
    public ArrayList getCodeList (String config, int levels, RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        try {
             connMgr = new DBConnectionManager();
             list = new ArrayList();

             sql  = " select code, codenm from tz_code            ";
             sql += "  where gubun  = " + config ;
             sql += "    and levels = " + levels;
             sql += " order by code asc";

             ls = connMgr.executeQuery(sql);

             while (ls.next()) {
                 dbox = ls.getDataBox();
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
     * QnA 카테고리 테이블 셀렉트
     * @param name           셀렉트박스명
     * @param selected       선택값
     * @param event          이벤트명
     * @param allcheck       전체유무
     * @return
     * @throws Exception
    */
     public static String homepageGetQnaCategoryTable(String grcode) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        ListSet ls2 = null;

        String sql = "";
        String sql1 = "";
        String sql2 = "";
        String result = "";
        int count =0;
        ResultSet rs1 = null;

        Statement stmt1 = null;
        CodeData data = null;
        int i = 1;

        result =  "\n   <table width='675' border='0' cellpadding='0' cellspacing='0' background='/images/user/support/faq_bg.gif'>"
                  +"\n     <tr>"
                  +"\n      <td valign='top'><img src='/images/user/support/qna_top.gif'></td>"
                  +"\n     </tr>"
                  +"\n     <tr>"
                  +"\n      <td align='center'><table width='500' border='0' cellspacing='0' cellpadding='0'>";

        try {
            connMgr = new DBConnectionManager();

            sql1 = "select count(*) cnt from tz_code where gubun='0046' and levels='1' ";
            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                count = ls1.getInt("cnt") ;
            }
             ls1.close();

            sql  = " select code, codenm from tz_code  where gubun  = '0046' and levels = '1'";
            sql += " order by code asc                        ";


            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CodeData();
                data.setCode(ls.getString("code"));
                data.setCodenm(ls.getString("codenm"));

                sql2 = "select count(*) categoryCnt from tz_homeqna where categorycd = " + SQLString.Format(data.getCode())+" and types = 0" ;
                sql2 += " and grcode = '"+grcode+"'" ;
                sql2 += " and tabseq = 5" ;
                //sql2 += " and types = 0" ;

                ls2 = connMgr.executeQuery(sql2);
                ls2.next();
                String categoryCnt = ls2.getString(1);
                ls2.close();

                    if(i%3==1){
                      result +="\n <tr> ";
                    }
                      result +="\n     <td width='180' class='tbl_cfaq'><img src='/images/user/support/bl_c.gif' width='9' height='9'> "
                             +"\n       <a href=javascript:changeCategory('"+data.getCode()+"')>" + data.getCodenm() +"</a>("+ categoryCnt+")</td>";

                   if (i%3==0  && i != (count)){
                      result +="\n  </tr> "
                          +"\n   <tr> "
                          +"\n    <td><img src='/images/user/support/faq_line-1.gif'></td>"
                          +"\n    <td><img src='/images/user/support/faq_line-1.gif'></td>"
                          +"\n    <td><img src='/images/user/support/faq_line-1.gif'></td>"
                          +"\n  </tr>";
                    }

                if(i!=3&&i%3==0){
                      result +="\n </tr> ";
                    }

                    i++;

            }
        int mod_cnt = (count)%3;

        if(mod_cnt !=0 ){
            for(int j=0; j<(3-mod_cnt); j++){
                result +="<td width='180' class='tbl_cfaq'>"+j+"</td>";
            }
        }

        result +="</tr>";

        }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        result +=   "\n   </table>"
                    +"\n    </td>"
                    +"\n  </tr>"
                    +"\n  <tr>"
                    +"\n    <td valign='bottom'><img src='/images/user/support/faq_bo.gif' ></td>"
                    +"\n  </tr>"
                    +"\n</table>";

        return result;

    }

    /**
     * QnA 카테고리 테이블 셀렉트
     * @param name           셀렉트박스명
     * @param selected       선택값
     * @param event          이벤트명
     * @param allcheck       전체유무
     * @return
     * @throws Exception
    */
     public static String homepageGetQnaCategoryTable() throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        ListSet ls2 = null;

        String sql = "";
        String sql1 = "";
        String sql2 = "";
        String result = "";
        int count =0;
        ResultSet rs1 = null;

        Statement stmt1 = null;
        CodeData data = null;
        int i = 1;


        result =  "\n   <table width='675' border='0' cellpadding='0' cellspacing='0' background='/images/user/support/faq_bg.gif'>"
                  +"\n     <tr>"
                  +"\n      <td valign='top'><img src='/images/user/support/qna_top.gif'></td>"
                  +"\n     </tr>"
                  +"\n     <tr>"
                  +"\n      <td align='center'><table width='500' border='0' cellspacing='0' cellpadding='0'>";

        try {
            connMgr = new DBConnectionManager();

            sql1 = "select count(*) cnt from tz_code where gubun='0046' and levels='1' ";
            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                count = ls1.getInt("cnt") ;
            }
             ls1.close();

            sql  = " select code, codenm from tz_code  where gubun  = '0046' and levels = '1'";
            sql += " order by code asc                        ";


            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CodeData();
                data.setCode(ls.getString("code"));
                data.setCodenm(ls.getString("codenm"));

                sql2 = "select count(*) categoryCnt from tz_homeqna where categorycd = " + SQLString.Format(data.getCode()) ;
                ls2 = connMgr.executeQuery(sql2);
                ls2.next();
                String categoryCnt = ls2.getString(1);
                ls2.close();

                    if(i%3==1){
                      result +="\n <tr> ";
                    }
                      result +="\n     <td width='180' class='tbl_cfaq'><img src='/images/user/support/bl_c.gif' width='9' height='9'> "
                             +"\n       <a href=javascript:changeCategory('"+data.getCode()+"')>" + data.getCodenm() +"</a>("+ categoryCnt+")</td>";

                   if (i%3==0  && i != (count)){
                      result +="\n  </tr> "
                          +"\n   <tr> "
                          +"\n    <td><img src='/images/user/support/faq_line-1.gif'></td>"
                          +"\n    <td><img src='/images/user/support/faq_line-1.gif'></td>"
                          +"\n    <td><img src='/images/user/support/faq_line-1.gif'></td>"
                          +"\n  </tr>";
                    }

                if(i!=3&&i%3==0){
                      result +="\n </tr> ";
                    }

                    i++;

            }
        int mod_cnt = (count)%3;

        if(mod_cnt !=0 ){
            for(int j=0; j<(3-mod_cnt); j++){
                result +="<td width='180' class='tbl_cfaq'>"+j+"</td>";
            }
        }

        result +="</tr>";

        }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        result +=   "\n   </table>"
                    +"\n    </td>"
                    +"\n  </tr>"
                    +"\n  <tr>"
                    +"\n    <td valign='bottom'><img src='/images/user/support/faq_bo.gif' ></td>"
                    +"\n  </tr>"
                    +"\n</table>";

        return result;

    }




    /**
    * QNA 꼬릿말 삭제할때
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */
    public int deleteCommentQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk1 = 1;

        int    v_seq          = box.getInt("p_seq");
        String v_types        = box.getString("p_types");
        int    v_commentseq   = box.getInt("p_commentseq");

        try {
            connMgr = new DBConnectionManager();

            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            sql  = " delete from TZ_COMMENTQNA    ";
            sql += "  where tabseq = ? and seq = ? and types = ? and commentseq = ? ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt   (1, v_tabseq);
            pstmt.setInt   (2, v_seq);
            pstmt.setString(3, v_types);
            pstmt.setInt   (4, v_commentseq);

            isOk1 = pstmt.executeUpdate();

        }
        catch (Exception ex) {
            if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1;
    }

}
