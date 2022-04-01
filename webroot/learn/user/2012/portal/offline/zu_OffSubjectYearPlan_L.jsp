<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.off.OffClassifySubjectBean" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	box.put("topmenu","4");
	box.put("submenu","5");

    if (box == null) box = RequestManager.getBox(request);
    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));
    
    GregorianCalendar   calendar    = new GregorianCalendar();
    String currentYear = Integer.toString(calendar.get(Calendar.YEAR));

    String  v_gyear         = box.getStringDefault("p_year", currentYear);           //년도
    String  v_month         = box.getStringDefault("p_month", "01");                  // 월
    String  v_upperclass    = box.getStringDefault("p_upperclass", "B01");            // 대분류
    String  v_middleclass   = box.getString("p_middleclass");                         // 중분류
    
    String v_subj           = "";
    String v_subjnm         = "";
    String v_edustart       = "";
    String v_eduend         = "";
    String v_propstart      = "";                          
    String v_propend        = "";
    String v_biyong         = "";
    String v_studentlimit   = "";                                              
    String v_status         = "";        
    String v_subjseq        = "";
    String v_year           = "";
    String v_classname      = "";
    String v_seq            = "";
    
    String v_menuid         = "";
    String v_type           = "";
    
    String v_edumonth  		= ""; 
    
    int    v_month_cnt   =  0;
    int    v_subj_cnt       =  0;

    ArrayList offSchlYearPlanList = (ArrayList)request.getAttribute("offSchlYearPlanList");
    int listSize    = offSchlYearPlanList != null ? offSchlYearPlanList.size() : 0 ;
    int subCnt      = offSchlYearPlanList != null && listSize > 0 ? ((DataBox)offSchlYearPlanList.get(0)).getInt("d_total_subj") : 0 ;
    	
    int     v_dispnum = 0;
    int     v_totalrowcount = offSchlYearPlanList != null &&  listSize > 0 ? ((DataBox)offSchlYearPlanList.get(0)).getInt("d_totalrowcount") : 0;
    int     v_totalpage = 0;  
    
    int		index = 0; 
    
    int v_pageno             = box.getInt("p_pageno"); 
    String s_userid          = box.getSession("userid");
    String s_usernm          = box.getSession("name");
    String s_grcode          = box.getStringDefault("tem_grcode", box.getSession("tem_grcode"));
    

    String v_classname1      = "end stxt";
    String v_classname2      = "end";
    
    DecimalFormat   dFormat = new DecimalFormat();
    
    ArrayList<DataBox> upperList = null;        // 과정 대분류 리스트
    HashMap<String, String> upperMap = null;    // 대분류 별 메뉴ID 매핑 맵 HashMap<upperlcass, menuid>
    
    upperList  = OffClassifySubjectBean.getUpperClassList(box); 
    upperMap   = OffClassifySubjectBean.getMenuId(box);
                  
%>

<%@ include file="/learn/user/2012/portal/include/top.jsp"%>
<!-- Common 영역종료 -->

<!-- 스크립트영역 -->
<script language="JavaScript" type="text/JavaScript">
<!--

//페이지 이동
function go(index) {
    document.form1.p_pageno.value = index;
    document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
    document.form1.p_process.value = "offSchlYearPlanList";
    document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
     document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
     document.form1.p_process.value = "offSchlYearPlanList";
     document.form1.submit();
}

//수강신청
function whenSubjPropose(subj,year,subjseq, subjnm, seq) {
    if(<%= box.getSession("userid").length()==0 %>) {
        alert("로그인이 필요합니다.");
        return;
    }
    if(!confirm(subjnm+"과정을 수강신청하시겠습니까?")){
     return;
    }
    var new_Open = window.open("","proposeWindow",'scrollbars=yes,width=800,height=600,resizable=no');
    document.form1.p_process.value = "SubjectEduProposePage";
    document.form1.target = "proposeWindow";
    document.form1.p_subj.value = subj;
    document.form1.p_year.value = year;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_seq.value = seq;
    document.form1.action = "/servlet/controller.off.ProposeOffServlet";
    document.form1.submit();
    document.form1.target = "_self";
}

function changeMonth(month) {
    document.form1.target = "_self";
    document.form1.p_pageno.value = 1;
    document.form1.p_month.value = month;
    document.form1.action = '/servlet/controller.course.EduScheduleHomePageServlet';
    document.form1.p_process.value = "offSchlYearPlanList";
    document.form1.submit();
}

