<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType = "text/html;charset=euc-kr" %>
<html lang="ko">
<head>
<title>한국콘텐츠진흥원 아카데미</title>
<style type="text/css" media="screen">@import "/css/2012/test/common.css";</style>
<style type="text/css" media="screen">@import "/css/2012/test/main.css";</style>
<script type="text/javascript" src="/js/jquery/1.4/jquery.min.js"></script>
<script language="javascript" type="text/javascript" src="/js/2012/test/lib.js"></script>
<script language="javascript" type="text/javascript" src="/js/2012/test/jquery.banner.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<meta http-equiv="Content-Language" content="euc-kr">
<meta http-equiv="imagetoolbar" content="false">
<meta name="robots" content="all">
<meta name="MSSmartTagsPreventParsing" content="true">
<meta name="description" content="">
</head>
<body>
<div id="header">
	<div class="topheader">
		<div class="logo"><img src="/images/2012/common/logo.png" alt="한국콘텐츠아카데미"></div>
		<ul class="navi">
			<li><a href="#" title="나의강의실로 이동합니다. 이용하시기 전에 로그인을 해주세요."><img src="/images/2012/common/menu/menu1.png" onmouseover="slideCateScroll('on','1');" id="navi1" alt="나의강의실"></a></li>
			<li><a href="#" title="온라인 교육과정을 확인 및 신청할 수 있습니다."><img src="/images/2012/common/menu/menu2.png" onmouseover="slideCateScroll('on','2');" id="navi2" alt="온라인과정"></a></li>
			<li><a href="#" title="오프라인 교육과정을 확인 및 신청할 수 있습니다."><img src="/images/2012/common/menu/menu3.png" onmouseover="slideCateScroll('on','3');" id="navi3" alt="오프라인과정"></a></li>
			<li><a href="#" title="학습 전/중/후 필요한 정보와 자료를 제공합니다."><img src="/images/2012/common/menu/menu4.png" onmouseover="slideCateScroll('on','4');" id="navi4" alt="학습지원"></a></li>
			<li><a href="#" title="유명인사들의 공개강의를 열람할 수 있습니다."><img src="/images/2012/common/menu/menu5.png" onmouseover="slideCateScroll('on','5');" id="navi5" alt="열린강의"></a></li>
			<li><a href="#" title="교육 중 궁금한 것이 있다면 무엇이든 물어보세요. 이용하시기 전에 로그인을 해주세요."><img src="/images/2012/common/menu/menu6.png" onmouseover="slideCateScroll('on','6');" id="navi6" alt="지식팩토리"></a></li>
			<li><a href="#" title="한국콘텐츠진흥원 아카데미를 소개합니다."><img src="/images/2012/common/menu/menu7.png" onmouseover="slideCateScroll('on','7');" id="navi7" alt="아카데미소개"></a></li>
		</ul>
		<div class="optionbox">
			<form method="post" action="">
			<ul class="search">
				<li class="intext"><input type="text" onfocus="this.style.background='#000000';" title="검색어를 입력해주세요."></li>
				<li class="outsearch"><input type="image" src="/images/2012/common/btn_search.png" alt="검색" title="검색을 시작합니다."></li>
			</ul>
			</form>
			<a href="javascript:zoomIn();" title="화면을 확대합니다."><img src="/images/2012/common/btn_big.png" class="plus" alt="화면확대"></a>
			<a href="javascript:zoomOut();" title="화면을 축소합니다."><img src="/images/2012/common/btn_small.png" class="minus" alt="화면축소"></a>
		</div>
	</div>
