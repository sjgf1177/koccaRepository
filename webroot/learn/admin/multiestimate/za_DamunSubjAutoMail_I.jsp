<%
/**
 * file name : za_DamunSubjAutoMail_I.jsp
 * date      : 2003/08/20
 * programmer:
 * function  : 다면평가 문항관리 - 디테일페이지
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.multiestimate.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }
    
	String  v_subj = box.getString("s_subjcourse");
	String  v_grcode = box.getString("s_grcode");
	String  v_gyear = box.getString("s_gyear");
	String  v_subjseq = box.getString("s_subjseq");
    
    String v_schecks = "";
	String v_userids = "";
	String v_names = "";
	Vector  v_checks = box.getVector("p_checks");
    StringTokenizer st = null;

    String  v_damunpapernum = box.getString("s_damunpapernum");
    int  v_mailnum = box.getInt("p_mailnum");

	String  v_mailgubun = box.getString("p_mailgubun");
	
    String v_reloadlist = box.getString("p_reloadlist");
%>
<html>
<head>
<title>다면평가문제관리-등록</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
function autoSetting(){
  document.form2.p_process.value = 'DamunMailSendPage';
  document.form2.p_action.value  = 'go';
  document.form2.p_reloadlist.value = 'true';
  document.form2.submit();
}

<% //if (v_reloadlist.equals("true")) { %>
//opener.ReloadPage();
<% //} %>
function onload() {
  window.self.close();
}


-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" <%=v_onload%>>
      <form name="form2" method="post" action="/servlet/controller.multiestimate.DamunSubjMailServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="p_subj"    value="<%=v_subj%>">
        <input type="hidden" name="p_grcode"  value="<%=v_grcode%>">
        <input type="hidden" name="p_gyear"  value="<%=v_gyear%>">
        <input type="hidden" name="p_subjseq"  value="<%=v_subjseq%>">
        <input type="hidden" name="p_damunpapernum"  value="<%=v_damunpapernum%>">
        <input type="hidden" name="s_damunpapernum"  value="<%=v_damunpapernum%>">
        <input type="hidden" name="p_mailnum"  value="<%=v_mailnum%>">
        <input type="hidden" name="p_mailgubun"     value="<%=v_mailgubun%>">
        <input type="hidden" name="p_reloadlist"  value="">
<%
    for(int i=0;i<v_checks.size();i++) {              
      v_schecks = (String)v_checks.elementAt(i);
      st = new StringTokenizer(v_schecks,"|");
	    
        if(st.hasMoreTokens()) v_userids = (String)st.nextToken();
        if(st.hasMoreTokens()) v_names = (String)st.nextToken();
%>
  <input type='hidden' name = 'p_checks' value='<%=v_schecks%>'>
  <input type='hidden' name = 'p_userids' value='<%=v_userids%>'>
  <input type='hidden' name = 'p_names' value='<%=v_names%>'>
<%  
   }
%>

<table width="660" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
	 <!----------------- 타이틀시작 ----------------->
	      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/multiestimate/m_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
	  <!----------------- 타이틀 끝 ----------------->
     <br>
      <!----------------- 과정명 시작 ----------------->
      <table width="97%" border="0" cellspacing="1" cellpadding="0" class=table_out>
	  <tr>
          <td colspan="2" class="table_top_line"></td>
        </tr>
        <tr>
          <td width="61%" class="table_title" colspan="2">대상자</td>
          <td width="40%" class="table_02_2">
              <input type="radio" name="p_daesang" value="1">
            지정 
            &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="p_daesang" value="2" checked>
            지정안함</td>
        </tr>
        <tr>
          <td class="table_title" rowspan="3">관찰자</td>
          <td class="table_title">상사</td>
          <td class="table_02_2"><input type="radio" name="p_sang" value="1">
            지정 
            &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="p_sang" value="2" checked>
            지정안함</td>
        </tr>
        <tr>
          <td class="table_title">동료</td>
          <td class="table_02_2"><input type="radio" name="p_dong" value="1">
            지정 
            &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="p_dong" value="2" checked>
            지정안함</td>
        </tr>
        <tr>
          <td class="table_title">부하</td>
          <td class="table_02_2"><input type="radio" name="p_buha" value="1">
            지정 
            &nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="p_buha" value="2" checked>
            지정안함</td>
        </tr>
      </table>
      <br>

	  <table cellspacing="0" cellpadding="0" >
        <tr>
          <td><a href="javascript:autoSetting()"><img src="/images/admin/button/btn_jijung.gif" border="0"></a></td>
		  <td width=8></td>
		  <td><a href="javascript:self.close()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록 버튼 끝 ----------------->
      <br>
    </td>
  </tr>


  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     
</form>
</table>
</body>
</html>
