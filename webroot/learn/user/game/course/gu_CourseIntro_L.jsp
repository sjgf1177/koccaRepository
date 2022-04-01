<%
//**********************************************************
//  1. 제      목: 과정안내
//  2. 프로그램명: gu_CourseIntro_L.jsp
//  3. 개      요: 과정안내 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.12.14
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
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);
    box.put("leftmenu","01");                       // 메뉴 아이디 세팅

    String  v_process     = box.getString("p_process");
    String  v_iscourseYn  = box.getStringDefault("p_iscourseYn","N");
    String v_tabnum       = box.getStringDefault("p_tabnum","design");

    String  v_code       = "";
    String  v_codenm     = "";
    String  v_tab_bg     = "";
    String  v_tab_class  = "";

    String v_subj           = "";
    String v_subjnm         = "";
    String v_upperclass     = "";
    String v_middleclass    = "";
    String v_sclowerclass   = "";
    String v_isonoff        = "";
    String v_usebook        = "";
    String v_isnew          = "";
    String v_ishit          = "";
    String v_isrecom        = "";
    String v_uclassnm       = "";
    String v_mclassnm       = "";

    String v_imgbook        = "";
    String v_imgNew         = "";
    String v_imgHit         = "";
    String v_imgRecom       = "";
    double v_sul_avg = 0; // 과정 만족도

    double tmp_sul_avg = 0;
    String v_imgStart = "";

    ArrayList list1      = null;
    list1 = (ArrayList)request.getAttribute("SubjectList");         // 과정 리스트

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
// 일반/전문가, 대분류코드 선택
function whenSelect(sel,gubun){
    document.form1.p_iscourseYn.value = sel;
    document.form1.p_tabnum.value   = gubun;
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.course.CourseIntroServlet';
    document.form1.p_process.value = 'SubjectList';

    document.form1.submit();
}

// 과정 내용보기
function whenSubjInfo(subj,tabnum){
    document.form1.p_subj.value     = subj;
    document.form1.p_tabnum.value   = tabnum;
    document.form1.p_process.value  = 'SubjectPreviewPage';
    document.form1.p_rprocess.value = 'SubjectList';
    document.form1.action='/servlet/controller.course.CourseIntroServlet';
    document.form1.target = "_self";
    document.form1.submit();
}
//-->
</SCRIPT>

<form name="form1" method="post" >
    <input type='hidden' name='p_process' value="<%=v_process%>">
    <input type='hidden' name='p_rprocess' value="">
    <input type='hidden' name='p_iscourseYn' value="<%=v_iscourseYn%>">
    <input type='hidden' name='p_tabnum'  value="<%=v_tabnum%>">
    <input type='hidden' name='p_subj' value="">




<!-- title -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="35" align="right"  background="/images/user/game/course/<%=tem_subimgpath%>/tit_eduintro.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> HOME > 과정안내 > 교육과정소개 </td>
  </tr>
  <tr>
    <td height="20"></td>
  </tr>
</table>
<table width="720" border="0" cellpadding="0" cellspacing="0">
   <tr>
      <td colspan="3">
      	<table border="0" cellpadding="0" cellspacing="0" width="100%">
      		<tr>
      			<td><img src="/images/user/game/course/st_cyber_course_on.gif"><a href="javascript:whenSelect('Y','')" onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('Image48','','/images/user/game/course/st_togher_course_on.gif',1)"><img src="/images/user/game/course/st_togher_course_off.gif" name="Image48" width="104" height="31" border="0"></a></td>
      			<td align="right" style="padding-right:5px;padding-bottom:5px"></td>
      		</tr>
      	</table>
      </td>
    </tr>
  <tr>
    <td width="9" height="16" valign="top"><img src="/images/user/game/course/g_box_left.gif" width="9" height="16"></td>
    <td width="702" valign="bottom" background="/images/user/game/course/bg_g_box.gif"><img src="/images/user/game/mystudy/<%=tem_subimgpath%>/blank.gif" width="20" height="10"></td>
    <td width="9" valign="top"><div align="right"><img src="/images/user/game/course/g_box_right.gif" width="9" height="16"></div></td>
  </tr>
  <tr>
    <td height="10" colspan="3"></td>
  </tr>
  <tr>
    <td colspan="3"><div align="right"><img src="/images/user/game/course/st_subbar_<%=v_tabnum%>.gif" width="467" height="29" usemap="#Map20"></div></td>
  </tr>
  <tr>
    <td height="10" colspan="3"><img src="/images/user/game/course/text_sub_<%=v_tabnum%>.gif" width="720" height="89"></td>
  </tr>
  <tr>
    <td height="15" colspan="3"></td>
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
        <td  height="33" align="center" valign="bottom" background="/images/user/game/course/ba_bg02.gif"><table width="677" height="23" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td width="120" align="center"  ><img src="/images/user/game/course/text_t_divide.gif" width="23" height="13"></td>
            <td width="1" valign="bottom"  ><img src="/images/user/game/course/vline02.gif"></td>
            <td align="center"  ><img src="/images/user/game/course/text_t_course_name.gif" width="33" height="13"></td>
            <td width="1" valign="bottom" ><img src="/images/user/game/course/vline02.gif"></td>
            <!--
            <td width="160" align="center" ><img src="/images/user/game/course/text_t_content.gif" width="34" height="13"></td>
            <td width="1" valign="bottom"  ><img src="/images/user/game/course/vline02.gif"></td>
            -->
            <td width="100" align="center"  ><img src="/images/user/game/course/text_t_skill.gif" width="22" height="13"></td>
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
    <table width="677" border="0" cellspacing="0" cellpadding="0">
