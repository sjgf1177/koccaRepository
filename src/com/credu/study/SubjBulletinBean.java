//**********************************************************
//  1. ��      ��: ���� �Խ��� DATA

//  2. ���α׷���: SubjBulletinBean.java
//  3. ��      ��: ���� �Խ��� bean
//  4. ȯ      ��: JDK 1.4
//  5. ��      ��: 1.0
//  6. ��      ��: ���� 2006.05.16
//  7. ��      ��: 
//**********************************************************
package com.credu.study;

import java.io.StringReader;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Vector;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FileManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;

/**
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 * 
 * @author Administrator
 */
public class SubjBulletinBean {
    private ConfigSet config;
    private int row;
    private static final String FILE_TYPE = "p_file"; //		���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 5; //	  �������� ���õ� ����÷�� ����

    public SubjBulletinBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        �� ����� �������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * �н�â ������ ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjBulletinList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;

        String sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        String group_sql1 = "";
        String order_sql1 = "";
        // String count_sql1 = "";

        // String sql2 = "";
        // QnaData data1 = null;
        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        int v_pageno = box.getInt("p_pageno");
        if (v_pageno == 0)
            v_pageno = 1;

        String v_gubun = box.getString("p_gubun");

        head_sql1 = " select B.seq, B.title, B.contents, B.inuserid, B.indate, A.name,b.readcnt, ";
        head_sql1 += "       (select count(*) from TZ_SUBJBULLETIN                                               ";
        head_sql1 += "         where subj=B.subj and year=B.year and subjseq=B.subjseq                  ";
        head_sql1 += "               and seq=B.seq and kind>0) anscnt,                                  ";
        head_sql1 += "       (select count(*) from TZ_SUBJBULLETINFILE                                           ";
        head_sql1 += "         where subj=B.subj and year=B.year and subjseq=B.subjseq and gubun=B.gubun and seq=B.seq and kind=B.kind ) upfilecnt    "; //���ε����ϰ���       
        body_sql1 += "   from TZ_MEMBER A, TZ_SUBJBULLETIN B                                                     ";
        body_sql1 += "  where B.subj = '" + v_subj + "' and year = '" + v_year + "' and subjseq='" + v_subjseq + "' and gubun = '" + v_gubun + "'";
        body_sql1 += "        and B.inuserid = A.userid and B.kind=0           ";

        if (!v_searchtext.equals("")) { //    �˻�� ������
            if (v_search.equals("name")) { //    �̸����� �˻��Ҷ�
                body_sql1 += " and A.name like " + StringManager.makeSQL("%" + v_searchtext + "%");
            } else if (v_search.equals("title")) { //    �������� �˻��Ҷ�
                body_sql1 += " and B.title like " + StringManager.makeSQL("%" + v_searchtext + "%");
            } else if (v_search.equals("content")) { //    �������� �˻��Ҷ�
                body_sql1 += " and B.contents like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }
        }
        order_sql1 += "  order by B.seq desc, anscnt, B.indate ";
        sql1 = head_sql1 + body_sql1 + group_sql1 + order_sql1;

        // count_sql1 = " select count(*) " + body_sql1;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            ls1 = connMgr.executeQuery(sql1);

            ls1.setPageSize(row); //  �������� row ������ �����Ѵ�
            ls1.setCurrentPage(v_pageno); //     ������������ȣ�� �����Ѵ�.
            int totalpagecount = ls1.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�
            int total_row_count = ls1.getTotalCount(); //     ��ü row ���� ��ȯ�Ѵ�

            while (ls1.next()) {
                dbox = ls1.getDataBox();

                dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(10));

                list1.add(dbox);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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

        return list1;
    }

