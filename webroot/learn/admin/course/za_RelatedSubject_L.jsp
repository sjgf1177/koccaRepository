<%
/**
 * file name : za_Preview_L.jsp
 * date      : 2003/07/21
 * programmer:
 * function  : 과정맛보기 관리
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.lang.*" %>
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
    
    String  ss_subj      = box.getStringDefault("s_subj","ALL");
    String v_grcode   = box.getStringDefault("p_grcode","N000001");
    String v_subj     = box.getString("p_subj");
    String v_subjnm   = box.getString("p_subjnm");

    ArrayList list1 = (ArrayList)request.getAttribute("PreRelatedSubjList");
    ArrayList list2 = (ArrayList)request.getAttribute("NextRelatedSubjList");
%>
<html>
<head>
<title>과정맛보기관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
function InsertRelatedSubjPage(p_subjgubun) {
  document.form2.p_process.value = 'relatedSubjInsertPage';
  document.form2.p_subjgubun.value = p_subjgubun;
  document.form2.submit();
}

function goRelatedSubjListPage() {
  document.form2.s_action.value = "go";
  document.form2.p_process.value = 'listPage';
  document.form2.p_subj.value    = "";
  document.form2.submit();
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form2" method="post" action="/servlet/controller.course.SubjectServlet">
    <input type="hidden" name="p_process"  value="">
    <input type="hidden" name="s_upperclass"       value="<%=ss_upperclass%>">
    <input type="hidden" name="s_middleclass"       value="<%=ss_middleclass%>">
    <input type="hidden" name="s_grcode"           value="<%=ss_grcode%>">
    
    <input type="hidden" name="s_subj"        value="<%=ss_subj%>">
    <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
    <input type="hidden" name="p_subj"  value="<%=v_subj%>">
    <input type="hidden" name="p_subjnm"  value="<%=v_subjnm%>">
    <input type="hidden" name="p_subjgubun"  value="">
    
    <input type="hidden" name="s_action"  value="">

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/course/c_title01.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 선수과정관리, 후수과정관리 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0" class="table1">
        <tr>
		  <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td valign="bottom" class=sub_title>과정명 : [<%=v_subj%>] <%=v_subjnm%></td>
          <td align="right"><a href="javascript:goRelatedSubjListPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a> <a href="javascript:InsertRelatedSubjPage('PRE')"><img src="/images/admin/button/btn_sunsoo.gif" border="0"></a> <a href="javascript:InsertRelatedSubjPage('NEXT')"><img src="/images/admin/button/btn_whosoo.gif"  border="0"></a></td>
        </tr>
        <tr>
          <td height="3" colspan="4"></td>
        </tr>
      </table>
      <!----------------- 선수과정관리, 후수과정관리 버튼 끝 ----------------->
      <!----------------- 연관과정 관리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>
        <tr>
          <td colspan="2" class="table_title" width="50%"><b>선수과정</b></td>
          <td colspan="2" class="table_title" width="50%"><b>후속과정</b></td>
        </tr>
<%
    SelectionData data  = null;
        String preno    = "";
        String presubj  = "";
        String nextno   = "";
        String nextsubj = "";

    int nSize = Math.max(list1.size(), list2.size());
    for (int i=0; i<nSize; i++) {
        if (i < list1.size()) {
            data    = (SelectionData)list1.get(i);
                presubj = "[" + data.getCode() + "] " + data.getName(); 
                preno   = String.valueOf(i+1);
        } else {
                presubj = "";
                preno   = "";
        }
        if (i < list2.size()) {
            data    = (SelectionData)list2.get(i);
                nextsubj = "[" + data.getCode() + "] " + data.getName(); 
                nextno   = String.valueOf(i+1);
        } else {
            nextsubj = "";
            nextno   = "";
        }  %>
        <tr>
          <td class="table_01"   width="10%"><%=preno%></td>
          <td class="table_02_1" width="40%"><%=presubj%></td>
          <td class="table_01"   width="10%"><%=nextno%></td>
          <td class="table_02_1" width="40%"><%=nextsubj%></td>
        </tr>
<%  }  %>
      </table>
      <!----------------- 연관과정 관리 끝 ----------------->
      <br>
    </td>
  </tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
