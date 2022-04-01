//**********************************************************
//1. 제      목: 공지화면용 DATA BEAN
//2. 프로그램명: NoticeData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 정상진 2003. 07. 22
//7. 수      정: 
//                 
//**********************************************************
package com.credu.homepage;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NoticeData {

	private int     seq         ;
	private int     cnt         ;
	private String  gubun       ;  //공지구분 Y : 전체 , N : 일반, P : 팝업
	private String  addate      ;
	private String  startdate   ;	
	private String  enddate     ;	
	private String  adtitle     ;
	private String  adname      ;
	private String  adcontent   ;
	private String  luserid;        // 최종수정자
	private String  ldate;          // 최종수정일
	private String  compnm;
	private String  comp;
	private String  loginyn;
	private String  useyn;
	private String  compcd;
	private int 	popwidth;
	private int 	popheight;
	private int 	popxpos;
	private int 	popypos; 	
	private String upfile;
	private String realfile;
	private String popup;
	private String uselist;
	private String useframe;
		
	private int dispnum;            // 총게시물수
	private int totalpagecount;     // 게시물총페이지수

	public  NoticeData() {}
	
	/**
	 * @return
	 */
	public String getAdcontent() {
		return adcontent;
	}

	/**
	 * @return
	 */
	public String getAddate() {
		return addate;
	}

	/**
	 * @return
	 */
	public String getAdname() {
		return adname;
	}

	/**
	 * @return
	 */
	public String getAdtitle() {
		return adtitle;
	}

	/**
	 * @return
	 */
	public int getCnt() {
		return cnt;
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
	public String getGubun() {
		return gubun;
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
	public int getTotalpagecount() {
		return totalpagecount;
	}

	/**
	 * @return
	 */
	
	public String getCompnm() {
		return compnm;
	}
	
	public String getComp() {
		return comp;
	}
	
	public String getLoginyn(){
		return loginyn;
	}
	
	public String getUseyn(){
		return useyn;
	}
	
	public String getCompcd(){
		return compcd;
	}
	
	public int getPopwidth(){
		return popwidth;
	}
	
	public int getPopheight(){
		return popheight;
	}
	
	public int getPopxpos(){
		return popxpos;
	}
	
	public int getPopypos(){
		return popypos;
	}

	public String getUpfile(){	
		return upfile;
	}
	
	public String getRealfile(){	
		return realfile;
	}
	
	public String getPopup(){	
		return popup;
	}
	
	/**
	 * @param string
	 */
	public void setAdcontent(String string) {
		adcontent = string;
	}

	/**
	 * @param string
	 */
	public void setAddate(String string) {
		addate = string;
	}

	/**
	 * @param string
	 */
	public void setAdname(String string) {
		adname = string;
	}

	/**
	 * @param string
	 */
	public void setAdtitle(String string) {
		adtitle = string;
	}

	/**
	 * @param i
	 */
	public void setCnt(int i) {
		cnt = i;
	}

	/**
	 * @param i
	 */
	public void setDispnum(int i) {
		dispnum = i;
	}

	/**
	 * @param string
	 */
	public void setGubun(String string) {
		gubun = string;
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
	 * @param i
	 */
	public void setTotalpagecount(int i) {
		totalpagecount = i;
	}

	/**
	 * @return
	 */
	public String getEnddate() {
		return enddate;
	}

	/**
	 * @return
	 */
	public String getStartdate() {
		return startdate;
	}

	/**
	 * @param string
	 */
	public void setEnddate(String string) {
		enddate = string;
	}

	/**
	 * @param string
	 */
	public void setStartdate(String string) {
		startdate = string;
	}
	
	public void setCompnm(String string) {
		compnm = string;
	}
	
	public void setComp(String string) {
		comp = string;
	}
	
	public void setLoginyn(String string) {
		loginyn = string;
	}
	
	public void setUseyn(String string) {
		useyn = string;
	}
	
	public void setCompcd(String string) {
		compcd = string;
	}
	
	public void setPopwidth(int string) {
		popwidth = string;
	}
	
	public void setPopheight(int string) {
		popheight = string;
	}
	
	public void setPopxpos(int string) {
		popxpos = string;
	}
	
	public void setPopypos(int string) {
		popypos = string;
	}
	
	/**
	 * @param string
	 */
	public void setUpfile(String string) {
		upfile = string;
	}
	public void setRealfile(String string) {
		realfile = string;
	}
	public void setPopup(String string) {
		popup = string;
	}
	
	public String getUselist() {
		return uselist;
	}
	public void setUselist(String string) {
		uselist = string;
	}
	
	public String getUseframe() {
		return useframe;
	}
	public void setUseframe(String string) {
		useframe = string;
	}
}
