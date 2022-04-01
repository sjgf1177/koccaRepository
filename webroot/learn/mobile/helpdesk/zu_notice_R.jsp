<%@ page language = "java" contentType = "text/html; charset=euc-kr" pageEncoding = "euc-kr" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<% 
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	//공지사항 목록
	DataBox dbox = (DataBox) request.getAttribute("_VIEW_");
	if(dbox == null) dbox = new DataBox("");
	
	/** 페이징 */
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
	//목록페이지 이동
	function goListPage()
	{
		var obj = document.listForm;
		
		obj.p_process.value = "noticeList";
		obj.action = "/servlet/controller.mobile.helpdesk.HelpDeskServlet";
		obj.submit();
	}
</script>
</head>
<body class="other_sub">
<div id="sub_header">	
	<!--header-->
	<%@ include file="/learn/mobile/include/header.jsp" %>
	<!--header-->
</div>
<div id="kocca_subContent">	
	<form name="listForm" method="post" action="/servlet/controller.mobile.helpdesk.HelpDeskServlet">
		<input type="hidden" name="p_process" value="noticeList"/>
		<input type="hidden" name="p_menuid" value="<%=v_menuid%>"/>
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>"/>
		
	</form>
			
	<div class="h_area_corse_list">
		<h3 class="t">상세</h3>
	</div>
	
	<button id="sub_searchSubmit" onclick="return goListPage();">
	<span>목록</span>
	</button>

	<div class="notice-view">
	<table cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="25%" />
		<col width="%" />
	</colgroup>
	<tr>
		<td class="td_center01"><%=dbox.getString("d_adtitle")%></td>
	</tr>	
	<tr>
		<td class="td_center02 division_date">작성일자:<%=FormatDate.getFormatDate(dbox.getString("d_addate"), "yy/MM/dd")%><span>|</span>조회 <%=NumberManager.getlongFormat(dbox.getInt("d_cnt"))%></td>
	</tr>		
	<tr>
		<td class="td_center03">
			<table cellpadding="0" cellspacing="0">
				<tr>
					<td class="td_left">
						<%=dbox.getString("d_adcontent")%>
	              	</td>
              	</tr>
            </table>
		</td>
	</tr>		
	</table>
	</div>

	<!--footer-->
	<jsp:include page="/learn/mobile/include/footer.jsp" flush="true" />
	<!--footer-->

</div>


</html>
</body>
</html>