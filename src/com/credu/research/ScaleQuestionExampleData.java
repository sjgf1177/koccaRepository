//**********************************************************
//1. 제      목: Scale Question Example Data
//2. 프로그램명: ScaleQuestionExampleData.java
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
public class ScaleQuestionExampleData {
	private ScaleQuestionData Question;
	private Hashtable ExampleDataList;
	
	public ScaleQuestionExampleData() {
		Question = new ScaleQuestionData();
		ExampleDataList = new Hashtable();		
	}
	
	public int getScalecode() {
		return Question.getScalecode();
	}

	public String getGrcode() {
		return Question.getGrcode();
	}

	public String getS_gubun() {
		return Question.getS_gubun();
	}

	public String getScaletype() {
		return Question.getScaletype();
	}
	
	public String getScalename() {
		return Question.getScalename();
	}

	public void setScalecode(int scalecode) {
		Question.setScalecode(scalecode);		
	}

	public void setGrcode(String grcode) {
		Question.setGrcode(grcode);		
	}
	
	public void setS_gubun(String  s_gubun) {
		Question.setS_gubun(s_gubun);
	}

	public void setScaletype(String scaletype) {
		Question.setScaletype(scaletype);
	}	 

	public void setScalename(String scalename) {
		Question.setScalename(scalename);		
	}
	
	public ScaleExampleData get(int selnum) {
		return (ScaleExampleData)ExampleDataList.get(String.valueOf(selnum));
	}
	
	public void add(ScaleExampleData exampledata) {
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
		ScaleExampleData exampledata = get(selnum);
		if (exampledata != null) {
			i = exampledata.getSelnum();
		}
		return i;
	}

	public int getSelpoint(int selnum) {
		int i = 0;
		ScaleExampleData exampledata = get(selnum);
		if (exampledata != null) {
			i = exampledata.getSelpoint();
		}
		return i;
	}

	public String getSeltext(int selnum) {
		String string = "";
		ScaleExampleData exampledata = get(selnum);
		if (exampledata != null) {
			string = exampledata.getSeltext();
		}
		return string;
	}

	public int getReplycnt(int selnum) {
		int i = 0;
		ScaleExampleData exampledata = get(selnum);
		if (exampledata != null) {
			i = exampledata.getReplycnt();
		}
		return i;
	}

	public double getReplyrate(int selnum) {
		double d = 0;
		ScaleExampleData exampledata = get(selnum);
		if (exampledata != null) {
			d = exampledata.getReplycnt();
		}
		return d;
	}

	public void setReplycnt(int selnum, int i) {
		ScaleExampleData exampledata = get(selnum);
		if (exampledata != null) {
			exampledata.setReplycnt(i);
		}
	}

	public void getReplyrate(int selnum, double d) {
		ScaleExampleData exampledata = get(selnum);
		if (exampledata != null) {
			exampledata.setReplyrate(d);
		}
	}
	
	public void IncreasReplyCount(int answer) {
		ScaleExampleData exampledata = get(answer);
		if (exampledata != null) {
			exampledata.setReplycnt(exampledata.getReplycnt()+1);
		}
	}
	
	public void ComputeRate() {
		ScaleExampleData data = null;
		Enumeration e1 = ExampleDataList.elements();
		int v_sum = 0;

		while(e1.hasMoreElements()) {
			data  = (ScaleExampleData)e1.nextElement();
			v_sum+= data.getReplycnt();
		}

		if (v_sum > 0) {
			Enumeration e2 = ExampleDataList.elements();
			while(e2.hasMoreElements()) {
				data = (ScaleExampleData) e2.nextElement();
				data.setReplyrate((double)Math.round((double)data.getReplycnt()/v_sum*100*100)/100);
			}
		}
	}
	
	public Enumeration elements() {
		return ExampleDataList.elements();
	}
}
