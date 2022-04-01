//**********************************************************
//  1. 제      목: STUDY STATUS DATA
//  2. 프로그램명: StudyStatusData.java
//  3. 개      요: 학습현황 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 30
//  7. 수      정:
//**********************************************************
package com.credu.study;

public class StudyStatusData {

	private String grcode;
	private String gyear;
	private String grseq;
	private String grcodenm;
	private String grseqnm;
    private String course;
    private String cyear;
    private String courseseq;
	private String subj;
    private String year;
	private String subjseq;
	private String subjseqgr;
	private String subjnm;
	private String compnm;      /* 소속명  	*/
	private String jikwi;       /* 직위     */
	private String jikwinm;     /* 직위명   */
	private String jikup;       /* 직급     */
	private String jikupnm;     /* 직급명   */
	private String userid ;     /* ID       */
	private String cono;        /* 사번     */
	private String name;   	    /* 이름     */
	private String appdate;	    /* 신청일   */
	private String edustart;    /* 교육 시작일시 	*/
	private String eduend;		/* 교육 종료일시 	*/
	private String chkfirst;    /* 1차 승인 여부 	*/
	private String isproposeapproval;    /* 1차 승인 여부 	*/
	private String chkfinal;    /* 최종 승인 여부 	*/
	private String membergubun;      /* 회원구분  	*/
	private String membergubunnm;      /* 회원구분명  	*/
	private String isonoff;
	private String coursenm;
	private String comptel;
	private String email;
	private String handphone;	/* 핸드폰 추가	*/
	private String company;
	private String companynm;
    private String isnewcourse;
	private String firstedu;
	private String totaltime;
	private String totalminute;
	private String totalsec;
	private String ldatestart;
	private String ldateend;
	private String classnm;
	private String lesson;
	private String sdesc;
	private String ldate;
	private String projscore;
	private String actscore;
	private String isgraduated;
	private String work_plcnm;
	private String sdate;
    private String tuserid;     /* 강사 아이디       */
    private String tname;       /* 강사 이름         */
    private String upperclassname;   /* 대분류명         */

	private int mtest;
	private int ftest;
	private int htest;
	private int point;
	private int educnt;
	private int tstep;
	private int report;
	private int etc1;
	private int etc2;
	private int avtstep;
	private int avmtest;
	private int avftest;
	private int avhtest;
	private int avreport;
	private int avetc1;
	private int avetc2;
	private int score;
	private int act;
	private int gradcnt;
	private int samtotal;
    private int rowspan;

	private String isbelongcourse;		// 전문가과정여부
	private int subjcnt;				// 전문가과정안 과목수
	private int studentcnt;				// 과목안의 학생수

	public StudyStatusData() {};

    public void   setGrcode(String grcode)      { this.grcode = grcode;     }
    public String getGrcode()                   { return grcode;            }

    public void   setGyear(String gyear)        { this.gyear = gyear;       }
    public String getGyear()                    { return gyear;             }

    public void   setGrseq(String grseq)        { this.grseq = grseq;       }
    public String getGrseq()                    { return grseq;             }

    public void   setGrcodenm(String grcodenm)  { this.grcodenm = grcodenm; }
    public String getGrcodenm()                 { return grcodenm;          }

    public void   setGrseqnm(String grseqnm)   { this.grseqnm = grseqnm; }
    public String getGrseqnm()                  { return grseqnm;          }

    public void   setCourse(String course)      { this.course = course;     }
    public String getCourse()                   { return course;            }

    public void   setCyear(String cyear)        { this.cyear = cyear;       }
    public String getCyear()                    { return cyear;             }

    public void   setCourseseq(String courseseq){ this.courseseq = courseseq;}
    public String getCourseseq()                { return courseseq;         }

    public void   setSubj(String subj)          { this.subj = subj;         }
    public String getSubj()                     { return subj;              }

    public void   setYear(String year)          { this.year = year;         }
    public String getYear()                     { return year;              }

    public void   setSubjseq(String subjseq)    { this.subjseq = subjseq;   }
    public String getSubjseq()                  { return subjseq;           }

    public void   setSubjseqgr(String subjseqgr)    { this.subjseqgr = subjseqgr;   }
    public String getSubjseqgr()                  { return subjseqgr;           }

