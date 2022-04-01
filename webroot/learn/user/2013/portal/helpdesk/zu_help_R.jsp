<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("title_str", "학습환경 테스트 - 학습지원 - 고객센터");
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
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=HelpHome">학습 가이드</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp"><strong><u>학습환경 설정</u></strong></a></li>
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
        <!-- //#mainMenu -->

        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
        <h2 class="subTit">학습환경 설정<!--<img src="/images/2013/support/envistudy_title.gif" alt="학습환경 설정" />//--></h2>

            <div id="subCont">
                <!-- 탭삭제
                <ul class="tabCus">
                    <li><a href="#"><img src="/images/2013/support/tabenvi1_on.jpg" alt="학습환경 테스트" /></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelpSWDown"><img src="/images/2013/support/tabenvi2_off.jpg" alt="소트트웨어 다운로드" /></a></li>
                </ul>
                -->

                <div class="noticBox">
                    동영상 재생시 문제가 있으시다면, 현재 사용 중인 인터넷 회선의 연결속도를 확인해 주세요.<br />
                    회선 속도 테스트는 하루에 20~30분 간격을 두고 반드시 여러차례 해주셔야 정확한 속도를 알 수 있습니다.
                </div>

                <ol class="imgOlList">
                    <li><img src="/images/2013/support/envi_step1.jpg" alt="Step 01" /></li>
                    <li><img src="/images/2013/support/envi_step2.jpg" alt="Step 02" /></li>
                    <li><img src="/images/2013/support/envi_step3.jpg" alt="Step 03" /></li>
                </ol>

                <ol class="txtOlList">
                    <li><span>Step 01.</span> 한국정보화진흥원 사이트에 접속합니다. 접속 후 상단 메뉴에서 품질측정테스트→인터넷 속도 또는 메인에 인터넷 속도를 선택합니다. (<a href="http://speed.nia.or.kr/" target="_blank">한국정보화진흥원 바로가기</a>)</li>
                    <li><span>Step 02.</span> 정보 수집 약관 동의화면에서 약관을 읽고 동의 후 속도 측정의 진행을 원한다면, 약관에 동의 후 다음을 클릭합니다.</li>
                    <li><span>Step 03.</span> 측정을 처음 시도하는 경우 다음과 같은 웹 페이지의 메시지가 나옵니다. 테스트 진행을 위해서는 "확인"버튼을 클릭하여 설치프로그램을 다운로드합니다.</li>
                </ol>

                <ol class="imgOlList">
                    <li><img src="/images/2013/support/envi_step4.jpg" alt="Step 04" /></li>
                    <li><img src="/images/2013/support/envi_step5.jpg" alt="Step 05" /></li>
                    <li><img src="/images/2013/support/envi_step6.jpg" alt="Step 06" /></li>
                </ol>

                <ol class="txtOlList">
                    <li><span>Step 04.</span>다운로드가 완료되면, 위 이미지와 같이 "사용자 계정 컨트롤" 윈도우 나옵니다. 이 때 "예"를 선택하여, 프로그램 설치를 진행합니다.</li>
                    <li><span>Step 05.</span>설치를 정상적으로 진행하기 위해, "Next"버튼을 설치게이지 화면이 나오기 전까지 클릭합니다.</li>
                    <li><span>Step 06.</span>설치 화면에서 PC환경에 따라 약 1~3분 정도가 소요됩니다.</li>
                </ol>

                <ol class="imgOlList">
                    <li><img src="/images/2013/support/envi_step7.jpg" alt="Step 07" /></li>
                    <li><img src="/images/2013/support/envi_step8.jpg" alt="Step 08" /></li>
                    <li><img src="/images/2013/support/envi_step9.jpg" alt="Step 09" /></li>
                </ol>

                <ol class="txtOlList">
                    <li><span>Step 07.</span>설치가 완료되면, "Close"버튼을 클릭하여 윈도우를 닫아줍니다.</li>
                    <li><span>Step 08.</span>프로그램 설치가 완료 후 회원님이 현재 이용하는 회선과, 현재 PC를 이용하는 위치와 주거형태를 옳바르게 선택한 후 "다음"버튼을 클릭합니다.</li>
                    <li><span>Step 09.</span>"시작"버튼을 클릭하여 측정을 시작합니다. (간혹 "현재 동시 이용자가 많습니다" 라는 메세지가 나오는 경우가 있습니다. 이러한 경우에는 몇 분 뒤 다시 진행합니다.)</li>
                    <li><span>Step 10.</span>측정에는 약 1~3분 정도 소요되며, 인터넷 속도 측정이 완료됩니다. 다운로드 속도가 1.00 Mbps 미만일 경우 온라인 교육의 진행이 어려울 수 있습니다.</li>
                </ol>

            </div>
            <!-- //#subCont -->



        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>