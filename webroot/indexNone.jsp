<%@ page import = "com.credu.library.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    //box.setSession("tem_type","KA");
   // box.setSession("tem_grcode","N000001");
   if(request.getServerName().equals("game.connect.or.kr"))
    {
	box.setSession("tem_type","GA");
	box.setSession("tem_grcode","N000002");
    }else if(request.getServerName().equals("cyber.gameacademy.or.kr"))
    {
	box.setSession("tem_type","GA");
	box.setSession("tem_grcode","N000002");
    }else if(request.getServerName().equals("wsu.gameacademy.or.kr"))
    {
	box.setSession("tem_type_sub","wsu");
	box.setSession("tem_type","GA");
	box.setSession("tem_grcode","N000012");
    }
    else
    {
    	box.setSession("tem_type","KA");
	box.setSession("tem_grcode","N000001");
    }

%>
<html>
<head>
<Script Language='JavaScript'>
<!--
    document.location.replace("/servlet/controller.homepage.NoneMainServlet");
//-->
</Script>
</head>
<body>
</body>
</html>
