//**********************************************************
//1. ��      ��: ���԰�� ���� ���
//2. ���α׷���: SulmunRegistResultBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: lyh 
//**********************************************************

package com.credu.research;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.StringTokenizer;
import java.util.Vector;

import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.credu.system.SelectionUtil;

/**
 * @author Administrator
 * 
 *         To change the template for this generated type comment go to
 *         Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunRegistResultBean {

    public SulmunRegistResultBean() {
    }

    ///////////////////////////////////////////////////////////////////////////////
    /**
     * ���԰�μ��� ��� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<SulmunQuestionExampleData> SelectObectResultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<SulmunQuestionExampleData> list = null;
        String v_action = box.getString("p_action");

        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = getObjectSulmunResult(connMgr, box);
            } else {
                list = new ArrayList<SulmunQuestionExampleData>();
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    ///////////////////////////////////////////////////////////////////////////////
    /**
     * ���԰�μ��� ��� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList<SulmunQuestionExampleData> getObjectSulmunResult(DBConnectionManager connMgr, RequestBox box) throws Exception {
        Vector<String> v_sulnums = null;
        ArrayList<SulmunQuestionExampleData> QuestionExampleDataList = null;
        Vector<String> v_answers = null;

        String v_tem_grcode = box.getString("tem_grcode");
        String v_subj = box.getString("s_subjcourse");
        String v_subjseq = box.getStringDefault("s_subjseq", "ALL");

        int v_sulpapernum = 0;

        String v_grseq = box.getString("s_grseq");

        // ������ȣ ��������
        String v_grcode = box.getStringDefault("s_grcode", v_tem_grcode);
        String v_gyear = box.getStringDefault("s_gyear", "ALL");

        // ������ ��������
        String v_company = box.getStringDefault("p_company", "ALL");
        String v_jikwi = box.getStringDefault("p_jikwi", "ALL");
        String v_jikun = box.getStringDefault("p_jikun", "ALL");
        String v_workplc = box.getStringDefault("p_workplc", "ALL");

        int v_studentcount = 0;

        try {

            v_sulpapernum = getPapernumSeq("REGIST", v_grcode) - 1;

            // ��������ȣ�� �ش��ϴ� ������ȣ�� vector�� �޾ƿ´�. ����(������ȣ1,3,5 ....)
            v_sulnums = getSulnums(connMgr, v_grcode, "REGIST", v_sulpapernum);

            // ������ȣ�� �ش��ϴ� ��������Ʈ�� �����. ����Ʈ((������ȣ1, ����1,2,3..))
            QuestionExampleDataList = getSelnums(connMgr, v_grcode, "REGIST", v_sulnums);

            // ������ �亯������ �о�´�. ����(�亯1,3,2,3..)
            v_answers = getSulmunAnswers(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_sulpapernum, v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);

            // ����Ʈ((������ȣ1, ����1,2,3..)) + ����(�亯1,3,2,3..) �̿��ؼ� �����ڼ� �� ������ ����Ѵ�.
            ComputeCount(QuestionExampleDataList, v_answers);

            // �����ڼ�
            box.put("p_replycount", String.valueOf(v_answers.size()));

            // �����ڼ�
            v_studentcount = getStudentCount(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);

            box.put("p_studentcount", String.valueOf(v_studentcount));
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return QuestionExampleDataList;
    }

    /**
     * ���԰�μ��� ������ ��ȣ ���ϱ�
     * 
     * @param box receive from the form object and session
     * @return Vector
     */
    public Vector<String> getSulnums(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql = "";
        Vector<String> v_sulnums = new Vector<String>();
        String v_tokens = "";
        StringTokenizer st = null;

        try {
            sql = "select sulnums  ";
            sql += "  from tz_sulpaper ";
            sql += " where grcode      = " + SQLString.Format(p_grcode);
            sql += "   and subj        = " + SQLString.Format(p_subj);
            sql += "   and sulpapernum = " + SQLString.Format(p_sulpapernum);
            System.out.println("SulmunRegistResultBean ���԰�μ��� ������ ��ȣ getSulnums :" + sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_tokens = ls.getString("sulnums");
            }

            st = new StringTokenizer(v_tokens, SulmunRegistBean.SPLIT_COMMA);
            while (st.hasMoreElements()) {
                v_sulnums.add((String) st.nextToken());
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return v_sulnums;
    }

    /**
     * ���԰�μ��� ������ ��ȣ,���� ���ϱ�
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<SulmunQuestionExampleData> getSelnums(DBConnectionManager connMgr, String p_grcode, String p_subj, Vector<String> p_sulnums) throws Exception {
        Hashtable<String, SulmunQuestionExampleData> hash = new Hashtable<String, SulmunQuestionExampleData>();
        ArrayList<SulmunQuestionExampleData> list = new ArrayList<SulmunQuestionExampleData>();

        ListSet ls = null;
        String sql = "";
        SulmunQuestionExampleData data = null;
        SulmunExampleData exampledata = null;
        int v_bef_sulnum = 0;

        String v_sulnums = "";
        for (int i = 0; i < p_sulnums.size(); i++) {
            v_sulnums += (String) p_sulnums.get(i);
            if (i < p_sulnums.size() - 1) {
                v_sulnums += ", ";
            }
        }
        if (v_sulnums.equals(""))
            v_sulnums = "-1";

        try {
            sql = "select a.subj,     a.sulnum, ";
            sql += "        a.distcode, 'ALL' distcodenm, ";
            sql += "       a.sultype,  d.codenm sultypenm, ";
            sql += "       a.sultext,  b.selnum, b.seltext, b.selpoint ";
            sql += "  from tz_sul     a, ";
            sql += "       tz_sulsel  b, ";
            sql += "       tz_code    d  ";
            sql += " where a.subj       =  b.subj(+)    ";
            sql += "   and a.sulnum     =  b.sulnum(+)  ";
            sql += "   and a.sultype    = d.code ";
            sql += "   and b.grcode     = " + SQLString.Format(p_grcode);
            sql += "   and a.grcode     = " + SQLString.Format(p_grcode);
            sql += "   and a.subj       = " + SQLString.Format(p_subj); //�����������̺� SUBJ��: REGIST
            sql += "   and a.sulnum in (" + v_sulnums + ")";
            sql += "   and a.distcode      = " + SQLString.Format(SulmunRegistBean.DIST_CODE);
            sql += "   and d.gubun      = " + SQLString.Format(SulmunRegistBean.SUL_TYPE);
            sql += "   and d.levels     =  1 ";
            sql += " order by a.subj, a.sulnum, b.selnum ";

            System.out.println("SulmunRegistResultBean ���԰�μ��� ������ ��ȣ,���� getSelnums: \n" + sql);

            /*
             * QuestionExampleDataList = getSelnums(connMgr, v_grcode, v_subj,
             * v_sulnums);
             */

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                if (v_bef_sulnum != ls.getInt("sulnum")) {
                    data = new SulmunQuestionExampleData();
                    data.setSubj(ls.getString("subj"));
                    data.setSulnum(ls.getInt("sulnum"));
                    data.setSultext(ls.getString("sultext"));
                    data.setSultype(ls.getString("sultype"));
                    data.setSultypenm(ls.getString("sultypenm"));
                    data.setDistcode(ls.getString("distcode"));
                    data.setDistcodenm(ls.getString("distcodenm"));
                }
                exampledata = new SulmunExampleData();
                exampledata.setSubj(data.getSubj());
                exampledata.setSulnum(data.getSulnum());
                exampledata.setSelnum(ls.getInt("selnum"));
                exampledata.setSelpoint(ls.getInt("selpoint"));
                exampledata.setSeltext(ls.getString("seltext"));
                data.add(exampledata);
                if (v_bef_sulnum != data.getSulnum()) {
                    hash.put(String.valueOf(data.getSulnum()), data);
                    v_bef_sulnum = data.getSulnum();
                }
            }
            data = null;
            for (int i = 0; i < p_sulnums.size(); i++) {
                data = (SulmunQuestionExampleData) hash.get((String) p_sulnums.get(i));
                if (data != null) {
                    list.add(data);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return list;
    }

    /**
     * ���԰�μ��� ��� �������� ���ϱ�
     * 
     * @param box receive from the form object and session
     * @return void
     */
    public void ComputeCount(ArrayList<SulmunQuestionExampleData> p_list, Vector<String> p_answers) throws Exception {
        StringTokenizer st1 = null;
        StringTokenizer st2 = null;

        SulmunQuestionExampleData data = null;
        Vector<String> subject = new Vector<String>();
        Vector<String> complex = new Vector<String>();
        String v_answers = "";
        String v_answer = "";
        int index = 0;

        try {
            for (int i = 0; i < p_answers.size(); i++) {
                v_answers = (String) p_answers.get(i);
                st1 = new StringTokenizer(v_answers, SulmunRegistBean.SPLIT_COMMA);
                index = 0;
                while (st1.hasMoreElements() && index < p_list.size()) {
                    v_answer = (String) st1.nextToken();
                    data = (SulmunQuestionExampleData) p_list.get(index);

                    if (data.getSultype().equals(SulmunRegistBean.OBJECT_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    } else if (data.getSultype().equals(SulmunRegistBean.MULTI_QUESTION)) {
                        st2 = new StringTokenizer(v_answer, SulmunRegistBean.SPLIT_COLON);
                        while (st2.hasMoreElements()) {
                            v_answer = (String) st2.nextToken();
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                        }
                    } else if (data.getSultype().equals(SulmunRegistBean.SUBJECT_QUESTION)) {
                        subject.add(v_answer);
                    } else if (data.getSultype().equals(SulmunRegistBean.COMPLEX_QUESTION)) {
                        st2 = new StringTokenizer(v_answer, SulmunRegistBean.SPLIT_COLON);
                        v_answer = (String) st2.nextToken();
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                        while (st2.hasMoreElements()) {
                            v_answer = (String) st2.nextToken();
                            complex.add(v_answer);
                        }
                    } else if (data.getSultype().equals(SulmunRegistBean.FSCALE_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    } else if (data.getSultype().equals(SulmunRegistBean.SSCALE_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    }
                    index++;
                }
            }
            // ��������� ����Ѵ�. ����Ʈ((������ȣ1, ����1,2,3..))
            for (int i = 0; i < p_list.size(); i++) {
                data = (SulmunQuestionExampleData) p_list.get(i);
                data.ComputeRate();
                data.setComplexAnswer(complex);
                data.setSubjectAnswer(subject);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * ���԰�μ��� ��� ���� �亯 ���ϱ�
     * 
     * @param box receive from the form object and session
     * @return Vector
     */
    public Vector<String> getSulmunAnswers(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, int p_sulpapernum, String p_subjseq, String p_company, String p_jikwi, String p_jikun, String p_workplc)
            throws Exception {
        ListSet ls = null;
        String sql = "";
        Vector<String> v_answers = new Vector<String>();
        // String v_comp = "";

        try {
            sql = "  select c.answers 			\n";
            sql += "     from    tz_suleach    c, \n";
            sql += "         tz_member     d  	\n";
            sql += "     where c.userid  = d.userid  \n";
            sql += " and c.grcode  = " + SQLString.Format(p_grcode);
            if (!p_gyear.equals("ALL")) {
                sql += " and c.year   = " + SQLString.Format(p_gyear);
            }
            //            if (!p_grseq.equals("ALL"))
            //                sql+= " and a.grseq   = " + SQLString.Format(p_grseq);
            //            sql+= "     and c.f_gubun = " + SQLString.Format(p_gubun);
            sql += "     and c.subj    = " + SQLString.Format(p_subj);
            sql += " 	and c.gubun = 'REGIST'  \n";
            if (!p_subjseq.equals("ALL"))
                sql += "     and c.subjseq = " + SQLString.Format(p_subjseq);
            sql += "     and c.sulpapernum = " + SQLString.Format(p_sulpapernum);

            System.out.println("SulmunRegistResultBean ���԰�μ��� ��� ���� getSulmunAnswers:" + sql);

            //Log.info.println("����Ʈ3>>"+sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_answers.add(ls.getString("answers"));
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return v_answers;
    }

    /**
     * ���԰�μ��� ��� �л��� ���ϱ�
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int getStudentCount(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, String p_subjseq, String p_company, String p_jikwi, String p_jikun, String p_workplc) throws Exception {
        ListSet ls = null;
        String sql = "";
        // String v_comp = "";
        int v_count = 0;

        try {
            sql = "  select count(*) cnt ";
            sql += "    from tz_subjseq    a, ";
            sql += "         tz_student    b, ";
            sql += "         tz_member     c  ";
            sql += "   where a.subj    = b.subj  ";
            sql += "     and a.year    = b.year  ";
            sql += "     and a.subjseq = b.subjseq  ";
            sql += "     and b.userid  = c.userid   ";
            sql += "     and a.subj  = " + SQLString.Format(p_subj);
            if (!p_grcode.equals("ALL"))
                sql += " and a.grcode  = " + SQLString.Format(p_grcode);
            if (!p_gyear.equals("ALL"))
                sql += " and a.gyear   = " + SQLString.Format(p_gyear);
            if (!p_gyear.equals("ALL"))
                sql += " and a.subjseq   = " + SQLString.Format(p_subjseq);
            if (!p_grseq.equals("ALL"))
                //                sql+= " and a.grseq   = " + SQLString.Format(p_grseq);

                if (!p_company.equals("ALL"))
                    sql += "     and c.comp = " + SQLString.Format(p_company);
            if (!p_jikwi.equals("ALL"))
                sql += " and c.jikwi   = " + SQLString.Format(p_jikwi);
            if (!p_jikun.equals("ALL"))
                sql += " and c.jikun   = " + SQLString.Format(p_jikun);
            if (!p_workplc.equals("ALL"))
                sql += " and c.work_plc   = " + SQLString.Format(p_workplc);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_count = ls.getInt("cnt");
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e) {
                }
            }
        }
        return v_count;
    }

    /**
     * �Ϸù�ȣ ���ϱ�
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int getPapernumSeq(String p_subj, String p_grcode) throws Exception {
        Hashtable<String, String> maxdata = new Hashtable<String, String>();
        maxdata.put("seqcolumn", "sulpapernum");
        maxdata.put("seqtable", "tz_sulpaper");
        maxdata.put("paramcnt", "2");
        maxdata.put("param0", "subj");
        maxdata.put("param1", "grcode");
        maxdata.put("subj", SQLString.Format(p_subj));
        maxdata.put("grcode", SQLString.Format(p_grcode));

        return SelectionUtil.getSeq(maxdata);
    }

    @SuppressWarnings("unchecked")
    public ArrayList<DataBox> selectBoardList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;
        // Vector v_answers = null;

        int v_pageno = box.getInt("p_pageno");
        if (v_pageno == 0)
            v_pageno = 1;
        int v_row = box.getInt("p_row");
        if (v_row == 0)
            v_row = 10;

        String v_searchtext = box.getString("s_searchtext");

        // String v_grseq = box.getStringDefault("p_grseq", "0001");
        // String v_gyear = box.getStringDefault("p_gyear", "ALL");

        // ������ ��������
        // String v_company = box.getStringDefault("p_company", "ALL");
        // String v_jikwi = box.getStringDefault("p_jikwi", "ALL");
        // String v_jikun = box.getStringDefault("p_jikun", "ALL");
        // String v_workplc = box.getStringDefault("p_workplc", "ALL");

        String ss_upperclass = box.getStringDefault("s_upperclass", "ALL");
        String ss_middleclass = box.getStringDefault("s_middleclass", "ALL");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select b.subj,        ";
            sql += "        a.sulpapernum, a.sulpapernm,  ";
            sql += "       b.subjnm , substring(b.indate,1,4) indate, '0001' as subjseq       ";
            sql += "  from tz_sulpaper  a, ";
            sql += "       tz_subj   b  ";
            sql += "   where a.subj  = 'REGIST' ";
            sql += "   and a.grcode    = 'ALL' and b.isonoff = 'ON' and b.isuse='Y' and b.isapproval='Y' ";

            if (!v_searchtext.equals("")) { //    �˻�� ������
                //v_pageno = 1;   //      �˻��� ��� ù��° �������� �ε��ȴ�

                sql += " and b.subjnm like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }
            if (!ss_upperclass.equals("ALL")) {
                sql += "   and b.upperclass   = " + SQLString.Format(ss_upperclass);
            }
            if (!ss_middleclass.equals("ALL")) {
                sql += "   and b.middleclass   = " + SQLString.Format(ss_middleclass);
            }

            sql += " order by b.indate desc ";
            //System.out.println(sql);
            ls = connMgr.executeQuery(sql);
            ls.setPageSize(v_row); //  �������� row ������ �����Ѵ�
            ls.setCurrentPage(v_pageno); //     ������������ȣ�� �����Ѵ�.
            int totalpagecount = ls.getTotalPage(); //     ��ü ������ ���� ��ȯ�Ѵ�
            int totalrowcount = ls.getTotalCount(); //     ��ü row ���� ��ȯ�Ѵ�

            while (ls.next()) {
                dbox = ls.getDataBox();

                box.put("p_subj", dbox.getString("d_subj"));
                box.put("p_subjseq", "ALL");
                ArrayList<SulmunQuestionExampleData> list1 = this.getObjectSulmunResult(connMgr, box);

                SulmunQuestionExampleData data = null;
                SulmunExampleData subdata = null;
                double v_total = 0;

                for (int i = 0; i < list1.size(); i++) {
                    data = (SulmunQuestionExampleData) list1.get(i);

                    double d = 0;
                    int person = 0;
                    double v_point = 0;

                    for (int j = 1; j <= data.size(); j++) {
                        subdata = (SulmunExampleData) data.get(j);
                        if (subdata != null) {

                            d += (subdata.getReplycnt()) * subdata.getSelpoint();
                            person += subdata.getReplycnt();
                        }
                    }

                    v_point = d / person;

                    v_total += v_point;
                }

                double v_average = v_total / list1.size();

                DecimalFormat df = new DecimalFormat("0.0");

                //����
                if (v_average > 0) {
                    //	   dbox.put("d_average", String.valueOf(v_average));
                    dbox.put("d_average", df.format(v_average));
                } else {
                    dbox.put("d_average", "0.0");
                }

                // �����ڼ�
                dbox.put("d_replycount", box.getString("p_replycount"));

                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(v_row));
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
     * ���κ� ����Ʈ
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectResultMemberList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;

        //String v_grcode     = box.getString("p_grcode");

        String v_action = box.getString("p_action");
        String v_subj = box.getString("p_subj");
        String v_gyear = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            if (v_action.equals("go")) {
                sql = "select a.userid, a.ldate, b.name, get_compnm(b.comp,2,2) compnm, b.deptnam , b.jikwinm, a.answers, ";
                sql += "       NVL(a.distcode1,0) distcode1, NVL(a.distcode2,0) distcode2, NVL(a.distcode3,0) distcode3  ";
                sql += "  from tz_suleach a, tz_member b  ";
                sql += " where a.userid=b.userid         ";
                sql += "   and a.subj='" + v_subj + "' and a.grcode='ALL' and a.year='" + v_gyear + "' and a.subjseq='" + v_subjseq + "'";

                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
                }
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