<%
//**********************************************************
//  1. 제      목: 전문가 과정소개
//  2. 프로그램명:  gu_CourseIntroOFF_L.jsp
//  3. 개      요: 전문가 과정소개
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2006.01.14
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅

    String  v_process       = box.getString("p_process");
    String  v_iscourseYn    = box.getString("p_iscourseYn");

    String v_course       = "";
    String v_coursenm     = "";
    String v_subj         = "";
    String v_subjnm       = "";
    String v_upperclass   = "";
    String v_middleclass  = "";
    String v_lowerclass   = "";
    String v_classname    = "";
    String v_biyong       = "";
    String v_tmp_course   = "";

    int    v_subjcnt        = 0;
    int    v_total          = 0;
    int    i        = 0;
    DecimalFormat  df = new DecimalFormat("###,##0");

    ArrayList list      = null;
    list = (ArrayList)request.getAttribute("SubjectList");

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--
    // 일반/전문가, 대분류코드 선택
    function whenSelect(sel){
        document.form1.p_iscourseYn.value = sel;
        document.form1.target = "_self";
        document.form1.action='/servlet/controller.course.CourseIntroServlet';
        document.form1.p_process.value = 'SubjectList';

        document.form1.submit();
    }

    // 과정 내용보기
    function whenSubjInfo(subj){
        document.form1.p_subj.value     = subj;
        document.form1.p_process.value  = 'SubjectPreviewPage';
        document.form1.p_rprocess.value = 'SubjectList';
        document.form1.action='/servlet/controller.course.CourseIntroServlet';
        document.form1.target = "_self";
        document.form1.submit();
    }
//-->

</script>

