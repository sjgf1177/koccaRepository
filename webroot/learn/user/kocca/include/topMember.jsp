<%//???ø? ????%>
<%@ include file="/learn/user/kocca/include/site_info.jsp" %>

<%
    String topmenu  = "7";

    if (tem_type.equals("KA")) {
%>
        <%@ include file="/learn/user/kocca/include/topMemberA.jsp" %>
<%
    } else if (tem_type.equals("KB")) {
%>
        <%@ include file="/learn/user/kocca/include/topMemberB.jsp" %>
<%
    }
%>