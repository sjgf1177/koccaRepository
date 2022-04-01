// **********************************************************
// 1. 제 목: PROPOSE STATUS ADMIN BEAN
// 2. 프로그램명: EduInfoBean.java
// 3. 개 요: 신청 현황 관리자 bean
// 4. 환 경: JDK 1.3
// 5. 버 젼: 1.0
// 6. 작 성:
// 7. 수 정:
// **********************************************************
package com.credu.common;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class EduInfoBean {
    // private ConfigSet config;
    // private int row;

    public EduInfoBean() {
    }

    /**
     * 신청명단 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectStduentMemberList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        // ArrayList list = null;
        ArrayList<DataBox> list1 = new ArrayList<DataBox>();
        // DataBox dbox = null;

        String sql1 = "";
        // String sql2 = "";

        try {

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
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
        return list1;
    }

}