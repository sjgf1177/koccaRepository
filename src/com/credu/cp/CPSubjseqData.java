//**********************************************************
//1. 제      목: 교육차수관리화면용 DATA
//2. 프로그램명: CPSubjseqData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 07. 22
//7. 수      정: 이창훈 2004.12.28
//                 
//**********************************************************
package com.credu.cp;

import java.util.*;

/**
*교육차수관리 Data
*<p>제목:CPSubjseqData.java</p>
*<p>설명:교육차수관리 Data 빈</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author 이창훈
*@version 1.0
*/

public class CPSubjseqData {
	private String  grcode		 ;	
	private String  grcodenm		 ;
	private String  grseq       ;
	private String  gyear       ;
	private String  grseqnm     ;
	private String  subj        ;
	private String  subjnm      ;
	private String  year        ;
	private String  subjseq     ;
	private String  course      ;
	private String  cyear       ;
	private String  courseseq   ;
	private String  coursenm    ;
	private String  cpsubjseq    ;
	
	private		String	isbelongcourse ;
	private		String	propstart      ;
	private		String	propend        ;
	private		String	edustart       ;
	private		String	eduend         ;
	private		String	endfirst       ;
	private		String	endfinal         ;
	private		String	isclosed       ;
	private		String	isgoyong       ;
	private		String	subj_isgoyong       ;
	private		String	ismultipaper   ;
	private		String	luserid        ;
	private		String	ldate          ;
	private 	int		studentlimit  ;
	private 	int		point         ;
	private 	int		biyong        ;
	private 	int		edulimit      ;
	private 	int		warndays      ;
	private 	int		stopdays      ;
	private 	int		gradscore     ;
	private 	int		gradstep      ;
	private 	double		wstep         ;
	private 	double		wmtest        ;
	private 	double		wftest        ;
	private 	double		wreport       ;
	private 	double		wact          ;
	private 	double		wetc1         ;
	private 	double		wetc2         ;
	private	String		proposetype;

	private 		String  isonoff     ;
	private		String	subjtypenm	;
	private int     cnt_propose =0;
	private int     cnt_student =0;
	private int     cnt_stold   =0;
	private boolean  canDelete  =false;

	//add
	private		int    	score; //학점배점
	private		int    	gradreport; //이수기준 - 리포트
	private 	int    	gradexam;   //이수기준 -  중간평가
	private 	int    	gradftest;   //이수기준 -  최종평가
	private 	int    	gradhtest;   //이수기준 -  형성평가
	private 	double		whtest        ; //형성평가
	private	String usesubjseqapproval; 	//결재여부 - 차수개설주관팀장
	private String useproposeapproval;	//결재여부 - 수강신청현업팀장
	private String usemanagerapproval;	//결재여부 - 주관팀장수강승인
	private double rndcreditreq;		//학점배점(연구개발-필수)
	private double rndcreditchoice;		//학점배점(연구개발-선택)
	private double rndcreditadd;		//학점배점(연구개발-지정가점)
	private double rndcreditdeduct;		//학점배점(연구개발-지정감점)
	private String rndjijung;			//연구개발-지정과정여부
	private String	   isablereview;		//복습가능여부
	private int	   tsubjbudget;		//과정예산
	private String isusebudget;		//과정예산 금액 제한 사용여부
	private String isessential = "";
	
	private String isvisible;		//학습자에게 보여주기
	
	public  CPSubjseqData() {}


	public String getSubjtypenm(){
		
		if (isonoff==null || isonoff.equals(""))	return "aaa";
		if (isonoff.equals("ON"))	return "사이버";
		else						return "집합";
	}
	/**
	 * @return
	 */
	public int getBiyong() {
		return biyong;
	}

	/**
	 * @return
	 */
	public boolean isCanDelete() {
		return canDelete;
	}

	/**
	 * @return
	 */
	public int getCnt_propose() {
		return cnt_propose;
	}

	/**
	 * @return
	 */
	public int getCnt_stold() {
		return cnt_stold;
	}

	/**
	 * @return
	 */
	public int getCnt_student() {
		return cnt_student;
	}

	/**
	 * @return
	 */
	public String getCourse() {
		return course;
	}

	/**
	 * @return
	 */
	public String getCoursenm() {
		return coursenm;
	}

	/**
	 * @return
	 */
	public String getCourseseq() {
		return courseseq;
	}

