<%@ page contentType = "text/html;charset=MS949" %>
<%@ page import = "com.credu.library.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String upperclass = box.getString("upperclass");
    String subj       = box.getString("subj");

%>
<html>
<head>
<title>::: 용어사전 :::</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<frameset rows="287,*" cols="*" frameborder="NO" border="0" framespacing="0"> 
  <frame name="topFrame" src="/servlet/controller.course.DicSubjServlet?p_process=selectTop&upperclass=<%=upperclass%>&subj=<%=subj%>" scrolling="NO" noresize  frameborder="NO" >
  <frameset cols="275,*" frameborder="NO" border="0" framespacing="0" rows="*"> 
    <frame name="leftFrame" src="/servlet/controller.course.DicSubjServlet?p_process=selectListPre" frameborder="NO" scrolling="AUTO">
    <frame name="mainFrame" src="/servlet/controller.course.DicSubjServlet?p_process=selectViewPre" frameborder="NO" scrolling="AUTO">
  </frameset>
</frameset>
<noframes> 
<body bgcolor="#FFFFFF" text="#000000">
</body>
</noframes> 
</html>
