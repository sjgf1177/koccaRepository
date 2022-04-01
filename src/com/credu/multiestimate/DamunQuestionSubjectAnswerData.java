// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunQuestionSubjectAnswerData.java

package com.credu.multiestimate;

import java.util.Hashtable;

// Referenced classes of package com.credu.multiestimate:
//            DamunQuestionData, DamunSubjectAnswerData

public class DamunQuestionSubjectAnswerData
{

    public DamunQuestionSubjectAnswerData()
    {
        Question = new DamunQuestionData();
        SubjectAnswerDataList = new Hashtable();
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

    public String getSultypenm()
    {
        return Question.getSultypenm();
    }

    public String getDistcodenm()
    {
        return Question.getDistcodenm();
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

    public void setSultypenm(String sultype)
    {
        Question.setSultypenm(sultype);
    }

    public void setDistcodenm(String distcode)
    {
        Question.setDistcodenm(distcode);
    }

    public DamunSubjectAnswerData get(int index)
    {
        return (DamunSubjectAnswerData)SubjectAnswerDataList.get(String.valueOf(index));
    }

    public void add(DamunSubjectAnswerData answerdata)
    {
        SubjectAnswerDataList.put(String.valueOf(SubjectAnswerDataList.size()), answerdata);
    }

    public void remove(int index)
    {
        SubjectAnswerDataList.remove(String.valueOf(index));
    }

    public void clear()
    {
        SubjectAnswerDataList.clear();
    }

    public int size()
    {
        return SubjectAnswerDataList.size();
    }

    public String getUserid(int index)
    {
        String string = "";
        DamunSubjectAnswerData answerdata = get(index);
        if(answerdata != null)
            string = answerdata.getUserid();
        return string;
    }

    public String getName(int index)
    {
        String string = "";
        DamunSubjectAnswerData answerdata = get(index);
        if(answerdata != null)
            string = answerdata.getName();
        return string;
    }

    public String getAnstext(int index)
    {
        String string = "";
        DamunSubjectAnswerData answerdata = get(index);
        if(answerdata != null)
            string = answerdata.getAnstext();
        return string;
    }

    private DamunQuestionData Question;
    private Hashtable SubjectAnswerDataList;
}
