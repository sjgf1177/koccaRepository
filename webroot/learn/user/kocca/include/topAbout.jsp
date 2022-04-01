<%//ÅÛÇÃ¸´ Á¤º¸%>
<%@ include file="/learn/user/kocca/include/site_info.jsp" %>

<%
    String topmenu  = "4";

    if (tem_type.equals("KA")) {
%>
        <%@ include file="/learn/user/kocca/include/topAboutA.jsp" %>
<%
    } else if (tem_type.equals("KB")) {
%>
        <%@ include file="/learn/user/kocca/include/topAboutB.jsp" %>
<%
    }
%>