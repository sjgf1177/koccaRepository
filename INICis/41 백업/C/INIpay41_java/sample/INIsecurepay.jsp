<%------------------------------------------------------------------------------
 FILE NAME : INIsecurepay.jsp
 AUTHOR : ts@inicis.com
 DATE : 2006/04
 USE WITH : INIsecurepay.html
 
 이니페이 플러그인을 통해 요청된 지불을 처리한다.
                                                          http://www.inicis.com
                                                      http://support.inicis.com
                            Copyright 2006 Inicis Co., Ltd. All rights reserved.
------------------------------------------------------------------------------%>


<%@ page language="java" contentType="text/html; charset=euc-kr"
	import="com.inicis.inipay.*"%>

<% 
	/**************************************
	 * 1. INIpay41 클래스의 인스턴스 생성 *
	 **************************************/
	INIpay41 inipay = new INIpay41();
	
	/*********************
	 * 2. 지불 정보 설정 *
	 *********************/
	 
	 /************************************************************************************
	 * 하단의 PGID 부분은 지불수단별로 TID를 별도로 표시하도록 하며,   *
	 * 임의로 수정하여 발생된 문제에 대해서는 (주)이니시스에 책임이      *
	 * 없으니 소스 수정시 주의를 바랍니다					        	*
	 ***********************************************************************************/
			
	String pgid = "";
	String paymethod = request.getParameter("paymethod");
	if(paymethod.equals("Card")){
		pgid = "CARD"; //신용카드
	}
	else if(paymethod.equals("Account")){
		pgid = "ACCT"; // 은행 계좌 이체
	}
	else if(paymethod.equals("DirectBank")){
		pgid = "DBNK"; // 실시간 계좌 이체
	}
	else if(paymethod.equals("OCBPoint")){
		pgid = "OCBP"; // OCB
	}
	else if(paymethod.equals("VCard")){
		pgid = "ISP_"; // ISP 결제
	}
	else if(paymethod.equals("HPP")){
		pgid = "HPP_"; // 휴대폰 결제
	}
	else if(paymethod.equals("Nemo")){
		pgid = "NEMO"; // NEMO 결제
	}
	else if(paymethod.equals("ArsBill")){
		pgid = "ARSB"; // 700 전화결제
	}
	else if(paymethod.equals("PhoneBill")){
		pgid = "PHNB"; // PhoneBill 결제(받는 전화)
	}
	else if(paymethod.equals("Ars1588Bill")){
		pgid = "1588"; // 1588 전화결제
	}
	else if(paymethod.equals("VBank")){
		pgid = "VBNK"; // 가상계좌 이체
	}
	else if(paymethod.equals("Culture")){
		pgid = "CULT"; // 문화상품권 결제
	}
	else if(paymethod.equals("CMS")){
		pgid = "CMS_"; // CMS 결제
	}
	else if(paymethod.equals("AUTH")){
		pgid = "AUTH"; // 신용카드 유효성 검사
	}
	else if(paymethod.equals("INIcard")){
		pgid = "INIC"; // 네티머니 결제
	}
	else if(paymethod.equals("MDX")){
		pgid = "MDX_"; // 몬덱스카드
	}
	else{
		pgid = paymethod;
	}	
	
	inipay.setInipayHome("/usr/local/INIpay41"); // INIpay Home (절대경로로 적절히 수정)
	inipay.setKeyPw("1111"); // 키패스워드(상점아이디에 따라 변경)
	inipay.setType("securepay"); // 고정
	inipay.setPgId("INIpay"+pgid); // 고정
	inipay.setSubPgIp("203.238.3.10"); // 고정
	inipay.setDebug("true"); // 로그모드("true"로 설정하면 상세한 로그가 생성됨)
	inipay.setMid(request.getParameter("mid")); // 상점아이디
	inipay.setUid(request.getParameter("uid")); // 고정(임의 수정 불가)
	inipay.setGoodName(request.getParameter("goodname"));
	inipay.setCurrency(request.getParameter("currency"));

     /*----------------------------------------------------------------------------------------
       price 등의 중요데이터는 
       브라우저상의 위변조여부를 반드시 확인하셔야 합니다.
      
       결제 요청페이지에서 요청된 금액과
       실제 결제가 이루어질 금액을 반드시 비교하여 처리하십시오.
      
       설치 메뉴얼 2장의 결제 처리페이지 작성부분의 보안경고 부분을 확인하시기 바랍니다.
	   적용참조문서: 이니시스홈페이지->가맹점기술지원자료실->기타자료실 의 
					  '결제 처리 페이지 상에 결제 금액 변조 유무에 대한 체크' 문서를 참조하시기 바랍니다.   
	   예제)
	   원 상품 가격 변수를 OriginalPrice 하고  원 가격 정보를 리턴하는 함수를 Return_OrgPrice()라 가정하면 
       다음 같이 적용하여 원가격과 웹브라우저에서 Post되어 넘어온 가격을 비교 한다. 
 
		
	    String OriginalPrice = Return_OrgPrice();
	    String PostPrice = request.getParameter("price"); 
	    if ( !OriginalPrice.equals(PostPrice) )
	    {
			//결제 진행을 중단하고  금액 변경 가능성에 대한 메시지 출력 처리
			//처리 종료 
		}
      ----------------------------------------------------------------------------------------*/
	inipay.setPrice(request.getParameter("price"));


	inipay.setBuyerName(request.getParameter("buyername"));
	inipay.setBuyerTel(request.getParameter("buyertel"));
	inipay.setBuyerEmail(request.getParameter("buyeremail"));
	inipay.setParentEmail(request.getParameter("parentemail")); // 보호자 이메일 주소(핸드폰 , 전화결제시에 14세 미만의 고객이 결제하면  부모 이메일로 결제 내용통보 의무, 다른결제 수단 사용시에 삭제 가능)
	inipay.setPayMethod(request.getParameter("paymethod"));
	inipay.setEncrypted(request.getParameter("encrypted"));
	inipay.setSessionKey(request.getParameter("sessionkey"));
	inipay.setUrl("http://www.your_domain.co.kr"); // 실제 서비스되는 상점 SITE URL로 변경할것
	inipay.setCardCode(request.getParameter("cardcode")); // 플러그인에서 리턴되는 카드코드
	/*-----------------------------------------------------------------*
	 * 수취인 정보 *                                                   *
	 *-----------------------------------------------------------------*
	 * 실물배송을 하는 상점의 경우에 사용되는 필드들이며               *
	 * 아래의 값들은 INIsecurepay.html 페이지에서 포스트 되도록        *
	 * 필드를 만들어 주도록 하십시요.                                  *
	 * 컨텐츠 제공업체의 경우 삭제하셔도 무방합니다.                   *
	 *-----------------------------------------------------------------*/
	inipay.setRecvName(request.getParameter("recvname")); 	// 수취인 명
	inipay.setRecvTel(request.getParameter("recvtel"));	// 수취인 연락처
	inipay.setRecvAddr(request.getParameter("recvaddr"));	// 수취인 주소
	inipay.setRecvPostNum(request.getParameter("recvpostnum"));	// 수취인 우편번호
	inipay.setRecvMsg(request.getParameter("recvmsg"));	// 수취인 전달메세지
	
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
	
	
	/****************
	 * 4. 지불 결과 *
	 ****************/
	 
	 String tid = inipay.getTid(); 					// 거래번호
	 String resultCode = inipay.getResultCode(); 			// 결과코드 ("00"이면 지불 성공)
	 String resultMsg = inipay.getResultMsg(); 			// 결과내용 (지불결과에 대한 설명)
	 String payMethod = inipay.getPayMethod(); 			// 지불방법 (매뉴얼 참조)

     /*------------------------------------------------------------------------------------------------
     * 결제 되는 금액 =>원상품가격과  결제결과금액과 비교하여 금액이 동일하지 않다면  
     * 결제 금액의 위변조가 의심됨으로 정상적인 처리가 되지않도록 처리 바랍니다. (해당 거래 취소 처리) 
     --------------------------------------------------------------------------------------------------*/
	 String resultprice = inipay.getresultprice(); 			// 결제 결과 금액

	 String price1 = inipay.getPrice1(); 				// OK Cashbag 복합결재시 신용카드 지불금액
	 String price2 = inipay.getPrice2(); 				// OK Cashbag 복합결재시 포인트 지불금액
	 String authCode = inipay.getAuthCode(); 			// 신용카드 승인번호
	 String cardQuota = inipay.getCardQuota(); 			// 할부기간
	 String cardCode = inipay.getCardCode(); 			// 신용카드사 코드 (매뉴얼 참조)
	 String cardIssuerCode = inipay.getCardIssuerCode(); 		// 카드발급사 코드 (매뉴얼 참조)
	 String authCertain = inipay.getAuthCertain(); 			// 본인인증 수행여부 ("00"이면 수행)
	 String pgAuthDate = inipay.getPgAuthDate(); 			// 이니시스 승인날짜
	 String pgAuthTime = inipay.getPgAuthTime(); 			// 이니시스 승인시각
	 String ocbSaveAuthCode = inipay.getOcbSaveAuthCode(); 		// OK Cashbag 적립 승인번호
	 String ocbUseAuthCode = inipay.getOcbUseAuthCode(); 		// OK Cashbag 사용 승인번호
	 String ocbAuthDate = inipay.getOcbAuthDate(); 			// OK Cashbag 승인일시
	 String eventFlag = inipay.getEventFlag(); 			// 각종 이벤트 적용 여부
	 String nohpp = inipay.getNoHpp(); 				// 휴대폰 결제시 사용된 휴대폰 번호
	 String noars = inipay.getNoArs(); 				// 전화결제 시 사용된 전화번호  
	 String perno = inipay.getPerno(); 				// 송금자 주민번호
	 String vacct = inipay.getVacct(); 				// 가상계좌번호
	 String vcdbank = inipay.getVcdbank(); 				// 입금할 은행코드
	 String dtinput = inipay.getDtinput(); 				// 입금예정일
  	 String tminput = inipay.getResultString("tminput"); 				// 입금예정시간
	 String nminput = inipay.getNminput(); 				// 송금자 명
	 String nmvacct = inipay.getNmvacct(); 				// 예금주 명
	 String moid = inipay.getmoid(); 				// 상점 주문번호
	 String codegw = inipay.getcodegw(); 				// 전화결제 사업자 코드
	 String ocbcardnumber = inipay.getocbcardnumber(); 		// OK CASH BAG 결제 , 적립인 경우 OK CASH BAG 카드 번호
	 String cultureid = inipay.getcultureid(); 			// 컬쳐 랜드 ID (K-merce ID, 틴캐시 아이디 공통사용)
	 String cardNumber = inipay.getCardNumber(); 			// 신용카드번호  	
	 String directbankcode = inipay.getdirectbankcode(); 		// 실시간 계좌이체 은행코드 
	 String rcash_rslt = inipay.getrcash_rslt();	 		// 현금영수증 발행 결과코드 (4자리 - "0000"이면 발급성공)
	 String ruseopt = inipay.getruseopt();				// 현금영수증 발급 구분코드 ("0"-소득공제용, "1"-지출증빙용)
	 String remain_price = inipay.getremain_price();		// 틴캐시 잔액
	 String sktg_method = inipay.getResultString("sktg_method");	// SKT 문화상품권 결제방법 (C - 카드형, M - 모바일형)
     String quotaInterest = request.getParameter("quotainterest");  // 무이자할부 여부 ("1"이면 무이자할부)
     String ReqCurrency = inipay.getReqCurrency();			// 화폐단위 (달러결제시 리턴)
	 String RateExchange = inipay.getRateExchange();		// 환률정보 (달러결제시 리턴)
	 String dgcl_cardcount = inipay.getdgcl_cardcount();		// 사용한 카드수 (게임상품권, SKT 상품권)
	 
    /*--------------------------------------------------------------*
	 * 무이자 할부 거래일 경우 할부개월 뒤에 무이자 여부를 표시한다.*
	 *--------------------------------------------------------------*/
	 String interest = "";
	 
	 if(quotaInterest.equals("1")){
	 	interest = "(무이자 할부)";
	 }
	 /*--------------------------------------------------------------*/


	 String mid = request.getParameter("mid");
	 String goodname = request.getParameter("goodname");
	 
     String price = request.getParameter("price");
    
	 String buyername = request.getParameter("buyername");
	 String buyertel = request.getParameter("buyertel");
	 String buyeremail = request.getParameter("buyeremail");
	 
	 
     /*----------------------------------------------------------------------------------------------------------*
	 * 에러발생시 결과 메세지에서 에러코드를 추출하는 부분으로 절대 수정 금지 (JDK 1.4.X 이하버전에서 사용불가) *
	 *----------------------------------------------------------------------------------------------------------*/
	 String tmp_ErrCode[] = resultMsg.split("]");
	 String resulterrCode = resultMsg.substring(1,tmp_ErrCode[0].length()); // 결과메세지 에러코드
	/*----------------------------------------------------------------------------------------------------------*/
	 
		 
	/*******************************************************************
	 * 5. 강제취소                                                     *
	 *                                                                 *
	 * 지불 결과를 DB 등에 저장하거나 기타 작업을 수행하다가 실패하는  *
	 * 경우, 아래의 코드를 참조하여 이미 지불된 거래를 취소하는 코드를 *
	 * 작성합니다.                                                     *
	 *******************************************************************/
	 try
	 {
	 	// DB CODE HERE
	 }
	 catch(Exception e)
	 {
		inipay.setType("cancel"); // 고정
		inipay.setCancelMsg("DB FAIL"); // 취소사유
		inipay.startAction();
		
		resultCode = "01";
		resultMsg = "DB FAIL";
	}
