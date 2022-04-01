//**********************************************************
//  1. 제      목: PROJECT DATA
//  2. 프로그램명: ProjectData.java
//  3. 개      요: 리포트 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 22
//  7. 수      정:
//**********************************************************
package com.credu.study;
import com.credu.library.*;

public class ProjectData
{
    private String subj;
    private String course;
    private String cyear;
    private String courseseq;
    private String year;
    private String subjseq;
	private String subjseqgr;
    private String lesson;
	private String lessonnm;
    private String reptype;
    private String isopen;
    private String isopenscore;
    private String title;
    private String contents;
    private String expiredate;
    private String deadlinesdate;
    private String deadlineedate;
    private String upfile;
	private String realfile;
	private String realfile2;
    private String upfile2;
    private String luserid;
    private String ldate;
    private String tudate;
    private String isclosed;
    private String subjnm;
    private String isonoff;
    private String coursenm;
    private String groupcnt;
    private String tocnt;
    private String grcnt;
    private String pjcnt;
    private String micnt;
    private String pgcnt;
    private String jccnt;
    private String assigncnt;
    private String projgrp;
    private String projname;
    private String userid;
    private String cono;
    private String name;
    private String jikwinm;
    private String companynm;
    private String gpmnm;
    private String deptnm;
    private String indate;
    private String isfinal;
    private String tucontents;
    private String chief;
    private String isnewcourse;
    private String title2;
    private String contents2;
    private String upfile1;
    private String couserid;
    private String coname;
	private String isusedcopy;
	private String retreason;
	private String retdate;
	private String isret;
    private int wreport;    // 가중치
    private int projseq;    // 리포트셋트번호
    private int seq;
    private int ordseq;
    private int ordseqcnt;
    private int projseqcnt;
    private int score;
    private int score2;
    private int score_mas;
    private int cnt;
    private int rowspan;
	private int rowspanseq;
	private int tabseq;				// Table NUMBER
	private String upfilesize;      // 파일사이즈
	private String contentsbyte;    // 내용바이트수
	private String deptnam;   	    // 부서명
	private String retuserid;
    private String edustart;
    private String eduend;
	private String ansyn;
	private String useyn;
	private String assigntitle;
	private String assigndate;
	private String issubmit;



    public void   setAssigndate(String assigndate)    { this.assigndate = assigndate;          }
    public String getAssigndate()                  { return assigndate;                  }

    public void   setAssigntitle(String assigntitle)    { this.assigntitle = assigntitle;          }
    public String getAssigntitle()                  { return assigntitle;                  }

	public void   setRetuserid(String retuserid)    { this.retuserid = retuserid;          }
    public String getRetuserid()                  { return retuserid;                  }

    public void   setDeptnam(String deptnam)    { this.deptnam = deptnam;          }
    public String getDeptnam()                  { return deptnam;                  }

    public void   setSubj(String subj)          { this.subj = subj;             }
    public String getSubj()                     { return subj;                  }

    public void   setCourse(String course)      { this.course = course;     }
    public String getCourse()                   { return course;            }

    public void   setCyear(String cyear)        { this.cyear = cyear;       }
    public String getCyear()                    { return cyear;             }

    public void   setCourseseq(String courseseq){ this.courseseq = courseseq;}
    public String getCourseseq()                { return courseseq;         }

    public void   setYear(String year)          { this.year = year;             }
    public String getYear()                     { return year;                  }

    public void   setSubjseq(String subjseq)    { this.subjseq = subjseq;       }
    public String getSubjseq()                  { return subjseq;               }

    public void   setSubjseqgr(String subjseqgr)    { this.subjseqgr = subjseqgr;       }
    public String getSubjseqgr()                  { return subjseqgr;               }

    public void   setLesson(String lesson)       { this.lesson = lesson;        }
    public String getLesson()                    { return lesson;               }

    public void   setLessonnm(String lessonnm)       { this.lessonnm = lessonnm;        }
    public String getLessonnm()                    { return lessonnm;               }

    public void   setReptype(String reptype)    { this.reptype = reptype;       }
    public String getReptype()                  { return reptype;               }

    public void   setIsopen(String isopen)      { this.isopen = isopen;         }
    public String getIsopen()                   { return isopen;                }

    public void   setIsopenscore(String isopenscore){this.isopenscore = isopenscore;}
    public String getIsopenscore()               { return isopenscore;            }

    public void   setTitle(String title)        { this.title = title;           }
    public String getTitle()                    { return title;                 }

