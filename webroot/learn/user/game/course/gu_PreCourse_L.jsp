<%
//**********************************************************
//  1. 제      목: 개설예정과정게시판
//  2. 프로그램명 : zu_PreCourse_L.jsp
//  3. 개      요: 개설예정과정게시판 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    box.put("leftmenu","05");                       // 메뉴 아이디 세팅
    int v_seq = 0;
    int v_cnt = 0;
    int i     = 0;

    String v_userid = "";
    String v_name   = "";
    String v_indate = "";
    String v_title  = "";
    String v_redate = "";

    int v_dispnum   = 0; // 총게시물수
    int v_totalpage = 0;
    int v_rowcount  = 0;

    int v_pageno    = box.getInt("p_pageno");

    String v_searchtext = box.getString("p_searchtext");
    //String v_search     = box.getString("p_search");

    String s_userid   = box.getSession("userid");
    String s_username = box.getSession("name");
    
    ArrayList list = (ArrayList)request.getAttribute("list");
   
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topCourse.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<SCRIPT LANGUAGE="JavaScript">
<!--
function go(index) {
     document.form1.p_pageno.value = index;
    document.form1.target = "_self";
     document.form1.action = "/servlet/controller.course.HomePagePreCourseServlet";
     document.form1.p_process.value = "selectList";
     document.form1.submit();
}
function goPage(pageNum) {
     document.form1.p_pageno.value = pageNum;
    document.form1.target = "_self";
     document.form1.action = "/servlet/controller.course.HomePagePreCourseServlet";
     document.form1.p_process.value = "selectList";
     document.form1.submit();
}

    //리스트 조회
    function select() {
        document.form1.action = "/servlet/controller.course.HomePagePreCourseServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }
    //상세보기
    function selectView(seq,userid) {
        document.form1.action = "/servlet/controller.course.HomePagePreCourseServlet";
        document.form1.p_process.value = "viewPage";
        document.form1.p_seq.value       = seq;
        document.form1.p_userid.value    = userid;
        document.form1.submit();
    }

//-->
</SCRIPT>



<form name="form1" method="post">
    <input type = "hidden" name = "p_process"   value = "">
    <input type = "hidden" name = "p_pageno"    value = "<%=v_pageno%>">
    <input type = "hidden" name = "p_rowseq"    value = "">
    <input type = "hidden" name = "p_seq"       value = "">
    <input type = "hidden" name = "p_userid"    value = "">
<!-- title 시작 -->
<table align="center" width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="35" align="right"  background="/images/user/game/course/<%=tem_subimgpath%>/tit_opencourse.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > 과정안내 > 개설예정과정</td>
  </tr>
  <tr> 
    <td height="20"></td>
  </tr>
</table>
<!-- title 끝 -->
<!-- 과정조회 -->
<table align="center" width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><table width="300" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="68"><img src="/images/user/game/course/open_courname.gif"></td>
          <td width="132">
              <input name="p_searchtext" type="text" class="input" value='<%=v_searchtext%>'>
           </td>
          <td width="100"><a href="javascript:select()"><img src="/images/user/game/button/btn_j.gif"></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
</table>
<table align="center" width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="4" height="27" valign="top" background="/images/user/game/course/ba_bg.gif"><img src="/images/user/game/course/ba_f.gif"></td>
    <td width="712" align="center" valign="bottom" background="/images/user/game/course/ba_bg.gif"> 
      <table width="710" height="23" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="35" align="center"  ><img src="/images/user/game/course/text_t_num.gif"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/course/vline.gif"></td>
          <td width="347" align="center"  ><img src="/images/user/game/course/text_t_openco.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/course/vline.gif"></td>
          <td width="129" align="center" ><img src="/images/user/game/course/text_t_openoday.gif"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/course/vline.gif"></td>
          <td width="107" align="center"  ><img src="/images/user/game/course/text_t_openday.gif"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/course/vline.gif"></td>
          <td width="50" align="center"  ><img src="/images/user/game/course/text_t_view.gif"></td>
        </tr>
      </table></td>
    <td width="4" align="right" valign="top" background="/images/user/game/course/ba_bg.gif"><img src="/images/user/game/course/ba_tail.gif" ></td>
  </tr>
  <tr> 
    <td height="5" colspan="3"></td>
  </tr>
  <tr class="linecolor_board">
    <td height="1" colspan="3"></td>
  </tr>
</table>
<table align="center" width="720" border="2" cellspacing="0" 
    cellpadding="3"  style="border-collapse:collapse;" bordercolor="#ECE1D0" frame="hsides" rules="rows" >

<%
    for(i = 0; i < list.size(); i++) {
        DataBox dbox = (DataBox)list.get(i);
        v_userid    = dbox.getString("d_userid");
        v_name      = dbox.getString("d_name");
        v_indate    = dbox.getString("d_indate");
        v_title     = dbox.getString("d_title");
        v_seq       = dbox.getInt("d_seq");
        v_cnt       = dbox.getInt("d_cnt");
        v_dispnum   = dbox.getInt("d_dispnum");
        v_totalpage = dbox.getInt("d_totalpage");
        v_rowcount  = dbox.getInt("d_rowcount");
        v_redate    = dbox.getString("d_redate");

            // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
            if (!v_searchtext.equals("")) {
                    v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }
%>
  <tr> 
    <td width="32" class="tbl_grc"><%=v_dispnum%></td>
    <td width="357" class="tbl_bleft"><a href="javascript:selectView('<%=v_seq%>','<%=v_userid%>')"><%=v_title%></a>
        <% if(FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){          %>
                 &nbsp;&nbsp;<img src = "/images/user/course/btn_new.gif" align = "absmiddle" border = "0">
        <% }                                                                                                   %> 
    </td>
    <td width="128" class="tbl_grc"><b><%=FormatDate.getFormatDate(v_redate, "yyyy-MM-dd")%></b></td>
    <td width="110" class="tbl_grc"><%=FormatDate.getFormatDate(v_indate, "yyyy-MM-dd")%></td>
    <td width="49" class="tbl_grc"><%=v_cnt%></td>
  </tr>
<%
    }
%>
<%   if (i == 0 ) {                         %>
                      <tr> 
                        <td class="board_text1" colspan ='5' align='center'>등록된 내용이 없습니다.</td>
                      </tr>
<%   }   %>
</table>
<table align="center" width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="2" class="lcolor"></td>
  </tr>
  <tr> 
    <td height="1" class="linecolor_board"></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
  <tr> 
    <td align="center" valign="bottom"> 
      <!-- page -->
      <table width="300" border="0" cellspacing="0" cellpadding="0">
        <!--tr> 
          <td width="75" align="right" valign="middle" style="MARGIN: 0px 3px 2px" ><img src="/images/user/game/button/btn_pre2.gif"></td>
          <td width="61" align="right" valign="middle" style="MARGIN: 0px 3px 2px" ><img src="/images/user/game/button/btn_pre.gif"></td>
          <td width="28" align="center"> <a href="#">1</a> <a href="#">2</a> <a href="#"> 
            3</a> </td>
          <td width="61" valign="middle"><img src="/images/user/game/button/btn_next.gif" ></td>
          <td width="75" valign="middle"><img src="/images/user/game/button/btn_next2.gif" ></td>
        </tr-->
        <tr>
          <td align="right" valign="absmiddle">
             <%= PageUtil.printPageListGame(v_totalpage, v_pageno, 10) %>
          </td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td height="5" ></td>
  </tr>
  <tr> 
    <td height="2"class="linecolor_board"></td>
  </tr>
</table>
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
