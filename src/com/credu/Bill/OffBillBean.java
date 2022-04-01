//**********************************************************
//1. 제	  목: 오프라인 수강신청 결제 BEAN
//2. 프로그램명: ProposeCourseBean.java
//3. 개	  요: 오프라인 수강신청 결제 bean
//4. 환	  경: JDK 1.5
//5. 버	  젼: 1.0
//6. 작	  성: 2010.1.18
//7. 수	  정:
//**********************************************************
package com.credu.Bill;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Hashtable;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.SQLString;
import com.inicis.inipay.INIpay50;

@SuppressWarnings("unchecked")
public class OffBillBean {
	ConfigSet cs = null;

	public int billStart(RequestBox box) throws Exception {

		cs = new ConfigSet();
		/**************************************
		 * 1. INIpay41 클래스의 인스턴스 생성 *
		 **************************************/
		INIpay50 inipay = new INIpay50();

		int isOk = 0;

		/*********************
		 * 2. 지불 정보 설정 *
		 *********************/

		String	v_listgubun	= box.get("p_listgubun");	//수강신청 : PROP, 결제요청 : REQ
		String	v_subj		= box.get("p_subj");
		String	v_year		= box.get("p_year");
		String	v_subjseq	= box.get("p_subjseq");
		String	v_seq		= box.get("p_seq");

		/************************************************************************************
		 * 하단의 PGID 부분은 지불수단별로 TID를 별도로 표시하도록 하며,                     *
		 * 임의로 수정하여 발생된 문제에 대해서는 (주)이니시스에 책임이                      *
		 * 없으니 소스 수정시 주의를 바랍니다					             *
		 ************************************************************************************/

		String pgid = cs.getProperty("inipay.pgId."+box.get("paymethod"));
		if (pgid == null) pgid = box.get("paymethod");

		inipay.SetField("inipayhome", cs.getProperty("inipay.inipayHome")); // INIpay Home (절대경로로 적절히 수정) - C:/INIpay41_JAVA
		inipay.SetField("admin", cs.getProperty("inipay.keyPw")); // 키패스워드(상점아이디에 따라 변경) - 1111
		inipay.SetField("keypw", cs.getProperty("inipay.keyPw")); // 키패스워드(상점아이디에 따라 변경) - 1111
		inipay.SetField("type", cs.getProperty("inipay.type")); // 고정 - securepay
		inipay.SetField("pgid", cs.getProperty("inipay.pgId")+pgid); // 고정 - INIpay
		inipay.SetField("subpgip", cs.getProperty("inipay.subPgIp")); // 고정 - 203.238.3.10
		inipay.SetField("debug", cs.getProperty("inipay.debug")); // 로그모드("true"로 설정하면 상세한 로그가 생성됨)
		inipay.SetField("url", cs.getProperty("inipay.url")); // 실제 서비스되는 상점 SITE URL로 변경할것 -- 기존 버그.. 잘못된 정보로 저장중이였음.("http://www.your_domain.co.kr")

		inipay.SetField("uid", box.get("uid")); // 고정(임의 수정 불가) - 이니시스 플러그인에서 자동으로 반환하므로 수정불가
		inipay.SetField("uip", box.get("userip")); // 고정
		inipay.SetField("goodname", box.get("goodname"));
		inipay.SetField("currency", box.get("currency"));
		inipay.SetField("price", box.get("price"));
		inipay.SetField("buyername", box.get("buyername"));
		inipay.SetField("buyertel", box.get("buyertel"));
		inipay.SetField("buyeremail", box.get("buyeremail"));
		inipay.SetField("parentemail", box.get("parentemail")); // 보호자 이메일 주소(핸드폰, 전화결제시에 14세 미만의 고객이 결제하면  부모 이메일로 결제 내용통보 의무, 다른결제 수단 사용시에 삭제 가능)
		inipay.SetField("paymethod", box.get("paymethod"));
		inipay.SetField("encrypted", box.get("encrypted"));
		inipay.SetField("sessionkey", box.get("sessionkey"));
		inipay.SetField("cardcode", box.get("cardcode")); // 플러그인에서 리턴되는 카드코드 두자리

		/**
		 * 상점아이디 : inipay.testPay.list의 목록에 해당되는 URL로 접근하였을 경우 자동취소가 이루어진다.
		 */
		String mid 			= null;
		//		String connectedUrl = box.get("request.serverName");
		//		String[]testBillList = cs.getProperty("inipay.testPay.list").split("|");

		String area			= box.get("p_area");
		if ( area.equals("G0") ) {			// 게임 mallId kocca00000
			mid	=	cs.getProperty("inipay.mid.realgame");
		} else if( area.equals("K0") ) {	// 문콘 mallId kocca00002
			mid	=	cs.getProperty("inipay.mid.realcontent");
		} else if( area.equals("B0") ) {	// 방송 mallId kocca00004
			mid	=	cs.getProperty("inipay.mid.realbroad");
		} else {
			mid = null;
		}

		//		for (String url : testBillList) {//2010.02.16 현재 localhost|lms.kocca.or.kr|test.kocca.or.kr
		//			if(connectedUrl.indexOf(url) != -1)
		//				mid = cs.getProperty("inipay.mid.test");	// 테스트 상점아이디
		//		}
		inipay.SetField("mid", mid);

		/*-----------------------------------------------------------------*
		 * 수취인 정보 *                                                   *
		 *-----------------------------------------------------------------*
		 * 실물배송을 하는 상점의 경우에 사용되는 필드들이며               *
		 * 아래의 값들은 INIsecurepay.html 페이지에서 포스트 되도록        *
		 * 필드를 만들어 주도록 하십시요.                                  *
		 * 컨텐츠 제공업체의 경우 삭제하셔도 무방합니다.                   *
		 *-----------------------------------------------------------------*/
		//		inipay.SetField("recvName", box.get("recvname")); 	// 수취인 명
		//		inipay.SetField("recvTel", box.get("recvtel"));	// 수취인 연락처
		//		inipay.SetField("recvAddr", box.get("recvaddr"));	// 수취인 주소
		//		inipay.SetField("recvPostNum", box.get("recvpostnum"));	// 수취인 우편번호

		/**
		 * 이니시스 조회 페이지(https://iniweb.inicis.com)에서 보이는
		 * 한글이 깨지는 경우 아래 3줄 중 하나를 사용(주석해제)하여
		 * 한글이 제대로 보이는 것을 사용하십시오.
		 */
		//inipay.SetField("encoding(1);
		//inipay.SetField("encoding(2);
		//inipay.SetField("encoding(3);


		/****************
		 * 3. 지불 요청 *
		 ****************/
		inipay.startAction();


		/****************
		 * 4. 지불 결과 *
		 ****************/

		/*	공통필드, 모든 지불수단에 공통	*/
		String tid 			= inipay.GetResult("tid"); // 거래번호
		String resultCode 		= inipay.GetResult("ResultCode"); // 결과코드 ("00"이면 지불 성공)
		String resultMsg 		= inipay.GetResult("ResultMsg"); // 결과내용 (지불결과에 대한 설명)
		String moid 			= inipay.GetResult("MOID"); // 상점주문번호. 결제 요청시 "oid" 필드에 설정된값
		String pgAuthDate 		= inipay.GetResult("ApplDate"); // 이니시스 승인날짜
		String pgAuthTime 		= inipay.GetResult("ApplTime"); // 이니시스 승인시각
		String authCode 		= inipay.GetResult("ApplNum"); // 신용카드 승인번호/결제승인 번호. OCB Point/VBank를 제외한 지불수단에 모두 존재
		String payMethod 		= inipay.GetResult("PayMethod"); // 지불방법 (매뉴얼 참조)
		//todo: => 원상품가격과 결제결과금액과 비교하여 금액이 동일하지 않다면 결제 금액의 위변조가 의심됨으로 정상적인 처리가 되지 않도록 처리 바랍니다.(거래취소처리)
		//String totPrice 			= inipay.GetResult("TotPrice"); // 결제결과 금액
		String eventFlag 		= inipay.GetResult("EventCode"); // 각종 이벤트 적용 여부 => 카드 할부 및 행사 적용 코드.[별첨정보 참조***매뉴얼을 의미]

		/*	지불수단 : 카드결제(Card, VCard 공통)	*/
		String cardNumber 		= inipay.GetResult("CARD_Num"); // 신용카드번호
		String quotaInterest	 	= inipay.GetResult("CARD_Interest"); // 할부 여부 ("1"이면 무이자할부)
		String cardQuota 		= inipay.GetResult("CARD_Quota"); // 할부기간
		String cardCode 			= inipay.GetResult("CARD_Code"); // 신용카드사 코드 (매뉴얼 참조)
		String cardIssuerCode 	= inipay.GetResult("CARD_BankCode"); // 카드발급사 코드 (매뉴얼 참조)
		//카드사 직발행 카드가 아닌 계열카드인 경우, 2자리 신용카드사코드와 더블어 자세한 카드정보를 나타냅니다(직발행 카드인 경우 "00"으로 반환됩니다.)
		String ocbcardnumber 		= inipay.GetResult("OCB_Num"); // OK CASH BAG 결제, 적립인 경우 OK CASH BAG 카드 번호
		String ocbSaveAuthCode	= inipay.GetResult("OCB_SaveApplNum"); // OK Cashbag 적립 승인번호
		String ocbUseAuthCode	= inipay.GetResult("OCB_PayApplNum"); // OK Cashbag 사용 승인번호
		String ocbAuthDate 			= inipay.GetResult("OCB_ApplDate"); // OK Cashbag 승인일시
		//		String ocbPayPrice = inipay.GetResult("OCB_PayPrice");//OK Cashbag 적립 및 사용내역, 포인트지불금액
		String price2					= inipay.GetResult("OCB_PayPrice"); // OK Cashbag 복합결재시 포인트 지불금액
		String price1					= inipay.GetResult("Price1"); // OK Cashbag 복합결재시 신용카드 지불금액 ---> 사용안함

		/*	지불수단 : 실시간계좌이체(DirectBank)	*/
		//String acctBankCode		= inipay.GetResult("ACCT_BankCode");//은행코드, [별첨정보 참조***매뉴얼을 의미] String[2]
		//String cshrResultCode	= inipay.GetResult("CSHR_ResultCode");// 현금영수증, 발급결과코드 String[10]
		//String cshrType				= inipay.GetResult("CSHR_Type");//현금영수증, 발급구분코드

		/*	지불수단 : 무통장입금 or 가상계좌(VBank)	*/
		String vacct 			= inipay.GetResult("VACT_Num"); // 가상계좌번호
		String vcdbank 		= inipay.GetResult("VACT_BankCode"); // 입금할 은행코드
		String nmvacct 		= inipay.GetResult("VACT_Name"); // 예금주 명
		String nminput 		= inipay.GetResult("VACT_InputName"); // 송금자 명
		String perno 			= inipay.GetResult("VACT_RegNum"); // 송금자 주민번호
		String dtinput 		= inipay.GetResult("VACT_Date"); // 입금예정일
		//String dtinputTime 		= inipay.GetResult("VACT_Time"); // 입금예정시간

		/*	지불수단 : 핸드폰, 전화 결제(HPP)	*/
		String nohpp 			= inipay.GetResult("HPP_Num"); // 휴대폰 결제시 사용된 휴대폰 번호
		String codegw 			= inipay.GetResult("HPP_GWCode"); // 전화결제 사업자 코드, 핸드폰, 전화 결제 결과 데이터( "실패 내역 자세히 보기"에서 필요 , 상점에서는 필요없는 정보임)

		/*	지불수단 : 거는 전화결제 - 핸드폰 결제 결과 데이터(Ars1588Bill)	*/
		String noars 			= inipay.GetResult("ARSB_Num"); // 전화결제 시 사용된 전화번호

		//* 10. 문화 상품권 결제 결과 데이터
		String cultureid 		= inipay.GetResult("CULT_UserID"); // 컬쳐 랜드 ID
		//* 12.틴캐시 잔액 데이터
		// 틴캐쉬 결제수단을 이용시에만  결제 결과 내용
		//		String RemainPrice 		= inipay.GetResult("RemainPrice");		//틴캐쉬결제후 잔액
		//*    inipay.GetResult("TEEN_Remains")
		//*  틴캐시 ID : inipay.GetResult("TEEN_UserID")
		//* 13.게임문화 상품권
		//*	사용 카드 갯수 : inipay.GetResult("GAMG_Cnt")
		//* 14.도서문화 상품권
		//*	사용자 ID : inipay.GetResult("BCSH_UserID")

		String authCertain 	= inipay.GetResult("AuthCertain"); // 본인인증 수행여부 ("00"이면 수행)



		String goodname 		= box.get("goodname");
		String price 			= box.get("price");
		String buyername 		= box.get("buyername");
		String buyertel 		= box.get("buyertel");
		String buyeremail 		= box.get("buyeremail");


		//N:일반과정, P:전문가과정, B:도서
		String v_gubun		= box.get("p_gubun");

		String v_receive = box.get("p_receive");
		String v_phone = box.get("p_phone");
		String v_post1 = box.get("p_post1");
		String v_post2 = box.get("p_post2");
		String v_addr1 = box.get("p_addr1");
		String v_addr2 = box.get("p_addr2");

		/*-------------------------------------------------------------------------*
		 * 에러발생시 결과 메세지에서 에러코드를 추출하는 부분으로 절대 수정 금지  *
		 *-------------------------------------------------------------------------*/
		//String tmp_ErrCode[] 	= resultMsg.split("]");
		//String resulterrCode 	= resultMsg.substring(1,tmp_ErrCode[0].length());

		System.out.println("resultmsg.indexOf=" + resultMsg.indexOf("]"));

		String resulterrCode 	="";

		if (resultMsg.indexOf("]") != -1) {
			resulterrCode=resultMsg.substring(1,resultMsg.indexOf("]"));
		}

		System.out.println("resulterrCode =" + resulterrCode);
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
			//			System.out.println("#1");

			// DB CODE HERE
			if (box.getSession("userid").equals(""))    {
				// 세션이 없으면 지불취소
				inipay.SetField("type", "cancel"); // 고정
				inipay.SetField("cancelMsg", "NO SESSION"); // 취소사유
				inipay.startAction();

				resultCode ="01";
				resultMsg ="NO SESSION";

			} else {
				//HashTable 저장

				Hashtable BillData = new Hashtable();

				BillData.clear();
				BillData.put("tid",tid 			);
				BillData.put("resultCode",resultCode 	);
				BillData.put("resultMsg",resultMsg 		);
				BillData.put("payMethod",payMethod 		);
				BillData.put("price1",price1 		);
				BillData.put("price2",price2 		);
				BillData.put("authCode",authCode 		);
				BillData.put("cardQuota",cardQuota 		);
				BillData.put("quotaInterest",quotaInterest 	);
				BillData.put("cardCode",cardCode 		);
				BillData.put("cardIssuerCode",cardIssuerCode );
				BillData.put("authCertain",authCertain 	);
				BillData.put("pgAuthDate",pgAuthDate 	);
				BillData.put("pgAuthTime",pgAuthTime 	);
				BillData.put("ocbSaveAuthCode",ocbSaveAuthCode);
				BillData.put("ocbUseAuthCode",ocbUseAuthCode );
				BillData.put("ocbAuthDate",ocbAuthDate 	);
				BillData.put("eventFlag",eventFlag 		);
				BillData.put("nohpp",nohpp 			);
				BillData.put("noars",noars 			);
				BillData.put("perno",perno 			);
				BillData.put("vacct",vacct 			);
				BillData.put("vcdbank",vcdbank 		);
				BillData.put("dtinput",dtinput 		);
				BillData.put("nminput",nminput 		);
				BillData.put("nmvacct",nmvacct 		);
				BillData.put("moid",moid 			);
				BillData.put("codegw",codegw 		);
				BillData.put("ocbcardnumber",ocbcardnumber 	);
				BillData.put("cultureid",cultureid 		);
				BillData.put("cardNumber",cardNumber 	);
				BillData.put("mid",mid 			);
				BillData.put("goodname",goodname 		);
				BillData.put("price",price 			);
				BillData.put("usePoint",box.get("usePoint") 			);
				String v_ispointuse = box.get("ispointuse");
				if (v_ispointuse == null) 	v_ispointuse ="N";

				BillData.put("ispointuse",	v_ispointuse); //포인트 사용여부


				String v_point = box.get("point");
				if (v_point == null) v_point ="0";
				BillData.put("point",v_point 		); //사용 포인트
				BillData.put("buyername",buyername 		);
				BillData.put("buyertel",buyertel 		);
				BillData.put("buyeremail",buyeremail 	);
				BillData.put("resulterrCode",resulterrCode  );


				BillData.put("v_gubun",v_gubun);
				BillData.put("userid",box.getSession("userid")      );
				BillData.put("usernm",box.getSession("name")      );


				BillData.put("inputname","");
				BillData.put("inputdate","");

				BillData.put("receive",v_receive     );
				BillData.put("phone",v_phone     );
				BillData.put("post1",v_post1     );
				BillData.put("post2",v_post2     );
				BillData.put("addr1",v_addr1     );
				BillData.put("addr2",v_addr2     );
				BillData.put("tem_grcode",box.getSession("tem_grcode")     );
				BillData.put("subjList",box.getQueryArray("p_subj"));

				BillData.put("listgubun", v_listgubun);//수강신청 구분
				BillData.put("subj"     , v_subj);
				BillData.put("year"     , v_year);
				BillData.put("subjseq"  , v_subjseq);
				BillData.put("seq"      , v_seq);

				if(cs.getBoolean("inipay.systemout")) {
					System.out.println("v_gubun 1=" + v_gubun);
					System.out.println("v_gubun 2=" + v_gubun);
					System.out.println("v_tid 				=" + tid 				);
					System.out.println("v_resultCode 	    =" + resultCode 	    );
					System.out.println("v_resultMsg 		=" + resultMsg 		);
					System.out.println("v_payMethod 		=" + payMethod 		);
					System.out.println("v_price1 			=" + price1 			);
					System.out.println("v_price2 			=" + price2 			);
					System.out.println("v_authCode 		    =" + authCode 		    );
					System.out.println("v_cardQuota 		=" + cardQuota 		);
					System.out.println("v_quotaInterest 	=" + quotaInterest 	);
					System.out.println("v_cardCode 		    =" + cardCode 		    );
					System.out.println("v_cardIssuerCode 	=" + cardIssuerCode 	);
					System.out.println("v_authCertain 	    =" + authCertain 	    );
					System.out.println("v_pgAuthDate 		=" + pgAuthDate 		);
					System.out.println("v_pgAuthTime 		=" + pgAuthTime 		);
					System.out.println("v_ocbSaveAuthCode   =" + ocbSaveAuthCode   );
					System.out.println("v_ocbUseAuthCode 	=" + ocbUseAuthCode 	);
					System.out.println("v_ocbAuthDate 	    =" + ocbAuthDate 	    );
					System.out.println("v_eventFlag		    =" + eventFlag		    );
					System.out.println("v_nohpp 			=" + nohpp 			);
					System.out.println("v_noars 			=" + noars 			);
					System.out.println("v_perno 			=" + perno 			);
					System.out.println("v_vacct 			=" + vacct 			);
					System.out.println("v_vcdbank 		    =" + vcdbank 		    );
					System.out.println("v_dtinput 		    =" + dtinput 		    );
					System.out.println("v_nminput 		    =" + nminput 		    );
					System.out.println("v_nmvacct 		    =" + nmvacct 		    );
					System.out.println("v_moid 			    =" + moid 			    );
					System.out.println("v_codegw 			=" + codegw 			);
					System.out.println("v_ocbcardnumber 	=" + ocbcardnumber 	);
					System.out.println("v_cultureid 		=" + cultureid 		);
					System.out.println("v_cardNumber 		=" + cardNumber 		);
					System.out.println("v_mid 			    =" + mid 			    );
					System.out.println("v_goodname 		    =" + goodname 		    );
					System.out.println("v_price	            =" + price	            );
					System.out.println("v_ispointuse		=" + v_ispointuse		);
					System.out.println("v_point 		    =" + v_point 		    );
					System.out.println("v_buyername 	    =" + buyername 	    );
					System.out.println("v_buyertel 	        =" + buyertel 	        );
					System.out.println("v_buyeremail 	    =" + buyeremail 	    );
					System.out.println("v_resulterrCode     =" + resulterrCode     );
					System.out.println("v_gubun             =" + v_gubun            );
					System.out.println("v_userid            =" + box.getSession("userid")            );
					System.out.println("v_usernm            =" + box.getSession("name")            );
					System.out.println("v_billprice         =" + box.get("p_billprice")         );
					System.out.println("tem_grcode         =" + box.getSession("tem_grcode")         );

					System.out.println("v_listgubun         =" + v_listgubun      );
					System.out.println("v_subj              =" + v_subj           );
					System.out.println("v_year              =" + v_year           );
					System.out.println("v_subjseq           =" + v_subjseq        );
					System.out.println("v_seq               =" + v_seq            );
				}

				if (resultCode.equals("00")) {
					//결제결과 저장
					isOk = billProcess(BillData);
				}

				box.putAll(BillData);
				System.out.println("최종결과"+isOk);

				if (isOk==1) {//성공
					resultCode ="00";
					resultMsg ="DB Success";

				} else {//실패
					inipay.SetField("type","cancel"); // 고정
					inipay.SetField("cancelMsg", "DB FAIL"); // 취소사유
					inipay.startAction();

					resultCode ="01";
					resultMsg ="DB FAIL";
				}
			}

		}
		catch(Exception e)
		{
			System.out.println("Exception FOUND");
			System.out.println("err="+e);
			inipay.SetField("type", "cancel"); // 고정
			inipay.SetField("cancelMsg", "DB FAIL"); // 취소사유
			inipay.startAction();

			resultCode ="01";
			resultMsg ="SYSTEM FAIL";

		}

