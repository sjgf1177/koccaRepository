//**********************************************************
//  1. ��      ��: ���ϸ��� ����
//  2. ���α׷��� : MileageManager.java
//  3. ��      ��: ���ϸ��� ����
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7.  2
//  7. ��      ��:
//**********************************************************

package com.credu.common;

import java.sql.PreparedStatement;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.StringManager;

/**
 * ���ϸ��� ����(ADMIN)
 *
 * @date   : 2003. 7
 * @author : s.j Jung
 */
public class MileageManager {

    public MileageManager() {}


    /**
    * ���ϸ��� �⺻����
    * @param  code        ���ϸ��� �ڵ�
    * @return result      �⺻����
    * @throws Exception
    */
    public static int getMileageBase (String code) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        try {
            connMgr = new DBConnectionManager();

            sql  = " select NVL(base,0) base from TZ_MILEAGE        ";
            sql += "  where mileagecode = " + StringManager.makeSQL(code); 
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("base");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return result;
    }


    /**
    * ���ϸ��� ��ȿ�� üũ
    * @param  code        ���ϸ��� �ڵ�
    * @return result      0 : �ش� ���ϸ��� ������    1 : �ش縶�ϸ��� �����
    * @throws Exception
    */
    public static int getMileageUse (String code) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        String today = FormatDate.getDate("yyyyMMdd");
        int result = 0;


        try {
            connMgr = new DBConnectionManager();

            sql  = " select count(*) cnt from TZ_MILEAGE        ";
            sql += "  where mileagecode  = " + StringManager.makeSQL(code); 
            sql += "    and isuse    = 'Y'                      ";
            sql += "    and started <= " + StringManager.makeSQL(today); 
            sql += "    and ended   >= " + StringManager.makeSQL(today); 
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) {try {ls.close();} catch(Exception e){}}
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }

        return result;
    }


    /**
    * ���ϸ��� ����
    * @param  code        ���ϸ��� �ڵ�
    * @param  userid      USER ID
    * @return result      0 : insert or update success  1 : insert or update fail
    * @throws Exception
    */

     public static int insertMileage(String code, String userid) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        ListSet ls = null;
        String sql  = "";
        String sql1 = "";
        String sql2 = "";
        int cnt   = 0;
        int isOk1 = 0;
        int isOk2 = 0;

        isOk1 = MileageManager.getMileageUse(code);
        if (isOk1 > 0) {
            int v_mileagebase  = MileageManager.getMileageBase(code);

            try {
                connMgr = new DBConnectionManager();

                sql  = "select count(*) from TZ_MEMBER_MILEAGE  ";
                sql += " where userid      = " + StringManager.makeSQL(userid);
                sql += "   and mileagecode = " + StringManager.makeSQL(code);
                ls = connMgr.executeQuery(sql);
                if (ls.next()) {
                    cnt = ls.getInt(1);
                }
                ls.close();

                if (cnt > 0) {                      // UPDATE
                    sql1  = " update TZ_MEMBER_MILEAGE set point = point + ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
                    sql1 += " where userid      = ? ";
                    sql1 += "   and mileagecode = ? ";
                    pstmt1 = connMgr.prepareStatement(sql1);

                    pstmt1.setInt(1, v_mileagebase);
                    pstmt1.setString(2, userid);
                    pstmt1.setString(3, code);

                    isOk2 = pstmt1.executeUpdate();
                } else {                            // INSERT
                    sql2  = " insert into TZ_MEMBER_MILEAGE(userid, mileagecode, point, ldate)      ";
                    sql2 += "                 values(?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                    pstmt2 = connMgr.prepareStatement(sql2);

                    pstmt2.setString(1, userid);
                    pstmt2.setString(2, code);
                    pstmt2.setInt(3, v_mileagebase);

                    isOk2 = pstmt2.executeUpdate();
                }
            }
            catch(Exception ex) {
                ErrorManager.getErrorStackTrace(ex);
                throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
            }
            finally {
                if(ls != null) { try { ls.close(); } catch (Exception e) {} }
                if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
                if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }

        }
        return isOk1 * isOk2;
    }

}
