//**********************************************************
//  1. ��      ��: ��ü�������� Data
//  2. ���α׷��� : WholeSulData.java
//  3. ��      ��: ��ü�������� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 9.  16
//  7. ��      ��:
//**********************************************************
package com.credu.homepage;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class WholeSulData {

	private String	grcode;
	private String	year;
	private String	subj;
	private String	subjseq;

	private int		sulpapernum;

	public WholeSulData() {}

	/**
	 * @return
	 */
	public String getGrcode() {
		return grcode;
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
	public int getSulpapernum() {
		return sulpapernum;
	}

	/**
	 * @return
	 */
	public String getYear() {
		return year;
	}

	/**
	 * @param string
	 */
	public void setGrcode(String string) {
		grcode = string;
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
	 * @param i
	 */
	public void setSulpapernum(int i) {
		sulpapernum = i;
	}

	/**
	 * @param string
	 */
	public void setYear(String string) {
		year = string;
	}

}
