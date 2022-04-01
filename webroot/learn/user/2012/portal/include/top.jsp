<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import = "com.credu.homepage.LoginBean" %>
<%@ page import="java.net.InetAddress" %>
<%
	InetAddress inet= InetAddress.getLocalHost();
	String ipSplit = inet.getHostAddress();
	String [] ipnum=ipSplit.replace(".","/").split("/");

	String topmenu = box.getString("topmenu");
	if ((topmenu == null) || (topmenu.equals("")) ) topmenu = "1";
	String submenu1 = box.getString("submenu");
	
	String notSelected = "ratemenu";
	String selected = "ratemenuon";
	int zindex = 0;
	
	String topWarp_userid = box.getSession("userid");
  	String topWarp_usernm = box.getSession("name");
  	String topWarp_gadmin = box.getSession("gadmin");
%>
<html lang="ko">
<head>
<title>한국콘텐츠진흥원 아카데미(<%=ipnum[3]%>)</title>
<style type="text/css" media="screen">@import "/css/2012/common.css";</style>
<% if (topmenu.equals("0")) { %>
<style type="text/css" media="screen">@import "/css/2012/main.css";</style>
<% } else { %>
<style type="text/css" media="screen">@import "/css/2012/sub.css";</style>
<% } %>
<link href="/css/jqueryui/1.8/themes/base/jquery-ui.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/js/jquery/1.7.2/jquery.min.js"></script>
<script type="text/javascript" src="/js/jqueryui/1.9.2/jquery-ui.min.js"></script>
<script type="text/javascript" src="/js/2012/lib.js"></script>
<script type="text/javascript" src="/js/2012/caption.js"></script>
<script type="text/javascript" src="/js/2012/jquery.banner.js"></script>
<script type="text/javascript" src="/script/mainscript.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Content-Language" content="euc-kr">
<meta http-equiv="imagetoolbar" content="false">
<meta name="robots" content="all">
<meta name="MSSmartTagsPreventParsing" content="true">
<meta name="description" content="">
</head>
<body>
<form name="topdefaultForm" method="post" action="/" id="topdefaultForm" >
  <input type='hidden' name='p_process'   value="">
  <input type='hidden' name='gubun'       value='0'>
  <input type='hidden' name='menuid'      value=''>
  <input type='hidden' name='url'         value=''>
  <input type='hidden' name='p_cmuno'     value=''>
  <input type='hidden' name='searchClass' value=''>
  <input type='hidden' name='p_auth'      value=''>
  <input type='hidden' name='p_topuserid' value=''>
  <input type='hidden' name='p_toppwd'    value=''>
  <input type='hidden' name='p_area'    value=''>
  <input type='hidden' name='p_lsearchtext'    value=''>
  <input type="submit" class="close" title="실행">
</form>
<%@ include file="/learn/user/2012/portal/include/sitemap.jsp"%>
<!-- viewer start -->
<table id="viewralbe" cellspacing="0" cellpadding="0">
	<colgroup><col width="15%"><col width="*"><col width="15%"></colgroup>
	<tr>
		<td class="alignR"><a href="javascript:changefacilityimage('prev');"><img src="/images/2012/sub/page7/arrow_left_normal.png" style="cursor:pointer;" class="imageprev" alt="이전 사진"></a></td>
		<td class="alignC">
			<div class="facilityImageBox">
				<img src="/images/2012/sub/page7/facilityImage/1.jpg" class="facilityImage" alt="시설소개 사진"><br>
				<p class="photoname"></p>
			</div>
		</td>
		<td class="alignL"><a href="javascript:changefacilityimage('next');"><img src="/images/2012/sub/page7/arrow_right_normal.png" style="cursor:pointer;" class="imagenext" alt="다음 사진"></a></td>
	</tr>
