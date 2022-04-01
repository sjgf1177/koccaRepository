<%@ page language="java" contentType="text/html; charset=euc-kr"
    pageEncoding="euc-kr"%>
<%
    String v_process    = request.getParameter("p_process");
    String v_seq        = request.getParameter("p_seq");
	String v_pageno     = request.getParameter("p_pageno");
	String v_pagesize   = request.getParameter("p_pagesize");
	String v_search     = request.getParameter("p_search");
	String v_searchtext = request.getParameter("p_searchtext");
%>
<html>
<SCRIPT LANGUAGE="JavaScript">
    function init(p){
        if(p == "selectWinner") {
        	document.form1.p_process.value = "selectWinPage";	
        } else if (p == "updatePage") {
        	document.form1.p_process.value = "updateWinPage";
        } else if (p == "insertPage") {
            document.form1.p_process.value = "insertWinPage";
        }

        document.form1.target = "_self";
        document.form1.action = '/servlet/controller.homepage.EventAdminServlet';
        document.form1.submit();
    }
</SCRIPT>
<body onload="init('<%=v_process %>');">
<form name="form1" method="post" enctype="multipart/form-data">
    <input type = "hidden" name="p_process"    value = "<%= v_process %>">
    <input type = "hidden" name="p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name="p_search"     value = "<%=v_search %>">
    <input type = "hidden" name="p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name="p_seq"        value = "<%=v_seq %>">
</form>
</body>
</html>