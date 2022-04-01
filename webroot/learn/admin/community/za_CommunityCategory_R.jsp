<%
//**********************************************************
//  1. 제      목: 학습동호회 공지사항
//  2. 프로그램명: za_CommunityCategory_R.jsp
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
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_codenm = box.getString("p_codenm");
    int v_commid    = 0;
    String  v_process  = box.getString("p_process");

    String v_userid = "";
    String v_name   = "";
    String v_indate = "";
    String v_code	= "";
    String v_parent = "";
    String v_parentnm = "";
    String v_levels   = "";

    DataBox dbox = (DataBox)request.getAttribute("selectCommunityCategoryView");
    if (dbox != null ) {
        v_userid      = dbox.getString("d_userid");
        v_name        = dbox.getString("d_name");
        v_codenm      = dbox.getString("d_codenm");
        v_code		  = dbox.getString("d_code");
        v_parent      = dbox.getString("d_parent");
        v_indate      = dbox.getString("d_ldate");
        v_parentnm    = dbox.getString("d_parentnm");
        v_levels	  = dbox.getString("d_levels");

    }

    String s_userid		= box.getSession("userid");
    String s_username	= box.getSession("name");
	String v_grtype		= box.getString("p_grtype");


%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function modify_CommunityCategory() {
        document.form1.action = "/servlet/controller.community.CommunityAdminCategoryServlet";
        document.form1.p_process.value = "updatePage";
		document.form1.submit();
    }

    function delete_CommunityCategory() {
        if (confirm("정말로 삭제하시겠습니까?")) {
            document.form1.action = "/servlet/controller.community.CommunityAdminCategoryServlet";
            document.form1.p_process.value = "delete";
            document.form1.submit();
        }
        else {
            return;
        }
    }

    function list_CommunityCategory() {
        document.form1.action = "/servlet/controller.community.CommunityAdminCategoryServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }


    function selectList() {
        document.form1.action = "/servlet/controller.community.CommunityAdminCategoryServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function select(seq, upfilecnt, userid) {
        document.form1.action = "/servlet/controller.community.CommunityAdminCategoryServlet";
        document.form1.p_process.value = "select";
        document.form1.p_seq.value = seq;
        document.form1.p_upfilecnt.value = upfilecnt;
        document.form1.p_userid.value    = userid;
        document.form1.submit();
    }
    function insertPage() {
        document.form1.action = "/servlet/controller.community.CommunityAdminCategoryServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post">
    <input type="hidden" name="p_commId" value='0'>
    <input type = "hidden" name = "p_codenm"   value = "<%=v_code %>">
    <input type = "hidden" name = "p_levels"   value = "<%=v_levels %>">
    <input type = "hidden" name = "p_process"  value = "<%=v_process %>">
    <input type = "hidden" name = "p_code"     value = "<%=v_code %>">
    <input type="hidden" name="p_grtype"	value="<%=v_grtype %>">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="../../../images/admin/community/tit_comm_06.gif"></td>
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
            <td class="table_title" height="25" width="30%"><b>작성자</b></td>
            <td class="table_02_2"><%= v_name %></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>등록일</b></td>
            <td class="table_02_2"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>대분류</b></td>
            <td class="table_02_2"><%= v_parentnm %></td>
          </tr>
          <tr>
            <td class="table_title" height="25"><b>코드명</b></td>
            <td class="table_02_2"><%= v_codenm %></td>
          </tr>
        </table>
        <!----------------- 자료등록 끝 ----------------->



        <br>

        <!----------------- 등록, 취소 버튼 시작 ----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center">
            <a href="javascript:modify_CommunityCategory()"><img src="/images/admin/button/btn_modify.gif" border="0"></a>
            </td>
			<td align="center">
            <a href="javascript:delete_CommunityCategory()"><img src="/images/admin/button/btn_del.gif"  border="0"></a>
            </td>
          </tr>
        </table>
        <!----------------- 등록, 취소 버튼 끝 ----------------->

        <br>
        <!----------------- 리스트, 추가 버튼 시작 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="right" height="3">
				<a href = "javascript:insertPage();"><img src="/images/admin/button/btn_addplus.gif" align="absmiddle"  border="0"></a>
            </td>
            <td align="right" width="50">
            <a href = "javascript:list_CommunityCategory();"><img src="/images/admin/button/btn_list.gif" align="absmiddle" border="0"></a>
            </td>
          </tr>
        </table>
        <!----------------- 리스트, 추가 버튼 끝 ----------------->


<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
