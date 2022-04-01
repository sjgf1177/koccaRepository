// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunSubjectAnswerData.java

package com.credu.multiestimate;


public class DamunSubjectAnswerData
{

    public DamunSubjectAnswerData()
    {
    }

    public String getAnstext()
    {
        return anstext;
    }

    public String getName()
    {
        return name;
    }

    public String getSubj()
    {
        return subj;
    }

    public int getSulnum()
    {
        return sulnum;
    }

    public String getUserid()
    {
        return userid;
    }

    public void setAnstext(String string)
    {
        anstext = string;
    }

    public void setName(String string)
    {
        name = string;
    }

    public void setSubj(String string)
    {
        subj = string;
    }

    public void setSulnum(int i)
    {
        sulnum = i;
    }

    public void setUserid(String string)
    {
        userid = string;
    }

    private String subj;
    private int sulnum;
    private String userid;
    private String name;
    private String anstext;
}
