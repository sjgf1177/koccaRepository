//**********************************************************
//1. ��      ��: HomePageMileageBean ����
//2. ���α׷��� : HomePageMileageBean.java
//3. ��      ��: HomePage FAQ ����
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��: �ϰ��� 2006.01.05
//7. ��      ��: 
//**********************************************************

package com.credu.homepage;

import java.util.ArrayList;

import com.credu.common.GetCodenm;
import com.credu.library.BoardPaging;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * FAQ ī�װ� ����(ADMIN)
 * 
 * @date : 2003. 7
 * @author : s.j Jung
 */
public class HomePageMileageBean {

    public HomePageMileageBean() {
    }

    /**
     * ���� �� ���ϸ���
     * 
     * @param box receive from the form object and session
     * @return int �� ���ϸ���.
     * @throws Exception
     */
    public static int TotalMileage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        int result = 0;
        String v_userid = box.getSession("userid");
        String v_grtype = box.getSession("tem_grcode");

        // v_grtype ó�� �������� �ӽ�.
        v_grtype = GetCodenm.get_grtype(box, v_grtype);

        try {
            connMgr = new DBConnectionManager();

            sql = " Select NVL(sum(point),0) as totalmileage  From TZ_MEMBER_MILEAGE ";
            sql += " Where grtype = '" + v_grtype + "' and userid = '" + v_userid + "'";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                result = ls.getInt("totalmileage");
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
     * ���ϸ��� ��� ���� List
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���ϸ��� ��� ����
     * @throws Exception
     */
    public ArrayList<DataBox> MileageList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";

        int v_pageno = box.getInt("p_pageno");
        int row = 15;

        String v_userid = box.getSession("userid");
        String v_grtype = box.getSession("tem_grcode");

        v_grtype = GetCodenm.get_grtype(box, v_grtype);

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql = " Select point, ldate, usememo ";
            body_sql += " From tz_member_mileage ";
            body_sql += "	Where grtype = '" + v_grtype + "' and userid = '" + v_userid + "'";
            order_sql += " Order By ldate DESC";

            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);

            System.out.println("MileageList.sql = " + sql);

            ls.setPageSize(row); //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); // ������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage(); // ��ü ������ ���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
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
     * ���ϸ��� ��� ���� List
     * 
     * @param box receive from the form object and session
     * @return ArrayList ���ϸ��� ��� ����
     * @throws Exception
     */
    public ArrayList<DataBox> MileageTopList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        String v_userid = box.getSession("userid");
        String v_grtype = box.getSession("tem_grcode");

        v_grtype = GetCodenm.get_grtype(box, v_grtype);

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select * from ( select rownum rnum, point, ldate, usememo ";
            sql += " From tz_member_mileage ";
            sql += "	Where grtype = '" + v_grtype + "' and userid = '" + v_userid + "'";
            sql += " Order By ldate DESC) where rnum < 5";

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

}