//**********************************************************
//1. 제      목: 
//2. 프로그램명: StoldData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-12
//7. 수      정: 이창훈 2004.12.25
//                 
//********************************************************** 
 
package com.credu.cp;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class CPStoldData {
	private String	subj;
	private String  year;
	private String	subjseq;
	private String	userid;
	private String	name;
	private String	comp;
	private String	dept;
	private String	deptname;
	private String	jik;

	private int gradscore;
	private int gradstep;

	private double  score;
	private double  tstep;
	private double  scoreFile;


	private double  mtest;     // 가중치 비적용           
	private double  ftest;   
	private double  htest;             
	private double  report;                
	private double  act;                
	private double  etc1;                
	private double  etc2;                

	private double  avtstep;   // 가중치 적용
	private double  avmtest;   
	private double  avhtest;
	private double  avftest;
	private double  avreport;
	private double  avact;
	private double  avetc1;
	private double  avetc2;

	private double  wstep;    // 가중치 비율
	private double  wmtest;  
	private double  whtest;
	private double  wftest;
	private double  wreport; 
	private double  wact;    
	private double  wetc1;   
	private double  wetc2;   
	
	private String  isgraduated;;
	private String  isb2c;
	private String	edustart;
	private String	eduend;
	private String	serno;
	private String	isrestudy;
	private String	notgraduetc;

	private String jikwi;
	private String jikwinm;
	private String cono;
	private String compnm;
	private String rank;

	public CPStoldData() { }	

	/**
	 * @return
	 */
	public double getAvact() {
		return avact;
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
	public double getAvftest() {
		return avftest;
	}

	/**
	 * @return
	 */
	public double getAvmtest() {
		return avmtest;
	}

	/**
	 * @return
	 */
	public double getAvreport() {
		return avreport;
	}

	/**
	 * @return
	 */
	public double getAvtstep() {
		return avtstep;
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
	public String getDept() {
		return dept;
	}

	/**
	 * @return
	 */
	public String getDeptname() {
		return deptname;
	}

	/**
	 * @return
	 */
	public String getEduend() {
		return eduend;
	}

	/**
	 * @return
	 */
	public String getEdustart() {
		return edustart;
	}

	/**
	 * @return
	 */
	public String getIsb2c() {
		return isb2c;
	}

	/**
	 * @return
	 */
	public String getIsgraduated() {
		return isgraduated;
	}

	/**
	 * @return
	 */
	public String getIsrestudy() {
		return isrestudy;
	}

	/**
	 * @return
	 */
	public String getJik() {
		return jik;
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
	public double getScore() {
		return score;
	}

	/**
	 * @return
	 */
	public String getSerno() {
		return serno;
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
	public String getSubjseq() {
		return subjseq;
	}

	/**
	 * @return
	 */
	public double getTstep() {
		return tstep;
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
	public String getYear() {
		return year;
	}

	/**
	 * @return
	 */
	public double getScoreFile() {
		return scoreFile;
	}
	
	/**
	 * @param d
	 */
	public void setAvact(double d) {
		avact = d;
	}

	/**
	 * @param d
	 */
	public void setAvetc1(double d) {
		avetc1 = d;
	}

	/**
	 * @param d
	 */
	public void setAvetc2(double d) {
		avetc2 = d;
	}

	/**
	 * @param d
	 */
	public void setAvftest(double d) {
		avftest = d;
	}

	/**
	 * @param d
	 */
	public void setAvmtest(double d) {
		avmtest = d;
	}

	/**
	 * @param d
	 */
	public void setAvreport(double d) {
		avreport = d;
	}

	/**
	 * @param d
	 */
	public void setAvtstep(double d) {
		avtstep = d;
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
	public void setDept(String string) {
		dept = string;
	}

	/**
	 * @param string
	 */
	public void setDeptname(String string) {
		deptname = string;
	}

	/**
	 * @param string
	 */
	public void setEduend(String string) {
		eduend = string;
	}

	/**
	 * @param string
	 */
	public void setEdustart(String string) {
		edustart = string;
	}

	/**
	 * @param string
	 */
	public void setIsb2c(String string) {
		isb2c = string;
	}

	/**
	 * @param string
	 */
	public void setIsgraduated(String string) {
		isgraduated = string;
	}

	/**
	 * @param string
	 */
	public void setIsrestudy(String string) {
		isrestudy = string;
	}

	/**
	 * @param string
	 */
	public void setJik(String string) {
		jik = string;
	}

	/**
	 * @param string
	 */
	public void setName(String string) {
		name = string;
	}

	/**
	 * @param d
	 */
	public void setScore(double d) {
		score = d;
	}

	/**
	 * @param string
	 */
	public void setSerno(String string) {
		serno = string;
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
	public void setSubjseq(String string) {
		subjseq = string;
	}

	/**
	 * @param d
	 */
	public void setTstep(double d) {
		tstep = d;
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
	public void setYear(String string) {
		year = string;
	}

	/**
	 * @return
	 */
	public double getAct() {
		return act;
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
	public double getFtest() {
		return ftest;
	}

	/**
	 * @return
	 */
	public double getMtest() {
		return mtest;
	}

	/**
	 * @return
	 */
	public double getReport() {
		return report;
	}

	/**
	 * @param d
	 */
	public void setAct(double d) {
		act = d;
	}

	/**
	 * @param d
	 */
	public void setEtc1(double d) {
		etc1 = d;
	}

	/**
	 * @param d
	 */
	public void setEtc2(double d) {
		etc2 = d;
	}

	/**
	 * @param d
	 */
	public void setFtest(double d) {
		ftest = d;
	}

	/**
	 * @param d
	 */
	public void setMtest(double d) {
		mtest = d;
	}

	/**
	 * @param d
	 */
	public void setReport(double d) {
		report = d;
	}

	/**
	 * @return
	 */
	public double getWact() {
		return wact;
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
	public double getWftest() {
		return wftest;
	}

	/**
	 * @return
	 */
	public double getWmtest() {
		return wmtest;
	}

	/**
	 * @return
	 */
	public double getWreport() {
		return wreport;
	}

	/**
	 * @return
	 */
	public double getWstep() {
		return wstep;
	}

	/**
	 * @param d
	 */
	public void setWact(double d) {
		wact = d;
	}

	/**
	 * @param d
	 */
	public void setWetc1(double d) {
		wetc1 = d;
	}

	/**
	 * @param d
	 */
	public void setWetc2(double d) {
		wetc2 = d;
	}

	/**
	 * @param d
	 */
	public void setWftest(double d) {
		wftest = d;
	}

	/**
	 * @param d
	 */
	public void setWmtest(double d) {
		wmtest = d;
	}

	/**
	 * @param d
	 */
	public void setWreport(double d) {
		wreport = d;
	}

	/**
	 * @param d
	 */
	public void setWstep(double d) {
		wstep = d;
	}

	/**
	 * @return
	 */
	public int getGradscore() {
		return gradscore;
	}

	/**
	 * @return
	 */
	public int getGradstep() {
		return gradstep;
	}

	/**
	 * @param i
	 */
	public void setGradscore(int i) {
		gradscore = i;
	}

	/**
	 * @param i
	 */
	public void setGradstep(int i) {
		gradstep = i;
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
	public String getCono() {
		return cono;
	}

	/**
	 * @return
	 */
	public String getJikwi() {
		return jikwi;
	}

	/**
	 * @return
	 */
	public String getJikwinm() {
		return jikwinm;
	}

	/**
	 * @param string
	 */
	public void setCompnm(String string) {
		compnm = string;
	}

	/**
	 * @param string
	 */
	public void setCono(String string) {
		cono = string;
	}

	/**
	 * @param string
	 */
	public void setJikwi(String string) {
		jikwi = string;
	}

	/**
	 * @param string
	 */
	public void setJikwinm(String string) {
		jikwinm = string;
	}

	/**
	 * @param d
	 */
	public void setScoreFile(double d) {
		scoreFile = d;
	}
	
	/**
	 * @return
	 */
	public double getHtest() {
		return htest;
	}
	
	/**
	 * @param d
	 */
	public void setHtest(double d) {
		htest = d;
	}
	
	/**
	 * @return
	 */
	public double getAvhtest() {
		return avhtest;
	}
	
	/**
	 * @param d
	 */
	public void setAvhtest(double d) {
		avhtest = d;
	}
	
	/**
	 * @return
	 */
	public double getWhtest() {
		return whtest;
	}
	
	/**
	 * @param d
	 */
	public void setWhtest(double d) {
		whtest = d;
	}
	
	/**
	 * @return
	 */
	public String getNotgraduetc() {
		return notgraduetc;
	}
	
	/**
	 * @param d
	 */
	public void setNotgraduetc(String string) {
		notgraduetc = string;
	}
	
	
	/**
	 * @return
	 */
	public String getRank() {
		return rank;
	}
	
	
	/**
	 * @param d
	 */
	public void setRank(String string) {
		rank = string;
	}
	
}
