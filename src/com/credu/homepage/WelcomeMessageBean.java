//**********************************************************
//  1. 제      목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개      요:  관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수      정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.homepage;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class WelcomeMessageBean {

	private ConfigSet config;
    private int row;

	public WelcomeMessageBean() {
	    try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	}
	
	/**
	* 자료실 테이블번호
	* @param box          receive from the form object and session
	* @return int         자료실 테이블번호
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


//=========운영자인화면 리스트 시작=========

	/**
	* 전체공지  리스트
	* @param box          receive from the form object and session
	* @return ArrayList   수강생 갤러리 리스트
	* @throws Exception
	*/
	public ArrayList selectListWelcomeMessage(RequestBox box) throws Exception {
		
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

        String 			v_orderColumn = box.getString("p_orderColumn");
        String 			v_orderType = box.getString("p_orderType");
        
        int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			headSql.append(" SELECT                                   \n ");
			headSql.append("         ROWNUM                           \n ");
			headSql.append("         , BOARD.SEQ                      \n ");
			headSql.append("         , BOARD.TYPE                     \n ");
			headSql.append("         , BOARD.CONTENT                  \n ");
			headSql.append("         , BOARD.STARTDATE                \n ");
			headSql.append("         , BOARD.ENDDATE                  \n ");
			headSql.append("         , BOARD.MONTH                    \n ");
			headSql.append("         , BOARD.USERID                   \n ");
			headSql.append("         , BOARD.NAME                     \n ");
			headSql.append("         , BOARD.LDATE                    \n ");
			headSql.append("         , ( CASE WHEN BOARD.TYPE = 'A1' AND TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN STARTDATE AND ENDDATE THEN \n");
			headSql.append("                       'Y'                                                                                   \n");
			headSql.append("                  WHEN BOARD.TYPE = 'B1' AND TO_CHAR(SYSDATE, 'YYYYMM')   = MONTH THEN                       \n");
			headSql.append("                       'Y'                                                                                   \n");
			headSql.append("                  ELSE                                                                                       \n");
			headSql.append("                	    'N'                                                                                  \n");
			headSql.append("         END ) FLAGYN                                                                                       \n");
			
			bodySql.append(" FROM    TZ_MESSAGE    BOARD              \n ");
			bodySql.append(" WHERE   1=1                              \n ");
			

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면                                         
				if (v_search.equals("title")) {                          //    제목으로 검색할때  
					bodySql.append(" AND TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");  
				} else if (v_search.equals("content")) {                //    내용으로 검색할때  
					bodySql.append(" AND CONTENT LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");
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
            int total_page_count = ls.getTotalPage();  	//전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize);                   	//페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, totalrowcount);    //현재페이지번호를 세팅한다.
			
            while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum",		new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage",		new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount",  	new Integer(row));
                dbox.put("d_totalrowcount",	new Integer(totalrowcount));
				list.add(dbox);
			}

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
	*  등록할때
	* @param box      receive from the form object and session
	* @return isOk    1:insert success,0:insert fail
	* @throws Exception
	*/
	public int insertWelcomeMessage(RequestBox box) throws Exception {
		
		DBConnectionManager connMgr = null;
		ListSet 			ls 		= null;
		PreparedStatement 	pstmt 	= null;
		String 				seqSql  = "";
		StringBuffer 		insertSql = new StringBuffer();
		
		int isOk  = 0;
		int v_seq = 0;
		int index = 1;

		String v_content   = StringUtil.removeTag(box.getString		("p_content"));
		String v_type      = box.getString		("p_type");
		String s_userid    = box.getSession		("userid");
		String s_name      = box.getSession		("name");
		String v_startdate = box.getString		("p_startdate");
		String v_enddate   = box.getString		("p_enddate");
		String v_year      = box.getString      ("p_year");
		String v_month     = v_year + box.getString		("p_month");
	
		try {
		   connMgr = new DBConnectionManager();
		   connMgr.setAutoCommit(false);

		   seqSql  = "SELECT MAX(seq) FROM TZ_MESSAGE";
		   ls = connMgr.executeQuery(seqSql);
		   if (ls.next()) {
			   v_seq = ls.getInt(1) + 1;
		   } else {
			   v_seq = 1;
		   }
		   
		   if(v_type.equals("A1")){
			   
			   insertSql.append(" INSERT INTO TZ_MESSAGE                                    \n");
			   insertSql.append(" (   SEQ, TYPE, STARTDATE, ENDDATE, CONTENT                \n");
			   insertSql.append("     , INDATE, USERID, NAME , LUSERID, LDATE )             \n");
			   insertSql.append(" VALUES                                                    \n");
			   insertSql.append(" (   ?, ?, ?, ? , ? , to_char(sysdate, 'YYYYMMDDHH24MISS') \n");
			   insertSql.append("      , ? , ?  , ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))   ");
			   
			   pstmt 			= connMgr.prepareStatement(insertSql.toString());
	           pstmt.setInt   (index++,  v_seq);
	           pstmt.setString(index++,  v_type);
	           pstmt.setString(index++,  v_startdate.replace("-",""));
	           pstmt.setString(index++,  v_enddate.replace("-",""));
	           pstmt.setString(index++,  v_content);
	           pstmt.setString(index++,  s_userid);
	           pstmt.setString(index++,  s_name);
	           pstmt.setString(index++,  s_userid);
			   
		   } else if (v_type.equals("B1")){
			   
			   insertSql.append(" INSERT INTO TZ_MESSAGE                                    \n");
			   insertSql.append(" (   SEQ, TYPE, MONTH, CONTENT                             \n");
			   insertSql.append("     , INDATE, USERID, NAME , LUSERID, LDATE )             \n");
			   insertSql.append(" VALUES                                                    \n");
			   insertSql.append(" (   ?, ?, ?, ? , to_char(sysdate, 'YYYYMMDDHH24MISS')     \n");
			   insertSql.append("      , ? , ?  , ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))    ");
			   
			   pstmt 			= connMgr.prepareStatement(insertSql.toString());  
			   pstmt.setInt   (index++,  v_seq);                                   
			   pstmt.setString(index++,  v_type);                                  
			   pstmt.setString(index++,  v_month);                             
			   pstmt.setString(index++,  v_content);                               
			   pstmt.setString(index++,  s_userid);                                
			   pstmt.setString(index++,  s_name);                                  
			   pstmt.setString(index++,  s_userid);                                
		   }
		   
           isOk = pstmt.executeUpdate();

           if(isOk > 0 ){
           	  connMgr.commit();
           	}else{
           	  connMgr.rollback();
           	}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, insertSql.toString());
			throw new Exception("sql ->" + insertSql.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}


	/**
	*  수정하여 저장할때
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/
	 public int updateWelcomeMessage(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer updateSql = new StringBuffer();
		int isOk = 0;
		
		int    v_seq       = box.getInt         ("p_seq");
		String v_content   = StringUtil.removeTag(box.getString		("p_content"));
		String v_type      = box.getString		("p_type");
		String s_userid    = box.getSession		("userid");
		String v_startdate = box.getString		("p_startdate");
		String v_enddate   = box.getString		("p_enddate");
		String v_year      = box.getString      ("p_year");
		String v_month     = v_year + box.getString		("p_month");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			updateSql.append(" UPDATE TZ_MESSAGE                                               \n");
			updateSql.append(" SET                                                             \n");
			updateSql.append("         TYPE        = ?                                         \n");
			updateSql.append("         , STARTDATE = ?                                         \n");
			updateSql.append("         , ENDDATE   = ?                                         \n");
			updateSql.append("         , MONTH     = ?                                         \n");
			updateSql.append("         , CONTENT   = ?                                         \n");
			updateSql.append("         , LUSERID   = ?                                         \n");
			updateSql.append("         , LDATE     = to_char(sysdate, 'YYYYMMDDHH24MISS')      \n");
			updateSql.append(" WHERE                                                           \n");
			updateSql.append("         SEQ         = ?                                         \n");

			pstmt = connMgr.prepareStatement(updateSql.toString());
			
			int index = 1;
			
            pstmt.setString(index++,  v_type);
            pstmt.setString(index++,  v_startdate.replace("-",""));
            pstmt.setString(index++,  v_enddate.replace("-",""));
            pstmt.setString(index++,  v_month);
            pstmt.setString(index++,  v_content);
            pstmt.setString(index++,  s_userid);
            pstmt.setInt   (index++,  v_seq);

			isOk = pstmt.executeUpdate();
			
			if(isOk > 0){
				connMgr.commit();
				isOk =1;
			}
			else{
				connMgr.rollback();
				isOk =0;
			}
		}
		catch(Exception ex) {connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, updateSql.toString());
			throw new Exception("sql = " + updateSql.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try {connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}


	/**
	*  삭제할때
	* @param box      receive from the form object and session
	* @return isOk    1:delete success,0:delete fail
	* @throws Exception
	*/
	public int deleteWelcomeMessage(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement 	pstmt 	= null;
		String sql = "";
		int isOk 		= 0;

		int v_seq  		= box.getInt("p_seq");

		try {
			connMgr = new DBConnectionManager();
			sql  = " DELETE FROM TZ_MESSAGE          ";
			sql += "   WHERE  SEQ = ?  ";

			pstmt = connMgr.prepareStatement(sql);
			pstmt.setInt(1, v_seq);
			isOk = pstmt.executeUpdate();
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql+ "\r\n");
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}



	/**
	* 화면 상세보기
	* @param box          receive from the form object and session
	* @return ArrayList   조회한 상세정보
	* @throws Exception
	*/
   public DataBox selectViewWelcomeMessage(RequestBox box) throws Exception {
	 
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox dbox = null;

		int v_seq = box.getInt("p_seq");

		try {
			connMgr = new DBConnectionManager();

			sql.append(" SELECT                                   \n ");
			sql.append("         ROWNUM                           \n ");
			sql.append("         , BOARD.SEQ                      \n ");
			sql.append("         , BOARD.TYPE                     \n ");
			sql.append("         , BOARD.CONTENT                  \n ");
			sql.append("         , BOARD.STARTDATE                \n ");
			sql.append("         , BOARD.ENDDATE                  \n ");
			sql.append("         , BOARD.MONTH                    \n ");
			sql.append("         , BOARD.USERID                   \n ");
			sql.append("         , BOARD.NAME                     \n ");
			sql.append("         , BOARD.LDATE                    \n ");
			sql.append("         , ( CASE WHEN BOARD.TYPE = 'A1' AND TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN STARTDATE AND ENDDATE THEN \n");
			sql.append("                       'Y'                                                                                   \n");
			sql.append("                  WHEN BOARD.TYPE = 'B1' AND TO_CHAR(SYSDATE, 'YYYYMM')   = MONTH THEN                       \n");
			sql.append("                       'Y'                                                                                   \n");
			sql.append("                  ELSE                                                                                       \n");
			sql.append("                	    'N'                                                                                  \n");
			sql.append("         END ) FLAG_YN                                                                                       \n");
			sql.append(" FROM    TZ_MESSAGE    BOARD              \n ");
			sql.append(" WHERE   BOARD.SEQ      = "+v_seq+"       \n ");
			
			ls = connMgr.executeQuery(sql.toString());
			
			while (ls.next()) {
				dbox = ls.getDataBox();
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
