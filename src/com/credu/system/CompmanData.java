//**********************************************************
//  1. 제      목: Compman DATA
//  2. 프로그램명 : CompmanData.java
//  3. 개      요: 부서담당자 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 5. 28
//  7. 수      정:
//**********************************************************
package com.credu.system;

public class CompmanData
{
    private String userid;            // USER ID
    private String comp;              // Comp Code = group + company + gpm + dept (+ part)
    private String groupsnm;          // Group   명
    private String companynm;         // Company 명
    private String gpmnm;             // 사업부   명
    private String deptnm;            // 부서     명
    private String partnm;            // 파트     명
    private String compnm;            // Comp코드명
    private String luserid;           // 최종수정자
    private String ldate;             // 최종수정일


    public CompmanData() {}

    public void   setUserid(String userid)       { this.userid= userid;        }
    public String getUserid()                    { return userid;              }
    public void   setComp(String comp)           { this.comp = comp;           }
    public String getComp()                      { return comp;                }
    public void   setGroupsnm(String groupsnm)   { this.groupsnm = groupsnm;   }
    public String getGroupsnm()                  { return groupsnm;            }
    public void   setCompanynm(String companynm) { this.companynm = companynm; }
    public String getCompanynm()                 { return companynm;           }
    public void   setGpmnm(String gpmnm)         { this.gpmnm = gpmnm;         }
    public String getGpmnm()                     { return gpmnm;               }
    public void   setDeptnm(String deptnm)       { this.deptnm = deptnm;       }
    public String getDeptnm()                    { return deptnm;              }
    public void   setPartnm(String partnm)       { this.partnm = partnm;       }
    public String getPartnm()                    { return partnm;              }
    public void   setCompnm(String compnm)       { this.compnm = compnm;       }
    public String getCompnm()                    { return compnm;              }
    public void   setLuserid(String luserid)     { this.luserid= luserid;      }
    public String getLuserid()                   { return luserid;             }
    public void   setLdate(String ldate)         { this.ldate = ldate;         }
    public String getLdate()                     { return ldate;               }


}