<%
    //과정 리스트
    int i = 0;
    for(i = 0; i < list1.size(); i++)
    {
          DataBox dbox = (DataBox)list1.get(i);

          v_subj          = dbox.getString("d_subj");
          v_subjnm        = dbox.getString("d_subjnm");
          v_isonoff       = dbox.getString("d_isonoff");
          v_upperclass    = dbox.getString("d_upperclass");
          v_middleclass   = dbox.getString("d_middleclass");
          v_sclowerclass  = dbox.getString("d_lowerclass");
          v_usebook       = dbox.getString("d_usebook");
          v_isnew         = dbox.getString("d_isnew");
          v_ishit         = dbox.getString("d_ishit");
          v_isrecom       = dbox.getString("d_isrecom");
          v_uclassnm      = dbox.getString("d_uclassnm");
          v_mclassnm      = dbox.getString("d_mclassnm");
          v_sul_avg    = dbox.getDouble("d_sul_avg");

          if (v_usebook.equals("Y")) v_imgbook = "&nbsp;<img src='/images/user/button/btn_book.gif' align='absmiddle'>";
          else                       v_imgbook = "";
          //v_isnew = "Y";v_ishit="Y";v_isrecom="Y";
          if(v_isnew.equals("Y"))    v_imgNew = "&nbsp;<img src='/images/user/button/btn_snew.gif' align='absmiddle'>";
          else                       v_imgNew = "";
          if(v_ishit.equals("Y"))    v_imgHit = "&nbsp;<img src='/images/user/button/btn_shit.gif' align='absmiddle'>";
          else                       v_imgHit = "";
          if(v_isrecom.equals("Y"))  v_imgRecom = "&nbsp;<img src='/images/user/button/btn_srecom.gif' align='absmiddle'>";
          else                       v_imgRecom = "";

            v_imgStart = "";
            tmp_sul_avg = v_sul_avg * 10;

            for(int k = 0; k < 5; k++) {
                if(tmp_sul_avg > 10) {
                    v_imgStart += "<img src='/images/user/game/course/i_star_on.gif'>";
                } else {
                    if(tmp_sul_avg >= 5) {
                        v_imgStart += "<img src='/images/user/game/course/i_star_half.gif'>";
                    } else {
                        v_imgStart += "<img src='/images/user/game/course/i_star_off.gif'>";
                    }
                }

                tmp_sul_avg = tmp_sul_avg - 10;
            }

          if (i != 0) {
%>
      <tr >
        <td height="2" colspan="5" background="/images/user/game/course/bg_dot_line.gif"></td>
      </tr>
<%
          }
%>
      <tr >
        <td width="128" height="27" class="tbl_grc">
        <% if (v_sclowerclass.equals("001")) { //초급 %>
			<img src="/images/user/game/course/i_level01.gif" width="68" height="18">
		<% } else if (v_sclowerclass.equals("002")) { //중급 %>
			<img src="/images/user/game/course/i_level02.gif" width="68" height="18">
		<% } else if (v_sclowerclass.equals("003")) { //고급 %>
			<img src="/images/user/game/course/i_level03.gif" width="68" height="18">
		<% } %>

        </td>
        <td width="285" class="tbl_bleft"><%=v_subjnm%><%=v_imgNew%><%=v_imgHit%><%=v_imgRecom%></td>
        <!--td width="65" class="tbl_grc"><%//=v_sul_avg%></td-->
        <!--
        <td width="96">&nbsp;<%=v_imgStart%></td>
        -->
        <td width="100">&nbsp;</td>
        <td width="103"  class="tbl_grc"><a href="javascript:whenSubjInfo('<%=v_subj%>', '<%=v_tabnum%>')"><img src="/images/user/game/button/btn_more_view.gif" width="67" height="19"></a></td>
      </tr>
      <tr >
        <td height="2" colspan="4" background="/images/user/game/course/bg_dot_line.gif"></td>
      </tr>
<%
    }
%>
<%
          if (i == 0) {
%>
      <tr>
        <td class="tbl_grc" align="center" colspan="4">개설 과정이 없습니다.</td>
      </tr>
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
<map name="Map" id="Map20">
    <area shape="rect" coords="3,2,120,25"   href="#" onclick="whenSelect('N','design');" />
    <area shape="rect" coords="122,2,232,27" href="#" onclick="whenSelect('N','program');" />
    <area shape="rect" coords="234,2,340,28" href="#" onclick="whenSelect('N','graphic');" />
    <area shape="rect" coords="345,2,450,27" href="#" onclick="whenSelect('N','common');" />
</map>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
