<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명:  ku_SubjectIntro1.jsp
//  3. 개      요: 과정조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 06.01.31
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
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%

    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅
    String  v_process         = box.getString("p_process");
    
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topApply.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--

 function move(tab){
    document.form1.p_process.value= "SubjectIntro";
    document.form1.p_tab.value = tab;
    document.form1.action = "/servlet/controller.propose.KProposeCourseServlet";
    document.form1.submit();
 }
 
//-->
</SCRIPT>

<form name="form1" method="post" >
    <input type='hidden' name='p_process'  >
    <input type='hidden' name='p_subj'     value="">
    <input type='hidden' name='p_tab'>

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 수강신청 &gt; 수강신청 취소안내</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/apply/<%=tem_subimgpath%>/tit_guide.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td height="12" valign="top"><img src="/images/user/kocca/apply/text_guide_con.gif"></td>
                          </tr>
                          <tr > 
                            <td height="1" background="/images/user/kocca/apply/dot_bg_9.gif"></td>
                          </tr>
                          <tr > 
                            <td height="16" ></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="566" align="right"><img src="/images/user/kocca/apply/tab_gapply_on.gif" width="94" height="30"></td>
                            <td width="5" align="right">&nbsp;</td>
                            <td width="94" align="right"><a href="javascript:move(2)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image31','','/images/user/kocca/apply/tab_gapplycan_on.gif',1)"><img src="/images/user/kocca/apply/tab_gapplycan.gif" name="Image31" width="94" height="30" border="0"></a></td>
                            <td width="14" align="right">&nbsp;</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellpadding="0" cellspacing="0" background="/images/user/kocca/apply/guide_box_bg.gif">
                          <tr> 
                            <td valign="top"><img src="/images/user/kocca/apply/guide_box_top.gif"></td>
                          </tr>
                          <tr> 
                            <td align="center" valign="top"><table width="660" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td><img src="/images/user/kocca/apply/sst_guide1.gif"></td>
                                </tr>
                                <tr>
                                  <td>&nbsp;</td>
                                </tr>
                              </table>
                              <table width="660" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td class="tbl_menualtext" >1. 수강신청메뉴선택</td>
                                </tr>
                                <tr>
                                  <td height="2"></td>
                                </tr>
                                <tr>
                                  <td height="5" background="/images/user/kocca/apply/guide_linebg.gif"></td>
                                </tr>
                              </table>
                              <table width="660" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td>&nbsp;</td>
                                </tr>
                                <tr>
                                  <td align="center"><img src="/images/user/kocca/apply/guide_1img1.gif"></td>
                                </tr>
                                <tr>
                                  <td>&nbsp;</td>
                                </tr>
                              </table>
                              <table width="660" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td class="tbl_menualtext">2. 원하는 
                                    과정의 신청버튼 클릭</td>
                                </tr>
                                <tr> 
                                  <td height="2"></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/kocca/apply/guide_linebg.gif"></td>
                                </tr>
                              </table>
                              <table width="660" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="10"></td>
                                </tr>
                                <tr>
                                  <td class="tbl_gleft">① 수강신청하고자 하는 강좌유형을 선택하고 
                                    원하는 강좌를 확인한 후 ②신청 버튼을 클릭합니다.</td>
                                </tr>
                                <tr> 
                                  <td height="5"></td>
                                </tr>
                                <tr> 
                                  <td align="center"><img src="/images/user/kocca/apply/guide_1img2.gif"></td>
                                </tr>
                                <tr> 
                                  <td>&nbsp;</td>
                                </tr>
                              </table>
                              <table width="660" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td class="tbl_menualtext">3. 상세정보 화면을 확인하고 
                                    결제클릭</td>
                                </tr>
                                <tr> 
                                  <td height="2"></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/kocca/apply/guide_linebg.gif"></td>
                                </tr>
                              </table>
                              <table width="660" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="10"></td>
                                </tr>
                                <tr>
                                  <td class="tbl_gleft"><strong>무료과정</strong>인 
                                    경우는 신청을 누르면 바로 과정신청확인 질문이 나타나고 확인하면 신청이 즉시 
                                    이루어 집니다.</td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft"><strong>유료과정</strong>은 
                                    신청하는 과정의 상세 정보가 나오고 하단에는 결제화면이 뜸니다.<br>
                                    ① 마일리지를 보유하고 있으면 해당한도 내에서 마일리지로 수강료를 일부 결제 
                                    하실 수 있습니다.<br>
                                    ② 원하는 결제 상태를 클릭합니다.</td>
                                </tr>
                                <tr> 
                                  <td height="5"></td>
                                </tr>
                                <tr> 
                                  <td align="center"><img src="/images/user/kocca/apply/guide_1img3.gif"></td>
                                </tr>
                                <tr> 
                                  <td>&nbsp;</td>
                                </tr>
                              </table>
                              <table width="660" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td class="tbl_menualtext">4. 현금결제 클릭시 결제처리 
                                    팝업창이 뜹니다.</td>
                                </tr>
                                <tr> 
                                  <td height="2"></td>
                                </tr>
                                <tr> 
                                  <td height="5" background="/images/user/kocca/apply/guide_linebg.gif"></td>
                                </tr>
                              </table>
                              <table width="660" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td height="10"></td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft">① 입금자란에 입금자 성명을 쓰고 입금할 
                                    예정일을 정확하게 씁니다.</td>
                                </tr>
                                <tr>
                                  <td class="tbl_gleft">확인을 누르면 신청이 완료되었다는 메세지가 
                                    뜨면서 수강신청이 완료됩니다.</td>
                                </tr>
                                <tr> 
                                  <td height="5"></td>
                                </tr>
                                <tr> 
                                  <td align="center" valign="top"><img src="/images/user/kocca/apply/guide_1img4.gif" width="546" height="275"></td>
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
