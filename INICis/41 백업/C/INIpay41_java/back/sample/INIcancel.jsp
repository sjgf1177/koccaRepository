<%------------------------------------------------------------------------------
 FILE NAME : INIcancel.jsp
 AUTHOR : jwkim@inicis.com
 DATE : 2004/06
 USE WITH : INIcancel.html
 
 지불 취소 요청을 처리한다.
                                                          http://www.inicis.com
                                                      http://support.inicis.com
                                 Copyright 2002 Inicis, Co. All rights reserved.
------------------------------------------------------------------------------%>

<%@ page language="java" contentType="text/html; charset=euc-kr"
	import="com.inicis.inipay.*"%>

<%
	/**************************************
	 * 1. INIpay41 클래스의 인스턴스 생성 *
	 **************************************/
	INIpay41 inipay = new INIpay41();
	
	
	/*********************
	 * 2. 취소 정보 설정 *
	 *********************/
	inipay.setType("cancel"); // 고정
	inipay.setInipayHome("C:/INIpay41_JAVA"); // INIpay Home (절대경로로 적절히 수정)
	inipay.setSubPgIp("203.238.3.10"); // 고정
	inipay.setKeyPw("1111"); // 키패스워드(상점아이디에 따라 변경)
	inipay.setDebug("true"); // 로그모드("true"로 설정하면 자세한 로그가 생성됨)
	inipay.setMid(request.getParameter("mid")); // 상점아이디
	inipay.setTid(request.getParameter("tid")); // 취소할 거래의 거래아이디
	inipay.setCancelMsg(request.getParameter("msg")); // 취소사유
	inipay.setUip(request.getRemoteAddr()); // 고정
	inipay.setMerchantReserved("merchantreserved"); // 예비

	/*
	 * 이니시스 조회 페이지(https://iniweb.inicis.com)에서 보이는
	 * 한글이 깨지는 경우 아래 3줄 중 하나를 사용(주석해제)하여
	 * 한글이 제대로 보이는 것을 사용하십시오.
	 */
	//inipay.setEncoding(1);
	//inipay.setEncoding(2);
	//inipay.setEncoding(3);
	
	
	/****************
	 * 3. 취소 요청 *
	 ****************/
	inipay.startAction();
	
	
	/****************
	 * 4. 취소 결과 *
	 ****************/
	 String resultCode = inipay.getResultCode(); // "00"이면 취소 성공
	 String resultMsg = inipay.getResultMsg(); // 취소결과에 대한 설명
	 String pgCancelDate = inipay.getPgCancelDate(); // 취소날짜 (YYYYMMDD)
	 String pgCancelTime = inipay.getPgCancelTime(); // 취소시각 (HHMMSS)
	 String Rcash_cancel_noappl = inipay.getRcashCancelNoappl(); //현금영수증 취소 승인번호
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
		<b>지불 취소 결과</b>
		<hr noshade size=1>
		</td>
	</tr>
</table>
<br>
<table border=0 width=500>
	<tr>
		<td align=right nowrap>결과코드 :</td>
		<td><%=resultCode%></td>
	</tr>
	<tr>
		<td align=right nowrap>결과내용 :</td>
		<td><font class=emp><%=resultMsg%></font></td>
	</tr>
	<tr>
		<td align=right nowrap>취소날짜 :</td>
		<td><%=pgCancelDate%></td>
	</tr>
	<tr>
		<td align=right nowrap>취소시각 :</td>
		<td><%=pgCancelTime%></td>
	</tr>
	<tr>
		<td align=right nowrap>현금영수증 취소시 취소승인번호 :</td>
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
