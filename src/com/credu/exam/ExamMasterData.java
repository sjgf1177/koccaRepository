//**********************************************************
//1. 제      목: 
//2. 프로그램명: ExamMasterData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-09-03
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
public class ExamMasterData {
	private String  subj;          /* 과정코드 */
	private String  year;          /* 과정연도(과정평가인경우:TEST) */
	private String  subjseq;       /* 과정차수(과정평가인경우:TEST) */
	private String  lesson;        /* 차시(온라인인 경우:OT) 		*/
	private String  ptype;         /* 평가 type : [중간|최종|온라인테스트] */
	private int     cnttotal;      /* 평가수(총평가갯수)	*/
	private String  partstart;     /* 평가범위(시작일차)	*/
	private String  partend;       /* 평가범위(종료일차)	*/
	private int     cntlevel1;     /* 문항수(상)	*/
	private int     cntlevel2;     /* 문항수(중)	*/
	private int     cntlevel3;     /* 문항수(하)	*/
	private String  testdate;      /* test 일시 :온라인/ 실시간 아니면 0으로 set */
	private int     testtime;      /* 소요시간	*/
	private int     jointotal;     /* 응시자수	*/
	private String  f_multiex;     /* 복수문제지 여부:온라인평가일경우에만 적용*/
	private String  f_expurl;      /* 결과화면에 넣을수 없을 만큼의 많은 양의 해설이 필요한 경우는 그 페이지를 작성하여 url링크를 걸어줌*/
	private String  f_usehtml;     /* 문제지를 별도의 HTML로 사용할 경우 (어학과정등) */
	private String  ptypenm;        /* 평가 type 이름 */

	public ExamMasterData() {
		subj       = "";
		year       = "";
		subjseq    = "";
		lesson     = "";
		ptype      = "";
		cnttotal   = 0;
		partstart  = "";
		partend    = "";
		cntlevel1  = 0;
		cntlevel2  = 0;
		cntlevel3  = 0;
		testdate   = "";
		testtime   = 0;
		jointotal  = 0;
		f_multiex  = "";
		f_expurl   = "";
		f_usehtml  = "";
		ptypenm    = "";
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
	public int getCnttotal() {
		return cnttotal;
	}

	/**
	 * @return
	 */
	public String getF_expurl() {
		return f_expurl;
	}

	/**
	 * @return
	 */
	public String getF_multiex() {
		return f_multiex;
	}

	/**
	 * @return
	 */
	public String getF_usehtml() {
		return f_usehtml;
	}

	/**
	 * @return
	 */
	public int getJointotal() {
		return jointotal;
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
	 * @return
	 */
	public String getPtype() {
		return ptype;
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
	public String getTestdate() {
		return testdate;
	}

	/**
	 * @return
	 */
	public int getTesttime() {
		return testtime;
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
	 * @param i
	 */
	public void setCnttotal(int i) {
		cnttotal = i;
	}

	/**
	 * @param string
	 */
	public void setF_expurl(String string) {
		f_expurl = string;
	}

	/**
	 * @param string
	 */
	public void setF_multiex(String string) {
		f_multiex = string;
	}

	/**
	 * @param string
	 */
	public void setF_usehtml(String string) {
		f_usehtml = string;
	}

	/**
	 * @param i
	 */
	public void setJointotal(int i) {
		jointotal = i;
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
	 * @param string
	 */
	public void setPtype(String string) {
		ptype = string;
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
	public void setTestdate(String string) {
		testdate = string;
	}

	/**
	 * @param i
	 */
	public void setTesttime(int i) {
		testtime = i;
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
	public String getPtypenm() {
		return ptypenm;
	}

	/**
	 * @param string
	 */
	public void setPtypenm(String string) {
		ptypenm = string;
	}

}
