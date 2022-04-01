//**********************************************************
//  1. 제      목: 과정공지화면용 DATA
//  2. 프로그램명: SubjGongData.java
//  3. 개      요: 과정공지화면용 DATA
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 23
//  7. 수      정:
//**********************************************************

package com.credu.course;

import java.util.*;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SubjGongData {

    private String  grcode      ;
    private String  grcodenm    ;
    private String  grseq       ;
    private String  gyear       ;
    private String  grseqnm     ;
    private String  subj        ;
    private String  subjnm      ;
    private String  year        ;
	private String  subjseq     ;
	private String  subjseqgr   ;
    private String  course      ;
    private String  cyear       ;
    private String  courseseq   ;
    private String  coursenm    ;

    private String  edustart    ;
    private String  eduend      ;
    private String  luserid     ;
    private String  ldate       ;

    private String  isonoff     ;
    private String  subjtypenm  ;
        
	private String  isnewcourse ;
	private int     rowspan     ;

	private int     seq         ;
	private String  types       ;
	private String  addate      ;
	private String  title       ;
	private String  userid      ;
	private String  adcontent   ;
	private String  upfile      ;

	private String  typesnm  	;
	private String  typescnt 	;
	private String  museridnm	;
	private String  musertel	;
	private String  comp		;
    private int     studentlimit;
    private int     framecnt    ;
    private int     edutimes    ;
    private int     upfilecnt   ;
	private String  name        ;
    private int     cnt         ;

	public  SubjGongData() {}

	public String getSubjtypenm(){
		if (isonoff==null || isonoff.equals(""))        return "aaa";
		if (isonoff.equals("ON"))       return "사이버";
		else                                            return "집합";
	}


	/**
	 * @return
	 */
	public String getAdcontent() {
		return adcontent;
	}

	/**
	 * @return
	 */
	public String getAddate() {
		return addate;
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
	public String getGyear() {
		return gyear;
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
	public String getUserid() {
		return userid;
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
	public int getSeq() {
		return seq;
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
	public String getTitle() {
		return title;
	}

	/**
	 * @return
	 */
	public String getTypes() {
		return types;
	}

	/**
	 * @return
	 */
	public String getTypescnt() {
		return typescnt;
	}

	/**
	 * @return
	 */
	public String getTypesnm() {
		return typesnm;
	}

	/**
	 * @return
	 */
	public String getUpfile() {
		return upfile;
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
    public String getMuseridNm() {
        return museridnm;
    }
    
    /**
     * @return
     */
    public String getMuserTel() {
        return musertel;
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
    public int getStudentLimit() {
        return studentlimit;
    }
    
    /**
     * @return
     */
    public int getFrameCnt() {
        return framecnt;
    }
    
    /**
     * @return
     */
    public int getEduTimes() {
        return edutimes;
    }
    
    /**
     * @return
     */
    public int getUpfilecnt() {
        return upfilecnt;
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
    public int getCnt() {
        return cnt;
    }

    /**
     * @return
     */
    public void setStudentLimit(int i) {
        studentlimit = i;
    }
    
    /**
     * @return
     */
    public void setFrameCnt(int i) {
        framecnt = i;
    }
    
    /**
     * @return
     */
    public void setEduTimes(int i) {
        edutimes = i;
    }
    
    /**
     * @return
     */
    public void setUpfilecnt(int i) {
        upfilecnt = i;
    }

    /**
     * @param string
     */
    public void setName(String string) {
        name = string;
    }

    /**
     * @return
     */
    public void setCnt(int i) {
        cnt = i;
    }

    /**
     * @param string
     */
    public void setMuseridNm(String string) {
        museridnm = string;
    }
    
    /**
     * @param string
     */
    public void setMuserTel(String string) {
        musertel = string;
    }
    
	/**
	 * @param string
	 */
	public void setAdcontent(String string) {
		adcontent = string;
	}

	/**
	 * @param string
	 */
	public void setAddate(String string) {
		addate = string;
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
	public void setGyear(String string) {
		gyear = string;
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
	public void setUserid(String string) {
		userid = string;
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
	public void setSeq(int i) {
		seq = i;
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
	public void setSubjtypenm(String string) {
		subjtypenm = string;
	}

	/**
	 * @param string
	 */
	public void setTitle(String string) {
		title = string;
	}

	/**
	 * @param string
	 */
	public void setTypes(String string) {
		types = string;
	}

	/**
	 * @param i
	 */
	public void setTypescnt(String string) {
		typescnt = string;
	}

	/**
	 * @param string
	 */
	public void setTypesnm(String string) {
		typesnm = string;
	}

	/**
	 * @param string
	 */
	public void setUpfile(String string) {
		upfile = string;
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
	public void setComp(String string) {
		comp = string;
	}

}
