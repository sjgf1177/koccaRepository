//**********************************************************
//	1. ��	   ��: ���� ������
//	2. ���α׷���: AdminQnaBean.java
//	3. ��	   ��: ���� ������
//	4. ȯ	   ��: JDK 1.4
//	5. ��	   ��: 1.0
//	6. ��	   ��: ������ 2005.	9. 20
//	7. ��	   ��:
//**********************************************************

package com.credu.tutor;

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
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

/**
 * �ڷ�� ����(ADMIN)
 * 
 * @date : 2005. 9
 * @author : S.W.Kang
 */
public class AdminQnaBean {
    private static final String FILE_TYPE = "p_file"; //		���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 10; //	  �������� ���õ� ����÷�� ����

    public static int getFILE_LIMIT() {
        return FILE_LIMIT;
    }

    private ConfigSet config;

    private int row;

    public AdminQnaBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //		  �� �����	�������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ���õ� �Խù� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        //		Connection conn	= null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 0;
        int isOk2 = 0;

        int v_seq = box.getInt("p_seq");
        int v_upfilecnt = box.getInt("p_upfilecnt"); //	������ ������ִ� ���ϼ�
        Vector<String> v_savefile = box.getVector("p_savefile");

        // �亯 ���� üũ(�亯 ������ �����Ұ�)
        if (this.selectBoard(v_seq) == 0) {

            try {
                connMgr = new DBConnectionManager();
                connMgr.setAutoCommit(false);////

                sql1 = "delete from	TZ_BOARD	where tabseq = 135 and seq = ? ";

                pstmt1 = connMgr.prepareStatement(sql1);

                pstmt1.setInt(1, v_seq);

                isOk1 = pstmt1.executeUpdate();

                if (v_upfilecnt > 0) {
                    sql2 = "delete from	TZ_BOARDFILE where tabseq = 135 and seq =	?";

                    pstmt2 = connMgr.prepareStatement(sql2);

                    pstmt2.setInt(1, v_seq);

                    isOk2 = pstmt2.executeUpdate();

                } else {
                    isOk2 = 1;
                }

                if (isOk1 > 0 && isOk2 > 0) {
                    connMgr.commit();
                    if (v_savefile != null) {
                        FileManager.deleteFile(v_savefile); //	 ÷������ ����
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
                if (pstmt2 != null) {
                    try {
                        pstmt2.close();
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
     * ���õ� �ڷ����� DB���� ����
     * 
     * @param connMgr DB Connection Manager
     * @param box receive from the form object and session
     * @param p_filesequence ���� ���� ����
     * @return
     * @throws Exception
     */
    public int deleteUpFile(DBConnectionManager connMgr, RequestBox box, Vector<String> p_filesequence) throws Exception {
        PreparedStatement pstmt3 = null;
        String sql3 = "";
        int isOk3 = 1;

        // int	v_tabseq = StringManager.toInt(box.getStringDefault("p_tabseq","135"));

        int v_seq = box.getInt("p_seq");

        try {
            sql3 = "delete from TZ_BOARDFILE where tabseq = 135 and seq =? and fileseq = ?";

            pstmt3 = connMgr.prepareStatement(sql3);

            for (int i = 0; i < p_filesequence.size(); i++) {
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
     * ���ο� �ڷ�� ���� ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "";//,sql2 = "";
        int isOk1 = 1;
        int isOk2 = 1;

        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));

        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("name");

        try {
            connMgr = new DBConnectionManager();

            //----------------------   �Խ��� ��ȣ �����´�	----------------------------
            sql = "select NVL(max(seq),	0) from	TZ_BOARD where tabseq = 135";
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_seq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------
            /*********************************************************************************************/
            // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�.
            try {
                v_content = (String) NamoMime.setNamoContent(v_content);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            //////////////////////////////////	 �Խ���	table �� �Է�  ///////////////////////////////////////////////////////////////////
            sql1 = "insert	into TZ_BOARD(tabseq, seq, userid, name, indate, title, content, cnt, refseq, levels, position, luserid, ldate)               ";
            sql1 += " values (?, ?, ?, ?, to_char(sysdate,'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

            int index = 1;
            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setInt(index++, 135);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);
            pstmt1.setString(index++, v_title);
            //pstmt1.setCharacterStream(index++, new StringReader(v_content1), v_content1.length());
            pstmt1.setString(index++, v_content);
            pstmt1.setInt(index++, 0);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setInt(index++, 1);
            pstmt1.setInt(index++, 1);
            pstmt1.setString(index++, s_userid);

            isOk1 = pstmt1.executeUpdate();

            //����:lyh  ����:2005-11-16  ����: content ���� �ֱ�
            /*
             * sql2 =
             * "select content from tz_board where tabseq = 135 and seq = " +
             * v_seq ; connMgr.setOracleCLOB(sql2, v_content1); // (��Ÿ ���� ���)
             */

            isOk2 = this.insertUpFile(connMgr, 135, v_seq, box);

        } catch (Exception ex) {
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
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
        return isOk1 * isOk2;
    }

    /**
     * ���ο� �ڷ����� ���
     * 
     * @param connMgr DB Connection Manager
     * @param p_tabseq �Խ��� �Ϸù�ȣ
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

        //----------------------   ���ε�Ǵ� ������ ������	�˰� �ڵ��ؾ��Ѵ�  --------------------------------

        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];

        for (int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));
        }
        //----------------------------------------------------------------------------------------------------------------------------

        String s_userid = box.getSession("userid");

        try {
            //----------------------	�ڷ� ��ȣ �����´� ----------------------------
            sql = "select NVL(max(fileseq),	0) from	TZ_BOARDFILE	where tabseq = 135 and seq =	" + p_seq;
            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------

            //////////////////////////////////�Խ���	table �� �Է�  ///////////////////////////////////////////////////////////////////
            //sql2 =	"insert	into TZ_BOARD(tabseq, seq, userid, name, indate, title, content, cnt, refseq, levels, position, luserid, ldate)               ";
            //sql2 +=	" values (?, ?, ?, ?, to_char(sysdate,	'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

            //////////////////////////////////	 ���� table	�� �Է�	 ///////////////////////////////////////////////////////////////////
            sql2 = "insert	into TZ_BOARDFILE(tabseq, seq, fileseq, realfile, savefile, luserid,	ldate)";
            //sql2 +=	" values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";
            sql2 += " values (?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt2 = connMgr.prepareStatement(sql2);

            for (int i = 0; i < FILE_LIMIT; i++) {
                if (!v_realFileName[i].equals("")) { //		���� ���ε�	�Ǵ� ���ϸ�	üũ�ؼ� db�� �Է��Ѵ�
                    pstmt2.setInt(1, 135);
                    pstmt2.setInt(2, p_seq);
                    pstmt2.setInt(3, v_fileseq);
                    pstmt2.setString(4, v_realFileName[i]);
                    pstmt2.setString(5, v_newFileName[i]);
                    pstmt2.setString(6, s_userid);

                    isOk2 = pstmt2.executeUpdate();
                    v_fileseq++;
                }
            }
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
     * ���� �亯 ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:reply success,0:reply fail
     * @throws Exception
     */
    public int replyQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        //		Statement stmt = null;
        PreparedStatement pstmt = null;
        PreparedStatement pstmt1 = null;
        String sql = "";
        String sql1 = "", sql2 = "";//, sql3 = "";
        //		int isOk  = 1;
        int isOk1 = 1;
        int isOk2 = 1;

        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        //		String v_content1 = "";

        String s_userid = box.getSession("userid");
        String s_usernm = box.getSession("name");
        int v_refseq = box.getInt("p_refseq");
        int v_levels = box.getInt("p_levels");
        int v_position = box.getInt("p_position");
        //System.out.println("v_refseq="+v_refseq);
        //System.out.println("v_levels="+v_levels);
        //System.out.println("v_position="+v_position);

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            // ���� �亯�� ��ġ ��ĭ������ ����
            sql = "update TZ_BOARD ";
            sql += "   set position = position + 1 ";
            sql += " where tabseq   = 135 ";
            sql += "   and refseq   = ? ";
            sql += "   and position > ? ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, v_refseq);
            pstmt.setInt(2, v_position);
            //			isOk =
            pstmt.executeUpdate();

            //			stmt = connMgr.createStatement();

            //----------------------   �Խ��� ��ȣ �����´�	----------------------------
            sql1 = "select NVL(max(seq),	0) from	TZ_BOARD where tabseq = 135";
            ls = connMgr.executeQuery(sql1);
            ls.next();
            int v_seq = ls.getInt(1) + 1;
            ls.close();
            //------------------------------------------------------------------------------------
            /*********************************************************************************************/
            // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�.
            try {
                v_content = (String) NamoMime.setNamoContent(v_content);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            //////////////////////////////////	 �Խ���	table �� �Է�  ///////////////////////////////////////////////////////////////////
            sql2 = "insert	into TZ_BOARD(tabseq, seq, userid, name, indate, title, content, cnt, refseq, levels, position, luserid, ldate)               ";
            //			sql2 +=	" values (?, ?, ?, ?, to_char(sysdate,	'YYYYMMDDHH24MISS'), ?, empty_clob(), ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";
            sql2 += " values (?, ?, ?, ?, to_char(sysdate,	'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS')) ";

            int index = 1;
            pstmt1 = connMgr.prepareStatement(sql2);

            pstmt1.setInt(index++, 135);
            pstmt1.setInt(index++, v_seq);
            pstmt1.setString(index++, s_userid);
            pstmt1.setString(index++, s_usernm);
            pstmt1.setString(index++, v_title);
            pstmt1.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            //			pstmt1.setString(index++, v_content);
            pstmt1.setInt(index++, 0);
            pstmt1.setInt(index++, v_refseq);
            pstmt1.setInt(index++, v_levels + 1);
            pstmt1.setInt(index++, v_position + 1);
            pstmt1.setString(index++, s_userid);

            isOk1 = pstmt1.executeUpdate();

            //����:lyh  ����:2005-11-16  ����: content ���� �ֱ�

            //			sql3 = "select content from tz_board where tabseq = 135 and seq = " + v_seq ;
            //			connMgr.setOracleCLOB(sql3, v_content);       //      (��Ÿ ���� ���)

            isOk2 = this.insertUpFile(connMgr, 135, v_seq, box);

            if (isOk1 > 0 && isOk2 > 0)
                connMgr.commit();
            else
                connMgr.rollback();
        } catch (Exception ex) {
            connMgr.rollback();
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
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
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
        return isOk1 * isOk2;
    }

    /**
     * ������ ���� �亯 ���� üũ
     * 
     * @param seq �Խ��� ��ȣ
     * @return result 0 : �亯 ����, 1 : �亯 ����
     * @throws Exception
     */
    public int selectBoard(int seq) throws Exception {
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
            sql += "      where tabseq = '135'";
            sql += "        and seq = " + seq;
            sql += "     ) a, TZ_BOARD b                          ";
            sql += " where a.tabseq = b.tabseq                    ";
            sql += "   and a.refseq = b.refseq                    ";
            sql += "   and b.levels = (a.levels+1)                ";
            sql += "   and b.position = (a.position+1)            ";
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

    ///////////////////////////////////////////////////////	 ���� ���̺�   /////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /**
     * ���õ� �ڷ�� �Խù� �󼼳��� select
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��ȸ�� ������
     * @throws Exception
     */
    public DataBox selectQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";

        DataBox dbox = null;

        int v_seq = box.getInt("p_seq");
        String v_process = box.getString("p_process");
        // int	v_tabseq = StringManager.toInt(box.getStringDefault("p_tabseq","135"));
        // int	v_upfilecnt	= (box.getInt("p_upfilecnt")>0?box.getInt("p_upfilecnt"):1);

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        //		int	[] fileseq = new int [v_upfilecnt];

        try {
            connMgr = new DBConnectionManager();

            sql = "select a.seq, a.userid, a.name, a.title, a.content, b.fileseq, b.realfile, b.savefile, a.indate, a.cnt ";
            sql += " from TZ_BOARD a, TZ_BOARDFILE b                                              ";
            // ������ : 05.11.03 ������ : �̳��� _ OuterJoin ����
            //			sql	+= " where a.tabseq = b.tabseq(+)                                                 ";
            //			sql += "   and a.seq    = b.seq(+)
            sql += " where a.tabseq = b.tabseq(+)                                                 ";
            sql += "   and a.seq    = b.seq(+)     ";
            sql += "   and a.tabseq = 135";
            sql += "   and a.seq    = " + v_seq;
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                //-------------------   2004.12.25  ����     -------------------------------------------------------------------
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
            if (v_process.equals("select")) {
                connMgr.executeUpdate("update TZ_BOARD set cnt = cnt + 1 where tabseq =135 and seq = " + v_seq);
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
     * �ڷ�� ����Ʈȭ�� select
     * 
     * @param box receive from the form object and session
     * @return ArrayList �ڷ�� ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectQnaList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        //		Connection conn	= null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        /* 2005.11.10_�ϰ��� : TotalCount ���ֱ� ���� ������ ���� ���� " */
        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        //PdsData	data = null;
        DataBox dbox = null;

        //		int	v_tabseq = StringManager.toInt(box.getStringDefault("p_tabseq","135"));

        int v_pageno = box.getInt("p_pageno") == 0 ? 1 : box.getInt("p_pageno");
        int v_pagesize = box.getInt("p_pagesize") == 0 ? 10 : box.getInt("p_pagesize");

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        String s_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            head_sql = "select a.seq, a.userid,a.title, a.name, a.indate, a.cnt, a.refseq, a.levels, a.position  ";
            body_sql += " from TZ_BOARD a                                              ";
            body_sql += " where a.tabseq = 135";
            if (s_gadmin.equals("P1")) {
                body_sql += " and a.refseq in (select seq from tz_board where levels=1 and tabseq=135 and userid =" + SQLString.Format(s_userid) + ") ";
            }
            if (!v_searchtext.equals("")) { //	  �˻�� ������
                if (v_search.equals("name")) { //	  �̸����� �˻��Ҷ�
                    body_sql += " and lower(a.name)	like lower(" + StringManager.makeSQL("%" + v_searchtext + "%") + ")";
                } else if (v_search.equals("title")) { //	  �������� �˻��Ҷ�
                    body_sql += " and lower(a.title) like lower(" + StringManager.makeSQL("%" + v_searchtext + "%") + ")";
                } else if (v_search.equals("content")) { //	  �������� �˻��Ҷ�
                    body_sql += " and a.content like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }

            order_sql += " order by a.refseq desc, a.position asc ";

            sql = head_sql + body_sql + group_sql + order_sql;

            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql); //     ��ü row ���� ��ȯ�Ѵ�
            //			int total_page_count = ls.getTotalPage();       //     ��ü ������ ���� ��ȯ�Ѵ�
            ls.setPageSize(v_pagesize); //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); //     ������������ȣ�� �����Ѵ�.

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
                dbox.put("d_rowcount", new Integer(row));
                dbox.put("d_totalrowcount", new Integer(total_row_count));
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
     * ���õ� �ڷ� �󼼳��� ����
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int updateQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;
        String sql1 = "";
        int isOk1 = 1;
        int isOk2 = 1;
        int isOk3 = 1;

        int v_seq = box.getInt("p_seq");
        int v_upfilecnt = box.getInt("p_upfilecnt"); //	������ ������ִ� ���ϼ�
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));

        Vector<String> v_savefile = new Vector<String>();
        Vector<String> v_filesequence = new Vector<String>();

        for (int i = 0; i < v_upfilecnt; i++) {
            if (!box.getString("p_fileseq" + i).equals("")) {
                v_savefile.addElement(box.getString("p_savefile" + i)); //		������ ������ִ� ���ϸ� �߿���	������ ���ϵ�
                v_filesequence.addElement(box.getString("p_fileseq" + i)); //		 ������	������ִ� ���Ϲ�ȣ	�߿��� ������ ���ϵ�
            }
        }

        String s_userid = box.getSession("userid");
        //		String s_usernm	= box.getSession("name");

        // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�.
        try {
            v_content = (String) NamoMime.setNamoContent(v_content);
        } catch (Exception e) {
            System.out.println(e.toString());
            return 0;
        }

        try {
            connMgr = new DBConnectionManager();

            connMgr.setAutoCommit(false);

            sql1 = "update TZ_BOARD set title = ?, content = ?, luserid = ?, ldate = to_char(sysdate,	'YYYYMMDDHH24MISS')";
            sql1 += "  where tabseq = 135 and seq = ?";

            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_title);
            pstmt1.setString(2, v_content);
            pstmt1.setString(3, s_userid);
            pstmt1.setInt(4, v_seq);

            isOk1 = pstmt1.executeUpdate();

            //����:lyh  ����:2005-11-16  ����: content ���� �ֱ�
            /*
             * sql2 =
             * "select content from tz_board where tabseq = 135 and seq = " +
             * v_seq; connMgr.setOracleCLOB(sql2, v_content); // (��Ÿ ���� ���)
             */

            isOk2 = this.insertUpFile(connMgr, 135, v_seq, box); //		����÷���ߴٸ� ����table��	insert

            isOk3 = this.deleteUpFile(connMgr, box, v_filesequence); //	   ������ ������ �ִٸ�	����table���� ����

            if (isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
                connMgr.commit();
                if (v_savefile != null) {
                    FileManager.deleteFile(v_savefile); //	 DB	���� ���ó����	�Ϸ�Ǹ� �ش� ÷������ ����
                }
            } else
                connMgr.rollback();
        } catch (Exception ex) {

            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
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
        return isOk1 * isOk2 * isOk3;
    }

}
