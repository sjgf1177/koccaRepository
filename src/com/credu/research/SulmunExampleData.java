//**********************************************************
//1. ��      ��: 
//2. ���α׷���: QuestionExampleData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-19
//7. ��      ��: 
//                 
//********************************************************** 
 
package com.credu.research;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunExampleData {
	private String 	subj;     
	private int	sulnum;    

	private int	    selnum;
	private int	    selpoint;
	private String  seltext;

	private int     replycnt;
	private double  replyrate;

	private int     pointcnt;
	private double  pointrate;

	public SulmunExampleData() {
		subj     = "";     
		sulnum   = 0;    
		selnum   = 0;
		selpoint   = 0;
		seltext  = "";
		replycnt = 0;
		replyrate= 0;
		pointcnt = 0;
		pointrate= 0;
	}

	/**
	 * @return
	 */
	public int getSelnum() {
		return selnum;
	}

	/**
	 * @return
	 */
	public int getSelpoint() {
		return selpoint;
	}

	/**
	 * @return
	 */
	public String getSeltext() {
		return seltext;
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
	 * @param i
	 */
	public void setSelnum(int i) {
		selnum = i;
	}

	/**
	 * @param i
	 */
	public void setSelpoint(int i) {
		selpoint = i;
	}


	/**
	 * @param string
	 */
	public void setSeltext(String string) {
		seltext = string;
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
	 * @return
	 */
	public int getReplycnt() {
		return replycnt;
	}

	/**
	 * @return
	 */
	public double getReplyrate() {
		return replyrate;
	}

	/**
	 * @param i
	 */
	public void setReplycnt(int i) {
		replycnt = i;
	}

	/**
	 * @param d
	 */
	public void setReplyrate(double d) {
		replyrate = d;
	}

	/**
	 * @return
	 */
	public int getPointcnt() {
		return pointcnt;
	}

	/**
	 * @return
	 */
	public double getPointrate() {
		return pointrate;
	}

	/**
	 * @param i
	 */
	public void setPointcnt(int i) {
		pointcnt = i;
	}

	/**
	 * @param d
	 */
	public void setPointrate(double d) {
		pointrate = d;
	}

}
