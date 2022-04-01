<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("title_str", "소프트웨어 다운로드 - 학습지원 - 고객센터");
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
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp">학습환경 설정</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelpSWDown"><strong><u>소프트웨어 다운로드</u></strong></a></li>
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

        <h2 class="subTit">소프트웨어 다운로드<!--<img src="/images/2013/support/swdown_title.gif" alt="소프트웨어다운로드" />//--></h2>

            <div id="subCont">
<!-- 탭삭제
                <ul class="tabCus">
                    <li><a href="/servlet/controller.homepage.HomePageHelpServlet?p_process=selectHelp"><img src="/images/2013/support/tabenvi1_off.jpg" alt="학습환경 테스트" /></a></li>
                    <li><a href="#"><img src="/images/2013/support/tabenvi2_on.jpg" alt="소트트웨어 다운로드" /></a></li>
                </ul>
-->
                <div class="imgList2 first">
                    <dl>
                        <dt><img src="/images/2013/support/sf_img1.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">Adobe Flash Player</p>
                            <p class="text">온라인과정의 교육은 대부분 플래시로 제작되어 있습니다. 학습을 하기 위해서는 이 S/W가 필수입니다.</p>
                            <a href="http://get.adobe.com/kr/flashplayer/" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                    <dl>
                        <dt><img src="/images/2013/support/sf_img2.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">Adobe ShockWave Player</p>
                            <p class="text">ShockWave로 제작된 강의 또는 미디어를 보기 위해서는 이 S/W가 필요합니다.</p>
                            <a href="http://get.adobe.com/kr/shockwave/" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                </div>

                <div class="imgList2">
                    <dl>
                        <dt><img src="/images/2013/support/sf_img3.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">RealPlayer</p>
                            <p class="text">ra, rm 등으로 이루어진 강의를 볼 수 있는 프로그램입니다.</p>
                            <a href="http://kr.real.com/" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                    <dl>
                        <dt><img src="/images/2013/support/sf_img4.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">ORACLE Java 7</p>
                            <p class="text">XP초기버전 사용자는 자바 가상머신이 설치되어 있지 않습니다. 강의를 받기 위해서는 필수인 S/W입니다.</p>
                            <a href="http://java.com/ko/download/index.jsp" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                </div>

                <div class="imgList2">
                    <dl>
                        <dt><img src="/images/2013/support/sf_img5.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">Windows Media Player</p>
                            <p class="text">강의 중 동영상이 나오지 않을 경우 이 S/W를 설치해야 하는 경우가 있습니다.</p>
                            <a href="http://www.microsoft.com/ko-KR/download/details.aspx?id=20426" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                    <dl>
                        <dt><img src="/images/2013/support/sf_img6.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">한글과 컴퓨터 오피스 뷰어 2010 SE+</p>
                            <p class="text">강의자료 중 한글파일(hwp)로 작성된 자료가 있습니다. 자료를 확인하실 때 설치합니다.</p>
                            <a href="http://www.hancom.com/downLoad.downPU.do?mcd=002" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                </div>

                <div class="imgList2">
                    <dl>
                        <dt><img src="/images/2013/support/sf_img7.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">Adobe Reader X</p>
                            <p class="text">강의자료 중 PDF파일로 된 아크로뱃리더 파일이 있습니다. 자료를 확인하실 때 설치합니다.</p>
                            <a href="http://get.adobe.com/kr/reader/?promoid=HTEHD" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                    <dl>
                        <dt><img src="/images/2013/support/sf_img8.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">MS Office PowerPoint Viewer</p>
                            <p class="text">강의자료 중 파워포인트(ppt)파일로 작성된 자료가 있습니다. 자료를 확인하실 때 설치합니다.</p>
                            <a href="http://www.microsoft.com/ko-kr/download/details.aspx?id=13" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                </div>

                <div class="imgList2">
                    <dl>
                        <dt><img src="/images/2013/support/sf_img9.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">Adobe Flash CS6 (무료 시험버전)</p>
                            <p class="text">게임,영화 및 애니메이션 등 다양한 분야에서 주로 사용되는 툴입니다.</p>
                            <a href="http://www.adobe.com/cfusion/tdrc/index.cfm?product=flash&loc=ko" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                    <dl>
                        <dt><img src="/images/2013/support/sf_img10.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">Adobe After Effect CS6 (무료 시험버전)</p>
                            <p class="text">영상 특수효과를 제작할 수 있는 툴입니다. 영화 및 게임에서 인지도가 매우 높습니다.</p>
                            <a href="http://www.adobe.com/cfusion/tdrc/index.cfm?product=after_effects" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                </div>

                <div class="imgList2">
                    <dl>
                        <dt><img src="/images/2013/support/sf_img11.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">Adobe Premiere Pro CS6 (무료 시험버전)</p>
                            <p class="text">영화, 애니메이션등의 영상을 편집과 인코딩을 할 수 있는 전문 툴입니다.</p>
                            <a href="http://www.adobe.com/cfusion/tdrc/index.cfm?product=premiere_pro" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                    <dl>
                        <dt><img src="/images/2013/support/sf_img12.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">Adobe Photoshop CS6 (무료 시험버전)</p>
                            <p class="text">모든 이미지 및 사진을 편집하거나 제작할 수 있는 대표적인 툴입니다.</p>
                            <a href="http://www.adobe.com/cfusion/tdrc/index.cfm?product=photoshop" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                </div>


                <div class="imgList2">
                    <dl>
                        <dt><img src="/images/2013/support/sf_img13.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">Autodesk 3ds Max (무료체험판)</p>
                            <p class="text">게임,영화,애니메이션,이미지 등 3D구현의 가장 널리 사용되는 툴입니다.</p>
                            <a href="http://www.autodesk.co.kr/adsk/servlet/download/item?siteID=1169528&id=17089546" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                    <dl>
                        <dt><img src="/images/2013/support/sf_img14.jpg" alt="adobe flash player" /></dt>
                        <dd>
                            <p class="title">Autodesk Maya (무료체험판)</p>
                            <p class="text">3D애니메이션 제작에 특화된 대표적인 3D그래픽 툴입니다.</p>
                            <a href="http://www.autodesk.co.kr/adsk/servlet/download/item?siteID=1169528&id=17089766" target="_blank"><img src="/images/2013/btn/btn_down.gif" alt="다운로드 바로가기" /></a>
                        </dd>
                    </dl>
                </div>

            </div>
            <!-- //#subCont -->

        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>