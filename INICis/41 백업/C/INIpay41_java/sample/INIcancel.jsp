<%------------------------------------------------------------------------------
 FILE NAME : INIcancel.jsp
 AUTHOR : jwkim@inicis.com
 DATE : 2002/12
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
	inipay.setInipayHome("/usr/local/INIpay41"); // INIpay Home (절대경로로 적절히 수정)
	inipay.setKeyPw("1111"); // 키패스워드(상점아이디에 따라 변경)
	inipay.setType("cancel"); // 고정
	inipay.setSubPgIp("203.238.3.10"); // 고정
	inipay.setDebug("ture"); // 로그모드("true"로 설정하면 상세한 로그가 생성됨)
	inipay.setMid(request.getParameter("mid")); // 상점아이디
	inipay.setTid(request.getParameter("tid")); // 취소할 거래의 거래아이디
	inipay.setCancelMsg(request.getParameter("msg")); // 취소사유
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
	 String resultCode = inipay.getResultCode(); 				// "00"이면 취소 성공
	 String resultMsg = inipay.getResultMsg(); 				// 취소결과에 대한 설명
	 String pgCancelDate = inipay.getPgCancelDate(); 			// 취소날짜 (YYYYMMDD)
	 String pgCancelTime = inipay.getPgCancelTime(); 			// 취소시각 (HHMMSS)
	 String rcash_cancel_noappl = inipay.getrcash_cancel_noappl();		// 현금영수증 취소 승인번호 (9자리)
	 String tid = request.getParameter("tid");
%>

<html>
<head>
<title>INIpay41 취소페이지 데모</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/group.css" type="text/css">
<style>
body,tr,td {
	font-size: 9pt;
	font-family: 굴림, verdana;
	color: #433F37;
	line-height: 19px;
}

table,img {
	border: none
}

/* Padding ******/
.pl_01 {
	padding: 1 10 0 10;
	line-height: 19px;
}

.pl_03 {
	font-size: 20pt;
	font-family: 굴림, verdana;
	color: #FFFFFF;
	line-height: 29px;
}

/* Link ******/
.a:link {
	font-size: 9pt;
	color: #333333;
	text-decoration: none
}

.a:visited {
	font-size: 9pt;
	color: #333333;
	text-decoration: none
}

.a:hover {
	font-size: 9pt;
	color: #0174CD;
	text-decoration: underline
}

.txt_03a:link {
	font-size: 8pt;
	line-height: 18px;
	color: #333333;
	text-decoration: none
}

.txt_03a:visited {
	font-size: 8pt;
	line-height: 18px;
	color: #333333;
	text-decoration: none
}

.txt_03a:hover {
	font-size: 8pt;
	line-height: 18px;
	color: #EC5900;
	text-decoration: underline
}
</style>

<script language="JavaScript" type="text/JavaScript">
<!--
function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}
MM_reloadPage(true);
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#242424" leftmargin=0 topmargin=15
	marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0>
<center>
<table width="632" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td height="83"
			background="<% 
    					// 지불수단에 따라 상단 이미지가 변경 된다
    					
    				if(resultCode.equals("01")){
					out.println("img/spool_top.gif");
				}
				else{
					out.println("img/cancle_top.gif");
				}
				
				%>"
			style="padding: 0 0 0 64">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="3%" valign="top"><img src="img/title_01.gif"
					width="8" height="27" vspace="5"></td>
				<td width="97%" height="40" class="pl_03"><font color="#FFFFFF"><b>취소결과</b></font></td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td align="center" bgcolor="6095BC">
		<table width="620" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td bgcolor="#FFFFFF" style="padding: 0 0 0 56">
				<table width="510" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="7"><img src="img/life.gif" width="7" height="30"></td>
						<td background="img/center.gif"><img src="img/icon03.gif"
							width="12" height="10"> <b>고객님께서 이니페이를 통해 취소하신 내용입니다. </b></td>
						<td width="8"><img src="img/right.gif" width="8" height="30"></td>
					</tr>
				</table>
				<br>
				<table width="510" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="407" style="padding: 0 0 0 9"><img
							src="img/icon.gif" width="10" height="11"> <strong><font
							color="433F37">취소내역</font></strong></td>
						<td width="103">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2" style="padding: 0 0 0 23">
						<table width="470" border="0" cellspacing="0" cellpadding="0">

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="26">결 과 코 드</td>
								<td width="343">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td><%=resultCode%></td>
										<td width='142' align='right'>&nbsp;</td>
									</tr>
								</table>
								</td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">결 과 내 용</td>
								<td width="343"><%=resultMsg%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">거 래 번 호</td>
								<td width="343"><%=tid%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width='18' align='center'><img src='img/icon02.gif'
									width='7' height='7'></td>
								<td width='109' height='25'>취 소 날 짜</td>
								<td width='343'><%=pgCancelDate%></td>
							</tr>

							<tr>
								<td height='1' colspan='3' align='center'
									background='img/line.gif'></td>
							</tr>
							<tr>
								<td width='18' align='center'><img src='img/icon02.gif'
									width='7' height='7'></td>
								<td width='109' height='25'>취 소 시 각</td>
								<td width='343'><%=pgCancelTime%></td>
							</tr>
							<tr>
								<td height='1' colspan='3' align='center'
									background='img/line.gif'></td>
							</tr>
							<tr>
								<td width='18' align='center'><img src='img/icon02.gif'
									width='7' height='7'></td>
								<td width='109' height='25'>현금영수증<br>
								취소승인번호</td>
								<td width='343'><%=rcash_cancel_noappl%></td>
							</tr>
							<tr>
								<td height='1' colspan='3' align='center'
									background='img/line.gif'></td>
							</tr>


						</table>
						</td>
					</tr>
				</table>
				<br>
				</td>
			</tr>
		</table>
		</td>
	</tr>
	<tr>
		<td><img src="img/bottom01.gif" width="632" height="13"></td>
	</tr>
</table>
</center>
</body>
</html>
