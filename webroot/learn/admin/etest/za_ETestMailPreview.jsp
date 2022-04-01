<%
/**
 * file name : za_ETestMailPreview.jsp
 * date      : 2003/09/12
 * programmer:
 * function  : 이테스트 메일 미리보기
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.etest.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_grcode    = box.getString("p_grcode");
    String  v_subj      = box.getString("p_subj");
    String  v_gyear      = box.getString("p_gyear");
	String  v_subjseq = box.getString("p_subjseq");
    int     v_sulpapernum = box.getInt("p_sulpapernum");
    String  v_sulpapernm= box.getString("p_sulpapernm");
    String  v_sulstart= box.getString("p_sulstart");
    String  v_sulend= box.getString("p_sulend");
    String  v_title= box.getString("p_title");
    String  v_content= box.getString("p_content");
    int  v_mailnum = box.getInt("p_mailnum");

    String v_schecks = "";
	String v_userids = "";
	String v_names = "";
	Vector  v_checks = box.getVector("p_checks");
    StringTokenizer st = null;

    String  v_fromEmail = box.getSession("email");
    String  v_fromName = box.getSession("name");
    String  v_comptel = box.getSession("comptel");

    String v_reloadlist = box.getString("p_reloadlist");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>ETest 안내 메일 미리보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/mail_style.css">
<script language="JavaScript">
<!--
function sendMail(){
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = 'ETestMailSend';
    document.form2.p_reloadlist.value = 'true';
    document.form2.p_action.value = "go";
    document.form2.action = "/servlet/controller.etest.ETestMailServlet";
    document.form2.submit();
}
function sulmunRight(){
  document.form2.p_process.value = 'SulmunUserPaperListPage';
  document.form2.action = "/servlet/controller.research.SulmunTargetUserServlet";
  document.form2.submit();
}
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage();
<% } %>
//-->
</script>
</head>

<body topmargin=0 leftmargin=0>
<form name="form2" method="post" action="/servlet/controller.research.SulmunTargetMailServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"    value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
        <input type="hidden" name="p_gyear"  value="<%=v_gyear%>">
        <input type="hidden" name="p_subjseq"  value="<%=v_subjseq%>">
        <input type="hidden" name="p_sulpapernum"  value="<%=v_sulpapernum%>">
        <input type="hidden" name="p_sulpapernm"  value="<%=v_sulpapernm%>">
        <input type="hidden" name="p_sulstart"  value="<%=v_sulstart%>">
        <input type="hidden" name="p_sulend"  value="<%=v_sulend%>">
        <input type="hidden" name="p_title"  value="<%=v_title%>">
        <input type="hidden" name="p_content"  value='<%=v_content%>'>
        <input type="hidden" name="p_mailnum"  value="<%=v_mailnum%>">
        <input type="hidden" name="p_reloadlist"  value="">
<%
    for(int i=0;i<v_checks.size();i++) {              
      v_schecks = (String)v_checks.elementAt(i);
      st = new StringTokenizer(v_schecks,"|");
        v_userids = (String)st.nextToken();
        v_names = (String)st.nextToken();
%>
  <input type='hidden' name = 'p_userids' value='<%=v_userids%>'>
  <input type='hidden' name = 'p_names' value='<%=v_names%>'>
<%
   }
%>
</form>
<table width="640" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><img src="/images/admin/research/Mform_top.gif" ></td>
  </tr>
  <tr> 
    <td class=sulmun_bg height=8>&nbsp;</td>
  </tr>
  <tr> 
    <td align="center" class=sulmun_bg><table width="600" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td class=sulmun_con><%=v_content%></td>
        </tr>
        <tr>
          <td height=8></td>
        </tr>
      </table>
      <table width="600" align="center" cellpadding="2" cellspacing="1" >
  <tr> 
    <td align=center>E-Test 시험 응시</td>
  </tr>
<!----------------- 제출 버튼 끝 ----------------->
            </form>
		   </table>		  

		  </td>
        </tr>

 <tr> 
    <td class=sulmun_bg>&nbsp;</td>
  </tr>
  <tr> 
    <td><img src="/images/admin/research/Mform_bottom.gif" ></td>
  </tr>
  <tr>
    <td><table cellspacing="0" cellpadding="0"  align="center">
        <tr> 
          <td><a href="javascript:history.go(-1)"><img src="/images/admin/button/btn_before.gif" border="0"></a></td>
		  <td width=8></td>
          <td><a href="javascript:sendMail()"><img src="/images/admin/button/btn_send.gif" border="0"></a></td>
		  <td width=8></td>
          <td><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
        </tr>
        <tr>
           <td></td>
           <td>&nbsp;</td> 
		   <td>&nbsp;</td> 
		   <td>&nbsp;</td> 
          <td>&nbsp;</td> 
        </tr>
      </table><%@ include file = "/learn/library/getJspName.jsp" %></td>
  </tr>
</table>
</body>
</html>
