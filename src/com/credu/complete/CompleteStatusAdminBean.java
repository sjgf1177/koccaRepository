//**********************************************************
//  1. ��      ��: COMPLETE STATUS ADMIN BEAN
//  2. ���α׷���: CompleteStatusAdminBean.java
//  3. ��      ��: ���� ��Ȳ ������ bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 8. 21
//  7. ��      ��: ������ 2008. 11. 14
//**********************************************************
package com.credu.complete;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.complete.*;
import com.credu.library.*;
import com.credu.common.*;
import com.credu.system.*;

public class CompleteStatusAdminBean {
    private ConfigSet config;
    private int row;

    public CompleteStatusAdminBean() {
        try{
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row"));  //�� ����� �������� row ���� �����Ѵ�
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
    ������ ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectCompleteMemberList(RequestBox box) throws Exception {
		PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
		DataBox	dbox		= null;
        String sql1         = "";
        String sql2         = "";
        String  v_Bcourse   = ""; //�����ڽ�
        String  v_course    = ""; //�����ڽ�
        String  v_Bcourseseq= ""; //�����ڽ�����
        String  v_courseseq = ""; //�����ڽ�����
        int     l           = 0;
        
		// ����¡
		int    v_pageno    = box.getInt("p_pageno");
		if (v_pageno == 0) v_pageno = 1;
		int    v_pagesize  = box.getInt("p_pagesize");

        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");       //�����׷�
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");        //�⵵
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");        //��������
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //�����з�
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //�����з�
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //�����з�
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");   //����&�ڽ�
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");      //���� ����
        String  ss_company  = box.getStringDefault("s_company","ALL");      //ȸ��
        String  ss_edustart = box.getString("s_edustart");     //����������
        String  ss_eduend   = box.getString("s_eduend");       //����������
        String  ss_selgubun = box.getString("s_selgubun");                  //������:JIKUN,���޺�:JIKUP,����κ�:GPM
        String  ss_seltext  = box.getStringDefault("s_seltext","ALL");      //�˻��з��� �˻�����
        String  ss_seldept  = box.getStringDefault("s_seldept","ALL");      //����κ� �μ� �˻�����
        String  ss_action   = box.getString("s_action");

        String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
        String  v_orderType     = box.getString("p_orderType");           		//������ ����

//		2005.11.15_�ϰ��� : TotalCount ���� ���� ����
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";
        String group_sql  = "";
        String order_sql  = "";

        ManagerAdminBean bean = null;
        String  v_sql_add   = "";
        String  v_userid    = box.getSession("userid");
        String  s_gadmin    = box.getSession("gadmin");

        try {
            if(ss_action.equals("go")){
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
                list2 = new ArrayList();

                //select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,isonoff,compnm,
                //jikwinm,userid,cono,name,edustart,eduend,tstep,avtstep,mtest,ftest,report,act,etc1,
                //score,isgraduated,email,ismailing,place
                head_sql = "select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,c.subjseqgr,A.serno, ";
				head_sql += "	C.isonoff, get_deptnm('',A.userid) compnm, get_compnm(B.comp,2,2) companynm, B.jikwinm, B.jikupnm,B.resno, ";
				head_sql += " 	B.userid,B.cono,B.name,C.edustart,C.eduend,A.tstep,A.avtstep, ";
                if(GetCodenm.get_config("score_disp").equals("WS")){    //����ġ����
					head_sql += " A.avreport report,A.avact act,A.avmtest mtest,A.avftest ftest,A.avetc1 etc1, A.avetc2 etc2, A.avhtest htest, ";
                }else{                                                  //����ġ������
					head_sql += " A.report report,A.act act,A.mtest mtest,A.ftest ftest,A.etc1 etc1, A.etc2 etc2, A.htest htest, ";
                }
				head_sql += " A.score,A.isgraduated,B.email,B.ismailing,C.place, B.MemberGubun, C.isbelongcourse, C.subjcnt ";
				body_sql += " from TZ_STOLD A,TZ_MEMBER B,VZ_SCSUBJSEQ C where c.isclosed = 'Y'  ";
                if (!ss_grcode.equals("ALL")) {
					body_sql += " and C.grcode = "+SQLString.Format(ss_grcode);
                }
                if (!ss_grseq.equals("ALL")) {
					body_sql += " and C.grseq = "+SQLString.Format(ss_grseq);
                }

                if (!ss_uclass.equals("ALL")) {
					body_sql += " and C.scupperclass = "+SQLString.Format(ss_uclass);
                }
                if (!ss_mclass.equals("ALL")) {
					body_sql += " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
                }
                if (!ss_lclass.equals("ALL")) {
					body_sql += " and C.sclowerclass = "+SQLString.Format(ss_lclass);
                }

                if (!ss_subjcourse.equals("ALL")) {
					body_sql += " and C.scsubj = "+SQLString.Format(ss_subjcourse);
                }
                if (!ss_subjseq.equals("ALL")) {
					body_sql += " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
                }
                if (!ss_company.equals("ALL")) {
					body_sql += " and B.comp like '"+GetCodenm.get_compval(ss_company)+"'";
                }

                if (!ss_edustart.equals("")){
					body_sql += " and C.edustart >= "+SQLString.Format(ss_edustart);
                }
                if (!ss_eduend.equals("")) {
					body_sql += " and C.eduend <= "+SQLString.Format(ss_eduend);
                }

                //���������ϰ� �������� ���õ��� ���� ��쿡�� �����⵵���� �˻���
                if (ss_edustart.equals("") && ss_eduend.equals("")) {
					body_sql += " and C.gyear = "+SQLString.Format(ss_gyear);
                }

				body_sql += " and A.userid=B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";

                if(v_orderColumn.equals("")) {
					order_sql += " order by C.course,C.cyear,C.courseseq,C.subj,C.year,C.subjseq,B.userid ";
				} else {
					order_sql += " order by C.course, " + v_orderColumn + v_orderType;
				}

				sql= head_sql+ body_sql+ order_sql;

				ls1 = connMgr.executeQuery(sql);

				count_sql= "select count(*) "+ body_sql;
				//int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

				// ������ : 05.11.09 ������ : �̳��� _ ���� > �����������̷� select ����
				//pstmt = connMgr.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				//ls1 = new ListSet(pstmt);

                //ls1.setPageSize(row);                       //�������� row ������ �����Ѵ�
				//ls1.setCurrentPage(v_pageno, total_row_count);               //������������ȣ�� �����Ѵ�.
                //int total_page_count = ls1.getTotalPage();  //��ü ������ ���� ��ȯ�Ѵ�
                //int total_row_count = ls1.getTotalCount();  //��ü row ���� ��ȯ�Ѵ�

                
    			//����¡
    			ls1.setPageSize(v_pagesize);             			//  �������� row ������ �����Ѵ�
    			ls1.setCurrentPage(v_pageno);				//     ������������ȣ�� �����Ѵ�.
    			int total_page_count = ls1.getTotalPage();	//     ��ü ������ ���� ��ȯ�Ѵ�
    			int total_row_count = ls1.getTotalCount();	//     ��ü row ���� ��ȯ�Ѵ�


                
                while (ls1.next()) {
					dbox = ls1.getDataBox();

		            dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
		            dbox.put("d_totalpage", new Integer(total_page_count));
		            dbox.put("d_rowcount", new Integer(row));
		            list1.add(dbox);
					/*
                    System.out.println(ls1.getString("compnm"));
                    data1=new CompleteStatusData();
                    data1.setGrseq(ls1.getString("grseq"));
                    data1.setCourse(ls1.getString("course"));
                    data1.setCyear(ls1.getString("cyear"));
                    data1.setCourseseq(ls1.getString("courseseq"));
                    data1.setCoursenm(ls1.getString("coursenm"));
                    data1.setSubj(ls1.getString("subj"));
                    data1.setYear(ls1.getString("year"));
                    data1.setSubjseq(ls1.getString("subjseq"));
                    data1.setSubjseqgr(ls1.getString("subjseqgr"));
                    data1.setSubjnm(ls1.getString("subjnm"));
                    data1.setIsonoff(ls1.getString("isonoff"));
                    data1.setCompnm(ls1.getString("compnm"));
                    data1.setCompanynm(ls1.getString("companynm"));
                    data1.setJikwinm(ls1.getString("jikwinm"));
                    data1.setJikupnm(ls1.getString("jikupnm"));
                    data1.setUserid(ls1.getString("userid"));
                    data1.setCono(ls1.getString("cono"));
                    data1.setName(ls1.getString("name"));
                    data1.setEdustart(ls1.getString("edustart"));
                    data1.setEduend(ls1.getString("eduend"));
                    data1.setTstep(ls1.getInt("tstep"));
                    data1.setAvtstep(ls1.getInt("avtstep"));
                    data1.setMtest(ls1.getInt("mtest"));
                    data1.setFtest(ls1.getInt("ftest"));
                    data1.setHtest(ls1.getInt("htest"));  // 2005.9.11 by������ (�� �̰� ������??)

					data1.setMembergubun(ls1.getString("MemberGubun"));

                    data1.setReport(ls1.getInt("report"));
                    data1.setAct(ls1.getInt("act"));
                    data1.setEtc1(ls1.getInt("etc1"));
                    data1.setScore(ls1.getInt("score"));
                    data1.setIsgraduated(ls1.getString("isgraduated"));
                    data1.setEmail(ls1.getString("email"));
                    data1.setPlace(ls1.getString("place"));
                    data1.setIsmailing(ls1.getString("ismailing"));
                    data1.setResno(ls1.getString("resno"));
                    data1.setSerno(ls1.getString("serno"));
                    data1.setDispnum(total_row_count - ls1.getRowNum() + 1);
                    data1.setTotalPageCount(total_page_count);
                    data1.setRowCount(row);
                    list1.add(data1);
                    */
                }
				/*
                for(int i=0;i < list1.size(); i++){
                    data2       =   (CompleteStatusData)list1.get(i);
                    v_course    =   data2.getCourse();
                    v_courseseq =   data2.getCourseseq();
                    if(!v_course.equals("000000") && !(v_Bcourse.equals(v_course) && v_Bcourseseq.equals(v_courseseq))){
                        sql2 = "select count(A.subj) cnt from VZ_SCSUBJSEQ A,TZ_STOLD B,TZ_MEMBER C ";
                        sql2+= "where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq ";
                        sql2+= "and B.userid=C.userid ";
                        if (!ss_grcode.equals("ALL")) {
                            sql2+= " and A.grcode = "+SQLString.Format(ss_grcode);
                        }
                        if (!ss_grseq.equals("ALL")) {
                            sql2+= " and A.grseq = "+SQLString.Format(ss_grseq);
                        }

                        if (!ss_uclass.equals("ALL")) {
                            sql2+= " and A.scupperclass = "+SQLString.Format(ss_uclass);
                        }
                        if (!ss_mclass.equals("ALL")) {
                            sql2+= " and A.scmiddleclass = "+SQLString.Format(ss_mclass);
                        }
                        if (!ss_lclass.equals("ALL")) {
                            sql2+= " and A.sclowerclass = "+SQLString.Format(ss_lclass);
                        }

                        if (!ss_subjcourse.equals("ALL")) {
                            sql2+= " and A.scsubj = "+SQLString.Format(ss_subjcourse);
                        }
                        if (!ss_subjseq.equals("ALL")) {
                            sql2+= " and A.scsubjseq = "+SQLString.Format(ss_subjseq);
                        }
                        if (!ss_company.equals("ALL")) {
                            sql2+= " and C.comp like '"+GetCodenm.get_compval(ss_company)+"'";
                        }
                        if (!ss_edustart.equals("ALL")){
                            sql2+= " and A.edustart >= "+SQLString.Format(ss_edustart);
                        }
                        if (!ss_eduend.equals("ALL")) {
                            sql2+= " and A.eduend <= "+SQLString.Format(ss_eduend);
                        }
                        //���������ϰ� �������� ���õ��� ���� ��쿡�� �����⵵���� �˻���
                        if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
                            sql2+= " and A.gyear = "+SQLString.Format(ss_gyear);
                        }
                        sql2+= "and A.course = "+SQLString.Format(v_course)+" and A.courseseq = "+SQLString.Format(v_courseseq);

                        ls2 = connMgr.executeQuery(sql2);

                        if(ls2.next()){
                            data2.setRowspan(ls2.getInt("cnt"));
                            data2.setIsnewcourse("Y");
                        }
                    }else{
                        data2.setRowspan(0);
                        data2.setIsnewcourse("N");
                    }
                    v_Bcourse   =   v_course;
                    v_Bcourseseq=   v_courseseq;
                    list2.add(data2);
                    if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
                }
                */
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }

    /**
    ������ ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectCompleteRosterList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
        String sql1         = "";
        String sql2         = "";
        CompleteStatusData data1=null;
        CompleteStatusData data2=null;
        String  v_Bcourse   = ""; //�����ڽ�
        String  v_course    = ""; //�����ڽ�
        String  v_Bcourseseq= ""; //�����ڽ�����
        String  v_courseseq = ""; //�����ڽ�����
        int v_pageno        = box.getInt("p_pageno");
        int     l           = 0;
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");           //�����׷�
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");            //�⵵
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");            //��������
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");       //�����з�
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");      //�����з�
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");       //�����з�
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");       //����&�ڽ�
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");          //���� ����
        String  ss_company  = box.getStringDefault("s_company","ALL");          //ȸ��
        String  ss_edustart = box.getStringDefault("s_edustart","ALL");         //����������
        String  ss_eduend   = box.getStringDefault("s_eduend","ALL");           //����������
        String  ss_selgubun = box.getString("s_selgubun");                      //������:JIKUN,���޺�:JIKUP,����κ�:GPM
        String  ss_seltext  = box.getStringDefault("s_seltext","ALL");          //�˻��з��� �˻�����
        String  ss_seldept  = box.getStringDefault("s_seldept","ALL");          //����κ� �μ� �˻�����
        String  ss_action   = box.getString("s_action");
        String  v_orderColumn= box.getString("p_orderColumn");                  //������ �÷���

        ManagerAdminBean bean = null;
        String  v_sql_add   = "";
        String  v_userid    = box.getSession("userid");
        String  s_gadmin    = box.getSession("gadmin");

//		2005.11.15_�ϰ��� : TotalCount ���� ���� ����
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";
        String group_sql  = "";
        String order_sql  = "";

        try {
            if(ss_action.equals("go")){
				PreparedStatement pstmt = null;
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
                list2 = new ArrayList();

                //select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,isonoff,compnm,
                //jikwinm,userid,cono,name,edustart,eduend,score,isgraduated,email,ismailing
				head_sql = " select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,";
				head_sql+= " C.isonoff,get_compnm(B.comp,3,5) compnm,get_jikwinm(B.jikwi,B.comp) jikwinm,get_jikupnm(B.jikup,B.comp) jikupnm,";
				head_sql+= " B.userid,B.cono,B.name,C.edustart,C.eduend,A.score,A.isgraduated,B.email,B.ismailing ";
				body_sql+= "from TZ_STOLD A,TZ_MEMBER B,VZ_SCSUBJSEQ C where A.isgraduated='Y' and  c.isclosed = 'Y' ";
                if (!ss_grcode.equals("ALL")) {
					body_sql+= " and C.grcode = "+SQLString.Format(ss_grcode);
                }
                if (!ss_grseq.equals("ALL")) {
					body_sql+= " and C.grseq = "+SQLString.Format(ss_grseq);
                }

                if (!ss_uclass.equals("ALL")) {
					body_sql+= " and C.scupperclass = "+SQLString.Format(ss_uclass);
                }
                if (!ss_mclass.equals("ALL")) {
					body_sql+= " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
                }
                if (!ss_lclass.equals("ALL")) {
					body_sql+= " and C.sclowerclass = "+SQLString.Format(ss_lclass);
                }

                if (!ss_subjcourse.equals("ALL")) {
					body_sql+= " and C.scsubj = "+SQLString.Format(ss_subjcourse);
                }
                if (!ss_subjseq.equals("ALL")) {
					body_sql+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
                }
                if (!ss_company.equals("ALL")) {
					body_sql+= " and B.comp like '"+GetCodenm.get_compval(ss_company)+"'";
                }
                if (!ss_edustart.equals("ALL")){
					body_sql+= " and C.edustart >= "+SQLString.Format(ss_edustart);
                }
                if (!ss_eduend.equals("ALL")) {
					body_sql+= " and C.eduend <= "+SQLString.Format(ss_eduend);
                }
                //���������ϰ� �������� ���õ��� ���� ��쿡�� �����⵵���� �˻���
                if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
					body_sql+= " and C.gyear = "+SQLString.Format(ss_gyear);
                }
/*
                // �μ����ϰ��
                if (s_gadmin.equals("K7")) {
                    bean = new ManagerAdminBean();
                    v_sql_add   = bean.getManagerDept(v_userid, s_gadmin);
                    if (!v_sql_add.equals("")) body_sql += " and B.comp in " + v_sql_add;       // �����μ��˻���������
                }

                if(ss_selgubun.equals("JIKUN") && !ss_seltext.equals("ALL"))      {  //������
					body_sql+= " and B.jikun = "+SQLString.Format(ss_seltext);
                }else if(ss_selgubun.equals("JIKWI") && !ss_seltext.equals("ALL")){  //������
					body_sql+= " and B.jikwi = "+SQLString.Format(ss_seltext);
                }else if(ss_selgubun.equals("GPM") && !ss_seltext.equals("ALL")){  //����κ�
					body_sql+= " and B.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seltext));
                    if(!ss_seldept.equals("ALL")){
						body_sql+= " and B.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seldept));
                     }
                }
                */
				body_sql+= " and A.userid=B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";
                if(!v_orderColumn.equals("")){
                    v_orderColumn = "B."+v_orderColumn;
					order_sql+= " order by C.course,C.cyear,C.courseseq,C.subj,C.year,C.subjseq,"+v_orderColumn;
                }else{
					order_sql+= " order by C.course,C.cyear,C.courseseq,C.subj,C.year,C.subjseq,B.userid ";
                }
/*
                ls1 = connMgr.executeQuery(sql1);

				pstmt = connMgr.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
*/
				sql= head_sql+ body_sql+ order_sql;

				ls1 = connMgr.executeQuery(sql);

				count_sql= "select count(*) "+ body_sql;
				int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

                ls1.setPageSize(row);                       //�������� row ������ �����Ѵ�
                ls1.setCurrentPage(v_pageno, total_row_count);               //������������ȣ�� �����Ѵ�.
                int total_page_count = ls1.getTotalPage();  //��ü ������ ���� ��ȯ�Ѵ�
                //int total_row_count = ls1.getTotalCount();  //��ü row ���� ��ȯ�Ѵ�

                while (ls1.next()) {
                    data1=new CompleteStatusData();
                    data1.setGrseq(ls1.getString("grseq"));
                    data1.setCourse(ls1.getString("course"));
                    data1.setCyear(ls1.getString("cyear"));
                    data1.setCourseseq(ls1.getString("courseseq"));
                    data1.setCoursenm(ls1.getString("coursenm"));
                    data1.setSubj(ls1.getString("subj"));
                    data1.setYear(ls1.getString("year"));
                    data1.setSubjseq(ls1.getString("subjseq"));
                    data1.setSubjnm(ls1.getString("subjnm"));
                    data1.setIsonoff(ls1.getString("isonoff"));
                    data1.setCompnm(ls1.getString("compnm"));
                    data1.setJikwinm(ls1.getString("jikwinm"));
                    data1.setJikupnm(ls1.getString("jikupnm"));
                    data1.setUserid(ls1.getString("userid"));
                    data1.setCono(ls1.getString("cono"));
                    data1.setName(ls1.getString("name"));
                    data1.setEdustart(ls1.getString("edustart"));
                    data1.setEduend(ls1.getString("eduend"));
                    data1.setScore(ls1.getInt("score"));
                    data1.setIsgraduated(ls1.getString("isgraduated"));
                    data1.setEmail(ls1.getString("email"));
                    data1.setIsmailing(ls1.getString("ismailing"));
                    data1.setDispnum(total_row_count - ls1.getRowNum() + 1);
                    data1.setTotalPageCount(total_page_count);
                    data1.setRowCount(row);
                    list1.add(data1);
                }
                for(int i=0;i < list1.size(); i++){
                    data2       =   (CompleteStatusData)list1.get(i);
                    v_course    =   data2.getCourse();
                    v_courseseq =   data2.getCourseseq();
                    if(!v_course.equals("000000") && !(v_Bcourse.equals(v_course) && v_Bcourseseq.equals(v_courseseq))){
                        sql2 = "select count(A.subj) cnt from VZ_SCSUBJSEQ A,TZ_STOLD B,TZ_MEMBER C ";
                        sql2+= "where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq ";
                        sql2+= "and B.isgraduated='Y' and B.userid=C.userid ";
                        if (!ss_grcode.equals("ALL")) {
                            sql2+= " and A.grcode = "+SQLString.Format(ss_grcode);
                        }
                        if (!ss_grseq.equals("ALL")) {
                            sql2+= " and A.grseq = "+SQLString.Format(ss_grseq);
                        }
                        if (!ss_uclass.equals("ALL")) {
                            sql2+= " and A.scupperclass = "+SQLString.Format(ss_uclass);
                        }
                        if (!ss_mclass.equals("ALL")) {
                            sql2+= " and A.scmiddleclass = "+SQLString.Format(ss_mclass);
                        }
                        if (!ss_lclass.equals("ALL")) {
                            sql2+= " and A.sclowerclass = "+SQLString.Format(ss_lclass);
                        }

                        if (!ss_subjcourse.equals("ALL")) {
                            sql2+= " and A.scsubj = "+SQLString.Format(ss_subjcourse);
                        }
                        if (!ss_subjseq.equals("ALL")) {
                            sql2+= " and A.scsubjseq = "+SQLString.Format(ss_subjseq);
                        }
                        if (!ss_company.equals("ALL")) {
                            sql2+= " and C.comp like '"+GetCodenm.get_compval(ss_company)+"'";
                        }
                        if (!ss_edustart.equals("ALL")){
                            sql2+= " and A.edustart >= "+SQLString.Format(ss_edustart);
                        }
                        if (!ss_eduend.equals("ALL")) {
                            sql2+= " and A.eduend <= "+SQLString.Format(ss_eduend);
                        }
                        //���������ϰ� �������� ���õ��� ���� ��쿡�� �����⵵���� �˻���
                        if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
                            sql2+= " and A.gyear = "+SQLString.Format(ss_gyear);
                        }

                        // �μ����ϰ��
                        if (s_gadmin.equals("K7")) {
                            if (!v_sql_add.equals("")) sql2 += " and C.comp in " + v_sql_add;       // �����μ��˻���������
                        }

                        if(ss_selgubun.equals("JIKUN") && !ss_seltext.equals("ALL"))      {  //������
                            sql2+= " and C.jikun = "+SQLString.Format(ss_seltext);
                        }else if(ss_selgubun.equals("JIKWI") && !ss_seltext.equals("ALL")){  //������
                            sql2+= " and C.jikwi = "+SQLString.Format(ss_seltext);
                        }else if(ss_selgubun.equals("GPM") && !ss_seltext.equals("ALL")){  //����κ�
                            sql2+= " and C.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seltext));
                            if(!ss_seldept.equals("ALL")){
                                sql2+= " and C.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seldept));
                             }
                        }
                        sql2+= "and A.course = "+SQLString.Format(v_course)+" and A.courseseq = "+SQLString.Format(v_courseseq);

                        ls2 = connMgr.executeQuery(sql2);

                        if(ls2.next()){
                            data2.setRowspan(ls2.getInt("cnt"));
                            data2.setIsnewcourse("Y");
                        }
                        if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
                    }else{
                        data2.setRowspan(0);
                        data2.setIsnewcourse("N");
                    }
                    v_Bcourse   =   v_course;
                    v_Bcourseseq=   v_courseseq;
                    list2.add(data2);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list2;
    }

    /**
    �̼����� ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectNoneCompleteRosterList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
        String sql1         = "";
        String sql2         = "";
        CompleteStatusData data1=null;
        CompleteStatusData data2=null;
        String  v_Bcourse   = ""; //�����ڽ�
        String  v_course    = ""; //�����ڽ�
        String  v_Bcourseseq= ""; //�����ڽ�����
        String  v_courseseq = ""; //�����ڽ�����
        int v_pageno        = box.getInt("p_pageno");
        int     l           = 0;
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //�����׷�
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //�⵵
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //��������
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");       //�����з�
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");      //�����з�
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");       //�����з�
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//����&�ڽ�
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //���� ����
        String  ss_company  = box.getStringDefault("s_company","ALL");   //ȸ��
        String  ss_edustart = box.getStringDefault("s_edustart","ALL");  //����������
        String  ss_eduend   = box.getStringDefault("s_eduend","ALL");    //����������
        String  ss_selgubun = box.getString("s_selgubun");               //������:JIKUN,���޺�:JIKUP,����κ�:GPM
        String  ss_seltext  = box.getStringDefault("s_seltext","ALL");   //�˻��з��� �˻�����
        String  ss_seldept  = box.getStringDefault("s_seldept","ALL");   //����κ� �μ� �˻�����
        String  ss_action   = box.getString("s_action");
        String  v_orderColumn= box.getString("p_orderColumn");           //������ �÷���

        ManagerAdminBean bean = null;
        String  v_sql_add   = "";
        String  v_userid    = box.getSession("userid");
        String  s_gadmin    = box.getSession("gadmin");

//		2005.11.15_�ϰ��� : TotalCount ���� ���� ����
        String sql    	  = "";
        String count_sql  = "";
        String head_sql   = "";
		String body_sql   = "";
        String group_sql  = "";
        String order_sql  = "";

        try {
            if(ss_action.equals("go")){
				PreparedStatement pstmt = null;
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
                list2 = new ArrayList();

                //select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,isonoff,compnm,
                //jikwinm,userid,cono,name,edustart,eduend,score,isgraduated,email,ismailing
				head_sql = "select C.grseq,C.course,C.cyear,C.courseseq,C.coursenm,C.subj,C.year,C.subjnm,C.subjseq,";
				head_sql+= "C.isonoff,get_compnm(B.comp,3,5) compnm,get_jikwinm(B.jikwi,B.comp) jikwinm,get_jikupnm(B.jikup,B.comp) jikupnm,";
				head_sql+= "B.userid,B.cono,B.name,C.edustart,C.eduend,A.score,A.isgraduated,B.email,B.ismailing ";
				body_sql+= "from TZ_STOLD A,TZ_MEMBER B,VZ_SCSUBJSEQ C where A.isgraduated='N' and c.isclosed = 'Y' ";
                if (!ss_grcode.equals("ALL")) {
					body_sql+= " and C.grcode = "+SQLString.Format(ss_grcode);
                }
                if (!ss_grseq.equals("ALL")) {
					body_sql+= " and C.grseq = "+SQLString.Format(ss_grseq);
                }
                if (!ss_uclass.equals("ALL")) {
					body_sql+= " and C.scupperclass = "+SQLString.Format(ss_uclass);
                }
                if (!ss_mclass.equals("ALL")) {
					body_sql+= " and C.scmiddleclass = "+SQLString.Format(ss_mclass);
                }
                if (!ss_lclass.equals("ALL")) {
					body_sql+= " and C.sclowerclass = "+SQLString.Format(ss_lclass);
                }

                if (!ss_subjcourse.equals("ALL")) {
					body_sql+= " and C.scsubj = "+SQLString.Format(ss_subjcourse);
                }
                if (!ss_subjseq.equals("ALL")) {
					body_sql+= " and C.scsubjseq = "+SQLString.Format(ss_subjseq);
                }
                if (!ss_company.equals("ALL")) {
					body_sql+= " and B.comp like '"+GetCodenm.get_compval(ss_company)+"'";
                }
                if (!ss_edustart.equals("ALL")){
					body_sql+= " and C.edustart >= "+SQLString.Format(ss_edustart);
                }
                if (!ss_eduend.equals("ALL")) {
					body_sql+= " and C.eduend <= "+SQLString.Format(ss_eduend);
                }
                //���������ϰ� �������� ���õ��� ���� ��쿡�� �����⵵���� �˻���
                if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
					body_sql+= " and C.gyear = "+SQLString.Format(ss_gyear);
                }
/*
                // �μ����ϰ��
                if (s_gadmin.equals("K7")) {
                    bean = new ManagerAdminBean();
                    v_sql_add   = bean.getManagerDept(v_userid, s_gadmin);
                    if (!v_sql_add.equals("")) body_sql += " and B.comp in " + v_sql_add;       // �����μ��˻���������
                }

                if(ss_selgubun.equals("JIKUN") && !ss_seltext.equals("ALL"))      {  //������
					body_sql+= " and B.jikun = "+SQLString.Format(ss_seltext);
                }else if(ss_selgubun.equals("JIKWI") && !ss_seltext.equals("ALL")){  //������
					body_sql+= " and B.jikwi = "+SQLString.Format(ss_seltext);
                }else if(ss_selgubun.equals("GPM") && !ss_seltext.equals("ALL")){  //����κ�
					body_sql+= " and B.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seltext));
                    if(!ss_seldept.equals("ALL")){
						body_sql+= " and B.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seldept));
                     }
                }
                */
				body_sql+= " and A.userid=B.userid and A.subj=C.subj and A.year=C.year and A.subjseq=C.subjseq ";
                if(!v_orderColumn.equals("")){
                    v_orderColumn = "B."+v_orderColumn;
					order_sql+= " order by C.course,C.cyear,C.courseseq,C.subj,C.year,C.subjseq,"+v_orderColumn;
                }else{
					order_sql+= " order by C.course,C.cyear,C.courseseq,C.subj,C.year,C.subjseq,B.userid ";
                }

				/*
                ls1 = connMgr.executeQuery(sql1);

				pstmt = connMgr.prepareStatement(sql1, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
				ls1 = new ListSet(pstmt);
				*/
				sql= head_sql+ body_sql+ order_sql;

			ls1 = connMgr.executeQuery(sql);

			count_sql= "select count(*) "+ body_sql;
			int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql);

                ls1.setPageSize(row);                       //�������� row ������ �����Ѵ�
                ls1.setCurrentPage(v_pageno, total_row_count);               //������������ȣ�� �����Ѵ�.
                int total_page_count = ls1.getTotalPage();  //��ü ������ ���� ��ȯ�Ѵ�
                //int total_row_count = ls1.getTotalCount();  //��ü row ���� ��ȯ�Ѵ�

                while (ls1.next()) {
                    data1=new CompleteStatusData();
                    data1.setGrseq(ls1.getString("grseq"));
                    data1.setCourse(ls1.getString("course"));
                    data1.setCyear(ls1.getString("cyear"));
                    data1.setCourseseq(ls1.getString("courseseq"));
                    data1.setCoursenm(ls1.getString("coursenm"));
                    data1.setSubj(ls1.getString("subj"));
                    data1.setYear(ls1.getString("year"));
                    data1.setSubjseq(ls1.getString("subjseq"));
                    data1.setSubjnm(ls1.getString("subjnm"));
                    data1.setIsonoff(ls1.getString("isonoff"));
                    data1.setCompnm(ls1.getString("compnm"));
                    data1.setJikwinm(ls1.getString("jikwinm"));
                    data1.setJikupnm(ls1.getString("jikupnm"));
                    data1.setUserid(ls1.getString("userid"));
                    data1.setCono(ls1.getString("cono"));
                    data1.setName(ls1.getString("name"));
                    data1.setEdustart(ls1.getString("edustart"));
                    data1.setEduend(ls1.getString("eduend"));
                    data1.setScore(ls1.getInt("score"));
                    data1.setIsgraduated(ls1.getString("isgraduated"));
                    data1.setEmail(ls1.getString("email"));
                    data1.setIsmailing(ls1.getString("ismailing"));
                    data1.setDispnum(total_row_count - ls1.getRowNum() + 1);
                    data1.setTotalPageCount(total_page_count);
                    data1.setRowCount(row);
                    list1.add(data1);
                }
                for(int i=0;i < list1.size(); i++){
                    data2       =   (CompleteStatusData)list1.get(i);
                    v_course    =   data2.getCourse();
                    v_courseseq =   data2.getCourseseq();
                    if(!v_course.equals("000000") && !(v_Bcourse.equals(v_course) && v_Bcourseseq.equals(v_courseseq))){
                        sql2 = "select count(A.subj) cnt from VZ_SCSUBJSEQ A,TZ_STOLD B,TZ_MEMBER C ";
                        sql2+= "where A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq ";
                        sql2+= "and B.isgraduated='N' and B.userid=C.userid ";
                        if (!ss_grcode.equals("ALL")) {
                            sql2+= " and A.grcode = "+SQLString.Format(ss_grcode);
                        }
                        if (!ss_grseq.equals("ALL")) {
                            sql2+= " and A.grseq = "+SQLString.Format(ss_grseq);
                        }
                        if (!ss_uclass.equals("ALL")) {
                            sql2+= " and A.scupperclass = "+SQLString.Format(ss_uclass);
                        }
                        if (!ss_mclass.equals("ALL")) {
                            sql2+= " and A.scmiddleclass = "+SQLString.Format(ss_mclass);
                        }
                        if (!ss_lclass.equals("ALL")) {
                            sql2+= " and A.sclowerclass = "+SQLString.Format(ss_lclass);
                        }

                        if (!ss_subjcourse.equals("ALL")) {
                            sql2+= " and A.scsubj = "+SQLString.Format(ss_subjcourse);
                        }
                        if (!ss_subjseq.equals("ALL")) {
                            sql2+= " and A.scsubjseq = "+SQLString.Format(ss_subjseq);
                        }
                        if (!ss_company.equals("ALL")) {
                            sql2+= " and C.comp like '"+GetCodenm.get_compval(ss_company)+"'";
                        }
                        if (!ss_edustart.equals("ALL")){
                            sql2+= " and A.edustart >= "+SQLString.Format(ss_edustart);
                        }
                        if (!ss_eduend.equals("ALL")) {
                            sql2+= " and A.eduend <= "+SQLString.Format(ss_eduend);
                        }
                        //���������ϰ� �������� ���õ��� ���� ��쿡�� �����⵵���� �˻���
                        if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
                            sql2+= " and A.gyear = "+SQLString.Format(ss_gyear);
                        }
/*
                        // �μ����ϰ��
                        if (s_gadmin.equals("K7")) {
                            if (!v_sql_add.equals("")) sql2 += " and C.comp in " + v_sql_add;       // �����μ��˻���������
                        }

                        if(ss_selgubun.equals("JIKUN") && !ss_seltext.equals("ALL"))      {  //������
                            sql2+= " and C.jikun = "+SQLString.Format(ss_seltext);
                        }else if(ss_selgubun.equals("JIKWI") && !ss_seltext.equals("ALL")){  //������
                            sql2+= " and C.jikwi = "+SQLString.Format(ss_seltext);
                        }else if(ss_selgubun.equals("GPM") && !ss_seltext.equals("ALL")){  //����κ�
                            sql2+= " and C.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seltext));
                            if(!ss_seldept.equals("ALL")){
                                sql2+= " and C.comp like "+SQLString.Format(GetCodenm.get_compval(ss_seldept));
                             }
                        }
                        */
                        sql2+= "and A.course = "+SQLString.Format(v_course)+" and A.courseseq = "+SQLString.Format(v_courseseq);

                        ls2 = connMgr.executeQuery(sql2);

                        if(ls2.next()){
                            data2.setRowspan(ls2.getInt("cnt"));
                            data2.setIsnewcourse("Y");
                        }
                        if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
                    }else{
                        data2.setRowspan(0);
                        data2.setIsnewcourse("N");
                    }
                    v_Bcourse   =   v_course;
                    v_Bcourseseq=   v_courseseq;
                    list2.add(data2);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list2;
    }

    /**
    ������ ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectCompleteRateList(RequestBox box) throws Exception {
		PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
		// ������ : 05.11.11 ������ : �̳��� _ TotalCount ���ֱ� ���� ������ ���� ����
	    String sql1     = "";
	    String count_sql1     = "";
	    String head_sql1 = "";
		String body_sql1 = "";
	    String group_sql1 = "";
	    String order_sql1 = "";

	    String sql2     = "";
	    String count_sql2     = "";
	    String head_sql2 = "";
		String body_sql2 = "";
	    String group_sql2 = "";
	    String order_sql2 = "";

		DataBox dbox = null;
        String  v_Bcourse   = ""; //�����ڽ�
        String  v_course    = ""; //�����ڽ�
        String  v_Bcourseseq= ""; //�����ڽ�����
        String  v_courseseq = ""; //�����ڽ�����

        int v_pageno        = box.getInt("p_pageno");
        int     l           = 0;

		String  v_subjnm	= box.getStringDefault("p_subjnm","");	 //������ �˻�
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //�����׷�
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //�⵵
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //��������
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");       //�����з�
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");      //�����з�
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");       //�����з�
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//����&�ڽ�
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //���� ����
        String  ss_edustart = box.getStringDefault("s_edustart","ALL");  //����������
        String  ss_eduend   = box.getStringDefault("s_eduend","ALL");    //����������
        String  ss_action   = box.getString("s_action");

		String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
        String  v_orderType     = box.getString("p_orderType");           		//������ ����

        try {
            if(ss_action.equals("go")){
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
                list2 = new ArrayList();

                //select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,
                //edustart,eduend,educnt,gradcnt1,gradcnt2,isonoff
                head_sql1 = "select A.grseq,A.course,A.cyear,A.courseseq,A.coursenm,A.subj,A.year,A.subjnm,A.subjseq,A.subjseqgr,";
				head_sql1+= "A.edustart,A.eduend,A.isonoff, A.isbelongcourse, A.subjcnt, ";
				head_sql1+= "(select count(s.subj) from TZ_STUDENT s where s.subj=A.subj and s.year=A.year and s.subjseq=A.subjseq) educnt, ";
				head_sql1+= " case A.isclosed	";
				head_sql1+= "	 When \'Y\'	Then (select count(subj) from TZ_STOLD where subj=A.subj and year=A.year and subjseq=A.subjseq and isgraduated='Y') ";
				head_sql1+= " End as gradcnt1,";
				head_sql1+= " case A.isclosed	";
				head_sql1+= "	 When \'N\'   Then 0 ";
				head_sql1+= " End as gradcnt2, ";
				head_sql1+= "	 ( (case A.isclosed	When \'Y\'	Then 15		End) +  ( case A.isclosed		When \'N\'	Then 0		End )  ) tgradcnt  	";
				// ������ : 05.11.04 ������ : �̳��� _ ������� ����

                body_sql1+= "from VZ_SCSUBJSEQ A ";
				body_sql1+= "where 1=1 ";
                if (!ss_grcode.equals("ALL")) {
					body_sql1+= " and A.grcode = "+SQLString.Format(ss_grcode);
                }
                if (!ss_grseq.equals("ALL")) {
					body_sql1+= " and A.grseq = "+SQLString.Format(ss_grseq);
                }
                if (!ss_uclass.equals("ALL")) {
					body_sql1+= " and A.scupperclass = "+SQLString.Format(ss_uclass);
                }
                if (!ss_mclass.equals("ALL")) {
					body_sql1+= " and A.scmiddleclass = "+SQLString.Format(ss_mclass);
                }
                if (!ss_lclass.equals("ALL")) {
					body_sql1+= " and A.sclowerclass = "+SQLString.Format(ss_lclass);
                }

                if (!ss_subjcourse.equals("ALL")) {
					body_sql1+= " and A.scsubj = "+SQLString.Format(ss_subjcourse);
                }
                if (!ss_subjseq.equals("ALL")) {
					body_sql1+= " and A.scsubjseq = "+SQLString.Format(ss_subjseq);
                }
                if (!ss_edustart.equals("ALL")){
					body_sql1+= " and A.edustart >= "+SQLString.Format(ss_edustart);
                }
                if (!ss_eduend.equals("ALL")) {
					body_sql1+= " and A.eduend <= "+SQLString.Format(ss_eduend);
                }
                //���������ϰ� �������� ���õ��� ���� ��쿡�� �����⵵���� �˻���
                if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
					body_sql1+= " and A.gyear = "+SQLString.Format(ss_gyear);
                }
				//������ �˻�
				if (!v_subjnm.equals("")) {
					body_sql1+= " and A.subjnm like '%" + v_subjnm + "%' ";
				}

				if(v_orderColumn.equals("")) {
                	order_sql1+= " order by A.course,A.cyear,A.courseseq,A.subj,A.year,A.subjseq ";
				} else {
				    order_sql1+= " order by " + v_orderColumn + v_orderType;
				}
				sql1= head_sql1+ body_sql1+ group_sql1+ order_sql1;

                ls1 = connMgr.executeQuery(sql1);


				count_sql1= "select count(*) " + body_sql1;
				int total_row_count= BoardPaging.getTotalRow(connMgr, count_sql1);

                ls1.setPageSize(row);                       //�������� row ������ �����Ѵ�
                ls1.setCurrentPage(v_pageno, total_row_count);               //������������ȣ�� �����Ѵ�.
                int total_page_count = ls1.getTotalPage();  //��ü ������ ���� ��ȯ�Ѵ�

                while (ls1.next()) {
					dbox = ls1.getDataBox();

					dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
	                dbox.put("d_totalpage", new Integer(total_page_count));
	                dbox.put("d_rowcount", new Integer(row));

			        list1.add(dbox);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }

	 /**
    ������ ����Ʈ(Excel)
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectCompleteRateExcelList(RequestBox box) throws Exception {
		PreparedStatement pstmt = null;
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
		// ������ : 05.11.11 ������ : �̳��� _ TotalCount ���ֱ� ���� ������ ���� ����
	    String sql1     = "";
	    String count_sql1     = "";
	    String head_sql1 = "";
		String body_sql1 = "";
	    String group_sql1 = "";
	    String order_sql1 = "";

	    String sql2     = "";
	    String count_sql2     = "";
	    String head_sql2 = "";
		String body_sql2 = "";
	    String group_sql2 = "";
	    String order_sql2 = "";

		DataBox dbox = null;
        String  v_Bcourse   = ""; //�����ڽ�
        String  v_course    = ""; //�����ڽ�
        String  v_Bcourseseq= ""; //�����ڽ�����
        String  v_courseseq = ""; //�����ڽ�����

        int     l           = 0;

		String  v_subjnm	= box.getStringDefault("p_subjnm","");	 //������ �˻�
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");    //�����׷�
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");     //�⵵
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");     //��������
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");       //�����з�
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");      //�����з�
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");       //�����з�
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");//����&�ڽ�
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");   //���� ����
        String  ss_edustart = box.getStringDefault("s_edustart","ALL");  //����������
        String  ss_eduend   = box.getStringDefault("s_eduend","ALL");    //����������
        String  ss_action   = box.getString("s_action");

		String  v_orderColumn   = box.getString("p_orderColumn");           	//������ �÷���
        String  v_orderType     = box.getString("p_orderType");           		//������ ����

        try {
            if(ss_action.equals("go")){
                connMgr = new DBConnectionManager();
                list1 = new ArrayList();
                list2 = new ArrayList();

                //select grseq,course,cyear,courseseq,coursenm,subj,year,subjnm,subjseq,
                //edustart,eduend,educnt,gradcnt1,gradcnt2,isonoff
                head_sql1 = "select A.grseq,A.course,A.cyear,A.courseseq,A.coursenm,A.subj,A.year,A.subjnm,A.subjseq,A.subjseqgr,";
				head_sql1+= "A.edustart,A.eduend,A.isonoff, A.isbelongcourse, A.subjcnt, ";
				head_sql1+= "(select count(s.subj) from TZ_STUDENT s where s.subj=A.subj and s.year=A.year and s.subjseq=A.subjseq) educnt, ";

				head_sql1+= " case A.isclosed	";
				head_sql1+= "	 When \'Y\'	Then (select count(subj) from TZ_STOLD where subj=A.subj and year=A.year and subjseq=A.subjseq and isgraduated='Y') ";
				head_sql1+= " End as gradcnt1,";
				head_sql1+= " case A.isclosed	";
				head_sql1+= "	 When \'N\'   Then 0 ";
				head_sql1+= " End as gradcnt2, ";
				head_sql1+= "	 ( (case A.isclosed	When \'Y\'	Then 15		End) +  ( case A.isclosed		When \'N\'	Then 0		End )  ) tgradcnt  	";
				// ������ : 05.11.04 ������ : �̳��� _ ������� ����

                body_sql1+= "from VZ_SCSUBJSEQ A ";
				body_sql1+= "where 1=1 ";
                if (!ss_grcode.equals("ALL")) {
					body_sql1+= " and A.grcode = "+SQLString.Format(ss_grcode);
                }
                if (!ss_grseq.equals("ALL")) {
					body_sql1+= " and A.grseq = "+SQLString.Format(ss_grseq);
                }
                if (!ss_uclass.equals("ALL")) {
					body_sql1+= " and A.scupperclass = "+SQLString.Format(ss_uclass);
                }
                if (!ss_mclass.equals("ALL")) {
					body_sql1+= " and A.scmiddleclass = "+SQLString.Format(ss_mclass);
                }
                if (!ss_lclass.equals("ALL")) {
					body_sql1+= " and A.sclowerclass = "+SQLString.Format(ss_lclass);
                }

                if (!ss_subjcourse.equals("ALL")) {
					body_sql1+= " and A.scsubj = "+SQLString.Format(ss_subjcourse);
                }
                if (!ss_subjseq.equals("ALL")) {
					body_sql1+= " and A.scsubjseq = "+SQLString.Format(ss_subjseq);
                }
                if (!ss_edustart.equals("ALL")){
					body_sql1+= " and A.edustart >= "+SQLString.Format(ss_edustart);
                }
                if (!ss_eduend.equals("ALL")) {
					body_sql1+= " and A.eduend <= "+SQLString.Format(ss_eduend);
                }
                //���������ϰ� �������� ���õ��� ���� ��쿡�� �����⵵���� �˻���
                if (ss_edustart.equals("ALL") && ss_eduend.equals("ALL")) {
					body_sql1+= " and A.gyear = "+SQLString.Format(ss_gyear);
                }
				//������ �˻�
				if (!v_subjnm.equals("")) {
					body_sql1+= " and A.subjnm like '%" + v_subjnm + "%' ";
				}

				if(v_orderColumn.equals("")) {
                	order_sql1+= " order by A.course,A.cyear,A.courseseq,A.subj,A.year,A.subjseq ";
				} else {
				    order_sql1+= " order by A.course," + v_orderColumn + v_orderType;
				}
				sql1= head_sql1+ body_sql1+ group_sql1+ order_sql1;

                ls1 = connMgr.executeQuery(sql1);


                while (ls1.next()) {
					dbox = ls1.getDataBox();

			        list1.add(dbox);
                }
            }
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list1;
    }

    /**
    ��뺸��ȯ�޸�� ����Ʈ
    @param box      receive from the form object and session
    @return ArrayList
    */
     public ArrayList selectHiringInsuranceReturnedList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1         = null;
        ListSet ls2         = null;
        ArrayList list1     = null;
        ArrayList list2     = null;
        String sql1         = "";
        String sql2         = "";
        CompleteStatusData data1=null;
        CompleteStatusData data2=null;
        String  v_Bcourse   = ""; //�����ڽ�
        String  v_course    = ""; //�����ڽ�
        String  v_Bcourseseq= ""; //�����ڽ�����
        String  v_courseseq = ""; //�����ڽ�����
        int     l           = 0;
        String  ss_grcode   = box.getStringDefault("s_grcode","ALL");       //�����׷�
        String  ss_gyear    = box.getStringDefault("s_gyear","ALL");        //�⵵
        String  ss_grseq    = box.getStringDefault("s_grseq","ALL");        //��������
        String  ss_uclass   = box.getStringDefault("s_upperclass","ALL");   //�����з�
        String  ss_mclass   = box.getStringDefault("s_middleclass","ALL");  //�����з�
        String  ss_lclass   = box.getStringDefault("s_lowerclass","ALL");   //�����з�
        String  ss_subjcourse=box.getStringDefault("s_subjcourse","ALL");   //����&�ڽ�
        String  ss_subjseq  = box.getStringDefault("s_subjseq","ALL");      //���� ����
        String  ss_company  = box.getStringDefault("s_company","ALL");      //ȸ��
        String  ss_selgubun = box.getString("s_selgubun");                  //������:JIKUN,���޺�:JIKUP,����κ�:GPM
        String  ss_seltext  = box.getStringDefault("s_seltext","ALL");      //�˻��з��� �˻�����
        String  ss_seldept  = box.getStringDefault("s_seldept","ALL");      //����κ� �μ� �˻�����
        String  ss_action   = box.getString("s_action");
        String  v_orderColumn= box.getString("p_orderColumn");              //������ �÷���

        try {
        }
        catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        }
        finally {
            if(ls1 != null) { try { ls1.close(); }catch (Exception e) {} }
            if(ls2 != null) { try { ls2.close(); }catch (Exception e) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
        return list2;
    }

    /**
    ������ �߼�
    @param box      receive from the form object and session
    @return int
    */
    public int sendFormMail(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        Connection conn = null;
        ListSet ls = null;
        String sql = "";
        int cnt = 0;    //  ���Ϲ߼��� ������ �����
        //p_checks�� �Ѿ�� �ټ��� value�� ó���ϱ� ���� vector�� ����
        Vector v_check1     = new Vector();
        v_check1            = box.getVector("p_checks");
        Enumeration em1     = v_check1.elements();
        StringTokenizer st1 = null;
        String v_checks     = "";
        String v_userid     = "";
        String v_subj       = "";
        String v_year       = "";
        String v_subjseq    = "";

        try {
            connMgr = new DBConnectionManager();

////////////////////  ������ �߼� //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            String v_sendhtml = "mail3.html";
            FormMail fmail = new FormMail(v_sendhtml);     //      �����Ϲ߼��� ���
            MailSet mset = new MailSet(box);        //      ���� ���� �� �߼�
            String v_mailTitle = "�ȳ��ϼ���? ����Դϴ�.(�������ȳ�)";
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            while(em1.hasMoreElements()){
                v_checks    = (String)em1.nextElement();
                st1      = new StringTokenizer(v_checks,",");
                while (st1.hasMoreElements()) {
                    v_userid    = (String)st1.nextToken();
                    v_subj      = (String)st1.nextToken();
                    v_year      = (String)st1.nextToken();
                    v_subjseq   = (String)st1.nextToken();
					break;
                }
                //select subjnm,passday,tstep,gradstep,gradscore,name,ismailing,cono,email
                sql = "select  B.subjnm,A.tstep,B.gradstep,B.sgradscore gradscore,D.name,D.ismailing,D.cono,D.email,";
                sql+= "(to_number(to_char(sysdate, 'YYYYMMDD')) - to_number(SUBSTR(B.edustart,1,8))) passday ";
                sql+= " from TZ_STUDENT A,VZ_SCSUBJSEQ B,TZ_MEMBER D ";
                sql+= " where A.userid = "+SQLString.Format(v_userid);
                sql+= " and A.subj = "+SQLString.Format(v_subj);
                sql+= " and A.year = "+SQLString.Format(v_year);
                sql+= " and A.subjseq = "+SQLString.Format(v_subjseq);
                sql+= " and A.subj=B.subj and A.year=B.year and A.subjseq=B.subjseq and A.userid=D.userid ";
                sql+= " group by B.subjnm,A.tstep,B.gradstep,B.sgradscore,D.name,D.ismailing,D.cono,D.email,B.edustart,B.eduend ";
                ls = connMgr.executeQuery(sql);
//                System.out.println("sql=========>"+sql);

                while (ls.next()) {
                    String v_toEmail =  ls.getString("email");
                    String v_toCono  =  ls.getString("cono");
                    String v_ismailing= ls.getString("ismailing");


                    mset.setSender(fmail);     //���Ϻ����� ��� ����

                    fmail.setVariable("tstep", ls.getString("tstep"));
                    fmail.setVariable("subjnm", ls.getString("subjnm"));
                    fmail.setVariable("passday", ls.getString("passday"));
                    fmail.setVariable("tstep", ls.getString("tstep"));
                    fmail.setVariable("gradstep", ls.getString("gradstep"));
                    fmail.setVariable("gradscore", ls.getString("gradscore"));
                    fmail.setVariable("toname", ls.getString("name"));

                    String v_mailContent = fmail.getNewMailContent();

                    boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent,v_ismailing, v_sendhtml);
                    if(isMailed) cnt++;     //      ���Ϲ߼ۿ� �����ϸ�
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

        return cnt;
    }

}