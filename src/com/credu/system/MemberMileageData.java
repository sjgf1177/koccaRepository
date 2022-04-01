//**********************************************************
//  1. ��      ��: MEMBERMILEAGE  DATA
//  2. ���α׷��� : MemberMileageData.java
//  3. ��      ��: ���ϸ��� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 5. 28
//  7. ��      ��: 
//**********************************************************
package com.credu.system;
import com.credu.library.*;

/**
 * file name : MemberMileageData.java
 * date      : 2003/5/28
 * programmer: ������ (puggjsj@hanmail.net)
 * function  : Poll DATA
 */
public class MemberMileageData
{
    private String userid;            // USER ID
    private String mileagecode;       // mileage code
    private String mileagename;       // mileage name
    private String sdesc;             // description
    private String luserid;           // ����������
    private String ldate;             // ����������

    private String cono;             // ���
    private String name;             // �̸�
	private String jikwi;            // ����       
    private String jikwinm;          // ����
    private String companynm;        // ȸ���
    private String gpmnm;            // ����θ�
    private String deptnm;           // �μ���
	private String compnm;           // ȸ��� + / + ����θ� + / + �μ���
	
    private int    point;              // ����


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
