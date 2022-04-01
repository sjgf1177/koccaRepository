<%
//**********************************************************
//  1. 제      목: 수강료결제 관리 엑셀
//  2. 프로그램명 : za_OffBillAdmin_E.jsp
//  3. 개      요: 행정서비스 - 수강료결제 관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.18
//  7. 수      정:
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.library.PageUtil" %>
<%@ page import = "com.credu.propose.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%	
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_OffBillAdmin_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");

    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String  v_searchtext  = box.getString("p_searchtext");
    String  v_search      = box.getString("p_search");

	String  v_process  = "selectList";

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

	int     v_totalpage =  0;
    int     v_rowcount  =  0;
    int     v_rowspan   =  0;
    int     v_total     =  0;
    int     i           =  0;
	int		v_total_rowcount = 0;

	if(v_pageno == 0)
	{
		v_pageno = 1;
	}

    String  v_ongo     = "whenSelection('go')";
	String  v_finalString = "";
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" height="30"><font size="3"><b>오프라인 수강료 결제 조회</b></font></td>
    </tr>
  <tr>
    <td align="center" valign="top">
      <!----------------- 조회 시작 ----------------->
      <table class="table_out" cellspacing="1" cellpadding="5" border="1">
         <tr>		
			<td>No</td>
			<td>과정명</td>
			<td>아이디</td>
			<td>성명</td>
			<td>회원구분</td>
			<td>결제상태</td>
			<td>결제금액</td>
			<td>결제방법</td>
			<td>결제자</td>
			<td>결제일</td>
          </tr>
<%  
	String  v_grcode       = "";
	String  v_tid          = "";
	String  v_userid       = "";
	String  v_usernm       = "";
	String  v_membergubun = "";
	String  v_codenm       = "";
	String  v_goodname     = "";
	String  v_inputdate    = "";
	double  v_price        = 0;
	String  v_resultcode   = "";
	String  v_paymethod    = "";
	String  v_paystatus    = "";
	String  v_buyername    = "";
	String  v_pgauthdate   = "";
	String  v_cancelyn     = "";
	double	v_total_price  = 0;
	String  v_cancel_btn   = "";

	if (v_action.equals("go")) {
        	
        ArrayList  list = (ArrayList)request.getAttribute("selectList");
        
        int v_today = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));  //오늘 날짜 일시 초기화

        for (i=0; i<list.size(); i++) {
			DataBox dbox = (DataBox)list.get(i);

            v_finalString	= "";

            v_grcode      = dbox.getString("d_grcode");
            v_tid         = dbox.getString("d_tid");
            v_userid      = dbox.getString("d_userid");
            v_usernm      = dbox.getString("d_usernm");
            v_membergubun = dbox.getString("d_membergubun");
            v_codenm      = dbox.getString("d_codenm");
            v_goodname    = dbox.getString("d_goodname");
            v_inputdate   = dbox.getString("d_inputdate");
            v_price       = dbox.getDouble("d_price");
            v_resultcode  = dbox.getString("d_resultcode");
            v_paymethod   = dbox.getString("d_paymethod");
            v_paystatus   = dbox.getString("d_paystatus");
            v_buyername   = dbox.getString("d_buyername");
            v_pgauthdate  = dbox.getString("d_pgauthdate");
            v_cancelyn    = dbox.getString("d_cancelyn");

			v_dispnum		= dbox.getString("d_dispnum");
			v_totalpage     = dbox.getInt("d_totalpage");
			v_rowcount      = dbox.getInt("d_rowcount");
			v_total_rowcount= dbox.getInt("d_totalrowcount");

			if ("Y".equals(v_paystatus) && (!"TaxBill".equals(v_paymethod) && (!"BankBook".equals(v_paymethod)))) {
				v_cancel_btn = "<a href=\"javascript:fnPayInfo('" + v_tid + "')\">[결제취소]</a>";
			} else {
				v_cancel_btn = "";
			}
			
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

			v_total_price += v_price;
%>
            <tr>
				<td><%= i + 1 %></td>
                <td><%= v_goodname %></td>
                <td><%= v_userid %></td>
                <td><%= v_usernm %></td>
                <td><%= v_codenm %></td>
                <td><%= v_paystatus %></td>
                <td><%= v_price %></td>
                <td><%= v_paymethod %></td>
                <td><%= v_buyername %></td>
                <td><%= FormatDate.getFormatDate(v_pgauthdate,"yyyy/MM/dd") %></td>
            </tr>
<%          //}
        }
		if (i == 0)
			{ %>
			<tr>
				<td align="center" bgcolor="#FFFFFF" height="100" colspan="11">등록된 내용이 없습니다.</td>
			  </tr>
			<%}
    }

%>
        </table>
      </td>
  </tr>
</table>
</body>
</html>