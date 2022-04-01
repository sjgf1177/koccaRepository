//**********************************************************
//1. 제      목: 개설예정과정게시판 
//2. 프로그램명: CommunityCreateBean.java
//3. 개      요: 개설예정과정게시판
//4. 환      경: JDK 1.0
//5. 버      젼: 0.1
//6. 작      성: Administrator 2005.12.13
//7. 수      정:
//
//**********************************************************

package com.credu.course;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;
import com.credu.community.*;
import com.dunet.common.util.StringUtil;
import com.namo.*;

/**
* @author Administrator
*
* To change the template for this generated type comment go to
* Window>Preferences>Java>Code Generation>Code and Comments
*/
public class HomePagePreCourseBean {
  private ConfigSet config;
  private static int row=10;
 /* private static String v_type = "PQ";
  private static final String FILE_TYPE = "p_file";           //      파일업로드되는 tag name
  private static final int FILE_LIMIT = 1;                    //    페이지에 세팅된 파일첨부 갯수
  */

  public void HomePagePreCourseBean() {
      try{
          config = new ConfigSet();
          row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //이 모듈의 페이지당 row 수를 셋팅한다
          row = 10; //강제로 지정
          System.out.println("....... row.....:"+row);
      }
      catch(Exception e) {
          e.printStackTrace();
      }
  }
  
  /**
   * 개설예정과정게시판 메인사용 리스트
   * @param box          receive from the form object and session
   * @return ArrayList   공지사항 리스트
   * @throws Exception
   */
   public ArrayList selectDirectList(RequestBox box) throws Exception {
 	  DBConnectionManager	connMgr	= null;
 	  Connection conn	= null;
 	  ListSet	ls = null;
 	  ArrayList list = null;
      String sql     = "";
      String count_sql = "";
      String head_sql  = "";
 	  String body_sql  = "";		
      String group_sql = "";
      String order_sql = "";
 	  DataBox dbox = null;

 		int		v_pageno 		= box.getInt("p_pageno");
 		String  v_searchtext	= box.getString("p_searchtext");

 		try	{
 			connMgr	= new DBConnectionManager();
 			list = new ArrayList();
			System.out.println(" 메인메인 개설예정 ");

 			head_sql  = "select * from ( select rownum rnum,  seq, title, userid, name, redate, content, indate, cnt, luserid, ldate  ";
 			body_sql += " from TZ_PRECOURSE ";
 			order_sql	+= " order by seq desc    ) where rnum < 5 ";

 			sql= head_sql+ body_sql+ group_sql+ order_sql;
 			ls = connMgr.executeQuery(sql);
 			
// 			count_sql= "select count(*) "+ body_sql;
// 			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);
//
// 			ls.setPageSize(row);			              	//	 페이지당 row 갯수를 세팅한다
// 			ls.setCurrentPage(v_pageno, total_row_count);	//	   현재페이지번호를	세팅한다.
// 			int	total_page_count = ls.getTotalPage();		  	//	 전체 페이지 수를 반환한다

 			while (ls.next()) {
               dbox = ls.getDataBox();
//               dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
//               dbox.put("d_totalpage", new Integer(total_page_count));
//               dbox.put("d_rowcount" , new Integer(row));
               list.add(dbox);
 			}
 		}
 		catch (Exception ex) {
 			   ErrorManager.getErrorStackTrace(ex, box,	sql);
 			throw new Exception("sql = " + sql + "\r\n"	+ ex.getMessage());
 		}
 		finally	{
 			if(ls != null) { try { ls.close(); }catch (Exception e)	{} }
 			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
 		}
 		return list;
 	}
  
