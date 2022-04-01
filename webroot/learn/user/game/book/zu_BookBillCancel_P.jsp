<%
//**********************************************************
//  1. 제      목: 환불처리 
//  2. 프로그램명:  zu_BookBillCancel_P
//  3. 개      요: 환불시 필요한 정보를 받는다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정: 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.book.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    String  v_process  = box.getString("p_process");
    int     v_seq      = box.getInt   ("p_seq");

	String v_username  = box.getSession("userid");
%>
<html>
<head>
<title>환불처리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--

//환불처리
function Insert() {
    if(document.form1.p_refundbank.value.length < 1) {
        alert("거래은행을 입력해 주세요.");
        document.form1.p_refundbank.focus();
        document.form1.p_refundbank.select();
        return;
    }
    if(document.form1.p_refundaccount.value.length < 1 ) {
        alert("계좌번호를 입력해 주세요.");
        document.form1.p_refundaccount.focus();
        document.form1.p_refundaccount.select();
        return;
    }

    document.form1.action = "/servlet/controller.book.BookServlet";
    document.form1.p_process.value = "bookBillCancel";
    document.form1.submit();
}

//-->
</SCRIPT>
</head>

<body bgcolor="F6F6F6">
<form name="form1" method="post">
    <input type='hidden' name='p_process' value="<%=v_process%>">
    <input type='hidden' name='p_seq'     value="<%=v_seq%>">

<table width="400" border="0" cellpadding="0" cellspacing="0" bgcolor="F6F6F6">
  <tr>
    <td><img src="/images/user/game/apply/pop_tit_mapply.gif"></td>
  </tr>
  <tr>
    <td><table width="350" border="0" cellspacing="0" cellpadding="0">
        <tr background="/images/user/game/apply/pop_box_topbg.gif">
          <td width="22" height="10" valign="top" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toph.gif" width="22" height="10"></td>
          <td width="657" height="10" background="/images/user/game/apply/pop_box_topbg.gif"></td>
          <td width="21" height="10" align="right" background="/images/user/game/apply/pop_box_topbg.gif"><img src="/images/user/game/apply/pop_box_toptail.gif" width="21" height="10"></td>
        </tr>
        <tr>
          <td valign="top" background="/images/user/game/apply/pop_box_lbg.gif">&nbsp;</td>
          <td align="center" valign="top" bgcolor="#FFFFFF">
            <!-- 수강안내 -->
            <table width="350" border="2" cellspacing="0" cellpadding="3" style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides">
              <tr  class="lcolor"> 
                <td height="3" class="linecolor_app"></td>
                <td width="57" height="3" class="linecolor_app2"></td>
                <td width="54" height="3" class="linecolor_app2"></td>
                <td width="102" height="3" class="linecolor_app2"></td>
              </tr>
              <tr> 
                <td width="101" class="tbl_gtit2">환불대상자이름</td>
                <td colspan="3" class="tbl_gleft"><%=v_username %></td>
              </tr>
              <tr> 
                <td height="26" class="tbl_gtit2">거래은행</td>
                <td colspan="3" class="tbl_gleft"><input type="text" class="input" name="p_refundbank"></td>
              </tr>
              <tr> 
                <td class="tbl_gtit2">계좌번호</td>
                <td colspan="3" class="tbl_gleft"><input type="text" class="input" name="p_refundaccount"></td>
              </tr>
            </table>
            <table width="350" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
              <tr> 
                <td colspan="2" align="right"><a href="javascript:Insert()"><img src="/images/user/game/button/btn_confirm.gif" width="48" height="21" border="0"></a> <a href="javascript:self.close()"><img src="/images/user/game/button/btn_close.gif" border="0"></a></td>
              </tr>
            </table></td>
          <td background="/images/user/game/apply/pop_box_rbg.gif">&nbsp;</td>
        </tr>
        <tr>
          <td valign="bottom" background="/images/user/game/apply/pop_box_lbg.gif"><img src="/images/user/game/apply/pop_box_boh.gif" width="22" height="21"></td>
          <td background="/images/user/game/apply/pop_box_bobg.gif">&nbsp;</td>
          <td valign="bottom" background="/images/user/game/apply/pop_box_rbg.gif"><img src="/images/user/game/apply/pop_box_botail.gif" width="21" height="21"></td>
        </tr>
      </table></td>
  </tr>
</table>
</body>
</html>
