//**********************************************************
//1. 제      목: 교육그룹코드 DATA
//2. 프로그램명: EduGroupData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 07. 16
//7. 수      정: 
//                 
//**********************************************************
package com.credu.course;

import java.util.*;
import com.credu.system.*;

public class EduGroupData {
	private  String  grcode     ;             
	private  String  grcodenm   ;             
	private  String  idtype     ;             
	private  String  manager    ;             
	private  String  master    ;	
	private  String  repdate    ;             
	private  String  domain     ;             
	private  String  chkFirst  ;
	private  String  chkFinal  ;             
	private  String  islogin    ;             
	private  String  isjik      ;             
	private  String  isonlygate ;             
	private  String  isusebill  ;             
	private  String  indate     ;             
	private  String  luserid     ;             
	private  String  ldate      ;
	private  String  comp      ;
	private  int	  propcnt    ;
	private  ArrayList	grcomps	 ;
	private  String	compTxt  ;
	private  String	managerName;
	private  String	masterName;
	private  String	masterEmail;
	private  String	masterComptel;
	private  String	etcdata;
	private  String	grtype;	
	
	public		Hashtable compList = new Hashtable();
	
	public EduGroupData() {};
	
	public void makeSub(String comp,String compnm){
		int i=compList.size();
		CompData ds = new CompData();
		ds.setComp(comp);
		ds.setCompnm(compnm);
		compList.put(String.valueOf(i), ds);
	}


	/**
	 * @return
	 */
	public String getDomain() {
		return domain;
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
	public String getGrcodenm() {
		return grcodenm;
	}
	
	/**
	 * @return
	 */
	public String getComp() {
		return comp;
	}

	/**
	 * @return
	 */
	public String getIdtype() {
		return idtype;
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
	public String getchkFirst() {
		return chkFirst;
	}

	/**
	 * @return
	 */
	public String getIsjik() {
		return isjik;
	}

	/**
	 * @return
	 */
	public String getIslogin() {
		return islogin;
	}

	/**
	 * @return
	 */
	public String getIsonlygate() {
		return isonlygate;
	}

	/**
	 * @return
	 */
	public String getIsusebill() {
		return isusebill;
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
	 * @return
	 */
	public String getManager() {
		return manager;
	}

	/**
	 * @return
	 */
	public int getPropcnt() {
		return propcnt;
	}

	/**
	 * @return
	 */
	public String getRepdate() {
		return repdate;
	}

	/**
	 * @param string
	 */
	public void setDomain(String string) {
		domain = string;
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
	public void setGrcodenm(String string) {
		grcodenm = string;
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
	public void setIdtype(String string) {
		idtype = string;
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
	public void setchkFirst(String string) {
		chkFirst = string;
	}

	/**
	 * @param string
	 */
	public void setIsjik(String string) {
		isjik = string;
	}

	/**
	 * @param string
	 */
	public void setIslogin(String string) {
		islogin = string;
	}

	/**
	 * @param string
	 */
	public void setIsonlygate(String string) {
		isonlygate = string;
	}

	/**
	 * @param string
	 */
	public void setIsusebill(String string) {
		isusebill = string;
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
	 * @param string
	 */
	public void setManager(String string) {
		manager = string;
	}

	/**
	 * @param i
	 */
	public void setPropcnt(int i) {
		propcnt = i;
	}

	/**
	 * @param string
	 */
	public void setRepdate(String string) {
		repdate = string;
	}

	/**
	 * @return
	 */
	public String getCompTxt() {
		return compTxt;
	}


	/**
	 * @param string
	 */
	public void setCompTxt(String string) {
		compTxt = string;
	}



	/**
	 * @return
	 */
	public ArrayList getGrcomps() {
		return grcomps;
	}

	/**
	 * @param list
	 */
	public void setGrcomps(ArrayList list) {
		grcomps = list;
	}

	/**
	 * @return
	 */
	public String getMasterComptel() {
		return masterComptel;
	}

	/**
	 * @return
	 */
	public String getMasterEmail() {
		return masterEmail;
	}

	/**
	 * @return
	 */
	public String getManagerName() {
		return managerName;
	}

	/**
	 * @param string
	 */
	public void setMasterComptel(String string) {
		masterComptel = string;
	}

	/**
	 * @param string
	 */
	public void setMasterEmail(String string) {
		masterEmail = string;
	}

	/**
	 * @param string
	 */
	public void setManagerName(String string) {
		managerName = string;
	}

	/**
	 * @return
	 */
	public String getMasterName() {
		return masterName;
	}

	/**
	 * @param string
	 */
	public void setMasterName(String string) {
		masterName = string;
	}

	/**
	 * @return
	 */
	public String getMaster() {
		return master;
	}

	/**
	 * @param string
	 */
	public void setMaster(String string) {
		master = string;
	}

	/**
	 * @return
	 */
	public String getChkFinal() {
		return chkFinal;
	}

	/**
	 * @return
	 */
	public String getChkFirst() {
		return chkFirst;
	}

	/**
	 * @param string
	 */
	public void setChkFinal(String string) {
		chkFinal = string;
	}

	/**
	 * @param string
	 */
	public void setChkFirst(String string) {
		chkFirst = string;
	}

	/**
	 * @return
	 */
	public String getEtcdata() {
		return etcdata;
	}

	/**
	 * @param string
	 */
	public void setEtcdata(String string) {
		etcdata = string;
	}

	/**
	 * @return
	 */
	public Hashtable getCompList() {
		return compList;
	}

	/**
	 * @param hashtable
	 */
	public void setCompList(Hashtable hashtable) {
		compList = hashtable;
	}

	public String getGrtype() {
		return grtype;
	}

	public void setGrtype(String grtype) {
		this.grtype = grtype;
	}

}
