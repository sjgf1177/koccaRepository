//**********************************************************
//  1. 제      목: Subject Data 정의(베타테스트시스템)
//  2. 프로그램명: SubjectData.java
//  3. 개      요: Subject Data 정의(베타테스트시스템)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 26
//  7. 수      정:
//**********************************************************
package com.credu.beta;

public class SubjectSimulationData {
	private String grcode;
	private String subj;
	private String subjnm;
	private String isonoff;
	private String subjclass;
	private String upperclass;
	private String middleclass;
	private String lowerclass;
	private String specials;
	private String muserid;
	private String cuserid;
	private String isuse;
	private String isgoyong;
	private int    goyongprice;
	private String ispropose;
	private String mftype;
	private String ismfmenuimg;
	private String ismfbranch;
	private String mforder;
	private String mfdlist;
	private String mfstart;
	private String mfnmtype;
	private String mfgrdate;
	private String otbgcolor;
	private String iscentered;
	private int    biyong;
	private int    edudays;
	private int    studentlimit;
	private String usebook;
	private int    bookprice;
	private String owner;
	private String producer;
	private String crdate;
	private String language;
	private String server;
	private String dir;
	private String eduurl;
	private String vodurl;
	private String preurl;
	private String conturl;
	private int    ratewbt;
	private int    ratevod;
	private int    framecnt;
	private String env;
	private String tutor;
	private String bookname;
	private String sdesc;
	private int    warndays;
	private int    stopdays;
	private int    point;
	private int    edulimit;
	private int    gradscore;
	private int    gradstep;
	private double wstep;
	private double wmtest;
	private double wftest;
	private double wreport;
	private double wact;
	private double wetc1;
	private double wetc2;
	private String inuserid;
	private String indate;
	private String luserid;
	private String ldate;

	private String classname;
	private String codenm;
	private String name;
	
	private String place;
	private String isessential;
	private int    score;
	private String subjtarget;
	
	private String museridnm;
	private String cuseridnm;
	private String producernm;
	private String ownernm;
	
	private String proposetype;
	private int    edutimes;
	private String edutype;
	private String intro;
	private String explain;
	private String company;
	private String companynm;
	
	private String contenttype;
	
	private	int    gradreport; 			//이수기준 - 리포트
	private	int    gradexam;   			//이수기준 - 시험
	private	double whtest; 				//형성평가
	private	String usesubjseqapproval; 	//결재여부 - 차수개설주관팀장
	private String useproposeapproval;	//결재여부 - 수강신청현업팀장
	private String usemanagerapproval;	//결재여부 - 주관팀장수강승인
	private int	   rndcreditreq;		//학점배점(연구개발-필수)
	private int    rndcreditchoice;		//학점배점(연구개발-선택)
	private int	   rndcreditadd;		//학점배점(연구개발-지정가점)
	private int	   rndcreditdeduct;		//학점배점(연구개발-지정감점)
	private String isablereview;		//복습가능여부
	private String isoutsourcing;		//위탁교육여부
	private String edumans;				//맛보기 대상	

