
<html>
<%@ page contentType = "text/html;charset=euc-kr" %>
<title>문화콘텐츠 경영분야</title>
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
	if (document.checkClose.kocca_open.checked ) 
		setCookie( "kocca_open", "done" , 1); // 쿠키값 체크
		self.close(); 
} 
// --> 
 
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0">

<table border="0" cellpadding="0" cellspacing="0">
  <tr>
	<td><img src="/learn/user/kocca/pop/img/open.jpg" border="0" usemap="#open"></td>
</tr>
  <form method="post" name="checkClose">
  <tr>
    <td height="36" align="right" background="/learn/user/kocca/pop/img/open02.jpg" style="padding-right:15">
		<font color="#FFFFFF">오늘 하루 이창을 열지 않음</font> <input type="Checkbox" name="kocca_open"  onClick="closeWin();" style="border:0">
  </tr>
  </form>
</table>

<map name="open">
	<area shape="rect" coords="136,492,225,506" href="mailto:cyber02@kocca.or.kr" onFocus="this.blur()">
</map>

</body>
 

