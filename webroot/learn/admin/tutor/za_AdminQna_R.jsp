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
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box 		= (RequestBox)request.getAttribute("requestbox");
    String  v_process  	= box.getString("p_process");
    int  v_pageno      	= box.getInt("p_pageno");
    int     v_pagesize = box.getInt("p_pagesize");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_tabseq     = box.getInt("p_tabseq");               // 게시판 ID
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int			v_refseq		 = box.getInt("p_refseq");							 // 게시물 상위 ID
    int			v_levels		 = box.getInt("p_levels");							 // 게시물 레벨
    int			v_position	 = box.getInt("p_position");						 // 게시물 위치
    int     v_orgseq     = v_seq;

    int i           = 0;
    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;

    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";

    DataBox dbox = (DataBox)request.getAttribute("selectQna");
    if (dbox != null ) {
        v_seq         	 = dbox.getInt("d_seq");
        v_userid      	 = dbox.getString("d_userid");
        v_name        	 = dbox.getString("d_name");
        v_title       	 = dbox.getString("d_title");
        v_content     	 = dbox.getString("d_content");
        v_cnt         	 = dbox.getInt("d_cnt");
        v_indate      	 = dbox.getString("d_indate");

        // 검색어가 있었다면 해당 부분을 해당색으로 부각시킨다.
        if (!v_searchtext.equals("")) {
            if (v_search.equals("name")) {
                v_name = StringManager.replace(v_name, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            } else if (v_search.equals("title")) {
                v_title = StringManager.replace(v_title, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }  else if (v_search.equals("content")) {
                v_content = StringManager.replace(v_content, v_searchtext, "<font color=\"red\">" + v_searchtext + "</font>"); 
            }
        }
    }
    
    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");
    String s_gadmin    = box.getSession("gadmin");

    // 리스트
    //ArrayList list = (ArrayList)request.getAttribute("selectQnaList");
%>
<html>
<head>
<title> 학습관리지원시스템 </title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/hhi_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	// 수정하기
    function modify_adminqna() {
        document.form1.action = "/servlet/controller.tutor.AdminQnaServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }
	//삭제하기
    function delete_adminqna() {
        if (confirm("정말로 삭제하시겠습니까?")) {
            document.form1.action = "/servlet/controller.tutor.AdminQnaServlet";
            document.form1.p_process.value = "delete";
            document.form1.submit();
        }
        else {
            return;
        }
    }
	
	//답변하기
	function reply_adminqna() {
		document.form1.action = "/servlet/controller.tutor.AdminQnaServlet";
		document.form1.p_process.value = "replyPage";
		document.form1.submit();
	}
	//목록으로
    function list_adminqna() {
        document.form1.action = "/servlet/controller.tutor.AdminQnaServlet";
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
    <input type = "hidden" name = "p_pagesize"   value = "<%=v_pagesize %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_tabseq"     value = "<%=v_tabseq%>">
    <input type = "hidden" name = "p_seq"        value = "<%= v_seq %>">
	<input type = "hidden" name = "p_refseq"     value = "<%= v_refseq %>">
	<input type = "hidden" name = "p_levels"     value = "<%= v_levels %>">
	<input type = "hidden" name = "p_position"   value = "<%= v_position %>">
    <input type = "hidden" name = "p_userid"     value = "<%= v_userid %>">


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="768">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="/images/admin/tutor/tit_tutor_quest.gif"></td>
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
          <tr class="table_02_2">
            <td height="25" class="table_title" ><strong>등록일</strong></td>
            <td height="25" class="table_02_2"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>제목</strong></td>
            <td height="25" class="table_02_2"><%= v_title %></td>
          </tr>
          <tr>
            <td width="15%" class="table_title"><strong>내용</strong></td>
            <td height="100" class="table_02_2"><%= v_content %></textarea>
            </td>
          </tr>
		<!---------파일첨부여부 ------------>
		  <tr>
			<td class = "table_title" align = "center">첨부파일</td>
			<td class = "table_02_2"  align = "left">
			  <%
                  Vector i_realfileVector    = (Vector)dbox.getObject("d_realfile");        // 실제 파일명
                  Vector i_savefileVector    = (Vector)dbox.getObject("d_savefile");        // 저장 파일명
                  %>
                  <%@ include file="/learn/admin/include/za_MultiAttach_R.jsp" %> 
			</td>
		  </tr>
	<!---------파일첨부여부 끝 ------------>
          
        </table>
        <!----------------- FORM 끝 ----------------->
        <br>

        <!----------------- 수정, 삭제, 리스트 버튼 시작 ----------------->
        <table width="16%" border="0" cellspacing="0" cellpadding="0">
          <tr>
<%  if(s_gadmin.equals("A1") || v_userid.equals(s_userid)) {    //    수정 여부 %>
            <td align="center"><a href="javascript:modify_adminqna()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>&nbsp;</td>
            <td align="center"><a href="javascript:delete_adminqna()"><img src="/images/admin/button/btn_del.gif" border="0"></a>&nbsp;</td>
<%  }   
	if(!v_userid.equals(s_userid)) { 	// 	  답변여부
%>
			<td align="center"><a href="javascript:reply_adminqna()"><img src="/images/admin/button/btn_answer.gif" border="0"></a>&nbsp;</td>
<%
	}
%>			

            <td align="center"><a href="javascript:list_adminqna()"><img src="/images/admin/button/btn_list.gif" border="0"></a>&nbsp;</td>
          </tr>
        </table>
        <!----------------- 수정, 삭제, 리스트 버튼 끝 ----------------->
        <br>
      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>

</form>
</body>
</html>
