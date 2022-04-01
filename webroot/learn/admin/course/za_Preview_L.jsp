<%
//**********************************************************
//  1. 제      목: 과정맛보기 관리
//  2. 프로그램명: za_Preview_L.jsp
//  3. 개      요: 과정맛보기 관리
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

	String  ss_grcode    = box.getString("s_grcode");           //교육그룹
    String  ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");    //과정중분류
    
	//String v_grcode   = box.getString("p_grcode");
    String v_subj     = box.getString("p_subj");
    String v_subjnm   = box.getString("p_subjnm");
	

    ArrayList list = (ArrayList)request.getAttribute("PreviewGrcodeList");
%>
<html>
<head>
<title>과정맛보기관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
function InsertPreviewPage(p_subj) {
    document.form2.p_process.value = 'previewInsertPage';
    document.form2.p_subj.value  = p_subj;
    document.form2.submit();
}

function UpdatePreviewPage(p_subj, p_grcode) {
    document.form2.p_process.value = 'previewUpdatePage';
    document.form2.p_subj.value    = p_subj;
    document.form2.p_grcode.value  = p_grcode;
    document.form2.submit();
}

function OpenPreviewPage(p_subj, p_grcode) {
  alert('OpenPreviewPage(p_subj, p_grcode)');
}

function goSubjectListPage() {
	document.form2.s_action.value     = "go";
    document.form2.p_process.value    = 'listPage';
    document.form2.p_subj.value       = "";
    document.form2.submit();
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
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
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="table1">
        <tr>
		   <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td valign="bottom" class=sub_title>과정명 : [<%=v_subj%>] <%=v_subjnm%></td>
          <td align="right" valign="top"> <a href="javascript:goSubjectListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명 끝 ----------------->
      <!----------------- 과정맛보기 관리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="3" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="7%" class="table_title"><b>NO</b></td>
          <td class="table_title"><b>교육그룹</b></td>
        </tr>
        <form name="form2" method="post" action="/servlet/controller.course.SubjectServlet">
            <input type="hidden" name="s_upperclass"       value="<%=ss_upperclass%>">
    		<input type="hidden" name="s_middleclass"       value="<%=ss_middleclass%>">
    		<input type="hidden" name="s_grcode"           value="<%=ss_grcode%>">
    
          <input type="hidden" name="p_process"  value="">
          <input type="hidden" name="p_subj"     value="<%=v_subj%>">
          <input type="hidden" name="p_subjnm"   value="<%=v_subjnm%>">
          <input type="hidden" name="p_grcode"   value="">
          
          <input type="hidden" name="s_action"   value="">
        </form>
<%  SelectionData data  = null;
    for (int i=0; i<list.size(); i++) {
      data  = (SelectionData)list.get(i); %>
        <tr>
          <td class="table_01"><%=String.valueOf(i+1)%></td>
          <td class="table_02_1"><a href="javascript:UpdatePreviewPage('<%=v_subj%>', '<%=data.getCode()%>')" class='e'><%= data.getName() %></a></td>
        </tr>
<%  }  %>
      </table>
      <!----------------- 과정맛보기 관리 끝 ----------------->
    </td>
  </tr>

</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
