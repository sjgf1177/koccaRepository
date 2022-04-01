package com.credu.common;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;

public class SelectBean {

	public static String get_grtype(RequestBox box, String p_grcode) throws Exception{
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql  = "";
        String result ="";
        
        if (box!=null)  result = box.getString("grcodenm");

        if(result.equals("")) {
            try {
                connMgr = new DBConnectionManager();
                sql = " select  grtype from tz_grcode where grcode="+SQLString.Format(p_grcode);
                ls = connMgr.executeQuery(sql);
                if(ls.next())   result = ls.getString("grtype");
            }catch (Exception ex) {
                ErrorManager.getErrorStackTrace(ex, box, sql);
                throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
            }finally {
                if(ls != null) { try { ls.close(); }catch (Exception e) {} }
                if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
            }
        }
        return result;
    }

}