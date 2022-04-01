<%@page import="java.io.*"%>
<%
	String filename = request.getParameter("filepath");
	String strLine = "";
	String line = "";
	try{
		FileReader fr = null;
		fr = new FileReader(filename);
		BufferedReader inFile = new BufferedReader(fr);
		while((strLine = inFile.readLine()) != null){
			line += strLine+"\r\n";
		}
		line = line.replaceAll("<!--.*-->","");
		line = line.replaceAll("(?:(#.*\\n{0,1})|//.*\\n{0,1})|(?:/\\*(?:.|\\s)*?\\*/\\n{0,1}|<(?i)script[^>]*>[\\w\\W\\r]*</(?i)script>|<(?i)style[^>]*>[\\w\\W\\r]*</(?i)style>)","");
		if(line.indexOf("initCheckWebfilter.jsp")>-1){
			out.println("insert");
		}else{
			out.println("delete");
		}
	}catch(Exception e) {
			out.println("none");
	} finally {
	}
%>