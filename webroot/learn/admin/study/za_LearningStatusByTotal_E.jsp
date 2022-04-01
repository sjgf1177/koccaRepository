<%
//**********************************************************
//  1. 제      목: LEARNING STATUS LIST BY SCORE
//  2. 프로그램명: za_LearningStatusByTotal_E.jsp
//  3. 개      요: 종합 학습현황
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: lyh
//***********************************************************
%>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.credu.study.*" %>
<%@ page import="com.credu.common.*" %>
<%@ page import="com.credu.library.*" %>
<%@ page import="com.credu.system.*" %>
<%@ page import="com.credu.system.*" %>

<%//@ page language="java" contentType="application/vnd.ms-excel;name=My_Excel;charset=euc-kr" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    response.setContentType("application/vnd.ms-text");
    response.setHeader("Content-Disposition", "inline; filename=za_LearningStatusByTotal_E.xls");
    response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    ArrayList list = null;
    list = (ArrayList)request.getAttribute("LearningStatusExcelByTotal");


    String v_subj = "";
    String v_subjgubun = "";
    String v_course = "";
    String v_courseseq = "";
    String v_coursenm   = "";
    String v_year = "";
    String v_subjseq = "";
    String v_subjnm = "";
    String v_subjseqgr = "";
    String v_userid = "";
    String v_name = "";
    String v_resno = "";
    String v_membergubunnm = "";
    String v_comptel = "";
    String v_email = "";
    String v_handphone = "";
    String v_gradu_yn = "";
    String v_firstedu = "";
    String v_ldatestart = "";
    String v_ldateend = "";
    String v_totaltime = "";
    String v_totalminute = "";
    String v_sdate = "";
    String v_isgraduated = "";
    String v_isbelongcourse = "";       //전문가과정 여부
    String v_biyong_name="";
    String  v_memberyear = ""; //나이
    String  v_sex       = ""; //성별
    String  v_addr      = ""; //지역
    String  v_compnm    = ""; //회사명
    String  v_workfieldcdnm     = ""; //부서명

    int v_avg_time = 0;
    int v_lessoncount = 0;
    int v_eduavgmin = 0;
    int v_edutotmin = 0;
    double v_tstep = 0;
    double v_report = 0;
    double v_act = 0;
    double v_mtest = 0;
    double v_ftest = 0;
    double v_htest = 0;
    double v_etc1 = 0;
    double v_etc2 = 0;
    double v_avtstep = 0;
    double v_avreport = 0;
    double v_avact = 0;
    double v_avmtest = 0;
    double v_avftest = 0;
    double v_avhtest = 0;
    double v_avetc1 = 0;
    double v_avetc2 = 0;
    double v_score = 0;
    int v_point = 0;
    double v_avg_tstep = 0;
    double v_avg_report= 0;
    double v_avg_act = 0;
    double v_avg_mtest = 0;
    double v_avg_ftest = 0;
    double v_avg_htest = 0;
    double v_avg_etc1 = 0;
    double v_avg_etc2 = 0;
    double v_avg_avtstep= 0;
    double v_avg_avreport= 0;
    double v_avg_avact = 0;
    double v_avg_avmtest = 0;
    double v_avg_avftest = 0;
    double v_avg_avhtest = 0;
    double v_avg_avetc1 = 0;
    double v_avg_avetc2 = 0;
    double v_avg_score = 0;
    int v_total = 0;
    int v_samtotal = 0;
    int i = 0;
    int v_subjcnt = 0;      //전문가과정일경우 속한 과정수
%>
<HTML>
<HEAD>
<TITLE></TITLE>

</HEAD>

<BODY leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >

