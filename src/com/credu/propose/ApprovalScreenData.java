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
package com.credu.propose;

import java.util.*;

public class ApprovalScreenData {
    private String  grcode      ;
    private String  grcodenm    ;
    private String  grseq       ;
    private String  grseqnm     ;
    private String  gyear       ;
    private String  scsubj      ;
    private String  scsubjnm    ;
    private String  scyear      ;
    private String  scsubjseq   ;
    private String  subjseqgr   ;

    private String  propstart   ;
    private String  propend     ;
    private String  edustart    ;
    private String  eduend      ;
    private String  luserid     ;
    private String  ldate       ;
    private int     point       ;
    private int     biyong      ;
    private int     edulimit    ;
    private int     rowspan =1  ;     //Rowspan

    private String  isonoff     ;
    private String  subjtypenm  ;

    private String  userid;
    private String  name;
    private String  comp;
    private String  compnm;
    private String  jikwinm;
    private String  cono;
    private String  comptel;
    private String  jik             ;
    private String  jiknm           ;
    private String  appdate         ;
    private String  isdinsert       ;
    private String  isb2c           ;
    private String  ischkfirst      ;
    private String  isproposeapproval;
    private String  useproposeapproval;
    private String  isproposeapprovaltxt;
    private String  useproposeapprovaltxt;
    private String  chkfirst        ;
    private String  chkfinal        ;
    private String  chkfirstTxt     ;
    private String  chkfinalTxt     ;
    private String  proptxt         ;
    private String  chkfirsttxt     ;
    private String  chkfirstmail    ;
    private String  chkfirstuserid  ;
    private String  chkfirstdate    ;
    private String  billstat        ;
    private String  ordcode         ;
    private String  cancelkind      ;
    private String  rejectkind      ;
    private String  rejectedreason  ;
	private String  b_cnt           ;
	private String  isclosed        ;

	private String  mastercd        ;
    private String  masternm        ;
    private String  isedutarget     ;
    private String  companynm       ;	

    public  ApprovalScreenData() {}


    /**
     * @return
     */
    public String getAppdate() {
        return appdate;
    }

