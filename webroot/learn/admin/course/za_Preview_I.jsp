<%
//**********************************************************
//  1. 제      목: 과정맛보기 등록
//  2. 프로그램명: za_Preview_I.jsp
//  3. 개      요: 과정맛보기 등록
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 18
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_grcode   = box.getString("p_grcode");
    String v_subj     = box.getString("p_subj");
    String v_subjnm   = box.getString("p_subjnm");
    
    String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
%>
<html>
<head>
<title>과정맛보기등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function goPreviewListPage() {
  document.form2.p_process.value = 'previewPage';
  document.form2.submit();
}
function InsertPreview() {
	if(!chkData()) {
	  return;
	}
  document.form2.p_process.value = 'previewInsert';
  document.form2.submit();
}

function chkData() {

  if (blankCheck(document.form2.p_edumans.value)) {
    alert('교육대상을 입력하십시요.');
    document.form2.p_edumans.focus();
    return false;
  }

  if (blankCheck(document.form2.p_intro.value)) {
    alert('교육목적을 입력하십시요.');
    document.form2.p_intro.focus();
    return false;
  }

  if (blankCheck(document.form2.p_explain.value)) {
    alert('교육내용 입력하십시요.');
    document.form2.p_explain.focus();
    return false;
  }

  return true;
}
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form2" method="post" action="/servlet/controller.course.SubjectServlet">
  <input type="hidden" name="p_process"  value="">
    <input type="hidden" name="s_upperclass"       value="<%=ss_upperclass%>">
    <input type="hidden" name="s_middleclass"       value="<%=ss_middleclass%>">
    <input type="hidden" name="s_grcode"           value="<%=ss_grcode%>">
    
  <input type="hidden" name="p_subj"     value="<%=v_subj%>">
  <input type="hidden" name="p_subjnm"   value="<%=v_subjnm%>">
  <input type="hidden" name="p_grcode"   value="<%=v_grcode%>">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
      <td align="center" valign="top"> 
        <!----------------- title 시작 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
          <tr> 
            <td><img src="../../../images/admin/course/tit_courseguide.gif"></td>
            <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
          </tr>
        </table>
        <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 과정명 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
		  <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>과정명 : [<%=v_subj%>] <%=v_subjnm%></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명 끝 ----------------->
      <!----------------- 과정맛보기 등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="16%" class="table_title"><b>교육대상</b></td>
          <td width="81%" class="table_02_2">
            <input name="p_edumans" type="text" class="input" size="118" maxlength="100">
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>교육목적</b></td>
          <td class="table_02_2">
            <textarea name="p_intro" cols="100" rows="3"></textarea>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>교육내용</b></td>
          <td class="table_02_2">
            <textarea name="p_explain" cols="100" rows="15"></textarea>
          </td>
        </tr>
        <tr>
          <td class="table_title"><b>강의교재 및 참고문헌</b></td>
          <td class="table_02_2">
            <textarea name="p_memo" cols="100" rows="5" tabindex=67></textarea>
          </td>
        </tr>
      </table>
      <!----------------- 과정맛보기 등록 끝 ----------------->
      <br>
      <!----------------- 등록, 리스트 버튼 시작 ----------------->
      <table border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td align="center"><a href="javascript:InsertPreview()"><img src="/images/admin/button/btn_add.gif" border="0"></a></td>
		  <td width=8></td>
          <td align="center"><a href="javascript:goPreviewListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록, 리스트 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
