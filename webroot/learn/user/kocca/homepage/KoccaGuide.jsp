
	   
<%
//**********************************************************
//  1. 제      목: 홈페이지 kocca가이드
//  2. 프로그램명 : KoccaGuide.jsp
//  3. 개      요: 홈페이지 kocca가이드
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
		<td><a href="/servlet/controller.homepage.KHomePageHelpServlet?p_process=Help"><img src="/images/user/kocca/homepage/type1/kocca_guide.gif" width="166" height="43"></a></td>
	  </tr>
	  <tr> 
		<td height="8"></td>
	  </tr>
	</table>
<!-- kocca가이드 -->	