//**********************************************************
//  1. 제      목: 고용보험 관리자페이지
//  2. 프로그램명: 고용보험 훈련실시신고 
//  3. 개      요: 
//  4. 환      경: 
//  5. 버      젼: 
//  6. 작      성: 2005.07.10 이연정
//  7. 수      정:
//**********************************************************
package com.credu.goyong;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.goyong.*;
import com.credu.propose.*;
import com.credu.library.*;
import com.credu.system.*;

public class GoYongManageBean {

	private ConfigSet config;
	private int row;
	
	public GoYongManageBean() {
		try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));        //        이 모듈의 페이지당 row 수를 셋팅한다
        }
        catch(Exception e) {
            e.printStackTrace();
        }
	}

	/**
	* 고용보험 훈련실시신고 리스트
	* @param box          receive from the form object and session
	* @return ArrayList         
	* @throws Exception
	*/
    public ArrayList selectStudentList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox                = null;
        ListSet ls                  = null;
        ArrayList list              = null;
        String sql            	= "";
        String count_sql  		= "";
        StringBuffer head_sql 	= new StringBuffer();
        StringBuffer body_sql 	= new StringBuffer();
        String order_sql  		= "";
        
      	String v_process	= box.getString("p_process");
        
        int 	v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
        int 	v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");  
        
        String  ss_grcode    = box.getStringDefault("s_grcode","ALL");    //교육그룹
        String  ss_gyear     = box.getStringDefault("s_gyear","ALL");     //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수
        //String  ss_grseq     = "0001";     //교육차수

        String  ss_uclass    = box.getStringDefault("s_upperclass","ALL");    //과정분류
				String  ss_mclass    = box.getStringDefault("s_middleclass","ALL");    //과정분류
        
				String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_subjseq   = box.getStringDefault("s_subjseq","ALL");   //과정 차수
        String  ss_comp      = box.getStringDefault("s_company","ALL");   //회사
				String  v_startdate  = box.getString("p_startdate");
				String  v_enddate    = box.getString("p_enddate");
        
				ProposeBean probean = new ProposeBean();
				String v_year       = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
			if (v_year.equals("")) v_year = ss_gyear;        

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();
            
            head_sql.append(" select                                                                                      \n");
            head_sql.append("         grseq                                                                               \n");
            head_sql.append("         , gyear                                                                             \n");
            head_sql.append("         , course                                                                            \n");
            head_sql.append("         , cyear                                                                             \n");
            head_sql.append("         , courseseq                                                                         \n");
            head_sql.append("         , coursenm                                                                          \n");                   
            head_sql.append("         , subj                                                                              \n");                   
            head_sql.append("         , year                                                                              \n");                   
            head_sql.append("         , subjnm                                                                            \n");                   
            head_sql.append("         , subjseq                                                                           \n");                   
            head_sql.append("         , subjseqgr                                                                         \n");                    
            head_sql.append("         , grcode                                                                            \n");                   
            head_sql.append("         , grcodenm                                                                          \n");                   
            head_sql.append("         , stucnt                                                                            \n");                    
            body_sql.append("   from                                                                                      \n");              
            body_sql.append("         (                                                                                   \n");                    
            body_sql.append("             select                                                                          \n");                        
            body_sql.append("                     c.grseq	                                                             \n");                                       
            body_sql.append("                     , c.gyear	                                                             \n");                                       
            body_sql.append("                     , c.course                                                              \n");                                      
            body_sql.append("                     , c.cyear	                                                             \n");                                       
            body_sql.append("                     , c.courseseq                                                           \n");
            body_sql.append("                     , c.coursenm                                                            \n");
            body_sql.append("                     , c.subj                                                                \n");
            body_sql.append("                     , c.year                                                                \n");
            body_sql.append("                     , c.subjnm                                                              \n");
            body_sql.append("                     , c.subjseq                                                             \n");
            body_sql.append("                     , c.subjseqgr                                                           \n");
            body_sql.append("                     , c.grcode                                                              \n");
            body_sql.append("                     , (select grcodenm from tz_grcode where grcode = c.grcode) grcodenm     \n");
            body_sql.append("                     , d.stucnt                                                              \n");
            body_sql.append("               from                                                                          \n");
            body_sql.append("                     vz_scsubjseq c	                                                         \n");
            body_sql.append("                     , (                                                                     \n");
            body_sql.append("                          select  subj, year, subjseq, count(*) stucnt                       \n");
            body_sql.append("                            from  tz_student                                                 \n");
            body_sql.append("                          group by subj, year, subjseq                                       \n");
            body_sql.append("                          having  count(*) > 0                                               \n");
            body_sql.append("                     ) d                                                                     \n");
            body_sql.append("              where  c.subj      = d.subj                                                    \n");
            body_sql.append("                and  c.year      = d.year                                                    \n");
            body_sql.append("                and  c.subjseq   = d.subjseq                                                 \n");
            			                                                                                                
			if(!v_startdate.equals("") ){//                                                                                             
				body_sql.append(" and substring(c.edustart, 1 , 8)  ="+SQLString.Format(v_startdate)+" \n");                                             
			}                                                                                                                           
			                                                                                                                            
			if(!v_enddate.equals("") ){//                                                                                               
				body_sql.append(" and substring(c.eduend, 1 , 8 ) ="+SQLString.Format(v_enddate)+" \n");  ;                                                 
			}                                                                                                                           
            if (!ss_grcode.equals("ALL")) {//교육그룹                                                                                   
            	body_sql.append(" and C.grcode = "+SQLString.Format(ss_grcode)+" \n");                                                      
            	body_sql.append(" and C.year = "+SQLString.Format(v_year)+" \n");                                                              
			}                                                                                                                           
            if (!ss_uclass.equals("ALL")) {                                                                                             
            	body_sql.append(" and C.scupperclass = "+SQLString.Format(ss_uclass)+" \n");                                                    
            }                                                                                                                           
            if (!ss_mclass.equals("ALL")) {                                                                                             
            	body_sql.append(" and C.scmiddleclass = "+SQLString.Format(ss_mclass)+" \n");                                                       
            }                                                                                                                           
            if (!ss_subjcourse.equals("ALL")) {                                                                                         
            	body_sql.append(" and C.scsubj = "+SQLString.Format(ss_subjcourse)+" \n");                                                            
            }                                                                                                                           
            if (!ss_subjseq.equals("ALL")) {                                                                                            
            	body_sql.append(" and C.scsubjseq = "+SQLString.Format(ss_subjseq)+" \n");                                                        
            }                                                                                                                           
            if (!ss_gyear.equals("") || !ss_gyear.equals("ALL") ) {                                                                     
            	body_sql.append(" and c.gyear = "+SQLString.Format(ss_gyear)+" \n");                                                                 
            }                                                                                                                           
            if (!ss_comp.equals("ALL")) {                                                                                               
                //body_sql.append(" and substr(b.comp,0,4) = '0101' \n");                                                                             
            }                                                                                                                           
            body_sql.append("        )   HuryunExeTable \n");                                                                                              
            order_sql = "  order by subj, year, subjseq \n";                                                                                    
            
            sql= head_sql.toString()+ body_sql.toString()+ order_sql;
            System.out.println("GoYongManageBean selectStudentList: "+sql);
            
            ls = connMgr.executeQuery(sql);
            
            //훈련실시 목록 요청시에만 페이지 처리합니다.(09.11.18)
            if(v_process.equals("HuryunExe")){
            	count_sql= "select count(*) "+ body_sql.toString();
                
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
            } else {
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
                }
            }
            
            
            ls.close();

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
	* 고용보험 훈련실시신고 회원 뷰
	* @param box          receive from the form object and session
	* @return ArrayList         
	*/
    public ArrayList selectStudentListView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox                = null;
        ListSet ls                  = null;
        ArrayList list              = null;
        String sql                  = "";
      	int v_pageno = 1;  
		
		String v_subj = box.getString("p_subj");
		String v_year = box.getString("p_year");
		String v_subjseq = box.getString("p_subjseq");
        
		ProposeBean probean = new ProposeBean();

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,C.grcode,";
			sql+= " get_deptnm(b.deptnam,'') compnm, get_jikwinm(B.jikwi, B.comp) jikwinm,\n";
            sql+= " B.userid,B.cono, b.resno,B.name, \n";
			sql+= "case when  b.membergubun = 'P' then  '개인'   \n";     
			sql+= "when  b.membergubun = 'C' then  '기업'       \n";
			sql+= "when  b.membergubun = 'U' then  '대학교'       \n";
			sql+= "else '-' end   as membergubunnm   			 \n";
			sql+= " from tz_student a,TZ_MEMBER b,vz_scsubjseq c \n";
			sql+= " where  a.userid = b.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq \n";
			sql+= "   and  a.subj="+SQLString.Format(v_subj);
			sql+= "   and  a.year="+SQLString.Format(v_year);
			sql+= "   and  a.subjseq="+SQLString.Format(v_subjseq);

			ls = connMgr.executeQuery(sql);

				while (ls.next()) {
          dbox = ls.getDataBox();

          list.add(dbox);
        }
        ls.close();
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
	* 출석부 SQL
	* @param box          receive from the form object and session
	* @return ArrayList         
	*/    
    public ArrayList selectProposeYList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox                = null;
        ListSet ls                  = null;
        ArrayList list              = null;
        String sql                  = "";
        
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //년도
        //String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수
        String  ss_grseq    = "0001";     //교육차수

        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");    //과정분류
		String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");    //과정분류
        
		String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //과정 차수
        String  ss_comp     = box.getStringDefault("s_company","ALL");   //회사
        
		ProposeBean probean = new ProposeBean();
		String v_year       = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
		if (v_year.equals("")) v_year = ss_gyear;        

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();
            
            sql = " select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,\n"
				+ " get_compnm(B.comp,2,4) compnm, get_jikwinm(B.jikwi, B.comp) jikwinm,\n"
                + " B.userid,B.cono, b.orga_ename,b.resno,B.name \n"
				+ " from tz_propose a,TZ_MEMBER b,vz_scsubjseq c \n"
				+ " where a.userid = b.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq \n";
            sql+= " and a.chkfinal = 'Y' \n";
            sql+= " and C.grcode = "+SQLString.Format(ss_grcode) +"\n";
            sql+= " and C.year = "+SQLString.Format(v_year) +"\n";
            
            if (!ss_uclass.equals("ALL")) {
                sql+= " and C.scupperclass = "+SQLString.Format(ss_uclass)+"\n";
            }
            if (!ss_mclass.equals("ALL")) {
                sql+= " and C.scmiddleclass = "+SQLString.Format(ss_mclass)+"\n";
            }
            
            if (!ss_subjcourse.equals("ALL")) {
              sql+= " and C.scsubj = "+SQLString.Format(ss_subjcourse)+"\n";
            }
            if (!ss_subjseq.equals("ALL")) {
              sql+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq)+"\n";
            }
            if (!ss_gyear.equals("ALL")) {
              sql+= " and c.gyear = "+SQLString.Format(ss_gyear)+"\n";
            }
            if (!ss_comp.equals("ALL")) {
                //sql+= " and substr(b.comp,0,4) = '0101'";
            }
            sql+= " order by B.name,C.course,C.cyear,C.courseseq,C.subj,C.year,C.subjseq,B.userid \n";
            
            System.out.println("sq="+sql);
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            ls.close();
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
	* 수료명단 리스트 
	* @param box          receive from the form object and session
	* @return ArrayList         
	*/    
    public ArrayList selectSuryoStudentList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox                = null;
        ListSet ls                  = null;
        ArrayList list              = null;
        String sql                  = "";

        String  ss_grcode   	= box.getStringDefault("s_grcode","ALL");    //교육그룹
        String  ss_gyear    	= box.getStringDefault("s_gyear","ALL");     //년도
        String  ss_grseq    	= box.getStringDefault("s_grseq","ALL");     //교육차수

        String  ss_uclass   	= box.getStringDefault("s_upperclass","ALL");    //과정분류
				String  ss_mclass   	= box.getStringDefault("s_middleclass","ALL");    //과정분류

				String  ss_subjcourse	=	box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_subjseq  	= box.getStringDefault("s_subjseq","ALL");   //과정 차수
        String  ss_comp  			= box.getStringDefault("s_company","ALL");   //회사
        
        String  v_startdate		= box.getString("p_startdate");		//학습시작일
        String  v_enddate			= box.getString("p_enddate");			//학습종료일

				ProposeBean probean = new ProposeBean();
				String v_year       = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
			if (v_year.equals("")) v_year = ss_gyear;        
			
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.serno,C.grseq,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,"
					+ " get_deptnm(B.deptnam,'') compnm, get_jikwinm(B.jikwi, B.comp) jikwinm,"
					+ " get_compnm(B.comp,1,2) company,"
					+ " B.userid,B.cono,resno=substring(b.resno,1,6) ||'-'||substring(b.resno,7,13),B.name,c.place,c.edustart,c.eduend,"
					+ "case when  b.membergubun = 'P' then  '개인'   \n"
					+ "when  b.membergubun = 'C' then  '기업'       \n"
					+ "when  b.membergubun = 'U' then  '대학교'       \n"
					+ "else '-' end   as membergubunnm   			 \n"
					+ " from tz_stold a,TZ_MEMBER b,vz_scsubjseq c"
					+ " where c.isclosed='Y' and a.isgoyong = 'Y' and a.userid = b.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq and A.isgraduated='Y'";
			System.out.println(" GoYongManageBean 수료증 :"+sql);			
			
			if (!ss_grcode.equals("ALL")){		
				sql+= " and C.grcode = "+SQLString.Format(ss_grcode);
            }
            if (!ss_gyear.equals("ALL")) {
                sql+= " and c.gyear = "+SQLString.Format(v_year);
            }
            if (!ss_uclass.equals("ALL")) {
                sql+= " and C.scupperclass = "+SQLString.Format(ss_uclass);
            }
            if (!ss_mclass.equals("ALL")) {
                sql+= " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
            }
            if (!ss_subjcourse.equals("ALL")) {
                sql+= " and C.scsubj = "+SQLString.Format(ss_subjcourse);
            }
            if (!ss_subjseq.equals("ALL")) {
                sql+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
            }
            if (!v_startdate.equals("")) {
            		sql+= " and substr(C.edustart,1,8) >= "+SQLString.Format(v_startdate);
            }
            if (!v_enddate.equals("")){
            		sql+= " and substr(C.eduend,1,8) <= "+SQLString.Format(v_enddate);
            }
            sql+= " order by B.name,C.course,C.year,C.courseseq,C.subj,C.subjseq,B.userid ";

 
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            ls.close();
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
	* 수료명단 데이타 
	* @param box          receive from the form object and session
	* @return DataBox         
	*/    
    public DataBox selectSuryoTitle(RequestBox box, String ss_subjcourse) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox                = null;
        ListSet ls                  = null;
        String sql                  = "";

        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","0001");     //교육차수

        String  ss_comp  = box.getStringDefault("s_company","ALL");   //회사

		//String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //과정 차수

		ProposeBean probean = new ProposeBean();
		String v_year       = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
		if (v_year.equals("")) v_year = ss_gyear;        

        try {
            connMgr = new DBConnectionManager();

            //sql = " select c.scsubjnm,d.companynm,decode(c.place,'','사이버') place,c.edustart,c.eduend, c.subjseqgr"
			//	+ " from vz_scsubjseq c, tz_comp d, tz_grcomp e"
			//	+ " where 
			//e.comp = d.comp and c.grcode = e.grcode";
			//	//+ " and c.eduend<to_char(sysdate,'YYYYMMDDHH24')";
            //
            //sql+= " and C.grcode = "+SQLString.Format(ss_grcode) +"\n";
            //sql+= " and c.year = "+SQLString.Format(v_year);
            //sql+= " and C.scsubj = "+SQLString.Format(ss_subjcourse);
            //sql+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq);

            sql+=" select                               \n";
            sql+="   c.scsubjnm,                        \n";
            sql+="   d.companynm,                       \n";
            sql+="   c.place, 							\n";
            sql+="   c.edustart,                        \n";
            sql+="   c.eduend,                          \n";
            sql+="   c.subjseqgr                        \n";
            sql+=" from                                 \n";
            sql+="   vz_scsubjseq c,                    \n";
            sql+="   tz_comp d,                         \n";
            sql+="   tz_grcomp e                        \n";
            sql+=" where                                \n";
            sql+="   e.comp = d.comp and c.grcode = e.grcode                   \n";
            sql+="   and C.grcode    = "+SQLString.Format(ss_grcode) +"\n";
            sql+="   and c.year      = "+SQLString.Format(v_year);
            sql+="   and C.scsubj    = "+SQLString.Format(ss_subjcourse);
            sql+="   and C.scsubjseq = "+SQLString.Format(ss_subjseq);

            //if (!ss_comp.equals("ALL")) {
            //    sql+= " and substr(b.comp,0,4) = '"+ss_comp+"'";
            //}

            System.out.println("sql1111111============>"+sql);

            ls = connMgr.executeQuery(sql);
            
            if (ls.next()) {
                dbox = ls.getDataBox();
            }
            ls.close();
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return dbox;
    }


	/**
	* 수료증  리스트 
	* @param box          receive from the form object and session
	* @return ArrayList         
	*/    
    public ArrayList selectSuryoJeungList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox                = null;
        ListSet ls                  = null;
        ArrayList list              = null;
        String sql                  = "";
        
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수

		String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //과정 차수

	    Vector v_checks  = box.getVector("p_checks");
		Enumeration em   = v_checks.elements();


		ProposeBean probean = new ProposeBean();
		String v_year       = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
		if (v_year.equals("")) v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.serno,b.orga_ename,C.grseq,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,"
				+ " get_compnm(B.comp,2,4) compnm, get_jikwinm(B.jikwi, B.comp) jikwinm,"
                + " B.userid,B.cono,b.resno,B.name,c.place,c.edustart,c.eduend"
				+ " from tz_stold a,TZ_MEMBER b,vz_scsubjseq c"
				+ " where a.userid = b.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq";
				//+ " and C.eduend<to_char(sysdate,'YYYYMMDDHH24') ";
		    sql+= " and C.grcode = "+SQLString.Format(ss_grcode) +"\n";
            
            if (!ss_gyear.equals("ALL")) {
                sql+= " and c.gyear = "+SQLString.Format(v_year);
            }
            if (!ss_subjcourse.equals("ALL")) {
                sql+= " and C.scsubj = "+SQLString.Format(ss_subjcourse);
            }
            if (!ss_subjseq.equals("ALL")) {
                sql+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
            }

			int i=0;
			sql += " and (";
			while(em.hasMoreElements()){
				String v_userid = (String)em.nextElement();
				if ( i != 0 ) sql += " or";
				sql += " a.userid = '" + v_userid + "'";
				i++;
			}
			sql += " )";

            sql+= " order by C.course,C.year,C.courseseq,C.subj,C.year,C.subjseq,B.userid ";

