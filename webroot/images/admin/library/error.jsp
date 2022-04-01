<%@ page contentType="text/html;charset=MS949" %>
<%@ page isErrorPage="true" %>
<%@ page import="com.credu.library.*" %>

<%
//-----------------------------------------------------------------
// 프로그램명 : 에러 처리
// 프로그램ID : error.jsp
// 작성자     : 지승훈
// 작성일     : 2002. 4. 9
//-----------------------------------------------------------------

String errMessage="";

		errMessage = ErrorManager.getErrorStackTrace(exception);
		
//		if (conf.getProperty("service.status").equals("true")) { // 현재 서비스중이면 에러의 내용을 보이지 않고 메시지처리한다.
//				Log.log(conf.getProperty("dir.log")+"error.log", errMessage.toString()); // log 파일에 기록하고
//				Log.log(conf.getProperty("dir.log")+"error.log", backTrace.toString());
%>
<html>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Pragma" content="no-cache">
<TITLE>Compound JSPs</TITLE></HEAD>

<BODY BGCOLOR=#C0C0C0>
<H2>Exception Occurred</H2>
<FONT SIZE=2>
<%=errMessage %>
</FONT>
</BODY></HTML>

