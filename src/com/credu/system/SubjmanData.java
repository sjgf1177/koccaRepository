//**********************************************************
//  1. ��      ��: Subjman DATA
//  2. ���α׷��� : SubjmanData.java
//  3. ��      ��: ��������� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 5. 28
//  7. ��      ��:
//**********************************************************
package com.credu.system;
import com.credu.library.*;

public class SubjmanData
{
    private String userid;            // USER ID
    private String subj;              // Subj Code
    private String subjnm;            // Subj�ڵ��
    private String luserid;           // ����������
    private String ldate;             // ����������


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
