//**********************************************************
//1. ��      ��: Scale Example Data
//2. ���α׷���: ScaleExampleData.java
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
public class ScaleExampleData {
	private int 	scalecode;     

	private int	    selnum;
	private int	    selpoint;
	private String  seltext;

	private int     replycnt;
	private double  replyrate;

	public ScaleExampleData() {
		scalecode     = 0;     
		selnum   = 0;
		selpoint   = 0;
		seltext  = "";
		replycnt = 0;
		replyrate= 0;
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
	public int getScalecode() {
		return scalecode;
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
	 * @param i
	 */
	public void setScalecode(int i) {
		scalecode = i;
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

}
