//**********************************************************
//  1. ��      ��: Compman DATA
//  2. ���α׷��� : CompmanData.java
//  3. ��      ��: �μ������ data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 5. 28
//  7. ��      ��:
//**********************************************************
package com.credu.system;

public class CompmanData
{
    private String userid;            // USER ID
    private String comp;              // Comp Code = group + company + gpm + dept (+ part)
    private String groupsnm;          // Group   ��
    private String companynm;         // Company ��
    private String gpmnm;             // �����   ��
    private String deptnm;            // �μ�     ��
    private String partnm;            // ��Ʈ     ��
    private String compnm;            // Comp�ڵ��
    private String luserid;           // ����������
    private String ldate;             // ����������


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
