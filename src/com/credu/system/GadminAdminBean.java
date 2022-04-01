//**********************************************************
//  1. ��      ��: �����ڱ���
//  2. ���α׷��� : GadminAdminBean.java
//  3. ��      ��: �����ڱ��� ����
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 16
//  7. ��      ��:
//**********************************************************

package com.credu.system;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.StringManager;

/**
 * ȸ�������з� ����(ADMIN)
 * 
 * @date : 2003. 7
 * @author : s.j Jung
 */
public class GadminAdminBean {

    public GadminAdminBean() {
    }

    /**
     * �����ڵ� �˻� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �����ڵ� �˻� ����Ʈ
     */
    /*
     * public ArrayList searchGadmin(RequestBox box) throws Exception {
     * DBConnectionManager connMgr = null; ListSet ls = null; ArrayList list =
     * null; String sql = ""; GadminData data = null;
     * 
     * String v_search = box.getString("p_gubun"); String v_searchtext =
     * box.getString("p_key1"); int v_pageno = box.getInt("p_pageno");
     * 
     * try { connMgr = new DBConnectionManager();
     * 
     * list = new ArrayList();
     * 
     * sql =
     * " select gadmin, control, gadminnm, comments, isneedsubj, isneedcomp from TZ_GADMIN   "
     * ;
     * 
     * if ( !v_searchtext.equals("")) { // �˻�� ������ if
     * (v_search.equals("gadmin")) { // �ڵ�� �˻��Ҷ� sql += " where comp like   " +
     * StringManager.makeSQL("%" + v_searchtext + "%"); } else if
     * (v_search.equals("gadminnm")) { // ��Ī���� �˻��Ҷ� sql += " where deptnm like "
     * + StringManager.makeSQL("%" + v_searchtext + "%"); } } sql +=
     * "   order by gadmin asc     ";
     * 
     * ls = connMgr.executeQuery(sql);
     * 
     * ls.setPageSize(10); // �������� row ������ �����Ѵ� ls.setCurrentPage(v_pageno); //
     * ������������ȣ�� �����Ѵ�. int total_page_count = ls.getTotalPage(); // ��ü ������ ����
     * ��ȯ�Ѵ� int total_row_count = ls.getTotalCount(); // ��ü row ���� ��ȯ�Ѵ�
     * 
     * while (ls.next()) { data = new GadminData();
     * 
     * data.setGadmin(ls.getString("gadmin"));
     * data.setGadminnm(ls.getString("gadminnm"));
     * data.setControl(ls.getString("control"));
     * data.setComments(ls.getString("comments"));
     * data.setIsneedsubj(ls.getString("isneedsubj"));
     * data.setIsneedcomp(ls.getString("isneedcomp"));
     * data.setDispnum(total_row_count - ls.getRowNum() + 1);
     * data.setTotalPageCount(total_page_count);
     * 
     * list.add(data); } } catch (Exception ex) {
     * ErrorManager.getErrorStackTrace(ex, box, sql); throw new
     * Exception("sql = " + sql + "\r\n" + ex.getMessage()); } finally { if(ls
     * != null) { try { ls.close(); }catch (Exception e) {} } if(connMgr !=
     * null) { try { connMgr.freeConnection(); }catch (Exception e10) {} } }
     * return list; }
     */

    /**
     * �����ڵ� ����Ʈ�ڽ�
     * 
     * @param name,selected,event,allcheck ����Ʈ�ڽ���,���ð�,�̺�Ʈ��,��ü����(0 -��ü����, 1 -
     *        ��ü����)
     * @return result �����ڵ� + "," + �����׷��ʿ俩�� + "," + �����ڵ��ʿ俩�� + "," + ȸ���ڵ��ʿ俩�� +
     *         "," + �μ��ڵ��ʿ俩��
     */
    public static String getGadminSelect(String name, String selected, String event) throws Exception {
        return getGadminSelect(name, selected, event, 1);
    }

