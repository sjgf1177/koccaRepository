//**********************************************************
//1. 제      목: 과정설문지
//2. 프로그램명: SulmunAllPaperBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: lyh
//7. 수      정: 조재형	2008.11.19
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
public class SulmunAllPaperBean {
    public final static String SPLIT_COMMA = ",";
    public final static String SPLIT_COLON = ":";

    //public final static String DEFAULT_GRCODE = "ALL";
    //public final static String DEFAULT_SUBJ   = "ALL";
    //public final static String DIST_CODE        = "0054";
    //public final static String SUL_TYPE         = "0011";
    public final static String OBJECT_QUESTION = "1";
    public final static String MULTI_QUESTION = "2";
    public final static String SUBJECT_QUESTION = "3";
    public final static String COMPLEX_QUESTION = "4";
    public final static String FSCALE_QUESTION = "5";
    public final static String SSCALE_QUESTION = "6";

    public final static String F_GUBUN = "";
    public final static String SUBJECT_SULMUN = "S";
    public final static String TARGET_SULMUN = "T";
    public final static String COMMON_SULMUN = "C";
    public final static String CONTENTS_SULMUN = "O";

    public SulmunAllPaperBean() {
    }

    /**
     * 설문문제 리스트
     * 
     * @param box
     * @return ArrayList
     **/
    public ArrayList<DataBox> selectQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;

        String v_tem_grcode = box.getString("tem_grcode");
        String v_gubun = box.getStringDefault("p_gubun", SulmunAllBean.DEFAULT_SUBJ);
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        String v_distcode = box.getStringDefault("s_distcode", "ALL");
        // String v_action = box.getStringDefault("p_action", "change");
        String v_grcode = box.getStringDefault("s_grcode", v_tem_grcode); // 값이 없으면 세션의 grcode로

