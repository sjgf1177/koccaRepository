<%@ page language="java" contentType="text/html; charset=euc-kr"
	import="com.inicis.inipay.*"%>

<% 
	/**************************************
	 * 1. INIpay41 클래스의 인스턴스 생성 *
	 **************************************/
	INIpay41 inipay = new INIpay41();
	
	
	 
	/*********************
	 * 2. 발급 정보 설정 *
	 *********************/
	inipay.setInipayHome("/usr/local/INIpay41"); 			// INIpay Home (절대경로로 적절히 수정)
	inipay.setKeyPw("1111"); 					// 키패스워드(상점아이디에 따라 변경)
	inipay.setType("receipt"); 					// 고정
	inipay.setPgId("INIpayRECP"); 					// 고정
	inipay.setPayMethod("CASH");					// 고정 (요청분류)
	inipay.setSubPgIp("203.238.3.10"); 				// 고정
	inipay.setCurrency(request.getParameter("currency"));		// 화폐단위 (고정)
	inipay.setDebug("true"); 					// 로그모드("true"로 설정하면 상세로그가 생성됨.)
	inipay.setMid(request.getParameter("mid")); 			// 상점아이디
	inipay.setGoodName(request.getParameter("goodname"));		// 상품명
	inipay.setcr_price(request.getParameter("cr_price"));		// 총 현금결제 금액
	inipay.setsup_price(request.getParameter("sup_price"));		// 공급가액
	inipay.settax(request.getParameter("tax"));			// 부가세
	inipay.setsrvc_price(request.getParameter("srvc_price"));	// 봉사료
	inipay.setBuyerName(request.getParameter("buyername"));		// 구매자 성명
	inipay.setBuyerEmail(request.getParameter("buyeremail"));	// 구매자 이메일 주소
	inipay.setBuyerTel(request.getParameter("buyertel"));		// 구매자 전화번호
	inipay.setreg_num(request.getParameter("reg_num"));		// 현금결제자 주민등록번호
	inipay.setuseopt(request.getParameter("useopt"));		// 현금영수증 발행용도 ("0" - 소비자 소득공제용, "1" - 사업자 지출증빙용)
	
	/*----------------------------------------------------------------------------------------*
         * 서브몰 사업자등록번호 *                                                                *
         *----------------------------------------------------------------------------------------*
         * 오픈마켓과 같이 서브몰이 존재하는 경우 반드시 서브몰 사업자등록번호를 입력해야합니다.  *
         * 서브몰 사업자등록번호를 입력하지 않고 현금영수증을 발급하는 경우 상점아이디에 해당하는 *
         * 현금영수증이 발급되어 서브몰 사업자로 현금영수증이 발급되지 않습니다.                  *
         * 상기 사항을 반드시 지켜주시기 바라며, 위 사항을 지키지 않아 발생된 문제에 대해서는     *
         * (주)이니시스에 책임이 없음을 유의하시기 바랍니다.                                      *
         *                                                                                        *
         * 서브몰 현금영수증을 발급하시려면 아래 필드 주석을 제거 하시고 사용하시기 바랍니다.     *
         *----------------------------------------------------------------------------------------*/

         // inipay.setCompanyNumber("222333444");              // 서브몰 사업자 등록번호
	
	
	/*
	 * 이니시스 조회 페이지(https://iniweb.inicis.com)에서 보이는
	 * 한글이 깨지는 경우 아래 3줄 중 하나를 사용(주석해제)하여 
	 * 한글이 제대로 보이는 것을 사용하십시오.
	 */
	
	//inipay.setEncoding(1);
	//inipay.setEncoding(2);
	//inipay.setEncoding(3);

	
	/****************
	 * 3. 지불 요청 *
	 ****************/
	inipay.startAction();

	
	
	/*****************************************************************
	 * 4. 발급 결과                           	                 *
	 ****************************************************************/
	 
	 String tid = inipay.getTid(); 			 // 거래번호
	 String rcash_rslt = inipay.getrcash_rslt();	 // 현금영수증 발행 결과코드 (4자리 - "0000"이면 발급성공)
	 String resultMsg = inipay.getResultMsg(); 	 // 결과내용 (발행결과에 대한 설명)
	 String payMethod = inipay.getPayMethod(); 	 // 지불방법 (매뉴얼 참조)
	 String rcash_noappl = inipay.getrcash_noappl(); // 현금영수증 발행 승인 번호
	 String pgAuthDate = inipay.getPgAuthDate(); 	 // 이니시스 승인날짜 (YYYYMMDD)
	 String pgAuthTime = inipay.getPgAuthTime(); 	 // 이니시스 승인시각 (HHMMSS)
	 String rcr_price = inipay.getrcr_price();	 // 총현금결제 금액
	 String rsup_price = inipay.getrsup_price();	 // 공급가
	 String rtax = inipay.getrtax();		 // 부가세
	 String rsrvc_price = inipay.getrsrvc_price();	 // 봉사료
	 String ruseopt = inipay.getruseopt();		 // 현금영수증 사용구분
	 
	 
	 
