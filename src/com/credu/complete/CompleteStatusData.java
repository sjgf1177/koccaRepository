//**********************************************************
//  1. 제      목: COMPLETE STATUS DATA
//  2. 프로그램명: CompleteStatusData.java
//  3. 개      요: 수료 현황 관리자 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 8. 21
//  7. 수      정:
//**********************************************************
package com.credu.complete;
import com.credu.library.*;

public class CompleteStatusData
{       
    private String grseq;
    private String course;
    private String cyear;
    private String courseseq;
    private String coursenm;
    private String subj;
    private String year;
    private String subjseq;
    private String subjseqgr;
    private String subjnm;
    private String isonoff;
    private String compnm; 
    private String companynm;
    private String jikwinm;
    private String jikupnm;
    private String userid ;
    private String cono;   
    private String name; 
    private String edustart;   
    private String eduend;  
    private String isgraduated;
    private String email;
    private String ismailing;
    private String isnewcourse;    
    private String place;    
    private String resno;
    private String serno;
	private String membergubun;
        
    private int tstep;
    private int avtstep;
    private int mtest;
    private int ftest;
	private int htest;  
    private int report;
    private int act;
    private int etc1;
    private int score;
    private int educnt;
    private int gradcnt1;
    private int gradcnt2;
    private int rowspan;
    private int dispnum;
    private int totalpagecount;
    private int rowcount;    
    private int edudays;
    private int edutimes;
    private int goyongprice;
 
    public CompleteStatusData() {};
    
    /**
     * @param string
     */
    public void setPlace(String string) {
        place = string;
    }
        
    /**
     * @return
     */
    public int getAct() {
        return act;
    }

    /**
     * @return
     */
    public int getAvtstep() {
        return avtstep;
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
    public int getEducnt() {
        return educnt;
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
    public String getEmail() {
        return email;
    }

    /**
     * @return
     */
    public int getEtc1() {
        return etc1;
    }

    /**
     * @return
     */
    public int getFtest() {
        return ftest;
    }
	
    /**
     * @return
     */
    public int getHtest() {
        return htest;
    }

    /**
     * @return
     */
    public int getGradcnt1() {
        return gradcnt1;
    }

    /**
     * @return
     */
    public int getGradcnt2() {
        return gradcnt2;
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
    public String getIsgraduated() {
        return isgraduated;
    }

    /**
     * @return
     */
    public String getIsmailing() {
        return ismailing;
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
    public String getJikwinm() {
        return jikwinm;
    }

    /**
     * @return
     */
    public String getJikupnm() {
        return jikupnm;
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
    public int getMtest() {
        return mtest;
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
    public int getReport() {
        return report;
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
    public int getScore() {
        return score;
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
    public int getTstep() {
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
    public String getMembergubun() {
		return membergubun;
    }

    /**
     * @param i
     */
    public void setAct(int i) {
        act = i;
    }

    /**
     * @param i
     */
    public void setAvtstep(int i) {
        avtstep = i;
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
     * @param i
     */
    public void setEducnt(int i) {
        educnt = i;
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
    public void setMembergubun(String string) {
		membergubun = string;
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
    public void setEmail(String string) {
        email = string;
    }
    
    /**
     * @param i
     */
    public void setEtc1(int i) {
        etc1 = i;
    }

    /**
     * @param i
     */
    public void setFtest(int i) {
        ftest = i;
    }
	
    /**
     * @param i
     */
    public void setHtest(int i) {
        htest = i;
    }

    /**
     * @param i
     */
    public void setGradcnt1(int i) {
        gradcnt1 = i;
    }

    /**
     * @param i
     */
    public void setGradcnt2(int i) {
        gradcnt2 = i;
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
    public void setIsgraduated(String string) {
        isgraduated = string;
    }

    /**
     * @param string
     */
    public void setIsmailing(String string) {
        ismailing = string;
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
    public void setJikwinm(String string) {
        jikwinm = string;
    }

    /**
     * @param string
     */
    public void setJikupnm(String string) {
        jikupnm = string;
    }

    /**
     * @param i
     */
    public void setMtest(int i) {
        mtest = i;
    }

    /**
     * @param string
     */
    public void setName(String string) {
        name = string;
    }

    /**
     * @param i
     */
    public void setReport(int i) {
        report = i;
    }

    /**
     * @param i
     */
    public void setRowspan(int i) {
        rowspan = i;
    }

    /**
     * @param i
     */
    public void setScore(int i) {
        score = i;
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
    public void setTstep(int i) {
        tstep = i;
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
    
    public void   setDispnum (int dispnum)  { this.dispnum = dispnum; }
    public int    getDispnum()  {   return dispnum; }   
    
    public void setTotalPageCount(int totalpagecount)   { this.totalpagecount = totalpagecount; }
    public int    getTotalPageCount()   {   return totalpagecount;  }   
    
    public void setRowCount(int rowcount)   { this.rowcount = rowcount; }
    public int    getRowCount() {   return rowcount;    }
    
    public void setResno(String resno)  { this.resno = resno; }
    public String getResno()    {   return resno;   }   
    
    public void setSerno(String serno)  { this.serno = serno; }
    public String getSerno()    {   return serno;   }
    
    public void setEdudays(int edudays) { this.edudays = edudays; }
    public int  getEdudays()    {   return edudays; }
        
    public void setEdutimes(int edutimes)   { this.edutimes = edutimes; }
    public int  getEdutimes()   {   return edutimes;    }

    public void setGoyongprice(int goyongprice) { this.goyongprice = goyongprice; }
    public int  getGoyongprice()    {   return goyongprice; }

    public void setCompanynm(String companynm)  { this.companynm = companynm; }
    public String getCompanynm()                { return companynm;   }
}
