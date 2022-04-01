//**********************************************************
//1. ��      ��: 
//2. ���α׷���: SubjExamPaperData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-09-04
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
public class SubjExamPaperData {
	private String subj;
	private String year;
	private String subjseq;
	private String subjnm;
	
	private Hashtable    ExamPaperDataList;
	
	public SubjExamPaperData() {
		subj = "";
		year = "";
		subjseq = "";
		subjnm = "";
		ExamPaperDataList = new Hashtable();		
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
	 * @return
	 */
	public String getSubjseq() {
		return subjseq;
	}

	/**
	 * @return
	 */
	public String getYear() {
		return year;
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

	/**
	 * @param string
	 */
	public void setSubjseq(String string) {
		subjseq = string;
	}

	/**
	 * @param string
	 */
	public void setYear(String string) {
		year = string;
	}


	public ExamPaperData get(int index) {
		return (ExamPaperData)ExamPaperDataList.get(String.valueOf(index));
	}
	
	public void add(ExamPaperData exampaperdata) {
		ExamPaperDataList.put(String.valueOf(ExamPaperDataList.size()), exampaperdata);
	}

	public void remove(int index) {
		ExamPaperDataList.remove(String.valueOf(index));
	}
	
	public void clear() {
		ExamPaperDataList.clear();
	}
	
	public int size() {
		return ExamPaperDataList.size();
	}

	public Enumeration elements() {
		return ExamPaperDataList.elements();		
	}
}
