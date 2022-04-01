<%
/**
 * file name : zu_SulmunResultList.jsp
 * date      : 2003/09/12
 * programmer:
 * function  : 설문결과등록
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

   
    String  v_f_gubun   = box.getString("p_f_gubun");
    String  v_message   = box.getString("p_message");
    
%>
<html>
<head>
<title>::: 쌍용자동차 사이버 연수원 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/user_style_research.css" type="text/css">
</head>
<body>
<!----------------- 타이틀 시작 ----------------->
<table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
	<tr> 
 		<td background="/images/user/research/b_title_dot.gif"><img src="/images/user/research/question_title2.gif" alt="설문조사" width="240" height="37" border="0"></td>
	</tr>
	<tr><td height="7"></td></tr>
</table>
<!----------------- 타이틀 끝 ---------------->
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
<!----------------- 제출 버튼 시작 ---------------->
<table cellspacing="0" cellpadding="0" class="open_board_table_out1" align="center">
  <tr>
		<td align="right">
<% if (v_f_gubun.equals(SulmunSubjResultBean.WHOLE_SULMUN)) { %>
		<a href="javascript:javascript:window.close()"><img src="/images/admin/exam/close1_butt.gif" alt="닫기" width="37" height="18" border="0"></a>
<% } else { %>
		<a href="javascript:javascript:top.etop.goExNext()"><img src="/images/admin/exam/close1_butt.gif" alt="닫기" width="37" height="18" border="0"></a>
<% }  %>
		</td>
	</tr>
</table>
<!----------------- 제출 버튼 끝 ----------------->
<br>
</body>
</html>