    public void   setContents(String contents)  { this.contents = contents;     }
    public String getContents()                 { return contents;              }

    public void   setExpiredate(String expiredate){this.expiredate = expiredate;}
    public String getExpiredate()               { return expiredate;            }

    public void   setDeadlinesdate(String deadlinesdate){this.deadlinesdate = deadlinesdate;}
    public String getDeadlinesdate()               { return deadlinesdate;            }

    public void   setDeadlineedate(String deadlineedate){this.deadlineedate = deadlineedate;}
    public String getDeadlineedate()               { return deadlineedate;            }

    public void   setUpfile(String upfile)      { this.upfile = upfile;         }
    public String getUpfile()                   { return upfile;                }

    public void   setRealfile(String realfile)  { this.realfile = realfile;     }
    public String getRealfile()                 { return realfile;              }

    public void   setRealfile2(String realfile2)  { this.realfile2 = realfile2;     }
    public String getRealfile2()                 { return realfile2;              }

    public void   setUpfile2(String upfile2)    { this.upfile2 = upfile2;       }
    public String getUpfile2()                  { return upfile2;               }

    public void   setLuserid(String luserid)    { this.luserid = luserid;       }
    public String getLuserid()                  { return luserid;               }

    public void   setLdate(String ldate)        { this.ldate = ldate;           }
    public String getLdate()                    { return ldate;                 }

    public void   setTudate(String tudate)       { this.tudate = tudate;          }
    public String getTudate()                    { return tudate;                 }

    public void   setIsclosed(String isclosed)  { this.isclosed = isclosed;     }
    public String getIsclosed()                 { return isclosed;              }

    public void   setSubjnm(String subjnm)      { this.subjnm = subjnm;         }
    public String getSubjnm()                   { return subjnm;                }

    public void   setIsonoff(String isonoff)     { this.isonoff = isonoff;      }
    public String getIsonoff()                   { return isonoff;              }

    public void   setCoursenm(String coursenm)  { this.coursenm = coursenm;     }
    public String getCoursenm()                 { return coursenm;              }

    public void   setGroupcnt(String groupcnt)  { this.groupcnt = groupcnt;     }
    public String getGroupcnt()                 { return groupcnt;              }

    public void   setTocnt(String tocnt)        { this.tocnt = tocnt;           }
    public String getTocnt()                    { return tocnt;                 }

    public void   setGrcnt(String grcnt)        { this.grcnt = grcnt;           }
    public String getGrcnt()                    { return grcnt;                 }

    public void   setPjcnt(String pjcnt)        { this.pjcnt = pjcnt;           }
    public String getPjcnt()                    { return pjcnt;                 }

    public void   setMicnt(String micnt)        { this.micnt = micnt;           }
    public String getMicnt()                    { return micnt;                 }

    public void   setPgcnt(String pgcnt)        { this.pgcnt = pgcnt;           }
    public String getPgcnt()                    { return pgcnt;                 }

    public void   setAssigncnt(String assigncnt){ this.assigncnt = assigncnt;   }
    public String getAssigncnt()                { return assigncnt;             }

	public void   setJccnt(String jccnt)		{ this.jccnt = jccnt;   }
    public String getJccnt()                	{ return jccnt;             }

    public void   setProjgrp(String projgrp)    { this.projgrp = projgrp;       }
    public String getProjgrp()                  { return projgrp;               }

    public void   setProjname(String projname)  { this.projname = projname;     }
    public String getProjname()                 { return projname;              }

    public void   setUserid(String userid)      { this.userid = userid;         }
    public String getUserid()                   { return userid;                }

    public void   setCono(String cono)          { this.cono = cono;             }
    public String getCono()                     { return cono;                  }

    public void   setName(String name)          { this.name = name;             }
    public String getName()                     { return name;                  }

    public void   setJikwinm(String jikwinm)    { this.jikwinm = jikwinm;       }
    public String getJikwinm()                  { return jikwinm;               }

    public void   setCompanynm(String companynm){ this.companynm = companynm;   }
    public String getCompanynm()                { return companynm;             }

    public void   setGpmnm(String gpmnm)        { this.gpmnm = gpmnm;           }
    public String getGpmnm()                    { return gpmnm;                 }

    public void   setDeptnm(String deptnm)      { this.deptnm = deptnm;         }
    public String getDeptnm()                   { return deptnm;                }

    public void   setIndate(String indate)      { this.indate = indate;         }
    public String getIndate()                   { return indate;                }

