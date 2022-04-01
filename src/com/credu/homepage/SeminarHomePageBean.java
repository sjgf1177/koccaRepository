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
public class SeminarHomePageBean {

	private ConfigSet config;
    private int row;


	public SeminarHomePageBean() {
	    try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	}

	
	/**
	* 화면 리스트
	* @param box          receive from the form object and session
	* @return ArrayList    리스트(전체공지 제외)
	* @throws Exception
	*/
	public ArrayList selectList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet 		ls 			= null;
		ArrayList 		list 		= null;
        String 			sql    		= "";
        String 			countSql  	= "";
        StringBuffer 	headSql   	= new StringBuffer();
		StringBuffer 	bodySql  	= new StringBuffer();
        String 			orderSql  	= "";

		DataBox 		dbox 		= null;

		String			v_process		= box.getString("p_process");
		
		String 			v_searchtext 	= box.getString("p_searchtext");
		String 			v_search     	= box.getString("p_search");

		int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");
        
        if( v_process.equals("ONLINE_COURESE") || v_process.equals("OFFLINE_COURESE")){	//  메인페이지 현재 진행중인 이벤트 목록
        	v_pagesize  = 5;
        }
        
		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			headSql.append(" SELECT \n");
			headSql.append("         A.SEQ, A.SEMINARGUBUN, A.SEMINARNM, A.TNAME \n");
			headSql.append("         , A.STARTDATE, A.ENDDATE, A.PROPSTART, A.PROPEND \n");
			headSql.append("         , A.LIMITMEMBER, B.CODE, B.CODENM  SEMINARGUBUNNM \n");
			headSql.append("         , CASE WHEN  A.PASS_YN = 'Y' THEN \n");
			headSql.append("                      'Y' \n");
			headSql.append("                WHEN  A.PASS_YN = 'N' THEN \n");
			headSql.append("                      CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') BETWEEN SUBSTR(STARTDATE, 1, 8) || STARTTIME AND SUBSTR(ENDDATE, 1, 8)|| ENDTIME THEN \n");
			headSql.append("                                'A' \n");
			headSql.append("                           WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') < SUBSTR(STARTDATE, 1, 8) THEN \n");
			headSql.append("                                'B' \n");
			headSql.append("                           ELSE \n");
			headSql.append("                                A.PASS_YN \n");
			headSql.append("                      END \n");
			headSql.append("         END PASS_YN, A.CNT, A.BANNERIMG , A.INDATE \n");     

			bodySql.append(" FROM TZ_SEMINAR A ,TZ_CODE B \n");
			bodySql.append(" WHERE A.SEMINARGUBUN  = B.CODE(+) \n");
			bodySql.append(" AND B.GUBUN(+) = '0061' \n");
			bodySql.append(" AND A.USEYN = 'Y' \n");
			
	        if(v_process.equals("INFOMATION") || v_process.equals("HELPDESK")){	// 정보광장 메인페이지 현재 모집중인 워크샵 목록 출력
	        	bodySql.append(" AND TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') BETWEEN SUBSTR(STARTDATE, 1, 8) || STARTTIME AND SUBSTR(ENDDATE, 1, 8)|| ENDTIME");
	        }
			
			if ( !v_searchtext.equals("")) {     
				v_pageno = 1;   
				if (v_search.equals("seminarnm")) {                          
					bodySql.append(" AND SEMINARNM LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+ " \n");
				} else if (v_search.equals("all")) {            
					bodySql.append(" AND (CONTENT LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+ " \n");
					bodySql.append(" OR SEMINARNM LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+ " ) \n");
				} 
			}
			
			orderSql = " ORDER BY A.SEQ DESC ";

			sql= headSql.toString()+ bodySql.toString()+ orderSql;

			ls = connMgr.executeQuery(sql);

			countSql= "SELECT COUNT(*) "+ bodySql.toString();
			int totalRowCount= BoardPaging.getTotalRow(connMgr, countSql);	//     전체 row 수를 반환한다
            int totalPageCount = ls.getTotalPage();       					//     전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize);             						//     페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, totalRowCount);   					//     현재페이지번호를 세팅한다.

			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(totalRowCount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount",	new Integer(totalRowCount));
				list.add(dbox);
			}
		} catch (Exception ex) {
			   ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		} finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		
		return list;
	}
	


