// **********************************************************
// 1. �� ��: ����
// 2. ���α׷��� : Bean.java
// 3. �� ��: ����
// 4. ȯ ��: JDK 1.5
// 5. �� ��: 1.0
// 6. �� ��: __����__ 2009. 10. 19
// 7. �� ��: __����__ 2009. 10. 19
// **********************************************************
package com.credu.homepage;

import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

/**
 * To change the template for this generated type comment go to Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 * 
 * @author Administrator
 */

@SuppressWarnings("unchecked")
public class EventBean {

    private ConfigSet config;
    private int row;

    // private static final String FILE_TYPE = "p_file"; // ���Ͼ��ε�Ǵ� tag name
    // private static final int FILE_LIMIT = 5; // �������� ���õ� ����÷�� ����

    public EventBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // �� ����� �������� row ���� �����Ѵ�
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
    public ArrayList selectListEventAll(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";

        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        String v_selGroup = box.getString("p_selGroup");
        String v_periodyn = box.getString("p_periodyn");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            headSql.append(" SELECT      A.SEQ, A.GUBUN, A.TITLE, A.CONTENT, A.ISALL  \n");
            headSql.append("             , A.STRDATE, A.ENDDATE, A.LOGINYN, A.USEYN   \n");
            headSql.append("             , A.POPWIDTH, A.POPHEIGHT, A.POPXPOS, A.CNT  \n");
            headSql.append("             , A.POPYPOS, A.POPUP, A.USELIST, A.USEFRAME  \n");
            headSql.append("             , A.USERID, A.INDATE, A.LUSERID, A.LDATE     \n");
            headSql.append("             , B.NAME, A.ISANSWER                         \n");
            headSql
                    .append("             , CASE WHEN (TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN SUBSTR (STRDATE,1,8) AND SUBSTR (ENDDATE,1,8) ) THEN 'Y' ELSE 'N' END AS PERIODYN \n");
            bodySql.append(" FROM        TZ_EVENT A, TZ_MEMBER B                       \n");
            bodySql.append(" WHERE       A.USERID = B.USERID(+)                        \n");
            bodySql.append(" AND         A.ISALL = 'Y'                                 \n");

            if (!v_periodyn.equals("")) {
                bodySql
                        .append(" AND CASE WHEN (TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN SUBSTR (STRDATE,1,8) AND SUBSTR (ENDDATE,1,8) ) THEN 'Y' ELSE 'N' END ='"
                                + v_periodyn + "'");
            }

            if (!v_selGroup.equals("")) {
                bodySql.append(" AND		GRCODECD LIKE '%" + v_selGroup + "%' \n");
            }

            if (box.getSession("userid").equals("lee1"))
                bodySql.append("\n  AND 		 b.grcode    = 'N000001'");

            if (!v_searchtext.equals("")) { // �˻�� ������
                if (v_search.equals("title")) { // �������� �˻��Ҷ�
                    bodySql.append(" AND		TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                } else if (v_search.equals("content")) { // �������� �˻��Ҷ�
                    bodySql.append(" AND		CONTENT LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                }
            }
            orderSql = " ORDER BY A.SEQ DESC ";

            sql = headSql.toString() + bodySql.toString() + orderSql;

            ls = connMgr.executeQuery(sql);

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

    /**
     * ȭ�� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ����Ʈ(��ü���� ����)
     * @throws Exception
     */
    public ArrayList selectListEvent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        String countSql = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String orderSql = "";

        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        // String v_selGroup = box.getString("p_selGroup");
        String v_periodyn = box.getString("p_periodyn");

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();

            headSql.append(" SELECT  A.SEQ, A.GUBUN, A.TITLE, A.CONTENT, A.ISALL            \n");
            headSql.append("     ,   A.STRDATE, A.ENDDATE, A.LOGINYN, A.USEYN               \n");
            headSql.append("     ,   A.USERID, A.INDATE, A.LUSERID, A.LDATE, A.CNT          \n");
            headSql
                    .append("     ,   CASE WHEN (TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN SUBSTR (STRDATE,1,8) AND SUBSTR (ENDDATE,1,8) ) THEN 'Y' ELSE 'N' END AS PERIODYN          \n");
            bodySql.append("  FROM   TZ_EVENT A           \n");
            bodySql.append(" WHERE   SEQ IS NOT NULL \n");

            if (!v_periodyn.equals("")) {
                bodySql
                        .append(" AND CASE WHEN (TO_CHAR(SYSDATE,'YYYYMMDD') BETWEEN SUBSTR (STRDATE,1,8) AND SUBSTR (ENDDATE,1,8) ) THEN 'Y' ELSE 'N' END ='"
                                + v_periodyn + "'");
            }

            if (!v_searchtext.equals("")) { // �˻�� ������
                if (v_search.equals("title")) { // �������� �˻��Ҷ�
                    bodySql.append(" AND		TITLE LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                } else if (v_search.equals("content")) { // �������� �˻��Ҷ�
                    bodySql.append(" AND		CONTENT LIKE " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n");
                }
            }
            orderSql = " ORDER BY PERIODYN DESC, ENDDATE DESC, A.SEQ DESC          ";

            sql = headSql.toString() + bodySql.toString() + orderSql;

            ls = connMgr.executeQuery(sql);

            countSql = "SELECT COUNT(*) " + bodySql.toString();
            int totalRowCount = BoardPaging.getTotalRow(connMgr, countSql); // ��ü
            ls.setPageSize(v_pagesize); // �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, totalRowCount); // ������������ȣ�� �����Ѵ�.

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
     * ����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertEvent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        StringBuffer insertSql = new StringBuffer();
        int isOk = 0;
        int v_seq = 0;

        String v_gubun = box.getStringDefault("p_gubun", "N");
        String v_startdate = box.getString("p_startdate");
        String v_enddate = box.getString("p_enddate");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        String v_loginyn = box.getString("p_login");
        String v_useyn = box.getString("p_use");
        int v_popwidth = box.getInt("p_popsize1");
        int v_popheight = box.getInt("p_popsize2");
        int v_popxpos = box.getInt("p_popposition1");
        int v_popypos = box.getInt("p_popposition2");
        String v_popup = box.getString("p_popup");
        String v_useframe = box.getString("p_useframe");
        String v_uselist = box.getString("p_uselist");
        String v_isall = box.getString("p_isAllvalue");
        String v_isanswer = box.getString("p_isanswer");

        String newFileName = box.getNewFileName("p_banner_file");
        String realFileName = box.getRealFileName("p_banner_file");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "select max(seq) from TZ_EVENT  ";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_seq = ls.getInt(1) + 1;
            } else {
                v_seq = 1;
            }

            /*********************************************************************************************/
            // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�.
            try {
                v_content = (String) NamoMime.setNamoContent(v_content);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            insertSql.append(" INSERT INTO  TZ_EVENT                               \n ");
            insertSql.append(" (                                                   \n ");
            insertSql.append("     SEQ, GUBUN, TITLE, CONTENT, ISALL               \n ");
            insertSql.append("     , STRDATE, ENDDATE, LOGINYN, USEYN              \n ");
            insertSql.append("     , POPWIDTH, POPHEIGHT, POPXPOS                  \n ");
            insertSql.append("     , POPYPOS, POPUP, USELIST, USEFRAME             \n ");
            insertSql.append("     , USERID, LUSERID, ISANSWER, INDATE, LDATE      \n ");
            insertSql.append("     , BANNERIMG, SAVE_BANNERIMG                     \n ");
            insertSql.append(" )                                                   \n ");
            insertSql.append(" VALUES                                              \n ");
            insertSql.append(" (   ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?                 \n ");
            insertSql.append("     , ?, ?, ?, ?, ?, ?, ?, ?                        \n ");
            insertSql.append("     , to_char(sysdate, 'YYYYMMDDHH24MISS')          \n ");
            insertSql.append("     , to_char(sysdate, 'YYYYMMDDHH24MISS')          \n ");
            insertSql.append("     ,? , ?                                          \n ");
            insertSql.append(" )                                                   \n ");

            int index = 1;
            pstmt = connMgr.prepareStatement(insertSql.toString());
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, v_gubun);
            pstmt.setString(index++, v_title);
            pstmt.setString(index++, v_content);
            pstmt.setString(index++, v_isall);
            pstmt.setString(index++, v_startdate);
            pstmt.setString(index++, v_enddate);
            pstmt.setString(index++, v_loginyn);
            pstmt.setString(index++, v_useyn);
            pstmt.setInt(index++, v_popwidth);
            pstmt.setInt(index++, v_popheight);
            pstmt.setInt(index++, v_popxpos);
            pstmt.setInt(index++, v_popypos);
            pstmt.setString(index++, v_popup); // �˾�����
            pstmt.setString(index++, v_uselist); // ����Ʈ��뿩��
            pstmt.setString(index++, v_useframe); // �����ӻ�뿩��
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_isanswer);
            pstmt.setString(index++, realFileName);
            pstmt.setString(index++, newFileName);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

            Log.err.println("isOk==> " + isOk);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, insertSql.toString());
            throw new Exception("sql ->" + insertSql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e1) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * �����Ͽ� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int updateEvent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        StringBuffer updateSql = new StringBuffer();
        int isOk = 0;
        int v_seq = box.getInt("p_seq");
        String v_gubun = box.getStringDefault("p_gubun", "N");
        String v_startdate = box.getString("p_startdate");
        String v_enddate = box.getString("p_enddate");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        String v_loginyn = box.getString("p_login");
        String v_useyn = box.getString("p_use");
        int v_popwidth = box.getInt("p_popsize1");
        int v_popheight = box.getInt("p_popsize2");
        int v_popxpos = box.getInt("p_popposition1");
        int v_popypos = box.getInt("p_popposition2");
        String v_popup = box.getString("p_popup");
        String v_useframe = box.getString("p_useframe");
        String v_uselist = box.getString("p_uselist");
        String v_isall = box.getString("p_isAllvalue");
        String v_isanswer = box.getString("p_isanswer");

        String orgSaveFileNm = box.getString("orgSaveFileNm");

        String newFileName = box.getNewFileName("p_file");
        String realFileName = box.getRealFileName("p_file");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            /*********************************************************************************************/
            // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�.
            try {
                v_content = (String) NamoMime.setNamoContent(v_content);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            updateSql.append(" UPDATE TZ_EVENT SET                                          \n ");
            updateSql.append("         GUBUN         = ?, TITLE       = ?                   \n ");
            updateSql.append("         , CONTENT     = ?, ISALL       = ?                   \n ");
            updateSql.append("         , STRDATE     = ?, ENDDATE     = ?                   \n ");
            updateSql.append("         , LOGINYN     = ?, USEYN       = ?                   \n ");
            updateSql.append("         , POPWIDTH    = ?, POPHEIGHT   = ?                   \n ");
            updateSql.append("         , POPXPOS     = ?, POPYPOS     = ?                   \n ");
            updateSql.append("         , POPUP       = ?, USELIST     = ?                   \n ");
            updateSql.append("         , USEFRAME    = ?, LUSERID     = ?                   \n ");
            updateSql.append("         , ISANSWER    = ?                                    \n ");
            updateSql.append("         , LDATE       = to_char(sysdate, 'YYYYMMDDHH24MISS') \n ");

            if (!newFileName.equals("")) { // �ű� ������ �ִ� ���
                updateSql.append("         , BANNERIMG   = ?                                    \n ");
                updateSql.append("         , SAVE_BANNERIMG   = ?                               \n ");
            }

            updateSql.append(" WHERE   SEQ           = ?                                    \n ");
            updateSql.append("   AND   USERID        = ?                                    \n ");

            pstmt = connMgr.prepareStatement(updateSql.toString());

            int index = 1;
            pstmt.setString(index++, v_gubun);
            pstmt.setString(index++, v_title);
            pstmt.setString(index++, v_content);
            pstmt.setString(index++, v_isall);
            pstmt.setString(index++, v_startdate);
            pstmt.setString(index++, v_enddate);
            pstmt.setString(index++, v_loginyn);
            pstmt.setString(index++, v_useyn);
            pstmt.setInt(index++, v_popwidth);
            pstmt.setInt(index++, v_popheight);
            pstmt.setInt(index++, v_popxpos);
            pstmt.setInt(index++, v_popypos);
            pstmt.setString(index++, v_popup); // �˾�����
            pstmt.setString(index++, v_uselist); // ����Ʈ��뿩��
            pstmt.setString(index++, v_useframe); // �����ӻ�뿩��
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_isanswer);

            if (!newFileName.equals("")) { // �ű� ������ �ִ� ���
                pstmt.setString(index++, realFileName);
                pstmt.setString(index++, newFileName);
            }
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, s_userid);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
                if (!newFileName.equals("")) { // �ű� ������ �ִ� ���
                    if ((newFileName != null && !newFileName.equals("")) && (orgSaveFileNm != null && !orgSaveFileNm.equals(""))) {
                        FileManager.deleteFile(orgSaveFileNm); // ÷������ ����
                    }
                }
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, updateSql.toString());
            throw new Exception("sql = " + updateSql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteEvent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;
        int v_seq = box.getInt("p_seq");
        String saveFileNm = box.getString("p_saveFileNm");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = " DELETE FROM TZ_EVENT WHERE SEQ = ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, v_seq);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                connMgr.commit();
                if (saveFileNm != null) {
                    FileManager.deleteFile(saveFileNm); // ÷������ ����
                }
                isOk = 1;
            } else {
                connMgr.rollback();
                isOk = 0;
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql + "\r\n");
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
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
        return isOk;
    }

