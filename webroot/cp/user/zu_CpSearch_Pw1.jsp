<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 비밀번호 검색화면
//  2. 프로그램명: zu_CpSearch_Pw.jsp
//  3. 개      요: 외주관리 시스템 비밀번호 검색화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>외주관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/gate_style.css">

<SCRIPT LANGUAGE="JavaScript">
<!--
function juminSearch() {
	if(form1.p_userid.value == ""){
		alert("ID을 입력하세요!");
	}else if (form1.p_resno.value == ""){
		alert("주민번호를 입력하세요!");
	}else if(form1.p_userid.value != "" && form1.p_resno.value != ""){
   document.form1.p_process.value   = "jumin";
   document.form1.action            = "/servlet/controller.cp.CpPassServlet";
   document.form1.submit();
	}	
}
function indexList() {

	
	document.form1.action            = "/cp/index.jsp";
	document.form1.submit()
}
function jumin_enter(e) { 
    if (e.keyCode =='13'){  juminSearch();  }
}
//-->
</SCRIPT>
</head>

<body leftmargin=0 topmargin=0>
<FORM METHOD=POST name="form1">
<input type='hidden' name='p_process'	value=''>

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><%@include file="zu_Include.jsp"%></td>
  </tr>
</table>
<table width="950" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="80">&nbsp;</td>
    <td class=O_bgline></td>
  </tr>
  <tr> 
    <td><table width="949" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="25">&nbsp;</td>
          <td colspan="3" align="center"><table width="588" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="257"><img src="/images/gate/O_loginimg_01.gif" width="257" height="29"></td>
                <td width="275" background="/images/gate/O_loginimg_bgtop.gif">&nbsp;</td>
                <td width="56"><img src="/images/gate/O_loginimg_04.gif" width="56" height="29"></td>
              </tr>
              <tr> 
                <td><img src="/images/gate/O_searchpw_02.gif" width="257" height="121"></td>
                <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height=4></td>
                      <td height=4></td>
                      <td height=4></td>
                    </tr>
                    <tr> 
                      <td width="12%">&nbsp;</td>
                      <td width="24%"><img src="/images/gate/L_searchpw_id.gif" width="52" height="12"></td>
                      <td width="64%"><input name="p_userid" type="text" size="20" class=input></td>
                    </tr>
                    <tr> 
                      <td height=6></td>
                      <td height=6></td>
                      <td height=6></td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                      <td><img src="/images/gate/L_searchpw_num.gif" width="52" height="12"></td>
                      <td><input name="p_resno" onkeypress="jumin_enter(event)" type="text" size="20" class=input>
					</td>
                    </tr>
                    <tr> 
                      <td height=10></td>
                      <td height=10></td>
                      <td height=10></td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="28%" height="14"><a href="javascript:juminSearch()" target="_self" onfocus=this.blur()><img src="/images/gate/btn_confirm02.gif"   width="63" height="23" border="0"></a></td>
                            <td width="5%">&nbsp;</td>
                            <td width="67%"><a href="javascript:indexList()"><img src="/images/gate/btn_cancel03.gif" width="63" height="23" border="0"></a></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
                <td><img src="/images/gate/O_loginimg_05.gif" width="56" height="121"></td>
              </tr>
              <tr> 
                <td><img src="/images/gate/O_loginimg_03.gif" width="257" height="30"></td>
                <td background="/images/gate/O_loginimg_undertop.gif">&nbsp;</td>
                <td><img src="/images/gate/O_loginimg_06.gif" width="56" height="30"></td>
              </tr>
              <tr> 
                <td>&nbsp;</td>
                <td>&nbsp;</td>
                <td>&nbsp;</td>
              </tr>
              <tr> 
                <td colspan="3" style=padding-left:44px>* 아이디와 주민번호를 정확히 입력하여 
                  주시기 바랍니다.<br>
                  * 본인 확인 절차를 걸친후 비밀번호는 이메일로 발송이 됩니다.</td>
              </tr>
            </table></td>
          <td width="10">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25" height="80">&nbsp;</td>
          <td width="85" height="80">&nbsp;</td>
          <td width="25" height="80">&nbsp;</td>
          <td width="775" height="80">&nbsp;</td>
          <td height="50">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25">&nbsp;</td>
          <td colspan="3"><img src="/images/gate/O_copyright.gif" width="370" height="41"></td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
    <td class=O_bgline></td>
  </tr>
</table>
</FORM>>
</body>
</html>
