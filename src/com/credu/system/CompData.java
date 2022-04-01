//**********************************************************
//  1. ��      ��: COMP DATA
//  2. ���α׷��� : CompData.java
//  3. ��      ��: ȸ�� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 5. 28
//  7. ��      ��:
//**********************************************************
package com.credu.system;

public class CompData
{
    private String comp;              // Comp Code = group + company + gpm + dept (+ part)
    private String groups;            // Group Code   = '00'(No-Group) ~ 'ZZ'
    private String company;           // Company Code = '00'(Group Code) ~ 'ZZ'
    private String gpm;               // �����  Code  = '00'(Group/Company) ~ 'ZZ'
    private String dept;              // �μ�    Code  = '00'(Group~�����) ~ 'ZZ'
    private String part;              // ��Ʈ    Code  = '00'(Group~ �μ�) ~ 'ZZ'
    private String groupsnm;          // Group   ��
    private String companynm;         // Company ��
    private String gpmnm;             // �����   ��
    private String deptnm;            // �μ�     ��
    private String partnm;            // ��Ʈ     ��
    private String compnm;            // Comp�ڵ��
    private String isused;            // ��뿩��(Y)
    private String legacygpm;         // �ܺνý��� �ڵ� I/F : ������ڵ�
    private String legacydept;        // �ܺνý��� �ڵ� I/F : �μ��ڵ�
    private String legacypart;        // �ܺνý��� �ڵ� I/F : ��Ʈ�ڵ�
    private String luserid;           // ����������
    private String ldate;             // ����������

    private int comptype;             // Comp Type
    private int dispnum;              // �ѰԽù���
    private int total_page_count;     // �Խù�����������

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
