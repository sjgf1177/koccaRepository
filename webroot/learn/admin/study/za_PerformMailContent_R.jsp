<%
//**********************************************************
//  1. 제      목: PROJECT DETAIL LIST
//  2. 프로그램명: za_ProjectDetail_L.jsp
//  3. 개      요: 리포트 제출자 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 7. 29
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	
	String contents =  request.getParameter("contents");       
    String title	= request.getParameter("title");
 
%>

<%@page import="weblogic.jms.dispatcher.Request"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">


</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" scroll="auto">

<form name="form1" method="post" action="/servlet/controller.study.StudyStatusAdminServlet">
<table width="600" border="0" cellpadding="0" cellspacing="10" >
  <tr>
  	<td>
  		제목 : <%=title %><br/>
  		내용 : <%=contents %>
  	</td>
  </tr>
</table>
</form>
</body>
</html>
