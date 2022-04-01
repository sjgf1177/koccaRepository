//**********************************************************
//1. 제      목: 진도/목차 리스트Data
//2. 프로그램명: EduListData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 08. 26
//7. 수      정: 
//                 
//**********************************************************
package com.credu.contents;



public class EduListData {
	
	private String		recordType		 ;	//DataType('STEP':진도,'EXAM':평가)
	private String		subj             ;
	private String		year             ;
	private String		subjseq          ;
	private String		userid           ;
	private String		lesson           ;
	private String		sdesc			 ;
	private String	 	module			;
	private String	 	modulenm			;
	private String		isbranch;
	private String		oid              ;
	private String		oidnm            ;		
	private String		parameterstring  ;
	private String		lessonstatus     ;
	private String		prerequisites    ;
	private String		exit             ;
	private String		entry            ;
	private String		type             ;
	private String		credit           ;
	private String		session_time     ;
	private String		total_time       ;
	private String		lesson_mode      ;
	private String		first_edu        ;		//진도: 최초학습시작일시, 평가:평가시작일시
	private String		first_end        ;		//진도: 최초학습종료일시, 평가:평가종료일시
	private String		lessonstatusbest ;
	private String		ldate            ;
	private String      starting	     ;
	private String      examtype		 ;
//	private String		isbranched       ;
	private int		score_raw        =0;
	private int		masteryscore     =0;
	private int		sequence         =0;
	private int		lesson_count     =0;
	                	
	private int		cntReport = 0;			//리포트수
	private int		cntMyReport=0;			//제출리포트수
	private int		cntAct	= 0;			//액티비티수
	private int		cntMyAct = 0;			//제출액티비티수
	private String		isEducated = "N";		//학습여부
	                	
	private int 	[]	scoreExam;				//평가점수 Array		
	private int 	[]	scoreReport;			//Report점수 Array
	private int 	[]	scoreAct;				//Activity점수 Array
	private int 	[]	branchs;				//분기 Array
	private String	[]	branchnm;				//분기명 Array
	
	private String		ptype;					//평가일경우 평가타입
	private double	score=0;				//평가일경우 점수
	private int		rowspan=1;				
	private int		rowspan_lesson=1;				//Rowspan (Lesson)	

	private String 	edustart;
	private String 	fromdate;
	private String 	todate;	
	
	public EduListData() {};
	

	/**
	 * @return
	 */
	public String[] getBranchnm() {
		return branchnm;
	}

	/**
	 * @return
	 */
	public int[] getBranchs() {
		return branchs;
	}

	/**
	 * @return
	 */
	public int getCntAct() {
		return cntAct;
	}


	/**
	 * @return
	 */
	public int getCntMyAct() {
		return cntMyAct;
	}

	/**
	 * @return
	 */
	public int getCntMyReport() {
		return cntMyReport;
	}

	/**
	 * @return
	 */
	public int getCntReport() {
		return cntReport;
	}

	/**
	 * @return
	 */
	public String getCredit() {
		return credit;
	}

	/**
	 * @return
	 */
	public String getEntry() {
		return entry;
	}

	/**
	 * @return
	 */
	public String getExit() {
		return exit;
	}

	/**
	 * @return
	 */
	public String getFirst_edu() {
		return first_edu;
	}

	/**
	 * @return
	 */
	public String getFirst_end() {
		return first_end;
	}


