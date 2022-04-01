<%@ page contentType="text/html; charset=euc-kr" %>
<%
String savePath = request.getParameter("v_savepath");


%>

<html>
<head>
<title>Untitled Document</title>

<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<frameset rows="*" frameborder="NO" border="0" framespacing="0">   
  <frame name="topFrame" scrolling="yes" noresize src="<%=savePath%>">  
</frameset>
<noframes> 
<body bgcolor="#FFFFFF" text="#000000">
</body>
</noframes> 
</html>
