//**********************************************************
//1. 제      목:
//2. 프로그램명: SulmunContentsResultBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-18
//7. 수      정:
//
//**********************************************************

package com.credu.research;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import java.text.*;
import com.credu.library.*;
import com.credu.system.*;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunContentsResultBean {


    public SulmunContentsResultBean() {}

///////////////////////////////////////////////////////////////////////////////
    /**
    컨텐츠설문 결과 리스트 
    @param box          receive from the form object and session
    @return ArrayList       
    */
    public ArrayList SelectObectResultList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ArrayList list = null;
        String v_action = box.getString("p_action");

        try {
            if (v_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list = getObjectSulmunResult(connMgr, box);
            } else {
                list = new ArrayList();
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

///////////////////////////////////////////////////////////////////////////////
    /**
    컨텐츠설문 결과 리스트 
    @param box          receive from the form object and session
    @return ArrayList       
    */
    public ArrayList getObjectSulmunResult(DBConnectionManager connMgr, RequestBox box) throws Exception {
        Vector    v_sulnums = null;
        ArrayList QuestionExampleDataList  = null;
        Vector    v_answers = null;

        String v_subj     = box.getStringDefault("p_subj",SulmunContentsBean.DEFAULT_SUBJ);
        String v_subjseq     = box.getStringDefault("p_subjseq","ALL");
        int v_sulpapernum = 0;

        String v_grseq     = box.getString("p_grseq");

        // 설문번호 선택조건
        String v_grcode = box.getStringDefault("p_grcode",SulmunContentsBean.DEFAULT_GRCODE);
        String v_gyear  = box.getStringDefault("p_gyear", "ALL");


        // 응답자 선택조건
        String v_company   = box.getStringDefault("p_company", "ALL");
        String v_jikwi  = box.getStringDefault("p_jikwi", "ALL");
        String v_jikun  = box.getStringDefault("p_jikun","ALL");
        String v_workplc  = box.getStringDefault("p_workplc","ALL");

        int v_studentcount = 0;

        try {

            v_sulpapernum = getPapernumSeq("CONTENTS", "ALL") - 1;

            // 설문지번호에 해당하는 설문번호를 vector로 받아온다. 벡터(설문번호1,3,5 ....)
            v_sulnums = getSulnums(connMgr, "ALL", "CONTENTS", v_sulpapernum);

            // 설문번호에 해당하는 문제리스트를 만든다. 리스트((설문번호1, 보기1,2,3..))
            QuestionExampleDataList = getSelnums(connMgr, "ALL","CONTENTS", v_sulnums);

    //System.out.println(">>>>>>>>>>"+v_gyear);
    //System.out.println(">>>>>>>>>>"+v_grseq);
    //System.out.println(">>>>>>>>>>"+v_subj);
    //System.out.println(">>>>>>>>>>"+v_sulpapernum);
    //System.out.println(">>>>>>>>>>"+v_subjseq);    
            // 설문지 답변모음을 읽어온다. 벡터(답변1,3,2,3..)
             v_answers = getSulmunAnswers(connMgr, "ALL", v_gyear, v_grseq, v_subj, v_sulpapernum, v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);
    //System.out.println("미치것당.."+v_answers);
            // 리스트((설문번호1, 보기1,2,3..)) + 벡터(답변1,3,2,3..) 이용해서 응답자수 및 비율을 계산한다.
            ComputeCount(QuestionExampleDataList, v_answers);

            // 응답자수
            box.put("p_replycount", String.valueOf(v_answers.size()));

            // 수강자수
             v_studentcount = getStudentCount(connMgr, v_grcode, v_gyear, v_grseq, v_subj, v_subjseq, v_company, v_jikwi, v_jikun, v_workplc);

			box.put("p_studentcount", String.valueOf(v_studentcount));
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }

        return QuestionExampleDataList;
    }


    /**
    컨텐츠설문 설문지 번호 구하기 
    @param box          receive from the form object and session
    @return Vector       
    */
    public Vector getSulnums(DBConnectionManager connMgr, String p_grcode, String p_subj, int p_sulpapernum) throws Exception {
        ListSet ls = null;
        String sql  = "";
        Vector v_sulnums = new Vector();
        String v_tokens  = "";
        StringTokenizer st = null;

        try {
            sql = "select sulnums  ";
            sql+= "  from tz_sulpaper ";
//          sql+= " where grcode      = " + SQLString.Format(p_grcode);
            sql+= " where grcode      = " + SQLString.Format(p_grcode);
            sql+= "   and subj        = " + SQLString.Format(p_subj);
            sql+= "   and sulpapernum = " + SQLString.Format(p_sulpapernum);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_tokens = ls.getString("sulnums");
            }

            st = new StringTokenizer(v_tokens,SulmunContentsBean.SPLIT_COMMA);
            while (st.hasMoreElements()) {
                v_sulnums.add((String)st.nextToken());
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_sulnums;
    }



    /**
    컨텐츠설문 설문지 번호,보기  구하기 
    @param box          receive from the form object and session
    @return ArrayList       
    */
    public ArrayList getSelnums(DBConnectionManager connMgr, String p_grcode, String p_subj, Vector p_sulnums) throws Exception {
        Hashtable hash = new Hashtable();
        ArrayList list = new ArrayList();

        ListSet ls  = null;
        String sql  = "";
        SulmunQuestionExampleData data = null;
        SulmunExampleData exampledata  = null;
        int v_bef_sulnum = 0;

        String v_sulnums = "";
        for (int i=0; i < p_sulnums.size(); i++) {
            v_sulnums += (String)p_sulnums.get(i);
            if (i<p_sulnums.size()-1) {
                v_sulnums += ", ";
            }
        }
        if (v_sulnums.equals("")) v_sulnums = "-1";

        try {
            sql = "select a.subj,     a.sulnum, ";
            sql+="        a.distcode, c.codenm distcodenm, ";
            sql+= "       a.sultype,  d.codenm sultypenm, ";
            sql+= "       a.sultext,  b.selnum, b.seltext, b.selpoint ";
            sql+= "  from tz_sul     a, ";
            sql+= "       tz_sulsel  b, ";
            sql+= "       tz_code    c, ";
            sql+= "       tz_code    d  ";
			// 수정일 : 05.11.04 수정자 : 이나연 _(+)  수정
//            sql+= " where a.subj     = b.subj(+)    ";
//            sql+= "   and a.sulnum   = b.sulnum(+)  ";
            sql+= " where a.subj       =  b.subj(+)    ";
            sql+= "   and a.sulnum     =  b.sulnum(+)  ";
            sql+= "   and a.distcode   = c.code ";
            sql+= "   and a.sultype    = d.code ";
            sql+= "   and a.grcode     = " + SQLString.Format(p_grcode);
            sql+= "   and a.subj       = " + SQLString.Format(p_subj);
            sql+= "   and a.sulnum in (" + v_sulnums + ")";
            sql+= "   and c.gubun      = " + SQLString.Format(SulmunContentsBean.DIST_CODE);
            sql+= "   and d.gubun      = " + SQLString.Format(SulmunContentsBean.SUL_TYPE);
            sql+= "   and d.levels     =  1 ";
            sql+= " order by a.subj, a.sulnum, b.selnum ";

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
                if (v_bef_sulnum != data.getSulnum())  {
                    hash.put(String.valueOf(data.getSulnum()), data);
                    v_bef_sulnum = data.getSulnum();
                }
            }
            data = null;
            for (int i=0; i < p_sulnums.size(); i++) {
                data = (SulmunQuestionExampleData)hash.get((String)p_sulnums.get(i));
                if (data != null) {
                    list.add(data);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return list;
    }


    /**
    컨텐츠설문 결과 응답자율 구하기 
    @param box          receive from the form object and session
    @return void       
    */
    public void ComputeCount(ArrayList p_list, Vector p_answers) throws Exception {
        StringTokenizer st1 = null;
        StringTokenizer st2 = null;

        SulmunQuestionExampleData data = null;
		Vector subject = new Vector();
		Vector complex = new Vector();
        String v_answers = "";
        String v_answer  = "";
        int index=0;

        try {
            for (int i=0; i < p_answers.size(); i++) {
                v_answers = (String)p_answers.get(i);
                st1 = new StringTokenizer(v_answers,SulmunContentsBean.SPLIT_COMMA);
                index = 0;
                while (st1.hasMoreElements() && index < p_list.size()) {
                    v_answer = (String)st1.nextToken();
                    data = (SulmunQuestionExampleData)p_list.get(index);

                    if (data.getSultype().equals(SulmunContentsBean.OBJECT_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    } else if (data.getSultype().equals(SulmunContentsBean.MULTI_QUESTION)) {
                        st2 = new StringTokenizer(v_answer,SulmunContentsBean.SPLIT_COLON);
                        while (st2.hasMoreElements()) {
                            v_answer = (String)st2.nextToken();
                            data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                        }
                    } else if (data.getSultype().equals(SulmunContentsBean.SUBJECT_QUESTION)) {
                        subject.add(v_answer);
                    } else if (data.getSultype().equals(SulmunContentsBean.COMPLEX_QUESTION)) {
                        st2 = new StringTokenizer(v_answer,SulmunContentsBean.SPLIT_COLON);
                        v_answer = (String)st2.nextToken();
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                        while (st2.hasMoreElements()) {
                            v_answer = (String)st2.nextToken();
                            complex.add(v_answer);
                        }
                    } else if (data.getSultype().equals(SulmunContentsBean.FSCALE_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    } else if (data.getSultype().equals(SulmunContentsBean.SSCALE_QUESTION)) {
                        data.IncreasReplyCount(Integer.valueOf(v_answer).intValue());
                    } 
                    index++;
                }
            }
            // 응답비율을 계산한다. 리스트((설문번호1, 보기1,2,3..))
            for (int i=0; i < p_list.size(); i++) {
                data = (SulmunQuestionExampleData)p_list.get(i);
	   				data.ComputeRate();
					data.setComplexAnswer(complex);
					data.setSubjectAnswer(subject);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
    }


    /**
    컨텐츠설문 결과 응답 답변 구하기 
    @param box          receive from the form object and session
    @return Vector       
    */
    public Vector getSulmunAnswers(DBConnectionManager connMgr, String p_grcode, String p_gyear,
                                    String p_grseq,    String p_subj, int p_sulpapernum, String p_subjseq,
                                    String p_company,  String p_jikwi,     String p_jikun, String p_workplc)  throws Exception {
        ListSet ls = null;
        String sql  = "";
        Vector v_answers = new Vector();
        String v_comp = "";

        try {
            sql = "  select c.answers ";
            sql+= "     from    tz_suleach    c, ";
            sql+= "         tz_member     d  ";
            sql+= "     where c.userid  = d.userid  ";
                sql+= " and c.grcode  = " + SQLString.Format(p_grcode);
            if (!p_gyear.equals("ALL"))
                sql+= " and c.year   = " + SQLString.Format(p_gyear);
//            if (!p_grseq.equals("ALL"))
//                sql+= " and a.grseq   = " + SQLString.Format(p_grseq);
//            sql+= "     and c.f_gubun = " + SQLString.Format(p_gubun);
            sql+= "     and c.subj    = " + SQLString.Format(p_subj);
            if (!p_subjseq.equals("ALL"))
            sql+= "     and c.subjseq = " + SQLString.Format(p_subjseq);
            sql+= "     and c.sulpapernum = " + SQLString.Format(p_sulpapernum);
  
            if (!p_company.equals("ALL"))
			sql+= "     and d.comp = " + SQLString.Format(p_company);
            if (!p_jikwi.equals("ALL"))
                sql+= " and d.jikwi   = " + SQLString.Format(p_jikwi);
            if (!p_jikun.equals("ALL"))
                sql+= " and d.jikun   = " + SQLString.Format(p_jikun);
            if (!p_workplc.equals("ALL"))
                sql+= " and d.work_plc   = " + SQLString.Format(p_workplc);
//Log.info.println("레포트3>>"+sql);
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_answers.add(ls.getString("answers"));
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_answers;
    }



    /**
    컨텐츠설문 결과 학생수 구하기 
    @param box          receive from the form object and session
    @return int       
    */
    public int  getStudentCount(DBConnectionManager connMgr, String p_grcode, String p_gyear, String p_grseq, String p_subj, String p_subjseq, 
                                String p_company,  String p_jikwi,     String p_jikun, String p_workplc)  throws Exception {
        ListSet ls = null;
        String sql  = "";
        String v_comp = "";
        int v_count = 0;

        try {
            sql = "  select count(*) cnt ";
            sql+= "    from tz_subjseq    a, ";
            sql+= "         tz_student    b, ";
            sql+= "         tz_member     c  ";
            sql+= "   where a.subj    = b.subj  ";
            sql+= "     and a.year    = b.year  ";
            sql+= "     and a.subjseq = b.subjseq  ";
            sql+= "     and b.userid  = c.userid   ";
            sql+= "     and a.subj  = " + SQLString.Format(p_subj);
            if (!p_grcode.equals("ALL"))
                sql+= " and a.grcode  = " + SQLString.Format(p_grcode);
            if (!p_gyear.equals("ALL"))
                sql+= " and a.gyear   = " + SQLString.Format(p_gyear);
            if (!p_gyear.equals("ALL"))
                sql+= " and a.subjseq   = " + SQLString.Format(p_subjseq);
            if (!p_grseq.equals("ALL"))
//                sql+= " and a.grseq   = " + SQLString.Format(p_grseq);

            if (!p_company.equals("ALL"))
            sql+= "     and c.comp = " + SQLString.Format(p_company);
            if (!p_jikwi.equals("ALL"))
                sql+= " and c.jikwi   = " + SQLString.Format(p_jikwi);
            if (!p_jikun.equals("ALL"))
                sql+= " and c.jikun   = " + SQLString.Format(p_jikun);
            if (!p_workplc.equals("ALL"))
                sql+= " and c.work_plc   = " + SQLString.Format(p_workplc);

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                v_count = ls.getInt("cnt");
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex);
            throw new Exception(ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
        }
        return v_count;
    }


    /**
    일련번호 구하기 
    @param box          receive from the form object and session
    @return int       
    */
    public int getPapernumSeq(String p_subj, String p_grcode) throws Exception {
        Hashtable maxdata = new Hashtable();
        maxdata.put("seqcolumn","sulpapernum");
        maxdata.put("seqtable","tz_sulpaper");
        maxdata.put("paramcnt","2");
        maxdata.put("param0","subj");
        maxdata.put("param1","grcode");
        maxdata.put("subj",   SQLString.Format(p_subj));
        maxdata.put("grcode",   SQLString.Format(p_grcode));

        return SelectionUtil.getSeq(maxdata);
    }



    public ArrayList selectBoardList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList list = null;
        String sql = "";
        DataBox dbox = null;
		Vector v_answers = null;

        int v_pageno = box.getInt("p_pageno");
		if(v_pageno==0) v_pageno=1;
        int v_row = box.getInt("p_row");
		if (v_row==0) v_row=10;

        String  v_searchtext = box.getString("s_searchtext");

        String v_grseq     = box.getStringDefault("p_grseq","0001");
        String v_gyear  = box.getStringDefault("p_gyear", "ALL");

        // 응답자 선택조건
        String v_company   = box.getStringDefault("p_company","ALL");
        String v_jikwi  = box.getStringDefault("p_jikwi","ALL");
        String v_jikun  = box.getStringDefault("p_jikun","ALL");
        String v_workplc  = box.getStringDefault("p_workplc","ALL");

        String  ss_upperclass  = box.getStringDefault("s_upperclass","ALL");
        String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");

        try {
            connMgr = new DBConnectionManager();

            list = new ArrayList();

	    sql = "select b.subj,        ";
            sql+="        a.sulpapernum, a.sulpapernm,  ";
            sql+= "       b.subjnm , substr(b.indate,1,4) indate, '0001' as subjseq       ";
            sql+= "  from tz_sulpaper  a, ";
            sql+= "       tz_subj   b  ";
            sql+= "   where a.subj  = 'CONTENTS' ";
            sql+= "   and a.grcode    = 'ALL' and b.isonoff = 'ON' and b.isuse='Y' and b.isapproval='Y' ";

             if ( !v_searchtext.equals("")) {                //    검색어가 있으면
                //v_pageno = 1;   //      검색할 경우 첫번째 페이지가 로딩된다

                    sql += " and b.subjnm like " + StringManager.makeSQL("%" + v_searchtext + "%");
            }
            if (!ss_upperclass.equals("ALL")) {
                sql+= "   and b.upperclass   = " + SQLString.Format(ss_upperclass);
            }
            if (!ss_middleclass.equals("ALL")) {
                sql+= "   and b.middleclass   = " + SQLString.Format(ss_middleclass);
            }

            sql+= " order by b.indate desc ";
//System.out.println(sql);
            ls = connMgr.executeQuery(sql);
            ls.setPageSize(v_row);                       //  페이지당 row 갯수를 세팅한다
            ls.setCurrentPage(v_pageno);               //     현재페이지번호를 세팅한다.
            int totalpagecount = ls.getTotalPage();    //     전체 페이지 수를 반환한다
            int totalrowcount = ls.getTotalCount();    //     전체 row 수를 반환한다

            while (ls.next()) {
                dbox = ls.getDataBox();

				box.put("p_subj",dbox.getString("d_subj"));
				box.put("p_subjseq","ALL");
			    ArrayList list1 = this.getObjectSulmunResult(connMgr, box);

	    	    SulmunQuestionExampleData data    = null;
                SulmunExampleData         subdata = null;
                double v_total = 0;

                for (int i=0; i < list1.size(); i++) {
                   data = (SulmunQuestionExampleData)list1.get(i);

	               double d = 0; 
			       int person = 0;
			       double v_point = 0;
      
		           for (int j=1; j <= data.size(); j++) {
	                   subdata  = (SulmunExampleData)data.get(j); 
                       if (subdata != null) { 

					   d +=  (subdata.getReplycnt()) * subdata.getSelpoint();
					   person += subdata.getReplycnt();
				       }
                   }	
               
		           v_point = d / person;	  
               
		           v_total += v_point;
               }	
    
	           double v_average = v_total / list1.size();
	           
	           DecimalFormat  df = new DecimalFormat("0.0");
                   
			   //평점
			   if(v_average>0){
		//	   dbox.put("d_average", String.valueOf(v_average));
		            dbox.put("d_average", df.format(v_average));
			   }else{
			   dbox.put("d_average", "0.0");
			   }

                // 응답자수
                dbox.put("d_replycount", box.getString("p_replycount"));

                dbox.put("d_dispnum", new Integer(totalrowcount - ls.getRowNum() + 1));
                dbox.put("d_totalpage", new Integer(totalpagecount));
                dbox.put("d_rowcount", new Integer(v_row));
                list.add(dbox);
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }

 
    /**
    개인별 리스트
    @param box          receive from the form object and session
    @return ArrayList   
    */ 
    public ArrayList selectResultMemberList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet     ls = null;
        ArrayList list = null;
        String     sql = "";
        DataBox   dbox = null;

		//String v_grcode     = box.getString("p_grcode");
		
		String v_action       = box.getString("p_action");
        String v_subj       = box.getString("p_subj");
        String v_gyear      = box.getString("p_gyear");
        String v_subjseq    = box.getString("p_subjseq");

        
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList();
                              
            if(v_action.equals("go")){                                    
                sql = "select a.userid, a.ldate, b.name, get_compnm(b.comp,2,2) compnm, b.deptnam , b.jikwinm, a.answers, ";
				sql+= "       NVL(a.distcode1,0) distcode1, NVL(a.distcode2,0) distcode2, NVL(a.distcode3,0) distcode3  ";
                sql+= "  from tz_suleach a, tz_member b  ";
                sql+= " where a.userid=b.userid         ";
                sql+= "   and a.subj='"+v_subj+"' and a.grcode='ALL' and a.year='"+v_gyear+"' and a.subjseq='"+v_subjseq+"'";
    
                ls = connMgr.executeQuery(sql);
                while (ls.next()) {
                    dbox = ls.getDataBox();
                    list.add(dbox);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls != null) { try { ls.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list;
    }
}