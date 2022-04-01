//**********************************************************
//1. 제      목: 
//2. 프로그램명: SulmunPaperSubData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-21
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.research;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunPaperSubData {
	private String 	subj;
	private String 	grcode;
	private String     year;
	private String     subjseq; 

	private int    	sulpapernum;     
	private String 	sulpapernm;                           
	private int    	totcnt;                         
	private String 	sulnums;
	private String     sulmailing;

	private String     sulstart; 
	private String     sulend; 
	
	public SulmunPaperSubData() {}

	/**
	 * @return
	 */
	public String getSubj() {
		return subj;
	}

	/**
	 * @return
	 */
	public String getGrcode() {
		return grcode;
	}

	/**
	 * @return
	 */
	public String getYear() {
		return year;
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
	public String getSulpapername() {
		return sulpapernm;
	}

	/**
	 * @return
	 */
	public int getTotcnt() {
		return totcnt;
	}

	/**
	 * @return
	 */
	public String getSulnums() {
		return sulnums;
	}

	/**
	 * @return
	 */
	public String getSulmailing() {
		return sulmailing;
	}

	/**
	 * @return
	 */
	public String getSulstart() {
		return sulstart;
	}

	/**
	 * @return
	 */
	public String getSulend() {
		return sulend;
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
	public void setGrcode(String string) {
		grcode = string;
	}

	/**
	 * @param string
	 */
	public void setYear(String string) {
		year = string;
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
	public void setSulpapername(String string) {
		sulpapernm = string;
	}

	/**
	 * @param i
	 */
	public void setTotcnt(int i) {
		totcnt = i;
	}

	/**
	 * @param string
	 */
	public void setSulnums(String string) {
		sulnums = string;
	}

	/**
	 * @param string
	 */
	public void setSulmailing(String string) {
		sulmailing = string;
	}

	/**
	 * @param string
	 */
	public void setSulstart(String string) {
		sulstart = string;
	}

	/**
	 * @param string
	 */
	public void setSulend(String string) {
		sulend = string;
	}

}
