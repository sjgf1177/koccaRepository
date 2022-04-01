//**********************************************************
//  1. 제      목: 참여도 Data
//  2. 프로그램명 : ActivityAdminData.java
//  3. 개      요: 모듈 data bean
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.11.12
//  7. 수      정:
//**********************************************************
package com.credu.study;
import com.credu.library.*;

//TZ_MENUSUB
//grcode, menu, seq, process, servlettype, method, luserid, ldate

public class ActivityAdminData
{

    private String  grcodenm;
    private String  subj;
	private String  year;
	private String  subjnm;
    private String	subjseq;
    private String	subjseqgr;
    private String	userid;
    private String	name;
    private int     logincnt;
    private int		qnacnt;
    private int     toroncnt;
    private double  etc1;
    private double  etc2;
    private double  wetc1;
    private double  wetc2;
    private double  avetc1;
    private double  avetc2;
	
    private String luserid;           // 최종수정자
    private String ldate;             // 최종수정일
    private int    seq;               // seq

    public ActivityAdminData() {}
    
	/**
	 * @return
	 */
	public String getGrcodenm() {
		return grcodenm;
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
	public String getYear() {
		return year;
	}

	/**
	 * @return
	 */
	public String getSubjnm() {
		return subjnm;
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
	public String getSubjseqgr() {
		return subjseqgr;
	}

	/**
	 * @return
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @return
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return
	 */
	public int getLogincnt() {
		return logincnt;
	}

	/**
	 * @return
	 */
	public int getQnacnt() {
		return qnacnt;
	}

	/**
	 * @return
	 */
	public int getToroncnt() {
		return toroncnt;
	}

	/**
	 * @return
	 */
	public double getEtc1() {
		return etc1;
	}

	/**
	 * @return
	 */
	public double getEtc2() {
		return etc2;
	}

	/**
	 * @return
	 */
	public double getWetc1() {
		return wetc1;
	}

	/**
	 * @return
	 */
	public double getWetc2() {
		return wetc2;
	}

	/**
	 * @return
	 */
	public double getAvetc1() {
		return avetc1;
	}

	/**
	 * @return
	 */
	public double getAvetc2() {
		return avetc2;
	}

	/**
	 * @return
	 */
	public int getSeq() {
		return seq;
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
	public void setSubj(String string) {
		subj = string;
	}

	/**
	 * @param string
	 */
	public void setYear(String string) {
		year = string;
	}

	/**
	 * @param string
	 */
	public void setSubjnm(String string) {
		subjnm = string;
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
	public void setSubjseqgr(String string) {
		subjseqgr = string;
	}

	/**
	 * @param string
	 */
	public void setUserid(String string) {
		userid = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param string
	 */
	public void setLogincnt(int i) {
		logincnt = i;
	}

	/**
	 * @param string
	 */
	public void setQnacnt(int i) {
		qnacnt = i;
	}

	/**
	 * @param string
	 */
	public void setToroncnt(int i) {
		toroncnt = i;
	}

	/**
	 * @param string
	 */
	public void setEtc1(double d) {
		etc1 = d;
	}

	/**
	 * @param string
	 */
	public void setEtc2(double d) {
		etc2 = d;
	}

	/**
	 * @param string
	 */
	public void setWetc1(double d) {
		wetc1 = d;
	}

	/**
	 * @param string
	 */
	public void setWetc2(double d) {
		wetc2 = d;
	}

	/**
	 * @param string
	 */
	public void setAvetc1(double d) {
		avetc1 = d;
	}

	/**
	 * @param string
	 */
	public void setAvetc2(double d) {
		avetc2 = d;
	}

	/**
	 * @param string
	 */
	public void setLuserid(String string) {
		luserid = string;
	}

	/**
	 * @param i
	 */
	public void setSeq(int i) {
		seq = i;
	}

}
