//**********************************************************
package com.credu.off;

import java.util.ArrayList;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class OfflecturerCollusionAdminBean {

    // private static final String FILE_TYPE = "p_file"; //		파일업로드되는 tag name
    private static final int FILE_LIMIT = 10; //	  페이지에 세팅된 파일첨부 갯수

    public static int getFILE_LIMIT() {
        return FILE_LIMIT;
    }

    private ConfigSet config;
    private int row;

    public OfflecturerCollusionAdminBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> listPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuilder sql = new StringBuilder();

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        String sortByName = box.getStringDefault("sortByName", "N");
        String lectureName = box.getString("lectureName");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append("SELECT  A.SEQ       \n");
            sql.append("    ,   A.NAME      \n");
            sql.append("    ,   A.COMPNM    \n");
            sql.append("    ,   CRYPTO.DEC ('normal', A.REG7) AS REG7           \n");
            sql.append("    ,   CRYPTO.DEC ('normal', A.HANDPHONE) AS HANDPHONE \n");
            sql.append("    ,   CRYPTO.DEC ('normal', A.EMAIL) AS EMAIL         \n");
            sql.append("    ,   A.INDATE    \n");
            sql.append("    ,   B.REALFILE AS REALFILE1 \n");
            sql.append("    ,   B.SAVEFILE AS SAVEFILE1 \n");
            sql.append("    ,   C.REALFILE AS REALFILE2 \n");
            sql.append("    ,   C.SAVEFILE AS SAVEFILE2 \n");
            sql.append("    ,   COUNT(A.SEQ) OVER() AS TOT_CNT  \n");
            sql.append("  FROM  TZ_OFFLECTCARDINFO A        \n");
            sql.append("  LEFT  JOIN TZ_OFFLECTCARDFILE B   \n");
            sql.append("    ON  B.SEQ = A.SEQ   \n");
            sql.append("   AND  B.FILESEQ = 2   \n");
            sql.append("  LEFT  JOIN TZ_OFFLECTCARDFILE C   \n");
            sql.append("    ON  C.SEQ = A.SEQ   \n");
            sql.append("   AND  C.FILESEQ = 3   \n");
            
            if(!lectureName.equals("")) {
                sql.append(" WHERE  A.NAME LIKE '%' || '").append(lectureName).append("' || '%' \n");
            }

            if (sortByName.equals("Y")) {
                sql.append("ORDER BY A.NAME \n");
            } else {
                sql.append("ORDER BY A.INDATE DESC  \n");
            }

            ls = connMgr.executeQuery(sql.toString());

            int total_row_count = 0;

            if (ls.next()) {
                total_row_count = ls.getInt("tot_cnt");
                ls.moveFirst();
            }

            ls.setPageSize(v_pagesize); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno, total_row_count); //     현재페이지번호를 세팅한다.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(total_row_count));
                list.add(dbox);
                
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
        return list;
    }

    /**
     * 
     * @param box
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public DataBox lectInfoView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = new DataBox("result");
        ListSet ls1 = null;
        StringBuffer sql = new StringBuffer();
        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();
            sql.append("SELECT  SEQ     \n");
            sql.append("    ,   NAME    \n");
            sql.append("    ,   CRYPTO.DEC('normal',REG7) AS REG7   \n");
            sql.append("    ,   COMPNM  \n");
            sql.append("    ,   CRYPTO.DEC('normal', COMPADDR) AS COMPADDR  \n");
            sql.append("    ,   CRYPTO.DEC('normal', COMPTEL) AS COMPTEL    \n");
            sql.append("    ,   CRYPTO.DEC('normal', HANDPHONE) AS HANDPHONE\n");
            sql.append("    ,   CRYPTO.DEC('normal', EMAIL) AS EMAIL        \n");
            sql.append("    ,   WORKNBOOK   \n");
            sql.append("    ,   INDATE  \n");
            sql.append("    ,   ETC \n");
            sql.append("    ,   SUBJGUBUN   \n");
            sql.append("    ,   LECTGUBUN \n");
            sql.append("  FROM  TZ_OFFLECTCARDINFO \n");
            sql.append(" WHERE  SEQ = ").append(v_seq);

            ls1 = connMgr.executeQuery(sql.toString(), box);

            if (ls1.next()) {
                dbox.putAll(ls1.getDataBox());
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + connMgr.replaceParam(sql, box) + "\r\n" + ex.getMessage());
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
        return dbox;
    }

    /**
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> lectFileList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuilder sql = new StringBuilder();
        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql.append("SELECT  *   \n");
            sql.append("  FROM  TZ_OFFLECTCARDFILE \n");
            sql.append(" WHERE  SEQ = ").append(v_seq);

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
        return list;
    }

    /**
     * 
     * @param box
     * @param v_gubun
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> lectCareerList(RequestBox box, String v_gubun) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        StringBuilder sql = new StringBuilder();
        int v_seq = box.getInt("p_seq");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql.append("SELECT  *   \n");
            sql.append("  FROM  TZ_OFFLECTCARDADDINFO   \n");
            sql.append(" WHERE  SEQ = ").append(v_seq).append(" \n");
            sql.append("   AND  GUBUN = '").append(v_gubun).append("'   \n");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
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
        return list;
    }

    /**
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> tlistPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;

        /* sql 작성 변수 */
        String sql = "";
        String head_sql = "";
        String body_sql = "";

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql += "SELECT COUNT(1) \n";
            body_sql += "FROM DUAL@TEST1 \n";

            sql = head_sql + body_sql;
            ls = connMgr.executeQuery(sql);
            System.out.println("==============링크성공");

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
        return list;
    }
}
