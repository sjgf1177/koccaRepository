// **********************************************************
// 1. 제 목: 직훈인성코드
// 2. 프로그램명: SelectPersonalattBean.java
// 3. 개 요: 직훈인성코드 셀렉트
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 이정한 2003. 4. 26
// 7. 수 정: 이정한 2003. 4. 26
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
 * 직훈인성코드 셀렉트 Class
 * 
 * @date : 2003. 5
 * @author : j.h. lee
 */
public class SelectPersonalattBean {

    /**
     * 직훈인성코드 대분류 SELECT
     * 
     * @param box receive from the form object and session
     * @return ArrayList 직훈인성코드 대분류 리스트
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
     * 직훈인성코드 중분류 SELECT
     * 
     * @param box receive from the form object and session
     * @return ArrayList 직훈인성코드 중분류 리스트
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
