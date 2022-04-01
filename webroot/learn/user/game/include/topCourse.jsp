<%//ÅÛÇÃ¸´ Á¤º¸%>
<%@ include file="/learn/user/game/include/site_info.jsp" %>

<%
    String topmenu  = "1";

    if (tem_type.equals("GA")) {
%>
        <%@ include file="/learn/user/game/include/topCourseA.jsp" %>
<%
    } else if (tem_type.equals("GB")) {
%>
        <%@ include file="/learn/user/game/include/topCourseB.jsp" %>
<%
    }
%>