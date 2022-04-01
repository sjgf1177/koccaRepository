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
public class GalaryBean {

	private ConfigSet config;
    private int row;
    private	static final String	FILE_TYPE =	"p_file";			//		파일업로드되는 tag name
	private	static final int FILE_LIMIT	= 1;					//	  페이지에 세팅된 파일첨부 갯수


	public GalaryBean() {
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

		String 			v_search     = box.getString("p_search");
		String 			v_searchtext = box.getString("p_searchtext");

		int 			v_tabseq 	= box.getInt("p_tabseq");
		
		int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");
        
        String 			v_orderColumn = box.getString("p_orderColumn");
        String 			v_orderType = box.getString("p_orderType");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			headSql.append(" SELECT                                   \n ");
			headSql.append("         ROWNUM                           \n ");
			headSql.append("         , BOARD.TABSEQ                   \n ");
			headSql.append("         , BOARD.SEQ                      \n ");
			headSql.append("         , BOARD.TITLE                    \n ");
			headSql.append("         , BOARD.USERID                   \n ");
			headSql.append("         , BOARD.NAME                     \n ");
			headSql.append("         , BOARD.CNT                      \n ");
			headSql.append("         , BOARD.LDATE                    \n ");
			headSql.append("         , BOARD.FLAGYN                   \n ");
			
			bodySql.append(" FROM    TZ_GALLERY    BOARD                \n ");
			bodySql.append(" WHERE   BOARD.TABSEQ	= "+StringManager.makeSQL(""+v_tabseq)+" \n ");
			

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면                                         
				if (v_search.equals("title")) {                          //    제목으로 검색할때  
					bodySql.append(" AND TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");  
				} else if (v_search.equals("contents")) {                //    내용으로 검색할때  
					bodySql.append(" AND CONTENT LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");
				}                                                                                   
			}  

			//bodySql.append(" AND     BOARD.TABSEQ    =  " +  v_tabseq + " \n ");
			
			if(!v_orderColumn.equals("")){
				orderSql = " ORDER BY " + v_orderColumn + " " + v_orderType;
			} else {
				orderSql = " ORDER BY SEQ DESC";	
			}
			
			System.out.println("photo gallery sql = " + headSql.toString() + bodySql.toString() + orderSql);
			
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
            
