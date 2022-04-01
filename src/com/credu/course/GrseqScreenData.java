//**********************************************************
//1. 제      목: 교육차수관리화면용 DATA
//2. 프로그램명: GrseqRefData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 07. 22
//7. 수      정: 
//                 
//**********************************************************
package com.credu.course;

import java.util.*;

public class GrseqScreenData {
	private String  grcode		 ;	
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
	private String  propstart   ;
	private String  propend     ;
	private String  edustart    ;
	private String  eduend      ;
	private String  isonoff     ;
	private String  recordType  ;			// : 'course' /'subj'
	private String  usesubjseqapproval;
	private String  isapproval ;
	private int     rowspan_grseq	=0;
	private int     rowspan_course =0;
	private int     cnt_propose =0;
	private int     cnt_student =0;
	private int     cnt_stold   =0;
	private boolean  canDelete  =false;
	
	public  GrseqScreenData() {}

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
	public String getIsonoff() {
		return isonoff;
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
	public String getRecordType() {
		return recordType;
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
	public String getIsapproval() {
		return isapproval;
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
	public String getYear() {
		return year;
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
	public void setIsonoff(String string) {
		isonoff = string;
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
	public void setRecordType(String string) {
		recordType = string;
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
	public void setIsapproval(String string) {
		isapproval = string;
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
	public void setYear(String string) {
		year = string;
	}

	/**
	 * @return
	 */
	public int getRowspan_course() {
		return rowspan_course;
	}

	/**
	 * @return
	 */
	public int getRowspan_grseq() {
		return rowspan_grseq;
	}

	/**
	 * @param i
	 */
	public void setRowspan_course(int i) {
		rowspan_course = i;
	}

	/**
	 * @param i
	 */
	public void setRowspan_grseq(int i) {
		rowspan_grseq = i;
	}

	public String getSubjtypenm(){
		
		if (isonoff==null || isonoff.equals(""))	return "aaa";
		if (isonoff.equals("ON"))	return "사이버";
		else						return "집합";
	}
}
