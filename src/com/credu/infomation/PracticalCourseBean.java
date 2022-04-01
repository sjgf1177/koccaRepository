//**********************************************************
//  1. 제      목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개      요:  관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수      정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.infomation;

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
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class PracticalCourseBean {

	private ConfigSet config;
    private int row;

	public PracticalCourseBean() {
	    try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	}
	

	/**
	* 전문가 관리 리스트
	* @param box          receive from the form object and session
	* @return ArrayList   전문가 관리 리스트
	* @throws Exception
	*/
	public ArrayList selectListPracticalCourse(RequestBox box) throws Exception {
		
		DBConnectionManager connMgr = null;
		ListSet 			ls 		= null;
		ArrayList 			list 	= null;
		StringBuffer 	headSql 	= new StringBuffer();
		StringBuffer 	bodySql 	= new StringBuffer();
		String 			orderSql 	= "";
		String 			countSql 	= "";
		String 			sql			= "";
		DataBox 		dbox 		= null;
		
		String			v_process	 = box.getString("p_process");
		
		String 			v_search     = box.getString("p_search");
		String 			v_searchtext = box.getString("p_searchtext");
		
		String 			v_selDtlCd   = box.getStringDefault("p_selDtlCd", "ALL");
		String 			v_selContentType = box.getStringDefault("p_selContentType", "ALL");

        int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");
        
        if(v_process.equals("INFOMATION") || v_process.equals("HELPDESK")){	// 정보광장 메인페이지 5개 출력
        	v_pagesize = 5;
        }	

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			headSql.append(" SELECT                                                   \n ");
			headSql.append("         A.NUM, A.CLSFCD, A.DTLCD, A.TITLE, A.CONTENTTYPE \n ");
			headSql.append("         , A.CNT, A.PROFESSOR, A.INDATE, A.NAME           \n ");
			headSql.append("         , A.MAINYN, A.USECHK, B.CODENM CONTENTTYPENM     \n ");
			headSql.append("         , C.CODENM DTLNM, A.USECHK                       \n ");

			bodySql.append(" FROM    TZ_PORTFOLIO A, TZ_CODE B, TZ_CODE C             \n ");                     
			bodySql.append(" WHERE   A.CONTENTTYPE   = B.CODE(+)                      \n ");
			bodySql.append(" AND     B.GUBUN(+)      = '0085'                         \n ");
			bodySql.append(" AND     A.DTLCD         = C.CODE(+)                      \n ");
			bodySql.append(" AND     C.GUBUN(+)      = '0086'                         \n ");
			
			if ( !v_searchtext.equals("")) {      //    검색어가 있으면                                         
				if (v_search.equals("title")) {                          //    제목으로 검색할때  
					bodySql.append(" AND     A.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");  
				} else if (v_search.equals("content")) {                //    내용으로 검색할때  
                    bodySql.append(" AND     A.CONTENT LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");  
				}   
			} 
			
			if ( !v_selDtlCd.equals("ALL")) {   
				bodySql.append(" AND     A.DTLCD = " + StringManager.makeSQL(v_selDtlCd)+" \n");
			}
			
			if ( !v_selContentType.equals("ALL")) {   
				bodySql.append(" AND     A.CONTENTTYPE = " + StringManager.makeSQL(v_selContentType)+" \n");
			}
			orderSql = " ORDER BY     A.MAINYN DESC, A.INDATE DESC ";
			
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
	*  등록
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/
	 public int insertPracticalCourse(RequestBox box) throws Exception {
		DBConnectionManager connMgr 	= null;
		PreparedStatement 	pstmt 		= null;       
		Statement 			stmt 		= null;
		StringBuffer 		insertSql	= new StringBuffer();
		String              sql     	= "";
		ResultSet           rs   		= null;
		int isOk = 0;
		
		int    v_seq            = 0;
		String v_clsfcd         = box.getStringDefault("p_clsfcd", "FF");                
		String v_dtlcd          = box.getString("p_dtlcd");       
		String v_contenttype    = box.getString("p_contenttype");     
		String v_title          = box.getString("p_title");       
		String v_content        = StringUtil.removeTag(box.getString("p_content"));    
		String v_professor      = box.getString("p_professor");   
		String v_pictureurl     = box.getString("p_pictureurl");  
		String v_gubun          = box.getString("p_gubun");       
		String v_genre          = box.getString("p_genre");      
		String v_inspector      = box.getString("p_inspector");   
		String v_runningtime    = box.getString("p_runningtime"); 
		String v_production     = box.getString("p_production");  
		String v_analyze        = box.getString("p_analyze");     
		String v_usechk         = box.getStringDefault("p_usechk", "N");      
		String v_splecturenm    = box.getString("p_splecturenm"); 
		String v_spcontent      = box.getString("p_spcontent");   
		String v_mainyn         = box.getString("p_mainyn");      
		                                         
		String v_imageurl       = box.getNewFileName("p_imageurl");
		String v_professorimg   = box.getNewFileName("p_professorimg");
		
		String v_upfile         = box.getRealFileName("p_file");      
		String v_svrfile        = box.getNewFileName("p_file");     

		String  s_userid        = box.getSession("userid");
		String  s_name          = box.getSession("name");
		
		try {
			connMgr = new DBConnectionManager();
			
			stmt 	= connMgr.createStatement();				
            sql 	= "select nvl(max(num), 0) + 1 num from TZ_PORTFOLIO ";
            rs 		= stmt.executeQuery(sql);
            if (rs.next()) {
            	v_seq  = rs.getInt("num");
            }
			
			connMgr.setAutoCommit(false);
			
		   /*********************************************************************************************/
		   // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다.                                                     
		   try {                                                                                          
			   v_content =(String) NamoMime.setNamoContent(v_content);                                    
		   } catch(Exception e) {                                                                         
			   System.out.println(e.toString());                                                          
			   return 0;                                                                                  
		   }                                                                                              
		   /*********************************************************************************************/

			
			insertSql.append(" INSERT INTO TZ_PORTFOLIO                        \n ");
			insertSql.append(" (                                               \n ");
			insertSql.append("     NUM, CLSFCD, DTLCD, CONTENTTYPE, TITLE      \n ");
			insertSql.append("     , CONTENT, PROFESSOR, PICTUREURL, GUBUN     \n ");
			insertSql.append("     , GENRE, INSPECTOR, RUNNINGTIME, PRODUCTION \n ");
			insertSql.append("     , ANALYZE, USECHK, SPLECTURENM, SPCONTENT   \n ");
			insertSql.append("     , MAINYN, IMAGEURL, UPFILE, SVRFILE, USERID \n ");
			insertSql.append("     , NAME, LUSERID, PROFESSORIMG, INDATE, LDATE\n ");
			insertSql.append(" )                                               \n ");
			insertSql.append(" VALUES                                          \n ");
			insertSql.append(" (                                               \n ");
			insertSql.append("     ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?       \n ");
			insertSql.append("     , ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?        \n ");
			insertSql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')      \n ");
			insertSql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')      \n ");
			insertSql.append(" ) ");                                                 


			pstmt = connMgr.prepareStatement(insertSql.toString());

			int index = 1;
			
            pstmt.setInt   (index++, v_seq         );
            pstmt.setString(index++, v_clsfcd      );
            pstmt.setString(index++, v_dtlcd       );
            pstmt.setString(index++, v_contenttype );
            pstmt.setString(index++, v_title       );
            pstmt.setString(index++, v_content     );
            pstmt.setString(index++, v_professor   );
			pstmt.setString(index++, v_pictureurl  );
			pstmt.setString(index++, v_gubun       );
			pstmt.setString(index++, v_genre       );
			pstmt.setString(index++, v_inspector   );
			pstmt.setString(index++, v_runningtime );
			pstmt.setString(index++, v_production  );
			pstmt.setString(index++, v_analyze     );
			pstmt.setString(index++, v_usechk      );
			pstmt.setString(index++, v_splecturenm );
			pstmt.setString(index++, v_spcontent   );
			pstmt.setString(index++, v_mainyn      );
			pstmt.setString(index++, v_imageurl    );
			pstmt.setString(index++, v_upfile      );
			pstmt.setString(index++, v_svrfile     );
			pstmt.setString(index++, s_userid      );
			pstmt.setString(index++, s_name        );
			pstmt.setString(index++, s_userid      );
			pstmt.setString(index++, v_professorimg);
			                         
			isOk = pstmt.executeUpdate();                         
			                         
			if(isOk > 0 ){             
				connMgr.commit();
				isOk =1;
			}
			else{
				connMgr.rollback();
				isOk =0;
			}
			
		}
		catch(Exception ex) {connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, insertSql.toString());
			throw new Exception("sql = " + insertSql.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(rs != null) { try { rs.close(); } catch (Exception e) {} }
            if(stmt != null) { try { stmt.close(); } catch (Exception e1) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try {connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}
	
	/**
	*  수정하여 저장할때
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/
	 public int updatePracticalCourse(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer updateSql = new StringBuffer();
		int isOk = 0;
		
		int    v_seq            = box.getInt("p_seq");
		String v_clsfcd         = box.getStringDefault("p_clsfcd", "FF");                
		String v_dtlcd          = box.getString("p_dtlcd");       
		String v_contenttype    = box.getString("p_contenttype");     
		String v_title          = box.getString("p_title");       
		String v_content        = StringUtil.removeTag(box.getString("p_content"));    
		String v_professor      = box.getString("p_professor");   
		String v_pictureurl     = box.getString("p_pictureurl");  
		String v_gubun          = box.getString("p_gubun");       
		String v_genre          = box.getString("p_genre");      
		String v_inspector      = box.getString("p_inspector");   
		String v_runningtime    = box.getString("p_runningtime"); 
		String v_production     = box.getString("p_production");  
		String v_analyze        = box.getString("p_analyze");     
		String v_usechk         = box.getString("p_usechk");      
		String v_splecturenm    = box.getString("p_splecturenm"); 
		String v_spcontent      = box.getString("p_spcontent");   
		String v_mainyn         = box.getString("p_mainyn");      
		String v_fileDelYn      = box.getStringDefault("p_fileDelYn", "N");
		
		String v_imageurl       = box.getNewFileName("p_imageurl");
		String v_professorimg   = box.getNewFileName("p_professorimg");
		
		String v_upfile         = box.getRealFileName("p_file");      
		String v_svrfile        = box.getNewFileName("p_file");     

		String s_userid         = box.getSession("userid");
		
		if(v_svrfile.length()   == 0) {
			if (v_fileDelYn.equals("Y")){
				v_upfile      	= "";
				v_svrfile     	= "";
            } else {
            	v_upfile    	= box.getString("p_up_file");
            	v_svrfile     	= box.getString("p_save_file");
            }
        } 
		
		if(v_imageurl.length()   == 0) {
			v_imageurl    	= box.getString("p_save_imageurl");
        } 
		
		if(v_professorimg.length()   == 0) {
			v_professorimg 	= box.getString("p_save_professorimg");
        } 
		
		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
		   /*********************************************************************************************/
		   // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다.                                                     
		   try {                                                                                          
			   v_content =(String) NamoMime.setNamoContent(v_content);                                    
		   } catch(Exception e) {                                                                         
			   System.out.println(e.toString());                                                          
			   return 0;                                                                                  
		   }                                                                                              
		   /*********************************************************************************************/


			updateSql.append(" UPDATE TZ_PORTFOLIO SET                            \n ");
			updateSql.append("     CLSFCD          = ?                            \n ");
			updateSql.append("     , DTLCD         = ? , CONTENTTYPE   = ?        \n ");
			updateSql.append("     , TITLE         = ? , CONTENT       = ?        \n ");
			updateSql.append("     , PROFESSOR     = ? , PICTUREURL    = ?        \n ");
			updateSql.append("     , GUBUN         = ? , GENRE         = ?        \n ");
			updateSql.append("     , INSPECTOR     = ? , RUNNINGTIME   = ?        \n ");
			updateSql.append("     , PRODUCTION    = ? , ANALYZE       = ?        \n ");
			updateSql.append("     , USECHK        = ? , SPLECTURENM   = ?        \n ");
			updateSql.append("     , SPCONTENT     = ? , MAINYN        = ?        \n ");
			updateSql.append("     , IMAGEURL      = ? , UPFILE        = ?        \n ");
			updateSql.append("     , SVRFILE       = ? , LUSERID       = ?        \n ");
			updateSql.append("     , PROFESSORIMG  = ?                            \n ");
			updateSql.append("     , LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') \n ");
			updateSql.append(" WHERE                                              \n ");
			updateSql.append("     NUM = ?                                        \n ");


			pstmt = connMgr.prepareStatement(updateSql.toString());
			
			int index = 1;
			
			pstmt.setString(index++,  v_clsfcd      );
			pstmt.setString(index++,  v_dtlcd       );
			pstmt.setString(index++,  v_contenttype );
			pstmt.setString(index++,  v_title       );
			pstmt.setString(index++,  v_content     );
			pstmt.setString(index++,  v_professor   );
			pstmt.setString(index++,  v_pictureurl  );
			pstmt.setString(index++,  v_gubun       );
			pstmt.setString(index++,  v_genre       );
			pstmt.setString(index++,  v_inspector   );
			pstmt.setString(index++,  v_runningtime );
			pstmt.setString(index++,  v_production  );
			pstmt.setString(index++,  v_analyze     );
			pstmt.setString(index++,  v_usechk      );     
			pstmt.setString(index++,  v_splecturenm );
			pstmt.setString(index++,  v_spcontent   );
			pstmt.setString(index++,  v_mainyn      );     
            pstmt.setString(index++,  v_imageurl    );    
            pstmt.setString(index++,  v_upfile      );
			pstmt.setString(index++,  v_svrfile     );    
            pstmt.setString(index++,  s_userid      );  
            pstmt.setString(index++,  v_professorimg);
            pstmt.setInt   (index++,  v_seq         );
            
			isOk = pstmt.executeUpdate();
			
			if(isOk > 0 ){
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
	* 화면 상세보기
	* @param box          receive from the form object and session
	* @return ArrayList   조회한 상세정보
	* @throws Exception
	*/
   public DataBox selectViewPracticalCourse(RequestBox box) throws Exception {
	 
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox dbox = null;

		int 	v_seq 		= box.getInt("p_seq");
		String  v_process   = box.getString("p_process");

		try {
			connMgr = new DBConnectionManager();

			sql.append(" SELECT                                  \n ");
			sql.append("         A.CLSFCD                        \n ");
			sql.append("         , A.DTLCD                       \n ");
			sql.append("         , A.NUM                         \n ");
			sql.append("         , A.USERID                      \n ");
			sql.append("         , A.NAME                        \n ");
			sql.append("         , A.TITLE                       \n ");
			sql.append("         , A.CONTENT                     \n ");
			sql.append("         , A.PROFESSOR                   \n ");
			sql.append("         , A.UPFILE                      \n ");
			sql.append("         , A.SVRFILE                     \n ");
			sql.append("         , A.IMAGEURL                    \n ");
			sql.append("         , A.PICTUREURL                  \n ");
			sql.append("         , A.GUBUN                       \n ");
			sql.append("         , A.GENRE                       \n ");
			sql.append("         , A.INSPECTOR                   \n ");
			sql.append("         , A.RUNNINGTIME                 \n ");
			sql.append("         , A.PRODUCTION                  \n ");
			sql.append("         , A.ANALYZE                     \n ");
			sql.append("         , A.CNT                         \n ");
			sql.append("         , A.USECHK                      \n ");
			sql.append("         , A.SPLECTURENM                 \n ");
			sql.append("         , A.SPCONTENT                   \n ");
			sql.append("         , A.MAINYN                      \n ");
			sql.append("         , A.CONTENTTYPE                 \n ");
			sql.append("         , A.INDATE                      \n ");
			sql.append("         , A.LUSERID                     \n ");
			sql.append("         , A.LDATE                       \n ");
			sql.append("         , A.PROFESSORIMG                \n ");
			sql.append("         , B.CODENM CONTENTTYPENM        \n ");
			sql.append(" FROM                                    \n ");
			sql.append("         TZ_PORTFOLIO A                  \n ");
			sql.append("         , TZ_CODE    B                  \n ");
			sql.append(" WHERE                                   \n ");
			sql.append("         A.CONTENTTYPE = B.CODE(+)       \n ");
			sql.append(" AND     B.GUBUN(+)    = '0085'          \n ");
			sql.append(" AND     A.NUM    = "+v_seq);

			
			ls = connMgr.executeQuery(sql.toString());
			
			while (ls.next()) {
				dbox = ls.getDataBox();
			}
			
			if(v_process.equals("selectView")){
				connMgr.executeUpdate(" update TZ_PORTFOLIO set cnt = cnt + 1 where num = "+v_seq);
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
 	
	/**
	* 공지사항 삭제할때
	* @param box      receive from the form object and session
	* @return isOk    1:delete success,0:delete fail
	* @throws Exception
	*/
	public int deletePracticalCourse(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 0;

		int v_seq  = box.getInt("p_seq");

		try {
			connMgr = new DBConnectionManager();
			sql  = " delete from TZ_PORTFOLIO           ";
			sql += "   where  num = ?  ";

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


}
