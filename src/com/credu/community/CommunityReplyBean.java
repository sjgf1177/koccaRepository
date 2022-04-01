//**********************************************************
//1. 제      목:
//2. 프로그램명: CommunityFrBoardBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
//
//**********************************************************

package com.credu.community;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("unchecked")
public class CommunityReplyBean {

	public void CommunityFrBoardBean() {
	}

	/**
	 * 댓글 리스트
	 * @param box          receive from the form object and session
	 * @return ArrayList   댓글 리스트
	 * @throws Exception
	 */
	public ArrayList selectList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		String sql    	   		= "";
		StringBuffer headSql  	= new StringBuffer();
		StringBuffer bodySql   	= new StringBuffer();
		String orderSql  = "";

		DataBox             dbox    = null;

		String v_cmuno         = box.getString("p_cmuno");
		String v_brdno         = box.getString("p_brdno");
		String v_menuno        = box.getString("p_menuno");

		try {
			connMgr = new DBConnectionManager();

			headSql.append(" select  a.cmuno, a.menuno, a.brdno, a.rplno              \n ");
			headSql.append("         , a.content, a.userid, a.register_dte            \n ");
			headSql.append("         , a.modifier_dte, b.name                         \n ");
			bodySql.append("   from tz_cmuboardreplay a,tz_member b                   \n ");
			bodySql.append("  where a.userid          = b.userid                      \n ");
			bodySql.append("    and b.grcode  = ").append(StringManager.makeSQL(box.getSession("tem_grcode"))).append("\n ");
			bodySql.append("    and a.cmuno           = '").append(v_cmuno ).append("' \n ");
			bodySql.append("    and a.menuno          = '").append(v_menuno).append("' \n ");
			bodySql.append("    and a.brdno           = '").append(v_brdno ).append("' \n ");
			orderSql = "order by a.rplno desc";


			sql= headSql.toString()+ bodySql.toString()+ orderSql;

			ls = connMgr.executeQuery(sql);

			//countSql= "select count(*) "+ bodySql.toString() ;

			//int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);

			//ls.setPageSize(row);                         // 페이지당 row 갯수를 세팅한다
			//ls.setCurrentPage(v_pageno, total_row_count);// 현재페이지번호를 세팅한다.
			//int total_page_count = ls.getTotalPage();    // 전체 페이지 수를 반환한다
			//int total_row_count = ls.getTotalCount();    // 전체 row 수를 반환한다

			while (ls.next()) {
				dbox = ls.getDataBox();
				//dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
				//dbox.put("d_totalpage", new Integer(total_page_count));
				//dbox.put("d_rowcount" , new Integer(row));
				list.add(dbox);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

	/**
	 * 댓글등록하기
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int insertReply(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt 	= null;
		ListSet     		ls     	= null;
		StringBuffer      	sql     = new StringBuffer();

		int		isOk        = 0;
		int     v_rplno		= 0;

		String v_cmuno          = box.getString("p_cmuno");
		String v_menuno         = box.getString("p_menuno");
		String v_brdno          = box.getString("p_brdno");

		String v_rep_content	= box.getString("p_rep_content" );

		String s_userid         = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql.append(" SELECT  ISNULL(MAX(RPLNO), 0)                    \n ");
			sql.append(" FROM    TZ_CMUBOARDREPLAY                        \n ");
			sql.append(" WHERE   CMUNO   = '").append(v_cmuno ).append("' \n ");
			sql.append(" AND     MENUNO  = '").append(v_menuno).append("' \n ");
			sql.append(" AND     BRDNO   =  ").append(v_brdno );

			ls = connMgr.executeQuery(sql.toString());
			while (ls.next()) v_rplno = ls.getInt(1) + 1;

			sql.setLength(0);

			sql.append(" INSERT INTO TZ_CMUBOARDREPLAY                    \n ");
			sql.append(" (                                                \n ");
			sql.append("     CMUNO, MENUNO, BRDNO, RPLNO, CONTENT, USERID \n ");
			sql.append("     , REGISTER_DTE , MODIFIER_DTE, DEL_FG        \n ");
			sql.append(" )                                                \n ");
			sql.append(" VALUES                                           \n ");
			sql.append(" (                                                \n ");
			sql.append("     ?, ?, ?, ?, ?, ?                             \n ");
			sql.append("     ,TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')        \n ");
			sql.append("     ,TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'),'N'    \n ");
			sql.append(" )                                                \n ");

			pstmt = connMgr.prepareStatement(sql.toString());
			pstmt.setString(1 , v_cmuno     );
			pstmt.setString(2 , v_menuno    );
			pstmt.setString(3 , v_brdno     );
			pstmt.setInt   (4 , v_rplno     );
			pstmt.setString(5, v_rep_content);
			pstmt.setString(6, s_userid     );

			isOk = pstmt.executeUpdate();

			if(isOk > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}

	/**
	 * 댓글삭제하기
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int deleteReply(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt 	= null;
		ListSet     		ls     	= null;
		StringBuffer      	sql     = new StringBuffer();

		int		isOk        = 0;

		String v_cmuno          = box.getString("p_cmuno");
		String v_menuno         = box.getString("p_menuno");
		String v_brdno          = box.getString("p_brdno");
		int    v_rplno          = box.getInt("p_rplno");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql.append(" DELETE FROM    TZ_CMUBOARDREPLAY                 \n ");
			sql.append(" WHERE   CMUNO   = '").append(v_cmuno ).append("' \n ");
			sql.append(" AND     MENUNO  = '").append(v_menuno).append("' \n ");
			sql.append(" AND     BRDNO   = '").append(v_brdno ).append("' \n ");
			sql.append(" AND     RPLNO   =  ").append(v_rplno );

			pstmt = connMgr.prepareStatement(sql.toString());
			isOk = pstmt.executeUpdate();
			if(isOk > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}
}