System.out.println("sql============>"+sql);
            
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            ls.close();
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
	* 수료증 리포트 
	* @param box          receive from the form object and session
	* @return ArrayList         
	*/   
    public ArrayList selectSuryoJeungPrint(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox                = null;
        ListSet ls                  = null;
        ArrayList list              = null;
        String sql                  = "";
        
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수

		String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //과정 차수

		String v_serno = box.getString("serno");
		System.out.println("v_serno============>"+v_serno);
		StringTokenizer st = new StringTokenizer(v_serno, "|");

		ProposeBean probean = new ProposeBean();
		String v_year       = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
		if (v_year.equals("")) v_year = ss_gyear;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            //sql = " select a.serno,substr(a.comp,0,4) comp, b.orga_ename,c.scsubjnm,C.grseq,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,"
			//	+ " get_compnm(B.comp,2,4) compnm, get_jikwinm(B.jikwi, B.comp) jikwinm,"
            //    + " B.userid,B.cono,b.resno,B.name,decode(c.place,'','사이버') place,c.edustart,c.eduend"
			//	+ " from tz_stold a,TZ_MEMBER b,vz_scsubjseq c"
			//	+ " where a.userid = b.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq";
			//	//+ " and C.eduend<to_char(sysdate,'YYYYMMDDHH24') ";
			
			
			sql = " select    \n";
            sql+= "   a.serno,substring(a.comp,1,4) comp,   \n";
            sql+= "   c.scsubjnm,  \n";
            sql+= "   C.grseq,    \n";
            sql+= "   C.courseseq,  \n";
            sql+= "   C.coursenm,  \n";
            sql+= "   C.subj,   \n";
            sql+= "   C.year,  \n";
            sql+= "   C.subjnm,   \n";
            sql+= "   C.subjseq,  \n";
            sql+= "   C.subjseqgr,  \n";
            sql+= "   get_compnm(B.comp,2,4) compnm, \n";
            sql+= "   get_jikwinm(B.jikwi, B.comp) jikwinm,   \n";
            sql+= "   B.userid,  \n";
            sql+= "   B.cono,    \n";
            sql+= "   b.resno,   \n";
            sql+= "   B.name,    \n";
            sql+= "   c.place,    \n";
            sql+= "   c.edustart,  \n";
            sql+= "   c.eduend   \n";
            sql+= " from         \n";
            sql+= "   tz_stold a,   \n";
            sql+= "   TZ_MEMBER b,   \n";
            sql+= "   vz_scsubjseq c    \n";
            sql+= " where     \n";
            sql+= "   a.userid = b.userid     \n";
            sql+= "   and A.subj=C.subj     \n";
            sql+= "   and A.year=C.year    \n";
            sql+= "   and A.subjseq=C.subjseq     \n";
            if (!ss_gyear.equals("ALL")) {
              sql+= "   and c.year = "+SQLString.Format(v_year)+"\n";
            }
            if (!ss_subjcourse.equals("ALL")) {
              sql+= "   and C.scsubj = "+SQLString.Format(ss_subjcourse);
            }
            if (!ss_subjseq.equals("ALL")) {
              sql+= "   and C.scsubjseq = "+SQLString.Format(ss_subjseq);
            }
            
            int i=0;
			sql += " and (";
			while (st.hasMoreTokens()) {
				String temp = st.nextToken();
				if ( i != 0 ) sql += " or";
				sql += " a.serno = '" + temp + "'";
				i++;
			}
			sql += " )";        
            sql+= " order by C.course,C.year,C.courseseq,C.subj,C.year,C.subjseq,B.userid \n";

System.out.println("sql_suryojeung============>"+sql);
            
            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            ls.close();
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
	* 훈련실시신고 파일출력
	* @param box          receive from the form object and session
	* @return ArrayList         
	*/   
    public ArrayList selectStudentList2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox                = null;
        ListSet ls                  = null;
        ArrayList list              = null;
        String sql                  = "";
        
        String  ss_grcode   = box.getStringDefault("p_grcode","ALL");    //교육그룹
        String  ss_gyear    = box.getStringDefault("p_gyear","ALL");     //년도
        String  ss_grseq    = box.getStringDefault("p_grseq","ALL");     //교육차수

		String  ss_subj	 	= box.getStringDefault("p_subj","ALL");		 //과정&코스
        String  ss_subjseq  = box.getStringDefault("p_subjseq","ALL");   //과정 차수
        String  ss_comp     = box.getStringDefault("s_company","ALL");   //회사
        
		ProposeBean probean = new ProposeBean();
		String v_year       = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subj, ss_subjseq);
		if (v_year.equals("")) v_year = ss_gyear;        

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();
            
            //sql = " select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,\n"
			//	+ " get_compnm(B.comp,2,4) compnm, get_jikwinm(B.jikwi, B.comp) jikwinm,\n"
            //   + " B.userid,B.cono, b.orga_ename,b.resno,B.name \n"
			//	+ " from tz_student a,TZ_MEMBER b,vz_scsubjseq c \n"
			
			/* 2005.11.17_하경태 : Oracle -> mssql
			sql = "select b.name, length(b.name) namelength,b.resno, decode(b.office_gbn,'Y','01','04') office_gbn,null as major,null as jikjong, \n"
				+ "decode(b.jikup,'1','04','2','05','3','03','4','02','5','01','6','01','7','01','8','01','9','01','0','06','A','06','B','06','07') jikup, \n"
				+ "null as judang,null as judangtime \n"
				+ " from tz_student a,TZ_MEMBER b,vz_scsubjseq c \n"				
				+ " where a.userid = b.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq \n";
				*/
			sql = "select b.name, LENGTH(b.name) namelength,b.resno, "
				+ " case b.office_gbn When 'Y' Then '01' Else '04' End as office_gbn,null as major,null as jikjong, \n"
				+ " case  b.jikup "
				+ " When '1' Then '04' "
				+ " When '2' Then '05' " 
				+ " When '3' Then '03' " 
				+ " When '4' Then '02' "
				+ " When '5' Then '01' "
				+ " When '6' Then '01' "
				+ " When '7' Then '01' "
				+ " When '8' Then '01' "
				+ " When '9' Then '01' "
				+ " When '0' Then '06' " 
				+ " When 'A' Then '06' " 
				+ " When 'B' Then '06' "
				+ " Else '07' End as jikup, \n"
				+ "null as judang,null as judangtime \n"
				+ " from tz_student a,TZ_MEMBER b,vz_scsubjseq c \n"				
				+ " where a.userid = b.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq \n";
				//+ " and C.eduend>to_char(sysdate,'YYYYMMDDHH24') \n";
            sql+= " and C.grcode = "+SQLString.Format(ss_grcode) +"\n";
