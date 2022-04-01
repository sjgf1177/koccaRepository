/**
 * FileName : KoccaTagBean.java Comment :
 * 
 * @version : 1.0
 * @author : kocca
 * @date : 2015. 2. 5.
 */
package com.credu.common;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;

/**
 * @author kocca
 * 
 */
public class KoccaTagBean {

    /**
     * sqlList.properties 파일에 저장된 query를 이용하여 조회한다.
     * 
     * @param sqlID
     * @return
     * @throws Exception
     */
    public static ArrayList<DataBox> getSelectBoxListBySqlID(String sqlID) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        DataBox dbox = null;
        StringBuilder sql = new StringBuilder();

        ConfigSet cs = new ConfigSet();

        try {
            connMgr = new DBConnectionManager();

            sql.append(cs.getProperty(sqlID));

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = new DataBox("");
                dbox.put("d_code", ls.getString(1));
                dbox.put("d_codenm", ls.getString(2));

                list.add(dbox);
            }

            // list = ls.getAllDataList();
        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                ls.close();
                ls = null;
            }
            if (connMgr != null) {
                connMgr.freeConnection();
                connMgr = null;
            }
        }
        return list;
    }

    /**
     * sqlList.properties 파일에 저장된 query를 이용하여 조회한다. 하나 이상의 파라미터 값은 ','로 구분한다.
     * 
     * @param sqlID
     * @return
     * @throws Exception
     */
    public static ArrayList<DataBox> getSelectBoxListBySqlID(String sqlID, String param) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        DataBox dbox = null;
        StringBuilder sql = new StringBuilder();

        ConfigSet cs = new ConfigSet();

        int index = 1;

        try {
            connMgr = new DBConnectionManager();

            if (param.lastIndexOf(",") == param.length() - 1) {
                return null;
            } else {
                sql.append(cs.getProperty(sqlID));

                pstmt = connMgr.prepareStatement(sql.toString());

                String[] paramArr = param.split(",");

                for (int i = 0; i < paramArr.length; i++) {
                    String temp = paramArr[i];
                    pstmt.setString(index++, temp);
                }

                ls = new ListSet(pstmt);

                while (ls.next()) {
                    dbox = new DataBox("");
                    dbox.put("d_code", ls.getString(1));
                    dbox.put("d_codenm", ls.getString(2));

                    list.add(dbox);
                }
            }
            // list = ls.getAllDataList();
        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                ls.close();
                ls = null;
            }

            if (pstmt != null) {
                pstmt.close();
                pstmt = null;
            }

            if (connMgr != null) {
                connMgr.freeConnection();
                connMgr = null;
            }
        }
        return list;
    }

    /**
     * 코드 구분자 값을 직접 입력받아 코드 테이블에서 목록을 조회한다. 이 메서드는 코드 테이블(TZ_CODE)내에 존재하는 자료만 사용 가능하다.
     * 
     * @param sqlID
     * @return
     * @throws Exception
     */
    public static ArrayList<DataBox> getSelectBoxListByCode(String codeGubun) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();
        StringBuilder sql = new StringBuilder();

        try {
            connMgr = new DBConnectionManager();

            sql.append("SELECT  CODE    \n");
            sql.append("    ,   CODENM  \n");
            sql.append("  FROM  TZ_CODE \n");
            sql.append(" WHERE  GUBUN = '").append(codeGubun).append("' \n");
            sql.append(" ORDER  BY CODE \n");

            ls = connMgr.executeQuery(sql.toString());

            list = ls.getAllDataList();
        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (ls != null) {
                ls.close();
                ls = null;
            }
            if (connMgr != null) {
                connMgr.freeConnection();
                connMgr = null;
            }
        }
        return list;
    }

}
