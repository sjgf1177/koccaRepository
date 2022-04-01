<%//템플릿 정보%>
<%@ include file="/learn/user/kocca/include/site_info.jsp" %>

<%
    String topmenu  = "2";

    if (tem_type.equals("KA")) { // 로그인이 없으므로 임시
    //if (tem_type.equals("GA")) {
%>
        <%@ include file="/learn/user/kocca/include/topApplyA.jsp" %>
<%
    } else if (tem_type.equals("KB")) {
%>
        <%@ include file="/learn/user/kocca/include/topApplyB.jsp" %>
<%
    }
%>