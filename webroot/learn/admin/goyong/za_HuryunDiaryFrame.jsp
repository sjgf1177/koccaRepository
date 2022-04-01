<%
/**
 * file name : za_HuryunDiaryFrame.jsp
 * date      : 2004/12/30
 * programmer: 
 * function  : ÈÆ·ÃÀÏÁö Frame
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title></title>
</head>
    <frameset  rows="50,*" border=0 frameborder=0 framespacing=0>
        <frame name="header" src="/learn/admin/goyong/za_PopupHeader.jsp" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" >
        <frame name="bottom" src="/learn/admin/goyong/za_HuryunExe_P.jsp" marginwidth="0" marginheight="0" scrolling="auto" frameborder="0" >
    </frameset>
</html>