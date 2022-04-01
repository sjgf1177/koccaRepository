<%@ page language="java" contentType="text/html; charset=euc-kr"
    pageEncoding="euc-kr"%>
<%
    String v_answer    = request.getParameter("p_answer");
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src= "/script/cresys_lib.js"></script>
<script language = "VBScript"   src= "/script/cresys_lib.vbs"></script>
</head>
<body>
<table width="390" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">

      <!----------------- title 시작 ----------------->
        <!----------------- title 끝 ----------------->

        
        <!----------------- 공지사항 관리 시작 ----------------->
        <table style="width:370" class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td class="table_top_line"></td>
          </tr>
          <tr>
            <td height="25" class="table_02_2" align="left">
                <%=v_answer %>
            </td>
          </tr>
        </table>
        <!----------------- 공지사항 관리 끝 ----------------->
        <br>
        <!----------------- 저장, 취소 버튼 시작----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:self.close()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 저장, 취소 버튼 끝 ----------------->
      </td>
  </tr>
</table>

</body>
</html>