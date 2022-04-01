//**********************************************************
//  1. 제      목: 관리자 Data
//  2. 프로그램명 : ManagerData.java
//  3. 개      요: 관리자 data bean
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 10
//  7. 수      정:
//**********************************************************

package com.credu.system;

/**
 * file name : ManagerData.java
 * date      : 2004/11/ 10
 * programmer: 강성욱 (swkang@credu.com)
 * function  : MANAGER DATA
 */

public class ManagerData
{
    private String userid;           // 관리자 ID
    private String gadmin;           // 권한 ID
    private String gadminnm;         // 권한명
    private String comp;             // 담당회사
    private String isdeleted;        // 삭제여부
    private String fmon;             // 활동기간 - 시작월
    private String tmon;             // 활동기간 - 종료월
    private String commented;        // 설명
    private String luserid;          // 최종수정자
    private String ldate;            // 최종수정일

    private String cono;             // 사번
    private String name;             // 이름
	private String jikwi;            // 직위    
    private String jikwinm;          // 직위
    private String companynm;        // 회사명
    private String gpmnm;            // 사업부명
    private String deptnm;           // 부서명
	private String compnm;           // 회사명 + / + 사업부명 + / + 부서명

    public ManagerData() {}

    public void   setUserid(String userid)       { this.userid = userid;       }
    public String getUserid()                    { return userid;              }
    public void   setGadmin(String gadmin)       { this.gadmin = gadmin;       }
    public String getGadmin()                    { return gadmin;              }
    public void   setGadminnm(String gadminnm)   { this.gadminnm = gadminnm;   }
    public String getGadminnm()                  { return gadminnm;            }
    public void   setComp(String comp)           { this.comp = comp;           }
    public String getComp()                      { return comp;                }
    public void   setIsdeleted(String isdeleted) { this.isdeleted = isdeleted; }
    public String getIsdeleted()                 { return isdeleted;           }
    public void   setFmon(String fmon)           { this.fmon = fmon;           }
    public String getFmon()                      { return fmon;                }
    public void   setTmon(String tmon)           { this.tmon = tmon;           }
    public String getTmon()                      { return tmon;                }
    public void   setCommented(String commented) { this.commented = commented; }
    public String getCommented()                 { return commented;           }
    public void   setLuserid(String luserid)     { this.luserid= luserid;      }
    public String getLuserid()                   { return luserid;             }
    public void   setLdate(String ldate)         { this.ldate = ldate;         }
    public String getLdate()                     { return ldate;               }
                      
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

}
