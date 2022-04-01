<%
//**********************************************************
//  1. 제      목: 운영자자료실
//  2. 프로그램명 : za_PdsAdmin_U.jsp
//  3. 개      요: 운영자자료실 수정
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
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";

    Vector v_realfileVector = null;          //      원파일명 배열
    Vector v_savefileVector = null;          //      서버에 저장되있는 파일명 배열
    Vector v_fileseqVector  = null;          //      저장된 파일번호 배열


    DataBox dbox = (DataBox)request.getAttribute("selectPds");
    if (dbox != null ) {
        v_seq         = dbox.getInt("d_seq");
        v_userid      = dbox.getString("d_userid");
        v_name        = dbox.getString("d_name");
        v_title       = dbox.getString("d_title");
        v_content     = dbox.getString("d_content");
        v_cnt         = dbox.getInt("d_cnt");
        v_indate      = dbox.getString("d_indate");
        v_realfileVector = (Vector)dbox.getObject("d_realfile");
        v_savefileVector = (Vector)dbox.getObject("d_savefile");
        v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");
        v_title = StringManager.replace(v_title, "`", "'");
    }

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
        document.form1.p_process.value = "update";
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
    <input type = "hidden" name = "p_seq"        value = "<%= v_seq %>">
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
            <td height="25" class="table_02_2"><%=v_name%></td>
          </tr>
          <tr >
            <td height="25" class="table_title"><strong>등록일</strong></td>
            <td height="25" class="table_02_2"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>제목</strong></td>
            <td height="25" class="table_02_2"><input type="text" name="p_title" size = 100 class="input" value = `<%= v_title %>`></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>내용</strong></td>
            <td height="25" class="table_02_2"><textarea name="p_content" cols="110" rows="10" ><%= v_content%></textarea>
            </td>
          </tr>
          <tr> 
            <td height="25" class="table_title"><b>이전파일</b></td>
            <td class="table_02_2">
            
<% 
		if (v_realfileVector !=null ) {
			for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
				String v_realfile = (String)v_realfileVector.elementAt(i);
				String v_savefile = (String)v_savefileVector.elementAt(i);								
				String v_fileseq  = (String)v_fileseqVector.elementAt(i);								
				if(v_realfile != null && !v_realfile.equals("")) { 
%>
                &nbsp;<%= v_realfile%>
                &nbsp;<input type = "checkbox"  name = "p_savefile" value = "<%= v_savefile%>"> (삭제시 체크)
                <br>
               <input type = "hidden" name = "p_realfile"  value = "<%= v_realfile%>">
<%				}  
			}
		}// end of if v_realfileVector != null 
%>            </td>
          </tr>          
          <tr>
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
        <br>
        <!----------------- FORM 끝 ----------------->

        <!----------------- total 시작 ----------------->

        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="right" class="ms"><a href="javascript:insert()"><img src="/images/admin/system/save1_butt.gif" width="37" height="18" border="0"></a></td>
            <td width="1%" align="center" class="ms">&nbsp;</td>
            <td align="left" class="ms"><a href="javascript:cancel()"><img src="/images/admin/system/cancel1_butt.gif" width="37" height="18" border="0"></a></td>
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
