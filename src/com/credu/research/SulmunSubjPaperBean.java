//**********************************************************
//1. 제      목:
//2. 프로그램명: SulmunSubjPaperBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-18
//7. 수      정:
//
//**********************************************************

package com.credu.research;

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
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.system.SelectionUtil;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunSubjPaperBean {

    public SulmunSubjPaperBean() {
    }

    /**
     * @param box receive from the form object and session
     * @return ArrayList 설문문제 리스트
     */
    public ArrayList<DataBox> selectQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;

        // String v_gubun = box.getStringDefault("p_gubun", SulmunSubjBean.DEFAULT_SUBJ);
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_distcode = box.getStringDefault("s_distcode", "ALL");
        String v_grcode = box.getString("s_grcode");
        String v_action = box.getStringDefault("p_action", "change");

        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = selectQuestionList(connMgr, v_grcode, v_subj, v_distcode);
            } else {
                list = new ArrayList<DataBox>();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * @param box receive from the form object and session
     * @return ArrayList 설문문제 리스트
     */
    public ArrayList<DataBox> selectQuestionList(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_distcode) throws Exception {
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = "select a.subj,     a.sulnum,  a.grcode, ";
            sql += "       a.distcode, b.codenm  distcodenm, ";
            sql += "       a.sultype,  c.codenm  sultypenm,  ";
            sql += "       a.sultext    ";
            sql += "  from tz_sul    a, ";
            sql += "       tz_code   b, ";
            sql += "       tz_code   c  ";
            sql += "   where a.distcode  = b.code ";
            sql += "   and a.sultype  = c.code ";
            sql += "   and a.grcode    = " + SQLString.Format(p_grcode);
            sql += "   and b.gubun    = " + SQLString.Format(SulmunSubjBean.DIST_CODE);
            sql += "   and c.gubun    = " + SQLString.Format(SulmunSubjBean.SUL_TYPE);
            sql += "    and c.levels  = 1  ";
            if (!p_subj.equals("ALL")) {
                sql += "   and a.subj     = " + SQLString.Format(p_subj);
            }
            if (!p_distcode.equals("ALL")) {
                sql += "   and a.distcode = " + SQLString.Format(p_distcode);
            }
            sql += " order by a.sulnum ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
            ls.close();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    /**
     * @param box receive from the form object and session
     * @return ArrayList 설문문제 리스트
     */
    public ArrayList<DataBox> selectPaperQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;

        String v_gubun = box.getStringDefault("p_gubun", SulmunSubjBean.DEFAULT_SUBJ);
        String v_grcode = box.getString("p_grcode");
        String v_subj = box.getString("p_subj");
        int v_sulpapernum = box.getInt("p_sulpapernum");

        try {
            connMgr = new DBConnectionManager();
            if (!v_gubun.equals("SUBJ")) {
                v_subj = v_gubun;
            }
            list = selectPaperQuestionList(connMgr, v_grcode, v_subj, v_sulpapernum, box);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * @param box receive from the form object and session
     * @return ArrayList 설문문제 리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectPaperQuestionList(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, RequestBox box) throws Exception {
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        Hashtable<String, DataBox> hash = new Hashtable<String, DataBox>();
        StringTokenizer st = null;

        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_sulnums = "";
        String v_sulnum = "";
        String v_sulpapernm = "";
        int v_progress = 0;

        try {
            sql = "select sulpapernm, totcnt, sulnums, progress";
            sql += "  from tz_sulpaper ";
            sql += " where grcode      = " + SQLString.Format(p_grcode);
            sql += " and subj        = " + SQLString.Format(p_subj);
            sql += "   and sulpapernum = " + SQLString.Format(p_sulpapernum);
            //     sql+= " and year = " + SQLString.Format(box.getString("p_year"));
            //     sql+= " and subjseq = " + SQLString.Format(box.getString("p_subjseqr"));

            ls = connMgr.executeQuery(sql);//System.out.println("sql" +sql);
            while (ls.next()) {
                v_sulnums = ls.getString("sulnums");
                v_sulpapernm = ls.getString("sulpapernm");
                v_progress = ls.getInt("progress");
            }
            ls.close();
            if (box != null) {
                box.put("p_sulpapernm", v_sulpapernm);//System.out.println("v_sulpapernm" +v_sulpapernm);
                box.put("p_progress", String.valueOf(v_progress));//System.out.println("v_progress" +String.valueOf(v_progress));
            }

            if (v_sulnums.length() > 0) {
                sql = "select a.subj,     a.sulnum,  ";
                sql += "       a.distcode, b.codenm  distcodenm, ";
                sql += "       a.sultype,  c.codenm  sultypenm,  ";
                sql += "       a.sultext    ";
                sql += "  from tz_sul    a, ";
                sql += "       tz_code   b, ";
                sql += "       tz_code   c  ";
                sql += " where a.distcode = b.code ";
                sql += "   and a.sultype  = c.code ";
                sql += "   and b.gubun    = " + SQLString.Format(SulmunSubjBean.DIST_CODE);
                sql += "   and c.gubun    = " + SQLString.Format(SulmunSubjBean.SUL_TYPE);
                sql += "   and a.subj     = " + SQLString.Format(p_subj);
                sql += "   and c.levels  =  1 ";
                if (v_sulnums.equals(""))
                    v_sulnums = "-1";
                sql += "   and a.sulnum in (" + v_sulnums + ")";

                sql += " order by a.sulnum ";

                ls.close();
                ls = connMgr.executeQuery(sql);

                st = new StringTokenizer(v_sulnums, SulmunSubjBean.SPLIT_COMMA);

                while (ls.next()) {
                    dbox = ls.getDataBox();

                    v_sulnum = String.valueOf(ls.getInt("sulnum"));

                    hash.put(v_sulnum, dbox);
                }
                ls.close();

                while (st.hasMoreElements()) {
                    v_sulnum = (String) st.nextToken();
                    dbox = (DataBox) hash.get(v_sulnum);
                    if (dbox != null) {
                        list.add(dbox);
                    }
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    public String getPaperListSQL(String p_grcode, String p_subj, String p_gyear, String p_subjsel, String p_upperclass, int p_sulpapernum) throws Exception {
        String sql = "";

        sql = "select a.grcode,      b.subj,   a.subjseq,      ";
        sql += "        a.sulpapernum, a.sulpapernm, a.year, ";
        sql += "       a.totcnt,      a.sulnums,  a.sulmailing, a.sulstart, a.sulend,    ";
        sql += "       b.subjnm        ";
        sql += "  from tz_sulpaper  a, ";
        sql += "       tz_subj      b  ";
        // 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
        //            sql+= " where a.subj(+) = b.subj ";
        sql += " where a.subj = b.subj(+) ";
        sql += "    and (a.grcode  = " + SQLString.Format(p_grcode) + " or a.grcode is null) ";
        /*
         * if (!p_upperclass.equals("ALL")) { sql+= "   and b.upperclass   = " +
         * SQLString.Format(p_upperclass); }
         */
        if (!p_subjsel.equals("ALL")) {
            sql += "   and b.subj   = " + SQLString.Format(p_subjsel);
        }
        if (p_sulpapernum > 0) {
            sql += "   and a.sulpapernum   = " + SQLString.Format(p_sulpapernum);
        }
        sql += " order by b.subj, b.subjseq ";

        return sql;
    }

    public String getPaperListSQL(String p_grcode, String p_subj, String p_gyear, String p_subjsel, String p_subjseq, String p_upperclass, int p_sulpapernum) throws Exception {
        String sql = "";

        sql = "select b.grcode,      b.subj,   b.subjseq,   b.subjseqgr,   ";
        sql += "        a.sulpapernum, a.sulpapernm, a.year, ";
        sql += "       a.totcnt,      a.sulnums,  a.sulmailing, a.sulstart, a.sulend,    a.progress,";
        sql += "       b.subjnm        ";
        sql += "  from tz_sulpaper  a, ";
        sql += "       tz_subjseq      b  ";
        // 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
        //            sql+= " where a.subj(+) = b.subj ";
        //            sql+= " and a.subjseq(+) = b.subjseq ";
        sql += " where a.subj  = b.subj(+) ";
        sql += " and a.subjseq = b.subjseq(+) ";
        sql += "    and b.grcode  = " + SQLString.Format(p_grcode);

        sql += "   and b.gyear   = " + SQLString.Format(p_gyear);

        /*
         * if (!p_upperclass.equals("ALL")) { sql+= "   and b.upperclass   = " +
         * SQLString.Format(p_upperclass); }
         */
        if (!p_subjsel.equals("ALL")) {
            sql += "   and b.subj   = " + SQLString.Format(p_subjsel);
        }
        if (!p_subjseq.equals("ALL")) {
            sql += "   and b.subjseq   = " + SQLString.Format(p_subjseq);
        }
        if (p_sulpapernum > 0) {
            sql += "   and a.sulpapernum   = " + SQLString.Format(p_sulpapernum);
        }
        sql += " order by b.subj, b.subjseq ";

        return sql;
    }

    /**
     * @param box receive from the form object and session
     * @return ArrayList 설문문제지 리스트
     */
    public ArrayList<DataBox> selectPaperList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        //    String v_grcode    = box.getStringDefault("p_grcode", SulmunSubjBean.DEFAULT_GRCODE);
        //      java.util.Date d_now = new java.util.Date();
        String v_grcode = box.getStringDefault("p_grcode", box.getString("s_grcode"));
        String v_gyear = box.getStringDefault("p_gyear", box.getString("s_gyear"));
        String v_action = box.getStringDefault("p_action", "change");
        String v_gubun = box.getStringDefault("p_gubun", SulmunSubjBean.DEFAULT_SUBJ);

        String ss_upperclass = box.getStringDefault("s_upperclass", "ALL");
        // String ss_middleclass = box.getStringDefault("s_middleclass", "ALL");
        // String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL");

        try {

            if (v_action.equals("go")) {

                //	sql = getPaperListSQL(v_grcode, v_gubun, v_gyear, ss_subjcourse, ss_upperclass, -1);
                sql = getPaperListSQL(v_grcode, v_gubun, v_gyear, ss_subjcourse, ss_subjseq, ss_upperclass, -1);
                System.out.println("sqlsql>>>>>" + sql);
                connMgr = new DBConnectionManager();
                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
                }
                ls.close();

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * @param box receive from the form object and session
     * @return PaperData
     */
    public DataBox getPaperData(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;

        DataBox dbox = null;

        String v_grcode = box.getStringDefault("p_grcode", box.getString("s_grcode"));
        String v_subj = box.getStringDefault("p_subj", box.getString("s_subjcourse"));//System.out.println("subj " + v_subj);
        // java.util.Date d_now = new java.util.Date();
        String v_gyear = box.getStringDefault("p_gyear", box.getString("s_gyear"));
        String v_subjsel = box.getString("p_subjsel");
        String v_subjseq = box.getString("p_subjseq");
        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");

        int v_sulpapernum = box.getInt("p_sulpapernum");

        try {
            connMgr = new DBConnectionManager();
            dbox = getPaperData(connMgr, v_grcode, v_subj, v_gyear, v_subjsel, v_subjseq, v_upperclass, v_sulpapernum);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return dbox;
    }

    /**
     * @param box receive from the form object and session
     * @return PaperData
     */
    public DataBox getPaperData(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_gyear, String p_subjsel, String p_subjseq, String p_upperclass, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            //sql = getPaperListSQL(p_grcode, p_subj, p_gyear, p_subjsel, p_upperclass, p_sulpapernum);

            sql = getPaperListSQL(p_grcode, p_subj, p_gyear, p_subjsel, p_subjseq, p_upperclass, p_sulpapernum);
            ls = connMgr.executeQuery(sql);
            //System.out.println(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

            }
            ls.close();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }

        if (dbox == null)
            dbox = new DataBox("resoponsebox");

        return dbox;
    }

    /*
     * public int insertTZ_sulpaper(DBConnectionManager connMgr, String
     * p_grcode, String p_gyear, String p_subj, String p_subjseq, int
     * p_sulpapernum, String p_sulpapernm, int p_totcnt, String p_sulnums,
     * String p_sulmailing, String p_sulstart, String p_sulend, int p_progress,
     * String p_luserid) throws Exception { PreparedStatement pstmt = null;
     * String sql = ""; int isOk = 0;
     * 
     * 
     * try { //insert TZ_SULPAPER table sql = "insert into TZ_SULPAPER "; sql+=
     * "(grcode,    subj,     sulpapernum, sulpapernm, "; sql+=
     * " year,      subjseq,     "; sql+=
     * " totcnt,       sulnums,     sulmailing,   "; sql+=
     * " sulstart, sulend,  progress,  luserid,  ldate )   "; sql+= " values ";
     * sql+= "(?,         ?,       ?,         ?,   "; sql+=
     * " ?,         ?,            "; sql+= " ?,         ?,       ?, "; sql+=
     * " ?,         ?,       ?,          ?,     ?) ";
     * 
     * pstmt = connMgr.prepareStatement(sql);
     * 
     * pstmt.setString( 1, p_grcode); pstmt.setString( 2, p_subj); pstmt.setInt
     * ( 3, p_sulpapernum); pstmt.setString( 4, p_sulpapernm); pstmt.setString(
     * 5, p_gyear); pstmt.setString( 6, p_subjseq); pstmt.setInt( 7, p_totcnt);
     * pstmt.setString( 8, p_sulnums); pstmt.setString( 9, p_sulmailing);
     * pstmt.setString(10, ""); pstmt.setString(11, ""); pstmt.setInt( 12,
     * p_progress); pstmt.setString(13, p_luserid); pstmt.setString(14,
     * FormatDate.getDate("yyyyMMddHHmmss"));
     * 
     * isOk = pstmt.executeUpdate(); } catch(Exception ex) {
     * ErrorManager.getErrorStackTrace(ex); throw new Exception("sql = " + sql +
     * "\r\n" + ex.getMessage()); } finally { if(pstmt != null) { try {
     * pstmt.close(); } catch (Exception e) {} } } return isOk; }
     */

    public int insertTZ_sulpaper(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_subj, String p_subjseq, int p_sulpapernum, String p_sulpapernm, int p_totcnt, String p_sulnums, String p_sulmailing, String p_sulstart,
            String p_sulend, int p_progress, String p_luserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_SULPAPER table
            sql = "insert into TZ_SULPAPER ";
            sql += "(grcode,    subj,     sulpapernum, sulpapernm, ";
            sql += " year,      subjseq,     ";
            sql += " totcnt,       sulnums,     sulmailing,   ";
            sql += " sulstart, sulend,  progress,  luserid,  ldate )   ";
            sql += " values ";
            sql += "(?,         ?,       ?,         ?,   ";
            sql += " ?,         ?,            ";
            sql += " ?,         ?,       ?, ";
            sql += " ?,         ?,       ?,          ?,     ?) ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, p_grcode);
            pstmt.setString(2, p_subj);
            pstmt.setInt(3, p_sulpapernum);
            pstmt.setString(4, p_sulpapernm);
            pstmt.setString(5, p_gyear);
            pstmt.setString(6, p_subjseq);
            pstmt.setInt(7, p_totcnt);
            pstmt.setString(8, p_sulnums);
            pstmt.setString(9, p_sulmailing);
            pstmt.setString(10, "");
            pstmt.setString(11, "");
            pstmt.setInt(12, p_progress);
            pstmt.setString(13, p_luserid);
            pstmt.setString(14, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    public int updateTZ_sulpaper(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_subj, String p_subjseq, int p_sulpapernum, String p_sulpapernm, int p_totcnt, String p_sulnums, String p_sulmailing, String p_sulstart,
            String p_sulend, int p_progress, String p_luserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_SULPAPER table
            sql = " update TZ_SULPAPER ";
            sql += "    set sulpapernm = ?, ";
            sql += "        totcnt       = ?, ";
            sql += "        sulnums      = ?, ";
            sql += "        progress      = ?, ";
            sql += "        luserid      = ?, ";
            sql += "        ldate        = ?  ";
            sql += "  where grcode       = ?  ";
            sql += "    and subj         = ?  ";
            sql += "    and sulpapernum  = ?  ";
            sql += "    and year  = ?  ";
            sql += "    and subjseq  = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, p_sulpapernm);
            pstmt.setInt(2, p_totcnt);
            pstmt.setString(3, p_sulnums);
            pstmt.setInt(4, p_progress);
            pstmt.setString(5, p_luserid);
            pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(7, p_grcode);
            pstmt.setString(8, p_subj);
            pstmt.setInt(9, p_sulpapernum);
            pstmt.setString(10, p_gyear);
            pstmt.setString(11, p_subjseq);

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    public int updateTZ_sulpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, String p_sulpapernm, int p_totcnt, String p_sulnums, String p_sulmailing, String p_sulstart, String p_sulend, int p_progress, String p_luserid)
            throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_SULPAPER table
            sql = " update TZ_SULPAPER ";
            sql += "    set sulpapernm = ?, ";
            sql += "        totcnt       = ?, ";
            sql += "        sulnums      = ?, ";
            sql += "        progress      = ?, ";
            sql += "        luserid      = ?, ";
            sql += "        ldate        = ?  ";
            sql += "  where grcode       = ?  ";
            sql += "    and subj         = ?  ";
            sql += "    and sulpapernum  = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, p_sulpapernm);
            pstmt.setInt(2, p_totcnt);
            pstmt.setString(3, p_sulnums);
            pstmt.setInt(4, p_progress);
            pstmt.setString(5, p_luserid);
            pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(7, p_grcode);
            pstmt.setString(8, p_subj);
            pstmt.setInt(9, p_sulpapernum);

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    public int deleteTZ_sulpaper(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_subj, String p_subjseq, int p_sulpapernum, String p_duserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //delete TZ_SULPAPER table

            sql = "delete from TZ_SULPAPER ";
            sql += " where grcode     = ?  ";
            sql += "   and subj       = ?  ";
            sql += "   and sulpapernum= ?  ";
            sql += "    and year  = ?  ";
            sql += "    and subjseq  = ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_grcode);
            pstmt.setString(2, p_subj);
            pstmt.setInt(3, p_sulpapernum);
            pstmt.setString(4, p_gyear);
            pstmt.setString(5, p_subjseq);

            isOk = pstmt.executeUpdate();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    public int insertPaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;

        //    String v_grcode       = box.getStringDefault("p_grcode",SulmunSubjBean.DEFAULT_GRCODE);
        String v_grcode = box.getStringDefault("p_grcode", box.getString("s_grcode"));
        String v_gyear = box.getStringDefault("p_gyear", box.getString("s_gyear"));
        // String v_gubun = box.getStringDefault("p_gubun", SulmunSubjBean.DEFAULT_SUBJ);
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_subjseq = box.getString("p_subjseq");

        String v_sulpapernm = box.getString("p_sulpapernm");
        int v_totcnt = box.getInt("p_totcnt");
        String v_sulnums = box.getString("p_sulnums");
        int v_sulpapernum = 0;

        int v_progress = box.getInt("p_progress");

        String v_sulstart = box.getString("p_sulstart");
        String v_sulend = box.getString("p_sulend");
        String v_sulmailing = "N";

        String v_luserid = box.getSession("userid");

        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            v_sulpapernum = getPapernumSeq(v_subj, v_grcode);

            isOk = insertTZ_sulpaper(connMgr, v_grcode, v_gyear, v_subj, v_subjseq, v_sulpapernum, v_sulpapernm, v_totcnt, v_sulnums, v_sulmailing, v_sulstart, v_sulend, v_progress, v_luserid);

        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    public int updatePaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;

        String v_grcode = box.getStringDefault("p_grcode", box.getString("s_grcode"));
        String v_gyear = box.getStringDefault("p_gyear", box.getString("s_gyear"));
        // String v_gubun = box.getStringDefault("p_gubun", SulmunSubjBean.DEFAULT_SUBJ);
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_subjseq = box.getString("p_subjseq");

        int v_sulpapernum = box.getInt("p_sulpapernum");
        String v_sulpapernm = box.getString("p_sulpapernm");
        int v_totcnt = box.getInt("p_totcnt");
        String v_sulnums = box.getString("p_sulnums");

        int v_progress = box.getInt("p_progress");

        String v_sulstart = box.getString("p_sulstart");
        String v_sulend = box.getString("p_sulend");
        String v_sulmailing = "N";

        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            isOk = updateTZ_sulpaper(connMgr, v_grcode, v_gyear, v_subj, v_subjseq, v_sulpapernum, v_sulpapernm, v_totcnt, v_sulnums, v_sulmailing, v_sulstart, v_sulend, v_progress, v_luserid);
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    public int deletePaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        String sql = "";
        int isOk = 0;

        String v_grcode = box.getStringDefault("p_grcode", box.getString("s_grcode"));
        String v_gyear = box.getStringDefault("p_gyear", box.getString("s_gyear"));
        // String v_gubun = box.getStringDefault("p_gubun", SulmunSubjBean.DEFAULT_SUBJ);
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_subjseq = box.getString("p_subjseq");
        int v_sulpapernum = box.getInt("p_sulpapernum");
        String v_duserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            isOk = deleteTZ_sulpaper(connMgr, v_grcode, v_gyear, v_subj, v_subjseq, v_sulpapernum, v_duserid);
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    public int getPapernumSeq(String p_subj, String p_grcode) throws Exception {
        Hashtable<String, String> maxdata = new Hashtable<String, String>();
        maxdata.put("seqcolumn", "sulpapernum");
        maxdata.put("seqtable", "tz_sulpaper");
        maxdata.put("paramcnt", "2");
        maxdata.put("param0", "subj");
        maxdata.put("param1", "grcode");
        maxdata.put("subj", SQLString.Format(p_subj));
        maxdata.put("grcode", SQLString.Format(p_grcode));

        return SelectionUtil.getSeq(maxdata);
    }

    public Vector<String> getSulnums(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql = "";
        Vector<String> v_sulnums = new Vector<String>();
        String v_tokens = "";
        StringTokenizer st = null;

        try {
            sql = "select sulnums  ";
            sql += "  from tz_sulpaper ";
            sql += " where grcode      = " + SQLString.Format(p_grcode);
            //         sql+= " where grcode      = " + SQLString.Format(SulmunSubjBean.DEFAULT_GRCODE);
            sql += "   and subj        = " + SQLString.Format(p_subj);
            sql += "   and sulpapernum = " + SQLString.Format(p_sulpapernum);
            System.out.println("s>>>" + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_tokens = ls.getString("sulnums");
            }

            st = new StringTokenizer(v_tokens, SulmunSubjBean.SPLIT_COMMA);
            while (st.hasMoreElements()) {
                v_sulnums.add((String) st.nextToken());
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return v_sulnums;
    }

    public ArrayList<ArrayList<DataBox>> getSelnums(DBConnectionManager connMgr, String p_subj, String p_grcode, Vector<String> p_sulnums) throws Exception {
        // Hashtable hash = new Hashtable();
        ArrayList<ArrayList<DataBox>> blist = new ArrayList<ArrayList<DataBox>>();
        ArrayList<DataBox> list = null;

        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        StringTokenizer st = null;

        String v_sulnums = "";
        for (int i = 0; i < p_sulnums.size(); i++) {
            v_sulnums += (String) p_sulnums.get(i);
            if (i < p_sulnums.size() - 1) {
                v_sulnums += ",";
            }
        }
        if (v_sulnums.equals(""))
            v_sulnums = "-1";

        try {

            st = new StringTokenizer(v_sulnums, ",");

            while (st.hasMoreElements()) {

                int sulnum = Integer.parseInt(st.nextToken());
                System.out.println(sulnum);

                sql = "select a.subj,     a.sulnum, a.selcount, a.selmax,  ";
                sql += "        a.distcode, c.codenm distcodenm, a.sulreturn, ";
                sql += "       a.sultype,  d.codenm sultypenm, ";
                sql += "       a.sultext,  b.selnum, b.seltext, b.selpoint ";
                sql += "  from tz_sul     a, ";
                sql += "       tz_sulsel  b, ";
                sql += "       tz_code    c, ";
                sql += "       tz_code    d  ";
                // 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
                //            sql+= " where a.subj     = b.subj(+)    ";
                //            sql+= "   and a.grcode   = b.grcode(+)  ";
                //			  sql+= "   and a.sulnum   = b.sulnum(+)  ";
                sql += " where a.subj      =  b.subj(+)    ";
                sql += "   and a.grcode    =  b.grcode(+)  ";
                sql += "   and a.sulnum    =  b.sulnum(+)  ";
                sql += "   and a.distcode = c.code ";
                sql += "   and a.sultype  = d.code ";
                sql += "   and a.subj     = " + SQLString.Format(p_subj);
                sql += "   and a.grcode     = " + SQLString.Format(p_grcode);
                sql += "   and a.sulnum = " + sulnum;
                sql += "   and c.gubun    = " + SQLString.Format(SulmunSubjBean.DIST_CODE);
                sql += "   and d.gubun    = " + SQLString.Format(SulmunSubjBean.SUL_TYPE);
                sql += "   and d.levels    =  1 ";
                sql += " order by a.subj, a.sulnum, b.selnum ";
                //Log.info.println(">>"+sql);
                System.out.println("sel------>>" + sql);
                ls = connMgr.executeQuery(sql);
                list = new ArrayList<DataBox>();

                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
                }
                blist.add(list);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return blist;
    }

    /**
     * @param box receive from the form object and session
     * @return ArrayList 설문문제 리스트
     */
    @SuppressWarnings("unchecked")
    public ArrayList<ArrayList<DataBox>> selectPaperQuestionExampleList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<ArrayList<DataBox>> list = null;

        String v_grcode = box.getSession("tem_grcode");
        //String v_grcode      = box.getString("p_grcode");
        //System.out.println("학습창 설문문제 리스트 //v_grcode:"+v_grcode);		
        String v_subj = box.getString("p_subj");
        int v_sulpapernum = box.getInt("p_sulpapernum");

        try {
            if (v_sulpapernum == 0) {
                v_sulpapernum = getPapernumSeq(v_subj, v_grcode) - 1;
                box.put("p_sulpapernum", String.valueOf(v_sulpapernum));
            }

            connMgr = new DBConnectionManager();
            list = selectPaperQuestionExampleList(connMgr, v_grcode, v_subj, v_sulpapernum, box);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return list;
    }

    /**
     * @param box receive from the form object and session
     * @return ArrayList 설문문제 리스트
     */
    public ArrayList<ArrayList<DataBox>> selectPaperQuestionExampleList(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, RequestBox box) throws Exception {
        Vector<String> v_sulnums = null;
        ArrayList<ArrayList<DataBox>> QuestionExampleDataList = null;

        try {
            // 설문지번호에 해당하는 설문번호를 vector로 받아온다. 벡터(설문번호1,3,5 ....)
            v_sulnums = getSulnums(connMgr, p_grcode, p_subj, p_sulpapernum);
            if (!v_sulnums.equals("")) {
                // 설문번호에 해당하는 문제리스트를 만든다. 리스트((설문번호1, 보기1,2,3..))
                QuestionExampleDataList = getSelnums(connMgr, p_subj, p_grcode, v_sulnums);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return QuestionExampleDataList;
    }

    /**
     * @param box receive from the form object and session
     * @return ArrayList 평가문제 리스트
     */
    public ArrayList<DataBox> selectPaperPool(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;

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
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * @param box receive from the form object and session
     * @return ArrayList 평가문제 리스트
     */
    public ArrayList<DataBox> getPaperPool(DBConnectionManager connMgr, String p_subj, String p_grcode) throws Exception {
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = "select distinct a.grcode,      a.subj,         ";
            sql += "        a.sulpapernum, a.sulpapernm, a.year, ";
            sql += "       a.totcnt,      a.sulnums,  a.sulmailing, a.sulstart, a.sulend,    ";
            sql += "       b.subjnm        ";
            sql += "  from (select distinct grcode, subj, sulpapernum, sulpapernm, year, totcnt, sulnums, sulmailing,";
            sql += "  sulstart, sulend from tz_sulpaper  ";
            sql += "   where grcode    = " + SQLString.Format(p_grcode);
            sql += "   and subj    = " + SQLString.Format(p_subj);
            sql += "   and subj    ! = 'COMMON' ";
            sql += "   and subj    ! = 'TARGET' ";
            sql += "   and subj    ! = 'REGIST') a, ";
            sql += "       tz_subj   b  ";
            sql += "   where a.subj  = b.subj ";
            sql += " order by a.subj, a.sulpapernum ";

            ls = connMgr.executeQuery(sql);//System.out.println(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    /**
     * 평가 문제 를 찾기위한 Pool
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectPaperPoolList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
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

                sql = "select a.grcode,      a.subj,         ";
                sql += "        a.sulpapernum, a.sulpapernm, a.year, ";
                sql += "       a.totcnt,      a.sulnums,  a.sulmailing, a.sulstart, a.sulend,    ";
                sql += "       b.subjnm        ";
                sql += "  from (select distinct grcode, subj, sulpapernum, sulpapernm, year, totcnt, sulnums, sulmailing,";
                sql += "  sulstart, sulend from tz_sulpaper  ";
                sql += "   where grcode    = " + SQLString.Format(v_grcode);
                //                sql+= "   and subj    = " + SQLString.Format(v_subj);
                sql += "   and subj    ! = 'COMMON' ";
                sql += "   and subj    ! = 'TARGET' ";
                sql += "   and subj    ! = 'REGIST') a, ";
                sql += "       tz_subj   b  ";
                sql += "   where a.subj  = b.subj ";

                if (ss_searchtype.equals("1")) { // 과정명
                    sql += "  and b.subjnm like " + SQLString.Format("%" + ss_searchtext + "%");
                } else if (ss_searchtype.equals("2")) { // 설문지명
                    sql += "  and a.sulpapernm like " + SQLString.Format("%" + ss_searchtext + "%");
                }
                sql += " order by a.subj, a.sulpapernum ";

                ls = connMgr.executeQuery(sql);//System.out.println(sql);

                while (ls.next()) {
                    dbox = ls.getDataBox();

                    list.add(dbox);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    public int insertPaperPool(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        StringTokenizer st = null;
        StringTokenizer st2 = null;
        String v_tokens = "";
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ArrayList<DataBox> list2 = new ArrayList<DataBox>();
        DataBox dbox = null;
        DataBox dbox2 = null;

        String v_grcode = box.getStringDefault("p_grcode", box.getString("s_grcode"));
        String v_gyear = box.getStringDefault("p_gyear", box.getString("s_gyear"));
        String v_subj = box.getStringDefault("p_subj", box.getString("s_subjcourse"));
        String v_subjseq = box.getString("p_subjseq");

        String v_luserid = box.getSession("userid");
        int v_sulpapernum = 0;

        String s_subj = "";
        int s_sulpapernum = 0;

        Vector v_checks = box.getVector("p_checks");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////           

            for (int i = 0; i < v_checks.size(); i++) {
                v_tokens = (String) v_checks.elementAt(i);
                st = new StringTokenizer(v_tokens, "|");
                s_subj = (String) st.nextToken();
                s_sulpapernum = Integer.parseInt((String) st.nextToken());

                list = getPaperData(connMgr, s_subj, v_grcode, s_sulpapernum);
                dbox = (DataBox) list.get(0);

                String v_sulpapernm = dbox.getString("d_sulpapernm");
                int v_totcnt = dbox.getInt("d_totcnt");
                String v_sulmailing = dbox.getString("d_sulmailing");
                String v_sulstart = dbox.getString("d_sulstart");
                String v_sulend = dbox.getString("d_sulend");
                int v_progress = dbox.getInt("d_progress");
                String s_sulnums = dbox.getString("d_sulnums");
                String v_sulnums = "";
                int s_sulnum = 0;
                int v_sulnum = getSulnumSeq(v_subj, v_grcode);

                int v_next = 0;

                if (!v_subj.equals(s_subj)) {
                    v_sulpapernum = getPapernumSeq(v_subj, v_grcode);//System.out.println("v_sulpapernum" + v_sulpapernum);

                    st2 = new StringTokenizer(s_sulnums, ",");
                    int v_token = st2.countTokens();

                    while (st2.hasMoreElements()) {

                        s_sulnum = Integer.parseInt((String) st2.nextToken());
                        list2 = getExampleData(connMgr, s_subj, v_grcode, s_sulnum);
                        dbox2 = (DataBox) list2.get(0);

                        String v_distcode = dbox2.getString("d_distcode");
                        String v_sultype = dbox2.getString("d_sultype");
                        String v_sultext = dbox2.getString("d_sultext");
                        int v_selcount = dbox2.getInt("d_selcount");
                        int v_selmax = dbox2.getInt("d_selmax");
                        String v_sulreturn = dbox2.getString("d_sulreturn");
                        int v_scalecode = dbox2.getInt("d_scalecode");

                        int isOk1 = 0;
                        int isOk2 = 0;

                        isOk1 = insertTZ_sul(connMgr, v_subj, v_grcode, v_sulnum, v_distcode, v_sultype, v_sultext, v_selcount, v_selmax, v_sulreturn, v_scalecode, v_luserid);

                        sql = "insert into TZ_SULSEL(subj, grcode, sulnum, selnum, seltext, selpoint, luserid, ldate) ";
                        sql += " values (?, ?, ?, ?, ?, ?, ?, ?)";

                        pstmt = connMgr.prepareStatement(sql);

                        for (int j = 0; j < list2.size(); j++) {
                            dbox2 = (DataBox) list2.get(j);
                            int v_selnum = dbox2.getInt("d_selnum");
                            String v_seltext = dbox2.getString("d_seltext");
                            int v_selpoint = dbox2.getInt("d_selpoint");

                            isOk2 += insertTZ_sulsel(pstmt, v_subj, v_grcode, v_sulnum, v_selnum, v_seltext, v_selpoint, v_luserid);

                        }

                        pstmt.close();

                        if (isOk1 > 0 && isOk2 == list2.size()) {
                            v_next++;
                        } else {
                            break;
                        }
                        if (v_token == v_next) {
                            v_sulnums += String.valueOf(v_sulnum);
                        } else {
                            v_sulnums += String.valueOf(v_sulnum) + ",";
                        }
                        v_sulnum++;
                    }

                    if (v_next == v_token) {
                        //  isOk = insertTZ_sulpaper(connMgr,  v_grcode, v_subj, v_sulpapernum, v_sulpapernm, v_totcnt, v_sulnums, v_sulmailing, v_sulstart, v_sulend, v_progress, v_luserid);
                        isOk = insertTZ_sulpaper(connMgr, v_grcode, v_gyear, v_subj, v_subjseq, v_sulpapernum, v_sulpapernm, v_totcnt, v_sulnums, v_sulmailing, v_sulstart, v_sulend, v_progress, v_luserid);

                        v_sulpapernum++;
                    }
                } else {
                    isOk = insertTZ_sulpaper(connMgr, v_grcode, v_gyear, v_subj, v_subjseq, s_sulpapernum, v_sulpapernm, v_totcnt, s_sulnums, v_sulmailing, v_sulstart, v_sulend, v_progress, v_luserid);

                }
            }
        } catch (Exception ex) {
            isOk = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (isOk > 0) {
                connMgr.commit();
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    public ArrayList<DataBox> getPaperData(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_sulpapernum) throws Exception {
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = "select a.grcode,      b.subj,   a.subjseq,      ";
            sql += "        a.sulpapernum, a.sulpapernm, a.year, ";
            sql += "       a.totcnt,      a.sulnums,  a.sulmailing, a.sulstart, a.sulend,    ";
            sql += "       b.subjnm        ";
            sql += "  from tz_sulpaper  a, ";
            sql += "       tz_subj      b  ";
            // 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
            //            sql+= " where a.subj(+) = b.subj ";
            sql += " where a.subj = b.subj(+) ";
            sql += "    and (a.grcode  = " + SQLString.Format(p_grcode) + " or a.grcode is null) ";
            sql += "   and a.subj   = " + SQLString.Format(p_subj);
            sql += "   and a.sulpapernum   = " + SQLString.Format(p_sulpapernum);
            sql += " order by b.subj, a.sulpapernum ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    public ArrayList<DataBox> getExampleData(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_sulnum) throws Exception {
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = "select a.subj,     a.sulnum, a.selcount, a.selmax,  ";
            sql += "        a.distcode, c.codenm distcodenm, ";
            sql += "       a.sultype,  d.codenm sultypenm, ";
            sql += "       a.sultext,  b.selnum, b.seltext, b.selpoint ";
            sql += "  from tz_sul     a, ";
            sql += "       tz_sulsel  b, ";
            sql += "       tz_code    c, ";
            sql += "       tz_code    d  ";
            // 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
            //            sql+= " where a.subj     = b.subj(+)    ";
            //            sql+= "   and a.grcode   = b.grcode(+)  ";
            //			sql+= "   and a.sulnum   = b.sulnum(+)  ";
            sql += " where a.subj      =  b.subj(+)    ";
            sql += "   and a.grcode    =  b.grcode(+)  ";
            sql += "   and a.sulnum    =  b.sulnum(+)  ";
            sql += "   and a.distcode = c.code ";
            sql += "   and a.sultype  = d.code ";
            sql += "   and a.subj     = " + SQLString.Format(p_subj);
            sql += "   and a.grcode     = " + SQLString.Format(p_grcode);
            sql += "   and a.sulnum = " + SQLString.Format(p_sulnum);
            sql += "   and c.gubun    = " + SQLString.Format(SulmunSubjBean.DIST_CODE);
            sql += "   and d.gubun    = " + SQLString.Format(SulmunSubjBean.SUL_TYPE);
            sql += "   and d.levels    =  1 ";
            sql += " order by a.subj, a.sulnum, b.selnum ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    public int getSulnumSeq(String p_subj, String p_grcode) throws Exception {
        Hashtable<String, String> maxdata = new Hashtable<String, String>();
        maxdata.put("seqcolumn", "sulnum");
        maxdata.put("seqtable", "tz_sul");
        maxdata.put("paramcnt", "2");
        maxdata.put("param0", "subj");
        maxdata.put("param1", "grcode");
        maxdata.put("subj", SQLString.Format(p_subj));
        maxdata.put("grcode", SQLString.Format(p_grcode));

        return SelectionUtil.getSeq(maxdata);
    }

    public int insertTZ_sul(DBConnectionManager connMgr, String p_subj, String p_grcode, int p_sulnum, String p_distcode, String p_sultype, String p_sultext, int p_selcount, int p_selmax, String p_sulreturn, int p_scalecode, String p_luserid)
            throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_SUL table
            sql = "insert into TZ_SUL(subj, grcode, sulnum, distcode, sultype, sultext, selcount, selmax, sulreturn, scalecode, luserid, ldate ) ";
            sql += " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_grcode);
            pstmt.setInt(3, p_sulnum);
            pstmt.setString(4, p_distcode);
            pstmt.setString(5, p_sultype);
            pstmt.setString(6, p_sultext);
            pstmt.setInt(7, p_selcount);
            pstmt.setInt(8, p_selmax);
            pstmt.setString(9, p_sulreturn);
            pstmt.setInt(10, p_scalecode);
            pstmt.setString(11, p_luserid);
            pstmt.setString(12, FormatDate.getDate("yyyyMMddHHmmss"));

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk;
    }

    public int insertTZ_sulsel(PreparedStatement pstmt, String p_subj, String p_grcode, int p_sulnum, int p_selnum, String p_seltext, int p_selpoint, String p_luserid) throws Exception {
        int isOk = 0;

        try {
            pstmt.setString(1, p_subj);
            pstmt.setString(2, p_grcode);
            pstmt.setInt(3, p_sulnum);
            pstmt.setInt(4, p_selnum);
            pstmt.setString(5, p_seltext);
            pstmt.setInt(6, p_selpoint);
            pstmt.setString(7, p_luserid);
            pstmt.setString(8, FormatDate.getDate("yyyyMMddHHmmss"));
            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return isOk;
    }

    /**
     * 설문지 셀렉트박스 (RequestBox, 셀렉트박스명,선택값,이벤트명) TZ_SULPAPER 이용
     */
    public static String getSulPaperSelect(String p_grcode, String p_gyear, String p_subj, String name, int selected, String event, String p_subjseq) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String redamunt = null;
        String sql = "";
        DataBox dbox = null;

        redamunt = "  <SELECT name=" + name + " " + event + " > \n";

        //redamunt += " <option value='0'>설문지를 선택하세요.</option> \n";

        try {
            connMgr = new DBConnectionManager();

            sql = " select NVL(sulpapernum,0) sulpapernum,  " + "        (select sulpapernm from tz_sulpaper " + "         where subj='ALL' and grcode='ALL'  and sulpapernum=tz_subjseq.sulpapernum) sulpapernm " + " from tz_subjseq  " + " where subj="
                    + StringManager.makeSQL(p_subj) + " and grcode=" + StringManager.makeSQL(p_grcode) + " and  year=" + StringManager.makeSQL(p_gyear) + " and subjseq=" + SQLString.Format(p_subjseq) + " ";

            /*
             * sql = "select distinct grcode,       subj,   year,       "; sql+=
             * "       sulpapernum,  sulpapernm,  "; sql+=
             * "       totcnt,       sulnums"; sql+= "  from tz_sulpaper ";
             * sql+= " where grcode = " + StringManager.makeSQL(p_grcode); sql+=
             * "   and subj   = " +StringManager.makeSQL(p_subj); sql+=
             * "   and year   = " + StringManager.makeSQL(p_gyear); if
             * (!p_subjseq.equals("ALL")) { sql+= "   and subjseq   = " +
             * SQLString.Format(p_subjseq); } sql+=
             * " order by subj, sulpapernum asc";
             */

            ls = connMgr.executeQuery(sql);
            Log.info.println("설문지>>>>>>>>>" + sql);
            // String v_null_test = "";
            // String v_subj_bef = "";

            //while (ls.next()) {
            if (ls.next()) {
                dbox = ls.getDataBox();

                if (dbox.getInt("d_sulpapernum") != 0) {
                    redamunt += " <option value=" + dbox.getInt("d_sulpapernum");
                    if (selected == dbox.getInt("d_sulpapernum")) {
                        redamunt += " selected ";
                    }

                    redamunt += ">" + dbox.getString("d_sulpapernm") + "</option> \n";
                } else {
                    redamunt += " <option value='0'>해당과정에 설문지가 없습니다.</option> \n";
                }

            } else {

                redamunt += " <option value='0'>설문지를 선택하세요.</option> \n";
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        redamunt += "  </SELECT> \n";
        return redamunt;
    }
}