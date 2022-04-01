<%@page contentType = "text/html; charset=euc-kr" %>

<%@page import="java.util.*" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="java.net.*" %>


<%

String urlVal = (String)request.getParameter("urlVal");
String encodeUrl_utf8 =  null;

if(urlVal != null && urlVal.length() != 0) {
	encodeUrl_utf8 = URLEncoder.encode(urlVal, "utf-8");
	out.println("Encode URL = '"+encodeUrl_utf8 + "'");
}

%>


<html>
	<body>
		<center>
		<hr>
		<br>
		<form action=urlEncode.jsp method=post>
		<table border=0>
		<tr><td width=100>URL : </td><td><input type=text name=urlVal size="100" value='' ></td></tr>
		<tr><td colspan=2 align=center><input type=reset value="다시입력">&nbsp;&nbsp;&nbsp;<input type=submit value="Encode"></td></tr>
		</table>
		</form>
		</center>
	</body>
</html>