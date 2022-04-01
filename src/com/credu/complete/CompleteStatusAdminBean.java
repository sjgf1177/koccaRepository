//**********************************************************
//  1. 제      목: COMPLETE STATUS ADMIN BEAN
//  2. 프로그램명: CompleteStatusAdminBean.java
//  3. 개      요: 수료 현황 관리자 bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 8. 21
//  7. 수      정: 조재형 2008. 11. 14
//**********************************************************
package com.credu.complete;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.complete.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

public class CompleteStatusAdminBean {
    private ConfigSet config;
    private int row;

    public CompleteStatusAdminBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //이 모듈의 페이지당 row 수를 셋팅한다
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
     public ArrayList selectCompleteMemberList(RequestBox box) throws Exception {
		PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
		DataBox	dbox		= null;
        String sql1         = "";
        String sql2         = "";
        String  v_Bcourse   = ""; //이전코스
        String  v_course    = ""; //현재코스
        String  v_Bcourseseq= ""; //이전코스차수
        String  v_courseseq = ""; //현재코스차수
        int     l           = 0;
        
		// 페이징
		int    v_pageno    = box.getInt("p_pageno");
		if (v_pageno == 0) v_pageno = 1;
		int    v_pagesize  = box.getInt("p_pagesize");

        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");       //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");        //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");        //교육차수
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //과정분류
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //과정분류
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //과정분류
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");   //과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");      //과정 차수
        String  ss_company  = box.getStringDefault("s_company","ALL");      //회사
        String  ss_edustart = box.getString("s_edustart");     //교육시작일
        String  ss_eduend   = box.getString("s_eduend");       //교육종료일
        String  ss_selgubun = box.getString("s_selgubun");                  //직군별:JIKUN,직급별:JIKUP,사업부별:GPM
        String  ss_seltext  = box.getStringDefault("s_seltext","ALL");      //검색분류별 검색내용
        String  ss_seldept  = box.getStringDefault("s_seldept","ALL");      //사업부별 부서 검색내용
        String  ss_action   = box.getString("s_action");

        String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
        String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서

