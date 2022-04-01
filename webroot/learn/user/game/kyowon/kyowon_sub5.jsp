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
    box.put("leftmenu","05");                       // 메뉴 아이디 세팅
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
        <td height="35" align="right"  background="/images/user/game/course/type1/kyowon/sub_05/tit_all_search.gif" class="location" valign="top" style="padding:17px 10px 0px 0px"><img src="/images/user/game/course/type1/kyowon/location_bl.gif"> HOME > 연수안내 > 출석시험</td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
    </table>

	<!-- 직무연수평가 -->
	<table width="720" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td><img src="/images/user/game/course/type1/kyowon/sub_05/pr_img.jpg"></td>
	  </tr>
	</table>

    <!-- 출석시험 -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
	    <td colspan="2" style="padding:10px 0px 5px 5px"><img src="/images/user/game/course/type1/kyowon/sub_05/tit_01.gif"></td>
	  </tr>
	  <tr>
	    <td width="710" class="02_bold" style="padding:5px 0px 5px 15px" colspan="2">1. 시험일시</td>
	  </tr>
	  <tr>
	    <td width="710" class="01" style="padding:0px 0px 5px 30px" colspan="2">출석시험은 연수 종료일 경에 1회 실시되며, 총점 100점 만점 중 60점을 차지하므로 불참 시에는 연수 이수가 불가능합니다.<BR>기수별 시험일시는 연간연수일정 안내 메뉴를 참조하시기 바랍니다.</td>
	  </tr>
	  <tr>
	    <td width="710" class="02_bold" style="padding:10px 0px 5px 15px" colspan="2">2. 시험장소</td>
	  </tr>
	  <tr>
	    <td style="padding:0px 5px 5px 30px"><img src="/images/user/game/course/type1/kyowon/sub_03/icon.gif"></td>
	    <td width="710" class="01" style="padding:0px 0px 5px 0px">전국 30개 지역에서 실시되며, 세부 고사장은 연수기간 중 별도로 공지됩니다.</td>
	  </tr>
	  <tr>
	    <td style="padding:0px 5px 5px 30px"><img src="/images/user/game/course/type1/kyowon/sub_03/icon.gif"></td>
	    <td width="710" class="01" style="padding:0px 0px 10px 0px">응시 지역은 거주지 관계나 기타 사유로 인하여 다른 지역으로 변경하여 응시가 가능합니다.</td>
	  </tr>
	  </table>

	  <table border="1" cellspacing="0" cellpadding="3" bordercolor="#77a6d6">
	  <tr>
	     <td width="300" class="01" colspan="2"><span class="02">서울권 :</span> 동부/북부, 서초/강남, 마포/신촌, 구로/양천<BR>
																					<span class="02">수도권 :</span> 인천, 수원, 일산, 의정부, 안양, 분당<BR>
																					<span class="02">강원권 :</span> 춘천, 강릉, 원주<BR>
																					<span class="02">충청권 :</span> 대전, 천안, 청주, 예산, 충주<BR>
																					<span class="02">경상권 :</span> 부산, 울산, 창원, 대구, 안동, 진주, 포항<BR>
																					<span class="02">전라권 :</span> 광주, 전주, 순천, 목포<BR>
																					<span class="02">제주권 :</span> 제주<BR>
            </td>
	   </tr>
	 </table>

     <table width="720" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td width="710" class="02_bold" style="padding:20px 0px 5px 15px" colspan="2">3. 시험유형 및 제한시간</td>
	  </tr>
	  <tr>
	    <td width="710" class="01" style="padding:0px 0px 5px 30px" colspan="2">지필평가 형태로 객관식과 단답형 문제가 혼용되어 출제됩니다(총 30문항).<BR>제한시간은 1과목당 1시간입니다.</td>
	  </tr>
	  <tr>
	    <td width="710" class="02_bold" style="padding:10px 0px 5px 15px" colspan="2">4. 점수확인</td>
	  </tr>
	  <tr>
	    <td width="710" class="01" style="padding:0px 0px 40px 30px" colspan="2">출석시험 문제와 정답은 연수종료일 익일에 공개됩니다.</td>
	  </tr>
    </table>

<br><br>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->