		return isOk;
	}

	/**
	 * 결제처리(결제완료시 장바구니->결제후 장바구니로 DATA 이관)
	 * @param    box          receive from the form object and session
	 * @return ArrayList  자료실 리스트
	 * @throws Exception
	 */
	public int billProcess(Hashtable<String, String> BillData) throws Exception {
		DBConnectionManager connMgr = null;
		PreparedStatement pstmt1 = null;

		int result = 0;
		int isOk1 = 0, isOk2 = 0, isOk3 = 0;

		ListSet ls = null;

		String sql 				="";
		String currentDate	  	= FormatDate.getDate("yyyyMMddHHmmss");

		String 	tid 			= BillData.get("tid"); 					// 거래번호
		String 	resultCode 		= BillData.get("resultCode"); 			// 결과코드 ("00"이면 지불 성공)
		String 	resultMsg 		= BillData.get("resultMsg"); 			// 결과내용 (지불결과에 대한 설명)
		String 	payMethod 		= BillData.get("payMethod"); 			// 지불방법 (매뉴얼 참조)
		String 	payStatus 		= BillData.get("payStatus"); 			// 지불상태
		String 	price1 			= BillData.get("price1"); 					// OK Cashbag 복합결재시 신용카드 지불금액
		String 	price2 			= BillData.get("price2"); 					// OK Cashbag 복합결재시 포인트 지불금액

		if	(price1 == null) price1 ="0";
		if	(price2 == null) price2 ="0";

		price1 ="0";
		price2 ="0";

		String 	authCode 		= BillData.get("authCode"); 			// 신용카드 승인번호
		String 	cardQuota 		= BillData.get("cardQuota");			// 할부기간
		String 	quotaInterest 	= BillData.get("quotaInterest"); 		// 무이자할부 여부 ("1"이면 무이자할부)
		String 	cardCode 		= BillData.get("cardCode"); 			// 신용카드사 코드 (매뉴얼 참조)
		String 	cardIssuerCode 	= BillData.get("cardIssuerCode"); 		// 카드발급사 코드 (매뉴얼 참조)
		String 	authCertain 	= BillData.get("authCertain");			// 본인인증 수행여부 ("00"이면 수행)
		String 	pgAuthDate 		= BillData.get("pgAuthDate"); 			// 이니시스 승인날짜
		String 	pgAuthTime 		= BillData.get("pgAuthTime"); 			// 이니시스 승인시각
		String 	ocbSaveAuthCode = BillData.get("ocbSaveAuthCode"); 		// OK Cashbag 적립 승인번호
		String 	ocbUseAuthCode 	= BillData.get("ocbUseAuthCode"); 		// OK Cashbag 사용 승인번호
		String 	ocbAuthDate 	= BillData.get("ocbAuthDate"); 			// OK Cashbag 승인일시
		String 	eventFlag 		= BillData.get("eventFlag"); 			// 각종 이벤트 적용 여부
		String 	nohpp 			= BillData.get("nohpp"); 				// 휴대폰 결제시 사용된 휴대폰 번호
		String 	noars 			= BillData.get("noars"); 				// 전화결제 시 사용된 전화번호
		String 	perno 			= BillData.get("perno"); 				// 송금자 주민번호
		String 	vacct 			= BillData.get("vacct"); 				// 가상계좌번호
		String 	vcdbank 		= BillData.get("vcdbank"); 				// 입금할 은행코드
		String 	dtinput 		= BillData.get("dtinput"); 				// 입금예정일
		String 	nminput 		= BillData.get("nminput"); 				// 송금자 명
		String 	nmvacct 		= BillData.get("nmvacct"); 				// 예금주 명
		String 	moid 			= BillData.get("moid"); 				// 상점 주문번호
		String 	codegw 			= BillData.get("codegw"); 				// 전화결제 사업자 코드
		String 	ocbcardnumber 	= BillData.get("ocbcardnumber"); 		// OK CASH BAG 결제, 적립인 경우 OK CASH BAG 카드 번호
		String 	cultureid 		= BillData.get("cultureid"); 			// 컬쳐 랜드 ID
		String 	cardNumber 		= BillData.get("cardNumber"); 			// 신용카드번호
		String 	mid 			= BillData.get("mid");					//상점 주문번호
		String 	goodname 		= BillData.get("goodname"); 			//상품명(과정명)
		String 	price 			= BillData.get("price"); 					//결제금액
		String 	ispointuse		= BillData.get("ispointuse"); 					//포인트사용여부
		String 	vpoint			= BillData.get("point"); 					//포인트
		String 	buyername 		= BillData.get("buyername"); 			//구매자 이름
		String 	buyertel 		= BillData.get("buyertel"); 			//구매자 전화번호
		String 	buyeremail 		= BillData.get("buyeremail"); 			//구매자 EMAIL
		String 	resulterrCode 	= BillData.get("resulterrCode");  		//오류 코드

		String 	v_gubun 		= BillData.get("v_gubun");  				//N:일반,P:전문가,B:도서
		String 	userid			= BillData.get("userid");  				//SESSION 사용자 ID
		String 	usernm			= BillData.get("usernm");  				//SESSION 사용자 이름

		String  inputname		= BillData.get("inputname");
		String  inputdate		= BillData.get("inputdate");

		String  receive		= BillData.get("receive");
		String  phone		= BillData.get("phone");
		String  post1		= BillData.get("post1");
		String  post2		= BillData.get("post2");
		String  addr1		= BillData.get("addr1");
		String  addr2		= BillData.get("addr2");
		String  multiyn		= BillData.get("multiyn");
		String  agencyyn	= BillData.get("agencyyn");

		String  v_listgubun	= BillData.get("listgubun"); //수강신청 : PROP; 결제요청 : REQ
		String	v_subj		= BillData.get("subj");
		String	v_year		= BillData.get("year");
		String	v_subjseq	= BillData.get("subjseq");
		String	v_seq		= BillData.get("seq");

		if(cs.getBoolean("inipay.log")) {
			Log.info.println("tid 			="+tid 				+"/");
			Log.info.println("resultCode 		="+resultCode 		+"/");
			Log.info.println("resultMsg 		="+resultMsg 		+"/");
			Log.info.println("payMethod 		="+payMethod 		+"/");
			Log.info.println("price1 			="+price1 			+"/");
			Log.info.println("price2 			="+price2 			+"/");
			Log.info.println("authCode 		="+authCode 		+"/");
			Log.info.println("cardQuota 		="+cardQuota 		+"/");
			Log.info.println("quotaInterest 	="+quotaInterest 	+"/");
			Log.info.println("cardCode 		="+cardCode 		+"/");
			Log.info.println("cardIssuerCode 	="+cardIssuerCode 	+"/");
			Log.info.println("authCertain 	="+authCertain 	    +"/");
			Log.info.println("pgAuthDate 		="+pgAuthDate 		+"/");
			Log.info.println("pgAuthTime 		="+pgAuthTime 		+"/");
			Log.info.println("ocbSaveAuthCode ="+ocbSaveAuthCode  +"/");
			Log.info.println("ocbUseAuthCode 	="+ocbUseAuthCode 	+"/");
			Log.info.println("ocbAuthDate 	="+ocbAuthDate 	    +"/");
			Log.info.println("eventFlag 		="+eventFlag 		+"/");
			Log.info.println("nohpp 			="+nohpp 			+"/");
			Log.info.println("noars 			="+noars 			+"/");
			Log.info.println("perno 			="+perno 			+"/");
			Log.info.println("vacct 			="+vacct 			+"/");
			Log.info.println("vcdbank 		="+vcdbank 		    +"/");
			Log.info.println("dtinput 		="+dtinput 		    +"/");
			Log.info.println("nminput 		="+nminput 		    +"/");
			Log.info.println("nmvacct 		="+nmvacct 		    +"/");
			Log.info.println("moid 			="+moid 			+"/");
			Log.info.println("codegw 			="+codegw 			+"/");
			Log.info.println("ocbcardnumber 	="+ocbcardnumber 	+"/");
			Log.info.println("cultureid 		="+cultureid 		+"/");
			Log.info.println("cardNumber 		="+cardNumber 		+"/");
			Log.info.println("mid 			="+mid 			    +"/");
			Log.info.println("goodname 		="+goodname 		+"/");
			Log.info.println("price 			="+price 			+"/");
			Log.info.println("buyername 		="+buyername 		+"/");
			Log.info.println("buyertel 		="+buyertel 		+"/");
			Log.info.println("buyeremail 		="+buyeremail 		+"/");
			Log.info.println("resulterrCode 	="+resulterrCode 	+"/");

			Log.info.println("v_gubun			="+v_gubun			+"/");
			Log.info.println("userid			="+userid			+"/");
			Log.info.println("usernm			="+usernm			+"/");

			Log.info.println("ispointuse		="+ispointuse	+"/");
			Log.info.println("point			="+vpoint		+"/");

			Log.info.println("v_inputname		="+inputname	+"/");
			Log.info.println("v_inputdate		="+inputdate	+"/");

			Log.info.println("v_receive		="+receive	+"/");
			Log.info.println("v_phone		="+phone	+"/");
			Log.info.println("v_post1		="+post1	+"/");
			Log.info.println("v_post2		="+post2	+"/");
			Log.info.println("v_addr1		="+addr1	+"/");
			Log.info.println("v_addr2		="+addr2	+"/");

			Log.info.println("v_listgubun	="+v_listgubun	+"/");
			Log.info.println("v_subj		="+v_subj	+"/");
			Log.info.println("v_year		="+v_year	+"/");
			Log.info.println("v_subjseq		="+v_subjseq	+"/");
			Log.info.println("v_seq			="+v_seq	+"/");
		}
		String v_grcode   = BillData.get("tem_grcode");
		if(v_grcode.equals("")) {
			v_grcode   ="N000001";
		}

		try
		{
			connMgr = new DBConnectionManager();
			connMgr.setAutoCommit(false);

			sql ="select * from tz_offbillinfo where tid = '" + tid +"'";

			Log.info.println("sql="+sql);
			ls = connMgr.executeQuery(sql);
			if (ls.next()) {
				result = 0;
			} else {
				//결제정보 저장
				sql ="insert into tz_offbillinfo(\n";
				sql+="          tid,				\n"; //01.거래번호
				sql+="          resultcode,		\n"; //02.결과코드
				sql+="          resultmsg,		\n"; //03.결과내용
				sql+="          paymethod,		\n"; //04.지불방법
				sql+="          paystatus,		\n"; //05.결제상태
				sql+="          mid,				\n"; //06.상점 ID
				sql+="          goodname,		\n"; //07.과정명
				sql+="          userid,			\n"; //08.사용자ID
				sql+="          usernm,			\n"; //09.이름
				sql+="          price,			\n"; //10.결제금액
				sql+="          point,			\n"; //11.포인트
				sql+="          buyername,		\n"; //12.구매자 이름
				sql+="          buyertel,		\n"; //13.구매자 전화번호
				sql+="          buyeremail,		\n"; //14.구매자 Email
				sql+="          resulterrcode,	\n"; //15.결과에러코드
				sql+="          price1,			\n"; //16.OkCashBag 신용카드 금액
				sql+="          price2,			\n"; //17.OkCashBag 포인트 금액
				sql+="          authcode,		\n"; //18.신용카드 승인번호
				sql+="          cardquota,		\n"; //19.할부기간
				sql+="          quotainterest,	\n"; //20.무이자 할부여부
				sql+="          cardcode,		\n"; //21.신용카드사 코드
				sql+="          cardissuercode,	\n"; //22.카드발급사 코드
				sql+="          authcertain,		\n"; //23.본인인증 수행여부
				sql+="          pgauthdate,		\n"; //24.이니시스 승인날짜
				sql+="          pgauthtime,		\n"; //25.이니시스 승인시각
				sql+="          ocbcardnumber,	\n"; //26.OKCashB 결제카드번호
				sql+="          ocbsaveauthcode,	\n"; //27.OKCashB 적립승인번호
				sql+="          ocbuseauthcode,	\n"; //28.OKCashB 사용승인번호
				sql+="          ocbauthdate	,	\n"; //29.OKCashB 승인일시
				sql+="          eventflag,		\n"; //30.각종이벤트 적용여부
				sql+="          perno,			\n"; //31.송금자 주민번호
				sql+="          vacct,			\n"; //32.가상계좌번호
				sql+="          vcdbank	,		\n"; //33.입금할 은행코드
				sql+="          dtinput	,		\n"; //34.입금예정일
				sql+="          nminput	,		\n"; //35.송금자명
				sql+="          nmvacct	,		\n"; //36.예금주명
				sql+="          moid,			\n"; //37.상점주문번호
				sql+="          cardnumber,		\n"; //38.신용카드번호
				sql+="          cancelyn,		\n"; //39.취소여부
				sql+="          cancelresult,	\n"; //40.취소결과
				sql+="          canceldate,		\n"; //41.취소날짜
				sql+="          canceltime,		\n"; //42.취소시간
				sql+="          rcashcancelno,	\n"; //43.현금영수증 승인번호
				sql+="          inputname,		\n"; //44.입금자명
				sql+="          inputdate,		\n"; //45.입금예정일
				sql+="          receive	,		\n"; //46.수취인 성명
				sql+="          phone,			\n"; //47.수취인 연락처
				sql+="          post1,			\n"; //48.수취인 우편번호1
				sql+="          post2,			\n"; //49.수취인 우편번호2
				sql+="          addr1,			\n"; //50.수취인 주소1
				sql+="          addr2,			\n"; //51.수취인 주소2
				sql+="          multiyn,			\n"; //52.다중결제여부
				sql+="          agencyyn,		\n"; //53.대행결제여부
				sql+="          luserid,			\n"; //54.최종등록자
				sql+="          ldate)			\n"; //55.최종등록일

				sql+="values(   ?, ?, ?, ?, ?, \n";  //1 ~ 5
				sql+="			?, ?, ?, ?, ?, \n"; 	 //6 ~ 10
				sql+="			?, ?, ?, ?, ?, \n"; 	 //11 ~ 15
				sql+="			?, ?, ?, ?, ?, \n"; 	 //16 ~ 20
				sql+="			?, ?, ?, ?, ?, \n"; 	 //21 ~ 25
				sql+="			?, ?, ?, ?, ?, \n"; 	 //26 ~ 30
				sql+="			?, ?, ?, ?, ?, \n"; 	 //31 ~ 35
				sql+="			?, ?, ?, 'N', NULL, \n";   //36 ~ 40
				sql+="			NULL, NULL, NULL, ?, ?, \n";   //41 ~ 45
				sql+="			?, ?, ?, ?, ?, \n";   //46 ~ 50
				sql+="			?, ?, ?, ?, ?) \n";   //51 ~ 55

				pstmt1 = connMgr.prepareStatement(sql);


				pstmt1.setString( 1, tid); //01.거래번호
				pstmt1.setString( 2, resultCode		); //02.결과코드
				pstmt1.setString( 3, resultMsg		); //03.결과내용
				pstmt1.setString( 4, payMethod		); //04.지불방법
				pstmt1.setString( 5, payStatus		); //05.결제상태
				pstmt1.setString( 6, mid			); //06.상점 ID
				pstmt1.setString( 7, goodname		); //07.과정명
				pstmt1.setString( 8, userid			); //08.사용자ID
				pstmt1.setString( 9, usernm			); //09.이름
				pstmt1.setString(10, price			); //10.결제금액
				pstmt1.setString(11, vpoint			); //11.포인트
				pstmt1.setString(12, buyername		); //12.구매자 이름
				pstmt1.setString(13, buyertel		); //13.구매자 전화번호
				pstmt1.setString(14, buyeremail		); //14.구매자 Email
				pstmt1.setString(15, resulterrCode	); //15.결과에러코드
				pstmt1.setString(16, price1			); //16.OkCashBag 신용카드 금액
				pstmt1.setString(17, price2			); //17.OkCashBag 포인트 금액
				pstmt1.setString(18, authCode		); //18.신용카드 승인번호
				pstmt1.setString(19, cardQuota		); //19.할부기간
				pstmt1.setString(20, quotaInterest	); //20.무이자 할부여부
				pstmt1.setString(21, cardCode		); //21.신용카드사 코드
				pstmt1.setString(22, cardIssuerCode	); //22.카드발급사 코드
				pstmt1.setString(23, authCertain	); //23.본인인증 수행여부
				pstmt1.setString(24, pgAuthDate		); //24.이니시스 승인날짜
				pstmt1.setString(25, pgAuthTime		); //25.이니시스 승인시각
				pstmt1.setString(26, ocbcardnumber	); //26.OKCashB 결제카드번호
				pstmt1.setString(27, ocbSaveAuthCode); //27.OKCashB 적립승인번호
				pstmt1.setString(28, ocbUseAuthCode	); //28.OKCashB 사용승인번호
				pstmt1.setString(29, ocbAuthDate	); //29.OKCashB 승인일시
				pstmt1.setString(30, eventFlag		); //30.각종이벤트 적용여부
				pstmt1.setString(31, perno			); //31.송금자 주민번호
				pstmt1.setString(32, vacct			); //32.가상계좌번호
				pstmt1.setString(33, vcdbank		); //33.입금할 은행코드
				pstmt1.setString(34, dtinput		); //34.입금예정일
				pstmt1.setString(35, nminput		); //35.송금자명
				pstmt1.setString(36, nmvacct		); //36.예금주명
				pstmt1.setString(37, moid			); //37.상점주문번호
				pstmt1.setString(38, cardNumber		); //38.신용카드번호
				pstmt1.setString(39, inputname		); //44.입금자명
				pstmt1.setString(40, inputdate		); //45.입금예정일
				pstmt1.setString(41, receive		); //46.수취인 성명
				pstmt1.setString(42, phone			); //47.수취인 연락처
				pstmt1.setString(43, post1			); //48.수취인 우편번호1
				pstmt1.setString(44, post2			); //49.수취인 우편번호2
				pstmt1.setString(45, addr1			); //50.수취인 주소1
				pstmt1.setString(46, addr2			); //51.수취인 주소2
				pstmt1.setString(47, multiyn		); //52.다중결제여부
				pstmt1.setString(48, agencyyn		); //53.대행결제여부
				pstmt1.setString(49, userid			); //54.최종등록자
				pstmt1.setString(50, currentDate	); //55.최종등록일


				Log.info.println("isOk1  before =" + isOk1);
				isOk1 = pstmt1.executeUpdate();

				Log.info.println("isOk1 =" + isOk1);
				Log.info.println("v_gubun =" + v_gubun);

				if (resulterrCode.equals("170204") || resulterrCode.equals("1979중")) {
					//ISP 이중 결제는 tz_propose에 Update하지 않는다.(코드 170204)
					//겨좌이체 이중 결제는 tz_propose에 Update하지 않는다.(코드 1979중)
					isOk1 = 1;
					isOk2 = 1;
					isOk3 = 1;
					result = 1;
				} else {
					//수강신청 Insert
					Log.info.println("#2");

					//isOk2 = moveBasketBill(connMgr, v_gubun, v_grcode, tid, userid, BillData.get("subjList") )
					//*insertPropose(connMgr, v_gubun, v_grcode, tid, userid, BillData.get("subjList") )
					//*saveUsePoint(connMgr, tid, userid, Integer.parseInt(BillData.get("usePoint")), goodname );
					if ("PROP".equals(v_listgubun)) {
						isOk2 = updatePropose(connMgr, tid, v_subj, v_year, v_subjseq, v_seq, userid, price);
						isOk3 = 1;
					} else if ("REQ".equals(v_listgubun)) {


						//2010.01.22 결제후 처리시 결제요청건에 대해 수강신청 내용이 없어야 정상이지만
						//          있을 경우 에러가 나므로 데이터를 확인함, 이경우에 수강신청내용과 이 결제와는 무관한 결제정보가 될 수 있음
						sql  = "select * from tz_offpropose a ";
						sql += " where userid  = '" + userid +"'";
						sql += "   and seq     = (SELECT MAX(SEQ) FROM tz_offsubjseq WHERE subj = a.subj and year = a.year and subjseq = a.subjseq) ";
						//sql += "   and seq     = '" + v_seq +"'";
						sql += "   and subjseq = '" + v_subjseq +"'";
						sql += "   and year    = '" + v_year +"'";
						sql += "   and subj    = '" + v_subj +"'";

						Log.info.println("sql="+sql);
						ls = connMgr.executeQuery(sql);
						if (ls.next()) {
							isOk2 = 1;
						} else {
							isOk2 = insertPropose(connMgr, tid, v_subj, v_year, v_subjseq, v_seq, userid, price);
						}
						isOk3 = insertBillReq(connMgr, tid, v_subj, v_year, v_subjseq, v_seq, userid);
					}
				}
			}

			Log.info.println("isOk1 =" + isOk1);
			Log.info.println("isOk2 =" + isOk2);
			Log.info.println("isOk3 =" + isOk3);

			ls.close();

			if (isOk1 > 0 && isOk2 > 0 && isOk3 > 0) {
				result = 1;
				connMgr.commit();
			} else {
				result = 0;
				connMgr.rollback();
			}

		}
		catch(Exception ex) {
			result = 0;
			connMgr.rollback();
			ErrorManager.getErrorStackTrace(ex, null, sql);
			Log.info.println("Err =" + ex.getMessage());
			throw new Exception("sql =" + sql +"\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}
	/**
	 * 수강신청 정보 저장
	 * @param    box          receive from the form object and session
	 * @throws Exception
	 */
	private int updatePropose(DBConnectionManager connMgr, String v_tid, String v_subj, String v_year, String v_subjseq, String v_seq, String v_userid, String v_price ) throws Exception {

		StringBuffer sql = new StringBuffer();
		PreparedStatement pstmt2 = null;
		int isOk2 = 0;

		try
		{

			sql.append("UPDATE tz_offpropose A SET \n");
			sql.append("TID = ?, \n");
			sql.append("REALPAYMENT = ?, \n");
			sql.append("LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')\n");
			sql.append("WHERE USERID = ?\n");
			sql.append("AND SEQ = ?\n");
			sql.append("AND SUBJSEQ = ?\n");
			sql.append("AND YEAR = ?\n");
			sql.append("AND SUBJ = ?\n");

			Log.info.println("#3");
			Log.info.println("sql="+sql.toString());
			pstmt2 = connMgr.prepareStatement(sql.toString());
			int i = 1;
			pstmt2.setString(i++, v_tid);
			pstmt2.setString(i++, v_price);
			pstmt2.setString(i++, v_userid);
			pstmt2.setString(i++, v_seq);
			pstmt2.setString(i++, v_subjseq);
			pstmt2.setString(i++, v_year);
			pstmt2.setString(i++, v_subj);

			isOk2 = pstmt2.executeUpdate();

			Log.info.println("#4\nisok =" + isOk2);

		}
		catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql.toString());
			throw new Exception("sql =" + sql +"\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
		}
		return isOk2;
	}
	/**
	 * 결제요청 정보 저장
	 * @param    box          receive from the form object and session
	 * @throws Exception
	 */
	private int insertBillReq(DBConnectionManager connMgr, String v_tid, String v_subj, String v_year, String v_subjseq, String v_seq, String v_userid ) throws Exception {

		StringBuffer sql = new StringBuffer();
		PreparedStatement pstmt2 = null;
		int isOk3 = 0;

		try
		{

			sql.append("UPDATE tz_offbillrequser A SET \n");
			sql.append("TID = ?, \n");
			sql.append("BILLSTATUS = '00', \n");
			sql.append("LDATE = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')\n");
			sql.append("WHERE USERID = ?\n");
			sql.append("AND SEQ = ?\n");
			sql.append("AND SUBJSEQ = ?\n");
			sql.append("AND YEAR = ?\n");
			sql.append("AND SUBJ = ?\n");

			Log.info.println("#5");
			Log.info.println("sql="+sql.toString());
			pstmt2 = connMgr.prepareStatement(sql.toString());
			int i = 1;
			pstmt2.setString(i++, v_tid);
			pstmt2.setString(i++, v_userid);
			pstmt2.setString(i++, v_seq);
			pstmt2.setString(i++, v_subjseq);
			pstmt2.setString(i++, v_year);
			pstmt2.setString(i++, v_subj);

			isOk3 = pstmt2.executeUpdate();

			Log.info.println("#6\nv_tid =" + v_tid);
			Log.info.println("#6\nv_userid =" + v_userid);
			Log.info.println("#6\nv_seq =" + v_seq);
			Log.info.println("#6\nv_subjseq =" + v_subjseq);
			Log.info.println("#6\nv_year =" + v_year);
			Log.info.println("#6\nv_subj =" + v_subj);

			Log.info.println("#6\nisok =" + isOk3);

		}
		catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql.toString());
			throw new Exception("sql =" + sql +"\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
		}
		return isOk3;
	}
	/**
	 * 결제요청 정보 저장 수강신청 테이블에 INSERT
	 * @param    box          receive from the form object and session
	 * @throws Exception
	 */
	private int insertPropose(DBConnectionManager connMgr, String v_tid, String v_subj, String v_year, String v_subjseq, String v_seq, String v_userid, String v_price ) throws Exception {

		StringBuffer sql = new StringBuffer();
		PreparedStatement pstmt2 = null;
		int isOk2 = 0;

		try
		{

			sql.append("INSERT INTO tz_offpropose \n");
			sql.append("(USERID, COMP, JIK, APPDATE, ISDINSERT, CHKFIRST, CHKFINAL, LUSERID, LDATE, PRICE, REALPAYMENT, SUBJ, YEAR, SUBJSEQ, SEQ, TID) \n");
			sql.append("SELECT \n");
			sql.append("?,\n");
			sql.append("(SELECT COMP FROM tz_member WHERE USERID = ?),\n");
			sql.append("(SELECT JIKUP FROM tz_member WHERE USERID = ?),\n");
			sql.append("TO_CHAR(sysdate,'yyyymmddhh24miss'),\n");
			sql.append("'Y',\n");
			sql.append("'Y',\n");
			sql.append("'U',\n");
			sql.append("?,\n");
			sql.append("TO_CHAR(sysdate,'yyyymmddhh24miss'),\n");
			sql.append("BIYONG,\n");
			sql.append("?,\n");
			sql.append("SUBJ,\n");
			sql.append("YEAR,\n");
			sql.append("SUBJSEQ,\n");
			sql.append("(SELECT MAX(SEQ) FROM tz_offsubjseq WHERE subj = a.subj and year = a.year and subjseq = a.subjseq),\n");
			sql.append("?\n");
			sql.append("FROM tz_offbillreq a\n");
			sql.append("WHERE SEQ = ?\n");
			sql.append("AND SUBJSEQ = ?\n");
			sql.append("AND YEAR = ?\n");
			sql.append("AND SUBJ = ?\n");

			Log.info.println("#5");
			Log.info.println("sql="+sql.toString());
			pstmt2 = connMgr.prepareStatement(sql.toString());
			int i = 1;
			pstmt2.setString(i++, v_userid);
			pstmt2.setString(i++, v_userid);
			pstmt2.setString(i++, v_userid);
			pstmt2.setString(i++, v_userid);
			pstmt2.setString(i++, v_price);
			pstmt2.setString(i++, v_tid);
			pstmt2.setString(i++, v_seq);
			pstmt2.setString(i++, v_subjseq);
			pstmt2.setString(i++, v_year);
			pstmt2.setString(i++, v_subj);

			isOk2 = pstmt2.executeUpdate();

			Log.info.println("#6\nisok =" + isOk2);

		}
		catch(Exception ex) {
			ErrorManager.getErrorStackTrace(ex, null, sql.toString());
			throw new Exception("sql =" + sql +"\r\n" + ex.getMessage());
		}
		finally {
			if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e) {} }
		}
		return isOk2;
	}

	/**
	 * off-line 결제 정보 조회
      @param box      receive from the form object and session
      @return ArrayList
	 */
	public ArrayList selectOffSecurePayInfo(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		DataBox dbox                = null;
		ListSet ls                  = null;
		ArrayList list              = null;
		String sql                  = "";

		String	v_tid    	= box.getString("tid");

		try {
			connMgr = new DBConnectionManager();
			list = new ArrayList();

			sql  = "SELECT tid, paymethod, cardnumber, pgauthdate, pgauthtime, authcode ";
			sql += "  FROM tz_offbillinfo ";
			sql += " WHERE tid = "+SQLString.Format(v_tid);

			ls = connMgr.executeQuery(sql);
			while (ls.next()) {
				dbox = ls.getDataBox();

				list.add(dbox);
			}

		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, sql);
			throw new Exception("sql = " + sql + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) { try { ls.close(); }catch (Exception e) {} }
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return list;

	}
}