<%@ page contentType = "text/html;charset=euc-kr" %>
<html>
<title>문화콘텐츠 입문과정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" >
<meta http-equiv="Pragma" content="no-cache" >
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">
<SCRIPT language="JavaScript"> 
<!-- 
 
function setCookie( name, value, expiredays ) 
{ 
var todayDate = new Date(); 
todayDate.setDate( todayDate.getDate() + expiredays ); 
document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";" 
} 
 
function closeWin() 
{ 
	if (document.checkClose.kocca_invitation.checked ) 
		setCookie( "kocca_invitation", "done" , 1); // 쿠키값 체크
		self.close(); 
} 
// --> 
 
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0">

<table border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/learn/user/kocca/pop/img/invitation01.jpg" border="0" usemap="#invitation01"></td>
  </tr>
  <form method="post" name="checkClose">
  <tr height="23" align="right" valign="top">
    <td background="/learn/user/kocca/pop/img/invitation02.jpg" style="padding:0 15 0 0">
		<input type="Checkbox" name="kocca_invitation"  onClick="closeWin();" style="border:0">
	</td>
  </tr>
   </form>
</table>

<map name="invitation01">
	<area shape="rect" coords="213,119,267,135" href="javascript:opener.parent.location.href = '/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=129';window.close();" onFocus="this.blur()">
	<area shape="rect" coords="213,305,267,323" href="javascript:opener.parent.location.href = '/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=130';window.close();" onFocus="this.blur()">
</map>

</body>
 

