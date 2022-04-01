//**********************************************************
//  1. 제      목: 공지사항 관리
//  2. 프로그램명 : LetterAdminBean.java
//  3. 개      요: 공지사항 관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0공지사항
//  6. 작      성: 이창훈 2005. 7.  14
//  7. 수      정: 이나연 05.11.17 _ connMgr.setOracleCLOB 수정
//**********************************************************
package com.credu.homepage;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.course.*;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
 
public class LetterAdminBean {
	
	private ConfigSet config;
    private int row; 
    private	static final String	FILE_TYPE =	"p_file";			//		파일업로드되는 tag name
	private	static final int FILE_LIMIT	= 10;					//	  페이지에 세팅된 파일첨부 갯수
    
    
	public LetterAdminBean() {
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
	* @return ArrayList   전체공지 리스트
	* @throws Exception
	*/
	public ArrayList selectListNoticeAll(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		NoticeData data = null;
		DataBox dbox = null;
		
		String v_defaultcomp = "";
		
		v_defaultcomp = selectDefalutComp(box);
		
		String v_search     = box.getString("p_search");
		String v_searchtext = box.getString("p_searchtext");
		String v_selcomp    = box.getStringDefault("p_selcomp", v_defaultcomp);
		String s_gadmin     = box.getSession("gadmin");
		String v_gadmin  = "";
		
		String v_selGroup   = box.getStringDefault("p_selGroup","ALL"); //교육주관
		
		if(!s_gadmin.equals("")){
		  v_gadmin = s_gadmin.substring(0, 1);
		}

        int v_tabseq = box.getInt("p_tabseq");

		try {
			connMgr = new DBConnectionManager();

			list = new ArrayList();
			sql += " select       \n";
			sql += "   rownum rowcount,     \n";
			sql += "   a.seq,        \n";
			sql += "   a.addate,     \n";
			sql += "   a.adtitle,    \n";
			sql += "   a.adname,     \n";
			sql += "   a.cnt,        \n";
			sql += "   a.luserid,    \n";
			sql += "   a.ldate,      \n";
			sql += "   a.isall,      \n";
			sql += "   a.useyn,      \n";
			sql += "   a.popup,      \n";
			sql += "   a.loginyn,    \n";
			sql += "   a.gubun,      \n";
			sql += "   a.aduserid,    \n";
			sql += "		(select count(realfile) from tz_boardfile where tabseq = a.TABSEQ and seq = a.seq) filecnt ";
			sql += " from TZ_LETTER  a";
			sql += "    where isall = 'Y'                                                                    ";

			if(!v_selcomp.equals("ALL")){
			  //sql += "    and compcd like '%"+v_selcomp+"%' ";
			  
			  //총괄관리자가 아닌경우 해당 교육주관만 나타나도록 함
			  if (!v_selGroup.equals("ALL")) {
			  		sql += "    and grcodecd like '%"+v_selGroup+"%' ";
			  }
			}

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면
				if (v_search.equals("adtitle")) {                          //    제목으로 검색할때
					sql += " and adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
				} else if (v_search.equals("adcontents")) {                //    내용으로 검색할때
					sql += " and adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%");
				}
			}
			
			sql += "      and tabseq = " +  v_tabseq;
			sql += "    order by seq desc                                                                    ";
			System.out.println("notice all sql = " + sql);
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
	* 공지사항화면 리스트
	* @param box          receive from the form object and session
	* @return ArrayList   공지사항 리스트(전체공지 제외)
	* @throws Exception
	*/
	public ArrayList selectListNotice(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		// 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
        String sql    = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";		
        String group_sql  = "";
        String order_sql  = "";
		
		NoticeData data = null;
		DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");
        
        String v_defaultcomp = "";
		v_defaultcomp = selectDefalutComp(box);
        
		String v_searchtext = box.getString("p_searchtext");
		String v_search     = box.getString("p_search");
		String v_selcomp    = box.getStringDefault("p_selcomp", v_defaultcomp);
		
		String s_gadmin     = box.getSession("gadmin");
		String v_gadmin  = "";
		
		String v_selGroup   = box.getStringDefault("p_selGroup","ALL"); //교육주관
		
		if(!s_gadmin.equals("")){
		  v_gadmin = s_gadmin.substring(0, 1);
		}
		
		int   	 		v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int    			v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			head_sql += " select       \n";
			head_sql += "   rownum rowcount,     \n";
			head_sql += "   a.seq,        \n";
			head_sql += "   a.addate,     \n";
			head_sql += "   a.adtitle,    \n";
			head_sql += "   a.adname,     \n";
			head_sql += "   a.cnt,        \n";
			head_sql += "   a.luserid,    \n";
			head_sql += "   a.ldate,      \n";
			head_sql += "   a.isall,      \n";
			head_sql += "   a.useyn,      \n";
			head_sql += "   a.popup,      \n";
			head_sql += "   a.loginyn,    \n";
			head_sql += "   a.aduserid,    \n";
			head_sql += "   a.gubun      \n";
			
			body_sql += " from TZ_LETTER  a";
			body_sql += "  where ";
			body_sql += "  isall = 'N' ";
			body_sql += "    and tabseq = " +  v_tabseq;
			
			System.out.println("v_selGroup = " + v_selGroup);
			System.out.println("v_selcomp = " + v_selcomp);
			if(!v_selcomp.equals("ALL")){
				//body_sql += "    and compcd like '%" +  v_selcomp +"%' ";
				System.out.println("#1");
			  //총괄관리자가 아닌경우 해당 교육주관만 나타나도록 함
			  if (!v_selGroup.equals("ALL")) {
			  	System.out.println("#2");
			  		body_sql += "    and grcodecd like '%"+v_selGroup+"%' ";
			  }
			}

			if ( !v_searchtext.equals("")) {      //    검색어가 있으면
				if (v_search.equals("adtitle")) {                          //    제목으로 검색할때
					body_sql += " and adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%");
				} else if (v_search.equals("adcontents")) {                //    내용으로 검색할때
					body_sql += " and adcontent like " + StringManager.makeSQL("%" + v_searchtext + "%");
				}
			}
			order_sql += " order by seq desc ";
			
			sql= head_sql+ body_sql+ order_sql;

			System.out.println("notice sql = " + sql);
			
			ls = connMgr.executeQuery(sql);
			
			count_sql= "select count(*) "+ body_sql;
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
	public int insertNotice(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		PreparedStatement pstmt = null;
		String sql  = "";
		String sql1 = "";
		String sql2 = "";
		int isOk  = 0;
		int isOk2 = 0;
		int v_seq = 0;

        int v_tabseq = box.getInt("p_tabseq");
		String v_gubun     = box.getStringDefault("p_gubun","N");
		String v_startdate = box.getString("p_startdate");
		String v_enddate   = box.getString("p_enddate");
		String v_adtitle   = box.getString("p_adtitle");
		String v_content   = StringUtil.removeTag(box.getString("p_content"));
		String v_loginyn   = box.getString("p_login");
		String v_useyn     = box.getString("p_use");
		String v_compcd	   = box.getString("p_compcd");
		int v_popwidth     = box.getInt("p_popsize1");
		int v_popheight    = box.getInt("p_popsize2");
		int v_popxpos      = box.getInt("p_popposition1");
		int v_popypos      = box.getInt("p_popposition2");
		String v_popup	   = box.getString("p_popup");
		String v_upfile	   = box.getNewFileName("p_file1");
		String v_realfile  = box.getRealFileName("p_file1");
		String v_useframe  = box.getString("p_useframe");
		String v_uselist   = box.getString("p_uselist");
		String v_isall     = box.getString("p_isAllvalue");
		String v_grcodecd  = box.getString("p_grocdecd");

		String s_userid   = box.getSession("userid");
		String s_name     = box.getSession("name");
		
		String v_content1 = "";
		
Log.err.println("v_upfil1==> " + v_upfile);		
Log.err.println("v_realfile1==> " + v_realfile);
		
		try {
		   connMgr = new DBConnectionManager();		   
		   connMgr.setAutoCommit(false);

		   sql  = "select max(seq) from TZ_LETTER  ";
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
		   
           sql1 =  " insert into TZ_LETTER(               \n";
           sql1 += " tabseq, seq, gubun, startdate,       \n";
           sql1 += " enddate, addate, adtitle, adname,    \n";
           sql1 += " adcontent, cnt, luserid, ldate,      \n";
           sql1 += " loginyn, useyn, compcd, popwidth,    \n";
           sql1 += " popheight, popxpos, popypos, upfile, \n";
           sql1 += " realfile , popup, uselist, useframe, \n"; 
           sql1 += " isall, grcodecd, aduserid)           \n";
           sql1 += " values (";
           sql1 += " ?,            ?,            ?,            ?," ;
           sql1 += " ?,            to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ";
//         sql1 += " empty_clob(), ?,            ?,            dbo.to_date(getdate(), 'YYYYMMDDHH24MISS'), ";
		   sql1 += " ?, 		   ?,            ?,            to_char(sysdate, 'YYYYMMDDHH24MISS'), ";
//         sql1 += " ?,            ?,            empty_clob(), ?,";
		   sql1 += " ?,            ?,            ?, 		   ?,";
           sql1 += " ?,            ?,            ?,            ?,";
           sql1 += " ?,            ?,            ?,            ?, ";
//         sql1 += " ?,            empty_clob(), ?) ";
		   sql1 += " ?,            ?,			 ?) ";
           
/*		   System.out.println("1 = "+v_tabseq);
           System.out.println("2 = "+v_seq);
           System.out.println("3 = "+v_gubun);
           System.out.println("4 = "+v_startdate);
           System.out.println("5 = "+v_enddate);
           System.out.println("6 = "+v_adtitle);
           System.out.println("7 = "+s_name);
		   System.out.println("8 = "+v_content);
		   System.out.println("9 = "+0);		  
           System.out.println("10 = "+s_userid);
           System.out.println("11 = "+v_loginyn);
           System.out.println("12 = "+v_useyn);
		   System.out.println("13 = "+v_compcd);
           System.out.println("14 = "+v_popwidth);
           System.out.println("15 = "+v_popheight);
           System.out.println("16 = "+v_popxpos);
           System.out.println("17 = "+v_popypos);   
           System.out.println("18 = "+v_upfile);       //파일
           System.out.println("19 = "+v_realfile);     //파일
           System.out.println("20 = "+v_popup);        //팝업여부
           System.out.println("21 = "+v_uselist);      //리스트사용여부
           System.out.println("22 = "+v_useframe);     //프레임사용여부
           System.out.println("23 = "+v_isall);        //전체공지여부
		   System.out.println("24 = "+v_grcodecd);
           System.out.println("25 = "+s_userid);   	   //교육그룹코드
*/
           pstmt = connMgr.prepareStatement(sql1);           
           pstmt.setInt   (1,  v_tabseq);
           pstmt.setInt   (2,  v_seq);
           pstmt.setString(3,  v_gubun);
           pstmt.setString(4,  v_startdate);
           pstmt.setString(5,  v_enddate);
           pstmt.setString(6,  v_adtitle);
           pstmt.setString(7,  s_name);
		   pstmt.setString(8, v_content);
		   pstmt.setInt   (9,  0);		  
           pstmt.setString(10, s_userid);
           pstmt.setString(11, v_loginyn);
           pstmt.setString(12, v_useyn);
		   pstmt.setString(13, v_compcd);
           pstmt.setInt   (14, v_popwidth);
           pstmt.setInt   (15, v_popheight);
           pstmt.setInt   (16, v_popxpos);
           pstmt.setInt   (17, v_popypos);   
           pstmt.setString(18, v_upfile);       //파일
           pstmt.setString(19, v_realfile);     //파일
           pstmt.setString(20, v_popup);        //팝업여부
           pstmt.setString(21, v_uselist);      //리스트사용여부
           pstmt.setString(22, v_useframe);     //프레임사용여부
           pstmt.setString(23, v_isall);        //전체공지여부
		   pstmt.setString(24, v_grcodecd);
           pstmt.setString(25, s_userid);     //교육그룹코드
		   
           isOk = pstmt.executeUpdate();
           /*
           sql2 = "select adcontent from TZ_LETTER where tabseq = " + v_tabseq + " and seq = " + v_seq ;
           connMgr.setOracleCLOB(sql2, v_content);       //      (기타 서버 경우)       
           
           sql2 = "select compcd from TZ_LETTER where seq = " + v_seq + "and tabseq = " + v_tabseq;
           connMgr.setOracleCLOB(sql2, v_compcd);       //      (기타 서버 경우)       
           
           sql2 = "select grcodecd from TZ_LETTER where seq = " + v_seq + "and tabseq = " + v_tabseq;
           connMgr.setOracleCLOB(sql2, v_grcodecd);       //      (기타 서버 경우)       
           */          
           isOk2 = this.insertUpFile(connMgr,v_tabseq, v_seq, box);
           
           if(isOk > 0 && isOk2 >0 ){
           	  connMgr.commit();
           	}else{
           	  connMgr.rollback();		   	
           	}
           Log.err.println("isOk==> " + isOk);
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql1);
			throw new Exception("sql ->" + sql1 + "\r\n" + ex.getMessage());
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
	 public int updateNotice(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		String sql = "";
		String sql2 = "";
		int isOk = 0;
		int isOk2 = 0;
		int isOk3 = 0;
		
		box.put("p_type", "HN");

        int v_tabseq = box.getInt("p_tabseq");
		int v_seq          = box.getInt("p_seq");
		String v_gubun     = box.getStringDefault("p_gubun","N");
		String v_startdate = box.getString("p_startdate");
		String v_enddate   = box.getString("p_enddate");
		String v_adtitle   = box.getString("p_adtitle");
		String v_adcontent = box.getString("p_adcontent");
		String v_loginyn   = box.getString("p_login");
		String v_useyn     = box.getString("p_use");
		String v_compcd    = box.getString("p_compcd");
		int v_popwidth     = box.getInt("p_popsize1");
		int v_popheight    = box.getInt("p_popsize2");
		int v_popxpos      = box.getInt("p_popposition1");
		int v_popypos      = box.getInt("p_popposition2");
		String v_popup     = box.getString("p_popup");
		String v_uselist   = box.getString("p_uselist");
		String v_useframe  = box.getString("p_useframe");
		String v_isall     = box.getString("p_isAllvalue");
		String v_grcodecd  = box.getString("p_grocdecd");
		
		String s_userid   = box.getSession("userid");
		String s_name     = box.getSession("name");		
		
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
				   v_adcontent =(String) NamoMime.setNamoContent(v_adcontent);
			   } catch(Exception e) {
				   System.out.println(e.toString());
				   return 0;
			   }
			   /*********************************************************************************************/    
		   
			sql  = " update TZ_LETTER set gubun = ? ,  startdate = ? , enddate = ? , adtitle = ? ,adname = ? ,       ";
//			sql += "                      adcontent = empty_clob() , luserid = ? , ldate = dbo.to_date(getdate(), 'YYYYMMDDHH24MISS'),";
			sql += "                      adcontent = ? , luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS'),";
//			sql += " loginyn= ?, useyn = ?, compcd = empty_clob(), popwidth = ?, popheight = ?, popxpos = ?, popypos = ?, 		 ";
			sql += " loginyn= ?, useyn = ?, compcd = ?, popwidth = ?, popheight = ?, popxpos = ?, popypos = ?, 		 ";
			sql += " popup=?, uselist= ?, useframe = ?, isall = ? , grcodecd = ? ";
			sql += " where tabseq = ? and seq = ?                                                                    ";

			pstmt = connMgr.prepareStatement(sql);

			pstmt.setString(1, v_gubun);
			pstmt.setString(2, v_startdate);
			pstmt.setString(3, v_enddate);
			pstmt.setString(4, v_adtitle);
			pstmt.setString(5, s_name);
			pstmt.setString(6, v_adcontent);
			pstmt.setString(7, s_userid);
			pstmt.setString(8, v_loginyn);
			pstmt.setString(9, v_useyn);
			pstmt.setString(10, v_compcd);
			pstmt.setInt   (11, v_popwidth); 
			pstmt.setInt   (12, v_popheight);
			pstmt.setInt   (13, v_popxpos);  
			pstmt.setInt   (14, v_popypos);  
			pstmt.setString(15, v_popup);
			pstmt.setString(16, v_uselist);
			pstmt.setString(17, v_useframe);
			pstmt.setString(18, v_isall);
			pstmt.setString(19, v_grcodecd);
			pstmt.setInt   (20, v_tabseq);
			pstmt.setInt   (21, v_seq);
			
			isOk = pstmt.executeUpdate();
			/* 05.11.16 이나연
			sql2 = "select adcontent from TZ_LETTER where seq = " + v_seq + "and tabseq = " + v_tabseq;
			connMgr.setOracleCLOB(sql2, v_adcontent);       //      (기타 서버 경우)       
			
			sql2 = "select compcd from TZ_LETTER where seq = " + v_seq + "and tabseq = " + v_tabseq;
			connMgr.setOracleCLOB(sql2, v_compcd);       //      (기타 서버 경우)       
			
			sql2 = "select grcodecd from TZ_LETTER where seq = " + v_seq + "and tabseq = " + v_tabseq;
			connMgr.setOracleCLOB(sql2, v_grcodecd);       //      (기타 서버 경우)
			*/       
			
			isOk3 =	this.deleteUpFile(connMgr, box,	v_filesequence);		//	   삭제할 파일이 있다면	파일table에서 삭제
			isOk2 =	this.insertUpFile(connMgr, v_tabseq, v_seq,	box);		//		파일첨부했다면 파일table에	insert
			
			if(isOk > 0 && isOk2 > 0 && isOk3 > 0){
				connMgr.commit();
				if (v_savefile != null)	{
					FileManager.deleteFile(v_savefile);			//	 DB	에서 모든처리가	완료되면 해당 첨부파일 삭제
				}
				isOk =1;
			}
			else{
				connMgr.rollback();
				isOk =0;
			}
		}
		catch(Exception ex) {connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
	public int deleteNotice(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt = null;
		String sql = "";
		int isOk = 0;
		int isOk2 = 1;
		
        int v_tabseq = box.getInt("p_tabseq");
		int v_seq  = box.getInt("p_seq");
		int	v_upfilecnt	= box.getInt("p_upfilecnt");	//	서버에 저장되있는 파일수		
		Vector v_savefile  = box.getVector("p_savefile");

		try {
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);
			sql  = " delete from TZ_LETTER           ";
			sql += "   where tabseq = ? and seq = ?  ";

			pstmt = connMgr.prepareStatement(sql);
			pstmt.setInt(1, v_tabseq);
			pstmt.setInt(2, v_seq);
			isOk = pstmt.executeUpdate();
			
			if (v_upfilecnt > 0 ) {

				pstmt.close();
				
				sql = "delete from	TZ_BOARDFILE where tabseq = ? and seq =	?";

				pstmt = connMgr.prepareStatement(sql);
	
				pstmt.setInt(1, v_tabseq);
				pstmt.setInt(2, v_seq);
	
				isOk2 =	pstmt.executeUpdate();
			}
			
			if (isOk >	0 && isOk2 > 0)	{
				connMgr.commit();
				if (v_savefile != null)	{
					FileManager.deleteFile(v_savefile);			//	 첨부파일 삭제
				}
			} else connMgr.rollback();
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
	* 공지사항화면 상세보기
	* @param box          receive from the form object and session
	* @return ArrayList   조회한 상세정보
	* @throws Exception
	*/
   public DataBox selectViewNotice(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		NoticeData data = null;
		DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");
		String v_seq = box.getString("p_seq");
		String v_process = box.getString("p_process");
		
		Vector realfileVector = new Vector();
		Vector savefileVector = new Vector();
		Vector fileseqVector  = new Vector();  

		try {
			connMgr = new DBConnectionManager();

			sql += " select \n";
			sql += "   a.seq,     \n";
			sql += "   a.gubun,   \n";
			sql += "   ltrim(rtrim(startdate)) startdate, \n";
			sql += "   ltrim(rtrim(enddate)) enddate,     \n";
			sql += "   a.addate,                    \n";
			sql += "   a.adtitle,                   \n";
			sql += "   a.adname,                    \n";
			sql += "   a.adcontent,                 \n";    
			sql += "   a.cnt,                       \n";
			sql += "   a.luserid,                   \n";
			sql += "   a.ldate,                     \n";
			sql += "   a.loginyn,                   \n"; 
			sql += "   a.useyn,                     \n"; 
			sql += "   a.compcd,                    \n";
			//sql += "   case When  a.compcd like '' Then '미입력'  Else a.compcd end as compcd, ";  //미입력
			sql += "   a.popwidth,                  \n";
			sql += "   a.popheight,                 \n";
			sql += "   a.popxpos,                   \n";
			sql += "   a.popypos,                   \n";
			sql += "   a.popup,                     \n";
			sql += "   a.uselist,                   \n";
			sql += "   a.useframe,                  \n";
			sql += "   a.isall,                     \n";
			sql += "   a.aduserid,                  \n";
			sql += "   a.grcodecd,                  \n";
			sql += "   b.realfile,                  \n";
			sql += "   b.savefile,                  \n";
			sql += "   b.fileseq                    \n";
			sql += " from TZ_LETTER a , TZ_BOARDFILE B  \n";
			sql += "  where a.seq    = " + StringManager.makeSQL(v_seq);
			sql += "    and a.tabseq = " +  v_tabseq;
			sql += "    and a.tabseq = b.tabseq(+)";
			sql += "    and a.seq = b.seq(+)";

			ls = connMgr.executeQuery(sql);
			
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
			if(!v_process.equals("popupview")){
			  connMgr.executeUpdate("update TZ_LETTER set cnt = cnt + 1 where seq = " + v_seq);
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("NoticeSql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) {try {ls.close();} catch(Exception e){}}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return dbox;
	}



	/**
	* 공지사항 홈페이지 메인화면 리스트(최신5개)
	* @param box          receive from the form object and session
	* @return ArrayList   공지사항 리스트(최신3개 전체공지 제외)
	* @throws Exception
	*/
	public ArrayList selectListNoticeMain(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		NoticeData data = null;
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
			// 수정일 : 05.11.17 수정자 : 이나연 _ rownum 수정
			sql  = " select seq, addate, adtitle, adname, cnt, luserid, ldate from  (" ;
			sql += " select rownum, seq, addate, adtitle, adname, cnt, luserid, ldate from TZ_LETTER  ";
//			sql += " select top 3 seq, addate, adtitle, adname, cnt, luserid, ldate from TZ_LETTER  ";
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
			sql += "    order by seq desc                                                                         ";
			sql += " } where rownum < 4                                                                              ";

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
		NoticeData data = null;
		DataBox dbox = null;

        int    v_tabseq = box.getInt("p_tabseq");
        String v_today  = FormatDate.getDate("yyyyMMdd");        
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
			
			sql += " select          \n";
			sql += "   rownum rowcount,       \n";
			sql += "   seq,          \n";
			sql += "   addate,       \n";
			sql += "   adtitle,      \n";
			sql += "   adname,       \n";
			sql += "   adcontent,    \n";
			sql += "   cnt,          \n";
			sql += "   uselist,      \n";
			sql += "   useframe,     \n";
			sql += "   popwidth,     \n";
			sql += "   popheight,     \n";
			sql += "   popxpos,     \n";
			sql += "   popypos,     \n";
			sql += "   luserid,      \n";
			sql += "   ldate         \n";
			sql += " from TZ_LETTER  \n";
			sql += "    where        \n";
			sql += "      tabseq = " +  v_tabseq;
			sql += "      and popup = 'Y'";
			sql += "      and to_char(sysdate,'YYYYMMDD') between startdate and enddate";

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
		NoticeData data = null;
		DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");
        
        String v_login  = "";
        String v_comp   = box.getSession("comp");
        String tem_grcode        = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));        
        
        String v_searchtext = box.getString("p_searchtext");
		String v_search     = box.getString("p_search");
        
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
			sql += " ( select rownum, seq, addate, adtitle, adname,  cnt, luserid, ldate from TZ_LETTER  ";
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
			sql = "Select seq, addate, adtitle, adname, cnt, luserid, ldate  \n";
			sql += " From TZ_LETTER	\n";
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
				data = new NoticeData();
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
		NoticeData data = null;
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
			sql += " select \n";
			// 수정일 : 05.11.17 수정자 : 이나연 _ rownum 쿼리 수정위해 오류 발생시킴
			//sql += "    top 3     \n";
			sql += "    seq,        \n";
			sql += "    addate,     \n";
			sql += "    adtitle,    \n";
			sql += "    adname,     \n";
			sql += "    cnt,        \n";
			sql += "    luserid,    \n";
			sql += "    ldate,      \n";
			sql += "    isall,      \n";
			sql += "    useyn,      \n";
			sql += "    popup,      \n";
			sql += "    loginyn,    \n";
			sql += "    gubun,      \n";
			sql += "    compcd,     \n";
			sql += "    uselist,     \n";
			sql += "    aduserid,    \n";
			sql += "    filecnt      \n";
			sql += " from            \n";
			sql += " (select         \n";
			//
			sql += "    rownum,       \n";
			sql += "    x.seq,        \n";
			sql += "    x.addate,     \n";
			sql += "    x.adtitle,    \n";
			sql += "    x.adname,     \n";
			sql += "    x.cnt,        \n";
			sql += "    x.luserid,    \n";
			sql += "    x.ldate,      \n";
			sql += "    x.isall,      \n";
			sql += "    x.useyn,      \n";
			sql += "    x.popup,      \n";
			sql += "    x.loginyn,    \n";
			sql += "    x.gubun,      \n";
			sql += "    x.compcd,     \n";
			sql += "    x.uselist,    \n";
			sql += "    x.tabseq,     \n";
			sql += "    x.adcontent,  \n";
			sql += "    x.aduserid,   \n";
			sql += "    x.grcodecd,   \n";
			sql += "	(select count(realfile) from tz_boardfile where tabseq = x.TABSEQ and seq = x.seq) filecnt ";
			sql += "  from      \n";
			sql += "    TZ_LETTER x ) a";
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
          sql += "    and rownum < 4";
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
	* 일반 공지사항화면 리스트(홈페이지more)
	* @param box          receive from the form object and session
	* @return ArrayList   공지사항 리스트(전체공지 제외)
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
		
		NoticeData data = null;
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
			head_sql += " select \n";
			//head_sql += "    rownum,     \n";
			head_sql += "    seq,        \n";
			head_sql += "    addate,     \n";
			head_sql += "    adtitle,    \n";
			head_sql += "    adname,     \n";
			head_sql += "    cnt,        \n";
			head_sql += "    luserid,    \n";
			head_sql += "    ldate,      \n";
			head_sql += "    isall,      \n";
			head_sql += "    useyn,      \n";
			head_sql += "    popup,      \n";
			head_sql += "    loginyn,    \n";
			head_sql += "    gubun,      \n";
			head_sql += "    compcd,     \n";
			head_sql += "    uselist,     \n";
			head_sql += "    aduserid,    \n";
			head_sql += "    filecnt      \n";
			body_sql += " from            \n";
			body_sql += " (select         \n";
			//body_sql += "    rownum,       \n";
			body_sql += "    x.seq,        \n";
			body_sql += "    x.addate,     \n";
			body_sql += "    x.adtitle,    \n";
			body_sql += "    x.adname,     \n";
			body_sql += "    x.cnt,        \n";
			body_sql += "    x.luserid,    \n";
			body_sql += "    x.ldate,      \n";
			body_sql += "    x.isall,      \n";
			body_sql += "    x.useyn,      \n";
			body_sql += "    x.popup,      \n";
			body_sql += "    x.loginyn,    \n";
			body_sql += "    x.gubun,      \n";
			body_sql += "    x.compcd,     \n";
			body_sql += "    x.uselist,    \n";
			body_sql += "    x.tabseq,     \n";
			body_sql += "    x.adcontent,  \n";
			body_sql += "    x.aduserid,   \n";
			body_sql += "    x.grcodecd,   \n";
			body_sql += "	(select count(realfile) from tz_boardfile where tabseq = x.TABSEQ and seq = x.seq) filecnt ";
			body_sql += "  from      \n";
			body_sql += "    TZ_LETTER x ) a";
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
	* 공지사항  명 select
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
	* 공지사항  default comp 셋팅
	* @param box          receive from the form object and session
	* @return ArrayList   공지 리스트
	* @throws Exception
    */
	
	public String selectDefalutComp(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		ArrayList list = null;
		String sql = "";
		//NoticeData data = null;
		DataBox dbox = null;

	    String s_userid   = box.getSession("userid");
		String s_gadmin   = box.getSession("gadmin"); 
		String v_gadmin = "";
		String returnValue = "";

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



    /**
    * 공지사항 새로운 자료파일 등록
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
			sql	= "select isnull(max(fileseq),	0) from	tz_boardfile	where tabseq = "+p_tabseq+" and seq = " +	p_seq ;

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
	}




	/**
	* 공지사항화면 이전글정보보기
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

			sql += " select seq,gubun, addate, adtitle, adname, adcontent, cnt, luserid, ldate from TZ_LETTER ";
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
	* 공지사항화면 다음글정보보기
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

			sql += " select seq, gubun, addate, adtitle, adname, adcontent, cnt, luserid, ldate from TZ_LETTER ";
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
	*  나모 액티브 미리보기 
	* @param box      receive from the form object and session
	* @return isOk    1:update success,0:update fail
	* @throws Exception
	*/
	 public String popupView(RequestBox box) throws Exception {

		String v_content   = StringUtil.removeTag(box.getString("p_content"));

		   /*********************************************************************************************/
		   // 나모 MIME 컨텐츠의 업로드 파일 및 등록가능한 컨텐츠로 변경합니다. 
		   try {
			   v_content =(String) NamoMime.setNamoContent(v_content);
		   } catch(Exception e) {
			   System.out.println(e.toString());
			   return null;
		   }
		   /*********************************************************************************************/ 
	
		return v_content;
	}
}
