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
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.PageList;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.dunet.common.util.UploadUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("unchecked")
public class CommunityFrBoardBean {
	private ConfigSet config;
	private static int row=10;
	private static final int FILE_LIMIT = 1;                    //    페이지에 세팅된 파일첨부 갯수


	public CommunityFrBoardBean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //이 모듈의 페이지당 row 수를 셋팅한다
			row = 10; //강제로 지정
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public CommunityFrBoardBean(String type) {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 리스트
	 * @param box          receive from the form object and session
	 * @return ArrayList   리스트
	 * @throws Exception
	 */
	public ArrayList selectListBrd(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		String sql    	   		= "";
		String countSql  		= "";
		StringBuffer headSql  	= new StringBuffer();
		StringBuffer bodySql   	= new StringBuffer();
		String orderSql  = "";

		DataBox             dbox    = null;

		String v_cmuno         = box.getString("p_cmuno");
		String v_brd_fg        = box.getString("p_brd_fg");
		String v_menuno        = box.getString("p_menuno");

		String v_searchtext = box.getString("p_searchtext");
		String v_select     = box.getString("p_select");
		int v_pageno        = box.getInt("p_pageno");

		try {
			connMgr = new DBConnectionManager();


			headSql.append(" select /* CommunityFrBoardBean.java : selectListBrd */\n		a.cmuno           cmuno                  ,a.menuno       menuno\n");
			headSql.append("        ,a.brdno           brdno                  ,a.title        title\n");
			headSql.append("        ,a.content         content                ,a.read_cnt     read_cnt\n");
			headSql.append("        ,a.add_cnt         add_cnt                ,a.parent       parent\n");
			headSql.append("        ,a.lv              lv                     ,a.position     position\n");
			headSql.append("        ,a.display_fg      display_fg             ,a.root         root\n");
			headSql.append("        ,a.register_userid register_userid        ,a.register_dte register_dte\n");
			headSql.append("        ,a.modifier_userid modifier_userid        ,a.modifier_dte modifier_dte\n");
			headSql.append("        ,b.userid          userid                 ,b.resno        resno\n");
			headSql.append("        ,c.kor_name        name                   ,c.email        email\n");
			headSql.append("        ,b.deptnam         deptnam                ,b.jikupnm      jikupnm\n");
			headSql.append("        ,b.jikwinm         jikwinm                ,c.tel          tel\n");
			headSql.append("        ,c.mobile          mobile                 ,c.office_tel   office_tel\n");
			headSql.append("        ,c.wk_area         wk_area                ,c.grade        grade\n");
			headSql.append("        ,e.kor_nm          grade_nm               ,isnull(d.cnt,0) cnt\n");
			bodySql.append("   from tz_cmuboard a,tz_member b,tz_cmuusermst c\n");
			bodySql.append("       ,(select cmuno cmuno   ,menuno    menuno\n");
			bodySql.append("               ,brdno brdno   , count(*) cnt\n");
			bodySql.append("          from tz_cmuboardfile\n");
			bodySql.append("         where cmuno     = '"+v_cmuno+"'\n");
			bodySql.append("           and menuno    = '"+v_menuno+"'\n");
			bodySql.append("         group by cmuno,menuno,brdno) d\n");
			bodySql.append("        ,tz_cmugrdcode  e\n");
			bodySql.append("        ,tz_cmumenu  f\n");
			bodySql.append("  where a.register_userid  = b.userid\n");
			bodySql.append("    and b.grcode  = ").append(StringManager.makeSQL(box.getSession("tem_grcode"))).append("\n ");
			bodySql.append("    and a.cmuno            = c.cmuno\n");
			bodySql.append("    and a.register_userid  = c.userid\n");
			bodySql.append("    and c.cmuno            = e.cmuno\n");
			bodySql.append("    and c.grade            = e.grcode\n");
			bodySql.append("    and a.cmuno            =  d.cmuno  (+)\n");
			bodySql.append("    and a.menuno           =  d.menuno (+)\n");
			bodySql.append("    and a.brdno            =  d.brdno (+)\n");
			bodySql.append("    and a.cmuno            = '").append(v_cmuno ).append("'\n");
			bodySql.append("    and a.menuno           = '").append(v_menuno).append("'\n");
			bodySql.append("    and a.del_fg           = 'N'\n");
			bodySql.append("    and a.cmuno            = f.cmuno(+)\n");
			bodySql.append("    and a.menuno           = f.menuno(+)\n");
			bodySql.append("    and f.brd_fg           = '").append(v_brd_fg).append("'\n");


			if ( !v_searchtext.equals("")) {      // 검색어가 있으면
				if (v_select.equals("title"))   bodySql.append(" and lower(a.title)    like lower (").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(")\n");
				if (v_select.equals("content")) bodySql.append(" and lower(a.content)  like lower (").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(")\n");
				if (v_select.equals("name"))    bodySql.append(" and lower(c.kor_name) like lower (").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(")\n");
			}
			orderSql += "\n  order by register_dte desc";

			sql= headSql.toString()+ bodySql.toString()+ orderSql;

			ls = connMgr.executeQuery(sql);

			countSql= "select count(*) "+ bodySql.toString() ;

			int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);

			ls.setPageSize(row);                         // 페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);// 현재페이지번호를 세팅한다.
			int total_page_count = ls.getTotalPage();    // 전체 페이지 수를 반환한다
			//int total_row_count = ls.getTotalCount();    // 전체 row 수를 반환한다

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(total_page_count));
				dbox.put("d_rowcount" , new Integer(row));
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
	 * 메인에서사용하는 리스트
	 * @param box          receive from the form object and session
	 * @return ArrayList   리스트
	 * @throws Exception
	 */
	public ArrayList selectRoomIndexListBrd(RequestBox box,String v_brd_fg) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		StringBuffer 		sql     = new StringBuffer();
		DataBox             dbox    = null;

		String v_cmuno         = box.getString("p_cmuno");


		try {
			connMgr = new DBConnectionManager();

			sql.append("   select/* CommunityFrBoardBean.java : selectRoomIndexListBrd */ *\n");
			sql.append("   from   (\n");
			sql.append("   select a.cmuno           cmuno                  ,a.menuno       menuno\n ");
			sql.append("          ,a.brdno           brdno                  ,a.title        title\n ");
			sql.append("          ,a.content         content                ,a.read_cnt     read_cnt\n ");
			sql.append("          ,a.add_cnt         add_cnt                ,a.parent       parent\n ");
			sql.append("          ,a.lv              lv                     ,a.position     position\n ");
			sql.append("          ,a.display_fg      display_fg             ,a.root         root\n ");
			sql.append("          ,a.register_userid register_userid        ,a.register_dte register_dte\n ");
			sql.append("          ,a.modifier_userid modifier_userid        ,a.modifier_dte modifier_dte\n ");
			sql.append("          ,b.userid          userid                 ,b.resno        resno\n ");
			sql.append("          ,c.kor_name        name                   ,c.email        email\n ");
			sql.append("          ,b.deptnam         deptnam                ,b.jikupnm      jikupnm\n ");
			sql.append("          ,b.jikwinm         jikwinm                ,c.tel          tel\n ");
			sql.append("          ,c.mobile          mobile                 ,c.office_tel   office_tel\n ");
			sql.append("          ,c.wk_area         wk_area                ,c.grade        grade\n ");
			sql.append("          ,e.kor_nm          grade_nm               ,isnull(d.cnt,0) cnt\n ");
			sql.append("     from tz_cmuboard a,tz_member b,tz_cmuusermst c\n ");
			sql.append("         ,(select cmuno cmuno   ,menuno    menuno\n ");
			sql.append("                 ,brdno brdno   ,count(*) cnt\n ");
			sql.append("            from tz_cmuboardfile\n ");
			sql.append("           where cmuno     = '"+v_cmuno+"'\n ");
			sql.append("           group by cmuno,menuno,brdno) d\n ");
			sql.append("          ,tz_cmugrdcode  e  ,tz_cmumenu f\n ");
			sql.append("    where a.register_userid  = b.userid\n ");
			sql.append("    and b.grcode  = ").append(StringManager.makeSQL(box.getSession("tem_grcode"))).append("\n ");
			sql.append("      and a.cmuno            = c.cmuno\n ");
			sql.append("      and a.register_userid  = c.userid\n ");
			sql.append("      and c.cmuno            = e.cmuno\n ");
			sql.append("      and c.grade            = e.grcode\n ");
			sql.append("      and a.cmuno            =  d.cmuno  (+)\n ");
			sql.append("      and a.menuno           =  d.menuno (+)\n ");
			sql.append("      and a.brdno            =  d.brdno (+)\n ");
			sql.append("      and a.cmuno            = '"+v_cmuno+"'\n ");
			sql.append("      and a.cmuno            = f.cmuno\n ");
			sql.append("      and a.menuno            = f.menuno\n ");
			sql.append("      and a.lv               =1\n ");
			if("0".equals(v_brd_fg)){//공지사항
				sql.append("    and f.brd_fg           = '0'\n ");
				sql.append("   and a.del_fg           = 'N'\n ");

			} else {
				sql.append("    and f.brd_fg           in ('1','2')\n ");
				sql.append("   and a.del_fg           = 'N'\n ");

			}
			sql.append("order by a.root desc,a.position asc\n");
			sql.append("     )\n");
			sql.append(" where rownum < 6 ");

			ls = connMgr.executeQuery(sql.toString());

			while (ls.next()) {
				dbox = ls.getDataBox();
				list.add(dbox);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}


	/**
	 * 통합검색 리스트
	 * @param box          receive from the form object and session
	 * @return ArrayList   리스트
	 * @throws Exception
	 */
	public ArrayList selectRoomTotalListBrd(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		DataBox             dbox    = null;

		String sql    	  		= "";
		String countSql  		= "";
		StringBuffer headSql   	= new StringBuffer();
		StringBuffer bodySql   	= new StringBuffer();
		String orderSql  		= "";

		String v_searchtext    = box.getString("p_searchtext");
		String v_select        = box.getString("p_select");
		String v_cmuno		   = box.getString("p_cmuno");
		int    v_pageno        = box.getInt("p_pageno");

		try {
			connMgr = new DBConnectionManager();


			headSql.append(" select  a.cmuno                  ,a.menuno\n ");
			headSql.append("        ,a.brdno                  ,a.title\n ");
			headSql.append("        ,a.content                ,a.read_cnt\n ");
			headSql.append("        ,a.add_cnt                ,a.parent\n ");
			headSql.append("        ,a.lv                     ,a.position\n ");
			headSql.append("        ,a.display_fg             ,a.root\n ");
			headSql.append("        ,a.register_userid        ,a.register_dte\n ");
			headSql.append("        ,a.modifier_userid        ,a.modifier_dte\n ");
			headSql.append("        ,b.userid\n ");
			headSql.append("        ,c.kor_name        name   ,c.email\n ");
			headSql.append("        ,b.deptnam                ,b.jikupnm\n ");
			headSql.append("        ,c.grade\n ");
			headSql.append("        ,e.kor_nm          grade_nm ,isnull(d.cnt,0) cnt\n ");
			headSql.append("        ,g.cmu_nm                      ,g.accept_dte\n ");
			headSql.append("        ,f.title           menu_title, brd_fg\n ");
			bodySql.append("   from tz_cmuboard a,tz_member b,tz_cmuusermst c\n ");
			bodySql.append("       ,(select cmuno cmuno   ,menuno    menuno\n ");
			bodySql.append("               ,brdno brdno   ,count(*) cnt\n ");
			bodySql.append("          from tz_cmuboardfile\n ");
			bodySql.append("         group by cmuno,menuno,brdno) d\n ");
			bodySql.append("        ,tz_cmugrdcode  e  ,tz_cmumenu f,tz_cmubasemst g\n ");
			bodySql.append("  where a.register_userid  = b.userid\n ");
			bodySql.append("    and a.cmuno            = c.cmuno\n ");
			bodySql.append("    and b.grcode  = ").append(StringManager.makeSQL(box.getSession("tem_grcode"))).append("\n ");
			bodySql.append("    and a.register_userid  = c.userid\n ");
			bodySql.append("    and c.cmuno            = e.cmuno\n ");
			bodySql.append("    and a.cmuno            = g.cmuno\n ");
			bodySql.append("    and c.grade            = e.grcode\n ");
			bodySql.append("    and a.cmuno            =  d.cmuno  (+)\n ");
			bodySql.append("    and a.menuno           =  d.menuno (+)\n ");
			bodySql.append("    and a.brdno            =  d.brdno (+)\n ");
			bodySql.append("    and a.cmuno            = f.cmuno\n ");
			bodySql.append("    and a.menuno           = f.menuno\n ");
			bodySql.append("    and a.lv               = 1\n ");
			bodySql.append("    and f.brd_fg in('1','2')\n ");
			bodySql.append("    and g.close_fg         = '1'\n ");
			bodySql.append("    and a.del_fg           = 'N'\n ");
			bodySql.append("    and a.display_fg       = 'Y'\n ");
			bodySql.append("    and a.cmuno            = '").append(v_cmuno).append("'\n");


			if ( !v_searchtext.equals("")) {      // 검색어가 있으면
				if (v_select.equals("title"))   bodySql.append("    and lower(a.title)    like lower (").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(")\n ");
				if (v_select.equals("content")) bodySql.append("    and lower(a.content)  like lower (").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(")\n ");
				if (v_select.equals("name"))    bodySql.append("    and lower(c.kor_name) like lower (").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(")\n ");
				if (v_select.equals("total")) {
					bodySql.append("    and ( lower(a.title)    like lower (").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(")\n ");
					bodySql.append("        or lower(a.content)  like lower (").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(")\n ");
					bodySql.append("        or lower(c.kor_name) like lower (").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(") )\n ");
				}
			}
			orderSql = "order by a.register_dte desc ";

			sql= headSql.toString()+ bodySql.toString()+ orderSql;

			ls = connMgr.executeQuery(sql);

			countSql= "select count(*) "+ bodySql.toString();
			int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);

			ls.setPageSize(row);                         // 페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);// 현재페이지번호를 세팅한다.
			int total_page_count = ls.getTotalPage();    // 전체 페이지 수를 반환한다
			//int total_row_count = ls.getTotalCount();  // 전체 row 수를 반환한다

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(total_page_count));
				dbox.put("d_rowcount" , new Integer(row));
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
	 * 앨범리스트
	 * @param box          receive from the form object and session
	 * @return ArrayList   앨범리스트
	 * @throws Exception
	 */
	public ArrayList selectAlbumListBrd(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();

		//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
		String sql    	   		= "";
		String countSql  		= "";
		StringBuffer headSql  	= new StringBuffer();
		StringBuffer bodySql   	= new StringBuffer();
		String orderSql  = "";
		DataBox             dbox    = null;

		String v_cmuno         = box.getString("p_cmuno");
		String v_menuno        = box.getString("p_menuno");

		String v_searchtext = box.getString("p_searchtext");
		String v_select     = box.getString("p_select");
		int v_pageno        = box.getInt("p_pageno");

		try {
			connMgr = new DBConnectionManager();

			headSql.append(" select /*CommunityFrBoardBean.selectAlbumListBrd : 403 */\n		a.cmuno           cmuno                  ,a.menuno       menuno\n ");
			headSql.append("        ,a.brdno           brdno                  ,a.title        title\n ");
			headSql.append("        ,a.content         content                ,a.read_cnt     read_cnt\n ");
			headSql.append("        ,a.add_cnt         add_cnt                ,a.parent       parent\n ");
			headSql.append("        ,a.lv              lv                     ,a.position     position\n ");
			headSql.append("        ,a.display_fg      display_fg             ,a.root         root\n ");
			headSql.append("        ,a.register_userid register_userid        ,a.register_dte register_dte\n ");
			headSql.append("        ,a.modifier_userid modifier_userid        ,a.modifier_dte modifier_dte\n ");
			headSql.append("        ,b.userid          userid                 ,b.resno        resno\n ");
			headSql.append("        ,c.kor_name        name                   ,c.email        email\n ");
			headSql.append("        ,b.deptnam         deptnam                ,b.jikupnm      jikupnm\n ");
			headSql.append("        ,b.jikwinm         jikwinm                ,c.tel          tel\n ");
			headSql.append("        ,c.mobile          mobile                 ,c.office_tel   office_tel\n ");
			headSql.append("        ,c.wk_area         wk_area                ,c.grade        grade\n ");
			headSql.append("        ,e.kor_nm          grade_nm               ,isnull(d.cnt,0) cnt\n ");
			headSql.append("        ,isnull(f.savefile,'/images/community/photo_smallback.gif')  savefile\n ");
			bodySql.append("   from tz_cmuboard a,tz_member b,tz_cmuusermst c\n ");
			bodySql.append("       ,(select cmuno cmuno   ,menuno    menuno\n ");
			bodySql.append("               ,brdno brdno   , count(*) cnt\n ");
			bodySql.append("          from tz_cmuboardfile\n ");
			bodySql.append("    where cmuno            = '").append(v_cmuno ).append("'\n ");
			bodySql.append("    and menuno           = '").append(v_menuno).append("'\n ");
			bodySql.append("         group by cmuno,menuno,brdno) d\n ");
			bodySql.append("        ,tz_cmugrdcode  e\n ");
			bodySql.append("        ,tz_cmuboardfile f\n ");
			bodySql.append("  where a.register_userid  = b.userid\n ");
			bodySql.append("    and a.cmuno            = c.cmuno\n ");
			bodySql.append("    and b.grcode  = ").append(StringManager.makeSQL(box.getSession("tem_grcode"))).append("\n ");
			bodySql.append("    and a.register_userid  = c.userid\n ");
			bodySql.append("    and c.cmuno            = e.cmuno\n ");
			bodySql.append("    and c.grade            = e.grcode\n ");
			bodySql.append("    and a.cmuno             =  d.cmuno (+)\n ");
			bodySql.append("    and a.menuno            =  d.menuno(+)\n ");
			bodySql.append("    and a.brdno             =  d.brdno(+)\n ");
			bodySql.append("    and a.cmuno             =  f.cmuno (+)\n ");
			bodySql.append("    and a.menuno            =  f.menuno(+)\n ");
			bodySql.append("    and a.brdno             =  f.brdno(+)\n ");
			bodySql.append("    and a.cmuno            = '").append(v_cmuno ).append("'\n");
			bodySql.append("    and a.menuno           = '").append(v_menuno).append("'\n");
			bodySql.append("    and a.del_fg           = 'N'\n ");

			if ( !v_searchtext.equals("")) {      // 검색어가 있으면
				if (v_select.equals("title"))   bodySql.append(" and lower(a.title)    like lower (").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(")\n");
				if (v_select.equals("content")) bodySql.append(" and lower(a.content)  like lower (").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(")\n");
				if (v_select.equals("name"))    bodySql.append(" and lower(c.kor_name) like lower (").append(StringManager.makeSQL("%" + v_searchtext + "%")).append(")\n");
			}
			orderSql += " order by a.root desc,a.position asc";

			sql= headSql.toString()+ bodySql.toString()+ orderSql;

			ls = connMgr.executeQuery(sql);

			countSql= "select count(*) "+ bodySql.toString() ;

			int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);

			ls.setPageSize(8);                         // 페이지당 row 갯수를 세팅한다
			ls.setCurrentPage(v_pageno, total_row_count);// 현재페이지번호를 세팅한다.
			int total_page_count = ls.getTotalPage();    // 전체 페이지 수를 반환한다
			//int total_row_count = ls.getTotalCount();    // 전체 row 수를 반환한다

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(total_page_count));
				dbox.put("d_rowcount" , new Integer(8));
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
	 * 게시판 조회
	 * @param box          receive from the form object and session
	 * @return ArrayList   게시판 조회
	 * @throws Exception
	 */
	public DataBox selectViewBrd(RequestBox box,String qryFlag) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet             ls      = null;

		StringBuffer        sql     = new StringBuffer();
		DataBox             dbox    = null;

		String v_cmuno              = box.getString("p_cmuno");
		String v_menuno             = box.getString("p_menuno");
		String v_brdno              = box.getString("p_brdno");

		Vector realfileVector = new Vector();
		Vector savefileVector = new Vector();
		Vector fileseqVector  = new Vector();

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			//조회수 증가.
			if("VIEW".equals(qryFlag)){
				sql.append(" update tz_cmuboard set read_cnt = read_cnt + 1\n ");
				sql.append("  where cmuno           = '").append(v_cmuno ).append("'\n");
				sql.append("    and menuno          =  ").append(v_menuno).append("\n");
				sql.append("    and brdno           =  ").append(v_brdno ).append("\n");
				pstmt = connMgr.prepareStatement(sql.toString());
				pstmt.executeUpdate();
			}
			sql.setLength(0);

			sql.append("\n select /*CommunityFrBoardBean.selectViewBrd : 519*/\n		a.cmuno           cmuno                  ,a.menuno       menuno ");
			sql.append("\n        ,a.brdno           brdno                  ,a.title        title ");
			sql.append("\n        ,a.content         content                ,a.read_cnt     read_cnt");
			sql.append("\n        ,a.add_cnt         add_cnt                ,a.parent       parent ");
			sql.append("\n        ,a.lv              lv                     ,a.position     position ");
			sql.append("\n        ,a.display_fg      display_fg             ,a.root         root");
			sql.append("\n        ,a.register_userid register_userid        ,a.register_dte register_dte");
			sql.append("\n        ,a.modifier_userid modifier_userid        ,a.modifier_dte modifier_dte");
			sql.append("\n        ,b.userid          userid                 ,b.resno        resno ");
			sql.append("\n        ,c.kor_name        name                   ,c.email        email ");
			sql.append("\n        ,b.deptnam         deptnam                ,b.jikupnm      jikupnm ");
			sql.append("\n        ,b.jikwinm         jikwinm                ,c.tel          tel");
			sql.append("\n        ,c.mobile          mobile                 ,c.office_tel   office_tel ");
			sql.append("\n        ,c.wk_area         wk_area                ,c.grade        grade ");
			sql.append("\n        ,e.kor_nm          grade_nm               ");
			sql.append("\n        , d.SAVEFILE, d.REALFILE, d.FILENO ");
			sql.append("\n   from tz_cmuboard a, tz_member b, tz_cmuusermst c ");
			sql.append("\n       , tz_cmuboardfile  d, tz_cmugrdcode  e  ");
			sql.append("\n  where a.register_userid  = b.userid ");
			sql.append("\n    and b.grcode  = ").append(StringManager.makeSQL(box.getSession("tem_grcode")));
			sql.append("\n    and a.cmuno            = c.cmuno ");
			sql.append("\n    and a.register_userid  = c.userid ");
			sql.append("\n    and c.cmuno            = e.cmuno  ");
			sql.append("\n    and c.grade            = e.grcode ");
			sql.append("\n    and a.cmuno            =  d.cmuno (+) ");
			sql.append("\n    and a.menuno           =  d.menuno(+) ");
			sql.append("\n    and a.brdno            =  d.brdno(+) ");
			sql.append("\n    and a.del_fg           = 'N'");
			sql.append("\n    and a.cmuno            = '").append(v_cmuno).append("'");
			sql.append("\n    and a.menuno           = '").append(v_menuno).append("'");
			sql.append("\n    and a.brdno           = '").append(v_brdno).append("'");

			ls = connMgr.executeQuery(sql.toString());
			while (ls.next()) {
				dbox = ls.getDataBox();
				if(!dbox.getString("d_realfile").equals("")) {
					realfileVector.addElement(dbox.getString("d_realfile"));
					savefileVector.addElement(dbox.getString("d_savefile"));
					fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileno")));
				}
			}

			if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
			if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
			if (fileseqVector 	!= null) dbox.put("d_fileno", fileseqVector);
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			//            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return dbox;
	}

	/**
	 * 게시판등록하기
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int insertBrd(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt   = null;
		ListSet             ls      = null;
		String              sql     = "";

		int         isOk1       = 1;
		int         isOk2       = 1;
		int         isOk3		= 1;
		int         v_brdno     = 0;

		String v_cmuno              = box.getString("p_cmuno");
		String v_brd_fg             = box.getString("p_brd_fg");
		String v_menuno             = box.getString("p_menuno");

		String v_content =  StringUtil.removeTag(box.getString("p_content"));

		String s_userid    = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			// 05.12.06 이나연 추가
			if("0".equals(v_brd_fg)) 	v_menuno = "1"; // 공지사항 menuno
			sql = "select isnull(max(brdno), 0) from tz_cmuboard where  cmuno = '"+v_cmuno+"' and menuno = '"+v_menuno+"'";
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				v_brdno = ls.getInt(1) + 1;
			}

			int tmp =0;
			sql = "select isnull(max(position), 0) from tz_cmuboard where cmuno = '"+v_cmuno+"' and menuno = '"+v_menuno+"'";
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				tmp = ls.getInt(1) + 1;
			}

			sql  =" insert into tz_cmuboard ( cmuno            , menuno           , brdno      "
				+"                         , title            , content          , read_cnt"
				+"                         , add_cnt          , lv               , position  "
				+"                         , display_fg       , register_userid  , register_dte "
				+"                         , modifier_userid  , modifier_dte     , del_fg"
				+"                         , parent           , root             )"
				+"                  values ( ?                , ?                , ?"
				+"                         , ?                , ?     , 0"
				+"                         , 0                , ?                , ?"
				+"                         , ?                , ?                , to_char(sysdate, 'YYYYMMDDHH24MISS')"
				+"                         , ?                ,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N'"
				+"                         , ?                , ?                )"
				;
			int index = 1;

			pstmt = connMgr.prepareStatement(sql);
			pstmt.setString(index++, v_cmuno                            );
			pstmt.setString(index++, v_menuno                           );
			pstmt.setInt   (index++, v_brdno                            );
			pstmt.setString(index++, StringUtil.removeTag(box.getString("p_title" ))          );//제목
			pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			pstmt.setInt   (index++, 1                                  );//답변레벨
			pstmt.setInt   (index++, tmp                                );//답변위치
			pstmt.setString(index++, box.getStringDefault("p_display_fg","Y" )     );//공개구분
			pstmt.setString(index++, s_userid);//게시자
			pstmt.setString(index++, s_userid );//수정자
			pstmt.setInt   (index++, v_brdno                             );//부모
			pstmt.setInt   (index++, v_brdno                             );//ROOT
			isOk1 = pstmt.executeUpdate();

			isOk3 = UploadUtil.fnRegisterAttachFile(box);
			isOk2 = this.insertUpFile(connMgr,box,String.valueOf(v_brdno));

			if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}else connMgr.rollback();

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk1*isOk2;
	}


	/**
	 * 답변등록하기
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int replyBrd(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet     ls          = null;
		String      sql         = "";

		int         isOk1       = 1;
		int         isOk2       = 1;
		int         isOk3       = 1;
		int         v_thisbrdno =0;


		String v_cmuno              = box.getString("p_cmuno");
		String v_menuno             = box.getString("p_menuno");
		String v_brdno              = box.getString("p_brdno");

		int    v_lv                 = box.getInt("p_lv");
		int    v_position           = box.getInt("p_position");
		int    v_root               = box.getInt("p_root");
		String v_content            = StringUtil.removeTag(box.getString("p_content"));
		String s_userid             = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql  =" update tz_cmuboard set    position = position+1"
				+ "  where cmuno        = ?"
				+"     and menuno       = ?"
				+"     and position     > ?"
				;
			pstmt = connMgr.prepareStatement(sql);

			pstmt.setString(1, v_cmuno                         );//커뮤니티번호
			pstmt.setString(2, v_menuno                        );//메뉴번호
			pstmt.setInt   (3, v_position                      );//답변위치
			pstmt.executeUpdate();

			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			sql = "select isnull(max(brdno), 0) from tz_cmuboard where  cmuno = '"+v_cmuno+"' and menuno = '"+v_menuno+"'";
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				v_thisbrdno = ls.getInt(1) + 1;
			}
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }

			sql  =" insert into tz_cmuboard ( cmuno            , menuno           , brdno      "
				+"                         , title            , content          , read_cnt"
				+"                         , add_cnt          , lv               , position  "
				+"                         , display_fg       , register_userid  , register_dte "
				+"                         , modifier_userid  , modifier_dte     , del_fg"
				+"                         , parent           , root             )"
				+"                  values ( ?                , ?                , ?"
				//                +"                         , ?                , empty_clob()     , 0"
				+"                         , ?                , ?     , 0"
				+"                         , 0                , ?                , ?"
				+"                         , ?                , ?                , to_char(sysdate, 'YYYYMMDDHH24MISS')"
				+"                         , ?                ,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N'"
				+"                         , ?                , ?                )"
				;
			int index = 1;

			pstmt = connMgr.prepareStatement(sql);
			pstmt.setString(index++, v_cmuno                            );
			pstmt.setString(index++, v_menuno                           );
			pstmt.setInt   (index++, v_thisbrdno                        );
			pstmt.setString(index++, box.getString("p_title" )          );//제목
			pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			pstmt.setInt   (index++, v_lv+1                             );//답변레벨
			pstmt.setInt   (index++, v_position+1                       );//답변위치
			pstmt.setString(index++, box.getStringDefault("p_display_fg","N" )     );//공개구분
			pstmt.setString(index++, s_userid);//게시자
			pstmt.setString(index++, s_userid );//수정자
			pstmt.setString(index++, v_brdno                             );//부모
			pstmt.setInt   (index++, v_root                             );//ROOT
			isOk1 = pstmt.executeUpdate();

			//           String sql1 = "select content from tz_cmuboard where cmuno = '"+v_cmuno+"' and menuno = '"+v_menuno+"' and brdno ="+v_brdno;
			//           connMgr.setOracleCLOB(sql1, v_content);

			isOk3 = this.insertUpFile(connMgr,box,String.valueOf(v_thisbrdno));

			if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk1*isOk2*isOk3;
	}

	/**
	 * 게시판 수정하기
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int updateBrd(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet     ls          = null;
		String      sql         = "";

		int isOk1       = 1;
		int isOk2       = 1;
		int isOk3       = 1;

		String v_cmuno              = box.getString("p_cmuno");
		String v_menuno             = box.getString("p_menuno");
		String v_brdno              = box.getString("p_brdno");
		// 05.12.05 이나연 _ 파일첨부 추가

		String v_content            = StringUtil.removeTag(box.getString("p_content"));

		String s_userid             = box.getSession("userid");

		Vector v_del_savefile = box.getVector("p_del_savefile");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);


			sql  =" update tz_cmuboard set  title           = ?"
				+"                       , content         = ?"
				+"                       , display_fg      = ?"
				+"                       , modifier_userid = ?"
				+"                       , modifier_dte =to_char(sysdate, 'YYYYMMDDHH24MISS')"
				+"  where cmuno           = ?"
				+"    and menuno          = ?"
				+"    and brdno           = ?"
				;
			pstmt = connMgr.prepareStatement(sql);
			int index = 1;

			pstmt.setString(index++, StringUtil.removeTag(box.getString("p_title"))          );//제목
			pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			pstmt.setString(index++, box.getStringDefault("p_display_fg", "Y" )     );//공개구분
			pstmt.setString(index++, s_userid);//게시자
			pstmt.setString(index++ , v_cmuno                            );
			pstmt.setString(index++ , v_menuno                           );
			pstmt.setString(index++ , v_brdno                        );

			isOk1 = pstmt.executeUpdate();

			UploadUtil.fnRegisterAttachFile(box);
			isOk3 =	this.deleteUpFile(connMgr, box);		//	   삭제할 파일이 있다면	파일table에서 삭제
			isOk2 = this.insertUpFile(connMgr,box,v_brdno);

			if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
				connMgr.commit();
				if (v_del_savefile != null)	{
					FileManager.deleteFile(v_del_savefile);			//	 DB	에서 모든처리가	완료되면 해당 첨부파일 삭제
				}
				isOk1 = 1;
			} else {
				connMgr.rollback();
				isOk1 = 0;
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk1;
	}

	/**
	 * QNA 새로운 자료파일 등록
	 * @param box      receive from the form object and session
	 * @return isOk    1:delete success,0:delete fail
	 * @throws Exception
	 */

	public int insertUpFile(DBConnectionManager connMgr, RequestBox    box,String v_brdno) throws Exception {
		ListSet           ls      = null;
		PreparedStatement pstmt   = null;
		String            sql     = "";
		String            sql2    = "";
		int               isOk2   = 1;
		int               vfileno = 0;

		String v_cmuno              = box.getString("p_cmuno");
		String v_menuno             = box.getString("p_menuno");

		String s_userid             = box.getSession("userid");

		CommunityMsMenuBean bean = new CommunityMsMenuBean();
		int    v_filecnt            = v_menuno.equals("2") ? 1 : Integer.parseInt(bean.getSingleColumn(box,v_cmuno,v_menuno,"filecnt"));

		String [] v_realFile     = new String [v_filecnt];
		String [] v_saveFile     = new String [v_filecnt];

		for(int i = 0; i < v_filecnt; i++) {
			v_realFile[i] = box.getRealFileName("p_file"+ (i+1));
			v_saveFile[i] = box.getNewFileName ("p_file" + (i+1));
		}

		ArrayList arySaveFileName  = (ArrayList)box.getObject("arySaveFileName");
		ArrayList aryRealFileName = (ArrayList)box.getObject("aryRealFileName");

		int aryLen = arySaveFileName.size();

		try {
			sql2 = "select isnull(max(fileno), 0) "
				+"  from tz_cmuboardfile "
				+" where cmuno   = '" +v_cmuno+ "'"
				+"   and menuno  =  " +v_menuno
				+"   and brdno   =  " +v_brdno;
			ls = connMgr.executeQuery(sql2);
			while (ls.next()) {
				vfileno = ls.getInt(1);
			}
			ls.close();

			sql  =" insert into tz_cmuboardfile ( cmuno, menuno,brdno, fileno, realfile, savepath"
				+"                       , savefile, filesize, register_userid, register_dte, modifier_userid, modifier_dte)"
				+"               values  (?,?,?,?,?,''"
				+"                       ,?,null,?"
				+"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),?"
				+"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'))"
				;
			pstmt = connMgr.prepareStatement(sql);
			for(int i = 0; i < aryLen ; i++) {
				if( !aryRealFileName.get(i).equals("")) {
					vfileno++;
					pstmt.setString(1 , v_cmuno                            );
					pstmt.setString(2 , v_menuno                           );
					pstmt.setString(3 , v_brdno                        );
					pstmt.setInt   (4, vfileno        );//파일일련번호
					pstmt.setString(5, (String)aryRealFileName.get(i)  );//원본파일명
					pstmt.setString(6, (String)arySaveFileName.get(i));//실제파일명
					pstmt.setString(7, s_userid       );//게시자
					pstmt.setString(8, s_userid       );//수정자
					isOk2 = pstmt.executeUpdate();
				}
			}
		}
		catch (Exception ex) {
			FileManager.deleteFile(v_saveFile, FILE_LIMIT);     //  일반파일, 첨부파일 있으면 삭제..
			ErrorManager.getErrorStackTrace(ex, box, sql2);
			throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
		}
		return isOk2;
	}

	/**
	 * 선택된 자료파일 DB에서 삭제
	 * @param connMgr           DB Connection Manager
	 * @param box               receive from the form object and session
	 * @param p_filesequence    선택 파일 갯수
	 * @return
	 * @throws Exception
	 */
	public int deleteUpFile(DBConnectionManager	connMgr, RequestBox box)    throws Exception {
		PreparedStatement   pstmt = null;
		ListSet           ls      = null;
		String            sql     = "";
		int               isOk   = 1;


		String v_cmuno              = box.getString("p_cmuno");
		String v_menuno             = box.getString("p_menuno");
		String v_brdno              = box.getString("p_brdno");

		Vector v_del_fileseq  = box.getVector("p_del_fileseq");
		try {
			sql  =" delete from  tz_cmuboardfile "
				+"  where cmuno   = '" +v_cmuno+ "'"
				+"    and menuno  =  " +v_menuno
				+"    and brdno   =  " +v_brdno
				+"    and fileno  =  ?"
				;

			pstmt = connMgr.prepareStatement(sql);
			for(int	i =	0; i < v_del_fileseq.size(); i++) {
				int	v_fileseq =	Integer.parseInt((String)v_del_fileseq.elementAt(i));
				pstmt.setInt(1, v_fileseq);
				isOk =	pstmt.executeUpdate();
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql  + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
		}
		return isOk;
	}


	/**
	 * 댓글등록하기
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int insertBrdMemo(RequestBox box) throws Exception {
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

			sql.append(" SELECT  ISNULL(MAX(RPLNO), 0)\n ");
			sql.append(" FROM    TZ_CMUBOARDREPLAY\n ");
			sql.append(" WHERE   CMUNO   = '").append(v_cmuno ).append("'\n ");
			sql.append(" AND     MENUNO  = '").append(v_menuno).append("'\n ");
			sql.append(" AND     BRDNO   =  ").append(v_brdno );

			ls = connMgr.executeQuery(sql.toString());
			while (ls.next()) v_rplno = ls.getInt(1) + 1;

			sql.setLength(0);

			sql.append(" INSERT INTO TZ_CMUBOARDREPLAY\n ");
			sql.append(" (\n ");
			sql.append("     CMUNO, MENUNO, BRDNO, RPLNO, CONTENT, USERID\n ");
			sql.append("     , REGISTER_DTE , MODIFIER_DTE, DEL_FG\n ");
			sql.append(" )\n ");
			sql.append(" VALUES\n ");
			sql.append(" (\n ");
			sql.append("     ?, ?, ?, ?, ?, ?\n ");
			sql.append("     ,TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')\n ");
			sql.append("     ,TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'),'N'\n ");
			sql.append(" )\n ");

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
	public int deleteBrdMemo(RequestBox box) throws Exception {
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

			sql.append(" DELETE FROM    TZ_CMUBOARDREPLAY\n ");
			sql.append(" WHERE   CMUNO   = '").append(v_cmuno ).append("'\n ");
			sql.append(" AND     MENUNO  = '").append(v_menuno).append("'\n ");
			sql.append(" AND     BRDNO   =  ").append(v_brdno ).append("'\n ");
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


	/**
	 * 글삭제하기
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int deleteBrdData(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt1 = null;
		PreparedStatement   pstmt2 = null;
		PreparedStatement   pstmt3 = null;
		ListSet     ls          = null;
		String      sql         = "";

		int         isOk1       = 1;
		int         isOk2       = 1;
		int         isOk3       = 1;
		int			temp		= 0;

		String v_cmuno              = box.getString("p_cmuno");
		String v_menuno             = box.getString("p_menuno");
		String v_brdno              = box.getString("p_brdno");
		Vector savefile             = box.getVector("p_savefile");


		System.out.println("v_cmuno :  : "+v_cmuno);System.out.println("v_menuno :  : "+v_menuno);System.out.println("v_brdno :  : "+v_brdno);

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql = "select count(*) from tz_cmuboardreplay where cmuno = '"+v_cmuno+"' and menuno = '"+v_menuno+"' and brdno = '"+v_brdno+"'";
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				temp = ls.getInt(1);
			}

			if(temp > 0 ){
				//댓글삭제
				sql = "delete from tz_cmuboardreplay where cmuno = ? and menuno = ? and brdno =?";

				pstmt1 = connMgr.prepareStatement(sql);
				pstmt1.setString(1, v_cmuno);
				pstmt1.setString(2, v_menuno);
				pstmt1.setString(3, v_brdno);
				isOk1 = pstmt1.executeUpdate();
			}

			sql = "select count(*) from tz_cmuboardfile where cmuno = '"+v_cmuno+"' and menuno = '"+v_menuno+"' and brdno = '"+v_brdno+"'";
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				temp = ls.getInt(1);
			}

			if(temp > 0 ){
				//파일삭제삭제
				sql = "delete from tz_cmuboardfile where cmuno = ? and menuno = ? and brdno =?";
				pstmt2 = connMgr.prepareStatement(sql);
				pstmt2.setString(1, v_cmuno);
				pstmt2.setString(2, v_menuno);
				pstmt2.setString(3, v_brdno);
				isOk2 = pstmt2.executeUpdate();
			}

			//본문삭제
			sql = "delete from tz_cmuboard where cmuno = ? and menuno = ? and brdno =?";
			pstmt3 = connMgr.prepareStatement(sql);
			pstmt3.setString(1, v_cmuno);
			pstmt3.setString(2, v_menuno);
			pstmt3.setString(3, v_brdno);
			isOk3 = pstmt3.executeUpdate();


			System.out.println("isOk[0]:"+isOk1);
			System.out.println("isOk[1]:"+isOk2);
			System.out.println("isOk[2]:"+isOk3);

			if(isOk1 > 0&& isOk2 > 0&& isOk3 > 0) {
				if (savefile != null) {
					FileManager.deleteFile(savefile);         //     첨부파일 삭제
				}
				connMgr.commit();
				isOk1 = 1;
			}
			else {
				connMgr.rollback();
				isOk1 = 0;
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql ->"+FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk1;
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

			str += "</tr>";
			str += "</table>";
		}
		return str;
	}
}

