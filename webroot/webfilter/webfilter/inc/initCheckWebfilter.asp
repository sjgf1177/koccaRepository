<%
    Set socket = Server.CreateObject("cssock.SocketTCP")
	wfcontextRoot = ""
	wfServerAddress="xxx.xxx.xxx.xxx"
    WFsocket = socket.Connect(wfServerAddress, 80)
	If WFsocket = false Then
		Response.Write "<iframe id='webfilterSmsFrame' name='webfilterSmsFrame' width='0' src='"&wfcontextRoot&"/webfilter/html/webfilterBypass.html' height='0' frameborder='0' scrolling='no' noresize></iframe>"
    Else
			Response.Write "<script type='text/javascript' src='"&wfcontextRoot&"/webfilter/js/webfilter.js' defer='defer'></script>"
			Response.Write "<iframe id='webfilterTargetFrame' name='webfilterTargetFrame' width='0' height='0' frameborder='0' scrolling='no' noresize></iframe>"
	End If
    Set socket = Nothing
%>