//**********************************************************
//  1. ��      ��: OFF SUBJECT LECTURE DATA
//  2. ���α׷���: OffSubjLectureData.java
//  3. ��      ��: ���հ��� ���� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 9. 16
//  7. ��      ��:
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
