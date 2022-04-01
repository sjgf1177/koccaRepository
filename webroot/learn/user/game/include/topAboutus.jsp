<%//ÅÛÇÃ¸´ Á¤º¸%>
<%@ include file="/learn/user/game/include/site_info.jsp" %>

<%
    String topmenu  = "2";

    if (tem_type.equals("GA")) {
%>
        <%@ include file="/learn/user/game/include/topAboutusA.jsp" %>
<%
    } else if (tem_type.equals("GB")) {
%>
        <%@ include file="/learn/user/game/include/topAboutusB.jsp" %>
<%
    }
%> 