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

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * 자료실(HomePage) 관련 Sample Class
 * 
 * @date : 2003. 5
 * @author : j.h. lee
 */
public class SelectEduBean {

    /**
     * 자료실 list화면 select
     */
    public ArrayList<DataBox> getGrcode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            if (v_gadmin.equals("F") || v_gadmin.equals("P")) { //  과정관리자, 강사
                sql = "select distinct a.grcode, a.grcodenm";
                sql += " from tz_grcode a, tz_grsubj b,";
                sql += " (select subj from tz_subjman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin) + ") c";
                sql += " where a.grcode = b.grcode and b.subjcourse = c.subj";
                sql += " and a.useyn='Y'";
                sql += " order by a.grcodenm";
            } else if (v_gadmin.equals("H")) { //  교육그룹관리자
                sql = "select distinct a.grcode, a.grcodenm";
                sql += " from tz_grcode a, tz_grcodeman b";
                sql += " where a.grcode = b.grcode and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " + SQLString.Format(s_gadmin);
                sql += " and a.useyn='Y'";
                sql += " order by a.grcodenm";
            } else if (v_gadmin.equals("K")) { //  회사관리자, 부서관리자
                sql = "select distinct a.grcode, a.grcodenm";
                sql += " from tz_grcode a, tz_grcomp b,";
                sql += " (select comp from tz_compman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin) + ") c";
                // 수정일 : 05.11.07 수정자 : 이나연 _ substr 수정
                //              sql += " where a.grcode = b.grcode and substr(b.comp, 1, 4) = substr(c.comp, 1, 4)";
                sql += " where a.grcode = b.grcode and substring(b.comp, 1, 4) = substring(c.comp, 1, 4)";
                sql += " and a.useyn='Y'";
                sql += " order by a.grcodenm";
            } else { //  Ultravisor, Supervisor
                sql = "select grcode, grcodenm";
                sql += " from tz_grcode";
                sql += " where useyn='Y'";
                sql += " order by grcodenm";
            }

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            if (v_gadmin.equals("A")) { //      Ultra/Super 인 경우 'ALL' 교육그룹 출력
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

    public ArrayList<DataBox> getGyear(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            // String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select distinct gyear ";
            sql += "   from tz_grseq       ";
            sql += "  order by gyear desc  ";

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

    /**
     * 교육차수
     * 
     * @param box receive from the form object
     * @return ArrayList
     */
    public ArrayList<DataBox> getGrseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
            String ss_gyear = box.getString("s_gyear"); //년도

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select grseq, grseqnm";
            sql += " from tz_grseq";
            sql += " where gyear = " + SQLString.Format(ss_gyear);
            sql += " and grcode = " + SQLString.Format(ss_grcode);
            sql += " order by grseq";

            ls = connMgr.executeQuery(sql);

            dbox = this.setAllSelectBox(ls);//System.out.println("dbox.size() : " + dbox.size());
            list.add(dbox);//System.out.println("list.size() : " + list.size());

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

    //===========================================================================================================================
    public static String getGrcode(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "교육그룹 ";

        try {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");
            String ss_grcode = box.getStringDefault("s_grcode", "N000001"); //교육그룹

            connMgr = new DBConnectionManager();

            if (v_gadmin.equals("F") || v_gadmin.equals("P")) { //  과정관리자, 강사
                sql = "select distinct a.grcode, a.grcodenm";
                sql += " from tz_grcode a, tz_grsubj b,";
                sql += " (select subj from tz_subjman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin) + ") c";
                sql += " where a.grcode = b.grcode and b.subjcourse = c.subj";
                sql += " order by a.grcodenm";
            } else if (v_gadmin.equals("H")) { //  교육그룹관리자
                sql = "select distinct a.grcode, a.grcodenm";
                sql += " from tz_grcode a, tz_grcodeman b";
                sql += " where a.grcode = b.grcode and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " + SQLString.Format(s_gadmin);
                sql += " order by a.grcodenm";
            } else if (v_gadmin.equals("K")) { //  회사관리자, 부서관리자
                sql = "select distinct a.grcode, a.grcodenm";
                sql += " from tz_grcode a, tz_grcomp b,";
                sql += " (select comp from tz_compman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin) + ") c";
                // 수정일 : 05.11.07 수정자 : 이나연 _ substr 수정
                //              sql += " where a.grcode = b.grcode and substr(b.comp, 1, 4) = substr(c.comp, 1, 4)";
                sql += " where a.grcode = b.grcode and substring(b.comp, 1, 4) = substring(c.comp, 1, 4)";
                sql += " order by a.grcodenm";
            } else if (v_gadmin.equals("B")) { //  기간관리자
                sql = "select distinct grcode, grcodenm";
                sql += " from tz_grcode ";
                sql += " where grtype =  " + SQLString.Format(box.getSession("grtype"));
                sql += " order by grcodenm";
            } else { //  Ultravisor, Supervisor
                sql = "select grcode, grcodenm";
                sql += " from tz_grcode";
                sql += " order by grcodenm";
            }

            //            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //            ls = new ListSet(pstmt);
            ls = connMgr.executeQuery(sql);

            result += getSelectTag(ls, isChange, isALL, "s_grcode", ss_grcode);
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

    public static String getGrcode2(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "교육그룹 ";

        try {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");
            String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹

            connMgr = new DBConnectionManager();

            if (v_gadmin.equals("F") || v_gadmin.equals("P")) { //  과정관리자, 강사
                sql = "select distinct a.grtype, a.grcodenm";
                sql += " from tz_grcode a, tz_grsubj b,";
                sql += " (select subj from tz_subjman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin) + ") c";
                sql += " where a.grcode = b.grcode and b.subjcourse = c.subj";
                sql += " order by a.grcodenm";
            } else if (v_gadmin.equals("H")) { //  교육그룹관리자
                sql = "select distinct a.grtype, a.grcodenm";
                sql += " from tz_grcode a, tz_grcodeman b";
                sql += " where a.grcode = b.grcode and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " + SQLString.Format(s_gadmin);
                sql += " order by a.grcodenm";
            } else if (v_gadmin.equals("K")) { //  회사관리자, 부서관리자
                sql = "select distinct a.grtype, a.grcodenm";
                sql += " from tz_grcode a, tz_grcomp b,";
                sql += " (select comp from tz_compman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin) + ") c";
                // 수정일 : 05.11.07 수정자 : 이나연 _ substr 수정
                //              sql += " where a.grcode = b.grcode and substr(b.comp, 1, 4) = substr(c.comp, 1, 4)";
                sql += " where a.grcode = b.grcode and substring(b.comp, 1, 4) = substring(c.comp, 1, 4)";
                sql += " order by a.grcodenm";
            } else if (v_gadmin.equals("B")) { //  기간관리자
                sql = "select distinct grtype, grcodenm";
                sql += " from tz_grcode ";
                sql += " where grtype =  " + SQLString.Format(box.getSession("grtype"));
                sql += " order by grcodenm";
            } else { //  Ultravisor, Supervisor
                sql = "select grtype, grcodenm";
                sql += " from tz_grcode";
                sql += " order by grcodenm";
            }

            //            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //            ls = new ListSet(pstmt);
            ls = connMgr.executeQuery(sql);

            result += getSelectTag(ls, isChange, isALL, "s_grcode", ss_grcode);
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

    /**
     * 교육년도
     * 
     * @param box receive from the form object
     * @param isChange
     * @return String
     */
    public static String getGyear(RequestBox box, boolean isChange) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "연도 ";

        try {
            String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
            String ss_gyear = box.getString("s_gyear"); //교육년도

            connMgr = new DBConnectionManager();

            sql = " select distinct gyear ";
            sql += "   from tz_grseq       ";
            sql += "   where grcode = " + SQLString.Format(ss_grcode);
            sql += "  order by gyear desc  ";

            //pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //ls = new ListSet(pstmt);

            ls = connMgr.executeQuery(sql);

            result += getSelectTag(ls, isChange, false, "s_gyear", ss_gyear);

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

    public static String getGrseq(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "교육차수 ";
        System.out.println("############## Select Tag 방식으로 전환 [code.list.course.subjseq] ##############");
        try {
            String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
            String ss_gyear = box.getString("s_gyear"); //교육년도
            String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수

            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");

            connMgr = new DBConnectionManager();
            if (v_gadmin.equals("F") || v_gadmin.equals("P")) { //  과정관리자, 강사
                sql = "select distinct f.grseq, f.grseqnm ";
                sql += "from tz_grcode a, tz_grsubj b, ";
                sql += "(select subj from tz_subjman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin) + ") c, tz_subj d, tz_subjseq e, tz_grseq f ";
                sql += "where a.grcode = b.grcode ";
                sql += "and b.subjcourse = c.subj ";
                sql += "and b.subjcourse = d.subj ";
                sql += "and b.subjcourse = e.subj ";
                sql += "and a.grcode = e.grcode ";
                sql += "and e.grcode = f.grcode ";
                sql += "and e.gyear = f.gyear ";
                sql += "and e.grseq = f.grseq ";
                if (!ss_gyear.equals("")) {
                    sql += "and e.gyear = " + SQLString.Format(ss_gyear) + " ";
                } else {
                    sql += "and e.gyear = (select max(gyear) from tz_grseq where grcode = " + SQLString.Format(ss_grcode) + ")";
                }

                sql += "and a.grcode = " + SQLString.Format(ss_grcode) + " ";
                sql += "order by f.grseq";
            } else {
                sql = "select grseq, grseqnm";
                sql += " from tz_grseq";

                if (!ss_gyear.equals("")) {
                    sql += " where gyear = " + SQLString.Format(ss_gyear);
                } else {
                    sql += " where gyear = (select max(gyear) from tz_grseq where grcode = " + SQLString.Format(ss_grcode) + ")";
                }

                sql += " and grcode = " + SQLString.Format(ss_grcode);
                sql += " order by grseq";
            }

            //            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //            ls = new ListSet(pstmt);

            ls = connMgr.executeQuery(sql);

            result += getSelectTag(ls, isChange, isALL, "s_grseq", ss_grseq);
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

    public static String getDamunGyear(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "연도 ";

        try {
            String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
            String ss_gyear = box.getString("s_gyear"); //교육년도

            connMgr = new DBConnectionManager();

            sql = " select distinct gyear ";
            sql += "   from tz_grseq       ";
            sql += "   where grcode = " + SQLString.Format(ss_grcode);
            sql += "  order by gyear desc  ";

            //            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
            //            ls = new ListSet(pstmt);
            ls = connMgr.executeQuery(sql);

            result += getSelectTag(ls, isChange, false, "s_damungyyyy", ss_gyear);
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

    public static String getSelectTag(ListSet ls, boolean isChange, boolean isALL, String selname, String optionselected) throws Exception {
        StringBuffer sb = null;
        boolean isSelected = false;
        String v_tmpselname = "";

        try {
            sb = new StringBuffer();

            if (selname.equals("s_damungyyyy")) {
                v_tmpselname = "s_gyear";
                sb.append("<select name = \"" + v_tmpselname + "\"");
            } else {
                sb.append("<select name = \"" + selname + "\"");
            }
            if (isChange)
                sb.append(" onChange = \"javascript:whenSelection('change')\"");
            sb.append(" >\r\n");
            if (isALL) {
                sb.append("<option value = \"ALL\">선택</option>\r\n");
            } else if (isChange) {
                if (selname.indexOf("year") == -1)
                    sb.append("<option value = \"----\">== 선택 ==</option>\r\n");
            }

            while (ls.next()) {
                ResultSetMetaData meta = ls.getMetaData();
                int columnCount = meta.getColumnCount();

                sb.append("<option value = \"" + ls.getString(1) + "\"");
                //System.out.println(optionselected);System.out.println(isSelected);System.out.println(ls.getString(1));
                if (optionselected.equals(ls.getString(1)) && !isSelected) {
                    sb.append(" selected");
                    isSelected = true;
                } else if (selname.equals("s_gyear") && ls.getString("gyear").equals(FormatDate.getDate("yyyy")) && optionselected.equals("") && !isSelected) { //      현대자동차에서만 사용됨
                    sb.append(" selected");
                    isSelected = true;
                }

                sb.append(">" + ls.getString(columnCount) + "</option>\r\n");
            }
            sb.append("</select>\r\n");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return sb.toString();
    }

    /**
     * 교육그룹 select box
     * 
     * @param selectname select box name
     * @param selected selected valiable
     * @param allcheck all check Y(1),all check N(0)
     * @return int
     */
    public static String getGrcodeSelect(String selectname, String selected, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        // Connection conn = null;
        ListSet ls = null;
        String result = null;
        String sql = "";

        result = "  <SELECT name=" + selectname + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>=== 전체 ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = "select grcode, grcodenm from tz_grcode  ";
            sql += " order by grcodenm";

            ls = connMgr.executeQuery(sql);
            //            System.out.println("selected===>"+selected);
            while (ls.next()) {
                result += " <option value=" + ls.getString("grcode");
                if (selected.equals(ls.getString("grcode"))) {
                    result += " selected ";
                }

                result += ">" + ls.getString("grcodenm") + "</option> \n";
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

        result += "  </SELECT> \n";
        return result;
    }
}
