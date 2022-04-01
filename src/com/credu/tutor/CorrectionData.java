//**********************************************************
//  1. 제      목: Correction DATA
//  2. 프로그램 명: CorrectionData.java
//  3. 개      요: 첨삭 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정경진 2005. 06. 29
//  7. 수      정:
//**********************************************************
package com.credu.tutor;
import com.credu.library.*;

public class CorrectionData
{
	private int seq;	 
    private String userid;
    private String comments;
    private String luserid;      
    private String ldate;
	    	
                	   
	public CorrectionData() {}

	public void   setSeq (int seq)	{ this.seq = seq; }
	public int    getSeq()	{	return seq;	}	
                   
	public void   setUserid (String userid)	{ this.userid = userid; }
	public String getUserid()	{	return userid;	}	                

	public void   setComments (String comments)	{ this.comments = comments; }
	public String getComments()	{	return comments;	}                   

	public void   setLuserid (String luserid)	{ this.luserid = luserid; }
	public String getLuserid()	{	return luserid;	} 
	
	public void   setLdate (String ldate)	{ this.ldate = ldate; }
	public String getLdate()	{	return ldate;	} 											          
									                   	
						
}
