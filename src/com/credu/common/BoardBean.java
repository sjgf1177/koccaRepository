//**********************************************************
//  1. ��      ��: �Խ���
//  2. ���α׷���: BoardBean.java
//  3. ��      ��: �Խ���
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: mscho 2004.01.15
//  7. ��      ��:
//**********************************************************
package com.credu.common;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.dunet.common.util.UploadUtil;
import com.namo.SmeNamoMime;

public class BoardBean {
    private ConfigSet config;
    private int row;
    private static final String FILE_TYPE = "p_file"; //		���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 10; //	  �������� ���õ� ����÷�� ����

    public BoardBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        �� ����� �������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �ڷ�� ���̺��ȣ
     * 
     * @param box receive from the form object and session
     * @return int �ڷ�� ���̺��ȣ
     * @throws Exception
     */
    public int selectTableseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        String v_type = box.getStringDefault("p_type", "");
        String v_grcode = box.getStringDefault("p_grcode", "0000000");
        String v_comp = box.getStringDefault("p_comp", "0000000000");
        String v_subj = box.getStringDefault("p_subj", "0000000000");
        String v_year = box.getStringDefault("p_year", "0000");
        String v_subjseq = box.getStringDefault("p_subjseq", "0000");

        try {
            connMgr = new DBConnectionManager();

            sql = " select tabseq from TZ_BDS      ";
            sql += "\n  where type    = " + StringManager.makeSQL(v_type);
            sql += "\n    and grcode  = " + StringManager.makeSQL(v_grcode);
            sql += "\n    and comp    = " + StringManager.makeSQL(v_comp);
            sql += "\n    and subj    = " + StringManager.makeSQL(v_subj);
            sql += "\n    and year    = " + StringManager.makeSQL(v_year);
            sql += "\n    and subjseq = " + StringManager.makeSQL(v_subjseq);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("tabseq");
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
        return result;
    }

    /**
     * �����Խ��� ���̺��ȣ
     * 
     * @param box receive from the form object and session
     * @return int �ڷ�� ���̺��ȣ
     * @throws Exception
     */
    public int selectSBTableseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt4 = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ListSet ls3 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        int v_tabseq = 0;
        // int result = 0;

        String v_user_id = box.getSession("userid");
        String v_type = box.getString("p_type");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        if (v_subj.equals("")) {
            v_subj = box.getSession("s_subj");
        }
        if (v_year.equals("")) {
            v_year = box.getSession("s_year");
        }
        if (v_subjseq.equals("")) {
            v_subjseq = box.getSession("s_subjseq");
        }

