// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunQuestionData.java

package com.credu.multiestimate;

import java.util.Vector;

public class DamunQuestionData
{

    public DamunQuestionData()
    {
        subj = "";
        grcode = "";
        sulnum = 0;
        distcode = "";
        sultype = "";
        sultext = "";
        selcount = 0;
        selmax = 0;
        sulreturn = "";
        scalecode = 0;
        sultypenm = "";
        distcodenm = "";
        answer = new Vector();
        r1answer = new Vector();
        r2answer = new Vector();
        r3answer = new Vector();
        c_answer = new Vector();
        c_r1answer = new Vector();
        c_r2answer = new Vector();
        c_r3answer = new Vector();
    }

    public void setSubj(String string)
    {
        subj = string;
    }

    public String getSubj()
    {
        return subj;
    }

    public void setGrcode(String string)
    {
        grcode = string;
    }

    public String getGrcode()
    {
        return grcode;
    }

    public void setSulnum(int i)
    {
        sulnum = i;
    }

    public int getSulnum()
    {
        return sulnum;
    }

    public void setDistcode(String string)
    {
        distcode = string;
    }

    public void setSubjectAnswer(Vector v)
    {
        answer = v;
    }

    public void setR1Answer(Vector v)
    {
        r1answer = v;
    }

    public void setR2Answer(Vector v)
    {
        r2answer = v;
    }

    public void setR3Answer(Vector v)
    {
        r3answer = v;
    }

    public void setComplexAnswer(Vector v)
    {
        c_answer = v;
    }

    public void setR1ComplexAnswer(Vector v)
    {
        c_r1answer = v;
    }

    public void setR2ComplexAnswer(Vector v)
    {
        c_r2answer = v;
    }

    public void setR3ComplexAnswer(Vector v)
    {
        c_r3answer = v;
    }

    public Vector getComplexAnswer()
    {
        return c_answer;
    }

    public Vector getR1ComplexAnswer()
    {
        return c_r1answer;
    }

    public Vector getR2ComplexAnswer()
    {
        return c_r2answer;
    }

    public Vector getR3ComplexAnswer()
    {
        return c_r3answer;
    }

    public Vector getSubjectAnswer()
    {
        return answer;
    }

    public Vector getR1Answer()
    {
        return r1answer;
    }

    public Vector getR2Answer()
    {
        return r2answer;
    }

    public Vector getR3Answer()
    {
        return r3answer;
    }

    public String getDistcode()
    {
        return distcode;
    }

    public void setSultype(String string)
    {
        sultype = string;
    }

    public String getSultype()
    {
        return sultype;
    }

    public void setSultext(String string)
    {
        sultext = string;
    }

    public String getSultext()
    {
        return sultext;
    }

    public void setSelcount(int i)
    {
        selcount = i;
    }

    public int getSelcount()
    {
        return selcount;
    }

    public void setSelmax(int i)
    {
        selmax = i;
    }

    public int getSelmax()
    {
        return selmax;
    }

    public void setSulreturn(String string)
    {
        sulreturn = string;
    }

    public String getSulreturn()
    {
        return sulreturn;
    }

    public void setScalecode(int i)
    {
        scalecode = i;
    }

    public int getScalecode()
    {
        return scalecode;
    }

    public void setSultypenm(String string)
    {
        sultypenm = string;
    }

    public String getSultypenm()
    {
        return sultypenm;
    }

    public void setDistcodenm(String string)
    {
        distcodenm = string;
    }

    public String getDistcodenm()
    {
        return distcodenm;
    }

    private String subj;
    private String grcode;
    private int sulnum;
    private String distcode;
    private String sultype;
    private String sultext;
    private int selcount;
    private int selmax;
    private String sulreturn;
    private int scalecode;
    private String sultypenm;
    private String distcodenm;
    private Vector answer;
    private Vector r1answer;
    private Vector r2answer;
    private Vector r3answer;
    private Vector c_answer;
    private Vector c_r1answer;
    private Vector c_r2answer;
    private Vector c_r3answer;
}
