package com.credu.common;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.credu.library.ConfigSet;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;

/**
 * <p>
 * 제목: MChannel 연동 라이브러리
 * </p>
 * <p>
 * 설명:
 * </p>
 * <p>
 * Copyright: Copyright (c) 2004
 * </p>
 * <p>
 * Company: Credu
 * </p>
 * 
 * @author 노희성
 *@date 2004. 12. 07
 *@version 1.0
 */
public class MChannelConnect {
    private String mchannel_pool_name = null;
    private Connection conn = null;
    private Context env = null;
    private DataSource source = null;
    private static int count = 0;
    // private InitialContext initCtx = null;
    private String start = "";
    private PreparedStatement pstmt = null;
    private String sql = "";

    public MChannelConnect() throws Exception {
        ConfigSet conf = new ConfigSet();
        try {
            if (mchannel_pool_name == null) {
                mchannel_pool_name = conf.getProperty("pool.name.mchannel");
            }
            this.initialize(mchannel_pool_name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initialize(String poolName) throws Exception {
        try {
            System.out.println("poolName " + poolName);
            // ------------- resin -----------------------------------------------------------------------
            env = (Context) new InitialContext().lookup("java:comp/env");
            source = (DataSource) env.lookup("jdbc/" + poolName);

            conn = source.getConnection();

            count++;
            start = FormatDate.getDate("yyyyMMddHHmmss");
            System.out.println("MgetConn : " + count);

            sql = "insert into s_announce (sender_id, sender_name, target_id, target_name, message, url, system)";
            sql += " values (?, ?, ?, ?, ?, ?, ?)";
            // sql += " values ('XG01410','이정한','XG01410','노희성', '테스트','http://www.credu.com','EDU')";

            pstmt = conn.prepareStatement(sql);

            System.out.println("conn : " + conn);

            System.out.println("pstmt : " + pstmt);

        } catch (Exception ex) {
            // Log.sys.println(this, ex, "Happen to DBConnectionManager.initialize(\"" + poolName + "\")");
            ex.printStackTrace();
        }
    }

    public int sendMessage(RequestBox box) throws Exception {
        int isOk = 0;

        try {

            /*
             * pstmt.setString(1, v_seq); pstmt.setString(2, s_userid); pstmt.setString(3, s_usernm); pstmt.setString(4, v_title); pstmt.setString(3,
             * s_usernm); pstmt.setString(4, v_title);
             */

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
        }
        return isOk;
    }

    public int sendMessage(String SendId, String SendName, String RecevieId, String RecevieName, String Msg, String Url) throws Exception {
        int isOk = 0;

        try {
            pstmt.setString(1, SendId);
            pstmt.setString(2, SendName);
            pstmt.setString(3, RecevieId);
            pstmt.setString(4, RecevieName);
            pstmt.setString(5, Msg);
            pstmt.setString(6, Url);
            pstmt.setString(7, "EDU");

            isOk = pstmt.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        return isOk;
    }

    /**
     * 컨넥션풀로 Connection 객체를 돌려준다
     */
    public void freeConnection() throws Exception {
        if (pstmt != null)
            try {
                pstmt.close();
            } catch (Exception e1) {
            }
        if (conn != null)
            try {
                conn.close();
            } catch (Exception ex) {
            }
        count--;
        System.out.println("MfreeConn : " + count + " | " + FormatDate.getDate("yyyy/MM/dd HH:mm:ss") + " | "
                + FormatDate.getSecDifference(start, FormatDate.getDate("yyyyMMddHHmmss")));
        conn = null;
    }
}
