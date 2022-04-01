// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DamunSubjResultBean.java

package com.credu.multiestimate;

import com.credu.library.*;
import com.credu.system.SelectionUtil;
import java.util.*;

// Referenced classes of package com.credu.multiestimate:
//            DamunQuestionExampleData, DamunExampleData

public class DamunSubjResultBean
{

    public DamunSubjResultBean()
    {
    }

    public ArrayList SelectObectResultList(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_action = box.getString("p_action");
        try
        {
            if(v_action.equals("go"))
            {
                connMgr = new DBConnectionManager();
                list = getObjectDamunResult(connMgr, box);
            } else
            {
                list = new ArrayList();
            }
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(connMgr != null)
                try
                {
                    connMgr.freeConnection();
                }
                catch(Exception exception1) { }
        }
        return list;
    }

    public ArrayList getObjectDamunResult(DBConnectionManager connMgr, RequestBox box)
        throws Exception
    {
        Vector v_damunnums = null;
        ArrayList QuestionExampleDataList = null;
        Vector v_answers = null;
        Vector v_obanswers1 = null;
        Vector v_obanswers2 = null;
        Vector v_obanswers3 = null;
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getStringDefault("p_subjseq", "ALL");
        int v_damunpapernum = box.getInt("p_damunpapernum");
        String v_grseq = box.getStringDefault("p_grseq", "0001");
        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_gyear");
        String v_company = box.getStringDefault("s_company", "ALL");
        String v_jikwi = box.getStringDefault("s_jikwi", "ALL");
        String v_jikun = box.getStringDefault("s_jikun", "ALL");
        String v_workplc = box.getStringDefault("s_workplc", "ALL");
        String v_d_gubun = box.getString("p_d_gubun");
        int v_studentcount = 0;
        int v_studentobcount = 0;
        try
        {
            v_damunnums = getSulnums(connMgr, v_grcode, v_subj, v_damunpapernum);
            QuestionExampleDataList = getSelnums(connMgr, v_subj, v_damunnums);
            v_answers = getDamunAnswers(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_damunpapernum, v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);
            v_obanswers1 = getDamunOBAnswers(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_damunpapernum, "1", v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);
            v_obanswers2 = getDamunOBAnswers(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_damunpapernum, "2", v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);
            v_obanswers3 = getDamunOBAnswers(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_damunpapernum, "3", v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);
            ComputeCount(QuestionExampleDataList, v_answers, v_obanswers1, v_obanswers2, v_obanswers3);
            box.put("p_replycount", String.valueOf(v_answers.size()));
            box.put("p_replyobcount", String.valueOf(v_obanswers1.size() + v_obanswers2.size() + v_obanswers3.size()));
            v_studentcount = getStudentCount(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_subjseq, v_damunpapernum, v_company, v_jikwi, v_jikun, v_workplc);
            v_studentobcount = getStudentOBCount(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_subjseq, v_damunpapernum, v_company, v_jikwi, v_jikun, v_workplc);
            box.put("p_studentcount", String.valueOf(v_studentcount));
            box.put("p_studentobcount", String.valueOf(v_studentobcount));
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return QuestionExampleDataList;
    }

    public ArrayList SelectObectResultList2(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = null;
        String v_action = box.getString("p_action");
        try
        {
            if(v_action.equals("go"))
            {
                connMgr = new DBConnectionManager();
                list = getObjectDamunResult2(connMgr, box);
            } else
            {
                list = new ArrayList();
            }
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(connMgr != null)
                try
                {
                    connMgr.freeConnection();
                }
                catch(Exception exception1) { }
        }
        return list;
    }

    public ArrayList getObjectDamunResult2(DBConnectionManager connMgr, RequestBox box)
        throws Exception
    {
        Vector v_damunnums = null;
        ArrayList QuestionExampleDataList = null;
        Vector v_answers = null;
        Vector v_obanswers1 = null;
        Vector v_obanswers2 = null;
        Vector v_obanswers3 = null;
        String v_subj = box.getString("p_subj");
        String v_subjseq = box.getString("p_subjseq");
        int v_damunpapernum = box.getInt("p_damunpapernum");
        String v_grseq = box.getStringDefault("p_grseq", "0001");
        String v_grcode = box.getString("p_grcode");
        String v_gyear = box.getString("p_gyear");
        String v_company = box.getStringDefault("s_company", "ALL");
        String v_jikwi = box.getStringDefault("s_jikwi", "ALL");
        String v_jikun = box.getStringDefault("s_jikun", "ALL");
        String v_workplc = box.getStringDefault("s_workplc", "ALL");
        String v_d_gubun = box.getString("p_d_gubun");
        int v_studentcount = 0;
        int v_studentobcount = 0;
        try
        {
            v_damunnums = getSulnums(connMgr, v_grcode, v_subj, v_damunpapernum);
            QuestionExampleDataList = getSelnums(connMgr, v_subj, v_damunnums);
            v_answers = getDamunAnswers2(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_damunpapernum, v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);
            v_obanswers1 = getDamunOBAnswers2(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_damunpapernum, "1", v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);
            v_obanswers2 = getDamunOBAnswers2(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_damunpapernum, "2", v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);
            v_obanswers3 = getDamunOBAnswers2(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_damunpapernum, "3", v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);
            ComputeCount2(QuestionExampleDataList, v_answers, v_obanswers1, v_obanswers2, v_obanswers3);
            box.put("p_replycount2", String.valueOf(v_answers.size()));
            box.put("p_replyobcount2", String.valueOf(v_obanswers1.size() + v_obanswers2.size() + v_obanswers3.size()));
            v_studentcount = getStudentCount(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_subjseq, v_damunpapernum, v_company, v_jikwi, v_jikun, v_workplc);
            v_studentobcount = getStudentOBCount(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_subjseq, v_damunpapernum, v_company, v_jikwi, v_jikun, v_workplc);
            box.put("p_studentcount2", String.valueOf(v_studentcount));
            box.put("p_studentobcount2", String.valueOf(v_studentobcount));
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        return QuestionExampleDataList;
    }

    public Vector getSulnums(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_damunpapernum)
        throws Exception
    {
        Vector v_damunnums;
        ListSet ls = null;
        String sql = "";
        v_damunnums = new Vector();
        String v_tokens = "";
        StringTokenizer st = null;
        try
        {
            sql = "select damunnums  ";
            sql = sql + "  from tz_damunpaper ";
            sql = sql + " where grcode      = " + SQLString.Format(p_grcode);
            sql = sql + "   and subj        = " + SQLString.Format(p_subj);
            sql = sql + "   and damunpapernum = " + SQLString.Format(p_damunpapernum);
            for(ls = connMgr.executeQuery(sql); ls.next();)
                v_tokens = ls.getString("damunnums");

            for(st = new StringTokenizer(v_tokens, ","); st.hasMoreElements(); v_damunnums.add(st.nextToken()));
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        return v_damunnums;
    }

    public ArrayList getSelnums(DBConnectionManager connMgr, String p_subj, Vector p_damunnums)
        throws Exception
    {
        ArrayList list;
        Hashtable hash = new Hashtable();
        list = new ArrayList();
        ListSet ls = null;
        String sql = "";
        DamunQuestionExampleData data = null;
        DamunExampleData exampledata = null;
        int v_bef_damunnum = 0;
        String v_damunnums = "";
        for(int i = 0; i < p_damunnums.size(); i++)
        {
            v_damunnums = v_damunnums + (String)p_damunnums.get(i);
            if(i < p_damunnums.size() - 1)
                v_damunnums = v_damunnums + ", ";
        }

        if(v_damunnums.equals(""))
            v_damunnums = "-1";
        try
        {
            sql = "select a.subj, a.grcode, a.damunnum, a.damuntype, a.damuntext, a.selcount, a.selmax, a.fscalecode, a.sscalecode, b.scalename, b.selnum, b.selpoint, b.seltext ";
            sql = sql + "  from tz_damun     a, ";
            sql = sql + "       tz_damunsel  b  ";
            sql = sql + " where a.subj   = b.subj(+)    ";
            sql = sql + "   and a.damunnum = b.damunnum(+)  ";
            sql = sql + "   and a.subj   = " + SQLString.Format(p_subj);
            sql = sql + "   and a.damunnum in (" + v_damunnums + ")";
            sql = sql + " order by b.damunnum , b.selnum asc ";
            for(ls = connMgr.executeQuery(sql); ls.next();)
            {
                if(v_bef_damunnum != ls.getInt("damunnum"))
                {
                    data = new DamunQuestionExampleData();
                    data.setSubj(ls.getString("subj"));
                    data.setSulnum(ls.getInt("damunnum"));
                    data.setSultext(ls.getString("damuntext"));
                    data.setSultype(ls.getString("damuntype"));
                }
                exampledata = new DamunExampleData();
                exampledata.setSubj(data.getSubj());
                exampledata.setSulnum(data.getSulnum());
                exampledata.setSelnum(ls.getInt("selnum"));
                exampledata.setSelpoint(ls.getInt("selpoint"));
                exampledata.setSeltext(ls.getString("seltext"));
                exampledata.setScalename(ls.getString("scalename"));
                data.add(exampledata);
                if(v_bef_damunnum != data.getSulnum())
                {
                    hash.put(String.valueOf(data.getSulnum()), data);
                    v_bef_damunnum = data.getSulnum();
                }
            }

            data = null;
            for(int i = 0; i < p_damunnums.size(); i++)
            {
                data = (DamunQuestionExampleData)hash.get((String)p_damunnums.get(i));
                if(data != null)
                    list.add(data);
            }

        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        return list;
    }

    public void ComputeCount(ArrayList p_list, Vector p_answers1, Vector p_answers2, Vector p_answers3, Vector p_answers4)
        throws Exception
    {
        StringTokenizer st1 = null;
        StringTokenizer st2 = null;
        DamunQuestionExampleData data = null;
        Vector subject = new Vector();
        Vector r1subject = new Vector();
        Vector r2subject = new Vector();
        Vector r3subject = new Vector();
        Vector complex = new Vector();
        Vector r1complex = new Vector();
        Vector r2complex = new Vector();
        Vector r3complex = new Vector();
        String v_answers = "";
        String v_answer = "";
        int index = 0;
        Vector p_answers = null;
        int p_relation = 0;
        String v_subjectok = "(\uAD00\uCC30\uC790) ";
        try
        {
            for(int p = 0; p < 4; p++)
            {
                if(p == 0)
                {
                    p_answers = p_answers1;
                    p_relation = 0;
                } else
                if(p == 1)
                {
                    p_answers = p_answers2;
                    p_relation = 1;
                } else
                if(p == 2)
                {
                    p_answers = p_answers3;
                    p_relation = 2;
                } else
                if(p == 3)
                {
                    p_answers = p_answers4;
                    p_relation = 3;
                }
                for(int i = 0; i < p_answers.size(); i++)
                {
                    v_answers = (String)p_answers.get(i);
                    st1 = new StringTokenizer(v_answers, ",");
                    for(index = 0; st1.hasMoreElements() && index < p_list.size(); index++)
                    {
                        v_answer = st1.nextToken();
                        data = (DamunQuestionExampleData)p_list.get(index);
                        if(data.getSultype().equals("1"))
                        {
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                            if(p_relation == 0)
                                data.IncreasSubjectCount(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 1)
                                data.IncreasRelation1Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 2)
                                data.IncreasRelation2Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 3)
                                data.IncreasRelation3Count(Integer.valueOf(v_answer).intValue());
                        } else
                        if(data.getSultype().equals("2"))
                            for(st2 = new StringTokenizer(v_answer, ":"); st2.hasMoreElements();)
                            {
                                v_answer = st2.nextToken();
                                data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                                if(p_relation == 0)
                                    data.IncreasSubjectCount(Integer.valueOf(v_answer).intValue());
                                else
                                if(p_relation == 1)
                                    data.IncreasRelation1Count(Integer.valueOf(v_answer).intValue());
                                else
                                if(p_relation == 2)
                                    data.IncreasRelation2Count(Integer.valueOf(v_answer).intValue());
                                else
                                if(p_relation == 3)
                                    data.IncreasRelation3Count(Integer.valueOf(v_answer).intValue());
                            }

                        else
                        if(data.getSultype().equals("3"))
                        {
                            if(p_relation == 0)
                            {
                                v_answer = "(\uB300\uC0C1\uC790) " + v_answer;
                                subject.add(v_answer);
                            } else
                            if(p_relation == 1)
                            {
                                v_answer = "(\uAD00\uCC30\uC790) " + v_answer;
                                r1subject.add(v_answer);
                            } else
                            if(p_relation == 2)
                            {
                                v_answer = "(\uAD00\uCC30\uC790) " + v_answer;
                                r2subject.add(v_answer);
                            } else
                            if(p_relation == 3)
                            {
                                v_answer = "(\uAD00\uCC30\uC790) " + v_answer;
                                r3subject.add(v_answer);
                            }
                        } else
                        if(data.getSultype().equals("4"))
                        {
                            st2 = new StringTokenizer(v_answer, ":");
                            v_answer = st2.nextToken();
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                            if(p_relation == 0)
                                data.IncreasSubjectCount(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 1)
                                data.IncreasRelation1Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 2)
                                data.IncreasRelation2Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 3)
                                data.IncreasRelation3Count(Integer.valueOf(v_answer).intValue());
                            while(st2.hasMoreElements()) 
                            {
                                v_answer = st2.nextToken();
                                if(p_relation == 0)
                                {
                                    v_answer = "(\uB300\uC0C1\uC790) " + v_answer;
                                    complex.add(v_answer);
                                } else
                                if(p_relation == 1)
                                {
                                    v_answer = "(\uAD00\uCC30\uC790) " + v_answer;
                                    r1complex.add(v_answer);
                                } else
                                if(p_relation == 2)
                                {
                                    v_answer = "(\uAD00\uCC30\uC790) " + v_answer;
                                    r2complex.add(v_answer);
                                } else
                                if(p_relation == 3)
                                {
                                    v_answer = "(\uAD00\uCC30\uC790) " + v_answer;
                                    r3complex.add(v_answer);
                                }
                            }
                        } else
                        if(data.getSultype().equals("5"))
                        {
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                            if(p_relation == 0)
                                data.IncreasSubjectCount(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 1)
                                data.IncreasRelation1Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 2)
                                data.IncreasRelation2Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 3)
                                data.IncreasRelation3Count(Integer.valueOf(v_answer).intValue());
                        } else
                        if(data.getSultype().equals("6"))
                        {
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                            if(p_relation == 0)
                                data.IncreasSubjectCount(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 1)
                                data.IncreasRelation1Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 2)
                                data.IncreasRelation2Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 3)
                                data.IncreasRelation3Count(Integer.valueOf(v_answer).intValue());
                        } else
                        if(data.getSultype().equals("7"))
                        {
                            st2 = new StringTokenizer(v_answer, ":");
                            v_answer = st2.nextToken();
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                            if(p_relation == 0)
                                data.IncreasSubjectCount(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 1)
                                data.IncreasRelation1Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 2)
                                data.IncreasRelation2Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 3)
                                data.IncreasRelation3Count(Integer.valueOf(v_answer).intValue());
                            while(st2.hasMoreElements()) 
                            {
                                v_answer = st2.nextToken();
                                if(p_relation == 0)
                                    data.IncreasGSubjectCount(Integer.valueOf(v_answer).intValue());
                                else
                                if(p_relation == 1)
                                    data.IncreasGRelation1Count(Integer.valueOf(v_answer).intValue());
                                else
                                if(p_relation == 2)
                                    data.IncreasGRelation2Count(Integer.valueOf(v_answer).intValue());
                                else
                                if(p_relation == 3)
                                    data.IncreasGRelation3Count(Integer.valueOf(v_answer).intValue());
                            }
                        }
                    }

                }

            }

            for(int i = 0; i < p_list.size(); i++)
            {
                data = (DamunQuestionExampleData)p_list.get(i);
                data.ComputeRate();
                data.setComplexAnswer(complex);
                data.setR1ComplexAnswer(r1complex);
                data.setR2ComplexAnswer(r2complex);
                data.setR3ComplexAnswer(r3complex);
                data.setSubjectAnswer(subject);
                data.setR1Answer(r1subject);
                data.setR2Answer(r2subject);
                data.setR3Answer(r3subject);
            }

        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    public void ComputeCount2(ArrayList p_list, Vector p_answers1, Vector p_answers2, Vector p_answers3, Vector p_answers4)
        throws Exception
    {
        StringTokenizer st1 = null;
        StringTokenizer st2 = null;
        DamunQuestionExampleData data = null;
        Vector subject = new Vector();
        Vector r1subject = new Vector();
        Vector r2subject = new Vector();
        Vector r3subject = new Vector();
        Vector complex = new Vector();
        Vector r1complex = new Vector();
        Vector r2complex = new Vector();
        Vector r3complex = new Vector();
        String v_answers = "";
        String v_answer = "";
        int index = 0;
        Vector p_answers = null;
        int p_relation = 0;
        String v_subjectok = "(\uAD00\uCC30\uC790) ";
        try
        {
            for(int p = 0; p < 4; p++)
            {
                if(p == 0)
                {
                    p_answers = p_answers1;
                    p_relation = 0;
                } else
                if(p == 1)
                {
                    p_answers = p_answers2;
                    p_relation = 1;
                } else
                if(p == 2)
                {
                    p_answers = p_answers3;
                    p_relation = 2;
                } else
                if(p == 3)
                {
                    p_answers = p_answers4;
                    p_relation = 3;
                }
                for(int i = 0; i < p_answers.size(); i++)
                {
                    v_answers = (String)p_answers.get(i);
                    st1 = new StringTokenizer(v_answers, ",");
                    for(index = 0; st1.hasMoreElements() && index < p_list.size(); index++)
                    {
                        v_answer = st1.nextToken();
                        data = (DamunQuestionExampleData)p_list.get(index);
                        if(data.getSultype().equals("1"))
                        {
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                            if(p_relation == 0)
                                data.IncreasSubjectCount(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 1)
                                data.IncreasRelation1Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 2)
                                data.IncreasRelation2Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 3)
                                data.IncreasRelation3Count(Integer.valueOf(v_answer).intValue());
                        } else
                        if(data.getSultype().equals("2"))
                            for(st2 = new StringTokenizer(v_answer, ":"); st2.hasMoreElements();)
                            {
                                v_answer = st2.nextToken();
                                data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                                if(p_relation == 0)
                                    data.IncreasSubjectCount(Integer.valueOf(v_answer).intValue());
                                else
                                if(p_relation == 1)
                                    data.IncreasRelation1Count(Integer.valueOf(v_answer).intValue());
                                else
                                if(p_relation == 2)
                                    data.IncreasRelation2Count(Integer.valueOf(v_answer).intValue());
                                else
                                if(p_relation == 3)
                                    data.IncreasRelation3Count(Integer.valueOf(v_answer).intValue());
                            }

                        else
                        if(data.getSultype().equals("3"))
                        {
                            if(p_relation == 0)
                            {
                                v_answer = "(\uB300\uC0C1\uC790) " + v_answer;
                                subject.add(v_answer);
                            } else
                            if(p_relation == 1)
                            {
                                v_answer = "(\uAD00\uCC30\uC790) " + v_answer;
                                r1subject.add(v_answer);
                            } else
                            if(p_relation == 2)
                            {
                                v_answer = "(\uAD00\uCC30\uC790) " + v_answer;
                                r2subject.add(v_answer);
                            } else
                            if(p_relation == 3)
                            {
                                v_answer = "(\uAD00\uCC30\uC790) " + v_answer;
                                r3subject.add(v_answer);
                            }
                        } else
                        if(data.getSultype().equals("4"))
                        {
                            st2 = new StringTokenizer(v_answer, ":");
                            v_answer = st2.nextToken();
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                            if(p_relation == 0)
                                data.IncreasSubjectCount(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 1)
                                data.IncreasRelation1Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 2)
                                data.IncreasRelation2Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 3)
                                data.IncreasRelation3Count(Integer.valueOf(v_answer).intValue());
                            while(st2.hasMoreElements()) 
                            {
                                v_answer = st2.nextToken();
                                if(p_relation == 0)
                                {
                                    v_answer = "(\uB300\uC0C1\uC790) " + v_answer;
                                    complex.add(v_answer);
                                } else
                                if(p_relation == 1)
                                {
                                    v_answer = "(\uAD00\uCC30\uC790) " + v_answer;
                                    r1complex.add(v_answer);
                                } else
                                if(p_relation == 2)
                                {
                                    v_answer = "(\uAD00\uCC30\uC790) " + v_answer;
                                    r2complex.add(v_answer);
                                } else
                                if(p_relation == 3)
                                {
                                    v_answer = "(\uAD00\uCC30\uC790) " + v_answer;
                                    r3complex.add(v_answer);
                                }
                            }
                        } else
                        if(data.getSultype().equals("5"))
                        {
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                            if(p_relation == 0)
                                data.IncreasSubjectCount(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 1)
                                data.IncreasRelation1Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 2)
                                data.IncreasRelation2Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 3)
                                data.IncreasRelation3Count(Integer.valueOf(v_answer).intValue());
                        } else
                        if(data.getSultype().equals("6"))
                        {
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                            if(p_relation == 0)
                                data.IncreasSubjectCount(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 1)
                                data.IncreasRelation1Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 2)
                                data.IncreasRelation2Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 3)
                                data.IncreasRelation3Count(Integer.valueOf(v_answer).intValue());
                        } else
                        if(data.getSultype().equals("7"))
                        {
                            st2 = new StringTokenizer(v_answer, ":");
                            v_answer = st2.nextToken();
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                            if(p_relation == 0)
                                data.IncreasSubjectCount(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 1)
                                data.IncreasRelation1Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 2)
                                data.IncreasRelation2Count(Integer.valueOf(v_answer).intValue());
                            else
                            if(p_relation == 3)
                                data.IncreasRelation3Count(Integer.valueOf(v_answer).intValue());
                            while(st2.hasMoreElements()) 
                            {
                                v_answer = st2.nextToken();
                                if(p_relation == 0)
                                    data.IncreasGSubjectCount(Integer.valueOf(v_answer).intValue());
                                else
                                if(p_relation == 1)
                                    data.IncreasGRelation1Count(Integer.valueOf(v_answer).intValue());
                                else
                                if(p_relation == 2)
                                    data.IncreasGRelation2Count(Integer.valueOf(v_answer).intValue());
                                else
                                if(p_relation == 3)
                                    data.IncreasGRelation3Count(Integer.valueOf(v_answer).intValue());
                            }
                        }
                    }

                }

            }

            for(int i = 0; i < p_list.size(); i++)
            {
                data = (DamunQuestionExampleData)p_list.get(i);
                data.ComputeRate();
                data.setComplexAnswer(complex);
                data.setR1ComplexAnswer(r1complex);
                data.setR2ComplexAnswer(r2complex);
                data.setR3ComplexAnswer(r3complex);
                data.setSubjectAnswer(subject);
                data.setR1Answer(r1subject);
                data.setR2Answer(r2subject);
                data.setR3Answer(r3subject);
            }

        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }

    public Vector getDamunAnswers(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, int p_damunpapernum, String p_subjseq, 
            String p_company, String p_jikwi, String p_jikun, String p_workplc)
        throws Exception
    {
        Vector v_answers;
        ListSet ls = null;
        String sql = "";
        v_answers = new Vector();
        String v_comp = "";
        DataBox dbox = null;
        try
        {
            sql = "  select b.fanswers ";
            sql = sql + "    from  ";
            sql = sql + "         tz_damuneach    b, ";
            sql = sql + "         tz_member     d  ";
            sql = sql + "   where ";
            sql = sql + "      b.userid  = d.userid  ";
            if(!p_grcode.equals("ALL"))
                sql = sql + " and b.grcode  = " + SQLString.Format(p_grcode);
            if(!p_gyear.equals("ALL"))
                sql = sql + " and b.year   = " + SQLString.Format(p_gyear);
            sql = sql + "     and b.subj    = " + SQLString.Format(p_subj);
            if(!p_subjseq.equals("ALL"))
                sql = sql + "     and b.subjseq = " + SQLString.Format(p_subjseq);
            sql = sql + "     and b.damunpapernum = " + SQLString.Format(p_damunpapernum);
            if(!p_company.equals("ALL"))
                sql = sql + "     and substr(d.comp, 1, 4) = " + SQLString.Format(StringManager.substring(p_company, 0, 4));
            if(!p_jikwi.equals("ALL"))
                sql = sql + " and d.jikwi   = " + SQLString.Format(p_jikwi);
            if(!p_jikun.equals("ALL"))
                sql = sql + " and d.jikun   = " + SQLString.Format(p_jikun);
            if(!p_workplc.equals("ALL"))
                sql = sql + " and d.work_plc   = " + SQLString.Format(p_workplc);
            for(ls = connMgr.executeQuery(sql); ls.next(); v_answers.add(ls.getString("fanswers")));
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        return v_answers;
    }

    public Vector getDamunAnswers2(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, int p_damunpapernum, String p_subjseq, 
            String p_company, String p_jikwi, String p_jikun, String p_workplc)
        throws Exception
    {
        Vector v_answers;
        ListSet ls = null;
        String sql = "";
        v_answers = new Vector();
        String v_comp = "";
        DataBox dbox = null;
        try
        {
            sql = "  select b.sanswers  ";
            sql = sql + "    from ";
            sql = sql + "         tz_damuneach    b, tz_member     d  ";
            sql = sql + "   where   ";
            sql = sql + "      b.userid  = d.userid  ";
            if(!p_grcode.equals("ALL"))
                sql = sql + " and b.grcode  = " + SQLString.Format(p_grcode);
            if(!p_gyear.equals("ALL"))
                sql = sql + " and b.year   = " + SQLString.Format(p_gyear);
            sql = sql + "     and b.subj    = " + SQLString.Format(p_subj);
            if(!p_subjseq.equals("ALL"))
                sql = sql + "     and b.subjseq = " + SQLString.Format(p_subjseq);
            sql = sql + "     and b.damunpapernum = " + SQLString.Format(p_damunpapernum);
            if(!p_company.equals("ALL"))
                sql = sql + "     and substr(d.comp, 1, 4) = " + SQLString.Format(StringManager.substring(p_company, 0, 4));
            if(!p_jikwi.equals("ALL"))
                sql = sql + " and d.jikwi   = " + SQLString.Format(p_jikwi);
            if(!p_jikun.equals("ALL"))
                sql = sql + " and d.jikun   = " + SQLString.Format(p_jikun);
            if(!p_workplc.equals("ALL"))
                sql = sql + " and d.work_plc   = " + SQLString.Format(p_workplc);
            for(ls = connMgr.executeQuery(sql); ls.next(); v_answers.add(ls.getString("sanswers")));
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        return v_answers;
    }

    public Vector getDamunOBAnswers(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, int p_damunpapernum, String p_relation, 
            String p_subjseq, String p_company, String p_jikwi, String p_jikun, String p_workplc)
        throws Exception
    {
        Vector v_answers;
        ListSet ls = null;
        String sql = "";
        v_answers = new Vector();
        String v_comp = "";
        DataBox dbox = null;
        try
        {
            sql = "  select b.fanswers ";
            sql = sql + "    from  ";
            sql = sql + "         tz_member     d,  ";
            sql = sql + "  (select p.fanswers, p.subj, p.year, p.subjseq, p.obuserid, p.grcode, q.relation ";
            sql = sql + "         from tz_damunobeach    p,  tz_damunobserver q";
            sql = sql + "   where p.subj    = q.subj  ";
            sql = sql + "     and p.year    = q.year  ";
            sql = sql + "     and p.subjseq = q.subjseq  ";
            sql = sql + "     and p.grcode = q.grcode  ";
            sql = sql + "     and p.damunpapernum = q.damunpapernum  ";
            sql = sql + "     and p.subjuserid  = q.subjuserid  ";
            sql = sql + "     and p.obuserid  = q.obuserid  ";
            if(!p_grcode.equals("ALL"))
                sql = sql + " and p.grcode  = " + SQLString.Format(p_grcode);
            if(!p_gyear.equals("ALL"))
                sql = sql + " and p.year   = " + SQLString.Format(p_gyear);
            sql = sql + "     and p.subj    = " + SQLString.Format(p_subj);
            if(!p_subjseq.equals("ALL"))
                sql = sql + "     and p.subjseq = " + SQLString.Format(p_subjseq);
            sql = sql + "     and p.damunpapernum = " + SQLString.Format(p_damunpapernum) + ") b";
            sql = sql + "     where ";
            sql = sql + "      b.obuserid  = d.userid  ";
            sql = sql + "     and b.relation = " + SQLString.Format(p_relation);
            if(!p_company.equals("ALL"))
                sql = sql + "     and substr(d.comp, 1, 4) = " + SQLString.Format(StringManager.substring(p_company, 0, 4));
            if(!p_jikwi.equals("ALL"))
                sql = sql + " and d.jikwi   = " + SQLString.Format(p_jikwi);
            if(!p_jikun.equals("ALL"))
                sql = sql + " and d.jikun   = " + SQLString.Format(p_jikun);
            if(!p_workplc.equals("ALL"))
                sql = sql + " and d.work_plc   = " + SQLString.Format(p_workplc);
            for(ls = connMgr.executeQuery(sql); ls.next(); v_answers.add(ls.getString("fanswers")));
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        return v_answers;
    }

    public Vector getDamunOBAnswers2(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, int p_damunpapernum, String p_relation, 
            String p_subjseq, String p_company, String p_jikwi, String p_jikun, String p_workplc)
        throws Exception
    {
        Vector v_answers;
        ListSet ls = null;
        String sql = "";
        v_answers = new Vector();
        String v_comp = "";
        DataBox dbox = null;
        try
        {
            sql = "  select b.sanswers ";
            sql = sql + "    from  ";
            sql = sql + "    tz_member     d,  ";
            sql = sql + "    (select p.sanswers, p.subj, p.year, p.subjseq, p.obuserid, p.grcode, q.relation ";
            sql = sql + "         from tz_damunobeach    p,  tz_damunobserver q";
            sql = sql + "     where p.subj    = q.subj  ";
            sql = sql + "     and p.year    = q.year  ";
            sql = sql + "     and p.subjseq = q.subjseq  ";
            sql = sql + "     and p.grcode = q.grcode  ";
            sql = sql + "     and p.damunpapernum = q.damunpapernum  ";
            sql = sql + "     and p.subjuserid  = q.subjuserid  ";
            sql = sql + "     and p.obuserid  = q.obuserid  ";
            if(!p_grcode.equals("ALL"))
                sql = sql + " and p.grcode  = " + SQLString.Format(p_grcode);
            if(!p_gyear.equals("ALL"))
                sql = sql + " and p.year   = " + SQLString.Format(p_gyear);
            sql = sql + "     and p.subj    = " + SQLString.Format(p_subj);
            if(!p_subjseq.equals("ALL"))
                sql = sql + "     and p.subjseq = " + SQLString.Format(p_subjseq);
            sql = sql + "     and p.damunpapernum = " + SQLString.Format(p_damunpapernum) + ") b";
            sql = sql + "     where ";
            sql = sql + "      b.obuserid  = d.userid  ";
            sql = sql + "     and b.relation = " + SQLString.Format(p_relation);
            if(!p_company.equals("ALL"))
                sql = sql + "     and substr(d.comp, 1, 4) = " + SQLString.Format(StringManager.substring(p_company, 0, 4));
            if(!p_jikwi.equals("ALL"))
                sql = sql + " and d.jikwi   = " + SQLString.Format(p_jikwi);
            if(!p_jikun.equals("ALL"))
                sql = sql + " and d.jikun   = " + SQLString.Format(p_jikun);
            if(!p_workplc.equals("ALL"))
                sql = sql + " and d.work_plc   = " + SQLString.Format(p_workplc);
            for(ls = connMgr.executeQuery(sql); ls.next();)
                if(!ls.getString("sanswers").equals(""))
                    v_answers.add(ls.getString("sanswers"));

        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        return v_answers;
    }

    public int getStudentCount(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, String p_subjseq, int p_damunpapernum, 
            String p_company, String p_jikwi, String p_jikun, String p_workplc)
        throws Exception
    {
        int v_count;
        ListSet ls = null;
        String sql = "";
        String v_comp = "";
        v_count = 0;
        try
        {
            sql = "  select count(b.userid) cnt ";
            sql = sql + "    from ";
            sql = sql + "         tz_damunmember    b, ";
            sql = sql + "         tz_member     c  ";
            sql = sql + "   where  ";
            sql = sql + "     b.userid  = c.userid   ";
            sql = sql + "     and b.subj  = " + SQLString.Format(p_subj);
            sql = sql + " and b.damunpapernum   = " + SQLString.Format(p_damunpapernum);
            if(!p_grcode.equals("ALL"))
                sql = sql + " and b.grcode  = " + SQLString.Format(p_grcode);
            if(!p_gyear.equals("ALL"))
                sql = sql + " and b.year   = " + SQLString.Format(p_gyear);
            if(!p_subjseq.equals("ALL"))
                sql = sql + " and b.subjseq   = " + SQLString.Format(p_subjseq);
            if(!p_grseq.equals("ALL") && !p_company.equals("ALL"))
                sql = sql + "     and substr(c.comp, 1, 4) = " + SQLString.Format(StringManager.substring(p_company, 0, 4));
            if(!p_jikwi.equals("ALL"))
                sql = sql + " and c.jikwi   = " + SQLString.Format(p_jikwi);
            if(!p_jikun.equals("ALL"))
                sql = sql + " and c.jikun   = " + SQLString.Format(p_jikun);
            if(!p_workplc.equals("ALL"))
                sql = sql + " and c.work_plc   = " + SQLString.Format(p_workplc);
            for(ls = connMgr.executeQuery(sql); ls.next();)
                v_count = ls.getInt("cnt");

        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        return v_count;
    }

    public int getStudentOBCount(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, String p_subjseq, int p_damunpapernum, 
            String p_company, String p_jikwi, String p_jikun, String p_workplc)
        throws Exception
    {
        int v_count;
        ListSet ls = null;
        String sql = "";
        String v_comp = "";
        v_count = 0;
        try
        {
            sql = "  select count(b.obuserid) cnt ";
            sql = sql + "    from ";
            sql = sql + "         tz_damunobserver    b, ";
            sql = sql + "         tz_member     c  ";
            sql = sql + "   where ";
            sql = sql + "     b.obuserid  = c.userid   ";
            sql = sql + "     and b.subj  = " + SQLString.Format(p_subj);
            sql = sql + " and b.damunpapernum   = " + SQLString.Format(p_damunpapernum);
            if(!p_grcode.equals("ALL"))
                sql = sql + " and b.grcode  = " + SQLString.Format(p_grcode);
            if(!p_gyear.equals("ALL"))
                sql = sql + " and b.year   = " + SQLString.Format(p_gyear);
            if(!p_subjseq.equals("ALL"))
                sql = sql + " and b.subjseq   = " + SQLString.Format(p_subjseq);
            if(!p_grseq.equals("ALL") && !p_company.equals("ALL"))
                sql = sql + "     and substr(c.comp, 1, 4) = " + SQLString.Format(StringManager.substring(p_company, 0, 4));
            if(!p_jikwi.equals("ALL"))
                sql = sql + " and c.jikwi   = " + SQLString.Format(p_jikwi);
            if(!p_jikun.equals("ALL"))
                sql = sql + " and c.jikun   = " + SQLString.Format(p_jikun);
            if(!p_workplc.equals("ALL"))
                sql = sql + " and c.work_plc   = " + SQLString.Format(p_workplc);
            for(ls = connMgr.executeQuery(sql); ls.next();)
                v_count = ls.getInt("cnt");

        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
        }
        return v_count;
    }

    public int getPapernumSeq(String p_subj, String p_grcode)
        throws Exception
    {
        Hashtable maxdata = new Hashtable();
        maxdata.put("seqcolumn", "damunpapernum");
        maxdata.put("seqtable", "tz_damunpaper");
        maxdata.put("paramcnt", "2");
        maxdata.put("param0", "subj");
        maxdata.put("param1", "grcode");
        maxdata.put("subj", SQLString.Format(p_subj));
        maxdata.put("grcode", SQLString.Format(p_grcode));
        return SelectionUtil.getSeq(maxdata);
    }

    public ArrayList selectDamunDetailResultExcelList(RequestBox box)
        throws Exception
    {
        ArrayList list;
        DBConnectionManager connMgr = null;
        list = null;
        ArrayList list2 = null;
        ListSet ls = null;
        ListSet ls2 = null;
        ListSet ls3 = null;
        DataBox dbox = null;
        DataBox dbox2 = null;
        DataBox dbox3 = null;
        String sql = "";
        String sql2 = "";
        try
        {
            String ss_grcode = box.getString("s_grcode");
            String ss_subjcourse = box.getString("s_subjcourse");
            String ss_year = box.getString("s_gyear");
            String ss_subjseq = box.getString("s_subjseq");
            String v_action = box.getString("p_action");
            int v_damunpapernum = box.getInt("s_damunpapernum");
            String v_mailgubun = box.getString("s_mailgubun");
            String v_subjusernm = "";
            list = new ArrayList();
            if(v_action.equals("go"))
            {
                connMgr = new DBConnectionManager();
                sql = "select a.subjuserid, a.obuserid, '0' relation,  b.comp  asgn,  dbo.get_compnm(b.comp,2,4) asgnnm, b.divinam,";
                sql = sql + "\t   \t  b.jikwi,  dbo.get_jikwinm(b.jikwi, b.comp) jikwinm, ";
                sql = sql + "\t   \t  b.cono,   b.name ";
                sql = sql + "\t   \t  from ";
                sql = sql + " ( select userid subjuserid, userid obuserid, subj, grcode, year, subjseq, damunpapernum ";
                sql = sql + "  from tz_damunmember  ";
                sql = sql + "   where subj    = " + SQLString.Format(ss_subjcourse);
                sql = sql + "   and grcode    = " + SQLString.Format(ss_grcode);
                sql = sql + "   and year    = " + SQLString.Format(ss_year);
                sql = sql + "   and subjseq    = " + SQLString.Format(ss_subjseq);
                sql = sql + "   and damunpapernum    = " + SQLString.Format(v_damunpapernum) + " ) a, ";
                sql = sql + "         tz_member  b  ";
                sql = sql + "         where a.subjuserid = b.userid  ";
                sql = sql + " order by a.subjuserid ";
                ls = connMgr.executeQuery(sql);
                Vector damunnum = new Vector();
                for(; ls.next(); ls2.close())
                {
                    dbox = ls.getDataBox();
                    dbox.put("d_relation", "0");
                    v_subjusernm = dbox.getString("d_name");
                    dbox.put("d_subjusernm", v_subjusernm);
                    dbox.put("d_subjasgmnm", dbox.getString("d_asgnnm"));
                    sql2 = "select userid, damunnums, fanswers, sanswers";
                    sql2 = sql2 + "  from tz_damuneach  ";
                    sql2 = sql2 + "   where subj    = " + SQLString.Format(ss_subjcourse);
                    sql2 = sql2 + "   and grcode    = " + SQLString.Format(ss_grcode);
                    sql2 = sql2 + "   and year    = " + SQLString.Format(ss_year);
                    sql2 = sql2 + "   and subjseq    = " + SQLString.Format(ss_subjseq);
                    sql2 = sql2 + "   and damunpapernum    = " + SQLString.Format(v_damunpapernum);
                    sql2 = sql2 + "   and userid    = " + SQLString.Format(dbox.getString("d_subjuserid"));
                    ls2 = connMgr.executeQuery(sql2);
                    if(ls2.next())
                    {
                        dbox2 = ls2.getDataBox();
                        dbox.put("d_fanwsers", ls2.getString("fanswers"));
                        dbox.put("d_sanwsers", ls2.getString("sanswers"));
                        for(StringTokenizer st = new StringTokenizer(ls2.getString("damunnums"), ","); st.hasMoreElements(); damunnum.addElement(st.nextToken()));
                        String fselpoint = "";
                        String sselpoint = "";
                        StringTokenizer f_st = new StringTokenizer(ls2.getString("fanswers"), ",");
                        for(int i = 0; f_st.hasMoreElements(); i++)
                        {
                            String token = f_st.nextToken();
                            if(token.equals(""))
                                break;
                            fselpoint = fselpoint + getSelpoints(connMgr, ss_subjcourse, ss_grcode, (String)damunnum.elementAt(i), token) + ",";
                        }

                        dbox.put("d_fselpoint", fselpoint);
                        StringTokenizer s_st = new StringTokenizer(ls2.getString("sanswers"), ",");
                        for(int j = 0; s_st.hasMoreElements(); j++)
                        {
                            String token = s_st.nextToken();
                            if(token.equals(""))
                                break;
                            sselpoint = sselpoint + getSelpoints(connMgr, ss_subjcourse, ss_grcode, (String)damunnum.elementAt(j), token) + ",";
                        }

                        dbox.put("d_sselpoint", sselpoint);
                    }
                    ls2.close();
                    list.add(dbox);
                    sql2 = "select a.subjuserid,  a.obuserid, a.relation, b.comp  asgn,  dbo.get_compnm(b.comp,2,4)       asgnnm, b.divinam,";
                    sql2 = sql2 + "\t   \t  b.jikwi,       dbo.get_jikwinm(b.jikwi, b.comp) jikwinm, ";
                    sql2 = sql2 + "\t   \t  b.cono,     b.name ";
                    sql2 = sql2 + "\t   \t  from ";
                    sql2 = sql2 + " ( select subjuserid, obuserid,  relation ";
                    sql2 = sql2 + "  from tz_damunobserver  ";
                    sql2 = sql2 + "   where subj    = " + SQLString.Format(ss_subjcourse);
                    sql2 = sql2 + "   and grcode    = " + SQLString.Format(ss_grcode);
                    sql2 = sql2 + "   and year    = " + SQLString.Format(ss_year);
                    sql2 = sql2 + "   and subjseq    = " + SQLString.Format(ss_subjseq);
                    sql2 = sql2 + "   and damunpapernum    = " + SQLString.Format(v_damunpapernum);
                    sql2 = sql2 + "   and subjuserid    = " + SQLString.Format(dbox.getString("d_subjuserid")) + " ) a, ";
                    sql2 = sql2 + "         tz_member  b  ";
                    sql2 = sql2 + "         where a.obuserid = b.userid  ";
                    sql2 = sql2 + " order by a.obuserid ";
                    for(ls2 = connMgr.executeQuery(sql2); ls2.next(); list.add(dbox2))
                    {
                        dbox2 = ls2.getDataBox();
                        dbox2.put("d_subjusernm", v_subjusernm);
                        dbox2.put("d_obusernm", dbox.getString("d_name"));
                        dbox2.put("d_subjasgmnm", dbox.getString("d_asgnnm"));
                        sql2 = "select subjuserid, obuserid, damunnums, fanswers, sanswers";
                        sql2 = sql2 + "  from tz_damunobeach  ";
                        sql2 = sql2 + "   where subj    = " + SQLString.Format(ss_subjcourse);
                        sql2 = sql2 + "   and grcode    = " + SQLString.Format(ss_grcode);
                        sql2 = sql2 + "   and year    = " + SQLString.Format(ss_year);
                        sql2 = sql2 + "   and subjseq    = " + SQLString.Format(ss_subjseq);
                        sql2 = sql2 + "   and damunpapernum    = " + SQLString.Format(v_damunpapernum);
                        sql2 = sql2 + "   and subjuserid    = " + SQLString.Format(dbox.getString("d_subjuserid"));
                        sql2 = sql2 + "   and obuserid    = " + SQLString.Format(dbox2.getString("d_obuserid"));
                        ls3 = connMgr.executeQuery(sql2);
                        if(ls3.next())
                        {
                            dbox3 = ls3.getDataBox();
                            dbox2.put("d_fanwsers", ls3.getString("fanswers"));
                            dbox2.put("d_sanwsers", ls3.getString("sanswers"));
                            String fselpoint = "";
                            String sselpoint = "";
                            StringTokenizer f_st = new StringTokenizer(ls3.getString("fanswers"), ",");
                            for(int k = 0; f_st.hasMoreElements(); k++)
                            {
                                String token = f_st.nextToken();
                                if(token.equals(""))
                                    break;
                                fselpoint = fselpoint + getSelpoints(connMgr, ss_subjcourse, ss_grcode, (String)damunnum.elementAt(k), token) + ",";
                            }

                            dbox2.put("d_fselpoint", fselpoint);
                            StringTokenizer s_st = new StringTokenizer(ls3.getString("sanswers"), ",");
                            for(int l = 0; s_st.hasMoreElements(); l++)
                            {
                                String token = s_st.nextToken();
                                if(token.equals(""))
                                    break;
                                sselpoint = sselpoint + getSelpoints(connMgr, ss_subjcourse, ss_grcode, (String)damunnum.elementAt(l), token) + ",";
                            }

                            dbox2.put("d_sselpoint", sselpoint);
                        }
                        ls3.close();
                    }

                }

            }
        }
        catch(Exception ex)
        {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(ls != null)
                try
                {
                    ls.close();
                }
                catch(Exception exception1) { }
            if(ls2 != null)
                try
                {
                    ls2.close();
                }
                catch(Exception exception2) { }
            if(ls3 != null)
                try
                {
                    ls3.close();
                }
                catch(Exception exception3) { }
            if(connMgr != null)
                try
                {
                    connMgr.freeConnection();
                }
                catch(Exception exception4) { }
        }
        return list;
    }

    public String getSelpoints(DBConnectionManager connMgr, String p_subj, String p_grcode, String p_damunnum, String p_damunselnum)
        throws Exception
    {
        String v_result;
        ListSet ls1 = null;
        ListSet ls2 = null;
        String sql1 = "";
        String sql2 = "";
        v_result = "0";
        try
        {
            sql1 = "select damuntype from tz_damun where damunnum = " + p_damunnum;
            sql1 = sql1 + "   and subj   = " + SQLString.Format(p_subj);
            sql1 = sql1 + "   and grcode    = " + SQLString.Format(p_grcode);
            for(ls1 = connMgr.executeQuery(sql1); ls1.next();)
            {
                sql2 = "select selpoint";
                sql2 = sql2 + "  from tz_damunsel  ";
                sql2 = sql2 + "   where subj   = " + SQLString.Format(p_subj);
                sql2 = sql2 + "   and grcode    = " + SQLString.Format(p_grcode);
                sql2 = sql2 + "   and damunnum    = " + p_damunnum;
                sql2 = sql2 + "   and selnum    = " + p_damunselnum;
                if(ls1.getString("damuntype").equals("5") || ls1.getString("damuntype").equals("6") || ls1.getString("damuntype").equals("7"))
                {
                    for(ls2 = connMgr.executeQuery(sql2); ls2.next();)
                        v_result = (new StringBuffer(String.valueOf(ls2.getInt(1)))).toString();

                    if(ls2 != null)
                        try
                        {
                            ls2.close();
                        }
                        catch(Exception exception) { }
                }
            }

            if(ls1 != null)
                try
                {
                    ls1.close();
                }
                catch(Exception exception1) { }
        }
        catch(Exception ex)
        {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally
        {
            if(ls1 != null)
                try
                {
                    ls1.close();
                }
                catch(Exception exception3) { }
            if(ls2 != null)
                try
                {
                    ls2.close();
                }
                catch(Exception exception4) { }
        }
        return v_result;
    }
}
