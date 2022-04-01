//**********************************************************
//  1. ��	  ��: ���ø��޴�����
//  2. ���α׷��� : TempletMenuAdminBean
//  3. ��	  ��: ���ø��������� ���Ǵ� �޴��� �����Ѵ�.
//  4. ȯ	  ��: JDK 1.5
//  5. ��	  ��: 1.0
//  6. ��	  ��: swchoi 2009.11.02
//  7. ��	  ��:
//**********************************************************

package com.credu.course;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.FileMove;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

@SuppressWarnings("unchecked")
public class TempletMenuAdminBean {

	public TempletMenuAdminBean() {}


	/**
	��з��ڵ� �����Ҷ� - ���� �Һз� ����
	@param box	  receive from the form object and session
	@return isOk	1:delete success,0:delete fail
	 */
	public int deleteMenu(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt1 = null;
		String sql1 = "";

		int isOk1 = 0;
		//		int isOk2 = 0;

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);////

			sql1  = " delete from tz_homemenu_master  ";
			sql1 += "  where GRTYPE = ? and  gubun = ? and MENUID = ?  ";

			pstmt1 = connMgr.prepareStatement(sql1);
			int i = 1;
			pstmt1.setString(i++, box.getString("p_grtype"));				// ����ڵ�
			pstmt1.setString(i++, box.getString("p_gubun"));				// �޴�����
			pstmt1.setString(i++, box.getString("p_menuid"));			// �޴���ȣ
			isOk1 = pstmt1.executeUpdate();

