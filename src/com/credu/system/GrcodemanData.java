//**********************************************************
//  1. 제      목: Grcodeman DATA
//  2. 프로그램명 : GrcodemanData.java
//  3. 개      요: 과정담당자 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 5. 28
//  7. 수      정:
//**********************************************************
package com.credu.system;

public class GrcodemanData
{
    private String userid;            // USER ID
    private String grcode;            // Grcode Code
    private String grcodenm;          // Grcode코드명
    private String luserid;           // 최종수정자
    private String ldate;             // 최종수정일


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
