// **********************************************************
// 1. �� ��: �������� ������
// 2. ���α׷���: HomeGalleryBean.java
// 3. �� ��: �������� ������
// 4. ȯ ��: JDK 1.4
// 5. �� ��: 0.1
// 6. �� ��: Administrator 2005.12.18
// 7. �� ��:
//
// **********************************************************

package com.credu.homepage;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;

import com.credu.library.BoardPaging;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.PageList;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;

/**
 * To change the template for this generated type comment go to Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */
public class HomeGalleryBean {
    private ConfigSet config;
    private static int row = 10;

    private static final String FILE_TYPE = "p_file"; // ���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 3; // �������� ���õ� ����÷�� ����

    public HomeGalleryBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); // �� ����� �������� row ���� �����Ѵ�
            row = 10; // ������ ����
            System.out.println("....... row.....:" + row);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ������ ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList ������ ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = new ArrayList<DataBox>();

        String sql = "";
        String count_sql = "";
        String head_sql = "";
        String body_sql = "";
        // String group_sql = "";
        String order_sql = "";
        DataBox dbox = null;

        String tem_grcode = box.getStringDefault("grcode", box.getSession("tem_grcode"));
        String v_process = box.getString("p_process");
        String v_grtype = box.getString("p_grtype");
        // String s_userid = box.getSession("userid");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();

            if (v_process.equals("mainList")) {
                head_sql = " select * from ( select rownum rnum, seq, savefile_S ";
            } else {

                head_sql = "  select seq, savefile_S, title, userid, luserid, ";
                head_sql += "	 	contents as  contents,  name, indate ";
            }
            body_sql += " 	from TZ_OFFGALLERY			";
            body_sql += " 	where grcode = " + StringManager.makeSQL(tem_grcode) + " and grtype = " + StringManager.makeSQL(v_grtype);
            if (v_process.equals("mainList")) {
                body_sql += " and useyn = 'Y' ";
            }
            order_sql += "	order by seq desc ) where rnum < 4";

            sql = head_sql + body_sql + order_sql;

            ls = connMgr.executeQuery(sql);

            count_sql = "select count(*) " + body_sql;
            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql);
            row = 3;

            ls.setPageSize(row); // �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); // ������������ȣ�� �����Ѵ�.
            int total_page_count = ls.getTotalPage(); // ��ü ������ ���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();
                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(total_page_count));
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
     * ������ �󼼺���
     * 
     * @param box receive from the form object and session
     * @return ArrayList �Խ��� ��ȸ
     * @throws Exception
     */
    public DataBox selectView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        String sql = "";
        String sql1 = "";
        DataBox dbox = null;

        String tem_grcode = box.getStringDefault("p_grcode", box.getSession("tem_grcode"));
        String v_grtype = box.getString("p_grtype");
        String v_process = box.getString("p_process");
        int v_seq = box.getInt("p_seq");
        System.out.println("v_process : " + v_process);

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql1 = "select seq, title, contents," + " 	indate, ldate, userid, luserid, " + " 	name, realfile_L, savefile_L, realfile_S, savefile_S, "
                    + " 	media_realfile, media_savefile, grcode, grtype, useyn, cnt " + " from TZ_OFFGALLERY " + " where seq = " + v_seq + " and grcode = "
                    + StringManager.makeSQL(tem_grcode) + " 	and grtype = " + StringManager.makeSQL(v_grtype);

            ls = connMgr.executeQuery(sql1);
            while (ls.next()) {
                dbox = ls.getDataBox();
            }

            System.out.println("sql1 " + sql1);

            if (v_process.equals("selectView")) {
                connMgr.executeUpdate("update TZ_OFFGALLERY set cnt = cnt + 1 where seq = " + v_seq);
                connMgr.commit();
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
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
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
     * ������ ����ϱ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertGallery(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;
        int isOk2 = 0;
        int v_seq = 0;

        String v_title = box.getString("p_title");
        String v_content = box.getString("content");
        String v_grcode = box.getStringDefault("p_grcode", box.getSession("tem_grcode"));
        String v_grtype = box.getString("p_grtype");
        String v_useyn = box.getStringDefault("p_useyn", "N");

        String s_userid = box.getSession("userid");
        String s_name = box.getSession("name");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "select max(seq) from TZ_OFFGALLERY  ";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_seq = ls.getInt(1) + 1;
            } else {
                v_seq = 1;
            }

            sql1 = " insert into TZ_OFFGALLERY(				\n";
            sql1 += " seq, 			title,			contents,		indate,	\n";
            sql1 += " ldate, 		userid, 		luserid, 		name,	\n";
            sql1 += " grcode, 		grtype,			cnt,			useyn)	\n";
            sql1 += " values (";
            // sql1 += " ?,			?,			empty_clob(),			to_char(sysdate, 'YYYYMMDDHH24MISS')," ;
            sql1 += " ?,			?,			?,			to_char(sysdate, 'YYYYMMDDHH24MISS'),";
            sql1 += " to_char(sysdate, 'YYYYMMDDHH24MISS'),			?, 			 ?,				?,";
            sql1 += " ?, 			?,			?,			?) ";

            int index = 1;
            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            // pstmt.setString(index++, v_content);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, s_name);
            pstmt.setString(index++, v_grcode);
            pstmt.setString(index++, v_grtype);
            pstmt.setInt(index++, 0);
            pstmt.setString(index++, v_useyn);

            isOk = pstmt.executeUpdate();

            isOk2 = this.insertUpFile(connMgr, box, v_seq);

            // sql1 = "select content from TZ_OFFGALLERY where seq = '"+v_seq+"'  ";
            // connMgr.setOracleCLOB(sql1, v_content);

            if (isOk > 0 && isOk2 > 0) {
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
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql ->" + sql1 + "\r\n" + ex.getMessage());
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
     * ������ �����ϱ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int updateGallery(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt = null;
        String sql1 = "";
        int isOk = 0;
        int isOk2 = 0;
        int v_seq = 0;

        v_seq = box.getInt("p_seq");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("P_content"));
        String v_grcode = box.getStringDefault("p_grcode", box.getSession("tem_grcode"));
        String v_grtype = box.getString("p_grtype");
        String v_useyn = box.getStringDefault("p_useyn", "N");

        String s_userid = box.getSession("userid");

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // sql1 = " update TZ_OFFGALLERY set title = ?,	contents = empty_clob(),	\n";
            sql1 = " update TZ_OFFGALLERY set title = ?,	contents = ?,	\n";
            sql1 += " ldate = to_char(sysdate, 'YYYYMMDDHH24MISS'),	\n";
            sql1 += " luserid = ?,		useyn = ?							\n";
            sql1 += " where seq = ? 	and grcode = ? 		and grtype = ?	  ";

            int index = 1;
            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setString(index++, v_title);
            pstmt.setCharacterStream(index++, new StringReader(v_content), v_content.length());
            // pstmt.setString(index++, v_content);
            pstmt.setString(index++, s_userid);
            pstmt.setString(index++, v_useyn);
            pstmt.setInt(index++, v_seq);
            pstmt.setString(index++, v_grcode);
            pstmt.setString(index++, v_grtype);

            isOk = pstmt.executeUpdate();

            isOk2 = this.insertUpFile(connMgr, box, v_seq);
            // sql1 = "select content from TZ_OFFGALLERY where seq = '"+v_seq+"'  ";
            // connMgr.setOracleCLOB(sql1, v_content);

            if (isOk > 0 && isOk2 > 0) {
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
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql ->" + sql1 + "\r\n" + ex.getMessage());
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
     * ������ ���ο� �ڷ����� ���
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */

    public int insertUpFile(DBConnectionManager connMgr, RequestBox box, int p_seq) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        int isOk2 = 1;

        // ---------------------- ���ε�Ǵ� ������ ������ �˰� �ڵ��ؾ��Ѵ� --------------------------------

        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];

        for (int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));
        }

        // ----------------------------------------------------------------------------------------------------------------------------

        try {
            // //////////////////////////////// ���� table �� �Է� ///////////////////////////////////////////////////////////////////
            sql1 = "update	TZ_OFFGALLERY set realfile_L = ?, savefile_L = ? where seq = ? ";
            pstmt1 = connMgr.prepareStatement(sql1);

            sql2 = "update	TZ_OFFGALLeRY set realfile_S = ?, savefile_S = ? where seq = ? ";
            pstmt2 = connMgr.prepareStatement(sql2);

            sql3 = "update	TZ_OFFGALLERY set media_realfile = ?, media_savefile = ? where seq = ? ";
            pstmt3 = connMgr.prepareStatement(sql3);

            for (int i = 0; i < FILE_LIMIT; i++) {
                if (!v_realFileName[i].equals("")) {

                    if (!v_realFileName[0].equals("")) { // ���� ���ε� �Ǵ� ���ϸ� üũ�ؼ� db�� �Է��Ѵ�
                        pstmt1.setString(1, v_realFileName[0]);
                        pstmt1.setString(2, v_newFileName[0]);
                        pstmt1.setInt(3, p_seq);
                        pstmt1.executeUpdate();
                    } // ū �̹��� ���

                    if (!v_realFileName[1].equals("")) {
                        pstmt2.setString(1, v_realFileName[1]);
                        pstmt2.setString(2, v_newFileName[1]);
                        pstmt2.setInt(3, p_seq);
                        isOk2 = pstmt2.executeUpdate();
                    } // ���� �̹��� ���

                    if (!v_realFileName[2].equals("")) {
                        pstmt3.setString(1, v_realFileName[2]);
                        pstmt3.setString(2, v_newFileName[2]);
                        pstmt3.setInt(3, p_seq);
                        pstmt3.executeUpdate();
                    } // �̵�� ���
                }
            }
        } catch (Exception ex) {
            FileManager.deleteFile(v_newFileName, FILE_LIMIT); // �Ϲ�����, ÷������ ������ ����..
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
     * �ۻ����ϱ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int deleteGallery(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";

        int isOk = 1;
        int v_seq = 0;

        v_seq = box.getInt("p_seq");
        System.out.println("v_seq : " + v_seq);

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            // ��������
            sql = "delete from TZ_OFFGALLERY where seq =?";
            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, v_seq);

            isOk = pstmt.executeUpdate();
            System.out.println("isOk[1]:" + isOk);
            connMgr.commit();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql ->" + FormatDate.getDate("yyyyMMdd") + sql + "\r\n" + ex.getMessage());
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
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return 1;
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
            // str += "	<td width='100%' align='center' valign='middle'>";

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
            // str += "	</td>";
            // str += "	<td width='15%' align='center'>";

            // str += "	</td>";
            str += "</tr>";
            str += "</table>";
        }
        return str;
    }
}
