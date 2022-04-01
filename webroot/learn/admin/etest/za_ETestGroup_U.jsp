<%
/**
 * file name : za_ETestGroup_U.jsp
 * date      : 2003/09/23
 * programmer:
 * function  : 온라인테스트 그룹 수정화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

	String v_reloadlist = box.getString("p_reloadlist");
	
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function UpdateGroup() {
	if(!chkData()) {
	  return;
	}
  document.form2.p_process.value = 'ETestGroupUpdate';
  document.form2.p_action.value  = 'go';
  document.form2.p_reloadlist.value = 'true';
  document.form2.submit();
}
function DeleteGroup() {
  if (confirm("E-TEST 그룹을 삭제 하시겠습니까?")) {
    document.form2.p_process.value = 'ETestGroupDelete';
    document.form2.p_action.value  = 'go';
    document.form2.p_reloadlist.value = 'true';
    document.form2.submit();
  }
}
function chkData() {
  if (blankCheck(document.form2.p_etestsubjnm.value)) {
    alert('그룹명을 입력하십시요.');
    document.form2.p_etestsubjnm.focus();
    return false;
  }
  if (document.form2.s_upperclass.value == 'ALL') {
			alert("그룹분류를 선택하세요.");
			return ;
  }
  return true;
}
function goGroupListPage() {
  document.form2.p_process.value  = 'ETestGroupListPage';
  document.form2.submit();
}
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>
function onload() {
  window.self.close();
}
-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" <%=v_onload%>>
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/etest/test_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
<%
    DataBox dbox = (DataBox)request.getAttribute("ETestGroupData");
%>
      <!----------------- 온라인테스트 그룹수정 시작 ----------------->
      <table cellspacing="1" cellpadding="0" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.etest.ETestGroupServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"      value="">
        <input type="hidden" name="p_grcode"  value="<%=dbox.getString("d_grcode")%>">
        <input type="hidden" name="p_etestsubj"    value="<%=dbox.getString("d_etestsubj")%>">
        <input type="hidden" name="p_reloadlist" value="">
        <tr> 
          <td class="table_top_line"></td>
        </tr>
        <tr> 
          <td class="table_01"> 
            <table cellspacing="0" cellpadding="0" class="s_table">
              <tr> 
                <td width="70" height="25" class="table_title">* 그룹코드 </td>
				<td width=1 height="30" bgcolor="76ABE0"></td>
                <td>&nbsp; 
                  <%=dbox.getString("d_etestsubj")%>
                </td>
              </tr>
              <tr> 
                <td height=1 bgcolor="76ABE0"></td>
				<td height=1 bgcolor="76ABE0"></td>
				<td height=1 bgcolor="76ABE0"></td>
              </tr>
              <tr> 
                <td height="25" class="table_title">* 그룹명</td>
				<td width=1 height="30" bgcolor="76ABE0"></td>
                <td>&nbsp;
                  <input name="p_etestsubjnm" type="text" class="input" size="50" value="<%=dbox.getString("d_etestsubjnm")%>">
                </td>
              </tr>
              <tr> 
                <td height=1 bgcolor="76ABE0"></td>
				<td height=1 bgcolor="76ABE0"></td>
				<td height=1 bgcolor="76ABE0"></td>
              </tr>
              <tr> 
                <td height="25" class="table_title">* 그룹분류</td>
				<td width=1 height="30" bgcolor="76ABE0"></td>
                <td>&nbsp;
                <%=CodeConfigBean.getCodeGubunSelect (ETestBean.ETSUBJ_CLASS, "", 1, "s_upperclass", dbox.getString("d_upperclass"), "", 2)%>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
      <!-----------------  온라인테스트 그룹수정 끝 ----------------->
      <br>
      <!----------------- 저장, 삭제, 리스트 버튼 시작 ----------------->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="center"><a href="javascript:UpdateGroup()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
		  <td width=8></td>
          <td align="center"><a href="javascript:DeleteGroup()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
		  <td width=8></td>
          <td align="center"><a href="javascript:window.close()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 저장, 삭제, 리스트 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
