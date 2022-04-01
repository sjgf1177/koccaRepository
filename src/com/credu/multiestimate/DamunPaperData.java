// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunPaperData.java

package com.credu.multiestimate;

import java.util.Hashtable;

// Referenced classes of package com.credu.multiestimate:
//            DamunPaperSubData

public class DamunPaperData
{

    public DamunPaperData()
    {
        PaperSubDataList = new Hashtable();
    }

    public String getGrcode()
    {
        return grcode;
    }

    public String getSubj()
    {
        return subj;
    }

    public String getSubjnm()
    {
        return subjnm;
    }

    public void setGrcode(String string)
    {
        grcode = string;
    }

    public void setSubj(String string)
    {
        subj = string;
    }

    public void setSubjnm(String string)
    {
        subjnm = string;
    }

    public DamunPaperSubData get(int index)
    {
        return (DamunPaperSubData)PaperSubDataList.get(String.valueOf(index));
    }

    public void add(DamunPaperSubData papersubdata)
    {
        PaperSubDataList.put(String.valueOf(PaperSubDataList.size()), papersubdata);
    }

    public void remove(int index)
    {
        PaperSubDataList.remove(String.valueOf(index));
    }

    public void clear()
    {
        PaperSubDataList.clear();
    }

    public int size()
    {
        return PaperSubDataList.size();
    }

    public int getSulpapernum(int index)
    {
        int i = 0;
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            i = papersubdata.getSulpapernum();
        return i;
    }

    public String getSulpapername(int index)
    {
        String string = "";
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            string = papersubdata.getSulpapername();
        return string;
    }

    public int getTotcnt(int index)
    {
        int i = 0;
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            i = papersubdata.getTotcnt();
        return i;
    }

    public String getSulnums(int index)
    {
        String string = "";
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            string = papersubdata.getSulnums();
        return string;
    }

    public String getSulmailing(int index)
    {
        String string = "";
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            string = papersubdata.getSulmailing();
        return string;
    }

    public String getSulstart(int index)
    {
        String string = "";
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            string = papersubdata.getSulstart();
        return string;
    }

    public String getSulend(int index)
    {
        String string = "";
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            string = papersubdata.getSulend();
        return string;
    }

    public void setSulpapernum(int index, int i)
    {
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            papersubdata.setSulpapernum(i);
    }

    public void setSulpapername(int index, String string)
    {
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            papersubdata.setSulpapername(string);
    }

    public void setTotcnt(int index, int i)
    {
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            papersubdata.setTotcnt(i);
    }

    public void setSulnums(int index, String string)
    {
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            papersubdata.setSulnums(string);
    }

    public void setSulmailing(int index, String string)
    {
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            papersubdata.setSulmailing(string);
    }

    public void setSulstart(int index, String string)
    {
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            papersubdata.setSulstart(string);
    }

    public void setSulend(int index, String string)
    {
        DamunPaperSubData papersubdata = get(index);
        if(papersubdata != null)
            papersubdata.setSulend(string);
    }

    private String grcode;
    private String subj;
    private String subjnm;
    private Hashtable PaperSubDataList;
}
