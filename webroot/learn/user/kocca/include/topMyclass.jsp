<%//ÅÛÇÃ¸´ Á¤º¸%>
<%@ include file="/learn/user/kocca/include/site_info.jsp" %>

<%
    String topmenu  = "3";

    if (tem_type.equals("KA")) {
%>
        <%@ include file="/learn/user/kocca/include/topMyclassA.jsp" %>
<%
    } else if (tem_type.equals("KB")) {
%>
        <%@ include file="/learn/user/kocca/include/topMyclassB.jsp" %>
<%
    }
%>