// **********************************************************
// 1. �� ��: Sample �ڷ��
// 2. ���α׷���: BulletinBeanjava
// 3. �� ��: Sample �ڷ��
// 4. ȯ ��: JDK 1.3
// 5. �� ��: 1.0
// 6. �� ��: ������ 2003. 4. 26
// 7. �� ��: ������ 2003. 4. 26
// **********************************************************

package com.credu.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

/**
 * �ڷ��(HomePage) ���� Sample Class
 * 
 * @date : 2003. 5
 * @author : j.h. lee
 */
public class SelectCompanyBean {

    /**
     * �ڷ�� listȭ�� select
     */

    public ArrayList<DataBox> getCompany(RequestBox box) throws Exception {
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

            if (v_gadmin.equals("H")) { // �����׷������
                sql = "select distinct a.comp, a.companynm";
                sql += " from tz_comp a, tz_grcomp b,";
                sql += " (select grcode from tz_grcodeman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin)
                        + ") c";
                sql += " where a.comp = b.comp and b.grcode = c.grcode and a.comptype = '2'";
                sql += " order by a.companynm";
            } else if (v_gadmin.equals("K")) { // ȸ�������, �μ�������
                sql = "select distinct a.comp, a.companynm";
                sql += " from tz_comp a, tz_compman b";
                // ������ : 05.11.07 ������ : �̳��� _ substr ����
                // sql += " where substr(a.comp, 1, 4) = substr(b.comp, 1, 4) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " +
                // SQLString.Format(s_gadmin);
                sql += " where substring(a.comp, 1, 4) = substring(b.comp, 1, 4) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = "
                        + SQLString.Format(s_gadmin);
                sql += "  and a.comptype = '2' order by a.comp";
                sql += " order by a.companynm";
            } else { // Ultravisor, Supervisor, ����������, ����
                sql = "select distinct comp, companynm";
                sql += " from tz_comp where comptype = '2'";
                sql += " order by companynm";
            }// System.out.println(sql);

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            if (v_gadmin.equals("A") || v_gadmin.equals("F") || v_gadmin.equals("P") || v_gadmin.equals("H")) {
                // Ultra/Super or ���������� or ���� or �����׷������ �� ��� 'ALL' ȸ�� ���
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

    public ArrayList<DataBox> getAesCompany(RequestBox box) throws Exception {
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

            if (v_gadmin.equals("H")) { // �����׷������
                sql = "select distinct a.aes_code, a.companynm";
                sql += " from tz_comp a, tz_grcomp b,";
                sql += " (select grcode from tz_grcodeman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin)
                        + ") c";
                sql += " where a.comp = b.comp and b.grcode = c.grcode and a.comptype = '2'";
                sql += " order by a.companynm";
            } else if (v_gadmin.equals("K")) { // ȸ�������, �μ�������
                sql = "select distinct a.aes_code, a.companynm";
                sql += " from tz_comp a, tz_compman b";
                // ������ : 05.11.07 ������ : �̳��� _ substr ����
                // sql += " where substr(a.comp, 1, 4) = substr(b.comp, 1, 4) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " +
                // SQLString.Format(s_gadmin);
                sql += " where substring(a.comp, 1, 4) = substring(b.comp, 1, 4) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = "
                        + SQLString.Format(s_gadmin);
                sql += "  and a.comptype = '2' order by a.comp";
                sql += " order by a.companynm";
            } else { // Ultravisor, Supervisor, ����������, ����
                sql = "select distinct aes_code, companynm";
                sql += " from tz_comp where comptype = '2'";
                sql += " order by companynm";
            }// System.out.println(sql);

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            if (v_gadmin.equals("A") || v_gadmin.equals("F") || v_gadmin.equals("P") || v_gadmin.equals("H")) {
                // Ultra/Super or ���������� or ���� or �����׷������ �� ��� 'ALL' ȸ�� ���
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

    public ArrayList<DataBox> getGpm(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String s_gadmin = box.getSession("gadmin");
            // String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");

            String ss_company = box.getStringDefault("s_company", "ALL"); // �ش� ȸ���� comp code

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            if (s_gadmin.equals("K6") || s_gadmin.equals("K7")) { // �μ������� or �μ���
                sql = "select distinct a.comp, a.gpmnm";
                sql += " from tz_comp a, tz_compman b";
                // ������ : 05.11.07 ������ : �̳��� _ substr ����
                // sql += " where substr(a.comp, 1, 6) = substr(b.comp, 1, 6) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " +
                // SQLString.Format(s_gadmin);
                sql += " where substring(a.comp, 1, 6) = substring(b.comp, 1, 6) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = "
                        + SQLString.Format(s_gadmin);

                if (!ss_company.equals("ALL")) {
                    sql += " and a.comp like '" + GetCodenm.get_compval(ss_company) + "'";
                }
                sql += " and a.comptype = '3' order by a.gpmnm";
            } else {
                sql = "select distinct comp, gpmnm";
                sql += " from tz_comp where comptype = '3'";

                if (!ss_company.equals("ALL")) {
                    sql += " and comp like '" + GetCodenm.get_compval(ss_company) + "'";
                }
                sql += " order by gpmnm";
            }
            System.out.println(sql);
            ls = connMgr.executeQuery(sql);

            if (!s_gadmin.equals("K6") && !s_gadmin.equals("K7")) { // �μ������� or �μ��� �� �ƴѰ�� 'ALL' ������� ���
                dbox = this.setAllSelectBox(ls);
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

    public ArrayList<DataBox> getDept(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String s_gadmin = box.getSession("gadmin");
            // String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");

            String ss_seltext = box.getStringDefault("s_seltext", "ALL"); // �ش� ��������� comp code

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            if (s_gadmin.equals("K6") || s_gadmin.equals("K7")) { // �μ������� or �μ���
                sql = "select distinct a.comp, a.deptnm";
                sql += " from tz_comp a, tz_compman b";
                // ������ : 05.11.07 ������ : �̳��� _ substr ����
                // sql += " where substr(a.comp, 1, 8) = substr(b.comp, 1, 8) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " +
                // SQLString.Format(s_gadmin);
                sql += " where substring(a.comp, 1, 8) = substring(b.comp, 1, 8) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = "
                        + SQLString.Format(s_gadmin);

                if (!ss_seltext.equals("ALL")) {
                    sql += " and a.comp like '" + GetCodenm.get_compval(ss_seltext) + "'";
                }
                sql += " and a.comptype = '4'  order by a.deptnm";
            } else {
                sql = "select distinct comp, deptnm";
                sql += " from tz_comp where comptype = '4'";

                if (!ss_seltext.equals("ALL")) {
                    sql += " and comp like '" + GetCodenm.get_compval(ss_seltext) + "'";
                }
                sql += " order by deptnm";
            }
            System.out.println(sql);
            ls = connMgr.executeQuery(sql);

            if (!s_gadmin.equals("K6") && !s_gadmin.equals("K7")) { // �μ������� or �μ��� �� �ƴѰ�� 'ALL' ������� ���
                dbox = this.setAllSelectBox(ls);
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

    public ArrayList<DataBox> getJikwi(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select distinct jikwi, jikwinm";
            sql += " from tz_jikwi";
            sql += " order by jikwinm";

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

    public ArrayList<DataBox> getJikup(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select distinct jikup, jikupnm";
            sql += " from tz_jikup";
            sql += " order by jikupnm";

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

    public ArrayList<DataBox> getJikun(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select distinct jikun, jikunnm";
            sql += " from tz_jikun";
            sql += " order by jikunnm";

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
            columnCount = meta.getColumnCount();// System.out.println("columnCount : " + columnCount);
            for (int i = 1; i <= columnCount; i++) {
                String columnName = meta.getColumnName(i).toLowerCase();// System.out.println("columnName : " + columnName);

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
            columnCount = meta.getColumnCount();// System.out.println("columnCount : " + columnCount);
            for (int i = 1; i <= columnCount; i++) {
                String columnName = meta.getColumnName(i).toLowerCase();// System.out.println("columnName : " + columnName);

                dbox.put("d_" + columnName, "----");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("SelectBean.setAllDataBox()\r\n\"" + ex.getMessage());
        }
        return dbox;
    }

    /**
     * �����׷캰ȸ��
     */
    public ArrayList<DataBox> getGrcomp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            // String s_gadmin = box.getSession("gadmin");
            // String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            // String s_userid = box.getSession("userid");
            String ss_grcode = box.getString("s_grcode");

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql += " select                                       \n";
            sql += "   b.comp,                                      \n";
            sql += "   b.companynm                                  \n";
            sql += " from                                         \n";
            sql += "   tz_grcomp a, tz_comp b                     \n";
            sql += " where                                        \n";
            sql += "   grcode = " + SQLString.Format(ss_grcode);
            sql += "   and a.comp = b.comp                        \n";

            System.out.println("sql=" + sql);

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            // if(v_gadmin.equals("A") || v_gadmin.equals("F") || v_gadmin.equals("P") || v_gadmin.equals("H")) {
            // Ultra/Super or ���������� or ���� or �����׷������ �� ��� 'ALL' ȸ�� ���

            if (!ss_grcode.equals("ALL")) {
                dbox = this.setAllSelectBox(ls);
                list.add(dbox);
            }

            // }
            // else {
            // dbox = this.setSpaceSelectBox(ls);
            // list.add(dbox);
            // }

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

    // ==============================================================================================================================
    public static String getCompany(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "ȸ�� ";

        try {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");
            String ss_company = box.getStringDefault("s_company", "ALL");

            connMgr = new DBConnectionManager();

            if (v_gadmin.equals("H")) { // �����׷������
                sql = "select distinct a.comp, a.companynm";
                sql += " from tz_comp a, tz_grcomp b,";
                sql += " (select grcode from tz_grcodeman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin)
                        + ") c";
                sql += " where a.comp = b.comp and b.grcode = c.grcode and a.comptype = '2'";
                sql += " order by a.companynm";
            } else if (v_gadmin.equals("K")) { // ȸ�������, �μ�������
                sql = "select distinct a.comp, a.companynm";
                sql += " from tz_comp a, tz_compman b";
                // ������ : 05.11.07 ������ : �̳��� _ substr ����
                // sql += " where substr(a.comp, 1, 4) = substr(b.comp, 1, 4) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " +
                // SQLString.Format(s_gadmin);
                sql += " where substring(a.comp, 1, 4) = substring(b.comp, 1, 4) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = "
                        + SQLString.Format(s_gadmin);
                sql += "  and a.comptype = '2' order by a.companynm";
            } else { // Ultravisor, Supervisor, ����������, ����
                sql = "select distinct comp, companynm";
                sql += " from tz_comp where comptype = '2'";
                sql += " order by companynm";
            }

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            if (v_gadmin.equals("A") || v_gadmin.equals("F") || v_gadmin.equals("P") || v_gadmin.equals("H")) {
                // Ultra/Super or ���������� or ���� or �����׷������ �� ��� 'ALL' ȸ�� ���
                result += getSelectTag(ls, isChange, isALL, "s_company", ss_company);
            } else {
                result += getSelectTag(ls, isChange, false, "s_company", ss_company);
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

    public static String getAesCompany(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "ȸ�� ";

        try {
            String s_gadmin = box.getSession("gadmin");
            String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            String s_userid = box.getSession("userid");
            String ss_company = box.getStringDefault("s_aes_code", "ALL");

            connMgr = new DBConnectionManager();

            if (v_gadmin.equals("H")) { // �����׷������
                sql = "select distinct a.aes_code, a.companynm";
                sql += " from tz_comp a, tz_grcomp b,";
                sql += " (select grcode from tz_grcodeman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin)
                        + ") c";
                sql += " where a.comp = b.comp and b.grcode = c.grcode and a.comptype = '2'";
                sql += " order by a.companynm";
            } else if (v_gadmin.equals("K")) { // ȸ�������, �μ�������
                sql = "select distinct a.aes_code, a.companynm";
                sql += " from tz_comp a, tz_compman b";
                // ������ : 05.11.07 ������ : �̳��� _ substr ����
                // sql += " where substr(a.comp, 1, 4) = substr(b.comp, 1, 4) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " +
                // SQLString.Format(s_gadmin);
                sql += " where substring(a.comp, 1, 4) = substring(b.comp, 1, 4) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = "
                        + SQLString.Format(s_gadmin);
                sql += "  and a.comptype = '2' order by a.companynm";
            } else { // Ultravisor, Supervisor, ����������, ����
                sql = "select distinct aes_code, companynm";
                sql += " from tz_comp where comptype = '2'";
                sql += " order by companynm";
            }

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            if (v_gadmin.equals("A") || v_gadmin.equals("F") || v_gadmin.equals("P") || v_gadmin.equals("H")) {
                // Ultra/Super or ���������� or ���� or �����׷������ �� ��� 'ALL' ȸ�� ���
                result += getSelectTag(ls, isChange, isALL, "s_aes_code", ss_company);
            } else {
                result += getSelectTag(ls, isChange, false, "s_aes_code", ss_company);
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

    public static String getSelgubun(RequestBox box) throws Exception {
        StringBuffer sb = new StringBuffer();

        try {
            String ss_company = box.getStringDefault("s_company", "ALL");

            if (!ss_company.equals("ALL") && !ss_company.equals("") && !ss_company.equals("----")) {
                ConfigSet conf = new ConfigSet();
                boolean isGpm = conf.getBoolean("selgubun.gpm");
                boolean isJikwi = conf.getBoolean("selgubun.jikwi");
                boolean isJikup = conf.getBoolean("selgubun.jikup");
                boolean isJikun = conf.getBoolean("selgubun.jikun");

                String s_gadmin = box.getSession("gadmin");
                String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
                // String s_userid = box.getSession("userid");
                String ss_selgubun = box.getStringDefault("s_selgubun", "ALL");

                sb.append("<select name = \"s_selgubun\"");
                sb.append(" onChange = \"javascript:whenSelection('change')\"");
                sb.append(">\r\n");

                sb.append("<option value = \"----\"");
                if (ss_selgubun.equals("----"))
                    sb.append(" selected");
                sb.append("> ---- </option>\r\n");

                if (isGpm) {
                    sb.append("<option value = \"GPM\"");
                    if (ss_selgubun.equals("GPM"))
                        sb.append(" selected");
                    sb.append("> ������κ� </option>\r\n");
                }
                if (isJikwi && !v_gadmin.equals("K6") && !v_gadmin.equals("K7")) {
                    sb.append("<option value = \"JIKWI\"");
                    if (ss_selgubun.equals("JIKWI"))
                        sb.append(" selected");
                    sb.append("> ������ </option>\r\n");
                }
                if (isJikup && !v_gadmin.equals("K6") && !v_gadmin.equals("K7")) {
                    sb.append("<option value = \"JIKUP\"");
                    if (ss_selgubun.equals("JIKUP"))
                        sb.append(" selected");
                    sb.append("> ���޺� </option>\r\n");
                }
                if (isJikun && !v_gadmin.equals("K6") && !v_gadmin.equals("K7")) {
                    sb.append("<option value = \"JIKUN\"");
                    if (ss_selgubun.equals("JIKUN"))
                        sb.append(" selected");
                    sb.append("> ������ </option>\r\n");
                }
                sb.append("</select>\r\n");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return sb.toString();
    }

    public static String getSeltext(RequestBox box) throws Exception {
        String result = "";

        try {
            // String s_gadmin = box.getSession("gadmin");
            // String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            // String s_userid = box.getSession("userid");

            String ss_company = box.getStringDefault("s_company", "ALL"); // �ش� ȸ���� comp code
            String ss_selgubun = box.getStringDefault("s_selgubun", "ALL"); // �ش� ȸ���� comp code

            if (!ss_company.equals("ALL") && !ss_company.equals("") && !ss_company.equals("----")) {

                if (ss_selgubun.equals("GPM") || ss_selgubun.equals("")) {
                    result = getGpm(box, true, false);
                } else if (ss_selgubun.equals("JIKWI")) {
                    result = getJikwi(box, true, false);
                } else if (ss_selgubun.equals("JIKUP")) {
                    result = getJikup(box, true, false);
                } else if (ss_selgubun.equals("JIKUN")) {
                    result = getJikun(box, true, false);
                }

                if (ss_selgubun.equals("GPM") || ss_selgubun.equals("")) {
                    result += getDept(box, true, false);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return result;
    }

    public static String getGpm(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "����/����� ";

        try {
            String s_gadmin = box.getSession("gadmin");
            // String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            // String s_userid = box.getSession("userid");

            String ss_company = box.getStringDefault("s_company", "ALL"); // �ش� ȸ���� comp code
            String ss_seltext = box.getStringDefault("s_seltext", "ALL"); // �ش� ȸ���� comp code

            connMgr = new DBConnectionManager();

            // if(s_gadmin.equals("K6") || s_gadmin.equals("K7")) { // �μ������� or �μ���
            // sql = "select distinct a.comp, a.gpmnm";
            // sql += " from tz_comp a, tz_compman b";
            // sql += " where substr(a.comp, 1, 6) = substr(b.comp, 1, 6) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " +
            // SQLString.Format(s_gadmin);
            //    
            // if( !ss_company.equals("ALL")) {
            // sql += " and a.comp like '" + GetCodenm.get_compval(ss_company) + "'";
            // }
            // sql += " and a.comptype = '3' order by a.comp";
            // }
            // else {
            //    
            // //if( !ss_company.equals("ALL")) {
            // // sql += " and comp like '" + GetCodenm.get_compval(ss_company) + "'";
            // //}
            //    
            // ss_company=""�ΰ�� ����Ÿ�� �������� ����
            if (ss_company.equals("ALL")) {
                // sql += " and substr(comp,1,4) = '" + StringManager.substring(box.getSession("comp"), 0, 4) + "'";
                sql = "select distinct comp, deptnm";
                sql += " from tz_comp where comptype = '6'";
            } else {
                sql = "select distinct comp, gpmnm";
                sql += " from tz_comp where comptype = '3'";

                // ������ : 05.11.07 ������ : �̳��� _substr ����
                // sql += " and substr(comp,1,4) = '" + StringManager.substring(ss_company, 0, 4) + "'";
                sql += " and substring(comp,1,4) = '" + StringManager.substring(ss_company, 0, 4) + "'";
                sql += " order by gpmnm";
            }

            // }
            System.out.println("sql=" + sql);
            ls = connMgr.executeQuery(sql);

            // if( !s_gadmin.equals("K6") && !s_gadmin.equals("K7")) { // �μ������� or �μ��� �� �ƴѰ�� 'ALL' ������� ���
            // result = getSelectTag(ls, isChange, true, "s_seltext", ss_seltext);
            // }

            if (!s_gadmin.equals("K6") && !s_gadmin.equals("K7")) { // �μ������� or �μ��� �� �ƴѰ�� 'ALL' ������� ���
                // if(!ss_company.equals("ALL")) {
                result += getSelectTag(ls, isChange, true, "s_seltext", ss_seltext);
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

    public static String getDept(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "�μ� ";

        try {
            // String s_gadmin = box.getSession("gadmin");
            // String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            // String s_userid = box.getSession("userid");

            String ss_seltext = box.getStringDefault("s_seltext", "ALL");
            String ss_seldept = box.getStringDefault("s_seldept", "ALL");

            connMgr = new DBConnectionManager();

            // if(s_gadmin.equals("K6") || s_gadmin.equals("K7")) { // �μ������� or �μ���
            // sql = "select distinct a.comp, a.deptnm";
            // sql += " from tz_comp a, tz_compman b";
            // sql += " where substr(a.comp, 1, 8) = substr(b.comp, 1, 8) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " +
            // SQLString.Format(s_gadmin);
            //    
            // if( !ss_seltext.equals("ALL")) {
            // sql += " and a.comp like '" + GetCodenm.get_compval(ss_seltext) + "'";
            // }
            // sql += " and a.comptype = '4'  order by a.comp";
            // }
            // else {

            // if( !ss_seltext.equals("ALL")) {
            // sql += " and comp like '" + GetCodenm.get_compval(ss_seltext) + "'";
            // }

            // ss_dept=""�ΰ�� �ڱ� �Ҽ�����
            if (ss_seltext.equals("ALL")) {
                // sql += " and substr(comp,1,6) = '" + StringManager.substring(box.getSession("comp"), 0, 6) + "'";
                sql = "select distinct comp, deptnm";
                sql += " from tz_comp where comptype = '6'";
            } else {
                sql = "select distinct comp, deptnm";
                sql += " from tz_comp where comptype = '4'";

                // ������ : 05.11.07 ������ : �̳��� _substr ����
                // sql += " and substr(comp,1,6) = '" + StringManager.substring(ss_seltext, 0, 6) + "'";
                sql += " and substring(comp,1,6) = '" + StringManager.substring(ss_seltext, 0, 6) + "'";
                sql += " order by deptnm";
            }

            // }
            ls = connMgr.executeQuery(sql);

            // if( !s_gadmin.equals("K6") && !s_gadmin.equals("K7")) { // �μ������� or �μ��� �� �ƴѰ�� 'ALL' ������� ���
            // if (!ss_seltext.equals("ALL")) {
            result += getSelectTag(ls, isChange, true, "s_seldept", ss_seldept);
            // }
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

    public static String getJikwi(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "���� ";

        // String ss_company = box.getStringDefault("s_company", "ALL"); // �ش� ȸ���� comp code
        String ss_seltext = box.getStringDefault("s_jikwi", "ALL");

        try {

            connMgr = new DBConnectionManager();

            // ss_company=""�ΰ�� ����Ÿ�� �������� ����
            // if(ss_company.equals("ALL")) {
            // //sql += " and substr(comp,1,4) = '" + StringManager.substring(box.getSession("comp"), 0, 4) + "'";
            // sql = "select distinct comp, deptnm";
            // sql += " from tz_comp where comptype = '6'";
            // sql += " order by deptnm";
            // } else {
            sql = "select distinct jikwi, jikwinm";
            sql += " from tz_jikwi";
            // sql += " where substr(grpcomp,1,4) = '" + StringManager.substring(ss_company, 0, 4) + "'";
            sql += " order by jikwinm";
            // }

            ls = connMgr.executeQuery(sql);

            result += getSelectTag(ls, isChange, true, "s_jikwi", ss_seltext);
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

    public static String getJikup(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "���� ";

        String ss_company = box.getStringDefault("s_company", "ALL"); // �ش� ȸ���� comp code
        String ss_jikup = box.getStringDefault("s_jikup", "ALL");

        try {

            connMgr = new DBConnectionManager();

            // ss_company=""�ΰ�� ����Ÿ�� �������� ����
            if (ss_company.equals("ALL")) {
                // sql += " and substr(comp,1,4) = '" + StringManager.substring(box.getSession("comp"), 0, 4) + "'";
                sql = "select distinct comp, deptnm";
                sql += " from tz_comp where comptype = '6'";
                sql += " order by deptnm";
            } else {
                sql = "select distinct jikup, jikupnm";
                sql += " from tz_jikup";
                // ������ : 05.11.07 ������ : �̳��� _substr ����
                // sql += " where substr(grpcomp,1,4) = '" + StringManager.substring(ss_company, 0, 4) + "'";
                sql += " where substring(grpcomp,1,4) = '" + StringManager.substring(ss_company, 0, 4) + "'";
                sql += " order by jikupnm";
            }

            ls = connMgr.executeQuery(sql);

            result += getSelectTag(ls, isChange, true, "s_jikup", ss_jikup);
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

    public static String getJikun(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "���� ";

        String ss_company = box.getStringDefault("s_company", "ALL"); // �ش� ȸ���� comp code
        String ss_seltext = box.getStringDefault("s_jikun", "ALL");

        try {

            connMgr = new DBConnectionManager();

            // ss_company=""�ΰ�� ����Ÿ�� �������� ����
            if (ss_company.equals("ALL")) {
                // sql += " and substr(comp,1,4) = '" + StringManager.substring(box.getSession("comp"), 0, 4) + "'";
                sql = "select distinct comp, deptnm";
                sql += " from tz_comp where comptype = '6'";
                sql += " order by deptnm";
            } else {
                sql = "select distinct jikun, jikunnm";
                sql += " from tz_jikun";
                // ������ : 05.11.07 ������ : �̳���
                // sql += " where substr(grpcomp,1,4) = '" + StringManager.substring(ss_company, 0, 4) + "'";
                sql += " where substring(grpcomp,1,4) = '" + StringManager.substring(ss_company, 0, 4) + "'";
                sql += " order by jikunnm";
            }

            ls = connMgr.executeQuery(sql);

            result += getSelectTag(ls, isChange, true, "s_jikun", ss_seltext);
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

    public static String getJikmu(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "���� ";

        String ss_company = box.getStringDefault("s_company", "ALL"); // �ش� ȸ���� comp code
        String ss_seltext = box.getStringDefault("s_jikmu", "ALL");

        try {

            connMgr = new DBConnectionManager();

            // ss_company=""�ΰ�� ����Ÿ�� �������� ����
            if (ss_company.equals("ALL")) {
                // sql += " and substr(comp,1,4) = '" + StringManager.substring(box.getSession("comp"), 0, 4) + "'";
                sql = "select distinct comp, deptnm";
                sql += " from tz_comp where comptype = '6'";
                sql += " order by deptnm";
            } else {
                sql = "select distinct jikmu, jikmunm";
                sql += " from tz_jikmu";
                // ������ : 05.11.07 ������ : �̳��� _substr ����
                // sql += " where substr(grpcomp,1,4) = '" + StringManager.substring(ss_company, 0, 4) + "'";
                sql += " where substring(grpcomp,1,4) = '" + StringManager.substring(ss_company, 0, 4) + "'";
                sql += " order by jikmunm";
            }

            ls = connMgr.executeQuery(sql);

            result += getSelectTag(ls, isChange, true, "s_jikmu", ss_seltext);
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

    public static String getWorkplc(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "�ٹ��� ";

        String ss_company = box.getStringDefault("s_company", "ALL"); // �ش� ȸ���� comp code
        String ss_seltext = box.getStringDefault("s_workplc", "ALL");

        try {

            connMgr = new DBConnectionManager();

            // ss_company=""�ΰ�� ����Ÿ�� �������� ����
            if (ss_company.equals("ALL")) {
                // sql += " and substr(comp,1,4) = '" + StringManager.substring(box.getSession("comp"), 0, 4) + "'";
                sql = "select distinct comp, deptnm";
                sql += " from tz_comp where comptype = '6'";
                sql += " order by deptnm";
            } else {
                sql = "select distinct work_plc, work_plcnm";
                sql += " from tz_workplc";
                // ������ : 05.11.07 ������ : �̳��� _ substr ����
                // sql += " where substr(grpcomp,1,4) = '" + StringManager.substring(ss_company, 0, 4) + "'";
                sql += " where substring(grpcomp,1,4) = '" + StringManager.substring(ss_company, 0, 4) + "'";
                sql += " order by work_plcnm";
            }

            ls = connMgr.executeQuery(sql);

            result += getSelectTag(ls, isChange, true, "s_workplc", ss_seltext);
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

    public static String getGrcomp(RequestBox box, boolean isChange, boolean isALL) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String result = "ȸ�� ";

        try {
            // String s_gadmin = box.getSession("gadmin");
            // String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
            // String s_userid = box.getSession("userid");
            // String ss_company = box.getStringDefault("s_company", "ALL");
            String ss_grcode = box.getString("s_grcode");
            String ss_grcomp = box.getString("s_grcomp");

            connMgr = new DBConnectionManager();

            // if(v_gadmin.equals("H")) { // �����׷������
            // sql = "select distinct a.comp, a.companynm";
            // sql += " from tz_comp a, tz_grcomp b,";
            // sql += " (select grcode from tz_grcodeman where userid = " + SQLString.Format(s_userid) + " and gadmin = " + SQLString.Format(s_gadmin) +
            // ") c";
            // sql += " where a.comp = b.comp and b.grcode = c.grcode and a.comptype = '2'";
            // sql += " order by a.companynm";
            // }
            // else if(v_gadmin.equals("K")) { // ȸ�������, �μ�������
            // sql = "select distinct a.comp, a.companynm";
            // sql += " from tz_comp a, tz_compman b";
            // sql += " where substr(a.comp, 1, 4) = substr(b.comp, 1, 4) and b.userid = " + SQLString.Format(s_userid) + " and b.gadmin = " +
            // SQLString.Format(s_gadmin);
            // sql += "  and a.comptype = '2' order by a.companynm";
            // }
            // else { // Ultravisor, Supervisor, ����������, ����
            // sql = "select distinct comp, companynm";
            // sql += " from tz_comp where comptype = '2'";
            // sql += " order by companynm";
            // }

            sql += " select                                       \n";
            sql += "   b.comp,                                    \n";
            sql += "   b.companynm                                \n";
            sql += " from                                         \n";
            sql += "   tz_grcomp a, tz_comp b                     \n";
            sql += " where                                        \n";
            sql += "   grcode = " + SQLString.Format(ss_grcode) + " \n";
            sql += "   and a.comp = b.comp                        \n";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

            // if(v_gadmin.equals("A") || v_gadmin.equals("F") || v_gadmin.equals("P") || v_gadmin.equals("H")) {
            // Ultra/Super or ���������� or ���� or �����׷������ �� ��� 'ALL' ȸ�� ���
            result += getSelectTag(ls, isChange, isALL, "s_grcomp", ss_grcomp);
            // }
            // else {
            // result += getSelectTag(ls, isChange, false, "s_company", ss_company);
            // }
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
                    sb.append("<option value = \"----\">== ���� ==</option>\r\n");
            }

            while (ls.next()) {
                ResultSetMetaData meta = ls.getMetaData();
                int columnCount = meta.getColumnCount();

                sb.append("<option value = \"" + ls.getString(1) + "\"");

                if (optionselected.equals(ls.getString(1)))
                    sb.append(" selected");

                sb.append(">" + ls.getString(columnCount) + "</option>\r\n");
            }
            sb.append("</select>\r\n");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, true);
            throw new Exception(ex.getMessage());
        }
        return sb.toString();
    }
}
