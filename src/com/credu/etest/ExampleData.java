//**********************************************************
//1. ��      ��: 
//2. ���α׷���: ExampleData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��: 
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
	private String  subj; 			// �����ڵ�		
	private int     etestnum;		// ������ȣ 	
	private int     selnum;			// sel number 	
	private String  seltext; 		// �����׸� �ؽ�Ʈ , �ܴ���� ����
	private String  isanswer; 		// ���俩��  Y,N
	private String  isreply;        // �н��� ���� ���� Y,N  

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
