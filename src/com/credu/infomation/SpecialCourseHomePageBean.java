//**********************"************************************
//  1. 제      목: 공지사항 관리
//  2. 프로그램명 : NoticeAdminBean.java
//  3. 개      요: 공지사항 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0공지사항
//  6. 작      성: 이창훈 2005. 7.  14
//  7. 수      정: 이나연 05.11.17 _ connMgr.setOracleCLOB 수정
//**********************************************************
package com.credu.infomation;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.course.EduGroupData;
import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.FormatDate;
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
         
public class SpecialCourseHomePageBean {


	public SpecialCourseHomePageBean() {
	}
	
//=========운영자인화면 리스트 시작=========

	/**
	* 공지사항화면 리스트
	* @param box          receive from the form object and session
	* @return ArrayList   공지사항 리스트(전체공지 제외)
	* @throws Exception
	*/
	public ArrayList selectList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls 			= null;
		ArrayList list 		= null;
        String sql    			= "";
        String count_sql  		= "";
        StringBuffer headSql 	= new StringBuffer();
        StringBuffer bodySql 	= new StringBuffer();
        String group_sql  		= "";
        String order_sql  		= "";
		
		int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		DataBox dbox 		= null;

		String v_searchtext = box.getString("p_searchtext");
		String v_search     = box.getString("p_search");
		
		String v_selClsf    = box.getStringDefault("p_selClsfCd", "ALL");
		String v_selDtl		= box.getStringDefault("p_selDtlCd", "ALL");

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			headSql.append(" SELECT                            \n ");
			headSql.append("         A.SEQ                     \n ");
			headSql.append("         , A.GUBUN                 \n ");
			headSql.append("         , A.CLSFCD                \n ");
			headSql.append("         , MST.CODENM    CLSFNM    \n ");
			headSql.append("         , A.DTLCD                 \n ");
			headSql.append("         , SUB.CODENM    DTLNM     \n ");
			headSql.append("         , A.TITLE                 \n ");
			headSql.append("         , A.PROFESSOR             \n ");
			headSql.append("         , A.CNT                   \n ");
			headSql.append("         , A.INDATE                \n ");
			headSql.append("         , A.USERID                \n ");
			headSql.append("         , A.NAME                  \n ");
			headSql.append("         , A.LDATE                 \n ");
			headSql.append("         , A.LUSERID               \n ");
			headSql.append("         , A.USEYN                 \n ");
			headSql.append("         , B.FILEYN                \n ");
			
			bodySql.append(" FROM                              \n ");
			bodySql.append("         TZ_SPECIALCOURSE A        \n ");
	        bodySql.append("         , (                                   \n ");
	        bodySql.append("                 SELECT  SEQ, 'Y' FILEYN       \n ");
	        bodySql.append("                 FROM    TZ_SPECIALCOURSE_FILE \n ");
	        bodySql.append("                 GROUP BY SEQ                  \n ");
	        bodySql.append("             ) B                               \n ");
			bodySql.append("         , TZ_CODE MST             \n ");
			bodySql.append("         , TZ_CODE SUB             \n ");
			bodySql.append(" WHERE   A.SEQ       = B.SEQ(+)    \n ");
			bodySql.append(" AND     A.CLSFCD    = MST.CODE(+) \n ");
			bodySql.append(" AND     A.DTLCD     = SUB.CODE(+) \n ");
			bodySql.append(" AND     MST.GUBUN(+)= '0087'      \n ");
			bodySql.append(" AND     SUB.GUBUN(+)= '0087'      \n ");
			bodySql.append(" AND     A.USEYN	 = 'Y'         \n ");
  
