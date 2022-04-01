<%------------------------------------------------------------------------------
 FILE NAME : INIsecurepay.jsp
 AUTHOR : jwkim@inicis.com
 DATE : 2004/07
 USE WITH : INIsecurepay.html
 
 �̴����� �÷������� ���� ��û�� ������ ó���Ѵ�.
                                                          http://www.inicis.com
                                                      http://support.inicis.com
                            Copyright 2004 Inicis Co., Ltd. All rights reserved.
------------------------------------------------------------------------------%>


<%@ page language="java" contentType="text/html; charset=euc-kr"
	import="com.inicis.inipay.*"%>

<% 
	/**************************************
	 * 1. INIpay41 Ŭ������ �ν��Ͻ� ���� *
	 **************************************/
	INIpay41 inipay = new INIpay41();
	
	
	/*********************
	 * 2. ���� ���� ���� *
	 *********************/
	 
	 /************************************************************************************
	 * �ϴ��� PGID �κ��� ���Ҽ��ܺ��� TID�� ������ ǥ���ϵ��� �ϸ�,                     *
	 * ���Ƿ� �����Ͽ� �߻��� ������ ���ؼ��� (��)�̴Ͻý��� å����                      *
	 * ������ �ҽ� ������ ���Ǹ� �ٶ��ϴ�					             *
	 ************************************************************************************/
			
	String pgid = "";
	String paymethod = request.getParameter("paymethod");
	if(paymethod.equals("Card")){
		pgid = "CARD"; //�ſ�ī��
	}
	else if(paymethod.equals("Account")){
		pgid = "ACCT"; // ���� ���� ��ü
	}
	else if(paymethod.equals("DirectBank")){
		pgid = "DBNK"; // �ǽð� ���� ��ü
	}
	else if(paymethod.equals("OCBPoint")){
		pgid = "OCBP"; // OCB
	}
	else if(paymethod.equals("VCard")){
		pgid = "ISP_"; // ISP ����
	}
	else if(paymethod.equals("HPP")){
		pgid = "HPP_"; // �޴��� ����
	}
	else if(paymethod.equals("Nemo")){
		pgid = "NEMO"; // NEMO ����
	}
	else if(paymethod.equals("ArsBill")){
		pgid = "ARSB"; // 700 ��ȭ����
	}
	else if(paymethod.equals("PhoneBill")){
		pgid = "PHNB"; // PhoneBill ����(�޴� ��ȭ)
	}
	else if(paymethod.equals("Ars1588Bill")){
		pgid = "1588"; // 1588 ��ȭ����
	}
	else if(paymethod.equals("VBank")){
		pgid = "VBNK"; // ������� ��ü
	}
	else if(paymethod.equals("Culture")){
		pgid = "CULT"; // ��ȭ��ǰ�� ����
	}
	else if(paymethod.equals("CMS")){
		pgid = "CMS_"; // CMS ����
	}
	else if(paymethod.equals("AUTH")){
		pgid = "AUTH"; // �ſ�ī�� ��ȿ�� �˻�
	}
	else if(paymethod.equals("INIcard")){
		pgid = "INIC"; // ��Ƽ�Ӵ� ����
	}
	else if(paymethod.equals("MDX")){
		pgid = "MDX_"; // �󵦽�ī��
	}
		else if(paymethod.equals("TEEN")){
		pgid = "TEEN"; // ƾĳ��
	}
	else{
		pgid = paymethod;
	}	
	
	inipay.setInipayHome("C:/INIpay41_JAVA"); // INIpay Home (�����η� ������ ����)
	inipay.setKeyPw("1111"); // Ű�н�����(�������̵� ���� ����)
	inipay.setType("securepay"); // ����
	inipay.setPgId("INIpay"+pgid); // ����
	inipay.setSubPgIp("203.238.3.10"); // ����
	inipay.setDebug("true"); // �α׸��("true"�� �����ϸ� ���� �αװ� ������)
	inipay.setMid(request.getParameter("mid")); // �������̵�
	inipay.setUid(request.getParameter("uid")); // ����(���� ���� �Ұ�)
	inipay.setUip(request.getRemoteAddr()); // ����
	inipay.setGoodName(request.getParameter("goodname"));
	inipay.setCurrency(request.getParameter("currency"));
	inipay.setPrice(request.getParameter("price"));
	inipay.setBuyerName(request.getParameter("buyername"));
	inipay.setBuyerTel(request.getParameter("buyertel"));
	inipay.setBuyerEmail(request.getParameter("buyeremail"));
	inipay.setParentEmail(request.getParameter("parentemail")); // ��ȣ�� �̸��� �ּ�(�ڵ��� , ��ȭ�����ÿ� 14�� �̸��� ���� �����ϸ�  �θ� �̸��Ϸ� ���� �����뺸 �ǹ�, �ٸ����� ���� ���ÿ� ���� ����)
	inipay.setPayMethod(request.getParameter("paymethod"));
	inipay.setEncrypted(request.getParameter("encrypted"));
	inipay.setSessionKey(request.getParameter("sessionkey"));
	inipay.setUrl("http://www.your_domain.co.kr"); // ���� ���񽺵Ǵ� ���� SITE URL�� �����Ұ�
	inipay.setCardCode(request.getParameter("cardcode")); // �÷����ο��� ���ϵǴ� ī���ڵ� ���ڸ�
	/*-----------------------------------------------------------------*
	 * ������ ���� *                                                   *
	 *-----------------------------------------------------------------*
	 * �ǹ������ �ϴ� ������ ��쿡 ���Ǵ� �ʵ���̸�               *
	 * �Ʒ��� ������ INIsecurepay.html ���������� ����Ʈ �ǵ���        *
	 * �ʵ带 ����� �ֵ��� �Ͻʽÿ�.                                  *
	 * ������ ������ü�� ��� �����ϼŵ� �����մϴ�.                   *
	 *-----------------------------------------------------------------*/
	inipay.setRecvName(request.getParameter("recvname")); 	// ������ ��
	inipay.setRecvTel(request.getParameter("recvtel"));	// ������ ����ó
	inipay.setRecvAddr(request.getParameter("recvaddr"));	// ������ �ּ�
	inipay.setRecvPostNum(request.getParameter("recvpostnum"));	// ������ �����ȣ
	
	
	/*--------------------------------------------------------------*
	 * ������ �Һ� �ŷ��� ��� �Һΰ��� �ڿ� ������ ���θ� ǥ���Ѵ�.*
	 *--------------------------------------------------------------*/
	String interest = "";
	String quotainterest = request.getParameter("quotainterest");
	if(quotainterest.equals("1")){
		interest = "(������ �Һ�)";
	}
	/*--------------------------------------------------------------*/
	
	
	/*
	 * �̴Ͻý� ��ȸ ������(https://iniweb.inicis.com)���� ���̴�
	 * �ѱ��� ������ ��� �Ʒ� 3�� �� �ϳ��� ���(�ּ�����)�Ͽ� 
	 * �ѱ��� ����� ���̴� ���� ����Ͻʽÿ�.
	 */
	
	//inipay.setEncoding(1);
	//inipay.setEncoding(2);
	//inipay.setEncoding(3);

	
	/****************
	 * 3. ���� ��û *
	 ****************/
	inipay.startAction();
	
	
	/****************
	 * 4. ���� ��� *
	 ****************/
	 
	 String tid = inipay.getTid(); // �ŷ���ȣ
	 String resultCode = inipay.getResultCode(); // ����ڵ� ("00"�̸� ���� ����)
	 String resultMsg = inipay.getResultMsg(); // ������� (���Ұ���� ���� ����)
	 String payMethod = inipay.getPayMethod(); // ���ҹ�� (�Ŵ��� ����)
	 String price1 = inipay.getPrice1(); // OK Cashbag ���հ���� �ſ�ī�� ���ұݾ�
	 String price2 = inipay.getPrice2(); // OK Cashbag ���հ���� ����Ʈ ���ұݾ�
	 String authCode = inipay.getAuthCode(); // �ſ�ī�� ���ι�ȣ
	 String cardQuota = inipay.getCardQuota(); // �ҺαⰣ
	 String quotaInterest = inipay.getQuotaInterest(); // �������Һ� ���� ("1"�̸� �������Һ�)
	 String cardCode = inipay.getCardCode(); // �ſ�ī��� �ڵ� (�Ŵ��� ����)
	 String cardIssuerCode = inipay.getCardIssuerCode(); // ī��߱޻� �ڵ� (�Ŵ��� ����)
	 String authCertain = inipay.getAuthCertain(); // �������� ���࿩�� ("00"�̸� ����)
	 String pgAuthDate = inipay.getPgAuthDate(); // �̴Ͻý� ���γ�¥
	 String pgAuthTime = inipay.getPgAuthTime(); // �̴Ͻý� ���νð�
	 String ocbSaveAuthCode = inipay.getOcbSaveAuthCode(); // OK Cashbag ���� ���ι�ȣ
	 String ocbUseAuthCode = inipay.getOcbUseAuthCode(); // OK Cashbag ��� ���ι�ȣ
	 String ocbAuthDate = inipay.getOcbAuthDate(); // OK Cashbag �����Ͻ�
	 String eventFlag = inipay.getEventFlag(); // ���� �̺�Ʈ ���� ����
	 String nohpp = inipay.getNoHpp(); // �޴��� ������ ���� �޴��� ��ȣ
	 String noars = inipay.getNoArs(); // ��ȭ���� �� ���� ��ȭ��ȣ  
	 String perno = inipay.getPerno(); // �۱��� �ֹι�ȣ
	 String vacct = inipay.getVacct(); // ������¹�ȣ
	 String vcdbank = inipay.getVcdbank(); // �Ա��� �����ڵ�
	 String dtinput = inipay.getDtinput(); // �Աݿ�����
	 String nminput = inipay.getNminput(); // �۱��� ��
	 String nmvacct = inipay.getNmvacct(); // ������ ��
	 String moid = inipay.getmoid(); // ���� �ֹ���ȣ
	 String codegw = inipay.getcodegw(); // ��ȭ���� ����� �ڵ�
	 String ocbcardnumber = inipay.getocbcardnumber(); // OK CASH BAG ���� , ������ ��� OK CASH BAG ī�� ��ȣ
	 String cultureid = inipay.getcultureid(); // ���� ���� ID
	 String cardNumber = inipay.getCardNumber(); // �ſ�ī���ȣ  	 
	 String mid = request.getParameter("mid");
	 String goodname = request.getParameter("goodname");
	 String price = request.getParameter("price");
	 String buyername = request.getParameter("buyername");
	 String buyertel = request.getParameter("buyertel");
	 String buyeremail = request.getParameter("buyeremail");
	 
	// ƾĳ�� ���������� �̿�ÿ���  ���� ��� ����	
	String RemainPrice = inipay.getRemainPrice();		//ƾĳ�������� �ܾ�

	 
	/*-------------------------------------------------------------------------*
	 * �����߻��� ��� �޼������� �����ڵ带 �����ϴ� �κ����� ���� ���� ����  *
	 *-------------------------------------------------------------------------*/
	 String tmp_ErrCode[] = resultMsg.split("]");
	 String resulterrCode = resultMsg.substring(1,tmp_ErrCode[0].length()); 
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
	 	// DB CODE HERE
	 }
	 catch(Exception e)
	 {
		inipay.setType("cancel"); // ����
		inipay.setCancelMsg("DB FAIL"); // ��һ���
		inipay.startAction();
		
		resultCode = "01";
		resultMsg = "DB FAIL";
	}