	/**
	 * @return
	 */
	public String getCyear() {
		return cyear;
	}

	/**
	 * @return
	 */
	public String getEduend() {
		return eduend;
	}

	/**
	 * @return
	 */
	public int getEdulimit() {
		return edulimit;
	}

	/**
	 * @return
	 */
	public String getEdustart() {
		return edustart;
	}

	/**
	 * @return
	 */
	public int getGradscore() {
		return gradscore;
	}

	/**
	 * @return
	 */
	public int getGradstep() {
		return gradstep;
	}

	/**
	 * @return
	 */
	public String getGrcode() {
		return grcode;
	}

	/**
	 * @return
	 */
	public String getGrseq() {
		return grseq;
	}

	/**
	 * @return
	 */
	public String getGrseqnm() {
		return grseqnm;
	}

	/**
	 * @return
	 */
	public String getGyear() {
		return gyear;
	}

	/**
	 * @return
	 */
	public String getIsbelongcourse() {
		return isbelongcourse;
	}

	/**
	 * @return
	 */
	public String getIsclosed() {
		return isclosed;
	}

	/**
	 * @return
	 */
	public String getIsgoyong() {
		return isgoyong;
	}

	/**
	 * @return
	 */
	public String getIsmultipaper() {
		return ismultipaper;
	}

	/**
	 * @return
	 */
	public String getIsonoff() {
		return isonoff;
	}

	/**
	 * @return
	 */
	public String getLdate() {
		return ldate;
	}

	/**
	 * @return
	 */
	public String getLuserid() {
		return luserid;
	}

	/**
	 * @return
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * @return
	 */
	public String getPropend() {
		return propend;
	}

	/**
	 * @return
	 */
	public String getPropstart() {
		return propstart;
	}

	/**
	 * @return
	 */
	public int getStopdays() {
		return stopdays;
	}

	/**
	 * @return
	 */
	public int getStudentlimit() {
		return studentlimit;
	}

	/**
	 * @return
	 */
	public String getSubj() {
		return subj;
	}

	/**
	 * @return
	 */
	public String getSubjnm() {
		return subjnm;
	}

	/**
	 * @return
	 */
	public String getSubjseq() {
		return subjseq;
	}

	/**
	 * @return
	 */
	public double getWact() {
		return wact;
	}

	/**
	 * @return
	 */
	public double getWarndays() {
		return warndays;
	}

	/**
	 * @return
	 */
	public double getWetc1() {
		return wetc1;
	}

	/**
	 * @return
	 */
	public double getWetc2() {
		return wetc2;
	}

	/**
	 * @return
	 */
	public double getWftest() {
		return wftest;
	}

	/**
	 * @return
	 */
	public double getWmtest() {
		return wmtest;
	}

	/**
	 * @return
	 */
	public double getWreport() {
		return wreport;
	}

	/**
	 * @return
	 */
	public double getWstep() {
		return wstep;
	}

	/**
	 * @return
	 */
	public String getYear() {
		return year;
	}
	
	/****************************
	//add
	/****************************
	/**
	 * @return
	 */
	public int getGradexam() {
		return gradexam;
	}
	
	/**
	 * @return
	 */
	public int getGradftest() {
		return gradftest;
	}
	
	/**
	 * @return
	 */
	public int getGradhtest() {
		return gradhtest;
	}

	/**
	 * @return
	 */
	public int getGradreport() {
		return gradreport;
	}

	/**
	 * @return
	 */
	public double getWhtest() {
		return whtest;
	}

	/**
	 * @return
	 */
	public String getUsesubjseqapproval() {
		return usesubjseqapproval;
	}
	
	/**
	 * @return
	 */
	public String getUseproposeapproval() {
		return useproposeapproval;
	}
	
	/**
	 * @return
	 */
	public String getUsemanagerapproval() {
		return usemanagerapproval;
	}
	
	/**
	 * @return
	 */
	public double getRndcreditreq() {
		return rndcreditreq;
	}
	
	/**
	 * @return
	 */
	public double getRndcreditchoice() {
		return rndcreditchoice;
	}
	
	/**
	 * @return
	 */
	public double getRndcreditadd() {
		return rndcreditadd;
	}
	
	/**
	 * @return
	 */
	public double getRndcreditdeduct() {
		return rndcreditdeduct;
	}
	
