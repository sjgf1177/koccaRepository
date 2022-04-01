<%
//**********************************************************
//  1. 제      목: 홈페이지 온라인 메뉴얼
//  2. 프로그램명: zu_HomePageOnline2_2_L.jsp
//  3. 개      요: 홈페이지 온라인 메뉴얼
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 7. 25
//  7. 수      정: 이연정 2005. 7. 25
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
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅
%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topGuide.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
    function menuMove(process) {

      document.form1.action = "/servlet/controller.homepage.HomePageOnlineServlet";
      document.form1.p_process.value = process;
      document.form1.submit();
    }

//-->
</SCRIPT>
<form name = "form1">
<input type="hidden" name="p_process" value="">
</form>
          <!-- center start -->
            <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location"> 
                  HOME &gt; 교육안내 &gt; 온라인메뉴얼</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td valign="top"><img src="/images/user/guide/<%=tem_subimgpath%>/tit_online.jpg"></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>
                  <!-- 구분탭 -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td height="5" colspan="5"></td>
                    </tr>
                    <tr> 
                      <td width="95" valign="top"><a href="javascript:menuMove('selectList')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image351','','/images/user/guide/tab_online_01_on.gif',1)"><img src="/images/user/guide/tab_online_01.gif" name="Image351" border="0" id="Image351"></a> 
                      </td>
                      <td width="113" valign="top" ><img src="/images/user/guide/tab_online_02_on.gif"></td>
                      <td width="128" valign="top" ><a href="javascript:menuMove('selectList3')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image371','','/images/user/guide/tab_online_03_on.gif',1)"><img src="/images/user/guide/tab_online_03.gif" name="Image371" border="0" id="Image371"></a></td>
                      <td width="100" valign="top" ><a href="javascript:menuMove('selectList4')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image381','','/images/user/guide/tab_online_04_on.gif',1)"><img src="/images/user/guide/tab_online_04.gif" name="Image381" border="0" id="Image381"></a></td>
                      <td width="239" align="right" >&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="10" colspan="5"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="337" valign="top"><img src="/images/user/guide/st_studydoguide.gif" ></td>
                      <td width="338" align="right" valign="top"><a href="javascript:menuMove('selectList2_1')"><img src="/images/user/button/btn_myclassguide.gif" border="0"></a></td>
                    </tr>
                    <tr> 
                      <td height="20" colspan="2">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307" valign="top"><img src="/images/user/guide/online_ms01.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><font color="#000000"><strong>학습시작 메인 </strong></font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">나의강의실에서 학습중인 과정의 <font color="#CC3300">학습하기 
                              버튼</font>을 클릭하면 왼쪽화면과 같은 학습메인 화면이 나옵니다.</td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                          <tr> 
                            <td><font color="#000000"><img src="/images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                              학습메인 </font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">화면 중앙오른쪽 메인화면에는 나의 현재 과정진도율과 권장진도율이 
                              표시되며 과정공지사항을 확인할 수 있습니다. <br> <br>
                              학습시작 버튼을 클릭하면 학습을 시작할 수 있습니다. <br> <br>
                              최초학습시엔 과정학습첫페이지로 이동하며 학습진도가 나간 상태에서는 최근에 학습한 페이지로 
                              이동하여 이어서 학습을 할 수 있습니다. <br></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/images/user/guide/online_ms02.jpg" width="289" height="193"></td>
                      <td valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num2.gif" align="absmiddle"> 
                              학습목차</td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">화면 오른쪽엔 <font color="#CC3300">과정학습목차</font>가 
                              있어 원하는 학습항목을 클릭하여 학습을 진행할 수 있습니다.<br>
                              학습목차에는 아래와 같이 이미지로 학습여부를 구분해줍니다. <br> </td>
                          </tr>
                          <tr> 
                            <td valign="top"><img src="/images/user/guide/myhelp_img07.gif" align="absmiddle"> 
                              이미 학습한 항목표시 </td>
                          </tr>
                          <tr> 
                            <td valign="top"><img src="/images/user/guide/myhelp_img08.gif" align="absmiddle"> 
                              학습중인 항목표시 </td>
                          </tr>
                          <tr> 
                            <td valign="top"><img src="/images/user/guide/myhelp_img09.gif" align="absmiddle"> 
                              미학습 항목표시 </td>
                          </tr>
                          <tr> 
                            <td valign="top">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td valign="top">상단의 HIDE를 클릭하면 목차프레임의 사이즈가 줄어들고 학습메인화면의 
                              크기가 늘어나서 학습화면의 크기를 넓게 하여 보실수 있습니다. <br>
                              상단의 공지사항을 클릭하면 공지사항을 확인할 수 있습니다. </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/images/user/guide/online_ms03.jpg"></td>
                      <td valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><font color="#000000"><strong>학습 보조도구메뉴</strong></font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">화면 하단에는 나의 상세학습진도 확인 및 레포트제출, 설문, 
                              평가응시를 할 수 있으며 기타 게시판등의 학습보조도구를 사용할 수 있습니다.</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/guide/online_ms04.jpg"></td>
                      <td valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num1.gif" align="absmiddle"> 
                              <font color="#000000">성적목차</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">성적/목차 페이지에서는 본인의 취득점수와 학습진행상황 및 세부 
                              목차별 학습여부를 확인할 수 있습니다.</td>
                          </tr>
                          <tr> 
                            <td valign="top">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td valign="top"><font color="#000000">- 취득점수확인 </font><br>
                              이수기준점수와 현재취득점수를 보여주어 이수에 필요한 정보를 확인할 수 있습니다. <br> 
                              <br> <font color="#000000">- 학습진행상황 </font><br>
                              최종평가 및 중간평가, 형성평가의 응시현황 및 리포트, 과정설문에 응답했는지의 여부를 
                              확인할 수 있습니다.<br>
                              또한 학습목차별 세부 학습현황을 확인할 수 있습니다. <br></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/images/user/guide/online_ms05.jpg" ></td>
                      <td valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num2.gif" align="absmiddle"> 
                              <font color="#000000">레포트</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">왼쪽화면과 같이 레포트가 출제되면 제목을 클릭한후 레포트를 
                              제출하면 됩니다.<br> </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/guide/online_ms06.jpg" ></td>
                      <td valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num3.gif" align="absmiddle"> 
                              <font color="#000000">설문</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">과정설문 및 콘텐츠 평가 설문에 응답할 수 있습니다. 상태란의 
                              응답가능여부를 확인하고 설문제목을 클릭하면 설문에 응답할 수 있습니다.<br> </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/guide/online_ms07.jpg" ></td>
                      <td valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num4.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">평가</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">중간평가 및 형성평가, 최종평가에 응시 할 수 있습니다. <br>
                              상태란에 응시가능여부를 확인하고 시험유형을 클릭하면 평가에 응시할 수 있으며 주어진 시간안에 
                              문제를 풀고 제출하면 됩니다.<br> </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td><img src="/images/user/guide/online_ms08.jpg" ></td>
                      <td valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num5.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">자료방/게시판/토론방</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">과정학습에 관련된 자료나 도움이 되는 글들을 여러학습자들과 
                              공유할 수 있는 공간입니다.<br> </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td>&nbsp;</td>
                      <td align="right" valign="top"><a href="#"><img src="/images/user/button/btn_top.gif" border="0"></a></td>
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
