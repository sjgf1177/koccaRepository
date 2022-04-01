<%@ page contentType="text/html;charset=MS949" %>
<!------------------------------------------------------------------------------
 FILE NAME : INIcancel.html
 AUTHOR : jwkim@inicis.com
 DATE : 2002/12
 USE WITH : INIcancel.jsp

 지불 취소를 요청한다.

                                                          http://www.inicis.com
                                                      http://support.inicis.com
                                 Copyright 2002 Inicis, Co. All rights reserved
------------------------------------------------------------------------------->


<%
	String v_tid = request.getParameter("p_tid");
%>

<html>
<head>
	<title>INIpay</title>
	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
	<meta http-equiv="Cache-Control" content="no-cache"/>
	<meta http-equiv="Expires" content="0"/>
	<meta http-equiv="Pragma" content="no-cache"/>
	<style type="text/css">
		BODY{font-size:9pt; line-height:160%}
		TD{font-size:9pt; line-height:160%}
		A {color:blue;line-height:160%; background-color:#E0EFFE}
		INPUT{font-size:9pt;}
		SELECT{font-size:9pt;}
		.emp{background-color:#FDEAFE;}
	</style>
</head>

<body>
<form name=ini method=post action=INIcancel.jsp>

<table border=0 width=500>
	<tr>
	<td>
	<hr noshade size=1>
	<b>지불 취소</b>
	<hr noshade size=1>
	</td>
	</tr>


	<tr>
	<td>
	<br>전자결제 취소요청을 합니다.

	</td>
	</tr>
</table>
<br>

<table border=0 width=500>
<tr>
<td align=center>
<table width=400 cellspacing=0 cellpadding=0 border=0 bgcolor=#6699CC>
<tr>
<td>
<table width=100% cellspacing=1 cellpadding=2 border=0>
<tr bgcolor=#BBCCDD height=25>
<td align=center>
사유를 입력하신 후 확인버튼을 눌러주십시오
</td>
</tr>
<tr bgcolor=#FFFFFF>
<td valign=top>
<table width=100% cellspacing=0 cellpadding=2 border=0>
<tr>
<td align=center>
<br>
<table>

	<tr>
	<td>거래번호 : </td>
	<td><input type=hidden name=tid size=45 value="<%=v_tid%>"><%=v_tid%></td>
	</tr>

	<tr>
	<td>취소사유 : </td>
	<td><input type=text name=msg size=45 value=""></td>
	</tr>

	<tr>
	<td colspan=2 align=center>
	<br>
	<input type="submit" value=" 확 인 ">
	<br><br>
	</td>
	</tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
</table>
<br>

<table border=0 width=500>
	<tr>
	<td><hr noshade size=1></td>
	</tr>
</table>

<!--
상점아이디.
테스트를 마친 후, 발급받은 아이디로 바꾸어 주십시오.
-->

<input type=hidden name=mid value="kocca00001">

</form>
</body>
</html>
