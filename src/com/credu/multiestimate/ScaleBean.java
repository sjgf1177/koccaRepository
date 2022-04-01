// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ScaleBean.java

package com.credu.multiestimate;

import com.credu.library.*;
import com.credu.system.SelectionUtil;
import java.sql.PreparedStatement;
import java.util.*;

public class ScaleBean
{

    public ScaleBean()
    {
    }

    public ArrayList selectScaleList(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_grcode = box.getString("p_grcode");
        String v_scaletype = box.getStringDefault("p_scaletype", "D");
        try
        {
            connMgr = new DBConnectionManager();
            list = selectScaleList(connMgr, v_grcode, v_scaletype);
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

    public ArrayList selectScaleList(DBConnectionManager connMgr, String p_grcode, String p_scaletype)
        throws Exception
    {
        ArrayList list;
        list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        try
        {
            sql = "select a.scalecode,  a.grcode, ";
            sql = sql + "       a.s_gubun, a.scaletype, a.scalename ";
            sql = sql + "  from tz_scale    a ";
            sql = sql + " where a.isdel = 'N' ";
            sql = sql + "   and a.scaletype    = " + SQLString.Format(p_scaletype);
            sql = sql + " order by a.ldate desc ";
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

    public ArrayList selectScaleExample(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_grcode = box.getString("p_grcode");
        String v_scaletype = box.getStringDefault("p_scaletype", "D");
        int v_scalecode = box.getInt("p_scalecode");
        try
        {
            if(v_scalecode > 0)
            {
                connMgr = new DBConnectionManager();
                list = getSelnums(connMgr, v_scalecode);
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

    public ArrayList selectScaleGubunExample(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = new ArrayList();
        ArrayList blist1 = new ArrayList();
        ArrayList blist2 = new ArrayList();
        String v_grcode = box.getString("p_grcode");
        String v_scaletype = box.getStringDefault("p_scaletype", "D");
        int v_damuntype = box.getInt("p_damuntype");
        int v_scalecode = box.getInt("p_scalecode");
        int v_fscalecode = box.getInt("p_fscalecode");
        int v_sscalecode = box.getInt("p_sscalecode");
        try
        {
            if(v_damuntype == 5 || v_damuntype == 6)
            {
                connMgr = new DBConnectionManager();
                blist1 = getSelnums(connMgr, v_scalecode);
                list.add(blist1);
                list.add(blist2);
            } else
            if(v_damuntype == 7)
            {
                connMgr = new DBConnectionManager();
                blist1 = getSelnums(connMgr, v_fscalecode);
                blist2 = getSelnums(connMgr, v_sscalecode);
                list.add(blist1);
                list.add(blist2);
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

    public ArrayList getSelnums(DBConnectionManager connMgr, int p_scalecode)
        throws Exception
    {
        ArrayList list;
        list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        try
        {
            sql = "select a.scalecode, a.s_gubun, a.scalename, b.selnum, b.selpoint, b.seltext ";
            sql = sql + "  from tz_scale     a, ";
            sql = sql + "       tz_scalesel  b  ";
            sql = sql + " where a.scalecode   = b.scalecode(+)    ";
            sql = sql + "   and a.scalecode = " + SQLString.Format(p_scalecode);
            sql = sql + " order by b.selnum ";
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

    public int insertTZ_scale(DBConnectionManager connMgr, int p_scalecode, String p_grcode, String p_sgubun, String p_scaletype, String p_scalename, String p_luserid)
        throws Exception
    {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        try
        {
            sql = "insert into TZ_SCALE(scalecode, grcode, s_gubun, scaletype, scalename, luserid, ldate, isdel) ";
            sql = sql + " values (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, p_scalecode);
            pstmt.setString(2, p_grcode);
            pstmt.setString(3, p_sgubun);
            pstmt.setString(4, p_scaletype);
            pstmt.setString(5, p_scalename);
            pstmt.setString(6, p_luserid);
            pstmt.setString(7, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(8, "N");
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

    public int updateTZ_scale(DBConnectionManager connMgr, int p_scalecode, String p_grcode, String p_sgubun, String p_scaletype, String p_scalename, String p_luserid)
        throws Exception
    {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        try
        {
            sql = " update TZ_SCALE ";
            sql = sql + "    set s_gubun = ?, ";
            sql = sql + "        scaletype  = ?, ";
            sql = sql + "        scalename  = ?, ";
            sql = sql + "        luserid  = ?, ";
            sql = sql + "        ldate    = ?  ";
            sql = sql + "  where scalecode     = ?  ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_sgubun);
            pstmt.setString(2, p_scaletype);
            pstmt.setString(3, p_scalename);
            pstmt.setString(4, p_luserid);
            pstmt.setString(5, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setInt(6, p_scalecode);
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

    public int deleteTZ_scale(DBConnectionManager connMgr, int p_scalecode, String duserid)
        throws Exception
    {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls = null;
        isOk = 0;
        try
        {
            sql = " select fscalecode from tz_damun where fscalecode='" + SQLString.Format(p_scalecode) + "' ";
            ls = connMgr.executeQuery(sql);
            if(ls.next())
                isOk = -2;
            if(isOk == 0)
            {
                sql = " delete tz_scale";
                sql = sql + "  where scalecode     = ?  ";
                pstmt = connMgr.prepareStatement(sql);
                pstmt.setInt(1, p_scalecode);
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

    public int insertTZ_scalesel(PreparedStatement pstmt, int p_scalecode, int p_selnum, int p_selpoint, String p_seltext, String p_luserid)
        throws Exception
    {
        int isOk = 0;
        try
        {
            pstmt.setInt(1, p_scalecode);
            pstmt.setInt(2, p_selnum);
            pstmt.setInt(3, p_selpoint);
            pstmt.setString(4, p_seltext);
            pstmt.setString(5, p_luserid);
            pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
            isOk = pstmt.executeUpdate();
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return isOk;
    }

    public int deleteTZ_scalesel(DBConnectionManager connMgr, int p_scalecode)
        throws Exception
    {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        try
        {
            sql = " delete from TZ_SCALESEL ";
            sql = sql + "  where scalecode     = ?  ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, p_scalecode);
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

    public int insertScale(RequestBox box)
        throws Exception
    {
        int isOk;
        DBConnectionManager connMgr = null;
        ErrorManager.systemOutPrintln(box);
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        int v_scalecode = 0;
        String v_scaletype = box.getStringDefault("p_scaletype", "D");
        String v_grcode = box.getString("p_grcode");
        String v_sgubun = box.getString("p_sgubun");
        String v_scalename = box.getString("p_scalename");
        int v_selnum = 0;
        int v_selpoint = 0;
        Vector v_selpoints = null;
        String v_seltext = "";
        Vector v_seltexts = null;
        Enumeration em1 = null;
        Enumeration em2 = null;
        if(v_sgubun.equals("5"))
        {
            v_seltexts = box.getVector("p_seltext1");
            v_selpoints = box.getVector("p_selpoint1");
            em1 = v_seltexts.elements();
            em2 = v_selpoints.elements();
        } else
        if(v_sgubun.equals("7"))
        {
            v_seltexts = box.getVector("p_seltext2");
            v_selpoints = box.getVector("p_selpoint2");
            em1 = v_seltexts.elements();
            em2 = v_selpoints.elements();
        }
        String v_luserid = box.getSession("userid");
        try
        {
            v_scalecode = getScaleSeq();
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            isOk = insertTZ_scale(connMgr, v_scalecode, v_grcode, v_sgubun, v_scaletype, v_scalename, v_luserid);
            sql = "insert into TZ_SCALESEL(scalecode, selnum, selpoint, seltext, luserid, ldate) ";
            sql = sql + " values (?, ?, ?, ?, ?, ?)";
            pstmt = connMgr.prepareStatement(sql);
            while(em1.hasMoreElements() && em2.hasMoreElements()) 
            {
                v_seltext = (String)em1.nextElement();
                v_selpoint = Integer.parseInt((String)em2.nextElement());
                if(!v_seltext.trim().equals(""))
                {
                    v_selnum++;
                    isOk = insertTZ_scalesel(pstmt, v_scalecode, v_selnum, v_selpoint, v_seltext, v_luserid);
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

    public int updateScale(RequestBox box)
        throws Exception
    {
        int isOk;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        ListSet ls = null;
        int v_scalecode = box.getInt("p_scalecode");
        String v_scaletype = box.getString("p_scaletype");
        String v_grcode = box.getString("p_grcode");
        String v_sgubun = box.getString("p_sgubun");
        String v_scalename = box.getString("p_scalename");
        int v_selnum = 0;
        int v_selpoint = 0;
        Vector v_selpoints = null;
        String v_seltext = "";
        Vector v_seltexts = null;
        Enumeration em1 = null;
        Enumeration em2 = null;
        if(v_sgubun.equals("5"))
        {
            v_seltexts = box.getVector("p_seltext1");
            v_selpoints = box.getVector("p_selpoint1");
            em1 = v_seltexts.elements();
            em2 = v_selpoints.elements();
        } else
        if(v_sgubun.equals("7"))
        {
            v_seltexts = box.getVector("p_seltext2");
            v_selpoints = box.getVector("p_selpoint2");
            em1 = v_seltexts.elements();
            em2 = v_selpoints.elements();
        }
        String v_luserid = box.getSession("userid");
        try
        {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            sql = " select fscalecode from tz_damun where fscalecode='" + SQLString.Format(v_scalecode) + "' ";
            ls = connMgr.executeQuery(sql);
            if(ls.next())
                isOk = -2;
            if(isOk == 0)
            {
                isOk = updateTZ_scale(connMgr, v_scalecode, v_grcode, v_sgubun, v_scaletype, v_scalename, v_luserid);
                isOk = deleteTZ_scalesel(connMgr, v_scalecode);
                sql = "insert into TZ_SCALESEL(scalecode, selnum, selpoint, seltext, luserid, ldate) ";
                sql = sql + " values (?, ?, ?, ?, ?, ?)";
                pstmt = connMgr.prepareStatement(sql);
                while(em1.hasMoreElements() && em2.hasMoreElements()) 
                {
                    v_seltext = (String)em1.nextElement();
                    v_selpoint = Integer.parseInt((String)em2.nextElement());
                    if(!v_seltext.trim().equals(""))
                    {
                        v_selnum++;
                        isOk = insertTZ_scalesel(pstmt, v_scalecode, v_selnum, v_selpoint, v_seltext, v_luserid);
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

    public int deleteScale(RequestBox box)
        throws Exception
    {
        int isOk;
        DBConnectionManager connMgr = null;
        isOk = 0;
        int v_scalecode = box.getInt("p_scalecode");
        String v_duserid = box.getSession("userid");
        try
        {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            isOk = deleteTZ_scale(connMgr, v_scalecode, v_duserid);
            if(isOk > 0)
                isOk = deleteTZ_scalesel(connMgr, v_scalecode);
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
                box.put("p_scalecode", String.valueOf("0"));
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

    public int getScaleSeq()
        throws Exception
    {
        Hashtable maxdata = new Hashtable();
        maxdata.put("seqcolumn", "scalecode");
        maxdata.put("seqtable", "tz_scale");
        maxdata.put("paramcnt", "0");
        return SelectionUtil.getSeq(maxdata);
    }

    public String getGrcode(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq)
        throws Exception
    {
        String v_grcode;
        v_grcode = "";
        ListSet ls = null;
        String sql = "";
        try
        {
            sql = "select grcode ";
            sql = sql + "  from tz_subjseq  ";
            sql = sql + " where subj    = " + SQLString.Format(p_subj);
            sql = sql + "   and year    = " + SQLString.Format(p_year);
            sql = sql + "   and subjseq = " + SQLString.Format(p_subjseq);
            for(ls = connMgr.executeQuery(sql); ls.next();)
                v_grcode = ls.getString("grcode");

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
        return v_grcode;
    }

    public static String getScaleCodeSelect(String s_gubun, String grcode, String scaletype, String name, int selected, String event)
        throws Exception
    {
        String result;
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        result = null;
        String sql = "";
        DataBox dbox = null;
        result = "  <SELECT name=" + name + " " + event + " > \n";
        result = result + " <option value='0'>\uCC99\uB3C4\uB97C \uC120\uD0DD\uD558\uC138\uC694.</option> \n";
        try
        {
            connMgr = new DBConnectionManager();
            sql = " select scalecode, scalename from tz_scale            ";
            sql = sql + "  where s_gubun  = " + StringManager.makeSQL(s_gubun);
            sql = sql + "    and scaletype = " + StringManager.makeSQL(scaletype);
            sql = sql + "    and isdel = 'N'";
            sql = sql + " order by scalecode asc";
            for(ls = connMgr.executeQuery(sql); ls.next();)
            {
                dbox = ls.getDataBox();
                result = result + " <option value=" + dbox.getInt("d_scalecode");
                if(selected == dbox.getInt("d_scalecode"))
                    result = result + " selected ";
                result = result + ">" + dbox.getString("d_scalename") + "</option> \n";
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
            if(connMgr != null)
                try
                {
                    connMgr.freeConnection();
                }
                catch(Exception exception2) { }
        }
        result = result + "  </SELECT> \n";
        return result;
    }

    public static final String SPLIT_COMMA = ",";
    public static final String SPLIT_COLON = ":";
    public static final String DEFAULT_GRCODE = "N000001";
    public static final String DEFAULT_TYPE = "D";
}
