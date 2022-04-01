<%
//**********************************************************
//  1. 제      목: 개인정보취급방침
//  2. 프로그램명 : zu_Personal.jsp
//  3. 개      요: 개인정보취급방침
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>

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
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > 교육사업 > <u>국제 인증과정</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/business.gif" alt="교육사업안내">
			</div>
		</div><br>
		<ul class="category">
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=1" tabindex="171" title="차세대 인재양성 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business01.png" alt="차세대 인재양성 교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=2" tabindex="172" title="현업인 직무 재교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business02.png" alt="산업계 직무 재교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=3" tabindex="173" title="온라인 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business03.png" alt="온라인 교육"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=4" tabindex="174" title="국제 인증과정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business04on.png" alt="국제 인증과정"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=5" tabindex="175" title="3D입체 콘텐츠 전문인력 양성에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business05.png" alt="3D입체 콘텐츠 전문인력 양성"></a></li>-->
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=6" tabindex="176" title="창의인재 양성사업에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business06.png" alt="창의인재 양성사업"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=7" tabindex="177" title="드라마 프로듀서 스쿨에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business07.png" alt="드라마 프로듀서 스쿨"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=8" tabindex="178" title="게임국가 기술자격 검정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business08.png" alt="게임국가 기술자격 검정"></a></li>-->
		</ul>
		<div class="txtpage">
			<h1>국제 인증과정</h1>
			<h4>국제자격인증센터는 미국의 Apple社, Avid社, Digidesign社, 영국 Quantel 社 등 해외 유관기관과의 파트너쉽을 구축하여 디지털 Video/Audio 분야, HD촬영 등 5개 분야의 국제 자격인증센터를 운영하며 국제 규격과 교육 체계에 맞는 과정운영을 통해 글로벌 경쟁력을 갖춘 디지털 방송전문 인력을 양성하고 있습니다.</h4>
			<br>
			<table class="intable" cellspacing="0" cellpadding="0">
				<caption>국제인증센터 안내</caption>
				<colgroup><col width="280"><col width="*"><col width="220"></colgroup>
				<tr>
					<th>국제인증센터</th>
					<th>운영현황</th>
					<th class="noborder">비고</th>
				</tr>
				<tr>
					<th rowspan="2" class="notweight">Avid Authorized Training Partner(AATP)</th>
					<td class="leftpadding">
						- Avid 국제공인 교재, Instructor에 의한 국제 규격의 영상편집 교육<br>
						- 과정 수료 후 희망자에 한해 테스트를 거쳐 국제 Cerificate 취득 가능<br>
						- 운영과정: Media Composer 101, 201, 202, 203, 205, 220, 324
					</td>
					<td rowspan="2" class="leftpadding">Avid(美)<br>Avid 공인 국제인증센터 개소(2008년)<br>Pro school 개소(2003)</td>
				</tr>
				<tr>
					<td class="leftpadding">
						- Avid 제공인 교재, Instructor에 의한 국제 규격의 오디오 교육<br>
						- 과정 수료 테스트를 거쳐 국제 Certificate취득 가능<br>
						- 운영과정: Pro Tools 101, 110, 201, 210M, 210P
					</td>
				</tr>
				<tr>
					<th class="notweight">Apple Authorized Training Center(AATC)</th>
					<td class="leftpadding">
						- Apple 국제공인 교재, Instructor에 의한 국제 규격의 Final Cut Pro 교육<br>
						- 과정 수료 후 희망자에 한해 테스트를 거쳐 국제 Certificate취득가능<br>
						- 운영과정: Final Cut Pro LevelⅠ,Ⅱ
					</td>
					<td class="leftpadding">Apple(美)<br>Apple공인 국제인증센터 개소(2007년)</td>
				</tr>
			</table>
		</div>
	</div>
	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>