        try {
            connMgr = new DBConnectionManager();
            v_subj = v_gubun;
            list = selectQuestionList(connMgr, v_grcode, v_subj, v_distcode);
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
     * 설문문제 리스트
     * 
     * @param connMgr
     * @param p_grcode 교육그룹
     * @param p_subj 과정
     * @param p_distcode분류코드 : 만족도,성취도
     * @return ArrayList
     **/
    public ArrayList<DataBox> selectQuestionList(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_distcode) throws Exception {
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = "select a.subj,     a.sulnum,  a.grcode, ";
            sql += "       a.distcode, b.codenm  distcodenm, ";
            sql += "       a.sultype,  ";
            sql += "       (select codenm from tz_code where a.sultype=code and gubun=" + SQLString.Format(SulmunAllBean.SUL_TYPE) + "  and levels=1) sultypenm,";
            sql += "       a.sultext    ";
            sql += "  from tz_sul    a, ";
            sql += "       tz_code   b ";
            sql += "   where a.distcode  = b.code ";
            //sql+= "   and a.sultype  = c.code ";
            sql += "   and a.grcode    = " + SQLString.Format(p_grcode);
            sql += "   and b.gubun    = " + SQLString.Format(SulmunAllBean.DIST_CODE);
            //sql+= "   and c.gubun    = " + SQLString.Format(SulmunAllBean.SUL_TYPE);
            sql += "   and subj = 'ALL'";
            sql += "   and b.levels    =  1 ";
            if (!p_subj.equals("ALL")) {
                sql += "   and a.subj     = " + SQLString.Format(p_subj);
            }
            if (!p_distcode.equals("ALL")) {
                sql += "   and a.distcode = " + SQLString.Format(p_distcode);
            }
            sql += " order by a.sulnum ";

            System.out.println("sql==========================" + sql);

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

    /**
     * 선택된 설문문제 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectPaperQuestionList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;

        String v_tem_grcode = box.getSession("tem_grcode");
        String v_grcode = box.getStringDefault("p_grcode", v_tem_grcode);
        String v_gubun = box.getStringDefault("p_gubun", SulmunAllBean.DEFAULT_SUBJ);
        String v_subj = box.getString("p_subj");
        int v_sulpapernum = box.getInt("p_sulpapernum");

        try {
            connMgr = new DBConnectionManager();
            v_subj = v_gubun;
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
     * 선택된 설문문제 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
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
        try {
            sql = "select sulpapernm, totcnt, sulnums";
            sql += "  from tz_sulpaper ";
            sql += " where grcode      = " + SQLString.Format(p_grcode);
            sql += " and subj        = " + SQLString.Format(p_subj);
            sql += "   and sulpapernum = " + SQLString.Format(p_sulpapernum);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_sulnums = ls.getString("sulnums");
                v_sulpapernm = ls.getString("sulpapernm");
            }
            if (box != null) {
                box.put("p_sulpapernm", v_sulpapernm);
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
                sql += "   and a.grcode    = " + SQLString.Format(p_grcode);
                sql += "   and b.gubun    = " + SQLString.Format(SulmunAllBean.DIST_CODE);
                sql += "   and c.gubun    = " + SQLString.Format(SulmunAllBean.SUL_TYPE);
                sql += "   and a.subj     = " + SQLString.Format(p_subj);
                sql += "   and c.levels  = 1  ";
                if (v_sulnums.equals(""))
                    v_sulnums = "-1";
                sql += "   and a.sulnum in (" + v_sulnums + ")";

                sql += " order by a.sulnum ";

                ls.close();
                ls = connMgr.executeQuery(sql);

                st = new StringTokenizer(v_sulnums, SulmunAllBean.SPLIT_COMMA);

                while (ls.next()) {
                    dbox = ls.getDataBox();

                    v_sulnum = String.valueOf(ls.getInt("sulnum"));

                    hash.put(v_sulnum, dbox);
                }

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

    /**
     * @param box receive from the form object and session
     * @return ArrayList 설문문제지 리스트
     */
    public String getPaperListSQL(String p_grcode, String p_subj, String p_subjsel, String p_upperclass, int p_sulpapernum) throws Exception {
        String sql = "";

        sql = "select grcode,       subj,    subjseq,     ";
        sql += "       sulpapernum,  sulpapernm, year, ";
        sql += "       totcnt,       sulnums, sulmailing, sulstart, sulend, progress, ";
        sql += "       'COMMON'      subjnm ";
        sql += "  from tz_sulpaper ";
        sql += " where grcode = " + SQLString.Format(p_grcode);
        sql += "   and subj   = " + SQLString.Format(p_subj);
        if (p_sulpapernum > 0) {
            sql += "   and sulpapernum   = " + SQLString.Format(p_sulpapernum);
        }
        sql += " order by subj, sulpapernum ";

        System.out.println("SulmunAllPaperBean 설문문제지 리스트 getPaperListSQL:" + sql);

        Log.info.println("sqlsulmoun>>>>>>>>>>" + sql);

        return sql;
    }

    /**
     * 메인 설문문제지 조회
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectPaperList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_tem_grcode = box.getSession("tem_grcode");
        String v_grcode = box.getStringDefault("p_grcode", v_tem_grcode);

        // java.util.Date d_now = new java.util.Date();
        // String v_gyear = box.getStringDefault("p_gyear", String.valueOf(d_now.getYear() + 1900));
        //String v_action    = box.getStringDefault("p_action", "change");
        String v_action = "go";
        String v_gubun = box.getStringDefault("p_gubun", SulmunAllBean.DEFAULT_SUBJ);

        String ss_upperclass = box.getStringDefault("s_upperclass", "ALL");
        // String ss_middleclass = box.getStringDefault("s_middleclass", "ALL");
        // String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");

        try {

            if (v_action.equals("go")) {
                ss_subjcourse = v_gubun;
                sql = getPaperListSQL(v_grcode, v_gubun, ss_subjcourse, ss_upperclass, -1);

                connMgr = new DBConnectionManager();
                ls = connMgr.executeQuery(sql);

                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
                }

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
    @SuppressWarnings("deprecation")
    public DataBox getPaperData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        DataBox dbox = null;

        String v_grcode = box.getStringDefault("p_grcode", SulmunAllBean.DEFAULT_GRCODE);
        String v_subj = box.getStringDefault("p_subj", SulmunAllBean.DEFAULT_SUBJ);
        java.util.Date d_now = new java.util.Date();
        String v_gyear = box.getStringDefault("p_gyear", String.valueOf(d_now.getYear() + 1900));
        String v_subjsel = box.getString("p_subjsel");
        String v_upperclass = box.getStringDefault("p_upperclass", "ALL");

        int v_sulpapernum = box.getInt("p_sulpapernum");

        try {
            connMgr = new DBConnectionManager();
            dbox = getPaperData(connMgr, v_grcode, v_subj, v_gyear, v_subjsel, v_upperclass, v_sulpapernum);

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
    public DataBox getPaperData(DBConnectionManager connMgr, String p_grcode, String p_subj, String p_gyear, String p_subjsel, String p_upperclass, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = getPaperListSQL(p_grcode, p_subj, p_subjsel, p_upperclass, p_sulpapernum);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

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

        if (dbox == null)
            dbox = new DataBox("resoponsebox");

        return dbox;
    }

    /**
     * 설문 문제지 등록
     * 
     * @param connMgr
     * @param p_grcode 교육그룹
     * @param p_subj 과정
     * @param p_sulpapernum 설문지번호
     * @param p_sulpapernm 설문지이름
     * @param p_totcnt 전체문제수
     * @param p_sulnums 문제번호
     * @param p_luserid 작성자
     * @return isOk
     **/
    public int insertTZ_sulpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, String p_sulpapernm, int p_totcnt, String p_sulnums, String p_luserid, String p_progress) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //insert TZ_SULPAPER table
            sql = "insert into TZ_SULPAPER ";
            sql += "(grcode, subj,  year,  subjseq, sulpapernum, sulpapernm,     ";
            sql += " totcnt, sulnums,  luserid,  ldate, progress )   ";
            sql += " values ";
            sql += "(?, ?, ?,  ?,  ?,  ?,  ";
            sql += " ?, ?, ?,  ?, ? ) ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, p_grcode);
            pstmt.setString(2, p_subj);
            pstmt.setString(3, FormatDate.getDate("yyyy"));
            pstmt.setString(4, "0001");
            pstmt.setInt(5, p_sulpapernum);
            pstmt.setString(6, p_sulpapernm);
            pstmt.setInt(7, p_totcnt);
            pstmt.setString(8, p_sulnums);
            pstmt.setString(9, p_luserid);
            pstmt.setString(10, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(11, p_progress);

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

    /**
     * 설문 문제지 수정
     * 
     * @param connMgr
     * @param p_grcode 교육그룹
     * @param p_subj 과정
     * @param p_sulpapernum 설문지번호
     * @param p_sulpapernm 설문지이름
     * @param p_totcnt 문제수
     * @param p_sulnums 문제번호
     * @param p_luserid 작성자
     * @return isOk
     **/
    public int updateTZ_sulpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, String p_sulpapernm, int p_totcnt, String p_sulnums, String p_luserid, String p_progress) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        try {
            //update TZ_SULPAPER table
            sql = " update TZ_SULPAPER ";
            sql += "    set sulpapernm   = ?, ";
            sql += "        year         = ?, ";
            sql += "        totcnt       = ?, ";
            sql += "        sulnums      = ?, "; // 선택된문제번호
            sql += "        luserid      = ?, ";
            sql += "        ldate        = ?, ";
            sql += "        progress     = ?  ";
            sql += "  where grcode       = ?  ";
            sql += "    and subj         = ?  ";
            sql += "    and sulpapernum  = ?  ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, p_sulpapernm);
            pstmt.setString(2, FormatDate.getDate("yyyy"));
            pstmt.setInt(3, p_totcnt);
            pstmt.setString(4, p_sulnums);
            pstmt.setString(5, p_luserid);
            pstmt.setString(6, FormatDate.getDate("yyyyMMddHHmmss"));
            pstmt.setString(7, p_progress);
            pstmt.setString(8, p_grcode);
            pstmt.setString(9, p_subj);
            pstmt.setInt(10, p_sulpapernum);

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

    /**
     * 설문 문제지 삭제
     * 
     * @param box receive from the form object and session
     * @return isOk
     **/
    public int deleteTZ_sulpaper(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, String p_duserid) throws Exception {
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls = null;
        int isOk = 0;

        try {

            // 삭제체크
            sql = " select  sulpapernum from tz_subjseq where sulpapernum=" + p_sulpapernum + " ";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                isOk = -2;
            }

            if (isOk == 0) {
                sql = "delete from TZ_SULPAPER ";
                sql += " where grcode     = ?  ";
                sql += "   and subj       = ?  ";
                sql += "   and sulpapernum= ?  ";

                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, p_grcode);
                pstmt.setString(2, p_subj);
                pstmt.setInt(3, p_sulpapernum);

                isOk = pstmt.executeUpdate();
            }

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

    /**
     * 설문 문제지 등록
     * 
     * @param box receive from the form object and session
     * @return isOk
     **/
    public int insertPaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;
        String v_tem_grcode = box.getSession("tem_grcode");
        String v_grcode = box.getStringDefault("p_grcode", v_tem_grcode);
        String v_gubun = box.getStringDefault("p_gubun", SulmunAllBean.DEFAULT_SUBJ);
        String v_subj = v_gubun;

        String v_sulpapernm = box.getString("p_sulpapernm");
        int v_totcnt = box.getInt("p_totcnt");
        String v_sulnums = box.getString("p_sulnums");
        //String v_sulmailing = box.getString("p_sulmailing");
        int v_sulpapernum = 0;
        //String v_sulstart = box.getString("p_sulstart");
        //String v_sulend   = box.getString("p_sulend");

        String v_luserid = box.getSession("userid");
        String v_progress = box.getString("p_progress");
        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            v_sulpapernum = getPapernumSeq(v_subj, v_grcode);

            isOk = insertTZ_sulpaper(connMgr, v_grcode, v_subj, v_sulpapernum, v_sulpapernm, v_totcnt, v_sulnums, v_luserid, v_progress);

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

    /**
     * 설문 문제지 수정
     * 
     * @param box receive from the form object and session
     * @return isOk
     **/
    public int updatePaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_gubun = box.getStringDefault("p_gubun", SulmunAllBean.DEFAULT_SUBJ);
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        v_subj = v_gubun;

        int v_sulpapernum = box.getInt("p_sulpapernum");
        String v_sulpapernm = box.getString("p_sulpapernm");
        int v_totcnt = box.getInt("p_totcnt");
        String v_sulnums = box.getString("p_sulnums");
        String v_progress = box.getString("p_progress");
        String v_luserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            isOk = updateTZ_sulpaper(connMgr, v_grcode, v_subj, v_sulpapernum, v_sulpapernm, v_totcnt, v_sulnums, v_luserid, v_progress);

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

    /**
     * 설문 문제지 삭제
     * 
     * @param box receive from the form object and session
     * @return isOk
     **/
    public int deletePaper(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        String sql = "";
        int isOk = 0;

        String v_grcode = box.getString("p_grcode");
        String v_gubun = box.getStringDefault("p_gubun", SulmunAllBean.DEFAULT_SUBJ);
        String v_subj = box.getStringDefault("s_subjcourse", "ALL");
        v_subj = v_gubun;

        int v_sulpapernum = box.getInt("p_sulpapernum");
        String v_duserid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            isOk = deleteTZ_sulpaper(connMgr, v_grcode, v_subj, v_sulpapernum, v_duserid);

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

    /**
     * 설문지 가져오기
     * 
     * @param box receive from the form object and session
     * @return isOk
     **/
    public Vector<String> getSulnums(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql = "";
        Vector<String> v_sulnums = new Vector<String>();
        String v_tokens = "";
        StringTokenizer st = null;

        try {
            sql = "select sulnums  \n";
            sql += "  from tz_sulpaper \n";
            //          sql+= " where grcode      = " + SQLString.Format(p_grcode);
            sql += " where grcode      = " + SQLString.Format(p_grcode);
            sql += "   and subj        = " + SQLString.Format(p_subj);
            sql += "   and sulpapernum = " + SQLString.Format(p_sulpapernum);

            System.out.println("SulmunAllPaperBean 설문지 가져오기 getSulnums 설문지 가져오기:" + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_tokens = ls.getString("sulnums");
            }

            st = new StringTokenizer(v_tokens, SulmunAllBean.SPLIT_COMMA);
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

    /**
     * 설문지의 문제 가져오기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<ArrayList<DataBox>> getSelnums(DBConnectionManager connMgr, String p_grcode, String p_subj, Vector<String> p_sulnums) throws Exception {
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

                sql = "select a.subj,     a.sulnum, a.selmax, \n";
                sql += "        a.distcode, c.codenm distcodenm, \n";
                sql += "       a.sultype,  d.codenm sultypenm, \n";
                sql += "       a.sultext,  b.selnum, b.seltext, b.selpoint \n";
                sql += "  from tz_sul     a, \n";
                sql += "       tz_sulsel  b, \n";
                sql += "       tz_code    c, \n";
                sql += "       tz_code    d  \n";
                // 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
                //            sql+= " where a.subj     = b.subj(+)    ";
                //            sql+= "   and a.sulnum   = b.sulnum(+)  ";
                //            sql+= "   and a.grcode = b.grcode(+) ";
                sql += " where a.subj      =  b.subj(+)    \n";
                sql += "   and a.sulnum    =  b.sulnum(+)  \n";
                sql += "   and a.grcode    =  b.grcode(+)  \n";
                sql += "   and a.distcode = c.code \n";
                sql += "   and a.sultype  = d.code \n";
                sql += "   and a.subj     = " + SQLString.Format(p_subj);
                sql += "   and a.grcode   = " + SQLString.Format(p_grcode);
                sql += "   and a.sulnum   = " + sulnum;
                sql += "   and c.gubun    = " + SQLString.Format(SulmunAllBean.DIST_CODE);
                sql += "   and d.gubun    = " + SQLString.Format(SulmunAllBean.SUL_TYPE);
                sql += "   and d.levels   =  1 ";
                sql += " order by a.subj, a.sulnum, b.selnum \n";

                System.out.println("SulmunAllPaperBean 설문지의 문제 가져오기 getSelnums 설문지 가져오기:" + sql);
                Log.info.println("sulmun>>>" + sql);
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
     * 설문지 미리보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList<ArrayList<DataBox>> selectPaperQuestionExampleList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<ArrayList<DataBox>> list = null;

        String v_grcode = box.getString("p_grcode");
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
     * 설문지 미리보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<ArrayList<DataBox>> selectPaperQuestionExampleList(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum, RequestBox box) throws Exception {
        Vector<String> v_sulnums = null;
        ArrayList<ArrayList<DataBox>> QuestionExampleDataList = null;

        try {
            // 설문지번호에 해당하는 설문번호를 vector로 받아온다. 벡터(설문번호1,3,5 ....)
            v_sulnums = getSulnums(connMgr, p_grcode, p_subj, p_sulpapernum);

            if (!v_sulnums.equals("")) {
                // 설문번호에 해당하는 문제리스트를 만든다. 리스트((설문번호1, 보기1,2,3..))
                QuestionExampleDataList = getSelnums(connMgr, p_grcode, p_subj, v_sulnums);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return QuestionExampleDataList;
    }

    /**
     * 설문가능진도 가져오기
     * 
     * @param p_subj 과정코드
     * @param p_year 과정년도
     * @param p_subjseq 과정차수
     * @return int 설문가능진도
     */
    public int getSulProgress(String p_subj, String p_year, String p_subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        // DataBox dbox = null;
        int result = 0;

        try {
            connMgr = new DBConnectionManager();

            sql = " select NVL(progress,0) progress from tz_sulpaper  ";
            sql += "  where subj = 'ALL' and grcode='ALL'     ";
            sql += "    and sulpapernum = ( select sulpapernum from tz_subjseq ";
            sql += "                         where subj    = " + SQLString.Format(p_subj);
            sql += "                           and year    = " + SQLString.Format(p_year);
            sql += "                           and subjseq = " + SQLString.Format(p_subjseq);
            sql += "                      )           ";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("progress");
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
        return result;
    }

    /**
     * 설문응시여부 가져오기
     * 
     * @param p_subj 과정코드
     * @param p_year 과정년도
     * @param p_subjseq 과정차수
     * @return int 설문응시여부
     */
    public int getSulResult(String p_subj, String p_year, String p_subjseq) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        // DataBox dbox = null;
        int result = 0;

        try {
            connMgr = new DBConnectionManager();

            sql = " select count(*) ";
            sql += "  from tz_suleach a, ";
            sql += "			 tz_subjseq b ";
            sql += " where a.subj=b.subj ";
            sql += "   and a.year=b.year ";
            sql += "   and a.subjseq=b.subjseq ";
            sql += "   and a.sulpapernum=b.sulpapernum ";
            sql += "   and a.subj=" + SQLString.Format(p_subj);
            sql += "   and a.year=" + SQLString.Format(p_year);
            sql += "   and a.subjseq=" + SQLString.Format(p_subjseq);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt(1);
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
        return result;
    }

    /**
     * 과정설문지 셀렉트박스 (RequestBox, 셀렉트박스명,선택값,이벤트명) TZ_SULPAPER 이용
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

            sql = " select NVL(sulpapernum,0) sulpapernum,  \n" + "        (select sulpapernm from tz_sulpaper \n" + "         where subj='ALL' \n" + " 		and grcode= " + StringManager.makeSQL(p_grcode)
                    + " 		and sulpapernum=tz_subjseq.sulpapernum) sulpapernm \n" + " from tz_subjseq  \n" + " where subj=" + StringManager.makeSQL(p_subj) + "  and grcode=" + StringManager.makeSQL(p_grcode) + " and  year=" + StringManager.makeSQL(p_gyear)
                    + " and subjseq= " + SQLString.Format(p_subjseq) + " \n";

            sql += " union " + "  select NVL(sulpapernum2,0) sulpapernum,  \n" + "        (select sulpapernm from tz_sulpaper \n" + "         where subj='ALL' \n" + " 		and grcode= " + StringManager.makeSQL(p_grcode)
                    + " 		and sulpapernum=tz_subjseq.sulpapernum2) sulpapernm \n" + " from tz_subjseq  \n" + " where subj=" + StringManager.makeSQL(p_subj) + "  and grcode=" + StringManager.makeSQL(p_grcode) + " and  year="
                    + StringManager.makeSQL(p_gyear) + " and subjseq= " + SQLString.Format(p_subjseq) + " \n" + " order by sulpapernum ";

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
            System.out.println("SulmunAllPaperBean 과정설문지 셀렉트박스:" + sql);
            Log.info.println("설문지>>>>>>>>>" + sql);
            // String v_null_test = "";
            // String v_subj_bef = "";

            //while (ls.next()) {
            while (ls.next()) {
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

            }
            //else{

            // redamunt += " <option value='0'>설문지를 선택하세요.</option> \n";
            //}
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

    /**
     * 과정설문지 셀렉트박스 (RequestBox, 셀렉트박스명,선택값,이벤트명) TZ_SULPAPER 이용
     */
    public static String getSulPaperSelect2(String p_grcode, String p_gyear, String p_grseq, String name, int selected, String event) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String redamunt = null;
        String sql = "";
        DataBox dbox = null;

        redamunt = "  <SELECT name=" + name + " " + event + " > \n";

        //redamunt += " <option value='0'>설문지를 선택하세요.</option> \n";

        try {
            connMgr = new DBConnectionManager();

            sql = " select NVL(sulpapernum,0) sulpapernum,  \n" + "        (select sulpapernm from tz_sulpaper \n" + "         where subj='ALL' \n" + " and grcode= " + StringManager.makeSQL(p_grcode)
                    + " and sulpapernum=tz_subjseq.sulpapernum) sulpapernm \n" + " from tz_subjseq  \n" + " where sulpapernum != 0 \n" + " and grcode=" + StringManager.makeSQL(p_grcode) + " and year=" + StringManager.makeSQL(p_gyear) + " and grseq= "
                    + SQLString.Format(p_grseq) + " \n";

            sql += " union " + "  select NVL(sulpapernum2,0) sulpapernum,  \n" + "        (select sulpapernm from tz_sulpaper \n" + "         where subj='ALL' \n" + " and grcode= " + StringManager.makeSQL(p_grcode)
                    + " and sulpapernum=tz_subjseq.sulpapernum2) sulpapernm \n" + " from tz_subjseq  \n" + " where sulpapernum != 0 \n" + "and grcode=" + StringManager.makeSQL(p_grcode) + " and year=" + StringManager.makeSQL(p_gyear) + " and grseq= "
                    + SQLString.Format(p_grseq) + " \n" + " order by sulpapernum ";

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
            while (ls.next()) {
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

            }
            //else{

            // redamunt += " <option value='0'>설문지를 선택하세요.</option> \n";
            //}
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