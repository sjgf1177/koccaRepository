<%//???ø? ????%>
<%@ include file="/learn/user/kocca/include/site_info.jsp" %>

<%
    String topmenu  = "6";

    if (tem_type.equals("KA")) {
%>
        <%@ include file="/learn/user/kocca/include/topSiteMapA.jsp" %>
<%
    } else if (tem_type.equals("KB")) {
%>
        <%@ include file="/learn/user/kocca/include/topSiteMapB.jsp" %>
<%
    }
%>