<html>
<%@ page contentType = "text/html;charset=euc-kr" %>
<title>[워크숍] 8기 수강모집 안내</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" >
<meta http-equiv="Pragma" content="no-cache" >
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">
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
<td><img src="img/workshop_img.jpg"></td>
</tr>
<tr>
<td><img src="img/workshop_txt.gif" border="0" usemap="#Map"></td>
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
    
    <area shape="rect" coords="250,429,374,465" href="http://contents.connect.or.kr/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=332" onFocus="this.blur()" target="_blank" />
    <area shape="rect" coords="145,221,286,236" href="http://124.111.37.56/notice/kocca/kocca01.html" onFocus="this.blur()" target="_blank" />
</map>
</form>
</body>
</html>