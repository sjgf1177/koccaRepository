//**********************************************************
//  1. 제	  목: PROJECT ADMIN BEAN
//  2. 프로그램명: ProjectAdminBean.java
//  3. 개	  요: 리포트 관리자 bean
//  4. 환	  경: JDK 1.3
//  5. 버	  젼: 1.0
//  6. 작	  성:
//  7. 수	  정:
//**********************************************************
package com.credu.study;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Vector;

import com.credu.library.CalcUtil;
import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormMail;
import com.credu.library.ListSet;
import com.credu.library.MailSet;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.credu.library.StringManager;
import com.dunet.common.util.StringUtil;

public class ProjectAdminBean {
    private ConfigSet config;
    private int row;

    public ProjectAdminBean() {
        try {
            config = new ConfigSet();
            row = Integer.parseInt(config.getProperty("page.bulletin.row")); //        이 모듈의 페이지당 row 수를 셋팅한다
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 리포트출제관리 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<DataBox> selectProjectQuestionsAList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<DataBox> list1 = null;
        //		ArrayList list2	 = null;
        DataBox dbox = null;
        String sql1 = "";
        //		String sql2		 = "";
        //		ProjectData data1   = null;
        //		ProjectData data2   = null;
        //		String  v_Bcourse   = ""; //이전코스
        //		String  v_course	= ""; //현재코스
        //		String  v_Bcourseseq= ""; //이전코스차수
        //		String  v_courseseq = ""; //현재코스차수
        //		int	 l		   = 0;

        int v_pageno = box.getInt("p_pageno");
        if (v_pageno == 0)
            v_pageno = 1;

        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수

        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류

        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //과정 차수
        String ss_action = box.getString("s_action");

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        String ss_area = box.getString("s_area"); //문콘,게임,방송
        String ss_biyong = box.getString("s_biyong"); //전체,무료,유료

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<DataBox>();
                //				list2 = new ArrayList();

                //select course,cyear,courseseq,coursenm,subj,year,subjseq,isclosed,subjnm,isonoff
                sql1 = "select  a.course,";
                sql1 += "		a.cyear,";
                sql1 += "		a.courseseq,";
                sql1 += "		a.coursenm,";
                sql1 += "		a.subj,";
                sql1 += "		a.year,";
                sql1 += "		a.subjseq,";
                sql1 += "		a.subjseqgr,";
                sql1 += "		a.isclosed,";
                sql1 += "		a.subjnm,";
                sql1 += "		a.isonoff, ";
                sql1 += "		a.edustart, "; // 교육기간 시작일
                sql1 += "		a.eduend, "; // 교육기간 마감일
                sql1 += "		a.subjcnt , "; // 코스과정 과목수.
                sql1 += "		a.isbelongcourse , "; // 코스/일반 구분값.

                //2009.11.05 1분이상 쿼리문 수정 
                //sql1+= "		(select count(distinct projseq) from tz_projord where subj=a.subj and year=a.year and subjseq=a.subjseq) projseqcnt, ";
                //sql1+= "		(select count(ordseq) from tz_projord where subj=a.subj and year=a.year and subjseq=a.subjseq) ordseqcnt ";
                sql1 += "        nvl(b.projseqcnt,0) projseqcnt, ";
                sql1 += "        nvl(b.ordseqcnt,0) ordseqcnt ";
                //sql1+= "from	VZ_SCSUBJSEQ a where 1 = 1 ";
                sql1 += " from VZ_SCSUBJSEQ a ";
                sql1 += "  left outer join (select subj, year, subjseq, count(distinct projseq) projseqcnt, count(ordseq) ordseqcnt from TZ_PROJORD group by subj, year, subjseq) b ";
                sql1 += "    on a.subj    = b.subj ";
                sql1 += "   and a.year    = b.year ";
                sql1 += "   and a.subjseq = b.subjseq ";
                sql1 += " where 1 = 1 ";
                //2009.11.05 1분이상 쿼리문 수정 

                if (!ss_area.equals("T"))
                    sql1 += " and a.area=" + SQLString.Format(ss_area);
                if (ss_biyong.equals("Z"))
                    sql1 += " and a.BIYONG        = 0";
                else if (ss_biyong.equals("P"))
                    sql1 += " and a.BIYONG        != 0";

                if (!ss_grcode.equals("ALL")) {
                    sql1 += " and a.grcode = '" + ss_grcode + "'";
                }
                if (!ss_gyear.equals("ALL")) {
                    sql1 += " and a.gyear = '" + ss_gyear + "'";
                }
                if (!ss_grseq.equals("ALL")) {
                    sql1 += " and a.grseq = '" + ss_grseq + "'";
                }

                if (!ss_uclass.equals("ALL")) {
                    sql1 += " and a.scupperclass = '" + ss_uclass + "'";
                }

                if (!ss_mclass.equals("ALL")) {
                    sql1 += " and a.scmiddleclass = '" + ss_mclass + "'";
                }

                if (!ss_lclass.equals("ALL")) {
                    sql1 += " and a.sclowerclass = '" + ss_lclass + "'";
                }

                if (!ss_subjcourse.equals("ALL")) {
                    sql1 += " and a.scsubj = '" + ss_subjcourse + "'";
                }
                if (!ss_subjseq.equals("ALL")) {
                    sql1 += " and a.scsubjseq = '" + ss_subjseq + "'";
                }

                if (v_orderColumn.equals("")) {
                    sql1 += " order by a.course, a.cyear, a.courseseq, a.subj, a.year, a.subjseq ";
                } else {
                    sql1 += " order by a.course," + v_orderColumn + v_orderType;
                }
                System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$" + sql1);
                ls1 = connMgr.executeQuery(sql1);

                //페이징
                ls1.setPageSize(row); //  페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno); //     현재페이지번호를 세팅한다.
                int total_page_count = ls1.getTotalPage(); //     전체 페이지 수를 반환한다
                int total_row_count = ls1.getTotalCount(); //     전체 row 수를 반환한다

                while (ls1.next()) {
                    dbox = ls1.getDataBox();
                    list1.add(dbox);

                    //페이징
                    dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                    dbox.put("d_totalpage", new Integer(total_page_count));
                    dbox.put("d_rowcount", new Integer(row));

                    /*
                     * data1=new ProjectData();
                     * data1.setCourse(ls1.getString("course"));
                     * data1.setCyear(ls1.getString("cyear"));
                     * data1.setCourseseq(ls1.getString("courseseq"));
                     * data1.setCoursenm(ls1.getString("coursenm"));
                     * data1.setSubj(ls1.getString("subj"));
                     * data1.setYear(ls1.getString("year"));
                     * data1.setSubjseq(ls1.getString("subjseq"));
                     * data1.setSubjseqgr(ls1.getString("subjseqgr"));
                     * data1.setSubjnm(ls1.getString("subjnm"));
                     * data1.setProjseqcnt(ls1.getInt("projseqcnt"));
                     * data1.setOrdseqcnt(ls1.getInt("ordseqcnt"));
                     * data1.setIsclosed(ls1.getString("isclosed"));
                     * data1.setIsonoff(ls1.getString("isonoff"));
                     * data1.setEdustart(ls1.getString("edustart"));
                     * data1.setEduend(ls1.getString("eduend"));
                     * 
                     * list1.add(data1);
                     */
                }
                /*
                 * for(int i=0;i < list1.size(); i++){ data2 =
                 * (ProjectData)list1.get(i); v_course = data2.getCourse();
                 * v_courseseq = data2.getCourseseq();
                 * if(!v_course.equals("000000") && !(v_Bcourse.equals(v_course)
                 * && v_Bcourseseq.equals(v_courseseq))){ sql2 =
                 * "select count(subj) cnt from VZ_SCSUBJSEQ "; sql2+=
                 * "where course = '" + v_course +
                 * "' and courseseq = '"+v_courseseq+"' "; if
                 * (!ss_grcode.equals("ALL")) { sql2+= " and grcode = '" +
                 * ss_grcode + "'"; } if (!ss_gyear.equals("ALL")) { sql2+=
                 * " and gyear = '" + ss_gyear + "'"; } if
                 * (!ss_grseq.equals("ALL")) { sql2+= " and grseq = '" +
                 * ss_grseq + "'"; } if (!ss_uclass.equals("ALL")) { sql2+=
                 * " and scupperclass = '" + ss_uclass + "'"; }
                 * 
                 * if (!ss_mclass.equals("ALL")) { sql2+=
                 * " and scmiddleclass = '" + ss_mclass + "'"; }
                 * 
                 * if (!ss_lclass.equals("ALL")) { sql2+=
                 * " and sclowerclass = '" + ss_lclass + "'"; } if
                 * (!ss_subjcourse.equals("ALL")) { sql2+= " and scsubj = '" +
                 * ss_subjcourse + "'"; } if (!ss_subjseq.equals("ALL")) {
                 * sql2+= " and scsubjseq = '" + ss_subjseq + "'"; } ls2 =
                 * connMgr.executeQuery(sql2); if(ls2.next()){
                 * data2.setRowspan(ls2.getInt("cnt"));
                 * data2.setIsnewcourse("Y"); } }else{ data2.setRowspan(0);
                 * data2.setIsnewcourse("N"); } v_Bcourse = v_course;
                 * v_Bcourseseq= v_courseseq; list2.add(data2); if(ls2 != null)
                 * { try { ls2.close(); }catch (Exception e) {} } }
                 */
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
        return list1;
    }

    /**
     * 리포트 출제리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<ProjectData> selectProjectQuestionsList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<ProjectData> list = null;
        String sql = "";
        ProjectData data = null;
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<ProjectData>();

            //sql+= "(select count(*) from TZ_PROJGRP where subj=A.subj and year=A.year "; 11/26 tz_projgrp 용도 알아낼 것
            // sql+= "and subjseq=A.subjseq and ordseq=A.ordseq) as grcnt,A.groupcnt ";
            sql = "select   A.projseq, ";
            sql += "		 A.ordseq,";
            sql += "		 isnull(A.lesson,'') lesson,";
            sql += "		 isnull((select sdesc from tz_subjlesson where subj = a.subj and lesson = a.lesson),'') lessonnm,";
            sql += "		 A.reptype,";
            sql += "		 A.isopen,";
            sql += "		 A.isopenscore,";
            sql += "		 A.title,";
            sql += "		 A.score,";
            sql += "		 isnull(A.expiredate,'') expiredate, ";
            sql += "		 isnull(A.deadlinesdate,'') deadlinesdate, ";
            sql += "		 isnull(A.deadlineedate,'') deadlineedate, ";
            sql += "		 (select count(*) from TZ_STUDENT where subj=A.subj and year=A.year and subjseq=A.subjseq) as tocnt, ";
            sql += "		 99 as grcnt,";
            sql += "		 A.groupcnt, ";
            sql += "		 isusedcopy, ";
            sql += "		 (select count(*) from TZ_PROJORD where subj=A.subj and year=A.year and subjseq=A.subjseq and projseq=a.projseq) as rowspan,	 ";
            sql += "		 (select min(ordseq) from tz_projord where subj=a.subj and year=a.year and subjseq=a.subjseq and projseq=a.projseq ) rowspanseq, ";
            sql += "		 A.upfile2, ";
            sql += "		A.useyn,  ";
            sql += "		 (select eduend from tz_subjseq x where x.subj=a.subj and x.year = a.year and x.subjseq = a.subjseq ) eduend					 ";
            sql += "from	 TZ_PROJORD A ";
            sql += "where	A.subj='" + v_subj + "' and A.year='" + v_year + "' and A.subjseq='" + v_subjseq + "' ";
            sql += "order by a.projseq,A.ordseq";

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new ProjectData();
                data.setProjseq(ls.getInt("projseq"));
                data.setOrdseq(ls.getInt("ordseq"));
                data.setLesson(ls.getString("lesson"));
                data.setLessonnm(ls.getString("lessonnm"));
                data.setReptype(ls.getString("reptype"));
                data.setIsopen(ls.getString("isopen"));
                data.setIsopenscore(ls.getString("isopenscore"));
                data.setTitle(ls.getString("title"));
                data.setScore(ls.getInt("score"));
                data.setTocnt(ls.getString("tocnt"));
                data.setGrcnt(ls.getString("grcnt"));
                data.setExpiredate(ls.getString("expiredate"));
                data.setDeadlinesdate(ls.getString("deadlinesdate"));
                data.setDeadlineedate(ls.getString("deadlineedate"));
                data.setGroupcnt(ls.getString("groupcnt"));
                data.setRowspan(ls.getInt("rowspan"));
                data.setIsusedcopy(ls.getString("isusedcopy"));
                data.setRowspanseq(ls.getInt("rowspanseq"));

                data.setUpfile2(ls.getString("upfile2"));
                data.setUseyn(ls.getString("useyn"));
                data.setEduend(ls.getString("eduend"));
                list.add(data);
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
     * 리포트 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertProjectQuestions(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        String v_user_id = box.getSession("userid");
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        int v_projseq = box.getInt("p_projseq"); //리포트차수
        String v_lesson = box.getString("p_lesson"); //일차
        String v_reptype = box.getString("p_reptype");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        String v_expiredate = box.getString("p_expiredate");
        String v_score = box.getString("p_score");
        String v_realFileName1 = box.getRealFileName("p_file1");
        String v_newFileName1 = box.getNewFileName("p_file1");
        String v_realFileName2 = box.getRealFileName("p_file2");
        String v_newFileName2 = box.getNewFileName("p_file2");
        int v_groupcnt = box.getInt("p_groupcnt");
        String v_ansyn = box.getString("ansyn"); // 답안제출옵션
        String v_useyn = box.getString("useyn"); // 사용여부
        int v_max = 0;
        int v_ordseq = 0;

        String v_deadlinesdate = box.getString("p_deadlinesdate");
        String v_deadlineedate = box.getString("p_deadlineedate");
        v_expiredate = v_deadlineedate;

        try {
            connMgr = new DBConnectionManager();
            sql1 = "select max(ordseq) from TZ_PROJORD ";
            sql1 += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "'";
            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                v_max = ls.getInt(1);
                if (v_max > 0) {
                    v_ordseq = v_max + 1;
                } else {
                    v_ordseq = 1;
                }
            }

            sql2 = "insert into TZ_PROJORD(subj,year,subjseq,ordseq,projseq,lesson,reptype,";
            sql2 += "expiredate,deadlinesdate,deadlineedate,title,contents,score,upfile,upfile2,realfile,realfile2,luserid,groupcnt,ldate,ansyn,useyn) ";
            sql2 += "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'),?,?)";
            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_subj);
            pstmt2.setString(2, v_year);
            pstmt2.setString(3, v_subjseq);
            pstmt2.setInt(4, v_ordseq);
            pstmt2.setInt(5, v_projseq);
            pstmt2.setString(6, v_lesson);
            pstmt2.setString(7, v_reptype);
            pstmt2.setString(8, v_expiredate);
            pstmt2.setString(9, v_deadlinesdate);
            pstmt2.setString(10, v_deadlineedate);
            pstmt2.setString(11, v_title);
            pstmt2.setString(12, v_contents);
            pstmt2.setString(13, v_score);
            pstmt2.setString(14, v_newFileName1);
            pstmt2.setString(15, v_newFileName2);
            pstmt2.setString(16, v_realFileName1);
            pstmt2.setString(17, v_realFileName2);
            pstmt2.setString(18, v_user_id);
            pstmt2.setInt(19, v_groupcnt);
            pstmt2.setString(20, v_ansyn);
            pstmt2.setString(21, v_useyn);
            isOk = pstmt2.executeUpdate();

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
     * 리포트 이전차수 복사
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int copyProjectQuestions(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;

        PreparedStatement pstmt1 = null;
        PreparedStatement pstmt2 = null;

        String sql1 = "";
        String sql2 = "";

        int isOk1 = 0;
        int isOk2 = 0;

        String v_user_id = box.getSession("userid");

        String v_grcode = box.getString("s_grcode"); //교육주관
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        String v_eduend = box.getString("p_eduend"); //교육기간마감일
        String v_edustart = "";

        String isusedcopy = "N";

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);////

            // 2008.09.23 리포트 출제기간 추가.
            sql1 = "SELECT (case isnull(edustart,'-') when '-' then '' else substring(edustart,1,8) end) As edustart ";
            sql1 += "   , (case isnull(eduend,'-') when '-' then '' else substring(eduend,1,8) end) As eduend ";
            sql1 += " FROM tz_subjseq WHERE subj='" + v_subj + "' AND year='" + v_year + "' AND subjseq='" + v_subjseq + "' ";
            ls = connMgr.executeQuery(sql1);
            if (ls.next()) {
                v_edustart = ls.getString("edustart");
                v_eduend = ls.getString("eduend");
            }
            ls.close();

            sql1 = "select isusedcopy from TZ_PROJORD ";
            sql1 += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "'";
            ls = connMgr.executeQuery(sql1);
            if (ls.next()) {
                isusedcopy = ls.getString("isusedcopy");
            }
            ls.close();

            if (isusedcopy.equals("Y")) {
                return -1;
            }

            sql1 = "insert into tz_projord( ";
            sql1 += "			subj,year,subjseq,ordseq,projseq,lesson,groupcnt,reptype,isopen, ";
            sql1 += "			isopenscore,title,contents,score,expiredate,deadlinesdate,deadlineedate,upfile,upfile2,isusedcopy,luserid, ldate, realfile, realfile2, upfilezise, upfilesize2, ansyn, useyn) ";
            sql1 += "select	  subj,year,?	  ,ordseq,projseq,lesson,groupcnt,reptype,isopen, "; //1
            sql1 += "			isopenscore,title,contents,score,?,?,?,upfile,upfile2,'N',?, to_char(sysdate,'YYYYMMDDHH24MISS'), realfile, realfile2, upfilezise, upfilesize2, ansyn, useyn "; //2,3
            sql1 += "from		tz_projord	  ";
            sql1 += "where					   ";
            sql1 += "			useyn!='N'  and "; // 2005.08.18 추가
            sql1 += "			subj	= ? and "; //4
            sql1 += "			year	= ? and "; //5
            sql1 += "			subjseq = ( select  max(subjseq) ";
            sql1 += "						from	tz_subjseq ";
            sql1 += "						where   subj   = ? and "; //6
            sql1 += "								year   = ? and "; //7
            sql1 += "								grcode = ? and "; //8
            sql1 += "								subjseq< ? ) "; //9

            pstmt1 = connMgr.prepareStatement(sql1);

            pstmt1.setString(1, v_subjseq);
            pstmt1.setString(2, StringManager.replace(v_eduend, "/", ""));
            pstmt1.setString(3, v_edustart);
            pstmt1.setString(4, v_eduend);
            pstmt1.setString(5, v_user_id);
            pstmt1.setString(6, v_subj);
            pstmt1.setString(7, v_year);
            pstmt1.setString(8, v_subj);
            pstmt1.setString(9, v_year);
            pstmt1.setString(10, v_grcode);
            pstmt1.setString(11, v_subjseq);

            isOk1 = pstmt1.executeUpdate();

            sql2 = "update  tz_projord ";
            sql2 += "set	 isusedcopy = 'Y' ";
            sql2 += "where   subj = ? and year = ? and subjseq = ? ";

            pstmt2 = connMgr.prepareStatement(sql2);

            pstmt2.setString(1, v_subj);
            pstmt2.setString(2, v_year);
            pstmt2.setString(3, v_subjseq);

            isOk2 = pstmt2.executeUpdate();

            if (isOk1 == isOk2) {
                connMgr.commit();
            } else {
                connMgr.rollback();
            }

            pstmt1.close();
            pstmt2.close();

        } catch (Exception ex) {
            if (connMgr != null) {
                try {
                    connMgr.rollback();
                } catch (Exception e10) {
                }
            }
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
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
        return isOk2;
    }

    /**
     * 리포트 수정 페이지
     * 
     * @param box receive from the form object and session
     * @return ProjectData
     */
    public ProjectData updateProjectQuestionsPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ProjectData data = null;
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        String v_lesson = box.getString("p_lesson"); //일차
        int v_ordseq = box.getInt("p_ordseq");

        try {
            connMgr = new DBConnectionManager();

            //select reptype,expiredate,title,contents,score,groupcnt,upfile,upfile2
            sql = "select projseq,reptype,isnull(expiredate,'') As expiredate,isnull(deadlinesdate,'') As deadlinesdate,isnull(deadlineedate,'') As deadlineedate, title,contents,score,groupcnt,upfile,upfile2,realfile,realfile2 ";
            sql += "	   ,ansyn, useyn ";
            sql += "from TZ_PROJORD ";
            sql += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";
            sql += "and ordseq='" + v_ordseq + "' and lesson='" + v_lesson + "'";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new ProjectData();
                data.setProjseq(ls.getInt("projseq"));

                System.out.println("What?1111->" + ls.getInt("projseq"));
                System.out.println("What?1111->" + data.getProjseq());

                data.setReptype(ls.getString("reptype"));
                data.setExpiredate(ls.getString("expiredate"));
                data.setDeadlinesdate(ls.getString("deadlinesdate"));
                data.setDeadlineedate(ls.getString("deadlineedate"));
                data.setTitle(ls.getString("title"));
                data.setContents(ls.getString("contents"));
                data.setScore(ls.getInt("score"));
                data.setGroupcnt(ls.getString("groupcnt"));
                data.setUpfile(ls.getString("upfile"));
                data.setUpfile2(ls.getString("upfile2"));
                data.setRealfile(ls.getString("realfile"));
                data.setRealfile2(ls.getString("realfile2"));
                data.setAnsyn(ls.getString("ansyn"));
                data.setUseyn(ls.getString("useyn"));
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
        return data;
    }

    /**
     * 리포트 수정
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateProjectQuestions(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        String sql = "";
        ListSet ls = null;
        int isOk = 0;
        String v_user_id = box.getSession("userid");
        String v_reptype = box.getString("p_reptype");
        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        String v_expiredate = box.getString("p_expiredate");
        String v_isopen = box.getString("p_isopen");
        String v_isopenscore = box.getString("p_isopenscore");
        String v_realFileName1 = box.getRealFileName("p_file1");
        String v_newFileName1 = box.getNewFileName("p_file1");
        String v_realFileName2 = box.getRealFileName("p_file2");
        String v_newFileName2 = box.getNewFileName("p_file2");
        String v_upfile = box.getString("p_upfile");
        String v_upfile2 = box.getString("p_upfile2");
        String v_realfile = box.getString("p_realfile");
        String v_realfile2 = box.getString("p_realfile2");
        String v_check1 = box.getString("p_check1"); //첨부파일1 이전파일 삭제
        String v_check2 = box.getString("p_check2"); //첨부파일2 아전파일 삭제

        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        String v_lesson = box.getString("p_lesson"); //일차

        //		String v_upfilesize		 = "";
        //		String v_upfilesize2		= "";
        String v_ansyn = box.getString("ansyn"); // 답안제출옵션
        String v_useyn = box.getString("useyn"); // 사용여부

        int v_score = box.getInt("p_score");
        int v_groupcnt = box.getInt("p_groupcnt");
        int v_ordseq = box.getInt("p_ordseq");
        if (v_newFileName1.length() == 0) {
            v_newFileName1 = v_upfile;
        }
        if (v_newFileName2.length() == 0) {
            v_newFileName2 = v_upfile2;
        }

        //기존 파일정보
        String v_oldupfile = v_upfile;
        String v_oldrealfile = v_realfile;
        String v_oldupfile2 = v_upfile2;
        String v_oldrealfile2 = v_realfile2;

        String v_deadlinesdate = box.getString("p_deadlinesdate");
        String v_deadlineedate = box.getString("p_deadlineedate");
        v_expiredate = v_deadlineedate;

        try {
            connMgr = new DBConnectionManager();

            sql = "select  count(userid)  ";
            sql += "from	TZ_PROJASSIGN ";
            sql += "where   subj='" + v_subj + "' and year='" + v_year + "' and ";
            sql += "		subjseq='" + v_subjseq + "' and ordseq = " + v_ordseq;

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                if (ls.getInt(1) != 0) {
                    isOk = -1; //배정된 학습자가 있어 삭제할 수 없습니다.
                } else {

                    //업로드한 파일이 없을 경우
                    if (v_realFileName1.equals("")) {
                        //기존파일 삭제
                        if (v_check1.equals("Y")) {
                            v_newFileName1 = "";
                            v_realFileName1 = "";
                        } else {
                            //기존파일 유지
                            v_newFileName1 = v_oldupfile;
                            v_realFileName1 = v_oldrealfile;
                        }
                    }

                    //업로드한 파일이 없을 경우
                    if (v_realFileName2.equals("")) {
                        //기존파일 삭제
                        if (v_check2.equals("Y")) {
                            v_newFileName2 = "";
                            v_realFileName2 = "";
                        } else {
                            //기존파일 유지
                            v_newFileName2 = v_oldupfile2;
                            v_realFileName2 = v_oldrealfile2;
                        }
                    }

                    sql = "update TZ_PROJORD set lesson=?,reptype=?,expiredate=?,deadlinesdate=?,deadlineedate=?,title=?,groupcnt=?,";
                    sql += "contents=?,score=?,upfile=?,upfile2=?,realfile=?,realfile2=?,luserid=?,ldate=to_char(sysdate,'YYYYMMDDHH24MISS'), ";
                    sql += "isopen=?,isopenscore=?, ansyn=?, useyn=? ";
                    sql += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";
                    sql += "and ordseq='" + v_ordseq + "' ";

                    pstmt = connMgr.prepareStatement(sql);
                    // System.out.println("sql==========>"+sql);
                    pstmt.setString(1, v_lesson);
                    pstmt.setString(2, v_reptype);
                    pstmt.setString(3, v_expiredate);
                    pstmt.setString(4, v_deadlinesdate);
                    pstmt.setString(5, v_deadlineedate);
                    pstmt.setString(6, v_title);
                    pstmt.setInt(7, v_groupcnt);
                    pstmt.setString(8, v_contents);
                    pstmt.setInt(9, v_score);
                    pstmt.setString(10, v_newFileName1);
                    pstmt.setString(11, v_newFileName2);
                    pstmt.setString(12, v_realFileName1);
                    pstmt.setString(13, v_realFileName2);
                    pstmt.setString(14, v_user_id);
                    pstmt.setString(15, v_isopen);
                    pstmt.setString(16, v_isopenscore);
                    pstmt.setString(17, v_ansyn);
                    pstmt.setString(18, v_useyn);
                    isOk = pstmt.executeUpdate();

                }
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
     * 리포트 그룹별 등록/수정 페이지
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<ProjectData> handlingProjectGroupPage(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        PreparedStatement pstmt2 = null;
        ResultSet rs = null;
        ArrayList<ProjectData> list = null;
        ProjectData data = null;
        String sql1 = "";
        String sql2 = "";
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        String v_userid = "";
        int v_ordseq = box.getInt("p_ordseq"); //출제번호
        //		int	 v_groupcnt	  = box.getInt("p_groupcnt");		 //그룹갯수
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<ProjectData>();
            //select userid,cono,name,jikwinm,companynm,gpmnm,deptnm
            sql1 = "select A.userid,A.cono,A.name,A.jikwinm,B.companynm,B.gpmnm,B.deptnm ";
            sql1 += "from TZ_MEMBER A,TZ_COMP B,TZ_STUDENT C ";
            sql1 += "where C.subj='" + v_subj + "' and C.year='" + v_year + "' and C.subjseq='" + v_subjseq + "' ";
            sql1 += "and A.userid=C.userid and A.comp=B.comp ";
            ls = connMgr.executeQuery(sql1);
            // System.out.println("sql1=============>"+sql1);

            //projgrp,projname,chief
            sql2 = "select projid projgrp,projname,chief from TZ_PROJGRP ";
            sql2 += "where subj=? and year=? and subjseq=? ";
            sql2 += "and ordseq=? and userid=?";

            pstmt2 = connMgr.prepareStatement(sql2);
            // System.out.println("sql2============>"+sql2);

            while (ls.next()) {
                data = new ProjectData();
                v_userid = ls.getString("userid");
                try {
                    pstmt2.setString(1, v_subj);
                    pstmt2.setString(2, v_year);
                    pstmt2.setString(3, v_subjseq);
                    pstmt2.setInt(4, v_ordseq);
                    pstmt2.setString(5, v_userid);
                    rs = pstmt2.executeQuery();
                    if (rs.next()) {
                        data.setProjgrp(rs.getString("projgrp"));
                        data.setProjname(rs.getString("projname"));
                        data.setChief(rs.getString("chief"));
                    }
                } catch (Exception ex) {
                } finally {
                    if (rs != null) {
                        try {
                            rs.close();
                        } catch (Exception e) {
                        }
                    }
                }
                data.setUserid(ls.getString("userid"));
                data.setCono(ls.getString("cono"));
                data.setName(ls.getString("name"));
                data.setJikwinm(ls.getString("jikwinm"));
                data.setCompanynm(ls.getString("companynm"));
                data.setGpmnm(ls.getString("gpmnm"));
                data.setDeptnm(ls.getString("deptnm"));
                list.add(data);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
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
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return list;
    }

    /**
     * 리포트 그룹별 등록/수정
     * 
     * @param box receive from the form object and session
     * @return int
     */

    @SuppressWarnings("unchecked")
    public int handlingProjectGroup(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        int isOk1 = 0;
        int isOk2 = 0;
        String v_subj = box.getString("p_subj"); //과정
        String v_subjnm = box.getString("p_subjnm"); //과정명
        String v_year = box.getString("p_year"); //년도
        String v_lesson = box.getString("p_lesson"); //일차
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        String v_projgrp = box.getString("p_projgrp"); //그룹코드
        String v_projname = v_subjnm + v_subjseq + "과정" + v_lesson + "일차" + v_projgrp + "그룹"; //그룹명
        int v_ordseq = box.getInt("p_ordseq");
        //p_grouping 넘어온 다수의 value를 처리하기 위해 vector로 구현
        Vector v_grouping = new Vector();
        v_grouping = box.getVector("p_grouping");
        Enumeration em = v_grouping.elements();
        String v_userid = ""; //실제 넘어온 각각의 value
        String v_chief = box.getString("p_chief");
        String v_user_id = box.getSession("userid");
        try {
            connMgr = new DBConnectionManager();

            //insert TZ_PROJGRP table
            sql2 = "insert into TZ_PROJGRP(subj,year,subjseq,ordseq,userid,projid,projname,chief,luserid,ldate) ";
            sql2 += " values(?,?,?,?,?,?,?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'))";
            //System.out.println("sql2================>"+sql2);
            pstmt2 = connMgr.prepareStatement(sql2);

            //delete TZ_PROJGRP
            sql1 = "delete from TZ_PROJGRP ";
            sql1 += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";
            sql1 += "and ordseq='" + v_ordseq + "' and projid='" + v_projgrp + "'";
            isOk1 = connMgr.executeUpdate(sql1);
            // System.out.println("sql1================>"+sql1);

            while (em.hasMoreElements()) {
                v_userid = (String) em.nextElement();

                pstmt2.setString(1, v_subj);
                pstmt2.setString(2, v_year);
                pstmt2.setString(3, v_subjseq);
                pstmt2.setInt(4, v_ordseq);
                pstmt2.setString(5, v_userid);
                pstmt2.setString(6, v_projgrp);
                pstmt2.setString(7, v_projname);
                pstmt2.setString(8, v_chief);
                pstmt2.setString(9, v_user_id);
                //System.out.println(v_subj+","+v_year+","+v_subjseq+","+v_ordseq+","+v_userid);
                //System.out.println(v_projgrp+","+v_projname+","+v_chief+","+v_user_id);
                isOk2 = pstmt2.executeUpdate();
            }
        } catch (Exception ex) {
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
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
        return isOk1 + isOk2;
    }

    /**
     * 리포트평가관리 리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<DataBox> selectProjectSubmitAList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        ArrayList<DataBox> list1 = null;
        //		ArrayList list2	 = null;
        String sql1 = "";
        //		String sql2		 = "";
        DataBox dbox = null;
        //		String  v_Bcourse   = ""; //이전코스
        //		String  v_course	= ""; //현재코스
        //		String  v_Bcourseseq= ""; //이전코스차수
        //		String  v_courseseq = ""; //현재코스차수
        //		int	 l		   = 0;
        String ss_grcode = box.getStringDefault("s_grcode", "ALL"); //교육그룹
        String ss_gyear = box.getStringDefault("s_gyear", "ALL"); //년도
        String ss_grseq = box.getStringDefault("s_grseq", "ALL"); //교육차수

        String ss_uclass = box.getStringDefault("s_upperclass", "ALL"); //과정분류
        String ss_mclass = box.getStringDefault("s_middleclass", "ALL"); //과정분류
        String ss_lclass = box.getStringDefault("s_lowerclass", "ALL"); //과정분류

        String ss_subjcourse = box.getStringDefault("s_subjcourse", "ALL");//과정&코스
        String ss_subjseq = box.getStringDefault("s_subjseq", "ALL"); //과정 차수
        String ss_action = box.getString("s_action");

        String v_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");
        String v_gadmin = StringManager.substring(s_gadmin, 0, 1);

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        // 페이징
        int v_pageno = box.getInt("p_pageno");
        if (v_pageno == 0)
            v_pageno = 1;

        try {
            if (ss_action.equals("go")) {
                connMgr = new DBConnectionManager();
                list1 = new ArrayList<DataBox>();
                //				list2 = new ArrayList();

                if (v_gadmin.equals("P")) { //		  강사권한 경우 해당 클래스의 학습자 리스트만
                    sql1 = "select a.course,a.cyear,a.courseseq,a.coursenm,a.subj,a.year,a.subjseq,a.subjseqgr,a.isclosed,a.subjnm,a.isonoff, A.isbelongcourse,A.subjcnt, ";
                    sql1 += "		(select count(*) from TZ_PROJREP where subj=A.subj and year=A.year ";
                    sql1 += "		and subjseq=A.subjseq) as pjcnt, a.subjcnt, a.isbelongcourse, ";
                    sql1 += "		(select count(*) from TZ_PROJREP where subj=A.subj and year=A.year ";
                    sql1 += "		and subjseq=A.subjseq and isfinal='N') as micnt ";
                    sql1 += " from   VZ_SCSUBJSEQ  A ,";
                    sql1 += "		(select subj ";
                    sql1 += "		 from   tz_subjman ";
                    sql1 += "		 where  substring(gadmin,1,1) = 'P' and userid = '" + v_userid + "' ) B ";
                    sql1 += " where  a.subj = b.subj  ";
                } else {
                    sql1 = "select a.course,a.cyear,a.courseseq,a.coursenm,a.subj,a.year,a.subjseq,a.subjseqgr,a.isclosed,a.subjnm,a.isonoff, A.isbelongcourse,A.subjcnt, ";
                    sql1 += "		(select count(*) from TZ_PROJREP where subj=A.subj and year=A.year ";
                    sql1 += "		and subjseq=A.subjseq) as pjcnt, ";
                    sql1 += "		(select count(*) from TZ_PROJREP where subj=A.subj and year=A.year ";
                    sql1 += "		and subjseq=A.subjseq and isfinal='N') as micnt ";
                    sql1 += " from VZ_SCSUBJSEQ  A where 1=1 ";

                }
                if (!ss_grcode.equals("ALL")) {
                    sql1 += " and a.grcode = '" + ss_grcode + "'";
                }
                if (!ss_gyear.equals("ALL")) {
                    sql1 += " and a.gyear = '" + ss_gyear + "'";
                }
                if (!ss_grseq.equals("ALL")) {
                    sql1 += " and a.grseq = '" + ss_grseq + "'";
                }

                if (!ss_uclass.equals("ALL")) {
                    sql1 += " and a.scupperclass = '" + ss_uclass + "'";
                }
                if (!ss_mclass.equals("ALL")) {
                    sql1 += " and a.scmiddleclass = '" + ss_mclass + "'";
                }
                if (!ss_lclass.equals("ALL")) {
                    sql1 += " and a.sclowerclass = '" + ss_lclass + "'";
                }

                if (!(ss_subjcourse.equals("ALL") || ss_subjcourse.equals("----"))) {
                    sql1 += " and a.scsubj = '" + ss_subjcourse + "'";
                }
                if (!ss_subjseq.equals("ALL")) {
                    sql1 += " and a.scsubjseq = '" + ss_subjseq + "'";
                }

                if (v_orderColumn.equals("")) {
                    sql1 += " order by a.course, a.cyear, a.courseseq, a.subj, a.year, a.subjseq ";
                } else {
                    sql1 += " order by a.course," + v_orderColumn + v_orderType;
                }

                System.out.println("sql1 = " + sql1);

                ls1 = connMgr.executeQuery(sql1);

                //페이징
                ls1.setPageSize(row); //  페이지당 row 갯수를 세팅한다
                ls1.setCurrentPage(v_pageno); //     현재페이지번호를 세팅한다.
                int total_page_count = ls1.getTotalPage(); //     전체 페이지 수를 반환한다
                int total_row_count = ls1.getTotalCount(); //     전체 row 수를 반환한다

                while (ls1.next()) {
                    dbox = ls1.getDataBox();

                    //페이징
                    dbox.put("d_dispnum", new Integer(total_row_count - ls1.getRowNum() + 1));
                    dbox.put("d_totalpage", new Integer(total_page_count));
                    dbox.put("d_rowcount", new Integer(row));

                    list1.add(dbox);
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
        return list1;
    }

    /**
     * 리포트 평가리스트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<ProjectData> selectProjectSubmitList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<ProjectData> list = null;
        String sql = "";
        ProjectData data = null;
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        String v_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");
        String v_gadmin = StringManager.substring(s_gadmin, 0, 1);

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<ProjectData>();

            if (v_gadmin.equals("P")) { //		  강사권한 경우 해당 클래스의 학습자 리스트만
                sql = "select   A.projseq,\n";
                sql += "		 A.ordseq,\n";
                sql += "		 A.lesson,\n";
                sql += "		 A.reptype,\n";
                sql += "		 A.isopen,\n";
                sql += "		 A.isopenscore,\n";
                sql += "		 A.title,\n";
                sql += "		 A.score, \n";
                sql += "		(select  count(projid) \n";
                sql += "		 from	TZ_PROJREP \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 isfinal =   'N' and \n";
                sql += "				 ordseq  =   A.ordseq and \n";
                sql += "				 projid  in (select userid \n";
                sql += "							 from	tz_student \n";
                sql += "							 where   subj = a.subj and \n";
                sql += "									 year = a.year and \n";
                sql += "									 subjseq = a.subjseq and \n";
                sql += "									 class = b.class \n";
                sql += "							 ) \n";
                sql += "		 ) as tutormicnt, \n"; //강사별  미평가수

                sql += "		(select  count(projid) \n";
                sql += "		 from	TZ_PROJREP \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 isfinal =   'N' and \n";
                sql += "				 ordseq  =   A.ordseq) as micnt, \n"; //과정별 전체 미평가수

                sql += "		(select  count(projid) \n";
                sql += "		 from	TZ_PROJREP \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 isfinal =   'Y' and \n";
                sql += "				 ordseq  =   A.ordseq and \n";
                sql += "				 projid  in (select userid \n";
                sql += "							 from	tz_student \n";
                sql += "							 where   subj = a.subj and \n";
                sql += "									 year = a.year and \n";
                sql += "									 subjseq = a.subjseq and \n";
                sql += "									 class = b.class \n";
                sql += "							 ) \n";
                sql += "		 ) as tutorpgcnt, \n"; //강사별 평가수

                sql += "		(select  count(projid) \n";
                sql += "		 from	TZ_PROJREP \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 isfinal =   'Y' and \n";
                sql += "				 ordseq  =   A.ordseq) as pgcnt, \n";//과정별 전체 평가수

                sql += "		 (select count(userid) \n";
                sql += "		 from	tz_projassign \n";
                sql += "		 where   subj = a.subj and \n";
                sql += "				 year = a.year and \n";
                sql += "				 subjseq = a.subjseq and \n";
                sql += "				 ordseq  = a.ordseq and \n";
                sql += "				 userid in ( select userid \n";
                sql += "							 from	tz_student \n";
                sql += "							 where   subj = a.subj and \n";
                sql += "									 year = a.year and \n";
                sql += "									 subjseq = a.subjseq and \n";
                sql += "									 class = b.class \n";
                sql += "						   ) \n";
                sql += "		 ) as tutorassigncnt, \n";//강사별 배정수

                sql += "		(select  count(userid) \n";
                sql += "		 from	TZ_PROJASSIGN \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 ordseq  =   A.ordseq) as assigncnt, \n";//과정별 전체 배정수

                sql += "		(select  count(projid) \n";
                sql += "		 from	TZ_PROJREP \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 ordseq  =   A.ordseq and \n";
                sql += "				 projid  in (select userid \n";
                sql += "							 from	tz_student \n";
                sql += "							 where   subj = a.subj and \n";
                sql += "									 year = a.year and \n";
                sql += "									 subjseq = a.subjseq and \n";
                sql += "									 class = b.class \n";
                sql += "							 ) \n";
                sql += "		 ) as tutorjccnt, \n";//강사별 제출수

                sql += "		(select  count(projid) \n";
                sql += "		 from	TZ_PROJREP \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 ordseq  =   A.ordseq) as jccnt, \n";//과정별 전체 제출수

                sql += "		(select  count(*) \n";
                sql += "		 from	TZ_PROJORD \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 projseq =   A.projseq) as rowspan, \n";
                sql += "		(select  min(ordseq) \n";
                sql += "		 from	tz_projord ";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 projseq =   A.projseq ) rowspanseq \n";
                sql += "from	 TZ_PROJORD A, \n";
                sql += "		(select subj, year, subjseq, class \n";
                sql += "		 from   tz_classtutor \n";
                sql += "		 where  subj = '" + v_subj + "' and \n";
                sql += "			 year = '" + v_year + "' and \n";
                sql += "			 subjseq = '" + v_subjseq + "' and \n";
                sql += "				tuserid = '" + v_userid + "' ) B \n";
                sql += " where  a.subj = b.subj and a.year = b.year and a.subjseq = b.subjseq and \n";
                sql += "	 A.subj	  = '" + v_subj + "' and \n";
                sql += "		 A.year	  = '" + v_year + "' and \n";
                sql += "		 A.subjseq   = '" + v_subjseq + "' \n";
                sql += "order by A.projseq, a.ordseq,A.lesson \n";

            } else {
                sql = "select   A.projseq,\n";
                sql += "		 A.ordseq,\n";
                sql += "		 A.lesson,\n";
                sql += "		 A.reptype,\n";
                sql += "		 A.isopen,\n";
                sql += "		 A.isopenscore,\n";
                sql += "		 A.title,\n";
                sql += "		 A.score, \n";
                sql += "		(select  count(*) \n";
                sql += "		 from	TZ_PROJREP \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 isfinal =   'N' and \n";
                sql += "				 ordseq  =   A.ordseq) as micnt, \n"; //미평가수
                sql += "		(select  count(*) \n";
                sql += "		 from	TZ_PROJREP \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 isfinal =   'Y' and \n";
                sql += "				 ordseq  =   A.ordseq) as pgcnt, \n";//평가수
                sql += "		(select  count(*) \n";
                sql += "		 from	TZ_PROJASSIGN \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 ordseq  =   A.ordseq) as assigncnt, \n";//배정수
                sql += "		(select  count(*) \n";
                sql += "		 from	TZ_PROJREP \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 ordseq  =   A.ordseq) as jccnt, \n";//제출수
                sql += "		(select  count(*) \n";
                sql += "		 from	TZ_PROJORD \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 projseq =   A.projseq) as rowspan, \n";
                sql += "		(select  min(ordseq) \n";
                sql += "		 from	tz_projord \n";
                sql += "		 where   subj	=   A.subj and \n";
                sql += "				 year	=   A.year and \n";
                sql += "				 subjseq =   A.subjseq and \n";
                sql += "				 projseq =   A.projseq ) rowspanseq \n";
                sql += "from	 TZ_PROJORD A \n";
                sql += "where	A.subj	  = '" + v_subj + "' and \n";
                sql += "		 A.year	  = '" + v_year + "' and \n";
                sql += "		 A.subjseq   = '" + v_subjseq + "' \n";
                sql += "order by A.projseq, a.ordseq, A.lesson \n";
            }

            System.out.println("sql = " + sql);

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new ProjectData();
                data.setProjseq(ls.getInt("projseq"));
                data.setOrdseq(ls.getInt("ordseq"));
                data.setLesson(ls.getString("lesson"));
                data.setReptype(ls.getString("reptype"));
                data.setIsopen(ls.getString("isopen"));
                data.setIsopenscore(ls.getString("isopenscore"));
                data.setTitle(ls.getString("title"));
                data.setScore(ls.getInt("score"));

                if (v_gadmin.equals("P")) {
                    //data.setMicnt(ls.getString("tutormicnt") + "/" + ls.getString("micnt"));
                    //data.setPgcnt(ls.getString("tutorpgcnt") + "/" + ls.getString("pgcnt"));
                    //data.setJccnt(ls.getString("tutorjccnt") + "/" + ls.getString("jccnt"));
                    //data.setAssigncnt(ls.getString("tutorassigncnt") + "/" + ls.getString("assigncnt"));
                    data.setMicnt(ls.getString("tutormicnt"));
                    data.setPgcnt(ls.getString("tutorpgcnt"));
                    data.setJccnt(ls.getString("tutorjccnt"));
                    data.setAssigncnt(ls.getString("tutorassigncnt"));
                } else {
                    data.setMicnt(ls.getString("micnt"));
                    data.setPgcnt(ls.getString("pgcnt"));
                    data.setJccnt(ls.getString("jccnt"));
                    data.setAssigncnt(ls.getString("assigncnt"));
                }
                data.setRowspan(ls.getInt("rowspan"));
                data.setRowspanseq(ls.getInt("rowspanseq"));
                list.add(data);
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
     * 리포트 리스트 전체 - 전체 리포트
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<DataBox> selectProjectDetailListAll(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls1 = null;
        ArrayList<DataBox> list1 = null;
        String sql = "";
        //		ProjectData data1   = null;
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수

        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서
        String v_iscopy = box.getString("p_iscopy"); //모사답안 검색 유무

        try {
            connMgr = new DBConnectionManager();
            list1 = new ArrayList<DataBox>();

            sql = "SELECT a.projseq, a.ordseq, b.seq,  nvl(b.resend,0) resend, nvl(b.copyupnm,'N') copyupnm, nvl(b.copysize,'N') copysize, nvl(b.copybyte,'N') copybyte, ";
            sql += "	  (SELECT title FROM tz_projord ";
            sql += "	   WHERE subj = a.subj and year = a.year and subjseq = a.subjseq and ordseq=a.ordseq ) assigntitle, ";
            sql += "	   c.deptnam, ";
            sql += "	   a.userid projgrp,  c.name projname,  ";
            sql += "	   b.title, b.realfile, b.upfile, b.upfilesize, b.contentsbyte, b.indate, b.score, nvl(b.isret,'N') isret, b.ldate, b.retdate, ";
            sql += "	  (SELECT tuserid FROM tz_classtutor	 ";
            sql += "	   WHERE subj = a.subj and year = a.year and subjseq = a.subjseq and class = (SELECT class  FROM tz_student  ";
            sql += "																				  WHERE  subj = a.subj and year = a.year and subjseq = a.subjseq and userid=a.userid) ) tuserid,  ";
            sql += "	  (SELECT name FROM tz_member WHERE userid = (SELECT tuserid FROM tz_classtutor   ";
            sql += "												  WHERE  subj = a.subj and year = a.year and subjseq = a.subjseq and class = (SELECT class FROM tz_student   ";
            sql += "																															  WHERE  subj = a.subj and year = a.year and subjseq = a.subjseq and userid=a.userid) ) ) tname,  ";
            sql += "	  (SELECT reptype FROM tz_projord WHERE subj=a.subj and year=a.year and subjseq=a.subjseq and ordseq=b.ordseq) reptype  ";
            sql += "FROM  tz_projassign a, tz_projrep b,  tz_member  c  ";
            sql += "WHERE a.subj = b.subj and a.year = b.year and a.subjseq=b.subjseq and a.ordseq = b.ordseq and a.userid=b.projid and a.userid = c.userid   ";
            sql += "	  and a.subj='" + v_subj + "' and a.year='" + v_year + "' and a.subjseq='" + v_subjseq + "'  ";

            /*
             * sql = "select   b.projseq, \n"; sql+= "		 b.ordseq,\n"; sql+=
             * "		 b.seq,\n"; sql+= "		 (select title \n"; sql+=
             * "		 from	tz_projord \n"; sql+= "		 where   subj	= a.subj	and \n";
             * sql+= "				 year	= a.year	and \n"; sql+=
             * "				 subjseq = a.subjseq and \n"; sql+=
             * "				 ordseq  = (select	ordseq  \n"; sql+=
             * "							from	 tz_projassign \n"; sql+=
             * "							where	subj	= a.subj and \n"; sql+=
             * "									 year	= a.year and \n"; sql+=
             * "									 subjseq = a.subjseq and \n"; sql+=
             * "									 userid  = a.userid)) assigntitle,\n"; sql+=
             * "		 c.deptnam,\n"; sql+= "		 a.userid projgrp,\n"; sql+=
             * "		 c.name projname, \n"; sql+= "		 b.title,\n"; sql+=
             * "		 b.realfile,\n"; sql+= "		 b.upfile,\n"; sql+=
             * "		 b.upfilesize,   \n"; sql+= "		 b.contentsbyte,\n"; sql+=
             * "		 b.indate,\n"; sql+= "		 b.score,\n"; sql+=
             * "		 isnull(b.isret,'N') isret,\n"; sql+= "		 (select tuserid \n";
             * sql+= "		 from	tz_classtutor \n"; sql+=
             * "		 where   subj	= a.subj and \n"; sql+=
             * "				 year	= a.year and \n"; sql+=
             * "				 subjseq = a.subjseq and \n"; sql+=
             * "				 class   = a.class) tuserid,\n"; sql+= "		 (select name \n";
             * sql+= "		 from	tz_member \n"; sql+=
             * "		 where   userid = (select tuserid \n"; sql+=
             * "						   from   tz_classtutor \n"; sql+=
             * "						   where  subj	= a.subj and \n"; sql+=
             * "								  year	= a.year and \n"; sql+=
             * "								  subjseq = a.subjseq and \n"; sql+=
             * "								  class   = a.class) ) tname, \n"; sql+=
             * "		(select  reptype "; sql+= "		 from	tz_projord "; sql+=
             * "		 where   subj=a.subj and "; sql+= "				 year=a.year and ";
             * sql+= "				 subjseq=a.subjseq and "; sql+=
             * "				 ordseq=b.ordseq) reptype "; sql+= "from	tz_student a, \n";
             * sql+= "		tz_projrep b, \n"; sql+= "		tz_member c \n"; sql+=
             * "where   a.userid = c.userid and \n"; sql+= "		a.subj='" + v_subj
             * + "' and \n"; sql+= "		a.year='" + v_year + "' and \n"; sql+=
             * "		a.subjseq='" + v_subjseq + "' and \n"; sql+=
             * "		a.subj = b.subj(+) and \n"; sql+=
             * "		a.year = b.year(+) and \n"; sql+=
             * "		a.subjseq = b.subjseq(+) and \n"; sql+=
             * "		a.userid = b.projid(+) \n";
             */

            if (v_iscopy.equals("")) {
                if (v_orderColumn.equals("")) {
                    sql += "order by b.projseq, a.ordseq, b.seq \n";
                } else {
                    sql += " order by b.projseq, " + v_orderColumn + v_orderType;
                }
            } else {
                sql += "order by b.projseq, b.ordseq, b.seq, c.deptnam, a.userid, " + v_orderColumn;
                //sql+= "order by b.projseq, b.ordseq, b.seq,   " +v_orderColumn ;

                System.out.println("v_orderColumn" + v_orderColumn);

            }

            ls1 = connMgr.executeQuery(sql);

            while (ls1.next()) {
                dbox = ls1.getDataBox();
                list1.add(dbox);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
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
     * 리포트 제출자 리스트 전체
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    /*
     * public ArrayList selectProjectDetailListAll(RequestBox box) throws
     * Exception { DBConnectionManager connMgr = null; DataBox dbox = null;
     * ListSet ls = null; ArrayList list = null; String sql = ""; ProjectData
     * data = null; String v_subj = box.getString("p_subj"); //과정 String v_year
     * = box.getString("p_year"); //년도 String v_subjseq =
     * box.getString("p_subjseq"); //과정차수
     * 
     * String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명 String
     * v_orderType = box.getString("p_orderType"); //정렬할 순서
     * 
     * 
     * try { connMgr = new DBConnectionManager(); list = new ArrayList();
     * 
     * sql = "select   b.seq, \n"; sql+= "		 a.userid projgrp,\n"; sql+=
     * "		 c.name projname,\n"; sql+= "		 (select ldate \n"; sql+=
     * "		 from	tz_projassign \n"; sql+= "		 where   subj	= a.subj	and \n";
     * sql+= "				 year	= a.year	and \n"; sql+=
     * "				 subjseq = a.subjseq and \n"; sql+=
     * "				 userid  = a.userid) assigndate,\n"; sql+= "		 (select title \n";
     * sql+= "		 from	tz_projord \n"; sql+= "		 where   subj	= a.subj	and \n";
     * sql+= "				 year	= a.year	and \n"; sql+=
     * "				 subjseq = a.subjseq and \n"; sql+=
     * "				 ordseq  = (select   ordseq \n"; sql+=
     * "							from	 tz_projassign \n"; sql+=
     * "							where	subj	= a.subj and \n"; sql+=
     * "									 year	= a.year and \n"; sql+=
     * "									 subjseq = a.subjseq and \n"; sql+=
     * "									 userid  = a.userid)) assigntitle,\n"; sql+= "		 b.title,\n";
     * sql+= "		 b.score_mas,\n"; sql+= "		 isnull(b.isfinal,'N') isfinal,\n";
     * sql+= "		 isnull(b.isret,'N') isret,\n"; sql+= "		 b.upfile,\n"; sql+=
     * "		 b.indate,\n"; sql+= "		 (select tuserid \n"; sql+=
     * "		 from	tz_classtutor \n"; sql+= "		 where   subj	= a.subj and \n";
     * sql+= "				 year	= a.year and \n"; sql+=
     * "				 subjseq = a.subjseq and \n"; sql+=
     * "				 class   = a.class) tuserid,\n"; sql+= "		 (select name \n"; sql+=
     * "		 from	tz_member \n"; sql+= "		 where   userid = (select tuserid \n";
     * sql+= "						   from   tz_classtutor \n"; sql+=
     * "						   where  subj	= a.subj and \n"; sql+=
     * "								  year	= a.year and \n"; sql+=
     * "								  subjseq = a.subjseq and \n"; sql+=
     * "								  class   = a.class) ) tname, \n"; sql+= "		b.ordseq,"; sql+=
     * "		b.lesson, "; sql+= "		(select  reptype "; sql+= "		 from	tz_projord ";
     * sql+= "		 where   subj=a.subj and "; sql+= "				 year=a.year and "; sql+=
     * "				 subjseq=a.subjseq and "; sql+= "				 ordseq=b.ordseq) reptype ";
     * sql+= "from	tz_student a, \n"; sql+= "		tz_projrep b, \n"; sql+=
     * "		tz_member c \n"; sql+= "where   a.userid = c.userid and \n"; sql+=
     * "		a.subj='" + v_subj + "' and \n"; sql+= "		a.year='" + v_year +
     * "' and \n"; sql+= "		a.subjseq='" + v_subjseq + "' and \n"; sql+=
     * "		a.subj = b.subj(+) and \n"; sql+= "		a.year = b.year(+) and \n"; sql+=
     * "		a.subjseq = b.subjseq(+) and \n"; sql+= "		a.userid = b.projid(+) \n";
     * 
     * if(v_orderColumn.equals("")) { sql+= "order by c.name \n"; } else { sql+=
     * " order by " + v_orderColumn + v_orderType; }
     * 
     * 
     * 
     * System.out.println("here=>"+sql); ls = connMgr.executeQuery(sql);
     * 
     * while (ls.next()) { dbox = ls.getDataBox(); list.add(dbox); } } catch
     * (Exception ex) { ErrorManager.getErrorStackTrace(ex, box, sql); throw new
     * Exception("sql = " + sql + "\r\n" + ex.getMessage()); } finally { if(ls
     * != null) { try { ls.close(); }catch (Exception e) {} } if(connMgr !=
     * null) { try { connMgr.freeConnection(); }catch (Exception e10) {} } }
     * return list; }
     */

    /**
     * 리포트 제출자 리스트 - 리포트유형
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<ProjectData> selectProjectDetailList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<ProjectData> list = null;
        String sql = "";
        ProjectData data = null;
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        String v_ordseq = box.getString("p_ordseq"); //일련번호
        //		String v_lesson	  = box.getString("p_lesson");		//일차
        String v_reptype = box.getString("p_reptype"); //타입
        String v_isfinal = box.getString("p_isfinal");
        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서
        String v_userid = box.getSession("userid");
        String s_gadmin = box.getSession("gadmin");
        String v_gadmin = StringManager.substring(s_gadmin, 0, 1);

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<ProjectData>();

            if (v_reptype.equals("P")) {
                //select seq,projgrp,projname,title,score_mas,upfile,indate
                sql = "select A.seq,A.projid projgrp,A.title,A.score_mas,isfinal,A.upfile,A.indate,  '' upfile2, '' realfile2, A.ldate, A.tudate, A.retdate,  A.isret, ";
                sql += "(select projname from TZ_PROJGRP where subj=A.subj and year=A.year and subjseq=A.subjseq ";
                sql += "and ordseq=A.ordseq and projid=A.projid GROUP BY projname) as projname ";
                sql += "from  TZ_PROJREP A  ";
                sql += "where A.subj='" + v_subj + "' and A.year='" + v_year + "' and A.subjseq='" + v_subjseq + "' ";
                sql += "and A.ordseq='" + v_ordseq + "' ";//and A.lesson='"+v_lesson+"' ";
                //sql+= "order by A.projid";

                if (v_orderColumn.equals("")) {
                    sql += "order by A.projid \n";
                } else {
                    sql += " order by " + v_orderColumn + v_orderType;
                }

            } else {
                if (v_gadmin.equals("P")) { //		  강사권한 경우 해당 클래스의 학습자 리스트만
                    sql = "select c.projseq, c.title assigntitle,a.seq, a.ordseq, a.projid projgrp, a.title,a.score,a.isfinal,a.isret,a.indate,a.upfile,a.upfilesize,a.contentsbyte,a.upfile, upfile2, a.realfile, realfile2, a.ldate, a.tudate, a.retdate, a.isret, get_name(a.projid) projname \n";
                    sql += " from tz_projrep a, \n";
                    sql += "  (select p.subj, p.year, p.subjseq, p.userid from tz_student p, tz_classtutor q \n";
                    sql += "  where p.subj = q.subj and p.year = q.year and p.subjseq = q.subjseq \n";
                    sql += "  and q.tuserid = '" + v_userid + "' \n";
                    sql += "  and q.subj = '" + v_subj + "' and q.year = '" + v_year + "' and q.subjseq = '" + v_subjseq + "') b, \n";
                    sql += "  TZ_PROJORD c \n";
                    sql += " where a.subj = b.subj and a.year = b.year and a.subjseq = b.subjseq \n";
                    sql += " and a.projid = b.userid \n";
                    sql += " and a.subj = c.subj and a.year = c.year and a.subjseq = c.subjseq  and a.ordseq=c.ordseq \n";
                    sql += " and a.subj= '" + v_subj + "' and a.year = '" + v_year + "' and a.subjseq = '" + v_subjseq + "'  \n";

                    if (!v_isfinal.equals(""))
                        sql += " and isfinal = '" + v_isfinal + "'"; // 채점여부

                    //sql += " and a.ordseq='"+v_ordseq+"'  \n";
                    // sql += " order by a.isfinal,indate \n";

                    if (v_orderColumn.equals("")) {
                        sql += "order by a.isfinal, c.ordseq \n";
                    } else {
                        sql += " order by " + v_orderColumn + v_orderType;
                    }

                } else { // 운영자에서 보는 리스트

                    sql = "select A.seq, A.ordseq, A.projid projgrp,A.title,A.score,isfinal, A.isret, A.upfile, A.realfile, A.upfilesize, A.contentsbyte, ";
                    sql += " '' assigntitle, A.projseq, A.indate, B.upfile2, B.realfile2, A.ldate, A.tudate, A.retdate, A.isret, \n";
                    sql += "get_name(a.projid) projname \n";
                    sql += "from TZ_PROJREP A, TZ_PROJORD B  \n";
                    sql += "where A.subj=B.subj AND A.year=B.year AND A.subjseq=B.subjseq AND A.ordseq=B.ordseq  ";
                    sql += "and   A.subj='" + v_subj + "' and A.year='" + v_year + "' and A.subjseq='" + v_subjseq + "' \n";
                    sql += "and   A.ordseq='" + v_ordseq + "' \n";//and A.lesson='"+v_lesson+"' ";
                    //sql+= "order by A.projid \n";

                    if (v_orderColumn.equals("")) {
                        sql += "order by A.projid \n";
                    } else {
                        sql += " order by " + v_orderColumn + v_orderType;
                    }
                }

            }

            System.out.println("report_list===>>>>" + sql);

            /*
             * if(v_orderColumn.equals("")) { sql+=
             * "order by a.isfinal, c.ordseq \n"; } else { sql+= " order by " +
             * v_orderColumn + v_orderType; }
             */

            ls = connMgr.executeQuery(sql);

            while (ls.next()) {

                data = new ProjectData();
                data.setSeq(ls.getInt("seq"));
                data.setOrdseq(ls.getInt("ordseq"));
                data.setProjgrp(ls.getString("projgrp"));
                data.setProjname(ls.getString("projname"));
                data.setTitle(ls.getString("title"));
                data.setScore(ls.getInt("score"));
                data.setIsfinal(ls.getString("isfinal"));
                data.setIsret(ls.getString("isret"));
                data.setUpfile(ls.getString("upfile"));
                data.setRealfile(ls.getString("realfile"));
                data.setUpfilesize(ls.getString("upfilesize"));
                data.setContentsbyte(ls.getString("contentsbyte"));
                data.setAssigntitle(ls.getString("assigntitle"));
                data.setProjseq(ls.getInt("projseq"));
                data.setIndate(ls.getString("indate"));
                data.setUpfile2(ls.getString("upfile2"));
                data.setRealfile2(ls.getString("realfile2"));
                data.setLdate(ls.getString("ldate"));
                data.setTudate(ls.getString("tudate"));
                data.setRetdate(ls.getString("retdate"));
                data.setIsret(ls.getString("isret"));
                list.add(data);
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
     * 리포트 제출자 리스트 - 강사메인
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public ArrayList<DataBox> selectProjectDetailListTutor(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        DataBox dbox = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        // 수정일 : 05.11.14 수정자 : 이나연 _ totalcount 해주기 위한 쿼리 수정
        String sql = "";
        String head_sql = "";
        String body_sql = "";
        String group_sql = "";
        String order_sql = "";
        //		String count_sql="";

        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        //		String  v_ordseq	= box.getString("p_ordseq");		//일련번호
        //		String  v_lesson	= box.getString("p_lesson");		//일차
        //		String  v_reptype   = box.getString("p_reptype");	   //타입
        String v_isfinal = box.getString("p_isfinal");
        String v_orderColumn = box.getString("p_orderColumn"); //정렬할 컬럼명
        String v_orderType = box.getString("p_orderType"); //정렬할 순서

        String v_userid = box.getSession("userid");
        //		String s_gadmin = box.getSession("gadmin");

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();
            /*
             * sql =
             * "SELECT a.projseq, a.ordseq, b.seq,  isnull(b.resend,0) resend, isnull(b.copyupnm,'N') copyupnm, isnull(b.copysize,'N') copysize, isnull(b.copybyte,'N') copybyte, "
             * ; sql+= "	  (SELECT title FROM tz_projord "; sql+=
             * "	   WHERE subj = a.subj and year = a.year and subjseq = a.subjseq and ordseq=a.ordseq ) assigntitle, "
             * ; sql+= "	   c.deptnam, "; sql+=
             * "	   a.userid projgrp,  c.name projname,  "; sql+=
             * "	   b.title, b.realfile, b.upfile, b.upfilesize, b.contentsbyte, b.indate, b.score, isnull(b.isret,'N') isret, b.ldate, b.retdate, "
             * ; sql+= "	  (SELECT tuserid FROM tz_classtutor	 "; sql+=
             * "	   WHERE subj = a.subj and year = a.year and subjseq = a.subjseq and class = (SELECT class  FROM tz_student  "
             * ; sql+=
             * "																				  WHERE  subj = a.subj and year = a.year and subjseq = a.subjseq and userid=a.userid) ) tuserid,  "
             * ; sql+=
             * "	  (SELECT name FROM tz_member WHERE userid = (SELECT tuserid FROM tz_classtutor   "
             * ; sql+=
             * "												  WHERE  subj = a.subj and year = a.year and subjseq = a.subjseq and class = (SELECT class FROM tz_student   "
             * ; sql+=
             * "																															  WHERE  subj = a.subj and year = a.year and subjseq = a.subjseq and userid=a.userid) ) ) tname,  "
             * ; sql+=
             * "	  (SELECT reptype FROM tz_projord WHERE subj=a.subj and year=a.year and subjseq=a.subjseq and ordseq=b.ordseq) reptype  "
             * ; sql+=
             * "				FROM  tz_projassign a, tz_projrep b,  tz_member  c  "; sql+=
             * "				WHERE a.subj = b.subj and a.year = b.year and a.subjseq=b.subjseq and a.ordseq = b.ordseq and a.userid=b.projid and a.userid = c.userid   "
             * ; sql+= "	  		and a.subj='" + v_subj + "' and a.year='" + v_year
             * + "' and a.subjseq='" + v_subjseq + "'  ";
             */
            head_sql = " select																					  \n";
            head_sql += "   z.projseq,	z.tudate,	   z.assigntitle,  z.seq,									  \n";
            head_sql += "   z.ordseq,	 z.projgrp,	  z.title,		z.score,									\n";
            head_sql += "   z.isfinal,	z.isret,		z.indate,												   \n";
            head_sql += "   z.upfilesize, z.contentsbyte, z.upfile,	   z.upfile2,								  \n";
            head_sql += "   z.realfile,   z.realfile2,	z.ldate,		z.retdate,								  \n";
            head_sql += "   z.projname,   z.resend,		  z.copyupnm,												 \n";
            head_sql += "   z.copysize,   z.copybyte,	 z.compnm,		  z.ortitle								   \n";
            body_sql += " from																						\n";
            body_sql += " (																						  \n";
            body_sql += " select																					  \n";
            body_sql += "   c.projseq, a.tudate, c.title assigntitle,												 \n";
            body_sql += "   a.seq, a.ordseq, a.projid projgrp, a.title,											   \n";
            body_sql += "   a.score,a.isfinal,a.isret,a.indate,													   \n";
            body_sql += "   a.upfilesize, a.contentsbyte, a.upfile,												   \n";
            body_sql += "   upfile2, a.realfile, realfile2, a.ldate,												  \n";
            body_sql += "   a.retdate, get_name(a.projid) projname,  isnull(a.resend,0) resend,						  \n";
            body_sql += "   isnull(a.copyupnm,'N') copyupnm, isnull(a.copysize,'N') copysize, isnull(a.copybyte,'N') copybyte, \n";
            body_sql += "   get_compnm(d.comp, 2,2) || ' / ' || get_deptnm(d.deptnam, d.userid) compnm,						  \n";
            body_sql += "   c.projseq || c.title AS ortitle						  \n";
            body_sql += " from tz_projrep a,																		  \n";
            body_sql += "   (select p.subj, p.year, p.subjseq, p.userid from tz_student p, tz_classtutor q			\n";
            body_sql += "   where p.subj = q.subj and p.year = q.year and p.subjseq = q.subjseq and p.class = q.class \n";
            body_sql += "   and q.tuserid = '" + v_userid + "'															\n";
            body_sql += "   and q.subj = '" + v_subj + "' and q.year = '" + v_year + "' and q.subjseq = '" + v_subjseq + "') b,   \n";
            body_sql += "   TZ_PROJORD c,																			 \n";
            body_sql += "   TZ_MEMBER  d																			  \n";
            body_sql += " where																					   \n";
            body_sql += "   a.subj = b.subj and a.year = b.year and a.subjseq = b.subjseq							 \n";
            body_sql += "   and a.projid = b.userid and a.projid = d.userid										   \n";
            body_sql += "   and a.subj = c.subj and a.year = c.year and a.subjseq = c.subjseq  and a.ordseq=c.ordseq  \n";
            body_sql += "   and a.subj= '" + v_subj + "' and a.year = '" + v_year + "' and a.subjseq = '" + v_subjseq + "'		\n";
            if (!v_isfinal.equals(""))
                sql += " and isfinal = '" + v_isfinal + "'"; // 채점여부
            body_sql += " ) z \n";

            if (v_orderColumn.equals("")) {
                order_sql += "order by isfinal, ordseq \n";
            } else {
                order_sql += " order by " + v_orderColumn + v_orderType;
            }
            sql = head_sql + body_sql + group_sql + order_sql;
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                dbox = ls.getDataBox();
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
     * 리포트 제출내역 관리
     * 
     * @param box receive from the form object and session
     * @return ProjectData
     */
    public ProjectData selectProjectSubmitOpen(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        String sql = "";
        ProjectData data = null;
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        int v_ordseq = box.getInt("p_ordseq");
        int v_seq = box.getInt("p_seq");
        //		String  v_lesson	= box.getString("p_lesson");
        String v_projgrp = box.getString("p_projgrp");

        try {
            connMgr = new DBConnectionManager();

            //sql = "select title, contents, upfile, realfile, upfilesize, score, tucontents, retreason, retdate, isret, retuserid, ";
            //sql+= "(select score from TZ_PROJORD where subj='"+v_subj+"' and year='"+v_year+"' and subjseq='"+v_subjseq+"' and ordseq='"+v_ordseq+"' and lesson='"+v_lesson+"') as score2 "; // 만점점수 (by 정은년 2005.06.30 뺌)
            //sql+= "from TZ_PROJREP ";
            //sql+= "where subj='"+v_subj+"' and year='"+v_year+"' and subjseq='"+v_subjseq+"' ";
            //sql+= "and ordseq='"+v_ordseq+"' and seq='"+v_seq+"' ";//and lesson='"+v_lesson+"' ";
            //sql+= "and projid='"+v_projgrp+"'";

            sql = " select a.title, a.contents, a.upfile, a.realfile, a.upfilesize, a.score,  ";
            sql += " a.tucontents, a.retreason, a.retdate, a.isret, ";
            sql += " (select name from TZ_MEMBER where userid=a.retuserid) retuserid , ";
            sql += " b.title title2, b.contents contents2,	  ";
            sql += " (select 100/count(ordseq) from TZ_PROJORD where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' and projseq=b.projseq group by projseq) score2, ";
            sql += " (select wreport from tz_subjseq where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "') wreport  ";
            sql += " from TZ_PROJREP a, TZ_PROJORD b	  ";
            sql += " where a.subj=b.subj				  ";
            sql += " and   a.year=b.year				  ";
            sql += " and   a.subjseq=b.subjseq			";
            sql += " and   a.ordseq=b.ordseq			  ";
            sql += " and   a.subj='" + v_subj + "' and a.year='" + v_year + "' and a.subjseq='" + v_subjseq + "' and a.ordseq='" + v_ordseq + "' and a.seq='" + v_seq + "' and a.projid='" + v_projgrp + "'";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                data = new ProjectData();
                data.setTitle(ls.getString("title"));
                data.setContents(ls.getCharacterStream("contents"));
                data.setUpfile(ls.getString("upfile"));
                data.setRealfile(ls.getString("realfile"));
                data.setUpfilesize(ls.getString("upfilesize"));
                data.setScore(ls.getInt("score"));
                data.setTucontents(ls.getString("tucontents"));
                data.setRetreason(ls.getString("retreason"));
                data.setRetdate(ls.getString("retdate"));
                data.setIsret(ls.getString("isret"));
                data.setRetuserid(ls.getString("retuserid")); // 반려자 ID
                data.setTitle2(ls.getString("title2")); // 질문제목
                data.setContents2(ls.getString("contents2")); // 질문내용
                data.setScore2(ls.getInt("score2")); // 만점점수
                data.setWreport(ls.getInt("wreport")); // 가중치
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
        return data;
    }

    /**
     * 리포트 제출 인원 명단
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */

    public ArrayList<ProjectData> selectProjectSubmitListOpen(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<ProjectData> list = null;
        String sql = "";
        ProjectData data = null;
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        int v_ordseq = box.getInt("p_ordseq");
        //		String  v_projgrp   = box.getString("p_projgrp");
        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<ProjectData>();

            //select userid,name
            sql = "select A.projid userid, ";
            sql += "(select name from TZ_MEMBER where userid=A.projid) as name ";
            // /		   sql+= "from TZ_PROJGRP A ";
            sql += "from TZ_PROJREP A ";
            sql += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";
            ///			sql+= "and ordseq='"+v_ordseq+"' and projid='"+v_projgrp+"'";
            sql += "and ordseq='" + v_ordseq + "' ";
            System.out.println("33333 sql" + sql);
            ls = connMgr.executeQuery(sql);

            while (ls.next()) {
                data = new ProjectData();
                data.setUserid(ls.getString("userid"));
                data.setName(ls.getString("name"));
                list.add(data);
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
     * 리포트 점수관리
     * 
     * @param box receive from the form object and session
     * @return int
     */

    @SuppressWarnings("unchecked")
    public int updateProjectJudge(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt2 = null;
        ListSet ls1 = null;
        ListSet ls2 = null;
        String sql = "";
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        String v_projgrp = box.getString("p_projgrp");
        String v_tucontents = box.getString("p_tucontents");
        String v_reptype = box.getString("p_reptype");

        String v_isret = box.getString("p_isret"); //반려여부
        String v_retreason = box.getString("p_retreason"); //반려사유

        int v_score_mas = box.getInt("p_score_mas");
        int v_ordseq = box.getInt("p_ordseq");
        int v_cnt = 0;
        long v_finalscore = 0;

        try {
            connMgr = new DBConnectionManager();

            if (v_reptype.equals("C")) { //COP타입일때
                //select cnt,score,subj
                sql1 = "select count(A.subj) cnt,sum(B.score) score ";
                sql1 += "from TZ_PROJGRP A,TZ_COPREP B ";
                sql1 += "where A.subj=" + SQLString.Format(v_subj) + " and A.year=" + SQLString.Format(v_year);
                sql1 += "and A.subjseq=" + SQLString.Format(v_subjseq) + " and A.ordseq=" + SQLString.Format(v_ordseq);
                sql1 += "and B.subj=A.subj and B.year=A.year and B.subjseq=A.subjseq and B.ordseq=A.ordseq ";
                sql1 += "and B.userid=A.userid and B.userid=" + SQLString.Format(v_projgrp);

                ls1 = connMgr.executeQuery(sql1);

                if (ls1.next()) {
                    v_cnt = ls1.getInt("cnt");
                    v_finalscore = ls1.getInt("score") / v_cnt;
                    v_finalscore = StrictMath.round(v_finalscore * 0.5 + v_score_mas * 0.5);
                }
            } else { //일반,프로젝트 타입일때
                v_finalscore = v_score_mas;
            }

            //반려일경우 반려정보만 반영 (by 정은년 , 점수=0점 처리, 2005.9.13 retmailing 추가)
            if (v_isret.equals("Y")) {
                sql2 = "update TZ_PROJREP set retmailing='N', resend=0, score=0, score_mas=0, isret='Y',isfinal='N',retreason='" + v_retreason + "',  ldate=to_char(sysdate,'YYYYMMDDHH24MISS') , ";
                sql2 += "					  retdate=to_char(sysdate,'YYYYMMDDHH24MISS'),retuserid='" + box.getSession("userid") + "' ";
                sql2 += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";
                sql2 += "  and ordseq='" + v_ordseq + "' and projid='" + v_projgrp + "'";

                isOk = connMgr.executeUpdate(sql2);

                //반려일때 학습자에게 반려메일을 전송한다.
                //메일 보내기 시작

                //학습자 정보
                sql = "select  (select name  from tz_member where userid = tz_projrep.projid) name, ";
                sql += "		(select cono  from tz_member where userid = tz_projrep.projid) tocono, ";
                sql += "		(select email from tz_member where userid = tz_projrep.projid) email, ";
                sql += "		title, ";
                sql += "		(select subjnm from tz_subj where subj = tz_projrep.subj) subjnm,";
                sql += "		retreason ";
                sql += "from	tz_projrep ";
                sql += "where   subj	= '" + v_subj + "' and ";
                sql += "		year	= '" + v_year + "' and ";
                sql += "		subjseq = '" + v_subjseq + "' and ";
                sql += "		ordseq  = '" + v_ordseq + "' and ";
                sql += "		projid  = '" + v_projgrp + "' ";

                ls2 = connMgr.executeQuery(sql);

                if (ls2.next()) {

                    String v_sendhtml = "RejectReport.html";

                    //보내는 사람 세팅
                    box.put("p_fromEmail", box.getSession("email"));
                    box.put("p_fromName", box.getSession("name"));
                    box.put("p_comptel", "");

                    FormMail fmail = new FormMail(v_sendhtml); //	  폼메일발송인 경우

                    MailSet mset = new MailSet(box); //	  메일 세팅 및 발송

                    String v_mailTitle = "오토에버 리포트 반려 안내 메일입니다.";
                    String v_isMailing = "1"; // email로만 전송
                    String v_name = ls2.getString("name");
                    String v_toCono = ls2.getString("tocono");
                    String v_toEmail = ls2.getString("email");
                    String v_report = ls2.getString("title");
                    String v_subjnm = ls2.getString("subjnm");
                    String v_content = ls2.getString("retreason"); //반려

                    box.put("v_name", v_name);
                    box.put("v_toEmail", v_toEmail);

                    if (!v_toEmail.equals("")) {
                        mset.setSender(fmail); // 메일보내는 사람 세팅
                        fmail.setVariable("name", v_name); // 수신자명
                        fmail.setVariable("subjnm", v_subjnm); // 과정명
                        fmail.setVariable("report", v_report); // 레포트 제출한 제목
                        fmail.setVariable("content", StringManager.replace(v_content, "\r\n", "<br>")); //반려사유

                        String v_mailContent = fmail.getNewMailContent();
                        boolean isMailed = mset.sendMail(v_toCono, v_toEmail, v_mailTitle, v_mailContent, v_isMailing, v_sendhtml);

                        System.out.println("반려메일 전송 여부 = " + isMailed);
                    }
                }//메일 보내기 끝

                //반려가 아닌 경우...
            } else {
                sql2 = " update TZ_PROJREP set resend=0, score_mas=" + v_score_mas + ",score=" + v_score_mas + ",tucontents='" + v_tucontents
                        + "',isret='N',isfinal='Y', ldate=to_char(sysdate,'YYYYMMDDHH24MISS'),tudate=to_char(sysdate,'YYYYMMDDHH24MISS')  ";
                sql2 += " where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";
                sql2 += " and   ordseq='" + v_ordseq + "' and projid='" + v_projgrp + "'";

                isOk = connMgr.executeUpdate(sql2);
            }

            //isOk = pstmt2.executeUpdate();
            //=========리포트 점수반영
            isOk = CalcUtil.getInstance().calc_score(connMgr, CalcUtil.REPORT, v_subj, v_year, v_subjseq, v_projgrp);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql2 = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls1 != null) {
                try {
                    ls1.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
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
     * 업로드 파일(첨부파일,답안파일) 삭제
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public int delUpfile(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;
        String sql = "";
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        int v_ordseq = box.getInt("p_ordseq");
        String v_upfile_type = box.getString("p_upfile_type");
        //System.out.println(v_upfile);
        if (v_upfile_type.equals("1")) {
            v_upfile_type = "";
        } else if (v_upfile_type.equals("2")) {
            v_upfile_type = "2";
        }
        try {
            connMgr = new DBConnectionManager();

            sql = "update TZ_PROJORD ";
            sql += "set upfile" + v_upfile_type + "='' ";
            sql += "where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";
            sql += "and ordseq='" + v_ordseq + "'";
            System.out.println("sql============>" + sql);
            isOk = connMgr.executeUpdate(sql);
            System.out.println(isOk);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
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
     * 업로드 파일(첨부파일,답안파일) 삭제
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public int selectMaxProjectSeq(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String sql = "";
        //		DataBox dbox		= null;
        ListSet ls = null;

        int v_projseq = 1;

        int v_maxprojcnt = 0;
        int v_maxprojseq = 0;

        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        connMgr = new DBConnectionManager();

        try {
            sql = "select ( select nvl(max(projseq),1)  ";
            sql += "		 from   tz_projord ";
            sql += "		 where  subj	= '" + v_subj + "' and ";
            sql += "				year	= '" + v_year + "' and ";
            sql += "				subjseq = '" + v_subjseq + "') maxprojseq, ";
            sql += "		(select count(*) ";
            sql += "		 from   tz_projord ";
            sql += "		 where  subj	= '" + v_subj + "' and ";
            sql += "				year	= '" + v_year + "' and ";
            sql += "				subjseq = '" + v_subjseq + "'  and ";
            sql += "				projseq = (select isnull(max(projseq),1) ";
            sql += "						   from   tz_projord ";
            sql += "						   where  subj	= '" + v_subj + "' and ";
            sql += "								  year	= '" + v_year + "' and ";
            sql += "								  subjseq = '" + v_subjseq + "') ";
            sql += " )	maxprojcnt from dual ";
            // 2005.11.04_하경태 : Orcle -> mssql 로 변환
            //sql+=" from dual";

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                v_maxprojseq = ls.getInt("maxprojseq");
                v_maxprojcnt = ls.getInt("maxprojcnt");

                if (v_maxprojcnt < 5) {
                    v_projseq = v_maxprojseq;
                } else {
                    v_projseq = v_maxprojseq + 1;
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql);
            throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
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
        return v_projseq;
    }

    public int getAdminData(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");

        int v_result = 0;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            //sql = "select isnull(max(projseq),0) projseq ";

            sql = " select SUM(isnull(count(ordseq),0)) projseq  ";
            sql += "  from tz_projord  ";
            sql += " where subj = " + SQLString.Format(v_subj);
            sql += " and year = " + SQLString.Format(v_year);
            sql += " and subjseq = " + SQLString.Format(v_subjseq);
            sql += " and useyn = 'Y'";
            sql += " group by projseq ";

            System.out.println("sql_projord===>>>>>" + sql);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();
                v_result = dbox.getInt("d_projseq");
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
        return v_result;
    }

    public int getUserData(RequestBox box) throws Exception {

        DBConnectionManager connMgr = null;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String s_userid = box.getSession("userid");

        int v_result = 0;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            sql = "select count(ordseq) seq";
            sql += "  from tz_projrep  ";
            sql += " where subj = " + SQLString.Format(v_subj);
            sql += " and year = " + SQLString.Format(v_year);
            sql += " and subjseq = " + SQLString.Format(v_subjseq);
            sql += " and projid = " + SQLString.Format(s_userid);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();
                v_result = dbox.getInt("d_seq");
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
        return v_result;
    }

    /**
     * 반려 갯수
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public int getIsRet(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        String v_subj = box.getString("p_subj");
        String v_year = box.getString("p_year");
        String v_subjseq = box.getString("p_subjseq");
        String s_userid = box.getSession("userid");

        int v_result = 0;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            connMgr = new DBConnectionManager();
            sql = "select count(isret) isret";
            sql += "  from tz_projrep  ";
            sql += " where subj = " + SQLString.Format(v_subj);
            sql += "   and year = " + SQLString.Format(v_year);
            sql += "   and subjseq = " + SQLString.Format(v_subjseq);
            sql += "   and projid = " + SQLString.Format(s_userid);
            sql += "   and isret = 'Y'   ";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                dbox = ls.getDataBox();
                v_result = dbox.getInt("d_isret");
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
        return v_result;
    }

    /**
     * 업로드 파일(첨부파일,답안파일) 삭제
     * 
     * @param box receive from the form object and session
     * @return ArrayList
     */
    public int getUserData(DBConnectionManager connMgr, String p_subj, String p_year, String p_subjseq, String p_userid, String p_examtype) throws Exception {

        //		ArrayList QuestionExampleDataList  = null;
        int v_result = 0;
        ListSet ls = null;
        String sql = "";
        DataBox dbox = null;

        try {
            sql = "select count(papernum) examcount ";
            sql += "  from tz_exampaper  ";
            sql += " where subj = " + SQLString.Format(p_subj);
            sql += " and year = " + SQLString.Format(p_year);
            sql += " and subjseq = " + SQLString.Format(p_subjseq);
            sql += " and examtype = " + SQLString.Format(p_examtype);

            ls = connMgr.executeQuery(sql);

            if (ls.next()) {
                dbox = ls.getDataBox();
                v_result = dbox.getInt("d_examcount");
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
        }
        return v_result;
    }

    /**
     * 리포트 삭제
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int deleteProjectReport(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt2 = null;
        ListSet ls = null;
        String sql1 = "";
        String sql2 = "";
        int isOk = 0;
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        int v_ordseq = box.getInt("p_ordseq"); //리포트번호

        try {
            connMgr = new DBConnectionManager();

            sql1 = "select  count(userid)  ";
            sql1 += "from	TZ_PROJASSIGN ";
            sql1 += "where   subj='" + v_subj + "' and year='" + v_year + "' and ";
            sql1 += "		subjseq='" + v_subjseq + "' and ordseq = " + v_ordseq;

            ls = connMgr.executeQuery(sql1);

            if (ls.next()) {
                if (ls.getInt(1) != 0) {
                    isOk = -1; //배정된 학습자가 있어 삭제할 수 없습니다.
                } else {
                    //Delete tz_projrep
                    sql2 = "delete  TZ_PROJORD ";
                    sql2 += "where   subj = ? and year=? and subjseq=? and ordseq = ? ";
                    pstmt2 = connMgr.prepareStatement(sql2);

                    pstmt2.setString(1, v_subj);
                    pstmt2.setString(2, v_year);
                    pstmt2.setString(3, v_subjseq);
                    pstmt2.setInt(4, v_ordseq);

                    isOk = pstmt2.executeUpdate();
                }
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql2);
            throw new Exception("sql2 = " + sql2 + "\r\n" + ex.getMessage());
        } finally {
            if (ls != null) {
                try {
                    ls.close();
                } catch (Exception e1) {
                }
            }
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
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
     * 모사답안 view
     * 
     * @param box receive from the form object and session
     * @return ProjectData
     */

    public ArrayList<ProjectData> selectProjectCopyView(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<ProjectData> list = null;
        String sql = "";
        String wsql = "";
        ProjectData data = null;
        String v_subj = box.getString("p_subj"); // 과정
        String v_year = box.getString("p_year"); // 년도
        String v_subjseq = box.getString("p_subjseq"); // 과정차수
        String v_wdata = box.getString("p_wdata"); // 조회 데이타(출제번호,제출자,일련번호)

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<ProjectData>();

            String[] tokens = v_wdata.split("\\^");
            for (int i = 0; i < tokens.length; i++) {
                String[] tokens1 = tokens[i].split("\\|");
                if (i != 0)
                    wsql += " or ";
                wsql += " (ordseq='" + tokens1[0] + "' and projid='" + tokens1[1] + "' and seq='" + tokens1[2] + "') ";
            }

            sql = " select ordseq,projid,title,contents,upfile,realfile,upfilesize,contentsbyte, " + "		retreason, retdate, isret, (select name from TZ_MEMBER where userid=retuserid) retuserid  " + " from TZ_PROJREP " + " where subj='" + v_subj
                    + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "'  " + " and (" + wsql + ")";

            ls = connMgr.executeQuery(sql);
            //Log.info.println("sql>>"+sql);
            while (ls.next()) {
                data = new ProjectData();
                data.setOrdseq(ls.getInt("ordseq"));
                data.setProjgrp(ls.getString("projid"));
                data.setTitle(ls.getString("title"));
                data.setContents(ls.getCharacterStream("contents"));
                data.setUpfile(ls.getString("upfile"));
                data.setRealfile(ls.getString("realfile"));
                data.setUpfilesize(ls.getString("upfilesize"));
                data.setContentsbyte(ls.getString("contentsbyte"));
                data.setRetreason(ls.getString("retreason"));
                data.setRetdate(ls.getString("retdate"));
                data.setIsret(ls.getString("isret"));
                data.setRetuserid(ls.getString("retuserid")); // 반려자 ID
                list.add(data);
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
     * 모사답안 반려 처리
     * 
     * @param box receive from the form object and session
     * @return ProjectData
     */

    @SuppressWarnings("unchecked")
    public int updateProjectJudgeCopy(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        int isOk = 0;
        String sql1 = "";
        String v_subj = box.getString("p_subj"); // 과정
        String v_year = box.getString("p_year"); // 년도
        String v_subjseq = box.getString("p_subjseq"); // 과정차수
        int v_copycnt = Integer.parseInt(box.getString("p_copycnt")); // 모사답안비교갯수
        String v_ordseq = ""; // 출제번호
        String v_projgrp = ""; // 제출자
        String v_isret = ""; // 반려여부
        String v_retreason = ""; // 반려내용

        try {
            connMgr = new DBConnectionManager();

            for (int i = 0; i < v_copycnt; i++) {
                v_ordseq = box.getString("p_ordseq" + String.valueOf(i));
                v_projgrp = box.getString("p_projid" + String.valueOf(i));
                v_isret = box.getString("p_isret" + String.valueOf(i));
                v_retreason = box.getString("p_retreason" + String.valueOf(i));
                box.put("p_ordseq", v_ordseq);
                box.put("p_projgrp", v_projgrp);
                box.put("p_isret", v_isret);
                box.put("p_retreason", v_retreason);

                if (v_isret.equals("Y")) {
                    // 리포트(반려)적용
                    isOk = updateProjectJudge(box);

                } else {
                    sql1 = " update TZ_PROJREP set isret='N', isfinal='Y', retreason='', retdate='', retuserid=''  " + " where subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' " + " and   ordseq='" + v_ordseq
                            + "' and projid='" + v_projgrp + "'";

                    isOk = connMgr.executeUpdate(sql1);
                }
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, box, sql1);
            throw new Exception("sql = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
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
     * 교육완료 후 재제출 리스트 - 배정받은 수강생
     * 
     * @param box receive from the form object and session
     * @return ProjectData
     */

    public ArrayList<DataBox> selectProjectEndAssignList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        String sql = "";
        String wsql = "";

        String v_subj = box.getString("p_subj"); // 과정
        String v_year = box.getString("p_year"); // 년도
        String v_subjseq = box.getString("p_subjseq"); // 과정차수
        int v_ordseq = box.getInt("p_ordseq"); // 리포트문제번호

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            wsql = "  subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' and ordseq=" + v_ordseq + " ";

            sql = " SELECT userid, name, deptnam, title, isnull(ordseq,0) ordseq, indate, isret, resend  " + " FROM																											 " + "	 ( select a.userid, b.name, b.deptnam  from tz_student a, tz_member b										 "
                    + "	   where a.userid=b.userid and a.subj='" + v_subj + "' and a.year='" + v_year + "' and a.subjseq='" + v_subjseq + "' and  " + "			 a.userid in (select distinct userid																  "
                    + "							from TZ_PROJASSIGN																	" + "						   where " + wsql + "			 ) ) A,													  " // 배정받은 수강생 정보
                    + "	 ( select title, ordseq, indate, projid, isret, isnull(resend, 0) resend										 " + "	   from tz_projrep where " + wsql + "  ) B																		" // 리포트결과
                    //+ " WHERE A.userid=B.projid(+)										  ";
                    + " WHERE A.userid = B.projid										 (+) ";

            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

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
     * 교육완료 후 재제출 리스트 - 다른 리포트에도 배정 받지 않은 수강생
     * 
     * @param box receive from the form object and session
     * @return ProjectData
     */

    public ArrayList<DataBox> selectProjectEndAssignNotList(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ArrayList<DataBox> list = null;
        DataBox dbox = null;
        String sql = "";
        String wsql = "";

        String v_subj = box.getString("p_subj"); // 과정
        String v_year = box.getString("p_year"); // 년도
        String v_subjseq = box.getString("p_subjseq"); // 과정차수
        int v_ordseq = box.getInt("p_ordseq"); // 리포트문제번호
        int v_projseq = box.getInt("p_projseq"); // 리포트셋트번호

        try {
            connMgr = new DBConnectionManager();
            list = new ArrayList<DataBox>();

            wsql = "  subj='" + v_subj + "' and year='" + v_year + "' and subjseq='" + v_subjseq + "' ";

            sql = " select a.userid, b.name, b.deptnam, '' title, 0 ordseq, '' indate, '' isret, '' resend  " + "   from tz_student a, tz_member b														" + "  where a.userid=b.userid and a.subj='" + v_subj + "' and a.year='" + v_year
                    + "' and a.subjseq='" + v_subjseq + "'   " + "	and a.userid not in (select distinct userid															  " + "						   from TZ_PROJASSIGN																"
                    //			  + "						  where "+wsql+" and (projseq!="+v_projseq+" or ordseq="+v_ordseq+"))				"; // 전체 리포트에 배정 안 받은 수강생 정보
                    + "						  where " + wsql + " and (projseq!=" + v_projseq + " and ordseq=" + v_ordseq + "))				"; // 해당 리포트 셋트에 배정 안 받은 수강생 정보
            ls = connMgr.executeQuery(sql);
            while (ls.next()) {
                dbox = ls.getDataBox();

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
     * 교육완료 후 재제출 등록
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int insertProjectEndSubmit(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        ListSet ls = null;
        ListSet ls3 = null;
        PreparedStatement pstmt2 = null;
        String sql1 = "";
        String sql2 = "";
        String sql3 = "";
        int isOk = 0;
        String v_user_id = box.getString("p_userid");
        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        int v_projseq = box.getInt("p_projseq");
        int v_ordseq = box.getInt("p_ordseq");

        String v_title = StringUtil.removeTag(box.getString("p_title"));
        String v_contents = StringUtil.removeTag(box.getString("p_contents"));
        String v_contentsbyte = box.getString("p_contentsbyte");
        String v_realFileName1 = box.getRealFileName("p_file1");
        String v_newFileName1 = box.getNewFileName("p_file1");
        String v_upfile1 = box.getString("p_upfile1");
        String v_check = box.getString("p_check");

        if (v_newFileName1.length() == 0) {
            v_newFileName1 = v_upfile1;
        }

        ConfigSet conf = new ConfigSet();
        File reportFile = new File(conf.getProperty("dir.upload.project"), v_year + File.separator + v_subj + File.separator + v_newFileName1);
        //File reportFile = new File(conf.getProperty("dir.upload.project")+ v_year + File.separator + v_subj  + File.separator , v_newFileName1);

        String fileSize = (reportFile.length()) + "";
        //기존 파일정보
        String v_oldupfile = "";
        String v_oldrealfile = "";
        String v_oldupfilesize = "";

        try {
            connMgr = new DBConnectionManager();
            sql1 = "select seq from TZ_PROJREP ";
            sql1 += " where subj=" + SQLString.Format(v_subj);
            sql1 += "   and year=" + SQLString.Format(v_year);
            sql1 += "   and subjseq=" + SQLString.Format(v_subjseq);
            sql1 += "   and ordseq=" + SQLString.Format(v_ordseq);
            sql1 += "   and projid=" + SQLString.Format(v_user_id);
            ls = connMgr.executeQuery(sql1);

            //기존파일 정보 불러오기
            sql3 = "select  upfile,";
            sql3 += "		upfilesize,";
            sql3 += "		realfile  ";
            sql3 += "from	TZ_PROJREP ";
            sql3 += "where   subj	= '" + v_subj + "' and ";
            sql3 += "		year	= '" + v_year + "' and ";
            sql3 += "		subjseq = '" + v_subjseq + "' and ";
            sql3 += "		ordseq  = '" + v_ordseq + "' and ";
            sql3 += "		projid  = '" + v_user_id + "' ";
            ls3 = connMgr.executeQuery(sql3);
            if (ls3.next()) {
                v_oldupfile = ls3.getString("upfile");
                v_oldrealfile = ls3.getString("realfile");
                v_oldupfilesize = ls3.getString("upfilesize");
            }

            //업로드한 파일이 없을 경우
            if (v_realFileName1.equals("")) {
                //기존파일 삭제
                if (v_check.equals("Y")) {
                    v_newFileName1 = "";
                    fileSize = "";
                    v_realFileName1 = "";
                } else {
                    //기존파일 유지
                    v_newFileName1 = v_oldupfile;
                    fileSize = v_oldupfilesize;
                    v_realFileName1 = v_oldrealfile;
                }
            }

            if (ls.next()) {
                System.out.println("LS NEXT");
                sql2 = "update TZ_PROJREP set resend=1, title=?,contents=?,upfile=?,upfilesize=?, realfile=?, luserid=?,ldate=to_char(sysdate,'YYYYMMDDHH24MISS'),indate=to_char(sysdate,'YYYYMMDDHH24MISS') ";
                sql2 += "where subj=? and year=? and subjseq=? ";
                sql2 += "and ordseq=? and projid=? ";
                pstmt2 = connMgr.prepareStatement(sql2);
                pstmt2.setString(1, v_title);
                pstmt2.setString(2, v_contents);
                pstmt2.setString(3, v_newFileName1);
                pstmt2.setString(4, fileSize);
                pstmt2.setString(5, v_realFileName1);
                pstmt2.setString(6, v_user_id);
                pstmt2.setString(7, v_subj);
                pstmt2.setString(8, v_year);
                pstmt2.setString(9, v_subjseq);
                pstmt2.setInt(10, v_ordseq);
                pstmt2.setString(11, v_user_id);
                isOk = pstmt2.executeUpdate();
            } else {
                System.out.println("LS NOT NEXT");
                sql2 = "insert into TZ_PROJREP(subj,year,subjseq,ordseq,projid,seq,lesson,title,contents,  upfile,upfilesize,realfile,luserid,ldate, ";
                sql2 += "					   indate, contentsbyte, projseq ) ";
                sql2 += "values(?,?,?,?,?,?,?,?,?,  ?,?,?,?,to_char(sysdate,'YYYYMMDDHH24MISS'), ";
                //sql2 += "	   (select eduend from tz_subjseq where subj=? and year=? and subjseq=?) , ?, ? )";
                sql2 += "	   to_char(sysdate,'YYYYMMDDHH24MISS') , ?, ? )";
                pstmt2 = connMgr.prepareStatement(sql2);
                pstmt2.setString(1, v_subj);
                pstmt2.setString(2, v_year);
                pstmt2.setString(3, v_subjseq);
                pstmt2.setInt(4, v_ordseq);
                pstmt2.setString(5, v_user_id);
                pstmt2.setInt(6, 1);
                pstmt2.setString(7, "000");
                pstmt2.setString(8, v_title);
                pstmt2.setString(9, v_contents);

                pstmt2.setString(10, v_newFileName1);
                pstmt2.setString(11, fileSize);
                pstmt2.setString(12, v_realFileName1);
                pstmt2.setString(13, v_user_id);

                //pstmt2.setString(14,v_subj);
                //pstmt2.setString(15,v_year);
                //pstmt2.setString(16,v_subjseq);

                pstmt2.setString(14, v_contentsbyte);
                pstmt2.setInt(15, v_projseq);
                isOk = pstmt2.executeUpdate();
            }

        } catch (Exception ex) {
            throw new Exception("sql1 = " + sql1 + "\r\n" + ex.getMessage());
        } finally {
            if (ls3 != null) {
                try {
                    ls3.close();
                } catch (Exception e) {
                }
            }
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
     * 모사답안비교 체크 등록 처리
     * 
     * @param box receive from the form object and session
     * @return int
     */
    public int updateProjectCopyRegist(RequestBox box) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt = null;
        int isOk = 0;
        String sql = "";

        String v_subj = box.getString("p_subj"); //과정
        String v_year = box.getString("p_year"); //년도
        String v_subjseq = box.getString("p_subjseq"); //과정차수
        int v_listcnt = box.getInt("p_listcnt"); //전체리스트갯수
        String v_copyupnm = "";
        String v_copysize = "";
        String v_copybyte = "";
        String v_projrep = "";

        try {
            connMgr = new DBConnectionManager();

            for (int i = 0; i < v_listcnt; i++) {
                String[] v_copy = new String[3];
                v_copyupnm = box.getString("p_copyupnm" + i);
                v_copysize = box.getString("p_copysize" + i);
                v_copybyte = box.getString("p_copybyte" + i);
                v_projrep = box.getString("p_projrep" + i);
                String[] tokens = v_projrep.split("\\|"); // 개별 정보

                // 첨부파일
                if (!v_copyupnm.equals(""))
                    v_copy[0] = "Y";
                // 파일사이즈
                if (!v_copysize.equals(""))
                    v_copy[1] = "Y";
                // 내용바이트수
                if (!v_copybyte.equals(""))
                    v_copy[2] = "Y";

                sql = " update tz_projrep set copyupnm=?, copysize=?, copybyte=?  " + " where subj=? and year=? and subjseq=? and ordseq=? and projid=? and seq=?  ";

                pstmt = connMgr.prepareStatement(sql);
                pstmt.setString(1, v_copy[0]);
                pstmt.setString(2, v_copy[1]);
                pstmt.setString(3, v_copy[2]);
                pstmt.setString(4, v_subj);
                pstmt.setString(5, v_year);
                pstmt.setString(6, v_subjseq);
                pstmt.setInt(7, Integer.parseInt(tokens[0]));
                pstmt.setString(8, tokens[1]);
                pstmt.setInt(9, Integer.parseInt(tokens[2]));
                isOk = pstmt.executeUpdate();
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
}