<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%
String  content = (String)request.getAttribute("content");

    
%>
<html>
<body>
<%=content %>
</body>
</html>

