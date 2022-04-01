<%
//**********************************************************
//  1. 제      목: 홈페이지 온라인 메뉴얼
//  2. 프로그램명: zu_HomePageOnline_L.jsp
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
                      <td width="95" valign="top"><img src="/images/user/guide/tab_online_01_on.gif"> 
                      </td>
                      <td width="113" valign="top" ><a href="javascript:menuMove('selectList2_1')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image36','','/images/user/guide/tab_online_02_on.gif',1)"><img src="/images/user/guide/tab_online_02.gif" name="Image36" border="0"></a></td>
                      <td width="128" valign="top" ><a href="javascript:menuMove('selectList3')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image37','','/images/user/guide/tab_online_03_on.gif',1)"><img src="/images/user/guide/tab_online_03.gif" name="Image37" border="0"></a></td>
                      <td width="100" valign="top" ><a href="javascript:menuMove('selectList4')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image38','','/images/user/guide/tab_online_04_on.gif',1)"><img src="/images/user/guide/tab_online_04.gif" name="Image38" border="0"></a></td>
                      <td width="239" align="right" >&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="10" colspan="5"></td>
                    </tr>
                  </table>
				  <!-- 서브탭 -->
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed">
                          <tr> 
                            <td align="center" bgcolor="#F0F7F5" style="padding-top:8px;padding-bottom:8px"><a href="#1">수강신청</a> 
                              ㅣ <a href="#2">수강신청확인/취소</a> ㅣ <a href="#3">전체과정검색</a> 
                              ㅣ <a href="#4">연간교육일정</a> ㅣ <a href="#5">월간교육일정</a></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307"><img src="/images/user/guide/online_c01.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num1.gif" align="absmiddle"> 
                              <font color="#000000">수강신청 메뉴이동 </font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td>왼쪽상단의 수강신청/안내를 클릭하면 수강신청화면으로 이동합니다.</td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/guide/online_c01-1.jpg"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td height="20" colspan="2"><a href="#"><img src="/images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="675" valign="top"><img src="/images/user/guide/st_online1.gif" border="0"><a name="#1" id="#1"></a></td>
                    </tr>
                    <tr> 
                      <td height="15">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307"><img src="/images/user/guide/online_c03.jpg"></td>
                      <td width="368" valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">과정조회 및 선택</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">온라인 및 오프라인과정을 과정분야별로 조회/선택하여 수강신청을 
                              할 수 있습니다.<br>
                              <br>
                              ①</strong></font>사이버 온라인과정이나 집합과정을 선택한후 하단의 <strong><font color="#CC3300">②</font></strong>과정분류및 
                              분야에 속한 <strong><font color="#CC3300">③</font></strong>과정을 
                              선택클릭하면 상세 과정정보보기 화면으로 이동하며 수강신청을 할 수 있습니다.<br> <br></td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="/images/user/guide/online_c04.jpg"></td>
                      <td valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num2.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">과정상세정보 조회 및 수강신청</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top"><font color="#CC3300"><strong>①</strong></font>선택한 
                              과정정보를 확인하고 하단의 <strong><font color="#CC3300">②</font></strong>교육일정 
                              리스트의 기간정보를 확인한후 오른쪽 신청란의 <strong><font color="#CC3300">③</font></strong>신청하기 
                              버튼을 클릭하면 수강신청을 할 수 있습니다.<br> </td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td colspan="2"><a href="#"><img src="/images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="675" valign="top"><img src="/images/user/guide/st_online1-1.gif" border="0"><a name="#2" id="#2"></a></td>
                    </tr>
                    <tr> 
                      <td height="15">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307" valign="top"><img src="/images/user/guide/online_c05.jpg"></td>
                      <td width="368" valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">수강신청확인/취소</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">본인이 수강신청한 과정의 <font color="#CC3300">승인상태</font> 
                              확인 및 <font color="#CC3300">신청취소</font>를 할 수<br>
                              있습니다.<br>
                              <br>
                              본인이 수강신청한 과정내역이 나오며 <font color="#CC3300">수강신청 승인처리 
                              여부</font>를 확인할 수 있습니다.<br> <font color="#CC3300">수강신청 
                              가능기간</font>에는 오른쪽 수강신청취소란의 취소 버튼을 클릭하면 수강신청 취소를 
                              할 수 있습니다.</td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
					 <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td colspan="2"><a href="#"><img src="/images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="675" valign="top"><img src="/images/user/guide/st_online1-2.gif" border="0"><a name="#3" id="#3"></a></td>
                    </tr>
                    <tr> 
                      <td height="15">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307" valign="top"><img src="/images/user/guide/online_c01-2.jpg"></td>
                      <td width="368" valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">전체과정검색</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">검색하려는 과정의 과정명을 입력/검색하여 수강신청 및 과정정<br>
                              보를 확인할 수 있습니다. <br>
                              <br>
                              운영되고 있는 전 과정이 보여지며, 각 과정별로 만족도와 콘텐츠에 대한 평가가 표시되어 
                              있습니다.</td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td colspan="2"><a href="#"><img src="/images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="675" valign="top"><img src="/images/user/guide/st_online1-3.gif" border="0"><a name="#4" id="#4"></a></td>
                    </tr>
                    <tr> 
                      <td height="15">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307" valign="top"><img src="/images/user/guide/online_c01-3.jpg"></td>
                      <td width="368" valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">연간교육일정</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">일년 동안의 전체 교육일정을 한눈에 쉽게 파악 할수 있고 전년도 
                              교육일정도 확인할 수 있습니다<br>
                              <br>
                              과정에 해당하는 그래프를 클릭하면 그 과정의 <font color="#CC3300">교육일정 
                              정보</font>와 <font color="#CC3300">수료기준</font>을 보실 
                              수 있습니다.</td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td colspan="2"><a href="#"><img src="/images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="675" valign="top"><img src="/images/user/guide/st_online1-4.gif" border="0"><a name="#5" id="#5"></a></td>
                    </tr>
                    <tr> 
                      <td height="15">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307" valign="top"><img src="/images/user/guide/online_c01-4.jpg"></td>
                      <td width="368" valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">월간교육일정</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">이 달에 이루어지는 교육과정의 일정을 보실 수 있으며 전 달 
                              과정 일정도 확일 할수 있습니다.<br>
                              <br>
                              과정에 해당하는 그래프를 클릭하면 그 과정의 <font color="#CC3300">교육일정 
                              정보</font>와 <font color="#CC3300">수료기준</font>을 보실 
                              수 있습니다. </td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="/images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td colspan="2"><a href="#"><img src="/images/user/button/btn_top.gif" border="0"></a></td>
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
