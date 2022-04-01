//**********************************************************
//1. 제      목: 
//2. 프로그램명: SubjScoreData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-09-19
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.complete;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SubjScoreData {
	private String subj;
	private String year;
	private String subjseq;
	private String userid;
	private String isgraduated;
	private double score; 
	
	public SubjScoreData() {
		subj        = "";
		year        = "";
		subjseq     = "";
		userid      = "";
		isgraduated = "";
		score       = 0; 
	}
	
	/**
	 * @return
	 */
	public String getIsgraduated() {
		return isgraduated;
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
	public String getSubj() {
		return subj;
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
	public String getYear() {
		return year;
	}

	/**
	 * @param string
	 */
	public void setIsgraduated(String string) {
		isgraduated = string;
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
	public void setSubj(String string) {
		subj = string;
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
	public void setYear(String string) {
		year = string;
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

}
