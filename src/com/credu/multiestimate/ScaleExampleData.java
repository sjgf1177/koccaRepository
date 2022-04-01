// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScaleExampleData.java

package com.credu.multiestimate;


public class ScaleExampleData
{

    public ScaleExampleData()
    {
        scalecode = 0;
        selnum = 0;
        selpoint = 0;
        seltext = "";
        replycnt = 0;
        replyrate = 0.0D;
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

    public int getScalecode()
    {
        return scalecode;
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

    public void setScalecode(int i)
    {
        scalecode = i;
    }

    public int getReplycnt()
    {
        return replycnt;
    }

    public double getReplyrate()
    {
        return replyrate;
    }

    public void setReplycnt(int i)
    {
        replycnt = i;
    }

    public void setReplyrate(double d)
    {
        replyrate = d;
    }

    private int scalecode;
    private int selnum;
    private int selpoint;
    private String seltext;
    private int replycnt;
    private double replyrate;
}
