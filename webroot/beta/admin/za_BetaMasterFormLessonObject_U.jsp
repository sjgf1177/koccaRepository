<%
/**
 * file name : za_MasterFormLessonObject_U.jsp
 * date      : 2003/10/23
 * programmer: LeeSuMin
 * function  : OBC마스터폼 Lesson Object 연결정보 저장
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }
    
    String  p_subj   	= box.getString("p_subj");
    String  p_lesson   	= box.getString("p_lesson");
    String  p_oid   	= box.getString("p_oid");
    //int		p_branch   	= Integer.parseInt(box.getString("p_branch"));
    String	c_gadmin	= box.getSession("s_gadmin");
    
    String	p_process	= box.getString("p_process");

    
	MfLessonDataSub	ds	=(MfLessonDataSub)request.getAttribute("MfLessonDataSub");
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
</head>
<script language="javascript">
	function whenSubmit(){
		document.form1.submit();
	}
</script>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="95%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td background="/images/admin/beta/tit_masterform.gif"><img src="/images/admin/contents/contents_title13.gif" width="350" height="36" border="0"></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 과정 관리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
		<FORM name=form1 method="post" action="/servlet/controller.beta.BetaMasterFormServlet">
        	<input type=hidden name="p_subj" value="<%=p_subj%>">
        	<input type=hidden name="p_lesson" value="<%=p_lesson%>">
        	<input type=hidden name="p_oid" value="<%=p_oid%>">
        	<input type=hidden name="p_process" value="changeObjectSave">
			
        <tr> 
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="20%"><b>Title of Assigned Object</b></td>
          <td class="table_02_2"><input type="text" class="input" name="p_sdesc" size="60" maxlength="100" value="<%=ds.getSdesc()%>"></td>
        </tr>
        <tr>
          <td class="table_title"><b>Type of Assign</b></td>
          <td class="table_02_2">
          <%SelectParam param = new SelectParam("p_types", "", false, 1, box.getHttpSession());%>
          <%=SelectionUtil.getLevel1Code("0033", ds.getTypes(), param)%>
          </td>
        </tr>
		</FORM>
		
      </table>
      <!----------------- 과정 관리 끝 ----------------->
      <br>
      <!----------------- 저장 버튼 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td align="center" height="20">
          	<a href='javascript:whenSubmit()'><img src="/images/admin/homepage/save1_butt.gif" width="37" height="18" border="0"></a>&nbsp;&nbsp;
          	<a href='javascript:self.close();'><img src="/images/admin/contents/close1_butt.gif" width="37" height="18" border="0"></a>
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 저장 버튼 끝 ----------------->
      <br>
    </td>
  </tr>
</table>
</body>
</html>
<%@ include file = "/learn/library/getJspName.jsp" %>
