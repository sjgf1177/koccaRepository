<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page import="java.util.List,com.credu.library.DataBox" %>
<%
	String rerurnFunction = request.getParameter("rerurnFunction");
	Object result = request.getAttribute("result");
	StringBuffer scriptSrc = new StringBuffer();
try {
	if (result == null)
		scriptSrc.append("{result: false}");
	else if (result.getClass().getName().equals("java.lang.String"))
		scriptSrc.append("{result: '"+result+"'}");
	else
		scriptSrc.append("{result: '"+result+"'}");
}
catch (Exception e) {
	System.out.println("!!!!!!!!!!!" + e);
}
	out.println("<script type='text/javascript'><!--\n");
	out.println(rerurnFunction + "("+scriptSrc.toString()+")");
	out.println("--></script>");
%>

