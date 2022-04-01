<%@ page contentType="text/html;charset=euc-kr" %>
<%
if(!box.getSession("tem_grcode").equals("N000001") && (box.getSession("tem_type").equals("") || box.getSession("tem_type") == null || box.getSession("tem_type").equals("B"))){
		response.sendRedirect("/");
	}
    String topWarp_userid = box.getSession("userid");
    String topWarp_usernm = box.getSession("name");
    String topWarp_gadmin = box.getSession("gadmin");

    boolean isUserLogin = ( !topWarp_userid.equals("") ) ? true : false;

    String titleStr = box.getString("title_str");
    titleStr = (titleStr == null || titleStr.equals("")) ? "한국콘텐츠아카데미" : titleStr + " - 한국콘텐츠아카데미";

    java.text.SimpleDateFormat headerSdf = new java.text.SimpleDateFormat("yyyyMMdd");
    java.util.Date headerDate = new java.util.Date();
    int currDate = Integer.parseInt( headerSdf.format(headerDate) );
%>
<!DOCTYPE html> 
<html>
	<head> 
    <meta property="og:title" content="<%= box.getString("og_title") %>">
    <meta property="og:url" content="<%= box.getString("og_url") %>">
    <meta property="og:description" content="<%= box.getString("og_description") %>">
    <meta property="og:image" content="<%= box.getString("og_image") %>">

	<meta charset="euc-kr">
	<meta name="viewport" content="width=device-width, initial-scale=1">

	<title><%= titleStr %></title>

    <link href="/css/jqueryui/1.10.0/themes/base/jquery-ui.css" rel="stylesheet" />
	<link href="/css/2013/common.css" rel="stylesheet">
	<link href="/css/2013/layout.css" rel="stylesheet">

	<link href="/css/2013/header.css" rel="stylesheet">
<%
    if ( box.getString("isMain").equals("Y") ) {
%>
	<link href="/css/2013/main.css" rel="stylesheet">
<%
    }
%>
	<link href="/css/2013/sub.css" rel="stylesheet">

	<script type="text/javascript" src="/js/jquery/1.8.3/jquery.js"></script>
    <script type="text/javascript" src="/js/jqueryui/1.10.0/jquery-ui.js"></script>
    <script type="text/javascript" src="/js/2013/jquery.selectbox-0.6.1.js"></script>
    <script type="text/javascript" src="/js/2013/caption.js"></script>
    <script type="text/javascript" src="/js/2013/common.js"></script>
	<script type="text/javascript" src="/js/2013/header.js"></script>
	<script type="text/javascript" src="/js/2013/lib.js"></script>
	<script type="text/javascript" src="/js/2013/main.js"></script>
    <script type="text/javascript" src="/script/cresys_lib.js"></script>
    <script type="text/javascript" src="/script/mainscript.js"></script>
    <!-- <script type="text/javascript" src="/js/2013/caption.js"></script> //-->
    <!-- <script type="text/javascript" src="/js/2013/jquery.easing.1.3.js"></script> //-->
    <!-- <script type="text/javascript" src="/js/2013/jquery.cycle2.min.js"></script> //-->
	<script>
		/* 구버전 아카데미 사이트 접근 시 신규 B2C 콘텐츠인재캠퍼스로 이동 rsg20180124 */
		location.href="https://edu.kocca.kr";
	</script>
</head>
<body>
<!-- 상단 기본 form //-->
<form name="topdefaultForm" method="post" action="/" id="topdefaultForm" >
    <input type="hidden" name="p_process" value="" />
    <input type="hidden" name="gubun" value="0" />
    <input type="hidden" name="menuid" value="" />
    <input type="hidden" name="url" value="" />
    <input type="hidden" name="p_cmuno" value="" />
    <input type="hidden" name="searchClass" value="" />
    <input type="hidden" name="p_auth" value="" />
    <input type="hidden" name="p_topuserid" value="" />
    <input type="hidden" name="p_toppwd" value="" />
    <input type="hidden" name="p_area" value="" />
    <input type="hidden" name="p_lsearchtext" id="oLsearchtext" value="" />
    <input type="hidden" name="p_lecture_cls" value="" /> <!-- 열린강좌(콘텐츠온) 분류별 조회가 필요하여 해당 엘리먼트 추가 //-->
    <input type="hidden" name="pageNo" value="" /> <!-- 열린강좌(콘텐츠온) 페이징 기능이 필요하여 해당 엘리먼트 추가 //-->
    <input type="submit" class="close" title="실행" />
</form>