			if ( !v_searchtext.equals("")) {      //    검색어가 있으면
				if (v_search.equals("title")) {                          //    제목으로 검색할때
					bodySql.append("   and title like " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
				} else if (v_search.equals("content")) {                //    내용으로 검색할때
					bodySql.append("   and (content like " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
					bodySql.append("   or title like " + StringManager.makeSQL("%" + v_searchtext + "%") + ") \n");
				}
			}
			
			if( !v_selClsf.equals("ALL")) {
				bodySql.append(" AND	A.CLSFCD 	= " + StringManager.makeSQL(v_selClsf) + " \n");
			}
			
			if( !v_selDtl.equals("ALL")) {
				bodySql.append(" AND	A.DTLCD 	= " + StringManager.makeSQL(v_selDtl) + " \n");
			}
			
			order_sql += " order by indate desc ";

			sql= headSql.toString()+ bodySql.toString()+ order_sql;

			ls = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ bodySql.toString();
			
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);	//     전체 row 수를 반환한다
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            ls.setPageSize(v_pagesize);             		//  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);   //     현재페이지번호를 세팅한다.
			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(v_pagesize));
                dbox.put("d_totalrowcount",	new Integer(total_row_count));
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
	* 공지사항화면 상세보기
	* @param box          receive from the form object and session
	* @return ArrayList   조회한 상세정보
	* @throws Exception
	*/
   public DataBox selectView(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox dbox = null;

		String v_process = box.getString("p_process");
		
		int v_seq          = box.getInt("p_seq");
		
		Vector realfileVector = new Vector();
		Vector savefileVector = new Vector();
		Vector fileseqVector  = new Vector();

		try {
			connMgr = new DBConnectionManager();

			sql.append(" SELECT                                                       \n ");          
			sql.append("         A.SEQ                                                \n ");
			sql.append("         , A.GUBUN                                            \n ");
			sql.append("         , A.CLSFCD                                           \n ");          
			sql.append("         , MST.CODENM    CLSFNM                               \n ");          
			sql.append("         , A.DTLCD                                            \n ");          
			sql.append("         , SUB.CODENM    DTLNM                                \n ");     
			sql.append("         , A.TITLE                                            \n ");     
			sql.append("         , A.CONTENT                                          \n ");     
			sql.append("         , A.PROFESSOR                                        \n ");     
			sql.append("         , NVL(A.CNT, 0) CNT                                  \n ");     
			sql.append("         , A.INDATE                                           \n ");     
			sql.append("         , A.USERID                                           \n ");     
			sql.append("         , A.NAME                                             \n ");     
			sql.append("         , A.LDATE                                            \n ");     
			sql.append("         , A.LUSERID                                          \n ");     
			sql.append("         , A.PICTUREURL                                       \n ");     
			sql.append("         , A.USEYN                                            \n ");     
			sql.append("         , DECODE(PICTUREURL, NULL, 'N', 'Y') PICTURE_YN      \n ");     
			sql.append("         , B.REALFILE                                         \n ");
			sql.append("         , B.SAVEFILE                                         \n ");
			sql.append("         , B.FILESEQ                                          \n ");
			sql.append(" FROM                                                         \n ");     
			sql.append("         TZ_SPECIALCOURSE A                                   \n ");      
			sql.append("         , TZ_CODE MST                                        \n ");      
			sql.append("         , TZ_CODE SUB                                        \n ");      
			sql.append("         , TZ_SPECIALCOURSE_FILE B                            \n ");                                              
			sql.append(" WHERE                                                        \n ");      
			sql.append("         A.CLSFCD    = MST.CODE(+)                            \n ");      
			sql.append(" AND     A.DTLCD     = SUB.CODE(+)                            \n ");      
			sql.append(" AND     MST.GUBUN(+)= '0087'                                 \n ");      
			sql.append(" AND     SUB.GUBUN(+)= '0087'                                 \n ");      
			sql.append(" AND     A.SEQ       = B.SEQ(+)                               \n ");                             
			sql.append(" AND     A.SEQ       = " + v_seq);

			ls = connMgr.executeQuery(sql.toString());

			while (ls.next()) {
				dbox = ls.getDataBox();
				realfileVector.addElement(dbox.getString("d_realfile"));
                savefileVector.addElement(dbox.getString("d_savefile"));
                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
			}

			if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
			if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
			if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);

			// 조회수 증가
			if(!v_process.equals("selectView")){
			  connMgr.executeUpdate("update TZ_SPECIALCOURSE set cnt = cnt + 1 WHERE SEQ =  " + v_seq);
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
