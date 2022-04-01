<%
/**
 * file name : z_EduStart_fup.jsp
 * date      : 2003/08/21
 * programmer: LeeSuMin
 * function  : 마스터폼 Upper Frame
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.library.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }

    String	p_url	= box.getString("p_url");
%>
<html>
<head>
<title>Education...</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<script language=javascript>
	var urlStr = "<%=p_url%>";
	parent.etop.popup_window(urlStr);
</script>
</head>
<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

</body>
</html>
