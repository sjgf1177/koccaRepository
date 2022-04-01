//**********************************************************
//1. 제      목: Question Data Bean
//2. 프로그램명: QuestionData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2004-11-08
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.research;

import java.util.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunQuestionData {
	private String subj;     
	private String grcode;
	private int	sulnum;    
	private String distcode;
	private String sultype;	
	private String sultext;
	private int selcount;
	private int selmax;
	private String sulreturn;
	private int scalecode;
	private String sultypenm;
	private String distcodenm;
	private Vector answer;
	private Vector c_answer;
	private Hashtable newAnswer;

	public SulmunQuestionData() {
		subj      = "";
		grcode = "";
		sulnum    = 0;    
		distcode  = "";
		sultype   = "";	
		sultext   = "";
		selcount = 0;
		selmax = 0;
		sulreturn = "";
		scalecode = 0;
		sultypenm = "";
		distcodenm = "";
		answer = new Vector();
		c_answer = new Vector();
		newAnswer = new Hashtable();
	};

	/**
	 * @param string
	 */
	public void setSubj(String string) {
		subj = string;
	}
	
	/**
	 * @return
	 */
	public String getSubj() {
		return subj;
	}

	/**
	 * @param string
	 */
	public void setGrcode(String string) {
		grcode = string;
	}
	
	/**
	 * @return
	 */
	public String getGrcode() {
		return grcode;
	}

	/**
	 * @param i
	 */
	public void setSulnum(int i) {
		sulnum = i;
	}
	
	/**
	 * @return
	 */
	public int getSulnum() {
		return sulnum;
	}

	/**
	 * @param i
	 */
	public void setDistcode(String string) {
		distcode = string;
	}
	
	/**
	 * @param i
	 */
	public void setSubjectAnswer(Vector v) {
		answer = v;
	}
	
	/**
	 * @param i
	 */
	public void setComplexAnswer(Vector v) {
		c_answer = v;
	}
	
	/**
	 * @param i
	 */
	public void setNewComplexAnswer(Hashtable h) {
		newAnswer = h;
	}
	
	/**
	 * @return
	 */
	public Vector getComplexAnswer() {
		return c_answer;
	}
	
	/**
	 * @return
	 */
	public Hashtable getNewComplexAnswer() {
		return newAnswer;
	}

	/**
	 * @return
	 */
	public Vector getSubjectAnswer() {
		return answer;
	}
	
	public Hashtable getNewSubjectAnswer() {
		return newAnswer;
	}

	/**
	 * @return
	 */
	public String getDistcode() {
		return distcode;
	}

	/**
	 * @param string
	 */
	public void setSultype(String string) {
		sultype = string;
	}
	
	/**
	 * @return
	 */
	public String getSultype() {
		return sultype;
	}

	/**
	 * @param string
	 */
	public void setSultext(String string) {
		sultext = string;
	}
	
	/**
	 * @return
	 */
	public String getSultext() {
		return sultext;
	}

	/**
	 * @param i
	 */
	public void setSelcount(int i) {
		selcount = i;
	}
	
	/**
	 * @return
	 */
	public int getSelcount() {
		return selcount;
	}

	/**
	 * @param i
	 */
	public void setSelmax(int i) {
		selmax = i;
	}
	
	/**
	 * @return
	 */
	public int  getSelmax() {
		return selmax;
	}

	/**
	 * @param string
	 */
	public void setSulreturn(String string) {
		sulreturn = string;
	}
	
	/**
	 * @return
	 */
	public String getSulreturn() {
		return sulreturn;
	}

	/**
	 * @param i
	 */
	public void setScalecode(int i) {
		scalecode = i;
	}
	
	/**
	 * @return
	 */
	public int getScalecode() {
		return scalecode;
	}

	/**
	 * @param string
	 */
	public void setSultypenm(String string) {
		sultypenm = string;
	}
	
	/**
	 * @return
	 */
	public String getSultypenm() {
		return sultypenm;
	}

	/**
	 * @param string
	 */
	public void setDistcodenm(String string) {
		distcodenm = string;
	}
	
	/**
	 * @return
	 */
	public String getDistcodenm() {
		return distcodenm;
	}

}
