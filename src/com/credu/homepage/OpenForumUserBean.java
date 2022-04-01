//**********************************************************
//  1. ��      ��: ȸ������
//  2. ���α׷���: OpenForumUserBean.java
//  3. ��      ��: ȸ������ ����
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2005.12.27
//  7. ��      ��:
//**********************************************************
package com.credu.homepage;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class OpenForumUserBean {

    public OpenForumUserBean() {

        try{
            config = new ConfigSet();
//            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
			row = 5; 	// �����̹Ƿ� 5�� ���� ����;
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private ConfigSet config;
	private static final String	BOARD_TYPE = "KB";
	private static final int    BOARD_TABSEQ = 7;
	private	static final String	FILE_TYPE =	"p_file";				// ���Ͼ��ε�Ǵ� tag name
	private	static final int    FILE_LIMIT	= 5;					// �������� ���õ� ����÷�� ����
    private int row;
    Vector realfileVector = new Vector();
    Vector savefileVector = new Vector();
    Vector fileseqVector  = new Vector();


	/**
	* �ڷ�� ���̺��ȣ
	* @param box          receive from the form object and session
	* @return int         �ڷ�� ���̺��ȣ
	* @throws Exception
	*/
	public int selectTableseq(RequestBox box) throws Exception {
		 DBConnectionManager connMgr = null;
		 ListSet ls = null;
		 String sql = "";
		 int result = 0;

		 String v_type    = box.getStringDefault("p_type","FD");
	     String v_grcode  = box.getStringDefault("p_grcode","0000000");
		 String v_comp    = box.getStringDefault("p_comp","0000000000");
		 String v_subj    = box.getStringDefault("p_subj","0000000000");
		 String v_year    = box.getStringDefault("p_year","0000");
		 String v_subjseq = box.getStringDefault("p_subjseq","0000");

		 try {
			 connMgr = new DBConnectionManager();

			 sql  = " select tabseq from TZ_BDS      ";
			 sql += "  where type    = " + StringManager.makeSQL(v_type);
			 sql += "    and grcode  = " + StringManager.makeSQL(v_grcode);
			 sql += "    and subj    = " + StringManager.makeSQL(v_subj);
			 sql += "    and year    = " + StringManager.makeSQL(v_year);
		 	 sql += "    and subjseq = " + StringManager.makeSQL(v_subjseq);

			 ls = connMgr.executeQuery(sql);

			 if ( ls.next()) {
				 result = ls.getInt("tabseq");
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
		 return result;
	 }

	/**
		* ������������ ��ȸ���� ���� �����Ѱ� select
		* @param box      receive from the form object and session
		* @return isOk    1:delete success,0:delete fail
		* @throws String
		*/
   public ArrayList  SelectSubjectForumMax(RequestBox box, String sType,  String sSelectCnt,  String sDateyn ) throws Exception {
	  DBConnectionManager connMgr = null;
	   ListSet ls = null;
	   ArrayList list = null;
	   String sql = "";
	   DataBox dbox = null;

	   String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

	   try {
		   connMgr = new DBConnectionManager();
		   list = new ArrayList();

		    sql = " select * from (select rownum rnum, a.grcode, a.seq, a.types, a.title, a.contents, a.indate, ";
			sql += "          a.inuserid, a.inusernm, a.upfile,  a.ldate, a.cnt,  ";
     		sql += "		  a.subjectseq, a.categorycd, a.openyn, a.recommend , case when NVL(a.subjectseq,0) >0   then b.subject else c.codenm end as subject, ";
			sql += "		  d.realfile, d.savefile, d.fileseq ";
			sql += " from   tz_openforum a ";
			sql += "			 left outer join  tz_openforumsubject b on  a.grcode = b.grcode and a.subjectseq = b.subjectseq ";
			sql += "			 left outer join  tz_code c on  c.gubun = '0066' and c.code = a.categorycd ";
			sql += "			 left outer join  TZ_BOARDFILE d on  d.tabseq = '23' and a.seq = d.seq ";
			sql += " where   a.grcode ='"+s_grcode+"'  ";
		    if ( "Y".equals(sDateyn))
		    {
				sql += "     and  a.indate   >=  '"+ FormatDate.getRelativeDate( FormatDate.getDate("yyyyMMdd") , -30)   + "'";
		    }
		    sql += "     and  a.types = '"+sType+"'  ";
		    sql += " order by a.cnt desc ) where rnum < "+sSelectCnt;
		   ls = connMgr.executeQuery(sql);
		   while (ls.next()) {
			   dbox = ls.getDataBox();
			   realfileVector.addElement(dbox.getString("d_realfile"));
               savefileVector.addElement(dbox.getString("d_savefile"));
               fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
			   if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
			   if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
               if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);

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
			* ������������ �ڸ�Ʈ ���� ���� ����
			* @param box      receive from the form object and session
			* @return isOk    1:delete success,0:delete fail
			* @throws String
			*/
	   public ArrayList  SelectSubjectCommentMax(RequestBox box, String sType,  String sSelectCnt ) throws Exception {

		  DBConnectionManager connMgr = null;
		   ListSet ls = null;
		   ArrayList list = null;
		   String sql = "";
		   DataBox dbox = null;

		   String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

		   try {
			   connMgr = new DBConnectionManager();
			   list = new ArrayList();

				sql = " select * from ( select rownum rnum,   a.grcode, a.seq, a.types, a.title, a.contents, a.indate, ";
				sql += "          a.inuserid, a.inusernm, a.upfile,  a.ldate, a.cnt,  ";
			    sql += "		  a.subjectseq, a.categorycd, a.openyn, a.recommend ,a.commentcnt,  case when NVL(a.subjectseq,0) >0   then b.subject else c.codenm end as subject, ";
				sql += "		  d.realfile, d.savefile, d.fileseq ";
				sql += " from   tz_openforum a ";
				sql += "			 left outer join  tz_openforumsubject b on  a.grcode = b.grcode and a.subjectseq = b.subjectseq ";
				sql += "			 left outer join  tz_code c on  c.gubun = '0066' and c.code = a.categorycd ";
				sql += "			 left outer join  TZ_BOARDFILE d on  d.tabseq = '23' and a.seq = d.seq ";
				sql += " where   a.grcode ='"+s_grcode+"'  ";
				sql += "     and  a.types = '"+sType+"'  ";
			    sql += "     and  a.indate   >=  '"+ FormatDate.getRelativeDate( FormatDate.getDate("yyyyMMdd") , -30)   + "'";
				sql += " order by a.commentcnt  desc ) where rnum < "+sSelectCnt;
			   ls = connMgr.executeQuery(sql);
			   while (ls.next()) {
				   dbox = ls.getDataBox();
				   realfileVector.addElement(dbox.getString("d_realfile"));
	               savefileVector.addElement(dbox.getString("d_savefile"));
	               fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));

				   if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
				   if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
				   if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);
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
		* ��õ���� �����Ѱ� select
		* @param box      receive from the form object and session
		* @return isOk    1:delete success,0:delete fail
		* @throws String
		*/
	   public ArrayList  SelectSubjectForumReviewMax(RequestBox box) throws Exception {
		  DBConnectionManager connMgr = null;
		   ListSet ls = null;
		   ArrayList list = null;
		   String sql = "";
		   DataBox dbox = null;

		   String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

		   try {
			   connMgr = new DBConnectionManager();
			   list = new ArrayList();


				sql =  " select * from ( select rownum rnum,  a.grcode, a.seq, a.types, a.title, a.contents, a.indate, ";
				sql += "			 a.inuserid, a.upfile, a.luserid, a.ldate, a.cnt,  ";
				sql += "			 a.subjectseq, a.categorycd, a.openyn, a.recommend , ";
			    sql += "			 case when NVL(a.subjectseq,0) >0   then b.subject else c.codenm end as subject, ";
				sql += "			 d.realfile, d.savefile, d.fileseq ";
				sql += " from   tz_openforum a ";
				sql += "			 left outer join  tz_openforumsubject b on  a.grcode = b.grcode and a.subjectseq = b.subjectseq ";
				sql += "			 left outer join  tz_code c on  c.gubun = '0066' and c.code = a.categorycd ";
				sql += "			 left outer join  TZ_BOARDFILE d on  d.tabseq = '23' and a.seq = d.seq ";
			    sql += " where  a.grcode ='"+s_grcode+"'   and a.openyn = 'Y' "; // openyn �����õ������ ��
				sql += " order by a.indate desc ) where rnum < 2";

			   ls = connMgr.executeQuery(sql);
			   while (ls.next()) {
				   dbox = ls.getDataBox();
					realfileVector.addElement(dbox.getString("d_realfile"));
	                savefileVector.addElement(dbox.getString("d_savefile"));
	                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));

			       if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
				   if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
				   if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);
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

//	   /**
//		* ��� ��õ���� more
//		* @param box      receive from the form object and session
//		* @return isOk    1:delete success,0:delete fail
//		* @throws String
//		*/
//	   public ArrayList  SelectSubjectForumReview(RequestBox box) throws Exception {
//
//		  DBConnectionManager connMgr = null;
//		   ListSet ls = null;
//		   ArrayList list = null;
//		   String sql = "";
//		   DataBox dbox = null;
//
//		   String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
//
//		   try {
//			   connMgr = new DBConnectionManager();
//			   list = new ArrayList();
//
//
//
//
//			   ls = connMgr.executeQuery(sql);
//			   while (ls.next()) {
//				   dbox = ls.getDataBox();
//				   list.add(dbox);
//			   }
//		   }
//		   catch (Exception ex) {
//				  ErrorManager.getErrorStackTrace(ex, box, sql);
//			   throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
//		   }
//		   finally {
//			   if(ls != null) { try { ls.close(); }catch (Exception e) {} }
//			   if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
//		   }
//		   return list;
//			}

    /**
    *��������Ʈ
    * @param box          receive from the form object and session
    * @return ArrayList   QNA ����Ʈ
    * @throws Exception
    */
    public ArrayList SelectForumSubjectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
		String head_sql="";
		String body_sql="";
		String group_sql="";
		String order_sql="";
		String count_sql="";

        String sql1 = "";
        DataBox dbox = null;

        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
		String v_select     = box.getString("p_select");
		int v_pageno        = box.getInt("p_pageno");
		int v_seq           = box.getInt("p_seq");
		String v_types = box.getString("p_types");

		 try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();


			if ("A".equals(v_types) )
			{
						//��������
						head_sql = " select   a.grcode, a.seq, a.types, a.title, a.contents, a.indate, ";
						head_sql += "          a.inuserid, a.inusernm, a.upfile,  a.ldate, a.cnt,   ";
						head_sql += "          a.subjectseq, a.categorycd, a.openyn, a.recommend , b.subject, ";
						head_sql += "		   d.realfile, d.savefile, d.fileseq ";
						head_sql += " from    tz_openforum a ";
						head_sql += "			left outer join  tz_openforumsubject b on  a.grcode = b.grcode and a.subjectseq = b.subjectseq ";
						head_sql += "			left outer join  TZ_BOARDFILE d on  d.tabseq = '23' and a.seq = d.seq ";
						head_sql += " where  a.grcode ='"+s_grcode+"'  ";
						System.out.println("v_select "+v_select);
						if( !v_select.equals("") ) head_sql += " and a.subjectseq ='"+ v_select +"'";

						head_sql += "            and  a.types ='"+v_types+"'  ";
						head_sql += " order by a.seq desc ";
			            ls = connMgr.executeQuery(head_sql);

			}
			else if ("B".equals(v_types) )
			{
			           //��������
	                    head_sql = " select   a.grcode, a.seq, a.types, a.title, a.contents, a.indate, ";
					    head_sql += "          a.inuserid, a.inusernm, a.upfile,  a.ldate, a.cnt,  ";
						head_sql += "          a.subjectseq, a.categorycd, a.openyn, a.recommend , c.codenm as subject, ";
						head_sql += "			 d.realfile, d.savefile, d.fileseq ";
						head_sql += " from   tz_openforum a 		";
			            head_sql += "		   left outer join  tz_code c on  c.gubun = '0066' and c.code = a.categorycd ";
						head_sql += "		   left outer join  TZ_BOARDFILE d on  d.tabseq = '23' and a.seq = d.seq ";
						head_sql += " where  a.grcode ='"+s_grcode+"'  ";
						head_sql += "     and  a.types ='"+v_types+"'  ";
						head_sql += " order by a.seq desc ";
						ls = connMgr.executeQuery(head_sql);

			}
			else if ("C".equals(v_types) )	// �����õ����
			{
						head_sql =  " select a.grcode, a.seq, a.types, a.title, a.contents, a.indate, ";
						head_sql += "			 a.inuserid, a.upfile, a.luserid, a.ldate, a.cnt,    ";
						head_sql += "			 a.subjectseq, a.categorycd, a.openyn, a.recommend , ";
						head_sql += "			 case when NVL(a.subjectseq,0) >0   then b.subject else c.codenm end as subject, ";
						head_sql += "			 d.realfile, d.savefile, d.fileseq ";
						head_sql += " from   tz_openforum a ";
						head_sql += "			 left outer join  tz_openforumsubject b on  a.grcode = b.grcode and a.subjectseq = b.subjectseq ";
						head_sql += "			 left outer join  tz_code c on  c.gubun = '0066' and c.code = a.categorycd ";
						head_sql += "			 left outer join  TZ_BOARDFILE d on  d.tabseq = '23' and a.seq = d.seq ";
						head_sql += " where  a.grcode ='"+s_grcode+"'   and a.openyn = 'Y' "; // openyn �����õ������ ��

						if( !v_select.equals("") ) head_sql += " and a.subjectseq ='"+ v_select +"'";

						head_sql += " order by a.indate desc ";
						ls = connMgr.executeQuery(head_sql);
			}
			else if( "D".equals( v_types ) ) 							// ���λѸ��� top 1
			{
						head_sql =  " select * from (select rownum rnum, a.grcode, a.seq, a.types, a.title, a.contents, a.indate, ";
						head_sql += "			 a.inuserid, a.upfile upfile, a.luserid, a.ldate, a.cnt,     ";
						head_sql += "			 a.subjectseq, a.categorycd, a.openyn, a.recommend , ";
						head_sql += "			 case when NVL(a.subjectseq,0) >0   then b.subject else c.codenm end as subject, ";
						head_sql += "			 d.realfile, d.savefile, d.fileseq ";
						head_sql += " from   tz_openforum a ";
						head_sql += "			 left outer join  tz_openforumsubject b on  a.grcode = b.grcode and a.subjectseq = b.subjectseq ";
						head_sql += "			 left outer join  tz_code c on  c.gubun = '0066' and c.code = a.categorycd ";
						head_sql += "			 left outer join  TZ_BOARDFILE d on  d.tabseq = '23' and a.seq = d.seq ";
						head_sql += " where  a.grcode ='"+s_grcode+"'  ";
						head_sql += " order by a.indate desc ) where rnum < 2";
						ls = connMgr.executeQuery(head_sql);
			}

			count_sql = " select  count(*) ";
			count_sql += " from   tz_openforum a 		";
			count_sql += " where  a.grcode ='"+s_grcode+"'  ";

			if("C".equals(v_types) ){									// ��� ��õ����
				count_sql += "     and  a.openyn ='Y'  ";
			}else{														// A = �������� , B= ��������
				count_sql += "     and  a.types ='"+v_types+"'  ";
			}
			System.out.println("head_sql = "+head_sql);
			if("C".equals(v_types) ) row = 7;

			int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);     // ��ü row ���� ��ȯ�Ѵ�
            ls.setPageSize(row);             				//  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count);   //     ������������ȣ�� �����Ѵ�.
			int total_page_count = ls.getTotalPage();       //     ��ü ������ ���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();

				realfileVector.addElement(dbox.getString("d_realfile"));
                savefileVector.addElement(dbox.getString("d_savefile"));
                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));

				if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
				if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
				if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

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
	   * ���õ� �ڷ�� �Խù� �󼼳��� select
	   * @param box      receive from the form object and session
	   * @return isOk    1:delete success,0:delete fail
	   * @throws Exception
	   */
	  public DataBox SelectView(RequestBox box) throws Exception {
		   DBConnectionManager connMgr = null;
		   ListSet ls = null;
		   String sql = "";
		   DataBox dbox = null;

		   int v_seq = box.getInt("p_seq");
		   String v_types = box.getString("p_types");
		   String v_fileseq = box.getString("p_fileseq");
           String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));


		   try {
			       connMgr = new DBConnectionManager();
					sql = " select    a.grcode, a.seq, a.types, a.title, a.contents, a.indate, ";
					sql += "			 a.inuserid, a.upfile, a.luserid, a.ldate, a.cnt,  ";
					sql += "			 a.subjectseq, a.categorycd, a.openyn, a.recommend , a.commentcnt,  ";
					sql += "			 case when NVL(a.subjectseq,0) >0   then b.subject else c.codenm end as subject, ";
					sql += "			 d.realfile, d.savefile, d.fileseq ";
					sql += " from   tz_openforum a   ";
					sql += "			 left outer join  tz_openforumsubject b on  a.grcode = b.grcode and a.subjectseq = b.subjectseq ";
					sql += "			 left outer join  tz_code c on  c.gubun = '0066' and c.code = a.categorycd ";
					sql += "			 left outer join  TZ_BOARDFILE d on  d.tabseq = '23' and a.seq = d.seq ";
					sql += "  where  a.grcode ='"+s_grcode+"'   ";
					sql += "      and a.seq =  "+v_seq ;
					sql += "      and a.types =  '"+v_types+"'   ";

      			   ls = connMgr.executeQuery(sql);

			   while(ls.next()) {
				   dbox = ls.getDataBox();
					realfileVector.addElement(dbox.getString("d_realfile"));
	                savefileVector.addElement(dbox.getString("d_savefile"));
	                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
			   }

				if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
				if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
				if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);

			   sql = "update tz_openforum set cnt = cnt + 1 ";
			   sql += "  where grcode ='"+s_grcode+"'   ";
			   sql += "      and seq =  "+v_seq ;
			   sql += "      and types =  '"+v_types+"'   ";
			   connMgr.executeUpdate(sql);
		   }
		   catch (Exception ex) {
			   ErrorManager.getErrorStackTrace(ex, box, sql);
			   throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		   }
		   finally {
			   if(ls != null) {try {ls.close();} catch(Exception e){}}
			   if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		   }
		   return dbox;
	   }

	/**
	  * ������ ����Ʈ
	  * @param box          receive from the form object and session
	  * @return ArrayList
	  * @throws Exception
	  */
	  public ArrayList selectcommentList(RequestBox box) throws Exception {
		  DBConnectionManager connMgr = null;
		  ListSet ls = null;
		  ArrayList list = null;
		  String sql = "";
		  String head_sql = "";
		  String body_sql = "";
		  String group_sql = "";
		  String order_sql = "";
		  String count_sql = "";

		  DataBox dbox = null;

		  String v_searchtext = box.getString("p_searchtext");
		  String v_select     = box.getString("p_select");
		  int v_pageno        = box.getInt("p_pageno");
		  int v_seq           = box.getInt("p_seq");
		  String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
		  String v_types = box.getString("p_types");

		  try {
			  connMgr = new DBConnectionManager();
			  list = new ArrayList();
			  // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate
			  head_sql += " select a.seq,a.types,a.commentseq,a.inuserid,a.commentqna,a.cdate, a.inusernm ";
			  body_sql += "   from TZ_OPENFORUMCOMMENT a  ";
			  body_sql += "  where  a.grcode ='"+s_grcode+"'   ";
			  body_sql += "  and a.seq = " + v_seq;
			  body_sql += "  and a.types = '" + v_types + "'";
			  order_sql += " order by  a.commentseq asc  ";

			  sql= head_sql+ body_sql+group_sql+ order_sql;
			  ls = connMgr.executeQuery(sql);

			  count_sql= "select count(*) "+ body_sql;
			  int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);    //     ��ü row ���� ��ȯ�Ѵ�
			  int total_page_count = ls.getTotalPage();       //     ��ü ������ ���� ��ȯ�Ѵ�

			  ls.setPageSize(row);             				//  �������� row ������ �����Ѵ�
			  ls.setCurrentPage(v_pageno,total_page_count);   //     ������������ȣ�� �����Ѵ�.

			  while (ls.next()) {
				  dbox = ls.getDataBox();
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
		* ��õ�ϱ�
		* @param box      receive from the form object and session
		* @return isOk    1:insert success,0:insert fail
		* @throws Exception
		*/
		public int insertRecommend(RequestBox box) throws Exception {
			DBConnectionManager connMgr = null;
			String sql   = "";
			String v_seq = box.getString("p_seq");
			String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
			String v_types = box.getString("p_types");
			String v_review = box.getString("Review");

			int isOk=0;

			try {
				connMgr = new DBConnectionManager();

				if( v_review.equals("Y") ){
					sql = " update tz_openforum set openyn = 'Y' ";
				}else{
					sql = " update tz_openforum set recommend = NVL(recommend,0) + 1 ";
				}
				sql += "  where  grcode ='"+s_grcode+"'   ";
				sql += "  and seq = " + v_seq;
				sql += "  and types = '" + v_types + "'";
				isOk = connMgr.executeUpdate(sql);

			}
			catch (Exception ex) {
				ErrorManager.getErrorStackTrace(ex, box, sql);
				throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
			}
			finally {
				if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
			}
			return isOk;
		}

	/**
	  * ������ ����Ҷ�
	  * @param box      receive from the form object and session
	  * @return isOk    1:insert success,0:insert fail
	  * @throws Exception
	  */
	   public int insertComment(RequestBox box) throws Exception {
		 DBConnectionManager connMgr = null;
		  ListSet ls = null;
		  PreparedStatement pstmt1 = null;
		  String sql = "";
		  String sql1 = "";
		  String sql2 = "";
		  int isOk1 = 1;
		  int isOk2 = 1;
		  int v_cnt = 0;

		  String v_commentqna =  box.getString("commentqna");
		  int v_seq = box.getInt("p_seq");
		  String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
		  String v_types = box.getString("p_types");

		  String s_userid = "";
		  String s_usernm = box.getSession("name");
		  String s_gadmin = box.getSession("gadmin");
           s_userid = box.getSession("userid");
		  String v_isopen  = "Y";



		  try {
			  connMgr = new DBConnectionManager();
			  connMgr.setAutoCommit(false);
			  //----------------------   �Խ��� ������ ��ȣ�� �����´� ----------------------------
			  sql = "select NVL(max(commentseq), 0) from TZ_OPENFORUMCOMMENT";
			  sql += "  where  grcode ='"+s_grcode+"'   ";
			  sql += "  and seq = " + v_seq;
			  sql += "  and types = '" + v_types + "'";

			  ls = connMgr.executeQuery(sql);
			  ls.next();
			  int v_commentseq = ls.getInt(1) + 1;
			  ls.close();
			  //------------------------------------------------------------------------------------

			  //////////////////////////////////   �Խ��� table �� �Է�  ///////////////////////////////////////////////////////////////////
			  sql1 =  "insert into TZ_OPENFORUMCOMMENT(grcode, seq, types, commentseq, inuserid,inusernm, commentqna, cdate)";
			  sql1 += "                values (?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

			  pstmt1 = connMgr.prepareStatement(sql1);
			  pstmt1.setString   (1, s_grcode);
			  pstmt1.setInt   (2, v_seq);
			  pstmt1.setString(3, v_types);
			  pstmt1.setInt   (4, v_commentseq);
			  pstmt1.setString(5,  s_userid);
		      pstmt1.setString(6,  s_usernm);
			  pstmt1.setString(7,  v_commentqna);

			  isOk1 = pstmt1.executeUpdate();
			  pstmt1.close();

			  if(isOk1 > 0 ) {

				sql = " update tz_openforum set commentcnt = NVL(commentcnt,0) + 1 ";
				sql += "  where  grcode ='"+s_grcode+"'   ";
				sql += "  and seq = " + v_seq;
				sql += "  and types = '" + v_types + "'";
				pstmt1 = connMgr.prepareStatement(sql);
				isOk1 = pstmt1.executeUpdate();
				pstmt1.close();
			  }

			  if(isOk1 > 0 ) {

				  if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			  }
			  else {
                if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            }
		  }
		  catch (Exception ex) {
			  if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			  ErrorManager.getErrorStackTrace(ex, box, sql1);
			  throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		  }
		  finally {
			  if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			  if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			  if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			  if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		  }
		  return isOk1;
	  }


	  /**
	  * ��� �����Ҷ�
	  * @param box      receive from the form object and session
	  * @return isOk    1:delete success,0:delete fail
	  * @throws Exception
	  */
	  public int deleteComment(RequestBox box) throws Exception {
		  DBConnectionManager connMgr = null;
		  ListSet ls = null;
		  Connection conn = null;
		  PreparedStatement pstmt = null;
		  String sql = "";
		  int isOk1 = 1;

		  int    v_seq          = box.getInt("p_seq");
		  String v_types        = box.getString("p_types");
		  int    v_commentseq   = box.getInt("p_commentseq");
	    	String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
		  try {
			  connMgr = new DBConnectionManager();

			 sql  = " delete from TZ_OPENFORUMCOMMENT    ";
			 sql += "  where  grcode ='"+s_grcode+"'   ";
			 sql += "  and seq = " + v_seq;
			 sql += "  and types = '" + v_types + "'";
 			 sql += "  and  commentseq  = " + v_commentseq + "";
			  pstmt = connMgr.prepareStatement(sql);
			  isOk1 = pstmt.executeUpdate();
			  pstmt.close();
			if ( isOk1 > 0 )
			{

				sql = " update tz_openforum set commentcnt = NVL(commentcnt,0) - 1 ";
				sql += "  where  grcode ='"+s_grcode+"'   ";
				sql += "  and seq = " + v_seq;
				sql += "  and types = '" + v_types + "'";
				pstmt = connMgr.prepareStatement(sql);
				isOk1 = pstmt.executeUpdate();
				pstmt.close();

			}


		  }
		  catch (Exception ex) {
			  if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			  ErrorManager.getErrorStackTrace(ex, box, sql);
			  throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		  }
		  finally {
			  if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			  if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
		  }
		  return isOk1;
	  }
	/**
	   * ���� �� ����Ҷ�
	   * @param box      receive from the form object and session
	   * @return isOk    1:insert success,0:insert fail
	   * @throws Exception
	   */
		public int insertKnowBoard(RequestBox box) throws Exception {
		   DBConnectionManager connMgr = null;
		   ListSet ls = null;
		   PreparedStatement pstmt1 = null;
		   String sql = "";
		   String sql1 = "";
		   String sql2 = "";
		   int isOk1 = 1;
		   int isOk2 = 1;
		   int v_cnt = 0;

		   int    v_tabseq = box.getInt("p_tabseq");
		   int 	  v_subjectseq = 0;
		   String v_types  = box.getString("p_types");
		   String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

		   String v_catecd = "";      // ī�װ�
		   String v_title  = box.getString("p_title");
		   String v_contents =  StringManager.replace(box.getString("content"),"<br>","\n");
		   String v_upfile = box.getString("p_file1");

		   String s_userid = "";
		   String s_usernm = box.getSession("name");
		   String s_gadmin = box.getSession("gadmin");
		   s_userid = box.getSession("userid");

		   if( v_types.equals("A") ){
			   v_catecd = box.getString("p_catecd");      // ���� ī�װ�
			   v_subjectseq = box.getInt("p_subjectseq");
		   }else if( v_types.equals("B") ){
			   v_catecd = box.getString("p_catenm");      // �������� �з��ڵ� ī�װ�
		   }

		   try {
			   connMgr = new DBConnectionManager();
			   connMgr.setAutoCommit(false);

			   //----------------------   �Խ��� ��ȣ �����´� ----------------------------
			   sql = "select NVL(max(seq), 0) from tz_openforum  ";
			   sql += "  where  grcode ='"+s_grcode+"'   ";
			   sql += "  and types = '" + v_types + "'";

			   ls = connMgr.executeQuery(sql);
			   ls.next();
			   int v_seq = ls.getInt(1)+1;
			   ls.close();

			   //////////////////////////////////   �Խ��� table �� �Է�  ///////////////////////////////////////////////////////////////////
			   sql1 =  "insert into tz_openforum (grcode,  seq, types, title, contents, indate, inuserid, inusernm, luserid, ldate, subjectseq, cnt, categorycd, recommend, commentcnt, upfile )                      ";
//			   sql1 += "                values (?, ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?, 0, 0, ?) ";
			   sql1 += "                values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?, 0, 0, ?) ";

int index = 1;
			   pstmt1 = connMgr.prepareStatement(sql1);
			   pstmt1.setString(index++,  s_grcode);
			   pstmt1.setInt   (index++,  v_seq);
			   pstmt1.setString(index++,  v_types);
			   pstmt1.setString(index++,  v_title);
pstmt1.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
//			   pstmt1.setString(index++,  v_contents);
			   pstmt1.setString(index++,  s_userid);
			   pstmt1.setString(index++,  s_usernm);
			   pstmt1.setString(index++,  s_userid);
			   pstmt1.setInt   (index++,  v_subjectseq);
			   pstmt1.setInt   (index++, v_cnt);
			   pstmt1.setString(index++, v_catecd);
			   pstmt1.setString(index++, v_upfile);

			   isOk1 = pstmt1.executeUpdate();     //      ���� �ش� content �� empty_clob()�� �����ϰ� ���� ���� ��Ʈ������ ġȯ�Ѵ�.

			   isOk2 = this.insertUpFile(connMgr, v_seq, v_tabseq, box);
//          	sql2 = "select contents from tz_openforum where grcode = " + s_grcode + " and  seq = " + v_seq+ " and types = '"+v_types+"'";
//            connMgr.setOracleCLOB(sql2, v_contents);       //      (��Ÿ ���� ���)

			   if(isOk1 > 0 ) {
				   if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			   }
			   else {
                if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            }
		   }
		   catch (Exception ex) {
			   if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			   ErrorManager.getErrorStackTrace(ex, box, sql1);
			   throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		   }
		   finally {
			   if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			   if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			   if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			   if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		   }
		   return isOk1*isOk2;
	   }
		/**
		    *������ ����Ʈ
		    * @param box          receive from the form object and session
		    * @return ArrayList   QNA ����Ʈ
		    * @throws Exception
		    */
		    public ArrayList SelectTheme(RequestBox box) throws Exception {
		        DBConnectionManager connMgr = null;
		        ListSet ls = null;
		        ArrayList list = null;
		        String sql = "";
				String head_sql="";
				String body_sql="";
				String group_sql="";
				String order_sql="";
				String count_sql="";
				int isOk = 0;
				int premonth = 0;

		        String sql1  = "";
		        DataBox dbox = null;

				String v_process	= box.getString("p_process");
		        String s_grcode 	= box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
				String v_select     = box.getString("p_select");
				int v_pageno        = box.getInt("p_pageno");
				int v_seq           = box.getInt("p_seq");
				String v_types 		= box.getString("p_types");
				String v_month		= "";
				String v_year		= "";

				// ======= �⵵ �� ���ϱ� ========================
				Calendar cal 	= Calendar.getInstance();
				int year		= cal.get(Calendar.YEAR);
					v_year		= String.valueOf(year);
				int month 		= cal.get(Calendar.MONTH)+1;

				if( v_process.equals("insertThemePage") ) month = month+1;

				if(premonth <10)
			    	 v_month 	= "0"+String.valueOf(month);
			    else
					 v_month 	= String.valueOf(month);
				// ======= �⵵ �� ���ϱ� ========================



				 try {
		            connMgr = new DBConnectionManager();
		            list = new ArrayList();

						head_sql   =  " select a.grcode, a.subjectseq, a.subject, a.tyear, a.tmonth, a.luserid, a.ldate    ";
						body_sql  += " from   tz_openforumsubject  a ";
						body_sql  += "			 left outer join  tz_openforum b on  a.grcode = b.grcode and a.subjectseq = b.subjectseq ";
						body_sql  += "			 left outer join  tz_code c on  c.gubun = '0066' and c.code = b.categorycd ";
						body_sql  += " where  a.grcode ='"+s_grcode+"' " ;
						body_sql  += "		  and tyear = '"+year+"' and tmonth = '"+v_month+"' ";
						order_sql += " order by  a.subjectseq  desc";
						ls = connMgr.executeQuery(head_sql+body_sql);
		            while (ls.next()) {
		                dbox = ls.getDataBox();
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
		 * �̴��� ������ ���� ���� �˻�
		 * @param box       receive from the form object and session
		 * @return String   ���̵� ���� ���� �˻�
		 * @throws Exception
		 */

		public int checkTheme(RequestBox box) throws Exception {
			DBConnectionManager connMgr = null;
			PreparedStatement 	pstmt   = null;
			ArrayList 			list    = null;
		    ListSet   			ls		= null;
			int 				isOk	= 0;
			String 				sql		= "";
			String	v_tmonth = "";

			// ======= �⵵ �� ���ϱ� ========================
			Calendar cal 	= Calendar.getInstance();
			int year		= cal.get(Calendar.YEAR);
			String v_year		= String.valueOf(year);
			int month 		= cal.get(Calendar.MONTH)+1; // �� ��
			if(month <10)
				v_tmonth 	= "0"+String.valueOf(month);
		    else
				v_tmonth 	= String.valueOf(month);
			// ======= �⵵ �� ���ϱ� ========================

			String v_usermonth = box.getString("p_tmonth");
			String v_useryear  = box.getString("p_tyear");

		    try {
				connMgr = new DBConnectionManager();
				   sql = " select count(*) cnt from tz_openforumsubject where tyear = '"+v_useryear+"' and tmonth = '"+ v_usermonth +"' ";

					ls = connMgr.executeQuery(sql);
			        ls.next();
			        isOk = ls.getInt(1);
					box.put("p_tmpmonth", v_usermonth);
				return isOk;


			}catch (Exception ex) {
			    throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
			}
			finally {
				if(ls != null) {try {ls.close();} catch(Exception e){}}
			    if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
			}
		}

	/**
	   * ������ ����Ҷ�
	   * @param box      receive from the form object and session
	   * @return isOk    1:insert success,0:insert fail
	   * @throws Exception
	   */
		public int insertTheme(RequestBox box) throws Exception {
		   DBConnectionManager connMgr = null;
		   ListSet ls  = null;
		   ListSet ls1 = null;
		   PreparedStatement pstmt1 = null;
		   String sql = "";
		   String sql1 = "";
		   String sql2 = "";
		   int isOk  = 0;
		   int isOk1 = 1;
		   int isOk2 = 1;
		   int v_cnt = 0;
		   String v_tmonth = "";

		   String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
		   String v_title  = box.getString("p_title");

		   String s_userid = box.getSession("userid");
		   String s_usernm = box.getSession("name");
		   String s_gadmin = box.getSession("gadmin");

		   // ��¥���
		   Calendar cal = Calendar.getInstance();
		   int year = cal.get(Calendar.YEAR);
		   String v_year	= String.valueOf(year);
	       String v_month   = box.getString("p_tmpmonth");

			if(v_month.length() < 2)
				v_tmonth 	= "0"+String.valueOf(v_month);
		    else
				v_tmonth 	= String.valueOf(v_month);

			try {
			   connMgr = new DBConnectionManager();
			   connMgr.setAutoCommit(false);

				   // �Խ��� ��ȣ
				   sql = "select NVL(max(subjectseq), 0) from tz_openforumsubject   ";
				   sql += "  where  grcode ='"+s_grcode+"'   ";

				   ls = connMgr.executeQuery(sql);
				   ls.next();
				   int v_seq = ls.getInt(1)+1;
				   ls.close();
				   //////////////////////////////////   �Խ��� table �� �Է�  ///////////////////////////////////////////////////////////////////
				   sql1 =  "insert into tz_openforumsubject (grcode, subjectseq, subject, tyear, tmonth, luserid, ldate )                      ";
				   sql1 += "                values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

				   pstmt1 = connMgr.prepareStatement(sql1);
				   pstmt1.setString(1,  s_grcode);
				   pstmt1.setInt   (2,  v_seq);
				   pstmt1.setString(3,  v_title);
				   pstmt1.setString(4,  v_year);
				   pstmt1.setString(5,  v_tmonth);
				   pstmt1.setString(6,  s_userid);

				   isOk1 = pstmt1.executeUpdate();

			   if(isOk1 > 0 ) {
				   if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			   }
			   else {
                if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
            }
		   }
		   catch (Exception ex) {
			   if(connMgr != null) { try { connMgr.rollback(); }catch (Exception e10) {} }
			   ErrorManager.getErrorStackTrace(ex, box, sql1);
			   throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		   }
		   finally {
			   if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			   if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			   if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
			   if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		   }
		   return isOk1;
	   }


		/**
		    *������ ����Ʈ
		    * @param box          receive from the form object and session
		    * @return ArrayList   QNA ����Ʈ
		    * @throws Exception
		    */
		    public ArrayList SelectAllTheme(RequestBox box) throws Exception {
		        DBConnectionManager connMgr = null;
		        ListSet ls = null;
		        ArrayList list = null;
		        String sql = "";
				String head_sql="";
				String body_sql="";
				String group_sql="";
				String order_sql="";
				String count_sql="";
				int isOk = 0;
				int premonth = 0;

		        String sql1  = "";
		        DataBox dbox = null;

				String v_process	= box.getString("p_process");
		        String s_grcode 	= box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
				String v_select     = box.getString("p_select");
				int v_pageno        = box.getInt("p_pageno");
				int v_seq           = box.getInt("p_seq");
				String v_types 		= box.getString("p_types");
				String v_month		= "";
				String v_year		= "";

				 try {
		            connMgr = new DBConnectionManager();
		            list = new ArrayList();

						head_sql   =  " select a.grcode, a.subjectseq, a.subject, a.tyear, a.tmonth, a.luserid, a.ldate    ";
						body_sql  += " from   tz_openforumsubject  a ";
						body_sql  += "			 left outer join  tz_openforum b on  a.grcode = b.grcode and a.subjectseq = b.subjectseq ";
						body_sql  += "			 left outer join  tz_code c on  c.gubun = '0066' and c.code = b.categorycd ";
						body_sql  += " where  a.grcode ='"+s_grcode+"' " ;
//						body_sql  += "		  and tyear = '"+year+"' and tmonth = '"+v_month+"' ";
						order_sql += " order by  a.subjectseq  desc";
						ls = connMgr.executeQuery(head_sql+body_sql);
		            while (ls.next()) {
		                dbox = ls.getDataBox();
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

//		=========================================  ���� ���ε�  =====================================================

		/**
		 * �������� ���ο� �ڷ����� ���
		 * @param box      receive from the form object and session
		 * @return isOk    1:delete success,0:delete fail
		 * @throws Exception
		 */

			 public	int	insertUpFile(DBConnectionManager connMgr, int p_seq, int p_tabseq, RequestBox	box) throws	Exception {
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
					v_newFileName [i]  = box.getNewFileName(FILE_TYPE + (i+1));
				}
				//----------------------------------------------------------------------------------------------------------------------------

				String s_userid	= box.getSession("userid");

				try	{
//					 //----------------------	�ڷ� ��ȣ �����´� ----------------------------
//					sql	= "select NVL(max(fileseq),	0) from	tz_boardfile where tabseq = "+p_tabseq+" and seq = " +	p_seq ;
//
//					ls = connMgr.executeQuery(sql);
//					ls.next();
//					int	v_fileseq =	ls.getInt(1) + 1;
//					ls.close();
//
//					//------------------------------------------------------------------------------------

					//////////////////////////////////	 ���� table	�� �Է�	 ///////////////////////////////////////////////////////////////////
					sql2 =	"insert	into tz_boardfile(tabseq, seq, fileseq, realfile, savefile, luserid,	ldate)";
					sql2 +=	" values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

					pstmt2 = connMgr.prepareStatement(sql2);

					for(int	i =	0; i < FILE_LIMIT; i++)	{
						if(	!v_realFileName	[i].equals(""))	{		//		���� ���ε�	�Ǵ� ���ϸ�	üũ�ؼ� db�� �Է��Ѵ�
							pstmt2.setInt(1, p_tabseq);
							pstmt2.setInt(2, p_seq);
							pstmt2.setInt(3, 1);
							pstmt2.setString(4,	v_realFileName[i]);
							pstmt2.setString(5,	v_newFileName[i]);
							pstmt2.setString(6,	s_userid);
							isOk2 =	pstmt2.executeUpdate();
							//v_fileseq++;
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
			public int deleteUpFile(DBConnectionManager	connMgr, RequestBox box)	throws Exception {
				PreparedStatement pstmt3 = null;
				String sql	= "";
				String sql3	= "";
		        ListSet ls = null;
				int	isOk3 =	1;
				int	v_seq 	  =	box.getInt("p_seq");
				int v_tabseq  = box.getInt("p_tabseq");

				try	{
					sql3 = "delete from tz_boardfile where tabseq = "+ v_tabseq +" and seq = '"+v_seq+"' and fileseq = '1'  ";
					pstmt3 = connMgr.prepareStatement(sql3);
					isOk3 =	pstmt3.executeUpdate();
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

//		 =========================================================================================================

}



