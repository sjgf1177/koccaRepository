<%
    if (tem_type.equals("KA")) {
%>
        <%@ include file="/learn/user/kocca/include/bottomA.jsp" %>
<%
    } else if (tem_type.equals("KB")) {
%>
        <%@ include file="/learn/user/kocca/include/bottomB.jsp" %>
<%
    }
%>