<%
/**
 * file name : za_SulmunSubjUser_R.jsp
 * date      : 2003/09/12
 * programmer:
 * function  : ����������
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

   
    String  v_message   = box.getString("p_message");
    
%>
<html>
<head>
<title>���� ����</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_research.css" type="text/css">
</head>
<body>
<!----------------- Ÿ��Ʋ ���� ----------------->
<table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
	<tr> 
 		<td background="/images/user/research/b_title_dot.gif"><img src="/images/user/research/question_title2.gif" width="240" height="37" border="0"></td>
	</tr>
	<tr><td height="7"></td></tr>
</table>
<!----------------- Ÿ��Ʋ �� ---------------->
<br>
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
	<tr> 
		<td width="13"><img src="/images/user/research/icon2.gif" width="13" height="11" border="0"></td>
		<td><b><%=v_message%></b></td>
	</tr>
	<tr> 
		<td height="5"></td>
	</tr>
</table>
<br>
<!----------------- ���� ��ư ���� ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr>
		<td align="right">
		<a href="javascript:window.close()"><img src="/images/admin/exam/close1_butt.gif" width="37" height="18" border="0"></a>
		</td>
	</tr>

	  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     


</table>
<!----------------- ���� ��ư �� ----------------->
<br>
</body>
</html>