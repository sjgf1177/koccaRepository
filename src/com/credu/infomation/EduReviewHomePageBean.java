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
import com.dunet.common.util.UploadUtil;
import com.namo.SmeNamoMime;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class EduReviewHomePageBean {

	private ConfigSet config;
	private	static final String	FILE_TYPE =	"p_file";			//		파일업로드되는 tag name
	private	static final int FILE_LIMIT	= 10;					//	  페이지에 세팅된 파일첨부 갯수


	public EduReviewHomePageBean() {
	    try{
            config = new ConfigSet();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
	}
	
	public static int getFILE_LIMIT(){
		return FILE_LIMIT;
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
	* @return ArrayList   교육 후기 리스트
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
        
        String 			v_orderColumn = box.getString("p_orderColumn");
        String 			v_orderType = box.getString("p_orderType");
        
        int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			if(v_tabseq != 2262){
				headSql.append(" SELECT                                   \n ");
				headSql.append("         ROWNUM                           \n ");
				headSql.append("         , BOARD.TABSEQ                   \n ");
				headSql.append("         , BOARD.SEQ                      \n ");
				headSql.append("         , BOARD.TITLE                    \n ");
				headSql.append("         , BOARD.USERID                   \n ");
				headSql.append("         , BOARD.CONTENT                  \n ");
				headSql.append("         , BOARD.NAME                     \n ");
				headSql.append("         , BOARD.CNT                      \n ");
				headSql.append("         , BOARD.INDATE                   \n ");
				headSql.append("         , BOARD.FLAGYN                   \n ");
				headSql.append("         , BOARD.URL                      \n ");
				headSql.append("         , BOARD.POSITION                 \n ");
		
				bodySql.append(" FROM    TZ_PR_ARTICLES    BOARD          \n ");
				bodySql.append(" WHERE   BOARD.TABSEQ	= " + StringManager.makeSQL(""+v_tabseq) + " \n ");
				bodySql.append(" AND     BOARD.FLAGYN   = 'Y'             \n ");
			}else{
				headSql.append(" SELECT                                   \n ");
				headSql.append("         ROWNUM                           \n ");
				headSql.append("         , BOARD.TABSEQ                   \n ");
				headSql.append("         , BOARD.SEQ                      \n ");
				headSql.append("         , BOARD.TITLE                    \n ");
				headSql.append("         , BOARD.USERID                   \n ");
				headSql.append("         , BOARD.CONTENT                  \n ");
				headSql.append("         , BOARD.NAME                     \n ");
				headSql.append("         , BOARD.CNT                      \n ");
				headSql.append("         , BOARD.INDATE                   \n ");
				headSql.append("         , BOARD.FLAGYN                   \n ");
				headSql.append("         , BOARD.URL                      \n ");
				headSql.append("         , BOARD.POSITION                 \n ");
				headSql.append("         , FILES.SAVEFILE                 \n ");
		
				bodySql.append(" FROM    TZ_PR_ARTICLES BOARD, TZ_BOARDFILE FILES	\n ");
				bodySql.append(" WHERE   BOARD.TABSEQ	= " + StringManager.makeSQL(""+v_tabseq) + " \n ");
				bodySql.append(" AND     BOARD.FLAGYN   = 'Y'             \n ");
				bodySql.append(" AND     BOARD.TABSEQ   = FILES.TABSEQ(+) \n ");
				bodySql.append(" AND     BOARD.SEQ   	= FILES.SEQ(+)    \n ");
				bodySql.append(" AND     FILES.FILESEQ  = '1'             \n ");
			}

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면                                         
				if (v_search.equals("title")) {                          //    제목으로 검색할때  
					bodySql.append(" AND BOARD.TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");  
				} else if (v_search.equals("contents")) {                //    내용으로 검색할때  
					bodySql.append(" AND BOARD.CONTENT LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");
				}                                                                                   
			}  

			//bodySql.append(" AND     BOARD.TABSEQ    =  " +  v_tabseq + " \n ");
			
			if(!v_orderColumn.equals("")){
				orderSql = " ORDER BY BOARD." + v_orderColumn + " " + v_orderType;
			} else {
				orderSql = " ORDER BY BOARD.SEQ DESC";	
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
                dbox.put("d_rowcount",  	new Integer(v_pagesize));
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
			sql.append("         , BOARD.URL                      \n ");
			sql.append("         , FILES.REALFILE                 \n ");
			sql.append("         , FILES.SAVEFILE                 \n ");
			sql.append("         , FILES.FILESEQ                  \n ");
			sql.append(" FROM    TZ_PR_ARTICLES       	BOARD     \n ");
			sql.append("         , TZ_BOARDFILE   	FILES         \n ");
			sql.append(" WHERE   BOARD.TABSEQ   = FILES.TABSEQ(+) \n ");
			sql.append(" AND     BOARD.SEQ      = FILES.SEQ(+)    \n ");
			sql.append(" AND     BOARD.TABSEQ   = " + v_tabseq + "\n");
			sql.append(" AND     BOARD.SEQ      = "+ StringManager.makeSQL(v_seq)+" \n");
			sql.append(" AND     BOARD.FLAGYN   = 'Y'             \n ");
			
			ls = connMgr.executeQuery(sql.toString());
			
			while (ls.next()) {
				
				dbox = ls.getDataBox();
				
				if( !dbox.getString("d_realfile").equals("")){
					realfileVector.addElement(dbox.getString("d_realfile"));
	                savefileVector.addElement(dbox.getString("d_savefile"));
	                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
				}
			}
			
			if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
			if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
			if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);
			
			// 조회수 증가
			if(v_process.equals("selectView")){
			  String sql1 = "UPDATE TZ_PR_ARTICLES SET CNT = CNT + 1 WHERE SEQ = " + v_seq+" AND TABSEQ   = " + v_tabseq;
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

    public DataBox edit(RequestBox box) throws Exception {

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
			sql.append("         , BOARD.URL                      \n ");
			sql.append("         , FILES.REALFILE                 \n ");
			sql.append("         , FILES.SAVEFILE                 \n ");
			sql.append("         , FILES.FILESEQ                  \n ");
			sql.append(" FROM    TZ_PR_ARTICLES       	BOARD     \n ");
			sql.append("         , TZ_BOARDFILE   	FILES         \n ");
			sql.append(" WHERE   BOARD.TABSEQ   = FILES.TABSEQ(+) \n ");
			sql.append(" AND     BOARD.SEQ      = FILES.SEQ(+)    \n ");
			sql.append(" AND     BOARD.TABSEQ   = " + v_tabseq + "\n");
			sql.append(" AND     BOARD.SEQ      = "+ StringManager.makeSQL(v_seq)+" \n");
			sql.append(" AND     BOARD.FLAGYN   = 'Y'             \n ");

			ls = connMgr.executeQuery(sql.toString());

			while (ls.next()) {

				dbox = ls.getDataBox();

				if( !dbox.getString("d_realfile").equals("")){
					realfileVector.addElement(dbox.getString("d_realfile"));
	                savefileVector.addElement(dbox.getString("d_savefile"));
	                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
				}
			}

			if (realfileVector 	!= null) dbox.put("d_realfile", realfileVector);
			if (savefileVector 	!= null) dbox.put("d_savefile", savefileVector);
			if (fileseqVector 	!= null) dbox.put("d_fileseq", fileseqVector);
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
		int isOk3 = 0;
		int v_seq = 0;

       int v_tabseq 	   = box.getInt			("p_tabseq");
		String v_title     = StringUtil.removeTag(box.getString("p_title"));
		String v_content   = StringUtil.removeTag(box.getString("p_content"));
		String v_flagYn    = box.getStringDefault("p_flagYn", "Y");
		String s_userid    = box.getSession		("userid");
		String s_name      = box.getSession		("name");
		String v_position  = box.getString      ("p_position");
		String v_url	   = box.getString      ("p_url");

		try {
		   connMgr = new DBConnectionManager();
		   connMgr.setAutoCommit(false);

		   seqSql  = "SELECT MAX(seq) FROM TZ_PR_ARTICLES  WHERE TABSEQ = " + v_tabseq;
		   ls = connMgr.executeQuery(seqSql);
		   if (ls.next()) {
			   v_seq = ls.getInt(1) + 1;
		   } else {
			   v_seq = 1;
		   }
          
		   insertSql.append(" INSERT INTO TZ_PR_ARTICLES                                       \n");
		   insertSql.append(" (   TABSEQ, SEQ, TITLE, USERID, NAME                             \n");
		   insertSql.append("     , CONTENT, INDATE, CNT                                       \n");
		   insertSql.append("     , LUSERID, LDATE, FLAGYN, POSITION, URL)                     \n");
		   insertSql.append(" VALUES                                                           \n");
		   insertSql.append(" (   ?, ?, ?, ? , ? , ?, to_char(sysdate, 'YYYYMMDDHH24MISS')     \n");
		   insertSql.append("     , ? , ? , to_char(sysdate, 'YYYYMMDDHH24MISS') , ?, ?, ?)\n");


		   int index = 1;
          pstmt = connMgr.prepareStatement(insertSql.toString());
          pstmt.setInt   (index++,  v_tabseq);
          pstmt.setInt   (index++,  v_seq);
          pstmt.setString(index++,  v_title);
          pstmt.setString(index++,  s_userid);
          pstmt.setString(index++,  s_name);
          pstmt.setString(index++,  v_content);
          pstmt.setInt   (index++,  0);
          pstmt.setString(index++,  s_userid);
          pstmt.setString(index++,  v_flagYn);
          pstmt.setString(index++,  v_position);
          pstmt.setString(index++,  v_url);
          
          isOk = pstmt.executeUpdate();
          isOk2 = UploadUtil.fnRegisterAttachFile(box);
          isOk3 = this.insertUpFile(connMgr,v_tabseq, v_seq, box);

          if(isOk > 0 && isOk2 >0 && isOk3 >0 ){
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

 			ArrayList<String> arySaveFileName  = (ArrayList)box.getObject("arySaveFileName");
			ArrayList<String> aryRealFileName = (ArrayList)box.getObject("aryRealFileName");
			
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
					if(	arySaveFileName.size()!=0 &&  !arySaveFileName.get(i).equals(""))	{		//		실제 업로드	되는 파일만	체크해서 db에 입력한다
						pstmt2.setInt(1, p_tabseq);
						pstmt2.setInt(2, p_seq);
						pstmt2.setInt(3, v_fileseq);
						pstmt2.setString(4,	(String)aryRealFileName.get(i));
						pstmt2.setString(5,	(String)arySaveFileName.get(i));
						pstmt2.setString(6,	s_userid);
						isOk2 =	pstmt2.executeUpdate();
						v_fileseq++;
					}
				}
			}
			catch (Exception ex) {
				FileManager.deleteFile(arySaveFileName);		//	일반파일, 첨부파일 있으면 삭제..
				ErrorManager.getErrorStackTrace(ex,	box, sql2);
				throw new Exception("sql = " + sql2	+ "\r\n" + ex.getMessage());
			}
			finally	{
			    if(ls != null) { try { ls.close(); } catch (Exception e) {}	}
				if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
			}
			return isOk2;
		}

    public int delete(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet 			ls 		= null;
		PreparedStatement 	pstmt 	= null;
		StringBuffer 		insertSql = new StringBuffer();

		int isOk  = 0;
        int v_tabseq   = box.getInt("p_tabseq");
		int v_seq     = box.getInt("p_seq");

		try {
		   connMgr = new DBConnectionManager();
		   connMgr.setAutoCommit(false);

		   insertSql.append(" delete from  TZ_PR_ARTICLES where  tabseq=? and seq=? \n");

		   int index = 1;
          pstmt = connMgr.prepareStatement(insertSql.toString());
          pstmt.setInt   (index++,  v_tabseq);
          pstmt.setInt   (index++,  v_seq);

          isOk = pstmt.executeUpdate();

          if(isOk > 0){
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

    public int update(RequestBox box) throws Exception {

		DBConnectionManager connMgr = null;
		ListSet 			ls 		= null;
		PreparedStatement 	pstmt 	= null;
		String 				seqSql  = "";
		StringBuffer 		insertSql = new StringBuffer();

		int isOk  = 0;
		int isOk2 = 0;
		int isOk3 = 0;

       int v_tabseq 	   = box.getInt			("p_tabseq");
        int v_seq 	      = box.getInt			("p_seq");
		String v_title     = StringUtil.removeTag(box.getString		("p_title"));
		String v_content   = StringUtil.removeTag(box.getString      ("p_content"));
		String v_flagYn    = box.getStringDefault("p_flagYn", "Y");
		String s_userid    = box.getSession		("userid");
		String s_name      = box.getSession		("name");
		String v_position  = box.getString		("p_position");
		String v_url	   = box.getString      ("p_url");

		try {
		   connMgr = new DBConnectionManager();
		   connMgr.setAutoCommit(false);

		   insertSql.append(" update TZ_PR_ARTICLES set   \n");
		   insertSql.append("  TITLE=?,                   \n");
		   insertSql.append("  CONTENT=?,                 \n");
		   insertSql.append("  POSITION=?                 \n");
		   insertSql.append("  where tabseq=? and seq=?   \n");

		   int index = 1;
          pstmt = connMgr.prepareStatement(insertSql.toString());
          pstmt.setString(index++,  v_title);
          pstmt.setString(index++,  v_content);
          pstmt.setString(index++,  v_position);
          pstmt.setInt   (index++,  v_tabseq);
          pstmt.setInt   (index++,  v_seq);

          isOk = pstmt.executeUpdate();
          isOk2 = UploadUtil.fnRegisterAttachFile(box);
          isOk3 = this.insertUpFile(connMgr,v_tabseq, v_seq, box);

          if(isOk > 0 && isOk2 >0 && isOk3 >0 ){
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
	아카데미 홍보관  - 메인
	@param box	  receive from the form object and session
	@return ArrayList
	 */
	public ArrayList<DataBox> selectAcademyPrList(RequestBox box) throws Exception {
		DBConnectionManager connMgr 	= null;

		ListSet ls1		 				= null;
		ArrayList list	 				= null;

		StringBuffer sql		 		= new StringBuffer();		
		DataBox dbox = null;		
		try {

			connMgr = new DBConnectionManager();

			list = new ArrayList();
			sql.append(" SELECT ROWNUM, A.*, TO_CHAR(TO_DATE(SYSDATE-8),'YYYYMMDDHH24MISS') AS AGODATE FROM");
			sql.append(" (SELECT BOARD.TABSEQ, BOARD.SEQ,BOARD.TITLE, BOARD.CONTENT, BOARD.INDATE, FILES.SAVEFILE");
			sql.append(" FROM TZ_PR_ARTICLES BOARD, TZ_BOARDFILE FILES");
			sql.append(" WHERE BOARD.TABSEQ = '2262' AND BOARD.FLAGYN = 'Y' AND BOARD.TABSEQ = FILES.TABSEQ(+) AND BOARD.SEQ = FILES.SEQ(+) AND FILES.FILESEQ = '1'");
			sql.append(" ORDER BY BOARD.INDATE DESC) A");
			sql.append(" WHERE ROWNUM <= 5");

			ls1 = connMgr.executeQuery(sql.toString());

			while (ls1.next()) {
				dbox = ls1.getDataBox();
				list.add(dbox);
			}

		} catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql.toString());
			throw new Exception("sql1 = " + sql.toString() + "\r\n" + ex.getMessage());
		} finally {
			if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}

		return list;
	}
}
