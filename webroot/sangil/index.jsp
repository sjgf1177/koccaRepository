<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<title>Insert title here</title>
</head>

<% 
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) box = RequestManager.getBox(request);

	box.setSession("tem_grcode", "N000042");
%>

<script type="text/javascript">

function indexList() {
    document.form1.p_process.value = "";
    document.form1.p_aspgubun.value = "sangil";
    document.form1.action = "/servlet/controller.homepage.MainServlet";
    document.form1.submit();
}

</script>

<body onload="indexList()">
<form name="form1"  action="" method="post">
	<input type="hidden"	name="p_process"	value="" />
	<input type="hidden"	name="p_aspgubun"	value="" />

		<table>
			<tr>
			    <td></td>
			</tr>
		</table>

</form>
</body>
</html>