//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";
        String group_sql  = "";
        String order_sql  = "";

        ManagerAdminBean bean = null;
        String  v_sql_add   = "";
        String  v_userid    = box.getSession("userid");
        String  s_gadmin    = box.getSession("gadmin");

        try {
            if(ss_action.equals("go")){
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
                list2 = new ArrayList();

                //select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,isonoff,compnm,
                //jikwinm,userid,cono,name,edustart,eduend,tstep,avtstep,mtest,ftest,report,act,etc1,
                //score,isgraduated,email,ismailing,place
                head_sql = "select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,c.subjseqgr,A.serno, ";
				head_sql += "	C.isonoff, get_deptnm('',A.userid) compnm, get_compnm(B.comp,2,2) companynm, B.jikwinm, B.jikupnm,B.resno, ";
				head_sql += " 	B.userid,B.cono,B.name,C.edustart,C.eduend,A.tstep,A.avtstep, ";
                if(GetCodenm.get_config("score_disp").equals("WS")){    //가중치적용
					head_sql += " A.avreport report,A.avact act,A.avmtest mtest,A.avftest ftest,A.avetc1 etc1, A.avetc2 etc2, A.avhtest htest, ";
                }else{                                                  //가중치비적용
					head_sql += " A.report report,A.act act,A.mtest mtest,A.ftest ftest,A.etc1 etc1, A.etc2 etc2, A.htest htest, ";
                }
				head_sql += " A.score,A.isgraduated,B.email,B.ismailing,C.place, B.MemberGubun, C.isbelongcourse, C.subjcnt ";
				body_sql += " from TZ_STOLD A,TZ_MEMBER B,VZ_SCSUBJSEQ C where c.isclosed = 'Y'  ";
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
                if (!ss_company.equals("ALL")) {
					body_sql += " and B.comp like '"+GetCodenm.get_compval(ss_company)+"'";
                }

                if (!ss_edustart.equals("")){
					body_sql += " and C.edustart >= "+SQLString.Format(ss_edustart);
                }
                if (!ss_eduend.equals("")) {
					body_sql += " and C.eduend <= "+SQLString.Format(ss_eduend);
                }

                //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                if (ss_edustart.equals("") && ss_eduend.equals("")) {
					body_sql += " and C.gyear = "+SQLString.Format(ss_gyear);
                }

				body_sql += " and A.userid=B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";

                if(v_orderColumn.equals("")) {
					order_sql += " order by C.course,C.cyear,C.courseseq,C.subj,C.year,C.subjseq,B.userid ";
				} else {
					order_sql += " order by C.course, " + v_orderColumn + v_orderType;
				}

				sql= head_sql+ body_sql+ order_sql;

				ls1 = connMgr.executeQuery(sql);

				count_sql= "select count(*) "+ body_sql;
				//int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

				// 수정일 : 05.11.09 수정자 : 이나연 _ 수료 > 과정별교육이력 select 쿼리
				//pstmt = connMgr.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				//ls1 = new ListSet(pstmt);

                //ls1.setPageSize(row);                       //페이지당 row 갯수를 세팅한다
				//ls1.setCurrentPage(v_pageno, total_row_count);               //현재페이지번호를 세팅한다.
                //int total_page_count = ls1.getTotalPage();  //전체 페이지 수를 반환한다
                //int total_row_count = ls1.getTotalCount();  //전체 row 수를 반환한다

                
    			//페이징
    			ls1.setPageSize(v_pagesize);             			//  페이지당 row 갯수를 세팅한다
    			ls1.setCurrentPage(v_pageno);				//     현재페이지번호를 세팅한다.
    			int total_page_count = ls1.getTotalPage();	//     전체 페이지 수를 반환한다
    			int total_row_count = ls1.getTotalCount();	//     전체 row 수를 반환한다


                
                while (ls1.next()) {
					dbox = ls1.getDataBox();

		            dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
		            dbox.put("d_totalpage", new Integer(total_page_count));
		            dbox.put("d_rowcount", new Integer(row));
		            list1.add(dbox);
					/*
                    System.out.println(ls1.getString("compnm"));
                    data1=new CompleteStatusData();
                    data1.setGrseq(ls1.getString("grseq"));
                    data1.setCourse(ls1.getString("course"));
                    data1.setCyear(ls1.getString("cyear"));
                    data1.setCourseseq(ls1.getString("courseseq"));
                    data1.setCoursenm(ls1.getString("coursenm"));
                    data1.setSubj(ls1.getString("subj"));
                    data1.setYear(ls1.getString("year"));
                    data1.setSubjseq(ls1.getString("subjseq"));
                    data1.setSubjseqgr(ls1.getString("subjseqgr"));
                    data1.setSubjnm(ls1.getString("subjnm"));
                    data1.setIsonoff(ls1.getString("isonoff"));
                    data1.setCompnm(ls1.getString("compnm"));
                    data1.setCompanynm(ls1.getString("companynm"));
                    data1.setJikwinm(ls1.getString("jikwinm"));
                    data1.setJikupnm(ls1.getString("jikupnm"));
                    data1.setUserid(ls1.getString("userid"));
                    data1.setCono(ls1.getString("cono"));
                    data1.setName(ls1.getString("name"));
                    data1.setEdustart(ls1.getString("edustart"));
                    data1.setEduend(ls1.getString("eduend"));
                    data1.setTstep(ls1.getInt("tstep"));
                    data1.setAvtstep(ls1.getInt("avtstep"));
                    data1.setMtest(ls1.getInt("mtest"));
                    data1.setFtest(ls1.getInt("ftest"));
                    data1.setHtest(ls1.getInt("htest"));  // 2005.9.11 by정은년 (왜 이게 엄엇지??)

					data1.setMembergubun(ls1.getString("MemberGubun"));

                    data1.setReport(ls1.getInt("report"));
                    data1.setAct(ls1.getInt("act"));
                    data1.setEtc1(ls1.getInt("etc1"));
                    data1.setScore(ls1.getInt("score"));
                    data1.setIsgraduated(ls1.getString("isgraduated"));
                    data1.setEmail(ls1.getString("email"));
                    data1.setPlace(ls1.getString("place"));
                    data1.setIsmailing(ls1.getString("ismailing"));
                    data1.setResno(ls1.getString("resno"));
                    data1.setSerno(ls1.getString("serno"));
                    data1.setDispnum(total_row_count - ls1.getRowNum() + 1);
                    data1.setTotalPageCount(total_page_count);
                    data1.setRowCount(row);
                    list1.add(data1);
                    */
                }
				/*
                for(int i=0;i < list1.size(); i++){
                    data2       =   (CompleteStatusData)list1.get(i);
                    v_course    =   data2.getCourse();
                    v_courseseq =   data2.getCourseseq();
                    if(!v_course.equals("000000") && !(v_Bcourse.equals(v_course) && v_Bcourseseq.equals(v_courseseq))){
                        sql2 = "select count(A.subj) cnt from VZ_SCSUBJSEQ A,TZ_STOLD B,TZ_MEMBER C ";
                        sql2+= "where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq ";
                        sql2+= "and B.userid=C.userid ";
                        if (!ss_grcode.equals("ALL")) {
                            sql2+= " and A.grcode = "+SQLString.Format(ss_grcode);
                        }
                        if (!ss_grseq.equals("ALL")) {
                            sql2+= " and A.grseq = "+SQLString.Format(ss_grseq);
                        }

                        if (!ss_uclass.equals("ALL")) {
                            sql2+= " and A.scupperclass = "+SQLString.Format(ss_uclass);
                        }
                        if (!ss_mclass.equals("ALL")) {
                            sql2+= " and A.scmiddleclass = "+SQLString.Format(ss_mclass);
                        }
                        if (!ss_lclass.equals("ALL")) {
                            sql2+= " and A.sclowerclass = "+SQLString.Format(ss_lclass);
                        }

                        if (!ss_subjcourse.equals("ALL")) {
                            sql2+= " and A.scsubj = "+SQLString.Format(ss_subjcourse);
                        }
                        if (!ss_subjseq.equals("ALL")) {
                            sql2+= " and A.scsubjseq = "+SQLString.Format(ss_subjseq);
                        }
                        if (!ss_company.equals("ALL")) {
                            sql2+= " and C.comp like '"+GetCodenm.get_compval(ss_company)+"'";
                        }
                        if (!ss_edustart.equals("ALL")){
                            sql2+= " and A.edustart >= "+SQLString.Format(ss_edustart);
                        }
                        if (!ss_eduend.equals("ALL")) {
                            sql2+= " and A.eduend <= "+SQLString.Format(ss_eduend);
                        }
                        //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                        if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
                            sql2+= " and A.gyear = "+SQLString.Format(ss_gyear);
                        }
                        sql2+= "and A.course = "+SQLString.Format(v_course)+" and A.courseseq = "+SQLString.Format(v_courseseq);

                        ls2 = connMgr.executeQuery(sql2);

                        if(ls2.next()){
                            data2.setRowspan(ls2.getInt("cnt"));
                            data2.setIsnewcourse("Y");
                        }
                    }else{
                        data2.setRowspan(0);
                        data2.setIsnewcourse("N");
                    }
                    v_Bcourse   =   v_course;
                    v_Bcourseseq=   v_courseseq;
                    list2.add(data2);
                    if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
                }
                */
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }

    /**
    수료자 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectCompleteRosterList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
        String sql1         = "";
        String sql2         = "";
        CompleteStatusData data1=null;
        CompleteStatusData data2=null;
        String  v_Bcourse   = ""; //이전코스
        String  v_course    = ""; //현재코스
        String  v_Bcourseseq= ""; //이전코스차수
        String  v_courseseq = ""; //현재코스차수
        int v_pageno        = box.getInt("p_pageno");
        int     l           = 0;
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");           //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");            //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");            //교육차수
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");       //과정분류
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");      //과정분류
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");       //과정분류
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");       //과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");          //과정 차수
        String  ss_company  = box.getStringDefault("s_company","ALL");          //회사
        String  ss_edustart = box.getStringDefault("s_edustart","ALL");         //교육시작일
        String  ss_eduend   = box.getStringDefault("s_eduend","ALL");           //교육종료일
        String  ss_selgubun = box.getString("s_selgubun");                      //직군별:JIKUN,직급별:JIKUP,사업부별:GPM
        String  ss_seltext  = box.getStringDefault("s_seltext","ALL");          //검색분류별 검색내용
        String  ss_seldept  = box.getStringDefault("s_seldept","ALL");          //사업부별 부서 검색내용
        String  ss_action   = box.getString("s_action");
        String  v_orderColumn= box.getString("p_orderColumn");                  //정렬할 컬럼명

        ManagerAdminBean bean = null;
        String  v_sql_add   = "";
        String  v_userid    = box.getSession("userid");
        String  s_gadmin    = box.getSession("gadmin");

//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";
        String group_sql  = "";
        String order_sql  = "";

        try {
            if(ss_action.equals("go")){
				PreparedStatement pstmt = null;
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
                list2 = new ArrayList();

                //select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,isonoff,compnm,
                //jikwinm,userid,cono,name,edustart,eduend,score,isgraduated,email,ismailing
				head_sql = " select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,";
				head_sql+= " C.isonoff,get_compnm(B.comp,3,5) compnm,get_jikwinm(B.jikwi,B.comp) jikwinm,get_jikupnm(B.jikup,B.comp) jikupnm,";
				head_sql+= " B.userid,B.cono,B.name,C.edustart,C.eduend,A.score,A.isgraduated,B.email,B.ismailing ";
				body_sql+= "from TZ_STOLD A,TZ_MEMBER B,VZ_SCSUBJSEQ C where A.isgraduated='Y' and  c.isclosed = 'Y' ";
                if (!ss_grcode.equals("ALL")) {
					body_sql+= " and C.grcode = "+SQLString.Format(ss_grcode);
                }
                if (!ss_grseq.equals("ALL")) {
					body_sql+= " and C.grseq = "+SQLString.Format(ss_grseq);
                }

                if (!ss_uclass.equals("ALL")) {
					body_sql+= " and C.scupperclass = "+SQLString.Format(ss_uclass);
                }
                if (!ss_mclass.equals("ALL")) {
					body_sql+= " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
                }
                if (!ss_lclass.equals("ALL")) {
					body_sql+= " and C.sclowerclass = "+SQLString.Format(ss_lclass);
                }

                if (!ss_subjcourse.equals("ALL")) {
					body_sql+= " and C.scsubj = "+SQLString.Format(ss_subjcourse);
                }
                if (!ss_subjseq.equals("ALL")) {
					body_sql+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
                }
                if (!ss_company.equals("ALL")) {
					body_sql+= " and B.comp like '"+GetCodenm.get_compval(ss_company)+"'";
                }
                if (!ss_edustart.equals("ALL")){
					body_sql+= " and C.edustart >= "+SQLString.Format(ss_edustart);
                }
                if (!ss_eduend.equals("ALL")) {
					body_sql+= " and C.eduend <= "+SQLString.Format(ss_eduend);
                }
                //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
					body_sql+= " and C.gyear = "+SQLString.Format(ss_gyear);
                }
/*
                // 부서장일경우
                if (s_gadmin.equals("K7")) {
                    bean = new ManagerAdminBean();
                    v_sql_add   = bean.getManagerDept(v_userid, s_gadmin);
                    if (!v_sql_add.equals("")) body_sql += " and B.comp in " + v_sql_add;       // 관리부서검색조건쿼리
                }

                if(ss_selgubun.equals("JIKUN") && !ss_seltext.equals("ALL"))      {  //직군별
					body_sql+= " and B.jikun = "+SQLString.Format(ss_seltext);
                }else if(ss_selgubun.equals("JIKWI") && !ss_seltext.equals("ALL")){  //직위별
					body_sql+= " and B.jikwi = "+SQLString.Format(ss_seltext);
                }else if(ss_selgubun.equals("GPM") && !ss_seltext.equals("ALL")){  //사업부별
					body_sql+= " and B.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seltext));
                    if(!ss_seldept.equals("ALL")){
						body_sql+= " and B.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seldept));
                     }
                }
                */
				body_sql+= " and A.userid=B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";
                if(!v_orderColumn.equals("")){
                    v_orderColumn = "B."+v_orderColumn;
					order_sql+= " order by C.course,C.cyear,C.courseseq,C.subj,C.year,C.subjseq,"+v_orderColumn;
                }else{
					order_sql+= " order by C.course,C.cyear,C.courseseq,C.subj,C.year,C.subjseq,B.userid ";
                }