</table>
<img src="/images/2012/sub/page7/btn_close.png" class="viewclose" alt="뷰어 닫기"/>
<div id="facilityview"></div>
<!-- viewer end -->
<div id="header">
	<div class="topheader">
		<img src="/images/2012/common/bg_header_deco.png" border="0" alt="헤더 꾸밈 이미지" style="margin-top: -4px;"/>
		<div class="logo"><a href="/servlet/controller.homepage.MainServlet" tabindex="1" title="홈으로 돌아갑니다"><img src="/images/2012/common/logo.png" alt="한국콘텐츠아카데미"></a></div>
		<ul class="memberoption">
			<li class="leftbg"></li>
			<li class="centerbg">
				<div class="fastmenu"><a href="javascript:sitemap();" tabindex="2" title="원하는 콘텐츠로 더욱 빠르게 이동할 수 있습니다"><img src="/images/2012/common/btn_fastmenu.png" alt="빠른메뉴"></a></div>
				<dl class="search">
					<dd class="intext"><input type="text" name="topWarp_searchText" tabindex="2" onkeypress="searchtotal_enter(event);" id="topWarp_searchText" onfocus="this.style.background='#000000';" title="검색어를 입력해주세요."></dd>
					<dd class="outsearch"><a href="javascript:totalSubjSearch1();" tabindex="3" title="종합검색을 시작합니다"><img src="/images/2012/common/btn_search.png" alt="검색" title="검색을 시작합니다."></a></dd>
				</dl>
				<dl class="option">
				<% if(topWarp_userid.equals("")) {%>     <%--로그인이 안되었는 경우--%>	
					<dd><a href="javascript:menuMainForward('90','/servlet/controller.homepage.MainServlet?p_process=MainLogin');" tabindex="4" title="로그인화면으로 이동합니다"><img src="/images/2012/common/option_btn_login.png" alt="로그인"></a></dd>
					<dd><a href="javascript:menuForward('90','01');" tabindex="5" title="지금 회원가입을 해보세요"><img src="/images/2012/common/option_btn_join.png" alt="회원가입"></a></dd>
				<% } else { %>	
					<!-- 로그인 후 -->
					<dd class="membername"><b><%=topWarp_usernm%></b>님 어서오세요.</dd>
					<dd><a href="javascript:mainmenu('998');" tabindex="4" title="로그아웃합니다"><img src="/images/2012/common/option_btn_logout.png" alt="로그아웃"></a></dd>
					<dd><a href="javascript:menuForward('90','03');" tabindex="5" title="회원님의 정보를 수정합니다"><img src="/images/2012/common/option_btn_modify.png" alt="회원정보변경"></a></dd>
					<!-- 관리자 모드 -->
					<dd class="select" tabindex="6">
						<%= box.getSession("tem_grcode").equals("N000001") ? LoginBean.getAuthSelect(topWarp_userid, "p_topAuth", topWarp_gadmin, "onChange = 'javascript:topAuthChange();'") : ""%>
					</dd>
					<dd>
					<% if ((!(topWarp_gadmin.equals("ZZ") || topWarp_gadmin.equals("")))) {  %>
					<a href="javascript:topAdminOpenPage()" tabindex="7" title="LMS로 이동합니다"><img src="/images/2012/common/option_btn_lms.png" alt="LMS"></a>
					<% } %>
					</dd>
					<% if ((!(topWarp_gadmin.equals("ZZ") || topWarp_gadmin.equals("")))) {  %>
					<!--<dd class="select">
						<select name="p_project" id="p_project" title="년도를 선택해주세요" tabindex="8">
							<option>선택</option>
							<option value="2011000011">2010</option>
							<option value="2011000006">2011</option>
							<option value="2011000010">2012</option>								
						</select>
					</dd>
					<dd>
						<a href="javascript:goSubIndex()" tabindex="9" title="프로젝트게시판으로 이동합니다"><img src="/images/2012/common/option_btn_project.png" alt="프로젝트게시판"></a>
					</dd>-->
					<% } %>
				<% } %>
				</dl>
			</li>
			<li class="rightbg"></li>
		</ul>
		<ul class="navi">
			<li><a href="javascript:menuMainForward('3', '/servlet/controller.study.MyClassServlet?p_process=EducationSubjectPage');" id="top1" tabindex="11" title="나의강의실로 이동합니다. 이용하시기 전에 로그인을 해주세요."><img src="/images/2012/common/menu/menu1.png" id="navi1" alt="나의강의실"></a></li>
			<li><a href="javascript:menuMainForward('1', '/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList');" id="top2" tabindex="21" title="온라인 교육과정을 확인 및 신청할 수 있습니다."><img src="/images/2012/common/menu/menu2.png" id="navi2" alt="온라인과정"></a></li>
			<li><a href="javascript:menuMainForward('2', '/servlet/controller.off.ProposeOffServlet?p_process=SubjectList');" id="top3" tabindex="31" title="오프라인 교육과정을 확인 및 신청할 수 있습니다."><img src="/images/2012/common/menu/menu3.png" id="navi3" alt="오프라인과정"></a></li>
			<li><a href="javascript:menuMainForward('4', '/servlet/controller.homepage.HomeNoticeServlet?p_process=List');" id="top4" tabindex="41" title="학습 전/중/후 필요한 정보와 자료를 제공합니다."><img src="/images/2012/common/menu/menu4.png" id="navi4" alt="학습지원"></a></li>
			<li><a href="javascript:menuForward('5','12');" id="top5" tabindex="51" title="유명인사들의 공개강의를 열람할 수 있습니다."><img src="/images/2012/common/menu/menu5.png" id="navi5" alt="열린강의"></a></li>
			<li><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage');" id="top6" tabindex="61" title="교육 중 궁금한 것이 있다면 무엇이든 물어보세요. 이용하시기 전에 로그인을 해주세요."><img src="/images/2012/common/menu/menu6.png" id="navi6" alt="지식팩토리"></a></li>
			<li><a href="javascript:menuForward('7','01');" id="top7" tabindex="71" title="한국콘텐츠진흥원 아카데미를 소개합니다."><img src="/images/2012/common/menu/menu7.png" id="navi7" alt="아카데미소개"></a></li>
		</ul>
	</div>
