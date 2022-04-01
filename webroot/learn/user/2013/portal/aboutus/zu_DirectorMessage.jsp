<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page errorPage="/learn/library/error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.credu.homepage.*"%>
<%@ page import="com.credu.common.*"%>
<%@ page import="com.credu.library.*"%>
<%@ page import="com.credu.system.*"%>

<%
	RequestBox box = (RequestBox) request.getAttribute("requestbox");

	box.put("title_str", "인사말 - 아카데미소개");
%>

<%@ include file="/learn/user/2013/portal/include/header.jsp"%>

<!-- container-new 시작 //-->
<div id="container-new">

	<div id="mainMenu">
		<!-- 아카데미 소개 -->
		<ul class="location">
			<li>홈</li>
			<li>아카데미소개</li>
			<li>인사말 <a href="#" class="unfold"><img
					src="/images/2013/btn/btn_menuview.gif" alt="메뉴 보기" /></a>
				<ul style="z-index: 1;">
					<!-- 활성화된 메뉴에  class="active" 삽입 -->
					<li><a
						href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Vision">아카데미소개</a></li>
					<li><a
						href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage"
						class="active">인사말</a></li>
					<!-- 2015-03-18 가림
                        <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Facility">시설소개</a></li>
                        //-->
					<li><a
						href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu">교육사업
							안내</a></li>
					<li><a
						href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=PR">홍보영상</a></li>
					<li><a
						href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=COOPER">교육협력기관</a></li>
					<li><a
						href="/servlet/controller.homepage.HomePageContactServlet?p_process=contactus">제휴문의</a></li>
					<li><a
						href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=Direction">오시는
							길</a></li>
					<!-- 2015-01-08 개편삭제
                        <li><a href="/servlet/controller.infomation.BriefingHomePageServlet?p_process=selectList">보도자료</a></li>
-->
				</ul>
			</li>
		</ul>
		<!-- 2015-01-08 개편 삭제
            <div class="introCategory">
                <ul>
                    <li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=DirectorMessage"><strong><u>진흥원장 인사말</u></strong></a></li>
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
		<h2 class="subTit">인사말</h2>
		<div id="subCont">
			<div class="greeting">
				<div>
					<div style="float: left">
						<img src="/images/2013/common/bar.jpg" alt="bar" />
					</div>
					<!-- <div style="padding-left: 15px; font-size: 20px; font-weight: bold">
						한국콘텐츠아카데미는 창조경제의 핵심산업인 콘텐츠산업을 견인하고 있는<br /> 한국콘텐츠진흥원의
						콘텐츠전문인력양성기관입니다.
					</div>
				</div>
				<div style="width: 530px; float: left; padding: 40px 16px;">

					한국콘텐츠아카데미는 창조경제의 핵심산업인 콘텐츠산업을 견인하고 있는 한국콘텐츠진흥원의 콘텐츠전문인력양성기관입니다. <br />
					<br />
					<br /> 한국콘텐츠아카데미는 콘텐츠분야 글로벌 인재양성을 위해 창의, 융합, 감성 등 창조산업을 대표하는 키워드를
					핵심가치로 콘텐츠 산업 현장에서 필요로 하는 융합형 창작인재를 양성하기 위해 노력하고 있습니다. <br />
					<br />
					<br /> 콘텐츠의 경쟁력은 전적으로 창작자의 창의 능력에서 비롯됩니다. 글로벌 경쟁력을 갖춘 콘텐츠의 기획과 제작,
					해외 시장 진출은 모두 우수한 창작인력 기반위에 창출되는 것으로, 고도의 창작 역량과 융합 기술력을 갖춘 창의 인재를
					양성하기 위해서는 체계적인 교육 커리큘럼과 전문창작을 뒷받침해줄 수 있는 교육인프라를 갖추어야 합니다. <br />
					<br />
					<br /> 한국콘텐츠아카데미는 국내외 최고의 강사진과 풍부한 교육 노하우, 국내 최고의 콘텐츠제작교육 인프라를 바탕으로
					콘텐츠 산업의 예비인력교육, 협업인 직무교육, 전략지역 해외연수 및 온라인교육과정 등을 운영하고 있으며, 이를 통해
					콘텐츠산업의 공적 연수기관으로서의 역할과 기능 수행을 위해 최선을 다하겠습니다. <br />
					<br />
					<br /> 여러분의 변함없는 관심과 성원을 부탁드리겠습니다. <br />
					<br />
					<br /> <span style="font-weight: bold">한국콘텐츠진흥원장 송성각</span>
				</div> -->
				<div style="padding-left: 15px; font-size: 20px; font-weight: bold">
					한국콘텐츠아카데미는<br />한국콘텐츠진흥원의 콘텐츠전문인력양성기관입니다.
				</div>
			</div>
			<div style="width: 530px; float: left; padding: 40px 16px;">


				<p>
					한국콘텐츠아카데미는 <br />창의/융합/감성 등의 키워드를 핵심가치로<br />콘텐츠 산업 현장에서 필요한 융합형
					창의인재를 양성하기 위해 노력하고 있습니다.
				</p>
				<br /> <br /> <br />
				<p>
					콘텐츠의 경쟁력은 창작자의 창의능력에서 비롯됩니다.<br />우수한 창작역량과 융합기술력을 갖춘 인재를 양성하기
					위해서는<br />체계적인 교육 커리큘럼과 교육인프라가 필요합니다.
				</p>
				<br /> <br /> <br />
				<p>
					한국콘텐츠아카데미는<br />국내외 최고 강사진의 풍부한 교육 노하우, 국내 최고의 콘텐츠 제작교육 인프라를 바탕으로<br />콘텐츠
					산업 예비인력교육, 현업인 직무교육, 전략지역 해외연수 및 온라인 교육과정 등을 운영하고 있습니다.<br />이를 통해
					글로벌 인재 양성을 위한 콘텐츠 산업 공적 연수기관으로서의 역할과 기능 수행에 최선을 다하겠습니다.
				</p>
				<br /> <br /> <br /><p>여러분의 변함없는 관심과 성원을 부탁드리겠습니다.</p> <br /> <br /> <br />
				<span style="font-weight: bold">한국콘텐츠진흥원장 송성각</span>
				
				
				<div style="float: right; padding-top: 20px;">
					<img src="/images/2013/introduce/img_director.jpg"
						alt="한국콘텐츠진흥원장 송성각 사진" />
				</div>
			</div>
			<!-- //greeting -->

		</div>
		<!-- //#subCont -->

	</div>
	<!-- 컨텐츠 영역 끝 -->

</div>
<!--  //container-new 끝 //-->

<%@ include file="/learn/user/2013/portal/include/footer.html"%>