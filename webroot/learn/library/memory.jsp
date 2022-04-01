<%@ page session="false" contentType="text/html" %>
<html><body>
<b><%= java.net.InetAddress.getLocalHost().getHostAddress() %></b>
<%= new java.util.Date() %><br><br>

<% 
   Runtime rt = Runtime.getRuntime();
   long free = rt.freeMemory();
   long total = rt.totalMemory();
   out.println("Free  = <b>"+(free/1024/1024) +"</b> MB ("+free +" bytes)<br>");
   out.println("Total = <b>"+(total/1024/1024)+"</b> MB ("+total+" bytes)<br>");
%>
</body></html>