</div>
<div id="submenuline" class="submenubox">
	<div class="subnavibox">
		<table id="submenu1" class="subnavi smenu1" summary="나의 강의실의 상속메뉴입니다">
			<tr>
				<td><a href="javascript:menuForward('3','11');" tabindex="12" title="현재 학습중인 과정을 확인 또는 학습할 수 있습니다.">수강중인 과정</a></td>
				<td><a href="javascript:menuForward('3','02');" tabindex="13" title="회원가입 후 현재까지 학습한 이력을 확인할 수 있습니다.">나의교육이력</a></td>
				<td><a href="javascript:menuForward('3','17');" tabindex="14" title="신청한 과정을 확인하거나 취소할 수 있습니다.">교육신청 확인/취소</a></td>
<!--				<td><a href="javascript:menuForward('3','19');" tabindex="15" title="결제 조회 또는 취소를 할 수 있습니다.">수강료 결제관리</a></td>-->
				<td><a href="javascript:menuForward('3','12');" tabindex="16" title="상담하신 내역을 확인할 수 있습니다.">나의 상담내역</a></td>
				<td><a href="javascript:menuForward('3','07');" tabindex="17" title="회원님이 참여하신 설문을 확인할 수 있습니다.">나의 설문</a></td>
				<td><a href="javascript:menuMainForward('4','/servlet/controller.study.MyClassServlet?p_process=StudyHistoryListSyuro');" tabindex="18" title="과정을 수료하신 경우 수료증을 출력할 수 있습니다.">수료증 출력</a></td>
<!--				<td><a href="/servlet/controller.study.MyClassServlet?p_process=celp" tabindex="18" title="자신의 리더십 및 팀웍의 역량을 진단해볼 수 있습니다.">자기역량진단</a></td>-->
			</tr>
		</table>
		<table id="submenu2" class="subnavi smenu2" summary="온라인과정의 상속메뉴입니다">
			<tr>
				<td><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList')" tabindex="22" title="방송영상 분야 온라인과정을 안내합니다.">전체</a></td>
				<td><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&amp;p_area=B0')" tabindex="23" title="방송영상 분야 온라인과정을 안내합니다.">방송영상</a></td>
				<td><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&amp;p_area=G0')" tabindex="24" title="게임개발 분야 온라인과정을 안내합니다.">게임개발</a></td>
				<td><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&amp;p_area=K0')" tabindex="25" title="문화콘텐츠 분야 온라인과정을 안내합니다.">문화콘텐츠</a></td>
				<td><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=Curriculum')" tabindex="26" title="온라인 교육과정을 확인합니다">커리큘럼</a></td>
			</tr>
		</table>
		<table id="submenu3" class="subnavi smenu3" summary="오프라인과정의 상속메뉴입니다">
			<tr>
				<td><a href="javascript:menuMainForward('2', '/servlet/controller.off.ProposeOffServlet?p_process=SubjectList');" tabindex="32" title="전체 오프라인과정을 안내합니다.">전체</a></td>
				<td><a href="javascript:menuForward('2','01');" tabindex="33" title="방송영상 분야 오프라인과정을 안내합니다.">방송영상</a></td>
				<td><a href="javascript:menuForward('2','02');" tabindex="34" title="게임개발 분야 오프라인과정을 안내합니다.">게임제작</a></td>
				<td><a href="javascript:menuForward('2','03');" tabindex="35" title="기획창작 분야 오프라인과정을 안내합니다.">기획창작</a></td>