	/**
	 * @return
	 */
	public String getRndjijung() {
		return rndjijung;
	}
	
	/**
	 * @return
	 */
	public String getIsablereview() {
		return isablereview;
	}
	
	/**
	 * @return
	 */
	public int getTsubjbudget() {
		return tsubjbudget;
	}
	
	/**
	 * @return
	 */
	public String getIsusebudget() {
		return isusebudget;
	}
	
	/**
	 * @return
	 */
	public int getScore() {
		return score;
	}
	
	/**
	 * @return
	 */
	public String getIsvisible() {
		return isvisible;
	}
	
	/*******************************
	/* set method
	/*******************************

	/**
	 * @param i
	 */
	public void setBiyong(int i) {
		biyong = i;
	}

	/**
	 * @param b
	 */
	public void setCanDelete(boolean b) {
		canDelete = b;
	}

	/**
	 * @param i
	 */
	public void setCnt_propose(int i) {
		cnt_propose = i;
	}

	/**
	 * @param i
	 */
	public void setCnt_stold(int i) {
		cnt_stold = i;
	}

	/**
	 * @param i
	 */
	public void setCnt_student(int i) {
		cnt_student = i;
	}

	/**
	 * @param string
	 */
	public void setCourse(String string) {
		course = string;
	}

	/**
	 * @param string
	 */
	public void setCoursenm(String string) {
		coursenm = string;
	}

	/**
	 * @param string
	 */
	public void setCourseseq(String string) {
		courseseq = string;
	}

	/**
	 * @param string
	 */
	public void setCyear(String string) {
		cyear = string;
	}

	/**
	 * @param string
	 */
	public void setEduend(String string) {
		eduend = string;
	}

	/**
	 * @param i
	 */
	public void setEdulimit(int i) {
		edulimit = i;
	}

	/**
	 * @param string
	 */
	public void setEdustart(String string) {
		edustart = string;
	}

	/**
	 * @param i
	 */
	public void setGradscore(int i) {
		gradscore = i;
	}

	/**
	 * @param i
	 */
	public void setGradstep(int i) {
		gradstep = i;
	}

	/**
	 * @param string
	 */
	public void setGrcode(String string) {
		grcode = string;
	}

	/**
	 * @param string
	 */
	public void setGrseq(String string) {
		grseq = string;
	}

	/**
	 * @param string
	 */
	public void setGrseqnm(String string) {
		grseqnm = string;
	}

	/**
	 * @param string
	 */
	public void setGyear(String string) {
		gyear = string;
	}

	/**
	 * @param string
	 */
	public void setIsbelongcourse(String string) {
		isbelongcourse = string;
	}

	/**
	 * @param string
	 */
	public void setIsclosed(String string) {
		isclosed = string;
	}

	/**
	 * @param string
	 */
	public void setIsgoyong(String string) {
		isgoyong = string;
	}

	/**
	 * @param string
	 */
	public void setIsmultipaper(String string) {
		ismultipaper = string;
	}

	/**
	 * @param string
	 */
	public void setIsonoff(String string) {
		isonoff = string;
	}

	/**
	 * @param string
	 */
	public void setLdate(String string) {
		ldate = string;
	}

	/**
	 * @param string
	 */
	public void setLuserid(String string) {
		luserid = string;
	}

	/**
	 * @param i
	 */
	public void setPoint(int i) {
		point = i;
	}

	/**
	 * @param string
	 */
	public void setPropend(String string) {
		propend = string;
	}

	/**
	 * @param string
	 */
	public void setPropstart(String string) {
		propstart = string;
	}

	/**
	 * @param i
	 */
	public void setStopdays(int i) {
		stopdays = i;
	}

	/**
	 * @param i
	 */
	public void setStudentlimit(int i) {
		studentlimit = i;
	}

	/**
	 * @param string
	 */
	public void setSubj(String string) {
		subj = string;
	}

	/**
	 * @param string
	 */
	public void setSubjnm(String string) {
		subjnm = string;
	}

	/**
	 * @param string
	 */
	public void setSubjseq(String string) {
		subjseq = string;
	}

	/**
	 * @param string
	 */
	public void setSubjtypenm(String string) {
		subjtypenm = string;
	}

	/**
	 * @param i
	 */
	public void setWact(double i) {
		wact = i;
	}

	/**
	 * @param i
	 */
	public void setWarndays(int i) {
		warndays = i;
	}

