<%
/**
 * file name : za_ETestMail_I.jsp
 * date      : 2003/08/20
 * programmer:
 * function  : E-Test 메일 등록 페이지
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.etest.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

    String  ss_upperclass= box.getString("s_upperclass");
    String  ss_etestsubj      = box.getString("s_etestsubj");
    String  ss_gyear       = box.getString("s_gyear");
    String  ss_etestcode    = box.getString("s_etestcode");
    String  v_etestsubjnm = box.getString("p_etestsubjnm");
    String  v_etesttext = box.getString("p_etesttext");
    String  v_action     = box.getString("p_action");
    String  v_process = box.getString("p_process");	

    String v_schecks = "";
	String v_userids = "";
	String v_names = "";
	Vector  v_checks = box.getVector("p_checks");
    StringTokenizer st = null;
   
    String v_reloadlist = box.getString("p_reloadlist");

	DataBox dbox0 = (DataBox)request.getAttribute("ETestMasterData");

%>
<html>
<head>
<title>ETEST 메일 발송</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
function previewPage(){
  if (blankCheck(document.form2.p_title.value)) {
    alert('메일 제목을 입력하십시요.');
	document.form2.p_title.focus();
    return;
  }
 if (realsize(document.form2.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form2.p_title.focus();
            return;
  }
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = 'ETestMailPreviewPage';
    document.form2.p_action.value = "go";
    document.form2.action = "/servlet/controller.etest.ETestMailServlet";
    document.form2.p_content.value = document.form2.we.BodyValue;
    document.form2.submit();
}
function sendMail(){
  if (blankCheck(document.form2.p_title.value)) {
    alert('메일 제목을 입력하십시요.');
	document.form2.p_title.focus();
    return;
  }
 if (realsize(document.form2.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form2.p_title.focus();
            return;
  }
    document.form2.target = "openWinInsert"
    document.form2.p_process.value = 'ETestMailSend';
    document.form2.p_reloadlist.value = 'true';
    document.form2.p_action.value = "go";
    document.form2.action = "/servlet/controller.etest.ETestMailServlet";
    document.form2.p_content.value = document.form2.we.BodyValue;
    document.form2.submit();
}
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage();
<% } %>
function onload() {
  window.self.close();
}
//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" <%=v_onload%>>

<table width="667" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
     <br>
      <!----------------- 과정명 시작 ----------------->
      <table cellpadding="0" cellspacing="0" class="table1">
        <tr> 
          <td><b>E-Test 안내 메일<b></td>
        </tr>
		<tr>
          <td>테스트명 : <%=dbox0.getString("d_etesttext")%></td>
        </tr>
		<tr>
          <td>테스트기간 : <%=FormatDate.getFormatDate(dbox0.getString("d_startdt"),"yyyy/MM/dd hh:mm")%> ~ <%=FormatDate.getFormatDate(dbox0.getString("d_enddt"),"yyyy/MM/dd hh:mm")%></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명 끝 ----------------->

      <!----------------- 문제등록 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
      <form name="form2" method="post" action="/servlet/controller.research.SulmunTargetMailServlet">
        <input type="hidden" name="p_process" value="">
        <input type="hidden" name="p_action"  value="">
        <input type="hidden" name="s_etestsubj"       value="<%=ss_etestsubj%>">
        <input type="hidden" name="p_etestsubjnm"       value="<%=v_etestsubjnm%>">
        <input type="hidden" name="s_gyear"       value="<%=ss_gyear%>">
        <input type="hidden" name="s_etestcode"    value="<%=ss_etestcode%>">
        <input type="hidden" name="s_upperclass"       value="<%=ss_upperclass%>">
        <input type="hidden" name="p_startdt"  value="<%=dbox0.getString("d_startdt")%>">
        <input type="hidden" name="p_enddt"  value="<%=dbox0.getString("d_enddt")%>">
        <input type="hidden" name="p_reloadlist"  value="">
<%
    for(int i=0;i<v_checks.size();i++) {              
      v_schecks = (String)v_checks.elementAt(i);
      st = new StringTokenizer(v_schecks,"|");
        v_userids = (String)st.nextToken();
        v_names = (String)st.nextToken();
%>
  <input type='hidden' name = 'p_checks' value='<%=v_schecks%>'>
  <input type='hidden' name = 'p_userids' value='<%=v_userids%>'>
  <input type='hidden' name = 'p_names' value='<%=v_names%>'>
<%
   }
%>
		<tr>
          <td colspan="4" class="table_top_line"></td>
        </tr>

		<tr>
          <td class="table_title_01">제목</td>
          <td class="table_02_1" valign="top">
            <input name="p_title" type="text" class="input" value="" size="60">
		  </td>
        </tr>
		<tr>
          <td class="table_title_01">내용</td>
          <td class="table_02_1" colspan="2"> 
							<script language = "javascript" src = "/script/user_patch.js"></script>
							<script language='javascript'>object_namopatch('530','360');</script>
					  <!--OBJECT WIDTH=0 HEIGHT=0 CLASSID="clsid:5220cb21-c88d-11cf-b347-00aa00a28331">
					  <PARAM NAME="LPKPath" VALUE="/learn/namo/NamoWec4_Namo.lpk">
					  </OBJECT>
                      <input type=hidden name=p_content value="">
                      <OBJECT ID="we" WIDTH="530" HEIGHT="360" CLASSID="CLSID:C5AC95C0-C873-445B-BA1F-3A430A4DEA42" CODEBASE="/learn/namo/NamoWec.cab#version=4,0,0,17">
                        <param name="InitFileURL" value="/learn/namo/namowec.env">
                      </OBJECT-->
          </td>
        </tr>
	   </table>

      </form>
	  <!----------------- 문제등록 끝 ----------------->
      <!----------------- 등록 버튼 시작 ----------------->
      <table cellspacing="0" cellpadding="0" >
        <tr>
		  <td><a href="javascript:previewPage()"><img src="/images/admin/button/btn_preview.gif" border="0"></a></td>
		  <td width=8></td>
          <td><a href="javascript:sendMail()"><img src="/images/admin/button/btn_send.gif" border="0"></a></td>
		  <td width=8></td>
          <td><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
        </tr>
      </table>
      <!----------------- 등록 버튼 끝 ----------------->
      <br>
    </td>
  </tr>

  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     

</table>
                              	<!-- IE 6.0 이하에서 필요-->
	<SCRIPT LANGUAGE="VBScript">
	<!--
		Function Load_Content()
				document.form2.we.BodyValue = document.form2.p_content.value
				document.form2.we.focus
		End Function

		Sub we_OnInitCompleted
			call Load_Content()
		End Sub

		Function Move_Content()
				document.form2.p_content.value = document.form2.we.BodyValue
		End Function
	-->
	</script>
</body>
</html>
