<%@ page language="java" contentType ="text/html;charset=euc-kr" pageEncoding="euc-kr"  %>
<%@ page
import = "java.util.*"
import = "com.softforum.xdbe.xCrypto"
%>

<html>
<head>
	<title>test xecuredb page</title>
</head>
<body>
<%
    	String sOutput		= null;
    	String sDecoded		= null;
    	String sOutput1		= null;
    	String sDecoded1	= null;
    	String sOutput_H	= null;
		String sString		= "1234567890123";
    	String Err			= "";


	
	try
	{
		long nStartTime    = System.currentTimeMillis();
	
		xCrypto.RegisterEx("normal", 2, "C:\\xecuredb\\conf\\xdsp_pool.properties", "pool1", "kocca_db", "kocca_owner", "kocca_table", "normal");
		xCrypto.RegisterEx("pattern7", 2, "C:\\xecuredb\\conf\\xdsp_pool.properties", "pool1", "kocca_db", "kocca_owner", "kocca_table", "pattern7");
		
		sOutput    = xCrypto.Encrypt("normal", sString);
		sDecoded   = xCrypto.Decrypt("normal", sOutput);
		Err	= new String(xCrypto.GetLastError());

		sOutput1    = xCrypto.Encrypt("pattern7", sString);
		sDecoded1   = xCrypto.Decrypt("pattern7", sOutput1);
		
		/*
		sOutput    = xCrypto.FastEncrypt("normal", sString);
		sDecoded   = xCrypto.FastDecrypt("normal", sOutput);

		sOutput1    = xCrypto.FastEncrypt("pattern7", sString);
		sDecoded1   = xCrypto.FastDecrypt("pattern7", sOutput1);
		*/
		sOutput_H	= xCrypto.Hash(6, sString);
%>
		<br><B>
		[Normal policy]
		<br>
		<br>
		<%=sString%> -> <font color=red><%=sOutput%></font>
		<br>
		<%=sOutput%> -> <font color=blue><%=sDecoded%></font>

		<br>
		<br>
        <%=Err%>  
		<br>

		<br><B>
		[Pattern7 policy]
		<br>
		<br>
		<%=sString%> -> <font color=red><%=sOutput1%></font>
		<br>
		<%=sOutput1%> -> <font color=blue><%=sDecoded1%></font>

		<br>
		<br>
		<br>
		[Hash policy]
		<br>
		<br>
		<%=sString%> -> <font color=green><%=sOutput_H%></font>

<%
		long nEndTime      = System.currentTimeMillis();
		long nDuration     = nEndTime - nStartTime;
%>
		<br>
		<br>
		<br>
		<br>
		<font color=red>Elasped: <%=nDuration%> ms</font>
		</B>
<%
	}
    	catch (Exception e)
    	{
        		e.printStackTrace();
		out.println(e);
    	}

%>

</body>
</html>