	/**
	 * @param i
	 */
	public void setWetc1(double i) {
		wetc1 = i;
	}

	/**
	 * @param i
	 */
	public void setWetc2(double i) {
		wetc2 = i;
	}

	/**
	 * @param i
	 */
	public void setWftest(double i) {
		wftest = i;
	}

	/**
	 * @param i
	 */
	public void setWmtest(double i) {
		wmtest = i;
	}

	/**
	 * @param i
	 */
	public void setWreport(double i) {
		wreport = i;
	}

	/**
	 * @param i
	 */
	public void setWstep(double i) {
		wstep = i;
	}

	/**
	 * @param string
	 */
	public void setYear(String string) {
		year = string;
	}

	/**
	 * @return
	 */
	public String getGrcodenm() {
		return grcodenm;
	}

	/**
	 * @param string
	 */
	public void setGrcodenm(String string) {
		grcodenm = string;
	}

	/**
	 * @return
	 */
	public String getSubj_isgoyong() {
		return subj_isgoyong;
	}

	/**
	 * @param string
	 */
	public void setSubj_isgoyong(String string) {
		subj_isgoyong = string;
	}

	/**
	 * @return
	 */
	public String getEndfinal() {
		return endfinal;
	}

	/**
	 * @return
	 */
	public String getEndfirst() {
		return endfirst;
	}

	/**
	 * @param string
	 */
	public void setEndfinal(String string) {
		endfinal = string;
	}

	/**
	 * @param string
	 */
	public void setEndfirst(String string) {
		endfirst = string;
	}

	/**
	 * @return
	 */
	public String getProposetype() {
		return proposetype;
	}

	/**
	 * @param string
	 */
	public void setProposetype(String string) {
		proposetype = string;
	}
	
	/*****************
	/add
	/*****************
	
	/**
	 * @param i
	 */
	public void setGradreport(int i) {
		gradreport = i;
	}

	/**
	 * @param i
	 */
	public void setGradexam(int i) {
		gradexam = i;
	}
	
	/**
	 * @param i
	 */
	public void setGradftest(int i) {
		gradftest = i;
	}
	
	/**
	 * @param i
	 */
	public void setGradhtest(int i) {
		gradhtest = i;
	}
	
	/**
	 * @param d
	 */
	public void setWhtest(double d) {
		whtest = d;
	}


	/**
	 * @param string
	 */
	public void setUsesubjseqapproval(String string) {
		usesubjseqapproval = string;
	}
	
	/**
	 * @param string
	 */
	public void setUseproposeapproval(String string) {
		useproposeapproval = string;
	}
	
	/**
	 * @param string
	 */
	public void setUsemanagerapproval(String string) {
		usemanagerapproval = string;
	}
	
	/**
	 * @param int
	 */
	public void setRndcreditreq(double d) {
		rndcreditreq = d;
	}
	
	/**
	 * @param int
	 */
	public void setRndcreditchoice(double d) {
		rndcreditchoice = d;
	}
	
	/**
	 * @param int
	 */
	public void setRndcreditadd(double d) {
		rndcreditadd = d;
	}
	
	/**
	 * @param int
	 */
	public void setRndcreditdeduct(double d) {
		rndcreditdeduct = d;
	}
	
	/**
	 * @param int
	 */
	public void setRndjijung(String string) {
		rndjijung = string;
	}
	
	/**
	 * @param string
	 */
	public void setIsablereview(String string) {
		isablereview = string;
	}
	
	/**
	 * @param string
	 */
	public void setScore(int i) {
		score = i;
	}
	
	/**
	 * @param string
	 */
	public void setTsubjbudget(int i) {
		tsubjbudget = i;
	}
	
	/**
	 * @param string
	 */
	public void setIsusebudget(String string) {
		isusebudget = string;
	}
	
	/**
	 * @return
	 */
	public String getIsessential() {
		return isessential;
	}
	
	/**
	 * @param string
	 */
	public void setIsessential(String string) {
		isessential = string;
	}

	/**
	 * @param string
	 */
	public void setIsvisible(String string) {
		isvisible = string;
	}
	
	/**
	 * @return
	 */
	public String getCPsubjseq() {
		return cpsubjseq;
	}
	
	/**
	 * @param string
	 */
	public void setCPsubjseq(String string) {
		cpsubjseq = string;
	}
}
