<%//ÅÛÇÃ¸´ Á¤º¸%>
<%@ include file="/learn/user/include/site_info.jsp" %>

<%
    String topmenu  = "2";

    if (tem_type.equals("A")) {
%>
        <%@ include file="/learn/user/include/topMyClassA.jsp" %>
<%
    } else if (tem_type.equals("B")) {
%>
        <%@ include file="/learn/user/include/topMyClassB.jsp" %>
<%
    } else if (tem_type.equals("C")) {
%>
        <%@ include file="/learn/user/include/topMyClassC.jsp" %>
<%
    }
%>