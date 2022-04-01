//**********************************************************
//	1. 제	   목: 운영자 자료실
//	2. 프로그램명: PdsAdminBean.java
//	3. 개	   요: 운영자 자료실
//	4. 환	   경: JDK 1.4
//	5. 버	   젼: 1.0
//	6. 작	   성: 강성욱 2005.	1. 2
//	7. 수      정: 이나연 05.11.16 _ connMgr.setOracleCLOB 수정
//**********************************************************

package	com.credu.homepage;

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
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

/**
 * 자료실 관리(ADMIN)
 *
 * @date   : 2005. 1
 * @author : S.W.Kang
 */
@SuppressWarnings("unchecked")
public class PdsAdminBean {
	private	static final String	FILE_TYPE =	"p_file";			//		파일업로드되는 tag name
	private	static final int FILE_LIMIT	= 5;					//	  페이지에 세팅된 파일첨부 갯수
	private	ConfigSet config;
	private	int	row;

	public PdsAdminBean() {
		try{
			config = new ConfigSet();
			row	= Integer.parseInt(config.getProperty("page.bulletin.row"));		//		  이 모듈의	페이지당 row 수를 셋팅한다
		}
		catch(Exception	e) {
			e.printStackTrace();
		}
	}

	/**
	 * 자료실 리스트화면 select
	 * @param	box			 receive from the form object and session
	 * @return ArrayList	 자료실	리스트
	 * @throws Exception
	 */
	public ArrayList selectPdsList(RequestBox box) throws Exception	{
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		ArrayList list = null;
		// 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
		String sql     = "";
		String count_sql = "";
		String head_sql  = "";
		String body_sql  = "";
		String group_sql = "";
		String order_sql = "";
		//PdsData	data = null;
		DataBox dbox = null;

		int	v_pageno = box.getInt("p_pageno");
		String v_searchtext	= box.getString("p_searchtext");
		String v_search	    = box.getString("p_search");

		try	{
			connMgr	= new DBConnectionManager();

			list = new ArrayList();

			head_sql	= "select a.seq, a.userid, a.name, a.title,	count(b.realfile) filecnt, a.indate, a.cnt  ";
			body_sql	+= " from TZ_BOARD a, TZ_BOARDFILE b                                              ";
			// 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
			//			sql	+= " where a.tabseq = b.tabseq(+)                                                       ";
			//			sql += "   and a.seq    = b.seq(+)                                                          ";
			body_sql	+= " where a.tabseq  =  b.tabseq(+)                                                       ";
			body_sql += "   and a.seq     =  b.seq(+)                                                          ";
			body_sql += "   and a.tabseq = 11";

			if ( !v_searchtext.equals("")) {				//	  검색어가 있으면
				if (v_search.equals("name")) {				//	  이름으로 검색할때
					body_sql	+= " and upper(a.name)	like upper(" + StringManager.makeSQL("%" + v_searchtext +	"%")+")";
				}
				else if	(v_search.equals("title")) {		//	  제목으로 검색할때
					body_sql	+= " and upper(a.title) like upper("	+ StringManager.makeSQL("%"	+ v_searchtext + "%")+")";
				}
				//	  else if (v_select.equals("content")) {		//	  내용으로 검색할때
				//		  sql += " and a.content like "	+ StringManager.makeSQL("%"	+ v_searchtext + "%");
				//	  }
			}
			group_sql	+= " group by a.seq, a.userid, a.name, a.title,	a.indate, a.cnt                        ";
			order_sql	+= " order by a.seq desc                                                               ";

			sql= head_sql+ body_sql+ group_sql+ order_sql;
			System.out.println(" pds sql >>>>> "+ sql);
			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ body_sql;
			System.out.println(" pds count >>>>> "+ count_sql);
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

			ls.setPageSize(row);			              	//	 페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);	//	   현재페이지번호를	세팅한다.
			//			int	totalpagecount = ls.getTotalPage();		  	//	 전체 페이지 수를 반환한다

			while (ls.next()) {
				dbox = ls.getDataBox();
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
	 * 선택된	자료실 게시물 상세내용 select
	 * @param box          receive from the form object and session
	 * @return ArrayList   조회한 상세정보
	 * @throws Exception
	 */
	public DataBox selectPds(RequestBox box)	throws Exception {
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		String sql = "";

		DataBox dbox = null;

		int	v_seq =	box.getInt("p_seq");

		Vector realfileVector = new Vector();
		Vector savefileVector = new Vector();
		Vector fileseqVector  = new Vector();


		//		int	[] fileseq = new int [v_upfilecnt];

		try	{
			connMgr	= new DBConnectionManager();

			sql	= "select a.seq, a.userid, a.name, a.title, a.content, b.fileseq, b.realfile, b.savefile, a.indate, a.cnt ";
			sql	+= " from TZ_BOARD a, TZ_BOARDFILE b                                              ";
			// 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
			//			sql	+= " where a.tabseq = b.tabseq(+)                                                 ";
			//			sql += "   and a.seq    = b.seq(+)                                                    ";
			sql	+= " where a.tabseq  =  b.tabseq(+)                                                 ";
			sql += "   and a.seq     =  b.seq(+)                                                    ";
			sql += "   and a.tabseq = 11";
			sql += "   and a.seq    = " + v_seq;

			ls = connMgr.executeQuery(sql);

			while(ls.next()) {
				//-------------------   2004.12.25  변경     -------------------------------------------------------------------
				dbox = ls.getDataBox();
				if(dbox.getString("d_realfile").length()>0) {
					realfileVector.addElement(dbox.getString("d_realfile"));
					savefileVector.addElement(dbox.getString("d_savefile"));
					fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
				}
			}
			if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
			if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
			if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);

			connMgr.executeUpdate("update TZ_BOARD set cnt = cnt + 1 where tabseq =11 and seq = "+v_seq);
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
	 * 새로운 자료실 내용 등록
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int insertPds(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		PreparedStatement pstmt1 = null;
		String sql = "";
		String sql1	= "";//,sql2 = "";
		int	isOk1 =	1;
		int	isOk2 =	1;

		String v_title    = StringUtil.removeTag(box.getString("p_title"));
		String v_content  = StringUtil.removeTag(box.getString("p_content"));

		String s_userid = box.getSession("userid");
		String s_usernm = box.getSession("name");

		try	{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			//----------------------   게시판 번호 가져온다	----------------------------
			sql	= "select NVL(max(seq),	0) from	TZ_BOARD where tabseq = 11";
			ls = connMgr.executeQuery(sql);
			ls.next();
			int	v_seq =	ls.getInt(1) + 1;
			ls.close();
			/*********************************************************************************************/
			// 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다.
			try {
				v_content =(String) NamoMime.setNamoContent(v_content);
			} catch(Exception e) {
				System.out.println(e.toString());
				return 0;
			}
			/*********************************************************************************************/

			//////////////////////////////////	 게시판	table 에 입력  ///////////////////////////////////////////////////////////////////
			sql1 =	"insert	into TZ_BOARD(tabseq, seq, userid, name, indate, title, content, cnt, luserid, ldate)               ";
			//			sql1 +=	" values (?, ?, ?, ?, to_char(sysdate,	'YYYYMMDDHH24MISS'), ?, empty_clob(), ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
			sql1 +=	" values (?, ?, ?, ?, to_char(sysdate,	'YYYYMMDDHH24MISS'), ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

			int index = 1;
			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setInt   (index++, 11);
			pstmt1.setInt   (index++, v_seq);
			pstmt1.setString(index++,	s_userid);
			pstmt1.setString(index++,	s_usernm);
			pstmt1.setString(index++,	v_title);
			pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			//			pstmt1.setString(index++,	v_content);
			pstmt1.setInt   (index++, 0);
			pstmt1.setString(index++,	s_userid);

			isOk1 =	pstmt1.executeUpdate();
			isOk2 = this.insertUpFile(connMgr,11, v_seq, box);

			/* 05.11.16 이나연*/
			//			sql2 = "select content from tz_board where tabseq = 11 and seq = " + v_seq ;
			//			connMgr.setOracleCLOB(sql2, v_content);       //      (기타 서버 경우)
			if(isOk1 > 0 && isOk2 > 0) connMgr.commit();
			else 		               connMgr.rollback();

		}
		catch (Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex,	box, sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(ls != null)      { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt1 != null)  { try { pstmt1.close(); } catch (Exception e1) {} }
			if(connMgr != null)   try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1*isOk2;
	}


	/**
	 * 선택된 자료 상세내용 수정
	 * @param box      receive from the form object and session
	 * @return isOk    1:update success,0:update fail
	 * @throws Exception
	 */
	public	int	updatePds(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		PreparedStatement pstmt1 = null;
		String sql1	= "";
		int	isOk1 =	1;
		int	isOk2 =	1;
		int	isOk3 =	1;

		int	v_seq =	box.getInt("p_seq");
		int	v_upfilecnt	= box.getInt("p_upfilecnt");	//	서버에 저장되있는 파일수
		String v_title = StringUtil.removeTag(box.getString("p_title"));
		String v_content = StringUtil.removeTag(box.getString("p_content"));

		Vector v_savefile =	new	Vector();
		Vector v_filesequence =	new	Vector();

		for(int	i =	0; i < v_upfilecnt;	i++) {
			if(	!box.getString("p_fileseq" + i).equals(""))	{
				v_savefile.addElement(box.getString("p_savefile" + i));			//		서버에 저장되있는 파일명 중에서	삭제할 파일들
				v_filesequence.addElement(box.getString("p_fileseq"	+ i));		 //		 서버에	저장되있는 파일번호	중에서 삭제할 파일들
			}
		}

		String s_userid	= box.getSession("userid");
		String s_usernm	= box.getSession("name");

		try	{
			connMgr	= new DBConnectionManager();
			connMgr.setAutoCommit(false);
			/*********************************************************************************************/
			// 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다.
			try {
				v_content =(String) NamoMime.setNamoContent(v_content);
			} catch(Exception e) {
				System.out.println(e.toString());
				return 0;
			}
			/*********************************************************************************************/

			sql1 = "update TZ_BOARD set title = ?, content = ?, userid =	?, name	= ?, luserid = ?, indate = to_char(sysdate,	'YYYYMMDDHH24MISS')";
			//			sql1 = "update TZ_BOARD set title = ?, content = empty_clob(), userid =	?, name	= ?, luserid = ?, indate = to_char(sysdate,	'YYYYMMDDHH24MISS')";
			sql1 +=	"  where tabseq = 11 and seq = ?";

			int index = 1;
			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setString(index++,	v_title);
			pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			pstmt1.setString(index++,	s_userid);
			pstmt1.setString(index++,	s_usernm);
			pstmt1.setString(index++,	s_userid);
			pstmt1.setInt   (index++, v_seq);

			isOk1 =	pstmt1.executeUpdate();

			isOk2 =	this.insertUpFile(connMgr, 11, v_seq,	box);		//		파일첨부했다면 파일table에	insert

			isOk3 =	this.deleteUpFile(connMgr, box,	v_filesequence);		//	   삭제할 파일이 있다면	파일table에서 삭제

			if(isOk1 > 0 &&	isOk2 >	0 && isOk3 > 0)	{
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
			if(pstmt1 != null)  { try { pstmt1.close(); } catch (Exception e) {}	}
			if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1*isOk2*isOk3;
	}


	/**
	 * 선택된 게시물	삭제
	 * @param box      receive from the form object and session
	 * @return isOk    1:delete success,0:delete fail
	 * @throws Exception
	 */
	public int deletePds(RequestBox	box) throws	Exception {
		DBConnectionManager	connMgr	= null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		String sql1	= "";
		String sql2	= "";
		int	isOk1 =	1;
		int	isOk2 =	1;

		int	v_seq =	box.getInt("p_seq");
		int	v_upfilecnt	= box.getInt("p_upfilecnt");	//	서버에 저장되있는 파일수
		Vector v_savefile  = box.getVector("p_savefile");

		try	{
			connMgr	= new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql1 = "delete from	TZ_BOARD	where tabseq = 11 and seq = ? ";

			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setInt(1, v_seq);

			isOk1 =	pstmt1.executeUpdate();

			if (v_upfilecnt > 0 ) {
				sql2 = "delete from	TZ_BOARDFILE where tabseq = 11 and seq =	?";

				pstmt2 = connMgr.prepareStatement(sql2);

				pstmt2.setInt(1, v_seq);

				isOk2 =	pstmt2.executeUpdate();

			}
			System.out.println(" pds 435 isOk1="+isOk1);

			if (isOk1 >	0 && isOk2 > 0)	{
				connMgr.commit();
				if (v_savefile != null)	{
					FileManager.deleteFile(v_savefile);			//	 첨부파일 삭제
				}
			} else connMgr.rollback();
		}
		catch (Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box,	sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {}	}
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {}	}
			if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1*isOk2;
	}


	///////////////////////////////////////////////////////	 파일 테이블   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * 새로운 자료파일 등록
	 * @param connMgr  DB Connection Manager
	 * @param p_tabseq 게시판 일련번호
	 * @param p_seq    게시물 일련번호
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public	int	insertUpFile(DBConnectionManager connMgr, int p_tabseq, int p_seq, RequestBox	box) throws	Exception {
		ListSet	ls = null;
		PreparedStatement pstmt2 = null;
		String sql = "";
		String sql2	= "";
		int	isOk2 =	1;

		//----------------------   업로드되는 파일의 형식을	알고 코딩해야한다  --------------------------------

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
			sql	= "select NVL(max(fileseq),	0) from	TZ_BOARDFILE	where tabseq = 11 and seq =	" +	p_seq;
			ls = connMgr.executeQuery(sql);
			ls.next();
			int	v_fileseq =	ls.getInt(1) + 1;
			ls.close();
			//------------------------------------------------------------------------------------

			//////////////////////////////////	 파일 table	에 입력	 ///////////////////////////////////////////////////////////////////
			sql2 =	"insert	into TZ_BOARDFILE(tabseq, seq, fileseq, realfile, savefile, luserid,	ldate)";
			sql2 +=	" values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

			pstmt2 = connMgr.prepareStatement(sql2);

			for(int	i =	0; i < FILE_LIMIT; i++)	{
				if(	!v_realFileName	[i].equals(""))	{		//		실제 업로드	되는 파일만	체크해서 db에 입력한다
					pstmt2.setInt(1, 11);
					pstmt2.setInt(2, p_seq);
					pstmt2.setInt(3, v_fileseq);
					pstmt2.setString(4,	v_realFileName[i]);
					pstmt2.setString(5,	v_newFileName[i]);
					pstmt2.setString(6,	s_userid);

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
		String sql3	= "";
		int	isOk3 =	1;


		int	v_seq =	box.getInt("p_seq");

		try	{
			sql3 = "delete from TZ_BOARDFILE where tabseq = 11 and seq =? and fileseq = ?";

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

}
