<%@ page contentType = "text/html;charset=euc-kr" %>
<html>
<title>문화콘텐츠 Best 7</title>
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
	if (document.checkClose.kocca_best.checked ) 
		setCookie( "kocca_best", "done" , 1); // 쿠키값 체크
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

<table border="0" cellpadding="0" cellspacing="0" width="480">
	<tr>
		<td height="10" bgcolor="#F3F2F7"></td>
	</tr>
  <tr>
    <td align="center" bgcolor="#F3F2F7">
		<script language = "javascript" src = "/script/user_patch.js"></script>
		<script language='javascript'>
			setParam1('/learn/user/kocca/pop/img/best.swf');
			setParam2('461');
			setParam3('264');
			//alert("1111");
			object_patch('leftmenuB');
			setParam1('');
			setParam2('');
			setParam3('');
		</script>
	</td>
  </tr>
  <tr>
	<td><img src="/learn/user/kocca/pop/img/best01.jpg" border="0" usemap="#best01"></td>
  </tr>
  <form method="post" name="checkClose">
  <tr height="23" align="right">
    <td style="padding:0 10 0 0" bgcolor="070114">
		<input type="Checkbox" name="kocca_best"  onClick="closeWin();" style="border:0"> <font color="#FFFFFF">오늘 하루 이창을 열지 않음
	</td>
  </tr>
   </form>
</table>

<map name="best01">
	<area shape="rect" coords="223,297,287,318" href="javascript:opener.parent.location.href='/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=30';window.close();" onFocus="this.blur()">
</map>

</body>
 

