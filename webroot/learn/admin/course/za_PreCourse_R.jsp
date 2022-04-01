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
    String  v_process    = box.getString("p_process");
    int     v_seq        = box.getInt   ("p_seq");
    
    String  v_searchtext = box.getString("p_searchtext");
    String  v_select     = box.getString("p_select");
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


<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script src="/script/cafe_select.js" language="javascript"></script>
<SCRIPT language="javascript">
<!--

//리스트로 이동
function wf_listOK() {
    document.form1.action = "/servlet/controller.course.HomePagePreCourseAdminServlet";
    document.form1.p_process.value = "selectList";
    document.form1.submit();
}

//글삭제하기
function uf_deleteOK() {
    document.form1.action = "/servlet/controller.course.HomePagePreCourseAdminServlet";
    document.form1.p_process.value = "deleteData";
    document.form1.submit();
}


//수정페이지로이동하기
function uf_updateOK() {
    document.form1.action = "/servlet/controller.course.HomePagePreCourseAdminServlet";
    document.form1.p_process.value = "updatePage";
    document.form1.submit();
}
//-->
</SCRIPT>
</head>
<body topmargin=0 leftmargin=0 >
<form name = "form1"     enctype = "multipart/form-data"   method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_pageno"      value = "<%=v_pageno%>">
<input type = "hidden" name = "p_searchtext"  value = "<%=v_searchtext%>">
<input type = "hidden" name = "p_select"	  value = "<%=v_select%>">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_seq"		  value = "<%=v_seq%>">
<input type = "hidden" name = "p_rowseq"	  value = "<%=v_rowseq%>">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
 <tr>
  <td align="center"> 
   <!----------------- title 시작 ----------------->
   <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
    <tr> 
     <td><img src="/images/admin/homepage/tit_precourse.gif"></td>
     <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
    </tr>
   </table>
   <br>

   <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
    <tr> 
      <td width="96" class="table_title"  width="10%">작성자</td>
      <td class="table_02_2"  width="40%"><%=dbox.getString("d_name")%></td>
	  <td class="table_title" width="10%">조회수</td>
      <td class="table_02_2"  width="40%"><%=dbox.getInt("d_cnt")%></td>
    </tr>
    <tr> 
      <td class="table_title">작성일</td>
      <td class="table_02_2" colspan="3"><%=FormatDate.getFormatDate(dbox.getString("d_indate"), "yyyy/MM/dd")%></td>
      
    </tr>
    <tr> 
      <td class="table_title" >제목</td>
      <td colspan="3" valign="top" class="table_02_2" ><%=dbox.getString("d_title")%></td>
    </tr>
	<tr> 
      <td class="table_title" >개설예정일</td>
      <td colspan="3" valign="top" class="table_02_2" ><%=FormatDate.getFormatDate(dbox.getString("d_redate"),"yyyy/MM/dd")%></td>
    </tr>
    <tr> 
      <td class="table_title" >내용</td>
      <td colspan="3" valign="top" class="table_02_2" ><%=v_content%></td>
    </tr>
  </table>
  <br>
    <table width="970" border="0" cellspacing="0" cellpadding="0">
     <tr align="center"> 
      <td>
       <a href="javascript:uf_updateOK();" onfocus=this.blur()><img src="/images/admin/button/btn_modify.gif" border = "0">
       <a href="javascript:uf_deleteOK();" onfocus=this.blur()><img src="/images/admin/button/btn_del.gif" border = "0">

       <a href="javascript:history.back();" onfocus=this.blur()><img src="/images/admin/button/btn_cancel.gif" border = "0">
       </td>
     </tr>
    </table></td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
