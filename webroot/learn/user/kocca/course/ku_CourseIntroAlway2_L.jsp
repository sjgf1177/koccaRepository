<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명:  ku_CourseIntroAlway2_L.jsp
//  3. 개      요: 과정조회 (상시)
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.01.12
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
    box.put("leftmenu", "02");                       // 메뉴 아이디 세팅
    String  v_process         = box.getString("p_process");

    String  v_sphere     = box.getString("p_sphere");


%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenTabSelect(sphere){
      document.form1.p_sphere.value = sphere;
      document.form1.p_process.value  = 'SubjectListAlway';
      document.form1.action='/servlet/controller.course.KCourseIntroServlet';
      document.form1.target = "_self";
      document.form1.submit();
    }
//-->
</SCRIPT>

<form name="form1" method="post" >
    <input type='hidden' name='p_process'  value="<%=v_process%>">
    <input type='hidden' name='p_rprocess' value="">
    <input type='hidden' name='p_sphere'   value="<%=v_sphere%>">
    <input type='hidden' name='p_subj'     value="">

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 과정소개 > 개별과정</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/course/<%=tem_subimgpath%>/tit_course_alway.gif"></td>
                          </tr>
                          <tr>
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/course/si_always.jpg" ></td>
                          </tr>
                          <tr> 
                            <td height="19"></td>
                          </tr>
                        </table>
                        <!--롤오버탭 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="94"><img src="/images/user/kocca/course/st_sub_alw05_over.gif"></td>
							<td width="3">&nbsp;</td>
							<td width="94"><a href="javascript:whenTabSelect('1')"><img src="/images/user/kocca/course/st_sub_alw01.gif" name="Image7"  id="Image7" onMouseOver="MM_swapImage('Image7','','/images/user/kocca/course/st_sub_alw01_over.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
                            <td width="94"><a href="javascript:whenTabSelect('2')"><img src="/images/user/kocca/course/st_sub_alw02.gif" name="Image8"  id="Image8" onMouseOver="MM_swapImage('Image8','','/images/user/kocca/course/st_sub_alw02_over.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
                            <td width="3">&nbsp;</td>
                            <td width="94"><a href="javascript:whenTabSelect('3')"><img src="/images/user/kocca/course/st_sub_alw03.gif" name="Image9" id="Image9" onMouseOver="MM_swapImage('Image9','','/images/user/kocca/course/st_sub_alw03_over.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
                            <td width="3">&nbsp;</td>
							<!--td width="94"><a href="javascript:whenTabSelect('4')"><img src="/images/user/kocca/course/st_sub_alw04.gif" name="Image11" id="Image11" onMouseOver="MM_swapImage('Image11','','/images/user/kocca/course/st_sub_alw04_over.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
                            <td width="3">&nbsp;</td-->
                            <td width="295">&nbsp;</td>
                          </tr>
                          <tr> 
                            <td height="5" colspan="10" ></td>
                          </tr>
                          <tr> 
                            <td height="1" colspan="10" class="linecolor_cource02"></td>
                          </tr>
                          <tr> 
                            <td height="17" colspan="10">&nbsp;</td>
                          </tr>
                        </table>


                        <!--상시과정안내 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="144"><div align="center"><img src="/images/user/kocca/course/text_sub_alw05.gif" ></div></td>
                            <td width="1" class="tbl_bleft5"><img src="/images/user/kocca/course/vline.gif" ></td>
                            <td class="tbl_bleft5"><img src="/images/user/kocca/course/text_top_always.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="20" colspan="3" >&nbsp;</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/course/st_sub_box_top.gif"></td>
                          </tr>
                          <tr> 
                            <td background="/images/user/kocca/course/bg_box.gif">
                              <table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="27">&nbsp;</td>
                                  <td width="625">
<!--
                                    <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td><img src="/images/user/kocca/course/sst_open_courser.gif" ></td>
                                      </tr>
                                      <tr> 
                                        <td height="5"></td>
                                      </tr>
                                    </table>
                                    <! --개설과목 한줄 -- >
                                    <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="22" colspan="3">&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td width="208"></td>
                                        <td width="208"></td>
                                        <td width="209"></td>
                                      </tr>
                                      <tr> 
                                        <td height="10" colspan="3"></td>
                                      </tr>
                                      <tr > 
                                        <td height="1" colspan="3" background="/images/user/kocca/course/bg_dot_line03.gif"></td>
                                      </tr>
                                    </table>
-->
                                    <!--특징 -->
                                    <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="20">&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td><img src="/images/user/kocca/course/sst_spack.gif" ></td>
                                      </tr>
                                      <tr> 
                                        <td height="10"></td>
                                      </tr>
                                    </table>
                                    <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td colspan="4"><img src="/images/user/kocca/course/bar_spack_top.gif" ></td>
                                      </tr>
                                      <tr bgcolor="#F9F7E8"> 
                                        <td width="19" height="74">&nbsp;</td>
                                        <td width="35" valign="top"><img src="/images/user/kocca/course/i_spack.gif" ></td>
                                        <td width="26">&nbsp;</td>
                                        <td width="552">
										  <strong>모집방법</strong> : 1인1강좌 제한, 선착순모집<br> 
                                          <strong>수강정원</strong> : 70명<br> 
                                          <strong>수료기준</strong> : 과정별 기준 상이<br> 
                                          <strong>특징</strong> : 오리엔테이션이나 1회 오프라인 특강을 통해 친목 도모및 인적 네트워크 구축
                                          <!--strong>모집방법</strong> : 1인1강좌 제한, 선착순모집<br> 
                                          <strong>수강정원</strong> : 70명<br> 
                                          <strong>수료기준</strong> : 진도율 (60%) 과제 (10%) : 온라인 시험(10%) : 참여울 (20%) 총점 60점 이상<br> 
                                          <strong>특징</strong> : 오리엔테이션이나 1회 오프라인 특강을 통해 친목 도모및 인적 네트워크 구축-->
                                        </td>
                                      </tr>
                                      <tr> 
                                        <td colspan="4"><img src="/images/user/kocca/course/bar_spack_bottom.gif" ></td>
                                      </tr>
                                      <tr> 
                                        <td height="20" colspan="4">&nbsp;</td>
                                      </tr>
                                    </table></td>
                                  <td width="27">&nbsp;</td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr> 
                            <td><img src="/images/user/kocca/course/st_sub_box_bottom.gif" ></td>
                          </tr>
                        </table>
</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/kocca/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
