<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명:  ku_CourseIntroAlway1_L.jsp
//  3. 개      요: 과정조회
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
    box.put("leftmenu","02");                       // 메뉴 아이디 세팅
    String  v_process         = box.getString("p_process");
    String  v_sphere     = box.getString("p_sphere");
    String  upload_url = conf.getProperty("url.upload");

    String v_subj       = "";
    String v_subjnm     = "";
	String v_muserid    = "";
	String v_mname      = "";
    String v_lowerclass = "";
    String v_mclassnm   = "";
    String v_introducefilenamereal = "";
    String v_introducefilenamenew  = "";
    String v_introducefilenamenew_view  = "";
    int     v_total           =  0;
    int     i                 =  0;

System.out.println("---------" + v_sphere);
    ArrayList list   = (ArrayList)request.getAttribute("SubjectList");

    // IMAGE NAME 변수 처리
    String v_img_type = "";
    if (v_sphere.equals("1")) { 
        v_img_type = "01";
    } else if (v_sphere.equals("2")) {
        v_img_type = "02";
    } else if (v_sphere.equals("3")) {
        v_img_type = "03";
	} else if (v_sphere.equals("4")) {
        v_img_type = "04";
    } else {
        v_img_type = "05";
    }
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

    // 과정 내용보기
    function whenSubjInfo(subj,sphere){
        document.form1.p_subj.value        = subj;
        document.form1.p_sphere.value = sphere;
        document.form1.p_process.value  = 'SubjectPreviewPage';
        document.form1.p_rprocess.value = 'SubjectListAlway';
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
    <input type='hidden' name='p_subjtype'    value="alway">

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td  class="location" > HOME > 과정소개 > 상시과정</td>
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
                            <td width="94">
                            <% if (v_sphere.equals("1")) {                   %>
                              <img src="/images/user/kocca/course/st_sub_alw01_over.gif">
                            <% } else {                                             %>
                              <a href="javascript:whenTabSelect('1')"><img src="/images/user/kocca/course/st_sub_alw01.gif" name="Image7"  id="Image7" onMouseOver="MM_swapImage('Image7','','/images/user/kocca/course/st_sub_alw01_over.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
                            <% }                                                    %>
                            </td>
                            <td width="3">&nbsp;</td>
                            <td width="94">
                            <% if (v_sphere.equals("2")) {                   %>
                              <img src="/images/user/kocca/course/st_sub_alw02_over.gif">
                            <% } else {                                             %>
                              <a href="javascript:whenTabSelect('2')"><img src="/images/user/kocca/course/st_sub_alw02.gif" name="Image8"  id="Image8" onMouseOver="MM_swapImage('Image8','','/images/user/kocca/course/st_sub_alw02_over.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
                            <% }                                                    %>
                            </td>
                            <td width="3">&nbsp;</td>
                            <td width="94">
                            <% if (v_sphere.equals("3")) {                   %>
                              <img src="/images/user/kocca/course/st_sub_alw03_over.gif">
                            <% } else {                                             %>
                              <a href="javascript:whenTabSelect('3')"><img src="/images/user/kocca/course/st_sub_alw03.gif" name="Image9" id="Image9" onMouseOver="MM_swapImage('Image9','','/images/user/kocca/course/st_sub_alw03_over.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
                            <% }                                                    %>
                            </td>
                            <td width="3">&nbsp;</td>
							<td width="94">
                            <% if (v_sphere.equals("4")) {                   %>
                              <img src="/images/user/kocca/course/st_sub_alw04_over.gif">
                            <% } else {                                             %>
                              <a href="javascript:whenTabSelect('4')"><img src="/images/user/kocca/course/st_sub_alw04.gif" name="Image11" id="Image11" onMouseOver="MM_swapImage('Image11','','/images/user/kocca/course/st_sub_alw04_over.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
                            <% }                                                    %>
                            </td>
                            <td width="3">&nbsp;</td>
                            <td width="94">
                              <a href="javascript:whenTabSelect('')"><img src="/images/user/kocca/course/st_sub_alw05.gif" name="Image10" id="Image10" onMouseOver="MM_swapImage('Image10','','/images/user/kocca/course/st_sub_alw05_over.gif',1)" onMouseOut="MM_swapImgRestore()"></a>
                            </td>
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
                        <!--장르별분야_서브텍스트-->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td width="144"><div align="center"><img src="/images/user/kocca/course/text_sub_alw<%=v_img_type%>.gif" ></div></td>
                            <td width="1" class="tbl_bleft5"><img src="/images/user/kocca/course/vline.gif" ></td>
                            <td class="tbl_bleft5"><img src="/images/user/kocca/course/text_sub_alw<%=v_img_type%>_01.gif" ></td>
                          </tr>
                          <tr> 
                            <td height="20" colspan="3" >&nbsp;</td>
                          </tr>
                        </table>
                        <!--장르별분야_내용 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><img src="/images/user/kocca/course/st_sub_box_top.gif"></td>
                          </tr>
                          <tr> 
                            <td background="/images/user/kocca/course/bg_box.gif"><table width="680" border="0" cellspacing="0" cellpadding="0">
                                <tr> 
                                  <td width="27">&nbsp;</td>
                                  <td width="625">
                                    <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td><img src="/images/user/kocca/course/sst_open_courser.gif" alt="개설과목"></td>
                                      </tr>
                                      <tr> 
                                        <td height="5"></td>
                                      </tr>
                                    </table>
<%

                    v_total = list.size();
                    for(i = 0; i < v_total; i++) {
                        DataBox dbox = (DataBox)list.get(i);
                        v_subj                  = dbox.getString("d_subj");
                        v_subjnm                = dbox.getString("d_subjnm");
                        v_muserid               = dbox.getString("d_muserid");
                        v_mname                 = dbox.getString("d_mname");
                        v_lowerclass            = dbox.getString("d_lowerclass");
                        v_mclassnm              = dbox.getString("d_mclassnm");
                        v_introducefilenamereal = dbox.getString("d_introducefilenamereal");
                        v_introducefilenamenew  = dbox.getString("d_introducefilenamenew");
                        if (v_introducefilenamenew.equals("")) v_introducefilenamenew_view = "/images/user/kocca/course/photo_blank.gif";
                        else                                   v_introducefilenamenew_view = upload_url + "bulletin/" + v_introducefilenamenew;


                        if ((i % 3) == 0) {
                            if (i != 0) {
%>
                                      </tr>
                                      <tr> 
                                        <td height="10" colspan="3"></td>
                                      </tr>
                                      <tr > 
                                        <td height="1" colspan="3" background="/images/user/kocca/course/bg_dot_line03.gif"></td>
                                      </tr>
                                    </table>
<%
                            }
%>
                                    <!--개설과목 한줄 -->
                                    <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="22" colspan="3">&nbsp;</td>
                                      </tr>
                                      <tr> 
<%
                        }
%>

                                        <td width="208"> 
                                          <!--과목소개 _컨덴츠-->
                                          <table width="208" border="0" cellspacing="0" cellpadding="0">
                                            <tr> 
                                              <td width="90" rowspan="4" valign="top"><table width="90" border="0" cellpadding="0" cellspacing="3" bgcolor="#e7e7e7">
                                                  <tr> 
                                                    <td><table width="84" border="0" cellpadding="0" cellspacing="2" bgcolor="#cccccc">
                                                        <tr> 
                                                          <td><img src="<%=v_introducefilenamenew_view%>" width="80" height="70"></td>
                                                        </tr>
                                                      </table></td>
                                                  </tr>
                                                </table></td>
                                              <td width="107" class="tbl_bleft_black"><%=v_subjnm%></td>
                                              <td width="13" rowspan="4">&nbsp;</td>
                                            </tr>
                                            <tr> 
                                              <td class="linecolor_course01" height="1"></td>
                                            </tr>
                                            <tr> 
                                              <td height="39" valign="top" class="tbl_bleft">책임교수:<%=v_mname%></td>
                                            </tr>
                                            <tr> 
                                              <td height="15" class="tbl_bleft"><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_sphere%>')"><img src="/images/user/kocca/button/btn_more.gif"   alt="more" border="0"></a></td>
                                            </tr>
                                          </table>
                                        </td>
<%
                    } // END FOR
                
                    if (i == 0) {
%>
                                    <!--개설과목 한줄 -->
                                    <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="22" colspan="3">&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td colspan="3" align="center"><b>개설된 과목이 없습니다.</b></td>
<%
                    } else {
                        for(int j = 0; j < 3-(i%3); j++) {
%>
                                        <td width="208">&nbsp;</td>
<%
                        }
                    }
%>

                                      </tr>
                                      <tr> 
                                        <td height="10" colspan="3"></td>
                                      </tr>
                                      <tr > 
                                        <td height="1" colspan="3" background="/images/user/kocca/course/bg_dot_line03.gif"></td>
                                      </tr>
                                    </table>

                                    <!--특징 -->
                                    <table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr> 
                                        <td height="20">&nbsp;</td>
                                      </tr>
                                      <tr> 
                                        <td><img src="/images/user/kocca/course/sst_spack.gif"  alt="특징"></td>
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
                                        <td width="19" height="85">&nbsp;</td>
                                        <td width="35" valign="top"><img src="/images/user/kocca/course/i_spack.gif" ></td>
                                        <td width="26">&nbsp;</td>
                                        <td width="552">
										<% if (v_sphere.equals("4")) {                   %>
										
										<b>개강일</b> : 매달 넷째주 월요일<br>
										<b>교육기간</b> : 2주 과정(7회차)<br>
										<b>모집방법</b> : 1인1강좌 제한, 온라인 선착순 모집<br>
										<b>수강정원</b> : 70명

										<% } else {                                             %>

										<strong>개강일</strong> 
                                          : 매달 첫 주 월요일<br>
										<strong>모집방법</strong> 
                                          : 1인1강좌 제한, 선착순모집<br> <strong>수강정원</strong> 
                                          : 70명<br> <strong>수료기준</strong> : 과정별 기준 상이<br> <strong>특징</strong> : 
                                          오리엔테이션이나 1회 오프라인 특강을 통해 친목 도모및 인적 네트워크 
                                          구축
										  <% }                                                    %>
										<!--strong>모집방법</strong> 
                                          : 1인1강좌 제한, 선착순모집<br> <strong>수강정원</strong> 
                                          : 70명<br> <strong>수료기준</strong> : 진도율 
                                          (60%) 과제 (10%) : 온라인 시험(10%) ; 참여울 (20%) 
                                          총점 60점 이상<br> <strong>특징</strong> : 
                                          오리엔테이션이나 1회 오프라인 특강을 통해 친목 도모및 인적 네트워크 
                                          구축--></td>
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
