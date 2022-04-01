
<html>
<%@ page contentType = "text/html;charset=euc-kr" %>
<title>문화콘텐츠 전문가과정</title>
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
	if (document.checkClose.kocca_special.checked ) 
		setCookie( "kocca_special", "done" , 1); // 쿠키값 체크
		self.close(); 
} 
// --> 
 
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0">

<table border="0" cellpadding="0" cellspacing="0">
  <tr>
	<td><img src="/learn/user/kocca/pop/img/special01.jpg" border="0" usemap="#open"></td>
</tr>
  <form method="post" name="checkClose">
  <tr>
    <td height="26" align="right" background="/learn/user/kocca/pop/img/special02.jpg" style="padding-right:174">
		<input type="Checkbox" name="kocca_special"  onClick="closeWin();" style="border:0">
  </tr>
  </form>
</table>

<map name="open">
	<area shape="rect" coords="132,419,265,433" href="javascript:opener.parent.location.href = 'http://contents.connect.or.kr/';window.close();" onFocus="this.blur()">	
	<area shape="rect" coords="195,474,299,492" href="javascript:opener.parent.location.href = 'http://contents.connect.or.kr/';window.close();" onFocus="this.blur()">
	<area shape="rect" coords="168,499,322,542" href="javascript:opener.parent.location.href = '/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=132';window.close();" onFocus="this.blur()">
</map>

</body>
 

