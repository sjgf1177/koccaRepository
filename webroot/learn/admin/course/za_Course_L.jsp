<%
/**
 * file name : za_Course_L.jsp
 * date      : 2003/07/08
 * programmer: 
 * function  : 전문가 조회화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
  RequestBox box = null;
  box = (RequestBox)request.getAttribute("requestbox");
	String  v_coursenm 	= box.getString("p_coursenm");
%>
<html>
<head>
<title>전문가관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--
function SelectCourse() {
	  document.form2.p_process.value = 'listPage';
    document.form2.submit();
}
function InsertCoursePage() {
	  document.form2.p_process.value = 'insertPage';
    document.form2.submit();
}
function UpdateCoursePage(p_course) {
	  document.form2.p_process.value  = 'updatePage';
	  document.form2.p_course.value   = p_course;
    document.form2.p_coursenm.value = '';
    document.form2.submit();
}
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <form name="form2" method="post" action="/servlet/controller.course.CourseServlet">
    <input type="hidden" name="p_process"  value="">
    <input type="hidden" name="p_course"   value="">
  <tr>
    <td align="center" valign="top">
     <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr> 
          <td><img src="/images/admin/course/c_title10.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
     <!----------------- title 끝 ----------------->

      <br>
      <br>
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td align="left">전문가 과정명 : <input type="text" name="p_coursenm" size=20 maxlength=50 value="<%=v_coursenm%>"</td>
          <td align="right"><a href="javascript:SelectCourse()"><img src="/images/admin/button/btn_inquiry.gif"  border="0"></a>  <a href="javascript:InsertCoursePage()"><img src="/images/admin/button/btn_add.gif" border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- form 시작 ----------------->
			<table width="97%" border="0" cellspacing="1" cellpadding="5"  class="table_out">
        <tr>
          <td colspan="5" class="table_top_line"></td>
        </tr>
	      <tr> 
          <td width="6%"  class="table_title"><b>NO</b></td>
	        <td width="15%" class="table_title"><b>전문가코드</b></td>
	        <td width="40%" class="table_title"><b>전문가명</b></td>
	        <td width="15%" class="table_title"><b>과정수</b></td>
	        <td width="20%" class="table_title"><b>생성일자</b></td>
	      </tr>
<%  ArrayList  list = (ArrayList)request.getAttribute("CourseList");
		CourseData data = null;
		for (int i=0; i<list.size(); i++) {
				data  = (CourseData)list.get(i); %>
        <tr> 
          <td class="table_01"><%=String.valueOf(i+1)%></td>
          <td class="table_02_1" ><%=data.getCourse()%></td>
          <td class="table_02_1" ><a href="javascript:UpdateCoursePage('<%=data.getCourse()%>')" class='a'><%=data.getCoursenm()%></a></td>
          <td class="table_02_1" ><%=String.valueOf(data.getSubjcnt())%></td>
          <td class="table_02_1" ><%=FormatDate.getFormatDate(data.getIndate(),"yyyy-MM-dd HH:mm") %></td>
        </tr>
<%	} %>
      </table>
      <!----------------- 과정 관리 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>

