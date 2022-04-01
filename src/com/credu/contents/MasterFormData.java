//**********************************************************
//1. 제      목: 마스터폼 DATA
//2. 프로그램명: MasterFormData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 08. 13
//7. 수      정:
//
//**********************************************************
package com.credu.contents;


public class MasterFormData {

	private	String	subj;					//과정코드
	private	String	dir;					//서버위치
	private	String	subjnm;					//과정명
	private	String	ismfbranch;				//분기식여부
	private	String	iscentered;				//중앙정렬여부
	private	String	contenttype;			//ContentType(N/O:obc/S:scorm)
	private	String	contenttypetxt;			//ContentType(N/O:obc/S:scorm)
	private	int	cnt_module=0;				//모듈수
	private	int	cnt_lesson=0;				//차시수
	private	int	cnt_branch=0;				//분기수

	private	String  mftype;					//프레임구조
	private	int	width=1024;
	private	int	height=768;
	private	String	mfdlist;				//(Normal)차시리스트박스 출력여부
	private	String	fontobj="";				//(OBC) Tree Font Color

	private	String	otbgcolor;				//(OBC) Tree배경색
	private	String	mfgrdate;				//(Normal)Module출력여부
	private	boolean isLessonChangable;		//차시정보 추가/삭제 가능여부
	private	String	unchangableMaxLesson;	//수정/삭제 불가 최대차시
	private	String	server="";
	private	String	port="";
	private	String  eduurl;
	private	String	preurl;
	private	String	vodurl;
	private String  eduprocess;				//학습진행방식(step-by-step,random)
	private String  isoutsourcing;

	private	boolean	canModify = true;		//변경가능여부 (MasterFormBran.canModify()에서 결정

	public MasterFormData() {};



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
	 * @return
	 */
	public String getEduprocess() {
		return eduprocess;
	}

	public void setEduprocess(String string) {
		eduprocess = string;
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
	public int getHeight() {
		return height;
	}

	/**
	 * @return
	 */
	public String getMfdlist() {
		return mfdlist;
	}

	/**
	 * @return
	 */
	public String getMfgrdate() {
		return mfgrdate;
	}

	/**
	 * @return
	 */
	public String getMftype() {
		return mftype;
	}

	/**
	 * @return
	 */
	public String getOtbgcolor() {
		return otbgcolor;
	}

	/**
	 * @return
	 */
	public int getWidth() {
		return width;
	}



	/**
	 * @return
	 */
	public String getIsoutsourcing() {
		return isoutsourcing;
	}


	/**
	 * @param i
	 */
	public void setHeight(int i) {
		height = i;
	}

	/**
	 * @param string
	 */
	public void setMfdlist(String string) {
		mfdlist = string;
	}

	/**
	 * @param string
	 */
	public void setMfgrdate(String string) {
		mfgrdate = string;
	}

	/**
	 * @param string
	 */
	public void setMftype(String string) {
		mftype = string;
	}

	/**
	 * @param string
	 */
	public void setOtbgcolor(String string) {
		otbgcolor = string;
	}

	/**
	 * @param i
	 */
	public void setWidth(int i) {
		width = i;
	}

	/**
	 * @return
	 */
	public String getContenttypetxt() {
		return contenttypetxt;
	}

	/**
	 * @param string
	 */
	public void setContenttypetxt(String string) {
		contenttypetxt = string;
	}

	/**
	 * @return
	 */
	public boolean isLessonChangable() {
		return isLessonChangable;
	}

	/**
	 * @return
	 */
	public String getUnchangableMaxLesson() {
		return unchangableMaxLesson;
	}

	/**
	 * @param b
	 */
	public void setLessonChangable(boolean b) {
		isLessonChangable = b;
	}

	/**
	 * @param string
	 */
	public void setUnchangableMaxLesson(String string) {
		unchangableMaxLesson = string;
	}

	/**
	 * @return
	 */
	public int getCnt_branch() {
		return cnt_branch;
	}

	/**
	 * @param i
	 */
	public void setCnt_branch(int i) {
		cnt_branch = i;
	}

	/**
	 * @return
	 */
	public String getEduurl() {
		return eduurl;
	}

	/**
	 * @return
	 */
	public String getPort() {
		return port;
	}

	/**
	 * @return
	 */
	public String getPreurl() {
		return preurl;
	}

	/**
	 * @return
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @return
	 */
	public String getVodurl() {
		return vodurl;
	}

	/**
	 * @param string
	 */
	public void setEduurl(String string) {
		eduurl = string;
	}

	/**
	 * @param string
	 */
	public void setPort(String string) {
		port = string;
	}

	/**
	 * @param string
	 */
	public void setPreurl(String string) {
		preurl = string;
	}

	/**
	 * @param string
	 */
	public void setServer(String string) {
		server = string;
	}

	/**
	 * @param string
	 */
	public void setVodurl(String string) {
		vodurl = string;
	}
	/**
	 * @return
	 */
	public String getFontobj() {
		return fontobj;
	}
	/**
	 * @param string
	 */
	public void setFontobj(String string) {
		fontobj = string;
	}

	/**
	 * @return
	 */
	public boolean isCanModify() {
		return canModify;
	}

	/**
	 * @param b
	 */
	public void setCanModify(boolean b) {
		canModify = b;
	}

	/**
	 * @param string
	 */
	public void setIsoutsourcing(String string) {
		isoutsourcing = string;
	}

}


