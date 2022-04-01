//**********************************************************
//1. ��      ��: MYCLASS USER BEAN
//2. ���α׷���: KOpenOfflineBean.java
//3. ��      ��: �������ΰ��� ����� bean
//4. ȯ      ��: JDK 1.4
//5. ��      ��: 1.0
//6. ��      ��: lyh
//**********************************************************
package com.credu.homepage;

import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

public class KOpenOfflineBean {

    private ConfigSet config;
    private int row;

    public KOpenOfflineBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        �� ����� �������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �������ΰ��� ����Ʈ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectOpenOffline(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String order_sql = "";
        String count_sql = "";
        DataBox dbox = null;
        int v_pageno = box.getInt("p_pageno");
        String v_grcode = box.getSession("tem_grcode");
        // String v_user_id = box.getSession("userid");

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            // �������ΰ��� �ڷ� ��������
            head_sql = " select a.seq, a.grcode, b.codenm,  a.subjnm , a.dday, a.readcnt , a.ldate   \n";
            body_sql += "  from  tz_offlinesubj a   , tz_code b  \n";
            body_sql += " where a.subjgubun = b.code  \n";
            body_sql += " and  a.useYn= 'Y'    \n";
            body_sql += " and  a.grcode='" + v_grcode + "'   \n";
            order_sql += " order by a.seq desc ";

            sql = head_sql + body_sql + order_sql;
            System.out.println("�������ΰ��� ����Ʈ :" + sql);
            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);

            row = 10; //�ϵ��ڵ�
            ls.setPageSize(row); //  �������� row ���� 7 �� �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); // ������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage(); // ��ü ������ ���� ��ȯ�Ѵ�

            while (ls.next()) {

                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(total_row_count));

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
     * �������ΰ��� �� ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectOpenOfflineDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String sql2 = "";
        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        // String v_user_id = box.getSession("userid");
        String v_seq = box.getString("seq");

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //��ȸ�� ����
            sql2 = " update   tz_offlinesubj  " + " set  readcnt = (isnull(readcnt,0) + 1)    " + "  where grcode = '" + v_grcode + "'  " + "  and  seq = " + v_seq + "    \n";

            connMgr.executeUpdate(sql2);

            // �������ΰ��� ���ڷ� ��������
            sql = "select a.seq, b.codenm,  a.subjseq, a.subjnm,   a.ldate,  a.readcnt,  \n" + " a.tuserid, a.tname, a.dday, a.starttime, a.endtime, a.place, a.limitmember, a.target, a.content    \n" + " from  tz_offlinesubj  a, tz_code b    \n "
                    + " where a.subjgubun = b.code  \n" + " and a.useYn= 'Y'   " + "  and a.seq = '" + v_seq + "'    \n" + "  and a.grcode = '" + v_grcode + "'  ";

            ls = connMgr.executeQuery(sql);
            System.out.println("selectOpenOfflineDetail: " + sql);

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
     * �������ΰ��� ������ ��������
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectOpenOfflineDetailPre(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        // String v_user_id = box.getSession("userid");
        int v_seq = StringManager.toInt(box.getString("seq"));

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            // ������ �������� 
            sql = " 	select * from ( select rownum rnum,   seq, grcode,  subjnm  \n" + "  from  tz_offlinesubj   \n" + " where useYn= 'Y'  \n" + "  and  grcode='" + v_grcode + "'   \n" + "  and seq < " + v_seq + " \n"
                    + " 	order by seq desc ) where rnum < 2";

            System.out.println("selectOpenOfflineDetailPre: " + sql);
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
     * ������ ��������
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectOpenOfflineDetailNext(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        // String v_user_id = box.getSession("userid");
        int v_seq = StringManager.toInt(box.getString("seq"));

        try {
            list = new ArrayList<DataBox>();
            connMgr = new DBConnectionManager();

            //�������ΰ��� ������ �������� 
            sql = " 	select * from ( select rownum rnum,  seq, grcode,  subjnm   \n" + "  from  tz_offlinesubj   \n" + " where useYn= 'Y'  \n" + "  and  grcode='" + v_grcode + "'   \n" + "  and seq > " + v_seq + "  \n"
                    + "  order by seq desc) where rnum < 2";
            System.out.println("selectOpenOfflineDetailNext: " + sql);

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
     * �������ΰ��� �˻�
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectOpenOfflineSearch(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String order_sql = "";
        String count_sql = "";
        DataBox dbox = null;

        String v_grcode = box.getSession("tem_grcode");
        // String v_user_id = box.getSession("userid");
        int v_pageno = box.getInt("p_pageno");

        String v_select = box.getString("select"); // �˻�����
        String v_search = (box.getString("search")).trim(); // �˻���

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            // �������ΰ��� �ڷ� ��������
            head_sql = " select a.seq, b.codenm,  a.subjnm , a.dday, a.readcnt , a.ldate   \n";
            body_sql += "  from  tz_offlinesubj a   , tz_code b  \n";
            body_sql += " where a.subjgubun = b.code  \n";
            body_sql += " and  a.useYn= 'Y'    \n";
            body_sql += " and  a.grcode='" + v_grcode + "'   \n";

            if (v_select.equals("title") && !v_search.equals("")) {
                body_sql += " and  a.subjnm like '%'||" + StringManager.makeSQL(v_search) + "||'%' ";

            } else if (v_select.equals("content") && !v_search.equals("")) {
                body_sql += " and  a.content like '%'||" + StringManager.makeSQL(v_search) + "||'%' ";

            } else if (v_select.equals("all") && !v_search.equals("")) {
                body_sql += "and ( a.subjnm like '%'||" + StringManager.makeSQL(v_search) + "||'%' ";
                body_sql += " or  a.content like '%'||" + StringManager.makeSQL(v_search) + "||'%' ) ";
            }
            order_sql = " order by a.seq desc  ";

            sql = head_sql + body_sql + order_sql;
            System.out.println("KOpenOfflineBean �������ΰ��� �˻� selectOpenOfflineSearch:" + sql);

            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);
            row = 10; //�ϵ��ڵ�		
            ls.setPageSize(row); //  �������� row ���� 10 �� �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); // ������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage(); // ��ü ������ ���� ��ȯ�Ѵ�

            while (ls.next()) {

                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_total_row_count", new Integer(total_row_count));

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

}
