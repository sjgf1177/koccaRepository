// **********************************************************
// 1. 제 목: Faq 카테고리 Data
// 2. 프로그램명 : FaqCategoryData.java
// 3. 개 요: Faq 카테고리 data bean
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 정상진 2003. 7. 2
// 7. 수 정:
// **********************************************************
package com.credu.homepage;


/**
 * file name : FaqCategoryData.java date : 2003/7/13 programmer: 정상진 (puggjsj@hanmail.net) function : FaqCategory DATA
 */

public class FaqCategoryData {
    private String faqcategory; // 코드 ID
    private String faqcategorynm; // 코드명
    private int categorycnt;
    private String luserid; // 최종수정자
    private String ldate; // 최종수정일

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
