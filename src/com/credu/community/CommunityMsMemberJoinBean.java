//**********************************************************
//1. ��      ��: Ŀ�´�Ƽ ȸ������
//2. ���α׷���: CommunityMsMemberJoinBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��:
//
//**********************************************************

package com.credu.community;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
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
public class CommunityMsMemberJoinBean {
	private ConfigSet config;
	private static int row=10;
	//    private String v_type = "PQ";
	//    private static final String FILE_TYPE = "p_file";           //      ���Ͼ��ε�Ǵ� tag name
	//    private static final int FILE_LIMIT = 1;                    //    �������� ���õ� ����÷�� ����


	public void CommunityMsMemberJoinBean() {
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
	 * Ŀ�´�Ƽ ȸ����ȸ
	 * @param box          receive from the form object and session
	 *        close_fg     ��������
	 * @return ArrayList   Ŀ�´�Ƽ ȸ������Ʈ
	 * @throws Exception
	 */
	public ArrayList selectMemberList(RequestBox box,String v_close_fg) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		// 05.12.07 �̳��� _ totlacount �ϱ����� ��������
		String              sql     = "";
		String              head_sql     = "";
		String              body_sql     = "";
		String              group_sql     = "";
		String              order_sql     = "";
		String              count_sql     = "";
		DataBox             dbox    = null;

		String v_static_cmuno  = box.getString("p_cmuno");
		String v_searchtext    = box.getString("p_searchtext");
		String v_select        = box.getString("p_select");

		String v_selGrade	   = box.getStringDefault("p_selGrade", "ALL");

		int    v_pageno        = box.getInt("p_pageno");


		// ��ް˻�����
		String v_grcode        = box.getStringDefault("p_grcode", "ALL");

		//        String s_userid        = box.getSession("userid");
		//        String s_name          = box.getSession("name");

		try {
			connMgr = new DBConnectionManager();
			// 05.12.07 �̳��� _ rownum ����
			//            sql  = "\n select a.*,rownum rowseq from ("
			head_sql  ="\n  select     a.cmuno           cmuno         , a.userid          userid        "
				+"\n           , a.kor_name        kor_name      , a.eng_name        eng_name      "
				+"\n           , a.email           email         , a.tel             tel           "
				+"\n           , a.mobile          mobile        , a.office_tel      office_tel    "
				+"\n           , a.duty            duty          , a.wk_area         wk_area       "
				+"\n           , a.grade           grade         , a.request_dte     request_dte   "
				+"\n           , a.license_dte     license_dte   , a.license_userid  license_userid"
				// 05.12.07 �̳��� _ Underlying input stream returned zero bytes ���� ����.
				//                +"\n           , a.close_fg        close_fg      , a.close_reason    close_reason  "
				//                +"\n           , a.close_dte       close_dte     , a.intro           intro         "
				+"\n          , a.close_fg        close_fg      , a.close_reason as close_reason "
				+"\n          , a.close_dte       close_dte     ,  a.intro as  intro         "
				+"\n           , a.recent_dte      recent_dte    , a.visit_num       visit_num     "
				+"\n           , a.search_num      search_num    , a.register_num    register_num  "
				+"\n           , a.modifier_dte    modifier_dte  , c.kor_nm          grade_kor_nm"
				+"\n           ,b.deptnam deptnam,b.jikupnm jikupnm,b.jikwinm jikwinm ";
			body_sql ="\n    from tz_cmuusermst a,tz_member b,tz_cmugrdcode c "
				+"\n   where a.userid = b.userid "
				+ "\n  and b.grcode  = " + StringManager.makeSQL(box.getSession("tem_grcode"))
				+"\n     and a.cmuno  = c.cmuno"
				+"\n     and a.grade  = c.grcode"
				+"\n     and a.cmuno  = '"+v_static_cmuno+"'"
				+"\n     and a.close_fg like '%"+v_close_fg+"%'"
				+"\n     and a.grade     !='01'";
			if ( !v_searchtext.equals("")) {      // �˻�� ������
				if (v_select.equals("userid"))   body_sql += "\n  and lower(a.userid) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%")+")";
				if (v_select.equals("kor_name")) body_sql += "\n  and a.kor_name like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i ��
			}
			// ��ް˻���
			if(!v_grcode.equals("ALL")){
				body_sql += "\n  and a.grade = '"+v_grcode+"'";
			}

			if(!v_selGrade.equals("ALL")){
				body_sql += "\n  and a.grade = '"+v_selGrade+"'";
			}

			order_sql += "\n  order by a.kor_name asc ";
			//                 + "\n  ) a";
			sql= head_sql+ body_sql+ group_sql+ order_sql;
			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ body_sql;
			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);    // ��ü row ���� ��ȯ�Ѵ�
			int total_page_count = ls.getTotalPage();    	// ��ü ������ ���� ��ȯ�Ѵ�

			ls.setPageSize(row);                         	// �������� row ������ �����Ѵ�
			ls.setCurrentPage(v_pageno,total_row_count);    // ������������ȣ�� �����Ѵ�.

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
				dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
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
	 * Ŀ�´�Ƽ ȸ�� ���� ���� �� �ź�
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int updateCmuUserCloseFg(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet   ls          = null;
		String    sql         = "";
		String    sql1        = "";
		int       isOk        = 0;
		//        int       v_seq       = 0;

		String    v_cmuno         = box.getString("p_cmuno");
		String    v_close_fg      = box.getString("p_close_fg");
		Vector    v_list_userid   = box.getVector("p_list_userid");
		//        String    v_intro     = StringManager.replace(box.getString("content"),"<br>","\n");
		String    s_userid        = box.getSession("userid");
		//        String    s_name          = box.getSession("name");


		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);


			for(int i=0;i<v_list_userid.size();i++){
				if("1".equals(v_close_fg)){

					sql  =" update tz_cmuusermst set  close_fg           =?   "
						+"                          ,license_dte    =to_char(sysdate, 'YYYYMMDDHH24MISS')"
						+"                          ,license_userid =?"
						+"                     where cmuno = ?"
						+"                       and userid =?";
					pstmt = connMgr.prepareStatement(sql);
					pstmt.setString(1, v_close_fg         );
					pstmt.setString(2, s_userid         );
					pstmt.setString(3, v_cmuno         );
					pstmt.setString(4, (String)v_list_userid.elementAt(i));
					isOk = pstmt.executeUpdate();

					// 05.12.08 �̳��� �߰� _ ���Խ��� ó���� ȸ���� ����
					sql1  = " update tz_cmubasemst set MEMBER_CNT = MEMBER_CNT+1 where cmuno ='"+v_cmuno+"'";
					pstmt = connMgr.prepareStatement(sql1);
					pstmt.executeUpdate();

				} else if("2".equals(v_close_fg)){
					sql  =" update tz_cmuusermst set  close_fg           =?   "
						+"                          ,close_dte    =to_char(sysdate, 'YYYYMMDDHH24MISS')"
						+"                     where cmuno = ?"
						+"                       and userid =?";
					pstmt = connMgr.prepareStatement(sql);
					pstmt.setString(1, v_close_fg         );
					pstmt.setString(2, v_cmuno         );
					pstmt.setString(3, (String)v_list_userid.elementAt(i));
					isOk = pstmt.executeUpdate();
				}
			}
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
