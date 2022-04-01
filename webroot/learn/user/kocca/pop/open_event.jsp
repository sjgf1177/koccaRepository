
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
	if (document.checkClose.kocca_event.checked ) 
		setCookie( "kocca_event", "done" , 1); // 쿠키값 체크
		self.close(); 
} 
// --> 
 
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0">

<table border="0" cellpadding="0" cellspacing="0">
  <tr>
    <td><img src="/learn/user/kocca/pop/img/open_event01.jpg" border="0" usemap="#open_event01"></td>
  </tr>
  <form method="post" name="checkClose">
  <tr height="20"valign="top" align="right">
    <td style="padding-right:5" valign="top" background="/learn/user/kocca/pop/img/open_event02.jpg">
		<input type="Checkbox" name="kocca_event"  onClick="closeWin();" style="border:0"> 
	</td>
  </tr>
   </form>
</table>

<map name="open_event01">
	<area shape="rect" coords="102,179,204,202" href="javascript:opener.parent.location.href = '/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=143';window.close();" onFocus="this.blur()">
</map>

</body>
 

