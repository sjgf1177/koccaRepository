//**********************************************************
//  1. 제      목: SUBJECT INFORMATION USER DATA
//  2. 프로그램명: ProposeCourseData.java
//  3. 개      요: 과정안내 사용자 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 8. 18
//  7. 수      정:
//**********************************************************
package com.credu.propose;
import com.credu.library.*;

public class ProposeCourseData
{
    private String upperclass;
	private String upperclassnm;
    private String isonoff;
    private String course;
    private String cyear;
    private String courseseq;
    private String coursenm;
    private String subj;
    private String year;
    private String subjseq;
    private String subjnm;
    private String preurl;
    private String proposetype;
    private String classnm;
    private String isnewcourse;
    private String proposeyn;
    private String subjtarget;
    private String sdesc;
    private String propstart;
    private String propend;
    private String edustart;
    private String eduend;
    private String place;
    private String tutor;
    private String muserid;
    private String isgoyong;
    private String lesson;
    private String lecture;
    private String lecturedate;
    private String isnewupperclass;
    private String proposestart;
    private String proposeend;
    private String edumans;
    private String intro;
    private String explain;
    private String creduman;
    private String env;
	private String owner;	//소유주코드
	private String ownerman;//소유주명
	private String ownertel;//소유주전화번호
	private String isessential;
	private String subjseqgr;

    private int studentlimit;
    private int stucnt;
    private int rowspan;
    private int biyong;
    private int wstep;
    private int wmtest;
    private int wftest;
    private int wreport;
    private int wact;
    private int wetc1;
    private int wetc2;
    private int stepcnt;
    private int testcnt;
    private int reportcnt;
    private int actcnt;
    private int gradscore;
    private int gradstep;
    private int wgradstep;
	private int    edudays;
	private int    edutimes;
	private String edutype;
	private int ratewbt;
	private int ratevod;
	private int score;

    public ProposeCourseData() {}


