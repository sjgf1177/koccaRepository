// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunExampleData.java

package com.credu.multiestimate;


public class DamunExampleData
{

    public DamunExampleData()
    {
        subj = "";
        sulnum = 0;
        selnum = 0;
        selpoint = 0;
        seltext = "";
        scalename = "";
        replycnt = 0;
        replyrate = 0.0D;
        replysubjectcnt = 0;
        replyrelation1cnt = 0;
        replyrelation2cnt = 0;
        replyrelation3cnt = 0;
        replyGsubjectcnt = 0;
        replyGrelation1cnt = 0;
        replyGrelation2cnt = 0;
        replyGrelation3cnt = 0;
        pointcnt = 0;
        pointrate = 0.0D;
    }

    public int getSelnum()
    {
        return selnum;
    }

    public int getSelpoint()
    {
        return selpoint;
    }

    public String getSeltext()
    {
        return seltext;
    }

    public String getScalename()
    {
        return scalename;
    }

    public String getSubj()
    {
        return subj;
    }

    public int getSulnum()
    {
        return sulnum;
    }

    public void setSelnum(int i)
    {
        selnum = i;
    }

    public void setSelpoint(int i)
    {
        selpoint = i;
    }

    public void setSeltext(String string)
    {
        seltext = string;
    }

    public void setScalename(String string)
    {
        scalename = string;
    }

    public void setSubj(String string)
    {
        subj = string;
    }

    public void setSulnum(int i)
    {
        sulnum = i;
    }

    public int getReplycnt()
    {
        return replycnt;
    }

    public int getSubjectcnt()
    {
        return replysubjectcnt;
    }

    public int getRelation1cnt()
    {
        return replyrelation1cnt;
    }

    public int getRelation2cnt()
    {
        return replyrelation2cnt;
    }

    public int getRelation3cnt()
    {
        return replyrelation3cnt;
    }

    public int getGSubjectcnt()
    {
        return replyGsubjectcnt;
    }

    public int getGRelation1cnt()
    {
        return replyGrelation1cnt;
    }

    public int getGRelation2cnt()
    {
        return replyGrelation2cnt;
    }

    public int getGRelation3cnt()
    {
        return replyGrelation3cnt;
    }

    public double getReplyrate()
    {
        return replyrate;
    }

    public void setReplycnt(int i)
    {
        replycnt = i;
    }

    public void setSubjectcnt(int i)
    {
        replysubjectcnt = i;
    }

    public void setRelation1cnt(int i)
    {
        replyrelation1cnt = i;
    }

    public void setRelation2cnt(int i)
    {
        replyrelation2cnt = i;
    }

    public void setRelation3cnt(int i)
    {
        replyrelation3cnt = i;
    }

    public void setGSubjectcnt(int i)
    {
        replyGsubjectcnt = i;
    }

    public void setGRelation1cnt(int i)
    {
        replyGrelation1cnt = i;
    }

    public void setGRelation2cnt(int i)
    {
        replyGrelation2cnt = i;
    }

    public void setGRelation3cnt(int i)
    {
        replyGrelation3cnt = i;
    }

    public void setReplyrate(double d)
    {
        replyrate = d;
    }

    public int getPointcnt()
    {
        return pointcnt;
    }

    public double getPointrate()
    {
        return pointrate;
    }

    public void setPointcnt(int i)
    {
        pointcnt = i;
    }

    public void setPointrate(double d)
    {
        pointrate = d;
    }

    private String subj;
    private int sulnum;
    private int selnum;
    private int selpoint;
    private String seltext;
    private String scalename;
    private int replycnt;
    private double replyrate;
    private int replysubjectcnt;
    private int replyrelation1cnt;
    private int replyrelation2cnt;
    private int replyrelation3cnt;
    private int replyGsubjectcnt;
    private int replyGrelation1cnt;
    private int replyGrelation2cnt;
    private int replyGrelation3cnt;
    private int pointcnt;
    private double pointrate;
}
