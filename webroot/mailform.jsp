<%@ page contentType="text/html; charset=euc-kr" %>
<html>
<body>
<form name="frm" Method="post" action="mailtest.jsp">
	<input type=text name=from_name size=12><br>
	<input type=text name=from_mail size=12><br>
	<input type=text name=to_mail size=12><br>
	<input type=text name=title size=12><br>
	<input type=text name=content size=12><br>
	<input type=submit value="mailsend">
</form>
</body>
</html>