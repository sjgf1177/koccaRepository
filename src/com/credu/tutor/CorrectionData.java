//**********************************************************
//  1. ��      ��: Correction DATA
//  2. ���α׷� ��: CorrectionData.java
//  3. ��      ��: ÷�� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2005. 06. 29
//  7. ��      ��:
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
