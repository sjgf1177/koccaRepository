<%
/**
 * file name : za_ClassifySubject.jsp
 * date      : 2003/6/30
 * programmer:
 * function  : 과정분류코드 조회화면
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<html>
<head>
<title>과정분류</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
function insertClassifySubjectPage() {
//    window.self.name = "winClassifySelectList";
    window.open("", "openClassifySubjectInsert", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes, resizable=no,copyhistory=no, width=585, height=500, top=0, left=0");
    document.form2.target = "openClassifySubjectInsert";
    document.form2.action = "/servlet/controller.course.ClassifySubjectServlet";
    document.form2.p_process.value = "insertPage";
    document.form2.submit();
}

function updateClassifySubjectPage(upperclass, middleclass, lowerclass, classname) {
//    window.self.name = "winClassifySelectList";
    window.open("", "openClassifySubjectUpdate", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width=585, height=220, top=0, left=0");
    document.form2.target = "openClassifySubjectUpdate";
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
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title09.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 추가 버튼 시작 ----------------->
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
        <tr>
          <td align="right" height="20"><a href="javascript:insertClassifySubjectPage()"><img src="/images/admin/button/btn_add.gif" border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 추가 버튼 끝 ----------------->
      <table width="97%" border="0" cellspacing="1" cellpadding="5" class="table_out">
        <form name="form2" method="post" action="/servlet/controller.course.SubjectServlet">
          <input type="hidden" name="p_process"     value="">
          <input type="hidden" name="p_upperclass"  value="">
          <input type="hidden" name="p_middleclass" value="">
          <input type="hidden" name="p_lowerclass"  value="">
          <input type="hidden" name="p_classname"   value="">
        </form>
        <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="7%" class="table_title"><b>NO</b></td>
          <td class="table_title" colspan="2" width="30%"><b>대분류</b></td>
          <td class="table_title" colspan="2" width="30%"><b>중분류</b></td>
          <td class="table_title" colspan="2" width="30%"><b>소분류</b></td>
        </tr>
<%  ArrayList list = (ArrayList)request.getAttribute("SubjectClassificationList");
    ClassifySubjectData data  = null;
    String   v_fmtstr = "<a href=\"javascript:updateClassifySubjectPage(''{0}'', ''{1}'', ''{2}'', ''{3}'')\" class=''e''>{4}</a>";
    Object[] v_arguments = {"", "", "", "", ""};
    String   v_updatelink = "";

    for (int i=0; i<list.size(); i++) {
        data  = (ClassifySubjectData)list.get(i);

		v_arguments[0] = data.getUpperclass();
		v_arguments[1] ="000";
		v_arguments[2] ="000"; 

		%>
        <tr>
          <td align="center" class="table_01"><%=String.valueOf(i+1)%></td>
<%      if (data.getUpperrowspannum() > 0) {
            v_arguments[3] = data.getUpperclassname();
            v_arguments[4] = data.getUpperclass();
            v_updatelink = MessageFormat.format(v_fmtstr, v_arguments);  
			
			%>
          <td align="center" class="table_02_1" rowspan="<%=String.valueOf(data.getUpperrowspannum())%>"><%=v_updatelink%></td>
          <td align="center" class="table_02_1" rowspan="<%=String.valueOf(data.getUpperrowspannum())%>"><%=data.getUpperclassname()%></td>
<%      }

        v_arguments[1] = data.getMiddleclass();
        v_arguments[2] = "000";
        if (data.getMiddlerowspannum() > 0) {
            if (data.getMiddleclass().equals("000")) {
                v_updatelink = "";
            } else {
                v_arguments[3] = data.getMiddleclassname();
                v_arguments[4] = data.getMiddleclass();
                v_updatelink = MessageFormat.format(v_fmtstr, v_arguments);
            } %>
          <td align="center" class="table_02_1" width="10%" rowspan="<%=String.valueOf(data.getMiddlerowspannum())%>"><%=v_updatelink%></td>
          <td align="center" class="table_02_1" width="20%" rowspan="<%=String.valueOf(data.getMiddlerowspannum())%>"><%=data.getMiddleclassname()%></td>
<%      }

		v_arguments[2] = data.getLowerclass();
        if (data.getLowerclass().equals("000")) {
            v_updatelink = "";
        } else {
            v_arguments[3] = data.getLowerclassname();
            v_arguments[4] = data.getLowerclass();
            v_updatelink = MessageFormat.format(v_fmtstr, v_arguments);
        } %>
          <td align="center" class="table_02_1" width="10%" ><%=v_updatelink%></td>
          <td align="center" class="table_02_1" width="20%" ><%=data.getLowerclassname()%><br>
		  
		  </td>
        </tr>
<%  } %>
      </table>
      <br>
    </td>
  </tr>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>
</body>
</html>