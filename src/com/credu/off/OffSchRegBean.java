//**********************************************************
//  1. 제      목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개      요:  관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수      정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.off;

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
public class OffSchRegBean {

	private ConfigSet config;
    private int row;
    private	static final String	FILE_TYPE =	"p_file";			//		파일업로드되는 tag name
	private	static final int FILE_LIMIT	= 1;					//	  페이지에 세팅된 파일첨부 갯수


	public OffSchRegBean() {
	    try{
            config = new ConfigSet();
            //row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
            row = Integer.parseInt(config.getProperty("page.manage.row"));  //이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }

	}

	/**
	* 학적관리 리스트
	* @param box          receive from the form object and session
	* @return ArrayList   학적관리 리스트
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

		String 			v_searchtext = box.getString("p_searchtext");
		String          v_subjsearchkey = box.get("s_subjsearchkey");
		
		int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");
        
        String 			v_orderColumn = box.getString("p_orderColumn");
        String 			v_orderType = box.getString("p_orderType");
        
        String          v_year       	= box.getStringDefault("s_year"       , "ALL");
        String          v_subjcode      = box.getStringDefault("s_subjcode"   , "ALL");
        String          v_upperclass    = box.getStringDefault("s_upperclass" , "ALL");
        String          v_middleclass   = box.getStringDefault("s_middleclass", "ALL");
        String          v_lowerclass    = box.getStringDefault("s_lowerclass" , "ALL");
        String          v_state         = box.getStringDefault("p_selState"   , "ALL");
        
		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			
			headSql.append(" SELECT  A.SUBJ, B.SUBJNM, A.SUBJSEQ, B.SUBJSEQ, C.NAME \n");
			headSql.append("         , A.STUDENTNO, A.STUSTATUS, E.CODENM STATENM   \n");
			headSql.append("         , A.LDATE, A.CONFIRMDATE, A.YEAR, A.USERID, F.ISTERM, A.ISGRADUATED  \n");
			bodySql.append(" FROM    TZ_OFFSTUDENT A, TZ_OFFSUBJSEQ B, TZ_MEMBER C  \n");
			bodySql.append("         , TZ_CODE E, TZ_OFFSUBJ F                      \n");
			bodySql.append(" WHERE   A.SUBJSEQ   = B.SUBJSEQ(+)                     \n");
			bodySql.append(" AND     A.SUBJ      = B.SUBJ(+)                        \n");
			bodySql.append(" AND     A.YEAR      = B.YEAR(+)                        \n");
			bodySql.append(" AND     A.SUBJSEQ   = B.SUBJSEQ(+)                     \n");
			bodySql.append(" AND     A.USERID    = C.USERID(+)                      \n");
			bodySql.append(" AND     A.STUSTATUS = E.CODE(+)                        \n");
			bodySql.append(" AND     E.GUBUN(+)  = '0089'                           \n");
			bodySql.append(" AND     A.SUBJ      = F.SUBJ(+)                        \n");       
			bodySql.append(" AND     B.SEQ(+)    = 1                                \n");

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면                                         
				bodySql.append(" AND ( UPPER(C.USERID) LIKE UPPER('%").append(v_searchtext).append("%') \n");  
				bodySql.append(" OR UPPER(C.NAME) LIKE UPPER('%").append(v_searchtext).append("%')) \n");
			}
			
			if ( !v_subjsearchkey.equals("")) {      //    과정명 검색어가 있으면                                         
				bodySql.append(" AND UPPER(B.SUBJNM) LIKE UPPER('%").append(v_subjsearchkey).append("%') \n");  
			}  
			
			if(!v_year.equals("ALL")){
				bodySql.append(" AND	 A.YEAR     = ").append(StringManager.makeSQL(v_year)).append(" \n");
			}
			if(!v_subjcode.equals("ALL")){
				bodySql.append(" AND	 F.SUBJ     = ").append(StringManager.makeSQL(v_subjcode)).append(" \n");
			}
			if(!v_upperclass.equals("ALL")){
				bodySql.append(" AND	 F.UPPERCLASS     = ").append(StringManager.makeSQL(v_upperclass)).append(" \n");
			}
			if(!v_middleclass.equals("ALL")){
				bodySql.append(" AND	 F.MIDDLECLASS     = ").append(StringManager.makeSQL(v_middleclass)).append(" \n");
			}
			if(!v_lowerclass.equals("ALL")){
				bodySql.append(" AND	 F.LOWERCLASS     = ").append(StringManager.makeSQL(v_lowerclass)).append(" \n");
			}
			if(!v_state.equals("ALL")){
				bodySql.append(" AND	 A.STUSTATUS     = ").append(StringManager.makeSQL(v_state)).append(" \n");
			}

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
	*  학적 변경내역 저장
	* @param box      receive from the form object and session
	* @return isOk    1:insert success,0:insert fail
	* @throws Exception
	*/
	public int insertState(RequestBox box, DBConnectionManager connMgr) throws Exception {
		
		ListSet 			ls 		= null;
		PreparedStatement 	pstmt 	= null;
		String 				seqSql  = "";
		StringBuffer 		insertSql = new StringBuffer();
		
		int isOk  = 0;
		int isOk2  = 0;
		int v_seq = 0;

        String v_subj          = box.getString("p_subj");
        String v_year          = box.getString("p_year");
        String v_subjseq       = box.getString("p_subjseq");
        String v_state         = box.getString("p_stustate");
        String v_changereason  = box.getString("p_changereason");
        String v_userid		   = box.getString("p_userid");	
        
        String s_userid   = box.getSession("userid");
		
		try {
		   connMgr.setAutoCommit(false);

		   seqSql  = "SELECT MAX(seq) FROM TZ_OFFSCHEDULE";
		   ls = connMgr.executeQuery(seqSql);
		   if (ls.next()) {
			   v_seq = ls.getInt(1) + 1;
		   } else {
			   v_seq = 1;
		   }
           
		   insertSql.append(" INSERT INTO TZ_OFFSCHEDULE                                       \n");
		   insertSql.append(" (   SUBJ, YEAR, SUBJSEQ, USERID, SEQ, STATE                      \n");
		   insertSql.append("     , CHANGEDATE, CHANGEREASON, LUSERID, LDATE                   \n");
		   insertSql.append(" )                          									   \n");
		   insertSql.append(" VALUES                                                           \n");
		   insertSql.append(" (   ?, ?, ?, ? , ? , ?, to_char(sysdate, 'YYYYMMDDHH24MISS')     \n");
		   insertSql.append("     , ? , ? , to_char(sysdate, 'YYYYMMDDHH24MISS') 			   \n");
		   insertSql.append(" ) ");


		   int index = 1;
           pstmt = connMgr.prepareStatement(insertSql.toString());
           pstmt.setString(index++,  v_subj);
           pstmt.setString(index++,  v_year);
           pstmt.setString(index++,  v_subjseq);
           pstmt.setString(index++,  v_userid);
           pstmt.setInt   (index++,  v_seq);
           pstmt.setString(index++,  v_state);
           pstmt.setString(index++,  v_changereason);
           pstmt.setString(index++,  s_userid);
           
           isOk = pstmt.executeUpdate();
           
           //if(isOk > 0){
           //	  connMgr.commit();
           //	}else{
           //	  connMgr.rollback();
           //	}
           //Log.err.println("isOk==> " + isOk);
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, insertSql.toString());
			throw new Exception("sql ->" + insertSql.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); } catch (Exception e) {} }
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }
			//if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return isOk;
	}


	/**
	*  학적 수정
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/
	public int updateState(RequestBox box) throws Exception {
		DBConnectionManager connMgr = new DBConnectionManager();
		int isOk = 0;
		try {
			isOk = this.updateState(box, connMgr);
		}catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, "");
			throw new Exception("error : updateState(RequestBox box)" + "\r\n" + ex.getMessage());
		}
		finally {
			if(connMgr != null) { try { connMgr.setAutoCommit(true); connMgr.freeConnection(); }catch (Exception e10) {} }
		}
	    return isOk;
	}
	
	
	 public int updateState(RequestBox box, DBConnectionManager connMgr) throws Exception {
		PreparedStatement pstmt = null;
		StringBuffer updateSql = new StringBuffer();
		int isOk = 0;
		int isOk2 = 0;
		
		String v_subj          = box.getString("p_subj");
        String v_year          = box.getString("p_year");
        String v_subjseq       = box.getString("p_subjseq");
        String v_state         = box.getString("p_stustate");
        String v_userid		   = box.getString("p_userid");	
        String v_oldstate	   = box.getString("p_oldstate");
		
        String s_userid        = box.getSession("userid");
        
        String v_isgraduated   = v_state.equals("5") ? "Y" : "N" ; // 학적이 졸업상태이면 'Y'
        String v_serno		   = "";

		try {
			
	        // 변경사항이 '졸업상태(gubun : 0089, code : 5) 이면 수료번호를 부여
			if(!v_oldstate.equals(v_state) && (v_state.equals("5") || v_state.equals("Y"))){
	        	v_serno = "0123456789"; // 수료번호 부여 로직이 들어갈 예정
	        }

			updateSql.append(" UPDATE TZ_OFFSTUDENT                                            \n");
			updateSql.append(" SET                                                             \n");
			updateSql.append("         STUSTATUS   		= ?                                    \n");
    		updateSql.append("         , SERNO       	= ?                                    \n");
			updateSql.append("         , ISGRADUATED   	= ?                                    \n");
			updateSql.append("         , LUSERID   		= ?                                    \n");
			updateSql.append("         , LDATE     		= to_char(sysdate, 'YYYYMMDDHH24MISS') \n");
			updateSql.append(" WHERE                                                           \n");
			updateSql.append("         SUBJ        = ?                                         \n");
			updateSql.append(" AND     YEAR        = ?                                         \n");
			updateSql.append(" AND     SUBJSEQ     = ?                                         \n");
			updateSql.append(" AND     USERID      = ?                                         \n");


			pstmt = connMgr.prepareStatement(updateSql.toString());
			
			int index = 1;
			
			pstmt.setString(index++, v_state);
			pstmt.setString(index++, v_serno);
			pstmt.setString(index++, v_isgraduated);
			pstmt.setString(index++, s_userid);
			pstmt.setString(index++, v_subj);
			pstmt.setString(index++, v_year);
			pstmt.setString(index++, v_subjseq);
			pstmt.setString(index++, v_userid);

			isOk = pstmt.executeUpdate();
			
			isOk2 = this.insertState(box, connMgr);
			
			if(isOk > 0 && isOk2 > 0){
				connMgr.commit();
				isOk =1;
			}
			else{
				connMgr.rollback();
				isOk =0;
			}
		}
		catch(Exception ex) {
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, updateSql.toString());
			throw new Exception("sql = " + updateSql.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
		}
		return isOk;
	}


	/**
	* 학적 상세보기
	* @param box          receive from the form object and session
	* @return ArrayList   학적 상세보기
	* @throws Exception
	*/
   public DataBox selectView(RequestBox box) throws Exception {
	 
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox dbox = null;
		DataBox dbox2 = null;
		
		String v_subj          = box.getString("p_subj");
        String v_year          = box.getString("p_year");
        String v_subjseq       = box.getString("p_subjseq");
        String v_userid		   = box.getString("p_userid");	

		Vector stateHistory  = new Vector();

		try {
			connMgr = new DBConnectionManager();

			sql.append(" SELECT  A.SUBJ, B.SUBJNM, A.SUBJSEQ, B.SUBJSEQ, C.NAME \n");
			sql.append("         , A.STUDENTNO, A.STUSTATUS, E.CODENM STATENM   \n");
			sql.append("         , A.LDATE, A.CONFIRMDATE, A.YEAR, A.USERID , F.ISTERM    \n");
			sql.append(" FROM    TZ_OFFSTUDENT A, TZ_OFFSUBJSEQ B, TZ_MEMBER C  \n");
			sql.append("         , TZ_OFFSUBJ D, TZ_CODE E, TZ_OFFSUBJ F        \n");
			sql.append(" WHERE   A.SUBJSEQ   = B.SUBJSEQ(+)                     \n");
			sql.append(" AND     A.SUBJ      = B.SUBJ(+)                        \n");
			sql.append(" AND     A.YEAR      = B.YEAR(+)                        \n");
			sql.append(" AND     A.USERID    = C.USERID(+)                      \n");
			sql.append(" AND     A.STUSTATUS = E.CODE(+)                        \n");
			sql.append(" AND     E.GUBUN(+)  = '0089'                           \n");
			sql.append(" AND     A.SUBJ      = F.SUBJ(+)                        \n");
			sql.append(" AND     A.SUBJ      = D.SUBJ(+)                        \n");
			sql.append(" AND     A.SUBJSEQ   = '").append(v_subjseq).append("'  \n");
			sql.append(" AND     A.SUBJ      = '").append(v_subj).append("'     \n");
			sql.append(" AND     A.YEAR      = '").append(v_year).append("'     \n");
			sql.append(" AND     A.USERID    = '").append(v_userid).append("'   \n");
			sql.append(" AND     B.SEQ       = 1                                \n");
			
			ls = connMgr.executeQuery(sql.toString());
			
			while(ls.next()){
				dbox	= ls.getDataBox();
			}
			
			sql.setLength(0);
			
			sql.append(" SELECT  SEQ, STATE, CHANGEDATE, CHANGEREASON, B.CODENM statenm          \n");
			sql.append(" FROM    TZ_OFFSCHEDULE A, TZ_CODE B, TZ_OFFSUBJ C					\n");
			sql.append(" WHERE   A.SUBJSEQ   = '").append(v_subjseq).append("'  \n");
			sql.append(" AND     A.SUBJ      = '").append(v_subj).append("'     \n");
			sql.append(" AND     A.YEAR      = '").append(v_year).append("'     \n");
			sql.append(" AND     A.USERID    = '").append(v_userid).append("'   \n");
			sql.append(" AND	 A.SUBJ      = C.SUBJ(+)                        \n");
			sql.append(" AND	 A.STATE     = B.CODE(+)                        \n");
			sql.append(" AND     B.gubun   = DECODE(C.ISTERM, 'Y', '0089', 'N', '0100', '0089')                     	    \n");
			sql.append(" ORDER BY A.CHANGEDATE DESC                   	    \n");
			 
			ls = connMgr.executeQuery(sql.toString());
			
			while(ls.next()){
				dbox2	= ls.getDataBox();
				
				stateHistory.add(dbox2);
			}
			
			dbox.put("d_stateHistory", stateHistory);
			
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