    /**
     * @return
     */
    public String getBillstat() {
        return billstat;
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
    public String getCancelkind() {
        return cancelkind;
    }
    
    /**
     * @return
     */
    public String getRejectkind() {
        return rejectkind;
    }
    
    /**
     * @return
     */
    public String getRejectedreason() {
        return rejectedreason;
    }

    /**
     * @return
     */
    public String getChkfinal() {
        return chkfinal;
    }

    /**
     * @return
     */
    public String getChkfirst() {
        return chkfirst;
    }
    
    /**
     * @return
     */
    public String getIsproposeapproval() {
        return isproposeapproval;
    }
    
    /**
     * @return
     */
    public String getIsproposeapprovalTxt() {
        return isproposeapprovaltxt;
    }    
    
    /**
     * @return
     */
    public String getUseproposeapprovalTxt() {
        return useproposeapprovaltxt;
    }

    /**
     * @return
     */
    public String getChkfirstdate() {
        return chkfirstdate;
    }

    /**
     * @return
     */
    public String getChkfirstmail() {
        return chkfirstmail;
    }

    /**
     * @return
     */
    public String getChkfirsttxt() {
        return chkfirsttxt;
    }

    /**
     * @return
     */
    public String getChkfirstuserid() {
        return chkfirstuserid;
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
    public String getCompnm() {
        return compnm;
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
    public String getCono() {
        return cono;
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
    public String getGrcode() {
        return grcode;
    }

    /**
     * @return
     */
    public String getGrcodenm() {
        return grcodenm;
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
    public String getIsclosed() {
        return isclosed;
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
    public String getIsb2c() {
        return isb2c;
    }

    /**
     * @return
     */
    public String getIschkfirst() {
        return ischkfirst;
    }

    /**
     * @return
     */
    public String getIsdinsert() {
        return isdinsert;
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
    public String getJik() {
        return jik;
    }

    /**
     * @return
     */
    public String getJiknm() {
        return jiknm;
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
    public String getName() {
        return name;
    }

    /**
     * @return
     */
    public String getOrdcode() {
        return ordcode;
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
    public String getProptxt() {
        return proptxt;
    }

    /**
     * @return
     */
    public String getScsubj() {
        return scsubj;
    }

    /**
     * @return
     */
    public String getScsubjnm() {
        return scsubjnm;
    }

    /**
     * @return
     */
    public String getScsubjseq() {
        return scsubjseq;
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
    public String getScyear() {
        return scyear;
    }

    /**
     * @return
     */
    public String getSubjtypenm() {
        return subjtypenm;
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
    public String getB_cnt() {
        return b_cnt;
    }
    
    /**
     * @return
     */
    public String getMastercd() {
        return mastercd;
    }
    
    /**
     * @return
     */
    public String getMasternm() {
        return masternm;
    }
    
    /**
     * @return
     */
    public String getIsedutarget() {
        return isedutarget;
    }

    /**
     * @param string
     */
    public void setAppdate(String string) {
        appdate = string;
    }

    /**
     * @param string
     */
    public void setBillstat(String string) {
        billstat = string;
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
    public void setCancelkind(String string) {
        cancelkind = string;
    }
    
    /**
     * @param string
     */
    public void setRejectkind(String string) {
        rejectkind = string;
    }
    
    /**
     * @param string
     */
    public void setRejectedreason(String string) {
        rejectedreason = string;
    }


    /**
     * @param string
     */
    public void setChkfinal(String string) {
        chkfinal = string;

        if (chkfinal.equals("Y"))   setChkfinalTxt("승인");
        else if (chkfinal.equals("N"))  setChkfinalTxt("반려");
        else    setChkfinalTxt("미처리");
    }
    


    /**
     * @param string
     */
    public void setIsproposeapproval(String string) {
        isproposeapproval = string;

        if (isproposeapproval.equals("Y"))   setIsproposeapprovalTxt("승인");
        else if (isproposeapproval.equals("N"))  setIsproposeapprovalTxt("반려");
        else if (isproposeapproval.equals("L"))  setIsproposeapprovalTxt("-");
        else if (isproposeapproval.equals("B"))  setIsproposeapprovalTxt("상신중");
    }
    
    /**
     * @param string
     */
    public void setIsproposeapprovalTxt(String string) {
        isproposeapprovaltxt = string;
    }
    
    /**
     * @param string
     */
    public void setUseproposeapproval(String string) {
        useproposeapproval = string;

        if (useproposeapproval.equals("Y"))   setUseproposeapprovalTxt("필요");
        else if (useproposeapproval.equals("N"))  setUseproposeapprovalTxt("불필요");
    }
    
    /**
     * @param string
     */
    public void setUseproposeapprovalTxt(String string) {
        useproposeapprovaltxt = string;
    }



    /**
     * @param string
     */
    public void setChkfirst(String string) {
        chkfirst = string;
        if (chkfirst.equals("Y"))   setChkfirstTxt("승인");
        else if (chkfirst.equals("N"))  setChkfirstTxt("반려");
        else    setChkfirstTxt("미처리");

    }

    /**
     * @param string
     */
    public void setChkfirstdate(String string) {
        chkfirstdate = string;
    }

    /**
     * @param string
     */
    public void setChkfirstmail(String string) {
        chkfirstmail = string;
    }

    /**
     * @param string
     */
    public void setChkfirsttxt(String string) {
        chkfirsttxt = string;
    }

    /**
     * @param string
     */
    public void setChkfirstuserid(String string) {
        chkfirstuserid = string;
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
     * @param string
     */
    public void setGrcode(String string) {
        grcode = string;
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
    public void setIsclosed(String string) {
        isclosed = string;
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
    public void setIsb2c(String string) {
        isb2c = string;
    }

    /**
     * @param string
     */
    public void setIschkfirst(String string) {
        ischkfirst = string;
    }

    /**
     * @param string
     */
    public void setIsdinsert(String string) {
        isdinsert = string;
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
    public void setJik(String string) {
        jik = string;
    }

    /**
     * @param string
     */
    public void setJiknm(String string) {
        jiknm = string;
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
    public void setName(String string) {
        name = string;
    }

    /**
     * @param string
     */
    public void setOrdcode(String string) {
        ordcode = string;
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
     * @param string
     */
    public void setProptxt(String string) {
        proptxt = string;
    }

    /**
     * @param string
     */
    public void setScsubj(String string) {
        scsubj = string;
    }

    /**
     * @param string
     */
    public void setScsubjnm(String string) {
        scsubjnm = string;
    }

    /**
     * @param string
     */
    public void setScsubjseq(String string) {
        scsubjseq = string;
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
    public void setScyear(String string) {
        scyear = string;
    }

    /**
     * @param string
     */
    public void setSubjtypenm(String string) {
        subjtypenm = string;
    }

    /**
     * @param string
     */
    public void setUserid(String string) {
        userid = string;
    }

    /**
     * @return
     */
    public String getComptel() {
        return comptel;
    }

    /**
     * @param string
     */
    public void setComptel(String string) {
        comptel = string;
    }

    /**
     * @return
     */
    public int getRowspan() {
        return rowspan;
    }

    /**
     * @param i
     */
    public void setRowspan(int i) {
        rowspan = i;
    }

    /**
     * @return
     */
    public String getChkfinalTxt() {
        return chkfinalTxt;
    }

    /**
     * @return
     */
    public String getChkfirstTxt() {
        return chkfirstTxt;
    }

    /**
     * @param string
     */
    public void setChkfinalTxt(String string) {
        chkfinalTxt = string;
    }

    /**
     * @param string
     */
    public void setChkfirstTxt(String string) {
        chkfirstTxt = string;
    }

    /**
     * @param string
     */
    public void setB_cnt(String string) {
        b_cnt = string;
    }
    
    /**
     * @param string
     */
    public void setMastercd(String string) {
        mastercd = string;
    }
    
    /**
     * @param string
     */
    public void setMasternm(String string) {
        masternm = string;
    }
    
    /**
     * @param string
     */
    public void setIsedutarget(String string) {
        isedutarget = string;
    }


    /**
     * @return
     */
    public String getCompanynm() {
        return companynm;
    }

    /**
     * @param string
     */
    public void setCompanynm(String string) {
        companynm = string;
    }

}
