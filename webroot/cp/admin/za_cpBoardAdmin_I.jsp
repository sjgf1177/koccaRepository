<%
//**********************************************************
//  1. 제      목: 운영자자료실
//  2. 프로그램명 : za_PdsAdmin_I.jsp
//  3. 개      요: 운영자자료실 등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2005. 1. 2
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    int  v_pageno      = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_tabseq     = box.getInt("p_tabseq");               // 게시판 ID
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수 

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");

%>
<html>
<head>
<title>▒▒▒▒▒ 현대기아자동차 통합교육관리시스템 ▒▒▒▒▒</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/hhi_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function insert() { 
        if (blankCheck(document.form1.p_title.value)) {
            alert("제목을 입력하세요!");
            document.form1.p_title.focus();
            return;
        }                
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }                     
        if (blankCheck(document.form1.p_content.value)) {
            alert("내용을 입력하세요!");
            document.form1.p_content.focus();
            return;
        }

        document.form1.p_search.value     = "";
        document.form1.p_searchtext.value = "";
        document.form1.action = "/servlet/controller.homepage.PdsAdminServlet";
        document.form1.p_process.value = "insert";                
        document.form1.submit();
    }

   function cancel() {
        document.form1.action = "/servlet/controller.homepage.PdsAdminServlet";
        document.form1.p_process.value = "";      
        document.form1.submit();    
   }

//-->
</SCRIPT>


</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" enctype = "multipart/form-data">
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_tabseq"     value = "<%=v_tabseq%>">
    <input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="768">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="../../images/admin/cp/cp_tit_board.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->

      <br>
        <!----------------- FORM 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>작성자</strong></td>
            <td height="25" class="table_02_2"><%=s_username%></td>
          </tr>
          <tr > 
            <td height="25" class="table_title"><strong>등록일</strong></td>
            <td height="25" class="table_02_2"><%= FormatDate.getDate("yyyy/MM/dd") %></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>제목</strong></td>
            <td height="25" class="table_02_2"><input type="text" name="p_title" size = 100 class="input"></td>
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>내용</strong></td>
            <td height="25" class="table_02_2"><textarea name="p_content" cols="110" rows="10" ></textarea>
            </td>
          </tr>
          <tr valign="middle"> 
            <td width="15%" class="table_title"><strong>파일첨부</strong></td>
            <td height="25" class="table_02_2"> 
              <input type="FILE" name="p_file1" size="100" class="input"><br>
              <input type="FILE" name="p_file2" size="100" class="input"><br>
              <input type="FILE" name="p_file3" size="100" class="input"><br>
              <input type="FILE" name="p_file4" size="100" class="input"><br>
              <input type="FILE" name="p_file5" size="100" class="input"><br>
            </td>
          </tr>
        </table>
        <!----------------- FORM 끝 ----------------->
        <!----------------- total 시작 ----------------->
        <br>
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="right" class="ms"><a href="javascript:insert()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
            <td width="1%" align="center" class="ms">&nbsp;</td>
            <td align="left" class="ms"><a href="javascript:cancel()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
          </tr>
        </table>
        <br>
        <!----------------- total 끝 ----------------->
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %> 

</form>
</body>
</html>
