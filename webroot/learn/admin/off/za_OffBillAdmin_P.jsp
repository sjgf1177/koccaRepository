 <%
//**********************************************************
//  1. 제      목: 수강료 납부관리 정보 수정
//  2. 프로그램명: za_OffBillAdmin_U
//  3. 개      요: 수강료 납부관리 결제정보 수정(팝업)
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.20
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");

    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");

    String  ss_year			    = box.getString("s_year");			//년도
	String  ss_subj             = box.getString("s_subjcode");	  	//과정
    String  ss_mastercd			= box.getString("s_mastercd");		//과정&코스
    String  ss_upperclass		= box.getString("s_upperclass");	//과정대분류
    String  ss_middleclass		= box.getString("s_middleclass");	//과정중분류
    String  ss_lowerclass		= box.getString("s_lowerclass");	//과정소분류
    String  ss_paystatus		= box.getString("s_paystatus");	    //결제상태
    String  ss_startdate		= box.getString("s_startdate");		//기간조회 시작
    String  ss_enddate			= box.getString("s_enddate");		//기간조회 끝
    String  ss_startappdate		= box.getString("s_startappdate");	//수강신청일 조회 시작
    String  ss_endappdate		= box.getString("s_endappdate");	//수강신청일 조회 끝
    String  ss_paymethod		= box.getString("s_paymethod");		//결제방법
    String  ss_membergubun		= box.getString("s_membergubun");		//결제방법
	String  s_subjsearchkey     = box.getString("s_subjsearchkey");
	
    String  v_action    = box.getString("p_action");

	String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서
	String  v_dispnum	= "";
    
    int     v_pageno    = box.getInt("p_pageno")== 0 ? 1 : box.getInt("p_pageno");
    int     v_pagesize  = box.getInt("p_pagesize")== 0 ? 10 : box.getInt("p_pagesize");
    
	String  v_tid		 = "";
	String  v_subj		 = "";
	String  v_subjnm	 = "";
	String  v_year   	 = "";
	String  v_subjseq	 = "";
	String  v_seq	     = "";
	double  v_biyong     = 0;
	double  v_price      = 0;
	String  v_paymethod  = "";
	String  v_paystatus  = "";
	String  v_pgauthdate = "";
	String  v_pgauthtime = "";
	String  v_buyername  = "";
	String  v_buyeremail = "";
	String  v_buyertel	 = "";
	String  v_resultcode = "";
	String  v_cancelyn   = "";
	String  v_canceldate = "";
	String  v_canceltime = "";
	String  v_resultmsg  = "";
	int		v_rowspan    = 0;

	DataBox dbox = (DataBox)request.getAttribute("OffBillInfo");
    if (dbox != null) {
    	v_tid	     = dbox.getString("d_tid");
    	v_subj	     = dbox.getString("d_subj");
    	v_subjnm     = dbox.getString("d_subjnm");
		v_year	     = dbox.getString("d_year");
		v_subjseq	 = dbox.getString("d_subjseq");
		v_seq		 = dbox.getString("d_seq");
		v_biyong	 = dbox.getDouble("d_biyong");
		v_price   	 = dbox.getDouble("d_price");
		v_paymethod	 = dbox.getString("d_paymethod");
		v_paystatus	 = dbox.getString("d_paystatus");
		v_pgauthdate = dbox.getString("d_pgauthdate");
		v_pgauthtime = dbox.getString("d_pgauthtime");
		v_buyername	 = dbox.getString("d_buyername");
		v_buyeremail = dbox.getString("d_buyeremail");
		v_buyertel	 = dbox.getString("d_buyertel");
		v_resultcode = dbox.getString("d_resultcode");
		v_cancelyn	 = dbox.getString("d_cancelyn");
		v_canceldate = dbox.getString("d_canceldate");
		v_canceltime = dbox.getString("d_canceltime");
		v_resultmsg  = dbox.getString("d_resultmsg");

		if ("Y".equals(v_paystatus)) {
			v_paystatus = "결제완료";
		} else if ("N".equals(v_paystatus)) {
			v_paystatus = "미결제";
		} else if ("R".equals(v_paystatus)) {
			v_paystatus = "환불완료";
		}
		
		if ("BankBook".equals(v_paymethod)) {
			v_paymethod = "무통장";
		} else if ("Card".equals(v_paymethod)) {
			v_paymethod = "신용카드(ISP)";
		} else if ("DirectBank".equals(v_paymethod)) {
			v_paymethod = "실시간계좌이체";
		} else if ("TaxBill".equals(v_paymethod)) {
			v_paymethod = "계산서";
		} else if ("VBank".equals(v_paymethod)) {
			v_paymethod = "무통장(가상계좌)";
		} else if ("VCard".equals(v_paymethod)) {
			v_paymethod = "신용카드(안심클릭)";
		}

    }
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<SCRIPT LANGUAGE = "JAVASCRIPT" SRC = "/script/hhi_lib.js"></SCRIPT>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<Script language="javascript">
</Script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<table width="600" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr>
    <td align="left">
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
          <tr>
            <td align="center" bgcolor="#FFFFFF">
			 <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr>
          <td><img src="/images/admin/portal/s.1_31.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
            <br>

