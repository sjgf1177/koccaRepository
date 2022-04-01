//**********************************************************
//1. ��      ��:
//2. ���α׷���: CommunityMsMangeBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��: 05.12.08 �̳���
//
//**********************************************************

package com.credu.community;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("unchecked")
public class CommunityMsMangeBean {
	private ConfigSet config;
	private static int row=10;

	public CommunityMsMangeBean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //�� ����� �������� row ���� �����Ѵ�
			row = 10; //������ ����
			System.out.println("....... row.....:"+row);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Ŀ�´�Ƽ ȸ������ȭ��
	 * @param box          receive from the form object and session
	 * @return ArrayList   Ŀ�´�Ƽ ȸ�����ø���Ʈ
	 * @throws Exception
	 */
	public ArrayList selectMemberList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		//		2005.11.15_�ϰ��� : TotalCount ���� ���� ����
		String sql    	  = "";
		String count_sql  = "";
		String head_sql   = "";
		String body_sql   = "";
		String order_sql  = "";
		DataBox             dbox    = null;

		String v_static_cmuno  = box.getString("p_cmuno");
		String v_searchtext    = box.getString("p_searchtext");
		String v_select        = box.getString("p_select");
		int v_pageno           = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");

		String v_process = box.getString("p_process");

		if(v_process.equals("gomsmemberPop")) v_pageno = 5;

		try {
			connMgr = new DBConnectionManager();

			//��������
			// 2005.11.16_�ϰ���  rownum ����
			head_sql  = " select     a.cmuno           cmuno         , a.userid          userid        ";
			head_sql  += "\n 	, a.kor_name        kor_name      , a.eng_name        eng_name      ";
			head_sql  += "\n   , a.email           email         , a.tel             tel           ";
			head_sql  += "\n   , a.mobile          mobile        , a.office_tel      office_tel    ";
			head_sql  += "\n   , a.duty            duty          , a.wk_area         wk_area       ";
			head_sql  += "\n   , a.grade           grade         , a.request_dte     request_dte   ";
			head_sql  += "\n   , a.license_dte     license_dte   , a.license_userid  license_userid";
			//2005.11.21_�ϰ��� : Underlying input stream returned zero bytes(Text ���� null or 0 byte �� ��쿡�����Ƿ� ����.
			//head_sql  += "\n   , a.close_fg        close_fg      , a.close_reason    close_reason  ";
			//head_sql  += "\n   , a.close_dte       close_dte     , a.intro           intro         ";
			head_sql += "\n 	, a.close_fg close_fg  , a.close_reason as close_reason  ";
			head_sql += "\n     , a.close_dte close_dte, a.intro as intro ";

			head_sql  += "\n   , a.recent_dte      recent_dte    , a.visit_num       visit_num     ";
			head_sql  += "\n   , a.search_num      search_num    , a.register_num    register_num  ";
			head_sql  += "\n   , a.modifier_dte    modifier_dte  , c.kor_nm          grade_kor_nm";
			head_sql  += "\n   ,b.deptnam deptnam,b.jikupnm jikupnm,b.jikwinm jikwinm";
			body_sql += "\n from tz_cmuusermst a,tz_member b,tz_cmugrdcode c ";
			body_sql += "\n  where a.userid = b.userid ";
			body_sql += "\n 	 and a.cmuno  = c.cmuno";
			body_sql += "\n 	 and b.grcode  = " + StringManager.makeSQL(box.getSession("tem_grcode"));
			body_sql += "\n    and a.grade  = c.grcode";
			body_sql += "\n 	 and a.cmuno  = '"+v_static_cmuno+"'" ;

			if ( !v_searchtext.equals("")) {      // �˻�� ������
				if (v_select.equals("userid"))   body_sql += "\n and lower(a.userid) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%")+")";
				if (v_select.equals("kor_name")) body_sql += "\n and a.kor_name like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i ��
			}

			//            order_sql += "\n order by a.kor_name asc";
			order_sql += "\n order by C.kor_nm asc";

			sql= head_sql+ body_sql+ order_sql;

			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

			ls.setPageSize(10);                         // �������� row ������ �����Ѵ�
			ls.setCurrentPage(v_pageno,total_row_count); // ������������ȣ�� �����Ѵ�.

