//**********************************************************
//1. ��      ��: 
//2. ���α׷���: SubjectInfoData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-07-11
//7. ��      ��: 
//                 
//********************************************************** 
 
package com.credu.course;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SubjectInfoData {
	private String subj;
	private String subjnm;
	private String upperclass;
	private String classname;
	private String isonoff;
	private String isonoffnm;
	private String displayname;

	public SubjectInfoData() {
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
