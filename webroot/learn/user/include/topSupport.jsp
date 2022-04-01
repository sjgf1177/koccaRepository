<%//ÅÛÇÃ¸´ Á¤º¸%>
<%@ include file="/learn/user/include/site_info.jsp" %>

<%
    String topmenu  = "3";

    if (tem_type.equals("A")) {
%>
        <%@ include file="/learn/user/include/topSupportA.jsp" %>
<%
    } else if (tem_type.equals("B")) {
%>
        <%@ include file="/learn/user/include/topSupportB.jsp" %>
<%
    } else if (tem_type.equals("C")) {
%>
        <%@ include file="/learn/user/include/topSupportC.jsp" %>
<%
    }
%>