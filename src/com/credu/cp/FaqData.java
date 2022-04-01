//**********************************************************
//  1. 제      목: Faq Data
//  2. 프로그램명 : FaqData.java
//  3. 개      요: Faq data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 22
//  7. 수      정:
//**********************************************************
package com.credu.cp;
import com.credu.library.*;

/**
 * file name : FaqData.java
 * date      : 2003/5/28
 * programmer: 정상진 (puggjsj@hanmail.net)
 * function  : FAQ DATA
 */
public class FaqData
{

    private String faqcategory;             // gategory code
    private String title;            // 타이틀
    private String contents;         // 내용
    private String indate;           // 등록일
    private String luserid;          // 최종수정자
    private String ldate;            // 최종수정일

    private int fnum;                // FAQ 번호


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
