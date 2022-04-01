//**********************************************************
//1. 제      목: 결제관리
//2. 프로그램명: AccountManagerBean.java
//3. 개      요: 결제관리
//4. 환      경: JDK 1.4
//5. 버      젼: 1.0
//6. 작      성: 노희성 2006.08.24
//7. 수      정:
//**********************************************************
package com.credu.account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Hashtable;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;


public class AccountManagerBean {

	private ConfigSet config;
	@SuppressWarnings("unused")
	private int row;
	@SuppressWarnings("unused")
	private static final String FILE_TYPE = "p_file";           // 파일업로드되는 tag name
	@SuppressWarnings("unused")
	private static final int FILE_LIMIT = 1;                    // 페이지에 세팅된 파일첨부 갯수

	public AccountManagerBean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 장바구니 리스트 - 과정
	 * @param  box receive from the form object and session
	 * @return ArrayList<DataBox>
	 * @throws Exception
	 */
	public ArrayList<DataBox> selectBasketSubj(RequestBox box) throws Exception {
		@SuppressWarnings("unused")
		PreparedStatement pstmt = null;
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;

		String sql    	  = "";

		String s_userid   = box.getSession("userid");
		String v_grcode   = box.getSession("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList<DataBox>();	// Java5 이상에서의 AutoBoxing

			sql = "select 	a.grcode	,";
			sql+= "			a.userid	,";
			sql+= "			a.seq		,";
			sql+= "			a.gubun		,";
			sql+= "			a.subj		,";
			sql+= "			a.year		,";
			sql+= "			a.subjseq	,";
			sql+= "			a.price		,";
			sql+= "			a.amount	,";
			sql+= "			a.ldate		,";
			sql+= "			(select subjnm from tz_subj where subj=a.subj) subjnm, ";
			sql+= "			b.subjseq	,";
			sql+= "			b.edustart	,";
			sql+= "			b.eduend	 ";
			sql+= "from 	tz_basket a	,";
			sql+= "			tz_subjseq b ";
			sql+= "where 	a.grcode  = '" + v_grcode + "'	and ";
			sql+= "			a.userid  = '" + s_userid + "'  and ";
			sql+= "			a.gubun   = 'N' 		and ";
			sql+= "			a.grcode  = b.grcode 	and ";
			sql+= "			a.subj    = b.subj 		and ";
			sql+= "			a.year    = b.year 		and ";
			sql+= "			a.subjseq = b. subjseq ";
			sql+= "order by a.ldate ";

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
	 * 장바구니 리스트 - 전문가과정
	 * @param  box receive from the form object and session
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList<DataBox> selectBasketPackage(RequestBox box) throws Exception {
		@SuppressWarnings("unused")
		PreparedStatement pstmt = null;
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;

		String sql    	  = "";

		String s_userid   = box.getSession("userid");
		String v_grcode   = box.getSession("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList<DataBox>();

			sql = "select 	a.grcode	,";
			sql+= "			a.userid	,";
			sql+= "			a.seq		,";
			sql+= "			a.gubun		,";
			sql+= "			a.subj		,";
			sql+= "			a.year		,";
			sql+= "			a.price		,";
			sql+= "			a.amount	,";
			sql+= "			a.ldate		,";
			sql+= "			b.course	,";
			sql+= "			b.scsubj	,";
			sql+= "			b.scyear	,";
			sql+= "			b.scsubjseq	,";
			sql+= "			b.scsubjnm	,";
			sql+= "			b.subjcnt	,";
			sql+= "			b.scbiyong	,";
			sql+= "			b.subjnm, ";
			sql+= "			b.subjseq	,";
			sql+= "			b.edustart	,";
			sql+= "			b.eduend	 ";
			sql+= "from 	tz_basket a	,";
			sql+= "			vz_scsubjseq b ";
			sql+= "where 	a.grcode  = '" + v_grcode + "'  and ";
			sql+= "			a.userid  = '" + s_userid + "'  and ";
			sql+= "			a.gubun   = 'P' 		and ";
			sql+= "			a.grcode  = b.grcode 	and ";
			sql+= "			a.course    = b.course 		and ";
			sql+= "			a.courseyear    = b.cyear 		and ";
			sql+= "			a.courseseq = b.courseseq ";
			sql+= "order by a.ldate ";

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
	 * 장바구니 리스트 - 도서
	 * @param  box receive from the form object and session
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList<DataBox> selectBasketBook(RequestBox box) throws Exception {
		@SuppressWarnings("unused")
		PreparedStatement pstmt = null;
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;

		String sql    	  = "";

		String s_userid   = box.getSession("userid");
		String v_grcode   = box.getSession("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList<DataBox>();

			sql ="select 	a.grcode	, 						\n";
			sql+="			a.userid	,                       \n";
			sql+="			a.seq		,                       \n";
			sql+="			a.gubun		,                       \n";
			sql+="			a.subj		,                       \n";
			sql+="			a.year		,                       \n";
			sql+="			a.unit		,                       \n";
			sql+="			a.price		,                       \n";
			sql+="			a.amount	,                       \n";
			sql+="			a.ldate		,                       \n";
			sql+="			b.bookcode	,                       \n";
			sql+="			b.bookname                          \n";
			sql+="from 		tz_basket a	,                       \n";
			sql+="			tz_book b                           \n";
			sql+="where 	a.grcode  = '"+v_grcode+"'  and     \n";
			sql+="			a.userid  = '"+s_userid+"'  and     \n";
			sql+="			a.gubun   = 'B' 		and         \n";
			sql+="			a.bookcode    = b.bookcode          \n";
			sql+="order by a.ldate                              \n";

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
	 * 결제후 장바구니 리스트
	 * @param  box receive from the form object and session
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList<DataBox> selectBasketBill(RequestBox box) throws Exception {
		@SuppressWarnings("unused")
		PreparedStatement pstmt = null;
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;

		String sql    	  = "";

		@SuppressWarnings("unused")
		String v_gubun    = box.getString("p_gubun");

		@SuppressWarnings("unused")
		String s_userid   = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList<DataBox>();

			sql = "select * from tz_basketbill";

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
	 * 장바구니 담기 - 일반과정
	 * @param    box          receive from the form object and session
	 * @return ArrayList  자료실 리스트
	 * @throws Exception
	 */
	public int InsertBasketSubj(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;

		PreparedStatement pstmt = null;
		ListSet ls = null;
		String sql = "";
		int isOk = 0;

		String s_userid  = box.getSession("userid");

		String v_subj    = box.getString("p_subj");
		String v_year    = box.getString("p_year");
		String v_subjseq = box.getString("p_subjseq");

		int	   v_price   = box.getInt("p_price");

		String v_grcode   = box.getSession("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}

		try
		{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			//중복체크
			sql = "select 	* ";
			sql+= "from 	tz_basket ";
			sql+= "where 	grcode  = '" + v_grcode  + "' and ";
			sql+= "			userid  = '" + s_userid  + "' and ";
			sql+= "			subj    = '" + v_subj    + "' and ";
			sql+= "			year    = '" + v_year    + "' and ";
			sql+= "			subjseq = '" + v_subjseq + "' ";

			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				isOk = -1;
			} else {

				if(ls != null) { try { ls.close(); } catch (Exception e) {} }

				// 수강신청기간이  지난 장바구니 내역 삭제
				sql =
					"\n  delete tz_basket  b" +
					"\n  where b.grcode = " + SQLString.Format(v_grcode) +
					"\n    and b.userid = " + SQLString.Format(s_userid) +
					"\n    and b.gubun = 'N'  " +
					"\n    and b.subj in (select a.subj from tz_subjseq a where a.year = b.year" +
					"\n									 and a.grcode = b.grcode and a.subjseq = b.subjseq and a.subj = b.subj " +
					"\n									 and ( a.propstart is null or a.propend is null or to_char(sysdate, 'YYYYMMDD')" +
					"\n									 not between substring( a.propstart, 0 , 9 ) and substring( a.propend, 0 , 9 ) ) )";
				//					"\n  from tz_subjseq a right outer join  tz_basket b  " +
				//					"\n  on a.subj = b.subj   " +
				//					"\n     and a.year = b.year  " +
				//					"\n     and a.subjseq = b.subjseq  " +
				//					"\n     and a.grcode = b.grcode  " +
				//					"\n    and ( a.propstart is null or a.propend is null or to_char(sysdate, 'YYYYMMDD') not between substring( a.propstart, 0 , 9 ) and substring( a.propend, 0 , 9 ) ) ";

				connMgr.executeUpdate(sql);

				//수강신청 개수를 5개로 제한함.
				sql = "select 	count(*) cnt ";
				sql+= "from 	tz_basket ";
				sql+= "where 	grcode  = '" + v_grcode  + "' and ";
				sql+= "			userid  = '" + s_userid  + "' and ";
				sql+= "			gubun	= 'N'";

				ls = connMgr.executeQuery(sql);
				if (ls.next()) {
					if (ls.getInt("cnt")>=5) {
						isOk = -2; //5개 초과
					}
				}

				if (isOk==0) {
					//신규 등록
					sql = "insert into tz_basket( 	 ";
					sql+= "				GRCODE		,";
					sql+= "				USERID		,";
					sql+= "				SEQ			,";
					sql+= "				GUBUN		,";
					sql+= "				SUBJ		,";
					sql+= "				YEAR		,";
					sql+= "				SUBJSEQ		,";
					sql+= "				UNIT		,";
					sql+= "				PRICE		,";
					sql+= "				AMOUNT		,";
					sql+= "				LDATE)		 ";
					sql+= "values(?,?,?,?,?,?,?,?,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'))";

					pstmt = connMgr.prepareStatement(sql);

					Log.info.println("maxseq = " + getMaxSeq(connMgr,v_grcode,s_userid));

					pstmt.setString( 1, v_grcode);
					pstmt.setString( 2, s_userid);
					pstmt.setInt   ( 3, getMaxSeq(connMgr,v_grcode,s_userid));
					pstmt.setString( 4, "N");
					pstmt.setString( 5, v_subj);
					pstmt.setString( 6, v_year);
					pstmt.setString( 7, v_subjseq);
					pstmt.setInt   ( 8, 1);
					pstmt.setInt   ( 9, v_price);
					pstmt.setInt   (10, v_price);

					isOk = pstmt.executeUpdate();
				}
			}

			Log.info.println("isOk = " + isOk);

			if (isOk > 0){
				connMgr.commit();
			} else {
				connMgr.rollback();
			}
		}
		catch(Exception ex) {
			isOk = 0;
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

	/**
	 * 장바구니 담기 - 패키지과정
	 * @param    box          receive from the form object and session
	 * @return ArrayList  자료실 리스트
	 * @throws Exception
	 */
	public int InsertBasketPackage(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;

		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 0;

		String s_userid   = box.getSession("userid");
		String v_course	  = box.getString("p_course");
		String v_courseyear	  = box.getString("p_cyear");
		String v_courseseq	  = box.getString("p_courseseq");

		int    v_price		= box.getInt("p_price");
		String v_grcode   = box.getSession("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}
		try
		{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql = "insert into tz_basket( 	 ";
			sql+= "				GRCODE		,";
			sql+= "				USERID		,";
			sql+= "				SEQ			,";
			sql+= "				GUBUN		,";
			sql+= "				COURSE		,";
			sql+= "				COURSEYEAR		,";
			sql+= "				COURSESEQ		,";
			sql+= "				UNIT		,";
			sql+= "				PRICE		,";
			sql+= "				AMOUNT		,";
			sql+= "				LDATE)		 ";
			sql+= "values(?,?,?,?,?,?,?,?,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'))";

			pstmt = connMgr.prepareStatement(sql);

			Log.info.println("maxseq = " + getMaxSeq(connMgr,v_grcode,s_userid));


			pstmt.setString( 1, v_grcode);
			pstmt.setString( 2, s_userid);
			pstmt.setInt   ( 3, getMaxSeq(connMgr,v_grcode,s_userid));
			pstmt.setString( 4, "P");
			pstmt.setString( 5, v_course);
			pstmt.setString( 6, v_courseyear);
			pstmt.setString( 7, v_courseseq);
			pstmt.setInt   ( 8, 1);
			pstmt.setInt   ( 9, v_price);
			pstmt.setInt   (10, v_price);

			isOk = pstmt.executeUpdate();

			Log.info.println("isOk = " + isOk);

			if (isOk > 0){
				connMgr.commit();
			} else {
				connMgr.rollback();
			}
		}
		catch(Exception ex) {
			isOk = 0;
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

	/**
	 * 장바구니 담기 - 도서
	 * @param    box          receive from the form object and session
	 * @return ArrayList  자료실 리스트
	 * @throws Exception
	 */
	public int InsertBasketBook(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;

		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 0;

		String s_userid   = box.getSession("userid");

		int	   v_bookcode = box.getInt("p_bookcode");
		int	   v_price    = box.getInt("p_price");
		String v_grcode   = box.getSession("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}

		try
		{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql = "insert into tz_basket( 	 ";
			sql+= "				GRCODE		,";
			sql+= "				USERID		,";
			sql+= "				SEQ			,";
			sql+= "				GUBUN		,";
			sql+= "				BOOKCODE	,";
			sql+= "				UNIT		,";
			sql+= "				PRICE		,";
			sql+= "				AMOUNT		,";
			sql+= "				LDATE)		 ";
			sql+= "values(?,?,?,?,?,?,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'))";

			pstmt = connMgr.prepareStatement(sql);

			Log.info.println("maxseq = " + getMaxSeq(connMgr,v_grcode,s_userid));

			pstmt.setString( 1, v_grcode);
			pstmt.setString( 2, s_userid);
			pstmt.setInt   ( 3, getMaxSeq(connMgr,v_grcode,s_userid));
			pstmt.setString( 4, "B");
			pstmt.setInt   ( 5, v_bookcode);
			pstmt.setInt   ( 6, 1);
			pstmt.setInt   ( 7, v_price);
			pstmt.setInt   ( 8, v_price);

			isOk = pstmt.executeUpdate();

			Log.info.println("isOk = " + isOk);

			if (isOk > 0){
				connMgr.commit();
			} else {
				connMgr.rollback();
			}
		}
		catch(Exception ex) {
			isOk = 0;
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

	/**
	 * 장바구니 수량변경 - 도서
	 * @param    box          receive from the form object and session
	 * @return ArrayList  자료실 리스트
	 * @throws Exception
	 */
	public int UpdateBookUnit(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;

		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 0;

		String s_userid   = box.getSession("userid");

		@SuppressWarnings("unused")
		int	   v_bookcode = box.getInt("p_bookcode");
		int	   v_seq    = box.getInt("p_seq");
		int	   v_unit    = box.getInt("p_unit");

		String v_grcode   = box.getSession("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}

		try
		{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql+="update 	tz_basket ";
			sql+="set		unit   = ?,";
			sql+="			amount = price * ? ";
			sql+="where		grcode = ? and ";
			sql+="			userid = ? and ";
			sql+="			seq    = ? ";

			pstmt = connMgr.prepareStatement(sql);

			pstmt.setInt( 1, v_unit);
			pstmt.setInt( 2, v_unit);
			pstmt.setString   ( 3, v_grcode);
			pstmt.setString( 4, s_userid);
			pstmt.setInt   ( 5, v_seq);

			isOk = pstmt.executeUpdate();

			Log.info.println("isOk = " + isOk);

			if (isOk > 0){
				connMgr.commit();
			} else {
				connMgr.rollback();
			}
		}
		catch(Exception ex) {
			isOk = 0;
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

	/**
	 * 장바구니 MAX SEQ 얻기
	 * @param    box          receive from the form object and session
	 * @return ArrayList  자료실 리스트
	 * @throws Exception
	 */
	public int getMaxSeq(DBConnectionManager connMgr, String v_grcode, String v_userid) throws Exception {
		ListSet ls = null;
		String sql = "";
		int v_maxseq = 1;

		try
		{

			sql = " select nvl(max(seq),0) + 1 maxseq from tz_basket where grcode = '" + v_grcode + "' and userid = '" + v_userid + "' ";

			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				v_maxseq = ls.getInt("maxseq");
			}

		}
		catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
		}
		return v_maxseq;
	}

	/**
	 * 장바구니 삭제
	 * @param    box          receive from the form object and session
	 * @return ArrayList  자료실 리스트
	 * @throws Exception
	 */
	public int DeleteBasket(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;

		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 0;

		String v_grcode   = box.getSession("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}
		String s_userid		= box.getSession("userid");

		String v_gubun 		= box.getString("p_gubun"); //N:일반과정,P:전문가과정,B:도서
		String v_subj		= box.getString("p_subj");
		String v_year       = box.getString("p_year");
		String v_subjseq    = box.getString("p_subjseq");
		String v_bookcode   = box.getString("p_bookcode");

		try
		{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			if (v_gubun.equals("N")) {
				sql = "delete 	tz_basket ";
				sql+= "where  	grcode = '" + v_grcode + "' and ";
				sql+= "			gubun  = 'N'				and ";
				sql+= "			userid = '" + s_userid + "' and ";
				sql+= "			subj   = '" + v_subj   + "' and ";
				sql+= "			year   = '" + v_year   + "' and ";
				sql+= "			subjseq= '" + v_subjseq+ "'";
			} else if (v_gubun.equals("P")) {
				sql = "delete 	tz_basket ";
				sql+= "where  	grcode 		= '" + v_grcode 	+ "' and ";
				sql+= "			gubun  		= 'P'					 and ";
				sql+= "			userid 		= '" + s_userid 	+ "' and ";
				sql+= "			course   	= '" + v_subj   	+ "' and ";
				sql+= "			courseyear  = '" + v_year + "' and ";
				sql+= "			courseseq	= '" + v_subjseq	+ "'";
			} else if (v_gubun.equals("B")) {
				v_bookcode=v_subj;
				sql = "delete 	tz_basket ";
				sql+= "where  	grcode 	= '" + v_grcode 	+ "' and ";
				sql+= "			gubun  	= 'B'					 and ";
				sql+= "			userid 	= '" + s_userid 	+ "' and ";
				sql+= "			bookcode= '" + v_bookcode   + "' ";
			}


			Log.info.println("Delete sql = " + sql);

			pstmt = connMgr.prepareStatement(sql);

			isOk = pstmt.executeUpdate();

			Log.info.println("Delete isOk = " + isOk);

			if (isOk > 0){
				connMgr.commit();
			} else {
				connMgr.rollback();
			}

		}
		catch(Exception ex) {
			isOk = 0;
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

	/**
	 * 결제처리(결제완료시 장바구니->결제후 장바구니로 DATA 이관)
	 * @param    box          receive from the form object and session
	 * @return ArrayList  자료실 리스트
	 * @throws Exception
	 */
	public int BillProcess(Hashtable<String, String> BillData) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;

		int result = 0;
		int isOk1 = 0, isOk2 = 0, isOk3 = 0, isOk4 = 0;
		@SuppressWarnings("unused")
		int nNum = 0;

		ListSet ls = null;
		@SuppressWarnings("unused")
		ListSet ls2 = null;

		String sql 				= "";
		String currentDate	  	= FormatDate.getDate("yyyyMMddHHmmss");

		String 	tid 			= BillData.get("tid"); 					// 거래번호
		String 	resultCode 		= BillData.get("resultCode"); 			// 결과코드 ("00"이면 지불 성공)
		String 	resultMsg 		= BillData.get("resultMsg"); 			// 결과내용 (지불결과에 대한 설명)
		String 	payMethod 		= BillData.get("payMethod"); 			// 지불방법 (매뉴얼 참조)
		String 	price1 			= BillData.get("price1"); 					// OK Cashbag 복합결재시 신용카드 지불금액
		String 	price2 			= BillData.get("price2"); 					// OK Cashbag 복합결재시 포인트 지불금액

		if	(price1 == null) price1 = "0";
		if	(price2 == null) price2 = "0";

		price1 = "0";
		price2 = "0";

		String 	authCode 		= BillData.get("authCode"); 			// 신용카드 승인번호
		String 	cardQuota 		= BillData.get("cardQuota");			// 할부기간
		String 	quotaInterest 	= BillData.get("quotaInterest"); 		// 무이자할부 여부 ("1"이면 무이자할부)
		String 	cardCode 		= BillData.get("cardCode"); 			// 신용카드사 코드 (매뉴얼 참조)
		String 	cardIssuerCode 	= BillData.get("cardIssuerCode"); 		// 카드발급사 코드 (매뉴얼 참조)
		String 	authCertain 	= BillData.get("authCertain");			// 본인인증 수행여부 ("00"이면 수행)
		String 	pgAuthDate 		= BillData.get("pgAuthDate"); 			// 이니시스 승인날짜
		String 	pgAuthTime 		= BillData.get("pgAuthTime"); 			// 이니시스 승인시각
		String 	ocbSaveAuthCode = BillData.get("ocbSaveAuthCode"); 		// OK Cashbag 적립 승인번호
		String 	ocbUseAuthCode 	= BillData.get("ocbUseAuthCode"); 		// OK Cashbag 사용 승인번호
		String 	ocbAuthDate 	= BillData.get("ocbAuthDate"); 			// OK Cashbag 승인일시
		String 	eventFlag 		= BillData.get("eventFlag"); 			// 각종 이벤트 적용 여부
		String 	nohpp 			= BillData.get("nohpp"); 				// 휴대폰 결제시 사용된 휴대폰 번호
		String 	noars 			= BillData.get("noars"); 				// 전화결제 시 사용된 전화번호
		String 	perno 			= BillData.get("perno"); 				// 송금자 주민번호
		String 	vacct 			= BillData.get("vacct"); 				// 가상계좌번호
		String 	vcdbank 		= BillData.get("vcdbank"); 				// 입금할 은행코드
		String 	dtinput 		= BillData.get("dtinput"); 				// 입금예정일
		String 	nminput 		= BillData.get("nminput"); 				// 송금자 명
		String 	nmvacct 		= BillData.get("nmvacct"); 				// 예금주 명
		String 	moid 			= BillData.get("moid"); 				// 상점 주문번호
		String 	codegw 			= BillData.get("codegw"); 				// 전화결제 사업자 코드
		String 	ocbcardnumber 	= BillData.get("ocbcardnumber"); 		// OK CASH BAG 결제 , 적립인 경우 OK CASH BAG 카드 번호
		String 	cultureid 		= BillData.get("cultureid"); 			// 컬쳐 랜드 ID
		String 	cardNumber 		= BillData.get("cardNumber"); 			// 신용카드번호
		String 	mid 			= BillData.get("mid");					//상점 주문번호
		String 	goodname 		= BillData.get("goodname"); 			//상품명(과정명)
		String 	price 			= BillData.get("price"); 					//결제금액
		String 	ispointuse		= BillData.get("ispointuse"); 					//포인트사용여부
		String 	vpoint			= BillData.get("point"); 					//포인트
		String 	buyername 		= BillData.get("buyername"); 			//구매자 이름
		String 	buyertel 		= BillData.get("buyertel"); 			//구매자 전화번호
		String 	buyeremail 		= BillData.get("buyeremail"); 			//구매자 EMAIL
		String 	resulterrCode 	= BillData.get("resulterrCode");  		//오류 코드

		String 	v_gubun 		= BillData.get("gubun");  				//N:일반,P:전문가,B:도서
		String 	userid			= BillData.get("userid");  				//SESSION 사용자 ID
		String 	usernm			= BillData.get("usernm");  				//SESSION 사용자 이름

		String  inputname		= BillData.get("inputname");
		String  inputdate		= BillData.get("inputdate");

		String  receive		= BillData.get("receive");
		String  phone		= BillData.get("phone");
		String  post1		= BillData.get("post1");
		String  post2		= BillData.get("post2");
		String  addr1		= BillData.get("addr1");
		String  addr2		= BillData.get("addr2");

		Log.info.println("tid 			="+tid 				+"/");
		Log.info.println("resultCode 		="+resultCode 		+"/");
		Log.info.println("resultMsg 		="+resultMsg 		+"/");
		Log.info.println("payMethod 		="+payMethod 		+"/");
		Log.info.println("price1 			="+price1 			+"/");
		Log.info.println("price2 			="+price2 			+"/");
		Log.info.println("authCode 		="+authCode 		+"/");
		Log.info.println("cardQuota 		="+cardQuota 		+"/");
		Log.info.println("quotaInterest 	="+quotaInterest 	+"/");
		Log.info.println("cardCode 		="+cardCode 		+"/");
		Log.info.println("cardIssuerCode 	="+cardIssuerCode 	+"/");
		Log.info.println("authCertain 	="+authCertain 	    +"/");
		Log.info.println("pgAuthDate 		="+pgAuthDate 		+"/");
		Log.info.println("pgAuthTime 		="+pgAuthTime 		+"/");
		Log.info.println("ocbSaveAuthCode ="+ocbSaveAuthCode  +"/");
		Log.info.println("ocbUseAuthCode 	="+ocbUseAuthCode 	+"/");
		Log.info.println("ocbAuthDate 	="+ocbAuthDate 	    +"/");
		Log.info.println("eventFlag 		="+eventFlag 		+"/");
		Log.info.println("nohpp 			="+nohpp 			+"/");
		Log.info.println("noars 			="+noars 			+"/");
		Log.info.println("perno 			="+perno 			+"/");
		Log.info.println("vacct 			="+vacct 			+"/");
		Log.info.println("vcdbank 		="+vcdbank 		    +"/");
		Log.info.println("dtinput 		="+dtinput 		    +"/");
		Log.info.println("nminput 		="+nminput 		    +"/");
		Log.info.println("nmvacct 		="+nmvacct 		    +"/");
		Log.info.println("moid 			="+moid 			+"/");
		Log.info.println("codegw 			="+codegw 			+"/");
		Log.info.println("ocbcardnumber 	="+ocbcardnumber 	+"/");
		Log.info.println("cultureid 		="+cultureid 		+"/");
		Log.info.println("cardNumber 		="+cardNumber 		+"/");
		Log.info.println("mid 			="+mid 			    +"/");
		Log.info.println("goodname 		="+goodname 		+"/");
		Log.info.println("price 			="+price 			+"/");
		Log.info.println("buyername 		="+buyername 		+"/");
		Log.info.println("buyertel 		="+buyertel 		+"/");
		Log.info.println("buyeremail 		="+buyeremail 		+"/");
		Log.info.println("resulterrCode 	="+resulterrCode 	+"/");

		Log.info.println("v_gubun			="+v_gubun			+"/");
		Log.info.println("userid			="+userid			+"/");
		Log.info.println("usernm			="+usernm			+"/");

		Log.info.println("ispointuse		="+ispointuse	+"/");
		Log.info.println("point			="+vpoint		+"/");

		Log.info.println("v_inputname		="+inputname	+"/");
		Log.info.println("v_inputdate		="+inputdate	+"/");

		Log.info.println("v_receive		="+receive	+"/");
		Log.info.println("v_phone		="+phone	+"/");
		Log.info.println("v_post1		="+post1	+"/");
		Log.info.println("v_post2		="+post2	+"/");
		Log.info.println("v_addr1		="+addr1	+"/");
		Log.info.println("v_addr2		="+addr2	+"/");

		String v_grcode   = BillData.get("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}

		try
		{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql = "select * from tz_billinfo where grcode = '" + v_grcode + "' and tid = '" + tid + "'";

			Log.info.println("sql="+sql);
			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
				result = 0;
			} else {
				//결제정보 저장
				sql = "insert into tz_billinfo(\n";
				sql+= "			grcode,			\n"; //01.grcode
				sql+= "			tid,			\n"; //01.거래번호
				sql+= "			resultcode,     \n"; //02.결과코드
				sql+= "			resultmsg,      \n"; //03.결과내용
				sql+= "			paymethod,      \n"; //04.지불방법
				sql+= "			mid,            \n"; //05.상점 ID
				sql+= "			goodname,       \n"; //06.과정명
				sql+= "			userid,         \n"; //10.사용자ID
				sql+= "			usernm,         \n"; //11.이름
				sql+= "			price,          \n"; //12.결제금액
				sql+= "			buyername,      \n"; //13.구매자 이름
				sql+= "			buyertel,       \n"; //14.구매자 전화번호
				sql+= "			buyeremail,     \n"; //15.구매자 Email
				sql+= "			resulterrcode,  \n"; //16.결과에러코드
				sql+= "			price1,         \n"; //17.OkCashBag 신용카드 금액
				sql+= "			price2,         \n"; //18.OkCashBag 포인트 금액
				sql+= "			authcode,		\n"; //19.신용카드 승인번호
				sql+= "			cardquota,      \n"; //20.할부기간
				sql+= "			quotainterest,  \n"; //21.무이자 할부여부
				sql+= "			cardcode,       \n"; //22.신용카드사 코드
				sql+= "			cardissuercode, \n"; //23.카드발급사 코드
				sql+= "			authcertain,    \n"; //24.본인인증 수행여부
				sql+= "			pgauthdate,     \n"; //25.이니시스 승인날짜
				sql+= "			pgauthtime,     \n"; //26.이니시스 승인시각
				sql+= "			ocbsaveauthcode,\n"; //27.OKCashB 적립승인번호
				sql+= "			ocbuseauthcode, \n"; //28.OKCashB 사용승인번호
				sql+= "			ocbauthdate,    \n"; //29.OKCashB 승인일시
				sql+= "			eventflag,      \n"; //30.각종이벤트 적용여부
				sql+= "			perno,          \n"; //31.송금자 주민번호
				sql+= "			vacct,          \n"; //32.가상계좌번호
				sql+= "			vcdbank,        \n"; //33.입금할 은행코드
				sql+= "			dtinput,        \n"; //34.입금예정일
				sql+= "			nminput,        \n"; //35.송금자명
				sql+= "			nmvacct,        \n"; //36.예금주명
				sql+= "			moid,           \n"; //37.상점주문번호
				sql+= "			ocbcardnumber,  \n"; //38.OKCashB 결제카드번호
				sql+= "			cardNumber,     \n"; //39.신용카드번호
				sql+= "			cancelyn,       \n"; //40.취소여부
				sql+= "			cancelresult,   \n"; //41.취소결과
				sql+= "			canceldate,     \n"; //42.취소날짜
				sql+= "			canceltime,     \n"; //43.취소시간
				sql+= "			rcashcancelno,  \n"; //44.현금영수증 승인번호
				sql+= "			luserid,        \n"; //45.등록자
				sql+= "			ldate,          \n"; //46.등록시간
				sql+= "			point,			\n"; //47. 포인트
				sql+= "			gubun,			\n"; //48. 구분(N:일반,P:패키지,B:도서)
				sql+= "			inputname,		\n"; //49. 입금자명
				sql+= "			inputdate,		\n"; //50. 입금예정일
				sql+= "			receive,		\n"; //51. 수취인 성명
				sql+= "			phone,			\n"; //52. 수취인 연락처
				sql+= "			post1,			\n"; //53. 수취인 우편번호1
				sql+= "			post2,			\n"; //54. 수취인 우편번호2
				sql+= "			addr1,			\n"; //55. 수취인 주소1
				sql+= "			addr2 )      	\n"; //56. 수취인 주소2
				sql+= "values(  ?, ?, ?, ?, ?, \n"; 	 //1 ~ 5
				sql+= "			?, ?, ?,\n"; 	 //6 ~ 8
				sql+= "			?, ?, ?, ?, ?,\n"; 	 //11 ~ 15
				sql+= "			?, ?, ?, ?, ?,\n"; 	 //16 ~ 20
				sql+= "			?, ?, ?, ?, ?,\n"; 	 //21 ~ 25
				sql+= "			?, ?, ?, ?, ?,\n"; 	 //26 ~ 30
				sql+= "			?, ?, ?, ?, ?,\n"; 	 //31 ~ 35
				sql+= "			?, ?, ?, ?, 'N',\n"; //36 ~ 40
				sql+= "			null, null, null, null, ?,\n"; //41 ~ 45
				sql+= "			?, ?, ?, ?, ?,\n";//46 ~ 48
				sql+= "			?,?,?,?,?,?)	  \n";

				pstmt1 = connMgr.prepareStatement(sql);

				pstmt1.setString( 1,     v_grcode);			 //grcode
				pstmt1.setString( 2,     tid);				 //거래번호
				pstmt1.setString( 3,     resultCode);        //결과코드
				pstmt1.setString( 4,     resultMsg);         //결과내용
				pstmt1.setString( 5,     payMethod);         //지불방법
				pstmt1.setString( 6,     mid);               //상점 ID
				pstmt1.setString( 7,     goodname);          //과정명
				pstmt1.setString( 8,     userid);            //사용자ID
				pstmt1.setString( 9,     usernm);            //이름
				pstmt1.setString(10,     price);             //결제금액
				pstmt1.setString(11,     buyername);         //구매자 이름
				pstmt1.setString(12,     buyertel);          //구매자 전화번호
				pstmt1.setString(13,     buyeremail);        //구매자 Email
				pstmt1.setString(14,     resulterrCode);     //결과에러코드
				pstmt1.setString(15,     price1);            //OkCashBag 신용카드 금액
				pstmt1.setString(16,     price2);            //OkCashBag 포인트 금액
				pstmt1.setString(17,     authCode);          //신용카드 승인번호
				pstmt1.setString(18,     cardQuota);         //할부기간
				pstmt1.setString(19,     quotaInterest);     //무이자 할부여부
				pstmt1.setString(20,     cardCode);          //신용카드사 코드
				pstmt1.setString(21,     cardIssuerCode);    //카드발급사 코드
				pstmt1.setString(22,     authCertain);       //본인인증 수행여부
				pstmt1.setString(23,     pgAuthDate);        //이니시스 승인날짜
				pstmt1.setString(24,     pgAuthTime);        //이니시스 승인시각
				pstmt1.setString(25,     ocbSaveAuthCode);   //OKCashB 적립승인번호
				pstmt1.setString(26,     ocbUseAuthCode);    //OKCashB 사용승인번호
				pstmt1.setString(27,     ocbAuthDate);       //OKCashB 승인일시
				pstmt1.setString(28,     eventFlag);         //각종이벤트 적용여부
				pstmt1.setString(29,     perno);             //송금자 주민번호
				pstmt1.setString(30,     vacct);             //가상계좌번호
				pstmt1.setString(31,     vcdbank);           //입금할 은행코드
				pstmt1.setString(32,     dtinput);           //입금예정일
				pstmt1.setString(33,     nminput);           //송금자명
				pstmt1.setString(34,     nmvacct);           //예금주명
				pstmt1.setString(35,     moid);              //상점주문번호
				pstmt1.setString(36,     ocbcardnumber);     //OKCashB 결제카드번호
				pstmt1.setString(37,     cardNumber);        //신용카드번호
				pstmt1.setString(38,     userid);           //등록자
				pstmt1.setString(39,     currentDate);             //등록시간
				pstmt1.setString(40,     vpoint);             //포인트
				pstmt1.setString(41,     v_gubun);             //구분(N:일반,P:패키지,B:도서)
				pstmt1.setString(42,     inputname);             //무통장 입금자명
				pstmt1.setString(43,     inputdate);             //무통장 입금예정일
				pstmt1.setString(44,     receive);             //
				pstmt1.setString(45,     phone);             //
				pstmt1.setString(46,     post1);             //
				pstmt1.setString(47,     post2);             //
				pstmt1.setString(48,     addr1);             //
				pstmt1.setString(49,     addr2);             //

				Log.info.println("isOk1  before = " + isOk1);
				isOk1 = pstmt1.executeUpdate();

				Log.info.println("isOk1 = " + isOk1);

				Log.info.println("v_gubun = " + v_gubun);

				if (resulterrCode.equals("170204") || resulterrCode.equals("1979중")) {
					//ISP 이중 결제는 tz_propose에 Update하지 않는다.(코드 170204)
					//겨좌이체 이중 결제는 tz_propose에 Update하지 않는다.(코드 1979중)
					isOk1 = 1;
					isOk2 = 1;
					isOk3 = 1;
					isOk4 = 1;
					result = 1;
				} else {
					//수강신청 Insert

					Log.info.println("#2");

					if (v_gubun.equals("B")) {
						//도서
						isOk2 = 1;
					} else {
						//일반과정
						isOk2 = InsertPropose(connMgr, v_gubun, v_grcode, tid, userid );
					}


					//tz_basket 내용을 tz_basketbill로 옮김
					if (isOk2 > 0) {
						isOk3 = MoveBasketBill(connMgr, v_gubun, v_grcode, tid, userid );
					}
					//장바구니에서 결제후 장바구니 테이블로 이동

				}

			}

			Log.info.println("isOk1 = " + isOk1);
			Log.info.println("isOk2 = " + isOk2);
			Log.info.println("isOk3 = " + isOk3);
			Log.info.println("isOk4 = " + isOk4);

			ls.close();

			if (isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
				result = 1;
				connMgr.commit();
			} else {
				result = 0;
				connMgr.rollback();
			}

		}
		catch(Exception ex) {
			result = 0;
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, null, sql);
			Log.info.println("Err = " + ex.getMessage());
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {} }
			if(pstmt4 != null) { try { pstmt4.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}

	/**
	 * 결제처리(결제완료시 장바구니->결제후 장바구니로 DATA 이관)
	 * @param    box          receive from the form object and session
	 * @return ArrayList  자료실 리스트
	 * @throws Exception
	 */
	public int BillProcessBook(Hashtable<String, String> BillData) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;

		int result = 0;
		int isOk1 = 0, isOk2 = 0;
		@SuppressWarnings("unused")
		int isOk3 = 0, isOk4 = 0;
		@SuppressWarnings("unused")
		int nNum = 0;

		ListSet ls = null;
		@SuppressWarnings("unused")
		ListSet ls2 = null;

		String sql 				= "";
		String currentDate	  	= FormatDate.getDate("yyyyMMddHHmmss");

		String 	tid 			= BillData.get("tid"); 					// 거래번호
		String 	resultCode 		= BillData.get("resultCode"); 			// 결과코드 ("00"이면 지불 성공)
		String 	resultMsg 		= BillData.get("resultMsg"); 			// 결과내용 (지불결과에 대한 설명)
		String 	payMethod 		= BillData.get("payMethod"); 			// 지불방법 (매뉴얼 참조)
		String 	price1 			= BillData.get("price1"); 					// OK Cashbag 복합결재시 신용카드 지불금액
		String 	price2 			= BillData.get("price2"); 					// OK Cashbag 복합결재시 포인트 지불금액

		if	(price1 == null) price1 = "0";
		if	(price2 == null) price2 = "0";

		price1 = "0";
		price2 = "0";

		String 	authCode 		= BillData.get("authCode"); 			// 신용카드 승인번호
		String 	cardQuota 		= BillData.get("cardQuota");			// 할부기간
		String 	quotaInterest 	= BillData.get("quotaInterest"); 		// 무이자할부 여부 ("1"이면 무이자할부)
		String 	cardCode 		= BillData.get("cardCode"); 			// 신용카드사 코드 (매뉴얼 참조)
		String 	cardIssuerCode 	= BillData.get("cardIssuerCode"); 		// 카드발급사 코드 (매뉴얼 참조)
		String 	authCertain 	= BillData.get("authCertain");			// 본인인증 수행여부 ("00"이면 수행)
		String 	pgAuthDate 		= BillData.get("pgAuthDate"); 			// 이니시스 승인날짜
		String 	pgAuthTime 		= BillData.get("pgAuthTime"); 			// 이니시스 승인시각
		String 	ocbSaveAuthCode = BillData.get("ocbSaveAuthCode"); 		// OK Cashbag 적립 승인번호
		String 	ocbUseAuthCode 	= BillData.get("ocbUseAuthCode"); 		// OK Cashbag 사용 승인번호
		String 	ocbAuthDate 	= BillData.get("ocbAuthDate"); 			// OK Cashbag 승인일시
		String 	eventFlag 		= BillData.get("eventFlag"); 			// 각종 이벤트 적용 여부
		String 	nohpp 			= BillData.get("nohpp"); 				// 휴대폰 결제시 사용된 휴대폰 번호
		String 	noars 			= BillData.get("noars"); 				// 전화결제 시 사용된 전화번호
		String 	perno 			= BillData.get("perno"); 				// 송금자 주민번호
		String 	vacct 			= BillData.get("vacct"); 				// 가상계좌번호
		String 	vcdbank 		= BillData.get("vcdbank"); 				// 입금할 은행코드
		String 	dtinput 		= BillData.get("dtinput"); 				// 입금예정일
		String 	nminput 		= BillData.get("nminput"); 				// 송금자 명
		String 	nmvacct 		= BillData.get("nmvacct"); 				// 예금주 명
		String 	moid 			= BillData.get("moid"); 				// 상점 주문번호
		String 	codegw 			= BillData.get("codegw"); 				// 전화결제 사업자 코드
		String 	ocbcardnumber 	= BillData.get("ocbcardnumber"); 		// OK CASH BAG 결제 , 적립인 경우 OK CASH BAG 카드 번호
		String 	cultureid 		= BillData.get("cultureid"); 			// 컬쳐 랜드 ID
		String 	cardNumber 		= BillData.get("cardNumber"); 			// 신용카드번호
		String 	mid 			= BillData.get("mid");					//상점 주문번호
		String 	goodname 		= BillData.get("goodname"); 			//상품명(과정명)
		String 	price 			= BillData.get("price"); 				//결제금액
		String 	ispointuse		= BillData.get("ispointuse"); 			//포인트사용여부
		String 	vpoint			= BillData.get("point"); 				//포인트
		String 	buyername 		= BillData.get("buyername"); 			//구매자 이름
		String 	buyertel 		= BillData.get("buyertel"); 			//구매자 전화번호
		String 	buyeremail 		= BillData.get("buyeremail"); 			//구매자 EMAIL
		String 	resulterrCode 	= BillData.get("resulterrCode");  		//오류 코드

		String 	v_gubun 		= BillData.get("gubun");  				//N:일반,P:전문가,B:도서
		String 	userid			= BillData.get("userid");  				//SESSION 사용자 ID
		String 	usernm			= BillData.get("usernm");  				//SESSION 사용자 이름

		String  inputname		= BillData.get("inputname");
		String  inputdate		= BillData.get("inputdate");

		Log.info.println("tid 			="+tid 				+"/");
		Log.info.println("resultCode 		="+resultCode 		+"/");
		Log.info.println("resultMsg 		="+resultMsg 		+"/");
		Log.info.println("payMethod 		="+payMethod 		+"/");
		Log.info.println("price1 			="+price1 			+"/");
		Log.info.println("price2 			="+price2 			+"/");
		Log.info.println("authCode 		="+authCode 		+"/");
		Log.info.println("cardQuota 		="+cardQuota 		+"/");
		Log.info.println("quotaInterest 	="+quotaInterest 	+"/");
		Log.info.println("cardCode 		="+cardCode 		+"/");
		Log.info.println("cardIssuerCode 	="+cardIssuerCode 	+"/");
		Log.info.println("authCertain 	="+authCertain 	    +"/");
		Log.info.println("pgAuthDate 		="+pgAuthDate 		+"/");
		Log.info.println("pgAuthTime 		="+pgAuthTime 		+"/");
		Log.info.println("ocbSaveAuthCode ="+ocbSaveAuthCode  +"/");
		Log.info.println("ocbUseAuthCode 	="+ocbUseAuthCode 	+"/");
		Log.info.println("ocbAuthDate 	="+ocbAuthDate 	    +"/");
		Log.info.println("eventFlag 		="+eventFlag 		+"/");
		Log.info.println("nohpp 			="+nohpp 			+"/");
		Log.info.println("noars 			="+noars 			+"/");
		Log.info.println("perno 			="+perno 			+"/");
		Log.info.println("vacct 			="+vacct 			+"/");
		Log.info.println("vcdbank 		="+vcdbank 		    +"/");
		Log.info.println("dtinput 		="+dtinput 		    +"/");
		Log.info.println("nminput 		="+nminput 		    +"/");
		Log.info.println("nmvacct 		="+nmvacct 		    +"/");
		Log.info.println("moid 			="+moid 			+"/");
		Log.info.println("codegw 			="+codegw 			+"/");
		Log.info.println("ocbcardnumber 	="+ocbcardnumber 	+"/");
		Log.info.println("cultureid 		="+cultureid 		+"/");
		Log.info.println("cardNumber 		="+cardNumber 		+"/");
		Log.info.println("mid 			="+mid 			    +"/");
		Log.info.println("goodname 		="+goodname 		+"/");
		Log.info.println("price 			="+price 			+"/");
		Log.info.println("buyername 		="+buyername 		+"/");
		Log.info.println("buyertel 		="+buyertel 		+"/");
		Log.info.println("buyeremail 		="+buyeremail 		+"/");
		Log.info.println("resulterrCode 	="+resulterrCode 	+"/");

		Log.info.println("v_gubun			="+v_gubun			+"/");
		Log.info.println("userid			="+userid			+"/");
		Log.info.println("usernm			="+usernm			+"/");

		Log.info.println("ispointuse		="+ispointuse	+"/");
		Log.info.println("point			="+vpoint		+"/");

		Log.info.println("v_inputname		="+inputname	+"/");
		Log.info.println("v_inputdate		="+inputdate	+"/");

		String v_grcode   = BillData.get("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}


		/* 도서 정보 */
		ResultSet rs1   = null;
		Statement stmt1 = null;
		String sql1   = "";

		int     v_seq           = 0;
		String  s_userid        = BillData.get("userid");
		String  v_bookcode      = BillData.get("p_bookcode");
		String  v_dis_price     = (String)BillData.get("p_dis_price");
		String  v_realpaymoney  = (String)BillData.get("p_realpaymoney");
		String  v_accountname   = (String)BillData.get("p_accountname");
		String  v_paydate       = (String)BillData.get("p_paydate");
		String  v_receive       = (String)BillData.get("p_receive");
		String  v_post1         = (String)BillData.get("p_post1");
		String  v_post2         = (String)BillData.get("p_post2");
		String  v_addr1         = (String)BillData.get("p_addr1");
		String  v_addr2         = (String)BillData.get("p_addr2");
		String  v_phone         = (String)BillData.get("p_phone");


		Log.info.println("s_userid       = " + s_userid       );
		Log.info.println("v_bookcode     = " + v_bookcode     );
		Log.info.println("v_dis_price    = " + v_dis_price    );
		Log.info.println("v_realpaymoney = " + v_realpaymoney );
		Log.info.println("v_accountname  = " + v_accountname  );
		Log.info.println("v_paydate      = " + v_paydate      );
		Log.info.println("v_receive      = " + v_receive      );
		Log.info.println("v_post1        = " + v_post1        );
		Log.info.println("v_post2        = " + v_post2        );
		Log.info.println("v_addr1        = " + v_addr1        );
		Log.info.println("v_addr2        = " + v_addr2        );
		Log.info.println("v_phone        = " + v_phone        );

		try
		{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql = "select * from tz_billinfo where grcode = '" + v_grcode + "' and tid = '" + tid + "'";

			Log.info.println("sql="+sql);
			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
				result = 0;
			} else {
				//결제정보 저장
				sql = "insert into tz_billinfo(\n";
				sql+= "			grcode,			\n"; //01.grcode
				sql+= "			tid,			\n"; //02.거래번호
				sql+= "			resultcode,     \n"; //03.결과코드
				sql+= "			resultmsg,      \n"; //04.결과내용
				sql+= "			paymethod,      \n"; //05.지불방법
				sql+= "			mid,            \n"; //06.상점 ID
				sql+= "			goodname,       \n"; //07.과정명
				sql+= "			userid,         \n"; //08.사용자ID
				sql+= "			usernm,         \n"; //09.이름
				sql+= "			price,          \n"; //10.결제금액
				sql+= "			buyername,      \n"; //11.구매자 이름
				sql+= "			buyertel,       \n"; //12.구매자 전화번호
				sql+= "			buyeremail,     \n"; //13.구매자 Email
				sql+= "			resulterrcode,  \n"; //14.결과에러코드
				sql+= "			price1,         \n"; //15.OkCashBag 신용카드 금액
				sql+= "			price2,         \n"; //16.OkCashBag 포인트 금액
				sql+= "			authcode,		\n"; //17.신용카드 승인번호
				sql+= "			cardquota,      \n"; //18.할부기간
				sql+= "			quotainterest,  \n"; //19.무이자 할부여부
				sql+= "			cardcode,       \n"; //20.신용카드사 코드
				sql+= "			cardissuercode, \n"; //21.카드발급사 코드
				sql+= "			authcertain,    \n"; //22.본인인증 수행여부
				sql+= "			pgauthdate,     \n"; //23.이니시스 승인날짜
				sql+= "			pgauthtime,     \n"; //24.이니시스 승인시각
				sql+= "			ocbsaveauthcode,\n"; //25.OKCashB 적립승인번호
				sql+= "			ocbuseauthcode, \n"; //26.OKCashB 사용승인번호
				sql+= "			ocbauthdate,    \n"; //27.OKCashB 승인일시
				sql+= "			eventflag,      \n"; //28.각종이벤트 적용여부
				sql+= "			perno,          \n"; //29.송금자 주민번호
				sql+= "			vacct,          \n"; //30.가상계좌번호
				sql+= "			vcdbank,        \n"; //31.입금할 은행코드
				sql+= "			dtinput,        \n"; //32.입금예정일
				sql+= "			nminput,        \n"; //33.송금자명
				sql+= "			nmvacct,        \n"; //34.예금주명
				sql+= "			moid,           \n"; //35.상점주문번호
				sql+= "			ocbcardnumber,  \n"; //36.OKCashB 결제카드번호
				sql+= "			cardNumber,     \n"; //37.신용카드번호
				sql+= "			cancelyn,       \n"; //38.취소여부
				sql+= "			cancelresult,   \n"; //39.취소결과
				sql+= "			canceldate,     \n"; //40.취소날짜
				sql+= "			canceltime,     \n"; //41.취소시간
				sql+= "			rcashcancelno,  \n"; //42.현금영수증 승인번호
				sql+= "			luserid,        \n"; //43.등록자
				sql+= "			ldate,          \n"; //44.등록시간
				sql+= "			point,			\n"; //45. 포인트
				sql+= "			gubun,			\n"; //46. 구분(N:일반,P:패키지,B:도서)
				sql+= "			inputname,		\n"; //47. 입금자명
				sql+= "			inputdate)      \n"; //48. 입금예정일
				sql+= "values(  ?, ?, ?, ?, \n"; 	 //1 ~ 4
				sql+= "			?, ?, ?,\n"; 	 //5 ~ 7
				sql+= "			?, ?, ?,"; //8~10
				sql+= "			?, ?,\n"; 	 //11 ~ 12
				sql+= "			?, ?, ?, ?, ?,\n"; 	 //13 ~ 17
				sql+= "			?, ?, ?, ?, ?,\n"; 	 //18 ~ 22
				sql+= "			?, ?, ?, ?, ?,\n"; 	 //23 ~ 27
				sql+= "			?, ?, ?, ?, ?,\n"; 	 //28 ~ 32
				sql+= "			?, ?, ?, ?, ?,'N',\n"; //33 ~ 37
				sql+= "			null, null, null, null, ?, \n"; //38 ~ 43
				sql+= "			?, ?, ?, ?, ?)			  \n"; //44 ~ 48

				pstmt1 = connMgr.prepareStatement(sql);
				//	            System.out.println(":v_grcode v_grcode = " + v_grcode);
				//	            System.out.println(":tid tid = " + tid);
				pstmt1.setString( 1,     v_grcode);			 //grcode
				pstmt1.setString( 2,     tid);				 //거래번호


				pstmt1.setString( 3,     resultCode);        //결과코드
				pstmt1.setString( 4,     resultMsg);         //결과내용
				pstmt1.setString( 5,     payMethod);         //지불방법
				pstmt1.setString( 6,     mid);               //상점 ID
				pstmt1.setString( 7,     goodname);          //과정명
				pstmt1.setString( 8,     userid);            //사용자ID
				pstmt1.setString( 9,     usernm);            //이름

				pstmt1.setString(10,     price);             //결제금액
				pstmt1.setString(11,     buyername);         //구매자 이름

				pstmt1.setString(12,     buyertel);          //구매자 전화번호
				pstmt1.setString(13,     buyeremail);        //구매자 Email
				pstmt1.setString(14,     resulterrCode);     //결과에러코드

				pstmt1.setString(15,     price1);            //OkCashBag 신용카드 금액
				pstmt1.setString(16,     price2);            //OkCashBag 포인트 금액
				pstmt1.setString(17,     authCode);          //신용카드 승인번호
				pstmt1.setString(18,     cardQuota);         //할부기간
				pstmt1.setString(19,     quotaInterest);     //무이자 할부여부
				pstmt1.setString(20,     cardCode);          //신용카드사 코드
				pstmt1.setString(21,     cardIssuerCode);    //카드발급사 코드
				pstmt1.setString(22,     authCertain);       //본인인증 수행여부
				pstmt1.setString(23,     pgAuthDate);        //이니시스 승인날짜
				pstmt1.setString(24,     pgAuthTime);        //이니시스 승인시각
				pstmt1.setString(25,     ocbSaveAuthCode);   //OKCashB 적립승인번호
				pstmt1.setString(26,     ocbUseAuthCode);    //OKCashB 사용승인번호
				pstmt1.setString(27,     ocbAuthDate);       //OKCashB 승인일시
				pstmt1.setString(28,     eventFlag);         //각종이벤트 적용여부
				pstmt1.setString(29,     perno);             //송금자 주민번호
				pstmt1.setString(30,     vacct);             //가상계좌번호
				pstmt1.setString(31,     vcdbank);           //입금할 은행코드
				pstmt1.setString(32,     dtinput);           //입금예정일
				pstmt1.setString(33,     nminput);           //송금자명
				pstmt1.setString(34,     nmvacct);           //예금주명

				pstmt1.setString(35,     moid);              //상점주문번호
				pstmt1.setString(36,     ocbcardnumber);     //OKCashB 결제카드번호
				pstmt1.setString(37,     cardNumber);        //신용카드번호
				pstmt1.setString(38,     userid);           //등록자
				pstmt1.setString(39,     currentDate);             //등록시간
				pstmt1.setString(40,     vpoint);             //포인트

				pstmt1.setString(41,     "B");             //구분(N:일반,P:패키지,B:도서)
				pstmt1.setString(42,     inputname);             //무통장 입금자명
				pstmt1.setString(43,     inputdate);             //무통장 입금예정일

				//				System.out.println("v_gubun = " + v_gubun);
				//				System.out.println("inputname = " + inputname);
				//				System.out.println("inputdate = " + inputdate);

				isOk1 = pstmt1.executeUpdate();

				Log.info.println("isOk1 = " + isOk1);

				Log.info.println("v_gubun = " + v_gubun);

				if (resulterrCode.equals("170204") || resulterrCode.equals("1979중")) {
					//ISP 이중 결제는 tz_propose에 Update하지 않는다.(코드 170204)
					//겨좌이체 이중 결제는 tz_propose에 Update하지 않는다.(코드 1979중)
					isOk1 = 1;
					isOk2 = 1;
					isOk3 = 1;
					isOk4 = 1;
					result = 1;
				} else {
					//도서 신청

					Log.info.println("#2");

					//---------------------- 교재 번호 가져온다 ----------------------------
					stmt1 = connMgr.createStatement();
					Log.info.println("#3");
					sql = "select isnull(max(seq) + 1, 1) maxseq from TZ_BOOKSELL ";
					Log.info.println("#4");
					rs1 = stmt1.executeQuery(sql);
					Log.info.println("#5");
					if (rs1.next()) {
						Log.info.println("#6");
						v_seq = rs1.getInt("maxseq");
						Log.info.println("#7");
					}
					rs1.close();
					//-------------------------------------------------------------------------
					Log.info.println("#8");
					//----------------------   교재판매 table 에 입력  --------------------------
					sql1  = " insert into TZ_BOOKSELL(seq, userid, bookcode, dis_price, indate, accountname, realpaymoney, paydate,  ";
					sql1 += "                         paystat, receive, phone, post1, post2, addr1, addr2, iscancel, luserid, ldate,tid) ";
					sql1 += "                 values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDD'), ?, ?, ?,               ";
					sql1 += "                         'B', ?, ?, ?, ?, ?, ?, 'N', ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?)     ";
					Log.info.println("#9");
					pstmt2 = connMgr.prepareStatement(sql1);
					pstmt2.setInt   ( 1, v_seq);
					pstmt2.setString( 2, s_userid);
					pstmt2.setString( 3, v_bookcode);
					pstmt2.setString   ( 4, v_dis_price);
					pstmt2.setString( 5, v_accountname);
					Log.info.println("#10");
					pstmt2.setString   ( 6, v_realpaymoney);
					pstmt2.setString( 7, v_paydate);
					pstmt2.setString( 8, v_receive);
					pstmt2.setString(9, v_phone);
					pstmt2.setString(10, v_post1);
					pstmt2.setString(11, v_post2);
					pstmt2.setString(12, v_addr1);
					Log.info.println("#11");
					pstmt2.setString(13, v_addr2);
					pstmt2.setString(14, s_userid);
					Log.info.println("#12");
					pstmt2.setString(15, tid);
					Log.info.println("#13");
					isOk2 = pstmt2.executeUpdate();
					Log.info.println("#14 = " + isOk2);
				}

			}

			Log.info.println("isOk1 = " + isOk1);
			Log.info.println("isOk2 = " + isOk2);

			ls.close();

			if (isOk1 > 0 && isOk2 > 0) {
				result = 1;
				connMgr.commit();
			} else {
				result = 0;
				connMgr.rollback();
			}

		}
		catch(Exception ex) {
			result = 0;
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, null, sql);
			Log.info.println("Err = " + ex.getMessage());
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {} }
			if(pstmt4 != null) { try { pstmt4.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}

	/**
	 * 결제현황
	 * @param  box receive from the form object and session
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList<DataBox> selectMyBillList(RequestBox box) throws Exception {
		@SuppressWarnings("unused")
		PreparedStatement pstmt = null;
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;

		String sql    	  = "";

		String s_userid   = box.getSession("userid");
		String v_grcode   = box.getSession("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList<DataBox>();

			sql = "select 	grcode, 		";
			sql+= "			gubun,          ";
			sql+= "			case gubun 	when 'N' then '일반' ";
			sql+= "				 		when 'P' then '전문가'	";
			sql+= "						when 'B' then '도서' end gubunnm,          ";
			sql+= "			tid,            ";
			sql+= "			resultcode,     ";
			sql+= "			resultmsg,      ";
			sql+= "			paymethod,      ";
			sql+= "			case paymethod 	when 'CARD' 		then '신용카드' ";
			sql+= "							when 'VCARD' 		then '신용카드' ";
			sql+= "							when 'DirectBank' 	then '계좌이체' ";
			sql+= "							when 'BankBook' 	then '무통장'	end paymethodnm,      ";
			sql+= "			mid,            ";
			sql+= "			goodname,       ";
			sql+= "			userid,         ";
			sql+= "			usernm,         ";
			sql+= "			price,          ";
			sql+= "			point,          ";
			sql+= "			buyername,      ";
			sql+= "			buyertel,       ";
			sql+= "			buyeremail,     ";
			sql+= "			resultcode,     ";
			sql+= "			pgauthdate,     ";
			sql+= "			pgauthtime,     ";
			sql+= "			moid,           ";
			sql+= "			cancelyn,       ";
			sql+= "			cancelresult,   ";
			sql+= "			canceldate,     ";
			sql+= "			canceltime      ";
			sql+= "from 	tz_billinfo     ";
			sql+= "where 	grcode  = '" + v_grcode + "'  and ";
			sql+= "			userid  = '" + s_userid + "'  ";
			sql+= "order by ldate desc ";

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
	 * 거래상세 정보
	 * @param  box receive from the form object and session
	 * @return ArrayList
	 * @throws Exception
	 */
	public DataBox selectMyBillInfo(RequestBox box) throws Exception {
		@SuppressWarnings("unused")
		PreparedStatement pstmt = null;
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		DataBox dbox = null;

		String sql    	  = "";

		String s_userid   = box.getSession("userid");
		String v_grcode   = box.getSession("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}
		String v_tid	  = box.getString("p_tid");

		try {
			connMgr = new DBConnectionManager();

			sql = "select 	*	 ";
			sql+= "from 	tz_billinfo ";
			sql+= "where 	grcode    = '" + v_grcode + "' 	and ";
			sql+= "			userid  = '" + s_userid + "'  and ";
			sql+= "			tid		= '" + v_tid + "' and ";
			sql+= "order by ldate ";

			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				dbox = ls.getDataBox();
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
		return dbox;
	}

	/**
	 * 장바구니 리스트 - 과정(결제완료후)
	 * @param  box receive from the form object and session
	 * @return ArrayList
	 * @throws Exception
	 */
	public ArrayList<DataBox> selectMyBasketBillList(RequestBox box) throws Exception {
		@SuppressWarnings("unused")
		PreparedStatement pstmt = null;
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		DataBox dbox = null;

		String sql    	  = "";

		String s_userid   = box.getSession("userid");
		String v_grcode   = box.getSession("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}
		String v_tid      = box.getString("p_tid");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList<DataBox>();

			sql = "select 	a.grcode	,";
			sql+= "			a.userid	,";
			sql+= "			a.seq		,";
			sql+= "			a.gubun		,";
			sql+= "			a.subj		,";
			sql+= "			a.year		,";
			sql+= "			a.subjseq	,";
			sql+= "			a.price		,";
			sql+= "			a.amount	,";
			sql+= "			a.ldate		,";
			sql+= "			(select subjnm from tz_subj where subj=a.subj) subjnm, ";
			sql+= "			b.subjseq	,";
			sql+= "			b.edustart	,";
			sql+= "			b.eduend	 ";
			sql+= "from 	tz_basketbill a	,";
			sql+= "			tz_subjseq b ";
			sql+= "where 	a.grcode  = '" + v_grcode + "'  and ";
			sql+= "			a.userid  = '" + s_userid + "'  and ";
			sql+= "			a.tid	  = '" + v_tid    + "'  and ";
			sql+= "			a.gubun   = 'N' 		and ";
			sql+= "			a.grcode  = b.grcode 	and ";
			sql+= "			a.subj    = b.subj 		and ";
			sql+= "			a.year    = b.year 		and ";
			sql+= "			a.subjseq = b. subjseq ";
			sql+= "order by a.ldate ";

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
	 * 수강신청 및 도서신청 정보 저장
	 * @param    box          receive from the form object and session
	 * @return ArrayList  자료실 리스트
	 * @throws Exception
	 */
	public int InsertPropose(DBConnectionManager connMgr, String v_gubun, String v_grcode, String tid, String userid ) throws Exception {

		PreparedStatement pstmt2 = null;
		String sql = "";
		int isOk2 = 0;

		try
		{
			if (v_gubun.equals("N")) {
				sql = "insert into tz_propose( 	 ";
				sql+= "				subj		,";
				sql+= "				year		,";
				sql+= "				subjseq		,";
				sql+= "				userid		,";
				sql+= "				comp		,";
				sql+= "				jik			,";
				sql+= "				appdate		,";
				sql+= "				isdinsert	,";
				sql+= "				isb2c		,";
				sql+= "				ischkfirst	,";
				sql+= "				chkfirst	,";
				sql+= "				chkfinal	,";
				sql+= "				tid			,";
				sql+= "				ldate	)	 ";
				sql+= "select 		a.subj, 	 ";
				sql+= "				a.year, 	 ";
				sql+= "				a.subjseq,	 ";
				sql+= "				a.userid,	 ";
				sql+= "				b.comp,		 ";
				sql+= "				b.jikwi,	 ";
				sql+= "				to_char(sysdate, 'YYYYMMDDHH24MISS'), ";
				sql+= "				'N',		 ";
				sql+= "				'N',		 ";
				sql+= "				'N',		 ";
				sql+= "				'Y',		 ";
				sql+= "				'B',		 ";
				sql+= "				'" + tid + "', ";
				sql+= "				to_char(sysdate, 'YYYYMMDDHH24MISS') ";
				sql+= "from 		tz_basket a, ";
				sql+= "				tz_member b ";
				sql+= "where 		a.userid = b.userid and ";
				sql+= "				a.grcode = '" + v_grcode + "' and a.grcode=b.grcode and";
				sql+= "				a.userid = '" + userid + "' and ";
				sql+= "				a.gubun='N' ";
				Log.info.println("sql="+sql);
				Log.info.println("#3");
				pstmt2 = connMgr.prepareStatement(sql);

				isOk2 = pstmt2.executeUpdate();

				Log.info.println("#4 isok = " + isOk2);
				//패키지과정
			} else if (v_gubun.equals("P")) {
				Log.info.println("#5");
				sql = "insert into tz_propose( 	 ";
				sql+= "				subj		,";
				sql+= "				year		,";
				sql+= "				subjseq		,";
				sql+= "				userid		,";
				sql+= "				comp		,";
				sql+= "				jik			,";
				sql+= "				appdate		,";
				sql+= "				isdinsert	,";
				sql+= "				isb2c		,";
				sql+= "				ischkfirst	,";
				sql+= "				chkfirst	,";
				sql+= "				chkfinal	,";
				sql+= "				tid			,";
				sql+= "				ldate	)	 ";
				sql+= "select 		c.subj, 	 ";
				sql+= "				c.year, 	 ";
				sql+= "				c.subjseq,	 ";
				sql+= "				a.userid,	 ";
				sql+= "				b.comp,		 ";
				sql+= "				b.jikwi,	 ";
				sql+= "				to_char(sysdate, 'YYYYMMDDHH24MISS'), ";
				sql+= "				'N',		 ";
				sql+= "				'N',		 ";
				sql+= "				'N',		 ";
				sql+= "				'Y',		 ";
				sql+= "				'B',		 ";
				sql+= "				'" + tid + "',";
				sql+= "				to_char(sysdate, 'YYYYMMDDHH24MISS') ";
				sql+= "from 		tz_basket a, ";
				sql+= "				tz_member b, ";
				sql+= "				vz_scsubjseq c ";
				sql+= "where 		a.userid = b.userid 		and ";
				sql+= "				a.course = c.course 		and	";
				sql+= "				a.courseyear = c.gyear 		and ";
				sql+= "				a.courseseq = c.courseseq 	and ";
				sql+= "				a.grcode = '" + v_grcode + "' and a.grcode=b.grcode and ";
				sql+= "				a.userid = '" + userid + "' and ";
				sql+= "				a.gubun='P' ";
				Log.info.println("sql="+sql);

				pstmt2 = connMgr.prepareStatement(sql);
				Log.info.println("#6");
				isOk2 = pstmt2.executeUpdate();
				Log.info.println("#7 isok = " + isOk2);
			} else if (v_gubun.equals("B")) {
				//도서 신청
				isOk2 = 1;
			}
		}
		catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
		}
		return isOk2;
	}

	/**
	 * 장바구니 정보 자료 이동
	 * @param    box          receive from the form object and session
	 * @return ArrayList  자료실 리스트
	 * @throws Exception
	 */
	public int MoveBasketBill(DBConnectionManager connMgr, String v_gubun, String v_grcode, String tid, String userid ) throws Exception {

		PreparedStatement pstmt3 = null;
		PreparedStatement pstmt4 = null;
		String sql = "";
		int isOk3 = 0;
		int isOk4 = 0;

		try
		{
			Log.info.println("#8");
			sql = "insert into tz_basketbill( ";
			sql+= "			GRCODE		,     ";
			sql+= "			TID			,     ";
			sql+= "			USERID      ,     ";
			sql+= "			SEQ         ,     ";
			sql+= "			GUBUN       ,     ";
			sql+= "			SUBJ        ,     ";
			sql+= "			YEAR        ,     ";
			sql+= "			SUBJSEQ     ,     ";
			sql+= "			COURSE      ,     ";
			sql+= "			COURSEYEAR  ,     ";
			sql+= "			COURSESEQ   ,     ";
			sql+= "			BOOKCODE    ,     ";
			sql+= "			UNIT        ,     ";
			sql+= "			PRICE       ,     ";
			sql+= "			AMOUNT      ,     ";
			sql+= "			LDATE           ) ";
			sql+= "select	GRCODE		,     ";
			sql+= "			'" + tid + "',    ";
			sql+= "			USERID      ,     ";
			sql+= "			SEQ         ,     ";
			sql+= "			GUBUN       ,     ";
			sql+= "			SUBJ        ,     ";
			sql+= "			YEAR        ,     ";
			sql+= "			SUBJSEQ     ,     ";
			sql+= "			COURSE      ,     ";
			sql+= "			COURSEYEAR  ,     ";
			sql+= "			COURSESEQ   ,     ";
			sql+= "			BOOKCODE    ,     ";
			sql+= "			UNIT        ,     ";
			sql+= "			PRICE       ,     ";
			sql+= "			AMOUNT      ,     ";
			sql+= "			LDATE             ";
			sql+= "from 	tz_basket		  ";
			sql+= "where 	grcode = '" + v_grcode + "' and ";
			sql+= "			userid = '" + userid + "' and ";
			sql+= "			gubun='" + v_gubun + "' ";

			Log.info.println("sql = " + sql);

			pstmt3 = connMgr.prepareStatement(sql);
			Log.info.println("#9");
			isOk3 = pstmt3.executeUpdate();
			Log.info.println("#10 isok3 = " + isOk3);

			//tz_basket 삭제
			if (isOk3 > 0) {
				Log.info.println("#11");
				sql = "delete tz_basket ";
				sql+= "where 	grcode = '" + v_grcode + "' and ";
				sql+= "			userid = '" + userid + "' and ";
				sql+= "			gubun='" + v_gubun + "' ";

				pstmt4 = connMgr.prepareStatement(sql);

				Log.info.println("#12");
				isOk4 = pstmt4.executeUpdate();
				Log.info.println("#13 isOk4 = " + isOk4);
			}

		}
		catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {} }
			if(pstmt4 != null) { try { pstmt4.close(); } catch (Exception e) {} }
		}
		return isOk3 * isOk4;
	}


	/**
	 * 무료과정 수강신청
	 * @param    box          receive from the form object and session
	 * @return ArrayList  자료실 리스트
	 * @throws Exception
	 */
	public int InsertProposeFree(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;

		int isOk = 0;
		int isOk2 = 0;

		String s_userid  = box.getSession("userid");
		String v_grcode   = box.getSession("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   = "N000001";
		}
		String v_gubun	 = box.getString("p_gubun");
		String v_tid	 = FormatDate.getDate("yyyyMMddHHmmss");

		//		System.out.println("s_userid = " + s_userid );
		//		System.out.println("v_grcode = " + v_grcode );
		//		System.out.println("v_gubun	 = " + v_gubun	);
		//		System.out.println("v_tid	 = " + v_tid	);

		try
		{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			isOk = InsertPropose(connMgr, v_gubun, v_grcode, v_tid, s_userid);

			//			System.out.println("isOk = " + isOk);

			Log.info.println("isOk = " + isOk);

			isOk2 = MoveBasketBill(connMgr, v_gubun, v_grcode, v_tid, s_userid );

			if (isOk > 0 && isOk2 > 0){
				connMgr.commit();
			} else {
				connMgr.rollback();
			}
		}
		catch(Exception ex) {
			isOk = 0;
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, "");
			throw new Exception(ex.getMessage());
		}
		finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk * isOk2;
	}


	/**
	 * 결제취소 정보 저장
	 */
	public int bill_cancel(Hashtable<String, String> BillData) throws Exception
	{
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		DBConnectionManager connMgr = null;

		int result = 0;
		int isOk1 = 0, isOk2 = 0;
		String sql = "";
		int maxseq = 0;

		ListSet ls = null;

		String  currentDate	  		= FormatDate.getDate("yyyyMMddHHmmss");

		String 	tid 				= (String)BillData.get("tid"); 					// 거래번호
		String 	mid 				= (String)BillData.get("mid"); 					// 상점ID
		String 	resultmsg 			= (String)BillData.get("resultmsg"); 			// 결과내용
		String 	resultcode 			= (String)BillData.get("resultcode"); 			// 결과코드
		String 	pgcanceldate 		= (String)BillData.get("pgcanceldate"); 		// 취소일
		String 	pgcanceltime 		= (String)BillData.get("pgcanceltime"); 		// 취소시간
		String 	rcash_cancel_noappl = (String)BillData.get("rcash_cancel_noappl"); 	// 현금영수증 승인번호

		String 	s_userid			= (String)BillData.get("userid");				// 사용자ID

		String  cancelyn			= "";

		if (resultcode.equals("00"))
			cancelyn = "Y";
		else
			cancelyn = "N";

		//		System.out.println("tid 				= " + tid 				 );
		//		System.out.println("mid 				= " + mid 				 );
		//		System.out.println("resultmsg 			= " + resultmsg 		 );
		//		System.out.println("resultcode 			= " + resultcode 		 );
		//		System.out.println("pgcanceldate 		= " + pgcanceldate 		 );
		//		System.out.println("pgcanceltime 		= " + pgcanceltime 		 );
		//		System.out.println("rcash_cancel_noappl = " + rcash_cancel_noappl);
		//		System.out.println("cancelyn = " 			+ cancelyn);

		//String tmp_ErrCode[] 	= resultmsg.spli t("]");
		//String resulterrCode 	= resultmsg.substring(1,tmp_ErrCode[0].length());
		String resulterrCode 	= resultmsg.substring(1,resultmsg.indexOf("]"));

		//		System.out.println("resulterrCode = " 			+ resulterrCode);

		try
		{
			//일련번호 구하기
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql = "select isnull(max(seq),0) + 1 maxseq from tz_billcancel where tid = '" + tid + "'";

			//        	System.out.println("sql="+sql);
			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
				maxseq = ls.getInt("maxseq");
			}

			//tb_billcancel에 저장
			sql = "insert into tz_billcancel( \n";
			sql+= "				tid, \n";
			sql+= "				seq, \n";
			sql+= "				mid, \n";
			sql+= "				resultmsg, \n";
			sql+= "				cancelyn, \n";
			sql+= "				cancelresult, \n";
			sql+= "				canceldate, \n";
			sql+= "				canceltime, \n";
			sql+= "				rcashcancelno, \n";
			sql+= "				luserid, \n";
			sql+= "				ldate ) \n";
			sql+= "values(		?,?,?,?,?,?,?,?,?,?,?)  \n";

			//    		System.out.println("sql = " + sql);

			pstmt1 = connMgr.prepareStatement(sql);

			pstmt1.setString( 1,     tid);				//거래번호
			pstmt1.setInt   ( 2,     maxseq);				//취소일련번호
			pstmt1.setString( 3,     mid);				//상점ID
			pstmt1.setString( 4,     resultmsg);			//결과내용
			pstmt1.setString( 5,     cancelyn);			//취소여부
			pstmt1.setString( 6,     resultcode);		//결과코드
			pstmt1.setString( 7,     pgcanceldate);		//취소날짜
			pstmt1.setString( 8,     pgcanceltime);		//취소시간
			pstmt1.setString( 9,     rcash_cancel_noappl);		//현금영수증 번호
			pstmt1.setString(10,     s_userid);			//사용자 ID
			pstmt1.setString(11,     currentDate);		//날짜

			isOk1 = pstmt1.executeUpdate();

			if (resulterrCode.equals("1225")) {
				//이미 취소된 결제는 tb_billinfo에 Update하지 않는다.(코드 1225)
				isOk2 = 1;
				//	        	System.out.println("이미 취소됨");
			} else {
				//tb_billinfo에 정보 UPdate
				//CANCELYN='Y',CANCELRESULT,CANCELDATE,CANCELTIME,RCASHCANCELNO
				sql = "update 	tz_billinfo \n";
				sql+= "set		cancelyn 		= ?, \n";
				sql+= "			cancelresult 	= ?, \n";
				sql+= "			canceldate 		= ?, \n";
				sql+= "			canceltime 		= ?, \n";
				sql+= "			rcashcancelno 	= ? \n";
				sql+= "where	tid 			= ? ";

				pstmt2 = connMgr.prepareStatement(sql);

				pstmt2.setString( 1,     cancelyn);			//취소여부
				pstmt2.setString( 2,     resultcode);		//취소결과
				pstmt2.setString( 3,     pgcanceldate);		//취소날짜
				pstmt2.setString( 4,     pgcanceltime);		//취소시간
				pstmt2.setString( 5,     rcash_cancel_noappl);		//현금영수증 번호
				pstmt2.setString( 6,     tid);				//거래번호

				isOk2 = pstmt2.executeUpdate();
			}

			//			System.out.println("isOk1 = " + isOk1);
			//			System.out.println("isOk2 = " + isOk2);

			ls.close();

			if (isOk1 == 1 && isOk2 == 1) {
				result = 1;
				connMgr.commit();
			} else {
				result = 0;
				connMgr.rollback();
			}

		}
		catch (Exception e11) {
			result = 0;
			connMgr.rollback();
			throw new Exception("sql = " + sql + "\r\n" + e11.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}

}