//**********************************************************
//1. 제      목: 
//2. 프로그램명: BetaSubjectInfoData.java
//3. 개      요:
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: Administrator 2004-12-26
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
public class BetaSubjectInfoData {
	private String subj;
	private String subjnm;
	private String upperclass;
	private String classname;
	private String isonoff;
	private String isonoffnm;
	private String displayname;

	public BetaSubjectInfoData() {
		subj = "";
		subjnm = "";
		upperclass = "";
		classname = "";
		isonoff = "";
		isonoffnm = "";
		displayname = "";
	}

	public String getDisplayname() {
		if (!displayname.equals("")) { 
			return displayname;
		} else {
			return "[" + isonoffnm + "]" + classname + "[" + subj + "]" + subjnm;
		}			
	}

	public void setDisplayname(String string) {
		displayname = string;
	}

	/**
	 * @return
	 */
	public String getClassname() {
		return classname;
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
	public String getUpperclass() {
		return upperclass;
	}

	/**
	 * @param string
	 */
	public void setClassname(String string) {
		classname = string;
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
	 * @param string
	 */
	public void setUpperclass(String string) {
		upperclass = string;
	}
	
	/**
	 * @return
	 */
	public String getIsonoff() {
		return isonoff;
	}

	/**
	 * @param string
	 */
	public void setIsonoff(String string) {
		isonoff = string;
	}
	/**
	 * @return
	 */
	public String getIsonoffnm() {
		return isonoffnm;
	}

	/**
	 * @param string
	 */
	public void setIsonoffnm(String string) {
		isonoffnm = string;
	}

}
