<%
/**
 * file name : za_ExamResultRetry_U.jsp
 * date      : 2005/08/20
 * programmer:
 * function  : 재응시 수정    
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String v_reloadlist = box.getString("p_reloadlist");  // opener reload
	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }
  
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/style_myhome.css">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function onload() {
  window.self.close();
}

<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>

// 재응시 수정
function fnRetry(){
  if(confirm('사용자 재응시횟수를 수정 하시겠습니까?')){
  
    // 숫자체크
    if(!numeric_chk(document.form3.p_userretrycnt)) return;
        
    if(parseInt(document.form3.p_userretrycnt.value) > parseInt(document.form2.p_retry.value)) {
        alert('시험지 재응시 횟수보다 큽니다.');return;
    }

  document.form2.p_process.value = 'ExamUserRetry';
  document.form2.p_userretrycnt.value = document.form3.p_userretrycnt.value;
  document.form2.submit();
  
  }
}
-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin=10 leftmargin=10 <%=v_onload%>>
<!----------------- 타이틀 시작 ----------------->
<table width="97%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
    <td  class=sub_title>사용자 재응시횟수 수정</td>
  </tr>
  <tr>
    <td height="3"></td>
  </tr>
</table>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td >

<form name="form2" method="post" action="/servlet/controller.exam.ExamUserServlet">
    <input type="hidden" name="p_subj"         value="<%=box.getString("p_subj")%>">
    <input type="hidden" name="p_gyear"        value="<%=box.getString("p_gyear")%>">
    <input type="hidden" name="p_subjseq"      value="<%=box.getString("p_subjseq")%>">
    <input type="hidden" name="p_lesson"       value="<%=box.getString("p_lesson")%>">
    <input type="hidden" name="p_examtype"     value="<%=box.getString("p_examtype")%>">
    <input type="hidden" name="p_papernum"     value="<%=box.getString("p_papernum")%>">
    <input type="hidden" name="p_userid"       value="<%=box.getString("p_userid")%>">
    <input type="hidden" name="p_retry"        value='<%=box.getString("p_retry")%>'>    
    <input type="hidden" name="p_process" >    
    <input type="hidden" name="p_userretrycnt" >        
</form>
<form name="form3" method="post" action="/servlet/controller.exam.ExamUserServlet">    
    <table border="0" cellpadding="0" cellspacing="0" width="480">
        <tr> 
          <td>
            <table width="100%" border="0" cellspacing="1" cellpadding="0" class=testbox_out>
              <tr> 
                <td class=testbox_con>[과정명] : <%=box.getString("p_subj")%> / [연도] : <%=box.getString("p_gyear")%> / [차수] : <%=box.getString("p_subjseq")%> / [시험지번호] : <%=box.getString("p_papernum")%> <br>
                </td>
              </tr>
            </table>
            
         </td>
        </tr>
		<tr> 
          <td height=8></td>
        </tr>


		<tr> 
          <td height=15 align=center>
               <input name="p_userretrycnt" type="text" class="input" size="5" value='<%=box.getString("p_userretry")%>'> (시험지 재응시 : <%=box.getString("p_retry")%>)
          </td>
        </tr>
        <tr> 
          <td class=dotline height=1></td>
        </tr>
		<tr> 
          <td height=15></td>
        </tr>
        <tr> 
          <td align="right"><table border="0" cellspacing="0" cellpadding="0">
                <td>
                <a href="javascript:fnRetry()"><img src="/images/admin/button/btn_apply.gif" border="0" ></a>&nbsp; 
                <a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0" ></a>
                </td>
              </tr>
            </table></td>
        </tr>
      </table>
      
      </form><!--폼-->
    </td>
  </tr>
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
</body>
</html>
