<%@ page import = "com.credu.homepage.LoginBean" %>
<%@ page import = "com.credu.propose.MainSubjSearchBean" %>
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

    String titleStr = box.getString("title_str");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="ko" xml:lang="ko">
<head>
<meta property="og:title" content="<%= box.getString("og_title") %>">
<meta property="og:url" content="<%= box.getString("og_url") %>">
<meta property="og:description" content="<%= box.getString("og_description") %>">
<meta property="og:image" content="<%= box.getString("og_image") %>">

<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta name="subject" content="한국콘텐츠아카데미" /> 
<meta name="title" content="한국콘텐츠아카데미" />
<meta name="description" content="한국콘텐츠아카데미" />
<meta name="keywords" content="<%= titleStr %>, kocca, 한국콘텐츠아카데미" />
<meta name="robots" content="index,follow" />

<title><%= titleStr %> | 한국콘텐츠아카데미 </title>
<link rel="stylesheet" href="/css/jqueryui/1.10.0/themes/base/jquery-ui.css" />
<script src="/js/jquery/1.8.3/jquery.js"></script>
<script src="/js/jqueryui/1.10.0/jquery-ui.js"></script>

<script type="text/javascript" src="/js/2013/caption.js"></script>
<script type="text/javascript" src="/js/2013/jquery.easing.1.3.js"></script>
<script type="text/javascript" src="/js/2013/jquery.selectbox-0.6.1.js"></script>
<script type="text/javascript" src="/js/2013/jquery.cycle2.min.js"></script>
<script type="text/javascript" src="/js/2013/common.js"></script>
<!--<script type="text/javascript" src="/js/2013/temp.js"></script>-->
<script type="text/javascript" src="/js/2013/lib.js"></script>

<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" language="JavaScript" src="/script/mainscript.js"></script>
<style type="text/css" media="screen">@import "/css/2013/common.css";</style>
<style type="text/css" media="screen">@import "/css/2013/layout.css";</style>
<style type="text/css" media="screen">@import "/css/2013/sub.css";</style>
<script type="text/javascript">
/* 패스워드 엔터키 체크 */
function password_enter(e) {
  if (e.keyCode =='13'){  login_main();  }
}
</script>
</head>
<body>
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

