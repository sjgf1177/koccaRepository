//**********************************************************
//  1. ��      ��: ��� ����
//  2. ���α׷��� : PermissionBean.java
//  3. ��      ��: ��� ����
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

import com.credu.library.DBConnectionManager;
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
public class PermissionBean {

    public PermissionBean() {
    }

    /**
     * ���ȭ�� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��� ����Ʈ
     */
    public ArrayList<PermissionData> selectListPermission(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<PermissionData> list = null;
        String sql = "";
        PermissionData data = null;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<PermissionData>();

            sql = " select gadmin, ";
            sql += "        gadminnm, ";
            sql += "        comments, ";
            sql += "        padmin, ";
            sql += "        seq ";
            sql += "   from tz_gadmin ";
            sql += "  order by seq asc, ";
            sql += "           gadminnm asc ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                data = new PermissionData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                data.setComments(ls.getString("comments"));
                data.setPadmin(ls.getString("padmin"));
                data.setGadminseq(ls.getInt("seq"));

                list.add(data);
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
     * @return ManagerData ��ȸ�� ������
     */
    public PermissionData selectViewPermission(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        PermissionData data = null;

        String v_padmin = box.getString("p_padmin");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadminnm,";
            sql += "        comments,";
            sql += "        seq ";
            sql += "   from TZ_GADMIN where gadmin='" + v_gadmin + "' and padmin='" + v_padmin + "'";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new PermissionData();

                data.setGadminnm(ls.getString("gadminnm"));
                data.setComments(ls.getString("comments"));
                data.setGadminseq(ls.getInt("seq"));
            }
            ls.close();
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
        return data;
    }

