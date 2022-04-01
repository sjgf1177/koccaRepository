<%
    if (tem_type.equals("GA")) {
%>
        <%@ include file="/learn/user/game/include/bottomA.jsp" %>
<%
    } else if (tem_type.equals("GB")) {
%>
        <%@ include file="/learn/user/game/include/bottomB.jsp" %>
<%
    }
%>