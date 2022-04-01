//**********************************************************
//1. 제      목: 
//2. 프로그램명: CancelMemberData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-07-28
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.propose;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ProposeMemberData {
	private String grcode;
	private String gyear;
	private String grseq;
	private String grseqnm;
	private String grcodenm;
	private String subjtype;  /* 과정:S, 코스:C */                         
	private String subj;
	private String year;
	private String subjseq;
	private String subjnm;
	
	private String asgn;       /* 소속코드	*/
	private String asgnnm;     /* 소속명  	*/
	private String jikwi;      /* 직위     */
	private String jikwinm;    /* 직위명   */
	private String jikup;      /* 직급     */
	private String jikupnm;    /* 직급명   */

	private String userid ;   	/* ID       */
	private String cono;       /* 사번     */
	private String name;   	/* 이름     */	
	
	private String propstart;   /* 수강신청 시작일시 	*/
	private String propend;	 /* 수강신청 종료일시 	*/
	private String appdate;	/* 신청일   */
	private String edustart;   /* 교육 시작일시 	*/
	private String eduend;		/* 교육 종료일시 	*/

	private String chkfirst;   /* 1차 승인 여부 	*/
	private String chkfinal;   /* 최종 승인 여부 	*/
	private String cancelkind;   /* 취소여부 	*/
	
	public ProposeMemberData() {};
	
	/**
	 * @return
	 */
	public String getAppdate() {
		return appdate;
	}

	/**
	 * @return
	 */
	public String getAsgn() {
		return asgn;
	}

	/**
	 * @return
	 */
	public String getAsgnnm() {
		return asgnnm;
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
	public String getCancelkind() {
		return cancelkind;
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
	public String getCono() {
		return cono;
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
	public String getJikwi() {
		return jikwi;
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
	public String getJikup() {
		return jikup;
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
	public String getSubjtype() {
		return subjtype;
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
	 * @param string
	 */
	public void setAppdate(String string) {
		appdate = string;
	}

	/**
	 * @param string
	 */
	public void setAsgn(String string) {
		asgn = string;
	}

	/**
	 * @param string
	 */
	public void setAsgnnm(String string) {
		asgnnm = string;
	}

	/**
	 * @param string
	 */
	public void setChkfinal(String string) {
		if (string == null || string.equals("")) {
			chkfinal = "N";
		} else {
			chkfinal = string;
		} 
	}

	/**
	 * @param string
	 */
	public void setChkfirst(String string) {
		if (string == null || string.equals("")) {
			chkfirst = "N";
		} else {
			chkfirst = string;
		} 
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
	public void setCono(String string) {
		cono = string;
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
	public void setJikwi(String string) {
		jikwi = string;
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
	public void setJikup(String string) {
		jikup = string;
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
	public void setSubjtype(String string) {
		subjtype = string;
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

}
