<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 오프라인 과정 학기별 점수 팝업
//  2. 프로그램명: gu_EducationStudyingOffTermScore_P.jsp
//  3. 개      요: 수강중인 오프라인과정
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009/11/24
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

	DataBox dbox = null;
    ArrayList list = (ArrayList)request.getAttribute("EducationStudyingOffTermScoreList");
    
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>학기별성적보기</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
</head>
<body id="popup"><!-- popup size : 400*277 -->
	<div id="pop_header">
		<h1><img src="/images/portal/myclass/pop_h1_tit1.gif" alt="학기별성적보기" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap" class="message_top">
			<table class="list">
			<colgroup><col width="24%" /><col width="19%" /><col width="19%" /><col width="19%" /><col width="19%" /></colgroup>
			<thead>
			<tr>
				<th>년도</th>
				<th>학기</th>
				<th>과목수</th>
				<th>평균점수</th>
				<th class="end">등수</th>
			</tr>
			</thead>
			<tbody>
<%
	for(int i=0; i < list.size(); i++){
		dbox = (DataBox)list.get(i);
%>
			<tr>
				<td><%= dbox.getString("d_year") %></td>
				<td><%= dbox.getString("d_term") %></td>
				<td><%= dbox.getString("d_lecturecnt") %></td>
				<td><%= dbox.getString("d_score") %></td>
				<td class="end"><%= dbox.getString("d_rank") %></td>
			</tr>
<%
	}												
%>
			</tbody>
			</table>
		</div>
	</div>
	<div id="pop_footer">
		<p class="f_btn"><a href="javascript:self.close()" class="btn_gr"><span>확인</span></a></p>
	</div>
</body>
</html>