<!-- topbar -->
<div id="topbar">
	<!-- body -->
	<div class="body">
		<div class="app-link">
			<a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">한국콘텐츠아카데미앱</a>
		</div>
		<div class="util">
<%
        if(topWarp_userid.equals("")) {
%>
			<a href="/learn/user/2013/portal/homepage/zu_MainLogin.jsp">로그인</a><span class="line"> | </span>
			<a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a>
<%
        } else {
%>
            <a href="#" class="my-menu-open"><%=topWarp_usernm%>님 개인메뉴</a><span class="line"> | </span>
			<div class="my-menu-layer">
				<ul>
					<li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">온라인 강의실</a></li>
					<li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">현장교육 강의실</a></li>
					<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE">내가 작성한 글</a></li>
					<li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage">상담내역</a></li>
					<li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">정보관리</a></li>
				</ul>
			</div>
			<a href="javascript:mainmenu('998');">로그아웃</a>
<%
        }
%>
		</div>
	</div>
	<!-- /body -->
</div>
<!-- /topbar -->
<!-- header-new -->
<div id="header-new">
	<div class="brand">
		<a href="/servlet/controller.homepage.MainServlet"><img src="/images/common/brand.png" alt="한국콘텐츠아카데미"></a>
	</div>

<!--
	<div class="search">
		<div class="field">
			<input type="text">
			<input type="image" src="/images/common/btn-search.png" alt="검색">
		</div>
		<div class="word">
			<ul id="searchWord">
				<li>
					<span><a href="#">인기검색어1</a></span>
					<span><a href="#">인기검색어1</a></span>
					<span><a href="#">인기검색어1</a></span>
					<span><a href="#">인기검색어1</a></span>
				</li>
				<li>
					<span><a href="#">인기검색어2</a></span>
					<span><a href="#">인기검색어2</a></span>
					<span><a href="#">인기검색어2</a></span>
					<span><a href="#">인기검색어2</a></span>
				</li>
			</ul>
		</div>
	</div>

-->
</div>
<!-- /header-new -->
<!-- nav -->
<div id="nav">
	<div class="sub-bg"></div>
	<ul>
		<li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">새소식</a>
			<ul>
				<li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                <li><a href="/servlet/controller.homepage.EventHomePageServlet?p_process=selectList">이벤트</a></li>
                <li><a href="/servlet/controller.homepage.TongBoardServlet?p_process=list" >아카데미 이야기</a></li>
                <li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list" >CMU 스토리</a></li>
				<!-- 2015-01-09 개편변경
                <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
                -->				
			</ul>
		</li>
		<li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001">현장교육</a>
			<ul>
				<li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001">교육신청</a></li>
				<!-- 2015-02-23 삭제
                <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=002">차세대 인재 양성교육</a></li>
                //-->
				<!--<li><a href="/servlet/controller.course.EduScheduleHomePageServlet?p_process=offSchlYearPlanList">일정안내</a></li>
			--></ul>
		</li>
		<li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">온라인교육</a>
			<ul>
				<li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">분야별</a></li>
				<li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikup">직업별</a></li>
				<li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem">교육체계도</a></li>
<%
        if ( currDate < 20160401 ) {
%>
				<li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=LiteratureSubjectList">휴넷인문학</a></li>
<%
        }
%>
			</ul>
		</li>
		<li><a href="javascript:fnOpenClassList('ALL');">열린강좌</a>
			<ul>
				<li><a href="javascript:fnOpenClassList('ALL');">분류별</a></li>
				<li><a href="javascript:fnOpenClassThemeList('ALL');">테마별</a></li>
			</ul>
		</li>
<!--
		<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">자료실</a>
			<ul>
				<li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">온라인교육</a></li>
				<li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list" >CMU 스토리</a></li>
				<li><a href="/servlet/controller.homepage.TongBoardServlet?p_process=list">아카데미 소식 뒷이야기</a></li>
			</ul>
		</li>
-->
        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome">고객센터</a>
			<ul>
				<li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome">학습지원</a></li>
				<li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">자주하는 질문</a></li>
				<li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
				<!--
                <li><a href="javascript:alert('수료증 발급안내 준비중입니다.');">수료증 발급안내</a></li>
                -->
			</ul>
		</li>
		<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">아카데미소개</a>
			<ul>
				<!--<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision">아카데미소개</a></li>-->
                <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">인사말</a></li>
                <!-- 2015-03-18 가림
                <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                //-->
                <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업안내</a></li>
                <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
                <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는길</a></li>
			</ul>
		</li>
	</ul>
</div>
<!-- /nav -->