//**********************************************************
//1. 제      목: 
//2. 프로그램명: SulmunResult.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-19
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
public class SulmunQuestionExampleData {
	private SulmunQuestionData Question;
	private Hashtable ExampleDataList;
	
	public SulmunQuestionExampleData() {
		Question = new SulmunQuestionData();
		ExampleDataList = new Hashtable();		
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

	public String getSulreturn() {
		return Question.getSulreturn();
	}
	
	public String getSultypenm() {
		return Question.getSultypenm();
	}
	
	public String getDistcodenm() {
		return Question.getDistcodenm();
	}

	public int getSelmax() {
		return Question.getSelmax();
	}	

	public int getScalecode() {
		return Question.getScalecode();
	}	

	public Vector getSubjectAnswer() {
		return Question.getSubjectAnswer();
	}
	
	public Hashtable getNewSubjectAnswer() {
		return Question.getNewSubjectAnswer();
	}

	public Vector getComplexAnswer() {
		return Question.getComplexAnswer();
	}
	public Hashtable getNewComplexAnswer() {
		return Question.getNewComplexAnswer();
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

	public void setDistcode(String  distcode) {
		Question.setDistcode(distcode);
	}

	public void setSultype(String sultype) {
		Question.setSultype(sultype);
	}	 

	public void setSultext(String sultext) {
		Question.setSultext(sultext);		
	}

	public void setSulreturn(String sulreturn) {
		Question.setSulreturn(sulreturn);
	}
	
	public void setDistcodenm(String  distcodenm) {
		Question.setDistcodenm(distcodenm);
	}

	public void setSultypenm(String  sultypenm) {
		Question.setSultypenm(sultypenm);
	}

	public void setSelmax(int selmax) {
		Question.setSelmax(selmax);		
	}

	public void setScalecode(int scalecode) {
		Question.setScalecode(scalecode);		
	}

	public void setSubjectAnswer(Vector  answer) {
		Question.setSubjectAnswer(answer);
	}

	public void setComplexAnswer(Vector  answer) {
		Question.setComplexAnswer(answer);
	}
	
	public void setNewComplexAnswer(Hashtable  answer) {
		Question.setNewComplexAnswer(answer);
	}

	public SulmunExampleData get(int selnum) {
		return (SulmunExampleData)ExampleDataList.get(String.valueOf(selnum));
	}
	
	public void add(SulmunExampleData exampledata) {
		ExampleDataList.put(String.valueOf(exampledata.getSelnum()), exampledata);
	}

	public void remove(int selnum) {
		ExampleDataList.remove(String.valueOf(selnum));
	}
	
	public void clear() {
		ExampleDataList.clear();
	}
	
	public int size() {
		return ExampleDataList.size();
	}

	public int getSelnum(int selnum) {
		int i = 0;
		SulmunExampleData exampledata = get(selnum);
		if (exampledata != null) {
			i = exampledata.getSelnum();
		}
		return i;
	}

	public String getSeltext(int selnum) {
		String string = "";
		SulmunExampleData exampledata = get(selnum);
		if (exampledata != null) {
			string = exampledata.getSeltext();
		}
		return string;
	}

	public int getReplycnt(int selnum) {
		int i = 0;
		SulmunExampleData exampledata = get(selnum);
		if (exampledata != null) {
			i = exampledata.getReplycnt();
		}
		return i;
	}

	public double getReplyrate(int selnum) {
		double d = 0;
		SulmunExampleData exampledata = get(selnum);
		if (exampledata != null) {
			d = exampledata.getReplycnt();
		}
		return d;
	}

    public void setReplycnt(int selnum, int i) {
		SulmunExampleData exampledata = get(selnum);
		if (exampledata != null) {
			exampledata.setReplycnt(i);
		}
	}

	public void setReplyrate(int selnum, double d) {
		SulmunExampleData exampledata = get(selnum);
		if (exampledata != null) {
			exampledata.setReplyrate(d);
		}
	}
	

	public int getPointcnt(int selnum) {
		int i = 0;
		SulmunExampleData exampledata = get(selnum);
		if (exampledata != null) {
			i = exampledata.getPointcnt();
		}
		return i;
	}

	public double getPointrate(int selnum) {
		double d = 0;
		SulmunExampleData exampledata = get(selnum);
		if (exampledata != null) {
			d = exampledata.getPointcnt();
		}
		return d;
	}

	public void setPointcnt(int selnum, int i) {
		SulmunExampleData exampledata = get(selnum);
		if (exampledata != null) {
			exampledata.setPointcnt(i);
		}
	}

	public void setPointrate(int selnum, double d) {
		SulmunExampleData exampledata = get(selnum);
		if (exampledata != null) {
			exampledata.setPointrate(d);
		}
	}

	public void IncreasReplyCount(int answer) {
		SulmunExampleData exampledata = get(answer);
		if (exampledata != null) {
			exampledata.setReplycnt(exampledata.getReplycnt()+1);
		}
	}
	
	public void IncreasPointCount(int answer) {
		SulmunExampleData exampledata = get(answer);
		if (exampledata != null) {
			exampledata.setPointcnt(exampledata.getPointcnt()+1);
		}
	}

	public void ComputeRate() {
		SulmunExampleData data = null;
		Enumeration e1 = ExampleDataList.elements();
		int v_sum = 0;

		while(e1.hasMoreElements()) {
			data  = (SulmunExampleData)e1.nextElement();
			v_sum+= data.getReplycnt();
		}

		if (v_sum > 0) {
			Enumeration e2 = ExampleDataList.elements();
			while(e2.hasMoreElements()) {
				data = (SulmunExampleData) e2.nextElement();
				data.setReplyrate((double)Math.round((double)data.getReplycnt()/v_sum*100*100)/100);
			}
		}
	}

	public void ComputePoint() {
		SulmunExampleData data = null;
		Enumeration e1 = ExampleDataList.elements();
		int v_sum = 0;

		while(e1.hasMoreElements()) {
			data  = (SulmunExampleData)e1.nextElement();
			v_sum+= data.getPointcnt();
		}

		if (v_sum > 0) {
			Enumeration e2 = ExampleDataList.elements();
			while(e2.hasMoreElements()) {
				data = (SulmunExampleData) e2.nextElement();
				data.setPointrate((double)Math.round((double)data.getPointcnt()/v_sum));
			}
		}
	}
	
	public Enumeration elements() {
		return ExampleDataList.elements();
	}
}
