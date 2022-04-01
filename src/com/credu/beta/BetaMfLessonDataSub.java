//**********************************************************
//1. ��      ��: MasterFormListDataSub DATA
//2. ���α׷���: ��������� Object����
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: LeeSuMin 2003. 10. 22
//7. ��      ��: 
//                 
//**********************************************************
package com.credu.beta;

import java.util.*;
import com.credu.library.*;

public class BetaMfLessonDataSub {
	/* tz_object informations */
	private	String 	oid				="";
	private	String	type            ="";
	private	String 	types           ="";
	private	int	npage           = 0;
	private	String 	sdesc           ="";
	private	String 	starting        ="";
	private	int 	ordering        = 0;
	private	int	branch			= 0;
	
	/*addon */
	private	String	isEduChecked = "N";
	private	String 	isPreviewed	 = "N";		//������ Object����
	
	/*for Branch */
	private	String branchnm	="";	// �б� Object��
	private	String broid="";		// �б� Oid.
	
	
	public BetaMfLessonDataSub() {};
	
	/**
	 * @return
	 */
	public int getBranch() {
		return branch;
	}

	/**
	 * @return
	 */
	public String getIsEduChecked() {
		return isEduChecked;
	}

	/**
	 * @return
	 */
	public String getIsPreviewed() {
		return isPreviewed;
	}

	/**z
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
	public int getOrdering() {
		return ordering;
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
	public String getStarting() {
		return starting;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @return
	 */
	public String getTypes() {
		return types;
	}

	/**
	 * @param i
	 */
	public void setBranch(int i) {
		branch = i;
	}

	/**
	 * @param string
	 */
	public void setIsEduChecked(String string) {
		isEduChecked = string;
	}

	/**
	 * @param string
	 */
	public void setIsPreviewed(String string) {
		isPreviewed = string;
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
	 * @param i
	 */
	public void setOrdering(int i) {
		ordering = i;
	}

	/**
	 * @param string
	 */
	public void setSdesc(String string) {
		sdesc = string;
	}

	/**
	 * @param string
	 */
	public void setStarting(String string) {
		starting = string;
	}

	/**
	 * @param string
	 */
	public void setType(String string) {
		type = string;
	}

	/**
	 * @param string
	 */
	public void setTypes(String string) {
		types = string;
	}

	/**
	 * @return
	 */
	public String getBranchnm() {
		return branchnm;
	}

	/**
	 * @return
	 */
	public String getBroid() {
		return broid;
	}

	/**
	 * @param string
	 */
	public void setBranchnm(String string) {
		branchnm = string;
	}

	/**
	 * @param string
	 */
	public void setBroid(String string) {
		broid = string;
	}

}
