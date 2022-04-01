//**********************************************************
//  1. ��      ��: �����������ø� ����
//  2. ���α׷��� : NoticeTempletBean.java
//  3. ��      ��: �����������ø� ����
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0��������
//  6. ��      ��: ������ 2005. 7.  14
//  7. ��      ��:
//**********************************************************
package com.credu.homepage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
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
import com.dunet.common.util.StringUtil;
import com.namo.active.NamoMime;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class NoticeTempletBean {
    private ConfigSet config;
    private int row;

    public NoticeTempletBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.manage.row")); //�� ����� �������� row ���� �����Ѵ�
            row = 10; //������ ����
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ���ø� �ڵ� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �����������ø� ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectTempletCodeList() throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;

        String sql = "";

        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " SELECT CODE, CODENM FROM TZ_CODE WHERE GUBUN = '0080' ";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
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
     * �������� ���ø� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �����������ø� ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectListNoticeTemplet(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ������ : 05.11.23 ������ : �̳��� _ totalcount �ϱ����� ��������
        String sql = "";
        StringBuffer headSql = new StringBuffer();
        StringBuffer bodySql = new StringBuffer();
        String countSql = "";
        String orderSql = "";

        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        String v_gubun = box.getString("p_selGubun");
        int v_pageno = box.getInt("p_pageno");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            headSql.append(" SELECT  A.SEQ, A.ADDATE, A.ADTITLE, A.ADNAME, A.TEMPLETFILE     \n ");
            headSql.append("         , A.CNT, A.LUSERID, A.LDATE, B.CODE, B.CODENM, A.GUBUN  \n ");
            headSql.append("         , COUNT(A.GUBUN) OVER (PARTITION BY A.GUBUN) CODECNT    \n ");
            bodySql.append(" FROM    TZ_NOTICE_TEMPLET A                                     \n ");
            bodySql.append("         , TZ_CODE B                                             \n ");
            bodySql.append(" WHERE   A.GUBUN     = B.CODE(+)                                 \n ");
            bodySql.append(" AND     B.GUBUN(+)  = '0080'                                    \n ");

            if (!v_searchtext.equals("")) { //    �˻�� ������
                if (v_search.equals("adtitle")) { //    �������� �˻��Ҷ�
                    bodySql.append(" AND adtitle like " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n ");
                } else if (v_search.equals("name")) { //    �������� �˻��Ҷ�
                    bodySql.append(" AND name like " + StringManager.makeSQL("%" + v_searchtext + "%") + " \n ");
                }
            }

            if (!v_gubun.equals("")) {
                bodySql.append(" AND A.GUBUN = " + StringManager.makeSQL(v_gubun) + " \n ");
            }

            orderSql += " order by seq desc ";

            sql = headSql.toString() + bodySql.toString() + orderSql;

            countSql = "select count(*) " + bodySql.toString();

            ls = connMgr.executeQuery(sql);
            // int total_page_count = ls.getTotalPage();  		//��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = BoardPaging.getTotalRow(connMgr, countSql); //��ü row ���� ��ȯ�Ѵ�

            ls.setPageSize(row); //�������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno, total_row_count); //������������ȣ�� �����Ѵ�.

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(ls.getTotalPage()));
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
     * �������� ���ø� �󼼺���
     * 
     * @param box receive from the form object and session
     * @return ArrayList ��ȸ�� ������
     * @throws Exception
     */
    public DataBox selectViewNoticeTemplet(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        StringBuffer sql = new StringBuffer();
        DataBox dbox = null;

        String v_seq = box.getString("p_seq");

        String v_filepath = config.getProperty("dir.namo.template");
        String v_templetfile = "";
        String v_contents = "";
        try {
            connMgr = new DBConnectionManager();

            sql.append(" SELECT  A.SEQ, A.ADDATE, A.ADTITLE, A.ADNAME, A.TEMPLETFILE     \n");
            sql.append("         , A.CNT, A.LUSERID, A.LDATE, B.CODE, B.CODENM, A.GUBUN  \n");
            sql.append(" FROM    TZ_NOTICE_TEMPLET A                                     \n");
            sql.append("         , TZ_CODE B                                             \n");
            sql.append(" WHERE   A.GUBUN     = B.CODE(+)                                 \n");
            sql.append(" AND     B.GUBUN(+)  = '0080'                                    \n");
            sql.append(" AND     SEQ    = " + StringManager.makeSQL(v_seq));

            ls = connMgr.executeQuery(sql.toString());

            if (ls.next()) {
                dbox = ls.getDataBox();

                v_templetfile = dbox.getString("d_templetfile");
                v_contents = read(v_filepath + v_templetfile);
                dbox.put("d_contents", v_contents);
            }
            System.out.println("v_templetfile1 : " + v_templetfile);
            // ��ȸ�� ����
            connMgr.executeUpdate("update TZ_NOTICE_TEMPLET set cnt = cnt + 1 where seq = " + v_seq);
        } catch (FileNotFoundException e) {
            return null;
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
        return dbox;
    }

    /**
     * �������� ���ø� ����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertNoticeTemplet(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        ListSet ls = null;
        String sql = "";
        String sql1 = "";
        int isOk = 0;
        int v_seq = 0;

        String v_adtitle = box.getString("p_adtitle");
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        String s_userid = box.getSession("userid");
        String s_name = box.getSession("name");
        String v_gubun = box.getString("p_gubun");

        String v_templetfile = "";

        try {
            connMgr = new DBConnectionManager();

            sql = "select max(seq) from TZ_NOTICE_TEMPLET  ";
            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                v_seq = ls.getInt(1) + 1;
            } else {
                v_seq = 1;
            }

            /*********************************************************************************************/
            // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
            try {
                v_contents = (String) NamoMime.setNamoContent(v_contents);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            // HTML ���ø� ���� ����
            String v_filepath = config.getProperty("dir.namo.template");
            long v_time = System.currentTimeMillis();
            v_templetfile = "namo_" + v_time + ".html";
            write(v_filepath + v_templetfile, v_contents);

            sql1 = "insert into TZ_NOTICE_TEMPLET(seq, addate, adtitle, adname, templetfile, cnt, luserid, ldate, gubun )     ";
            sql1 += "     values (?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'), ?) ";
            pstmt = connMgr.prepareStatement(sql1);

            pstmt.setInt(1, v_seq);
            pstmt.setString(2, v_adtitle);
            pstmt.setString(3, s_name);
            pstmt.setString(4, v_templetfile);
            pstmt.setInt(5, 0);
            pstmt.setString(6, s_userid);
            pstmt.setString(7, v_gubun);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                // templete_list.ini ���� ���� �ۼ�
                makeTemplateList();
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return isOk;
    }

    /**
     * �������� ���ø� �����Ͽ� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:update success,0:update fail
     * @throws Exception
     */
    public int updateNoticeTemplet(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int v_seq = box.getInt("p_seq");
        String v_adtitle = box.getString("p_adtitle");
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        String v_templetfile = box.getString("p_templetfile");
        String v_gubun = box.getString("p_gubun");

        String s_userid = box.getSession("userid");
        String s_name = box.getSession("name");

        try {

            /*********************************************************************************************/
            // ���� MIME �������� ���ε� ���� �� ��ϰ����� �������� �����մϴ�. 
            try {
                v_contents = (String) NamoMime.setNamoContent(v_contents);
            } catch (Exception e) {
                System.out.println(e.toString());
                return 0;
            }
            /*********************************************************************************************/

            // HTML ���ø� ���� ����
            String v_filepath = config.getProperty("dir.namo.template");
            write(v_filepath + v_templetfile, v_contents);

            connMgr = new DBConnectionManager();

            sql = " update TZ_NOTICE_TEMPLET set adtitle = ? ,     adname = ? ,       ";
            sql += "                              templetfile = ? , luserid = ? , ldate = to_char(sysdate, 'YYYYMMDDHH24MISS') ";
            sql += "  , GUBUN = ? ";
            sql += " where  seq = ?                                                                    ";

            pstmt = connMgr.prepareStatement(sql);

            pstmt.setString(1, v_adtitle);
            pstmt.setString(2, s_name);
            pstmt.setString(3, v_templetfile);
            pstmt.setString(4, s_userid);
            pstmt.setString(5, v_gubun);
            pstmt.setInt(6, v_seq);

            isOk = pstmt.executeUpdate();

            if (isOk > 0) {
                // templete_list.ini ���� ���� �ۼ�
                makeTemplateList();
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
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
     * �������� ���ø� �����Ҷ�
     * 
     * @param box receive from the form object and session
     * @return isOk 1:delete success,0:delete fail
     * @throws Exception
     */
    public int deleteNoticeTemplet(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        int isOk = 0;

        int v_seq = box.getInt("p_seq");
        String v_templetfile = box.getString("d_templetfile");
        String v_filepath = config.getProperty("dir.namo.template");

        try {
            connMgr = new DBConnectionManager();

            sql = " delete from TZ_NOTICE_TEMPLET   ";
            sql += "   where seq = ?  ";

            pstmt = connMgr.prepareStatement(sql);
            pstmt.setInt(1, v_seq);
            isOk = pstmt.executeUpdate();
            if (isOk > 0) {
                // template ���� ����
                delete(v_filepath + v_templetfile);
                // template_list.ini ���� ���� �ۼ�
                makeTemplateList();
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
     * �������� ���ø� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �����������ø� ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectAllNoticeTemplet() throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select seq, addate, adtitle, adname, templetfile, cnt, luserid, ldate from TZ_NOTICE_TEMPLET ";
            sql += " order by seq desc ";
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql);
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
     * ���ø� ini ���� �ۼ�
     * 
     * @throws Exception
     */
    public void makeTemplateList() throws Exception {
        ArrayList<DataBox> list = null;
        StringBuffer templetList = new StringBuffer();
        String v_tem_title = "";
        String v_tem_templetfile = "";
        String v_filepath = config.getProperty("dir.namo.template");
        String v_server = config.getProperty("kocca.url.value");
        String v_path = config.getProperty("url.namo.template");
        String v_filename = config.getProperty("name.namo.template.ini");

        ArrayList<DataBox> codeList = null;

        String v_code = "";
        String v_codenm = "";

        try {

            codeList = selectTempletCodeList();

            if (codeList != null && codeList.size() > 0) {
                for (int i = 0; i < codeList.size(); i++) {
                    DataBox codeBox = (DataBox) codeList.get(i);

                    v_code = codeBox.getString("d_code");
                    v_codenm = codeBox.getString("d_codenm");

                    templetList.append("[" + v_codenm + "] \n");

                    list = selectTemplet(v_code);

                    if (list != null) {
                        for (int j = 0; j < list.size(); j++) {
                            DataBox dbox = (DataBox) list.get(j);
                            v_tem_title = dbox.getString("d_adtitle");
                            v_tem_templetfile = dbox.getString("d_templetfile");
                            templetList.append(v_tem_title + "=" + v_server + v_path + v_tem_templetfile + "\n");
                        }
                    }
                }

            }

            write(v_filepath + v_filename, templetList.toString());

            /*
             * v_templet_list = "[���ø� ���]\n"; // ���ø� ����Ʈ list =
             * selectAllNoticeTemplet();
             * 
             * if (list != null) { for (int i = 0; i < list.size(); i++) {
             * DataBox dbox = (DataBox)list.get(i); v_tem_title =
             * dbox.getString("d_adtitle"); v_tem_templetfile =
             * dbox.getString("d_templetfile"); v_templet_list = v_templet_list
             * + v_tem_title + "="+v_server+v_path+ v_tem_templetfile+"\n"; } }
             */

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * �����ۼ�
     * 
     * @param path ���� �н�+�̸�
     * @param contents ������ ����
     * @throws Exception
     */
    public void write(String path, String contents) throws Exception {
        File file = null;

        try {
            file = new File(path);
            FileWriter fw = new FileWriter(file);
            BufferedWriter owriter = new BufferedWriter(fw);
            owriter.write(contents);
            owriter.flush();
            owriter.close();
            fw.close();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * ���� ������ �����Ѵ�
     * 
     * @param path ������ ������ �н�+���ϸ�
     * @throws Exception
     */
    public void delete(String path) throws Exception {
        try {
            File file = new File(path);
            file.delete();
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * ������ �о ������ �����Ѵ�.
     * 
     * @param sPath ������ �н�+���ϸ�
     * @return result ���� ������ ��� �ִ� ��Ʈ�� ��ü
     * @throws Exception
     */
    public String read(String path) throws Exception {
        String result = "";
        try {
            FileReader fr = new FileReader(path);
            BufferedReader reader = new BufferedReader(fr);
            StringBuffer sb = new StringBuffer();
            int len = 4096; // 4k
            char[] buff = new char[len];
            while (true) {
                int rsize = reader.read(buff, 0, len);
                if (rsize < 0) {
                    break;
                }
                sb.append(buff, 0, rsize);
            }
            buff = null;
            result = sb.toString();
        } catch (FileNotFoundException ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new FileNotFoundException(ex.getMessage());
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return result;
    }

    /**
     * �������� ���ø� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList �����������ø� ����Ʈ
     * @throws Exception
     */
    public ArrayList<DataBox> selectTemplet(String v_gubun) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // ������ : 05.11.23 ������ : �̳��� _ totalcount �ϱ����� ��������
        StringBuffer bodySql = new StringBuffer();

        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            bodySql.append(" SELECT  A.ADTITLE, A.ADNAME, A.TEMPLETFILE     \n ");
            bodySql.append(" FROM    TZ_NOTICE_TEMPLET A                    \n ");
            bodySql.append(" WHERE   GUBUN = " + StringManager.makeSQL(v_gubun) + " \n ");

            ls = connMgr.executeQuery(bodySql.toString());

            while (ls.next()) {
                dbox = ls.getDataBox();
                list.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, bodySql.toString());
            throw new Exception("sql = " + bodySql.toString() + "\r\n" + ex.getMessage());
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
