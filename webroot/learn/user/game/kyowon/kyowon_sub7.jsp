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
    box.put("leftmenu","07");                       // 메뉴 아이디 세팅
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
        <td height="35" align="right"  background="/images/user/game/course/type1/kyowon/sub_07/tit_all_search.gif" class="location" valign="top" style="padding:17px 10px 0px 0px"><img src="/images/user/game/course/type1/kyowon/location_bl.gif"> HOME > 연수안내 > 연간연수일정</td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
    </table>

	<!-- 연간연수일정 -->
	<table width="720" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="20" align="center" style="padding:0px 0px 0px 10px"><img src="/images/user/game/course/type1/kyowon/sub_07/icon.gif"></td>
		<td width="700" class="01" style="padding:5px 0px 5px 0px">2008년도 원격직무연수(4학점, 2학점) 연간연수일정 입니다.</td>
	  </tr>
	  <tr>
	    <td width="20" align="center" style="padding:0px 0px 0px 10px"><img src="/images/user/game/course/type1/kyowon/sub_07/icon.gif"></td>
		<td width="700" class="01" style="padding:5px 0px 5px 0px">수강등록은 온라인으로 신청하시고 연수지명 번호를 입력하여 주시기 바랍니다.</td>
	  </tr>
	  <tr>
	    <td colspan="2" class="01_bold" style="padding:5px 0px 5px 20px">* 출석시험장소: 전국 30개 고사장 동시 실시</td>
	  </tr>
	  <tr>
	    <td colspan="2" class="01_1" style="padding:0px 0px 10px 20px">서울(서초/강남), 서울(동부/북부), 서울(마포/신촌), 서울(구로/양천), 의정부, 수원, 인천, 안양, 부산, 대구, 대전<BR>청주, 예산, 광주, 목포, 전주, 울산, 창원, 진주, 안동, 원주, 춘천, 강릉, 제주, 순천, 천안, 일산, 분당, 충주, 포항</td>
	  </tr>
	  </table>

	   <!-- 표 -->
		  <table width="700" border="1" cellspacing="0" cellpadding="3" bordercolor="#d2d2d2">
		  <TR class="01" align="center" bgcolor="#cfe1f3">
			<TD>진행기수</TD>
			<TD>구분</TD>
			<TD>수강신청기간</TD>
			<TD colspan="2">연수기간</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD rowspan="2" bgcolor="#f2f8fd">겨울방학</TD>
			<TD rowspan="2" bgcolor="#e8faff">2학점, 3주</TD>
			<TD>2007.11.19~2008. 01. 13</TD>
			<TD bgcolor="#e8faff">1차</TD>
			<TD>2008.01.14~2008.02.03</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD>2007.11.19~2008. 02. 03</TD>
			<TD bgcolor="#e8faff">2차</TD>
			<TD>2008.02.04~2008.02.24</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD bgcolor="#f2f8fd">특별학기</TD>
			<TD bgcolor="#e8faff">2학점. 3주</TD>
			<TD>2007.02.04~2008. 02. 24</TD>
			<TD bgcolor="#e8faff">3차</TD>
			<TD>2008.02.25~2008.03.16</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD rowspan="4" bgcolor="#f2f8fd">상반기<BR>(1학기)</TD>
			<TD rowspan="2" bgcolor="#e8faff">2학점, 3주</TD>
			<TD>2008.02.11~2008.04.06</TD>
			<TD bgcolor="#e8faff">4차</TD>
			<TD>2008.04.07~2008.04.27</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD>2008.02.11~2008.04.27</TD>
			<TD bgcolor="#e8faff">5차</TD>
			<TD>2008.04.28~2008.05.18</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD rowspan="2" bgcolor="#e8faff">2학점, 3주</TD>
			<TD>2008.04.21~2008.05.25</TD>
			<TD bgcolor="#e8faff">6차</TD>
			<TD>2008.05.26~2008.06.15</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD>2008.04.21~2008.06.15</TD>
			<TD bgcolor="#e8faff">7차</TD>
			<TD>2008.06.16~2008.07.06</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD rowspan="2" bgcolor="#f2f8fd">여름방학</TD>
			<TD rowspan="2" bgcolor="#e8faff">2학점, 3주</TD>
			<TD>2008.06.09~2008.07.13</TD>
			<TD bgcolor="#e8faff">8차</TD>
			<TD>2008.07.14~2008.08.03</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD>2008.06.09~2008.08.03</TD>
			<TD bgcolor="#e8faff">9차</TD>
			<TD>2008.08.04~2008.08.24</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD rowspan="4" bgcolor="#f2f8fd">하반기<BR>(2학기)</TD>
			<TD rowspan="2" bgcolor="#e8faff">2학점, 3주</TD>
			<TD>2008.08.11~2008.09.21</TD>
			<TD bgcolor="#e8faff">10차</TD>
			<TD>2008.09.22~2008.10.12</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD>2008.08.11~2008.10.12</TD>
			<TD bgcolor="#e8faff">11차</TD>
			<TD>2008.10.13~2008.11.02</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD rowspan="2" bgcolor="#e8faff">2학점, 3주</TD>
			<TD>2008.10.06~2008.11.09</TD>
			<TD bgcolor="#e8faff">12차</TD>
			<TD>2008.11.10~2008.11.30</TD>
		  </TR>
		  <TR class="01" align="center">
			<TD>2008.10.06~2008.11.30</TD>
			<TD bgcolor="#e8faff">13차</TD>
			<TD>2008.12.01~2008.12.21</TD>
		  </TR>
		  </table>
	<table width="720" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="20" align="center" style="padding:0px 0px 0px 10px"><img src="/images/user/game/course/type1/kyowon/icon.gif"></td>
		<td width="700" class="01_1" style="padding:10px 0px 5px 0px">연수일정은 연수사정이나 방학기간 조정에 따라 다소 조정될 수 있습니다.</td>
	  </tr>
	  <tr>
	    <td colspan="2" align="right"><img src="/images/user/game/course/type1/kyowon/sub_07/b_img.gif"></td>
	  </tr>
	</table>

<br><br>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->