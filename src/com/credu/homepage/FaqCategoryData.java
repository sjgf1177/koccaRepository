// **********************************************************
// 1. �� ��: Faq ī�װ� Data
// 2. ���α׷��� : FaqCategoryData.java
// 3. �� ��: Faq ī�װ� data bean
// 4. ȯ ��: JDK 1.3
// 5. �� ��: 1.0
// 6. �� ��: ������ 2003. 7. 2
// 7. �� ��:
// **********************************************************
package com.credu.homepage;


/**
 * file name : FaqCategoryData.java date : 2003/7/13 programmer: ������ (puggjsj@hanmail.net) function : FaqCategory DATA
 */

public class FaqCategoryData {
    private String faqcategory; // �ڵ� ID
    private String faqcategorynm; // �ڵ��
    private int categorycnt;
    private String luserid; // ����������
    private String ldate; // ����������

    public FaqCategoryData() {
    }

    public void setFaqCategory(String faqcategory) {
        this.faqcategory = faqcategory;
    }

    public String getFaqCategory() {
        return faqcategory;
    }

    public void setFaqCategorynm(String faqcategorynm) {
        this.faqcategorynm = faqcategorynm;
    }

    public String getFaqCategorynm() {
        return faqcategorynm;
    }

    public void setLuserid(String luserid) {
        this.luserid = luserid;
    }

    public String getLuserid() {
        return luserid;
    }

    public void setLdate(String ldate) {
        this.ldate = ldate;
    }

    public String getLdate() {
        return ldate;
    }

    public int getCategorycnt() {
        return categorycnt;
    }

    public void setCategorycnt(int categorycnt) {
        this.categorycnt = categorycnt;
    }

}