        try {
            connMgr = new DBConnectionManager();

            sql1 = " select tabseq from TZ_BDS      ";
            sql1 += "  where type    = " + StringManager.makeSQL(v_type);
            sql1 += " and subj       = " + StringManager.makeSQL(v_subj);
            sql1 += " and year       = " + StringManager.makeSQL(v_year);
            sql1 += " and subjseq    = " + StringManager.makeSQL(v_subjseq);
            ls1 = connMgr.executeQuery(sql1);

            if (ls1.next()) { //TZ_BDS�� �ش� ���̺� �������� �ִ� ���
                v_tabseq = ls1.getInt("tabseq");
            } else { //TZ_BDS�� �ش� ���̺� �������� ���� ���
                sql2 = " select count(subj) cnt from TZ_SUBJSEQ      ";
                sql2 += "  where subj    = " + StringManager.makeSQL(v_subj);
                sql2 += " and year       = " + StringManager.makeSQL(v_year);
                sql2 += " and subjseq    = " + StringManager.makeSQL(v_subjseq);
                ls2 = connMgr.executeQuery(sql2);

                if (ls2.next() && ls2.getInt("cnt") > 0) { //���� �����Ǿ��ִ� �����̸�
                    sql3 = "select NVL(max(tabseq), 0) from TZ_BDS";
                    ls3 = connMgr.executeQuery(sql3);
                    ls3.next();
                    v_tabseq = ls3.getInt(1) + 1;
                    ls3.close();

                    sql4 = "insert into TZ_BDS(tabseq,type,subj,year,subjseq,sdesc,luserid,ldate) ";
                    sql4 += " values(?,?,?,?,?,?,?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                    pstmt4 = connMgr.prepareStatement(sql4);
                    pstmt4.setInt(1, v_tabseq);
                    pstmt4.setString(2, v_type);
                    pstmt4.setString(3, v_subj);
                    pstmt4.setString(4, v_year);
                    pstmt4.setString(5, v_subjseq);
                    pstmt4.setString(6, v_subj + "����" + v_subjseq + "�����Խ���");
                    pstmt4.setString(7, v_user_id);
                    pstmt4.executeUpdate();
                } else {
                    v_tabseq = 0;
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls3.close();
                } catch (Exception e) {
                }
            }
            if (pstmt4 != null) {
                try {
                    pstmt4.close();
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
        return v_tabseq;
    }

    /**
     * �����ڷ�� ���̺��ȣ
     * 
     * @param box receive from the form object and session
     * @return int �ڷ�� ���̺��ȣ
     * @throws Exception
     */
    public int selectSDTableseq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt4 = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ListSet ls3 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";
        int v_tabseq = 0;
        // int result = 0;

        String v_user_id = box.getSession("userid");
        String v_type = box.getString("p_type");
        String v_subj = box.getString("p_subj");
        if (v_subj.equals("")) {
            v_subj = box.getSession("s_subj");
        }

        try {
            connMgr = new DBConnectionManager();

            sql1 = " select tabseq from TZ_BDS      ";
            sql1 += "  where type    = " + StringManager.makeSQL(v_type);
            sql1 += " and subj    = " + StringManager.makeSQL(v_subj);
            ls1 = connMgr.executeQuery(sql1);

            if (ls1.next()) { //TZ_BDS�� �ش� ���̺� �������� �ִ� ���
                v_tabseq = ls1.getInt("tabseq");
            } else { //TZ_BDS�� �ش� ���̺� �������� ���� ���
                sql2 = " select count(subj) cnt from TZ_SUBJ      ";
                sql2 += "  where subj    = " + StringManager.makeSQL(v_subj);
                ls2 = connMgr.executeQuery(sql2);

                if (ls2.next() && ls2.getInt("cnt") > 0) { //���� �����Ǿ��ִ� �����̸�
                    sql3 = "select NVL(max(tabseq), 0) from TZ_BDS";
                    ls3 = connMgr.executeQuery(sql3);
                    ls3.next();
                    v_tabseq = ls3.getInt(1) + 1;
                    ls3.close();

                    sql4 = "insert into TZ_BDS(tabseq,type,subj,sdesc,luserid,ldate) ";
                    sql4 += " values(?,?,?,?,?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
                    pstmt4 = connMgr.prepareStatement(sql4);
                    pstmt4.setInt(1, v_tabseq);
                    pstmt4.setString(2, v_type);
                    pstmt4.setString(3, v_subj);
                    pstmt4.setString(4, v_subj + "�����ڷ��");
                    pstmt4.setString(5, v_user_id);
                    pstmt4.executeUpdate();
                } else {
                    v_tabseq = 0;
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
            if (ls2 != null) {
                try {
                    ls2.close();
                } catch (Exception e) {
                }
            }
            if (ls3 != null) {
                try {
                    ls3.close();
                } catch (Exception e) {
                }
            }
            if (pstmt4 != null) {
                try {
                    pstmt4.close();
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
        return v_tabseq;
    }

    /**
     * �ڷ�� ����Ʈȭ�� select
     * 
     * @param box receive from the form object and session
     * @return ArrayList �ڷ�� ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectBoardList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ������ : 05.11.14 ������ : �̳��� _ totalrowcount ���� ���� ��������
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        String count_sql = "";
        DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");
        int v_pageno = box.getInt("p_pageno");
        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql = " select a.seq seq, a.userid userid, a.name name, a.title title, count(b.realfile) upfilecnt,\n";
            head_sql += "        a.indate indate, a.cnt cnt, a.refseq refseq, a.levels levels, a.position position\n";
            body_sql += "   from TZ_BOARD a, TZ_BOARDFILE b\n";
            // 2005.11.04_�ϰ��� : Orcle -> Mssql ����.
            //sql += "  where a.tabseq = b.tabseq(+)\n";
            //sql += "    and a.seq    = b.seq(+)\n";
            body_sql += "  where a.tabseq  =  b.tabseq(+)\n";
            body_sql += "    and a.seq     =  b.seq(+)\n";
            body_sql += "    and a.tabseq = " + v_tabseq;

            if (!v_searchtext.equals("")) { //    �˻�� ������
                //v_pageno = 1;   //      �˻��� ��� ù��° �������� �ε��ȴ�

                if (v_search.equals("name")) { //    �̸����� �˻��Ҷ�
                    body_sql += " and a.name like lower(" + StringManager.makeSQL("%" + v_searchtext + "%") + ")\n";
                } else if (v_search.equals("title")) { //    �������� �˻��Ҷ�
                    body_sql += " and a.title like lower(" + StringManager.makeSQL("%" + v_searchtext + "%") + ")\n";
                } else if (v_search.equals("content")) { //    �������� �˻��Ҷ�
                    //sql += " and dbms_lob.instr(a.content, " + StringManager.makeSQL(v_searchtext) + ",1,1) <> 0\n";
                    body_sql += " and a.content like " + StringManager.makeSQL("%" + v_searchtext + "%"); //   Oracle 9i ��
                }
            }

            group_sql += " group by a.seq, a.userid, a.name, a.title, a.indate, a.cnt, a.refseq, a.levels, a.position\n";
            order_sql += " order by a.refseq desc, position asc\n";

            //System.out.println("sql==>"+ sql);
            sql = head_sql + body_sql + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            //count_sql= "select count(*) from TZ_BOARD where tabseq = " + v_tabseq; //������ ��ü ����� ���ͼ� �˻��� �������� ��������
            count_sql = "select count(*) from (" + head_sql + body_sql + group_sql + ")\n";
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     ��ü row ���� ��ȯ�Ѵ�

            ls.setPageSize(row); //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); //     ������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�
            //int totalrowcount = ls.getTotalCount();

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(row));
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
     * ���õ� �ڷ�� �Խù� �󼼳��� select
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��ȸ�� ������
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public DataBox selectBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;
        String v_upcnt = "Y";

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");
        // int v_upfilecnt = (box.getInt("p_upfilecnt")>0?box.getInt("p_upfilecnt"):1);

        //String [] realfile = new String [v_upfilecnt];
        //String [] savefile= new String [v_upfilecnt];
        //int [] fileseq = new int [v_upfilecnt];
        Vector realfileVector = new Vector();
        Vector savefileVector = new Vector();
        Vector fileseqVector = new Vector();
        try {
            connMgr = new DBConnectionManager();

            sql = " select a.seq seq, a.userid userid, a.name name, a.title title, b.fileseq fileseq, b.realfile realfile, a.content content,  ";
            sql += "        b.savefile savefile, a.indate indate, a.cnt cnt, a.refseq refseq, a.levels levels, a.position position ,             ";
            sql += "         (select count(realfile) from TZ_BOARDFILE where tabseq = a.tabseq and seq = a.seq) upfilecnt                         ";
            sql += " from TZ_BOARD a, TZ_BOARDFILE b  ";
            sql += "  where a.tabseq  =  b.tabseq(+) ";
            sql += "    and a.seq     =  b.seq(+)   ";
            sql += "    and a.tabseq = " + v_tabseq;
            sql += "    and a.seq    = " + v_seq;
            ls = connMgr.executeQuery(sql);

            for (int i = 0; ls.next(); i++) {

                dbox = ls.getDataBox();
                realfileVector.addElement(dbox.getString("d_realfile"));
                savefileVector.addElement(dbox.getString("d_savefile"));
                fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));

            }

            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);

            if (!v_upcnt.equals("N")) {
                connMgr.executeUpdate("update TZ_BOARD set cnt = cnt + 1 where  tabseq = " + v_tabseq + " and seq = " + v_seq);
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
        return dbox;
    }

    /**
     * ���ο� �ڷ�� ���� ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;
        Statement stmt1 = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        // String sql2   = "";
        // String v_type = "";
        int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;
        int v_seq = 0;

        int v_tabseq = box.getInt("p_tabseq");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("name");
        // String v_isedu   = box.getString("p_isedu"); // (2005.9)�н�â�Խ���,����ڷ�� ���� ����(�н�â�Խ��� = '1', ����ڷ�� = '')

        /*
         * if (box.getSession("gadmin").substring(0,1).equals("A")) { s_usernm =
         * "���"; }
         */

        if (box.getSession("gadmin").substring(0, 1).equals("A1")) {
            s_userid = "���";
        } else {
            s_userid = box.getSession("userid");
        }

        //System.out.println("�н�â�ΰ�??--["+v_isedu+"]");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            stmt1 = connMgr.createStatement();

            //----------------------   �Խ��� ��ȣ �����´� ----------------------------
            sql = "select NVL(max(seq), 0) from TZ_BOARD where tabseq = " + v_tabseq;
            rs1 = stmt1.executeQuery(sql);
            if (rs1.next()) {
                v_seq = rs1.getInt(1) + 1;
            }
            rs1.close();
            //-------------------------------------------------------------------------

            //----------------------   �Խ��� table �� �Է�  --------------------------
            int index = 1;
            sql1 = " insert into TZ_BOARD(tabseq, seq, userid, name, indate, title, content, cnt, refseq, levels, position, luserid, ldate)  ";
            //            sql1 += " values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, empty_clob(), ?, ?, ?, ?, ?,  to_char(sysdate, 'YYYYMMDDHH24MISS'))    ";
            sql1 += " values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, ?, ?,  to_char(sysdate, 'YYYYMMDDHH24MISS'))    ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(index++, v_tabseq);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);
            pstmt1.setString(index++, v_title);
            pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            //			pstmt1.setString(index++, v_content);
            pstmt1.setInt(index++, 0);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setInt(index++, 1);
            pstmt1.setInt(index++, 1);
            pstmt1.setString(index++, s_userid);

            isOk1 = pstmt1.executeUpdate();
            //			sql2 = "select content from TZ_BOARD where tabseq = " + v_tabseq + " and seq = " + v_seq;
            //			connMgr.setOracleCLOB(sql2, v_content);       //      (��Ÿ ���� ���)

            // ���Ͼ��ε�
            isOk2 = UploadUtil.fnRegisterAttachFile(box);
            isOk3 = this.insertUpFile(connMgr, v_tabseq, v_seq, box);

            //System.out.println("isOk1=======================>"+isOk1);
            //System.out.println("isOk2=======================>"+isOk2);

            if (isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
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
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
            }
            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (Exception e1) {
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
                    connMgr.setAutoCommit(true);
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
     * �����Խ��� ���
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int insertStudyBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;
        Statement stmt1 = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";
        // String sql2   = "";
        // String v_type = "";
        int isOk1 = 1;
        int isOk2 = 1;
        // int isOk3     = 1;
        int v_seq = 0;

        int v_tabseq = box.getInt("p_tabseq");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("name");
        // String v_isedu   = box.getString("p_isedu"); // (2005.9)�н�â�Խ���,����ڷ�� ���� ����(�н�â�Խ��� = '1', ����ڷ�� = '')

        /*
         * if (box.getSession("gadmin").substring(0,1).equals("A")) { s_usernm =
         * "���"; }
         */

        if (box.getSession("gadmin").substring(0, 1).equals("A1")) {
            s_userid = "���";
        } else {
            s_userid = box.getSession("userid");
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            stmt1 = connMgr.createStatement();

            //----------------------   �Խ��� ��ȣ �����´� ----------------------------
            sql = "select NVL(max(seq), 0) from TZ_BOARD where tabseq = " + v_tabseq;
            rs1 = stmt1.executeQuery(sql);
            if (rs1.next()) {
                v_seq = rs1.getInt(1) + 1;
            }
            rs1.close();
            //-------------------------------------------------------------------------

            //----------------------   �Խ��� table �� �Է�  --------------------------
            int index = 1;
            sql1 = " insert into TZ_BOARD(tabseq, seq, userid, name, indate, title, content, cnt, refseq, levels, position, luserid, ldate)  ";
            sql1 += " values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, ?, ?,  to_char(sysdate, 'YYYYMMDDHH24MISS'))    ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(index++, v_tabseq);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);
            pstmt1.setString(index++, v_title);
            pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            // 			pstmt1.setString(index++, v_content);
            pstmt1.setInt(index++, 0);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setInt(index++, 1);
            pstmt1.setInt(index++, 1);
            pstmt1.setString(index++, s_userid);

            isOk1 = pstmt1.executeUpdate();

            // ���Ͼ��ε�
            //             isOk2 = UploadUtil.fnRegisterAttachFile(box);
            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, box);

            if (isOk1 > 0 && isOk2 > 0) {
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
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
            }
            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (Exception e1) {
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
                    connMgr.setAutoCommit(true);
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
     * 
     * ���ο� �ڷ�� �亯 ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int replyBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;

        Statement stmt1 = null;
        Statement stmt2 = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;

        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        String sql4 = "";

        // int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;
        int v_seq = 0;
        String v_isedu = box.getString("p_isedu"); // (2005.9)�н�â�Խ���,����ڷ�� ���� ����(�н�â�Խ��� = '1', ����ڷ�� = '')
        int v_tabseq = box.getInt("p_tabseq");
        int v_refseq = box.getInt("p_refseq");
        int v_levels = box.getInt("p_levels");
        int v_position = box.getInt("p_position");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));

        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("name");

        if (box.getSession("gadmin").substring(0, 1).equals("A")) {
            s_usernm = "���";
        }
        //System.out.println("v_content--["+v_content+"]");
        //System.out.println("�н�â�ΰ�??--["+v_isedu+"]");
        if (v_isedu.equals("")) {
            /*********************************************************************************************/
            // ���𿡵��� ���� ó��  (Mime Document Parsing �� �̹��� ���ε�)
            ConfigSet conf = new ConfigSet();
            SmeNamoMime namo = new SmeNamoMime(v_content); // ��ü����
            boolean result = namo.parse(); // ���� �Ľ� ����
            if (!result) { // �Ľ� ���н�
                System.out.println(namo.getDebugMsg()); // ����� �޽��� ���
                return 0;
            }
            if (namo.isMultipart()) { // ������ ��Ƽ��Ʈ���� �Ǵ�
                String v_server = conf.getProperty("autoever.url.value");
                String fPath = conf.getProperty("dir.namo"); // ���� ���� ��� ����
                String refUrl = conf.getProperty("url.namo");
                ; // ������ ����� ������ �����ϱ� ���� ���
                String prefix = "board" + v_tabseq; // ���ϸ� ���ξ�
                result = namo.saveFile(fPath, v_server + refUrl, prefix); // ���� ���� ����
            }
            if (!result) { // �������� ���н�
                System.out.println(namo.getDebugMsg()); // ����� �޽��� ���
                return 0;
            }
            v_content = namo.getContent(); // ���� ����Ʈ ���
            /*********************************************************************************************/
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ���� �亯�� ��ġ ��ĭ������ ����
            sql1 = "update TZ_BOARD ";
            sql1 += "   set position = position + 1 ";
            sql1 += " where tabseq   = ? ";
            sql1 += "   and refseq   = ? ";
            sql1 += "   and position > ? ";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setInt(1, v_tabseq);
            pstmt1.setInt(2, v_refseq);
            pstmt1.setInt(3, v_position);
            pstmt1.executeUpdate();

            stmt1 = connMgr.createStatement();
            //----------------------   �Խ��� ��ȣ �����´� ----------------------------
            sql2 = "select NVL(max(seq), 0) from TZ_BOARD where tabseq = " + v_tabseq;
            rs1 = stmt1.executeQuery(sql2);
            if (rs1.next()) {
                v_seq = rs1.getInt(1) + 1;
            }
            //------------------------------------------------------------------------------------

            /////////////////////////////////   �Խ��� table �� �Է�  ///////////////////////////////////////////////////////////////////
            int index = 1;
            sql3 = " insert into TZ_BOARD(tabseq, seq, userid, name, indate, title, content, cnt, refseq, levels, position, luserid, ldate)  ";
            sql3 += " values (?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))    ";

            pstmt2 = connMgr.prepareStatement(sql3);
            pstmt2.setInt(index++, v_tabseq);
            pstmt2.setInt(index++, v_seq);
            pstmt2.setString(index++, s_userid);
            pstmt2.setString(index++, s_usernm);
            pstmt2.setString(index++, v_title);
            pstmt2.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            //connMgr.setCharacterStream(pstmt2, 6, v_content); //      Oracle 9i or Weblogic 6.1 �� ���
            pstmt2.setInt(index++, 0);
            pstmt2.setInt(index++, v_refseq);
            pstmt2.setInt(index++, v_levels + 1);
            pstmt2.setInt(index++, v_position + 1);
            pstmt2.setString(index++, s_userid);
            isOk2 = pstmt2.executeUpdate();

            //WebLogic 6.1�ΰ��
            //            sql4 = "select content from TZ_BOARD where tabseq = " + v_tabseq + " and seq = " + v_seq + " for update";
            //            connMgr.setOracleCLOB(sql4, v_content);       //      clob �ʵ忡 ��Ʈ���� �̿�,  data �� �ִ´�(Weblogic �� ���)

            isOk3 = this.insertUpFile(connMgr, v_tabseq, v_seq, box);

            if (isOk2 > 0 && isOk3 > 0) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1 + "\r\n" + sql2 + "\r\n" + sql3 + "\r\n" + sql4);
            throw new Exception("sql = " + sql1 + "\r\n" + "sql2 = " + sql2 + "\r\n" + "sql3 = " + sql3 + "\r\n" + "sql4 = " + sql4 + "\r\n" + ex.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
                } catch (Exception e) {
                }
            }
            if (stmt1 != null) {
                try {
                    stmt1.close();
                } catch (Exception e1) {
                }
            }
            if (stmt2 != null) {
                try {
                    stmt2.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e1) {
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
        //return isOk2*isOk3;
        return isOk2;
    }

    /**
     * ���õ� �ڷ� �󼼳��� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int updateBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ResultSet rs1 = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        // String sql2 = "";

        int isOk1 = 1, isOk2 = 1, isOk3 = 1;
        // int isOk4 = 1;
        // String v_isedu = box.getString("p_isedu"); // (2005.9)�н�â�Խ���,����ڷ�� ���� ����(�н�â�Խ��� = '1', ����ڷ�� = '')
        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");
        int v_upfilecnt = box.getInt("p_upfilecnt"); //  ������ ������ִ� ���ϼ�
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));

        //        Vector v_del_savefile = box.getVector("p_del_savefile"); // ���� ���� ���ϵ�

        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("name");

        Vector v_savefile = new Vector();
        Vector v_filesequence = new Vector();

        if (box.getSession("gadmin").substring(0, 1).equals("A")) {
            s_usernm = "���";
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            for (int i = 0; i < v_upfilecnt; i++) {
                if (!box.getString("p_fileseq" + i).equals("")) {
                    v_savefile.addElement(box.getString("p_savefile" + i)); //		������ ������ִ� ���ϸ� �߿���	������ ���ϵ�
                    v_filesequence.addElement(box.getString("p_fileseq" + i)); //		������	������ִ� ���Ϲ�ȣ	�߿��� ������ ���ϵ�
                }
            }

            sql1 = "update TZ_BOARD set title = ?, content=?, userid = ?, name = ?, luserid = ?, ldate = to_char(sysdate, 'YYYYMMDDHH24MISS')";
            sql1 += "  where tabseq = ? and seq = ?";

            pstmt1 = connMgr.prepareStatement(sql1);
            pstmt1.setString(1, v_title);
            //  connMgr.setCharacterStream(pstmt1, 2, v_content);           //Oracle 9i or Weblogic 6.1 �� ���
            pstmt1.setCharacterStream(2, new StringReader(v_content), v_content.length());
            pstmt1.setString(3, s_userid);
            pstmt1.setString(4, s_usernm);
            pstmt1.setString(5, s_userid);
            pstmt1.setInt(6, v_tabseq);
            pstmt1.setInt(7, v_seq);

            isOk1 = pstmt1.executeUpdate();

            //WebLogic 6.1�ΰ��
            //sql2 = "select content from TZ_BOARD where tabseq = " + v_tabseq + " and seq = " + v_seq ;
            //connMgr.setOracleCLOB(sql2, v_content);       //      clob �ʵ忡 ��Ʈ���� �̿�,  data �� �ִ´�(Weblogic �� ���)

            //			isOk2 =	this.insertUpFile(connMgr, v_tabseq, v_seq,	box);		//		����÷���ߴٸ� ����table��	insert
            //			System.out.println("isOk2 ==========> " + isOk2);
            //			isOk4 = UploadUtil.fnRegisterAttachFile(box);
            //			System.out.println("isOk4 ==========> " + isOk4);
            //			isOk3 =	this.deleteUpFile(connMgr, box);		//	   ������ ������ �ִٸ�	����table���� ����
            //			System.out.println("isOk3 ==========> " + isOk3);
            //
            //			if(isOk1 > 0 &&	isOk2 >	0 && isOk3 > 0)	{
            //				connMgr.commit();
            //				if (v_del_savefile != null)	{
            //					FileManager.deleteFile(v_del_savefile);			//	 DB	���� ���ó����	�Ϸ�Ǹ� �ش� ÷������ ����
            //				}
            //			}  else connMgr.rollback();

            isOk3 = this.deleteUpFile(connMgr, box, v_filesequence); // ������ ������ �ִٸ�	����table���� ����
            isOk2 = this.insertUpFile(connMgr, v_tabseq, v_seq, box); // ����÷���ߴٸ� ����table��	insert

            if (isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                connMgr.commit();
                //connMgr.rollback();
                isOk1 = 1;
            } else {
                connMgr.rollback();
                isOk1 = 0;
            }

        } catch (Exception ex) {

            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (rs1 != null) {
                try {
                    rs1.close();
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

        return isOk1 * isOk2 * isOk3;
    }

    /**
     * ���õ� �Խù� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int deleteBoard(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 0;
        int isOk2 = 0;

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");

        Vector v_savefile = box.getVector("p_savefile");
        int v_upfilecnt = v_savefile.size(); //  ������ ������ִ� ���ϼ�
        //System.out.println("savefile =======>" + v_savefile + " size ===>" + v_savefile.size());

        // �亯 ���� üũ(�亯 ������ �����Ұ�)
        if (this.selectBoard(v_tabseq, v_seq) == 0) {

            try {
                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);

                isOk1 = 1;
                isOk2 = 1;
                sql1 = "delete from TZ_BOARD where tabseq = ? and seq = ? ";
                pstmt1 = connMgr.prepareStatement(sql1);
                pstmt1.setInt(1, v_tabseq);
                pstmt1.setInt(2, v_seq);
                isOk1 = pstmt1.executeUpdate();

                if (v_upfilecnt > 0) {
                    //sql2 = "delete from TZ_BOARDFILE where tabseq = ? and seq =  ?";
                    sql2 = "delete from TZ_BOARDFILE where tabseq = " + v_tabseq + " and seq = " + v_seq;
                    //System.out.println("sql2==>"+ sql2);
                    //pstmt2 = connMgr.prepareStatement(sql2);
                    //pstmt2.setInt(1, v_tabseq);
                    //pstmt2.setInt(2, v_seq);
                    //isOk2 = pstmt2.executeUpdate();
                    isOk2 = connMgr.executeUpdate(sql2);
                }
                if (isOk1 > 0 && isOk2 > 0) {
                    connMgr.commit();
                    if (v_upfilecnt > 0) {
                        FileManager.deleteFile(v_savefile); //   ÷������ ����
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
        }

        return isOk1 * isOk2;

    }

    /**
     * ������ ���� �亯 ���� üũ
     * 
     * @param seq �Խ��� ��ȣ
     * @return result 0 : �亯 ����, 1 : �亯 ����
     * @throws Exception
     */
    public int selectBoard(int tabseq, int seq) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        int result = 0;

        try {
            connMgr = new DBConnectionManager();

            sql = "  select count(*) cnt                         ";
            sql += "  from                                        ";
            sql += "    (select tabseq, refseq, levels, position  ";
            sql += "       from TZ_BOARD                          ";
            sql += "      where tabseq = " + tabseq;
            sql += "        and seq = " + seq;
            sql += "     ) a, TZ_BOARD b                          ";
            sql += " where a.tabseq = b.tabseq                    ";
            sql += "   and a.refseq = b.refseq                    ";
            sql += "   and b.levels = (a.levels+1)                ";
            sql += "   and b.position = (a.position+1)            ";
            System.out.println("sql+=>" + sql);
            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                result = ls.getInt("cnt");
            }
        } catch (Exception ex) {
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
        return result;
    }

    ///////////////////////////////////////////////////////  ���� ���̺�   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * ���ο� �ڷ����� ���
     * 
     * @param connMgr DB Connection Manager
     * @param p_seq �Խù� �Ϸù�ȣ
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertUpFile(DBConnectionManager connMgr, int p_tabseq, int p_seq, RequestBox box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 1;

        //----------------------   ���ε�Ǵ� ������ ������ �˰� �ڵ��ؾ��Ѵ�  --------------------------------
        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];

        for (int i = 0; i < FILE_LIMIT; i++) {
            //			System.out.println("box.getRealFileName(p_file) ====> " + box.getRealFileName(FILE_TYPE + [i]));
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));
        }

        //      ArrayList<String> arySaveFileName  = (ArrayList)box.getObject("arySaveFileName");
        //		ArrayList<String> aryRealFileName = (ArrayList)box.getObject("aryRealFileName");

        String s_userid = box.getSession("userid");

        try {

            //        	if( arySaveFileName != null && arySaveFileName.size() > 0 ) {

            //----------------------   �ڷ� ��ȣ �����´� ----------------------------
            sql = "select NVL(max(fileseq), 0) from TZ_BOARDFILE where tabseq = " + p_tabseq + " and seq =   " + p_seq;

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------
            //////////////////////////////////   ���� table �� �Է�  ///////////////////////////////////////////////////////////////////
            sql2 = "insert into TZ_BOARDFILE(tabseq, seq, fileseq, realfile, savefile, luserid, ldate)";
            sql2 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt2 = connMgr.prepareStatement(sql2);

            for (int i = 0; i < FILE_LIMIT; i++) {
                if (!v_realFileName[i].equals("")) { //		���� ���ε�	�Ǵ� ���ϸ�	üũ�ؼ� db�� �Է��Ѵ�
                    pstmt2.setInt(1, p_tabseq);
                    pstmt2.setInt(2, p_seq);
                    pstmt2.setInt(3, v_fileseq);
                    pstmt2.setString(4, v_realFileName[i]);
                    pstmt2.setString(5, v_newFileName[i]);
                    pstmt2.setString(6, s_userid);

                    System.out.println("p_tabseq ==========> " + p_tabseq);
                    System.out.println("p_seq ==========> " + p_seq);
                    System.out.println("v_fileseq ==========> " + v_fileseq);
                    System.out.println("v_realFileName[i] ==========> " + v_realFileName[i]);
                    System.out.println("v_newFileName[i] ==========> " + v_newFileName[i]);
                    System.out.println("s_userid ==========> " + s_userid);

                    isOk2 = pstmt2.executeUpdate();
                    v_fileseq++;
                }
            }

            //        	}

        } catch (Exception ex) {
            FileManager.deleteFile(v_newFileName, FILE_LIMIT); //	�Ϲ�����, ÷������ ������ ����..
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
    @SuppressWarnings("unchecked")
    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box, Vector p_filesequence) throws Exception {
        PreparedStatement pstmt3 = null;
        // String sql = "";
        String sql3 = "";
        // ListSet ls = null;
        int isOk3 = 1;
        int v_tabseq = box.getInt("p_tabseq");
        // String v_types = box.getString("p_types");
        int v_seq = box.getInt("p_seq");

        //		Vector v_del_fileseq  = box.getVector("p_del_fileseq");

        try {
            sql3 = "delete from TZ_BOARDFILE where tabseq = " + v_tabseq + " and seq =? and fileseq = ?";

            pstmt3 = connMgr.prepareStatement(sql3);
            for (int i = 0; i < p_filesequence.size(); i++) {
                //				int	v_fileseq =	Integer.parseInt((String)v_del_fileseq.elementAt(i));
                int v_fileseq = Integer.parseInt((String) p_filesequence.elementAt(i));

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
     * ���� ���
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int insertReply(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;

        StringBuffer sql = new StringBuffer();

        int isOk = 0;

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");

        String v_content = box.getString("replyContent");
        String s_userid = box.getSession("userid");
        String s_name = box.getSession("name");

        int m_comseq = 0;

        try {

            connMgr = new DBConnectionManager();

            // ��� �Ϸù�ȣ select
            sql.append(" SELECT NVL(MAX(COMSEQ), 0) FROM TZ_BOARDCOMMENT \n");
            sql.append(" WHERE TABSEQ = " + v_tabseq + " \n");
            sql.append("   AND SEQ = " + v_seq);

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                m_comseq = ls.getInt(1) + 1;
            }

            sql.setLength(0);
            ls.close();

            sql.append(" INSERT INTO TZ_BOARDCOMMENT ");
            sql.append(" VALUES( ?, ?, ?, ?, ?, ?, ?, TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS') ) ");

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setInt(1, v_tabseq);
            pstmt.setInt(2, v_seq);
            pstmt.setInt(3, m_comseq);
            pstmt.setString(4, s_userid);
            pstmt.setString(5, s_name);
            pstmt.setCharacterStream(6, new StringReader(v_content), v_content.length());
            pstmt.setString(7, s_userid);

            isOk = pstmt.executeUpdate();

            sql.setLength(0);

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e1) {
                }
            }
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e2) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e3) {
                }
            }
        }

        return isOk;
    }

