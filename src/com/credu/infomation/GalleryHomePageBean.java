//**********************************************************
//  1. ��      ��:  ����
//  2. ���α׷��� : Bean.java
//  3. ��      ��:  ����
//  4. ȯ      ��: JDK 1.5
//  5. ��      ��: 1.0
//  6. ��      ��: __����__ 2009. 10. 19
//  7. ��      ��: __����__ 2009. 10. 19
//**********************************************************
package com.credu.infomation;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.course.EduGroupData;
import com.credu.homepage.NoticeData;
import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.namo.SmeNamoMime;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class GalleryHomePageBean {

	private ConfigSet config;
    private int row;

	public GalleryHomePageBean() {
	    try{
            config = new ConfigSet();
            //row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        �� ����� �������� row ���� �����Ѵ�
            row = Integer.parseInt(config.getProperty("page.manage.row"));  //�� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	}

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

		 String v_type    = box.getStringDefault("p_type","");
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
			 sql += "    and comp    = " + StringManager.makeSQL(v_comp);
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


//=========�����ȭ�� ����Ʈ ����=========

	/**
	* ��ü����  ����Ʈ
	* @param box          receive from the form object and session
	* @return ArrayList   ������ ������ ����Ʈ
	* @throws Exception
	*/
	public ArrayList selectList(RequestBox box) throws Exception {
		
		DBConnectionManager connMgr = null;
		ListSet 			ls 		= null;
		ArrayList 			list 	= null;
		StringBuffer 	headSql 	= new StringBuffer();
		StringBuffer 	bodySql 	= new StringBuffer();
		String 			orderSql 	= "";
		String 			countSql 	= "";
		String 			sql			= "";
		DataBox 		dbox 		= null;

		String 			v_search     = box.getString("p_search");
		String 			v_searchtext = box.getString("p_searchtext");

		int 			v_tabseq 	= box.getInt("p_tabseq");
		
		int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 8 : box.getInt("p_pagesize");
        
        String 			v_orderColumn = box.getString("p_orderColumn");
        String 			v_orderType = box.getString("p_orderType");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			headSql.append(" SELECT                                   \n ");
			headSql.append("         ROWNUM                           \n ");
			headSql.append("         , BOARD.TABSEQ                   \n ");
			headSql.append("         , BOARD.SEQ                      \n ");
			headSql.append("         , BOARD.TITLE                    \n ");
			headSql.append("         , BOARD.USERID                   \n ");
			headSql.append("         , BOARD.NAME                     \n ");
			headSql.append("         , BOARD.CNT                      \n ");
			headSql.append("         , BOARD.INDATE                   \n ");
			headSql.append("         , BOARD.FLAGYN                   \n ");
			headSql.append("         , BOARD.POSITION                 \n ");
			headSql.append("         , FILES.SAVEFILE                 \n ");         
			headSql.append("         , FILES.REALFILE                 \n ");
			
			bodySql.append(" FROM    TZ_GALLERY     BOARD             \n ");
			bodySql.append("         , TZ_BOARDFILE FILES             \n ");
			bodySql.append(" WHERE   BOARD.TABSEQ 	= FILES.TABSEQ(+) \n ");
			bodySql.append(" AND     BOARD.SEQ 		= FILES.SEQ(+)    \n ");
			bodySql.append(" AND	 BOARD.TABSEQ	= "+StringManager.makeSQL(""+v_tabseq)+" \n ");
			bodySql.append(" AND     BOARD.FLAGYN   = 'Y'             \n ");
			

			if ( !v_searchtext.equals("")) {      //    �˻�� ������                                         
				if (v_search.equals("title")) {                          //    �������� �˻��Ҷ�  
					bodySql.append(" AND TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");  
				} else if (v_search.equals("position")) {                //    �������� �˻��Ҷ�  
					bodySql.append(" AND POSITION LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");
				}                                                                                   
			}  

			//bodySql.append(" AND     BOARD.TABSEQ    =  " +  v_tabseq + " \n ");
			
			if(!v_orderColumn.equals("")){
				orderSql = " ORDER BY " + v_orderColumn + " " + v_orderType;
			} else {
				orderSql = " ORDER BY SEQ DESC";	
			}
			
			sql = headSql.toString() + bodySql.toString() + orderSql;
			
			ls = connMgr.executeQuery(sql);
			 
			countSql= "SELECT COUNT(*) "+ bodySql.toString();
			
			int totalrowcount = BoardPaging.getTotalRow(connMgr, countSql) ;
            int total_page_count = ls.getTotalPage();  	//��ü ������ ���� ��ȯ�Ѵ�
            ls.setPageSize(v_pagesize);                   	//�������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, totalrowcount);    //������������ȣ�� �����Ѵ�.
            
            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum",		new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage",		new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount",  	new Integer(row));
                dbox.put("d_totalrowcount",	new Integer(totalrowcount));
                
                list.add(dbox);
            }
            
			/*int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);	//     ��ü row ���� ��ȯ�Ѵ�
			//ls.setPageSize(row);                       							//�������� row ������ �����Ѵ�
            ls.setPageSize(v_pagesize);  										//�������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count);   					//     ������������ȣ�� �����Ѵ�.
            
            System.out.println("v_pageno : " + v_pageno);
            System.out.println("d_dispnum : "+(total_row_count - ls.getRowNum() + 1));
            System.out.println("d_totalpage : "+ls.getTotalPage());
            System.out.println("d_rowcount : "+v_pagesize);
            System.out.println("d_totalrowcount : "+total_row_count);
			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(v_pagesize));
                dbox.put("d_totalrowcount",	new Integer(total_row_count));
				list.add(dbox);
			}
*/
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
	* ȭ�� �󼼺���
	* @param box          receive from the form object and session
	* @return ArrayList   ��ȸ�� ������
	* @throws Exception
	*/
   public DataBox selectView(RequestBox box) throws Exception {
	 
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");
		String v_seq = box.getString("p_seq");
		String v_process = box.getString("p_process");

		Vector realfileVector = new Vector();
		Vector savefileVector = new Vector();
		Vector fileseqVector  = new Vector();

		try {
			connMgr = new DBConnectionManager();

			sql.append(" SELECT                                   \n ");
			sql.append("         BOARD.TABSEQ                     \n ");
			sql.append("         , BOARD.SEQ                      \n ");
			sql.append("         , BOARD.TITLE                    \n ");
			sql.append("         , BOARD.CONTENT                  \n ");
			sql.append("         , BOARD.USERID                   \n ");
			sql.append("         , BOARD.NAME                     \n ");
			sql.append("         , BOARD.CNT                      \n ");
			sql.append("         , BOARD.LDATE                    \n ");
			sql.append("         , BOARD.POSITION                 \n ");
			sql.append("         , BOARD.FLAGYN                   \n ");
			sql.append("         , FILES.REALFILE                 \n ");
			sql.append("         , FILES.SAVEFILE                 \n ");
			sql.append("         , FILES.FILESEQ                  \n ");
			sql.append(" FROM    TZ_GALLERY       	BOARD         \n ");
			sql.append("         , TZ_BOARDFILE   	FILES         \n ");
			sql.append(" WHERE   BOARD.TABSEQ   = FILES.TABSEQ(+) \n ");
			sql.append(" AND     BOARD.SEQ      = FILES.SEQ(+)    \n ");
			sql.append(" AND     BOARD.TABSEQ   = " + v_tabseq + "\n");
			sql.append(" AND     BOARD.SEQ      = "+ StringManager.makeSQL(v_seq)+" \n");
			
			ls = connMgr.executeQuery(sql.toString());
			while (ls.next()) {
				
				dbox = ls.getDataBox();
			}
			
			// ��ȸ�� ����
			if(v_process.equals("selectView")){
			  String sql1 = "UPDATE TZ_GALLERY SET CNT = CNT + 1 WHERE SEQ = " + v_seq+" AND TABSEQ   = " + v_tabseq;
			  connMgr.executeUpdate(sql1);
			}
			
			
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("Sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) {try {ls.close();} catch(Exception e){}}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return dbox;
	}
}
