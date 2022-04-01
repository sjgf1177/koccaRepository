<%
//**********************************************************
//  1. 제      목: 
//  2. 프로그램명: 
//  3. 개      요: 
//  4. 환      경: 
//  5. 버      젼: 
//  6. 작      성: 
//  7. 수      정: 
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String v_subj   = box.getString("p_subj");
    String v_year   = box.getString("p_year");
    String v_subj   = box.getString("p_subj");
    String v_userid = box.getString("p_userid");
    String v_subjnm = box.getString("p_subjnm");
    String v_userid = box.getSession("userid");


%>
<html>
<head>
<title>결제처리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">
<script>
    
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<form name="form1">
    <input type='hidden' name='p_process'   >
    <input type='hidden' name='p_subj'      value="<%=v_subj%>">
    <input type='hidden' name='p_year'      value="<%=v_year%>">
    <input type='hidden' name='userid'      value="<%=v_userid%>">
    <input type='hidden' name='p_subjseq'   value="<%=v_subjseq%>">
    <input type='hidden' name='p_subjnm'    value="<%=v_subjnm%>">
    <input type='hidden' name='p_grtype'    value="KDGI">

<table width="673" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="/images/user/kocca/apply/paywindow_top.gif" width="673" height="12"></td>
  </tr>
  <tr>
    <td background="/images/user/kocca/apply/paywindow_bg.gif">
    <table width="667" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="6">&nbsp;</td>
          <td width="667" align="right"><img src="/images/user/kocca/apply/pop_tit_mapply.gif"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
    <td height="25" background="/images/user/kocca/apply/paywindow_bg.gif">&nbsp;</td>
  </tr>
</table>
<table width="673" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td colspan="2" background="/images/user/kocca/apply/paywindow_bg.gif">
    <table width="673" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="26">&nbsp;</td>
          <td width="623"><table width="623" border="1" cellpadding="0" cellspacing="0" bordercolor="#EDEDED">
              <tr> 
                <td width="151" height="5" bgcolor="#cb7c39"></td>
                <td width="466" bgcolor="#cfcfcf"></td>
              </tr>
              <tr> 
                <td align="center" bgcolor="#FFF3EC" >환불대상자 이름</td>
                <td class="tbl_gleft">박대상</td>
              </tr>
              <tr> 
                <td align="center" bgcolor="#FFF3EC" >거래은행</td>
                <td class="tbl_gleft"><input type="text" class="input" name="p_refundbank></td>
              </tr>
              <tr> 
                <td align="center" bgcolor="#FFF3EC" >무통장입금계좌</td>
                <td class="tbl_gleft"><input type="text" class="input" name="p_refundaccount"></td>
              </tr>
            </table></td>
          <td width="24">&nbsp;</td>
        </tr>
        <tr> 
          <td height="10" colspan="3"></td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td><div align="right"><img src="/images/user/kocca/button/btn_ok.gif" width="42" height="19">&nbsp;&nbsp;<img src="/images/user/kocca/button/btn_close.gif" width="42" height="19"></div></td>
          <td>&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="3">&nbsp;</td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td colspan="2"><img src="/images/user/kocca/apply/paywindow_bottom.gif" width="673" height="19"></td>
  </tr>
</table>
</form>
</body>
</html>
