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
public class SeminarBean {

	private ConfigSet config;
    private int row;
    //private	static final String	FILE_TYPE =	"p_file";			//		파일업로드되는 tag name
	//private	static final int FILE_LIMIT	= 5;					//	  페이지에 세팅된 파일첨부 갯수


	public SeminarBean() {
	    try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	}


	/**
	* 세미나 분류 리스트
	* @param box          receive from the form object and session
	* @return ArrayList    세미나 분류 리스트
	* @throws Exception
	*/
	public ArrayList selectListSeminarType(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet 		ls 			= null;
		ArrayList 		list 		= null;
        String 			sql    		= "";

		DataBox 		dbox 		= null;

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();
			
			
			sql = " SELECT CODE, CODENM FROM TZ_CODE WHERE GUBUN ='0061' ORDER BY CODE ";

			ls = connMgr.executeQuery(sql);

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
	* 세미나명 리스트
	* @param box          receive from the form object and session
	* @return ArrayList    세미나명 리스트
	* @throws Exception
	*/
	public ArrayList selectListSeminarName(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet 		ls 			= null;
		ArrayList 		list 		= null;
        String 			sql    		= "";

		DataBox 		dbox 		= null;

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();
			
			
			sql = " SELECT SEQ, SEMINARNM FROM TZ_SEMINAR GROUP BY SEQ, SEMINARNM ";

			ls = connMgr.executeQuery(sql);

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
	* 화면 리스트
	* @param box          receive from the form object and session
	* @return ArrayList    리스트(전체공지 제외)
	* @throws Exception
	*/
	public ArrayList selectListSeminar(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet 		ls 			= null;
		ArrayList 		list 		= null;
        String 			sql    		= "";
        String 			countSql  	= "";
        StringBuffer 	headSql   	= new StringBuffer();
		StringBuffer 	bodySql  	= new StringBuffer();
        String 			orderSql  	= "";

		DataBox 		dbox 		= null;

		String          v_selGroup     = box.getString("p_selGroup");
		String          v_selGubun     = box.getString("p_selGubun");
		String          v_strdate	   = box.getString("p_propstart");
		String          v_enddate	   = box.getString("p_propend");
		String 			v_selSeminarNm = box.getStringDefault("p_selSeminarNm", "ALL");

		int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			headSql.append(" SELECT                                                                                                                                                    \n ");
			headSql.append("         A.SEQ, A.SEMINARGUBUN, A.SEMINARNM, A.TNAME                                                                                                     \n ");
			headSql.append("         , A.STARTDATE, A.ENDDATE, A.PROPSTART, A.PROPEND                                                                                                   \n ");
			headSql.append("         , A.LIMITMEMBER, B.CODE, B.CODENM  SEMINARGUBUNNM                                                                                                         \n ");
			headSql.append("         , CASE WHEN  A.PASS_YN = 'Y' THEN                                                                                                                 \n ");
			headSql.append("                      'Y'                                                                                                                                  \n ");
			headSql.append("                WHEN  A.PASS_YN = 'N' THEN                                                                                                                 \n ");
			headSql.append("                      CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') BETWEEN SUBSTR(STARTDATE, 1, 8) || STARTTIME AND SUBSTR(ENDDATE, 1, 8)|| ENDTIME THEN   \n ");
			headSql.append("                                'A'                                                                                                                        \n ");
			headSql.append("                           WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') < SUBSTR(STARTDATE, 1, 8) THEN                                                          \n ");
			headSql.append("                                'B'                                                                                                                        \n ");
			headSql.append("                           ELSE                                                                                                                            \n ");
			headSql.append("                                A.PASS_YN                                                                                                                  \n ");
			headSql.append("                      END                                                                                                                                  \n ");
			headSql.append("         END PASS_YN, A.CNT                                                                                                                                      \n ");     

			bodySql.append(" FROM                                        \n ");
			bodySql.append("         TZ_SEMINAR A                        \n ");
			bodySql.append("         , TZ_CODE B                         \n ");
			bodySql.append(" WHERE                                       \n ");
			bodySql.append("         A.SEMINARGUBUN  = B.CODE(+)         \n ");
			bodySql.append(" AND     B.GUBUN(+)      = '0061'            \n ");

			
			if (!v_selGroup.equals("")) {
				  bodySql.append(" AND		GRCODECD LIKE '%"+v_selGroup+"%' \n");
			}

			if ( !v_strdate.equals("") && !v_enddate.equals("")) {      //    세미나 신청 기간 검색
				bodySql.append(" AND     (                              \n "); 
				bodySql.append("             A.PROPSTART BETWEEN "+ StringManager.makeSQL(v_strdate)+" AND "+ StringManager.makeSQL(v_enddate)+"  \n "); 
				bodySql.append("             OR                         \n "); 
				bodySql.append("             A.PROPEND BETWEEN "+ StringManager.makeSQL(v_strdate)+" AND "+ StringManager.makeSQL(v_enddate)+"  \n "); 
				bodySql.append("         )                              \n "); 

			}
			
			if (!v_selGubun.equals("")) { 
				bodySql.append(" AND     A.SEMINARGUBUN = "+ StringManager.makeSQL(v_selGubun)+"     \n ");
			}
			
			if(!v_selSeminarNm.equals("ALL")){
				bodySql.append(" AND     A.SEQ = "+ StringManager.makeSQL(v_selSeminarNm)+"    \n ");				
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
	* 세미나 신청자 리스트
	* @param box          receive from the form object and session
	* @return ArrayList    
	* @throws Exception
	*/
	public ArrayList selectRegList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet 		ls 			= null;
		ArrayList 		list 		= null;
        String 			sql    		= "";
        String 			countSql  	= "";
        StringBuffer 	headSql   	= new StringBuffer();
		StringBuffer 	bodySql  	= new StringBuffer();
        String 			orderSql  	= "";

		DataBox 		dbox 		= null;

		String          v_selGroup  = box.getString("p_selGroup");
		String          v_selGubun  = box.getString("p_selGubun");
		String          v_strdate	= box.getString("p_propstart");
		String          v_enddate	= box.getString("p_propend");
		String          v_flagPass  = box.getString("p_selPassYn");
		String  		v_orderColumn   = box.getString("p_orderColumn");               //정렬할 컬럼명
        String  		v_orderType     = box.getString("p_orderType");                 //정렬할 순서         
		int             v_seq		= box.getInt("p_selSeminarNm");
		

		int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			headSql.append(" SELECT                                                        \n ");
			headSql.append("         A.SEQ, A.SEMINARGUBUN, A.SEMINARNM, A.TNAME           \n ");
			headSql.append("         , A.STARTDATE, A.ENDDATE, A.PROPSTART, A.PROPEND      \n ");
			headSql.append("         , B.CODE, B.CODENM  SEMINARGUBUNNM                    \n ");
			headSql.append("         , C.PASS_YN, C.INDATE, D.NAME, C.USERID               \n ");
			                                                                               
			bodySql.append(" FROM                                                          \n ");
			bodySql.append("         TZ_SEMINAR A                                          \n ");
			bodySql.append("         , TZ_CODE B                                           \n ");
			bodySql.append("         , TZ_SEMINAR_APPLY C                                  \n ");
			bodySql.append("         , TZ_MEMBER D                                         \n ");
			bodySql.append(" WHERE                                                         \n ");
			bodySql.append("         A.SEMINARGUBUN  = B.CODE(+)                           \n ");
			bodySql.append(" AND     B.GUBUN(+)      = '0061'                              \n ");
			bodySql.append(" AND     C.SEQ           = A.SEQ(+)                            \n ");
			bodySql.append(" AND     C.USERID        = D.USERID(+)                         \n ");

			if (!v_selGroup.equals("")) {
				  bodySql.append(" AND		GRCODECD LIKE '%"+v_selGroup+"%' \n");
			}
			
			if(!v_selGubun.equals("")){
				bodySql.append(" AND		SEMINARGUBUN = '"+v_selGubun+"' \n");
			}

			if ( !v_strdate.equals("") && !v_enddate.equals("")) {      //    세미나 신청 기간 검색
				bodySql.append(" AND     (                              \n "); 
				bodySql.append("             A.PROPSTART BETWEEN "+ StringManager.makeSQL(v_strdate)+" AND "+ StringManager.makeSQL(v_enddate)+"  \n "); 
				bodySql.append("             OR                         \n "); 
				bodySql.append("             A.PROPEND BETWEEN "+ StringManager.makeSQL(v_strdate)+" AND "+ StringManager.makeSQL(v_enddate)+"  \n "); 
				bodySql.append("         )                              \n "); 

			}
			
			if ( !v_flagPass.equals("")){
				bodySql.append(" AND     A.PASS_YN    = "+ StringManager.makeSQL(v_flagPass)+" \n ");   
			}
			
			if ( v_seq != 0){
				bodySql.append(" AND     A.SEQ       = "+v_seq+"                \n "); 
			}
			
			if(v_orderColumn.equals("")) {
				orderSql = " ORDER BY C.LDATE DESC ";
            } else {
            	orderSql = " ORDER BY " + v_orderColumn + v_orderType;
            }
			
			sql= headSql.toString()+ bodySql.toString()+ orderSql;
			System.out.println(sql);
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
	* 세미나 신청자 리스트
	* @param box          receive from the form object and session
	* @return ArrayList    
	* @throws Exception
	*/
	public ArrayList selectEventList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet 		ls 			= null;
		ArrayList 		list 		= null;
        String 			sql    		= "";
        String 			countSql  	= "";
        StringBuffer 	headSql   	= new StringBuffer();
		StringBuffer 	bodySql  	= new StringBuffer();
        String 			orderSql  	= "";

		DataBox 		dbox 		= null;

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			headSql.append(" SELECT                                 \n ");  
			headSql.append("         A.SEQ                          \n ");  
			headSql.append("         , A.TITLE                      \n ");  
			bodySql.append(" FROM                                   \n ");
			bodySql.append("         TZ_EVENT A                     \n ");
			
			orderSql = " ORDER BY A.INDATE DESC ";
			
			sql= headSql.toString()+ bodySql.toString()+ orderSql;

			ls = connMgr.executeQuery(sql);

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
//=========운영자인화면 리스트 끝=========




	/**
	*  등록할때
	* @param box      receive from the form object and session
	* @return isOk    1:insert success,0:insert fail
	* @throws Exception
	*/
	public int insertSeminar(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		PreparedStatement pstmt = null;
		String sql  = "";
		StringBuffer insertSql = new StringBuffer();
		int isOk  = 0;
		
		int v_seq = 0;
		String v_startdate  = box.getString("p_startdate");
		String v_enddate    = box.getString("p_enddate");
		String v_shour		= box.getString("p_shour");
		String v_ehour		= box.getString("p_ehour");
		String v_propstart  = box.getString("p_propstart");
		String v_propend    = box.getString("p_propend");
		String v_serminarnm = box.getString("p_seminarnm");
		String v_content    = StringUtil.removeTag(box.getString("p_content"));
		String v_tname		= box.getString("p_tname");
		String v_seminargubun	= box.getString("p_seminargubun");
		int v_limitmember	= box.getInt("p_limitmember");
		String v_target     = box.getString("p_target");
		String v_use	    = box.getString("p_use");
		

		String s_userid   = box.getSession("userid");


		//Log.err.println("v_upfil1==> " + v_upfile);
		//Log.err.println("v_realfile1==> " + v_realfile);

		try {
		   connMgr = new DBConnectionManager();
		   connMgr.setAutoCommit(false);

		   sql  = "select max(seq) from TZ_SEMINAR  ";
		   ls = connMgr.executeQuery(sql);
		   if (ls.next()) {
			   v_seq = ls.getInt(1) + 1;
		   } else {
			   v_seq = 1;
		   }

		   /*********************************************************************************************/ 
		   // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다.                                                      
		   try {                                                                                           
			   v_content =(String) NamoMime.setNamoContent(v_content);                                     
		   } catch(Exception e) {                                                                          
			   System.out.println(e.toString());                                                           
			   return 0;                                                                                   
		   }                                                                                               
		   /*********************************************************************************************/ 

		   insertSql.append(" INSERT INTO TZ_SEMINAR     						\n ");
		   insertSql.append(" (   SEQ, SEMINARGUBUN, SEMINARNM, CONTENT     	\n ");
		   insertSql.append("     , STARTDATE, ENDDATE, STARTTIME, ENDTIME     	\n ");
		   insertSql.append("     , PROPSTART, PROPEND, LIMITMEMBER             \n ");
		   insertSql.append("     , TNAME, USEYN, TARGET, USERID, LUSERID     	\n ");
		   insertSql.append("     , INDATE , LDATE     							\n ");
		   insertSql.append(" )     											\n ");
		   insertSql.append(" VALUES     										\n ");
		   insertSql.append(" (   ?, ?, ?, ?, ?, ?, ?, ?, ?          			\n ");
		   insertSql.append("     , ? , ? , ? , ? , ? , ? , ?          			\n ");
		   insertSql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI')     		\n ");
		   insertSql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI')          \n ");
		   insertSql.append(" ) "); 
		   
		   int index = 1;
           pstmt = connMgr.prepareStatement(insertSql.toString());
           pstmt.setInt   (index++, v_seq);
           pstmt.setString(index++, v_seminargubun);
           pstmt.setString(index++, v_serminarnm);
           pstmt.setString(index++, v_content);
           pstmt.setString(index++, v_startdate);
           pstmt.setString(index++, v_enddate);
           pstmt.setString(index++, v_shour);
           pstmt.setString(index++, v_ehour);
           pstmt.setString(index++, v_propstart);
           pstmt.setString(index++, v_propend);
           pstmt.setInt   (index++, v_limitmember);
           pstmt.setString(index++, v_tname);
           pstmt.setString(index++, v_use);
           pstmt.setString(index++, v_target);
           pstmt.setString(index++, s_userid);
           pstmt.setString(index++, s_userid);
           
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
	*  수정하여 저장할때
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/
	 public int updateSeminar(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement 	pstmt = null;
		
		StringBuffer 	updateSql = new StringBuffer();
		int 			isOk 	  = 0;

		int v_seq           = box.getInt("p_seq");
		String v_startdate  = box.getString("p_startdate");
		String v_enddate    = box.getString("p_enddate");
		String v_shour		= box.getString("p_shour");
		String v_ehour		= box.getString("p_ehour");
		String v_propstart  = box.getString("p_propstart");
		String v_propend    = box.getString("p_propend");
		String v_serminarnm = box.getString("p_seminarnm");
		String v_content    = StringUtil.removeTag(box.getString("p_content"));
		String v_tname		= box.getString("p_tname");
		String v_seminargubun	= box.getString("p_seminargubun");
		int v_limitmember	= box.getInt("p_limitmember");
		String v_target     = box.getString("p_target");
		String v_use	    = box.getString("p_use");

		String s_userid    = box.getSession("userid");
		
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
	
		   updateSql.append(" UPDATE TZ_SEMINAR SET                                          \n ");
		   updateSql.append("         SEMINARGUBUN    = ?, SEMINARNM  = ?                    \n ");
		   updateSql.append("         , CONTENT       = ?, TNAME      = ?                    \n ");
		   updateSql.append("         , STARTDATE     = ?, ENDDATE    = ?                    \n ");
		   updateSql.append("         , STARTTIME     = ?, ENDTIME    = ?                    \n ");
		   updateSql.append("         , PROPSTART     = ?, PROPEND    = ?                    \n ");
		   updateSql.append("         , LIMITMEMBER   = ?, USEYN      = ?                    \n ");
		   updateSql.append("         , TARGET        = ?, LUSERID    = ?                    \n ");
		   updateSql.append("         , LDATE         = to_char(sysdate, 'YYYYMMDDHH24MISS') \n ");
		   updateSql.append(" WHERE   SEQ             = ?                                    \n ");

			pstmt = connMgr.prepareStatement(updateSql.toString());
			
			int index = 1;
	        pstmt.setString(index++, v_seminargubun);
	        pstmt.setString(index++, v_serminarnm);
	        pstmt.setString(index++, v_content);
	        pstmt.setString(index++, v_tname);
	        pstmt.setString(index++, v_startdate);
	        pstmt.setString(index++, v_enddate);
	        pstmt.setString(index++, v_shour);
	        pstmt.setString(index++, v_ehour);
	        pstmt.setString(index++, v_propstart);
	        pstmt.setString(index++, v_propend);
	        pstmt.setInt   (index++, v_limitmember);
	        pstmt.setString(index++, v_use);
	        pstmt.setString(index++, v_target);        //팝업여부
	        pstmt.setString(index++, s_userid);
			pstmt.setInt   (index++, v_seq);

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
	*  삭제할때
	* @param box      receive from the form object and session
	* @return isOk    1:delete success,0:delete fail
	* @throws Exception
	*/
	public int deleteSeminar(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement 	pstmt = null;
		
		String sql  = "";
		int    isOk = 0;

		int v_seq  = box.getInt("p_seq");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			sql  = " DELETE FROM TZ_EVENT WHERE SEQ = ? ";
			
			pstmt = connMgr.prepareStatement(sql);
			pstmt.setInt(1, v_seq);
			
			isOk = pstmt.executeUpdate();
			
			pstmt = connMgr.prepareStatement(sql);
			sql  = " DELETE FROM TZ_EVENT_APPLY WHERE SEQ = ? ";
			pstmt.setInt(1, v_seq);
			
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
	* 담청자 목록 가져오기
	* @param box          receive from the form object and session
	* @return ArrayList   조회한 상세정보
	* @throws Exception
	*/
  public ArrayList selectPassList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox dbox = null;
		ArrayList list = null;

		int v_seq = box.getInt("p_seq");
		String v_process = box.getString("p_process");

		try {
			connMgr = new DBConnectionManager();
			
			list = new ArrayList();

			sql.append(" SELECT                          \n ");
			sql.append("         A.USERID, B.NAME        \n ");
			sql.append(" FROM                            \n ");
			sql.append("         TZ_SEMINAR_APPLY A        \n ");
			sql.append("         , TZ_MEMBER B           \n ");
			sql.append(" WHERE                           \n ");
			sql.append("         A.USERID = B.USERID(+)  \n ");
			sql.append(" AND     A.PASS_YN = 'Y'          \n ");
			sql.append(" AND     A.SEQ    = " +v_seq);
			

			ls = connMgr.executeQuery(sql.toString());

			while (ls.next()) {
				dbox = ls.getDataBox();
				
				list.add(dbox);
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
		return list;
	}



	/**
	* 화면 상세보기
	* @param box          receive from the form object and session
	* @return ArrayList   조회한 상세정보
	* @throws Exception
	*/
   public DataBox selectViewSeminar(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox dbox = null;

		int v_seq = box.getInt("p_seq");
		String v_process = box.getString("p_process");

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
			sql.append("                          CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') BETWEEN STARTDATE || STARTTIME AND ENDDATE || ENDTIME THEN \n ");
			sql.append("                                    'A'                                                                                           \n ");
			sql.append("                               WHEN TO_CHAR(SYSDATE, 'YYYYMMDDHH24MI') < STARTDATE || STARTTIME THEN                              \n ");
			sql.append("                                    'B'                                                                                           \n ");
			sql.append("                               ELSE                                                                                               \n ");
			sql.append("                                    A.PASS_YN                                                                                     \n ");
			sql.append("                          END                                                                                                     \n ");
			sql.append("             END PASS_YN, PASS_CONTENT, BANNERIMG                                 \n ");                                                  
			sql.append(" FROM        TZ_SEMINAR A     										   \n ");  
			sql.append(" WHERE	 	 A.SEQ    = " +v_seq);
			
			ls = connMgr.executeQuery(sql.toString());

			while (ls.next()) {
				dbox = ls.getDataBox(); 
			}

			// 조회수 증가
			if(!v_process.equals("popupview")){
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
   
	/**
	*  수정하여 저장할때
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/
	 public int updateSeminarPass(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement 	pstmt = null;
		
		StringBuffer 	updateSql = new StringBuffer();
		int 			isOk 	  = 0;

		int v_seq          = box.getInt("p_seq");
		String v_pass_content   = box.getString("p_pass_content");

		String s_userid    = box.getSession("userid");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

		   /*********************************************************************************************/
		   // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다. 
		   try {
			   v_pass_content =(String) NamoMime.setNamoContent(v_pass_content);
		   } catch(Exception e) {
			   System.out.println(e.toString());
			   return 0;
		   }
		   /*********************************************************************************************/ 
	
		   updateSql.append(" UPDATE TZ_SEMINAR SET                                        \n ");
		   updateSql.append("         PASS_YN       = ?                                    \n ");
		   updateSql.append("         , PASS_CONTENT= ?, LUSERID     = ?                   \n ");
		   updateSql.append("         , LDATE       = to_char(sysdate, 'YYYYMMDDHH24MISS') \n ");
		   updateSql.append(" WHERE   SEQ           = ?                                    \n ");

			pstmt = connMgr.prepareStatement(updateSql.toString());
			
			int index = 1;
			pstmt.setString(index++, "Y");
			pstmt.setString(index++, v_pass_content);
	        pstmt.setString(index++, s_userid);
			pstmt.setInt   (index++, v_seq);

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
		*  세미나 신청자 당첨 여부 수정
		* @param box      receive from the form object and session
		* @return isOk    1:update success,0:update fail
		* @throws Exception
		*/
		 public int updateRegList(RequestBox box) throws Exception {
			DBConnectionManager connMgr = null;
			PreparedStatement 	pstmt = null;
			
			StringBuffer 	updateSql = new StringBuffer();
			int 			isOk 	  = 0;
			
			int vectorLen = 0;

			String v_totalPassYn	= box.getString("p_totalPassYn");
			Vector v_flagYn   	= box.getVector("p_flagYn");
			Vector v_seq   	    = box.getVector("ary_seq");
			Vector v_userid	    = box.getVector("ary_userid");

			String s_userid    = box.getSession("userid");

			try {
				connMgr = new DBConnectionManager();
				connMgr.setAutoCommit(false);
				
				
				vectorLen = v_seq.size();
				System.out.println("vectorLen : "+ vectorLen);
				for(int i = 0 ; i < vectorLen ; i++){
					updateSql.setLength(0); // StringBuffer 초기화
					
					updateSql.append(" UPDATE TZ_SEMINAR_APPLY SET                                  \n ");
					updateSql.append("         PASS_YN       = ?, LUSERID     = ?                   \n ");
					updateSql.append("         , LDATE       = to_char(sysdate, 'YYYYMMDDHH24MISS') \n ");
					updateSql.append(" WHERE   SEQ           = ?                                    \n ");
					updateSql.append(" AND     USERID        = ?                                    \n ");

					pstmt = connMgr.prepareStatement(updateSql.toString());
					
					int index = 1;
					
					if (v_totalPassYn.equals("Y") || v_totalPassYn.equals("N")){
						pstmt.setString(index++, v_totalPassYn);
					} else {
						pstmt.setString(index++, (String)v_flagYn.get(i));
					}
				    
				    pstmt.setString(index++, s_userid);
				    pstmt.setInt   (index++, Integer.parseInt((String)v_seq.get(i)));
				    pstmt.setString(index++, (String)v_userid.get(i));
				    System.out.println("(String)v_userid.get(i) : "+ (String)v_userid.get(i));
				    System.out.println("Integer.parseInt((String)v_seq.get(i)) : "+ Integer.parseInt((String)v_seq.get(i)));
					isOk = pstmt.executeUpdate();
					
					if (isOk == 0 ) break;
					
				}
		
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
	*  홈페이지 메인화면 리스트(최신5개)
	* @param box          receive from the form object and session
	* @return ArrayList    리스트(최신3개 전체공지 제외)
	* @throws Exception
	*/
	public ArrayList selectListNoticeMain(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;
		String v_login  = "";
        String v_comp   = box.getSession("comp");

        if(box.getSession("userid").equals("")){
        	v_login = "N";
        }else{
            v_comp = v_comp.substring(0, 4);
        	v_login = "Y";
        }

        int v_tabseq = box.getInt("p_tabseq");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			sql += " select * from ( select rownum rnum,  seq, addate, adtitle, adname, cnt, luserid, ldate from TZ_NOTICE  ";
			sql += "    where ";
			//sql += "      gubun = 'N' ";
			sql += "      and tabseq = " +  v_tabseq;
			sql += "      and useyn  = 'Y'";

			if(v_login.equals("Y")){  //로그인을 한경우
			  sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )";
			  sql += "      and ( gubun = 'N' and compcd like '%"+v_comp+"%') ";
			}else{ //로그인하지 않은경우
			  sql += "      and ( loginyn = 'AL' or loginyn = 'N' )";
			  sql += "      and gubun = 'Y'";
			}
			sql += "      and  ( ( popup = 'N' ) or (popup = 'Y' and uselist = 'Y') )";
			sql += "    order by seq desc  ) where rnum < 4                                                                       ";

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				//data = new NoticeData();
				//data.setSeq(ls.getInt("seq"));
				//data.setAddate(ls.getString("addate"));
				//data.setAdtitle(ls.getString("adtitle"));
				//data.setAdname(ls.getString("adname"));
				//data.setAdcontent(ls.getString("adcontent"));
				//data.setCnt(ls.getInt("cnt"));
				//data.setLuserid(ls.getString("luserid"));
				//data.setLdate(ls.getString("ldate"));
				//list.add(data);
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



//======================홈페이지Main사용==================

	/**
	* 팝업공지  리스트
	* @param box          receive from the form object and session
	* @return ArrayList   팝업공지 리스트
	* @throws Exception
	*/
	public ArrayList selectListNoticePopupHome(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;

        int    v_tabseq = box.getInt("p_tabseq");
        String v_login  = "";
        String tem_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        if(box.getSession("userid").equals("")){
        	v_login = "N";
        }else{
        	v_login = "Y";
        }

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();

			sql += " select           \n";
			sql += "   rownum,        \n";
			sql += "   seq,           \n";
			sql += "   addate,        \n";
			sql += "   adtitle,       \n";
			sql += "   adname,        \n";
			sql += "   adcontent,     \n";
			sql += "   cnt,           \n";
			sql += "   uselist,       \n";
			sql += "   useframe,      \n";
			sql += "   popwidth,      \n";
			sql += "   popheight,      \n";
			sql += "   popxpos,      \n";
			sql += "   popypos,      \n";
			sql += "   luserid,       \n";
			sql += "   ldate          \n";
			sql += " from TZ_NOTICE   \n";
			sql += "    where         \n";
			sql += "      tabseq = " +  v_tabseq;
			sql += "      and popup = 'Y'";
			sql += "      and to_char(sysdate, 'YYYYMMDD') between startdate and enddate";

			if(v_login.equals("Y")){  //로그인을 한경우
			  sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )";
			  sql += "      and ( grcodecd like '%"+tem_grcode+"%' )";
			}else{ //로그인하지 않은경우
			//로그인 전선택되거나 전체가 선택된경우
			  sql += "      and ( ( loginyn = 'AL' or loginyn = 'N' ) and grcodecd like '%"+tem_grcode+"%' )";
			}

            sql += "      and useyn= 'Y'";
            /*
			sql += "      and startdate <= " + StringManager.makeSQL(v_today);
			sql += "      and enddate   >= " + StringManager.makeSQL(v_today);
			*/
			sql += "    order by enddate desc ";

			ls = connMgr.executeQuery(sql);
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
	* 전체공지  리스트 (최신 전체 공지 3개만)
	* @param box          receive from the form object and session
	* @return ArrayList   전체공지 리스트
	* @throws Exception
	*/
	public ArrayList selectListNoticeTop(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");

        String v_login  = "";
        String tem_grcode        = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));


        if(box.getSession("userid").equals("")){
        	v_login = "N";
        }else{
        	v_login = "Y";
        }

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			/*
			sql  = " select seq, addate, adtitle, adname, cnt, luserid, ldate from                      ";
			sql += " ( select rownum, seq, addate, adtitle, adname,  cnt, luserid, ldate from TZ_NOTICE  ";
			//sql += "    where gubun = 'Y'                                                                          ";
			sql += "    where  ";
			sql += "      tabseq = " +  v_tabseq;
			sql += "      and useyn= 'Y'";
			sql += "      and (popup = 'N' or (popup = 'Y' and uselist='Y') )";

			if(v_login.equals("Y")){  //로그인을 한경우
			  sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )";
			  sql += "      and ( compcd like '%"+v_comp+"%' )";
			}else{ //로그인하지 않은경우
			//로그인 전선택되거나 전체가 선택된경우
			  sql += "      and ( ( loginyn = 'AL' or loginyn = 'N' ) and grcodecd like '%"+tem_grcode+"%' )";
			}
			sql += "    order by seq desc )";
			sql += " where rownum < 4";
			*/
			sql = "Select seq, addate, adtitle, adname, cnt, luserid, ldate   \n";
			sql += " From TZ_NOTICE	 \n";
			sql += " Where  ";
			sql += "      tabseq = " +  v_tabseq;
			sql += "      and useyn= 'Y'";
			sql += "      and (popup = 'N' or (popup = 'Y' and uselist='Y') )";

			if(v_login.equals("Y")){  //로그인을 한경우
			  sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )";
			  sql += "      and ( grcodecd like '%"+tem_grcode+"%' )";
			}else{ //로그인하지 않은경우
			//로그인 전선택되거나 전체가 선택된경우
			  sql += "      and ( ( loginyn = 'AL' or loginyn = 'N' ) and grcodecd like '%"+tem_grcode+"%' )";
			}
			sql += "    order by seq desc";

			ls = connMgr.executeQuery(sql);

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
	* 전체공지  리스트 (홈페이지more)
	* @param box          receive from the form object and session
	* @return ArrayList   전체공지 리스트
	* @throws Exception
	*/
	public ArrayList selectListNoticeAllHome(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;

		int v_tabseq = box.getInt("p_tabseq");

		String v_searchtext = box.getString("p_searchtext");
		String v_search     = box.getString("p_search");
		String v_login  = "";
        String v_comp   = box.getSession("comp");
        String tem_grcode        = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        if(box.getSession("userid").equals("")){
        	v_login = "N";
        }else{
            v_comp = v_comp.substring(0, 4);
        	v_login = "Y";
        }

        int v_pageno        = box.getInt("p_pageno");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			sql += " select  \n";
			// 수정일 : 05.11.17 수정자 : 이나연 _ rownum 쿼리 수정위해 오류 발생시킴
			sql += "    top 3      \n";
			sql += "    seq,         \n";
			sql += "    addate,      \n";
			sql += "    adtitle,     \n";
			sql += "    adname,      \n";
			sql += "    cnt,         \n";
			sql += "    luserid,     \n";
			sql += "    ldate,       \n";
			sql += "    isall,       \n";
			sql += "    useyn,       \n";
			sql += "    popup,       \n";
			sql += "    loginyn,     \n";
			sql += "    gubun,       \n";
			sql += "    compcd,      \n";
			sql += "    uselist,      \n";
			sql += "    aduserid,     \n";
			sql += "    filecnt       \n";
			sql += " from             \n";
			sql += " (select          \n";
			//
			//sql += "    rownum,        \n";
			sql += "    x.seq,         \n";
			sql += "    x.addate,      \n";
			sql += "    x.adtitle,     \n";
			sql += "    x.adname,      \n";
			sql += "    x.cnt,         \n";
			sql += "    x.luserid,     \n";
			sql += "    x.ldate,       \n";
			sql += "    x.isall,       \n";
			sql += "    x.useyn,       \n";
			sql += "    x.popup,       \n";
			sql += "    x.loginyn,     \n";
			sql += "    x.gubun,       \n";
			sql += "    x.compcd,      \n";
			sql += "    x.uselist,     \n";
			sql += "    x.tabseq,      \n";
			sql += "    x.adcontent,   \n";
			sql += "    x.aduserid,    \n";
			sql += "    x.grcodecd,    \n";
			sql += "	(select count(realfile) from tz_boardfile where tabseq = x.TABSEQ and seq = x.seq) filecnt ";
			sql += "  from       \n";
			sql += "    TZ_NOTICE x ) a";
			sql += "  where isall = 'Y' ";
			sql += "      and tabseq = " +  v_tabseq;
			sql += "      and useyn= 'Y'";
			sql += "      and (popup = 'N' or (popup = 'Y' and uselist='Y') )";

			if(v_login.equals("Y")){  //로그인을 한경우
			  sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )";
			  sql += "      and ( compcd like '%"+v_comp+"%' )";
			}else{ //로그인하지 않은경우
			//로그인 전선택되거나 전체가 선택된경우
			  sql += "      and ( ( loginyn = 'AL' or loginyn = 'N' ) and grcodecd like '%"+tem_grcode+"%' )";
			}

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면
				v_pageno = 1;   //      검색할 경우 첫번째 페이지가 로딩된다
				box.put("p_pageno", String.valueOf(v_pageno));
				if (v_search.equals("adtitle")) {                          //    제목으로 검색할때
					sql += " and adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
				} else if (v_search.equals("adcontents")) {                //    내용으로 검색할때
					sql += " and adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%");
				} else if (v_search.equals("adname")) {                //    작성자로 검색할때
					sql += " and adname like " + StringManager.makeSQL("%" + v_searchtext + "%");
				}
			}
//          sql += "    and rownum < 4";
			sql += "    order by seq desc                                                                    ";
			ls = connMgr.executeQuery(sql);
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
	* 일반 화면 리스트(홈페이지more)
	* @param box          receive from the form object and session
	* @return ArrayList    리스트(전체공지 제외)
	* @throws Exception
	*/
	public ArrayList selectListNoticeHome(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		// 수정일 : 05.11.16 수정자 : 이나연 _ totalcount 하기위한 쿼리수정
		String sql = "";
		String head_sql = "";
		String body_sql = "";
		String group_sql = "";
		String order_sql = "";
		String count_sql = "";

		DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");

        String v_login  = "";
        String v_comp   = box.getSession("comp");
        String tem_grcode        = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
        String v_searchtext = box.getString("p_searchtext");
		String v_search     = box.getString("p_search");
		int v_pageno        = box.getInt("p_pageno");

        if(box.getSession("userid").equals("")){
        	v_login = "N";
        }else{
            v_comp = v_comp.substring(0, 4);
        	v_login = "Y";
        }

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();

			//2005.11.18_하경태  : Oracle -> Mssql rownum 제거
			head_sql += " select  \n";
			//head_sql += "    rownum,      \n";
			head_sql += "    seq,         \n";
			head_sql += "    addate,      \n";
			head_sql += "    adtitle,     \n";
			head_sql += "    adname,      \n";
			head_sql += "    cnt,         \n";
			head_sql += "    luserid,     \n";
			head_sql += "    ldate,       \n";
			head_sql += "    isall,       \n";
			head_sql += "    useyn,       \n";
			head_sql += "    popup,       \n";
			head_sql += "    loginyn,     \n";
			head_sql += "    gubun,       \n";
			head_sql += "    compcd,      \n";
			head_sql += "    uselist,      \n";
			head_sql += "    aduserid,     \n";
			head_sql += "    filecnt       \n";
			body_sql += " from             \n";
			body_sql += " (select          \n";
			//body_sql += "    rownum,        \n";
			body_sql += "    x.seq,         \n";
			body_sql += "    x.addate,      \n";
			body_sql += "    x.adtitle,     \n";
			body_sql += "    x.adname,      \n";
			body_sql += "    x.cnt,         \n";
			body_sql += "    x.luserid,     \n";
			body_sql += "    x.ldate,       \n";
			body_sql += "    x.isall,       \n";
			body_sql += "    x.useyn,       \n";
			body_sql += "    x.popup,       \n";
			body_sql += "    x.loginyn,     \n";
			body_sql += "    x.gubun,       \n";
			body_sql += "    x.compcd,      \n";
			body_sql += "    x.uselist,     \n";
			body_sql += "    x.tabseq,      \n";
			body_sql += "    x.adcontent,   \n";
			body_sql += "    x.aduserid,    \n";
			body_sql += "    x.grcodecd,    \n";
			body_sql += "	(select count(realfile) from tz_boardfile where tabseq = x.TABSEQ and seq = x.seq) filecnt ";
			body_sql += "  from       \n";
			body_sql += "    TZ_NOTICE x ) a";
			body_sql += "  where ";
			body_sql += "  isall = 'N' ";
			body_sql += "      and tabseq = " +  v_tabseq;
			body_sql += "      and useyn= 'Y'";
			body_sql += "      and (popup = 'N' or (popup = 'Y' and uselist='Y') )";


			if(v_login.equals("Y")){  //로그인을 한경우
				body_sql += "      and ( loginyn = 'AL' or loginyn = 'Y' )";
				body_sql += "      and ( compcd like '%"+v_comp+"%' )";
			}else{ //로그인하지 않은경우
			//로그인 전선택되거나 전체가 선택된경우
				body_sql += "      and ( ( loginyn = 'AL' or loginyn = 'N' ) and grcodecd like '%"+tem_grcode+"%' )";
			}

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면
				v_pageno = 1;   //      검색할 경우 첫번째 페이지가 로딩된다
				if (v_search.equals("adtitle")) {                          //    제목으로 검색할때
					body_sql += " and adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
				} else if (v_search.equals("adcontents")) {                //    내용으로 검색할때
					body_sql += " and adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%");
				} else if (v_search.equals("adname")) {                //    작성자로 검색할때
					body_sql += " and adname like " + StringManager.makeSQL("%" + v_searchtext + "%");
				}
			}
			order_sql += " order by seq desc ";
			sql= head_sql+ body_sql+ group_sql+ order_sql;

			ls = connMgr.executeQuery(sql);
			count_sql= "select count(*) "+ body_sql;
			int total_row_count = BoardPaging.getTotalRow(connMgr,count_sql);    //     전체 row 수를 반환한다
			int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
			row = 7;
			ls.setPageSize(row);             				//  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);                    //     현재페이지번호를 세팅한다.

			while (ls.next()) {
				dbox = ls.getDataBox();
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
//------------------------------------------------------------------------------------------------



// ===============================회사 select method===============================
	/**
	*   명 select
	* @param box          receive from the form object and session
	* @return ArrayList   공지 리스트
	* @throws Exception
    */
	public ArrayList selectComp(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		NoticeData data = null;

	    String s_userid   = box.getSession("userid");
		String s_gadmin   = box.getSession("gadmin");
		String v_gadmin = "";

		if(!s_gadmin.equals("")){
		  v_gadmin = s_gadmin.substring(0,1);
		}

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
	   	 	sql += " select compnm, comp from tz_comp ";
	   		sql += "    where comptype='2'" ;
	   		sql += "    and isused = 'Y'" ;

		    if(v_gadmin.equals("K")){ //회사관리자
			  sql += "    and   comp IN (select comp from tz_compman where userid='"+s_userid+"' and gadmin='"+s_gadmin +"')";
		    } else if(v_gadmin.equals("H")){  //교육그룹관리자
		      sql += "    and   comp IN (select comp from tz_grcomp where grcode in (select grcode from tz_grcodeman where userid = '"+s_userid+"' and gadmin='"+s_gadmin +"' ) ) ";
		    }
			sql += "    order by companynm";

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
			  data = new NoticeData();
			  data.setCompnm(ls.getString("compnm"));
			  data.setComp(ls.getString("comp").substring(0,4));
			  list.add(data);
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

	// 05.12.15 이나연 _ 추가 (교육그룹별 select)
	public ArrayList selectEduGroup(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		EduGroupData data = null;

	    String s_userid   = box.getSession("userid");
		String s_gadmin   = box.getSession("gadmin");
		String v_gadmin = "";

		if(!s_gadmin.equals("")){
		  v_gadmin = s_gadmin.substring(0,1);
		}

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
	   	 	sql += " select grcodenm, grcode from tz_grcode ";

			if(v_gadmin.equals("H")){  //교육그룹관리자
		      sql += "    and   comp IN (select grcode from tz_grcodeman where userid = '"+s_userid+"' and gadmin='"+s_gadmin +"' )  ";
		    }
			sql += "    order by grcodenm";

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
			  data = new EduGroupData();
			  data.setGrcodenm(ls.getString("grcodenm"));
			  data.setGrcode(ls.getString("grcode"));
			  list.add(data);
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
	*   default comp 셋팅
	* @param box          receive from the form object and session
	* @return ArrayList   공지 리스트
	* @throws Exception
    */

	public String selectDefalutComp(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		//NoticeData data = null;

	    String s_userid   = box.getSession("userid");
		String s_gadmin   = box.getSession("gadmin");
		String v_gadmin = "";
		String returnValue = "";

		if(!s_gadmin.equals("")){
		  v_gadmin = s_gadmin.substring(0,1);
		}

		try {
			connMgr = new DBConnectionManager();

	   	 	sql += " select compnm, comp from tz_comp ";
	   		sql += "    where comptype='2'" ;
	   		sql += "    and isused = 'Y'" ;

		    if(v_gadmin.equals("K")){ //회사관리자
			  sql += "    and   comp IN (select comp from tz_compman where userid='"+s_userid+"' and gadmin='"+s_gadmin +"')";
		    } else if(v_gadmin.equals("H")){  //교육그룹관리자
		      sql += "    and   comp IN (select comp from tz_grcomp where grcode in (select grcode from tz_grcodeman where userid = '"+s_userid+"' and gadmin='"+s_gadmin +"' ) ) ";
		    }
			sql += "    order by companynm";

			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
			  //data = new NoticeData();
			  //data.setCompnm(ls.getString("compnm"));
			  //data.setComp(ls.getString("comp").substring(0,4));
              //dbox = ls.getDataBox();
              returnValue = ls.getString("comp");
              returnValue = returnValue.substring(0,4);
			}

			if(v_gadmin.equals("A")){
				returnValue = "ALL";
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
		return returnValue;
	}


	/**
	* 권한별 회사리스트
	* @param box          receive from the form object and session
	* @return ArrayList   권한별 회사리스트
	* @throws Exception
	*/

	public ArrayList selectCompany(RequestBox box, String compcd) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		NoticeData data = null;

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();

	   	 	sql += " select compnm, comp from tz_comp ";
	   		sql += "    where comptype='2' and substring(comp,0,4) in ("+compcd +")" ;
	   		sql += "    and isused = 'Y'" ;
			sql += "    order by companynm";
			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				data = new NoticeData();
				data.setCompnm(ls.getString("compnm"));
				//data.setComp(ls.getString("comp").substring(0,4));
				list.add(data);
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
	* 권한별 회사리스트
	* @param box          receive from the form object and session
	* @return ArrayList   권한별 회사리스트
	* @throws Exception
	*/

	public ArrayList selectGrcode(RequestBox box, String grcodecd) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		DataBox dbox = null;

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

	   	 	sql += " select grcodenm, grcode from tz_grcode ";
	   		sql += "    where grcode in ("+grcodecd+")" ;
			sql += "    order by grcodenm";

			ls = connMgr.executeQuery(sql);

			while (ls.next()) {
				dbox = (DataBox)ls.getDataBox();
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


// ===============================회사 select method end===============================



/*    *//**
    *  새로운 자료파일 등록
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    *//*

	 public	int	insertUpFile(DBConnectionManager connMgr, int p_tabseq, int p_seq, RequestBox	box) throws	Exception {
		ListSet	ls = null;
		PreparedStatement pstmt2 = null;
		String sql = "";
		String sql2	= "";
		int	isOk2 =	1;

		//----------------------   업로드되는 파일의 형식을	알고 코딩해야한다  --------------------------------

		String [] v_realFileName = new String [FILE_LIMIT];
		String [] v_newFileName	= new String [FILE_LIMIT];

		for(int	i =	0; i < FILE_LIMIT; i++)	{
			v_realFileName [i] = box.getRealFileName(FILE_TYPE + (i+1));
			v_newFileName [i] =	box.getNewFileName(FILE_TYPE + (i+1));
		}
		//----------------------------------------------------------------------------------------------------------------------------

		String s_userid	= box.getSession("userid");

		try	{
			 //----------------------	자료 번호 가져온다 ----------------------------
			sql	= "select NVL(max(fileseq),	0) from	tz_boardfile	where tabseq = "+p_tabseq+" and seq = " +	p_seq ;

			ls = connMgr.executeQuery(sql);
			ls.next();
			int	v_fileseq =	ls.getInt(1) + 1;
			ls.close();
			//------------------------------------------------------------------------------------

			//////////////////////////////////	 파일 table	에 입력	 ///////////////////////////////////////////////////////////////////
			sql2 =	"insert	into tz_boardfile(tabseq, seq, fileseq, realfile, savefile, luserid,	ldate)";
			sql2 +=	" values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

			pstmt2 = connMgr.prepareStatement(sql2);

			for(int	i =	0; i < FILE_LIMIT; i++)	{
				if(	!v_realFileName	[i].equals(""))	{		//		실제 업로드	되는 파일만	체크해서 db에 입력한다
					pstmt2.setInt(1, p_tabseq);
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
			FileManager.deleteFile(v_newFileName, FILE_LIMIT);		//	일반파일, 첨부파일 있으면 삭제..
			ErrorManager.getErrorStackTrace(ex,	box, sql2);
			throw new Exception("sql = " + sql2	+ "\r\n" + ex.getMessage());
		}
		finally	{
		    if(ls != null) { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
		}
		return isOk2;
	}


	*//**
	 * 선택된 자료파일 DB에서 삭제
	 * @param connMgr			DB Connection Manager
	 * @param box				receive from the form object and session
	 * @param p_filesequence    선택 파일 갯수
	 * @return
	 * @throws Exception
	 *//*
	public int deleteUpFile(DBConnectionManager	connMgr, RequestBox box, Vector p_filesequence)	throws Exception {
		PreparedStatement pstmt3 = null;
		String sql	= "";
		String sql3	= "";
        ListSet ls = null;
		int	isOk3 =	1;
        String v_type   = box.getString("p_type");
		int	v_seq =	box.getInt("p_seq");

		try	{

            //----------------------   어떤게시판인지정보를  가져와 tabseq를 리턴한다 ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            //------------------------------------------------------------------------------------

			sql3 = "delete from tz_boardfile where tabseq = "+ v_tabseq +" and seq =? and fileseq = ?";
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
	}*/
}
