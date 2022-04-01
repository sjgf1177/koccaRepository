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

    String v_subj       = box.getString("p_subj");
    String v_year       = box.getString("p_year");
    String v_subjnm     = box.getString("p_subjnm");
    String v_subjseq    = box.getString("p_subjseq");

    String v_userid     = box.getSession("userid");
    String v_username   = box.getSession("name");

    String v_membergubun= "";       
    String v_chkfinal   = "";
    String v_edustart   = "";
    String v_eduend     = "";
    String v_appdate    = "";
    String v_subjprice  = "";
    String v_usepoint   = "";
    String v_paymoney   = "";
    String v_accountname= "";
    String v_paydate    = "";
    String v_paytype    = "";
    String v_refundbank = "";
    String v_refundaccount  = "";
    String v_discountrate   = "";
    String v_refundmoney    = "";
    String v_refunddate     = "";
    String v_refundstat = "";

    DataBox dbox = (DataBox)request.getAttribute("select");

    if (dbox != null) {    
        v_subj          = dbox.getString("d_subj");
        v_subjnm        = dbox.getString("d_subjnm");
        v_userid        = dbox.getString("d_userid");
        v_subjseq       = dbox.getString("d_subjseq");
        v_subjprice     = dbox.getString("d_subjprice");
        v_discountrate  = dbox.getString("d_discountrate");
        v_usepoint      = dbox.getString("d_usepoint");
        v_paymoney      = dbox.getString("d_paymoney");
        v_accountname   = dbox.getString("d_accountname");
        v_paydate       = dbox.getString("d_paydate");
        v_paytype       = dbox.getString("d_paytype");
        v_refundbank    = dbox.getString("d_refundbank");
        v_refundaccount = dbox.getString("d_refundaccount");
        v_refundmoney   = dbox.getString("d_refundmoney");
        v_refunddate    = dbox.getString("d_refunddate");
        v_refundstat    = dbox.getString("d_refundstat");
     }  


%>
<html>
<head>
<title>결제처리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">
<script>
    function Insert()
    {
        var frm = document.form1;

        frm.action = "/servlet/controller.polity.KRefundmentAdminServlet";
        frm.p_process.value = "RefundmentInsert";
        frm.submit();
    }
</script>
</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">

<form name="form1">
    <input type='hidden' name='p_process'   >
    <input type='hidden' name='p_subj'      value="<%=v_subj%>">
    <input type='hidden' name='p_year'      value="<%=v_year%>">
    <input type='hidden' name='p_userid'    value="<%=v_userid%>">
    <input type='hidden' name='p_subjseq'   value="<%=v_subjseq%>">
    <input type='hidden' name='p_subjnm'    value="<%=v_subjnm%>">
    <input type='hidden' name='p_paymoney'  value="<%=v_paymoney%>">
    <input type='hidden' name='p_grtype'    value="KOCCA">
    
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
                <td class="tbl_gleft"><%=v_username %></td>
              </tr>
              <tr> 
                <td align="center" bgcolor="#FFF3EC" >거래은행</td>
                <td class="tbl_gleft"><input type="text" class="input" name="p_refundbank"></td>
              </tr>
              <tr> 
                <td align="center" bgcolor="#FFF3EC" >무통장입금계좌</td>
                <td class="tbl_gleft"><input type="text" class="input" name="p_refundaccount"></td>
              </tr>
              <tr> 
                <td align="center" bgcolor="#FFF3EC" >예금주명</td>
                <td class="tbl_gleft"><input type="text" class="input" name="p_accountname"></td>
              </tr>
            </table></td>
          <td width="24">&nbsp;</td>
        </tr>
        <tr> 
          <td height="10" colspan="3"></td>
        </tr>
        <tr> 
          <td>&nbsp;</td>
          <td><div align="right"><a href="javascript:Insert()"><img src="/images/user/kocca/button/btn_ok.gif" width="42" height="19"></a>&nbsp;&nbsp;<a href="javascript:self.close()"><img src="/images/user/kocca/button/btn_close.gif" width="42" height="19"></a></div></td>
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
