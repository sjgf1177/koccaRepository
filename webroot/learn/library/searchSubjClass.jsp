<%
//**********************************************************
//  1. 제      목: 과정 대분류 중분류 (IFrame)
//  2. 프로그램명: searchSubjClass.jsp
//  3. 개      요: 과정 대분류 중분류 (IFrame)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2005. 11. 14
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String v_formname = box.getString("p_formname");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="JavaScript">
function whenSelection(ss_action) {
    parent.<%=v_formname%>.p_upperclass.value = document.form1.s_upperclass.value;
    parent.<%=v_formname%>.p_middleclass.value = document.form1.s_middleclass.value;
    parent.<%=v_formname%>.p_lowerclass.value = document.form1.s_lowerclass.value;

        document.form1.submit();
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
      <form name="form1" method="post" action="/servlet/controller.library.SearchServlet">
<table  border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="left" valign="top"> 
        <input type="hidden" name="p_process" value="subjclass">
        <input type="hidden" name="p_formname" value="<%=v_formname%>">
        <%= SelectSubjBean.getUpperClass(box, true, true, false)%><!-- getUpperclass(RequestBox, isChange, isALL, isStatisticalPage)    과정대분류  --><br>
        <%= SelectSubjBean.getMiddleClass(box, true, true, false)%><!-- getMiddleclass(RequestBox, isChange, isALL, isStatisticalPage)    과정중분류  --><br>
        <%= SelectSubjBean.getLowerClass(box, true, true, true)%><!-- getLowerclass(RequestBox, isChange, isALL, isStatisticalPage)    과정소분류  -->
      </td>
  </tr>
</table>
    </form>                      
</body>
</html>
