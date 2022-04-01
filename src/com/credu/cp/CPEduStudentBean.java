//**********************************************************
//  1. 제      목: 과정입과 인원조회 빈
//  2. 프로그램명: CPEduStudentBean.java
//  3. 개      요: 과정입과 인원조회관리 프로그램
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2004. 12. 23
//  7. 수      정: 이나연 05.11.17 _ Oracle -> MSSQL (OuterJoin) 수정
//**********************************************************

package com.credu.cp;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;

/**
*과정입과 인원조회
*<p>제목:CPEduStudentBean.java</p>
*<p>설명:과정입과 인원조회 빈</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
*@version 1.0
*/

public class CPEduStudentBean {
    private ConfigSet config;
    private int row;
	    	
    public CPEduStudentBean() {
        try{
            config = new ConfigSet();  
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }	
    }
  
    /**
    수강확정자 과정리스트
    @param box          receive from the form object and session
    @return ArrayList	외주업체 과정리스트
    */
    public ArrayList selectApprovalList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        //BulletinData data = null;
        DataBox dbox = null;
        
        String v_searchtext = box.getString("p_searchtext");
        String v_cp = box.getString("p_cp");			//외주업체
        String v_grcode = box.getString("s_grcode");	//교육주관
        String v_gyear = box.getString("s_gyear");		//교육년도
        String v_grseq = box.getString("s_grseq");		//교육차수
        //String v_select = box.getString("p_select");
        int v_pageno = box.getInt("p_pageno");
        
        String  s_grcomp   = box.getString("s_grcomp");
        String  v_grcomp   = "";
        if(!s_grcomp.equals("ALL")){
          v_grcomp = s_grcomp.substring(0,4);
        }

