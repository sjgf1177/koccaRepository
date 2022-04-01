//**********************************************************
//  1. ��      ��: Faq Data
//  2. ���α׷��� : FaqData.java
//  3. ��      ��: Faq data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 22
//  7. ��      ��:
//**********************************************************
package com.credu.cp;
import com.credu.library.*;

/**
 * file name : FaqData.java
 * date      : 2003/5/28
 * programmer: ������ (puggjsj@hanmail.net)
 * function  : FAQ DATA
 */
public class FaqData
{

    private String faqcategory;             // gategory code
    private String title;            // Ÿ��Ʋ
    private String contents;         // ����
    private String indate;           // �����
    private String luserid;          // ����������
    private String ldate;            // ����������

    private int fnum;                // FAQ ��ȣ


    public FaqData() {}

    public void   setaqcategory(String FaqCategory)         { this.faqcategory = faqcategory;         }
    public String getFaqCategory()                    { return faqcategory;              }
    public void   setTitle(String title)       { this.title = title;       }
    public String getTitle()                   { return title;             }
    public void   setIndate(String indate)     { this.indate = indate;     }
    public String getIndate()                  { return indate;            }
    public void   setContents(String contents) { this.contents = contents; }
    public String getContents()                { return contents;          }
    public void   setLuserid(String luserid)   { this.luserid = luserid;   }
    public String getLuserid()                 { return luserid;           }
    public void   setLdate(String ldate)       { this.ldate = ldate;       }
    public String getLdate()                   { return ldate;             }

    public void   setFnum(int fnum)   { this.fnum = fnum;  }
    public int    getFnum()           { return fnum;       }

}
