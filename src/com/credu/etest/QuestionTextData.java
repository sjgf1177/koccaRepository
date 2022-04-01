//**********************************************************
//1. 제      목: 
//2. 프로그램명: ETestTextData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-09-02
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
public class QuestionTextData {
	private String subj; 			
	private int    addseq;			
	private String addtext; 		
	private String isset;		
	private String imgurl;			                                                     
	private String mediaurl;		                             
	private String realimgurl;			                                                     
	private String realmediaurl;		                             
	
	public QuestionTextData() {}
	
	/**
	 * @return
	 */
	public int getAddseq() {
		return addseq;
	}

	/**
	 * @return
	 */
	public String getAddtext() {
		return addtext;
	}

	/**
	 * @return
	 */
	public String getImgurl() {
		return imgurl;
	}

	/**
	 * @return
	 */
	public String getIsset() {
		return isset;
	}

	/**
	 * @return
	 */
	public String getMediaurl() {
		return mediaurl;
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
	public void setAddseq(int i) {
		addseq = i;
	}

	/**
	 * @param string
	 */
	public void setAddtext(String string) {
		addtext = string;
	}

	/**
	 * @param string
	 */
	public void setImgurl(String string) {
		imgurl = string;
	}

	/**
	 * @param string
	 */
	public void setIsset(String string) {
		isset = string;
	}

	/**
	 * @param string
	 */
	public void setMediaurl(String string) {
		mediaurl = string;
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
	public String getRealimgurl() {
		return realimgurl;
	}

	/**
	 * @return
	 */
	public String getRealmediaurl() {
		return realmediaurl;
	}

	/**
	 * @param string
	 */
	public void setRealimgurl(String string) {
		realimgurl = string;
	}

	/**
	 * @param string
	 */
	public void setRealmediaurl(String string) {
		realmediaurl = string;
	}

}
