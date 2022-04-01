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
    box.put("leftmenu","03");                       // 메뉴 아이디 세팅
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
        <td height="35" align="right"  background="/images/user/game/course/type1/kyowon/sub_03/tit_all_search.gif" class="location" valign="top" style="padding:17px 10px 0px 0px"><img src="http://cyber.gameacademy.or.kr/images/user/game/common/location_bl.gif"> HOME > 연수안내 > 단체신청</td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
    </table>

	<!-- top 이미지와버튼 -->
	<table width="720" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td align="center"><img src="/images/user/game/course/type1/kyowon/sub_03/top_img.jpg"></td>
	  </tr>
	  <!--
	  <tr>
	    <td align="center"><img src="/images/user/game/course/type1/kyowon/sub_03/b_img.jpg"></td>
	  </tr>
	  -->
	</table>

    <!-- 단체할인대상 -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
	    <td colspan="2"><img src="/images/user/game/course/type1/kyowon/sub_03/title_01.gif"></td>
	  </tr>
	  <tr>
	    <td width="10" style="padding:7px 3px 8px 30px"><img src="/images/user/game/course/type1/kyowon/sub_03/icon.gif"></td>
		<td width="710" class="01">5인 이상 단체신청 시 연수비 30%할인</td>
	  </tr>
	  <tr>
	    <td width="10" style="padding:7px 3px 8px 30px"><img src="/images/user/game/course/type1/kyowon/sub_03/icon.gif"></td>
		<td width="710" class="01">10인 이상 단체신청 시 연수비 50%할인</td>
	  </tr>
	   <tr>
	    <td class="02" colspan="2" style="padding:0px 3px 10px 42px">(어학과정, 법교육과정, 자격증과정은 단체이벤트와 포인트는 적용되지만, 연수비 할인은 적용되지 않습니다.)</td>
	  </tr>
    </table>

	<!-- 온라인 단체 -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
	    <td colspan="2"><img src="/images/user/game/course/type1/kyowon/sub_03/title_02.gif"></td>
	  </tr>
	  <tr>
	    <td width="10" style="padding:7px 3px 8px 30px"><img src="/images/user/game/course/type1/kyowon/sub_03/icon.gif"></td>
		<td width="710" class="01">대표자 선생님께서 단체회원으로 등록을 합니다.</td>
	  </tr>
	  <tr>
	    <td width="10" style="padding:5px 3px 5px 30px"><img src="/images/user/game/course/type1/kyowon/sub_03/icon.gif"></td>
		<td width="710" class="01">단체등록 시 등록하는 선생님들의 이름,휴대폰번호, 생년월일을 정확하게 기재합니다.</td>
	  </tr>
	  <tr>
	    <td width="10" style="padding:5px 3px 5px 30px"><img src="/images/user/game/course/type1/kyowon/sub_03/icon.gif"></td>
		<td width="710" class="01">단체등록이 완료되면 개별적으로 수강신청을 합니다.</td>
	  </tr>
	  <tr>
	    <td width="10" style="padding:5px 3px 10px 30px"><img src="/images/user/game/course/type1/kyowon/sub_03/icon.gif"></td>
		<td width="710" class="01">수강신청 시 5인 이상 단체로 등록이 된 경우에 자동으로 연수비 할인금액이 나오고 개별적으로 결제를 하실 수 있습니다.</td>
	  </tr>

    </table>

	<!-- fax단체수강 -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
	    <td colspan="2"><img src="/images/user/game/course/type1/kyowon/sub_03/title_03.gif"></td>
	  </tr>
	  <tr>
	    <td width="10" style="padding:10px 3px 8px 30px"><img src="/images/user/game/course/type1/kyowon/sub_03/icon.gif"></td>
		<td width="710" class="01">먼저 개별적으로 수강 신청을 합니다.</td>
	  </tr>
	  <tr>
	    <td width="10" style="padding:5px 3px 5px 30px"><img src="/images/user/game/course/type1/kyowon/sub_03/icon.gif"></td>
		<td width="710" class="01">단체신청서는 한 장의 신청서에 모두 기재 후<span class="02">Fax(02-511-9918)</span>로 전송합니다.</td>
	  </tr>
	  <tr>
	    <td class="02_bold" colspan="2" style="padding:20px 0px 5px 30px">[단체 할인 수강 선택 시 결제방법]</td>
	  </tr>
	  <tr>
	    <td class="01" colspan="2" style="padding:0px 0px 5px 30px">단체수강 대표자 이름 혹은 학교명으로 무통장 일괄 송금하여 주시면 신청자 모두 최종 승인 처리 됨</td>
	  </tr>
	  <tr>
	    <td class="01" colspan="2" style="padding:0px 0px 0px 30px"><span class="sub_bold">국민은행 : </span>361437-04-001948</td>
	  </tr>
	  <tr>
	    <td class="01" colspan="2" style="padding:0px 0px 0px 30px"><span class="sub_bold">예금주 : </span>게임아카데미</td>
	  </tr>
	  <tr>
	    <td colspan="2" style="padding:10px 0px 40px 35px"><a href="/upload/kyowon/kyowon02.hwp"><img src="/images/user/game/course/type1/kyowon/sub_03/b_down.gif" border="0"></a></td>
	  </tr>
    </table>

<br><br>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->