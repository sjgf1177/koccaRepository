<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page errorPage="/learn/library/error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.credu.library.*"%>
<%@ page import="com.credu.course.*"%>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    ArrayList list1 = null;
    ArrayList list2 = null;
    
    String left_menu = "helpdesk";
   
%>

<%@ include file="/learn/user/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역시작 -->
<SCRIPT LANGUAGE="JavaScript">
<!--

//-->
</SCRIPT>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->

<!-- Form 영역 시작 -->
<form name="nform1" method="post" onsubmit="javascript:select();">
	<input type="hidden" name="p_process">


	<h2>
		<img src="/images/portal/studying/h2_tit11.gif" alt="원격지원서비스" class="fl_l" />
		<p class="category">
			Home > 학습지원센터 > <strong>원격지원서비스</strong>
		</p>
	</h2>

	<p>
		<img src="/images/portal/homepage_renewal/support/500090_01.gif" alt="원격지원서비스" class="fl_l" />
	</p>
	<p>
		<img src="/images/portal/homepage_renewal/support/500090_02.gif" alt="Step01. 교육운영팀 02-6441-3258 상담전화 고객센터로 전화 후 학습 오류 자가증상을 설명합니다." class="fl_l" />
		<a title="새창열림" href="https://113366.com/edu_kocca" target="_blank">
			<img src="/images/portal/homepage_renewal/support/500090_03.gif" alt="Step02. 원격지원 사이트 접속 : 원격지원 사이트 바로가기" class="fl_l" />
		</a>
		<img src="/images/portal/homepage_renewal/support/500090_04.gif" alt="Step03. 고객정보입력(이름 및 숫자 아이콘 클릭! 숫자 안보일경우 새로고침 클릭!)" class="fl_l" />
		<img src="/images/portal/homepage_renewal/support/500090_05.gif" alt="Step04. 프로그램 설치 및 동의(일회성 실행파일이 자동 설치되며 고객의 동의 후 서비스 시작)" class="fl_l" />
	</p>


	<!-- footer 영역 시작 -->
	<%@ include file="/learn/user/portal/include/footer.jsp"%>
	<!-- footer 영역 종료 -->