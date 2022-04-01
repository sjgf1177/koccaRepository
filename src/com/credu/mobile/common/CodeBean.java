package com.credu.mobile.common;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class CodeBean {
    public CodeBean() {
    }

    /**
     * 공통코드 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectCommonCodeList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* 공통코드 목록 조회 */    \n");
            sql.append("SELECT  CODE    \n");
            sql.append("    ,   CODENM  \n");
            sql.append("  FROM  TZ_CODE \n");
            sql.append(" WHERE  GUBUN = '0107'  \n");
            sql.append("   AND  CODE <> '01'    \n");
            sql.append(" ORDER  BY CODE \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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
     * 분류별 교육그룹 목록을 조회한다.
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectGroupCodeList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;

        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        String groupType = box.getString("groupType");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("/* 분류별 교육그룹 목록 조회 */    \n");
            sql.append("SELECT  GRCODE || '|' || GR_PREFIX AS GRCODE    \n");
            sql.append("    ,   GRCODENM AS GRCODENM \n");
            sql.append("  FROM  TZ_GRCODE       \n");
            sql.append(" WHERE  GUBUN = '").append(groupType).append("' \n");
            sql.append("   AND  USEYN = 'Y'     \n");
            sql.append("   AND  CHKFINAL = 'Y'  \n");
            sql.append(" ORDER  BY GRCODENM     \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
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