<form name="form1" method="post" >
    <input type='hidden' name='p_process' value="<%=v_process%>">
    <input type='hidden' name='p_rprocess' value="">
    <input type='hidden' name='p_iscourseYn' value="<%=v_iscourseYn%>">
    <input type='hidden' name='p_subj' value="">

        <table width="720" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td height="35" align="right"  background="/images/user/game/course/<%=tem_subimgpath%>/tit_eduintro.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
              HOME > 과정안내 > 교육과정소개 </td>
          </tr>
          <tr> 
            <td height="20"></td>
          </tr>
        </table>

        <table width="720" border="0" cellpadding="0" cellspacing="0">
          <tr> 
            <td colspan="3"></td>
          </tr>
          <tr> 
            <td colspan="3">
            	<table border="0" cellpadding="0" cellspacing="0" width="100%">
            		<tr>
            			<td><a href="javascript:whenSelect('N','')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image49','','/images/user/game/course/st_cyber_course_on.gif',1)"><img src="/images/user/game/course/st_cyber_course_off.gif" name="Image49" border="0"></a><img src="/images/user/game/course/st_togher_course_on.gif" name="Image48" border="0"></td>
            			<td align="right" style="padding-right:5px;padding-bottom:5px"></td>
            		</tr>
            	</table>
            </td>
          </tr>
          <tr> 
            <td width="9" height="16" valign="top"><img src="/images/user/game/course/g_box_left.gif" width="9" height="16"></td>
            <td width="702" valign="bottom" background="/images/user/game/course/bg_g_box.gif"><img src="/images/user/game/mystudy/type1/blank.gif" width="20" height="10"></td>
            <td width="9" valign="top"><div align="right"><img src="/images/user/game/course/g_box_right.gif" width="9" height="16"></div></td>
          </tr>
          <tr>
            <td height="10" colspan="3"><img src="/images/user/game/course/text_sub_prof.jpg"></td>
          </tr>
          <tr> 
            <td height="10" colspan="3"></td>
          </tr>
        </table>

        <!-- 과정조회 -->
        <table width="720" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td height="5"></td>
          </tr>
        </table>
        <table width="720" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="11">&nbsp;</td>
            <td><table width="689" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="4" height="27" valign="top" background="/images/user/game/course/ba_bg02.gif"><img src="/images/user/game/course/st_eduintro_left.gif" width="6" height="33"></td>
                <td  height="33" align="center" valign="bottom" background="/images/user/game/course/ba_bg02.gif">
                <table width="677" height="23" border="0" cellpadding="0" cellspacing="0">
                      <tr> 
                        <td width="45" align="center"  ><img src="/images/user/game/course/text_t_number.gif"></td>
                        <td width="1" valign="bottom"  ><img src="/images/user/game/course/vline02.gif"></td>
                        <td width="431" align="center" ><img src="/images/user/game/course/text_t_course_name.gif"></td>
                        <td width="1" valign="bottom" ><img src="/images/user/game/course/vline02.gif"></td>
                        <td width="100" align="center"  ><img src="/images/user/game/course/text_t_applymon.gif"></td>
                        <td width="1" valign="bottom"  ><img src="/images/user/game/course/vline02.gif"></td>
                        <td width="79" align="center"  ><img src="/images/user/game/course/text_t_skill.gif"></td>
                      </tr>                           
                    </table></td>
                <td width="4" align="right" valign="top" background="/images/user/game/course/ba_bg.gif"><img src="/images/user/game/course/st_eduintro_right.gif" width="6" height="33" ></td>
              </tr>
              <tr>
                <td height="5" colspan="3"></td>
              </tr>
              <tr >
                <td height="1" colspan="3"></td>
              </tr>
            </table></td>
            <td width="20">&nbsp;</td>
          </tr>
        </table>

        <table width="720" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="11" rowspan="4">&nbsp;</td>
            <td height="5" colspan="3"></td>
            <td width="20" rowspan="4">&nbsp;</td>
          </tr>
          <tr>
            <td width="6">&nbsp;</td>
            <td>
              <table width="677" border="1" cellspacing="1" cellpadding="1" style="border-collapse:collapse;" bordercolor="#ededed"  frame="hsides"> 

          <%


            for(i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox)list.get(i);

                v_course        = dbox.getString("d_course");
                v_coursenm      = dbox.getString("d_coursenm");
                v_subj          = dbox.getString("d_subj");
                v_subjnm        = dbox.getString("d_subjnm");
                v_upperclass    = dbox.getString("d_upperclass");
                v_middleclass   = dbox.getString("d_middleclass");
                v_lowerclass    = dbox.getString("d_lowerclass");
                v_classname     = dbox.getString("d_classname");
                v_biyong        = dbox.getString("d_biyong");
                v_subjcnt       = dbox.getInt("d_subjcnt");

            if(!v_tmp_course.equals(v_course)) {

                v_tmp_course = v_course;
%>
                <tr > 
                    <td width="46"  class="tbl_grc"   rowspan="<%=v_subjcnt %>"><%=i+1%></td>
                    <td width="206" class="tbl_grc" rowspan="<%=v_subjcnt %>"><%= v_coursenm %></td>
                    <td width="226" class="tbl_bleft" ><%= v_subjnm %></td>
                    <td width="101" class="tbl_grc"   rowspan="<%=v_subjcnt %>"><%=df.format(Integer.parseInt(v_biyong))%></td>
                    <td width="80"  class="tbl_grc"   ><a href="javascript:whenSubjInfo('<%=v_subj%>', '')"><img src="/images/user/game/button/btn_more_view.gif" width="67" height="19"></a></td>

<%
           } else { 
%>
                <tr > 
                    <td class="tbl_bleft" ><%=v_subjnm%></td>
                    <td width="80"  class="tbl_grc"   ><a href="javascript:whenSubjInfo('<%=v_subj%>', '')"><img src="/images/user/game/button/btn_more_view.gif" width="67" height="19"></a>
<%
    }  
%>

                </tr>
<!--
                <tr > 
                  <td height="2" colspan="5" background="/images/user/game/course/bg_dot_line.gif"></td>
                </tr>
-->
<%              }
            if(i == 0){
%>
                  <tr> 
                    <td class="tbl_grc" colspan="5">등록된 전문가 과정이 없습니다.</td>
                  </td>
<%
            }
%>
          

              </table>
            </td>
            <td width="6">&nbsp;</td>
          </tr>
          <tr>
            <td height="2" colspan="3" class="linecolor_board5"></td>
          </tr>
          <tr>
            <td height="2" colspan="3"></td>
          </tr>
        </table>
</form>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->