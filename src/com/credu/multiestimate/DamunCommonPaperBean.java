// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunCommonPaperBean.java

package com.credu.multiestimate;

import com.credu.library.*;
import com.credu.system.SelectionUtil;
import java.io.PrintStream;
import java.sql.PreparedStatement;
import java.util.*;

public class DamunCommonPaperBean
{

    public DamunCommonPaperBean()
    {
    }

    public ArrayList selectQuestionList(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_gubun = "COMMON";
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_distcode = box.getStringDefault("s_distcode", "ALL");
        String v_grcode = box.getString("s_grcode");
        String v_action = box.getStringDefault("p_action", "change");
        try
        {
            if(v_action.equals("go"))
            {
                connMgr = new DBConnectionManager();
                list = selectQuestionList(connMgr, v_grcode, v_gubun, v_distcode);
            } else
            {
                list = new ArrayList();
            }
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(connMgr != null)
                try
                {
                    connMgr.freeConnection();
                }
                catch(Exception exception1) { }
        }
        return list;
    }

    public ArrayList selectQuestionList(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_distcode)
        throws Exception
    {
        ArrayList list;
        list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        try
        {
            sql = "select a.subj,     a.damunnum,  a.grcode, ";
            sql = sql + "       a.damuntype,  b.codenm  damuntypenm,  ";
            sql = sql + "       a.damuntext    ";
            sql = sql + "  from tz_damun    a, ";
            sql = sql + "       tz_code   b ";
            sql = sql + " where b.levels = 0 ";
            sql = sql + "   and a.damuntype  = b.code ";
            sql = sql + "   and a.grcode    = " + SQLString.Format(p_grcode);
            sql = sql + "   and b.gubun    = " + SQLString.Format("0011");
            if(!p_subj.equals("ALL"))
                sql = sql + "   and a.subj     = " + SQLString.Format(p_subj);
            sql = sql + " order by a.damunnum ";
            ls = connMgr.executeQuery(sql);
            System.out.println(sql);
            for(; ls.next(); list.add(dbox))
                dbox = ls.getDataBox();

        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        return list;
    }

    public ArrayList selectPaperQuestionList(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_gubun = "COMMON";
        String v_grcode = box.getString("p_grcode");
        String v_subj = box.getString("p_subj");
        int v_damunpapernum = box.getInt("p_damunpapernum");
        try
        {
            connMgr = new DBConnectionManager();
            if(!v_gubun.equals("SUBJ"))
                v_subj = v_gubun;
            list = selectPaperQuestionList(connMgr, v_grcode, v_gubun, v_damunpapernum, box);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(connMgr != null)
                try
                {
                    connMgr.freeConnection();
                }
                catch(Exception exception1) { }
        }
        return list;
    }

    public ArrayList selectPaperQuestionList(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_damunpapernum, RequestBox box)
        throws Exception
    {
        ArrayList list;
        list = new ArrayList();
        Hashtable hash = new Hashtable();
        StringTokenizer st = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_damunnums = "";
        String v_damunnum = "";
        String v_damunpapernm = "";
        try
        {
            sql = "select damunpapernm, totcnt, damunnums";
            sql = sql + "  from tz_damunpaper ";
            sql = sql + " where subj        = " + SQLString.Format(p_subj);
            sql = sql + "   and damunpapernum = " + SQLString.Format(p_damunpapernum);
            for(ls = connMgr.executeQuery(sql); ls.next();)
            {
                v_damunnums = ls.getString("damunnums");
                v_damunpapernm = ls.getString("damunpapernm");
            }

            if(box != null)
                box.put("p_damunpapernm", v_damunpapernm);
            if(v_damunnums.length() > 0)
            {
                sql = "select a.subj,     a.damunnum,  ";
                sql = sql + "       a.damuntype,  b.codenm  damuntypenm,  ";
                sql = sql + "       a.damuntext    ";
                sql = sql + "  from tz_damun    a, ";
                sql = sql + "       tz_code   b ";
                sql = sql + " where b.levels = 0 ";
                sql = sql + "   and a.damuntype  = b.code ";
                sql = sql + "   and b.gubun    = " + SQLString.Format("0011");
                sql = sql + "   and a.subj     = " + SQLString.Format(p_subj);
                if(v_damunnums.equals(""))
                    v_damunnums = "-1";
                sql = sql + "   and a.damunnum in (" + v_damunnums + ")";
                sql = sql + " order by a.damunnum ";
                ls.close();
                ls = connMgr.executeQuery(sql);
                st = new StringTokenizer(v_damunnums, ",");
                for(; ls.next(); hash.put(v_damunnum, dbox))
                {
                    dbox = ls.getDataBox();
                    v_damunnum = String.valueOf(ls.getInt("damunnum"));
                }

                while(st.hasMoreElements()) 
                {
                    v_damunnum = st.nextToken();
                    dbox = (DataBox)hash.get(v_damunnum);
                    if(dbox != null)
                        list.add(dbox);
                }
            }
            System.out.println(sql);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        return list;
    }

    public String getPaperListSQL(String p_grcode, String p_subj, String p_gyear, String p_subjsel, String p_upperclass, int p_damunpapernum)
        throws Exception
    {
        String sql = "";
        sql = "select grcode,       subj,         ";
        sql = sql + "       damunpapernum,  damunpapernm, year, ";
        sql = sql + "       totcnt,       damunnums, d_gubun, fdamunstart, fdamunend, sdamunstart, sdamunend, ";
        sql = sql + "       'COMMON'      subjnm ";
        sql = sql + "  from tz_damunpaper ";
        sql = sql + " where grcode = " + SQLString.Format(p_grcode);
        sql = sql + "   and subj   = " + SQLString.Format(p_subj);
        sql = sql + "   and year   = " + SQLString.Format(p_gyear);
        if(p_damunpapernum > 0)
            sql = sql + "   and damunpapernum   = " + SQLString.Format(p_damunpapernum);
        sql = sql + " order by subj, damunpapernum ";
        System.out.println(sql);
        return sql;
    }

    public ArrayList selectPaperList(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getStringDefault("p_gyear", box.getString("s_gyear"));
        String v_action = box.getStringDefault("p_action", "change");
        String v_gubun = "COMMON";
        String ss_upperclass = box.getStringDefault("s_upperclass", "ALL");
        String ss_middleclass = box.getStringDefault("s_middleclass", "ALL");
        String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");
        try
        {
            if(v_action.equals("go"))
            {
                sql = getPaperListSQL(v_grcode, v_gubun, v_gyear, ss_subjcourse, ss_upperclass, -1);
                connMgr = new DBConnectionManager();
                for(ls = connMgr.executeQuery(sql); ls.next(); list.add(dbox))
                    dbox = ls.getDataBox();

            }
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
            if(connMgr != null)
                try
                {
                    connMgr.freeConnection();
                }
                catch(Exception exception2) { }
        }
        return list;
    }

    public DataBox getPaperData(RequestBox box)
        throws Exception
    {
        DataBox dbox;
        DBConnectionManager connMgr = null;
        dbox = null;
        String v_gubun = "COMMON";
        String v_grcode = box.getString("p_grcode");
        String v_subj = box.getString("p_subj");
        String v_gyear = box.getStringDefault("p_gyear", box.getString("s_gyear"));
        String v_subjsel = box.getString("p_subj");
        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");
        int v_damunpapernum = box.getInt("p_damunpapernum");
        try
        {
            connMgr = new DBConnectionManager();
            dbox = getPaperData(connMgr, v_grcode, v_gubun, v_gyear, v_subjsel, v_upperclass, v_damunpapernum);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(connMgr != null)
                try
                {
                    connMgr.freeConnection();
                }
                catch(Exception exception1) { }
        }
        return dbox;
    }

    public DataBox getPaperData(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_gyear, String p_subjsel, String p_upperclass, int p_damunpapernum)
        throws Exception
    {
        DataBox dbox;
        ListSet ls = null;
        String sql = "";
        dbox = null;
        try
        {
            sql = getPaperListSQL(p_grcode, p_subj, p_gyear, p_subjsel, p_upperclass, p_damunpapernum);
            for(ls = connMgr.executeQuery(sql); ls.next();)
                dbox = ls.getDataBox();

        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        if(dbox == null)
            dbox = new DataBox("resoponsebox");
        return dbox;
    }

    public int insertTZ_damunpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_gyear, int p_damunpapernum, String p_d_gubun, String p_damunpapernm, 
            int p_totcnt, String p_damunnums, String p_fdamunstart, String p_fdamunend, String p_sdamunstart, String p_sdamunend, String p_luserid)
        throws Exception
    {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        try
        {
            sql = "insert into TZ_DAMUNPAPER ";
            sql = sql + "(grcode,    subj,    year,  damunpapernum, d_gubun, damunpapernm, ";
            sql = sql + " totcnt,       damunnums,     fdamunstart, fdamunend,  ";
            sql = sql + " sdamunstart, sdamunend,  luserid,  ldate)   ";
            sql = sql + " values ";
            sql = sql + "(?,         ?,       ?,         ?,       ?,      ?, ";
            sql = sql + " ?,         ?,       ?,          ?, ";
            sql = sql + " ?,         ?,       ?,          ?) ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_grcode);
            pstmt.setString(2, p_subj);
            pstmt.setString(3, p_gyear);
            pstmt.setInt(4, p_damunpapernum);
            pstmt.setString(5, p_d_gubun);
            pstmt.setString(6, p_damunpapernm);
            pstmt.setInt(7, p_totcnt);
            pstmt.setString(8, p_damunnums);
            pstmt.setString(9, p_fdamunstart);
            pstmt.setString(10, p_fdamunend);
            pstmt.setString(11, p_sdamunstart);
            pstmt.setString(12, p_sdamunend);
            pstmt.setString(13, p_luserid);
            pstmt.setString(14, FormatDate.getDate("yyyyMMddHHmmss"));
            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally
        {
            if(pstmt != null)
                try
                {
                    pstmt.close();
                }
                catch(Exception exception1) { }
        }
        return isOk;
    }

    public int updateTZ_damunpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_gyear, int p_damunpapernum, String p_d_gubun, String p_damunpapernm, 
            int p_totcnt, String p_damunnums, String p_fdamunstart, String p_fdamunend, String p_sdamunstart, String p_sdamunend, String p_luserid)
        throws Exception
    {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        try
        {
            sql = " update TZ_DAMUNPAPER ";
            sql = sql + "    set damunpapernm = ?, ";
            sql = sql + "        d_gubun       = ?, ";
            sql = sql + "        totcnt       = ?, ";
            sql = sql + "        damunnums      = ?, ";
            sql = sql + "        fdamunstart       = ?, ";
            sql = sql + "        fdamunend       = ?, ";
            sql = sql + "        sdamunstart       = ?, ";
            sql = sql + "        sdamunend       = ?, ";
            sql = sql + "        luserid      = ?, ";
            sql = sql + "        ldate        = ?  ";
            sql = sql + "  where grcode       = ?  ";
            sql = sql + "    and subj         = ?  ";
            sql = sql + "    and damunpapernum  = ?  ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_damunpapernm);
            pstmt.setString(2, p_d_gubun);
            pstmt.setInt(3, p_totcnt);
            pstmt.setString(4, p_damunnums);
            pstmt.setString(5, p_fdamunstart);
            pstmt.setString(6, p_fdamunend);
            pstmt.setString(7, p_sdamunstart);
            pstmt.setString(8, p_sdamunend);
            pstmt.setString(9, p_luserid);
            pstmt.setString(10, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(11, p_grcode);
            pstmt.setString(12, p_subj);
            pstmt.setInt(13, p_damunpapernum);
            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally
        {
            if(pstmt != null)
                try
                {
                    pstmt.close();
                }
                catch(Exception exception1) { }
        }
        return isOk;
    }

    public int deleteTZ_damunpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_damunpapernum, String p_duserid)
        throws Exception
    {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls = null;
        isOk = 0;
        try
        {
            sql = " select userid from tz_damuneach where subj='" + p_subj + "' and grcode='" + p_grcode + "' and damunpapernum=" + p_damunpapernum + " ";
            ls = connMgr.executeQuery(sql);
            if(ls.next())
                isOk = -2;
            if(isOk == 0)
            {
                sql = "delete from  TZ_DAMUNPAPER ";
                sql = sql + " where grcode     = ?  ";
                sql = sql + "   and subj       = ?  ";
                sql = sql + "   and damunpapernum= ?  ";
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, p_grcode);
                pstmt.setString(2, p_subj);
                pstmt.setInt(3, p_damunpapernum);
                isOk = pstmt.executeUpdate();
            }
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
            if(pstmt != null)
                try
                {
                    pstmt.close();
                }
                catch(Exception exception2) { }
        }
        return isOk;
    }

    public int insertPaper(RequestBox box)
        throws Exception
    {
        int isOk;
        DBConnectionManager connMgr = null;
        isOk = 0;
        String v_grcode = box.getStringDefault("p_grcode", "N000001");
        String v_gubun = "COMMON";
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_gyear = box.getStringDefault("p_gyear", box.getString("s_gyear"));
        String v_damunpapernm = box.getString("p_damunpapernm");
        String v_d_gubun = box.getString("p_d_gubun");
        int v_totcnt = box.getInt("p_totcnt");
        String v_damunnums = box.getString("p_damunnums");
        int v_damunpapernum = 0;
        String v_fdamunstart = box.getString("p_fdamunstart");
        String v_fdamunend = box.getString("p_fdamunend");
        String v_sdamunstart = box.getString("p_sdamunstart");
        String v_sdamunend = box.getString("p_sdamunend");
        String v_luserid = box.getSession("userid");
        try
        {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            v_damunpapernum = getPapernumSeq(v_gubun, v_grcode);
            isOk = insertTZ_damunpaper(connMgr, v_grcode, v_gubun, v_gyear, v_damunpapernum, v_d_gubun, v_damunpapernm, v_totcnt, v_damunnums, v_fdamunstart, v_fdamunend, v_sdamunstart, v_sdamunend, v_luserid);
        }
        catch(Exception ex)
        {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(isOk > 0)
                connMgr.commit();
            if(connMgr != null)
                try
                {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                }
                catch(Exception exception1) { }
        }
        return isOk;
    }

    public int updatePaper(RequestBox box)
        throws Exception
    {
        int isOk;
        DBConnectionManager connMgr = null;
        isOk = 0;
        String v_grcode = box.getString("p_grcode");
        String v_gubun = "COMMON";
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_gyear = box.getStringDefault("p_gyear", box.getString("s_gyear"));
        String v_damunpapernm = box.getString("p_damunpapernm");
        String v_d_gubun = box.getString("p_d_gubun");
        int v_totcnt = box.getInt("p_totcnt");
        String v_damunnums = box.getString("p_damunnums");
        int v_damunpapernum = box.getInt("p_damunpapernum");
        String v_fdamunstart = box.getString("p_fdamunstart");
        String v_fdamunend = box.getString("p_fdamunend");
        String v_sdamunstart = box.getString("p_sdamunstart");
        String v_sdamunend = box.getString("p_sdamunend");
        String v_luserid = box.getSession("userid");
        try
        {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            isOk = updateTZ_damunpaper(connMgr, v_grcode, v_gubun, v_gyear, v_damunpapernum, v_d_gubun, v_damunpapernm, v_totcnt, v_damunnums, v_fdamunstart, v_fdamunend, v_sdamunstart, v_sdamunend, v_luserid);
        }
        catch(Exception ex)
        {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(isOk > 0)
                connMgr.commit();
            if(connMgr != null)
                try
                {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                }
                catch(Exception exception1) { }
        }
        return isOk;
    }

    public int deletePaper(RequestBox box)
        throws Exception
    {
        int isOk;
        DBConnectionManager connMgr = null;
        String sql = "";
        isOk = 0;
        String v_grcode = box.getString("p_grcode");
        String v_gubun = "COMMON";
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        int v_damunpapernum = box.getInt("p_damunpapernum");
        String v_duserid = box.getSession("userid");
        try
        {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            isOk = deleteTZ_damunpaper(connMgr, v_grcode, v_gubun, v_damunpapernum, v_duserid);
        }
        catch(Exception ex)
        {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally
        {
            if(isOk > 0)
                connMgr.commit();
            if(connMgr != null)
                try
                {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                }
                catch(Exception exception1) { }
        }
        return isOk;
    }

    public int getPapernumSeq(String p_subj, String p_grcode)
        throws Exception
    {
        Hashtable maxdata = new Hashtable();
        maxdata.put("seqcolumn", "damunpapernum");
        maxdata.put("seqtable", "tz_damunpaper");
        maxdata.put("paramcnt", "2");
        maxdata.put("param0", "subj");
        maxdata.put("param1", "grcode");
        maxdata.put("subj", SQLString.Format(p_subj));
        maxdata.put("grcode", SQLString.Format(p_grcode));
        return SelectionUtil.getSeq(maxdata);
    }

    public Vector getSulnums(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_damunpapernum)
        throws Exception
    {
        Vector v_damunnums;
        ListSet ls = null;
        String sql = "";
        v_damunnums = new Vector();
        String v_tokens = "";
        StringTokenizer st = null;
        try
        {
            sql = "select damunnums  ";
            sql = sql + "  from tz_damunpaper ";
            sql = sql + " where grcode      = " + SQLString.Format(p_grcode);
            sql = sql + "   and subj        = " + SQLString.Format(p_subj);
            sql = sql + "   and damunpapernum = " + SQLString.Format(p_damunpapernum);
            for(ls = connMgr.executeQuery(sql); ls.next();)
                v_tokens = ls.getString("damunnums");

            for(st = new StringTokenizer(v_tokens, ","); st.hasMoreElements(); v_damunnums.add(st.nextToken()));
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        return v_damunnums;
    }

    public ArrayList getSelnums(DBConnectionManager connMgr, String p_subj, String p_grcode, Vector p_damunnums)
        throws Exception
    {
        ArrayList blist;
        Hashtable hash = new Hashtable();
        blist = new ArrayList();
        ArrayList list = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        StringTokenizer st = null;
        String v_damunnums = "";
        for(int i = 0; i < p_damunnums.size(); i++)
        {
            v_damunnums = v_damunnums + (String)p_damunnums.get(i);
            if(i < p_damunnums.size() - 1)
                v_damunnums = v_damunnums + ",";
        }

        if(v_damunnums.equals(""))
            v_damunnums = "-1";
        try
        {
            for(st = new StringTokenizer(v_damunnums, ","); st.hasMoreElements(); blist.add(list))
            {
                int damunnum = Integer.parseInt(st.nextToken());
                sql = "select a.subj,     a.damunnum, a.selcount, ";
                sql = sql + "       a.damuntype,  c.codenm damuntypenm, ";
                sql = sql + "       a.damuntext,  b.selnum, b.seltext, b.scalename ";
                sql = sql + "  from tz_damun     a, ";
                sql = sql + "       tz_damunsel  b, ";
                sql = sql + "       tz_code    c ";
                sql = sql + " where a.subj     = b.subj(+)    ";
                sql = sql + "   and a.damunnum   = b.damunnum(+)  ";
                sql = sql + "   and a.grcode = b.grcode(+) ";
                sql = sql + "   and a.damuntype  = c.code ";
                sql = sql + "   and a.subj     = " + SQLString.Format(p_subj);
                sql = sql + "   and a.grcode     = " + SQLString.Format(p_grcode);
                sql = sql + "   and a.damunnum = " + damunnum;
                sql = sql + "   and c.gubun    = " + SQLString.Format("0011");
                sql = sql + "    and c.levels  =  0   ";
                sql = sql + " order by a.subj, a.damunnum, b.selnum ";
                ls = connMgr.executeQuery(sql);
                list = new ArrayList();
                for(; ls.next(); list.add(dbox))
                    dbox = ls.getDataBox();

            }

        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        return blist;
    }

    public ArrayList selectPaperQuestionExampleList(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_gubun = "COMMON";
        String v_grcode = box.getString("p_grcode");
        String v_subj = box.getString("p_subj");
        int v_damunpapernum = box.getInt("p_damunpapernum");
        try
        {
            if(v_damunpapernum == 0)
            {
                v_damunpapernum = getPapernumSeq(v_gubun, v_grcode) - 1;
                box.put("p_damunpapernum", String.valueOf(v_damunpapernum));
            }
            connMgr = new DBConnectionManager();
            list = selectPaperQuestionExampleList(connMgr, v_grcode, v_gubun, v_damunpapernum, box);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(connMgr != null)
                try
                {
                    connMgr.freeConnection();
                }
                catch(Exception exception1) { }
        }
        return list;
    }

    public ArrayList selectPaperQuestionExampleList(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_damunpapernum, RequestBox box)
        throws Exception
    {
        Vector v_damunnums = null;
        ArrayList QuestionExampleDataList = null;
        try
        {
            v_damunnums = getSulnums(connMgr, p_grcode, p_subj, p_damunpapernum);
            if(!v_damunnums.equals(""))
                QuestionExampleDataList = getSelnums(connMgr, p_subj, p_grcode, v_damunnums);
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return QuestionExampleDataList;
    }
}
