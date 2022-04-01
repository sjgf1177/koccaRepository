//**********************************************************
//1. 제      목: 
//2. 프로그램명: ExamPaperData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-09-04
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.exam;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ExamPaperData {
	private String subj;
	private String year;
	private String subjseq;
	private String lesson;
	private String ptype;
	private int    branch;
	private String sdesc;
	private int    papernum;
	private String gubun;
	private int    cnttotal;
	private double grbase;
	private String examsubjs;
	private String grexamsubjs;
	private String htmlurl;
	private String pstart;
	private String pend;
	private int    testtime;

	private String ptypenm;
	private int    examstudentcnt;
	private int    studentcnt;
	
	private String partstart;
	private String partend;
	private int    cntlevel1;
	private int    cntlevel2;
	private int    cntlevel3;
	
	public ExamPaperData() {
		subj          = "";     
		year          = ""; 
		subjseq       = ""; 
		lesson        = ""; 
		ptype         = ""; 
		branch        = 0;    
		papernum      = 0;    
		cnttotal      = 0;    
		grbase        = 0;    
		examsubjs     = ""; 
		grexamsubjs   = ""; 
		htmlurl       = ""; 
		pstart        = ""; 
		pend          = ""; 
		ptypenm       = ""; 
		examstudentcnt= 0;    
		studentcnt    = 0;    
		partstart     = "";
		partend       = "";
		cntlevel1     = 0;
		cntlevel2     = 0;
		cntlevel3     = 0;
	}
	
	/**
	 * @return
	 */
	public int getBranch() {
		return branch;
	}

	/**
	 * @return
	 */
	public int getCnttotal() {
		return cnttotal;
	}

	/**
	 * @return
	 */
	public int getExamstudentcnt() {
		return examstudentcnt;
	}

	/**
	 * @return
	 */
	public String getExamsubjs() {
		return examsubjs;
	}

	/**
	 * @return
	 */
	public double getGrbase() {
		return grbase;
	}

	/**
	 * @return
	 */
	public String getGrexamsubjs() {
		return grexamsubjs;
	}

	/**
	 * @return
	 */
	public String getHtmlurl() {
		return htmlurl;
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
	public int getPapernum() {
		return papernum;
	}

	/**
	 * @return
	 */
	public String getPend() {
		return pend;
	}

	/**
	 * @return
	 */
	public String getPstart() {
		return pstart;
	}

	/**
	 * @return
	 */
	public String getPtype() {
		return ptype;
	}

	/**
	 * @return
	 */
	public String getPtypenm() {
		return ptypenm;
	}

	/**
	 * @return
	 */
	public int getStudentcnt() {
		return studentcnt;
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
	 * @param i
	 */
	public void setBranch(int i) {
		branch = i;
	}

	/**
	 * @param i
	 */
	public void setCnttotal(int i) {
		cnttotal = i;
	}

	/**
	 * @param i
	 */
	public void setExamstudentcnt(int i) {
		examstudentcnt = i;
	}

	/**
	 * @param string
	 */
	public void setExamsubjs(String string) {
		examsubjs = string;
	}

	/**
	 * @param i
	 */
	public void setGrbase(double d) {
		grbase = d;
	}

	/**
	 * @param string
	 */
	public void setGrexamsubjs(String string) {
		grexamsubjs = string;
	}

	/**
	 * @param string
	 */
	public void setHtmlurl(String string) {
		htmlurl = string;
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
	public void setPapernum(int i) {
		papernum = i;
	}

	/**
	 * @param string
	 */
	public void setPend(String string) {
		pend = string;
	}

	/**
	 * @param string
	 */
	public void setPstart(String string) {
		pstart = string;
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
	public void setPtypenm(String string) {
		ptypenm = string;
	}

	/**
	 * @param i
	 */
	public void setStudentcnt(int i) {
		studentcnt = i;
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
	public int getCntlevel1() {
		return cntlevel1;
	}

	/**
	 * @return
	 */
	public int getCntlevel2() {
		return cntlevel2;
	}

	/**
	 * @return
	 */
	public int getCntlevel3() {
		return cntlevel3;
	}

	/**
	 * @return
	 */
	public String getPartend() {
		return partend;
	}

	/**
	 * @return
	 */
	public String getPartstart() {
		return partstart;
	}

	/**
	 * @param i
	 */
	public void setCntlevel1(int i) {
		cntlevel1 = i;
	}

	/**
	 * @param i
	 */
	public void setCntlevel2(int i) {
		cntlevel2 = i;
	}

	/**
	 * @param i
	 */
	public void setCntlevel3(int i) {
		cntlevel3 = i;
	}

	/**
	 * @param string
	 */
	public void setPartend(String string) {
		partend = string;
	}

	/**
	 * @param string
	 */
	public void setPartstart(String string) {
		partstart = string;
	}

	/**
	 * @return
	 */
	public String getGubun() {
		return gubun;
	}

	/**
	 * @param string
	 */
	public void setGubun(String string) {
		gubun = string;
	}

	/**
	 * @return
	 */
	public int getTesttime() {
		return testtime;
	}

	/**
	 * @param i
	 */
	public void setTesttime(int i) {
		testtime = i;
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
	public void setSdesc(String string) {
		sdesc = string;
	}

}
