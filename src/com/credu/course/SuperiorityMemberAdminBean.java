//**********************************************************
//1. 제      목: 우수회원관리  BEAN
//2. 프로그램명:  SuperiorityMemberAdminBean.java
//3. 개      요: 우수회원관리  BEAN
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 하경태 2006.01.02
//7. 수      정:
//**********************************************************
package com.credu.course;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

@SuppressWarnings("unchecked")
public class SuperiorityMemberAdminBean {

	public SuperiorityMemberAdminBean() {}

	/**
	우수 회원 리스트
	@param box          receive from the form object and session
	@return ArrayList   우수 회원 리스트
	 */
	public ArrayList selectList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls         	= null;
		ArrayList list     	= null;
		DataBox dbox 		= null;
		String sql    	  = "";
		String sql1    	  = "";
		String sql2    	  = "";
		String count_sql  = "";
		String head_sql   = "";
		String body_sql   = "";
		String group_sql  = "";
		String order_sql  = "";

		String  v_userid    = box.getString("p_userid");				//검색할 userid
		String  v_username	= box.getString("p_username"); 				//검색할 username
		String 	v_grgubun	= "superyn";
		String  v_isunum    = box.getStringDefault("p_isunum","");   	//수료 과정수

		String  v_membergubun	= box.getString("p_membergubun");		//정렬할 순서

		//	    String  v_orderColumn   = box.getString("p_orderColumn");		//정렬할 컬럼명
		//	    String  v_orderType     = box.getString("p_orderType");			//정렬할 순서
		int v_pageno = box.getInt("p_pageno");
		int row = 15;
		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			head_sql  = " Select m.userid, m.name, m.membergubun, m.email, Count(s.userid) as isnum, m.indate  ";
			body_sql += " from TZ_MEMBER m ";
			body_sql += "	left outer join  tz_stold s on m.userid = s.userid ";
			//body_sql += "  where " + v_grgubun + " = 'Y' ";

			group_sql += "	Group By  m.userid, m.membergubun,  m.name, m.email, m.indate, m.indate,  " + v_grgubun;

			sql1 = head_sql+ body_sql+group_sql;

			if ( !v_membergubun.equals("")) {	// 회원구분으로 검색
				sql2 += "	and a.membergubun ='" + v_membergubun +"' ";
			}
			if ( !v_userid.equals("")) {	// 회원 ID로 검색
				sql2 += "	and a.userid like ('%" + v_userid +"%') ";
			}
			if ( !v_username.equals("")) {	// 회원 이름으로 검색
				sql2 += "	and a.name like ('%" + v_username +"%') ";
			}
			if ( !v_isunum.equals("")) {	// 회원 이름으로 검색
				sql2 += "	and a.isnum >= " + v_isunum ;
			}

			order_sql += " order by a.indate desc, a.userid asc ";

			sql = " Select a.userid, a.name, a.membergubun, a.email, a.isnum From (" + sql1 + ") a  Where 1=1 " + sql2 + order_sql;

