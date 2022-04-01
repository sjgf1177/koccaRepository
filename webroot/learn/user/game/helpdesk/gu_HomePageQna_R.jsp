<%
//**********************************************************
//  1. 제      목: HelpDesk > QNA
//  2. 프로그램명 : gu_HomePageQna_R.jsp
//  3. 개      요: QNA 상세보기
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이나연 05.12.26
//  7. 수      정: 
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_title = "", v_contents = "", v_inuserid = "", v_indate = "" , v_types = "";
Vector v_realfileVector = null;
Vector v_savefileVector = null;
int    v_seq        = box.getInt("p_seq");
int    v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수
int    v_pageno     = box.getInt("p_pageno");
String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
String v_select     = box.getString("p_select"); 
String v_name = "";
int v_cnt = 0;

DataBox dbox        = (DataBox)request.getAttribute("selectQna");
System.out.println("dbox "+dbox);
int v_commentseq    = dbox.getInt("d_commentseq");

v_inuserid          = dbox.getString("d_inuserid");
v_name              = dbox.getString("d_name");
v_types             = dbox.getString("d_types");
v_indate            = dbox.getString("d_indate");
v_title             = dbox.getString("d_title");
v_seq               = dbox.getInt("d_seq");
v_cnt               = dbox.getInt("d_cnt");
v_contents          = dbox.getString("d_contents");
v_realfileVector    = (Vector)dbox.getObject("d_realfile");
v_savefileVector    = (Vector)dbox.getObject("d_savefile");
String  v_categorycd= dbox.getString("d_categorycd");
String s_userid     = box.getSession("userid");
box.put("leftmenu","02");                       // 메뉴 아이디 세팅

int    v_filecount = v_realfileVector.size();

%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/game/include/topHelpdesk.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->

<script language="JavaScript" type="text/JavaScript">
<!--

//리스트페이지로 이동
function selectList() {
   	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value = "";     
	document.form1.submit();	
}      

//수정페이지로 이동
function updatePage() {
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value = "updatePage";
	document.form1.submit();
}

//답변 등록 페이지로 이동
function replyPage() {
	document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form1.p_process.value = "replyPage";
	document.form1.submit();
}   

//꼬릿말 등록
function insertcomment() {
	if(document.form2.commentqna.value==""){
		alert("꼬릿말을 등록하세요");
		return;
	}
	document.form2.p_categorycd.value = document.form1.p_categorycd.value; // 카테고리
	document.form2.action = "/servlet/controller.homepage.HomePageQNAServlet";
	document.form2.p_process.value = "commentInsertPage";
	document.form2.submit();
}


//뷰 화면 삭제
function deleteAction() {
	if (confirm("정말로 삭제하시겠습니까?")) {
		
		document.form1.action = "/servlet/controller.homepage.HomePageQNAServlet";
		document.form1.p_process.value = "delete";
		document.form1.submit();
	}
	else {
		return;
	}
}

//꼬릿말 삭제
function deleteComment(v_commentseq) {
	if (confirm("정말로 삭제하시겠습니까?")) {
		document.form2.p_commentseq.value = v_commentseq;
		document.form2.action = "/servlet/controller.homepage.HomePageQNAServlet";
		document.form2.p_process.value = "deleteComment";
		document.form2.submit();
	}
	else {
		return;
	}
}


//-->
</script>
                  <!-- qna table -->
<form name = "form1" method = "post">
		<input type = "hidden" name = "p_seq"         value = "<%= v_seq %>">
		<input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
		<input type = "hidden" name = "p_select"      value = "<%= v_select %>">
		<input type = "hidden" name = "p_pageno"      value = "<%= v_pageno %>">
		<input type = "hidden" name = "p_process"     value = "">
		<input type = "hidden" name = "p_userid"      value = "<%= v_inuserid %>">
		<input type = "hidden" name = "p_types"       value = "<%=v_types%>">
		<input type = "hidden" name = "p_categorycd"  value = "<%=v_categorycd%>">
		<input type = "hidden" name = "p_upfilecnt"   value = "<%= v_upfilecnt %>">
                  
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="720" height="35" align="right"  background="/images/user/game/helpdesk/<%=tem_subimgpath%>/tit_qna.gif" class="location" ><img src="/images/user/game/common/location_bl.gif"> 
      HOME > Help Desk > Q&amp;A</td>
  </tr>
  <tr> 
    <td height="20"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="/images/user/game/helpdesk/text_qna.gif"></td>
  </tr>
  <tr>
    <td height="15">&nbsp;</td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="81" height="2" class="linecolor_helpdesk01"></td>
	<td width="639" height="2" class="linecolor_helpdesk02"></td>
  </tr>
