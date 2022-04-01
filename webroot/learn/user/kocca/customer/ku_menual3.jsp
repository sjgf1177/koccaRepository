<%
//**********************************************************
//  1. 제      목: HelpDesk > 학습환경도우미
//  2. 프로그램명 : ku_menual3.jsp
//  3. 개      요: 학습환경도우미 (인터넷)
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정: 
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
    RequestBox box       = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
	box.put("leftmenu","03"); 

    String  v_process     = box.getString("p_process");
                  
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<SCRIPT>
var old_menu = ''; var old_cell = '';
function comment( submenu, cellbar) {

	if( old_menu != submenu ) {

		if( old_menu !='' ) {
			old_menu.style.display = 'none';
		}
		submenu.style.display = 'block';
		old_menu = submenu;
		old_cell = cellbar;

	} else {
		submenu.style.display = 'none';
		old_menu = '';
		old_cell = '';
	}
}


function move(tab){
	document.form1.p_process.value= "Help";
	document.form1.p_tab.value = tab;
	document.form1.action = "/servlet/controller.homepage.KHomePageHelpServlet";
	document.form1.submit();
}

</SCRIPT>

<style type="text/css">
<!--
.l-padding {
	padding-left: 12px;
}
-->
</style>


<form name = "form1" enctype = "multipart/form-data" method = "post">
		<input type = "hidden" name = "p_process"   value = "">
		<input type = "hidden" name = "p_tab"		value = "">

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 고객센타 &gt; 온라인학습이용안내</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/<%=tem_subimgpath%>/tit_menual.gif"></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/customer/text_img_menual.gif"></td>
                          </tr>
                          <tr> 
                            <td height="15"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td align="right"><a href="javascript:move(1)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image35','','/images/user/kocca/customer/tab_01on.gif',1)"><img src="/images/user/kocca/customer/tab_01.gif" name="Image35" border="0"></a><a href="javascript:move(2)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image33','','/images/user/kocca/customer/tab_02on.gif',1)"><img src="/images/user/kocca/customer/tab_02.gif" name="Image33" border="0"></a><img src="/images/user/kocca/customer/tab_03on.gif"><a href="javascript:move(4)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image34','','/images/user/kocca/customer/tab_04on.gif',1)"><img src="/images/user/kocca/customer/tab_04.gif" name="Image34" border="0"></a></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellpadding="0" cellspacing="0" background="/images/user/kocca/apply/guide_box_bg.gif">
                          <tr> 
                            <td valign="top"><img src="/images/user/kocca/apply/guide_box_top.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top"><table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td><img src="/images/user/kocca/customer/st_g_study.gif" width="220" height="14"></td>
                                </tr>
                                <tr> 
                                  <td height="8"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td class="tbl_menualtext2" >1. 마이클래스 메뉴를 선택합니다.</td>
                                </tr>
                                <tr> 
                                  <td height="2"></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/kocca/apply/guide_linebg.gif"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td align="center"><img src="/images/user/kocca/customer/menual_img1.gif" width="203" height="136"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td class="tbl_menualtext2">2. 좌측메뉴의 강의실을 클릭합니다.</td>
                                </tr>
                                <tr> 
                                  <td height="2"></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/kocca/apply/guide_linebg.gif"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="10"></td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft"><strong>학습중인과정</strong><br>
                                    현재 학습하고 있는 수강신청 완료된 과정들이 학습중인과정에 나옵니다. <br>
                                    해당과정의 ①학습현황보기를 클릭하면 그 과정의 상세보기를 보실 수 있으며 ②학습하기를 
                                    누르면 학습창이 뜨고 그 과정을 학습하실 수 있습니다.</td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft"><strong>학습예정과정<br>
                                    </strong> 수강신청은 하였으나 아직 수강신청이 완료되지 않은 과정들이 
                                    나옵니다.<br>
                                    아직 학습은 할수 없으나 ③ 해당 과정의 맛보기 강좌를 미리 보실 수 있습니다.</td>
                                </tr>
                                <tr> 
                                  <td height="5"></td>
                                </tr>
                                <tr> 
                                  <td align="center" valign="top"><img src="/images/user/kocca/customer/menual_img2.gif"></td>
                                </tr>
                                <tr> 
                                  <td>&nbsp;</td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td class="tbl_menualtext2">3. 학습현황보기를 클릭하시면 
                                    과정상세보기 창이 뜹니다.</td>
                                </tr>
                                <tr> 
                                  <td height="2"></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/kocca/apply/guide_linebg.gif"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="10"></td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft">⑤ 학습과정의 나의 진도율과 학습활동 정보를 
                                    확인하실 수 있습니다.</td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft">⑥ 취득점수확인 -이수기준점수와 현재취득점수를 
                                    보여주어 이수에 필요한 정보를 확인할 수 있습니다.</td>
                                </tr>
                                <tr>
                                  <td class="tbl_gleft">⑦ 학습진행상황 <br>
                                    최종평가 및 중간평가, 형성평가의 응시현황 및 리포트, 과정설문에 응답했는지의 
                                    여부를 확인할 수 있습니다.<br>
                                    또한 학습목차별 세부 학습현황을 확인할 수 있습니다.</td>
                                </tr>
                                <tr> 
                                  <td height="5"></td>
                                </tr>
                                <tr> 
                                  <td align="center"><img src="/images/user/kocca/customer/menual_img3.gif" width="581" height="525"></td>
                                </tr>
                                <tr> 
                                  <td>&nbsp;</td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td class="tbl_menualtext2">4. 학습하기를 클릭하면 학습창이 
                                    떠서 학습하실 수 있습니다.</td>
                                </tr>
                                <tr> 
                                  <td height="2"></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/kocca/apply/guide_linebg.gif"></td>
                                </tr>
                              </table>
                              <table width="650" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="10"></td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft">학습창 첫화면엔 그 과정의 공지사항과 진도율을 
                                    확인하실수 있으며 하단에는 과정의 차시를 한번에 보실 수 있습니다.</td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft">⑧ 학습시작을 누르면 과정학습이 바로 시작됩니다.</td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft">⑨ <strong>학습 보조도구메뉴<br>
                                    </strong>* 성적/목차 - 본인의 취득점수와 학습진행상황 및 세부 목차별 
                                    학습여부를 확인할 수 있습니다. <br>
                                    * 질문하기 - 학습하면서 궁금한 점들을 질문하실 수 있습니다.<br>
                                    * 리포트함 - 출제된 레포트를 확인하고 제출합니다.<br>
                                    * 자료실 - 과정학습에 관련된 자료나 도움이 되는 글들을 여러학습자들과 공유할 
                                    수 있는 공간입니다.<br>
                                    * 평가하기 - 과정학습후 평가하실 수 있습니다.</td>
                                </tr>
                                <tr>
                                  <td class="tbl_gleft">⑩ 과정 차시 목록를 한눈에 보실 수 있고 
                                    각 차시별로 클릭해서 들어갈 수 있습니다.</td>
                                </tr>
                                <tr> 
                                  <td height="5"></td>
                                </tr>
                                <tr> 
                                  <td align="center" valign="top"><img src="/images/user/kocca/customer/menual_img4.gif" width="598" height="406"></td>
                                </tr>
                                <tr> 
                                  <td>&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/kocca/apply/guide_box_bo.gif"></td>
                          </tr>
                        </table>
	</form>




<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
