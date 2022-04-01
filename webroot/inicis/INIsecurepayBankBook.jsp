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
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.account.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	System.out.println("#1");


	String v_gubun = request.getParameter("p_gubun");     //N:일반과정, P:전문가과정, B:도서
	String v_process = request.getParameter("p_process"); //나의강의실에서 진입시 p_process = "update", opener.location.reload()한다.
	String tid = FormatDate.getDate("yyyyMMddHHmmss");
	String pgAuthDate = FormatDate.getDate("yyyyMMdd");
	String pgAuthTime = FormatDate.getDate("HHmmss");

	String goodname   = request.getParameter("goodname");
	String price      = request.getParameter("price");
	String buyername  = request.getParameter("buyername");
	String buyertel   = request.getParameter("buyertel");
	String buyeremail = request.getParameter("buyeremail");
	String paymethod  = request.getParameter("paymethod");

	String v_inputname = request.getParameter("p_inputname");
	String v_inputdate = request.getParameter("p_inputdate");

	//double d = Math.round(Math.random() * 10000);

	//int s = Integer.parseInt(d);
    //System.out.println(d);
    //System.out.println(s);

    System.out.println("v_gubun = " + v_gubun);
    System.out.println("price = " + price);


	String v_receive = request.getParameter("p_receive");
	String v_phone = request.getParameter("p_phone");
	String v_post1 = request.getParameter("p_post1");
	String v_post2 = request.getParameter("p_post2");
	String v_addr1 = request.getParameter("p_addr1");
	String v_addr2 = request.getParameter("p_addr2");

	 try
	 {
	 		//HashTable 저장
			Hashtable BillData = new Hashtable();

			BillData.clear();
			BillData.put("tid" 				, tid			);
			BillData.put("resultCode" 		,"00" 	);
			BillData.put("resultMsg" 		,"[BankBook|성공]" 		);
			BillData.put("payMethod" 		,"BankBook" 		);
			BillData.put("price1" 			,"" 		);
			BillData.put("price2" 			,"" 		);
			BillData.put("authCode" 		,"" 		);
			BillData.put("cardQuota" 		,"" 		);
			BillData.put("quotaInterest" 	,"" 	);
			BillData.put("cardCode" 		,"" 		);
			BillData.put("cardIssuerCode" 	,"");
			BillData.put("authCertain" 	    ,"");
			BillData.put("pgAuthDate" 		,pgAuthDate 	);
			BillData.put("pgAuthTime" 		,pgAuthTime 	);
			BillData.put("ocbSaveAuthCode"  ,"");
			BillData.put("ocbUseAuthCode" 	,"");
			BillData.put("ocbAuthDate" 	    ,"");
			BillData.put("eventFlag" 		,"");
			BillData.put("nohpp" 			,"");
			BillData.put("noars" 			,"");
			BillData.put("perno" 			,"");
			BillData.put("vacct" 			,"");
			BillData.put("vcdbank" 		    ,"");
			BillData.put("dtinput" 		    ,"");
			BillData.put("nminput" 		    ,"");
			BillData.put("nmvacct" 		    ,"");
			BillData.put("moid" 			,"");
			BillData.put("codegw" 			,"");
			BillData.put("ocbcardnumber" 	,"");
			BillData.put("cultureid" 		,"");
			BillData.put("cardNumber" 		,"");
			BillData.put("mid" 			    ,pgAuthDate 	);
			BillData.put("goodname" 		,goodname 		);
			BillData.put("price" 			,price 			);

			String v_ispointuse = request.getParameter("ispointuse");
			if (v_ispointuse==null) v_ispointuse = "N";

			BillData.put("ispointuse"		,v_ispointuse	); //포인트 사용여부
			BillData.put("point" 			,request.getParameter("point") 		);

			System.out.println("req point ------------------" + request.getParameter("point"));

			//사용 포인트
			BillData.put("buyername" 		,buyername 		);
			BillData.put("buyertel" 		,buyertel 		);
			BillData.put("buyeremail" 		,buyeremail 	);

			BillData.put("resulterrCode"	,"00" );

			BillData.put("gubun"            ,v_gubun);
			BillData.put("inputname"        ,v_inputname);
			BillData.put("inputdate"        ,v_inputdate);

			BillData.put("userid"           ,box.getSession("userid")      );
			BillData.put("usernm"           ,box.getSession("name")      );

			BillData.put("receive"          ,v_receive     );
			BillData.put("phone"           	,v_phone     );
			BillData.put("post1"           	,v_post1     );
			BillData.put("post2"           	,v_post2     );
			BillData.put("addr1"           	,v_addr1     );
			BillData.put("addr2"           	,v_addr2     );
			BillData.put("tem_grcode"           	,box.getSession("tem_grcode")     );
			System.out.println("tem_grcode         = " + box.getSession("tem_grcode")         );

			//결제결과 저장
			AccountManagerBean bean = new AccountManagerBean();
	 		//결제결과 저장
	 		int isOk = bean.BillProcess(BillData);

	 		System.out.println("최종결과"+isOk);


	 }
	 catch(Exception e)
	 {
	 	System.out.println("err="+e);
	}
%>

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
	opener.location="/servlet/controller.account.AccountManagerServlet?p_process=BasketList";
<% //} %>

function myclass() {
	//opener.location="/servlet/controller.account.AccountManagerServlet?p_process=BasketList";
	opener.location="/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage";

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
          <td height="35" bgcolor="ddd9cd">
                  <b>입금 후 관리자의 승인 과정이 필요합니다.</b></td>
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
			<td width="118" height="25" align="center" bgcolor="ddd9cd">결제방법 </td>
    	    <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="15" height="8">
    	    <b>무통장 입금 계좌 </b>
			</td>
        </tr>
        <tr>
			<td width="118" height="25" align="center" bgcolor="ddd9cd">은행명 </td>
    	    <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="15" height="8">
    	    <b>국민은행 </b>
			</td>
        </tr>
        <tr>
			<td width="118" height="25" align="center" bgcolor="ddd9cd">계좌번호 </td>
    	    <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="15" height="8">
    	    <b>361437-04-001935 </b>
			</td>
        </tr>
        <tr>
			<td width="118" height="25" align="center" bgcolor="ddd9cd">예금주 </td>
    	    <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="15" height="8">
    	    <b>한국게임산업개발원 </b>
			</td>
        </tr>
        <tr>
			<td width="118" height="25" align="center" bgcolor="ddd9cd">입금금액 </td>
    	    <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="15" height="8">
    	    <b><%=price%> </b>
			</td>
        </tr>

      <table width="100%" height="62" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" ><table width="250" border="0" cellspacing="0" cellpadding="0">
            <tr align="center">
              <td width="60"><a href="javascript:window.close();"><img src="/images/user/button/btn_close.gif" border="0"></a></td>
              <td width="10"></td>
              <td width="176"><a href="javascript:myclass();"><img src="/images/user/game/account/b_my.gif" width="89" height="21" border="0"></a></td>
              </tr>
          </table></td>
        </tr>
      </table>      </td>
  </tr>
</table>
<br>
<br>
</body>
</html>
