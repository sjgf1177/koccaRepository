<%
//**********************************************************
//  1. 제      목: 환불처리 
//  2. 프로그램명: ku_Refundment_I.jsp
//  3. 개      요: 환불시 필요한 정보를 받는다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 하경태 2006.01.12
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

	String v_subj		= box.getString("p_subj");
	String v_year		= box.getString("p_year");
	String v_subjnm		= box.getString("p_subjnm");
	String v_subjseq	= box.getString("p_subjseq");

	String v_userid		= box.getSession("userid");
	String v_username	= box.getSession("name");

	String v_membergubun= "";		
	String v_chkfinal	= "";
	String v_edustart	= "";
	String v_eduend 	= "";
	String v_appdate	= "";
	String v_subjprice	= "";
	String v_usepoint	= "";
	String v_paymoney	= "";
	String v_accountname= "";
	String v_paydate	= "";
	String v_paytype	= "";
	String v_refundbank	= "";
	String v_refundaccount	= "";
	String v_discountrate	= "";
	String v_refundmoney	= "";
	String v_refunddate		= "";
	String v_refundstat	= "";

	DataBox dbox = (DataBox)request.getAttribute("select");

	if (dbox != null) {    
		v_subj			= dbox.getString("d_subj");
		v_subjnm		= dbox.getString("d_subjnm");
		v_userid		= dbox.getString("d_userid");
		v_subjseq		= dbox.getString("d_subjseq");
		v_subjprice		= dbox.getString("d_subjprice");
		v_discountrate	= dbox.getString("d_discountrate");
		v_usepoint		= dbox.getString("d_usepoint");
		v_paymoney		= dbox.getString("d_paymoney");
		v_accountname	= dbox.getString("d_accountname");
		v_paydate		= dbox.getString("d_paydate");
		v_paytype		= dbox.getString("d_paytype");
		v_refundbank	= dbox.getString("d_refundbank");
		v_refundaccount	= dbox.getString("d_refundaccount");
		v_refundmoney	= dbox.getString("d_refundmoney");
		v_refunddate	= dbox.getString("d_refunddate");
		v_refundstat	= dbox.getString("d_refundstat");
     } 
%>
<html>
<head>
<title>결제처리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<script>
	function Insert()
	{
		var frm = document.form1;

		frm.action = "/servlet/controller.polity.RefundmentAdminServlet";
		frm.p_process.value = "RefundmentInsert";
		frm.submit();
	}
</script>
</head>

<body bgcolor="F6F6F6">
<form name="form1">
    <input type='hidden' name='p_process'	>
    <input type='hidden' name='p_subj'		value="<%=v_subj%>">
    <input type='hidden' name='p_year'		value="<%=v_year%>">
    <input type='hidden' name='p_userid'	value="<%=v_userid%>">
    <input type='hidden' name='p_subjseq'	value="<%=v_subjseq%>">
    <input type='hidden' name='p_subjnm'	value="<%=v_subjnm%>">
    <input type='hidden' name='p_paymoney'	value="<%=v_paymoney%>">
    <input type='hidden' name='p_grtype'	value="KGDI">
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
			  <tr> 
                <td class="tbl_gtit2">예금주명</td>
                <td colspan="3" class="tbl_gleft"><input type="text" class="input" name="p_accountname"></td>
              </tr>
            </table>
            <table width="350" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td colspan="2" align="right">&nbsp;</td>
              </tr>
              <tr> 
                <td colspan="2" align="right"><a href="javascript:Insert()"><img src="/images/user/game/button/btn_confirm.gif" width="48" height="21" border="0"></a> <a href="self.close()"><img src="/images/user/game/button/btn_close.gif" border="0"></a></td>
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
