<%@ page contentType="text/html;charset=MS949" %>
<!------------------------------------------------------------------------------
 FILE NAME : INIcancel.html
 AUTHOR : jwkim@inicis.com
 DATE : 2002/12
 USE WITH : INIcancel.jsp

 ���� ��Ҹ� ��û�Ѵ�.

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
	<b>���� ���</b>
	<hr noshade size=1>
	</td>
	</tr>


	<tr>
	<td>
	<br>���ڰ��� ��ҿ�û�� �մϴ�.

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
������ �Է��Ͻ� �� Ȯ�ι�ư�� �����ֽʽÿ�
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
	<td>�ŷ���ȣ : </td>
	<td><input type=hidden name=tid size=45 value="<%=v_tid%>"><%=v_tid%></td>
	</tr>

	<tr>
	<td>��һ��� : </td>
	<td><input type=text name=msg size=45 value=""></td>
	</tr>

	<tr>
	<td colspan=2 align=center>
	<br>
	<input type="submit" value=" Ȯ �� ">
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
�������̵�.
�׽�Ʈ�� ��ģ ��, �߱޹��� ���̵�� �ٲپ� �ֽʽÿ�.
-->

<input type=hidden name=mid value="kocca00001">

</form>
</body>
</html>