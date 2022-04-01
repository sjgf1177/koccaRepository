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
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > 교육사업 > <u>게임국가기술자격검정</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/business.gif" alt="교육사업안내">
			</div>
		</div><br>
		<dl class="skipcontent">
			<dt>본문 바로가기</dt>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip01" tabindex="121" title="게임국가기술자격검정의 자격 검정 시행 종목으로 이동합니다">자격 검정 시행 종목</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip02" tabindex="122" title="게임국가기술자격검정의 자격 검정 시행으로 이동합니다">자격 검정 시행</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip03" tabindex="123" title="게임국가기술자격검정의 시험신청으로 이동합니다">시험신청</a></dd>
			<dd><img src="/images/2012/common/icon_dot.png" align="middle" alt="구분점"><a href="#skip04" tabindex="124" title="게임국가기술자격검정의 교육관련 문의로 이동합니다">교육관련 문의</a></dd>
		</dl>
		<ul class="category">
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=1" tabindex="171" title="차세대 인재양성 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business01.png" alt="차세대 인재양성 교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=2" tabindex="172" title="현업인 직무 재교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business02.png" alt="산업계 직무 재교육"></a></li>
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=3" tabindex="173" title="온라인 교육에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business03.png" alt="온라인 교육"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=4" tabindex="174" title="국제 인증과정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business04.png" alt="국제 인증과정"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=5" tabindex="175" title="3D입체 콘텐츠 전문인력 양성에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business05.png" alt="3D입체 콘텐츠 전문인력 양성"></a></li>-->
			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=6" tabindex="176" title="창의인재 양성사업에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business06.png" alt="창의인재 양성사업"></a></li>
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=7" tabindex="177" title="드라마 프로듀서 스쿨에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business07.png" alt="드라마 프로듀서 스쿨"></a></li>-->
<!--			<li><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=edu&p_gubun=8" tabindex="178" title="게임국가 기술자격 검정에 대해 소개합니다"><img src="/images/2012/sub/page7/tabmenu/business08on.png" alt="게임국가 기술자격 검정"></a></li>-->
		</ul>
		<div class="txtpage">
			<h1>게임국가기술자격검정</h1>
			<h4>게임산업발전에 따른 환경변화와 산업계의 인력수요에 부응하는 우수한 게임인력을 배출하고자 게임기획전문가, 게임그래픽전문가, 게임프로그래밍전문가 국가기술자격검정을 실시하고 있습니다.</h4>
			<h3 id="skip01"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="181" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>자격 검정 시행 종목</h3>
			<h6>
				<p>게임기획전문가, 게임그래픽전문가, 게임프로그래밍전문가</p>
			</h6>
			<h3 id="skip02"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="182" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>자격 검정 시행</h3>
			<h6>
				<p>
					전국 7개 지역 검정장에서 연 4회(필기 2회, 실기 2회) 검정 시행<br>
					※ 검정지역 : 서울, 경기, 대전, 대구, 부산, 전주, 제주 (지역별 응시인원에 따라 변경, 취소 가능)
				</p>
			</h6>
			<table class="intable" cellspacing="0" cellpadding="0">
				<caption>자격검정일정안내</caption>
				<colgroup><col width="80"><col width="*"><col width="100"><col width="160"><col width="180"><col width="100"><col width="160"></colgroup>
				<tr>
					<th>회차</th>
					<th>필기시험 원서접수</th>
					<th>필기시험</th>
					<th>필기시험 합격발표</th>
					<th>실기시험 원서접수</th>
					<th>실기시험</th>
					<th class="noborder">실기시험 합격발표</th>
				</tr>
				<tr>
					<th class="notweight">1회</th>
					<td>4. 16 ~ 4. 20</td>
					<td>5. 13</td>
					<td>6. 4</td>
					<td>6. 4 ~ 6. 8</td>
					<td>7. 1</td>
					<td>8. 6</td>
				</tr>
				<tr>
					<th class="notweight">2회</th>
					<td>8. 6 ~ 8. 10</td>
					<td>9. 2</td>
					<td>9. 24</td>
					<td>9. 24 ~ 9. 28</td>
					<td>10.28</td>
					<td>11.26</td>
				</tr>
			</table>
			<br><br>
			<h6>&nbsp; ※ 종목별 검정 현황(2011년 기준)</h6>
			<table class="intable" cellspacing="0" cellpadding="0">
				<caption>종목별 검정 현황 (2011년 기준) (단위 : 명)</caption>
				<tr>
					<th colspan="2" rowspan="2">구분</th>
					<th colspan="3">게임기획</th>
					<th colspan="3">게임그래픽</th>
					<th colspan="3">게임프로그래밍</th>
					<th colspan="3" class="noborder">합계</th>
				</tr>
				<tr>
					<td>응시</td>
					<td>합격</td>
					<td>합격률</td>
					<td>응시</td>
					<td>합격</td>
					<td>합격률</td>
					<td>응시</td>
					<td>합격</td>
					<td>합격률</td>
					<td>응시</td>
					<td>합격</td>
					<td>합격률</td>
				</tr>
				<tr>
					<th rowspan="2" class="notweight">필기</th>
					<td>1회</td>
					<td>477</td>
					<td>366</td>
					<td>76.7%</td>
					<td>263</td>
					<td>125</td>
					<td>47.5%</td>
					<td>169</td>
					<td>51</td>
					<td>30.2%</td>
					<td>909</td>
					<td>542</td>
					<td>59.6%</td>
				</tr>
				<tr>
					<td>2회</td>
					<td>167</td>
					<td>115</td>
					<td>68.9%</td>
					<td>239</td>
					<td>72</td>
					<td>30.1%</td>
					<td>121</td>
					<td>19</td>
					<td>15.7%</td>
					<td>527</td>
					<td>206</td>
					<td>39.1%</td>
				</tr>
				<tr>
					<th rowspan="2" class="notweight">실기</th>
					<td>1회</td>
					<td>272</td>
					<td>76</td>
					<td>27.9%</td>
					<td>174</td>
					<td>60</td>
					<td>34.5%</td>
					<td>58</td>
					<td>15</td>
					<td>25.9%</td>
					<td>504</td>
					<td>151</td>
					<td>30.0%</td>
				</tr>
				<tr>
					<td>2회</td>
					<td>196</td>
					<td>58</td>
					<td>29.6%</td>
					<td>145</td>
					<td>40</td>
					<td>27.4%</td>
					<td>32</td>
					<td>7</td>
					<td>21.9%</td>
					<td>373</td>
					<td>105</td>
					<td>28.1%</td>
				</tr>
			</table>
			<h3 id="skip03"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="183" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>시험신청</h3>
			<h6>
				<p>온라인 신청 / <a href="http://www.kgq.or.kr" tabindex="184" target="_blank" title="한국콘텐츠아카데미 바로가기 (새창)">http://www.kgq.or.kr</a></p>
			</h6>
			<h3 id="skip04"><p><img src="/images/2012/common/icon/icon_skip_up.gif" alt="상위로 돌아가기"><a href="#" tabindex="185" title="현재 화면위치를 가장 상위로 올립니다">상위로 돌아가기</a></p>교육관련 문의</h3>
			<h6>
				<p>대표 : 02-3153-1462</p>
			</h6>
		</div>
	</div>
	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>