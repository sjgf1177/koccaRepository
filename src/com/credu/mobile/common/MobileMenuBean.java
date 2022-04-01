package com.credu.mobile.common;

import java.sql.SQLException;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;

/**
 * 모바일 앱 메뉴 관리
 * @author saderaser
 *
 */
public class MobileMenuBean {
    public MobileMenuBean() {
    }

    /**
     * 모바일 앱에 사용할 메뉴 정보를 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList getMobileMenuList() throws Exception {
        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;

        ArrayList list = new ArrayList();
        DataBox dbox = null;

        StringBuilder sql = new StringBuilder();

        try {
            connMgr = new DBConnectionManager();

            sql.append("/* com.credu.mobile.common.MobileMenuBean getMobileMenuList (모바일앱 메뉴 조회) */\n");
            sql.append("SELECT  MENU_ID         \n");
            sql.append("    ,   MENU_NAME       \n");
            sql.append("    ,   MENU_URL        \n");
            sql.append("    ,   MENU_ICON       \n");
            sql.append("    ,   LOGIN_NEED_YN   \n");
            sql.append("  FROM  TZ_MOBILE_MENU  \n");
            sql.append(" WHERE  USE_YN = 'Y'    \n");
            sql.append(" ORDER  BY MENU_ID      \n");
            
            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (SQLException e) {
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n SQL : [\n" + sql.toString() + "]\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } catch (Exception e) {

            ErrorManager.getErrorStackTrace(e);
            throw new Exception("\n e.getMessage() : [\n" + e.getMessage() + "\n]");
        } finally {
//            if (pstmt != null) {
//                try {
//                    pstmt.close();
//                    pstmt = null;
//                } catch (Exception e) {
//                }
//            }
            if (ls != null) {
                try {
                    ls.close();
                    ls = null;
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                    connMgr = null;
                } catch (Exception e) {
                }
            }

        }

        return list;
    }

}
