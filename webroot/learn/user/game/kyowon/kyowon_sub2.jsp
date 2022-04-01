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
    box.put("leftmenu","02");                       // 메뉴 아이디 세팅
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
        <td height="35" align="right"  background="/images/user/game/course/type1/kyowon/sub_02/tit_all_search.gif" class="location" valign="top" style="padding:17px 10px 0px 0px"><img src="/images/user/game/course/type1/kyowon/location_bl.gif"> HOME > 연수안내 > 연수신청</td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
    </table>

	<!-- 직무연수 신청시 주의사항 -->
	<table width="720" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td><img src="/images/user/game/course/type1/kyowon/sub_02/sub_title_process.jpg"></td>
	  </tr>
	</table>

    <!-- 직무신청시 유의사항 -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
	    <td><img src="/images/user/game/course/type1/kyowon/sub_02/sub_title_01.gif"></td>
	  </tr>
	  <tr>
	    <td class="01_1">1.연수대상</td>
	  </tr>
	  <tr>
	    <td class="01" style="padding:0px 10px 10px 20px">전국 16개 시도교육청 소속 유,초,중,고 교사 및 교육공무원(교장/교감 포함)<br>휴직 중이거나 기간제 교사, 일반인은 학점이 인정되지 않습니다. </td>
	  </tr>
	  <tr>
	    <td class="01_1">2. 연수기간 중복 범위</td>
	  </tr>
	  <tr>
	    <td class="01" style="padding:0px 10px 5px 20px">교육공무원대상 원격연수는 평일에는 1강좌, 방학 동안에는 2강좌까지 수강이 가능합니다.<br>(단, 방학동안이라도 출석시험이 중복될 경우에는 1강좌만 가능함)</td>
	  </tr>
	  <tr>
	    <td align="center"><img src="/images/user/game/course/type1/kyowon/sub_02/img_02.jpg"></td>
	  </tr>

    </table>

	 <!-- 연수지명번호 -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
	 <tr>
	   <td><img src="/images/user/game/course/type1/kyowon/sub_02/sub_title_02.gif"></td>
	 </tr>
	 <tr>
	   <td class="01" style="padding:0px 5px 5px 10px">2007.03.01 부터 '학교장 추천서'를 팩스로 제출하는 방식이 폐지되고 학교에 『 연수 지명 명부 』비치된 연수<br>지명 번호 기입 시스템으로 변경되었습니다. 연수 지명 번호 변경은 나의 강의실에서 변경하실 수 있습니다.</td>
	 </tr>

    </table>

	 <!--연수취소,환불-->
	<table width="720" border="0" cellspacing="0" cellpadding="0">
	 <tr>
	   <td><img src="/images/user/game/course/type1/kyowon/sub_02/sub_title_03.gif"></td>
	 </tr>
	 <tr>
	   <td class="01_1" style="padding:5px 10px 3px 10px">1. 연수취소 및 환불 규정</td>
	 </tr>
	 <tr>
	   <td class="01" style="padding:0px 10px 20px 25px">연수취소기간: 연수 시작 후 7일까지만 가능합니다.<br>연수비 환불: 연수 시작 전 100% 환불, 연수 시작 후 7일 이내 80% 환불, 7일 경과 후에는 환불 불가</td>
	 </tr>

     <tr>
	  <td style="padding:0px 0px 0px 25px">
        <table width="550" border="1" cellspacing="0" cellpadding="0" bordercolor="#d2d2d2">
	     <TR class="01" bgcolor="#f3ebeb" align="center">
			<TD>취소 시점 </TD>
			<TD>연수기간</TD>
			<TD>과정개요</TD>
	     </TR>
	     <TR class="01" align="center">
			<TD>결제 승인 전 (미납 상태)</TD>
			<TD>-</TD>
			<TD>MyClass에서 직접 취소 가능 </TD>
	     </TR>
	     <TR class="01" align="center">
			<TD>연수 시작 전</TD>
			<TD>전액 환불</TD>
			<TD rowspan="2">연수원으로 취소 요청<BR>(TEL 02-540-8194)</TD>
	     </TR>
	     <TR class="01" align="center">
			<TD>연수 시작 후 7일 이내</TD>
			<TD>80% 환불</TD>
	     </TR>
	     <TR class="01" align="center">
			<TD>7일 경과 후</TD>
			<TD>-</TD>
			<TD>취소 불가</TD>
	     </TR>
	    </table>
	   </td>
      </tr>


	  <tr>
	   <td class="01_1" style="padding:20px 10px 3px 10px">2. 연수 연기</td>
	  </tr>
	  <tr>
	   <td class="01" style="padding:0px 10px 5px 25px">연수취소기간 이후에 특별한 사유로 인하여 연수 수강이 불가능한 상황이 발생할 경우,차기 연수로 연기하여<br>수강할 수 있습니다. (연수종료 14일전까지 제출)
         <br>(예) 교통사고, 가족의 상, 직계가족 결혼식 등으로 출석시험 당일 응시가 불가능한 경우<br>단, 연기 신청 시에는 수강연기신청서와 사유에 관한 증명서(진단서, 청첩장 등)를 제출해야 합니다.<br>(FAX: 02-511-9918)
      </tr>
	  <tr>
	    <td class="01" style="padding:15px 10px 5px 25px">* 연기 신청서 작성시 유의 사항<br>
		   * 정확한 연기 사유를 작성해 주시기 바랍니다.<BR>
		   * 수강 연기는 1회만 가능합니다(1회 연기 이후 재연기 불가)<BR>
		   * 수강 연기는 1년 이내 가능합니다.<BR>
		   * 수강 연기는 신청 연수가 종료되기 14일 전까지 가능합니다.<BR>
		   * 수강 연기는 신청하신 과정으로만 연기 가능합니다.(다른 과정으로 변경불가)<BR>
		   * 다음 기수에 연수지명번호는 새로 부여받아 입력하셔야 합니다.<BR></td>
	  </tr>
	  <tr>
	    <td align="center" style="padding:10px 0px 30px 0px"><a href="/upload/kyowon/kyowon01.hwp"><img src="/images/user/game/course/type1/kyowon/sub_02/b.jpg" border=0></a></td>
	  </tr>

    </table>

<br><br>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->