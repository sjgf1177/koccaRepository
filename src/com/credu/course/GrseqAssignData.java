//**********************************************************
//1. 제      목: 교육차수 지정 과정/코스코드 DATA
//2. 프로그램명: GrseqAssignData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 07. 22
//7. 수      정: 
//                 
//**********************************************************
package com.credu.course;

import java.util.*;

public class GrseqAssignData {
	private String  grcode ;                        
	private String  gyear  ;                        
	private String  grseq  ;                        
	private String  grseqnm; 
	private String	 subjcourse;
	private String	 isonoff;
	private String	 iscourse;
	private String	 subjcoursenm;
	private String	 subjclass;
	private String	 subjclassnm;
	private String	 isUsing	= "N";
	private int	 cnt_using;
	private int	 disseq;
	private String	 isnew;
	private String	 isuse;    
	private String	 ldateyear;
	
	public GrseqAssignData() {};
	


	
	/**
	 * @return
	 */
	public int getCnt_using() {
		return cnt_using;
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
	public String getIscourse() {
		return iscourse;
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
	public String getisUsing() {
		return isUsing;
	}

	/**
	 * @return
	 */
	public String getSubjclass() {
		return subjclass;
	}

	/**
	 * @return
	 */
	public String getSubjclassnm() {
		return subjclassnm;
	}

	/**
	 * @return
	 */
	public String getSubjcourse() {
		return subjcourse;
	}

	/**
	 * @return
	 */
	public String getSubjcoursenm() {
		return subjcoursenm;
	}

	/**
	 * @param i
	 */
	public void setCnt_using(int i) {
		cnt_using = i;
		if (i > 0)	isUsing = "Y";
		else		isUsing = "N";
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
	public void setIscourse(String string) {
		iscourse = string;
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
	public void setisUsing(String string) {
		isUsing = string;
	}

	/**
	 * @param string
	 */
	public void setSubjclass(String string) {
		subjclass = string;
	}

	/**
	 * @param string
	 */
	public void setSubjclassnm(String string) {
		subjclassnm = string;
	}

	/**
	 * @param string
	 */
	public void setSubjcourse(String string) {
		subjcourse = string;
	}

	/**
	 * @param string
	 */
	public void setSubjcoursenm(String string) {
		subjcoursenm = string;
	}

	/**
	 * @return
	 */
	public int getDisseq() {
		return disseq;
	}

	/**
	 * @return
	 */
	public String getIsnew() {
		return isnew;
	}
	
	/**
	 * @return
	 */
	public String getIsuse() {
		return isuse;
	}
	
	/**
	 * @return
	 */
	public String getLdateyear() {
		return ldateyear;
	}

	/**
	 * @param i
	 */
	public void setDisseq(int i) {
		disseq = i;
	}

	/**
	 * @param string
	 */
	public void setIsnew(String string) {
		isnew = string;
	}
	
	/**
	 * @param string
	 */
	public void setIsuse(String string) {
		isuse = string;
	}
	
	/**
	 * @param string
	 */
	public void setLdateyear(String string) {
		ldateyear = string;
	}

	/**
	 * @return
	 */
	public String getIsUsing() {
		return isUsing;
	}

	/**
	 * @param string
	 */
	public void setIsUsing(String string) {
		isUsing = string;
	}


}
