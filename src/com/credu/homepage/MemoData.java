//**********************************************************
//  1. 제      목: Memo Data
//  2. 프로그램명 : ContactData.java
//  3. 개      요: Memo data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7.  2
//  7. 수      정:
//**********************************************************
package com.credu.homepage;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class MemoData {

	private int    memocode      ;
	private String sender        ;
	private String sendernm      ;
	private String receiver      ;
	private String receivernm    ;
	private String sdate         ;
	private String viewdate      ;
	private String viewyn        ;
	private String memosubj      ;
	private String contents      ;
	private String sendgubun     ;
	private String receivegubun  ;

	private int dispnum;            // 총게시물수
	private int totalpagecount;     // 게시물총페이지수

	public MemoData() {}
	/**
	 * @return
	 */
	public String getContents() {
		return contents;
	}

	/**
	 * @return
	 */
	public int getMemocode() {
		return memocode;
	}

	/**
	 * @return
	 */
	public String getMemosubj() {
		return memosubj;
	}

	/**
	 * @return
	 */
	public String getReceivegubun() {
		return receivegubun;
	}

	/**
	 * @return
	 */
	public String getReceiver() {
		return receiver;
	}

	/**
	 * @return
	 */
	public String getReceivernm() {
		return receivernm;
	}

	/**
	 * @return
	 */
	public String getSdate() {
		return sdate;
	}

	/**
	 * @return
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @return
	 */
	public String getSendernm() {
		return sendernm;
	}

	/**
	 * @return
	 */
	public String getSendgubun() {
		return sendgubun;
	}

	/**
	 * @return
	 */
	public String getViewdate() {
		return viewdate;
	}

	/**
	 * @param string
	 */
	public void setContents(String string) {
		contents = string;
	}

	/**
	 * @param string
	 */
	public void setMemocode(int i) {
		memocode = i;
	}

	/**
	 * @param string
	 */
	public void setMemosubj(String string) {
		memosubj = string;
	}

	/**
	 * @param string
	 */
	public void setReceivegubun(String string) {
		receivegubun = string;
	}

	/**
	 * @param string
	 */
	public void setReceiver(String string) {
		receiver = string;
	}

	/**
	 * @param string
	 */
	public void setReceivernm(String string) {
		receivernm = string;
	}

	/**
	 * @param string
	 */
	public void setSdate(String string) {
		sdate = string;
	}

	/**
	 * @param string
	 */
	public void setSender(String string) {
		sender = string;
	}

	/**
	 * @param string
	 */
	public void setSendernm(String string) {
		sendernm = string;
	}

	/**
	 * @param string
	 */
	public void setSendgubun(String string) {
		sendgubun = string;
	}

	/**
	 * @param string
	 */
	public void setViewdate(String string) {
		viewdate = string;
	}

	/**
	 * @return
	 */
	public String getViewyn() {
		return viewyn;
	}

	/**
	 * @param string
	 */
	public void setViewyn(String string) {
		viewyn = string;
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
