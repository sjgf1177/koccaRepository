//*****************N*****************************************
//1. 제      목: 관리자권SER한
//2. 프로그램명 : GadminAdminBean.java
//3. 개      요: 관리자권한 관리
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성: 정상진 2003. 7. 16
//7. 수      정:
//**********************************************************

package com.credu.system;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.common.GetCodenm;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * 회사조직분류 관리(ADMIN)
 * 
 * @date : 2003. 7
 * @author : s.j Jung
 */
public class DiscountBean {

    public DiscountBean() {
    }

    /**
     * 할인률 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList 권한코드 검색 리스트
     */

    @SuppressWarnings("unchecked")
    public ArrayList searchDiscount(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        PreparedStatement pstmt = null;
        DataBox dbox = null;

        StringBuffer sql = new StringBuffer();
        StringBuffer sql_insert = new StringBuffer();

        String s_grcode = box.getSession("tem_grcode");
        String v_grcode = box.getStringDefault("s_grcode", s_grcode);
        String v_subjcnt = box.getStringDefault("p_subjcnt", "2"); // 기본값 2개

        if (v_grcode.equals("ALL")) {
            v_grcode = s_grcode;
            box.put("s_grcode", v_grcode);
        }

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append(" SELECT GRCODE, SUBJCNT, DISCOUNT, LUSERID, LDATE FROM TZ_DISCOUNT \n");
            sql.append(" WHERE GRCODE = '" + v_grcode + "' ");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

            connMgr.commit();

            sql.setLength(0);

            if (list.size() == 0) {

                sql_insert.append("INSERT INTO TZ_DISCOUNT (GRCODE, SUBJCNT, DISCOUNT, LUSERID, LDATE, SETTINGTYPE, SETTINGTYPENM) \n");
                if (v_grcode.equals("N000001")) { // 통합 GRTYPE=>GRCODE, GUBUN=>우수회원기준

                    sql_insert.append("SELECT '" + v_grcode + "',	'" + v_subjcnt + "',	0	, '" + box.getSession("userid")
                            + "', TO_CHAR(sysdate,'yyyymmddhh24miss'), '00001', '우수회원 할인율 관리'     FROM DUAL \n");

                } else { // ASP

                    sql_insert.append("SELECT '" + v_grcode + "',	'0',	0	, '" + box.getSession("userid")
                            + "', TO_CHAR(sysdate,'yyyymmddhh24miss'), '00002', '기업회원 할인율 관리'     FROM DUAL \n");

                }

                pstmt = connMgr.prepareStatement(sql_insert.toString());

                pstmt.executeUpdate();

                connMgr.commit();

                sql_insert.setLength(0);

            }

            sql.append(" SELECT SUBJCNT,DISCOUNT FROM TZ_DISCOUNT ");
            sql.append(" WHERE GRCODE = '" + v_grcode + "' ");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

            sql.setLength(0);

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

    @SuppressWarnings("unchecked")
    public int update(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        int isOk = 0;

        String v_userid = box.getSession("userid");

        String s_grcode = box.getSession("tem_grcode");
        String v_grcode = box.getStringDefault("s_grcode", s_grcode);

        if (v_grcode.equals("ALL")) {
            v_grcode = s_grcode;
            box.put("s_grcode", v_grcode);
        }

        String v_gubun = null;
        String v_discount = null;

        try {

            connMgr = new DBConnectionManager();

            if (v_grcode.equals("N000001")) {
                // 통합
                v_gubun = box.getString("p_subjcnt");
                v_discount = box.getString("p_discount");

                sql.append(" UPDATE TZ_DISCOUNT ");
                sql.append(" SET DISCOUNT = ? ");
                sql.append(" ,SUBJCNT = ? ");
                sql.append(" ,LDATE = TO_CHAR(sysdate,'yyyymmddhh24miss') ");
                sql.append(" ,LUSERID = ? ");
                sql.append(" WHERE GRCODE = ? ");

                pstmt = connMgr.prepareStatement(sql.toString());

                pstmt.setString(1, v_discount);
                pstmt.setString(2, v_gubun);
                pstmt.setString(3, v_userid);
                pstmt.setString(4, v_grcode);

                isOk = pstmt.executeUpdate();

                sql.setLength(0);

            } else {
                // ASP
                v_discount = box.getString("p_discount");

                sql.append(" UPDATE TZ_DISCOUNT ");
                sql.append(" SET DISCOUNT = ? ");
                sql.append(" ,LDATE = TO_CHAR(sysdate,'yyyymmddhh24miss') ");
                sql.append(" ,LUSERID = ? ");
                sql.append(" WHERE GRCODE = ? ");

                pstmt = connMgr.prepareStatement(sql.toString());

                pstmt.setString(1, v_discount);
                pstmt.setString(2, v_userid);
                pstmt.setString(3, v_grcode);

                isOk = pstmt.executeUpdate();

                sql.setLength(0);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql ->" + sql + "\r\n" + ex.getMessage());
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
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }

        return isOk;
    }

    public static int returnDiscount(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        // ArrayList list = null;
        String sql = "";
        int result = 0;

        String v_userid = box.getSession("userid");
        String v_grcode = box.getSession("tem_grcode");
        String v_grtype = GetCodenm.get_grtype(box, v_grcode);

        try {
            connMgr = new DBConnectionManager();

            sql = " Select d.discount ";
            sql += " From TZ_DISCOUNT d ";
            sql += "	join tz_member m on m.membergubun = d.gubun ";
            sql += " Where userid = '" + v_userid + "' ";
            sql += "	and d.grtype='" + v_grtype + "'";

            ls = connMgr.executeQuery(sql);
            if (ls.next())
                result = ls.getInt("discount");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->" + sql + "\r\n" + ex.getMessage());
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

}
