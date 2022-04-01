<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 비밀번호확인화면
//  2. 프로그램명: zu_CpPw_Find.jsp
//  3. 개      요: 외주관리 시스템 비밀번호확인화면
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
<%@ page import = "com.credu.cp.*" %>
<%
DataBox dbox    = (DataBox)request.getAttribute("selectQna");
String v_userid = dbox.getString("d_userid");
String v_pwd    = dbox.getString("d_pwd");
String v_name   = dbox.getString("d_name");
%>


<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../../css/cp_style.css" rel="stylesheet" type="text/css">
<script language="JavaScript" type="text/JavaScript">
<!--

//로그인 페이지로 이동
function loginpage() {
    document.form1.p_process.value   = "loginpage";
    document.form1.action            = "/servlet/controller.cp.CpLoginServlet";
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
                                        <td align="right">
										  <table width="214" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td width="210" align = "center"><b><%=v_name%></b> 님의 비밀번호는 <br><b><%=v_pwd%></b> 입니다.</td>
                                            </tr>
                                            <tr> 
                                              <td height="5" ></td>
                                            </tr>
                                          </table></td>
                                      </tr>
                                      <tr> 
                                        <td height="8" align="right"><img src="/images/cp/log_line.gif"></td>
                                      </tr>
                                      <tr> 
                                        <td height="8" align="center"><a href="javascript:loginpage()"><img src="/images/cp/btn_confirm.gif" border="0" ></a></td>
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