    public void   setSubjnm(String subjnm)      { this.subjnm = subjnm;     }
    public String getSubjnm()                   { return subjnm;            }

    public void   setCompnm(String compnm)      { this.compnm = compnm;     }
    public String getCompnm()                   { return compnm;            }

    public void   setJikwi(String jikwi)        { this.jikwi = jikwi;       }
    public String getJikwi()                    { return jikwi;             }

    public void   setJikwinm(String jikwinm)    { this.jikwinm = jikwinm;   }
    public String getJikwinm()                  { return jikwinm;           }

	public void   setJikup(String jikup)        { this.jikup = jikup;       }
	public String getJikup()                    { return jikup;             }

	public void   setJikupnm(String jikupnm)    { this.jikupnm = jikupnm;   }
	public String getJikupnm()                  { return jikupnm;           }

    public void   setUserid(String userid)      { this.userid = userid;     }
    public String getUserid()                   { return userid;            }

    public void   setCono(String cono)          { this.cono = cono;         }
    public String getCono()                     { return cono;              }

    public void   setName(String name)          { this.name = name;         }
    public String getName()                     { return name;              }

    public void   setAppdate(String appdate)    { this.appdate = appdate;   }
    public String getAppdate()                  { return appdate;           }

    public void   setEdustart(String edustart)  { this.edustart = edustart; }
    public String getEdustart()                 { return edustart;          }

    public void   setEduend(String eduend)      { this.eduend = eduend;     }
    public String getEduend()                   { return eduend;            }

    public void   setChkfirst(String chkfirst)  { this.chkfirst = chkfirst; }
    public String getChkfirst()                 { return chkfirst;          }

    public void   setIsproposeapproval(String chkfirst)  { this.isproposeapproval = isproposeapproval; }
    public String getIsproposeapproval()                 { return isproposeapproval;          }

    public void   setChkfinal(String chkfinal)  { this.chkfinal = chkfinal; }
    public String getChkfinal()                 { return chkfinal;          }

    public void   setIsonoff(String isonoff)    { this.isonoff = isonoff;   }
    public String getIsonoff()                  { return isonoff;           }

    public void   setCoursenm(String coursenm)  { this.coursenm = coursenm; }
    public String getCoursenm()                 { return coursenm;          }

    public void   setComptel(String comptel)    { this.comptel = comptel;   }
    public String getComptel()                  { return comptel;           }

    public void   setEmail(String email)        { this.email = email;       }
    public String getEmail()                    { return email;             }

    public void   setHandphone(String handphone)        { this.handphone = handphone;       }
    public String getHandphone()                    { return handphone;             }

    public void   setCompany(String company)    { this.company = company;   }
    public String getCompany()                  { return company;           }

    public void   setCompanynm(String companynm){ this.companynm = companynm;}
    public String getCompanynm()                { return companynm;         }

    public void   setIsnewcourse(String isnewcourse){this.isnewcourse=isnewcourse;}
    public String getIsnewcourse()              { return isnewcourse;       }

    public void   setFirstedu(String firstedu)  { this.firstedu = firstedu; }
    public String getFirstedu()                 { return firstedu;          }

    public void   setTotaltime(String totaltime){ this.totaltime=totaltime; }
    public String getTotaltime()                { return totaltime;         }

    public void   setTotalminute(String totalminute){this.totalminute=totalminute;}
    public String getTotalminute()              { return totalminute;       }

    public void   setTotalsec(String totalsec)  {this.totalsec=totalsec;    }
    public String getTotalsec()                 { return totalsec;          }

    public void   setLdatestart(String ldatestart){this.ldatestart=ldatestart;}
    public String getLdatestart()               { return ldatestart;        }

    public void   setLdateend(String ldateend)  { this.ldateend = ldateend; }
    public String getLdateend()                 { return ldateend;          }

    public void   setClassnm(String classnm)    { this.classnm = classnm;   }
    public String getClassnm()                  { return classnm;           }

    public void   setLesson(String lesson)      { this.lesson = lesson;     }
    public String getLesson()                   { return lesson;            }

    public void   setSdesc(String sdesc)        { this.sdesc = sdesc;       }
    public String getSdesc()                    { return sdesc;             }

    public void   setLdate(String ldate)        { this.ldate = ldate;       }
    public String getLdate()                    { return ldate;             }

