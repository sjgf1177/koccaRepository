//**********************************************************
//  1. 제      목: 모듈 관리
//  2. 프로그램명 : MenuSubAdminBean.java
//  3. 개      요: 메뉴 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12.  16
//  7. 수      정:
//**********************************************************

package com.credu.system;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * 메뉴 관리(ADMIN)
 *
 * @date   : 2003. 7
 * @author : s.j Jung
 */
@SuppressWarnings("unchecked")
public class MenuSubAdminBean {
	private static final String CONFIG_NAME = "cur_nrm_grcode";
	public MenuSubAdminBean() {}


	/**
    모듈 삭제할때 - 하위 프로세서 삭제
    @param box      receive from the form object and session
    @return isOk    1:delete success,0:delete fail
	 */
	public int deleteMenuSub(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		String sql1 = "";
		String sql2 = "";

		int isOk1 = 0;
		int isOk2 = 0;

		String v_grcode  = CodeConfigBean.getConfigValue(CONFIG_NAME);
		String v_menu   = box.getString("p_menu");
		int    v_seq = box.getInt("p_seq");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);////

			sql1  = " delete from TZ_MENUSUB                       ";
			sql1 += "   where grcode = ? and menu = ? and seq = ?  ";

			sql2  = " delete from TZ_MENUSUBPROCESS    ";
			sql2 += "   where grcode = ? and menu = ? and seq = ?  ";


			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setString(1, v_grcode);
			pstmt1.setString(2, v_menu);
			pstmt1.setInt(3, v_seq);
			isOk1 = pstmt1.executeUpdate();


			pstmt2 = connMgr.prepareStatement(sql2);
			pstmt2.setString(1, v_grcode);
			pstmt2.setString(2, v_menu);
			pstmt2.setInt(3, v_seq);
			isOk2 = pstmt2.executeUpdate();

