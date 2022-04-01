<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import="com.credu.library.*" %>
<%@ page import = "java.lang.String" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box        = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.setSession("tem_grcode","N000034");
    box.setSession("tem_sso","Y");

    String id=box.getString("id");

     String strUserId = id;
    if(strUserId.equals(""))
    {
%>
<body>
<script type="text/javascript">
    alert("잘못된 접근 입니다.");
    location.href="http://edu.kocca.or.kr";
</script>
<%} else {%>
<jsp:forward page="/servlet/controller.homepage.MainServlet" >
        <jsp:param name="p_id" value="<%=strUserId%>" />
        <jsp:param name="gubun" value="60" />
        <jsp:param name="p_process" value="ASP_Login" />
    </jsp:forward>
<%}%>