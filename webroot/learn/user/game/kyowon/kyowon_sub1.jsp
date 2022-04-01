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
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅
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

// 과정 내용보기
function whenSubjInfo(subj,tabnum){
    document.form1.p_subj.value     = subj;
    document.form1.p_tabnum.value   = tabnum;
    document.form1.p_process.value  = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.course.CourseKyowonServlet';
    document.form1.target = "_self";
    document.form1.submit();
}
//-->
</SCRIPT>
<form name="form1" method="post" >
    <input type='hidden' name='p_process' value="SubjectList">
    <input type='hidden' name='p_rprocess' value="">
    <input type='hidden' name='p_iscourseYn' value="N">
    <input type='hidden' name='p_tabnum'  value="common">
    <input type='hidden' name='p_subj' value="">
</form>
    <!-- title -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="35" align="right"  background="/images/user/game/course/type1/kyowon/tit_all_search.gif" class="location" valign="top" style="padding:17px 10px 0px 0px"><img src="/images/user/game/course/type1/kyowon/location_bl.gif"> HOME > 연수안내 > 모집안내</td>
      </tr>
      <tr>
        <td height="20"></td>
      </tr>
    </table>

    <!-- 개인단체 할인률 -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
      <tr>
	    <td colspan="2" style="padding:20px 10px 0px 6px"><img src="/images/user/game/course/type1/kyowon/t_01.gif"></td>
	  </tr>
	  <tr>
	    <td width="20" align="center" style="padding:5px 0px 2px 20px"><img src="/images/user/game/course/type1/kyowon/dot.gif"></td>
		<td class="01" width="700" style="padding:5px 0px 2px 0px"><span class="sub_bold">기존직무연수 :</span> 수강자 30%할인</td>
	  </tr>
	  <tr>
	    <td width="20" align="center" style="padding:5px 0px 2px 20px"><img src="/images/user/game/course/type1/kyowon/dot.gif"></td>
		<td class="01" width="700" style="padding:5px 0px 2px 0px"><span class="sub_bold">단체신청 :</span> 5인 이상 신청시 연수비 30%할인, 10인 이상 신청시 연수비 50%할인</td>
	  </tr>

    </table>

	 <!-- 할인내용 -->
    <table width="720" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td colspan="2" style="padding:20px 10px 0px 6px"><img src="/images/user/game/course/type1/kyowon/t_02.gif"></td>
	  </tr>
      <tr>
	    <td valign="top" width="20" align="center" style="padding:5px 0px 2px 20px"><img src="/images/user/game/course/type1/kyowon/icon.gif"></td>
	    <td class="01" width="700" style="padding:5px 0px 2px 0px"><span class="sub_bold">대상과정 :</span> 직무연수과정</td>
	  </tr>
	  <tr>
	    <td class="02" colspan="2" style="padding:0px 0px 5px 35px">(단,어학과정,자격증 과정은 연수비 할인이 적용되지 않습니다.)</td>
	  </tr>
	  <tr>
	    <td valign="top" width="20" align="center" style="padding:5px 0px 2px 20px"><img src="/images/user/game/course/type1/kyowon/icon.gif"></td>
	    <td class="01" width="700" style="padding:5px 0px 2px 0px" cellpadding="5">일부 수강취소 후 단체할인 기준이 변경되는 경우(5인 이상 신청시)<br>먼저 운영자(02-540-8194)에게 문의 후에 수강취소 하시기 바랍니다.<br>이 경우 적용되었던 단체할인이 취소되며, 차액을 추가 부담하셔야 합니다.<BR>이점 유의하시기 바랍니다. </td>
      </tr>

    </table>

     <!-- 과정-->
	 <table width="720" border="0" cellspacing="0" cellpadding="0">
	  <tr>
	    <td colspan="2" style="padding:20px 10px 5px 6px"><img src="/images/user/game/course/type1/kyowon/sub_01/t_03.gif"></td>
	  </tr>
	  <tr>
	    <td style="padding:0px 0px 20px 20px">
		   <table border="1" cellspacing="0" cellpadding="2" bordercolor="#d7d7d7">
		     <tr bgcolor="#eef0f9">
			   <td width="70" class="02_bold" align="center">분&nbsp;류</td>
			   <td width="350" class="02_bold" align="center">과&nbsp;정&nbsp;명</td>
			   <td width="150" class="02_bold" align="center">기&nbsp;능</td>
			 </tr>
			 <tr>
			   <td align="center" bgcolor="#f8f9fe"><img src="/images/user/game/course/type1/kyowon/sub_01/course.gif"></td>
			   <td>
			     <table>
				    <tr>
						<td class="01" style="padding:0px 0px 0px 10px">게임과몰입예방</td>
						<td><img src="/images/user/game/course/type1/kyowon/sub_01/b_co.gif"></td>
					</tr>
			     </table>
			   </td>
			   <td align="center"><a href="javascript:whenSubjInfo('T000100', 'common');"><img src="/images/user/game/course/type1/kyowon/sub_01/b_course.gif" border=0></a></td>
			 </tr>
			 <tr>
			   <td align="center" bgcolor="#f8f9fe"><img src="/images/user/game/course/type1/kyowon/sub_01/course.gif"></td>
			   <td>
			     <table>
				    <tr>
						<td class="01" style="padding:0px 0px 0px 10px">게임이용지도</td>
						<td><img src="/images/user/game/course/type1/kyowon/sub_01/b_co.gif"></td>
					</tr>
			     </table>
			    </td>
			    <td align="center"><a href="javascript:whenSubjInfo('T000099', 'common');"><img src="/images/user/game/course/type1/kyowon/sub_01/b_course.gif" border=0></a></td>
			 </tr>
		   </table>
		</td>
	  </tr>
	 </table>

<br><br>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->