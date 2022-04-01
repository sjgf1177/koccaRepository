<%//ÅÛÇÃ¸´ Á¤º¸%>
<%@ include file="/learn/user/include/site_info.jsp" %>

<%
    String topmenu  = "1";

    if (tem_type.equals("A")) {
%>
        <%@ include file="/learn/user/include/topApplyA.jsp" %>
<%
    } else if (tem_type.equals("B")) {
%>
        <%@ include file="/learn/user/include/topApplyB.jsp" %>
<%
    } else if (tem_type.equals("C")) {
%>
        <%@ include file="/learn/user/include/topApplyC.jsp" %>
<%
    }
%>