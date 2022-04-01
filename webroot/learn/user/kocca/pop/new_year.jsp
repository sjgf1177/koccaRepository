<%@ page contentType = "text/html;charset=euc-kr" %>
<html>
<title>문화콘텐츠</title>
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
	if (document.checkClose.kocca_year.checked ) 
		setCookie( "kocca_year", "done" , 1); // 쿠키값 체크
		self.close(); 
} 
// --> 
 
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0">

<table border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/learn/user/kocca/pop/img/year01.jpg" border="0"></td>
  </tr>
  <form method="post" name="checkClose">
  <tr height="23" align="right" valign="top">
    <td background="/learn/user/kocca/pop/img/year02.jpg" style="padding:2 5 0 0">
		<input type="Checkbox" name="kocca_year"  onClick="closeWin();" style="border:0">
	</td>
  </tr>
   </form>
</table>


</body>
 

