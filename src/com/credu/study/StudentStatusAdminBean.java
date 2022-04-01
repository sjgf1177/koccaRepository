//**********************************************************
//  1. 제      목: STUDENT STATUS ADMIN BEAN
//  2. 프로그램명: StudentStatusAdminBean.java
//  3. 개      요: 입과 현황 관리자 bean
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성:
//**********************************************************
package com.credu.study;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormMail;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.MailSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.propose.ProposeBean;
import com.dunet.common.util.Constants;
import com.dunet.common.util.EncryptUtil;

@SuppressWarnings("unchecked")
public class StudentStatusAdminBean {
	private ConfigSet config;
	private int row;

	public StudentStatusAdminBean() {
		try{
			config = new ConfigSet();
			row = Integer.parseInt(config.getProperty("page.manage.row"));  //이 모듈의 페이지당 row 수를 셋팅한다
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
    학습진행자 명단 리스트
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public ArrayList selectStudentMemberList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		DataBox dbox        = null;
		ListSet ls1         = null;
		ListSet ls2         = null;
		ArrayList list1     = null;
		// 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
		String sql1     = "";
		String head_sql1 = "";
		String body_sql1 = "";
		String group_sql1 = "";
		String order_sql1 = "";
		String count_sql1 ="";

		String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //교육그룹
		String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //년도
		String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수

		String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");    //과정분류
		String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");   //과정분류
		String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");    //과정분류

		String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스

		String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //과정 차수
		String  ss_company  = box.getStringDefault("s_company","ALL");   //회사
		String  ss_usebook  = box.getStringDefault("s_usebook","ALL");   //교재

		String  ss_user     = box.getStringDefault("s_user","ALL");    //ID 또는 이름 2009.10.27

		String  ss_edustart = box.getStringDefault("s_edustart","ALL");  //교육시작일
		String  ss_eduend   = box.getStringDefault("s_eduend","ALL");    //교육종료일

		String  ss_action   = box.getString("s_action");
		String  v_orderColumn= box.getString("p_orderColumn");           //정렬할 컬럼명
		String  v_orderType     = box.getString("p_orderType");          //정렬할 순서
		int    v_pageno    = box.getInt("p_pageno");
		int    v_pagesize  = box.getInt("p_pagesize");

		ProposeBean probean = new ProposeBean();
		Hashtable outdata = new Hashtable();

		try {
			EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);
			if(ss_action.equals("go")){
				connMgr = new DBConnectionManager();
				list1 = new ArrayList();

				head_sql1 = " select  C.grseq, C.course, C.cyear, C.courseseq, C.coursenm, C.subj, C.year, C.subjnm, C.subjseq, C.subjseqgr,         \n";
				head_sql1+= "         B.userid, B.name,crypto.dec('normal',B.email) email, crypto.dec('normal',B.handphone) handphone, \n";
				head_sql1+= "         A.tstep, A.avtstep,";
				head_sql1+= "         A.mtest, A.avmtest,";
				head_sql1+= "         A.ftest, A.avftest,";
				head_sql1+= "         A.report, A.avreport,";
				head_sql1+= "         A.htest, A.avhtest,";
				head_sql1+= "         A.etc1, A.avetc1,";
				head_sql1+= "         A.etc2, A.avetc2,";
				head_sql1+= "         A.score,";
				head_sql1+= "         C.isonoff, \n";
				head_sql1+= "         (select grseqnm from tz_grseq where grcode=c.grcode and gyear=c.gyear and grseq = c.grseq) grseqnm ,           \n";
				head_sql1+= "			case when  B.membergubun = 'P' then  '개인'	when  B.membergubun = 'C' then  '기업' 							 \n";
				head_sql1+= " 		  when  B.membergubun = 'U' then  '대학교'  else '-' end   as membergubunnm ,c.isbelongcourse, c.subjcnt,		\n ";
				head_sql1+= " 		  b.resno, b.post1, b.post2, b.addr, b.addr2, b.comp_post1, b.comp_post2, b.comp_addr1, b.comp_addr2, 			\n ";
				head_sql1+= " 		  crypto.dec('normal',b.hometel) hometel, b.comptel, B.jikup, get_jikupnm(B.jikup,B.comp, jikupnm) jikupnm, b.degree, b.ismailing		\n ";
				body_sql1+= " from TZ_STUDENT A, TZ_MEMBER B, VZ_SCSUBJSEQ C                                                                         \n";
				body_sql1+= " where A.userid=B.userid                                                                                                \n";
				body_sql1+= "   and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq                                                          \n";
				//sql1+= "   and C.eduend>to_char(sysdate,'YYYYMMDDHH24')";
				//              sql1+= " and  A.isgraduated='N' ";
				if (!ss_grcode.equals("ALL")){
					body_sql1+= " and C.grcode = "+SQLString.Format(ss_grcode);
					body_sql1+= " and B.grcode = "+SQLString.Format(ss_grcode);
				}
				if (!ss_grseq.equals("ALL"))      body_sql1+= " and C.grseq = "+SQLString.Format(ss_grseq);
				if (!ss_uclass.equals("ALL"))     body_sql1+= " and C.scupperclass = "+SQLString.Format(ss_uclass);
				if (!ss_mclass.equals("ALL"))     body_sql1+= " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
				if (!ss_lclass.equals("ALL"))     body_sql1+= " and C.sclowerclass = "+SQLString.Format(ss_lclass);
				if (!ss_subjcourse.equals("ALL")) body_sql1+= " and C.scsubj = "+SQLString.Format(ss_subjcourse);
				if (!ss_subjseq.equals("ALL"))    body_sql1+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
				if (!ss_company.equals("ALL"))    body_sql1+= " and substring(B.comp,1,4) = '"+StringManager.substring(ss_company, 0, 4)+"'";
				if (!ss_usebook.equals("ALL"))    body_sql1+= " and C.usebook = "+SQLString.Format(ss_usebook);
				if (!ss_user.equals("ALL"))       body_sql1+= " and (B.userid like "+ StringManager.makeSQL("%" + ss_user + "%")+" or B.name like "+ StringManager.makeSQL("%" + ss_user + "%")+")";
				if (v_orderColumn.equals("grseq"))   v_orderColumn = "C.grseq";
				if (v_orderColumn.equals("grseqnm")) v_orderColumn = "grseqnm";
				if (v_orderColumn.equals("subj"))    v_orderColumn = "C.subj";
				if (v_orderColumn.equals("subjnm"))  v_orderColumn = "C.subjnm";
				if (v_orderColumn.equals("compnm1")) v_orderColumn = "get_compnm(b.comp,2,2)";
				if (v_orderColumn.equals("compnm2")) v_orderColumn = "get_deptnm(B.deptnam,'')";
				if (v_orderColumn.equals("userid"))  v_orderColumn = "b.userid ";
				if (v_orderColumn.equals("name"))    v_orderColumn = "b.name ";
				if (v_orderColumn.equals("jiknm"))   v_orderColumn = "get_jikwinm(b.jikwi,b.comp)";
				if (v_orderColumn.equals("membergubunnm"))    v_orderColumn = "membergubunnm ";

				//교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
				if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
					body_sql1+= " and C.gyear = "+SQLString.Format(ss_gyear);
				}

				if(v_orderColumn.equals("")) {	//반드시 C.course 이 첫번째로 오도록한다- jsp 출력과 연관
					order_sql1+= " order by  C.course , c.courseseq , name , c.subj, c.subjseq  ";
				} else {
					order_sql1+= " order by "+ v_orderColumn + v_orderType +" , C.course , c.courseseq , name  , c.subj, c.subjseq ";
				}

				sql1 = head_sql1+ body_sql1+ group_sql1+ order_sql1 ;

				ls1 = connMgr.executeQuery(sql1);
				Log.info.println(" 학습현황 입과명단: "+sql1);

				count_sql1 = "select count(*) " + body_sql1;
				int totalrowcount = BoardPaging.getTotalRow(connMgr, count_sql1) ;
				//System.out.println(row+"#######################");

				ls1.setPageSize(v_pagesize);                   	//페이지당 row 갯수를 세팅한다
				ls1.setCurrentPage(v_pageno, totalrowcount);    //현재페이지번호를 세팅한다.
				int total_page_count = ls1.getTotalPage();  	//전체 페이지 수를 반환한다


				//int total_row_count = ls1.getTotalCount();  	//전체 row 수를 반환한다

				while (ls1.next()) {
					dbox = ls1.getDataBox();

					dbox.put("d_dispnum",		new Integer(totalrowcount - ls1.getRowNum() + 1));
					dbox.put("d_totalpage",		new Integer(total_page_count));
					dbox.put("d_rowcount",  	new Integer(row));
					dbox.put("d_totalrowcount",	new Integer(totalrowcount));
					
					//if (!dbox.getString("d_handphone").equals("")) dbox.put("d_handphone", encryptUtil.decrypt(dbox.getString("d_handphone")));

					outdata.clear();
					outdata = probean.getMeberInfo(ls1.getString("userid"));
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
    학습완료자 명단 리스트
    @param box      receive from the form object and session
    @return ArrayList
	 */
	public ArrayList selectCompleteMemberList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		DataBox dbox        = null;
		ListSet ls1         = null;
		ListSet ls2         = null;
		ArrayList list1     = null;
		// 수정일 : 05.11.11 수정자 : 이나연 _ TotalCount 해주기 위한 쿼리를 담을 변수
		String sql2     = "";
		String count_sql2    = "";
		String head_sql2 = "";
		String body_sql2 = "";
		String group_sql2 = "";
		String order_sql2 = "";

		String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //교육그룹
		String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //년도
		String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //교육차수

		String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");    //과정분류
		String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");    //과정분류
		String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");    //과정분류

		String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//과정&코스
		String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //과정 차수
		String  ss_company  = box.getStringDefault("s_company","ALL");   //회사
		String  ss_edustart = box.getStringDefault("s_edustart","ALL");  //교육시작일
		String  ss_eduend   = box.getStringDefault("s_eduend","ALL");    //교육종료일

		String  ss_user     = box.getStringDefault("s_user","ALL");      //ID 또는 이름 2009.10.27

		String  ss_action   = box.getString("s_action");
		String  v_orderColumn= box.getString("p_orderColumn");           //정렬할 컬럼명
		String  v_orderType     = box.getString("p_orderType");          //정렬할 순서

		int    v_pageno   = box.getInt("p_pageno");
		int    v_pagesize = box.getInt("p_pagesize");

		ProposeBean probean = new ProposeBean();
		Hashtable outdata = new Hashtable();

		try {
			if(ss_action.equals("go")){
				connMgr = new DBConnectionManager();
				list1 = new ArrayList();

				//select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,compnm,
				//jikwinm,userid,cono,name,isGraduated,avmtest,avftest,mtest,ftest,score,email,ismailing,isonoff
				head_sql2 = "select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,C.subjseqgr, \n";
				head_sql2+= "        B.userid,B.name,B.email, B.handphone,A.isGraduated,  \n";
				//if(GetCodenm.get_config("score_disp").equals("WS")){    //가중치적용
				//	head_sql2+= "A.avmtest mtest,A.avftest ftest,  \n";
				//}else{                                                  //가중치비적용
				//	head_sql2+= "A.mtest mtest,A.ftest ftest,  \n";
				//}
				head_sql2+= "         A.tstep, A.avtstep,";
				head_sql2+= "         A.mtest, A.avmtest,";
				head_sql2+= "         A.ftest, A.avftest,";
				head_sql2+= "         A.report, A.avreport,";
				head_sql2+= "         A.htest, A.avhtest,";
				head_sql2+= "         A.etc1, A.avetc1,";
				head_sql2+= "         A.etc2, A.avetc2,";
				head_sql2+= "         A.score,C.isonoff,  \n";
				head_sql2+= "        (select grseqnm from tz_grseq where grcode=c.grcode and gyear=c.gyear and grseq = c.grseq) grseqnm,  \n";
				//sql1+= "from TZ_STUDENT A,TZ_MEMBER B,VZ_SCSUBJSEQ C where //C.eduend<to_char(sysdate,'YYYYMMDDHH24') ";
				head_sql2+= "  		case when  B.membergubun = 'P' then  '개인'	when  B.membergubun = 'C' then  '기업' 	 \n";
				head_sql2+= " 		 when  B.membergubun = 'U' then  '대학교'  else '-' end   as membergubunnm , c.isbelongcourse, c.subjcnt, \n";
				head_sql2+= " 		  b.resno, b.post1, b.post2, b.addr, b.addr2, b.comp_post1, b.comp_post2, b.comp_addr1, b.comp_addr2, 	  \n ";
				head_sql2+= " 		  b.hometel, b.comptel, B.jikup, get_jikupnm(B.jikup,B.comp, jikupnm) jikupnm, b.degree, b.ismailing \n ";
				body_sql2+= "   from TZ_STUDENT A,TZ_MEMBER B,VZ_SCSUBJSEQ C  \n";
				body_sql2+= "  where  c.isclosed = 'Y'  \n";
				body_sql2+= " and A.userid=B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq  \n";

				if (!ss_grcode.equals("ALL"))     body_sql2+= " and C.grcode = "+SQLString.Format(ss_grcode);
				if (!ss_grseq.equals("ALL"))      body_sql2+= " and C.grseq = "+SQLString.Format(ss_grseq);
				if (!ss_uclass.equals("ALL"))     body_sql2+= " and C.scupperclass = "+SQLString.Format(ss_uclass);
				if (!ss_mclass.equals("ALL"))     body_sql2+= " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
				if (!ss_lclass.equals("ALL"))     body_sql2+= " and C.sclowerclass = "+SQLString.Format(ss_lclass);
				if (!ss_subjcourse.equals("ALL")) body_sql2+= " and C.scsubj = "+SQLString.Format(ss_subjcourse);
				if (!ss_subjseq.equals("ALL"))    body_sql2+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
				if (!ss_company.equals("ALL"))    body_sql2+= " and substring(B.comp,1,4) = '"+StringManager.substring(ss_company, 0, 4)+"'";
				if (!ss_user.equals("ALL"))       body_sql2+= " and (B.userid like "+ StringManager.makeSQL("%" + ss_user + "%")+" or B.name like "+ StringManager.makeSQL("%" + ss_user + "%")+")";
				//if (!ss_edustart.equals("ALL")){  sql1+= " and C.edustart >= "+SQLString.Format(ss_edustart);
				//if (!ss_eduend.equals("ALL")) {   sql1+= " and C.eduend <= "+SQLString.Format(ss_eduend);

				//교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
				if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
					body_sql2+= " and C.gyear = "+SQLString.Format(ss_gyear);
				}

				if (v_orderColumn.equals("grseq"))   v_orderColumn = "C.grseq  		\n";
				if (v_orderColumn.equals("subj"))    v_orderColumn = "C.subj  		\n";
				if (v_orderColumn.equals("compnm1")) v_orderColumn = "get_compnm(b.comp,2,2) 	 \n";
				if (v_orderColumn.equals("compnm2")) v_orderColumn = "get_deptnm(b.deptnam,'')  \n";
				if (v_orderColumn.equals("userid"))  v_orderColumn = "b.userid   	\n";
				if (v_orderColumn.equals("name"))    v_orderColumn = "b.name  		\n";
				if (v_orderColumn.equals("jiknm"))   v_orderColumn = "get_jikwinm(b.jikwi,b.comp) \n";

				if (v_orderColumn.equals("grseqnm")) v_orderColumn = "grseqnm";
				if (v_orderColumn.equals("subjnm"))  v_orderColumn = "C.subjnm";
				if (v_orderColumn.equals("membergubunnm"))    v_orderColumn = "membergubunnm ";


				if(v_orderColumn.equals("")) {
					order_sql2+= " order by C.course , C.courseseq, B.name , C.subj,  C.subjseq  ";
				} else {
					order_sql2+= " order by " + v_orderColumn + v_orderType + ", C.course , C.courseseq, B.name ";
				}

				sql2= head_sql2+ body_sql2+ group_sql2+ order_sql2;

				System.out.println(" selectCompleteMemberList학습완료자 명단 리스트2:"+sql2);
				ls1 = connMgr.executeQuery(sql2);

				count_sql2= "select count(*) " + body_sql2;
				int totalrowcount = BoardPaging.getTotalRow(connMgr, count_sql2);

				//ls1.setPageSize(row);                       	//페이지당 row 갯수를 세팅한다
				ls1.setPageSize(v_pagesize);                   	//페이지당 row 갯수를 세팅한다
				ls1.setCurrentPage(v_pageno, totalrowcount);    //현재페이지번호를 세팅한다.
				int total_page_count = ls1.getTotalPage();  	//전체 페이지 수를 반환한다

				while (ls1.next()) {
					dbox = ls1.getDataBox();
					outdata.clear();
					outdata = probean.getMeberInfo(ls1.getString("userid"));
					dbox.put("d_workplc",   outdata.get("work_plcnm"));
					dbox.put("d_deptnam",   outdata.get("deptnam"));
					dbox.put("d_officegbn", outdata.get("officegbn"));
					dbox.put("d_gubuntxt",  outdata.get("gubuntxt"));

					dbox.put("d_dispnum",		new Integer(totalrowcount - ls1.getRowNum() + 1));
					dbox.put("d_totalpage",		new Integer(total_page_count));
					dbox.put("d_rowcount",  	new Integer(row));
					dbox.put("d_totalrowcount",	new Integer(totalrowcount));

					list1.add(dbox);
				}
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql2);
			throw new Exception("sql2 = " + sql2 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
			if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list1;
	}

	/**
    입과 인원 조회 리스트
    @param box      receive from the form object and session
    @return ArrayList 입과인원 리스트
	 */
	public ArrayList selectStudentMemberCountList(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls1         = null;
		ListSet ls2         = null;
		ArrayList list1     = null;
		String sql3     = "";
		String count_sql3     = "";
		String head_sql3 = "";
		String head_sql3_2 = "";
		String body_sql3 = "";
		String order_sql3 = "";
		StudentStatusData data1=null;

		int v_pageno = box.getInt("p_pageno");

		String  ss_grcode     = box.getStringDefault("s_grcode","ALL");    //교육그룹
		String  ss_gyear      = box.getStringDefault("s_gyear","ALL");     //년도
		String  ss_grseq      = box.getStringDefault("s_grseq","ALL");     //교육차수

		String  ss_uclass     = box.getStringDefault("s_upperclass","ALL");    //과정분류
		String  ss_mclass     = box.getStringDefault("s_middleclass","ALL");    //과정분류
		String  ss_lclass     = box.getStringDefault("s_lowerclass","ALL");    //과정분류

		String  ss_subjcourse =box.getStringDefault("s_subjcourse","ALL");//과정&코스
		String  ss_subjseq    = box.getStringDefault("s_subjseq","ALL");   //과정 차수
		String  ss_action     = box.getString("s_action");
		String  v_orderColumn= box.getString("p_orderColumn");           //정렬할 컬럼명
		String  v_orderType     = box.getString("p_orderType");          //정렬할 순서
		String  v_selTab      = box.getString("p_selTab");               //선택된 탭

		//System.out.println("v_seltab===========>"+v_selTab);

		try {
			if(ss_action.equals("go")){
				connMgr = new DBConnectionManager();
				list1 = new ArrayList();

				/*
                //select grseq,course,cyear,coursenm,courseseq,subj,year,subjnm,subjseq,
                //propstart,propend,edustart,eduend,studentlimit,procnt,cancnt,isonoff
                head_sql3 = " select grseq,course,cyear,coursenm,courseseq,subj,year,subjnm,subjseq,subjseqgr,";
				head_sql3+= "        propstart,propend,edustart,eduend,studentlimit, ";
				head_sql3+= "        (select count(subj) from TZ_STUDENT where subj=B.subj and year=B.year and subjseq=B.subjseq ";
				head_sql3+= "                                              and isGraduated in ('N')) stucnt, ";
				head_sql3+= "        (select count(subj) from TZ_STUDENT where subj=B.subj and year=B.year and subjseq=B.subjseq ";
				head_sql3+= "                                              and isGraduated in ('Y')) comcnt, ";
				head_sql3+= "        (select count(subj) from TZ_CANCEL where subj=B.subj and year=B.year and subjseq=B.subjseq ) cancnt, ";
				head_sql3+= "        (select count(subj) from TZ_PROPOSE where subj=B.subj and year=B.year and subjseq=B.subjseq ) procnt, ";
				head_sql3+= "        (select count(subj) from TZ_Student where subj=B.subj and year=B.year and subjseq=B.subjseq  ) proycnt, ";
				head_sql3+= "        (select grseqnm from tz_grseq where grcode=B.grcode and gyear=B.gyear and grseq = B.grseq) grseqnm,    ";
				head_sql3+= "        isonoff , isbelongcourse, subjcnt  ";
                body_sql3+= "   from VZ_SCSUBJSEQ B   ";
				body_sql3+= "  where B.gyear = "+SQLString.Format(ss_gyear);

                if (!ss_grcode.equals("ALL"))     body_sql3+= " and B.grcode = "+SQLString.Format(ss_grcode);
                if (!ss_grseq.equals("ALL"))      body_sql3+= " and B.grseq = "+SQLString.Format(ss_grseq);
                if (!ss_uclass.equals("ALL"))     body_sql3+= " and B.scupperclass = "+SQLString.Format(ss_uclass);
                if (!ss_mclass.equals("ALL"))     body_sql3+= " and B.scmiddleclass = "+SQLString.Format(ss_mclass);
                if (!ss_lclass.equals("ALL"))     body_sql3+= " and B.sclowerclass = "+SQLString.Format(ss_lclass);
                if (!ss_subjcourse.equals("ALL")) body_sql3+= " and B.scsubj = "+SQLString.Format(ss_subjcourse);
                if (!ss_subjseq.equals("ALL"))    body_sql3+= " and B.scsubjseq = "+SQLString.Format(ss_subjseq);


                //학습대기중인과정선택시
                if(v_selTab.equals("wait")){
					body_sql3+= " and B.edustart >= '"+FormatDate.getDate("yyyyMMddHH")+"'";
                }
                //학습진행중인과정선택시
                else if(v_selTab.equals("progress")){
					body_sql3+= " and B.edustart < '"+FormatDate.getDate("yyyyMMddHH")+"'";
					body_sql3+= " and B.eduend >= '"+FormatDate.getDate("yyyyMMddHH")+"'";
                }
                //학습완료한과정선택시
                else if(v_selTab.equals("finish")){
					body_sql3+= " and B.eduend < '"+FormatDate.getDate("yyyyMMddHH")+"'";
                }

                //교육시작일과 종료일이 선택되지 않은 경우에만 교육년도별도 검색함
                //if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
                //    sql1+= " and B.gyear = "+SQLString.Format(ss_gyear);
                //}
                //sql1+= " and userid=A.userid and subj=B.subj and year=B.year and subjseq=B.subjseq ";

                if (v_orderColumn.equals("grseq"))   v_orderColumn = "b.grseq";
                if (v_orderColumn.equals("subj"))    v_orderColumn = "b.subj";
                if (v_orderColumn.equals("subjseq")) v_orderColumn = "b.subjseqgr";
                if (v_orderColumn.equals("isonoff")) v_orderColumn = "b.isonoff";

                if(v_orderColumn.equals("")) {
                    order_sql3+= " order by  course, subj, year, subjseq ";
                } else {
                    order_sql3+= " order by  course, " + v_orderColumn + v_orderType;
                }

				 */

				//2009.11.06 쿼리속도 향상 수정
				head_sql3 = " SELECT   grseq, course, cyear, coursenm, courseseq, subj, YEAR, subjnm, \n";
				head_sql3+= "         subjseq, subjseqgr, propstart, propend, edustart, eduend, \n";
				head_sql3+= "         studentlimit, stucnt, comcnt, cancnt, procnt, proycnt, \n";
				head_sql3+= "         (SELECT grseqnm \n";
				head_sql3+= "            FROM TZ_GRSEQ \n";
				head_sql3+= "           WHERE grcode = T.grcode \n";
				head_sql3+= "             AND gyear = T.gyear \n";
				head_sql3+= "             AND grseq = T.grseq) grseqnm, \n";
				head_sql3+= "         isonoff, isbelongcourse, subjcnt \n";
				head_sql3+= "    FROM (SELECT   B.grseq, B.course, B.cyear, B.coursenm, B.courseseq, \n";
				head_sql3+= "                   B.subj, B.YEAR, B.subjnm, B.subjseq, B.subjseqgr, ";
				head_sql3+= "                   B.propstart, B.propend, B.edustart, B.eduend, ";
				head_sql3+= "                   B.studentlimit, B.gyear, B.grcode, ";
				head_sql3+= "                   NVL (SUM (stucnt), 0) stucnt, NVL (SUM (comcnt), 0) comcnt, ";
				head_sql3+= "                   NVL (SUM (proycnt), 0) proycnt, ";
				head_sql3+= "                   NVL (SUM (cancnt), 0) cancnt, NVL (SUM (procnt), 0) procnt, ";
				head_sql3+= "                   B.isonoff, B.isbelongcourse, B.subjcnt ";
				head_sql3+= "              FROM VZ_SCSUBJSEQ B, ";
				head_sql3+= "                   (SELECT subj, YEAR, subjseq, ";
				head_sql3+= "                           CASE ";
				head_sql3+= "                              WHEN isgraduated = 'N' ";
				head_sql3+= "                                 THEN 1 ";
				head_sql3+= "                              ELSE 0 ";
				head_sql3+= "                           END stucnt, ";
				head_sql3+= "                           CASE ";
				head_sql3+= "                              WHEN isgraduated = 'Y' ";
				head_sql3+= "                                 THEN 1 ";
				head_sql3+= "                              ELSE 0 ";
				head_sql3+= "                           END comcnt, 1 proycnt, 0 cancnt, 0 procnt ";
				head_sql3+= "                      FROM TZ_STUDENT ";
				head_sql3+= "                     WHERE YEAR = "+SQLString.Format(ss_gyear);
				head_sql3+= "                    UNION ALL ";
				head_sql3+= "                    SELECT subj, YEAR, subjseq, 0 stucnt, 0 comcnt, 0 proycnt, ";
				head_sql3+= "                           1 cancnt, 0 procnt ";
				head_sql3+= "                      FROM TZ_CANCEL ";
				head_sql3+= "                     WHERE YEAR = "+SQLString.Format(ss_gyear);
				head_sql3+= "                    UNION ALL ";
				head_sql3+= "                    SELECT subj, YEAR, subjseq, 0 stucnt, 0 comcnt, 0 proycnt, ";
				head_sql3+= "                           0 cancnt, 1 procnt ";
				head_sql3+= "                      FROM TZ_PROPOSE ";
				head_sql3+= "                     WHERE YEAR = "+SQLString.Format(ss_gyear)+") C ";
				body_sql3+= "             WHERE B.gyear = "+SQLString.Format(ss_gyear);


				if (!ss_grcode.equals("ALL"))     body_sql3+= " AND B.grcode = "+SQLString.Format(ss_grcode);
				if (!ss_grseq.equals("ALL"))      body_sql3+= " AND B.grseq = "+SQLString.Format(ss_grseq);
				if (!ss_uclass.equals("ALL"))     body_sql3+= " AND B.scupperclass = "+SQLString.Format(ss_uclass);
				if (!ss_mclass.equals("ALL"))     body_sql3+= " AND B.scmiddleclass = "+SQLString.Format(ss_mclass);
				if (!ss_lclass.equals("ALL"))     body_sql3+= " AND B.sclowerclass = "+SQLString.Format(ss_lclass);
				if (!ss_subjcourse.equals("ALL")) body_sql3+= " AND B.scsubj = "+SQLString.Format(ss_subjcourse);
				if (!ss_subjseq.equals("ALL"))    body_sql3+= " AND B.scsubjseq = "+SQLString.Format(ss_subjseq);

				//학습대기중인과정선택시
				if(v_selTab.equals("wait")){
					body_sql3+= " AND B.edustart >= '"+FormatDate.getDate("yyyyMMddHH")+"'";
				}
				//학습진행중인과정선택시
				else if(v_selTab.equals("progress")){
					body_sql3+= " AND B.edustart < '"+FormatDate.getDate("yyyyMMddHH")+"'";
					body_sql3+= " AND B.eduend >= '"+FormatDate.getDate("yyyyMMddHH")+"'";
				}
				//학습완료한과정선택시
				else if(v_selTab.equals("finish")){
					body_sql3+= " AND B.eduend < '"+FormatDate.getDate("yyyyMMddHH")+"'";
				}

				head_sql3_2 = "               AND B.subj = C.subj(+) ";
				head_sql3_2+= "               AND B.YEAR = C.YEAR(+) ";
				head_sql3_2+= "               AND B.subjseq = C.subjseq(+) ";
				head_sql3_2+= "          GROUP BY B.course, ";
				head_sql3_2+= "                   B.subj, ";
				head_sql3_2+= "                   B.YEAR, ";
				head_sql3_2+= "                   B.subjseq, ";
				head_sql3_2+= "                   B.grseq, ";
				head_sql3_2+= "                   B.cyear, ";
				head_sql3_2+= "                   B.coursenm, ";
				head_sql3_2+= "                   B.courseseq, ";
				head_sql3_2+= "                   B.subjnm, ";
				head_sql3_2+= "                   B.subjseqgr, ";
				head_sql3_2+= "                   B.propstart, ";
				head_sql3_2+= "                   B.propend, ";
				head_sql3_2+= "                   B.edustart, ";
				head_sql3_2+= "                   B.eduend, ";
				head_sql3_2+= "                   B.studentlimit, ";
				head_sql3_2+= "                   B.gyear, ";
				head_sql3_2+= "                   B.grcode, ";
				head_sql3_2+= "                   B.isonoff, ";
				head_sql3_2+= "                   B.isbelongcourse, ";
				head_sql3_2+= "                   B.subjcnt) T ";
				//head_sql3+= "ORDER BY course, subj, YEAR, subjseq ";

				if (v_orderColumn.equals("grseq"))   v_orderColumn = "grseq";
				if (v_orderColumn.equals("subj"))    v_orderColumn = "subj";
				if (v_orderColumn.equals("subjseq")) v_orderColumn = "subjseqgr";
				if (v_orderColumn.equals("isonoff")) v_orderColumn = "isonoff";

				if(v_orderColumn.equals("")) {
					order_sql3+= " order by  course, subj, year, subjseq ";
				} else {
					order_sql3+= " order by  course, " + v_orderColumn + v_orderType;
				}

				//sql3= head_sql3+ body_sql3+ group_sql3+ order_sql3;
				sql3= head_sql3+ body_sql3+ head_sql3_2+ order_sql3;

				Log.info.println("입과 인원 = "+sql3);
				ls1 = connMgr.executeQuery(sql3);

				//count_sql3= "select count(*) "+ body_sql3 ;
				count_sql3= "select count(*) FROM VZ_SCSUBJSEQ B "+ body_sql3 ;
				//System.out.println(" count_sql3 : "+ count_sql3);
				int totalrowcount = BoardPaging.getTotalRow(connMgr, count_sql3);

				ls1.setPageSize(row);                       	//페이지당 row 갯수를 세팅한다
				ls1.setCurrentPage(v_pageno, totalrowcount);    //현재페이지번호를 세팅한다.
				int total_page_count = ls1.getTotalPage();  	//전체 페이지 수를 반환한다
				int total_row_count = ls1.getTotalCount();  	//전체 row 수를 반환한다

				while (ls1.next()) {
					data1=new StudentStatusData();
					data1.setGrseq(ls1.getString("grseq"));
					data1.setGrseqnm(ls1.getString("grseqnm"));
					data1.setCourse(ls1.getString("course"));
					data1.setCyear(ls1.getString("cyear"));
					data1.setCourseseq(ls1.getString("courseseq"));
					data1.setCoursenm(ls1.getString("coursenm"));
					data1.setSubj(ls1.getString("subj"));
					data1.setYear(ls1.getString("year"));
					data1.setSubjseq(ls1.getString("subjseq"));
					data1.setSubjseqgr(ls1.getString("subjseqgr"));
					data1.setSubjnm(ls1.getString("subjnm"));
					data1.setPropstart(ls1.getString("propstart"));
					data1.setPropend(ls1.getString("propend"));
					data1.setEdustart(ls1.getString("edustart"));
					data1.setEduend(ls1.getString("eduend"));
					data1.setStudentlimit(ls1.getInt("studentlimit"));
					data1.setStucnt(ls1.getInt("stucnt"));
					data1.setProcnt(ls1.getInt("procnt"));
					data1.setProycnt(ls1.getInt("proycnt"));
					data1.setComcnt(ls1.getInt("comcnt"));
					data1.setCancnt(ls1.getInt("cancnt"));
					data1.setIsonoff(ls1.getString("isonoff"));
					data1.setIsbelongcourse(ls1.getString("isbelongcourse"));
					data1.setSubjcnt(ls1.getInt("subjcnt"));

					data1.setDispnum(total_row_count - ls1.getRowNum() + 1);
					data1.setTotalPageCount(total_page_count);
					data1.setRowCount(row);
					list1.add(data1);
				}
			}
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql3);
			throw new Exception("sql3 = " + sql3 + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
			if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list1;
	}


	/**
    폼메일 발송-사전예고메일
    @param box      receive from the form object and session
    @return int
	 */
	public int sendStudyBeforeMail(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		int cnt = 0;    //  메일발송이 성공한 사람수
		//p_checks로 넘어온 다수의 value를 처리하기 위해 vector로 구현
		Vector v_check1     = new Vector();
		v_check1            = box.getVector("p_checks"); // userid,subj,year,subjseq

		Enumeration em1     = v_check1.elements();

		StringTokenizer st1 = null;
		String v_checks     = "";
		String v_subj       = "";
		String v_year       = "";
		String v_subjseq    = "";
		String v_userid     = "";

		try {
			connMgr = new DBConnectionManager();

			////////////////////  폼메일 발송 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			String v_sendhtml = "mail3.html";
			FormMail fmail = new FormMail(v_sendhtml);     //      폼메일발송인 경우
			MailSet mset = new MailSet(box);        //      메일 세팅 및 발송
			String v_mailTitle = "안녕하세요? 쌍용자동차 인재개발원 운영자입니다.(진도율안내)-1";
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			while(em1.hasMoreElements()){
				v_checks    = (String)em1.nextElement();
				st1      = new StringTokenizer(v_checks,",");
				while (st1.hasMoreElements()) {
					//v_userid    = (String)st1.nextToken();
					v_subj      = st1.nextToken();
					v_year      = st1.nextToken();
					v_subjseq   = st1.nextToken();
					break;
				}
				//select subjnm,passday,tstep,gradstep,gradscore,name,ismailing,cono,email
				sql = "select  B.subjnm,A.tstep,B.gradstep,B.sgradscore gradscore,D.name,D.ismailing,D.cono,D.email,";
				sql+= "(to_number(to_char(sysdate, 'YYYYMMDD')) - to_number(substring(B.edustart,1,8))) passday ";
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
					//String v_toEmail =  "jj1004@dreamwiz.com";

					mset.setSender(fmail);     //  메일보내는 사람 세팅

					fmail.setVariable("tstep", ls.getString("tstep"));
					fmail.setVariable("subjnm", ls.getString("subjnm"));
					fmail.setVariable("passday", ls.getString("passday"));
					fmail.setVariable("tstep", ls.getString("tstep"));
					fmail.setVariable("gradstep", ls.getString("gradstep"));
					fmail.setVariable("gradscore", ls.getString("gradscore"));
					fmail.setVariable("toname", ls.getString("name"));

					String v_mailContent = fmail.getNewMailContent();
					//                    System.out.println("ismailing" + ls.getString("ismailing"));

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


	/**
    폼메일 발송-사후통보메일
    @param box      receive from the form object and session
    @return int
	 */

	public int sendStudyAfterMail(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		int cnt = 0;    //  메일발송이 성공한 사람수
		//p_checks로 넘어온 다수의 value를 처리하기 위해 vector로 구현
		Vector v_check1     = new Vector();
		v_check1            = box.getVector("p_checks"); // userid,subj,year,subjseq

		Enumeration em1     = v_check1.elements();

		StringTokenizer st1 = null;
		String v_checks     = "";
		String v_subj       = "";
		String v_year       = "";
		String v_subjseq    = "";
		String v_userid     = "";

		try {
			connMgr = new DBConnectionManager();

			////////////////////  폼메일 발송 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			String v_sendhtml = "mail3.html";
			FormMail fmail = new FormMail(v_sendhtml);     //      폼메일발송인 경우
			MailSet mset = new MailSet(box);        //      메일 세팅 및 발송
			String v_mailTitle = "안녕하세요? 문화콘텐츠/게임아카데미 운영자입니다.(진도율안내)-1";
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			while(em1.hasMoreElements()){
				v_checks    = (String)em1.nextElement();
				st1      = new StringTokenizer(v_checks,",");
				while (st1.hasMoreElements()) {
					v_userid    = st1.nextToken();
					v_subj      = st1.nextToken();
					v_year      = st1.nextToken();
					v_subjseq   = st1.nextToken();
					break;
				}
				//select subjnm,passday,tstep,gradstep,gradscore,name,ismailing,cono,email
				sql = "select  B.subjnm,A.tstep,B.gradstep,B.sgradscore gradscore,D.name,D.ismailing,D.cono,D.email,";
				sql+= "(to_number(to_char(sysdate, 'YYYYMMDD')) - to_number(substring(B.edustart,1,8))) passday ";
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
					//String v_toEmail =  "jj1004@dreamwiz.com";

					mset.setSender(fmail);     //  메일보내는 사람 세팅

					fmail.setVariable("tstep", ls.getString("tstep"));
					fmail.setVariable("subjnm", ls.getString("subjnm"));
					fmail.setVariable("passday", ls.getString("passday"));
					fmail.setVariable("tstep", ls.getString("tstep"));
					fmail.setVariable("gradstep", ls.getString("gradstep"));
					fmail.setVariable("gradscore", ls.getString("gradscore"));
					fmail.setVariable("toname", ls.getString("name"));

					String v_mailContent = fmail.getNewMailContent();
					//                    System.out.println("ismailing" + ls.getString("ismailing"));

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




	/**
    폼메일 발송-독려메일
    @param box      receive from the form object and session
    @return int
	 */
	public int sendFormMail(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		String sql = "";
		int cnt = 0;    //  메일발송이 성공한 사람수
		//p_checks로 넘어온 다수의 value를 처리하기 위해 vector로 구현
		Vector v_check1     = new Vector();
		v_check1            = box.getVector("p_checks"); // userid,subj,year,subjseq

		Enumeration em1     = v_check1.elements();

		StringTokenizer st1 = null;
		String v_checks     = "";
		String v_subj       = "";
		String v_year       = "";
		String v_subjseq    = "";
		String v_userid     = "";
		String v_ismailing  = box.getString("p_isMailing");


		String v_touch = box.getString("p_touch");
		String v_msubjnm = box.getString("p_msubjnm");
		String v_mseqgrnm = box.getString("p_mseqgrnm");
		String v_msubj = box.getString("p_msubj");
		String v_myear = box.getString("p_myear");
		String v_msubjseq = box.getString("p_msubjseq");

		DataBox dbox = null;


		try {
			connMgr = new DBConnectionManager();

			////////////////////  폼메일 발송 //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//String v_sendhtml = "mail3.htm";
			String v_sendhtml = "mail_jindopush.html";
			FormMail fmail = new FormMail(v_sendhtml);     //      폼메일발송인 경우
			MailSet mset = new MailSet(box);        //      메일 세팅 및 발송
			//String v_mailTitle = "안녕하세요? HKMC 교육지원팀 입니다.(진도율안내) ";
			String v_mailTitle = v_msubjnm+"과정 학습진행 안내입니다.";
			/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			while(em1.hasMoreElements()){
				v_checks    = (String)em1.nextElement();
				st1      = new StringTokenizer(v_checks,",");
				while (st1.hasMoreElements()) {
					v_userid    = st1.nextToken();
					v_subj      = st1.nextToken();
					v_year      = st1.nextToken();
					v_subjseq   = st1.nextToken();
					break;
				}
				//select subjnm,passday,tstep,gradstep,gradscore,name,ismailing,cono,email
				sql = "select  B.subjnm,A.tstep,B.gradstep,B.gradscore,B.gradreport,B.gradexam,B.gradhtest,B.gradftest,B.wstep,B.wmtest,B.wftest,B.whtest,B.wreport,D.name,D.ismailing,D.cono,D.email,\n";
				sql+= " B.edustart,B.eduend, \n";
				sql+= " (to_number(to_char(sysdate, 'YYYYMMDD')) - to_number(substring(B.edustart,1,8))) passday \n";
				sql+= " from TZ_STUDENT A,tZ_SUBJSEQ B,TZ_MEMBER D \n";
				sql+= " where A.userid = "+SQLString.Format(v_userid);
				sql+= " and A.subj = "+SQLString.Format(v_subj);
				sql+= " and A.year = "+SQLString.Format(v_year);
				sql+= " and A.subjseq = "+SQLString.Format(v_subjseq);
				sql+= " and A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq and A.userid=D.userid \n";
				sql+= " group by B.subjnm,A.tstep,B.gradstep,B.gradscore,B.gradreport,B.gradexam,B.gradhtest,B.gradftest,B.wstep,B.wmtest,B.wftest,B.whtest,B.wreport,D.name,D.ismailing,D.cono,D.email,B.edustart,B.eduend \n";


				ls = connMgr.executeQuery(sql);
				System.out.println("sql=========>"+sql);

				while (ls.next()) {
					String v_toEmail =  ls.getString("email");
					String v_toCono  =  ls.getString("cono");
					String v_suryo = "";
					//String v_ismailing= ls.getString("ismailing");
					//String v_toEmail =  "jj1004@dreamwiz.com";
					int wmtest  = Integer.parseInt(ls.getString("wmtest"));
					int wftest  = Integer.parseInt(ls.getString("wftest"));
					int whtest  = Integer.parseInt(ls.getString("whtest"));

					v_suryo = "-진도율 ("+ls.getString("wstep")+"%): "+ls.getString("gradstep")+"% 이상<br>";
					if(whtest > 0){
						v_suryo+= "-형성평가 ("+whtest+"%): "+ls.getString("gradhtest")+"점 이상<br>";
					}
					if(wmtest > 0){
						v_suryo+= "-중간평가 ("+wmtest+"%): "+ls.getString("gradexam")+"점 이상<br>";
					}
					if(wftest > 0){
						v_suryo+= "-최종평가 ("+wftest+"%): "+ls.getString("gradftest")+"점 이상<br>";
					}
					if(!ls.getString("wreport").equals("0")){
						v_suryo+= "-과제물 ("+ls.getString("wreport")+"%): "+ls.getString("gradreport")+"점 이상<br>";
					}
					v_suryo+= "-총점 : "+ls.getString("gradscore")+"점 이상시 수료가능<br>";
					v_suryo+= "-수료점수 : "+ls.getString("gradscore")+"점";

					mset.setSender(fmail);     //  메일보내는 사람 세팅

					fmail.setVariable("tstep",     ls.getString("tstep"));
					fmail.setVariable("subjnm",    ls.getString("subjnm"));
					fmail.setVariable("passday",   ls.getString("passday"));
					fmail.setVariable("tstep",     ls.getString("tstep"));
					fmail.setVariable("gradstep",  v_suryo);
					fmail.setVariable("gradscore", ls.getString("gradscore"));
					fmail.setVariable("toname",    ls.getString("name"));
					fmail.setVariable("edustart",  FormatDate.getFormatDate(ls.getString("edustart"), "yyyy/MM/dd") );
					fmail.setVariable("eduend",    FormatDate.getFormatDate(ls.getString("eduend"), "yyyy/MM/dd") );

					String v_mailContent = fmail.getNewMailContent();

					System.out.println("ismailing" + ls.getString("ismailing"));
					System.out.println("v_toCono="+v_toCono);
					System.out.println("v_toEmail="+v_toEmail);
					System.out.println("v_mailTitle="+v_mailTitle);
					System.out.println("v_mailContent="+v_mailContent);
					System.out.println("v_ismailing="+v_ismailing);
					System.out.println("v_sendhtml="+v_sendhtml);

					boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent,v_ismailing, v_sendhtml);
					if(isMailed) cnt++;     //      메일발송에 성공하면

					dbox = ls.getDataBox();
					dbox.put("d_subj", v_msubj);
					dbox.put("d_year", v_myear);
					dbox.put("d_subjseq", v_msubjseq);
					dbox.put("d_userid", v_userid);
					dbox.put("d_touch", v_touch);
					dbox.put("d_ismail", "1");
					dbox.put("d_title",v_mailTitle);
					if(isMailed){
						dbox.put("d_isok", "Y");
					}else{
						dbox.put("d_isok", "N");
					}
					dbox.put("d_ismailopen", "N");
					dbox.put("d_subjnm", v_msubjnm);
					dbox.put("d_seqgrnm", v_mseqgrnm);


				}

				mset.insertHumanTouch(dbox);

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