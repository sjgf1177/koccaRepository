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

    box.put("title_str", "온라인교육 - 교육사업 안내 -  아카데미소개");
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
					<li><a href="#"><img src="/images/2013/introduce/tabbs3_on.jpg" alt="온라인 교육" /></a></li>
					<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=4"><img src="/images/2013/introduce/tabbs4_off.jpg" alt="창의인재 양성사업" /></a></li>
				</ul>
				<h3 class="blind">차세대 인재 양성교육</h3>
				
				<h4 class="subTit_h4_gray">사이버콘텐츠아카데미  http://edu.kocca.or.kr</h4>
				<div class="academiSec gray">
					<ul>
						<li> 콘텐츠산업 예비 인력 및 현업종사자, 한국콘텐츠아카데미 회원 대상의 정규 강좌와 열린 강좌</li>
						<li> 정규과정 분야 131개 과정 서비스</li>
						<li> 열린강좌 199개 강좌 서비스</li>
						<li> 분야별 전문 튜터의  튜터링 서비스 제공</li>
						<li> 전과정 무료 강의</li>
					</ul>
				</div>

				<h4 class="subTit_h4_gray">교육분야 및 체계</h4>
				<div class="academiSec gray gimg">
					<ul>
						<li>기능과 산업을 고려한 다중분류체계 구성</li>
					</ul>
				</div>

				<h4 class="subTit_h4_gray">교육 신청/선발</h4>
				<div class="academiSec gray">
					<ul>
						<li>신청방법 : 온라인 신청 / http://edu.kocca.or.kr</li>
						<li>신청기준 : 한국콘텐츠아카데미 회원, 신청 회원수 제한 없음</li>
					</ul>
				</div>

				<h4 class="subTit_h4_gray">교육과정운영</h4>
				<div class="academiSec gray">
					<ul>
						<li>교육방법 : 학습자 개인 PC 또는 모바일에서 온라인 학습</li>
						<li>과정운영 : 매월 2회 개강(1일, 15일</li>
						<li>운영내용 : 방송영상 / 게임 / 문화콘텐츠 기획, 제작, 비즈니스 분야 등 131개 과정 운영 (열린강좌 199개 강좌 운영)</li>
						<li>학습기간 : 교육 시작일 기준 학습 1개월, 교육 종료일 기준 복습 3개월</li>
						<li>교육비용 : 무료</li>
					</ul>
				</div>

				<h4 class="subTit_h4_gray">교육문의</h4>
				<div class="academiSec gray">
					<ul>
						<li>한국콘텐츠아카데미 온라인교육팀<br/>Tel : 02-2161-0077</li>
					</ul>
				</div>
				
			</div>
			<!-- //#subCont -->
			
		</div>
		<!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->
		
<%@ include file="/learn/user/2013/portal/include/footer.html"%>