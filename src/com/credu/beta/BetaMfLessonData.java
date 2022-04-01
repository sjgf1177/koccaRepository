//**********************************************************
//1. ��      ��: �������� DATA
//2. ���α׷���: MfLessonData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: LeeSuMin 2003. 08. 13
//7. ��      ��: 
//                 
//**********************************************************
package com.credu.beta;

import com.credu.library.*;
import java.util.*;

public class BetaMfLessonData {
	
	private 	String subj		;		//�����ڵ�
	private 	String module	;		//����ڵ�
	private 	String lesson	;		//����(����)�ڵ�
	private 	String sdesc	;		//������
	private 	String types	;		//����Ÿ��
	private 	String owner	;		//���������ڵ�
	private 	String starting	;		//����URL
	private 	String isbranch	;		//�б�Ŀ���
	private 	String luserid	;
	private 	String ldate	;
	
	/** Application Data **/
	private	String iseducated = "N";	//��������
	private	String isexam	  = "N";	//�������ʿ俩�� (�����ؾ��ϸ� Y)
	public		Hashtable objList = new Hashtable();	//OBC�� ��� Object List

	public BetaMfLessonData() {};
	
	
	public void makeSub(BetaMfLessonDataSub ds){
		int hsize = objList.size();
		objList.put(String.valueOf(hsize), ds);
	}
	
	public BetaMfLessonDataSub getSub(String oid){		//Object List�� Ư�� Object Return.
		return (BetaMfLessonDataSub)objList.get(oid);
	}



	/**
	 * @return
	 */
	public String getIsbranch() {
		return isbranch;
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
	public String getLesson() {
		return lesson;
	}

	/**
	 * @return
	 */
	public String getLuserid() {
		return luserid;
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
	public String getOwner() {
		return owner;
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
	public String getStarting() {
		return starting;
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
	 * @param string
	 */
	public void setIsbranch(String string) {
		isbranch = string;
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
	public void setLesson(String string) {
		lesson = string;
	}

	/**
	 * @param string
	 */
	public void setLuserid(String string) {
		luserid = string;
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
	public void setOwner(String string) {
		owner = string;
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
	public void setStarting(String string) {
		starting = string;
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

	/**
	 * @return
	 */
	public String getIseducated() {
		return iseducated;
	}

	/**
	 * @return
	 */
	public String getIsexam() {
		return isexam;
	}

	/**
	 * @param string
	 */
	public void setIseducated(String string) {
		iseducated = string;
	}

	/**
	 * @param string
	 */
	public void setIsexam(String string) {
		isexam = string;
	}
	/**
	 * @return
	 */
	public Hashtable getObjList() {
		return objList;
	}

	/**
	 * @param hashtable
	 */
	public void setObjList(Hashtable hashtable) {
		objList = hashtable;
	}
}
