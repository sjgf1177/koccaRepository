<%//ÅÛÇÃ¸´ Á¤º¸%>
<%@ include file="/learn/user/include/site_info.jsp" %>

<%
    String topmenu  = "0";

    if (tem_type.equals("A")) {
%>
        <%@ include file="/learn/user/include/topHomeA.jsp" %>
<%
    } else if (tem_type.equals("B")) {
%>
        <%@ include file="/learn/user/include/topHomeB.jsp" %>
<%
    } else if (tem_type.equals("C")) {
%>
        <%@ include file="/learn/user/include/topHomeC.jsp" %>
<%
    }
%>