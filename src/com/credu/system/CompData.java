//**********************************************************
//  1. 제      목: COMP DATA
//  2. 프로그램명 : CompData.java
//  3. 개      요: 회사 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 5. 28
//  7. 수      정:
//**********************************************************
package com.credu.system;

public class CompData
{
    private String comp;              // Comp Code = group + company + gpm + dept (+ part)
    private String groups;            // Group Code   = '00'(No-Group) ~ 'ZZ'
    private String company;           // Company Code = '00'(Group Code) ~ 'ZZ'
    private String gpm;               // 사업부  Code  = '00'(Group/Company) ~ 'ZZ'
    private String dept;              // 부서    Code  = '00'(Group~사업부) ~ 'ZZ'
    private String part;              // 파트    Code  = '00'(Group~ 부서) ~ 'ZZ'
    private String groupsnm;          // Group   명
    private String companynm;         // Company 명
    private String gpmnm;             // 사업부   명
    private String deptnm;            // 부서     명
    private String partnm;            // 파트     명
    private String compnm;            // Comp코드명
    private String isused;            // 사용여부(Y)
    private String legacygpm;         // 외부시스템 코드 I/F : 사업부코드
    private String legacydept;        // 외부시스템 코드 I/F : 부서코드
    private String legacypart;        // 외부시스템 코드 I/F : 파트코드
    private String luserid;           // 최종수정자
    private String ldate;             // 최종수정일

    private int comptype;             // Comp Type
    private int dispnum;              // 총게시물수
    private int total_page_count;     // 게시물총페이지수

    public void compData() {}

    public void   setComp(String comp)             { this.comp = comp;             }
    public String getComp()                        { return comp;                  }
    public void   setGroups(String groups)         { this.groups = groups;         }
    public String getGroups()                      { return groups;                }
    public void   setCompany(String company)       { this.company = company;       }
    public String getCompany()                     { return company;               }
    public void   setGpm(String gpm)               { this.gpm = gpm;               }
    public String getGpm()                         { return gpm;                   }
    public void   setDept(String dept)             { this.dept = dept;             }
    public String getDept()                        { return dept;                  }
    public void   setPart(String part)             { this.part = part;             }
    public String getPart()                        { return part;                  }
    public void   setGroupsnm(String groupsnm)     { this.groupsnm = groupsnm;     }
    public String getGroupsnm()                    { return groupsnm;              }
    public void   setCompanynm(String companynm)   { this.companynm = companynm;   }
    public String getCompanynm()                   { return companynm;             }
    public void   setGpmnm(String gpmnm)           { this.gpmnm = gpmnm;           }
    public String getGpmnm()                       { return gpmnm;                 }
    public void   setDeptnm(String deptnm)         { this.deptnm = deptnm;         }
    public String getDeptnm()                      { return deptnm;                }
    public void   setPartnm(String partnm)         { this.partnm = partnm;         }
    public String getPartnm()                      { return partnm;                }
    public void   setCompnm(String compnm)         { this.compnm = compnm;         }
    public String getCompnm()                      { return compnm;                }
    public void   setIsused(String isused)         { this.isused = isused;         }
    public String getIsused()                      { return isused;                }
    public void   setLegacygpm(String legacygpm)   { this.legacygpm = legacygpm;   }
    public String getLegacygpm()                   { return legacygpm;             }
    public void   setLegacydept(String legacydept) { this.legacydept = legacydept; }
    public String getLegacydept()                  { return legacydept;            }
    public void   setLegacypart(String legacypart) { this.legacypart = legacypart; }
    public String getLegacypart()                  { return legacypart;            }
    public void   setLuserid(String luserid)      { this.luserid= luserid;     }
    public String getLuserid()                    { return luserid;            }
    public void   setLdate(String ldate)          { this.ldate = ldate;        }
    public String getLdate()                      { return ldate;              }

    public void   setComptype(int comptype) { this.comptype = comptype; }
    public int    getComptype()             { return comptype;          }
    public void   setDispnum(int dispnum)   { this.dispnum = dispnum;   }
    public int    getDispnum()              { return dispnum;           }
    public void   setTotalPageCount(int total_page_count) { this.total_page_count = total_page_count; }
    public int    getTotalpagecount()                     { return total_page_count;                  }

}
