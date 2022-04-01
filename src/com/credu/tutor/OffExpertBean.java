//**********************************************************
//  1. 제      목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개      요:  관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수      정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.tutor;

import java.sql.PreparedStatement;
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

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class OffExpertBean {

	private ConfigSet config;
    private int row;

	public OffExpertBean() {
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
	public ArrayList selectListOffExpert(RequestBox box) throws Exception {
		
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

        int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			headSql.append(" SELECT  A.SEQ, A.GUBUN, B.CODENM GUBUNNM, A.COMP, A.DEPT        \n ");
			headSql.append("         , A.MOBILE_PHONE, A.APPROVE_YN, A.NAME, A.REG_YN, A.INDATE \n ");
			bodySql.append(" FROM    TZ_PROFESSIONAL A, TZ_CODE B                            \n ");
			bodySql.append(" WHERE   A.GUBUN     = B.CODE(+)                                 \n ");
			bodySql.append(" AND     B.GUBUN(+)  = '0083'                                    \n ");
			
			orderSql = " ORDER BY     A.INDATE DESC ";
			

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면                                         
				if (v_search.equals("name")) {                          //    제목으로 검색할때  
					bodySql.append(" AND A.NAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");  
				} else if (v_search.equals("comp")) {                //    내용으로 검색할때  
					bodySql.append(" AND A.COMP LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");
				}                                                                                   
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
	*  수정하여 저장할때
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/
	 public int updateOffExpert(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer updateSql = new StringBuffer();
		int isOk = 0;
		int isOk2 = 0;
		
		int    	v_seq       	    = box.getInt   ("p_seq");
		String	v_approve_yn	    = box.getString("p_approve_yn");
		String  v_reg_yn		    = box.getString("p_reg_yn");
		String  v_living_place      = box.getString("p_living_place");
		Vector  v_special_field     = box.getVector("p_special_field");
		StringBuffer  v_special_fields	= new StringBuffer();
		
		String  s_userid            = box.getSession("userid");
		
		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			
			for(int i = 0 ; i < v_special_field.size() ; i++) {
				v_special_fields.append((String)v_special_field.get(i));
				v_special_fields.append("|");
			}

			updateSql.append(" UPDATE TZ_PROFESSIONAL SET          \n");
		    updateSql.append("     APPROVE_YN          = ?         \n");
		    updateSql.append("     , REG_YN            = ?         \n");
		    updateSql.append("     , SPECIAL_FIELD     = ?         \n");
		    updateSql.append("     , LIVING_PLACE      = ?		   \n");	
		    updateSql.append("     , LUSERID           = ?         \n");
		    updateSql.append("     , LDATE             = to_char(sysdate, 'YYYYMMDDHH24MISS') \n");
		    updateSql.append(" WHERE                               \n");
		    updateSql.append("     SEQ = ?                         \n");

			pstmt = connMgr.prepareStatement(updateSql.toString());
			
			int index = 1;
			
            pstmt.setString(index++,  v_approve_yn);
            pstmt.setString(index++,  v_reg_yn);
            pstmt.setString(index++,  v_special_fields.toString());
            pstmt.setString(index++,  v_living_place);
            pstmt.setString(index++,  s_userid);
            pstmt.setInt   (index++,  v_seq);

			isOk = pstmt.executeUpdate();
			
			isOk2 = changeFile(box, connMgr);
			
			if(isOk > 0 && isOk2 > 0){
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
   public DataBox selectViewOffExpert(RequestBox box) throws Exception {
	 
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox dbox = null;

		int v_seq = box.getInt("p_seq");

		try {
			connMgr = new DBConnectionManager();

			sql.append(" SELECT                                                            \n ");
			sql.append("         A.SEQ, A.GUBUN, A.ENG_NAME, A.NAME, A.RESNO               \n ");
			sql.append("         , A.COMP, A.DEPT, A.COMP_POST1, A.COMP_POST2              \n ");
			sql.append("         , A.COMP_ADDR1, A.COMP_ADDR2, A.COMP_TEL, A.COMP_FAX      \n ");
			sql.append("         , A.MOBILE_PHONE, A.EMAIL, A.POST1, A.POST2, A.ADDR1      \n ");
			sql.append("         , A.ADDR2, A.HOME_TEL, A.LIVING_PLACE, A.EDUCATION        \n ");
			sql.append("         , A.INTRODUCE, A.PHOTO_REAL_FILE, A.PHOTO_SAVE_FILE       \n ");
			sql.append("         , A.INTRO_REAL_FILE, A.INTRO_SAVE_FILE, A.SPECIAL_FIELD   \n ");
			sql.append("         , A.APPROVE_YN, A.REG_YN, A.INDATE                        \n ");
			sql.append("         , C.CODENM GUBUNNM, D.CODENM EDUCATIONNM                  \n ");
			sql.append(" FROM                                                              \n ");
			sql.append("         TZ_PROFESSIONAL A                                         \n ");
			sql.append("         , TZ_CODE C                                               \n ");
			sql.append("         , TZ_CODE D                                               \n ");
			sql.append(" WHERE                                                             \n ");
			sql.append("         A.GUBUN     = C.CODE(+)                                   \n ");
			sql.append(" AND     C.GUBUN(+)  = '0083'                                      \n ");
			sql.append(" AND     A.EDUCATION = D.CODE(+)                                   \n ");
			sql.append(" AND     D.GUBUN(+)  = '0069'                                      \n ");
			sql.append(" AND     A.SEQ       = "+v_seq);
			
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
   
   public int changeFile(RequestBox box, DBConnectionManager connMgr) throws Exception{
	   
		PreparedStatement pstmt = null;
		StringBuffer updateSql = new StringBuffer();
		int isOk = 0;
		
		int    	v_seq       	    = box.getInt   ("p_seq");
		String	v_photo_real_file	= "";
	    String	v_photo_save_file	= "";
	    String  v_intro_real_file	= "";
	    String  v_intro_save_file	= "";
		
		String  v_photo_del_check   = box.getString("p_photoDelCheck");
		String  v_intro_del_check   = box.getString("p_introDelCheck");
		
		String  v_photo_file		= box.getString("p_photo_real_file");
		String  v_intro_file		= box.getString("p_intro_real_file");
		
		if ( v_photo_del_check.equals("Y")) {
			FileManager.deleteFile(box.getString("p_photo_save_file"));
			
			v_photo_real_file	= box.getRealFileName("p_photo_file");
			v_photo_save_file	= box.getNewFileName("p_photo_file");
		} else if (!v_photo_file.equals("")) {
			v_photo_real_file	= box.getString("p_photo_real_file");
			v_photo_save_file	= box.getString("p_photo_save_file");
		} else {
			v_photo_real_file	= box.getRealFileName("p_photo_file");
			v_photo_save_file	= box.getNewFileName("p_photo_file");
		}
		
		if ( v_intro_del_check.equals("Y")) {
			FileManager.deleteFile(box.getString("p_intro_save_file"));
			
			v_intro_real_file	= box.getRealFileName("p_intro_file");
			v_intro_save_file	= box.getNewFileName("p_intro_file");
		} else if (!v_intro_file.equals("")) {
			v_intro_real_file	= box.getString("p_photo_real_file");
			v_intro_save_file	= box.getString("p_photo_save_file");
		} else {
			v_intro_real_file	= box.getRealFileName("p_intro_file");
			v_intro_save_file	= box.getNewFileName("p_intro_file");
		}
		
		try {

		   updateSql.append(" UPDATE TZ_PROFESSIONAL SET          \n");
		   updateSql.append("     PHOTO_REAL_FILE   = ?           \n");
		   updateSql.append("     , PHOTO_SAVE_FILE   = ?         \n");
		   updateSql.append("     , INTRO_REAL_FILE   = ?         \n");
		   updateSql.append("     , INTRO_SAVE_FILE   = ?         \n");
		   updateSql.append(" WHERE                               \n");
		   updateSql.append("     SEQ = ?                         \n");
		   pstmt = connMgr.prepareStatement(updateSql.toString());
			
		   int index = 1;
			
		   pstmt.setString(index++,  v_photo_real_file);
		   pstmt.setString(index++,  v_photo_save_file);
		   pstmt.setString(index++,  v_intro_real_file);
		   pstmt.setString(index++,  v_intro_save_file);
           pstmt.setInt   (index++,  v_seq);

		   isOk = pstmt.executeUpdate();
			
			if(isOk > 0){
				isOk =1;
			}
			else{
				isOk =0;
			}
		}
		catch(Exception ex) {connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, updateSql.toString());
			throw new Exception("sql = " + updateSql.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
		}
	   
	   return isOk;
   }
   
	/**
	* 전문가 관리 리스트
	* @param box          receive from the form object and session
	* @return ArrayList   전문가 관리 리스트
	* @throws Exception
	*/
	public ArrayList selectListGubun(RequestBox box) throws Exception {
		
		DBConnectionManager connMgr = null;
		ListSet 		ls 		= null;
		ListSet 		ls2		= null;
		ArrayList 		list 	= null;
		ArrayList		subList = null;
		StringBuffer 	sql1 	= new StringBuffer();
		StringBuffer 	sql2 	= new StringBuffer();
		DataBox 		dbox 	= null;
		DataBox 		dbox2 	= null;

		String v_parent = box.getString("p_gubun");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();

			sql1.append(" SELECT                                                         \n ");
			sql1.append("         A.CODE PARENT_CODE, A.CODENM PARENT_NAME               \n ");
			sql1.append("         , B.CODE UPPER_CODE, B.CODENM UPPER_NAME               \n ");
			sql1.append("         , COUNT(*) OVER (PARTITION BY A.CODE)    AS PARENT_CNT \n ");
			sql1.append(" FROM    TZ_CODE A, TZ_CODE B                                   \n ");
			sql1.append(" WHERE                                                          \n ");
			sql1.append("         B.UPPER = A.CODE                                       \n ");
			sql1.append(" AND     B.LEVELS = 2                                           \n ");
			sql1.append(" AND     A.GUBUN = '0083'                                       \n ");
			sql1.append(" AND     B.GUBUN = '0083'                                       \n ");
			//sql1.append(" AND     B.PARENT = '"+v_parent+"'                              \n ");
			sql1.append(" ORDER BY A.CODE, B.CODE                                        \n "); 

			ls = connMgr.executeQuery(sql1.toString());
			
           while (ls.next()) {
				dbox = ls.getDataBox();
				
				sql2.setLength(0);
				sql2.append(" SELECT                                                         \n ");
		        sql2.append("         B.CODE UPPER_CODE, B.CODENM UPPER_NAME                 \n ");
		        sql2.append("         , C.CODE, C.CODENM, B.PARENT PARENT_CODE               \n ");
		        sql2.append("         , COUNT(*) OVER (PARTITION BY B.CODE)     AS UPPER_CNT \n ");
		        sql2.append(" FROM    TZ_CODE B, TZ_CODE C                                   \n ");
		        sql2.append(" WHERE                                                          \n ");
		        sql2.append("         C.UPPER = B.CODE                                       \n ");
		        sql2.append(" AND     C.PARENT = "+ StringManager.makeSQL(dbox.getString("d_parent_code")) +" \n ");
		        sql2.append(" AND     C.UPPER = "+ StringManager.makeSQL(dbox.getString("d_upper_code")) +" \n ");
		        sql2.append(" AND     C.LEVELS = 3                                           \n ");
		        sql2.append(" AND     C.GUBUN = '0083'                                       \n ");
		        sql2.append(" AND     B.GUBUN = '0083'                                       \n ");
		        sql2.append(" ORDER BY B.CODE, C.CODE                                        \n ");
		        
		        ls2 = connMgr.executeQuery(sql2.toString());
		        
		        subList = new ArrayList();
		        
		        while (ls2.next()) {
		        	dbox2 = ls2.getDataBox();
		        	subList.add(dbox2);
		        	dbox.put("d_subList", subList);
		        }
		        
				list.add(dbox);
			}
		}
		catch (Exception ex) {
			   ErrorManager.getErrorStackTrace(ex, box, sql1.toString());
			throw new Exception("sql = " + sql1.toString() + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;
	}

}
