// **********************************************************
// 1. �� ��: �����ȣ Data
// 2. ���α׷��� : PostSearchData.java
// 3. �� ��: �����ȣ Data
// 4. ȯ ��: JDK 1.3
// 5. �� ��: 1.0
// 6. �� ��: ������ 2003. 7. 7
// 7. �� ��:
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
