//**********************************************************
//1. ��      ��:
//2. ���α׷���: CommunityCreateBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��:
//
//**********************************************************

package com.credu.community;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.common.GetCodenm;
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

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
@SuppressWarnings("unchecked")
public class CommunityQnABean {
	private ConfigSet config;
	private static int row=10;
	private static final String FILE_TYPE = "p_file";           //      ���Ͼ��ε�Ǵ� tag name
	private static final int FILE_LIMIT = 1;                    //    �������� ���õ� ����÷�� ����


	public void CommuniytQnABean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //�� ����� �������� row ���� �����Ѵ�
			row = 10; //������ ����
			//System.out.println("....... row.....:"+row);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void CommuniytQnABean(String type) {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * �������� ����Ʈ
	 * @param box          receive from the form object and session
	 * @return ArrayList   QNA ����Ʈ
	 * @throws Exception
	 */
	public ArrayList selectDirectList(RequestBox box) throws Exception {
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

		String v_searchtext = box.getString("p_searchtext");
		String v_select     = box.getString("p_select");
		System.out.println("v_searchtext :"+v_searchtext);
		System.out.println("v_select :"+v_select);
		String v_faq_type   = box.getStringDefault("p_faq_type", "DIRECT");
		int v_pageno        = box.getInt("p_pageno");

		try {
			connMgr = new DBConnectionManager();
			/* 2005.11.16_�ϰ��� : Oracle -> mssql rownum ����
            sql  = "\n select a.*,rownum rowseq from ("
                 + "\n select a.faq_type faq_type,a.faqno faqno,a.title title,a.content content,a.read_cnt read_cnt"
                 + "\n       ,a.add_cnt add_cnt,a.parent parent,a.lv lv,a.position position"
                 + "\n       ,a.register_userid register_userid,a.register_dte register_dte"
                 + "\n       ,a.modifier_userid modifier_userid,a.modifier_dte modifier_dte"
                 + "\n       ,b.userid userid,b.resno resno,b.name name,b.email email,b.deptnam deptnam,b.jikupnm jikupnm,b.jikwinm jikwinm"
                 + "\n      ,NVL(c.cnt,0) cnt "
                 + "\n   from tz_cmufaq a,tz_member b "
                 + "\n       ,(select faq_type faq_type,faqno faqno, count(*) cnt from tz_cmufaqfile where faq_type = '"+v_faq_type+"' group by faq_type,faqno) c"
                 + "\n  where a.register_userid = b.userid "
                 // ������ : 05.11.09 ������ : �̳��� _(+) ����
//               + "\n    and a.faq_type        = c.faq_type(+)"
//               + "\n    and a.faqno           = c.faqno(+)"
                 + "\n    and a.faq_type        =  c.faq_type(+) "
                 + "\n    and a.faqno           =  c.faqno(+) "
                 + "\n    and a.faq_type        = '"+v_faq_type+"'"
                 ;
			 */
			head_sql  = "select a.faq_type faq_type,a.faqno faqno,a.title title,a.content content,a.read_cnt read_cnt";
			head_sql += "\n       ,a.add_cnt add_cnt,a.parent parent,a.lv lv,a.position position";
			head_sql += "\n       ,a.register_userid register_userid,a.register_dte register_dte";
			head_sql += "\n       ,a.modifier_userid modifier_userid,a.modifier_dte modifier_dte";
			head_sql += "\n       ,b.userid userid,b.resno resno,b.name name,b.email email,b.deptnam deptnam,b.jikupnm jikupnm,b.jikwinm jikwinm";
			head_sql += "\n      ,NVL(c.cnt,0) cnt ";
			body_sql += "\n   from tz_cmufaq a,tz_member b ";
			body_sql += "\n       ,(select faq_type faq_type,faqno faqno, count(*) cnt from tz_cmufaqfile where faq_type = '"+v_faq_type+"' group by faq_type,faqno) c";
			body_sql += "\n  where a.register_userid = b.userid "  ;
			body_sql += "\n    and a.faq_type        =  c.faq_type(+) " ;
			body_sql += "\n    and a.faqno           =  c.faqno(+) ";
			body_sql += "\n    and a.faq_type        = '"+v_faq_type+"'";

			if ( !v_searchtext.equals("")) {      // �˻�� ������
				if (v_select.equals("title"))   body_sql += "\n and lower(a.title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%")+")";
				if (v_select.equals("content")) body_sql += "\n and a.content like " + StringManager.makeSQL("%" + v_searchtext + "%");            //   Oracle 9i ��
				if (v_select.equals("name"))    body_sql += "\n and lower(b.name) like lower (" +  StringManager.makeSQL("%" + v_searchtext + "%")+")";            //   Oracle 9i ��
			}

			order_sql += "\n order by a.root desc,a.position asc";

			sql= head_sql+ body_sql+ order_sql;

			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

			ls.setPageSize(row);                         // �������� row ������ �����Ѵ�
			ls.setCurrentPage(v_pageno, total_row_count);                 // ������������ȣ�� �����Ѵ�.
			int total_page_count = ls.getTotalPage();    // ��ü ������ ���� ��ȯ�Ѵ�
			//int total_row_count = ls.getTotalCount();    // ��ü row ���� ��ȯ�Ѵ�

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
	 * QNAȭ�� ����Ʈ
	 * @param box          receive from the form object and session
	 * @return ArrayList   QNA ����Ʈ
	 * @throws Exception
	 */
	public ArrayList selectListQna(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet             ls      = null;
		ArrayList           list    = new ArrayList();
		// ������ : 05.11.15 ������ : �̳��� _ rownum ����
		String              sql      	= "";
		StringBuffer        headSql     = new StringBuffer();
		StringBuffer        bodySql     = new StringBuffer();
		StringBuffer        bodySql2    = new StringBuffer();
		StringBuffer        bodySql3    = new StringBuffer();
		String              countSql   = "";

		DataBox             dbox    = null;

		String v_searchtext = box.getString("p_searchtext");
		String v_select     = box.getString("p_select");

		String v_faq_type   = box.getStringDefault("p_faq_type", "DIRECT");
		int v_pageno        = box.getInt("p_pageno");


		String  s_grcode	 = box.getSession("tem_grcode");
		//		String  v_grtype	 = GetCodenm.get_grtype(box,s_grcode);

		try {
			connMgr = new DBConnectionManager();
			headSql.append(" SELECT /* CommunityQnABean.selectListQna : 199 */\n	ROWNUM ROWSEQ, X.*\n ");
			headSql.append(" FROM    (\n ");
			headSql.append("             SELECT  A.FAQ_TYPE, A.FAQNO, A.TITLE, A.CONTENT\n ");
			headSql.append("                     , A.READ_CNT, A.ADD_CNT,A.PARENT, A.LV\n ");
			headSql.append("                     , A.POSITION, A.REGISTER_USERID, A.REGISTER_DTE\n ");
			headSql.append("                     , A.MODIFIER_USERID, A.MODIFIER_DTE, B.USERID\n ");
			headSql.append("                     , B.NAME, B.EMAIL, B.DEPTNAM, B.JIKUPNM, B.JIKWINM\n ");
			headSql.append("                     , NVL(C.CNT,0) FILE_CNT\n ");
			bodySql.append("             FROM    TZ_CMUFAQ A\n ");
			bodySql.append("                     , TZ_MEMBER B\n ");
			bodySql.append("                     , (\n ");
			bodySql.append("                         SELECT  FAQ_TYPE ,FAQNO , COUNT(*) CNT\n ");
			bodySql.append("                         FROM    TZ_CMUFAQFILE\n ");
			bodySql.append("                         WHERE   FAQ_TYPE = 'CMUQNA'\n ");
			bodySql.append("                         GROUP BY FAQ_TYPE,FAQNO\n ");
			bodySql.append("                     ) C\n ");
			bodySql.append("             WHERE   A.REGISTER_USERID = B.USERID\n ");
			bodySql.append("             AND     A.FAQ_TYPE        =  C.FAQ_TYPE(+)\n ");
			bodySql.append("             AND     A.FAQNO           =  C.FAQNO(+)\n ");
			bodySql.append("             AND     A.FAQ_TYPE        = "+ StringManager.makeSQL(v_faq_type)+ "\n ");
			bodySql.append("             AND     B.GRCODE          = "+ StringManager.makeSQL(s_grcode)+ "\n ");
			bodySql2.append("         ORDER BY A.ROOT DESC,A.POSITION ASC\n ");
			bodySql3.append(" ) X\n ");


			if ( !v_searchtext.equals("")) {      // �˻�� ������
				if (v_select.equals("title"))   bodySql.append("\n and lower(a.title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%")+")\n");
				if (v_select.equals("content")) bodySql.append("\n and a.content like " + StringManager.makeSQL("%" + v_searchtext + "%")+"\n");
				if (v_select.equals("name"))    bodySql.append("\n and lower(b.name) like lower (" +  StringManager.makeSQL("%" + v_searchtext + "%")+")\n");
			}

			sql= headSql.toString()+ bodySql.toString()+ bodySql2.toString()+bodySql3.toString();

			ls = connMgr.executeQuery(sql);
			countSql= "select count(*) "+ bodySql.toString();
			int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);

			ls.setPageSize(row);                        	 // �������� row ������ �����Ѵ�
			ls.setCurrentPage(v_pageno,total_row_count);	  	 // ������������ȣ�� �����Ѵ�.
			int total_page_count = ls.getTotalPage();    	 // ��ü ������ ���� ��ȯ�Ѵ�
			//int total_row_count = ls.getTotalCount();    	 // ��ü row ���� ��ȯ�Ѵ�

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
	 * �������� ��ȸ
	 * @param box          receive from the form object and session
	 * @return ArrayList   �������� ��ȸ
	 * @throws Exception
	 */
	public ArrayList selectViewQna(RequestBox box,String qryFlag) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt   = null;
		ListSet             ls      = null;
		ArrayList           list1   = new ArrayList();
		ArrayList           list    = new ArrayList();

		Vector realfileVector = new Vector();
		Vector savefileVector = new Vector();
		Vector fileseqVector  = new Vector();

		StringBuffer        sql     = new StringBuffer();
		DataBox             dbox    = null;

		String v_faq_type   = box.getStringDefault("p_faq_type", "DIRECT");
		//String v_grtype     = box.getStringDefault("p_grtype", "KICPA");
		int    v_faqno      = box.getInt("p_faqno");

		try {
			connMgr = new DBConnectionManager();

			//��ȸ�� ����.
			if("VIEW".equals(qryFlag)){
				sql.append(" update tz_cmufaq set read_cnt =read_cnt+1");
				sql.append("  where faq_type        = '"+v_faq_type+"'");
				sql.append("    and faqno           = "+v_faqno);

				pstmt = connMgr.prepareStatement(sql.toString());
				pstmt.executeUpdate();
			}

			sql.setLength(0);

			//���������б�
			sql.append(" SELECT  A.FAQ_TYPE, A.FAQNO, A.TITLE, A.CONTENT, A.READ_CNT\n");
			sql.append("         , A.ADD_CNT ,A.PARENT ,A.LV ,A.POSITION ,A.ROOT\n");
			sql.append("         , A.REGISTER_USERID, A.REGISTER_DTE, A.MODIFIER_USERID\n");
			sql.append("         , A.MODIFIER_DTE, B.USERID, B.RESNO, B.NAME, B.EMAIL\n");
			sql.append("         , B.DEPTNAM, B.JIKUPNM, B.JIKWINM\n");
			sql.append("         , C.FILENO FILESEQ, C.SAVEFILE, C.REALFILE\n");
			sql.append("  FROM   TZ_CMUFAQ A ,TZ_MEMBER B, TZ_CMUFAQFILE C\n");
			sql.append(" WHERE   A.REGISTER_USERID = B.USERID\n");
			sql.append("   AND   A.FAQ_TYPE        =  C.FAQ_TYPE(+)\n");
			sql.append("   AND   A.FAQNO           =  C.FAQNO(+)\n");
			sql.append("   AND   A.FAQ_TYPE        = '"+v_faq_type+"'\n");
			sql.append("   AND   A.FAQNO           = '"+v_faqno+"'\n");

			ls = connMgr.executeQuery(sql.toString());
			while (ls.next()) {
				dbox = ls.getDataBox();
				if( !dbox.getString("d_realfile").equals("")){
					realfileVector.addElement(dbox.getString("d_realfile"));
					savefileVector.addElement(dbox.getString("d_savefile"));
					fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
				}

			}

			if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
			if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
			if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);

			list1.add(dbox);

			// if(ls != null) { try { ls.close(); }catch (Exception e) {} }

			// sql.setLength(0);
			//
			// sql.append(" SELECT  Y.*\n ");
			// sql.append("         , CASE WHEN ROWSEQ < "+v_rowseq+" THEN 'NEXT'\n ");
			// sql.append("                ELSE 'PREV'\n ");
			// sql.append("         END GUBUN\n ");
			// sql.append(" FROM    (\n ");
			// sql.append("             SELECT  ROWNUM ROWSEQ, X.*\n ");
			// sql.append("             FROM    (\n ");
			// sql.append("                     SELECT  A.FAQ_TYPE, A.FAQNO, A.TITLE\n ");
			// sql.append("                     FROM   TZ_CMUFAQ A\n ");
			// sql.append("                     WHERE   A.FAQ_TYPE = '"+v_faq_type+"'\n ");
			// //sql.append("                     AND   A.GRTYPE     = '"+v_grtype+"'\n ");
			// sql.append("                     ORDER BY A.ROOT DESC, A.POSITION ASC\n ");
			// sql.append("                     ) X\n ");
			// sql.append("         ) Y\n ");
			// sql.append(" WHERE   ROWSEQ = "+(v_rowseq - 1 )+"\n ");
			// sql.append(" OR      ROWSEQ = "+(v_rowseq + 1 )+"\n ");
			//
			// ls = connMgr.executeQuery(sql.toString());
			//
			// while (ls.next()) {
			//     dbox = ls.getDataBox();
			//     list2.add(dbox);
			// }
			//
			// if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			//
			//sql.setLength(0);
			//
			// //����б�
			//sql.append(" SELECT  A.FAQ_TYPE, A.FAQNO, A.RPLNO, A.CONTENT, A.USERID\n");
			//sql.append("         , A.REGISTER_DTE, A.MODIFIER_DTE, B.RESNO, B.NAME\n");
			//sql.append("         , B.EMAIL, B.DEPTNAM, B.JIKUPNM, B.JIKWINM\n");
			//sql.append("   FROM  TZ_CMUFAQREPLAY A, TZ_MEMBER B\n");
			//sql.append("  WHERE  A.USERID    = B.USERID\n");
			//sql.append("    AND  A.FAQ_TYPE  = '"+v_faq_type+"'\n");
			//sql.append("    AND  A.FAQNO     = "+v_faqno+"\n");
			//sql.append("  ORDER BY A.RPLNO DESC\n");
			//
			// ls = connMgr.executeQuery(sql.toString());
			// while (ls.next()) {
			//     dbox = ls.getDataBox();
			//     list3.add(dbox);
			// }

			list.add(list1);
			//list.add(list2);
			//list.add(list3);
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
	 * Q&A����ϱ�
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int insertQnA(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet     ls          = null;
		String      sql         = "";

		int         isOk1       = 1;
		int         isOk2       = 1;
		int         v_faqno     = 0;


		String v_faq_type  = box.getString("p_faq_type");
		String v_content   = StringManager.replace(box.getString("content"),"<br>","\n");
		String s_userid    = box.getSession("userid");

		String  s_grcode	 = box.getSession("tem_grcode");
		String  v_grtype	 = GetCodenm.get_grtype(box,s_grcode);

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			//qna faqno��ȣ�� �����Ѵ�.
			sql = "select NVL(max(faqno), 0) from tz_cmufaq where faq_type = '" +v_faq_type+ "'";
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				v_faqno = ls.getInt(1) + 1;
			}
			int tmp =0;
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			sql = "select NVL(max(position), 0) from tz_cmufaq where faq_type = '" +v_faq_type+ "'";
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				tmp = ls.getInt(1) + 1;
			}
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			sql  =" insert into tz_cmufaq ( faq_type     , faqno          , title       , content   , read_cnt"
				+"                       , add_cnt      , lv          , position  , register_userid"
				+"                       , register_dte , modifier_userid, modifier_dte, del_fg,parent,root, grtype)"
				//
				//                +"               values  (?,?,?,empty_clob(),?"
				+"               values  (?,?,?,?,?"
				+"                       ,?,?,?,?"
				+"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),?"
				+"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N',?,?,?)"
				;
			int index = 1;
			pstmt = connMgr.prepareStatement(sql);