<!----------------- 성적별 학습현황 시작 ----------------->
<table cellspacing="1" cellpadding="5" class="table_out" border="1">
    <tr>
        <td colspan="21" class="table_top_line"></td>
    </tr>
    <tr>
        <td class="table_title" width="3%" >N0</td>
        <td class="table_title" colspan="2">과정명</td>
        <td class="table_title">목차 개수</td>
        <td class="table_title">교육구분</td>
        <td class="table_title">회원구분</td>
        <td class="table_title">ID</td>
        <td class="table_title">성명</td>
        <td class="table_title">나이</td>
        <td class="table_title">성별</td>
        <td class="table_title">지역</td>
        <td class="table_title">email</td>
        <td class="table_title">이동전화</td>
        <td class="table_title">회사명</td>
        <td class="table_title">부서</td>
        <td class="table_title">교육그룹<br>개설차수</td>
        <td class="table_title">교육그룹내<br>과정차수</td>
        <td class="table_title">진도율</td>
        <td class="table_title">진도율 점수</td>
        <!-- <td class="table_title">중간평가</td>
        <td class="table_title">중간평가 점수</td>
        <td class="table_title">최종평가</td>
        <td class="table_title">최종평가 점수</td>
        <td class="table_title">형성평가</td>
        <td class="table_title">형성평가 점수</td>
        <td class="table_title">리포트</td>
        <td class="table_title">리포트 점수</td>
        <td class="table_title">참여도</td>
        <td class="table_title">참여도 점수</td>
        <td class="table_title">기타</td>
        <td class="table_title">기타 점수</td> -->
        <td class="table_title">총점</td>
        <!-- <td class="table_title">접속<br>횟수</td>
        <td class="table_title">학습<br>시간(분)</td>
        <td class="table_title">1회<br>학습시간<br>(분)</td>
        <td class="table_title">최종상담<br>일자</td>
        <td class="table_title">평가미응시<br>갯수</td>
        <td class="table_title">설문미응시<br>갯수</td>
        <td class="table_title">과제<br>미제출<br>갯수</td> -->
        <td class="table_title">수료<br>가능</td>
        <!-- <td class="table_title">유무료</td> -->
    </tr>