<div id="wrap">
    <div id="shortcutmenu">
        <a href="#quickEduMenu">주메뉴로 바로가기</a>
        <a href="#contents">본문으로 바로가기</a>
    </div>
    <!-- //#shortcutmenu -->

    <div id="header">
        <h1><a href="/servlet/controller.homepage.MainServlet"><img src="/images/2013/common/logo.png" alt="kocca 한국콘텐츠아카데미" /></a></h1>
        <ul class="util">
            <% if(topWarp_userid.equals("")) {%>     <%--로그인이 안되었는 경우--%>
            <li class="login">
            <form method="post" name="login" action="">
                <input type="hidden" name="p_process"   value="" />
                <input type="hidden" name="p_frmURL" value="<%= box.getStringDefault("p_frmURL", "") %>" />
                <label for="p_userid" style="display:none;">로그인 아이디</label><input type="text" name="p_userid" id="p_userid" class="id" />
                <label for="p_pwd" style="display:none;">로그인 비밀번호</label><input type="password" name="p_pwd" id="p_pwd" class="pw" onkeypress="password_enter(event)" />
                <a href="javascript:void(0);" onclick="return login_main();" title="로그인"><img src="/images/2013/btn/btn_login.gif" alt="로그인" /></a>
            </form>
            </li>
            <li class="signup"><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
            <li class="idpw"><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">ID/PW찾기</a></li>
            <% } else { %>
            <li class="name"><%=topWarp_usernm%> 님</li>
            <li class="myinfo">
                <label for="myInfoMng"><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내정보 관리</a></label>
                <%= box.getSession("tem_grcode").equals("N000001") ? LoginBean.getAuthSelect(topWarp_userid, "p_topAuth", topWarp_gadmin, "onChange = 'javascript:topAuthChange();'") : ""%>
                <% if ((!(topWarp_gadmin.equals("ZZ") || topWarp_gadmin.equals("")))) {  %>
                <a href="javascript:topAdminOpenPage()" tabindex="7" title="LMS로 이동합니다">LMS이동</a>
            <% } %>
            </li>
            <li class="logout"><a href="javascript:mainmenu('998');"><img src="/images/2013/btn/btn_logout.gif" alt="로그아웃" /></a></li>
            <% } %>
            <li class="zoom">
                크기조절
                <span>
                    <a href="javascript:"><img src="/images/2013/btn/btn_plus.gif" alt="화면 확대" /></a>
                    <a href="javascript:"><img src="/images/2013/btn/btn_minus.gif" alt="화면 축소" /></a>
                </span>
            </li>
        </ul>
    </div>
    <!-- //#header -->

    <hr />

    <div id="sideMenu">
        <div class="search">
            <fieldset>
                <legend>빠른 검색</legend>
                 <label for="topWarp_searchText" style="display:none;">검색어</label><input type="text" name="topWarp_searchText" onkeypress="searchtotal_enter(event);" id="topWarp_searchText" title="검색어" class="keyword" />
                <a href="javascript:totalSubjSearch1();"><img src="/images/2013/btn/btn_src.gif" alt="검색" /></a>
                <!-- <input type="image" src="/images/2013/btn/btn_src.gif" alt="검색" class="btn" onclick="javascript:totalSubjSearch1();"/> //-->
            </fieldset>
        </div>
        <div class="eduSup">
            <h2 style="margin-bottom:5px;"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision"><img src="/images/2013/common/sidemenu_tit2.gif" alt="아카데미 소개" /></a></h2>
            <h2><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome"><img src="/images/2013/common/sidemenu_tit.gif" alt="학습지원" /></a></h2>
            <ul>
                <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService"><img src="/images/2013/common/sidemenu_1.gif" alt="원격지원서비스" /></a></li>
                <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp"><img src="/images/2013/common/sidemenu_2.gif" alt="학습환경설정" /></a></li>
                <!-- 2014-06-25 수정. 하단에 관련 사이트 배너를 추가하기 위해 현재 운영중인 메뉴를 제거함 //-->
                <!-- <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp"><img src="/images/2013/common/sidemenu_1-2.gif" alt="모바일앱" /></a></li> //-->
                <!-- <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList"><img src="/images/2013/common/sidemenu_3.gif" alt="FAQ" /></a></li> //-->
                <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList"><img src="/images/2013/common/sidemenu_4.gif" alt="교육문의" /></a></li>
                <!-- <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List"><img src="/images/2013/common/sidemenu_5.gif" alt="공지사항" /></a></li> //-->
                <!-- <li><a href="/servlet/controller.course.EduScheduleHomePageServlet?p_process=offSchlYearPlanList"><img src="/images/2013/common/sidemenu_6.gif" alt="연간일정" /></a></li> //-->
                <!-- <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome"><img src="/images/2013/common/sidemenu_7.gif" alt="이용안내" /></a></li> //-->
            </ul>
        </div>
        <div class="sns">
            <ul>
                <li><a href="https://www.facebook.com/edukocca" target="_blank"><img src="/images/2013/main/btn_sns_fb.gif" alt="페이스북"/></a></li>
                <li><a href="https://twitter.com/edukocca" target="_blank"><img src="/images/2013/main/btn_sns_tw.gif" alt="트위터"/></a></li>
                <li><a href="http://blog.naver.com/kocca02" target="_blank"><img src="/images/2013/main/btn_sns_nv.gif" alt="블로그"/></a></li>
                <li><a href="http://www.youtube.com/KoreanContent" target="_blank"><img src="/images/2013/main/btn_sns_yt.gif" alt="유튜브"/></a></li>
            </ul>
        </div>

        <%@ include file="/learn/user/2013/portal/include/popular_search.html" %>
        <%-- @ include file="/upload/homepage/popularsearch/popular_search.html" --%>

        <div style="padding-top:20px; padding-left:3px;">
            <ul>
                <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp" target="_self" title="모바일 서비스 안내"><img src="/images/common/right_banner_mobile.jpg" alt="모바일 서비스 안내" /></a></li>
            </ul>
        </div>

        <div style="padding-top:20px; padding-left:3px;">
            <ul>
                <li><a href="http://www.ckl.or.kr" target="_blank" title="CONTENTS KOREA LAB 홈페이지로 이동"><img src="/images/common/right_banner_ckl_20140625.jpg" alt="상상력이 창작으로, 창작이 창업으로. 콘텐츠코리아랩" /></a></li>
            </ul>
        </div>

    </div>
    <!-- //#sidemenu -->

    <hr />

    <div id="quickEduMenu">
        <ul class="tab">
            <li><a href="#quickEdu1"><img src="/images/2013/common/quick_tab_1.gif" alt="온라인 교육" /></a></li>
            <li><a href="#quickEdu2"><img src="/images/2013/common/quick_tab_2.gif" alt="현장 교육" /></a></li>
            <li><a href="#quickEdu3"><img src="/images/2013/common/quick_tab_3.gif" alt="열린 강좌" /></a></li>
        </ul>
        <p class="viewAll"><a href="#totalmenu"><img src="/images/2013/btn/btn_all.gif" alt="전체보기" /></a></p>

        <!-- 온라인교육 -->
        <div id="quickEdu1" class="cont">
            <div class="pointer"></div>
            <h3>
