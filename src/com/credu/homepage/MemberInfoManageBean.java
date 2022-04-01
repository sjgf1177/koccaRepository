package com.credu.homepage;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.HashCipher;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.dunet.common.util.Constants;
import com.dunet.common.util.EncryptUtil;

public class MemberInfoManageBean {

    public MemberInfoManageBean() {
    }

    /**
     * 
     * @param grCode
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectB2BMemberList(String grCode) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        ArrayList<DataBox> memberList = null;
        DataBox dbox = null;

        StringBuilder sql = new StringBuilder();
        String pwd = "";
        String decPwd = "";
        String newEncPwd = "";
        try {

            sql.append("/* 그룹별 B2B 회원 목록 조회 */   \n");
            sql.append("SELECT               \n");
            sql.append("        GRCODE       \n");
            sql.append("    ,   USERID       \n");
            sql.append("    ,   NAME         \n");
            sql.append("    ,   PWD          \n");
            sql.append("    ,   STATE        \n");
            sql.append("  FROM  TZ_MEMBER    \n");
            sql.append(" WHERE  GRCODE = ?   \n");
            sql.append("   AND  STATE = 'Y'  \n");
            sql.append(" ORDER  BY USERID    \n");

            connMgr = new DBConnectionManager();
            memberList = new ArrayList<DataBox>();

            pstmt = connMgr.prepareStatement(sql.toString());
            pstmt.setString(1, grCode);

            ls = new ListSet(pstmt);

            EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY, Constants.APP_IV);

            while (ls.next()) {
                dbox = ls.getDataBox();
                pwd = ls.getString("pwd");
                if (pwd.length() == 24) {
                    decPwd = encryptUtil.decrypt(pwd);
                    newEncPwd = HashCipher.createHash(decPwd);
                } else {
                    decPwd = "";
                    newEncPwd = pwd;
                }

                dbox.put("d_decPwd", decPwd);
                dbox.put("d_newEncPwd", newEncPwd);

                memberList.add(dbox);
            }
        } catch (Exception ex) {
            Log.err.println(this.getClass().getName() + ".selectB2BMemberList : " + ex.getMessage());
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }

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

        return memberList;
    }

    /**
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int updateB2BMemberPassword(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        StringBuilder sb = new StringBuilder();

        String[] userId = box.getStringArray("userId");
        String[] newEncPwd = box.getStringArray("newEncPwd");
        String[] procUser = box.getStringArray("procUser");
        String grCode = box.getString("grCode");

        int resultCnt[] = null;
        int index = 0;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sb.append(" UPDATE  TZ_MEMBER   \n");
            sb.append("    SET  PWD = ?     \n");
            sb.append("  WHERE  USERID = ?  \n");
            sb.append("    AND  GRCODE = '").append(grCode).append("'  \n");

            pstmt = connMgr.prepareStatement(sb.toString());

            for (int i = 0; i < userId.length; i++) {
                if (procUser[i].equals("Y")) {
                    System.out.println(userId[i] + " : " + newEncPwd[i]);
                    index = 1;
                    pstmt.setString(index++, newEncPwd[i]);
                    pstmt.setString(index++, userId[i]);
                    pstmt.addBatch();
                }
            }

            resultCnt = pstmt.executeBatch();

            System.out.println("resultCnt : " + resultCnt.length);

            if (resultCnt.length > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sb.toString());
            throw new Exception("sql = " + sb.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }

            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e) {
                }
            }
        }

        return resultCnt.length;
    }

}
