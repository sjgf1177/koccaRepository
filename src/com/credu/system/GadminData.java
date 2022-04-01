//**********************************************************
//  1. 제      목: 권한 Data
//  2. 프로그램명 : GadminData.java
//  3. 개      요: 권한 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 22
//  7. 수      정:
//**********************************************************

package com.credu.system;


//TZ_Gadmin
//gadmin,control,gadminnm,comments,isneedcomp

public class GadminData
{
	private String gadmin;                // 권한ID
	private String padmin;				  // 상속권한ID
	private String control;               // 조작범위   R-READ, W:WRITE
	private String gadminnm;              // 권한명
	private String comments;              // 권한설명
	private String isneedgrcode;          // 교육그룹코드필요여부
	private String isneedsubj;            // 과정코드필요여부
	private String isneedcomp;            // 회사코드필요여부
	private String isneeddept;            // 부서코드필요여부
	private String isoutcomp;             // 외주업체회사코드필요여부

	private int dispnum;            // 총게시물수
	private int total_page_count;   // 게시물총페이지수

	public GadminData() {}

	public void   setGadmin(String gadmin)             { this.gadmin = gadmin;             }
	public String getGadmin()                          { return gadmin;                    }
	public void   setPadmin(String padmin)			   { this.padmin = padmin;			   }
	public String getPadmin()						   { return padmin;					   }
	public void   setControl(String control)           { this.control = control;           }
	public String getControl()                         { return control;                   }
	public void   setGadminnm(String gadminnm)         { this.gadminnm = gadminnm;         }
	public String getGadminnm()                        { return gadminnm;                  }
	public void   setComments(String comments)         { this.comments = comments;         }
	public String getComments()                        { return comments;                  }
	public void   setIsneedgrcode(String isneedgrcode) { this.isneedgrcode = isneedgrcode; }
	public String getIsneedgrcode()                    { return isneedgrcode;              }
	public void   setIsneedsubj(String isneedsubj)     { this.isneedsubj = isneedsubj;     }
	public String getIsneedsubj()                      { return isneedsubj;                }
	public void   setIsneedcomp(String isneedcomp)     { this.isneedcomp = isneedcomp;     }
	public String getIsneedcomp()                      { return isneedcomp;                }
	public void   setIsneeddept(String isneeddept)     { this.isneeddept = isneeddept;     }
	public String getIsneeddept()                      { return isneeddept;                }
	public void   setIsneedoutcomp(String isoutcomp)     { this.isoutcomp = isoutcomp;     }
	public String getIsneedoutcomp()                      { return isoutcomp;              }

	public void   setDispnum(int dispnum) { this.dispnum = dispnum; }
	public int    getDispnum()            { return dispnum;         }
	public void   setTotalPageCount(int total_page_count) { this.total_page_count = total_page_count; }
	public int    getTotalpagecount()                     { return total_page_count;                  }

}
