//**********************************************************
//  1. 제      목: MEMBERMILEAGE  DATA
//  2. 프로그램명 : MemberMileageData.java
//  3. 개      요: 마일리지 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 5. 28
//  7. 수      정: 
//**********************************************************
package com.credu.system;
import com.credu.library.*;

/**
 * file name : MemberMileageData.java
 * date      : 2003/5/28
 * programmer: 정상진 (puggjsj@hanmail.net)
 * function  : Poll DATA
 */
public class MemberMileageData
{
    private String userid;            // USER ID
    private String mileagecode;       // mileage code
    private String mileagename;       // mileage name
    private String sdesc;             // description
    private String luserid;           // 최종수정자
    private String ldate;             // 최종수정일

    private String cono;             // 사번
    private String name;             // 이름
	private String jikwi;            // 직위       
    private String jikwinm;          // 직위
    private String companynm;        // 회사명
    private String gpmnm;            // 사업부명
    private String deptnm;           // 부서명
	private String compnm;           // 회사명 + / + 사업부명 + / + 부서명
	
    private int    point;              // 점수


    public MemberMileageData() {}

    public void   setUserid(String userid)           { this.userid = userid;           }
    public String getUserid()                        { return userid;                  }
    public void   setMileagecode(String mileagecode) { this.mileagecode = mileagecode; }
    public String getMileagecode()                   { return mileagecode;             }
    public void   setMileagename(String mileagename) { this.mileagename = mileagename; }
    public String getMileagename()                   { return mileagename;             }
    public void   setSdesc(String sdesc)             { this.sdesc = sdesc;             }
    public String getSdesc()                         { return sdesc;                   }
    public void   setLuserid(String luserid)         { this.luserid = luserid;         }
    public String getLuserid()                       { return luserid;                 }
    public void   setLdate(String ldate)             { this.ldate = ldate;             }
    public String getLdate()                         { return ldate;                   }

    public void   setCono(String cono)           { this.cono = cono;           }
    public String getCono()                      { return cono;                }
    public void   setName(String name)           { this.name = name;           }
    public String getName()                      { return name;                }
	public void   setJikwi(String jikwi)         { this.jikwi = jikwi;         }
	public String getJikwi()                     { return jikwi;               }      
    public void   setJikwinm(String jikwinm)     { this.jikwinm = jikwinm;     }
    public String getJikwinm()                   { return jikwinm;             }
    public void   setCompanynm(String companynm) { this.companynm = companynm; }
    public String getCompanynm()                 { return companynm;           }
    public void   setGpmnm(String gpmnm)         { this.gpmnm = gpmnm;         }
    public String getGpmnm()                     { return gpmnm;               }
    public void   setDeptnm(String deptnm)       { this.deptnm = deptnm;       }
    public String getDeptnm()                    { return deptnm;              }
	public void   setCompnm(String compnm)       { this.compnm = compnm;       }
	public String getCompnm()                    { return compnm;              }       

    public void   setPoint(int point)    { this.point = point;  }
    public int    getPoint()             { return point;        }

}
