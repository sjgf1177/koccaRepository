//**********************************************************
//1. 제      목: 
//2. 프로그램명: QuestionSubjectAnswerData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-28
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.research;

import java.util.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunQuestionSubjectAnswerData {
	private SulmunQuestionData Question;
	private Hashtable SubjectAnswerDataList;
	
	public SulmunQuestionSubjectAnswerData() {
		Question = new SulmunQuestionData();
		SubjectAnswerDataList = new Hashtable();		
	}
	
	public String getSubj() {
		return Question.getSubj();
	}

	public String getGrcode() {
		return Question.getGrcode();
	}

	public int getSulnum() {
		return Question.getSulnum();
	}

	public String getDistcode() {
		return Question.getDistcode();
	}	

	public String getSultype() {
		return Question.getSultype();
	}

	public String getSultext() {
		return Question.getSultext();
	}
	
    public String getSultypenm() {
		return Question.getSultypenm();
	}
	
	public String getDistcodenm() {
		return Question.getDistcodenm();
	}
	
	public void setSubj(String subj) {
		Question.setSubj(subj);		
	}

	public void setGrcode(String grcode) {
		Question.setGrcode(grcode);		
	}
	
	public void setSulnum(int sulnum) {
		Question.setSulnum(sulnum);		
	}

	public void setDistcode(String distcode) {
		Question.setDistcode(distcode);		
	}

	public void setSultype(String sultype) {
		Question.setSultype(sultype);
	}	 

	public void setSultext(String sultext) {
		Question.setSultext(sultext);		
	}

	public void setSultypenm(String sultype) {
		Question.setSultypenm(sultype);
	}
	
	public void setDistcodenm(String  distcode) {
		Question.setDistcodenm(distcode);
	}
///////////////////////////////////////////////////////////////////////////////
	public SulmunSubjectAnswerData get(int index) {
		return (SulmunSubjectAnswerData)SubjectAnswerDataList.get(String.valueOf(index));
	}
	
	public void add(SulmunSubjectAnswerData answerdata) {
		SubjectAnswerDataList.put(String.valueOf(SubjectAnswerDataList.size()), answerdata);
	}

	public void remove(int index) {
		SubjectAnswerDataList.remove(String.valueOf(index));
	}
	
	public void clear() {
		SubjectAnswerDataList.clear();
	}
	
	public int size() {
		return SubjectAnswerDataList.size();
	}
///////////////////////////////////////////////////////////////////////////////
	public String getUserid(int index) {
		String string = "";
		SulmunSubjectAnswerData answerdata = get(index);
		if (answerdata != null) {
			string = answerdata.getUserid();
		}
		return string;
	}

	public String getName(int index) {
		String string = "";
		SulmunSubjectAnswerData answerdata = get(index);
		if (answerdata != null) {
			string = answerdata.getName();
		}
		return string;
	}

	public String getAnstext(int index) {
		String string = "";
		SulmunSubjectAnswerData answerdata = get(index);
		if (answerdata != null) {
			string = answerdata.getAnstext();
		}
		return string;
	}
}
