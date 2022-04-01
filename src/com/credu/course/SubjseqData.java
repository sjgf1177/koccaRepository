//**********************************************************
//1. 제      목: 교육차수관리화면용 DATA
//2. 프로그램명: GrseqRefData.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 07. 22
//7. 수      정:
//
//**********************************************************
package com.credu.course;


public class SubjseqData {
    private String  grcode       ;
    private String  grcodenm         ;
    private String  grseq       ;
    private String  gyear       ;
    private String  grseqnm     ;
    private String  subj        ;
    private String  subjnm      ;
    private String  year        ;
    private String  subjseq     ;
    private String  subjseqgr     ;
    private String  course      ;
    private String  cyear       ;
    private String  courseseq   ;
    private String  coursenm    ;
    private String  place    ;
    private String  placejh    ;

    private     String  isbelongcourse ;
    private     String  propstart      ;
    private     String  propend        ;
    private     String  edustart       ;
    private     String  eduend         ;
    private     String  endfirst       ;
    private     String  endfinal         ;
    private     String  isclosed       ;
    private     String  isgoyong       ;
    private     String  subj_isgoyong       ;
    private     String  ismultipaper   ;
    private     String  luserid        ;
    private     String  ldate          ;
    private     int     studentlimit  ;
    private     int     point         ;
    private     int     biyong        ;
    private     int     edulimit      ;
    private     int     warndays      ;
    private     int     stopdays      ;
    private     int     gradscore     ;
    private     int     gradstep      ;
    private     double      wstep         ;
    private     double      wmtest        ;
    private     double      wftest        ;
    private     double      wreport       ;
    private     double      wact          ;
    private     double      wetc1         ;
    private     double      wetc2         ;
    private String      proposetype;

    private         String  isonoff     ;
//    private     String  subjtypenm  ;
    private int     cnt_propose =0;
    private int     cnt_student =0;
    private int     cnt_stold   =0;
    private boolean  canDelete  =false;

    //add
	private String      isoutsourcing;  // 외주여부
    private     int     score; //학점배점
    private     int     gradreport; //이수기준 - 리포트
    private     int     gradexam;   //이수기준 -  중간평가
    private     int     gradftest;   //이수기준 -  최종평가
    private     int     gradhtest;   //이수기준 -  형성평가
    private     int     gradetc1;   //이수기준 -  기타
    private     double      whtest        ; //형성평가
    private String     isablereview;        //복습가능여부
    private int    tsubjbudget;     //과정예산
    private String isusebudget;     //과정예산 금액 제한 사용여부
    private String isessential = "";

    private String isvisible;       //학습자에게 보여주기
    private String bookname;        // 교재
    private int    bookprice;       // 교재비
    private int    sulpapernum;      // 설문번호
    private int    sulpapernum2;      // 설문번호2
    private int    canceldays;        // 수강신청취소기간
	private String isordinary;        // 상시(Y) / 수시(N)
	private String isworkshop;        // 워크샵(Y)
	private String isunit;            // 학점인증(Y)
	private String sulstartdate1;  //설문시작일1
	private String sulenddate1;  //설문종료일1
	private String sulstartdate2;  //설문시작일2
	private String sulenddate2;  //설문종료일2
    private int	   reviewdays;          //복습기간
    private String reviewtype;          //복습기간 단위(D:일, W:주, M:월, Y:년)

    private String startcanceldate;  //신청취소 시작일
	private String endcanceldate;  //신청취소 종료일
	private String charger; // 운영자 ID
	private String autoconfirm;//자동승인여부

    private String filedapply;
    private int edu_sumtime;
    private int bigcoin;
    private int prioritycoin;

    public String getFiledapply() {
        return filedapply;
    }

    public void setFiledapply(String filedapply) {
        this.filedapply = filedapply;
    }

    public int getEdu_sumtime() {
        return edu_sumtime;
    }

    public void setEdu_sumtime(int edu_sumtime) {
        this.edu_sumtime = edu_sumtime;
    }

    public int getBigcoin() {
        return bigcoin;
    }

    public void setBigcoin(int bigcoin) {
        this.bigcoin = bigcoin;
    }

    public int getPrioritycoin() {
        return prioritycoin;
    }

    public void setPrioritycoin(int prioritycoin) {
        this.prioritycoin = prioritycoin;
    }

    public String getSulstartdate1() {
		return sulstartdate1;
	}


	public void setSulstartdate1(String sulstartdate1) {
		this.sulstartdate1 = sulstartdate1;
	}


