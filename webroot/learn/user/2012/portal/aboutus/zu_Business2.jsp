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
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > 교육사업 > <u>현업인 직무 재교육</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/business.gif" alt="교육사업안내">
			</div>
		</div><br>
		<!--dl class="skipcontent">
			<dt>본문 바로가기</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="121" title="산업계 직무 재교육과정의 개요로 이동합니다">개요</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="122" title="산업계 직무 재교육과정의 운영방향으로 이동합니다">운영방향</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip03" tabindex="123" title="산업계 직무 재교육과정의 운영방법으로 이동합니다">운영방법</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip04" tabindex="124" title="산업계 직무 재교육과정의 교육관련 문의로 이동합니다">교육관련 문의</a></dd>
		</dl-->
		<ul class="category">
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=1" tabindex="171" title="차세대 인재양성 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business01.png" alt="차세대 인재양성 교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=2" tabindex="172" title="현업인 직무 재교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business02on.png" alt="산업계 직무 재교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=3" tabindex="173" title="온라인 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business03.png" alt="온라인 교육"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=4" tabindex="174" title="국제 인증과정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business04.png" alt="국제 인증과정"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=5" tabindex="175" title="3D입체 콘텐츠 전문인력 양성에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business05.png" alt="3D입체 콘텐츠 전문인력 양성"></a></li>-->
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=6" tabindex="176" title="창의인재 양성사업에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business06.png" alt="창의인재 양성사업"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=7" tabindex="177" title="드라마 프로듀서 스쿨에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business07.png" alt="드라마 프로듀서 스쿨"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=8" tabindex="178" title="게임국가 기술자격 검정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business08.png" alt="게임국가 기술자격 검정"></a></li>-->
		</ul>
		<div class="txtpage">
			<h1>현업인 직무 재교육과정</h1>
			<h4>현업인 직무 재교육과정은 콘텐츠 산업계 재직자를 대상으로 융합콘텐츠 창작 역량 및 글로벌 경쟁력 강화를 목적으로 창작기술 전문가 양성을 위해 운영되는 교육프로그램입니다.</h4>
			<!--h3 id="skip01"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="181" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>개요</h3>
			<h6>
				<p>운영목적 : 콘텐츠 산업의 국가 경쟁력 제고를 위한 융합콘텐츠 제작 기술과 창작 역량을 갖춘 전문인 육성</p>
				<p>대상 : 현업인 대상의 콘텐츠 분야 종사자</p>
				<p>교육운영 : 1-10일 / 단기 교육 운영</p>
			</h6>
			<table class="intable" cellspacing="0" cellpadding="0" summary="분야별 교육과정 안내입니다">
				<caption>분야별 교육과정 안내</caption>
				<colgroup><col width="250"><col width="*"><col width="180"><col width="180"></colgroup>
				<tr>
					<th>분야</th>
					<th>과정명</th>
					<th>교육기간</th>
					<th class="noborder">교육인원</th>
				</tr>
				<tr>
					<th rowspan="12" class="notweight">국가인적 자원개발 중소기업<br>컨소시엄 직무과정 (노동부)</th>
					<td>Final Cut Pro과정</td>
					<td>'12.01.09~01.13</td>
					<td>5</td>
				</tr>
				<tr>
					<td>iPhone 제작과정</td>
					<td>'12.01.09~01.13</td>
					<td>15</td>
				</tr>
				<tr>
					<td>콘텐츠 기획서 작성 전략 과정</td>
					<td>'12.02.20~2.21</td>
					<td>15</td>
				</tr>
				<tr>
					<td>비즈니스 스킬업 과정</td>
					<td>'12.02.1~2.2</td>
					<td>5</td>
				</tr>
				<tr>
					<td>Protools 강사과정</td>
					<td>'12.02.13~02.17</td>
					<td>5</td>
				</tr>
				<tr>
					<td>디지털미디어 워크플로우 과정</td>
					<td>'12.03.12~03.14</td>
					<td>5</td>
				</tr>
				<tr>
					<td>레벨 디자인 방법론 과정</td>
					<td>'12.04.4~04.6</td>
					<td>10</td>
				</tr>
				<tr>
					<td>콘텐츠기획(영화,드라마,웹툰,애니,공연)과정</td>
					<td>'12.05.8~05.10</td>
					<td>10</td>
				</tr>
				<tr>
					<td>Avid MC 101과정</td>
					<td>'12.02.6~2.10</td>
					<td>5</td>
				</tr>
				<tr>
					<td>Final Cut Pro과정</td>
					<td>'12.02.20~2.24</td>
					<td>5</td>
				</tr>
				<tr>
					<td>상표권, 저작권, 라이센싱 계약 사례 과정</td>
					<td>'12.06.11~06.15</td>
					<td>10</td>
				</tr>
				<tr>
					<td colspan="2">합 계</td>
					<td>90</td>
				</tr>
				<tr>
					<th rowspan="7" class="notweight">문화콘텐츠 직무과정</th>
					<td>창의적 콘텐츠 소재발견을 위한 세미나</td>
					<td>‘12.05.16~05.17</td>
					<td>15</td>
				</tr>
				<tr>
					<td>다문화 콘텐츠 제작의 이해</td>
					<td>5월 중</td>
					<td>15</td>
				</tr>
				<tr>
					<td>국제다큐멘터리 공동제작 가이드</td>
					<td>‘12.06.19~06.20</td>
					<td>20</td>
				</tr>
				<tr>
					<td>OSMU를 위한 앱 창작과정</td>
					<td>‘12.09.3~10.12</td>
					<td>10</td>
				</tr>
				<tr>
					<td>공연예술, 기획에서 수출까지</td>
					<td>‘12.10.16.~10.17</td>
					<td>20</td>
				</tr>
				<tr>
					<td>청소년을 위한 창의과정 해피미디어 세상</td>
					<td>10월 중</td>
					<td>15</td>
				</tr>
				<tr>
					<td colspan="2">합 계</td>
					<td>95</td>
				</tr>
			</table>
			<br>
			<h6>※ 상기과정은 변동될 수 있으며 교육내용과 교육일정 등은 한국콘텐츠아카데미(<a href="http://edu.kocca.or.kr" tabindex="182" target="_blank" title="한국콘텐츠아카데미 홈페이지로 이동합니다">http://edu.kocca.or.kr</a>)을 참조하시기 바랍니다.</h6>
			<h3 id="skip02"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="183" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>운영방향</h3>
			<h6>
				<p>국내 최고의 교육인프라를 활용한 콘텐츠 제작 전문 교육 서비스 제공</p>
				<p>분야별 교육요구도 조사, 트렌드 분석을 통한 콘텐츠 제작 현장 중심의 교육과정 운영</p>
				<p>국내·외 유관기관과 네트웍 구축을 통한 고품위 교육서비스 제공</p>
				<p>풍부한 현장 경험과 강의 경험을 보유한 국내·외 전문 강사진을 활용 교육 서비스 제공</p>
				<p>철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고</p>
			</h6>
			<h3 id="skip03"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="184" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>운영방법</h3>
			<h6>
				<p class="bolder">교육 신청/선발</p>
					<span>- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr" tabindex="185" target="_blank" title="한국콘텐츠아카데미 바로가기 (새창)">http://edu.kocca.or.kr</a></span><br>
					<span>- 선발기준 : 각 과정별 참가자격 기준 적합성 여부, 교육신청 선착순, 소속기관별 인원 안배 등</span>
					<br><br>
				<p class="bolder">교육과정 운영</p>
					<span>- 교육방법 : 강의 및 실습교육, 제작현장 실습교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영</span><br>
					<span>- 교육강사 : 현장 실무전문가, 학계 전문가 등</span><br>
					<span>- 교육시스템 및 장비 : 국내 최고의 교육 장비를 활용한 콘텐츠 제작 실무 중심의 과정 운영</span><br>
					<span>- 교육장소 : 한국콘텐츠아카데미 강의실(목동 방송회관 소재)</span>
			</h6>
			<h3 id="skip04"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="186" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>교육관련 문의</h3>
			<h6>
				<p>대표 : 02-2161-0072</p>
			</h6-->
			<br/>
			<img src="/images/2012/sub/page7/hyunup_01.gif"/>
			<img src="/images/2012/sub/page7/hyunup_02.gif"/>
			<h6>※ 상기과정은 변동될 수 있으며 교육내용과 교육일정 등은 한국콘텐츠아카데미(<a href="http://edu.kocca.or.kr" tabindex="182" target="_blank" title="한국콘텐츠아카데미 홈페이지로 이동합니다">http://edu.kocca.or.kr</a>)을 참조하시기 바랍니다.</h6>
			<img src="/images/2012/sub/page7/hyunup_03.gif"/>
		</div>
	</div>
	<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>