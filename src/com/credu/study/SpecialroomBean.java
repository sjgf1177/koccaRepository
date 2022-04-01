//**********************************************************
//  1. ��      ��: SpecialroomBean BEAN
//  2. ���α׷���: SpecialroomBean.java
//  3. ��      ��: Ư���� ����� bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: lyh
//**********************************************************
package com.credu.study;

import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

public class SpecialroomBean {

    private ConfigSet config;
    private int row;

    public SpecialroomBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row")); //�� ����� �������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    /**
     * Ư���� ����Ʈ ��ȸ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSpecialroom(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String count_sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        String order_sql1 = "";
        DataBox dbox = null;

        // String v_tem_grcode = box.getSession("tem_grcode");
        // String v_user_id = box.getSession("userid");
        int v_pageno = box.getInt("p_pageno");

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            // Ư���� �ڷ� ��������
            head_sql1 += " select a.seq, a.grcode, b.codenm,  a.subjnm , a.readcnt , a.ldate   \n";
            body_sql1 += "  from  tz_offlinesubj a   , tz_code b  \n";
            body_sql1 += " where a.subjgubun = b.code  \n";
            body_sql1 += " and  a.useYn= 'Y'    \n";
            body_sql1 += " and  a.grcode='N000002'   \n"; //�������� �ϵ��ڵ�
            order_sql1 += " order by  a.seq desc  \n";

            sql = head_sql1 + body_sql1 + order_sql1;
            ls = connMgr.executeQuery(sql);
            //System.out.println("Ư���� ����Ʈ selectSpecialroom: "+sql);

            count_sql1 = "select count(*)" + body_sql1;

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1);

            row = 10; //�ϵ��ڵ�
            ls.setPageSize(row); //�������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); //������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage(); //��ü ������ ���� ��ȯ�Ѵ�

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

    /**
     * Ư���� �� ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSpecialroomDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        String sql2 = "";
        DataBox dbox = null;

        String v_grcode = box.getString("grcode");
        // String v_user_id = box.getSession("userid");
        String v_seq = box.getString("seq");

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //��ȸ�� ����
            sql2 = " update   tz_offlinesubj  " + " set  readcnt = (isnull(readcnt,0) + 1)    " + "  where  grcode='N000002' " + "  and  seq = " + v_seq + "    \n";

            connMgr.executeUpdate(sql2);

            // Ư���� �ڷ� ��������
            sql = "select a.seq, b.codenm,  a.subjseq, a.subjnm, a.ldate,  a.readcnt,  \n" + " a.tuserid, a.tname, a.dday, a.starttime, a.endtime, a.place, a.limitmember, a.target , a.content    \n" + " from  tz_offlinesubj a , tz_code b    \n "
                    + " where a.subjgubun = b.code  \n" + " and a.useYn= 'Y'   " + "  and a.seq = " + v_seq + "    \n" + "  and a.grcode = '" + v_grcode + "'  ";

            ls = connMgr.executeQuery(sql);
            //System.out.println("selectSpecialroomDetail: "+sql);

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
     * Ư���� ������ ��������
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSpecialroomDetailPre(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        // String v_user_id = box.getSession("userid");
        int v_seq = StringManager.toInt(box.getString("seq"));

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //Ư���� ������ �������� 
            sql = " 	select * from ( select rownum rnum,  seq, grcode,  subjnm   \n" + "  from  tz_offlinesubj   \n" + " where useYn= 'Y'  \n" + "  and  grcode='N000002'   \n" //���� ���Ӱ� ������ �����ڵ带 �������ʾ����Ƿ� �ϵ��ڵ���.
                    + "  and seq < " + v_seq + "  \n" + "	 order by seq	) where rnum < 2";

            ls = connMgr.executeQuery(sql);
            //System.out.println("selectSpecialroomDetailPre: "+sql);

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
     * Ư���� ������ ��������
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSpecialroomDetailNext(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        // String v_user_id = box.getSession("userid");
        int v_seq = StringManager.toInt(box.getString("seq"));

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            //Ư���� ������ �������� 
            sql = " 	select seq, grcode,  subjnm   \n" + "  from  tz_offlinesubj   \n" + " where useYn= 'Y'  \n" + "  and  grcode='N000002'   \n" //���� ���Ӱ� ������ �����ڵ带 �������ʾ����Ƿ� �ϵ��ڵ���.
                    + "  and seq > " + v_seq + "  and rownum = 1\n" + "	 order by seq			";

            ls = connMgr.executeQuery(sql);
            //System.out.println("selectSpecialroomDetailNext: "+sql);

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
     * Ư���� �˻����� ��������
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSpecialroomSearch(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = ""; //����
        String count_sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        String order_sql1 = "";
        DataBox dbox = null;

        // String v_user_id = box.getSession("userid");
        // int v_seq = StringManager.toInt(box.getString("seq"));
        int v_pageno = box.getInt("p_pageno");

        String v_select = box.getString("select"); // �˻�����
        String v_search = (box.getString("search")).trim(); // �˻���

        try {

            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            head_sql1 = " select a.seq, a.grcode, b.codenm,  a.subjnm , a.readcnt , a.ldate   \n";
            body_sql1 += "  from  tz_offlinesubj a   , tz_code b  \n";
            body_sql1 += " where a.subjgubun = b.code  \n";
            body_sql1 += " and  a.useYn= 'Y'    \n";
            body_sql1 += " and  a.useYn= 'Y'    \n";
            body_sql1 += " and  a.grcode='N000002'   \n"; //�������� �ϵ��ڵ�
            //+" and b.gubun='0035'   \n"	 //�����ϵ��ڵ�

            //���� �߰����� �����
            if (v_select.equals("title") && !v_search.equals("")) {
                body_sql1 += " and  a.subjnm like '%" + v_search + "%' ";

            } else if (v_select.equals("content") && !v_search.equals("")) {
                body_sql1 += " and  a.content like '%" + v_search + "%' ";

            } else if (v_select.equals("all") && !v_search.equals("")) {
                body_sql1 += "and ( a.subjnm like '%" + v_search + "%' ";
                body_sql1 += " or  a.content like '%" + v_search + "%' ) ";
            }

            order_sql1 += " order by  a.seq desc  \n";

            sql = head_sql1 + body_sql1 + order_sql1;
            //System.out.println("SpecialroomBean Ư���� �˻����� �������� selectSpecialroomSearch:"+sql);		

            ls = connMgr.executeQuery(sql);

            count_sql1 = "select count(*)" + body_sql1;

            //����¡����
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1);
            row = 10;
            ls.setPageSize(row); //�������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); //������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage(); //��ü ������ ���� ��ȯ�Ѵ�

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
