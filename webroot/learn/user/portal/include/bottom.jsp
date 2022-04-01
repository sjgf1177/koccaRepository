<%
    if (tem_type.equals("GA")) {
%>
        <%@ include file="/learn/user/portal/include/bottomA.jsp" %>
<%
    } else if (tem_type.equals("GB")) {
%>
        <%@ include file="/learn/user/portal/include/bottomB.jsp" %>
<%
    }
%>