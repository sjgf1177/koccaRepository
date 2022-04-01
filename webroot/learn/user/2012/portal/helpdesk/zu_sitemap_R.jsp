<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  v_process     = box.getString("p_process");
    String  v_tem_grcode   = box.getSession("tem_grcode");
    
    String v_content    = "";
    String v_code       = "";
    String v_title      = "";
    box.put("topmenu","1");
    box.put("submenu","8");
    
    DataBox dbox = (DataBox)request.getAttribute("selectView");
    if (dbox != null) {
    	v_content    = dbox.getString("d_content");
        v_code       = dbox.getString("d_code");
        v_title      = dbox.getString("d_title");
    }
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->
	<div class="content">
		<table class="rootinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"><img src="/images/2012/sub/page0/title/sitemap.gif" alt="사이트맵"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > <u>사이트맵</u></td>
			</tr>
		</table>
		<div class="sitemap">
			<ul class="mapbox">
				<li class="topmenu">나의 강의실</li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('3','11');" tabindex="171" title="현재 학습중인 과정을 확인 또는 학습할 수 있습니다.">수강중인 과정</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('3','02');" tabindex="172" title="회원가입 후 현재까지 학습한 이력을 확인할 수 있습니다.">나의교육이력</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('3','17');" tabindex="173" title="신청한 과정을 확인하거나 취소할 수 있습니다.">교육신청 확인/취소</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('3','19');" tabindex="174" title="결제 조회 또는 취소를 할 수 있습니다.">수강료 결제관리</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('3','12');" tabindex="175" title="상담하신 내역을 확인할 수 있습니다.">나의 상담내역</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('3','07');" tabindex="176" title="회원님이 참여하신 설문을 확인할 수 있습니다.">나의 설문</a></li>
				<!-- <li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="#" title="">자기역량 진단</a></li> -->
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuMainForward('4','/servlet/controller.study.MyClassServlet?p_process=StudyHistoryListSyuro');" tabindex="177" title="과정을 수료하신 경우 수료증을 출력할 수 있습니다.">수료증 출력</a></li>
			</ul>
			<ul class="mapbox margin20">
				<li class="topmenu">온라인과정</li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_area=B0')" tabindex="181" title="방송영상 분야 온라인과정을 안내합니다.">방송영상</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_area=G0')" tabindex="182" title="게임개발 분야 온라인과정을 안내합니다.">게임제작</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=SubjectList&p_area=K0')" tabindex="183" title="문화콘텐츠 분야 온라인과정을 안내합니다.">문화콘텐츠</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuMainForward('4','/servlet/controller.propose.ProposeCourseServlet?p_process=Curriculum')" tabindex="184" title="문화콘텐츠 분야 온라인과정을 안내합니다.">커리큘럼</a></li>
			</ul>
			<ul class="mapbox margin20">
				<li class="topmenu">오프라인과정</li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('2','01');" tabindex="191" title="방송영상 분야 오프라인과정을 안내합니다.">방송영상</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('2','02');" tabindex="192" title="게임개발 분야 오프라인과정을 안내합니다.">게임개발</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('2','03');" tabindex="193" title="기획창작 분야 오프라인과정을 안내합니다">기획창작</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('2','04');" tabindex="194" title="학습 중 필요한 자료를 제공합니다.">교육자료실</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('2','05');" tabindex="195" title="오프라인 교육의 연간 및 월간 교육일정을 확인할 수 있습니다.">교육일정</a></li>
				<!-- <li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="#" title="">교육환경소개</a></li> -->
			</ul>
			<ul class="mapbox margin20">
				<li class="topmenu">학습지원</li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('4','07');" tabindex="201" title="한국콘텐츠진흥원 아카데미의 새소식을 알려드립니다.">공지사항</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('4','01');" tabindex="202" title="자주 묻는 질문을 모아두었습니다.">FAQ</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('4','02');" tabindex="203" title="궁금한 점이 있다면 물어보세요.">묻고답하기</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('4','05');" tabindex="204" title="온라인 학습 전 정상적인 학습을 위해 학습환경을 설정합니다.">학습환경도우미</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('4','09');" tabindex="205" title="오프라인 교육 시 사용되는 교재를 소개합니다.">1:1문의</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('4','10');" tabindex="206" title="비공개 질문을 원하신다면 이곳 1:1문의를 이용해주세요.">교재소개</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('4','12');" tabindex="207" title="아카데미에서 진행하는 다양한 이벤트를 확인할 수 있습니다.">이벤트</a></li>
				<!-- <li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="#" title="">수강신청방법</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="#" title="">교육가이드</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="#" title="">자기역량진단</a></li> -->
			</ul>
			<div class="clears"></div>
			<dl class="topgrid">
				<dd class="frist"></dd>
				<dd class="twoend"></dd>
				<dd class="twoend"></dd>
				<dd class="twoend"></dd>
			</dl>
			<div class="centerlogo"><img src="/images/2012/sub/page0/sitemap_logo.gif"></div>
			<dl class="bottomgrid">
				<dd class="frist"></dd>
				<dd class="twoend"></dd>
				<dd class="twoend"></dd>
				<dd class="twoend"></dd>
			</dl>
			<ul class="mapbox">
				<li class="topmenu">열린강의</li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('5','12');" tabindex="211" title="방송영상 관련 열린강의를 열람할 수 있습니다.">방송영상</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('5','13');" tabindex="212" title="영화 관련 열린강의를 열람할 수 있습니다.">영화</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('5','14');" tabindex="213" title="다큐멘터리 관련 열린강의를 열람할 수 있습니다.">다큐멘터리</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('5','15');" tabindex="214" title="문화예술 관련 열린강의를 열람할 수 있습니다.">문화예술</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('5','17');" tabindex="215" title="게임 관련 열린강의를 열람할 수 있습니다.">게임</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('5','16');" tabindex="215" title="기타 관련 열린강의를 열람할 수 있습니다.">기타</a></li>
				<!-- <li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="#" title="">기획</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="#" title="">기타</a></li> -->
			</ul>
			<ul class="mapbox margin20">
				<li class="topmenu">지식팩토리</li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage')" tabindex="221" title="지식팩토리의 전체 지식을 종합해보았습니다.">종합</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=CB')" tabindex="222" title="방송영상 분야 지식 공간입니다.">방송영상</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=CG')" tabindex="223" title="게임개발 분야 지식 공간입니다.">게임개발</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=CK')" tabindex="224" title="문화콘텐츠 분야 지식 공간입니다.">문화콘텐츠</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuMainForward('7','/servlet/controller.homepage.KnowBoardUserServlet?p_process=ListPage&p_categorycd=MINE')" tabindex="225" title="회원님께서 질문하신 내용을 이용할 수 있습니다.">내가 작성한 글</a></li>
			</ul>
			<ul class="mapbox margin20">
				<li class="topmenu">아카데미소개</li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('7','01');" tabindex="231" title="한국콘텐츠진흥원장님께서 회원님들께 드리는 인사말입니다.">진흥원장 인사말</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('7','02');" tabindex="232" title="아카데미의 설립목적과 비전에 대해 소개합니다.">설립목적 및 비전</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('7','05');" tabindex="233" title="아카데미 교육사업을 안내합니다.">교육사업안내</a></li>
				<!-- <li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="#" title="">교육시설안내</a></li> -->
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('7','06');" tabindex="235" title="아카데미와 협력관계인 기관을 소개합니다.">교육협력기관</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('7','04');" tabindex="236" title="한국콘텐츠진흥원 아카데미에 오시는길을 안내합니다.">오시는길</a></li>
			</ul>
			<ul class="mapbox margin20">
				<li class="topmenu">회원서비스</li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuMainForward('90','/servlet/controller.homepage.MainServlet?p_process=MainLogin');" tabindex="241" title="이미 회원이신 분께서는 회원로그인을 해주세요.">회원 로그인</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('90','01');" tabindex="242" title="한국콘텐츠아카데미에 처음 오셨나요? 무료회원가입으로 다양한 서비스를 받아보세요.">회원가입</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('90','02');" tabindex="243" title="아이디 또는 비밀번호를 잊으셨다면 이곳을 이용해주세요.">아이디/비밀번호 찾기</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('90','03');" tabindex="244" title="회원님의 비밀번호 및 개인정보를 변경하고자 하실 때 이용해주세요.">회원정보변경</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('90','04');" tabindex="245" title="회원탈퇴를 하고 싶은 경우 클릭해주세요.">회원탈퇴</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('90','05');" tabindex="246" title="한국콘텐츠아카데미를 이용하시기 위한 약관입니다.">이용약관</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('90','06');" tabindex="247" title="한국콘텐츠아카데미에서는 고객님의 소중한 개인정보를 이렇게 관리 및 이용합니다.">개인정보처리방침</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('90','02');" tabindex="248" title="한국콘텐츠아카데미는 이메일무단수집을 거부합니다">이메일무단수집거부</a></li>
				<li class="middlemenu"><img src="/images/2012/sub/icon_dot.png" align="absmiddle" alt="dot"><a href="javascript:menuForward('90','09');" tabindex="249" title="한국콘텐츠아카데미 홈페이지 서비스를 한눈에 확인하실 수 있습니다.">사이트맵</a></li>
			</ul>
			<div class="clearL"></div>
		</div>
	</div>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>