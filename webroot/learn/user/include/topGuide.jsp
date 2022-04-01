<%//ÅÛÇÃ¸´ Á¤º¸%>
<%@ include file="/learn/user/include/site_info.jsp" %>

<%
    String topmenu  = "4";

    if (tem_type.equals("A")) {
%>
        <%@ include file="/learn/user/include/topGuideA.jsp" %>
<%
    } else if (tem_type.equals("B")) {
%>
        <%@ include file="/learn/user/include/topGuideB.jsp" %>
<%
    } else if (tem_type.equals("C")) {
%>
        <%@ include file="/learn/user/include/topGuideC.jsp" %>
<%
    }
%>