  /**
  * 개설예정과정게시판 리스트
  * @param box          receive from the form object and session
  * @return ArrayList   공지사항 리스트
  * @throws Exception
  */
  public ArrayList selectList(RequestBox box) throws Exception {
	  DBConnectionManager	connMgr	= null;
	  Connection conn	= null;
	  ListSet	ls = null;
	  ArrayList list = null;
      String sql     = "";
      String count_sql = "";
      String head_sql  = "";
	  String body_sql  = "";		
      String group_sql = "";
      String order_sql = "";
	  DataBox dbox = null;

		int		v_pageno 		= box.getInt("p_pageno");
		String  v_searchtext	= box.getString("p_searchtext");

		try	{
			connMgr	= new DBConnectionManager();
			list = new ArrayList();

			head_sql  = "select seq, title, userid, name, redate, content, indate, cnt, luserid, ldate  ";
			body_sql += " from TZ_PRECOURSE ";

			if ( !v_searchtext.equals("")) {				//	  검색어가 있으면
					body_sql	+= " where upper(title) like upper("	+ StringManager.makeSQL("%"	+ v_searchtext + "%")+")";
			}
			order_sql	+= " order by seq desc                                                               ";

			sql= head_sql+ body_sql+ group_sql+ order_sql;
			ls = connMgr.executeQuery(sql);
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

			ls.setPageSize(row);			              	//	 페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);	//	   현재페이지번호를	세팅한다.
			int	total_page_count = ls.getTotalPage();		  	//	 전체 페이지 수를 반환한다

			while (ls.next()) {
              dbox = ls.getDataBox();
              dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
              dbox.put("d_totalpage", new Integer(total_page_count));
              dbox.put("d_rowcount" , new Integer(row));
              list.add(dbox);
			}
		}
		catch (Exception ex) {
			   ErrorManager.getErrorStackTrace(ex, box,	sql);
			throw new Exception("sql = " + sql + "\r\n"	+ ex.getMessage());
		}
		finally	{
			if(ls != null) { try { ls.close(); }catch (Exception e)	{} }
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return list;
	}



  /**
  * 개설예정과정게시판 상세내용 조회
  * @param box          receive from the form object and session
  * @return ArrayList   공지사항 조회
  * @throws Exception
  */
  public DataBox selectView(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;
		int	v_seq =	box.getInt("p_seq");

		try	{
			connMgr	= new DBConnectionManager();

			sql	 = "select a.seq, a.userid, a.name, a.title, a.content, a.redate, a.indate, a.cnt ";
			sql	+= " from TZ_PRECOURSE a";
			sql += "   where a.seq    = " + v_seq;			

			ls = connMgr.executeQuery(sql);
			
			while(ls.next()) {
          //-------------------   2004.12.25  변경     -------------------------------------------------------------------
				dbox = ls.getDataBox();
			}
			connMgr.executeUpdate("update TZ_PRECOURSE set cnt = cnt + 1 where seq = "+v_seq);
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex,	box, sql);
			throw new Exception("sql = " + sql + "\r\n"	+ ex.getMessage());
		}
		finally	{
			if(ls != null) {try	{ls.close();} catch(Exception e){}}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return dbox;
	}

  /**
  * 개설예정과정게시판 등록하기
  * @param box      receive from the form object and session
  * @return isOk    1:insert success,0:insert fail
  * @throws Exception
  */
  public int insertPreCourse(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		PreparedStatement pstmt1 = null;
		String sql = "";
		String sql1	= "",sql2 = "";
		int	isOk1 =	1;

		String v_title    = StringUtil.removeTag(box.getString("p_title"));
		String v_content  = StringUtil.removeTag(box.getString("p_content"));
		//String v_redate	  = box.getString("p_redate");
		//System.out.println("v_redate : "+v_redate);
		String v_content1 = "";

		String s_userid = box.getSession("userid");
		String s_usernm = box.getSession("name");

		try	{
			connMgr = new DBConnectionManager();

			//----------------------   게시판 번호 가져온다	----------------------------
			sql	= "select isnull(max(seq),	0) from	tz_PRECOURSE ";
			ls = connMgr.executeQuery(sql);
			ls.next();
			int	v_seq =	ls.getInt(1) + 1;
			ls.close();
			//------------------------------------------------------------------------------------
			/*********************************************************************************************/
//			 나모에디터 본문 처리  (Mime Document Parsing 및 이미지 업로드)
          ConfigSet conf = new ConfigSet();
          SmeNamoMime namo = new SmeNamoMime(v_content); // 객체생성 
          boolean result = namo.parse(); // 실제 파싱 수행 
          if ( !result ) { // 파싱 실패시 
              System.out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력 
              return 0;
          }
//          if ( namo.isMultipart() ) { // 문서가 멀티파트인지 판단 
//              String v_server = conf.getProperty("autoever.url.value");
//              String fPath  = conf.getProperty("dir.namo");   // 파일 저장 경로 지정
//              String refUrl = conf.getProperty("url.namo");; // 웹에서 저장된 파일을 접근하기 위한 경로
//          }
//          if ( !result ) { // 파일저장 실패시 
//              System.out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력 
//              return 0;
//          }
          v_content = namo.getContent(); // 최종 컨텐트 얻기
          /*********************************************************************************************/

			System.out.println(" PreCourse 235 content = "+v_content);
			
			
			//////////////////////////////////	 게시판	table 에 입력  ///////////////////////////////////////////////////////////////////
			sql1 =	"insert	into TZ_PRECOURSE(seq, title, userid, name, redate, content, indate, cnt, luserid, ldate)               ";
			sql1 +=	" values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setInt   (1, v_seq);
			pstmt1.setString(2, v_title);
			pstmt1.setString(3,	s_userid);
			pstmt1.setString(4,	s_usernm);
			pstmt1.setString(5,	box.getString("p_redate").replaceAll("-",""));
			pstmt1.setString(6,	v_content);
			pstmt1.setInt   (7, 0);
			pstmt1.setString(8,	s_userid);

			isOk1 =	pstmt1.executeUpdate();
		}
		catch (Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex,	box, sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(ls != null)      { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt1 != null)  { try { pstmt1.close(); } catch (Exception e1) {} }
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1;
	}


    /**
  * 개설예정과정게시판 수정하기
  * @param box      receive from the form object and session
  * @return isOk    1:insert success,0:insert fail
  * @throws Exception
  */
  public int updatePreCourse(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		Connection conn	= null;
		PreparedStatement pstmt1 = null;
		String sql1	= "",sql2="";
		int	isOk1 =	1;
		int	isOk2 =	1;
		int	isOk3 =	1;

		int	v_seq =	box.getInt("p_seq");
		String v_title 		= StringUtil.removeTag(box.getString("p_title"));
		String v_content 	= StringUtil.removeTag(box.getString("p_content"));
		String v_redate		= box.getString("p_redate");

		String s_userid	= box.getSession("userid");
		String s_usernm	= box.getSession("name");
		
		/*********************************************************************************************/
		ConfigSet conf = new ConfigSet();
      SmeNamoMime namo = new SmeNamoMime(v_content); // 객체생성 
      boolean result = namo.parse(); // 실제 파싱 수행 
      if ( !result ) { // 파싱 실패시 
          System.out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력 
          return 0;
      }
//      if ( namo.isMultipart() ) { // 문서가 멀티파트인지 판단 
//          String v_server = conf.getProperty("autoever.url.value");
//          String fPath = conf.getProperty("dir.namo");   // 파일 저장 경로 지정
//          String refUrl = conf.getProperty("url.namo");; // 웹에서 저장된 파일을 접근하기 위한 경로
//      }
//      if ( !result ) { // 파일저장 실패시 
//          System.out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력 
//          return 0;
//      }
      v_content = namo.getContent(); // 최종 컨텐트 얻기
      /*********************************************************************************************/
   
		System.out.println(" PreCourse 314 수정 content = "+v_content);
		
		try	{
			connMgr	= new DBConnectionManager();

			sql1  = " update TZ_PRECOURSE set title = ?, content = ?, userid = ?, name	= ?, luserid = ?, REDATE = ? ,ldate = to_char(sysdate,	'YYYYMMDDHH24MISS')";
			sql1 +=	"  where seq = ?";

			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setString(1,	v_title  );
			pstmt1.setString(2,	v_content);
			pstmt1.setString(3,	s_userid );
			pstmt1.setString(4,	s_usernm );
			pstmt1.setString(5,	s_userid );
			pstmt1.setString(6,	box.getString("p_redate").replaceAll("-",""));
			pstmt1.setInt   (7, v_seq    );

			isOk1 =	pstmt1.executeUpdate();
		}
		catch(Exception	ex)	{
			ErrorManager.getErrorStackTrace(ex,	box, sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null)  { try { pstmt1.close(); } catch (Exception e) {}	}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1*isOk2*isOk3;
	}

 
	 /**
	    * 개설예정과정게시판 글삭제하기
	    * @param box      receive from the form object and session
	    * @return isOk    1:insert success,0:insert fail
	    * @throws Exception
	    */
	    public int deletePreCourse(RequestBox box) throws Exception {
			DBConnectionManager	connMgr	= null;
			Connection conn	= null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			String sql1	= "";
			String sql2	= "";
			int	isOk1 =	1;
			int	isOk2 =	1;

			int	v_seq =	box.getInt("p_seq");
			
			try	{
				connMgr	= new DBConnectionManager();

				sql1 = "delete from	TZ_PRECOURSE	where seq = ? ";

				pstmt1 = connMgr.prepareStatement(sql1);

				pstmt1.setInt(1, v_seq);

				isOk1 =	pstmt1.executeUpdate();

				System.out.println(" PRECOURSE 835 isOk1 = "+isOk1);
			}
			catch (Exception ex) {
				connMgr.rollback();
				ErrorManager.getErrorStackTrace(ex, box,	sql1);
				throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
			}
			finally	{
				if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {}	}
				if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {}	}
				if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
			}
			return isOk1*isOk2;
		}
}