	public SubjectSimulationData() {
		grcode = "";
		subj  = "";
		subjnm = "";
		isonoff = "";
		subjclass = "";
		upperclass = "";
		middleclass = "";
		lowerclass = "";
		specials = "";
		muserid = "";
		cuserid = "";
		isuse = "";
		isgoyong = "";
		ispropose = "";
		mftype = "";
		ismfmenuimg = "";
		ismfbranch = "";
		mforder = "";
		mfdlist = "";
		mfstart = "";
		mfnmtype = "";
		mfgrdate = "";
		otbgcolor = "";
		iscentered = "";
		biyong = 0;
		edudays = 0;
		studentlimit = 0;
		usebook = "";
		bookprice = 0;
		owner = "";
		producer = "";
		crdate = "";
		language = "";
		server = "";
		dir = "";
		eduurl = "";
		vodurl = "";
		preurl = "";
		conturl = "";
		ratewbt = 0;
		ratevod = 0;
		framecnt = 0;
		env = "";
		tutor = "";
		bookname = "";
		sdesc = "";
		warndays = 0;
		stopdays = 0;
		point = 0;
		edulimit = 0;
		gradscore = 0;
		gradstep = 0;
		wstep  = 0;
		wmtest = 0;
		wftest = 0;
		wreport = 0;
		wact = 0;
		wetc1 = 0;
		wetc2 = 0;
		inuserid = "";
		indate = "";
		luserid = "";
		ldate = "";

		classname = "";
		codenm = "";
		name = "";

		place = "";
		isessential = "";
		score = 0;
		subjtarget = "";

		museridnm = "";
		cuseridnm = "";
		producernm = "";
		ownernm = "";
		
		gradreport = 0;	//이수기준 - 리포트
		gradexam = 0;	//이수기준 - 시험
		whtest = 0;		//형성평가
		usesubjseqapproval = "";
		useproposeapproval = "";
		usemanagerapproval = "";
		rndcreditreq = 0;
		rndcreditchoice = 0;
		rndcreditadd = 0;
		rndcreditdeduct = 0;
		isablereview = "";
		isoutsourcing = "";
		edumans = "";
	}

	/**
	 * @return
	 */
	public int getBiyong() {
		return biyong;
	}

	/**
	 * @return
	 */
	public int getBookprice() {
		return bookprice;
	}

	/**
	 * @return
	 */
	public String getClassname() {
		return classname;
	}

	/**
	 * @return
	 */
	public String getCodenm() {
		return codenm;
	}

	/**
	 * @return
	 */
	public String getCrdate() {
		return crdate;
	}

	/**
	 * @return
	 */
	public String getCuserid() {
		return cuserid;
	}

	/**
	 * @return
	 */
	public String getDir() {
		return dir;
	}

	/**
	 * @return
	 */
	public int getEdudays() {
		return edudays;
	}

	/**
	 * @return
	 */
	public int getEdulimit() {
		return edulimit;
	}

	/**
	 * @return
	 */
	public String getEduurl() {
		return eduurl;
	}

	/**
	 * @return
	 */
	public String getEnv() {
		return env;
	}

