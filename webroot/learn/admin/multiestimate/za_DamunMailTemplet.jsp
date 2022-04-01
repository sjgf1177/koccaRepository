<%
/**
 * file name : za_DamunMailTemplet.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : 다면 메일 템플릿
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_subj      = box.getString("p_subj");
    String  v_grcode    = box.getString("s_grcode");
    String  v_gyear     = box.getString("s_gyear");
    String  v_subjseq   = box.getString("s_subjseq");
    String  v_luserid   = box.getSession("userid");
    String  v_damunpapernum    = box.getString("p_damunpapernum");
    String  v_damunpapernm     = box.getString("p_damunpapernm");
	Vector  v_checks = box.getVector("p_checks");
%> 
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/mail_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">
<!--
function insertOB(){
 if(confirm('메일발송을 등록하시겠습니까?')){
	  document.form1.p_process.value = 'DamunMailInsert';
	  document.form1.submit();
  }
}
//-->
</script>
<body leftmargin="20" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
	  <br>
      <table cellpadding="0" cellspacing="0" class="heed_table">
        <tr>
		  <td width="3%" align=right><img src="/images/admin/common/icon.gif" ></td>
          <td valign="bottom" class=sub_title><b>다면평가 메일 폼 해설 등록</b></td>
        </tr>
      </table>
	  <br>
<form name=form1 method=post action='/servlet/controller.multiestimate.DamunSubjMemberServlet'>
        <input type="hidden" name="p_subj"       value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"       value="<%=v_grcode%>">
        <input type="hidden" name="p_gyear"       value="<%=v_gyear%>">
        <input type="hidden" name="p_subjseq"    value="<%=v_subjseq%>">
        <input type="hidden" name="p_damunpapernum"      value="<%=v_damunpapernum%>">
        <input type="hidden" name="p_damunpapernm"       value="<%=v_damunpapernm%>">
        <input type="hidden" name="p_process"       value="">
                
		<%for(int i=0; i<v_checks.size(); i++){%>
        <input type="hidden" name="p_checks"       value="<%=v_checks.get(i)%>">
		<%}%>
<!-- mail topimg -->
<table width="600" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td><img src="/images/mail/1topimg.jpg"></td>
  </tr>
</table>
<!--mail  -->

<table width="600" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td valign="top" background="/images/mail/bg_mail.gif" class="mail_text">
	<!-- 타이틀 -->
	<table width="449" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td><img src="/images/mail/text_test1.gif" align="absmiddle" ><%=v_damunpapernm%></td>
        </tr>
        <tr> 
          <td><img src="/images/mail/line.gif" width="442" height="8"></td>
        </tr>
      </table>
      
      <!-- 다면평가설명 -->
      <table width="442" border="0" cellpadding="0" cellspacing="0" bgcolor="F1F7F6">
        <tr> 
          <td height="5" valign="top" bgcolor="#FFFFFF"></td>
        </tr>
        <tr> 
          <td valign="top"><img src="/images/mail/box_top.gif"></td>
        </tr>
        <tr> 
          <td class="mail_box">
		  <textarea name="p_damuntext"  cols="50" rows="2"></textarea>
		  <br></td>
        </tr>
        <tr> 
          <td valign="bottom"><img src="/images/mail/box_bo.gif"></td>
        </tr>
      </table>
      <!-- 조사대상자정보 -->
      <table width="442" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="2">&nbsp;</td>
        </tr>
        <tr> 
          <td colspan="2"><img src="/images/mail/text_test2.gif" align="absmiddle">(대상자와의 
            관계 : )</td>
        </tr>
        <tr> 
          <td colspan="2"><img src="/images/mail/line.gif" width="442" height="8"></td>
        </tr>
        <tr> 
          <td height="5" colspan="2"></td>
        </tr>
        <tr> 
          <td width="211"><img src="/images/mail/bl_01.gif" align="absmiddle"> 
            <strong>ID : </strong></td>
          <td width="231"><img src="/images/mail/bl_01.gif" align="absmiddle"> <strong>성명 
            : </strong></td>
        </tr>
        <tr> 
          <td height="5" colspan="2"></td>
        </tr>
        <tr> 
          <td><img src="/images/mail/bl_01.gif" align="absmiddle"> <strong>회사명 
            : </strong></td>
          <td><img src="/images/mail/bl_01.gif" align="absmiddle"> <strong>직위 
            : </strong></td>
        </tr>
        <tr> 
          <td colspan="2">&nbsp;</td>
        </tr>
      </table>
      <table width="442" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td>&nbsp;</td>
        </tr>
        <tr>
          <td align="center"><img src="/images/mail/btn_click.gif" border="0"></td>
        </tr>
      </table>
	  </td>
  </tr>
</table>
<!-- bottomimg -->
<table width="600" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="95" valign="top" background="/images/mail/1bottom.jpg" class="bottom">문의 
      ☎ 080-224-9696 &nbsp;&nbsp;&nbsp;000@autoeversystems.com<br>
      상담응대시간 - [평 일] AM 8:00 ~ PM 19:30 <br>
      &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
      [토요일] AM 8:00 ~ PM 13:00 <br></td>
  </tr>
</table>

<br>
      <table align=center cellspacing="0" cellpadding="0" >
        <tr> 
          <td align="center">
          <a href="javascript:insertOB()"><img src="/images/admin/button/btn_apply.gif" border="0"></a>&nbsp;
		  <a href="javascript:window.close()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a>
		  </td>
        </tr>
    </table>
</form>
</body>
</html>
