<%@ page contentType = "text/html;charset=euc-kr" %>
<%
//**********************************************************
//  1. 제      목: 페이지명을 출력하는 include 화면
//  2. 프로그램명: getJspName.jsp
//  3. 개      요: 페이지명을 출력하는 include 화면
//  4. 환      경: JDK 1.6
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정: 
//**********************************************************
%>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet" scope="page" />
<% 
	//if(conf.getProperty("jsp.name.view").equals("true")) {      
%>
     <%//=request.getServletPath()%>
<%  //}  %>