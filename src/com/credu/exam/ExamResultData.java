/*
 * 작성된 날짜: 2003-09-07
 *
 * 이 생성된 파일에 대한 템플리트를 변경하려면 다음으로 이동하십시오.
 * 창&gt;환경설정&gt;Java&gt;코드 생성&gt;코드 및 주석
 */
package com.credu.exam;

/**
 * @author sghong
 *
 * 이 생성된 유형 주석에 대한 템플리트를 변경하려면 다음으로 이동하십시오.
 * 창&gt;환경설정&gt;Java&gt;코드 생성&gt;코드 및 주석
 */
public class ExamResultData {
	private String  subj;
	private String  year;
	private String  subjseq;
	private String  subjseqgr;
	private String  lesson;
	private String  examtype;
	private int     papernum;
	private String  userid;
	private String  exam;
	private int examcnt;
	private int exampoint;
	private int score;
	private int answercnt;
	private int userretry;
	private int retrycnt;
	private String  started;
	private String  ended;
	private double  time;
    private String answer;
    private String corrected;
	
	private String subjnm;
	private String asgnnm;
	private String companynm;	
	private String jikwinm;
	private String cono;
	private String name;
	private String status;
	private String eduend;
	private String membergubunnm;

	public ExamResultData() {
		subj 		= "";
		year 		= "";
		subjseq 	= "";
		subjseqgr	= "";		
		lesson 		= "";
		examtype 		= "";
		papernum 	= 0;
		userid 		= "";
		exam 		= "";
		examcnt 	= 0;
		exampoint 	= 0;
		score 	= 0;
		answercnt 	= 0;
		userretry   = 0;
		retrycnt   = 0;
		started 	= "";
		ended 		= "";
		time 		= 0.0;
		answer 		= "";
		corrected 		= "";

		subjnm 		= "";	
		asgnnm 		= "";
		jikwinm 	= "";
		cono 		= "";
		name 		= "";
		status 		= "";
		membergubunnm = "";
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
	public String getYear() {
		return year;
	}
	
	/**
	 * @return
	 */
	public String getSubjseq() {
		return subjseq;
	}
	
	public String getSubjseqgr() {
		return subjseqgr;
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
	public String getExamtype() {
		return examtype;
	}

	/**
	 * @return
	 */
	public int getPapernum() {
		return papernum;
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
	public String getExam() {
		return exam;
	}

	/**
	 * @return
	 */
	public int getExamcnt() {
		return examcnt;
	}

	/**
	 * @return
	 */
	public int getExampoint() {
		return exampoint;
	}

	/**
	 * @return
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return
	 */
	public int getAnswercnt() {
		return answercnt;
	}

	/**
	 * @return
	 */
	public int getUserretry() {
		return userretry;
	}
	
	/**
	 * @return
	 */
	public int getRetrycnt() {
		return retrycnt;
	}
	
	/**
	 * @return
	 */
	public String getStarted() {
		return started;
	}

	/**
	 * @return
	 */
	public String getEnded() {
		return ended;
	}

	/**
	 * @return
	 */
	public double getTime() {
		return time;
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
	public String getCorrected() {
		return corrected;
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
	public String getAsgnnm() {
		return asgnnm;
	}

	/**
	 * @return
	 */
	public String getJikwinm() {
		return jikwinm;
	}

	/**
	 * @return
	 */
	public String getCono() {
		return cono;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public String getStatus() {
		return status;
	}

	public String getEduEnd() {
		return eduend;
	}
	
	public String getMembergubunnm() {
		return membergubunnm;
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
	public void setYear(String string) {
		year = string;
	}
	
	/**
	 * @param string
	 */
	public void setSubjseq(String string) {
		subjseq = string;
	}
	
	public void setSubjseqgr(String string) {
		subjseqgr = string;
	}

	/**
	 * @param string
	 */
	public void setLesson(String string) {
		lesson = string;
	}

	/**
	 * @param string
	 */
	public void setExamtype(String string) {
		examtype = string;
	}

	/**
	 * @param i
	 */
	public void setPapernum(int i) {
		papernum = i;
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
	public void setExam(String string) {
		exam = string;
	}

	/**
	 * @param i
	 */
	public void setExamcnt(int i) {
		examcnt = i;
	}

	/**
	 * @param i
	 */
	public void setExampoint(int i) {
		exampoint = i;
	}

	/**
	 * @param i
	 */
	public void setScore(int i) {
		score = i;
	}

	/**
	 * @param i
	 */
	public void setAnswercnt(int i) {
		answercnt = i;
	}
	
	/**
	 * @param i
	 */
	public void setUserretry(int i) {
		userretry = i;
	}
	
	/**
	 * @param i
	 */
	public void setRetrycnt(int i) {
		retrycnt = i;
	}

	/**
	 * @param string
	 */
	public void setStarted(String string) {
		started = string;
	}

	/**
	 * @param string
	 */
	public void setEnded(String string) {
		ended = string;
	}

	/**
	 * @param d
	 */
	public void setTime(double d) {
		time = d;
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
	public void setCorrected(String string) {
		corrected = string;
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
	public void setAsgnnm(String string) {
		asgnnm = string;
	}

	/**
	 * @param string
	 */
	public void setJikwinm(String string) {
		jikwinm = string;
	}

	/**
	 * @param string
	 */
	public void setCono(String string) {
		cono = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setStatus(String string) {
		status = string;
	}
	
	public void setEduEnd(String string) {
		eduend = string;
	}

	public String getCompanynm()               { return companynm; }	
	public void setCompanynm(String companynm) { this.companynm = companynm; }
	
	public void setMembergubunnm(String string) {
		membergubunnm = string;
	}
	
}
