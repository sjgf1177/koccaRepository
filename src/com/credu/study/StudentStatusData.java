//**********************************************************
//  1. ��      ��: STUDENT STATUS DATA
//  2. ���α׷���: StudentStatusData.java
//  3. ��      ��: �԰� ��Ȳ data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �迵�� 2003. 9. 1.
//  7. ��      ��:
//**********************************************************
package com.credu.study;

public class StudentStatusData {
    
	private String grseq;
	private String grseqnm;
    private String course;		
    private String cyear;		
    private String courseseq;
	private String coursenm;
	private String subj;
    private String year;	
	private String subjseq;
	private String subjseqgr;
	private String subjnm;	
	private String compnm;      /* �ҼӸ�  	*/
	private String jikwinm;     /* ������   */
	private String jikupnm;     /* ���޸�   */

	private String userid ;     /* ID       */
	private String cono;        /* ���     */
	private String name;   	    /* �̸�     */		
	private String edustart;    /* ���� �����Ͻ�? 	*/
	private String eduend;		/* ���� �����Ͻ�? 	*/
	private String tstep;	    /* ������   */
	private String avmtest;    /* ����ġ ���� �߰�������� 	*/
	private String avftest;		/* ����ġ ���� �������� 	*/
	private String mtest;      /* ����ġ ������ �߰�������� 	*/
	private String ftest;    /* ����ġ ������ �������� 	*/
	private String score;    /* ����ġ ������ �������� 	*/
	private String isgraduated;    /* ���Ῡ��	*/
	private String email;
	private String ismailing;
    private String isnewcourse;    	
    private String propstart;   /* ������û �����Ͻ� 	*/
    private String propend;     /* ������û �����Ͻ� 	*/
	private String isonoff;        
    private int studentlimit;    /* ���� 	*/
    private int procnt;          /* �ѽ�û�ο� 	*/
    private int proycnt;         /* ���������ο� 	*/
    private int stucnt;          /* �н����� �ο� 	*/
    private int comcnt;          /* �н��Ϸ� �ο� 	*/
    private int cancnt;          /* �����԰���� �ο�	*/
	private int rowspan;		
	private int dispnum;
	private int totalpagecount;
	private int rowcount;
	private String isbelongcourse;	//�ڽ��������� �ƴ�������
	private int subjcnt;			//���� ������ 
	

	public StudentStatusData() {};
	
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
	public String getName() {
		return name;
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
	public String getTstep() {
		return tstep;
	}

	/**
	 * @return
	 */
	public String getMtest() {
		return mtest;
	}

	/**
	 * @return
	 */
	public String getFtest() {
		return ftest;
	}

	/**
	 * @return
	 */
	public String getScore() {
		return score;
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
	public String getPropstart() {
		return propstart;
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
	public int getStudentlimit() {
		return studentlimit;
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
	public int getProcnt() {
		return procnt;
	}
	
	/**
	 * @return
    */
	public int getProycnt() {
		return proycnt;
	}

   /**
	 * @return
	 */
	public int getComcnt() {
		return comcnt;
	}

   /**
	 * @return
	 */
	public int getCancnt() {
		return cancnt;
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
	public void setEmail(String string) {
		email = string;
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
	 * @param string
	 */
	public void setName(String string) {
		name = string;
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
	 * @param string
	 */
	public void setTstep(String string) {
		tstep = string;
	}
	
	/**
	 * @param string
	 */
	public void setMtest(String string) {
		mtest = string;
	}
	
	/**
	 * @param string
	 */
	public void setFtest(String string) {
		ftest = string;
	}
	
	/**
	 * @param string
	 */
	public void setScore(String string) {
		score = string;
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
	public void setPropstart(String string) {
		propstart = string;
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
	public void setStudentlimit(int string) {
		studentlimit = string;
	}

    /**
	 * @param string
	 */
	public void setStucnt(int string) {
		stucnt = string;
	}
	
	/**
	 * @param string
	 */
	public void setProcnt(int string) {
		procnt = string;
	}
	
	/**
	 * @param string
	 */
	 public void setProycnt(int string) {
		proycnt = string;
	}
	
	/**
	 * @param string
	 */
	public void setComcnt(int string) {
		comcnt = string;
	}
	
	/**
	 * @param string
	 */
	public void setCancnt(int string) {
		cancnt = string;
	}
	
	public void   setDispnum (int dispnum)	{ this.dispnum = dispnum; }
	public int    getDispnum()	{	return dispnum;	}	
	
	public void setTotalPageCount(int totalpagecount)	{ this.totalpagecount = totalpagecount; }
	public int    getTotalPageCount()	{	return totalpagecount;	}	
	
	public void setRowCount(int rowcount)	{ this.rowcount = rowcount; }
	public int    getRowCount()	{	return rowcount;	}	
	
	/**
	 * @param string
	 */
	public void setIsonoff(String string) {
		isonoff = string;
	}
	
	/**
	 * @return
	 */
	public String getIsonoff() {
		return isonoff;
	}

	public String getIsbelongcourse() {
		return isbelongcourse;
	}

	public void setIsbelongcourse(String isbelongcourse) {
		this.isbelongcourse = isbelongcourse;
	}

	public int getSubjcnt() {
		return subjcnt;
	}

	public void setSubjcnt(int subjcnt) {
		this.subjcnt = subjcnt;
	}
	
}	