			/*int total_row_count= BoardPaging.getTotalRow(connMgr, countSql);	//     전체 row 수를 반환한다
			//ls.setPageSize(row);                       							//페이지당 row 갯수를 세팅한다
            ls.setPageSize(v_pagesize);  										//페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count);   					//     현재페이지번호를 세팅한다.
            
            System.out.println("v_pageno : " + v_pageno);
            System.out.println("d_dispnum : "+(total_row_count - ls.getRowNum() + 1));
            System.out.println("d_totalpage : "+ls.getTotalPage());
            System.out.println("d_rowcount : "+v_pagesize);
            System.out.println("d_totalrowcount : "+total_row_count);
			while (ls.next()) {
				dbox = ls.getDataBox();
				dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(v_pagesize));
                dbox.put("d_totalrowcount",	new Integer(total_row_count));
				list.add(dbox);
			}
*/
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
	public int insert(RequestBox box) throws Exception {
		
		DBConnectionManager connMgr = null;
		ListSet 			ls 		= null;
		PreparedStatement 	pstmt 	= null;
		String 				seqSql  = "";
		StringBuffer 		insertSql = new StringBuffer();
		
		int isOk  = 0;
		int isOk2 = 0;
		int v_seq = 0;

        int v_tabseq 	   = box.getInt			("p_tabseq");
		String v_title     = StringUtil.removeTag(box.getString		("p_title"));
		String v_content   = StringUtil.removeTag(box.getString		("p_content"));
		
		String v_upfile	   = box.getNewFileName	("p_file1");
		String v_realfile  = box.getRealFileName("p_file1");
		String v_flagYn    = box.getString		("p_flagYn");
		String s_userid    = box.getSession		("userid");
		String s_name      = box.getSession		("name");
		String v_position  = box.getString		("p_position");

		Log.err.println("v_upfil1==> " + v_upfile);
		Log.err.println("v_realfile1==> " + v_realfile);
		
		try {
		   connMgr = new DBConnectionManager();
		   connMgr.setAutoCommit(false);

		   seqSql  = "SELECT MAX(seq) FROM TZ_GALLERY  WHERE TABSEQ = " + v_tabseq;
		   ls = connMgr.executeQuery(seqSql);
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


		   insertSql.append(" INSERT INTO TZ_GALLERY                                           \n");
		   insertSql.append(" (   TABSEQ, SEQ, TITLE, USERID, NAME                             \n");
		   //insertSql.append("     , CONTENT, INDATE, UPFILE, CNT                               \n");
		   insertSql.append("     , CONTENT, INDATE,  CNT                               \n");
		   insertSql.append("     , LUSERID, LDATE, FLAGYN, POSITION)                          \n");
		   insertSql.append(" VALUES                                                           \n");
		   insertSql.append(" (   ?, ?, ?, ? , ? , ?, to_char(sysdate, 'YYYYMMDDHH24MISS')     \n");
		   //insertSql.append("     , ? , ? , ? , to_char(sysdate, 'YYYYMMDDHH24MISS') , ?, ?) \n");
		   insertSql.append("     , ? , ? , to_char(sysdate, 'YYYYMMDDHH24MISS') , ?, ?) \n");


		   int index = 1;
           pstmt = connMgr.prepareStatement(insertSql.toString());
           pstmt.setInt   (index++,  v_tabseq);
           pstmt.setInt   (index++,  v_seq);
           pstmt.setString(index++,  v_title);
           pstmt.setString(index++,  s_userid);
           pstmt.setString(index++,  s_name);
           pstmt.setString(index++,  v_content);
           //pstmt.setString(index++,  v_upfile);       //파일
           pstmt.setInt   (index++,  0);
           pstmt.setString(index++,  s_userid);
           pstmt.setString(index++,  v_flagYn);
           pstmt.setString(index++,  v_position);
           
           isOk = pstmt.executeUpdate();

           isOk2 = this.insertUpFile(connMgr,v_tabseq, v_seq, box);

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
	*  수정하여 저장할때
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
		
		box.put("p_type", "PG");

        int v_tabseq 	      = box.getInt			("p_tabseq");
        int v_seq			  = box.getInt			("p_seq");
		String v_title        = StringUtil.removeTag(box.getString		("p_title"));
		String v_content      = StringUtil.removeTag(box.getString		("p_content"));
		String v_upfile	      = box.getNewFileName	("p_file1");
		String v_realfile     = box.getRealFileName	("p_file1");
		String v_flagYn       = box.getString		("p_flagYn");
		String s_userid       = box.getSession		("userid");
		String v_position     = box.getString		("p_position");
		
		//int	v_upfilecnt       = box.getInt("p_upfilecnt");	//	서버에 저장되있는 파일수
		Vector v_savefile     =	new	Vector();
		Vector v_filesequence =	new	Vector();

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

		  if(!box.getString("p_fileseq1").equals(""))	{
		  	v_savefile.addElement(box.getString("p_savefile1"));			//		서버에 저장되있는 파일명 중에서	삭제할 파일들
		  	v_filesequence.addElement(box.getString("p_fileseq1"));		 //		서버에	저장되있는 파일번호	중에서 삭제할 파일들
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

			updateSql.append(" UPDATE TZ_GALLERY                                                 \n");
			updateSql.append(" SET                                                             \n");
			updateSql.append("         TITLE       = ?                                         \n");
			updateSql.append("         , CONTENT   = ?                                         \n");
			updateSql.append("         , LUSERID   = ?                                         \n");
			updateSql.append("         , LDATE     = to_char(sysdate, 'YYYYMMDDHH24MISS')      \n");
			updateSql.append("         , FLAGYN    = ?                                         \n");
			//updateSql.append("         , UPFILE    = ?                                         \n");
			updateSql.append("         , POSITION  = ?                                         \n");
			updateSql.append(" WHERE                                                           \n");
			updateSql.append("         TABSEQ      = ?                                         \n");
			updateSql.append(" AND     SEQ         = ?                                         \n");


			pstmt = connMgr.prepareStatement(updateSql.toString());
			
			int index = 1;
			
			pstmt.setString(index++, v_title);
			pstmt.setString(index++, v_content);
			pstmt.setString(index++, s_userid);
			pstmt.setString(index++, v_flagYn);
			//pstmt.setString(index++, v_upfile);
			pstmt.setString(index++, v_position);
			pstmt.setInt   (index++, v_tabseq);
			pstmt.setInt   (index++, v_seq);

			isOk = pstmt.executeUpdate();
			
			isOk3 =	this.deleteUpFile(connMgr, box,	v_filesequence);		//	   삭제할 파일이 있다면	파일table에서 삭제
			isOk2 =	this.insertUpFile(connMgr, v_tabseq, v_seq,	box);		//		파일첨부했다면 파일table에	insert

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
	*  삭제할때
	* @param box      receive from the form object and session
	* @return isOk    1:delete success,0:delete fail
	* @throws Exception
	*/
	public int delete(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 0;

        int v_tabseq = box.getInt("p_tabseq");
		int v_seq  = box.getInt("p_seq");

		try {
			connMgr = new DBConnectionManager();
			sql  = " DELETE FROM TZ_GALLERY          ";
			sql += "   WHERE TABSEQ = ? AND SEQ = ?  ";

			pstmt = connMgr.prepareStatement(sql);
			pstmt.setInt(1, v_tabseq);
			pstmt.setInt(2, v_seq);
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
	*  삭제할때
	* @param box      receive from the form object and session
	* @return isOk    1:delete success,0:delete fail
	* @throws Exception
	*/
	public int deleteFile(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 0;

        int v_tabseq = box.getInt("p_tabseq");
		int v_seq  = box.getInt("p_seq");

		try {
			connMgr = new DBConnectionManager();
            sql  = " DELETE FROM TZ_BOARDFILE          ";
			sql += "   WHERE TABSEQ = ? AND SEQ = ?  ";

			pstmt = connMgr.prepareStatement(sql);
			pstmt.setInt(1, v_tabseq);
			pstmt.setInt(2, v_seq);
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
   public DataBox selectView(RequestBox box) throws Exception {
	 
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");
		String v_seq = box.getString("p_seq");
		String v_process = box.getString("p_process");

		Vector realfileVector = new Vector();
		Vector savefileVector = new Vector();
		Vector fileseqVector  = new Vector();

		try {
			connMgr = new DBConnectionManager();

			sql.append(" SELECT                                   \n ");
			sql.append("         BOARD.TABSEQ                     \n ");
			sql.append("         , BOARD.SEQ                      \n ");
			sql.append("         , BOARD.TITLE                    \n ");
			sql.append("         , BOARD.CONTENT                  \n ");
			sql.append("         , BOARD.USERID                   \n ");
			sql.append("         , BOARD.NAME                     \n ");
			sql.append("         , BOARD.CNT                      \n ");
			sql.append("         , BOARD.LDATE                    \n ");
			sql.append("         , BOARD.POSITION                 \n ");
			sql.append("         , BOARD.FLAGYN                   \n ");
			sql.append("         , FILES.REALFILE                 \n ");
			sql.append("         , FILES.SAVEFILE                 \n ");
			sql.append("         , FILES.FILESEQ                  \n ");
			sql.append(" FROM    TZ_GALLERY       	BOARD         \n ");
			sql.append("         , TZ_BOARDFILE   	FILES         \n ");
			sql.append(" WHERE   BOARD.TABSEQ   = FILES.TABSEQ(+) \n ");
			sql.append(" AND     BOARD.SEQ      = FILES.SEQ(+)    \n ");
			sql.append(" AND     BOARD.TABSEQ   = " + v_tabseq + "\n");
			sql.append(" AND     BOARD.SEQ      = "+ StringManager.makeSQL(v_seq)+" \n");
			
			ls = connMgr.executeQuery(sql.toString());
			System.out.println(sql.toString());
			System.out.println(v_tabseq);
			System.out.println(StringManager.makeSQL(v_seq));
			while (ls.next()) {
				
				dbox = ls.getDataBox();
				
				//realfileVector.addElement(dbox.getString("d_realfile"));
                //savefileVector.addElement(dbox.getString("d_savefile"));
                //fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
			}
			
			//if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
			//if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
			//if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);
			
			// 조회수 증가
			if(v_process.equals("selectView")){
			  String sql1 = "UPDATE TZ_GALLERY SET CNT = CNT + 1 WHERE SEQ = " + v_seq+" AND TABSEQ   = " + v_tabseq;
			  connMgr.executeUpdate(sql1);
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



// ===============================회사 select method end===============================



    /**
    *  새로운 자료파일 등록
    * @param box      receive from the form object and session
    * @return isOk    1:delete success,0:delete fail
    * @throws Exception
    */

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

			System.out.println("sql :  " + sql);
			
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
			System.out.println("del sql : " + sql3);
			

			for(int	i =	0; i < p_filesequence.size(); i++) {
				int	v_fileseq =	Integer.parseInt((String)p_filesequence.elementAt(i));
				pstmt3.setInt(1, v_seq);
				pstmt3.setInt(2, v_fileseq);
				System.out.println(v_tabseq + " : " + v_seq + " : " + v_fileseq);
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
	}




	/**
	* 화면 이전글정보보기
	* @param box          receive from the form object and session
	* @return ArrayList   조회한 이전글정보
	* @throws Exception
	*/
   public NoticeData selectViewPre(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		NoticeData data = null;

        int v_tabseq = box.getInt("p_tabseq");
		String v_seq = box.getString("p_seq");
        String v_gubun = box.getString("p_gubun");
		String v_gubun_query = "";

		if (v_gubun.equals("Y")) {		// 전체공지일경우
			v_gubun_query = "('Y')";
		} else {						// 전체공지가아닐경우(일반,팝업)
			v_gubun_query = "('N','P')";
		}

		try {
			connMgr = new DBConnectionManager();

			sql += " select seq,gubun, addate, adtitle, adname, adcontent, cnt, luserid, ldate from TZ_NOTICE ";
			sql += "  where gubun in " + v_gubun_query;
			sql += "    and tabseq = " +  v_tabseq;
			sql += "    and seq   <  " + StringManager.makeSQL(v_seq);
			sql += "  order by seq desc                                                                       ";

			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				data=new NoticeData();
				data.setSeq(ls.getInt("seq"));
				data.setGubun(ls.getString("gubun"));
				data.setAddate(ls.getString("addate"));
				data.setAdtitle(ls.getString("adtitle"));
				data.setAdname(ls.getString("adname"));
				data.setAdcontent(ls.getString("adcontent"));
				data.setCnt(ls.getInt("cnt"));
				data.setLuserid(ls.getString("luserid"));
				data.setLdate(ls.getString("ldate"));
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
		return data;
	}


	/**
	* 화면 다음글정보보기
	* @param box          receive from the form object and session
	* @return ArrayList   조회한 다음글정보
	* @throws Exception
	*/
   public NoticeData selectViewNext(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		NoticeData data = null;

        int v_tabseq = box.getInt("p_tabseq");
		String v_seq   = box.getString("p_seq");
		String v_gubun = box.getString("p_gubun");

		String v_gubun_query = "";

		if (v_gubun.equals("Y")) {		// 전체공지일경우
			v_gubun_query = "('Y')";
		} else {						// 전체공지가아닐경우(일반,팝업)
			v_gubun_query = "('N','P')";
		}

		try {
			connMgr = new DBConnectionManager();

			sql += " select seq, gubun, addate, adtitle, adname, adcontent, cnt, luserid, ldate from TZ_NOTICE ";
			sql += "  where gubun in " + v_gubun_query;
			sql += "    and tabseq = " +  v_tabseq;
			sql += "    and seq  >  " + StringManager.makeSQL(v_seq);
			sql += "  order by seq asc                                                                        ";

			ls = connMgr.executeQuery(sql);

			if (ls.next()) {
				data=new NoticeData();
				data.setSeq(ls.getInt("seq"));
				data.setGubun(ls.getString("gubun"));
				data.setAddate(ls.getString("addate"));
				data.setAdtitle(ls.getString("adtitle"));
				data.setAdname(ls.getString("adname"));
				data.setAdcontent(ls.getString("adcontent"));
				data.setCnt(ls.getInt("cnt"));
				data.setLuserid(ls.getString("luserid"));
				data.setLdate(ls.getString("ldate"));
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
		return data;
	}
}
