//**********************************************************
//1. 제      목:
//2. 프로그램명: SulmunSubjResultBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-18
//7. 수      정:
//
//**********************************************************

package com.credu.research;

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
public class SulmunSubjResultBean {

    public SulmunSubjResultBean() {
    }

    /**
     * 과정설문 결과 보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<SulmunQuestionExampleData> SelectObectResultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<SulmunQuestionExampleData> list = null;
        String v_action = box.getString("p_action");
        System.out.println("설문결과" + v_action);
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

    /**
     * 과정설문 결과 보기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    @SuppressWarnings("unchecked")
    public ArrayList<SulmunQuestionExampleData> getObjectSulmunResult(DBConnectionManager connMgr, RequestBox box) throws Exception {
        Vector<String> v_sulnums = null;
        ArrayList<SulmunQuestionExampleData> QuestionExampleDataList = null;
        Vector<String> v_answers = null;

        String v_subj = box.getStringDefault("p_subj", box.getString("s_subjcourse"));
        String v_subjseq = box.getStringDefault("p_subjseq", box.getString("s_subjseq"));
        int v_sulpapernum = box.getInt("p_sulpapernum");
        if (v_sulpapernum == 0)
            v_sulpapernum = box.getInt("s_sulpapernum");

        String v_grseq = box.getStringDefault("p_grseq", "0001");

        // 설문번호 선택조건
        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_gyear");

        // 응답자 선택조건
        String v_company = box.getStringDefault("p_company", "ALL");
        String v_jikwi = box.getStringDefault("p_jikwi", "ALL");
        String v_jikun = box.getStringDefault("p_jikun", "ALL");
        String v_workplc = box.getStringDefault("p_workplc", "ALL");

        int v_studentcount = 0;

        try {

            //   v_sulpapernum = getPapernumSeq(v_subj, v_grcode) - 1;

            // 설문지번호에 해당하는 설문번호를 vector로 받아온다. 벡터(설문번호1,3,5 ....)
            // v_sulnums = getSulnums(connMgr, v_grcode, v_gyear, v_subj, v_subjseq, v_sulpapernum);

            v_sulnums = getSulnums(connMgr, "ALL", v_gyear, "ALL", v_subjseq, v_sulpapernum);

            // 설문번호에 해당하는 문제리스트를 만든다. 리스트((설문번호1, 보기1,2,3..))
            // QuestionExampleDataList = getSelnums(connMgr, v_subj, v_sulnums);
            QuestionExampleDataList = getSelnums(connMgr, "ALL", v_sulnums);

            // 설문지 답변모음을 읽어온다. 벡터(답변1,3,2,3..)
            v_answers = getSulmunAnswers(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_sulpapernum, v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);

            // 리스트((설문번호1, 보기1,2,3..)) + 벡터(답변1,3,2,3..) 이용해서 응답자수 및 비율을 계산한다.
            ComputeCount(QuestionExampleDataList, v_answers);

            // 응답자수
            box.put("p_replycount", String.valueOf(v_answers.size()));
            System.out.println("p_replycount>>>>" + box.getString("p_replycount"));
            // 수강자수
            v_studentcount = getStudentCount(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);

            box.put("p_studentcount", String.valueOf(v_studentcount));
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        System.out.println("QuestionExampleDataList" + QuestionExampleDataList.size());
        return QuestionExampleDataList;
    }

    /**
     * 문제번호(안쓰임)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
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
            //            sql+= " where grcode      = " + SQLString.Format(SulmunSubjBean.DEFAULT_GRCODE);
            sql += "   and subj        = " + SQLString.Format(p_subj);
            sql += "   and sulpapernum = " + SQLString.Format(p_sulpapernum);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_tokens = ls.getString("sulnums");
            }
            ls.close();

            st = new StringTokenizer(v_tokens, SulmunSubjBean.SPLIT_COMMA);
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
     * 문제번호(쓰임)
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public Vector<String> getSulnums(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_subj, String p_subjseq, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql = "";
        Vector<String> v_sulnums = new Vector<String>();
        String v_tokens = "";
        StringTokenizer st = null;

        try {

            // subj='ALL' , GRCODE='ALL'
            sql = "select sulnums  ";
            sql += "  from tz_sulpaper ";
            sql += " where grcode      = " + SQLString.Format(p_grcode);
            //sql+= " and year      = " + SQLString.Format(p_gyear);
            sql += "   and subj        = " + SQLString.Format(p_subj);
            //if (!p_subjseq.equals("ALL"))
            //    sql+= "   and subjseq        = " + SQLString.Format(p_subjseq);
            sql += "   and sulpapernum = " + SQLString.Format(p_sulpapernum);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_tokens = ls.getString("sulnums");
            }
            ls.close();

            st = new StringTokenizer(v_tokens, SulmunSubjBean.SPLIT_COMMA);
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
     * 문제리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<SulmunQuestionExampleData> getSelnums(DBConnectionManager connMgr, String p_subj, Vector<String> p_sulnums) throws Exception {
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
            /*
             * sql = "select a.subj,     a.sulnum, ";
             * sql+="        a.distcode, c.codenm distcodenm, "; sql+=
             * "       a.sultype,  d.codenm sultypenm, "; sql+=
             * "       a.sultext,  b.selnum, b.seltext, b.selpoint "; sql+=
             * "  from tz_sul     a, "; sql+= "       tz_sulsel  b, "; sql+=
             * "       tz_code    c, "; sql+= "       tz_code    d  "; sql+=
             * " where a.subj     = b.subj(+)    "; sql+=
             * "   and a.sulnum   = b.sulnum(+)  "; sql+=
             * "   and a.distcode = c.code "; sql+=
             * "   and a.sultype  = d.code "; sql+= "   and a.subj     = " +
             * SQLString.Format(p_subj); sql+= "   and a.sulnum in (" +
             * v_sulnums + ")"; sql+= "   and c.gubun    = " +
             * SQLString.Format(SulmunSubjBean.DIST_CODE); sql+=
             * "   and d.gubun    = " +
             * SQLString.Format(SulmunSubjBean.SUL_TYPE); sql+=
             * " order by a.subj, a.sulnum, b.selnum ";
             */

