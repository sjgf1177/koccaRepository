<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    box.put("title_str", "교육협력기관 - 아카데미소개");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- container-new 시작 //-->
<div id="container-new">

        <div id="mainMenu">
            <!-- 아카데미 소개 -->
            <ul class="location">
                <li>홈</li>
                <li>아카데미소개</li>
                <li>
                    교육협력기관 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision">아카데미소개</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage"> 인사말</a></li>
                        <!-- 2015-03-18 가림
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                        //-->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER" class="active">교육협력기관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
<!-- 2015-01-08 개편삭제
                        <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
-->

                    </ul>
                </li>
            </ul>
<!-- 2015-01-08 개편 삭제
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">진흥원장 인사말</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER"><strong><u>교육협력기관</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
                    <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
                </ul>
            </div>
-->
        </div>
        <div id="contents">
        <h2 class="subTit">교육협력기관<!--<img src="/images/2013/introduce/partner_title.gif" alt="교육협력기관" />//--></h2>
            <div id="subCont">
                <div class="cooperation">
                    <ul>
                        <li class="pdnone"><img src="/images/2013/introduce/contact1.gif"  alt="문화체육관광부" /></li>
                        <li><img src="/images/2013/introduce/contact2.gif"  alt="송파구" /></li>
                        <li><img src="/images/2013/introduce/contact3.gif"  alt="한국소리문화의전당" /></li>
                        <li><img src="/images/2013/introduce/contact4.gif"  alt="중앙경찰학교" /></li>
                        <li class="pdnone"><img src="/images/2013/introduce/contact5.gif"  alt="대한민국공군" /></li>
                        <li><img src="/images/2013/introduce/contact6.gif"  alt="부산교육연수원" /></li>
                        <li><img src="/images/2013/introduce/contact7.gif"  alt="서울교대연수원" /></li>
                        <li><img src="/images/2013/introduce/contact8.gif"  alt="중앙선거관리위원회" /></li>
                        <li class="pdnone"><img src="/images/2013/introduce/contact9.gif"  alt="한국방송공사" /></li>
                        <li><img src="/images/2013/introduce/contact10.gif"  alt="부산경남대표방송KNN" /></li>
                        <li><img src="/images/2013/introduce/contact11.gif"  alt="SBS" /></li>
                        <li><img src="/images/2013/introduce/contact12.gif"  alt="홈&쇼핑" /></li>
                        <li class="pdnone"><img src="/images/2013/introduce/contact13.gif"  alt="HOMI" /></li>
                        <li><img src="/images/2013/introduce/contact14.gif"  alt="HD KOREA broadcasting CORP" /></li>
                        <li><img src="/images/2013/introduce/contact15.gif"  alt="세종대학교" /></li>
                        <li><img src="/images/2013/introduce/contact16.gif"  alt="중부대학교" /></li>
                        <li class="pdnone"><img src="/images/2013/introduce/contact17.gif"  alt="서울호서전문학교" /></li>
                        <li><img src="/images/2013/introduce/contact18.gif"  alt="경기모바일과학고등학교" /></li>
                        <li><img src="/images/2013/introduce/contact19.gif"  alt="상일미디어고등학교" /></li>
                        <li><img src="/images/2013/introduce/contact20.gif"  alt="서울디지텍고등학교" /></li>
                        <li class="pdnone"><img src="/images/2013/introduce/contact21.gif"  alt="천안천일고등학교" /></li>
                        <li><img src="/images/2013/introduce/contact22.gif"  alt="한국게임과학고등학교" /></li>
                        <li><img src="/images/2013/introduce/contact23.gif"  alt="한세사이버보안고등학교" /></li>
                        <li><img src="/images/2013/introduce/contact24.gif"  alt="한국문화콘텐츠고등학교" /></li>
                    </ul>
                </div>
                <!-- //cooperation -->
            </div>
            <!-- //#subCont -->
        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>