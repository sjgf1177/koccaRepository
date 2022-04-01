//**********************************************************
//  1. ��      ��:  ����
//  2. ���α׷��� : Bean.java
//  3. ��      ��:  ����
//  4. ȯ      ��: JDK 1.5
//  5. ��      ��: 1.0
//  6. ��      ��: __����__ 2009. 10. 19
//  7. ��      ��: __����__ 2009. 10. 19
//**********************************************************
package com.credu.homepage;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
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
public class EventHomePageBean {

    private ConfigSet config;
    private int row;

    public EventHomePageBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        �� ����� �������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * ȭ�� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ����Ʈ(��ü���� ����)
     * @throws Exception
     */
    public ArrayList<DataBox> selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList list = null;

        String sql = "";
        String countSql = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";

        DataBox dbox = null;

        String v_process = box.getString("p_process");
        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        if (v_process.equals("ONLINE_COURSE") || v_process.equals("OFFLINE_COURSE")) { //  ���������� ���� �������� �̺�Ʈ ���
            v_pagesize = 5;
        }

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            headSql.append(" SELECT A.SEQ, A.GUBUN, A.TITLE, A.CONTENT, A.ISALL \n");
            headSql.append("        ,A.STRDATE ,A.ENDDATE ,A.LOGINYN ,A.USEYN \n");
            headSql.append("        ,A.POPWIDTH ,A.POPHEIGHT ,A.POPXPOS ,A.CNT \n");
            headSql.append("        ,A.POPYPOS ,A.POPUP ,A.USELIST ,A.USEFRAME \n");
            headSql.append("        ,A.USERID ,A.INDATE ,A.LUSERID ,A.LDATE \n");
            headSql.append("        ,CASE WHEN  A.WINNERYN = 'Y' THEN \n");
            headSql.append("                          'Y' \n");
            headSql.append("                    WHEN A.WINNERYN = 'N' THEN \n");
            headSql.append("                          CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN SUBSTR(STRDATE, 1, 8) AND SUBSTR(ENDDATE, 1, 8) THEN \n");
            headSql.append("                                    'A' \n");
            headSql.append("                               WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') < SUBSTR(STRDATE, 1, 8) THEN \n");
            headSql.append("                                    'B' \n");
            headSql.append("                               WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') >	 SUBSTR(STRDATE, 1, 8) THEN \n");
            headSql.append("                                    'C' \n");
            headSql.append("                               ELSE \n");
            headSql.append("                                    A.WINNERYN \n");
            headSql.append("                          END \n");
            headSql.append("             END WINNERYN \n");
            headSql.append("             , B.NAME, A.ISANSWER \n");

            bodySql.append(" FROM TZ_EVENT A, TZ_MEMBER B \n");
            bodySql.append(" WHERE A.USERID = B.USERID(+) AND A.USEYN='Y' \n");
            bodySql.append(" AND TO_CHAR(SYSDATE, 'YYYYMMDD') >= SUBSTR(STRDATE, 1, 8) \n");
            bodySql.append(" AND B.GRCODE= " + StringManager.makeSQL(box.getSession("tem_grcode")) + "\n");

            if (v_process.equals("INFORMATION") || v_process.equals("HELPDESK") || v_process.equals("ONLINE_COURESE") || v_process.equals("OFFLINE_COURESE")) { //  ���������� ���� �������� �̺�Ʈ ���
                bodySql.append(" AND TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN SUBSTR(STRDATE, 1, 8) AND SUBSTR(ENDDATE, 1, 8)  \n ");
            }

