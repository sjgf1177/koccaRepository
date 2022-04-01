<%@ page language = "java" contentType = "text/html; charset=euc-kr" pageEncoding = "euc-kr" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<% 
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	//과정상세
	DataBox dbox = (DataBox) request.getAttribute("_VIEW_");
	if(dbox == null) dbox = new DataBox("");
	
	//과정 차시목록
	ArrayList list = (ArrayList) request.getAttribute("_LIST_");
	if(list == null) list = new ArrayList();
	
	//강사 목록
	ArrayList tutorlist = (ArrayList) request.getAttribute("_TUTORLIST_");
	if(tutorlist == null) tutorlist = new ArrayList();
	
	DataBox listbox = null;
	
	/** 페이징 */
	int v_pageno = box.getInt("p_pageno");
	
	
	String v_cousegubun = box.getString("p_cousegubun");
	
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
<script type="text/javascript" src="/js/mdetect.js"></script>
<script type="text/javascript" language="javascript">
	//목록페이지 이동
	function goListPage()
	{
		var obj = document.listForm;

		obj.p_process.value = "onLineStudyingList";
		obj.action = "/servlet/controller.mobile.study.MyClassServlet";
		obj.submit();
	}

	//동영상보기
	function download(v_lesson, url, v_progessyn)
	{
		var v_mobile_url = "";
		var obj = document.viewForm; 
		var v_subj = obj.p_subj.value;
		var v_year = obj.p_year.value;
		var v_subjseq = obj.p_subjseq.value;
		
		if(f_is_trim(url) == "") 
		{
			alert("준비중입니다."); 
		}
		else
		{
			if(confirm("3G망 이용시 통신사의 요금제에 따른 이용요금이 발생할 수 있습니다. 계속 진행하시겠습니까?")){

				v_mobile_url = v_subj + "@" + v_year + "@" + v_subjseq + "@" + v_lesson + "@" + url + "@<%=s_userid%>@" + v_progessyn;
				//v_mobile_url = v_subj + "@" + v_year + v_subjseq + "@" + v_lesson + "@" + url + "@<%=s_userid%>";
				
				if ( DetectAndroid() )
				{
					window.kocca.showLecture(v_mobile_url);
				}
				else if ( DetectIphoneOrIpod() || DetectIpad() )
				{
					document.location.href = "kocca://showLecture?" + v_mobile_url;
				}
			}
		}
	}
	
	//ePUb
	function downloadePub(v_url)
	{		
		document.location.href = v_url;		
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
	<div class="h_area_corse">
		<h3 class="t">과정상세</h3>
	</div>
	
	<form name="listForm" method="post" action="/servlet/controller.mobile.study.MyClassServlet">
		<input type="hidden" name="p_process" value="onLineStudyingList" />
		<input type="hidden" name="p_menuid" value="<%=v_menuid%>" />
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>" />
	</form>
	
	<form name="viewForm" method="post" action="/servlet/controller.mobile.study.MyClassServlet">
		<input type="hidden" name="p_process" value="onLineStudyingList" />
		<input type="hidden" name="p_menuid" value="<%=v_menuid%>" />
		<input type="hidden" name="p_pageno" value="<%=v_pageno%>" />
		
		<input type="hidden" name="p_subj" value="<%=dbox.getString("d_subj")%>" />
		<input type="hidden" name="p_year" value="<%=dbox.getString("d_year")%>" />
		<input type="hidden" name="p_subjseq" value="<%=dbox.getString("d_subjseq")%>" />
	</form>
	<button id="sub_searchSubmit" onclick="return goListPage();">
		<span>목록</span>
	</button>
	<div class="h_area">
	<h3 class="h">과정소개</h3>
	</div>			

	<div class="board-view">
		<table cellpadding="0" cellspacing="0">
		<colgroup>
			<col width="25%" />
			<col width="%" />
		</colgroup>
		<tr>
			<td class="td_left">과정명</td>
			<td class="td_right">&lt;<%=dbox.getString("d_areanm")%>&gt; <%=dbox.getString("d_subjnm")%></td>
		</tr>	
		<%if (!dbox.getString("d_epub_file").equals("")) {%>
		<tr>
			<td class="td_left">EPub</td>
			<td class="td_right"><button id="test_Submit" onclick="return downloadePub('<%=dbox.getString("d_epub_file")%>');">ePub보기</button></td>
		</tr>
		<% } %>
		<tr>
			<td class="td_left">교육기간</td>
			<td class="td_right"><%=FormatDate.getFormatDate(dbox.getString("d_edustart"), "yyyy/M/d") + " ~ " + FormatDate.getFormatDate(dbox.getString("d_eduend"), "yyyy/M/d")%></td>
		</tr>	
		</table>
	</div>

	<div class="h_area">
		<h3 class="h">차시정보</h3>
		</div>			
	
		<div class="board-view01">
		<table cellpadding="0" cellspacing="0">
		<colgroup>
			<col width="25%" />
			<col width="%" />
			<col width="25%" />
		</colgroup>
		<tr>
			<td class="td_center01">차시</td>
			<td class="td_center02">강의주제</td>
			<td class="td_center02">동영상보기</td>
		</tr>
<% 
		if(list.size() == 0)
		{
%>
		<tr>
			<td class="td_center01" colspan="3">차시정보가 없습니다.</td>
		</tr>	
<%		
		}
		else
		{
			for(int i = 0, j = list.size(); i < j; i++)
			{
				listbox = (DataBox) list.get(i);
%>		
		<tr>
			<td class="td_center01"><%=listbox.getString("d_lesson")%></td>
			<td class="td_left"><%=listbox.getString("d_sdesc")%></td>
			<td class="td_center02"><button id="test_Submit" onclick="return download('<%=listbox.getString("d_lesson")%>', '<%=listbox.getString("d_mobile_url")%>', '<%=listbox.getString("d_processyn")%>');">동영상</button></td>
			
		</tr>	
<%
			}
		}
%>	
		</table>
	</div>
			
	<!--footer-->
	<jsp:include page="/learn/mobile/include/footer.jsp" flush="true" />
	<!--footer-->

</div>
</body>
</html>