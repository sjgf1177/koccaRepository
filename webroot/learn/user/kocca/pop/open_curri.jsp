<%@ page contentType = "text/html;charset=euc-kr" %>
<html>
<title>오프라인 열린강좌</title>
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
	if (document.checkClose.kocca_curri.checked ) 
		setCookie( "kocca_curri", "done" , 1); // 쿠키값 체크
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
    <td><img src="/learn/user/kocca/pop/img/curri01.jpg" border="0" usemap="#curri01"></td>
  </tr>
  <form method="post" name="checkClose">
  <tr align="right">
    <td height="25" style="padding:2 13 0 0" valign="top" background="/learn/user/kocca/pop/img/curri02.jpg">
		<input type="Checkbox" name="kocca_curri"  onClick="closeWin();" style="border:0">
	</td>
  </tr>
   </form>
</table>

<map name="curri01">
	<area shape="rect" coords="47,386,154,408" href="#" onClick="MM_openBrWindow('http://contents.connect.or.kr/learn/user/kocca/homepage/pop_map.jsp','','scrollbars=yes,width=680,height=720,left=0,top=0');" onFocus="this.blur();" onFocus="this.blur()">
	<area shape="rect" coords="139,439,259,468" href="javascript:opener.parent.location.href = '/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=131';window.close();" onFocus="this.blur()">
</map>

</body>
 

