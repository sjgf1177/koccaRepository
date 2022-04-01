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

    box.put("title_str", "창의인재 양성사업 - 교육사업 안내 - 아카데미소개");
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
			<h2 class="subTit">교육사업 안내<!--<img src="/images/2013/introduce/edubusiness_title.gif" alt="교육사업" />//--></h2>
			
			<div id="subCont">
				<ul class="tabCus">
					<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=1"><img src="/images/2013/introduce/tabbs1_off.jpg" alt="차세대 인재 양성교육" /></a></li>
					<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=2"><img src="/images/2013/introduce/tabbs2_off.jpg" alt="협업인 직무 재교육" /></a></li>
					<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=3"><img src="/images/2013/introduce/tabbs3_off.jpg" alt="온라인 교육" /></a></li>
					<li><a href="#"><img src="/images/2013/introduce/tabbs4_on.jpg" alt="창의인재 양성사업" /></a></li>
				</ul>
				<h3 class="blind">차세대 인재 양성교육</h3>
				
				<h4 class="subTit_h4_gray">사업 목적</h4>
				<div class="academiSec gray">
					<ul>
						<li>
							미래 콘텐츠 산업을 이끌어갈 젊은 창의인재 양성을 위해, 창작 분야의 고급 전문가(멘토)를 통한 도제식 멘토링을 지원하여, 청년 인재의<br />
							창작능력 개발과 일자리 창출<br />
							- 도제식 창의숙성 멘토링 기획/운영이 가능한 법인을 ‘플랫폼기관’으로 선정하여 지원<br />
							- 플랫폼 기관을 통해 전문가(멘토)와 창의교육생(멘티)을 매칭, 장기간의 도제식 훈련과 견습 창작의 장을 제공 (9개월~10개월)<br /><br />
							&nbsp;&nbsp;※ 창의교육생(멘티)은 플랫폼 기관과 근로계약을 체결하고, 멘토와 플랫폼 기관이 진행하는 다양한 프로젝트에  인턴쉽으로 참여<br />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(매월 약 100백만원 지급, 4대보험 포함)<br />
							&nbsp;&nbsp;※ 플랫폼 기관과 멘토를 통해 진로상담, 취업 알선, 창직/창업 멘토링 등 다양한 경력경로 코칭
						</li>
					</ul>
				</div>

				<h4 class="subTit_h4_gray">지원대상</h4>
				<div class="tableWrap2">
					<table class="layoutTable2 space3">
						<colgroup>
							<col width="22%"/>
							<col width="*"/>
						</colgroup>
						<tr>
							<th scope="col" class="thtit">구분</th>
							<th scope="col">자격</th>
						</tr>
						<tr>
							<td class="tdtit">플랫폼 기관</td>
							<td>
								<ul>
									<li>콘텐츠 창조분야의 도제식 멘토링/훈련이 가능한 설립 3년 이상의 법인</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td class="tdtit">멘토</td>
							<td>
								<ul>
									<li>해당분야 10년 이상의 경력을 쌓은 전문가 (플랫폼 기관에서 위촉)</li>
								</ul>
							</td>
						</tr>
						<tr>
							<td class="tdtit">창의교육생(멘티)</td>
							<td>
								<ul>
									<li>창작의 소질과 소양을 갖춘 예비취업자 (만 18세~35세로 창작분야 종사 희망자를 서류/면접을 통해 선발)</li>
								</ul>
							</td>
						</tr>
					</table>
				</div>
				<!-- //tableWrap2 -->

				<h4 class="subTit_h4_gray">추진절차 및 지원내용</h4>
				<div class="academiSec gray">
					<ul>
						<li>2014년도 콘텐츠 창의인재 동반사업이 곧, 새롭게 시작됩니다.</li>
						<li>
							추진절차 : 플랫폼 기관 선정공고 > 플랫폼 기관 선정 > 기관별 멘토 확정 후 창의교육생(멘티)선발 > 발대식 및 도제식 멘토링 실시<br />
							<span>플랫폼기관 : 2014년 약 7개 기관 내외, 기관당 약 4,5억원 내외 지원예정</span>
							<span>멘      토 :멘토 1인당 100만원의 활동비를 플랫폼 기관에서 지원</span>
							<span>창의교육생(멘티) : 플랫폼기관 및 멘토와 상호협약(근로계약)을 맺고, 창작활동에 필요한 실무를 습득하도록 지원<br />
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							(매월 약 100만원의 급여를 플랫폼 기관이 지금, 4대보험 포함)</span>
						</li>
					</ul>
				</div>

				<h4 class="subTit_h4_gray">신청방법</h4>
				<div class="academiSec gray">
					<ul>
						<li>
							한국콘텐츠진흥원 홈페이지(<a href="http://www.kocca.kr" target="_blank">www.kocca.kr</a>) 접속, 기업회원 가입, 로그인 후에 해당 사업 신청양식을 다운받아 작성 후<br />
							첨부서류와 함께 온라인 지원신청 접수
						</li>
					</ul>
				</div>

				<h4 class="subTit_h4_gray">연락처</h4>
				<div class="academiSec gray">
					<ul>
						<li>한국콘텐츠진흥원 콘텐츠창업지원팀 T : 02-2161-0072, 1566-1114</li>
					</ul>
				</div>

			</div>
			<!-- //#subCont -->
			
		</div>
		<!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>