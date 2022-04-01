// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunQuestionExampleData.java

package com.credu.multiestimate;

import java.util.*;

// Referenced classes of package com.credu.multiestimate:
//            DamunQuestionData, DamunExampleData

public class DamunQuestionExampleData
{

    public DamunQuestionExampleData()
    {
        Question = new DamunQuestionData();
        ExampleDataList = new Hashtable();
    }

    public String getSubj()
    {
        return Question.getSubj();
    }

    public String getGrcode()
    {
        return Question.getGrcode();
    }

    public int getSulnum()
    {
        return Question.getSulnum();
    }

    public String getDistcode()
    {
        return Question.getDistcode();
    }

    public String getSultype()
    {
        return Question.getSultype();
    }

    public String getSultext()
    {
        return Question.getSultext();
    }

    public String getSulreturn()
    {
        return Question.getSulreturn();
    }

    public String getSultypenm()
    {
        return Question.getSultypenm();
    }

    public String getDistcodenm()
    {
        return Question.getDistcodenm();
    }

    public int getSelmax()
    {
        return Question.getSelmax();
    }

    public int getScalecode()
    {
        return Question.getScalecode();
    }

    public Vector getSubjectAnswer()
    {
        return Question.getSubjectAnswer();
    }

    public Vector getR1Answer()
    {
        return Question.getR1Answer();
    }

    public Vector getR2Answer()
    {
        return Question.getR2Answer();
    }

    public Vector getR3Answer()
    {
        return Question.getR3Answer();
    }

    public Vector getComplexAnswer()
    {
        return Question.getComplexAnswer();
    }

    public Vector getR1ComplexAnswer()
    {
        return Question.getR1ComplexAnswer();
    }

    public Vector getR2ComplexAnswer()
    {
        return Question.getR2ComplexAnswer();
    }

    public Vector getR3ComplexAnswer()
    {
        return Question.getR3ComplexAnswer();
    }

    public void setSubj(String subj)
    {
        Question.setSubj(subj);
    }

    public void setGrcode(String grcode)
    {
        Question.setGrcode(grcode);
    }

    public void setSulnum(int sulnum)
    {
        Question.setSulnum(sulnum);
    }

    public void setDistcode(String distcode)
    {
        Question.setDistcode(distcode);
    }

    public void setSultype(String sultype)
    {
        Question.setSultype(sultype);
    }

    public void setSultext(String sultext)
    {
        Question.setSultext(sultext);
    }

    public void setSulreturn(String sulreturn)
    {
        Question.setSulreturn(sulreturn);
    }

    public void setDistcodenm(String distcodenm)
    {
        Question.setDistcodenm(distcodenm);
    }

    public void setSultypenm(String sultypenm)
    {
        Question.setSultypenm(sultypenm);
    }

    public void setSelmax(int selmax)
    {
        Question.setSelmax(selmax);
    }

    public void setScalecode(int scalecode)
    {
        Question.setScalecode(scalecode);
    }

    public void setSubjectAnswer(Vector answer)
    {
        Question.setSubjectAnswer(answer);
    }

    public void setR1Answer(Vector answer)
    {
        Question.setR1Answer(answer);
    }

    public void setR2Answer(Vector answer)
    {
        Question.setR2Answer(answer);
    }

    public void setR3Answer(Vector answer)
    {
        Question.setR3Answer(answer);
    }

    public void setComplexAnswer(Vector answer)
    {
        Question.setComplexAnswer(answer);
    }

    public void setR1ComplexAnswer(Vector answer)
    {
        Question.setR1ComplexAnswer(answer);
    }

    public void setR2ComplexAnswer(Vector answer)
    {
        Question.setR2ComplexAnswer(answer);
    }

    public void setR3ComplexAnswer(Vector answer)
    {
        Question.setR3ComplexAnswer(answer);
    }

    public DamunExampleData get(int selnum)
    {
        return (DamunExampleData)ExampleDataList.get(String.valueOf(selnum));
    }

