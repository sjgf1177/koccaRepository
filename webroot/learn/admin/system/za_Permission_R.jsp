<%
//**********************************************************
//  1. 제      목: 권한관리
//  2. 프로그램명 : za_Permission_R.jsp
//  3. 개      요: 권한관리 등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 11. 10
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");
    String  v_pageno    = box.getString("p_pageno");
    String ss_gadmin    = box.getStringDefault("s_gadmin","ALL");       //gadmin

    String v_padmin     = box.getString("p_padmin");
    String v_gadmin     = box.getString("p_gadmin");

    // 관리자 기본정보
    String v_gadminnm   = "";
    String v_comments   = "";
    String v_luserid    = "";
    String v_ldate      = "";
    int    v_gadminseq  = 0;

    // 관리자 기본정보
    PermissionData data = (PermissionData)request.getAttribute("selectManager");
    v_gadminnm	   = data.getGadminnm();
    v_comments     = data.getComments();
    v_gadminseq    = data.getGadminseq();
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function modify_manager() {
		if (document.form1.p_gadmin.value=="P1") {
             alert("강사관리에서 수정해주십시요");
			 return;
		}

        document.form1.action = "/servlet/controller.system.PermissionServlet";
        document.form1.p_process.value = "update";
        document.form1.submit();
    }

    function delete_manager() {
    	if (confirm("권한을 삭제 하시겠습니까?")){
        document.form1.action = "/servlet/controller.system.PermissionServlet";
        document.form1.p_process.value = "delete";
        document.form1.submit();
      }  
    }

    function list_manager() {
        document.form1.action = "/servlet/controller.system.PermissionServlet";
        document.form1.p_process.value = "select";
        document.form1.submit();
    }

    // 숫자만 들어가게 만든다.
    function onlyNumber()
    {
        if(((event.keyCode<48)||(event.keyCode>57)))
        event.returnValue=false;
    }
//-->
</SCRIPT>
</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">


<form name="form1" method="post">
    <input type = "hidden" name = "p_process"    value = "<%= v_process %>">
    <input type = "hidden" name = "p_pageno"     value = "<%=v_pageno%>">
    <input type = "hidden" name = "p_padmin"     value = "<%=v_padmin%>">
    <input type = "hidden" name = "p_gadmin"     value = "<%=v_gadmin%>">
    <input type = "hidden" name = "s_gadmin"     value = "<%=ss_gadmin%>">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="665">
  <tr>
    <td align="center" valign="top">


      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <!----------------- title 끝 ----------------->
        <br>
      <!----------------- 권한등록 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">
          <tr> 
            <td colspan="4" class="table_top_line"></td>
          </tr>
          <!------------------------  권한분류 시작 ---------------------->
          <tr>
            <td class="table_title"><strong>권한(기본)</strong></td>
            <td height="25" class="table_02_2" colspan="3">
               <%= GadminAdminBean.getGadminSelectTop("p_gadminsel",v_padmin) %>
            </td>
          </tr>
          <!------------------------  권한분류 끝   ---------------------->
<%
	if (v_gadmin.equals("A1") || v_gadmin.equals("A2") || v_gadmin.equals("C1") || v_gadmin.equals("C2") || v_gadmin.equals("F1") || v_gadmin.equals("H1") || v_gadmin.equals("K2") || v_gadmin.equals("P1") || v_gadmin.equals("R1") || v_gadmin.equals("S1") || v_gadmin.equals("T1") || v_gadmin.equals("U1")){
%>
          <tr> 
            <td width="15%" class="table_title"><strong>추가 권한명</strong></td>
            <td width="45%" height="25" class="table_02_2"><%=v_gadminnm%></td>
            <td width="15%" class="table_title"><strong>정렬 순서</strong></td>
            <td width="25%" class="table_02_2"><input type="text" name="p_gadminseq" value="<%=v_gadminseq%>" size="2" maxlength="2" OnKeypress="onlyNumber();">
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>권한 사용 용도</strong></td>
            <td height="25" class="table_02_2" colspan="3"><%=v_comments%></td>
          </tr>
	       </table>
        <!----------------- 권한등록 끝 ----------------->
        <br>
        <!----------------- 수정, 삭제, 리스트 버튼 시작 ----------------->
        <table width="16%" border="0" cellspacing="0" cellpadding="0">
          <tr>
          	<td align="center"><a href="javascript:list_manager()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
<%
	} else {
%>	
          <tr> 
            <td width="15%" class="table_title"><strong>추가 권한명</strong></td>
            <td width="45%" height="25" class="table_02_2"><input type="text" name="p_gadminnm" size="50" maxlength="50" value="<%=v_gadminnm%>"></td>
            <td width="15%" class="table_title"><strong>권한 순서</strong></td>
            <td width="25%" class="table_02_2"><input type="text" name="p_gadminseq" value="<%=v_gadminseq%>" size="2" maxlength="2" OnKeypress="onlyNumber();">
          </tr>
          <tr> 
            <td width="15%" class="table_title"><strong>권한 사용 용도</strong></td>
            <td height="25" class="table_02_2" colspan="3"><input type="text" name="p_comments" size="100" maxlength="100" value="<%=v_comments%>"></td>
          </tr>
        </table>
        <!----------------- 권한등록 끝 ----------------->
        <br>
        <!----------------- 수정, 삭제, 리스트 버튼 시작 ----------------->
        <table  border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><a href="javascript:modify_manager()"><img src="/images/admin/button/btn_modify.gif" border="0"></a></td>
			<td width=8></td>
            <td align="center"><a href="javascript:delete_manager()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
			<td width=8></td>
            <td align="center"><a href="javascript:list_manager()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
<%
	}
%>  
          </tr>
        </table>
        <!----------------- 수정, 삭제, 리스트 버튼 끝 ----------------->
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
