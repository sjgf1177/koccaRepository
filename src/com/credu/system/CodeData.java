//**********************************************************
//  1. 제      목: CODE DATA
//  2. 프로그램명 : CodeData.java
//  3. 개      요: 코드 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 5. 28
//  7. 수      정: 
//**********************************************************
package com.credu.system;


public class CodeData {
    private String gubun; // 코드구분
    private String gubunnm; // 코드구분명
    private String code; // 코드 ID
    private String codenm; // 코드명
    private String upper; // 상위코드 ID
    private String parent; // 최상위코드 ID
    private String issystem; // 하위코드수동등록여부    
    private String luserid; // 최종수정자
    private String ldate; // 최종수정일

    private int maxlevel; // MAXSLEVEL
    private int levels; // LEVELS

    public CodeData() {
    }

    public void setGubun(String gubun) {
        this.gubun = gubun;
    }

    public String getGubun() {
        return gubun;
    }

    public void setGubunnm(String gubunnm) {
        this.gubunnm = gubunnm;
    }

    public String getGubunnm() {
        return gubunnm;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCodenm(String codenm) {
        this.codenm = codenm;
    }

    public String getCodenm() {
        return codenm;
    }

    public void setUpper(String upper) {
        this.upper = upper;
    }

    public String getUpper() {
        return upper;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public String getParent() {
        return parent;
    }

    public void setIssystem(String issystem) {
        this.issystem = issystem;
    }

    public String getIssystem() {
        return issystem;
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

    public void setMaxlevel(int maxlevel) {
        this.maxlevel = maxlevel;
    }

    public int getMaxlevel() {
        return maxlevel;
    }

    public void setLevels(int levels) {
        this.levels = levels;
    }

    public int getLevels() {
        return levels;
    }

}
