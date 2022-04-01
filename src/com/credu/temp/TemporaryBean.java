//**********************************************************
//  1. ��      ��:  ����
//  2. ���α׷��� : Bean.java
//  3. ��      ��:  ����
//  4. ȯ      ��: JDK 1.5
//  5. ��      ��: 1.0
//  6. ��      ��: __����__ 2009. 10. 19
//  7. ��      ��: __����__ 2009. 10. 19
//**********************************************************
package com.credu.temp;

import java.util.ArrayList;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

@SuppressWarnings("unchecked")
public class TemporaryBean {

    public TemporaryBean() {
    }

    /**
     * �̴��� ��� Ŭ���� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �̴��� ��� Ŭ���� ����Ʈ
     * @throws Exception
     */
    public ArrayList selectTemporaryList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        StringBuilder sql = new StringBuilder();
        DataBox dbox = null;

        String subj = box.getString("subj");
        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

            sql.append("/* selectTemporaryList (�ӽ� ��� ���ϱ�) */\n");
            sql.append("SELECT  A.SUBJ                  \n");
            sql.append("    ,   A.SUBJNM                \n");
            sql.append("    ,   A.WIDTH                 \n");
            sql.append("    ,   A.HEIGHT                \n");
            sql.append("    ,   B.LESSON                \n");
            sql.append("    ,   REPLACE(B.SDESC, '&', '') AS SDESC  \n");
            sql.append("    ,   B.STARTING              \n");
            sql.append("  FROM  TZ_SUBJ A               \n");
            sql.append("    ,   TZ_SUBJLESSON B         \n");
            sql.append(" WHERE  A.SUBJ = B.SUBJ         \n");
            sql.append("   AND  A.SUBJ = '").append(subj).append("' \n");
            sql.append(" ORDER  BY SUBJ, LESSON         \n");


            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.remove("d_sdesc");
                dbox.put("d_sdesc", StringManager.convertHtmlchars(ls.getString("sdesc")));

                list.add(dbox);
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
        return list;
    }
}
