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

    box.put("title_str", "아카데미소개 - 아카데미소개");
%>
        
<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- container-new 시작 //-->
<div id="container-new">

        <div id="mainMenu">
            <!-- 아카데미 소개 -->
            <ul class="location">
                <li>홈</li>
                <li>아카데미소개</li>
                <li>아카데미소개 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision" class="active">아카데미소개</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage" >인사말</a></li>
                        <!-- 2015-03-18 가림
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                        //-->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
<!-- 2015-01-08 개편삭제
                        <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
-->
                    </ul>
                </li>
            </ul>
        
<!--2015-01-08 개편 삭제
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">진흥원장 인사말</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
                    <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
                </ul>
            </div>
-->
        </div>
        
        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
            <h2 class="subTit">아카데미 소개<!--<img src="/images/2013/introduce/academi_title.gif" alt="아카데미소개" />//--></h2>
            <div id="subCont">
                <div class="intro">
                    <p class="aboutTxt" style="margin-left:60px;">
                        콘텐츠분야 인재양성의 중심에 있는 한국콘텐츠아카데미는 미래 콘텐츠 산업을 선도할 콘텐츠 분야 
                        핵심인재를 양성하고 있습니다. 차세대 인재 양성, <br />현업인 직무 재교육, 온라인 평생교육을 통해 콘텐츠
                        강국으로의 발돋움에 힘을 보태고 있습니다.</p>
                    <div class="aboutImg">
                        <img src="/images/2013/introduce/intro_gaphic5.jpg" alt="차세대인재양성교육, 현업인직무재교육,온라인평생교육을 세개의 원을 사용하여 교집합형태로 도형화 시킨 이미지" />
                        <div class="blind">
                            <dl>
                                <dt>차세대인재양성교육</dt>
                                <dd>
                                    콘텐츠 창의인재 동반사업, 영상제작/게임콘텐츠 과정
                                </dd>
                            </dl>
                            <dl>
                                <dt>온라인평생교육</dt>
                                <dd>
                                    게임/방송영상/문화분야 사이어콘텐츠아카데미 운영 http://edu.kocca.or.kr
                                </dd>
                            </dl>
                            <dl>
                                <dt>현업인 직무 재교육</dt>
                                <dd>
                                    창의 마스터 클래스,전략지역 전문가 해외연수, 기획프로듀서양성 과정
                                </dd>
                            </dl>
                        </div>
                        <!-- //blind -->
                    </div>
                    <!-- //aboutImg -->
                </div>
                <!-- //intro -->
            </div>
            <!-- //#subCont -->
        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->
        
<%@ include file="/learn/user/2013/portal/include/footer.html"%>