<form name="form1" method = "post">
  <input type="hidden" name="p_process" value="<%=v_process%>">
  <input type="hidden" name="p_mode1"   value = "name">
  <input type="hidden" name="p_mode2"   value = "">
  <input type="hidden" name="p_tid"   value = "<%= v_tid %>">

  <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
  <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
  <input type="hidden" name="p_pagesize"  value = "<%=v_pagesize%>">

  <input type="hidden" name="p_uclass"     value="<%=ss_upperclass%>">
  <input type="hidden" name="p_mclass"     value="<%=ss_middleclass%>">
  <input type="hidden" name="p_lclass"     value="<%=ss_lowerclass%>">

  <input type="hidden" name="s_upperclass"  value="<%= ss_upperclass %>">
  <input type="hidden" name="s_middleclass" value="<%= ss_middleclass %>">
  <input type="hidden" name="s_lowerclass"  value="<%=ss_lowerclass%>">
  <input type="hidden" name="s_paystatus"   value="<%=ss_paystatus%>">
  <input type="hidden" name="s_startdate"   value="<%=ss_startdate%>">
  <input type="hidden" name="s_enddate"     value="<%=ss_enddate%>">
  <input type="hidden" name="p_action"      value="go">
  <input type="hidden" name="p_searchtext"  value="<%= v_searchtext %>">
  <input type="hidden" name="p_search"      value="<%= v_search %>">
  
        <table class="box_table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="30%"><b>신청과정명</b></td>
            <td class="table_02_2" colspan="3"><%= v_subjnm %></td>
          </tr>
          <tr>
            <td class="table_title" ><b>과정차수</b></td>
            <td class="table_02_2" colspan="3"><%= StringManager.cutZero(v_subjseq) %> 기</td>
          </tr>
          <tr>
            <td class="table_title" ><b>학기</b></td>
            <td class="table_02_2" colspan="3"><%= StringManager.cutZero(v_seq) %> 학기</td>
          </tr>
          <tr>
            <td class="table_title" ><b>수강료</b></td>
            <td class="table_02_2" colspan="3"><%= new java.text.DecimalFormat("###,###,##0").format(v_biyong) %>원</td>
          </tr>
          <tr>
            <td class="table_title" ><b>총결제금액</b></td>
            <td class="table_02_2" colspan="3"><%= new java.text.DecimalFormat("###,###,##0").format(v_price) %>원</td>
          </tr>
          <tr>
            <td class="table_title" ><b>결제내역</b></td>
            <td class="table_02_2" colspan="3"><%= new java.text.DecimalFormat("###,###,##0").format(v_price) %>원</td>
          </tr>
          <tr>
            <td class="table_title" ><b>결제방법</b></td>
            <td class="table_02_2" colspan="3"><%= v_paymethod %></td>
          </tr>
          <tr>
            <td class="table_title" ><b>결제일</b></td>
            <td class="table_02_2" colspan="3"><%= !"".equals(v_pgauthdate) ? FormatDate.getFormatDate(v_pgauthdate,"yyyy/MM/dd") : "" %></td>
          </tr>
          <tr>
            <td class="table_title" ><b>결제자명</b></td>
            <td class="table_02_2" colspan="3"><%= v_buyername %></td>
          </tr>
          <tr>
            <td class="table_title" ><b>이메일</b></td>
            <td class="table_02_2" colspan="3"><%= v_buyeremail %></td>
          </tr>
          <tr>
            <td class="table_title" ><b>전화번호</b></td>
            <td class="table_02_2" colspan="3"><%= v_buyertel %></td>
          </tr>
          <tr>
            <td class="table_title" ><b>결제상태</b></td>
            <td class="table_02_2" colspan="3"><%= v_paystatus %></td>
          </tr>
<%
	if ("Y".equals(v_cancelyn))	{
		v_rowspan = 3;
	} else {
		v_rowspan = 2;
	}
%>
          <tr>
            <td class="table_title" rowspan="<%= v_rowspan %>"><b>결제상태 변경기록</b></td>
            <td class="table_title">결제상태</td>
            <td class="table_title">변경일자</td>
            <td class="table_title">변경사유</td>
          </tr>
          <tr>
            <td class="table_02_1">결제완료</td>
            <td class="table_02_1"><%= FormatDate.getFormatDate(v_pgauthdate+v_pgauthtime,"yyyy-MM-dd HH:mm ss") %></td>
            <td class="table_02_1">결제승인</td>
          </tr>
<%
	if ("Y".equals(v_cancelyn)) {
%>
          <tr>
            <td class="table_02_1">환불완료</td>
            <td class="table_02_1"><%= FormatDate.getFormatDate(v_canceldate+v_canceltime,"yyyy-MM-dd HH:mm ss") %></td>
            <td class="table_02_1">결제취소</td>
          </tr>
<%		
	}
%>
        </table>

        <!----------------- 닫기 버튼 시작 ----------------->
        <table cellspacing="0" cellpadding="0" >
          <tr>
            <td align="right" style="padding-top=10"><a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 닫기 버튼 끝 ----------------->
        <br>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
