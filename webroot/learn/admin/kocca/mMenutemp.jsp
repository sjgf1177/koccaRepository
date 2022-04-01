<%
/**
 * file name : mScreenFbody.jsp
 * date      : 2003/7/11
 * programmer: LeeSuMin
 * function  : manager screen Initial Body
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>

<frameset cols="160,*" frameborder="NO" border="0" framespacing="0">
  <frame src="mMenutempLeft.jsp" name="leftFrame" scrolling="YES" noresize>
  <frame src="" name="mainFrame">
</frameset>
<noframes><body>

</body></noframes>
</html>

