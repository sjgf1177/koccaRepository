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

    box.put("title_str", "학습가이드 - 학습지원 - 고객센터");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<!-- container-new 시작 //-->
<div id="container-new">


        <div id="mainMenu">
        <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>고객센터</li>
                <li>학습지원 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome" class="active">학습지원</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageFAQServlet?p_process=selectList">자주하는 질문</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
<!--
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp" >학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelpSWDown">소프트웨어다운로드</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일 앱 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome">이용안내</a></li>
-->
                    </ul>
                </li>
            </ul>
            <!-- //학습지원 -->
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome"><strong><u>학습 가이드</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp" >학습환경 설정</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelpSWDown">소프트웨어 다운로드</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp">모바일 앱 서비스</a></li>
<!--
                    <li><a href="/servlet/controller.homepage.HomePageQNAServlet?p_process=selectList">교육문의</a></li>
                    <li><a href="#">연간일정</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=SupportHome">이용안내</a></li>
-->                </ul>
            </div>
            <!-- //서브메인용 -->
        </div>


        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
        <h2 class="subTit">학습가이드<!--<img src="/images/2013/support/eduguide_title.gif" alt="학습가이드" />//--></h2>

            <div id="subCont">
                <div class="noticBox">
                    학습 전/중/후 필요한 정보, 자료 및 이용안내를 제공합니다.
                </div>
                <div class="step4"><img src="/images/2013/support/img_4step.jpg" alt="한국콘텐츠아카데미에 처음오셨나요? 전문교율을 무료로 받아보세요" usemap="#step_map" /></div>
                <ol class="blind">
                    <li>step1 회원가입</li>
                    <li>step2 로그인</li>
                    <li>step3 과정선택</li>
                    <li>step4 수강신청</li>
                </ol>
<%
        if ( isUserLogin ) {
%>
                <map name="step_map">
                    <area shape="rect" coords="635,0,745,111" href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" target="_self" alt="과정선택" />
                    <area shape="rect" coords="800,0,910,111" href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" target="_self" alt="수강신청" />
                </map>
<%
        } else {
%>
                <map name="step_map">
                    <area shape="rect" coords="305,0,415,111" href="/servlet/controller.homepage.MainMemberJoinServlet?p_process=MemberJoin" target="_self" alt="회원가입" />
                    <area shape="rect" coords="470,0,580,111" href="/servlet/controller.homepage.MainServlet?p_process=MainLogin" target="_self" alt="로그인" />
                    <area shape="rect" coords="635,0,745,111" href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" target="_self" alt="과정선택" />
                    <area shape="rect" coords="800,0,910,111" href="/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList" target="_self" alt="수강신청" />
                </map>
<%
        }
%>
            </div>
            <!-- //#subCont -->

        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>