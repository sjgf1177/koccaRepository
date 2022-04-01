<%@ page language = "java" contentType = "text/html; charset=euc-kr" pageEncoding = "euc-kr" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<% 
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	//수강료결재 조회 목록
	ArrayList list = (ArrayList) request.getAttribute("_LIST_");
	if(list == null) list = new ArrayList();
	
	DataBox dbox = null;
	
	/** 페이징 */
	int v_totalpage = 0;
	int v_rowcount = 1;
	
	int v_pageno = box.getInt("p_pageno");
%>
<%@ include file="/learn/mobile/include/common.jsp" %>
<!doctype html>
<html lang="ko">
<head>
<meta charset="euc-kr">
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no, target-densitydpi=medium-dpi" />
<meta name="format-detection" content="telephone=no"/>
<title><%=v_title.toString()%></title>
<link rel="stylesheet" type="text/css" href="/css/m_common.css" />
<script type="text/javascript" language="javascript" src="/js/common.js"></script>
<script type="text/javascript" language="javascript">
	//페이지 이동
	function goPage(v_pageno)
	{
		var obj = document.viewForm;
		
		obj.method = "post";
		obj.p_pageno.value = v_pageno;
		obj.p_process.value = "offlinePayList";
		obj.action = "/servlet/controller.mobile.study.MyClassServlet";
		obj.submit();
	}
</script>
</head>
<body class="other_sub">
<div id="sub_header">	
	<%@ include file="/learn/mobile/include/header.jsp" %>
</div>
<div id="kocca_subContent">	

	<!--온라인/오프라인 탭메뉴-->
	<form name="viewForm" method="post" action="/servlet/controller.mobile.course.CourseServlet">
		<input type="hidden" name="p_process" value="offlinePayList" />
		<input type="hidden" name="p_menuid" value="<%=v_menuid%>" />
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>" />
		<input type="hidden" name="p_subj" value="" />
		<input type="hidden" name="p_year" value="" />
		<input type="hidden" name="p_subjseq" value="" />	
	</form>
	<nav class="onoff_tab_area">
		<div class="onoff_tab">
			<ul>
				<li class="tabmenu_l" style="width:49%;">
					<a title="온라인과정" href="/servlet/controller.mobile.study.MyClassServlet?p_process=onlinePayList&p_menuid=<%=v_menuid%>"><span>온라인과정</span></a>
				</li>
				<li class="tabmenu_on" style="width:50.8%;">
					<a title="오프라인과정" href=""><span>오프라인과정</span></a>
				</li>
			</ul>
		</div>
	</nav>
<!-- 
	<div class="field_area_04">
		<ul id="field_m" class="field_m">
			<li class="field_li field">
			결제명<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			수강신청일<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			수강료<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			실결제액<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			결제상태<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			결제방법<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			결제일자<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			취소완료일
			</li>
		</ul>
	</div>
 -->
 	<div class="category_two">
	</div>
	<!--과정리스트-->
	<div class="list_area">
<% 
	int v_biyong = 0;
	int v_price = 0;
	String v_cancelyn = "";
	String v_paymethod = "";
	String v_canceldate = "";
	if(list.size() == 0)
	{
%>
		<div class="list_area_c">
			결재내역이 없습니다.
		</div>
<%		
	}
	else
	{
		for(int i = 0, j = list.size(); i < j; i++)
		{
			dbox = (DataBox) list.get(i);
			
			v_biyong = dbox.getInt("d_biyong");
			v_price = dbox.getInt("d_price");
			v_cancelyn = dbox.getString("d_cancelyn");
			v_canceldate = dbox.getString("d_canceldate");
			
			v_paymethod	= dbox.getString("d_paymethod");
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
			} else if ("FreePay".equals(v_paymethod)) {
				v_paymethod = "무료결제";
			}
			
			v_canceldate = dbox.getString("d_canceldate");
%>		
		<!--1행-->
		<div class="list_area_l">
		<div><%=dbox.getString("d_goodname")%></div>
			<ul class="field_m">
				<li class="field_li"><span class="list_date01"><%=FormatDate.getFormatDate(dbox.getString("d_inputdate"), "yyyy.MM.dd")%> <%=NumberManager.getIntFormat(v_biyong)%>원</span><span class="color_blue">(<%=NumberManager.getIntFormat(v_price)%>원)</span></li>
				<li class="field_li"><span id="payment_Submit"><%= "Y".equals(v_cancelyn) ? "환불완료" : "결제완료" %></span><span id="card_Submit"><%=v_paymethod%></span></li>
				<li class="field_li"><span class="color_red"><%=FormatDate.getFormatDate(dbox.getString("d_inputdate"), "yyyy.MM.dd")%></span> <span class="color_green"><%=FormatDate.getFormatDate(v_canceldate, "yyyy.MM.dd")%></span></li>
			</ul>
		</div>
<%
			v_totalpage = dbox.getInt("d_totalpage");
			v_rowcount = dbox.getInt("d_rowcount"); 

		}
	}
%>
		<!--페이지-->
		<div class="pg">
			<%= PageUtil.printPageMobileList(v_totalpage, v_pageno, v_rowcount, "") %>
		</div>
	
	</div>
	
	<!--Footer-->
	<jsp:include page="/learn/mobile/include/footer.jsp" flush="true" />
	<!--Footer-->
	
</div>
</body>
</html>