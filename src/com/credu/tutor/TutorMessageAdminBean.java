//**********************************************************
//1. ��      ��: ���� �޼���
//2. ���α׷���: TutorMessageAdminBean.java
//3. ��      ��: ���� �޼���
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 1.0
//6. ��      ��: ������ 2005. 12. 20
//7. ��      ��:
//**********************************************************

package com.credu.tutor;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

public class TutorMessageAdminBean {
	
	private	ConfigSet config;
	private	int	row;

	public TutorMessageAdminBean() {
		try{
			config = new ConfigSet();
			row	= Integer.parseInt(config.getProperty("page.bulletin.row"));		//		  �� �����	�������� row ���� �����Ѵ�
		}
		catch(Exception	e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ο� �޼��� ���� ���
	 * @param box        receive from the form object and session
	 * @return isOk      1:insert success, 0:insert fail
	 * @throws Exception
	 */
	public int insertMessage(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt1 = null;
		ListSet ls = null;		
		String sql = "";
		String sql1 = "";
		int isOk1 = 0;

		String v_title   = StringUtil.removeTag(box.getString("p_title"));
		String v_content = StringUtil.removeTag(box.getString("p_content"));
		String v_subj = box.getString("p_subjcourse");
		String s_userid = box.getSession("userid");
		int v_seq  = 0;
		
		try {
			connMgr = new DBConnectionManager();

			// ---------------------- �ִ밪 �����´� ----------------------------
			sql = "select NVL(max(seq),	0) from TZ_TUTORMESSAGE";
			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
			   v_seq = ls.getInt(1) + 1;
			}
			// ------------------------------------------------------------------------------------

			   /*********************************************************************************************/
			   // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
			   try {
				   v_content =(String) NamoMime.setNamoContent(v_content);
			   } catch(Exception e) {
				   System.out.println(e.toString());
				   return 0;
			   }
			   /*********************************************************************************************/

			// //////////////////////////////// �Խ��� table �� �Է�
			sql1 = "insert	into TZ_TUTORMESSAGE(SEQ, SUBJ, USERID, TITLE, CONTENT, INDATE, CNT, LDATE)               ";
			sql1 += " values (?, ?, ?, ?, ?, to_char(sysdate,	'YYYYMMDDHH24MISS'), ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setInt(1, v_seq);
			pstmt1.setString(2, v_subj);
			pstmt1.setString(3, s_userid);
			pstmt1.setString(4, v_title);
			pstmt1.setString(5, v_content);
			pstmt1.setInt(6, 0);

			isOk1 = pstmt1.executeUpdate();
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		} finally {
			if(ls != null) {try	{ls.close();} catch(Exception e){}}
			if (pstmt1 != null) { try { pstmt1.close();} catch (Exception e1) {}}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1;
	}
	
	/**
	* ���õ� �ڷ� �󼼳��� ����
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/  	
	 public	int	updateMessage(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		PreparedStatement pstmt1 = null;
		String sql1	= "";
		int	isOk1 =	1;

		int	v_seq =	box.getInt("p_seq");
		String v_title = StringUtil.removeTag(box.getString("p_title"));
		String v_content = StringUtil.removeTag(box.getString("p_content"));
		String s_userid	= box.getSession("userid");
		
		try	{
			connMgr	= new DBConnectionManager();
			
			   /*********************************************************************************************/
			   // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
			   try {
				   v_content =(String) NamoMime.setNamoContent(v_content);
			   } catch(Exception e) {
				   System.out.println(e.toString());
				   return 0;
			   }
			   /*********************************************************************************************/

			sql1  = " update TZ_TUTORMESSAGE set title = ?, content = ?, userid = ?, indate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
			sql1 +=	"  where seq = ? ";

			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setString(1,	v_title);
			pstmt1.setString(2,	v_content);
			pstmt1.setString(3,	s_userid);			
			pstmt1.setInt(4, v_seq);

			isOk1 =	pstmt1.executeUpdate();

		}
		catch(Exception	ex)	{
			ErrorManager.getErrorStackTrace(ex,	box, sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {}	}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1;
	}
	
	/**
	* ���õ� �Խù�	����
	* @param box      receive from the form object and session
	* @return isOk    1:delete success,0:delete fail
	* @throws Exception
	*/    	
	public int deleteMessage(RequestBox	box) throws	Exception {
		DBConnectionManager	connMgr	= null;
		PreparedStatement pstmt1 = null;
		String sql1	= "";
		// String sql2	= "";
		int	isOk1 =	0;
		int	v_seq =	box.getInt("p_seq");
		
		try	{
			connMgr	= new DBConnectionManager();

			sql1 = "delete from	TZ_TUTORMESSAGE	where seq = ? ";
			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setInt(1, v_seq);
			isOk1 =	pstmt1.executeUpdate();

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box,	sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {}	}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1;
	}
	
	   /**
	   * ���õ�	�������� �Խù� �󼼳��� select
	   * @param box          receive from the form object and session
	   * @return ArrayList   ��ȸ�� ������
	   * @throws Exception
	   */   
	   public DataBox selectMessage(RequestBox box)	throws Exception {
			DBConnectionManager	connMgr	= null;
			ListSet	ls = null;
			String sql = "";
			DataBox dbox = null;

			int	v_seq =	box.getInt("p_seq");

			try	{
				connMgr	= new DBConnectionManager();
				
				sql = " select seq, subj, userid, get_name(userid) name, "; 
				sql+= "        title, content, indate, cnt                   ";
	            sql+= "   from TZ_tutormessage                               ";
	            sql+= "  where seq =   " + v_seq;
				ls = connMgr.executeQuery(sql);
				
				if (ls.next()) {
	                dbox = ls.getDataBox();
	            }
				
				connMgr.executeUpdate("update TZ_TUTORMESSAGE set cnt = cnt + 1 where seq = "+v_seq);
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
	 * �����޼��� ����Ʈȭ�� select
	 * @param box        receive from the form object and session
	 * @return ArrayList �������� ����Ʈ
	 * @throws Exception
	 */
	public ArrayList<DataBox> selectMessageList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList<DataBox> list = null;
		String sql = "";
		DataBox dbox = null;

		int v_pageno = box.getInt("p_pageno");
		
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");    //�����з�
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");   //�����з�
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");    //�����з�
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//����&�ڽ�
        String  ss_action   = box.getString("s_action");

		try {
            if(ss_action.equals("go")){
				connMgr = new DBConnectionManager();
				list = new ArrayList<DataBox>();

				sql = " select a.seq, a.userid, get_name(userid) name, "; 
				sql+= "        a.title, a.indate, a.cnt, c.subjnm          ";
	            sql+= "  from TZ_tutormessage a, TZ_SUBJ c                 ";
	            sql+= "  where a.subj=c.subj                               ";
	
	            if (!ss_uclass.equals("ALL"))     sql+= " and c.upperclass = "+SQLString.Format(ss_uclass);
	            if (!ss_mclass.equals("ALL"))     sql+= " and c.middleclass = "+SQLString.Format(ss_mclass);
	            if (!ss_lclass.equals("ALL"))     sql+= " and c.lowerclass = "+SQLString.Format(ss_lclass);
	            if (!ss_subjcourse.equals("ALL")) sql+= " and c.subj       = "+SQLString.Format(ss_subjcourse);
	
				sql+= " order by a.seq desc                                                               \n";			
				
	//			System.out.println("TutorMessageAdminBean �����޼��� ����Ʈȭ�� : "+sql);
	
				ls = connMgr.executeQuery(sql);
	
				ls.setPageSize(row);                          //  �������� row ������ �����Ѵ�
	            ls.setCurrentPage(v_pageno);                //     ������������ȣ�� �����Ѵ�.
	            int total_page_count = ls.getTotalPage();   //     ��ü ������ ���� ��ȯ�Ѵ�
	            int total_row_count = ls.getTotalCount();   //     ��ü row ���� ��ȯ�Ѵ�
	
				while (ls.next()) {
	                dbox = ls.getDataBox();
	                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
	                dbox.put("d_totalpagecount", new Integer(total_page_count));
	                dbox.put("d_rowcount",new Integer(row));					
	                list.add(dbox);
				}
            }
		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		} finally {
			if (ls != null) { try {ls.close();} catch (Exception e) {}}
			if (connMgr != null) {try {	connMgr.freeConnection();} catch (Exception e10) {}}
		}
		return list;
	}

}
