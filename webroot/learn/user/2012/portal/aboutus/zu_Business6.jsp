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
	box.put("topmenu","7");
	box.put("submenu","3");
%>


<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고계신 콘텐츠의 위치입니다">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > 교육사업 > <u>창의인재 양성사업</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/business.gif" alt="교육사업안내">
			</div>
		</div><br>
		<dl class="skipcontent">
			<dt>본문 바로가기</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="121" title="창의인재 양성사업의 개요로 이동합니다">개요</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="122" title="창의인재 양성사업의 운영방향으로 이동합니다">운영방향</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip03" tabindex="123" title="창의인재 양성사업의 운영방법으로 이동합니다">운영방법</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip04" tabindex="124" title="창의인재 양성사업의 교육관련 문의로 이동합니다">교육관련 문의</a></dd>
		</dl>
		<ul class="category">
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=1" tabindex="171" title="차세대 인재양성 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business01.png" alt="차세대 인재양성 교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=2" tabindex="172" title="현업인 직무 재교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business02.png" alt="산업계 직무 재교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=3" tabindex="173" title="온라인 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business03.png" alt="온라인 교육"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=4" tabindex="174" title="국제 인증과정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business04.png" alt="국제 인증과정"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=5" tabindex="175" title="3D입체 콘텐츠 전문인력 양성에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business05.png" alt="3D입체 콘텐츠 전문인력 양성"></a></li>-->
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=6" tabindex="176" title="창의인재 양성사업에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business06on.png" alt="창의인재 양성사업"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=7" tabindex="177" title="드라마 프로듀서 스쿨에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business07.png" alt="드라마 프로듀서 스쿨"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=8" tabindex="178" title="게임국가 기술자격 검정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business08.png" alt="게임국가 기술자격 검정"></a></li>-->
		</ul>
		<div class="txtpage">
			<h1>콘텐츠 창의인재 동반사업</h1>
			<h4>&lt;콘텐츠 창의인재 동반사업&gt;은 한국콘텐츠진흥원이 미래를 꿈꾸는 젊은 창작자를 키우기 위해, 다양한 분야의 멘토들과 함께 현장 밀착형 숙성 교육을 제공하는 프로그램입니다.<br>
			콘텐츠 창조분야의 전문가(멘토)와 교육생을 매칭하여, 장기간의 도제식 교육과 견습창작의 장을 제공하며, 플랫폼 기관의 인턴쉽 과정에 참여하여, 매월 약 100만원(4대보험 포함)의 교육지원비를 지급받습니다.</h4>
			<!--h3 id="skip01"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="181" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>개요</h3>
			<h6>
				<p>사업 및 교육 대상 : 창작의 소질과 소양을 갖춘 예비취업자 및 콘텐츠 관련 기관</p>
				<p>사업형태 : 지원사업</p>
			</h6>
			<br>
			<h3 id="skip02"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="182" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>운영방향</h3>
			<h6>
				<p>
					<b>창의숙성과정</b><br>
					 - 콘텐츠분야 창의숙성과정 운영이 가능한 학교와 기업을‘플랫폼기관’으로 선정하여 운영<br>
			     - 현장 경력 10년 이상의 전문가를 창의숙성과정 ‘멘토’로 발굴해 개별 교육 실시<br>
			     - 플랫폼기관 선정 → 멘토 확정 → 교육생 모집 및 선정 → 프로젝트 교육 진행 (10개월 내외)<br>
			        &nbsp; ※ 교육생 모집은 플랫폼기관을 통해 이루어지며, 모집일정은 진흥원 홈페이지와 아카데미 홈페이지 공고 예정 (3~4월)<br>
			     - 선정 교육생 대상으로 프로젝트 기간 중 인턴십 비용 지급
				</p>
				<br>
				<p>
					<b>온라인커뮤니티‘스마트월드’구축 및 교육운영</b><br>
					- 창의숙성과정에 참여하는 멘토 및 교육생 DB구축 및 커뮤니티 공간 마련<br>
					- 사업참여자 이력,포트폴리오,결과물의 효율적 관리 및 정기 교육 실시 등의 사업지원 시스템 구축<br>
				</p>
			</h6>
			<h3 id="skip03"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="183" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>운영방법</h3>
			<h6>
				<p><b>교육 신청/선발</b><br>
					- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr" tabindex="184" target="_blank" title="한국콘텐츠아카데미 바로가기 (새창)">http://edu.kocca.or.kr</a><br>
					- 선발기준 : 각 과정별 참가자격 기준 적합성 여부, 교육신청 선착순, 소속기관별 인원 안배 등<br>
				</p>
				<br>
				<p><b>교육과정 운영</b><br>
					- 교육방법 : 강의 및 실습교육, 제작현장 실습교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영<br>
					- 교육강사 : 현장 실무전문가, 학계 전문가 등<br>
					- 교육시스템 및 장비 : 국내 최고의 교육 장비를 활용한 콘텐츠 제작 실무 중심의 과정 운영<br>
					- 교육장소 : 한국콘텐츠아카데미 강의실(목동 방송회관 소재)<br>
				</p>
			</h6>
			<h3 id="skip04"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="185" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>교육관련 문의</h3>
			<h6>
				<p>대표 : 02-2161-0072</p>
			</h6-->
			<br>
			<img src="/images/2012/sub/page7/chang.gif"/>
		</div>
	</div>
	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>