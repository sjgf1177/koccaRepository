<%
//**********************************************************
//  1. 제      목: 분석
//  2. 프로그램명: za_JobInclination_L.java
//  3. 개      요: 나이별 분석
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: msc
//  7. 수      정:
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_grcode    = box.getString("s_grcode");        //교육그룹
    String  v_subj      = box.getString("s_subjcourse");    // 과정코드
    String  v_action    = box.getString("p_action");

    String v_code     = "";
    String v_codenm   = "";

    int v_educnt               = 0; //교육인원
    int v_comlcnt              = 0; //수료인원
    int v_ncomlcnt             = 0; //미수료 인원
    int v_educnt_total         = 0; //교육인원 총합
    int v_comlcnt_total        = 0; //수료인원 총합
    int v_ncomlcnt_total       = 0; //미수료인원 총합
    double v_suryopercen       = 0; //수료율
    double v_suryopercen_total = 0; //수료율 총합
    double re = 0;
    double re1 = 0;
    int i = 0;

    ArrayList list = null;
    DataBox dbox = null;

    String v_tab_color1 = "black";
    String v_tab_color2 = "black";
    String v_tab_color3 = "black";
    String v_tab_color4 = "black";
    String v_tab_color5 = "blue";
    DecimalFormat  df = new DecimalFormat("0.00");

    if ( v_action.equals("go") ) {
        list = (ArrayList)request.getAttribute("AgeList");
    }
%>
<html>
<head>
<title>학력별 성향 분석</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
// 조회 검색
function whenSelection(p_action) {
    if (p_action=="go") {
        if (document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
        }

        if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
        }
    }

  document.form1.target = "_self";
  document.form1.p_action.value  = p_action;
  document.form1.p_process.value = 'ageInclinationList';
  document.form1.submit();
}

// 엑셀출력
function goExcel(p_action) {
    if (document.form1.s_grcode.value == '----') {
            alert("교육그룹을 선택하세요.");
            return ;
    }
    if (p_action=="go") {
        if (document.form1.s_subjcourse.value=="----"){
            alert("과정을 선택하세요");
            return;
        }
    }

    //window.self.name = "AgeExcelList";
    //open_window("openExcelList","","100","100","700","400","yes","yes","yes","yes","yes");
    //document.form1.target = "openExcelList";
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.analysis.InclinationAdminServlet';
    document.form1.p_process.value = 'ageExcelList';
    document.form1.submit();
}

// 다른 탭 이동
function changeTabpage(p_gubun) {

    if(p_gubun == "CARRER") {
        document.form1.p_process.value = "carrerInclinationList";
    } else if(p_gubun == "GENDER") {
        document.form1.p_process.value = "genderInclinationList";
    } else if(p_gubun == "AGE"){
        document.form1.p_process.value = "ageInclinationList";
    } else if(p_gubun == "JOB"){
        document.form1.p_process.value = "jobInclinationList";
    }

    document.form1.target = "_self";
    document.form1.action = "/servlet/controller.analysis.InclinationAdminServlet";
    document.form1.p_action.value = 'change';
    //document.form1.p_gubun.value = p_gubun;
    //document.form1.s_grcode.value = "";
    document.form1.submit();
}

