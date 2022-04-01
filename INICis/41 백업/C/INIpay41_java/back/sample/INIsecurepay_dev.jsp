<%------------------------------------------------------------------------------
 FILE NAME : INIsecurepay.jsp
 AUTHOR : jwkim@inicis.com
 DATE : 2004/07
 USE WITH : INIsecurepay.html
 
 이니페이 플러그인을 통해 요청된 지불을 처리한다.
                                                          http://www.inicis.com
                                                      http://support.inicis.com
                            Copyright 2004 Inicis Co., Ltd. All rights reserved.
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
	
	inipay.setInipayHome("C:/INIpay41_JAVA"); // INIpay Home (절대경로로 적절히 수정)
	inipay.setKeyPw("1111"); // 키패스워드(상점아이디에 따라 변경)
	inipay.setType("securepay"); // 고정
	inipay.setPgId("INIpay"+pgid); // 고정
	inipay.setSubPgIp("203.238.3.10"); // 고정
	inipay.setDebug("true"); // 로그모드("true"로 설정하면 상세한 로그가 생성됨)
	inipay.setMid(request.getParameter("mid")); // 상점아이디
	inipay.setUid(request.getParameter("uid")); // 고정(임의 수정 불가)
	inipay.setUip(request.getRemoteAddr()); // 고정
	inipay.setGoodName(request.getParameter("goodname"));
	inipay.setCurrency(request.getParameter("currency"));
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
	
	
	/*--------------------------------------------------------------*
	 * 무이자 할부 거래일 경우 할부개월 뒤에 무이자 여부를 표시한다.*
	 *--------------------------------------------------------------*/
	String interest = "";
	String quotainterest = request.getParameter("quotainterest");
	if(quotainterest.equals("1")){
		interest = "(무이자 할부)";
	}
	/*--------------------------------------------------------------*/
	
	
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
	 
	 String tid = inipay.getTid(); // 거래번호
	 String resultCode = inipay.getResultCode(); // 결과코드 ("00"이면 지불 성공)
	 String resultMsg = inipay.getResultMsg(); // 결과내용 (지불결과에 대한 설명)
	 String payMethod = inipay.getPayMethod(); // 지불방법 (매뉴얼 참조)
	 String price1 = inipay.getPrice1(); // OK Cashbag 복합결재시 신용카드 지불금액
	 String price2 = inipay.getPrice2(); // OK Cashbag 복합결재시 포인트 지불금액
	 String authCode = inipay.getAuthCode(); // 신용카드 승인번호
	 String cardQuota = inipay.getCardQuota(); // 할부기간
	 String quotaInterest = inipay.getQuotaInterest(); // 무이자할부 여부 ("1"이면 무이자할부)
	 String cardCode = inipay.getCardCode(); // 신용카드사 코드 (매뉴얼 참조)
	 String cardIssuerCode = inipay.getCardIssuerCode(); // 카드발급사 코드 (매뉴얼 참조)
	 String authCertain = inipay.getAuthCertain(); // 본인인증 수행여부 ("00"이면 수행)
	 String pgAuthDate = inipay.getPgAuthDate(); // 이니시스 승인날짜
	 String pgAuthTime = inipay.getPgAuthTime(); // 이니시스 승인시각
	 String ocbSaveAuthCode = inipay.getOcbSaveAuthCode(); // OK Cashbag 적립 승인번호
	 String ocbUseAuthCode = inipay.getOcbUseAuthCode(); // OK Cashbag 사용 승인번호
	 String ocbAuthDate = inipay.getOcbAuthDate(); // OK Cashbag 승인일시
	 String eventFlag = inipay.getEventFlag(); // 각종 이벤트 적용 여부
	 String nohpp = inipay.getNoHpp(); // 휴대폰 결제시 사용된 휴대폰 번호
	 String noars = inipay.getNoArs(); // 전화결제 시 사용된 전화번호  
	 String perno = inipay.getPerno(); // 송금자 주민번호
	 String vacct = inipay.getVacct(); // 가상계좌번호
	 String vcdbank = inipay.getVcdbank(); // 입금할 은행코드
	 String dtinput = inipay.getDtinput(); // 입금예정일
	 String nminput = inipay.getNminput(); // 송금자 명
	 String nmvacct = inipay.getNmvacct(); // 예금주 명
	 String moid = inipay.getmoid(); // 상점 주문번호
	 String codegw = inipay.getcodegw(); // 전화결제 사업자 코드
	 String ocbcardnumber = inipay.getocbcardnumber(); // OK CASH BAG 결제 , 적립인 경우 OK CASH BAG 카드 번호
	 String cultureid = inipay.getcultureid(); // 컬쳐 랜드 ID
	 String cardNumber = inipay.getCardNumber(); // 신용카드번호  	 
	 String mid = request.getParameter("mid");
	 String goodname = request.getParameter("goodname");
	 String price = request.getParameter("price");
	 String buyername = request.getParameter("buyername");
	 String buyertel = request.getParameter("buyertel");
	 String buyeremail = request.getParameter("buyeremail");
	 
        /*-------------------------------------------------------------------------*
	 * 에러발생시 결과 메세지에서 에러코드를 추출하는 부분으로 절대 수정 금지  *
	 *-------------------------------------------------------------------------*/
	 String tmp_ErrCode[] = resultMsg.split("]");
	 String resulterrCode = resultMsg.substring(1,tmp_ErrCode[0].length()); // 결과메세지 에러코드
	/*-------------------------------------------------------------------------*/
	 
		 
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

