//**********************************************************
//  1. 제      목: 나의 포인트
//  2. 프로그램명 : MyPointBean.java
//  3. 개      요:  나의 포인트 
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009. 11. 30
//  7. 수      정: 2009. 11. 30
//**********************************************************
package com.credu.point;

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
import com.namo.SmeNamoMime;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class PointManageBean {

	private ConfigSet config;
    private int row;

	public PointManageBean() {
	    try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	}

    /**
    보유포인트 
    @param box      receive from the form object and session
    @return ArrayList
    **/
     public ArrayList selectHavePointList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls         = null;
        ArrayList list     = null;
		DataBox dbox = null;
		String v_grcode = box.getSession("tem_grcode");
        String sql         = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String 		 orderSql = "";
        String       countSql = "";
        String  v_user_id   = box.getStringDefault("p_userid", "lee1");
        
        int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");


		try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            headSql.append("select  tid  \n ");
            headSql.append("      , getdate  \n ");
            headSql.append("      , getpoint  \n ");
            headSql.append("      , case when expiredate < to_char(sysdate, 'YYYYMMDD') then '유효기간 만료' \n  ");
            headSql.append("             else rtrim(ltrim(to_char(nvl(usepoint, 0), '9,999,999,999')))    \n ");
            headSql.append("        end as usepoint    \n ");
            headSql.append("      , case when expiredate < to_char(sysdate, 'YYYYMMDD') then 0    \n ");
            headSql.append("             else getpoint - nvl(usepoint, 0) end as lefrpoint    \n ");
            headSql.append("      , title    \n ");
            headSql.append("      , subj    \n ");
            headSql.append("      , year    \n ");
            headSql.append("      , subjseq    \n ");
            headSql.append("      , luserid    \n ");
            headSql.append("      , ldate    \n ");
            headSql.append("      , expiredate    \n ");
            bodySql.append("from    tz_pointget    \n ");
            bodySql.append("where   userid = ").append(SQLString.Format(v_user_id));
            orderSql	= "order by getdate desc  \n ";
            
            sql = headSql.toString() + bodySql.toString() + orderSql;
			
            ls = connMgr.executeQuery(sql);
            
            countSql= "select count(*) "+ bodySql.toString();
			
			int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);	//     전체 row 수를 반환한다
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize);             		//  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.
			//
            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount",	new Integer(total_row_count));
                list.add(dbox);
            }
        }            
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;        
    }

     /**
     적립포인트
     @param box      receive from the form object and session
     @return ArrayList
     **/
      public ArrayList selectStoldPointList(RequestBox box) throws Exception {
         DBConnectionManager connMgr = null;
         ListSet ls         = null;
         ArrayList list     = null;
 		DataBox dbox = null;
 		String v_grcode = box.getSession("tem_grcode");
 		String sql         = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String 		 orderSql = "";
        String       countSql = "";
         String  v_user_id   = box.getString("p_userid");
         
         int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
         int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

 		try {
             connMgr = new DBConnectionManager();
             list = new ArrayList();

             headSql.append(" SELECT   tid, userid, getpoint, getdate, title, subj, YEAR, subjseq, luserid, \n ");
        	 headSql.append("         ldate, \n ");
        	 headSql.append("         (SELECT CASE multiyn \n ");
        	 headSql.append("                    WHEN 'Y' \n ");
        	 headSql.append("                       THEN (SELECT subjprice \n ");
        	 headSql.append("                               FROM tz_billing \n ");
        	 headSql.append("                              WHERE tid = a.tid \n ");
        	 headSql.append("                                AND subj = a.subj \n ");
        	 headSql.append("                                AND year = a.year \n ");
        	 headSql.append("                                AND subjseq = a.subjseq \n ");
        	 headSql.append("                                AND userid = a.userid) \n ");
        	 headSql.append("                    ELSE subjprice \n ");
        	 headSql.append("                 END \n ");
        	 headSql.append("            FROM tz_billinfo \n ");
        	 headSql.append("           WHERE tid = a.tid) subjprice, \n ");
        	 headSql.append("         (SELECT CASE multiyn \n ");
        	 headSql.append("                    WHEN 'Y' \n ");
        	 headSql.append("                       THEN (SELECT price \n ");
        	 headSql.append("                               FROM tz_billing \n ");
        	 headSql.append("                              WHERE tid = a.tid \n ");
        	 headSql.append("                                AND subj = a.subj \n ");
        	 headSql.append("                                AND year = a.year \n ");
        	 headSql.append("                                AND subjseq = a.subjseq \n ");
        	 headSql.append("                                AND userid = a.userid) \n ");
        	 headSql.append("                    ELSE price \n ");
        	 headSql.append("                 END \n ");
        	 headSql.append("            FROM tz_billinfo \n ");
        	 headSql.append("           WHERE tid = a.tid) price \n ");
        	 bodySql.append("    FROM tz_pointget a \n ");
        	 bodySql.append("   WHERE userid = ").append(SQLString.Format(v_user_id));
        	 orderSql	= "ORDER BY getdate DESC ";
 			
        	 sql = headSql.toString() + bodySql.toString() + orderSql;                                       
             
        	 ls = connMgr.executeQuery(sql);                                                                 
        	                                                                                                 
        	 countSql= "select count(*) "+ bodySql.toString();                                               
        	                                                                                                 
        	 int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);	//     전체 row 수를 반환한다       
        	 int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다                           
        	 ls.setPageSize(v_pagesize);             		//  페이지당 row 갯수를 세팅한다                           
        	 ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.                           
//        	                                                                                               
        	 while (ls.next()) {                                                                             
        	     dbox = ls.getDataBox();                                                                     
        	     dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));                   
        	     dbox.put("d_totalpage", new Integer(ls.getTotalPage()));                                    
        	     dbox.put("d_rowcount", new Integer(row));                                                   
        	     dbox.put("d_totalrowcount",	new Integer(total_row_count));                                  
        	     list.add(dbox);                                                                             
        	 }                                                                                               
         }            
         catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, box, sql);
             throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
         }
         finally {
             if(ls != null) { try { ls.close(); }catch (Exception e) {} }
             if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
         }
         return list;        
     }
	
      /**
      사용포인트 
      @param box      receive from the form object and session
      @return ArrayList
      **/
       public ArrayList selectUsePointList(RequestBox box) throws Exception {
          DBConnectionManager connMgr = null;
          ListSet ls         = null;
          ArrayList list     = null;
  		DataBox dbox = null;
  		String v_grcode = box.getSession("tem_grcode");
  		String sql         = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String 		 orderSql = "";
        String       countSql = "";
         String  v_user_id   = box.getString("p_userid");
         
         int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
         int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

  		try {
              connMgr = new DBConnectionManager();
              list = new ArrayList();

              headSql.append(" SELECT   tid, userid, usepoint, usedate, title, luserid, ldate,  \n ");
        	  headSql.append("         (SELECT CASE multiyn  \n ");
        	  headSql.append("                    WHEN 'Y'  \n ");
        	  headSql.append("                       THEN (SELECT subjprice  \n ");
        	  headSql.append("                               FROM tz_billing  \n ");
        	  headSql.append("                              WHERE tid = a.tid  \n ");
        	  headSql.append("                                AND userid = a.userid)  \n ");
        	  headSql.append("                    ELSE subjprice  \n ");
        	  headSql.append("                 END  \n ");
        	  headSql.append("            FROM tz_billinfo  \n ");
        	  headSql.append("           WHERE tid = a.tid) subjprice,  \n ");
        	  headSql.append("         (SELECT CASE multiyn  \n ");
        	  headSql.append("                    WHEN 'Y'  \n ");
        	  headSql.append("                       THEN (SELECT price  \n ");
        	  headSql.append("                               FROM tz_billing  \n ");
        	  headSql.append("                              WHERE tid = a.tid  \n ");
        	  headSql.append("                                AND userid = a.userid)  \n ");
        	  headSql.append("                    ELSE price  \n ");
        	  headSql.append("                 END  \n ");
        	  headSql.append("            FROM tz_billinfo  \n ");
        	  headSql.append("           WHERE tid = a.tid) price  \n ");
        	  bodySql.append("    FROM tz_pointuse a  \n ");
        	  bodySql.append("   WHERE userid = ").append(SQLString.Format(v_user_id));
        	  orderSql = "ORDER BY usedate DESC  \n ";
  			
        	  sql = headSql.toString() + bodySql.toString() + orderSql;                                       
              
         	 ls = connMgr.executeQuery(sql);                                                                 
         	                                                                                                 
         	 countSql= "select count(*) "+ bodySql.toString();                                               
         	                                                                                                 
         	 int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);	//     전체 row 수를 반환한다       
         	 int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다                           
         	 ls.setPageSize(v_pagesize);             		//  페이지당 row 갯수를 세팅한다                           
         	 ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.                           
//         	                                                                                               
         	 while (ls.next()) {                                                                             
         	     dbox = ls.getDataBox();                                                                     
         	     dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));                   
         	     dbox.put("d_totalpage", new Integer(ls.getTotalPage()));                                    
         	     dbox.put("d_rowcount", new Integer(row));                                                   
         	     dbox.put("d_totalrowcount",	new Integer(total_row_count));                                  
         	     list.add(dbox);                                                                             
         	 }                         
          }            
          catch (Exception ex) {
              ErrorManager.getErrorStackTrace(ex, box, sql);
              throw new Exception("sql1 = " + sql + "\r\n" + ex.getMessage());
          }
          finally {
              if(ls != null) { try { ls.close(); }catch (Exception e) {} }
              if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
          }
          return list;        
      }

   	/**
   	* 나의 적립포인트
   	* @param box          receive from the form object and session
   	* @return int         적립포인트
   	* @throws Exception
   	*/
   	public int selectGetPoint(RequestBox box) throws Exception {
   		 DBConnectionManager connMgr = null;
   		 ListSet ls = null;
   		 String sql = "";
   		 int result = 0;

   		 String  v_user_id   = box.getString("p_userid");

   		 try {
   			 connMgr = new DBConnectionManager();

             sql = "select nvl(sum(getpoint - nvl(usepoint, 0)), 0) as getpoint   ";
             sql+= "from   tz_pointget   ";
             sql+= "where  userid = " + SQLString.Format(v_user_id);
             sql+= "and    to_char(sysdate, 'YYYYMMDD') <= expiredate   ";

   			 ls = connMgr.executeQuery(sql);

   			 if ( ls.next()) {
   				 result = ls.getInt("getpoint");
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
   	* 나의 사용포인트
   	* @param box          receive from the form object and session
   	* @return int         사용포인트
   	* @throws Exception
   	*/
   	public int selectUsePoint(RequestBox box) throws Exception {
   		 DBConnectionManager connMgr = null;
   		 ListSet ls = null;
   		 String sql = "";
   		 int result = 0;

   		 String  v_user_id   = box.getString("p_userid");

   		 try {
   			 connMgr = new DBConnectionManager();

         	 //sql = "select  	nvl(sum(usepoint),0) usepoint 		 ";
             sql = "select  	0 usepoint 		 ";
             sql+= "from		tz_pointuse  ";
             sql+= "where	userid = " + SQLString.Format(v_user_id);

   			 ls = connMgr.executeQuery(sql);

   			 if ( ls.next()) {
   				 result = ls.getInt("usepoint");
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
   	* 나의 대기포인트
   	* @param box          receive from the form object and session
   	* @return int         대기포인트
   	* @throws Exception
   	*/
   	public int selectWaitPoint(RequestBox box) throws Exception {
   		 DBConnectionManager connMgr = null;
   		 ListSet ls = null;
   		 String sql = "";
   		 int result = 0;

   		 String  v_user_id   = box.getSession("userid");

   		 try {
   			 connMgr = new DBConnectionManager();

             sql = "select  	nvl(sum(usepoint),0) usepoint 		 ";
             sql+= "from		tz_pointwait  ";
             sql+= "where	userid = " + SQLString.Format(v_user_id);

   			 ls = connMgr.executeQuery(sql);

   			 if ( ls.next()) {
   				 result = ls.getInt("usepoint");
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
	* 관리자 포인트 적립
	* @param box      receive from the form object and session
	* @return isOk    1:insert success,0:insert fail
	* @throws Exception
	*/
	public int insert(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet 			ls 		= null;
		PreparedStatement 	pstmt 	= null;
		StringBuffer 		sql  	= new StringBuffer();
		
		int isOk  = 0;
		
		String v_receive_userid	= box.getStringDefault("p_receive_userid", "lee1");
		String v_title		    = box.getString("p_title");
		int    v_receive_point	= box.getInt("p_receive_point");
		
		String s_userid   = box.getSession("userid");
		String s_name     = box.getSession("name");

		try {
		   connMgr = new DBConnectionManager();
		   connMgr.setAutoCommit(false);

           sql.append(" insert into TZ_POINTGET(                \n");
           sql.append(" tid, userid, getpoint, getdate, title,        \n");
           sql.append(" luserid, ldate)            \n");
           sql.append(" values (");
           sql.append(" to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ");
           sql.append(" to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ");
           sql.append(" to_char(sysdate, 'YYYYMMDDHH24MISS') ");
           sql.append(") ");

         int index = 1;
         pstmt = connMgr.prepareStatement(sql.toString());
         pstmt.setString(index++,  v_receive_userid);
         pstmt.setInt   (index++,  v_receive_point);
         pstmt.setString(index++,  v_title);
         pstmt.setString(index++,  s_userid);
         isOk = pstmt.executeUpdate();

           if(isOk > 0 ){
           	  connMgr.commit();
           	}else{
           	  connMgr.rollback();
           	}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql ->" + sql.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}
}
