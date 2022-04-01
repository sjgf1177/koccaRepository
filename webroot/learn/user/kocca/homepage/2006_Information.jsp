
	   
<%
//**********************************************************
//  1. 제      목: 홈페이지 2006_입학모집안내
//  2. 프로그램명 : 2006_Information.jsp
//  3. 개      요: 홈페이지 2006_입학모집안내
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.22
//  7. 수      정:
//***********************************************************
%>
	   
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

%>



	<table width="166" border="0" cellspacing="0" cellpadding="0">
	  <tr> 
		<td><img src="/images/user/kocca/homepage/type1/2006_admi_info.gif" width="166" height="56"></td>
	  </tr>
	  <tr> 
		<td height="8"></td>
	  </tr>
	</table>