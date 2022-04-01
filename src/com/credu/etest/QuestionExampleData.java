//**********************************************************
//1. 제      목: 
//2. 프로그램명: QuestionExampleData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.etest;

import java.util.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class QuestionExampleData {
	private QuestionData Question;
	private Hashtable    ExampleDataList;
	
	public QuestionExampleData() {
		Question = new QuestionData();
		ExampleDataList = new Hashtable();		
	}
	
	public int getAddseq() {
		return Question.getAddseq();
	}

	/**
	 * @return
	 */
	public int getBranch() {
		return Question.getBranch();
	}

	/**
	 * @return
	 */
	public int getETestnum() {
		return Question.getETestnum();
	}

	/**
	 * @return
	 */
	public String getETesttype() {
		return Question.getETesttype();
	}

	/**
	 * @return
	 */
	public String getExptext() {
		return Question.getExptext();
	}

	/**
	 * @return
	 */
	public String getImgurl() {
		return Question.getImgurl();
	}

	/**
	 * @return
	 */
	public String getLesson() {
		return Question.getLesson();
	}

	/**
	 * @return
	 */
	public String getLevels() {
		return Question.getLevels();
	}

	/**
	 * @return
	 */
	public String getMediaurl() {
		return Question.getMediaurl();
	}

	/**
	 * @return
	 */
	public String getOid() {
		return Question.getOid();
	}

	/**
	 * @return
	 */
	public String getRefurl1() {
		return Question.getRefurl1();
	}

	/**
	 * @return
	 */
	public String getRefurl2() {
		return Question.getRefurl2();
	}

	/**
	 * @return
	 */
	public String getSdesc() {
		return Question.getSdesc();
	}

	/**
	 * @return
	 */
	public String getSubj() {
		return Question.getSubj();
	}

	/**
	 * @return
	 */
	public String getSubj_sub() {
		return Question.getSubj_sub();
	}

	/**
	 * @return
	 */
	public String getText() {
		return Question.getText();
	}

	/**
	 * @return
	 */
	public String getETesttypenm() {
		return Question.getETesttypenm();
	}

	/**
	 * @return
	 */
	public String getLevelsnm() {
		return Question.getLevelsnm();
	}
	
	/**
	 * @return
	 */
	public double getMarks() {
		return Question.getMarks();
	}
	
	/**
	 * @return
	 */
	public double getGetmarks() {
		return Question.getGetmarks();
	}

	/**
	 * @return
	 */
	public String getRealimgurl() {
		return Question.getRealimgurl();
	}

	/**
	 * @return
	 */
	public String getRealmediaurl() {
		return Question.getRealmediaurl();
	}

	/**
	 * @return
	 */
	public String getRealrefurl1() {
		return Question.getRealrefurl1();
	}

	/**
	 * @return
	 */
	public String getRealrefurl2() {
		return Question.getRealrefurl2();
	}

	/**
	 * @param i
	 */
	public void setAddseq(int i) {
		Question.setAddseq(i);
	}

	/**
	 * @param i
	 */
	public void setBranch(int i) {
		Question.setBranch(i);
	}

	/**
	 * @param i
	 */
	public void setETestnum(int i) {
		Question.setETestnum(i);
	}

	/**
	 * @param string
	 */
	public void setETesttype(String string) {
		Question.setETesttype(string);
	}

	/**
	 * @param string
	 */
	public void setExptext(String string) {
		Question.setExptext(string);
	}

	/**
	 * @param string
	 */
	public void setImgurl(String string) {
		Question.setImgurl(string);
	}

	/**
	 * @param string
	 */
	public void setLesson(String string) {
		Question.setLesson(string);
	}

	/**
	 * @param i
	 */
	public void setLevels(String string) {
		Question.setLevels(string);
	}

	/**
	 * @param string
	 */
	public void setMediaurl(String string) {
		Question.setMediaurl(string);
	}

	/**
	 * @param string
	 */
	public void setOid(String string) {
		Question.setOid(string);
	}

	/**
	 * @param string
	 */
	public void setRefurl1(String string) {
		Question.setRefurl1(string);
	}

	/**
	 * @param string
	 */
	public void setRefurl2(String string) {
		Question.setRefurl2(string);
	}

	/**
	 * @param string
	 */
	public void setSdesc(String string) {
		Question.setSdesc(string);
	}

	/**
	 * @param string
	 */
	public void setSubj(String string) {
		Question.setSdesc(string);
	}

	/**
	 * @param string
	 */
	public void setSubj_sub(String string) {
		Question.setSdesc(string);
	}

	/**
	 * @param string
	 */
	public void setText(String string) {
		Question.setText(string);
	}

	/**
	 * @param string
	 */
	public void setETesttypenm(String string) {
		Question.setETesttypenm(string);
	}

	/**
	 * @param string
	 */
	public void setLevelsnm(String string) {
		Question.setLevelsnm(string);
	}

	/**
	 * @return
	 */
	public void setMarks(double d) {
		Question.setMarks(d);
	}
	
	/**
	 * @return
	 */
	public void setGetmarks(double d) {
		Question.setGetmarks(d);
	}

	/**
	 * @param string
	 */
	public void setRealimgurl(String string) {
		Question.setRealimgurl(string);
	}

	/**
	 * @param string
	 */
	public void setRealmediaurl(String string) {
		Question.setRealmediaurl(string);
	}

	/**
	 * @param string
	 */
	public void setRealrefurl1(String string) {
		Question.setRealrefurl1(string);
	}

	/**
	 * @param string
	 */
	public void setRealrefurl2(String string) {
		Question.setRealrefurl2(string);
	}

	public ExampleData get(int selnum) {
		return (ExampleData)ExampleDataList.get(String.valueOf(selnum));
	}
	
	public void add(ExampleData etestpledata) {
		ExampleDataList.put(String.valueOf(etestpledata.getSelnum()), etestpledata);
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
		ExampleData etestpledata = get(selnum);
		if (etestpledata != null) {
			i = etestpledata.getSelnum();
		}
		return i;
	}

	public String getSeltext(int selnum) {
		String string = "";
		ExampleData etestpledata = get(selnum);
		if (etestpledata != null) {
			string = etestpledata.getSeltext();
		}
		return string;
	}

	public String getIsanswer(int selnum) {
		String string = "";
		ExampleData etestpledata = get(selnum);
		if (etestpledata != null) {
			string  = etestpledata.getIsanswer();
		}
		return string;
	}
	
	public String getIsreply(int selnum) {
		String string = "";
		ExampleData etestpledata = get(selnum);
		if (etestpledata != null) {
			string  = etestpledata.getIsreply();
		}
		return string;
	}
	
	public void setIsreply(int selnum, String string) {
		ExampleData etestpledata = get(selnum);
		if (etestpledata != null) {
			etestpledata.setIsreply(string);
		}
	}
	
	public Enumeration elements() {
		return ExampleDataList.elements();		
	}
}
