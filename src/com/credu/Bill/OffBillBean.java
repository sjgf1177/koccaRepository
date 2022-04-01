//**********************************************************
//1. ��	  ��: �������� ������û ���� BEAN
//2. ���α׷���: ProposeCourseBean.java
//3. ��	  ��: �������� ������û ���� bean
//4. ȯ	  ��: JDK 1.5
//5. ��	  ��: 1.0
//6. ��	  ��: 2010.1.18
//7. ��	  ��:
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
		 * 1. INIpay41 Ŭ������ �ν��Ͻ� ���� *
		 **************************************/
		INIpay50 inipay = new INIpay50();

		int isOk = 0;

		/*********************
		 * 2. ���� ���� ���� *
		 *********************/

		String	v_listgubun	= box.get("p_listgubun");	//������û : PROP, ������û : REQ
		String	v_subj		= box.get("p_subj");
		String	v_year		= box.get("p_year");
		String	v_subjseq	= box.get("p_subjseq");
		String	v_seq		= box.get("p_seq");

		/************************************************************************************
		 * �ϴ��� PGID �κ��� ���Ҽ��ܺ��� TID�� ������ ǥ���ϵ��� �ϸ�,                     *
		 * ���Ƿ� �����Ͽ� �߻��� ������ ���ؼ��� (��)�̴Ͻý��� å����                      *
		 * ������ �ҽ� ������ ���Ǹ� �ٶ��ϴ�					             *
		 ************************************************************************************/

		String pgid = cs.getProperty("inipay.pgId."+box.get("paymethod"));
		if (pgid == null) pgid = box.get("paymethod");

		inipay.SetField("inipayhome", cs.getProperty("inipay.inipayHome")); // INIpay Home (�����η� ������ ����) - C:/INIpay41_JAVA
		inipay.SetField("admin", cs.getProperty("inipay.keyPw")); // Ű�н�����(�������̵� ���� ����) - 1111
		inipay.SetField("keypw", cs.getProperty("inipay.keyPw")); // Ű�н�����(�������̵� ���� ����) - 1111
		inipay.SetField("type", cs.getProperty("inipay.type")); // ���� - securepay
		inipay.SetField("pgid", cs.getProperty("inipay.pgId")+pgid); // ���� - INIpay
		inipay.SetField("subpgip", cs.getProperty("inipay.subPgIp")); // ���� - 203.238.3.10
		inipay.SetField("debug", cs.getProperty("inipay.debug")); // �α׸��("true"�� �����ϸ� ���� �αװ� ������)
		inipay.SetField("url", cs.getProperty("inipay.url")); // ���� ���񽺵Ǵ� ���� SITE URL�� �����Ұ� -- ���� ����.. �߸��� ������ �������̿���.("http://www.your_domain.co.kr")

		inipay.SetField("uid", box.get("uid")); // ����(���� ���� �Ұ�) - �̴Ͻý� �÷����ο��� �ڵ����� ��ȯ�ϹǷ� �����Ұ�
		inipay.SetField("uip", box.get("userip")); // ����
		inipay.SetField("goodname", box.get("goodname"));
		inipay.SetField("currency", box.get("currency"));
		inipay.SetField("price", box.get("price"));
		inipay.SetField("buyername", box.get("buyername"));
		inipay.SetField("buyertel", box.get("buyertel"));
		inipay.SetField("buyeremail", box.get("buyeremail"));
		inipay.SetField("parentemail", box.get("parentemail")); // ��ȣ�� �̸��� �ּ�(�ڵ���, ��ȭ�����ÿ� 14�� �̸��� ���� �����ϸ�  �θ� �̸��Ϸ� ���� �����뺸 �ǹ�, �ٸ����� ���� ���ÿ� ���� ����)
		inipay.SetField("paymethod", box.get("paymethod"));
		inipay.SetField("encrypted", box.get("encrypted"));
		inipay.SetField("sessionkey", box.get("sessionkey"));
		inipay.SetField("cardcode", box.get("cardcode")); // �÷����ο��� ���ϵǴ� ī���ڵ� ���ڸ�

		/**
		 * �������̵� : inipay.testPay.list�� ��Ͽ� �ش�Ǵ� URL�� �����Ͽ��� ��� �ڵ���Ұ� �̷������.
		 */
		String mid 			= null;
		//		String connectedUrl = box.get("request.serverName");
		//		String[]testBillList = cs.getProperty("inipay.testPay.list").split("|");

		String area			= box.get("p_area");
		if ( area.equals("G0") ) {			// ���� mallId kocca00000
			mid	=	cs.getProperty("inipay.mid.realgame");
		} else if( area.equals("K0") ) {	// ���� mallId kocca00002
			mid	=	cs.getProperty("inipay.mid.realcontent");
		} else if( area.equals("B0") ) {	// ��� mallId kocca00004
			mid	=	cs.getProperty("inipay.mid.realbroad");
		} else {
			mid = null;
		}

		//		for (String url : testBillList) {//2010.02.16 ���� localhost|lms.kocca.or.kr|test.kocca.or.kr
		//			if(connectedUrl.indexOf(url) != -1)
		//				mid = cs.getProperty("inipay.mid.test");	// �׽�Ʈ �������̵�
		//		}
		inipay.SetField("mid", mid);

		/*-----------------------------------------------------------------*
		 * ������ ���� *                                                   *
		 *-----------------------------------------------------------------*
		 * �ǹ������ �ϴ� ������ ��쿡 ���Ǵ� �ʵ���̸�               *
		 * �Ʒ��� ������ INIsecurepay.html ���������� ����Ʈ �ǵ���        *
		 * �ʵ带 ����� �ֵ��� �Ͻʽÿ�.                                  *
		 * ������ ������ü�� ��� �����ϼŵ� �����մϴ�.                   *
		 *-----------------------------------------------------------------*/
		//		inipay.SetField("recvName", box.get("recvname")); 	// ������ ��
		//		inipay.SetField("recvTel", box.get("recvtel"));	// ������ ����ó
		//		inipay.SetField("recvAddr", box.get("recvaddr"));	// ������ �ּ�
		//		inipay.SetField("recvPostNum", box.get("recvpostnum"));	// ������ �����ȣ

		/**
		 * �̴Ͻý� ��ȸ ������(https://iniweb.inicis.com)���� ���̴�
		 * �ѱ��� ������ ��� �Ʒ� 3�� �� �ϳ��� ���(�ּ�����)�Ͽ�
		 * �ѱ��� ����� ���̴� ���� ����Ͻʽÿ�.
		 */
		//inipay.SetField("encoding(1);
		//inipay.SetField("encoding(2);
		//inipay.SetField("encoding(3);


		/****************
		 * 3. ���� ��û *
		 ****************/
		inipay.startAction();


		/****************
		 * 4. ���� ��� *
		 ****************/

		/*	�����ʵ�, ��� ���Ҽ��ܿ� ����	*/
		String tid 			= inipay.GetResult("tid"); // �ŷ���ȣ
		String resultCode 		= inipay.GetResult("ResultCode"); // ����ڵ� ("00"�̸� ���� ����)
		String resultMsg 		= inipay.GetResult("ResultMsg"); // ������� (���Ұ���� ���� ����)
		String moid 			= inipay.GetResult("MOID"); // �����ֹ���ȣ. ���� ��û�� "oid" �ʵ忡 �����Ȱ�
		String pgAuthDate 		= inipay.GetResult("ApplDate"); // �̴Ͻý� ���γ�¥
		String pgAuthTime 		= inipay.GetResult("ApplTime"); // �̴Ͻý� ���νð�
		String authCode 		= inipay.GetResult("ApplNum"); // �ſ�ī�� ���ι�ȣ/�������� ��ȣ. OCB Point/VBank�� ������ ���Ҽ��ܿ� ��� ����
		String payMethod 		= inipay.GetResult("PayMethod"); // ���ҹ�� (�Ŵ��� ����)
		//todo: => ����ǰ���ݰ� ��������ݾװ� ���Ͽ� �ݾ��� �������� �ʴٸ� ���� �ݾ��� �������� �ǽɵ����� �������� ó���� ���� �ʵ��� ó�� �ٶ��ϴ�.(�ŷ����ó��)
		//String totPrice 			= inipay.GetResult("TotPrice"); // ������� �ݾ�
		String eventFlag 		= inipay.GetResult("EventCode"); // ���� �̺�Ʈ ���� ���� => ī�� �Һ� �� ��� ���� �ڵ�.[��÷���� ����***�Ŵ����� �ǹ�]

		/*	���Ҽ��� : ī�����(Card, VCard ����)	*/
		String cardNumber 		= inipay.GetResult("CARD_Num"); // �ſ�ī���ȣ
		String quotaInterest	 	= inipay.GetResult("CARD_Interest"); // �Һ� ���� ("1"�̸� �������Һ�)
		String cardQuota 		= inipay.GetResult("CARD_Quota"); // �ҺαⰣ
		String cardCode 			= inipay.GetResult("CARD_Code"); // �ſ�ī��� �ڵ� (�Ŵ��� ����)
		String cardIssuerCode 	= inipay.GetResult("CARD_BankCode"); // ī��߱޻� �ڵ� (�Ŵ��� ����)
		//ī��� ������ ī�尡 �ƴ� �迭ī���� ���, 2�ڸ� �ſ�ī����ڵ�� ����� �ڼ��� ī�������� ��Ÿ���ϴ�(������ ī���� ��� "00"���� ��ȯ�˴ϴ�.)
		String ocbcardnumber 		= inipay.GetResult("OCB_Num"); // OK CASH BAG ����, ������ ��� OK CASH BAG ī�� ��ȣ
		String ocbSaveAuthCode	= inipay.GetResult("OCB_SaveApplNum"); // OK Cashbag ���� ���ι�ȣ
		String ocbUseAuthCode	= inipay.GetResult("OCB_PayApplNum"); // OK Cashbag ��� ���ι�ȣ
		String ocbAuthDate 			= inipay.GetResult("OCB_ApplDate"); // OK Cashbag �����Ͻ�
		//		String ocbPayPrice = inipay.GetResult("OCB_PayPrice");//OK Cashbag ���� �� ��볻��, ����Ʈ���ұݾ�
		String price2					= inipay.GetResult("OCB_PayPrice"); // OK Cashbag ���հ���� ����Ʈ ���ұݾ�
		String price1					= inipay.GetResult("Price1"); // OK Cashbag ���հ���� �ſ�ī�� ���ұݾ� ---> ������

		/*	���Ҽ��� : �ǽð�������ü(DirectBank)	*/
		//String acctBankCode		= inipay.GetResult("ACCT_BankCode");//�����ڵ�, [��÷���� ����***�Ŵ����� �ǹ�] String[2]
		//String cshrResultCode	= inipay.GetResult("CSHR_ResultCode");// ���ݿ�����, �߱ް���ڵ� String[10]
		//String cshrType				= inipay.GetResult("CSHR_Type");//���ݿ�����, �߱ޱ����ڵ�

		/*	���Ҽ��� : �������Ա� or �������(VBank)	*/
		String vacct 			= inipay.GetResult("VACT_Num"); // ������¹�ȣ
		String vcdbank 		= inipay.GetResult("VACT_BankCode"); // �Ա��� �����ڵ�
		String nmvacct 		= inipay.GetResult("VACT_Name"); // ������ ��
		String nminput 		= inipay.GetResult("VACT_InputName"); // �۱��� ��
		String perno 			= inipay.GetResult("VACT_RegNum"); // �۱��� �ֹι�ȣ
		String dtinput 		= inipay.GetResult("VACT_Date"); // �Աݿ�����
		//String dtinputTime 		= inipay.GetResult("VACT_Time"); // �Աݿ����ð�

		/*	���Ҽ��� : �ڵ���, ��ȭ ����(HPP)	*/
		String nohpp 			= inipay.GetResult("HPP_Num"); // �޴��� ������ ���� �޴��� ��ȣ
		String codegw 			= inipay.GetResult("HPP_GWCode"); // ��ȭ���� ����� �ڵ�, �ڵ���, ��ȭ ���� ��� ������( "���� ���� �ڼ��� ����"���� �ʿ� , ���������� �ʿ���� ������)

		/*	���Ҽ��� : �Ŵ� ��ȭ���� - �ڵ��� ���� ��� ������(Ars1588Bill)	*/
		String noars 			= inipay.GetResult("ARSB_Num"); // ��ȭ���� �� ���� ��ȭ��ȣ

		//* 10. ��ȭ ��ǰ�� ���� ��� ������
		String cultureid 		= inipay.GetResult("CULT_UserID"); // ���� ���� ID
		//* 12.ƾĳ�� �ܾ� ������
		// ƾĳ�� ���������� �̿�ÿ���  ���� ��� ����
		//		String RemainPrice 		= inipay.GetResult("RemainPrice");		//ƾĳ�������� �ܾ�
		//*    inipay.GetResult("TEEN_Remains")
		//*  ƾĳ�� ID : inipay.GetResult("TEEN_UserID")
		//* 13.���ӹ�ȭ ��ǰ��
		//*	��� ī�� ���� : inipay.GetResult("GAMG_Cnt")
		//* 14.������ȭ ��ǰ��
		//*	����� ID : inipay.GetResult("BCSH_UserID")

		String authCertain 	= inipay.GetResult("AuthCertain"); // �������� ���࿩�� ("00"�̸� ����)



		String goodname 		= box.get("goodname");
		String price 			= box.get("price");
		String buyername 		= box.get("buyername");
		String buyertel 		= box.get("buyertel");
		String buyeremail 		= box.get("buyeremail");


		//N:�Ϲݰ���, P:����������, B:����
		String v_gubun		= box.get("p_gubun");

		String v_receive = box.get("p_receive");
		String v_phone = box.get("p_phone");
		String v_post1 = box.get("p_post1");
		String v_post2 = box.get("p_post2");
		String v_addr1 = box.get("p_addr1");
		String v_addr2 = box.get("p_addr2");

		/*-------------------------------------------------------------------------*
		 * �����߻��� ��� �޼������� �����ڵ带 �����ϴ� �κ����� ���� ���� ����  *
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
		 * 5. �������                                                     *
		 *                                                                 *
		 * ���� ����� DB � �����ϰų� ��Ÿ �۾��� �����ϴٰ� �����ϴ�  *
		 * ���, �Ʒ��� �ڵ带 �����Ͽ� �̹� ���ҵ� �ŷ��� ����ϴ� �ڵ带 *
		 * �ۼ��մϴ�.                                                     *
		 *******************************************************************/
		try
		{
			//			System.out.println("#1");

			// DB CODE HERE
			if (box.getSession("userid").equals(""))    {
				// ������ ������ �������
				inipay.SetField("type", "cancel"); // ����
				inipay.SetField("cancelMsg", "NO SESSION"); // ��һ���
				inipay.startAction();

				resultCode ="01";
				resultMsg ="NO SESSION";

			} else {
				//HashTable ����

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

				BillData.put("ispointuse",	v_ispointuse); //����Ʈ ��뿩��


				String v_point = box.get("point");
				if (v_point == null) v_point ="0";
				BillData.put("point",v_point 		); //��� ����Ʈ
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

				BillData.put("listgubun", v_listgubun);//������û ����
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
					//������� ����
					isOk = billProcess(BillData);
				}

				box.putAll(BillData);
				System.out.println("�������"+isOk);

				if (isOk==1) {//����
					resultCode ="00";
					resultMsg ="DB Success";

				} else {//����
					inipay.SetField("type","cancel"); // ����
					inipay.SetField("cancelMsg", "DB FAIL"); // ��һ���
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
			inipay.SetField("type", "cancel"); // ����
			inipay.SetField("cancelMsg", "DB FAIL"); // ��һ���
			inipay.startAction();

			resultCode ="01";
			resultMsg ="SYSTEM FAIL";

		}

		return isOk;
	}

	/**
	 * ����ó��(�����Ϸ�� ��ٱ���->������ ��ٱ��Ϸ� DATA �̰�)
	 * @param    box          receive from the form object and session
	 * @return ArrayList  �ڷ�� ����Ʈ
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

		String 	tid 			= BillData.get("tid"); 					// �ŷ���ȣ
		String 	resultCode 		= BillData.get("resultCode"); 			// ����ڵ� ("00"�̸� ���� ����)
		String 	resultMsg 		= BillData.get("resultMsg"); 			// ������� (���Ұ���� ���� ����)
		String 	payMethod 		= BillData.get("payMethod"); 			// ���ҹ�� (�Ŵ��� ����)
		String 	payStatus 		= BillData.get("payStatus"); 			// ���һ���
		String 	price1 			= BillData.get("price1"); 					// OK Cashbag ���հ���� �ſ�ī�� ���ұݾ�
		String 	price2 			= BillData.get("price2"); 					// OK Cashbag ���հ���� ����Ʈ ���ұݾ�

		if	(price1 == null) price1 ="0";
		if	(price2 == null) price2 ="0";

		price1 ="0";
		price2 ="0";

		String 	authCode 		= BillData.get("authCode"); 			// �ſ�ī�� ���ι�ȣ
		String 	cardQuota 		= BillData.get("cardQuota");			// �ҺαⰣ
		String 	quotaInterest 	= BillData.get("quotaInterest"); 		// �������Һ� ���� ("1"�̸� �������Һ�)
		String 	cardCode 		= BillData.get("cardCode"); 			// �ſ�ī��� �ڵ� (�Ŵ��� ����)
		String 	cardIssuerCode 	= BillData.get("cardIssuerCode"); 		// ī��߱޻� �ڵ� (�Ŵ��� ����)
		String 	authCertain 	= BillData.get("authCertain");			// �������� ���࿩�� ("00"�̸� ����)
		String 	pgAuthDate 		= BillData.get("pgAuthDate"); 			// �̴Ͻý� ���γ�¥
		String 	pgAuthTime 		= BillData.get("pgAuthTime"); 			// �̴Ͻý� ���νð�
		String 	ocbSaveAuthCode = BillData.get("ocbSaveAuthCode"); 		// OK Cashbag ���� ���ι�ȣ
		String 	ocbUseAuthCode 	= BillData.get("ocbUseAuthCode"); 		// OK Cashbag ��� ���ι�ȣ
		String 	ocbAuthDate 	= BillData.get("ocbAuthDate"); 			// OK Cashbag �����Ͻ�
		String 	eventFlag 		= BillData.get("eventFlag"); 			// ���� �̺�Ʈ ���� ����
		String 	nohpp 			= BillData.get("nohpp"); 				// �޴��� ������ ���� �޴��� ��ȣ
		String 	noars 			= BillData.get("noars"); 				// ��ȭ���� �� ���� ��ȭ��ȣ
		String 	perno 			= BillData.get("perno"); 				// �۱��� �ֹι�ȣ
		String 	vacct 			= BillData.get("vacct"); 				// ������¹�ȣ
		String 	vcdbank 		= BillData.get("vcdbank"); 				// �Ա��� �����ڵ�
		String 	dtinput 		= BillData.get("dtinput"); 				// �Աݿ�����
		String 	nminput 		= BillData.get("nminput"); 				// �۱��� ��
		String 	nmvacct 		= BillData.get("nmvacct"); 				// ������ ��
		String 	moid 			= BillData.get("moid"); 				// ���� �ֹ���ȣ
		String 	codegw 			= BillData.get("codegw"); 				// ��ȭ���� ����� �ڵ�
		String 	ocbcardnumber 	= BillData.get("ocbcardnumber"); 		// OK CASH BAG ����, ������ ��� OK CASH BAG ī�� ��ȣ
		String 	cultureid 		= BillData.get("cultureid"); 			// ���� ���� ID
		String 	cardNumber 		= BillData.get("cardNumber"); 			// �ſ�ī���ȣ
		String 	mid 			= BillData.get("mid");					//���� �ֹ���ȣ
		String 	goodname 		= BillData.get("goodname"); 			//��ǰ��(������)
		String 	price 			= BillData.get("price"); 					//�����ݾ�
		String 	ispointuse		= BillData.get("ispointuse"); 					//����Ʈ��뿩��
		String 	vpoint			= BillData.get("point"); 					//����Ʈ
		String 	buyername 		= BillData.get("buyername"); 			//������ �̸�
		String 	buyertel 		= BillData.get("buyertel"); 			//������ ��ȭ��ȣ
		String 	buyeremail 		= BillData.get("buyeremail"); 			//������ EMAIL
		String 	resulterrCode 	= BillData.get("resulterrCode");  		//���� �ڵ�

		String 	v_gubun 		= BillData.get("v_gubun");  				//N:�Ϲ�,P:������,B:����
		String 	userid			= BillData.get("userid");  				//SESSION ����� ID
		String 	usernm			= BillData.get("usernm");  				//SESSION ����� �̸�

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

		String  v_listgubun	= BillData.get("listgubun"); //������û : PROP; ������û : REQ
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
				//�������� ����
				sql ="insert into tz_offbillinfo(\n";
				sql+="          tid,				\n"; //01.�ŷ���ȣ
				sql+="          resultcode,		\n"; //02.����ڵ�
				sql+="          resultmsg,		\n"; //03.�������
				sql+="          paymethod,		\n"; //04.���ҹ��
				sql+="          paystatus,		\n"; //05.��������
				sql+="          mid,				\n"; //06.���� ID
				sql+="          goodname,		\n"; //07.������
				sql+="          userid,			\n"; //08.�����ID
				sql+="          usernm,			\n"; //09.�̸�
				sql+="          price,			\n"; //10.�����ݾ�
				sql+="          point,			\n"; //11.����Ʈ
				sql+="          buyername,		\n"; //12.������ �̸�
				sql+="          buyertel,		\n"; //13.������ ��ȭ��ȣ
				sql+="          buyeremail,		\n"; //14.������ Email
				sql+="          resulterrcode,	\n"; //15.��������ڵ�
				sql+="          price1,			\n"; //16.OkCashBag �ſ�ī�� �ݾ�
				sql+="          price2,			\n"; //17.OkCashBag ����Ʈ �ݾ�
				sql+="          authcode,		\n"; //18.�ſ�ī�� ���ι�ȣ
				sql+="          cardquota,		\n"; //19.�ҺαⰣ
				sql+="          quotainterest,	\n"; //20.������ �Һο���
				sql+="          cardcode,		\n"; //21.�ſ�ī��� �ڵ�
				sql+="          cardissuercode,	\n"; //22.ī��߱޻� �ڵ�
				sql+="          authcertain,		\n"; //23.�������� ���࿩��
				sql+="          pgauthdate,		\n"; //24.�̴Ͻý� ���γ�¥
				sql+="          pgauthtime,		\n"; //25.�̴Ͻý� ���νð�
				sql+="          ocbcardnumber,	\n"; //26.OKCashB ����ī���ȣ
				sql+="          ocbsaveauthcode,	\n"; //27.OKCashB �������ι�ȣ
				sql+="          ocbuseauthcode,	\n"; //28.OKCashB �����ι�ȣ
				sql+="          ocbauthdate	,	\n"; //29.OKCashB �����Ͻ�
				sql+="          eventflag,		\n"; //30.�����̺�Ʈ ���뿩��
				sql+="          perno,			\n"; //31.�۱��� �ֹι�ȣ
				sql+="          vacct,			\n"; //32.������¹�ȣ
				sql+="          vcdbank	,		\n"; //33.�Ա��� �����ڵ�
				sql+="          dtinput	,		\n"; //34.�Աݿ�����
				sql+="          nminput	,		\n"; //35.�۱��ڸ�
				sql+="          nmvacct	,		\n"; //36.�����ָ�
				sql+="          moid,			\n"; //37.�����ֹ���ȣ
				sql+="          cardnumber,		\n"; //38.�ſ�ī���ȣ
				sql+="          cancelyn,		\n"; //39.��ҿ���
				sql+="          cancelresult,	\n"; //40.��Ұ��
				sql+="          canceldate,		\n"; //41.��ҳ�¥
				sql+="          canceltime,		\n"; //42.��ҽð�
				sql+="          rcashcancelno,	\n"; //43.���ݿ����� ���ι�ȣ
				sql+="          inputname,		\n"; //44.�Ա��ڸ�
				sql+="          inputdate,		\n"; //45.�Աݿ�����
				sql+="          receive	,		\n"; //46.������ ����
				sql+="          phone,			\n"; //47.������ ����ó
				sql+="          post1,			\n"; //48.������ �����ȣ1
				sql+="          post2,			\n"; //49.������ �����ȣ2
				sql+="          addr1,			\n"; //50.������ �ּ�1
				sql+="          addr2,			\n"; //51.������ �ּ�2
				sql+="          multiyn,			\n"; //52.���߰�������
				sql+="          agencyyn,		\n"; //53.�����������
				sql+="          luserid,			\n"; //54.���������
				sql+="          ldate)			\n"; //55.���������

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


				pstmt1.setString( 1, tid); //01.�ŷ���ȣ
				pstmt1.setString( 2, resultCode		); //02.����ڵ�
				pstmt1.setString( 3, resultMsg		); //03.�������
				pstmt1.setString( 4, payMethod		); //04.���ҹ��
				pstmt1.setString( 5, payStatus		); //05.��������
				pstmt1.setString( 6, mid			); //06.���� ID
				pstmt1.setString( 7, goodname		); //07.������
				pstmt1.setString( 8, userid			); //08.�����ID
				pstmt1.setString( 9, usernm			); //09.�̸�
				pstmt1.setString(10, price			); //10.�����ݾ�
				pstmt1.setString(11, vpoint			); //11.����Ʈ
				pstmt1.setString(12, buyername		); //12.������ �̸�
				pstmt1.setString(13, buyertel		); //13.������ ��ȭ��ȣ
				pstmt1.setString(14, buyeremail		); //14.������ Email
				pstmt1.setString(15, resulterrCode	); //15.��������ڵ�
				pstmt1.setString(16, price1			); //16.OkCashBag �ſ�ī�� �ݾ�
				pstmt1.setString(17, price2			); //17.OkCashBag ����Ʈ �ݾ�
				pstmt1.setString(18, authCode		); //18.�ſ�ī�� ���ι�ȣ
				pstmt1.setString(19, cardQuota		); //19.�ҺαⰣ
				pstmt1.setString(20, quotaInterest	); //20.������ �Һο���
				pstmt1.setString(21, cardCode		); //21.�ſ�ī��� �ڵ�
				pstmt1.setString(22, cardIssuerCode	); //22.ī��߱޻� �ڵ�
				pstmt1.setString(23, authCertain	); //23.�������� ���࿩��
				pstmt1.setString(24, pgAuthDate		); //24.�̴Ͻý� ���γ�¥
				pstmt1.setString(25, pgAuthTime		); //25.�̴Ͻý� ���νð�
				pstmt1.setString(26, ocbcardnumber	); //26.OKCashB ����ī���ȣ
				pstmt1.setString(27, ocbSaveAuthCode); //27.OKCashB �������ι�ȣ
				pstmt1.setString(28, ocbUseAuthCode	); //28.OKCashB �����ι�ȣ
				pstmt1.setString(29, ocbAuthDate	); //29.OKCashB �����Ͻ�
				pstmt1.setString(30, eventFlag		); //30.�����̺�Ʈ ���뿩��
				pstmt1.setString(31, perno			); //31.�۱��� �ֹι�ȣ
				pstmt1.setString(32, vacct			); //32.������¹�ȣ
				pstmt1.setString(33, vcdbank		); //33.�Ա��� �����ڵ�
				pstmt1.setString(34, dtinput		); //34.�Աݿ�����
				pstmt1.setString(35, nminput		); //35.�۱��ڸ�
				pstmt1.setString(36, nmvacct		); //36.�����ָ�
				pstmt1.setString(37, moid			); //37.�����ֹ���ȣ
				pstmt1.setString(38, cardNumber		); //38.�ſ�ī���ȣ
				pstmt1.setString(39, inputname		); //44.�Ա��ڸ�
				pstmt1.setString(40, inputdate		); //45.�Աݿ�����
				pstmt1.setString(41, receive		); //46.������ ����
				pstmt1.setString(42, phone			); //47.������ ����ó
				pstmt1.setString(43, post1			); //48.������ �����ȣ1
				pstmt1.setString(44, post2			); //49.������ �����ȣ2
				pstmt1.setString(45, addr1			); //50.������ �ּ�1
				pstmt1.setString(46, addr2			); //51.������ �ּ�2
				pstmt1.setString(47, multiyn		); //52.���߰�������
				pstmt1.setString(48, agencyyn		); //53.�����������
				pstmt1.setString(49, userid			); //54.���������
				pstmt1.setString(50, currentDate	); //55.���������


				Log.info.println("isOk1  before =" + isOk1);
				isOk1 = pstmt1.executeUpdate();

				Log.info.println("isOk1 =" + isOk1);
				Log.info.println("v_gubun =" + v_gubun);

				if (resulterrCode.equals("170204") || resulterrCode.equals("1979��")) {
					//ISP ���� ������ tz_propose�� Update���� �ʴ´�.(�ڵ� 170204)
					//������ü ���� ������ tz_propose�� Update���� �ʴ´�.(�ڵ� 1979��)
					isOk1 = 1;
					isOk2 = 1;
					isOk3 = 1;
					result = 1;
				} else {
					//������û Insert
					Log.info.println("#2");

					//isOk2 = moveBasketBill(connMgr, v_gubun, v_grcode, tid, userid, BillData.get("subjList") )
					//*insertPropose(connMgr, v_gubun, v_grcode, tid, userid, BillData.get("subjList") )
					//*saveUsePoint(connMgr, tid, userid, Integer.parseInt(BillData.get("usePoint")), goodname );
					if ("PROP".equals(v_listgubun)) {
						isOk2 = updatePropose(connMgr, tid, v_subj, v_year, v_subjseq, v_seq, userid, price);
						isOk3 = 1;
					} else if ("REQ".equals(v_listgubun)) {


						//2010.01.22 ������ ó���� ������û�ǿ� ���� ������û ������ ����� ����������
						//          ���� ��� ������ ���Ƿ� �����͸� Ȯ����, �̰�쿡 ������û����� �� �����ʹ� ������ ���������� �� �� ����
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
	 * ������û ���� ����
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
	 * ������û ���� ����
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
	 * ������û ���� ���� ������û ���̺� INSERT
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
	 * off-line ���� ���� ��ȸ
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