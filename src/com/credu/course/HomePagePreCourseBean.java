//**********************************************************
//1. ��      ��: �������������Խ��� 
//2. ���α׷���: CommunityCreateBean.java
//3. ��      ��: �������������Խ���
//4. ȯ      ��: JDK 1.0
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2005.12.13
//7. ��      ��:
//
//**********************************************************

package com.credu.course;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;
import com.credu.community.*;
import com.dunet.common.util.StringUtil;
import com.namo.*;

/**
* @author Administrator
*
* To change the template for this generated type comment go to
* Window>Preferences>Java>Code Generation>Code and Comments
*/
public class HomePagePreCourseBean {
  private ConfigSet config;
  private static int row=10;
 /* private static String v_type = "PQ";
  private static final String FILE_TYPE = "p_file";           //      ���Ͼ��ε�Ǵ� tag name
  private static final int FILE_LIMIT = 1;                    //    �������� ���õ� ����÷�� ����
  */

  public void HomePagePreCourseBean() {
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
   * �������������Խ��� ���λ�� ����Ʈ
   * @param box          receive from the form object and session
   * @return ArrayList   �������� ����Ʈ
   * @throws Exception
   */
   public ArrayList selectDirectList(RequestBox box) throws Exception {
 	  DBConnectionManager	connMgr	= null;
 	  Connection conn	= null;
 	  ListSet	ls = null;
 	  ArrayList list = null;
      String sql     = "";
      String count_sql = "";
      String head_sql  = "";
 	  String body_sql  = "";		
      String group_sql = "";
      String order_sql = "";
 	  DataBox dbox = null;

 		int		v_pageno 		= box.getInt("p_pageno");
 		String  v_searchtext	= box.getString("p_searchtext");

 		try	{
 			connMgr	= new DBConnectionManager();
 			list = new ArrayList();
			System.out.println(" ���θ��� �������� ");

 			head_sql  = "select * from ( select rownum rnum,  seq, title, userid, name, redate, content, indate, cnt, luserid, ldate  ";
 			body_sql += " from TZ_PRECOURSE ";
 			order_sql	+= " order by seq desc    ) where rnum < 5 ";

 			sql= head_sql+ body_sql+ group_sql+ order_sql;
 			ls = connMgr.executeQuery(sql);
 			
// 			count_sql= "select count(*) "+ body_sql;
// 			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);
//
// 			ls.setPageSize(row);			              	//	 �������� row ������ �����Ѵ�
// 			ls.setCurrentPage(v_pageno, total_row_count);	//	   ������������ȣ��	�����Ѵ�.
// 			int	total_page_count = ls.getTotalPage();		  	//	 ��ü ������ ���� ��ȯ�Ѵ�

 			while (ls.next()) {
               dbox = ls.getDataBox();
//               dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
//               dbox.put("d_totalpage", new Integer(total_page_count));
//               dbox.put("d_rowcount" , new Integer(row));
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
  * �������������Խ��� ����Ʈ
  * @param box          receive from the form object and session
  * @return ArrayList   �������� ����Ʈ
  * @throws Exception
  */
  public ArrayList selectList(RequestBox box) throws Exception {
	  DBConnectionManager	connMgr	= null;
	  Connection conn	= null;
	  ListSet	ls = null;
	  ArrayList list = null;
      String sql     = "";
      String count_sql = "";
      String head_sql  = "";
	  String body_sql  = "";		
      String group_sql = "";
      String order_sql = "";
	  DataBox dbox = null;

		int		v_pageno 		= box.getInt("p_pageno");
		String  v_searchtext	= box.getString("p_searchtext");

		try	{
			connMgr	= new DBConnectionManager();
			list = new ArrayList();

			head_sql  = "select seq, title, userid, name, redate, content, indate, cnt, luserid, ldate  ";
			body_sql += " from TZ_PRECOURSE ";

			if ( !v_searchtext.equals("")) {				//	  �˻�� ������
					body_sql	+= " where upper(title) like upper("	+ StringManager.makeSQL("%"	+ v_searchtext + "%")+")";
			}
			order_sql	+= " order by seq desc                                                               ";

			sql= head_sql+ body_sql+ group_sql+ order_sql;
			ls = connMgr.executeQuery(sql);
			
			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

			ls.setPageSize(row);			              	//	 �������� row ������ �����Ѵ�
			ls.setCurrentPage(v_pageno, total_row_count);	//	   ������������ȣ��	�����Ѵ�.
			int	total_page_count = ls.getTotalPage();		  	//	 ��ü ������ ���� ��ȯ�Ѵ�

			while (ls.next()) {
              dbox = ls.getDataBox();
              dbox.put("d_dispnum"  , new Integer(total_row_count - ls.getRowNum() + 1));
              dbox.put("d_totalpage", new Integer(total_page_count));
              dbox.put("d_rowcount" , new Integer(row));
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
  * �������������Խ��� �󼼳��� ��ȸ
  * @param box          receive from the form object and session
  * @return ArrayList   �������� ��ȸ
  * @throws Exception
  */
  public DataBox selectView(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;
		int	v_seq =	box.getInt("p_seq");

		try	{
			connMgr	= new DBConnectionManager();

			sql	 = "select a.seq, a.userid, a.name, a.title, a.content, a.redate, a.indate, a.cnt ";
			sql	+= " from TZ_PRECOURSE a";
			sql += "   where a.seq    = " + v_seq;			

			ls = connMgr.executeQuery(sql);
			
			while(ls.next()) {
          //-------------------   2004.12.25  ����     -------------------------------------------------------------------
				dbox = ls.getDataBox();
			}
			connMgr.executeUpdate("update TZ_PRECOURSE set cnt = cnt + 1 where seq = "+v_seq);
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
  * �������������Խ��� ����ϱ�
  * @param box      receive from the form object and session
  * @return isOk    1:insert success,0:insert fail
  * @throws Exception
  */
  public int insertPreCourse(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		ListSet	ls = null;
		PreparedStatement pstmt1 = null;
		String sql = "";
		String sql1	= "",sql2 = "";
		int	isOk1 =	1;

		String v_title    = StringUtil.removeTag(box.getString("p_title"));
		String v_content  = StringUtil.removeTag(box.getString("p_content"));
		//String v_redate	  = box.getString("p_redate");
		//System.out.println("v_redate : "+v_redate);
		String v_content1 = "";

		String s_userid = box.getSession("userid");
		String s_usernm = box.getSession("name");

		try	{
			connMgr = new DBConnectionManager();

			//----------------------   �Խ��� ��ȣ �����´�	----------------------------
			sql	= "select isnull(max(seq),	0) from	tz_PRECOURSE ";
			ls = connMgr.executeQuery(sql);
			ls.next();
			int	v_seq =	ls.getInt(1) + 1;
			ls.close();
			//------------------------------------------------------------------------------------
			/*********************************************************************************************/
//			 ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
          ConfigSet conf = new ConfigSet();
          SmeNamoMime namo = new SmeNamoMime(v_content); // ��ü���� 
          boolean result = namo.parse(); // ���� �Ľ� ���� 
          if ( !result ) { // �Ľ� ���н� 
              System.out.println( namo.getDebugMsg() ); // ����� �޽��� ��� 
              return 0;
          }
//          if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ� 
//              String v_server = conf.getProperty("autoever.url.value");
//              String fPath  = conf.getProperty("dir.namo");   // ���� ���� ��� ����
//              String refUrl = conf.getProperty("url.namo");; // ������ ����� ������ �����ϱ� ���� ���
//          }
//          if ( !result ) { // �������� ���н� 
//              System.out.println( namo.getDebugMsg() ); // ����� �޽��� ��� 
//              return 0;
//          }
          v_content = namo.getContent(); // ���� ����Ʈ ���
          /*********************************************************************************************/

			System.out.println(" PreCourse 235 content = "+v_content);
			
			
			//////////////////////////////////	 �Խ���	table �� �Է�  ///////////////////////////////////////////////////////////////////
			sql1 =	"insert	into TZ_PRECOURSE(seq, title, userid, name, redate, content, indate, cnt, luserid, ldate)               ";
			sql1 +=	" values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

			pstmt1 = connMgr.prepareStatement(sql1);

			pstmt1.setInt   (1, v_seq);
			pstmt1.setString(2, v_title);
			pstmt1.setString(3,	s_userid);
			pstmt1.setString(4,	s_usernm);
			pstmt1.setString(5,	box.getString("p_redate").replaceAll("-",""));
			pstmt1.setString(6,	v_content);
			pstmt1.setInt   (7, 0);
			pstmt1.setString(8,	s_userid);

			isOk1 =	pstmt1.executeUpdate();
		}
		catch (Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex,	box, sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(ls != null)      { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt1 != null)  { try { pstmt1.close(); } catch (Exception e1) {} }
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1;
	}


    /**
  * �������������Խ��� �����ϱ�
  * @param box      receive from the form object and session
  * @return isOk    1:insert success,0:insert fail
  * @throws Exception
  */
  public int updatePreCourse(RequestBox box) throws Exception {
		DBConnectionManager	connMgr	= null;
		Connection conn	= null;
		PreparedStatement pstmt1 = null;
		String sql1	= "",sql2="";
		int	isOk1 =	1;
		int	isOk2 =	1;
		int	isOk3 =	1;

		int	v_seq =	box.getInt("p_seq");
		String v_title 		= StringUtil.removeTag(box.getString("p_title"));
		String v_content 	= StringUtil.removeTag(box.getString("p_content"));
		String v_redate		= box.getString("p_redate");

		String s_userid	= box.getSession("userid");
		String s_usernm	= box.getSession("name");
		
		/*********************************************************************************************/
		ConfigSet conf = new ConfigSet();
      SmeNamoMime namo = new SmeNamoMime(v_content); // ��ü���� 
      boolean result = namo.parse(); // ���� �Ľ� ���� 
      if ( !result ) { // �Ľ� ���н� 
          System.out.println( namo.getDebugMsg() ); // ����� �޽��� ��� 
          return 0;
      }
//      if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ� 
//          String v_server = conf.getProperty("autoever.url.value");
//          String fPath = conf.getProperty("dir.namo");   // ���� ���� ��� ����
//          String refUrl = conf.getProperty("url.namo");; // ������ ����� ������ �����ϱ� ���� ���
//      }
//      if ( !result ) { // �������� ���н� 
//          System.out.println( namo.getDebugMsg() ); // ����� �޽��� ��� 
//          return 0;
//      }
      v_content = namo.getContent(); // ���� ����Ʈ ���
      /*********************************************************************************************/
   
		System.out.println(" PreCourse 314 ���� content = "+v_content);
		
		try	{
			connMgr	= new DBConnectionManager();

			sql1  = " update TZ_PRECOURSE set title = ?, content = ?, userid = ?, name	= ?, luserid = ?, REDATE = ? ,ldate = to_char(sysdate,	'YYYYMMDDHH24MISS')";
			sql1 +=	"  where seq = ?";

			pstmt1 = connMgr.prepareStatement(sql1);
			pstmt1.setString(1,	v_title  );
			pstmt1.setString(2,	v_content);
			pstmt1.setString(3,	s_userid );
			pstmt1.setString(4,	s_usernm );
			pstmt1.setString(5,	s_userid );
			pstmt1.setString(6,	box.getString("p_redate").replaceAll("-",""));
			pstmt1.setInt   (7, v_seq    );

			isOk1 =	pstmt1.executeUpdate();
		}
		catch(Exception	ex)	{
			ErrorManager.getErrorStackTrace(ex,	box, sql1);
			throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt1 != null)  { try { pstmt1.close(); } catch (Exception e) {}	}
			if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
		}
		return isOk1*isOk2*isOk3;
	}

 
	 /**
	    * �������������Խ��� �ۻ����ϱ�
	    * @param box      receive from the form object and session
	    * @return isOk    1:insert success,0:insert fail
	    * @throws Exception
	    */
	    public int deletePreCourse(RequestBox box) throws Exception {
			DBConnectionManager	connMgr	= null;
			Connection conn	= null;
			PreparedStatement pstmt1 = null;
			PreparedStatement pstmt2 = null;
			String sql1	= "";
			String sql2	= "";
			int	isOk1 =	1;
			int	isOk2 =	1;

			int	v_seq =	box.getInt("p_seq");
			
			try	{
				connMgr	= new DBConnectionManager();

				sql1 = "delete from	TZ_PRECOURSE	where seq = ? ";

				pstmt1 = connMgr.prepareStatement(sql1);

				pstmt1.setInt(1, v_seq);

				isOk1 =	pstmt1.executeUpdate();

				System.out.println(" PRECOURSE 835 isOk1 = "+isOk1);
			}
			catch (Exception ex) {
				connMgr.rollback();
				ErrorManager.getErrorStackTrace(ex, box,	sql1);
				throw new Exception("sql = " + sql1	+ "\r\n" + ex.getMessage());
			}
			finally	{
				if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {}	}
				if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {}	}
				if(connMgr != null)	{ try {	connMgr.freeConnection(); }catch (Exception	e10) {}	}
			}
			return isOk1*isOk2;
		}
}