/*
                ls1 = connMgr.executeQuery(sql1);

				pstmt = connMgr.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
*/
				sql= head_sql+ body_sql+ order_sql;

				ls1 = connMgr.executeQuery(sql);

				count_sql= "select count(*) "+ body_sql;
				int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

                ls1.setPageSize(row);                       //페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno, total_row_count);               //현재페이지번호를 세팅한다.
                int total_page_count = ls1.getTotalPage();  //전체 페이지 수를 반환한다
                //int total_row_count = ls1.getTotalCount();  //전체 row 수를 반환한다

                while (ls1.next()) {
                    data1=new CompleteStatusData();
                    data1.setGrseq(ls1.getString("grseq"));
                    data1.setCourse(ls1.getString("course"));
                    data1.setCyear(ls1.getString("cyear"));
                    data1.setCourseseq(ls1.getString("courseseq"));
                    data1.setCoursenm(ls1.getString("coursenm"));
                    data1.setSubj(ls1.getString("subj"));
                    data1.setYear(ls1.getString("year"));
                    data1.setSubjseq(ls1.getString("subjseq"));
                    data1.setSubjnm(ls1.getString("subjnm"));
                    data1.setIsonoff(ls1.getString("isonoff"));
                    data1.setCompnm(ls1.getString("compnm"));
                    data1.setJikwinm(ls1.getString("jikwinm"));
                    data1.setJikupnm(ls1.getString("jikupnm"));
                    data1.setUserid(ls1.getString("userid"));
                    data1.setCono(ls1.getString("cono"));
                    data1.setName(ls1.getString("name"));
                    data1.setEdustart(ls1.getString("edustart"));
                    data1.setEduend(ls1.getString("eduend"));
                    data1.setScore(ls1.getInt("score"));
                    data1.setIsgraduated(ls1.getString("isgraduated"));
                    data1.setEmail(ls1.getString("email"));
                    data1.setIsmailing(ls1.getString("ismailing"));
                    data1.setDispnum(total_row_count - ls1.getRowNum() + 1);
                    data1.setTotalPageCount(total_page_count);
                    data1.setRowCount(row);
                    list1.add(data1);
                }
                for(int i=0;i < list1.size(); i++){
                    data2       =   (CompleteStatusData)list1.get(i);
                    v_course    =   data2.getCourse();
                    v_courseseq =   data2.getCourseseq();
                    if(!v_course.equals("000000") && !(v_Bcourse.equals(v_course) && v_Bcourseseq.equals(v_courseseq))){
                        sql2 = "select count(A.subj) cnt from VZ_SCSUBJSEQ A,TZ_STOLD B,TZ_MEMBER C ";
                        sql2+= "where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq ";
                        sql2+= "and B.isgraduated='Y' and B.userid=C.userid ";
                        if (!ss_grcode.equals("ALL")) {
                            sql2+= " and A.grcode = "+SQLString.Format(ss_grcode);
                        }
                        if (!ss_grseq.equals("ALL")) {
                            sql2+= " and A.grseq = "+SQLString.Format(ss_grseq);
                        }
                        if (!ss_uclass.equals("ALL")) {
                            sql2+= " and A.scupperclass = "+SQLString.Format(ss_uclass);
                        }
                        if (!ss_mclass.equals("ALL")) {
                            sql2+= " and A.scmiddleclass = "+SQLString.Format(ss_mclass);
                        }
                        if (!ss_lclass.equals("ALL")) {
                            sql2+= " and A.sclowerclass = "+SQLString.Format(ss_lclass);
                        }

                        if (!ss_subjcourse.equals("ALL")) {
                            sql2+= " and A.scsubj = "+SQLString.Format(ss_subjcourse);
                        }
                        if (!ss_subjseq.equals("ALL")) {
                            sql2+= " and A.scsubjseq = "+SQLString.Format(ss_subjseq);
                        }
                        if (!ss_company.equals("ALL")) {
                            sql2+= " and C.comp like '"+GetCodenm.get_compval(ss_company)+"'";
                        }
                        if (!ss_edustart.equals("ALL")){
                            sql2+= " and A.edustart >= "+SQLString.Format(ss_edustart);
                        }
                        if (!ss_eduend.equals("ALL")) {
                            sql2+= " and A.eduend <= "+SQLString.Format(ss_eduend);
                        }
                        //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                        if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
                            sql2+= " and A.gyear = "+SQLString.Format(ss_gyear);
                        }

                        // 부서장일경우
                        if (s_gadmin.equals("K7")) {
                            if (!v_sql_add.equals("")) sql2 += " and C.comp in " + v_sql_add;       // 관리부서검색조건쿼리
                        }

                        if(ss_selgubun.equals("JIKUN") && !ss_seltext.equals("ALL"))      {  //직군별
                            sql2+= " and C.jikun = "+SQLString.Format(ss_seltext);
                        }else if(ss_selgubun.equals("JIKWI") && !ss_seltext.equals("ALL")){  //직위별
                            sql2+= " and C.jikwi = "+SQLString.Format(ss_seltext);
                        }else if(ss_selgubun.equals("GPM") && !ss_seltext.equals("ALL")){  //사업부별
                            sql2+= " and C.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seltext));
                            if(!ss_seldept.equals("ALL")){
                                sql2+= " and C.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seldept));
                             }
                        }
                        sql2+= "and A.course = "+SQLString.Format(v_course)+" and A.courseseq = "+SQLString.Format(v_courseseq);

                        ls2 = connMgr.executeQuery(sql2);

                        if(ls2.next()){
                            data2.setRowspan(ls2.getInt("cnt"));
                            data2.setIsnewcourse("Y");
                        }
                        if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
                    }else{
                        data2.setRowspan(0);
                        data2.setIsnewcourse("N");
                    }
                    v_Bcourse   =   v_course;
                    v_Bcourseseq=   v_courseseq;
                    list2.add(data2);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list2;
    }

    /**
    미수료자 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectNoneCompleteRosterList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
        String sql1         = "";
        String sql2         = "";
        CompleteStatusData data1=null;
        CompleteStatusData data2=null;
        String  v_Bcourse   = ""; //이전코스
        String  v_course    = ""; //현재코스
        String  v_Bcourseseq= ""; //이전코스차수
        String  v_courseseq = ""; //현재코스차수
        int v_pageno        = box.getInt("p_pageno");
        int     l           = 0;
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");       //과정분류
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");      //과정분류
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");       //과정분류
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //과정 차수
        String  ss_company  = box.getStringDefault("s_company","ALL");   //회사
        String  ss_edustart = box.getStringDefault("s_edustart","ALL");  //교육시작일
        String  ss_eduend   = box.getStringDefault("s_eduend","ALL");    //교육종료일
        String  ss_selgubun = box.getString("s_selgubun");               //직군별:JIKUN,직급별:JIKUP,사업부별:GPM
        String  ss_seltext  = box.getStringDefault("s_seltext","ALL");   //검색분류별 검색내용
        String  ss_seldept  = box.getStringDefault("s_seldept","ALL");   //사업부별 부서 검색내용
        String  ss_action   = box.getString("s_action");
        String  v_orderColumn= box.getString("p_orderColumn");           //정렬할 컬럼명

        ManagerAdminBean bean = null;
        String  v_sql_add   = "";
        String  v_userid    = box.getSession("userid");
        String  s_gadmin    = box.getSession("gadmin");

//		2005.11.15_하경태 : TotalCount 관련 쿼리 수정
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";
        String group_sql  = "";
        String order_sql  = "";

        try {
            if(ss_action.equals("go")){
				PreparedStatement pstmt = null;
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
                list2 = new ArrayList();

                //select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,isonoff,compnm,
                //jikwinm,userid,cono,name,edustart,eduend,score,isgraduated,email,ismailing
				head_sql = "select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,";
				head_sql+= "C.isonoff,get_compnm(B.comp,3,5) compnm,get_jikwinm(B.jikwi,B.comp) jikwinm,get_jikupnm(B.jikup,B.comp) jikupnm,";
				head_sql+= "B.userid,B.cono,B.name,C.edustart,C.eduend,A.score,A.isgraduated,B.email,B.ismailing ";
				body_sql+= "from TZ_STOLD A,TZ_MEMBER B,VZ_SCSUBJSEQ C where A.isgraduated='N' and c.isclosed = 'Y' ";
                if (!ss_grcode.equals("ALL")) {
					body_sql+= " and C.grcode = "+SQLString.Format(ss_grcode);
                }
                if (!ss_grseq.equals("ALL")) {
					body_sql+= " and C.grseq = "+SQLString.Format(ss_grseq);
                }
                if (!ss_uclass.equals("ALL")) {
					body_sql+= " and C.scupperclass = "+SQLString.Format(ss_uclass);
                }
                if (!ss_mclass.equals("ALL")) {
					body_sql+= " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
                }
                if (!ss_lclass.equals("ALL")) {
					body_sql+= " and C.sclowerclass = "+SQLString.Format(ss_lclass);
                }

                if (!ss_subjcourse.equals("ALL")) {
					body_sql+= " and C.scsubj = "+SQLString.Format(ss_subjcourse);
                }
                if (!ss_subjseq.equals("ALL")) {
					body_sql+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
                }
                if (!ss_company.equals("ALL")) {
					body_sql+= " and B.comp like '"+GetCodenm.get_compval(ss_company)+"'";
                }
                if (!ss_edustart.equals("ALL")){
					body_sql+= " and C.edustart >= "+SQLString.Format(ss_edustart);
                }
                if (!ss_eduend.equals("ALL")) {
					body_sql+= " and C.eduend <= "+SQLString.Format(ss_eduend);
                }
                //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
					body_sql+= " and C.gyear = "+SQLString.Format(ss_gyear);
                }
/*
                // 부서장일경우
                if (s_gadmin.equals("K7")) {
                    bean = new ManagerAdminBean();
                    v_sql_add   = bean.getManagerDept(v_userid, s_gadmin);
                    if (!v_sql_add.equals("")) body_sql += " and B.comp in " + v_sql_add;       // 관리부서검색조건쿼리
                }

                if(ss_selgubun.equals("JIKUN") && !ss_seltext.equals("ALL"))      {  //직군별
					body_sql+= " and B.jikun = "+SQLString.Format(ss_seltext);
                }else if(ss_selgubun.equals("JIKWI") && !ss_seltext.equals("ALL")){  //직위별
					body_sql+= " and B.jikwi = "+SQLString.Format(ss_seltext);
                }else if(ss_selgubun.equals("GPM") && !ss_seltext.equals("ALL")){  //사업부별
					body_sql+= " and B.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seltext));
                    if(!ss_seldept.equals("ALL")){
						body_sql+= " and B.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seldept));
                     }
                }
                */
				body_sql+= " and A.userid=B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";
                if(!v_orderColumn.equals("")){
                    v_orderColumn = "B."+v_orderColumn;
					order_sql+= " order by C.course,C.cyear,C.courseseq,C.subj,C.year,C.subjseq,"+v_orderColumn;
                }else{
					order_sql+= " order by C.course,C.cyear,C.courseseq,C.subj,C.year,C.subjseq,B.userid ";
                }

				/*
                ls1 = connMgr.executeQuery(sql1);

				pstmt = connMgr.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ls1 = new ListSet(pstmt);
				*/
				sql= head_sql+ body_sql+ order_sql;

			ls1 = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

                ls1.setPageSize(row);                       //페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno, total_row_count);               //현재페이지번호를 세팅한다.
                int total_page_count = ls1.getTotalPage();  //전체 페이지 수를 반환한다
                //int total_row_count = ls1.getTotalCount();  //전체 row 수를 반환한다

                while (ls1.next()) {
                    data1=new CompleteStatusData();
                    data1.setGrseq(ls1.getString("grseq"));
                    data1.setCourse(ls1.getString("course"));
                    data1.setCyear(ls1.getString("cyear"));
                    data1.setCourseseq(ls1.getString("courseseq"));
                    data1.setCoursenm(ls1.getString("coursenm"));
                    data1.setSubj(ls1.getString("subj"));
                    data1.setYear(ls1.getString("year"));
                    data1.setSubjseq(ls1.getString("subjseq"));
                    data1.setSubjnm(ls1.getString("subjnm"));
                    data1.setIsonoff(ls1.getString("isonoff"));
                    data1.setCompnm(ls1.getString("compnm"));
                    data1.setJikwinm(ls1.getString("jikwinm"));
                    data1.setJikupnm(ls1.getString("jikupnm"));
                    data1.setUserid(ls1.getString("userid"));
                    data1.setCono(ls1.getString("cono"));
                    data1.setName(ls1.getString("name"));
                    data1.setEdustart(ls1.getString("edustart"));
                    data1.setEduend(ls1.getString("eduend"));
                    data1.setScore(ls1.getInt("score"));
                    data1.setIsgraduated(ls1.getString("isgraduated"));
                    data1.setEmail(ls1.getString("email"));
                    data1.setIsmailing(ls1.getString("ismailing"));
                    data1.setDispnum(total_row_count - ls1.getRowNum() + 1);
                    data1.setTotalPageCount(total_page_count);
                    data1.setRowCount(row);
                    list1.add(data1);
                }
                for(int i=0;i < list1.size(); i++){
                    data2       =   (CompleteStatusData)list1.get(i);
                    v_course    =   data2.getCourse();
                    v_courseseq =   data2.getCourseseq();
                    if(!v_course.equals("000000") && !(v_Bcourse.equals(v_course) && v_Bcourseseq.equals(v_courseseq))){
                        sql2 = "select count(A.subj) cnt from VZ_SCSUBJSEQ A,TZ_STOLD B,TZ_MEMBER C ";
                        sql2+= "where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq ";
                        sql2+= "and B.isgraduated='N' and B.userid=C.userid ";
                        if (!ss_grcode.equals("ALL")) {
                            sql2+= " and A.grcode = "+SQLString.Format(ss_grcode);
                        }
                        if (!ss_grseq.equals("ALL")) {
                            sql2+= " and A.grseq = "+SQLString.Format(ss_grseq);
                        }
                        if (!ss_uclass.equals("ALL")) {
                            sql2+= " and A.scupperclass = "+SQLString.Format(ss_uclass);
                        }
                        if (!ss_mclass.equals("ALL")) {
                            sql2+= " and A.scmiddleclass = "+SQLString.Format(ss_mclass);
                        }
                        if (!ss_lclass.equals("ALL")) {
                            sql2+= " and A.sclowerclass = "+SQLString.Format(ss_lclass);
                        }

                        if (!ss_subjcourse.equals("ALL")) {
                            sql2+= " and A.scsubj = "+SQLString.Format(ss_subjcourse);
                        }
                        if (!ss_subjseq.equals("ALL")) {
                            sql2+= " and A.scsubjseq = "+SQLString.Format(ss_subjseq);
                        }
                        if (!ss_company.equals("ALL")) {
                            sql2+= " and C.comp like '"+GetCodenm.get_compval(ss_company)+"'";
                        }
                        if (!ss_edustart.equals("ALL")){
                            sql2+= " and A.edustart >= "+SQLString.Format(ss_edustart);
                        }
                        if (!ss_eduend.equals("ALL")) {
                            sql2+= " and A.eduend <= "+SQLString.Format(ss_eduend);
                        }
                        //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                        if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
                            sql2+= " and A.gyear = "+SQLString.Format(ss_gyear);
                        }
/*
                        // 부서장일경우
                        if (s_gadmin.equals("K7")) {
                            if (!v_sql_add.equals("")) sql2 += " and C.comp in " + v_sql_add;       // 관리부서검색조건쿼리
                        }

                        if(ss_selgubun.equals("JIKUN") && !ss_seltext.equals("ALL"))      {  //직군별
                            sql2+= " and C.jikun = "+SQLString.Format(ss_seltext);
                        }else if(ss_selgubun.equals("JIKWI") && !ss_seltext.equals("ALL")){  //직위별
                            sql2+= " and C.jikwi = "+SQLString.Format(ss_seltext);
                        }else if(ss_selgubun.equals("GPM") && !ss_seltext.equals("ALL")){  //사업부별
                            sql2+= " and C.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seltext));
                            if(!ss_seldept.equals("ALL")){
                                sql2+= " and C.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seldept));
                             }
                        }
                        */
                        sql2+= "and A.course = "+SQLString.Format(v_course)+" and A.courseseq = "+SQLString.Format(v_courseseq);

                        ls2 = connMgr.executeQuery(sql2);

                        if(ls2.next()){
                            data2.setRowspan(ls2.getInt("cnt"));
                            data2.setIsnewcourse("Y");
                        }
                        if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
                    }else{
                        data2.setRowspan(0);
                        data2.setIsnewcourse("N");
                    }
                    v_Bcourse   =   v_course;
                    v_Bcourseseq=   v_courseseq;
                    list2.add(data2);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list2;
    }

    /**
    수료율 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectCompleteRateList(RequestBox box) throws Exception {
		PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
		// 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
	    String sql1     = "";
	    String count_sql1     = "";
	    String head_sql1 = "";
		String body_sql1 = "";
	    String group_sql1 = "";
	    String order_sql1 = "";

	    String sql2     = "";
	    String count_sql2     = "";
	    String head_sql2 = "";
		String body_sql2 = "";
	    String group_sql2 = "";
	    String order_sql2 = "";

		DataBox dbox = null;
        String  v_Bcourse   = ""; //이전코스
        String  v_course    = ""; //현재코스
        String  v_Bcourseseq= ""; //이전코스차수
        String  v_courseseq = ""; //현재코스차수

        int v_pageno        = box.getInt("p_pageno");
        int     l           = 0;

		String  v_subjnm	= box.getStringDefault("p_subjnm","");	 //과정명 검색
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");       //과정분류
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");      //과정분류
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");       //과정분류
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //과정 차수
        String  ss_edustart = box.getStringDefault("s_edustart","ALL");  //교육시작일
        String  ss_eduend   = box.getStringDefault("s_eduend","ALL");    //교육종료일
        String  ss_action   = box.getString("s_action");

		String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
        String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서

        try {
            if(ss_action.equals("go")){
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
                list2 = new ArrayList();

                //select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,
                //edustart,eduend,educnt,gradcnt1,gradcnt2,isonoff
                head_sql1 = "select A.grseq,A.course,A.cyear,A.courseseq,A.coursenm,A.subj,A.year,A.subjnm,A.subjseq,A.subjseqgr,";
				head_sql1+= "A.edustart,A.eduend,A.isonoff, A.isbelongcourse, A.subjcnt, ";
				head_sql1+= "(select count(s.subj) from TZ_STUDENT s where s.subj=A.subj and s.year=A.year and s.subjseq=A.subjseq) educnt, ";
				head_sql1+= " case A.isclosed	";
				head_sql1+= "	 When \'Y\'	Then (select count(subj) from TZ_STOLD where subj=A.subj and year=A.year and subjseq=A.subjseq and isgraduated='Y') ";
				head_sql1+= " End as gradcnt1,";
				head_sql1+= " case A.isclosed	";
				head_sql1+= "	 When \'N\'   Then 0 ";
				head_sql1+= " End as gradcnt2, ";
				head_sql1+= "	 ( (case A.isclosed	When \'Y\'	Then 15		End) +  ( case A.isclosed		When \'N\'	Then 0		End )  ) tgradcnt  	";
				// 수정일 : 05.11.04 수정자 : 이나연 _ 여기까지 수정

                body_sql1+= "from VZ_SCSUBJSEQ A ";
				body_sql1+= "where 1=1 ";
                if (!ss_grcode.equals("ALL")) {
					body_sql1+= " and A.grcode = "+SQLString.Format(ss_grcode);
                }
                if (!ss_grseq.equals("ALL")) {
					body_sql1+= " and A.grseq = "+SQLString.Format(ss_grseq);
                }
                if (!ss_uclass.equals("ALL")) {
					body_sql1+= " and A.scupperclass = "+SQLString.Format(ss_uclass);
                }
                if (!ss_mclass.equals("ALL")) {
					body_sql1+= " and A.scmiddleclass = "+SQLString.Format(ss_mclass);
                }
                if (!ss_lclass.equals("ALL")) {
					body_sql1+= " and A.sclowerclass = "+SQLString.Format(ss_lclass);
                }

                if (!ss_subjcourse.equals("ALL")) {
					body_sql1+= " and A.scsubj = "+SQLString.Format(ss_subjcourse);
                }
                if (!ss_subjseq.equals("ALL")) {
					body_sql1+= " and A.scsubjseq = "+SQLString.Format(ss_subjseq);
                }
                if (!ss_edustart.equals("ALL")){
					body_sql1+= " and A.edustart >= "+SQLString.Format(ss_edustart);
                }
                if (!ss_eduend.equals("ALL")) {
					body_sql1+= " and A.eduend <= "+SQLString.Format(ss_eduend);
                }
                //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
					body_sql1+= " and A.gyear = "+SQLString.Format(ss_gyear);
                }
				//과정명 검색
				if (!v_subjnm.equals("")) {
					body_sql1+= " and A.subjnm like '%" + v_subjnm + "%' ";
				}

				if(v_orderColumn.equals("")) {
                	order_sql1+= " order by A.course,A.cyear,A.courseseq,A.subj,A.year,A.subjseq ";
				} else {
				    order_sql1+= " order by " + v_orderColumn + v_orderType;
				}
				sql1= head_sql1+ body_sql1+ group_sql1+ order_sql1;

                ls1 = connMgr.executeQuery(sql1);


				count_sql1= "select count(*) " + body_sql1;
				int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql1);

                ls1.setPageSize(row);                       //페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno, total_row_count);               //현재페이지번호를 세팅한다.
                int total_page_count = ls1.getTotalPage();  //전체 페이지 수를 반환한다

                while (ls1.next()) {
					dbox = ls1.getDataBox();

					dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
	                dbox.put("d_totalpage", new Integer(total_page_count));
	                dbox.put("d_rowcount", new Integer(row));

			        list1.add(dbox);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }

	 /**
    수료율 리스트(Excel)
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectCompleteRateExcelList(RequestBox box) throws Exception {
		PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
		// 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
	    String sql1     = "";
	    String count_sql1     = "";
	    String head_sql1 = "";
		String body_sql1 = "";
	    String group_sql1 = "";
	    String order_sql1 = "";

	    String sql2     = "";
	    String count_sql2     = "";
	    String head_sql2 = "";
		String body_sql2 = "";
	    String group_sql2 = "";
	    String order_sql2 = "";

		DataBox dbox = null;
        String  v_Bcourse   = ""; //이전코스
        String  v_course    = ""; //현재코스
        String  v_Bcourseseq= ""; //이전코스차수
        String  v_courseseq = ""; //현재코스차수

        int     l           = 0;

		String  v_subjnm	= box.getStringDefault("p_subjnm","");	 //과정명 검색
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");       //과정분류
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");      //과정분류
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");       //과정분류
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //과정 차수
        String  ss_edustart = box.getStringDefault("s_edustart","ALL");  //교육시작일
        String  ss_eduend   = box.getStringDefault("s_eduend","ALL");    //교육종료일
        String  ss_action   = box.getString("s_action");

		String  v_orderColumn   = box.getString("p_orderColumn");           	//정렬할 컬럼명
        String  v_orderType     = box.getString("p_orderType");           		//정렬할 순서

        try {
            if(ss_action.equals("go")){
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
                list2 = new ArrayList();

                //select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,
                //edustart,eduend,educnt,gradcnt1,gradcnt2,isonoff
                head_sql1 = "select A.grseq,A.course,A.cyear,A.courseseq,A.coursenm,A.subj,A.year,A.subjnm,A.subjseq,A.subjseqgr,";
				head_sql1+= "A.edustart,A.eduend,A.isonoff, A.isbelongcourse, A.subjcnt, ";
				head_sql1+= "(select count(s.subj) from TZ_STUDENT s where s.subj=A.subj and s.year=A.year and s.subjseq=A.subjseq) educnt, ";

				head_sql1+= " case A.isclosed	";
				head_sql1+= "	 When \'Y\'	Then (select count(subj) from TZ_STOLD where subj=A.subj and year=A.year and subjseq=A.subjseq and isgraduated='Y') ";
				head_sql1+= " End as gradcnt1,";
				head_sql1+= " case A.isclosed	";
				head_sql1+= "	 When \'N\'   Then 0 ";
				head_sql1+= " End as gradcnt2, ";
				head_sql1+= "	 ( (case A.isclosed	When \'Y\'	Then 15		End) +  ( case A.isclosed		When \'N\'	Then 0		End )  ) tgradcnt  	";
				// 수정일 : 05.11.04 수정자 : 이나연 _ 여기까지 수정

                body_sql1+= "from VZ_SCSUBJSEQ A ";
				body_sql1+= "where 1=1 ";
                if (!ss_grcode.equals("ALL")) {
					body_sql1+= " and A.grcode = "+SQLString.Format(ss_grcode);
                }
                if (!ss_grseq.equals("ALL")) {
					body_sql1+= " and A.grseq = "+SQLString.Format(ss_grseq);
                }
                if (!ss_uclass.equals("ALL")) {
					body_sql1+= " and A.scupperclass = "+SQLString.Format(ss_uclass);
                }
                if (!ss_mclass.equals("ALL")) {
					body_sql1+= " and A.scmiddleclass = "+SQLString.Format(ss_mclass);
                }
                if (!ss_lclass.equals("ALL")) {
					body_sql1+= " and A.sclowerclass = "+SQLString.Format(ss_lclass);
                }

                if (!ss_subjcourse.equals("ALL")) {
					body_sql1+= " and A.scsubj = "+SQLString.Format(ss_subjcourse);
                }
                if (!ss_subjseq.equals("ALL")) {
					body_sql1+= " and A.scsubjseq = "+SQLString.Format(ss_subjseq);
                }
                if (!ss_edustart.equals("ALL")){
					body_sql1+= " and A.edustart >= "+SQLString.Format(ss_edustart);
                }
                if (!ss_eduend.equals("ALL")) {
					body_sql1+= " and A.eduend <= "+SQLString.Format(ss_eduend);
                }
                //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
					body_sql1+= " and A.gyear = "+SQLString.Format(ss_gyear);
                }
				//과정명 검색
				if (!v_subjnm.equals("")) {
					body_sql1+= " and A.subjnm like '%" + v_subjnm + "%' ";
				}

				if(v_orderColumn.equals("")) {
                	order_sql1+= " order by A.course,A.cyear,A.courseseq,A.subj,A.year,A.subjseq ";
				} else {
				    order_sql1+= " order by A.course," + v_orderColumn + v_orderType;
				}
				sql1= head_sql1+ body_sql1+ group_sql1+ order_sql1;

                ls1 = connMgr.executeQuery(sql1);


                while (ls1.next()) {
					dbox = ls1.getDataBox();

			        list1.add(dbox);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }

    /**
    고용보험환급명단 리스트
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectHiringInsuranceReturnedList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
        String sql1         = "";
        String sql2         = "";
        CompleteStatusData data1=null;
        CompleteStatusData data2=null;
        String  v_Bcourse   = ""; //이전코스
        String  v_course    = ""; //현재코스
        String  v_Bcourseseq= ""; //이전코스차수
        String  v_courseseq = ""; //현재코스차수
        int     l           = 0;
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");       //교육그룹
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");        //년도
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");        //교육차수
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //과정분류
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //과정분류
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //과정분류
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");   //과정&코스
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");      //과정 차수
        String  ss_company  = box.getStringDefault("s_company","ALL");      //회사
        String  ss_selgubun = box.getString("s_selgubun");                  //직군별:JIKUN,직급별:JIKUP,사업부별:GPM
        String  ss_seltext  = box.getStringDefault("s_seltext","ALL");      //검색분류별 검색내용
        String  ss_seldept  = box.getStringDefault("s_seldept","ALL");      //사업부별 부서 검색내용
        String  ss_action   = box.getString("s_action");
        String  v_orderColumn= box.getString("p_orderColumn");              //정렬할 컬럼명

        try {
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list2;
    }

    /**
    폼메일 발송
    @param box      receive from the form object and session
    @return int
    */
    public int sendFormMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        ListSet ls = null;
        String sql = "";
        int cnt = 0;    //  메일발송이 성공한 사람수
        //p_checks로 넘어온 다수의 value를 처리하기 위해 vector로 구현
        Vector v_check1     = new Vector();
        v_check1            = box.getVector("p_checks");
        Enumeration em1     = v_check1.elements();
        StringTokenizer st1 = null;
        String v_checks     = "";
        String v_userid     = "";
        String v_subj       = "";
        String v_year       = "";
        String v_subjseq    = "";

        try {
            connMgr = new DBConnectionManager();

////////////////////  폼메일 발송 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String v_sendhtml = "mail3.html";
            FormMail fmail = new FormMail(v_sendhtml);     //      폼메일발송인 경우
            MailSet mset = new MailSet(box);        //      메일 세팅 및 발송
            String v_mailTitle = "안녕하세요? 운영자입니다.(진도율안내)";
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            while(em1.hasMoreElements()){
                v_checks    = (String)em1.nextElement();
                st1      = new StringTokenizer(v_checks,",");
                while (st1.hasMoreElements()) {
                    v_userid    = (String)st1.nextToken();
                    v_subj      = (String)st1.nextToken();
                    v_year      = (String)st1.nextToken();
                    v_subjseq   = (String)st1.nextToken();
					break;
                }
                //select subjnm,passday,tstep,gradstep,gradscore,name,ismailing,cono,email
                sql = "select  B.subjnm,A.tstep,B.gradstep,B.sgradscore gradscore,D.name,D.ismailing,D.cono,D.email,";
                sql+= "(to_number(to_char(sysdate, 'YYYYMMDD')) - to_number(SUBSTR(B.edustart,1,8))) passday ";
                sql+= " from TZ_STUDENT A,VZ_SCSUBJSEQ B,TZ_MEMBER D ";
                sql+= " where A.userid = "+SQLString.Format(v_userid);
                sql+= " and A.subj = "+SQLString.Format(v_subj);
                sql+= " and A.year = "+SQLString.Format(v_year);
                sql+= " and A.subjseq = "+SQLString.Format(v_subjseq);
                sql+= " and A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq and A.userid=D.userid ";
                sql+= " group by B.subjnm,A.tstep,B.gradstep,B.sgradscore,D.name,D.ismailing,D.cono,D.email,B.edustart,B.eduend ";
                ls = connMgr.executeQuery(sql);
//                System.out.println("sql=========>"+sql);

                while (ls.next()) {
                    String v_toEmail =  ls.getString("email");
                    String v_toCono  =  ls.getString("cono");
                    String v_ismailing= ls.getString("ismailing");


                    mset.setSender(fmail);     //메일보내는 사람 세팅

                    fmail.setVariable("tstep", ls.getString("tstep"));
                    fmail.setVariable("subjnm", ls.getString("subjnm"));
                    fmail.setVariable("passday", ls.getString("passday"));
                    fmail.setVariable("tstep", ls.getString("tstep"));
                    fmail.setVariable("gradstep", ls.getString("gradstep"));
                    fmail.setVariable("gradscore", ls.getString("gradscore"));
                    fmail.setVariable("toname", ls.getString("name"));

                    String v_mailContent = fmail.getNewMailContent();

                    boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent,v_ismailing, v_sendhtml);
                    if(isMailed) cnt++;     //      메일발송에 성공하면
                }
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

        return cnt;
    }

}