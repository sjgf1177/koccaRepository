//**********************************************************
//  1. ��      ��: �н�����
//  2. ���α׷��� : EdutimeControlBean.java
//  3. ��      ��: �н�����
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2004. 12.
//  7. ��      ��:
//**********************************************************

package com.credu.system;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * �н����� ����/�ð� ����Ʈ
 *
 * @date   : 2004. 11
 * @author : S.W.Kang
 */
public class EdutimeControlBean {

    public EdutimeControlBean() {}


    /**
    �н����� ����/�ð� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ��� ����Ʈ
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
    �н����� ���� ����Ʈ
    @param box          receive from the form object and session
    @return ArrayList   ��� ����Ʈ
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