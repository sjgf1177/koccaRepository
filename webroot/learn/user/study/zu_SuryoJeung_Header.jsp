<%
/**
 * file name : za_SuryoJeung_P.jsp
 * date      : 2004/12/30
 * programmer: Lee Chang Hun
 * function  : 수료증 Header
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.goyong.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
</head>
<body topmargin=0 leftmargin=0>
<form name="form1" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
      <table width="700" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align=right><br>
            <table border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><a href="javascript:parent.bottom.focus();parent.bottom.doPrint();"><img src="/images/admin/button/btn_print.gif" alt="인쇄" border=0></a></td>
                <td width=8></td>
                <td><a href="javascript:parent.window.close();"><img src="/images/admin/button/btn_close.gif" alt="닫기" border=0></a></td>
              </tr>
            </table></td>
        </tr>
      </table>
      </td>
  </tr>
</table>
</form>
</body>
</html>
