//**********************************************************
//  1. ��      ��: ���� QNA DATA
//  2. ���α׷���: SubjQnaBean.java
//  3. ��      ��: ���� QNA bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: �迵�� 2003. 9. 7
//  7. ��      ��: �̳��� 05.11.23 _ totlacount �ϱ����� ��������
//**********************************************************
package com.credu.study;

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
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;
import com.dunet.common.util.UploadUtil;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */

public class SubjQnaBean {
    private ConfigSet config;
    private int row;
    private static final String FILE_TYPE = "p_file"; //		���Ͼ��ε�Ǵ� tag name
    private static final int FILE_LIMIT = 10; //	  �������� ���õ� ����÷�� ����

    public SubjQnaBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        �� ����� �������� row ���� �����Ѵ�
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ������ q&a ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<QnaData> selectSubjQnaSubjList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<QnaData> list1 = null;
        ArrayList<QnaData> list2 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        QnaData data1 = null;
        QnaData data2 = null;
        String v_Bcourse = ""; //�����ڽ�
        String v_course = ""; //�����ڽ�
        String v_Bcourseseq = ""; //�����ڽ�����
        String v_courseseq = ""; //�����ڽ�����
        String v_subj = ""; //�����䰹�� ������� ����
        String v_year = "";
        String v_subjseq = "";
        int v_noans = 0;
        int v_cnt = 0;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //�����׷�
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //�⵵
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //��������
        String ss_uclass = box.getStringDefault("s_uclass", "ALL"); //�����з�
        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL"); //����&�ڽ�
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //���� ����
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<QnaData>();
            list2 = new ArrayList<QnaData>();

            sql1 = "select course,cyear,courseseq,coursenm,subj,year,subjseq,subjnm,isclosed,isonoff, ";
            sql1 += "(select count(*) from TZ_QNA where subj = A.subj and subjseq=A.subjseq and kind = 0) as qcnt,";
            sql1 += "(select count(*) from TZ_QNA where subj = A.subj and subjseq=A.subjseq and kind > 0) as anscnt ";
            sql1 += "from VZ_SCSUBJSEQ A where 1 = 1 ";
            if (!ss_grcode.equals("ALL")) {
                sql1 += " and grcode = '" + ss_grcode + "'";
            }
            if (!ss_gyear.equals("ALL")) {
                sql1 += " and gyear = '" + ss_gyear + "'";
            }
            if (!ss_grseq.equals("ALL")) {
                sql1 += " and grseq = '" + ss_grseq + "'";
            }
            if (!ss_uclass.equals("ALL")) {
                sql1 += " and scupperclass = '" + ss_uclass + "'";
            }
            if (!ss_subjcourse.equals("ALL")) {
                sql1 += " and scsubj = '" + ss_subjcourse + "'";
            }
            if (!ss_subjseq.equals("ALL")) {
                sql1 += " and scsubjseq = '" + ss_subjseq + "'";
            }
            sql1 += " order by course, cyear, courseseq, subj, year, subjseq ";
            //            System.out.println("sql1============>"+sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                data1 = new QnaData();
                data1.setCourse(ls1.getString("course"));
                data1.setCyear(ls1.getString("cyear"));
                data1.setCourseseq(ls1.getString("courseseq"));
                data1.setCoursenm(ls1.getString("coursenm"));
                data1.setSubj(ls1.getString("subj"));
                data1.setYear(ls1.getString("year"));
                data1.setSubjseq(ls1.getString("subjseq"));
                data1.setSubjnm(ls1.getString("subjnm"));
                data1.setQcnt(ls1.getInt("qcnt"));
                data1.setAnscnt(ls1.getInt("anscnt"));

                v_subj = ls1.getString("subj");
                v_year = ls1.getString("year");
                v_subjseq = ls1.getString("subjseq");

                sql3 = "select lesson, seq, count(*) cnt from tz_qna where subj='" + v_subj + "' and year='" + v_year + "'";
                sql3 += "\r\n  and subjseq='" + v_subjseq + "'  group by lesson, seq";
                ls2 = connMgr.executeQuery(sql3);
                v_noans = 0;
                while (ls2.next()) {
                    v_cnt = ls2.getInt("cnt");
                    if (v_cnt == 1) {
                        v_noans += 1;
                    }
                }
                ls2.close();

                data1.setNoanscnt(v_noans);

