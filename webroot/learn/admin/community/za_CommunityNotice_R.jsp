<%
//**********************************************************
//  1. 제      목: 학습동호회 공지사항
//  2. 프로그램명: za_CommunityNotice_R.jsp
//  3. 개      요: 공지사항 보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: mscho 2004. 02. 17
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_subjnm = box.getString("p_subjnm");
    int v_commid    = 0;
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_seq        = box.getInt("p_seq");                  // 게시물 ID
    int     v_orgseq     = v_seq;
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

    int v_levels = 0;
    int v_dispnum   = 0;
    int v_totalpage = 0;
    int v_rowcount  = 0;

    int    i         = 0;
    int    v_cnt     = 0;
    String v_userid  = "";
    String v_name    = "";
    String v_indate  = "";
    String v_title   = "";
    String v_content = "";

    DataBox dbox = (DataBox)request.getAttribute("selectCommunityNotice");
    if (dbox != null ) {
        v_seq         = dbox.getInt("d_seq");
        v_userid      = dbox.getString("d_userid");
        v_name        = dbox.getString("d_name");
        v_title       = dbox.getString("d_title");
        v_content     = dbox.getString("d_content");
        v_cnt         = dbox.getInt ("d_cnt");
        v_indate      = dbox.getString("d_indate");

       // v_content = StringManager.replace(v_content,"\r\n","<br>");
//        v_content = BoardBean.convertBody(v_content);
    }

    String s_userid    = box.getSession("userid");
    String s_username  = box.getSession("name");


%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function reply_CommunityNotice() {
        document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
        document.form1.p_process.value = "replyPage";
        document.form1.submit();
    }

    function modify_CommunityNotice() {
        document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

    function delete_CommunityNotice() {
        if (confirm("정말로 삭제하시겠습니까?")) {
            document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
            document.form1.p_process.value = "delete";
            document.form1.submit();
        }
        else {
            return;
        }
    }

    function list_CommunityNotice() {
        document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }


    function selectList() {
        document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function select(seq, upfilecnt, userid) {
        document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
        document.form1.p_process.value = "select";
        document.form1.p_seq.value = seq;
        document.form1.p_upfilecnt.value = upfilecnt;
        document.form1.p_userid.value    = userid;
        document.form1.submit();
    }
    function insertPage() {
        document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type="hidden" name="p_commId" value='0'>
    <input type = "hidden" name = "p_subjnm"    value = "<%=v_subjnm %>">
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_seq"        value = "<%= v_seq %>">
    <input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">
    <input type = "hidden" name = "p_userid"     value = "<%= v_userid %>">



  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="../../../images/admin/community/tit_comm_02.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
        <br>
        <br>

        <!----------------- 자료등록 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr>
            <td colspan="2" class="table_top_line"></td>
          </tr>
          <tr>
            <td width="12%" class="table_title"><b>작성자</b></td>
            <td class="table_02_2" width="88%"><%=v_name%></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>제목</b></td>
            <td class="table_02_2"><%= v_title %></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>등록일</b></td>
            <td class="table_02_2"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>조회수</b></td>
            <td class="table_02_2"><%=v_cnt%></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>내용</b></td>
            <td class="table_02_2"><%= v_content %></td>
          </tr>
        </table>
        <!----------------- 자료등록 끝 ----------------->



        <br>

        <!----------------- 등록, 취소 버튼 시작 ----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
<%  if(BulletinManager.isAuthority(box, box.getString("p_canModify"))) {    //    수정 여부 %>
            <td align="center">
            <a href="javascript:modify_CommunityNotice()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>
            </td>
<%
    }

    if(BulletinManager.isAuthority(box, box.getString("p_canDelete"))) {    //    삭제 여부
%>
            <td align="center">
            <a href="javascript:delete_CommunityNotice()"><img src="/images/admin/button/btn_del.gif"  border="0"></a>
            </td>
<%  }  %>
          </tr>
        </table>
        <!----------------- 등록, 취소 버튼 끝 ----------------->

        <br>
        <!----------------- 리스트, 추가 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" height="3">
                <%  if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {    //    쓰기 여부 %>
                    <a href = "javascript:insertPage();"><img src="/images/admin/button/btn_addplus.gif" align="absmiddle"  border="0"></a>
                <%  }   %>
            </td>
            <td align="right" width="50">
            <a href = "javascript:list_CommunityNotice();"><img src="/images/admin/button/btn_list.gif" align="absmiddle" border="0"></a>
            </td>
          </tr>
        </table>
        <!----------------- 리스트, 추가 버튼 끝 ----------------->


<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
