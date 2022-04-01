<%------------------------------------------------------------------------------
 FILE NAME : INIsecurepayBankBook.jsp
 AUTHOR : heesung2@credu.com
 DATE : 2005/10
 
------------------------------------------------------------------------------%>
<%@ page contentType="text/html;charset=MS949" %>
<%@ page errorPage="/bin/error.jsp" %>
<%@ include file="/inc/fbean.inc" %>
<jsp:useBean id="bill"  class="cybus.bean.Bill" scope="page" />
<jsp:useBean id="tpoint"  class="cybus.bean.TPoint" scope="page" />

<% if (!UserUtil.hasSession(session))    { UserUtil.goLogin(out); return; } // 세션이 없으면 login 으로

	
	String v_subjcd = request.getParameter("p_subjcd");
	String v_term   = request.getParameter("p_term");
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
	
	//double d = Math.round(Math.random() * 10000);
	
	//int s = Integer.parseInt(d);
    //System.out.println(d);
    //System.out.println(s);

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
			BillData.put("ispointuse"		,request.getParameter("ispointuse")	); //포인트 사용여부
			BillData.put("point" 			,request.getParameter("point") 		); 
			
			System.out.println("req point ------------------" + request.getParameter("point"));

			//사용 포인트
			BillData.put("buyername" 		,buyername 		);
			BillData.put("buyertel" 		,buyertel 		);
			BillData.put("buyeremail" 		,buyeremail 	);
			
			BillData.put("resulterrCode"	,"00" );
			
	 		BillData.put("subjcd"           ,v_subjcd);
			BillData.put("term"             ,v_term  );
			BillData.put("userid"           ,fbean.getSession("userid")      );
			BillData.put("usernm"           ,fbean.getSession("usernm")      );
			BillData.put("subjprice"        ,request.getParameter("subjprice")   );
			
			//결제결과 저장
	 		int isOk = bill.bill_insert(BillData);
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
<% if (v_process.equals("update")) { %>
	opener.location.reload();
<% } %>

function myclass() {
	opener.location="/bin/my_class1.jsp";
	window.close();
}

function bill_retry() {
	document.fbill.submit();
}
-->
</script>
</head>

<%
	DataBox dbox = bill.selectBillSubjectInfo(fbean);
	                       
	String v_subjnm			= "";
	int	   v_subjprice	 	= 0;
	String v_place	 		= "";                       
	String v_begindt 		= "";	                       
	String v_enddt 			= "";                       
	String v_appbegindt 	= "";                       
	String v_appenddt 	    = "";                       
	String v_acceptdt 	    = "";                       
	String v_acceptenddt 	= "";
	String v_onoff			= "";                       
	                       
	                       
	
	if (dbox != null) {
		//과정정보
		v_subjnm		= dbox.getString("d_subjnm");
		v_subjprice	 	= dbox.getInt("d_price");
		v_place	 		= dbox.getString("d_place");
		v_begindt 		= dbox.getString("d_begindt");
		v_enddt 		= dbox.getString("d_enddt");
		v_appbegindt 	= dbox.getString("d_appbegindt");
		v_appenddt 	    = dbox.getString("d_appenddt");
		v_acceptdt 	    = dbox.getString("d_acceptdt");
		v_acceptenddt 	= dbox.getString("d_acceptenddt");
		v_onoff			= dbox.getString("d_onoff");
	}
%>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="#f3f3eb">
<table width="590" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="/img/applica_top.gif" width="590" height="54"></td>
  </tr>
</table>
<table width="590" border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td align="center" bgcolor="f3f3eb"><br>      <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td><img src="/img/title_1.gif" width="141" height="24"></td>
      </tr>
    </table>    <br>    
      <table width="508" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td><img src="/img/bul_arrow.gif" width="10" height="10"> 
                  <b>입금 후 관리자의 승인 과정이 필요합니다. </b></td>
        </tr>
    </table>      
      <br>
      <!--table width="508" border="0" cellpadding="0" cellspacing="0">
        <tr>
            <td><img src="/img/bul_arrow.gif" width="10" height="10"> 과정정보</td>
        </tr>
    </table>      
      <br-->
      <table width="508" height="1" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td bgcolor="c1bba5"></td>
        </tr>
      </table>
        <table width="508" border="0" cellpadding="0" cellspacing="1" bgcolor="c1bba5">
          <tr> 
            <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">과정명</td>
            <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"> 
              <input type=hidden name=goodname size=20 value="<%=v_subjnm%>"> 
              <%=v_subjnm%></td>
          </tr>
          <tr> 
            <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">차수</td>
            <td height="25" bgcolor="#FFFFFF"> <img src="/img/spacer.gif" width="24" height="8"><%=v_term%> 
            </td>
          </tr>
          <tr> 
            <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">수강신청일</td>
            <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=FormatDate.formatDate(v_appbegindt, "yyyy/MM/dd")%> 
              ~ <%=FormatDate.formatDate(v_appenddt, "yyyy/MM/dd")%></td>
          </tr>
          <tr> 
            <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">교육일</td>
            <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"><%=FormatDate.formatDate(v_begindt, "yyyy/MM/dd")%> 
              ~ <%=FormatDate.formatDate(v_enddt, "yyyy/MM/dd")%></td>
          </tr>
          <tr> 
            <td width="118" height="25" align="center" background="/img/td_bg.gif" bgcolor="ddd9cd">교육비</td>
            <td height="25" bgcolor="#FFFFFF"><img src="/img/spacer.gif" width="24" height="8"> 
              <%=new java.text.DecimalFormat("###,###,###").format(v_subjprice)%> 
              <input type="hidden" name="subjprice" value="<%=v_subjprice%>"> 
            </td>
          </tr>
        </table>
      <table width="508" height="1" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td bgcolor="c1bba5"></td>
        </tr>
      </table>
      

      <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td></td>
      </tr>
    </table>    
      <table width="100%" height="62" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="center" ><table width="250" border="0" cellspacing="0" cellpadding="0">
            <tr align="center">
              <td width="60"><a href="javascript:window.close();"><img src="/img/btn_close.gif" width="56" height="19" border="0"></a></td>
              <td width="10"></td>
              <td width="176"><a href="javascript:myclass();"><img src="/img/btn_move.gif" width="176" height="19" border="0"></a></td>
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
