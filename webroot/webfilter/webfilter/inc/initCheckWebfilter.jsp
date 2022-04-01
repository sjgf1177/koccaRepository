<%@page import="java.net.*,java.io.*"%>
<%
	Socket s = null;
	String wfcontextRoot = "/webfilter";
	String wfServerAddress="210.96.133.11"; 
	try{
		s = new Socket();
		s.connect(new InetSocketAddress(wfServerAddress,80),3000); 
		out.print("<script type='text/javascript' src='"+wfcontextRoot+"/webfilter/js/webfilter.js' defer='defer'></script>");
		out.print("<iframe id='webfilterTargetFrame' name='webfilterTargetFrame' width='0' height='0' frameborder='0' scrolling='no' noresize></iframe>");
	}catch(Exception e){
		out.print("<iframe id='webfilterSmsFrame' name='webfilterSmsFrame' width='0' src='"+wfcontextRoot+"/webfilter/html/webfilterBypass.html' height='0' frameborder='0' scrolling='no' noresize></iframe>");
	}finally{
		if( s != null ){
			try{
				s.close();
			}catch(Exception ignore){}
		}
	}
%>