    public void   setProjscore(String projscore){ this.projscore=projscore; }
    public String getProjscore()                { return projscore;         }

    public void   setActscore(String actscore)  { this.actscore = actscore; }
    public String getActscore()                 { return actscore;          }

    public void   setWork_plcnm(String work_plcnm) { this.work_plcnm = work_plcnm; }
    public String getWork_plcnm()                  { return work_plcnm;            }

    public void   setSdate(String sdate)        { this.sdate = sdate;      }
    public String getSdate()                    { return sdate;            }

    public void setTuserid(String tuserid)      { this.tuserid = tuserid;  }
    public String getTuserid()                  { return tuserid;          }

    public void setTname(String tname)          { this.tname = tname;      }
    public String getTname()                    { return tname;            }

    public void setUpperclassname(String upperclassname) { this.upperclassname = upperclassname; }
    public String getUpperclassname()           { return upperclassname;                }

    
    public void setScore(int score)             { this.score = score;       }
    public int  getScore()                      { return score;             }

    public void setPoint(int point)             { this.point = point;       }
    public int  getPoint()                      { return point;             }

    public void setEducnt(int educnt)           { this.educnt = educnt;     }
    public int  getEducnt()                     { return educnt;            }

    public void setTstep(int tstep)             { this.tstep = tstep;       }
    public int  getTstep()                      { return tstep;             }

    public void setMtest(int mtest)             { this.mtest = mtest;       }
    public int  getMtest()                      { return mtest;             }

    public void setFtest(int ftest)             { this.ftest = ftest;       }
    public int  getFtest()                      { return ftest;             }

    public void setHtest(int htest)             { this.htest = htest;       }
    public int  getHtest()                      { return htest;             }

    public void setReport(int report)           { this.report = report;     }
    public int  getReport()                     { return report;            }

    public void setEtc1(int etc1)               { this.etc1 = etc1;         }
    public int  getEtc1()                       { return etc1;              }

    public void setEtc2(int etc2)               { this.etc2 = etc2;         }
    public int  getEtc2()                       { return etc2;              }

    public void setAct(int act)                 { this.act = act;           }
    public int  getAct()                        { return act;               }

    public void setAvtstep(int avtstep)         { this.avtstep = avtstep;   }
    public int  getAvtstep()                    { return avtstep;           }

    public void setAvmtest(int avmtest)         { this.avmtest = avmtest;   }
    public int  getAvmtest()                    { return avmtest;           }

    public void setAvftest(int avftest)         { this.avftest = avftest;   }
    public int  getAvftest()                    { return avftest;           }

    public void setAvhtest(int avhtest)         { this.avhtest = avhtest;   }
    public int  getAvhtest()                    { return avhtest;           }

    public void setAvreport(int avreport)       { this.avreport = avreport; }
    public int  getAvreport()                   { return avreport;          }

    public void setAvetc1(int avetc1)           { this.avetc1 = avetc1;     }
    public int  getAvetc1()                     { return avetc1;            }

    public void setAvetc2(int avetc2)           { this.avetc2 = avetc2;     }
    public int  getAvetc2()                     { return avetc2;            }

    public void setGradcnt(int gradcnt)         { this.gradcnt = gradcnt;   }
    public int  getGradcnt()                    { return gradcnt;           }

    public void setSamtotal(int samtotal)       { this.samtotal = samtotal;   }
    public int  getSamtotal()                   { return samtotal;           }

    public void setRowspan(int rowspan)         { this.rowspan = rowspan;   }
    public int  getRowspan()                    { return rowspan;           }
	/**
	 * @return
	 */
	public String getIsgraduated() {
		return isgraduated;
	}

	/**
	 * @param string
	 */
	public void setIsgraduated(String string) {
		isgraduated = string;
	}

	public String getMembergubun() {
		return membergubun;
	}

	public void setMembergubun(String membergubun) {
		this.membergubun = membergubun;
	}

	public String getMembergubunnm() {
		return membergubunnm;
	}

	public void setMembergubunnm(String membergubunnm) {
		this.membergubunnm = membergubunnm;
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

	public int getStudentcnt() {
		return studentcnt;
	}

	public void setStudentcnt(int studentcnt) {
		this.studentcnt = studentcnt;
	}



}