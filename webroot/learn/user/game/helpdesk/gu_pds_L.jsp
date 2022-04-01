<%
//**********************************************************
//  1. 제      목: HelpDesk > 자료실
//  2. 프로그램명 : gu_pds_L.jsp
//  3. 개      요: 자료실 리스트화면
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
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

	int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    int v_tabseq   = box.getInt("p_tabseq");
    String  v_process  = box.getString("p_process");
    String v_userid = "";
    String v_name   = "";
    String v_indate = "";
    String v_title  = "";
	String v_class1 = "";
	String v_class2 = "";
    int v_seq       = 0;
    int v_levels    = 0;
    int v_cnt       = 0;
    int i           = 0;
    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;
    int v_upfilecnt = 0;
    int v_pageno    = box.getInt("p_pageno");
	if (v_pageno == 0) v_pageno = 1;
    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");
    String s_userid     = box.getSession("userid");
    String s_username   = box.getSession("name");

	String ss_gadmin   = (String)box.getSession("gadmin");

    ArrayList list      = (ArrayList)request.getAttribute("selectHomePageBoardList");
	box.put("leftmenu","04");                       // 메뉴 아이디 세팅
                  
%>

<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->


<script language="JavaScript" type="text/JavaScript">
<!--
//검색된 페이지로 이동
function selectList() {
    document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
    document.form1.p_pageno.value = "1";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//등록 페이지로 이동
function insertPage() {
    document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
    document.form1.p_process.value = "insertPage";
    document.form1.submit();
}

//상세화면보기 페이지로 이동
function select(seq, upfilecnt, userid) {
    document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
    document.form1.p_process.value = "select";
    document.form1.p_seq.value = seq;
    document.form1.p_upfilecnt.value = upfilecnt;
    document.form1.p_userid.value    = userid;
    document.form1.submit();
}
//페이지 이동
function go(index) {
	document.form1.p_pageno.value = index;
	document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
	document.form1.p_process.value = "";
	document.form1.submit();
}

//페이지 이동
function goPage(pageNum) {
	 document.form1.p_pageno.value = pageNum;
	 document.form1.action = "/servlet/controller.homepage.HomePageBoardServlet";
	 document.form1.p_process.value = "";
	 document.form1.submit();
}
//-->
</script>


<form name = "form1" enctype = "multipart/form-data" method = "post">
		<input type = "hidden" name = "p_process" value = "">
		<input type = "hidden" name = "p_tabseq"    value = "<%=v_tabseq %>">
		<input type = "hidden" name = "p_pageno"    value = "<%=v_pageno %>">
		<input type = "hidden" name = "p_seq"       value = "">
		<input type = "hidden" name = "p_upfilecnt" value = "">
		<input type = "hidden" name = "p_userid"    value = "">


<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/helpdesk/<%=tem_subimgpath%>/tit_pds.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > Help Desk > 자료실</td>
  </tr>
  <tr> 
    <td height="20"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><img src="/images/user/game/helpdesk/text_pds.gif"></td>
  </tr>
  <tr> 
    <td height="15">&nbsp;</td>
  </tr>
</table>
<!-- 과정조회 -->
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="3" height="27" valign="top" ><img src="/images/user/game/helpdesk/pdslist_left.gif"></td>
    <td width="712" height="27"align="center" valign="bottom" background="/images/user/game/helpdesk/pdslist_bg.gif"> 
      <table width="715" height="23" border="0" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="35" align="center"  ><img src="/images/user/game/helpdesk/text_num.gif" width="12" height="12"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/helpdesk/vline.gif"></td>
          <td width="334" align="center"  ><img src="/images/user/game/helpdesk/text_sujbj.gif" width="22" height="12"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/helpdesk/vline.gif"></td>
          <td width="129" align="center" ><img src="/images/user/game/helpdesk/text_writer_l.gif" width="39" height="12"></td>
          <td width="1" valign="bottom"  ><img src="/images/user/game/helpdesk/vline.gif"></td>
          <td width="107" align="center"  ><img src="/images/user/game/helpdesk/text_date.gif" width="39" height="12"></td>
          <td width="1" valign="bottom" ><img src="/images/user/game/helpdesk/vline.gif" width="1" height="8"></td>
          <td width="50" align="center"  ><img src="/images/user/game/helpdesk/text_addfile_l.gif" width="39" height="12"></td>
          <td width="1" align="center" valign="bottom"  ><img src="/images/user/game/helpdesk/vline.gif" width="1" height="8"></td>
          <td width="55" align="center"  ><img src="/images/user/game/helpdesk/text_sea.gif" width="18" height="12"></td>
        </tr>
      </table> </td>
    <td width="4" align="right" valign="top" background="/images/user/game/course/ba_bg.gif"><img src="/images/user/game/helpdesk/pdslist_right.gif" width="3" height="27" ></td>
  </tr>
  <tr> 
    <td height="5" colspan="3"></td>
  </tr>
  <tr >
    <td height="1" colspan="3" class="linecolor_helpdesk02"></td>
  </tr>
</table>
<table width="720" border="2" cellspacing="0" 
    cellpadding="3"  style="border-collapse:collapse;" bordercolor="#A9B2CD" frame="hsides" rules="rows" >
<%
			for(i = 0; i < list.size(); i++) {
				DataBox dbox = (DataBox)list.get(i);
				v_userid    = dbox.getString("d_userid");
				v_name      = dbox.getString("d_name");
				v_indate    = dbox.getString("d_indate");
				v_title     = dbox.getString("d_title");
				v_seq       = dbox.getInt("d_seq");
				v_cnt       = dbox.getInt("d_cnt");
				v_levels    = dbox.getInt("d_levels");
				v_upfilecnt = dbox.getInt("d_upfilecnt");
				v_dispnum   = dbox.getInt("d_dispnum");
				v_totalpage = dbox.getInt("d_totalpage");
				v_rowcount  = dbox.getInt("d_rowcount");

				if(i%2 == 0){   
					v_class1 = "board_text1";
					v_class2 = "board_text3";
				}else{
					v_class1 = "board_text2";
					v_class2 = "board_text4";
				}

					if (!v_searchtext.equals("")&&v_search.equals("title")) {
						v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
					} else if(!v_searchtext.equals("")&&v_search.equals("name")){
						v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
					}

%>
  <tr> 
    <td width="32" class="tbl_grc"><%=v_dispnum%></td>
    <td width="357" class="tbl_bleft">
		<a href="javascript:select('<%=v_seq%>','<%=v_upfilecnt%>','<%=v_userid%>')"><%=v_title%></a> 
					  <%if (FormatDate.getDate("yyyyMMdd").equals(FormatDate.getFormatDate(v_indate, "yyyyMMdd"))){%><img src="/images/user/game/button/btn_new.gif"><%}%></a> 
    </td>
    <td width="129" class="tbl_grc"> 운영자 (<%=v_userid%>)</td>
    <td width="110" class="tbl_grc"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
    <td width="49" class="tbl_grc">
		<% if (v_upfilecnt>0) {%> <img src="/images/user/game/service/i_add.gif" width="13" border="0">
		<% }else{ %> - <%}%></td>
    <td width="49" class="tbl_grc"><%=v_cnt%></td>
  </tr>
<%}%>

<%   if (i == 0 ) {                         %>
                      <tr  height="35"> 
                        <td colspan ="4" align=center valign=center> 등록된 내용이 없습니다. </td>
                      </tr>
<%   }                                     %>

</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="2" class="lcolor"></td>
  </tr>
  <tr> 
    <td height="2" class="linecolor_helpdesk01" colspan="2"></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
  <tr> 
    <td align="center" valign="bottom"> 
      <!-- page -->
		<table width="540" border="0" align="center" cellpadding="0" cellspacing="0">
		  <tr> 
			<td>
			<%= PageUtil.printPageListGame(v_totalpage, v_pageno, row) %>
			</td>
		  </tr>
		</table>  
	</td>
	<%if( ss_gadmin.equals("B2") || StringManager.substring(ss_gadmin,0,1).equals("A") ){%>
	<td align="right" valign="bottom"> 
		<a href="javascript:insertPage()"><img src="/images/user/game/button/btn_register.gif" border="0">
	</td>
	<%}%>
  </tr>
  <tr> 
    <td height="5" ></td>
  </tr>
  <tr> 
    <td height="2"class="linecolor_helpdesk01" colspan="2"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><table width="300" border="0" align="center" cellpadding="0" cellspacing="0">
        <tr> 
          <td width="100">
		    <select name="p_search" >
			  <option value='title'   <% if (v_search.equals("title")) out.print("selected"); %>>:: 제목</option>
			  <option value='content' <% if (v_search.equals("content")) out.print("selected"); %>> 내용 </option>
			</select></td>
          <td width="132"> <input name="p_searchtext" type="text" class="input"> 
          </td>
          <td width="100"><div align="center"><a href = "javascript:selectList();"><img src="/images/user/game/button/btn_j.gif"></a></div></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td height="5"></td>
  </tr>
</table>
</form>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
