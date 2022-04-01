<%
//**********************************************************
//  1. 제      목: 개설예정과정게시판 
//  2. 프로그램명 : za_PreCourse_R.jsp
//  3. 개      요: 개설예정과정게시판 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.13
//  7. 수      정: 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />


<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
	box.put("leftmenu","05");                       // 메뉴 아이디 세팅
    String  v_process    = box.getString("p_process");
    int     v_seq        = box.getInt   ("p_seq");
    
    int     v_pageno     = box.getInt("p_pageno");
    int     v_rowseq     = box.getInt("p_rowseq");

    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");

    DataBox dbox = (DataBox)request.getAttribute("list");

    String  v_title      = dbox.getString("d_title");
	String  v_name		 = dbox.getString("d_name");
	String  v_indate	 = dbox.getString("d_indate");
    String  v_redate     = dbox.getString("d_redate");
	String  v_content	 = dbox.getString("d_content");

%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT language="javascript">
<!--

//리스트로 이동
function wf_listOK() {
    document.form1.action = "/servlet/controller.course.HomePagePreCourseServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//-->
</SCRIPT>

<form name = "form1"     enctype = "multipart/form-data"   method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_seq"		  value = "<%=v_seq%>">
<input type = "hidden" name = "p_rowseq"	  value = "<%=v_rowseq%>">

<!-- title -->
<table align="center" width="720" border="0" cellspacing="0" cellpadding="0" >
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/course/<%=tem_subimgpath%>/tit_opencourse.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 과정안내 > 개설예정과정</td>
  </tr>
  <tr> 
    <td height="20"></td>
  </tr>
</table>
<table align="center" width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="81" height="2" class="linecolor_board2"></td>
	<td width="639" height="2" class="linecolor_board"></td>
  </tr>
</table>
<table align="center" width="720" border="2" cellspacing="0" 
    cellpadding="0"  style="border-collapse:collapse;" bordercolor="#ECE1D0" frame="hsides" rules="rows" >
  <tr> 
    <td width="77" class="tbl_grc"><img src="/images/user/game/course/text_t.gif"></td>
    <td width="8" valign="bottom" ><img src="/images/user/game/course/vline.gif"></td>
    <td width="625" class="font_botit"><%=v_title%></td>
  </tr>
  <tr> 
    <td  class="tbl_grc"><img src="/images/user/game/course/text_day.gif"></td>
    <td  valign="bottom"><img src="/images/user/game/course/vline.gif"></td>
    <td bgcolor="F8F7EF" class="tbl_bleft"><table width="611" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="611" bgcolor="F8F7EF"><%=FormatDate.getFormatDate(v_indate, "yyyy-MM-dd")%></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td class="tbl_grc"><img src="/images/user/game/course/text_oday.gif"></td>
    <td valign="bottom"><img src="/images/user/game/course/vline.gif"></td> 
    
    <td bgcolor="F8F7EF" class="tbl_bleft"><table width="611" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="611" bgcolor="F8F7EF"><%=FormatDate.getFormatDate(v_redate, "yyyy-MM-dd")%></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td class="tbl_grc"><img src="/images/user/game/course/text_con.gif"></td>
    <td valign="bottom"><img src="/images/user/game/course/vline.gif"></td>
    <td class="tbl_contents"><%=v_content%></td>
  </tr>
</table>
<table align="center" width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="2" colspan="2" class="lcolor"></td>
  </tr>
  <tr> 
    <td height="1" colspan="2" class="linecolor_board"></td>
  </tr>
  <tr> 
    <td height="5" colspan="2"></td>
  </tr>
  <tr> 
    <td align="center" valign="bottom">&nbsp;</td>
    <td align="right" valign="bottom"><a href="javascript:wf_listOK()"><img src="/images/user/game/button/btn_list.gif" border="0"></a></td>
  </tr>
  <tr> 
    <td height="5" colspan="2" ></td>
  </tr>
  <tr> 
    <td height="2" colspan="2"class="linecolor_board"></td>
  </tr>
</table>
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
t