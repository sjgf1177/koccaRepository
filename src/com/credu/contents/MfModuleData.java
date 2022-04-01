//**********************************************************
//1. 제      목: 마스터폼 DATA
//2. 프로그램명: MfModuleData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 08. 13
//7. 수      정: 
//                 
//**********************************************************
package com.credu.contents;

import com.credu.library.*;

public class MfModuleData {
	
	private	String	subj;			//과정코드
	private	String	module;			//모듈코드
	private	String	sdesc;			//모듈명
	private	String	types;			//모듈타입
	private	int	cnt_lesson=0;	//Lesson수

	private	String	luesrid;		//최종변경자 ID
	private	String	ldate;			//최종변경일시
	
	
	
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
