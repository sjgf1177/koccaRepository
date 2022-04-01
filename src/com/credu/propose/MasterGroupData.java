package com.credu.propose;

import com.credu.system.*;
import java.util.*;

public class MasterGroupData
{

    private String grcode;
    private String grcodenm;
    private String idtype;
    private String manager;
    private String master;
    private String repdate;
    private String domain;
    private String chkFirst;
    private String chkFinal;
    private String islogin;
    private String isjik;
    private String isonlygate;
    private String isusebill;
    private String indate;
    private String luserid;
    private String ldate;
    private int propcnt;
    private ArrayList grcomps;
    private String compTxt;
    private String managerName;
    private String masterName;
    private String masterEmail;
    private String masterComptel;
    private String etcdata;
    public Hashtable compList;

    public MasterGroupData()
    {
        compList = new Hashtable();
    }

    public String getChkFinal()
    {
        return chkFinal;
    }

    public String getChkFirst()
    {
        return chkFirst;
    }

    public Hashtable getCompList()
    {
        return compList;
    }

    public String getCompTxt()
    {
        return compTxt;
    }

    public String getDomain()
    {
        return domain;
    }

    public String getEtcdata()
    {
        return etcdata;
    }

    public String getGrcode()
    {
        return grcode;
    }

    public String getGrcodenm()
    {
        return grcodenm;
    }

    public ArrayList getGrcomps()
    {
        return grcomps;
    }

    public String getIdtype()
    {
        return idtype;
    }

    public String getIndate()
    {
        return indate;
    }

    public String getIsjik()
    {
        return isjik;
    }

    public String getIslogin()
    {
        return islogin;
    }

    public String getIsonlygate()
    {
        return isonlygate;
    }

    public String getIsusebill()
    {
        return isusebill;
    }

    public String getLdate()
    {
        return ldate;
    }

    public String getLuserid()
    {
        return luserid;
    }

    public String getManager()
    {
        return manager;
    }

    public String getManagerName()
    {
        return managerName;
    }

    public String getMaster()
    {
        return master;
    }

    public String getMasterComptel()
    {
        return masterComptel;
    }

    public String getMasterEmail()
    {
        return masterEmail;
    }

    public String getMasterName()
    {
        return masterName;
    }

    public int getPropcnt()
    {
        return propcnt;
    }

    public String getRepdate()
    {
        return repdate;
    }

    public String getchkFirst()
    {
        return chkFirst;
    }

    public void makeSub(String s, String s1)
    {
        int i = compList.size();
        CompData compdata = new CompData();
        compdata.setComp(s);
        compdata.setCompnm(s1);
        compList.put(String.valueOf(i), compdata);
    }

    public void setChkFinal(String s)
    {
        chkFinal = s;
    }

    public void setChkFirst(String s)
    {
        chkFirst = s;
    }

    public void setCompList(Hashtable hashtable)
    {
        compList = hashtable;
    }

    public void setCompTxt(String s)
    {
        compTxt = s;
    }

    public void setDomain(String s)
    {
        domain = s;
    }

    public void setEtcdata(String s)
    {
        etcdata = s;
    }

    public void setGrcode(String s)
    {
        grcode = s;
    }

    public void setGrcodenm(String s)
    {
        grcodenm = s;
    }

    public void setGrcomps(ArrayList arraylist)
    {
        grcomps = arraylist;
    }

    public void setIdtype(String s)
    {
        idtype = s;
    }

    public void setIndate(String s)
    {
        indate = s;
    }

    public void setIsjik(String s)
    {
        isjik = s;
    }

    public void setIslogin(String s)
    {
        islogin = s;
    }

    public void setIsonlygate(String s)
    {
        isonlygate = s;
    }

    public void setIsusebill(String s)
    {
        isusebill = s;
    }

    public void setLdate(String s)
    {
        ldate = s;
    }

    public void setLuserid(String s)
    {
        luserid = s;
    }

    public void setManager(String s)
    {
        manager = s;
    }

    public void setManagerName(String s)
    {
        managerName = s;
    }

    public void setMaster(String s)
    {
        master = s;
    }

    public void setMasterComptel(String s)
    {
        masterComptel = s;
    }

    public void setMasterEmail(String s)
    {
        masterEmail = s;
    }

    public void setMasterName(String s)
    {
        masterName = s;
    }

    public void setPropcnt(int i)
    {
        propcnt = i;
    }

    public void setRepdate(String s)
    {
        repdate = s;
    }

    public void setchkFirst(String s)
    {
        chkFirst = s;
    }


}
