// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunCommonQuestionBean.java

package com.credu.multiestimate;

import com.credu.library.*;
import com.credu.system.SelectionUtil;
import java.sql.PreparedStatement;
import java.util.*;

public class DamunCommonQuestionBean
{

    public DamunCommonQuestionBean()
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
        com.credu.library.DataBox dbox = null;
        try
        {
            sql = "select a.subj,     a.damunnum,  a.grcode, ";
            sql = sql + "       a.damuntype,  b.codenm  damuntypenm,  ";
            sql = sql + "       a.damuntext    ";
            sql = sql + "  from tz_damun    a, ";
            sql = sql + "       tz_code   b ";
            sql = sql + "   where a.damuntype  = b.code  ";
            sql = sql + "   and a.grcode    = " + SQLString.Format(p_grcode);
            sql = sql + "   and b.gubun    = " + SQLString.Format("0011");
            sql = sql + "   and b.levels    =  0 ";
            if(!p_subj.equals("ALL"))
                sql = sql + "   and a.subj     = " + SQLString.Format(p_subj);
            sql = sql + " order by a.damunnum ";
            for(ls = connMgr.executeQuery(sql); ls.next(); list.add(dbox))
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

    public ArrayList selectQuestionExample(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_gubun = "COMMON";
        String v_subj = box.getString("p_subj");
        String v_grcode = box.getString("p_grcode");
        int v_damunnum = box.getInt("p_damunnum");
        String v_action = box.getStringDefault("p_action", "change");
        try
        {
            if(v_action.equals("go") && v_damunnum > 0)
            {
                connMgr = new DBConnectionManager();
                list = getSelnums(connMgr, v_gubun, v_grcode, v_damunnum);
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

    public ArrayList getSelnums(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_damunnum)
        throws Exception
    {
        ArrayList list;
        list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        com.credu.library.DataBox dbox = null;
        try
        {
            sql = "select a.subj, a.grcode, a.damunnum, a.damuntype, a.damuntext, a.selcount, b.selpoint, a.selmax, a.fscalecode, a.sscalecode, b.selnum, b.seltext ";
            sql = sql + "  from tz_damun     a, ";
            sql = sql + "       tz_damunsel  b  ";
            sql = sql + " where a.subj   = b.subj(+)    ";
            sql = sql + "   and a.damunnum = b.damunnum(+)  ";
            sql = sql + "   and a.grcode = b.grcode(+) ";
            sql = sql + "   and a.subj   = " + SQLString.Format(p_subj);
            sql = sql + "   and a.grcode = " + SQLString.Format(p_grcode);
            sql = sql + "   and a.damunnum = " + SQLString.Format(p_damunnum);
            sql = sql + " order by b.damunnum ";
            for(ls = connMgr.executeQuery(sql); ls.next(); list.add(dbox))
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

    public ArrayList selectQuestionExampleList(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        com.credu.library.DataBox dbox = null;
        String v_gubun = "COMMON";
        String v_subj = box.getString("p_subj");
        String v_grcode = box.getString("p_grcode");
        int v_damunnum = box.getInt("p_damunnum");
        try
        {
            sql = "select subj,  damunnum,  selnum,  seltext, selpoint ";
            sql = sql + "  from tz_damunsel ";
            sql = sql + " where subj   = " + SQLString.Format(v_gubun);
            sql = sql + "   and grcode = " + SQLString.Format(v_grcode);
            sql = sql + "   and damunnum = " + SQLString.Format(v_damunnum);
            sql = sql + " order by selnum ";
            connMgr = new DBConnectionManager();
            for(ls = connMgr.executeQuery(sql); ls.next(); list.add(dbox))
                dbox = ls.getDataBox();

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

    public int insertTZ_damun(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_damunnum, String p_damuntype, String p_damuntext, int p_selcount, 
            int p_selmax, int p_fscalecode, int p_sscalecode, String p_luserid)
        throws Exception
    {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        try
        {
            sql = "insert into TZ_DAMUN(subj, grcode, damunnum, damuntype, damuntext, selcount, selmax, fscalecode, sscalecode, luserid, ldate) ";
            sql = sql + " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_grcode);
            pstmt.setInt(3, p_damunnum);
            pstmt.setString(4, p_damuntype);
            pstmt.setString(5, p_damuntext);
            pstmt.setInt(6, p_selcount);
            pstmt.setInt(7, p_selmax);
            pstmt.setInt(8, p_fscalecode);
            pstmt.setInt(9, p_sscalecode);
            pstmt.setString(10, p_luserid);
            pstmt.setString(11, FormatDate.getDate("yyyyMMddHHmmss"));
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

    public int updateTZ_damun(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_damunnum, String p_damuntype, String p_damuntext, int p_selcount, 
            int p_selmax, int p_fscalecode, int p_sscalecode, String p_luserid)
        throws Exception
    {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        try
        {
            sql = " update TZ_DAMUN ";
            sql = sql + "    set   damuntype  = ?, ";
            sql = sql + "        damuntext  = ?, ";
            sql = sql + "        selcount  = ?, ";
            sql = sql + "        selmax  = ?, ";
            sql = sql + "        fscalecode  = ?, ";
            sql = sql + "        sscalecode  = ?, ";
            sql = sql + "        luserid  = ?, ";
            sql = sql + "        ldate    = ?  ";
            sql = sql + "  where subj     = ?  ";
            sql = sql + "    and grcode   = ?  ";
            sql = sql + "    and damunnum   = ?  ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_damuntype);
            pstmt.setString(2, p_damuntext);
            pstmt.setInt(3, p_selcount);
            pstmt.setInt(4, p_selmax);
            pstmt.setInt(5, p_fscalecode);
            pstmt.setInt(6, p_sscalecode);
            pstmt.setString(7, p_luserid);
            pstmt.setString(8, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(9, p_subj);
            pstmt.setString(10, p_grcode);
            pstmt.setInt(11, p_damunnum);
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

    public int updateTZ_damunS(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_damunnum, String p_damuntype, String p_damuntext, int p_selcount, 
            int p_selmax, int p_fscalecode, int p_sscalecode, String p_luserid)
        throws Exception
    {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        try
        {
            sql = " update TZ_DAMUN ";
            sql = sql + "    set damuntype = ?, ";
            sql = sql + "        damuntext  = ?, ";
            sql = sql + "        fscalecode  = ?, ";
            sql = sql + "        sscalecode  = ?, ";
            sql = sql + "        luserid  = ?, ";
            sql = sql + "        ldate    = ?  ";
            sql = sql + "  where subj     = ?  ";
            sql = sql + "    and grcode   = ?  ";
            sql = sql + "    and damunnum   = ?  ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_damuntype);
            pstmt.setString(2, p_damuntext);
            pstmt.setInt(3, p_fscalecode);
            pstmt.setInt(4, p_sscalecode);
            pstmt.setString(5, p_luserid);
            pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(7, p_subj);
            pstmt.setString(8, p_grcode);
            pstmt.setInt(9, p_damunnum);
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

    public int deleteTZ_damun(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_damunnum, String p_duserid)
        throws Exception
    {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls = null;
        isOk = 0;
        try
        {
            sql = " select damunnums from tz_damunpaper     ";
            sql = sql + " where subj='" + p_subj + "' and grcode='" + p_grcode + "'  ";
            sql = sql + " and ((damunnums  like '%" + SQLString.Format(p_damunnum) + ",%') or (damunnums  like '%," + SQLString.Format(p_damunnum) + ",%') or (damunnums = '" + SQLString.Format(p_damunnum) + "') or (damunnums like '%," + SQLString.Format(p_damunnum) + "%')) ";
            ls = connMgr.executeQuery(sql);
            if(ls.next())
                isOk = -2;
            if(isOk == 0)
            {
                sql = " delete from TZ_DAMUN ";
                sql = sql + "  where subj     = ?  ";
                sql = sql + "    and grcode   = ?  ";
                sql = sql + "    and damunnum   = ?  ";
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, p_subj);
                pstmt.setString(2, p_grcode);
                pstmt.setInt(3, p_damunnum);
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

    public int insertTZ_damunsel(PreparedStatement pstmt, String p_subj, String p_grcode, int p_damunnum, int p_selnum, String p_seltext, String p_scalename, 
            int p_selpoint, String p_luserid)
        throws Exception
    {
        int isOk = 0;
        try
        {
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_grcode);
            pstmt.setInt(3, p_damunnum);
            pstmt.setInt(4, p_selnum);
            pstmt.setString(5, p_seltext);
            pstmt.setString(6, p_scalename);
            pstmt.setInt(7, p_selpoint);
            pstmt.setString(8, p_luserid);
            pstmt.setString(9, FormatDate.getDate("yyyyMMddHHmmss"));
            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return isOk;
    }

    public int deleteTZ_damunsel(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_damunnum)
        throws Exception
    {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        try
        {
            sql = " delete from TZ_DAMUNSEL ";
            sql = sql + "  where subj     = ?  ";
            sql = sql + "    and grcode   = ?  ";
            sql = sql + "    and damunnum   = ?  ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_grcode);
            pstmt.setInt(3, p_damunnum);
            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
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

    public int insertQuestion(RequestBox box)
        throws Exception
    {
        int isOk;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        String v_gubun = "COMMON";
        String v_subj = box.getString("p_subj");
        String v_grcode = box.getString("p_grcode");
        int v_damunnum = 0;
        String v_damuntype = box.getString("p_damuntype");
        String v_damuntext = box.getString("p_damuntext");
        int v_selnum = 0;
        int v_selcount = 0;
        int v_selmax = 0;
        int v_fscalecode = 0;
        int v_sscalecode = 0;
        String v_seltext = "";
        Vector v_seltexts = null;
        String v_scalename = "";
        Vector v_scalenames = null;
        Enumeration em = null;
        Enumeration em1 = null;
        int v_selpoint = 0;
        Vector v_selpoints = null;
        Enumeration em2 = null;
        if(v_damuntype.equals("1"))
        {
            v_selcount = box.getInt("p_selcount1");
            v_selmax = box.getInt("p_selmax1");
            v_fscalecode = box.getInt("p_scalecode1");
            v_seltexts = box.getVector("p_seltext1");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename1");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint1");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("2"))
        {
            v_selcount = box.getInt("p_selcount2");
            v_selmax = box.getInt("p_selmax2");
            v_fscalecode = box.getInt("p_scalecode2");
            v_seltexts = box.getVector("p_seltext2");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename2");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint2");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("3"))
        {
            v_selcount = box.getInt("p_selcount3");
            v_selmax = box.getInt("p_selmax3");
            v_fscalecode = box.getInt("p_scalecode3");
            v_seltexts = box.getVector("p_seltext3");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename3");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint3");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("4"))
        {
            v_selcount = box.getInt("p_selcount4");
            v_selmax = box.getInt("p_selmax4");
            v_fscalecode = box.getInt("p_scalecode4");
            v_seltexts = box.getVector("p_seltext4");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename4");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint4");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("5"))
        {
            v_selcount = box.getInt("p_selcount5");
            v_selmax = box.getInt("p_selmax5");
            v_fscalecode = box.getInt("p_fscalecode5");
            v_seltexts = box.getVector("p_seltext5");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename5");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint5");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("6"))
        {
            v_selcount = box.getInt("p_selcount6");
            v_selmax = box.getInt("p_selmax6");
            v_fscalecode = box.getInt("p_fscalecode6");
            v_seltexts = box.getVector("p_seltext6");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename6");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint6");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("7"))
        {
            v_selcount = box.getInt("p_selcount7");
            v_selmax = box.getInt("p_selmax7");
            v_fscalecode = box.getInt("p_fscalecode7");
            v_sscalecode = box.getInt("p_sscalecode7");
            v_seltexts = box.getVector("p_seltext7");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename7");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint7");
            em2 = v_selpoints.elements();
        }
        String v_luserid = box.getSession("userid");
        try
        {
            v_damunnum = getDamunSeq(v_gubun, v_grcode);
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            isOk = insertTZ_damun(connMgr, v_gubun, v_grcode, v_damunnum, v_damuntype, v_damuntext, v_selcount, v_selmax, v_fscalecode, v_sscalecode, v_luserid);
            sql = "insert into TZ_DAMUNSEL(subj, grcode, damunnum, selnum, seltext, scalename, selpoint,  luserid, ldate) ";
            sql = sql + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = connMgr.prepareStatement(sql);
            while(em.hasMoreElements()) 
            {
                v_seltext = (String)em.nextElement();
                if(v_damuntype.equals("5") || v_damuntype.equals("6") || v_damuntype.equals("7"))
                {
                    v_scalename = (String)em1.nextElement();
                    v_selpoint = Integer.parseInt((String)em2.nextElement());
                }
                if(!v_seltext.trim().equals(""))
                {
                    v_selnum++;
                    isOk = insertTZ_damunsel(pstmt, v_gubun, v_grcode, v_damunnum, v_selnum, v_seltext, v_scalename, v_selpoint, v_luserid);
                }
            }
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
            if(pstmt != null)
                try
                {
                    pstmt.close();
                }
                catch(Exception exception1) { }
            if(connMgr != null)
                try
                {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                }
                catch(Exception exception2) { }
        }
        return isOk;
    }

    public int updateQuestion(RequestBox box)
        throws Exception
    {
        int isOk;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        ListSet ls = null;
        String v_gubun = "COMMON";
        String v_subj = box.getString("p_subj");
        String v_grcode = box.getString("p_grcode");
        int v_damunnum = box.getInt("p_damunnum");
        String v_distcode = box.getString("p_distcode");
        String v_damuntype = box.getString("p_damuntype");
        String v_damuntext = box.getString("p_damuntext");
        int v_selnum = 0;
        int v_selcount = 0;
        int v_selmax = 0;
        int v_fscalecode = 0;
        int v_sscalecode = 0;
        String v_seltext = "";
        Vector v_seltexts = null;
        String v_scalename = "";
        Vector v_scalenames = null;
        Enumeration em = null;
        Enumeration em1 = null;
        int v_selpoint = 0;
        Vector v_selpoints = null;
        Enumeration em2 = null;
        if(v_damuntype.equals("1"))
        {
            v_selcount = box.getInt("p_selcount1");
            v_selmax = box.getInt("p_selmax1");
            v_fscalecode = box.getInt("p_scalecode1");
            v_seltexts = box.getVector("p_seltext1");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename1");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint1");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("2"))
        {
            v_selcount = box.getInt("p_selcount2");
            v_selmax = box.getInt("p_selmax2");
            v_fscalecode = box.getInt("p_scalecode2");
            v_seltexts = box.getVector("p_seltext2");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename2");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint2");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("3"))
        {
            v_selcount = box.getInt("p_selcount3");
            v_selmax = box.getInt("p_selmax3");
            v_fscalecode = box.getInt("p_scalecode3");
            v_seltexts = box.getVector("p_seltext3");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename3");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint3");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("4"))
        {
            v_selcount = box.getInt("p_selcount4");
            v_selmax = box.getInt("p_selmax4");
            v_fscalecode = box.getInt("p_scalecode4");
            v_seltexts = box.getVector("p_seltext4");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename4");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint4");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("5"))
        {
            v_selcount = box.getInt("p_selcount5");
            v_selmax = box.getInt("p_selmax5");
            v_fscalecode = box.getInt("p_fscalecode5");
            v_seltexts = box.getVector("p_seltext5");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename5");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint5");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("6"))
        {
            v_selcount = box.getInt("p_selcount6");
            v_selmax = box.getInt("p_selmax6");
            v_fscalecode = box.getInt("p_fscalecode6");
            v_seltexts = box.getVector("p_seltext6");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename6");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint6");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("7"))
        {
            v_selcount = box.getInt("p_selcount7");
            v_selmax = box.getInt("p_selmax7");
            v_fscalecode = box.getInt("p_fscalecode7");
            v_sscalecode = box.getInt("p_sscalecode7");
            v_seltexts = box.getVector("p_seltext7");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename7");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint7");
            em2 = v_selpoints.elements();
        }
        String v_luserid = box.getSession("userid");
        try
        {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sql = " select damunnums from tz_damunpaper     ";
            sql = sql + " where subj='COMMON' and grcode='" + v_grcode + "'  ";
            sql = sql + " and ((damunnums  like '%" + SQLString.Format(v_damunnum) + ",%') or (damunnums  like '%," + SQLString.Format(v_damunnum) + ",%') or (damunnums = '" + SQLString.Format(v_damunnum) + "') or (damunnums like '%," + SQLString.Format(v_damunnum) + "%')) ";
            ls = connMgr.executeQuery(sql);
            if(ls.next())
                isOk = -2;
            if(isOk == 0)
            {
                isOk = updateTZ_damun(connMgr, v_gubun, v_grcode, v_damunnum, v_damuntype, v_damuntext, v_selcount, v_selmax, v_fscalecode, v_sscalecode, v_luserid);
                if(!v_damuntype.equals("3"))
                    isOk = deleteTZ_damunsel(connMgr, v_gubun, v_grcode, v_damunnum);
                sql = "insert into TZ_DAMUNSEL(subj, grcode, damunnum, selnum, seltext, scalename, selpoint,  luserid, ldate) ";
                sql = sql + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = connMgr.prepareStatement(sql);
                while(em.hasMoreElements()) 
                {
                    v_seltext = (String)em.nextElement();
                    if(v_damuntype.equals("5") || v_damuntype.equals("6") || v_damuntype.equals("7"))
                    {
                        v_scalename = (String)em1.nextElement();
                        v_selpoint = Integer.parseInt((String)em2.nextElement());
                    }
                    if(!v_seltext.trim().equals(""))
                    {
                        v_selnum++;
                        isOk = insertTZ_damunsel(pstmt, v_gubun, v_grcode, v_damunnum, v_selnum, v_seltext, v_scalename, v_selpoint, v_luserid);
                    }
                }
            }
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
            if(connMgr != null)
                try
                {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                }
                catch(Exception exception3) { }
        }
        return isOk;
    }

    public int updateSQuestion(RequestBox box)
        throws Exception
    {
        int isOk;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        String v_gubun = "COMMON";
        String v_subj = box.getString("p_subj");
        String v_grcode = box.getString("p_grcode");
        int v_damunnum = box.getInt("p_damunnum");
        String v_distcode = box.getString("p_distcode");
        String v_damuntype = box.getString("p_damuntype");
        String v_damuntext = box.getString("p_damuntext");
        int v_selnum = 0;
        int v_selcount = 0;
        int v_selmax = 0;
        int v_fscalecode = 0;
        int v_sscalecode = 0;
        String v_seltext = "";
        Vector v_seltexts = null;
        String v_scamename = "";
        Vector v_scalenames = null;
        Enumeration em = null;
        Enumeration em1 = null;
        int v_selpoint = 0;
        Vector v_selpoints = null;
        Enumeration em2 = null;
        if(v_damuntype.equals("1"))
        {
            v_selcount = box.getInt("p_selcount1");
            v_selmax = box.getInt("p_selmax1");
            v_fscalecode = box.getInt("p_scalecode1");
            v_seltexts = box.getVector("p_seltext1");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename1");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint1");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("2"))
        {
            v_selcount = box.getInt("p_selcount2");
            v_selmax = box.getInt("p_selmax2");
            v_fscalecode = box.getInt("p_scalecode2");
            v_seltexts = box.getVector("p_seltext2");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename2");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint2");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("3"))
        {
            v_selcount = box.getInt("p_selcount3");
            v_selmax = box.getInt("p_selmax3");
            v_fscalecode = box.getInt("p_scalecode3");
            v_seltexts = box.getVector("p_seltext3");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename3");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint3");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("4"))
        {
            v_selcount = box.getInt("p_selcount4");
            v_selmax = box.getInt("p_selmax4");
            v_fscalecode = box.getInt("p_scalecode4");
            v_seltexts = box.getVector("p_seltext4");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename4");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint4");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("5"))
        {
            v_selcount = box.getInt("p_selcount5");
            v_selmax = box.getInt("p_selmax5");
            v_fscalecode = box.getInt("p_fscalecode5");
            v_seltexts = box.getVector("p_seltext5");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename5");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint5");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("6"))
        {
            v_selcount = box.getInt("p_selcount6");
            v_selmax = box.getInt("p_selmax6");
            v_fscalecode = box.getInt("p_fscalecode6");
            v_seltexts = box.getVector("p_seltext6");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename6");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint6");
            em2 = v_selpoints.elements();
        } else
        if(v_damuntype.equals("7"))
        {
            v_selcount = box.getInt("p_selcount7");
            v_selmax = box.getInt("p_selmax7");
            v_fscalecode = box.getInt("p_fscalecode7");
            v_sscalecode = box.getInt("p_sscalecode7");
            v_seltexts = box.getVector("p_seltext7");
            em = v_seltexts.elements();
            v_scalenames = box.getVector("p_scalename7");
            em1 = v_scalenames.elements();
            v_selpoints = box.getVector("p_selpoint7");
            em2 = v_selpoints.elements();
        }
        String v_luserid = box.getSession("userid");
        try
        {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            isOk = updateTZ_damunS(connMgr, v_gubun, v_grcode, v_damunnum, v_damuntype, v_damuntext, v_selcount, v_selmax, v_fscalecode, v_sscalecode, v_luserid);
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
            if(pstmt != null)
                try
                {
                    pstmt.close();
                }
                catch(Exception exception1) { }
            if(connMgr != null)
                try
                {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                }
                catch(Exception exception2) { }
        }
        return isOk;
    }

    public int deleteQuestion(RequestBox box)
        throws Exception
    {
        int isOk;
        DBConnectionManager connMgr = null;
        isOk = 0;
        int isOk1 = 0;
        String v_gubun = "COMMON";
        String v_subj = box.getString("p_subj");
        String v_grcode = box.getString("p_grcode");
        int v_damunnum = box.getInt("p_damunnum");
        String v_duserid = box.getSession("userid");
        String v_damuntype = box.getString("p_damuntype");
        try
        {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            isOk = deleteTZ_damun(connMgr, v_gubun, v_grcode, v_damunnum, v_duserid);
            if(isOk > 0 && !v_damuntype.equals("3"))
                isOk1 = deleteTZ_damunsel(connMgr, v_gubun, v_grcode, v_damunnum);
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
            {
                connMgr.commit();
                box.put("p_damunnum", String.valueOf("0"));
            }
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

    public int getDamunSeq(String p_subj, String p_grcode)
        throws Exception
    {
        Hashtable maxdata = new Hashtable();
        maxdata.put("seqcolumn", "damunnum");
        maxdata.put("seqtable", "tz_damun");
        maxdata.put("paramcnt", "2");
        maxdata.put("param0", "subj");
        maxdata.put("param1", "grcode");
        maxdata.put("subj", SQLString.Format(p_subj));
        maxdata.put("grcode", SQLString.Format(p_grcode));
        return SelectionUtil.getSeq(maxdata);
    }
}
