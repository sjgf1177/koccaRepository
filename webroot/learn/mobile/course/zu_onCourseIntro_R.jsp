<%@ page language = "java" contentType = "text/html; charset=euc-kr" pageEncoding = "euc-kr" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<% 
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
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
</head>
<body class="other_sub">
<div id="sub_header">	
	<%@ include file="/learn/mobile/include/header.jsp" %>
</div>
<div id="kocca_subContent">	
	<h3 class="cource_step">1) 한국콘텐츠진흥원 한국콘텐츠아카데미 홈페이지에 접속합니다.</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">2)<span class="color_red"> 회원님의 계정으로 로그인</span> 합니다.(회원 미가입시 수강신청이 불가합니다.)</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">3) 대메뉴 중 <span class="color_red">온라인과정</span>을 클릭하여 온라인과정 홈에 접근합니다.</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">4) 온라인과정 카테고리(기획/제작/유통/경영지원/특강)별로 수강신청을 원하는 과정을 탐색(검색)합니다.</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">5) 탐색(검색) 결과 목록에 나타나는 과정명을 클릭하면 과정상세 페이지로 이동합니다. 수강신청은 홈페이지에서만 가능하십니다.</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">6)<span class="color_red"> 1. 신청목록확인 ? 2. 결제정보확인 ? 3. 결제완료</span> 의 순으로 수강신청을 진행합니다.(유료과정의 경우에 결제프로세스를 진행합니다.)</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">7) 결제가 완료되면, <span class="color_red">나의강의실 > 수강신청 확인/취소 메뉴로 이동하여 수강신청 현황을 확인</span>합니다.(신청 및 결제만 완료된 경우에는 ‘승인대기’로 , 신청 및 결제, 관리자의 승인까지 완료된 경우에는 ‘승인’으로 표시됩니다.)</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">8) 수강신청 승인이 완료되면, <span class="color_red">나의강의실 > 수강신청 중인 과정에 신청 과정이 목록에 추가</span>됩니다.(단, 학습은 교육시작일 이후부터 가능합니다.)
	<h4 class="step_pad"></h4>
</h3>	
	
	<!--Footer-->
	<jsp:include page="/learn/mobile/include/footer.jsp" flush="true" />
	<!--Footer-->
</div>
</body>
</html>