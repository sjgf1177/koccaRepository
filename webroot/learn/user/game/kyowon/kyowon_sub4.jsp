<%
//**********************************************************
//  1. 제      목: 교육일정 리스트
//  2. 프로그램명:  gu_EducationSchedule_L.jsp
//  3. 개      요: 교육일정 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 12. 20
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process         = box.getString("p_process");
    box.put("leftmenu","04");                       // 메뉴 아이디 세팅
    String  ss_year    = box.getStringDefault("s_year",FormatDate.getDate("yyyy"));

    String  v_upperclass      =  "";
    String  v_middleclass     =  "";
    String  v_lowerclass      =  "";
    String  v_isonoff         =  "";
    String  v_course          =  "";
    String  v_coursenm        =  "";
    String  v_subj            =  "";
    String  v_year            =  "";
    String  v_subjseq         =  "";
    String  v_subjseqgr       =  "";
    String  v_subjnm          =  "";
    String  v_classnm         =  "";
    String  v_propstart       =  "";
    String  v_propend         =  "";
    int     v_biyong          =  0;
    String  v_eduperiod       =  "";

    String  v_isonoff_value   =  "";
    String  v_propstart_value =  "";
    String  v_propend_value   =  "";
    int     v_total           =  0;
    int     i                 =  0;
    DecimalFormat  df = new DecimalFormat("###,##0");

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topKyowonCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenSelect(){
        document.form1.p_process.value = 'EducationSchedule';
        document.form1.action='/servlet/controller.propose.MainSubjSearchServlet';
        document.form1.target = "_self";
        document.form1.submit();
    }

// 과정 내용보기
function whenSubjInfo(subj){
    document.form1.p_subj.value = subj;
    document.form1.p_process.value  = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'EducationSchedule';
    document.form1.action='/servlet/controller.course.CourseIntroServlet';
    document.form1.target = "_self";
    document.form1.submit();
}

// 일반/전문가, 대분류코드 선택
function whenSelect(sel,gubun){
    document.form1.p_iscourseYn.value = sel;
    document.form1.p_gubun.value   = gubun;
    document.form1.p_tabnum.value  = gubun;
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.propose.ProposeCourseServlet';
    document.form1.p_process.value = 'SubjectList';

    document.form1.submit();
}
//-->
</SCRIPT>

    <!-- title -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="35" align="right"  background="/images/user/game/course/type1/kyowon/sub_04/tit_all_search.gif" class="location" valign="top" style="padding:17px 10px 0px 0px"><img src="/images/user/game/course/type1/kyowon/location_bl.gif"> HOME > 연수안내 > 학습방법</td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
    </table>

	<!-- 직무연수학습 -->
	<table width="720" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td><img src="/images/user/game/course/type1/kyowon/sub_04/pr_img.jpg"></td>
	  </tr>
	</table>

    <!-- 연수 시작전 숙지사항 -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
	    <td><img src="/images/user/game/course/type1/kyowon/sub_04/tit_img.gif"></td>
	  </tr>
	  <!--
	  <tr>
	    <td width="710" class="02_bold" style="padding:5px 0px 5px 15px">1. 공지사항 확인</td>
	  </tr>
	  <tr>
	    <td width="710" class="01" style="padding:0px 0px 5px 15px">Classroom에 입장하시면, 반드시 연수일정과 공지사항을 먼저 확인하셔야 합니다.<BR>‘학사공지’란에는 과제/시험 일정 및 연수와 관련된 주요 공지사항이 안내되므로, 연수기간 중에도 수시로<BR>확인해보셔야 합니다. </td>
	  </tr>
	  -->
	  <tr>
	    <td width="710" class="02_bold" style="padding:10px 0px 5px 15px">1. 학습방법 및 진도체크</td>
	  </tr>
	  <tr>
	    <td width="710" class="01" style="padding:0px 0px 5px 15px">강의는 HTML, Flash, 동영상 등이 혼합된 형태인 원격연수 콘텐츠를 기본으로 하며, 연수과정에 따라 교재(책)가<BR>제공되거나, 다운로드용 파일 형태로 제공됩니다.<BR>(단, 정보화/자격증 연수는 별도로 지정된 교재가 없습니다.)<BR>[온라인교재]는 연수기간(6주 또는 7주) 동안 1주일(월요일~일요일) 단위로 오픈이 되며,<BR> 1일 평균 학습 시간은 1.5~2시간 정도 입니다.<BR><BR>출석체크는 별도로 하지 않고, [온라인교재] 진도율이 페이지마다 누적되기 때문에 매일매일 접속하지 않으셔도 됩니다.</td>
	  </tr>
	  <tr>
	    <td width="710" class="02_bold" style="padding:10px 0px 5px 15px">2. 본인 학습 현황 확인</td>
	  </tr>
	  <tr>
	    <td width="710" class="01" style="padding:0px 0px 20px 15px">"나의 강의실" 메뉴에서 진도율 및 과제/시험, 출석시험, 상대평가 점수를 확인할 수 있습니다.</td>
	  </tr>
    </table>

<br><br>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->