    /**
     * ���ȭ�� �󼼺��� - �׷�
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���� �����׷� ����Ʈ
     */
    public ArrayList<GrcodemanData> selectViewManagerGrcode(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<GrcodemanData> list = null;
        String sql = "";
        GrcodemanData data = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<GrcodemanData>();

            sql = " select a.grcode, b.grcodenm from TZ_GRCODEMAN a , TZ_GRCODE b   ";
            sql += "  where a.grcode = b.grcode                                      ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.grcode asc                                           ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GrcodemanData();
                data.setGrcode(ls.getString("grcode"));
                data.setGrcodenm(ls.getString("grcodenm"));
                list.add(data);
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
    public ArrayList<SubjmanData> selectViewManagerSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<SubjmanData> list = null;
        String sql = "";
        SubjmanData data = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<SubjmanData>();

            sql = " select a.subj, b.subjnm from TZ_SUBJMAN a , TZ_SUBJ b   ";
            sql += "  where a.subj = b.subj                                  ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.subj asc                                     ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new SubjmanData();
                data.setSubj(ls.getString("subj"));
                data.setSubjnm(ls.getString("subjnm"));
                list.add(data);
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
    public ArrayList<CompmanData> selectViewManagerComp(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<CompmanData> list = null;
        String sql = "";
        CompmanData data = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<CompmanData>();

            sql = " select a.comp, b.groupsnm , b.companynm , b.compnm from TZ_COMPMAN a , TZ_COMP b   ";
            sql += "  where a.comp = b.comp                                  ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.comp asc                ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CompmanData();
                data.setComp(ls.getString("comp"));
                data.setGroupsnm(ls.getString("groupsnm"));
                data.setCompanynm(ls.getString("companynm"));
                data.setCompnm(ls.getString("compnm"));
                list.add(data);
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
    public ArrayList<CompmanData> selectViewManagerDept(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<CompmanData> list = null;
        String sql = "";
        CompmanData data = null;

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<CompmanData>();

            sql = " select a.comp, b.groupsnm , b.companynm , b.gpmnm, b.deptnm , b.compnm from TZ_COMPMAN a , TZ_COMP b   ";
            sql += "  where a.comp = b.comp                                  ";
            sql += "    and a.userid = " + StringManager.makeSQL(v_userid);
            sql += "    and a.gadmin = " + StringManager.makeSQL(v_gadmin);
            sql += " order by a.comp asc                ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CompmanData();
                data.setComp(ls.getString("comp"));
                data.setGroupsnm(ls.getString("groupsnm"));
                data.setCompanynm(ls.getString("companynm"));
                data.setGpmnm(ls.getString("gpmnm"));
                data.setDeptnm(ls.getString("deptnm"));
                data.setCompnm(ls.getString("compnm"));
                list.add(data);
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
     * ���� ����Ҷ�
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
        String v_gadmin = "";
        String v_admin = "";
        String v_isneedsubj = "N";
        String v_isneedcomp = "N";
        String v_isneeddept = "N";
        String v_isneedgrcode = "N";
        String v_count = "";

        int isOk = 0;
        int v_cnt = 0; // �ߺ�üũ
        int v_applevel = 3;

        String v_gadminnm = box.getString("p_gadminnm");
        String v_comments = box.getString("p_comments");
        String v_padmin = box.getString("p_gadminsel");
        String s_userid = box.getSession("userid");
        int v_gadminseq = box.getInt("p_gadminseq");

        StringTokenizer st = new StringTokenizer(v_padmin, ",");

        // �׷�/����/ȸ��/�μ� �ʿ俩��
        if (st.hasMoreElements()) {
            v_admin = (String) st.nextToken();
            v_isneedgrcode = (String) st.nextToken();
            v_isneedsubj = (String) st.nextToken();
            v_isneedcomp = (String) st.nextToken();
            v_isneeddept = (String) st.nextToken();
        }

        try {
            connMgr = new DBConnectionManager();

            sql = "select count(*) from TZ_GADMIN ";
            sql += " where gadminnm  = " + StringManager.makeSQL(v_gadminnm);
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_cnt = ls.getInt(1);
            }
            ls.close();

            if (v_cnt > 0) { // ���� ��ϵǾ�������
                isOk = 0;
            } else {
                sql = "select 	to_char(max(NVL(SUBSTR(gadmin,3,4),0)+1),'00') count from TZ_GADMIN ";
                sql += " where substring(gadmin,1,2)='" + v_admin + "'";
                ls = connMgr.executeQuery(sql);
                if (ls.next()) {
                    v_count = ls.getString(1);
                }
                ls.close();

                v_gadmin = v_admin + v_count;

                sql1 = "insert into TZ_GADMIN";
                sql1 += "( gadmin,        ";
                sql1 += "  control,       ";
                sql1 += "  gadminnm,      ";
                sql1 += "  comments,      ";
                sql1 += "  isneedsubj,    ";
                sql1 += "  isneedcomp,    ";
                sql1 += "  isneeddept,    ";
                sql1 += "  isneedgrcode,  ";
                sql1 += "  applevel,      ";
                sql1 += "  padmin,        ";
                sql1 += "  seq,     ";
                sql1 += "  luserid,       ";
                sql1 += "  ldate)         ";
                sql1 += "  values(?,'rw',?,?,?,?,?,?,?,?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'))               ";

                pstmt = connMgr.prepareStatement(sql1);
                pstmt.setString(1, v_gadmin);
                pstmt.setString(2, v_gadminnm);
                pstmt.setString(3, v_comments);
                pstmt.setString(4, v_isneedsubj);
                pstmt.setString(5, v_isneedcomp);
                pstmt.setString(6, v_isneeddept);
                pstmt.setString(7, v_isneedgrcode);
                pstmt.setInt(8, v_applevel);
                pstmt.setString(9, v_admin);
                pstmt.setInt(10, v_gadminseq);
                pstmt.setString(11, s_userid);

                isOk = pstmt.executeUpdate();
            }
        } catch (Exception ex) {
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
        // int v_cnt = 0; // �ߺ�üũ

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");
        String v_gadminview = box.getString("p_gadminview");
        String v_isneedgrcode = "";
        String v_isneedsubj = "";
        String v_isneedcomp = "";
        String v_isneeddept = "";

        // �ڵ���� (�����ڵ� + "," + �����׷��ʿ俩��  + "," + �����ڵ��ʿ俩�� + "," + ȸ���ڵ��ʿ俩�� + "," + �μ��ڵ��ʿ俩��)
        StringTokenizer st = new StringTokenizer(v_gadminview, ",");

        // �׷�/����/ȸ��/�μ� �ʿ俩��
        if (st.hasMoreElements()) {
            v_gadmin = (String) st.nextToken();
            v_isneedgrcode = (String) st.nextToken();
            v_isneedsubj = (String) st.nextToken();
            v_isneedcomp = (String) st.nextToken();
            v_isneeddept = (String) st.nextToken();
        }

        // �׷��ڵ�
        Vector<?> v_vgrcode = box.getVector("p_grcode");
        String v_sgrcode = "";
        // �����ڵ�
        Vector<?> v_vsubj = box.getVector("p_subj");
        String v_ssubj = "";
        // ȸ���ڵ�
        Vector<?> v_vcomp = box.getVector("p_company");
        String v_scomp = "";
        // �μ��ڵ�
        Vector<?> v_vdept = box.getVector("p_dept");
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
     * ���� ����� �� �ش� ���� ������ �޴� ������ �°��Ѵ�.
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     */
    public int insertMenuAuth(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        // PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        String v_gadmin = "";
        String v_admin = "";
        // String v_isneedsubj = "N";
        // String v_isneedcomp = "N";
        // String v_isneeddept = "N";
        // String v_isneedgrcode = "N";
        String v_count = "";

        int isOk = 0; // ó�����
        // int v_cnt = 0; // �ߺ�üũ
        // int v_applevel = 3;

        // String v_gadminnm = box.getString("p_gadminnm");
        // String v_comments = box.getString("p_comments");
        String v_padmin = box.getString("p_gadminsel");
        String s_userid = box.getSession("userid");
        StringTokenizer st = new StringTokenizer(v_padmin, ",");

        // �׷�/����/ȸ��/�μ� �ʿ俩��
        if (st.hasMoreElements()) {
            v_admin = (String) st.nextToken();
            // v_isneedgrcode = (String) st.nextToken();
            // v_isneedsubj = (String) st.nextToken();
            // v_isneedcomp = (String) st.nextToken();
            // v_isneeddept = (String) st.nextToken();
        }

        try {
            connMgr = new DBConnectionManager();

            //sql  =  "select 	max(gadmin) gadmin from TZ_GADMIN ";
            //sql +=  " where substring(gadmin,1,2)='"+v_admin+"'";
            //ls = connMgr.executeQuery(sql);
            //if (ls.next()) {
            //		v_gadmin = ls.getString(1);
            //	}
            //	ls.close();

            sql = "select 	to_char(max(NVL(SUBSTR(gadmin,3,4),0)),'00') count from TZ_GADMIN ";
            sql += " where substring(gadmin,1,2)='" + v_admin + "'";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_count = ls.getString(1);
            }
            ls.close();

            v_gadmin = v_admin + v_count;

            System.out.println("v_gadmin###################=" + v_gadmin);

            sql1 = "insert into TZ_MENUAUTH	";
            sql1 += "select grcode,";
            sql1 += "       menu,";
            sql1 += "	   menusubseq,";
            sql1 += "	   '" + v_gadmin + "',";
            sql1 += "	   control,";
            sql1 += "	   '" + s_userid + "',";
            sql1 += "	   to_char(sysdate,'YYYYMMDDHH24MISS'),";
            sql1 += "     systemgubun ";
            sql1 += "  from TZ_MENUAUTH ";
            sql1 += " where gadmin='" + v_admin + "'";
            System.out.println("sql1#########################=" + sql1);
            isOk = connMgr.executeUpdate(sql1);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
        return isOk;
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
        ListSet ls = null;

        String sql = "";
        String v_gadmin = "";
        String v_admin = "";
        String v_isneedsubj = "N";
        String v_isneedcomp = "N";
        String v_isneeddept = "N";
        String v_isneedgrcode = "N";
        String v_count = "";

        int isOk = 0;
        int v_cnt = 0; // �ߺ�üũ
        int v_applevel = 3;

        String v_oldadmin = box.getString("p_gadmin");
        String v_gadminnm = box.getString("p_gadminnm");
        String v_comments = box.getString("p_comments");
        String v_padmin = box.getString("p_gadminsel");
        String s_userid = box.getSession("userid");
        int v_gadminseq = box.getInt("p_gadminseq");

        StringTokenizer st = new StringTokenizer(v_padmin, ",");

        // �׷�/����/ȸ��/�μ� �ʿ俩��
        if (st.hasMoreElements()) {
            v_admin = (String) st.nextToken();
            v_isneedgrcode = (String) st.nextToken();
            v_isneedsubj = (String) st.nextToken();
            v_isneedcomp = (String) st.nextToken();
            v_isneeddept = (String) st.nextToken();
        }

        try {
            connMgr = new DBConnectionManager();

            sql = "select count(gadmin) ";
            sql += "  from TZ_GADMIN ";
            sql += " where gadmin='" + v_oldadmin + "'";
            sql += "   and padmin='" + v_admin + "'";
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                v_cnt = ls.getInt(1);
            }
            ls.close();

            sql = "select to_char(max(NVL(SUBSTR(gadmin,3,4),0)+1),'00') count ";
            sql += "  from TZ_GADMIN ";
            sql += " where substring(gadmin,1,2)='" + v_admin + "'";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_count = ls.getString(1);
            }
            ls.close();

            if (v_cnt > 0) {
                v_gadmin = v_oldadmin;
            } else {
                v_gadmin = v_admin + v_count;
            }

            sql = " update TZ_GADMIN set gadmin = ? ,";
            sql += "                      gadminnm = ?,";
            sql += "                      comments = ?,";
            sql += "                      isneedsubj = ?,";
            sql += "                      isneedcomp = ?,";
            sql += "                      isneeddept = ?,";
            sql += "                      isneedgrcode = ?,";
            sql += " 					  applevel = ?,";
            sql += "                      padmin = ? ,";
            sql += "                      seq = ?,";
            sql += "                      luserid= ? ,";
            sql += "                      ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where gadmin  = ?";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_gadmin);
            pstmt.setString(2, v_gadminnm);
            pstmt.setString(3, v_comments);
            pstmt.setString(4, v_isneedsubj);
            pstmt.setString(5, v_isneedcomp);
            pstmt.setString(6, v_isneeddept);
            pstmt.setString(7, v_isneedgrcode);
            pstmt.setInt(8, v_applevel);
            pstmt.setString(9, v_admin);
            pstmt.setInt(10, v_gadminseq);
            pstmt.setString(11, s_userid);
            pstmt.setString(12, v_oldadmin);

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
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
        // int v_cnt = 0; // �ߺ�üũ

        String v_userid = box.getString("p_userid");
        String v_gadmin = box.getString("p_gadmin");
        String v_gadminview = box.getString("p_gadminview");
        String v_isneedgrcode = "";
        String v_isneedsubj = "";
        String v_isneedcomp = "";
        String v_isneeddept = "";

        // �ڵ���� (�����ڵ� + "," + �����׷��ʿ俩��  + "," + �����ڵ��ʿ俩�� + "," + ȸ���ڵ��ʿ俩�� + "," + �μ��ڵ��ʿ俩��)
        StringTokenizer st = new StringTokenizer(v_gadminview, ",");
        // int j = 0;

        // �׷�/����/ȸ��/�μ� �ʿ俩��
        if (st.hasMoreElements()) {
            v_gadmin = (String) st.nextToken();
            v_isneedgrcode = (String) st.nextToken();
            v_isneedsubj = (String) st.nextToken();
            v_isneedcomp = (String) st.nextToken();
            v_isneeddept = (String) st.nextToken();
        }

        // �׷��ڵ� - v_gadmin(H)
        Vector<?> v_vgrcode = box.getVector("p_grcode");
        String v_sgrcode = "";

        // �����ڵ� - v_gadmin(F,P)
        Vector<?> v_vsubj = box.getVector("p_subj");
        String v_ssubj = "";

        // ȸ���ڵ� - v_gadmin(K)
        Vector<?> v_vcomp = box.getVector("p_company");
        String v_scomp = "";

        // �μ��ڵ� - v_gadmin(K)
        Vector<?> v_vdept = box.getVector("p_dept");
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

    /**
     * ���� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     */
    public int deleteManager(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////  

            // ���Ѱ��� ���̺��� ����(�⺻ ������ ������ �� ����.)
            sql = " delete from TZ_GADMIN      ";
            sql += "  where gadmin  = ?          ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_gadmin);
            isOk = pstmt.executeUpdate();

            if (isOk > 0)
                connMgr.commit();
            else
                connMgr.rollback();

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
        return isOk;
    }

    /**
     * �ش� ������ �޴��� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     */
    public int deleteMenu(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        String v_gadmin = box.getString("p_gadmin");

        try {
            connMgr = new DBConnectionManager();

            // ���Ѱ��� ���̺��� ����(�⺻ ������ ������ �� ����.)
            sql = " delete from TZ_MENUAUTH      ";
            sql += "  where gadmin  = ?          ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_gadmin);
            isOk = pstmt.executeUpdate();

            if (isOk > 0)
                connMgr.commit();
            else
                connMgr.rollback();

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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /*---------------------------------------------------------------------------------------------------------------------------*/
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

}
