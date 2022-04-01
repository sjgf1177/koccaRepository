<%@ page language = "java" contentType = "text/html; charset=euc-kr" pageEncoding = "euc-kr" %>
<div id="sub_header">	
	<h1 id="logo"><a href="/servlet/controller.mobile.main.MainServlet"><img src="/images/mobile/sub_logo.gif" alt="·Î°í" /></a></h1> 
	<p id="title"><%=v_map.toString()%></p>
<% 
	if("".equals(s_userid))
	{
%>	
		<button id="backSubmit" onclick="location.replace('/servlet/controller.mobile.member.LoginServlet?p_process=loginpage&p_menuid=010010000000')"><span>login</span></button>	
<% 
	}
	else
	{
%>
		<button id="backSubmit" onclick="location.replace('/servlet/controller.mobile.member.LoginServlet?p_process=logoutPoc&p_menuid=010010000000')"><span>logout</span></button>	
<%		
	}
%>
</div>