    /**
     * ȭ�� �󼼺���
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��ȸ�� ������
     * @throws Exception
     */
    public DataBox selectViewEvent(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        // String v_process = box.getString("p_process");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql.append(" SELECT      A.SEQ, A.GUBUN, A.TITLE, A.CONTENT, A.ISALL      \n");
            sql.append("             , A.STRDATE, A.ENDDATE, A.LOGINYN, A.USEYN       \n");
            sql.append("             , A.POPWIDTH, A.POPHEIGHT, A.POPXPOS, A.CONTENT  \n");
            sql.append("             , A.POPYPOS, A.POPUP, A.USELIST, A.USEFRAME      \n");
            sql.append("             , A.USERID, A.INDATE, A.LUSERID, A.LDATE, A.ISANSWER \n");
            sql.append("             , BANNERIMG                                         \n");
            sql.append("             , SAVE_BANNERIMG                                         \n");
            sql.append(" FROM        TZ_EVENT A                                          \n");
            sql.append(" WHERE       A.SEQ    = " + v_seq);

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                dbox = ls.getDataBox();
            }
            // ��ȸ�� ���� ������ ������
            // if (!v_process.equals("popupview")) {
            //
            // sql.setLength(0);
            // sql.append("update TZ_EVENT set cnt = cnt + 1 where seq = ").append(v_seq);
            //
            // int resultCnt = connMgr.executeUpdate(sql.toString());
            // if (resultCnt > 0) {
            // connMgr.commit();
            // } else {
            // connMgr.rollback();
            // }
            // }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("NoticeSql = " + sql + "\r\n" + ex.getMessage());
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

}
