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
public class ContactUsBean {

	private ConfigSet config;
    private int row;
    private	static final String	FILE_TYPE =	"p_file";			//		파일업로드되는 tag name
	private	static final int FILE_LIMIT	= 10;					//	  페이지에 세팅된 파일첨부 갯수


	public ContactUsBean() {
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
	* 리스트
	* @param box          receive from the form object and session
	* @return ArrayList   ContactUS 리스트
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

        String 			v_orderColumn = box.getString("p_orderColumn");
        String 			v_orderType = box.getString("p_orderType");
        
        int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			headSql.append(" SELECT                                   \n ");
			headSql.append("         ROWNUM                           \n ");
			headSql.append("         , SEQ                      \n ");
			headSql.append("         , COMPANY                    \n ");
			headSql.append("         , COM_NAME                   \n ");
			headSql.append("         , TEL                     \n ");
			headSql.append("         , MOBILE                      \n ");
			headSql.append("         , LDATE                    \n ");
						
			bodySql.append(" FROM    TZ_B2BCONTACT          \n ");
			bodySql.append(" WHERE   (1=1)                            \n ");

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면                                         
				if (v_search.equals("COMPANY")) {                          //    제목으로 검색할때  
					bodySql.append(" AND COMPANY LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");  
				} else if (v_search.equals("ETC")) {                //    내용으로 검색할때  
					bodySql.append(" AND ETC LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");
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
			sql.append("          SEQ                      \n ");
			sql.append("         , COMPANY                    \n ");
			sql.append("         , ADDR                  \n ");
			sql.append("         , COM_DEPT                   \n ");
			sql.append("         , COM_NAME                     \n ");
			sql.append("         , TEL                      \n ");
			sql.append("         , MOBILE                    \n ");
			sql.append("         , EMAIL                 \n ");
			sql.append("         , HOMEPAGE                   \n ");
			sql.append("         , CATEGORY                      \n ");
			
			sql.append("         , EDUSTART                      \n ");
			sql.append("         , EDUEND                      \n ");
			sql.append("         , EDUSTARTDAY                      \n ");
			sql.append("         , EDUPERIOD                      \n ");
			sql.append("         , EDUSTUDENT                      \n ");
			sql.append("         , EDUSUBJCNT                      \n ");
			sql.append("         , APPLY_GUBUN                      \n ");
			sql.append("         , APPLY_SUBJ                      \n ");
			sql.append("         , ETC                      \n ");
			sql.append("         , DOMAIN                      \n ");
			
			sql.append("         , SAVEFILE_L                 \n ");
			sql.append(" FROM    TZ_B2BCONTACT       	    \n ");
			sql.append(" WHERE   \n ");
			sql.append(" SEQ      = "+ StringManager.makeSQL(v_seq)+" \n");
			
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
   
   /**
	* 리스트
	* @param box          receive from the form object and session
	* @return ArrayList   ContactUS 리스트
	* @throws Exception
	*/
	public ArrayList selectListEtc(RequestBox box) throws Exception {
		
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
			headSql.append("         , SEQ                      \n ");
			headSql.append("         , USER_NAME                    \n ");
			headSql.append("         , TEL                   \n ");
			headSql.append("         , EMAIL                     \n ");
			headSql.append("         , TITLE                      \n ");
			headSql.append("         , ADDATE                    \n ");
						
			bodySql.append(" FROM    TZ_B2BCONTACTETC          \n ");
			bodySql.append(" WHERE   (1=1)                            \n ");

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면                                         
				if (v_search.equals("TITLE")) {                          //    제목으로 검색할때  
					bodySql.append(" AND TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");  
				} else if (v_search.equals("CONTENT")) {                //    내용으로 검색할때  
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
	* 화면 상세보기
	* @param box          receive from the form object and session
	* @return ArrayList   조회한 상세정보
	* @throws Exception
	*/
   public DataBox selectViewEtc(RequestBox box) throws Exception {
	 
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
			sql.append("          SEQ                      \n ");
			sql.append("         , USER_NAME                    \n ");
			sql.append("         , TEL                  \n ");
			sql.append("         , EMAIL                   \n ");
			sql.append("         , CATEGORY                     \n ");
			sql.append("         , TITLE                      \n ");
			sql.append("         , CONTENT                    \n ");
			sql.append("         , LINK                 \n ");
			sql.append("         , ADDATE                   \n ");
			sql.append("         , SAVEFILE_L                      \n ");
		
			sql.append(" FROM    TZ_B2BCONTACTETC       	    \n ");
			sql.append(" WHERE   \n ");
			sql.append(" SEQ      = "+ StringManager.makeSQL(v_seq)+" \n");
			
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
   
   
   /**
  	* 리스트
  	* @param box          receive from the form object and session
  	* @return ArrayList   ContactUS 리스트
  	* @throws Exception
  	*/
  	public ArrayList selectListVocation(RequestBox box) throws Exception {
  		
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
  			headSql.append(" SELECT                                   \n ");
  			headSql.append("         ROWNUM                           \n ");
  			headSql.append("         , SEQ                      \n ");
  			headSql.append("         , USER_NAME, JOB, CATEGORY                    \n ");
  			headSql.append("         , TEL                   \n ");
  			headSql.append("         , COMPANY                     \n ");
  			headSql.append("         , ADDATE                    \n ");
  						
  			bodySql.append(" FROM    TZ_B2BVOCATION          \n ");
  			bodySql.append(" WHERE   (1=1)                            \n ");

  			if ( !v_searchtext.equals("")) {      //    검색어가 있으면                                         
  				if (v_search.equals("COMPANY")) {                          //    제목으로 검색할때  
  					bodySql.append(" AND COMPANY LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");  
  				} else if (v_search.equals("USER_NAME")) {                //    내용으로 검색할때  
  					bodySql.append(" AND USER_NAME LIKE " + StringManager.makeSQL("%" + v_searchtext + "%")+" \n");
  				}                                                                                   
  			}  
  			orderSql = " ORDER BY SEQ DESC";	
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
  	* 화면 상세보기
  	* @param box          receive from the form object and session
  	* @return ArrayList   조회한 상세정보
  	* @throws Exception
  	*/
     public DataBox selectViewVocation(RequestBox box) throws Exception {
  	 
  		DBConnectionManager connMgr = null;
  		ListSet ls = null;
  		StringBuffer sql = new StringBuffer();
  		DataBox dbox = null;

  		String v_seq = box.getString("p_seq");
  		String v_process = box.getString("p_process");

  		try {
  			connMgr = new DBConnectionManager();

  			sql.append(" SELECT                                   \n ");
  			sql.append("          SEQ                      \n ");
  			sql.append("         , COMPANY                    \n ");
  			sql.append("         , CATEGORY                  \n ");
  			sql.append("         , USER_NAME                   \n ");
  			sql.append("         , JOB                     \n ");
  			sql.append("         , TEL                      \n ");
  			sql.append("         , ADDATE                   \n ");
  			sql.append(" FROM    TZ_B2BVOCATION      	    \n ");
  			sql.append(" WHERE   \n ");
  			sql.append(" SEQ      = "+ StringManager.makeSQL(v_seq)+" \n");
  			
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
   
   /**
	* 웹취약성 신고 리스트
	* @param box          receive from the form object and session
	* @return ArrayList   ContactUS 리스트
	* @throws Exception
	*/
	public ArrayList selectListWebReport(RequestBox box) throws Exception {
		
		DBConnectionManager connMgr = null;
		ListSet 			ls 		= null;
		ArrayList 			list 	= null;
		StringBuffer 	headSql 	= new StringBuffer();
		StringBuffer 	bodySql 	= new StringBuffer();
		StringBuffer 	orderSql 	= new StringBuffer();
		String 			countSql 	= "";
		String 			sql			= "";
		DataBox 		dbox 		= null;

       int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
       int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			headSql.append(" SELECT						\n ");
			headSql.append("         SEQ				\n ");
			headSql.append("         , GUBUN			\n ");
			headSql.append("         , CONTENT			\n ");
			headSql.append("         , SMSYN			\n ");
			headSql.append("         , INDATE			\n ");
			headSql.append("         , PROCESSING		\n ");
			headSql.append("         , TITLE			\n ");
			headSql.append("         , CRYPTO.DEC('normal',EMAIL) AS EMAIL	\n ");
			bodySql.append(" FROM    TZ_WEBREPORT		\n ");
			bodySql.append(" WHERE   (1=1)				\n ");
			orderSql.append("ORDER BY INDATE DESC");

			sql = headSql.toString() + bodySql.toString() + orderSql.toString();
			
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
	
	public String getHandphone(String p_seq) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls1 = null;
		String sql1 = "";		
		String result = "";

		try {
			connMgr = new DBConnectionManager();

			// tz_webreport 테이블 seq 번호로 비교하여 가져오기
			sql1  = " select crypto.dec('normal',handphone) as handphone     ";
			sql1 += " from tz_webreport  ";		
			sql1 += " WHERE seq ="+ StringManager.makeSQL(p_seq);			

			ls1 = connMgr.executeQuery(sql1);
			if (ls1.next()) {
				result = ls1.getString("handphone");
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex);
			throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls1 != null) {try {ls1.close();} catch(Exception e){}}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}
	
	 public int update_Report_smsYn(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		StringBuffer updateSql = new StringBuffer();
		int isOk = 0;
		int v_seq = box.getInt("p_seq");
		
		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
	
			updateSql.append(" UPDATE TZ_WEBREPORT SET                                        \n ");
			updateSql.append("     SENDSMS = 'Y' , SENDSMSDATE = TO_CHAR(SYSDATE,'YYYYMMDDHH24MISS') \n ");
			updateSql.append(" WHERE                                                          \n ");
			updateSql.append("     SEQ = ?                                                    \n ");
	
			pstmt = connMgr.prepareStatement(updateSql.toString());
			
			int index = 1;
			pstmt.setInt(index++,  v_seq );
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
	     public DataBox selectViewWebReport(RequestBox box) throws Exception {
	  	 
	  		DBConnectionManager connMgr = null;
	  		ListSet ls = null;
	  		StringBuffer sql = new StringBuffer();
	  		DataBox dbox = null;
	  		String v_seq = box.getString("p_seq");

	  		try {
	  			connMgr = new DBConnectionManager();

	  			sql.append(" SELECT						\n ");
	  			sql.append("		SEQ,				\n ");
	  			sql.append("		GUBUN,				\n ");
	  			sql.append("		CONTENT,			\n ");
	  			sql.append("		SMSYN,				\n ");
	  			sql.append("		INDATE,				\n ");
	  			sql.append("		PROCESSING,			\n ");
	  			sql.append("		PROCESSINGDATE,		\n ");
	  			sql.append("		PROCESSINGCONTENT,	\n ");
	  			sql.append("		TITLE,				\n ");
	  			sql.append("		CRYPTO.DEC('normal', EMAIL) AS EMAIL	\n ");
	  			sql.append("   FROM TZ_WEBREPORT		\n ");
	  			sql.append("  WHERE						\n ");
	  			sql.append("        SEQ = "+ StringManager.makeSQL(v_seq)+" \n");
	  			
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
