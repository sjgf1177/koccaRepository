<%
/**
 * file name : za_Courseseq_L.jsp
 * date      : 2003/10/29
 * programmer: LeeSuMin.. icarus..
 * function  : 코스차수 조회화면
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/jsp/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%
  	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	String  s_course 	= box.getString("s_course");
	String	s_cyear		= box.getString("s_cyear");
	
	
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="../../../css/admin_style.css" type="text/css">
<script language="JavaScript" src="../../../js/default.js"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">

	function whenSubmit(act) {
		document.form1.p_action.value=act;
		if (act == 'go') {
		    top.ftop.setPam();
  		}
		document.form1.p_process.value = 'listSeqPage';
		document.form1.submit();
	}
	function whenOrder(vals) {
		document.form1.p_action.value='go';
		document.form1.p_order.value = vals;
		whenSubmit();
	}
</SCRIPT>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="../../../images/admin/community/title_bg.gif"><img src="../../../images/admin/course/course_title36.gif" width="140" height="36" border="0"></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 박스 시작 ----------------->
      <table cellspacing="0" cellpadding="3" class="form_table_out">
        <form name="form1" method="post" action="/servlet/controller.course.CourseServlet">
          <input type="hidden" name="p_process"  value="">
          <input type="hidden" name="p_action"  value="">
          <input type="hidden" name="p_order"   value="cyear,courseseq">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg">
              <tr>
                <td height="7" width="99%"></td>
              </tr>
              <tr>
                <td align="center" width="99%" valign="middle">
                  <table width="99%" cellspacing="0" cellpadding="0" class=""form_table">
                    <tr>
                      <td>
                     	코스           
						<% SelectParam param = new SelectParam("s_course", "",true, 24, box.getHttpSession()); %>
                      	<%=SelectionUtil.getCourse(s_course,s_course, param)%>         
						&nbsp;
                      	년도
                        <%=CourseBean.getCyearSelect(s_cyear)%>&nbsp;
                      <a href="javascript:whenSubmit('go')"><img src="../../../images/admin/propose/go1_butt.gif" width="33" height="20" border="0" align=absmiddle></a></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="7" width="99%"></td>
              </tr>
            </table>
          </td>
        </tr>
        </FORM>
      </table>
      <!----------------- 박스 끝 ----------------->
      <br>
      <!----------------테이블 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="6%" class="table_title"><b>No</b></td>
          <td class="table_title"><b><a href="javascript:whenOrder('coursenm')">코스</a></b></td>
          <td class="table_title"><b><a href="javascript:whenOrder('cyear')">코스년도</a></b></td>
          <td class="table_title"><b><a href="javascript:whenOrder('courseseq')">코스차수</a></b></td>
          <td class="table_title"><b><a href="javascript:whenOrder('a.grcode')">교육주관</a></b></td>
          <td class="table_title"><b>교육그룹</b></td>
          <td class="table_title"><b><a href="javascript:whenOrder('a.luserid')">최종수정자</a></b></td>
          <td class="table_title"><b><a href="javascript:whenOrder('a.indate')">생성일</a></b></td>
        </tr>
<%  ArrayList  list = (ArrayList)request.getAttribute("CourseseqList");
		CourseData data = null;
		for (int i=0; i<list.size(); i++) {
				data  = (CourseData)list.get(i); %>
        <tr> 
          <td class="table_01"><%=String.valueOf(i+1)%></td>
          <td class="table_02_1" ><%=data.getCourse()%>-<%=data.getCoursenm()%></td>
          <td class="table_02_1" ><%=data.getCyear()%></td>
          <td class="table_02_1" ><%=data.getCourseseq()%></td>
          <td class="table_02_1" ><%=data.getGrcodenm()%></td>
          <td class="table_02_1" ><%=data.getGyear()%>-<%=data.getGrseq()%></td>
          <td class="table_02_1" ><%=data.getLuserid()%></td>
          <td class="table_02_1" ><%=FormatDate.getFormatDate(data.getIndate(),"yyyy-MM-dd HH:mm") %></td>
        </tr>
<%	} %>        

      </table>
      <!----------------- 테이블 끝 ----------------->
      <br>
    </td>
  </tr>
  
  
  <br>
  
</table>
</body>
</html>
