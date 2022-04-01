//**********************************************************
//  1. 제      목: Sample 자료실
//  2. 프로그램명: BulletinBeanjava
//  3. 개      요: Sample 자료실
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이정한 2003. 4. 26
//  7. 수      정: 이정한 2003. 4. 26
//**********************************************************

package com.credu.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * 자료실(HomePage) 관련 Sample Class
 * 
 * @date : 2003. 5
 * @author : j.h. lee
 */
public class SelectSubjBean {

    /**
     * 대분류 SELECT
     * 
     * @param box receive from the form object and session
     * @return ArrayList 대분류 리스트
     */
    public ArrayList<DataBox> getUpperClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String isCourse = box.getStringDefault("isCourse", "N"); //      코스가 있어야 하는지 여부
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select upperclass, classname";
            sql += " from tz_subjatt";
            sql += " where middleclass = '000'";
            sql += " and lowerclass = '000'";
            if (isCourse.equals("N")) { //     코스분류도 없다
                sql += " and middleclass != 'COU'";
            }

            sql += " order by classname";
            //System.out.println(sql);
            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            dbox = this.setAllSelectBox(ls);
            list.add(dbox);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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
     * 대분류 SELECT (ALL 제외)
     * 
     * @param box receive from the form object and session
     * @return ArrayList 대분류 리스트
     */
    public ArrayList<DataBox> getOnlyUpperClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String isCourse = box.getStringDefault("isCourse", "N"); //      코스가 있어야 하는지 여부
            //String isCourse = "N";        //      코스가 있어야 하는지 여부
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select upperclass, classname";
            sql += " from tz_subjatt";
            sql += " where middleclass = '000'";
            sql += " and lowerclass = '000'";

            if (isCourse.equals("N")) { //     코스분류도 없다
                sql += " and middleclass != 'COU'";
            }

            sql += " order by classname";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            //dbox = this.setAllSelectBox(ls);
            //list.add(dbox);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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
     * 대분류 SELECT (ALL 제외)
     * 
     * @param box receive from the form object and session
     * @return ArrayList 대분류 리스트
     */
    public ArrayList<DataBox> getOnlyUpperClass(RequestBox box, String firststr) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        StringTokenizer st = null;
        String upperstr = "";

