//**********************************************************
//1. 제      목: 
//2. 프로그램명: CourseScoreData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-09-19
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
public class CourseScoreData {
	private String course;
	private String cyear;
	private String courseseq;
	private String userid;
	private int    graduatedcnt;
	private String isgraduated; 
	private double score;
	
	private Hashtable SubjScoreDataList;

	public CourseScoreData() {
		course     = "";
		cyear      = "";
		courseseq  = "";
		userid     = "";
		graduatedcnt = 0;
		isgraduated = "";
		score      = 0; 
		
		SubjScoreDataList = new Hashtable();
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
	public int getGraduatedcnt() {
		return graduatedcnt;
	}

	/**
	 * @return
	 */
	public double getScore() {
		return score;
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
	public void setCourse(String string) {
		course = string;
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
	 * @param i
	 */
	public void setGraduatedcnt(int i) {
		graduatedcnt = i;
	}

	/**
	 * @param d
	 */
	public void setScore(double d) {
		score = d;
	}

	/**
	 * @param string
	 */
	public void setUserid(String string) {
		userid = string;
	}

	public SubjScoreData get(int index) {
		return (SubjScoreData)SubjScoreDataList.get(String.valueOf(index));
	}
	
	public void add(SubjScoreData data) {
		SubjScoreDataList.put(String.valueOf(SubjScoreDataList.size()), data);
	}

	public void remove(int index) {
		SubjScoreDataList.remove(String.valueOf(index));
	}
	
	public void clear() {
		SubjScoreDataList.clear();
	}
	
	public int size() {
		return SubjScoreDataList.size();
	}

	public Enumeration elements() {
		return SubjScoreDataList.elements();		
	}

	/**
	 * @return
	 */
	public String getIsgraduated() {
		return isgraduated;
	}

	/**
	 * @param string
	 */
	public void setIsgraduated(String string) {
		isgraduated = string;
	}

}