        try {
            connMgr = new DBConnectionManager();            
            
            list = new ArrayList();
        
			if(v_gyear.equals("")){
				v_gyear = FormatDate.getDate("yyyy");
				box.put("s_gyear",v_gyear);
			}
        
			sql  = "select  \n";
			sql += "   z.subj,      \n";
            sql += "   z.subjnm,  \n";
            sql += "   z.year,  \n";
            sql += "   z.subjseq,  \n";
            sql += "   z.subjseqgr, \n";
            sql += "   z.propstart,  \n";
            sql += "   z.propend, \n";
            sql += "   z.edustart,  \n";
            sql += "   z.eduend,  \n";
            sql += "   z.cpnm,  \n";
            sql += "   z.cpsubjseq, \n"; 
            sql += "   z.usercnt  \n"; 
			sql += " from ";
			sql += " ( ";
			sql += " select         \n";
            sql += "   b.subj,      \n";
            sql += "   b.subjnm,  \n";
            sql += "   b.year,  \n";
            sql += "   b.subjseq,  \n";
            sql += "   b.subjseqgr, \n";
            sql += "   b.propstart,  \n";
            sql += "   b.propend, \n";
            sql += "   b.edustart,  \n";
            sql += "   b.eduend,  \n";
            sql += "   c.cpnm,  \n";
            sql += "   b.cpsubjseq, \n"; 
            sql += "   (   \n";
            sql += "     select  \n";
            sql += "       count(x.userid)  \n";
            sql += "     from   \n";
            sql += "       tz_student x, tz_member y  \n";
            sql += "     where                        \n";
            sql += "       x.subj = b.subj              \n";
            sql += "       and x.subjseq = b.subjseq    \n";
            sql += "       and x.year = b.year          \n";
            sql += "       and x.userid = y.userid    \n";
            if(!s_grcomp.equals("ALL")){
				// 수정일 : 05.11.17 수정자 : 이나연 _ substr 수정
//            sql += "   and substr(y.comp, 1, 4) = '"+v_grcomp+"'  \n";
			  sql += "   and substring(y.comp, 1, 4) = '"+v_grcomp+"'  \n";
            }
            sql += "   ) as usercnt   \n";
            sql += " from  \n";
            sql += "   vz_scsubjseq b,  \n";
            sql += "   tz_cpinfo c \n";
			sql += "   where  \n";
			sql += "   1=1      \n";
            sql += "   and b.owner = c.cpseq   \n";
            
			//교육그룹검색(교육주관)
			if(!v_grcode.equals("")){
				sql += " and b.grcode = " + SQLString.Format(v_grcode);
            
				//과정명검색
				if ( !v_searchtext.equals("")) {	//    검색어가 있으면
	                sql += " and lower(b.subjnm) like " + SQLString.Format("%" + v_searchtext.toLowerCase() + "%");
	            }
	            
	            sql += " and b.gyear = " + SQLString.Format(v_gyear);
            
	            //교육차수 검색
	            if(!v_grseq.equals("ALL")){
	            	sql += " and b.grseq = " + SQLString.Format(v_grseq);
	            }
            
	            //외주업체 검색
	            if(!v_cp.equals("ALL")){
	                sql += " and b.owner = " + SQLString.Format(v_cp) ;
	            }
	        }
	        
	        sql += "   ) z  \n";
            sql += " where  \n";
            sql += " usercnt != 0 \n";
            
            System.out.println(sql);
            
            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            ls = new ListSet(pstmt);        //      ListSet (ResultSet) 객체생성
            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno);                    //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount();    //     전체 row 수를 반환한다
            
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
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }  


    /**
    수강확정자 명단
    @param box          receive from the form object and session
    @return ArrayList	수강확정자 리스트
    */	
    public ArrayList selectApprovalUserList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        
        String v_gyear = box.getString("p_gyear");		//교육년도
        String v_year = box.getString("p_year");		//과정년도
        String v_subj = box.getString("p_subj");		//과정코드        
        String v_subjseq = box.getString("p_subjseq");		//과정차수
        
        String  s_grcomp   = box.getString("s_grcomp");
        String  v_grcomp   = "";
        
        //System.out.println(s_grcomp);
        if(!s_grcomp.equals("ALL")){
          v_grcomp = s_grcomp.substring(0,4);
        }   

        try {
            connMgr = new DBConnectionManager();            
            
            list = new ArrayList();
            
            sql  = "select        \n";
            sql += " a.userid,    \n";
            sql += " a.name,      \n";
            sql += " a.email,     \n";
            sql += " a.handphone, \n"; 
            sql += " a.comptel,   \n";
            sql += " c.subjnm,    \n";
            sql += " c.eduurl,    \n"; 
            sql += " c.cpsubj,    \n";
            sql += " get_compnm(a.comp, 2, 2) compnm, \n";
            sql += " get_cpsubjeduurl(a.userid, b.subj, b.year, b.subjseq, 'ZZ') cpeduurl";
            sql += " from tz_member a, tz_student b, tz_subj c  \n";
            sql += " where a.userid = b.userid ";
            sql += " and b.year = " + SQLString.Format(v_year);
            sql += " and b.subj = " + SQLString.Format(v_subj);
            sql += " and b.subjseq = " + SQLString.Format(v_subjseq);
            sql += " and b.subj = c.subj ";
            
            if(!s_grcomp.equals("ALL")){
				// 수정일 : 05.11.17 수정자 : 이나연 _ substr 수정
//            	sql += "   and substr(a.comp, 1, 4) = '"+v_grcomp+"'  \n";
				sql += "   and substring(a.comp, 1, 4) = '"+v_grcomp+"'  \n";
            }
            sql += "order by a.name";
            
			ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                
                dbox.put("d_dispnum", new Integer(ls.getRowNum()));
                
                list.add(dbox);
            }
            
            String total_row_count = "" + (ls.getRowNum() - 1);    //     전체 row 수를 반환한다
            
            box.put("d_totalrow",total_row_count);
        
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }  

    /**
    수강취소자 과정리스트
    @param box          receive from the form object and session
    @return ArrayList	외주업체 과정리스트
    */
    public ArrayList selectCancelList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        //BulletinData data = null;
        DataBox dbox = null;
        
        String v_searchtext = box.getString("p_searchtext");
        String v_cp = box.getString("p_cp");			//외주업체
        String v_grcode = box.getString("s_grcode");	//교육주관
        String v_gyear = box.getString("s_gyear");		//교육년도
        String v_grseq = box.getString("s_grseq");		//교육차수
        int v_pageno = box.getInt("p_pageno");
        
        String  s_grcomp   = box.getString("s_grcomp");
        String  v_grcomp   = "";
        if(!s_grcomp.equals("ALL")){
          v_grcomp = s_grcomp.substring(0,4);
        }

        try {
            connMgr = new DBConnectionManager();            
            
            list = new ArrayList();

			if(v_gyear.equals("")){
				v_gyear = FormatDate.getDate("yyyy");
				box.put("s_gyear",v_gyear);
			}
            sql += " select         \n";
            sql += "   subj,      \n";
            sql += "   subjnm,  \n";
            sql += "   year,  \n";
            sql += "   subjseq,  \n";
            sql += "   subjseqgr, \n";
            sql += "   propstart,  \n";
            sql += "   propend, \n";
            sql += "   edustart,  \n";
            sql += "   eduend,  \n";
            sql += "   cpnm,  \n";
            sql += "   cpsubjseq, \n"; 
            sql += "   usercnt  \n"; 
            sql += " from           \n";
            sql += " (              \n";
			sql += " select         \n";
            sql += "   b.subj,      \n";
            sql += "   b.subjnm,  \n";
            sql += "   b.year,  \n";
            sql += "   b.subjseq,  \n";
            sql += "   b.subjseqgr, \n";
            sql += "   b.propstart,  \n";
            sql += "   b.propend, \n";
            sql += "   b.edustart,  \n";
            sql += "   b.eduend,  \n";
            sql += "   c.cpnm,  \n";
            sql += "   b.cpsubjseq, \n"; 
            sql += "   (   \n";
            sql += "     select  \n";
            sql += "       count(x.userid)  \n";
            sql += "     from   \n";
            sql += "       tz_cancel x, tz_member y  \n";
            sql += "     where                        \n";
            sql += "       x.subj = b.subj              \n";
            sql += "       and x.subjseq = b.subjseq    \n";
            sql += "       and x.year = b.year          \n";
            sql += "       and x.userid = y.userid    \n";
            if(!s_grcomp.equals("ALL")){
				// 수정일 : 05.11.17 수정자 : 이나연 _ substr 수정
//            sql += "   and substr(y.comp, 1, 4) = '"+v_grcomp+"'  \n";
			  sql += "   and substring(y.comp, 1, 4) = '"+v_grcomp+"'  \n";
            }
            sql += "   ) as usercnt   \n";
            sql += " from  \n";
            sql += "   vz_scsubjseq b,  \n";
            sql += "   tz_cpinfo c \n";
			sql += "   where  \n";
			sql += "   1=1      \n";
            sql += "   and b.owner = c.cpseq   \n";
			
			//교육그룹검색(교육주관)
			if(!v_grcode.equals("")){
				sql += " and b.grcode = " + SQLString.Format(v_grcode);

				//과정명검색
				if ( !v_searchtext.equals("")) {	//    검색어가 있으면
	                sql += " and b.subjnm like " + SQLString.Format("%" + v_searchtext + "%");
	            }
	            
	            sql += " and b.gyear = " + SQLString.Format(v_gyear);

	            //교육차수 검색
	            if(!v_grseq.equals("ALL")){
	            	sql += " and b.grseq = " + SQLString.Format(v_grseq);
	            }

	            //외주업체 검색
	            if(!v_cp.equals("ALL")){
	                sql += " and b.owner = " + SQLString.Format(v_cp) ;
	            }
	        }
            sql += " )  \n";
            sql += " where  \n";
            sql += " usercnt != 0 \n";

			
            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            ls = new ListSet(pstmt);        //      ListSet (ResultSet) 객체생성
            
            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno);                    //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount();    //     전체 row 수를 반환한다

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
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }  

    /**
    수강취소자 명단
    @param box          receive from the form object and session
    @return ArrayList	수강취소자 리스트
    */
    public ArrayList selectCancelUserList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        
        String v_gyear = box.getString("p_gyear");		//교육년도
        String v_year = box.getString("p_year");		//과정년도
        String v_subj = box.getString("p_subj");		//과정코드
        String v_subjseq = box.getString("p_subjseq");		//과정차수
        
        String  s_grcomp   = box.getString("s_grcomp");
        String  v_grcomp   = "";
        if(!s_grcomp.equals("ALL")){
          v_grcomp = s_grcomp.substring(0,4);
        }

        try {
            connMgr = new DBConnectionManager();            
            
            list = new ArrayList();
            
            sql  = "select a.userid, a.name, a.email, a.handphone, a.comptel, c.subjnm, c.eduurl, c.cpsubj, b.canceldate, b.reason ";
            sql += " from tz_member a, tz_cancel b, tz_subj c ";
            sql += " where a.userid = b.userid ";
            sql += " and b.year = " + SQLString.Format(v_year);
            sql += " and b.subj = " + SQLString.Format(v_subj);
            sql += " and b.subjseq = " + SQLString.Format(v_subjseq);
            sql += " and b.subj = c.subj ";
            if(!s_grcomp.equals("ALL")){
				// 수정일 : 05.11.17 수정자 : 이나연 _ substr 수정
//            	sql += "   and substr(a.comp, 1, 4) = '"+v_grcomp+"'  \n";
				sql += "   and substring(a.comp, 1, 4) = '"+v_grcomp+"'  \n";
			}
            sql += " order by a.name ";

			ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                
                dbox.put("d_dispnum", new Integer(ls.getRowNum()));
                
                list.add(dbox);
            }
            
            String total_row_count = "" + (ls.getRowNum() - 1);    //     전체 row 수를 반환한다
            
            box.put("d_totalrow",total_row_count);
        
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }  
    
    /**
    수강신청자 과정리스트
    @param box          receive from the form object and session
    @return ArrayList	외주업체과정 리스트
    */	
    public ArrayList selectProposeList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        //BulletinData data = null;
        DataBox dbox = null;
        
        String v_searchtext = box.getString("p_searchtext");
        String v_cp = box.getString("p_cp");			//외주업체
        String v_grcode = box.getString("s_grcode");	//교육주관
        String v_gyear = box.getString("s_gyear");		//교육년도
        //String v_select = box.getString("p_select");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();            
            
            list = new ArrayList();

			if(v_gyear.equals("")){
				v_gyear = FormatDate.getDate("yyyy");
				box.put("s_gyear",v_gyear);
			}
 
			sql = "select a.subj, a.subjnm, b.year, b.subjseq, b.propstart, b.propend, b.edustart, b.eduend, c.cpnm, b.cpsubjseq, count(d.userid) as usercnt ";
			sql += " from tz_subj a, tz_subjseq b, tz_cpinfo c, tz_propose d ";
			sql += " where a.subj = b.subj and ";
			// 수정일 : 05.11.17 수정자 : 이나연 _(+)  수정
