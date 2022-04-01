// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SelectCompBean.java

package com.credu.multiestimate;

import com.credu.common.GetCodenm;
import com.credu.library.*;
import java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;

public class SelectCompBean
{

    public SelectCompBean()
    {
    }

    public static String getCompany(RequestBox box, boolean isChange, boolean isALL)
        throws Exception
    {
        String result;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        result = "";
        try
        {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");
            String ss_company = box.getStringDefault("s_company", "ALL");
            connMgr = new DBConnectionManager();
            if(v_gadmin.equals("H"))
            {
                sql = "select distinct a.comp, a.companynm";
                sql = sql + " from tz_comp a, tz_grcomp b,";
                sql = sql + " (select grcode from tz_grcodeman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin) + ") c";
                sql = sql + " where a.comp = b.comp and b.grcode = c.grcode and a.comptype = '2'";
                sql = sql + " order by a.comp";
            } else
            if(v_gadmin.equals("K"))
            {
                sql = "select distinct a.comp, a.companynm";
                sql = sql + " from tz_comp a, tz_compman b";
                sql = sql + " where substr(a.comp, 1, 4) = substr(b.comp, 1, 4) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " + SQLString.Format(s_gadmin);
                sql = sql + "  and a.comptype = '2' order by a.comp";
            } else
            {
                sql = "select distinct comp, companynm";
                sql = sql + " from tz_comp where comptype = '2'";
                sql = sql + " order by comp";
            }
            ls = connMgr.executeQuery(sql);
            if(v_gadmin.equals("A") || v_gadmin.equals("F") || v_gadmin.equals("P") || v_gadmin.equals("H"))
                result = getSelectTag(ls, isChange, true, "s_company", ss_company);
            else
                result = getSelectTag(ls, isChange, false, "s_company", ss_company);
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
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
            if(pstmt != null)
                try
                {
                    pstmt.close();
                }
                catch(Exception exception2) { }
            if(connMgr != null)
                try
                {
                    connMgr.freeConnection();
                }
                catch(Exception exception3) { }
        }
        return result;
    }

    public static String getGpm(RequestBox box, boolean isChange, boolean isALL)
        throws Exception
    {
        String result;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        result = "";
        try
        {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");
            String ss_company = box.getStringDefault("s_company", "ALL");
            String ss_gpm = box.getStringDefault("s_gpm", "ALL");
            connMgr = new DBConnectionManager();
            if(s_gadmin.equals("K6") || s_gadmin.equals("K7"))
            {
                sql = "select distinct a.comp, a.gpmnm";
                sql = sql + " from tz_comp a, tz_compman b";
                sql = sql + " where substr(a.comp, 1, 6) = substr(b.comp, 1, 6) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " + SQLString.Format(s_gadmin);
                if(!ss_company.equals("ALL"))
                    sql = sql + " and a.comp like '" + GetCodenm.get_compval(ss_company) + "'";
                sql = sql + " and a.comptype = '3' order by a.gpmnm";
            } else
            {
                sql = "select distinct comp, gpmnm";
                sql = sql + " from tz_comp where comptype = '3'";
                if(!ss_company.equals("ALL"))
                    sql = sql + " and comp like '" + GetCodenm.get_compval(ss_company) + "'";
                sql = sql + " order by gpmnm";
            }
            ls = connMgr.executeQuery(sql);
            if(!s_gadmin.equals("K6") && !s_gadmin.equals("K7"))
                result = getSelectTag(ls, isChange, true, "s_gpm", ss_gpm);
            else
                result = getSelectTag(ls, isChange, false, "s_gpm", ss_gpm);
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
        return result;
    }

    public static String getDept(RequestBox box, boolean isChange, boolean isALL)
        throws Exception
    {
        String result;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        result = "";
        try
        {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");
            String ss_company = box.getStringDefault("s_company", "ALL");
            String ss_gpm = box.getStringDefault("s_gpm", "ALL");
            String ss_dept = box.getStringDefault("s_dept", "ALL");
            connMgr = new DBConnectionManager();
            if(s_gadmin.equals("K6") || s_gadmin.equals("K7"))
            {
                sql = "select distinct a.comp, a.deptnm";
                sql = sql + " from tz_comp a, tz_compman b";
                sql = sql + " where substr(a.comp, 1, 8) = substr(b.comp, 1, 8) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " + SQLString.Format(s_gadmin);
                if(!ss_gpm.equals("ALL"))
                    sql = sql + " and a.comp like '" + GetCodenm.get_compval(ss_gpm) + "'";
                sql = sql + " and a.comptype = '4'  order by a.deptnm";
            } else
            {
                sql = "select distinct comp, deptnm";
                sql = sql + " from tz_comp where comptype = '4'";
                if(!ss_gpm.equals("ALL"))
                    sql = sql + " and comp like '" + GetCodenm.get_compval(ss_gpm) + "'";
                sql = sql + " order by deptnm";
            }
            ls = connMgr.executeQuery(sql);
            if(!s_gadmin.equals("K6") && !s_gadmin.equals("K7"))
                result = getSelectTag(ls, isChange, true, "s_dept", ss_dept);
            else
                result = getSelectTag(ls, isChange, false, "s_dept", ss_dept);
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
        return result;
    }

    public static String getPart(RequestBox box, boolean isChange, boolean isALL)
        throws Exception
    {
        String result;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        result = "";
        try
        {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");
            String ss_company = box.getStringDefault("s_company", "ALL");
            String ss_gpm = box.getStringDefault("s_gpm", "ALL");
            String ss_dept = box.getStringDefault("s_dept", "ALL");
            String ss_part = box.getStringDefault("s_part", "ALL");
            connMgr = new DBConnectionManager();
            if(s_gadmin.equals("K6") || s_gadmin.equals("K7"))
            {
                sql = "select distinct a.comp, a.partnm";
                sql = sql + " from tz_comp a, tz_compman b";
                sql = sql + " where substr(a.comp, 1, 8) = substr(b.comp, 1, 8) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " + SQLString.Format(s_gadmin);
                if(!ss_part.equals("ALL"))
                    sql = sql + " and a.comp like '" + GetCodenm.get_compval(ss_part) + "'";
                sql = sql + " and a.comptype = '5'  order by a.partnm";
            } else
            {
                sql = "select distinct comp, partnm";
                sql = sql + " from tz_comp where comptype = '5'";
                if(!ss_dept.equals("ALL"))
                    sql = sql + " and comp like '" + GetCodenm.get_compval(ss_dept) + "'";
                sql = sql + " order by partnm";
            }
            ls = connMgr.executeQuery(sql);
            if(!s_gadmin.equals("K6") && !s_gadmin.equals("K7"))
                result = getSelectTag(ls, isChange, true, "s_part", ss_part);
            else
                result = getSelectTag(ls, isChange, false, "s_part", ss_part);
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
        return result;
    }

    public static String getJikwi(RequestBox box, boolean isChange, boolean isALL)
        throws Exception
    {
        String result;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        result = "";
        try
        {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String ss_jikwi = box.getStringDefault("s_jikwi", "ALL");
            String v_company = box.getStringDefault("s_company", "ALL");
            connMgr = new DBConnectionManager();
            sql = "select distinct jikwi, jikwinm";
            sql = sql + " from tz_jikwi";
            if(!v_company.equals("ALL"))
                sql = sql + " where grpcomp = substr('" + v_company + "', 1, 4)";
            sql = sql + " order by jikwinm";
            ls = connMgr.executeQuery(sql);
            result = getSelectTag(ls, isChange, isALL, "s_jikwi", ss_jikwi);
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
        return result;
    }

    public static String getJikup(RequestBox box, boolean isChange, boolean isALL)
        throws Exception
    {
        String result;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        result = "";
        try
        {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String ss_jikup = box.getStringDefault("s_jikup", "ALL");
            String v_company = box.getStringDefault("s_company", "ALL");
            connMgr = new DBConnectionManager();
            sql = "select distinct jikup, jikupnm";
            sql = sql + " from tz_jikup";
            if(!v_company.equals("ALL"))
                sql = sql + " where grpcomp = substr('" + v_company + "', 1, 4)";
            sql = sql + " order by jikupnm";
            ls = connMgr.executeQuery(sql);
            result = getSelectTag(ls, isChange, isALL, "s_jikup", ss_jikup);
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
        return result;
    }

    public static String getJikun(RequestBox box, boolean isChange, boolean isALL)
        throws Exception
    {
        String result;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        result = "";
        try
        {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String ss_jikun = box.getStringDefault("s_jikun", "ALL");
            String v_company = box.getStringDefault("s_company", "ALL");
            connMgr = new DBConnectionManager();
            sql = "select distinct jikun, jikunnm";
            sql = sql + " from tz_jikun";
            if(!v_company.equals("ALL"))
                sql = sql + " where grpcomp = substr('" + v_company + "', 1, 4)";
            sql = sql + " order by jikunnm";
            ls = connMgr.executeQuery(sql);
            result = getSelectTag(ls, isChange, isALL, "s_jikun", ss_jikun);
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
        return result;
    }

    public static String getWorkplc(RequestBox box, boolean isChange, boolean isALL)
        throws Exception
    {
        String result;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        result = "";
        try
        {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String ss_workplc = box.getStringDefault("s_workplc", "ALL");
            String v_company = box.getStringDefault("s_company", "ALL");
            connMgr = new DBConnectionManager();
            sql = "select distinct work_plc, work_plcnm";
            sql = sql + " from tz_workplc";
            if(!v_company.equals("ALL"))
                sql = sql + " where grpcomp = substr('" + v_company + "', 1, 4)";
            sql = sql + " order by work_plcnm";
            ls = connMgr.executeQuery(sql);
            result = getSelectTag(ls, isChange, isALL, "s_workplc", ss_workplc);
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
        return result;
    }

    public static String getSelectTag(ListSet ls, boolean isChange, boolean isALL, String selname, String optionselected)
        throws Exception
    {
        StringBuffer sb = null;
        try
        {
            sb = new StringBuffer();
            sb.append("<select name = \"" + selname + "\"");
            if(isChange)
                sb.append(" onChange = \"javascript:whenSelection('change')\"");
            sb.append(">\r\n");
            if(isALL)
                sb.append("<option value = \"ALL\">ALL</option>\r\n");
            else
            if(isChange)
                sb.append("<option value = \"----\">----</option>\r\n");
            int columnCount;
            for(; ls.next(); sb.append(">" + ls.getString(columnCount) + "</option>\r\n"))
            {
                ResultSetMetaData meta = ls.getMetaData();
                columnCount = meta.getColumnCount();
                sb.append("<option value = \"" + ls.getString(1) + "\"");
                if(optionselected.equals(ls.getString(1)))
                    sb.append(" selected");
            }

            sb.append("</select>\r\n");
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return sb.toString();
    }
}
