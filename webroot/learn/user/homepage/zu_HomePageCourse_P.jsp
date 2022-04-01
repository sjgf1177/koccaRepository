<%
//**********************************************************
//  1. 제      목: 홈페이지 과정체계도 팝업
//  2. 프로그램명: zu_HomePageCourse_P.jsp
//  3. 개      요: 홈페이지 과정체계도 팝업
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 7. 25
//  7. 수      정: 이연정 2005. 7. 25
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    String v_dirKey      = "bulletin";
    String v_url         = conf.getProperty("url.upload")+v_dirKey;
	String str           = request.getParameter("p_savelfile");
    String savelfile     = str.substring(1,str.length()-1); 
	String v_savelfile   = v_url + "/" + savelfile;
%>
<html>
<head>
<title>과정체계도</title>
</head>
<body>
<a href="javascript:close();"><img src="<%=v_savelfile%>"></a>
</body>
</html>