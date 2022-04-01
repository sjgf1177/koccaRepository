//**********************************************************
//1. 제      목: 마스터폼리스트 DATA
//2. 프로그램명: MasterFormListData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 08. 13
//7. 수      정:
//
//**********************************************************
package com.credu.contents;

public class MasterFormListData {

	private String  subj;               //과정코드
	private String  dir;                //서버위치
	private String  subjnm;             //과정명
	private String  ismfbranch;         //분기식여부
	private String  iscentered;         //중앙정렬여부
	private String  isuse;              //사용여부
	private String  contenttype;        //ContentType(N/O:obc/S:scorm)
	private String  mftype;             //메뉴프레임 위치
	private String  mftypenm;             //메뉴프레임 위치
	private String  width;              //창크기(WIDTH)
	private String  height;             //창크기(HEIGHT)
	private String  eduprocess;         //학습진행방식
	private String  otbgcolor;          //트리메뉴배경색
	private String  isvisible;          //학습자에게 보여주기
	private String  isoutsourcing;      //외주여부

	private int cnt_module=0;       //모듈수
	private int cnt_lesson=0;       //차시수
	/* addon */
	private String  contenttypenm="";
	//SERVER,a.PORT,a.EDUURL
	private String  server="",port="",eduurl="";

	public String getServer() {
		return server;
	}



	public void setServer(String server) {
		this.server = server;
	}



	public String getPort() {
		return port;
	}



	public void setPort(String port) {
		this.port = port;
	}



	public String getEduurl() {
		return eduurl;
	}



	public void setEduurl(String eduurl) {
		this.eduurl = eduurl;
	}



	public String getMftypenm() {
		return mftypenm;
	}



	public void setMftypenm(String mftypenm) {
		this.mftypenm = mftypenm;
	}



	public MasterFormListData() {};



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


	/**
	 * @return
	 */
	public String getMftype() {
		return mftype;
	}

	/**
	 * @param string
	 */
	public void setMftype(String string) {
		mftype = string;
	}



	/**
	 * @return
	 */
	public String getWidth() {
		return width;
	}

	/**
	 * @param string
	 */
	public void setWidth(String string) {
		width = string;
	}

	/**
	 * @return
	 */
	public String getHeight() {
		return height;
	}

	/**
	 * @param string
	 */
	public void setHeight(String string) {
		height = string;
	}

	/**
	 * @return
	 */
	public String getEduprocess() {
		return eduprocess;
	}

	/**
	 * @param string
	 */
	public void setEduprocess(String string) {
		eduprocess = string;
	}

	/**
	 * @return
	 */
	public String getOtbgcolor() {
		return otbgcolor;
	}

	/**
	 * @param string
	 */
	public void setOtbgcolor(String string) {
		otbgcolor = string;
	}

	/**
	 * @return
	 */
	public String getIsvisible() {
		return isvisible;
	}

	/**
	 * @param string
	 */
	public void setIsvisible(String string) {
		isvisible = string;
	}



	public String getIsoutsourcing() {
		return isoutsourcing;
	}



	public void setIsoutsourcing(String isoutsourcing) {
		this.isoutsourcing = isoutsourcing;
	}

}