    public void add(DamunExampleData exampledata)
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
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getSelnum();
        return i;
    }

    public String getSeltext(int selnum)
    {
        String string = "";
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            string = exampledata.getSeltext();
        return string;
    }

    public int getReplycnt(int selnum)
    {
        int i = 0;
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getReplycnt();
        return i;
    }

    public double getReplyrate(int selnum)
    {
        double d = 0.0D;
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            d = exampledata.getReplycnt();
        return d;
    }

    public void setReplycnt(int selnum, int i)
    {
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setReplycnt(i);
    }

    public void setReplyrate(int selnum, double d)
    {
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setReplyrate(d);
    }

    public int getSubjectcnt(int selnum)
    {
        int i = 0;
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getSubjectcnt();
        return i;
    }

    public void setSubjectcnt(int selnum, int i)
    {
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setSubjectcnt(i);
    }

    public int getRelation1cnt(int selnum)
    {
        int i = 0;
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getRelation1cnt();
        return i;
    }

    public void setRelation1cnt(int selnum, int i)
    {
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setRelation1cnt(i);
    }

    public int getRelation2cnt(int selnum)
    {
        int i = 0;
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getRelation2cnt();
        return i;
    }

    public void setRelation2cnt(int selnum, int i)
    {
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setRelation2cnt(i);
    }

    public int getRelation3cnt(int selnum)
    {
        int i = 0;
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getRelation3cnt();
        return i;
    }

    public void setRelation3cnt(int selnum, int i)
    {
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setRelation3cnt(i);
    }

    public int getGSubjectcnt(int selnum)
    {
        int i = 0;
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getGSubjectcnt();
        return i;
    }

    public void setGSubjectcnt(int selnum, int i)
    {
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setGSubjectcnt(i);
    }

    public int getGRelation1cnt(int selnum)
    {
        int i = 0;
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getGRelation1cnt();
        return i;
    }

    public void setGRelation1cnt(int selnum, int i)
    {
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setGRelation1cnt(i);
    }

    public int getGRelation2cnt(int selnum)
    {
        int i = 0;
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getGRelation2cnt();
        return i;
    }

    public void setGRelation2cnt(int selnum, int i)
    {
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setGRelation2cnt(i);
    }

    public int getGRelation3cnt(int selnum)
    {
        int i = 0;
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getGRelation3cnt();
        return i;
    }

    public void setGRelation3cnt(int selnum, int i)
    {
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setGRelation3cnt(i);
    }

    public int getPointcnt(int selnum)
    {
        int i = 0;
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            i = exampledata.getPointcnt();
        return i;
    }

    public double getPointrate(int selnum)
    {
        double d = 0.0D;
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            d = exampledata.getPointcnt();
        return d;
    }

    public void setPointcnt(int selnum, int i)
    {
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setPointcnt(i);
    }

    public void setPointrate(int selnum, double d)
    {
        DamunExampleData exampledata = get(selnum);
        if(exampledata != null)
            exampledata.setPointrate(d);
    }

    public void IncreasReplyCount(int answer)
    {
        DamunExampleData exampledata = get(answer);
        if(exampledata != null)
            exampledata.setReplycnt(exampledata.getReplycnt() + 1);
    }

    public void IncreasSubjectCount(int answer)
    {
        DamunExampleData exampledata = get(answer);
        if(exampledata != null)
            exampledata.setSubjectcnt(exampledata.getSubjectcnt() + 1);
    }

    public void IncreasRelation1Count(int answer)
    {
        DamunExampleData exampledata = get(answer);
        if(exampledata != null)
            exampledata.setRelation1cnt(exampledata.getRelation1cnt() + 1);
    }

    public void IncreasRelation2Count(int answer)
    {
        DamunExampleData exampledata = get(answer);
        if(exampledata != null)
            exampledata.setRelation2cnt(exampledata.getRelation2cnt() + 1);
    }

    public void IncreasRelation3Count(int answer)
    {
        DamunExampleData exampledata = get(answer);
        if(exampledata != null)
            exampledata.setRelation3cnt(exampledata.getRelation3cnt() + 1);
    }

    public void IncreasGSubjectCount(int answer)
    {
        DamunExampleData exampledata = get(answer);
        if(exampledata != null)
            exampledata.setGSubjectcnt(exampledata.getGSubjectcnt() + 1);
    }

    public void IncreasGRelation1Count(int answer)
    {
        DamunExampleData exampledata = get(answer);
        if(exampledata != null)
            exampledata.setGRelation1cnt(exampledata.getGRelation1cnt() + 1);
    }

    public void IncreasGRelation2Count(int answer)
    {
        DamunExampleData exampledata = get(answer);
        if(exampledata != null)
            exampledata.setGRelation2cnt(exampledata.getGRelation2cnt() + 1);
    }

    public void IncreasGRelation3Count(int answer)
    {
        DamunExampleData exampledata = get(answer);
        if(exampledata != null)
            exampledata.setGRelation3cnt(exampledata.getGRelation3cnt() + 1);
    }

    public void IncreasPointCount(int answer)
    {
        DamunExampleData exampledata = get(answer);
        if(exampledata != null)
            exampledata.setPointcnt(exampledata.getPointcnt() + 1);
    }

    public void ComputeRate()
    {
        DamunExampleData data = null;
        Enumeration e1 = ExampleDataList.elements();
        int v_sum;
        for(v_sum = 0; e1.hasMoreElements(); v_sum += data.getReplycnt())
            data = (DamunExampleData)e1.nextElement();

        if(v_sum > 0)
        {
            for(Enumeration e2 = ExampleDataList.elements(); e2.hasMoreElements(); data.setReplyrate((double)Math.round(((double)data.getReplycnt() / (double)v_sum) * 100D * 100D) / 100D))
                data = (DamunExampleData)e2.nextElement();

        }
    }

    public void ComputePoint()
    {
        DamunExampleData data = null;
        Enumeration e1 = ExampleDataList.elements();
        int v_sum;
        for(v_sum = 0; e1.hasMoreElements(); v_sum += data.getPointcnt())
            data = (DamunExampleData)e1.nextElement();

        if(v_sum > 0)
        {
            for(Enumeration e2 = ExampleDataList.elements(); e2.hasMoreElements(); data.setPointrate(Math.round((double)data.getPointcnt() / (double)v_sum)))
                data = (DamunExampleData)e2.nextElement();

        }
    }

    public Enumeration elements()
    {
        return ExampleDataList.elements();
    }

    private DamunQuestionData Question;
    private Hashtable ExampleDataList;
}
