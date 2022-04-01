package com.credu.mobile.common;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.Log;

public class DeviceInfoBean {

    public DeviceInfoBean() {
    }

    /**
     * Device 정보를 등록한다..
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int registerDeviceInfo(String deviceToken, String devicePlatform) throws Exception {

        DBConnectionManager connMgr = null;
        // PreparedStatement pstmt = null;
        ListSet ls = null;

        StringBuilder sql = new StringBuilder();
        int resultCnt = 0;
        
        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append("/* Device 정보 등록 여부 확인 */    \n");
            sql.append("SELECT  COUNT(SEQ) AS CNT   \n");
            sql.append("  FROM  TZ_MOBILE_DEVICE    \n");
            sql.append(" WHERE  DEVICE_TOKEN = '").append(deviceToken).append("'    \n");
            sql.append("   AND  DEVICE_PLATFORM = '").append(devicePlatform).append("' \n");
            

            //            pstmt = connMgr.prepareStatement(sql.toString());
            //            pstmt.setString(1, deviceToken);
            //            pstmt.setString(2, devicePlatform);
            //
            //            ls = new ListSet(pstmt);
            
            ls = connMgr.executeQuery(sql.toString());

            ls.next();

            if (ls.getInt("cnt") == 0) {

                // regiseter
                sql.setLength(0);
                sql.append("INSERT  INTO    TZ_MOBILE_DEVICE (    \n");
                sql.append("        SEQ                           \n");
                sql.append("    ,   DEVICE_TOKEN                  \n");
                sql.append("    ,   DEVICE_PLATFORM               \n");
                sql.append("    ,   USERID                        \n");
                sql.append("    ,   REGDT                         \n");
                sql.append("    ,   MODDT                         \n");
                sql.append(") VALUES (                            \n");
                sql.append("        (                             \n");
                sql.append("        SELECT  NVl(MAX(SEQ), 0) + 1  \n");
                sql.append("          FROM  TZ_MOBILE_DEVICE      \n");
                sql.append("        )                             \n");
                sql.append("    ,   '").append(deviceToken).append("'   \n");
                sql.append("    ,   '").append(devicePlatform).append("'\n");
                sql.append("    ,   '_anonymous_'                  \n");
                sql.append("    ,   SYSDATE                       \n");
                sql.append("    ,   SYSDATE                       \n");
                sql.append(" )                                    \n");
                
                resultCnt = connMgr.executeUpdate(sql.toString());
                
//                pstmt = connMgr.prepareStatement(sql.toString());
//                pstmt.setString(1, deviceToken);
//                pstmt.setString(2, devicePlatform);
//
//                resultCnt = pstmt.executeUpdate();
                
                if ( resultCnt > 0 ) {
                    connMgr.commit();
                    System.out.println("[디바이스 장비 정보 등록 성공] " + devicePlatform + " : " + deviceToken);
                    Log.info.println("[디바이스 장비 정보 등록 성공] " + devicePlatform + " : " + deviceToken);
                } else {
                    connMgr.rollback();
                    System.out.println("[디바이스 장비 정보 등록 실패] " + devicePlatform + " : " + deviceToken);
                    Log.info.println("[디바이스 장비 정보 등록 실패] " + devicePlatform + " : " + deviceToken);
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
//            if (pstmt != null) {
//                try {
//                    pstmt.close();
//                } catch (Exception e) {
//                }
//            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return resultCnt;
    }
}
