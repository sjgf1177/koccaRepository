<%@ page contentType = "text/html;charset=euc-kr" %>
<html>
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
	if (document.checkClose.kocca_poll.checked ) 
		setCookie( "kocca_poll", "done" , 1); // 쿠키값 체크
		self.close(); 
} 
// --> 
 
</SCRIPT>

<!-- 팝업창 띄우는 메소드 //-->
<script language="JavaScript" type="text/JavaScript">
	<!--
		function MM_openBrWindow(theURL,winName,features) { //v2.0
		window.open(theURL,winName,features);
	}
//-->
</script>
</head>

<body leftmargin="0" topmargin="0">

<table border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/learn/user/kocca/pop/img/poll01.jpg" border="0" usemap="#poll01"></td>
  </tr>
  <form method="post" name="checkClose">
  <tr height="29"valign="top" bgcolor="000000" align="right">
    <td style="padding-right:10">
		<input type="Checkbox" name="kocca_poll"  onClick="closeWin();" style="border:0"> <font color="#FFFFFF">오늘 하루 팝업창 그만보기</font>
	</td>
  </tr>
   </form>
</table>

<map name="poll01">
	<area shape="rect" coords="59,263,176,380" href="#" onClick="MM_openBrWindow('http://nmanager.cybermba.com/poll6.asp','','scrollbars=yes,width=818,height=800,left=0,top=0');return false" onFocus="this.blur();">
</map>

</body>
 

