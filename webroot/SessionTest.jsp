<%@ page  contentType="text/html;charset=euc-kr"%>

<%@ page import="java.util.*" %>
<%@ page import="java.net.InetAddress" %>
<html>
<head>
<title>세션 예제</title>
</head>
<body>

<%
 Integer count = (Integer)session.getAttribute("count");
 if(count == null){
  count = new Integer(1);
 } else{
  count = new Integer(count.intValue() + 1);
 }
 
 //session.setMaxInactiveInterval(120);//120초로 설정

 session.setAttribute("count", count);

 out.println("방문 횟수(session): " + count + "<br>");
 out.println("<p>");
 out.println("<h3>세션 정보 : </h3>");
 
 out.println("세션 ID: " + session.getId() + "<br>");
 out.println("세션 유효시간(초): " + session.getMaxInactiveInterval() + "<br>");
 out.println("세션 생성: " + session.isNew() + "<br>");
 out.println("생성 시간: " + session.getCreationTime());
 out.println("<i>(" + new Date(session.getCreationTime()) + ")</i><br>");
 out.println("마지막 참조 시간: " + session.getLastAccessedTime());
 out.println("<i>(" + new Date(session.getLastAccessedTime()) + "></i><br>");
 out.println("세션 요청 여부(쿠키): " + request.isRequestedSessionIdFromCookie() + "<br>");
 out.println("세션 유효 여부: " + request.isRequestedSessionIdValid() + "<br>");
 InetAddress address = null;
 address = InetAddress.getLocalHost();
 
 out.println("Host 이름 : " + address.getHostName() + "<br>");
 out.println("Host IP : " + address.getHostAddress() + "<br>");
 out.println("<br>");
 out.println("SERVER_NAME : " + System.getProperty("weblogic.Name") + "<br>");
 
%>

<%
 out.println("<h3>Cookie 값들 "+"</h3>");
String cookieName = "username";
Cookie cookies [] = request.getCookies ();
Cookie myCookie = null;
if (cookies != null)
{
 for (int i = 0; i < cookies.length; i++)
  {
    out.println (cookies [i].getName()+"=");
    out.println (cookies [i].getValue()+"<br>");
  }
}
%>

<%
out.println("<h3>Keys</h3>");

Enumeration e = session.getAttributeNames();
for (; e.hasMoreElements() ;) {
  String key = (String)e.nextElement();    
  out.println(key+":" + session.getValue(key).toString());
  out.println("<br>");
}

%>


</body>
</html>

