//**********************************************************
//1. ��      ��: 
//2. ���α׷���: PtypeData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-09-18
//7. ��      ��: 
//                 
//********************************************************** 
 
package com.credu.exam;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class PtypeData {
	String ptype; 
	int    papernum; 
	String userid;
	
	public PtypeData() {
		ptype   = ""; 
		papernum= 0; 
		userid  = "";
	}

	/**
	 * @return
	 */
	public int getPapernum() {
		return papernum;
	}

	/**
	 * @return
	 */
	public String getPtype() {
		return ptype;
	}

	/**
	 * @return
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param i
	 */
	public void setPapernum(int i) {
		papernum = i;
	}

	/**
	 * @param string
	 */
	public void setPtype(String string) {
		ptype = string;
	}

	/**
	 * @param string
	 */
	public void setUserid(String string) {
		userid = string;
	}

}
