/**
*���ְ����ý����� ��
*<p>����:FaqCategoryData.java</p>
*<p>����:FAQī�װ������� ��</p>
*<p>Copyright: Copright(c)2004</p>
*<p>Company: VLC</p>
*@author ������
*@version 1.0
*/
package com.credu.cp;
import com.credu.library.*;



public class FaqCategoryData
{
    private String faqcategory;             // �ڵ� ID
    private String faqcategorynm;           // �ڵ��
    private String luserid;          // ����������
    private String ldate;            // ����������


    public FaqCategoryData() {}

    public void   setFaqCategory(String faqcategory)       { this.faqcategory = faqcategory;       }
    public String getFaqCategory()                  { return faqcategory;            }
    public void   setFaqCategorynm(String faqcategorynm)   { this.faqcategorynm = faqcategorynm;   }
    public String getFaqCategorynm()                { return faqcategorynm;          }
    public void   setLuserid(String luserid) { this.luserid = luserid; }
    public String getLuserid()               { return luserid;         }
    public void   setLdate(String ldate)     { this.ldate = ldate;     }
    public String getLdate()                 { return ldate;           }

}
