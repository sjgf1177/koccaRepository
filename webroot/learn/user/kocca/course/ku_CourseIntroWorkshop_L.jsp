<%
//**********************************************************
//  1. 제      목: SUBJECT LIST
//  2. 프로그램명:  ku_CourseIntroWorkshop_L.jsp
//  3. 개      요: 과정조회 (워크숍)
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
    box.put("leftmenu","05");                       // 메뉴 아이디 세팅
    String  v_process         = box.getString("p_process");
    String  v_sphere     = box.getString("p_sphere");
    String  upload_url = conf.getProperty("url.upload");

    String v_subj       = "";
    String v_subjnm     = "";
    String v_lowerclass = "";
    String v_mclassnm   = "";
    String v_introducefilenamereal = "";
    String v_introducefilenamenew  = "";
    String v_introducefilenamenew_view  = "";
    int     v_total           =  0;
    int     i                 =  0;
    String v_mname = "";


    ArrayList list   = (ArrayList)request.getAttribute("SubjectList");


%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/kocca/include/topCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
    function whenTabSelect(sphere){
      document.form1.p_sphere.value = sphere;
      document.form1.p_process.value  = 'SubjectListWorkshop';
      document.form1.action='/servlet/controller.course.KCourseIntroServlet';
      document.form1.target = "_self";
      document.form1.submit();
    }

    // 과정 내용보기
    function whenSubjInfo(subj,sphere){
        document.form1.p_subj.value        = subj;
        document.form1.p_sphere.value = sphere;
        document.form1.p_process.value  = 'SubjectPreviewPage';
        document.form1.p_rprocess.value = 'SubjectListWorkshop';
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
    <input type='hidden' name='p_subjtype'    value="workshop">

                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td  class="location" > HOME > 과정소개 > 워크숍과정</td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/course/<%=tem_subimgpath%>/tit_course_workshop.gif"></td>
                          </tr>
                          <tr>
                            <td height="20"></td>
                          </tr>
                        </table>
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td><img src="/images/user/kocca/course/text_main_workshop.jpg" ></td>
                          </tr>
                          <tr>
                            <td height="19"></td>
                          </tr>
                        </table>
                        <!--롤오버탭 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="94"><a href="javascript:whenTabSelect('')"><img src="/images/user/kocca/course/tab_work02.gif" name="Image312"  id="Image312" onMouseOver="MM_swapImage('Image312','','/images/user/kocca/course/tab_work02_on.gif',1)" onMouseOut="MM_swapImgRestore()"></a></td>
							<td width="3">&nbsp;</td>
							<td width="94"><img src="/images/user/kocca/course/tab_work01_on.gif"></td>
                            <td width="490">&nbsp;</td>
                          </tr>
                          <tr>
                            <td height="5" colspan="12" ></td>
                          </tr>
                          <tr>
                            <td height="1" colspan="12" class="linecolor_cource02"></td>
                          </tr>
                          <tr>
                            <td height="17" colspan="12">&nbsp;</td>
                          </tr>
                        </table>
                        <!--장르별분야_서브텍스트 -->
                        <table width="680" border="0" cellspacing="0" cellpadding="0">
                          <tr>
                            <td width="144"><div align="center"><img src="/images/user/kocca/course/text_t_workshop.gif" ></div></td>
                            <td width="1" class="tbl_bleft5"><img src="/images/user/kocca/course/vline.gif" ></td>
                            <td class="tbl_bleft5"><img src="/images/user/kocca/course/text_t_workshop_02.gif" ></td>
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
                                  <td width="625"><table width="625" border="0" cellspacing="0" cellpadding="0">
                                      <tr>
                                        <td><img src="/images/user/kocca/course/sst_open_courser.gif" ></td>
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
                        v_lowerclass            = dbox.getString("d_lowerclass");
                        v_mclassnm              = dbox.getString("d_mclassnm");
                        v_introducefilenamereal = dbox.getString("d_introducefilenamereal");
                        v_introducefilenamenew  = dbox.getString("d_introducefilenamenew");
                        if (v_introducefilenamenew.equals("")) v_introducefilenamenew_view = "/images/user/kocca/course/photo_blank.gif";
                        else                                   v_introducefilenamenew_view = upload_url + "bulletin/" + v_introducefilenamenew;

						v_mname = dbox.getString("d_mname");

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
                                              <td height="15" class="tbl_bleft"><a href="javascript:whenSubjInfo('<%=v_subj%>','<%=v_sphere%>')"><img src="/images/user/kocca/button/btn_more.gif"  border="0"></a></td>
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

                                    </td>
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
