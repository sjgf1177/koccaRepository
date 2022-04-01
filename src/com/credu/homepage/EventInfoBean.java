// **********************************************************
// 1. 제 목: 이벤트정보 수정 제어(사용자)
// 2. 프로그램명 : EventInfoBean.java
// 3. 개 요: 이벤트정보 수정 제어(사용자)
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성: 조재형 2008. 10. 07
// 7. 수 정:
// **********************************************************
package com.credu.homepage;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

public class EventInfoBean {

    public EventInfoBean() {
    }

    /**
     * Event 정보 변경
     * 
     * @param box
     * @return is_Ok 1 : success 2 : fail
     */
    public int insertEventData(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int is_Ok = 0;
        int is_Cnt = 0;
        String v_userid = box.getString("p_ruserid");

        try {

            connMgr = new DBConnectionManager();

            sql = " select count(*) cnt from TZ_MEMBER where userid = " + StringManager.makeSQL(v_userid);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                is_Cnt = ls.getInt("cnt");
            }

            if (is_Cnt > 0) {

                sql = " insert into eventRegUser                                                       ";
                sql += " select userid, name, to_char(sysdate, 'YYYYMMDDHH24MISS') from tz_member ";
                sql += " where userid = " + StringManager.makeSQL(v_userid) + " and userid not in (select userid from eventRegUser)   ";
                is_Ok = connMgr.executeUpdate(sql);

                sql = " update TZ_MEMBER set isevent = 'Y' where userid = " + StringManager.makeSQL(v_userid);
                is_Ok = connMgr.executeUpdate(sql);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
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

        return is_Ok;
    }

}
