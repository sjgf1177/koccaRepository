<%------------------------------------------------------------------------------
 FILE NAME : INIsecurepay_kocca.jsp
 AUTHOR : jwkim@inicis.com
 DATE : 2004/07
 USE WITH : INIsecurepay.html

 이니페이 플러그인을 통해 요청된 지불을 처리한다.
                                                          http://www.inicis.com
                                                      http://support.inicis.com
                            Copyright 2004 Inicis Co., Ltd. All rights reserved.
------------------------------------------------------------------------------%>

<%@ page
	language = "java"
	contentType = "text/html; charset=euc-kr"
	import = "com.inicis.inipay.*"
%>

<%
//**********************************************************
//  1. 제      목: SUBJECT PREVIEW PAGE
//  2. 프로그램명: gu_SubjectPreviewOn.jsp
//  3. 개      요: 과정안내
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004. 01.12
//  7. 수      정:
//***********************************************************
%>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.account.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<jsp:useBean id = "memberBean" class = "com.credu.system.MemberAdminBean"  scope = "page" />
<jsp:useBean id = "code" class = "com.credu.system.CodeConfigBean"  scope = "page" />
<jsp:useBean id = "probean" class = "com.credu.propose.ProposeBean"  scope = "page" />

<% //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	/**************************************
	 * 1. INIpay41 클래스의 인스턴스 생성 *
	 **************************************/
	INIpay41 inipay = new INIpay41();


	/*********************
	 * 2. 지불 정보 설정 *
	 *********************/

	 /************************************************************************************
	 * 하단의 PGID 부분은 지불수단별로 TID를 별도로 표시하도록 하며,                     *
	 * 임의로 수정하여 발생된 문제에 대해서는 (주)이니시스에 책임이                      *
	 * 없으니 소스 수정시 주의를 바랍니다					             *
	 ************************************************************************************/

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
		else if(paymethod.equals("TEEN")){
		pgid = "TEEN"; // 틴캐쉬
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
	inipay.setCardCode(request.getParameter("cardcode")); // 플러그인에서 리턴되는 카드코드 두자리
	/*-----------------------------------------------------------------*
	 * 수취인 정보 *                                                   *
	 *-----------------------------------------------------------------*
	 * 실물배송을 하는 상점의 경우에 사용되는 필드들이며               *
	 * 아래의 값들은 INIsecurepay.html 페이지에서 포스트 되도록        *
	 * 필드를 만들어 주도록 하십시요.                                  *
	 * 콘텐츠 제공업체의 경우 삭제하셔도 무방합니다.                   *
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

	 String tid 			= inipay.getTid(); // 거래번호
	 String resultCode 		= inipay.getResultCode(); // 결과코드 ("00"이면 지불 성공)
	 String resultMsg 		= inipay.getResultMsg(); // 결과내용 (지불결과에 대한 설명)
	 String payMethod 		= inipay.getPayMethod(); // 지불방법 (매뉴얼 참조)
	 String price1 			= inipay.getPrice1(); // OK Cashbag 복합결재시 신용카드 지불금액
	 String price2 			= inipay.getPrice2(); // OK Cashbag 복합결재시 포인트 지불금액
	 String authCode 		= inipay.getAuthCode(); // 신용카드 승인번호
	 String cardQuota 		= inipay.getCardQuota(); // 할부기간
	 String quotaInterest 	= inipay.getQuotaInterest(); // 무이자할부 여부 ("1"이면 무이자할부)
	 String cardCode 		= inipay.getCardCode(); // 신용카드사 코드 (매뉴얼 참조)
	 String cardIssuerCode 	= inipay.getCardIssuerCode(); // 카드발급사 코드 (매뉴얼 참조)
	 String authCertain 	= inipay.getAuthCertain(); // 본인인증 수행여부 ("00"이면 수행)
	 String pgAuthDate 		= inipay.getPgAuthDate(); // 이니시스 승인날짜
	 String pgAuthTime 		= inipay.getPgAuthTime(); // 이니시스 승인시각
	 String ocbSaveAuthCode = inipay.getOcbSaveAuthCode(); // OK Cashbag 적립 승인번호
	 String ocbUseAuthCode 	= inipay.getOcbUseAuthCode(); // OK Cashbag 사용 승인번호
	 String ocbAuthDate 	= inipay.getOcbAuthDate(); // OK Cashbag 승인일시
	 String eventFlag 		= inipay.getEventFlag(); // 각종 이벤트 적용 여부
	 String nohpp 			= inipay.getNoHpp(); // 휴대폰 결제시 사용된 휴대폰 번호
	 String noars 			= inipay.getNoArs(); // 전화결제 시 사용된 전화번호
	 String perno 			= inipay.getPerno(); // 송금자 주민번호
	 String vacct 			= inipay.getVacct(); // 가상계좌번호
	 String vcdbank 		= inipay.getVcdbank(); // 입금할 은행코드
	 String dtinput 		= inipay.getDtinput(); // 입금예정일
	 String nminput 		= inipay.getNminput(); // 송금자 명
	 String nmvacct 		= inipay.getNmvacct(); // 예금주 명
	 String moid 			= inipay.getmoid(); // 상점 주문번호
	 String codegw 			= inipay.getcodegw(); // 전화결제 사업자 코드
	 String ocbcardnumber 	= inipay.getocbcardnumber(); // OK CASH BAG 결제 , 적립인 경우 OK CASH BAG 카드 번호
	 String cultureid 		= inipay.getcultureid(); // 컬쳐 랜드 ID
	 String cardNumber 		= inipay.getCardNumber(); // 신용카드번호

	 String mid 			= request.getParameter("mid");
	 String goodname 		= request.getParameter("goodname");
	 String price 			= request.getParameter("price");
	 String buyername 		= request.getParameter("buyername");
	 String buyertel 		= request.getParameter("buyertel");
	 String buyeremail 		= request.getParameter("buyeremail");

	// 틴캐쉬 결제수단을 이용시에만  결제 결과 내용
	String RemainPrice 		= inipay.getRemainPrice();		//틴캐쉬결제후 잔액

	//N:일반과정, P:전문가과정, B:도서
	String v_gubun		= request.getParameter("p_gubun");

	String v_receive = request.getParameter("p_receive");
	String v_phone = request.getParameter("p_phone");
	String v_post1 = request.getParameter("p_post1");
	String v_post2 = request.getParameter("p_post2");
	String v_addr1 = request.getParameter("p_addr1");
	String v_addr2 = request.getParameter("p_addr2");

	/*-------------------------------------------------------------------------*
	 * 에러발생시 결과 메세지에서 에러코드를 추출하는 부분으로 절대 수정 금지  *
	 *-------------------------------------------------------------------------*/
	 //String tmp_ErrCode[] 	= resultMsg.split("]");
	 //String resulterrCode 	= resultMsg.substring(1,tmp_ErrCode[0].length());

	 System.out.println("resultmsg.indexOf=" + resultMsg.indexOf("]"));

	 String resulterrCode 	= "";

	 if (resultMsg.indexOf("]") != -1) {
	 	resulterrCode=resultMsg.substring(1,resultMsg.indexOf("]"));
	 }

	 System.out.println("resulterrCode = " + resulterrCode);
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
	 	System.out.println("#1");

	 	// DB CODE HERE
	 	if (box.getSession("userid").equals(""))    {
	 		// 세션이 없으면 지불취소
	 		inipay.setType("cancel"); // 고정
			inipay.setCancelMsg("NO SESSION"); // 취소사유
			inipay.startAction();

			resultCode = "01";
			resultMsg = "NO SESSION";

	 	} else {
	 		//HashTable 저장

			Hashtable BillData = new Hashtable();

			BillData.clear();
			BillData.put("tid" 				,tid 			);
			BillData.put("resultCode" 		,resultCode 	);
			BillData.put("resultMsg" 		,resultMsg 		);
			BillData.put("payMethod" 		,payMethod 		);
			BillData.put("price1" 			,price1 		);
			BillData.put("price2" 			,price2 		);
			BillData.put("authCode" 		,authCode 		);
			BillData.put("cardQuota" 		,cardQuota 		);
			BillData.put("quotaInterest" 	,quotaInterest 	);
			BillData.put("cardCode" 		,cardCode 		);
			BillData.put("cardIssuerCode" 	,cardIssuerCode );
			BillData.put("authCertain" 	    ,authCertain 	);
			BillData.put("pgAuthDate" 		,pgAuthDate 	);
			BillData.put("pgAuthTime" 		,pgAuthTime 	);
			BillData.put("ocbSaveAuthCode"  ,ocbSaveAuthCode);
			BillData.put("ocbUseAuthCode" 	,ocbUseAuthCode );
			BillData.put("ocbAuthDate" 	    ,ocbAuthDate 	);
			BillData.put("eventFlag" 		,eventFlag 		);
			BillData.put("nohpp" 			,nohpp 			);
			BillData.put("noars" 			,noars 			);
			BillData.put("perno" 			,perno 			);
			BillData.put("vacct" 			,vacct 			);
			BillData.put("vcdbank" 		    ,vcdbank 		);
			BillData.put("dtinput" 		    ,dtinput 		);
			BillData.put("nminput" 		    ,nminput 		);
			BillData.put("nmvacct" 		    ,nmvacct 		);
			BillData.put("moid" 			,moid 			);
			BillData.put("codegw" 			,codegw 		);
			BillData.put("ocbcardnumber" 	,ocbcardnumber 	);
			BillData.put("cultureid" 		,cultureid 		);
			BillData.put("cardNumber" 		,cardNumber 	);
			BillData.put("mid" 			    ,mid 			);
			BillData.put("goodname" 		,goodname 		);
			BillData.put("price" 			,price 			);
			String v_ispointuse = request.getParameter("ispointuse");
			if (v_ispointuse == null) 	v_ispointuse = "N";

			BillData.put("ispointuse"		,	v_ispointuse); //포인트 사용여부


			String v_point = request.getParameter("point");
			if (v_point == null) v_point = "0";
			BillData.put("point" 			,v_point 		); //사용 포인트
			BillData.put("buyername" 		,buyername 		);
			BillData.put("buyertel" 		,buyertel 		);
			BillData.put("buyeremail" 		,buyeremail 	);
			BillData.put("resulterrCode"	,resulterrCode  );

			System.out.println("v_gubun 1= " + v_gubun);

	 		BillData.put("gubun"           ,v_gubun);
	 		System.out.println("v_gubun 2= " + v_gubun);
			BillData.put("userid"           ,box.getSession("userid")      );
			BillData.put("usernm"           ,box.getSession("name")      );


			BillData.put("inputname"        ,"");
			BillData.put("inputdate"        ,"");

			BillData.put("receive"          ,v_receive     );
			BillData.put("phone"           	,v_phone     );
			BillData.put("post1"           	,v_post1     );
			BillData.put("post2"           	,v_post2     );
			BillData.put("addr1"           	,v_addr1     );
			BillData.put("addr2"           	,v_addr2     );
			BillData.put("tem_grcode"           	,box.getSession("tem_grcode")     );

			System.out.println("v_tid 				= " + tid 				);
			System.out.println("v_resultCode 	    = " + resultCode 	    );
			System.out.println("v_resultMsg 		= " + resultMsg 		);
			System.out.println("v_payMethod 		= " + payMethod 		);
			System.out.println("v_price1 			= " + price1 			);
			System.out.println("v_price2 			= " + price2 			);
			System.out.println("v_authCode 		    = " + authCode 		    );
			System.out.println("v_cardQuota 		= " + cardQuota 		);
			System.out.println("v_quotaInterest 	= " + quotaInterest 	);
			System.out.println("v_cardCode 		    = " + cardCode 		    );
			System.out.println("v_cardIssuerCode 	= " + cardIssuerCode 	);
			System.out.println("v_authCertain 	    = " + authCertain 	    );
			System.out.println("v_pgAuthDate 		= " + pgAuthDate 		);
			System.out.println("v_pgAuthTime 		= " + pgAuthTime 		);
			System.out.println("v_ocbSaveAuthCode   = " + ocbSaveAuthCode   );
			System.out.println("v_ocbUseAuthCode 	= " + ocbUseAuthCode 	);
			System.out.println("v_ocbAuthDate 	    = " + ocbAuthDate 	    );
			System.out.println("v_eventFlag		    = " + eventFlag		    );
			System.out.println("v_nohpp 			= " + nohpp 			);
			System.out.println("v_noars 			= " + noars 			);
			System.out.println("v_perno 			= " + perno 			);
			System.out.println("v_vacct 			= " + vacct 			);
			System.out.println("v_vcdbank 		    = " + vcdbank 		    );
			System.out.println("v_dtinput 		    = " + dtinput 		    );
			System.out.println("v_nminput 		    = " + nminput 		    );
			System.out.println("v_nmvacct 		    = " + nmvacct 		    );
			System.out.println("v_moid 			    = " + moid 			    );
			System.out.println("v_codegw 			= " + codegw 			);
			System.out.println("v_ocbcardnumber 	= " + ocbcardnumber 	);
			System.out.println("v_cultureid 		= " + cultureid 		);
			System.out.println("v_cardNumber 		= " + cardNumber 		);
			System.out.println("v_mid 			    = " + mid 			    );
			System.out.println("v_goodname 		    = " + goodname 		    );
			System.out.println("v_price	            = " + price	            );
			System.out.println("v_ispointuse		= " + v_ispointuse		);
			System.out.println("v_point 		    = " + v_point 		    );
			System.out.println("v_buyername 	    = " + buyername 	    );
			System.out.println("v_buyertel 	        = " + buyertel 	        );
			System.out.println("v_buyeremail 	    = " + buyeremail 	    );
			System.out.println("v_resulterrCode     = " + resulterrCode     );
			System.out.println("v_gubun             = " + v_gubun            );
			System.out.println("v_userid            = " + box.getSession("userid")            );
			System.out.println("v_usernm            = " + box.getSession("name")            );
			System.out.println("v_billprice         = " + request.getParameter("p_billprice")         );
			System.out.println("tem_grcode         = " + box.getSession("tem_grcode")         );

			int isOk = 0;

			AccountManagerBean bean = new AccountManagerBean();

			if (resultCode.equals("00")) {
				//장바구니 저장
				if(v_gubun.equals("P")) {	//패키지과정
					isOk = bean.InsertBasketPackage(box);
				} else {	//일반과정
					isOk = bean.InsertBasketSubj(box);
				}
				//결제결과 저장
				if(isOk > 0) {
		 			isOk = bean.BillProcess(BillData);
		 		}
			}

		 	System.out.println("최종결과"+isOk);

	 		if (isOk==1) {//성공

	 		} else {//실패
	 			inipay.setType("cancel"); // 고정
				inipay.setCancelMsg("DB FAIL"); // 취소사유
				inipay.startAction();

				resultCode = "01";
				resultMsg = "DB FAIL";
	 		}
	 	}


	 }
	 catch(Exception e)
	 {
	 	System.out.println("Exception FOUND");
	 	System.out.println("err="+e);
		inipay.setType("cancel"); // 고정
		inipay.setCancelMsg("DB FAIL"); // 취소사유
		inipay.startAction();

		resultCode = "01";
		resultMsg = "SYSTEM FAIL";
	}
