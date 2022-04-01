
<html>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" >
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.homepage.*" %>
<meta http-equiv="Pragma" content="no-cache" >
<link href="/css/user_style_k1.css" rel="stylesheet" type="text/css">

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>사이버문화콘텐츠아카데미 교육일정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/kocca.css" rel="stylesheet" type="text/css">
<link href="/css/sub.css" rel="stylesheet" type="text/css">

<style type="text/css">
<!--
td {
	font-family: "Dotum";
	font-size: 12px;
	line-height: 17px;
	color: #666666;

}

.01 {
	font-family: "Dotum";
	font-size: 11px;
	line-height: 17px;
	color: #666666;

}
-->
</style>

<script language="javascript">

function Go(juso){
opener.location=(juso);

}

</script>

<!-- 팝업창 띄우는 메소드 //-->
<script language="JavaScript" type="text/JavaScript">
	<!--
		function MM_openBrWindow(theURL,winName,features) { //v2.0
		window.open(theURL,winName,features);
	}
//-->
</script>

<SCRIPT LANGUAGE="JavaScript">
<!--
    // 커뮤니티 새창
    function CommunityOpenPage() {
        window.self.name = "winSelectViewCommunity";
        farwindow = window.open("", "openWinCommunity", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 920, height = 640, top=0, left=0");
        document.topdefaultForm.target = "openWinCommunity"
        document.topdefaultForm.action = "/servlet/controller.community.CommunityIndexServlet";
        document.topdefaultForm.submit();

        farwindow.window.focus();
        document.topdefaultForm.target = window.self.name;
    }    
//-->
</SCRIPT>

<SCRIPT LANGUAGE="JavaScript">
<!--
function menuForward(topmenu, leftmenu, orders){

    document.topdefaultForm.orders.value     = orders;
    document.topdefaultForm.topmenu.value    = topmenu;
    document.topdefaultForm.leftmenu.value   = leftmenu;
    document.topdefaultForm.p_process.value  = "writeLog";
    document.topdefaultForm.action = "http://contents.connect.or.kr/servlet/controller.system.MenuCountServlet";

    document.topdefaultForm.submit();
}
//-->
</SCRIPT>

<form name="topdefaultForm" method="post" action="javascript:return;">
  <input type='hidden' name='p_process'  value=''>
  <input type='hidden' name='topmenu'    value='0'>
  <input type='hidden' name='leftmenu'   value=''>
  <input type='hidden' name='orders'     value=''>
</form>

</head>

<body>

<center>
<table border=0 cellpadding=0 cellspacing=0>
	<tr>
		<td><img src=http://218.232.93.13/learn/user/kocca/edm/06_11_22/img/01.jpg border=0 usemap=#01></td>
	</tr>
	<tr>
		<td><img src=http://218.232.93.13/learn/user/kocca/edm/06_11_22/img/02.jpg border=0></td>
	</tr>
	<tr>
		<td><img src=http://218.232.93.13/learn/user/kocca/edm/06_11_22/img/03.jpg border=0 usemap=#03></td>
	</tr>
</table>
</center>

<map name="01">
	<area shape="rect" coords="0,0,144,57" href="http://contents.connect.or.kr" target="blank" onFocus="this.blur()">
	<area shape="rect" coords="603,367,700,388" href="http://contents.connect.or.kr/learn/user/kocca/homepage/pop_map02.jsp" target="blank" onFocus="this.blur()">
</map>

<map name="03">
	<area shape="rect" coords="450,22,574,43" href="mailto:cyber03@kocca.or.kr" target="blank" onFocus="this.blur()">
	<area shape="rect" coords="281,74,420,100" href="http://contents.connect.or.kr" onFocus="this.blur()" target="blank">
	<area shape="rect" coords="319,131,489,150" href="http://contents.connect.or.kr" onFocus="this.blur()" target="blank">
	<area shape="rect" coords="312,177,380,198" href="mailto:cyber03@kocca.or.kr" target="blank" onFocus="this.blur()">
</map>
</center>

</body>
 