	/**
	 * @return
	 */
	public int getFramecnt() {
		return framecnt;
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
	 * @return
	 */
	public String getIndate() {
		return indate;
	}

	/**
	 * @return
	 */
	public String getInuserid() {
		return inuserid;
	}

	/**
	 * @return
	 */
	public String getIscentered() {
		return iscentered;
	}

	/**
	 * @return
	 */
	public String getIsgoyong() {
		return isgoyong;
	}

	/**
	 * @return
	 */
	public String getIsmfbranch() {
		return ismfbranch;
	}

	/**
	 * @return
	 */
	public String getIsmfmenuimg() {
		return ismfmenuimg;
	}

	/**
	 * @return
	 */
	public String getIsonoff() {
		return isonoff;
	}

	/**
	 * @return
	 */
	public String getIspropose() {
		return ispropose;
	}

	/**
	 * @return
	 */
	public String getIsuse() {
		return isuse;
	}

	/**
	 * @return
	 */
	public String getLanguage() {
		return language;
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
	public String getLowerclass() {
		return lowerclass;
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
	public String getMfdlist() {
		return mfdlist;
	}

	/**
	 * @return
	 */
	public String getMfgrdate() {
		return mfgrdate;
	}

	/**
	 * @return
	 */
	public String getMfnmtype() {
		return mfnmtype;
	}

	/**
	 * @return
	 */
	public String getMforder() {
		return mforder;
	}

	/**
	 * @return
	 */
	public String getMfstart() {
		return mfstart;
	}

	/**
	 * @return
	 */
	public String getMftype() {
		return mftype;
	}

	/**
	 * @return
	 */
	public String getMiddleclass() {
		return middleclass;
	}

	/**
	 * @return
	 */
	public String getMuserid() {
		return muserid;
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
	public String getOtbgcolor() {
		return otbgcolor;
	}

	/**
	 * @return
	 */
	public String getOwner() {
		return owner;
	}

	/**
	 * @return
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * @return
	 */
	public String getPreurl() {
		return preurl;
	}

	/**
	 * @return
	 */
	public String getConturl() {
		return conturl;
	}

	/**
	 * @return
	 */
	public String getProducer() {
		return producer;
	}

	/**
	 * @return
	 */
	public int getRatevod() {
		return ratevod;
	}

	/**
	 * @return
	 */
	public int getRatewbt() {
		return ratewbt;
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
	public String getServer() {
		return server;
	}

	/**
	 * @return
	 */
	public String getSpecials() {
		return specials;
	}

	/**
	 * @return
	 */
	public int getStopdays() {
		return stopdays;
	}

	/**
	 * @return
	 */
	public int getStudentlimit() {
		return studentlimit;
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
	public String getSubjclass() {
		return subjclass;
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
	public String getGrcode() {
		return grcode;
	}

	/**
	 * @return
	 */
	public String getTutor() {
		return tutor;
	}

	/**
	 * @return
	 */
	public String getUpperclass() {
		return upperclass;
	}

	/**
	 * @return
	 */
	public String getUsebook() {
		return usebook;
	}

	/**
	 * @return
	 */
	public String getVodurl() {
		return vodurl;
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
	public int getWarndays() {
		return warndays;
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
	
	/****************************
	//add
	/****************************
	/**
	 * @return
	 */
	public int getGradexam() {
		return gradexam;
	}

	/**
	 * @return
	 */
	public int getGradreport() {
		return gradreport;
	}

	/**
	 * @return
	 */
	public double getWhtest() {
		return whtest;
	}

	/**
	 * @return
	 */
	public String getUsesubjseqapproval() {
		return usesubjseqapproval;
	}
	
	/**
	 * @return
	 */
	public String getUseproposeapproval() {
		return useproposeapproval;
	}
	
	/**
	 * @return
	 */
	public String getUsemanagerapproval() {
		return usemanagerapproval;
	}
	
	/**
	 * @return
	 */
	public int getRndcreditreq() {
		return rndcreditreq;
	}
	
	/**
	 * @return
	 */
	public int getRndcreditchoice() {
		return rndcreditchoice;
	}
	
	/**
	 * @return
	 */
	public int getRndcreditadd() {
		return rndcreditadd;
	}
	
	/**
	 * @return
	 */
	public int getRndcreditdeduct() {
		return rndcreditdeduct;
	}
	
	/**
	 * @return
	 */
	public String getIsablereview() {
		return isablereview;
	}
	
	/**
	 * @return
	 */
	public String getIsoutsourcing() {
		return isoutsourcing;
	}
	
	/**
	 * @return
	 */
	public String getEdumans() {
		return edumans;
	}
	
	/*******************************
	/* set method
	/*******************************
	

	/**
	 * @param i
	 */
	public void setBiyong(int i) {
		biyong = i;
	}

	/**
	 * @param i
	 */
	public void setBookprice(int i) {
		bookprice = i;
	}

	/**
	 * @param string
	 */
	public void setClassname(String string) {
		classname = string;
	}

	/**
	 * @param string
	 */
	public void setCodenm(String string) {
		codenm = string;
	}

	/**
	 * @param string
	 */
	public void setCrdate(String string) {
		crdate = string;
	}

	/**
	 * @param string
	 */
	public void setCuserid(String string) {
		cuserid = string;
	}

	/**
	 * @param string
	 */
	public void setDir(String string) {
		dir = string;
	}

	/**
	 * @param i
	 */
	public void setEdudays(int i) {
		edudays = i;
	}

	/**
	 * @param i
	 */
	public void setEdulimit(int i) {
		edulimit = i;
	}

	/**
	 * @param string
	 */
	public void setEduurl(String string) {
		eduurl = string;
	}

	/**
	 * @param string
	 */
	public void setEnv(String string) {
		env = string;
	}

	/**
	 * @param i
	 */
	public void setFramecnt(int i) {
		framecnt = i;
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
	 * @param string
	 */
	public void setIndate(String string) {
		indate = string;
	}

	/**
	 * @param string
	 */
	public void setInuserid(String string) {
		inuserid = string;
	}

	/**
	 * @param string
	 */
	public void setIscentered(String string) {
		iscentered = string;
	}

	/**
	 * @param string
	 */
	public void setIsgoyong(String string) {
		isgoyong = string;
	}

	/**
	 * @param string
	 */
	public void setIsmfbranch(String string) {
		ismfbranch = string;
	}

	/**
	 * @param string
	 */
	public void setIsmfmenuimg(String string) {
		ismfmenuimg = string;
	}

	/**
	 * @param string
	 */
	public void setIsonoff(String string) {
		isonoff = string;
	}

	/**
	 * @param string
	 */
	public void setIspropose(String string) {
		ispropose = string;
	}

	/**
	 * @param string
	 */
	public void setIsuse(String string) {
		isuse = string;
	}

	/**
	 * @param string
	 */
	public void setLanguage(String string) {
		language = string;
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
	public void setLowerclass(String string) {
		lowerclass = string;
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
	public void setMfdlist(String string) {
		mfdlist = string;
	}

	/**
	 * @param string
	 */
	public void setMfgrdate(String string) {
		mfgrdate = string;
	}

	/**
	 * @param string
	 */
	public void setMfnmtype(String string) {
		mfnmtype = string;
	}

	/**
	 * @param string
	 */
	public void setMforder(String string) {
		mforder = string;
	}

	/**
	 * @param string
	 */
	public void setMfstart(String string) {
		mfstart = string;
	}

	/**
	 * @param string
	 */
	public void setMftype(String string) {
		mftype = string;
	}

	/**
	 * @param string
	 */
	public void setMiddleclass(String string) {
		middleclass = string;
	}

	/**
	 * @param string
	 */
	public void setMuserid(String string) {
		muserid = string;
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
	public void setOtbgcolor(String string) {
		otbgcolor = string;
	}

	/**
	 * @param string
	 */
	public void setOwner(String string) {
		owner = string;
	}

	/**
	 * @param i
	 */
	public void setPoint(int i) {
		point = i;
	}

	/**
	 * @param string
	 */
	public void setPreurl(String string) {
		preurl = string;
	}
	
	/**
	 * @param string
	 */
	public void setConturl(String string) {
		conturl = string;
	}

	/**
	 * @param string
	 */
	public void setProducer(String string) {
		producer = string;
	}

	/**
	 * @param i
	 */
	public void setRatevod(int i) {
		ratevod = i;
	}

	/**
	 * @param i
	 */
	public void setRatewbt(int i) {
		ratewbt = i;
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
	public void setServer(String string) {
		server = string;
	}

	/**
	 * @param string
	 */
	public void setSpecials(String string) {
		specials = string;
	}

	/**
	 * @param i
	 */
	public void setStopdays(int i) {
		stopdays = i;
	}

	/**
	 * @param i
	 */
	public void setStudentlimit(int i) {
		studentlimit = i;
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
	public void setSubjclass(String string) {
		subjclass = string;
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
	public void setSubjnm(String string) {
		subjnm = string;
	}

	/**
	 * @param string
	 */
	public void setTutor(String string) {
		tutor = string;
	}

	/**
	 * @param string
	 */
	public void setUpperclass(String string) {
		upperclass = string;
	}

	/**
	 * @param string
	 */
	public void setUsebook(String string) {
		usebook = string;
	}

	/**
	 * @param string
	 */
	public void setVodurl(String string) {
		vodurl = string;
	}

	/**
	 * @param d
	 */
	public void setWact(double d) {
		wact = d;
	}

	/**
	 * @param i
	 */
	public void setWarndays(int i) {
		warndays = i;
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
	public String getBookname() {
		return bookname;
	}

	/**
	 * @param string
	 */
	public void setBookname(String string) {
		bookname = string;
	}

	/**
	 * @return
	 */
	public String getIsessential() {
		return isessential;
	}

	/**
	 * @return
	 */
	public int getScore() {
		return score;
	}

	/**
	 * @return
	 */
	public String getPlace() {
		return place;
	}

	/**
	 * @return
	 */
	public String getSubjtarget() {
		return subjtarget;
	}

	/**
	 * @param string
	 */
	public void setIsessential(String string) {
		isessential = string;
	}

	/**
	 * @param int
	 */
	public void setScore(int i) {
		score = i;
	}

	/**
	 * @param string
	 */
	public void setPlace(String string) {
		place = string;
	}

	/**
	 * @param string
	 */
	public void setSubjtarget(String string) {
		subjtarget = string;
	}

	/**
	 * @return
	 */
	public String getCuseridnm() {
		return cuseridnm;
	}

	/**
	 * @return
	 */
	public String getMuseridnm() {
		return museridnm;
	}

	/**
	 * @return
	 */
	public String getOwnernm() {
		return ownernm;
	}

	/**
	 * @return
	 */
	public String getProducernm() {
		return producernm;
	}

	/**
	 * @param string
	 */
	public void setCuseridnm(String string) {
		cuseridnm = string;
	}

	/**
	 * @param string
	 */
	public void setMuseridnm(String string) {
		museridnm = string;
	}

	/**
	 * @param string
	 */
	public void setOwnernm(String string) {
		ownernm = string;
	}

	/**
	 * @param string
	 */
	public void setProducernm(String string) {
		producernm = string;
	}
	
	/**
	 * @return
	 */
	public String getProposetype() {
		return proposetype;
	}

	/**
	 * @param string
	 */
	public void setProposetype(String string) {
		proposetype = string;
	}
	
	
	/*****************
	/Add
	/*****************
	
	/**
	 * @param i
	 */
	public void setGradreport(int i) {
		gradreport = i;
	}

	/**
	 * @param i
	 */
	public void setGradexam(int i) {
		gradexam = i;
	}
	
	/**
	 * @param d
	 */
	public void setWhtest(double d) {
		whtest = d;
	}


	/**
	 * @param string
	 */
	public void setUsesubjseqapproval(String string) {
		usesubjseqapproval = string;
	}
	
	/**
	 * @param string
	 */
	public void setUseproposeapproval(String string) {
		useproposeapproval = string;
	}
	
	/**
	 * @param string
	 */
	public void setUsemanagerapproval(String string) {
		usemanagerapproval = string;
	}
	
	/**
	 * @param int
	 */
	public void setRndcreditreq(int i) {
		rndcreditreq = i;
	}
	
	/**
	 * @param int
	 */
	public void setRndcreditchoice(int i) {
		rndcreditchoice = i;
	}
	
	/**
	 * @param int
	 */
	public void setRndcreditadd(int i) {
		rndcreditadd = i;
	}
	
	/**
	 * @param int
	 */
	public void setRndcreditdeduct(int i) {
		rndcreditdeduct = i;
	}
	
	/**
	 * @param string
	 */
	public void setIsablereview(String string) {
		isablereview = string;
	}
	
	/**
	 * @param string
	 */
	public void setIsoutsourcing(String string) {
		isoutsourcing = string;
	}
	
	/**
	 * @param string
	 */
	public void setEdumans(String string) {
		edumans = string;
	}
	
    public void setEdutimes(int edutimes)       { this.edutimes = edutimes;     }
    public int  getEdutimes()                   { return edutimes;              } 	   
    	
    public void   setEdutype(String edutype)    { this.edutype = edutype;       }
    public String getEdutype()                  { return edutype;               } 		

    public void   setIntro(String intro)        { this.intro = intro;           }
    public String getIntro()                    { return intro;                 } 	
    	
    public void   setExplain(String explain)    { this.explain = explain;       }
    public String getExplain()                  { return explain;               } 	    	
	
    public void setGoyongprice(int goyongprice) { this.goyongprice = goyongprice;     }
    public int  getGoyongprice()                { return goyongprice;              } 	   

	public String getContenttype() {
		return contenttype;
	}	
	public void setContenttype(String string) {
		contenttype= string;
	}
	
	public void   setCompany(String company) { this.company = company;			}
	public String getCompany()				  { return company;					}
	
	public void  setCompanynm(String companynm) { this.companynm = companynm;			}
	public String getCompanynm()				  { return companynm;					}

}
