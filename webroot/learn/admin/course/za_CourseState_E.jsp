<%
/**
 * file name : za_CourseState_E.jsp
 * date      :
 * programmer:
 * function  : 과정운영정보 리스트 조회화면
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>

<%  response.setHeader("Content-Disposition", "inline; filename=za_CoursePorgress_E.xls");
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_process   = box.getString("p_process");
    String  v_grcode    = "";
    String  v_grcodenm  = "";
    String  v_gyear     = "";
    String  v_grseq     = "";
    String  v_course    = "";
    String  v_cyear     = "";
    String  v_courseseq = "";
    String  v_coursenm  = "";
    String  v_subj      = "";
    String  v_year      = "";
    String  v_subjseq   = "";
    String  v_subjseqgr = "";
    String  v_subjnm    = "";
    String  v_isonoff   = "";
    String  v_edustart  = "";
    String  v_eduend    = "";
    String  v_isgoyong  = "";
    String  v_biyong_view       = "";
    String  v_goyongprice_view  = "";
    String  v_iscourseYn = "";
    String  v_tmp_subj  = "";

    int     v_wstep     = 0;
    int     v_wmtest    = 0;
    int     v_wftest    = 0;
    int     v_whtest    = 0;
    int     v_wreport   = 0;
    int     v_wact      = 0;
    int     v_gradstep  = 0;
    int     v_gradscore = 0;
    int     v_biyong      = 0;
    int     v_goyongprice = 0;

    int     v_cnt_mexam = 0;
    int     v_cnt_texam = 0;
    int     v_cnt_hexam = 0;
    int     v_cnt_proj  = 0;
    int     v_cnt_act   = 0;
    int     v_cnt_ord   = 0;

    String  v_scsubjseq   = "";
    String  v_subjtypenm  = "";
    String  v_isnewcourse = "";
    int     v_subjcnt     =  0;
    int     i = 0;
    int     l = 0;

    ArrayList list      = null;
    list = (ArrayList)request.getAttribute("selectList");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" height="30"><font size="3"><b>과정운영정보 리스트</b></font></td>
    </tr>
  <tr>
    <td align="center" valign="top">
        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5" border="1">
          <tr>
            <td width="3%" rowspan="2"><b>NO</b></td>
            <td width="20%" rowspan="2" colspan="2"><b>과정</b></td>
            <td width="5%" rowspan="2"><b>절대<br>차수</b></td>
            <td width="5%" rowspan="2"><b>차수</b></td>
            <td width="11%" rowspan="2"><b>교육기간</b></td>
            <td width="5%" rowspan="2"><b>진도율<br>가중치<br>(%)</b></td>
            <td colspan="2"><b>중간평가</b></td>
            <td colspan="2"><b>최종평가</b></td>
            <td colspan="2"><b>형성평가</b></td>
            <td colspan="2"><b>리포트</b></td>
            <td width="5%" rowspan="2"><b>이수<br>진도율<br>(%)</b></td>
            <td width="6%" rowspan="2"><b>이수기준<br>점수</b></td>
          </tr>
          <tr>
            <td width="5%" ><b>가중치(%)</b></td>
            <td width="3%"><b>개수</b></td>
            <td width="5%"><b>가중치(%)</b></td>
            <td width="3%"><b>개수</b></td>
            <td width="5%"><b>가중치(%)</b></td>
            <td width="3%"><b>개수</b></td>
            <td width="4%"><b>가중치(%)</b></td>
            <td width="3%"><b>개수</b></td>
          </tr>
          <%
            for(i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);

                v_scsubjseq = dbox.getString("d_scsubjseq"); // 절대차수
                v_grcode    = dbox.getString("d_grcode");
                v_grcodenm  = dbox.getString("d_grcodenm");
                v_gyear     = dbox.getString("d_gyear");
                v_grseq     = dbox.getString("d_grseq");
                v_course    = dbox.getString("d_course");
                v_cyear     = dbox.getString("d_cyear");
                v_courseseq = dbox.getString("d_courseseq");
                v_coursenm  = dbox.getString("d_coursenm");
                v_subj      = dbox.getString("d_subj");
                v_year      = dbox.getString("d_year");
                v_subjseq   = dbox.getString("d_subjseq");
                v_subjseqgr = dbox.getString("d_subjseqgr");
                v_subjnm    = dbox.getString("d_subjnm");
                v_isonoff   = dbox.getString("d_isonoff");
                v_edustart  = dbox.getString("d_edustart");
                v_eduend    = dbox.getString("d_eduend");
                v_isgoyong  = dbox.getString("d_isgoyong");
                v_wstep     = dbox.getInt("d_wstep");
                v_wmtest    = dbox.getInt("d_wmtest");;
                v_wftest    = dbox.getInt("d_wftest");
                v_whtest    = dbox.getInt("d_whtest");
                v_wreport   = dbox.getInt("d_wreport");;
                v_wact      = dbox.getInt("d_wact");
                v_gradstep  = dbox.getInt("d_gradstep");
                v_gradscore = dbox.getInt("d_gradscore");
                v_cnt_mexam = dbox.getInt("d_cnt_mexam");
                v_cnt_texam = dbox.getInt("d_cnt_texam");
                v_cnt_hexam = dbox.getInt("d_cnt_hexam");
                v_cnt_proj  = dbox.getInt("d_cnt_proj");
                v_cnt_ord   = dbox.getInt("d_cnt_ord");//문제수

                v_biyong      = dbox.getInt("d_biyong");
                v_goyongprice = dbox.getInt("d_goyongprice");

                v_subjtypenm  = dbox.getString("d_subjtypenm");
                v_subjcnt     = dbox.getInt("d_subjcnt");
                v_iscourseYn = dbox.getString("d_isbelongcourse");

                v_biyong_view       = NumberFormat.getInstance().format(v_biyong);
                v_goyongprice_view  = NumberFormat.getInstance().format(v_goyongprice);
               %>
          <tr>
            <td class="table_01"><%= i+1 %></td>
<% if(v_iscourseYn.equals("Y"))
    {
        if(l == v_subjcnt) v_tmp_subj = "";
        if(!v_tmp_subj.equals(v_course) && !v_tmp_subj.equals("000000"))
        {
             l = 1;
%>
            <td class="table_02_2" rowspan="<%=v_subjcnt%>" ><%= v_coursenm %></td>
            <td class="table_02_2" ><%=v_subjnm%></td>
<%
        }else{
             l++;
%>
            <td class="table_02_2" ><%=v_subjnm%></td>
<%      }
        v_tmp_subj = v_course;
    } else { 
%>
            <td class="table_02_2" colspan="2"><%=v_subjnm%></td>
<% } %>
            <td class="table_02_1"><font color=red><%=StringManager.cutZero(v_scsubjseq)%></font></td>
            <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
            <td class="table_02_1"><%= FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd") %> ~ <%= FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd") %></td>
            <td class="table_02_1"><%= v_wstep %></td>
            <td class="table_02_1"><%= v_wmtest %></td>
            <td class="table_02_1"><%= v_cnt_mexam %></td>
            <td class="table_02_1"><%= v_wftest %></td>
            <td class="table_02_1"><%= v_cnt_texam %></td>
            <td class="table_02_1"><%= v_whtest %></td>
            <td class="table_02_1"><%= v_cnt_hexam %></td>
            <td class="table_02_1"><%= v_wreport %>
            </td>
            <td class="table_02_1"><%= v_cnt_proj %>/<%=v_cnt_ord%></td>
            <td class="table_02_1"><%= v_gradstep %></td>
            <td class="table_02_1"><%= v_gradscore %></td>
          </tr>
          <%
          }
          if(v_process.equals("select") && i == 0){ %>
          <tr>
            <td class="table_02_1" colspan="17">등록된 내용이 없습니다</td>
          </tr>
          <%  } %>
        </table>
      </td>
  </tr>
</table>
</body>
</html>