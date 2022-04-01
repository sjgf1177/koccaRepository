<%
/**
 * file name : za_MasterFormSubjObject_Fset.jsp
 * date      : 2003/10/22
 * programmer: LeeSuMin
 * function  : Lesson에 Object연결 Frame Set.
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }
%>
<html>
<head>
<title>Select Object</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>

<frameset rows="50%,50%" cols=*" framespacing="2" frameborder="YES" border="0">
  <frame src="/servlet/controller.contents.SCObjectServlet?p_process=searchList&s_subj=<%=box.getString("p_subj")%>" name="fup" scrolling="auto">
  <frame src="/servlet/controller.contents.MasterFormServlet?p_process=subjObjectPage&p_subj=<%=box.getString("p_subj")%>&p_module=<%=box.getString("p_module")%>&p_lesson=<%=box.getString("p_lesson")%>" name="fdown">
</frameset>
</html>