    public static String getGadminSelect(String name, String selected, String event, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;
        String v_value = "";

        result = "  <SELECT name=" + name + " " + event + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>=== ��ü ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadmin, gadminnm, isneedgrcode, isneedsubj, isneedcomp,isneeddept,seq from tz_gadmin  ";
            sql += " where isview = 'Y'";
            sql += " order by seq asc,gadmin asc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GadminData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                data.setIsneedgrcode(ls.getString("isneedgrcode"));
                data.setIsneedsubj(ls.getString("isneedsubj"));
                data.setIsneedcomp(ls.getString("isneedcomp"));
                data.setIsneeddept(ls.getString("isneeddept"));
                v_value = data.getGadmin() + "," + data.getIsneedgrcode() + "," + data.getIsneedsubj() + "," + data.getIsneedcomp() + "," + data.getIsneeddept();
                result += " <option value=\"" + v_value + "\"";
                if (selected.equals(v_value)) {
                    result += " selected ";
                }
                result += ">" + data.getGadminnm() + "</option> \n";
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

    /**
     * �����ڵ� ����Ʈ�ڽ�(��������)
     * 
     * @param name,selected,event,allcheck ����Ʈ�ڽ���,���ð�,�̺�Ʈ��,��ü����(0 -��ü����, 1 -
     *        ��ü����)
     * @return result �����ڵ� + "," + �����ְ��ʿ俩�� + "," + �����ڵ��ʿ俩�� + "," + ȸ���ڵ��ʿ俩�� +
     *         "," + �μ��ڵ��ʿ俩��
     */
    public static String getGadminSelectNop(String name, String selected, String event) throws Exception {
        return getGadminSelectNop(name, selected, event, 1);
    }

    public static String getGadminSelectNop(String name, String selected, String event, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;
        String v_value = "";

        result = "  <SELECT name=" + name + " " + event + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>=== ��ü ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadmin, gadminnm, isneedgrcode, isneedsubj, isneedcomp,isneeddept, isneedoutcomp from tz_gadmin  ";
            sql += " where gadmin != 'P1'                                                          ";
            sql += " and  isview = 'Y'";
            sql += " order by gadmin asc                                                           ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GadminData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                data.setIsneedgrcode(ls.getString("isneedgrcode"));
                data.setIsneedsubj(ls.getString("isneedsubj"));
                data.setIsneedcomp(ls.getString("isneedcomp"));
                data.setIsneeddept(ls.getString("isneeddept"));
                data.setIsneedoutcomp(ls.getString("isneedoutcomp"));
                v_value = data.getGadmin() + "," + data.getIsneedgrcode() + "," + data.getIsneedsubj() + "," + data.getIsneedcomp() + "," + data.getIsneeddept() + "," + data.getIsneedoutcomp();
                result += " <option value=\"" + v_value + "\"";
                if (selected.equals(v_value)) {
                    result += " selected ";
                }
                result += ">" + data.getGadminnm() + "</option> \n";
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

    /**
     * �����ڵ� ����Ʈ�ڽ�(��������)
     * 
     * @param name,selected,event,allcheck ����Ʈ�ڽ���,���ð�,�̺�Ʈ��)
     * @return result �����ڵ�
     */
    public static String getGadminSelectTop(String name, String gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;
        String v_value = "";
        String v_padmin = "";

        result = "  <SELECT name=" + name + "> \n";

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadmin,";
            sql += "        gadminnm,";
            sql += "        isneedgrcode,";
            sql += "        isneedsubj,";
            sql += "        isneedcomp,";
            sql += "        isneeddept,";
            sql += "        padmin";
            sql += "   from tz_gadmin  ";
            sql += " where gadmin = 'A1'";
            sql += "    or gadmin='A2'";
            sql += "    or gadmin='C1'";
            sql += "    or gadmin='C2'";
            sql += "    or gadmin='F1'";
            sql += "    or gadmin='H1'";
            sql += "    or gadmin='K2'";
            sql += "    or gadmin='P1'";
            sql += " order by gadmin asc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GadminData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                data.setIsneedgrcode(ls.getString("isneedgrcode"));
                data.setIsneedsubj(ls.getString("isneedsubj"));
                data.setIsneedcomp(ls.getString("isneedcomp"));
                data.setIsneeddept(ls.getString("isneeddept"));
                data.setPadmin(ls.getString("padmin"));
                v_value = data.getGadmin() + "," + data.getIsneedgrcode() + "," + data.getIsneedsubj() + "," + data.getIsneedcomp() + "," + data.getIsneeddept();
                v_padmin = data.getPadmin();

                result += " <option value=\"" + v_value + "\"";
                if (gadmin.equals(v_padmin)) {
                    result += " selected ";
                }
                result += ">" + data.getGadminnm() + "</option> \n";
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

    /**
     * �����ڵ� ����Ʈ�ڽ�-�����ڵ常 ��ȸ(��������)
     * 
     * @param name,selected ����Ʈ�ڽ���,���ð�,�̺�Ʈ��)
     * @return result �����ڵ�
     */
    public static String getGadminSelectOnly(String name, String gadmin, String event) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;
        // String v_value  = "";
        String v_gadmin = "";

        result = "  <SELECT name=" + name + " " + event + "> \n";
        result += " <option value=userid";
        if (name.equals("") || name.equals("userid")) {
            result += " selected ";
        }
        result += ">��ڸ޴�����</option>\r\n";

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadmin, gadminnm from tz_gadmin  ";
            sql += " where isview = 'Y'";
            sql += " order by gadmin asc                     ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GadminData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                v_gadmin = data.getGadmin();

                result += " <option value=\"" + v_gadmin + "\"";
                if (gadmin.equals(v_gadmin)) {
                    result += " selected ";
                }
                result += ">" + data.getGadminnm() + "</option> \n";
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

    /**
     * �����ڵ� + �ʿ��ڵ忩��
     * 
     * @param gadmin gadmin code
     * @return result �����ڵ� + "," + �����׷��ʿ俩�� + "," + �����ڵ��ʿ俩�� + "," + ȸ���ڵ��ʿ俩�� +
     *         "," + �μ��ڵ��ʿ俩��
     */
    public static String getGadminIsNeed(String gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;

        result = gadmin;

        try {
            connMgr = new DBConnectionManager();

            sql = " select isneedgrcode, isneedsubj, isneedcomp,isneeddept, isneedoutcomp from tz_gadmin  ";
            sql += "  where gadmin = " + StringManager.makeSQL(gadmin);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new GadminData();

                data.setIsneedgrcode(ls.getString("isneedgrcode"));
                data.setIsneedsubj(ls.getString("isneedsubj"));
                data.setIsneedcomp(ls.getString("isneedcomp"));
                data.setIsneeddept(ls.getString("isneeddept"));
                data.setIsneedoutcomp(ls.getString("isneedoutcomp"));

                result += "," + data.getIsneedgrcode() + "," + data.getIsneedsubj() + "," + data.getIsneedcomp() + "," + data.getIsneeddept() + "," + data.getIsneedoutcomp();
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

        return result;
    }

    /**
     * ���Ѹ�
     * 
     * @param gadmin gadmin code
     * @return result ���Ѹ�
     */
    public static String getGadminName(String gadmin) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        // GadminData data = null;

        result = gadmin;

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadminnm from tz_gadmin ";
            sql += "  where gadmin = " + StringManager.makeSQL(gadmin);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getString("gadminnm");
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

        return result;
    }

    /**
     * �����ڵ� ����Ʈ�ڽ�(�ڱ� ���� ���Ѹ�)
     * 
     * @param name,selected,event,allcheck ����Ʈ�ڽ���,���ð�,�̺�Ʈ��, ����, ��ü����(0 -��ü����, 1
     *        - ��ü����)
     * @return result �����ڵ� + "," + �����׷��ʿ俩�� + "," + �����ڵ��ʿ俩�� + "," + ȸ���ڵ��ʿ俩�� +
     *         "," + �μ��ڵ��ʿ俩��
     */
    public static String getGadminSelectGrtype(String name, String selected, String event, String gadmin) throws Exception {
        return getGadminSelectGrtype(name, selected, event, gadmin, 1);
    }

    public static String getGadminSelectGrtype(String name, String selected, String event, String gadmin, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;
        String v_value = "";

        result = "  <SELECT name=" + name + " " + event + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>=== ��ü ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadmin, gadminnm, isneedgrcode, isneedsubj, isneedcomp,isneeddept,seq from tz_gadmin  ";
            sql += " where isview = 'Y'";
            if (!StringManager.substring(gadmin, 0, 1).equals("A")) {
                sql += "   and (gadmin = " + StringManager.makeSQL(gadmin);
                sql += "        or substring(gadmin,1,1)  > " + StringManager.makeSQL(StringManager.substring(gadmin, 0, 1)) + ") ";
            }

            sql += " order by seq asc,gadmin asc";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GadminData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                data.setIsneedgrcode(ls.getString("isneedgrcode"));
                data.setIsneedsubj(ls.getString("isneedsubj"));
                data.setIsneedcomp(ls.getString("isneedcomp"));
                data.setIsneeddept(ls.getString("isneeddept"));
                v_value = data.getGadmin() + "," + data.getIsneedgrcode() + "," + data.getIsneedsubj() + "," + data.getIsneedcomp() + "," + data.getIsneeddept();
                result += " <option value=\"" + v_value + "\"";
                if (selected.equals(v_value)) {
                    result += " selected ";
                }
                result += ">" + data.getGadminnm() + "</option> \n";
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

    /**
     * �����ڵ� ����Ʈ�ڽ�(��������)-(�ڱ� ���� ���Ѹ�)
     * 
     * @param name,selected,event,allcheck ����Ʈ�ڽ���,���ð�,�̺�Ʈ��,����,��ü����(0 -��ü����, 1 -
     *        ��ü����)
     * @return result �����ڵ� + "," + �����ְ��ʿ俩�� + "," + �����ڵ��ʿ俩�� + "," + ȸ���ڵ��ʿ俩�� +
     *         "," + �μ��ڵ��ʿ俩��
     */
    public static String getGadminSelectNopGrtype(String name, String selected, String event, String gadmin) throws Exception {
        return getGadminSelectNopGrtype(name, selected, event, gadmin, 1);
    }

    public static String getGadminSelectNopGrtype(String name, String selected, String event, String gadmin, int allcheck) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String result = null;
        String sql = "";
        GadminData data = null;
        String v_value = "";

        result = "  <SELECT name=" + name + " " + event + " > \n";

        if (allcheck == 1) {
            result += " <option value=''>=== ��ü ===</option> \n";
        }

        try {
            connMgr = new DBConnectionManager();

            sql = " select gadmin, gadminnm, isneedgrcode, isneedsubj, isneedcomp,isneeddept, isneedoutcomp from tz_gadmin  ";
            sql += " where gadmin != 'P1'                                                          ";
            sql += " and  isview = 'Y'";
            if (!StringManager.substring(gadmin, 0, 1).equals("A")) {
                sql += "   and (gadmin = " + StringManager.makeSQL(gadmin);
                sql += "        or substring(gadmin,1,1)  > " + StringManager.makeSQL(StringManager.substring(gadmin, 0, 1)) + ") ";
            }
            sql += " order by gadmin asc                                                           ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new GadminData();

                data.setGadmin(ls.getString("gadmin"));
                data.setGadminnm(ls.getString("gadminnm"));
                data.setIsneedgrcode(ls.getString("isneedgrcode"));
                data.setIsneedsubj(ls.getString("isneedsubj"));
                data.setIsneedcomp(ls.getString("isneedcomp"));
                data.setIsneeddept(ls.getString("isneeddept"));
                data.setIsneedoutcomp(ls.getString("isneedoutcomp"));
                v_value = data.getGadmin() + "," + data.getIsneedgrcode() + "," + data.getIsneedsubj() + "," + data.getIsneedcomp() + "," + data.getIsneeddept() + "," + data.getIsneedoutcomp();
                result += " <option value=\"" + v_value + "\"";
                if (selected.equals(v_value)) {
                    result += " selected ";
                }
                result += ">" + data.getGadminnm() + "</option> \n";
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