	public String getSulenddate1() {
		return sulenddate1;
	}


	public void setSulenddate1(String sulenddate1) {
		this.sulenddate1 = sulenddate1;
	}


	public String getSulstartdate2() {
		return sulstartdate2;
	}


	public void setSulstartdate2(String sulstartdate2) {
		this.sulstartdate2 = sulstartdate2;
	}


	public String getSulenddate2() {
		return sulenddate2;
	}


	public void setSulenddate2(String sulenddate2) {
		this.sulenddate2 = sulenddate2;
	}


	public String getStartcanceldate() {
		return startcanceldate;
	}


	public void setStartcanceldate(String startcanceldate) {
		this.startcanceldate = startcanceldate;
	}


	public String getEndcanceldate() {
		return endcanceldate;
	}


	public void setEndcanceldate(String endcanceldate) {
		this.endcanceldate = endcanceldate;
	}


	public  SubjseqData() {}


    public String getSubjtypenm(){

        if (isonoff==null || isonoff.equals(""))    return "aaa";
        if (isonoff.equals("ON"))   return "사이버";
        else                        return "집합";
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
    public boolean isCanDelete() {
        return canDelete;
    }

    /**
     * @return
     */
    public int getCnt_propose() {
        return cnt_propose;
    }

    /**
     * @return
     */
    public int getCnt_stold() {
        return cnt_stold;
    }

    /**
     * @return
     */
    public int getCnt_student() {
        return cnt_student;
    }

    /**
     * @return
     */
    public String getCourse() {
        return course;
    }

    /**
     * @return
     */
    public String getCoursenm() {
        return coursenm;
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
    public String getCourseseq() {
        return courseseq;
    }

    /**
     * @return
     */
    public String getCyear() {
        return cyear;
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
    public int getEdulimit() {
        return edulimit;
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
    public int getGradetc1() {
        return gradetc1;
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
    public String getGrcode() {
        return grcode;
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
    public String getGrseqnm() {
        return grseqnm;
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
    public String getIsbelongcourse() {
        return isbelongcourse;
    }

    /**
     * @return
     */
    public String getIsclosed() {
        return isclosed;
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
    public String getIsmultipaper() {
        return ismultipaper;
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
    public int getPoint() {
        return point;
    }

    /**
     * @return
     */
    public String getPropend() {
        return propend;
    }

    /**
     * @return
     */
    public String getPropstart() {
        return propstart;
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
    public double getWact() {
        return wact;
    }

    /**
     * @return
     */
    public double getWarndays() {
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

    /**
     * @return
     */
    public String getYear() {
        return year;
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
    public int getTsubjbudget() {
        return tsubjbudget;
    }

    /**
     * @return
     */
    public String getIsusebudget() {
        return isusebudget;
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
    public String getIsvisible() {
        return isvisible;
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
     * @param b
     */
    public void setCanDelete(boolean b) {
        canDelete = b;
    }

    /**
     * @param i
     */
    public void setCnt_propose(int i) {
        cnt_propose = i;
    }

    /**
     * @param i
     */
    public void setCnt_stold(int i) {
        cnt_stold = i;
    }

    /**
     * @param i
     */
    public void setCnt_student(int i) {
        cnt_student = i;
    }

    /**
     * @param string
     */
    public void setCourse(String string) {
        course = string;
    }

    /**
     * @param string
     */
    public void setCoursenm(String string) {
        coursenm = string;
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
    public void setCourseseq(String string) {
        courseseq = string;
    }

    /**
     * @param string
     */
    public void setCyear(String string) {
        cyear = string;
    }

    /**
     * @param string
     */
    public void setEduend(String string) {
        eduend = string;
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
    public void setEdustart(String string) {
        edustart = string;
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
    public void setGrcode(String string) {
        grcode = string;
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
    public void setGrseqnm(String string) {
        grseqnm = string;
    }

    /**
     * @param string
     */
    public void setGyear(String string) {
        gyear = string;
    }

    /**
     * @param string
     */
    public void setIsbelongcourse(String string) {
        isbelongcourse = string;
    }

    /**
     * @param string
     */
    public void setIsclosed(String string) {
        isclosed = string;
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
    public void setIsmultipaper(String string) {
        ismultipaper = string;
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
     * @param i
     */
    public void setPoint(int i) {
        point = i;
    }

    /**
     * @param string
     */
    public void setPropend(String string) {
        propend = string;
    }

    /**
     * @param string
     */
    public void setPropstart(String string) {
        propstart = string;
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
     * @param i
     */
    public void setWact(double i) {
        wact = i;
    }

    /**
     * @param i
     */
    public void setWarndays(int i) {
        warndays = i;
    }

    /**
     * @param i
     */
    public void setWetc1(double i) {
        wetc1 = i;
    }

    /**
     * @param i
     */
    public void setWetc2(double i) {
        wetc2 = i;
    }

    /**
     * @param i
     */
    public void setWftest(double i) {
        wftest = i;
    }

    /**
     * @param i
     */
    public void setWmtest(double i) {
        wmtest = i;
    }

    /**
     * @param i
     */
    public void setWreport(double i) {
        wreport = i;
    }

    /**
     * @param i
     */
    public void setWstep(double i) {
        wstep = i;
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
    public String getGrcodenm() {
        return grcodenm;
    }

    /**
     * @param string
     */
    public void setGrcodenm(String string) {
        grcodenm = string;
    }

    /**
     * @return
     */
    public String getSubj_isgoyong() {
        return subj_isgoyong;
    }

    /**
     * @param string
     */
    public void setSubj_isgoyong(String string) {
        subj_isgoyong = string;
    }

    /**
     * @return
     */
    public String getEndfinal() {
        return endfinal;
    }

    /**
     * @return
     */
    public String getEndfirst() {
        return endfirst;
    }

    /**
     * @param string
     */
    public void setEndfinal(String string) {
        endfinal = string;
    }

    /**
     * @param string
     */
    public void setEndfirst(String string) {
        endfirst = string;
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
    /add
    /*****************

	/**
     * @param i
     */
    public void setGradetc1(int i) {
        gradetc1 = i;
    }
    
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
    public void setScore(int i) {
        score = i;
    }

    /**
     * @param string
     */
    public void setTsubjbudget(int i) {
        tsubjbudget = i;
    }

    /**
     * @param string
     */
    public void setIsusebudget(String string) {
        isusebudget = string;
    }

    /**
     * @return
     */
    public String getIsessential() {
        return isessential;
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
    public void setIsvisible(String string) {
        isvisible = string;
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
    public int getBookprice() {
        return bookprice;
    }
    /**
     * @param i
     */
    public void setBookprice(int i) {
        bookprice = i;
    }


    /**
     * @return
     */
    public int getSulpapernum() {
        return sulpapernum;
    }

    /**
     * @return
     */
    public int getSulpapernum2() {
        return sulpapernum2;
    }

    /**
     * @param i
     */
    public void setSulpapernum(int i) {
        sulpapernum = i;
    }

	/**
     * @param i
     */
    public void setSulpapernum2(int i) {
        sulpapernum2 = i;
    }

    /**
     * @return
     */
    public int getCanceldays() {
        return canceldays;
    }
    /**
     * @param i
     */
    public void setCanceldays(int i) {
        canceldays = i;
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

	public String getSulStartDate1() {
		return sulstartdate1;
	}

	public String getSulEndDate1() {
		return sulenddate1;
	}

	public String getSulEndDate2() {
		return sulenddate2;
	}

	public String getSulStartDate2() {
		return sulstartdate2;
	}


	public void setIsunit(String isunit) {
		this.isunit = isunit;
	}

	public void setSulStartDate1(String sulstartdate1) {
		this.sulstartdate1 = sulstartdate1;
	}

	public void setSulEndDate1(String sulenddate1) {
		this.sulenddate1 = sulenddate1;
	}

	public void setSulStartDate2(String sulstartdate2) {
		this.sulstartdate2 = sulstartdate2;
	}

	public void setSulEndDate2(String sulenddate2) {
		this.sulenddate2 = sulenddate2;
	}


	public String getIsworkshop() {
		return isworkshop;
	}


	public void setIsworkshop(String isworkshop) {
		this.isworkshop = isworkshop;
	}


	public String getIsoutsourcing() {
		return isoutsourcing;
	}


	public void setIsoutsourcing(String isoutsourcing) {
		this.isoutsourcing = isoutsourcing;
	}

    public void setReviewdays(int i) {
		this.reviewdays = i;
    }
    public int  getReviewdays() {
		return reviewdays;
    }

    public void setReviewtype(String string) {
		reviewtype = string;
    }
    public String getReviewtype() {
		return reviewtype;
    }


	public void setCharger(String charger) {
		this.charger = charger;
	}


	public String getCharger() {
		return charger;
	}


	public void setAutoconfirm(String autoconfirm) {
		this.autoconfirm = autoconfirm;
	}


	public String getAutoconfirm() {
		return autoconfirm;
	}


}