<!--				<td><a href="javascript:menuForward('2','04');" tabindex="36" title="학습 중 필요한 자료를 제공합니다.">교육자료실</a></td>-->
<!--				<td><a href="javascript:menuForward('2','05');" tabindex="37" title="오프라인 교육의 연간 및 월간 교육일정을 확인할 수 있습니다.">교육일정</a></td>-->
				<!-- <td><a href="#" title="교육실, 교육장비, 휴식공간 등 학습환경을 소개합니다.">교육환경소개</a></td> -->
			</tr>
		</table>
		<table id="submenu4" class="subnavi smenu4" summary="학습지원의 상속메뉴입니다">
			<tr>
				<td><a href="javascript:menuForward('4','07');" tabindex="42" title="한국콘텐츠진흥원 아카데미의 새소식을 알려드립니다.">공지사항</a></td>
				<td><a href="javascript:menuForward('4','01');" tabindex="43" title="자주 묻는 질문을 모아두었습니다.">FAQ</a></td>
				<td><a href="javascript:menuForward('4','02');" tabindex="44" title="궁금한 점이 있다면 물어보세요.">묻고답하기</a></td>
				<td><a href="javascript:menuForward('4','05');" tabindex="45" title="온라인 학습 전 정상적인 학습을 위해 학습환경을 설정합니다.">학습환경도우미</a></td>
				<td><a href="javascript:menuForward('4','10');" tabindex="46" title="오프라인 교육 시 사용되는 교재를 소개합니다.">교재소개</a></td>
				<td><a href="javascript:menuForward('4','09');" tabindex="47" title="비공개 질문을 원하신다면 이곳 1:1문의를 이용해주세요.">1:1문의</a></td>
				<td><a href="javascript:menuForward('4','12');" tabindex="48" title="아카데미에서 진행하는 다양한 이벤트를 확인할 수 있습니다.">이벤트</a></td>
				<!-- <td><a href="javascript:menuForward('4','07');" title="수강신청이 어려우실 경우 가이드를 통해 따라해보세요.">수강신청방법</a></td> -->
				<!-- <td><a href="javascript:menuForward('4','07');" title="온라인/오프라인 교육 시작 전 궁금한 점이 있다면 안내를 받아보세요.">교육가이드</a></td> -->
			</tr>
		</table>
		<table id="submenu5" class="subnavi smenu5" summary="열린강의의 상속메뉴입니다">
			<tr>
				<td><a href="javascript:menuForward('5','12');" tabindex="52" title="방송영상 관련 열린강의를 열람할 수 있습니다.">방송영상</a></td>
				<td><a href="javascript:menuForward('5','13');" tabindex="53" title="영화 관련 열린강의를 열람할 수 있습니다.">영화</a></td>
				<td><a href="javascript:menuForward('5','14');" tabindex="54" title="다큐멘터리 관련 열린강의를 열람할 수 있습니다.">다큐멘터리</a></td>
				<td><a href="javascript:menuForward('5','15');" tabindex="55" title="문화예술 관련 열린강의를 열람할 수 있습니다.">문화예술</a></td>
				<td><a href="javascript:menuForward('5','17');" tabindex="56" title="게임 관련 열린강의를 열람할 수 있습니다.">게임</a></td>
				<td><a href="javascript:menuForward('5','16');" tabindex="57" title="기타 관련 열린강의를 열람할 수 있습니다.">기타</a></td> 
				<!-- <td><a href="javascript:menuForward('5','17');" title="기획 관련 열린강의를 열람할 수 있습니다.">기획</a></td>
				<td><a href="javascript:menuForward('5','17');" title="기타 관련 열린강의를 열람할 수 있습니다.">기타</a></td> -->
			</tr>
		</table>
		<table id="submenu6" class="subnavi smenu6" summary="커뮤니티의 상속메뉴입니다">
			<tr>
				<td><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage')" tabindex="62" title="커뮤니티의 전체 지식을 종합해보았습니다.">종합</a></td>
				<td><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&amp;p_area=B0')" tabindex="63" title="방송영상 분야 커뮤니티 공간입니다.">방송영상</a></td>
				<td><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&amp;p_area=G0')" tabindex="64" title="게임개발 분야 커뮤니티 공간입니다.">게임개발</a></td>
				<td><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&amp;p_area=K0')" tabindex="65" title="문화콘텐츠 분야 커뮤니티 공간입니다.">문화콘텐츠</a></td>
