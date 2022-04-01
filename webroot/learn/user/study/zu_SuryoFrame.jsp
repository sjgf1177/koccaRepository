<%
/**
 * file name : za_SuryoFrame.jsp
 * date      : 2004/12/30
 * programmer: 
 * function  : 수료대장 Frame
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
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    request.setAttribute("box", box);

    String  ss_subjseq         = box.getString("s_subjseq");          //과정 차수
    String  ss_subjcourse         = box.getString("s_subjcourse");          //과정 차수
%>

<html>
<head>
	<title></title>
</head>
    <frameset  rows="50,*" border=0 frameborder=0 framespacing=0>
        <frame name="header" src="/learn/admin/goyong/za_PopupHeader.jsp" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" >
        <frame name="bottom" src="/learn/admin/goyong/za_SuryoJeung_V.jsp?s_subjseq=<%= ss_subjseq %>&s_subjcourse=<%= ss_subjcourse %>" marginwidth="0" marginheight="0" scrolling="auto" frameborder="0" >
    </frameset>
</html>