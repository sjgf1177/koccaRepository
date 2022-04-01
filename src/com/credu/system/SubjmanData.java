//**********************************************************
//  1. 제      목: Subjman DATA
//  2. 프로그램명 : SubjmanData.java
//  3. 개      요: 과정담당자 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 5. 28
//  7. 수      정:
//**********************************************************
package com.credu.system;
import com.credu.library.*;

public class SubjmanData
{
    private String userid;            // USER ID
    private String subj;              // Subj Code
    private String subjnm;            // Subj코드명
    private String luserid;           // 최종수정자
    private String ldate;             // 최종수정일


    public SubjmanData() {}

    public void   setUserid(String userid)   { this.userid= userid;   }
    public String getUserid()                { return userid;         }
    public void   setSubj(String subj)       { this.subj = subj;      }
    public String getSubj()                  { return subj;           }
    public void   setSubjnm(String subjnm)   { this.subjnm = subjnm;  }
    public String getSubjnm()                { return subjnm;         }
    public void   setLuserid(String luserid) { this.luserid= luserid; }
    public String getLuserid()               { return luserid;        }
    public void   setLdate(String ldate)     { this.ldate = ldate;    }
    public String getLdate()                 { return ldate;          }


}
