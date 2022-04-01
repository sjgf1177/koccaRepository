<%
    if (tem_type.equals("A")) {
%>
        <%@ include file="/learn/user/include/bottomA.jsp" %>
<%
    } else if (tem_type.equals("B")) {
%>
        <%@ include file="/learn/user/include/bottomB.jsp" %>
<%
    } else if (tem_type.equals("C")) {
%>
        <%@ include file="/learn/user/include/bottomC.jsp" %>
<%
    }
%>