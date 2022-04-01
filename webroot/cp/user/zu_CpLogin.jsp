<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 로그인화면
//  2. 프로그램명: zu_CpLogin.jsp
//  3. 개      요: 외주관리 시스템 로그인화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 07. 18
//  7. 수      정: 이연정 2005. 07. 18
//***********************************************************
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>       
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.common.*" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../../css/cp_style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--

function password_enter(e) { 
    if (e.keyCode =='13'){  login();  }
}

function login() {
	if (document.form1.p_userid.value=="") {
		alert("사번을 입력하세요");
		document.form1.p_userid.focus();
		return;
	}

	if (document.form1.p_pwd.value=="") {
		alert("비밀번호를 입력하세요");
		document.form1.p_pwd.focus();
		return;
	}

    document.form1.p_process.value   = "login";
    document.form1.action            = "/servlet/controller.cp.CpLoginServlet";
    document.form1.submit();
}

function winonload() {
<% if (session.getAttribute("userid")==null) {%>     
  if( getCookie( "userid_cp" ) == "" ) {
    document.form1.p_userid.focus();
  } else {
    document.form1.p_userid.value = getCookie( "userid_cp" );
    document.form1.rememberId.checked = true;
    document.form1.p_pwd.focus();
  }
<% } else { %>
	return;
<% } %>
}


function getCookie( name ) {
    var nameOfCookie = name + "=";
    var x = 0;
    while ( x <= document.cookie.length )
    {
        var y = (x+nameOfCookie.length);
        if ( document.cookie.substring( x, y ) == nameOfCookie ) {
            if ( (endOfCookie=document.cookie.indexOf( ";", y )) == -1 )
                endOfCookie = document.cookie.length;
            return unescape( document.cookie.substring( y, endOfCookie ) );
        }
        x = document.cookie.indexOf( " ", x ) + 1;
        if ( x == 0 )
            break;
    }
    return "";
}

function pwSearch() {
   document.form1.p_process.value   = "pass";
   document.form1.action            = "/servlet/controller.cp.CpPassServlet";
   document.form1.submit();
}



function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}
//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('../../../images/cp/m_board_on.gif','../../../images/cp/m_company_on.gif','../../../images/cp/m_sdata_on.gif','../../../images/cp/m_swdata_on.gif','../../../images/user/button/btn_idpw_on.gif','../../../images/user/button/btn_login_on.gif','../../../images/cp/m_data_on.gif','../../../images/user/button/btn_login-1_on.gif','../../../images/user/button/btn_pwsearch_on.gif')">
<%@include file="zu_Include.jsp"%>


<table width="100%" border="0" cellpadding="0" cellspacing="0" background="../../../images/cp/bg_subtotal.gif">
  <tr> 
    <td valign="top">
	<!-- topimg -->
	<table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="12" valign="top"><img src="../../../images/cp/img_sub_head.gif"></td>
          <td width="888" valign="top"><img src="../../../images/cp/noticeimg.jpg"></td>
        </tr>
        <tr valign="top"> 
          <td colspan="2">
		
		  <table width="900" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td height="20" align="center" valign="top"></td>
              </tr>
              <tr> 
                <td align="center" valign="top">
				<table width="835" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="../../../images/cp/tit_login.gif"></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table>

				<form name = "form1" method = "post">
				<input type="hidden" name="p_systemgubun" value="">
				<input type='hidden' name='p_process'	value=''>
				<input type='hidden' name='p_frmURL'	value=''>
                  <table width="800" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center" valign="top"> 
                        <!-- 로그인박스 -->
                        <table width="428" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="135" valign="top"><img src="../../../images/cp/log_img.jpg" ></td>
                            <td width="293" valign="top"> 
						  <table width="292" height="131" border="0" cellpadding="0" cellspacing="0" background="../../../images/cp/log_bg.gif">
                                <tr> 
                                  <td height="18" valign="top"><img src="../../../images/cp/log_top.gif" width="292" height="18"></td>
                                </tr>
                                <tr> 
                                  <td height="96" align="center">
								    <table width="230" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width="18"><img src="../../../images/user/homepage/id.gif"></td>
                                        <td width="108">
										<input name="p_userid" type="text" class="input_login" tabindex="1" value="" size="17">
										</td>
                                        <td width="50" rowspan="2">
										<a href="javascript:login()" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image23','','../../../images/cp/btn_login-1_on.gif',1)"><img src="../../../images/cp/btn_login-1.gif" name="Image23"  border="0"></a></td>
                                        <td width="54" rowspan="2">
										<a href="javascript:pwSearch()" target="_self" onfocus=this.blur() onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image24','','../../../images/cp/btn_pwsearch_on.gif',1)"><img src="../../../images/cp/btn_pwsearch.gif" name="Image24" border="0"></a></td>
                                      </tr>
                                      <tr> 
                                        <td><img src="../../../images/user/homepage/pw.gif" ></td>
                                        <td><input name="p_pwd" type="password" class="input_login" onkeypress="password_enter(event)" value="" size="17">
										</td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr> 
                                  <td height="17" valign="top"><img src="../../../images/cp/log_bo.gif" width="292" height="17"></td>
                                </tr>
                              </table>
							</td>
                          </tr>
                          <tr> 
                            <td height="10" colspan="2"></td>
                          </tr>
                          <tr> 
                            <td colspan="2" class="font_ex">
							  <table width="428" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td width="26">&nbsp;</td>
                                  <td width="402" class="font_ex">- 회원님이 원하시는 정보를 얻기 위해서는 로그인을 
                                    해야 합니다.<br>
                                    - 아이디와 비밀번호를 입력하신 후 로그인 버튼을 눌러 주시기 바랍니다.<br>
                                    - 회원이 아니신 분은 회원가입을 해 주시기 바랍니다.</td>
                                </tr>
                              </table></td>
                          </tr>
                        </table>
					  </td>
                    </tr>
                    <tr> 
                      <td height="35" align="center" valign="top">&nbsp;</td>
                    </tr>
                  </table>
				</form>

                </td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>



<!-- footer -->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td background="../../../images/cp/bg_footer.gif"><table width="900" height="64" border="0" cellpadding="0" cellspacing="0">
        <tr valign="top"> 
          <td colspan="2"></td>
        </tr>
        <tr> 
          <td width="131" rowspan="2" align="right" valign="top">&nbsp;</td>
          <td width="634" height="17" align="center" class="bottom_menu"><a href="#">통신훈련유의사항</a> 
            ㅣ <a href="#">개인정보보호정책</a> <strong>교육문의: TEL</strong> 080-224-9697~7 
            <strong>E-MAIL</strong> <a href="mailto:elearning@autoeversystems.com">elearning@autoeversystems.com</a> 
          </td>
        </tr>
        <tr> 
          <td height="35" align="center" valign="top"><img src="../../../images/user/homepage/type1/footer_copy.gif" ></td>
        </tr>
      </table></td>
  </tr>
</table>


</body>
</html>