			System.out.println(" selectList.sql ==> "+ sql);
			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) From ( "+ head_sql + body_sql + group_sql + " ) a Where 1 = 1 " + sql2 ;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

			ls.setPageSize(row);                       //  페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);	// 현재페이지번호를 세팅한다.
			int totalpagecount = ls.getTotalPage();    		// 전체 페이지 수를 반환한다

			while (ls.next()) {
				dbox = ls.getDataBox();

				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(totalpagecount));
				dbox.put("d_rowcount", new Integer(row));
				list.add(dbox);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;

	}

	/**
		우수 회원 리스트(Excel)
		@param box          receive from the form object and session
		@return ArrayList   우수 회원 리스트
	 */
	public ArrayList selectExcelList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls         	= null;
		ArrayList list     	= null;
		DataBox dbox 		= null;
		String sql    	  = "";
		String sql1    	  = "";
		String sql2    	  = "";
		//		    String count_sql  = "";
		String head_sql   = "";
		String body_sql   = "";
		String group_sql  = "";
		String order_sql  = "";

		String  v_grcode    = box.getStringDefault("s_grcode","N000001");	//교육그룹
		String  v_userid    = box.getString("p_userid");				//검색할 userid
		String  v_username	= box.getString("p_username"); 				//검색할 username
		String 	v_grgubun	= "";
		String  v_isunum    = box.getStringDefault("p_isunum","");   	//수료 과정수

		String  v_membergubun	= box.getString("p_membergubun");		//정렬할 순서

		//		    String  v_orderColumn   = box.getString("p_orderColumn");		//정렬할 컬럼명
		//		    String  v_orderType     = box.getString("p_orderType");			//정렬할 순서
		//		    int v_pageno = box.getInt("p_pageno");
		//			int row = 15;

		if (v_grcode.equals("N000001"))
		{
			v_grgubun = "koccasuperyn";
		}
		else
		{
			v_grgubun = "gamesuperyn";
		}
		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			head_sql  = " Select m.userid, m.name, m.membergubun, m.email, Count(s.userid) as isnum, m.indate  ";
			body_sql += " from TZ_MEMBER m ";
			body_sql += "	left outer join  tz_stold s on m.userid = s.userid ";
			//body_sql += "  where " + v_grgubun + " = 'Y' ";

			group_sql += "	Group By  m.userid, m.membergubun,  m.name, m.email, m.indate, m.indate  "; //+ v_grgubun;

			sql1 = head_sql+ body_sql+group_sql;

			if ( !v_membergubun.equals("")) {	// 회원구분으로 검색
				sql2 += "	and a.membergubun ='" + v_membergubun +"' ";
			}
			if ( !v_userid.equals("")) {	// 회원 ID로 검색
				sql2 += "	and a.userid like ('%" + v_userid +"%') ";
			}
			if ( !v_username.equals("")) {	// 회원 이름으로 검색
				sql2 += "	and a.name like ('%" + v_username +"%') ";
			}
			if ( !v_isunum.equals("")) {	// 회원 이름으로 검색
				sql2 += "	and a.isnum >= " + v_isunum ;
			}

			order_sql += " order by a.indate desc, a.userid asc ";

			sql = " Select a.userid, a.name, a.membergubun, a.email, a.isnum From (" + sql1 + ") a  Where 1=1 " + sql2 + order_sql;

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = ls.getDataBox();

				list.add(dbox);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;

	}

	/**
	 우수회원 대상자 리스트
	 @param box          receive from the form object and session
	 @return ArrayList   우수회원 대상자 리스트
	 */
	public ArrayList selectMemberList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls         	= null;
		ArrayList list     	= null;
		DataBox dbox 		= null;
		String sql    	  = "";
		String sql1    	  = "";
		String sql2    	  = "";
		String count_sql  = "";
		String head_sql   = "";
		String body_sql   = "";
		String group_sql  = "";
		String order_sql  = "";

		String  v_grcode    = box.getStringDefault("s_grcode","N000001");	//교육그룹
		String  v_userid    = box.getString("p_userid");				//검색할 userid
		String  v_username	= box.getString("p_username"); 				//검색할 username
		String 	v_grgubun	= "";
		String  v_isunum    = box.getStringDefault("p_isunum","3");   	//수료 과정수

		String  v_membergubun	= box.getString("p_membergubun");		//정렬할 순서

		//	     String  v_orderColumn   = box.getString("p_orderColumn");		//정렬할 컬럼명
		//	     String  v_orderType     = box.getString("p_orderType");			//정렬할 순서
		int v_pageno = box.getInt("p_pageno");
		int row = 15;

		if (v_grcode.equals("N000001"))
		{
			v_grgubun = "koccasuperyn";
		}
		else
		{
			v_grgubun = "gamesuperyn";
		}
		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			head_sql  = " Select m.userid, m.name, m.membergubun, m.email, Count(s.userid) as isnum, m.indate  ";
			body_sql += " from TZ_MEMBER m ";
			body_sql += "	left outer join  tz_stold s on m.userid = s.userid ";
			//body_sql += " Where " + v_grgubun + " = 'N'";
			group_sql += "	Group By  m.userid, m.membergubun,  m.name, m.email, m.indate, m.indate  ";  //+ v_grgubun;

			sql1 = head_sql+ body_sql+group_sql;

			if ( !v_membergubun.equals("")) {	// 회원구분으로 검색
				sql2 += "	and a.membergubun ='" + v_membergubun +"' ";
			}
			if ( !v_userid.equals("")) {	// 회원 ID로 검색
				sql2 += "	and a.userid like ('%" + v_userid +"%') ";
			}
			if ( !v_username.equals("")) {	// 회원 이름으로 검색
				sql2 += "	and a.name like ('%" + v_username +"%') ";
			}
			if ( !v_isunum.equals("")) {	// 회원 이름으로 검색
				sql2 += "	and a.isnum >= " + v_isunum ;
			}

			order_sql += " order by a.indate desc, a.userid asc ";

			sql = " Select a.userid, a.name, a.membergubun, a.email, a.isnum From (" + sql1 + ") a  Where 1=1 " + sql2 + order_sql;

			System.out.println(" selectList.sql ==> "+ sql);
			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) From ( "+ head_sql + body_sql + group_sql + " ) a Where 1 = 1 " + sql2 ;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

			ls.setPageSize(row);                       //  페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);	// 현재페이지번호를 세팅한다.
			int totalpagecount = ls.getTotalPage();    		// 전체 페이지 수를 반환한다

			while (ls.next()) {
				dbox = ls.getDataBox();

				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(totalpagecount));
				dbox.put("d_rowcount", new Integer(row));
				list.add(dbox);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

	/**
		우수회원  등록
		@param box      receive from the form object and session
		@return isOk    1:insert success,0:insert fail
	 */
	public int inputMember(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ListSet ls2 = null;
		PreparedStatement pstmt = null;
		PreparedStatement pstmt2 = null;
		PreparedStatement pstmt3 = null;
		//		    String sql = "";
		String sql1 = "";
		String sql2 = "";
		String sql3 = "";
		String sql4 = "";
		String v_grcode = box.getStringDefault("s_grcode","N000001");
		String v_userid = "";
		Vector v_tmp0		= box.getVector("p_checks");
		String tmpArr		= "";

		//			String[] tmpArr1	= null;
		String[] tmpArr2	= null;

		int isOk = 1;
		int isOk2= 0;
		int isOk3= 0;
		int i = 0;

		if(v_grcode.equals("N000001"))
		{
			v_grcode = "koccasuperyn";
		}
		else
		{
			v_grcode = "gamesuperyn";
		}
		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql4  = " select (member_cnt + 1) as member_cnt from tz_cmubasemst where cmuno ='2006000001'";
			ls2 = connMgr.executeQuery(sql4);

//			sql1 =  " Update TZ_MEMBER Set " + v_grcode + " = 'Y' Where userid = ?";
//			pstmt = connMgr.prepareStatement(sql1);

			sql4 =  " Update tz_cmubasemst Set member_cnt = ? Where cmuno ='2006000001'";
			pstmt3 = connMgr.prepareStatement(sql4);

			sql2 = "Insert Into tz_cmuusermst (cmuno, userid, kor_name, grade, email, tel, mobile, request_dte, license_dte) ";
			sql2 += "	Values ('2006000001',?, ?, '04', ?,?,?, to_char(sysdate,'YYYYMMDDHH24MISS'),to_char(sysdate,'YYYYMMDDHH24MISS')) ";
			pstmt2 = connMgr.prepareStatement(sql2);

			for(i = 0; i < v_tmp0.size(); i++)
			{
				tmpArr = v_tmp0.elementAt(i).toString();
				tmpArr2 = tmpArr.split(",");

				v_userid 		= tmpArr2[0];

//				pstmt.setString(1, v_userid);
//				isOk = pstmt.executeUpdate();

				sql3  = " select userid,name,email,hometel,handphone from tz_member where userid ='"+v_userid+"'";
				ls = connMgr.executeQuery(sql3);

				while (ls.next()) {
					pstmt2.setString(1, v_userid);
					pstmt2.setString(2, ls.getString("name") );
					pstmt2.setString(3, ls.getString("email") );
					pstmt2.setString(4, ls.getString("hometel") );
					pstmt2.setString(5, ls.getString("handphone") );
					isOk2=pstmt2.executeUpdate();
				}

				while(ls2.next())
				{
					pstmt3.setInt(1, ls2.getInt("member_cnt"));
					isOk3=pstmt3.executeUpdate();
				}

			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if (isOk*isOk2*isOk3 > 0) {connMgr.commit();}
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(ls2 != null) { try { ls2.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e1) {} }
			if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk*isOk2;
	}

}
