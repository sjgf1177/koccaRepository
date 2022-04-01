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
    box.put("leftmenu","06");                       // 메뉴 아이디 세팅
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
              <td height="35" align="right"  background="/images/user/game/course/type1/kyowon/sub_06/tit_all_search.gif" class="location" valign="top" style="padding:17px 10px 0px 0px"><img src="/images/user/game/course/type1/kyowon/location_bl.gif">
                HOME > 연수안내 > 평가방법</td>
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
          <!-- 평가방법 및 이수기준 -->
          <table width="720" border="0" cellspacing="0" cellpadding="0">
            <tr>
              <td colspan="2" style="padding:10px 0px 5px 5px"><img src="/images/user/game/course/type1/kyowon/sub_06/title.gif"></td>
            </tr>
            <tr>
              <td width="710" class="02_bold" style="padding:5px 0px 5px 15px">1.
                평가방법</td>
            </tr>
            <tr>
              <td width="710" class="01" style="padding:0px 0px 5px 30px">60시간
                이상의 직무연수과정의 성적은 교육부 연수규정에 따라 온라인평가와 출석평가를 합산하여 산출됩니다.</td>
            </tr>
            <tr>
              <td style="padding:0px 0px 0px 30px"> <table width="550" border="1" cellspacing="0" cellpadding="5" bordercolor="#d2d2d2">
                  <TR class="01" align="center" bgcolor="#f3ebeb">
                    <TD colspan="2">평가구분</TD>
                    <TD>평가유형</TD>
                    <TD>평가회수</TD>
                    <TD>배점</TD>
                  </TR>
                  <TR class="01" align="center">
                    <TD rowspan="3">온라인 평가(40%)</TD>
                    <TD>과제</TD>
                    <TD>보고서 또는 실습파일 제출</TD>
                    <TD>1회</TD>
                    <TD>10점</TD>
                  </TR>
                  <TR class="01" align="center">
                    <TD>시험</TD>
                    <TD>객관식/단답형 혼합</TD>
                    <TD>1회</TD>
                    <TD>10점</TD>
                  </TR>
                  <TR class="01" align="center">
                    <TD>참여점수</TD>
                    <TD>강의실 글등록, 자료실글등록,접속일,<BR>
                      온라인 교재 진도율</TD>
                    <TD>연수종료 후</TD>
                    <TD>20점</TD>
                  </TR>
                  <TR class="01" align="center">
                    <TD>연수종료 전<BR>
                      토요일 진행</TD>
                    <TD>출석시험</TD>
                    <TD>지필평가<BR>
                      (객관식/단답형 혼합 30~40문제)</TD>
                    <TD>1회</TD>
                    <TD>60점</TD>
                  </TR>
                  <TR class="01" align="center">
                    <TD colspan="4">계</TD>
                    <TD>100점</TD>
                  </TR>
                </table></td>
            </tr>
            <tr>
              <td width="710" class="01" style="padding:10px 0px 5px 30px">* 과제/시험
                항목별 배점 기준은 과정에 따라 상이할 수 있습니다. (예: 과제 0점, 시험 30점)<BR>
                * 참여점수는 연수 종료 후에 일괄 평가됩니다.</td>
            </tr>
            <tr>
              <td width="710" class="02_bold" style="padding:5px 0px 5px 15px">2.
                이수기준 및 최종 성적 산출</td>
            </tr>
            <tr>
              <td width="710" class="01" style="padding:0px 0px 5px 30px">총점 60점
                이상, 진도율 80% 이상 되어야 이수가 가능하며, 이수를 못한 경우 이수증은 발급되지 않습니다.<BR>
                최종 성적인 상대평가 점수는 교육인적자원부의 원격연수 관련 규정에 준하여, 연수성적분포 조견표에 따라<BR>
                80점~100점 사이로 산출됩니다.<BR>
                동점자 발생 시 동점자 처리 기준에 의해 처리됩니다.</td>
            </tr>
            <tr>
              <td style="padding:0px 0px 0px 30px"> <table border="1" cellspacing="0" cellpadding="3" width="550" bordercolor="#d2d2d2">
                  <TR class="01" align="center" bgcolor="#f6f6f6">
                    <TD colspan="6">[ 연수성적분포 조견표]</TD>
                  </TR>
                  <TR class="01" align="center" bgcolor="#f6f6f6">
                    <TD>점수</TD>
                    <TD>백분율</TD>
                    <TD>점수</TD>
                    <TD>백분율</TD>
                    <TD>점수</TD>
                    <TD>백분율</TD>
                  </TR>
                  <TR class="01" align="center">
                    <TD bgcolor="#faffe8">100</TD>
                    <TD>2.0%</TD>
                    <TD bgcolor="#faffe8">93</TD>
                    <TD>6.2%</TD>
                    <TD bgcolor="#faffe8">86</TD>
                    <TD>5.7%</TD>
                  </TR>
                  <TR class="01" align="center">
                    <TD bgcolor="#faffe8">99</TD>
                    <TD>2.6%</TD>
                    <TD bgcolor="#faffe8">92</TD>
                    <TD>6.6%</TD>
                    <TD bgcolor="#faffe8">85</TD>
                    <TD>5.1%</TD>
                  </TR>
                  <TR class="01" align="center">
                    <TD bgcolor="#faffe8">98</TD>
                    <TD>3.2%</TD>
                    <TD bgcolor="#faffe8">91</TD>
                    <TD>6.8%</TD>
                    <TD bgcolor="#faffe8">84</TD>
                    <TD>4.5%</TD>
                  </TR>
                  <TR class="01" align="center">
                    <TD bgcolor="#faffe8">97</TD>
                    <TD>3.8%</TD>
                    <TD bgcolor="#faffe8">90</TD>
                    <TD>7.0%</TD>
                    <TD bgcolor="#faffe8">83</TD>
                    <TD>3.8%</TD>
                  </TR>
                  <TR class="01" align="center">
                    <TD bgcolor="#faffe8">96</TD>
                    <TD>4.5%</TD>
                    <TD bgcolor="#faffe8">89</TD>
                    <TD>6.8%</TD>
                    <TD bgcolor="#faffe8">82</TD>
                    <TD>3.2%</TD>
                  </TR>
                  <TR class="01" align="center">
                    <TD bgcolor="#faffe8">95</TD>
                    <TD>5.1%</TD>
                    <TD bgcolor="#faffe8">88</TD>
                    <TD>6.6%</TD>
                    <TD bgcolor="#faffe8">81</TD>
                    <TD>2.6%</TD>
                  </TR>
                  <TR class="01" align="center">
                    <TD bgcolor="#faffe8">94</TD>
                    <TD>5.7%</TD>
                    <TD bgcolor="#faffe8">87</TD>
                    <TD>6.2%</TD>
                    <TD bgcolor="#faffe8">80</TD>
                    <TD>2.0%</TD>
                  </TR>
                </table></td>
            </tr>
            <tr>
              <td width="710" class="02_bold" style="padding:15px 0px 5px 15px">3.
                연수결과통보</td>
            </tr>
            <tr>
              <td width="710" class="01" style="padding:0px 0px 40px 30px">상대평가
                점수 산출 후 이수번호를 발급하여 각 시도교육청에 이수자명단을 통보합니다.<BR>
                이수자에게는 이수증이 발급되며, 본인이 직접 출력할 수 있습니다.<BR>
                (단, 이수증은 온라인 발급을 원칙으로 하며, 온라인 상의 이수증이 원본의 효력을 갖습니다.)</td>
            </tr>
          </table>

<br><br>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->