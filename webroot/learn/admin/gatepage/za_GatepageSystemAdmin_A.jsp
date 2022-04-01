<%
//**********************************************************
//  1. 제      목: 게이트시스템 운영자에게 관리 답변화면
//  2. 프로그램명: zu_GatepageSystemAdmin_A.jsp
//  3. 개      요: 게이트시스템 운영자에게 관리 답변화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.gatepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");


int v_seq = box.getInt("p_seq");

int v_pageno = box.getInt("p_pageno");

String v_name = ""; 
String v_email = "";
String v_queid = "";
String v_jikmunm = "";
String v_jikwin = "";
String v_quecontent = "";
String v_quetitle = "";
String v_post = "";
String v_anstitle = "";
String v_anscontent = "";
String v_gubun = "";
String s_userid = box.getSession("userid");


DataBox dbox = (DataBox)request.getAttribute("selectContact");



v_name = dbox.getString("d_name"); 
v_email = dbox.getString("d_email"); 
v_queid = dbox.getString("d_queid"); 
v_jikwin = dbox.getString("d_jikwin"); 
v_quecontent = dbox.getString("d_quecontent"); 
v_quetitle = dbox.getString("d_quetitle"); 
v_post = dbox.getString("d_post"); 
v_anstitle = dbox.getString("d_anstitle"); 
v_anscontent = dbox.getString("d_anscontent"); 
v_seq = dbox.getInt("d_seq");
v_gubun = dbox.getString("d_gubun");

%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>GatePage운영자관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script  language="JavaScript">
<!--
function selectList() {
	document.form1.action = "/servlet/controller.gatepage.GatePageContactAdminServlet";
	document.form1.p_process.value = "selectList";   
	document.form1.p_pageno.value = "1";       
	document.form1.submit();
}  

function deleteAction() {
    if (confirm("정말로 삭제하시겠습니까?")) {
        document.form1.action = "/servlet/controller.gatepage.GatePageContactAdminServlet";
        document.form1.p_process.value = "delete";
        document.form1.submit();
    }
    else {
        return;
    }
}

function replyAction() {
	if(form1.p_anstitle.value == ""){
		alert("제목을 입력해 주세요!");
	}
	else if (form1.p_anscontent.value == ""){
		alert("내용을 입력해주세요!");
	
	}else{
		document.form1.action = "/servlet/controller.gatepage.GatePageContactAdminServlet";
		document.form1.p_process.value = "reply";      
		document.form1.submit();
	}
}
//-->
</script>
</head>

<body topmargin=0 leftmargin=0>
<form name = "form1" enctype = "multipart/form-data" method = "POST">
	        <input type = "hidden" name = "p_seq"     value = "<%= v_seq %>">
	        <input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">
	        <input type = "hidden" name = "p_process"  value = "">
	        <input type = "hidden" name = "p_userid" value = "<%= s_userid %>">
			<input type = "hidden" name = "p_gubun" value = "<%= v_gubun %>">



<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/gatepage/cp_title08.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
        </tr>
      </table>
	         <br>
      <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
        <tr> 
		
	


          <td class="table_title" width="16%">소 속</td>
          <td class="table_02_2"  width="84%"><%= v_post %></td>
        </tr>
        <tr> 
          <td class="table_title" width="16%">직 위</td>
          <td class="table_02_2"  width="84%"><%= v_jikwin %></td>
        </tr>
        <tr> 
          <td class="table_title" width="16%">I D</td>
          <td class="table_02_2"  width="84%"><%= v_queid %></td>
        </tr>
        <tr> 
          <td class="table_title" width="16%">성 명</td>
          <td class="table_02_2"  width="84%">	<%= v_name %></td>
        </tr>
        <tr> 
          <td class="table_title">제 목</td>
          <td class="table_02_2"><%= v_quetitle %></td>
        </tr>
        <tr> 
          <td class="table_title">내 용</td>
          <td class="table_02_2"><%= StringManager.replace(v_quecontent,"\r\n","<br>")%></td>
        </tr>
        <tr> 
          <td class="table_title">답변제목</td>
          <td class="table_02_2"><input name="p_anstitle" type="text" class="input" style="width:600" value = "<%= v_anstitle %>"></td>
        </tr>
        <tr> 
          <td class="table_title">답변내용</td>
          <td class="table_02_2" style=padding-top:5;padding-bottom:5>
		  
		  
		   <TEXTAREA NAME="p_anscontent" ROWS="20"  COLS="55"><%= v_anscontent %></textarea>
		  </td>


        </tr>
      </table>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td height=6></td>
          <td height=6></td>
          <td height=6></td>
          <td align="right" height=6></td>
          <td width="12" height=6></td>
          <td align="right" height=6></td>
        </tr>
        <tr> 
          <td width="823" align="right"></td>
          <td width="45" align="right"><a href="javascript:replyAction()"><img src="/images/admin/button/btn_reply.gif" border="0"></a></td>
          <td width="14">&nbsp;</td>
          <td width="32" align="right"><a href="javascript:deleteAction()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
          <td width="12">&nbsp;</td>
          <td width="44" align="right"><a href="javascript:selectList()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table></td>
  </tr>
</table>
</form>
</body>
</html>
