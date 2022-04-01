<%
//**********************************************************
//  1. 제      목: 홈페이지 온라인 메뉴얼
//  2. 프로그램명: zu_HomePageOnline2_1_L.jsp
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
                      <td><table width="675" border="2" cellspacing="0" 
                         cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed">
                          <tr> 
                            <td align="center" bgcolor="#F0F7F5" style="padding-top:8px;padding-bottom:8px"><a href="#1">나의 
                              강의실</a> ㅣ <a href="#2">나의 교육이력</a> ㅣ <a href="#3">나의 
                              질문방 </a> ㅣ <a href="#4">나의 학습활동</a> ㅣ <a href="#5">지식공유 </a>
                              ㅣ <a href="#6">E-test  </a>l <a href="#7">설문</a></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr> 
                      <td height="20"></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307"><img src="../../../images/user/guide/online_m01.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num1.gif" align="absmiddle"> 
                              <font color="#000000">나의 강의실 메뉴이동 </font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">왼쪽상단의 나의 강의실을 클릭하면 나의강의실로 이동하여 학습을 
                              시작 할 수 있으며 학습을 종료한 과정을 다시 복습할 수 있습니다.</td>
                          </tr>
                          <tr> 
                            <td>&nbsp;</td>
                          </tr>
                          <tr> 
                            <td><img src="../../../images/user/guide/online_m01-1.jpg"></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td colspan="2"><a href="#"><img src="../../../images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                    <tr align="right"> 
                      <td height="10" colspan="2"></td>
                    </tr>
                  </table>

                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="351" valign="top"><img src="../../../images/user/guide/st_online2.gif"><a name="1"></a></td>
                      <td width="324" align="right" valign="top"><a href="javascript:menuMove('selectList2_2')"><img src="../../../images/user/button/btn_studydoguide.gif" border="0"></a></td>
                    </tr>
                    <tr> 
                      <td height="15" colspan="2">&nbsp;</td>
                    </tr>
                  </table>

                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307" valign="top"><img src="../../../images/user/guide/online_m02.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">학습중인 과정</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">현재 학습하고 있는 과정으로 과정명을 클릭하면 과정 정보를 
                              보실수 있으며 <font color="#CC3300">학습하기 버튼</font>을 누르면 
                              학습창이 뜹니다.<br> <br> <font color="#CC3300">학습현황보기</font>를 
                              누르면 본인의 취득점수와 학습진행상황 및 세부 목차별 학습여부를 확인할 수 있습니다.</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td><img src="../../../images/user/guide/online_m03.jpg"></td>
                      <td valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num2.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">학습예정중인 과정</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">수강신청하여 승인처리가 완료된 과정이지만 아직 학습기간이 도래되지 
                              않은 과정으로 학습대기중인 과정입니다. <br>
                              학습기간이 되면 학습중인과정으로 옮겨져 학습을 할 수 있습니다.<br></td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr> 
                      <td valign="top"><img src="../../../images/user/guide/online_m04.jpg" width="289" height="193"></td>
                      <td valign="top"><table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num3.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">학습을 마친과정</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">학습(교육)기간이 종료된 과정으로 복습가능기간에 따라 <font color="#CC3300">복습</font>을 
                              할 수 있습니다.<br> <br>
                              수료여부(복습)는 학습종료 <font color="#CC3300">7일 후 부터</font> 
                              확인가능 합니다.</td>
                          </tr>
                        </table></td>
                    </tr>
					  <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td colspan="2"><a href="#"><img src="../../../images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="351" valign="top"><img src="../../../images/user/guide/st_online2-1.gif"><a name="2" id="2"></a></td>
                      <td width="324" align="right" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="15" colspan="2">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307" valign="top"><img src="../../../images/user/guide/online_m05.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">개인교육이력 정보</font> </td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">&nbsp;본인이 수강신청한 과정에 대한 <font color="#CC3300">교육이력</font>을 
                              볼수 있습니다.<br>
                              <br>
                            </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
					                    <tr> 
                      <td width="307" valign="top"><img src="../../../images/user/guide/online_m06.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num2.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">성적보기/수료증</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">신청한 과정들의 교육기간과 복습기간, 이수여부를 알수 있으며 
                              <font color="#CC3300">성적보기</font>와 <font color="#CC3300">수료증</font>을 
                              쉽게 출력하실 수 있습니다.</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td colspan="2"><a href="#"><img src="../../../images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="351" valign="top"><img src="../../../images/user/guide/st_online2-2.gif"><a name="3" id="3"></a></td>
                      <td width="324" align="right" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="15" colspan="2">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307" valign="top"><img src="../../../images/user/guide/online_m07.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">나의 질문방</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">사이트,학습관련해서 본인이 올린 질문과 그에대한 답변 등을 
                              한눈에 확인 하실 수 있습니다. </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td colspan="2"><a href="#"><img src="../../../images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="351" valign="top"><img src="../../../images/user/guide/st_online2-3.gif"><a name="4" id="4"></a></td>
                      <td width="324" align="right" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="15" colspan="2">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307" valign="top"><img src="../../../images/user/guide/online_m08.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">사이트활동</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top"><font color="#CC3300">①</font>자신이 
                              사이트에 로그인한 시간과 로그인한 횟수 등 로그인 관련 정보를 보여주고 <font color="#CC3300">②</font>사이트내에서 
                              글을 작성하고 자료를 등록하는 횟수가 기록되어 나의 활동을 한눈에 보실 수 있습니다.<br>
                              <font color="#CC3300">③</font>사이트의 각 메뉴별로 접근한 횟수도 
                              알 수 있습니다.</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
					 <tr> 
                      <td width="307" valign="top"><img src="../../../images/user/guide/online_m09.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num2.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">학습활동</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">현재 학습중인 과정들이 순서대로 등록되어 있어서 숫자 위에 
                              마우스를 가져가면 해당 과정명이 말풍선으로 뜹니다. 과정 활동 통계를 보고 싶은 과정을 
                              선택하면 하단에 해당 과정의 상세 내용이 제시됩니다.<br>
                              <font color="#000000">&nbsp; </font> </td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td colspan="2"><a href="#"><img src="../../../images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="351" valign="top"><img src="../../../images/user/guide/st_online2-4.gif"><a name="5" id="5"></a></td>
                      <td width="324" align="right" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="15" colspan="2">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307" valign="top"><img src="../../../images/user/guide/online_m10.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">최근지식</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">질문과 노하우 등 원하시는 모든 <font color="#CC3300">지식을 
                              서로 공유</font>하고 얻을 수<br>
                              있는 곳입니다. <br>
                              최근에 올라온 따끈한 지식들이 등록되어 있습니다.<br>
                              등록을 원하시면 하단의 지식등록을 클릭하여 등록 하시면 됩니다</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
					<tr> 
                      <td width="307" valign="top"><img src="../../../images/user/guide/online_m11.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num2.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">최근지식 등록</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">지식을 등록할때 카테고리를 선택하여 분야를 선택한후 지식을 
                              등록하고 첨부파일은 최대 5가지를 올리실 수 있습니다.</td>
                          </tr>
                        </table></td>
                    </tr>
					
                    <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
					<tr> 
                      <td width="307" valign="top"><img src="../../../images/user/guide/online_m12.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num3.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">인기지식</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">올린 지식들 중에 최다조회/최다추천/최다댓글을 기록한 인기지식들을 
                              모아 놓은 곳입니다.</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td colspan="2"><a href="#"><img src="../../../images/user/button/btn_top.gif" border="0"></a></td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="351" valign="top"><img src="../../../images/user/guide/st_online2-5.gif"><a name="6" id="6"></a></td>
                      <td width="324" align="right" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="15" colspan="2">&nbsp;</td>
                    </tr>
                  </table>
                  
      <table width="675" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="307" valign="top"><img src="../../../images/user/guide/online_m13.jpg"></td>
          <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td><img src="../../../images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                  <font color="#000000">E - test평가</font> </td>
              </tr>
              <tr> 
                <td height="10"></td>
              </tr>
              <tr> 
                <td valign="top">현재 진행되고 있는 테스트에 응시 하실 수 있습니다.</td>
              </tr>
              <tr> 
                <td><img src="../../../images/user/guide/online_num2.gif" width="13" height="14" align="absmiddle"> 
                  <font color="#000000">E - test결과</font></td>
              </tr>
              <tr> 
                <td height="10"></td>
              </tr>
              <tr> 
                <td valign="top">현재 해당되는 테스트와 그간에 응시한 테스트 이력을 조회 하실<br>
                  수 있습니다.</td>
              </tr>
            </table></td>
        </tr>
        <tr > 
          <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
        </tr>
        <tr align="right"> 
          <td colspan="2"><a href="#"><img src="../../../images/user/button/btn_top.gif" border="0"></a></td>
        </tr>
      </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="351" valign="top"><img src="../../../images/user/guide/st_online2-6.gif" width="333" height="16"><a name="7" id="7"></a></td>
                      <td width="324" align="right" valign="top">&nbsp;</td>
                    </tr>
                    <tr> 
                      <td height="15" colspan="2">&nbsp;</td>
                    </tr>
                  </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="307" valign="top"><img src="../../../images/user/guide/online_m15.jpg"></td>
                      <td width="368" valign="top"> <table width="355" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="../../../images/user/guide/online_num1.gif" width="13" height="14" align="absmiddle"> 
                              <font color="#000000">설문리스트</font></td>
                          </tr>
                          <tr> 
                            <td height="10"></td>
                          </tr>
                          <tr> 
                            <td valign="top">설문은 크게 과정/일반/콘텐츠/대상자 부분으로 나뉘어 설문이 
                              진행됩니다.<br>
                              <br>
                              <font color="#CC3300">① </font>해당 설문분야를 선택한 후 설문리스트의 
                              <font color="#CC3300">②상태</font>에서 응시가능한 리스트의 <font color="#CC3300">③설문지명을 
                              클릭</font>하시면 해당 설문지로 연결됩니다.</td>
                          </tr>
                        </table></td>
                    </tr>
                    <tr > 
                      <td height="14" colspan="2" background="../../../images/user/guide/vline.gif" ></td>
                    </tr>
                    <tr align="right"> 
                      <td colspan="2"><a href="#"><img src="../../../images/user/button/btn_top.gif" border="0"></a></td>
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
