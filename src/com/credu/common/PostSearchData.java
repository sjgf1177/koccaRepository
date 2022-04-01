// **********************************************************
// 1. 제 목: 우편번호 Data
// 2. 프로그램명 : PostSearchData.java
// 3. 개 요: 우편번호 Data
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 정상진 2003. 7. 7
// 7. 수 정:
// **********************************************************

package com.credu.common;

public class PostSearchData {
    private String zipcode;
    private String sido;
    private String gugun;
    private String dong;
    private String bunji;

    public PostSearchData() {
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setSido(String sido) {
        this.sido = sido;
    }

    public String getSido() {
        return sido;
    }

    public void setGugun(String gugun) {
        this.gugun = gugun;
    }

    public String getGugun() {
        return gugun;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public String getDong() {
        return dong;
    }

    public void setBunji(String bunji) {
        this.bunji = bunji;
    }

    public String getBunji() {
        return bunji;
    }

}
