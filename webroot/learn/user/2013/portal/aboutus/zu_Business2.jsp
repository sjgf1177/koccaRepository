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

    //box.put("title_str", "현업인직무재교육 - 교육사업 안내 - 아카데미소개");
    box.put("title_str", "현업인직무재교육 - 현장교육");
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
                        교육사업 안내 <a href="#" class="unfold"><img src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
                        <ul style="z-index: 1;">
                            <!-- 활성화된 메뉴에  class="active" 삽입 -->
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision">아카데미소개</a></li>
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">인사말</a></li>
                            <!-- 2015-03-18 가림
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                            //-->
                            <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu" class="active">교육사업 안내</a></li>
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
<!--2015-01-08 개편삭제
				<div class="introCategory">
					<ul>
						<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage">진흥원장 인사말</a></li>
						<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
						<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu"><strong><u>교육사업 안내</u></strong></a></li>
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
            <h2 class="subTit">교육사업 안내<!--<img src="../images/2013/introduce/edubusiness_title.gif" alt="교육사업" />//--></h2>

            <div id="subCont">
                <ul class="tabCus">
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=1"><img src="/images/2013/introduce/tabbs1_off.jpg" alt="차세대 인재 양성교육" /></a></li>
                    <li><a href="#"><img src="/images/2013/introduce/tabbs2_on.jpg" alt="협업인 직무 재교육" /></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=3"><img src="/images/2013/introduce/tabbs3_off.jpg" alt="온라인 교육" /></a></li>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=4"><img src="/images/2013/introduce/tabbs4_off.jpg" alt="창의인재 양성사업" /></a></li>
                </ul>
                <h3 class="blind">현업인 직무 재교육</h3>

                <h4 class="subTit_h4_gray">교육과정</h4>
                <div class="tableWrap2">
                    <table class="layoutTable2 space3">
                        <colgroup>
                            <col width="17%"/>
                            <col width="*"/>
                            <col width="21%"/>
                            <col width="9%"/>
                        </colgroup>
                        <tr>
                            <th scope="col" class="thtit">분야</th>
                            <th scope="col">과정명</th>
                            <th scope="col">일자</th>
                            <th scope="col">인원(명)</th>
                        </tr>
                        <tr class="line2">
                            <td rowspan="9" class="tdtit"><strong>문화콘텐츠<br />전문인력양성</strong></td>
                            <td>KOCCA 창의 마스터클래스 [콘텐츠 기획]</td>
                            <td>6/25, 7/2, 7/9, 7/13, 7/16</td>
                            <td class="center">25</td>
                        </tr>
                        <tr class="line2">
                            <td>KOCCA 창의 마스터클래스 [콘텐츠 디자인]</td>
                            <td>6/27, 7/4, 7/11, 7/18, 7/20</td>
                            <td class="center">25</td>
                        </tr>
                        <tr class="line2">
                            <td>KOCCA 창의 마스터클래스 [콘텐츠 제작A]</td>
                            <td>8/8, 8/16, 8/22, 8/24</td>
                            <td class="center">25</td>
                        </tr>
                        <tr class="line2">
                            <td>KOCCA 창의 마스터클래스 [콘텐츠 제작B]</td>
                            <td>8/13, 8/20, 8/27, 8/31</td>
                            <td class="center">25</td>
                        </tr>
                        <tr class="line2">
                            <td>KOCCA 창의 마스터클래스 [콘텐츠 비지니스] (해외 전문가 초청 강연)</td>
                            <td>9/7</td>
                            <td class="center">100</td>
                        </tr>
                        <tr class="line2">
                            <td>전략지역 전문가 해외연수 [뮤지컬] (일본)</td>
                            <td>5/20, 5/25</td>
                            <td class="center">21</td>
                        </tr>
                        <tr class="line2">
                            <td>전략지역 전문가 해외연수 [드라마] (동남아)</td>
                            <td>9월(예정)</td>
                            <td class="center">20</td>
                        </tr>
                        <tr class="line2">
                            <td>전략지역 전문가 해외연수 [K-POP] (미국)</td>
                            <td>11월(예정)</td>
                            <td class="center">15</td>
                        </tr>
                        <tr>
                            <td>합계</td>
                            <td></td>
                            <td class="center">256</td>
                        </tr>
                        <tr class="line2">
                            <td rowspan="2" class="tdtit"><strong>기획프로듀서<br />과정</strong></td>
                            <td>기획프로듀서 양성과정</td>
                            <td>2013. 4 ~ 2013. 12 (9개월)</td>
                            <td class="center">20</td>
                        </tr>
                        <tr>
                            <td>기획프로듀서 프로젝트 과정</td>
                            <td>2013. 5 ~ 2013. 10 (6개월)</td>
                            <td class="center">10</td>
                        </tr>
                    </table>
                </div>
                <!-- //tableWrap2 -->

                <h4 class="subTit_h4_gray">교육신청/선발</h4>
                <div class="tableWrap2">
                    <table class="layoutTable2 space3">
                        <colgroup>
                            <col width="22%"/>
                            <col width="*"/>
                        </colgroup>
                        <tr>
                            <th scope="col" class="thtit">구분</th>
                            <th scope="col">내용</th>
                        </tr>
                        <tr>
                            <td class="tdtit">신청 방법</td>
                            <td>
                                <ul>
                                    <li>온라인 신청 / http://edu.kocca.or.kr</li>
                                </ul>
                            </td>
                        </tr>
                        <tr>
                            <td class="tdtit">선발 기준</td>
                            <td>
                                <ul>
                                    <li>각 과정별 참가자격 적합성 여부(서류전형), 소속기관별 인원 안배 등</li>
                                </ul>
                            </td>
                        </tr>
                    </table>
                </div>
                <!-- //tableWrap2 -->

                <h4 class="subTit_h4_gray">교육문의</h4>
                <div class="tableWrap2 noclose">
                    <table class="layoutTable2 space3">
                        <colgroup>
                            <col width="*"/>
                        </colgroup>
                        <tr>
                            <td class="line">
                                한국콘텐츠진흥원 창의인재양성팀 T : 02-2161-0072
                            </td>
                        </tr>
                    </table>
                </div>
                <!-- //tableWrap2 -->

            </div>
            <!-- //#subCont -->

        </div>
        <!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>