            sql = "select a.subj,     a.sulnum,         a.distcode, c.codenm distcodenm,        a.sultype,  d.codenm sultypenm, ";
            sql += "        a.sultext,  b.selnum, b.seltext, b.selpoint                                ";
            sql += " from tz_sul     a,        tz_sulsel  b,        tz_code   c,        tz_code    d  ";
            // 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
            //            sql+= " where a.subj   = b.subj(+)  AND a.GRCODE     = b.GRCODE(+)                        ";
            //            sql+= "   and a.sulnum   = b.sulnum(+)     and a.distcode = c.code                        ";
            sql += " where a.subj    =  b.subj(+)  AND a.GRCODE      =  b.GRCODE(+)                        ";
            sql += "   and a.sulnum    =  b.sulnum(+)     and a.distcode = c.code                        ";
            sql += "   and a.sultype  = d.code    and a.subj     = 'ALL' AND a.GRCODE     = 'ALL'      ";
            sql += "   and a.sulnum in (" + v_sulnums + ")";
            sql += "   and c.gubun    = " + SQLString.Format(SulmunSubjBean.DIST_CODE);
            sql += "   and d.gubun    = " + SQLString.Format(SulmunSubjBean.SUL_TYPE);
            sql += " order by a.subj, a.sulnum, b.selnum ";