            if (!v_searchtext.equals("")) {
                v_pageno = 1;
                if (v_search.equals("title")) {
                    bodySql.append(" AND TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                } else if (v_search.equals("all")) {
                    bodySql.append(" AND (CONTENT LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                    bodySql.append(" OR TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " ) \n");
                }
            }

            orderSql = " ORDER BY A.ISALL DESC, A.SEQ DESC ";

            sql = headSql.toString() + bodySql.toString() + orderSql;

            ls = connMgr.executeQuery(sql);

            countSql = " SELECT COUNT(*) " + bodySql.toString();

            int totalRowCount = BoardPaging.getTotalRow(connMgr, countSql); //     ��ü row ���� ��ȯ�Ѵ�
            //int totalPageCount = ls.getTotalPage();       					//     ��ü ������ ���� ��ȯ�Ѵ�
            ls.setPageSize(v_pagesize); //     �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, totalRowCount); //     ������������ȣ�� �����Ѵ�.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(totalRowCount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(totalRowCount));
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

    /**
     * �̺�Ʈ ȭ�� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ����Ʈ(��ü���� ����)
     * @throws Exception
     */
    public ArrayList<DataBox> selectListNew(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        ArrayList list = null;

        String sql = "";
        String countSql = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";
        String v_periodyn = box.getString("p_periodyn");

        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            headSql.append(" /*  com.credu.homepage.EventHomePageBean  : �̺�Ʈ ���ȭ�� */  \n");
            headSql.append(" SELECT A.SEQ, A.GUBUN, A.TITLE, A.CONTENT, A.ISALL  \n");
            headSql.append("        ,A.STRDATE ,A.ENDDATE ,A.LOGINYN ,A.USEYN    \n");
            headSql.append("        ,A.POPWIDTH ,A.POPHEIGHT ,A.POPXPOS ,A.CNT   \n");
            headSql.append("        ,A.POPYPOS ,A.POPUP ,A.USELIST ,A.USEFRAME       \n");
            headSql.append("        ,A.USERID ,A.INDATE ,A.LUSERID ,A.LDATE         \n");
            headSql.append("        ,A.BANNERIMG ,A.SAVE_BANNERIMG                  \n");
            //			headSql.append("        ,CASE WHEN  A.WINNERYN = 'Y' THEN \n");
            //			headSql.append("                          'Y' \n");
            //			headSql.append("                    WHEN A.WINNERYN = 'N' THEN \n");
            //			headSql.append("                          CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN SUBSTR(STRDATE, 1, 8) AND SUBSTR(ENDDATE, 1, 8) THEN \n");
            //			headSql.append("                                    'A' \n");
            //			headSql.append("                               WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') < SUBSTR(STRDATE, 1, 8) THEN \n");
            //			headSql.append("                                    'B' \n");
            //			headSql.append("                               WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') >	 SUBSTR(STRDATE, 1, 8) THEN \n");
            //			headSql.append("                                    'C' \n");
            //			headSql.append("                               ELSE \n");
            //			headSql.append("                                    A.WINNERYN \n");
            //			headSql.append("                          END \n");
            //			headSql.append("             END WINNERYN \n");
            //			headSql.append("             , B.NAME, A.ISANSWER \n");
            headSql.append("    ,   CASE                                                                                                        \n");
            headSql.append("            WHEN (TO_CHAR (SYSDATE, 'YYYYMMDD') BETWEEN SUBSTR (STRDATE, 1, 8) AND SUBSTR (ENDDATE, 1, 8)) THEN 'Y' \n");
            headSql.append("            WHEN (TO_CHAR (SYSDATE, 'YYYYMMDD')) < SUBSTR (STRDATE, 1, 8) THEN 'P'                                  \n");
            headSql.append("            ELSE 'N'                                                                                                \n");
            headSql.append("        END AS PERIODYN                                                                                             \n");
            bodySql.append(" FROM TZ_EVENT A\n");
            bodySql.append(" WHERE USEYN = 'Y' \n");

            if (v_periodyn.equals("Y")) {
                bodySql.append("  AND TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN SUBSTR (STRDATE, 1, 8) AND SUBSTR (ENDDATE, 1, 8) \n");
            } else if (v_periodyn.equals("P")) {
                bodySql.append("  AND TO_CHAR(SYSDATE, 'YYYYMMDD') < SUBSTR (STRDATE, 1, 8) ");
            } else if (v_periodyn.equals("N")) {
                bodySql.append("  AND TO_CHAR(SYSDATE, 'YYYYMMDD') > SUBSTR (ENDDATE, 1, 8) ");
            }
            //          bodySql.append(" FROM TZ_EVENT A , TZ_MEMBER B \n");
            //			bodySql.append(" WHERE A.USERID = B.USERID(+) AND A.USEYN='Y' \n");
            //			bodySql.append(" AND TO_CHAR(SYSDATE, 'YYYYMMDD') >= SUBSTR(STRDATE, 1, 8) \n");
            //			bodySql.append(" AND B.GRCODE= " + StringManager.makeSQL(box.getSession("tem_grcode"))+"\n");

            if (!v_searchtext.equals("")) {
                v_pageno = 1;
                if (v_search.equals("title")) {
                    bodySql.append(" AND TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                } else if (v_search.equals("all")) {
                    bodySql.append(" AND (CONTENT LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                    bodySql.append(" OR TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " ) \n");
                }
            }

            orderSql = " ORDER BY PERIODYN DESC, ENDDATE DESC ";
            //orderSql = " ORDER BY A.ISALL DESC, A.SEQ DESC ";

            sql = headSql.toString() + bodySql.toString() + orderSql;

            ls = connMgr.executeQuery(sql);

            countSql = " SELECT COUNT(*) " + bodySql.toString();

            int totalRowCount = BoardPaging.getTotalRow(connMgr, countSql); //     ��ü row ���� ��ȯ�Ѵ�
            //			int totalPageCount = ls.getTotalPage();       					//     ��ü ������ ���� ��ȯ�Ѵ�
            ls.setPageSize(v_pagesize); //     �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, totalRowCount); //     ������������ȣ�� �����Ѵ�.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(totalRowCount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(totalRowCount));
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

    /**
     * ȭ�� �󼼺���
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��ȸ�� ������
     * @throws Exception
     */
    public DataBox selectView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_process = box.getString("p_process");
        //		String s_userid  = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();

            //			sql.append(" SELECT      A.SEQ, A.GUBUN, A.TITLE, A.CONTENT, A.ISALL      \n");
            //			sql.append("             , A.STRDATE, A.ENDDATE, A.LOGINYN, A.USEYN       \n");
            //			sql.append("             , A.POPWIDTH, A.POPHEIGHT, A.POPXPOS, A.CONTENT  \n");
            //			sql.append("             , A.POPYPOS, A.POPUP, A.USELIST, A.USEFRAME      \n");
            //			sql.append("             , A.USERID, A.INDATE, A.LUSERID, A.LDATE, A.ISANSWER \n");
            //			sql.append("             , CASE WHEN  A.WINNERYN = 'Y' THEN                                                                                \n ");
            //			sql.append("                          'Y'                                                                                                  \n ");
            //			sql.append("                    WHEN  A.WINNERYN = 'N' THEN                                                                                \n ");
            //			sql.append("                          CASE WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') BETWEEN SUBSTR(STRDATE, 1, 8) AND SUBSTR(ENDDATE, 1, 8) THEN  \n ");
            //			sql.append("                                    'A'                                                                                        \n ");
            //			sql.append("                               WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') < SUBSTR(STRDATE, 1, 8) THEN                                  \n ");
            //			sql.append("                                    'B'                                                                                        \n ");
            //			sql.append("                               WHEN TO_CHAR(SYSDATE, 'YYYYMMDD') >	 SUBSTR(STRDATE, 1, 8) THEN                                  \n ");
            //			sql.append("                                    'C'                                                                                        \n ");
            //			sql.append("                               ELSE                                                                                            \n ");
            //			sql.append("                                    A.WINNERYN                                                                                 \n ");
            //			sql.append("                          END                                                                                                  \n ");
            //			sql.append("             END WINNERYN                                                                                                      \n ");
            //			sql.append("             , A.WINNERS, A.CNT                               \n");
            //			sql.append("             , B.NAME                                         \n");
            //			sql.append("             , DECODE(C.SEQ, NULL, 'N', 'Y') APPLY_YN         \n");
            //			sql.append(" FROM        TZ_EVENT A, TZ_MEMBER B, TZ_EVENT_APPLY C        \n");
            //			sql.append(" WHERE       A.USERID = B.USERID(+)                           \n");
            //			sql.append(" AND         A.SEQ      = C.SEQ(+)                            \n");
            //			sql.append(" AND 		 A.SEQ    = " +v_seq);
            //			sql.append(" AND 	 	 C.USERID(+)= " +StringManager.makeSQL(s_userid));
            sql.append(" /*com.credu.homepage.EventHomePageBean  �̺�Ʈ ������ �󼼺���*/\n");
            sql.append(" SELECT A.SEQ, A.GUBUN, A.TITLE, A.CONTENT, A.ISALL     \n");
            sql.append("    ,   SUBSTR (STRDATE, 1, 8) STRDATE                  \n");
            sql.append("    ,   SUBSTR (ENDDATE, 1, 8) ENDDATE                  \n");
            sql.append("    ,   A.LOGINYN, A.USEYN       \n");
            sql.append("    ,   A.CONTENT                                       \n");
            sql.append("    ,   A.USERID, A.INDATE, A.LUSERID, A.LDATE, A.ISANSWER \n");
            sql.append("    ,   CASE WHEN (TO_CHAR (SYSDATE, 'YYYYMMDD') BETWEEN SUBSTR (STRDATE, 1, 8) AND SUBSTR (ENDDATE, 1, 8)) THEN 'Y' ELSE 'N' END AS PERIODYN \n");
            sql.append("   FROM TZ_EVENT A                                       \n");
            sql.append("  WHERE A.SEQ    = " + v_seq);

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
            }

            // ��ȸ�� ����
            if (v_process.equals("selectView")) {
                connMgr.executeUpdate("update TZ_EVENT set cnt = cnt + 1 where seq = " + v_seq);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("EventSql = " + sql + "\r\n" + ex.getMessage());
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
        return dbox;
    }

    /**
	 * 
	 * 
	 * 
	 * 	
	 */
    public ArrayList<DataBox> selectMemoList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        DataBox dbox = null;
        StringBuffer strSQL = null;

        //		int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");
        //		int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();
            strSQL = new StringBuffer();

            strSQL.append("SELECT a.seq, a.userid,a.indate, a.gender, a.answer, b.name  FROM tz_event_Apply a, tz_member b \n");
            strSQL.append(" where a.seq = " + v_seq + " and a.userid = b.userid order by a.indate desc \n");

            ls = connMgr.executeQuery(strSQL.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, strSQL.toString());
            throw new Exception("EventSql = " + strSQL.toString() + "\r\n" + ex.getMessage());
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
     * ��� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteComment(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        //	    ListSet ls = null;
        //Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk1 = 1;

        String v_indate = box.getString("p_commentindate");
        int v_commentseq = box.getInt("p_commentseq");

        try {
            connMgr = new DBConnectionManager();
            sql = " delete from tz_event_Apply    ";
            sql += "  where seq = ? and indate = ? ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, v_commentseq);
            pstmt.setString(2, v_indate);

            isOk1 = pstmt.executeUpdate();
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return isOk1;
    }

    /**
     * ��� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int updateComment(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        //		    ListSet ls = null;
        //		    Connection conn = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk1 = 1;

        String v_indate = box.getString("p_indate");
        //		    int    v_commentseq   = box.getInt("p_commentseq");
        String v_answer = box.getString("p_answer");

        try {
            connMgr = new DBConnectionManager();
            sql = " update tz_event_Apply set answer = ?    ";
            sql += "  where  indate = ? ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setString(1, v_answer);
            pstmt.setString(2, v_indate);

            isOk1 = pstmt.executeUpdate();
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
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
        return isOk1;
    }
}
