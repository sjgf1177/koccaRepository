<%
//**********************************************************
//  1. 제      목: 서비스 > 사이트맵
//  2. 프로그램명 : gu_siteMap.jsp
//  3. 개      요: 사이트맵
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 06.01.10
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box    = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");
    box.put("leftmenu","07");                       // 메뉴 아이디 세팅
%>

<SCRIPT LANGUAGE="JavaScript">
<!--

//-->
</SCRIPT>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topService.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<link href="/css/user_style1.css" rel="stylesheet" type="text/css">

<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/service/<%=tem_subimgpath%>/tit_sitemap.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> HOME > 서비스 > 사이트맵</td>
  </tr>
  <tr> 
    <td height="20">
      <!-- page sub -->
    </td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td valign="top"><img src="/images/user/game/service/text_sitemap.gif"></td>
  </tr>
  <tr> 
    <td height="25">&nbsp;</td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="155" valign="top"><table width="155" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="2"><img src="/images/user/game/service/sm_tit_course.gif"></td>
        </tr>
        <tr> 
          <td width="11">&nbsp;</td>
          <td width="144"><table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21" height="10" background="/images/user/game/service/sm_line.gif"></td>
                <td></td>
              </tr>
              <tr> 
                <td><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('1', '01', '1')">교육과정소개</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('1', '02', '2')">학습위계</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('1', '03', '3')">전체과정검색</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('1', '04', '4')">교육일정</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('1', '05', '5')">개설예정과정</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('1', '06', '6')">인기과정</a></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
    <td width="33">&nbsp;</td>
    <td width="155" valign="top"><table width="155" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="2"><img src="/images/user/game/service/sm_tit_apply.gif"></td>
        </tr>
        <tr> 
          <td width="11">&nbsp;</td>
          <td width="144"><table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21" height="10" background="/images/user/game/service/sm_line.gif"></td>
                <td></td>
              </tr>
              <tr> 
                <td><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('2', '01', '1')">수강신청안내</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('2', '02', '2')">수강신청</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('2', '03', '3')">수강신청확인/취소</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('2', '04', '4')">self진단</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('2', '05', '5')">오프라인강좌 신청/확인</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21">&nbsp;</td>
                <td class="tbl_bleft_none">&nbsp;</td>
              </tr>
            </table></td>
        </tr>
      </table></td>
    <td width="33">&nbsp;</td>
    <td width="155" valign="top"><table width="155" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="2"><img src="/images/user/game/service/sm_tit_myclass.gif"></td>
        </tr>
        <tr> 
          <td width="11">&nbsp;</td>
          <td width="144"><table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21" height="10" background="/images/user/game/service/sm_line.gif"></td>
                <td></td>
              </tr>
              <tr> 
                <td><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('3', '01', '1')">나의강의실</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('3', '02', '2')">나의교육이력</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('3', '03', '3')">나의질문방</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('3', '04', '4')">나의학습활동</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('3', '05', '5')">지식공유게시판</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('3', '06', '6')"> E-TEST</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('3', '07', '7')">나의설문</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('3', '08', '8')">특강실</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('3', '09', '9')">관심과정</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('3', '10', '10')">마일리지</a></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
    <td width="33" valign="top">&nbsp;</td>
    <td width="155" valign="top"><table width="155" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="2"><img src="/images/user/game/service/sm_tit_helpdesk.gif"></td>
        </tr>
        <tr> 
          <td width="11">&nbsp;</td>
          <td width="144"><table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21" height="10" background="/images/user/game/service/sm_line.gif"></td>
                <td></td>
              </tr>
              <tr> 
                <td><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('4', '01', '1')">F A Q</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('4', '02', '2')">Q &amp; A</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('4', '03', '3')">S/W 다운로드</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('4', '04', '4')">자료실</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('4', '05', '5')">학습환경도우미</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('4', '06', '6')">온라인메뉴얼</a></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td colspan="7">&nbsp;</td>
  </tr>
  <tr> 
    <td valign="top"><table width="155" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="2"><img src="/images/user/game/service/sm_tit_book.gif"></td>
        </tr>
        <tr> 
          <td width="11">&nbsp;</td>
          <td width="144"><table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21" height="10" background="/images/user/game/service/sm_line.gif"></td>
                <td></td>
              </tr>
              <tr> 
                <td><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('6', '01', '1')">교재판매</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('6', '02', '2')">교재신청 확인/취소</a></td>
              </tr>
            </table>
          </td>
        </tr>
      </table></td>
    <td>&nbsp;</td>
    <td valign="top"><table width="155" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="2"><img src="/images/user/game/service/sm_tit_service.gif"></td>
        </tr>
        <tr> 
          <td width="11">&nbsp;</td>
          <td width="144"><table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21" height="10" background="/images/user/game/service/sm_line.gif"></td>
                <td></td>
              </tr>
              <tr> 
                <td><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('5', '01', '1')">공지사항</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('5', '02', '2')">이달의 우수회원</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('5', '03', '3')">OffLine 학생갤러리</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('5', '04', '4')">구인구직</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('5', '05', '5')">운영자에게</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('5', '06', '6')">건의 &amp; 버그신고</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="javascript:menuForward('5', '07', '7')">사이트맵</a></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
    <td>&nbsp;</td>
    <td valign="top"><table width="155" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="2"><img src="/images/user/game/service/sm_tit_intro.gif"></td>
        </tr>
        <tr> 
          <td width="11">&nbsp;</td>
          <td width="144"><table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21" height="10" background="/images/user/game/service/sm_line.gif"></td>
                <td></td>
              </tr>
              <tr> 
                <td><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=tab1">추진목표</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=tab2">취지 및 개요</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=tab3">원장인사말</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=tab4">협정체결기관</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=tab5">발전단계</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=tab6">무료교육취지</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="/servlet/controller.homepage.HomePageAboutUsServlet?p_process=tab7">위치/연락처</a></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
    <td>&nbsp;</td>
    <td valign="top"><table width="155" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="2"><img src="/images/user/game/service/sm_tit_member.gif"></td>
        </tr>
        <tr> 
          <td width="11">&nbsp;</td>
          <td width="144"><table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21" height="10" background="/images/user/game/service/sm_line.gif"></td>
                <td></td>
              </tr>
              <tr> 
                <td><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="/servlet/controller.homepage.MemberJoinServlet?p_process=join">회원가입</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="/servlet/controller.homepage.LoginServlet?p_process=golosspwd">아이디/비밀번호찾기</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21"><img src="/images/user/game/service/sm_bl.gif"></td>
                <td class="tbl_bleft_none"><a href="/servlet/controller.homepage.MemberJoinServlet?p_process=personal">개인정보 보호정책</a></td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21">&nbsp;</td>
                <td class="tbl_bleft_none">&nbsp;</td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21">&nbsp;</td>
                <td class="tbl_bleft_none">&nbsp;</td>
              </tr>
            </table>
            <table width="144" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="21">&nbsp;</td>
                <td class="tbl_bleft_none">&nbsp;</td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</table>
</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