            System.out.println("설문>>" + sql);
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
            ls.close();
            data = null;
            System.out.println(">>>>>>>>>p_sulnums" + p_sulnums.size());
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
     * 과정설문 결과보기 응답율 구하기
     * 
     * @param box receive from the form object and session
     * @return ArrayList
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
                st1 = new StringTokenizer(v_answers, SulmunSubjBean.SPLIT_COMMA);
                index = 0;
                while (st1.hasMoreElements() && index < p_list.size()) {
                    v_answer = (String) st1.nextToken();
                    data = (SulmunQuestionExampleData) p_list.get(index);

                    if (data.getSultype().equals(SulmunSubjBean.OBJECT_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    } else if (data.getSultype().equals(SulmunSubjBean.MULTI_QUESTION)) {
                        st2 = new StringTokenizer(v_answer, SulmunSubjBean.SPLIT_COLON);
                        while (st2.hasMoreElements()) {
                            v_answer = (String) st2.nextToken();
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                        }
                    } else if (data.getSultype().equals(SulmunSubjBean.SUBJECT_QUESTION)) {
                        subject.add(v_answer);//System.out.println("v_answer " + v_answer);
                    } else if (data.getSultype().equals(SulmunSubjBean.COMPLEX_QUESTION)) {
                        st2 = new StringTokenizer(v_answer, SulmunSubjBean.SPLIT_COLON);
                        v_answer = (String) st2.nextToken();
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                        String v_sanswer = "";
                        if (st2.hasMoreElements()) {
                            v_sanswer = (String) st2.nextToken();
                        }
                        complex.add(v_sanswer);
                    } else if (data.getSultype().equals(SulmunSubjBean.FSCALE_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    } else if (data.getSultype().equals(SulmunSubjBean.SSCALE_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    }

                    index++;
                }
            }
            // 응답비율을 계산한다. 리스트((설문번호1, 보기1,2,3..))
            for (int i = 0; i < p_list.size(); i++) {
                data = (SulmunQuestionExampleData) p_list.get(i);
                data.ComputeRate();
                data.setComplexAnswer(complex);
                data.setSubjectAnswer(subject);
                System.out.println(subject);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    /**
     * 과정설문 결과보기 정답 구하기
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
            /*
             * sql = "  select c.answers "; sql+= "    from tz_subjseq    a, ";
             * sql+= "         tz_student    b, "; sql+=
             * "         tz_suleach    c, "; sql+= "         tz_member     d  ";
             * sql+= "   where a.subj    = b.subj  "; sql+=
             * "     and a.year    = b.year  "; sql+=
             * "     and a.subjseq = b.subjseq  "; sql+=
             * "     and b.subj    = c.subj  "; sql+=
             * "     and b.year    = c.year  "; sql+=
             * "     and b.subjseq = c.subjseq  "; sql+=
             * "     and b.userid  = c.userid  "; sql+=
             * "     and c.userid  = d.userid  "; if (!p_grcode.equals("ALL"))
             * sql+= " and c.grcode  = " + SQLString.Format(p_grcode); if
             * (!p_gyear.equals("ALL")) sql+= " and c.year   = " +
             * SQLString.Format(p_gyear); // if (!p_grseq.equals("ALL")) //
             * sql+= " and a.grseq   = " + SQLString.Format(p_grseq); // sql+=
             * "     and c.f_gubun = " + SQLString.Format(p_gubun); sql+=
             * "     and c.subj    = " + SQLString.Format(p_subj); if
             * (!p_subjseq.equals("ALL")) sql+= "     and c.subjseq = " +
             * SQLString.Format(p_subjseq);
             * 
             * sql+= "     and c.sulpapernum = " +
             * SQLString.Format(p_sulpapernum);
             * 
             * if (!p_company.equals("ALL")) sql+= "     and d.comp = " +
             * SQLString.Format(p_company); if (!p_jikwi.equals("ALL")) sql+=
             * " and d.jikwi   = " + SQLString.Format(p_jikwi); if
             * (!p_jikun.equals("ALL")) sql+= " and d.jikun   = " +
             * SQLString.Format(p_jikun); if (!p_workplc.equals("ALL")) sql+=
             * " and d.work_plc   = " + SQLString.Format(p_workplc);
             */

            sql = "select answers from tz_suleach where grcode  = " + SQLString.Format(p_grcode) + " and  subj=" + SQLString.Format(p_subj) + "";
            sql += " and year=" + SQLString.Format(p_gyear) + " and subjseq=" + SQLString.Format(p_subjseq) + " ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_answers.add(ls.getString("answers"));
            }
            ls.close();
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
     * 과정설문 결과보기 학생수 구하기
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
            if (!p_subjseq.equals("ALL"))
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

    public int getPapernumSeq(String p_subj, String p_grcode, String p_gyear, String p_subjseq) throws Exception {
        Hashtable<String, String> maxdata = new Hashtable<String, String>();
        maxdata.put("seqcolumn", "sulpapernum");
        maxdata.put("seqtable", "tz_sulpaper");
        maxdata.put("paramcnt", "4");
        maxdata.put("param0", "subj");
        maxdata.put("param1", "grcode");
        maxdata.put("param2", "year");
        maxdata.put("param3", "subjseq");
        maxdata.put("subj", SQLString.Format(p_subj));
        maxdata.put("grcode", SQLString.Format(p_grcode));
        maxdata.put("year", SQLString.Format(p_gyear));
        maxdata.put("subjseq", SQLString.Format(p_subjseq));

        return SelectionUtil.getSeq(maxdata);
    }

    /**
     * 자료실 리스트화면 select
     * 
     * @param box receive from the form object and session
     * @return ArrayList 자료실 리스트
     * @throws Exception
     */
    @SuppressWarnings("deprecation")
    public ArrayList<DataBox> selectBoardList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        String sql = "";
        DataBox dbox = null;
        Vector<String> v_answers = null;

        int v_pageno = box.getInt("p_pageno");
        if (v_pageno == 0)
            v_pageno = 1;
        int v_row = box.getInt("p_row");
        if (v_row == 0)
            v_row = 10;

        java.util.Date d_now = new java.util.Date();
        String v_gyear = box.getStringDefault("s_gyear", String.valueOf(d_now.getYear() + 1900));
        String v_searchtype = box.getString("s_searchtype");
        String v_searchtext = box.getString("s_searchtext");

        String v_grseq = box.getStringDefault("p_grseq", "0001");

        // 응답자 선택조건
        String v_company = box.getStringDefault("p_company", "ALL");
        String v_jikwi = box.getStringDefault("p_jikwi", "ALL");
        String v_jikun = box.getStringDefault("p_jikun", "ALL");
        String v_workplc = box.getStringDefault("p_workplc", "ALL");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList<DataBox>();

            sql = "select distinct p.subj, p.subjseq, p.grcode, p.sulpapernum, p.sulpapernm, p.year, p.edustart, p.eduend, p.subjnm";
            sql += "        from";
            sql += " (select b.subj,      b.subjseq,  b.grcode,    ";
            sql += "        a.sulpapernum, a.sulpapernm, b.year, ";
            sql += "       b.edustart,  b.eduend,    ";
            sql += "       b.subjnm        ";
            sql += "  from tz_sulpaper  a, ";
            sql += "       tz_subjseq   b  ";
            sql += "   where a.subj  = b.subj ";
            sql += "   and a.subjseq    = b.subjseq ";
            sql += "   and b.year  = " + SQLString.Format(v_gyear);
            sql += "   and b.isclosed = 'Y'";
            if (!v_searchtext.equals("")) { //    검색어가 있으면
                //      검색할 경우 첫번째 페이지가 로딩된다
                if (v_searchtype.equals("1")) { //    설문제목으로 검색할때
                    sql += " and a.sulpapernm like " + StringManager.makeSQL("%" + v_searchtext + "%");
                } else if (v_searchtype.equals("2")) { //    과정명으로 검색할때
                    sql += " and b.subjnm like " + StringManager.makeSQL("%" + v_searchtext + "%");
                }
            }
            sql += "  ) p, ";
            sql += " tz_suleach q ";
            sql += " where p.subj = q.subj and p.grcode = q.grcode and p.year = q.year and p.subjseq = q.subjseq ";
            sql += " and p.sulpapernum = q.sulpapernum ";
            sql += " order by p.edustart desc, p.subjnm";
            //System.out.println(sql);
            ls = connMgr.executeQuery(sql);
            ls.setPageSize(v_row); //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno); //     현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage(); //     전체 페이지 수를 반환한다
            int totalrowcount = ls.getTotalCount(); //     전체 row 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();

                // 설문지 답변모음을 읽어온다. 벡터(답변1,3,2,3..)
                v_answers = getSulmunAnswers(connMgr, dbox.getString("d_grcode"), v_gyear, v_grseq, dbox.getString("d_subj"), dbox.getInt("d_sulpapernum"), dbox.getString("d_subjseq"), v_company, v_jikwi, v_jikun, v_workplc);

                // 응답자수
                dbox.put("d_replycount", String.valueOf(v_answers.size()));

                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(v_row));
                list.add(dbox);
            }
            ls.close();
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
     * 과정설문 결과 엑셀리스트_상세
     * 
     * @param box receive from the form object and session
     * @return ArrayList 과정설문 결과 엑셀리스트
     */
    public ArrayList<DataBox> selectSulmunDetailResultExcelList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList<DataBox> list = null;
        // ArrayList list2 = null;
        ListSet ls = null;
        ListSet ls2 = null;
        DataBox dbox = null;
        //     DataBox dbox2 = null;
        String sql = "";
        String sql2 = "";

