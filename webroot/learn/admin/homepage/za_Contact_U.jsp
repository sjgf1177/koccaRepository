<%
//**********************************************************
//  1. 제      목: 운영자에게 리스트
//  2. 프로그램명 : za_Contact_U.jsp
//  3. 개      요: 운영자에게 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 8
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process     = box.getString("p_process");
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");
    int  v_pageno         = box.getInt("p_pageno");

    int v_seq    = box.getInt("p_seq");

    String  v_addate     = "";
    String  v_queid      = "";
    String  v_quetitle   = "";
    String  v_quecontent = "";
    String  v_ansid      = "";
    String  v_anstitle   = "";
    String  v_anscontent = "";
    String  v_ismail     = "";

    String  v_cono       = "";
    String  v_name       = "";
    String  v_email      = "";
    String  v_jikwinm    = "";
    String  v_compnm     = "";

    ContactData data = (ContactData)request.getAttribute("selectContact");

    v_seq        = data.getSeq();
    v_addate     = data.getAddate();
    v_addate     = FormatDate.getFormatDate(v_addate,"yyyy/MM/dd HH")+"시";
    v_queid      = data.getQueid();
    v_quetitle   = data.getQuetitle();
    v_quecontent = data.getQuecontent();
    v_ansid      = data.getAnsid();
    v_anstitle   = data.getAnstitle();
    v_anscontent = data.getAnscontent();
    v_ismail     = data.getIsmail();

    v_cono       = data.getCono();
    v_name       = data.getName();
    v_email      = data.getEmail();
    v_jikwinm    = data.getJikwinm();
    v_compnm     = data.getCompnm();

    v_quecontent  = StringManager.replace(v_quecontent, "\n","<br>");
    v_anstitle    = StringManager.replace(v_anstitle,"`","'");

%>
<html>
<head>
<title>eIBIs</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function insert_check() {
        if (blankCheck(document.form1.p_anstitle.value)) {
            alert("제목을 입력하세요!");
            document.form1.p_anstitle.focus();
            return;
        }
        if (realsize(document.form1.p_anstitle.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_anstitle.focus();
            return;
        }
        if (blankCheck(document.form1.p_anscontent.value)) {
            alert("내용을 입력하세요!");
            document.form1.p_anscontent.focus();
            return;
        }
        document.form1.action = "/servlet/controller.homepage.ContactAdminServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }

    function list() {
        document.form1.action = "/servlet/controller.homepage.ContactAdminServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

    function delete_contact() {
        document.form1.p_searchtext.value = "";
        document.form1.p_search.value     = "";
        document.form1.action = "/servlet/controller.homepage.ContactAdminServlet";
        document.form1.p_process.value = "delete";
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>




<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"    value = "<%= v_process %>">
    <input type = "hidden" name = "p_searchtext" value='<%=v_searchtext%>'>
    <input type = "hidden" name = "p_search"     value='<%=v_search%>'>
    <input type = "hidden" name = "p_pageno"     value='<%=v_pageno%>'>
    <input type = "hidden" name = "p_seq"        value='<%=v_seq%>'>

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="../../../images/admin/homepage/h_title03.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->


        <br>
        <br>
        <!----------------- 운영자에게 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="18%" height="25" class="table_title"><b>소속</b></td>
            <td class="table_02_2" width="79%"><%=v_compnm%></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>직위</b></td>
            <td class="table_02_2"><%=v_jikwinm%></td>
          </tr>
          <tr>
            <td height="25" class="table_title"><b>ID</b></td>
            <td class="table_02_2"><%=v_queid%></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>사번</b></td>
            <td class="table_02_2"><%=v_cono%></td>
          </tr>
          <tr>
            <td height="25" class="table_title"><b>성명</b></td>
            <td class="table_02_2"><%=v_name%></td>
          </tr>
          <tr>
            <td height="25" class="table_title"><b>작성일</b></td>
            <td class="table_02_2"><%=v_addate%></td>
          </tr>
          <tr>
            <td height="25" class="table_title"><b>제목</b></td>
            <td class="table_02_2"><%=v_quetitle%></td>
          </tr>
          <tr>
            <td height="25" class="table_title"><b>내용</b></td>
            <td class="table_02_2"><%=v_quecontent%></td>
          </tr>
          <tr>
            <td height="25" class="table_title"><b>답변 제목</b></td>
            <td class="table_02_2">
              <input name="p_anstitle" type="text" class="input" size="117" value=`<%=v_anstitle%>`>
            </td>
          </tr>
          <tr>
            <td height="25" class="table_title"><b>답변 내용</b></td>
            <td class="table_02_2">
              <textarea name="p_anscontent" cols="100" rows="15"><%=v_anscontent%></textarea>
            </td>
          </tr>
        </table>
        <!----------------- 운영자에게 끝 ----------------->

        <br>
        <!----------------- 답변, 삭제, 리스트 시작 ----------------->
        <table width="16%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:insert_check()"><img src="/images/admin/homepage/reply1_butt.gif" width="37" height="18" border="0"></a></td>
            <td align="center"><a href="javascript:delete_contact()"><img src="/images/admin/homepage/del1_butt.gif" width="37" height="18" border="0"></a></td>
            <td align="center"><a href="javascript:list()"><img src="/images/admin/homepage/list1_butt.gif" width="48" height="18" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 답변, 삭제, 리스트 끝 ----------------->

        <br>
      </td>
  </tr>
</table>


<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