<!--                <a href="#">-->
                    <span class="img"><img src="/images/2013/common/quick1_tit.png" alt="온라인 교육" /></span>
                    <span class="txt">게임, 방송, 만화애니메이션<br />캐릭터, 음악공연 등 <strong>콘텐츠분야<br /> 무료 온라인 교육</strong>을 제공합니다.</span>
<!--                </a>-->
            </h3>
            <ul>
                <li>
                    <a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">
                        <span class="img"><img src="/images/2013/common/quick1_tit2.png" alt="나의 강의실" /></span>
                        <span class="txt">현재 학습중인 과정, 신청중인<br />과정 확인 및 학습완료 과정의<br /> 수료증을 발급 받을 수 있습니다.</span>
                    </a>
                </li>
                <li>
                    <a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">
                        <span class="img"><img src="/images/2013/common/quick1_tit3.png" alt="과정안내" /></span>
                        <span class="txt">역량별, 직업별, 분야별, 과정별<br /> 원하는 과정을 찾을 수 있으며,<br />과정 정보를 볼 수 있습니다.</span>
                    </a>
                </li>
                <li>
                    <a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage">
                        <span class="img"><img src="/images/2013/common/quick1_tit4.png" alt="커뮤니티" /></span>
                        <span class="txt">튜터와 학습자,<br />학습자와 학습자간의 지식을<br /> 공유하고 토론하는 공간입니다.</span>
                    </a>
                </li>
            </ul>
<!--            <p class="link"><span><a href="#">매달 3개의 과정을 수강 할 수 있습니다. 나에게 필요한 <strong>교육과정 알아보기</strong></a></span></p>-->
            <p class="closeBtn"><a href="#"><img src="/images/2013/btn/quick_close.gif" alt="창닫기" /></a></p>
        </div>
        <!-- //온라인교육 -->

        <!-- 현장교육 -->
        <div id="quickEdu2" class="cont">
            <div class="pointer"></div>
            <h3>
<!--                <a href="#">-->
                    <span class="img"><img src="/images/2013/common/quick2_tit.png" alt="현장 교육" /></span>
                    <span class="txt">콘텐츠분야 <strong>예비인력</strong>과<br /> 콘텐츠산업 <strong>현업인</strong>을 위한 <br /><strong>전문 현장교육</strong>을 제공합니다.  </span>
<!--                </a>-->
            </h3>
            <ul>
                <li>
                    <a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">
                        <span class="img"><img src="/images/2013/common/quick2_tit2.png" alt="나의 강의실" /></span>
                        <span class="txt">현재  신청중인 과정, 교육중인<br />과정 확인 및 교육완료 과정의<br /> 수료증을 발급 받을 수 있습니다.</span>
                    </a>
                </li>
                <li>
                    <!-- 2014-04-23 기존 3D과정이 미래창조부로 이관 완료됨으로 인하여 주석처리
                    <a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=004">
                    //-->
                    <a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001">
                        <span class="img"><img src="/images/2013/common/quick2_tit3.png" alt="과정신청" /></span>
                        <span class="txt">예비인력교육, 현업인 직무교육 등<br /> 모집 중인 과정을 확인하고, 원하는<br />과정을 신청 할 수 있습니다.</span>
                    </a>
                </li>
                <li>
                    <a href="/servlet/controller.course.EduScheduleHomePageServlet?p_process=offSchlYearPlanList">
                        <span class="img"><img src="/images/2013/common/quick2_tit4.png" alt="일정안내" /></span>
                        <span class="txt">연,월 단위로 현장교육 모집일정 <br />안내를 확인 하실 수 있습니다.</span>
                    </a>
                </li>
            </ul>
