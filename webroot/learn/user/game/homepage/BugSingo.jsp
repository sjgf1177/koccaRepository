


<%
//**********************************************************
//  1. 제      목: 홈페이지 버그신고
//  2. 프로그램명 : BugSingo.jsp
//  3. 개      요: 홈페이지 버그신고
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.21
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

            <table cellpadding="0" cellspacing="0" width="190">
            <tr>
            <td height="10"></td>
            </tr>
              <tr>
               	<td align="center">
					<a href="/servlet/controller.homepage.HomePageBugBoardServlet">
					<img src="/images/user/game/homepage/type1/right_bug.gif"></a>
			    </td>
              </tr>
            </table>