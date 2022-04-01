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
	box.put("submenu","2");
%>


<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
	<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0" summary="현재 보고계신 콘텐츠의 위치입니다">
			<tr>
				<td class="pagenaviR"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 아카데미 소개 > <u>아카데미 소개</u></td>
			</tr>
		</table>
		<div class="concept">
			<div>
				<img src="/images/2012/sub/page7/title/vision.gif" alt="아카데미 소개">
			</div>
		</div>
		<div class="allimagepage">
<!--			<img src="/images/2012/sub/page7/vision_01.gif" alt="국가비전 : 세계 5대 콘텐츠 강국 실현 - 시장규모 100조원, 수출규모 78억불, 고용규모 100만명">-->
<!--			<img src="/images/2012/sub/page7/vision_02.gif" alt="진흥원 비전 : 창조경제를 선도하는 글로벌 콘텐츠 리더">-->
<!--			<img src="/images/2012/sub/page7/vision_03.gif" alt="목표 : 장르 특성화 융합시너지 지향 일자리 창출 - 융합형 콘텐츠 신시장 창출, 대한민국 대중음악 글로벌화, 세계 5대 캐릭터강국, 2012년 3대 게임강국 진입, 2012 디지털 방송영상 BIG6 진입, 세계 애니메이션 강국 실현, 세계시장을 선도하는 한국만화">-->
<!--			<img src="/images/2012/sub/page7/vision_04.gif" alt="추진계획 : 글로벌 시장 및 투융자 확대지원, 전문인력 양성 및 일자리 창출, 미래 융합콘텐츠 육성지원 정책지원강화, CT역량강화 지원, 건전한 콘텐츠 이용 문화조성">-->
			<img src="/images/2012/sub/page7/vision_1234.gif" alt="차세대인재양성교육 : -콘텐츠 창의인재 동반사업 -영상제작 게임콘텐츠 과정 -S3D엘리트 창의인재 양성 과정, 온라인평생교육 : -게임 방송영상 문화분야 사이버콘텐츠아카데미 운영 -http://edu.kocca.or.kr, 현업인직무재교육 : -창의 마스터 클래스 -전략지역 전문가 해외연수 -3D입체 콘텐츠 전문인력양성 과정 -스마트 콘텐츠 전문인력양성 과정 -기획프로듀서양성 과정">
		</div>
	</div>
	
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>