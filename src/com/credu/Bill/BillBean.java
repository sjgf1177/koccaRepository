//**********************************************************
//1. 제	  목: SUBJECT INFORMATION USER BEAN
//2. 프로그램명: ProposeCourseBean.java
//3. 개	  요: 과정안내 사용자 bean
//4. 환	  경: JDK 1.3
//5. 버	  젼: 1.0
//6. 작	  성: 2004.01.14
//7. 수	  정:
//**********************************************************
package com.credu.Bill;

import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;

import com.credu.library.ConfigSet;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.ListSet;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.inicis.inipay.INIpay50;

@SuppressWarnings("unchecked")
public class BillBean {
    ConfigSet cs = null;

    /**
     * 무료과정 결제정보 처리부분
     * 
     * @param box
     * @return
     * @throws Exception
     */
    public int billStartZero(RequestBox box) throws Exception {

        cs = new ConfigSet();

        int isOk = 0;

        if (box.getSession("userid").equals("")) {

            isOk = 0;

        } else {

            try {

                Date today = new Date();
                SimpleDateFormat d1 = new SimpleDateFormat("yyyyMMddHHmmss");
                SimpleDateFormat d2 = new SimpleDateFormat("yyyyMMdd");
                String now = d1.format(today);
                String pgAuthDate = d2.format(today);

                String v_tid = box.getSession("userid") + "_" + now + System.currentTimeMillis();
                String v_grcode = box.getSession("tem_grcode");
                String v_gubun = box.get("p_gubun").equals("") ? "N" : box.get("p_gubun");
                String v_paymethod = "FreePay";
                String v_goodname = box.get("goodname").equals("") ? box.getSession("goodname") : box.get("goodname");
                String v_userid = box.getSession("userid");
                String v_usernm = box.getSession("name");
                String v_price = box.get("price");
                String v_point = box.get("point");
                if (v_point == null || v_point.equals("")) {
                    v_point = "0";
                }
                String v_ispointuse = box.get("ispointuse");
                if (v_ispointuse == null || v_ispointuse.equals("")) {
                    v_ispointuse = "N";
                }
                String v_usepoint = box.get("usePoint");
                if (v_usepoint == null || v_usepoint.equals("")) {
                    v_usepoint = "0";
                }
                String v_buyername = box.get("buyername").equals("") ? box.getSession("name") : box.get("buyername");
                String v_buyertel = box.get("buyertel").equals("") ? box.getSession("buyertel") : box.get("buyertel");
                String v_buyeremail = box.get("buyeremail").equals("") ? box.getSession("buyeremail") : box.get("buyeremail");
                String v_receive = box.get("p_receive");
                String v_phone = box.get("p_phone");
                String v_post1 = box.get("p_post1");
                String v_post2 = box.get("p_post2");
                String v_addr1 = box.get("p_addr1");
                String v_addr2 = box.get("p_addr2");
                String v_mid = cs.getProperty("inipay.mid.real");

                Hashtable BillData = new Hashtable();

                BillData.clear();

                /*
                 * if (v_grcode.equals("N000001")) {
                 * //==================================================== //
                 * 개인정보 복호화 /* SeedCipher seed = new SeedCipher();
                 * //v_buyeremail =
                 * seed.decryptAsString(Base64.decode(v_buyeremail),
                 * seed.key.getBytes(), "UTF-8"); //v_buyertel =
                 * seed.decryptAsString(Base64.decode(v_buyertel),
                 * seed.key.getBytes(), "UTF-8"); //v_phone =
                 * seed.decryptAsString(Base64.decode(v_phone),
                 * seed.key.getBytes(), "UTF-8"); //v_addr2 =
                 * seed.decryptAsString(Base64.decode(v_addr2),
                 * seed.key.getBytes(), "UTF-8");
                 * 
                 * EncryptUtil encryptUtil = new EncryptUtil(Constants.APP_KEY,
                 * Constants.APP_IV); v_buyeremail =
                 * encryptUtil.decrypt(v_buyeremail); v_buyertel =
                 * encryptUtil.decrypt(v_buyertel); v_phone =
                 * encryptUtil.decrypt(v_phone); v_addr2 =
                 * encryptUtil.decrypt(v_addr2);
                 * //==================================================== }
                 */

                BillData.put("tid", v_tid);
                BillData.put("resultCode", "00");
                BillData.put("payMethod", v_paymethod);
                BillData.put("goodname", v_goodname);
                BillData.put("price", v_price);
                BillData.put("usePoint", v_usepoint);
                BillData.put("ispointuse", v_ispointuse);
                BillData.put("point", v_point);
                BillData.put("buyername", v_buyername);
                BillData.put("buyertel", v_buyertel);
                BillData.put("buyeremail", v_buyeremail);
                BillData.put("v_gubun", v_gubun);
                BillData.put("userid", v_userid);
                BillData.put("usernm", v_usernm);
                BillData.put("receive", v_receive);
                BillData.put("phone", v_phone);
                BillData.put("post1", v_post1);
                BillData.put("post2", v_post2);
                BillData.put("addr1", v_addr1);
                BillData.put("addr2", v_addr2);
                BillData.put("tem_grcode", v_grcode);
                BillData.put("subjList", box.getQueryArray("p_subj"));
                BillData.put("p_dis", box.get("p_dis"));
                BillData.put("subjKeyList", box.getQueryArray("p_subj") + "_" + box.getQueryArray("p_subjseq") + "_" + box.getQueryArray("p_year"));
                BillData.put("mid", v_mid);
                BillData.put("pgAuthDate", pgAuthDate);

                BillData.put("inputname", "");
                BillData.put("inputdate", "");
                BillData.put("resultMsg", "");
                BillData.put("price1", "");
                BillData.put("price2", "");
                BillData.put("authCode", "");
                BillData.put("cardQuota", "");
                BillData.put("quotaInterest", "");
                BillData.put("cardCode", "");
                BillData.put("cardIssuerCode", "");
                BillData.put("authCertain", "");
                BillData.put("pgAuthTime", "");
                BillData.put("ocbSaveAuthCode", "");
                BillData.put("ocbUseAuthCode", "");
                BillData.put("ocbAuthDate", "");
                BillData.put("eventFlag", "");
                BillData.put("nohpp", "");
                BillData.put("noars", "");
                BillData.put("perno", "");
                BillData.put("vacct", "");
                BillData.put("vcdbank", "");
                BillData.put("dtinput", "");
                BillData.put("nminput", "");
                BillData.put("nmvacct", "");
                BillData.put("moid", "");
                BillData.put("codegw", "");
                BillData.put("ocbcardnumber", "");
                BillData.put("cultureid", "");
                BillData.put("cardNumber", "");
                BillData.put("resulterrCode", "");

                // 결제정보 저장
                isOk = billProcess(BillData);

            } catch (Exception ex) {
                System.out.println("Exception Error => " + ex.toString());
            }

        }

        return isOk;
    }