                list1.add(data1);
            }

            for (int i = 0; i < list1.size(); i++) {
                data2 = (QnaData) list1.get(i);
                v_course = data2.getCourse();
                v_courseseq = data2.getCourseseq();
                if (!v_course.equals("000000") && !(v_Bcourse.equals(v_course) && v_Bcourseseq.equals(v_courseseq))) {
                    sql2 = "select count(subj) cnt from TZ_SUBJSEQ ";
                    sql2 += "where course = '" + v_course + "' and courseseq = '" + v_courseseq + "' ";
                    if (!ss_grcode.equals("ALL")) {
                        sql2 += " and grcode = '" + ss_grcode + "'";
                    }
                    if (!ss_gyear.equals("ALL")) {
                        sql2 += " and gyear = '" + ss_gyear + "'";
                    }
                    if (!ss_grseq.equals("ALL")) {
                        sql2 += " and grseq = '" + ss_grseq + "'";
                    }
                    //                        System.out.println("sql2============>"+sql2);
                    ls2 = connMgr.executeQuery(sql2);
                    if (ls2.next()) {
                        data2.setRowspan(ls2.getInt("cnt"));
                        data2.setIsnewcourse("Y");
                    }
                } else {
                    data2.setRowspan(0);
                    data2.setIsnewcourse("N");
                }
                v_Bcourse = v_course;
                v_Bcourseseq = v_courseseq;
                list2.add(data2);
                if (ls2 != null) {
                    try {
                        ls2.close();
                    } catch (Exception e) {
                    }
                }
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
        return list2;
    }

    /**
     * �������ú� q&a ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<QnaData> selectSubjQnaSubjseqList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<QnaData> list1 = null;
        String sql1 = "";
        String sql2 = "";
        QnaData data1 = null;
        String v_lesson = "";
        int v_noans = 0;
        int v_cnt = 0;
        String v_subj = box.getString("p_subj"); //����
        String v_year = box.getString("p_year"); //�⵵
        String v_subjseq = box.getString("p_subjseq"); //���� ����
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<QnaData>();
            // �ý��۰��ù� ��Ÿ���� ������ �������� start
            data1 = new QnaData();
            data1.setLesson("00");
            data1.setLessonnm("�ý��۰��� �� ��Ÿ����");
            sql1 = "select count(*) cnt from TZ_QNA where subj = '" + v_subj + "' and subjseq='" + v_subjseq + "' and lesson='00' and kind = 0";
            ls1 = connMgr.executeQuery(sql1);
            if (ls1.next()) {
                data1.setQcnt(ls1.getInt("cnt"));
            }
            ls1.close();

            sql1 = "select seq, count(*) cnt from tz_qna where subj='" + v_subj + "' and year='" + v_year + "'";
            sql1 += "\r\n  and subjseq='" + v_subjseq + "' and  lesson = '00' group by seq";

            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {
                v_cnt = ls1.getInt("cnt");
                if (v_cnt == 1) {
                    v_noans += 1;
                }
            }
            ls1.close();
            data1.setNoanscnt(v_noans);
            list1.add(data1);
            //          �ý��۰��ù� ��Ÿ���� ������ �������� end

            sql1 = "select lesson, sdesc, ";
            sql1 += "(select count(*) from TZ_QNA where subj = '" + v_subj + "' and subjseq='" + v_subjseq + "' and lesson=A.lesson and kind = 0) as qcnt ";
            sql1 += "from TZ_SUBJLESSON A where A.subj = '" + v_subj + "' ";
            //            System.out.println("sql1============>"+sql1);
            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                data1 = new QnaData();
                data1.setLesson(ls1.getString("lesson"));
                data1.setLessonnm(ls1.getString("sdesc"));
                data1.setQcnt(ls1.getInt("qcnt"));
                v_lesson = ls1.getString("lesson");

                sql2 = "select seq, count(*) cnt from tz_qna where subj='" + v_subj + "' and year='" + v_year + "'";
                sql2 += "\r\n  and subjseq='" + v_subjseq + "' and  lesson = '" + v_lesson + "' group by seq";
                ls2 = connMgr.executeQuery(sql2);
                v_noans = 0;
                while (ls2.next()) {
                    v_cnt = ls2.getInt("cnt");
                    if (v_cnt == 1) {
                        v_noans += 1;
                    }
                }
                ls2.close();

                data1.setNoanscnt(v_noans);

                list1.add(data1);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql2 = " + sql2 + "\r\n" + ex.getMessage());
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
     * �н�â ������ ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectSubjQnaList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        // ������ : 05.11.22 ������ : �̳��� _ totalcount �ϱ����� ����
        String sql1 = "";
        String head_sql1 = "";
        String body_sql1 = "";
        String group_sql1 = "";
        String order_sql1 = "";
        String count_sql1 = "";

        DataBox dbox = null;

        String v_searchtext = box.getString("p_searchtext");
        String v_search = box.getString("p_search");
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        int v_pageno = box.getInt("p_pageno");
        if (v_pageno == 0)
            v_pageno = 1;

        head_sql1 = " select B.seq, B.title, B.isopen, B.contents, B.inuserid, B.indate,A.jikwinm, A.cono, A.name,  B.togubun, B.cnt, ";
        head_sql1 += "       (select count(*) from TZ_QNA                                               ";
        head_sql1 += "         where subj=B.subj and year=B.year and subjseq=B.subjseq                  ";
        head_sql1 += "               and seq=B.seq and kind>0) anscnt,                                  ";
        head_sql1 += "       (select count(*) from TZ_QNAFILE                                           ";
        head_sql1 += "         where subj=B.subj and year=B.year and subjseq=B.subjseq and lesson=B.lesson and seq=B.seq and kind=B.kind ) upfilecnt    "; //���ε����ϰ���
        body_sql1 += "   from TZ_MEMBER A, TZ_QNA B                                                     ";
        body_sql1 += "  where a.grcode=b.grcode and B.subj = '" + v_subj + "' and year = '" + v_year + "' and subjseq='" + v_subjseq + "' ";
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

        count_sql1 = " select count(*) " + body_sql1;

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            ls1 = connMgr.executeQuery(sql1);

            int total_row_count = BoardPaging.getTotalRow(connMgr, count_sql1); //     ��ü row ���� ��ȯ�Ѵ�

            ls1.setPageSize(row); //  �������� row ������ �����Ѵ�
            ls1.setCurrentPage(v_pageno, total_row_count); //     ������������ȣ�� �����Ѵ�.
            int totalpagecount = ls1.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�

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
    public ArrayList<DataBox> selectSubjQnaDetail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        DataBox dbox = null;
        String v_upcnt = "Y";
        String sql1 = "";
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getString("p_lesson");
        if (v_lesson.equals(""))
            v_lesson = "000";
        String v_seq = box.getString("p_seq");

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql1 = "select B.kind as kind, B.seq, B.title, B.isopen, B.contents, B.inuserid as inuserid, ";
            sql1 += "	B.indate, B.replygubun, A.comp ,  isnull(B.togubun,'1')  togubun, A.name, B.cnt  ";
            sql1 += " from TZ_MEMBER A, TZ_QNA B where  B.subj = '" + v_subj + "' and year = '" + v_year + "' and subjseq='" + v_subjseq + "' ";
            sql1 += " and lesson = '" + v_lesson + "' and B.seq =" + v_seq + " and B.inuserid = A.userid order by B.kind ";

            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {

                dbox = ls1.getDataBox();

                list1.add(dbox);

                /*
                 * data1 = new QnaData(); data1.setSeq(ls1.getInt("seq"));
                 * data1.setKind(ls1.getInt("kind"));
                 * data1.setTitle(ls1.getString("title"));
                 * data1.setIsOpen(ls1.getString("isopen"));
                 * data1.setContents(ls1.getCharacterStream("contents"));
                 * data1.setInuserid(ls1.getString("inuserid"));
                 * data1.setIndate(ls1.getString("indate"));
                 * data1.setReplygubun(ls1.getString("replygubun"));
                 * data1.setInusernm
                 * (MemberAdminBean.getUserName(ls1.getString("inuserid")));
                 * data1.setTogubun(ls1.getString("togubun"));
                 * 
                 * list1.add(data1);
                 */
            }

            if (!v_upcnt.equals("N")) {
                connMgr.executeUpdate("update TZ_QNA set cnt = cnt + 1 where subj = " + StringManager.makeSQL(v_subj) + " and year    = " + StringManager.makeSQL(v_year) + " and subjseq = " + StringManager.makeSQL(v_subjseq) + " and lesson = "
                        + StringManager.makeSQL(v_lesson) + " and seq = " + v_seq);
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
            sql = "  select realfile, savefile, fileseq from tz_qnafile  ";
            sql += "  where subj='" + p_subj + "' and year='" + p_year + "' and subjseq='" + p_subjseq + "' and seq = " + p_seq + " and kind = '" + String.valueOf(p_kind) + "' order by fileseq ";
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                result += "<a href='/servlet/controller.library.DownloadServlet?p_savefile=" + ls.getString("savefile") + "&p_realfile=" + ls.getString("realfile") + "'><img src=\"/images/portal/ico/ico_file.gif\" alt=\"file\" /><span>"
                        + ls.getString("realfile") + "</span></a><br>";

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
    public DataBox selectSubjQnaDetail2(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        DataBox dbox = null;
        ArrayList<DataBox> list1 = null;

        Vector<String> realfileVector = new Vector<String>();
        Vector<String> savefileVector = new Vector<String>();
        Vector<String> fileseqVector = new Vector<String>();

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String v_lesson = box.getStringDefault("p_lesson", "000");
        String v_seq = box.getString("p_seq");
        String v_kind = box.getString("p_kind");

        //sql1 = "select B.kind, B.seq, B.title, B.isopen, B.contents, B.inuserid, B.indate,  B.replygubun, A.comp, isnull(B.togubun,'1')  togubun ";
        //sql1 +=" from TZ_MEMBER A, TZ_QNA B where B.subj = '"+v_subj+"' and B.year = '"+v_year+"' and B.subjseq='"+v_subjseq+"' ";
        //sql1 +=" and B.lesson = '"+v_lesson+"' and B.seq ="+v_seq+" and B.kind = '"+v_kind+"' and B.inuserid = A.userid order by B.kind ";

        sql1 = " select a.kind, a.seq, a.title, a.isopen, a.contents, a.inuserid, a.indate,  ";
        sql1 += "        a.replygubun,  isnull(a.togubun,'1')  togubun,                                 ";
        sql1 += "        (select name from tz_member where userid=a.inuserid and grcode = a.grcode) name, b.fileseq, b.realfile, b.savefile  ";
        sql1 += " from tz_qna a, tz_qnafile b                                                ";
        sql1 += " where                                                                       ";
        sql1 += " a.subj = b.subj(+) and a.year = b.year(+) and a.subjseq = b.subjseq(+) and a.lesson = b.lesson(+) and  a.seq = b.seq(+) and a.kind = b.kind(+) ";
        sql1 += " and  a.subj = '" + v_subj + "' and a.year = '" + v_year + "' and a.subjseq='" + v_subjseq + "' and a.lesson = '" + v_lesson + "' and a.seq =" + v_seq + " and a.kind = '" + v_kind + "'  ";
        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            ls1 = connMgr.executeQuery(sql1);
            while (ls1.next()) {

                dbox = ls1.getDataBox();
                if (!dbox.getString("d_realfile").equals("")) {
                    realfileVector.addElement(dbox.getString("d_realfile"));
                    savefileVector.addElement(dbox.getString("d_savefile"));
                    fileseqVector.addElement(String.valueOf(dbox.getInt("d_fileseq")));
                }

                list1.add(dbox);
            }

            if (realfileVector != null)
                dbox.put("d_realfile", realfileVector);
            if (savefileVector != null)
                dbox.put("d_savefile", savefileVector);
            if (fileseqVector != null)
                dbox.put("d_fileseq", fileseqVector);

            /*
             * while (ls1.next()){ /*data1 = new QnaData();
             * data1.setSeq(ls1.getInt("seq"));
             * data1.setKind(ls1.getInt("kind"));
             * data1.setTitle(ls1.getString("title"));
             * data1.setIsOpen(ls1.getString("isopen"));
             * data1.setContents(ls1.getCharacterStream("contents"));
             * data1.setInuserid(ls1.getString("inuserid"));
             * data1.setIndate(ls1.getString("indate"));
             * data1.setReplygubun(ls1.getString("replygubun"));
             * data1.setInusernm
             * (MemberAdminBean.getUserName(ls1.getString("inuserid")));
             * data1.setTogubun(ls1.getString("togubun"));
             * 
             * dbox = ls1.getDataBox(); list1.add(dbox); }
             */
            //ls1.close();

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

        return dbox;
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
        String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //����
        String v_year = box.getString("p_year"); //�⵵
        String v_subjseq = box.getString("p_subjseq"); //��������
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_content = StringUtil.removeTag(box.getString("p_content"));
        String v_lesson = box.getString("p_lesson");
        String v_isopen = box.getStringDefault("p_isopen", "Y");
        String v_togubun = box.getString("p_togubun");
        String v_categorycd = box.getStringDefault("p_categorycd", "00"); //��������&�����ó�Ʈ
        int v_inseq = 0;
        int v_intype = 0;
        int i = 1;
        String v_grcode = "";
        //String v_replygubun = getReplyGubun(box);

        try {
            connMgr = new DBConnectionManager();

            // �Ϸù�ȣ ���ϱ�
            sql1 = "select isnull(max(seq),0)+1 maxseq from TZ_QNA ";
            sql1 += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "'";
            sql1 += "and kind='0' ";
            if (v_categorycd.equals("03")) { //�����ó�Ʈ
                sql1 += " and lesson = '" + v_lesson + "' ";
            } else {
                v_lesson = "000";
            }

            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                v_inseq = ls.getInt(1);
                v_intype = 0;
            }
            ls.close();

            // �����׷��ڵ� ���ϱ�
            sql1 = " select grcode from TZ_SUBJSEQ where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";
            ls = connMgr.executeQuery(sql1);

            if (ls.next())
                v_grcode = ls.getString(1);
            ls.close();

            // TZ_QNA ���
            sql2 = "insert into TZ_QNA(subj,year,subjseq,lesson,seq,kind,title,contents,";
            sql2 += "                   indate,inuserid,isopen,luserid, ldate,grcode, togubun, categorycd) ";
            sql2 += "values(?,?,?,?,?,?,?, ?,to_char(sysdate, 'YYYYMMDDHHMMSS'),?,?,?,to_char(sysdate, 'YYYYMMDDHHMMSS'),?, ? ,?)";
            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(i++, v_subj);
            pstmt2.setString(i++, v_year);
            pstmt2.setString(i++, v_subjseq);
            pstmt2.setString(i++, v_lesson);
            pstmt2.setInt(i++, v_inseq);
            pstmt2.setInt(i++, v_intype);
            pstmt2.setString(i++, v_title);
            //           pstmt2.setString(i++,v_contents);
            //           pstmt2.setCharacterStream(i++,  new StringReader(v_contents), v_contents.length());
            pstmt2.setString(i++, v_content);
            pstmt2.setString(i++, v_user_id);
            pstmt2.setString(i++, v_isopen);
            //pstmt2.setString(i++,v_replygubun);
            pstmt2.setString(i++, v_user_id);
            pstmt2.setString(i++, v_grcode);
            pstmt2.setString(i++, v_togubun);
            pstmt2.setString(i++, v_categorycd);

            isOk = pstmt2.executeUpdate();

            // ���Ͼ��ε�

            UploadUtil.fnRegisterAttachFile(box); // ���� ���� ���ε�

            this.insertUpFile(connMgr, v_subj, v_year, v_subjseq, v_inseq, v_intype, box); // DB ���ε� ���� ���

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
        int v_seq = box.getInt("p_seq");
        int v_kind = box.getInt("p_kind");
        String v_title = box.getString("p_title");
        String v_isopen = box.getString("p_isopen");
        String v_togubun = box.getString("p_togubun");
        String v_contents = StringUtil.removeTag(box.getString("p_content"));

        Vector v_del_savefile = box.getVector("p_del_savefile");

        //************************���ϰ���**********************************//
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

        //String v_replygubun = getReplyGubun(box);

        try {
            connMgr = new DBConnectionManager();

            sql2 = " update TZ_QNA set title=?,isopen=?,contents=?,inuserid=?,luserid=?,ldate=to_char(sysdate, 'YYYYMMDDHHMMSS'), togubun=? ";
            sql2 += " where subj=? and year=? and subjseq=? and lesson=? and seq=? and kind=? ";
            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(i++, v_title);
            pstmt2.setString(i++, v_isopen);
            //          pstmt2.setString(i++,v_contents);
            //          pstmt2.setCharacterStream(i++,  new StringReader(v_contents), v_contents.length());
            pstmt2.setString(i++, v_contents);
            pstmt2.setString(i++, v_user_id);
            //pstmt2.setString(i++,v_replygubun);
            pstmt2.setString(i++, v_user_id);
            pstmt2.setString(i++, v_togubun); // ����,��� ����
            pstmt2.setString(i++, v_subj);
            pstmt2.setString(i++, v_year);
            pstmt2.setString(i++, v_subjseq);
            pstmt2.setString(i++, "000");
            pstmt2.setInt(i++, v_seq);
            pstmt2.setInt(i++, v_kind);
            isOk = pstmt2.executeUpdate();

            // ���ϵ�����
            UploadUtil.fnRegisterAttachFile(box);
            isOk3 = this.deleteUpFile(connMgr, v_subj, v_year, v_subjseq, v_seq, v_kind, box);

            // ���Ͼ��ε�
            isOk2 = this.insertUpFile(connMgr, v_subj, v_year, v_subjseq, v_seq, v_kind, box);
            System.out.println("���Ͼ��ε�>>>" + isOk2);

            if (isOk > 0 && isOk2 > 0 && isOk3 > 0) {

                if (v_savefile != null) {
                    FileManager.deleteFile(v_del_savefile); //	 DB	���� ���ó����	�Ϸ�Ǹ� �ش� ÷������ ����
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
        int isOk3 = 0;
        String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //����
        String v_year = box.getString("p_year"); //�⵵
        String v_subjseq = box.getString("p_subjseq"); //��������
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_content"));
        String v_togubun = box.getString("p_togubun");
        String v_grcode = "";

        int v_seq = box.getInt("p_seq");
        int v_inseq = 0;
        int v_intype = 0;
        int i = 1;

        //String v_replygubun = getReplyGubun(box);
        String v_replygubun = "";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////
            sql1 = "select max(kind)+1 maxseq from TZ_QNA ";
            sql1 += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "'";
            //sql1 += " and lesson = '"+v_lesson+"' ";
            sql1 += "and seq=" + v_seq + " ";

            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                v_inseq = v_seq;
                v_intype = ls.getInt(1);
            }

            ls.close();

            // �����׷��ڵ� ���ϱ�
            sql1 = " select grcode from TZ_SUBJSEQ where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";
            ls = connMgr.executeQuery(sql1);

            if (ls.next())
                v_grcode = ls.getString(1);
            ls.close();

            // �亯���
            sql2 = "insert into TZ_QNA(subj,year,subjseq,lesson,seq,kind,title,contents, ";
            sql2 += "                   indate,inuserid,replygubun,luserid,ldate,grcode, togubun ) ";
            //           sql2 += "values(?,?,?,?,?,?,?,empty_clob(),to_char(sysdate, 'YYYYMMDDHHMMSS'),?,?,?,to_char(sysdate, 'YYYYMMDDHHMMSS'),?,?)";
            sql2 += "values(?,?,?,?,?,?,?,?,to_char(sysdate, 'YYYYMMDDHHMMSS'),?,?,?,to_char(sysdate, 'YYYYMMDDHHMMSS'),?,?)";
            pstmt2 = connMgr.prepareStatement(sql2);
            pstmt2.setString(i++, v_subj);
            pstmt2.setString(i++, v_year);
            pstmt2.setString(i++, v_subjseq);
            pstmt2.setString(i++, "000");
            pstmt2.setInt(i++, v_inseq);
            pstmt2.setInt(i++, v_intype);
            pstmt2.setString(i++, v_title);
            //           pstmt2.setString(i++,v_contents);
            pstmt2.setCharacterStream(i++, new StringReader(v_contents), v_contents.length());
            pstmt2.setString(i++, v_user_id);
            pstmt2.setString(i++, v_replygubun);
            pstmt2.setString(i++, v_user_id);
            pstmt2.setString(i++, v_grcode);
            pstmt2.setString(i++, v_togubun);
            isOk = pstmt2.executeUpdate();

            //�亯�ڿ��� ����Ʈ ���� �ο�
            //LoginBean bean = new LoginBean();
            //System.out.println("�亯 ����Ʈ ���� ");
            //bean.loginMileage(connMgr,v_user_id, "ELN_REG_REPLY");
            //System.out.println("�亯 ����Ʈ ���� ");

            // ����
            //           sql1 = "select contents from TZ_QNA where subj='"+v_subj+"' and year='"+v_year+"' and subjseq='"+v_subjseq+"' and seq="+v_inseq+" and kind='"+v_intype+"'";
            //			connMgr.setOracleCLOB(sql1, v_contents);

            // ���Ͼ��ε�
            isOk2 = UploadUtil.fnRegisterAttachFile(box); // ���� ���� ���ε�
            isOk3 = this.insertUpFile(connMgr, v_subj, v_year, v_subjseq, v_inseq, v_intype, box);

            if (isOk > 0 && isOk2 > 0 && isOk3 > 0) {
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
        String v_subj = box.getString("p_subj"); //����
        String v_year = box.getString("p_year"); //�⵵
        String v_subjseq = box.getString("p_subjseq"); //��������
        String v_lesson = box.getStringDefault("p_lesson", "000");
        int v_seq = box.getInt("p_seq");
        int v_kind = box.getInt("p_kind");

        int i = 1;

        try {
            connMgr = new DBConnectionManager();
            sql1 = " delete TZ_QNA where subj=? and year=? and subjseq=? and lesson=? and seq=? ";
            if (v_kind != 0) { //������ �ƴҰ��
                sql1 += " and kind=? ";
            }

            pstmt2 = connMgr.prepareStatement(sql1);
            //         System.out.println("sql2==========>"+sql1);

            pstmt2.setString(i++, v_subj);
            pstmt2.setString(i++, v_year);
            pstmt2.setString(i++, v_subjseq);
            pstmt2.setString(i++, v_lesson);
            pstmt2.setInt(i++, v_seq);
            if (v_kind != 0) { //������ �ƴҰ��
                pstmt2.setInt(i++, v_kind);
            }

            isOk = pstmt2.executeUpdate();

            sql3 = "delete from TZ_QNAFILE where ";
            sql3 += " subj=? and year=? and subjseq=?  and lesson='000'  and seq =? and kind=? ";

            pstmt3 = connMgr.prepareStatement(sql3);
            pstmt3.setString(1, v_subj);
            pstmt3.setString(2, v_year);
            pstmt3.setString(3, v_subjseq);
            pstmt3.setInt(4, v_seq);
            pstmt3.setString(5, String.valueOf(v_kind));
            pstmt3.executeUpdate();

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

    /**
     * �亯�ڱ��� ���ϱ�(1:�н���, 2:����, 3:���)
     * 
     * @param box receive from the form object and session
     * @return result �亯�� ���а�
     */
    public String getReplyGubun(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        String result = "";
        int cnt = 0;

        String s_user_id = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");
        String v_gadmin_gubun = StringManager.substring(s_gadmin, 0, 1);

        // 1 : �н���, 2 : ����,  3 : ���
        if (v_gadmin_gubun.equals("A") || v_gadmin_gubun.equals("H") || v_gadmin_gubun.equals("K")) {
            result = "3";
        } else if (v_gadmin_gubun.equals("Z")) {
            result = "1";
        } else if (v_gadmin_gubun.equals("F") || v_gadmin_gubun.equals("P")) { // ������������ üũ
            try {
                connMgr = new DBConnectionManager();

                sql1 = " select count(*) cnt from TZ_GADMIN  ";
                sql1 += "  where userid = " + StringManager.makeSQL(s_user_id);
                sql1 += "    and gadmin = " + StringManager.makeSQL(s_gadmin);
                //sql1 += "    and subj   = "+ StringManager.makeSQL(v_subj);

                ls1 = connMgr.executeQuery(sql1);
                if (ls1.next())
                    cnt = ls1.getInt("cnt");

                if (cnt > 0) { // ���������ϰ��
                    if (v_gadmin_gubun.equals("H"))
                        result = "3"; // �����������ϰ�� = 3
                    else
                        result = "2"; // �����ϰ��         = 2
                } else {
                    result = "1";
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
        }

        return result;
    }

    /**
     * ��ڿ���
     * 
     * @param box receive from the form object and session
     * @return String 1 : ��� , 0 : �н���
     */
    public String getGadminYn(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        String sql1 = "";
        String result = "";
        int cnt = 0;
        String v_subj = box.getString("p_subj"); //����

        String s_user_id = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");

        System.out.println("s_gadmin=" + s_gadmin);
        //System.out.println("v_gadmin_gubun="+v_gadmin_gubun);

        String v_gadmin_gubun = StringManager.substring(s_gadmin, 0, 1);

        if (v_gadmin_gubun.equals("A") || v_gadmin_gubun.equals("H") || v_gadmin_gubun.equals("K")) {
            result = "1";
        } else if (v_gadmin_gubun.equals("Z")) {
            result = "0";
        } else if (v_gadmin_gubun.equals("F") || v_gadmin_gubun.equals("P")) { // ������������ üũ
            try {
                connMgr = new DBConnectionManager();

                //sql1  = " select count(*) cnt from TZ_GADMIN  ";
                //sql1 += "  where userid = "+ StringManager.makeSQL(s_user_id);
                //sql1 += "    and gadmin = "+ StringManager.makeSQL(s_gadmin);
                //sql1 += "    and subj   = "+ StringManager.makeSQL(v_subj);

                sql1 = " select count(*) cnt from TZ_SUBJMAN ";
                sql1 += "  where userid = " + StringManager.makeSQL(s_user_id);
                sql1 += "    and gadmin = " + StringManager.makeSQL(s_gadmin);
                sql1 += "    and subj = " + StringManager.makeSQL(v_subj);

                ls1 = connMgr.executeQuery(sql1);
                if (ls1.next())
                    cnt = ls1.getInt("cnt");

                if (cnt > 0) {
                    result = "1";
                } else {
                    result = "0";
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
        }

        return result;
    }

    /**
     * ���� �Խ��� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> mySubjQnaList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        int v_pageno = box.getInt("p_pageno");
        String v_userid = box.getSession("userid");
        String v_srchtype = box.getString("p_srchtype"); //T:����˻�,C:����˻�
        String v_srchkey = box.getString("p_srchkey"); //�˻���

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            sql = " select b.subj,";
            sql += "		b.year,";
            sql += "		b.subjseq,";
            sql += "		b.lesson,";
            sql += "		B.seq, ";
            sql += "	   	B.title, ";
            sql += "	   	B.isopen,";
            sql += "	   	B.indate, ";
            sql += "	   decode((select 	count(*) ";
            sql += "		from 	TZ_QNA ";
            sql += "	    where  	subj=B.subj and ";
            sql += "	   		  	year=B.year and ";
            sql += "			  	subjseq=B.subjseq and ";
            sql += "	   		  	lesson=B.lesson and seq=B.seq and kind>0),0,'N','Y') isans, ";
            sql += "	   (select  subjnm ";
            sql += "		from 	tz_subj ";
            sql += "		where 	subj=b.subj) subjnm ";
            sql += "from   TZ_MEMBER A, TZ_QNA B ";
            sql += "where  B.inuserid = A.userid and ";
            sql += "	   B.kind=0 and ";
            sql += "	   b.inuserid = '" + v_userid + "' ";

            System.out.println("v_srchkey = " + v_srchkey);

            if (!v_srchkey.equals("")) {
                if (v_srchtype.equals("T")) {
                    //Ÿ��Ʋ �˻�
                    sql += " and b.title like '%" + v_srchkey + "%' ";
                } else {
                    //����˻�
                    sql += " and b.contents like '%" + v_srchkey + "%' ";
                }

            }

            sql += "order by indate desc ";

            System.out.println("sql=" + sql);

            ls = connMgr.executeQuery(sql);

            ls.setPageSize(10); //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno); //     ������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�
            int totalrowcount = ls.getTotalCount(); //     ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("subj", ls.getString("subj"));
                dbox.put("year", ls.getString("year"));
                dbox.put("subjseq", ls.getString("subjseq"));
                dbox.put("lesson", ls.getString("lesson"));
                dbox.put("seq", new Integer(ls.getInt("seq")));
                dbox.put("title", ls.getString("title"));
                dbox.put("ispopen", ls.getString("isopen"));
                dbox.put("indate", ls.getString("indate"));
                dbox.put("d_isans", ls.getString("isans"));
                dbox.put("d_subjnm", ls.getString("subjnm"));

                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpagecount", new Integer(totalpagecount));

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
     * ���� �Խ��� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> mySubjQnaListUser(String v_userid) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();
            // ������ : 05.11.22 ������ : �̳��� _ rownum ����
            sql = " select b.subj,";
            sql += "		b.year,";
            sql += "		b.subjseq,";
            sql += "		b.lesson,";
            sql += "		B.seq, ";
            sql += "	   	B.title, ";
            sql += "	   	B.isopen,";
            sql += "	   	B.indate, ";
            sql += "	   decode((select 	count(*) ";
            sql += "		from 	TZ_QNA ";
            sql += "	    where  	subj=B.subj and ";
            sql += "	   		  	year=B.year and ";
            sql += "			  	subjseq=B.subjseq and ";
            sql += "	   		  	lesson=B.lesson and seq=B.seq and kind>0),0,'N','Y') isans, ";
            sql += "	   (select  subjnm ";
            sql += "		from 	tz_subj ";
            sql += "		where 	subj=b.subj) subjnm ";
            sql += "from   TZ_MEMBER A, TZ_QNA B ";
            sql += "where  B.inuserid = A.userid and ";
            sql += "	   B.kind=0 and ";
            sql += "	   b.inuserid = '" + v_userid + "' and rownum < 5 ";
            sql += "order by indate desc ";

            System.out.println(" SubjQna >>> 1201  sql=" + sql);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();

                dbox.put("subj", ls.getString("subj"));
                dbox.put("year", ls.getString("year"));
                dbox.put("subjseq", ls.getString("subjseq"));
                dbox.put("lesson", ls.getString("lesson"));
                dbox.put("seq", new Integer(ls.getInt("seq")));
                dbox.put("title", ls.getString("title"));
                dbox.put("ispopen", ls.getString("isopen"));
                dbox.put("indate", ls.getString("indate"));
                dbox.put("d_isans", ls.getString("isans"));
                dbox.put("d_subjnm", ls.getString("subjnm"));

                list.add(dbox);
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
        return list;
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
    @SuppressWarnings("unchecked")
    public int insertUpFile(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq, int v_seq, int v_intype, RequestBox box) throws Exception {
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql = "";
        String sql2 = "";
        int isOk2 = 1;

        //----------------------   ���ε�Ǵ� ������ ������ �˰� �ڵ��ؾ��Ѵ�  --------------------------------
        String[] v_realFileName = new String[FILE_LIMIT];
        String[] v_newFileName = new String[FILE_LIMIT];

        for (int i = 0; i < FILE_LIMIT; i++) {
            v_realFileName[i] = box.getRealFileName(FILE_TYPE + (i + 1));
            v_newFileName[i] = box.getNewFileName(FILE_TYPE + (i + 1));
        }

        ArrayList arySaveFileName = (ArrayList) box.getObject("arySaveFileName");
        ArrayList aryRealFileName = (ArrayList) box.getObject("aryRealFileName");

        int aryLen = arySaveFileName.size();

        String s_userid = box.getSession("userid");

        try {

            //----------------------   �ڷ� ��ȣ �����´� ------------------------------//
            sql = " select isnull(max(fileseq), 0) from TZ_QNAFILE ";
            sql += " where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "'  and lesson='000'  and seq = " + v_seq + " and kind='" + v_intype + "' ";

            ls = connMgr.executeQuery(sql);
            ls.next();
            int v_fileseq = ls.getInt(1) + 1;
            ls.close();
            //--------------------------------------------------------------------------//

            //----------------------   ���� table �� �Է�   ----------------------------//
            sql2 = "insert into TZ_QNAFILE(subj, year, subjseq, lesson, seq, kind, fileseq, realfile, savefile, luserid, ldate)";
            sql2 += " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, to_char(sysdate, 'YYYYMMDDHH24MISS'))";

            pstmt2 = connMgr.prepareStatement(sql2);

            for (int i = 0; i < aryLen; i++) {

                if (!aryRealFileName.get(i).equals("")) { //		���� ���ε�	�Ǵ� ���ϸ�	üũ�ؼ� db�� �Է��Ѵ�

                    pstmt2.setString(1, v_subj);
                    pstmt2.setString(2, v_year);
                    pstmt2.setString(3, v_subjseq);
                    pstmt2.setString(4, "000");
                    pstmt2.setInt(5, v_seq);
                    pstmt2.setInt(6, v_intype);
                    pstmt2.setInt(7, v_fileseq);
                    pstmt2.setString(8, (String) aryRealFileName.get(i));
                    pstmt2.setString(9, (String) arySaveFileName.get(i));
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
    public int deleteUpFile(DBConnectionManager connMgr, String v_subj, String v_year, String v_subjseq, int v_seq, int v_intype, RequestBox box) throws Exception {
        PreparedStatement pstmt3 = null;
        String sql3 = "";
        int isOk3 = 1;

        Vector v_del_fileseq = box.getVector("p_del_fileseq");

        try {
            sql3 = "delete from TZ_QNAFILE where ";
            sql3 += " subj=? and year=? and subjseq=?  and lesson='000'  and seq =? and kind=? and fileseq = ? ";

            pstmt3 = connMgr.prepareStatement(sql3);
            for (int i = 0; i < v_del_fileseq.size(); i++) {
                int v_fileseq = Integer.parseInt((String) v_del_fileseq.elementAt(i));

                pstmt3.setString(1, v_subj);
                pstmt3.setString(2, v_year);
                pstmt3.setString(3, v_subjseq);
                pstmt3.setInt(4, v_seq);
                pstmt3.setString(5, String.valueOf(v_intype));
                pstmt3.setInt(6, v_fileseq);
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

    /**
     * ������ ��Ʈ ���� ���
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int MkqnaInsert(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        String v_subj = box.getString("p_subj"); //����
        String v_year = box.getString("p_year"); //�⵵
        String v_subjseq = box.getString("p_subjseq"); //��������
        String v_lesson = box.getString("p_lesson");
        int v_max = 0;
        //String v_replygubun = getReplyGubun(box);

        try {
            connMgr = new DBConnectionManager();
            // �ش� ���ÿ� ������ ���� Ȯ��
            sql1 = "select count(*) as cnt from TZ_QNA ";
            sql1 += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "'";
            sql1 += "and lesson='" + v_lesson + "' and kind='0' and seq=1";
            ls = connMgr.executeQuery(sql1);
            if (ls.next()) {
                v_max = ls.getInt(1);
            }
            ls.close();

            if (v_max < 1) {
                isOk = qnaInsert(box);
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

}
