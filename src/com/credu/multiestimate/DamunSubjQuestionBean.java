// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunSubjQuestionBean.java

package com.credu.multiestimate;

import com.credu.library.*;
import com.credu.system.SelectionUtil;
import java.sql.PreparedStatement;
import java.util.*;

public class DamunSubjQuestionBean
{

    public DamunSubjQuestionBean()
    {
    }

    public ArrayList selectQuestionList(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_gubun = box.getStringDefault("p_gubun", "SUBJ");
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_distcode = box.getStringDefault("s_distcode", "ALL");
        String v_grcode = box.getString("s_grcode");
        String v_action = box.getStringDefault("p_action", "change");
        try
        {
            if(v_action.equals("go"))
            {
                connMgr = new DBConnectionManager();
                list = selectQuestionList(connMgr, v_grcode, v_subj, v_distcode);
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
        String v_gubun = box.getStringDefault("p_gubun", "SUBJ");
        String v_subj = box.getString("p_subj");
        int v_damunnum = box.getInt("p_damunnum");
        String v_action = box.getStringDefault("p_action", "change");
        try
        {
            if(v_action.equals("go") && v_damunnum > 0)
            {
                connMgr = new DBConnectionManager();
                list = getSelnums(connMgr, v_subj, v_damunnum);
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

    public ArrayList getSelnums(DBConnectionManager connMgr, String p_subj, int p_damunnum)
        throws Exception
    {
        ArrayList list;
        list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        try
        {
            sql = "select a.subj, a.grcode, a.damunnum, a.damuntype, a.damuntext, a.selcount, b.selpoint, a.selmax, a.fscalecode, a.sscalecode, b.selnum, b.seltext ";
            sql = sql + "  from tz_damun     a, ";
            sql = sql + "       tz_damunsel  b  ";
            sql = sql + " where a.subj   = b.subj(+)    ";
            sql = sql + "   and a.damunnum = b.damunnum(+)  ";
            sql = sql + "   and a.subj   = " + SQLString.Format(p_subj);
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
        DataBox dbox = null;
        String v_subj = box.getString("p_subj");
        int v_damunnum = box.getInt("p_damunnum");
        try
        {
            sql = "select subj,  damunnum,  selnum,  seltext, selpoint ";
            sql = sql + "  from tz_damunsel ";
            sql = sql + " where subj   = " + SQLString.Format(v_subj);
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
            if(pstmt != null)
                try
                {
                    pstmt.close();
                }
                catch(Exception exception1) { }
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
        String v_gubun = box.getStringDefault("p_gubun", "SUBJ");
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
            v_damunnum = getDamunSeq(v_subj, v_grcode);
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            isOk = insertTZ_damun(connMgr, v_subj, v_grcode, v_damunnum, v_damuntype, v_damuntext, v_selcount, v_selmax, v_fscalecode, v_sscalecode, v_luserid);
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
                    isOk = insertTZ_damunsel(pstmt, v_subj, v_grcode, v_damunnum, v_selnum, v_seltext, v_scalename, v_selpoint, v_luserid);
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
        String v_gubun = box.getStringDefault("p_gubun", "SUBJ");
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
            sql = sql + " where subj='" + v_subj + "' and grcode='" + v_grcode + "'  ";
            sql = sql + " and ((damunnums  like '%" + SQLString.Format(v_damunnum) + ",%') or (damunnums  like '%," + SQLString.Format(v_damunnum) + ",%') or (damunnums = '" + SQLString.Format(v_damunnum) + "') or (damunnums like '%," + SQLString.Format(v_damunnum) + "%')) ";
            ls = connMgr.executeQuery(sql);
            if(ls.next())
                isOk = -2;
            if(isOk == 0)
            {
                isOk = updateTZ_damun(connMgr, v_subj, v_grcode, v_damunnum, v_damuntype, v_damuntext, v_selcount, v_selmax, v_fscalecode, v_sscalecode, v_luserid);
                if(!v_damuntype.equals("3"))
                    isOk = deleteTZ_damunsel(connMgr, v_subj, v_grcode, v_damunnum);
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
                        isOk = insertTZ_damunsel(pstmt, v_subj, v_grcode, v_damunnum, v_selnum, v_seltext, v_scalename, v_selpoint, v_luserid);
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
        String v_gubun = box.getStringDefault("p_gubun", "SUBJ");
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
            isOk = updateTZ_damunS(connMgr, v_subj, v_grcode, v_damunnum, v_damuntype, v_damuntext, v_selcount, v_selmax, v_fscalecode, v_sscalecode, v_luserid);
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
        String v_gubun = box.getStringDefault("p_gubun", "SUBJ");
        String v_subj = box.getString("p_subj");
        String v_grcode = box.getString("p_grcode");
        int v_damunnum = box.getInt("p_damunnum");
        String v_duserid = box.getSession("userid");
        String v_damuntype = box.getString("p_damuntype");
        try
        {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            isOk = deleteTZ_damun(connMgr, v_subj, v_grcode, v_damunnum, v_duserid);
            if(isOk > 0 && !v_damuntype.equals("3"))
                isOk = deleteTZ_damunsel(connMgr, v_subj, v_grcode, v_damunnum);
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

    public ArrayList selectQuestionPool(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_subj = box.getString("p_subj");
        String v_grcode = box.getString("p_grcode");
        String v_action = box.getStringDefault("p_action", "change");
        try
        {
            if(v_action.equals("go"))
            {
                connMgr = new DBConnectionManager();
                list = getQuestionPool(connMgr, v_subj, v_grcode);
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

    public ArrayList getQuestionPool(DBConnectionManager connMgr, String p_subj, String p_grcode)
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
            sql = sql + "       a.damuntext,    ";
            sql = sql + "       d.subjnm    subjnm    ";
            sql = sql + "  from tz_damun    a, ";
            sql = sql + "       tz_code   b, ";
            sql = sql + "       tz_subj   d  ";
            sql = sql + "   where a.subj  = d.subj ";
            sql = sql + "   and a.damuntype  = b.code ";
            sql = sql + "   and a.grcode    = " + SQLString.Format(p_grcode);
            sql = sql + "   and a.subj    ! = " + SQLString.Format(p_subj);
            sql = sql + "   and a.subj    ! = 'COMMON' ";
            sql = sql + "   and b.gubun    = " + SQLString.Format("0011");
            sql = sql + "   and b.levels    =  0 ";
            sql = sql + " order by a.subj, a.damunnum ";
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

    public ArrayList selectQuestionPoolList(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        try
        {
            String ss_searchtype = box.getString("s_searchtype");
            String ss_searchtext = box.getString("s_searchtext");
            String v_action = box.getString("p_action");
            String v_subj = box.getString("p_subj");
            String v_grcode = box.getString("p_grcode");
            list = new ArrayList();
            if(v_action.equals("go"))
            {
                connMgr = new DBConnectionManager();
                sql = "select a.subj,     a.damunnum,  a.grcode, ";
                sql = sql + "       a.damuntype,  b.codenm  damuntypenm,  ";
                sql = sql + "       a.damuntext,    ";
                sql = sql + "       d.subjnm    subjnm    ";
                sql = sql + "  from tz_damun    a, ";
                sql = sql + "       tz_code   b, ";
                sql = sql + "       tz_subj   d  ";
                sql = sql + "   where a.subj  = d.subj ";
                sql = sql + "   and a.damuntype  = b.code ";
                sql = sql + "   and a.grcode    = " + SQLString.Format(v_grcode);
                sql = sql + "   and a.subj    ! = " + SQLString.Format(v_subj);
                sql = sql + "   and a.subj    ! = 'COMMON' ";
                sql = sql + "   and b.gubun    = " + SQLString.Format("0011");
                sql = sql + "   and b.levels    =  0 ";
                if(ss_searchtype.equals("1"))
                    sql = sql + "  and (lower(d.subjnm) like " + SQLString.Format("%" + ss_searchtext + "%") + " or upper(d.subjnm) like " + SQLString.Format("%" + ss_searchtext + "%") + ")";
                else
                if(ss_searchtype.equals("2"))
                    sql = sql + "  and (lower(a.damuntext) like " + SQLString.Format("%" + ss_searchtext + "%") + " or upper(a.damuntext) like " + SQLString.Format("%" + ss_searchtext + "%") + ")";
                sql = sql + " order by a.subj, a.damunnum ";
                for(ls = connMgr.executeQuery(sql); ls.next(); list.add(dbox))
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
            if(connMgr != null)
                try
                {
                    connMgr.freeConnection();
                }
                catch(Exception exception2) { }
        }
        return list;
    }

    public int insertQuestionPool(RequestBox box)
        throws Exception
    {
        int isOk;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        StringTokenizer st = null;
        String v_tokens = "";
        ArrayList list = new ArrayList();
        DataBox dbox = null;
        String v_subj = box.getString("p_subj");
        String v_grcode = box.getString("p_grcode");
        String v_luserid = box.getSession("userid");
        int v_damunnum = 0;
        String s_subj = "";
        int s_damunnum = 0;
        Vector v_checks = box.getVector("p_checks");
        try
        {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            v_damunnum = getDamunSeq(v_subj, v_grcode);
            for(int i = 0; i < v_checks.size(); i++)
            {
                v_tokens = (String)v_checks.elementAt(i);
                st = new StringTokenizer(v_tokens, "|");
                s_subj = st.nextToken();
                s_damunnum = Integer.parseInt(st.nextToken());
                list = getExampleData(connMgr, s_subj, v_grcode, s_damunnum);
                dbox = (DataBox)list.get(0);
                String v_damuntype = dbox.getString("d_damuntype");
                String v_damuntext = dbox.getString("d_damuntext");
                int v_selcount = dbox.getInt("d_selcount");
                int v_selmax = dbox.getInt("d_selmax");
                int v_fscalecode = dbox.getInt("d_fscalecode");
                int v_sscalecode = dbox.getInt("d_sscalecode");
                isOk = insertTZ_damun(connMgr, v_subj, v_grcode, v_damunnum, v_damuntype, v_damuntext, v_selcount, v_selmax, v_fscalecode, v_sscalecode, v_luserid);
                sql = "insert into TZ_DAMUNSEL(subj, grcode, damunnum, selnum, seltext, scalename, selpoint,  luserid, ldate) ";
                sql = sql + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                pstmt = connMgr.prepareStatement(sql);
                for(int j = 0; j < list.size(); j++)
                {
                    dbox = (DataBox)list.get(j);
                    int v_selnum = dbox.getInt("d_selnum");
                    String v_seltext = dbox.getString("d_seltext");
                    String v_scalename = dbox.getString("d_scalename");
                    int v_selpoint = dbox.getInt("d_selpoint");
                    isOk = insertTZ_damunsel(pstmt, v_subj, v_grcode, v_damunnum, v_selnum, v_seltext, v_scalename, v_selpoint, v_luserid);
                }

                v_damunnum++;
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

    public ArrayList getExampleData(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_damunnum)
        throws Exception
    {
        ArrayList list;
        list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        try
        {
            sql = "select a.subj,     a.damunnum, a.selcount,  a.selmax, a.fscalecode, a.sscalecode, ";
            sql = sql + "       a.damuntype,  d.codenm damuntypenm, ";
            sql = sql + "       a.damuntext,  b.selnum, b.seltext, b.scalename, b.selpoint ";
            sql = sql + "  from tz_damun     a, ";
            sql = sql + "       tz_damunsel  b, ";
            sql = sql + "       tz_code    d  ";
            sql = sql + " where a.subj     = b.subj(+)    ";
            sql = sql + "   and a.grcode   = b.grcode(+)  ";
            sql = sql + "   and a.damunnum   = b.damunnum(+)  ";
            sql = sql + "   and a.damuntype  = d.code ";
            sql = sql + "   and a.subj     = " + SQLString.Format(p_subj);
            sql = sql + "   and a.grcode     = " + SQLString.Format(p_grcode);
            sql = sql + "   and a.damunnum = " + SQLString.Format(p_damunnum);
            sql = sql + "   and d.gubun    = " + SQLString.Format("0011");
            sql = sql + "   and d.levels    =  0 ";
            sql = sql + " order by a.subj, a.damunnum, b.selnum ";
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
}
