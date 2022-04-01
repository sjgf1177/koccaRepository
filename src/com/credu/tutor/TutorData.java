//**********************************************************
//  1. 제      목: TUTOR DATA
//  2. 프로그램명: TutorData.java
//  3. 개      요: 강사 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 14
//  7. 수      정:
//**********************************************************
package com.credu.tutor;

public class TutorData
{
    private String userid;
    private String name;
    private String sex;
    private String post1;
    private String post2;
    private String add1;
    private String add2; 
    private String phone;
    private String handphone;
    private String hometel;
    private String fax;
    private String email;
    private String comp;
    private String compcd;
    private String dept;		
    private String jik;			
    private String academic;	
    private String major;		
    private String isadd;		
    private String iscyber;		
    private String isgubun;		
    private String isgubuntype;	
    private String isstatus;	
    private String istutor;
    private String license;
    private String career;
    private String book;
    private String grcode;
    private String grcodenm;
    private String professional;
    private String charge;
    private String isinfo;
    private String etc;			
    private String indate;		
    private String luserid;      
    private String ldate;
    private String subj;
    private String subjnm;
    private String photo;
    private String fmon;
    private String tmon;
    private String year;		
    private String subjseq;		
    private String lectdate;	
    private String lectsttime;	
    private String lecttime;	
    private String sdesc;   	    
    private String tutorid;       
    private String tutorname;        
    private String lectlevel;
    private String tel_line;
    private String edustart;
    private String eduend;

	private int careeryear;	   
	private int dispnum;
	private int totalpagecount;	
	private int rowcount;	
	private int managerchk;
	private int lecture;	
    private int lectscore;		    	
                	   
	public TutorData() {}
                   
	public void   setUserid (String userid)	{ this.userid = userid; }
	public String getUserid()	{	return userid;	}	
                   
	public void   setName (String name)	{ this.name = name; }
	public String getName()	{	return name;	}                   

	public void   setSex (String sex)	{ this.sex = sex; }
	public String getSex()	{	return sex;	}                    

	public void   setPost1 (String post1)	{ this.post1 = post1; }
	public String getPost1()	{	return post1;	}   
	
	public void   setPost2 (String post2)	{ this.post2 = post2; }
	public String getPost2()	{	return post2;	}   	                   

	public void   setAdd1 (String add1)	{ this.add1 = add1; }
	public String getAdd1()	{	return add1;	}   	                   

	public void   setAdd2 (String add2)	{ this.add2 = add2; }
	public String getAdd2()	{	return add2;	}   	                   

	public void   setPhone (String phone)	{ this.phone = phone; }
	public String getPhone()	{	return phone;	}   	                   

	public void   setHandphone (String handphone)	{ this.handphone = handphone; }
	public String getHandphone()	{	return handphone;	}   	                   

	public void   setHometel (String hometel)	{ this.hometel = hometel; }
	public String getHometel()	{	return hometel;	}   	                   

	public void   setFax (String fax)	{ this.fax = fax; }
	public String getFax()	{	return fax;	}   	                   

	public void   setEmail (String email)	{ this.email = email; }
	public String getEmail()	{	return email;	}   	                   

	public void   setComp (String comp)	{ this.comp = comp; }
	public String getComp()	{	return comp;	}   	                   

	public void   setCompcd (String compcd)	{ this.compcd = compcd; }
	public String getCompcd()	{	return compcd;	}   

	public void   setDept (String dept)	{ this.dept = dept; }
	public String getDept()	{	return dept;	}   	                   

	public void   setJik (String jik)	{ this.jik = jik; }
	public String getJik()	{	return jik;	}   	                   

	public void   setAcademic (String academic)	{ this.academic = academic; }
	public String getAcademic()	{	return academic;	}   	                   

	public void   setMajor (String major)	{ this.major = major; }
	public String getMajor()	{	return major;	}   	                   

	public void   setIsadd (String isadd )	{ this.isadd = isadd; }
	public String getIsadd()	{	return isadd;	}   	                   

	public void   setIscyber (String iscyber)	{ this.iscyber = iscyber; }
	public String getIscyber()	{	return iscyber;	}   	                   

	public void   setIsgubun (String isgubun)	{ this.isgubun = isgubun; }
	public String getIsgubun()	{	return isgubun;	}   	                   

	public void   setIsgubuntype (String isgubuntype)	{ this.isgubuntype = isgubuntype; }
	public String getIsgubuntype()	{	return isgubuntype;	}   	                   

