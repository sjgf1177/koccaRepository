//**********************************************************
//1. 제      목: 
//2. 프로그램명: ClassListData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-07-29
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.study;

import java.util.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ClassListData {
	String subj;
	String year;
	String subjseq;
	String subjseqgr;
	String subjnm;
	String coursenm;
	String edustart;
	String eduend;
	int studentcnt;
	int classcnt;
	int noassignstudentcnt; 
	int availabletutorcnt;
	Vector tutor; 
	String subtutor;
	String isbelongcourse;
	int subjcnt;
	
	public ClassListData() {
		subj = "";
		year = "";
		subjseq = "";
		subjseqgr = "";
		subjnm = "";
		coursenm = "";
		edustart = "";
		eduend = "";
		studentcnt = 0;
		classcnt = 0;
		noassignstudentcnt = 0; 
		availabletutorcnt = 0;
		tutor = null; 
		subtutor = "";
		isbelongcourse = "";
		subjcnt = 0;
		
	}
	
	/**
	 * @return
	 */
	public int getAvailabletutorcnt() {
		return availabletutorcnt;
	}

	/**
	 * @return
	 */
	public int getClasscnt() {
		return classcnt;
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
	public int getNoassignstudentcnt() {
		return noassignstudentcnt;
	}

	/**
	 * @return
	 */
	public int getStudentcnt() {
		return studentcnt;
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
	public String getSubtutor() {
		return subtutor;
	}

	/**
	 * @return
	 */
	public Vector getTutor() {
		return tutor;
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
	public void setAvailabletutorcnt(int i) {
		availabletutorcnt = i;
	}

	/**
	 * @param i
	 */
	public void setClasscnt(int i) {
		classcnt = i;
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
	 * @param i
	 */
	public void setNoassignstudentcnt(int i) {
		noassignstudentcnt = i;
	}

	/**
	 * @param i
	 */
	public void setStudentcnt(int i) {
		studentcnt = i;
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
	public void setSubtutor(String string) {
		subtutor = string;
	}

	/**
	 * @param vector
	 */
	public void setTutor(Vector vector) {
		tutor = vector;
	}

	/**
	 * @param string
	 */
	public void setYear(String string) {
		year = string;
	}

	public String getIsbelongcourse() {
		return isbelongcourse;
	}

	public void setIsbelongcourse(String isbelongcourse) {
		this.isbelongcourse = isbelongcourse;
	}

	public int getSubjcnt() {
		return subjcnt;
	}

	public void setSubjcnt(int subjcnt) {
		this.subjcnt = subjcnt;
	}
	
}
