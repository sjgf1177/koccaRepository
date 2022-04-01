//**********************************************************
//  1. 제      목: OFF SUBJECT LECTURE DATA
//  2. 프로그램명: OffSubjLectureData.java
//  3. 개      요: 집합과정 강좌 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 9. 16
//  7. 수      정:
//**********************************************************
package com.credu.course;
import com.credu.library.*;

public class OffSubjLectureData
{

    private String subj;		
    private String year;		
    private String subjseq;		
    private String lecture;		
    private String lectdate;	
    private String lectsttime;	
    private String lecttime;	
    private String sdesc;   	    
    private String tutorid;       
    private String tutorname;    
    private String lectscore;	    
    private String lectlevel;	        
    
	public OffSubjLectureData() {}                   

	public void   setSubj (String subj)	{ this.subj = subj; }
	public String getSubj()	{	return subj;	} 	
	
	public void   setYear (String year)	{ this.year = year; }
	public String getYear()	{	return year;	} 		
	
	public void   setSubjseq (String subjseq)	{ this.subjseq = subjseq; }
	public String getSubjseq()	{	return subjseq;	} 		
	
	public void   setLecture (String lecture)	{ this.lecture = lecture; }
	public String getLecture()	{	return lecture;	}
	
	public void   setLectdate (String lectdate)	{ this.lectdate = lectdate; }
	public String getLectdate()	{	return lectdate;	} 	
	
	public void   setLectsttime (String lectsttime)	{ this.lectsttime = lectsttime; }
	public String getLectsttime()	{	return lectsttime;	} 	
	
	public void   setLecttime (String lecttime)	{ this.lecttime = lecttime; }
	public String getLecttime()	{	return lecttime;	} 		

	public void   setSdesc (String sdesc)	{ this.sdesc = sdesc; }
	public String getSdesc()	{	return sdesc;	} 	
	
	public void   setTutorid (String tutorid)	{ this.tutorid = tutorid; }
	public String getTutorid()	{	return tutorid;	} 			
	
	public void   setTutorname (String tutorname)	{ this.tutorname = tutorname; }
	public String getTutorname()	{	return tutorname;	} 		
	
	public void   setLectscore (String lectscore)	{ this.lectscore = lectscore; }
	public String getLectscore()	{	return lectscore;	} 	
			
	public void   setLectlevel (String lectlevel)	{ this.lectlevel = lectlevel; }
	public String getLectlevel()	{	return lectlevel;	} 										          
}
