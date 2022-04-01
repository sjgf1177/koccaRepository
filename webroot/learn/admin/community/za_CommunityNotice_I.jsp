<%
//**********************************************************
//  1. 제      목: 학습동호회 공지사항
//  2. 프로그램명: za_CommunityNotice_I.jsp
//  3. 개      요: 공지사항 등록
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2004.02.17
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
    String v_subjnm = box.getString("p_subjnm");
    int v_commid    = 0;
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    int     v_upfilecnt  = box.getInt("p_upfilecnt");            //  서버에 저장되있는 파일수

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
    function insert() {
        moveContent();
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
		/*
        document.forms[0].p_content.editorApplet = DHTMLEdit.tbContentElement;     
        document.forms[0].p_content.value = DHTMLEdit.getDocumentHTML();      
        document.forms[0].InitHTML.value="";      
        document.forms[0].addBody.value="";
		*/
        document.form1.p_search.value     = "";
        document.form1.p_searchtext.value = "";
        document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
        document.form1.p_process.value = "insert";
        document.form1.submit();
    }

   function cancel() {
        document.form1.action = "/servlet/controller.community.CommunityAdminNoticeServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
   }
    //namo editor 작성 내용값 옮기기
    function moveContent(){
        document.form1.p_content.value = document.form1.we.BodyValue;
    }

//-->
</SCRIPT>

<!-- SCRIPT FOR NAMO EDITOR -->
<SCRIPT LANGUAGE="VBScript">
<!--
    Function Load_Content()
            document.form1.we.BodyValue = document.form1.p_content.value
            document.form1.we.focus
    End Function

    Sub we_OnInitCompleted
        call Load_Content()
    End Sub

    Function Move_Content()
            document.form1.p_content.value = document.form1.we.BodyValue
    End Function
-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name = "form1" method = "post" enctype = "multipart/form-data">
    <input type="hidden" name="p_commId" value='0'>
    <input type = "hidden" name = "p_subjnm"    value = "<%=v_subjnm %>">    
    <input type = "hidden" name = "p_process"    value = "<%=v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno %>">
    <input type = "hidden" name = "p_search"     value = "<%=v_search %>">
    <input type = "hidden" name = "p_searchtext" value = "<%=v_searchtext %>">
    <input type = "hidden" name = "p_upfilecnt"  value = "<%= v_upfilecnt %>">
    <input type = "hidden" name = "p_content"    value="">

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
            <td width="12%" height="25" class="table_title"><b>작성자</b></td>
            <td class="table_02_2" width="88%"><%=s_username%></td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>제목</b></td>
            <td class="table_02_2"> 
              <input size="80" maxlength="20" name="p_title" class="input">
            </td>
          </tr>
          <tr> 
            <td class="table_title" height="25"><b>내용</b></td>
            <td class="table_02_2"> 
				<script language = "javascript" src = "/script/user_patch.js"></script>
                <script language='javascript'>object_namopatch('580','300');</script>
							<!--OBJECT WIDTH=0 HEIGHT=0 CLASSID="clsid:5220cb21-c88d-11cf-b347-00aa00a28331">
							<PARAM NAME="LPKPath" VALUE="/jsp/namo/NamoWec4_Namo.lpk">
							</OBJECT>
							<OBJECT ID="we" WIDTH="580" HEIGHT="300" CLASSID="CLSID:C5AC95C0-C873-445B-BA1F-3A430A4DEA42" CODEBASE="/jsp/namo/NamoWec.cab#version=4,0,0,17">
							  <param name="InitFileURL" value="/jsp/namo/namowec.env">
							</OBJECT-->   
            </td>
          </tr>
        </table>
        <!----------------- 자료등록 끝 ----------------->
        <br>

        <!----------------- 등록, 취소 버튼 시작 ----------------->
        <table width="11%" border="0" cellspacing="0" cellpadding="0">
          <tr> 
            <td align="center">
            <a href="javascript:insert()"><img src="/images/admin/button/btn_add.gif"  border="0"></a>
            </td>
            <td align="center">
            <a href="javascript:cancel()"><img src="/images/admin/button/btn_cancel.gif"  border="0"></a>
            </td>
          </tr>
        </table>
        <!----------------- 등록, 취소 버튼 끝 ----------------->
        <br>

      </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
