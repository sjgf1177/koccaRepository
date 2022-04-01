//**********************************************************
//1. 제      목: 교육그룹대상회사 DATA
//2. 프로그램명: GrcompData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 07. 21
//7. 수      정: 
//                 
//**********************************************************
package com.credu.course;

public class GrcompData {
	private  String  grcode     ;             
	private  String  comp	   ;             
	private  String  indate     ;             
	private  String  luserid     ;             
	private  String  ldate      ;
	private  String  compnm;
	private  String  groupsnm;
	private  String  companynm;
	private  String  gpmnm;
	private  String  deptnm;
	private  String  partnm;
	private  String  groups;
	private  String  company;
	private  String  gpm;
	private  String  dept;
	private  String  part;
	private  int	  comptype;
	
	
	public GrcompData() {};
	
	/**
	 * @return
	 */
	public String getComp() {
		return comp;
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
	public String getIndate() {
		return indate;
	}

	/**
	 * @return
	 */
	public String getLdate() {
		return ldate;
	}

	/**
	 * @return
	 */
	public String getLuserid() {
		return luserid;
	}

	/**
	 * @param string
	 */
	public void setComp(String string) {
		comp = string;
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
	public void setIndate(String string) {
		indate = string;
	}

	/**
	 * @param string
	 */
	public void setLdate(String string) {
		ldate = string;
	}

	/**
	 * @param string
	 */
	public void setLuserid(String string) {
		luserid = string;
	}

	/**
	 * @return
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @return
	 */
	public String getCompanynm() {
		return companynm;
	}

	/**
	 * @return
	 */
	public String getCompnm() {
		return compnm;
	}

	/**
	 * @return
	 */
	public int getComptype() {
		return comptype;
	}

	/**
	 * @return
	 */
	public String getDept() {
		return dept;
	}

	/**
	 * @return
	 */
	public String getDeptnm() {
		return deptnm;
	}

	/**
	 * @return
	 */
	public String getGpm() {
		return gpm;
	}

	/**
	 * @return
	 */
	public String getGpmnm() {
		return gpmnm;
	}

	/**
	 * @return
	 */
	public String getGroups() {
		return groups;
	}

	/**
	 * @return
	 */
	public String getGroupsnm() {
		return groupsnm;
	}

	/**
	 * @return
	 */
	public String getPart() {
		return part;
	}

	/**
	 * @return
	 */
	public String getPartnm() {
		return partnm;
	}

	/**
	 * @param string
	 */
	public void setCompany(String string) {
		company = string;
	}

	/**
	 * @param string
	 */
	public void setCompanynm(String string) {
		companynm = string;
	}

	/**
	 * @param string
	 */
	public void setCompnm(String string) {
		compnm = string;
	}

	/**
	 * @param i
	 */
	public void setComptype(int i) {
		comptype = i;
	}

	/**
	 * @param string
	 */
	public void setDept(String string) {
		dept = string;
	}

	/**
	 * @param string
	 */
	public void setDeptnm(String string) {
		deptnm = string;
	}

	/**
	 * @param string
	 */
	public void setGpm(String string) {
		gpm = string;
	}

	/**
	 * @param string
	 */
	public void setGpmnm(String string) {
		gpmnm = string;
	}

	/**
	 * @param string
	 */
	public void setGroups(String string) {
		groups = string;
	}

	/**
	 * @param string
	 */
	public void setGroupsnm(String string) {
		groupsnm = string;
	}

	/**
	 * @param string
	 */
	public void setPart(String string) {
		part = string;
	}

	/**
	 * @param string
	 */
	public void setPartnm(String string) {
		partnm = string;
	}

}
