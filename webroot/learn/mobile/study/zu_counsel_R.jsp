<%@ page language = "java" contentType = "text/html; charset=euc-kr" pageEncoding = "euc-kr" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<% 
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	//나의상담내역목록
	DataBox dbox = (DataBox) request.getAttribute("_VIEW_");
	if(dbox == null) dbox = new DataBox("");
	
	DataBox rdbox = (DataBox) request.getAttribute("_REVIEW_");
	ArrayList list = (ArrayList)request.getAttribute("selectMyQnaCounselQnaListA");
	
	String v_type = dbox.getString("d_type");
	String v_typenm = "";
	if (v_type.equals("PQ"))
    	v_typenm = "홈페이지";
    else if (v_type.equals("BU") || v_type.equals("CU") || v_type.equals("OO") || v_type.equals("MM"))
    	v_typenm = "1vs1상담";
    else if (v_type.equals("OFF"))
    	v_typenm = "유선상담";
    else if (v_type.equals("SUBJ"))
    	v_typenm = "과정질문방";
    else
    	v_typenm = "";
	
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
		
		obj.p_process.value = "counselList";
		obj.action = "/servlet/controller.mobile.study.MyClassServlet";
		obj.submit();
	}
<% 
	if(!(rdbox != null || list != null))
	{
%>
	//삭제
	function goRemove()
	{
		var obj = document.viewForm;
		if(!confirm("삭제하시겠습니까?"))
			return false;
		
		obj.p_process.value = "counselRemove";
		obj.action = "/servlet/controller.mobile.study.MyClassServlet";
		obj.submit();
	}
<%
	}
%>
</script>
</head>
<body class="other_sub">
<div id="sub_header">	
	<%@ include file="/learn/mobile/include/header.jsp" %>
</div>
<div id="kocca_subContent">	
	<div class="h_area_corse_list">
		<h3 class="t">상세</h3>
	</div>
	<form name="listForm" method="post" action="/servlet/controller.mobile.study.MyClassServlet">
		<input type="hidden" name="p_process" value="counselList"/>
		<input type="hidden" name="p_menuid" value="<%=v_menuid%>"/>
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>"/>	
	</form>
<% 
	if(!(rdbox != null || list != null))
	{
%>	
	<form name="viewForm" method="post" action="/servlet/controller.mobile.study.MyClassServlet">
		<input type="hidden" name="p_process" value="counselList"/>
		<input type="hidden" name="p_menuid" value="<%=v_menuid%>"/>
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>"/>
		
		<input type="hidden" name=p_types value="<%=box.getString("p_types")%>"/>
		<input type="hidden" name=p_type value="<%=box.getString("p_type")%>"/>
		<input type="hidden" name="p_seq" value="<%=box.getString("p_seq")%>"/>
		
	</form>
	
	<button id="cancle_Submit" onclick="return goRemove();">
	<span>삭제</span>
	</button>
<% 
	}
%>	
	<button id="sub_searchSubmit" onclick="return goListPage();">
	<span>목록</span>
	</button>

	<div class="contact">
	<table cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="25%" />
		<col width="%" />
	</colgroup>
	<tr>
		<td class="td_center01"><%=dbox.getString("d_title")%></td>
	</tr>	
	<tr>
		<td class="td_center02 division_date">분류 : <%=v_typenm%><span class="content_bar">|</span>작성자 : <%=dbox.getString("d_name") %><span class="content_bar">|</span>작성일자:<%=FormatDate.getFormatDate(dbox.getString("d_indate"), "yy/MM/dd")%><span class="content_bar">|</span>조회 <%=NumberManager.getIntFormat(dbox.getInt("d_cnt"))%></td>
	</tr>		
	<tr>
		<td class="td_center03"><%=dbox.getString("d_contents")%></td>
	</tr>		
	</table>
	</div>
<% 
	if(rdbox != null)
	{
%>
	<div class="h_area_corse_list">
		<h3 class="r icon_rep"><span class=""></span>문의답변</h3>
	</div>
	
	<div class="contact_reply">
	<table cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="25%" />
		<col width="%" />
	</colgroup>
	<tr>
		<td class="td_center02"><img src="/images/mobile/icon_re.gif"  />답변드립니다.<span class="division_date"><%= FormatDate.getFormatDate(rdbox.getString("d_indate"), "yy/MM/dd") %></span></td>
	</tr>		
	<tr>
		<td colspan="2" class="td_center03">
		<%=rdbox.getString("d_contents")%>
		</td>
	</tr>		
	</table>
	</div>
<% 
	}
	if(list != null)
	{
%>
	<div class="h_area_corse_list">
		<h3 class="r icon_rep"><span class=""></span>문의답변</h3>
	</div>
	
	<div class="contact_reply">
	<table cellpadding="0" cellspacing="0">
	<colgroup>
		<col width="25%" />
		<col width="%" />
	</colgroup>
<% 
		for(int i = 0, j = list.size(); i < j; i++)
		{
			rdbox = (DataBox) list.get(i);
%>
	<tr>
		<td class="td_center02"><img src="/images/mobile/icon_re.gif"  />답변드립니다.<span class="division_date"><%= FormatDate.getFormatDate(rdbox.getString("d_indate"), "yy/MM/dd") %></span></td>
	</tr>		
	<tr>
		<td colspan="2" class="td_center03">
		<%=rdbox.getString("d_contents")%>
		</td>
	</tr>	
<% 
		}
%>	
	</table>
	</div>
<%		
	}
%>	

	<!--Footer-->
	<jsp:include page="/learn/mobile/include/footer.jsp" flush="true" />
	<!--Footer-->

</div>
</body>
</html>