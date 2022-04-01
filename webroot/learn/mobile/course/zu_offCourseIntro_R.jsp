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
	<h3 class="cource_step">3) 대메뉴 중 <span class="color_red">오프라인과정</span>을 클릭하여 오프라인과정 홈에 접근합니다.</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">4) 오프라인과정 카테고리(방송영상/게임제작/기획창작)별로 수강신청을 원하는 과정을 탐색(검색)합니다.</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">5) 탐색(검색) 결과 목록에 나타나는 과정명을 클릭하면 과정상세 페이지로 이동합니다. 수강신청은 홈페이지에서만 가능하십니다.</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">6)<span class="color_red"> 수강신청 팝업이 생성되면, 각 항목(기본정보, 병역사항, 학력, 경력사항, 자격증 및 자기소개서)을 작성한 후 [신청]버튼을 클릭</span>합니다.</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">7) 신청이 완료되면, <span class="color_red">나의강의실 > 수강신청 확인/취소 메뉴로 이동하여 수강신청 현황을 확인</span>합니다. (관리자가 [예비합격]처리를 한 경우에, 승인상태가 ‘예비합격’으로 표시되며, [결제하기]버튼이 표시됩니다.)</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">8) [결제하기]버튼을 눌러, 결제프로세스에 따라 결제를 진행합니다</h3>
	<h4 id="step_arrow" class="step_arrow"></h4>
	<h3 class="cource_step">9) 결제가 완료되면, <span class="color_red">나의강의실 > 수강신청 중인 과정에 신청 과정이 목록에 추가</span>됩니다.(단, 학습은 교육시작일 이후 오프라인 교육참여시 가능합니다.)</h3>
	<h4 class="step_pad"></h4>
</h3>
	
	<!--Footer-->
	<jsp:include page="/learn/mobile/include/footer.jsp" flush="true" />
	<!--Footer-->

</div>
</body>
</html>