            connMgr.commit();
            
//			if(isOk1 > 0 && isOk2 >0) connMgr.commit();         //      2가지 sql 이 꼭 같이 delete 되어야 하는 경우이므로
//			else connMgr.rollback();
//			if(isOk1 > 0 ) connMgr.commit();         //      하위 분류의 로우가 없을경우 isOk2 가 0 이므로 isOk2 >0 조건 제외
//			else connMgr.rollback();
		}
		catch (Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql1+ "\r\n" +sql2);
			throw new Exception("sql = " + sql1 + "\r\n" + sql2 + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
			if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		//        return isOk1*isOk2;
		return isOk1;
	}


	/**
    권한 등록
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
	 */
	public int insertMenuAuth(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		String sql1 = "";
		String sql2 = "";
		//		int isOk1 = 0;
		int isOk2 = 0;
		int isOk2_check = 0;

		ArrayList list1  = selectListGadmin(box);           // 권한종류리스트
		int    v_gadmincnt = list1.size();

		String v_grcode  	 = CodeConfigBean.getConfigValue(CONFIG_NAME);
		String v_systemgubun = box.getString("p_systemgubun");
		String v_menu    	 = box.getString("p_menu");
		int    v_seq     	 = box.getInt("p_seq");
		//Vector v_vgadmin  	 = box.getVector("p_gadmin");
		//Vector v_vcontrolr 	 = box.getVector("p_controlR");
		//Vector v_vcontrolw 	 = box.getVector("p_controlW");

		Vector v_vgadmin[]     = new Vector[v_gadmincnt];
		Vector v_vcontrolr[]   = new Vector[v_gadmincnt];
		Vector v_vcontrolw[]   = new Vector[v_gadmincnt];

		String v_gadmin    	 = "";
		String v_controlr  	 = "";
		String v_controlw  	 = "";
		String v_control   	 = "";

		String s_userid 	 = box.getSession("userid");
		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);////

			// 기존 데이타 삭제
			sql1  = " delete from TZ_MENUAUTH                             ";
			sql1 += "   where grcode = ? and menu  = ? and menusubseq = ? ";
			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setString(1, v_grcode);
			pstmt1.setString(2, v_menu);
			pstmt1.setInt(3, v_seq);
			//			isOk1 =
			pstmt1.executeUpdate();
			// 변경된 자료 등록
			sql2  = " insert into TZ_MENUAUTH (grcode, gadmin, menusubseq, menu, control, systemgubun, luserid, ldate) ";
			sql2 += "                   values (?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))    ";
			pstmt2 = connMgr.prepareStatement(sql2);

			isOk2 = 1;

			for(int i = 0; i < v_gadmincnt; i++) {

				v_vgadmin[i]    = box.getVector("p_gadmin" + i);
				v_vcontrolr[i] 	= box.getVector("p_controlR" + i);
				v_vcontrolw[i]  = box.getVector("p_controlW" + i);

				//   권한 갯수만큼 루프

				v_gadmin = (String)v_vgadmin[i].elementAt(0);

				if (v_vcontrolr[i].size() > 0) v_controlr = (String)v_vcontrolr[i].elementAt(0);
				else							  v_controlr = "";
				if (v_vcontrolw[i].size() > 0) v_controlw = (String)v_vcontrolw[i].elementAt(0);
				else							  v_controlw = "";

				v_control = StringManager.chkNull(v_controlr) + StringManager.chkNull(v_controlw);

				// DB INSERT
				pstmt2.setString(1, v_grcode);
				pstmt2.setString(2, v_gadmin);
				pstmt2.setInt(3, v_seq);
				pstmt2.setString(4, v_menu);
				pstmt2.setString(5, v_control);
				pstmt2.setString(6, v_systemgubun);
				pstmt2.setString(7, s_userid);
				isOk2_check = pstmt2.executeUpdate();
				System.out.println("isOk2_check="+isOk2_check);

				if (isOk2_check == 0) isOk2 = 0;

			}
			// 기존 등록된 자료가 없을 수 있으므로 isOk1 체크 제외
			if ( isOk2 > 0) connMgr.commit();
			else connMgr.rollback();
		}
		catch(Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
			if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk2;
	}


	/**
    모듈 등록할때
    @param box      receive from the form object and session
    @return isOk    1:insert success,0:insert fail
	 */
	public int insertMenuSub(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		PreparedStatement pstmt = null;
		String sql = "";
		String sql1 = "";
		int isOk = 0;

		String v_grcode 	= CodeConfigBean.getConfigValue(CONFIG_NAME);
		String v_menu   	= box.getString("p_menu");
		int    v_seq    	= 0;
		String v_servlet 	= box.getString("p_servlet");
		String v_modulenm  	= box.getString("p_modulenm");
		String v_systemgubun= box.getString("p_systemgubun");

		String s_userid = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			sql  = "select NVL(max(seq),-1) seq from TZ_MENUSUB  ";
			sql += " where grcode = " + StringManager.makeSQL(v_grcode);
			sql += "   and menu   = " + StringManager.makeSQL(v_menu);

			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
				v_seq = ls.getInt(1) + 1;
			} else {
				v_seq = 0;
			}

			sql1 =  "insert into TZ_MENUSUB(grcode, menu, seq, servlet, modulenm, systemgubun, luserid, ldate ) ";
			sql1 += "            values (?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))   ";

			pstmt = connMgr.prepareStatement(sql1);

			pstmt.setString(1,  v_grcode);
			pstmt.setString(2,  v_menu);
			pstmt.setInt(3,  v_seq);
			pstmt.setString(4,  v_servlet);
			pstmt.setString(5,  v_modulenm);
			pstmt.setString(6,  v_systemgubun);
			pstmt.setString(7, s_userid);

			isOk = pstmt.executeUpdate();

			box.put("p_seq",String.valueOf(v_seq));
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}


	/**
    권한 리스트
    @param box          receive from the form object and session
    @return ArrayList   리스트
	 */
	public ArrayList selectListGadmin(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		GadminData data = null;

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();

			sql  = " select gadmin, gadminnm from TZ_GADMIN  ";
			sql += "  where isview = 'Y'                     ";
			sql += "  order by gadmin asc                    ";

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				data = new GadminData();

				data.setGadmin(ls.getString("gadmin"));
				data.setGadminnm(ls.getString("gadminnm"));

				list.add(data);
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
    메뉴화면 리스트
    @param box          receive from the form object and session
    @return ArrayList   메뉴 리스트
	 */
	public ArrayList selectListMenu(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;//, ls1 = null;
		ArrayList list = null;
		String sql = "";//, sql1 ="";
		MenuData data = null;

		String v_grcode  = CodeConfigBean.getConfigValue(CONFIG_NAME);
		String v_searchtext = box.getString("p_searchtext");
		//		int    v_cnt = 0;

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();

			sql  = " select\na.menu, a.menunm, a.upper, a.parent, a.pgm, a.isdisplay, a.para1, a.para2, a.para3, a.para4,\n\ta.para5, a.para6, a.para7, a.para8, a.para9, a.para10, a.para11, a.para12,\n\ta.created, a.luserid, a.ldate, a.levels, a.orders, a.systemgubun, count(b.servlet) cnt\n";
			sql += "from TZ_MENU A, tz_menusub B\n";
			sql += "where A.MENU = B.MENU(+)\nAND a.grcode  = " + StringManager.makeSQL(v_grcode);

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면
				sql += " and menunm like " + StringManager.makeSQL("%" + v_searchtext + "%");
			}
			sql += "\nGROUP BY a.menu, a.menunm, a.upper, a.parent, a.pgm, a.isdisplay, a.para1, a.para2, a.para3, a.para4,\n\ta.para5, a.para6, a.para7, a.para8, a.para9, a.para10, a.para11, a.para12,\n\ta.created, a.luserid, a.ldate, a.levels, a.orders, a.systemgubun\norder by parent asc, levels asc, orders asc, menu asc";

			ls = connMgr.executeQuery(sql);

			while (ls.next())
			{
				data = new MenuData();

				data.setMenu(ls.getString("menu"));
				data.setMenunm(ls.getString("menunm"));
				data.setUpper(ls.getString("upper"));
				data.setParent(ls.getString("parent"));
				data.setPgm(ls.getString("pgm"));
				data.setIsdisplay(ls.getString("isdisplay"));
				data.setPara1(ls.getString("para1"));
				data.setPara2(ls.getString("para2"));
				data.setPara3(ls.getString("para3"));
				data.setPara4(ls.getString("para4"));
				data.setPara5(ls.getString("para5"));
				data.setPara6(ls.getString("para6"));
				data.setPara7(ls.getString("para7"));
				data.setPara8(ls.getString("para8"));
				data.setPara9(ls.getString("para9"));
				data.setPara10(ls.getString("para10"));
				data.setPara11(ls.getString("para11"));
				data.setPara12(ls.getString("para12"));
				data.setCreated(ls.getString("created"));
				data.setLuserid(ls.getString("luserid"));
				data.setLdate(ls.getString("ldate"));
				data.setLevels(ls.getInt("levels"));
				data.setOrders(ls.getInt("orders"));
				data.setSystemgubun(ls.getString("systemgubun"));

				//				sql1 = "select count(*) from tz_menusub where menu='"+ls.getString("menu")+"'";
				//				ls1 = connMgr.executeQuery(sql1);
				//
				//				if(ls1.next())
				//				{
				//					v_cnt = ls1.getInt(1);
				//				}
				//				ls1.close();
				data.setCnt(ls.getInt("CNT"));
				list.add(data);
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
    권한 리스트
    @param box          receive from the form object and session
    @return ArrayList   권한 리스트
	 */
	public ArrayList selectListMenuAuth(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		MenuAuthData data = null;

		String v_grcode  = CodeConfigBean.getConfigValue(CONFIG_NAME);
		String v_menu    = box.getString("p_menu");
		String v_seq     = box.getString("p_seq");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();

			sql  = " select a.gadmin,a.control,a.systemgubun, b.gadminnm  ";
			sql += "   from TZ_MENUAUTH a, TZ_GADMIN b     ";
			sql += "  where a.gadmin = b.gadmin             ";
			sql += "    and a.grcode     = " + StringManager.makeSQL(v_grcode);
			sql += "    and a.menu       = " + StringManager.makeSQL(v_menu);
			sql += "    and a.menusubseq = " + v_seq;
			sql += " order by a.gadmin asc";

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				data = new MenuAuthData();

				data.setGadmin(ls.getString("gadmin"));
				data.setGadminnm(ls.getString("gadminnm"));
				data.setControl(ls.getString("control"));
				data.setSystemgubun(ls.getString("systemgubun"));
				list.add(data);
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
    모듈화면 리스트
    @param box          receive from the form object and session
    @return ArrayList   모듈 리스트
	 */
	public ArrayList selectListMenuSub(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		MenuSubData data = null;

		String v_grcode  = CodeConfigBean.getConfigValue(CONFIG_NAME);
		String v_menu    = box.getString("p_menu");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();

			sql  = " select menu, seq, servlet, modulenm, luserid, ldate    ";
			sql += "   from TZ_MENUSUB                                      ";
			sql += "  where grcode = " + StringManager.makeSQL(v_grcode);
			sql += "    and menu   = " + StringManager.makeSQL(v_menu);
			sql += " order by menu asc";

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				data = new MenuSubData();

				data.setMenu(ls.getString("menu"));
				data.setSeq(ls.getInt("seq"));
				data.setServlet(ls.getString("servlet"));
				data.setModulenm(ls.getString("modulenm"));
				data.setLuserid(ls.getString("luserid"));
				data.setLdate(ls.getString("ldate"));

				list.add(data);
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
    모듈화면 상세보기
    @param box          receive from the form object and session
    @return MenuData    조회한 상세정보
	 */
	public MenuSubData selectViewMenuSub(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		MenuSubData data = null;

		String v_grcode = CodeConfigBean.getConfigValue(CONFIG_NAME);
		String v_menu  = box.getString("p_menu");
		int    v_seq   = box.getInt("p_seq");

		try {
			connMgr = new DBConnectionManager();

			sql  = " select menu, seq, servlet, modulenm, luserid, ldate    ";
			sql += "   from TZ_MENUSUB                                      ";
			sql += "  where grcode = " + StringManager.makeSQL(v_grcode);
			sql += "    and menu   = " + StringManager.makeSQL(v_menu);
			sql += "    and seq    = " + v_seq;

			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				data=new MenuSubData();
				data.setMenu(ls.getString("menu"));
				data.setSeq(ls.getInt("seq"));
				data.setServlet(ls.getString("servlet"));
				data.setModulenm(ls.getString("modulenm"));
				data.setLuserid(ls.getString("luserid"));
				data.setLdate(ls.getString("ldate"));
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) {try {ls.close();} catch(Exception e){}}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return data;
	}

	/**
    모듈 수정하여 저장할때
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail
	 */
	public int updateMenuSub(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 0;

		String v_grcode 	 = CodeConfigBean.getConfigValue(CONFIG_NAME);
		String v_menu   	 = box.getString("p_menu");
		int    v_seq    	 = box.getInt("p_seq");
		String v_servlet 	 = box.getString("p_servlet");
		String v_modulenm  	 = box.getString("p_modulenm");
		String v_systemgubun = box.getString("p_systemgubun");

		String s_userid = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			sql  = " update TZ_MENUSUB set servlet = ? , modulenm = ?, luserid= ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') , systemgubun= ? ";
			sql += "  where grcode = ? and menu = ? and seq = ?                                                                  ";

			pstmt = connMgr.prepareStatement(sql);

			pstmt.setString(1,  v_servlet);
			pstmt.setString(2,  v_modulenm);
			pstmt.setString(3,  s_userid);
			pstmt.setString(4,  v_systemgubun);
			pstmt.setString(5,  v_grcode);
			pstmt.setString(6,  v_menu);
			pstmt.setInt(7,  v_seq);

			isOk = pstmt.executeUpdate();
		}
		catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}


}
