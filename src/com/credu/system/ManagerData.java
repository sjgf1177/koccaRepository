//**********************************************************
//  1. ��      ��: ������ Data
//  2. ���α׷��� : ManagerData.java
//  3. ��      ��: ������ data bean
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 11. 10
//  7. ��      ��:
//**********************************************************

package com.credu.system;

/**
 * file name : ManagerData.java
 * date      : 2004/11/ 10
 * programmer: ������ (swkang@credu.com)
 * function  : MANAGER DATA
 */

public class ManagerData
{
    private String userid;           // ������ ID
    private String gadmin;           // ���� ID
    private String gadminnm;         // ���Ѹ�
    private String comp;             // ���ȸ��
    private String isdeleted;        // ��������
    private String fmon;             // Ȱ���Ⱓ - ���ۿ�
    private String tmon;             // Ȱ���Ⱓ - �����
    private String commented;        // ����
    private String luserid;          // ����������
    private String ldate;            // ����������

    private String cono;             // ���
    private String name;             // �̸�
	private String jikwi;            // ����    
    private String jikwinm;          // ����
    private String companynm;        // ȸ���
    private String gpmnm;            // ����θ�
    private String deptnm;           // �μ���
	private String compnm;           // ȸ��� + / + ����θ� + / + �μ���

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
