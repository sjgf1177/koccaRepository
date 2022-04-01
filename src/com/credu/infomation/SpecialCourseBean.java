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
import com.dunet.common.util.StringUtil;
import com.namo.SmeNamoMime;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
         
public class SpecialCourseBean {

	private ConfigSet config;
    private int row;
    private	static final String	FILE_TYPE =	"p_file";			//		파일업로드되는 tag name
	private	static final int FILE_LIMIT	= 10;					//	  페이지에 세팅된 파일첨부 갯수


	public SpecialCourseBean() {
	    try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	public static int getFILE_LIMIT(){
		return FILE_LIMIT;
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
			
			bodySql.append(" FROM                              \n ");
			bodySql.append("         TZ_SPECIALCOURSE A        \n ");
			bodySql.append("         , TZ_CODE MST             \n ");
			bodySql.append("         , TZ_CODE SUB             \n ");
			bodySql.append(" WHERE                             \n ");
			bodySql.append("         A.CLSFCD    = MST.CODE(+) \n ");
			bodySql.append(" AND     A.DTLCD     = SUB.CODE(+) \n ");
			bodySql.append(" AND     MST.GUBUN(+)= '0087'      \n ");
			bodySql.append(" AND     SUB.GUBUN(+)= '0087'      \n ");
  
			                                                                                    
			
			if ( !v_searchtext.equals("")) {      //    검색어가 있으면
				if (v_search.equals("title")) {                          //    제목으로 검색할때
					bodySql.append("   and title like " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
				} else if (v_search.equals("content")) {                //    내용으로 검색할때
					bodySql.append("   and content like " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
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
                dbox.put("d_rowcount", new Integer(row));
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
	* 공지사항 등록할때
	* @param box      receive from the form object and session
	* @return isOk    1:insert success,0:insert fail
	* @throws Exception
	*/
	public int insert(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet 			ls 		= null;
		PreparedStatement 	pstmt 	= null;
		String 				sql  	= "";
		StringBuffer     	insertSql = new StringBuffer();
		
		int isOk  = 0;
		int isOk2 = 0;
		int v_seq = 0;

		String v_gubun     = box.getStringDefault("p_gubun","9");
		String v_clsfcd    = box.getString("p_clsfcd");
		String v_dtlcd     = box.getString("p_dtlcd");
		String v_title     = StringUtil.removeTag(box.getString("p_title"));
		String v_content   = StringUtil.removeTag(box.getString("p_content"));
		String v_professor = box.getString("p_professor");
		String v_pictureurl = box.getString("p_pictureurl");
		String v_useyn     = box.getStringDefault("p_useyn", "N");

		String s_userid   = box.getSession("userid");
		String s_name     = box.getSession("name");

		try {
		   connMgr = new DBConnectionManager();
		   connMgr.setAutoCommit(false);

		   sql  = "select max(seq) from TZ_SPECIALCOURSE ";
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

		   insertSql.append(" INSERT INTO TZ_SPECIALCOURSE                \n ");
		   insertSql.append(" (                                           \n ");
		   insertSql.append("     GUBUN, CLSFCD, DTLCD, SEQ, TITLE        \n ");
		   insertSql.append("     , CONTENT, PROFESSOR, PICTUREURL, USEYN \n ");
		   insertSql.append("     , USERID, NAME, LUSERID, INDATE, LDATE  \n ");
		   insertSql.append(" )                                           \n ");
		   insertSql.append(" VALUES                                      \n ");
		   insertSql.append(" (                                           \n ");
		   insertSql.append("     ?, ?, ?, ?, ?, ?, ?                     \n ");
		   insertSql.append("     , ?, ?, ?, ?, ?                         \n ");
		   insertSql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  \n ");
		   insertSql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  \n ");
		   insertSql.append(" )                                           \n ");

         
         pstmt = connMgr.prepareStatement(insertSql.toString());
         
         int index = 1;
         pstmt.setString(index++,  v_gubun);
         pstmt.setString(index++,  v_clsfcd);
         pstmt.setString(index++,  v_dtlcd);
         pstmt.setInt   (index++,  v_seq);
         pstmt.setString(index++,  v_title);
         pstmt.setString(index++,  v_content);
         pstmt.setString(index++,  v_professor);
         pstmt.setString(index++,  v_pictureurl);
         pstmt.setString(index++,  v_useyn);
         pstmt.setString(index++,  s_userid);
         pstmt.setString(index++,  s_name);
         pstmt.setString(index++,  s_userid);

         isOk = pstmt.executeUpdate();
         
         isOk2 = this.insertUpFile(connMgr, v_seq, box);

           if(isOk > 0 && isOk2 >0 ){
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
	* 공지사항 수정하여 저장할때
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/
	 public int update(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer updateSql = new StringBuffer();
		int isOk = 0;
		int isOk2 = 0;
		int isOk3 = 0;

		int    v_seq       = box.getInt("p_seq");
		String v_gubun     = box.getString("p_gubun");
		String v_clsfcd    = box.getString("p_clsfcd");
		String v_dtlcd     = box.getString("p_dtlcd");
		String v_title     = StringUtil.removeTag(box.getString("p_title"));
		String v_content   = StringUtil.removeTag(box.getString("p_content"));
		String v_professor = box.getString("p_professor");
		String v_pictureurl = box.getString("p_pictureurl");
		String v_useyn     = box.getStringDefault("p_useyn", "N");

		String s_userid   = box.getSession("userid");

		int	v_upfilecnt       = box.getInt("p_upfilecnt");	//	서버에 저장되있는 파일수
		Vector v_savefile     =	new	Vector();
		Vector v_filesequence =	new	Vector();

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			for(int	i =	0; i < v_upfilecnt;	i++) {
			  if(	!box.getString("p_fileseq" + i).equals(""))	{
			  	v_savefile.addElement(box.getString("p_savefile" + i));			//		서버에 저장되있는 파일명 중에서	삭제할 파일들
			  	v_filesequence.addElement(box.getString("p_fileseq"	+ i));		 //		서버에	저장되있는 파일번호	중에서 삭제할 파일들
			  }
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

			   updateSql.append(" UPDATE TZ_SPECIALCOURSE SET                             \n ");
			   updateSql.append("     GUBUN       = ? , CLSFCD    = ?                     \n ");
			   updateSql.append("     , DTLCD     = ?                                     \n ");
			   updateSql.append("     , TITLE     = ? , CONTENT   = ?                     \n ");
			   updateSql.append("     , PROFESSOR = ? , PICTUREURL    = ?                 \n ");
			   updateSql.append("     , USEYN     = ? , LUSERID       = ?                 \n ");
			   updateSql.append("     , LDATE     = to_char(sysdate, 'YYYYMMDDHH24MISS')  \n ");
			   updateSql.append(" WHERE                                                   \n ");
			   updateSql.append("      SEQ       = ?                                      \n ");

			pstmt = connMgr.prepareStatement(updateSql.toString());
			int index = 1;
			pstmt.setString(index++, v_gubun);
			pstmt.setString(index++, v_clsfcd);
			pstmt.setString(index++, v_dtlcd);
			pstmt.setString(index++, v_title);
			pstmt.setString(index++, v_content);
			pstmt.setString(index++, v_professor);
			pstmt.setString(index++, v_pictureurl);
			pstmt.setString(index++, v_useyn);
			pstmt.setString(index++, s_userid);
			pstmt.setInt   (index++, v_seq);

			isOk = pstmt.executeUpdate();

			isOk3 =	this.deleteUpFile(connMgr, box,	v_filesequence);		//	   삭제할 파일이 있다면	파일table에서 삭제
			isOk2 =	this.insertUpFile(connMgr, v_seq,	box);		       //		파일첨부했다면 파일table에	insert

			if(isOk > 0 && isOk2 > 0 && isOk3 > 0){
				connMgr.commit();
				//connMgr.rollback();
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
	* 공지사항 삭제할때
	* @param box      receive from the form object and session
	* @return isOk    1:delete success,0:delete fail
	* @throws Exception
	*/
	public int delete(RequestBox box) throws Exception {
		DBConnectionManager connMgr 	= null;
		PreparedStatement pstmt 		= null;
		ListSet ls 						= null;
		DataBox dbox 					= null;
		
		StringBuffer sql 		= new StringBuffer();
		Vector v_filesequence 	= new Vector();
		
		int isOk = 0;

		int v_seq          = box.getInt("p_seq");

		try {
			
			connMgr = new DBConnectionManager();
			
			sql.append(" DELETE FROM TZ_SPECIALCOURSE WHERE SEQ = ? ");

			pstmt = connMgr.prepareStatement(sql.toString());

			pstmt.setInt   (1, v_seq);
			
			isOk = pstmt.executeUpdate();
			
			if( isOk > 0) {
				
				sql.setLength(0);
				
				sql.append(" SELECT FILESEQ ");
				sql.append("   FROM TZ_SPECIALCOURSE_FILE ");
				sql.append("  WHERE SEQ = ").append(v_seq);
				
				ls = connMgr.executeQuery( sql.toString() );
				
				while( ls.next() ) {
					
					dbox = ls.getDataBox();
					
					v_filesequence.addElement( dbox.getString("d_fileseq") );
					
				}
				
				if( v_filesequence != null ) {
					
					isOk = deleteUpFile( connMgr, box, v_filesequence);
					
				}
				
			}
			
		} catch ( Exception ex ) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString() + "\r\n");
			throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
		} finally {
			if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		
		return isOk;
		
	}



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





// ===============================회사 select method end===============================



    /**
    * 공지사항 새로운 자료파일 등록
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */

	 public	int	insertUpFile(DBConnectionManager connMgr, int p_seq, RequestBox	box) throws	Exception {
		ListSet	ls = null;
		PreparedStatement pstmt2 = null;
		StringBuffer sql = new StringBuffer();
		StringBuffer insertSql	= new StringBuffer();
		int	isOk2 =	1;
		int index = 1;
		
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
			sql.append("select NVL(max(fileseq),	0) from	TZ_SPECIALCOURSE_FILE	WHERE  SEQ = " + p_seq);

			ls = connMgr.executeQuery(sql.toString());
			ls.next();
			int	v_fileseq =	ls.getInt(1) + 1;
			ls.close();
			//------------------------------------------------------------------------------------

			//////////////////////////////////	 파일 table	에 입력	 ///////////////////////////////////////////////////////////////////

			insertSql.append(" INSERT INTO TZ_SPECIALCOURSE_FILE           \n ");
			insertSql.append(" (                                           \n ");
			insertSql.append("     SEQ, FILESEQ                            \n ");
			insertSql.append("     , REALFILE, SAVEFILE, LUSERID,	LDATE  \n ");
			insertSql.append(" )                                           \n ");
			insertSql.append(" VALUES                                      \n ");
			insertSql.append(" (                                           \n ");
			insertSql.append("      ?, ?, ?, ?, ?                          \n ");
			insertSql.append("     , TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')  \n ");
			insertSql.append(" )                                           \n ");
			
			pstmt2 = connMgr.prepareStatement(insertSql.toString());

			for(int	i =	0; i < FILE_LIMIT; i++)	{
				if(	!v_realFileName	[i].equals(""))	{		//		실제 업로드	되는 파일만	체크해서 db에 입력한다
					index = 1;
					pstmt2.setInt   (index++, p_seq);
					pstmt2.setInt   (index++, v_fileseq);
					pstmt2.setString(index++, v_realFileName[i]);
					pstmt2.setString(index++, v_newFileName[i]);
					pstmt2.setString(index++, s_userid);
					isOk2 =	pstmt2.executeUpdate();
					v_fileseq++;
				}
			}
		}
		catch (Exception ex) {
			FileManager.deleteFile(v_newFileName, FILE_LIMIT);		//	일반파일, 첨부파일 있으면 삭제..
			ErrorManager.getErrorStackTrace(ex,	box, insertSql.toString());
			throw new Exception("sql = " + insertSql.toString()	+ "\r\n" + ex.getMessage());
		}
		finally	{
		    if(ls != null) { try { ls.close(); } catch (Exception e) {}	}
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
		}
		return isOk2;
	}


	/**
	 * 선택된 자료파일 DB에서 삭제
	 * @param connMgr			DB Connection Manager
	 * @param box				receive from the form object and session
	 * @param p_filesequence    선택 파일 갯수
	 * @return
	 * @throws Exception
	 */
	public int deleteUpFile(DBConnectionManager	connMgr, RequestBox box, Vector p_filesequence)	throws Exception {
		PreparedStatement pstmt3 = null;
		StringBuffer sql	= new StringBuffer();
		int	isOk3 =	1;
		int index = 1;

		int    v_seq	   = box.getInt("p_seq");

		try	{
			sql.append(" delete from TZ_SPECIALCOURSE_FILE  \n"); 
			sql.append(" WHERE SEQ = ? AND FILESEQ = ? ");
			pstmt3 = connMgr.prepareStatement(sql.toString());

			for(int	i =	0; i < p_filesequence.size(); i++) {
				index = 1;
				int	v_fileseq =	Integer.parseInt((String)p_filesequence.elementAt(i));
				pstmt3.setInt(index++, v_seq);
				pstmt3.setInt(index++, v_fileseq);
				isOk3 =	pstmt3.executeUpdate();
			}
		}
		catch (Exception ex) {
			   ErrorManager.getErrorStackTrace(ex, box,	sql.toString());
			throw new Exception("sql = " + sql.toString()	+ "\r\n" + ex.getMessage());
		}
		finally	{
			if(pstmt3 != null) { try { pstmt3.close(); } catch (Exception e) {}	}
		}
		return isOk3;
	}



}
