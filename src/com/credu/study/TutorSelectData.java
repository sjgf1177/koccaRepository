//**********************************************************
//1. ��      ��: 
//2. ���α׷���: TutorSelectData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-07-30
//7. ��      ��: 
//                 
//********************************************************** 
 
package com.credu.study;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class TutorSelectData {
	String userid;
	String name;
	String ttype;
	boolean selected;
	
	public TutorSelectData() {
		userid = "";
		name   = "";
		selected = false;
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
	public boolean isSelected() {
		return selected;
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
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param b
	 */
	public void setSelected(boolean b) {
		selected = b;
	}

	/**
	 * @param string
	 */
	public void setUserid(String string) {
		userid = string;
	}
	/**
	 * @return
	 */
	public String getTtype() {
		return ttype;
	}

	/**
	 * @param string
	 */
	public void setTtype(String string) {
		ttype = string;
	}

}
