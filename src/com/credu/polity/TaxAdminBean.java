//**********************************************************
//1. 제      목: 세금계산서  관리 (어드민)
//2. 프로그램명: TaxAdminBean.java
//3. 개      요: 게시판
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 하경태 2006.02.03
//7. 수      정:
//**********************************************************
package com.credu.polity;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;

import oracle.jdbc.driver.*;
import oracle.sql.*;
import com.credu.common.*;
import com.credu.complete.CompleteStatusData;
import com.credu.library.*;
import com.credu.study.*;
import com.credu.system.*;
import com.namo.*;
import com.credu.polity.*;


public class TaxAdminBean {
	
	private ConfigSet config;
	private int row;
	private static final String FILE_TYPE = "p_file";           //      파일업로드되는 tag name
	private static final int FILE_LIMIT = 1;                    //    페이지에 세팅된 파일첨부 갯수
	
	public TaxAdminBean() {
	  try{
	      config = new ConfigSet();
	      row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
	  }
	  catch(Exception e) {
	      e.printStackTrace();
	  }
}
	
	/**
	  수료명단 리스트
	  @param box      receive from the form object and session
	  @return ArrayList
	  */
	   public ArrayList selectList(RequestBox box) throws Exception {
			PreparedStatement pstmt = null;   
	      DBConnectionManager connMgr = null;
	      ListSet ls         = null;
	      ArrayList list     = null;
	      String sql1         = "";
	  String sql2         = "";
	  DataBox dbox = null;
	
	  String  v_Bcourse   = ""; //이전코스
	  String  v_course    = ""; //현재코스
	  String  v_Bcourseseq= ""; //이전코스차수
	  String  v_courseseq = ""; //현재코스차수
	  int v_pageno        = box.getInt("p_pageno");
	  int     l           = 0;
	  String  ss_grcode   = box.getStringDefault("s_grcode","ALL");       //교육그룹
	  String  ss_gyear    = box.getStringDefault("s_gyear","ALL");        //년도
	  String  ss_grseq    = box.getStringDefault("s_grseq","ALL");        //교육차수
	  String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //과정분류
	  String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //과정분류
	  String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //과정분류
	  String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");   //과정&코스
	  String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");      //과정 차수
	  String  ss_action   = box.getString("s_action");
	  String  ss_isusegubun   = box.getString("s_isusegubun");
	  
	  String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
	  String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서
		
	  String sql    	  = "";
	  String count_sql  = "";
	  String head_sql   = "";
		String body_sql   = "";		
	  String group_sql  = "";
	  String order_sql  = "";
	
	  ManagerAdminBean bean = null;
	  String  v_sql_add   = "";
	  String  v_userid    = box.getSession("userid");
	
	  try {
	      connMgr = new DBConnectionManager();
	      list = new ArrayList();
			
	      head_sql = "select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,c.subjseqgr,";
			head_sql += "	C.isonoff, B.membergubun, ";
			head_sql += " 	B.userid, B.name, C.edustart, C.eduend, ";
			head_sql += "	C.place, A.isuseyn, A.isusedate, A.gubun, C.isonoff, A.ldate ";
			body_sql += " from TZ_TAX A,TZ_MEMBER B,VZ_SCSUBJSEQ C ";
			body_sql += " where a.userid = b.userid and A.subj=C.subj ";
			body_sql += "	and A.year=C.year and A.subjseq=C.subjseq ";
			
	      if (!ss_grcode.equals("ALL")) {
				body_sql += " and C.grcode = "+SQLString.Format(ss_grcode);
	      }
	      if (!ss_grseq.equals("ALL")) {
				body_sql += " and C.grseq = "+SQLString.Format(ss_grseq);
	      }            
	      if (!ss_uclass.equals("ALL")) {
				body_sql += " and C.scupperclass = "+SQLString.Format(ss_uclass);
	      }
	      if (!ss_mclass.equals("ALL")) {
				body_sql += " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
	      }
	      if (!ss_lclass.equals("ALL")) {
				body_sql += " and C.sclowerclass = "+SQLString.Format(ss_lclass);
	      }
	
	      if (!ss_subjcourse.equals("ALL")) {
				body_sql += " and C.scsubj = "+SQLString.Format(ss_subjcourse);
	      }
	      if (!ss_subjseq.equals("ALL")) {
				body_sql += " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
	      }			
		  if (!ss_gyear.equals("ALL")) {
				body_sql += " and C.scyear = "+SQLString.Format(ss_gyear);
	      }
		  if (!ss_isusegubun.equals("ALL")) {
				body_sql += " and A.gubun = "+SQLString.Format(ss_isusegubun);
	      }
	      	
			order_sql += " order by b.ldate desc ";
				
				sql= head_sql+ body_sql+group_sql+ order_sql;
	System.out.println("selectList.sql = " + sql);				
			ls = connMgr.executeQuery(sql);
							
			count_sql= "select count(*) "+ body_sql;
	
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);
		
	        ls.setPageSize(row);                       //  페이지당 row 갯수를 세팅한다
	        ls.setCurrentPage(v_pageno, total_row_count);	// 현재페이지번호를 세팅한다.
	        int totalpagecount = ls.getTotalPage();    		// 전체 페이지 수를 반환한다
	
	        while (ls.next()) {
	            dbox = ls.getDataBox();
	
	            dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
	            dbox.put("d_totalpage", new Integer(totalpagecount));
	            dbox.put("d_rowcount", new Integer(row));
	            dbox.put("d_total_rowcount", new Integer(total_row_count));
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
	  수료명단 리스트(Excel)
	  @param box      receive from the form object and session
	  @return ArrayList
	  */
	   public ArrayList selectExcelList(RequestBox box) throws Exception {
			PreparedStatement pstmt = null;   
	      DBConnectionManager connMgr = null;
	      ListSet ls         = null;
	      ArrayList list     = null;
	      String sql1         = "";
	  String sql2         = "";
	  DataBox dbox = null;
	
	  String  v_Bcourse   = ""; //이전코스
	  String  v_course    = ""; //현재코스
	  String  v_Bcourseseq= ""; //이전코스차수
	  String  v_courseseq = ""; //현재코스차수
	  int v_pageno        = box.getInt("p_pageno");
	  int     l           = 0;
	  String  ss_grcode   = box.getStringDefault("s_grcode","ALL");       //교육그룹
	  String  ss_gyear    = box.getStringDefault("s_gyear","ALL");        //년도
	  String  ss_grseq    = box.getStringDefault("s_grseq","ALL");        //교육차수
	  String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //과정분류
	  String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //과정분류
	  String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //과정분류
	  String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");   //과정&코스
	  String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");      //과정 차수
	  String  ss_action   = box.getString("s_action");
	  
	  String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
	  String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서
		
	  String sql    	  = "";
	  String count_sql  = "";
	  String head_sql   = "";
		String body_sql   = "";		
	  String group_sql  = "";
	  String order_sql  = "";
	
	  ManagerAdminBean bean = null;
	  String  v_sql_add   = "";
	  String  v_userid    = box.getSession("userid");
	
	  try {
	      connMgr = new DBConnectionManager();
	      list = new ArrayList();
			
	      head_sql = "select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,c.subjseqgr,A.serno, ";
			head_sql += "	C.isonoff, B.resno, B.membergubun, ";
			head_sql += " 	B.userid, B.name, C.edustart, C.eduend, A.tstep, A.avtstep, ";
			head_sql += "	A.score,A.isgraduated,B.email,B.ismailing,C.place, B.MemberGubun ";
			body_sql += " from TZ_STOLD A,TZ_MEMBER B,VZ_SCSUBJSEQ C where c.isclosed = 'Y'  ";
			body_sql += " 	and A.userid=B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";
	      if (!ss_grcode.equals("ALL")) {
				body_sql += " and C.grcode = "+SQLString.Format(ss_grcode);
	      }
	      if (!ss_grseq.equals("ALL")) {
				body_sql += " and C.grseq = "+SQLString.Format(ss_grseq);
	      }
	      
	      if (!ss_uclass.equals("ALL")) {
				body_sql += " and C.scupperclass = "+SQLString.Format(ss_uclass);
	      }
	      if (!ss_mclass.equals("ALL")) {
				body_sql += " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
	      }
	      if (!ss_lclass.equals("ALL")) {
				body_sql += " and C.sclowerclass = "+SQLString.Format(ss_lclass);
	      }
	
	      if (!ss_subjcourse.equals("ALL")) {
				body_sql += " and C.scsubj = "+SQLString.Format(ss_subjcourse);
	      }
	      if (!ss_subjseq.equals("ALL")) {
				body_sql += " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
	      }
	      	
			order_sql += " order by b.ldate desc ";
				
				sql= head_sql+ body_sql+group_sql+ order_sql;
	System.out.println("sql = " + sql);				
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
	 
	/** 세금 계산서 내용 변경. 
	  @param box      receive from the form object and session
	  @return ArrayList
	  */
	   public int TaxUpdate(RequestBox box) throws Exception {
		   DBConnectionManager connMgr = null;

	        PreparedStatement pstmt = null;
	        String sql = "";
	        int isOk = 0;
			String v_isuse 	= "";
			String v_isusedate	= "";
			String v_subj			= "";		
			String v_year			= "";
			String v_subjseq		= "";
			String v_userid			= "";
			String v_grtype			= "";
			
			String v_chkvalue		= box.getString("p_chkvalue");
			String v_grcode 		= box.getString("s_grcode");
			int v_chknum			= box.getInt("p_chknum");
			
			//String[] arr_tmp	= null;
			//String[] arr_value	= null;

			int i = 0;
			
			try
			{
	            connMgr = new DBConnectionManager();
	            connMgr.setAutoCommit(false);
								
				StringTokenizer arr_tmp = new StringTokenizer(v_chkvalue, "|");
				
				sql = " Update TZ_TAX ";
				sql +=" Set isuseYn = ?, isusedate = ? ";
				sql +=" Where subj = ? and year = ? and subjseq = ? and userid = ? and grcode = ? ";
				
				pstmt = connMgr.prepareStatement(sql);

				while (arr_tmp.hasMoreTokens() && arr_tmp.hasMoreTokens())
				{
					StringTokenizer arr_value = new StringTokenizer(arr_tmp.nextToken(), ",");
					while (arr_value.hasMoreTokens() && arr_value.hasMoreTokens())
					{				
						v_isuse 		= arr_value.nextToken();
						v_isusedate 	= arr_value.nextToken();
						v_userid 		= arr_value.nextToken();
						v_subj		 	= arr_value.nextToken();
						v_year 			= arr_value.nextToken();
						v_subjseq	 	= arr_value.nextToken();
				
			            pstmt.setString(1, v_isuse);
			            pstmt.setString(2, v_isusedate);
			            pstmt.setString(3, v_subj);
			            pstmt.setString(4, v_year);
			            pstmt.setString(5, v_subjseq);
			            pstmt.setString(6, v_userid);
			            pstmt.setString(7, v_grcode);
//System.out.println(v_isuse+" : "+v_isusedate +" : "+ v_subj +" : "+ v_year +" : "+ v_subjseq +" : "+ v_userid +" : "+ v_grcode);
						isOk = pstmt.executeUpdate();
					}
				}
				
			}
		    catch(Exception ex) {
		        isOk = 0;
		        connMgr.rollback();
		        ErrorManager.getErrorStackTrace(ex, box, sql);
		        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		    }
		    finally {
		        if (isOk > 0) {connMgr.commit();}
		        if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
		        if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
		        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		    }
		    return isOk;
	}
	   
	   /** 세금 계산서 내용 변경. 
		  @param box      receive from the form object and session
		  @return ArrayList
		  */
		   public int TaxInsert(RequestBox box) throws Exception {
			   DBConnectionManager connMgr = null;

		        PreparedStatement pstmt = null;
		        String sql = "";
		        int isOk = 0;
				String v_userid		= box.getSession("userid");
				String v_grcode		= box.getSession("tem_grcode");
				String v_isuse 		= box.getString("p_isuse");
				String v_subj		= box.getString("p_subj");		
				String v_year		= box.getString("p_subjseq");
				String v_subjseq	= box.getString("p_year");
				
				String v_post1		= box.getString("p_post1");
				String v_post2		= box.getString("p_post2");
				String v_addr1		= box.getString("p_addr1");
				String v_addr2		= box.getString("p_addr2");
				String v_gubun		= box.getString("p_gubun");
				String v_money		= box.getString("p_money");

				int i = 0;
System.out.println(v_post1 + " : " +  v_post2 + " : " + v_addr1 + " : " + v_addr2 + " : " + v_gubun + " : " + v_money);
				try
				{
		            connMgr = new DBConnectionManager();
		            connMgr.setAutoCommit(false);
					
					sql = " Insert Into TZ_TAX ";
					sql +=" (subj, subjseq, year, userid, money, gubun, isuseYn, post1, post2, addr1, addr2, grcode, luserid, ldate) ";
					sql +="values (?, ?, ?, ?, ?, ?, 'N', ?, ?, ?, ?, ?,?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";
					
					pstmt = connMgr.prepareStatement(sql);
						
		            pstmt.setString(1, v_subj);
		            pstmt.setString(2, v_year);
		            pstmt.setString(3, v_subjseq);
		            pstmt.setString(4, v_userid);
		            pstmt.setString(5, v_money);
		            pstmt.setString(6, v_gubun);
		            pstmt.setString(7, v_post1);
		            pstmt.setString(8, v_post2);
		            pstmt.setString(9, v_addr1);
		            pstmt.setString(10, v_addr2);
		            pstmt.setString(11, v_grcode);
		            pstmt.setString(12, v_userid);
					
					isOk = pstmt.executeUpdate();
					
				}
			    catch(Exception ex) {
			        isOk = 0;
			        connMgr.rollback();
			        ErrorManager.getErrorStackTrace(ex, box, sql);
			        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
			    }
			    finally {
			        if (isOk > 0) {connMgr.commit();}
			        if(pstmt != null) { try { pstmt.close(); } catch (Exception e) {} }
			        if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
			    }
			    return isOk;
		}
		   
		   /**
			차수정보상세보기
			@param box      receive from the form object and session
			@return ProposeCourseData
			*/
			 public DataBox selectTax(RequestBox box) throws Exception {
			    DBConnectionManager connMgr = null;
			    DataBox dbox        = null;
			    ListSet ls1         = null;
			    String sql          = "";
			    String  v_userid   = box.getSession("userid");
			    String  v_subj	   = box.getString("p_subj");
			    String  v_subjseq  = box.getString("p_subjseq");
			    String  v_year	   = box.getString("p_year");
			    try {
			        connMgr = new DBConnectionManager();
			         sql = "  Select m.userid, m.name, m.post1, m.post2, m.resno, m.ADDR, m.ADDR2, m.membergubun, b.paymoney ";
					 sql += " From TZ_MEMBER m ";
					 sql +=	" 	left outer join TZ_BILLING B ON m.userid = b.userid ";
			         sql += "  where                    \n";
			         sql += "    m.userid = "+SQLString.Format(v_userid)+"  \n";
			         sql += "    and subj = "+SQLString.Format(v_subj)+"  \n";
			         sql += "    and subjseq = "+SQLString.Format(v_subjseq)+"  \n";
			         sql += "    and year = "+SQLString.Format(v_year)+"  \n";
					 
			        System.out.println(sql);
			
			        ls1 = connMgr.executeQuery(sql);
			
			            if (ls1.next()) {
			                dbox = ls1.getDataBox();
			            }
			    }
			    catch (Exception ex) {
			        ErrorManager.getErrorStackTrace(ex, box, sql);
			        throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
			    }
			    finally {
			        if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
			        if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
			    }
			    return dbox;
			}
		 
	
}