<%@ page contentType = "text/html;charset=euc-kr" %>
<html>
<title>수강생 모집</title>
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
	if (document.checkClose.kocca_work.checked ) 
		setCookie( "kocca_work", "done" , 1); // 쿠키값 체크
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
    <td><img src="/learn/user/kocca/pop/img/work01.jpg" border="0" usemap="#work01"></td>
  </tr>
  <form method="post" name="checkClose">
  <tr height="24" align="right">
    <td style="padding:0 10 0 0" bgcolor="#000000">
		<font color="FFFFFF">오늘 하루 이창을 열지 않음 <input type="Checkbox" name="kocca_work"  onClick="closeWin();" style="border:0">
	</td>
  </tr>
   </form>
</table>

<map name="work01">
	<area shape="rect" coords="152,336,284,353" href="#" href="javascript:opener.parent.location.href = 'http://contents.connect.or.kr';window.close();" onFocus="this.blur()">
	<area shape="rect" coords="157,500,280,526" href="javascript:opener.parent.location.href = '/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=124';window.close();" onFocus="this.blur()">
</map>

</body>
 

