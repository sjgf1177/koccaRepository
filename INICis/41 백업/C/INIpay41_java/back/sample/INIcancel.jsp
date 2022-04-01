<%------------------------------------------------------------------------------
 FILE NAME : INIcancel.jsp
 AUTHOR : jwkim@inicis.com
 DATE : 2004/06
 USE WITH : INIcancel.html
 
 ���� ��� ��û�� ó���Ѵ�.
                                                          http://www.inicis.com
                                                      http://support.inicis.com
                                 Copyright 2002 Inicis, Co. All rights reserved.
------------------------------------------------------------------------------%>

<%@ page language="java" contentType="text/html; charset=euc-kr"
	import="com.inicis.inipay.*"%>

<%
	/**************************************
	 * 1. INIpay41 Ŭ������ �ν��Ͻ� ���� *
	 **************************************/
	INIpay41 inipay = new INIpay41();
	
	
	/*********************
	 * 2. ��� ���� ���� *
	 *********************/
	inipay.setType("cancel"); // ����
	inipay.setInipayHome("C:/INIpay41_JAVA"); // INIpay Home (�����η� ������ ����)
	inipay.setSubPgIp("203.238.3.10"); // ����
	inipay.setKeyPw("1111"); // Ű�н�����(�������̵� ���� ����)
	inipay.setDebug("true"); // �α׸��("true"�� �����ϸ� �ڼ��� �αװ� ������)
	inipay.setMid(request.getParameter("mid")); // �������̵�
	inipay.setTid(request.getParameter("tid")); // ����� �ŷ��� �ŷ����̵�
	inipay.setCancelMsg(request.getParameter("msg")); // ��һ���
	inipay.setUip(request.getRemoteAddr()); // ����
	inipay.setMerchantReserved("merchantreserved"); // ����

	/*
	 * �̴Ͻý� ��ȸ ������(https://iniweb.inicis.com)���� ���̴�
	 * �ѱ��� ������ ��� �Ʒ� 3�� �� �ϳ��� ���(�ּ�����)�Ͽ�
	 * �ѱ��� ����� ���̴� ���� ����Ͻʽÿ�.
	 */
	//inipay.setEncoding(1);
	//inipay.setEncoding(2);
	//inipay.setEncoding(3);
	
	
	/****************
	 * 3. ��� ��û *
	 ****************/
	inipay.startAction();
	
	
	/****************
	 * 4. ��� ��� *
	 ****************/
	 String resultCode = inipay.getResultCode(); // "00"�̸� ��� ����
	 String resultMsg = inipay.getResultMsg(); // ��Ұ���� ���� ����
	 String pgCancelDate = inipay.getPgCancelDate(); // ��ҳ�¥ (YYYYMMDD)
	 String pgCancelTime = inipay.getPgCancelTime(); // ��ҽð� (HHMMSS)
	 String Rcash_cancel_noappl = inipay.getRcashCancelNoappl(); //���ݿ����� ��� ���ι�ȣ
%>

<html>
<head>

<title>INIpay</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<style type="text/css">
BODY {
	font-size: 9pt;
	line-height: 160%
}

TD {
	font-size: 9pt;
	line-height: 160%
}

INPUT {
	font-size: 9pt;
}

.emp {
	background-color: #E0EFFE;
}
</style>

</head>

<body>

<table border=0 width=500>
	<tr>
		<td>
		<hr noshade size=1>
		<b>���� ��� ���</b>
		<hr noshade size=1>
		</td>
	</tr>
</table>
<br>
<table border=0 width=500>
	<tr>
		<td align=right nowrap>����ڵ� :</td>
		<td><%=resultCode%></td>
	</tr>
	<tr>
		<td align=right nowrap>������� :</td>
		<td><font class=emp><%=resultMsg%></font></td>
	</tr>
	<tr>
		<td align=right nowrap>��ҳ�¥ :</td>
		<td><%=pgCancelDate%></td>
	</tr>
	<tr>
		<td align=right nowrap>��ҽð� :</td>
		<td><%=pgCancelTime%></td>
	</tr>
	<tr>
		<td align=right nowrap>���ݿ����� ��ҽ� ��ҽ��ι�ȣ :</td>
		<td><%=Rcash_cancel_noappl%></td>
	</tr>
	<tr>
		<td colspan=2><br>
		<hr noshade size=1>
		</td>
	</tr>

	<tr>
		<td align=right colspan=2>Copyright Inicis, Co.<br>
		www.inicis.com</td>
	</tr>
</table>
</body>
</html>
