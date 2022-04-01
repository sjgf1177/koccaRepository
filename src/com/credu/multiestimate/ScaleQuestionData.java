// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScaleQuestionData.java

package com.credu.multiestimate;


public class ScaleQuestionData
{

    public ScaleQuestionData()
    {
        scalecode = 0;
        grcode = "";
        s_gubun = "";
        scaletype = "";
        scalename = "";
    }

    public void setScalecode(int i)
    {
        scalecode = i;
    }

    public int getScalecode()
    {
        return scalecode;
    }

    public void setGrcode(String string)
    {
        grcode = string;
    }

    public String getGrcode()
    {
        return grcode;
    }

    public void setS_gubun(String string)
    {
        s_gubun = string;
    }

    public String getS_gubun()
    {
        return s_gubun;
    }

    public void setScaletype(String string)
    {
        scaletype = string;
    }

    public String getScaletype()
    {
        return scaletype;
    }

    public void setScalename(String string)
    {
        scalename = string;
    }

    public String getScalename()
    {
        return scalename;
    }

    private int scalecode;
    private String grcode;
    private String s_gubun;
    private String scaletype;
    private String scalename;
}
