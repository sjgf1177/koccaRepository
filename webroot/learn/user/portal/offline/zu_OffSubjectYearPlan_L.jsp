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

<%@ include file="/learn/user/portal/include/top.jsp"%>
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
<form name = "form1" method = "post">
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
      
      <%if( box.getSession("tem_grcode").equals("N000001")) { %>
      
      <table>
      	<tr>
      		<td>
      		
      			<!--타이틀부분-->
    <table width="672" border="0" cellpadding="0" cellspacing="0">
      <tr>
        <td height="30" colspan="2" class="h_road">&nbsp;</td>
      </tr>
      <tr>
        <td><img src="/images/portal/homepage_renewal/offline/stitle_06.gif" alt="연간교육 일정" /></td>
        <td class="h_road">Home &gt; 오프라인 과정  &gt; <strong>연간교육 일정</strong></td>
      </tr>
      <tr>
        <td height="12" colspan="2"></td>
        </tr>
      <tr>
        <td height="1" colspan="2" bgcolor="E5E5E5"></td>
      </tr>
    </table>
    
    
       
<!-- content start -->
<br/>
<p><img src="/images/portal/homepage_renewal/offline/off_title01.gif" alt="차세대 핵심 인력양성 교육"></p><br>
   <table width="100%" border="0" cellspacing="0" cellpadding="0">

      <tr>
        <td colspan="2" class="tit_table">과정명</td>
        <td class="tit_table">교육일정</td>
        <td class="tit_table">기간</td>
        <td class="tit_table_right">인원</td>
      </tr>

      <tr>
        <td rowspan="4" class="ct_tit_table">방송ㆍ영상</td>
        <td class="ct_tit_table_left">VFX (특수영상)</td>
        <td class="ct_tit_table">2012년 3월~12월</td>
        <td class="ct_tit_table">9개월</td>
        <td class="ct_tit_table_right">15명</td>
      </tr>

      <tr>
        <td class="ct_tit_table_left">모션 그래픽</td>
        <td class="ct_tit_table">2012년 3월~12월</td>
        <td class="ct_tit_table">9개월 </td>
        <td class="ct_tit_table_right">15명</td>        
      </tr>

      <tr>
        <td class="ct_tit_table_left">디지털 영상편집</td>
        <td class="ct_tit_table">2012년 3월~8월</td>
        <td class="ct_tit_table">6개월</td>
        <td class="ct_tit_table_right">30명</td>        
      </tr>

      <tr>
        <td class="ct_tit_table_left">디지털 음향제작</td>
        <td class="ct_tit_table">2012년 3월~8월</td>
        <td class="ct_tit_table">6개월</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>



      <tr>
        <td rowspan="4" class="ct_tit_table">게임</td>
        <td class="ct_tit_table_left">게임 디자인(기획) </td>
        <td class="ct_tit_table">2012년 3월~12월</td>
        <td class="ct_tit_table">10개월</td>
        <td class="ct_tit_table_right">12명</td>        
      </tr>
      <tr>
        <td class="ct_tit_table_left">게임 프로그램</td>
        <td class="ct_tit_table">2012년 3월~12월</td>
        <td class="ct_tit_table">10개월</td>
        <td class="ct_tit_table_right">18명</td>        
      </tr>
      <tr>
        <td class="ct_tit_table_left">게임 그래픽</td>
        <td class="ct_tit_table">2012년 3월~12월</td>
        <td class="ct_tit_table"> 10개월</td>
        <td class="ct_tit_table_right">15명</td>
        
      </tr>
      <tr>
        <td class="ct_tit_table_left">게임 프로젝트</td>
        <td class="ct_tit_table">2012년 3월~12월</td>
        <td class="ct_tit_table"> 10개월</td>
        <td class="ct_tit_table_right">30명</td>        
      </tr>



      <tr>
        <td class="ct_tit_table">기획ㆍ창작</td>
        <td class="ct_tit_table_left">콘텐츠 기획창작</td>
        <td class="ct_tit_table">2012년 3월~12월</td>
        <td class="ct_tit_table"> 10개월</td>
        <td class="ct_tit_table_right">20명</td>        
      </tr>

	  <tr>
        <td class="ct_tit_table">비즈니스</td>
        <td class="ct_tit_table_left">국제콘텐츠 비즈니스</td>
        <td class="ct_tit_table">2012년 3월~11월</td>
        <td class="ct_tit_table"> 9개월</td>
        <td class="ct_tit_table_right">15명</td>
      </tr>


      <tr>
        <td rowspan="3" class="ct_tit_table">3D입체영상</td>
        <td class="ct_tit_table_left">Pre-Production(기획/연출)</td>
        <td class="ct_tit_table">2012년 3월~12월</td>
        <td class="ct_tit_table">10개월</td>
        <td class="ct_tit_table_right">10명</td>
        
      </tr>

      <tr>
        <td class="ct_tit_table_left">Production(촬영)</td>
        <td class="ct_tit_table">2012년 3월~12월</td>
        <td class="ct_tit_table"> 10개월</td>
        <td class="ct_tit_table_right">15명</td>
        
      </tr>

      <tr>
        <td class="ct_tit_table_left">Post-Production(편집/VFX)</td>
        <td class="ct_tit_table">2012년 3월~12월</td>
        <td class="ct_tit_table"> 10개월</td>
        <td class="ct_tit_table_right">20명</td>
        
      </tr>


      <tr>
        <td height="1" colspan="5" bgcolor="#CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="5" bgcolor="#F7F7F7"></td>
      </tr>

    </table>


<br><br>
<p><img src="/images/portal/homepage_renewal/offline/off_title02.gif" alt="산업계 직무 재교육"></p><br>

  <table width="100%" border="0" cellspacing="0" cellpadding="0">

      <tr>
        <td class="tit_table">구분</td>
        <td class="tit_table">과정명</td>
        <td class="tit_table">교육일정</td>
        <td class="tit_table_right">인원</td>        
      </tr>

      <tr>
        <td rowspan="11" class="ct_tit_table">국가인적자원개발<br>
중소기업<br>
컨소시엄<br>
직무과정<br>
(노동부)<br>
</td>
        <td class="ct_tit_table_left">Final Cut Pro과정</td>
        <td class="ct_tit_table">01.09 ~ 01.13</td>
        <td class="ct_tit_table_right">5명</td>        
      </tr>

      <tr>
        <td class="ct_tit_table_left">iPhone 제작과정</td>
        <td class="ct_tit_table">01.09 ~ 01.13</td>
        <td class="ct_tit_table_right">15명</td>
        
      </tr>

      <tr>
        <td class="ct_tit_table_left">콘텐츠 기획서 작성 전략  과정</td>
        <td class="ct_tit_table">02.20 ~ 02.21</td>
        <td class="ct_tit_table_right">15명</td>        
      </tr>

	  <tr>
        <td class="ct_tit_table_left">비즈니스 스킬업 과정</td>
        <td class="ct_tit_table">02.01 ~ 02.02</td>
        <td class="ct_tit_table_right">5명</td>        
      </tr>


      <tr>
        <td class="ct_tit_table_left">Protools 강사과정</td>
        <td class="ct_tit_table">02.13 ~ 02.17</td>
        <td class="ct_tit_table_right">5명</td>        
      </tr>


	  <tr>
        <td class="ct_tit_table_left">디지털미디어 워크플로우 과정</td>
        <td class="ct_tit_table">03.12 ~ 03.14</td>
        <td class="ct_tit_table_right">5명</td>        
      </tr>

      <tr>
        <td class="ct_tit_table_left">레벨 디자인 방법론 과정</td>
        <td class="ct_tit_table">04.04 ~ 04.06</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>

      <tr>
        <td class="ct_tit_table_left">콘텐츠기획(영화,드라마,웹툰,애니,공연)과정</td>
        <td class="ct_tit_table">05.08 ~ 05.10</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>

	  <tr>
        <td class="ct_tit_table_left">Avid MC 101과정</td>
        <td class="ct_tit_table">02.06 ~ 02.10</td>
        <td class="ct_tit_table_right">5명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Final Cut Pro과정</td>
        <td class="ct_tit_table">02.20 ~ 02.24</td>
        <td class="ct_tit_table_right">5명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">상표권, 저작권, 라이센싱  계약 사례 과정</td>
        <td class="ct_tit_table">06.11 ~ 06.15</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>

      <tr>
        <td rowspan="6" class="ct_tit_table">문화콘텐츠<br>직무과정
		</td>
        <td class="ct_tit_table_left">창의적 콘텐츠 소재발견을 위한 세미나</td>
        <td class="ct_tit_table">05.16 ~ 05.17</td>
        <td class="ct_tit_table_right">15명</td>        
      </tr>

	  <tr>
        <td class="ct_tit_table_left">다문화 콘텐츠 제작의 이해</td>
        <td class="ct_tit_table">5월 중</td>
        <td class="ct_tit_table_right">15명</td>
      </tr>
	  <tr>
        <td class="ct_tit_table_left">국제다큐멘터리 공동제작 가이드</td>
        <td class="ct_tit_table">06.19 ~ 06.20</td>
        <td class="ct_tit_table_right">20명</td>
      </tr>
	  <tr>
        <td class="ct_tit_table_left">OSMU를 위한 앱 창작과정</td>
        <td class="ct_tit_table">09.03 ~ 10.12</td>
        <td class="ct_tit_table_right">10명</td>
      </tr>
	  <tr>
        <td class="ct_tit_table_left">공연예술, 기획에서 수출까지</td>
        <td class="ct_tit_table">10.16 ~ 10.17</td>
        <td class="ct_tit_table_right">20명</td>
      </tr>
	  <tr>
        <td class="ct_tit_table_left">청소년을 위한 창의과정 해피미디어 세상</td>
        <td class="ct_tit_table">10월 중</td>
        <td class="ct_tit_table_right">15명</td>
      </tr>

      <tr>
        <td height="1" colspan="5" bgcolor="#CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="5" bgcolor="#F7F7F7"></td>
      </tr>

    </table>


<br><br>
<p><img src="/images/portal/homepage_renewal/offline/off_title03.gif" alt="3D입체콘텐츠 전문인력양성 교육"></p>
<p style="padding:35px 0px 10px 10px;">
	<img src="/images/portal/homepage_renewal/offline/off_dot.gif"><b style="padding-left:5px; color:#5f7292; font-size:13px;">현업인 대상 3D입체 전환 재교육(단기과정)</b>
</p>


<table width="100%" border="0" cellspacing="0" cellpadding="0">

      <tr>        
        <td colspan="2" class="tit_table">과정명</td>
        <td class="tit_table">교육일정</td>
        <td class="tit_table_right">인원</td>        
      </tr>

      <tr>
        <td rowspan="35" class="ct_tit_table">3D입체콘텐츠<br>전문인력양성</td>
        <td class="ct_tit_table_left">Smoke를 활용한 3D입체 제작 과정 1차</td>
        <td class="ct_tit_table">04.16  ~ 04.20</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
      <tr>
        <td class="ct_tit_table_left">Avid를 활용한 3D입체 제작 과정 1차</td>
        <td class="ct_tit_table">04.23  ~ 04.27</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
      <tr>
        <td class="ct_tit_table_left">스테레오그래퍼 과정 1차</td>
        <td class="ct_tit_table">04.23  ~ 04.27</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">3ALITY 리그 테크니션 과정 1차</td>
        <td class="ct_tit_table">05.02  ~ 05.04</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
      <tr>
        <td class="ct_tit_table_left">3D입체 특수촬영 과정</td>
        <td class="ct_tit_table">05.14  ~ 05.18</td>
        <td class="ct_tit_table_right">10명</td>
      </tr>
	  <tr>
        <td class="ct_tit_table_left">P+S Technik 리그 테크니션 과정 1차</td>
        <td class="ct_tit_table">05.14  ~ 05.18</td>
        <td class="ct_tit_table_right">10명</td>
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Avid를 활용한 3D입체 제작 과정 2차</td>
        <td class="ct_tit_table">05.21  ~ 05.25</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">3ALITY Rig Technician Instructor</td>
        <td class="ct_tit_table">05.29  ~ 05.31</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Nuke와 Ocula를 활용한 3D입체 컴포지팅 과정 1차</td>
        <td class="ct_tit_table">05.29  ~ 05.31</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Cinema4D를  활용한 3D입체 제작 과정 1차</td>
        <td class="ct_tit_table">06.11  ~ 06.15</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">3D입체 수중촬영 과정</td>
        <td class="ct_tit_table">06.18  ~ 06.22</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Avid를 활용한 3D입체 제작 과정 3차</td>
        <td class="ct_tit_table">06.25  ~ 06.29</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">3D입체 교수워크숍</td>
        <td class="ct_tit_table">06.28  ~ 07.06</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">3ALITY 리그 테크니션 과정 2차</td>
        <td class="ct_tit_table">07.04  ~ 07.06</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Smoke를 활용한 3D입체 제작 과정 2차</td>
        <td class="ct_tit_table">07.09  ~ 07.13</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Avid를 활용한 3D입체 제작 과정 4차</td>
        <td class="ct_tit_table">07.23  ~ 07.27</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Nuke와 Ocula를 활용한 3D입체 컴포지팅 과정 2차</td>
        <td class="ct_tit_table">07.30  ~ 08.03</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Avid를  활용한 3D입체 제작 과정 5차</td>
        <td class="ct_tit_table">08.20  ~ 08.24</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">After Effect를 활용한 3D입체 모션그래픽 1차</td>
        <td class="ct_tit_table">08.27  ~ 08.31</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Nuke와  Ocula를 활용한 3D입체 컴포지팅 과정 3차</td>
        <td class="ct_tit_table">09.03  ~ 09.07</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">스테레오그래퍼 과정 2차</td>
        <td class="ct_tit_table">09.10  ~ 09.14</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">스테디캠 촬영 과정</td>
        <td class="ct_tit_table">09.17  ~ 09.21</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">3D입체 드라마 제작 과정</td>
        <td class="ct_tit_table">09.24  ~ 09.28</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">3D입체  다큐멘터리 제작 과정</td>
        <td class="ct_tit_table">10.08  ~ 10.12</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Nuke와 Ocula를 활용한 3D입체 컴포지팅 과정 4차</td>
        <td class="ct_tit_table">10.08  ~ 10.12</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">해외 워크샵 - 뉴질랜드 WETA</td>
        <td class="ct_tit_table">미정(별도공지)</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Smoke를 활용한 3D입체 제작 과정 3차</td>
        <td class="ct_tit_table">10.15  ~ 10.19</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">3D입체 항공 촬영 과정</td>
        <td class="ct_tit_table">10.22  ~ 10.26</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">P+S Technik리그 테크니션 과정 2차</td>
        <td class="ct_tit_table">10.29  ~ 10.31</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">After Effect를 활용한 3D입체 모션그래픽 2차</td>
        <td class="ct_tit_table">10.29  ~ 10.31</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">3D입체  촬영 과정</td>
        <td class="ct_tit_table">11.07  ~ 11.09</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Nuke와 Ocula를 활용한 3D입체 컴포지팅 과정 5차</td>
        <td class="ct_tit_table">11.12  ~ 11.16</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Avid를 활용한 3D입체 제작 과정 6차</td>
        <td class="ct_tit_table">11.19  ~ 11.23</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Frame  Forge를 활용한 3D입체 제작 과정</td>
        <td class="ct_tit_table">12.03  ~ 12.07</td>
        <td class="ct_tit_table_right">10명</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table_left">Avid를 활용한 3D입체 제작 과정 7차</td>
        <td class="ct_tit_table">12.17  ~ 12.21</td>
        <td class="ct_tit_table_right">10명</td>
      </tr>



      <tr>
        <td height="1" colspan="5" bgcolor="#CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="5" bgcolor="#F7F7F7"></td>
      </tr>

    </table>

<p style="padding:45px 0px 10px 10px;">
	<img src="/images/portal/homepage_renewal/offline/off_dot.gif"><b style="padding-left:5px; color:#5f7292; font-size:13px;">3D입체콘텐츠 해외강사 초청 워크샵</b>
</p>
<table width="100%" border="0" cellspacing="0" cellpadding="0">

      <tr>
        <td class="tit_table">과정명</td>
        <td class="tit_table">교육내용</td>
        <td class="tit_table_right">일정</td>
      </tr>

      <tr>
        <td class="ct_tit_table">VFX슈퍼바이저 초정</td>
        <td class="ct_tit_table_left">해외 유명 거장 초청을 통한 3D입체 VFX세미나</td>
        <td rowspan="3" class="ct_tit_table_right">별도공지예정</td>        
      </tr>
      <tr>
		<td class="ct_tit_table">S3D Director 초청 워크샵</td>
        <td class="ct_tit_table_left">입체영상의 효과적인 기획과 연출을 위한 전문가 초청 워크샵</td>           
      </tr>
      <tr>
	    <td class="ct_tit_table">S3D 비즈매칭 세미나</td>
        <td class="ct_tit_table_left">3D입체 콘텐츠 해외 진출을 위한 글로벌 제작 가이드라인 세미나</td>        
      </tr>

      <tr>
        <td height="1" colspan="3" bgcolor="#CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="3" bgcolor="#F7F7F7"></td>
      </tr>

    </table>

<p style="padding:45px 0px 10px 10px;">
	<img src="/images/portal/homepage_renewal/offline/off_dot.gif"><b style="padding-left:5px; color:#5f7292; font-size:13px;">대학별 3D입체콘텐츠 전환 재교육</b>
</p>


<table width="100%" border="0" cellspacing="0" cellpadding="0">

      <tr>
        <td class="tit_table">과정명</td>
        <td class="tit_table">교육내용</td>
        <td class="tit_table_right">일정</td>
      </tr>
      <tr>
        <td class="ct_tit_table">대학재학생 3D입체콘텐츠<br>전환 재교육 과정</td>
        <td class="ct_tit_table_left">
			-S3D Terminology<br>
			-3D Design<br>
			-프리비즈<br>
		</td>
        <td class="ct_tit_table_right">여름 및 겨울방학</td>        
      </tr>

      <tr>
        <td height="1" colspan="3" bgcolor="#CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="3" bgcolor="#F7F7F7"></td>
      </tr>

    </table>
	<p style="color:#5f7292; font-size:12px;">※ 본 과정은 별도 공고를 통해 진행될 예정입니다.</p>

<p style="padding:45px 0px 10px 10px;">
	<img src="/images/portal/homepage_renewal/offline/off_dot.gif"><b style="padding-left:5px; color:#5f7292; font-size:13px;">3D입체콘텐츠 프로그램 제작과정</b>
</p>
	<table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="tit_table">과정명</td>
        <td class="tit_table">교육내용</td>
        <td class="tit_table_right">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;일정&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
      </tr>
      <tr>
        <td class="ct_tit_table">3D입체 드라마<br>프로그램 제작  과정(4회)</td>
        <td class="ct_tit_table_left">
			-3D입체 드라마 연출 기법, 케이스 스터디<br>
			-3D입체용 카메라 2K, 4K, 미러방식, 페러럴방식등 촬영기법<br>
			-3D입체 리그테크니션, Editing & Finishing, VFX<br>
			-단편 드라마 제작 실습
		</td>
        <td class="ct_tit_table_right">수시</td>        
      </tr>
	  <tr>
        <td class="ct_tit_table">3D입체 다큐멘터리<br>프로그램 제작 과정(6회)</td>
        <td class="ct_tit_table_left">
			-3D입체 다큐멘터리 연출, 케이스 스터디<br>
			-3D입체 다큐멘터리 글로벌 마켓팅 현황<br>
			-3D입체 특수 촬영 기법, 다큐멘터리 3D Editing 기법<br>
			-자연 인물 다큐멘터리 제작 실습
		</td>
        <td class="ct_tit_table_right">수시</td>        
      </tr>

      <tr>
        <td height="1" colspan="3" bgcolor="#CACACA"></td>
      </tr>
      <tr>
        <td height="4" colspan="3" bgcolor="#F7F7F7"></td>
      </tr>
   </table>
	<p style="color:#5f7292; font-size:12px;">※ 본 과정은 별도 공고를 통해 진행될 예정입니다.</p><br>
	<!-- content end -->
    <!--타이틀부분//-->
    
    </td>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
      		      
		      </tr>		
		</table>
    
            
      
     
      <%}else{ %>      
      
            <h2><img src="/images/portal/online/h2_tit5.gif" alt="연간교육일정" class="fl_l" /><p class="category">Home > 오프라인과정 > <strong>연간교육일정</strong></p></h2>                                     
                                                                                                                                                                                                 
            <p><img src="/images/portal/online/year_txt.gif" alt="온라인 과정 연간교육일정입니다. 교육과정명을 클릭하시면 과정내용을 보실 수 있으며, 월을 클릭하시면 해당월의 교육과정을 보실 수 있습니다 " /></p>

            <ul class="tabwrap mg_t30">
            <%  if(upperList != null && upperList.size() > 0) {
                    for(int i = 0 ; i < upperList.size() ; i++){ 
                           DataBox dbox = upperList.get(i); %>
                <li><a <%=v_upperclass.equals(dbox.get("d_upperclass")) ?  "class=\"tab_online tab_online_on\"" : "href=\"javascript:moveTab('"+dbox.get("d_upperclass")+"');\" class=\"tab_online\""%>><span><%=dbox.get("d_classname") %></span></a></li>
            <%      if(v_upperclass.equals(dbox.get("d_upperclass"))) v_classname =  dbox.get("d_classname");
                    }
                } %>
            </ul>
            
            <p class="board_top_search">
                <img src="/images/portal/board/serbox_category.gif"> <kocca_select:select name="p_middleclass" sqlNum="off.0003"  param="<%= v_upperclass %>"
                                                    onChange="changeYear()" styleClass="mg_r12" attr=" " selectedValue="<%= v_middleclass %>" isLoad="true" all="true" />
                <img src="/images/portal/board/serbox_year.gif  ">
                    <select name="p_year" class="mg_r12" onchange="changeYear();">
                        <option value="<%=Integer.parseInt(currentYear)+1 %>" <%=v_gyear.equals(""+(Integer.parseInt(currentYear)+1)) ? "selected" : ""%>><%=Integer.parseInt(currentYear)+1 %></option>
                        <option value="<%=currentYear %>"   <%=v_gyear.equals(currentYear) ? "selected" : ""%>><%=currentYear %></option>
                        <option value="<%=Integer.parseInt(currentYear)-1 %>" <%=v_gyear.equals(""+(Integer.parseInt(currentYear)-1)) ? "selected" : ""%>><%=Integer.parseInt(currentYear)-1 %></option>
                    </select>
            </p>
            
            <table class="row_list_prg" >
            <colgroup>
                <col width="138px" />
                <col width="45px" />
                <col width="45px" />
                <col width="45px" />
                <col width="45px" />
                <col width="45px" />
                <col width="45px" />
                <col width="45px" />
                <col width="45px" />
                <col width="45px" />
                <col width="49px" />
                <col width="49px" />
                <col width="49px" />
            </colgroup>
            <thead>
            <tr>
                <th>교육과정<br /><span class="stit">(총 <%=subCnt %>개 과정)</span></th>
                <th><a href="javascript:moveMonthList('01');" class="btn_wred"><span>1월</span></a></th>
                <th><a href="javascript:moveMonthList('02');" class="btn_wred"><span>2월</span></a></th>
                <th><a href="javascript:moveMonthList('03');" class="btn_wred"><span>3월</span></a></th>
                <th><a href="javascript:moveMonthList('04');" class="btn_wred"><span>4월</span></a></th>
                <th><a href="javascript:moveMonthList('05');" class="btn_wred"><span>5월</span></a></th>
                <th><a href="javascript:moveMonthList('06');" class="btn_wred"><span>6월</span></a></th>
                <th><a href="javascript:moveMonthList('07');" class="btn_wred"><span>7월</span></a></th>
                <th><a href="javascript:moveMonthList('08');" class="btn_wred"><span>8월</span></a></th>
                <th><a href="javascript:moveMonthList('09');" class="btn_wred"><span>9월</span></a></th>
                <th><a href="javascript:moveMonthList('10');" class="btn_wred"><span>10월</span></a></th>
                <th><a href="javascript:moveMonthList('11');" class="btn_wred"><span>11월</span></a></th>
                <th><a href="javascript:moveMonthList('12');" class="btn_wred"><span>12월</span></a></th>
            </tr>
            </thead>
            <tbody>
            <%
            if(listSize > 0 ) {
            	String temp_subjseq = "1";
            	String temp_seq = "1";
	            for(int i = 0; i < listSize;) {
	                DataBox dbox = (DataBox)offSchlYearPlanList.get(i);
	
	                v_subj           = dbox.getString("d_subj");        
	                v_year           = dbox.getString("d_year");
	                v_subjnm         = dbox.getString("d_subjnm");  
	                v_upperclass     = dbox.getString("d_upperclass");
                	
                	v_subj_cnt       = dbox.getInt("d_subj_cnt");
                	
                	v_menuid = upperMap !=null && upperMap.get(v_upperclass) != null ?  upperMap.get(v_upperclass) : "00";
                	
                	// 과정중 수강신청 가능한 차수와 SEQ를 링크, 수강신청 가능한 차수가 없으면 1,1 로 설정
                	if(dbox.getString("d_status").equals("Y")) {
                		temp_subjseq = dbox.getString("d_subjseq");
                		temp_seq = dbox.getString("d_seq");
                	}

	        %>
	        <tr>
                <td class="ta_l"><a href="javascript:whenSubjInfo('<%=v_subj %>', '<%=temp_subjseq %>', '<%=temp_seq %>', '<%=v_menuid %>')"><%=v_subjnm %></a></td>
            <%  for(int k = 1 ; k <= 12 ; k++){ %>
                <td class="data<%=k == 1 || k%2 == 1 ?  " board_bg" : ""%>">
	        <%        
	                for(int j = 0 ; j < v_subj_cnt ; j++){
	                	dbox = (DataBox)offSchlYearPlanList.get(i+j);
	                	v_month_cnt   = dbox.getInt("d_month_"+(k < 10 ? "0"+k : "k")+"_cnt");
	                	if(v_month_cnt != 0) {
	                		v_status         = dbox.getString("d_status");
	                		v_edustart       = dbox.getString("d_edustart");    
	                        v_eduend         = dbox.getString("d_eduend");   
	                        v_subjseq        = dbox.getString("d_subjseq");
	                        v_seq            = dbox.getString("d_seq");
	                        if(v_status.equals("Y")) {
	                        	if( !box.getSession("userid").equals("") ) {
	                            	v_status = "<a href=\"javascript:whenSubjPropose('"+v_subj+"','"+v_year+"','"+v_subjseq+"','"+v_subjnm+"','"+v_seq+"')\" class=\"board_btn\"><span class=\"pd_l6 pd_r5\">신청</span></a>";
	                        	} else {
	                        		v_status = "<a href=\"javascript:alert('로그인후 이용하실수 있습니다.')\" class=\"board_btn\"><span class=\"pd_l6 pd_r5\">신청</span></a>";
	                        	}
	                        } else {
	                            v_status = "";
	                        }
	                        
	                        int v_edustartMonth = Integer.parseInt(v_edustart.substring(4,6));
	                        int v_edustartDay = Integer.parseInt(v_edustart.substring(6,8));
	                        
	                        int v_eduendYear = Integer.parseInt(v_eduend.substring(0,4));
	                        int v_eduendMonth = Integer.parseInt(v_eduend.substring(4,6));
	                        int v_eduendDay = Integer.parseInt(v_eduend.substring(6,8));
	                        
	                        if(v_eduendYear > Integer.parseInt(v_year)){
	                        	v_eduend  = v_eduendMonth+"."+ v_eduendDay;
	                        } else if(v_eduendMonth > v_edustartMonth){
	                        	v_eduend  = v_eduendMonth+"."+ v_eduendDay;
	                        } else {
	                        	v_eduend = ""+v_eduendDay;
	                        }
	                        
	                        v_edustart = ""+v_edustartDay;
	                        
	                		v_edustart  = "<p"+(j > 0 ? "  class=\"mg_t20\"" : "")+" style='margin-top:0px;font-size:8px;'>"+v_edustart+"~"+v_eduend+"</p>";
	                		out.println(v_edustart+v_status);
	                	}
	                }
            %>
                </td>
            <% }  %>
            </tr>
            <% i += v_subj_cnt;
                }
            } else { %>
              <tr>
                <td colspan="13">등록된 내용이 없습니다</td>
              </tr>
            <%  } %>
            </tbody>
            </table>
            
            <%} %>
            
</form>
<!-- Form 영역 종료 -->


<!-- footer 영역 시작 -->
<%@ include file="/learn/user/portal/include/footer.jsp"%>
<!-- footer 영역 종료 -->