    /**
     * 유료과정 결제처리 부분
     * 
     * @param box
     * @return
     * @throws Exception
     */
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

        /************************************************************************************
         * 하단의 PGID 부분은 지불수단별로 TID를 별도로 표시하도록 하며, * 임의로 수정하여 발생된 문제에 대해서는
         * (주)이니시스에 책임이 * 없으니 소스 수정시 주의를 바랍니다 *
         ************************************************************************************/

        String pgid = cs.getProperty("inipay.pgId." + box.get("paymethod"));
        if (pgid == null)
            pgid = box.get("paymethod");

        inipay.SetField("inipayhome", cs.getProperty("inipay.inipayHome")); // INIpay Home (절대경로로 적절히 수정) - C:/INIpay41_JAVA
        inipay.SetField("admin", cs.getProperty("inipay.keyPw")); // 키패스워드(상점아이디에 따라 변경) - 1111
        inipay.SetField("keypw", cs.getProperty("inipay.keyPw")); // 키패스워드(상점아이디에 따라 변경) - 1111
        inipay.SetField("type", cs.getProperty("inipay.type")); // 고정 - securepay
        inipay.SetField("pgid", cs.getProperty("inipay.pgId") + pgid); // 고정 - INIpay
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
        String mid = null;
        //		String connectedUrl = box.get("request.serverName");
        //		String[]testBillList = cs.getProperty("inipay.testPay.list").split("|");

