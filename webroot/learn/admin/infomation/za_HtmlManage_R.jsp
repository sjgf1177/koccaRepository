<%
//**********************************************************
//  1. 제      목: Notice Templet
//  2. 프로그램명 : za_NoticeTemplet_R.jsp
//  3. 개      요: 공지사항 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 13
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
    String  v_process  = box.getString("p_process");


    int     v_seq          = box.getInt("p_seq");

    int    v_cnt         = 0;            // 조회수
    String v_title       = "";           // 제목
    String v_code    = "";           // 템플릿 파일
    String v_content      = "";           // 등록자
    String v_indate      = "";           // 수정일

    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
        v_title     = dbox.getString("d_title");
        v_code      = dbox.getString("d_code");
        v_content    = dbox.getString("d_content");

     }
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function modify_notice() {
        document.form1.action = "/servlet/controller.infomation.HtmlManageAdminServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

    function delete_notice() {
        document.form1.action = "/servlet/controller.infomation.HtmlManageAdminServlet";
        document.form1.p_process.value = "delete";
        document.form1.submit();
    }

    function list_notice() {
        document.form1.action = "/servlet/controller.infomation.HtmlManageAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post">
    <input type = "hidden" name = "p_process"     value = "<%= v_process %>">
    <input type = "hidden" name = "p_seq"         value = "<%=v_seq %>">

    

  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/portal/s.1_18.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>

      <br>


      <!----------------- CENTER 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5">
        <tr>
          <td colspan="9" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="16%" class="table_title"><b>제목</b></td>
          <td height="25" colspan="8" class="table_02_2"><%=v_title%></td>
        </tr>
         <tr>
          <td width="16%" class="table_title"><b>코드</b></td>
          <td height="25" colspan="8" class="table_02_2"><%=v_code%></td>
        </tr>
        <tr>
          <td align="center" class="table_title">내용</td>
          <td colspan="8" class="table_02_2" >
          <%=v_content %>
          </td>
        </tr>
      </table>
      <br>
      <!----------------- 버튼 시작 ----------------->

      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" height="20">
            <a href="javascript:modify_notice()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>
            <a href="javascript:delete_notice()"><img src="/images/admin/button/btn_del.gif" border="0"></a>
            <a href="javascript:list_notice()"><img src="/images/admin/button/btn_list.gif" border="0"></a>
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- CENTER 끝 ----------------->
      </td>
  </tr>
</table>


<%@ include file = "/learn/library/getJspName.jsp" %>

</body>
</html>