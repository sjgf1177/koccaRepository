//**********************************************************
//1. 제      목: SCO-Object DATA
//2. 프로그램명: SCObjectData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 10. 08
//7. 수      정: 
//                 
//**********************************************************
package com.credu.beta;

import java.util.*;
import com.credu.library.*;

public class BetaSCObjectData {
	/* tz_object 
	[OID] [nvarchar] (10) COLLATE Korean_Wansung_CI_AS NOT NULL ,
	[OTYPE] [nvarchar] (4) COLLATE Korean_Wansung_CI_AS NOT NULL ,
	[FILETYPE] [nvarchar] (4) COLLATE Korean_Wansung_CI_AS NOT NULL ,
	[NPAGE] [decimal](3, 0) NOT NULL ,
	[SDESC] [nvarchar] (200) COLLATE Korean_Wansung_CI_AS NOT NULL ,
	[MASTER] [nvarchar] (13) COLLATE Korean_Wansung_CI_AS NULL ,
	[STARTING] [nvarchar] (200) COLLATE Korean_Wansung_CI_AS NOT NULL ,
	[SERVER] [nvarchar] (10) COLLATE Korean_Wansung_CI_AS NULL ,
	[SUBJ] [nvarchar] (10) COLLATE Korean_Wansung_CI_AS NULL ,
	[PARAMETERSTRING] [nvarchar] (255) COLLATE Korean_Wansung_CI_AS NULL ,
	[DATAFROMLMS] [nvarchar] (255) COLLATE Korean_Wansung_CI_AS NULL ,
	[IDENTIFIER] [nvarchar] (255) COLLATE Korean_Wansung_CI_AS NULL ,
	[PREREQUISITES] [nvarchar] (255) COLLATE Korean_Wansung_CI_AS NULL ,
	[MASTERYSCORE] [decimal](5, 2) NULL ,
	[MAXTIMEALLOWED] [nvarchar] (20) COLLATE Korean_Wansung_CI_AS NULL ,
	[TIMELIMITACTION] [nvarchar] (255) COLLATE Korean_Wansung_CI_AS NULL ,
	[SEQUENCE] [decimal](3, 0) NULL ,
	[THELEVEL] [decimal](3, 0) NULL ,
	[LUSERID] [nvarchar] (20) COLLATE Korean_Wansung_CI_AS NULL ,
	[LDATE] [nvarchar] (14) COLLATE Korean_Wansung_CI_AS NULL 
	 * 	 * 
	 */
	
	private	String 	oid				="";
	private	String	otype           ="";
	private	String 	filetype        ="";
	private	int	npage           =0;
	private	String 	sdesc           ="";
	private	String 	master          ="";
	private	String 	starting          ="";
	private	String	server          ="";
	private	String	subj            ="";
	private	String 	parameterstring ="";
	private	String	datafromlms     ="";
	private	String	identifier      ="";
	private	String 	prerequisites   ="";
	private	int	masteryscore    =0;
	private	String	maxtimeallowed  ="";
	private	String 	timelimitaction ="";
	private	int 	sequence        =0;
	private	int	thelevel        =0;
	private	String	luserid         ="";
	private	String	ldate           ="";
	
	/*addon */
	private	String	mastername		="";	//master 이름
	private	int	cntUsed			=0;		//사용된 과정수
	public		Hashtable subjList = new Hashtable();
	
	public BetaSCObjectData() {};
	
	public void makeSub(String subj,String subjnm){
		cntUsed = subjList.size();
		subjList.put(String.valueOf(cntUsed), subjnm);
		//subjList.put(subj, subjnm);
	}

	/**
	 * @return
	 */
	public String getDatafromlms() {
		return datafromlms;
	}

	/**
	 * @return
	 */
	public String getFiletype() {
		return filetype;
	}

	/**
	 * @return
	 */
	public String getIdentifier() {
		return identifier;
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
	public String getMaster() {
		return master;
	}

	/**
	 * @return
	 */
	public int getMasteryscore() {
		return masteryscore;
	}

	/**
	 * @return
	 */
	public String getMaxtimeallowed() {
		return maxtimeallowed;
	}

	/**
	 * @return
	 */
	public int getNpage() {
		return npage;
	}

	/**
	 * @return
	 */
	public String getOid() {
		return oid;
	}

	/**
	 * @return
	 */
	public String getOtype() {
		return otype;
	}

	/**
	 * @return
	 */
	public String getParameterstring() {
		return parameterstring;
	}

	/**
	 * @return
	 */
	public String getPrerequisites() {
		return prerequisites;
	}

	/**
	 * @return
	 */
	public String getSdesc() {
		return sdesc;
	}

	/**
	 * @return
	 */
	public int getSequence() {
		return sequence;
	}

	/**
	 * @return
	 */
	public String getServer() {
		return server;
	}

	/**
	 * @return
	 */
	public String getSubj() {
		return subj;
	}

	/**
	 * @return
	 */
	public int getThelevel() {
		return thelevel;
	}

	/**
	 * @return
	 */
	public String getTimelimitaction() {
		return timelimitaction;
	}

	/**
	 * @param string
	 */
	public void setDatafromlms(String string) {
		datafromlms = string;
	}

	/**
	 * @param string
	 */
	public void setFiletype(String string) {
		filetype = string;
	}

	/**
	 * @param string
	 */
	public void setIdentifier(String string) {
		identifier = string;
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
	public void setMaster(String string) {
		master = string;
	}

	/**
	 * @param i
	 */
	public void setMasteryscore(int i) {
		masteryscore = i;
	}

	/**
	 * @param string
	 */
	public void setMaxtimeallowed(String string) {
		maxtimeallowed = string;
	}

	/**
	 * @param i
	 */
	public void setNpage(int i) {
		npage = i;
	}

	/**
	 * @param string
	 */
	public void setOid(String string) {
		oid = string;
	}

	/**
	 * @param string
	 */
	public void setOtype(String string) {
		otype = string;
	}

	/**
	 * @param string
	 */
	public void setParameterstring(String string) {
		parameterstring = string;
	}

	/**
	 * @param string
	 */
	public void setPrerequisites(String string) {
		prerequisites = string;
	}

	/**
	 * @param string
	 */
	public void setSdesc(String string) {
		sdesc = string;
	}

	/**
	 * @param i
	 */
	public void setSequence(int i) {
		sequence = i;
	}

	/**
	 * @param string
	 */
	public void setServer(String string) {
		server = string;
	}

	/**
	 * @param string
	 */
	public void setSubj(String string) {
		subj = string;
	}

	/**
	 * @param i
	 */
	public void setThelevel(int i) {
		thelevel = i;
	}

	/**
	 * @param string
	 */
	public void setTimelimitaction(String string) {
		timelimitaction = string;
	}

	/**
	 * @return
	 */
	public String getStarting() {
		return starting;
	}

	/**
	 * @param string
	 */
	public void setStarting(String string) {
		starting = string;
	}

	/**
	 * @return
	 */
	public int getCntUsed() {
		return cntUsed;
	}

	/**
	 * @return
	 */
	public Hashtable getSubjList() {
		return subjList;
	}

	/**
	 * @param i
	 */
	public void setCntUsed(int i) {
		cntUsed = i;
	}

	/**
	 * @param hashtable
	 */
	public void setSubjList(Hashtable hashtable) {
		subjList = hashtable;
	}

	/**
	 * @return
	 */
	public String getMastername() {
		return mastername;
	}

	/**
	 * @param string
	 */
	public void setMastername(String string) {
		mastername = string;
	}

}
