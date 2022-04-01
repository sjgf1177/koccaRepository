//**********************************************************
//  1. 제      목: PROPOSE STATUS DATA
//  2. 프로그램명: ProposeStatusData.java
//  3. 개      요: 신청 현황 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 8. 20.
//  7. 수      정:
//**********************************************************
package com.credu.propose;

public class ProposeStatusData {
    
	private String grseq;
	private String grseqnm;
    private String course;		
    private String cyear;		
    private String courseseq;
	private String coursenm;
	private String subj;
    private String year;	
	private String subjseq;
	private String subjseqgr;
	private String subjnm;	
	private String companynm;            /* 회사명  	*/
	private String compnm;               /* 소속명  	*/
	private String jikwinm;              /* 직위명   */
	private String jikunnm;              /* 직군명   */
	private String jikupnm;              /* 직급명   */
	                                     
	private String userid ;              /* ID       */
	private String cono;                 /* 사번     */
	private String name;   	             /* 이름     */		
	private String appdate;	             /* 신청일   */
	private String edustart;             /* 교육 시작일시 	*/
	private String eduend;		         /* 교육 종료일시 	*/
	private String isproposeapproval;    /* 현업팀장결재 승인 여부 	*/
	private String chkfirst;             /* 1차 승인 여부 	*/
	private String chkfinal;             /* 최종 승인 여부 	*/
	private String email;                
	private String ismailing;            
    private String isnewcourse;             	
    private String canceldate;	         /* 취소일   */
    private String cancelkind;	         /* 취소구분   */
    private String reason;	             /* 취소사유   */
    private String propstart;            /* 수강신청 시작일시 	*/
    private String propend;              /* 수강신청 시작일시 	*/
	private String isonoff;    
    private int studentlimit;            /* 정원 	*/
    private int procnt;                  /* 수강신청 인원 	*/
    private int cancnt;                  /* 수강신청취소 인원	*/
	private int rowspan;		
	private int dispnum;
	private int totalpagecount;
	private int rowcount;
	
	public ProposeStatusData() {};
	
	/**
	 * @return
	 */
	public String getAppdate() {
		return appdate;
	}

	/**
	 * @return
	 */
	public String getChkfinal() {
		return chkfinal;
	}

	/**
	 * @return
	 */
	public String getChkfirst() {
		return chkfirst;
	}
	
	/**
	 * @return
	 */
	public String getIsproposeapproval() {
		return isproposeapproval;
	}

	/**
	 * @return
	 */
	public String getCompnm() {
		return compnm;
	}

	/**
	 * @return
	 */
	public String getCono() {
		return cono;
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
	public String getEdustart() {
		return edustart;
	}

	/**
	 * @return
	 */
	public String getEmail() {
		return email;
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
	public String getIsmailing() {
		return ismailing;
	}

	/**
	 * @return
	 */
	public String getIsnewcourse() {
		return isnewcourse;
	}

	/**
	 * @return
	 */
	public String getJikwinm() {
		return jikwinm;
	}

	/**
	 * @return
	 */
	public String getJikupnm() {
		return jikupnm;
	}


	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public int getRowspan() {
		return rowspan;
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
	public String getUserid() {
		return userid;
	}

	/**
	 * @return
	 */
	public String getYear() {
		return year;
	}

    /**
   	 * @return
	 */
	public String getCanceldate() {
		return canceldate;
	}

    /**
	 * @return
	 */
	public String getCancelkind() {
		return cancelkind;
	}

    /**
	 * @return
	 */
	public String getReason() {
		return reason;
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
	public String getPropend() {
		return propend;
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
	public int getProcnt() {
		return procnt;
	}

   /**
	 * @return
	 */
	public int getCancnt() {
		return cancnt;
	}

	/**
	 * @param string
	 */
	public void setAppdate(String string) {
		appdate = string;
	}

	/**
	 * @param string
	 */
	public void setChkfinal(String string) {
		chkfinal = string;
	}

	/**
	 * @param string
	 */
	public void setChkfirst(String string) {
		chkfirst = string;
	}

     /**
	 * @param string
	 */
    public void setIsproposeapproval(String string) {
		isproposeapproval = string;
	}
	
	/**
	 * @param string
	 */
	public void setCompnm(String string) {
		compnm = string;
	}

	/**
	 * @param string
	 */
	public void setCono(String string) {
		cono = string;
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
	 * @param string
	 */
	public void setEdustart(String string) {
		edustart = string;
	}

	/**
	 * @param string
	 */
	public void setEmail(String string) {
		email = string;
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
	public void setIsmailing(String string) {
		ismailing = string;
	}

	/**
	 * @param string
	 */
	public void setIsnewcourse(String string) {
		isnewcourse = string;
	}

	/**
	 * @param string
	 */
	public void setJikwinm(String string) {
		jikwinm = string;
	}

	/**
	 * @param string
	 */
	public void setJikupnm(String string) {
		jikupnm = string;
	}
	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param i
	 */
	public void setRowspan(int i) {
		rowspan = i;
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
	public void setUserid(String string) {
		userid = string;
	}

	/**
	 * @param string
	 */
	public void setYear(String string) {
		year = string;
	}
	
	/**
	 * @param string
	 */
	public void setCanceldate(String string) {
		canceldate = string;
	}
	
	/**
	 * @param string
	 */
	public void setCancelkind(String string) {
		cancelkind = string;
	}
	
	/**
	 * @param string
	 */
	public void setReason(String string) {
		reason = string;
	}
	
	/**
	 * @param string
	 */
	public void setPropstart(String string) {
		propstart = string;
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
	public void setStudentlimit(int string) {
		studentlimit = string;
	}

    /**
	 * @param string
	 */
	public void setProcnt(int string) {
		procnt = string;
	}
	
	/**
	 * @param string
	 */
	public void setCancnt(int string) {
		cancnt = string;
	}
	
	public void   setDispnum (int dispnum)	{ this.dispnum = dispnum; }
	public int    getDispnum()	{	return dispnum;	}	
	
	public void setTotalPageCount(int totalpagecount)	{ this.totalpagecount = totalpagecount; }
	public int    getTotalPageCount()	{	return totalpagecount;	}	
	
	public void setRowCount(int rowcount)	{ this.rowcount = rowcount; }
	public int    getRowCount()	{	return rowcount;	}		
	
	public void setJikunnm(String jikunnm)	{ this.jikunnm = jikunnm; }
	public String getJikunnm()	{	return jikunnm;	}				
	
	public void setCompanynm(String companynm)	{ this.companynm = companynm; }
	public String getCompanynm()	{	return companynm;	}				
	
	/**
	 * @param string
	 */
	public void setIsonoff(String string) {
		isonoff = string;
	}
	
	/**
	 * @return
	 */
	public String getIsonoff() {
		return isonoff;
	}		
}	