function moveTab(upperclass){
	 document.form1.p_upperclass.value = upperclass;
	 document.form1.p_middleclass.value = "";
     document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
     document.form1.p_process.value = "offSchlYearPlanList";
     document.form1.submit();
}
function changeYear(){
	document.form1.action = "/servlet/controller.course.EduScheduleHomePageServlet";
    document.form1.p_process.value = "offSchlYearPlanList";
    document.form1.submit();
}

function moveMonthList(month){
	document.form1.p_gyear.value = document.form1.p_year.value;
    document.form1.target = "_self";
    document.form1.p_pageno.value = 1;
    document.form1.menuid.value = "06";
    document.form1.p_month.value = month;
    document.form1.action = '/servlet/controller.course.EduScheduleHomePageServlet';
    document.form1.p_process.value = "offSchlMonthPlanList";
    document.form1.submit();
}

//과정 내용보기
function whenSubjInfo(subj, subjseq, seq, menuid){
    document.form1.p_subj.value = subj;
    document.form1.p_subjseq.value = subjseq;
    document.form1.p_seq.value = seq;
    document.form1.menuid.value = menuid;
    document.form1.p_process.value = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.off.ProposeOffServlet';
    document.form1.target = "_self";
    document.form1.submit();
    }
//-->
</script>
<div id="ajaxDiv"></div>
<!-- 스크립트영역종료 -->
<!-- form 시작 -->
<form name = "form1" method = "post" action="">
      <input type = "hidden" name = "menuid"        value = "">
      <input type = "hidden" name = "p_process"     value = "">
      <input type = "hidden" name = "p_rprocess"     value = "">
      <input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
      <input type = "hidden" name = "p_seq"         value = "">
      <input type = "hidden" name = "p_userid"      value = "">
      <input type = "hidden" name = "p_month"       value = "<%=v_month %>">
      <input type = "hidden" name = "p_subj"        value = "">
      <input type = "hidden" name = "p_subjseq"     value = "">
      <input type = "hidden" name = "p_upperclass"  value = "<%=v_upperclass %>">
      <input type = "hidden" name = "type"          value = "<%=v_type %>">
      <input type = "hidden" name = "p_gyear"       value = "">
      
      <div class="content">
		<table class="pageinfo" cellpadding="0" cellspacing="0">
			<tr>
				<td class="pagetitle"></td>
				<td class="pagenavi"><img src="/images/2012/sub/icon_home.gif" alt="홈"> 홈 > 오프라인과정 > <u>교육일정</u></td>
			</tr>
		</table>
		<div class="concept c03">
			<div class="pagetitle"><img src="/images/2012/sub/page3/title/offline_schedule.gif" alt="교육자료실"></div>
			<ul class="pagetab">
				<li><a href="javascript:menuForward('2','05');" tabindex="11-1" title="오프라인 연간 교육일정을 안내합니다"><img src="/images/2012/sub/page3/tabmenu/yearschedule_on.png" alt="연간교육일정"></a></li>
				<li><a href="javascript:menuForward('2','06');" tabindex="11-2" title="오프라인 월별 교육일정을 안내합니다"><img src="/images/2012/sub/page3/tabmenu/monthschedule_off.png" alt="월간교육일정"></a></li>
			</ul>
		</div>
		<div class="offlinecourse">
			<ul class="curriculumlayout">
				<li>
					<img src="/images/2012/sub/page3/off_notice1.png" alt="업데이트 예정중입니다." style="margin-top:30px;"/>
					<!--<table class="curriculum" cellspacing="0" cellpadding="0">
						<caption class="bgA">차세대 핵심 인력양성 교육</caption>
						<colgroup><col width="80"><col width="*"><col width="120"><col width="50"><col width="50"></colgroup>
						<thead>
							<tr>
								<th colspan="2">과정명</th>
								<th>교육일정</th>
								<th>기간</th>
								<th>인원</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td rowspan="4">방송ㆍ영상</td>
								<td class="coursename">VFX (특수영상)</td>
								<td>2012년 3월~12월</td>
								<td>9개월</td>
								<td>15명</td>
							</tr>
							<tr>
								<td class="coursename">모션 그래픽</td>
								<td>2012년 3월~12월</td>
								<td>9개월</td>
								<td>15명</td>
							</tr>
							<tr>
								<td class="coursename">디지털 영상편집</td>
								<td>2012년 3월~8월</td>
								<td>6개월</td>
								<td>30명</td>
							</tr>
							<tr>
								<td class="coursename">디지털 음향제작</td>
								<td>2012년 3월~8월</td>
								<td>6개월</td>
								<td>10명</td>
							</tr>
							
							<tr class="division">
								<td rowspan="4">게임</td>
								<td class="coursename">게임 디자인(기획) </td>
								<td>2012년 3월~12월</td>
								<td>10개월</td>
								<td>12명</td>
							</tr>
							<tr class="division">
								<td class="coursename">게임 프로그램</td>
								<td>2012년 3월~12월</td>
								<td>10개월</td>
								<td>18명</td>
							</tr>
							<tr class="division">
								<td class="coursename">게임 그래픽</td>
								<td>2012년 3월~12월</td>
								<td>10개월</td>
								<td>15명</td>
							</tr>
							<tr class="division">
								<td class="coursename">게임 프로젝트</td>
								<td>2012년 3월~12월</td>
								<td>10개월</td>
								<td>30명</td>
							</tr>
							<tr>
								<td>기획ㆍ창작</td>
								<td class="coursename">콘텐츠 기획창작</td>
								<td>2012년 3월~12월</td>
								<td>10개월</td>
								<td>20명</td>
							</tr>
							<tr class="division">
								<td>비즈니스</td>
								<td class="coursename">국제콘텐츠 비즈니스</td>
								<td>2012년 3월~11월</td>
								<td>9개월</td>
								<td>15명</td>
							</tr>
							<tr>
								<td rowspan="3">3D입체영상</td>
								<td class="coursename">Pre-Production(기획/연출)</td>
								<td>2012년 3월~12월</td>
								<td>10개월</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Production(촬영)</td>
								<td>2012년 3월~12월</td>
								<td>10개월</td>
								<td>15명</td>
							</tr>
							<tr>
								<td class="coursename">st-Production(편집/VFX)</td>
								<td>2012년 3월~12월</td>
								<td>10개월</td>
								<td>20명</td>
							</tr>
						</tbody>
					</table>
					
					<table class="curriculum" cellspacing="0" cellpadding="0">
						<caption class="bgD">3D입체콘텐츠 전문인력양성 교육 : <span>현업인 대상 3D입체 전환 재교육(단기과정)</span></caption>
						<colgroup><col width="80"><col width="*"><col width="90"><col width="40"></colgroup>
						<thead>
							<tr>
								<th>과정명</th>
								<th>과정명</th>
								<th>교육일정</th>
								<th>인원</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td rowspan="11">국가인적자원개발<br>중소기업<br>컨소시엄<br>직무과정<br>(노동부)</td>
								<td class="coursename">Final Cut Pro과정</td>
								<td>01.09 ~ 01.13</td>
								<td>5명</td>
							</tr>
							<tr>
								<td class="coursename">iPhone 제작과정</td>
								<td>01.09 ~ 01.13</td>
								<td>15명</td>
							</tr>
							<tr>
								<td class="coursename">콘텐츠 기획서 작성 전략 과정</td>
								<td>02.20 ~ 02.21</td>
								<td>15명</td>
							</tr>
							<tr>
								<td class="coursename">비즈니스 스킬업 과정</td>
								<td>02.01 ~ 02.02</td>
								<td>5명</td>
							</tr>
							<tr>
								<td class="coursename">Protools 강사과정</td>
								<td>02.01 ~ 02.02</td>
								<td>5명</td>
							</tr>
							<tr>
								<td class="coursename">디지털미디어 워크플로우 과정</td>
								<td>03.12 ~ 03.14</td>
								<td>5명</td>
							</tr>
							<tr>
								<td class="coursename">레벨 디자인 방법론 과정</td>
								<td>04.04 ~ 04.06</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">콘텐츠기획(영화,드라마,웹툰,애니,공연)과정</td>
								<td>05.08 ~ 05.10</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Avid MC 101과정</td>
								<td>02.06 ~ 02.10</td>
								<td>5명</td>
							</tr>
							<tr>
								<td class="coursename">Final Cut Pro과정</td>
								<td>02.20 ~ 02.24</td>
								<td>5명</td>
							</tr>
							<tr>
								<td class="coursename">상표권, 저작권, 라이센싱 계약 사례 과정</td>
								<td>06.11 ~ 06.15</td>
								<td>10명</td>
							</tr>
							<tr class="division">
								<td rowspan="6">문화콘텐츠<br>직무과정</td>
								<td class="coursename">창의적 콘텐츠 소재발견을 위한 세미나</td>
								<td>05.16 ~ 05.17</td>
								<td>15명</td>
							</tr>
							<tr class="division">
								<td class="coursename">다문화 콘텐츠 제작의 이해</td>
								<td>5월 중</td>
								<td>15명</td>
							</tr>
							<tr class="division">
								<td class="coursename">국제다큐멘터리 공동제작 가이드</td>
								<td>06.19 ~ 06.20</td>
								<td>20명</td>
							</tr>
							<tr class="division">
								<td class="coursename">OSMU를 위한 앱 창작과정</td>
								<td>09.03 ~ 10.12</td>
								<td>10명</td>
							</tr>
							<tr class="division">
								<td class="coursename">공연예술, 기획에서 수출까지</td>
								<td>10.16 ~ 10.17</td>
								<td>20명</td>
							</tr>
							<tr class="division">
								<td class="coursename">청소년을 위한 창의과정 해피미디어 세상</td>
								<td>10월 중</td>
								<td>15명</td>
							</tr>
						</tbody>
					</table>
					
					<table class="curriculum" cellspacing="0" cellpadding="0">
						<caption class="bgD">3D입체콘텐츠 전문인력양성 교육 : <span>3D입체콘텐츠 해외강사 초청 워크샵</span></caption>
						<colgroup><col width="150"><col width="*"><col width="80"></colgroup>
						<thead>
							<tr>
								<th>과정명</th>
								<th>교육내용</th>
								<th>일정</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>VFX슈퍼바이저 초정</td>
								<td class="coursename">해외 유명 거장 초청을 통한 3D입체 VFX세미나</td>
								<td rowspan="3">별도공지예정</td>
							</tr>
							<tr class="division">
								<td>S3D Director 초청 워크샵</td>
								<td class="coursename">입체영상의 효과적인 기획과 연출을 위한 전문가 초청 워크샵</td>
							</tr>
							<tr>
								<td>S3D 비즈매칭 세미나</td>
								<td class="coursename">3D입체 콘텐츠 해외 진출을 위한 글로벌 제작 가이드라인 세미나</td>
							</tr>
						</tbody>
					</table>
					
					<table class="curriculum" cellspacing="0" cellpadding="0">
						<caption class="bgD">3D입체콘텐츠 전문인력양성 교육 : <span>대학별 3D입체콘텐츠 전환 재교육</span></caption>
						<colgroup><col width="150"><col width="*"><col width="120"></colgroup>
						<thead>
							<tr>
								<th>과정명</th>
								<th>교육내용</th>
								<th>일정</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>대학재학생 3D입체콘텐츠<br>전환 재교육 과정</td>
								<td class="coursename">
									- S3D Terminology<br>
									- 3D Design<br>
									- 프리비즈
								</td>
								<td>여름 및 겨울방학</td>
							</tr>
						</tbody>
					</table>
					<h6>※ 본 과정은 별도 공고를 통해 진행될 예정입니다.</h6>
				</li>
				<li class="marginspace">
					<table class="curriculum" cellspacing="0" cellpadding="0">
						<caption class="bgC">산업계 직무 재교육</caption>
						<colgroup><col width="*"><col width="85"><col width="35"></colgroup>
						<thead>
							<tr>
								<th>과정명</th>
								<th>교육일정</th>
								<th>인원</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td class="coursename">Smoke를 활용한 3D입체 제작 과정 1차</td>
								<td>04.16 ~ 04.20</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Avid를 활용한 3D입체 제작 과정 1차</td>
								<td>04.23 ~ 04.27</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">스테레오그래퍼 과정 1차</td>
								<td>04.23 ~ 04.27</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">3ALITY 리그 테크니션 과정 1차</td>
								<td>05.02 ~ 05.04</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">3D입체 특수촬영 과정</td>
								<td>05.14 ~ 05.18</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">P+S Technik 리그 테크니션 과정 1차</td>
								<td>05.14 ~ 05.18</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Avid를 활용한 3D입체 제작 과정 2차</td>
								<td>05.21 ~ 05.25</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">3ALITY Rig Technician Instructor</td>
								<td>05.29 ~ 05.31</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Nuke와 Ocula를 활용한 3D입체 컴포지팅 과정 1차</td>
								<td>05.29 ~ 05.31</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Cinema4D를 활용한 3D입체 제작 과정 1차</td>
								<td>06.11 ~ 06.15</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">3D입체 수중촬영 과정</td>
								<td>06.18 ~ 06.22</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Avid를 활용한 3D입체 제작 과정 3차</td>
								<td>06.25 ~ 06.29</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">3D입체 교수워크숍</td>
								<td>06.28 ~ 07.06</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">3ALITY 리그 테크니션 과정 2차</td>
								<td>07.04 ~ 07.06</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Smoke를 활용한 3D입체 제작 과정 2차</td>
								<td>07.09 ~ 07.13</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Avid를 활용한 3D입체 제작 과정 4차</td>
								<td>07.23 ~ 07.27</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Nuke와 Ocula를 활용한 3D입체 컴포지팅 과정 2차</td>
								<td>07.30 ~ 08.03</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Avid를 활용한 3D입체 제작 과정 5차</td>
								<td>08.20 ~ 08.24</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">After Effect를 활용한 3D입체 모션그래픽 1차</td>
								<td>08.27 ~ 08.31</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Nuke와 Ocula를 활용한 3D입체 컴포지팅 과정 3차</td>
								<td>09.03 ~ 09.07</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">스테레오그래퍼 과정 2차</td>
								<td>09.10 ~ 09.14</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">스테디캠 촬영 과정</td>
								<td>09.17 ~ 09.21</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">3D입체 드라마 제작 과정</td>
								<td>09.24 ~ 09.28</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">3D입체 다큐멘터리 제작 과정</td>
								<td>10.08 ~ 10.12</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Nuke와 Ocula를 활용한 3D입체 컴포지팅 과정 4차</td>
								<td>10.08 ~ 10.12</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">해외 워크샵 - 뉴질랜드 WETA</td>
								<td>미정(별도공지)</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Smoke를 활용한 3D입체 제작 과정 3차</td>
								<td>10.15 ~ 10.19</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">3D입체 항공 촬영 과정</td>
								<td>10.22 ~ 10.26</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">P+S Technik리그 테크니션 과정 2차</td>
								<td>10.29 ~ 10.31</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">After Effect를 활용한 3D입체 모션그래픽 2차</td>
								<td>10.29 ~ 10.31</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">3D입체 촬영 과정</td>
								<td>11.07 ~ 11.09</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Nuke와 Ocula를 활용한 3D입체 컴포지팅 과정 5차</td>
								<td>11.12 ~ 11.16</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Avid를 활용한 3D입체 제작 과정 6차</td>
								<td>11.19 ~ 11.23</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Frame Forge를 활용한 3D입체 제작 과정</td>
								<td>12.03 ~ 12.07</td>
								<td>10명</td>
							</tr>
							<tr>
								<td class="coursename">Avid를 활용한 3D입체 제작 과정 7차</td>
								<td>12.17 ~ 12.21</td>
								<td>10명</td>
							</tr>
						</tbody>
					</table>
					
					<table class="curriculum" cellspacing="0" cellpadding="0">
						<caption class="bgD">3D입체콘텐츠 전문인력양성 교육 : <span>3D입체콘텐츠 프로그램 제작과정</span></caption>
						<colgroup><col width="110"><col width="*"><col width="60"></colgroup>
						<thead>
							<tr>
								<th>과정명</th>
								<th>교육내용</th>
								<th>일정</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>3D입체 드라마<br>프로그램<br>제작 과정(4회)</td>
								<td class="coursename">
									- 3D입체 드라마 연출 기법, 케이스 스터디<br>
									- 3D입체용 카메라 2K, 4K, 미러방식, 페러럴방식등<br>&nbsp; &nbsp;촬영기법<br>
									- 3D입체 리그테크니션, Editing & Finishing, VFX<br>
									- 단편 드라마 제작 실습
								</td>
								<td>수시</td>
							</tr>
							<tr class="division">
								<td>3D입체 다큐멘터리<br>프로그램<br>제작 과정(6회)</td>
								<td class="coursename">
									- 3D입체 다큐멘터리 연출, 케이스 스터디<br>
									- 3D입체 다큐멘터리 글로벌 마켓팅 현황<br>
									- 3D입체 특수 촬영 기법, 다큐멘터리 3D Editing 기법<br>
									- 자연 인물 다큐멘터리 제작 실습 
								</td>
								<td>수시</td>
							</tr>
						</tbody>
					</table>
					<h6>※ 본 과정은 별도 공고를 통해 진행될 예정입니다.</h6>
				--></li>
			</ul>
			<div class="clearL"></div>
		</div>
	</div>
</form>
<%@ include file="/learn/user/2012/portal/include/footer.jsp"%>
