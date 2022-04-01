<%@ page import="com.credu.library.RequestBox" %>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    out.print(box.getString("dbLoad_ID_Exists"));
%> 