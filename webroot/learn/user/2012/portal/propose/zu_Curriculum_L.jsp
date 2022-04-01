<%
//**********************************************************
//  1. 제	  목: SUBJECT LIST
//  2. 프로그램명: zu_Subject_L.jsp
//  3. 개	  요: 과정조회
//  4. 환	  경: JDK 1.3
//  5. 버	  젼: 1.0
//  6. 작	  성: 2004.01.14
//  7. 수	  정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","3");
	box.put("submenu","5");
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>

<div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 온라인과정 > <u>커리큘럼</u></td>
			</tr>
		</table>
		<div class="onlinecourse">
			<ul class="curriculumlayout">
				<li>
					<table class="curriculum" cellpadding="0" cellspacing="0" summary="온라인과정 방송영상 분야를 소개합니다">
						<caption class="bgA">방송영상 분야 커리큘럼</caption>
						<colgroup><col width="80"><col width="*"><col width="70"></colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th>교육과정</th>
								<th>학습차시</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td rowspan="10">기획</td>
								<td class="coursename">TV 다큐멘터리 글쓰기</td>
								<td>10</td>
							</tr>
							<tr>
								<td class="coursename">글쓰기를 위한 창조적 사고</td>
								<td>5</td>
							</tr>
							<tr>
								<td class="coursename">다큐멘터리 트레일러 만들기와 피칭전략</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">디지털미디어 스토리텔링</td>
								<td>20</td>
							</tr>
							<tr>
								<td class="coursename">방송영상 콘텐츠기획</td>
								<td>16</td>
							</tr>
							<tr>
								<td class="coursename">방송콘텐츠 스토리텔링</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">블로그에서 책까지, 글쓰기 실습</td>
								<td>5</td>
							</tr>
							<tr>
								<td class="coursename">영상미학</td>
								<td>10</td>
							</tr>
							<tr>
								<td class="coursename">정재형의 영화로 보는 영상미학</td>
								<td>10</td>
							</tr>
							<tr>
								<td class="coursename">통하는 글쓰기 기본</td>
								<td>15</td>
							</tr>
							
							<tr class="division">
								<td rowspan="22">제작</td>
								<td class="coursename">3D 영상제작개론</td>
								<td>12</td>
							</tr>
							<tr class="division">
								<td class="coursename">3D 입체촬영실무</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">3D 입체편집</td>
								<td>12</td>
							</tr>
							<tr class="division">
								<td class="coursename">Adobe Premiere Pro CS5</td>
								<td>23</td>
							</tr>
							<tr class="division">
								<td class="coursename">After Effect CS5</td>
								<td>17</td>
							</tr>
							<tr class="division">
								<td class="coursename">Autodesk Maya를 이용한 입체영상 제작</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">Avid Media Composer Advanced</td>
								<td>17</td>
							</tr>
							<tr class="division">
								<td class="coursename">Final cut Pro의 Motion 제작 실무</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">TV다큐멘터리의 이론과 실제_new</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">디지털 영상 색 보정 </td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">디지털 포맷과 워크플로어</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">방송아나운싱</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">방송음향 기초</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">방송제작 실무</td>
								<td>12</td>
							</tr>
							<tr class="division">
								<td class="coursename">스튜디오프로그램제작</td>
								<td>17</td>
							</tr>
							<tr class="division">
								<td class="coursename">영상 창작의 기본 - 조명</td>
								<td>13</td>
							</tr>
							<tr class="division">
								<td class="coursename">영상제작전문가특강(연출론)_new</td>
								<td>12</td>
							</tr>
							<tr class="division">
								<td class="coursename">영상제작전문가특강(영상론)_new</td>
								<td>10</td>
							</tr>
							<tr class="division">
								<td class="coursename">영상촬영 기본</td>
								<td>16</td>
							</tr>
							<tr class="division">
								<td class="coursename">영상촬영의 이해</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">촬영에서 편집까지 비주얼 스토리텔링</td>
								<td>17</td>
							</tr>
							<tr class="division">
								<td class="coursename">파이널컷프로</td>
								<td>15</td>
							</tr>
							
							<tr>
								<td rowspan="4">경영지원</td>
								<td class="coursename">미디어속의 양성평등</td>
								<td>10</td>
							</tr>
							<tr>
								<td class="coursename">방송콘텐츠 유통과 마케팅</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">성공기업의 성공 CEO [01-10]</td>
								<td>10</td>
							</tr>
							<tr>
								<td class="coursename">콘텐츠 비즈니스와 투자전략의 이론과 실제</td>
								<td>10</td>
							</tr>
						</tbody>
					</table>
					
					<table class="curriculum" cellpadding="0" cellspacing="0" summary="온라인과정 방송영상 분야를 소개합니다">
						<caption class="bgB">게임개발 분야 커리큘럼</caption>
						<colgroup><col width="80"><col width="*"><col width="70"></colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th>교육과정</th>
								<th>학습차시</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td rowspan="11">기획</td>
								<td class="coursename">게임 기획 및 그래픽 실무자를 위한 게임기술 이해</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">게임산업연구</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">기초 게임시나리오 창작실습</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">기획자가 알아야 할 2D 게임그래픽</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">기획자가 알아야 할 3D 게임그래픽</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">네트워크 이론</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">사례를 통한 게임마케팅 연구</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">스토리보드</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">아이디어발상_new</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">알고리즘</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">자료구조</td>
								<td>14</td>
							</tr>
							
							<tr class="division">
								<td rowspan="26">제작</td>
								<td class="coursename">3D Data Exporting Techniques</td>
								<td>10</td>
							</tr>
							<tr class="division">
								<td class="coursename">3D 게임 텍스처 기초</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">3D 공간구조프로그래밍</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">3D 엔진프로그래밍</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">3D 프로그래밍 기초</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">C#</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">DirectX 2D 프로그래밍</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">DirectX10</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">MAX를 이용한 3D그래픽 실습</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">MOD 게임제작 및 활용</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">UDK 활용 방법(스크립트)</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">UDK 활용 방법(툴)</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">게임 배경그래픽 기초</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">게임디자인 기초</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">게임수학</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">게임캐릭터디자인1</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">게임캐릭터디자인2</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">아이폰 및 아이패드용 게임 제작(기초)</td>
								<td>16</td>
							</tr>
							<tr class="division">
								<td class="coursename">아이폰 및 아이패드용 게임 제작(실무)</td>
								<td>10</td>
							</tr>
							<tr class="division">
								<td class="coursename">안드로이드 게임 제작(기초)</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">안드로이드 게임 제작(실무)</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">애니메이션개론</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">재미이론</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">포트폴리오 구성과 제작</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">프로그래밍 이론</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">현장 중심의 3D 게임레벨디자인-MMORPG, FPS, 레이싱 게임</td>
								<td>14</td>
							</tr>
							
							<tr>
								<td>유통</td>
								<td class="coursename">온라인 게임의 해외진출 전략</td>
								<td>7</td>
							</tr>
						</tbody>
					</table>
				</li>
				<li class="marginspace">
					<table class="curriculum" cellpadding="0" cellspacing="0" summary="온라인과정 방송영상 분야를 소개합니다">
						<caption class="bgC">문화콘텐츠 분야 커리큘럼</caption>
						<colgroup><col width="80"><col width="*"><col width="70"></colgroup>
						<thead>
							<tr>
								<th>구분</th>
								<th>교육과정</th>
								<th>학습차시</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td rowspan="36">기획</td>
								<td class="coursename">11가지 법칙으로 끝내는 스토리텔링</td>
								<td>11</td>
							</tr>
							<tr>
								<td class="coursename">OSMU를 위한 창조적 발상</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">[입문] 문화콘텐츠와 스토리디자인</td>
								<td>13</td>
							</tr>
							<tr>
								<td class="coursename">[입문] 유비쿼터스사회와 문화콘텐츠</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">감독과 배우가 이야기하는 나의 영화인생</td>
								<td>11</td>
							</tr>
							<tr>
								<td class="coursename">글로컬문화콘텐츠의 이해와 사례</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">대중문화분석</td>
								<td>28</td>
							</tr>
							<tr>
								<td class="coursename">대중음악콘텐츠기획과 해외진출</td>
								<td>13</td>
							</tr>
							<tr>
								<td class="coursename">모바일 앱 비즈니스 기획부터 프로모션까지</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">문화계 거장들의 인생 이야기</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">문화콘텐츠 상품기획</td>
								<td>17</td>
							</tr>
							<tr>
								<td class="coursename">문화콘텐츠 성공사례</td>
								<td>30</td>
							</tr>
							<tr>
								<td class="coursename">문화콘텐츠 스토리텔링 사례</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">문화콘텐츠론</td>
								<td>20</td>
							</tr>
							<tr>
								<td class="coursename">문화콘텐츠연출론</td>
								<td>14</td>
							</tr>
							<tr>
								<td class="coursename">미국 영화</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">세계의 애니메이션 역사와 사례</td>
								<td>7</td>
							</tr>
							<tr>
								<td class="coursename">쉽게 배우는 전시기획</td>
								<td>13</td>
							</tr>
							<tr>
								<td class="coursename">스마트폰과 문화</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">스토리텔링 개론</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">스토리텔링의 이해</td>
								<td>28</td>
							</tr>
							<tr>
								<td class="coursename">시간 속의 만화와 캐릭터</td>
								<td>9</td>
							</tr>
							<tr>
								<td class="coursename">아시아 영화</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">에듀테인먼트 사례</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">영화산업의 사례연구</td>
								<td>10</td>
							</tr>
							<tr>
								<td class="coursename">원천스토리로서의 만화 읽기</td>
								<td>10</td>
							</tr>
							<tr>
								<td class="coursename">전문가의 명사특강</td>
								<td>10</td>
							</tr>
							<tr>
								<td class="coursename">재미있는 TV 읽기</td>
								<td>7</td>
							</tr>
							<tr>
								<td class="coursename">재미있는 광고 읽기</td>
								<td>7</td>
							</tr>
							<tr>
								<td class="coursename">재미있는 디자인 읽기</td>
								<td>7</td>
							</tr>
							<tr>
								<td class="coursename">재미있는 영화읽기</td>
								<td>10</td>
							</tr>
							<tr>
								<td class="coursename">프로듀서가 이야기하는 우리 프로그램 이렇게 만들었다</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">첨단기술 애니메이션</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">축제기획과 문화콘텐츠</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">헐리우드 애니메이션 스토리텔링 전략</td>
								<td>7</td>
							</tr>
							<tr>
								<td class="coursename">헐리웃에서 이야기하는 컨셉 디자인</td>
								<td>4</td>
							</tr>
							
							<tr class="division">
								<td>제작</td>
								<td class="coursename">음반제작워크숍</td>
								<td>10</td>
							</tr>
							
							<tr>
								<td rowspan="3">유통</td>
								<td class="coursename">분야별로 배우는 콘텐츠 마케팅</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">사례로 알아보는 소셜네트워크</td>
								<td>15</td>
							</tr>
							<tr>
								<td class="coursename">해외진출전략</td>
								<td>26</td>
							</tr>
							
							<tr class="division">
								<td rowspan="10">경영지원</td>
								<td class="coursename">간단하게 배우는 문화콘텐츠 법률</td>
								<td>7</td>
							</tr>
							<tr class="division">
								<td class="coursename">공연기획과 홍보마케팅 실무</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">대중문화예술인과 인권</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">대중문화예술인의 법적권리</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">대중문화예술인의 이해</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">디자인과 상표(저작권심화)</td>
								<td>20</td>
							</tr>
							<tr class="division">
								<td class="coursename">사례로 보는 SNS 비즈니스 전략</td>
								<td>14</td>
							</tr>
							<tr class="division">
								<td class="coursename">예술경영의 실무</td>
								<td>15</td>
							</tr>
							<tr class="division">
								<td class="coursename">저작권(저작권기본)</td>
								<td>20</td>
							</tr>
							<tr class="division">
								<td class="coursename">트렌드 워칭 - 스마트 환경에서의 콘텐츠 비즈니스</td>
								<td>11</td>
							</tr>
						</tbody>
					</table>
				</li>
			</ul>
			<div class="clearL"></div>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