</div>
<div id="submenuline" class="submenubox">
	<table id="submenu1" class="subnavi smenu1">
		<tr>
			<td><a onmouseover="slideCateScroll('on','1');" title="현재 학습중인 과정을 확인 또는 학습할 수 있습니다.">수강중인 과정</a></td>
			<td><a onmouseover="slideCateScroll('on','1');" title="회원가입 후 현재까지 학습한 이력을 확인할 수 있습니다.">나의교육이력</a></td>
			<td><a onmouseover="slideCateScroll('on','1');" title="신청한 과정을 확인하거나 취소할 수 있습니다.">교육신청 확인/취소</a></td>
			<td><a onmouseover="slideCateScroll('on','1');" title="결제 조회 또는 취소를 할 수 있습니다.">수강료 결제관리</a></td>
			<td><a onmouseover="slideCateScroll('on','1');" title="상담하신 내역을 확인할 수 있습니다.">나의 상담내역</a></td>
			<td><a onmouseover="slideCateScroll('on','1');" title="회원님이 참여하신 설문을 확인할 수 있습니다.">나의 설문</a></td>
			<td><a onmouseover="slideCateScroll('on','1');" title="회원님의 현재의 역량을 진단하여 알맞은 과정을 선택할 수 있습니다.">자기역량 진단</a></td>
			<td><a onmouseover="slideCateScroll('on','1');" title="과정을 수료하신 경우 수료증을 출력할 수 있습니다.">수료증 출력</a></td>
		</tr>
	</table>
	<table id="submenu2" class="subnavi smenu2">
		<tr>
			<td><a href="#" title="방송영상 분야 온라인과정을 안내합니다.">방송영상</a></td>
			<td><a href="#" title="게임개발 분야 온라인과정을 안내합니다.">게임개발</a></td>
			<td><a href="#" title="문화콘텐츠 분야 온라인과정을 안내합니다.">문화콘텐츠</a></td>
		</tr>
	</table>
	<table id="submenu3" class="subnavi smenu3">
		<tr>
			<td><a href="#" title="방송영상 분야 오프라인과정을 안내합니다.">방송영상</a></td>
			<td><a href="#" title="게임개발 분야 오프라인과정을 안내합니다.">게임개발</a></td>
			<td><a href="#" title="기획창작 분야 오프라인과정을 안내합니다.">기획창작</a></td>
			<td><a href="#" title="학습 중 필요한 자료를 제공합니다.">교육자료실</a></td>
			<td><a href="#" title="오프라인 교육의 연간 및 월간 교육일정을 확인할 수 있습니다.">교육일정</a></td>
			<td><a href="#" title="교육실, 교육장비, 휴식공간 등 학습환경을 소개합니다.">교육환경소개</a></td>
		</tr>
	</table>
	<table id="submenu4" class="subnavi smenu4">
		<tr>
			<td><a href="#" title="한국콘텐츠진흥원 아카데미의 새소식을 알려드립니다.">공지사항</a></td>
			<td><a href="#" title="자주 묻는 질문을 모아두었습니다.">FAQ</a></td>
			<td><a href="#" title="궁금한 점이 있다면 물어보세요.">묻고답하기</a></td>
			<td><a href="#" title="온라인 학습 전 정상적인 학습을 위해 학습환경을 설정합니다.">학습환경도우미</a></td>
			<td><a href="#" title="비공개 질문을 원하신다면 이곳 1:1문의를 이용해주세요.">1:1문의</a></td>
			<td><a href="#" title="오프라인 교육 시 사용되는 교재를 소개합니다.">교재소개</a></td>
			<td><a href="#" title="아카데미에서 진행하는 다양한 이벤트를 확인할 수 있습니다.">이벤트</a></td>
			<td><a href="#" title="수강신청이 어려우실 경우 가이드를 통해 따라해보세요.">수강신청방법</a></td>
			<td><a href="#" title="온라인/오프라인 교육 시작 전 궁금한 점이 있다면 안내를 받아보세요.">교육가이드</a></td>
			<td><a href="#" title="과정선택이 어렵다면? 역량진단을 통해 알맞는 과정을 선택해보세요.">자기역량진단?</a></td>
		</tr>
	</table>
	<table id="submenu5" class="subnavi smenu5">
		<tr>
			<td><a href="#" title="방송영상 관련 열린강의를 열람할 수 있습니다.">방송영상</a></td>
			<td><a href="#" title="영화 관련 열린강의를 열람할 수 있습니다.">영화</a></td>
			<td><a href="#" title="다큐멘터리 관련 열린강의를 열람할 수 있습니다.">다큐멘터리</a></td>
			<td><a href="#" title="문화예술 관련 열린강의를 열람할 수 있습니다.">문화예술</a></td>
			<td><a href="#" title="게임 관련 열린강의를 열람할 수 있습니다.">게임</a></td>
			<td><a href="#" title="기획 관련 열린강의를 열람할 수 있습니다.">기획</a></td>
			<td><a href="#" title="기타 관련 열린강의를 열람할 수 있습니다.">기타</a></td>
		</tr>
	</table>
	<table id="submenu6" class="subnavi smenu6">
		<tr>
			<td><a href="#" title="지식팩토리의 전체 지식을 종합해보았습니다.">종합</a></td>
			<td><a href="#" title="방송영상 분야 지식 공간입니다.">방송영상</a></td>
			<td><a href="#" title="방송영상 분야 지식 공간입니다.">게임개발</a></td>
			<td><a href="#" title="방송영상 분야 지식 공간입니다.">문화콘텐츠</a></td>
			<td><a href="#" title="회원님께서 질문하신 내용을 이용할 수 있습니다.">내가 작성한 글</a></td>
		</tr>
	</table>
	<table id="submenu7" class="subnavi smenu7">
		<tr>
			<td><a href="#" title="한국콘텐츠진흥원장님께서 회원님들께 드리는 인사말입니다.">진흥원장 인사말</a></td>
			<td><a href="#" title="아카데미의 설립목적과 비전에 대해 소개합니다.">설립목적 및 비전</a></td>
			<td><a href="#" title="아카데미 교육사업을 안내합니다.">교육사업</a></td>
			<td><a href="#" title="아카데미 교육시설을 안내합니다.">교육시설</a></td>
			<td><a href="#" title="아카데미와 협력관계인 기관을 소개합니다.">교육협력기관</a></td>
			<td><a href="#" title="한국콘텐츠진흥원 아카데미에 오시는길을 안내합니다.">오시는길</a></td>
		</tr>
	</table>
	<div onmouseover="slideCateScroll('off','0');" style="margin-top:7px; height:10px; background:#dddddd;"></div>