			if(isOk1 > 0 ) {
				connMgr.commit();		 //	  ���� �з��� �ο찡 ������� isOk2 �� 0 �̹Ƿ� isOk2 >0 ���� ����
			} else {
				connMgr.rollback();
			}
		}
		catch (Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql1+ "\r\n");
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
			if(connMgr != null) {
				try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		//		return isOk1*isOk2;
		return isOk1;
	}

	/**
	�޴� ����Ҷ�
	@param box	  receive from the form object and session
	@return isOk	1:insert success,0:insert fail
	 */
	public int insertMenu(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();
		int isOk = 0;
		String s_userid = box.getSession("userid");

		try {
			moveFile(box);
			connMgr = new DBConnectionManager();

			sql.append("INSERT INTO TZ_HOMEMENU_MASTER ( ");
			sql.append(" GRTYPE, GUBUN, MENUID,\n\t   KIND, POSITION, ORDERS,\n\t   MENUNAME, MENUURL, MENUIMG, ");
			sql.append(" \n\t   MENUOVERIMG, SUBIMG, SUBOVERIMG, FLASHFILENAME, MENUHOMEIMG,LDATE,LUSERID ");
			sql.append(" )\n   ");
			sql.append(" SELECT ");
			sql.append(" 	B.GRTYPE, ");
			sql.append(" 	B.GUBUN, ");
			sql.append(" 	NVL( ?, LPAD( NVL( MAX( MENUID ), 0 )\n\t   + 1, 2, '0' )) MENUID, ");
			sql.append(" 	B.KIND, ");
			sql.append(" 	B.POSITION, ");
			sql.append(" 	B.ORDERS,\n\t ");
			sql.append(" 	B.MENUNAME, ");
			sql.append(" 	B.MENUURL, ");
			sql.append(" 	B.MENUIMG,\n\t   ");
			sql.append(" 	B.MENUOVERIMG, ");
			sql.append(" 	B.SUBIMG, ");
			sql.append(" 	B.SUBOVERIMG, ");
			sql.append(" 	B.FLASHFILENAME, ");
			sql.append(" 	B.MENUHOMEIMG, ");
			sql.append(" 	B.LDATE, ");
			sql.append(" 	B.LUSERID\n ");
			sql.append(" FROM TZ_HOMEMENU_MASTER A,\n\t ");
			sql.append(" 	( ");
			sql.append(" 		SELECT ");
			sql.append(" 			? GRTYPE, ");
			sql.append(" 			? GUBUN, ");
			sql.append(" 			? KIND,\n\t\t\t  ");
			sql.append(" 			? POSITION, ");
			sql.append(" 			? ORDERS, ");
			sql.append(" 			? MENUNAME,\n\t\t\t  ");
			sql.append(" 			? MENUURL, ");
			sql.append(" 			? MENUIMG, ");
			sql.append(" 			? MENUOVERIMG, ");
			sql.append(" 			? SUBIMG, ");
			sql.append(" 			? SUBOVERIMG,\n\t\t\t  ");
			sql.append(" 			? FLASHFILENAME, ");
			sql.append(" 			? MENUHOMEIMG, ");
			sql.append(" 			TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') LDATE, ");
			sql.append(" 			? LUSERID\n\t   ");
			sql.append(" 		FROM DUAL ");
			sql.append(" 	) B\n ");
			sql.append(" WHERE A.GRTYPE(+) = B.GRTYPE\n  AND A.GUBUN(+) = B.GUBUN \n ");
			// sql.append(" GROUP BY A.GRTYPE, A.GUBUN");

			pstmt = connMgr.prepareStatement(sql.toString());

			int i = 1;
			pstmt.setString(i++, box.getString("p_menuid"));				// �޴���ȣ
			pstmt.setString(i++, box.getString("p_grtype"));					// ����ڵ�
			pstmt.setString(i++, box.getString("p_gubun"));					// �޴�����
			pstmt.setString(i++, box.getString("p_kind"));						// ���θ޴�, ����޴� ����
			pstmt.setString(i++, box.getString("p_position"));				// ��ġ����
			pstmt.setString(i++, box.getString("p_orders"));					// ���ļ���
			pstmt.setString(i++, box.getString("p_menuname"));			// �޴���
			pstmt.setString(i++, box.getString("p_menuurl"));				// �޴��ּ�
			pstmt.setString(i++, box.getString("new_menuimg"));		// �޴��̹���
			pstmt.setString(i++, box.getString("new_menuoverimg"));	// ���콺�����޴��̹���
			pstmt.setString(i++, box.getString("new_subimg"));		// �޴��̹���
			pstmt.setString(i++, box.getString("new_suboverimg"));	// ���콺�����޴��̹���
			pstmt.setString(i++, box.getString("new_flashfilename"));	// ���콺�����޴��̹���
			pstmt.setString(i++, box.getString("new_menuhomeimg"));	// ���콺�����޴��̹���
			pstmt.setString(i++, s_userid);

			isOk = pstmt.executeUpdate();

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}


	private void moveFile(RequestBox box) throws Exception {
		// ���ε�� ���ϸ�
		String v_menuimg        = box.getNewFileName("p_menuimg");
		String v_menuoverimg     = box.getNewFileName("p_menuoverimg");
		String v_subimg        = box.getNewFileName("p_subimg");
		String v_suboverimg     = box.getNewFileName("p_suboverimg");
		String v_flashfilename     = box.getNewFileName("P_flashfilename");
		String v_menuhomeimg     = box.getNewFileName("p_menuhomeimg");

		String defaultName ="menu_" + box.getString("p_gubun") + "_" + box.getString("p_menuid");
		FileMove filemove = new FileMove();
		box.put("new_menuimg", runMove(filemove, v_menuimg, defaultName, "_main_off", box.getString("p_gubun")));
		box.put("new_menuoverimg", runMove(filemove, v_menuoverimg, defaultName, "_main_on", box.getString("p_gubun")));
		box.put("new_subimg", runMove(filemove, v_subimg, defaultName, "_sub_off", box.getString("p_gubun")));
		box.put("new_suboverimg", runMove(filemove, v_suboverimg, defaultName, "_sub_on", box.getString("p_gubun")));
		box.put("new_flashfilename", runMove(filemove, v_flashfilename, defaultName, "", box.getString("p_gubun")));
		box.put("new_menuhomeimg", runMove(filemove, v_menuhomeimg, defaultName, "", box.getString("p_gubun")));
	}

	private String runMove(FileMove filemove, String oFile, String defaultName, String type, String gubun) throws Exception{
		// ���� �̵�
		ConfigSet conf = new ConfigSet();
		String v_thisPath = conf.getProperty("dir.home");     //  �̵��ϱ��� ���
		String v_thatPath = v_thisPath+conf.getProperty("dir.templet.submenu")+gubun + "\\";   // �̵��� ���
		String result = null;
		if (!oFile.equals("")) {
			// ����� ���ϸ�
			result = defaultName + type+ oFile.substring(oFile.lastIndexOf('.'));
			filemove.move(v_thatPath, v_thisPath, oFile,	result);
		} else {
			result = "";
		}
		return result;
	}
	/**
	�޴����
	@param box		  receive from the form object and session
	@return ArrayList   �޴����
	 */
	public ArrayList selectListMenu(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = null;

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();

			sql  = " select GRTYPE, GUBUN, MENUID,\n\t   KIND, DECODE(KIND, 'MB','����','SB','����') KINDNM, POSITION, ORDERS,\n\t   MENUNAME, MENUURL, MENUIMG,\n\t   MENUOVERIMG, subimg, suboverimg, useyn from tz_homemenu_master   ";
			sql += "\n where MENUNAME like " + StringManager.makeSQL("%" + box.get("s_menuname") + "%");
			sql += "\n and decode(nvl(" + StringManager.makeSQL(box.get("s_gubun")) + ", '00'), '00', MENUID, GUBUN) = nvl(" + StringManager.makeSQL(box.get("s_gubun")) + ", '00') ";
			sql += "\n and GRTYPE like nvl(" + StringManager.makeSQL(box.get("s_grtype")) + ", '%') ";
			sql += "\n order by GRTYPE, gubun asc, POSITION, orders";

			System.out.println(sql);
			ls = connMgr.executeQuery(sql);
			list = ls.getAllDataList();
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
	�޴� �����Ͽ� �����Ҷ�
	@param box	  receive from the form object and session
	@return isOk	1:update success,0:update fail
	 */
	public int updateMenu(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer sql = new StringBuffer();
		int isOk = 0;

		String s_userid	= box.getSession("userid");

		try {
			moveFile(box);
			connMgr = new DBConnectionManager();

			sql.append(" UPDATE TZ_HOMEMENU_MASTER ");
			sql.append(" SET KIND = ?, ");
			sql.append(" 	POSITION = ?, ");
			sql.append("	ORDERS = ?, ");
			sql.append("	MENUNAME = ?, ");
			sql.append("	MENUURL = ?, ");
			sql.append("	MENUIMG = NVL(TRIM(?),MENUIMG), ");
			sql.append("	MENUOVERIMG = NVL(TRIM(?),MENUOVERIMG), ");
			sql.append("	SUBIMG = NVL(TRIM(?),SUBIMG), ");
			sql.append("	SUBOVERIMG = NVL(TRIM(?),SUBOVERIMG), ");
			sql.append("	FLASHFILENAME = NVL(TRIM(?),FLASHFILENAME), ");
			sql.append("	MENUHOMEIMG = NVL(TRIM(?),MENUHOMEIMG), ");
			sql.append("	LUSERID= ?, ");
			sql.append("	LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ");
			sql.append(" WHERE GRTYPE = ? AND  GUBUN = ? AND MENUID = ? ");

			pstmt = connMgr.prepareStatement(sql.toString());
			int i = 1;

			pstmt.setString(i++, box.getString("p_kind"));				// ���θ޴�, ����޴� ����
			pstmt.setString(i++, box.getString("p_position"));			// ��ġ����
			pstmt.setString(i++, box.getString("p_orders"));			// ���ļ���
			pstmt.setString(i++, box.getString("p_menuname"));			// �޴���
			pstmt.setString(i++, box.getString("p_menuurl"));			// �޴��ּ�
			pstmt.setString(i++, box.getString("new_menuimg"));			// �޴��̹���
			pstmt.setString(i++, box.getString("new_menuoverimg"));		// ���콺�����޴��̹���
			pstmt.setString(i++, box.getString("new_subimg"));			// �޴��̹���
			pstmt.setString(i++, box.getString("new_suboverimg"));		// ���콺�����޴��̹���
			pstmt.setString(i++, box.getString("new_flashfilename"));	// �÷��� �̹���
			pstmt.setString(i++, box.getString("new_menuhomeimg"));		// ���� �޴�Ȩ �̹���

			pstmt.setString(i++, s_userid);
			pstmt.setString(i++, box.getString("p_grtype"));			// ����ڵ�
			pstmt.setString(i++, box.getString("p_gubun"));				// �޴�����
			pstmt.setString(i++, box.getString("p_menuid"));			// �޴���ȣ
			//System.out.println(sql+"\n"+box.getString("p_grtype")+"\n"+box.getString("p_gubun")+"\n"+box.getString("p_menuid"));
			isOk = pstmt.executeUpdate();
		}
		catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}
}