//            sql+= " and C.year = "+SQLString.Format(v_year) +"\n";
            
            if (!ss_subj.equals("ALL")) {
              sql+= " and C.scsubj = "+SQLString.Format(ss_subj)+"\n";
            }
            if (!ss_subjseq.equals("ALL")) {
              sql+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq)+"\n";
            }
            if (!ss_gyear.equals("ALL")) {
              sql+= " and c.gyear = "+SQLString.Format(ss_gyear)+"\n";
            }
            if (!ss_comp.equals("ALL")) {
                //sql+= " and substr(b.comp,0,4) = '0101'";
            }
            sql+= " order by C.course,C.cyear,C.courseseq,C.subj,C.year,C.subjseq,B.userid \n";

System.out.println("sql고용보험===========>"+sql);

            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            ls.close();
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
	* 수료증 발급대장 txt파일 인쇄
	* @param box          receive from the form object and session
	* @return ArrayList         
	*/      
    public ArrayList selectStudentList3(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox                = null;
        ListSet ls                  = null;
        ArrayList list              = null;
        String sql                  = "";
        
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //년도
        //String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수
        String  ss_grseq    = "0001";     //교육차수

        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");    //과정분류
		String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");    //과정분류
        
		String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //과정 차수
        String  ss_comp     = box.getStringDefault("s_company","ALL");   //회사
        
		ProposeBean probean = new ProposeBean();
		String v_year       = probean.getSubjYear(ss_grcode, ss_gyear, ss_grseq, ss_subjcourse, ss_subjseq);
		if (v_year.equals("")) v_year = ss_gyear;        

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();
            
            //sql = " select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,\n"
			//	+ " get_compnm(B.comp,2,4) compnm, get_jikwinm(B.jikwi, B.comp) jikwinm,\n"
            //   + " B.userid,B.cono, b.orga_ename,b.resno,B.name \n"
			//	+ " from tz_student a,TZ_MEMBER b,vz_scsubjseq c \n"
			/* 2005.11.17_하경태 : Oracle -> Mssql
			sql = "select b.name, length(b.name) namelength,b.resno, decode(b.office_gbn,'Y','01','04') office_gbn,b.acceace,null as major,null as jikjong, \n"
				+ "decode(b.jikup,'1','04','2','05','3','03','4','02','5','01','6','01','7','01','8','01','9','01','0','06','A','06','B','06','07') jikup, \n"
				+ "null as judang,null as judangtime \n"
				+ " from tz_stold a,TZ_MEMBER b,vz_scsubjseq c \n"				
				+ " where c.isclosed='Y' and a.userid = b.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq \n";
			*/
			sql = "select b.name, LENGTH(b.name) namelength,b.resno, "
				+ " case  b.office_gbn When 'Y' Then '01' Else '04' End as office_gbn,b.acceace,null as major,null as jikjong, \n"
				+ " case b.jikup "
				+ " 	When '1' Then '04' "
				+ "		When '2' Then '05' "
				+ "		When '3' Then '03' "
				+ " 	When '4' Then '02' "
				+ "		When '5' Then '01' "
				+ " 	When '6' Then '01' "
				+ " 	When '7' Then '01' "
				+ " 	When '8' Then '01' "
				+ "		When '9' Then '01' "
				+ "		When '0' Then '06' "
				+ "		When 'A' Then '06' "
				+ "		When 'B' Then '06' "
				+ "		Else '07' End as jikup, \n"
				+ " null as judang,null as judangtime \n"
				+ " from tz_stold a,TZ_MEMBER b,vz_scsubjseq c \n"				
				+ " where c.isclosed='Y' and a.userid = b.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq \n";
				//+ " and C.eduend>to_char(sysdate,'YYYYMMDDHH24') \n";
            sql+= " and C.grcode = "+SQLString.Format(ss_grcode) +"\n";
            sql+= " and C.year = "+SQLString.Format(v_year) +"\n";
            
            if (!ss_uclass.equals("ALL")) {
                sql+= " and C.scupperclass = "+SQLString.Format(ss_uclass)+"\n";
            }
            if (!ss_mclass.equals("ALL")) {
                sql+= " and C.scmiddleclass = "+SQLString.Format(ss_mclass)+"\n";
            }
            
            if (!ss_subjcourse.equals("ALL")) {
              sql+= " and C.scsubj = "+SQLString.Format(ss_subjcourse)+"\n";
            }
            if (!ss_subjseq.equals("ALL")) {
              sql+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq)+"\n";
            }
            if (!ss_gyear.equals("ALL")) {
              sql+= " and c.gyear = "+SQLString.Format(ss_gyear)+"\n";
            }
            if (!ss_comp.equals("ALL")) {
                //sql+= " and substr(b.comp,0,4) = '0101'";
            }
            sql+= " order by C.course, C.cyear, C.courseseq, C.subj, C.year, C.subjseq, B.userid \n";

System.out.println("sql12341111============>"+sql);

            ls = connMgr.executeQuery(sql);
            
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            ls.close();
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

}