</div>
<div id="container">
	<div class="topcontent">
		<ul class="contentA">
			<li class="leftspace">
				<div class="loginput">
					<form method="post" action="">
						<input type="text" class="id" onfocus="this.style.background='#ffffff';" title="아이디를 입력해주세요.">
						<input type="password" class="pass" onfocus="this.style.background='#ffffff';" title="비밀번호를 입력해주세요.">
						<input type="image" src="/images/2012/main/btn_login.png" class="login" alt="로그인" title="회원 로그인을 실행합니다.">
					</form>
				</div>
				<a href="#" title="아이디 또는 비밀번호를 잊었을 때 클릭하세요."><img src="/images/2012/main/btn_idpass.png" class="idpass" alt="아이디/비밀번호 찾기"></a>
				<a href="#" title="신규회원가입을 진행합니다."><img src="/images/2012/main/btn_join.png" class="join" alt="회원가입"></a>
				<dl class="titlenewspost">
					<dt><img src="/images/2012/main/subject_news.png" alt="아카데미 새소식"></dt>
					<dt class="more"><a href="#" title="공지사항으로 이동합니다."><img src="/images/2012/main/btn_more.png" alt="전체보기"></a></dt>
				</dl>
				<table class="newspost" cellpadding="0" cellspacing="0" summary="아카데미 새소식">
					<colgroup><col width="8"><col width="*"></colgroup>
					<tbody>
						<tr>
							<td><img src="/images/2012/main/icon_dot.png" alt="구분점"></td>
							<td><a href="#" title="[08-13] 드라마 한류 발전을 위한 작가세미나">드라마 한류 발전을 위한 작가세미나</a></td>
							<td class="date">08-13</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon_dot.png" alt="구분점"></td>
							<td><a href="#" title="[08-13] 2012년 6월 1차 온라인과정 수료처리 및 종료일 연장...">2012년 6월 1차 온라인과정 수료처리 및 종료일 연장...</a></td>
							<td class="date">08-13</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon_dot.png" alt="구분점"></td>
							<td><a href="#" title="[08-13] 골드클래스 (특강 영상) 18편 추가 서비스 개시">골드클래스 (특강 영상) 18편 추가 서비스 개시</a></td>
							<td class="date">08-13</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon_dot.png" alt="구분점"></td>
							<td><a href="#" title="[08-13] 심화과정 (온·오프라인 학습) 수강신청 안내">심화과정 (온·오프라인 학습) 수강신청 안내</a></td>
							<td class="date">08-13</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon_dot.png" alt="구분점"></td>
							<td><a href="#" title="[08-13] 열린 지식채널 지식팩토리 오픈">열린 지식채널 "지식팩토리" 오픈</a></td>
							<td class="date">08-13</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon_dot.png" alt="구분점"></td>
							<td><a href="#" title="[08-13] 드라마 한류 발전을 위한 작가세미나">드라마 한류 발전을 위한 작가세미나</a></td>
							<td class="date">08-13</td>
						</tr>
					</tbody>
				</table>
			</li>
			<li class="rightspace">
				<!-- 배너 영역 -->
				<script type="text/javascript">
				<!--
				$(function() {
					$("#cyberbanner").jQBanner({nWidth:589,nHeight:245,nCount:5,isActType:"left",nOrderNo:1,isStartAct:"N",isStartDelay:"Y",nDelay:4000,isBtnType:"img"});
				});
				//-->
				</script>
				<div id="cyberbanner">
					<div class="clsBannerScreen">
						<div class="images" style="display:block"><a href="#" title="2012년 해외전문가 연계 프로젝트 교육사업"><img src="/images/2012/main/sample_banner.png" alt="2012년 해외 전문가 연계 프로젝트"></a></div>
						<div class="images"><img src="/images/2012/main/sample_banner.png" alt="샘플배너"></div>
						<div class="images"><img src="/images/2012/main/sample_banner.png" alt="샘플배너"></div>
						<div class="images"><img src="/images/2012/main/sample_banner.png" alt="샘플배너"></div>
						<div class="images"><img src="/images/2012/main/sample_banner.png" alt="샘플배너"></div>
					</div>
					<ul class="clsBannerButton" id="label_3">
						<li><img src="/images/2012/main/banner/number/num01on.png" oversrc="/images/2012/main/banner/number/num01on.png" outsrc="/images/2012/main/banner/number/num01.png" alt="1"></li>
						<li><img src="/images/2012/main/banner/number/num02.png" oversrc="/images/2012/main/banner/number/num02on.png" outsrc="/images/2012/main/banner/number/num02.png" alt="2"></li>
						<li><img src="/images/2012/main/banner/number/num03.png" oversrc="/images/2012/main/banner/number/num03on.png" outsrc="/images/2012/main/banner/number/num03.png" alt="3"></li>
						<li><img src="/images/2012/main/banner/number/num04.png" oversrc="/images/2012/main/banner/number/num04on.png" outsrc="/images/2012/main/banner/number/num04.png" alt="4"></li>
						<li><img src="/images/2012/main/banner/number/num05.png" oversrc="/images/2012/main/banner/number/num05on.png" outsrc="/images/2012/main/banner/number/num05.png" alt="5"></li>
					</ul>
				</div>
				<!-- 배너 영역 끝 -->
			</li>
		</ul>
		<ul class="contentB">
			<li class="online">
				<dl class="titleonline">
					<dd><img src="/images/2012/main/subject_onlinecourse.png" alt="온라인 과정"></dd>
					<dd class="more"><a href="#" title="전체보기"><img src="/images/2012/main/btn_more.png" alt="온라인 과정 전체보기"></a></dd>
				</dl>
				<dl class="onlinetab">
					<dd><button type="button" onmouseover="onlinetab('1')" onclick="document.location='#'" class="over" id="online1" title="온라인 추천과정">온라인 추천과정</button></dd>
					<dd><button type="button" onmouseover="onlinetab('2')" onclick="document.location='#'" class="out" id="online2" title="게임제작">게임제작</button></dd>
					<dd><button type="button" onmouseover="onlinetab('3')" onclick="document.location='#'" class="out" id="online3" title="방송영상">방송영상</button></dd>
					<dd><button type="button" onmouseover="onlinetab('4')" onclick="document.location='#'" class="out" id="online4" title="문화콘텐츠">문화콘텐츠</button></dd>
				</dl>
				<table id="onlinelist1" class="courselist" cellpadding="0" cellspacing="0" summary="온라인 추천과정 리스트">
					<colgroup><col width="60"><col width="*"></colgroup>
					<tbody>
						<tr>
							<td><img src="/images/2012/main/icon/icon_bl.gif" alt="BL심화과정"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 11가지 법칙으로 끝내는 스토리텔링">11가지 법칙으로 끝내는 스토리텔링</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D Data Exporting Techniques">3D Data Exporting Techniques</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D 게임 텍스처 기초">3D 게임 텍스처 기초</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_open.gif" alt="열린특강"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D 공간구조프로그래밍">3D 공간구조프로그래밍</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D 엔진프로그래밍">3D 엔진프로그래밍</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D 영상제작개론">3D 영상제작개론</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
					</tbody>
				</table>
				<table id="onlinelist2" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="게임제작분야 과정 리스트">
					<colgroup><col width="60"><col width="*"></colgroup>
					<tbody>
						<tr>
							<td><img src="/images/2012/main/icon/icon_bl.gif" alt="BL심화과정"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 11가지 법칙으로 끝내는 스토리텔링">11가지 법칙으로 끝내는 스토리텔링</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D Data Exporting Techniques">3D Data Exporting Techniques</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D 게임 텍스처 기초">3D 게임 텍스처 기초</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
					</tbody>
				</table>
				<table id="onlinelist3" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="방송영상분야 과정 리스트">
					<colgroup><col width="60"><col width="*"></colgroup>
					<tbody>
						<tr>
							<td><img src="/images/2012/main/icon/icon_open.gif" alt="열린특강"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D 공간구조프로그래밍">3D 공간구조프로그래밍</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_bl.gif" alt="BL심화과정"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 11가지 법칙으로 끝내는 스토리텔링">11가지 법칙으로 끝내는 스토리텔링</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D Data Exporting Techniques">3D Data Exporting Techniques</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D 게임 텍스처 기초">3D 게임 텍스처 기초</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D 엔진프로그래밍">3D 엔진프로그래밍</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
					</tbody>
				</table>
				<table id="onlinelist4" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="문화콘텐츠분야 과정 리스트">
					<colgroup><col width="60"><col width="*"></colgroup>
					<tbody>
						<tr>
							<td><img src="/images/2012/main/icon/icon_online.gif" alt="온라인"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3D Data Exporting Techniques">3D Data Exporting Techniques</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_bl.gif" alt="BL심화과정"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 11가지 법칙으로 끝내는 스토리텔링">11가지 법칙으로 끝내는 스토리텔링</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
					</tbody>
				</table>
			</li>
			<li class="offline">
				<dl class="titleonline">
					<dd><img src="/images/2012/main/subject_offlinecourse.png" alt="오프라인 과정"></dd>
					<dd class="more"><a href="#" title="전체보기"><img src="/images/2012/main/btn_more.png" alt="오프라인 과정 전체보기"></a></dd>
				</dl>
				<dl class="onlinetab">
					<dd><button type="button" onmouseover="offlinetab('1')" onclick="document.location='#'" class="over" id="offline1" title="3D 입체콘텐츠">3D 입체콘텐츠</button></dd>
					<dd><button type="button" onmouseover="offlinetab('2')" onclick="document.location='#'" class="out" id="offline2" title="산업계 직무재교육">산업계 직무재교육</button></dd>
					<dd><button type="button" onmouseover="offlinetab('3')" onclick="document.location='#'" class="out" id="offline3" title="차세대 인재양성">차세대 인재양성</button></dd>
					<dd><button type="button" onmouseover="offlinetab('4')" onclick="document.location='#'" class="out" id="offline4" title="수탁교육">수탁교육</button></dd>
				</dl>
				<table id="offlinelist1" class="courselist" cellpadding="0" cellspacing="0" summary="온라인과정 리스트">
					<colgroup><col width="50"><col width="*"></colgroup>
					<tbody>
						<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Smoke를 활용한 3D입체 제작 과정">Smoke를 활용한 3D입체 제작 과정</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Avid MC6를 활용한 3D입체">Avid MC6를 활용한 3D입체</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_close.gif" alt="마감"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Avid MC6를 활용한 3D입체">1Avid MC6를 활용한 3D입체</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 디지털 영상편집 과정">디지털 영상편집 과정</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_close.gif" alt="마감"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Smoke를 활용한 3D입체 제작 과정">Smoke를 활용한 3D입체 제작 과정</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3ALITY리그 테크니션 과정">3ALITY리그 테크니션 과정</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
					</tbody>
				</table>
				<table id="offlinelist2" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="온라인과정 리스트">
					<colgroup><col width="50"><col width="*"></colgroup>
					<tbody>
						<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Avid MC6를 활용한 3D입체">Avid MC6를 활용한 3D입체</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_close.gif" alt="마감"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Avid MC6를 활용한 3D입체">1Avid MC6를 활용한 3D입체</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3ALITY리그 테크니션 과정">3ALITY리그 테크니션 과정</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
					</tbody>
				</table>
				<table id="offlinelist3" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="온라인과정 리스트">
					<colgroup><col width="50"><col width="*"></colgroup>
					<tbody>
						<tr>
							<td><img src="/images/2012/main/icon/icon_close.gif" alt="마감"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Avid MC6를 활용한 3D입체">1Avid MC6를 활용한 3D입체</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 디지털 영상편집 과정">디지털 영상편집 과정</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
					</tbody>
				</table>
				<table id="offlinelist4" class="courselist" style="display:none;" cellpadding="0" cellspacing="0" summary="온라인과정 리스트">
					<colgroup><col width="50"><col width="*"></colgroup>
					<tbody>
						<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Avid MC6를 활용한 3D입체">Avid MC6를 활용한 3D입체</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_close.gif" alt="마감"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Avid MC6를 활용한 3D입체">1Avid MC6를 활용한 3D입체</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_close.gif" alt="마감"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] Smoke를 활용한 3D입체 제작 과정">Smoke를 활용한 3D입체 제작 과정</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
						<tr>
							<td><img src="/images/2012/main/icon/icon_recruitment.gif" alt="모집중"></td>
							<td><a href="#" title="[2012.09.01 ~ 2012.09.30] 3ALITY리그 테크니션 과정">3ALITY리그 테크니션 과정</a></td>
							<td class="date">2012.09.01 ~ 2012.09.30</td>
						</tr>
					</tbody>
				</table>
			</li>
		</ul>
		<div style="clear:left;"></div>
	</div>
	<div class="bottomcontent">
		<div class="contentbox">
			<ul class="content">
				<li class="leftspace">
					<div class="detail">
						<a href="#" title="성공을 말하다! <열린강의> 바로가기"><img src="/images/2012/main/subject_opencast.png" alt="성공을 말하다! 열린강의"></a>
						<div class="opencastment">방송, 영상, 영화, 다큐멘터리, 문화, 예술, 게임, 유통 등 다양한 분야의 유명인사들이 한자리에 모여 말하다.</div>
						<div class="banner"><a href="#" title="온라인과정 수업 전 교육환경 설정하러 바로가기"><img src="/images/2012/main/banner_config.gif" alt="온라인과정 수업 전 교육환경 설정하기"></a></div>
						<div class="talent"><img src="/images/2012/main/subject_talent.png" alt="차세대 인재를 위한 전문화된 교육시설"></div>
						<script type="text/javascript">
						<!--
						$(function() {
							$("#learningroom").jQBanner({	nWidth:370, nHeight:76, nCount:5,	isActType:"up", nOrderNo:1, nDelay:5000 });
						});
						//-->
						</script>
						<div id="learningroom">
							<div class="clsBannerScreen">
								<div class="images" style="display:block"><img src="/images/2012/main/sample_learningroom.png" alt="샘플교육실"></div>
								<div class="images"><img src="/images/2012/main/sample_learningroom.png" alt="샘플교육실"></div>
								<div class="images"><img src="/images/2012/main/sample_learningroom.png" alt="샘플교육실"></div>
								<div class="images"><img src="/images/2012/main/sample_learningroom.png" alt="샘플교육실"></div>
								<div class="images"><img src="/images/2012/main/sample_learningroom.png" alt="샘플교육실"></div>
							</div>
						</div>
					</div>
				</li>
				<li class="rightspace">
					<dl class="steprable">
						<dd class="msg"><img src="/images/2012/main/step_msg.png" alt="한국콘텐츠아카데미의 처음오셨나요? 전문교육을 무료로 받아보세요."></dd>
						<dd class="step1"><a href="#" title="회원가입 바로가기"><img src="/images/2012/main/step1.png" alt="1단계 회원가입"></a></dd>
						<dd class="step2"><a href="#" title="로그인 바로가기"><img src="/images/2012/main/step2.png" alt="2단계 로그인"></a></dd>
						<dd class="step3"><a href="#" title="과정선택 바로가기"><img src="/images/2012/main/step3.png" alt="3단계 과정선택"></a></dd>
						<dd class="step4"><a href="#" title="수강신청 바로가기"><img src="/images/2012/main/step4.png" alt="4단계 수강신청"></a></dd>
					</dl>
					<dl class="support">
						<dd><a href="#" title="원격지원 서비스로 이동합니다."><img src="/images/2012/main/banner_support.gif" alt="학습 중 PC문제가 발생하셨나요? 원격지원서비스 이용하기"></a></dd>
						<dd><a href="#" title="묻고답하기 게시판으로 이동합니다."><img src="/images/2012/main/banner_sms.gif" alt="묻고답하기 답변 알림 SMS : 질문 후 답변 안내 문자가 발송됩니다."></a></dd>
					</dl>
					<dl class="business">
						<dd class="learning">
							<table class="businessfield" cellpadding="0" cellspacing="0" summary="한국콘텐츠아카데미 교육사업 분야 안내">
								<thead>
									<tr>
										<th colspan="2"><img src="/images/2012/main/subject_business.png" alt="한국콘텐츠아카데미 교육사업"></th>
									</tr>
								</thead>
								<tbody>
									<tr>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="#" title="차세대 인재 양성교육사업 안내로 이동합니다.">차세대 인재 양성교육</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="#" title="3D입체 콘텐츠 전문인력 양성사업 안내로 이동합니다.">3D입체 콘텐츠 전문인력 양성</a></td>
									</tr>
									<tr>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="#" title="산업계 직무 재교육사업 안내로 이동합니다.">산업계 직무 재교육</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="#" title="창의인재 양성사업 안내로 이동합니다.">창의인재 양성사업</a></td>
									</tr>
									<tr>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="#" title="온라인 교육사업 안내로 이동합니다.">온라인 교육</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="#" title="드라마 프로듀서 스쿨사업 안내로 이동합니다.">드라마 프로듀서 스쿨</a></td>
									</tr>
									<tr>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="#" title="국제 인증과정사업 안내로 이동합니다.">국제 인증과정</a></td>
										<td><img src="/images/2012/main/icon_dot.png" alt="구분점"> <a href="#" title="게임 국가기술 자격 검정사업 안내로 이동합니다.">게임 국가기술 자격 검정</a></td>
									</tr>
								</tbody>
							</table>
						</dd>
						<dd class="another">
							<a href="#" title="드림멘토 창의인재 동반사업 바로가기"><img src="/images/2012/main/sample_banner1.gif" alt="드림멘토 창의인재 동반사업"></a><br>
							<a href="#" title="교육콘텐츠 기획 아이디어 모집 바로가기"><img src="/images/2012/main/sample_banner2.gif" alt="드림멘토 창의인재 동반사업"></a>
						</dd>
					</dl>
				</li>
			</ul>
			<div class="clearscript"></div>
		</div>
		<div class="smarticon">
			<table class="icon" cellspacing="0" cellpadding="0" summary="자주이용하는 기능들을 모았습니다.">
				<tbody>
					<tr>
						<td class="icon1"><a href="#" title="온라인/오프라인 교육 시작 전 궁금한 점이 있다면 안내를 받아보세요."><img src="/images/2012/common/smarticon/icon_guide.png" alt="교육가이드"></a></td>
						<td class="icon2"><a href="#" title="선택하신 과정에 결제가 필요하신 경우 결제를 진행할 수 있습니다."><img src="/images/2012/common/smarticon/icon_payment.png" alt="결제하기"></a></td>
						<td class="icon3"><a href="#" title="과정선택이 어렵다면? 역량진단을 통해 알맞는 과정을 선택해보세요."><img src="/images/2012/common/smarticon/icon_level.png" alt="자기역량진단"></a></td>
						<td class="icon4"><a href="#" title="온라인 학습 전 정상적인 학습을 위해 학습환경을 설정합니다."><img src="/images/2012/common/smarticon/icon_config.png" alt="학습환경도우미"></a></td>
						<td class="icon5"><a href="#" title="비공개 질문을 원하신다면 이곳 1:1문의를 이용해주세요."><img src="/images/2012/common/smarticon/icon_1n1.png" alt="1:1문의"></a></td>
						<td class="icon6"><a href="#" title="과정을 수료하신 경우 수료증을 출력할 수 있습니다."><img src="/images/2012/common/smarticon/icon_cert.png" alt="수료증 출력"></a></td>
						<td class="icon7"><a href="#" title="오프라인 교육 시 사용되는 교재를 소개합니다."><img src="/images/2012/common/smarticon/icon_book.png" alt="교재안내"></a></td>
						<td class="icon8"><a href="#" title="자주 묻는 질문을 모아두었습니다."><img src="/images/2012/common/smarticon/icon_faq.png" alt="FAQ"></a></td>
						<td class="icon9"><a href="#" title="궁금한 점이 있다면 물어보세요."><img src="/images/2012/common/smarticon/icon_qna.png" alt="묻고답하기"></a></td>
						<td class="icon10"><a href="#" title="한국콘텐츠진흥원 아카데미에 오시는길을 안내합니다."><img src="/images/2012/common/smarticon/icon_location.png" alt="찾아오시는길"></a></td>
					</tr>
				</tbody>
			</table>
		</div>
	</div>
