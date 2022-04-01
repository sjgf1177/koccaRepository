// **********************************************************
// 1. �� ��: HomePage QNA ����
// 2. ���α׷���: HomePageQnaBean.java
// 3. �� ��: QNA ����
// 4. ȯ ��: JDK 1.3
// 5. �� ��: 1.0
// 6. �� ��: �̿��� 2005. 6. 23
// 7. �� ��: �̳��� 05.11.16 _ connMgr.setOracleCLOB ����
// **********************************************************
package com.credu.homepage;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.PageList;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.system.CodeData;
import com.dunet.common.util.StringUtil;
import com.dunet.common.util.UploadUtil;

/**
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 * 
 */
public class HomePageQnaBean {
    private ConfigSet config;
    private int row;
    private String v_type = "PQ";
    private static final String FILE_TYPE = "p_file"; // ���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 10; // �������� ���õ� ����÷�� ����

    public HomePageQnaBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // �� ����� �������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getFILE_LIMIT() {
        return FILE_LIMIT;
    }

    public HomePageQnaBean(String type) {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // �� ����� �������� row ���� �����Ѵ�
            this.v_type = type;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * QNAȭ�� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList QNA ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectListQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;

        String sql = "";
        StringBuffer head_sql = new StringBuffer();
        StringBuffer body_sql = new StringBuffer();
        String order_sql = "";
        String count_sql = "";
        DataBox dbox = null;

        String v_process = box.getString("p_process");

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        String v_categorycd = box.getStringDefault("p_selCategory", "ALL");
        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        int v_pageno = box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        if (v_process.equals("HELPDESK")) {
            v_pagesize = 5;
        }

        try {
            connMgr = new DBConnectionManager();
            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            // ------------------------------------------------------------------------------------
            list = new ArrayList<DataBox>();
            // seq, types, title, contents, indate, inuserid, upfile, isopen, luserid, ldate

            head_sql.append(" SELECT  A.TABSEQ, A.SEQ, A.TYPES, A.TITLE, A.CONTENTS\n");
            head_sql.append("         , A.INDATE, A.INUSERID, A.ISOPEN, OKYN1\n");
            head_sql.append("         , A.CNT, B.NAME, A.CATEGORYCD, C.CODENM CATEGORYNM\n");
            // head_sql.append("         , A.CNT, B.NAME, A.CATEGORYCD, C.CLASSNAME CATEGORYNM\n");
            body_sql.append(" FROM    TZ_HOMEQNA A, TZ_MEMBER B, TZ_CODE C\n");
            // body_sql.append(" FROM    TZ_HOMEQNA A, TZ_MEMBER B, TZ_SUBJATT C\n");
            body_sql.append(" WHERE   A.INUSERID      = B.USERID(+)\n");
            body_sql.append(" AND     A.CATEGORYCD    = C.CODE(+)\n");
            // body_sql.append(" AND     A.CATEGORYCD    = C.UPPERCLASS(+)\n");
            body_sql.append(" AND     C.GUBUN(+)      = '0088'\n");
            // body_sql.append(" AND 	  C.MIDDLECLASS   = '000' \n");
            // body_sql.append(" AND     C.LOWERCLASS    = '000' \n");
            body_sql.append(" AND     TABSEQ          = " + v_tabseq + "\n");
            body_sql.append(" AND     A.TYPES         = 0\n");
            body_sql.append(" AND     A.GRCODE        = B.GRCODE \n");
            body_sql.append(" AND     A.GRCODE        = " + SQLString.Format(s_grcode));

            if (!v_searchtext.equals("")) { // �˻�� ������
                v_pageno = 1; // �˻��� ��� ù��° �������� �ε��ȴ�

                if (v_search.equals("TITL")) { // �������� �˻��Ҷ�
                    body_sql.append(" and lower(title) like lower ( " + StringManager.makeSQL("%" + v_searchtext + "%") + ")");
                } else if (v_search.equals("CONT")) { // �������� �˻��Ҷ�
                    body_sql.append(" and contents like " + StringManager.makeSQL("%" + v_searchtext + "%")); // Oracle 9i ��
                } else if (v_search.equals("NAME")) { // �̸����� �˻��Ҷ�
                    body_sql.append(" and lower(name) like lower (" + StringManager.makeSQL("%" + v_searchtext + "%") + ")"); // Oracle 9i ��
                } else { // �̸����� �˻��Ҷ�
                    body_sql.append(" and contents like lower (" + StringManager.makeSQL("%" + v_searchtext + "%") + ")"); // Oracle 9i ��
                }
            }
            if (!v_categorycd.equals("ALL")) {
                body_sql.append(" and categorycd = '" + v_categorycd + "'");
            }

            order_sql = " order by seq desc, types asc ";

            sql = head_sql.toString() + body_sql.toString() + order_sql;

            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);

            ls.setPageSize(v_pagesize); // �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); // ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage(); // ��ü ������ ���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
                dbox.put("d_rowcount", new Integer(v_pagesize));

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
     * QNA ����Ҷ�(����)
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */

    public int insertQnaQue(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;

        StringBuffer sql = new StringBuffer();

        int isOk = 1;
        int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;
        int v_cnt = 0;

        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_content"));
        String v_types = "0";
        String s_userid = box.getSession("userid");
        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
        String v_isopen = box.getStringDefault("p_isopen", "Y");
        String v_categorycd = box.getStringDefault("p_categorycd", "OO");

        try {

            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql.append(" SELECT TABSEQ FROM TZ_BDS WHERE TYPE = " + SQLString.Format(v_type));

            ls = connMgr.executeQuery(sql.toString());

            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            // ------------------------------------------------------------------------------------
            // ---------------------- �Խ��� ��ȣ �����´� ----------------------------
            sql.setLength(0);

            sql.append(" SELECT NVL(MAX(SEQ), 0) FROM TZ_HOMEQNA WHERE TABSEQ = '" + v_tabseq + "' ");

            ls = connMgr.executeQuery(sql.toString());

            ls.next();
            int v_seq = ls.getInt(1) + 1;
            ls.close();

            // //////////////////////////////// �Խ��� table �� �Է� ///////////////////////////////////////////////////////////////////
            sql.setLength(0);

            sql
                    .append(" INSERT INTO TZ_HOMEQNA(TABSEQ, SEQ, TYPES, TITLE, CONTENTS, INDATE, INUSERID, ISOPEN, LUSERID, LDATE, GRCODE, CNT, CATEGORYCD, OKYN1) \n");
            sql.append(" VALUES (?, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), ?, ?, ?, TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'),?,?,?, ?) ");

            pstmt1 = connMgr.prepareStatement(sql.toString());

            pstmt1.setInt(1, v_tabseq);
            pstmt1.setInt(2, v_seq);
            pstmt1.setString(3, v_types);
            pstmt1.setString(4, v_title);
            pstmt1.setCharacterStream(5, new StringReader(v_contents), v_contents.length());
            pstmt1.setString(6, s_userid);
            pstmt1.setString(7, v_isopen);
            pstmt1.setString(8, s_userid);
            pstmt1.setString(9, s_grcode);
            pstmt1.setInt(10, v_cnt);
            pstmt1.setString(11, v_categorycd);
            pstmt1.setString(12, "1");

            isOk1 = pstmt1.executeUpdate(); // ���� �ش� content �� empty_clob()�� �����ϰ� ���� ���� ��Ʈ������ ġȯ�Ѵ�.
            isOk2 = UploadUtil.fnRegisterAttachFile(box);
            // isOk3 = this.insertUpFileNew(connMgr,v_tabseq, v_seq,v_types, box);
            isOk3 = this.insertUpFile(connMgr, v_tabseq, v_seq, v_types, box);

            if (isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
                isOk = 1;
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
                isOk = 0;
            }

        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
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
     * QNA ����Ҷ�(�亯)
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertQnaAns(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;
        int isOk2 = 0;
        int v_cnt = 0;
        String s_grcode = box.getSession("tem_grcode");
        int v_seq = box.getInt("p_seq");
        String v_types = "";
        String v_title = box.getString("p_title");
        String v_contents = StringManager.replace(box.getString("content"), "<br>", "\n");
        String v_isopen = "Y";
        String s_userid = "";
        String v_categorycd = box.getString("p_categorycd");

        s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql1);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            // ------------------------------------------------------------------------------------
            sql = " select max(to_number(types)) from TZ_HOMEQNA  ";
            sql += "  where tabseq = " + v_tabseq + " and seq = " + v_seq;
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_types = String.valueOf((ls.getInt(1) + 1));
            } else {
                v_types = "1";
            }
            // //////////////////////////////// �Խ��� table �� �Է� ///////////////////////////////////////////////////////////////////
            sql1 = "insert into TZ_HOMEQNA(tabseq, seq, types, title, contents, indate, inuserid, isopen, luserid, ldate, cnt, grcode,categorycd)                ";
            // sql1 +=
            // "                values (?, ?, ?, ?, empty_clob(), to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?) ";
            sql1 += "                values (?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'),?,?,?) ";

            int index = 1;
            pstmt = connMgr.prepareStatement(sql1);
            pstmt.setInt(index++, v_tabseq);
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, v_types);
            pstmt.setString(index++, v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_contents), v_contents.length());
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_isopen);
            pstmt.setString(index++, s_userid);
            pstmt.setInt(index++, v_cnt);
            pstmt.setString(index++, s_grcode);
            pstmt.setString(index++, v_categorycd);

            isOk = pstmt.executeUpdate();
            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, v_types, box); // ����÷���ߴٸ� ����table�� insert
            // sql2 = "select contents from tz_HOMEQNA where tabseq = " + v_tabseq + " and  seq = " + v_seq+ " and types = '"+v_types+"'";
            // connMgr.setOracleCLOB(sql2, v_contents); // (��Ÿ ���� ���)
            /*
             * 05.11.16 �̳��� //isOk3 = this.deleteUpFile(connMgr, box);
             */

            if (isOk > 0 && isOk2 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
                isOk = 1;
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
                isOk = 0;
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
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
     * QNA �����Ͽ� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int updateQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        ListSet ls = null;
        int isOk = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        int isOk4 = 0;
        int v_seq = box.getInt("p_seq");

        String v_types = box.getString("p_types");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_content"));
        String s_userid = box.getSession("userid");
        String s_grcode = box.getSession("tem_grcode");

        String v_categorycd = box.getStringDefault("p_categorycd", "OO");

        Vector<String> v_del_savefile = box.getVector("p_del_savefile");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql1);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            // ------------------------------------------------------------------------------------

            sql = " update TZ_HOMEQNA set title = ? , contents = ?,          ";
            sql += "            categorycd = ?, luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  where tabseq = ? and seq = ? and types = ? and grcode = ?                       ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_title);
            pstmt.setCharacterStream(2, new StringReader(v_contents), v_contents.length());
            // pstmt.setString(3, v_isopen);
            pstmt.setString(3, v_categorycd);
            pstmt.setString(4, s_userid);
            pstmt.setInt(5, v_tabseq);
            pstmt.setInt(6, v_seq);
            pstmt.setString(7, v_types);
            pstmt.setString(8, s_grcode);

            isOk = pstmt.executeUpdate();
            isOk4 = UploadUtil.fnRegisterAttachFile(box);
            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, v_types, box); // ����÷���ߴٸ� ����table�� insert

            isOk3 = this.deleteUpFile(connMgr, box); // ������ ������ �ִٸ� ����table���� ����

            if (isOk > 0 && isOk2 > 0 && isOk3 > 0 && isOk4 > 0) {
                connMgr.commit();
                if (v_del_savefile != null) {
                    FileManager.deleteFile(v_del_savefile); // DB ���� ���ó���� �Ϸ�Ǹ� �ش� ÷������ ����
                }
            } else
                connMgr.rollback();
        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null)
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e) {
                }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk * isOk2 * isOk3;
    }

    /**
     * QNA �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql1 = "";
        int isOk1 = 1;
        int isOk2 = 1;

        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");
        int v_upfilecnt = box.getInt("p_upfilecnt"); // ������ ������ִ� ���ϼ�
        Vector<String> v_savefile = box.getVector("p_savefile");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            // ------------------------------------------------------------------------------------
            if (v_types.equals("0")) { // ���������� �亯���û���
                sql1 = " delete from TZ_HOMEQNA    ";
                sql1 += "  where tabseq = ? and seq = ?";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(1, v_tabseq);
                pstmt1.setInt(2, v_seq);

            } else {
                sql1 = " delete from TZ_HOMEQNA";
                sql1 += "  where tabseq = ? and seq = ? and types = ?  ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(1, v_tabseq);
                pstmt1.setInt(2, v_seq);
                pstmt1.setString(3, v_types);

            }

            isOk1 = pstmt1.executeUpdate();

            if (v_upfilecnt > 0) {

                sql = "delete from	TZ_HOMEFILE where tabseq = ? and seq =	?";

                pstmt1 = connMgr.prepareStatement(sql);

                pstmt1.setInt(1, v_tabseq);
                pstmt1.setInt(2, v_seq);

                isOk2 = pstmt1.executeUpdate();
            }

            if (isOk1 > 0 && isOk2 > 0) {
                connMgr.commit();
                if (v_savefile != null) {
                    FileManager.deleteFile(v_savefile); // ÷������ ����
                }
            } else
                connMgr.rollback();
        }

        catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
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
        return isOk1 * isOk2;
    }

    /**
     * QNA ���ο� �ڷ����� ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    @SuppressWarnings("unchecked")
    public int insertUpFile(DBConnectionManager connMgr, int p_tabseq, int p_seq, String types, RequestBox box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 1;

        ArrayList<String> arySaveFileName = (ArrayList) box.getObject("arySaveFileName");
        ArrayList<String> aryRealFileName = (ArrayList) box.getObject("aryRealFileName");

        String s_userid = box.getSession("userid");

        System.out.println("p_tabseq : " + p_tabseq);
        System.out.println("p_seq : " + p_seq);
        System.out.println("types : " + types);
        try {
            // ---------------------- �ڷ� ��ȣ �����´� ----------------------------
            sql = "select NVL(max(fileseq),	0) from	tz_homefile	where tabseq = " + p_tabseq + " and seq = " + p_seq + " and types = " + types;
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            ls.close();
            // ------------------------------------------------------------------------------------

            // //////////////////////////////// ���� table �� �Է� ///////////////////////////////////////////////////////////////////
            sql2 = "insert	into tz_homefile(tabseq, seq, fileseq, types, realfile, savefile, luserid,	ldate)";
            sql2 += " values (?, ?, ?, ?, ?, ?,?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt2 = connMgr.prepareStatement(sql2);

            for (int i = 0; i < arySaveFileName.size(); i++) {
                if (!arySaveFileName.get(i).equals("")) { // ���� ���ε� �Ǵ� ���ϸ� üũ�ؼ� db�� �Է��Ѵ�
                    pstmt2.setInt(1, p_tabseq);
                    pstmt2.setInt(2, p_seq);
                    pstmt2.setInt(3, v_fileseq);
                    pstmt2.setString(4, types);
                    pstmt2.setString(5, aryRealFileName.get(i));
                    pstmt2.setString(6, arySaveFileName.get(i));
                    pstmt2.setString(7, s_userid);
                    isOk2 = pstmt2.executeUpdate();
                    v_fileseq++;
                    System.out.println(aryRealFileName.get(i));
                    System.out.println(arySaveFileName.get(i));
                }
            }
        } catch (Exception ex) {
            FileManager.deleteFile(arySaveFileName); // �Ϲ�����, ÷������ ������ ����..
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
        }
        return isOk2;
    }

    /**
     * QNA ���ο� �ڷ����� ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public int insertUpFileNew(DBConnectionManager connMgr, int p_tabseq, int p_seq, String types, RequestBox box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 1;

        int FILE_LIMITTemp = 1;

        String[] v_realFileName = new String[FILE_LIMITTemp];
        String[] v_newFileName = new String[FILE_LIMITTemp];

        for (int i = 0; i < FILE_LIMITTemp; i++) {
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));

            System.out.println("v_realFileName[" + i + "] : " + v_realFileName[i]);
            System.out.println("v_newFileName[" + i + "] : " + v_newFileName[i]);
        }
        /*
         * ArrayList<String> arySaveFileName = (ArrayList)box.getObject("arySaveFileName"); ArrayList<String> aryRealFileName =
         * (ArrayList)box.getObject("aryRealFileName");
         */
        String s_userid = box.getSession("userid");

        try {
            // ---------------------- �ڷ� ��ȣ �����´� ----------------------------
            sql = "select NVL(max(fileseq),	0) from	tz_homefile	where tabseq = " + p_tabseq + " and seq = " + p_seq + "and types = " + types;
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            ls.close();
            // ------------------------------------------------------------------------------------

            // //////////////////////////////// ���� table �� �Է� ///////////////////////////////////////////////////////////////////
            sql2 = "insert	into tz_homefile(tabseq, seq, fileseq, types, realfile, savefile, luserid,	ldate)";
            sql2 += " values (?, ?, ?, ?, ?, ?,?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt2 = connMgr.prepareStatement(sql2);
            for (int i = 0; i < FILE_LIMITTemp; i++) {

                if (!v_realFileName[i].equals("")) { // ���� ���ε� �Ǵ� ���ϸ� üũ�ؼ� db�� �Է��Ѵ�

                    pstmt2.setInt(1, p_tabseq);
                    pstmt2.setInt(2, p_seq);
                    pstmt2.setInt(3, v_fileseq);
                    pstmt2.setString(4, types);
                    pstmt2.setString(5, v_realFileName[i]);
                    pstmt2.setString(6, v_newFileName[i]);
                    pstmt2.setString(7, s_userid);
                    isOk2 = pstmt2.executeUpdate();
                    v_fileseq++;
                    System.out.println(v_realFileName[i]);
                    System.out.println(v_newFileName[i]);
                }
            }
            if (v_fileseq > 0) {
                connMgr.commit();
            }
        } catch (Exception ex) {
            FileManager.deleteFile(v_newFileName, FILE_LIMITTemp); // �Ϲ�����, ÷������ ������ ����..
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
                }
            }
        }
        return isOk2;
    }

    /**
     * ���õ� �ڷ����� DB���� ����
     * 
     * @param connMgr DB Connection Manager
     * @param box receive from the form object and session
     * @param p_filesequence ���� ���� ����
     * @return
     * @throws Exception
     */
    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box) throws Exception {
        PreparedStatement pstmt3 = null;
        String sql = "";
        String sql3 = "";
        ListSet ls = null;
        int isOk3 = 1;
        int v_seq = box.getInt("p_seq");

        Vector<String> v_del_fileseq = box.getVector("p_del_fileseq");

        try {

            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            // ------------------------------------------------------------------------------------

            sql3 = "delete from tz_homefile where tabseq = " + v_tabseq + " and seq =? and fileseq = ?";
            pstmt3 = connMgr.prepareStatement(sql3);
            for (int i = 0; i < v_del_fileseq.size(); i++) {
                int v_fileseq = Integer.parseInt((String) v_del_fileseq.elementAt(i));

                pstmt3.setInt(1, v_seq);
                pstmt3.setInt(2, v_fileseq);
                isOk3 = pstmt3.executeUpdate();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql3);
            throw new Exception("sql = " + sql3 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk3;
    }

    /**
     * ���õ� �ڷ�� �Խù� �󼼳��� select
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public DataBox selectQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        DataBox dbox = null;

        String v_process = box.getString("p_process");
        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");

        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        try {

            connMgr = new DBConnectionManager();

            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql.append(" SELECT TABSEQ FROM TZ_BDS WHERE TYPE = " + SQLString.Format(v_type));

            ls = connMgr.executeQuery(sql.toString());

            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            // ------------------------------------------------------------------------------------
            sql.setLength(0);

            sql.append(" SELECT A.TYPES, A.SEQ, A.INUSERID, A.TITLE, A.CONTENTS, A.CATEGORYCD, A.INDATE ,C.NAME, A.CNT, \n");
            sql.append("  		A.ISOPEN, B.REALFILE, B.SAVEFILE, B.FILESEQ, D.CODENM CATEGORYNM , A.OKYN1, A.ISOPEN \n");
            // sql.append("  		A.ISOPEN, B.REALFILE, B.SAVEFILE, B.FILESEQ, D.CLASSNAME CATEGORYNM , A.OKYN1, A.ISOPEN \n");
            sql.append("   FROM TZ_HOMEQNA A, TZ_HOMEFILE B, TZ_MEMBER C, TZ_CODE D \n");
            // sql.append("   FROM TZ_HOMEQNA A, TZ_HOMEFILE B, TZ_MEMBER C, TZ_SUBJATT D \n");
            sql.append("  WHERE A.TABSEQ = B.TABSEQ(+) AND A.SEQ  =  B.SEQ(+) AND A.TYPES  =  B.TYPES(+) \n");
            sql.append("    AND A.INUSERID = C.USERID(+) \n");
            sql.append("    AND A.CATEGORYCD = D.CODE(+) \n");
            // sql.append("    AND A.CATEGORYCD = D.UPPERCLASS(+) \n");
            sql.append("    AND D.GUBUN(+) = '0088' \n");
            // sql.append("    AND D.MIDDLECLASS = '000' \n");
            // sql.append("    AND D.LOWERCLASS = '000' \n");
            sql.append("    AND A.GRCODE = C.GRCODE \n");
            sql.append("    AND A.GRCODE = " + SQLString.Format(s_grcode));
            sql.append("    AND A.TABSEQ = " + v_tabseq + " AND A.SEQ = " + v_seq + " AND A.TYPES = " + SQLString.Format(v_types));

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                // ------------------- 2003.12.25 ���� -------------------------------------------------------------------
                dbox = ls.getDataBox();
                if (!dbox.getString("d_realfile").equals("")) {
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }

            }
            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);

            if (v_process.equals("selectView")) {
                sql.setLength(0);

                sql.append(" UPDATE TZ_HOMEQNA SET CNT = CNT + 1 WHERE TABSEQ = " + v_tabseq + " AND SEQ = " + v_seq + " AND TYPES = "
                        + SQLString.Format(v_types));

                connMgr.executeUpdate(sql.toString());
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

        return dbox;
    }

    public DataBox selectQnaUD(RequestBox box, String UpDown) throws Exception {
        DBConnectionManager connMgr = null;

        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");

        if (UpDown.equals("U")) {
            v_seq = v_seq + 1;
        } else if (UpDown.equals("D")) {
            v_seq = v_seq - 1;
        }
        String v_types = box.getString("p_types");

        String s_grcode = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));

        try {

            connMgr = new DBConnectionManager();

            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql.append(" SELECT TABSEQ FROM TZ_BDS WHERE TYPE = " + SQLString.Format(v_type));

            ls = connMgr.executeQuery(sql.toString());

            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            // ------------------------------------------------------------------------------------
            sql.setLength(0);

            sql.append(" SELECT A.TYPES, A.SEQ, A.INUSERID, A.TITLE, A.CONTENTS, A.CATEGORYCD, A.INDATE ,C.NAME, A.CNT, \n");
            sql.append("  		A.ISOPEN, B.REALFILE, B.SAVEFILE, B.FILESEQ, D.CODENM CATEGORYNM , A.OKYN1 \n");
            sql.append("   FROM TZ_HOMEQNA A, TZ_HOMEFILE B, TZ_MEMBER C, TZ_CODE D \n");
            sql.append("  WHERE A.TABSEQ = B.TABSEQ(+) AND A.SEQ  =  B.SEQ(+) AND A.TYPES  =  B.TYPES(+) \n");
            sql.append("    AND A.INUSERID = C.USERID(+) \n");
            sql.append("    AND A.CATEGORYCD = D.CODE(+) \n");
            sql.append("    AND D.GUBUN(+) = '0088' \n");
            sql.append("    AND A.GRCODE = C.GRCODE \n");
            sql.append("    AND A.GRCODE = " + SQLString.Format(s_grcode));
            sql.append("    AND A.TABSEQ = " + v_tabseq + " AND A.SEQ = " + v_seq + " AND A.TYPES = " + SQLString.Format(v_types));

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
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

        return dbox;
    }

    /**
     * ���õ� �ڷ�� �Խù� �󼼳��� select
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public ArrayList<DataBox> selectAns(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls2 = null;
        String sql = "";

        int v_seq = box.getInt("p_seq");
        String s_grcode = box.getSession("tem_grcode");

        String selectSql = "";
        ArrayList<DataBox> list = null;
        list = new ArrayList<DataBox>();
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            // ------------------------------------------------------------------------------------
            selectSql = "select a.types, a.seq, a.inuserid, a.title, a.contents, a.categorycd, a.indate ,c.name, a.cnt, ";
            selectSql += " a.isopen, b.realfile, b.savefile, b.fileseq, d.codenm categorynm ";
            selectSql += " from tz_homeqna a, tz_homefile b, tz_member c, tz_code d ";

            // ������ : 05.11.09 ������ : �̳��� _(+) ����
            selectSql += " where a.tabseq  =  b.tabseq(+) and a.seq  =  b.seq(+) and a.types  =  b.types(+) ";
            selectSql += " and a.inuserid  =  c.userid(+) ";
            selectSql += " and a.grcode  = " + SQLString.Format(s_grcode);
            selectSql += " and a.categorycd  =  d.code(+) ";
            selectSql += " and d.gubun(+)    =  '0088' ";
            selectSql += " and a.tabseq = " + v_tabseq + " and a.seq = " + v_seq + " and a.types != 0 ";

            // sql = selectSql.toString();
            ls2 = connMgr.executeQuery(selectSql);

            while (ls2.next()) {
                // ------------------- 2003.12.25 ���� -------------------------------------------------------------------
                dbox = ls2.getDataBox();
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
            if (ls2 != null) {
                try {
                    ls2.close();
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
     * �Խ��� ��ȣ�ޱ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public static String printPageList(int totalPage, int currPage, int blockSize) throws Exception {

        currPage = (currPage == 0) ? 1 : currPage;
        String str = "";
        if (totalPage > 0) {
            PageList pagelist = new PageList(totalPage, currPage, blockSize);

            str += "<table border='0' width='100%' align='center'>";
            str += "<tr>";
            // str += "    <td width='100%' align='center' valign='middle'>";

            if (pagelist.previous()) {
                str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + pagelist.getPreviousStartPage()
                        + "')\"><img src=\"/images/user/button/pre.gif\" border=\"0\" align=\"middle\"></a></td>  ";
            } else {
                str += "<td align='center' valign='middle'><img src=\"/images/user/button/pre.gif\" border=\"0\" align=\"middle\"></td>";
            }

            for (int i = pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) {
                if (i == currPage) {
                    str += "<td align='center' valign='middle'><strong>" + i + "</strong>" + "</td>";
                } else {
                    str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + i + "')\">" + i + "</a></td> ";
                }
            }

            if (pagelist.next()) {
                str += "<td align='center' valign='middle'><a href=\"javascript:goPage('" + pagelist.getNextStartPage()
                        + "')\"><img src=\"/images/user/button/next.gif\"  border=\"0\" align=\"middle\"></a></td>";
            } else {
                str += "<td align='center' valign='middle'><img src=\"/images/user/button/next.gif\" border=\"0\" align=\"middle\"></td>";
            }

            /*
             * if (str.equals("")) { str += "<�ڷᰡ �����ϴ�."; }
             */
            // str += "    </td>";
            // str += "    <td width='15%' align='center'>";
            // str += "    </td>";
            str += "</tr>";
            str += "</table>";
        }
        return str;
    }

    /**
     * �Խ��� ��ȣ�ޱ� 2 (���������� - ������ �����Ҷ�)
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     * 
     * 
     *             public static String printPageList(int totalPage, int currPage, int blockSize, int tab) throws Exception {
     * 
     *             currPage = (currPage == 0) ? 1 : currPage; String str=""; if(totalPage > 0) { PageList pagelist = new
     *             PageList(totalPage,currPage,blockSize);
     * 
     * 
     *             str += "
     *             <table border='0' width='100%' align='center'>
     *             "; str += "
     *             <tr>
     *             "; str += "
     *             <td width='100%' align='center' valign='middle'>";
     * 
     *             if (pagelist.previous()) { str += "<a href=\"javascript:goPage('" + pagelist.getPreviousStartPage() + "', "','"+tab+"')\"><img
     *             src=\"/images/board/prev.gif\" border=\"0\" align=\"middle\"></a>  "; }else{ str +=
     *             "<img src=\"/images/board/prev.gif\" border=\"0\" align=\"middle\">"; }
     * 
     *             for (int i=pagelist.getStartPage(); i <= pagelist.getEndPage(); i++) { if (i == currPage) { str += "<B>" + i + "</B>" + " "; } else { str
     *             += "<a href=\"javascript:goPage('" + i + "', "','"+tab+"')\">" + i + "</a> "; } }
     * 
     *             if (pagelist.next()) { str += "<a href=\"javascript:goPage('" + pagelist.getNextStartPage() + "', "','"+tab+"')\"> <img
     *             src=\"/images/board/next.gif\"  border=\"0\" align=\"middle\"></a>"; }else{ str +=
     *             "<img src=\"/images/board/next.gif\" border=\"0\" align=\"middle\">"; }
     * 
     *             if (str.equals("")) { str += "�ڷᰡ �����ϴ�."; }
     * 
     *             str += "</td>"; str += "
     *             <td width='15%' align='center'>";
     * 
     * 
     * 
     *             str += "</td>"; str += "
     *             </tr>
     *             "; str += "
     *             </table>
     *             "; } return str; }
     */

    /**
     * QNA ������ ����Ҷ�(����)
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertCommentQnaQue(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        int isOk1 = 1;

        String v_commentqna = box.getString("commentqna");
        int v_seq = box.getInt("p_seq");

        String v_types = box.getString("p_types");

        String s_userid = "";

        s_userid = "���";
        s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            // ---------------------- �Խ��� ������ ��ȣ�� �����´� ----------------------------
            sql = "select NVL(max(commentseq), 0) from TZ_COMMENTQNA";
            sql += " where tabseq=" + v_tabseq + " and seq = " + v_seq + " and types = " + v_types;

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_commentseq = ls.getInt(1) + 1;
            ls.close();
            // ------------------------------------------------------------------------------------
            // //////////////////////////////// �Խ��� table �� �Է� ///////////////////////////////////////////////////////////////////
            sql1 = "insert into TZ_COMMENTQNA(tabseq, seq, types, commentseq, inuserid, commentqna, cdate)";
            sql1 += "                values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, v_tabseq);
            pstmt1.setInt(2, v_seq);
            pstmt1.setString(3, v_types);
            pstmt1.setInt(4, v_commentseq);
            pstmt1.setString(5, s_userid);
            pstmt1.setString(6, v_commentqna);

            isOk1 = pstmt1.executeUpdate(); // ���� �ش� content �� empty_clob()�� �����ϰ� ���� ���� ��Ʈ������ ġȯ�Ѵ�.

            if (isOk1 > 0) {
                if (connMgr != null) {
                    try {
                        connMgr.commit();
                    } catch (Exception e10) {
                    }
                }
            } else {
                if (connMgr != null) {
                    try {
                        connMgr.rollback();
                    } catch (Exception e10) {
                    }
                }
            }
        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
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
     * QNAȭ�� ������ ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList QNA ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectcommentListQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql1 = "";
        // ������ : 05.11.16 ������ : �̳��� _ totalcount �ϱ����� ��������
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        String count_sql = "";

        DataBox dbox = null;

        int v_pageno = box.getInt("p_pageno");
        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");

        try {
            connMgr = new DBConnectionManager();
            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql1 = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql1);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();
            // ------------------------------------------------------------------------------------
            list = new ArrayList<DataBox>();
            head_sql += " select a.seq,a.types,a.commentseq,a.inuserid,a.commentqna,a.cdate, b.name ";
            body_sql += "   from TZ_COMMENTQNA a, tz_member b ";

            // ������ : 05.11.09 ������ : �̳��� _(+) ����
            body_sql += "  where a.inuserid  =  b.userid(+) ";
            body_sql += "  and tabseq = " + v_tabseq;
            body_sql += "  and seq = " + v_seq;
            body_sql += "  and types = " + v_types;
            order_sql += " order by seq desc, types asc, commentseq asc ";

            sql = head_sql + body_sql + group_sql + order_sql;
            count_sql = "select count(*) " + body_sql;
            ls = connMgr.executeQuery(sql);
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); // ��ü row ���� ��ȯ�Ѵ�

            ls.setPageSize(row); // �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); // ������������ȣ�� �����Ѵ�.

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
     * QNA �ڵ� ����Ʈ (config��,����)
     * 
     * @param box receive from the form object and session
     * @return ArrayList QNA ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> getCodeList(String config, int levels, RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select code, codenm from tz_code            ";
            sql += "  where gubun  = " + config;
            sql += "    and levels = " + levels;
            sql += " order by code asc";

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
     * QnA ī�װ� ���̺� ����Ʈ
     * 
     * @param name ����Ʈ�ڽ���
     * @param selected ���ð�
     * @param event �̺�Ʈ��
     * @param allcheck ��ü����
     * @return
     * @throws Exception
     */
    public static String homepageGetQnaCategoryTable(String grcode) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        ListSet ls2 = null;

        String sql = "";
        String sql1 = "";
        String sql2 = "";
        String result = "";
        int count = 0;
        CodeData data = null;
        int i = 1;

        result = "\n   <table width='675' border='0' cellpadding='0' cellspacing='0' background='/images/user/support/faq_bg.gif'>" + "\n     <tr>"
                + "\n      <td valign='top'><img src='/images/user/support/qna_top.gif'></td>" + "\n     </tr>" + "\n     <tr>"
                + "\n      <td align='center'><table width='500' border='0' cellspacing='0' cellpadding='0'>";

        try {
            connMgr = new DBConnectionManager();

            sql1 = "select count(*) cnt from tz_code where gubun='0046' and levels='1' ";
            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                count = ls1.getInt("cnt");
            }
            ls1.close();

            sql = " select code, codenm from tz_code  where gubun  = '0046' and levels = '1'";
            sql += " order by code asc                        ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CodeData();
                data.setCode(ls.getString("code"));
                data.setCodenm(ls.getString("codenm"));

                sql2 = "select count(*) categoryCnt from tz_homeqna where categorycd = " + SQLString.Format(data.getCode()) + " and types = 0";
                sql2 += " and grcode = '" + grcode + "'";
                sql2 += " and tabseq = 5";
                // sql2 += " and types = 0" ;

                ls2 = connMgr.executeQuery(sql2);
                ls2.next();
                String categoryCnt = ls2.getString(1);
                ls2.close();

                if (i % 3 == 1) {
                    result += "\n <tr> ";
                }
                result += "\n     <td width='180' class='tbl_cfaq'><img src='/images/user/support/bl_c.gif' width='9' height='9'> "
                        + "\n       <a href=javascript:changeCategory('" + data.getCode() + "')>" + data.getCodenm() + "</a>(" + categoryCnt + ")</td>";

                if (i % 3 == 0 && i != (count)) {
                    result += "\n  </tr> " + "\n   <tr> " + "\n    <td><img src='/images/user/support/faq_line-1.gif'></td>"
                            + "\n    <td><img src='/images/user/support/faq_line-1.gif'></td>"
                            + "\n    <td><img src='/images/user/support/faq_line-1.gif'></td>" + "\n  </tr>";
                }

                if (i != 3 && i % 3 == 0) {
                    result += "\n </tr> ";
                }

                i++;

            }
            int mod_cnt = (count) % 3;

            if (mod_cnt != 0) {
                for (int j = 0; j < (3 - mod_cnt); j++) {
                    result += "<td width='180' class='tbl_cfaq'>" + j + "</td>";
                }
            }

            result += "</tr>";

        }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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

        result += "\n   </table>" + "\n    </td>" + "\n  </tr>" + "\n  <tr>"
                + "\n    <td valign='bottom'><img src='/images/user/support/faq_bo.gif' ></td>" + "\n  </tr>" + "\n</table>";

        return result;

    }

    /**
     * QnA ī�װ� ���̺� ����Ʈ
     * 
     * @param name ����Ʈ�ڽ���
     * @param selected ���ð�
     * @param event �̺�Ʈ��
     * @param allcheck ��ü����
     * @return
     * @throws Exception
     */
    public static String homepageGetQnaCategoryTable() throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls1 = null;
        ListSet ls2 = null;

        String sql = "";
        String sql1 = "";
        String sql2 = "";
        String result = "";
        int count = 0;

        CodeData data = null;
        int i = 1;

        result = "\n   <table width='675' border='0' cellpadding='0' cellspacing='0' background='/images/user/support/faq_bg.gif'>" + "\n     <tr>"
                + "\n      <td valign='top'><img src='/images/user/support/qna_top.gif'></td>" + "\n     </tr>" + "\n     <tr>"
                + "\n      <td align='center'><table width='500' border='0' cellspacing='0' cellpadding='0'>";

        try {
            connMgr = new DBConnectionManager();

            sql1 = "select count(*) cnt from tz_code where gubun='0046' and levels='1' ";
            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                count = ls1.getInt("cnt");
            }
            ls1.close();

            sql = " select code, codenm from tz_code  where gubun  = '0046' and levels = '1'";
            sql += " order by code asc                        ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new CodeData();
                data.setCode(ls.getString("code"));
                data.setCodenm(ls.getString("codenm"));

                sql2 = "select count(*) categoryCnt from tz_homeqna where categorycd = " + SQLString.Format(data.getCode());
                ls2 = connMgr.executeQuery(sql2);
                ls2.next();
                String categoryCnt = ls2.getString(1);
                ls2.close();

                if (i % 3 == 1) {
                    result += "\n <tr> ";
                }
                result += "\n     <td width='180' class='tbl_cfaq'><img src='/images/user/support/bl_c.gif' width='9' height='9'> "
                        + "\n       <a href=javascript:changeCategory('" + data.getCode() + "')>" + data.getCodenm() + "</a>(" + categoryCnt + ")</td>";

                if (i % 3 == 0 && i != (count)) {
                    result += "\n  </tr> " + "\n   <tr> " + "\n    <td><img src='/images/user/support/faq_line-1.gif'></td>"
                            + "\n    <td><img src='/images/user/support/faq_line-1.gif'></td>"
                            + "\n    <td><img src='/images/user/support/faq_line-1.gif'></td>" + "\n  </tr>";
                }

                if (i != 3 && i % 3 == 0) {
                    result += "\n </tr> ";
                }

                i++;

            }
            int mod_cnt = (count) % 3;

            if (mod_cnt != 0) {
                for (int j = 0; j < (3 - mod_cnt); j++) {
                    result += "<td width='180' class='tbl_cfaq'>" + j + "</td>";
                }
            }

            result += "</tr>";

        }

        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
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

        result += "\n   </table>" + "\n    </td>" + "\n  </tr>" + "\n  <tr>"
                + "\n    <td valign='bottom'><img src='/images/user/support/faq_bo.gif' ></td>" + "\n  </tr>" + "\n</table>";

        return result;

    }

    /**
     * QNA ������ �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteCommentQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk1 = 1;

        int v_seq = box.getInt("p_seq");
        String v_types = box.getString("p_types");
        int v_commentseq = box.getInt("p_commentseq");

        try {
            connMgr = new DBConnectionManager();

            // ---------------------- ��Խ������������� ������ tabseq�� �����Ѵ� ----------------------------
            sql = "select tabseq from tz_bds where type = " + SQLString.Format(v_type);
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_tabseq = ls.getInt(1);
            ls.close();

            sql = " delete from TZ_COMMENTQNA    ";
            sql += "  where tabseq = ? and seq = ? and types = ? and commentseq = ? ";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, v_tabseq);
            pstmt.setInt(2, v_seq);
            pstmt.setString(3, v_types);
            pstmt.setInt(4, v_commentseq);

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
