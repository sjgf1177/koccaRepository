<html>
<%@ page contentType = "text/html;charset=euc-kr" %>
<title>문화콘텐츠 12월 과정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" >
<meta http-equiv="Pragma" content="no-cache" >
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">
<style type="text/css">
<!--
.style1 {color: #FFFFFF}
.style3 {font-size: 12px}
-->
</style>
<SCRIPT language="JavaScript"> 
<!-- 
// 체크후 닫기버튼을 눌렀을때 쿠키를 만들고 창을 닫습니다

function eg_popup()
{
	var todayDate = new Date();
	todayDate.setDate( todayDate.getDate() + 1 );
	document.cookie = "MainPopup=done; path=/; expires=" + todayDate.toGMTString() + ";" 
	self.close();
}

function setCookie( name, value, expiredays ) 
{ 
var todayDate = new Date(); 
todayDate.setDate( todayDate.getDate() + expiredays ); 
document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";" 
} 
 
function closeWin() 
{ 
	if(document.frm.checkvalue.checked)
	{
		setCookie( "kocca_open", "done" , 1); // 쿠키값 체크
		self.close();
	}
}
</SCRIPT>
</head>

<body leftmargin="0" topmargin="0">
<form name="frm" method="post">
<table width="400" border="0" cellspacing="0" cellpadding="0">

<tr>
<td><a href="http://contents.connect.or.kr/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=244" target="_blank"> <img src="img/6.gif" width="400" height="653" border="0" usemap="#Map2"></a></td>
</tr>
<tr>
<td height="25" bgcolor="d4d4d3">
<div>
<div align="center" class="style1">

<label></label><div align="center" class="style1">
<input type="checkbox" name="checkvalue" value="Y" onclick="closeWin();">
<span class="style3"><a href="javascript:closeWin();">오늘 하루 이 창을 열지 않음</a></span> </div></td>
</tr>
</table>
<map name="Map">
<area shape="rect" coords="64,57,176,74" href="http://contents.connect.or.kr/images/user/kocca/homepage/type2/map.gif" target="_blank">
<area shape="rect" coords="135,222,258,255" href="http://contents.connect.or.kr/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=254" target="_blank">
</map>
</form>
<map name="Map2">
  <area shape="rect" coords="238,615,345,645" href="http://contents.connect.or.kr/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=253" target="_blank">
</map>
</body>
</html>