<%@ page contentType="text/html;charset=MS949" %>
<%@ page isErrorPage="true" %>
<%@ page import="com.credu.library.*" %>

<%
//**********************************************************
//  1. 제      목: 에러처리
//  2. 프로그램명: error.jsp
//  3. 개      요: 커뮤니티 회원승인 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이정한 2003. 4. 9
//  7. 수      정: 이정한 2003. 4. 9
//***********************************************************

String errMessage="";

if(ErrorManager.isErrorMessageView()) {
    errMessage = ErrorManager.getErrorStackTrace(exception, true);		
%>
<html>
<head>	
<meta http-equiv="content-type" content="text/html; charset=euc-kr">
<meta http-equiv="pragma" content="no-cache">
<title>compound jsp</title></head>
<body bgcolor=#c0c0c0>
<h2>exception occurred</h2>
<font size=2>
<%=errMessage %>
</font>
</body></html>
<%
}else {	
%>

<html>
<body onload="javascript:document.errform.submit()">
<form name="errform" action="/learn/library/include/printSystemErrorMessage.jsp" method=post>
</form>
</body>
</html>
<%
}
%>
