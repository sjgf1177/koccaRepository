package com.credu.mobile.user;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.HashCipher;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class LoginBean {

    public LoginBean() {
    }

    public DataBox loginUser(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuilder sql = new StringBuilder();

        DataBox dbox = null;

        String userid = box.getString("userid");
        String pwd = box.getString("pwd");
        String grcode = box.getString("grcode");
        String grPrefix = box.getStringDefault("grPrefix", "");
        String mobileUserid = "";
        boolean loginFlag = false;

        if (!grPrefix.equals("")) {
            if (userid.indexOf(grPrefix) == 0) {
                mobileUserid = userid;
            } else {
                mobileUserid = grPrefix + userid;
            }
        }

        try {
            pwd = HashCipher.createHash(pwd);
            connMgr = new DBConnectionManager();

            sql.append("SELECT   USERID                                 \n");
            sql.append("     ,   NAME                                   \n");
            //sql.append("     ,   CRYPTO.DEC('normal', EMAIL) AS EMAIL   \n");
            sql.append("     ,   PWD                                    \n");
            sql.append("     ,   SEX                                    \n");
            //sql.append("     ,   CRYPTO.DEC('normal', HANDPHONE) AS HANDPHONE  \n");
            sql.append("     ,   LGLAST                                 \n");
            sql.append("  FROM   TZ_MEMBER                              \n");

            if (!grcode.equals("N000001") && !mobileUserid.equals("")) {
                sql.append(" WHERE   MOBILE_USERID = '").append(mobileUserid).append("'  \n");
            } else {
                sql.append(" WHERE   USERID = '").append(userid).append("'  \n");
            }

            sql.append("   AND   PWD = '").append(pwd).append("'        \n");
            sql.append("  AND    LEAVE_DATE IS NULL     \n");
            sql.append("  AND    STATE = 'Y'            \n");
            sql.append("  AND    GRCODE = '").append(grcode).append("'  \n");

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
            	loginFlag = true;

                box.setSession("grcode", grcode);
                box.setSession("userid", userid);
                box.setSession("name", ls.getString("name"));
                //box.setSession("email", ls.getString("email"));
                //box.setSession("handphone", ls.getString("handphone"));
                box.setSession("sex", ls.getString("sex"));

                dbox = ls.getDataBox();

                this.createMemberLoginLog(box, "MOBILE", connMgr);
                
            }
            
            if(!loginFlag){
            	this.createLoginFailLog(box, connMgr);
            }
            
            
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                    ErrorManager.getErrorStackTrace(e);
                    throw new Exception("로그인 오류 : " + e.getMessage());
                }
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
        return dbox;
    }

    /**
     * 로그인 이력 정보를 저장한다.
     * 
     * @param connMgr
     * @param box
     * @throws Exception
     */
    private void createMemberLoginLog(RequestBox box, String loginType, DBConnectionManager connMgr) throws Exception {
        StringBuilder sql = new StringBuilder();
        // PreparedStatement pstmt = null;
        // int idx = 0;
        int resultCnt = 0;

        String userid = box.getSession("userid");
        String grcode = box.getSession("grcode");
        String userip = box.getString("userip");
        String hostip = box.getString("hostip");
        try {
            connMgr.setAutoCommit(false);

            sql.append("UPDATE  TZ_MEMBER       \n");
            sql.append("   SET  LGLAST = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') \n");
            sql.append(" WHERE  GRCODE = '").append(grcode).append("'   \n");
            sql.append("   AND  USERID = '").append(userid).append("'   \n");

            resultCnt += connMgr.executeUpdate(sql.toString());

            sql.setLength(0);

            sql.append("INSERT  INTO    TZ_LOG_MEMBER_LOGIN  \n");
            sql.append("    (   \n");
            sql.append("        USERID          \n");
            sql.append("    ,   LOGIN_DT        \n");
            sql.append("    ,   SESSION_ID      \n");
            sql.append("    ,   USER_IP         \n");
            sql.append("    ,   HOST_IP         \n");
            sql.append("    ,   GRCODE          \n");
            sql.append("    ,   LOGIN_TYPE      \n");
            sql.append("    )   VALUES (        \n");
            sql.append("        '").append(userid).append("'    \n");
            sql.append("    ,   SYSDATE         \n");
            sql.append("    ,   '").append(box.getSessionId()).append("'    \n");
            sql.append("    ,   '").append(userip).append("'    \n");
            sql.append("    ,   '").append(hostip).append("'    \n");
            sql.append("    ,   '").append(grcode).append("'    \n");
            sql.append("    ,   '").append(loginType).append("' \n");
            sql.append("    )                   \n");

            resultCnt += connMgr.executeUpdate(sql.toString());

            // pstmt = connMgr.prepareStatement(sql.toString());
            //
            // pstmt.setString(++idx, box.getSession("userid"));
            // pstmt.setString(++idx, box.getSessionId());
            // pstmt.setString(++idx, box.getString("userip"));
            // pstmt.setString(++idx, box.getString("hostip"));
            // pstmt.setString(++idx, box.getSession("grcode"));
            // pstmt.setString(++idx, loginType);
            //
            // resultCnt = pstmt.executeUpdate();

            if (resultCnt > 0) {
                connMgr.commit();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("사용자 로그인 이력 저장 오류 :\r\n" + sql.toString() + "\r\n" + ex.getMessage());
        }

    }
    
    private void createLoginFailLog(RequestBox box, DBConnectionManager connMgr) throws Exception {
    	StringBuilder sql = new StringBuilder();
    	
    	String userid = box.getString("userid");
    	String grcode = box.getString("grcode");
    	String userip = box.getString("userip");
    	String hostip = box.getString("hostip");
    	
    	String pwd1 = box.getString("pwd");
    	String pwd2 = "";
    	
    	try {
    		
    		connMgr.setAutoCommit(false);
    		
    		pwd2 = HashCipher.createHash(pwd1);
    		
    		sql.setLength(0);
    		sql.append("INSERT INTO TZ_LOG_MEMBER_LOGIN_FAIL  	\n");
    		sql.append("    (   								\n");
    		sql.append("        USERID          				\n");
    		sql.append("    ,   PWD1    	    				\n");
    		sql.append("    ,   PWD2	        				\n");
    		sql.append("    ,   LOGIN_DT        				\n");
    		sql.append("    ,   USER_IP         				\n");
    		sql.append("    ,   HOST_IP         				\n");
    		sql.append("    ,   GRCODE          				\n");
    		sql.append("    )   VALUES (        				\n");
    		sql.append("        '").append(userid).append("'    \n");
    		sql.append("    ,   '").append(pwd1).append("'    	\n");
    		sql.append("    ,   '").append(pwd2).append("'    	\n");
    		sql.append("    ,   SYSDATE         				\n");
    		sql.append("    ,   '").append(userip).append("'    \n");
    		sql.append("    ,   '").append(hostip).append("'    \n");
    		sql.append("    ,   '").append(grcode).append("'    \n");
    		sql.append("    )                   				\n");
    		
    		connMgr.executeUpdate(sql.toString());
    		
			connMgr.commit();
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		ErrorManager.getErrorStackTrace(ex, box, sql.toString());
    	}
    	
    }

    public boolean isUserLogin(RequestBox box) throws Exception {
        boolean isLogin = false;
        try {

            String userid = box.getSession("userid");

            if (!userid.equals("")) {
                isLogin = true;
            }

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("사용자 로그인 여부 오류 :\r\n" + e.getMessage());
        }
        return isLogin;
    }
}