	/**
	 * @return
	 */
	public String getIsEducated() {
		return isEducated;
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
	public String getStarting() {
		return starting;
	}

	/**
	 * @return
	 */
	public int getLesson_count() {
		return lesson_count;
	}

	/**
	 * @return
	 */
	public String getLesson_mode() {
		return lesson_mode;
	}

	/**
	 * @return
	 */
	public String getLessonstatus() {
		return lessonstatus;
	}

	/**
	 * @return
	 */
	public String getLessonstatusbest() {
		return lessonstatusbest;
	}

	/**
	 * @return
	 */
	public int getMasteryscore() {
		return masteryscore;
	}

	/**
	 * @return
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @return
	 */
	public String getParameterstring() {
		return parameterstring;
	}

	/**
	 * @return
	 */
	public String getPrerequisites() {
		return prerequisites;
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
	public int getScore_raw() {
		return score_raw;
	}

	/**
	 * @return
	 */
	public int[] getScoreAct() {
		return scoreAct;
	}

	/**
	 * @return
	 */
	public int[] getScoreExam() {
		return scoreExam;
	}

	/**
	 * @return
	 */
	public int[] getScoreReport() {
		return scoreReport;
	}

	/**
	 * @return
	 */
	public int getSequence() {
		return sequence;
	}

	/**
	 * @return
	 */
	public String getSession_time() {
		return session_time;
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
	public String getTotal_time() {
		return total_time;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
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
	 * @param strings
	 */
	public void setBranchnm(String[] strings) {
		branchnm = strings;
	}

	/**
	 * @param is
	 */
	public void setBranchs(int[] is) {
		branchs = is;
	}

	/**
	 * @param i
	 */
	public void setCntAct(int i) {
		cntAct = i;
	}

	/**
	 * @param i
	 */

	/**
	 * @param i
	 */
	public void setCntMyAct(int i) {
		cntMyAct = i;
	}

	/**
	 * @param i
	 */
	public void setCntMyReport(int i) {
		cntMyReport = i;
	}

	/**
	 * @param i
	 */
	public void setCntReport(int i) {
		cntReport = i;
	}

	/**
	 * @param string
	 */
	public void setCredit(String string) {
		credit = string;
	}
	
	/**
	 * @param string
	 */
	public void setExamtype(String string) {
		examtype = string;
	}
	
	/**
	 * @param string
	 */
	public void setEntry(String string) {
		entry = string;
	}

	/**
	 * @param string
	 */
	public void setExit(String string) {
		exit = string;
	}

	/**
	 * @param string
	 */
	public void setFirst_edu(String string) {
		first_edu = string;
	}

	/**
	 * @param string
	 */
	public void setFirst_end(String string) {
		first_end = string;
	}



	/**
	 * @param string
	 */
	public void setIsEducated(String string) {
		isEducated = string;
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
	public void setLesson_count(int i) {
		lesson_count = i;
	}

	/**
	 * @param string
	 */
	public void setLesson_mode(String string) {
		lesson_mode = string;
	}

	/**
	 * @param string
	 */
	public void setLessonstatus(String string) {
		lessonstatus = string;
	}

	/**
	 * @param string
	 */
	public void setLessonstatusbest(String string) {
		lessonstatusbest = string;
	}

	/**
	 * @param i
	 */
	public void setMasteryscore(int i) {
		masteryscore = i;
	}

	/**
	 * @param string
	 */
	public void setOid(String string) {
		oid = string;
	}

	/**
	 * @param string
	 */
	public void setParameterstring(String string) {
		parameterstring = string;
	}

	/**
	 * @param string
	 */
	public void setPrerequisites(String string) {
		prerequisites = string;
	}

	/**
	 * @param i
	 */
	public void setScore_raw(int i) {
		score_raw = i;
	}

	/**
	 * @param is
	 */
	public void setScoreAct(int[] is) {
		scoreAct = is;
	}

	/**
	 * @param is
	 */
	public void setScoreExam(int[] is) {
		scoreExam = is;
	}

	/**
	 * @param is
	 */
	public void setScoreReport(int[] is) {
		scoreReport = is;
	}

	/**
	 * @param i
	 */
	public void setSequence(int i) {
		sequence = i;
	}

	/**
	 * @param string
	 */
	public void setSession_time(String string) {
		session_time = string;
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
	public void setTotal_time(String string) {
		total_time = string;
	}

	/**
	 * @param string
	 */
	public void setType(String string) {
		type = string;
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
	public String getIsbranch() {
		return isbranch;
	}

	/**
	 * @return
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @return
	 */
	public String getModulenm() {
		return modulenm;
	}

	/**
	 * @return
	 */
	public String getSdesc() {
		return sdesc;
	}

	/**
	 * @param string
	 */
	public void setIsbranch(String string) {
		isbranch = string;
	}

	/**
	 * @param string
	 */
	public void setModule(String string) {
		module = string;
	}

	/**
	 * @param string
	 */
	public void setModulenm(String string) {
		modulenm = string;
	}

	/**
	 * @param string
	 */
	public void setSdesc(String string) {
		sdesc = string;
	}

	/**
	 * @return
	 */
	public String getRecordType() {
		return recordType;
	}

	/**
	 * @return
	 */
	public double getScore() {
		return score;
	}

	/**
	 * @param string
	 */
	public void setRecordType(String string) {
		recordType = string;
	}

	/**
	 * @param d
	 */
	public void setScore(double d) {
		score = d;
	}

	/**
	 * @return
	 */
	public String getPtype() {
		return ptype;
	}

	/**
	 * @param string
	 */
	public void setPtype(String string) {
		ptype = string;
	}
	/**
	 * @param string
	 */
	public void setOidnm(String string) {
		oidnm = string;
	}
	
	/**
	 * @param string
	 */
	public void setStarting(String string) {
		starting = string;
	}

	/**
	 * @return
	 */
	public String getOidnm() {
		return oidnm;
	}

	/**
	 * @return
	 */
	public int getRowspan() {
		return rowspan;
	}

	/**
	 * @param i
	 */
	public void setRowspan(int i) {
		rowspan = i;
	}

	/**
	 * @return
	 */
	public int getRowspan_lesson() {
		return rowspan_lesson;
	}
	/**
	 * @param i
	 */
	public void setRowspan_lesson(int i) {
		rowspan_lesson = i;
	}


	public String getEdustart() {
		return edustart;
	}


	public void setEdustart(String edustart) {
		this.edustart = edustart;
	}


	public String getFromdate() {
		return fromdate;
	}


	public void setFromdate(String fromdate) {
		this.fromdate = fromdate;
	}


	public String getTodate() {
		return todate;
	}


	public void setTodate(String todate) {
		this.todate = todate;
	}

}
