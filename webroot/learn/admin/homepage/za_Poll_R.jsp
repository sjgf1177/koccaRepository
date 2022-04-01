<%
//**********************************************************
//  1. 제      목: Poll
//  2. 프로그램명 : za_Poll_R.jsp
//  3. 개      요: Poll 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

    String v_searchtext = box.getString("p_searchtext");

    int    v_seq     = 0;            // 폴일련번호
    int    v_total   = 0;            // 응답수


    String v_title   = "";           // 타이틀
    String v_started = "";           // 시작일
    String v_ended   = "";           // 등록일
    String v_f_use   = "";           // 사용여부
    String v_f_usenm = "";
    String v_grcode  = "";
    String v_grcodenm= "";


    //PollData data1 = (PollData)request.getAttribute("selectPoll");

    DataBox dbox  = (DataBox)request.getAttribute("selectPoll");

    v_seq     = dbox.getInt("d_seq");
    v_total   = dbox.getInt("d_total");
    v_title   = dbox.getString("d_title");
    v_grcodenm= dbox.getString("d_grcodenm");
    v_started = dbox.getString("d_started");
    v_ended   = dbox.getString("d_ended");
    v_f_use   = dbox.getString("d_f_use");
    v_total   = dbox.getInt("d_total");
    v_grcode  = dbox.getString("d_grcode");

    v_ended   = FormatDate.getFormatDate(v_ended,"yyyy/MM/dd");
    v_started = FormatDate.getFormatDate(v_started,"yyyy/MM/dd");
    
    box.put("s_grcode",v_grcode);

    if (v_f_use.equals("Y")) v_f_usenm = "메인등록";
    else v_f_usenm = "등록안함";

    int v_maxcnt  = 0;
    int v_cnt     = 0;
    int v_percent = 0;
    int v_selnum  = 0;
    String v_seltxt = "";
    int v_graph   = 0;

    ArrayList list = (ArrayList)request.getAttribute("selectResultPoll");
%>
<html>
<head>
<title>eIBIs</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function modify_menu() {
        document.form1.action = "/servlet/controller.homepage.PollAdminServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

    function delete_menu() {
        document.form1.p_searchtext.value = "";
        document.form1.action = "/servlet/controller.homepage.PollAdminServlet";
        document.form1.p_process.value = "delete";
        document.form1.submit();
    }

    function list_menu() {
        document.form1.action = "/servlet/controller.homepage.PollAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

    function search() {
        document.form1.action = "/servlet/controller.homepage.PollAdminServlet";
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }
//-->
</SCRIPT>
</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"    value = "<%= v_process %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext%>">
    <input type = "hidden" name = "p_seq"        value = "<%=v_seq%>">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td background="/images/admin/homepage/title_bg.gif" class="page_title"><img src="/images/admin/homepage/homepage_title2.gif" border="0"></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->

        <br>
        <br>
        <!----------------- CyberPoll 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="20%" height="25" class="table_title"><b>질문번호</b></td>
            <td class="table_02_2"><b><%=v_seq%></b></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>교육그룹</b></td>
            <td class="table_02_2"><%=v_grcodenm%></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>질문</b></td>
            <td class="table_02_2"><%=v_title%></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>설문기간</b></td>
            <td class="table_02_2"><%=v_started%> ~ <%=v_ended%></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>메인등록</b></td>
            <td class="table_02_2"><%=v_f_usenm%></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>답변수</b></td>
            <td class="table_02_2"><b><%=v_total%></b></td>
          </tr>
        </table>
        <!----------------- CyberPoll 끝 ----------------->

        <br>
        <br>

        <!----------------- 지문 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="0">
          <tr>
            <td colspan="5" class="table_top_line"></td>
          </tr>
          <tr>
            <td colspan="5" height="25" class="table_title"><b>지문</b></td>
          </tr>


<%
            // 최대값 구하기
            for(int i = 0; i < list.size(); i++) {
                 DataBox dbox2 = (DataBox)list.get(i);
                 v_cnt              = dbox2.getInt("d_cnt");
                 if (v_maxcnt < v_cnt)  v_maxcnt = v_cnt;
            }

            for(int i = 0; i < list.size(); i++) {

                //PollSelData data3   = (PollSelData)list.get(i);
                //
                //v_selnum = data3.getSelnum();
                //v_seltxt = data3.getSeltxt();
                //v_cnt    = data3.getCnt();

                DataBox dbox1 = (DataBox)list.get(i);

                v_selnum = dbox1.getInt("d_selnum");
                v_seltxt = dbox1.getString("d_seltxt");
                v_cnt    = dbox1.getInt("d_cnt");
                v_percent = (int)((float)v_cnt /(float)v_total * 100);
                v_graph = (int)((float)v_cnt /(float)v_maxcnt * 100);
%>

          <tr>
            <td class="table_01" height="25" width="5%"><%=i+1%></td>
            <td class="table_02_1" width="30%"><%=v_seltxt%></td>
            <td class="table_02_1" width="10%"><%=v_cnt%>명</td>
            <td class="table_02_1" width="10%"><%=v_percent%>%</td>
            <td class="table_02_2" width="42%">
              <table width="<%=v_graph%>%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td background="/images/admin/homepage/poll_line.gif" height="14"></td>
                </tr>
              </table>
            </td>
          </tr>

<%
            }
%>
        </table>
        <!----------------- 지문 끝 ----------------->

        <br>
        <!----------------- 수정, 삭제, 리스트 버튼 시작 ----------------->
        <table width="16%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:modify_menu()"><img src="/images/admin/button/btn_modify.gif"  border="0"></a>&nbsp;&nbsp;</td>
            <td align="center"><a href="javascript:delete_menu()"><img src="/images/admin/button/btn_del.gif" border="0"></a>&nbsp;&nbsp;</td>
            <td align="center"><a href="javascript:list_menu()"><img src="/images/admin/button/btn_list.gif" border="0"></a>&nbsp;</td>
          </tr>
        </table>
        <!----------------- 수정, 삭제, 리스트 버튼 끝 ----------------->

        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
