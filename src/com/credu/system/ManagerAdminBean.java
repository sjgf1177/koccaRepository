//**********************************************************
//  1. ��      ��: �н����
//  2. ���α׷��� : ManagerAdminBean.java
//  3. ��      ��: �н����
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 11. 10
//  7. ��      ��:
//**********************************************************

package com.credu.system;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * ��� ����(ADMIN)
 * 
 * @date : 2004. 11
 * @author : S.W.Kang
 */
@SuppressWarnings("unchecked")
public class ManagerAdminBean {

    public ManagerAdminBean() {
    }

    /**
     * ��� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     */
    public int deleteManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        PreparedStatement pstmt4 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        int isOk1 = 0;
        // int isOk2 = 0;
        // int isOk3 = 0;
        // int isOk4 = 0;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");
        // String v_isdeleted = "Y";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            // ������ TABLE ���� -- isdeleted �ʵ忡 Y ����
            sql1 = "  delete from TZ_MANAGER 	  ";
            sql1 += "   where userid = ?		  ";
            sql1 += "     and gadmin = ?		  ";
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_userid);
            pstmt1.setString(2, v_gadmin);
            isOk1 = pstmt1.executeUpdate();

            // �����׷� TABLE ����
            sql2 = " delete from TZ_GRCODEMAN    ";
            sql2 += "  where userid  = ?          ";
            sql2 += "    and gadmin  = ?          ";

            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setString(1, v_userid);
            pstmt2.setString(2, v_gadmin);
            pstmt2.executeUpdate();

            // ����ȸ��/�μ����� TABLE ����
            sql3 = " delete from TZ_COMPMAN      ";
            sql3 += "  where userid  = ?          ";
            //            sql3 += "    and gadmin  = ?          ";

            pstmt3 = connMgr.prepareStatement(sql3);
            pstmt3.setString(1, v_userid);
            //            pstmt3.setString(2, v_gadmin);
            pstmt3.executeUpdate();

            // �������� TABLE ����
            sql4 = " delete from TZ_SUBJMAN      ";
            sql4 += "  where userid  = ?          ";
            sql4 += "    and gadmin  = ?          ";

            pstmt4 = connMgr.prepareStatement(sql4);
            pstmt4.setString(1, v_userid);
            pstmt4.setString(2, v_gadmin);
            pstmt4.executeUpdate();

            if (isOk1 > 0)
                connMgr.commit(); //  �׷�, ����, ȸ��(�μ�) ����� �ܿ��� ���� ���� ���̺��� �����Ƿ�
            else
                connMgr.rollback();

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e) {
                }
            }
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e) {
                }
            }
            if (pstmt4 != null) {
                try {
                    pstmt4.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
                }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        //        return isOk1*isOk2;
        return isOk1;
    }

    /**
     * �μ������� �����λ���������
     * 
     * @param box receive from the form object and session
     * @return String ��������
     */
    public String getManagerDept(String v_userid, String v_gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String result = "";
        int i = 0;

        try {
            connMgr = new DBConnectionManager();
            sql = " select comp comp from TZ_COMPMAN  ";
            sql += "  where userid = " + StringManager.makeSQL(v_userid);
            sql += "    and gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by comp asc            ";
            ls = connMgr.executeQuery(sql);
            //System.out.println("comp_sql="+sql);

            while (ls.next()) {
                if (i == 0)
                    result = " ( ";
                else
                    result += ", ";

                result += StringManager.makeSQL(ls.getString("comp"));
                i++;
            }
            if (i > 0)
                result += " ) ";
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
     * ��� ����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;
        int isOk1 = 0;
        int v_cnt = 0; // �ߺ�üũ

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");
        String v_grtype = box.getString("p_grtype");
        String v_isdeleted = "N";
        String v_fmon = box.getString("p_fmon");
        String v_tmon = box.getString("p_tmon");
        String v_commented = box.getString("p_commented");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            sql = "select count(*) from TZ_MANAGER ";
            sql += " where userid  = " + StringManager.makeSQL(v_userid);
            sql += "   and gadmin = " + StringManager.makeSQL(v_gadmin);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_cnt = ls.getInt(1);
            }

            if (v_cnt > 0) { // ���� ��ϵǾ�������
                isOk = 0;
            } else {
                sql1 = "insert into TZ_MANAGER(userid, gadmin, grtype, isdeleted, fmon, tmon, commented, luserid, ldate)  ";
                sql1 += "               values (?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))        ";

                pstmt = connMgr.prepareStatement(sql1);
                pstmt.setString(1, v_userid);
                pstmt.setString(2, v_gadmin);
                pstmt.setString(3, v_grtype);
                pstmt.setString(4, v_isdeleted);
                pstmt.setString(5, v_fmon);
                pstmt.setString(6, v_tmon);
                pstmt.setString(7, v_commented);
                pstmt.setString(8, s_userid);

                isOk = pstmt.executeUpdate();
            }

            isOk1 = insertManagerSub(box);

            System.out.println("isOk=" + isOk);

            if (isOk > 0 && isOk1 > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
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

    /**
     * ��� ����Ҷ� - ��������
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertManagerSub(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        // int v_cnt = 0;       // �ߺ�üũ

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");
        String v_gadminview = box.getString("p_gadminview");

        String v_isneedgrcode = "";
        String v_isneedsubj = "";
        String v_isneedcomp = "";
        String v_isneedoutcomp = "";
        String v_isneeddept = "";

        System.out.println("v_gadminview = " + v_gadminview);

        // �ڵ���� (�����ڵ� + "," + �����׷��ʿ俩��  + "," + �����ڵ��ʿ俩�� + "," + ȸ���ڵ��ʿ俩�� + "," + �μ��ڵ��ʿ俩��)
        StringTokenizer st = new StringTokenizer(v_gadminview, ",");

        // �׷�/����/ȸ��/�μ� �ʿ俩��
        if (st.hasMoreElements()) {
            v_gadmin = st.nextToken();
            v_isneedgrcode = st.nextToken();
            v_isneedsubj = st.nextToken();
            v_isneedcomp = st.nextToken();
            v_isneeddept = st.nextToken();
            //v_isneedoutcomp = (String)st.nextToken();
        }

        // �׷��ڵ�
        Vector v_vgrcode = box.getVector("p_grcode");
        String v_sgrcode = "";
        // �����ڵ�
        Vector v_vsubj = box.getVector("p_subj");
        String v_ssubj = "";
        // ȸ���ڵ�
        Vector v_vcomp = box.getVector("p_company");
        String v_scomp = "";

        // ȸ���ڵ�
        Vector v_voutcomp = box.getVector("p_outcompany");
        String v_soutcomp = "";

        // �μ��ڵ�
        Vector v_vdept = box.getVector("p_dept");
        String v_sdept = "";

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            isOk = 1; // �׷�/����/�μ� �ڵ� ��� �������(UltraVisor,SuperVisor)
            // �׷��ڵ� �ʿ俩��
            if (v_isneedgrcode.equals("Y")) {
                for (int i = 0; i < v_vgrcode.size(); i++) {
                    v_sgrcode = (String) v_vgrcode.elementAt(i);
                    sql = "insert into TZ_GRCODEMAN(userid, gadmin, grcode, luserid, ldate)         ";
                    sql += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_userid);
                    pstmt.setString(2, v_gadmin);
                    pstmt.setString(3, v_sgrcode);
                    pstmt.setString(4, s_userid);
                    isOk = pstmt.executeUpdate();
                }
            }
            // �����ڵ� �ʿ俩��
            if (v_isneedsubj.equals("Y")) {
                for (int i = 0; i < v_vsubj.size(); i++) {
                    v_ssubj = (String) v_vsubj.elementAt(i);
                    sql = "insert into TZ_SUBJMAN(userid, gadmin, subj, luserid, ldate)             ";
                    sql += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_userid);
                    pstmt.setString(2, v_gadmin);
                    pstmt.setString(3, v_ssubj);
                    pstmt.setString(4, s_userid);
                    isOk = pstmt.executeUpdate();
                }
            }
            // ȸ���ڵ� �ʿ俩��
            if (v_isneedcomp.equals("Y")) {
                for (int i = 0; i < v_vcomp.size(); i++) {
                    v_scomp = (String) v_vcomp.elementAt(i);
                    sql = "insert into TZ_COMPMAN(userid, gadmin, comp, luserid, ldate)             ";
                    sql += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_userid);
                    pstmt.setString(2, v_gadmin);
                    pstmt.setString(3, v_scomp);
                    pstmt.setString(4, s_userid);
                    isOk = pstmt.executeUpdate();
                }
            }
            // �ܺξ�üȸ���ڵ� �ʿ俩��
            if (v_isneedoutcomp.equals("Y")) {
                for (int i = 0; i < v_voutcomp.size(); i++) {
                    v_soutcomp = (String) v_voutcomp.elementAt(i);
                    sql = "insert into TZ_OUTCOMPMAN(userid, gadmin, comp, luserid, ldate)             ";
                    sql += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_userid);
                    pstmt.setString(2, v_gadmin);
                    pstmt.setString(3, v_soutcomp);
                    pstmt.setString(4, s_userid);
                    isOk = pstmt.executeUpdate();
                }
            }
            // �μ��ڵ� �ʿ俩��
            if (v_isneeddept.equals("Y")) {
                for (int i = 0; i < v_vdept.size(); i++) {
                    v_sdept = (String) v_vdept.elementAt(i);
                    sql = "insert into TZ_COMPMAN(userid, gadmin, comp, luserid, ldate)             ";
                    sql += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                    pstmt = connMgr.prepareStatement(sql);
                    pstmt.setString(1, v_userid);
                    pstmt.setString(2, v_gadmin);
                    pstmt.setString(3, v_sdept);
                    pstmt.setString(4, s_userid);
                    isOk = pstmt.executeUpdate();
                }
            }

            System.out.println("insertManagerSub=====>>>>" + isOk);
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
        return isOk;
    }

    /**
     * ���ȭ�� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��� ����Ʈ
     */
    public ArrayList selectListManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext").trim();
        String v_search = box.getString("p_search").trim();
        String s_gadmin = box.getStringDefault("s_gadmin", "ALL"); //select box gadmin
        String ss_gadmin = box.getSession("gadmin");
        String ss_grtype = box.getSession("grtype");
        String v_orderColumn = box.getString("p_orderColumn"); //������ �÷���
        String v_orderType = box.getString("p_orderType"); //������ ����
        StringTokenizer st = new StringTokenizer(s_gadmin, ",");

        if (st.hasMoreElements()) {
            s_gadmin = st.nextToken();
        }

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql = " Select a.userid userid, a.gadmin gadmin, c.gadminnm gadminnm, \n ";
            sql += "        a.grtype grtype, a.isdeleted isdeleted, a.fmon fmon, a.tmon tmon, \n";
            sql += "        a.commented commented, a.luserid luserid, a.ldate ldate, \n";
            sql += "        b.name name, get_compnm(b.comp,2,2) compnm \n";
            sql += "   From TZ_MANAGER a, TZ_MEMBER b, TZ_GADMIN c\n";
            sql += "  Where a.userid = b.userid \n";
            sql += "    and a.gadmin = c.gadmin\n";
            sql += "    and a.isdeleted = 'N'\n ";
            sql += "    and c.isView = 'Y' \n";
            //			sql += "    and b.grcode = 'N000001' \n";

            if (!v_searchtext.equals("")) { //    �˻�� ������
                if (v_search.equals("name")) { //    �������� �˻��Ҷ�
                    sql += " and b.name like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_search.equals("userid")) { //    �������� �˻��Ҷ�
                    sql += " and upper(b.userid) like upper(" + StringManager.makeSQL("%" + v_searchtext + "%") + ")\n";
                }
            }
            if (!s_gadmin.equals("ALL")) {
                sql += " and c.gadmin = " + StringManager.makeSQL(s_gadmin);
            }
            if (!StringManager.substring(ss_gadmin, 0, 1).equals("A")) {
                sql += " and a.grtype = " + StringManager.makeSQL(ss_grtype);
            }

            if (v_orderColumn.equals("")) {
                sql += " order by a.gadmin asc , b.name asc";
            } else {
                sql += " order by b." + v_orderColumn + v_orderType;
            }

            ls = connMgr.executeQuery(sql);
            //			while (ls.next()) {
            //				dbox = ls.getDataBox();
            //				list.add(dbox);
            //			}

            int total_row_count = BoardPaging.getTotalRow(connMgr, sql, true); //     ��ü row ���� ��ȯ�Ѵ�
            int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
            int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");
            ls.setPageSize(v_pagesize); //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); //     ������������ȣ�� �����Ѵ�.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(v_pagesize));
                dbox.put("d_totalrowcount", new Integer(total_row_count));
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
     * ���ȭ�� �󼼺���
     * 
     * @param box receive from the form object and session
     * @return DataBox ��ȸ�� ������
     */
    public DataBox selectViewManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();

            sql = " select a.userid userid, a.gadmin gadmin, c.gadminnm gadminnm,          ";
            sql += "        a.grtype grtype, a.isdeleted isdeleted, a.fmon fmon, a.tmon tmon,   ";
            sql += "        a.commented commented, a.luserid luserid, a.ldate ldate,        ";
            sql += "        b.name name, get_compnm(b.comp,2,2) compnm                  ";
            sql += "   from TZ_MANAGER a, TZ_MEMBER b, TZ_GADMIN c                          ";
            sql += "  where a.userid = b.userid                                             ";
            sql += "    and a.gadmin = c.gadmin                                             ";
            sql += "    and a.userid  = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();
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
        return dbox;
    }

    /**
     * ���ȭ�� �󼼺��� - ȸ��
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���� ȸ�� ����Ʈ
     */
    public ArrayList selectViewManagerComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.comp, b.groupsnm , b.companynm , b.compnm from TZ_COMPMAN a , TZ_COMP b   ";
            sql += "  where a.comp = b.comp                                  ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.comp asc                ";
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
     * ���ȭ�� �󼼺��� - �μ�
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���� �μ� ����Ʈ
     */
    public ArrayList selectViewManagerDept(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.comp, b.groupsnm , b.companynm , b.gpmnm, b.deptnm , b.compnm from TZ_COMPMAN a , TZ_COMP b   ";
            sql += "  where a.comp = b.comp                                  ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.comp asc                ";
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
     * ���ȭ�� �󼼺��� - �׷�
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���� �����׷� ����Ʈ
     */
    public ArrayList selectViewManagerGrcode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.grcode, b.grcodenm from TZ_GRCODEMAN a , TZ_GRCODE b   ";
            sql += "  where a.grcode = b.grcode                                      ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.grcode asc                                           ";
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
     * ���ȭ�� �󼼺��� - ȸ��
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���� ȸ�� ����Ʈ
     */
    public ArrayList selectViewManagerOutComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.comp, b.cpnm from TZ_OUTCOMPMAN a , TZ_CPINFO b   ";
            sql += "  where a.comp = b.cpseq                                  ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.comp asc                ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                System.out.println(sql);
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
     * ���ȭ�� �󼼺��� - ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���� ���� ����Ʈ
     */
    public ArrayList selectViewManagerSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            sql = " select a.subj, b.subjnm from TZ_SUBJMAN a , TZ_SUBJ b   ";
            sql += "  where a.subj = b.subj                                  ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.subj asc                                     ";

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
     * ��� �����Ͽ� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int updateManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        int isOk1 = 0;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");
        String v_grtype = box.getString("p_grtype");
        String v_fmon = box.getString("p_fmon");
        String v_tmon = box.getString("p_tmon");
        String v_commented = box.getString("p_commented");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            sql = " update TZ_MANAGER set grtype = ? , fmon = ?, tmon = ?, commented = ?, luserid= ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where userid  = ?  and gadmin = ? ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_grtype);
            pstmt.setString(2, v_fmon);
            pstmt.setString(3, v_tmon);
            pstmt.setString(4, v_commented);
            pstmt.setString(5, s_userid);
            pstmt.setString(6, v_userid);
            pstmt.setString(7, v_gadmin);

            isOk = pstmt.executeUpdate();
            isOk1 = updateManagerSub(box);

            if (isOk > 0 && isOk1 > 0) {
                connMgr.commit();
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * ��� �����Ҷ� - ��������
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     */
    public int updateManagerSub(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        // int v_cnt = 0;       // �ߺ�üũ

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");
        String v_gadminview = box.getString("p_gadminview");
        String v_isneedgrcode = "";
        String v_isneedsubj = "";
        String v_isneedcomp = "";
        String v_isneeddept = "";
        String v_isneedoutcomp = "";

        // �ڵ���� (�����ڵ� + "," + �����׷��ʿ俩��  + "," + �����ڵ��ʿ俩�� + "," + ȸ���ڵ��ʿ俩�� + "," + �μ��ڵ��ʿ俩��)
        StringTokenizer st = new StringTokenizer(v_gadminview, ",");
        // int j = 0;

        // �׷�/����/ȸ��/�μ� �ʿ俩��
        if (st.hasMoreElements()) {
            v_gadmin = st.nextToken();
            v_isneedgrcode = st.nextToken();
            v_isneedsubj = st.nextToken();
            v_isneedcomp = st.nextToken();
            v_isneeddept = st.nextToken();
            //v_isneedoutcomp = (String)st.nextToken();
        }

        //System.out.println(v_gadmin       );
        //System.out.println(v_isneedgrcode );
        //System.out.println(v_isneedsubj   );
        //System.out.println(v_isneedcomp   );
        //System.out.println(v_isneeddept   );
        //System.out.println(v_isneedoutcomp);

        // �׷��ڵ� - v_gadmin(H)
        Vector v_vgrcode = box.getVector("p_grcode");
        String v_sgrcode = "";

        // �����ڵ� - v_gadmin(F,P)
        Vector v_vsubj = box.getVector("p_subj");
        String v_ssubj = "";

        // ȸ���ڵ� - v_gadmin(K)
        Vector v_vcomp = box.getVector("p_company");
        String v_scomp = "";

        // ȸ���ڵ� - v_gadmin(S,T,M)
        Vector v_voutcomp = box.getVector("p_outcompany");
        String v_soutcomp = "";

        // �μ��ڵ� - v_gadmin(K)
        Vector v_vdept = box.getVector("p_dept");
        String v_sdept = "";

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            isOk = 1; // �׷�/����/�μ� �ڵ� ��� �������(UltraVisor,SuperVisor)
            // �׷��ڵ� �ʿ俩��
            if (v_isneedgrcode.equals("Y")) {
                // ���� ����Ÿ ����
                sql1 = " delete from TZ_GRCODEMAN ";
                sql1 += "  where userid = ?        ";
                sql1 += "    and gadmin = ?        ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(1, v_userid);
                pstmt1.setString(2, v_gadmin);
                isOk = pstmt1.executeUpdate();
                // ���
                sql2 = "insert into TZ_GRCODEMAN(userid, gadmin, grcode, luserid, ldate)        ";
                sql2 += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                pstmt2 = connMgr.prepareStatement(sql2);
                for (int i = 0; i < v_vgrcode.size(); i++) {
                    v_sgrcode = (String) v_vgrcode.elementAt(i);
                    pstmt2.setString(1, v_userid);
                    pstmt2.setString(2, v_gadmin);
                    pstmt2.setString(3, v_sgrcode);
                    pstmt2.setString(4, s_userid);
                    isOk = pstmt2.executeUpdate();
                }
            }
            // �����ڵ� �ʿ俩��
            if (v_isneedsubj.equals("Y")) {
                // ���� ����Ÿ ����
                sql1 = " delete from TZ_SUBJMAN ";
                sql1 += "  where userid = ?        ";
                sql1 += "    and gadmin = ?        ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(1, v_userid);
                pstmt1.setString(2, v_gadmin);
                isOk = pstmt1.executeUpdate();
                // ���
                sql2 = "insert into TZ_SUBJMAN(userid, gadmin, subj, luserid, ldate)             ";
                sql2 += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                pstmt2 = connMgr.prepareStatement(sql2);
                for (int i = 0; i < v_vsubj.size(); i++) {
                    v_ssubj = (String) v_vsubj.elementAt(i);
                    pstmt2.setString(1, v_userid);
                    pstmt2.setString(2, v_gadmin);
                    pstmt2.setString(3, v_ssubj);
                    pstmt2.setString(4, s_userid);
                    isOk = pstmt2.executeUpdate();
                }
            }
            // ȸ���ڵ� �ʿ俩��
            if (v_isneedcomp.equals("Y")) {
                // ���� ����Ÿ ����
                sql1 = " delete from TZ_COMPMAN ";
                sql1 += "  where userid = ?        ";
                sql1 += "    and gadmin = ?        ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(1, v_userid);
                pstmt1.setString(2, v_gadmin);
                isOk = pstmt1.executeUpdate();
                // ���
                sql2 = "insert into TZ_COMPMAN(userid, gadmin, comp, luserid, ldate)             ";
                sql2 += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                pstmt2 = connMgr.prepareStatement(sql2);
                for (int i = 0; i < v_vcomp.size(); i++) {
                    v_scomp = (String) v_vcomp.elementAt(i);
                    pstmt2.setString(1, v_userid);
                    pstmt2.setString(2, v_gadmin);
                    pstmt2.setString(3, v_scomp);
                    pstmt2.setString(4, s_userid);
                    isOk = pstmt2.executeUpdate();
                }
            }
            // �����ڵ� �ʿ俩��
            if (v_isneedoutcomp.equals("Y")) {
                // ���� ����Ÿ ����
                sql1 = " delete from TZ_OUTCOMPMAN ";
                sql1 += "  where userid = ?        ";
                sql1 += "    and gadmin = ?        ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(1, v_userid);
                pstmt1.setString(2, v_gadmin);
                isOk = pstmt1.executeUpdate();
                // ���
                sql2 = "insert into TZ_OUTCOMPMAN(userid, gadmin, comp, luserid, ldate)             ";
                sql2 += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                pstmt2 = connMgr.prepareStatement(sql2);
                for (int i = 0; i < v_voutcomp.size(); i++) {
                    v_soutcomp = (String) v_voutcomp.elementAt(i);
                    pstmt2.setString(1, v_userid);
                    pstmt2.setString(2, v_gadmin);
                    pstmt2.setString(3, v_soutcomp);
                    pstmt2.setString(4, s_userid);
                    isOk = pstmt2.executeUpdate();
                }
            }

            // �μ��ڵ� �ʿ俩��
            if (v_isneeddept.equals("Y")) {
                // ���� ����Ÿ ����
                sql1 = " delete from TZ_COMPMAN ";
                sql1 += "  where userid = ?        ";
                sql1 += "    and gadmin = ?        ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setString(1, v_userid);
                pstmt1.setString(2, v_gadmin);
                isOk = pstmt1.executeUpdate();
                // ���
                sql2 = "insert into TZ_COMPMAN(userid, gadmin, comp, luserid, ldate)             ";
                sql2 += "               values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                pstmt2 = connMgr.prepareStatement(sql2);
                for (int i = 0; i < v_vdept.size(); i++) {
                    v_sdept = (String) v_vdept.elementAt(i);
                    pstmt2.setString(1, v_userid);
                    pstmt2.setString(2, v_gadmin);
                    pstmt2.setString(3, v_sdept);
                    pstmt2.setString(4, s_userid);
                    isOk = pstmt2.executeUpdate();
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
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
        return isOk;
    }

}
