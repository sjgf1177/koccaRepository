//**********************************************************
//1. 제      목: 마스터폼 메뉴 마스터 DATA
//2. 프로그램명: mfMenuData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 08. 13
//7. 수      정: 
//                 
//**********************************************************
package com.credu.contents;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class OBCTreeData {

	private	String	ap;		//Applet Parameter  String
	private	String	ah;		//Hidden Form-Value String
	
	public OBCTreeData() {
	}

	/**
	 * @return
	 */
	public String getAh() {
		return ah;
	}

	/**
	 * @return
	 */
	public String getAp() {
		return ap;
	}

	/**
	 * @param string
	 */
	public void setAh(String string) {
		ah = string;
	}

	/**
	 * @param string
	 */
	public void setAp(String string) {
		ap = string;
	}

}
