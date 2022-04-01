/*
 * 작성된 날짜: 2003-09-07
 *
 * 이 생성된 파일에 대한 템플리트를 변경하려면 다음으로 이동하십시오.
 * 창&gt;환경설정&gt;Java&gt;코드 생성&gt;코드 및 주석
 */
package com.credu.etest;

/**
 * @author sghong
 *
 * 이 생성된 유형 주석에 대한 템플리트를 변경하려면 다음으로 이동하십시오.
 * 창&gt;환경설정&gt;Java&gt;코드 생성&gt;코드 및 주석
 */
public class ETestResultData {
	private String  subj;
	private String  year;
	private String  subjseq;
	private String  lesson;
	private String  etesttype;
	private int     papernum;
	private String  userid;
	private String  etest;
	private int etestcnt;
	private int etestpoint;
	private int score;
	private int answercnt;
	private String  started;
	private String  ended;
	private double  time;
    private String answer;
    private String corrected;
	
	private String subjnm;
	private String asgnnm;
	private String jikwinm;
	private String cono;
	private String name;
	private String status;
	private String companynm;
	
	public ETestResultData() {
		subj 		= "";
		year 		= "";
		subjseq 	= "";
		lesson 		= "";
		etesttype 		= "";
		papernum 	= 0;
		userid 		= "";
		etest 		= "";
		etestcnt 	= 0;
		etestpoint 	= 0;
		score 	= 0;
		answercnt 	= 0;
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
		companynm 		= "";		
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

	/**
	 * @return
	 */
	public String getLesson() {
		return lesson;
	}

	/**
	 * @return
	 */
	public String getETesttype() {
		return etesttype;
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
	public String getETest() {
		return etest;
	}

	/**
	 * @return
	 */
	public int getETestcnt() {
		return etestcnt;
	}

	/**
	 * @return
	 */
	public int getETestpoint() {
		return etestpoint;
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

	/**
	 * @param string
	 */
	public void setLesson(String string) {
		lesson = string;
	}

	/**
	 * @param string
	 */
	public void setETesttype(String string) {
		etesttype = string;
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
	public void setETest(String string) {
		etest = string;
	}

	/**
	 * @param i
	 */
	public void setETestcnt(int i) {
		etestcnt = i;
	}

	/**
	 * @param i
	 */
	public void setETestpoint(int i) {
		etestpoint = i;
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
	
	public String getCompanynm() { return this.companynm; }	
	public void setCompanynm(String string) { this.companynm = string; }
}