			System.out.println("ls.getTotalPage() "+ls.getTotalPage());

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
				dbox.put("d_rowcount" , new Integer(v_pageno));
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
	 * Ŀ�´�Ƽ ����Ÿ���� ȸ������ȭ��
	 * @param box          receive from the form object and session
	 * @return ArrayList   Ŀ�´�Ƽ ����Ÿ����ȸ�����ø���Ʈ
	 * @throws Exception
	 */
	public ArrayList selectMemberNoneList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		//		2005.11.15_�ϰ��� : TotalCount ���� ���� ����
		String sql    	  = "";
		String count_sql  = "";
		String head_sql   = "";
		String body_sql   = "";
		String order_sql  = "";

		DataBox             dbox    = null;

		String v_static_cmuno  = box.getString("p_cmuno");
		String v_searchtext    = box.getString("p_searchtext");
		String v_select        = box.getString("p_select");
		int v_pageno        = box.getInt("p_pageno");
		try {
			connMgr = new DBConnectionManager();

			//��������
			// 2005.11.16_�ϰ��� : Oracle -> mssql
			head_sql  += " select     a.cmuno           cmuno         , a.userid          userid        ";
			head_sql  += "\n      , a.kor_name        kor_name      , a.eng_name        eng_name      ";
			head_sql  += "\n      , a.email           email         , a.tel             tel           ";
			head_sql  += "\n      , a.mobile          mobile        , a.office_tel      office_tel    ";
			head_sql  += "\n       , a.duty            duty          , a.wk_area         wk_area       ";
			head_sql  += "\n       , a.grade           grade         , a.request_dte     request_dte   ";
			head_sql  += "\n       , a.license_dte     license_dte   , a.license_userid  license_userid";
			head_sql  += "\n      , a.close_fg        close_fg      , a.close_reason    close_reason  ";
			head_sql  += "\n       , a.close_dte       close_dte     , a.intro           intro         ";
			head_sql  += "\n      , a.recent_dte      recent_dte    , a.visit_num       visit_num     ";
			head_sql  += "\n      , a.search_num      search_num    , a.register_num    register_num  ";
			head_sql  += "\n      , a.modifier_dte    modifier_dte  , c.kor_nm          grade_kor_nm";
			head_sql  += "\n       ,b.deptnam deptnam,b.jikupnm jikupnm,b.jikwinm jikwinm";
			body_sql  += "\n   from tz_cmuusermst a,tz_member b,tz_cmugrdcode c ";
			body_sql  += "\n  where a.userid = b.userid ";
			body_sql += "\n 	 and b.grcode  = " + StringManager.makeSQL(box.getSession("tem_grcode"));
			body_sql  += "\n   and a.cmuno  = c.cmuno";
			body_sql  += "\n   and a.grade  = c.grcode";
			body_sql  += "\n    and a.cmuno  = '"+v_static_cmuno+"'";
			body_sql  += "\n   and a.grade !='01'";

			if ( !v_searchtext.equals("")) {      // �˻�� ������
				if (v_select.equals("userid"))   body_sql += "\n and lower(a.userid) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%")+")";
				if (v_select.equals("kor_name")) body_sql += "\n and a.kor_name like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i ��
			}

			order_sql += "\n order by a.kor_name asc";

			sql= head_sql + body_sql + order_sql;

			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

