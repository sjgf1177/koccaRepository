<%
//**********************************************************
//  1. 제      목: 오프라인 결제 실패
//  2. 프로그램명: gu_OffPayCheck_P.jsp
//  3. 개      요: 오프라인 결제 실패
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2010.01.19
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>결제완료</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
</head>
<body id="popup"><!-- popup size : 752* -->
	<div id="pop_header">
		<h1><img src="/images/portal/popup/myclass_h1_tit2.gif" alt="결제완료" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap" class="message_top">
			<ul class="basket_tab">
				<li><img src="/images/portal/myclass/basket_off_tab1_off.gif" alt="결제정보확인" /></li>
				<li><img src="/images/portal/myclass/basket_off_tab2_on.gif" alt="결제완료" /></li>
			</ul>
			
			<div class="message_wrap bo_bn pd_t45 pd_bn">
				<img src="/images/portal/myclass/basket_message3.gif" alt="고객님의 결제요청이 실패되었습니다." />
			</div>
		</div>
	</div>
	<div id="pop_footer">
		<p class="f_btn"><a href="javascript:top.window.close()" class="btn_gr"><span>닫기</span></a></p>
	</div>
</body>
</html>