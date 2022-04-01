//**********************************************************
//1. 제      목: 
//2. 프로그램명: SubjExamMasterData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-09-03
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.exam;

import java.util.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SubjExamMasterData {
	private String subj;
	private String subjnm;
	private Hashtable    ExamMasterDataList;
	
	public SubjExamMasterData() {
		subj = "";
		subjnm = "";
		ExamMasterDataList = new Hashtable();		
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
	public String getSubjnm() {
		return subjnm;
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
	public void setSubjnm(String string) {
		subjnm = string;
	}
	
	public ExamMasterData get(int index) {
		return (ExamMasterData)ExamMasterDataList.get(String.valueOf(index));
	}
	
	public void add(ExamMasterData exammasterdata) {
		ExamMasterDataList.put(String.valueOf(ExamMasterDataList.size()), exammasterdata);
	}

	public void remove(int index) {
		ExamMasterDataList.remove(String.valueOf(index));
	}
	
	public void clear() {
		ExamMasterDataList.clear();
	}
	
	public int size() {
		return ExamMasterDataList.size();
	}

	public Enumeration elements() {
		return ExamMasterDataList.elements();		
	}
}
