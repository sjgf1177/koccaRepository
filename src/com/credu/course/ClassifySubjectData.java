//**********************************************************
//  1. 제      목: 과정분류코드 DATA
//  2. 프로그램명: ClassifySubjectData.java
//  3. 개      요: 과정분류코드 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: anonymous 2003. 6. 30
//  7. 수      정: 
//**********************************************************
package com.credu.course;

public class ClassifySubjectData
{
    private String subjclass;	    	
	private String upperclass;	    	
	private String middleclass;		
	private String lowerclass;         
	private String classname;		    
	private String luserid;	        
	private String ldate;	

	private String upperclassname;
	private String middleclassname;
	private String lowerclassname;

	private int upperrowspannum;
	private int middlerowspannum;

	
	public ClassifySubjectData() {}

	public void setSubjclass(String subjclass) { 
		this.subjclass = subjclass; 
	}
	
	public String getSubjclass() {	
		return subjclass;	
	}	

	public void setUpperclass(String upperclass) { 
		this.upperclass = upperclass; 
	}
	
	public String getUpperclass() {	
		return upperclass;	
	}	

	public void setMiddleclass(String middleclass) { 
		this.middleclass = middleclass; 
	}
	
	public String getMiddleclass() {	
		return middleclass;	
	}	

	public void setLowerclass(String lowerclass) { 
		this.lowerclass = lowerclass; 
	}
	
	public String getLowerclass() {	
		return lowerclass;	
	}	

	public void setClassname(String classname)	{ 
		this.classname = classname; 
	}
	
	public String getClassname() {	
		return classname;	
	}	

	public void  setLuserid(String luserid) { 
		this.luserid = luserid; 
	}
	
	public String getLuserid() {	
		return luserid;	
	}	

	public void setLdate(String ldate) { 
		this.ldate = ldate; 
	}
	
	public String getLdate() {	
		return ldate;	
	}	
	
	public boolean isUpperclass() {
		return (this.middleclass.equals("000") && this.lowerclass.equals("000"));		
	}
	
	public boolean isMiddleclass() {
		return (!this.middleclass.equals("000") && this.lowerclass.equals("000"));		
	}
	
	public boolean isLowerclass() {
		return (!this.middleclass.equals("000") && !this.lowerclass.equals("000"));		
	}

	/**
	 * @return
	 */
	public String getLowerclassname() {
		return lowerclassname;
	}

	/**
	 * @return
	 */
	public String getMiddleclassname() {
		return middleclassname;
	}

	/**
	 * @return
	 */
	public String getUpperclassname() {
		return upperclassname;
	}

	/**
	 * @param string
	 */
	public void setLowerclassname(String string) {
		if (isLowerclass()) {
			lowerclassname = string;
		} else { 
			lowerclassname = "";
		}
	}

	/**
	 * @param string
	 */
	public void setMiddleclassname(String string) {
		if (string == null)
			string = "";
		middleclassname = string;
	}

	/**
	 * @param string
	 */
	public void setUpperclassname(String string) {
		upperclassname = string;
	}

	/**
	 * @return
	 */
	public int getMiddlerowspannum() {
		return middlerowspannum;
	}

	/**
	 * @return
	 */
	public int getUpperrowspannum() {
		return upperrowspannum;
	}

	/**
	 * @param i
	 */
	public void setMiddlerowspannum(int i) {
		middlerowspannum = i;
	}

	/**
	 * @param i
	 */
	public void setUpperrowspannum(int i) {
		upperrowspannum = i;
	}

}
