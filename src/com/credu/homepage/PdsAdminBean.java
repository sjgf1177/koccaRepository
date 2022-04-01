//**********************************************************
//	1. ��	   ��: ��� �ڷ��
//	2. ���α׷���: PdsAdminBean.java
//	3. ��	   ��: ��� �ڷ��
//	4. ȯ	   ��: JDK 1.4
//	5. ��	   ��: 1.0
//	6. ��	   ��: ������ 2005.	1. 2
//	7. ��      ��: �̳��� 05.11.16 _ connMgr.setOracleCLOB ����
//**********************************************************

package	com.credu.homepage;

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
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

/**
 * �ڷ�� ����(ADMIN)
 *
 * @date   : 2005. 1
 * @author : S.W.Kang
 */
@SuppressWarnings("unchecked")
public class PdsAdminBean {
	private	static final String	FILE_TYPE =	"p_file";			//		���Ͼ��ε�Ǵ� tag name
	private	static final int FILE_LIMIT	= 5;					//	  �������� ���õ� ����÷�� ����
	private	ConfigSet config;
	private	int	row;

	public PdsAdminBean() {
		try{
			config = new ConfigSet();
			row	= Integer.parseInt(config.getProperty("page.bulletin.row"));		//		  �� �����	�������� row ���� �����Ѵ�
		}
		catch(Exception	e) {
			e.printStackTrace();
		}
	}

