//**********************************************************
//1. ��      ��: 
//2. ���α׷���: ClassInsertData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-06
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
public class ClassInsertData {
	String code;
	String codenm;
	int    studentcnt;
	String class1;
	String classnm;
	
	public ClassInsertData() { }

	/**
	 * @return
	 */
	public String getClassnm() {
		return classnm;
	}

	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return
	 */
	public String getCodenm() {
		return codenm;
	}

	/**
	 * @return
	 */
	public int getStudentcnt() {
		return studentcnt;
	}

	/**
	 * @param string
	 */
	public void setClassnm(String string) {
		classnm = string;
	}

	/**
	 * @param string
	 */
	public void setCode(String string) {
		code = string;
	}

	/**
	 * @param string
	 */
	public void setCodenm(String string) {
		codenm = string;
	}

	/**
	 * @param string
	 */
	public void setStudentcnt(int i) {
		studentcnt = i;
	}
	/**
	 * @return
	 */
	public String getClass1() {
		return class1;
	}

	/**
	 * @param string
	 */
	public void setClass1(String string) {
		class1 = string;
	}

}
