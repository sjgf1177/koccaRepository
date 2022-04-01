//**********************************************************
//  1. 제      목: PROJECT ADMIN BEAN
//  2. 프로그램명: BPProjectAdminBean.java
//  3. 개      요: 리포트 관리자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//**********************************************************
package com.credu.beta;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.beta.*;
import com.credu.library.*;
import com.credu.system.*;

public class BPProjectAdminBean {

    public BPProjectAdminBean() {}

    /**
    리포트출제관리 리스트
    @param box      receive from the form object and session
    @return ArrayList   
    */
     public ArrayList selectProjectQuestionsAList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls			= null;       
        ListSet ls1			= null;       
        ArrayList list1     = null;
        String sql			= "";
        String sql1         = "";
        BPProjectData data1 = null;
        int     l           = 0;

        String s_gadmin = box.getSession("gadmin");
        String s_userid = box.getSession("userid");
        String v_cp = box.getString("p_cp");
        String v_year = box.getString("p_year");
        String v_searchtext = box.getString("p_searchtext");
        String ss_action = box.getString("s_action");
        
        String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
        String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서

        try {

            if(ss_action.equals("go")){ 
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
            
				//베타업체 리스트
				if(s_gadmin.equals("S1") || s_gadmin.equals("T1") || s_gadmin.equals("M1") ){
					//베타업체 담당자일경우(해당업체의 정보만보여줌)
					sql  = " select  comp, compnm from tz_comp            ";
					sql += "  where comp in (select comp from tz_compman  ";
					sql += "                  where gadmin = 'T1'         ";
					sql += "                    and userid = " + SQLString.Format(s_userid) + " )";			         
								
					ls = connMgr.executeQuery(sql);
					
					if(ls.next())
						v_cp = ls.getString("comp");
					else
						v_cp = "";
				}

                sql1 = "select 	a.subj,";
                sql1+= " 		a.subjnm,";
				sql1+= "		substring(a.indate,1,4) year,";
                sql1+= " 		'0000' subjseq,";
                sql1+= " 		a.isonoff,";
                sql1+= "		(select count(distinct projseq) from tz_projord where subj = a.subj and subjseq = '0000') projseqcnt, ";
                sql1+= "		(select count(ordseq) from tz_projord where subj=a.subj and subjseq = '0000') ordseqcnt ";
                sql1+= "from 	tz_subj a, tz_comp b where a.producer = b.comp ";

				//과정명검색
				if ( !v_searchtext.equals("")) {	//    검색어가 있으면
					
					//v_pageno = 1;	//      검색할 경우 첫번째 페이지가 로딩된다
					sql1 += " and a.subjnm like " + StringManager.makeSQL("%" + v_searchtext + "%");
					
				}
				
				//과정개설년도 검색
				if(!v_year.equals("") && !v_year.equals("ALL")){
					sql1 += " and substring(a.indate,1,4) = " + StringManager.makeSQL(v_year);
				}
				
				else if(v_year.equals("")){
					sql1 += " and substring(a.indate,1,4) = " + StringManager.makeSQL(FormatDate.getDate("yyyy"));
				}
				
				//베타업체 검색
				if(!v_cp.equals("") && !v_cp.equals("ALL")){
					sql1 += " and a.producer = " + StringManager.makeSQL(v_cp);
				}    			

    			if(v_orderColumn.equals("")) {
                	sql1+= " order by a.subj ";
				} else {
				    sql1+= " order by " + v_orderColumn + v_orderType;
				}
    			
                ls1 = connMgr.executeQuery(sql1);
                
				while (ls1.next()) {
					data1=new BPProjectData();
					data1.setSubj(ls1.getString("subj"));
					data1.setSubjnm(ls1.getString("subjnm"));
					data1.setYear(ls1.getString("year"));
					data1.setSubjseq(ls1.getString("subjseq"));
					data1.setIsonoff(ls1.getString("isonoff"));
					data1.setProjseqcnt(ls1.getInt("projseqcnt"));
					data1.setOrdseqcnt(ls1.getInt("ordseqcnt"));
						
					list1.add(data1);
				}
			}
        }            
        catch (Exception ex) {
        	ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }

