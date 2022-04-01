package com.credu.library;

import java.io.Serializable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * 세션관련 라이브러리
 * 
 * @date : 2002. 11
 * @author : j.s.h
 */
public class SessionListener implements HttpSessionBindingListener, Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = -4471951448316730706L;
    RequestBox box = null;

    public SessionListener(RequestBox box) {
        this.box = box;
    }

    /**
     * 로그인할때 자동으로 호출된다. 여기에서 tz_sessioninfo table 에 sessionid, userid, name,
     * level 등을 저장하고, TB_loginhistory 에 로그인시간, 날짜등을 userid 별로 저장하여 나중에 통계자료를 낼때
     * 사용한다.
     */
    public void valueBound(HttpSessionBindingEvent event) {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        HttpSession session = event.getSession();

        String sql = "";

        if (session != null) {
            String v_userid = (String) session.getAttribute("userid");
            String v_name = (String) session.getAttribute("name");
            String v_userip = box.getString("p_userip");

            try {
                connMgr = new DBConnectionManager();

                sql = "select sessionid from tz_sessioninfo where sessionid= '" + session.getId() + "'";
                ls = connMgr.executeQuery(sql);
                if (!ls.next()) {
                    sql = "insert into tz_sessioninfo(sessionid, userid, name, userip, indate) values (";
                    sql += "'" + session.getId() + "'";
                    sql += ",'" + v_userid + "'";
                    sql += ",'" + v_name + "'";
                    sql += ",'" + v_userip + "'";
                    sql += ", to_char(sysdate, 'YYYYMMDDHH24MISS'))";
                    connMgr.executeUpdate(sql);
                }
            } catch (Exception e) {
                System.out.println("*******************");
                System.out.println(e.getMessage());
                System.out.println("SessionListener.valueBound()\r\nsql=" + sql + "\r\ncheck log file!");
                System.out.println("*******************");
            } finally {
                if (ls != null) {
                    try {
                        ls.close();
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
        }
    }

    /**
     * 로그아웃할때, 혹은 시간이 경과하여 웹서버가 자동으로 세션을 종료시킬때 호출한다. cu_sessioninfo 테이블에 존재하는 해당
     * 아이디를 삭제하고, session Pool 을 지워주며, cu_loginhistory 에 로그아웃 시간을 userid 별로
     * 기록한다.
     */
    public void valueUnbound(HttpSessionBindingEvent event) {
        DBConnectionManager connMgr = null;

        // HttpSession session = event.getSession();

        String sql = "";

        // tz_sessioninfo 에서 내용을 지우고, 
        // TB_loginhistory 에 로그아웃된 시간을 update 하고,
        // 해당 아이디에 관련된 세션을 몽땅 지운다!!
        try {
            connMgr = new DBConnectionManager();

            //sql ="delete from tz_sessioninfo";
            //sql+=" where sessionid = '" + session.getId() + "'";
            //connMgr.executeUpdate(sql);
            //            System.out.println("del sql" + sql);
            /*
             * 세션풀에 있던 넘들을 몽땅 날린다. removeSession 을 사용하여 invalidate 하고 세션풀도 날리는것은
             * 합당하지 않다. 왜냐하면 valueUnbound 가 호출되는 시점은 이미 session 이 invalidate 된
             * 상황이기 때문이다. 이 상황에서 또다시 session 을 invalidate 하려는 시도를 하면 resin 은
             * nullpointer exception 을 던지면서 웹서버 자체가 죽어버린다.
             */

            //   ul.removePool(session.getId());
        } catch (Exception e) {
            System.out.println("*******************");
            System.out.println(e.getMessage());
            System.out.println("SessionListener.valueUnbound()\r\nsql=" + sql);
            System.out.println("*******************");
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
    }

}
