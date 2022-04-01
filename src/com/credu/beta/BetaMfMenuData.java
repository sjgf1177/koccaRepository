//**********************************************************
//1. 제      목: 마스터폼 메뉴 마스터 DATA
//2. 프로그램명: mfMenuData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 08. 13
//7. 수      정: 
//                 
//**********************************************************
package com.credu.beta;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class BetaMfMenuData {

	private	String	menu       ;
	private	String	menunm     ;
	private	String	isrequired ;
	private	String	isrequiredTxt="";
	private	String	pgm        ;
	private	String	pgmtype        ;
	private	String	pgram1     ;
	private	String	pgram2     ;
	private	String	pgram3     ;
	private	String	pgram4     ;
	private	String	pgram5     ;
	private	String	luserid    ;
	private	String	ldate      ;
	
	public BetaMfMenuData() {
	}

	/**
	 * @return
	 */
	public String getIsrequired() {
		return isrequired;
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
	public String getMenu() {
		return menu;
	}

	/**
	 * @return
	 */
	public String getMenunm() {
		return menunm;
	}

	/**
	 * @return
	 */
	public String getPgm() {
		return pgm;
	}

	/**
	 * @return
	 */
	public String getPgram1() {
		return pgram1;
	}

	/**
	 * @return
	 */
	public String getPgram2() {
		return pgram2;
	}

	/**
	 * @return
	 */
	public String getPgram3() {
		return pgram3;
	}

	/**
	 * @return
	 */
	public String getPgram4() {
		return pgram4;
	}

	/**
	 * @return
	 */
	public String getPgram5() {
		return pgram5;
	}

	/**
	 * @param string
	 */
	public void setIsrequired(String string) {
		isrequired = string;
		if(isrequired.equalsIgnoreCase("Y"))	setIsrequiredTxt("[Required]");
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
	 * @param string
	 */
	public void setMenu(String string) {
		menu = string;
	}

	/**
	 * @param string
	 */
	public void setMenunm(String string) {
		menunm = string;
	}

	/**
	 * @param string
	 */
	public void setPgm(String string) {
		pgm = string;
	}

	/**
	 * @param string
	 */
	public void setPgram1(String string) {
		pgram1 = string;
	}

	/**
	 * @param string
	 */
	public void setPgram2(String string) {
		pgram2 = string;
	}

	/**
	 * @param string
	 */
	public void setPgram3(String string) {
		pgram3 = string;
	}

	/**
	 * @param string
	 */
	public void setPgram4(String string) {
		pgram4 = string;
	}

	/**
	 * @param string
	 */
	public void setPgram5(String string) {
		pgram5 = string;
	}

	/**
	 * @return
	 */
	public String getIsrequiredTxt() {
		return isrequiredTxt;
	}

	/**
	 * @param string
	 */
	public void setIsrequiredTxt(String string) {
		isrequiredTxt = string;
	}

	/**
	 * @return
	 */
	public String getPgmtype() {
		return pgmtype;
	}

	/**
	 * @param string
	 */
	public void setPgmtype(String string) {
		pgmtype = string;
	}

}