%>
<!-------------------------------------------------------------------------------------------------------
 *  													*
 *       												*
 *        												*
 *	아래 내용은 결제 결과에 대한 출력 페이지 샘플입니다. 				                *
 *	여러 결제 수단별 결과내용이 별도로 출력되도록 되어 있으므로 소스 파악이 힘드신 경우             *
 *      간단한 결과 내용을 보여 주는 개발자용 샘플페이지 (INIsecurepay_dev.jsp)를 참조 하시기 바랍니다.	*
 *													*
 *													*
 *													*
 -------------------------------------------------------------------------------------------------------->

<html>
<head>
<title>INIpay41 결제페이지</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/group.css" type="text/css">
<style>
body, tr, td {font-size:10pt; font-family:굴림,verdana; color:#433F37; line-height:19px;}
table, img {border:none}

/* Padding ******/
.pl_01 {padding:1 10 0 10; line-height:19px;}
.pl_03 {font-size:20pt; font-family:굴림,verdana; color:#FFFFFF; line-height:29px;}

/* Link ******/
.a:link  {font-size:9pt; color:#333333; text-decoration:none}
.a:visited { font-size:9pt; color:#333333; text-decoration:none}
.a:hover  {font-size:9pt; color:#0174CD; text-decoration:underline}

.txt_03a:link  {font-size: 8pt;line-height:18px;color:#333333; text-decoration:none}
.txt_03a:visited {font-size: 8pt;line-height:18px;color:#333333; text-decoration:none}
.txt_03a:hover  {font-size: 8pt;line-height:18px;color:#EC5900; text-decoration:underline}
</style>

<script>
	var openwin=window.open("childwin.html","childwin","width=300,height=160");
	openwin.close();

	/*------------------------------------------------------------------------------------------------------*
         * 1. $inipay->m_resultCode 										*
         *       가. 결 과 코 드: "00" 인 경우 결제 성공[무통장입금인 경우 - 고객님의 무통장입금 요청이 완료]	*
         *       나. 결 과 코 드: "00"외의 값인 경우 결제 실패  						*
         *------------------------------------------------------------------------------------------------------*/

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

window.resizeTo(619,600);

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

<script language="javascript">
<!--
<% //if (v_process.equals("update")) { %>
	//opener.location.reload();
	opener.location="/servlet/controller.study.KMyClassServlet?p_process=ProposeCancelPage";
<% //} %>

function myclass() {
	opener.location="/servlet/controller.study.KMyClassServlet?p_process=EducationSubjectPage";
	window.close();
}

function mybilllist() {
	opener.location="/servlet/controller.study.KMyClassServlet?p_process=ProposeCancelPage";
	window.close();
}
-->
</script>
</head>


<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table width="590" border="0" cellspacing="0" cellpadding="0" bgcolor="f3f3eb">
  <tr>
    <td align="center" ><img src="/images/user/game/account/ti_ok.gif" width="532" height="35"><br>
 <br>
      <table width="508" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td height="35" bgcolor="ddd9cd"> <!-------------------------------------------------------------------------------------------------------
                 * 1. resultCode 											*
                 *       가. 결 과 코 드: "00" 인 경우 결제 성공[무통장입금인 경우 - 고객님의 무통장입금 요청이 완료]	*
                 *       나. 결 과 코 드: "00"외의 값인 경우 결제 실패  						*
                 -------------------------------------------------------------------------------------------------------->
                  <b><% if(resultCode.equals("00") && payMethod.equals("VBank")){ out.println("고객님의 무통장입금 요청이 완료되었습니다.");}
                  	   else if(resultCode.equals("00")){ out.println("고객님의 결제요청이 성공되었습니다.");}
                           else{ out.println("고객님의 결제요청이 실패되었습니다.");} %> </b></td>
        </tr>
    </table>
      <br>      <table width="508" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td> <img src="/images/user/game/account/ti_set_01.gif" width="192" height="31"></td>
        </tr>
      </table>
      <table width="508" height="1" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td bgcolor="c1bba5"></td>
        </tr>
      </table>
      <table width="508" border="0" cellpadding="0" cellspacing="1" bgcolor="c1bba5">
        <tr>
        <!-------------------------------------------------------------------------------------------------------
                 * 2. m_payMethod 										*
                 *       가. 결제 방법에 대한 값									*
                 *       	ㄱ. 신용카드 	- 	Card								*
                 *		ㄴ. ISP		-	VCard								*
                 *		ㄷ. 은행계좌	-	DirectBank							*
                 *		ㄹ. 무통장입금	-	VBank								*
                 *		ㅁ. 핸드폰	- 	HPP								*
                 *		ㅂ. 전화결제 (ars전화 결제)	-	Ars1588Bill					*
                 *		ㅅ. 전화결제 (받는전화결제)	-	PhoneBill					*
                 *		ㅇ. OK CASH BAG POINT		-	OCBPoint					*
                 *		ㅈ. 문화상품권	-	Culture								*
                 -------------------------------------------------------------------------------------------------------->

<td width="118" height="25" align="center" bgcolor="ddd9cd">결제방법 </td>
          <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="15" height="8">
            <%if (payMethod.equals("Card")) out.print("신용카드");
              else if (payMethod.equals("VCard")) out.print("신용카드");
              else if (payMethod.equals("DirectBank")) out.print("계좌이체");
              else if (payMethod.equals("VBank")) out.print("무통장(가상계좌)");
            %></td>
        </tr>
        <tr>
          <td width="118" height="25" align="center"  bgcolor="ddd9cd">결&nbsp;&nbsp;&nbsp;&nbsp;과 </td>
          <td height="25" bgcolor="#FFFFFF">
          <table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="15" height="8">
                            <% if(resultCode.equals("00")){
                            		out.print("성공");
                               } else {
                               		out.print("실패");
                               }
                            %>
                            </td>
                            <td width="142" align="right">

                <!-------------------------------------------------------------------------------------------------------
                 * 2. $inipay->m_resultCode 값에 따라 "영수증 보기" 또는 "실패 내역 자세히 보기" 버튼 출력		*
                 *       가. 결제 코드의 값이 "00"인 경우에는 "영수증 보기" 버튼 출력					*
                 *       나. 결제 코드의 값이 "00" 외의 값인 경우에는 "실패 내역 자세히 보기" 버튼 출력			*
                 -------------------------------------------------------------------------------------------------------->
		<!-- 실패결과 상세 내역 버튼 출력 -->
                            	<%
                            		if(resultCode.equals("00")){
                				out.println("<a href='javascript:show_receipt();'><img src='/images/user/game/account/button_02.gif' width='85' height='24' border='0'></a>");
                			}
                			else{
                            			out.println("<a href='javascript:errhelp();'><img src='/images/user/game/account/button_02.gif' width='85' height='24' border='0'></a>");
                            		}

                            	%>                    </td>
                          </tr>
                        </table>
                        </td>
        </tr>
        		<!-------------------------------------------------------------------------------------------------------
                 * 1. resultMsg 											*
                 *       가. 결과 내용을 보여 준다 실패시에는 "[에러코드] 실패 메세지" 형태로 보여 준다.                *
                 *		예> [9121]서명확인오류									*
                 -------------------------------------------------------------------------------------------------------->
        <tr>
          <td width="118" height="25" align="center"  bgcolor="ddd9cd">결과내용</td>
          <td height="25" bgcolor="#FFFFFF">            <img src="/img/spacer.gif" width="24" height="8"><%=resultMsg%></td>
        </tr>
        		<!-------------------------------------------------------------------------------------------------------
                 * 1. $inipay->m_tid											*
                 *       가. 이니시스가 부여한 거래 번호 -모든 거래를 구분할 수 있는 키가 되는 값			*
                 -------------------------------------------------------------------------------------------------------->

        <tr>
          <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">거래번호</td>
          <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=tid%></td>
        </tr>
        		<!-------------------------------------------------------------------------------------------------------
                 * 1. $inipay->m_moid											*
                 *       가. 상점에서 할당한 주문번호 									*
                 -------------------------------------------------------------------------------------------------------->
        <!--tr>
          <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">주문번호</td>
          <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=moid%></td>
        </tr-->




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
            	<td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">신용카드번호</td>
                <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=cardNumber%>****</td>
   			</tr>
			<tr>
            	<td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">승 인 날 짜</td>
                <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=pgAuthDate%></td>
        	</tr>
            <tr>
                <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">승 인 시 각</td>
            	<td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=pgAuthTime%></td>
            </tr>
            <tr>
            	<td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">승 인 번 호</td>
                <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=authCode%></td>
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
                                  <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">승 인 날 짜</td>
                                   <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=pgAuthDate%></td>
                                </tr>
                                <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">승 인 시 각</td>
                                   <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=pgAuthTime%></td>
                                </tr>
<%
          }
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*
	 *													*
	 *  3.  가상계좌 출력 										*
	 -------------------------------------------------------------------------------------------------------*/
          else if(payMethod.equals("VBank")){
%>
          			<tr>
                        <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">입금계좌번호</td>
                        <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=vacct%></td>
                    </tr>
          			<tr>
                        <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">입금은행</td>
                        <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8">
                        <%
                          if(vcdbank.equals("03")) {
                          	out.print("기업은행");
                          } else if(vcdbank.equals("04")) {
                          	out.print("국민은행");
                          } else if(vcdbank.equals("05")) {
                          	out.print("외환은행");
                          } else if(vcdbank.equals("07")) {
                          	out.print("수협중앙회");
                          } else if(vcdbank.equals("11")) {
                          	out.print("농협중앙회");
                          } else if(vcdbank.equals("20")) {
                          	out.print("우리은행");
                          } else if(vcdbank.equals("23")) {
                          	out.print("SC제일은행");
                          } else if(vcdbank.equals("31")) {
                          	out.print("대구은행");
                          } else if(vcdbank.equals("32")) {
                          	out.print("부산은행");
                          } else if(vcdbank.equals("34")) {
                          	out.print("광주은행");
                          } else if(vcdbank.equals("37")) {
                          	out.print("전북은행");
                          } else if(vcdbank.equals("39")) {
                          	out.print("경남은행");
                          } else if(vcdbank.equals("53")) {
                          	out.print("한국씨티은행");
                          } else if(vcdbank.equals("71")) {
                          	out.print("우체국");
                          } else if(vcdbank.equals("81")) {
                          	out.print("하나은행");
                          } else if(vcdbank.equals("88")) {
                          	out.print("통합 신한은행 (신한,조흥은행)");
                          }
                        %>
                        </td>
                    </tr>
                    <tr>
                        <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">예금주 명</td>
                        <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=nmvacct%></td>
                    </tr>
                    <tr>
                        <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">송금자 명</td>
                        <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=nminput%></td>
                    </tr>
<%
          }
%>

      </table>
      <table width="508" height="1" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td bgcolor="c1bba5"></td>
        </tr>
      </table>

            <br>

      <!-- 이용안내 -->
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
 ------------------------------------------------------------------------------------------------------->

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

   <table width="508" height="30" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td> <img src="/images/user/game/account/ti_set_05.gif" width="192" height="31"></td>
        </tr>
      </table>

	<table width="508" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><table width="508" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="10">&nbsp; </td>
              <td>(1) 신용카드 청구서에 <b>"이니시스(inicis.com)"</b>으로 표기됩니다.<br>
			   (2) LG카드 및 BC카드의 경우 <b>"이니시스(이용 상점명)"</b>으로 표기되고, 삼성카드의 경우 <b>"이니시스(이용상점 URL)"</b>로 표기됩니다.</td>
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

       <table width="508" height="30" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td> <img src="/images/user/game/account/ti_set_05.gif" width="192" height="31"></td>
        </tr>
      </table>

	<table width="508" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><table width="508" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="10">&nbsp; </td>
              <td>(1) 신용카드 청구서에 <b>"이니시스(inicis.com)"</b>으로 표기됩니다.<br>
			   (2) LG카드 및 BC카드의 경우 <b>"이니시스(이용 상점명)"</b>으로 표기되고, 삼성카드의 경우 <b>"이니시스(이용상점 URL)"</b>로 표기됩니다.</td>
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

 <table width="508" height="30" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td> <img src="/images/user/game/account/ti_set_05.gif" width="192" height="31"></td>
        </tr>
      </table>

	<table width="508" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><table width="508" border="0" cellpadding="0" cellspacing="0">
            <tr>
              <td width="10">&nbsp; </td>
              <td>(1) 고객님의 통장에는 <b>"이니시스"</b>로 표기됩니다.</td>
            </tr>
          </table>
          </td>
        </tr>
      </table>


<%
				}

		}

%>


      <!-- 이용안내 끝 -->

      <br>

      <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td></td>
      </tr>
    </table>
      <table width="100%" height="62" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" ><table width="250" border="0" cellspacing="0" cellpadding="0">
            <tr align="center">
            <% if (resultCode.equals("00")) { %>
              <!--td width="176"><a href="javascript:myclass();"><img src="/images/user/game/account/b_my.gif" width="89" height="21"></a></td>
              <td width="10"></td-->
              <td width="176"><a href="javascript:mybilllist();"><img src="/images/user/game/account/b_list.gif" width="89" height="21"></a></td>
              <td width="10"></td>
            <% } %>
              <td width="60"><a href="javascript:window.close();"><img src="/images/user/game/account/btn_cl.gif" width="45" height="21"></a></td>
              </tr>
          </table></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<form name="fbill" method="post" action="/inicis/INIsecurepayform.jsp">
	<input type="hidden" name="p_gubun" value="<%=v_gubun%>">
</form>
</body>
</html>
