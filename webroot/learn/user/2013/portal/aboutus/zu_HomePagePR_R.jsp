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

    box.put("title_str", "홍보영상 - 아카데미소개");
    String userAgent = request.getHeader("User-Agent").toLowerCase();
    boolean isUnderIE9 = ( userAgent.indexOf("msie 8") > -1 || userAgent.indexOf("msie 7") > -1 || userAgent.indexOf("msie 6") > -1 );
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>
<!-- container-new 시작 //-->
<div id="container-new">

        <div id="mainMenu">
        <!-- 학습지원 -->
            <ul class="location">
                <li>홈</li>
                <li>아카데미소개</li>
                <li>
                    홍보영상 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                    <ul style="z-index: 1;">
                        <!-- 활성화된 메뉴에  class="active" 삽입 -->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision">아카데미소개</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage" > 인사말</a></li>
                        <!-- 2015-03-18 가림
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                        //-->
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR" class="active">홍보영상</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
                        <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
<!-- 2015-01-08 개편삭제
                        <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
-->

                    </ul>
                </li>
            </ul>
            <!-- //학습지원 -->
<!--
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">진흥원장 인사말</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업 안내</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR"><strong><u>홍보영상</u></strong></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는 길</a></li>
                    <li><a href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
                    <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
                </ul>
            </div>

-->
            <!-- //서브메인용 -->
        </div>


        <!-- 컨텐츠 영역 시작 -->
        <div id="contents">
        <h2 class="subTit">홍보영상<!--<img src="/images/2013/introduce/academi_pr.gif" alt="홍보영상" />//--></h2>

            <div id="subCont">

				<div class="intro">
					<p class="aboutTxt">
                        창의인재 배출! 문화사업 전문인력양성! 한국콘텐츠아카데미 &lt;온라인교육&gt;편을 소개합니다.<br />
                        한국콘텐츠아카데미는 게임, 방송영상, 만화애니캐릭터, 음악공연 분야의 154개 온라인 정규과정과<br />
                        예비인력과 현업종사자 대상의 열린교육을 전과정 무료서비스하고 있습니다.
                    </p>
					<div class="aboutImg">
<%
        if ( isUnderIE9 ) {
%>
                        <object type="video/x-ms-wmv" data="mms://211.201.145.102/pr/kocca_pr_movie_640.wmv" width="640" height="405">
                            <param name="src" value="mms://211.201.145.102/pr/kocca_pr_movie_640.wmv" /> 
                            <param name="autostart" value="true" /> 
                            <param name="controller" value="true" />
                            <param name="showcontrols" value="true" />
                        </object>
<%
        } else {
%>
                        <video id="Video1" controls autoplay width="640" height="360">
                            <source src="/contents/pr_movie/kocca_pr_movie_640.mp4" type="video/mp4" />
                        </video>
<%
        }
%>

						<div class="blind">
							<dl>
								<dt>한국콘텐츠아카데미</dt>
								<dd>홍보영상</dd>
							</dl>
						</div>
						<!-- //blind -->
					</div>
					<!-- //aboutImg -->
				</div>

            </div>
            <!-- //#subCont -->

        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>