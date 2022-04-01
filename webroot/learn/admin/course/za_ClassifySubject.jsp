<%
/**
 * file name : za_ClassifySubject.jsp
 * date      : 2003/6/30
 * programmer: 
 * function  : 과정분류코드 조회화면
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
    if (box == null) {
    	box = RequestManager.getBox(request);
    }
%>
<html>
<head>
<title>Manager Screen</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
  <style>
    A:link     { color: white; text-decoration:none; font-family: 굴림; font-size: 9pt; font-weight:bold;}
    A:visited  { color: white; text-decoration: none; font-family: 굴림; font-size: 9pt; font-weight:bold;}
    A:active   { text-decoration: none; font-family: 굴림; font-size: 9pt; font-weight:bold;}
    A:hover    { color: #A5FCF8; text-decoration:none; font-family: 굴림; font-size: 9pt; font-weight:bold;}
    .ms   {font-family: "굴림", "굴림체", "돋움", "돋움체", "Arial", "Verdana", "Tahoma"; font-size: 9pt; color: #002957}
    .ms1  {font-family: "굴림", "굴림체", "돋움", "돋움체", "Arial", "Verdana", "Tahoma"; font-size: 8pt; color: #FFFFFF;}
    td  {font-family: "굴림", "굴림체", "돋움", "돋움체", "Arial", "Verdana", "Tahoma"; font-size: 9pt; color: #3C3C3C}
  </style>
<SCRIPT LANGUAGE="JavaScript">
function whenSelection(url, process, seltype) {
	document.form1.action=url;
	document.form1.p_process.value=process;
	document.form1.p_seltype.value=seltype;
	document.form1.submit();
}

function insertClassifySubjectPage() {
//    window.self.name = "winSelectList";
    window.open("", "openClassifySubjectInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=yes,copyhistory=no, width=400, height=300, top=0, left=0");
    document.form2.target = "openClassifySubjectInsert"
    document.form2.action = "/servlet/controller.course.ClassifySubjectServlet";
    document.form2.p_process.value = "insertPage";
    document.form2.submit();
}

function updateClassifySubjectPage(upperclass, middleclass, lowerclass, classname) {
//    window.self.name = "winSelectList";
    window.open("", "openClassifySubjectUpdate", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width=400, height=300, top=0, left=0");
    document.form2.target = "openClassifySubjectUpdate"
    document.form2.action = "/servlet/controller.course.ClassifySubjectServlet";
    document.form2.p_process.value    = "updatePage";
    document.form2.p_upperclass.value  = upperclass;
    document.form2.p_middleclass.value = middleclass;
    document.form2.p_lowerclass.value  = lowerclass;
    document.form2.p_classname.value   = classname;
    document.form2.submit();
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1024" border="0" cellspacing="0" cellpadding="0" height="768">
  <tr>
    <td valign="top" bgcolor="#EEF8F9"> 
      <table width="1024" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td>
            <!----------------- 메뉴시작 ----------------->
            <table width="1024" border="0" cellspacing="0" cellpadding="0">
<%@ include file="../include/za_menu_bar01.jsp" %>
            </table>
            <!----------------- 메뉴끝 ------------------->
          </td>
        </tr>
      </table>
      <!----------------- 콘텐츠 시작 ----------------->
      <table width="1024" border="0" cellspacing="0" cellpadding="0">
        <form name="form2" method="post" action="">
<%@ include file="za_ClassifySubject_L.jsp" %>
        </form>
      </table>
      <!----------------- 컨텐트 끝 ----------------->
    </td>
  </tr>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     


</table>
</body>
</html>