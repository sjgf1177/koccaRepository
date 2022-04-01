//**********************************************************
//  1. 제      목: Subject Data 정의
//  2. 프로그램명: SubjectData.java
//  3. 개      요: Subject Data 정의
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 14
//  7. 수      정:
//**********************************************************
package com.credu.course;

public class SubjectData {
    private String grcode;
    private String subj;
    private String subjnm;
    private String isonoff;
    private String subjclass;
    private String upperclass;
    private String upperclassnm;
    private String middleclass;
    private String middleclassnm;
    private String lowerclass;
    private String lowerclassnm;
    private String specials;
    private String muserid;
    private String musertel;
    private String cuserid;
    private String isuse;
    private String isapproval;
    private String isgoyong;
    private int goyongprice;
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
    private int biyong;
    private int edudays;
    private int studentlimit;
    private String usebook;
    private int bookprice;
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
    private int ratewbt;
    private int ratevod;
    private int framecnt;
    private String env;
    private String tutor;
    private String bookname;
    private String sdesc;
    private int warndays;
    private int stopdays;
    private int point;
    private int edulimit;
    private int gradscore;
    private int gradstep;
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
    private String placejh;
    private String ispromotion;
    private String isessential;
    private int score;
    private String subjtarget;

    private String museridnm;
    private String cuseridnm;
    private String producernm;
    private String ownernm;

    private String proposetype;
    private int edutimes;
    private String edutype;
    private String intro;
    private String explain;

    private String contenttype;
    private String contentgrade;

    private int gradreport; //이수기준 - 리포트
    private int gradexam; //이수기준 - 시험(중간평가)
    private int gradftest; //이수기준 - 시험(최종평가)
    private int gradhtest; //이수기준 - 시험(형성평가)
    private double whtest; //형성평가
    private String isablereview; //복습가능여부
    private int reviewdays; //복습기간
    private String reviewtype; //복습기간 단위(D:일, W:주, M:월, Y:년)
    private String isoutsourcing; //위탁교육여부
    private String edumans; //맛보기 대상
    private String bookfilenamereal; //집합과정 첨부파일
    private String bookfilenamenew; //집합과정 첨부파일

    private String isvisible; //학습자에게 보여주기
    private String isalledu; //전사교육여부

    private int subjseqcount; //과정차수 개수
    private int subjobjcount; //과정매핑컨텐츠 개수

    private String isintroduction; //학습소개 사용여부
    private int eduperiod; //학습기간
    private int goyongpricemajor; //고용보험 환급액(대기업)
    private int goyongpriceminor; //고용보험 환급액(중소기업)
    private String introducefilenamereal; //과정소개 이미지
    private String introducefilenamenew; //과정소개 이미지
    private String informationfilenamereal; //파일(목차)
    private String informationfilenamenew; //파일(목차)
    private String submainfilenamereal; //서브메인 화면용 이미지
    private String submainfilenamenew; //서브메인 화면용 이미지

    private String isordinary; // 상시/수시
    private String isworkshop; // 워크숍
    private String isunit; // 학점인증
    private String memo; // 비고
    private String sphere; // 분야 (문콘)

    private int prewidth; //맛보기 창 width
    private int preheight; //맛보기 창 height

    private String subjtype; //과정유형
    private String autoconfirm; //수시과정여부
    private String suryoyn; //수료증출력가능여부
    private String suryotitle; //수료증제목
    private String needinput; //수강신청 입력정보
    private String area;
    private String degree;
    private String hitnumber;

    private String wj_classkey;
    private String search_nm;

    private String mobile_preurl; // 모바일 미리보기 url
    private String bizType; // 모바일 미리보기 url
    private String mobileUseYn; // 모바일 미리보기 url
    private String subjTags; // 태그
    private String subjSummary; // 과정개요

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getHitnumber() {
        return hitnumber;
    }

    public void setHitnumber(String hitnumber) {
        this.hitnumber = hitnumber;
    }

    public SubjectData() {
        grcode = "";
        subj = "";
        subjnm = "";
        isonoff = "";
        subjclass = "";
        upperclass = "";
        upperclassnm = "";
        middleclass = "";
        middleclassnm = "";
        lowerclass = "";
        lowerclassnm = "";
        specials = "";
        muserid = "";
        musertel = "";
        cuserid = "";
        isuse = "";
        isapproval = "";
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
        wstep = 0;
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
        placejh = "";
        ispromotion = "";
        isessential = "";
        score = 0;
        subjtarget = "";

        museridnm = "";
        cuseridnm = "";
        producernm = "";
        ownernm = "";

        gradreport = 0; //이수기준 - 리포트
        gradexam = 0; //이수기준 - 시험(중간평가)
        gradftest = 0; //이수기준 - 시험(최종평가)
        gradhtest = 0; //이수기준 - 시험(형성평가)
        whtest = 0; //형성평가
        isablereview = "";
        isoutsourcing = "";
        edumans = "";
        bookfilenamereal = "";
        bookfilenamenew = "";
        subjseqcount = 0;

        isvisible = "Y";
        isalledu = "N";
        area = "";
        degree = "0";
        hitnumber = "0";

        wj_classkey = "";
        search_nm = "";
        mobile_preurl = "";
        bizType = "";
        mobileUseYn = "";
        subjTags = "";
        subjSummary = "";
    }

