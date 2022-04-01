<%@page contentType = "text/html; charset=euc-kr" %>
<%@page import="java.net.*" %>
<%@page import="java.util.*" %>
<%@page import="javax.servlet.http.*" %>
<%@page import="com.tmax.sso.filter.*" %>


<%
/********************************************************************
1. returnURL 은 로그아웃 하고 이동할 URL 로 설정된다.               *
																	*
2. 로그아웃시 single모드로 변경됨으로, TMAXSSOCLOS 를 no로 설정해서 *
   재 접속시에도 문제가 발생하지 않게 한다							*
********************************************************************/

//String returnURL = SSOConf.loginInputURL;   //설정파일의 --> com.tmax.sso.filter.logininputurl
//String returnURL = SSOConf.portalLoginURL;
//String returnURL = "http://www.gameacademy.or.kr";
String returnURL = "http://www.wecon.kr/sub/kocca/member/join_2.jsp";
session.invalidate();
if(SSOConf.relogin) {
	out.println("relogin ok");
	Cookie tmaxsso_cookie = new Cookie(Util.authenticatedCookieName, "no");
	Cookie tmaxssocookie2 = new Cookie("TMAXSSOCLOS", "no");
//	Cookie tmaxsso_cookie3 = new Cookie("TMAXSSOCOOK", "no");

	tmaxsso_cookie.setPath("/");
	tmaxssocookie2.setPath("/");
//	tmaxsso_cookie3.setPath("/");

	response.addCookie(tmaxsso_cookie);
	response.addCookie(tmaxssocookie2);
//	response.addCookie(tmaxsso_cookie3);

// 포털 로그아웃 내용을 추가해야 함

}else{
	out.println("relogin false");
}
out.println("returnURL" + returnURL);

%>