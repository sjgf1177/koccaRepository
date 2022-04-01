<%
/**
 * file name : za_ClassifySubject_U.jsp
 * date      : 2003/07/07
 * programmer:
 * function  : 과정분류코드 수정화면
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
<%
  RequestBox box = null;
  box = (RequestBox)request.getAttribute("requestbox");

  String v_upperclass  = box.getString("p_upperclass");
  String v_middleclass = box.getString("p_middleclass");
  String v_lowerclass  = box.getString("p_lowerclass");
  String v_classname   = box.getString("p_classname");
%>
<html>
<head><title>과정분류수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language="javascript">
  function  whenUpdate(){
    document.form1.p_process.value = "update";
    document.form1.submit();
  }
  function  whenDelete(){
	  if (confirm("과정분류정보를 삭제 하시겠습니까?")) {
  	  document.form1.p_process.value = "delete";
    	document.form1.submit();
    }
  }
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <table width="320" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
    <tr>
      <td>
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
          <tr>
            <td align="center" bgcolor="#FFFFFF">
              <!----------------- title 시작 ----------------->
      <table width="95%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/system/unite_title09-1.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
              <!----------------- title 끝 ----------------->
              <br>
              <!----------------- 과정분류 수정 시작 ----------------->
              <table cellspacing="1" cellpadding="5" class="table_out">
                <form name="form1" method="post" action="/servlet/controller.course.ClassifySubjectServlet">
                <input type=hidden name="p_process"     value="">
                <input type=hidden name="p_upperclass"  value="<%=v_upperclass%>">
                <input type=hidden name="p_middleclass" value="<%=v_middleclass%>">
                <input type=hidden name="p_lowerclass"  value="<%=v_lowerclass%>">
<%        String v_classtype="";
          String v_classcode="";
          if (v_middleclass.equals("000") && v_lowerclass.equals("000")) {
              v_classtype = "대";
              v_classcode = v_upperclass;
          } else if (!v_middleclass.equals("000") && v_lowerclass.equals("000")) {
              v_classtype = "중";
              v_classcode = v_middleclass;
          } else if (!v_middleclass.equals("000") && !v_lowerclass.equals("000")) {
              v_classtype = "소";
              v_classcode = v_lowerclass;
          } %>
                <tr>
                  <td class="table_top_line"></td>
                </tr>
                <tr>
                  <td height="26" class="table_02_1">
                    <table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr>
                        <td>* <%=v_classtype%>분류코드 : <%=v_classcode%></td>
                      </tr>
                      <tr>
                        <td height="8"></td>
                      </tr>
                      <tr>
                        <td>* <%=v_classtype%>분류코드명 :
                          <input type="text" class="input" name="p_classname" size=20 maxlength=40 value="<%=v_classname%>">
                        </td>
                      </tr>
                    </table>
                  </td>
                </tr>
                </form>
              </table>
              <!----------------- 과정분류 수정 끝 ----------------->
              <!----------------- 저장, 삭제, 닫기 버튼 시작 ----------------->
              <table  border="0" cellspacing="0" cellpadding="0">
			  <tr>
			  <td height=6></td>
			  <td height=6></td>
			  <td height=6></td>
			  <td height=6></td>
			  <td height=6></td>
			  </tr>
                <tr>
                  <td><a href="javascript:whenUpdate()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
				  <td width=8></td>
                  <td><a href="javascript:whenDelete()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
				  <td width=8></td>
                  <td><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
                </tr>
              </table>
              <!----------------- 저장, 삭제, 닫기 버튼 끝 ----------------->
              <br>
            </td>
          </tr>
        </table>
      </td>
    </tr>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

  </table>
</body>
</html>