    public String getSubmainfilenamereal() {
        return submainfilenamereal;
    }

    public void setSubmainfilenamereal(String submainfilenamereal) {
        this.submainfilenamereal = submainfilenamereal;
    }

    public String getSubmainfilenamenew() {
        return submainfilenamenew;
    }

    public void setSubmainfilenamenew(String submainfilenamenew) {
        this.submainfilenamenew = submainfilenamenew;
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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
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
    public String getIsapproval() {
        return isapproval;
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
    public String getMusertel() {
        return musertel;
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
    public String getUpperclassnm() {
        return upperclassnm;
    }

    /**
     * @return
     */
    public String getMiddleclassnm() {
        return middleclassnm;
    }

    /**
     * @return
     */
    public String getLowerclassnm() {
        return lowerclassnm;
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
     * //add /**************************** /**
     * 
     * @return
     */
    public int getGradexam() {
        return gradexam;
    }

    /**
     * @return
     */
    public int getGradftest() {
        return gradftest;
    }

    /**
     * @return
     */
    public int getGradhtest() {
        return gradhtest;
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
    public String getIsablereview() {
        return isablereview;
    }

    /**
     * @return
     */
    public String getReviewtype() {
        return reviewtype;
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

    /**
     * @return
     */
    public String getBookfilenamereal() {
        return bookfilenamereal;
    }

    /**
     * @return
     */
    public String getBookfilenamenew() {
        return bookfilenamenew;
    }

    /**
     * @return
     */
    public int getSubjseqcount() {
        return subjseqcount;
    }

    /**
     * @return
     */
    public String getIsvisible() {
        return isvisible;
    }

    /**
     * @return
     */
    public String getIsalledu() {
        return isalledu;
    }

    /**
     * @return
     */
    public int getSubjobjcount() {
        return subjobjcount;
    }

    /**
     * @return
     */
    public String getMemo() {
        return memo;
    }

    public String getWj_classkey() {
        return wj_classkey;
    }

    public String getSearch_nm() {
        return search_nm;
    }

    /*******************************
     * /* set method /*******************************
     * 
     * 
     * /**
     * 
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
    public void setIsapproval(String string) {
        isapproval = string;
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
    public void setLowerclassnm(String string) {
        lowerclassnm = string;
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
    public void setMusertel(String string) {
        musertel = string;
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
    public void setUpperclassnm(String string) {
        upperclassnm = string;
    }

    /**
     * @param string
     */
    public void setMiddleclassnm(String string) {
        middleclassnm = string;
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
    public String getIspromotion() {
        return ispromotion;
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
    public String getPlacejh() {
        return placejh;
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
     * @param string
     */
    public void setIspromotion(String string) {
        ispromotion = string;
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
    public void setPlacejh(String string) {
        placejh = string;
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
     * /Add /*****************
     * 
     * /**
     * 
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
     * @param i
     */
    public void setGradftest(int i) {
        gradftest = i;
    }

    /**
     * @param i
     */
    public void setGradhtest(int i) {
        gradhtest = i;
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
    public void setIsablereview(String string) {
        isablereview = string;
    }

    /**
     * @param string
     */
    public void setReviewtype(String string) {
        reviewtype = string;
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

    /**
     * @param string
     */
    public void setBookfilenamereal(String string) {
        bookfilenamereal = string;
    }

    /**
     * @param string
     */
    public void setBookfilenamenew(String string) {
        bookfilenamenew = string;
    }

    /**
     * @param i
     */
    public void setSubjseqcount(int i) {
        subjseqcount = i;
    }

    /**
     * @param string
     */
    public void setIsvisible(String string) {
        isvisible = string;
    }

    /**
     * @param string
     */
    public void setIsalledu(String string) {
        isalledu = string;
    }

    /**
     * @param i
     */
    public void setSubjobjcount(int i) {
        subjobjcount = i;
    }

    public void setWj_classkey(String wj_classkey) {
        this.wj_classkey = wj_classkey;
    }

    public void setSearch_nm(String search_nm) {
        this.search_nm = search_nm;
    }

    /**
     * @param string
     */
    public void setMemo(String string) {
        memo = string;
    }

    public void setEdutimes(int edutimes) {
        this.edutimes = edutimes;
    }

    public int getEdutimes() {
        return edutimes;
    }

    public void setEdutype(String edutype) {
        this.edutype = edutype;
    }

    public String getEdutype() {
        return edutype;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getIntro() {
        return intro;
    }

    public void setExplain(String explain) {
        this.explain = explain;
    }

    public String getExplain() {
        return explain;
    }

    public void setGoyongprice(int goyongprice) {
        this.goyongprice = goyongprice;
    }

    public int getGoyongprice() {
        return goyongprice;
    }

    public String getContenttype() {
        return contenttype;
    }

    public void setContenttype(String string) {
        contenttype = string;
    }

    public String getContentgrade() {
        return contentgrade;
    }

    public void setContentgrade(String string) {
        contentgrade = string;
    }

    public void setIsintroduction(String isintroduction) {
        this.isintroduction = isintroduction;
    }

    public String getIsintroduction() {
        return isintroduction;
    }

    public void setEduperiod(int eduperiod) {
        this.eduperiod = eduperiod;
    }

    public int getEduperiod() {
        return eduperiod;
    }

    public void setGoyongpricemajor(int goyongpricemajor) {
        this.goyongpricemajor = goyongpricemajor;
    }

    public int getGoyongpricemajor() {
        return goyongpricemajor;
    }

    public void setGoyongpriceminor(int goyongpriceminor) {
        this.goyongpriceminor = goyongpriceminor;
    }

    public int getGoyongpriceminor() {
        return goyongpriceminor;
    }

    public void setIntroducefilenamereal(String introducefilenamereal) {
        this.introducefilenamereal = introducefilenamereal;
    }

    public String getIntroducefilenamereal() {
        return introducefilenamereal;
    }

    public void setIntroducefilenamenew(String introducefilenamenew) {
        this.introducefilenamenew = introducefilenamenew;
    }

    public String getIntroducefilenamenew() {
        return introducefilenamenew;
    }

    public void setInformationfilenamereal(String informationfilenamereal) {
        this.informationfilenamereal = informationfilenamereal;
    }

    public String getInformationfilenamereal() {
        return informationfilenamereal;
    }

    public String getInformationfilenamenew() {
        return informationfilenamenew;
    }

    public void setInformationfilenamenew(String informationfilenamenew) {
        this.informationfilenamenew = informationfilenamenew;
    }

    public String getIsordinary() {
        return isordinary;
    }

    public void setIsordinary(String isordinary) {
        this.isordinary = isordinary;
    }

    public String getIsunit() {
        return isunit;
    }

    public void setIsunit(String isunit) {
        this.isunit = isunit;
    }

    public String getIsworkshop() {
        return isworkshop;
    }

    public void setIsworkshop(String isworkshop) {
        this.isworkshop = isworkshop;
    }

    public String getSphere() {
        return sphere;
    }

    public void setSphere(String sphere) {
        this.sphere = sphere;
    }

    public void setPrewidth(int prewidth) {
        this.prewidth = prewidth;
    }

    public int getPrewidth() {
        return prewidth;
    }

    public void setPreheight(int preheight) {
        this.preheight = preheight;
    }

    public int getPreheight() {
        return preheight;
    }

    public void setReviewdays(int reviewdays) {
        this.reviewdays = reviewdays;
    }

    public int getReviewdays() {
        return reviewdays;
    }

    public String getSubjtype() {
        return subjtype;
    }

    public void setSubjtype(String subjtype) {
        this.subjtype = subjtype;
    }

    public String getAutoconfirm() {
        return autoconfirm;
    }

    public void setAutoconfirm(String autoconfirm) {
        this.autoconfirm = autoconfirm;
    }

    public String getSuryoyn() {
        return suryoyn;
    }

    public void setSuryoyn(String suryoyn) {
        this.suryoyn = suryoyn;
    }

    public String getSuryotitle() {
        return suryotitle;
    }

    public void setSuryotitle(String suryotitle) {
        this.suryotitle = suryotitle;
    }

    public String getNeedinput() {
        return needinput;
    }

    public void setNeedinput(String needinput) {
        this.needinput = needinput;
    }

    public void setMobile_preurl(String mobile_url) {
        this.mobile_preurl = mobile_url;
    }

    public String getMobile_preurl() {
        return mobile_preurl;
    }

    public void setBizType(String bizType) {
        this.bizType = bizType;
    }

    public String getBizType() {
        return this.bizType;
    }

    public void setMobileUseYn(String mobileUseYn) {
        this.mobileUseYn = mobileUseYn;
    }

    public String getMobileUseYn() {
        return this.mobileUseYn;
    }
    
    public void setSubjTags(String subjTags) {
    	this.subjTags = subjTags;
    }
    
    public String getSubjTags() {
    	return this.subjTags;
    }
    
    public void setSubjSummary(String subjSummary) {
    	this.subjSummary = subjSummary;
    }
    
    public String getSubjSummary() {
    	return this.subjSummary;
    }
}