        mid = cs.getProperty("inipay.mid.real"); // 실제 상점아이디

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
         * 이니시스 조회 페이지(https://iniweb.inicis.com)에서 보이는 한글이 깨지는 경우 아래 3줄 중 하나를
         * 사용(주석해제)하여 한글이 제대로 보이는 것을 사용하십시오.
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

        /* 공통필드, 모든 지불수단에 공통 */
        String tid = inipay.GetResult("tid"); // 거래번호
        String resultCode = inipay.GetResult("ResultCode"); // 결과코드 ("00"이면 지불 성공)
        String resultMsg = inipay.GetResult("ResultMsg"); // 결과내용 (지불결과에 대한 설명)
        String moid = inipay.GetResult("MOID"); // 상점주문번호. 결제 요청시 "oid" 필드에 설정된값
        String pgAuthDate = inipay.GetResult("ApplDate"); // 이니시스 승인날짜
        String pgAuthTime = inipay.GetResult("ApplTime"); // 이니시스 승인시각
        String authCode = inipay.GetResult("ApplNum"); // 신용카드 승인번호/결제승인 번호. OCB Point/VBank를 제외한 지불수단에 모두 존재
        String payMethod = inipay.GetResult("PayMethod"); // 지불방법 (매뉴얼 참조)
        //todo: => 원상품가격과 결제결과금액과 비교하여 금액이 동일하지 않다면 결제 금액의 위변조가 의심됨으로 정상적인 처리가 되지 않도록 처리 바랍니다.(거래취소처리)
        //String totPrice 			= inipay.GetResult("TotPrice"); // 결제결과 금액
        String eventFlag = inipay.GetResult("EventCode"); // 각종 이벤트 적용 여부 => 카드 할부 및 행사 적용 코드.[별첨정보 참조***매뉴얼을 의미]

        /* 지불수단 : 카드결제(Card, VCard 공통) */
        String cardNumber = inipay.GetResult("CARD_Num"); // 신용카드번호
        String quotaInterest = inipay.GetResult("CARD_Interest"); // 할부 여부 ("1"이면 무이자할부)
        String cardQuota = inipay.GetResult("CARD_Quota"); // 할부기간
        String cardCode = inipay.GetResult("CARD_Code"); // 신용카드사 코드 (매뉴얼 참조)
        String cardIssuerCode = inipay.GetResult("CARD_BankCode"); // 카드발급사 코드 (매뉴얼 참조)
        //카드사 직발행 카드가 아닌 계열카드인 경우, 2자리 신용카드사코드와 더블어 자세한 카드정보를 나타냅니다(직발행 카드인 경우 "00"으로 반환됩니다.)
        String ocbcardnumber = inipay.GetResult("OCB_Num"); // OK CASH BAG 결제, 적립인 경우 OK CASH BAG 카드 번호
        String ocbSaveAuthCode = inipay.GetResult("OCB_SaveApplNum"); // OK Cashbag 적립 승인번호
        String ocbUseAuthCode = inipay.GetResult("OCB_PayApplNum"); // OK Cashbag 사용 승인번호
        String ocbAuthDate = inipay.GetResult("OCB_ApplDate"); // OK Cashbag 승인일시
        //		String ocbPayPrice = inipay.GetResult("OCB_PayPrice");//OK Cashbag 적립 및 사용내역, 포인트지불금액
        String price2 = inipay.GetResult("OCB_PayPrice"); // OK Cashbag 복합결재시 포인트 지불금액
        String price1 = inipay.GetResult("Price1"); // OK Cashbag 복합결재시 신용카드 지불금액 ---> 사용안함

        /* 지불수단 : 실시간계좌이체(DirectBank) */
        //String acctBankCode		= inipay.GetResult("ACCT_BankCode");//은행코드, [별첨정보 참조***매뉴얼을 의미] String[2]
        //String cshrResultCode	= inipay.GetResult("CSHR_ResultCode");// 현금영수증, 발급결과코드 String[10]
        //String cshrType				= inipay.GetResult("CSHR_Type");//현금영수증, 발급구분코드

        /* 지불수단 : 무통장입금 or 가상계좌(VBank) */
        String vacct = inipay.GetResult("VACT_Num"); // 가상계좌번호
        String vcdbank = inipay.GetResult("VACT_BankCode"); // 입금할 은행코드
        String nmvacct = inipay.GetResult("VACT_Name"); // 예금주 명
        String nminput = inipay.GetResult("VACT_InputName"); // 송금자 명
        String perno = inipay.GetResult("VACT_RegNum"); // 송금자 주민번호
        String dtinput = inipay.GetResult("VACT_Date"); // 입금예정일
        //String dtinputTime 		= inipay.GetResult("VACT_Time"); // 입금예정시간

        /* 지불수단 : 핸드폰, 전화 결제(HPP) */
        String nohpp = inipay.GetResult("HPP_Num"); // 휴대폰 결제시 사용된 휴대폰 번호
        String codegw = inipay.GetResult("HPP_GWCode"); // 전화결제 사업자 코드, 핸드폰, 전화 결제 결과 데이터( "실패 내역 자세히 보기"에서 필요 , 상점에서는 필요없는 정보임)

        /* 지불수단 : 거는 전화결제 - 핸드폰 결제 결과 데이터(Ars1588Bill) */
        String noars = inipay.GetResult("ARSB_Num"); // 전화결제 시 사용된 전화번호

        //* 10. 문화 상품권 결제 결과 데이터
        String cultureid = inipay.GetResult("CULT_UserID"); // 컬쳐 랜드 ID
        //* 12.틴캐시 잔액 데이터
        // 틴캐쉬 결제수단을 이용시에만  결제 결과 내용
        //		String RemainPrice 		= inipay.GetResult("RemainPrice");		//틴캐쉬결제후 잔액
        //*    inipay.GetResult("TEEN_Remains")
        //*  틴캐시 ID : inipay.GetResult("TEEN_UserID")
        //* 13.게임문화 상품권
        //*	사용 카드 갯수 : inipay.GetResult("GAMG_Cnt")
        //* 14.도서문화 상품권
        //*	사용자 ID : inipay.GetResult("BCSH_UserID")

        String authCertain = inipay.GetResult("AuthCertain"); // 본인인증 수행여부 ("00"이면 수행)

        String goodname = box.get("goodname");
        String price = box.get("price");
        String buyername = box.get("buyername");
        String buyertel = box.get("buyertel");
        String buyeremail = box.get("buyeremail");

        //N:일반과정, P:전문가과정, B:도서
        String v_gubun = box.get("p_gubun");

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

        String resulterrCode = "";

        if (resultMsg.indexOf("]") != -1) {
            resulterrCode = resultMsg.substring(1, resultMsg.indexOf("]"));
        }

        System.out.println("resulterrCode =" + resulterrCode);
        /*-------------------------------------------------------------------------*/

        /*******************************************************************
         * 5. 강제취소 * * 지불 결과를 DB 등에 저장하거나 기타 작업을 수행하다가 실패하는 * 경우, 아래의 코드를 참조하여
         * 이미 지불된 거래를 취소하는 코드를 * 작성합니다. *
         *******************************************************************/
        try {
            //			System.out.println("#1");

            // DB CODE HERE
            if (box.getSession("userid").equals("")) {
                // 세션이 없으면 지불취소
                inipay.SetField("type", "cancel"); // 고정
                inipay.SetField("cancelMsg", "NO SESSION"); // 취소사유
                inipay.startAction();

                resultCode = "01";
                resultMsg = "NO SESSION";

            } else {
                //HashTable 저장

                Hashtable BillData = new Hashtable();

                BillData.clear();
                BillData.put("tid", tid);
                BillData.put("resultCode", resultCode);
                BillData.put("resultMsg", resultMsg);
                BillData.put("payMethod", payMethod);
                BillData.put("price1", price1);
                BillData.put("price2", price2);
                BillData.put("authCode", authCode);
                BillData.put("cardQuota", cardQuota);
                BillData.put("quotaInterest", quotaInterest);
                BillData.put("cardCode", cardCode);
                BillData.put("cardIssuerCode", cardIssuerCode);
                BillData.put("authCertain", authCertain);
                BillData.put("pgAuthDate", pgAuthDate);
                BillData.put("pgAuthTime", pgAuthTime);
                BillData.put("ocbSaveAuthCode", ocbSaveAuthCode);
                BillData.put("ocbUseAuthCode", ocbUseAuthCode);
                BillData.put("ocbAuthDate", ocbAuthDate);
                BillData.put("eventFlag", eventFlag);
                BillData.put("nohpp", nohpp);
                BillData.put("noars", noars);
                BillData.put("perno", perno);
                BillData.put("vacct", vacct);
                BillData.put("vcdbank", vcdbank);
                BillData.put("dtinput", dtinput);
                BillData.put("nminput", nminput);
                BillData.put("nmvacct", nmvacct);
                BillData.put("moid", moid);
                BillData.put("codegw", codegw);
                BillData.put("ocbcardnumber", ocbcardnumber);
                BillData.put("cultureid", cultureid);
                BillData.put("cardNumber", cardNumber);
                BillData.put("mid", mid);
                BillData.put("goodname", goodname);
                BillData.put("price", price);
                BillData.put("usePoint", box.get("usePoint"));
                String v_ispointuse = box.get("ispointuse");
                if (v_ispointuse == null)
                    v_ispointuse = "N";

                BillData.put("ispointuse", v_ispointuse); //포인트 사용여부

                String v_point = box.get("point");
                if (v_point == null)
                    v_point = "0";
                BillData.put("point", v_point); //사용 포인트
                BillData.put("buyername", buyername);
                BillData.put("buyertel", buyertel);
                BillData.put("buyeremail", buyeremail);
                BillData.put("resulterrCode", resulterrCode);

                BillData.put("v_gubun", v_gubun);
                BillData.put("userid", box.getSession("userid"));
                BillData.put("usernm", box.getSession("name"));

                BillData.put("inputname", "");
                BillData.put("inputdate", "");

                BillData.put("receive", v_receive);
                BillData.put("phone", v_phone);
                BillData.put("post1", v_post1);
                BillData.put("post2", v_post2);
                BillData.put("addr1", v_addr1);
                BillData.put("addr2", v_addr2);
                BillData.put("tem_grcode", box.getSession("tem_grcode"));
                BillData.put("subjList", box.getQueryArray("p_subj"));
                BillData.put("p_dis", box.get("p_dis"));
                BillData.put("subjKeyList", box.getQueryArray("p_subj") + "_" + box.getQueryArray("p_subjseq") + "_" + box.getQueryArray("p_year"));

                if (cs.getBoolean("inipay.systemout")) {
                    System.out.println("v_gubun 1=" + v_gubun);
                    System.out.println("v_gubun 2=" + v_gubun);
                    System.out.println("v_tid 				=" + tid);
                    System.out.println("v_resultCode 	    =" + resultCode);
                    System.out.println("v_resultMsg 		=" + resultMsg);
                    System.out.println("v_payMethod 		=" + payMethod);
                    System.out.println("v_price1 			=" + price1);
                    System.out.println("v_price2 			=" + price2);
                    System.out.println("v_authCode 		    =" + authCode);
                    System.out.println("v_cardQuota 		=" + cardQuota);
                    System.out.println("v_quotaInterest 	=" + quotaInterest);
                    System.out.println("v_cardCode 		    =" + cardCode);
                    System.out.println("v_cardIssuerCode 	=" + cardIssuerCode);
                    System.out.println("v_authCertain 	    =" + authCertain);
                    System.out.println("v_pgAuthDate 		=" + pgAuthDate);
                    System.out.println("v_pgAuthTime 		=" + pgAuthTime);
                    System.out.println("v_ocbSaveAuthCode   =" + ocbSaveAuthCode);
                    System.out.println("v_ocbUseAuthCode 	=" + ocbUseAuthCode);
                    System.out.println("v_ocbAuthDate 	    =" + ocbAuthDate);
                    System.out.println("v_eventFlag		    =" + eventFlag);
                    System.out.println("v_nohpp 			=" + nohpp);
                    System.out.println("v_noars 			=" + noars);
                    System.out.println("v_perno 			=" + perno);
                    System.out.println("v_vacct 			=" + vacct);
                    System.out.println("v_vcdbank 		    =" + vcdbank);
                    System.out.println("v_dtinput 		    =" + dtinput);
                    System.out.println("v_nminput 		    =" + nminput);
                    System.out.println("v_nmvacct 		    =" + nmvacct);
                    System.out.println("v_moid 			    =" + moid);
                    System.out.println("v_codegw 			=" + codegw);
                    System.out.println("v_ocbcardnumber 	=" + ocbcardnumber);
                    System.out.println("v_cultureid 		=" + cultureid);
                    System.out.println("v_cardNumber 		=" + cardNumber);
                    System.out.println("v_mid 			    =" + mid);
                    System.out.println("v_goodname 		    =" + goodname);
                    System.out.println("v_price	            =" + price);
                    System.out.println("v_ispointuse		=" + v_ispointuse);
                    System.out.println("v_point 		    =" + v_point);
                    System.out.println("v_buyername 	    =" + buyername);
                    System.out.println("v_buyertel 	        =" + buyertel);
                    System.out.println("v_buyeremail 	    =" + buyeremail);
                    System.out.println("v_resulterrCode     =" + resulterrCode);
                    System.out.println("v_gubun             =" + v_gubun);
                    System.out.println("v_userid            =" + box.getSession("userid"));
                    System.out.println("v_usernm            =" + box.getSession("name"));
                    System.out.println("v_billprice         =" + box.get("p_billprice"));
                    System.out.println("tem_grcode         =" + box.getSession("tem_grcode"));
                }

                if (resultCode.equals("00")) {
                    //결제결과 저장
                    isOk = billProcess(BillData);
                }

                box.putAll(BillData);
                System.out.println("최종결과" + isOk);

                if (isOk == 1) {//성공
                    resultCode = "00";
                    resultMsg = "DB Success";
                } else {//실패
                    inipay.SetField("type", "cancel"); // 고정
                    inipay.SetField("cancelMsg", "DB FAIL"); // 취소사유
                    inipay.startAction();

                    resultCode = "01";
                    resultMsg = "DB FAIL";
                }
            }

        } catch (Exception e) {
            System.out.println("Exception FOUND");
            System.out.println("err=" + e);
            inipay.SetField("type", "cancel"); // 고정
            inipay.SetField("cancelMsg", "DB FAIL"); // 취소사유
            inipay.startAction();

            resultCode = "01";
            resultMsg = "SYSTEM FAIL";
        }

        return isOk;
    }