			pstmt.setString(index++, v_faq_type                         );//faq�з�
			pstmt.setInt   (index++, v_faqno                            );//�Ϸù�ȣ
			pstmt.setString(index++, box.getString("p_title" )          );//����
			pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			// pstmt.setString(index++, v_content          );
			pstmt.setInt   (index++, 0                                  );//��ȸ��
			pstmt.setInt   (index++, 0                                  );//��õ��
			pstmt.setInt   (index++, 1         );//�亯����
			pstmt.setInt   (index++, tmp         );//�亯��ġ
			pstmt.setString(index++, s_userid);//�Խ���
			pstmt.setString(index++, s_userid );//������
			pstmt.setInt   (index++, v_faqno                             );//�θ�
			pstmt.setInt   (index++, v_faqno                             );//�θ�
			pstmt.setString(index++, v_grtype                             );//Grtype
			isOk1 = pstmt.executeUpdate();

			//           String sql1 = "select content from tz_cmufaq where faq_type = '" + v_faq_type + "' and faqno ="+v_faqno;
			//           connMgr.setOracleCLOB(sql1, v_content);
			isOk2 = this.insertUpFile(connMgr,v_faq_type, v_faqno, box);

			if(isOk1 > 0 && isOk2 > 0) {
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
		return isOk1*isOk2;
	}


