<%
//**********************************************************
//  1. 제      목: 홈페이지 마일리지 문의
//  2. 프로그램명: zu_PageMileage_L.jsp
//  3. 개      요: 홈페이지 마일리지 문의
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 7. 1
//  7. 수      정: 이연정 2005. 7. 1
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>


<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    String s_userid     = box.getSession("userid");
    box.put("leftmenu","05");                       // 메뉴 아이디 세팅
%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topSupport.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->
          <!-- center start -->
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; Activity 문의</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/support/<%=tem_subimgpath%>/tit_activity.jpg"></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>

                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="675" valign="top"><img src="/images/user/support/st_act_01.gif"></td>
                    </tr>
                    <tr> 
                      <td height="10">&nbsp;</td>
                    </tr>
                  </table> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center"><img src="/images/user/support/act_01img.jpg"></td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="576" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="/images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                        <font color="#000000">자신이 사이트에 로그인한 시간과 로그인한 횟수 등 로그인 
                        관련 정보를 보여줍니다.</font></td>
                    </tr>
                    <tr> 
                      <td height="10"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/guide/online_num2.gif" width="13" height="14" align="absmiddle"> 
                        <font color="#000000">사이트내에서 글을 작성하고 자료를 등록하는 횟수가 기록되어 
                        나의 활동을 한눈에 보실 수 있습니다.</font></td>
                    </tr>
                    <tr> 
                      <td height="20">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center"><img src="/images/user/support/act_02img.jpg"></td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="576" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td><img src="/images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                        <font color="#000000">사이트 메뉴별로 접근한 횟수가 기록되고 총계까지 나와서 자신의 
                        사이트활동 범위를 보실 수 있습니다.</font></td>
                    </tr>
                    <tr> 
                      <td height="30">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="675" valign="top"><img src="/images/user/support/st_act_02.gif" width="333" height="16"></td>
                    </tr>
                    <tr> 
                      <td height="10">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td align="center"><img src="/images/user/support/act_03img.jpg"></td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table>
                  <table width="576" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                        <font color="#000000">현재 학습중인 과정들이 순서대로 등록되어 있어서 숫자
                        위에 마우스를 가져가면 해당 과정명이 말풍선으로 뜹니다. 과정 활동 통계를 보고 싶은 과정을 선택하면 
                        하단에 해당 과정의 상세 내용이 제시됩니다.</font></td>
                    </tr>
                    <tr> 
                      <td height="10"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/guide/online_num2.gif" width="13" height="14" align="absmiddle"> 
                        <font color="#000000">학습중인 과정 중 선택한 과정의 학습시간, 최근에 학습했던 
                        날짜, 이 과정의 강의실에 접근했던 횟수, 진도가 어느정도 나갔는지를 쉽게 확인 하실 수 있습니다.</font> 
                      </td>
                    </tr>
                    <tr> 
                      <td height="10"></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/guide/online_num3.gif" width="13" height="14" align="absmiddle"> 
                        <font color="#000000">선택한 과정의 학습활동 내역이 상세하게 기록되어 있어 있습니다. 
                        <br>
                        메뉴별로 접근횟수, 등록수로 나뉘어 학습한 빈도수와 등록여부를 확인 할 수 있어서 학습과정을 쉽게 
                        관리하실 수 있습니다.</font></td>
                    </tr>
                    <tr> 
                      <td height="20" align="right"><a href="#"><img src="/images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                  </table></td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
            </table>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->