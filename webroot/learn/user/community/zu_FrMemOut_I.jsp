<%
//**********************************************************
//  1. 제      목: 커뮤니티 폐쇄 
//  2. 프로그램명 : zu_MsPopNotice.jsp
//  3. 개      요: 커뮤니티 폐쇄.
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
    String  v_process      = box.getStringDefault("p_process","updatememout");
    String  v_static_cmuno = box.getString("p_static_cmuno");
    String  v_cmuno        = box.getString("p_cmuno");

    String  s_userid       = box.getSession("userid");
    String  s_username     = box.getSession("name");

    String  content        = box.getString("content");
    String  width          = "600";
    String  height         = "200";

    //커뮤니티 타입정보
    String v_top_display_fg =( new CommunityCreateBean()).getSingleColumn(box.getString("p_cmuno"),"html_skin_fg");
    if(v_top_display_fg.equals("")) v_top_display_fg="1";
    String[] v_title_color={"","",""};
    if("1".equals(v_top_display_fg)){v_title_color[0]="line_color_blue";v_title_color[1]="tbl_dbtit1";v_title_color[2]="tbl_dbtit2";}
    if("2".equals(v_top_display_fg)){v_title_color[0]="line_color_green";v_title_color[1]="tbl_gtit1" ;v_title_color[2]="tbl_gtit2";}
    if("3".equals(v_top_display_fg)){v_title_color[0]="line_color_pink";v_title_color[1]="tbl_ptit1" ;v_title_color[2]="tbl_ptit2";}
    if("4".equals(v_top_display_fg)){v_title_color[0]="line_color_yellow";v_title_color[1]="tbl_ytit1" ;v_title_color[2]="tbl_ytit2";}
    if("5".equals(v_top_display_fg)){v_title_color[0]="line_color_violet";v_title_color[1]="tbl_vtit1" ;v_title_color[2]="tbl_vtit2";}

%>  
<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="../../css/user_style_community2.css" rel="stylesheet" type="text/css">
</head>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language="JavaScript" type="text/JavaScript">
<!--
<% if("0".equals(box.getString("p_flag"))){%>
    opener.location.replace("/servlet/controller.community.CommunityIndexServlet?p_process=selectmain");
    this.close();
<% }%>


//등록
function uf_cfmOK() {

    if(document.all.use_editor[0].checked) {        
        form1.content.value = ObjEditor.document.all.tags("html")[0].outerHTML;
    }else {
        form1.content.value = document.all.txtDetail.value;
    }

    document.form1.p_flag.value=0;
    document.form1.action = "/servlet/controller.community.CommunityIndexServlet";
    document.form1.p_process.value = "updatememout";
    document.form1.submit();
}
//-->
</script>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0">
<form name = "form1"    method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_userid"      value = "">
<input type = "hidden" name = "p_static_cmuno"      value = "<%=v_static_cmuno%>">
<input type = "hidden" name = "p_cmuno"      value = "<%=v_cmuno%>">
<input type = "hidden" name = "p_flag"      value = "1">
<input type = "hidden" name = "p_close_fg"      value = "2">
<table width="520" height="210" border="0" cellpadding="0" cellspacing="0">
 <tr> 
  <td colspan="2" valign="top" background="../../images/community/pop_bg.gif" style="padding-left:6px; padding-top:6px; padding-right:6px;"> 
   <table width="100%" border="1" cellspacing="0" cellpadding="5"   style="border-collapse:collapse;" bordercolor="#B7BEC1">
    <tr> 
     <td align="center" valign="top" bgcolor="#FFFFFF"> 
      <!-- 제목 -->
      <table width="97%" border="0" cellspacing="0" cellpadding="0">
       <tr><td height="1" bgcolor="CCCCCC"></td></tr>
       <tr> 
        <td>
         <table width="243" border="0" cellspacing="0" cellpadding="0">
          <tr> 
           <td valign="bottom" class="title"><strong><img src="../../images/community/pop_bl.gif" align="absmiddle">커뮤니티 폐쇄</strong><img src="../../images/community/title_vline.gif" align="absbottom"></td>
          </tr>
         </table>
        </td>
       </tr>
       <tr><td height="1" bgcolor="CCCCCC"></td></tr>
       <tr><td height="20"></td></tr>
       <tr> 
        <td align="center" ><font color="#CC3300">※</font> 커뮤니티 폐쇄 사유를 적어주세요.</td></tr>
       <tr>
        <td height="1" background="../../images/community/bg_line.gif"></td>
       </tr>
       <tr><td height="10"></td></tr>
      </table>
      <table width="97%" border="0" cellpadding="0" cellspacing="0">
       <tr> 
        <td align="center" valign="top"> 
         <!-- 초대내용쓰기 -->
         <table width="443" border="2" cellspacing="0" cellpadding="3"   style="border-collapse:collapse;" bordercolor="#ededed" frame="hsides">
          <tr  class="lcolor"><td height="3" class="<%=v_title_color[0]%>"></td></tr>
          <tr> 
           <td width="443" class="comm_contents"  ><%@ include file="/include/DhtmlEditor.jsp" %></td>
          </tr>
         </table>
        </td>
       </tr>
       <tr><td height="20"></td></tr>
      </table>
     </td>
    </tr>
   </table>
  </td>
 </tr>
 <tr> 
  <td width="411" height="38" valign="top" background="../../images/community/pop_bobg.jpg"><img src="../../images/community/pop_backimg.jpg"></td>
  <td width="109" background="../../images/community/pop_bobg.jpg"><a href="javascript:uf_cfmOK();"><img src="../../images/user/button/btn_confirm.gif" border="0"></a><a href="javascript;this.close();"><img src="../../images/user/button/btn_cancel.gif" border="0"></a></td>
 </tr>
</table>
</form>
</body>
</html>

