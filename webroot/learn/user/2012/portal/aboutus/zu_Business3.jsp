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
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > 교육사업 > <u>온라인교육</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/business.gif" alt="교육사업안내">
			</div>
		</div><br>
		<!--dl class="skipcontent">
			<dt>본문 바로가기</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="121" title="사이버콘텐츠아카데미 운영의 개요로 이동합니다">개요</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="122" title="사이버콘텐츠아카데미 운영의 운영방향으로 이동합니다">운영방향</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip03" tabindex="123" title="사이버콘텐츠아카데미 운영의 운영방법으로 이동합니다">운영방법</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip04" tabindex="124" title="사이버콘텐츠아카데미 운영의 교육관련 문의로 이동합니다">교육관련 문의</a></dd>
		</dl-->
		<ul class="category">
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=1" tabindex="171" title="차세대 인재양성 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business01.png" alt="차세대 인재양성 교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=2" tabindex="172" title="현업인 직무 재교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business02.png" alt="산업계 직무 재교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=3" tabindex="173" title="온라인 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business03on.png" alt="온라인 교육"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=4" tabindex="174" title="국제 인증과정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business04.png" alt="국제 인증과정"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=5" tabindex="175" title="3D입체 콘텐츠 전문인력 양성에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business05.png" alt="3D입체 콘텐츠 전문인력 양성"></a></li>-->
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=6" tabindex="176" title="창의인재 양성사업에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business06.png" alt="창의인재 양성사업"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=7" tabindex="177" title="드라마 프로듀서 스쿨에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business07.png" alt="드라마 프로듀서 스쿨"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=8" tabindex="178" title="게임국가 기술자격 검정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business08.png" alt="게임국가 기술자격 검정"></a></li>-->
		</ul>
		<div class="txtpage">
			<h1>사이버콘텐츠아카데미 운영</h1>
			<h4>온라인교육과정은 방송영상/게임/문화콘텐츠 기획, 제작, 경영, 비즈니스 분야의 우수 교육 콘텐츠를 개발하여 콘텐츠산업 신규 진입자와 종사자들에게 열린 교육과 평생교육을 위해 운영되는 교육프로그램입니다.</h4>
			<!--h3 id="skip01"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="181" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>개요</h3>
			<h6>
				<p>운영목적 : 문화콘텐츠산업 종사자의 직무능력향상과 인력양성의 저변확대</p>
				<p>교육대상 : 콘텐츠 산업 종사자, 콘텐츠관련학과 학생 및 일반인 등</p>
				<p>교육운영 : 136개 과정 매월 1회 개강</p>
			</h6>
			<br>
			<h3 id="skip02"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="182" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>운영방향</h3>
			<h6>
				<p>콘텐츠 산업계 교육요구도 조사를 통한 현장 중심의 온라인 교육 콘텐츠개발</p>
				<p>융합미디어 환경변화에 대응 가능한 온라인교육시스템 운영</p>
				<p>과정별 전문 온라인 튜터를 활용한 1:1 맞춤식 학습관리 운영</p>
				<p> 철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고</p>
			</h6>
			<h3 id="skip03"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="183" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>운영방법</h3>
			<h6>
				<p class="bolder">교육 신청/선발</p>
					<span>- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr" tabindex="184" target="_blank" title="한국콘텐츠아카데미 바로가기 (새창)">http://edu.kocca.or.kr</a></span><br>
					<span>- 선발기준 : 교육신청 선착순</span>
					<br><br>
				<p class="bolder">교육과정 운영</p>
					<span>- 교육방법 : 학습자 개인 PC에서 온라인 학습</span><br>
					<span>- 과정운영 : 매월 1회 개강(1일)</span><br>
					<span>- 운영내용 : 방송영상/게임/문화콘텐츠 기획, 제작, 경영비즈니스 분야 등 136개 과정 운영</span><br>
					<span>- 학습기간 : 학습 1개월, 복습 3개월</span>
					<span>- 교 육 비 : 무료</span>
					<span>- 교육강사 : 콘텐츠 산업 분야 전문가, 학계전문가</span>
			</h6>
			<h3 id="skip04"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="185" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>교육관련 문의</h3>
			<h6>
				<p>박경은 주임 (02-3219-6541, <a href="mailto:pke329@kocca.kr" tabindex="186" title="pke329@kocca.kr로 보내는 메일을 작성합니다">pke329@kocca.kr</a>)</p>
				<p>조나연 대리 (02-2161-0077, <a href="mailto:edu_kocca@naver.com" tabindex="186" title="edu_kocca@naver.com로 보내는 메일을 작성합니다">edu_kocca@naver.com</a>)</p>
			</h6-->
			<br/>
			<img src="/images/2012/sub/page7/cyberonline.gif"/>
			<img src="/images/2012/sub/page7/cyberonline_02.gif"/>
		</div>
	</div>
	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>