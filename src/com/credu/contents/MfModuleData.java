//**********************************************************
//1. ��      ��: �������� DATA
//2. ���α׷���: MfModuleData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: LeeSuMin 2003. 08. 13
//7. ��      ��: 
//                 
//**********************************************************
package com.credu.contents;

import com.credu.library.*;

public class MfModuleData {
	
	private	String	subj;			//�����ڵ�
	private	String	module;			//����ڵ�
	private	String	sdesc;			//����
	private	String	types;			//���Ÿ��
	private	int	cnt_lesson=0;	//Lesson��

	private	String	luesrid;		//���������� ID
	private	String	ldate;			//���������Ͻ�
	
	
	
	public MfModuleData() {};
	


	/**
	 * @return
	 */
	public int getCnt_lesson() {
		return cnt_lesson;
	}

	/**
	 * @return
	 */
	public String getLdate() {
		return ldate;
	}

	/**
	 * @return
	 */
	public String getLuesrid() {
		return luesrid;
	}

	/**
	 * @return
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @return
	 */
	public String getSdesc() {
		return sdesc;
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
	public String getTypes() {
		return types;
	}

	/**
	 * @param i
	 */
	public void setCnt_lesson(int i) {
		cnt_lesson = i;
	}

	/**
	 * @param string
	 */
	public void setLdate(String string) {
		ldate = string;
	}

	/**
	 * @param string
	 */
	public void setLuesrid(String string) {
		luesrid = string;
	}

	/**
	 * @param string
	 */
	public void setModule(String string) {
		module = string;
	}

	/**
	 * @param string
	 */
	public void setSdesc(String string) {
		sdesc = string;
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
	public void setTypes(String string) {
		types = string;
	}

}
