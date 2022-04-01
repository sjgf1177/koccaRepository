//**********************************************************
//1. 제      목: 코스코드 DATA
//2. 프로그램명: CourseData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: anonymous 2003. 07. 07
//7. 수      정: 
//                 
//**********************************************************
package com.credu.course;

public class CourseData {
	private String course;
	private String coursenm;
	private String courseclass;
	private String upperclass;
	private String middleclass;
	private String lowerclass;
	private String inuserid;
	private String indate;
	private int canceldays;
	private int gradscore;
	private int gradfailcnt;
	private String luserid;
	private String ldate;
	private int subjcnt; 
	private int biyong;
	
	//addon icarus-- courseseq data
	private String	cyear="";
	private String	courseseq="";
	private String	grcode="";
	private String	grcodenm="";
	private String	gyear ="";
	private String	grseq ="";
	private	String	propstart="";
	private	String	propend="";
	private	String	edustart="";
	private	String	eduend="";
	
	public CourseData() {};
	
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
	public int getGradfailcnt() {
		return gradfailcnt;
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
	public String getIndate() {
		return indate;
	}

	/**
	 * @return
	 */
	public String getInuserid() {
		return inuserid;
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
	public int getSubjcnt() {
		return subjcnt;
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
	 * @param i
	 */
	public void setGradfailcnt(int i) {
		gradfailcnt = i;
	}

	/**
	 * @param i
	 */
	public void setGradscore(int i) {
		gradscore = i;
	}

	/**
	 * @param string
	 */
	public void setIndate(String string) {
		indate = string;
	}

	/**
	 * @param string
	 */
	public void setInuserid(String string) {
		inuserid = string;
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
	public void setSubjcnt(int i) {
		subjcnt = i;
	}

	/**
	 * @return
	 */
	public String getCourseclass() {
		return courseclass;
	}

	/**
	 * @return
	 */
	public String getLowerclass() {
		return lowerclass;
	}

	/**
	 * @return
	 */
	public String getMiddleclass() {
		return middleclass;
	}

	/**
	 * @return
	 */
	public String getUpperclass() {
		return upperclass;
	}

	/**
	 * @param string
	 */
	public void setCourseclass(String string) {
		courseclass = string;
	}

	/**
	 * @param string
	 */
	public void setLowerclass(String string) {
		lowerclass = string;
	}

	/**
	 * @param string
	 */
	public void setMiddleclass(String string) {
		middleclass = string;
	}

	/**
	 * @param string
	 */
	public void setUpperclass(String string) {
		upperclass = string;
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
	public String getGyear() {
		return gyear;
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
	public void setGyear(String string) {
		gyear = string;
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

	public int getBiyong() {
		return biyong;
	}

	public void setBiyong(int biyong) {
		this.biyong = biyong;
	}

	
	public String getEduend() {
		return eduend;
	}

	public void setEduend(String eduend) {
		this.eduend = eduend;
	}

	public String getEdustart() {
		return edustart;
	}

	public void setEdustart(String edustart) {
		this.edustart = edustart;
	}

	public int getCanceldays() {
		return canceldays;
	}

	public void setCanceldays(int canceldays) {
		this.canceldays = canceldays;
	}
	
}