    public void   setIsfinal(String isfinal)    { this.isfinal = isfinal;       }
    public String getIsfinal()                  { return isfinal;               }

    public void   setTucontents(String tucontents){ this.tucontents=tucontents; }
    public String getTucontents()               { return tucontents;            }

    public void   setChief(String chief)        { this.chief = chief;           }
    public String getChief()                    { return chief;                 }

    public void   setIsnewcourse(String isnewcourse){this.isnewcourse=isnewcourse;}
    public String getIsnewcourse()              { return isnewcourse;       }

    public void   setTitle2(String title2)      { this.title2 = title2;         }
    public String getTitle2()                   { return title2;                }

    public void   setContents2(String contents2){ this.contents2 = contents2;   }
    public String getContents2()                { return contents2;             }

    public void   setUpfile1(String upfile1)    { this.upfile1 = upfile1;       }
    public String getUpfile1()                  { return upfile1;               }

    public void   setCouserid(String couserid)  { this.couserid = couserid;     }
    public String getCouserid()                 { return couserid;              }

    public void   setConame(String coname)      { this.coname = coname;         }
    public String getConame()                   { return coname;                }

    public void   setIsusedcopy(String isusedcopy)  { this.isusedcopy = isusedcopy;         }
    public String getIsusedcopy()                   { return isusedcopy;                }

    public void   setRetreason(String retreason) { this.retreason = retreason;         }
    public String getRetreason()                 { return retreason;                }

    public void   setRetdate(String retdate)      { this.retdate = retdate;         }
    public String getRetdate()                   { return retdate;                }

    public void   setIsret(String isret)      { this.isret = isret;         }
    public String getIsret()                   { return isret;                }

    public void   setSeq(int seq)               { this.seq = seq;               }
    public int    getSeq()                      { return seq;                   }

    public void   setUpfilesize(String upfilesize) { this.upfilesize = upfilesize;        }
    public String getUpfilesize()                  { return upfilesize;                   }

    public void   setContentsbyte(String contentsbyte) { this.contentsbyte = contentsbyte;        }
    public String getContentsbyte()                    { return contentsbyte;                   }

    public void   setOrdseq(int ordseq)         { this.ordseq = ordseq;         }
    public int    getOrdseq()                   { return ordseq;                }

    public void   setProjseq(int projseq)         { this.projseq = projseq;      }
    public int    getProjseq()                   { return projseq;              }

    public void   setOrdseqcnt(int ordseqcnt)         { this.ordseqcnt = ordseqcnt;         }
    public int    getOrdseqcnt()                   { return ordseqcnt;                }

    public void   setProjseqcnt(int projseqcnt)         { this.projseqcnt = projseqcnt;      }
    public int    getProjseqcnt()                   { return projseqcnt;              }

    public void   setScore(int score)           { this.score = score;           }
    public int    getScore()                    { return score;                 }

    public void   setScore2(int score2)         { this.score2 = score2;         }
    public int    getScore2()                   { return score2;                }

    public void   setScore_mas(int score_mas)   { this.score_mas = score_mas;   }
    public int    getScore_mas()                { return score_mas;             }

    public void   setCnt(int cnt)               { this.cnt = cnt;               }
    public int    getCnt()                      { return cnt;                   }

    public void   setRowspan(int rowspan)       { this.rowspan = rowspan;       }
    public int    getRowspan()                  { return rowspan;               }

    public void   setRowspanseq(int rowspanseq)    { this.rowspanseq = rowspanseq;    }
    public int    getRowspanseq()                  { return rowspanseq;               }

    public void   setWreport(int wreport)       { this.wreport = wreport;       }
    public int    getWreport()                  { return wreport;               }

    public void   setEdustart(String edustart)  { this.edustart=edustart;   }
    public String getEdustart()                 { return edustart;          }

    public void   setEduend(String eduend)      { this.eduend=eduend;       }
    public String getEduend()                   { return eduend;            }

    public void   setAnsyn(String ansyn)     { this.ansyn=ansyn;      }
    public String getAnsyn()                 { return ansyn;          }

    public void   setUseyn(String useyn)     { this.useyn=useyn;       }
    public String getUseyn()                 { return useyn;           }

    public void   setIssubmit(String issubmit)     { this.issubmit=issubmit;       }
    public String getIssubmit()                 { return issubmit;           }

	/**
	 * @return
	 */
	public int getTabseq() {
		return tabseq;
	}

	/**
	 * @param i
	 */
	public void setTabseq(int i) {
		tabseq = i;
	}
}
