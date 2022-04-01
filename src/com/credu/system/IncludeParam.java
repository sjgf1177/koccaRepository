//**********************************************************
//1. 제      목: 
//2. 프로그램명: IncludeParam.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-07-24
//7. 수      정: 
//                 
//********************************************************** 
 
package com.credu.system;

import java.util.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class IncludeParam {
	private int     selectiontype;
	private String  nameprefix;
	private String  onchange;

	private String grcode;
	private String gyear;
	private String grseq;  
	private String uclass;
	private String subjcourse;
	private String subjseq;
	private String comp;
	
	private String grcode_change;
	private String gyear_change;
	private String grseq_change;  
	private String uclass_change;
	private String subjcourse_change;
	private String subjseq_change;
	private String comp_change;
	
	
	public IncludeParam() {
		selectiontype = SelectionUtil.SUBJSEQ;
		nameprefix = "";
		onchange   = "";

		grcode     = "";
		gyear      = "";
		grseq      = "";  
		uclass     = "";
		subjcourse = "";
		subjseq    = "";
		comp       = "";
	
		grcode_change = "";
		gyear_change  = "" ;
		grseq_change  = "";   
		uclass_change = "";
		subjcourse_change = "";
		subjseq_change= "";
		comp_change   = "";
	}

	/**
	 * @return
	 */
	public String getGrcode() {
		return grcode;
	}

	/**
	 * @return
	 */
	public String getGrcode_change() {
		return grcode_change;
	}

	/**
	 * @return
	 */
	public String getGrseq() {
		return grseq;
	}

	/**
	 * @return
	 */
	public String getGrseq_change() {
		return grseq_change;
	}

	/**
	 * @return
	 */
	public String getGyear() {
		return gyear;
	}

	/**
	 * @return
	 */
	public String getGyear_change() {
		return gyear_change;
	}

	/**
	 * @return
	 */
	public String getNameprefix() {
		return nameprefix;
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
	public int getSelectiontype() {
		return selectiontype;
	}

	/**
	 * @return
	 */
	public String getSubjcourse() {
		return subjcourse;
	}

	/**
	 * @return
	 */
	public String getSubjcourse_change() {
		return subjcourse_change;
	}

	/**
	 * @return
	 */
	public String getSubjseq() {
		return subjseq;
	}

	/**
	 * @return
	 */
	public String getSubjseq_change() {
		return subjseq_change;
	}

	/**
	 * @return
	 */
	public String getUclass() {
		return uclass;
	}

	/**
	 * @return
	 */
	public String getUclass_change() {
		return uclass_change;
	}

	/**
	 * @param string
	 */
	public void setGrcode(String string) {
		grcode = string;
	}

	/**
	 * @param string
	 */
	public void setGrcode_change(String string) {
		grcode_change = string;
	}

	/**
	 * @param string
	 */
	public void setGrseq(String string) {
		grseq = string;
	}

	/**
	 * @param string
	 */
	public void setGrseq_change(String string) {
		grseq_change = string;
	}

	/**
	 * @param string
	 */
	public void setGyear(String string) {
		if (string.equals("")) {
			gyear = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
		} else {
			gyear = string;
		}
	}

	/**
	 * @param string
	 */
	public void setGyear_change(String string) {
		gyear_change = string;
	}

	/**
	 * @param string
	 */
	public void setNameprefix(String string) {
		nameprefix = string;
	}

	/**
	 * @param string
	 */
	public void setOnchange(String string) {
		onchange      = string;
		grcode_change = string;
		gyear_change  = string;
		grseq_change  = string;  
		uclass_change = string;
		subjcourse_change = string;
		subjseq_change = string;
		comp_change = string;
	}

	/**
	 * @param i
	 */
	public void setSelectiontype(int i) {
		selectiontype = i;
	}

	/**
	 * @param string
	 */
	public void setSubjcourse(String string) {
		subjcourse = string;
	}

	/**
	 * @param string
	 */
	public void setSubjcourse_change(String string) {
		subjcourse_change = string;
	}

	/**
	 * @param string
	 */
	public void setSubjseq(String string) {
		subjseq = string;
	}

	/**
	 * @param string
	 */
	public void setSubjseq_change(String string) {
		subjseq_change = string;
	}

	/**
	 * @param string
	 */
	public void setUclass(String string) {
		uclass = string;
	}

	/**
	 * @param string
	 */
	public void setUclass_change(String string) {
		uclass_change = string;
	}

	/**
	 * @return
	 */
	public String getCompany() {
		return comp;
	}

	/**
	 * @return
	 */
	public String getCompany_change() {
		return comp_change;
	}

	/**
	 * @param string
	 */
	public void setCompany(String string) {
		comp = string;
	}

	/**
	 * @param string
	 */
	public void setCompany_change(String string) {
		comp_change = string;
	}

}