<!--				<td><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&amp;p_area=C0')" tabindex="66" title="학습 외 커뮤니티 공간입니다.">문의</a></td>-->
				<td><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&amp;p_area=MINE')" tabindex="67" title="회원님께서 질문하신 내용을 이용할 수 있습니다.">내가 작성한 글</a></td>
			</tr>
		</table>
		<table id="submenu7" class="subnavi smenu7" summary="아카데미소개의 상속메뉴입니다">
			<tr>
				<td><a href="javascript:menuForward('7','01');" tabindex="72" title="한국콘텐츠진흥원장님께서 회원님들께 드리는 인사말입니다.">진흥원장 인사말</a></td>
				<td><a href="javascript:menuForward('7','02');" tabindex="73" title="아카데미의 설립목적과 비전에 대해 소개합니다.">아카데미소개</a></td>
				<td><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=facility" tabindex="74" title="한국 콘텐츠 아카데미의 시설을 안내합니다.">시설소개</a></td>
				<td><a href="javascript:menuForward('7','05');" tabindex="75" title="아카데미 교육사업을 안내합니다.">교육사업</a></td>
				<td><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList" tabindex="76" title="한국콘텐츠진흥원 아카데미의 홍보활동을 공개합니다">아카데미소식</a></td>
				<td><a href="javascript:menuForward('7','06');" tabindex="77" title="아카데미와 협력관계인 기관을 소개합니다.">교육협력기관</a></td>
				<td><a href="javascript:menuForward('7','04');" tabindex="78" title="한국콘텐츠진흥원 아카데미에 오시는길을 안내합니다.">오시는길</a></td>
				<td><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus" tabindex="79" title="단체수강문의 및 기업문의,제안등을 할 수 있습니다">Contact Us</a></td>
			</tr>
		</table>
	</div>
