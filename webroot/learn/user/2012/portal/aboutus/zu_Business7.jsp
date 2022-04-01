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
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > 교육사업 > <u>드라마 프로듀서 스쿨</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/business.gif" alt="교육사업안내">
			</div>
		</div><br>
		<dl class="skipcontent">
			<dt>본문 바로가기</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="121" title="드라마 프로듀서 스쿨의 개요로 이동합니다">개요</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="122" title="드라마 프로듀서 스쿨의 운영방향으로 이동합니다">운영방향</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip03" tabindex="123" title="드라마 프로듀서 스쿨의 운영방법으로 이동합니다">운영방법</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip04" tabindex="124" title="드라마 프로듀서 스쿨의 교육관련 문의로 이동합니다">교육관련 문의</a></dd>
		</dl>
		<ul class="category">
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=1" tabindex="171" title="차세대 인재양성 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business01.png" alt="차세대 인재양성 교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=2" tabindex="172" title="현업인 직무 재교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business02.png" alt="산업계 직무 재교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=3" tabindex="173" title="온라인 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business03.png" alt="온라인 교육"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=4" tabindex="174" title="국제 인증과정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business04.png" alt="국제 인증과정"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=5" tabindex="175" title="3D입체 콘텐츠 전문인력 양성에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business05.png" alt="3D입체 콘텐츠 전문인력 양성"></a></li>-->
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=6" tabindex="176" title="창의인재 양성사업에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business06.png" alt="창의인재 양성사업"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=7" tabindex="177" title="드라마 프로듀서 스쿨에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business07on.png" alt="드라마 프로듀서 스쿨"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=8" tabindex="178" title="게임국가 기술자격 검정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business08.png" alt="게임국가 기술자격 검정"></a></li>-->
		</ul>
		<div class="txtpage">
			<h1>드라마 프로듀서 스쿨</h1>
			<h4>프로듀서스쿨은 콘텐츠산업의 핵심장르인 드라마의 글로벌 경쟁력 강화를 위한 창의적 드라마 프로듀서 양성을 위해 드라마제작사협회와 협력하여 운영하는 교육프로그램입니다.</h4>
			<h3 id="skip01"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="181" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>개요</h3>
			<h6>
				<p>운영목적 : 드라마의 글로벌 경쟁력 강화를 위한 창의적 드라마 프로듀서 양성</p>
				<p>교육대상 : 드라마프로듀서 분야에 재능과 관심 있는 자, 관련학과 졸업(예정)자</p>
				<p>교육운영 : 드라마프로듀서과정 2개 반 운영(각반 20명)</p>
			</h6>
			<br>
			<h3 id="skip02"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="182" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>운영방향</h3>
			<h6>
				<p>글로벌 시장 경쟁력 강화와 방송영상산업계 요구를 반영한 창의적 드라마전문 프로듀서 양성, 공급</p>
				<p>드라마콘텐츠 기획, 제작관리, 마케팅 등 드라마제작 환경 변화에 대응 가능한 글로벌 역량을 갖춘 드라마프로듀서 예비인력 양성</p>
				<p>드라마분야별 전문성을 고려하여 실무위주의 강사배치 및 단계별 전임강사 편성</p>
				<p>교육생 취업지원을 위한 드라마제작사 네트워크 운영</p>
			</h6>
			<h3 id="skip03"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="183" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>운영방법</h3>
			<h6>
				<p>
					<b>교육 신청/선발</b><br>
					- 신청방법 : 온라인 신청(<a href="http://www.dramapd.kr" tabindex="184" target="_blank" title="온라인 신청 홈페이지로 이동합니다 (새창)">www.dramapd.kr</a>)<br>
					- 선발기준 : 서류심사 및 면접심사를 통해 과정별 자격기준에 적합한 교육생 선발<br>
				</p>
				<br>
				<p>
					<b>교육과정 운영</b><br>
					- 교육방법 : 강의 및 실습교육, 제작현장 실습교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영<br>
					- 교육강사 : 현장 실무전문가, 학계 전문가 등<br>
					- 교육장소 : 한국드라마프로듀서스쿨 강의실(강서구 등촌동 KGIT 4층)
				</p>
			</h6>
			<h3 id="skip04"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="185" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>교육관련 문의</h3>
			<h6>
				<p>대표 : 02-2161-0072</p>
			</h6>
		</div>
	</div>
	<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>