	/**
	*  워크샵 신청할때
	* @param box      receive from the form object and session
	* @return isOk    1:insert success,0:insert fail
	* @throws Exception
	*/
	public int insertRequest(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		PreparedStatement pstmt = null;
		String sql  = "";
		StringBuffer insertSql = new StringBuffer();
		int isOk  = 0;
		
		int 	v_seq 		= box.getInt("p_seq");
		String 	v_email		= box.getString("p_email");
		String 	v_handphone	= box.getString("p_handphone");

		String 	s_userid   	= box.getSession("userid");

		try {
		   connMgr = new DBConnectionManager();
		   connMgr.setAutoCommit(false);

		   insertSql.append(" INSERT INTO TZ_SEMINAR_APPLY 						\n ");
		   insertSql.append(" (                                               	\n ");
		   insertSql.append("     SEQ, USERID, EMAIL, HANDPHONE, INDATE , LDATE\n ");
		   insertSql.append(" )     											\n ");
		   insertSql.append(" VALUES     										\n ");
		   insertSql.append(" (   ?, ?, ?, ?                        			\n ");
		   insertSql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI')     		\n ");
		   insertSql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI')          \n ");
		   insertSql.append(" ) "); 
		   
		   int index = 1;
           pstmt = connMgr.prepareStatement(insertSql.toString());
           pstmt.setInt   (index++, v_seq);
           pstmt.setString(index++, s_userid);
           pstmt.setString(index++, v_email);
           pstmt.setString(index++, v_handphone);
           
           isOk = pstmt.executeUpdate();

           if(isOk > 0 ){
           	  connMgr.commit();
           	}else{
           	  connMgr.rollback();
           	}
           Log.err.println("isOk==> " + isOk);
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
	* 화면 상세보기
	* @param box          receive from the form object and session
	* @return ArrayList   조회한 상세정보
	* @throws Exception
	*/
   public DataBox selectView(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox dbox = null;

		int v_seq = box.getInt("p_seq");
		String v_process = box.getString("p_process");
		String s_userid  = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();

			sql.append(" SELECT      A.SEQ, A.SEMINARGUBUN, A.SEMINARNM, A.CONTENT             \n ");         
			sql.append("             , A.STARTDATE, A.ENDDATE, A.STARTTIME, A.ENDTIME          \n ");                                                        
			sql.append("             , A.PROPSTART, A.PROPEND, A.LIMITMEMBER, A.TNAME          \n ");                                                   
			sql.append("             , A.USEYN, A.TARGET, A.PASS_CONTENT, A.CNT                \n ");                                             
			sql.append("             , A.USERID, A.INDATE, A.LUSERID, A.LDATE                  \n ");                                          
			sql.append("             , CASE WHEN  A.PASS_YN = 'Y' THEN                         \n ");                                          
			sql.append("                          'Y'                                          \n ");                                          
			sql.append("                    WHEN  A.PASS_YN = 'N' THEN                         \n ");                                                
			sql.append("                          CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') BETWEEN A.STARTDATE || A.STARTTIME AND A.ENDDATE || A.ENDTIME THEN \n ");
			sql.append("                                    'A'                                                                                           \n ");
			sql.append("                               WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') < A.STARTDATE || A.STARTTIME THEN                              \n ");
			sql.append("                                    'B'                                                                                           \n ");
			sql.append("                               ELSE                                                                                               \n ");
			sql.append("                                    A.PASS_YN                                                                                     \n ");
			sql.append("                          END                                                                                                     \n ");
			sql.append("             END PASS_YN, A.PASS_CONTENT, B.CODENM SEMINARGUBUNNM      \n ");
			sql.append("             , CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN A.PROPSTART AND A.PROPEND THEN 'Y' \n ");
			sql.append("                  ELSE 'N'                                                                      \n ");
			sql.append("             END PROGRESS_YN                                                                    \n ");
					
			sql.append("             , DECODE(C.SEQ, NULL, 'N', 'Y') APPLY_YN                  \n "); 
			sql.append(" FROM        TZ_SEMINAR A, TZ_CODE B, TZ_SEMINAR_APPLY C			   \n ");  
			sql.append(" WHERE       A.SEMINARGUBUN = B.CODE(+)								   \n ");
			sql.append(" AND         B.GUBUN(+) 	= '0061'								   \n ");
			sql.append(" AND 	 	 A.SEQ    = " +v_seq);
			sql.append(" AND         A.SEQ    = C.SEQ(+)     								   \n ");
			sql.append(" AND 	 	 C.USERID(+)= " +StringManager.makeSQL(s_userid));
			
			ls = connMgr.executeQuery(sql.toString());

			while (ls.next()) {
				dbox = ls.getDataBox();
			}

			// 조회수 증가
			if(v_process.equals("selectView")){
			  connMgr.executeUpdate("update TZ_SEMINAR set cnt = cnt + 1 where seq = " + v_seq);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("NoticeSql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) {try {ls.close();} catch(Exception e){}}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return dbox;
	}
}
