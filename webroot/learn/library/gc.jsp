<%@ page session="false" contentType="text/html" %>
<html><body>
<h1>Weblogic Memory Monitor(GC Version)</h1>
<%= new java.util.Date() %><br>

<% 
   System.out.println(java.net.InetAddress.getLocalHost().getHostAddress()+"<br>");
   Runtime rt = Runtime.getRuntime();
   long free = rt.freeMemory();
   long total = rt.totalMemory();
   out.println("Free  = " + (free/1024/1024) + " MB (" + free + " bytes)<br>");
   out.println("Total = " + (total/1024/1024) + " MB (" + total + " bytes)<br>");
   out.println("----------------------------------------------------<br>");
   System.gc();
   rt = Runtime.getRuntime();
   free = rt.freeMemory();
   total = rt.totalMemory();
   out.println("Free  = " + (free/1024/1024) + " MB (" + free + " bytes)<br>");
   out.println("Total = " + (total/1024/1024) + " MB (" + total + " bytes)<br>");
%>
</body></html>
