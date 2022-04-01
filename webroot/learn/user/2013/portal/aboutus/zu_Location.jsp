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

    box.put("title_str", "오시는 길 - 아카데미소개");
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
                    오시는 길 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision">아카데미 소개</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">인사말</a></li>
                        <!-- 2015-03-18 가림
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                        //-->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction" class="active">오시는 길</a></li>
<!-- 2015-01-08 개편삭제
                        <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
-->

                    </ul>
                </li>
            </ul>
            <!-- 2015-01-08 개편삭제
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">진흥원장 인사말</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction"><strong><u>오시는 길</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
                    <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
                </ul>
            </div>
            //-->

        </div>


        <div id="contents">
            <h2 class="subTit">오시는 길<!--<img src="/images/2013/introduce/map_title.gif" alt="오시는 길" />//--></h2>
            <div id="subCont">
                <div class="mapLocation">
                    <div class="situation"><img src="/images/2013/introduce/img_map.gif" alt="" /></div>
                    <p class="address">158-715 서울시 종로구 연견동 대학로 57 홍익대 아트센터 건물 1층 (전화 02-2161-0077(온라인 교육), 02-2161-0072(오프라인 교육) / 팩스 02-2161-0078)</p>
                    <p class="tit"><img src="/images/2013/introduce/tit_guide_road.gif" width="132" height="17" alt="대중교통 이용안내" /></p>

                    <dl class="subway">
                        <dt>지하철 이용 시</dt>
                        <dd>
                        	<ul>
                                <li>
                                <!--<img src="/images/2013/introduce/img_1line.gif" alt="1호선마크" /> --> 1호선 종로5가역 하차 : 2번 출구 방면으로 이동  이화사거리 방면으로 약 800m 직전 (도보 7분)
                                </li>
                                <li>
                        		<!-- <img src="/images/2013/introduce/img_4line.gif" alt="4호선마크" /> --> 4호선 혜화역 하차: 3번 출구 방면으로 이동  이화사거리 방면으로 약 800m 직진 (도보 7분)후 횡단보도 이용
                        		</li>
                        	</ul>
						</dd>
                    </dl>
                    <p class="tit"></p>
                    <dl class="bus">
                        <dt>버스 이용 시</dt>
                        <dd>
                            <ul>
                                <li>
									이화사거리(01572) 하차 : 종로 05번 마을버스(혜화역 3번출구  홍익대 대학로 캠퍼스 운행)
                                </li>
                                <li>
									이화장(01-223) 하차 : 109, 273, 601, 2112, 7025 
                                </li>
                                <li>
									통신대(01-219) 하차 : 104, 106, 107, 140, 143, 150, 160, 273, 710, 2112 
                                </li>
                                <li>
									현대그룹빌딩(01-218) 하차 : 102, 107, 108, 109, 162, 301, 7025 
                                </li>
                            </ul>
                        </dd>
                    </dl>
                </div>
                <!-- //mapLocation -->
            </div>
            <!-- //#subCont -->

        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>