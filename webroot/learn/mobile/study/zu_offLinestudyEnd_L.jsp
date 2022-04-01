<%@ page language = "java" contentType = "text/html; charset=euc-kr" pageEncoding = "euc-kr" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<% 
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	//수강완료 과정 목록
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
		obj.p_process.value = "offLineStudyendList";
		obj.action = "/servlet/controller.mobile.study.MyClassServlet";
		obj.submit();
	}

	//과정 상세페이지 이동
	function goCouseDetail(v_subj, v_year, v_subjseq)
	{
		var obj = document.viewForm;

		obj.p_subj.value = v_subj;
		obj.p_year.value = v_year;
		obj.p_subjseq.value = v_subjseq;
		obj.p_menuid.value = "020001000000";
		obj.p_process.value = "courseView";
		obj.action = "/servlet/controller.mobile.course.CourseServlet";
		obj.submit();
	}

	// 점수보기
	function goScorePage(subj, year, subjseq)
	{
		var obj = document.viewForm;
	
		obj.p_subj.value = subj;
	    obj.p_year.value = year;
	    obj.p_subjseq.value = subjseq;
	    obj.p_process.value = "offLineScoreViewPage";
	    obj.action = "/servlet/controller.mobile.study.MyClassServlet";
	    obj.submit();
		
	}

	//학기별 성적보기
	function goTermScorePage(subj, year, subjseq)
	{
		var obj = document.viewForm;
		
		obj.p_subj.value = subj;
	    obj.p_year.value = year;
	    obj.p_subjseq.value = subjseq;
	    obj.p_process.value = "offTermLineScoreListPage";
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

	<form name="viewForm" method="post" action="/servlet/controller.mobile.course.CourseServlet">
		<input type="hidden" name="p_process" value="offLineStudyendList" />
		<input type="hidden" name="p_cousegubun" value="OFF" />
		<input type="hidden" name="p_menuid" value="<%=v_menuid%>" />
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>" />
		<input type="hidden" name="p_subj" value="" />
		<input type="hidden" name="p_year" value="" />
		<input type="hidden" name="p_subjseq" value="" />	
	</form>
	
	<table class="onofftable_tab" cellpadding="0" cellspacing="0">
		<colgroup><col width="50%"><col width="*"></colgroup>
		<tr>
			<td class="tabmenu_on"><a title="온라인과정" href="/servlet/controller.mobile.study.MyClassServlet?p_process=onLineStudyendList&p_menuid=<%=v_menuid%>"><span>온라인과정</span></a></td>
			<td class="tabmenu_l"><span>오프라인과정</span></td>
		</tr>
	</table>
	
	<%-- <nav class="onoff_tab_area">
		<div class="onoff_tab">
			<ul>
				<li class="tabmenu_l" style="width:49%;">
					<a title="온라인과정" href="/servlet/controller.mobile.study.MyClassServlet?p_process=onLineStudyendList&p_menuid=<%=v_menuid%>"><span>온라인과정</span></a>
				</li>
				<li class="tabmenu_on" style="width:50.8%;">
					<a title="오프라인과정" href=""><span>오프라인과정</span></a>
				</li>
			</ul>
		</div>
	</nav> --%>
	
	<!-- 
	<div id="" class="field_area_06">
		<ul id="field_m" class="field_m">
			<li class="field_li field">
			분류<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			과정명<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			교육기간<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			성적보기<span class="bar_line">|</span>
			</li>
			<li class="field_li field">
			상태
			</li>			
		</ul>
	</div>
 	-->
	<div class="category_two">
	</div>

	<!--과정리스트-->
	<div class="list_area">
<% 
	String v_isoutsourcing = "";
	String v_eduurl = "";
	String v_subj = "";
	String v_year = "";
	String v_subjseq = "";
	String v_subjnm = "";
	
	String v_isterm = "";
	String v_stustatusnm = "";
	double v_score = 0;
	String v_isgraduated = "";
	String v_isgraduated_txt = "";
	
	if(list.size() == 0)
	{
%>
		<div class="list_area_c">
			나의 교육이력 과정이 없습니다.
		</div>
<%		
	}
	else
	{
		for(int i = 0, j = list.size(); i < j; i++)
		{
			dbox = (DataBox) list.get(i);
			
			
			
			v_subj          =  dbox.getString("d_subj");
			v_year          =  dbox.getString("d_year");
			v_subjseq          =  dbox.getString("d_subjseq");
			v_subjnm        =  dbox.getString("d_subjnm");
			
			v_stustatusnm 	= dbox.getString("d_stustatusnm");
			v_score			= dbox.getDouble("d_score");
			v_isterm 	    = dbox.getString("d_isterm");
			
			v_isgraduated 	= dbox.getString("d_isgraduated");
			if ("Y".equals(v_isterm)) 
			{
				v_isgraduated_txt = v_stustatusnm;
			} 
			else 
			{
				if(v_isgraduated.equals("Y"))       v_isgraduated_txt = "수료";
				else if(v_isgraduated.equals("N"))  v_isgraduated_txt = "미수료";
			}
            
%>			
		<div class="list_area_l">
		<div>[<%=dbox.getString("d_upperclassnm")%>]<%=StringManager.cutText(v_subjnm, 50, true)%></div>
			<p><span class="list_date color_red"><%=FormatDate.getFormatDate(dbox.getString("d_edustart"), "yyyy.MM.dd") + "~" + FormatDate.getFormatDate(dbox.getString("d_eduend"), "yyyy.MM.dd")%> </span>
			<span class="list_btn">
<% 
			if("N".equals(dbox.getString("d_isterm")))
			{
%>
			<button id="test_Submit" onclick="javascript:goScorePage('<%=v_subj%>', '<%=v_year%>', '<%=v_subjseq%>');">성적보기</button>
<%				
			}
			else
			{
%>			
			<button id="test_Submit" onclick="javascript:goTermScorePage('<%=v_subj%>', '<%=v_year%>', '<%=v_subjseq%>');">학기별</button>
<% 
			}
%>			
			<%=v_isgraduated_txt%></span></p>
		</div>
<% 
			v_totalpage = dbox.getInt("d_totalpage");
			v_rowcount = dbox.getInt("d_rowcount"); 

		}
	}
%>
	</div>

	
	<!--페이지-->
	<div class="pg">
		<%= PageUtil.printPageMobileList(v_totalpage, v_pageno, v_rowcount, "") %>
	</div>

	<!--Footer-->
	<jsp:include page="/learn/mobile/include/footer.jsp" flush="true" />
	<!--Footer-->
	
</div>
</html>