	/**
	 * �ڷ�� ����Ʈȭ�� select
	 * @param	box			 receive from the form object and session
	 * @return ArrayList	 �ڷ��	����Ʈ
	 * @throws Exception
	 */
	public ArrayList selectPdsList(RequestBox box) throws Exception	{
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		ArrayList list = null;
		// ������ : 05.11.11 ������ : �̳��� _ TotalCount ���ֱ� ���� ������ ���� ����
		String sql     = "";
		String count_sql = "";
		String head_sql  = "";
		String body_sql  = "";
		String group_sql = "";
		String order_sql = "";
		//PdsData	data = null;
		DataBox dbox = null;

		int	v_pageno = box.getInt("p_pageno");
		String v_searchtext	= box.getString("p_searchtext");
		String v_search	    = box.getString("p_search");

		try	{
			connMgr	= new DBConnectionManager();

			list = new ArrayList();

			head_sql	= "select a.seq, a.userid, a.name, a.title,	count(b.realfile) filecnt, a.indate, a.cnt  ";
			body_sql	+= " from TZ_BOARD a, TZ_BOARDFILE b                                              ";
			// ������ : 05.11.04 ������ : �̳��� _(+)  ����
			//			sql	+= " where a.tabseq = b.tabseq(+)                                                       ";
			//			sql += "   and a.seq    = b.seq(+)                                                          ";
			body_sql	+= " where a.tabseq  =  b.tabseq(+)                                                       ";
			body_sql += "   and a.seq     =  b.seq(+)                                                          ";
			body_sql += "   and a.tabseq = 11";

			if ( !v_searchtext.equals("")) {				//	  �˻�� ������
				if (v_search.equals("name")) {				//	  �̸����� �˻��Ҷ�
					body_sql	+= " and upper(a.name)	like upper(" + StringManager.makeSQL("%" + v_searchtext +	"%")+")";
				}
				else if	(v_search.equals("title")) {		//	  �������� �˻��Ҷ�
					body_sql	+= " and upper(a.title) like upper("	+ StringManager.makeSQL("%"	+ v_searchtext + "%")+")";
				}
				//	  else if (v_select.equals("content")) {		//	  �������� �˻��Ҷ�
				//		  sql += " and a.content like "	+ StringManager.makeSQL("%"	+ v_searchtext + "%");
				//	  }
			}
			group_sql	+= " group by a.seq, a.userid, a.name, a.title,	a.indate, a.cnt                        ";
			order_sql	+= " order by a.seq desc                                                               ";

			sql= head_sql+ body_sql+ group_sql+ order_sql;
			System.out.println(" pds sql >>>>> "+ sql);
			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ body_sql;
			System.out.println(" pds count >>>>> "+ count_sql);
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

			ls.setPageSize(row);			              	//	 �������� row ������ �����Ѵ�
			ls.setCurrentPage(v_pageno, total_row_count);	//	   ������������ȣ��	�����Ѵ�.
			//			int	totalpagecount = ls.getTotalPage();		  	//	 ��ü ������ ���� ��ȯ�Ѵ�

			while (ls.next()) {
				dbox = ls.getDataBox();
				list.add(dbox);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box,	sql);
			throw new Exception("sql = " + sql + "\r\n"	+ ex.getMessage());
		}
		finally	{
			if(ls != null) { try { ls.close(); }catch (Exception e)	{} }
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return list;
	}

	/**
	 * ���õ�	�ڷ�� �Խù� �󼼳��� select
	 * @param box          receive from the form object and session
	 * @return ArrayList   ��ȸ�� ������
	 * @throws Exception
	 */
	public DataBox selectPds(RequestBox box)	throws Exception {
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		String sql = "";

		DataBox dbox = null;

		int	v_seq =	box.getInt("p_seq");

		Vector realfileVector = new Vector();
		Vector savefileVector = new Vector();
		Vector fileseqVector  = new Vector();


		//		int	[] fileseq = new int [v_upfilecnt];

		try	{
			connMgr	= new DBConnectionManager();

			sql	= "select a.seq, a.userid, a.name, a.title, a.content, b.fileseq, b.realfile, b.savefile, a.indate, a.cnt ";
			sql	+= " from TZ_BOARD a, TZ_BOARDFILE b                                              ";
			// ������ : 05.11.04 ������ : �̳��� _(+)  ����
			//			sql	+= " where a.tabseq = b.tabseq(+)                                                 ";
			//			sql += "   and a.seq    = b.seq(+)                                                    ";
			sql	+= " where a.tabseq  =  b.tabseq(+)                                                 ";
			sql += "   and a.seq     =  b.seq(+)                                                    ";
			sql += "   and a.tabseq = 11";
			sql += "   and a.seq    = " + v_seq;

			ls = connMgr.executeQuery(sql);

			while(ls.next()) {
				//-------------------   2004.12.25  ����     -------------------------------------------------------------------
				dbox = ls.getDataBox();
				if(dbox.getString("d_realfile").length()>0) {
					realfileVector.addElement(dbox.getString("d_realfile"));
					savefileVector.addElement(dbox.getString("d_savefile"));
					fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
				}
			}
			if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
			if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
			if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);

			connMgr.executeUpdate("update TZ_BOARD set cnt = cnt + 1 where tabseq =11 and seq = "+v_seq);
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
	 * ���ο� �ڷ�� ���� ���
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int insertPds(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		PreparedStatement pstmt1 = null;
		String sql = "";
		String sql1	= "";//,sql2 = "";
		int	isOk1 =	1;
		int	isOk2 =	1;

		String v_title    = StringUtil.removeTag(box.getString("p_title"));
		String v_content  = StringUtil.removeTag(box.getString("p_content"));

		String s_userid = box.getSession("userid");
		String s_usernm = box.getSession("name");

		try	{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			//----------------------   �Խ��� ��ȣ �����´�	----------------------------
			sql	= "select NVL(max(seq),	0) from	TZ_BOARD where tabseq = 11";
			ls = connMgr.executeQuery(sql);
			ls.next();
			int	v_seq =	ls.getInt(1) + 1;
			ls.close();
			/*********************************************************************************************/
			// ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�.
			try {
				v_content =(String) NamoMime.setNamoContent(v_content);
			} catch(Exception e) {
				System.out.println(e.toString());
				return 0;
			}
			/*********************************************************************************************/

			//////////////////////////////////	 �Խ���	table �� �Է�  ///////////////////////////////////////////////////////////////////
			sql1 =	"insert	into TZ_BOARD(tabseq, seq, userid, name, indate, title, content, cnt, luserid, ldate)               ";
			//			sql1 +=	" values (?, ?, ?, ?, to_char(sysdate,	'YYYYMMDDHH24MISS'), ?, empty_clob(), ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
			sql1 +=	" values (?, ?, ?, ?, to_char(sysdate,	'YYYYMMDDHH24MISS'), ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

			int index = 1;
			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setInt   (index++, 11);
			pstmt1.setInt   (index++, v_seq);
			pstmt1.setString(index++,	s_userid);
			pstmt1.setString(index++,	s_usernm);
			pstmt1.setString(index++,	v_title);
			pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			//			pstmt1.setString(index++,	v_content);
			pstmt1.setInt   (index++, 0);
			pstmt1.setString(index++,	s_userid);

			isOk1 =	pstmt1.executeUpdate();
			isOk2 = this.insertUpFile(connMgr,11, v_seq, box);

			/* 05.11.16 �̳���*/
			//			sql2 = "select content from tz_board where tabseq = 11 and seq = " + v_seq ;
			//			connMgr.setOracleCLOB(sql2, v_content);       //      (��Ÿ ���� ���)
			if(isOk1 > 0 && isOk2 > 0) connMgr.commit();
			else 		               connMgr.rollback();

		}
		catch (Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex,	box, sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(ls != null)      { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt1 != null)  { try { pstmt1.close(); } catch (Exception e1) {} }
			if(connMgr != null)   try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1*isOk2;
	}


	/**
	 * ���õ� �ڷ� �󼼳��� ����
	 * @param box      receive from the form object and session
	 * @return isOk    1:update success,0:update fail
	 * @throws Exception
	 */
	public	int	updatePds(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		PreparedStatement pstmt1 = null;
		String sql1	= "";
		int	isOk1 =	1;
		int	isOk2 =	1;
		int	isOk3 =	1;

		int	v_seq =	box.getInt("p_seq");
		int	v_upfilecnt	= box.getInt("p_upfilecnt");	//	������ ������ִ� ���ϼ�
		String v_title = StringUtil.removeTag(box.getString("p_title"));
		String v_content = StringUtil.removeTag(box.getString("p_content"));

		Vector v_savefile =	new	Vector();
		Vector v_filesequence =	new	Vector();

		for(int	i =	0; i < v_upfilecnt;	i++) {
			if(	!box.getString("p_fileseq" + i).equals(""))	{
				v_savefile.addElement(box.getString("p_savefile" + i));			//		������ ������ִ� ���ϸ� �߿���	������ ���ϵ�
				v_filesequence.addElement(box.getString("p_fileseq"	+ i));		 //		 ������	������ִ� ���Ϲ�ȣ	�߿��� ������ ���ϵ�
			}
		}

		String s_userid	= box.getSession("userid");
		String s_usernm	= box.getSession("name");

		try	{
			connMgr	= new DBConnectionManager();
			connMgr.setAutoCommit(false);
			/*********************************************************************************************/
			// ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�.
			try {
				v_content =(String) NamoMime.setNamoContent(v_content);
			} catch(Exception e) {
				System.out.println(e.toString());
				return 0;
			}
			/*********************************************************************************************/

			sql1 = "update TZ_BOARD set title = ?, content = ?, userid =	?, name	= ?, luserid = ?, indate = to_char(sysdate,	'YYYYMMDDHH24MISS')";
			//			sql1 = "update TZ_BOARD set title = ?, content = empty_clob(), userid =	?, name	= ?, luserid = ?, indate = to_char(sysdate,	'YYYYMMDDHH24MISS')";
			sql1 +=	"  where tabseq = 11 and seq = ?";

			int index = 1;
			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setString(index++,	v_title);
			pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			pstmt1.setString(index++,	s_userid);
			pstmt1.setString(index++,	s_usernm);
			pstmt1.setString(index++,	s_userid);
			pstmt1.setInt   (index++, v_seq);

			isOk1 =	pstmt1.executeUpdate();

			isOk2 =	this.insertUpFile(connMgr, 11, v_seq,	box);		//		����÷���ߴٸ� ����table��	insert

			isOk3 =	this.deleteUpFile(connMgr, box,	v_filesequence);		//	   ������ ������ �ִٸ�	����table���� ����

			if(isOk1 > 0 &&	isOk2 >	0 && isOk3 > 0)	{
				connMgr.commit();
				if (v_savefile != null)	{
					FileManager.deleteFile(v_savefile);			//	 DB	���� ���ó����	�Ϸ�Ǹ� �ش� ÷������ ����
				}
			} else connMgr.rollback();
		}
		catch(Exception	ex)	{
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex,	box, sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null)  { try { pstmt1.close(); } catch (Exception e) {}	}
			if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1*isOk2*isOk3;
	}


	/**
	 * ���õ� �Խù�	����
	 * @param box      receive from the form object and session
	 * @return isOk    1:delete success,0:delete fail
	 * @throws Exception
	 */
	public int deletePds(RequestBox	box) throws	Exception {
		DBConnectionManager	connMgr	= null;
		PreparedStatement pstmt1 = null;
		PreparedStatement pstmt2 = null;
		String sql1	= "";
		String sql2	= "";
		int	isOk1 =	1;
		int	isOk2 =	1;

		int	v_seq =	box.getInt("p_seq");
		int	v_upfilecnt	= box.getInt("p_upfilecnt");	//	������ ������ִ� ���ϼ�
		Vector v_savefile  = box.getVector("p_savefile");

		try	{
			connMgr	= new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql1 = "delete from	TZ_BOARD	where tabseq = 11 and seq = ? ";

			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setInt(1, v_seq);

			isOk1 =	pstmt1.executeUpdate();

			if (v_upfilecnt > 0 ) {
				sql2 = "delete from	TZ_BOARDFILE where tabseq = 11 and seq =	?";

				pstmt2 = connMgr.prepareStatement(sql2);

				pstmt2.setInt(1, v_seq);

				isOk2 =	pstmt2.executeUpdate();

			}
			System.out.println(" pds 435 isOk1="+isOk1);

			if (isOk1 >	0 && isOk2 > 0)	{
				connMgr.commit();
				if (v_savefile != null)	{
					FileManager.deleteFile(v_savefile);			//	 ÷������ ����
				}
			} else connMgr.rollback();
		}
		catch (Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box,	sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {}	}
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {}	}
			if(connMgr != null) try { connMgr.setAutoCommit(true); } catch (Exception e) {}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1*isOk2;
	}


	///////////////////////////////////////////////////////	 ���� ���̺�   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * ���ο� �ڷ����� ���
	 * @param connMgr  DB Connection Manager
	 * @param p_tabseq �Խ��� �Ϸù�ȣ
	 * @param p_seq    �Խù� �Ϸù�ȣ
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public	int	insertUpFile(DBConnectionManager connMgr, int p_tabseq, int p_seq, RequestBox	box) throws	Exception {
		ListSet	ls = null;
		PreparedStatement pstmt2 = null;
		String sql = "";
		String sql2	= "";
		int	isOk2 =	1;

		//----------------------   ���ε�Ǵ� ������ ������	�˰� �ڵ��ؾ��Ѵ�  --------------------------------

		String [] v_realFileName = new String [FILE_LIMIT];
		String [] v_newFileName	= new String [FILE_LIMIT];

		for(int	i =	0; i < FILE_LIMIT; i++)	{
			v_realFileName [i] = box.getRealFileName(FILE_TYPE + (i+1));
			v_newFileName [i] =	box.getNewFileName(FILE_TYPE + (i+1));
		}
		//----------------------------------------------------------------------------------------------------------------------------

		String s_userid	= box.getSession("userid");

		try	{
			//----------------------	�ڷ� ��ȣ �����´� ----------------------------
			sql	= "select NVL(max(fileseq),	0) from	TZ_BOARDFILE	where tabseq = 11 and seq =	" +	p_seq;
			ls = connMgr.executeQuery(sql);
			ls.next();
			int	v_fileseq =	ls.getInt(1) + 1;
			ls.close();
			//------------------------------------------------------------------------------------

			//////////////////////////////////	 ���� table	�� �Է�	 ///////////////////////////////////////////////////////////////////
			sql2 =	"insert	into TZ_BOARDFILE(tabseq, seq, fileseq, realfile, savefile, luserid,	ldate)";
			sql2 +=	" values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

			pstmt2 = connMgr.prepareStatement(sql2);

			for(int	i =	0; i < FILE_LIMIT; i++)	{
				if(	!v_realFileName	[i].equals(""))	{		//		���� ���ε�	�Ǵ� ���ϸ�	üũ�ؼ� db�� �Է��Ѵ�
					pstmt2.setInt(1, 11);
					pstmt2.setInt(2, p_seq);
					pstmt2.setInt(3, v_fileseq);
					pstmt2.setString(4,	v_realFileName[i]);
					pstmt2.setString(5,	v_newFileName[i]);
					pstmt2.setString(6,	s_userid);

					isOk2 =	pstmt2.executeUpdate();
					v_fileseq++;
				}
			}
		}
		catch (Exception ex) {
			FileManager.deleteFile(v_newFileName, FILE_LIMIT);		//	�Ϲ�����, ÷������ ������ ����..
			ErrorManager.getErrorStackTrace(ex,	box, sql2);
			throw new Exception("sql = " + sql2	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(ls != null) { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
		}
		return isOk2;
	}


	/**
	 * ���õ� �ڷ����� DB���� ����
	 * @param connMgr			DB Connection Manager
	 * @param box				receive from the form object and session
	 * @param p_filesequence    ���� ���� ����
	 * @return
	 * @throws Exception
	 */
	public int deleteUpFile(DBConnectionManager	connMgr, RequestBox box, Vector p_filesequence)	throws Exception {
		PreparedStatement pstmt3 = null;
		String sql3	= "";
		int	isOk3 =	1;


		int	v_seq =	box.getInt("p_seq");

		try	{
			sql3 = "delete from TZ_BOARDFILE where tabseq = 11 and seq =? and fileseq = ?";

			pstmt3 = connMgr.prepareStatement(sql3);

			for(int	i =	0; i < p_filesequence.size(); i++) {
				int	v_fileseq =	Integer.parseInt((String)p_filesequence.elementAt(i));

				pstmt3.setInt(1, v_seq);
				pstmt3.setInt(2, v_fileseq);

				isOk3 =	pstmt3.executeUpdate();
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box,	sql3);
			throw new Exception("sql = " + sql3	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {}	}
		}
		return isOk3;
	}

}