</div>
<div id="footer">
	<div class="supportbox">
		<div class="support">
			<div class="supportcontent">
				<ul class="supportmenu">
					<li class="startline"><a href="#" title="개인정보처리방침으로 이동합니다."><img src="/images/2012/common/btn_member_terms.png" alt="개인정보처리방침"></a></li>
					<li><a href="#" title="이용약관으로 이동합니다."><img src="/images/2012/common/btn_use_terms.png" alt="이용약관"></a></li>
					<li><a href="#" title="이메일무단수집거부로 이동합니다."><img src="/images/2012/common/btn_email_denial.png" alt="이메일부단수집거부"></a></li>
					<li><a href="#" title="사이트맵으로 이동합니다."><img src="/images/2012/common/btn_sitemap.png" alt="사이트맵"></a></li>
				</ul>
				<ul class="certification">
					<li><a href="#" title="클릭하시면 이니시스의 유효성을 확인하실 수 있습니다."><img src="/images/2012/common/icon_inicis.png" alt="이니시스 결제 시스템"></a></li>
					<li><a href="#" title="콘텐츠제공서비스 품질인증서를 확인합니다."><img src="/images/2012/common/icon_goodcontent.png" alt="콘텐츠제공서비스 품질인증"></a></li>
					<li><a href="#" title="개인정보보호우수 인증서를 확인합니다."><img src="/images/2012/common/icon_eprivacy.png" alt="개인정보보호우수사이트 인증"></a></li>
					<li><a href="#" title="개인정보보호 안전 인증서를 확인합니다."><img src="/images/2012/common/icon_isafe.png" alt="개인정보보호안전 인증"></a></li>
				</ul>
			</div>
		</div>
		<div class="about">
			<ul class="information">
				<li><img src="/images/2012/common/bottom_logo.png" alt="한국콘텐츠진흥원 아카데미"></li>
				<li class="copy">
					<dl class="copyright">
						<dd class="address">서울특별시 양천구 목동동로 203-1 방송회관 8층<br>사업자등록번호 : 117-82-04412      통신판매업신고번호 : 양천 00471</dd>
						<dd class="corp">한국콘텐츠진흥원(목동) 원장 홍상표</dd>
						<dd class="maker">COPYRIGHTⓒ 2012 KOCCA ALL RIGHTS RESERVED.</dd>
					</dl>
				</li>
				<li class="servicecenter">
					<img src="/images/2012/common/bottom_servicecenter.png" alt="온라인 교육문의 02-2161-0077번, 오프라인 교육문의 02-2161-0072">
					<select class="familysite">
						<option>문화체육관광부</option>
						<option>한국콘텐츠진흥원</option>
						<option>콘텐츠도서관</option>
						<option>글로벌게임허브센터</option>
						<option>문화콘텐츠닷컴</option>
						<option>콘텐츠이용보호센터</option>
						<option>저작권위탁관리시스템</option>
						<option>콘텐츠식별체계(UCI)</option>
						<option>게임국가자격기술검정</option>
						<option>종합인력정보시스템</option>
						<option>음악아카이브</option>
						<option>보조사업비카드관리시스템</option>
					</select>
				</li>
			</ul>
		</div>
	</div>
</div>
</body>
</html>