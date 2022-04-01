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
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > 교육사업 > <u>3D입체 콘텐츠 전문인력 양성</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/business.gif" alt="교육사업안내">
			</div>
		</div><br>
		<dl class="skipcontent">
			<dt>본문 바로가기</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="121" title="3D입체 콘텐츠 전문인력 양성의 개요로 이동합니다">개요</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="122" title="3D입체 콘텐츠 전문인력 양성의 운영방향으로 이동합니다">운영방향</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip03" tabindex="123" title="3D입체 콘텐츠 전문인력 양성의 운영방법으로 이동합니다">운영방법</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip04" tabindex="124" title="3D입체 콘텐츠 전문인력 양성의 교육관련 문의로 이동합니다">교육관련 문의</a></dd>
		</dl>
		<ul class="category">
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=1" tabindex="171" title="차세대 인재양성 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business01.png" alt="차세대 인재양성 교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=2" tabindex="172" title="현업인 직무 재교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business02.png" alt="산업계 직무 재교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=3" tabindex="173" title="온라인 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business03.png" alt="온라인 교육"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=4" tabindex="174" title="국제 인증과정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business04.png" alt="국제 인증과정"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=5" tabindex="175" title="3D입체 콘텐츠 전문인력 양성에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business05on.png" alt="3D입체 콘텐츠 전문인력 양성"></a></li>-->
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=6" tabindex="176" title="창의인재 양성사업에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business06.png" alt="창의인재 양성사업"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=7" tabindex="177" title="드라마 프로듀서 스쿨에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business07.png" alt="드라마 프로듀서 스쿨"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=8" tabindex="178" title="게임국가 기술자격 검정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business08.png" alt="게임국가 기술자격 검정"></a></li>-->
		</ul>
		<div class="txtpage">
			<h1>3D입체 콘텐츠 전문인력 양성</h1>
			<h4>3D 입체콘텐츠 전문인력양성 교육은 3D 입체콘텐츠 선진 제작기술의 조기 습득을 통해 해외 시장 진출을 선도할 리더급 제작 핵심인재를 양성하기 위한 교육프로그램입니다.</h4>
			<h3 id="skip01"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="181" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>개요</h3>
			<h6>
				<p>운영목적 : 3D 입체영상 제작 분야별 전문 인력 양성을 통한 국내 3D 콘텐츠 산업의 조기 정착 유도</p>
				<br>
				<p>
					교육대상<br>
					- S3D 프로그램 제작과정 : 콘텐츠 산업 종사자<br>
					- S3D 입체 제작기술 인재양성 : 콘텐츠 산업 종사자<br>
					- S3D 엘리트 인재양성과정 : 예비인력(졸업예정자, 미취업자, 프리랜서 등)<br><br>
				</p>
				<p>
					교육운영<br>
					- S3D 프로그램 제작과정 : 3D입체작품‘맞춤형’제작 교육 및 실습<br>
					- S3D 입체 제작기술 인재양성 : 3D기획/촬영/편집/그래픽/스테레오그래퍼<br>
					- S3D 엘리트 인재양성과정 : 팀 베이스의 프로젝트 수행을 통한 현장 제작 실습과 일자리 창출을 위한 Job-matching<br><br>
				</p>
				<p>교육과정</p>
			</h6>
			<table class="intable" cellspacing="0" cellpadding="0">
				<caption>분야별 교육과정 안내</caption>
				<colgroup><col width="250"><col width="*"><col width="180"><col width="180"></colgroup>
				<tr>
					<th>분야</th>
					<th>과정명</th>
					<th>교육기간</th>
					<th class="noborder">교육인원</th>
				</tr>
				<tr>
					<th class="notweight">S3D 프로그램 제작과정 (10개 과정)</th>
					<td>S3D 프로그램 제작과정</td>
					<td>12년 03월 ~ 13년 02월</td>
					<td>100명</td>
				</tr>
				<tr>
					<th rowspan="38" class="notweight">3D입체 제작기술 인재양성 과정 (38과정)</th>
					<td>3ALITY 리그 테크니션 과정 1차</td>
					<td>04.04 ~ 04.06</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Smoke를 활용한 3D입체 제작 과정 1차</td>
					<td>04.16 ~ 04.20</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Avid를 활용한 3D입체 제작 과정 1차</td>
					<td>04.23 ~ 04.27</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>스테레오그래퍼 과정 1차</td>
					<td>04.23 ~ 04.27</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>3D입체 특수촬영 과정</td>
					<td>05.14 ~ 05.18</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Avid를 활용한 3D입체 제작 과정 2차</td>
					<td>05.21 ~ 05.25</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>P+S Technik 리그 테크니션 과정 1차</td>
					<td>05.29 ~ 05.31</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>3D입체 Quasar 리그 테크니션 1차</td>
					<td>06.11 ~ 06.15</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Cinema4D를 활용한 3D입체 제작 과정 1차</td>
					<td>06.18 ~ 06.22</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>3D입체 수중촬영 과정</td>
					<td>06.25 ~ 06.29</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Avid를 활용한 3D입체 제작 과정 3차</td>
					<td>06.28 ~ 07.06</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>3D입체 교수워크숍</td>
					<td>07.04 ~ 07.06</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>3ALITY 리그 테크니션 과정 2차</td>
					<td>07.09 ~ 07.13</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Smoke를 활용한 3D입체 제작 과정 2차</td>
					<td>07.16 ~ 07.20</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>3D입체 Quasar 리그 테크니션 2차</td>
					<td>07.16 ~ 07.20</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Nuke를 활용한 3D입체 컴포지팅 과정 1차</td>
					<td>07.23 ~ 07.27</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Avid를 활용한 3D입체 제작 과정 4차</td>
					<td>07.30 ~ 08.03</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Nuke를 활용한 3D입체 컴포지팅 과정 2차</td>
					<td>08.06 ~ 08.10</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>DSLR을 활용한 3D입체 촬영 과정</td>
					<td>08.20 ~ 08.24</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Avid를 활용한 3D입체 제작 과정 5차</td>
					<td>08.27 ~ 08.31</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>After Effect를 활용한 3D입체 모션그래픽 1차</td>
					<td>09.03 ~ 09.07</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Nuke를 활용한 3D입체 컴포지팅 과정 3차</td>
					<td>09.10 ~ 09.14</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>스테레오그래퍼 과정 2차</td>
					<td>09.17 ~ 09.21</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>스테디캠 촬영 과정</td>
					<td>09.24 ~ 09.28</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>3D입체 드라마 제작 과정</td>
					<td>09.26 ~ 09.28</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>3D입체 영화 제작 과정 3차</td>
					<td>10.08 ~ 10.12</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>3D입체 다큐멘터리 제작 과정</td>
					<td>10.08 ~ 10.12</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Nuke를 활용한 3D입체 컴포지팅 과정 4차</td>
					<td>10.07 ~ 10.14</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>해외 워크샵 - 뉴질랜드 WETA(반지의제왕3D)</td>
					<td>10.15 ~ 10.19</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Smoke를 활용한 3D입체 제작 과정 3차</td>
					<td>10.22 ~ 10.26</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>3D입체 항공 촬영 과정</td>
					<td>10.29 ~ 10.31</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>P+S Technik리그 테크니션 2차</td>
					<td>10.29 ~ 10.31</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>After Effect를 활용한 3D입체 모션그래픽 2차</td>
					<td>10.29 ~ 10.31</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>3D입체 촬영 과정</td>
					<td>11.07 ~ 11.9</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Nuke를 활용한 3D입체 컴포지팅 과정 5차</td>
					<td>11.12 ~ 11.16</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Avid를 활용한 3D입체 제작 과정 6차</td>
					<td>11.19 ~ 11.23</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Frame Forge를 활용한 3D입체 제작 과정</td>
					<td>12.03 ~ 12.07</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>Avid를 활용한 3D입체 제작 과정 7차</td>
					<td>12.17 ~ 12.21</td>
					<td>10명</td>
				</tr>
				<tr>
					<th rowspan="3" class="notweight">S3D 엘리트 인재양성 과정 (3개 과정)</th>
					<td>S3D Pre-Production 과정</td>
					<td>03월 ~ 12월</td>
					<td>10명</td>
				</tr>
				<tr>
					<td>S3D Production 과정</td>
					<td>03월 ~ 12월</td>
					<td>15명</td>
				</tr>
				<tr>
					<td>S3D Post-Production 과정</td>
					<td>03월 ~ 12월</td>
					<td>20명</td>
				</tr>
				<tr>
					<th rowspan="3" class="notweight">해외초청 워크숍 (3회)</th>
					<td>서울국제3D페어입체가이드라인세미나/비즈매칭</td>
					<td>05.15 ~ 05.18</td>
					<td>100명</td>
				</tr>
				<tr>
					<td>VFX슈퍼바이저초청워크숍</td>
					<td>09.10 ~ 09.14</td>
					<td>100명</td>
				</tr>
				<tr>
					<td>3D DIRECTOR워크숍</td>
					<td>11.05 ~ 11.09</td>
					<td>20명</td>
				</tr>
				<tr>
					<th class="notweight">지역연계 교육과정 (10회)</th>
					<td>지역 대학 및 진흥원 연계 교육(10회)</td>
					<td>12년 07월 ~ 12년 09월<br>12년 12월 ~ 13년 02월</td>
					<td>200명</td>
				</tr>
				<tr>
					<td colspan="3" class="noborder">합 계</td>
					<td>1,025명</td>
				</tr>
			</table>
			<br>
			<h3 id="skip02"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="182" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>운영방향</h3>
			<h6>
				<p>3D 입체영상 제작 분야별 단기 실무 실습 과정 운영</p>
				<p>국내 3D 프로젝트 중심으로 ‘맞춤형’컨설팅 제작 교육진행</p>
				<p>풍부한 현장 경험과 강의 경험을 보유한 국내·외 전문 강사진을 활용 교육 서비스 제공</p>
				<p>철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고</p>
			</h6>
			<h3 id="skip03"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="183" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>운영방법</h3>
			<h6>
				<p class="bolder">교육 신청/선발</p>
					<span>- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr" tabindex="184" target="_blank" title="한국콘텐츠아카데미 바로가기 (새창)">http://edu.kocca.or.kr</a></span><br>
					<span>
						- 선발기준 : 각 과정별 참가자격 기준 적합성 여부, 교육신청 선착순, 소속기관별 인원 안배 등
						<br>※ 중기교육과정은 수시로 맞춤형 교육으로 추후 별도 신청/선발기준 수립</span>
					<br><br>
				<p class="bolder">교육과정 운영</p>
					<span>- 교육방법 : 강의 및 실습교육, 제작현장 실습 교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영</span><br>
					<span>- 교육강사 : 국 내외 현장실무전문가, 학계전문가 등</span><br>
					<span>- 교육시스템 및 장비 : 국내 최고의 교육 장비를 활용한 콘텐츠 제작 실무 중심의 과정 운영</span><br>
					<span>- 교육장소 : 한국콘텐츠아카데미 강의실(목동 방송회관 소재)</span>
			</h6>
			<h3 id="skip04"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="185" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>교육관련 문의</h3>
			<h6>
				<p>대표 : 02-2161-0072</p>
			</h6>
		</div>
	</div>
	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>