//**********************************************************
//  1. 제      목: 
//  2. 프로그램명: 
//  3. 개      요: 
//  4. 환      경: 
//  5. 버      젼: 
//  6. 작      성: 
//  7. 수      정: 
//**********************************************************

package com.credu.goyong;

import java.util.*;
import com.credu.library.*;

public class GoYongManageData {

/*
C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,"
				+ " get_compnm(B.comp,2,4) compnm, get_jikwinm(B.jikwi, B.comp) jikwinm,"
                + " B.userid,B.cono, b.orga_ename,b.resno,B.name
*/

	private	String	grseq				="";
	private	String 	course				="";
	private	String 	cyear				="";
	private	String 	courseseq			="";
	private	String 	coursenm			="";
	private	String 	subj				="";
	private	String 	year				="";
	private	String 	subjnm				="";
	private	String 	subjseq				="";
	private	String 	compnm				="";
	private	String 	jikwinm				="";
	private	String 	userid				="";
	private	String 	cono				="";
	private	String 	orga_ename			="";
	private	String 	resno				="";
	private	String 	name				="";
		
	public GoYongManageData() {};
		
	/**
	 * @return
	 */
	public String getGrseq() {
		return grseq;
	}
	
	/**
	 * @param string
	 */
	public void setGrseq(String string) {
		grseq = string;
	}
		
	/**
	 * @return
	 */
	public String getCourse() {
		return course;
	}
	
	/**
	 * @param string
	 */
	public void setCourse(String string) {
		course = string;
	}
		
	/**
	 * @return
	 */
	public String getCyear() {
		return cyear;
	}
	
	/**
	 * @param string
	 */
	public void setCyear(String string) {
		cyear = string;
	}
		
	/**
	 * @return
	 */
	public String getCourseseq() {
		return courseseq;
	}
	
	/**
	 * @param string
	 */
	public void setCourseseq(String string) {
		courseseq = string;
	}
		
	/**
	 * @return
	 */
	public String getCoursenm() {
		return coursenm;
	}
	
	/**
	 * @param string
	 */
	public void setCoursenm(String string) {
		coursenm = string;
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
	public void setSubj(String string) {
		subj = string;
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
	public void setYear(String string) {
		year = string;
	}
		
	/**
	 * @return
	 */
	public String getSubjnm() {
		return subjnm;
	}
	
	/**
	 * @param string
	 */
	public void setSubjnm(String string) {
		subjnm = string;
	}
		
	/**
	 * @return
	 */
	public String getSubjseq() {
		return subjseq;
	}
	
	/**
	 * @param string
	 */
	public void setSubjseq(String string) {
		subjseq = string;
	}
		
	/**
	 * @return
	 */
	public String getCompnm() {
		return compnm;
	}
	
	/**
	 * @param string
	 */
	public void setCompnm(String string) {
		compnm = string;
	}
		
	/**
	 * @return
	 */
	public String getJikwinm() {
		return jikwinm;
	}
	
	/**
	 * @param string
	 */
	public void setJikwinm(String string) {
		jikwinm = string;
	}
		
	/**
	 * @return
	 */
	public String getUserid() {
		return userid;
	}
	
	/**
	 * @param string
	 */
	public void setUserid(String string) {
		userid = string;
	}
		
	/**
	 * @return
	 */
	public String getCono() {
		return cono;
	}
	
	/**
	 * @param string
	 */
	public void setCono(String string) {
		cono = string;
	}
		
	/**
	 * @return
	 */
	public String getOrga_ename() {
		return orga_ename;
	}
	
	/**
	 * @param string
	 */
	public void setOrga_ename(String string) {
		orga_ename = string;
	}
		
	/**
	 * @return
	 */
	public String getResno() {
		return resno;
	}
	
	/**
	 * @param string
	 */
	public void setResno(String string) {
		resno = string;
	}
		
	/**
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}
}