//**********************************************************
//1. ��      ��: 
//2. ���α׷���: SubjExamMasterData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-09-03
//7. ��      ��: 
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