        try {
            // String isCourse = box.getStringDefault("isCourse", "N"); // 코스가 있어야 하는지 여부
            // String isCourse = "N";        // 코스가 있어야 하는지 여부
            connMgr = new DBConnectionManager();
            if (firststr.indexOf(",") > 0) {
                st = new StringTokenizer(firststr, ",");
                while (st.hasMoreElements()) {
                    upperstr += StringManager.makeSQL((String) st.nextToken()) + ",";
                }
                upperstr += "''";
            } else {
                upperstr = StringManager.makeSQL(firststr);
            }

            list = new ArrayList<DataBox>();
            sql = "select upperclass, classname";
            sql += " from tz_subjatt";
            sql += " where middleclass = '000'";
            sql += " and lowerclass = '000'";
            //sql += " and substr(upperclass, 0, 1) in ("+upperstr+")";
            sql += " and upperclass in (" + upperstr + ")";
            sql += " order by upperclass";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            //dbox = this.setAllSelectBox(ls);
            //list.add(dbox);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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
     * 중분류 SELECT
     * 
     * @param box receive from the form object and session
     * @return ArrayList 중분류 리스트
     */
    public ArrayList<DataBox> getMiddleClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String ss_upperclass = box.getStringDefault("s_upperclass", "ALL");

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select middleclass, classname";
            sql += " from tz_subjatt";
            sql += " where upperclass = " + SQLString.Format(ss_upperclass);
            sql += " and lowerclass = '000'";
            sql += " order by classname";
            ls = connMgr.executeQuery(sql);

            dbox = this.setAllSelectBox(ls);
            list.add(dbox);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
     * 중분류 SELECT
     * 
     * @param box receive from the form object and session
     * @return ArrayList 중분류 리스트
     */
    public ArrayList<DataBox> getOnlyMiddleClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String ss_upperclass = box.getStringDefault("s_upperclass", "ALL");

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select middleclass, classname";
            sql += " from tz_subjatt";
            sql += " where upperclass = " + SQLString.Format(ss_upperclass);
            sql += " and lowerclass = '000'";
            sql += " order by classname";
            //System.out.print("sql_middleclass="+sql);
            ls = connMgr.executeQuery(sql);

            //dbox = this.setAllSelectBox(ls);
            //list.add(dbox);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
     * 소분류 SELECT
     * 
     * @param box receive from the form object and session
     * @return ArrayList 소분류 리스트
     */
    public ArrayList<DataBox> getLowerClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String ss_upperclass = box.getStringDefault("s_upperclass", "ALL");
            String ss_middleclass = box.getStringDefault("s_middleclass", "ALL");

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select lowerclass, classname";
            sql += " from tz_subjatt";
            sql += " where upperclass = " + SQLString.Format(ss_upperclass);
            sql += " and middleclass = " + SQLString.Format(ss_middleclass);
            sql += " order by classname";

            ls = connMgr.executeQuery(sql);

            dbox = this.setAllSelectBox(ls);
            list.add(dbox);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
     * 과정 SELECT
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정 리스트
     */
    public ArrayList<DataBox> getSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            System.out.println();
            String isCourse = box.getStringDefault("isCourse", "N"); //      코스가 있어야 하는지 여부
            String isOffSubj = box.getStringDefault("isOffSubj", "N"); //      집합과정 여부

            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");

            String ss_upperclass = box.getStringDefault("s_upperclass", "ALL");
            String ss_middleclass = box.getStringDefault("s_middleclass", "ALL");
            String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");

            String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
            String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수

            // String ss_subjsearchkey = box.getSession("subjsearchkey");

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            if (v_gadmin.equals("F") || v_gadmin.equals("P")) { //  과정관리자, 강사
                sql = "select distinct a.subj, a.subjnm";
                sql += " from tz_subj a, tz_subjman b";
                sql += " where 1=1 ";//a.isapproval = 'Y'
                sql += "   and a.subj = b.subj and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " + SQLString.Format(s_gadmin);
                if (isOffSubj.equals("Y")) {
                    sql += " and a.isonoff = 'OFF'  ";
                }
                sql += " order by a.subjnm";
            } else if (v_gadmin.equals("H")) { //  교육그룹관리자
                sql = "select distinct a.subj, a.subjnm";
                sql += " from tz_subj a, ";
                sql += " (select e.subjcourse as subj from tz_grsubj e, tz_grcodeman f where 1=1 ";//a.isapproval = 'Y'
                sql += "    and f.userid = " + SQLString.Format(s_userid) + " and f.gadmin = " + SQLString.Format(s_gadmin);
                sql += " and e.grcode = f.grcode) b";
                sql += " where a.subj = b.subj";
                if (isOffSubj.equals("Y")) {
                    sql += " and a.isonoff = 'OFF'  ";
                }
                sql += " order by a.subjnm";
            } else if (v_gadmin.equals("K")) { //  회사관리자, 부서관리자
                sql = "select distinct a.subj, a.subjnm";
                sql += " from tz_subj a, tz_grsubj b,";
                sql += " (select e.grcode from tz_grcomp e, tz_compman f where 1=1 ";//a.isapproval = 'Y'
                sql += "    and f.userid = " + SQLString.Format(s_userid) + " and f.gadmin = " + SQLString.Format(s_gadmin);
                sql += " and substring(e.comp, 1, 4) = substring(f.comp, 1, 4)) c";
                sql += " where a.subj = b.subjcourse and b.grcode = c.grcode";
                if (isOffSubj.equals("Y")) {
                    sql += " and a.isonoff = 'OFF'  ";
                }
                sql += " order by a.subjnm";
            } else { //  Ultravisor, Supervisor
                sql = "select distinct a.subj, a.subjnm from tz_subj a, tz_grsubj b, tz_subjseq c where 1=1 "; //a.isapproval = 'Y' ";
                if (!ss_upperclass.equals("ALL") && ss_middleclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && !ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    sql += " and a.lowerclass = " + SQLString.Format(ss_lowerclass);
                }
                if (!ss_grcode.equals("ALL") && ss_grseq.equals("ALL")) { //      교육그룹만 선택된 경우
                    sql += " and b.grcode = " + SQLString.Format(ss_grcode) + " and a.subj = b.subjcourse";
                }
                if (!ss_grcode.equals("ALL") && !ss_grseq.equals("ALL")) { //      교육그룹과 교육차수가 선택된 경우
                    sql += " and b.grcode = " + SQLString.Format(ss_grcode) + " and a.subj = b.subjcourse";
                    sql += " and c.grseq = " + SQLString.Format(ss_grseq) + " and a.subj = c.subj";
                    sql += " and c.course = '000000'";
                }

                if (isOffSubj.equals("Y")) {
                    sql += " and isonoff = 'OFF'  ";
                }//집합과정

                if (isCourse.equals("Y")) { //     코스가 있다
                    sql += " union";
                    sql += " select distinct a.course, a.coursenm from tz_course a, tz_grsubj b, tz_courseseq c where 1=1";
                    if (!ss_upperclass.equals("ALL") && ss_middleclass.equals("ALL")) {
                        sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && ss_lowerclass.equals("ALL")) {
                        sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                        sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && !ss_lowerclass.equals("ALL")) {
                        sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                        sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                        sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    }
                    if (!ss_grcode.equals("ALL") && ss_grseq.equals("ALL")) { //      교육그룹만 선택된 경우
                        sql += " and b.grcode = " + SQLString.Format(ss_grcode) + " and a.course = b.subjcourse";
                    }
                    if (!ss_grcode.equals("ALL") && !ss_grseq.equals("ALL")) { //      교육그룹과 교육차수가 선택된 경우
                        sql += " and b.grcode = " + SQLString.Format(ss_grcode) + " and a.course = b.subjcourse";
                        sql += " and c.grseq = " + SQLString.Format(ss_grseq) + " and a.course = c.course";
                    }
                }
                sql += " order by 2";
            }

            ls = connMgr.executeQuery(sql);
            System.out.println("정렬된 데이타" + sql);

            if (v_gadmin.equals("A") || v_gadmin.equals("B") || v_gadmin.equals("H") || v_gadmin.equals("K")) { //      Ultra/Super or 교육그룹관리자 or 회사/부서/부서장인 경우 'ALL' 과정 출력
                dbox = this.setAllSelectBox(ls);
                list.add(dbox);
            } else {
                dbox = this.setSpaceSelectBox(ls);
                list.add(dbox);
            }

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
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
     * 과정차시 SELECT without ALL
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정차시 리스트
     */
    public static String getSubj2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        // ArrayList list = null;
        String sql = "";
        // DataBox dbox = null;
        String result = "";

        String s_gadmin = box.getSession("gadmin");
        String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
        String s_userid = box.getSession("userid");

        try {
            // String ss_subj = box.getStringDefault("s_subjcourse", "ALL");

            connMgr = new DBConnectionManager();

            // list = new ArrayList();
            if (v_gadmin.equals("F") || v_gadmin.equals("P")) { //  과정관리자, 강사
                sql = "select distinct a.subj, a.subjnm";
                sql += " from tz_subj a, tz_subjman b";
                sql += " where 1=1 ";//a.isapproval = 'Y'
                sql += " and a.subj = b.subj and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " + SQLString.Format(s_gadmin);
                sql += " order by a.subjnm";
            } else if (v_gadmin.equals("A")) {
                sql = "select distinct subj, subjnm";
                sql += " from tz_subj";
                sql += " where 1=1";//a.isapproval = 'Y'
                sql += " order by subjnm";
            }

            pstmt = connMgr.prepareStatement(sql);

            ls = new ListSet(pstmt);

            result += getSelectTag(ls, false, false, "s_subj", "");

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
        return result;
    }

    /**
     * 과정차시 SELECT without ALL
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정차시 리스트
     */
    public static String getSubj3(RequestBox box, String category) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        // ArrayList list = null;
        String sql = "";
        // DataBox dbox = null;
        String result = "";

        try {
            //String  g_category = box.getString("g_categorycd");

            connMgr = new DBConnectionManager();

            // list = new ArrayList();
            sql = "SELECT DISTINCT A.SUBJ, A.SUBJNM \n";
            sql += "FROM    TZ_SUBJ A, TZ_SUBJSEQ B \n";
            sql += "WHERE   A.SUBJ = B.SUBJ \n";
            sql += "AND B.YEAR = '2013' \n";
            sql += "AND B.GRCODE = 'N000001' \n";
            sql += "AND B.EDUSTART = '2013060100' \n";

            //sql = "select subj, subjnm ";
            //sql += " from tz_subjseq where year ='2013' and grcode='N000001' and edustart ='2013060100' ";
            if (!(category.equals("MINE") || category.equals(""))) {
                sql += " and a.area = '" + category + "'";
            }
            sql += " order by a.subjnm";

            //System.out.println(sql);

            pstmt = connMgr.prepareStatement(sql);
            ls = new ListSet(pstmt);
            result += getSelectTag(ls, false, false, "p_catecd", "");
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
        return result;
    }

    /**
     * 과정차시 SELECT without ALL
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정차시 리스트
     */
    public static String getSubjInfo(RequestBox box, String category) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        // ArrayList list = null;
        String sql = "";
        // DataBox dbox = null;
        String result = "";

        try {
            //String  g_category = box.getString("g_categorycd");

            connMgr = new DBConnectionManager();

            // list = new ArrayList();
            sql = "SELECT   DISTINCT A.SUBJ||B.SUBJSEQ||B.YEAR AS SUBJINFO, A.SUBJNM \n";
            sql += "  FROM   TZ_SUBJ A, TZ_SUBJSEQ B \n";
            sql += "WHERE       A.SUBJ = B.SUBJ \n";
            sql += "AND B.YEAR = TO_CHAR(SYSDATE,'YYYY') \n";
            sql += "AND B.GRCODE = 'N000001' \n";
            sql += "AND SUBSTR(B.EDUSTART,0,6) = TO_CHAR(SYSDATE,'YYYYMM') AND SUBSTR(B.EDUEND,0,6) = TO_CHAR(SYSDATE,'YYYYMM') \n";
            //sql += "AND SULSTARTDATE2 IS NOT NULL \n";

            //sql = "select subj, subjnm ";
            //sql += " from tz_subjseq where year ='2013' and grcode='N000001' and edustart ='2013060100' ";
            if (!(category.equals("MINE") || category.equals("") || category.equals("C0"))) {
                sql += " and a.area = '" + category + "'";
            }
            sql += " order by a.subjnm";

            //System.out.println(sql);

            pstmt = connMgr.prepareStatement(sql);
            ls = new ListSet(pstmt);
            result += getSelectTag(ls, false, false, "p_subjinfo", "");
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
        return result;
    }

    /**
     * 과정차수 SELECT
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정차수 리스트
     */
    public ArrayList<DataBox> getSubjseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");
            String ss_gyear = box.getString("s_gyear"); //  년도

            String ss_grcode = box.getStringDefault("s_grcode", "ALL"); // 교육그룹
            String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //   교육차수

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select distinct";
            // 수정일 : 05.11.03 수정자 : 이나연 _ decode 수정
            //          sql += " decode(b.course, '000000', a.subjseq, b.courseseq) subjseq";
            sql += " case b.course ";
            sql += " 		When '000000' 	Then  a.subjseq ";
            sql += " 		Else b.courseseq ";
            sql += " End subjseq";
            sql += " from tz_subjseq a, tz_courseseq b";
            //          sql += " where decode(b.course, '000000', a.subj, b.course) = " + SQLString.Format(ss_subjcourse);
            sql += " where case b.course ";
            sql += " 			When '000000' 	Then  a.subj ";
            sql += " 			Else b.course ";
            sql += " End = " + SQLString.Format(ss_subjcourse);
            //          sql += " and decode(b.course, '000000', a.gyear, b.gyear) = " + SQLString.Format(ss_gyear);
            sql += " and case b.course ";
            sql += " 			When '000000' 	Then  a.gyear ";
            sql += " 			Else b.gyear ";
            sql += " End  = " + SQLString.Format(ss_gyear);
            if (!ss_grcode.equals("ALL") && ss_grseq.equals("ALL") && !ss_subjcourse.equals("ALL")) { //      교육그룹만 선택된 경우
            //              sql += " and decode(b.course, '000000', a.grcode, b.grcode) = " + SQLString.Format(ss_grcode);
                sql += " and case b.course ";
                sql += " 			When '000000' 	Then  a.grcode ";
                sql += " 			Else b.grcode ";
                sql += " End  = " + SQLString.Format(ss_grcode);
            } else if (!ss_grcode.equals("ALL") && !ss_grseq.equals("ALL") && !ss_subjcourse.equals("ALL")) { //      교육그룹과 교육차수가 선택된 경우
            //              sql += " and decode(b.course, '000000', a.grcode, b.grcode) = " + SQLString.Format(ss_grcode);
                sql += " and case b.course ";
                sql += " 			hen '000000' 	Then  a.grcode ";
                sql += " 			Else b.grcode ";
                sql += " End  = " + SQLString.Format(ss_grcode);
                //              sql += " and decode(b.course, '000000', a.grseq, b.grseq) = " + SQLString.Format(ss_grseq);
                sql += " and case b.course ";
                sql += " 			When '000000' 	Then  a.grseq ";
                sql += " 			Else b.grseq ";
                sql += " End  = " + SQLString.Format(ss_grseq);
            }
            //            sql += " order by decode(b.course, '000000', a.subjseq, b.courseseq)";
            sql += " order by case b.course ";
            sql += " 					When '000000' 	Then  a.subjseq";
            sql += " 					Else b.courseseq";
            sql += " End ";
            // 수정일 : 05.11.03 수정자 : 이나연 _ 여기까지 수정

            ls = connMgr.executeQuery(sql); //System.out.println(sql);

            dbox = this.setAllSelectBox(ls);
            list.add(dbox);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
     * 과정차시 SELECT
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정차시 리스트
     */
    public ArrayList<DataBox> getSubjLesson(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String ss_subj = box.getStringDefault("s_subj", "ALL");

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select lesson, sdesc    ";
            sql += "   from tz_subjlesson    ";
            sql += "  where subj = " + SQLString.Format(ss_subj);
            sql += "  order by lesson        ";

            ls = connMgr.executeQuery(sql);

            dbox = this.setAllSelectBox(ls);
            list.add(dbox);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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
     * 과정차시 SELECT without ALL
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정차시 리스트
     */
    public ArrayList<DataBox> getSubjLesson2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String ss_subj = box.getStringDefault("s_subjcourse", "ALL");

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select lesson, sdesc    ";
            sql += "   from tz_subjlesson    ";
            sql += "  where subj = " + SQLString.Format(ss_subj);
            sql += "  order by lesson        ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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

    /*
     * 
     * 과정 Class 정보
     */
    public ArrayList<DataBox> getSubjClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select class , classnm ";
            sql += " from tz_class   ";
            sql += " where   subj    = " + SQLString.Format(ss_subjcourse);
            sql += "     and year    =" + SQLString.Format(box.getString("s_gyear"));
            sql += "     and subjseq =" + SQLString.Format(box.getString("s_subjseq"));
            sql += " order by class  ";
            //System.out.println("sql class==>" + sql);
            ls = connMgr.executeQuery(sql);

            dbox = this.setAllSelectBox(ls);
            list.add(dbox);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
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

    public DataBox setAllSelectBox(ListSet ls) throws Exception {
        DataBox dbox = null;
        int columnCount = 0;
        try {
            dbox = new DataBox("selectbox");

            ResultSetMetaData meta = ls.getMetaData();
            columnCount = meta.getColumnCount();//System.out.println("columnCount : " + columnCount);
            for (int i = 1; i <= columnCount; i++) {
                String columnName = meta.getColumnName(i).toLowerCase();//System.out.println("columnName : " + columnName);

                dbox.put("d_" + columnName, "ALL");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("SelectBean.setAllDataBox()\r\n\"" + ex.getMessage());
        }
        return dbox;
    }

    public DataBox setSpaceSelectBox(ListSet ls) throws Exception {
        DataBox dbox = null;
        int columnCount = 0;
        try {
            dbox = new DataBox("selectbox");

            ResultSetMetaData meta = ls.getMetaData();
            columnCount = meta.getColumnCount();//System.out.println("columnCount : " + columnCount);
            for (int i = 1; i <= columnCount; i++) {
                String columnName = meta.getColumnName(i).toLowerCase();//System.out.println("columnName : " + columnName);

                dbox.put("d_" + columnName, "----");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("SelectBean.setAllDataBox()\r\n\"" + ex.getMessage());
        }
        return dbox;
    }

    /**
     * 오프라인 분류 SELECT
     * 
     * @param box receive from the form object and session
     * @return ArrayList 오프라인 분류
     */
    public static String getOffGubun(RequestBox box, boolean isChange, boolean isALL, String grcode) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        // ArrayList list = null;
        String sql = "";

        String result = "OffLine분류 ";
        // DataBox dbox = null;

        String ss_upperclass = box.getStringDefault("s_subjgubun", "ALL"); //1단계 과정분류

        try {
            connMgr = new DBConnectionManager();

            // list = new ArrayList();

            sql = "Select code, codenm From TZ_CODEGUBUN  cg ";
            sql += "	join tz_code c on c.gubun = cg.gubun ";
            sql += " Where cg.gubun ='0061'";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            result += getSelectTag(ls, isChange, isALL, "s_subjgubun", ss_upperclass);
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
        return result;
    }

    /**
     * 
     * @param box
     * @param isChange
     * @param isALL
     * @param isStatisticalPage
     * @return
     * @throws Exception
     */
    public static String getUpperClass(RequestBox box, boolean isChange, boolean isALL, boolean isStatisticalPage) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "대분류 ";
        boolean isVisible = false;
        System.out.println("############## Select Tag 방식으로 전환 [code.list.course.0002] ##############");

        String v_gadmin = box.getSession("gadmin").substring(0, 1);
        String v_tem_grcode = box.getSession("tem_grcode");

        try {
            isVisible = getIsVisible(isStatisticalPage, 1); //      UpperClass = 1;

            if (isVisible) {
                String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); //1단계 과정분류

                connMgr = new DBConnectionManager();

                sql = "select distinct upperclass, classname";
                sql += " from tz_subjatt";
                sql += " where middleclass = '000'";
                sql += " and lowerclass = '000'";

                ConfigSet conf = new ConfigSet();

                if (!conf.getBoolean("course.use")) { //     코스분류 없다
                    sql += " and upperclass != 'COUR'";
                }

                //권한체크 시작
                //총괄관리자가 아닌경우
                if (!v_gadmin.equals("A")) {
                    if (v_tem_grcode.equals("N000001")) {
                        sql += " and upperclass = 'K01'";
                    }

                    if (v_tem_grcode.equals("N000002")) {
                        sql += " and upperclass = 'G01'";
                    }
                }

                sql += " order by classname";

                pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ls = new ListSet(pstmt);

                //총괄관리자가 아닌경우
                //if (v_gadmin.equals("A")) {
                result += getSelectTag(ls, isChange, isALL, "s_upperclass", ss_upperclass);
                //} else {
                //	result += getSelectTag(ls, isChange, false, "s_upperclass", ss_upperclass);
                //}
            } else {
                result = "";
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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

    public static String getMiddleClass(RequestBox box, boolean isChange, boolean isALL, boolean isStatisticalPage) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "중분류 ";
        boolean isVisible = false;
        System.out.println("############## Select Tag 방식으로 전환 [code.list.course.0003] ##############");

        try {
            isVisible = getIsVisible(isStatisticalPage, 2); //      MiddleClass = 2;

            if (isVisible) {
                String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); //     1단계 과정분류
                String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); //       2단계 과정분류
                //System.out.println("ss_middleclass" + ss_middleclass);
                connMgr = new DBConnectionManager();

                sql = "select distinct middleclass, classname";
                sql += " from tz_subjatt";
                sql += " where upperclass = " + SQLString.Format(ss_upperclass);
                sql += " and middleclass != '000'";
                sql += " and lowerclass = '000'";
                sql += " order by classname";

                pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ls = new ListSet(pstmt);

                result += getSelectTag(ls, isChange, isALL, "s_middleclass", ss_middleclass);
            } else {
                result = "";
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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

    public static String getLowerClass(RequestBox box, boolean isChange, boolean isALL, boolean isStatisticalPage) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "소분류 ";
        boolean isVisible = false;
        System.out.println("############## Select Tag 방식으로 전환 [code.list.course.0004] ##############");

        try {
            isVisible = getIsVisible(isStatisticalPage, 3); //      LowerClass = 3;

            if (isVisible) {
                String ss_upperclass = box.getStringDefault("s_upperclass", "ALL"); //     1단계 과정분류
                String ss_middleclass = box.getStringDefault("s_middleclass", "ALL"); //       2단계 과정분류
                String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL"); //       3단계 과정분류

                connMgr = new DBConnectionManager();

                sql = "select distinct lowerclass, classname";
                sql += " from tz_subjatt";
                sql += " where upperclass = " + SQLString.Format(ss_upperclass);
                sql += " and middleclass = " + SQLString.Format(ss_middleclass);
                sql += " and lowerclass != '000'";
                sql += " order by classname";

                pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

                ls = new ListSet(pstmt);

                result += getSelectTag(ls, isChange, isALL, "s_lowerclass", ss_lowerclass);
            } else {
                result = "";
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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

    public static String getMasterSubj(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        return getMasterSubj(box, isChange, isALL, false, false);
    }

    public static String getMasterSubj(RequestBox box, boolean isChange, boolean isALL, boolean isTarget) throws Exception {
        return getMasterSubj(box, isChange, isALL, false, isTarget);
    }

    public static String getMasterSubj(RequestBox box, boolean isChange, boolean isALL, boolean justOn, boolean isTarget) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "마스터과정";

        try {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            // String s_userid = box.getSession("userid");

            // String ss_upperclass = box.getStringDefault("s_upperclass", "ALL");
            // String ss_middleclass = box.getStringDefault("s_middleclass", "ALL");
            // String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");

            String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
            String ss_gyear = box.getStringDefault("s_gyear", FormatDate.getDate("yyyy")); //교육그룹
            String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수
            // String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //
            String ss_mastercd = box.getStringDefault("s_mastercd", "ALL"); //

            connMgr = new DBConnectionManager();

            //if(v_gadmin.equals("F") || v_gadmin.equals("P")) {      //  과정관리자, 강사
            //    sql = "select distinct a.subj, a.subjnm";
            //    sql += " from tz_subj a, tz_subjman b";
            //    sql += " where a.subj = b.subj and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " + SQLString.Format(s_gadmin);
            //    if(justOn) sql += " and a.isonoff = 'ON' ";
            //    if(justOff) sql += " and a.isonoff = 'OFF' ";
            //    sql += " order by a.subj";
            //}
            //else if(v_gadmin.equals("H")) {     //  교육그룹관리자
            //    sql = "select distinct a.subj, a.subjnm";
            //    sql += " from tz_subj a, ";
            //    sql += " (select e.subjcourse as subj from tz_grsubj e, tz_grcodeman f where f.userid = " + SQLString.Format(s_userid) + " and f.gadmin = " + SQLString.Format(s_gadmin);
            //    sql += " and e.grcode = f.grcode) b";
            //    sql += " where a.subj = b.subj";
            //    if(justOn) sql += " and a.isonoff = 'ON' ";
            //    if(justOff) sql += " and a.isonoff = 'OFF' ";
            //    sql += " order by a.subj";
            //}
            //else if(v_gadmin.equals("K")) {     //  회사관리자, 부서관리자
            //    sql = "select distinct a.subj, a.subjnm";
            //    sql += " from tz_subj a, tz_grsubj b,";
            //    sql += " (select e.grcode from tz_grcomp e, tz_compman f where f.userid = " + SQLString.Format(s_userid) + " and f.gadmin = " + SQLString.Format(s_gadmin);
            //    sql += " and substr(e.comp, 1, 4) = substr(f.comp, 1, 4)) c";
            //    sql += " where a.subj = b.subjcourse and b.grcode = c.grcode";
            //    if(justOn) sql += " and a.isonoff = 'ON' ";
            //    if(justOff) sql += " and a.isonoff = 'OFF' ";
            //    sql += " order by a.subj";
            //}
            //else {      //  Ultravisor, Supervisor
            sql = "select mastercd, masternm from tz_mastercd where 1=1";

            if (!ss_grcode.equals("ALL") && !ss_gyear.equals("ALL")) { //      교육그룹과 교육년도가 선택된 경우
                sql += " and grcode = " + SQLString.Format(ss_grcode);
                //sql += " and gyear  = " + SQLString.Format(ss_gyear);
                //
            }

            sql += " and gyear  = " + SQLString.Format(ss_gyear);

            if (!ss_grcode.equals("ALL") && !ss_gyear.equals("ALL") && !ss_grseq.equals("ALL")) { //      교육차수가 선택된 경우
                sql += " and grseq = " + SQLString.Format(ss_grseq);
                //
            }

            if (isTarget) {
                sql += " and isedutarget = 'Y'";
            }
            sql += " order by masternm";
            //     System.out.println("sql_master11="+sql);
            //}
            //Log.info.println("sql---------------\n" + sql);
            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            if (v_gadmin.equals("A") || v_gadmin.equals("B") || v_gadmin.equals("H") || v_gadmin.equals("K")) { //      Ultra/Super or 교육그룹관리자 or 회사/부서/부서장인 경우 'ALL' 과정 출력
                result += getSelectTag(ls, isChange, isALL, "s_mastercd", ss_mastercd);
            } else {
                result += getSelectTag(ls, isChange, false, "s_mastercd", ss_mastercd);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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

    public static String getBetaSubj(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        return getBetaSubj(box, isChange, isALL, false, false);
    }

    public static String getBetaSubj(RequestBox box, boolean isChange, boolean isALL, boolean justOn, boolean justOff) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "과정 ";

        try {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");

            String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //교육차수

            connMgr = new DBConnectionManager();

            if (s_gadmin.equals("A1") || s_gadmin.equals("A2")) { //  Ultravisor, Supervisor
                sql = "select subj, subjnm from tz_betasubj order by subjnm";
            } else { //
                sql = "select a.subj, a.subjnm";
                sql += " from tz_betasubj a, tz_betacpinfo b";
                sql += " where a.company=b.betacpno ";
                sql += "   and a.cuserid=" + SQLString.Format(s_userid);
                sql += " order by a.subjnm";

            }

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            if (v_gadmin.equals("A") || v_gadmin.equals("B")) { //      Ultra/Super or 교육그룹관리자 or 회사/부서/부서장인 경우 'ALL' 과정 출력
                result += getSelectTag(ls, isChange, isALL, "s_subjcourse", ss_subjcourse);
            } else {
                result += getSelectTag(ls, isChange, false, "s_subjcourse", ss_subjcourse);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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

    public static String getSubj(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        return getSubj(box, isChange, isALL, false, false);
    }

    public static String getSubj(RequestBox box, boolean isChange, boolean isALL, boolean justOn, boolean justOff) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "과정 ";
        System.out.println("############## Select Tag 방식으로 전환 [code.list.course.subjlist] ##############");
        //예정
        try {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");

            String ss_upperclass = box.getStringDefault("s_upperclass", "ALL");
            String ss_middleclass = box.getStringDefault("s_middleclass", "ALL");
            String ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");

            String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
            String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수

            String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //과정
            //String  s_subjsearchkey = box.getSession("sbujsearchkey");
            String s_subjsearchkey = box.getString("s_subjsearchkey");
            //    System.out.println("s_subjsearchkey===========================>"+s_subjsearchkey);
            //System.out.println(s_userid);
            connMgr = new DBConnectionManager();

            ConfigSet conf = new ConfigSet();

            if (v_gadmin.equals("F") || v_gadmin.equals("P")) { //  과정관리자, 강사
                sql = "select distinct a.subj, a.subjnm";
                sql += " from tz_subj a, tz_subjman b";

                if (!ss_grcode.equals("ALL")) {
                    sql += " , tz_grsubj c ";
                }

                sql += " where a.isuse='Y' ";//and a.isapproval='Y'
                sql += "   and a.subj = b.subj and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " + SQLString.Format(s_gadmin);
                if (!s_subjsearchkey.equals("")) {
                    sql += "  and a.subjnm like '%" + s_subjsearchkey + "%'";
                }
                if (justOn)
                    sql += " and a.isonoff = 'ON' ";
                if (justOff)
                    sql += " and a.isonoff = 'OFF' ";

                if (!ss_grcode.equals("ALL")) {
                    sql += " and c.grcode = " + SQLString.Format(ss_grcode) + " and a.subj = c.subjcourse";
                }

                if (!ss_upperclass.equals("ALL") && ss_middleclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && !ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    sql += " and a.lowerclass = " + SQLString.Format(ss_lowerclass);
                }

                //by heesung2 - 2006-06-21
                if (conf.getBoolean("course.use")) { //     코스분류 있다
                    sql += " union";
                    sql += " select distinct a.course, a.coursenm 	\n";
                    sql += " from 	tz_course a, 					\n";
                    sql += "		tz_grsubj b,                    \n";
                    sql += "		tz_courseseq c ,                \n";

                    sql += "		tz_subjseq d,                   \n";
                    sql += "		tz_subjman e                    \n";
                    sql += " where 1=1 ";
                    if (!ss_upperclass.equals("ALL") && ss_middleclass.equals("ALL")) {
                        sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && ss_lowerclass.equals("ALL")) {
                        sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                        sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && !ss_lowerclass.equals("ALL")) {
                        sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                        sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                        sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    }
                    if (!ss_grcode.equals("ALL") && ss_grseq.equals("ALL")) { //      교육그룹만 선택된 경우
                        sql += " and b.grcode = " + SQLString.Format(ss_grcode) + " and a.course = b.subjcourse";
                    }
                    if (!ss_grcode.equals("ALL") && !ss_grseq.equals("ALL")) { //      교육그룹과 교육차수가 선택된 경우
                        sql += " and b.grcode = " + SQLString.Format(ss_grcode) + " and a.course = b.subjcourse";
                        sql += " and c.grseq = " + SQLString.Format(ss_grseq) + " and a.course = c.course";
                    }

                    sql += "	and b.grcode = d.grcode			\n";
                    sql += "	and a.course = d.course         \n";
                    sql += "	and c.course = d.course         \n";
                    sql += "	and c.courseseq = d.courseseq   \n";
                    sql += "	and d.subj = e.subj             \n";
                    sql += "	and e.userid = " + SQLString.Format(s_userid);

                }

                sql += " order by subjnm";
            } else if (v_gadmin.equals("H")) { //  교육그룹관리자
                sql = "select distinct a.subj, a.subjnm";
                sql += " from tz_subj a, ";
                sql += " (select e.subjcourse as subj, e.grcode as grcode from tz_grsubj e, tz_grcodeman f where f.userid = " + SQLString.Format(s_userid) + " and f.gadmin = " + SQLString.Format(s_gadmin);
                sql += " and e.grcode = f.grcode) b,";
                sql += " tz_subjseq c ";
                sql += " where a.subj = b.subj and a.isuse='Y' ";//and a.isapproval='Y' ";
                sql += " and b.subj = c.subj";
                sql += " and b.grcode = c.grcode";
                sql += " and c.grseq = '" + ss_grseq + "'";

                if (!s_subjsearchkey.equals("")) {
                    sql += "  and a.subjnm like '%" + s_subjsearchkey + "%'";
                }
                if (justOn)
                    sql += " and a.isonoff = 'ON' ";
                if (justOff)
                    sql += " and a.isonoff = 'OFF' ";

                if (!ss_grcode.equals("ALL")) {
                    sql += " and b.grcode = " + SQLString.Format(ss_grcode);
                }

                if (!ss_upperclass.equals("ALL") && ss_middleclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && !ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    sql += " and a.lowerclass = " + SQLString.Format(ss_lowerclass);
                }
                sql += " order by a.subjnm";
            } else if (v_gadmin.equals("K")) { //  회사관리자, 부서관리자
                sql = "select distinct a.subj, a.subjnm";
                sql += " from tz_subj a, tz_grsubj b,";
                sql += " (select e.grcode from tz_grcomp e, tz_compman f where f.userid = " + SQLString.Format(s_userid) + " and f.gadmin = " + SQLString.Format(s_gadmin);
                sql += " and substring(e.comp, 1, 4) = substring(f.comp, 1, 4)) c";
                sql += " where a.subj = b.subjcourse and b.grcode = c.grcode and a.isuse='Y' ";//and a.isapproval='Y'";
                if (!s_subjsearchkey.equals("")) {
                    sql += "  and a.subjnm like '%" + s_subjsearchkey + "%'";
                }
                if (justOn)
                    sql += " and a.isonoff = 'ON' ";
                if (justOff)
                    sql += " and a.isonoff = 'OFF' ";

                if (!ss_grcode.equals("ALL")) {
                    sql += " and b.grcode = " + SQLString.Format(ss_grcode);
                }

                if (!ss_upperclass.equals("ALL") && ss_middleclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && !ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    sql += " and a.lowerclass = " + SQLString.Format(ss_lowerclass);
                }
                sql += " order by a.subjnm";
            } else if (s_gadmin.equals("B1")) { //  문콘관리자
                sql = "select distinct a.subj, a.subjnm from tz_subj a";

                if (ss_grseq.equals("ALL")) { //      교육그룹만 선택된 경우
                    sql += " , tz_grsubj b where a.isuse='Y' ";// and a.isapproval='Y' ";
                    sql += " and b.grcode = 'N000001' and a.subj = b.subjcourse";
                } else if (!ss_grseq.equals("ALL")) { //      교육그룹과 교육차수가 선택된 경우
                    sql += " , tz_grsubj b, tz_subjseq c where a.isuse='Y' ";//and a.isapproval='Y' ";
                    sql += " and b.grcode = 'N000001' and a.subj = b.subjcourse";
                    sql += " and b.grcode = c.grcode ";
                    sql += " and c.grseq = " + SQLString.Format(ss_grseq) + " and a.subj = c.subj";
                    sql += " and c.course = '000000'";
                }

                if (!ss_upperclass.equals("ALL") && ss_middleclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && !ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    sql += " and a.lowerclass = " + SQLString.Format(ss_lowerclass);
                }

                if (!s_subjsearchkey.equals("")) {
                    sql += "  and a.subjnm like '%" + s_subjsearchkey + "%'";
                }
                if (justOn)
                    sql += " and a.isonoff = 'ON' ";
                if (justOff)
                    sql += " and a.isonoff = 'OFF' ";

                sql += " order by a.subjnm";

            } else if (s_gadmin.equals("B2")) { //  게임관리자
                sql = "select distinct a.subj, a.subjnm from tz_subj a";

                if (ss_grseq.equals("ALL")) { //      교육그룹만 선택된 경우
                    sql += " , tz_grsubj b where a.isuse='Y' ";// and a.isapproval='Y' ";
                    sql += " and b.grcode = 'N000002' and a.subj = b.subjcourse";
                } else if (!ss_grseq.equals("ALL")) { //      교육그룹과 교육차수가 선택된 경우
                    sql += " , tz_grsubj b, tz_subjseq c where a.isuse='Y' ";//and a.isapproval='Y' ";
                    sql += " and b.grcode = 'N000002' and a.subj = b.subjcourse";
                    sql += " and b.grcode = c.grcode ";
                    sql += " and c.grseq = " + SQLString.Format(ss_grseq) + " and a.subj = c.subj";
                    sql += " and c.course = '000000'";
                }

                if (!ss_upperclass.equals("ALL") && ss_middleclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && !ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    sql += " and a.lowerclass = " + SQLString.Format(ss_lowerclass);
                }

                if (!s_subjsearchkey.equals("")) {
                    sql += "  and a.subjnm like '%" + s_subjsearchkey + "%'";
                }
                if (justOn)
                    sql += " and a.isonoff = 'ON' ";
                if (justOff)
                    sql += " and a.isonoff = 'OFF' ";

                sql += " order by a.subjnm";
            }

            else { //  Ultravisor, Supervisor
                sql = "select distinct a.subj, a.subjnm from tz_subj a";

                if (!ss_grcode.equals("ALL") && ss_grseq.equals("ALL")) { //      교육그룹만 선택된 경우
                    sql += " , tz_grsubj b where a.isuse='Y' ";// and a.isapproval='Y' ";
                    sql += " and b.grcode = " + SQLString.Format(ss_grcode) + " and a.subj = b.subjcourse";
                } else if (!ss_grcode.equals("ALL") && !ss_grseq.equals("ALL")) { //      교육그룹과 교육차수가 선택된 경우
                    sql += " , tz_subjseq c where a.isuse='Y' ";//and a.isapproval='Y' ";
                    sql += " and c.grcode = " + SQLString.Format(ss_grcode);
                    sql += " and c.grseq = " + SQLString.Format(ss_grseq) + " and a.subj = c.subj";
                    sql += " and c.course = '000000'";
                } else {
                    sql += " where a.isuse='Y' ";//and a.isapproval='Y'";
                }
                /*
                 * if (!ss_grcode.equals("ALL") && ss_grseq.equals("ALL")) { //
                 * 교육그룹만 선택된 경우 sql += " , tz_grsubj b where a.isuse='Y' ";//
                 * and a.isapproval='Y' "; sql += " and b.grcode = " +
                 * SQLString.Format(ss_grcode) + " and a.subj = b.subjcourse"; }
                 * else if (!ss_grcode.equals("ALL") && !ss_grseq.equals("ALL"))
                 * { // 교육그룹과 교육차수가 선택된 경우 sql +=
                 * " , tz_grsubj b, tz_subjseq c where a.isuse='Y' ";//and
                 * a.isapproval='Y' "; sql += " and b.grcode = " +
                 * SQLString.Format(ss_grcode) + " and a.subj = b.subjcourse";
                 * sql += " and b.grcode = c.grcode "; sql += " and c.grseq = "
                 * + SQLString.Format(ss_grseq) + " and a.subj = c.subj"; sql +=
                 * " and c.course = '000000'"; } else { sql +=
                 * " where a.isuse='Y' ";//and a.isapproval='Y'"; }//
                 */

                if (!ss_upperclass.equals("ALL") && ss_middleclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && !ss_lowerclass.equals("ALL")) {
                    sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    sql += " and a.lowerclass = " + SQLString.Format(ss_lowerclass);
                }

                if (!s_subjsearchkey.equals("")) {
                    sql += "  and a.subjnm like '%" + s_subjsearchkey + "%'";
                }
                if (justOn)
                    sql += " and a.isonoff = 'ON' ";
                if (justOff)
                    sql += " and a.isonoff = 'OFF' ";

                if (conf.getBoolean("course.use")) { //     코스분류 있다
                    sql += " union";
                    sql += " select distinct a.course, a.coursenm from tz_course a, tz_grsubj b, tz_courseseq c where 1=1 and a.course = b.subjcourse and a.course = c.course";
                    if (!ss_upperclass.equals("ALL") && ss_middleclass.equals("ALL")) {
                        sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                    } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && ss_lowerclass.equals("ALL")) {
                        sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                        sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    } else if (!ss_upperclass.equals("ALL") && !ss_middleclass.equals("ALL") && !ss_lowerclass.equals("ALL")) {
                        sql += " and a.upperclass = " + SQLString.Format(ss_upperclass);
                        sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                        sql += " and a.middleclass = " + SQLString.Format(ss_middleclass);
                    }
                    if (!ss_grcode.equals("ALL") && ss_grseq.equals("ALL")) { //      교육그룹만 선택된 경우
                        sql += " and b.grcode = " + SQLString.Format(ss_grcode);
                    }
                    if (!ss_grcode.equals("ALL") && !ss_grseq.equals("ALL")) { //      교육그룹과 교육차수가 선택된 경우
                        sql += " and b.grcode = " + SQLString.Format(ss_grcode);
                        sql += " and c.grseq = " + SQLString.Format(ss_grseq);
                    }
                }

                sql += " order by subjnm";
            }
            System.out.println("\n\n@@@@@@@@@@@@@과정 sql =\n" + sql + "\n@@@@@@@@@@@@@@@@@");
            Log.info.println("과정 sql = " + sql);

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            if (v_gadmin.equals("A") || v_gadmin.equals("B") || v_gadmin.equals("H") || v_gadmin.equals("K") || v_gadmin.equals("U") || v_gadmin.equals("P")) { //      Ultra/Super or 교육그룹관리자 or 회사/부서/부서장인 경우 'ALL' 과정 출력
                result += getSelectTag(ls, isChange, isALL, "s_subjcourse", ss_subjcourse);
            } else {
                result += getSelectTag(ls, isChange, false, "s_subjcourse", ss_subjcourse);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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

    public static String getSubjseq(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "차수 ";

        try {
            connMgr = new DBConnectionManager();

            String ss_grcode = box.getStringDefault("s_grcode", "ALL"); // 교육그룹
            String ss_gyear = box.getStringDefault("s_gyear", getGyear(connMgr, ss_grcode)); //  교육년도
            String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //   교육차수

            String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //     과정
            String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //     과정차수

            sql = "select distinct";
            sql += "      case b.course When '000000' Then a.subjseq   Else b.courseseq End as subjseq, ";
            sql += "      case b.course When '000000' Then a.subjseqgr Else b.courseseq End as subjseqgr ";
            sql += " from tz_subjseq a, tz_courseseq b ";
            sql += " where case b.course When '000000' Then a.subj  Else b.course End = " + SQLString.Format(ss_subjcourse);
            sql += "   and case b.course When '000000' Then a.gyear Else b.gyear  End = " + SQLString.Format(ss_gyear);
            if (!ss_grcode.equals("ALL") && ss_grseq.equals("ALL") && !ss_subjcourse.equals("ALL")) { //      교육그룹만 선택된 경우
                sql += " and case b.course When '000000' Then a.grcode Else b.grcode End = " + SQLString.Format(ss_grcode);
            } else if (!ss_grcode.equals("ALL") && !ss_grseq.equals("ALL") && !ss_subjcourse.equals("ALL")) { //      교육그룹과 교육차수가 선택된 경우
                sql += " and case b.course When '000000' Then a.grcode Else b.grcode End = " + SQLString.Format(ss_grcode);
                sql += " and case b.course When '000000' Then a.grseq  Else b.grseq  End = " + SQLString.Format(ss_grseq);
            }

            sql += " order by case b.course When '000000' 	Then  a.subjseq Else b.courseseq End ";

            //            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            pstmt = connMgr.prepareStatement(sql);

            ls = new ListSet(pstmt);

            result += getSelectTag(ls, isChange, isALL, "s_subjseq", ss_subjseq);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
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

    public static String getGyear(DBConnectionManager connMgr, String ss_grcode) throws Exception {
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "";

        try {

            sql = " select distinct gyear ";
            sql += "   from tz_grseq       ";
            sql += "   where grcode = " + SQLString.Format(ss_grcode);
            sql += "  order by gyear desc  ";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            if (ls.next())
                result = ls.getString(1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
        }
        return result;
    }

    public static String getSubjClass(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "클래스 ";

        try {
            String ss_gyear = box.getString("s_gyear"); //  교육년도

            String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //     과정
            String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //     과정차수
            String ss_class = box.getStringDefault("s_class", "ALL"); //     과정차수

            connMgr = new DBConnectionManager();

            sql = " select class , classnm ";
            sql += " from tz_class   ";
            sql += " where   subj    = " + SQLString.Format(ss_subjcourse);
            sql += "     and year    =" + SQLString.Format(ss_gyear);
            sql += "     and subjseq =" + SQLString.Format(ss_subjseq);
            sql += " order by classnm  ";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //System.out.println(sql);
            ls = new ListSet(pstmt);

            result += getSelectTag(ls, isChange, isALL, "s_class", ss_class);
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
        return result;
    }

    public static String getSelectTag(ListSet ls, boolean isChange, boolean isALL, String selname, String optionselected) throws Exception {
        StringBuffer sb = null;
        //System.out.println("isChange" + isChange);System.out.println("isALL" + isALL);System.out.println("selname" + selname);System.out.println("optionselected" + optionselected);
        try {
            sb = new StringBuffer();

            sb.append("<select name = \"" + selname + "\"");
            if (isChange)
                sb.append(" onChange = \"javascript:whenSelection('change')\"");
            sb.append(" >\r\n");
            if (isALL) {
                sb.append("<option value = \"ALL\">ALL</option>\r\n");
            } else if (isChange) {
                if (selname.indexOf("year") == -1)
                    sb.append("<option value = \"----\">== 선택 ==</option>\r\n");
            }

            while (ls.next()) {
                ResultSetMetaData meta = ls.getMetaData();
                int columnCount = meta.getColumnCount();

                sb.append("<option value = \"" + ls.getString(1) + "\"");

                if (optionselected.equals(ls.getString(1)))
                    sb.append(" selected");

                if (selname.equals("s_subjseq"))
                    sb.append(">" + StringManager.cutZero(ls.getString(columnCount)) + "차</option>\r\n");
                else
                    sb.append(">" + ls.getString(columnCount) + "</option>\r\n");
            }
            sb.append("</select>\r\n");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return sb.toString();
    }

    //마스터과정과 하위 과정 매핑시 해당되는 차수 select
    public static String getMasterSeq(RequestBox box, String subj, String grcode, String gyear, String grseq, String mastercd) throws Exception {

        StringBuffer sb = null;
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ListSet ls = null;
        String sql = "";
        //        String  resString = "";
        String v_subjseq = "";
        String v_subjseqgr = "";
        int i = 0;

        try {
            sb = new StringBuffer();
            connMgr = new DBConnectionManager();

            sql = "select a.subjseq,a.subjseqgr from tz_subjseq a ";
            sql += "where ";
            sql += " a.subj = " + SQLString.Format(subj);
            sql += " and a.grcode = " + SQLString.Format(grcode);
            sql += " and a.gyear= " + SQLString.Format(gyear);
            sql += " and a.grseq= " + SQLString.Format(grseq);
            sql += " and a.subjseq not in( ";
            sql += " select subjseq from tz_mastersubj ";
            sql += " where ";
            sql += " 1=1 ";
            //sql += " and mastercd="+SQLString.Format(mastercd);
            sql += " and a.year  = year ";
            sql += " and subjcourse = " + SQLString.Format(subj);
            sql += " )";
            sql += " order by subjseqgr";
            System.out.println("=================1223" + sql);

            pstmt = connMgr.prepareStatement(sql);
            rs = pstmt.executeQuery();

            i = 0;

            while (rs.next()) {
                v_subjseq = rs.getString("subjseq");
                v_subjseqgr = rs.getString("subjseqgr");
                sb.append("document.form2.p_subjectseq.options[" + i + "] = new Option(\"" + Integer.parseInt(v_subjseqgr) + "차\",\"" + v_subjseq + "\");");
                i++;
            }
        }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (rs != null) {
                try {
                    rs.close();
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
        return sb.toString();

    }

    public static boolean getIsVisible(boolean isStatisticalPage, int classNum) throws Exception {
        boolean result = false;
        int classview = 0;
        int classdepth = 0;

        try {
            ConfigSet conf = new ConfigSet();

            classview = conf.getInt("subj.class.view");
            classdepth = conf.getInt("subj.class.depth");

            if (isStatisticalPage) { //      통계페이지
                if (classNum == 1) { //      UpperClass
                    if (classview == 0)
                        result = false;
                    else if (classview == 1)
                        result = true;
                    else if (classview == 2)
                        result = true;
                    else if (classview == 3)
                        result = true;
                } else if (classNum == 2) { //      MiddleClass
                    if (classview == 0)
                        result = false;
                    else if (classview == 1)
                        result = false;
                    else if (classview == 2)
                        result = true;
                    else if (classview == 3)
                        result = true;
                } else if (classNum == 3) { //      LowerClass
                    if (classview == 0)
                        result = false;
                    else if (classview == 1)
                        result = false;
                    else if (classview == 2)
                        result = false;
                    else if (classview == 3)
                        result = true;
                }
            } else {
                if (classNum == 1) { //      UpperClass
                    if (classdepth > 0)
                        result = true;
                } else if (classNum == 2) { //      MiddleClass
                    if (classdepth > 1)
                        result = true;
                } else if (classNum == 3) { //      LowerClass
                    if (classdepth > 2)
                        result = true;
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return result;
    }

}
