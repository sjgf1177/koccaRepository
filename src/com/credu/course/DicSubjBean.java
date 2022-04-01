//**********************************************************
//  1. ��      ��: ������ ����
//  2. ���α׷���: DicSubjBean.java
//  3. ��      ��: ������ ����(����)
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ���� 2004. 11. 14
//  7. ��      ��:
//**********************************************************

package com.credu.course;

import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;

public class DicSubjBean {
    private ConfigSet config;
    private int row;

    public DicSubjBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        �� ����� �������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ������ ȭ�� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ������ ����Ʈ
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectListDicSubj(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;
        int v_pageno = box.getInt("p_pageno");

        String v_gubun = "1";

        // String ss_upperclass = box.getString("s_upperclass"); // �����з�
        // String ss_middleclass = box.getString("s_middleclass"); // �����з�
        // sString ss_lowerclass = box.getString("s_lowerclass"); // �����з�
        String ss_subj = box.getString("s_subjcourse"); // �����ڵ�
        String v_searchtext = box.getString("p_searchtext");

        String v_groups = box.getStringDefault("p_group", ""); // ��,��,�� ....
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select a.seq seq, a.subj subj, b.subjnm subjnm, a.words words,           ";
            sql += "        a.descs descs, a.groups groups, a.luserid luserid, a.ldate ldate ";
            sql += "   from TZ_DIC a, TZ_SUBJ b, TZ_DICGROUP c                               ";
            sql += "  where a.subj = b.subj                                                  ";
            sql += "    and a.groups = c.groups                                              ";
            sql += "    and a.gubun = " + StringManager.makeSQL(v_gubun);

            if (!ss_subj.equals("")) { // ������ ������
                sql += "  and a.subj   = " + StringManager.makeSQL(ss_subj);
            }

            if (!v_searchtext.equals("")) { //    �˻�� ������
                sql += " and a.words like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }

            if (!v_groups.equals("")) { //    ���з��� �˻��Ҷ�
                sql += " and a.groups = " + StringManager.makeSQL(v_groups);
            }
            sql += " order by a.subj asc, a.groups asc, a.words asc ";

            ls = connMgr.executeQuery(sql);

            ls.setPageSize(row); //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno); //     ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount(); //     ��ü row ���� ��ȯ�Ѵ�
            box.put("total_row_count", String.valueOf(total_row_count)); // �� ������ BOX�� ����

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

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
     * ������ ȭ�� ����Ʈ(������ ���� ������)
     * 
     * @param box receive from the form object and session
     * @return ArrayList ������ ����Ʈ
     */
    public ArrayList<DataBox> selectListDicSubjStudy(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // 2005.12.06_�ϰ��� :  totalrowcount ���� ���� ��������
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        // String count_sql = "";
        DataBox dbox = null;
        // int v_pageno = box.getInt("p_pageno");

        String v_gubun = "1";

        // String p_subj = box.getString("p_subj"); // �����ڵ�
        String v_searchtext = box.getString("p_searchtext");

        String v_groups = box.getStringDefault("p_group", ""); // ��,��,�� ....

        String p_grocde = box.getString("p_grcode"); // �׷��ڵ� 2010.1.12 ���������� p_subj �� grcode ���� �޾Ƽ� �����ϴ� ������ �Ǿ��־
        // ������ subj �ڵ带 ���� �������� ������ �߻��Ͽ� grcode�� grcode �� subj �� subj ��
        // ������ ������, �׷��� tz_dic ���̺� subj Į������ ������ grcode �� ��� ����

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            // 2005.12.06_�ϰ��� : ������ ���� (tz_subj -> tz_grcode �� ��ü.)
            head_sql = " select a.seq seq, a.subj subj, b.grcodenm grcodenm, a.words words,  ";
            head_sql += "        a.descs as descs, a.groups groups, a.luserid luserid, a.ldate ldate ";
            body_sql += "   from TZ_DIC a, tz_grcode b, TZ_DICGROUP c ";
            body_sql += "  where a.subj = b.grcode ";
            body_sql += "    and a.groups = c.groups  ";
            body_sql += "    and a.gubun  = " + StringManager.makeSQL(v_gubun);
            body_sql += "    and a.subj   = " + StringManager.makeSQL(p_grocde); //p_subj --> p_grcode

            if (!v_searchtext.equals("")) { //    �˻�� ������
                body_sql += " and a.words like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }

            if (!v_groups.equals("")) { //    ���з��� �˻��Ҷ�
                body_sql += " and a.groups = " + StringManager.makeSQL(v_groups);
            }
            order_sql += " order by a.subj asc, a.groups asc, a.words asc ";

            sql = head_sql + body_sql + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            /*
             * 2010.1.12 ����¡ ���� count_sql= "select count(*) " + body_sql; int
             * total_row_count= BoardPaging.getTotalRow(connMgr, count_sql); //
             * ��ü row ���� ��ȯ�Ѵ�
             * 
             * ls.setPageSize(row); // �������� row ������ �����Ѵ�
             * ls.setCurrentPage(v_pageno, total_row_count); // ������������ȣ�� �����Ѵ�.
             * int total_page_count = ls.getTotalPage(); // ��ü ������ ���� ��ȯ�Ѵ� //int
             * total_row_count = ls.getTotalCount(); // ��ü row ���� ��ȯ�Ѵ�
             * box.put("total_row_count", String.valueOf(total_row_count)); // ��
             * ������ BOX�� ����
             */

            while (ls.next()) {
                dbox = ls.getDataBox();

                /*
                 * 2010.1.12 ����¡ ���� dbox.put("d_dispnum", new
                 * Integer(total_row_count - ls.getRowNum() + 1));
                 * dbox.put("d_totalpage", new Integer(total_page_count));
                 * dbox.put("d_rowcount", new Integer(row));
                 */

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
     * ������ ȭ�� ����Ʈ(��ü ������)
     * 
     * @param box receive from the form object and session
     * @return ArrayList ������ ����Ʈ
     */
    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectListDicTotal(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;
        int v_pageno = box.getInt("p_pageno");

        String v_gubun = "1";

        // String p_subj = box.getString("p_subj"); // �����ڵ�
        String v_searchtext = box.getString("p_searchtext");

        String v_groups = box.getStringDefault("p_group", ""); // ��,��,�� ....
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            //sql  = " select a.seq seq, a.subj subj, b.subjnm subjnm, a.words words,        ";
            sql = " select a.seq seq, a.subj subj, a.words words,                           ";
            sql += "        a.descs descs, a.groups groups, a.luserid luserid, a.ldate ldate ";
            //sql += "   from TZ_DIC a, TZ_SUBJ b, TZ_DICGROUP c                             ";
            sql += "   from TZ_DIC a, TZ_DICGROUP c                                          ";
            sql += "  where                                                                  ";
            //sql += "    a.subj = b.subj                                                    ";
            sql += "    a.groups = c.groups                                                  ";
            sql += "    and a.gubun  = " + StringManager.makeSQL(v_gubun);
            //sql += "    and a.subj   = " + StringManager.makeSQL(p_subj);

            if (!v_searchtext.equals("")) { //    �˻�� ������
                sql += " and a.words like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }

            if (!v_groups.equals("")) { //    ���з��� �˻��Ҷ�
                sql += " and a.groups = " + StringManager.makeSQL(v_groups);
            }
            sql += " order by a.groups asc, a.words asc ";
            System.out.println("sql_dictionary=" + sql);

            ls = connMgr.executeQuery(sql);

            ls.setPageSize(row); //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno); //     ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls.getTotalCount(); //     ��ü row ���� ��ȯ�Ѵ�
            box.put("total_row_count", String.valueOf(total_row_count)); // �� ������ BOX�� ����

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(row));

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
     * ������ �˾���(��ü ������)
     * 
     * @param box receive from the form object and session
     * @return ArrayList ������ ����Ʈ
     */
    public DataBox selectWordContent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        String p_subj = box.getString("p_subj");
        String p_gubun = box.getString("p_gubun");
        String p_seq = box.getString("p_seq");

        try {
            connMgr = new DBConnectionManager();

            sql = " select words, descs from tz_dic ";
            sql += " where ";
            sql += " subj  = " + SQLString.Format(p_subj);
            sql += " and gubun= " + SQLString.Format(p_gubun);
            sql += " and seq = " + SQLString.Format(p_seq);
            System.out.println(sql);

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

}