    /**
    리포트 출제리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectProjectQuestionsList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls          = null;
        ArrayList list      = null;
        String sql          = "";
        BPProjectData data  = null;
        String  v_subj      = box.getString("p_subj");          //과정
        String  v_year      = box.getStringDefault("p_year",box.getString("p_year1"));          //년도
        String  v_subjseq   = box.getString("p_subjseq");       //과정차수

		//v_year.equals("")
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            //sql+= "(select count(*) from TZ_PROJGRP where subj=A.subj and year=A.year "; 11/26 tz_projgrp 용도 알아낼 것
            // sql+= "and subjseq=A.subjseq and ordseq=A.ordseq) as grcnt,A.groupcnt ";
            sql = "select 	A.projseq, ";
            sql+= "			A.ordseq,";
            sql+= "			NVL(A.lesson,'') lesson,";
            sql+= "			NVL((select sdesc from tz_subjlesson where subj = a.subj and lesson = a.lesson),'') lessonnm,";
            sql+= "			A.reptype,";
            sql+= "			A.isopen,";
            sql+= "			A.isopenscore,";
            sql+= "			A.title,";
            sql+= "			A.score,";
            sql+= "			NVL(A.expiredate,'') expiredate, ";
            sql+= "			(select count(*) from TZ_STUDENT where subj=A.subj and year=A.year and subjseq=A.subjseq) as tocnt, ";
		    sql+= " 		99 as grcnt,";
		    sql+= "			A.groupcnt, ";
		    sql+= "			isusedcopy, ";
			sql+= " 		(select count(*) from TZ_PROJORD where subj=A.subj and year=A.year and subjseq=A.subjseq and projseq=a.projseq) as rowspan, ";
			sql+= " 		(select min(ordseq) from tz_projord where subj=a.subj and year=a.year and subjseq=a.subjseq and projseq=a.projseq ) rowspanseq, ";
			//2005.11.15_하경태 : Oracle -> Mssql
			//sql+= "	        A.upfile, A.upfile2, decode(A.useyn, 'Y', '사용', '미사용')  useyn  ";
			sql+= "	        A.upfile, A.upfile2, case A.useyn When 'Y' Then  '사용' Else '미사용' End as  useyn  ";
            sql+= "from 	TZ_PROJORD A ";
            sql+= "where 	A.subj='"+v_subj+"' and A.year='"+v_year+"' and A.subjseq='"+v_subjseq+"' ";
            sql += "order by a.projseq,A.ordseq";

             ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    data=new BPProjectData();
					data.setProjseq(ls.getInt("projseq"));
                    data.setOrdseq(ls.getInt("ordseq"));
                    data.setLesson(ls.getString("lesson"));
                    data.setLessonnm(ls.getString("lessonnm"));
                    data.setReptype(ls.getString("reptype"));
                    data.setIsopen(ls.getString("isopen"));
                    data.setIsopenscore(ls.getString("isopenscore"));
                    data.setTitle(ls.getString("title"));
                    data.setScore(ls.getInt("score"));
                    data.setTocnt(ls.getString("tocnt"));     
                    data.setGrcnt(ls.getString("grcnt")); 
					data.setExpiredate(ls.getString("expiredate")); 
                    data.setGroupcnt(ls.getString("groupcnt")); 
					data.setRowspan(ls.getInt("rowspan")); 
					data.setIsusedcopy(ls.getString("isusedcopy")); 
					data.setRowspanseq(ls.getInt("rowspanseq"));
					data.setUpfile(ls.getString("upfile")); 
					data.setUpfile2(ls.getString("upfile2")); 
					data.setUseyn(ls.getString("useyn")); 										
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
    리포트 등록
    @param box      receive from the form object and session
    @return int
    */
     public int insertProjectQuestions(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;     
        ListSet ls                  = null;                   
        PreparedStatement pstmt2    = null;        
        String sql1                 = "";
        String sql2                 = "";        
        int isOk                    = 0;
        String v_user_id            = box.getSession("userid");        
        String v_subj               = box.getString("p_subj");          //과정
        String v_year               = box.getString("p_year");          //년도
        String v_subjseq            = box.getString("p_subjseq");       //과정차수
		int    v_projseq            = box.getInt("p_projseq");       //리포트차수
        String v_lesson             = box.getString("p_lesson");         //일차
        String v_reptype            = box.getString("p_reptype");
        String v_title              = box.getString("p_title");
        String v_contents           = box.getString("p_contents");
        String v_expiredate         = box.getString("p_expiredate");
        String v_isopen             = box.getString("p_isopen");
        String v_isopenscore        = box.getString("p_isopenscore");
        String v_score              = box.getString("p_score");
        String v_realFileName1      = box.getRealFileName("p_file1");
        String v_newFileName1       = box.getNewFileName("p_file1");
        String v_realFileName2      = box.getRealFileName("p_file2");
        String v_newFileName2       = box.getNewFileName("p_file2");
        int    v_groupcnt           = box.getInt("p_groupcnt");
        String v_ansyn              = box.getString("ansyn");     // 답안제출옵션
        String v_useyn              = box.getString("useyn");     // 사용여부          
        int    v_max                = 0;
        int    v_ordseq             = 0;
                
        try{
            connMgr = new DBConnectionManager();
            sql1  = "select max(ordseq) from TZ_PROJORD ";
            sql1 += "where subj='"+v_subj+"' and year='"+v_year+"' and subjseq='"+v_subjseq+"'";            
            ls = connMgr.executeQuery(sql1);
            
            if(ls.next()){
                v_max = ls.getInt(1);
                if(v_max > 0){ v_ordseq = v_max + 1; }
                else          { v_ordseq = 1;         }
            }

            sql2  = "insert into TZ_PROJORD(subj,year,subjseq,ordseq,projseq,lesson,reptype,";
            sql2 += "expiredate,title,contents,score,upfile,upfile2,realfile,realfile2,luserid,groupcnt,ldate,ansyn,useyn) ";
            sql2 += "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, to_char(sysdate,'YYYYMMDDHH24MISS'),?,?)";
            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1,v_subj);
            pstmt2.setString(2,v_year);
            pstmt2.setString(3,v_subjseq);
            pstmt2.setInt(4,v_ordseq);
			pstmt2.setInt(5,v_projseq);
            pstmt2.setString(6,v_lesson);
            pstmt2.setString(7,v_reptype);
            pstmt2.setString(8,v_expiredate);
            pstmt2.setString(9,v_title);
            pstmt2.setString(10,v_contents);
            pstmt2.setString(11,v_score);
            pstmt2.setString(12,v_newFileName1);
            pstmt2.setString(13,v_newFileName2);
			pstmt2.setString(14,v_realFileName1);
            pstmt2.setString(15,v_realFileName2);
            pstmt2.setString(16,v_user_id);
            pstmt2.setInt(17,v_groupcnt);
            pstmt2.setString(18,v_ansyn);
            pstmt2.setString(19,v_useyn);            
            isOk = pstmt2.executeUpdate();
           
        }
        catch(Exception ex) {                        
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null)      { try { ls.close(); } catch (Exception e) {} }
            if(pstmt2 != null)  { try { pstmt2.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
    
	
	
    /**
    리포트 수정 페이지
    @param box      receive from the form object and session
    @return BPProjectData   
    */
     public BPProjectData updateProjectQuestionsPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls          = null;
        String sql          = "";
        BPProjectData data   = null;
        String  v_subj      = box.getString("p_subj");          //과정
        String  v_year      = box.getString("p_year");          //년도
        String  v_subjseq   = box.getString("p_subjseq");       //과정차수
        String  v_lesson    = box.getString("p_lesson");         //일차
        int     v_ordseq    = box.getInt("p_ordseq");        
        
        try {
            connMgr = new DBConnectionManager();

            //select reptype,expiredate,title,contents,score,groupcnt,upfile,upfile2
            sql = "select projseq,reptype,NVL(expiredate,'') expiredate, ";
            sql+= "title,contents,score,groupcnt,upfile,upfile2,realfile,realfile2,ansyn, useyn ";            
            sql+= "from TZ_PROJORD ";
            sql+= "where subj='"+v_subj+"' and year='"+v_year+"' and subjseq='"+v_subjseq+"' ";
            sql+= "and ordseq='"+v_ordseq+"' and lesson='"+v_lesson+"'";

             ls = connMgr.executeQuery(sql);

                if (ls.next()) {
                    data=new BPProjectData();
					data.setProjseq(ls.getInt("projseq"));
					
                    data.setReptype(ls.getString("reptype"));
                    data.setExpiredate(ls.getString("expiredate"));
                    data.setTitle(ls.getString("title"));
                    data.setContents(ls.getString("contents"));
                    data.setScore(ls.getInt("score"));
                    data.setGroupcnt(ls.getString("groupcnt"));
                    data.setUpfile(ls.getString("upfile"));
                    data.setUpfile2(ls.getString("upfile2"));
					data.setRealfile(ls.getString("realfile"));
                    data.setRealfile2(ls.getString("realfile2"));
                    data.setAnsyn(ls.getString("ansyn"));
                    data.setUseyn(ls.getString("useyn"));
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
        return data;        
    }

    /**
    리포트 수정
    @param box      receive from the form object and session
    @return int
    */
     public int updateProjectQuestions(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt     = null;
        String sql                  = "";
        int isOk                    = 0;
        String v_user_id            = box.getSession("userid");
        String v_reptype            = box.getString("p_reptype");
        String v_title              = box.getString("p_title");
        String v_contents           = box.getString("p_contents");
        String v_expiredate         = box.getString("p_expiredate");
        String v_isopen             = box.getString("p_isopen");
        String v_isopenscore        = box.getString("p_isopenscore");
        String v_realFileName1      = box.getRealFileName("p_file1");
        String v_newFileName1       = box.getNewFileName("p_file1");
        String v_realFileName2      = box.getRealFileName("p_file2");
        String v_newFileName2       = box.getNewFileName("p_file2");
        String v_upfile             = box.getString("p_upfile");
        String v_upfile2            = box.getString("p_upfile2");
		String v_realfile           = box.getString("p_realfile");
        String v_realfile2          = box.getString("p_realfile2");		
		String v_check1				= box.getString("p_check1");	//첨부파일1 이전파일 삭제
		String v_check2				= box.getString("p_check2");	//첨부파일2 아전파일 삭제
		
        String v_subj               = box.getString("p_subj");          //과정
        String v_year               = box.getString("p_year");          //년도
        String v_subjseq            = box.getString("p_subjseq");       //과정차수 
        String v_lesson              = box.getString("p_lesson");         //일차      
		
		String v_upfilesize 		= "";
		String v_upfilesize2		= "";
        String v_ansyn              = box.getString("ansyn");     // 답안제출옵션
        String v_useyn              = box.getString("useyn");     // 사용여부   
        		                         
        int    v_score              = box.getInt("p_score");        
        int    v_groupcnt           = box.getInt("p_groupcnt");
        int    v_ordseq             = box.getInt("p_ordseq"); 
        if(v_newFileName1.length() == 0){   v_newFileName1 = v_upfile;   }
        if(v_newFileName2.length() == 0){   v_newFileName2 = v_upfile2;   }
		
		//기존 파일정보
		String v_oldupfile = v_upfile;
		String v_oldrealfile = v_realfile;
		String v_oldupfile2 = v_upfile2;
		String v_oldrealfile2 = v_realfile2;
		
                
        try{
            connMgr = new DBConnectionManager();
			//업로드한 파일이 없을 경우
			if (v_realFileName1.equals("")) {
				//기존파일 삭제
				if (v_check1.equals("Y")) {
					v_newFileName1   = "";
					v_realFileName1  = "";
				} else {
				//기존파일 유지
					v_newFileName1   = v_oldupfile;
					v_realFileName1  = v_oldrealfile;
				}
			}
			
			//업로드한 파일이 없을 경우
			if (v_realFileName2.equals("")) {
				//기존파일 삭제
				if (v_check2.equals("Y")) {
					v_newFileName2   = "";
					v_realFileName2  = "";
				} else {
				//기존파일 유지
					v_newFileName2   = v_oldupfile2;
					v_realFileName2  = v_oldrealfile2;
				}
			}


            sql  = "update TZ_PROJORD set lesson=?,reptype=?,expiredate=?,title=?,groupcnt=?,";
            sql += "contents=?,score=?,upfile=?,upfile2=?,realfile=?,realfile2=?,luserid=?,ldate=to_char(sysdate,'YYYYMMDDHH24MISS'), ";
            sql += "isopen=?,isopenscore=?, ansyn=?, useyn=? ";
            sql += "where subj='"+v_subj+"' and year='"+v_year+"' and subjseq='"+v_subjseq+"' ";
            sql += "and ordseq='"+v_ordseq+"' ";
            
            pstmt = connMgr.prepareStatement(sql);
           // System.out.println("sql==========>"+sql);
            pstmt.setString(1,v_lesson);
            pstmt.setString(2,v_reptype);
            pstmt.setString(3,v_expiredate);
            pstmt.setString(4,v_title);
            pstmt.setInt(5,v_groupcnt);
            pstmt.setString(6,v_contents);
            pstmt.setInt(7,v_score);
            pstmt.setString(8,v_newFileName1);
            pstmt.setString(9,v_newFileName2);
			pstmt.setString(10,v_realFileName1);
            pstmt.setString(11,v_realFileName2);
            pstmt.setString(12,v_user_id);
            pstmt.setString(13,v_isopen);
            pstmt.setString(14,v_isopenscore);
            pstmt.setString(15,v_ansyn);
            pstmt.setString(16,v_useyn);               
            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex) {                        
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt != null)  { try { pstmt.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }
        
    /**
    리포트 그룹별 등록/수정 페이지
    @param box      receive from the form object and session
    @return ArrayList   
    */
     public ArrayList handlingProjectGroupPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls              = null;        
        PreparedStatement pstmt2= null;
        ResultSet rs            = null;
        ArrayList list          = null;
        BPProjectData  data       = null;
        String sql1             = "";
        String sql2             = "";
        String  v_subj          = box.getString("p_subj");          //과정
        String  v_year          = box.getString("p_year");          //년도
        String  v_subjseq       = box.getString("p_subjseq");       //과정차수        
        String  v_userid        = ""; 
        int     v_ordseq        = box.getInt("p_ordseq");           //출제번호                
        int     v_groupcnt      = box.getInt("p_groupcnt");         //그룹갯수        
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();
            //select userid,cono,name,jikwinm,companynm,gpmnm,deptnm
            sql1 = "select A.userid,A.cono,A.name,A.jikwinm,B.companynm,B.gpmnm,B.deptnm ";
            sql1+= "from TZ_MEMBER A,TZ_COMP B,TZ_STUDENT C ";
            sql1+= "where C.subj='"+v_subj+"' and C.year='"+v_year+"' and C.subjseq='"+v_subjseq+"' ";
            sql1+= "and A.userid=C.userid and A.comp=B.comp ";
            ls = connMgr.executeQuery(sql1);             
//            System.out.println("sql1=============>"+sql1);
            
            //projgrp,projname,chief
            sql2 = "select projid projgrp,projname,chief from TZ_PROJGRP ";
            sql2+= "where subj=? and year=? and subjseq=? ";
            sql2+= "and ordseq=? and userid=?";
			
            pstmt2 = connMgr.prepareStatement(sql2);   
//            System.out.println("sql2============>"+sql2);

            while (ls.next()) {
                data=new BPProjectData();                
                v_userid    = ls.getString("userid");           
                try{
                    pstmt2.setString(1,v_subj);
                    pstmt2.setString(2,v_year);
                    pstmt2.setString(3,v_subjseq);
                    pstmt2.setInt(4,v_ordseq);
                    pstmt2.setString(5,v_userid);              
                    rs = pstmt2.executeQuery();
                    if(rs.next()){
                        data.setProjgrp(rs.getString("projgrp"));
                        data.setProjname(rs.getString("projname"));
                        data.setChief(rs.getString("chief"));
                    }
                }catch(Exception ex) {}
                finally {  
                    if(rs != null){try { rs.close(); }catch (Exception e){} }  
                }
                data.setUserid(ls.getString("userid"));
                data.setCono(ls.getString("cono"));
                data.setName(ls.getString("name"));
                data.setJikwinm(ls.getString("jikwinm"));
                data.setCompanynm(ls.getString("companynm"));
                data.setGpmnm(ls.getString("gpmnm"));
                data.setDeptnm(ls.getString("deptnm"));
                list.add(data);
            }
        }            
        catch (Exception ex) {
        	ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }           
            if(pstmt2 != null){try { pstmt2.close(); }catch (Exception e){} }              
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;        
    }    
    
    /**
    리포트 그룹별 등록/수정
    @param box      receive from the form object and session
    @return int
    */
     public int handlingProjectGroup(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt2    = null;
        String sql1                 = "";        
        String sql2                 = "";      
        int isOk1                   = 0;
        int isOk2                   = 0;
        String v_subj               = box.getString("p_subj");          //과정
        String v_subjnm             = box.getString("p_subjnm");        //과정명
        String v_year               = box.getString("p_year");          //년도
        String v_lesson             = box.getString("p_lesson");         //일차
        String v_subjseq            = box.getString("p_subjseq");       //과정차수 
        String v_projgrp            = box.getString("p_projgrp");       //그룹코드
        String v_projname           = v_subjnm+v_subjseq+"과정"+v_lesson+"일차"+v_projgrp+"그룹"; //그룹명 
        int    v_ordseq             = box.getInt("p_ordseq"); 
        //p_grouping 넘어온 다수의 value를 처리하기 위해 vector로 구현
        Vector v_grouping           = new Vector();
        v_grouping                  = box.getVector("p_grouping");
        Enumeration em              = v_grouping.elements();
        String v_userid             = "";   //실제 넘어온 각각의 value 
        String v_chief              = box.getString("p_chief");
        String v_user_id            = box.getSession("userid");                
        try{
            connMgr = new DBConnectionManager();
               
                //insert TZ_PROJGRP table
                sql2 = "insert into TZ_PROJGRP(subj,year,subjseq,ordseq,userid,projid,projname,chief,luserid,ldate) ";
                sql2+= " values(?,?,?,?,?,?,?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'))";
                pstmt2 = connMgr.prepareStatement(sql2);            
                
                //delete TZ_PROJGRP
                sql1 = "delete from TZ_PROJGRP ";
                sql1+= "where subj='"+v_subj+"' and year='"+v_year+"' and subjseq='"+v_subjseq+"' ";
                sql1+= "and ordseq='"+v_ordseq+"' and projid='"+v_projgrp+"'";
                isOk1 = connMgr.executeUpdate(sql1);
                                    
                while(em.hasMoreElements()){
			        v_userid    = (String)em.nextElement();			       
            
                    pstmt2.setString(1,v_subj);
                    pstmt2.setString(2,v_year);
                    pstmt2.setString(3,v_subjseq);
                    pstmt2.setInt(4,v_ordseq);
                    pstmt2.setString(5,v_userid);
                    pstmt2.setString(6,v_projgrp);
                    pstmt2.setString(7,v_projname);
                    pstmt2.setString(8,v_chief);
                    pstmt2.setString(9,v_user_id);
                    isOk2 = pstmt2.executeUpdate();
                }    
        }
        catch(Exception ex) {                        
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(pstmt2 != null)  { try { pstmt2.close(); } catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk1 + isOk2;
    }    


     /**
    리포트 NO 다음값 구하기
    @param box      receive from the form object and session
    @return ArrayList
    */
     public int selectMaxProjectSeq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql          = "";
        DataBox dbox 		= null;
        ListSet ls         	= null;
        
        int	v_projseq 		= 1;
        
        int v_maxprojcnt 	= 0;
        int v_maxprojseq 	= 0;
        
        String v_subj		= box.getString("p_subj");
        String v_year		= box.getString("p_year");
        String v_subjseq	= box.getString("p_subjseq");
        
        connMgr = new DBConnectionManager();
        
        
        try {
            sql = "select a.maxprojseq, b.maxprojcnt                ";
			sql+= "  from                                           ";
			sql+= "     ( select NVL(max(projseq),1) maxprojseq  ";
			sql+= "		    from  tz_projord                        ";
			sql+= "		   where  subj    = '" + v_subj    + "'     ";
			sql+= "		     and  year    = '" + v_year    + "'     ";
			sql+= "			 and  subjseq = '" + v_subjseq + "') a, ";
			sql+= "		( select count(*) maxprojcnt                ";
			sql+= "		    from  tz_projord                        ";
			sql+= "		   where  subj    = '" + v_subj    + "'     "; 
			sql+= "		 	 and  year    = '" + v_year    + "'     ";
			sql+= "		     and  subjseq = '" + v_subjseq + "'     ";
			sql+= "		     and  projseq = (select NVL(max(projseq),1) ";
			sql+= "				   		   	   from  tz_projord            ";
			sql+= "							  where  subj    = '" + v_subj   + "'     ";
			sql+= "							  	and  year    = '" + v_year   + "'     ";
			sql+= "								and subjseq = '" + v_subjseq + "')) b ";
            ls  = connMgr.executeQuery(sql);  
            
            if(ls.next()){
            	v_maxprojseq = ls.getInt("maxprojseq");
            	v_maxprojcnt = ls.getInt("maxprojcnt");
            	
            	if (v_maxprojcnt < 5 ) {
            	 	v_projseq = v_maxprojseq;
            	} else {
            		v_projseq = v_maxprojseq + 1;
            	}
            }
           
        }            
        catch (Exception ex) {
        	ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
        	if(ls != null)  { try { ls.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return v_projseq;        
    }              
    
    /**
    업로드 파일(첨부파일,답안파일) 삭제
    @param box      receive from the form object and session
    @return ArrayList
    */
     public int delUpfile(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk            = 0;
        String sql          = "";
        String  v_subj      = box.getString("p_subj");          //과정
        String  v_year      = box.getString("p_year");          //년도
        String  v_subjseq   = box.getString("p_subjseq");       //과정차수
        int     v_ordseq    = box.getInt("p_ordseq");
        String  v_upfile_type    = box.getString("p_upfile_type");

		if (v_upfile_type.equals("1")) { v_upfile_type=""; }
        else if (v_upfile_type.equals("2")) { v_upfile_type="2"; }
        try {
            connMgr = new DBConnectionManager();
           
            sql = "update TZ_PROJORD ";
            sql+= "set upfile"+v_upfile_type+"='' ";
            sql+= "where subj='"+v_subj+"' and year='"+v_year+"' and subjseq='"+v_subjseq+"' ";
            sql+= "and ordseq='"+v_ordseq+"'";

			isOk = connMgr.executeUpdate(sql);
           System.out.println(isOk);
        }            
        catch (Exception ex) {
        	ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;        
    }            

   
    /**
    리포트 삭제
    @param box      receive from the form object and session
    @return int
    */
     public int deleteProjectReport(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt2 = null; 
        ListSet ls         = null;     
        String sql1         = ""; 
        String sql2         = ""; 
        int isOk            = 0;
        String  v_subj      = box.getString("p_subj");          //과정
        String  v_year      = box.getString("p_year");          //년도
        String  v_subjseq   = box.getString("p_subjseq");       //과정차수        
        int     v_ordseq    = box.getInt("p_ordseq");      		//리포트번호  


        try {
            	connMgr = new DBConnectionManager();
    		    
    		    sql1 = "select 	count(userid)  ";
    		    sql1+= "from 	TZ_PROJASSIGN ";
    		    sql1+= "where   subj='" + v_subj + "' and year='" + v_year + "' and ";
    		    sql1+= "		subjseq='" + v_subjseq +"' and ordseq = " + v_ordseq ;

                ls = connMgr.executeQuery(sql1);  
            
                if (ls.next()) {
	                if(ls.getInt(1)!=0){
	                    isOk = -1; //배정된 학습자가 있어 삭제할 수 없습니다.
	                } else {
			            //Delete tz_projrep
			            sql2 = "delete 	TZ_PROJORD ";
			            sql2+= "where   subj = ? and year=? and subjseq=? and ordseq = ? ";
			            pstmt2 = connMgr.prepareStatement(sql2);
			
			            pstmt2.setString(1, v_subj);
			            pstmt2.setString(2, v_year);
			            pstmt2.setString(3, v_subjseq);
			            pstmt2.setInt(4, v_ordseq);
			            
			            isOk = pstmt2.executeUpdate();
	                }
	             }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql2 = " + sql2 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null)  { try { ls.close(); } catch (Exception e1) {} }   	
            if(pstmt2 != null)  { try { pstmt2.close(); } catch (Exception e1) {} }   	
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return isOk;
    }   
}
