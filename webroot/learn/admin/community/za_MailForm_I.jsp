<%
//**********************************************************
//  1. 제      목: 메일입력페이지
//  2. 프로그램명 : zu_MsPopNotice.jsp
//  3. 개      요: 메일입력페이지
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.07.01 
//  7. 수      정: 2005.07.01 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process         = box.getStringDefault("p_process","gomsnoticePop");
    String  v_token_cmuno     = box.getString("p_token_cmuno");
    String  v_close_fg        = box.getString("p_close_fg");
    String  v_okflag        = box.getStringDefault("okflag","N");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");


    String  content      = box.getString("content");
    String  width = "540";
    String  height = "200";
%>  
<html>
<head>
<%if("1".equals(v_close_fg)){%>
<title>커뮤니티승인</title>
<%}else {%>
<title>커뮤니티승인거부</title>
<%}%>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../css/user_style_community2.css" rel="stylesheet" type="text/css">
</head>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
<%if("Y".equals(v_okflag)){%>
    opener.goPage(1);
    this.close();
<%}%>

//저장
function uf_insertOK() {
    if (document.form1.p_title.value == "") {
        alert("제목을 입력하세요");
        document.form1.p_title.focus();
        return;
    }
    document.form1.content.value = document.form1.Wec.MIMEValue;
    document.form1.content.value.replace("&","&amp;");
    if (document.form1.Wec.Value.length < 3) {
        alert("내용을 입력하세요");
        return;
    }
    document.form1.action = "/servlet/controller.community.CommunityAdminRoomServlet";
    document.form1.p_process.value = "acceptData";
    document.form1.submit();

}

//-->
</script>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_token_cmuno"      value = "<%=v_token_cmuno%>">
<input type = "hidden" name = "p_close_fg"      value = "<%=v_close_fg%>">
	<input type = "hidden" name = "content"   value = "<%=content%>">
<input type = "hidden" name = "p_pgm"      value = "popnotice">

<table width="800" height="210" border="0" cellpadding="0" cellspacing="0">
  <tr> 
    <td colspan="2" valign="top" background="../../images/community/pop_bg.gif" style="padding-left:6px; padding-top:6px; padding-right:6px;"> 
      <table width="100%" border="1" cellspacing="0" 
                         cellpadding="5"   style="border-collapse:collapse;" bordercolor="#B7BEC1">
        <tr> 
          <td align="center" valign="top" bgcolor="#FFFFFF"> 
            <!-- 제목 -->
            <table width="97%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="1" bgcolor="CCCCCC"></td>
              </tr>
              <tr> 
                <td><table width="243" border="0" cellspacing="0" cellpadding="0">
<%if("1".equals(v_close_fg)){%>
                    <tr> 
                      <td valign="bottom" class="title"><strong><img src="../../images/community/pop_bl.gif" align="absmiddle"><img src="../../images/admin/community/tit_comm_permit.gif" align="absmiddle"></strong><img src="../../images/community/title_vline.gif" align="absbottom"></td>
                    </tr>
<%}else {%>
                    <tr> 
                      <td valign="bottom" class="title"><strong><img src="../../images/community/pop_bl.gif" align="absmiddle"><img src="../../images/admin/community/tit_comm_permitno.gif" align="absmiddle"></strong><img src="../../images/community/title_vline.gif" align="absbottom"></td>
                    </tr>
<%}%>

                  </table></td>
              </tr>
              <tr> 
                <td height="1" bgcolor="CCCCCC"></td>
              </tr>
              <tr> 
                <td height="20"></td>
              </tr>
            </table>
            <table width="97%" border="0" cellpadding="0" cellspacing="0">
              <tr> 
                <td align="center" valign="top"> 
                  <!-- 초대내용쓰기 -->
                  <table width="98%" border="2" cellspacing="0" 
            cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
                    <tr>
                      <td class="table_title">제목</td>
                      <td class="table_02_2" ><input name="p_title" type="text" class="input2" size="55"></td>
                    </tr>
                    <tr> 
                      <td class="table_title">내용</td>
      <td class="table_02_2" style=padding-top:5;padding-bottom:5>
             <!-- 나모 Editor  -->
             <p align="center">
				<script language = "javascript" src = "/script/user_patch.js"></script>
                <script language='javascript'>object_namopatch('650','280');</script>
             <!--OBJECT WIDTH=0 HEIGHT=0 CLASSID="clsid:5220cb21-c88d-11cf-b347-00aa00a28331">
                <PARAM NAME="LPKPath" VALUE="/namo/NamoWec5_Bobs_Consulting.lpk">
             </OBJECT>
             <OBJECT ID="Wec" WIDTH="650" HEIGHT="280"
             CLASSID="CLSID:DB1A8D98-97E8-4a42-9624-0E4BCD77F109" CODEBASE="/namo/NamoWec.cab#version=5,0,0,69">
             <param name="InitFileURL" value="/namo/namowec.env">
             <param name="InitFileVer" value="1.0">
             </OBJECT-->
             </p>
             <!-- 나모 Editor  --> 
      </td>
                    </tr>
                  </table></td>
              </tr>
              <tr> 
                <td height="20"></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td width="411" height="38" valign="top" background="../../images/community/pop_bobg.JPG"><img src="../../images/community/pop_backimg.JPG"></td>
    <td width="109" background="../../images/community/pop_bobg.JPG"><a href="javascript:uf_insertOK();"><img src="../../images/user/button/btn_confirm.gif" border="0"></a> 
      <a href="javascript:this.close();"><img src="../../images/user/button/btn_cancel.gif" border="0"></a> 
    </td>
  </tr>
</table>
</form>
</body>
</html>
