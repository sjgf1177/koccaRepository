// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunSubjPaperBean.java

package com.credu.multiestimate;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.system.SelectionUtil;

public class DamunSubjPaperBean {

    public DamunSubjPaperBean() {
    }

    public ArrayList<DataBox> selectQuestionList(RequestBox box) throws Exception {
        ArrayList<DataBox> list;
        DBConnectionManager connMgr = null;
        list = null;
        // String v_gubun = box.getStringDefault("p_gubun", "SUBJ");
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        // String v_distcode = box.getStringDefault("s_distcode", "ALL");
        String v_grcode = box.getString("s_grcode");
        String v_d_gubun = box.getStringDefault("p_d_gubun", "1");
        String v_action = box.getStringDefault("p_action", "change");
        String v_damuntype = "";
        if (v_d_gubun.equals("2"))
            v_damuntype = "7";
        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = selectQuestionList(connMgr, v_grcode, v_subj, v_damuntype);
            } else {
                list = new ArrayList<DataBox>();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null)
                try {
                    connMgr.freeConnection();
                } catch (Exception exception1) {
                }
        }
        return list;
    }

    public ArrayList<DataBox> selectQuestionList(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_damuntype) throws Exception {
        ArrayList<DataBox> list;
        list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        try {
            sql = "select a.subj,     a.damunnum,  a.grcode, ";
            sql = sql + "       a.damuntype,  b.codenm  damuntypenm,  ";
            sql = sql + "       a.damuntext    ";
            sql = sql + "  from tz_damun    a, ";
            sql = sql + "       tz_code   b ";
            sql = sql + " where b.levels = 0 ";
            sql = sql + "   and a.damuntype  = b.code ";
            sql = sql + "   and a.grcode    = " + SQLString.Format(p_grcode);
            sql = sql + "   and b.gubun    = " + SQLString.Format("0011");
            if (!p_subj.equals("ALL"))
                sql = sql + "   and a.subj     = " + SQLString.Format(p_subj);
            if (!p_damuntype.equals(""))
                sql = sql + "   and a.damuntype   ! = " + SQLString.Format(p_damuntype);
            sql = sql + " order by a.damunnum ";
            for (ls = connMgr.executeQuery(sql); ls.next(); list.add(dbox))
                dbox = ls.getDataBox();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null)
                try {
                    ls.close();
                } catch (Exception exception1) {
                }
        }
        return list;
    }

    public ArrayList<DataBox> selectPaperQuestionList(RequestBox box) throws Exception {
        ArrayList<DataBox> list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_gubun = box.getStringDefault("p_gubun", "SUBJ");
        String v_grcode = box.getString("p_grcode");
        String v_subj = box.getString("p_subj");
        int v_damunpapernum = box.getInt("p_damunpapernum");
        try {
            connMgr = new DBConnectionManager();
            if (!v_gubun.equals("SUBJ"))
                v_subj = v_gubun;
            list = selectPaperQuestionList(connMgr, v_grcode, v_subj, v_damunpapernum, box);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null)
                try {
                    connMgr.freeConnection();
                } catch (Exception exception1) {
                }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectPaperQuestionList(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_damunpapernum, RequestBox box) throws Exception {
        ArrayList<DataBox> list;
        list = new ArrayList<DataBox>();
        Hashtable<String, DataBox> hash = new Hashtable<String, DataBox>();
        StringTokenizer st = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_damunnums = "";
        String v_damunnum = "";
        String v_damunpapernm = "";
        try {
            sql = "select damunpapernm, totcnt, damunnums";
            sql = sql + "  from tz_damunpaper ";
            sql = sql + " where subj        = " + SQLString.Format(p_subj);
            sql = sql + "   and damunpapernum = " + SQLString.Format(p_damunpapernum);
            for (ls = connMgr.executeQuery(sql); ls.next();) {
                v_damunnums = ls.getString("damunnums");
                v_damunpapernm = ls.getString("damunpapernm");
            }

            if (box != null)
                box.put("p_damunpapernm", v_damunpapernm);
            if (v_damunnums.length() > 0) {
                sql = "select a.subj,     a.damunnum,  ";
                sql = sql + "       a.damuntype,  b.codenm  damuntypenm,  ";
                sql = sql + "       a.damuntext    ";
                sql = sql + "  from tz_damun    a, ";
                sql = sql + "       tz_code   b ";
                sql = sql + " where b.levels = 0 ";
                sql = sql + "   and a.damuntype  = b.code ";
                sql = sql + "   and b.gubun    = " + SQLString.Format("0011");
                sql = sql + "   and a.subj     = " + SQLString.Format(p_subj);
                if (v_damunnums.equals(""))
                    v_damunnums = "-1";
                sql = sql + "   and a.damunnum in (" + v_damunnums + ")";
                sql = sql + " order by a.damunnum ";
                ls.close();
                ls = connMgr.executeQuery(sql);
                st = new StringTokenizer(v_damunnums, ",");
                for (; ls.next(); hash.put(v_damunnum, dbox)) {
                    dbox = ls.getDataBox();
                    v_damunnum = String.valueOf(ls.getInt("damunnum"));
                }

                while (st.hasMoreElements()) {
                    v_damunnum = st.nextToken();
                    dbox = (DataBox) hash.get(v_damunnum);
                    if (dbox != null)
                        list.add(dbox);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null)
                try {
                    ls.close();
                } catch (Exception exception1) {
                }
        }
        return list;
    }

    public String getPaperListSQL(String p_grcode, String p_subj, String p_gyear, String p_subjsel, String p_upperclass, int p_damunpapernum) throws Exception {
        String sql = "";
        sql = "select a.grcode,      b.subj,         ";
        sql = sql + "        a.damunpapernum, a.damunpapernm, a.year, ";
        sql = sql + "       a.totcnt,      a.damunnums,  a.d_gubun, a.fdamunstart, a.fdamunend, a.sdamunstart, a.sdamunend,   ";
        sql = sql + "       b.subjnm        ";
        sql = sql + "  from tz_damunpaper  a, ";
        sql = sql + "       tz_subj      b  ";
        sql = sql + " where  a.subj(+) = b.subj ";
        sql = sql + "    and  a.grcode  = " + SQLString.Format(p_grcode);
        sql = sql + "    and  a.year  = " + SQLString.Format(p_gyear);
        if (!p_upperclass.equals("ALL"))
            sql = sql + "   and b.upperclass   = " + SQLString.Format(p_upperclass);
        if (!p_subjsel.equals("ALL"))
            sql = sql + "   and b.subj   = " + SQLString.Format(p_subjsel);
        if (p_damunpapernum > 0)
            sql = sql + "   and a.damunpapernum   = " + SQLString.Format(p_damunpapernum);
        sql = sql + " order by b.subj, a.damunpapernum ";
        System.out.println(sql);
        return sql;
    }

    public ArrayList<DataBox> selectPaperList(RequestBox box) throws Exception {
        ArrayList<DataBox> list;
        DBConnectionManager connMgr = null;
        list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_gyear");
        String v_action = box.getStringDefault("p_action", "change");
        String v_gubun = box.getStringDefault("p_gubun", "SUBJ");
        String ss_upperclass = box.getStringDefault("s_upperclass", "ALL");
        // String ss_middleclass = box.getStringDefault("s_middleclass", "ALL");
        // String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");
        try {
            if (v_action.equals("go")) {
                sql = getPaperListSQL(v_grcode, v_gubun, v_gyear, ss_subjcourse, ss_upperclass, -1);
                connMgr = new DBConnectionManager();
                for (ls = connMgr.executeQuery(sql); ls.next(); list.add(dbox))
                    dbox = ls.getDataBox();

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null)
                try {
                    ls.close();
                } catch (Exception exception1) {
                }
            if (connMgr != null)
                try {
                    connMgr.freeConnection();
                } catch (Exception exception2) {
                }
        }
        return list;
    }

    public DataBox getPaperData(RequestBox box) throws Exception {
        DataBox dbox;
        DBConnectionManager connMgr = null;
        dbox = null;
        String v_grcode = box.getString("p_grcode");
        String v_subj = box.getString("p_subj");
        String v_gyear = box.getString("p_gyear");
        String v_subjsel = box.getString("p_subj");
        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");
        int v_damunpapernum = box.getInt("p_damunpapernum");
        try {
            connMgr = new DBConnectionManager();
            dbox = getPaperData(connMgr, v_grcode, v_subj, v_gyear, v_subjsel, v_upperclass, v_damunpapernum);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null)
                try {
                    connMgr.freeConnection();
                } catch (Exception exception1) {
                }
        }
        return dbox;
    }

    public DataBox getPaperData(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_gyear, String p_subjsel, String p_upperclass, int p_damunpapernum) throws Exception {
        DataBox dbox;
        ListSet ls = null;
        String sql = "";
        dbox = null;
        try {
            sql = getPaperListSQL(p_grcode, p_subj, p_gyear, p_subjsel, p_upperclass, p_damunpapernum);
            for (ls = connMgr.executeQuery(sql); ls.next();)
                dbox = ls.getDataBox();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null)
                try {
                    ls.close();
                } catch (Exception exception1) {
                }
        }
        if (dbox == null)
            dbox = new DataBox("resoponsebox");
        return dbox;
    }

    public int insertTZ_damunpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_gyear, int p_damunpapernum, String p_d_gubun, String p_damunpapernm, int p_totcnt, String p_damunnums, String p_fdamunstart, String p_fdamunend,
            String p_sdamunstart, String p_sdamunend, String p_luserid) throws Exception {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        try {
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
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception exception1) {
                }
        }
        return isOk;
    }

    public int updateTZ_damunpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_gyear, int p_damunpapernum, String p_d_gubun, String p_damunpapernm, int p_totcnt, String p_damunnums, String p_fdamunstart, String p_fdamunend,
            String p_sdamunstart, String p_sdamunend, String p_luserid) throws Exception {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        try {
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
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception exception1) {
                }
        }
        return isOk;
    }

    public int deleteTZ_damunpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_damunpapernum, String p_duserid) throws Exception {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls = null;
        isOk = 0;
        try {
            sql = " select userid from tz_damuneach where subj='" + p_subj + "' and grcode='" + p_grcode + "' and damunpapernum=" + p_damunpapernum + " ";
            ls = connMgr.executeQuery(sql);
            if (ls.next())
                isOk = -2;
            if (isOk == 0) {
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
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null)
                try {
                    ls.close();
                } catch (Exception exception1) {
                }
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception exception2) {
                }
        }
        return isOk;
    }

    public int insertPaper(RequestBox box) throws Exception {
        int isOk;
        DBConnectionManager connMgr = null;
        isOk = 0;
        String v_grcode = box.getStringDefault("p_grcode", "N000001");
        // String v_gubun = box.getStringDefault("p_gubun", "SUBJ");
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_gyear = box.getString("p_gyear");
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
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            v_damunpapernum = getPapernumSeq(v_subj, v_grcode);
            isOk = insertTZ_damunpaper(connMgr, v_grcode, v_subj, v_gyear, v_damunpapernum, v_d_gubun, v_damunpapernm, v_totcnt, v_damunnums, v_fdamunstart, v_fdamunend, v_sdamunstart, v_sdamunend, v_luserid);
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (isOk > 0)
                connMgr.commit();
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception exception1) {
                }
        }
        return isOk;
    }

    public int updatePaper(RequestBox box) throws Exception {
        int isOk;
        DBConnectionManager connMgr = null;
        isOk = 0;
        String v_grcode = box.getString("p_grcode");
        // String v_gubun = box.getStringDefault("p_gubun", "SUBJ");
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_gyear = box.getString("s_gyear");
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
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            isOk = updateTZ_damunpaper(connMgr, v_grcode, v_subj, v_gyear, v_damunpapernum, v_d_gubun, v_damunpapernm, v_totcnt, v_damunnums, v_fdamunstart, v_fdamunend, v_sdamunstart, v_sdamunend, v_luserid);
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (isOk > 0)
                connMgr.commit();
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception exception1) {
                }
        }
        return isOk;
    }

    public int deletePaper(RequestBox box) throws Exception {
        int isOk;
        DBConnectionManager connMgr = null;
        String sql = "";
        isOk = 0;
        String v_grcode = box.getString("p_grcode");
        // String v_gubun = box.getStringDefault("p_gubun", "SUBJ");
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        int v_damunpapernum = box.getInt("p_damunpapernum");
        String v_duserid = box.getSession("userid");
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            isOk = deleteTZ_damunpaper(connMgr, v_grcode, v_subj, v_damunpapernum, v_duserid);
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0)
                connMgr.commit();
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception exception1) {
                }
        }
        return isOk;
    }

    public int getPapernumSeq(String p_subj, String p_grcode) throws Exception {
        Hashtable<String, String> maxdata = new Hashtable<String, String>();
        maxdata.put("seqcolumn", "damunpapernum");
        maxdata.put("seqtable", "tz_damunpaper");
        maxdata.put("paramcnt", "2");
        maxdata.put("param0", "subj");
        maxdata.put("param1", "grcode");
        maxdata.put("subj", SQLString.Format(p_subj));
        maxdata.put("grcode", SQLString.Format(p_grcode));
        return SelectionUtil.getSeq(maxdata);
    }

    public Vector<String> getSulnums(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_damunpapernum) throws Exception {
        Vector<String> v_damunnums;
        ListSet ls = null;
        String sql = "";
        v_damunnums = new Vector<String>();
        String v_tokens = "";
        StringTokenizer st = null;
        try {
            sql = "select damunnums  ";
            sql = sql + "  from tz_damunpaper ";
            sql = sql + " where grcode      = " + SQLString.Format(p_grcode);
            sql = sql + "   and subj        = " + SQLString.Format(p_subj);
            sql = sql + "   and damunpapernum = " + SQLString.Format(p_damunpapernum);
            for (ls = connMgr.executeQuery(sql); ls.next();)
                v_tokens = ls.getString("damunnums");

            for (st = new StringTokenizer(v_tokens, ","); st.hasMoreElements(); v_damunnums.add(st.nextToken()))
                ;
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null)
                try {
                    ls.close();
                } catch (Exception exception1) {
                }
        }
        return v_damunnums;
    }

    public ArrayList<ArrayList<DataBox>> getSelnums(DBConnectionManager connMgr, String p_subj, Vector<String> p_damunnums) throws Exception {
        ArrayList<ArrayList<DataBox>> blist;
        // Hashtable hash = new Hashtable();
        blist = new ArrayList<ArrayList<DataBox>>();
        ArrayList<DataBox> list = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        StringTokenizer st = null;
        String v_damunnums = "";
        for (int i = 0; i < p_damunnums.size(); i++) {
            v_damunnums = v_damunnums + (String) p_damunnums.get(i);
            if (i < p_damunnums.size() - 1)
                v_damunnums = v_damunnums + ",";
        }

        if (v_damunnums.equals(""))
            v_damunnums = "-1";
        try {
            for (st = new StringTokenizer(v_damunnums, ","); st.hasMoreElements(); blist.add(list)) {
                int damunnum = Integer.parseInt(st.nextToken());
                sql = "select a.subj,     a.damunnum, a.selcount, a.selmax, ";
                sql = sql + "       a.damuntype,  c.codenm damuntypenm, ";
                sql = sql + "       a.damuntext,  b.selnum, b.seltext, b.scalename ";
                sql = sql + "  from tz_damun     a, ";
                sql = sql + "       tz_damunsel  b, ";
                sql = sql + "       tz_code    c ";
                sql = sql + " where a.subj     = b.subj(+)    ";
                sql = sql + "   and a.damunnum   = b.damunnum(+)  ";
                sql = sql + "   and a.damuntype  = c.code ";
                sql = sql + "   and a.subj     = " + SQLString.Format(p_subj);
                sql = sql + "   and a.damunnum = " + damunnum;
                sql = sql + "   and c.gubun    = " + SQLString.Format("0011");
                sql = sql + "    and c.levels  =  0   ";
                sql = sql + " order by a.subj, a.damunnum, b.selnum ";
                ls = connMgr.executeQuery(sql);
                list = new ArrayList<DataBox>();
                for (; ls.next(); list.add(dbox))
                    dbox = ls.getDataBox();

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null)
                try {
                    ls.close();
                } catch (Exception exception1) {
                }
        }
        return blist;
    }

    @SuppressWarnings("unchecked")
    public ArrayList<ArrayList<DataBox>> selectPaperQuestionExampleList(RequestBox box) throws Exception {
        ArrayList<ArrayList<DataBox>> list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_grcode = box.getString("p_grcode");
        String v_subj = box.getString("p_subj");
        int v_damunpapernum = box.getInt("p_damunpapernum");
        try {
            if (v_damunpapernum == 0) {
                v_damunpapernum = getPapernumSeq(v_subj, v_grcode) - 1;
                box.put("p_damunpapernum", String.valueOf(v_damunpapernum));
            }
            connMgr = new DBConnectionManager();
            list = selectPaperQuestionExampleList(connMgr, v_grcode, v_subj, v_damunpapernum, box);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null)
                try {
                    connMgr.freeConnection();
                } catch (Exception exception1) {
                }
        }
        return list;
    }

    public ArrayList<ArrayList<DataBox>> selectPaperQuestionExampleList(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_damunpapernum, RequestBox box) throws Exception {
        Vector<String> v_damunnums = null;
        ArrayList<ArrayList<DataBox>> QuestionExampleDataList = null;
        try {
            v_damunnums = getSulnums(connMgr, p_grcode, p_subj, p_damunpapernum);
            if (!v_damunnums.equals(""))
                QuestionExampleDataList = getSelnums(connMgr, p_subj, v_damunnums);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return QuestionExampleDataList;
    }

    public ArrayList<DataBox> selectPaperPool(RequestBox box) throws Exception {
        ArrayList<DataBox> list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_subj = box.getString("p_subj");
        String v_grcode = box.getString("p_grcode");
        String v_action = box.getStringDefault("p_action", "change");
        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = getPaperPool(connMgr, v_subj, v_grcode);
            } else {
                list = new ArrayList<DataBox>();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null)
                try {
                    connMgr.freeConnection();
                } catch (Exception exception1) {
                }
        }
        return list;
    }

    public ArrayList<DataBox> getPaperPool(DBConnectionManager connMgr, String p_subj, String p_grcode) throws Exception {
        ArrayList<DataBox> list;
        list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        try {
            sql = "select a.grcode,      b.subj,         ";
            sql = sql + "        a.damunpapernum, a.damunpapernm, a.year, ";
            sql = sql + "       a.totcnt,      a.damunnums,  a.fdamunstart, a.fdamunend,  a.sdamunstart,  a.sdamunend, a.d_gubun,  ";
            sql = sql + "       b.subjnm        ";
            sql = sql + "  from tz_damunpaper  a, ";
            sql = sql + "       tz_subj   b  ";
            sql = sql + "   where a.subj  = b.subj ";
            sql = sql + "   and a.grcode    = " + SQLString.Format(p_grcode);
            sql = sql + "   and a.subj    ! = 'COMMON' ";
            sql = sql + " order by a.subj, a.damunpapernum ";
            for (ls = connMgr.executeQuery(sql); ls.next(); list.add(dbox))
                dbox = ls.getDataBox();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null)
                try {
                    ls.close();
                } catch (Exception exception1) {
                }
        }
        return list;
    }

    public ArrayList<DataBox> selectPaperPoolList(RequestBox box) throws Exception {
        ArrayList<DataBox> list;
        DBConnectionManager connMgr = null;
        list = null;
        ListSet ls = null;
        DataBox dbox = null;
        String sql = "";
        try {
            String ss_searchtype = box.getString("s_searchtype");
            String ss_searchtext = box.getString("s_searchtext");
            String v_action = box.getString("p_action");
            // String v_subj = box.getString("p_subj");
            String v_grcode = box.getString("p_grcode");
            list = new ArrayList<DataBox>();
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                sql = "select a.grcode,      b.subj,         ";
                sql = sql + "        a.damunpapernum, a.damunpapernm, a.year, ";
                sql = sql + "       a.totcnt,      a.damunnums,  a.fdamunstart, a.fdamunend,  a.sdamunstart,  a.sdamunend, a.d_gubun,  ";
                sql = sql + "       b.subjnm        ";
                sql = sql + "  from tz_damunpaper  a, ";
                sql = sql + "       tz_subj   b  ";
                sql = sql + "   where a.subj  = b.subj ";
                sql = sql + "   and a.grcode    = " + SQLString.Format(v_grcode);
                sql = sql + "   and a.subj    ! = 'COMMON' ";
                if (ss_searchtype.equals("1"))
                    sql = sql + "  and d.subjnm like " + SQLString.Format("%" + ss_searchtext + "%");
                else if (ss_searchtype.equals("2"))
                    sql = sql + "  and a.damunpapernm like " + SQLString.Format("%" + ss_searchtext + "%");
                sql = sql + " order by a.subj, a.damunpapernum ";
                for (ls = connMgr.executeQuery(sql); ls.next(); list.add(dbox))
                    dbox = ls.getDataBox();

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null)
                try {
                    ls.close();
                } catch (Exception exception1) {
                }
            if (connMgr != null)
                try {
                    connMgr.freeConnection();
                } catch (Exception exception2) {
                }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public int insertPaperPool(RequestBox box) throws Exception {
        int isOk;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        StringTokenizer st = null;
        StringTokenizer st2 = null;
        String v_tokens = "";
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ArrayList<DataBox> list2 = new ArrayList<DataBox>();
        DataBox dbox = null;
        DataBox dbox2 = null;
        String v_subj = box.getString("p_subj");
        String v_grcode = box.getString("p_grcode");
        String v_luserid = box.getSession("userid");
        int v_damunpapernum = 0;
        String s_subj = "";
        int s_damunpapernum = 0;
        Vector v_checks = box.getVector("p_checks");
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            for (int i = 0; i < v_checks.size(); i++) {
                v_tokens = (String) v_checks.elementAt(i);
                st = new StringTokenizer(v_tokens, "|");
                s_subj = st.nextToken();
                s_damunpapernum = Integer.parseInt(st.nextToken());
                list = getPaperData(connMgr, s_subj, v_grcode, s_damunpapernum);
                dbox = (DataBox) list.get(0);
                String v_gyear = dbox.getString("d_year");
                String v_damunpapernm = dbox.getString("d_damunpapernm");
                String v_d_gubun = dbox.getString("d_d_gubun");
                int v_totcnt = dbox.getInt("d_totcnt");
                String s_damunnums = dbox.getString("d_damunnums");
                // String v_fdamunstart = dbox.getString("d_fdamunstart");
                // String v_fdamunend = dbox.getString("d_fdamunend");
                // String v_sdamunstart = dbox.getString("d_sdamunstart");
                // String v_sdamunend = dbox.getString("d_sdamunend");
                String v_damunnums = "";
                int s_damunnum = 0;
                int v_damunnum = getDamunnumSeq(v_subj, v_grcode);
                int v_next = 0;
                v_damunpapernum = getPapernumSeq(v_subj, v_grcode);
                if (!v_subj.equals(s_subj)) {
                    st2 = new StringTokenizer(s_damunnums, ",");
                    int v_token = st2.countTokens();
                    while (st2.hasMoreElements()) {
                        s_damunnum = Integer.parseInt(st2.nextToken());
                        list2 = getExampleData(connMgr, s_subj, v_grcode, s_damunnum);
                        dbox2 = (DataBox) list2.get(0);
                        String v_damuntype = dbox2.getString("d_damuntype");
                        String v_damuntext = dbox2.getString("d_damuntext");
                        int v_selcount = dbox2.getInt("d_selcount");
                        int v_selmax = dbox2.getInt("d_selmax");
                        int v_fscalecode = dbox2.getInt("d_fscalecode");
                        int v_sscalecode = dbox2.getInt("d_sscalecode");
                        int isOk1 = 0;
                        int isOk2 = 0;
                        isOk1 = insertTZ_damun(connMgr, v_subj, v_grcode, v_damunnum, v_damuntype, v_damuntext, v_selcount, v_selmax, v_fscalecode, v_sscalecode, v_luserid);
                        sql = "insert into TZ_DAMUNSEL(subj, grcode, damunnum, selnum, seltext, scalename, selpoint,  luserid, ldate) ";
                        sql = sql + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
                        pstmt = connMgr.prepareStatement(sql);
                        for (int j = 0; j < list2.size(); j++) {
                            dbox2 = (DataBox) list2.get(j);
                            int v_selnum = dbox2.getInt("d_selnum");
                            String v_seltext = dbox2.getString("d_seltext");
                            String v_scalename = dbox2.getString("d_scalename");
                            int v_selpoint = dbox2.getInt("d_selpoint");
                            isOk2 += insertTZ_damunsel(pstmt, v_subj, v_grcode, v_damunnum, v_selnum, v_seltext, v_scalename, v_selpoint, v_luserid);
                        }

                        pstmt.close();
                        if (isOk1 <= 0 || isOk2 != list2.size())
                            break;
                        v_next++;
                        if (v_token == v_next)
                            v_damunnums = v_damunnums + String.valueOf(v_damunnum);
                        else
                            v_damunnums = v_damunnums + String.valueOf(v_damunnum) + ",";
                        v_damunnum++;
                    }
                    if (v_next == v_token) {
                        isOk = insertTZ_damunpaper(connMgr, v_grcode, v_subj, v_gyear, v_damunpapernum, v_d_gubun, v_damunpapernm, v_totcnt, v_damunnums, "", "", "", "", v_luserid);
                        if (isOk > 0)
                            connMgr.commit();
                        else
                            connMgr.rollback();
                        v_damunpapernum++;
                    }
                } else {
                    isOk = insertTZ_damunpaper(connMgr, v_grcode, v_subj, v_gyear, v_damunpapernum, v_d_gubun, v_damunpapernm, v_totcnt, s_damunnums, "", "", "", "", v_luserid);
                    if (isOk > 0)
                        connMgr.commit();
                    else
                        connMgr.rollback();
                }
            }

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0)
                connMgr.commit();
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception exception1) {
                }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception exception2) {
                }
        }
        return isOk;
    }

    public ArrayList<DataBox> getPaperData(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_damunpapernum) throws Exception {
        ArrayList<DataBox> list;
        list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        try {
            sql = "select a.grcode,      b.subj,   ";
            sql = sql + "        a.damunpapernum, a.damunpapernm, a.year, ";
            sql = sql + "       a.totcnt,      a.damunnums,  a.fdamunstart, a.fdamunend,  a.sdamunstart,  a.sdamunend, a.d_gubun,  ";
            sql = sql + "       b.subjnm        ";
            sql = sql + "  from tz_damunpaper  a, ";
            sql = sql + "       tz_subj      b  ";
            sql = sql + " where a.subj(+) = b.subj ";
            sql = sql + "    and (a.grcode  = " + SQLString.Format(p_grcode) + " or a.grcode is null) ";
            sql = sql + "   and a.subj   = " + SQLString.Format(p_subj);
            sql = sql + "   and a.damunpapernum   = " + SQLString.Format(p_damunpapernum);
            sql = sql + " order by b.subj, a.damunpapernum ";
            ls = connMgr.executeQuery(sql);
            System.out.println(sql);
            for (; ls.next(); list.add(dbox))
                dbox = ls.getDataBox();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null)
                try {
                    ls.close();
                } catch (Exception exception1) {
                }
        }
        return list;
    }

    public ArrayList<DataBox> getExampleData(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_damunnum) throws Exception {
        ArrayList<DataBox> list;
        list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        try {
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
            for (ls = connMgr.executeQuery(sql); ls.next(); list.add(dbox))
                dbox = ls.getDataBox();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null)
                try {
                    ls.close();
                } catch (Exception exception1) {
                }
        }
        return list;
    }

    public int getDamunnumSeq(String p_subj, String p_grcode) throws Exception {
        Hashtable<String, String> maxdata = new Hashtable<String, String>();
        maxdata.put("seqcolumn", "damunnum");
        maxdata.put("seqtable", "tz_damun");
        maxdata.put("paramcnt", "2");
        maxdata.put("param0", "subj");
        maxdata.put("param1", "grcode");
        maxdata.put("subj", SQLString.Format(p_subj));
        maxdata.put("grcode", SQLString.Format(p_grcode));
        return SelectionUtil.getSeq(maxdata);
    }

    public int insertTZ_damun(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_damunnum, String p_damuntype, String p_damuntext, int p_selcount, int p_selmax, int p_fscalecode, int p_sscalecode, String p_luserid) throws Exception {
        int isOk;
        PreparedStatement pstmt = null;
        String sql = "";
        isOk = 0;
        try {
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
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null)
                try {
                    pstmt.close();
                } catch (Exception exception1) {
                }
        }
        return isOk;
    }

    public int insertTZ_damunsel(PreparedStatement pstmt, String p_subj, String p_grcode, int p_damunnum, int p_selnum, String p_seltext, String p_scalename, int p_selpoint, String p_luserid) throws Exception {
        int isOk = 0;
        try {
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
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return isOk;
    }
}
