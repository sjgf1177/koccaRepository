<%@ page contentType="text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "com.credu.library.*" %>

<% 
String v_today = FormatDate.getDate("yyyyMMddHHmm");
//System.out.println("v_today " + v_today);
%>
<html>
<head>
<title></title>
<script language="javascript">
setTimeout("Load()", 3000); 
function Load() {
    location.reload();
    parent.document.form1.p_servertime.value = "<%=v_today%>";
}
parent.document.form1.p_servertime.value = "<%=v_today%>";

</script>
</head>
<body>
</body>
</html>