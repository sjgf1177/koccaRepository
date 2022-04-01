<%
//**********************************************************
//  1. 제      목: 홈페이지 Q&A 상세화면
//  2. 프로그램명: zu_HomePageQna_R.jsp
//  3. 개      요: 홈페이지 Q&A 상세화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이연정 2005. 6. 29
//  7. 수      정: 이연정 2005. 6. 29
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

DataBox dbox        = (DataBox)request.getAttribute("selectQna");

int v_commentseq    = dbox.getInt("d_commentseq");

v_inuserid          = dbox.getString("d_inuserid");
v_name              = dbox.getString("d_name");
v_types             = dbox.getString("d_types");
v_indate            = dbox.getString("d_indate");
v_title             = dbox.getString("d_title");
v_seq               = dbox.getInt("d_seq");
v_contents          = dbox.getString("d_contents");
v_realfileVector    = (Vector)dbox.getObject("d_realfile");
v_savefileVector    = (Vector)dbox.getObject("d_savefile");
String  v_categorycd= dbox.getString("d_categorycd");
String s_userid     = box.getSession("userid");
box.put("leftmenu","02");                       // 메뉴 아이디 세팅

int    v_filecount = v_realfileVector.size();

 /*if (v_select.equals("title") ) {
	v_title    = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
}else if (v_select.equals("contents")){
	v_contents = StringManager.replace(v_contents, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
}else if (v_select.equals("name")){
	v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 

}*/


%>
<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topSupport.jsp"%>
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
		  <!-- center start -->
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; Q&amp;A</td>
              </tr>
              <tr> 
                <td align="center" valign="top">
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/support/<%=tem_subimgpath%>/tit_qna.jpg" ></td>
                    </tr>
                  </table>
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
                  
        <table width="675" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
          </tr>
          <tr> 
            <td width="94"><img src="/images/user/support/text_title.gif"></td>
            <td colspan="5" class="tbl_btit"><%= v_title %></td>
          </tr>
          <tr> 
            <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
          </tr>
          <tr> 
            <td><img src="/images/user/support/text_pe.gif"></td>
            <td width="304" class="tbl_gleft"><%=v_name%><!--(<%= v_inuserid %>)--></td>
            <td width="96"><img src="/images/user/support/text_day.gif"></td>
            <td width="182" class="tbl_gleft"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %> </td>
          </tr>
          <tr> 
            <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
          </tr>
          <!---------파일첨부여부 ------------>
          <tr> 
            <td width="94" valign="top" background="/images/user/support/text_bg_head.gif"><img src="/images/user/support/text_top_head.gif"> 
            </td>
            <td colspan="3" rowspan="3" class="tbl_bdata"> <%		
				if( v_realfileVector != null ) { 
					for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라

						String v_realfile = (String)v_realfileVector.elementAt(i);
						String v_savefile = (String)v_savefileVector.elementAt(i);  	

						if(v_realfile != null && !v_realfile.equals("")) {  %> <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'> 
              <%= v_realfile%></a><br>
              <input type="hidden" name="p_savefile"  value=<%=v_savefile%>>
              <%		}   %> <%			}
			  }
		%> </td>
          </tr>
          <tr>
            <td align="center" valign="top" background="/images/user/support/text_bg_head.gif"><img src="/images/user/support/text_addfile_a.gif"></td>
          </tr>
          <tr>
            <td valign="bottom" background="/images/user/support/text_bg_head.gif"><img src="/images/user/support/text_bo_head.gif"></td>
          </tr>
          <!---------파일첨부여부 끝 ------------>
          <tr> 
            <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
          </tr>
          <tr> 
            <td colspan="6" class="tbl_contents"><%=v_contents%></td>
          </tr>
          <tr bgcolor="B8D5E5"> 
            <td height="5" colspan="6"><img src="/images/user/support/sup_tb_bo.gif" width="675" height="5"></td>
          </tr>
          <tr> 
            <td height="10" colspan="6"></td>
          </tr>
        </table>
				  </form>
				  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="418" align="right">&nbsp; </td>
                      <td width="257" align="right">
                      <!--
					    <a href="javascript:replyPage()" target="_self" onfocus=this.blur()><img src="/images/user/button/btn_answer.gif" border="0"></a> 
					  -->
					  <%if(v_inuserid.equals(box.getSession("userid"))){%>
                        <a href="javascript:updatePage()" target="_self" onfocus=this.blur()><img src="/images/user/button/btn_mod.gif" border="0"></a>
                        <a href="javascript:deleteAction()" target="_self" onfocus=this.blur()><img src="/images/user/button/btn_del.gif" border="0"></a>
                      <%}%>
                      <a href="javascript:selectList()" target="_self" onfocus=this.blur()><img src="/images/user/button/btn_list.gif" border="0"></a></td>
                    </tr>
                  </table>
				  <%@include file="/learn/user/homepage/zu_HomePageQna_C.jsp"%>
                </td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
            </table>
<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->
