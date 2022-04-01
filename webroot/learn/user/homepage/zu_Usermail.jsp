<%
//**********************************************************
//  1. 제      목: 사용자 메일
//  2. 프로그램명: za_Usermail.jsp
//  3. 개      요: 사용자 메일
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정사진 2005. 9. 11
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String v_tuserid    = box.getString("p_tuserid");
    String v_tusername  = box.getString("p_tusername");
    String v_tuseremail = box.getString("p_tuseremail");
    String v_fuserid    = box.getSession("userid");
    String v_fusername  = box.getSession("name");
    String v_fuseremail = box.getSession("email");

%>
<html>
<head>
<title>메일</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
// 메일전송
function whenSumit() {

    if (document.form1.p_title.value == "") {
        alert("제목을 작성하세요");
        return;
    }
    if (document.form1.p_contents.value == "") {
        alert("내용을 작성하세요");
        return;
    }
    if (document.form1.p_fuseremail.value == "") {
        alert("E-MAIL을작성하세요");
        return;
    }


    document.form1.action="/servlet/controller.homepage.MainServlet"
    document.form1.p_process.value = 'usermailsend';
    document.form1.submit();
}
//-->
</SCRIPT>
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<form name = "form1" method = "post" action="/servlet/controller.homepage.MainServlet">
    <input type="hidden" name="p_process"        value="usermailsend">
    <input type="hidden" name="p_tuserid"        value="<%=v_tuserid%>">
    <input type="hidden" name="p_tusername"      value="<%=v_tusername%>">
    <input type="hidden" name="p_tuseremail"     value="<%=v_tuseremail%>">

<table width="590"  cellpadding="0" cellspacing="10" bgcolor="#EEEEEE">
  <tr> 
    <td> <table cellpadding="0" cellspacing="1" bgcolor="#bebebe" class="table2" height="100%">
        <tr> 
          <td align="center" valign="top" bgcolor="#FFFFFF" class="body_color"><img src="/images/user/common/mail_topimg.jpg"> 
            <!-- qna table -->
            <table width="550" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td colspan="4"><img src="/images/user/common/mail_line.gif"></td>
              </tr>
              <tr> 
                <td width="94"><img src="/images/user/common/text_title.gif"></td>
                <td width="456"  class="tbl_gleft" colspan="3"> <input type="text" name="p_title" size="60" class="input" maxlength="55"> 
                </td>
              </tr>
              <tr> 
                <td colspan="4"><img src="/images/user/common/mail_line.gif"></td>
              </tr>
              <tr> 
                <td width="94"><img src="/images/user/common/text_pe.gif"></td>
                <td  width="181" class="tbl_gleft">
<%        if (v_fuserid.equals("")) {      %>
                  <input type="text" name="p_fusername" size="15" value="<%=v_fusername%>"  class="input">
<%        } else {                         %>
                  <%=v_fusername%><input type="hidden" name="p_fusername" value="<%=v_fusername%>">
<%        }                                %>
                </td>
                <td width="94"><img src="/images/user/common/text_id.gif"></td>
                <td width="181" class="tbl_gleft">
<%        if (v_fusername.equals("")) {      %>
                  <input type="text" name="p_fuserid" size="15" value="<%=v_fuserid%>"  class="input">
<%        } else {                         %>
                  <%=v_fuserid%><input type="hidden" name="p_fuserid" value="<%=v_fuserid%>">
<%        }                                %>

                </td>
              </tr>
              <tr> 
                <td colspan="4"><img src="/images/user/common/mail_line.gif"></td>
              </tr>
              <tr> 
                <td><img src="/images/user/common/text_email.gif"></td>
                <td class="tbl_gleft"  colspan="3">
<%        if (v_fuseremail.equals("")) {      %>
                  <input type="text" name="p_fuseremail" size="60" value="<%=v_fuseremail%>"  class="input">
<%        } else {                         %>
                  <%=v_fuseremail%><input type="hidden" name="p_fuseremail" value="<%=v_fuseremail%>">
<%        }                                %>
                </td>
              </tr>
              <tr> 
                <td colspan="4"><img src="/images/user/common/mail_line.gif"></td>
              </tr>
              <tr> 
                <td colspan="4"><img src="/images/user/common/mail_line.gif"></td>
              </tr>
              <tr> 
                <td colspan="4"><img src="/images/user/common/text_con-1.gif"></td>
              </tr>
              <tr valign="top"> 
                <td colspan="4" class="tbl_contents"> <textarea name="p_contents" cols="80" rows="15" ></textarea> 
                </td>
              </tr>
              <tr> 
                <td height="3" colspan="4"></td>
              </tr>
              <tr bgcolor="B8D5E5"> 
                <td height="5" colspan="4"><img src="/images/user/common/com_tb_bo-2.gif"></td>
              </tr>
            </table>
            <table width="550" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="10" colspan="2"></td>
              </tr>
              <tr> 
                <td  align="right">&nbsp; </td>
                <td width="257" align="right"> <a href="javascript:whenSumit()"><img src="/images/user/button/btn_send.gif" border="0"></a> 
                  <a href="javascript:self.close()"><img src="/images/user/button/btn_cancel.gif" border="0"></a> 
                </td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>

</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