<%
    int v_avg_edutotmin = 0;
    int v_avg_lessoncount = 0;
    int v_avg_eduavgmin = 0;
    int v_totsulcnt = 0;
    int v_repsulcnt = 0;
    int v_totexamcnt = 0;
    int v_repexamcnt = 0;
    int v_totprojcnt = 0;
    int v_repprojcnt = 0;
    int v_isretcnt = 0; // 레포트 반려갯수
    int v_smsnum     = 0; // sms발송수
    int v_mailnum    = 0; // mail발송수
    int v_counselnum = 0; // 상담수

    DataBox dbox = null;
    String preCourse = "";          //이전출력과정 - 000000이면 일반과정이고 이외는 전문가과정
    int l = 0;


    //내용이 있고 없음여부
    if( list.size() != 0 ){     // 검색된 내용이 있다면

                v_total = list.size();
            for(i = 0; i < v_total; i++) {
                dbox = (DataBox)list.get(i);

                v_subj          = dbox.getString("d_subj");
                v_subjgubun     = dbox.getString("d_area");
                    if(v_subjgubun.equals("B0")){
                        v_subjgubun = "방송";
                    }else if(v_subjgubun.equals("G0")){
                        v_subjgubun = "게임";
                    }else if(v_subjgubun.equals("K0")){
                        v_subjgubun = "문콘";
                    }
                v_courseseq     = dbox.getString("d_courseseq");
                v_course        = dbox.getString("d_course");
                v_coursenm    = dbox.getString("d_coursenm");
                v_year          = dbox.getString("d_year");
                v_subjseq       = dbox.getString("d_subjseq");
                v_subjnm        = dbox.getString("d_subjnm");
                v_subjseqgr     = dbox.getString("d_subjseqgr");
                v_userid        = dbox.getString("d_userid");
                v_name          = dbox.getString("d_name");
                if(!dbox.getString("d_resno1").equals("")){
                v_resno         = dbox.getString("d_resno1")+"-"+dbox.getString("d_resno2");
                }else if(!dbox.getString("d_resno").equals("") && dbox.getString("d_resno").length()==13){
                v_resno         = dbox.getString("d_resno").substring(0,6)+"-"+dbox.getString("d_resno").substring(6,13);
                }else{
                    v_resno = dbox.getString("d_resno");
                }
                v_membergubunnm = dbox.getString("d_membergubunnm");
                v_email         = dbox.getString("d_email");
                v_handphone         = dbox.getString("d_handphone");

                v_tstep         = dbox.getDouble("d_tstep");
                v_avtstep       = dbox.getDouble("d_avtstep");
                v_report        = dbox.getDouble("d_report");
                v_avreport      = dbox.getDouble("d_avreport");

                v_memberyear    = dbox.getString("d_memberyear");
                v_sex           = dbox.getString("d_sex");
                    if(v_sex.equals("1")){
                        v_sex = "남";
                    }else if(v_sex.equals("2")){
                        v_sex = "여";
                    }
                v_addr          = dbox.getString("d_addr");

                //out.println(v_avreport);

                v_act           = dbox.getDouble("d_act");
                v_avact         = dbox.getDouble("d_avact");
                v_mtest         = dbox.getDouble("d_mtest");
                v_avmtest       = dbox.getDouble("d_avmtest");
                v_ftest         = dbox.getDouble("d_ftest");
                v_avftest       = dbox.getDouble("d_avftest");
                v_htest         = dbox.getDouble("d_htest");
                v_avhtest       = dbox.getDouble("d_avhtest");
                v_etc1          = dbox.getDouble("d_etc1");
                v_avetc1        = dbox.getDouble("d_avetc1");
                v_etc2          = dbox.getDouble("d_etc2");
                v_avetc2        = dbox.getDouble("d_avetc2");
                v_score         = dbox.getDouble("d_score");

                v_point     = dbox.getInt("d_point");
                v_isgraduated   = dbox.getString("d_isgraduated");
                v_compnm   = dbox.getString("d_compnm");
                v_workfieldcdnm   = dbox.getString("d_deptnam");

                /*

                v_firstedu      = dbox.getString("d_firstedu");
                v_lessoncount   = dbox.getInt("d_lessoncount");
                v_totaltime     = dbox.getString("d_totaltime");
                v_totalminute   = dbox.getString("d_totalminute");
                v_ldatestart    = dbox.getString("d_ldatestart");
                v_ldateend      = dbox.getString("d_ldateend");
                v_sdate          = dbox.getString("d_sdate");

                v_totsulcnt      = dbox.getInt("d_totsulcnt");
                v_repsulcnt      = dbox.getInt("d_repsulcnt");
                v_totexamcnt     = dbox.getInt("d_totexamcnt");
                v_repexamcnt     = dbox.getInt("d_repexamcnt");
                v_totprojcnt     = dbox.getInt("d_totprojcnt");
                v_repprojcnt     = dbox.getInt("d_repprojcnt");
                v_samtotal       = dbox.getInt("d_samtotal");
                v_isretcnt       = dbox.getInt("d_isretcnt");
                v_subjcnt    = dbox.getInt("d_subjcnt");            // 전문가과정의 과목수
                v_isbelongcourse = dbox.getString("d_isbelongcourse");
                v_biyong_name=dbox.getString("d_biyong_name");
                v_firstedu      = FormatDate.getFormatDate(v_firstedu,"yyyy/MM/dd-HH:mm:ss");
                v_ldatestart    = FormatDate.getFormatDate(v_ldatestart,"yyyy/MM/dd-HH:mm:ss");
                v_ldateend      = FormatDate.getFormatDate(v_ldateend,"yyyy/MM/dd-HH:mm:ss");
                // v_sdate         = FormatDate.getFormatDate(v_sdate ,"yyyy/MM/dd HH:mm:ss");
                v_sdate         = FormatDate.getFormatDate(v_sdate ,"yyyy/MM/dd");


                v_edutotmin = Integer.parseInt(v_totaltime)*60 + Integer.parseInt(v_totalminute);
                v_edutotmin = Integer.parseInt(v_totalminute);
                if(v_lessoncount != 0){
                    v_eduavgmin = v_edutotmin/v_lessoncount;
                }
                else{
                    v_eduavgmin = 0;
                }

                if(v_firstedu.length() > 0)  { v_firstedu = v_firstedu.substring(0,16);      }
                if(v_ldatestart.length() > 0){ v_ldatestart=v_ldatestart.substring(0,16);    }
                if(v_ldateend.length() > 0)  { v_ldateend = v_ldateend.substring(0,16);      }
                if(v_totaltime.length() > 0) {
                  v_avg_time += Integer.parseInt(v_totaltime);
                }
                */


%>
                 <tr>
<%
            //전문가과정이라면
            if( v_isbelongcourse.equals("Y")){

                    if(l == v_subjcnt) preCourse = "";

                    if( !preCourse.equals("000000") && !preCourse.equals(v_course) ) {
                        l = 1;
%>

                      <td class="table_01"><%=v_total-i%></td>
                       <td class="table_02_1" rowspan="<%=v_subjcnt%>"><%=v_coursenm%><br><%=StringManager.cutZero(v_courseseq)%>차</td>
                       <td class="table_02_1">
                            <font class="text_color04" >
                            <CENTER><%=v_subjnm%></CENTER></font></td>
                     <td class="table_02_1"><%= dbox.getString("d_lesson_cnt") %></td>
                      <td class="table_02_1"><%=v_subjgubun%></td>
                      <td class="table_02_1"><%=v_membergubunnm%></td>
                      <td class="table_02_1" style='mso-number-format:"\@";'><%= v_userid %></td>
                      <td class="table_02_1"><%= v_name%></td>
                      <td class="table_02_1"><%= v_memberyear%></td>
                      <td class="table_02_1"><%= v_sex%></td>
                      <td class="table_02_1"><%= v_addr%></td>
                      <td class="table_02_1"><%= v_email%></td>
                      <td class="table_02_1" style='mso-number-format:"\@";'><%= v_handphone%></td>
                      <td class="table_02_1"><%= v_compnm%>%</td>
                      <td class="table_02_1"><%= v_workfieldcdnm%>%</td>
                      <td class="table_02_1"><%= StringManager.cutZero(dbox.getString("d_grseq"))%></td>
                      <td class="table_02_1"><%= StringManager.cutZero(v_subjseqgr)%></td>
                      <td class="table_02_1"><%= v_tstep%>%</td>
                      <%-- <td class="table_02_1"><%= v_avtstep %></td>
                      <td class="table_02_1"><%= v_mtest%></td>
                      <td class="table_02_1"><%= v_avmtest %></td>
                      <td class="table_02_1"><%= v_ftest%></td>
                      <td class="table_02_1"><%= v_avftest %></td>
                      <td class="table_02_1"><%= v_htest%></td>
                      <td class="table_02_1"><%= v_avhtest %></td>
                      <td class="table_02_1"><%= v_report%></td>
                      <td class="table_02_1"><%= v_avreport %></td>
                      <td class="table_02_1"><%= v_etc1%></td>
                      <td class="table_02_1"><%= v_avetc1 %></td>
                      <td class="table_02_1"><%= v_etc2%></td>
                      <td class="table_02_1"><%= v_avetc2 %></td> --%>
                      <td class="table_02_1"><%= v_score%></td>
                      <%-- <td class="table_02_1"><%= v_lessoncount%></td>
                      <td class="table_02_1"><%= v_edutotmin%></td>
                      <td class="table_02_1"><%= v_eduavgmin%></td>
                      <td class="table_02_1"><%= v_sdate%></td>
                      <td class="table_02_1"><%if(v_totexamcnt>0){if((v_totexamcnt-v_repexamcnt) == 0){out.print("완료");}else{out.print(v_totexamcnt-v_repexamcnt);;out.print("("+v_totexamcnt+")");}}else{out.println("-");}%></td>
                      <td class="table_02_1"><%if(v_totsulcnt>0){if((v_totsulcnt-v_repsulcnt) == 0){out.print("완료");}else{out.print(v_totsulcnt-v_repsulcnt);out.print("("+v_totsulcnt+")");}}else{out.println("-");}%></td>
                      <td class="table_02_1"><%if(v_totprojcnt>0){if((v_totprojcnt-v_repprojcnt+v_isretcnt) == 0){out.print("완료");}else{out.print(v_totprojcnt-v_repprojcnt+v_isretcnt);out.print("("+v_totprojcnt+")");if(v_isretcnt > 0){out.println("<font color='red'>반려</font>");}}}else{out.println("-");}%></td> --%>
                     <td class="table_02_1"><%= v_isgraduated %></td>
                     <%-- <td class="table_02_1"><%= v_biyong_name %></td> --%>


<%
                    v_avg_tstep       += v_tstep;
                    v_avg_report      += v_report;
                    v_avg_act         += v_act;
                    v_avg_mtest       += v_mtest;
                    v_avg_ftest       += v_ftest;
                    v_avg_htest       += v_htest;
                    v_avg_etc1        += v_etc1;
                    v_avg_etc2        += v_etc2;
                    v_avg_avtstep     += v_avtstep;
                    v_avg_avreport    += v_avreport;
                    v_avg_avact       += v_avact;
                    v_avg_avmtest     += v_avmtest;
                    v_avg_avftest     += v_avftest;
                    v_avg_avhtest     += v_avhtest;
                    v_avg_avetc1      += v_avetc1;
                    v_avg_avetc2      += v_avetc2;
                    v_avg_score       += v_score;
                    v_avg_edutotmin   += v_edutotmin;
                    v_avg_lessoncount += v_lessoncount;
                    v_avg_eduavgmin   += v_eduavgmin;

                    preCourse = v_course;

                    }else{
                        l++;
%>

                      <td class="table_01"><%=v_total-i%></td>
                       <td class="table_02_1">
                            <font class="text_color04" >
                            <CENTER><%=v_subjnm%></CENTER></font></td>
                     <td class="table_02_1"><%= dbox.getString("d_lesson_cnt") %></td>
                      <td class="table_02_1"><%=v_subjgubun%></td>
                      <td class="table_02_1"><%=v_membergubunnm%></td>
                      <td class="table_02_1"><%= v_userid %></td>
                      <td class="table_02_1"><%=v_name %></td>
                      <td class="table_02_1"><%= v_memberyear%></td>
                      <td class="table_02_1"><%= v_sex%></td>
                      <td class="table_02_1"><%= v_addr%></td>
                      <td class="table_02_1"><%= v_email%></td>
                      <td class="table_02_1" style='mso-number-format:"\@";'><%= v_handphone%></td>
                       <td class="table_02_1"><%=v_compnm%>%</td>
                      <td class="table_02_1"><%=v_workfieldcdnm%>%</td>
                      <td class="table_02_1"><%= StringManager.cutZero(dbox.getString("d_grseq"))%></td>
                      <td class="table_02_1"><%= StringManager.cutZero(v_subjseqgr)%></td>
                      <td class="table_02_1"><%= v_tstep%>%</td>
                      <td class="table_02_1"><%= v_avtstep %></td>
                      <%-- <td class="table_02_1"><%= v_mtest%></td>
                      <td class="table_02_1"><%= v_avmtest %></td>
                      <td class="table_02_1"><%= v_ftest%></td>
                      <td class="table_02_1"><%= v_avftest %></td>
                      <td class="table_02_1"><%= v_htest%></td>
                      <td class="table_02_1"><%= v_avhtest %></td>
                      <td class="table_02_1"><%= v_report%></td>
                      <td class="table_02_1"><%= v_avreport %></td>
                      <td class="table_02_1"><%= v_etc1%></td>
                      <td class="table_02_1"><%= v_avetc1 %></td>
                      <td class="table_02_1"><%= v_etc2%></td>
                      <td class="table_02_1"><%= v_avetc2 %></td> --%>
                      <td class="table_02_1"><%= v_score%></td>
                      <%-- <td class="table_02_1"><%= v_lessoncount%></td>
                      <td class="table_02_1"><%= v_edutotmin%></td>
                      <td class="table_02_1"><%= v_eduavgmin%></td>
                      <td class="table_02_1"><%= v_sdate%></td>
                      <td class="table_02_1"><%if(v_totexamcnt>0){if((v_totexamcnt-v_repexamcnt) == 0){out.print("완료");}else{out.print(v_totexamcnt-v_repexamcnt);;out.print("("+v_totexamcnt+")");}}else{out.println("-");}%></td>
                      <td class="table_02_1"><%if(v_totsulcnt>0){if((v_totsulcnt-v_repsulcnt) == 0){out.print("완료");}else{out.print(v_totsulcnt-v_repsulcnt);out.print("("+v_totsulcnt+")");}}else{out.println("-");}%></td>
                      <td class="table_02_1"><%if(v_totprojcnt>0){if((v_totprojcnt-v_repprojcnt+v_isretcnt) == 0){out.print("완료");}else{out.print(v_totprojcnt-v_repprojcnt+v_isretcnt);out.print("("+v_totprojcnt+")");if(v_isretcnt > 0){out.println("<font color='red'>반려</font>");}}}else{out.println("-");}%></td> --%>
                      <td class="table_02_1"><%=v_isgraduated%></td>
                      <%-- <td class="table_02_1"><%=v_biyong_name%></td> --%>

<%
                    v_avg_tstep       += v_tstep;
                    v_avg_report      += v_report;
                    v_avg_act         += v_act;
                    v_avg_mtest       += v_mtest;
                    v_avg_ftest       += v_ftest;
                    v_avg_htest       += v_htest;
                    v_avg_etc1        += v_etc1;
                    v_avg_etc2        += v_etc2;
                    v_avg_avtstep     += v_avtstep;
                    v_avg_avreport    += v_avreport;
                    v_avg_avact       += v_avact;
                    v_avg_avmtest     += v_avmtest;
                    v_avg_avftest     += v_avftest;
                    v_avg_avhtest     += v_avhtest;
                    v_avg_avetc1      += v_avetc1;
                    v_avg_avetc2      += v_avetc2;
                    v_avg_score       += v_score;
                    v_avg_edutotmin   += v_edutotmin;
                    v_avg_lessoncount += v_lessoncount;
                    v_avg_eduavgmin   += v_eduavgmin;

                    }


            }else{      //전문가과정이 아니면
%>

                      <td class="table_01"><%=v_total-i%></td>
                      <td class="table_02_1" colspan="2">
                          <font class="text_color04" align="center">
                          <CENTER><%=v_subjnm%></CENTER></font></td>
                     <td class="table_02_1"><%= dbox.getString("d_lesson_cnt") %></td>
                      <td class="table_02_1"><%=v_subjgubun%></td>
                      <td class="table_02_1"><%=v_membergubunnm%></td>
                      <td class="table_02_1"><%= v_userid %></td>
                      <td class="table_02_1"><%=v_name %></td>
                      <td class="table_02_1"><%= v_memberyear%></td>
                      <td class="table_02_1"><%= v_sex%></td>
                      <td class="table_02_1"><%= v_addr%></td>
                      <td class="table_02_1"><%= v_email%></td>
                      <td class="table_02_1" style='mso-number-format:"\@";'><%= v_handphone%></td>
                       <td class="table_02_1"><%= v_compnm %></td>
                      <td class="table_02_1"><%= v_workfieldcdnm %></td>
                      <td class="table_02_1"><%= StringManager.cutZero(dbox.getString("d_grseq")) %></td>
                      <td class="table_02_1"><%= StringManager.cutZero(v_subjseqgr) %></td>
                      <td class="table_02_1"><%=v_tstep%>%</td>
                      <td class="table_02_1"><%= v_avtstep %></td>
                      <%-- <td class="table_02_1"><%=v_mtest%></td>
                      <td class="table_02_1"><%= v_avmtest %></td>
                      <td class="table_02_1"><%=v_ftest%></td>
                      <td class="table_02_1"><%= v_avftest %></td>
                      <td class="table_02_1"><%=v_htest%></td>
                      <td class="table_02_1"><%= v_avhtest %></td>
                      <td class="table_02_1"><%=v_report%></td>
                      <td class="table_02_1"><%= v_avreport %></td>
                      <td class="table_02_1"><%=v_etc1%></td>
                      <td class="table_02_1"><%= v_avetc1 %></td>
                      <td class="table_02_1"><%=v_etc2%></td>
                      <td class="table_02_1"><%= v_avetc2 %></td> --%>
                      <td class="table_02_1"><%=v_score%></td>
                      <%-- <td class="table_02_1"><%=v_lessoncount%></td>
                      <td class="table_02_1"><%=v_edutotmin%></td>
                      <td class="table_02_1"><%=v_eduavgmin%></td>
                      <td class="table_02_1"><%=v_sdate%></td>
                      <td class="table_02_1"><%if(v_totexamcnt>0){if((v_totexamcnt-v_repexamcnt) == 0){out.print("완료");}else{out.print(v_totexamcnt-v_repexamcnt);;out.print("("+v_totexamcnt+")");}}else{out.println("-");}%></td>
                      <td class="table_02_1"><%if(v_totsulcnt>0){if((v_totsulcnt-v_repsulcnt) == 0){out.print("완료");}else{out.print(v_totsulcnt-v_repsulcnt);out.print("("+v_totsulcnt+")");}}else{out.println("-");}%></td>
                      <td class="table_02_1"><%if(v_totprojcnt>0){if((v_totprojcnt-v_repprojcnt+v_isretcnt) == 0){out.print("완료");}else{out.print(v_totprojcnt-v_repprojcnt+v_isretcnt);out.print("("+v_totprojcnt+")");if(v_isretcnt > 0){out.println("<font color='red'>반려</font>");}}}else{out.println("-");}%></td> --%>
                      <td class="table_02_1"><%=v_isgraduated%></td>
                      <%-- <td class="table_02_1"><%=v_biyong_name%></td> --%>
<%
                    v_avg_tstep       += v_tstep;
                    v_avg_report      += v_report;
                    v_avg_act         += v_act;
                    v_avg_mtest       += v_mtest;
                    v_avg_ftest       += v_ftest;
                    v_avg_htest       += v_htest;
                    v_avg_etc1        += v_etc1;
                    v_avg_etc2        += v_etc2;
                    v_avg_avtstep     += v_avtstep;
                    v_avg_avreport    += v_avreport;
                    v_avg_avact       += v_avact;
                    v_avg_avmtest     += v_avmtest;
                    v_avg_avftest     += v_avftest;
                    v_avg_avhtest     += v_avhtest;
                    v_avg_avetc1      += v_avetc1;
                    v_avg_avetc2      += v_avetc2;
                    v_avg_score       += v_score;
                    v_avg_edutotmin   += v_edutotmin;
                    v_avg_lessoncount += v_lessoncount;
                    v_avg_eduavgmin   += v_eduavgmin;

%>

            </tr>

<%
            }

        }//for


                    DecimalFormat df = new DecimalFormat("###.#");

                    v_avg_tstep     = v_avg_tstep/v_total;
                    v_avg_report    = v_avg_report/v_total;
                    v_avg_act       = v_avg_act/v_total;
                    v_avg_mtest     = v_avg_mtest/v_total;
                    v_avg_ftest     = v_avg_ftest/v_total;
                    v_avg_htest     = v_avg_htest/v_total;
                    v_avg_etc1      = v_avg_etc1/v_total;
                    v_avg_etc2      = v_avg_etc2/v_total;
                    v_avg_avtstep   = v_avg_avtstep/v_total;
                    v_avg_avreport  = v_avg_avreport/v_total;
                    v_avg_avact     = v_avg_avact/v_total;
                    v_avg_avmtest   = v_avg_avmtest/v_total;
                    v_avg_avftest   = v_avg_avftest/v_total;
                    v_avg_avhtest   = v_avg_avhtest/v_total;
                    v_avg_avetc1    = v_avg_avetc1/v_total;
                    v_avg_avetc2    = v_avg_avetc2/v_total;
                    v_avg_score     = v_avg_score/v_total;
                    v_avg_edutotmin   = v_avg_edutotmin/v_total;
                    v_avg_lessoncount = v_avg_lessoncount/v_total;
                    v_avg_eduavgmin   = v_avg_eduavgmin/v_total;
%>
                <tr>
                  <td class="table_02_3" colspan="17">평균</td>
                  <td class="table_02_3"><%=df.format(v_avg_tstep)%>%</td>
                  <td class="table_02_3"><%=df.format(v_avg_avtstep)%></td>
                 <%--  <td class="table_02_3"><%=df.format(v_avg_mtest)%></td>
                  <td class="table_02_3"><%=df.format(v_avg_avmtest)%></td>
                  <td class="table_02_3"><%=df.format(v_avg_ftest)%></td>
                  <td class="table_02_3"><%=df.format(v_avg_avftest)%></td>
                  <td class="table_02_3"><%=df.format(v_avg_htest)%></td>
                  <td class="table_02_3"><%=df.format(v_avg_avhtest)%></td>
                  <td class="table_02_3"><%=df.format(v_avg_report)%></td>
                  <td class="table_02_3"><%=df.format(v_avg_avreport)%></td>
                  <td class="table_02_3"><%=df.format(v_avg_etc1)%></td>
                  <td class="table_02_3"><%=df.format(v_avg_avetc1)%></td>
                  <td class="table_02_3"><%=df.format(v_avg_etc2)%></td>
                  <td class="table_02_3"><%=df.format(v_avg_avetc2)%></td> --%>
                  <td class="table_02_3"><%=df.format(v_avg_score)%></td>
                  <%-- <td class="table_02_3"><%=v_avg_lessoncount%></td>
                  <td class="table_02_3"><%=v_avg_edutotmin%></td>
                  <td class="table_02_3"><%=v_avg_eduavgmin%></td>
                  <td class="table_02_3">&nbsp;</td>
                  <td class="table_02_3">&nbsp;</td>
                  <td class="table_02_3">&nbsp;</td>
                  <td class="table_02_3">&nbsp;</td>
                  <td class="table_02_3">&nbsp;</td>
                  <td class="table_02_1"><%=v_biyong_name%></td> --%>
                  <td class="table_02_1"></td>
                </tr>

<%
    }else{      //내용이 없다면
%>
              <tr>
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="22">등록된 내용이 없습니다</td>
              </tr>
<%

    }
%>
</table>
      <!----------------- 성적별 학습현황 끝 ----------------->

</body>
</HTML>

