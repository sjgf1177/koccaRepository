<%@ page language = "java" contentType = "text/html; charset=euc-kr" pageEncoding = "euc-kr" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<% 
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	//수강완료 과정 목록
	DataBox dbox = (DataBox) request.getAttribute("_VIEW_");
	if(dbox == null) dbox = new DataBox("request");

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
	function goListPage()
	{
		var obj = document.listForm;
		
		obj.method = "post";
		obj.p_process.value = "offLineStudyendList";
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
	
	
	<form name="listForm" method="post" action="/servlet/controller.mobile.course.CourseServlet">
		<input type="hidden" name="p_process" value="offLineStudyendList" />
		<input type="hidden" name="p_menuid" value="<%=v_menuid%>" />
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>" />
	<div class="mp_view_top">
		<button id="sub_searchSubmit" onclick="return goListPage();">
			<span>목록</span>
		</button>
	</div>
	</form>			
		
	<div class="mp-view">
	
		<div class="mp-view">
			<table cellpadding="0" cellspacing="0">
			<colgroup>
				<col width="50%" />		
				<col width="50%" />
			</colgroup>
			<tr>
				<th class="th_left">항목</th>
				<th class="th_left01">점수</th>		
			</tr>	
			<tr>
				<th class="th_left">진도율(점수)</th>
				<td class="td_ct02"><%=dbox.getDouble("d_tstep")%>(<%=dbox.getDouble("d_avtstep")%>)</td>		
			</tr>
			<tr>
				<th class="th_left">중간평가</th>
				<td class="td_ct02"><%=dbox.getDouble("d_mtest")%>(<%=dbox.getDouble("d_avmtest")%>)</td>		
			</tr>
			<tr>
				<th class="th_left">최종평가</th>
				<td class="td_ct02"><%=dbox.getDouble("d_ftest")%>(<%=dbox.getDouble("d_avftest")%>)</td>		
			</tr>
			<tr>
				<th class="th_left">형성평가</th>
				<td class="td_ct02"><%=dbox.getDouble("d_htest")%>(<%=dbox.getDouble("d_avhtest")%>)</td>		
			</tr>
			<tr>
				<th class="th_left">리포트</th>
				<td class="td_ct02"><%=dbox.getDouble("d_report")%>(<%=dbox.getDouble("d_avreport")%>)</td>		
			</tr>
			<tr>
				<th class="th_left">참여도</th>
				<td class="td_ct02"><%=dbox.getDouble("d_etc1")%>(<%=dbox.getDouble("d_avetc1")%>)</td>	
			</tr>
			<tr>
				<th class="th_left">기타</th>
				<td class="td_ct02"><%=dbox.getDouble("d_etc2")%>(<%=dbox.getDouble("d_avetc2")%>)</td>	
			</tr>
			<tr>
				<th class="th_left">총점</th>
				<td class="td_ct02"><%=dbox.getDouble("d_score")%></td>	
			</tr>
			</table>
		</div>
	</div>
	
	<!--Footer-->
	<jsp:include page="/learn/mobile/include/footer.jsp" flush="true" />
	<!--Footer-->

</div>

</body>
</html>