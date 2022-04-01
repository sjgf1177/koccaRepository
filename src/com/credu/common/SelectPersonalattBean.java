// **********************************************************
// 1. �� ��: �����μ��ڵ�
// 2. ���α׷���: SelectPersonalattBean.java
// 3. �� ��: �����μ��ڵ� ����Ʈ
// 4. ȯ ��: JDK 1.3
// 5. �� ��: 1.0
// 6. �� ��: ������ 2003. 4. 26
// 7. �� ��: ������ 2003. 4. 26
// **********************************************************

package com.credu.common;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

/**
 * �����μ��ڵ� ����Ʈ Class
 * 
 * @date : 2003. 5
 * @author : j.h. lee
 */
public class SelectPersonalattBean {

    /**
     * �����μ��ڵ� ��з� SELECT
     * 
     * @param box receive from the form object and session
     * @return ArrayList �����μ��ڵ� ��з� ����Ʈ
     */
    public ArrayList<DataBox> getUpperClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = "  select upperclass, personalnm ";
            sql += "    from TZ_PERSONALATT         ";
            sql += "   where middleclass = '0000'   ";
            sql += "     and lowerclass  = '0000'   ";
            sql += "   order by upperclass          ";

            pstmt = connMgr.prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

            ls = new ListSet(pstmt);

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

    /**
     * �����μ��ڵ� �ߺз� SELECT
     * 
     * @param box receive from the form object and session
     * @return ArrayList �����μ��ڵ� �ߺз� ����Ʈ
     */
    public ArrayList<DataBox> getMiddleClass(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            String ss_upperclass = box.getStringDefault("s_upperclass", "T001");

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "  select middleclass, personalnm ";
            sql += "    from TZ_PERSONALATT          ";
            sql += "   where upperclass =  " + SQLString.Format(ss_upperclass);
            sql += "     and middleclass != '0000'   ";
            sql += "     and lowerclass  = '0000'    ";
            sql += "   order by middleclass          ";

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
