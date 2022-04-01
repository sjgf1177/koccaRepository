//**********************************************************
//1. ��      ��: ���� �Ŀ����ͺ� (������, �����)
//2. ���α׷���: KOpenPowerBean
//3. ��      ��: �Ŀ����ͺ�
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 1.0
//6. ��      ��:
//7. ��      ��:
//**********************************************************
package com.credu.homepage;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
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
import com.namo.active.NamoMime;

public class KOpenPowerHomePageBean {

  private ConfigSet config;

	public KOpenPowerHomePageBean() {
      try{
          config = new ConfigSet();
      }
      catch(Exception e) {
          e.printStackTrace();
      }
  }

		/**
	    *  ����Ʈȭ�� select (������)
	    * @param    box          receive from the form object and session
	    * @return ArrayList  �ڷ�� ����Ʈ
	    * @throws Exception
	    */
	    public ArrayList selectList(RequestBox box) throws Exception {
	    	DBConnectionManager connMgr = null;
	        ArrayList 	list 	= null;
	        ListSet 	ls 		= null;
	        DataBox 	dbox 	= null;
	        
	        String 			sql    	  = "";
	        StringBuffer 	headSql   = new StringBuffer();
	        StringBuffer 	bodySql   = new StringBuffer();
	        String 			countSql  = "";
	        String 			orderSql  = "";

	        
	        int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
	        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");
			
	        String v_process  = box.getString("p_process");
			
	        String v_searchtext = box.getString("p_searchtext");
	        String v_search     = box.getString("p_search");

	        try {
	            connMgr = new DBConnectionManager();

	            list = new ArrayList();

				headSql.append(" SELECT  A.SEQ, A.TITLE, A.COMPTEXT, A.USERID, A.LNAME  \n ");
	            headSql.append("         , A.SAVEFILE, A.REALFILE, A.INDATE, A.TARGET  \n ");
	            headSql.append("         , A.USEYN, A.CNT, B.CODENM TARGETNM           \n ");
	            bodySql.append(" FROM    TZ_SUPERIORITY A, TZ_CODE B                   \n ");
	            bodySql.append(" WHERE   A.TARGET    = B.CODE(+)                       \n ");
	            bodySql.append(" AND     B.GUBUN(+)  = '0090'                          \n ");
	            bodySql.append(" AND     A.USEYN     = 'Y'                          \n ");

	            if ( !v_searchtext.equals("")) {                //    �˻�� ������

	                if (v_search.equals("title")) {              //    �̸����� �˻��Ҷ�
	                	bodySql.append(" and title like lower(" + StringManager.makeSQL("%" + v_searchtext + "%")+") \n");
	                }
	                else if (v_search.equals("all")) {        //    �������� �˻��Ҷ�
	                	bodySql.append(" and (title like lower(" + StringManager.makeSQL("%" + v_searchtext + "%")+") \n");
	                	bodySql.append(" or contents like (" + StringManager.makeSQL("%" + v_searchtext + "%")+") )\n");
	                }
	             
	            }

	            orderSql += " order by seq desc";
	            sql= headSql.toString() + bodySql.toString() + orderSql;
				ls = connMgr.executeQuery(sql);

				countSql= "select count(*) "+ bodySql.toString();
				int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);

	            ls.setPageSize(v_pagesize);                       		//  �������� row ������ �����Ѵ�
	            ls.setCurrentPage(v_pageno, total_row_count);	// ������������ȣ�� �����Ѵ�.
	            int totalpagecount = ls.getTotalPage();    		// ��ü ������ ���� ��ȯ�Ѵ�

	            while (ls.next()) {
	                dbox = ls.getDataBox();
	                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
	                dbox.put("d_totalpage", new Integer(totalpagecount));
	                dbox.put("d_rowcount", new Integer(10));
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
	   * ���õ�  �Խù� �󼼳��� select
	   * @param box          receive from the form object and session
	   * @return ArrayList   ��ȸ�� ������
	   * @throws Exception
	   */
	   public DataBox selectView(RequestBox box) throws Exception {
	        DBConnectionManager connMgr = null;
	        ListSet ls = null;
	        StringBuffer sql = new StringBuffer();
	        DataBox dbox = null;
	        String v_upcnt = "Y";

	        int 	v_seq		= box.getInt("p_seq");
	        String	v_process 	= box.getString("p_process"); 

	        try {
	            connMgr = new DBConnectionManager();

	            sql.append(" SELECT  A.SEQ, A.TITLE, A.CONTENTS, A.SAVEFILE, A.CNT      \n ");
	            sql.append("         , A.REALFILE, A.USEYN, A.COMPTEXT, A.TARGET        \n ");
	            sql.append("         , A.USERID, A.LNAME, A.INDATE, B.CODENM TARGETNM   \n ");
	            sql.append(" FROM    TZ_SUPERIORITY A, TZ_CODE B                        \n ");
	            sql.append(" WHERE   A.TARGET    = B.CODE(+)                            \n ");
	            sql.append(" AND     B.GUBUN(+)  = '0090'                               \n ");
	            sql.append(" AND     SEQ         = " + v_seq);                                 

	            ls = connMgr.executeQuery(sql.toString());

	            for (int i = 0; ls.next(); i++) {

	                dbox = ls.getDataBox();

	            }

	            if (v_process.equals("selectView")){
	                connMgr.executeUpdate("update TZ_SUPERIORITY set cnt = cnt + 1 where seq = "+ v_seq);
	            }
	        }
	        catch (Exception ex) {
	            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
	            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
	        }
	        finally {
	            if(ls != null) {try {ls.close();} catch(Exception e){}}
	            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
	        }
	        return dbox;
	    }

}