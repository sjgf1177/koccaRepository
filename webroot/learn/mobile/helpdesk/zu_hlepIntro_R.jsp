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

	<div class="infor_area">
		<h3 class="icon_t">주요서비스 안내</h3>
	</div>

	<div class="infor_Contents">
		<ul>
			<li>나의 강의실을 통해 수강중인 과정의 진도율, 취득점수 및 학습기간 정보를 확인 할 수 있습니다. </li>
			<li>학습/상담 이력에서는 등록하신 내용에 대한 답변여부와 내용을 확인 하실 수 있습니다.</li>
			<li>골드클래스에서는 국내 콘텐츠 전문가 동영상을 카테고리화하여 과정에 대한 소개 및 동영상을 제공합니다.  </li>
		</ul>
	</div>	

	<div class="infor_area">
		<h3 class="icon_t">수강신청 및 학습관련</h3>
	</div>

	<div class="infor_Contents">
		<ul>
			<li>수강신청 및 학습은 PC를 통해 아카데미 사이트에서 이용하실 수 있으며, 모바일에서는 지원하지 않습니다.</li>			
		</ul>
	</div>
	
	<div class="infor_area">
		<h3 class="icon_t">로그인/회원가입 안내</h3>
	</div>

	<div class="infor_Contents">
		<ul>
			<li>휴대폰을 통해 아카데미 어플리케이션 서비스를 이용하시려면 아카데미 회원이셔야 합니다.</li>
			<li>회원가입은 아카데미 사이트에서 하실 수 있습니다.</li>
			<li>로그인 시 아카데미 사이트와 동일 아이디/비밀번호를 이용하시면 됩니다. </li>
			<li class="li_last">아이디/비밀번호를 잊으신 경우 아카데미 사이트에서 조회하실 수 있습니다. </li>
		</ul>
	</div>

	<div class="infor_area">
		<h3 class="icon_t">고객센터 전화상담 안내</h3>
	</div>

	<div class="customer_area">
		<ul>
			<li class="phone"><img src="/images/mobile/phone.png"></li>			
			<li>온라인 문의 : <a href="tel:02-2161-0077">02)2161-0077</a></li>
			<li>오프라인 문의 : <a href="tel:02-2161-0072">02)2161-0072</a></li>			
		</ul>
	</div>
	
	<!--Footer-->
	<jsp:include page="/learn/mobile/include/footer.jsp" flush="true" />
	<!--Footer-->

</div>

</body>
</html>