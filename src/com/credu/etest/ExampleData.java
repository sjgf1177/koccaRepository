//**********************************************************
//1. 제      목: 
//2. 프로그램명: ExampleData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.etest;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ExampleData {
	private String  subj; 			// 과정코드		
	private int     etestnum;		// 문제번호 	
	private int     selnum;			// sel number 	
	private String  seltext; 		// 선택항목 텍스트 , 단답식은 정답
	private String  isanswer; 		// 정답여부  Y,N
	private String  isreply;        // 학습자 선택 여부 Y,N  

	public ExampleData() {
		subj    = "";		
		etestnum = 0; 	
		selnum  = 0; 	
		seltext = "";
		isanswer= "N";
		isreply = "N";
	}

	/**
	 * @return
	 */
	public int getExamnum() {
		return etestnum;
	}

	/**
	 * @return
	 */
	public String getIsanswer() {
		return isanswer;
	}

	/**
	 * @return
	 */
	public int getSelnum() {
		return selnum;
	}

	/**
	 * @return
	 */
	public String getSeltext() {
		return seltext;
	}

	/**
	 * @return
	 */
	public String getSubj() {
		return subj;
	}

	/**
	 * @param i
	 */
	public void setExamnum(int i) {
		etestnum = i;
	}

	/**
	 * @param b
	 */
	public void setIsanswer(String string) {
		isanswer = string;
	}

	/**
	 * @param i
	 */
	public void setSelnum(int i) {
		selnum = i;
	}

	/**
	 * @param string
	 */
	public void setSeltext(String string) {
		seltext = string;
	}

	/**
	 * @param string
	 */
	public void setSubj(String string) {
		subj = string;
	}

	/**
	 * @return
	 */
	public String getIsreply() {
		return isreply;
	}

	/**
	 * @param string
	 */
	public void setIsreply(String string) {
		isreply = string;
	}

}
