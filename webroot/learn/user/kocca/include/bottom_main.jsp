<%
    if (tem_type.equals("KA")) {
%>
        <%@ include file="/learn/user/kocca/include/bottomA_main.jsp" %>
<%
    } else if (tem_type.equals("KB")) {
%>
        <%@ include file="/learn/user/kocca/include/bottomB_main.jsp" %>
<%
    }
%>