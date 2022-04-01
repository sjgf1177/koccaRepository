/*
 * Created on 2004-03-19
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package com.credu.library;

/**
 * @author SEONGSHIN
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class BoardPaging {

    /**
     * 醚其捞瘤荐 Return
     * 
     * @param DBConnectionManager目池记
     * @param String 孽府
     * @return int 醚其捞瘤荐
     */
    public static int getTotalRow(DBConnectionManager connMgr, String sql) throws Exception {
        return getTotalRow(connMgr, sql, false);
    }

    /**
     * 醚其捞瘤荐 Return
     * 
     * @param DBConnectionManager目池记
     * @param String 孽府
     * @return int 醚其捞瘤荐
     */
    public static int getTotalRow(DBConnectionManager connMgr, String sql, boolean countYn) throws Exception {
        ListSet ls = null;
        int result = 0;

        try {

            if (countYn) {
                sql = "SELECT COUNT(*) FROM (\n" + sql + "\n)";
            }
            // ConfigSet cs = new ConfigSet();
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt(1);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            ;
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return result;
    }

    /**
	 * 
	 */
    public BoardPaging() {

    }
}