    /**
     * ���� ����ȸ �亯�� ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjBulletinDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        DataBox dbox = null;
        String sql1 = "";
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_seq = box.getString("p_seq");
        String v_gubun = box.getString("p_gubun");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = "select B.kind as kind, B.seq, B.title, B.contents, B.inuserid as inuserid, ";
            sql1 += "	B.indate, B.replygubun, A.comp ,   A.name  ";
            sql1 += " from TZ_MEMBER A, TZ_SUBJBULLETIN B where B.subj = '" + v_subj + "' and year = '" + v_year + "' and subjseq='" + v_subjseq + "' and gubun = '" + v_gubun + "'";
            sql1 += " and B.seq =" + v_seq + " and B.inuserid = A.userid order by B.kind ";

            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {

                dbox = ls1.getDataBox();

                list1.add(dbox);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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

        return list1;
    }

    /**
     * ÷������ ����Ʈ ����
     */
    public static String selectQnaFileList(String p_subj, String p_year, String p_subjseq, int p_seq, int p_kind) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        //DataBox dbox = null;
        String result = "";

        try {
            connMgr = new DBConnectionManager();
            sql = "  select realfile, savefile, fileseq from TZ_SUBJBULLETINFILE  ";
            sql += "  where subj='" + p_subj + "' and year='" + p_year + "' and subjseq='" + p_subjseq + "' and seq = " + p_seq + " and kind = '" + String.valueOf(p_kind) + "' order by fileseq ";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                result += "<a href='/servlet/controller.library.DownloadServlet?p_savefile=" + ls.getString("savefile") + "&p_realfile=" + ls.getString("realfile") + "'>" + ls.getString("realfile") + "</a><br>";

            }

        } catch (Exception ex) {
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
     * ���� �� �亯 ����ȸ(������)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjBulletinDetail2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        DataBox dbox = null;
        ArrayList<DataBox> list1 = null;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_gubun = box.getString("p_gubun");
        String v_seq = box.getString("p_seq");
        String v_kind = box.getString("p_kind");

        sql1 = " select a.kind, a.seq, a.title, a.contents, a.inuserid, a.indate,  ";
        sql1 += "        (select name from tz_member where userid=a.inuserid) name, b.fileseq, b.realfile, b.savefile  ";
        sql1 += " from TZ_SUBJBULLETIN a, TZ_SUBJBULLETINFILE b                                                ";
        sql1 += " where                                                                       ";
        sql1 += " a.subj = b.subj(+) and a.year = b.year(+) and a.subjseq = b.subjseq(+) and a.gubun = b.gubun(+) and  a.seq = b.seq(+) and a.kind = b.kind(+) ";
        sql1 += " and  a.subj = '" + v_subj + "' and a.year = '" + v_year + "' and a.subjseq='" + v_subjseq + "' and a.gubun = '" + v_gubun + "' and a.seq =" + v_seq + " and a.kind = '" + v_kind + "'  ";

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {

                dbox = ls1.getDataBox();

                list1.add(dbox);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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

        return list1;
    }

    /**
     * ���� ���
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int qnaInsert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        int isOk2 = 0;
        String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //����
        String v_year = box.getString("p_year"); //�⵵
        String v_subjseq = box.getString("p_subjseq"); //��������
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        String v_gubun = box.getString("p_gubun");
        int v_inseq = 0;
        int v_intype = 0;
        int i = 1;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);
            // �Ϸù�ȣ ���ϱ�
            sql1 = "select isnull(max(seq),0)+1 maxseq from TZ_SUBJBULLETIN ";
            sql1 += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "'";
            sql1 += " and gubun = '" + v_gubun + "' ";
            sql1 += "and kind='0' ";

            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                v_inseq = ls.getInt(1);
                v_intype = 0;
            }
            ls.close();

            // TZ_SUBJBULLETIN ���
            sql2 = "insert into TZ_SUBJBULLETIN(subj,year,subjseq,gubun,seq,kind,title,contents, readcnt,";
            sql2 += "                   indate,inuserid,luserid, ldate) ";
            sql2 += "values(?,?,?,?,?,?,?, ?,0,to_char(sysdate, 'YYYYMMDDHHMMSS'),?,?,to_char(sysdate, 'YYYYMMDDHHMMSS'))";
            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(i++, v_subj);
            pstmt2.setString(i++, v_year);
            pstmt2.setString(i++, v_subjseq);
            pstmt2.setString(i++, v_gubun);
            pstmt2.setInt(i++, v_inseq);
            pstmt2.setInt(i++, v_intype);
            pstmt2.setString(i++, v_title);
            //           pstmt2.setString(i++,v_contents);
            //           pstmt2.setCharacterStream(i++,  new StringReader(v_contents), v_contents.length());
            pstmt2.setString(i++, v_contents);

            pstmt2.setString(i++, v_user_id);
            pstmt2.setString(i++, v_user_id);

            isOk = pstmt2.executeUpdate();

            // ���Ͼ��ε�
            if (isOk > 0) {
                isOk2 = this.insertUpFile(connMgr, v_subj, v_year, v_subjseq, v_inseq, v_intype, box);

                if (isOk2 > 0) {
                    connMgr.commit();
                }
            }

        } catch (Exception ex) {

            throw new Exception("sql2 = " + sql2 + "\r\n" + ex.getMessage());
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
     * ���� ����
     * 
     * @param box receive from the form object and session
     * @return int
     */
    @SuppressWarnings("unchecked")
    public int updateQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql2 = "";
        int isOk = 0;
        int isOk2 = 0;
        int isOk3 = 0;
        String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //����
        String v_year = box.getString("p_year"); //�⵵
        String v_subjseq = box.getString("p_subjseq"); //��������
        String v_gubun = box.getString("p_gubun");
        int v_seq = box.getInt("p_seq");
        int v_kind = box.getInt("p_kind");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));

        int v_upfilecnt = box.getInt("p_upfilecnt"); //������ ������ִ� ���ϼ�        
        Vector v_savefile = box.getVector("p_savefile"); //���û�������
        Vector v_filesequence = box.getVector("p_fileseq"); //���û������� sequence

        for (int i = 0; i < v_upfilecnt; i++) {
            if (!box.getString("p_fileseq" + i).equals("")) {
                v_savefile.addElement(box.getString("p_savefile" + i)); //		������ ������ִ� ���ϸ� �߿���	������ ���ϵ�
                v_filesequence.addElement(box.getString("p_fileseq" + i)); //		������	������ִ� ���Ϲ�ȣ	�߿��� ������ ���ϵ�

            }
        }
        //************************���ϰ���**********************************//

        int i = 1;

        try {
            connMgr = new DBConnectionManager();

            sql2 = " update TZ_SUBJBULLETIN set title=?,contents=?,inuserid=?,luserid=?,ldate=to_char(sysdate, 'YYYYMMDDHHMMSS') ";
            sql2 += " where subj=? and year=? and subjseq=? and gubun=? and seq=? and kind=? ";
            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(i++, v_title);
            //          pstmt2.setString(i++,v_contents);
            //          pstmt2.setCharacterStream(i++,  new StringReader(v_contents), v_contents.length());
            pstmt2.setString(i++, v_contents);
            pstmt2.setString(i++, v_user_id);
            //pstmt2.setString(i++,v_replygubun);
            pstmt2.setString(i++, v_user_id);
            pstmt2.setString(i++, v_subj);
            pstmt2.setString(i++, v_year);
            pstmt2.setString(i++, v_subjseq);
            pstmt2.setString(i++, v_gubun);
            pstmt2.setInt(i++, v_seq);
            pstmt2.setInt(i++, v_kind);
            isOk = pstmt2.executeUpdate();

            // ���ϵ�����
            isOk3 = this.deleteUpFile(connMgr, v_subj, v_year, v_subjseq, v_gubun, v_seq, v_kind, v_filesequence);

            // ���Ͼ��ε�
            isOk2 = this.insertUpFile(connMgr, v_subj, v_year, v_subjseq, v_seq, v_kind, box);
            System.out.println("���Ͼ��ε�>>>" + isOk2);

            if (isOk > 0 && isOk2 > 0 && isOk3 > 0) {

                if (v_savefile != null) {
                    FileManager.deleteFile(v_savefile); //	 DB	���� ���ó����	�Ϸ�Ǹ� �ش� ÷������ ����
                }
            }
        } catch (Exception ex) {
            throw new Exception("sql2 = " + sql2 + "\r\n" + ex.getMessage());
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
     * �亯 ���
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int replyQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        int isOk2 = 0;
        String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //����
        String v_year = box.getString("p_year"); //�⵵
        String v_subjseq = box.getString("p_subjseq"); //��������
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        String v_gubun = box.getString("p_gubun");

        int v_seq = box.getInt("p_seq");
        int v_inseq = 0;
        int v_intype = 0;
        int i = 1;

        String v_replygubun = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////           
            sql1 = "select max(kind)+1 maxseq from TZ_SUBJBULLETIN ";
            sql1 += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";
            sql1 += " and gubun = '" + v_gubun + "' ";
            sql1 += "and seq=" + v_seq + " ";

            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                v_inseq = v_seq;
                v_intype = ls.getInt(1);
            }

            ls.close();

            // �亯���
            sql2 = "insert into TZ_SUBJBULLETIN(subj,year,subjseq,gubun,seq,kind,title,contents,readcnt, ";
            sql2 += "                   indate,inuserid,replygubun,luserid,ldate) ";
            //           sql2 += "values(?,?,?,?,?,?,?,empty_clob(),0,to_char(sysdate, 'YYYYMMDDHHMMSS'),?,?,?,to_char(sysdate, 'YYYYMMDDHHMMSS'))";
            sql2 += "values(?,?,?,?,?,?,?,?,0,to_char(sysdate, 'YYYYMMDDHHMMSS'),?,?,?,to_char(sysdate, 'YYYYMMDDHHMMSS'))";
            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setString(i++, v_subj);
            pstmt2.setString(i++, v_year);
            pstmt2.setString(i++, v_subjseq);
            pstmt2.setString(i++, v_gubun);
            pstmt2.setInt(i++, v_inseq);
            pstmt2.setInt(i++, v_intype);
            pstmt2.setString(i++, v_title);
            //pstmt2.setString(i++,v_contents);
            pstmt2.setCharacterStream(i++, new StringReader(v_contents), v_contents.length());
            pstmt2.setString(i++, v_user_id);
            pstmt2.setString(i++, v_replygubun);
            pstmt2.setString(i++, v_user_id);

            isOk = pstmt2.executeUpdate();

            // ���� 
            //           sql1 = "select contents from TZ_SUBJBULLETIN where subj='"+v_subj+"' and year='"+v_year+"' and subjseq='"+v_subjseq+"' and gubun='"+v_gubun+"' and seq="+v_inseq+" and kind='"+v_intype+"'";
            //			connMgr.setOracleCLOB(sql1, v_contents);

            // ���Ͼ��ε�
            isOk2 = this.insertUpFile(connMgr, v_subj, v_year, v_subjseq, v_inseq, v_intype, box);

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
            connMgr.rollback();
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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
        return isOk;
    }

    /**
     * ����
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int deleteQna(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        PreparedStatement pstmt3 = null;
        String sql1 = "";
        String sql3 = "";
        int isOk = 0;
        int isOk1 = 0;
        String v_subj = box.getString("p_subj"); //����
        String v_year = box.getString("p_year"); //�⵵
        String v_subjseq = box.getString("p_subjseq"); //��������
        String v_gubun = box.getString("p_gubun");
        int v_seq = box.getInt("p_seq");
        int v_kind = box.getInt("p_kind");

        int i = 1;

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql1 = " delete TZ_SUBJBULLETIN where subj=? and year=? and subjseq=? and gubun=? and seq=? ";
            if (v_kind != 0) { //������ �ƴҰ��
                sql1 += " and kind=? ";
            }

            pstmt2 = connMgr.prepareStatement(sql1);
            //         System.out.println("sql2==========>"+sql1);

            pstmt2.setString(i++, v_subj);
            pstmt2.setString(i++, v_year);
            pstmt2.setString(i++, v_subjseq);
            pstmt2.setString(i++, v_gubun);
            pstmt2.setInt(i++, v_seq);
            if (v_kind != 0) { //������ �ƴҰ��
                pstmt2.setInt(i++, v_kind);
            }

            isOk = pstmt2.executeUpdate();

            sql3 = "delete from TZ_SUBJBULLETINFILE where ";
            sql3 += " subj=? and year=? and subjseq=?  and gubun=?  and seq =? and kind=? ";

            pstmt3 = connMgr.prepareStatement(sql3);
            pstmt3.setString(1, v_subj);
            pstmt3.setString(2, v_year);
            pstmt3.setString(3, v_subjseq);
            pstmt3.setString(4, v_gubun);
            pstmt3.setInt(5, v_seq);
            pstmt3.setString(6, String.valueOf(v_kind));
            isOk1 = pstmt3.executeUpdate();

            if (isOk > 0 && isOk1 > 0) {
                connMgr.commit();
            }

        } catch (Exception ex) {
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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
                } catch (Exception e) {
                }
            }
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
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

    ///////////////////////////////////////////////////////  ���� ���̺�   ////////////////////////////////////////
    /**
     * ���ο� �ڷ����� ���
     * 
     * @param connMgr DB Connection Manager
     * @param p_seq �Խù� �Ϸù�ȣ
     * @param box receive from the form object and session
     * @return isOk 1:insert success,0:insert fail
     * @throws Exception
     */
    public int insertUpFile(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq, int v_seq, int v_intype, RequestBox box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 1;

        String v_gubun = box.getString("p_gubun");

        //----------------------   ���ε�Ǵ� ������ ������ �˰� �ڵ��ؾ��Ѵ�  --------------------------------
        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];

        for (int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));
        }

        String s_userid = box.getSession("userid");

        try {

            //----------------------   �ڷ� ��ȣ �����´� ------------------------------//
            sql = " select isnull(max(fileseq), 0) from TZ_SUBJBULLETINFILE ";
            sql += " where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "'  and gubun='" + v_gubun + "'  and seq = " + v_seq + " and kind='" + v_intype + "' ";

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            ls.close();
            //--------------------------------------------------------------------------//

            //----------------------   ���� table �� �Է�   ----------------------------//
            sql2 = "insert into TZ_SUBJBULLETINFILE(subj, year, subjseq, gubun, seq, kind, fileseq, realfile, savefile, luserid, ldate)";
            sql2 += " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt2 = connMgr.prepareStatement(sql2);

            for (int i = 0; i < FILE_LIMIT; i++) {

                if (!v_realFileName[i].equals("")) { //		���� ���ε�	�Ǵ� ���ϸ�	üũ�ؼ� db�� �Է��Ѵ�

                    pstmt2.setString(1, v_subj);
                    pstmt2.setString(2, v_year);
                    pstmt2.setString(3, v_subjseq);
                    pstmt2.setString(4, v_gubun);
                    pstmt2.setInt(5, v_seq);
                    pstmt2.setInt(6, v_intype);
                    pstmt2.setInt(7, v_fileseq);
                    pstmt2.setString(8, v_realFileName[i]);
                    pstmt2.setString(9, v_newFileName[i]);
                    pstmt2.setString(10, s_userid);

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
     * ���õ� �ڷ����� DB���� ����
     * 
     * @param connMgr DB Connection Manager
     * @param box receive from the form object and session
     * @param p_filesequence ���� ���� ����
     * @return
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public int deleteUpFile(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq, String v_gubun, int v_seq, int v_intype, Vector p_filesequence) throws Exception {
        PreparedStatement pstmt3 = null;
        String sql3 = "";
        int isOk3 = 1;

        try {
            sql3 = "delete from TZ_SUBJBULLETINFILE where ";
            sql3 += " subj=? and year=? and subjseq=?  and gubun=?  and seq =? and kind=? and fileseq = ? ";

            pstmt3 = connMgr.prepareStatement(sql3);
            for (int i = 0; i < p_filesequence.size(); i++) {
                int v_fileseq = Integer.parseInt((String) p_filesequence.elementAt(i));

                pstmt3.setString(1, v_subj);
                pstmt3.setString(2, v_year);
                pstmt3.setString(3, v_subjseq);
                pstmt3.setString(4, v_gubun);
                pstmt3.setInt(5, v_seq);
                pstmt3.setString(6, String.valueOf(v_intype));
                pstmt3.setInt(7, v_fileseq);
                isOk3 = pstmt3.executeUpdate();
            }
        } catch (Exception ex) {
            //ErrorManager.getErrorStackTrace(ex, box,	sql3);
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

}
