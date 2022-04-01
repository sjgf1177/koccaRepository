<%
    String topmenu  = "0";
%>
<HTML>
<HEAD>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" >
<meta http-equiv="Pragma" content="no-cache" >
<TITLE> :::: 우송대학교 게임멀티미디어학과 :::: </TITLE>
<link rel="stylesheet" type="text/css" href="/css/user_style1.css" >
<link rel="stylesheet" type="text/css" href="/css/user_style_new.css" >
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="VBScript" src="/script/cresys_lib.vbs"></script>
<script language = "javascript" src = "/script/user_patch.js"></script>
</HEAD>

<BODY <%=onLoad%>>
<!----------------- 공통폼 / 메뉴이동스크립트 시작 ----------------->
<%@ include file="/learn/user/game/include/common.jsp"%>
<!----------------- 공통폼 / 메뉴이동스크립트 끝 ------------------>
<div id="topmenu">
<%@ include file="/learn/user/game/include/menuflashA.jsp"%>
</div>


<table cellpadding="0" cellspacing="0" width="100%" height="100%">
<tr valign="top" height="64">
<td>
	<!-- TOP --><!--height 97에서 64로 수정-->
	<table cellpadding="0" cellspacing="0" width="100%" height="64" background="<%=tem_imgpath%><%=tem_topbg%>" style="table-layout:fixed;" border="0">
	<COLGROUP>
        <COL width=1>
        <COL width=999><!--070703수정-->
        <COL>
	<tr>
	<td width="22%" rowspan="2" valign="top"><!-- Logo -->
		<% String v_indexURL = "";
		   String s_tem_grcode = box.getSession("tem_grcode");
		   if (s_tem_grcode.equals("N000002")) {
		   	v_indexURL = "/indexGame.jsp";
		   } else {
		   	v_indexURL = "/index" + s_tem_grcode + ".jsp";
		   }
		%>
		<a href="<%=v_indexURL%>"><img src="<%=tem_imgpath%><%=tem_toplogo%>"></a><br>
	</td><!--padding-right:95px;-->
	<td width="77%" height=31 valign="top" style="text-align:right;padding-right:5px;"><!-- top menu -->
    <!----------------- global menu 시작 ----------------->
    <%@ include file="/learn/user/game/include/globalmenuA.jsp"%>
    <!-----------------  global menu 끝 ------------------>
	</td>
	<td rowspan="2"></td>
	</tr>
	<!--기존 menu flash 자리-->
	</table>
	<!-- // TOP -->
</td>
</tr>

<tr valign="top">
<td background="<%=tem_imgpath%><%=tem_mainbg%>" bgcolor="#f6f6f6">

	<!-- KEY VISUAL FLASH -->
	<table border=0 cellpadding="0" cellspacing="0" width="1018" height="245">
	<tr>
	<td><img src="/images/user/game/homepage/type1/N000012_main_img01.jpg" border="0"></td>
	<td><img src="/images/user/game/homepage/type1/N000012_main_img02.jpg" border="0"></td>
	</tr>
	</table>
	<!-- //KEY VISUAL FLASH -->

	<!-- CONTENTS -->
	<TABLE width=1000 height="100%" cellPadding=0 cellSpacing=0 >
        <COLGROUP>
        <COL width=212>
        <COL width=564>
        <COL width=224>
        <TR vAlign=top>
              <TD width="212" align=center>