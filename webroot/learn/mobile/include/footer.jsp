<%@ page language = "java" contentType = "text/html; charset=euc-kr" pageEncoding = "euc-kr" %>
<%@ page import = "com.credu.library.*" %>
<% 
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
String s_userid = box.getSession("userid");
%>
	<div id="footer_area" class="footer_area">
		<a class="f_menu" href="/">KOCCA홈</a>
		<a class="f_menu" href="http://edu.kocca.co.kr">PC화면</a>
<% 
	if("".equals(s_userid))
	{
%>	
		<a class="f_menu" href="/servlet/controller.mobile.member.LoginServlet?p_process=loginpage&p_menuid=010010000000">로그인</a>
<% 
	}
	else
	{
%>
		<a class="f_menu" href="/servlet/controller.mobile.member.LoginServlet?p_process=logoutPoc&p_menuid=010010000000">로그아웃</a>	
<%		
	}
%>		
	</div>

	<div id="sub_koccaFoot">
		<address>COPYRIGHT@2011 한국콘텐츠진흥원 All Right Reserverd</address>
	</div>
	<jsp:include page="/learn/mobile/include/getJspName.jsp" flush="true" />