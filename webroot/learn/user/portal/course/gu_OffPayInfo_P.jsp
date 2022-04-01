<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 오프라인 결제정보
//  2. 프로그램명: za_PayInfo_P.jsp
//  3. 개      요: 오프라인 결제정보
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.24
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");

    String v_tid          = "";
    String v_goodname     = "";
    String v_buyername    = "";
    String v_pgauthdate   = "";
    double v_price        = 0;
    double v_biyong       = 0;
    double v_total_biyong = 0;
    String v_subjnm       = "";
    
    ArrayList list = (ArrayList)request.getAttribute("offpayInfo");

    if (list.size() > 0) {
        DataBox dbox   = (DataBox)list.get(0);
        v_tid          = dbox.getString("d_tid");
        v_goodname     = dbox.getString("d_subjnm");
        v_buyername    = dbox.getString("d_buyername");
        v_pgauthdate   = dbox.getString("d_pgauthdate");
        v_pgauthdate   = FormatDate.getFormatDate(v_pgauthdate,"yyyy.MM.dd");
        v_total_biyong = dbox.getDouble("d_biyong");
        v_price        = dbox.getDouble("d_price");
    }
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>수강료 결제내역</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">
<!--
-->
</script>
</head>

<body id="popup"><!-- popup size : 550* -->
	<div id="pop_header">
		<h1><img src="/images/portal/myclass/pop_h1_tit4.gif" alt="수강료 결제내역" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap" class="message_top">
			<h2 class="pop_tit"><img src="/images/portal/common/stit_detailinfo.gif" alt="결제 세부정보" /></h2>
			<table class="view">
			<colgroup><col width="25%" /><col width="75%" /></colgroup>
			<tr>
				<th>결제명</th>
				<td class="end"><%= v_goodname %></td>
			</tr>
			<tr>
				<th>결제자</th>
				<td class="end"><%= v_buyername %></td>
			</tr>
			<tr>
				<th>결제일</th>
				<td class="end"><%= v_pgauthdate %></td>
			</tr>
			<tr>
				<th>실결제액</th>
				<td class="end"><%= new java.text.DecimalFormat("###,###,##0").format(v_price) %>원</td>
			</tr>
			<!--tr>
				<th>상세내역</th>
				<td class="end">수강료 <%//= new java.text.DecimalFormat("###,###,##0").format(v_total_biyong) %>원 - 할인 <%//= new java.text.DecimalFormat("###,###,##0").format(v_total_biyong - v_price) %>원(<%//= (v_total_biyong - v_price) / v_total_biyong * 100 %>%) = <%//= new java.text.DecimalFormat("###,###,##0").format(v_price) %>원</td>
			</tr-->
			</table>
 
			<h2 class="pop_tit mg_t35"><img src="/images/portal/common/stit_applystep.gif" alt="신청과정" /></h2>
			<table class="list">
			<colgroup><col width="10%" /><col width="42%" /><col width="24%" /><col width="24%" /></colgroup>
			<thead>
			<tr>
				<th>No.</th>
				<th>신청과정명</th>
				<th>수강료</th>
				<th class="end">실결제액</th>
			</tr>
			</thead>
			<tbody>
<%
            for(int i = 0; i < list.size(); i++) {

            	DataBox dbox  = (DataBox)list.get(i);
                v_subjnm   = dbox.getString("d_subjnm");
                v_biyong   = dbox.getDouble("d_biyong");
%>
			<tr>
				<td><%= i+ 1 %></td>
				<td><%= v_subjnm %></td>
				<td><%= new java.text.DecimalFormat("###,###,##0").format(v_biyong) %>원</td>
				<td class="end fw_b"><%= new java.text.DecimalFormat("###,###,##0").format(v_biyong * (v_price/v_total_biyong)) %>원</td>
			</tr>
<%
			}
%>
			</tbody>
			</table>
		</div>
	</div>
	<div id="pop_footer">
		<p class="f_btn"><a href="javascript:self.close();" class="btn_gr"><span>닫기</span></a></p>
	</div>
</body>
</html>
