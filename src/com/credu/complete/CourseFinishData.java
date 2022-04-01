//**********************************************************
//1. 제      목: 
//2. 프로그램명: CourseFinishData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-11
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.complete;

import java.util.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CourseFinishData {
	private String grcode;
	private String grcodenm;
	private String gyear;
	private String grseq;

	private String course;
	private String coursenm;
	private String cyear;
	private String courseseq;

	private String  coursecompletemsg;   // 코스 이수 메시지
	private double  coursecompleterate;  // 코스 이수율

	private int coursestudentcnt;       // 코스 교육생 수  
	private int coursegradcnt;          // 코스 수료생 수 
	
	private Hashtable    FinishDataList;
	
	public CourseFinishData() {
		grcode = "";
		grcodenm = "";
		gyear = "";
		grseq = "";

		course = "";
		coursenm = "";
		cyear = "";
		courseseq = "";

		coursecompletemsg = "";
		coursecompleterate = 0;
		
		coursestudentcnt = 0;
		coursegradcnt = 0;
		
		FinishDataList = new Hashtable();		
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
	public String getGyear() {
		return gyear;
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
	 * @return
	 */
	public String getCoursecompletemsg() {
		return coursecompletemsg;
	}

	/**
	 * @return
	 */
	public double getCoursecompleterate() {
		return coursecompleterate;
	}

	/**
	 * @param string
	 */
	public void setCoursecompletemsg(String string) {
		coursecompletemsg = string;
	}

	/**
	 * @param d
	 */
	public void setCoursecompleterate(double d) {
		coursecompleterate = d;
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
	public void setGyear(String string) {
		gyear = string;
	}
	
	public FinishData get(int index) {
		return (FinishData)FinishDataList.get(String.valueOf(index));
	}
	
	public void add(FinishData finishdata) {
		FinishDataList.put(String.valueOf(FinishDataList.size()), finishdata);
	}

	public void remove(int index) {
		FinishDataList.remove(String.valueOf(index));
	}
	
	public void clear() {
		FinishDataList.clear();
	}
	
	public int size() {
		return FinishDataList.size();
	}

	public Enumeration elements() {
		return FinishDataList.elements();		
	}
	/**
	 * @return
	 */
	public int getCoursegradcnt() {
		return coursegradcnt;
	}

	/**
	 * @return
	 */
	public int getCoursestudentcnt() {
		return coursestudentcnt;
	}

	/**
	 * @param i
	 */
	public void setCoursegradcnt(int i) {
		coursegradcnt = i;
	}

	/**
	 * @param i
	 */
	public void setCoursestudentcnt(int i) {
		coursestudentcnt = i;
	}

}
