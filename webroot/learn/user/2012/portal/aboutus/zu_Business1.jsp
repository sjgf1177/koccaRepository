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
	box.put("topmenu","7");
	box.put("submenu","3");
%>


<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고계신 콘텐츠의 위치입니다">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > 교육사업 > <u>차세대 인재 양성교육</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/business.gif" alt="교육사업안내">
			</div>
		</div><br>
		<!--dl class="skipcontent">
			<dt>본문 바로가기</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="121" title="차세대 핵심인력양성의 개요로 이동합니다">개요</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="122" title="차세대 핵심인력양성의 운영방향으로 이동합니다">운영방향</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip03" tabindex="123" title="차세대 핵심인력양성의 운영방법으로 이동합니다">운영방법</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip04" tabindex="124" title="차세대 핵심인력양성의 교육관련 문의로 이동합니다">교육관련 문의</a></dd>
		</dl-->
		<ul class="category">
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=1" tabindex="171" title="차세대 인재양성 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business01on.png" alt="차세대 인재양성 교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=2" tabindex="172" title="현업인 직무 재교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business02.png" alt="산업계 직무 재교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=3" tabindex="173" title="온라인 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business03.png" alt="온라인 교육"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=4" tabindex="174" title="국제 인증과정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business04.png" alt="국제 인증과정"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=5" tabindex="175" title="3D입체 콘텐츠 전문인력 양성에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business05.png" alt="3D입체 콘텐츠 전문인력 양성"></a></li>-->
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=6" tabindex="176" title="창의인재 양성사업에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business06.png" alt="창의인재 양성사업"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=7" tabindex="177" title="드라마 프로듀서 스쿨에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business07.png" alt="드라마 프로듀서 스쿨"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=8" tabindex="178" title="게임국가 기술자격 검정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business08.png" alt="게임국가 기술자격 검정"></a></li>-->
		</ul>
		<div class="txtpage">
			<h1>차세대 핵심 인력양성</h1>
			<h2>(방송영상,게임,3D)</h2>
			<h4>차세대 핵심 인력양성 교육과정은 예비취업자 대상으로 미디어 환경 변화에 맞는 융합콘텐츠 창작 역량과 글로벌 경쟁력 강화를 목적으로 게임, 방송영상, 3D 등의 창작기술 전문가 양성을 위해 운영되는 교육프로그램입니다.</h4>
			<!--h3 id="skip01"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="181" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>개요</h3>
			<h6>
				<p>운영목적 : 콘텐츠 산업의 국가 경쟁력 제고를 위한 융합콘텐츠 제작 기술과 고도 창작 역량을 갖춘 전문인 육성</p>
				<p>교육대상 : 콘텐츠 분야에 재능과 관심 있는 자, 관련학과 졸업(예정)자, 관련분야 경력자</p>
				<p>교육운영 : 6~10개월 / 장기교육 운영</p>
			</h6>
			<table class="intable" cellspacing="0" cellpadding="0" summary="분야별 교육과정 안내입니다">
				<caption>분야별 교육과정 안내</caption>
				<colgroup><col width="150"><col width="*"><col width="180"><col width="180"><col width="140"></colgroup>
				<tr>
					<th id="v_category">분야</th>
					<th id="v_course">과정명</th>
					<th id="v_schedule">교육일정</th>
					<th id="v_learning">교육기간</th>
					<th id="v_people" class="noborder">교육인원</th>
				</tr>
				<tr>
					<th name="v_category" rowspan="5" class="notweight">방송.영상</th>
					<td name="v_course">VFX (특수영상)</td>
					<td name="v_schedule">''12.3~11</td>
					<td name="v_learning">9개월</td>
					<td name="v_people">15</td>
				</tr>
				<tr>
					<td name="v_course">모션 그래픽</td>
					<td name="v_schedule">''12.3~11</td>
					<td name="v_learning">9개월</td>
					<td name="v_people">15</td>
				</tr>
				<tr>
					<td name="v_course">디지털 영상편집</td>
					<td name="v_schedule">''12.3~9/9~12</td>
					<td name="v_learning">6/4개월</td>
					<td name="v_people">30</td>
				</tr>
				<tr>
					<td name="v_course">디지털 음향제작</td>
					<td name="v_schedule">''12.3~9</td>
					<td name="v_learning">6개월</td>
					<td name="v_people">10</td>
				</tr>
				<tr>
					<td colspan="3">합 계</td>
					<td name="v_people">70</td>
				</tr>
				<tr>
					<th name="v_category" rowspan="5" class="notweight">게임</th>
					<td name="v_course">게임디자인(기획)</td>
					<td name="v_schedule">''12.3~12</td>
					<td name="v_learning">10개월</td>
					<td name="v_people">15</td>
				</tr>
				<tr>
					<td name="v_course">게임프로그램</td>
					<td name="v_schedule">''12.3~12</td>
					<td name="v_learning">10개월</td>
					<td name="v_people">13</td>
				</tr>
				<tr>
					<td name="v_course">게임그래픽</td>
					<td name="v_schedule">''12.3~12</td>
					<td name="v_learning">10개월</td>
					<td name="v_people">18</td>
				</tr>
				<tr>
					<td name="v_course">게임 프로젝트</td>
					<td name="v_schedule">''12.3~12</td>
					<td name="v_learning">10개월</td>
					<td name="v_people">19</td>
				</tr>
				<tr>
					<td colspan="3">합 계</td>
					<td>65</td>
				</tr>
				<tr>
					<th name="v_category" name="v_category" class="notweight">기획.창작</th>
					<td name="v_course">콘텐츠 기획창작</td>
					<td name="v_schedule">''12.3~12</td>
					<td name="v_learning">10개월</td>
					<td name="v_people">20</td>
				</tr>
				<tr>
					<th name="v_category" class="notweight">비즈니스</th>
					<td name="v_course">국제콘텐츠 비즈니스</td>
					<td name="v_schedule">''12.3~11</td>
					<td name="v_learning">9개월</td>
					<td name="v_people">15</td>
				</tr>
				<tr>
					<th></th>
					<td colspan="3">합 계</td>
					<td>35</td>
				</tr>
				<tr>
					<th>총 합 계</th>
					<td colspan="3"></td>
					<td>170</td>
				</tr>
			</table>
			<h3 id="skip02"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="182" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>운영방향</h3>
			<h6>
				<p>국내 최고의 교육인프라를 활용한 콘텐츠 제작 전문 교육 서비스 제공</p>
				<p>분야별 교육요구도 조사, 트렌드 분석을 통한 콘텐츠 제작 현장 중심의 교육과정 운영</p>
				<p>국내·외 유관기관과 네트웍 구축을 통한 고품위 교육서비스 제공</p>
				<p>풍부한 현장 경험과 강의 경험을 보유한 국내·외 전문 강사진을 활용 교육 서비스 제공</p>
				<p>철저한 교육 관리를 통한 교육 효과 및 교육 만족도 제고</p>
			</h6>
			<h3 id="skip03"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="183" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>운영방법</h3>
			<div class="outline">
				<table class="stepbox" cellspacing="0" cellpadding="0" summary="운영 순서입니다">
					<colgroup><col width="120"><col width="40"><col width="120"><col width="40"><col width="120"><col width="40"><col width="120"><col width="40"><col width="120"><col width="40"><col width="120"></colgroup>
					<tr>
						<td class="inbox">현업 교육요구조사</td>
						<td><img src="/images/2012/sub/page7/arrow.gif" alt="arrow"></td>
						<td class="inbox">교육과정 개발</td>
						<td><img src="/images/2012/sub/page7/arrow.gif" alt="arrow"></td>
						<td class="inbox">교육신청/선발<br>(1~2월)</td>
						<td><img src="/images/2012/sub/page7/arrow.gif" alt="arrow"></td>
						<td class="inbox">교육과정운영<br>(3~11월)</td>
						<td><img src="/images/2012/sub/page7/arrow.gif" alt="arrow"></td>
						<td class="inbox">최종교육평가<br>(12월)</td>
						<td><img src="/images/2012/sub/page7/arrow.gif" alt="arrow"></td>
						<td class="inbox">취업지원</td>
					</tr>
				</table>
				<p>교육과정별 운영방법과 일정은 일부 차이가 있습니다.</p>
			</div>
			<br>
			<h6>
				<p class="bolder">교육 신청/선발</p>
					<span>- 신청방법 : 온라인 신청 / <a href="http://edu.kocca.or.kr" tabindex="184" target="_blank" title="한국콘텐츠아카데미 바로가기 (새창)">http://edu.kocca.or.kr</a></span><br>
					<span>- 선발기준 : 서류심사 및 면접심사를 통해 과정별 자격기준에 적합한 교육생 선발</span>
					<br><br>
				<p class="bolder">교육과정 운영</p>
					<span>- 교육방법 : 강의 및 실습교육, 제작현장 실습교육 등 과정별 커리큘럼에 의한 제작 현장 중심의 과정 운영</span><br>
					<span>- 교육강사 : 현장 실무전문가, 학계 전문가 등</span><br>
					<span>- 교육시스템 및 장비 : 국내 최고의 교육 장비를 활용한 콘텐츠 제작 실무 중심의 과정 운영</span><br>
					<span>- 교육장소 : 한국콘텐츠아카데미 강의실(목동 방송회관 소재)</span>
					<br><br>
				<p class="bolder">교육 특전</p>
				<table class="intable" cellspacing="0" cellpadding="0" summary="교육 특전 안내">
				<caption>교육 특전 안내표</caption>
				<colgroup><col width="200"><col width="*"></colgroup>
					<tr>
						<th>과정명</th>
						<th class="noborder">내용</th>
					</tr>
					<tr>
						<td id="v_cate1" class="noborder">공통</td>
						<td name="v_cate1" class="leftpadding">
							- 월 316,000원 장학금 지급(비즈니스 과정 제외)<br>
							- 한국콘텐츠진흥원 교육프로그램 우선 참여 기회 제공<br>
								&nbsp;&nbsp;※ 사이버콘텐츠아카데미, 전략지역 연수과정 등<br>
							- 국내 컨퍼런스 및 전시회 참가 지원
						</td>
					</tr>
					<tr>
						<td id="v_cate2" class="noborder">기획·창작</td>
						<td name="v_cate2" class="leftpadding">- 프로젝트 완성화를 위한 전담 멘토, 전문가활용 기회 확대 제공</td>
					</tr>
					<tr>
						<td id="v_cate3" class="noborder">국제콘텐츠 비즈니스</td>
						<td name="v_cate3" class="leftpadding">- 수료 후 우수자에게 관련분야 연수, 인턴십 기회 제공</td>
					</tr>
				</table>
			</h6>
			<h3 id="skip04"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="185" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>교육관련 문의</h3>
			<h6>
				<p>대표 : 02-2161-0072</p>
			</h6--><br>
			<img src="/images/2012/sub/page7/cha_01.gif"/>
			<img src="/images/2012/sub/page7/cha_02.gif"/>
			<img src="/images/2012/sub/page7/cha_03.gif"/>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>