			ls.setPageSize(5);                         // �������� row ������ �����Ѵ�
			ls.setCurrentPage(v_pageno, total_row_count);// ������������ȣ�� �����Ѵ�.
			int total_page_count = ls.getTotalPage();    // ��ü ������ ���� ��ȯ�Ѵ�
			//int total_row_count = ls.getTotalCount();    // ��ü row ���� ��ȯ�Ѵ�

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(total_page_count));
				dbox.put("d_rowcount" , new Integer(5));
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
	 * Ŀ�´�Ƽ ȸ���ʴ�ȭ��
	 * @param box          receive from the form object and session
	 * @return ArrayList   Ŀ�´�Ƽ ȸ��������
	 * @throws Exception
	 */
	public ArrayList selectMemberInvitation(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		String              sql     = "";
		DataBox             dbox    = null;

		String v_cmuno         = box.getString("p_cmuno");
		String v_searchtext    = box.getString("p_searchtext");

		String v_display_fg    ="";
		try {
			connMgr = new DBConnectionManager();

			sql  = " select display_fg"
				+ "   from tz_cmubasemst"
				+ "  where cmuno    = '"+v_cmuno+"'";

			ls = connMgr.executeQuery(sql);
			while (ls.next()) v_display_fg=ls.getString(1);


			sql  = "\n select     b.userid  userid  ,b.email   email,b.name name"
				+"\n           ,b.deptnam deptnam ,b.jikupnm jikupnm,b.jikwinm jikwinm"
				+ "\n   from tz_member b,tz_compclass a"
				+ "\n  where b.comp =a.comp(+)"
				+ "\n 	 and b.grcode  = " + StringManager.makeSQL(box.getSession("tem_grcode"))
				+"\n     and b.userid not in (select userid from tz_cmuusermst where cmuno ='"+v_cmuno+"')";
			if("1".equals(v_display_fg)) sql += "\n and b.comp = '0101000000'";
			if("2".equals(v_display_fg)) sql += "\n and (b.comp = '0101000000' or a.gubun='2')";
			if("3".equals(v_display_fg)) sql += "\n and (a.gubun='1' or a.gubun='2')";


			if ( !v_searchtext.equals("")) {      // �˻�� ������
				sql += "\n and (lower(b.userid) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%")+")"
				+ "\n  or b.name like " + StringManager.makeSQL("%" + v_searchtext + "%") +")";
			}

			sql += "\n order by b.name asc";
			System.out.println(sql);
			if(v_searchtext.length()>0){
				ls = connMgr.executeQuery(sql);
				while (ls.next()) {
					dbox = ls.getDataBox();
					list.add(dbox);
				}
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
	 * Ŀ�´�Ƽ ȸ������
	 * @param box          receive from the form object and session
	 * @return ArrayList   Ŀ�´�Ƽ ȸ������
	 * @throws Exception
	 */
	public ArrayList selectMemberSingleData(RequestBox box,String v_flag) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		String              sql     = "";

		try {
			connMgr = new DBConnectionManager();

			sql  = " select     a.cmuno           cmuno         , a.userid          userid        "
				+"\n          , a.kor_name        kor_name      , a.eng_name        eng_name      "
				+"\n          , a.email           email         , a.tel             tel           "
				+"\n          , a.mobile          mobile        , a.office_tel      office_tel    "
				+"\n          , a.duty            duty          , a.wk_area         wk_area       "
				+"\n          , a.grade           grade         , a.request_dte     request_dte   "
				+"\n          , a.license_dte     license_dte   , a.license_userid  license_userid"
				//2005.12.01_�ϰ��� : zero ���� ����
				//+"\n          , a.close_fg        close_fg      , a.close_reason    close_reason  "
				//+"\n          , a.close_dte       close_dte     , a.intro           intro         "
				+"\n          , a.close_fg        close_fg      , a.close_reason as close_reason  "
				+"\n          , a.close_dte       close_dte     , a.intro as intro         "
				+"\n          , a.recent_dte      recent_dte    , a.visit_num       visit_num     "
				+"\n          , a.search_num      search_num    , a.register_num    register_num  "
				+"\n          , a.modifier_dte    modifier_dte  , c.kor_nm          grade_kor_nm"
				+"\n          ,b.deptnam deptnam,b.jikupnm jikupnm,b.jikwinm jikwinm"
				+"\n   from tz_cmuusermst a,tz_member b,tz_cmugrdcode c "
				+"\n  where a.userid = b.userid "
				+"\n 	and b.grcode  = " + StringManager.makeSQL(box.getSession("tem_grcode"))
				+"\n    and a.cmuno  = c.cmuno"
				+"\n    and a.grade  = c.grcode"
				+"\n    and a.cmuno  = '"+box.getString("p_cmuno")+"'"
				+"\n    and a.userid = '"+box.getSession("userid")+"'"
				;
			if("01".equals(v_flag)){
				sql += "\n    and a.grade = '01'";
			} else {
				sql += "\n    and a.grade != '01'";
			}
			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				list.add(ls.getDataBox());
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
	 * Ŀ�´�Ƽ ������� ȸ������
	 * @param box          receive from the form object and session
	 * @return ArrayList   Ŀ�´�Ƽ ȸ������
	 * @throws Exception
	 */
	public static DataBox selectMemSingleData(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		StringBuffer        sql     = new StringBuffer();
		DataBox             dbox    = null;


		try {
			connMgr = new DBConnectionManager();

			sql.append(" select     a.cmuno           cmuno         , a.userid          userid\n ");
			sql.append("          , a.kor_name        kor_name      , a.eng_name        eng_name\n ");
			sql.append("          , a.email           email         , a.tel             tel\n ");
			sql.append("          , a.mobile          mobile        , a.office_tel      office_tel\n ");
			sql.append("          , a.duty            duty          , a.wk_area         wk_area\n ");
			sql.append("          , a.grade           grade         , a.request_dte     request_dte\n ");
			sql.append("          , a.license_dte     license_dte   , a.license_userid  license_userid\n ");
			sql.append("          , a.close_fg        close_fg      , a.close_reason as close_reason\n ");
			sql.append("          , a.close_dte       close_dte     ,  a.intro as intro\n ");
			sql.append("          , a.recent_dte      recent_dte    , a.visit_num       visit_num\n ");
			sql.append("          , a.search_num      search_num    , a.register_num    register_num\n ");
			sql.append("          , a.modifier_dte    modifier_dte  , c.kor_nm          grade_kor_nm\n ");
			sql.append("          , a.fax             fax , c.grcode\n ");
			sql.append("          ,b.deptnam deptnam,b.jikupnm jikupnm,b.jikwinm jikwinm\n ");
			sql.append("   from tz_cmuusermst a,tz_member b,tz_cmugrdcode c\n ");
			sql.append("  where a.userid = b.userid\n ");
			sql.append("	 and b.grcode  = " + StringManager.makeSQL(box.getSession("tem_grcode"))).append("\n");
			sql.append("    and a.cmuno  = c.cmuno\n ");
			sql.append("    and a.grade  = c.grcode\n ");
			//sql.append("    and a.close_fg  = '1'\n ");
			sql.append("    and a.cmuno  = '").append(box.getString("p_cmuno")).append("'\n ");
			sql.append("    and a.userid = '").append(box.getSession("userid")).append("'\n ");

			ls = connMgr.executeQuery(sql.toString());

			while (ls.next()) {
				dbox = ls.getDataBox();
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
		return dbox;
	}



	/**
	 * Ŀ�´�Ƽ ����Ÿȸ��������
	 * @param box          receive from the form object and session
	 * @return ArrayList   Ŀ�´�Ƽ ȸ������
	 * @throws Exception
	 */
	public static DataBox selectMemMasterSingleData(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		StringBuffer        sql     = new StringBuffer();
		DataBox             dbox    = null;


		try {
			connMgr = new DBConnectionManager();

			sql.append(" select     a.cmuno           cmuno         , a.userid          userid\n ");
			sql.append("          , a.kor_name        kor_name      , a.eng_name        eng_name\n ");
			sql.append("          , a.email           email         , a.tel             tel\n ");
			sql.append("          , a.mobile          mobile        , a.office_tel      office_tel\n ");
			sql.append("          , a.duty            duty          , a.wk_area         wk_area\n ");
			sql.append("          , a.grade           grade         , a.request_dte     request_dte\n ");
			sql.append("          , a.license_dte     license_dte   , a.license_userid  license_userid\n ");
			sql.append("          , a.close_fg        close_fg      , a.close_reason as close_reason\n ");
			sql.append("          , a.close_dte       close_dte     ,  a.intro as intro\n ");
			sql.append("          , a.recent_dte      recent_dte    , a.visit_num       visit_num\n ");
			sql.append("          , a.search_num      search_num    , a.register_num    register_num\n ");
			sql.append("          , a.modifier_dte    modifier_dte  , c.kor_nm          grade_kor_nm\n ");
			sql.append("          , a.fax             fax\n ");
			sql.append("          ,b.deptnam deptnam,b.jikupnm jikupnm,b.jikwinm jikwinm\n ");
			sql.append("   from tz_cmuusermst a,tz_member b,tz_cmugrdcode c\n ");
			sql.append("  where a.userid = b.userid\n ");
			sql.append("	 and b.grcode  = " + StringManager.makeSQL(box.getSession("tem_grcode"))).append("\n");
			sql.append("    and a.cmuno  = c.cmuno\n ");
			sql.append("    and a.grade  = c.grcode\n ");
			sql.append("    and a.cmuno  = '").append(box.getString("p_cmuno")).append("'\n ");
			sql.append("    and a.userid in (select userid from tz_cmuusermst where cmuno  = '").append(box.getString("p_cmuno")).append("' and grade='01')");

			ls = connMgr.executeQuery(sql.toString());

			while (ls.next()) {
				dbox = ls.getDataBox();
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
		return dbox;
	}

	/**
	 * ����Ŀ�´�Ƽ
	 * @param box          receive from the form object and session
	 * @return String   ����Ŀ�´�Ƽ ����Ʈ
	 * @throws Exception
	 */
	public String  selectMainIndex(String v_userid,String v_cmuno) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;

		String              sql     = "";
		String              ret     = "";

		try {
			connMgr = new DBConnectionManager();

			//��������
			sql  = " select b.cmuno cmuno,b.cmu_nm cmu_nm"
				+ "\n   from tz_cmuusermst a,tz_cmubasemst b"
				+ "\n  where a.cmuno     = b.cmuno"
				+ "\n    and a.userid    = '"+v_userid+"'"
				+ "\n    and b.close_fg  ='1'"
				+ "\n order by b.cmu_nm asc";
			System.out.println(sql);
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				if(ls.getString("cmuno").equals(v_cmuno)){
					ret += "\n <option value='"+ls.getString("cmuno")+"' selected>"+ls.getString("cmu_nm")+"</option>";
				} else {
					ret += "\n <option value='"+ls.getString("cmuno")+"'>"+ls.getString("cmu_nm")+"</option>";
				}
			}
			ls.close();

		}
		catch (Exception ex) {
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return ret;
	}

	/**
	 * ��ü�˸�ȭ��
	 * @param box          receive from the form object and session
	 * @return String   ��ü�˸� ��� �޼���
	 * @throws Exception
	 */
	public int  sendMsNotice(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet             ls      = null;
		ListSet             ls1      = null;

		String              sql     = "";
		String              sql1    = "";
		int                 isOk       = 1;

		String v_cmuno         = box.getString("p_cmuno");
		String v_title         = StringUtil.removeTag(box.getString("p_title"));
		String v_tmp_userid    = box.getString("p_tmp_userid");
		String v_allselect     = box.getStringDefault("p_allselect","N");
		String v_intro         = StringUtil.removeTag(box.getString("p_content"));

		String s_userid    = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			//�Ϸù�ȣ ���ϱ�
			int v_mailno=0;
			sql1 = "select NVL(max(MAILNO), 0)   from TZ_CMUMAIL ";
			ls = connMgr.executeQuery(sql1);
			while (ls.next()) v_mailno = ls.getInt(1);

			if(ls != null) { try { ls.close(); }catch (Exception e) {} }

			sql  =" insert into TZ_CMUMAIL ( mailno, userid, kor_nm, recv_email"
				+"                       ,cmuno, cmu_nm, SEND_USERID,send_email, title, content"
				+"                       ,loc_fg,loc_nm,regster_dte, send_fg)"
				+"               values  (?,?,?,?"
				//2005.11.16_�ϰ��� : Oracle -> Mssql empty_clob() ����
				//+"                       ,?,?,?,?,?,empty_clob()"
				+"                       ,?,?,?,?,?,?"
				+"                       ,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N')"
				;
			pstmt = connMgr.prepareStatement(sql);

			int index = 1;

			if("Y".equals(v_allselect)){//��üȸ���ΰ��
				sql1 = "select userid,kor_name,email  from tz_cmuusermst where cmuno='"+v_cmuno+"'";
				ls = connMgr.executeQuery(sql1);
				while (ls.next()){

					//Ŀ�´�Ƽ���ϱ�
					String v_tmp_cmu_nm="";
					sql1 = "select cmu_nm   from tz_cmubasemst where cmuno = '"+v_cmuno+"' ";
					System.out.println(sql1);
					ls1 = connMgr.executeQuery(sql1);
					while (ls1.next()) v_tmp_cmu_nm = ls1.getString(1);

					//�߽��� �̸���
					String v_tmp_send_email="";
					sql1 = "select email   from tz_member where userid = '"+s_userid+"'  and grcode  = " + StringManager.makeSQL(box.getSession("tem_grcode"));
					ls1 = connMgr.executeQuery(sql1);
					while (ls1.next()) v_tmp_send_email = ls1.getString(1);

					v_mailno =v_mailno+1;
					pstmt.setInt   (index++, v_mailno                                );//�Ϸù�ȣ
					pstmt.setString(index++, ls.getString(1)                         );//�����ھ��̵�
					pstmt.setString(index++, ls.getString(2)                         );//�����ڸ�
					pstmt.setString(index++, ls.getString(3)                         );//�������̸���
					pstmt.setString(index++, v_cmuno                                 );//Ŀ�´�Ƽ��ȣ
					pstmt.setString(index++, v_tmp_cmu_nm                            );//Ŀ�´�Ƽ��
					pstmt.setString(index++ ,s_userid                                );//�߽��ھ��̵�
					pstmt.setString(index++ ,v_tmp_send_email                        );//�߽����̸���
					pstmt.setString(index++ , v_title                                );//����
					pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
					//					pstmt.setString(index++ , v_intro                                );
					pstmt.setString(index++, "2"                                    );//����
					pstmt.setString(index++, "��ü�˶��޼���"                       );//���и�

					isOk = pstmt.executeUpdate();
					//                    sql1 = "select content  from TZ_CMUMAIL where mailno = '"+v_mailno+"'";
					//                    connMgr.setOracleCLOB(sql1, v_intro);
					/*2005.11.16_�ϰ��� : Oracle -> Mssql empty_clob() ����
                    if(isOk > 0 ) {
                        if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
                    }
					 */
				}
				if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			}  else {
				StringTokenizer stok    = new StringTokenizer(v_tmp_userid, "/");
				String[]        sTokens = new String[stok.countTokens()];
				for (int i = 0; stok.hasMoreElements();i++){
					sTokens[i] = ((String)stok.nextElement()).trim();
				}
				for (String sToken : sTokens) {

					//�����ڸ��ϱ�
					String v_recv_nm="";
					String v_recv_mail="";
					sql1 = "select kor_name,email   from tz_cmuusermst where userid = '"+sToken+"' ";
					ls1 = connMgr.executeQuery(sql1);
					while (ls1.next()){
						v_recv_nm = ls1.getString(1); v_recv_mail = ls1.getString(2);
					}

					if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
					//Ŀ�´�Ƽ���ϱ�
					String v_tmp_cmu_nm="";
					sql1 = "select cmu_nm   from tz_cmubasemst where cmuno = '"+v_cmuno+"' ";
					System.out.println(sql1);
					ls1 = connMgr.executeQuery(sql1);
					while (ls1.next()) v_tmp_cmu_nm = ls1.getString(1);

					//�߽��� �̸���
					String v_tmp_send_email="";
					sql1 = "select email   from tz_member where userid = '"+s_userid+"'  and grcode  = " + StringManager.makeSQL(box.getSession("tem_grcode"));
					ls1 = connMgr.executeQuery(sql1);
					while (ls1.next()) v_tmp_send_email = ls1.getString(1);

					v_mailno =v_mailno+1;
					pstmt.setInt   (index++, v_mailno                                );//�Ϸù�ȣ
					pstmt.setString(index++, sToken                              );//�����ھ��̵�
					pstmt.setString(index++, v_recv_nm                               );//�����ڸ�
					pstmt.setString(index++, v_recv_mail                             );//�������̸���
					pstmt.setString(index++, v_cmuno                                 );//Ŀ�´�Ƽ��ȣ
					pstmt.setString(index++, v_tmp_cmu_nm                            );//Ŀ�´�Ƽ��
					pstmt.setString(index++ ,s_userid                                );//�߽��ھ��̵�
					pstmt.setString(index++ ,v_tmp_send_email                        );//�߽����̸���
					pstmt.setString(index++ , v_title                                );//����
					//					pstmt.setString(index++ , v_intro                                );
					pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
					pstmt.setString(index++, "2"                                    );//����
					pstmt.setString(index++, "��ü�˶��޼���"                       );//���и�

					/*2005.11.16_�ϰ��� : Oracle -> Mssql empty_clob() ����
					 */
					isOk = pstmt.executeUpdate();
					//                    sql1 = "select content  from TZ_CMUMAIL where mailno = '"+v_mailno+"'";
					//                    connMgr.setOracleCLOB(sql1, v_intro);
				}
				if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }

			}//end if
			if(isOk > 0 ) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}

		}
		catch (Exception ex) {
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}



	/**
	 * �Ϲݸ�������
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int sendMail(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet     ls          = null;
		String      sql         = "";
		String      sql1        = "";
		int         isOk        = 1;

		String v_cmuno         = box.getString("p_static_cmuno");
		String v_parent_userid = box.getString("p_parent_userid");
		String v_title         = box.getString("p_title");
		String v_intro     = StringManager.replace(box.getString("content"),"<br>","\n");

		String s_userid    = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			//�Ϸù�ȣ ���ϱ�
			int v_mailno=0;
			sql1 = "select NVL(max(MAILNO), 0)   from TZ_CMUMAIL ";
			ls = connMgr.executeQuery(sql1);
			while (ls.next()) v_mailno = ls.getInt(1);

			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			sql  =" insert into TZ_CMUMAIL ( mailno, userid, kor_nm, recv_email"
				+"                       ,cmuno, cmu_nm, SEND_USERID,send_email, title, content"
				+"                       ,loc_fg,loc_nm,regster_dte, send_fg)"
				+"               values  (?,?,?,?"
				//2005.11.16_�ϰ��� : Oracle -> Mssql empty_clob() ����
				//+"                       ,?,?,?,?,?,empty_clob()"
				+"                       ,?,?,?,?,?,?"
				+"                       ,?,?,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N')"
				;
			pstmt = connMgr.prepareStatement(sql);
			//�����ڸ��ϱ�
			String v_tmp_nm="";String v_tmp_recv_emial="";
			sql1 = "select name,email   from tz_member where userid = '"+v_parent_userid+"'  and grcode  = " + StringManager.makeSQL(box.getSession("tem_grcode"));
			System.out.println(sql1);
			ls = connMgr.executeQuery(sql1);
			while (ls.next()) {
				v_tmp_nm = ls.getString(1);
				v_tmp_recv_emial = ls.getString(2);

			}
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			//Ŀ�´�Ƽ���ϱ�
			String v_tmp_cmu_nm="";
			sql1 = "select cmu_nm   from tz_cmubasemst where cmuno = '"+v_cmuno+"' ";
			System.out.println(sql1);
			ls = connMgr.executeQuery(sql1);
			while (ls.next()) v_tmp_cmu_nm = ls.getString(1);

			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			//�߽��� �̸���
			String v_tmp_send_email="";
			sql1 = "select email   from tz_member where userid = '"+s_userid+"'  and grcode  = " + StringManager.makeSQL(box.getSession("tem_grcode"));
			System.out.println(sql1);
			ls = connMgr.executeQuery(sql1);
			while (ls.next()) v_tmp_send_email = ls.getString(1);
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			int index = 1;
			v_mailno =v_mailno+1;
			pstmt.setInt   (1, v_mailno                                );//�Ϸù�ȣ
			pstmt.setString(2, v_parent_userid                         );//�����ھ��̵�
			pstmt.setString(3, v_tmp_nm                                );//�����ڸ�
			pstmt.setString(4, v_tmp_recv_emial                        );//�������̸���
			pstmt.setString(5, v_cmuno                                 );//Ŀ�´�Ƽ��ȣ
			pstmt.setString(6, v_tmp_cmu_nm                            );//Ŀ�´�Ƽ��
			pstmt.setString(7 ,s_userid                                );//�߽��ھ��̵�
			pstmt.setString(8 ,v_tmp_send_email                        );//�߽����̸���
			pstmt.setString(9 , v_title                                );//����
			pstmt.setCharacterStream(index++, new StringReader(v_intro), v_intro.length());
			//			 pstmt.setString(10 , v_intro                                );
			pstmt.setString(11, "4"                                    );//����
			pstmt.setString(12, "�Ϲݸ���"                           );//���и�
			isOk = pstmt.executeUpdate();

			/*2005.11.16_�ϰ��� : Oracle -> Mssql empty_clob() ����
             if(isOk > 0 ) {
                 if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
             }
			 */
			//             sql1 = "select content  from TZ_CMUMAIL where mailno = '"+v_mailno+"'";
			//             connMgr.setOracleCLOB(sql1, v_intro);
			if(isOk > 0 ) {
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
		return isOk;
	}

}