// 인원상세정보 팝업
function open_detailList(code, codenm) {
    window.self.name = "AgeDetailList";     //opener 객체에 name 을 지정한다
    farwindow = window.open("", "openAgeDetailList", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 690, height = 567, top=0, left=0");
    document.form1.target = "openAgeDetailList";
    document.form1.action = "/servlet/controller.analysis.InclinationAdminServlet";
    document.form1.p_process.value = "ageDetailList";
    document.form1.p_code.value = code;
    document.form1.p_codenm.value = codenm;
    document.form1.p_grcodenm.value = document.form1.s_grcode.options[document.form1.s_grcode.selectedIndex].text;
    document.form1.p_subjnm.value = document.form1.s_subjcourse.options[document.form1.s_subjcourse.selectedIndex].text;
    document.form1.submit();
    document.form1.target = window.self.name;
}

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.analysis.InclinationAdminServlet">
    <input type="hidden" name="p_process"  value="">
    <input type="hidden" name="p_action"   value="<%=v_action%>">

    <input type="hidden" name="p_code" >
    <input type="hidden" name="p_codenm" >
    <input type="hidden" name="p_grcodenm" >
    <input type="hidden" name="p_subjnm" >

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
          <td><img src="/images/admin/analysis/tit_analysis_stu1.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>

      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center" valign="middle">
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="27%" colspan="2" align="left" valign="middle"> <font color="red">★</font>
                        <%= SelectEduBean.getGrcode(box, true, false)%><!-- getGrcode(RequestBox, isChange, isALL)   교육그룹  -->
                      </td>
                      <td width="60%"colspan="2">
                        <font color="red">★</font>  <%= SelectSubjBean.getSubj(box, true, false)%><!-- getSubj(RequestBox, isChange, isALL)    과정  -->
                       </td>
                      <td width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td><!-- whenSelection('go') -->
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7"></td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <br>
      <br>

      <table cellspacing="0" cellpadding="0" class="table1">
        <tr>
          <td width="85">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_left"></td>
                <td class="<%=v_tab_color3%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color3%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_middle"><a href="javascript:changeTabpage('JOB')" class="c">직업별</td>
              </tr>
              <tr>
                <td class="<%=v_tab_color3%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color2%>_butt_left"></td>
                <td class="<%=v_tab_color2%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color2%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color2%>_butt_middle"><a href="javascript:changeTabpage('CARRER')" class="c">학력별</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color2%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_left"></td>
                <td class="<%=v_tab_color4%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color4%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_middle"><a href="javascript:changeTabpage('GENDER')" class="c">성별</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color4%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr>
                <td rowspan="3" class="<%=v_tab_color5%>_butt_left"></td>
                <td class="<%=v_tab_color5%>_butt_top"></td>
                <td rowspan="3" class="<%=v_tab_color5%>_butt_right"></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color5%>_butt_middle">나이별</a></td>
              </tr>
              <tr>
                <td class="<%=v_tab_color5%>_butt_bottom"></td>
              </tr>
            </table>
          </td>
          <td>&nbsp;</td>
          <td width="2"></td>
          <td width="85" height="23" align="center" valign="middle">
           <table cellpadding="0" cellspacing="0" class="s_table">
            <tr>
              <td align="right">
              &nbsp;&nbsp;<a href="javascript:goExcel('go')" class="c"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a></td>
            </tr>
           </table>
        </tr>
      </table>

      <table class="table_out" cellspacing="1" cellpadding="5">
      <tr>
        <td colspan="11" class="table_top_line"></td>
      </tr>

      <tr>
        <td class="table_title" width="25%">나이</td>
        <td class="table_title" width="17%">교육인원</td>
        <td class="table_title" width="17%">수료인원</td>
        <td class="table_title" width="17%">미수료인원</td>
        <td class="table_title" width="14%">수료율</td>
      </tr>


<%
    if ( v_action.equals("go") && list.size() > 0) {

        String [] v_value = {"50","40","30","20","10", "0"};
        String [] v_text =  {"50대", "40대", "30대", "20대", "10대", "기타"};

        for(int j = 0; j < v_value.length; j++)  {
            v_codenm   = v_text[j];
            v_comlcnt  = 0;
            v_ncomlcnt = 0;
            v_educnt   = 0;

            for (i = 0; i<list.size(); i++ ) {
                dbox = (DataBox)list.get(i);
                v_code     = dbox.getString("d_age");

                if (v_value[j].equals(v_code)) {
                    v_comlcnt  = dbox.getInt   ("d_comlcnt");
                    v_ncomlcnt = dbox.getInt   ("d_ncomlcnt");
                    v_educnt   = v_comlcnt + v_ncomlcnt;
                }
            }
            v_suryopercen = ((v_educnt - v_ncomlcnt) /(double)v_educnt) * 100;
            re = Math.round(v_suryopercen * 1000) / 1000;

            v_educnt_total      += v_educnt;        //교육인원 총합
            v_comlcnt_total     += v_comlcnt;       //수료인원 총합
            v_ncomlcnt_total    += v_ncomlcnt;      //미수료인원 총합
            v_suryopercen_total += v_suryopercen;

            double v_suryo_total_percen = ((v_educnt_total - v_ncomlcnt_total) / (double)v_educnt_total) * 100;
            re1 = Math.round(v_suryo_total_percen * 1000) / 1000;
%>
          <tr class="table_02_1">
            <td class="table_02_1"><%=v_text[j]%></td>
<%
    if(v_educnt > 0) {
%>
            <td class="table_02_1">
                <a href="javascript:open_detailList('<%=v_value[j]%>', '<%=v_text[j]%>')"><%= v_educnt %></a></td>
<%
    } else {
%>
            <td class="table_02_1"><%= v_educnt %></td>
<%
    }
%>
            <td class="table_02_1"><%= v_comlcnt %></td>
            <td class="table_02_1"><%= v_ncomlcnt %></td>
            <td class="table_02_1"><%= re %>%</td>
          </tr>
<%
        }
%>
          <tr>
            <td class="table_title" width="25%">계</td>
            <td class="table_title" width="17%"><%=v_educnt_total%></td>
            <td class="table_title" width="17%"><%=v_comlcnt_total%></td>
            <td class="table_title" width="17%"><%=v_ncomlcnt_total%></td>
            <td class="table_title" width="17%"><%=re1%>%</td>
          </tr>
<%
    }
%>


<%if( i == 0  ){%>
                <tr class="table_01" height="35">
                    <td colspan="5">나이별 성향 분석결과가 없습니다.</td>
                </tr>
<%}%>
      </table>
  </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>