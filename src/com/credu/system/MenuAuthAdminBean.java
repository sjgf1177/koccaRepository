//**********************************************************
//  1. ��      ��: �޴� ����
//  2. ���α׷��� : MenuAuthAdminBean.java
//  3. ��      ��: �޴� ����
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 11.  09
//  7. ��      ��:
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
 * �޴� ����(ADMIN)
 *
 * @date   : 2003. 7
 * @author : s.j Jung
 */
@SuppressWarnings("unchecked")
public class MenuAuthAdminBean {
	private static final String CONFIG_NAME = "cur_nrm_grcode";

	public MenuAuthAdminBean() {}

	/**
    ���� ����
    @param box          receive from the form object and session
    @return result      ���� ����
	 */
	public int selectCountGadmin(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		//		ArrayList list = null;
		String sql = "";
		int result = 0;

		try {
			connMgr = new DBConnectionManager();

			//			list = new ArrayList();

			sql  = " select count(*) cnt from TZ_GADMIN where isview = 'Y' ";

			ls = connMgr.executeQuery(sql);

			if  (ls.next()) {
				result = ls.getInt("cnt");
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
		return result;
	}



	/**
    ���� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ����Ʈ
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

			sql  = " select gadmin, gadminnm from TZ_GADMIN where isview = 'Y' ";
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
    �޴����Ѽ���ȭ�� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ����Ʈ
	 */
	public ArrayList selectListMenuAuth(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		ArrayList list2 = null;
		MenuAuthData data2 = null;

		//        String v_grcode  = box.getString("p_grcode");
		String v_systemgubun = box.getStringDefault("p_systemgubun","1");
		String v_grcode  = CodeConfigBean.getConfigValue(CONFIG_NAME);

		String sql_add1 = "";                                         // �������� ���� �߰� ����(�����ڵ�)
		String sql_add2 = "";                                         // �������� ���� �߰� ����(�ش����(�б�/����))
		ArrayList list1 = selectListGadmin(box);           // ������������Ʈ
		GadminData data1 = null;
		String v_gadmin    = "";
		int    v_gadmincnt = list1.size();

		try {
			connMgr = new DBConnectionManager();

			//   ���� ���� ���� ����
			for(int i = 0; i < v_gadmincnt; i++) {
				data1   = (GadminData)list1.get(i);
				v_gadmin   = data1.getGadmin();

				//�����ڵ�
				if (i == 0) {                                           // ó�� ���� ������ ���Ͽ�
					sql_add1 += ",\n ";
				} else {                                                // ���ڰ� ���ս� ���̿� '/' �߰�
					// ������ : 05.11.04 ������ : �̳��� _ || ����
					//				   sql_add1 += " || '/' ||  ";
					sql_add1 += "\n || '/' ||  ";
				}

				// ������ ���Ѻ� ��� ���� ID colnum
				sql_add1 += StringManager.makeSQL(v_gadmin) ;
				if ((i+1) == v_gadmincnt ) sql_add1 += " as gadmin ";   // ������ �ο��� ���

				//�ش����(�б�/����)
				if (i == 0) {                                           // ó�� ���� ������ ���Ͽ�
					sql_add2 += ", ";
				} else {                                                // ���ڰ� ���ս� ���̿� '/' �߰�
					// ������ : 05.11.04 ������ : �̳��� _ || ����
					//                 sql_add2 += " || '/' ||  ";
					sql_add2 += " || '/' ||  ";
				}
				// ������ ���Ѻ� ���Ѱ�(�б�/����) �÷� (row =>  colum ���� ��ȯ)
				// ���� : �ش簪�� ��ġ ������ ���� ���а� (�Ǿռ��� ���ڸ�) �߰����� (  i  + ���Ѱ�(�б�/����)  )

				// ������ : 05.11.04 ������ : �̳��� _ decode, || ����
				//              sql_add2 += " max(decode(gadmin, "  + StringManager.makeSQL(v_gadmin) + ", '" + i + "' || control ,'" + i + "')) ";
				sql_add2 += " max(case gadmin ";
				sql_add2 += " When "  + StringManager.makeSQL(v_gadmin) + " 	Then  '" + i + "' || control ";
				sql_add2 += " Else '" + i + "' ";
				sql_add2 += " End )\n";
				// ������ : 05.11.04 ������ : �̳��� _ ������� ����
				if ((i+1) == v_gadmincnt ) sql_add2 += " as control ";   // ������ �ο��� ���
			}

			list2 = new ArrayList();

			// ������ : 05.11.04 ������ : �̳��� _ ���� ���� from z ��Ī ���
			sql  = " select z.grcode, z.menu, z.menunm, z.levels, z.seq\n";

			// ���� ���� ���� ���� �߰�
			sql += sql_add1;
			sql += sql_add2;

			sql += "\n   from\n";
			sql += " (\n";
			sql += "   select a.grcode grcode, a.menu menu, b.modulenm menunm, b.seq seq,\n";
			sql += "          a.levels levels, c.gadmin gadmin, c.control control\n";
			sql += "     from tz_menu a\n";
            sql+="       left join tz_menusub b on a.GRCODE=B.GRCODE and A.MENU=b.menu\n";
            sql+="       left join tz_menuauth c on b.grcode=c.grcode and b.menu=c.menu and b.seq=C.MENUSUBSEQ\n ";
			sql += "     where ";
			sql += "      a.grcode = " + StringManager.makeSQL(v_grcode);
			sql += "      and a.systemgubun = " + StringManager.makeSQL(v_systemgubun);
			sql += "  ) z\n";
			// ������ : 05.11.04 ������ : �̳��� _ �������
			sql += " group by grcode, menu, menunm, seq, levels\n";
			sql += " order by grcode asc , menu asc, seq asc\n";

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				data2 = new MenuAuthData();

				data2.setGrcode(ls.getString("grcode"));
				data2.setMenu(ls.getString("menu"));
				data2.setMenunm(ls.getString("menunm"));
				data2.setLevels(ls.getInt("levels"));
				data2.setSeq(ls.getInt("seq"));
				if ( v_gadmincnt > 0) {
					data2.setGadmin(ls.getString("gadmin"));
					data2.setControl(ls.getString("control"));
				}

				list2.add(data2);
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

		return list2;
	}


	/**
    �޴����Ѽ����Ͽ� �����Ҷ�
    @param box      receive from the form object and session
    @return isOk    1:update success,0:update fail
	 */
	public int updateMenuAuth(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		String sql1 = "";
		String sql2 = "";
		//		int isOk1 = 0;
		int isOk2 = 0;
		int isOk2_check = 0;

		String v_grcode  = CodeConfigBean.getConfigValue(CONFIG_NAME);
		String s_userid  = box.getSession("userid");
		String v_systemgubun = box.getString("p_systemgubun");

		ArrayList list1  = selectListGadmin(box);           // ������������Ʈ
		GadminData data1 = null;
		String v_gadmin_org    = "";
		int    v_gadmincnt = list1.size();                            // ������������

		String v_menu    = "";
		String v_gadmin  = "";
		String v_control = "";
		int v_menusubseq = 0;

		Vector v_vecmenu[]       = new Vector[v_gadmincnt];
		Vector v_vecmenusubseq[] = new Vector[v_gadmincnt];
		Vector v_vecgadmin[]     = new Vector[v_gadmincnt];
		System.out.println("v_gadmincnt="+v_gadmincnt);

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);////

			// ���� ����Ÿ ����
			sql1  = " delete from TZ_MENUAUTH   ";
			sql1 += "   where grcode = ? "; //and systemgubun = ?   ";
			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setString(1, v_grcode);
			//            pstmt1.setString(2, v_systemgubun);
			//			isOk1 =
			pstmt1.executeUpdate();

			// ����� �ڷ� ���
			sql2  = " insert into TZ_MENUAUTH  (grcode, gadmin, menusubseq, menu, control, systemgubun, luserid, ldate)       ";
			sql2 += "                  values (?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
			pstmt2 = connMgr.prepareStatement(sql2);

			isOk2 = 1;
			//   ���� ������ŭ ����
			for(int i = 0; i < v_gadmincnt; i++) {

				data1   = (GadminData)list1.get(i);
				v_gadmin_org   = data1.getGadmin();

				v_vecmenu[i]       = box.getVector("p_menu" + v_gadmin_org);
				v_vecmenusubseq[i] = box.getVector("p_menusubseq" + v_gadmin_org);
				v_vecgadmin[i]     = box.getVector("p_gadmin" + v_gadmin_org);

				// �޴� ������ŭ ����
				for(int j = 0; j < v_vecmenu[i].size() ; j++){
					isOk2_check = 0;

					v_menu       = (String)v_vecmenu[i].elementAt(j);
					v_menusubseq = StringManager.toInt((String)v_vecmenusubseq[i].elementAt(j));
					v_gadmin     = (String)v_vecgadmin[i].elementAt(j);
					v_control = StringManager.chkNull(box.getString("p_" + v_gadmin_org + "R" + j + i)) + StringManager.chkNull(box.getString("p_" + v_gadmin_org + "W" + j + i));

					// DB INSERT
					pstmt2.setString(1, v_grcode);
					pstmt2.setString(2, v_gadmin);
					pstmt2.setInt(3, v_menusubseq);
					pstmt2.setString(4, v_menu);
					pstmt2.setString(5, v_control);
					pstmt2.setString(6, v_systemgubun);
					pstmt2.setString(7, s_userid);

					isOk2_check = pstmt2.executeUpdate();
					if (isOk2_check == 0) isOk2 = 0;
				}
			}

			// ���� ��ϵ� �ڷᰡ ���� �� �����Ƿ� isOk1 üũ ����
			if ( isOk2 > 0) connMgr.commit();
			else connMgr.rollback();
		}
		catch(Exception ex) {connMgr.rollback();
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


}
