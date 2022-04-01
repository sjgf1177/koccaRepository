<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 비밀번호 검색화면 
//  2. 프로그램명: zu_CpSearch_Pw.jsp                
//  3. 개      요: 외주관리 시스템 비밀번호 검색화면 
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 07. 18
//  7. 수      정: 이연정 2005. 07. 18
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
  RequestBox box = (RequestBox)request.getAttribute("requestbox");

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/cp_style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--
//비밀번호 찾기
function juminSearch() {
	if(form1.p_userid.value == ""){
		alert("사번을 입력하세요!");
		document.form1.p_userid.focus();
	}else if(form1.p_name.value == ""){
		alert("이름을 입력하세요!");
		document.form1.p_name.focus();
	}else if (form1.p_resno1.value == ""){
		alert("주민번호 앞의 여섯자리를 입력하세요!");
		document.form1.p_resno1.focus();
	}else if (form1.p_resno2.value == ""){
		alert("주민번호 뒤 일곱자리를 입력하세요!");
		document.form1.p_resno2.focus();
	}else if(form1.p_userid.value != "" &&form1.p_name.value != "" && form1.p_resno1.value != "" && form1.p_resno2.value != ""){
    document.form1.p_resno.value = document.form1.p_resno1.value + document.form1.p_resno2.value;
    document.form1.p_process.value   = "jumin";
    document.form1.action            = "/servlet/controller.cp.CpPassServlet";
    document.form1.submit();
	}	
}
//메인페이지로 이동
function indexList() {
	document.form1.p_process.value = "index"; 
	alert("1");
	document.form1.action          = "/servlet/controller.cp.CpNoticeServlet";
	alert("2");
	document.form1.submit()
}
//엔터키 먹기
function jumin_enter(e) { 
    if (e.keyCode =='13'){  juminSearch();  }
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

//자동이동
function moveFocus(num,fromform,toform)
{
  var str = fromform.value.length;
  if(str == num)
  toform.focus();
}

// 숫자만 들어가게 만든다.
function onlyNumber()
{
  if(((event.keyCode<48)||(event.keyCode>57)))
  event.returnValue=false;
}
//-->
</script>
</head>

<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" onLoad="MM_preloadImages('/images/cp/m_board_on.gif','/images/cp/m_company_on.gif','/images/cp/m_sdata_on.gif','/images/cp/m_swdata_on.gif','/images/user/button/btn_idpw_on.gif','/images/user/button/btn_login_on.gif','/images/cp/m_data_on.gif')">
<%@include file="zu_Include.jsp"%>
<!-- center -->
<table width="100%" border="0" cellpadding="0" cellspacing="0" background="/images/cp/bg_subtotal.gif">
  <tr> 
    <td valign="top">
	<!-- topimg -->
	<table width="900" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="12" valign="top"><img src="/images/cp/img_sub_head.gif"></td>
          <td width="888" valign="top"><img src="/images/cp/noticeimg.jpg"></td>
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
                      <td><img src="/images/cp/tit_pw.gif" width="835" height="34"></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="800" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center" valign="top">&nbsp; </td>
                    </tr>
                    <tr> 
                      <td align="center" valign="top"> 

                        <!-- 로그인박스 -->
						<FORM METHOD=POST name="form1">
						<input type = "hidden" name = "p_process">
						<input type = "hidden" name = "p_resno">

                        <table width="428" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="135" valign="top"><img src="/images/cp/log_img.jpg" ></td>
                            <td width="293" valign="top"> <table width="292" height="131" border="0" cellpadding="0" cellspacing="0" background="/images/cp/log_bg.gif">
                                <tr> 
                                  <td height="18" valign="top"><img src="/images/cp/log_top.gif" width="292" height="18"></td>
                                </tr>
                                <tr> 
                                  <td height="96" align="center"><table width="200" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td width="259" class="padd03">&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td align="right"><table width="214" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td width="18"><img src="/images/cp/log_id.gif"></td>
                                              <td width="108"><input name="p_userid" type="text" class="input_login"" size="20" maxlength="15"></td>
                                            </tr>
                                            <tr> 
                                              <td height="5" colspan="2"></td>
                                            </tr>
                                            <tr> 
                                              <td width="18"><img src="/images/cp/log_name.gif"></td>
                                              <td width="108"><input name="p_name" type="text" class="input_login"" size="20" maxlength="15"></td>
                                            </tr>
                                            <tr> 
                                              <td height="5" colspan="2"></td>
                                            </tr>

                                            <tr> 
                                              <td><img src="/images/cp/log_junum.gif"></td>
                                              <td><input name="p_resno1" type="text" class="input_login"  onkeyup="moveFocus(6,this,this.form.p_resno2);" size="6" maxlength="6" OnKeypress="onlyNumber();">
                                                - 
                                                <input name="p_resno2" type="text" class="input_login"  size="7" maxlength="7" onkeypress="jumin_enter(event)"></td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                      <tr> 
                                        <td height="8" align="right"><img src="/images/cp/log_line.gif"></td>
                                      </tr>
                                      <tr> 
                                        <td height="8" align="right"><a href="javascript:juminSearch()" target="_self" onfocus=this.blur()><img src="/images/cp/btn_confirm.gif" border="0"></a> 
                                          <a href="javascript:indexList()"><img src="/images/cp/btn_cancel.gif"></a></td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr> 
                                  <td height="17" valign="top"><img src="/images/cp/log_bo.gif" width="292" height="17"></td>
                                </tr>
                              </table></td>
                          </tr>
                        </table>
						</form>
					  </td>
                    </tr>
                    <tr>
                      <td height="35" align="center" valign="top">&nbsp;</td>
                    </tr>
                  </table>
                  
                </td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>
<%@ include file = "/include/cp_footer.jsp"%>


<%@ include file = "/learn/library/getJspName.jsp" %> 

</body>
</html>