    /**
     * 결제처리(결제완료시 장바구니->결제후 장바구니로 DATA 이관)
     * 
     * @param box receive from the form object and session
     * @return ArrayList 자료실 리스트
     * @throws Exception
     */
    public int billProcess(Hashtable<String, String> BillData) throws Exception {
        DBConnectionManager connMgr = null;
        PreparedStatement pstmt1 = null;

        int result = 0;
        int isOk1 = 0, isOk2 = 0;

        ListSet ls = null;

        String sql = "";
        String currentDate = FormatDate.getDate("yyyyMMddHHmmss");

        String tid = BillData.get("tid"); // 거래번호
        String resultCode = BillData.get("resultCode"); // 결과코드 ("00"이면 지불 성공)
        String resultMsg = BillData.get("resultMsg"); // 결과내용 (지불결과에 대한 설명)
        String payMethod = BillData.get("payMethod"); // 지불방법 (매뉴얼 참조)
        String price1 = BillData.get("price1"); // OK Cashbag 복합결재시 신용카드 지불금액
        String price2 = BillData.get("price2"); // OK Cashbag 복합결재시 포인트 지불금액

        if (price1 == null)
            price1 = "0";
        if (price2 == null)
            price2 = "0";

        price1 = "0";
        price2 = "0";

        String authCode = BillData.get("authCode"); // 신용카드 승인번호
        String cardQuota = BillData.get("cardQuota"); // 할부기간
        String quotaInterest = BillData.get("quotaInterest"); // 무이자할부 여부 ("1"이면 무이자할부)
        String cardCode = BillData.get("cardCode"); // 신용카드사 코드 (매뉴얼 참조)
        String cardIssuerCode = BillData.get("cardIssuerCode"); // 카드발급사 코드 (매뉴얼 참조)
        String authCertain = BillData.get("authCertain"); // 본인인증 수행여부 ("00"이면 수행)
        String pgAuthDate = BillData.get("pgAuthDate"); // 이니시스 승인날짜
        String pgAuthTime = BillData.get("pgAuthTime"); // 이니시스 승인시각
        String ocbSaveAuthCode = BillData.get("ocbSaveAuthCode"); // OK Cashbag 적립 승인번호
        String ocbUseAuthCode = BillData.get("ocbUseAuthCode"); // OK Cashbag 사용 승인번호
        String ocbAuthDate = BillData.get("ocbAuthDate"); // OK Cashbag 승인일시
        String eventFlag = BillData.get("eventFlag"); // 각종 이벤트 적용 여부
        // String 	nohpp 			= BillData.get("nohpp"); 				// 휴대폰 결제시 사용된 휴대폰 번호
        // String 	noars 			= BillData.get("noars"); 				// 전화결제 시 사용된 전화번호
        String perno = BillData.get("perno"); // 송금자 주민번호
        String vacct = BillData.get("vacct"); // 가상계좌번호
        String vcdbank = BillData.get("vcdbank"); // 입금할 은행코드
        String dtinput = BillData.get("dtinput"); // 입금예정일
        String nminput = BillData.get("nminput"); // 송금자 명
        String nmvacct = BillData.get("nmvacct"); // 예금주 명
        String moid = BillData.get("moid"); // 상점 주문번호
        // String codegw = BillData.get("codegw"); // 전화결제 사업자 코드
        String ocbcardnumber = BillData.get("ocbcardnumber"); // OK CASH BAG 결제, 적립인 경우 OK CASH BAG 카드 번호
        // String cultureid = BillData.get("cultureid"); // 컬쳐 랜드 ID
        String cardNumber = BillData.get("cardNumber"); // 신용카드번호
        String mid = BillData.get("mid"); //상점 주문번호
        String goodname = BillData.get("goodname"); //상품명(과정명)
        String price = BillData.get("price"); //결제금액

        if (price == null || price.equals("")) {
            price = "0";
        }

        // String ispointuse = BillData.get("ispointuse"); //포인트사용여부
        String vpoint = BillData.get("point"); //포인트
        String buyername = BillData.get("buyername"); //구매자 이름
        String buyertel = BillData.get("buyertel"); //구매자 전화번호
        String buyeremail = BillData.get("buyeremail"); //구매자 EMAIL
        String resulterrCode = BillData.get("resulterrCode"); //오류 코드

        String v_gubun = BillData.get("v_gubun"); //N:일반,P:전문가,B:도서
        String userid = BillData.get("userid"); //SESSION 사용자 ID
        String usernm = BillData.get("usernm"); //SESSION 사용자 이름

        String inputname = BillData.get("inputname");
        String inputdate = BillData.get("inputdate");

        String receive = BillData.get("receive");
        String phone = BillData.get("phone");
        String post1 = BillData.get("post1");
        String post2 = BillData.get("post2");
        String addr1 = BillData.get("addr1");
        String addr2 = BillData.get("addr2");

        String v_grcode = BillData.get("tem_grcode");
        if (v_grcode.equals("")) {
            v_grcode = "N000001";
        }

        try {
            connMgr = new DBConnectionManager();
            connMgr.setAutoCommit(false);

            sql = "select * from tz_billinfo where grcode = '" + v_grcode + "' and tid = '" + tid + "'";

            ls = connMgr.executeQuery(sql);
            if (ls.next()) {
                result = 0;
            } else {
                //결제정보 저장
                sql = "insert into tz_billinfo(\n";
                sql += "			grcode,			\n"; //01.grcode
                sql += "			tid,			\n"; //01.거래번호 무료:id_시간
                sql += "			resultcode,     \n"; //02.결과코드
                sql += "			resultmsg,      \n"; //03.결과내용
                sql += "			paymethod,      \n"; //04.지불방법
                sql += "			mid,            \n"; //05.상점 ID
                sql += "			goodname,       \n"; //06.과정명
                sql += "			userid,         \n"; //10.사용자ID
                sql += "			usernm,         \n"; //11.이름
                sql += "			price,          \n"; //12.결제금액
                sql += "			buyername,      \n"; //13.구매자 이름
                sql += "			buyertel,       \n"; //14.구매자 전화번호
                sql += "			buyeremail,     \n"; //15.구매자 Email
                sql += "			resulterrcode,  \n"; //16.결과에러코드
                sql += "			price1,         \n"; //17.OkCashBag 신용카드 금액
                sql += "			price2,         \n"; //18.OkCashBag 포인트 금액
                sql += "			authcode,		\n"; //19.신용카드 승인번호
                sql += "			cardquota,      \n"; //20.할부기간
                sql += "			quotainterest,  \n"; //21.무이자 할부여부
                sql += "			cardcode,       \n"; //22.신용카드사 코드
                sql += "			cardissuercode, \n"; //23.카드발급사 코드
                sql += "			authcertain,    \n"; //24.본인인증 수행여부
                sql += "			pgauthdate,     \n"; //25.이니시스 승인날짜
                sql += "			pgauthtime,     \n"; //26.이니시스 승인시각
                sql += "			ocbsaveauthcode,\n"; //27.OKCashB 적립승인번호
                sql += "			ocbuseauthcode, \n"; //28.OKCashB 사용승인번호
                sql += "			ocbauthdate,    \n"; //29.OKCashB 승인일시
                sql += "			eventflag,      \n"; //30.각종이벤트 적용여부
                sql += "			perno,          \n"; //31.송금자 주민번호
                sql += "			vacct,          \n"; //32.가상계좌번호
                sql += "			vcdbank,        \n"; //33.입금할 은행코드
                sql += "			dtinput,        \n"; //34.입금예정일
                sql += "			nminput,        \n"; //35.송금자명
                sql += "			nmvacct,        \n"; //36.예금주명
                sql += "			moid,           \n"; //37.상점주문번호
                sql += "			ocbcardnumber,  \n"; //38.OKCashB 결제카드번호
                sql += "			cardNumber,     \n"; //39.신용카드번호
                sql += "			cancelyn,       \n"; //40.취소여부
                sql += "			cancelresult,   \n"; //41.취소결과
                sql += "			canceldate,     \n"; //42.취소날짜
                sql += "			canceltime,     \n"; //43.취소시간
                sql += "			rcashcancelno,  \n"; //44.현금영수증 승인번호
                sql += "			luserid,        \n"; //45.등록자
                sql += "			ldate,          \n"; //46.등록시간
                sql += "			point,			\n"; //47. 포인트
                sql += "			gubun,			\n"; //48. 구분(N:일반,P:패키지,B:도서)
                sql += "			inputname,		\n"; //49. 입금자명
                sql += "			inputdate,		\n"; //50. 입금예정일
                sql += "			receive,		\n"; //51. 수취인 성명
                sql += "			phone,			\n"; //52. 수취인 연락처
                sql += "			post1,			\n"; //53. 수취인 우편번호1
                sql += "			post2,			\n"; //54. 수취인 우편번호2
                sql += "			addr1,			\n"; //55. 수취인 주소1
                sql += "			addr2,discountpercent )      	\n"; //56. 수취인 주소2, 할인율
                sql += "values(  ?, ?, ?, ?, ?, \n"; //1 ~ 5
                sql += "			?, ?, ?,\n"; //6 ~ 8
                sql += "			?, ?, ?, ?, ?,\n"; //11 ~ 15
                sql += "			?, ?, ?, ?, ?,\n"; //16 ~ 20
                sql += "			?, ?, ?, ?, ?,\n"; //21 ~ 25
                sql += "			?, ?, ?, ?, ?,\n"; //26 ~ 30
                sql += "			?, ?, ?, ?, ?,\n"; //31 ~ 35
                sql += "			?, ?, ?, ?, 'N',\n"; //36 ~ 40
                sql += "			null, null, null, null, ?,\n"; //41 ~ 45
                sql += "			?, ?, ?, ?, ?,\n";//46 ~ 48
                sql += "			?,?,?,?,?,?,?)	  \n";

                pstmt1 = connMgr.prepareStatement(sql);

                pstmt1.setString(1, v_grcode); //grcode
                pstmt1.setString(2, tid); //거래번호
                pstmt1.setString(3, resultCode); //결과코드
                pstmt1.setString(4, resultMsg); //결과내용
                pstmt1.setString(5, payMethod); //지불방법
                pstmt1.setString(6, mid); //상점 ID
                pstmt1.setString(7, goodname); //과정명
                pstmt1.setString(8, userid); //사용자ID
                pstmt1.setString(9, usernm); //이름
                pstmt1.setString(10, price); //결제금액
                pstmt1.setString(11, buyername); //구매자 이름
                pstmt1.setString(12, buyertel); //구매자 전화번호
                pstmt1.setString(13, buyeremail); //구매자 Email
                pstmt1.setString(14, resulterrCode); //결과에러코드
                pstmt1.setString(15, price1); //OkCashBag 신용카드 금액
                pstmt1.setString(16, price2); //OkCashBag 포인트 금액
                pstmt1.setString(17, authCode); //신용카드 승인번호
                pstmt1.setString(18, cardQuota); //할부기간
                pstmt1.setString(19, quotaInterest); //무이자 할부여부
                pstmt1.setString(20, cardCode); //신용카드사 코드
                pstmt1.setString(21, cardIssuerCode); //카드발급사 코드
                pstmt1.setString(22, authCertain); //본인인증 수행여부
                pstmt1.setString(23, pgAuthDate); //이니시스 승인날짜
                pstmt1.setString(24, pgAuthTime); //이니시스 승인시각
                pstmt1.setString(25, ocbSaveAuthCode); //OKCashB 적립승인번호
                pstmt1.setString(26, ocbUseAuthCode); //OKCashB 사용승인번호
                pstmt1.setString(27, ocbAuthDate); //OKCashB 승인일시
                pstmt1.setString(28, eventFlag); //각종이벤트 적용여부
                pstmt1.setString(29, perno); //송금자 주민번호
                pstmt1.setString(30, vacct); //가상계좌번호
                pstmt1.setString(31, vcdbank); //입금할 은행코드
                pstmt1.setString(32, dtinput); //입금예정일
                pstmt1.setString(33, nminput); //송금자명
                pstmt1.setString(34, nmvacct); //예금주명
                pstmt1.setString(35, moid); //상점주문번호
                pstmt1.setString(36, ocbcardnumber); //OKCashB 결제카드번호
                pstmt1.setString(37, cardNumber); //신용카드번호
                pstmt1.setString(38, userid); //등록자
                pstmt1.setString(39, currentDate); //등록시간
                pstmt1.setString(40, vpoint); //포인트
                pstmt1.setString(41, v_gubun); //구분(N:일반,P:패키지,B:도서)
                pstmt1.setString(42, inputname); //무통장 입금자명
                pstmt1.setString(43, inputdate); //무통장 입금예정일
                pstmt1.setString(44, receive); //
                pstmt1.setString(45, phone); //
                pstmt1.setString(46, post1); //
                pstmt1.setString(47, post2); //
                pstmt1.setString(48, addr1); //
                pstmt1.setString(49, addr2); //
                pstmt1.setString(50, BillData.get("p_dis")); //

                isOk1 = pstmt1.executeUpdate();

                if (resulterrCode.equals("170204") || resulterrCode.equals("1979중")) {
                    //ISP 이중 결제는 tz_propose에 Update하지 않는다.(코드 170204)
                    //겨좌이체 이중 결제는 tz_propose에 Update하지 않는다.(코드 1979중)
                    isOk1 = 1;
                    isOk2 = 1;
                    result = 1;
                } else {
                    //수강신청 Insert
                    if (v_gubun.equals("B")) {
                        //도서
                        isOk2 = 1;
                    } else {
                        //일반과정
                        isOk2 = moveBasketBill(connMgr, v_gubun, v_grcode, tid, userid, BillData.get("subjList"), BillData.get("subjKeyList")) + insertPropose(connMgr, v_gubun, v_grcode, tid, userid, BillData.get("subjList"), BillData.get("subjKeyList"));
                        //+saveUsePoint(connMgr, tid, userid, Integer.parseInt(BillData.get("usePoint")), goodname );
                    }
                }
            }

            ls.close();

            if (isOk1 > 0 && isOk2 > 0) {
                result = 1;
                connMgr.commit();
            } else {
                result = 0;
                connMgr.rollback();
            }

        } catch (Exception ex) {
            result = 0;
            connMgr.rollback();
            ErrorManager.getErrorStackTrace(ex, null, sql);
            Log.info.println("Err =" + ex.getMessage());
            throw new Exception("sql =" + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt1 != null) {
                try {
                    pstmt1.close();
                } catch (Exception e) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.setAutoCommit(true);
                } catch (Exception e10) {
                }
            }
            if (connMgr != null) {
                try {
                    connMgr.freeConnection();
                } catch (Exception e10) {
                }
            }
        }
        return result;
    }

    /**
     * 수강신청 및 도서신청 정보 저장
     * 
     * @param box receive from the form object and session
     * @return ArrayList 자료실 리스트
     * @throws Exception
     */
    private int insertPropose(DBConnectionManager connMgr, String v_gubun, String v_grcode, String tid, String userid, String subjList, String subjKeyList) throws Exception {

        StringBuffer sql = new StringBuffer();
        PreparedStatement pstmt2 = null;
        ListSet ls = null;
        int isOk2 = 0;

        try {

            if (v_gubun.equals("N")) {//신청하기 할 때 이미 등록되어 있으므로(1차 승인을 해야 할 필요가 있을 경우 때문에 신청 테이블에 먼저 등록하게 변경됨) 인서트에서 수정으로 변경

                String arrKeyGubun[] = subjKeyList.split("_");
                String arrCount[];
                String arrSubj[] = new String[20];
                String arrYear[] = new String[20];
                String arrSubjseq[] = new String[20];
                String addSql = "";
                int cnt = 0;

                arrCount = arrKeyGubun[0].replace("'", "").split(",");
                for (int i = 0; i < arrCount.length; i++) {
                    arrSubj[i] = arrCount[i];
                }

                arrCount = arrKeyGubun[1].replace("'", "").split(",");
                for (int i = 0; i < arrCount.length; i++) {
                    arrSubjseq[i] = arrCount[i];
                }

                arrCount = arrKeyGubun[2].replace("'", "").split(",");
                for (int i = 0; i < arrCount.length; i++) {
                    arrYear[i] = arrCount[i];
                }

                cnt = 0;
                for (int i = 0; i < arrCount.length; i++) {
                    cnt++;
                    if (cnt == 1) {
                        addSql += " AND ( ";
                    } else {
                        addSql += " OR ";
                    }
                    addSql += "( SUBJ = '" + arrSubj[i] + "' AND SUBJSEQ ='" + arrSubjseq[i] + "' AND YEAR = '" + arrYear[i] + "' )";
                }

                if (!addSql.equals("")) {
                    addSql += " ) ";
                } else {
                    addSql += "AND SUBJ IN (" + subjList + ")";
                }

                sql.append("UPDATE tz_propose A SET \n");
                sql.append("APPDATE =TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS'), TID = ?, \n");
                sql.append("CHKFINAL=(SELECT DECODE(NVL(AUTOCONFIRM, 'N'), 'Y', 'Y', 'B') FROM TZ_SUBJSEQ WHERE SUBJ=A.SUBJ AND SUBJSEQ=A.SUBJSEQ AND YEAR=A.YEAR), \n");
                sql.append("ldate = TO_CHAR(SYSDATE, 'YYYYMMDDHH24MISS')\n");
                sql.append("WHERE USERID = ?\n");
                sql.append("AND asp_gubun = ? \n");
                sql.append("AND CHKFINAL = 'B'\n");
                sql.append(addSql);

                pstmt2 = connMgr.prepareStatement(sql.toString());
                int i = 1;
                pstmt2.setString(i++, tid);
                pstmt2.setString(i++, userid);
                pstmt2.setString(i++, v_grcode);

                isOk2 = pstmt2.executeUpdate();

                sql.setLength(0);

                String sql1 = "";
                String subj = "";
                String year = "";
                String seq = "";
                String comp = "N000001";
                String grcode = "";
                String sw1 = "";

                sql1 = "select a.subj,a.year,a.subjseq,b.comp,b.grcode,a.asp_gubun,c.userid as sw1 from tz_propose a\n";
                sql1 += "left join tz_member b on a.userid=b.userid\n";
                sql1 += "left join tz_student c on a.subj=c.subj and a.year=c.year and a.subjseq=c.subjseq and a.userid=c.userid \n";
                sql1 += "where tid = '" + tid + "'";

                ls = connMgr.executeQuery(sql1);
                if (ls.next()) {
                    subj = ls.getString("subj");
                    year = ls.getString("year");
                    seq = ls.getString("subjseq");
                    comp = ls.getString("comp");
                    grcode = ls.getString("asp_gubun");
                    sw1 = ls.getString("sw1");
                }

                if (sw1.equals("")) {
                    sql1 = "insert into TZ_STUDENT ( ";
                    sql1 += " subj,        year,     subjseq,     userid,    ";
                    sql1 += " class,       comp,     isdinsert,   score,     ";
                    sql1 += " tstep,       mtest,    ftest,       report,    ";
                    sql1 += " act,         etc1,     etc2,        avtstep,   ";
                    sql1 += " avmtest,     avftest,  avreport,    avact,     ";
                    sql1 += " avetc1,      avetc2,   isgraduated, isrestudy, ";
                    sql1 += " isb2c,       edustart, eduend,      branch,    ";
                    sql1 += " confirmdate, eduno,    luserid,     ldate,     ";
                    sql1 += " stustatus )  ";
                    sql1 += " values ( ";
                    sql1 += " ?, ?, ?, ?, ";
                    sql1 += " ?, ?, ?, ?, ";
                    sql1 += " ?, ?, ?, ?, ";
                    sql1 += " ?, ?, ?, ?, ";
                    sql1 += " ?, ?, ?, ?, ";
                    sql1 += " ?, ?, ?, ?, ";
                    sql1 += " ?, ?, ?, ?, ";
                    sql1 += " ?, ?, ?, ?, ";
                    sql1 += " ?) ";

                    pstmt2 = connMgr.prepareStatement(sql1);

                    pstmt2.setString(1, subj);
                    pstmt2.setString(2, year);
                    pstmt2.setString(3, seq);
                    pstmt2.setString(4, userid);
                    pstmt2.setString(5, ""); // v_class
                    pstmt2.setString(6, grcode.equals("") ? (String) comp : grcode);
                    pstmt2.setString(7, "Y"); // v_isdinsert
                    pstmt2.setDouble(8, 0); // v_score
                    pstmt2.setDouble(9, 0); // v_tstep
                    pstmt2.setDouble(10, 0); // v_mtest
                    pstmt2.setDouble(11, 0); // v_ftest
                    pstmt2.setDouble(12, 0); // v_report
                    pstmt2.setDouble(13, 0); // v_act
                    pstmt2.setDouble(14, 0); // v_etc1
                    pstmt2.setDouble(15, 0); // v_etc2
                    pstmt2.setDouble(16, 0); // v_avtstep
                    pstmt2.setDouble(17, 0); // v_avmtest
                    pstmt2.setDouble(18, 0); // v_avftest
                    pstmt2.setDouble(19, 0); // v_avreport
                    pstmt2.setDouble(20, 0); // v_avact
                    pstmt2.setDouble(21, 0); // v_avetc1
                    pstmt2.setDouble(22, 0); // v_avetc2
                    pstmt2.setString(23, "N"); // v_isgraduated
                    pstmt2.setString(24, "N"); // v_isrestudy)
                    pstmt2.setString(25, "N");
                    pstmt2.setString(26, FormatDate.getDate("yyyyMMdd")); //v_edustart
                    pstmt2.setString(27, ""); //  v_eduend
                    pstmt2.setInt(28, 99); //v_branch
                    pstmt2.setString(29, ""); // v_confirmdate
                    pstmt2.setInt(30, 0); // v_eduno
                    pstmt2.setString(31, "system");
                    pstmt2.setString(32, FormatDate.getDate("yyyyMMddHHmmss")); // ldate
                    pstmt2.setString(33, "Y"); // stustatus
                    isOk2 = pstmt2.executeUpdate();
                }
            } else if (v_gubun.equals("P")) { //패키지과정 - 사용안함
            } else if (v_gubun.equals("B")) {
                //도서 신청
                isOk2 = 1;
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql.toString());
            throw new Exception("sql =" + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt2 != null) {
                try {
                    pstmt2.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk2;
    }

    /**
     * 장바구니 정보 자료 이동 - 별로 의미는 없는듯 하지만 혹시 어딘가에서 사용할 지 몰라서 다시 추가
     * 
     * @param box receive from the form object and session
     * @return ArrayList 자료실 리스트
     * @throws Exception
     */
    private int moveBasketBill(DBConnectionManager connMgr, String v_gubun, String v_grcode, String tid, String userid, String subjList, String subjKeyList) throws Exception {
        PreparedStatement pstmt3 = null;
        StringBuffer sql = new StringBuffer();
        int isOk3 = 0;

        try {
            Log.info.println("#8");

            String arrKeyGubun[] = subjKeyList.split("_");
            String arrCount[];
            String arrSubj[] = new String[20];
            String arrYear[] = new String[20];
            String arrSubjseq[] = new String[20];
            String addSql = "";
            int cnt = 0;

            arrCount = arrKeyGubun[0].replace("'", "").split(",");
            for (int i = 0; i < arrCount.length; i++) {
                arrSubj[i] = arrCount[i];
            }

            arrCount = arrKeyGubun[1].replace("'", "").split(",");
            for (int i = 0; i < arrCount.length; i++) {
                arrSubjseq[i] = arrCount[i];
            }

            arrCount = arrKeyGubun[2].replace("'", "").split(",");
            for (int i = 0; i < arrCount.length; i++) {
                arrYear[i] = arrCount[i];
            }

            cnt = 0;
            for (int i = 0; i < arrCount.length; i++) {
                cnt++;
                if (cnt == 1) {
                    addSql += " AND ( ";
                } else {
                    addSql += " OR ";
                }
                addSql += "( SUBJ = '" + arrSubj[i] + "' AND SUBJSEQ ='" + arrSubjseq[i] + "' AND YEAR = '" + arrYear[i] + "' )";
            }

            if (!addSql.equals("")) {
                addSql += " ) ";
            } else {
                addSql += "AND SUBJ IN (" + subjList + ")";
            }

            sql.append("insert into tz_basketbill(\n");
            sql.append("	GRCODE, TID, USERID, SEQ, GUBUN, SUBJ, YEAR, SUBJSEQ, UNIT, PRICE, AMOUNT, LDATE\n)\n");
            sql.append("select	'");
            sql.append(v_grcode);
            sql.append("' GRCODE, '");
            sql.append(tid);
            sql.append("', '");
            sql.append(userid);
            sql.append("' USERID, ROWNUM SEQ, '");
            sql.append(v_gubun);
            sql.append("' GUBUN, SUBJ, YEAR, SUBJSEQ,\n 1 UNIT/*책 구매시 몇권*/, 0 PRICE/* 책 가격 */, 0 AMOUNT /*합계 가격*/, LDATE\n from 	TZ_PROPOSE A\n WHERE USERID = ?\n AND CHKFINAL = 'B'\n ");
            sql.append(addSql);

            Log.info.println("sql =" + sql);

            pstmt3 = connMgr.prepareStatement(sql.toString());
            int i = 1;
            pstmt3.setString(i++, userid);
            Log.info.println("#9");
            isOk3 = pstmt3.executeUpdate();
            Log.info.println("#10 isok3 =" + isOk3);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, null, sql.toString());
            throw new Exception("sql =" + sql + "\r\n" + ex.getMessage());
        } finally {
            if (pstmt3 != null) {
                try {
                    pstmt3.close();
                } catch (Exception e) {
                }
            }
        }
        return isOk3;
    }

    /**
     * 장바구니 정보 자료 이동 - 별로 의미는 없는듯 하지만 혹시 어딘가에서 사용할 지 몰라서 다시 추가
     * 
     * @param box receive from the form object and session
     * @return ArrayList 자료실 리스트
     * @throws Exception
     */
    @SuppressWarnings("unused")
    private int saveUsePoint(DBConnectionManager connMgr, String tid, String userid, int usePoint, String goodname) throws Exception {
        PreparedStatement pstmt2 = null, pstmt3 = null;
        StringBuffer sql = new StringBuffer();
        ListSet list = null;
        int isOk3 = 1;

        if (usePoint > 0) {
            try {
                sql.append(" SELECT getpoint - nvl(usepoint, 0) POINT, TID FROM TZ_POINTGET A WHERE userid = '");
                sql.append(userid);
                sql.append("' AND getpoint > nvl(usepoint, 0) and to_char(sysdate, 'YYYYMMDD') <= nvl(expiredate,TO_CHAR(to_date(substr(getdate,1,8),'yyyymmdd') + 365,'yyyymmdd')) ");

                list = connMgr.executeQuery(sql.toString());

                ArrayList<DataBox> pointList = list.getAllDataList();

                int i = 1;
                sql.delete(0, sql.length());
                sql.append(" INSERT INTO TZ_POINTUSE ( \n");
                sql.append(" 	TID,USERID, \n");
                sql.append(" 	USEPOINT,USEDATE,TITLE,LUSERID,LDATE \n");
                sql.append(" ) \n");
                sql.append(" SELECT \n");
                sql.append("	TID, USERID, \n");
                sql.append("	? USEPOINT, LDATE, ? TITLE, \n");
                sql.append("	USERID, LDATE \n");
                sql.append(" FROM TZ_BASKETBILL \n");
                sql.append(" WHERE tid=? AND ROWNUM=1 ");

                pstmt2 = connMgr.prepareStatement(sql.toString());

                pstmt2.setInt(i++, usePoint);
                pstmt2.setString(i++, goodname);
                pstmt2.setString(i++, tid);

                pstmt2.executeUpdate();

                sql.delete(0, sql.length());
                sql.append(" UPDATE TZ_POINTGET \n");
                sql.append(" SET usepoint = (? + NVL(USEPOINT, 0)) WHERE userid = '");
                sql.append(userid);
                sql.append("' AND TID = ? ");

                pstmt3 = connMgr.prepareStatement(sql.toString());

                int payPoint = 0;
                for (DataBox entity : pointList) {
                    int point = entity.getInt("d_point");
                    if (usePoint > point)
                        payPoint = point;
                    else
                        payPoint = usePoint;

                    pstmt3.setInt(1, payPoint);
                    pstmt3.setString(2, entity.get("d_tid"));

                    pstmt3.addBatch();

                    usePoint = usePoint - payPoint;
                    if (usePoint == 0)
                        break;
                }

                pstmt3.executeBatch();
                Log.info.println("#10 isok3 =" + isOk3);
            } catch (Exception ex) {
                isOk3 = 0;
                ErrorManager.getErrorStackTrace(ex, null, sql.toString());
                throw new Exception("sql =" + sql + "\r\n" + ex.getMessage());
            } finally {
                if (list != null) {
                    try {
                        list.close();
                    } catch (Exception e) {
                    }
                }
                if (pstmt2 != null) {
                    try {
                        pstmt2.close();
                    } catch (Exception e) {
                    }
                }
                if (pstmt3 != null) {
                    try {
                        pstmt3.close();
                    } catch (Exception e) {
                    }
                }
            }
        }
        return isOk3;
    }

    /**
     * payMethod의 명칭을 통일하기 위해 생성됨.
     * 
     * @return
     */
    static public DataBox getPayMethod() {
        DataBox data = new DataBox("payMethod");
        data.put("Card", "신용카드");
        data.put("VCard", "신용카드");
        data.put("HPP", "휴대폰");
        data.put("Ars1588Bill", "1588");
        data.put("PhoneBill", " 폰빌");
        data.put("OCBPoint", " OKCASHBAG");
        data.put("DirectBank", " 은행계좌이체");
        data.put("VBank", " 무통장 입금 서비스");
        data.put("Culture", " 문화상품권 결제");
        data.put("TEEN", " 틴캐시 결제");
        data.put("DGCL", " 게임문화 상품권");
        data.put("BCSH", " 도서문화 상품권");
        return data;
    }
    //	static public DataBox getPayMethod() {
    //		DataBox data = new DataBox("payMethod");
    //		data.put("Card","신용카드");
    //		data.put("VCard","ISP");
    //		data.put("HPP","휴대폰");
    //		data.put("Ars1588Bill","1588");
    //		data.put("PhoneBill"," 폰빌");
    //		data.put("OCBPoint"," OKCASHBAG");
    //		data.put("DirectBank"," 은행계좌이체");
    //		data.put("VBank"," 무통장 입금 서비스");
    //		data.put("Culture"," 문화상품권 결제");
    //		data.put("TEEN"," 틴캐시 결제");
    //		data.put("DGCL"," 게임문화 상품권");
    //		data.put("BCSH"," 도서문화 상품권");
    //		return data;
    //	}
}