	/**
	 * @return
	 */
	public int getRatewbt() {
		return ratewbt;
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
	public int getBiyong() {
		return biyong;
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
	public String getOwnerman() {
		return ownerman;
	}


	/**
	 * @return
	 */
	public String getOwnertel() {
		return ownertel;
	}

	/**
	 * @return
	 */
	public String getClassnm() {
		return classnm;
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
	public String getEdustart() {
		return edustart;
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
	public String getIsnewcourse() {
		return isnewcourse;
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
	public String getLecture() {
		return lecture;
	}

	/**
	 * @return
	 */
	public String getLecturedate() {
		return lecturedate;
	}

	/**
	 * @return
	 */
	public String getLesson() {
		return lesson;
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
	public String getPlace() {
		return place;
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
	public String getPropend() {
		return propend;
	}

	/**
	 * @return
	 */
	public String getProposetype() {
		return proposetype;
	}

	/**
	 * @return
	 */
	public String getProposeyn() {
		return proposeyn;
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
	public int getRowspan() {
		return rowspan;
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
	public int getStucnt() {
		return stucnt;
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
	public String getSubjtarget() {
		return subjtarget;
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
	public int getWact() {
		return wact;
	}

	/**
	 * @return
	 */
	public int getWftest() {
		return wftest;
	}

	/**
	 * @return
	 */
	public int getWmtest() {
		return wmtest;
	}

	/**
	 * @return
	 */
	public int getWreport() {
		return wreport;
	}

	/**
	 * @return
	 */
	public int getWstep() {
		return wstep;
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
	public String getEnv() {
		return env;
	}

	/**
	 * @param String
	 */
	public void setEnv(String string) {
		env = string ;
	}

	/**
	 * @return
	 */
	public void setRatewbt(int i) {
		ratewbt = i;
	}


	/**
	 * @return
	 */
	public void setRatevod(int i) {
		ratevod = i;
	}


	/**
	 * @param i
	 */
	public void setBiyong(int i) {
		biyong = i;
	}

	/**
	 * @param string
	 */
	public void setOwner(String string) {
		owner = string;
	}

	/**
	 * @param string
	 */
	public void setOwnerman(String string) {
		ownerman = string;
	}

	/**
	 * @param string
	 */
	public void setOwnertel(String string) {
		ownertel = string;
	}


	/**
	 * @param string
	 */
	public void setClassnm(String string) {
		classnm = string;
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
	 * @param string
	 */
	public void setEdustart(String string) {
		edustart = string;
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
	public void setIsnewcourse(String string) {
		isnewcourse = string;
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
	public void setLecture(String string) {
		lecture = string;
	}

	/**
	 * @param string
	 */
	public void setLecturedate(String string) {
		lecturedate = string;
	}

	/**
	 * @param string
	 */
	public void setLesson(String string) {
		lesson = string;
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
	public void setPlace(String string) {
		place = string;
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
	public void setPropend(String string) {
		propend = string;
	}

	/**
	 * @param string
	 */
	public void setProposetype(String string) {
		proposetype = string;
	}

	/**
	 * @param string
	 */
	public void setProposeyn(String string) {
		proposeyn = string;
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
	public void setRowspan(int i) {
		rowspan = i;
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
	public void setStucnt(int i) {
		stucnt = i;
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
	 * @param string
	 */
	public void setSubjtarget(String string) {
		subjtarget = string;
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
	 * @param i
	 */
	public void setWact(int i) {
		wact = i;
	}

	/**
	 * @param i
	 */
	public void setWftest(int i) {
		wftest = i;
	}

	/**
	 * @param i
	 */
	public void setWmtest(int i) {
		wmtest = i;
	}

	/**
	 * @param i
	 */
	public void setWreport(int i) {
		wreport = i;
	}

	/**
	 * @param i
	 */
	public void setWstep(int i) {
		wstep = i;
	}

	/**
	 * @param string
	 */
	public void setYear(String string) {
		year = string;
	}

    public void   setIsnewupperclass(String isnewupperclass){ this.isnewupperclass = isnewupperclass;   }
    public String getIsnewupperclass()                      { return isnewupperclass;                   }

    public void   setProposestart(String proposestart)          { this.proposestart = proposestart;             }
    public String getProposestart()                           { return proposestart;                        }

    public void   setProposeend(String proposeend)          { this.proposeend = proposeend;             }
    public String getProposeend()                           { return proposeend;                        }

	public void   setWetc1 (int wetc1)	{ this.wetc1 = wetc1; }
	public int    getWetc1()	{	return wetc1;	}

	public void   setWetc2 (int wetc2)	{ this.wetc2 = wetc2; }
	public int    getWetc2()	{	return wetc2;	}

	public void   setStepcnt (int stepcnt)	{ this.stepcnt = stepcnt; }
	public int    getStepcnt()	{	return stepcnt;	}

	public void   setTestcnt (int testcnt)	{ this.testcnt = testcnt; }
	public int    getTestcnt()	{	return testcnt;	}

	public void   setReportcnt (int reportcnt)	{ this.reportcnt = reportcnt; }
	public int    getReportcnt()	{	return reportcnt;	}

	public void   setActcnt (int actcnt)	{ this.actcnt = actcnt; }
	public int    getActcnt()	{	return actcnt;	}

	public void   setGradscore (int gradscore)	{ this.gradscore = gradscore; }
	public int    getGradscore()	{	return gradscore;	}

	public void   setGradstep (int gradstep)	{ this.gradstep = gradstep; }
	public int    getGradstep()	{	return gradstep;	}

	public void   setWgradstep (int wgradstep)	{ this.wgradstep = wgradstep; }
	public int    getWgradstep()	{	return wgradstep;	}
	/**
	 * @return
	 */
	public String getUpperclassnm() {
		return upperclassnm;
	}

	/**
	 * @param string
	 */
	public void setUpperclassnm(String string) {
		upperclassnm = string;
	}

    public void   setEdumans(String edumans)          { this.edumans = edumans;             }
    public String getEdumans()                        { return edumans;                     }

    public void   setIntro(String intro)              { this.intro = intro;                 }
    public String getIntro()                          { return intro;                       }

    public void   setExplain(String explain)          { this.explain = explain;             }
    public String getExplain()                        { return explain;                     }

    public void setEdudays(int edudays)         { this.edudays = edudays;     }
    public int  getEdudays()                   { return edudays;              }

    public void setEdutimes(int edutimes)       { this.edutimes = edutimes;     }
    public int  getEdutimes()                   { return edutimes;              }

    public void   setEdutype(String edutype)    { this.edutype = edutype;       }
    public String getEdutype()                  { return edutype;               }

    public void   setCreduman(String creduman)   { this.creduman = creduman;      }
    public String getCreduman()                  { return creduman;               }

    public void   setIsessential(String isessential) { this.isessential = isessential;    }
    public String getIsessential()                   { return isessential;               }

    public void setScore(int score)          { this.score = score;     }
    public int  getScore()                   { return score;           }



}
