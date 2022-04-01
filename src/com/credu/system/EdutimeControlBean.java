//**********************************************************
//  1. 제      목: 학습제약
//  2. 프로그램명 : EdutimeControlBean.java
//  3. 개      요: 학습제약
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12.
//  7. 수      정:
//**********************************************************

package com.credu.system;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * 학습제약 요일/시간 리스트
 *
 * @date   : 2004. 11
 * @author : S.W.Kang
 */
public class EdutimeControlBean {

    public EdutimeControlBean() {}


    /**
    학습제약 요일/시간 리스트
    @param box          receive from the form object and session
    @return ArrayList   운영자 리스트
    */
    @SuppressWarnings("unchecked")
    public ArrayList selectListEdutime(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql  = " select gadmin,gadminnm,comments,padmin from tz_gadmin order by gadmin asc ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

	/**
    학습제약 일자 리스트
    @param box          receive from the form object and session
    @return ArrayList   운영자 리스트
    */
    @SuppressWarnings("unchecked")
    public ArrayList selectListEduday(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        //EdutimeControlData data = null;
        DataBox dbox = null;
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql  = " select gadmin,gadminnm,comments,padmin from tz_gadmin order by gadmin asc ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        }
        catch (Exception ex) {
               ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

}