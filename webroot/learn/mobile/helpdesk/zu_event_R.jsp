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
	
	String v_event_date =  FormatDate.getFormatDate(dbox.getString("d_strdate"), "yyyy.MM.dd") + "~" + FormatDate.getFormatDate(dbox.getString("d_enddate"), "yyyy.MM.dd");
	
	String v_winneryn  = dbox.getString("d_winneryn");
	String v_status = "";
	if(v_winneryn.equals("C")){
    	v_status = "완료";
    } else if(v_winneryn.equals("Y")){
        v_status = "완료";
    } else if(v_winneryn.equals("N")){
    	v_status = "진행";
    } else if(v_winneryn.equals("A")){
    	v_status = "진행";
    } 
    else if(v_winneryn.equals("B")){
    	v_status = "예정";
    } 
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
		
		obj.p_process.value = "eventList";
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
		<input type="hidden" name="p_process" value="eventList"/>
		<input type="hidden" name="p_menuid" value="<%=v_menuid%>"/>
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>"/>
		
	</form>
	
	<div class="h_area_corse_list">
		<h3 class="t">상세</h3>
	</div>
	
	<button id="sub_searchSubmit" onclick="return goListPage();">
	<span>목록</span>
	</button>
	
	<div class="contact">
		<table cellpadding="0" cellspacing="0">
		
		<tr>
			<td class="td_center01"><%=dbox.getString("d_title")%>
			<span class="division_date"><span class="content_bar">|</span>작성자 : <%=dbox.getString("d_name")%></span></td>
		</tr>	
		<tr>
			<td class="td_center02 division_date">진행여부 : <%=v_status%><span class="content_bar">|</span>이벤트기간 : <%=v_event_date%><span class="content_bar">|</span>조회 <%=NumberManager.getlongFormat(dbox.getInt("d_cnt"))%></td>
		</tr>		
		<tr>
			<td  class="td_center03"  style="text-align:center;">
			<%=dbox.getString("d_content")%>
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