        try {
            String ss_grcode = box.getString("s_grcode");
            String ss_subjcourse = box.getString("s_subjcourse");
            String ss_year = box.getString("s_gyear");
            String ss_subjseq = box.getString("s_subjseq");
            String v_action = box.getString("p_action");
            int v_sulpapernum = box.getInt("s_sulpapernum");
            String v_subjusernm = "";

            list = new ArrayList<DataBox>();

            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();

                sql = "select a.userid, b.comp  asgn,  get_compnm(b.comp,2,4) asgnnm, b.divinam,";
                sql += "	   	  b.jikwi,  get_jikwinm(b.jikwi, b.comp) jikwinm, ";
                sql += "	   	  b.cono,   b.name ";
                sql += "	   	  from ";
                sql += " ( select userid, subj, year, subjseq ";
                sql += "  from tz_student  ";
                sql += "   where subj    = " + SQLString.Format(ss_subjcourse);
                sql += "   and year    = " + SQLString.Format(ss_year);
                sql += "   and subjseq    = " + SQLString.Format(ss_subjseq) + " ) a, ";
                sql += "         tz_member  b  ";
                sql += "         where a.userid = b.userid  ";
                sql += " order by a.userid ";

                ls = connMgr.executeQuery(sql);

                Vector<String> sulnum = new Vector<String>();

                while (ls.next()) {
                    dbox = ls.getDataBox();

                    v_subjusernm = dbox.getString("d_name");
                    dbox.put("d_subjusernm", v_subjusernm);
                    dbox.put("d_subjasgmnm", dbox.getString("d_asgnnm"));

                    sql2 = "select userid, sulnums, answers";
                    sql2 += "  from tz_suleach";
                    sql2 += "   where subj    = " + SQLString.Format(ss_subjcourse);
                    sql2 += "   and grcode    = " + SQLString.Format(ss_grcode);
                    sql2 += "   and year    = " + SQLString.Format(ss_year);
                    sql2 += "   and subjseq    = " + SQLString.Format(ss_subjseq);
                    sql2 += "   and sulpapernum    = " + SQLString.Format(v_sulpapernum);
                    sql2 += "   and userid    = " + SQLString.Format(dbox.getString("d_userid"));

                    ls2 = connMgr.executeQuery(sql2);//System.out.println("sql2" + sql2);

                    if (ls2.next()) {
                        //   dbox2 = ls2.getDataBox();

                        dbox.put("d_anwsers", ls2.getString("answers"));//System.out.println("sulnums" + ls2.getString("sulnums"));

                        StringTokenizer st = new StringTokenizer(ls2.getString("sulnums"), ",");

                        while (st.hasMoreElements()) {
                            sulnum.addElement(st.nextToken());
                        }

                        String selpoint = "";

                        StringTokenizer f_st = new StringTokenizer(ls2.getString("answers"), ",");//System.out.println("fanswers" + ls2.getString("fanswers"));
                        for (int i = 0; f_st.hasMoreElements(); i++) {
                            String token = f_st.nextToken();
                            if (token.equals(""))
                                break;

                            selpoint = selpoint + this.getSelpoints(connMgr, ss_subjcourse, ss_grcode, (String) (sulnum.elementAt(i)), token) + ",";
                        } //      System.out.println(selpoint);
                        dbox.put("d_selpoint", selpoint);
                    }
                    ls2.close();

                    list.add(dbox);
                }
                ls.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
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

    public String getSelpoints(DBConnectionManager connMgr, String p_subj, String p_grcode, String p_sulnum, String p_sulselnum) throws Exception {
        ListSet ls1 = null;
        ListSet ls2 = null;
        String sql1 = "";
        String sql2 = "";
        String v_result = "0";
        try {
            sql1 = "select sultype from tz_sul where sulnum = " + p_sulnum;
            sql1 += "   and subj   = " + SQLString.Format(p_subj);
            sql1 += "   and grcode    = " + SQLString.Format(p_grcode);

            ls1 = connMgr.executeQuery(sql1);

            while (ls1.next()) {
                sql2 = "select selpoint";
                sql2 += "  from tz_sulsel  ";
                sql2 += "   where subj   = " + SQLString.Format(p_subj);
                sql2 += "   and grcode    = " + SQLString.Format(p_grcode);
                sql2 += "   and sulnum    = " + p_sulnum;
                sql2 += "   and selnum    = " + p_sulselnum;

                //System.out.println((ls1.getString("sultype")));
                //System.out.println(p_sulselnum);
                if (ls1.getString("sultype").equals("5") || ls1.getString("sultype").equals("6")) {

                    ls2 = connMgr.executeQuery(sql2);
                    while (ls2.next()) {
                        v_result = ls2.getInt(1) + "";
                    }
                    if (ls2 != null) {
                        try {
                            ls2.close();
                        } catch (Exception e) {
                        }
                    }
                }
            }
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e) {
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
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
        }
        return v_result;
    }

    /**
     * 개인별 리스트
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

        String v_action = box.getString("p_action");
        String v_subj = box.getString("p_subj");
        String v_gyear = box.getString("p_gyear");
        String v_subjseq = box.getString("p_subjseq");
        String v_grcode = box.getString("p_grcode");
        int v_sulpapernum = box.getInt("p_sulpapernum");
        if (v_sulpapernum == 0)
            v_sulpapernum = box.getInt("s_sulpapernum");
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            if (v_action.equals("go")) {
                sql = "select a.userid, a.ldate, b.name, get_compnm(b.comp,2,2) compnm, b.deptnam , b.jikwinm,  a.answers,  ";
                sql += "       NVL(a.distcode1,0) distcode1, NVL(a.distcode2,0) distcode2, NVL(a.distcode3,0) distcode3,     ";
                sql += "       NVL(a.distcode4,0) distcode4, NVL(a.distcode5,0) distcode5, NVL(a.distcode6,0) distcode6, NVL(a.distcode7,0) distcode7,    ";
                sql += "        NVL(a.distcode1_avg,0) distcode1_avg, NVL(a.distcode2_avg,0) distcode2_avg, NVL(a.distcode3_avg,0) distcode3_avg ,     ";
                sql += "        NVL(a.distcode4_avg,0) distcode4_avg, NVL(a.distcode5_avg,0) distcode5_avg, NVL(a.distcode6_avg,0) distcode6_avg , NVL(a.distcode7_avg,0) distcode7_avg     ";
                sql += "  from tz_suleach a, tz_member b  ";
                sql += " where a.userid=b.userid         ";
                sql += "   and a.subj='" + v_subj + "' and a.grcode='" + v_grcode + "' and a.year='" + v_gyear + "' and a.subjseq='" + v_subjseq + "' and sulpapernum=" + v_sulpapernum + " ";

                System.out.println("sql-------------------------============" + sql);

                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    dbox.put("distcode1avg", new Double(ls.getDouble("distcode1_avg")));
                    dbox.put("distcode2avg", new Double(ls.getDouble("distcode2_avg")));
                    dbox.put("distcode3avg", new Double(ls.getDouble("distcode3_avg")));
                    dbox.put("distcode4avg", new Double(ls.getDouble("distcode4_avg")));
                    dbox.put("distcode5avg", new Double(ls.getDouble("distcode5_avg")));
                    dbox.put("distcode6avg", new Double(ls.getDouble("distcode6_avg")));
                    dbox.put("distcode7avg", new Double(ls.getDouble("distcode7_avg")));
                    list.add(dbox);
                }
                ls.close();
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
