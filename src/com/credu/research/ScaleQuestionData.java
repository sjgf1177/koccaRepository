//**********************************************************
//1. ��      ��: Scale Question Data Bean
//2. ���α׷���: ScaleQuestionData.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2004-11-08
//7. ��      ��: 
//                 
//********************************************************** 
 
package com.credu.research;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ScaleQuestionData {
	private int scalecode;     
	private String grcode;
	private String s_gubun;
	private String scaletype;	
	private String scalename;

	public ScaleQuestionData() {
		scalecode      = 0;
		grcode = "";
		s_gubun  = "";
		scaletype   = "";	
		scalename   = "";
	};

	/**
	 * @param i
	 */
	public void setScalecode(int i) {
		scalecode = i;
	}
	
	/**
	 * @return
	 */
	public int getScalecode() {
		return scalecode;
	}

	/**
	 * @param string
	 */
	public void setGrcode(String string) {
		grcode = string;
	}
	
	/**
	 * @return
	 */
	public String getGrcode() {
		return grcode;
	}

	/**
	 * @param string
	 */
	public void setS_gubun(String string) {
		s_gubun = string;
	}
	
	/**
	 * @return
	 */
	public String getS_gubun() {
		return s_gubun;
	}

	/**
	 * @param string
	 */
	public void setScaletype(String string) {
		scaletype = string;
	}
	
	/**
	 * @return
	 */
	public String getScaletype() {
		return scaletype;
	}

	/**
	 * @param string
	 */
	public void setScalename(String string) {
		scalename = string;
	}
	
	/**
	 * @return
	 */
	public String getScalename() {
		return scalename;
	}

}
