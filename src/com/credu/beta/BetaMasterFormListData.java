//**********************************************************
//1. 제      목: 마스터폼리스트 DATA
//2. 프로그램명: BetaMasterFormListData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 08. 13
//7. 수      정: 
//                 
//**********************************************************
package com.credu.beta;

public class BetaMasterFormListData {
	
	private	String	subj;				//과정코드
	private	String	dir;				//서버위치
	private	String	subjnm;				//과정명
	private	String	ismfbranch;			//분기식여부
	private	String	iscentered;			//중앙정렬여부
	private	String	isuse;				//사용여부
	private	String	contenttype;		//ContentType(N/O:obc/S:scorm)
	private	int	cnt_module=0;		//모듈수
	private	int	cnt_lesson=0;		//차시수
	/* addon */
	private	String	contenttypenm="";
		

	
	public BetaMasterFormListData() {};
	


	/**
	 * @return
	 */
	public int getCnt_lesson() {
		return cnt_lesson;
	}

	/**
	 * @return
	 */
	public int getCnt_module() {
		return cnt_module;
	}

	/**
	 * @return
	 */
	public String getContenttype() {
		return contenttype;
	}

	/**
	 * @return
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @return
	 */
	public String getIscentered() {
		return iscentered;
	}

	/**
	 * @return
	 */
	public String getIsmfbranch() {
		return ismfbranch;
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
	public String getSubjnm() {
		return subjnm;
	}

	/**
	 * @param i
	 */
	public void setCnt_lesson(int i) {
		cnt_lesson = i;
	}

	/**
	 * @param i
	 */
	public void setCnt_module(int i) {
		cnt_module = i;
	}

	/**
	 * @param string
	 */
	public void setContenttype(String string) {
		contenttype = string;
	}

	/**
	 * @param string
	 */
	public void setDir(String string) {
		dir = string;
	}

	/**
	 * @param string
	 */
	public void setIscentered(String string) {
		iscentered = string;
	}

	/**
	 * @param string
	 */
	public void setIsmfbranch(String string) {
		ismfbranch = string;
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
	public void setSubjnm(String string) {
		subjnm = string;
	}

	/**
	 * @return
	 */
	public String getIsuse() {
		return isuse;
	}

	/**
	 * @param string
	 */
	public void setIsuse(String string) {
		isuse = string;
	}

	/**
	 * @return
	 */
	public String getContenttypenm() {
		return contenttypenm;
	}

	/**
	 * @param string
	 */
	public void setContenttypenm(String string) {
		contenttypenm = string;
	}
}
