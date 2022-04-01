//**********************************************************
//1. 제      목: 
//2. 프로그램명: SelectParam.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-07-23
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.system;

import javax.servlet.http.HttpSession;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SelectParam {
/*
	<select name="selectname" onChange="onchange">
  		<option value="ALL">ALL</option>  --> if (all == true) option중 ALL 추가  
	</select>
	nterm : 공백자리수 
*/					
	private String selectname;    
	private String onchange;
	private boolean all;
	private int nterm;
	private HttpSession session ;

	public SelectParam(String pselectname) {
		selectname = pselectname;    
		onchange ="";
		all = true;
		nterm = 0;
		session = null;
	}
	/*
	public SelectParam(String pselectname, String ponchange, boolean pall, int pnterm) {
		selectname = pselectname;    
		onchange = ponchange;
		all = pall;
		nterm = pnterm;
		session = null;
	}
	*/
	public SelectParam(String pselectname, String ponchange, boolean pall, int pnterm, HttpSession psession) {
		selectname = pselectname;    
		onchange = ponchange;
		all = pall;
		nterm = pnterm;
		session = psession;
	}

	/**
	 * @return
	 */
	public boolean isAll() {
		return all;
	}

	/**
	 * @return
	 */
	public int getNterm() {
		return nterm;
	}

	/**
	 * @return
	 */
	public String getOnchange() {
		return onchange;
	}

	/**
	 * @return
	 */
	public String getSelectname() {
		return selectname;
	}
	
	/**
	 * @param b
	 */
	public void setAll(boolean b) {
		all = b;
	}

	/**
	 * @param i
	 */
	public void setNterm(int i) {
		nterm = i;
	}

	/**
	 * @param string
	 */
	public void setOnchange(String string) {
		onchange = string;
	}

	/**
	 * @param string
	 */
	public void setSelectname(String string) {
		selectname = string;
	}
	
	/**
	 * @return
	 */
	public HttpSession getSession() {
		return session;
	}

	/**
	 * @param session
	 */
	public void setSession(HttpSession session) {
		this.session = session;
	}

}