</table>
<table width="720" border="2" cellspacing="0" 
    cellpadding="0"  style="border-collapse:collapse;" bordercolor="#A9B2CD" frame="hsides" rules="rows" >
  <tr> 
    <td width="150" class="tbl_grc"><img src="/images/user/game/helpdesk/text_subject.gif"></td>
    <td width="8" valign="bottom" ><img src="/images/user/game/helpdesk/vline.gif"></td>
    <td width="625" colspan="9" class="tbl_bleft"><strong><%= v_title %></strong></td>
  </tr>
  <tr> 
    <td  class="tbl_grc"><img src="/images/user/game/helpdesk/text_writer.gif"></td>
    <td  valign="bottom"><img src="/images/user/game/helpdesk/vline.gif" ></td>
    <td width="212" class="tbl_bleft"><%=v_name%></td>
    <td  valign="bottom"><img src="/images/user/game/helpdesk/vline.gif" ></td>
    <td width="80"  class="tbl_grc"><img src="/images/user/game/helpdesk/text_date_list.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/helpdesk/vline.gif" width="1" height="8"></td>
    <td width="116" class="tbl_bleft"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/helpdesk/vline.gif" width="1" height="8"></td>
    <td width="80" class="tbl_grc"><img src="/images/user/game/helpdesk/text_search_view.gif"></td>
    <td width="1" valign="bottom" ><img src="/images/user/game/helpdesk/vline.gif" width="1" height="8"></td>

    <td width="149" class="tbl_bleft"><%=v_cnt%></td>
  </tr>
  <tr> 
    <td class="tbl_grc"><img src="/images/user/game/helpdesk/text_page.gif"></td>
    <td valign="bottom"><img src="/images/user/game/helpdesk/vline.gif"></td>
    <td colspan="9" class="tbl_contents"><%=v_contents%></td>
  </tr>
  <tr> 
    <td class="tbl_grc"><img src="/images/user/game/helpdesk/text_addfile.gif"></td>
    <td valign="bottom"><img src="/images/user/game/helpdesk/vline.gif"></td>
    <td colspan="9" bgcolor="F8F7EF" class="tbl_bleft"><table width="611" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="611" bgcolor="EEF0F5">
		  <%		
				if( v_realfileVector != null ) { 
					for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라

						String v_realfile = (String)v_realfileVector.elementAt(i);
						String v_savefile = (String)v_savefileVector.elementAt(i);  	

						if(v_realfile != null && !v_realfile.equals("")) {  %> 
			<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'> 
              <%= v_realfile%></a><br>
              <input type="hidden" name="p_savefile"  value=<%=v_savefile%>>
              <%	}else{
			  %>
			첨부된 파일이 없습니다.
			<%			}
					}
				}%>
		  </td>
        </tr>
      </table></td>
  </tr>
</table>
  <table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="81" height="2" class="linecolor_helpdesk01"></td>
	<td width="639" height="2" class="linecolor_helpdesk01"></td>
  </tr>
</table>
<table width="720" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="2" colspan="2" class="lcolor"></td>
  </tr>
  <tr> 
    <td height="1" colspan="2"></td>
  </tr>
  <tr> 
    <td height="5" colspan="2"></td>
  </tr>
  <tr> 
    <td align="center" valign="bottom">&nbsp;</td>
    <td align="right" valign="bottom">
	  <table width="208" border="0" cellspacing="0" cellpadding="0">
        <tr align="right">
          <!--td width="52"><a href="#"><img src="/images/user/game/button/btn_answer.gif" border="0"></a></td-->
		  <td width="52">&nbsp;</td>
		  <%if(v_inuserid.equals(box.getSession("userid"))){%>
          <td width="52"><a href="javascript:updatePage()"><img src="/images/user/game/button/btn_mod.gif" border="0"></a></td>
          <td width="52"><a href="javascript:deleteAction()"><img src="/images/user/game/button/btn_del.gif" border="0"></a></td>
		   <%}%>
          <td><a href="javascript:selectList()"><img src="/images/user/game/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>
</form>
	  <%@include file="/learn/user/game/helpdesk/gu_HomePageQna_C.jsp"%>

	  </td>
  </tr>
  <tr> 
    <td height="5" colspan="2" ></td>
  </tr>
  <tr> 
    <td height="2" colspan="2"></td>
  </tr>
</table>


<!---------- copyright start ------------------->
<%@ include file="/learn/user/game/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
