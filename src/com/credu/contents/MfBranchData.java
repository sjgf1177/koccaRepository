//**********************************************************
//1. 제      목: 마스터폼 DATA
//2. 프로그램명: MfBranchData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 08. 13
//7. 수      정: 
//                 
//**********************************************************
package com.credu.contents;

import com.credu.library.*;

public class MfBranchData {
	
	private 	String subj		;		//과정코드
	private 	String branch	;		//분기코드
	private 	String sdesc	;		//분기명
	private 	String luserid	;
	private 	String ldate	;
	
	/* for tz_subjlesson */
	private	String 	oid;
	private	String 	starting;
	private	int	ordering=0;
	
	/* for EduBranch */
	private	String  lesson;
	private	String  lessonnm;
	

	public MfBranchData() {};


	/**
	 * @return
	 */
	public String getBranch() {
		return branch;
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
	public String getSdesc() {
		return sdesc;
	}

	/**
	 * @return
	 */
	public String getSubj() {
		return subj;
	}

	/**
	 * @param string
	 */
	public void setBranch(String string) {
		branch = string;
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
	 * @param string
	 */
	public void setSdesc(String string) {
		sdesc = string;
	}

	/**
	 * @param string
	 */
	public void setSubj(String string) {
		subj = string;
	}

	/**
	 * @return
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @return
	 */
	public int getOrdering() {
		return ordering;
	}

	/**
	 * @return
	 */
	public String getStarting() {
		return starting;
	}

	/**
	 * @param string
	 */
	public void setOid(String string) {
		oid = string;
	}

	/**
	 * @param i
	 */
	public void setOrdering(int i) {
		ordering = i;
	}

	/**
	 * @param string
	 */
	public void setStarting(String string) {
		starting = string;
	}

	/**
	 * @return
	 */
	public String getLesson() {
		return lesson;
	}

	/**
	 * @param string
	 */
	public void setLesson(String string) {
		lesson = string;
	}

	/**
	 * @return
	 */
	public String getLessonnm() {
		return lessonnm;
	}

	/**
	 * @param string
	 */
	public void setLessonnm(String string) {
		lessonnm = string;
	}

}
