<html>
<%@ page contentType = "text/html;charset=euc-kr" %>
<title>[특강] 2월 23일 열린강좌 안내</title>
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
	if (document.checkClose.kocca_open.checked ) 
		setCookie( "kocca_open", "done" , 1); // 쿠키값 체크
		self.close(); 
} 

</script>

</head>
<body leftmargin="0" topmargin="0">
<form name="frm" method="post">
<table width="400" border="0" cellspacing="0" cellpadding="0">
<tr>
<td><img src="img/080201.jpg"></td>
</tr>
<tr>
<td><img src="img/080202.gif" border="0" usemap="#Map"></td>
</tr>
<tr>
<td height="25" bgcolor="d4d4d3">
<div>
<div align="center" class="style1">

<label></label><div align="center" class="style1">
<input type="checkbox" onClick="eg_popup();" name="checkbox">
<span class="style3"><a href="javascript:close();">오늘 하루 이 창을 열지 않음</a></span> </div></td>
</tr>
</table>

<map name="Map">
    <area shape="rect" coords="32,85,146,104" href="http://contents.connect.or.kr/images/user/kocca/homepage/type2/map.gif" onFocus="this.blur()" target="_blank">
    <area shape="rect" coords="136,155,254,190" href="http://contents.connect.or.kr/servlet/controller.homepage.KHomeNoticeServlet?p_process=selectView&p_seq=327" onFocus="this.blur()" target="_blank">
</map>

</form>
</body>
</html>


