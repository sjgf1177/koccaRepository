//**********************************************************
//  1. ��      ��: Grcodeman DATA
//  2. ���α׷��� : GrcodemanData.java
//  3. ��      ��: ��������� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 5. 28
//  7. ��      ��:
//**********************************************************
package com.credu.system;

public class GrcodemanData
{
    private String userid;            // USER ID
    private String grcode;            // Grcode Code
    private String grcodenm;          // Grcode�ڵ��
    private String luserid;           // ����������
    private String ldate;             // ����������


    public GrcodemanData() {}

    public void   setUserid(String userid)     { this.userid= userid;      }
    public String getUserid()                  { return userid;            }
    public void   setGrcode(String grcode)     { this.grcode = grcode;     }
    public String getGrcode()                  { return grcode;            }
    public void   setGrcodenm(String grcodenm) { this.grcodenm = grcodenm; }
    public String getGrcodenm()                { return grcodenm;          }
    public void   setLuserid(String luserid)   { this.luserid= luserid;    }
    public String getLuserid()                 { return luserid;           }
    public void   setLdate(String ldate)       { this.ldate = ldate;       }
    public String getLdate()                   { return ldate;             }


}
