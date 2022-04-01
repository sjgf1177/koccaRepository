<%
/**
 * file name : za_MetadataFrame.jsp
 * date      : 2004/11/23
 * programmer: Park Mibok
 * function  : Metadata Frame
 */
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.scorm.*" %>
<%
	String v_metadata_idx = (String)request.getAttribute("metadata_idx");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title>SCORM MDG</title>
</head>
<!-- frames -->
<!-- frameset  cols="70%,*"  border=0 frameborder=0 framespacing=0 -->
<frameset  cols="65%,*" >
    <frameset  rows="80,*,25" border=0 frameborder=0 framespacing=0>
        <frame name="header" src="/learn/admin/include/za_MetaDataHeader.jsp" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" >
        <frame name="main" src="/servlet/controller.contents.MetaDataServlet?p_process=listPage&p_metadata_idx=<%=v_metadata_idx %>" marginwidth="0" marginheight="0" scrolling="auto" frameborder="0" >
        <frame name="footer" src="/learn/admin/include/za_MetaDataFooter.jsp" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" >
    </frameset>
    <frame name="info" src="/learn/admin/include/za_MetaDataInfo.jsp" marginwidth="0" marginheight="0" scrolling="auto" frameborder="0" >
</frameset>
</html>
