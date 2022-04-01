//******************x****************************************
//1. 제      목: 의견달기 DATA
//2. 프로그램명: OpinionData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 정 상 진 2003.11.4
//7. 수      정: 
//                 
//**********************************************************
package com.credu.contents;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OpinionData {

	private	String	subj     ;
	private	String	year     ;
	private	String	subjseq  ;
	private	String	lesson   ;
	private	String	userid   ;
	private	String	name     ;
	private	String	answer   ;
	private	String	luserid  ;
	private	String	ldate    ;
	private	int	lessonseq    ;
	private	int	seq          ;

	private int dispnum;          // 총게시물수
	private int totalpagecount;   // 게시물총페이지수
	
	public OpinionData() {
	}

	/**
	 * @return
	 */
	public String getAnswer() {
		return answer;
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
	public String getLesson() {
		return lesson;
	}

	/**
	 * @return
	 */
	public int getLessonseq() {
		return lessonseq;
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
	public int getSeq() {
		return seq;
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
	public void setAnswer(String string) {
		answer = string;
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
	public void setLesson(String string) {
		lesson = string;
	}

	/**
	 * @param i
	 */
	public void setLessonseq(int i) {
		lessonseq = i;
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
	public void setSeq(int i) {
		seq = i;
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
	public void setUserid(String string) {
		userid = string;
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
	public String getName() {
		return name;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @return
	 */
	public int getDispnum() {
		return dispnum;
	}

	/**
	 * @return
	 */
	public int getTotalpagecount() {
		return totalpagecount;
	}

	/**
	 * @param i
	 */
	public void setDispnum(int i) {
		dispnum = i;
	}

	/**
	 * @param i
	 */
	public void setTotalpagecount(int i) {
		totalpagecount = i;
	}

}

