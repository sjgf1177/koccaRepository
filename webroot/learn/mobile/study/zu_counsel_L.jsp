<%@ page language = "java" contentType = "text/html; charset=euc-kr" pageEncoding = "euc-kr" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<% 
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	//나의상담내역목록
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
		var obj = document.listForm;
		
		obj.method = "post";
		obj.p_process.value = "counselList";
		obj.p_pageno.value = v_pageno;
		obj.action = "/servlet/controller.mobile.study.MyClassServlet";
		obj.submit();
	}
	
	//상세페이지 이동
	function goViewPage(seq, userid, types, type)
	{
		
		var obj = document.viewForm;
		obj.p_seq.value = seq;
		obj.p_userid.value = userid;
		obj.p_types.value = types;
		obj.p_type.value = type;
		obj.p_process.value = "counselViewPage";

		obj.action = "/servlet/controller.mobile.study.MyClassServlet";
		obj.submit();
	}

	function viewQna(seq,subj,year,subjseq) {
		
		var obj = document.viewForm;
		
		obj.p_seq.value     = seq;
		obj.p_subj.value    = subj;
		obj.p_year.value    = year;
		obj.p_subjseq.value = subjseq;
		obj.action = "/servlet/controller.mobile.study.MyClassServlet";
		obj.p_process.value = "MyQnaCounselQnaViewPage";
		obj.submit();
	}
	
	//글쓰기 페이지 이동
	function goWritePage()
	{
		
		var obj = document.viewForm;
		
		obj.p_process.value = "counselWritePage";
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
	
	<form name="viewForm" method="post" action="/servlet/controller.mobile.study.MyClassServlet">
		<input type="hidden" name="p_process" value="counselView" />
		<input type="hidden" name="p_menuid" value="<%=v_menuid%>" />
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>" />
		<input type="hidden" name="p_userid" value="" />
		<input type="hidden" name="p_seq" value="" />
		<input type="hidden" name="p_types" value="" />
		<input type="hidden" name="p_type" value="" />
		
		<input type="hidden" name="p_subj" value="" />
		<input type="hidden" name="p_year" value="" />
		<input type="hidden" name="p_subjseq" value="" />
	</form>
	<form name="listForm" method="post" action="/servlet/controller.mobile.study.MyClassServlet">
		<input type="hidden" name="p_process" value="counselList" />
		<input type="hidden" name="p_menuid" value="<%=v_menuid%>" />
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>" />
	</form>
	
	<div class="h_area_corse_list">
		<h3 class="t">목록</h3>
		<button id="sub_searchSubmit" type="submit" onclick="return goWritePage();">
			<span>글쓰기</span>
		</button>
	</div>
<!--  
	<div class="field_area_06">
		<ul id="field_m" class="field_m">
			<li class="field_li field">
			게시판분류<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			제목<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			작성일자<span class="bar_line">|</span>
			</li>			
			<li class="field_li field">
			답변여부
			</li>
		</ul>
	</div>
-->	
	<div class="category_two">
	</div>
	

	<!--과정리스트-->
	<div class="division_area">
<%
	String v_type = "";
	String v_typenm = "";
	String v_status = "";
	String v_status_view = "";
	String v_status_class  = "";
	if(list.size() == 0)
	{
%>
		<div class="list_area_c">
			상담내역이 없습니다.
		</div>
<%	
	}
	else
	{
		for(int i = 0, j = list.size(); i < j; i++)
		{
			dbox = (DataBox) list.get(i);
			
			v_type = dbox.getString("d_type");
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
			
			v_status	= dbox.getString("d_status");
            
			if (v_status.equals("1")) {
	            v_status_view	= "열람전";
	            v_status_class	= "pre";
	        } else if (v_status.equals("2")) {
	            v_status_view	= "답변준비중";
	            v_status_class	= "ing";
	        } else if (v_status.equals("3")) {
	            v_status_view	= "답변완료";
	            v_status_class	= "finish";
	        } else {
	            v_status_view	= "열람전";
	            v_status_class	= "pre";
	        }
			
%>
		<div class="">
			<ul class="li1">
			<li class="">
<% 
				if ("PQ".equals(v_type) || "BU".equals(v_type) || "CU".equals(v_type) || "OO".equals(v_type) || "MM".equals(v_type)) 
				{
%>				
				<a class="ct" onclick="goViewPage('<%=dbox.getString("d_no")%>', '<%=dbox.getString("d_userid")%>', '<%=dbox.getString("d_types")%>', '<%=dbox.getString("d_type")%>');" href="#none">
<%	
				}
				else
				{
%>				
				<a class="ct" onclick="viewQna('<%=dbox.getString("d_no")%>', '<%=dbox.getString("d_subj")%>', '<%=dbox.getString("d_year")%>', '<%=dbox.getString("d_subjseq")%>');" href="#none">

<% 
				}
%>				
				
				<span class="if"><%=v_typenm%> | <%=FormatDate.getFormatDate(dbox.getString("d_sdate"), "yyyy.MM.dd")%> </span>
				
				<br>
				<strong><%=StringManager.cutText(dbox.getString("d_title"), 50, true)%></strong>
<% 
				if ("PQ".equals(v_type) || "BU".equals(v_type) || "CU".equals(v_type) || "OO".equals(v_type) || "MM".equals(v_type)) 
				{
%>				
					<span class="r1"><button id="add_Submit" onclick="goViewPage('<%=dbox.getString("d_no")%>', '<%=dbox.getString("d_userid")%>', '<%=dbox.getString("d_types")%>', '<%=dbox.getString("d_type")%>');"><%=v_status_view%></button></span>
<%	
				}
				else
				{
%>				
				<span class="r1"><button id="add_Submit" onclick="viewQna('<%=dbox.getString("d_no")%>', '<%=dbox.getString("d_subj")%>', '<%=dbox.getString("d_year")%>', '<%=dbox.getString("d_subjseq")%>');"><%=v_status_view%></button></span>
<% 
				}
%>				
				</a>
				</li>
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