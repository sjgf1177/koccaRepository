<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
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
    }else if(request.getServerName().equals("cyber.gameacademy.or.kr"))	//사이버게임아카데미
    {
	box.setSession("tem_type","GA");
	box.setSession("tem_grcode","N000002");
    }else if(request.getServerName().equals("wsu.gameacademy.or.kr"))	//우송대학교
    {
	box.setSession("tem_type_sub","wsu");
	box.setSession("tem_type","GA");
	box.setSession("tem_grcode","N000012");
    }else if(request.getServerName().equals("jeju.gameacademy.or.kr"))	//제주대학교
    {
	box.setSession("tem_type_sub","jeju");
	box.setSession("tem_type","GA");
	box.setSession("tem_grcode","N000013");
    }else if(request.getServerName().equals("kyowon.gameacademy.or.kr"))	//게임원격교원연수원
    {
	box.setSession("tem_type_sub","kyowon");
	box.setSession("tem_type","GA");
	box.setSession("tem_grcode","N000010");
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
	alert("로그인 후 이용해주세요");
    document.location.replace("/servlet/controller.homepage.MainServlet");
//-->
</Script>
</head>
<body>
</body>
</html>
