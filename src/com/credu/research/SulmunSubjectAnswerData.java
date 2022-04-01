//**********************************************************
//1. 제      목: 
//2. 프로그램명: SubjectAnswerData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-28
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
public class SulmunSubjectAnswerData {
	private String 	subj;     
	private int	sulnum;    
	private String  userid;
	private String  name;
	private String  anstext;


	/**
	 * @return
	 */
	public String getAnstext() {
		return anstext;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
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
	public int getSulnum() {
		return sulnum;
	}

	/**
	 * @return
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param string
	 */
	public void setAnstext(String string) {
		anstext = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setSubj(String string) {
		subj = string;
	}

	/**
	 * @param i
	 */
	public void setSulnum(int i) {
		sulnum = i;
	}

	/**
	 * @param string
	 */
	public void setUserid(String string) {
		userid = string;
	}

}