%>


<!-------------------------------------------------------------------------------------------------------
 *  													*
 *	아래 내용은 결제 결과에 대한 출력 페이지 샘플입니다. 				                *
 *													*
 -------------------------------------------------------------------------------------------------------->

<html>
<head>
<title>INIpay41 결제페이지 데모</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/group.css" type="text/css">
<style>
body,tr,td {
	font-size: 10pt;
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
	
	function show_receipt(tid) // 영수증 출력
	{
		if("<%=resultCode%>" == "00")
		{
			var receiptUrl = "https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/mCmReceipt_head.jsp?noTid=" + "<%=tid%>" + "&noMethod=1";
			window.open(receiptUrl,"receipt","width=430,height=700");
		}
		else
		{
			alert("해당하는 결제내역이 없습니다");
		}
	}
		
	function errhelp() // 상세 에러내역 출력
	{
		var errhelpUrl = "http://www.inicis.com/ErrCode/Error.jsp?result_err_code=" + "<%=resulterrCode%>" + "&mid=" + "<%=mid%>" + "&tid=<%=tid%>" + "&goodname=" + "<%=goodname%>" + "&price=" + "<%=price%>" + "&paymethod=" + "<%=payMethod%>" + "&buyername=" + "<%=buyername%>" + "&buyertel=" + "<%=buyertel%>" + "&buyeremail=" + "<%=buyeremail%>" + "&codegw=" + "<%=codegw%>";
		window.open(errhelpUrl,"errhelp","width=520,height=150, scrollbars=yes,resizable=yes");
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
		<td height="85"
			background=<% 

/*------------------------------------------------------------------------------------------------------*
 * 결제 방법에 따라 상단 이미지가 변경 된다								*
 * 	 가. 결제 실패 시에 "img/spool_top.gif" 이미지 사용						*
 *       나. 결제 방법에 따라 상단 이미지가 변경							*
 *       	1. 신용카드 	- 	"img/card.gif"							*
 *		2. ISP		-	"img/card.gif"							*
 *		3. 은행계좌	-	"img/bank.gif"							*
 *		4. 무통장입금	-	"img/bank.gif"							*
 *		5. 핸드폰	- 	"img/hpp.gif"							*
 *		6. 전화결제 (ars전화 결제)	-	"img/phone.gif"					*
 *		7. 전화결제 (받는전화결제)	-	"img/phone.gif"					*
 *		8. OK CASH BAG POINT		-	"img/okcash.gif"				*
 *		9. 문화상품권	-	"img/ticket.gif"						*
 *              10. K-merce 상품권 -    "img/kmerce.gig"                                                *
 *		11. 틴캐시 결제		- 	"img/teen_top.gif"                                      *
 *              12. 게임문화상품권 결제		-	"img/dgcl_top.gif"                              *
 *              13. SKT 상품권		-	"img/skt_top.gif"					*
 *------------------------------------------------------------------------------------------------------*/
    					
    				if(resultCode.equals("01")){
					out.println("img/spool_top.gif");
				}
				else{
					
    					if(payMethod.equals("Card")){
    						out.println("img/card.gif");
    					}
    					else if(payMethod.equals("VCard")){ // ISP
						out.println("img/card.gif");
					}
    					else if(payMethod.equals("HPP")){ // 휴대폰
						out.println("img/hpp.gif");
					}
    					else if(payMethod.equals("Ars1588Bill")){ // 1588
						out.println("img/phone.gif");
					}
    					else if(payMethod.equals("PhoneBill")){ // 폰빌
						out.println("img/phone.gif");
					}
    					else if(payMethod.equals("OCBPoint")){ // OKCASHBAG
						out.println("img/okcash.gif");
					}
    					else if(payMethod.equals("DirectBank")){  // 은행계좌이체
						out.println("img/bank.gif");
					}
    					else if(payMethod.equals("VBank")){  // 무통장 입금 서비스
						out.println("img/bank.gif");
					}
    					else if(payMethod.equals("Culture")){  // 문화상품권 결제
						out.println("img/ticket.gif");
					}
					else if(payMethod.equals("KMC_")){	// K-merce 상품권 결제
						out.println("img/kmerce.gif");
					}
					else if(payMethod.equals("TEEN")){
						out.println("img/teen_top.gif");
					}
					else if(payMethod.equals("DGCL")){
						out.println("img/dgcl_top.gif");
					}
					else if(payMethod.equals("SKTG")){
						out.println("img/skt_top.gif");
					}
					else{
						out.println("img/card.gif");
					}// 기타 지불수단의 경우
				}
					
    				%>
			style="padding: 0 0 0 64"><!-------------------------------------------------------------------------------------------------------
 *													*
 *  아래 부분은 모든 결제수단의 공통적인 결과메세지 출력 부분입니다.					*
 *  													*
 *	1. resultCode 	(결 과 코 드) 									*
 *  	2. resultMsg	(결과 메세지)									*
 *  	3. payMethod	(결 제 수 단)									*
 *  	4. tid		(거 래 번 호)									*
 *  	5. moid  	(주 문 번 호)									*
 -------------------------------------------------------------------------------------------------------->

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="3%" valign="top"><img src="img/title_01.gif"
					width="8" height="27" vspace="5"></td>
				<td width="97%" height="40" class="pl_03"><font color="#FFFFFF"><b>결제결과</b></font></td>
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
							width="12" height="10"> <!-------------------------------------------------------------------------------------------------------
                 * resultCode 											        *
                 *       가. 결 과 코 드: "00" 인 경우 결제 성공[무통장입금인 경우 - 고객님의 무통장입금 요청이 완료]	*
                 *       나. 결 과 코 드: "00"외의 값인 경우 결제 실패  						*
                 -------------------------------------------------------------------------------------------------------->
						<b>
						<% if(resultCode.equals("00") && payMethod.equals("VBank")){ out.println("고객님의 무통장입금 요청이 완료되었습니다.");}
                  	   else if(resultCode.equals("00")){ out.println("고객님의 결제요청이 성공되었습니다.");}
                           else{ out.println("고객님의 결제요청이 실패되었습니다.");} %>
						</b></td>
						<td width="8"><img src="img/right.gif" width="8" height="30"></td>
					</tr>
				</table>
				<br>
				<table width="510" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="407" style="padding: 0 0 0 9"><img
							src="img/icon.gif" width="10" height="11"> <strong><font
							color="433F37">결제내역</font></strong></td>
						<td width="103">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2" style="padding: 0 0 0 23">
						<table width="470" border="0" cellspacing="0" cellpadding="0">
							<tr>

								<!-------------------------------------------------------------------------------------------------------
                 * 2. $inipay->m_payMethod 										*
                 *       가. 결제 방법에 대한 값									*
                 *       	1. 신용카드 	- 	Card								*
                 *		2. ISP		-	VCard								*
                 *		3. 은행계좌	-	DirectBank							*
                 *		4. 무통장입금	-	VBank								*
                 *		5. 핸드폰	- 	HPP								*
                 *		6. 전화결제 (ars전화 결제)	-	Ars1588Bill					*
                 *		7. 전화결제 (받는전화결제)	-	PhoneBill					*
                 *		8. OK CASH BAG POINT		-	OCBPoint					*
                 *		9. 문화상품권			-	Culture						*
                 *		10. K-merce 상품권 		- 	KMC_                                            *
                 *              11. 틴캐시 결제 		- 	TEEN						*
                 *		12. 게임문화 상품권 		-	DGCL                                            *
                 *		13. SKT 상품권			-	SKTG						*
                 *-------------------------------------------------------------------------------------------------------->
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">결 제 방 법</td>
								<td width="343"><%=payMethod%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="26">결 과 코 드</td>
								<td width="343">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td><%=resultCode%></td>
										<td width="142" align="right"><!-------------------------------------------------------------------------------------------------------
                 * resultCode 값에 따라 "영수증 보기" 또는 "실패 내역 자세히 보기" 버튼 출력		                *
                 *       가. 결제 코드의 값이 "00"인 경우에는 "영수증 보기" 버튼 출력					*
                 *       나. 결제 코드의 값이 "00" 외의 값인 경우에는 "실패 내역 자세히 보기" 버튼 출력			*
                 -------------------------------------------------------------------------------------------------------->
										<!-- 실패결과 상세 내역 버튼 출력 --> <%
                            		if(resultCode.equals("00")){
                				out.println("<a href='javascript:show_receipt();'><img src='img/button_02.gif' width='94' height='24' border='0'></a>");
                			}
                			else{
                            			out.println("<a href='javascript:errhelp();'><img src='img/button_01.gif' width='142' height='24' border='0'></a>");
                            		}
                                                    	
                            	%>
										</td>
									</tr>
								</table>
								</td>
							</tr>

							<!-------------------------------------------------------------------------------------------------------
                 * resultMsg 											        *
                 *       가. 결과 내용을 보여 준다 실패시에는 "[에러코드] 실패 메세지" 형태로 보여 준다.                *
                 *		예> [9121]서명확인오류									*
                 -------------------------------------------------------------------------------------------------------->
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

							<!-------------------------------------------------------------------------------------------------------
                 * tid											                *
                 *       가. 이니시스가 부여한 거래 번호 -모든 거래를 구분할 수 있는 키가 되는 값			*
                 -------------------------------------------------------------------------------------------------------->
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

							<!-------------------------------------------------------------------------------------------------------
                 * moid											                 *
                 *       가. 상점에서 할당한 주문번호 									*
                 -------------------------------------------------------------------------------------------------------->
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">주 문 번 호</td>
								<td width="343"><%=moid%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>

							<!-------------------------------------------------------------------------------------------------------
                 * resultprice				                                                                *
                 *       가. 결제 완료 금액             								*
                 -------------------------------------------------------------------------------------------------------->
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">결제결과금액</td>
								<td width="343"><%=resultprice%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>


							<%                    
                    

	/*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  1.  신용카드 , ISP 결제 결과 출력 (OK CASH BAG POINT 복합 결제 내역 )				*
	 -------------------------------------------------------------------------------------------------------*/

	if(payMethod.equals("Card") || payMethod.equals("VCard")){
%>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">신용카드번호</td>
								<td width="343"><%=cardNumber%>****</td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 날 짜</td>
								<td width="343"><%=pgAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 시 각</td>
								<td width="343"><%=pgAuthTime%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 번 호</td>
								<td width="343"><%=authCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">할 부 기 간</td>
								<td width="343"><%=cardQuota%>개월&nbsp;<b><font
									color="red"><%=interest%></font></b></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">카 드 종 류</td>
								<td width="343"><%=cardCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">카드발급사</td>
								<td width="343"><%=cardIssuerCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td height="1" colspan="3">&nbsp;</td>
							</tr>
							<tr>
								<td style='padding: 0 0 0 9' colspan='3'><img
									src='img/icon.gif' width='10' height='11'> <strong><font
									color='433F37'>달러결제 정보</font></strong></td>
							</tr>
							<tr>
								<td width='18' align='center'><img src='img/icon02.gif'
									width='7' height='7'></td>
								<td width='109' height='25'>통 화 코 드</td>
								<td width='343'><%=ReqCurrency%></td>
							</tr>
							<tr>
								<td height='1' colspan='3' align='center'
									background='img/line.gif'></td>
							</tr>
							<tr>
								<td width='18' align='center'><img src='img/icon02.gif'
									width='7' height='7'></td>
								<td width='109' height='25'>환 율</td>
								<td width='343'><%=RateExchange%></td>
							</tr>
							<tr>
								<td height='1' colspan='3' align='center'
									background='img/line.gif'></td>
							</tr>
							<tr>
								<td height='1' colspan='3'>&nbsp;</td>
							</tr>
							<tr>
								<td style="padding: 0 0 0 9" colspan="3"><img
									src="img/icon.gif" width="10" height="11"> <strong><font
									color="433F37">OK CASHBAG 적립 및 사용내역</font></strong></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">카 드 번 호</td>
								<td width="343"><%=ocbcardnumber%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">적립 승인번호</td>
								<td width="343"><%=ocbSaveAuthCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">사용 승인번호</td>
								<td width="343"><%=ocbUseAuthCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 일 시</td>
								<td width="343"><%=ocbAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">포인트지불금액</td>
								<td width="343"><%=price2%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<%
                    
          }
        
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  2.  은행계좌결제 결과 출력 										*
	 -------------------------------------------------------------------------------------------------------*/
	 
          else if(payMethod.equals("DirectBank")){
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 날 짜</td>
								<td width="343"><%=pgAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 시 각</td>
								<td width="343"><%=pgAuthTime%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">은 행 코 드</td>
								<td width="343"><%=directbankcode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">현금영수증<br>
								발급결과코드</td>
								<td width="343"><%=rcash_rslt%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">현금영수증<br>
								발급구분코드</td>
								<td width="343"><%=ruseopt%> <font color=red><b>(0
								- 소득공제용, 1 - 지출증빙용)</b></font></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>

							<%                            
          }
          
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  3.  무통장입금 입금 예정 결과 출력 (결제 성공이 아닌 입금 예정 성공 유무)				*
	 -------------------------------------------------------------------------------------------------------*/
	 
          else if(payMethod.equals("VBank")){
 
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">입금계좌번호</td>
								<td width="343"><%=vacct%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">입금 은행코드</td>
								<td width="343"><%=vcdbank%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">예금주 명</td>
								<td width="343"><%=nmvacct%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">송금자 명</td>
								<td width="343"><%=nminput%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">송금자 주민번호</td>
								<td width="343"><%=perno%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">상품 주문번호</td>
								<td width="343"><%=moid%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">입금 예정일자</td>
								<td width="343"><%=dtinput%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">입금 예정시간</td>
								<td width="343"><%=tminput%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<%                    		
          }
          
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  4.  핸드폰 결제 											*
	 -------------------------------------------------------------------------------------------------------*/
	 
          else if(payMethod.equals("HPP")){
  
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">휴대폰번호</td>
								<td width="343"><%=nohpp%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 날 짜</td>
								<td width="343"><%=pgAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 시 각</td>
								<td width="343"><%=pgAuthTime%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<%                    		
          }
          
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  5.  전화 결제 											*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if(payMethod.equals("Ars1588Bill") || payMethod.equals("PhoneBill")){

%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">전 화 번 호</td>
								<td width="343"><%=noars%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 날 짜</td>
								<td width="343"><%=pgAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 시 각</td>
								<td width="343"><%=pgAuthTime%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>

							<%                    	
         }
         
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  6.  OK CASH BAG POINT 적립 및 지불 									*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if(payMethod.equals("OCBPoint")){
         	
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">카 드 번 호</td>
								<td width="343"><%=ocbcardnumber%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 날 짜</td>
								<td width="343"><%=pgAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 시 각</td>
								<td width="343"><%=pgAuthTime%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">적립 승인번호</td>
								<td width="343"><%=ocbSaveAuthCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">사용 승인번호</td>
								<td width="343"><%=ocbUseAuthCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">승 인 일 시</td>
								<td width="343"><%=ocbAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">포인트지불금액</td>
								<td width="343"><%=price2%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>

							<%                    		
         }
         
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  7.  문화 상품권						                			*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if(payMethod.equals("Culture")){
         	
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">컬쳐랜드 ID</td>
								<td width="343"><%=cultureid%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>

							<%                                
         }
         
         
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  8.  K-merce 상품권						                			*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if(payMethod.equals("KMC_")){
         	
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">K-merce ID</td>
								<td width="343"><%=cultureid%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>

							<%                                
         }
         
         
          /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  9.  틴캐시 결제						                			*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if(payMethod.equals("TEEN")){
         	
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">틴캐시 잔액</td>
								<td width="343"><%=remain_price%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">틴캐시 아이디</td>
								<td width="343"><%=cultureid%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<%                                
         }
         
         
    	/*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  10.  게임문화상품권 결제						               			*
	 -------------------------------------------------------------------------------------------------------*/
	 else if(payMethod.equals("DGCL")){
%>

							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">사용한 카드수</td>
								<td width="343"><%=dgcl_cardcount%> 장</td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<%
	}                                
        
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  11.  SKT 문화상품권 결제						               			*
	 -------------------------------------------------------------------------------------------------------*/
	 else if(payMethod.equals("SKTG")){
	 
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">결제방법</td>
								<td width="343">
								<%
                  	if(sktg_method.equals("C")){
                  		
                  		out.println("카드형");
                  		
                  	}
                  	else{
                  		
                  		out.println("모바일형");
                  	}
		  %>
								</td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">사용한 카드수</td>
								<td width="343"><%=dgcl_cardcount%> 장</td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<%
	}
%>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
						</table>
						</td>
					</tr>
				</table>
				<br>

				<!-------------------------------------------------------------------------------------------------------
 *													*
 *  결제 성공시($inipay->m_resultCode == "00"인 경우 ) "이용안내"  보여주기 부분입니다.			*	    
 *  결제 수단별로 이용고객에게 결제 수단에 대한 주의 사항을 보여 줍니다. 				*
 *  switch , case문 형태로 결제 수단별로 출력 하고 있습니다.						*
 *  아래 순서로 출력 합니다.										*
 *													*
 *  1.	신용카드 											*
 *  2.  ISP 결제 											*
 *  3.  핸드폰 												*
 *  4.  전화 결제 (1588Bill)										*
 *  5.  전화 결제 (PhoneBill)										*
 *  6.	OK CASH BAG POINT										*
 *  7.  은행계좌이체											*
 *  8.  무통장 입금 서비스										*
 *  9.  문화상품권 결제											*
 *  10. K-merce 상품권 결제                                                                             *
 *  11. 틴캐시 결제											*
 *  12. 게임문화상품권 결제                                                                             *
 *  13. SKT 상품권 결제											*	
 -------------------------------------------------------------------------------------------------------->

				<%
            	
            	if(resultCode.equals("00")){
            		
            		        /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  1.  신용카드 						                			*
	 			--------------------------------------------------------------------------------------------------------*/
	
				if(payMethod.equals("Card")){ 
%>
				<table width="510"
					border="0' cellspacing='0' cellpadding='0'>
         					<tr> 
         					    <td height='25'  style='padding:0 0 0 9'><img src='img/icon.gif' width='10' height='11'> 
         					      <strong><font color='433F37'>이용안내</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style='padding:0 0 0 23'> 
         					      <table width='470' border='0' cellspacing='0' cellpadding='0'>
         					        <tr>          					          
         					          <td height='25'>(1) 신용카드 청구서에 <b>"이니시스(inicis.com)"
					</b>으로 표기됩니다.<br>
         					          (2) LG카드 및 BC카드의 경우 <b>"이니시스(이용 상점명)"</b>으로 표기되고, 삼성카드의 경우 <b>\"이니시스(이용상점 URL)\"</b>로 표기됩니다.</td>
         					        </tr>
         					        <tr> 
         					          <td height='1' colspan='2' align='center'  background='img/line.gif'></td>
         					        </tr>
         					        
         					      </table>
			</td>
         					  </tr>
		</table>
		<%         				      
				}
				
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  2.  ISP 						                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("VCard")){
%>
		<table width='510' border='0' cellspacing='0' cellpadding='0'>
			<tr>
				<td height='25' style='padding: 0 0 0 9'><img
					src='img/icon.gif' width='10' height='11'> <strong><font
					color='433F37'>이용안내</font></strong></td>
			</tr>
			<tr>
				<td style='padding: 0 0 0 23'>
				<table width='470' border='0' cellspacing='0' cellpadding='0'>
					<tr>
						<td height='25'>(1) 신용카드 청구서에 <b>"이니시스(inicis.com)"</b>으로
						표기됩니다.<br>
						(2) LG카드 및 BC카드의 경우 <b>"이니시스(이용 상점명)"</b>으로 표기되고, 삼성카드의 경우 <b>"이니시스(이용상점
						URL)"</b>로 표기됩니다.</td>
					</tr>
					<tr>
						<td height='1' colspan='2' align='center'
							background='img/line.gif'></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>
		<%         				      
				}
					
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  3. 핸드폰 						                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("HPP")){
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">이용안내</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) 핸드폰 청구서에 <b>"소액결제"</b> 또는 <b>"외부정보이용료"</b>로
						청구됩니다.<br>
						(2) 본인의 월 한도금액을 확인하시고자 할 경우 각 이동통신사의 고객센터를 이용해주십시오.</td>
					</tr>
					<tr>
						<td height="1" colspan="2" align="center"
							background="img/line.gif"></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>
		<%         				      
				}
								
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  4. 전화 결제 (ARS1588Bill)				                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("Ars1588Bill")){ 
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">이용안내</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) 전화 청구서에 <b>"컨텐츠 이용료"</b>로 청구됩니다.<br>
						(2) 월 한도금액의 경우 동일한 가입자의 경우 등록된 전화번호 기준이 아닌 주민등록번호를 기준으로 책정되어 있습니다.<br>
						(3) 전화 결제취소는 당월에만 가능합니다.</td>
					</tr>
					<tr>
						<td height="1" colspan="2" align="center"
							background="img/line.gif"></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>
		<%         				      
				}
					
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  5. 폰빌 결제 (PhoneBill)				                				*
	 			--------------------------------------------------------------------------------------------------------*/
				
				else if(payMethod.equals("PhoneBill")){
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">이용안내</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) 전화 청구서에 <b>"인터넷 컨텐츠 (음성)정보이용료"</b>로
						청구됩니다.<br>
						(2) 월 한도금액의 경우 동일한 가입자의 경우 등록된 전화번호 기준이 아닌 주민등록번호를 기준으로 책정되어 있습니다.<br>
						(3) 전화 결제취소는 당월에만 가능합니다.</td>
					</tr>
					<tr>
						<td height="1" colspan="2" align="center"
							background="img/line.gif"></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>
		<%
				}
				
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  6. OK CASH BAG POINT					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("OCBPoint")){ 
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">이용안내</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) OK CASH BAG 포인트 결제취소는 당월에만 가능합니다.</td>
					</tr>
					<tr>
						<td height="1" colspan="2" align="center"
							background="img/line.gif"></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>
		<%         				      
				}
					
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  7. 은행계좌이체					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("DirectBank")){  
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">이용안내</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) 고객님의 통장에는 이용하신 상점명이 표기됩니다.<br>
						(2) 결제에 대한 상세조회는 www.inicis.com의 왼쪽 상단 "사용내역 및 청구요금 조회"에서도
						확인가능합니다.</td>
					</tr>
					<tr>
						<td height="1" colspan="2" align="center"
							background="img/line.gif"></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>
		<%         				      
				}
					
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  8. 무통장 입금 서비스					                				*
	 			--------------------------------------------------------------------------------------------------------*/		
				
				else if(payMethod.equals("VBank")){  
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">이용안내</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						(1) 상기 결과는 입금예약이 완료된 것일뿐 실제 입금완료가 이루어진 것이 아닙니다.
						<br>
						(2) 상기 입금계좌로 해당 상품금액을 무통장입금(창구입금)하시거나, 인터넷 뱅킹 등을 통한 온라인 송금을 하시기
						바랍니다.
						<br>
						(3) 반드시 입금기한 내에 입금하시기 바라며, 대금입금시 반드시 주문하신 금액만 입금하시기 바랍니다.
						</td>
					</tr>
					<tr>
						<td height="1" colspan="2" align="center"
							background="img/line.gif"></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>
		<%         				      
				}
					
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  9. 문화상품권 결제					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("Culture")){
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">이용안내</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) 문화상품권을 온라인에서 이용하신 경우 오프라인에서는 사용하실 수 없습니다.<br>
						(2) 컬쳐캐쉬 잔액이 남아있는 경우, 고객님의 컬쳐캐쉬 잔액을 다시 사용하시려면 컬쳐랜드 ID를 기억하시기 바랍니다.
						</td>
					</tr>
					<tr>
						<td height="1" colspan="2" align="center"
							background="img/line.gif"></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>
		<%         				      
				}
				
				/*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  10. K-merce 상품권 결제					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("KMC_")){
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">이용안내</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) K-merce 상품권은 소액결제가 가능하며, 상품권의 잔여 금액에 대해
						지속적으로 사용가능합니다.<br>
						(2) K-merce 상품권 충전은 K-merce 사이트(www.k-merce.com)에서만 충전이 가능합니다.</td>
					</tr>
					<tr>
						<td height="1" colspan="2" align="center"
							background="img/line.gif"></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>
		<%         				      
				}
				
				/*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  10. 틴캐시 결제					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("TEEN")){
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">이용안내</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1)틴캐시는 인터넷 사이트 또는 PC방에서 자유롭게 사용할 수 있는 선불
						결제수단입니다.<br>
						(2)틴캐시 카드번호 결제 : 틴캐시 카드 뒷면에 적힌 12자리 번호를 입력하여 결제하는 방식입니다.<br>
						(3)틴캐시 아이디 결제 : 틴캐시 사이트 (www.teencash.co.kr)에 회원가입 후 틴캐시 사이트에 접속하여
						구매한 틴캐시 카드를 등록하여 이용하는 방식입니다.</td>
					</tr>
					<tr>
						<td height="1" colspan="2" align="center"
							background="img/line.gif"></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>

		<%         				      
				}
				
				/*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  11. 게임문화상품권 결제					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("DGCL")){
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">이용안내</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1)게임문화 상품권은 상품권에 인쇄되어있는 스크래치 번호로 결제하는
						방식입니다..<br>
						(2)게임문화 상품권 결제은 문화상품권(www.cultureland.co.kr)에서 구입 하실수 있습니다.<br>
						(3)게임문화 상품권은 최대 6장까지 결제가 가능합니다.</td>
					</tr>
					<tr>
						<td height="1" colspan="2" align="center"
							background="img/line.gif"></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>
		<%
				}
				
				/*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  12. SKT 상품권 결제					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("SKTG")){
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">이용안내</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td>(1)SKT 통화료 선납,통화시간 충전 서비스 및 온/오프라인 상품권 가맹점에서<br>
						&nbsp;&nbsp;편리하게 현금 대용으로 결제할수 있는 선불 결제 수단 으로 다음 2가지 방식의<br>
						&nbsp;&nbsp;결제 방법을 제공합니다.<br>
						<br>
						(2)<b>SKT 상품권 카드형 결제</b><br>
						&nbsp;&nbsp;- SKT 카드형 상품권 뒷면에 적힌 16자리 Pin 번호와 비밀 번호 6자리를 입력<br>
						&nbsp;&nbsp;하여 결제 하는 방식입니다.<br>
						<br>
						(3)<b>SKT 상품권 모바일형 결제</b><br>
						&nbsp;&nbsp;- SKT 모바일 상품권은 회원가입 ID(전화번호)와 비밀번호 6 ~ 8자리를 입력<br>
						&nbsp;&nbsp;하여 결제 하는 방식으로 모바일 상품권을 이용 하시려면 SKT 상품권 SITE<br>
						&nbsp;&nbsp;(www.monetagift.com)에 회원 가입을 하여야 합니다.</td>
					</tr>
					<tr>
						<td height="1" colspan="2" align="center"
							background="img/line.gif"></td>
					</tr>

				</table>
				</td>
			</tr>
		</table>
		<%
				}
		}
%> <!-- 이용안내 끝 --></td>
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
