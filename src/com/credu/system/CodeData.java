//**********************************************************
//  1. ��      ��: CODE DATA
//  2. ���α׷��� : CodeData.java
//  3. ��      ��: �ڵ� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 5. 28
//  7. ��      ��: 
//**********************************************************
package com.credu.system;


public class CodeData {
    private String gubun; // �ڵ屸��
    private String gubunnm; // �ڵ屸�и�
    private String code; // �ڵ� ID
    private String codenm; // �ڵ��
    private String upper; // �����ڵ� ID
    private String parent; // �ֻ����ڵ� ID
    private String issystem; // �����ڵ������Ͽ���    
    private String luserid; // ����������
    private String ldate; // ����������

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