</div>
<div id="container">
<% if(topmenu.equals("1")) { // 로그인 %>	
	<div class="submenutool">
		<ul class="ratemenutool">
			<li><button type="button" tabindex="107" onclick="javascript:menuForward('90','06');" class="<%=submenu1.equals("7")?selected:notSelected%>" title="한국콘텐츠아카데미에서는 고객님의 소중한 개인정보를 이렇게 관리 및 이용합니다."><p>개인정보<br>처리방침</p></button></li>
			<li><button type="button" tabindex="106" onclick="javascript:menuForward('90','05');" class="<%=submenu1.equals("6")?selected:notSelected%>" title="한국콘텐츠아카데미를 이용하시기 위한 약관입니다."><p>이용약관</p></button></li>
			<li><button type="button" tabindex="105" onclick="javascript:menuForward('90','04');" class="<%=submenu1.equals("5")?selected:notSelected%>" title="회원탈퇴를 하고 싶은 경우 클릭해주세요."><p>회원탈퇴</p></button></li>
			<li><button type="button" tabindex="104" onclick="javascript:menuForward('90','03');" class="<%=submenu1.equals("4")?selected:notSelected%>" title="회원님의 비밀번호 및 개인정보를 변경하고자 하실 때 이용해주세요."><p>회원정보<br>변경</p></button></li>
			<li><button type="button" tabindex="103" onclick="javascript:menuForward('90','02');" class="<%=submenu1.equals("3")?selected:notSelected%>" title="아이디 또는 비밀번호를 잊으셨다면 이곳을 이용해주세요."><p>아이디<br>비밀번호<br>찾기</p></button></li>
			<li><button type="button" tabindex="102" onclick="javascript:menuForward('90','01');" class="<%=submenu1.equals("2")?selected:notSelected%>" title="한국콘텐츠아카데미에 처음 오셨나요? 무료회원가입으로 다양한 서비스를 받아보세요."><p>회원가입</p></button></li>
			<li><button type="button" tabindex="101" onclick="javascript:menuMainForward('90','/servlet/controller.homepage.MainServlet?p_process=MainLogin');" class="<%=submenu1.equals("1")?selected:notSelected%>" title="이미 회원이신 분께서는 회원로그인을 해주세요."><p>회원로그인</p></button></li>
		</ul>
	</div>
<%  } else if (topmenu.equals("2")) { //나의 강의실%>	
	<div class="submenutool">
		<ul class="ratemenutool">
<!--			<li><button type="button" tabindex="108" onclick="javascript:menuMainForward('4','/servlet/controller.study.MyClassServlet?p_process=celp');" class="<%=submenu1.equals("8")?selected:notSelected%>" title="비전 진단과 리더쉽 진단을 통해  조직의 비전 및 목표에 필요한 역량을 확인합니다"><p>자기역량<br>진단</p></button></li>-->
			<li><button type="button" tabindex="107" onclick="javascript:menuMainForward('4','/servlet/controller.study.MyClassServlet?p_process=StudyHistoryListSyuro');" class="<%=submenu1.equals("7")?selected:notSelected%>" title="과정을 수료하신 경우 수료증을 출력할 수 있습니다."><p>수료증<br>출력</p></button></li>
			<li><button type="button" tabindex="106" onclick="javascript:menuForward('3','07');" class="<%=submenu1.equals("6")?selected:notSelected%>" title="회원님이 참여하신 설문을 확인할 수 있습니다."><p>나의 설문</p></button></li>
			<li><button type="button" tabindex="105" onclick="javascript:menuForward('3','12');" class="<%=submenu1.equals("5")?selected:notSelected%>" title="상담하신 내역을 확인할 수 있습니다."><p>나의<br>상담내역</p></button></li>
<!--			<li><button type="button" tabindex="104" onclick="javascript:menuForward('3','19');" class="<%=submenu1.equals("4")?selected:notSelected%>" title="결제 조회 또는 취소를 할 수 있습니다."><p>수강료<br>결제관리</p></button></li>-->
			<li><button type="button" tabindex="103" onclick="javascript:menuForward('3','17');" class="<%=submenu1.equals("3")?selected:notSelected%>" title="신청한 과정을 확인하거나 취소할 수 있습니다."><p>교육신청<br>확인/취소</p></button></li>
			<li><button type="button" tabindex="102" onclick="javascript:menuForward('3','02');" class="<%=submenu1.equals("2")?selected:notSelected%>" title="회원가입 후 현재까지 학습한 이력을 확인할 수 있습니다."><p>나의<br>교육이력</p></button></li>
			<li><button type="button" tabindex="101" onclick="javascript:menuForward('3','11');" class="<%=submenu1.equals("1")?selected:notSelected%>" title="현재 학습중인 과정을 확인 또는 학습할 수 있습니다."><p>수강중인<br>과정</p></button></li>
		</ul>
	</div>
<%  } else if (topmenu.equals("3")) { //온라인과정%>		
	<div class="submenutool">
		<ul class="ratemenutool">
			<%-- <li><button type="button" onclick="getlink('page2_online_course.html');" class="<%=submenu1.equals("5")?selected:notSelected%>" title="수강신청 방법을 안내합니다"><p>수강신청<br>안내</p></button></li> --%>
			<li><button type="button" tabindex="105" onclick="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=Curriculum')" class="<%=submenu1.equals("5")?selected:notSelected%>" title="온라인 교육과정 목록으로 이동합니다"><p>커리큘럼</p></button></li>
			<li><button type="button" tabindex="104" onclick="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&amp;p_area=K0')" class="<%=submenu1.equals("4")?selected:notSelected%>" title="문화콘텐츠 분야 온라인과정 목록으로 이동합니다"><p>문화콘텐츠</p></button></li>
			<li><button type="button" tabindex="103" onclick="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&amp;p_area=G0')" class="<%=submenu1.equals("3")?selected:notSelected%>" title="게임개발 분야 온라인과정 목록으로 이동합니다"><p>게임개발</p></button></li>
			<li><button type="button" tabindex="102" onclick="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&amp;p_area=B0')" class="<%=submenu1.equals("2")?selected:notSelected%>" title="방송영상 분야 온라인과정 목록으로 이동합니다"><p>방송영상</p></button></li>
			<li><button type="button" tabindex="101" onclick="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList')" class="<%=submenu1.equals("1")?selected:notSelected%>" title="온라인과정 전체 목록을 확인합니다"><p>전체 과정</p></button></li>
		</ul>
	</div>
<%  } else if (topmenu.equals("4")) { //오프라인과정%>		
	<div class="submenutool">
		<ul class="ratemenutool">
			<%-- <li><button type="button" onclick="getlink('page3_offline_course.html');" class="<%=submenu1.equals("6")?selected:notSelected%>" title="수강신청 방법을 안내합니다"><p>수강신청<br>안내</p></button></li> --%>
<!--			<li><button type="button" tabindex="106" onclick="javascript:menuForward('2','05');" class="<%=submenu1.equals("5")?selected:notSelected%>" title="수강신청 방법을 안내합니다"><p>교육일정</p></button></li>-->
<!--			<li><button type="button" tabindex="105" onclick="javascript:menuForward('2','04');" class="<%=submenu1.equals("4")?selected:notSelected%>" title="문화콘텐츠 분야 온라인과정 목록으로 이동합니다"><p>교육자료실</p></button></li>-->
			<li><button type="button" tabindex="104" onclick="javascript:menuForward('2','03');" class="<%=submenu1.equals("3")?selected:notSelected%>" title="게임개발 분야 온라인과정 목록으로 이동합니다"><p>기획창작</p></button></li>
			<li><button type="button" tabindex="103" onclick="javascript:menuForward('2','02');" class="<%=submenu1.equals("2")?selected:notSelected%>" title="방송영상 분야 온라인과정 목록으로 이동합니다"><p>게임제작</p></button></li>
			<li><button type="button" tabindex="102" onclick="javascript:menuForward('2','01');" class="<%=submenu1.equals("1")?selected:notSelected%>" title="온라인과정 전체 목록을 확인합니다"><p>방송영상</p></button></li>
			<li><button type="button" tabindex="101" onclick="javascript:menuMainForward('2', '/servlet/controller.off.ProposeOffServlet?p_process=SubjectList');" class="<%=submenu1.equals("0")?selected:notSelected%>" title="전체 목록을 확인합니다"><p>전체</p></button></li>
		</ul>
	</div>
<%  } else if (topmenu.equals("5")) { //학습지원%>		

	<div class="submenutool">
		<ul class="ratemenutool">
			<%-- <li><button type="button" onclick="javascript:menuForward('4','01');" class="<%=submenu1.equals("8")?selected:notSelected%>" title="온라인/오프라인 교육 시작 전 궁금한 점이 있다면 안내를 받아보세요"><p>교육가이드</p></button></li> --%>
			<li><button type="button" tabindex="107" onclick="javascript:menuForward('4','12');" class="<%=submenu1.equals("7")?selected:notSelected%>" title="아카데미에서 진행하는 다양한 이벤트를 확인할 수 있습니다"><p>이벤트</p></button></li>
			<li><button type="button" tabindex="106" onclick="javascript:menuForward('4','09');" class="<%=submenu1.equals("6")?selected:notSelected%>" title="비공개 질문을 원하신다면 이곳 1:1문의를 이용해주세요"><p>1:1문의</p></button></li>
			<li><button type="button" tabindex="105" onclick="javascript:menuForward('4','10');" class="<%=submenu1.equals("5")?selected:notSelected%>" title="오프라인 교육 시 사용되는 교재를 소개합니다"><p>교재소개</p></button></li>
			<li><button type="button" tabindex="104" onclick="javascript:menuForward('4','05');" class="<%=submenu1.equals("4")?selected:notSelected%>" title="온라인 학습 전 정상적인 학습을 위해 학습환경을 설정합니다"><p>학습환경<br>도우미</p></button></li>
			<li><button type="button" tabindex="103" onclick="javascript:menuForward('4','02');" class="<%=submenu1.equals("3")?selected:notSelected%>" title="궁금한 점이 있다면 물어보세요"><p>묻고답하기</p></button></li>
			<li><button type="button" tabindex="102" onclick="javascript:menuForward('4','01');" class="<%=submenu1.equals("2")?selected:notSelected%>" title="자주 묻는 질문을 모아두었습니다"><p>FAQ</p></button></li>
			<li><button type="button" tabindex="101" onclick="javascript:menuForward('4','07');" class="<%=submenu1.equals("1")?selected:notSelected%>" title="한국콘텐츠진흥원 아카데미의 새소식을 알려드립니다"><p>공지사항</p></button></li>
		</ul>
	</div>

<%  } else if (topmenu.equals("6")) { //열린강의%>		
	<div class="submenutool">
		<ul class="ratemenutool">
			<li><button type="button" tabindex="106" onclick="javascript:menuForward('5','16');" class="<%=submenu1.equals("5")?selected:notSelected%>" title="기타 관련 열린강의를 열람할 수 있습니다."><p>기타</p></button></li>
			<li><button type="button" tabindex="105" onclick="javascript:menuForward('5','17');" class="<%=submenu1.equals("6")?selected:notSelected%>" title="게임 관련 열린강의를 열람할 수 있습니다."><p>게임</p></button></li>
			<li><button type="button" tabindex="104" onclick="javascript:menuForward('5','15');" class="<%=submenu1.equals("4")?selected:notSelected%>" title="문화예술 관련 열린강의를 열람할 수 있습니다."><p>문화예술</p></button></li>
			<li><button type="button" tabindex="103" onclick="javascript:menuForward('5','14');" class="<%=submenu1.equals("3")?selected:notSelected%>" title="다큐멘터리 관련 열린강의를 열람할 수 있습니다."><p>다큐멘터리</p></button></li>
			<li><button type="button" tabindex="102" onclick="javascript:menuForward('5','13');" class="<%=submenu1.equals("2")?selected:notSelected%>" title="영화 관련 열린강의를 열람할 수 있습니다."><p>영화</p></button></li>
			<li><button type="button" tabindex="101" onclick="javascript:menuForward('5','12');" class="<%=submenu1.equals("1")?selected:notSelected%>" title="현방송영상 관련 열린강의를 열람할 수 있습니다."><p>방송영상</p></button></li>
		</ul>
	</div>
	
<%  } else if (topmenu.equals("7")) { //아카데미소개%>		
	<div class="submenutool">
		<ul class="ratemenutool">
			<li><button type="button" tabindex="107" onclick="javascript:menuMainForward('7','/servlet/controller.homepage.HomePageContactServlet?p_process=contactus')" class="<%=submenu1.equals("7")?selected:notSelected%>" title="단체수강문의 및 기업문의,제안등을 할 수 있습니다."><p>Contact<br>Us</p></button></li>
			<li><button type="button" tabindex="106" onclick="javascript:menuForward('7','04');" class="<%=submenu1.equals("6")?selected:notSelected%>" title="한국콘텐츠진흥원 아카데미에 오시는길을 안내합니다."><p>오시는길</p></button></li>
			<li><button type="button" tabindex="105" onclick="javascript:menuForward('7','06');" class="<%=submenu1.equals("5")?selected:notSelected%>" title="아카데미와 협력관계인 기관을 소개합니다."><p>교육협력<br>기관</p></button></li>
			<li><button type="button" tabindex="104" onclick="javascript:menuMainForward('7','/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList')" class="<%=submenu1.equals("4")?selected:notSelected%>" title="한국콘텐츠아카데미의 대외 활동 소식을 전해드립니다"><p>아카데미<br>소식</p></button></li>
			<li><button type="button" tabindex="103" onclick="javascript:menuForward('7','05');" class="<%=submenu1.equals("3")?selected:notSelected%>" title="아카데미 교육사업을 안내합니다."><p>교육사업</p></button></li>
			<li><button type="button" tabindex="108" onclick="javascript:menuMainForward('7','/servlet/controller.homepage.HomePageAboutUsServlet?p_process=facility')" class="<%=submenu1.equals("8")?selected:notSelected%>" title="한국 콘텐츠 아카데미의 시설을 안내합니다."><p>시설소개</p></button></li>
			<li><button type="button" tabindex="102" onclick="javascript:menuForward('7','02');" class="<%=submenu1.equals("2")?selected:notSelected%>" title="아카데미의 설립목적과 비전에 대해 소개합니다."><p>아카데미<br>소개</p></button></li>
			<li><button type="button" tabindex="101" onclick="javascript:menuForward('7','01');" class="<%=submenu1.equals("1")?selected:notSelected%>" title="한국콘텐츠진흥원장님께서 회원님들께 드리는 인사말입니다."><p>진흥원장<br>인사말</p></button></li>
		</ul>
	</div>

<% }else if (topmenu.equals("8")) { //강사공모%>		
	<div class="submenutool">
	<ul class="ratemenutool">
		<li><button type="button" tabindex="101" onclick="javascript:menuForward('7','01');" class="<%=submenu1.equals("1")?selected:notSelected%>" title="한국콘텐츠진흥원장님께서 회원님들께 드리는 인사말입니다."><p>강사<br>공모제</p></button></li>
	</ul>
</div>

<% } %>