    public int deleteReply(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;

        StringBuffer sql = new StringBuffer();

        int isOk = 0;

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");

        int r_comseq = box.getInt("r_comseq");
        String r_userid = box.getString("r_userid");

        try {

            connMgr = new DBConnectionManager();

            sql.append(" DELETE TZ_BOARDCOMMENT \n");
            sql.append("  WHERE TABSEQ = ? \n");
            sql.append("    AND SEQ = ? \n");
            sql.append("    AND COMSEQ = ? \n");
            sql.append("    AND USERID = ? \n");

            pstmt = connMgr.prepareStatement(sql.toString());

            pstmt.setInt(1, v_tabseq);
            pstmt.setInt(2, v_seq);
            pstmt.setInt(3, r_comseq);
            pstmt.setString(4, r_userid);

            isOk = pstmt.executeUpdate();

            sql.setLength(0);

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, box, sql.toString());
            throw new Exception("sql = " + sql.toString() + "\r\n" + e.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e1) {
                }
            }
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e2) {
                }
            }
        }

        return isOk;

    }

    /**
     * ���� ����Ʈ
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public ArrayList<DataBox> selectBoardReplyList(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;

        StringBuffer sql = new StringBuffer();

        DataBox dbox = null;

        int v_tabseq = box.getInt("p_tabseq");
        int v_seq = box.getInt("p_seq");

        try {

            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql.append(" SELECT * FROM TZ_BOARDCOMMENT \n");
            sql.append(" WHERE TABSEQ = " + v_tabseq + " \n");
            sql.append("   AND SEQ = " + v_seq + " \n");
            sql.append(" ORDER BY COMSEQ ");

            ls = connMgr.executeQuery(sql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();

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

    public static String convertBody(String contents) throws Exception {

        String result = "";

        result = StringManager.replace(contents, "<HTML>", "");
        result = StringManager.replace(result, "<HEAD>", "");
        result = StringManager.replace(result, "<META NAME=\"GENERATOR\" Content=\"Microsoft DHTML Editing Control\">", "");
        result = StringManager.replace(result, "<TITLE>", "");
        result = StringManager.replace(result, "</TITLE>", "");
        result = StringManager.replace(result, "</HEAD>", "");
        result = StringManager.replace(result, "<BODY>", "");
        result = StringManager.replace(result, "</BODY>", "");
        result = StringManager.replace(result, "</HTML>", "");

        return result;
    }

}