%>
<!-------------------------------------------------------------------------------------------------------
 *  													*
 *       												*
 *        												*
 *	�Ʒ� ������ ���� ����� ���� ��� ������ �����Դϴ�. 				                *
 *	���� ���� ���ܺ� ��������� ������ ��µǵ��� �Ǿ� �����Ƿ� �ҽ� �ľ��� ����� ���             *
 *      ������ ��� ������ ���� �ִ� �����ڿ� ���������� (INIsecurepay_dev.jsp)�� ���� �Ͻñ� �ٶ��ϴ�.	*
 *													*
 *													*
 *													*
 -------------------------------------------------------------------------------------------------------->

<html>
<head>
<title>INIpay41 ���������� ����</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="css/group.css" type="text/css">
<style>
body,tr,td {
	font-size: 10pt;
	font-family: ����, verdana;
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
	font-family: ����, verdana;
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
	var openwin=window.open("childwin.html","childwin","width=300,height=160");
	openwin.close();
	
	/*------------------------------------------------------------------------------------------------------*
         * 1. $inipay->m_resultCode 										*
         *       ��. �� �� �� ��: "00" �� ��� ���� ����[�������Ա��� ��� - ������ �������Ա� ��û�� �Ϸ�]	*
         *       ��. �� �� �� ��: "00"���� ���� ��� ���� ����  						*
         *------------------------------------------------------------------------------------------------------*/
	
	function show_receipt() // ������ ���
	{
		if("<%=resultCode%>" == "00")
		{
			var receiptUrl = "https://iniweb.inicis.com/DefaultWebApp/mall/cr/cm/mCmReceipt_head.jsp?noTid=" + "<%=tid%>" + "&noMethod=1";
			window.open(receiptUrl,"receipt","width=430,height=700");
		}
		else
		{
			alert("�ش��ϴ� ���������� �����ϴ�");
		}
	}
		
	function errhelp() // �� �������� ���
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
 * ���� ����� ���� ��� �̹����� ���� �ȴ�								*
 * 	 ��. ���� ���� �ÿ� "img/spool_top.gif" �̹��� ���						*
 *       ��. ���� ����� ���� ��� �̹����� ����							*
 *       	��. �ſ�ī�� 	- 	"img/card.gif"							*
 *		��. ISP		-	"img/card.gif"							*
 *		��. �������	-	"img/bank.gif"							*
 *		��. �������Ա�	-	"img/bank.gif"							*
 *		��. �ڵ���	- 	"img/hpp.gif"							*
 *		��. ��ȭ���� (ars��ȭ ����)	-	"img/phone.gif"					*
 *		��. ��ȭ���� (�޴���ȭ����)	-	"img/phone.gif"					*
 *		��. OK CASH BAG POINT		-	"img/okcash.gif"				*
 *		��. ��ȭ��ǰ��	-	"img/ticket.gif"						*
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
    					else if(payMethod.equals("HPP")){ // �޴���
						out.println("img/hpp.gif");
					}
    					else if(payMethod.equals("Ars1588Bill")){ // 1588
						out.println("img/phone.gif");
					}
    					else if(payMethod.equals("PhoneBill")){ // ����
						out.println("img/phone.gif");
					}
    					else if(payMethod.equals("OCBPoint")){ // OKCASHBAG
						out.println("img/okcash.gif");
					}
    					else if(payMethod.equals("DirectBank")){  // ���������ü
						out.println("img/bank.gif");
					}
    					else if(payMethod.equals("VBank")){  // ������ �Ա� ����
						out.println("img/bank.gif");
					}
    					else if(payMethod.equals("Culture")){  // ��ȭ��ǰ�� ����
						out.println("img/ticket.gif");
					}
						else if(payMethod.equals("TEEN")){  // ƾĳ�� ����
						out.println("img/teen_top.gif");
					}
					else{
						out.println("img/card.gif");
					}// ��Ÿ ���Ҽ����� ���
				}
					
    				%>
			style="padding: 0 0 0 64"><!-------------------------------------------------------------------------------------------------------
 *													*
 *  �Ʒ� �κ��� ��� ���������� �������� ����޼��� ��� �κ��Դϴ�.					*
 *  													*
 *	1. resultCode 	(�� �� �� ��) 									*
 *  	2. resultMsg	(��� �޼���)									*
 *  	3. payMethod	(�� �� �� ��)									*
 *  	4. tid		(�� �� �� ȣ)									*
 *  	5. moid  	(�� �� �� ȣ)									*
 -------------------------------------------------------------------------------------------------------->

		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td width="3%" valign="top"><img src="img/title_01.gif"
					width="8" height="27" vspace="5"></td>
				<td width="97%" height="40" class="pl_03"><font color="#FFFFFF"><b>�������</b></font></td>
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
                 * 1. resultCode 											*	
                 *       ��. �� �� �� ��: "00" �� ��� ���� ����[�������Ա��� ��� - ������ �������Ա� ��û�� �Ϸ�]	*
                 *       ��. �� �� �� ��: "00"���� ���� ��� ���� ����  						*
                 -------------------------------------------------------------------------------------------------------->
						<b>
						<% if(resultCode.equals("00") && payMethod.equals("VBank")){ out.println("������ �������Ա� ��û�� �Ϸ�Ǿ����ϴ�.");}
                  	   else if(resultCode.equals("00")){ out.println("������ ������û�� �����Ǿ����ϴ�.");}
                           else{ out.println("������ ������û�� ���еǾ����ϴ�.");} %>
						</b></td>
						<td width="8"><img src="img/right.gif" width="8" height="30"></td>
					</tr>
				</table>
				<br>
				<table width="510" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td width="407" style="padding: 0 0 0 9"><img
							src="img/icon.gif" width="10" height="11"> <strong><font
							color="433F37">��������</font></strong></td>
						<td width="103">&nbsp;</td>
					</tr>
					<tr>
						<td colspan="2" style="padding: 0 0 0 23">
						<table width="470" border="0" cellspacing="0" cellpadding="0">
							<tr>

								<!-------------------------------------------------------------------------------------------------------
                 * 2. m_payMethod 										*	
                 *       ��. ���� ����� ���� ��									*
                 *       	��. �ſ�ī�� 	- 	Card								*
                 *		��. ISP		-	VCard								*	
                 *		��. �������	-	DirectBank							*
                 *		��. �������Ա�	-	VBank								*
                 *		��. �ڵ���	- 	HPP								*
                 *		��. ��ȭ���� (ars��ȭ ����)	-	Ars1588Bill					*
                 *		��. ��ȭ���� (�޴���ȭ����)	-	PhoneBill					*
                 *		��. OK CASH BAG POINT		-	OCBPoint					*
                 *		��. ��ȭ��ǰ��	-	Culture								*
                 -------------------------------------------------------------------------------------------------------->
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ��</td>
								<td width="343"><%=payMethod%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="26">�� �� �� ��</td>
								<td width="343">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td><%=resultCode%></td>
										<td width="142" align="right"><!-------------------------------------------------------------------------------------------------------
                 * 2. $inipay->m_resultCode ���� ���� "������ ����" �Ǵ� "���� ���� �ڼ��� ����" ��ư ���		*
                 *       ��. ���� �ڵ��� ���� "00"�� ��쿡�� "������ ����" ��ư ���					*
                 *       ��. ���� �ڵ��� ���� "00" ���� ���� ��쿡�� "���� ���� �ڼ��� ����" ��ư ���			*
                 -------------------------------------------------------------------------------------------------------->
										<!-- ���а�� �� ���� ��ư ��� --> <%
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
                 * 1. resultMsg 											*
                 *       ��. ��� ������ ���� �ش� ���нÿ��� "[�����ڵ�] ���� �޼���" ���·� ���� �ش�.                *
                 *		��> [9121]����Ȯ�ο���									*
                 -------------------------------------------------------------------------------------------------------->
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ��</td>
								<td width="343"><%=resultMsg%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>

							<!-------------------------------------------------------------------------------------------------------
                 * 1. $inipay->m_tid											*
                 *       ��. �̴Ͻý��� �ο��� �ŷ� ��ȣ -��� �ŷ��� ������ �� �ִ� Ű�� �Ǵ� ��			*
                 -------------------------------------------------------------------------------------------------------->
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ȣ</td>
								<td width="343"><%=tid%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>

							<!-------------------------------------------------------------------------------------------------------
                 * 1. $inipay->m_moid											*
                 *       ��. �������� �Ҵ��� �ֹ���ȣ 									*
                 -------------------------------------------------------------------------------------------------------->
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ȣ</td>
								<td width="343"><%=moid%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>


							<%                    
                    

	/*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  1.  �ſ�ī�� , ISP ���� ��� ��� (OK CASH BAG POINT ���� ���� ���� )				*
	 -------------------------------------------------------------------------------------------------------*/

	if(payMethod.equals("Card") || payMethod.equals("VCard")){
%>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�ſ�ī���ȣ</td>
								<td width="343"><%=cardNumber%>****</td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ¥</td>
								<td width="343"><%=pgAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ��</td>
								<td width="343"><%=pgAuthTime%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ȣ</td>
								<td width="343"><%=authCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ��</td>
								<td width="343"><%=cardQuota%>����&nbsp;<b><font
									color="red"><%=interest%></font></b></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">ī �� �� ��</td>
								<td width="343"><%=cardCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">ī��߱޻�</td>
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
								<td style="padding: 0 0 0 9" colspan="3"><img
									src="img/icon.gif" width="10" height="11"> <strong><font
									color="433F37">OK CASHBAG ���� �� ��볻��</font></strong></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">ī �� �� ȣ</td>
								<td width="343"><%=ocbcardnumber%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">���� ���ι�ȣ</td>
								<td width="343"><%=ocbSaveAuthCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">��� ���ι�ȣ</td>
								<td width="343"><%=ocbUseAuthCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ��</td>
								<td width="343"><%=ocbAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">����Ʈ���ұݾ�</td>
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
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  2.  ������°��� ��� ��� 										*
	 -------------------------------------------------------------------------------------------------------*/
	 
          else if(payMethod.equals("DirectBank")){
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ¥</td>
								<td width="343"><%=pgAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ��</td>
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
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  3.  �������Ա� �Ա� ���� ��� ��� (���� ������ �ƴ� �Ա� ���� ���� ����)				*
	 -------------------------------------------------------------------------------------------------------*/
	 
          else if(payMethod.equals("VBank")){
 
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�Աݰ��¹�ȣ</td>
								<td width="343"><%=vacct%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�Ա� �����ڵ�</td>
								<td width="343"><%=vcdbank%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">������ ��</td>
								<td width="343"><%=nmvacct%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�۱��� ��</td>
								<td width="343"><%=nminput%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�۱��� �ֹι�ȣ</td>
								<td width="343"><%=perno%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">��ǰ �ֹ���ȣ</td>
								<td width="343"><%=moid%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�Ա� ��������</td>
								<td width="343"><%=dtinput%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<%                    		
          }
          
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  4.  �ڵ��� ���� 											*
	 -------------------------------------------------------------------------------------------------------*/
	 
          else if(payMethod.equals("HPP")){
  
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�޴�����ȣ</td>
								<td width="343"><%=nohpp%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ¥</td>
								<td width="343"><%=pgAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ��</td>
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
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  4.  ��ȭ ���� 											*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if(payMethod.equals("Ars1588Bill") || payMethod.equals("PhoneBill")){

%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� ȭ �� ȣ</td>
								<td width="343"><%=noars%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ¥</td>
								<td width="343"><%=pgAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ��</td>
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
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  4.  OK CASH BAG POINT ���� �� ���� 									*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if(payMethod.equals("OCBPoint")){
         	
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">ī �� �� ȣ</td>
								<td width="343"><%=ocbcardnumber%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ¥</td>
								<td width="343"><%=pgAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ��</td>
								<td width="343"><%=pgAuthTime%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">���� ���ι�ȣ</td>
								<td width="343"><%=ocbSaveAuthCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">��� ���ι�ȣ</td>
								<td width="343"><%=ocbUseAuthCode%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">�� �� �� ��</td>
								<td width="343"><%=ocbAuthDate%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">����Ʈ���ұݾ�</td>
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
	 *  �Ʒ� �κ��� ���� ���ܺ� ��� �޼��� ��� �κ��Դϴ�.    						*	
	 *													*
	 *  4.  ��ȭ ��ǰ��						                			*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if(payMethod.equals("Culture")){
         	
%>

							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">���ķ��� ID</td>
								<td width="343"><%=cultureid%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>

							<%                                
         }
         
		/*-------------------------------------------------------------------------------------------------------
	 	*  5.ƾĳ��
	 	-------------------------------------------------------------------------------------------------------*/
         else if(payMethod.equals("TEEN")){
		%>
							<tr>
								<td width="18" align="center"><img src="img/icon02.gif"
									width="7" height="7"></td>
								<td width="109" height="25">ƾĳ�� ��� �ܾ�</td>
								<td width="343"><%=RemainPrice%></td>
							</tr>
							<tr>
								<td height="1" colspan="3" align="center"
									background="img/line.gif"></td>
							</tr>

							<%                                
         }
		%>


						</table>
						</td>
					</tr>
				</table>
				<br>

				<!-------------------------------------------------------------------------------------------------------
 *													*
 *  ���� ������($inipay->m_resultCode == "00"�� ��� ) "�̿�ȳ�"  �����ֱ� �κ��Դϴ�.			*	    
 *  ���� ���ܺ��� �̿������ ���� ���ܿ� ���� ���� ������ ���� �ݴϴ�. 				*
 *  switch , case�� ���·� ���� ���ܺ��� ��� �ϰ� �ֽ��ϴ�.						*
 *  �Ʒ� ������ ��� �մϴ�.										*
 *													*
 *  1.	�ſ�ī�� 											*
 *  2.  ISP ���� 											*
 *  3.  �ڵ��� 												*
 *  4.  ��ȭ ���� (1588Bill)										*
 *  5.  ��ȭ ���� (PhoneBill)										*
 *  6.	OK CASH BAG POINT										*
 *  7.  ���������ü											*
 *  8.  ������ �Ա� ����										*
 *  9.  ��ȭ��ǰ�� ����											*	
 ------------------------------------------------------------------------------------------------------->

				<%
            	
            	if(resultCode.equals("00")){
            		
            		        /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  1.  �ſ�ī�� 						                			*
	 			--------------------------------------------------------------------------------------------------------*/
	
				if(payMethod.equals("Card")){ 
%>
				<table width="510"
					border="0' cellspacing='0' cellpadding='0'>
         					<tr> 
         					    <td height='25'  style='padding:0 0 0 9'><img src='img/icon.gif' width='10' height='11'> 
         					      <strong><font color='433F37'>�̿�ȳ�</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style='padding:0 0 0 23'> 
         					      <table width='470' border='0' cellspacing='0' cellpadding='0'>
         					        <tr>          					          
         					          <td height='25'>(1) �ſ�ī�� û������ <b>"�̴Ͻý�(inicis.com)"
					</b>���� ǥ��˴ϴ�.<br>
         					          (2) LGī�� �� BCī���� ��� <b>"�̴Ͻý�(�̿� ������)"</b>���� ǥ��ǰ�, �Ｚī���� ��� <b>"�̴Ͻý�(�̿���� URL)"</b>�� ǥ��˴ϴ�.</td>
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
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  2.  ISP 						                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("VCard")){
%>
		<table width='510' border='0' cellspacing='0' cellpadding='0'>
			<tr>
				<td height='25' style='padding: 0 0 0 9'><img
					src='img/icon.gif' width='10' height='11'> <strong><font
					color='433F37'>�̿�ȳ�</font></strong></td>
			</tr>
			<tr>
				<td style='padding: 0 0 0 23'>
				<table width='470' border='0' cellspacing='0' cellpadding='0'>
					<tr>
						<td height='25'>(1) �ſ�ī�� û������ <b>"�̴Ͻý�(inicis.com)"</b>����
						ǥ��˴ϴ�.<br>
						(2) LGī�� �� BCī���� ��� <b>"�̴Ͻý�(�̿� ������)"</b>���� ǥ��ǰ�, �Ｚī���� ��� <b>"�̴Ͻý�(�̿����
						URL)"</b>�� ǥ��˴ϴ�.</td>
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
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  3. �ڵ��� 						                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("HPP")){
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">�̿�ȳ�</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) �ڵ��� û������ <b>"�Ҿװ���"</b> �Ǵ� <b>"�ܺ������̿��"</b>��
						û���˴ϴ�.<br>
						(2) ������ �� �ѵ��ݾ��� Ȯ���Ͻð��� �� ��� �� �̵���Ż��� �����͸� �̿����ֽʽÿ�.</td>
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
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  4. ��ȭ ���� (ARS1588Bill)				                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("Ars1588Bill")){ 
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">�̿�ȳ�</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) ��ȭ û������ <b>"������ �̿��"</b>�� û���˴ϴ�.<br>
						(2) �� �ѵ��ݾ��� ��� ������ �������� ��� ��ϵ� ��ȭ��ȣ ������ �ƴ� �ֹε�Ϲ�ȣ�� �������� å���Ǿ� �ֽ��ϴ�.<br>
						(3) ��ȭ ������Ҵ� ������� �����մϴ�.</td>
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
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  5. ���� ���� (PhoneBill)				                				*
	 			--------------------------------------------------------------------------------------------------------*/
				
				else if(payMethod.equals("PhoneBill")){
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">�̿�ȳ�</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) ��ȭ û������ <b>"���ͳ� ������ (����)�����̿��"</b>��
						û���˴ϴ�.<br>
						(2) �� �ѵ��ݾ��� ��� ������ �������� ��� ��ϵ� ��ȭ��ȣ ������ �ƴ� �ֹε�Ϲ�ȣ�� �������� å���Ǿ� �ֽ��ϴ�.<br>
						(3) ��ȭ ������Ҵ� ������� �����մϴ�.</td>
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
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  6. OK CASH BAG POINT					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("OCBPoint")){ 
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">�̿�ȳ�</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) OK CASH BAG ����Ʈ ������Ҵ� ������� �����մϴ�.</td>
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
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  7. ���������ü					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("DirectBank")){  
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">�̿�ȳ�</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) ������ ���忡�� <b>"�̴Ͻý�"</b>�� ǥ��˴ϴ�.<br>
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
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  8. ������ �Ա� ����					                				*
	 			--------------------------------------------------------------------------------------------------------*/		
				
				else if(payMethod.equals("VBank")){  
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">�̿�ȳ�</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						(1) ��� ����� �Աݿ����� �Ϸ�� ���ϻ� ���� �ԱݿϷᰡ �̷���� ���� �ƴմϴ�.
						<br>
						(2) ��� �Աݰ��·� �ش� ��ǰ�ݾ��� �������Ա�(â���Ա�)�Ͻðų�, ���ͳ� ��ŷ ���� ���� �¶��� �۱��� �Ͻñ�
						�ٶ��ϴ�.
						<br>
						(3) �ݵ�� �Աݱ��� ���� �Ա��Ͻñ� �ٶ��, ����Աݽ� �ݵ�� �ֹ��Ͻ� �ݾ׸� �Ա��Ͻñ� �ٶ��ϴ�.
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
	 			* ���� ������ �̿�ȳ� �����ֱ� 			    						*	
				*													*
	 			*  9. ��ȭ��ǰ�� ����					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("Culture")){
%>
		<table width="510" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="25" style="padding: 0 0 0 9"><img
					src="img/icon.gif" width="10" height="11"> <strong><font
					color="433F37">�̿�ȳ�</font></strong></td>
			</tr>
			<tr>
				<td style="padding: 0 0 0 23">
				<table width="470" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<td height="25">(1) ��ȭ��ǰ���� �¶��ο��� �̿��Ͻ� ��� �������ο����� ����Ͻ� �� �����ϴ�.<br>
						(2) ����ĳ�� �ܾ��� �����ִ� ���, ������ ����ĳ�� �ܾ��� �ٽ� ����Ͻ÷��� ���ķ��� ID�� ����Ͻñ� �ٶ��ϴ�.
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
			
		}
		
%> <!-- �̿�ȳ� �� --></td>
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