<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("title_str", "사이트맵-이용안내, 학습지원");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<style>

.siteamp-header {padding:12px 0 20px 120px; color:#444; line-height:18px; background:url(/images/2013/support/icon-header.png) no-repeat 50px 0;}
.sitemap-body {padding:0 10px;}
.sitemap-body .row {margin-top:60px; zoom:1;}
.sitemap-body .row:after {content:""; display:block; clear:both;}
.sitemap-body .col {float:left; width:170px; margin-left:40px;}
.sitemap-body h3 {padding:9px 0; text-align:center; color:#fff; font-size:14px; font-family:'나눔고딕','nanumgothic','맑은 고딕','malgun gothic',sans-serif; font-weight:600; background:#ce0000;}
.sitemap-body li {line-height:16px; border-bottom:1px solid #dedede; background:url(/images/2013/support/icon1.png) no-repeat 10px 12px;}
.sitemap-body li a {display:block; padding:8px 0 8px 23px; color:#444;}
.sitemap-body li ul {margin-bottom:10px;}
.sitemap-body li li {border:0; background:url(/images/2013/support/icon2.png) no-repeat 30px 10px;}
.sitemap-body li li a {padding:5px 0 5px 43px; color:#969696;}
</style>
<!-- container-new 시작 //-->
<div id="container-new">

    <div id="mainMenu">
        <!-- 학습지원 -->
        <ul class="location">
            <li>홈</li>
            <li>사이트맵</li>
            <!-- 2015-01-19 개편삭제
            <li>학습지원</li>
            <li>
                이용안내 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <!-- 활성화된 메뉴에  class="active"
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">FAQ</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                    <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome" class="active">이용안내</a></li>
                </ul>
            </li>
            <li>
                사이트맵 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap" class="active">사이트맵</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                    <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                </ul>
            </li>
            
            //-->
        </ul>
        <!-- 서브메인용 -->
        <!-- 2015-01-19 개편삭제 
        <div class="introCategory">
            <ul>
                <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SiteMap"><strong><u>사이트맵</u></strong></a></li>
                <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">내 정보 관리</a></li>
                <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&amp;p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
            </ul>
        </div>
        -->
    </div>
    <!-- //#mainMenu -->

    <!-- 컨텐츠 영역 시작 -->
    <div id="contents">

        <div id="subCont">
        <h2 class="subTit">사이트맵<!-- <img src="/images/2013/support/sitemap_title.gif" alt="사이트맵" /> //--></h2>

            <!-- sitemap-body-->
            <div class="siteamp-header">
                사이트를 쉽고 편하게 이용하세요.<br>
                항상 노력하고 발전하는 한국콘텐츠아카데미가 되겠습니다.
            </div>
            <div class="sitemap-body">
                <!-- row -->
                <div class="row">
                    <!-- col -->
                    <div class="col">
                        <h3>새소식</h3>
                        <ul>
                            <li><a href="/servlet/controller.homepage.HomeNoticeServlet?p_process=List">공지사항</a></li>
                            <li><a href="javascript:alert('준비중 입니다.');">이벤트</a></li>
                            <li><a href="/servlet/controller.homepage.TongBoardServlet?p_process=list">아카데미 이야기</a></li>
                            <li><a href="/servlet/controller.homepage.CMUBoardServlet?p_process=list">CMU스토리</a></li>
                        </ul>
                    </div>
                    <!-- /col -->
                    <!-- col -->
                    <div class="col">
                        <h3>현장교육</h3>
                        <ul>
                            <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=001">교육신청</a></li>
                            <!--
                            <li><a href="/servlet/controller.off.ProposeOffServlet?p_process=SubjectList&p_area=002">차세대 인재양성교육</a></li>
                            //-->
                            <li><a href="/servlet/controller.course.EduScheduleHomePageServlet?p_process=offSchlYearPlanList">일정안내</a></li>
                        </ul>
                    </div>
                    <!-- /col -->
                    <!-- col -->
                    <div class="col">
                        <h3>온라인교육</h3>
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
                    </div>
                    <!-- /col -->
                    <!-- col -->
                    <div class="col">
                        <h3>열린강좌</h3>
                        <ul>
                            <li><a href="javascript:fnOpenClassList('ALL');">분류별</a></li>
                            <li><a href="javascript:fnOpenClassList('ALL');">테마별</a></li>
                        </ul>
                    </div>
                    <!-- /col -->
                </div>
                <!-- /row -->
                <!-- row -->
                <div class="row">
                    <!-- col -->
                    <div class="col">
                        <h3>고객센터</h3>
                        <ul>
                            <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome">학습지원</a>
                                <ul>
                                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome">학습가이드</a></li>
                                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelpSWDown">소프트웨어 다운로드</a></li>
                                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원서비스</a></li>
                                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일앱  서비스</a></li>
                                </ul>
                            </li>
                            <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">자주하는 질문</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a>
                                <ul>
                                    <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">묻고답하기</a></li>
                                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=insertPage;">1:1문의</a></li>
                                </ul>
                            </li>
                            <li><a href="javascript:alert('수료증 발급안내 준비중입니다.');">수료증 발급안내</a></li>
                        </ul>
                    </div>
                    <!-- /col -->
                    <!-- col -->
                    <div class="col">
                        <h3>아카데미소개</h3>
                        <ul>
                            <!--<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision">아카데미소개</a></li>-->
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">인사말</a></li>
                            <!-- 2015-03-18 가림
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                            //-->
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
                        </ul>
                    </div>
                    <!-- /col -->
                    <!-- col -->
                    <div class="col">
                        <h3>개인메뉴</h3>
                        <ul>
                            <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">온라인 강의실</a>
                                <ul>
                                    <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingSubjectPage">나의 강의실</a></li>
                                    <li><a href="/servlet/controller.study.FavorOpenClassServlet?p_process=favorOpenClassList">찜목록</a></li>
                                    <li><a href="/servlet/controller.research.SulmunSubjUserServlet?p_process=SulmunNew">설문</a></li>
                                    <li><a href="/servlet/controller.study.ProposeReviewServlet?p_process=selectReviewList">과정후기</a></li>
                                </ul>
                            </li>
                            <li><a href="/servlet/controller.study.MyClassServlet?p_process=EducationStudyingOffSubjectPage">현장교육 강의실</a></li>
                            <li><a href="/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_area=MINE">내가 작성한 글</a></li>
                            <li><a href="/servlet/controller.study.MyQnaServlet?p_process=MyQnaCounselListPage">상담내역</a></li>
                            <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberInfoUpdate">정보관리</a></li>
                        </ul>
                    </div>
                    <!-- /col -->
                    <!-- col -->
                    <div class="col">
                        <h3>이용안내</h3>
                        <ul>
                            <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin">회원가입</a></li>
                            <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=FindIdPwd">아이디/패스워드 찾기</a></li>
                            <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=PersonalNew">개인정보처리방침</a></li>
                            <li><a href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=UserAgree">이용약관</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=Footer&p_code=FOOTER_EMAIL">이메일무단수집거부</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=webReport">홈페이지 개선의견</a></li>
                        </ul>
                    </div>
                    <!-- /col -->
                </div>
                <!-- /row -->
            </div>
            <!--// sitemap-body-->
        </div>

    </div>
    <!-- //#contents -->
    <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>