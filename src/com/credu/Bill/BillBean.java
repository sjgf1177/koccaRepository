//**********************************************************
//1. ��	  ��: SUBJECT INFORMATION USER BEAN
//2. ���α׷���: ProposeCourseBean.java
//3. ��	  ��: �����ȳ� ����� bean
//4. ȯ	  ��: JDK 1.3
//5. ��	  ��: 1.0
//6. ��	  ��: 2004.01.14
//7. ��	  ��:
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
     * ������� �������� ó���κ�
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
                 * �������� ��ȣȭ /* SeedCipher seed = new SeedCipher();
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

                // �������� ����
                isOk = billProcess(BillData);

            } catch (Exception ex) {
                System.out.println("Exception Error => " + ex.toString());
            }

        }

        return isOk;
    }

    /**
     * ������� ����ó�� �κ�
     * 
     * @param box
     * @return
     * @throws Exception
     */
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

        /************************************************************************************
         * �ϴ��� PGID �κ��� ���Ҽ��ܺ��� TID�� ������ ǥ���ϵ��� �ϸ�, * ���Ƿ� �����Ͽ� �߻��� ������ ���ؼ���
         * (��)�̴Ͻý��� å���� * ������ �ҽ� ������ ���Ǹ� �ٶ��ϴ� *
         ************************************************************************************/

        String pgid = cs.getProperty("inipay.pgId." + box.get("paymethod"));
        if (pgid == null)
            pgid = box.get("paymethod");

        inipay.SetField("inipayhome", cs.getProperty("inipay.inipayHome")); // INIpay Home (�����η� ������ ����) - C:/INIpay41_JAVA
        inipay.SetField("admin", cs.getProperty("inipay.keyPw")); // Ű�н�����(�������̵� ���� ����) - 1111
        inipay.SetField("keypw", cs.getProperty("inipay.keyPw")); // Ű�н�����(�������̵� ���� ����) - 1111
        inipay.SetField("type", cs.getProperty("inipay.type")); // ���� - securepay
        inipay.SetField("pgid", cs.getProperty("inipay.pgId") + pgid); // ���� - INIpay
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
        String mid = null;
        //		String connectedUrl = box.get("request.serverName");
        //		String[]testBillList = cs.getProperty("inipay.testPay.list").split("|");

        mid = cs.getProperty("inipay.mid.real"); // ���� �������̵�

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
         * �̴Ͻý� ��ȸ ������(https://iniweb.inicis.com)���� ���̴� �ѱ��� ������ ��� �Ʒ� 3�� �� �ϳ���
         * ���(�ּ�����)�Ͽ� �ѱ��� ����� ���̴� ���� ����Ͻʽÿ�.
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

        /* �����ʵ�, ��� ���Ҽ��ܿ� ���� */
        String tid = inipay.GetResult("tid"); // �ŷ���ȣ
        String resultCode = inipay.GetResult("ResultCode"); // ����ڵ� ("00"�̸� ���� ����)
        String resultMsg = inipay.GetResult("ResultMsg"); // ������� (���Ұ���� ���� ����)
        String moid = inipay.GetResult("MOID"); // �����ֹ���ȣ. ���� ��û�� "oid" �ʵ忡 �����Ȱ�
        String pgAuthDate = inipay.GetResult("ApplDate"); // �̴Ͻý� ���γ�¥
        String pgAuthTime = inipay.GetResult("ApplTime"); // �̴Ͻý� ���νð�
        String authCode = inipay.GetResult("ApplNum"); // �ſ�ī�� ���ι�ȣ/�������� ��ȣ. OCB Point/VBank�� ������ ���Ҽ��ܿ� ��� ����
        String payMethod = inipay.GetResult("PayMethod"); // ���ҹ�� (�Ŵ��� ����)
        //todo: => ����ǰ���ݰ� ��������ݾװ� ���Ͽ� �ݾ��� �������� �ʴٸ� ���� �ݾ��� �������� �ǽɵ����� �������� ó���� ���� �ʵ��� ó�� �ٶ��ϴ�.(�ŷ����ó��)
        //String totPrice 			= inipay.GetResult("TotPrice"); // ������� �ݾ�
        String eventFlag = inipay.GetResult("EventCode"); // ���� �̺�Ʈ ���� ���� => ī�� �Һ� �� ��� ���� �ڵ�.[��÷���� ����***�Ŵ����� �ǹ�]

        /* ���Ҽ��� : ī�����(Card, VCard ����) */
        String cardNumber = inipay.GetResult("CARD_Num"); // �ſ�ī���ȣ
        String quotaInterest = inipay.GetResult("CARD_Interest"); // �Һ� ���� ("1"�̸� �������Һ�)
        String cardQuota = inipay.GetResult("CARD_Quota"); // �ҺαⰣ
        String cardCode = inipay.GetResult("CARD_Code"); // �ſ�ī��� �ڵ� (�Ŵ��� ����)
        String cardIssuerCode = inipay.GetResult("CARD_BankCode"); // ī��߱޻� �ڵ� (�Ŵ��� ����)
        //ī��� ������ ī�尡 �ƴ� �迭ī���� ���, 2�ڸ� �ſ�ī����ڵ�� ����� �ڼ��� ī�������� ��Ÿ���ϴ�(������ ī���� ��� "00"���� ��ȯ�˴ϴ�.)
        String ocbcardnumber = inipay.GetResult("OCB_Num"); // OK CASH BAG ����, ������ ��� OK CASH BAG ī�� ��ȣ
        String ocbSaveAuthCode = inipay.GetResult("OCB_SaveApplNum"); // OK Cashbag ���� ���ι�ȣ
        String ocbUseAuthCode = inipay.GetResult("OCB_PayApplNum"); // OK Cashbag ��� ���ι�ȣ
        String ocbAuthDate = inipay.GetResult("OCB_ApplDate"); // OK Cashbag �����Ͻ�
        //		String ocbPayPrice = inipay.GetResult("OCB_PayPrice");//OK Cashbag ���� �� ��볻��, ����Ʈ���ұݾ�
        String price2 = inipay.GetResult("OCB_PayPrice"); // OK Cashbag ���հ���� ����Ʈ ���ұݾ�
        String price1 = inipay.GetResult("Price1"); // OK Cashbag ���հ���� �ſ�ī�� ���ұݾ� ---> ������

        /* ���Ҽ��� : �ǽð�������ü(DirectBank) */
        //String acctBankCode		= inipay.GetResult("ACCT_BankCode");//�����ڵ�, [��÷���� ����***�Ŵ����� �ǹ�] String[2]
        //String cshrResultCode	= inipay.GetResult("CSHR_ResultCode");// ���ݿ�����, �߱ް���ڵ� String[10]
        //String cshrType				= inipay.GetResult("CSHR_Type");//���ݿ�����, �߱ޱ����ڵ�

        /* ���Ҽ��� : �������Ա� or �������(VBank) */
        String vacct = inipay.GetResult("VACT_Num"); // ������¹�ȣ
        String vcdbank = inipay.GetResult("VACT_BankCode"); // �Ա��� �����ڵ�
        String nmvacct = inipay.GetResult("VACT_Name"); // ������ ��
        String nminput = inipay.GetResult("VACT_InputName"); // �۱��� ��
        String perno = inipay.GetResult("VACT_RegNum"); // �۱��� �ֹι�ȣ
        String dtinput = inipay.GetResult("VACT_Date"); // �Աݿ�����
        //String dtinputTime 		= inipay.GetResult("VACT_Time"); // �Աݿ����ð�

        /* ���Ҽ��� : �ڵ���, ��ȭ ����(HPP) */
        String nohpp = inipay.GetResult("HPP_Num"); // �޴��� ������ ���� �޴��� ��ȣ
        String codegw = inipay.GetResult("HPP_GWCode"); // ��ȭ���� ����� �ڵ�, �ڵ���, ��ȭ ���� ��� ������( "���� ���� �ڼ��� ����"���� �ʿ� , ���������� �ʿ���� ������)

        /* ���Ҽ��� : �Ŵ� ��ȭ���� - �ڵ��� ���� ��� ������(Ars1588Bill) */
        String noars = inipay.GetResult("ARSB_Num"); // ��ȭ���� �� ���� ��ȭ��ȣ

        //* 10. ��ȭ ��ǰ�� ���� ��� ������
        String cultureid = inipay.GetResult("CULT_UserID"); // ���� ���� ID
        //* 12.ƾĳ�� �ܾ� ������
        // ƾĳ�� ���������� �̿�ÿ���  ���� ��� ����
        //		String RemainPrice 		= inipay.GetResult("RemainPrice");		//ƾĳ�������� �ܾ�
        //*    inipay.GetResult("TEEN_Remains")
        //*  ƾĳ�� ID : inipay.GetResult("TEEN_UserID")
        //* 13.���ӹ�ȭ ��ǰ��
        //*	��� ī�� ���� : inipay.GetResult("GAMG_Cnt")
        //* 14.������ȭ ��ǰ��
        //*	����� ID : inipay.GetResult("BCSH_UserID")

        String authCertain = inipay.GetResult("AuthCertain"); // �������� ���࿩�� ("00"�̸� ����)

        String goodname = box.get("goodname");
        String price = box.get("price");
        String buyername = box.get("buyername");
        String buyertel = box.get("buyertel");
        String buyeremail = box.get("buyeremail");

        //N:�Ϲݰ���, P:����������, B:����
        String v_gubun = box.get("p_gubun");

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

        String resulterrCode = "";

        if (resultMsg.indexOf("]") != -1) {
            resulterrCode = resultMsg.substring(1, resultMsg.indexOf("]"));
        }

        System.out.println("resulterrCode =" + resulterrCode);
        /*-------------------------------------------------------------------------*/

        /*******************************************************************
         * 5. ������� * * ���� ����� DB � �����ϰų� ��Ÿ �۾��� �����ϴٰ� �����ϴ� * ���, �Ʒ��� �ڵ带 �����Ͽ�
         * �̹� ���ҵ� �ŷ��� ����ϴ� �ڵ带 * �ۼ��մϴ�. *
         *******************************************************************/
        try {
            //			System.out.println("#1");

            // DB CODE HERE
            if (box.getSession("userid").equals("")) {
                // ������ ������ �������
                inipay.SetField("type", "cancel"); // ����
                inipay.SetField("cancelMsg", "NO SESSION"); // ��һ���
                inipay.startAction();

                resultCode = "01";
                resultMsg = "NO SESSION";

            } else {
                //HashTable ����

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

                BillData.put("ispointuse", v_ispointuse); //����Ʈ ��뿩��

                String v_point = box.get("point");
                if (v_point == null)
                    v_point = "0";
                BillData.put("point", v_point); //��� ����Ʈ
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
                    //������� ����
                    isOk = billProcess(BillData);
                }

                box.putAll(BillData);
                System.out.println("�������" + isOk);

                if (isOk == 1) {//����
                    resultCode = "00";
                    resultMsg = "DB Success";
                } else {//����
                    inipay.SetField("type", "cancel"); // ����
                    inipay.SetField("cancelMsg", "DB FAIL"); // ��һ���
                    inipay.startAction();

                    resultCode = "01";
                    resultMsg = "DB FAIL";
                }
            }

        } catch (Exception e) {
            System.out.println("Exception FOUND");
            System.out.println("err=" + e);
            inipay.SetField("type", "cancel"); // ����
            inipay.SetField("cancelMsg", "DB FAIL"); // ��һ���
            inipay.startAction();

            resultCode = "01";
            resultMsg = "SYSTEM FAIL";
        }

        return isOk;
    }

    /**
     * ����ó��(�����Ϸ�� ��ٱ���->������ ��ٱ��Ϸ� DATA �̰�)
     * 
     * @param box receive from the form object and session
     * @return ArrayList �ڷ�� ����Ʈ
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

        String tid = BillData.get("tid"); // �ŷ���ȣ
        String resultCode = BillData.get("resultCode"); // ����ڵ� ("00"�̸� ���� ����)
        String resultMsg = BillData.get("resultMsg"); // ������� (���Ұ���� ���� ����)
        String payMethod = BillData.get("payMethod"); // ���ҹ�� (�Ŵ��� ����)
        String price1 = BillData.get("price1"); // OK Cashbag ���հ���� �ſ�ī�� ���ұݾ�
        String price2 = BillData.get("price2"); // OK Cashbag ���հ���� ����Ʈ ���ұݾ�

        if (price1 == null)
            price1 = "0";
        if (price2 == null)
            price2 = "0";

        price1 = "0";
        price2 = "0";

        String authCode = BillData.get("authCode"); // �ſ�ī�� ���ι�ȣ
        String cardQuota = BillData.get("cardQuota"); // �ҺαⰣ
        String quotaInterest = BillData.get("quotaInterest"); // �������Һ� ���� ("1"�̸� �������Һ�)
        String cardCode = BillData.get("cardCode"); // �ſ�ī��� �ڵ� (�Ŵ��� ����)
        String cardIssuerCode = BillData.get("cardIssuerCode"); // ī��߱޻� �ڵ� (�Ŵ��� ����)
        String authCertain = BillData.get("authCertain"); // �������� ���࿩�� ("00"�̸� ����)
        String pgAuthDate = BillData.get("pgAuthDate"); // �̴Ͻý� ���γ�¥
        String pgAuthTime = BillData.get("pgAuthTime"); // �̴Ͻý� ���νð�
        String ocbSaveAuthCode = BillData.get("ocbSaveAuthCode"); // OK Cashbag ���� ���ι�ȣ
        String ocbUseAuthCode = BillData.get("ocbUseAuthCode"); // OK Cashbag ��� ���ι�ȣ
        String ocbAuthDate = BillData.get("ocbAuthDate"); // OK Cashbag �����Ͻ�
        String eventFlag = BillData.get("eventFlag"); // ���� �̺�Ʈ ���� ����
        // String 	nohpp 			= BillData.get("nohpp"); 				// �޴��� ������ ���� �޴��� ��ȣ
        // String 	noars 			= BillData.get("noars"); 				// ��ȭ���� �� ���� ��ȭ��ȣ
        String perno = BillData.get("perno"); // �۱��� �ֹι�ȣ
        String vacct = BillData.get("vacct"); // ������¹�ȣ
        String vcdbank = BillData.get("vcdbank"); // �Ա��� �����ڵ�
        String dtinput = BillData.get("dtinput"); // �Աݿ�����
        String nminput = BillData.get("nminput"); // �۱��� ��
        String nmvacct = BillData.get("nmvacct"); // ������ ��
        String moid = BillData.get("moid"); // ���� �ֹ���ȣ
        // String codegw = BillData.get("codegw"); // ��ȭ���� ����� �ڵ�
        String ocbcardnumber = BillData.get("ocbcardnumber"); // OK CASH BAG ����, ������ ��� OK CASH BAG ī�� ��ȣ
        // String cultureid = BillData.get("cultureid"); // ���� ���� ID
        String cardNumber = BillData.get("cardNumber"); // �ſ�ī���ȣ
        String mid = BillData.get("mid"); //���� �ֹ���ȣ
        String goodname = BillData.get("goodname"); //��ǰ��(������)
        String price = BillData.get("price"); //�����ݾ�

        if (price == null || price.equals("")) {
            price = "0";
        }

        // String ispointuse = BillData.get("ispointuse"); //����Ʈ��뿩��
        String vpoint = BillData.get("point"); //����Ʈ
        String buyername = BillData.get("buyername"); //������ �̸�
        String buyertel = BillData.get("buyertel"); //������ ��ȭ��ȣ
        String buyeremail = BillData.get("buyeremail"); //������ EMAIL
        String resulterrCode = BillData.get("resulterrCode"); //���� �ڵ�

        String v_gubun = BillData.get("v_gubun"); //N:�Ϲ�,P:������,B:����
        String userid = BillData.get("userid"); //SESSION ����� ID
        String usernm = BillData.get("usernm"); //SESSION ����� �̸�

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
                //�������� ����
                sql = "insert into tz_billinfo(\n";
                sql += "			grcode,			\n"; //01.grcode
                sql += "			tid,			\n"; //01.�ŷ���ȣ ����:id_�ð�
                sql += "			resultcode,     \n"; //02.����ڵ�
                sql += "			resultmsg,      \n"; //03.�������
                sql += "			paymethod,      \n"; //04.���ҹ��
                sql += "			mid,            \n"; //05.���� ID
                sql += "			goodname,       \n"; //06.������
                sql += "			userid,         \n"; //10.�����ID
                sql += "			usernm,         \n"; //11.�̸�
                sql += "			price,          \n"; //12.�����ݾ�
                sql += "			buyername,      \n"; //13.������ �̸�
                sql += "			buyertel,       \n"; //14.������ ��ȭ��ȣ
                sql += "			buyeremail,     \n"; //15.������ Email
                sql += "			resulterrcode,  \n"; //16.��������ڵ�
                sql += "			price1,         \n"; //17.OkCashBag �ſ�ī�� �ݾ�
                sql += "			price2,         \n"; //18.OkCashBag ����Ʈ �ݾ�
                sql += "			authcode,		\n"; //19.�ſ�ī�� ���ι�ȣ
                sql += "			cardquota,      \n"; //20.�ҺαⰣ
                sql += "			quotainterest,  \n"; //21.������ �Һο���
                sql += "			cardcode,       \n"; //22.�ſ�ī��� �ڵ�
                sql += "			cardissuercode, \n"; //23.ī��߱޻� �ڵ�
                sql += "			authcertain,    \n"; //24.�������� ���࿩��
                sql += "			pgauthdate,     \n"; //25.�̴Ͻý� ���γ�¥
                sql += "			pgauthtime,     \n"; //26.�̴Ͻý� ���νð�
                sql += "			ocbsaveauthcode,\n"; //27.OKCashB �������ι�ȣ
                sql += "			ocbuseauthcode, \n"; //28.OKCashB �����ι�ȣ
                sql += "			ocbauthdate,    \n"; //29.OKCashB �����Ͻ�
                sql += "			eventflag,      \n"; //30.�����̺�Ʈ ���뿩��
                sql += "			perno,          \n"; //31.�۱��� �ֹι�ȣ
                sql += "			vacct,          \n"; //32.������¹�ȣ
                sql += "			vcdbank,        \n"; //33.�Ա��� �����ڵ�
                sql += "			dtinput,        \n"; //34.�Աݿ�����
                sql += "			nminput,        \n"; //35.�۱��ڸ�
                sql += "			nmvacct,        \n"; //36.�����ָ�
                sql += "			moid,           \n"; //37.�����ֹ���ȣ
                sql += "			ocbcardnumber,  \n"; //38.OKCashB ����ī���ȣ
                sql += "			cardNumber,     \n"; //39.�ſ�ī���ȣ
                sql += "			cancelyn,       \n"; //40.��ҿ���
                sql += "			cancelresult,   \n"; //41.��Ұ��
                sql += "			canceldate,     \n"; //42.��ҳ�¥
                sql += "			canceltime,     \n"; //43.��ҽð�
                sql += "			rcashcancelno,  \n"; //44.���ݿ����� ���ι�ȣ
                sql += "			luserid,        \n"; //45.�����
                sql += "			ldate,          \n"; //46.��Ͻð�
                sql += "			point,			\n"; //47. ����Ʈ
                sql += "			gubun,			\n"; //48. ����(N:�Ϲ�,P:��Ű��,B:����)
                sql += "			inputname,		\n"; //49. �Ա��ڸ�
                sql += "			inputdate,		\n"; //50. �Աݿ�����
                sql += "			receive,		\n"; //51. ������ ����
                sql += "			phone,			\n"; //52. ������ ����ó
                sql += "			post1,			\n"; //53. ������ �����ȣ1
                sql += "			post2,			\n"; //54. ������ �����ȣ2
                sql += "			addr1,			\n"; //55. ������ �ּ�1
                sql += "			addr2,discountpercent )      	\n"; //56. ������ �ּ�2, ������
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
                pstmt1.setString(2, tid); //�ŷ���ȣ
                pstmt1.setString(3, resultCode); //����ڵ�
                pstmt1.setString(4, resultMsg); //�������
                pstmt1.setString(5, payMethod); //���ҹ��
                pstmt1.setString(6, mid); //���� ID
                pstmt1.setString(7, goodname); //������
                pstmt1.setString(8, userid); //�����ID
                pstmt1.setString(9, usernm); //�̸�
                pstmt1.setString(10, price); //�����ݾ�
                pstmt1.setString(11, buyername); //������ �̸�
                pstmt1.setString(12, buyertel); //������ ��ȭ��ȣ
                pstmt1.setString(13, buyeremail); //������ Email
                pstmt1.setString(14, resulterrCode); //��������ڵ�
                pstmt1.setString(15, price1); //OkCashBag �ſ�ī�� �ݾ�
                pstmt1.setString(16, price2); //OkCashBag ����Ʈ �ݾ�
                pstmt1.setString(17, authCode); //�ſ�ī�� ���ι�ȣ
                pstmt1.setString(18, cardQuota); //�ҺαⰣ
                pstmt1.setString(19, quotaInterest); //������ �Һο���
                pstmt1.setString(20, cardCode); //�ſ�ī��� �ڵ�
                pstmt1.setString(21, cardIssuerCode); //ī��߱޻� �ڵ�
                pstmt1.setString(22, authCertain); //�������� ���࿩��
                pstmt1.setString(23, pgAuthDate); //�̴Ͻý� ���γ�¥
                pstmt1.setString(24, pgAuthTime); //�̴Ͻý� ���νð�
                pstmt1.setString(25, ocbSaveAuthCode); //OKCashB �������ι�ȣ
                pstmt1.setString(26, ocbUseAuthCode); //OKCashB �����ι�ȣ
                pstmt1.setString(27, ocbAuthDate); //OKCashB �����Ͻ�
                pstmt1.setString(28, eventFlag); //�����̺�Ʈ ���뿩��
                pstmt1.setString(29, perno); //�۱��� �ֹι�ȣ
                pstmt1.setString(30, vacct); //������¹�ȣ
                pstmt1.setString(31, vcdbank); //�Ա��� �����ڵ�
                pstmt1.setString(32, dtinput); //�Աݿ�����
                pstmt1.setString(33, nminput); //�۱��ڸ�
                pstmt1.setString(34, nmvacct); //�����ָ�
                pstmt1.setString(35, moid); //�����ֹ���ȣ
                pstmt1.setString(36, ocbcardnumber); //OKCashB ����ī���ȣ
                pstmt1.setString(37, cardNumber); //�ſ�ī���ȣ
                pstmt1.setString(38, userid); //�����
                pstmt1.setString(39, currentDate); //��Ͻð�
                pstmt1.setString(40, vpoint); //����Ʈ
                pstmt1.setString(41, v_gubun); //����(N:�Ϲ�,P:��Ű��,B:����)
                pstmt1.setString(42, inputname); //������ �Ա��ڸ�
                pstmt1.setString(43, inputdate); //������ �Աݿ�����
                pstmt1.setString(44, receive); //
                pstmt1.setString(45, phone); //
                pstmt1.setString(46, post1); //
                pstmt1.setString(47, post2); //
                pstmt1.setString(48, addr1); //
                pstmt1.setString(49, addr2); //
                pstmt1.setString(50, BillData.get("p_dis")); //

                isOk1 = pstmt1.executeUpdate();

                if (resulterrCode.equals("170204") || resulterrCode.equals("1979��")) {
                    //ISP ���� ������ tz_propose�� Update���� �ʴ´�.(�ڵ� 170204)
                    //������ü ���� ������ tz_propose�� Update���� �ʴ´�.(�ڵ� 1979��)
                    isOk1 = 1;
                    isOk2 = 1;
                    result = 1;
                } else {
                    //������û Insert
                    if (v_gubun.equals("B")) {
                        //����
                        isOk2 = 1;
                    } else {
                        //�Ϲݰ���
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
     * ������û �� ������û ���� ����
     * 
     * @param box receive from the form object and session
     * @return ArrayList �ڷ�� ����Ʈ
     * @throws Exception
     */
    private int insertPropose(DBConnectionManager connMgr, String v_gubun, String v_grcode, String tid, String userid, String subjList, String subjKeyList) throws Exception {

        StringBuffer sql = new StringBuffer();
        PreparedStatement pstmt2 = null;
        ListSet ls = null;
        int isOk2 = 0;

        try {

            if (v_gubun.equals("N")) {//��û�ϱ� �� �� �̹� ��ϵǾ� �����Ƿ�(1�� ������ �ؾ� �� �ʿ䰡 ���� ��� ������ ��û ���̺� ���� ����ϰ� �����) �μ�Ʈ���� �������� ����

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
            } else if (v_gubun.equals("P")) { //��Ű������ - ������
            } else if (v_gubun.equals("B")) {
                //���� ��û
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
     * ��ٱ��� ���� �ڷ� �̵� - ���� �ǹ̴� ���µ� ������ Ȥ�� ��򰡿��� ����� �� ���� �ٽ� �߰�
     * 
     * @param box receive from the form object and session
     * @return ArrayList �ڷ�� ����Ʈ
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
            sql.append("' GUBUN, SUBJ, YEAR, SUBJSEQ,\n 1 UNIT/*å ���Ž� ���*/, 0 PRICE/* å ���� */, 0 AMOUNT /*�հ� ����*/, LDATE\n from 	TZ_PROPOSE A\n WHERE USERID = ?\n AND CHKFINAL = 'B'\n ");
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
     * ��ٱ��� ���� �ڷ� �̵� - ���� �ǹ̴� ���µ� ������ Ȥ�� ��򰡿��� ����� �� ���� �ٽ� �߰�
     * 
     * @param box receive from the form object and session
     * @return ArrayList �ڷ�� ����Ʈ
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
     * payMethod�� ��Ī�� �����ϱ� ���� ������.
     * 
     * @return
     */
    static public DataBox getPayMethod() {
        DataBox data = new DataBox("payMethod");
        data.put("Card", "�ſ�ī��");
        data.put("VCard", "�ſ�ī��");
        data.put("HPP", "�޴���");
        data.put("Ars1588Bill", "1588");
        data.put("PhoneBill", " ����");
        data.put("OCBPoint", " OKCASHBAG");
        data.put("DirectBank", " ���������ü");
        data.put("VBank", " ������ �Ա� ����");
        data.put("Culture", " ��ȭ��ǰ�� ����");
        data.put("TEEN", " ƾĳ�� ����");
        data.put("DGCL", " ���ӹ�ȭ ��ǰ��");
        data.put("BCSH", " ������ȭ ��ǰ��");
        return data;
    }
    //	static public DataBox getPayMethod() {
    //		DataBox data = new DataBox("payMethod");
    //		data.put("Card","�ſ�ī��");
    //		data.put("VCard","ISP");
    //		data.put("HPP","�޴���");
    //		data.put("Ars1588Bill","1588");
    //		data.put("PhoneBill"," ����");
    //		data.put("OCBPoint"," OKCASHBAG");
    //		data.put("DirectBank"," ���������ü");
    //		data.put("VBank"," ������ �Ա� ����");
    //		data.put("Culture"," ��ȭ��ǰ�� ����");
    //		data.put("TEEN"," ƾĳ�� ����");
    //		data.put("DGCL"," ���ӹ�ȭ ��ǰ��");
    //		data.put("BCSH"," ������ȭ ��ǰ��");
    //		return data;
    //	}
}