	public void   setIsstatus (String isstatus)	{ this.isstatus = isstatus; }
	public String getIsstatus()	{	return isstatus;	}   	                   
	
	public void   setIstutor (String istutor)	{ this.istutor = istutor; }
	public String getIstutor()	{	return istutor;	}   
		                   	
	public void   setLicense (String license)	{ this.license = license; }
	public String getLicense()	{	return license;	}   	                   

	public void   setCareer (String career)	{ this.career = career; }
	public String getCareer()	{	return career;	}   	            

	public void   setBook (String book)	{ this.book = book; }
	public String getBook()	{	return book;	} 

	public void   setGrcode (String grcode)	{ this.grcode = grcode; }
	public String getGrcode()	{	return grcode;	} 

	public void   setGrcodenm (String grcodenm)	{ this.grcodenm = grcodenm; }
	public String getGrcodenm()	{	return grcodenm;	} 
		
	public void   setProfessional (String professional)	{ this.professional = professional; }
	public String getProfessional()	{	return professional;	} 
	
	public void   setCharge (String charge)	{ this.charge = charge; }
	public String getCharge()	{	return charge;	} 

	public void   setIsinfo (String isinfo)	{ this.isinfo = isinfo; }
	public String getIsinfo()	{	return isinfo;	} 
			
	public void   setEtc (String etc)	{ this.etc = etc; }
	public String getEtc()	{	return etc;	} 

	public void   setIndate (String indate)	{ this.indate = indate; }
	public String getIndate()	{	return indate;	} 
	
	public void   setLuserid (String luserid)	{ this.luserid = luserid; }
	public String getLuserid()	{	return luserid;	} 
	
	public void   setLdate (String ldate)	{ this.ldate = ldate; }
	public String getLdate()	{	return ldate;	} 

	public void   setSubj (String subj)	{ this.subj = subj; }
	public String getSubj()	{	return subj;	} 	
	
	public void   setSubjnm (String subjnm)	{ this.subjnm = subjnm; }
	public String getSubjnm()	{	return subjnm;	} 		
	
	public void   setPhoto (String photo)	{ this.photo = photo; }
	public String getPhoto()	{	return photo;	} 			
	
	public void   setFmon (String fmon)	{ this.fmon = fmon; }
	public String getFmon()	{	return fmon;	} 			
	
	public void   setTmon (String tmon)	{ this.tmon = tmon; }
	public String getTmon()	{	return tmon;	} 					

	public void   setTel_line (String tel_line)	{ this.tel_line = tel_line; }
	public String getTel_line()	{	return tel_line;	} 					
											          
	public void   setCareeryear (int careeryear)	{ this.careeryear = careeryear; }
	public int    getCareeryear()	{	return careeryear;	}	
									                   	
	public void   setDispnum (int dispnum)	{ this.dispnum = dispnum; }
	public int    getDispnum()	{	return dispnum;	}
	
	public void   setTotalpagecount(int totalpagecount)	{ this.totalpagecount = totalpagecount; }
	public int    getTotalpagecount()	{	return totalpagecount;	}
	
	public void   setRowcount(int rowcount)	{ this.rowcount = rowcount; }
	public int    getRowcount()	{	return rowcount;	}
	
	public void   setManagerchk(int managerchk)	{ this.managerchk = managerchk; }
	public int    getManagerchk()	{	return managerchk;	}
	
	public void   setYear (String year)	{ this.year = year; }
	public String getYear()	{	return year;	} 		
	
	public void   setSubjseq (String subjseq)	{ this.subjseq = subjseq; }
	public String getSubjseq()	{	return subjseq;	} 		
	
	public void   setEdustart (String edustart)	{ this.edustart = edustart; }
	public String getEdustart()	{	return edustart;	}
	
	public void   setEduend (String eduend)	{ this.eduend = eduend; }
	public String getEduend()	{	return eduend;	}
	
	public void   setLecture (int lecture)	{ this.lecture = lecture; }
	public int getLecture()	{	return lecture;	}
	
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
	
	public void   setLectscore (int lectscore)	{ this.lectscore = lectscore; }
	public int getLectscore()	{	return lectscore;	} 	
			
	public void   setLectlevel (String lectlevel)	{ this.lectlevel = lectlevel; }
	public String getLectlevel()	{	return lectlevel;	} 						
}
