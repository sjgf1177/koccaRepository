<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명:  ku_SubjectIntro2.jsp
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
                            <td  class="location" > HOME > 수강신청 &gt; 수강신청 취소/안내</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/apply/<%=tem_subimgpath%>/tit_guide.gif"></td>
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
                            <td width="566" align="right"><a href="javascript:move(1)" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image32','','/images/user/kocca/apply/tab_gapply_on.gif',1)"><img src="/images/user/kocca/apply/tab_gapply.gif" name="Image32" border="0"></a></td>
                            <td width="5" align="right">&nbsp;</td>
                            <td width="94" align="right"><img src="/images/user/kocca/apply/tab_gapplycan_on.gif"></td>
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
                                  <td><img src="/images/user/kocca/apply/sst_guide3.gif" width="220" height="14"></td>
                                </tr>
                                <tr>
                                  <td>&nbsp;</td>
                                </tr>
                              </table>
                              <table width="660" border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td class="tbl_menualtext" >1. 수강신청 확인/취소 메뉴를 
                                    클릭하여 수강신청한 리스트를 확인합니다.</td>
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
                                    경우는 ①신청취소를 누르면 바로 신청취소가 이루어집니다.(수강신청취소기간내에)</td>
                                </tr>
                                <tr>
                                  <td class="tbl_gleft"><strong>유료과정</strong>인 
                                    경우라도 입금 확인중일 경우는 바로 ①신청취소를 하실 수 있습니다.</td>
                                </tr>
                                <tr> 
                                  <td height="5"></td>
                                </tr>
                                <tr> 
                                  <td align="center" valign="top"><img src="/images/user/kocca/apply/guide_1img5.gif" width="549" height="121"></td>
                                </tr>
                                <tr> 
                                  <td>&nbsp;</td>
                                </tr>
                              </table>
                              <table width="660" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td class="tbl_menualtext">2. 입금확인 되었을 경우의 신청취소<strong>(유료과정일경우)</strong></td>
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
                                  <td class="tbl_gleft">②수강신청취소 기간에 해당하면 ③신청취소를 
                                    하실 수 있습니다.</td>
                                </tr>
                                <tr> 
                                  <td height="5"></td>
                                </tr>
                                <tr> 
                                  <td align="center" valign="top"><img src="/images/user/kocca/apply/guide_1img6.gif" width="549" height="121"></td>
                                </tr>
                                <tr>
                                  <td>&nbsp;</td>
                                </tr>
                                <tr> 
                                  <td class="tbl_gleft">신청취소를 누르면 <strong>④환불신청 
                                    화면이 뜹니다</strong>. 정보를 입력하고 확인하면 입금구분은 환불 처리중이 
                                    되며 관리자 승인후 환불 완료와 신청취소가 이루어집니다.</td>
                                </tr>
                                <tr> 
                                  <td height="5"></td>
                                </tr>
                                <tr> 
                                  <td align="center" valign="top"><img src="/images/user/kocca/apply/guide_1img7.gif" width="535" height="242"></td>
                                </tr>
                                <tr> 
                                  <td>&nbsp;</td>
                                </tr>
                              </table>
                            </td>
                          </tr>
                          <tr> 
                            <td valign="bottom"><img src="/images/user/kocca/apply/guide_box_bo.gif"></td>
                          </tr>
                        </table>


</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
