package com.credu.library;

import java.io.Serializable;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;

/**
 * ���ǰ��� ���̺귯��
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
     * �α����Ҷ� �ڵ����� ȣ��ȴ�. ���⿡�� tz_sessioninfo table �� sessionid, userid, name,
     * level ���� �����ϰ�, TB_loginhistory �� �α��νð�, ��¥���� userid ���� �����Ͽ� ���߿� ����ڷḦ ����
     * ����Ѵ�.
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
     * �α׾ƿ��Ҷ�, Ȥ�� �ð��� ����Ͽ� �������� �ڵ����� ������ �����ų�� ȣ���Ѵ�. cu_sessioninfo ���̺� �����ϴ� �ش�
     * ���̵� �����ϰ�, session Pool �� �����ָ�, cu_loginhistory �� �α׾ƿ� �ð��� userid ����
     * ����Ѵ�.
     */
    public void valueUnbound(HttpSessionBindingEvent event) {
        DBConnectionManager connMgr = null;

        // HttpSession session = event.getSession();

        String sql = "";

        // tz_sessioninfo ���� ������ �����, 
        // TB_loginhistory �� �α׾ƿ��� �ð��� update �ϰ�,
        // �ش� ���̵� ���õ� ������ ���� �����!!
        try {
            connMgr = new DBConnectionManager();

            //sql ="delete from tz_sessioninfo";
            //sql+=" where sessionid = '" + session.getId() + "'";
            //connMgr.executeUpdate(sql);
            //            System.out.println("del sql" + sql);
            /*
             * ����Ǯ�� �ִ� �ѵ��� ���� ������. removeSession �� ����Ͽ� invalidate �ϰ� ����Ǯ�� �����°���
             * �մ����� �ʴ�. �ֳ��ϸ� valueUnbound �� ȣ��Ǵ� ������ �̹� session �� invalidate ��
             * ��Ȳ�̱� �����̴�. �� ��Ȳ���� �Ǵٽ� session �� invalidate �Ϸ��� �õ��� �ϸ� resin ��
             * nullpointer exception �� �����鼭 ������ ��ü�� �׾������.
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
