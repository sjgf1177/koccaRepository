// **********************************************************
// 1. 제 목: 학습점수Data
// 2. 프로그램명: EduScoreData.java
// 3. 개 요:
// 4. 환 경: JDK 1.3
// 5. 버 젼: 0.1
// 6. 작 성: LeeSuMin 2003. 08. 26
// 7. 수 정:
//                 
// **********************************************************
package com.credu.beta;

import java.util.Hashtable;

public class BetaEduScoreData {

    private String datatype;
    private double score = 0;
    private double tstep = 0;
    private double htest = 0;
    private double mtest = 0;
    private double ftest = 0;
    private double report = 0;
    private double act = 0;
    private double etc1 = 0;
    private double etc2 = 0;
    private double avtstep = 0;
    private double avhtest = 0;
    private double avmtest = 0;
    private double avftest = 0;
    private double avreport = 0;
    private double avact = 0;
    private double avetc1 = 0;
    private double avetc2 = 0;
    private double gradscore = 0; // 이수기준 점수
    private double gradstep = 0; // 이수기준 진도율
    private double gradexam = 0; // 이수기준 평가점수
    private double wstep = 0; // 진도 가중치
    private double wmtest = 0; // 중가평가 가중치
    private double wftest = 0; // 총괄평가 가중치
    private double whtest = 0; // 형성평가 가중치
    private double wreport = 0; // 레포트 가중치
    private double wact = 0; // Activity 가중치
    private double wetc1 = 0; // 기타1 가중치
    private double wetc2 = 0; // 기타2 가중치
    private String isgraduated = "N"; // 수료여부
    private String edustart = ""; // 교육시작일
    private String eduend = ""; // 교육종료일

    public Hashtable<String, BetaEduScoreDataSub> eduScoreList = new Hashtable<String, BetaEduScoreDataSub>();

    public BetaEduScoreData() {
    };

    public void makeSub(String datatype, double weight, double score, double avscore, double gradscore) {
        int i = eduScoreList.size();
        BetaEduScoreDataSub ds = new BetaEduScoreDataSub(datatype, weight, score, avscore, gradscore);
        eduScoreList.put(String.valueOf(i), ds);
    }

    public void makeScoreList() {
        if (wstep > 0)
            makeSub("STEP", wstep, tstep, avtstep, gradstep);
        if (whtest > 0)
            makeSub("HTEST", whtest, htest, avhtest, gradexam);
        if (wmtest > 0)
            makeSub("MTEST", wmtest, mtest, avmtest, gradexam);
        if (wftest > 0)
            makeSub("FTEST", wftest, ftest, avftest, gradexam);
        if (wreport > 0)
            makeSub("REPORT", wreport, report, avreport, 0);
        if (wact > 0)
            makeSub("ACT", wact, act, avact, 0);
        if (wetc1 > 0)
            makeSub("ETC1", wetc1, etc1, avetc1, 0);
        if (wetc2 > 0)
            makeSub("ETC2", wetc2, etc2, avetc2, 0);
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
    public double getAvhtest() {
        return avhtest;
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
    public String getDatatype() {
        return datatype;
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
    public double getGradstep() {
        return gradstep;
    }

    /**
     * @return
     */
    public double getGradexam() {
        return gradexam;
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
    public String getEdustart() {
        return edustart;
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
    public double getHtest() {
        return htest;
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
    public double getgradscore() {
        return gradscore;
    }

    /**
     * @return
     */
    public double getReport() {
        return report;
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
    public double getTstep() {
        return tstep;
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
    public double getWhtest() {
        return whtest;
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
    public void setAct(double d) {
        act = d;
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
    public void setAvhtest(double d) {
        avhtest = d;
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
    public void setDatatype(String string) {
        datatype = string;
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
    public void setGradstep(double d) {
        gradstep = d;
    }

    /**
     * @param d
     */
    public void setGradexam(double d) {
        gradexam = d;
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
    public void setEdustart(String string) {
        edustart = string;
    }

    /**
     * @param string
     */
    public void setEduend(String string) {
        eduend = string;
    }

    /**
     * @param d
     */
    public void setHtest(double d) {
        htest = d;
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
    public void setgradscore(double d) {
        gradscore = d;
    }

    /**
     * @param d
     */
    public void setReport(double d) {
        report = d;
    }

    /**
     * @param d
     */
    public void setScore(double d) {
        score = d;
    }

    /**
     * @param d
     */
    public void setTstep(double d) {
        tstep = d;
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
    public void setWhtest(double d) {
        whtest = d;
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
    public double getGradscore() {
        return gradscore;
    }

    /**
     * @param d
     */
    public void setGradscore(double d) {
        gradscore = d;
    }

    /**
     * @return
     */
    public Hashtable<String, BetaEduScoreDataSub> getEduScoreList() {
        return eduScoreList;
    }

    /**
     * @param hashtable
     */
    public void setEduScoreList(Hashtable<String, BetaEduScoreDataSub> hashtable) {
        eduScoreList = hashtable;
    }

}
