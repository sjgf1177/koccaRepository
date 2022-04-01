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

    box.put("title_str", "모바일 앱 서비스 - 학습지원 - 고객센터");
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
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelpSWDown">소프트웨어다운로드</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp" class="active">모바일 앱 서비스</a></li>
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
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelpSWDown">소프트웨어다운로드</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=RemoteService">원격지원 서비스</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=mobileApp"><strong><u>모바일 앱 서비스</u></strong></a></li>
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
        <h2 class="subTit">모바일앱 서비스</h2>

            <div id="subCont">
                <div class="noticBox">
                    스마트폰을 통해 접속할 수 있는 한국콘텐츠아카데미 공식 모바일 홈페이지 입니다.<br/>
                    PC에서 뿐만 아니라 모바일로 학습하실 수 있습니다.<br/><br/>
                    -안드로이드 : 구글플레이 접속 > '한국콘텐츠아카데미' 검색<br/>
                    -IOS : 앱스토어 접속 > '한국콘텐츠아카데미' 검색
                </div>
                <div>

                    <div style="width:550px; float:left; padding-top:40px;">
                        <div>
                            <img src="/images/2013/support/mobile_app_sub_title.jpg" alt="한국콘텐츠아카데미 모바일웹/애플리케이션"/>
                        </div>
                        <div>
                            <div style="width:250px; float:left; padding-top:50px">
                                <span style="font-size:20px; font-weight:bold; padding:25px;">PC에서 다운받기</span>
                                <a href="https://itunes.apple.com/us/app/kocca/id492151283?l=ko&ls=1&mt=8" target="_blank" title="새창으로 열립니다."><img src="/images/2013/support/mobileapp_down_ios.png" style="padding:20px 0px 0px 20px;" alt="iOS용 다운받기" /></a></br>
                                <a href="https://play.google.com/store/apps/details?id=com.newin.kocca" target="_blank" title="새창으로 열립니다."><img src="/images/2013/support/mobileapp_down_android.png" style="padding:20px 0px 0px 20px;" alt="Android용 다운받기" /></a>
                            </div>
                            <div style="width:250px; float:right; padding-top:50px">
                                <span style="font-size:20px; font-weight:bold; padding:25px;">QR코드로 다운받기</span>
                                <img src="/images/2013/support/qrcode.jpg" style="width:110px; height:110px; padding:20px 0px 0px 25px;" alt="QR코드" />
                            </div>
                        </div>

                    </div>

                    <div style="width:370px; float:right;">
                        <img src="/images/2013/support/mobile_screen_shot.jpg" alt="모바일서비스 스크린샷" />
                    </div>
                </div>
               
            </div>
            <!-- //#subCont -->

        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>