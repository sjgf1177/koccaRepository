//**********************************************************
//  1. 제      목: TORON DATA
//  2. 프로그램명: ToronData.java
//  3. 개      요: 토론방 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 22
//  7. 수      정:
//**********************************************************
package com.credu.study;
import com.credu.library.*;

public class ToronData
{       	         
    private String grcode;		
    private String grcodenm;		    
    private String gyear;		
    private String grseq;		
    private String scsubj;
    private String scyear;
    private String scsubjseq;
    private String scsubjnm;
    private String course;		
    private String cyear;		
    private String courseseq;	    	
    private String coursenm;	
    private String subj;	
    private String year;		    
    private String subjseq;
	private String subjseqgr;		
    private String subjnm;          
    private String tpcode;		
    private String title;		
    private String adcontent;	
    private String aduserid;			
    private String addate;		
    private String f_open;      
    private String started;     
    private String ended;       
    private String upday;       
    private String luserid;		
    private String ldate;						
    private String isopen;	     
    private String edustart;
    private String eduend;
    private String name;
    private String isnewcourse;    
    private String refyn;
	private String isonoff;        
	    
    private int seq;			
    private int refseq;		
    private int levels;	    
    private int position;	    
    private int cnt;	
    private int joinnum;	   
    private int rowspan;    
 
    public void   setScsubj(String scsubj)      { this.scsubj = scsubj;     }
    public String getScsubj()                   { return scsubj;            }

    public void   setScyear(String scyear)       { this.scyear = scyear;    }
    public String getScyear()                    { return scyear;           }
    
    public void   setScsubjseq(String scsubjseq){ this.scsubjseq = scsubjseq;}
    public String getScsubjseq()                { return scsubjseq;         }
    
    public void   setScsubjnm(String scsubjnm)  { this.scsubjnm=scsubjnm;   }
    public String getScsubjnm()                 { return scsubjnm;          }     
                        
    public void   setCourse(String course)      { this.course = course;     }
    public String getCourse()                   { return course;            }

    public void   setCyear(String cyear)        { this.cyear = cyear;       }
    public String getCyear()                    { return cyear;             }
    
    public void   setCourseseq(String courseseq){ this.courseseq = courseseq;}
    public String getCourseseq()                { return courseseq;         }
    
    public void   setCoursenm(String coursenm)  { this.coursenm=coursenm;   }
    public String getCoursenm()                 { return coursenm;          }

    public void   setGrcode(String grcode)      { this.grcode = grcode;     }
    public String getGrcode()                   { return grcode;            }

    public void   setGrcodenm(String grcodenm)  { this.grcodenm = grcodenm; }
    public String getGrcodenm()                 { return grcodenm;          }

    public void   setGyear(String gyear)        { this.gyear = gyear;       }
    public String getGyear()                    { return gyear;             }
                
    public void   setGrseq(String grseq)        { this.grseq = grseq;       }
    public String getGrseq()                    { return grseq;             }
                                                
    public void   setYear(String year)          { this.year = year;         }
    public String getYear()                     { return year;              } 
                                                
    public void   setSubj(String subj)          { this.subj = subj;         }
    public String getSubj()                     { return subj;              }
                                                    
    public void   setSubjseq(String subjseq)    { this.subjseq = subjseq;   }
    public String getSubjseq()                  { return subjseq;           }
	
    public void   setSubjseqgr(String subjseqgr)    { this.subjseqgr = subjseqgr;   }
    public String getSubjseqgr()                  { return subjseqgr;           }
                                                
    public void   setTpcode(String tpcode)      { this.tpcode = tpcode;     }
    public String getTpcode()                   { return tpcode;            }
                                                
    public void   setTitle(String title)        { this.title = title;       }
    public String getTitle()                    { return title;             }
    
    public void   setAdcontent(String adcontent){ this.adcontent = adcontent;}
    public String getAdcontent()                { return adcontent;         }
    
    public void   setAduserid(String aduserid)  { this.aduserid = aduserid; }
    public String getAduserid()                 { return aduserid;          }
    
    public void   setAddate(String addate)      { this.addate = addate;     }
    public String getAddate()                   { return addate;            }              

    public void   setF_open(String f_open)      { this.f_open = f_open;     }
    public String getF_open()                   { return f_open;            }      
    
    public void   setStarted(String started)    { this.started = started;   }
    public String getStarted()                  { return started;           }     
    
    public void   setEnded(String ended)        { this.ended = ended;       }
    public String getEnded()                    { return ended;             }     
    
    public void   setUpday(String upday)        { this.upday = upday;       }
    public String getUpday()                    { return upday;             }     
    
    public void   setLuserid(String luserid)    { this.luserid = luserid;   }
    public String getLuserid()                  { return luserid;           }     
    
    public void   setLdate(String ldate)        { this.ldate = ldate;       }
    public String getLdate()                    { return ldate;             }     
                        
    public void   setIsopen(String isopen)      { this.isopen = isopen;     }
    public String getIsopen()                   { return isopen;            }           
        
    public void   setSubjnm(String subjnm)      { this.subjnm = subjnm;     }
    public String getSubjnm()                   { return subjnm;            }        
  
    public void   setEdustart(String edustart)  { this.edustart=edustart;   }
    public String getEdustart()                 { return edustart;          }  

    public void   setEduend(String eduend)      { this.eduend=eduend;       }
    public String getEduend()                   { return eduend;            }  

    public void   setName(String name)          { this.name=name;           }
    public String getName()                     { return name;              }  
    
    public void   setIsnewcourse(String isnewcourse){this.isnewcourse=isnewcourse;}
    public String getIsnewcourse()              { return isnewcourse;       }     
              
    public void   setRefyn(String refyn)        { this.refyn=refyn;         }
    public String getRefyn()                    { return refyn;             }      
                            
    public void   setSeq(int seq)               { this.seq = seq;           }
    public int    getSeq()                      { return seq;               }
        
    public void   setRefseq(int refseq)         { this.refseq = refseq;     }
    public int    getRefseq()                   { return refseq;            }    
    
    public void   setLevels(int levels)         { this.levels = levels;     }
    public int    getLevels()                   { return levels;            }
    
    public void   setPosition(int position)     { this.position = position; }
    public int    getPosition()                 { return position;          }
        
    public void   setCnt(int cnt)               { this.cnt = cnt;           }
    public int    getCnt()                      { return cnt;               }
    
    public void   setJoinnum(int joinnum)       { this.joinnum = joinnum;   }
    public int    getJoinnum()                  { return joinnum;           } 
   
    public void   setRowspan(int rowspan)       { this.rowspan = rowspan;   }
    public int    getRowspan()                  { return rowspan;           }       
    
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
}