<!--            <p class="link"><span><a href="#">9월 현장교육 과정 안내를 확인 할 수 있습니다. 나에게 필요한 <strong>교육과정 알아보기</strong></a></span></p>-->
            <p class="closeBtn"><a href="#"><img src="/images/2013/btn/quick_close.gif" alt="창닫기" /></a></p>
        </div>
        <!-- //현장교육 -->

        <!-- 2014-06-12 new 열린강좌(콘텐츠온) start //-->
		<div id="quickEdu3" class="cont">
			<div class="pointer"></div>
			<div class="bgTopRight"></div>
			<h3>
				<span class="img"><img src="/images/2013/common/quick3_tit.png" alt="열린 강좌" /></span>
				<span class="txt">방송영상, 문화예술, 게임 등<br /> <strong>자유롭게 볼 수 있는 콘텐츠 </strong>를<br /> 제공 하고 있습니다.</span>
			</h3>
			<div>
				<span class="img" style="float:left; margin-top:13px;"><img src="/images/common/line_v.png" alt="" /></span>
				<div>
					<h4>
						<span class="img" style="float:left; padding:30px 10px 10px 20px;"><img src="/images/common/list01.png" alt="분류별" /></span>
					</h4>
					<div style="float:left; margin-top:60px; padding-left:7px;">
						<a href="javascript:fnOpenClassList('ALL');" style="margin:15px; color:#865d21">전체</a><font style="color:#dadada;">|</font>
						<a href="javascript:fnOpenClassList('GC01');" style="margin:15px; color:#865d21">방송영상</a><font style="color:#dadada;">|</font>
						<a href="javascript:fnOpenClassList('GC02');" style="margin:15px; color:#865d21">게임</a><font style="color:#dadada;">|</font>
						<a href="javascript:fnOpenClassList('GC03');" style="margin:15px; color:#865d21">만화애니캐릭터</a><font style="color:#dadada;">|</font>
						<a href="javascript:fnOpenClassList('GC04');" style="margin:15px; color:#865d21">음악/공연</a><font style="color:#dadada;">|</font>
						<a href="javascript:fnOpenClassList('GC05');" style="margin:15px; color:#865d21">기타</a>
					</div>
				</div>
				<span class="img" style="float:left; width:550px; height:10px;"><img src="/images/common/line_h.png" alt="" /></span>
				<div>
					<h4>
						<span class="img" style="float:left; padding:10px 10px 10px 20px;"><img src="/images/common/list02.png" alt="테마별" /></span>
					</h4>
					<div style="float:left; margin-top:38px; padding-left:7px;">
						<a href="javascript:fnOpenClassList('GC06');" style="margin:15px; color:#521e87">콘텐츠인사이트</a><font style="color:#dadada;">|</font>
						<a href="javascript:fnOpenClassList('GC07');" style="margin:15px; color:#521e87">마스터클래스</a><font style="color:#dadada;">|</font>
						<a href="javascript:fnOpenClassList('GC08');" style="margin:15px; color:#521e87">휴넷열린강좌</a><font style="color:#dadada;">|</font>
					</div>
				</div>
					
			</div>
            <!-- <p class="link"><span><a href="#">수강신청 없이 언제든지 학습이 가능한 열린 강좌입니다. <strong>신규 열린강좌 둘러보기</strong></a></span></p> //-->
			<p class="closeBtn"><a href="#"><img src="/images/2013/btn/quick_close.gif" alt="창닫기" /></a></p>
		</div>

        <!-- 2014-06-12 new 열린강좌(콘텐츠온) end //-->

        <!-- 열린강좌 2014-06-10 열린강좌를 분류별로 조회하는 것으로 변경되어 기존 내용을 주석처리 함 //-->
        <!--
        <div id="quickEdu3" class="cont">
            <div class="pointer"></div>
            <h3>
            //-->

                <!-- <a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage">-->
                <!--
                <span class="img"><img src="/images/2013/common/quick3_tit.png" alt="열린 강좌" /></span>
                <span class="txt">방송영상, 문화예술, 게임 등<br /><strong>자유롭게 볼 수 있는 콘텐츠</strong>를<br />제공하고 있습니다.</span>
                //-->
                <!-- </a>-->

            <!--
            </h3>
            <ul>
                <li class="top">
                    <a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G01">
                        <span class="img"><img src="/images/2013/common/quick3_tit2.png" alt="방송영상" /></span>
                        <span class="txt">1박2일, 슈퍼스타,<br />대장금, 허준 등</span>
                    </a>
                </li>
                <li class="top">
                    <a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G02">
                        <span class="img"><img src="/images/2013/common/quick3_tit3.png" alt="영화" /></span>
                        <span class="txt">윤제균, 이창동감독<br />이미도 번역가 등</span>
                    </a>
                </li>
                <li class="top">
                    <a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G03">
                        <span class="img"><img src="/images/2013/common/quick3_tit4.png" alt="다큐멘터" /></span>
                        <span class="txt">워낭소리, 아마존의<br />눈물 등</span>
                    </a>
                </li>
                <li>
                    <a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G04">
                        <span class="img"><img src="/images/2013/common/quick3_tit5.png" alt="문화예술" /></span>
                        <span class="txt">윤제균, 이창동감독<br />이미도 번역가 등</span>
                    </a>
                </li>
                <li>
                    <a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G06">
                        <span class="img"><img src="/images/2013/common/quick3_tit6.png" alt="게임" /></span>
                        <span class="txt">등급심의 세미나,<br />해외진출전략 등 </span>
                    </a>
                </li>
                <li>
                    <a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_process=mainPage&p_genre=G05">
                        <span class="img"><img src="/images/2013/common/quick3_tit7.png" alt="기타" /></span>
                        <span class="txt">콘텐츠 창업성공기,<br />콘텐츠와 저작권 등</span>
                    </a>
                </li>
            </ul>
            //-->
            <!-- <p class="link"><span><a href="#">수강신청 없이 언제든지 학습이 가능한 열린 강좌입니다. <strong>신규 열린강좌 둘러보기</strong></a></span></p> //-->
            <!--
            <p class="closeBtn"><a href="#"><img src="/images/2013/btn/quick_close.gif" alt="창닫기" /></a></p>
        </div>
        //-->

        <!-- 열린강좌 끝//-->

        <div id="totalmenu" class="totalmenu">
            <ul class="group">
                <li class="d1"><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage"><strong>내 강의실</strong></a>
                    <ul>
                       <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">- 학습중인 과정</a></li>
                       <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">- 다음 과정</a></li>
                       <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">- 찜한 과정</a></li>
                       <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">- 지난 과정</a></li>
                    </ul>
                </li>
                <li class="d1"><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList"><strong>과정안내</strong></a>
                    <ul>
                       <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList">- 과정별 소개</a></li>
                       <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikup">- 직업별 분류</a></li>
                       <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectListJikmu">- 직무별 분류</a></li>
                       <li><a href="/servlet/controller.propose.ProposeCourseServlet?p_process=EduSystem">- 교육체계도</a></li>
                    </ul>
                </li>
                <li class="d1"><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage"><strong>커뮤니티</strong></a></li>
            </ul>
            <ul class="group">
                <li class="d1"><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage"><strong>내 강의실</strong></a>
                    <ul>
                       <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">- 신청중인 과정</a></li>
                       <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">- 종료된 과정</a></li>
                    </ul>
                </li>
                <li class="d1"><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001"><strong>과정안내</strong></a>
                    <ul>
                        <!-- 2014-04-23 기존 3D과정이 미래창조부로 이관 완료됨으로 인하여 주석처리
                        <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=004">- 3D 입체콘텐츠</a></li>
                        //-->
                        <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001">- 현업인직무재교육</a></li>
                        <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=002">- 차세대 인재양성교육</a></li>
                    </ul>
                </li>
                <li class="d1"><a href="/servlet/controller.course.EduScheduleHomePageServlet?p_process=offSchlYearPlanList"><strong>일정안내</strong></a></li>
            </ul>
            <ul class="group">
                <li class="d1"><a href="/servlet/controller.infomation.GoldClassHomePageServlet"><strong>분류별</strong></a>
                    <ul>
                       <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet">- 전체</a></li>
                       <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_lecture_cls=GC01">- 방송영상</a></li>
                       <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_lecture_cls=GC02">- 게임</a></li>
                       <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_lecture_cls=GC03">- 만화애니캐릭터</a></li>
                       <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_lecture_cls=GC04">- 음악/공연</a></li>
                       <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_lecture_cls=GC05">- 기타</a></li>
                    </ul>
                </li>

                <li class="d1"><a href="/servlet/controller.infomation.GoldClassHomePageServlet"><strong>테마별</strong></a>
                    <ul>
                       <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_lecture_cls=GC06">- 콘텐츠인사이트</a></li>
                       <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_lecture_cls=GC07">- 마스터클래스</a></li>
                       <li><a href="/servlet/controller.infomation.GoldClassHomePageServlet?p_lecture_cls=GC08">- 휴넷열린강좌</a></li>
                    </ul>
                </li>
            </ul>
            <p class="closeBtn"><a href="#"><img src="/images/2013/btn/quick_close.gif" alt="창닫기" /></a></p>
        </div>
    </div>
    <!-- //#quickEduMenu -->

    <hr />
    <div id="container">