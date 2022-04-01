//**********************************************************
//1. 제      목: 교재 마스터
//2. 프로그램명: BookBean.java
//3. 개      요: 교재 마스터
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: 정상진 2005. 02. 09
//7. 수      정:
//**********************************************************

package com.credu.book;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;


@SuppressWarnings("unchecked")
public class BookBean {

	private	ConfigSet config;
	private	int	row;

	public BookBean() {
		try{
			config = new ConfigSet();
			row	= Integer.parseInt(config.getProperty("page.bulletin.row"));		//		  이 모듈의	페이지당 row 수를 셋팅한다
		}
		catch(Exception	e) {
			e.printStackTrace();
		}
	}

	/**
	 * 교재 리스트화면 select
	 * @param box        receive from the form object and session
	 * @return ArrayList 교재 리스트
	 * @throws Exception
	 */
	public ArrayList selectBookList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;

		int    v_pageno     = box.getInt("p_pageno");
		String v_searchtext = box.getString("p_searchtext");
		String ss_classify  = box.getStringDefault("s_classify","ALL");    //교재분류
		String ss_action    = box.getString("s_action");

		try {
			if(ss_action.equals("go")){
				connMgr = new DBConnectionManager();
				list = new ArrayList();

				sql = " select a.bookcode, a.classify, b.codenm, a.bookname, a.price, a.dis_price,      ";
				sql+= "        a.intro, a.writer, a.content, a.review, a.realfilename1, a.newfilename1, ";
				sql+= "        a.realfilename2, a.newfilename2, a.recom, a.luserid, a.ldate, a.cnt,     ";
				sql+= "        a.book_site, a.book_url, a.author      ";
				sql+= "  from TZ_book a, TZ_CODE b                                                      ";
				sql+= "  where a.classify = b.code                                                      ";
				sql+= "    and b.gubun = '0073'                                                         ";

				if (!ss_classify.equals("ALL")) sql+= " and a.classify = "+SQLString.Format(ss_classify);
				if ( !v_searchtext.equals("")) {                //    검색어가 있으면
					sql+= "  and a.bookname like " + StringManager.makeSQL("%" + v_searchtext + "%");
				}
				sql+= " order by a.bookcode desc                                                        ";
				//   			System.out.println("selectBookList 리스트화면 : "+sql);

				ls = connMgr.executeQuery(sql);

				ls.setPageSize(row);                        //  페이지당 row 갯수를 세팅한다
				ls.setCurrentPage(v_pageno);                //     현재페이지번호를 세팅한다.
				int total_page_count = ls.getTotalPage();   //     전체 페이지 수를 반환한다
				int total_row_count = ls.getTotalCount();   //     전체 row 수를 반환한다

				while (ls.next()) {
					dbox = ls.getDataBox();
					dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
					dbox.put("d_totalpagecount", new Integer(total_page_count));
					dbox.put("d_rowcount",new Integer(row));
					list.add(dbox);
				}
			}
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		} finally {
			if (ls != null) { try {ls.close();} catch (Exception e) {}}
			if (connMgr != null) {try {	connMgr.freeConnection();} catch (Exception e10) {}}
		}
		return list;
	}


	/**
	 * 선택된 교재 상세내용 select
	 * @param box          receive from the form object and session
	 * @return ArrayList   조회한 상세정보
	 * @throws Exception
	 */
	public DataBox selectBook(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		String sql = "";
		DataBox dbox = null;

		String	v_bookcode = box.getString("p_bookcode");
		String  v_process  = box.getString("p_process");

		try	{
			connMgr	= new DBConnectionManager();

			sql = " select a.bookcode, a.classify, b.codenm, a.bookname, a.price, a.dis_price,      ";
			sql+= "        a.intro, a.writer, a.content, a.review, a.realfilename1, a.newfilename1, ";
			sql+= "        a.realfilename2, a.newfilename2, a.recom, a.luserid, a.ldate, a.book_site, a.book_url, a.author, a.isbn ";
			sql+= "  from TZ_book a, TZ_CODE b                                                      ";
			sql+= "  where a.classify = b.code                                                      ";
			sql+= "    and b.gubun    = '0073'                                                      ";
			sql+= "    and a.bookcode = " + SQLString.Format(v_bookcode);
			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				dbox = ls.getDataBox();
			}

			if(v_process.equals("selectView")){
				connMgr.executeUpdate(" update tz_book set cnt = cnt + 1 where bookcode = '"+v_bookcode+"'");
			}

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
	 * 새로운 교재 내용 등록
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int insertBook(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ResultSet rs1   = null;
		Statement stmt1 = null;
		PreparedStatement pstmt1  = null;
		String sql    = "";
		String sql1   = "";
		int isOk1     = 0;

		String v_bookcode      = "";
		String v_classify      = box.getString("p_classify");
		String v_bookname      = StringUtil.removeTag(box.getString("p_bookname"));
		//int    v_price         = box.getInt("p_price");
		//int    v_dis_price     = box.getInt("p_dis_price");
		String v_intro         = box.getString("p_intro");
		String v_writer        = box.getString("p_writer");
		String v_content       = StringUtil.removeTag(box.getString("p_content"));
		String v_review        = box.getString("p_review");
		String v_realfilename1 = box.getRealFileName("p_file1");  // 큰이미지
		String v_newfilename1  = box.getNewFileName ("p_file1");  // 큰이미지
		String v_realfilename2 = box.getRealFileName("p_file2");  // 작은이미지
		String v_newfilename2  = box.getNewFileName ("p_file2");  // 작은이미지
		//String v_recom         = box.getString("p_recom");
		String v_book_site     = box.getString("p_book_site");
		String v_book_url      = box.getString("p_book_url");
		String v_author        = box.getString("p_author");
		String v_isbn          = box.getString("p_isbn");
		String s_userid = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			//---------------------- 교재 번호 가져온다 ----------------------------
			stmt1 = connMgr.createStatement();
			sql = "select NVL(max(bookcode) + 1,'1000')  maxbook from TZ_BOOK ";
			rs1 = stmt1.executeQuery(sql);
			if (rs1.next()) {
				v_bookcode = rs1.getString("maxbook");
			}
			rs1.close();
			//			if (v_bookcode.equals(""))	v_bookcode = "1000";
			//-------------------------------------------------------------------------

			//----------------------   교재 table 에 입력  --------------------------
			//sql1  =  " insert into TZ_BOOK(bookcode, classify, bookname, price, dis_price, intro, writer, content, review, ";
			//sql1 += "                      realfilename1, newfilename1, realfilename2, newfilename2, recom, luserid, ldate)";
			//sql1 += "  values (?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
			sql1  =  " insert into TZ_BOOK(bookcode, classify, bookname, book_site, book_url, intro, writer, content, review, ";
			sql1 += "                      realfilename1, newfilename1, realfilename2, newfilename2, author, userid, luserid, isbn, indate, ldate)";
			sql1 += "  values (?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ? , to_char(sysdate, 'YYYYMMDDHH24MISS'), to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setString( 1, v_bookcode);
			pstmt1.setString( 2, v_classify);
			pstmt1.setString( 3, v_bookname);
			//pstmt1.setInt   ( 4, v_price);
			//pstmt1.setInt   ( 5, v_dis_price);
			pstmt1.setString( 4, v_book_site);
			pstmt1.setString( 5, v_book_url);
			pstmt1.setString( 6, v_intro);
			pstmt1.setString( 7, v_writer);
			pstmt1.setString( 8, v_content);
			pstmt1.setString( 9, v_review);

			pstmt1.setString(10, v_realfilename1);
			pstmt1.setString(11, v_newfilename1);
			pstmt1.setString(12, v_realfilename2);
			pstmt1.setString(13, v_newfilename2);
			pstmt1.setString(14, v_author);
			pstmt1.setString(15, s_userid);
			pstmt1.setString(16, s_userid);
			pstmt1.setString(17, v_isbn);

			isOk1 = pstmt1.executeUpdate();

		}
		catch (Exception ex) {
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
	 * 새로운 교재 내용 수정
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int updateBook(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt1  = null;
		String sql1   = "";
		int isOk1     = 0;

		String v_bookcode      = box.getString("p_bookcode");
		String v_classify      = box.getString("p_classify");
		String v_bookname      = box.getString("p_bookname");
		//int    v_price         = box.getInt("p_price");
		//int    v_dis_price     = box.getInt("p_dis_price");
		String v_intro         = box.getString("p_intro");
		String v_writer        = box.getString("p_writer");
		String v_content       = StringUtil.removeTag(box.getString("p_content"));
		String v_review        = box.getString("p_review");
		String v_realfilename1 = box.getRealFileName("p_file1");  // 큰이미지
		String v_newfilename1  = box.getNewFileName ("p_file1");  // 큰이미지
		String v_realfilename2 = box.getRealFileName("p_file2");  // 작은이미지
		String v_newfilename2  = box.getNewFileName ("p_file2");  // 작은이미지
		//String v_recom         = box.getString("p_recom");
		String v_book_site     = box.getString("p_book_site");
		String v_book_url      = box.getString("p_book_url");
		String v_author        = box.getString("p_author");
		String v_isbn          = box.getString("p_isbn");

		String filename10   = box.getStringDefault("p_file10", "0");
		String filename20   = box.getStringDefault("p_file20", "0");

		if(v_realfilename1.length()       == 0) {
			if (filename10.equals("1")){
				v_realfilename1    = "";
				v_newfilename1     = "";
			} else {
				v_realfilename1    = box.getString("p_file11");
				v_newfilename1     = box.getString("p_file12");
			}
		}

		if(v_realfilename2.length()       == 0) {
			if (filename20.equals("1")){
				v_realfilename2    = "";
				v_newfilename2     = "";
			} else {
				v_realfilename2    = box.getString("p_file21");
				v_newfilename2     = box.getString("p_file22");
			}
		}

		String s_userid = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			//----------------------   교재 table 에 입력  --------------------------
			sql1  = " update TZ_BOOK set classify=?, bookname=?, book_site=?, book_url=?, intro=?, writer=?, content=?, ";
			sql1 += "                    review=?, realfilename1=?, newfilename1=?, realfilename2=?, newfilename2=?, ";
			sql1 += "                     author=?, isbn=?, luserid=?, ldate=to_char(sysdate, 'YYYYMMDDHH24MISS')        ";
			sql1 += "  where  bookcode = ?                                                                           ";

			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setString( 1, v_classify);
			pstmt1.setString( 2, v_bookname);
			pstmt1.setString( 3, v_book_site);
			pstmt1.setString( 4, v_book_url);
			pstmt1.setString( 5, v_intro);
			pstmt1.setString( 6, v_writer);
			pstmt1.setString( 7, v_content);
			pstmt1.setString( 8, v_review);
			pstmt1.setString( 9, v_realfilename1);
			pstmt1.setString(10, v_newfilename1);
			pstmt1.setString(11, v_realfilename2);
			pstmt1.setString(12, v_newfilename2);
			pstmt1.setString(13, v_author);
			pstmt1.setString(14, v_isbn);
			pstmt1.setString(15, s_userid);
			pstmt1.setString(16, v_bookcode);

			isOk1 = pstmt1.executeUpdate();

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk1;
	}

	/**
	 * 선택된 교재 삭제
	 * @param box      receive from the form object and session
	 * @return isOk    1:delete success,0:delete fail
	 * @throws Exception
	 */
	public int deleteBook(RequestBox	box) throws	Exception {
		DBConnectionManager	connMgr	= null;
		PreparedStatement pstmt1 = null;
		String sql1	= "";
		int	isOk1 =	0;
		String v_bookcode      = box.getString("p_bookcode");

		try	{
			connMgr	= new DBConnectionManager();

			sql1 = "delete from	TZ_Book	where bookcode = ? ";
			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setString(1, v_bookcode);
			isOk1 =	pstmt1.executeUpdate();
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box,	sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {}	}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1;
	}

	/* =====================================================================================*/

	public static String getBook(RequestBox box, boolean isChange, boolean isALL) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		String result = "교재명 ";

		try {
			String ss_bookcode   = box.getStringDefault("s_bookcode","ALL");    //교재

			connMgr = new DBConnectionManager();

			sql  = " select bookcode, bookname ";
			sql += "   from tz_book            ";
			sql += "  order by bookname        ";

			ls = connMgr.executeQuery(sql);

			result += getSelectTag(ls, isChange, isALL, "s_bookcode", ss_bookcode);
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, true);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}

	public static String getSelectTag(ListSet ls, boolean isChange, boolean isALL, String selname, String optionselected) throws Exception {
		StringBuffer sb = null;
		boolean isSelected = false;

		try {
			sb = new StringBuffer();

			sb.append("<select name = \"" + selname + "\"");
			if(isChange) sb.append(" onChange = \"javascript:whenSelection('change')\"");
			sb.append(" >\r\n");
			if(isALL) {
				sb.append("<option value = \"ALL\">ALL</option>\r\n");
			}

			while (ls.next()) {
				ResultSetMetaData meta = ls.getMetaData();
				int columnCount = meta.getColumnCount();

				sb.append("<option value = \"" + ls.getString(1) + "\"");
				if (optionselected.equals(ls.getString(1)) && !isSelected) {
					sb.append(" selected");
					isSelected = true;
				}

				sb.append(">" + ls.getString(columnCount) + "</option>\r\n");
			}
			sb.append("</select>\r\n");
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, true);
			throw new Exception(ex.getMessage());
		}
		return sb.toString();
	}


	/* =====================================================================================*/

	/**
	 * 교재 리스트화면 select
	 * @param box        receive from the form object and session
	 * @return ArrayList 교재 리스트
	 * @throws Exception
	 */
	public ArrayList selectUserBookList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;

		int    v_pageno     = box.getInt("p_pageno");
		String v_searchtext = box.getString("p_searchtext");
		String ss_classify  = box.getStringDefault("s_classify","ALL");    //교재분류

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			sql = " select a.bookcode, a.classify, b.codenm, a.bookname, a.price, a.dis_price,      ";
			sql+= "\n        a.intro, a.writer, a.content, a.review, a.realfilename1, a.newfilename1, ";
			sql+= "\n        a.realfilename2, a.newfilename2, a.recom, a.luserid, a.ldate, a.book_site, a.book_url, a.author            ";
			sql+= "\n  from TZ_book a, TZ_CODE b                                                      ";
			sql+= "\n  where a.classify = b.code                                                      ";
			sql+= "\n    and b.gubun = '0073'                                                         ";

			if (!ss_classify.equals("ALL")) sql+= "\n and a.classify = "+SQLString.Format(ss_classify);
			if ( !v_searchtext.equals("")) {                //    검색어가 있으면
				sql+= "\n  and a.bookname like " + StringManager.makeSQL("%" + v_searchtext + "%");
			}
			sql+= "\n order by a.bookcode asc                                                        ";
			//   			System.out.println("selectBookList 리스트화면 : "+sql);

			ls = connMgr.executeQuery(sql);

			ls.setPageSize(12);                        //  페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno);                //     현재페이지번호를 세팅한다.
			int total_page_count = ls.getTotalPage();   //     전체 페이지 수를 반환한다
			int total_row_count = ls.getTotalCount();   //     전체 row 수를 반환한다

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpagecount", new Integer(total_page_count));
				dbox.put("d_rowcount",new Integer(row));
				list.add(dbox);
			}

		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		} finally {
			if (ls != null) { try {ls.close();} catch (Exception e) {}}
			if (connMgr != null) {try {	connMgr.freeConnection();} catch (Exception e10) {}}
		}
		return list;
	}


	/**
	 * 추천 교재 상세내용 select
	 * @param box          receive from the form object and session
	 * @return ArrayList   조회한 상세정보
	 * @throws Exception
	 */
	public DataBox selectRecomBook(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		String sql = "";
		DataBox dbox = null;

		try	{
			connMgr	= new DBConnectionManager();

			sql = " select * from ( select rownum rnum,  a.bookcode, a.classify, b.codenm, a.bookname, a.price, a.dis_price, ";
			sql+= "        a.intro, a.writer, a.content, a.review, a.realfilename1, a.newfilename1,  ";
			sql+= "        a.realfilename2, a.newfilename2, a.recom, a.luserid, a.ldate              ";
			sql+= "  from TZ_book a, TZ_CODE b                                                       ";
			sql+= "  where a.classify = b.code                                                       ";
			sql+= "    and b.gubun    = '0073'                                                       ";
			sql+= "    and a.recom    = 'Y'                                                          ";
			sql+= "  order by a.bookcode desc  ) where rnum < 2                                      ";
			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				dbox = ls.getDataBox();
			}

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
	 * 추천 교재 LIST
	 * @param box          receive from the form object and session
	 * @return ArrayList   조회한 상세정보
	 * @throws Exception
	 */
	public ArrayList selectRecomBookList(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;

		try	{
			connMgr = new DBConnectionManager();

			list = new ArrayList();

			//top 3
			sql = " select a.bookcode, a.classify, b.codenm, a.bookname, a.price, a.dis_price, ";
			sql+= "        a.intro, a.writer, a.content, a.review, a.realfilename1, a.newfilename1,  ";
			sql+= "        a.realfilename2, a.newfilename2, a.recom, a.luserid, a.ldate              ";
			sql+= "  from TZ_book a, TZ_CODE b                                                       ";
			sql+= "  where a.classify = b.code                                                       ";
			sql+= "    and b.gubun    = '0073'                                                       ";
			sql+= "    and a.recom    = 'Y'                                                          ";
			sql+= "  order by a.bookcode desc                                                        ";
			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = ls.getDataBox();
				list.add(dbox);
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex,	box, sql);
			throw new Exception("sql = " + sql + "\r\n"	+ ex.getMessage());
		}
		finally	{
			if(ls != null) {try	{ls.close();} catch(Exception e){}}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return list;
	}
}