//			sql += " b.subj = d.subj(+) and ";
//			sql += " b.year = d.year(+) and ";
//			sql += " b.subjseq = d.subjseq(+) and ";
			sql += " b.subj     =  d.subj(+) and ";
			sql += " b.year     =  d.year(+) and ";
			sql += " b.subjseq  =  d.subjseq(+) and ";
			
			//교육그룹검색(교육주관)
			if(!v_grcode.equals("")){
				sql += " b.grcode = " + SQLString.Format(v_grcode);
				//if(!v_searchtext.equals("") || !v_gyear.equals("")){
					sql += " and ";
				//}				

				//과정명검색
				if ( !v_searchtext.equals("")) {	//    검색어가 있으면
	                //v_pageno = 1;	//      검색할 경우 첫번째 페이지가 로딩된다
	                
	                sql += " a.subjnm like " + SQLString.Format("%" + v_searchtext + "%");
	                if(!v_gyear.equals("")){
	                	sql += " and ";
	                }
	            }
	            
	            //교육년도 검색
	            if(!v_gyear.equals("") && !v_gyear.equals("ALL")){
	            	sql += " b.gyear = " + SQLString.Format(v_gyear);
	            	sql += " and ";
	            }
	            else if(v_gyear.equals("")){
	            	sql += " b.gyear = " + SQLString.Format(FormatDate.getDate("yyyy"));
	            	sql += " and ";
	            }
	            
	            //외주업체 검색
	            if(!v_cp.equals("") && !v_cp.equals("ALL")){
	                sql += " a.owner = " + SQLString.Format(v_cp);
	                sql += " and a.owner = c.cpseq ";
	            }
	            else if(!v_cp.equals("") || v_cp.equals("ALL")){
	            	sql += " a.owner = c.cpseq ";
	            }
	            else{
	            	sql += " a.owner = c.cpseq ";
	            }
	        }
	        else{
	        	//조회가 되지 않도록함.
	        	sql += " b.grcode = 'zzzzzz'";
	        }
	        
            sql += " group by a.subj, a.subjnm, b.year, b.subjseq, b.propstart, b.propend, b.edustart, b.eduend, b.cpsubjseq, c.cpnm ";


			
            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            
            ls = new ListSet(pstmt);        //      ListSet (ResultSet) 객체생성
            
            ls.setPageSize(row);             //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno);                    //     현재페이지번호를 세팅한다.
            int total_page_count = ls.getTotalPage();       //     전체 페이지 수를 반환한다
            int total_row_count = ls.getTotalCount();    //     전체 row 수를 반환한다

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
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }  

    /**
    수강신청자 명단
    @param box          receive from the form object and session
    @return ArrayList	수강신청자 리스트
    */
    public ArrayList selectProposeUserList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        
        String v_gyear = box.getString("p_gyear");		//교육년도
        String v_year = box.getString("p_year");		//과정년도
        String v_subj = box.getString("p_subj");		//과정코드
        String v_subjseq = box.getString("p_subjseq");		//과정차수
        

        try {
            connMgr = new DBConnectionManager();            
            
            list = new ArrayList();
            
            sql  = "select a.userid, a.name, a.email, a.handphone, c.subjnm, c.eduurl, c.cpsubj ";
            sql += " from tz_member a, tz_propose b, tz_subj c ";
            sql += " where a.userid = b.userid ";
            sql += " and b.year = " + SQLString.Format(v_year);
            sql += " and b.subj = " + SQLString.Format(v_subj);
            sql += " and b.subjseq = " + SQLString.Format(v_subjseq);
            sql += " and b.subj = c.subj ";

			ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                
                dbox.put("d_dispnum", new Integer(ls.getRowNum()));
                
                list.add(dbox);
            }

            String total_row_count = "" + (ls.getRowNum() - 1);    //     전체 row 수를 반환한다
            
            box.put("d_totalrow",total_row_count);
            
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }  


    /**
    수강확정/취소/신청 명단 엑셀 다운로드
    @param box          receive from the form object and session
    @return ArrayList	수강확정/취소/신청자 리스트
    */
    public ArrayList selectStudentExcel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        String downgubun;	//엑셀다운구분(1:확정자,2:취소자,3:신청자)
        
        downgubun = box.getString("p_downgubun");
        
        String v_syear = box.getString("p_syear");		//수강시작년도
        String v_smon = CodeConfigBean.addZero(StringManager.toInt(box.getString("p_smon")), 2);		//수강시작월
        String v_sday = CodeConfigBean.addZero(StringManager.toInt(box.getString("p_sday")), 2);		//수강시작일
        
        String v_start = v_syear + v_smon + v_sday;
        
        String v_eyear = box.getString("p_eyear");		//수강종료년도
        String v_emon = CodeConfigBean.addZero(StringManager.toInt(box.getString("p_emon")), 2);		//수강종료월
        String v_eday = CodeConfigBean.addZero(StringManager.toInt(box.getString("p_eday")), 2);		//수강종료일
        
        String v_end = v_eyear + v_emon + v_eday;
        
        String v_grcode = box.getString("s_grcode");
        
        String  s_grcomp   = box.getString("s_grcomp");
        String  v_grcomp   = "";
        if(!s_grcomp.equals("ALL")){
          v_grcomp = s_grcomp.substring(0,4);
        }
        
        
        String v_cp = box.getString("p_cp");

        if(box.getSession("gadmin").equals("S1")){
        	//외주업체 담당자라면 해당 회사정보코드를 알아낸다.
        	v_cp = this.selectCPseq(box.getSession("userid"));
        	//v_cp = CodeConfigBean.addZero(StringManager.toInt(v_seq), 5);
    	}   
        
        try {
            connMgr = new DBConnectionManager();            
            
            list = new ArrayList();
            
            if(downgubun.equals("1")){
            	//확정자 명단 다운로드
 
            	sql = "select a.userid, a.name, get_jikwinm(a.jikwi, a.comp) as jikwinm, get_deptnm(a.deptnam, a.userid) as deptnam, a.resno, a.email, a.comptel, a.handphone, ";
            	sql += " get_compnm(a.comp, 2,2) companynm , ";
            	sql += " b.appdate as adate, c.subjnm, c.subjseq, c.cpsubjseq, c.edustart, c.eduend, d.cpnm, e.grcodenm ";
            	sql += " from tz_member a, tz_propose b, tz_subjseq c, tz_cpinfo d, tz_grcode e, tz_subj f ";
            	sql += " where a.userid = b.userid ";
            	sql += " and b.subj = c.subj ";
            	sql += " and b.subjseq = c.subjseq ";
            	sql += " and b.year = c.year ";
            	sql += " and c.subj = f.subj ";
            	sql += " and f.owner = d.cpseq ";
            	sql += " and c.grcode = e.grcode ";
            	//sql += " and (";
            	//sql += " (substr(c.edustart,1,8) <= " + SQLString.Format(v_start);
            	//sql += " and substr(c.eduend,1,8) >= " + SQLString.Format(v_start);
            	//sql += " ) or (";
            	//sql += " substr(c.edustart,1,8) <= " + SQLString.Format(v_end);
            	//sql += " and substr(c.eduend,1,8) >= " + SQLString.Format(v_end);
            	//sql += " ) )";
	            //sql += " and b.isproposeapproval = ANY('Y','L') ";
	            
	            if(!s_grcomp.equals("ALL")){
					// 수정일 : 05.11.17 수정자 : 이나연 _ substr 수정
//                sql += "   and substr(a.comp, 1, 4) = '"+v_grcomp+"'  \n";
				  sql += "   and substring(a.comp, 1, 4) = '"+v_grcomp+"'  \n";
                }
//				sql += " and ( (substr(c.edustart,1,8) >= "+SQLString.Format(v_start)+" and substr(c.eduend,1,8) <= "+SQLString.Format(v_end)+" ) )";
	            sql += " and ( (SUBSTR(c.edustart,1,8) >= "+SQLString.Format(v_start)+" and substring(c.eduend,1,8) <= "+SQLString.Format(v_end)+" ) )";
	            sql += " and b.chkfinal = 'Y' ";
	            sql += " and (NVL(b.cancelkind,'zz') = 'zz' or length(ltrim(b.cancelkind)) = 0) ";
            	
            }
            else if(downgubun.equals("2")){
            	//취소자 명단 다운로드

            	sql = "select a.userid, a.name, get_jikwinm(a.jikwi, a.comp) as jikwinm, get_deptnm(a.deptnam, a.userid) as deptnam, a.resno, a.email, a.comptel, a.handphone, ";
            	sql += " b.canceldate as adate, c.subjnm, c.subjseq, c.cpsubjseq, c.edustart, c.eduend, d.cpnm, e.grcodenm ";
            	sql += " from tz_member a, tz_cancel b, tz_subjseq c, tz_cpinfo d, tz_grcode e, tz_subj f ";
            	sql += " where a.userid = b.userid ";
            	sql += " and b.subj = c.subj ";
            	sql += " and b.subjseq = c.subjseq ";
            	sql += " and b.year = c.year ";
            	sql += " and c.subj = f.subj ";
            	sql += " and f.owner = d.cpseq ";
            	sql += " and c.grcode = e.grcode ";            	
            	if(!s_grcomp.equals("ALL")){
					// 수정일 : 05.11.17 수정자 : 이나연 _substr 수정
//                sql += "   and substr(a.comp, 1, 4) = '"+v_grcomp+"'  \n";
				  sql += "   and substring(a.comp, 1, 4) = '"+v_grcomp+"'  \n";
                }
            	//sql += " and (";
            	//sql += " (substr(c.edustart,1,8) <= " + SQLString.Format(v_start);
            	//sql += " and substr(c.eduend,1,8) >= " + SQLString.Format(v_start);
            	//sql += " ) or (";
            	//sql += " substr(c.edustart,1,8) <= " + SQLString.Format(v_end);
            	//sql += " and substr(c.eduend,1,8) >= " + SQLString.Format(v_end);
            	//sql += " ) )";
				
//            	sql += " and ( (substr(c.edustart,1,8) >= "+SQLString.Format(v_start)+" and substr(c.eduend,1,8) <= "+SQLString.Format(v_end)+" ) )";
				sql += " and ( (SUBSTR(c.edustart,1,8) >= "+SQLString.Format(v_start)+" and substring(c.eduend,1,8) <= "+SQLString.Format(v_end)+" ) )";
            }

            if(!v_grcode.equals("----")){
            	sql += " and e.grcode = " + SQLString.Format(v_grcode);
            }
            
            if(!v_cp.equals("ALL")){
            	sql += " and d.cpseq = " + SQLString.Format(v_cp);
            }
            
            System.out.println(sql);
            
            ls = connMgr.executeQuery(sql);
			
            while (ls.next()) {
                dbox = ls.getDataBox();
                
                dbox.put("d_dispnum", new Integer(ls.getRowNum()));
                
                list.add(dbox);
            }
            
            String total_row_count = "" + (ls.getRowNum() - 1);    //     전체 row 수를 반환한다
            
            box.put("total_row_count",total_row_count);
            
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }  

    /**
    과정별 입과명단 엑셀 다운로드
    @param box          receive from the form object and session
    @return ArrayList	과정별 입과명단 리스트
    */
    public ArrayList selectExcel(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;        
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        String downgubun;	//엑셀다운구분(1:확정자,2:취소자,3:신청자)
        
        downgubun = box.getString("p_downgubun");
        
        String v_gyear = box.getString("p_gyear");		//교육년도
        String v_year = box.getString("p_year");		//과정년도
        String v_subj = box.getString("p_subj");		//과정코드        
        String v_subjseq = box.getString("p_subjseq");		//과정차수
        
        String  s_grcomp   = box.getString("s_grcomp");
        String  v_grcomp   = "";
        
        //System.out.println(s_grcomp);
        if(!s_grcomp.equals("ALL")){
          v_grcomp = s_grcomp.substring(0,4);
        }
        

        try {
            connMgr = new DBConnectionManager();            
            
            list = new ArrayList();
            
            if(downgubun.equals("1")){
            	//확정자 명단 다운로드
	            
                sql  = "select        \n";
                sql += " a.userid,    \n";
                sql += " a.name,      \n";
                sql += " a.email,     \n";
                sql += " a.handphone, \n"; 
                sql += " a.comptel,   \n";
                sql += " c.subjnm,    \n";
                sql += " c.eduurl,    \n"; 
                sql += " c.cpsubj,    \n";
                sql += " a.resno,    \n";
                sql += " get_compnm(a.comp, 2, 2) compnm \n";
                sql += " from tz_member a, tz_student b, tz_subj c  \n";
                sql += " where a.userid = b.userid ";
                sql += " and b.year = " + SQLString.Format(v_year);
                sql += " and b.subj = " + SQLString.Format(v_subj);
                sql += " and b.subjseq = " + SQLString.Format(v_subjseq);
                sql += " and b.subj = c.subj ";
                
                if(!s_grcomp.equals("ALL")){
					// 수정일 : 05.11.17 수정자 : 이나연 _ substr 수정
//                	sql += "   and substr(a.comp, 1, 4) = '"+v_grcomp+"'  \n";
					sql += "   and substring(a.comp, 1, 4) = '"+v_grcomp+"'  \n";
                }
                sql += "order by a.name";
            	
            }
            else if(downgubun.equals("2")){
            	//취소자 명단 다운로드
	            sql  = "select a.userid, a.name, a.email, a.handphone, a.comptel, c.subjnm, c.eduurl, c.cpsubj, b.canceldate, b.reason, a.resno ";
                sql += " from tz_member a, tz_cancel b, tz_subj c ";
                sql += " where a.userid = b.userid ";
                sql += " and b.year = " + SQLString.Format(v_year);
                sql += " and b.subj = " + SQLString.Format(v_subj);
                sql += " and b.subjseq = " + SQLString.Format(v_subjseq);
                sql += " and b.subj = c.subj ";
                if(!s_grcomp.equals("ALL")){
					// 수정일 : 05.11.17 수정자 : 이나연 _substr 수정
//                	sql += "   and substr(a.comp, 1, 4) = '"+v_grcomp+"'  \n";
					sql += "   and substring(a.comp, 1, 4) = '"+v_grcomp+"'  \n";
                }
                sql += " order by a.name ";
            	
            }            
            else if(downgubun.equals("3")){
            	//신청자 명단 다운로드

	            sql  = "select a.userid, a.name, a.resno, a.jikwinm, a.email, a.comptel, a.handphone, b.appdate as adate, c.subjnm, c.eduurl, c.cpsubj ";
	            sql += " from tz_member a, tz_propose b, tz_subj c ";
	            sql += " where a.userid = b.userid ";
	            sql += " and b.year = " + SQLString.Format(v_year);
	            sql += " and b.subj = " + SQLString.Format(v_subj);
	            sql += " and b.subjseq = " + SQLString.Format(v_subjseq);
	            sql += " and b.subj = c.subj ";
            }
            
            System.out.println(sql);
            
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                dbox = ls.getDataBox();
            
                dbox.put("d_dispnum", new Integer(ls.getRowNum()));
                
                list.add(dbox);
            }
            
            String total_row_count = "" + (ls.getRowNum() - 1);    //     전체 row 수를 반환한다
            
            box.put("total_row_count",total_row_count);
            
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(pstmt != null) { try { pstmt.close(); } catch (Exception e1) {} }        //      꼭 닫아준다
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }  
    

    /**
    외주업체회사코드 조회
    @param p_userid	외주업체담당자 아이디
    @return String	외주업체코드
    */
   public String selectCPseq(String p_userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_cpseq = "";
        
        try {
            connMgr = new DBConnectionManager();

			sql = "select cpseq, cpnm ";
			sql += " from tz_cpinfo ";
			sql += " where userid = " + SQLString.Format(p_userid);
            
            ls = connMgr.executeQuery(sql); 
        
            while(ls.next()) {
				//dbox = ls.getDataBox();
				v_cpseq = ls.getString("cpseq");
            }  
        }
        catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_cpseq;
    }   

}