	/**
	 * Q&A�亯����ϱ�
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int replyQnA(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet     ls          = null;
		String      sql         = "";

		int         isOk1       = 1;
		int         isOk2       = 1;
		int         isOk3       = 1;
		int         v_thisfaqno =0;


		String v_faq_type  	= box.getString("p_faq_type");
		int    v_faqno     	= box.getInt("p_faqno");
		int    v_lv        	= box.getInt("p_lv");
		int    v_position  	= box.getInt("p_position");
		int    v_root  		= box.getInt("p_root");
		String v_content   	= StringManager.replace(box.getString("content"),"<br>","\n");
		String s_userid    	= box.getSession("userid");

		String  s_grcode	 = box.getSession("tem_grcode");
		String  v_grtype	 = GetCodenm.get_grtype(box, s_grcode);

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql  =" update tz_cmufaq set    position = position+1"
				+ "  where faq_type        = ?"
				//              +"     and parent          = ?"
				+"     and position        > ?"
				;
			pstmt = connMgr.prepareStatement(sql);

			pstmt.setString(1, v_faq_type                         );//faq�з�
			//           pstmt.setInt   (2, v_parent                            );//�θ�
			pstmt.setInt   (2, v_position                            );//�亯��ġ
			pstmt.executeUpdate();

			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }

			//qna faqno��ȣ�� �����Ѵ�.
			sql = "select NVL(max(faqno), 0) from tz_cmufaq where faq_type = '" +v_faq_type+ "'";
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				v_thisfaqno = ls.getInt(1) + 1;
			}
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			sql  =" insert into tz_cmufaq ( faq_type     , faqno          , title       , content   , read_cnt"
				+"                       , add_cnt      , lv          , position  , register_userid"
				+"                       , register_dte , modifier_userid, modifier_dte, del_fg,parent,root, grtype)"
				//2005.11.16_�ϰ��� : Oracle -> Mssql empty_clob() ����
				//                +"               values  (?,?,?,empty_clob(),?"
				+"               values  (?,?,?,?,?"
				+"                       ,?,?,?,?"
				+"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),?"
				+"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N',?,?, ?)"
				;
			int index = 1;
			pstmt = connMgr.prepareStatement(sql);

			pstmt.setString(index++, v_faq_type                         );//faq�з�
			pstmt.setInt   (index++, v_thisfaqno                            );//�Ϸù�ȣ
			pstmt.setString(index++, box.getString("p_title" )          );//����
			pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			//		   pstmt.setString(index++, v_content          );
			pstmt.setInt   (index++, 0                                  );//��ȸ��
			pstmt.setInt   (index++, 0                                 );//��õ��
			pstmt.setInt   (index++, v_lv+1         );//�亯����
			//           pstmt.setInt   (index++, tmp         );//�亯��ġ
			pstmt.setInt   (index++, v_position+1         );//�亯��ġ

			pstmt.setString(index++, s_userid);//�Խ���
			pstmt.setString(index++, s_userid );//������
			pstmt.setInt   (index++, v_faqno                            );//�θ�
			pstmt.setInt   (index++, v_root                            );//�θ�
			pstmt.setString(index++, v_grtype );
			isOk2 = pstmt.executeUpdate();

			/* 2005.11.16_�ϰ��� : Oracle -> Mssql empty_clob() ����
			 */
			//           String sql1 = "select content from tz_cmufaq where faq_type = '" + v_faq_type + "' and faqno ="+v_faqno;
			//           connMgr.setOracleCLOB(sql1, v_content);
			isOk3 = this.insertUpFile(connMgr,v_faq_type, v_thisfaqno, box);
			if(isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}
			System.out.println("isOk1 :"+isOk1);
			System.out.println("isOk2 :"+isOk2);
			System.out.println("isOk3 :"+isOk3);

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
	 * Q&A�����ϱ�
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int updateQnA(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet     ls          = null;
		String      sql         = "";

		int         isOk1       = 1;
		int         isOk2       = 1;

		String v_faq_type  = box.getString("p_faq_type");
		int    v_faqno     = box.getInt("p_faqno");
		String v_content   = StringManager.replace(box.getString("content"),"<br>","\n");
		String s_userid    = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql  =" update tz_cmufaq set    title   = ?"
				//2005.11.16_�ϰ��� : Oracle -> Mssql empty_clob() ����
				//			   +"                       , content = empty_clob()"
				+"                       , content = ?"
				+"                       , modifier_userid = ?"
				+"                       , modifier_dte =to_char(sysdate, 'YYYYMMDDHH24MISS')"
				+ "  where faq_type        = ?"
				+ "    and faqno           = ?"
				;
			int index = 1;
			pstmt = connMgr.prepareStatement(sql);

			pstmt.setString(index++, box.getString("p_title" )          );//����
			pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
			//		   pstmt.setString(index++, v_content);
			pstmt.setString(index++, s_userid);//�Խ���
			pstmt.setString(index++, v_faq_type                         );//faq�з�
			pstmt.setInt   (index++, v_faqno                            );//�Ϸù�ȣ

			isOk1 = pstmt.executeUpdate();

			//           String sql1 = "select content from tz_cmufaq where faq_type = '" + v_faq_type + "' and faqno ="+v_faqno;
			//           connMgr.setOracleCLOB(sql1, v_content);
			isOk2 = this.insertUpFile(connMgr,v_faq_type, v_faqno, box);
			if(isOk1 > 0 && isOk2 > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}
			/*2005.11.16_�ϰ��� : Oracle -> Mssql empty_clob() ����
			 */
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
	 * QNA ���ο� �ڷ����� ���
	 * @param box      receive from the form object and session
	 * @return isOk    1:delete success,0:delete fail
	 * @throws Exception
	 */

	public int insertUpFile(DBConnectionManager connMgr, String faq_type, int faqno, RequestBox    box) throws Exception {
		ListSet           ls      = null;
		PreparedStatement pstmt   = null;
		String            sql     = "";
		String            sql2    = "";
		int               isOk2   = 1;
		int               vfileno = 0;
		//----------------------   ���ε�Ǵ� ������ ������ �˰� �ڵ��ؾ��Ѵ�  --------------------------------
		String [] v_realFile     = new String [FILE_LIMIT];
		String [] v_saveFile     = new String [FILE_LIMIT];

		for(int i = 0; i < FILE_LIMIT; i++) {
			v_realFile[i] = box.getRealFileName(FILE_TYPE + (i+1));
			v_saveFile[i] = box.getNewFileName (FILE_TYPE + (i+1));
		}
		String s_userid = box.getSession("userid");

		try {

			sql  =" insert into tz_cmufaqfile ( faq_type, faqno, fileno, realfile, savepath"
				+"                       , savefile, filesize, register_userid, register_dte, modifier_userid, modifier_dte)"
				+"               values  (?,?,?,?,''"
				+"                       ,?,null,?"
				+"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),?"
				+"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'))"
				;
			pstmt = connMgr.prepareStatement(sql);
			for(int    i = 0; i < FILE_LIMIT; i++) {
				if (!v_realFile    [i].equals("")&& "".equals(box.getString("p_fileno"+(i+1))) )   {
					//���Ϲ�ȣ����ȣ�� �����Ѵ�.
					sql2 = "select NVL(max(fileno), 0) from tz_cmufaqfile where faq_type = '" +faq_type+ "' and faqno ="+faqno;
					ls = connMgr.executeQuery(sql2);
					while (ls.next()) {
						vfileno = ls.getInt(1) + 1;
					}
					System.out.println("...............vfileno:"+vfileno);
					ls.close();
					pstmt.setString(1, faq_type       );//faq�з�
					pstmt.setInt   (2, faqno          );//�Ϸù�ȣ
					pstmt.setInt   (3, vfileno        );//�����Ϸù�ȣ
					pstmt.setString(4, v_realFile[i]  );//�������ϸ�
					pstmt.setString(5, v_saveFile[i]  );//�������ϸ�
					pstmt.setString(6, s_userid       );//�Խ���
					pstmt.setString(7, s_userid       );//������
					isOk2 = pstmt.executeUpdate();
				}
			}
		}
		catch (Exception ex) {
			FileManager.deleteFile(v_saveFile, FILE_LIMIT);     //  �Ϲ�����, ÷������ ������ ����..
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
	 * ���õ� �ڷ����� DB���� ����
	 * @param connMgr           DB Connection Manager
	 * @param box               receive from the form object and session
	 * @param p_filesequence    ���� ���� ����
	 * @return
	 * @throws Exception
	 */
	public int deleteUpFile( RequestBox box)    throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet           ls      = null;
		String            sql     = "";
		int               isOk2   = 1;


		String v_faq_type  = box.getString("p_faq_type");
		int    v_faqno     = box.getInt("p_faqno");
		int    v_fileno    = box.getInt("p_delfileno");
		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql  =" delete from  tz_cmufaqfile "
				+ "  where faq_type        = ?"
				+ "    and faqno           = ?"
				+ "    and fileno          = ?"
				;
			pstmt = connMgr.prepareStatement(sql);
			pstmt.setString(1, v_faq_type       );//faq�з�
			pstmt.setInt   (2, v_faqno          );//�Ϸù�ȣ
			pstmt.setInt   (3, v_fileno        );//�����Ϸù�ȣ
			isOk2 = pstmt.executeUpdate();
			if(isOk2 > 0) {
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql  + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk2;
	}


	/**
	 * Q&A ��۵���ϱ�
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int insertQnAMemo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet     ls          = null;
		String      sql         = "";

		int         isOk1       = 1;
		int         isOk2       = 1;
		int         v_rplno     = 0;


		String v_faq_type  = box.getString("p_faq_type");
		int    v_faqno     = box.getInt("p_faqno");
		String s_userid    = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			//qna faqno��ȣ�� �����Ѵ�.
			sql = "select NVL(max(rplno), 0) from tz_cmufaqreplay where faq_type = '" +v_faq_type+ "' and faqno = "+v_faqno;
			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				v_rplno = ls.getInt(1) + 1;
			}
			sql  =" insert into tz_cmufaqreplay ( faq_type     , faqno          , rplno       , content   , userid"
				+"                       , register_dte , modifier_dte, del_fg)"
				+"               values  (?,?,?,?,?"
				+"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS')"
				+"                       ,to_char(sysdate, 'YYYYMMDDHH24MISS'),'N')"
				;
			pstmt = connMgr.prepareStatement(sql);

			pstmt.setString(1, v_faq_type                         );//faq�з�
			pstmt.setInt   (2, v_faqno                            );//�Ϸù�ȣ
			pstmt.setInt   (3, v_rplno                            );//��۹�ȣ
			pstmt.setString(4, box.getString("p_rep_content" )          );//����
			pstmt.setString(5, s_userid);//�Խ���
			isOk1 = pstmt.executeUpdate();
			if(isOk1 > 0) {
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
		return isOk1*isOk2;
	}

	/**
	 * Q&A ��ۻ����ϱ�
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int deleteQnAMemo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt = null;
		ListSet     ls          = null;
		String      sql         = "";

		int         isOk1       = 1;
		int         isOk2       = 1;


		String v_faq_type  = box.getString("p_faq_type");
		int    v_faqno     = box.getInt("p_faqno");
		int    v_rplno     = box.getInt("p_rplno");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql = "delete from tz_cmufaqreplay where faq_type = '" +v_faq_type+ "' and faqno = "+v_faqno+" and rplno="+v_rplno;
			pstmt = connMgr.prepareStatement(sql);
			isOk1 = pstmt.executeUpdate();
			System.out.println("isOk1:"+isOk1);
			if(isOk1 > 0) {
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
		return isOk1*isOk2;
	}


	/**
	 * Q&A �ۻ����ϱ�
	 * @param box      receive from the form object and session
	 * @return isOk    1:insert success,0:insert fail
	 * @throws Exception
	 */
	public int deleteQnAData(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement   pstmt1 = null;
		PreparedStatement   pstmt2 = null;
		PreparedStatement   pstmt3 = null;
		ListSet     ls          = null;
		String      sql         = "";

		int         isOk1       = 1;
		int         isOk2       = 1;
		int         isOk3       = 1;

		String v_faq_type  = box.getString("p_faq_type");
		int    v_faqno     = box.getInt("p_faqno");
		Vector savefile    = box.getVector("p_savefile");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			//��ۻ���
			sql = "delete from tz_cmufaqreplay where faq_type = ? and faqno = ?";
			System.out.println(sql);
			pstmt1 = connMgr.prepareStatement(sql);
			pstmt1.setString(1, v_faq_type);
			pstmt1.setInt   (2, v_faqno);
			isOk1 = pstmt1.executeUpdate();
			System.out.println("isOk[0]:"+isOk1);

			//���ϻ�������
			sql = "delete from tz_cmufaqfile where faq_type = ? and faqno = ?";
			pstmt2 = connMgr.prepareStatement(sql);
			pstmt2.setString(1, v_faq_type);
			pstmt2.setInt   (2, v_faqno);
			isOk2 = pstmt2.executeUpdate();

			//��������
			sql = "delete from tz_cmufaq where faq_type = ? and faqno = ?";
			pstmt3 = connMgr.prepareStatement(sql);
			pstmt3.setString(1, v_faq_type);
			pstmt3.setInt   (2, v_faqno);
			isOk3 = pstmt3.executeUpdate();


			System.out.println("isOk[1]:"+isOk2);
			System.out.println("isOk[2]:"+isOk3);

			if(isOk1 > 0&& isOk2 > 0&& isOk3 > 0) {
				if (savefile != null) {
					FileManager.deleteFile(savefile);         //     ÷������ ����
				}
				if(connMgr != null) { try { connMgr.commit(); }catch (Exception e10) {} }
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
		return 1;
	}

	/**
	 * �Խ��� ��ȣ�ޱ�
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
			//str += "    <td width='100%' align='center' valign='middle'>";

			if (pagelist.previous()) {
				str += "<td align='right' valign='middle'><a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "')\"><img src=\"/images/user/button/pre.gif\" border=\"0\" align=\"middle\"></a></td>  ";
			}else{
				str += "<td align='right' valign='middle'><img src=\"/images/user/button/pre.gif\" border=\"0\" align=\"middle\"></td>";
			}


			for (int i=pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
				if (i == currPage) {
					str += "<td align='center' valign='middle'><strong>" + i + "</strong>" + "</td>";
				} else {
					str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + i + "')\">" + i + "</a></td> ";
				}
			}

			if (pagelist.next()) {
				str += "<td align='left' valign='middle'><a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "')\"><img src=\"/images/user/button/next.gif\"  border=\"0\" align=\"middle\"></a></td>";
			}else{
				str += "<td align='left' valign='middle'><img src=\"/images/user/button/next.gif\" border=\"0\" align=\"middle\"></td>";
			}

			/* if (str.equals("")) {
                str += "<�ڷᰡ �����ϴ�.";
            }
			 */
			// str += "    </td>";
			// str += "    <td width='15%' align='center'>";

			// str += "    </td>";
			str += "</tr>";
			str += "</table>";
		}
		return str;
	}
}