<html>
<head>

<title>INIpay</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<script>
	var openwin=window.open("childwin.html","childwin","width=300,height=160");
	openwin.close();
	
	function show_receipt() // 영수증 출력
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
<table border=0 width=600>
	<tr>
		<td>
		<hr noshade size=1>
		<b>결제 결과</b>
		<hr noshade size=1>
		</td>
	</tr>
</table>
<br>

<table border=0 width=600>
	<tr>
		<td align=right nowrap>결제방법 :</td>
		<td><%=payMethod%></td>
	</tr>
	<tr>
		<td align=right nowrap>결과코드 :</td>
		<td><%=resultCode%></td>
	</tr>
	<tr>
		<td align=right nowrap>결과내용 :</td>
		<td><font class=emp><%=resultMsg%></font></td>
	</tr>
	<tr>
		<td align=right nowrap>거래번호 :</td>
		<td><%=tid%></td>
	</tr>
	<tr>
		<td align=right nowrap>승인번호 :</td>
		<td><%=authCode%></td>
	</tr>
	<tr>
		<td align=right nowrap>할부기간 :</td>
		<td><%=cardQuota%>개월 <%=interest%></td>
	</tr>
	<tr>
		<td align=right nowrap>카드종류 :</td>
		<td><%=cardCode%></td>
	</tr>
	<tr>
		<td align=right nowrap>카드발급사 :</td>
		<td><%=cardIssuerCode%></td>
	</tr>
	<tr>
		<td align=right nowrap>승인날짜 :</td>
		<td><%=pgAuthDate%></td>
	</tr>
	<tr>
		<td align=right nowrap>승인시각 :</td>
		<td><%=pgAuthTime%></td>
	</tr>
	<tr>
		<td align=right><br>
		<b>무통장 입금예약 정보</b></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align=right nowrap>입금 계좌 번호 :</td>
		<td><%=vacct%></td>
	</tr>
	<tr>
		<td align=right nowrap>입금 은행코드 :</td>
		<td><%=vcdbank%></td>
	</tr>
	<tr>
		<td align=right nowrap>예금주 명 :</td>
		<td><%=nmvacct%></td>
	</tr>
	<tr>
		<td align=right nowrap>송금자 명 :</td>
		<td><%=nminput%></td>
	</tr>
	<tr>
		<td align=right nowrap>송금자 주민번호 :</td>
		<td><%=perno%></td>
	</tr>
	<tr>
		<td align=right nowrap>상품 주문번호 :</td>
		<td><%=moid%></td>
	</tr>
	<tr>
		<td align=right nowrap>입금예약 일자 :</td>
		<td><%=dtinput%></td>
	</tr>
	<tr>
		<td align=right><br>
		<b>OK Cashbag 정보</b></td>
		<td>&nbsp;</td>
	</tr>
	<tr>
		<td align=right nowrap>적립 승인번호 :</td>
		<td><%=ocbSaveAuthCode%></td>
	</tr>
	<tr>
		<td align=right nowrap>사용 승인번호 :</td>
		<td><%=ocbUseAuthCode%></td>
	</tr>
	<tr>
		<td align=right nowrap>승인일시 :</td>
		<td><%=ocbAuthDate%></td>
	</tr>
	<tr>
		<td align=right nowrap>포인트 지불금액 :</td>
		<td><%=price2%></td>
	</tr>
	<tr>
		<td colspan=2><br>
		당 쇼핑몰이 (주)이니시스의 대표가맹을 이용하는 경우, 카드이용명세서에 <font color=red>구입처가
		이니시스전자지불로 표기</font>됩니다</font>.</td>
	</tr>
	<tr>
		<td colspan=2 align=center><br>
		<%
               	if(resultCode.equals("00")){
               		out.println("<input type=button value='영수증보기' onclick=javascript:show_receipt()>");
                }
                else{
                	out.println("<input type=button value='실패내역 자세히 보기' onclick=javascript:errhelp()>");
                }
                                                    	
       	%> <br>
		<br>
		</td>
	</tr>
	<tr>
		<td colspan=2>
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
