package com.credu.mobile.information;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

public class FAQBean {
    public FAQBean() { }

    
    /**
     * 모바일 FAQ 목록
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public ArrayList selectFAQList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        StringBuffer sql = new StringBuffer();
        ArrayList list = null;
        ListSet ls = null;
        
        String grcode = box.getSession("grcode");
        grcode = (grcode == null || grcode.equals("")) ? "N000001" : grcode;

        try {
            connMgr = new DBConnectionManager("unpp");
            list = new ArrayList();

            //2015.11.13 포털 FAQ를 보여주도록 함
            sql = new StringBuffer();
            sql.append("SELECT			\n");
            sql.append("    A.NTT_ID	\n");
            sql.append("    , A.BBS_ID	\n");
            sql.append("    , A.NTT_SJ	\n");
            sql.append("    , A.NTT_CN	\n");
            sql.append("    , TO_CHAR(A.FRST_REGIST_PNTTM, 'YYYY-MM-DD') AS FRST_REGISTER_PNTTM	\n");
            sql.append("FROM								\n");
            sql.append("    TB_BBS_ESTN A					\n");
            sql.append("WHERE								\n");
            sql.append("    A.BBS_ID IN ('B0000055')		\n");
            sql.append("    AND A.PARNTS IN (0)				\n");
            sql.append("    AND A.DELETE_CODE IN ('0')		\n");
            sql.append("ORDER BY A.FRST_REGIST_PNTTM DESC	\n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                list.add(ls.getDataBox());
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
            
        } finally {
            if (ls != null) { try { ls.close(); } catch (Exception e) { } }
            if (connMgr != null) { try { connMgr.freeConnection(); } catch (Exception e10) { } }
        }
        return list;
    }

}
