//**********************************************************
//  1. 제      목: SCO Object Operation Data
//  2. 프로그램명: SCOData.java
//  3. 개      요: SCO관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.11
//  7. 수      정: 박미복 2004. 11.11
//**********************************************************

package com.credu.beta;

import java.util.*;
import com.credu.library.*;

public class BetaSCOData {

	private	String 	oid		="";
	private	String	otype           ="";
	private	String 	filetype        ="";
	private	int	npage           =0;
	private	String 	sdesc           ="";
	private	String 	master          ="";
	private	String 	starting        ="";
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
	private	int	oidnumber        =0;
	private	String	highoid         ="";
	private	String	metalocation    ="";
	private	String	scolocate       ="";
	private	int	scoall       =0;
	private	String	scotitle       ="";
	private	int	metadata_idx =0 ;
	private	int	ordering =0 ;  // tz_scosubjobj 순서
	private	String	module ="" ;  // tz_scosubjobj 모듈
	private	String	lesson ="" ;  // tz_scosubjobj 레슨(차시)
	private	String	jindostatus ="" ;  // 진도결과
	private	String	core_lesson_location ="" ;  // 북마크 페이지

	
	/*addon */
	private	String	mastername		="";	//master 이름
	private	int	cntUsed			=0;		//사용된 과정수
	public		Hashtable subjList = new Hashtable();
	
	public BetaSCOData() {};
	
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

	/**
	 * @return
	 */
	public int getOidnumber() {
		return oidnumber;
	}

	/**
	 * @param i
	 */
	public void setOidnumber(int i) {
		oidnumber = i;
	}

	/**
	 * @return
	 */
	public String getHighoid() {
		return highoid;
	}

	/**
	 * @param string
	 */
	public void setHighoid(String string) {
		highoid = string;
	}

	/**
	 * @return
	 */
	public String getMetalocation() {
		return metalocation;
	}

	/**
	 * @param string
	 */
	public void setMetalocation(String string) {
		metalocation = string;
	}

	/**
	 * @return
	 */
	public String getScolocate() {
		return scolocate;
	}

	/**
	 * @param string
	 */
	public void setScolocate(String string) {
		scolocate = string;
	}

	/**
	 * @return
	 */
	public int getScoAll() {
		return scoall;
	}

	/**
	 * @param string
	 */
	public void setScoAll(int i) {
		scoall = i;
	}

	/**
	 * @return
	 */
	public String getScoTitle() {
		return scotitle;
	}

	/**
	 * @param string
	 */
	public void setScoTitle(String string) {
		scotitle = string;
	}

	/**
	 * @return
	 */
	public int getMetadata() {
		return metadata_idx;
	}

	/**
	 * @param string
	 */
	public void setMetadata(int i) {
		metadata_idx = i;
	}

	/**
	 * @return
	 */
	public int getOrdering() {
		return ordering;
	}

	/**
	 * @param string
	 */
	public void setOrdering(int i) {
		ordering = i;
	}

	/**
	 * @return
	 */
	public String getModule() {
		return module;
	}

	/**
	 * @param string
	 */
	public void setModule(String s) {
		module = s;
	}

	/**
	 * @return
	 */
	public String getLesson() {
		return lesson;
	}

	/**
	 * @param string
	 */
	public void setLesson(String s) {
		lesson = s;
	}

	/**
	 * @return
	 */
	public String getJindoStatus() {
		return jindostatus;
	}

	/**
	 * @param string
	 */
	public void setJindoStatus(String s) {
		jindostatus = s;
	}

	/**
	 * @return
	 */
	public String getCoreLessonLocation() {
		return core_lesson_location;
	}

	/**
	 * @param string
	 */
	public void setCoreLessonLocation(String s) {
		core_lesson_location = s;
	}

}