%>

<html>
<head>
<title>INIpay41 현금영수증 발행 데모</title>
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
<script>
	var openwin=window.open("childwin.html","childwin","width=299,height=149");
	openwin.close();
	
function showreceipt() // 현금 영수증 출력
{
	var showreceiptUrl = "https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/Cash_mCmReceipt.jsp?noTid=<%=tid%>" + "&clpaymethod=22";
	window.open(showreceiptUrl,"showreceipt","width=380,height=540, scrollbars=no,resizable=no");
}

	
	
</script>

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
    					
    				if(rcash_rslt.equals("0000")){
					out.println("img/cash_top.gif");
				}
				else{
					out.println("img/spool_top.gif");
				}
				
				%>"
			style="padding: 0 0 0 64">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="3%" valign="top"><img src="img/title_01.gif"
					width="8" height="27" vspace="5"></td>
				<td width="97%" height="40" class="pl_03"><font color="#FFFFFF"><b>현금결제
				영수증 발급결과</b></font></td>
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
							width="12" height="10"> <b>고객님께서 요청하신 현금영수증 발급 내용입니다. </b></td>
						<td width="8"><img src="img/right.gif" width="8" height="30"></td>
					</tr>
				</table>
				<br>
				<table width="510" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="407" style="padding: 0 0 0 9"><img
							src="img/icon.gif" width="10" height="11"> <strong><font
							color="433F37">발급내역</font></strong></td>
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
										<td><%=rcash_rslt%></td>
										<td width='142' align='right'><a
											href='javascript:showreceipt();'><img
											src='img/button_02.gif' width='94' height='24' border='0'></a></td>
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
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 번 호</td>
								<td width="343"><%=rcash_noappl%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width='18' align='center'><img src='img/icon02.gif'
									width='7' height='7'></td>
								<td width='109' height='25'>승 인 날 짜</td>
								<td width='343'><%=pgAuthDate%></td>
							</tr>

							<tr>
								<td height='1' colspan='3' align='center'
									background='img/line.gif'></td>
							</tr>
							<tr>
								<td width='18' align='center'><img src='img/icon02.gif'
									width='7' height='7'></td>
								<td width='109' height='25'>승 인 시 각</td>
								<td width='343'><%=pgAuthTime%></td>
							</tr>
							<tr>
								<td height='1' colspan='3' align='center'
									background='img/line.gif'></td>
							</tr>
							<tr>
								<td width='18' align='center'><img src='img/icon02.gif'
									width='7' height='7'></td>
								<td width='109' height='25'>총 현금결제금액</td>
								<td width='343'><%=rcr_price%> 원</td>
							</tr>
							<tr>
								<td height='1' colspan='3' align='center'
									background='img/line.gif'></td>
							</tr>
							<tr>
								<td width='18' align='center'><img src='img/icon02.gif'
									width='7' height='7'></td>
								<td width='109' height='25'>공 급 가 액</td>
								<td width='343'><%=rsup_price%> 원</td>
							</tr>
							<tr>
								<td height='1' colspan='3' align='center'
									background='img/line.gif'></td>
							</tr>
							<tr>
								<td width='18' align='center'><img src='img/icon02.gif'
									width='7' height='7'></td>
								<td width='109' height='25'>부 가 세</td>
								<td width='343'><%=rtax%> 원</td>
							</tr>
							<tr>
								<td height='1' colspan='3' align='center'
									background='img/line.gif'></td>
							</tr>
							<tr>
								<td width='18' align='center'><img src='img/icon02.gif'
									width='7' height='7'></td>
								<td width='109' height='25'>봉 사 료</td>
								<td width='343'><%=rsrvc_price%> 원</td>
							</tr>
							<tr>
								<td height='1' colspan='3' align='center'
									background='img/line.gif'></td>
							</tr>
							<tr>
								<td width='18' align='center'><img src='img/icon02.gif'
									width='7' height='7'></td>
								<td width='109' height='25'>사 용 용 도</td>
								<td width='343'>
								<% 
                      			if(ruseopt.equals("0"))
                      			{
                      				out.println("소득공제용");
                      			}else{
                      				out.println("지출증빙용");
                      			}
                      		      %>
								</td>
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
