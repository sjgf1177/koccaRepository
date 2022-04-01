// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScaleQuestionExampleData.java

package com.credu.multiestimate;

import java.util.Enumeration;
import java.util.Hashtable;

// Referenced classes of package com.credu.multiestimate:
//            ScaleQuestionData, ScaleExampleData

public class ScaleQuestionExampleData
{

    public ScaleQuestionExampleData()
    {
        Question = new ScaleQuestionData();
        ExampleDataList = new Hashtable();
    }

    public int getScalecode()
    {
        return Question.getScalecode();
    }

    public String getGrcode()
    {
        return Question.getGrcode();
    }

    public String getS_gubun()
    {
        return Question.getS_gubun();
    }

    public String getScaletype()
    {
        return Question.getScaletype();
    }

    public String getScalename()
    {
        return Question.getScalename();
    }

    public void setScalecode(int scalecode)
    {
        Question.setScalecode(scalecode);
    }

    public void setGrcode(String grcode)
    {
        Question.setGrcode(grcode);
    }

    public void setS_gubun(String s_gubun)
    {
        Question.setS_gubun(s_gubun);
    }

    public void setScaletype(String scaletype)
    {
        Question.setScaletype(scaletype);
    }

    public void setScalename(String scalename)
    {
        Question.setScalename(scalename);
    }

    public ScaleExampleData get(int selnum)
    {
        return (ScaleExampleData)ExampleDataList.get(String.valueOf(selnum));
    }

    public void add(ScaleExampleData exampledata)
    {
        ExampleDataList.put(String.valueOf(exampledata.getSelnum()), exampledata);
    }

    public void remove(int selnum)
    {
        ExampleDataList.remove(String.valueOf(selnum));
    }

    public void clear()
    {
        ExampleDataList.clear();
    }

    public int size()
    {
        return ExampleDataList.size();
    }

    public int getSelnum(int selnum)
    {
        int i = 0;
        ScaleExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getSelnum();
        return i;
    }

    public int getSelpoint(int selnum)
    {
        int i = 0;
        ScaleExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getSelpoint();
        return i;
    }

    public String getSeltext(int selnum)
    {
        String string = "";
        ScaleExampleData exampledata = get(selnum);
        if(exampledata != null)
            string = exampledata.getSeltext();
        return string;
    }

    public int getReplycnt(int selnum)
    {
        int i = 0;
        ScaleExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getReplycnt();
        return i;
    }

    public double getReplyrate(int selnum)
    {
        double d = 0.0D;
        ScaleExampleData exampledata = get(selnum);
        if(exampledata != null)
            d = exampledata.getReplycnt();
        return d;
    }

    public void setReplycnt(int selnum, int i)
    {
        ScaleExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setReplycnt(i);
    }

    public void getReplyrate(int selnum, double d)
    {
        ScaleExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setReplyrate(d);
    }

    public void IncreasReplyCount(int answer)
    {
        ScaleExampleData exampledata = get(answer);
        if(exampledata != null)
            exampledata.setReplycnt(exampledata.getReplycnt() + 1);
    }

    public void ComputeRate()
    {
        ScaleExampleData data = null;
        Enumeration e1 = ExampleDataList.elements();
        int v_sum;
        for(v_sum = 0; e1.hasMoreElements(); v_sum += data.getReplycnt())
            data = (ScaleExampleData)e1.nextElement();

        if(v_sum > 0)
        {
            for(Enumeration e2 = ExampleDataList.elements(); e2.hasMoreElements(); data.setReplyrate((double)Math.round(((double)data.getReplycnt() / (double)v_sum) * 100D * 100D) / 100D))
                data = (ScaleExampleData)e2.nextElement();

        }
    }

    public Enumeration elements()
    {
        return ExampleDataList.elements();
    }

    private ScaleQuestionData Question;
    private Hashtable ExampleDataList;
}
