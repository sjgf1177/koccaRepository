//**********************************************************
//1. 제      목: 교육차수관리화면용 DATA
//2. 프로그램명: CourseStateData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 정상진 2003. 07. 22
//7. 수      정: 
//                 
//**********************************************************
package com.credu.course;

import java.util.*;
 
/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CourseStateData {



	private String  grcode		;	
	private String  grcodenm	;
	private String  grseq       ;
	private String  gyear       ;
	private String  grseqnm     ;
	private String  subj        ;
	private String  subjnm      ;
	private String  year        ;
	private String  subjseq     ;
	private String  subjseqgr     ;
	private String  course      ;
	private String  cyear       ;
	private String  courseseq   ;
	private String  coursenm    ;
	
	private		String	isbelongcourse ;
	private		String	propstart      ;
	private		String	propend        ;
	private		String	edustart       ;
	private		String	eduend         ;
	private		String	isclosed       ;
	private		String	isgoyong       ;
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
	private 	int		wstep         ;
	private 	int		wmtest        ;
	private 	int		wftest        ;
	private 	int		whtest        ;
	private 	int		wreport       ;
	private 	int		wact          ;
	private 	int		wetc1         ;
	private 	int		wetc2         ;
	private 	int		goyongprice   ;

	private 	String  isonoff     ;
	private		String	subjtypenm	;
	private 	int     cnt_mexam = 0;
	private 	int     cnt_texam = 0;
	private 	int     cnt_hexam  = 0;	
	private 	int     cnt_proj  = 0;	
	private 	int     cnt_act   = 0;

	private 	String  isnewcourse ;	
	private 	int     rowspan     ;
    private     String  scsubjseq;
		
	public  CourseStateData() {}


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
	public int getCnt_act() {
		return cnt_act;
	}

	/**
	 * @return
	 */
	public int getCnt_mexam() {
		return cnt_mexam;
	}

	/**
	 * @return
	 */
	public int getCnt_proj() {
		return cnt_proj;
	}

	/**
	 * @return
	 */
	public int getCnt_texam() {
		return cnt_texam;
	}
	
	/**
	 * @return
	 */
	public int getCnt_hexam() {
		return cnt_hexam;
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
	public String getGrcodenm() {
		return grcodenm;
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
	public String getSubjseqgr() {
		return subjseqgr;
	}

	/**
	 * @return
	 */
	public int getWact() {
		return wact;
	}

	/**
	 * @return
	 */
	public int getWarndays() {
		return warndays;
	}

	/**
	 * @return
	 */
	public int getWetc1() {
		return wetc1;
	}

	/**
	 * @return
	 */
	public int getWetc2() {
		return wetc2;
	}

	/**
	 * @return
	 */
	public int getWftest() {
		return wftest;
	}
	
	/**
	 * @return
	 */
	public int getWhtest() {
		return whtest;
	}

	/**
	 * @return
	 */
	public int getWmtest() {
		return wmtest;
	}

	/**
	 * @return
	 */
	public int getWreport() {
		return wreport;
	}

	/**
	 * @return
	 */
	public int getWstep() {
		return wstep;
	}

	/**
	 * @return
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param i
	 */
	public void setBiyong(int i) {
		biyong = i;
	}

	/**
	 * @param i
	 */
	public void setCnt_act(int i) {
		cnt_act = i;
	}

	/**
	 * @param i
	 */
	public void setCnt_mexam(int i) {
		cnt_mexam = i;
	}

	/**
	 * @param i
	 */
	public void setCnt_proj(int i) {
		cnt_proj = i;
	}

	/**
	 * @param i
	 */
	public void setCnt_texam(int i) {
		cnt_texam = i;
	}
	
	/**
	 * @param i
	 */
	public void setCnt_hexam(int i) {
		cnt_hexam = i;
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
	public void setGrcodenm(String string) {
		grcodenm = string;
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
	public void setSubjseqgr(String string) {
		subjseqgr = string;
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
	public void setWact(int i) {
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
	public void setWetc1(int i) {
		wetc1 = i;
	}

	/**
	 * @param i
	 */
	public void setWetc2(int i) {
		wetc2 = i;
	}

	/**
	 * @param i
	 */
	public void setWftest(int i) {
		wftest = i;
	}
	
	/**
	 * @param i
	 */
	public void setWhtest(int i) {
		whtest = i;
	}

	/**
	 * @param i
	 */
	public void setWmtest(int i) {
		wmtest = i;
	}

	/**
	 * @param i
	 */
	public void setWreport(int i) {
		wreport = i;
	}

	/**
	 * @param i
	 */
	public void setWstep(int i) {
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
	public int getRowspan() {
		return rowspan;
	}

	/**
	 * @param i
	 */
	public void setRowspan(int i) {
		rowspan = i;
	}

	/**
	 * @return
	 */
	public String getIsnewcourse() {
		return isnewcourse;
	}

	/**
	 * @param string
	 */
	public void setIsnewcourse(String string) {
		isnewcourse = string;
	}

    public void setGoyongprice(int goyongprice) { this.goyongprice = goyongprice; }
    public int  getGoyongprice()    {   return goyongprice; }

    public void    setScsubjseq(String scsubjseq) { this.scsubjseq = scsubjseq; }
    public String  getScsubjseq()    {   return scsubjseq; }
    
}