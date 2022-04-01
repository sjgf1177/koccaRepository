// **********************************************************
// 1. �� �� : ����ü�赵 ����
// 2. ���α׷��� : CourseMapBean.java
// 3. �� �� : ����ü�赵 ����
// 4. ȯ �� : JDK 1.3
// 5. �� �� : 1.0
// 6. �� �� : �̿��� 2005. 08. 02
// 7. �� �� :
// **********************************************************

package com.credu.homepage;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class CourseMapBean {
    // private ConfigSet config;
    // private int row;
    // private int gubun = 5;
    public CourseMapBean() {
        try {
            // config = new ConfigSet();
            // row = Integer.parseInt(config.getProperty("page.bulletin.row")); // �� ����� �������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Faqȭ�� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList Faq ����Ʈ
     **/
    public ArrayList<DataBox> selectTabName(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = " select tabseq, tabseqname     ";
            sql += "   from TZ_COURSEMAP   ";
            sql += "   order by tabseq asc";

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
     * ���õ� �ڷ�� �Խù� �󼼳��� select
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public DataBox selectImgFile(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        int v_tabseq = Integer.parseInt(box.getStringDefault("p_tabseq", "1"));
        try {

            connMgr = new DBConnectionManager();
            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq,realpdf,savepdf,realsfile,savesfile,reallfile,savelfile";
            sql += " from TZ_COURSEMAP";
            sql += " where tabseq = " + v_tabseq;
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                // ------------------- 2003.12.25 ���� -------------------------------------------------------------------
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
