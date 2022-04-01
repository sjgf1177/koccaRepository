<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 Q&A상세화면
//  2. 프로그램명: zu_CpQna_R.jsp
//  3. 개      요: 외주관리 시스템 Q&A상세화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.cp.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_title = "", v_contents = "", v_inuserid = "", v_indate = "" , v_types = "";

int v_seq = box.getInt("p_seq");
String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
String v_select = box.getString("p_select"); 
int v_pageno = box.getInt("p_pageno");

Vector v_realfileVector = null;
Vector v_savefileVector = null;
String v_realmotion = "";
String v_savemotion = "";

DataBox dbox = (DataBox)request.getAttribute("selectQna");

v_inuserid   = dbox.getString("d_inuserid");
v_types = dbox.getString("d_types");
v_indate    = dbox.getString("d_indate");
v_title    = dbox.getString("d_title");
v_seq      = dbox.getInt("d_seq");
v_contents = dbox.getString("d_contents");
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");
v_realmotion  = dbox.getString("d_realmotion");
v_savemotion  = dbox.getString("d_savemotion");
%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>외주관리시스템</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/gate_style.css">
<script language="JavaScript" type="text/JavaScript">
<!--
function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function selectList() {
   	document.form1.action = "/servlet/controller.cp.CpQnaServlet";
	document.form1.p_process.value = "";     
	document.form1.submit();	
}            
function updatePage() {
	document.form1.action = "/servlet/controller.cp.CpQnaServlet";
	document.form1.p_process.value = "updatePage";
	document.form1.submit();
}
function replyPage() {
	document.form1.action = "/servlet/controller.cp.CpQnaServlet";
	document.form1.p_process.value = "replyPage";
	document.form1.submit();
}      
function deleteAction() {
	if (confirm("정말로 삭제하시겠습니까?")) {
		document.form1.action = "/servlet/controller.cp.CpQnaServlet";
		document.form1.p_process.value = "delete";
		document.form1.submit();
	}
	else {
		return;
	}
}
function faqList() {
<% if (session.getAttribute("userid")==null) {%>     
  if( getCookie( "userid_cp" ) == "" ) {
    alert("로그인을 하셔야 사용 가능합니다");
  } else {
	
  }
<% } else { %>
	document.form1.p_process.value   = "selectList";
	document.form1.action            = "/servlet/controller.cp.CpFaqServlet";
	document.form1.submit()
<% } %>
}

function adminList() {
	document.form1.action = "/servlet/controller.cp.CpAdminServlet";
	document.form1.p_process.value    = "adminiList";
	document.form1.submit();
}
//-->
</script>
</head>

<body leftmargin=0 topmargin=0 onLoad="MM_preloadImages('/images/gate/O_help_m01_on.gif','/images/gate/O_help_m02_on.gif','/images/gate/O_help_m03_on.gif');">
 <form name = "form1" method = "post">
	<input type = "hidden" name = "p_seq"     value = "<%= v_seq %>">
     <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
     <input type = "hidden" name = "p_select"  value = "<%= v_select %>">
     <input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">
     <input type = "hidden" name = "p_process"  value = "">
     <input type = "hidden" name = "p_userid" value = "<%= v_inuserid %>">
	 <input type = "hidden" name = "p_types"  value = "<%=v_types%>">
	

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><%@include file="zu_Include.jsp"%></td>
  </tr>
</table>
<table width="950" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="907" bgcolor="EFEFEA"><object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="830" height="120">
          <param name="movie" value="/images/gate/O_sub03.swf">
          <param name=wmode value=transparent>
          <param name="quality" value="high">
          <embed src="/images/gate/O_sub03.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="830" height="120"></embed></object></td>
    <td class=O_bgline></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class=O_bgline></td>
  </tr>
  <tr>
    <td><table width="949" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="25">&nbsp;</td>
          <td width="85" valign="top"><table width="85" border="0" cellpadding="0" cellspacing="0" background="/images/gate/O_help_mbg.gif">
              <tr> 
                <td><img src="/images/gate/O_help_top.gif" width="85" height="17"></td>
              </tr>
              <tr> 
                <td height=30><a href="javascript:faqList()" target="_self" onfocus=this.blur() onMouseOver="MM_swapImage('sub01','','/images/gate/O_help_m01_on.gif',1)" onMouseOut="MM_swapImgRestore()"><img src="/images/gate/O_help_m01.gif" name="sub01" width="85" height="20" border="0"></a></td>
              </tr>
              <tr> 
                <td height=30><a href="javascript:selectList()"><img src="/images/gate/O_help_m02_on.gif" name="sub02" width="85" height="20" border="0"></a></td>
              </tr>
              <tr> 
                <td height=30><a href="javascript:adminList()" target="_self" onfocus=this.blur() onMouseOut="MM_swapImgRestore()" onMouseOver="MM_swapImage('sub03','','/images/gate/O_help_m03_on.gif',1)"><img src="/images/gate/O_help_m03.gif" name="sub03" width="85" height="20" border="0"></a></td>
              </tr>
              <tr>
                <td><img src="/images/gate/O_help_bottom.gif" width="85" height="40"></td>
              </tr>
            </table></td>
          <td width="25">&nbsp;</td>
          <td width="775"> <table width="775" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="11"><img src="/images/gate/O_boardview_t.gif" width="11" height="65"></td>
                <td width="754" class=O_boardview_title><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="11%" height="29" class=b_titleview>날짜</td>
                      <td width="1%"><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                      <td width="61%" style="padding-left:10">&nbsp;&nbsp;<%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td><td width="1%"><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                      <td width="9%" class=b_titleview>작성자</td>
                      <td width="1%"><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                      <td width="16%" style="padding-left:10">&nbsp;&nbsp;<%= v_inuserid %></td>
                    </tr>
                    <tr> 
                      <td class=b_titleview>제목</td>
                      <td><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                      <td style="padding-left:10">&nbsp;&nbsp;<%= v_title %></td>
                      <td><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                      <td class=b_titleview>첨부파일</td>
                      <td><img src="/images/gate/L_board_Tg.gif" width="1"></td>
                      <td style="padding-left:10">
                                <%   for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
                                   String v_realfile = (String)v_realfileVector.elementAt(i);
                                   String v_savefile = (String)v_savefileVector.elementAt(i);
//								   System.out.println("v_realfile = " + v_realfile);
								if(v_realfile == null || v_realfile.equals("") ) {  %>
                                 
                        <%        } else { %>
						&nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
								 <img src="/images/gate/icon_file.gif" border="0"></a><br>
                                 <input type = "hidden" name = "p_savefile"  value = "<%= v_savefile%>">
						<%}
                                }   %></td>
                    </tr>
                  </table></td>
                <td width="10"><img src="/images/gate/O_boardview_t.gif" width="10" height="65"></td>
              </tr>
              <tr> 
                <td height=8></td>
                <td height=8></td>
                <td height=8></td>
              </tr>
            </table>
            <table width="775" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="20"><img src="/images/gate/boardbox_c01.gif" width="20" height="20"></td>
                <td width="735" class=b_viewbox_T>&nbsp;</td>
                <td width="20"><img src="/images/gate/boardbox_c02.gif" width="20" height="20"></td>
              </tr>
              <tr> 
                <td class=b_viewbox_L>&nbsp;</td>
                <td align="center"><table width="690" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="7%" height=8></td>
                    </tr>
                    <tr> 
                      <td><%= StringManager.replace(v_contents,"&amp;","&") %></td>
                    </tr>
                    <tr> 
                      <td>&nbsp;</td>
                    </tr>
                  </table></td>
                <td class=b_viewbox_R>&nbsp;</td>
              </tr>
              <tr> 
                <td><img src="/images/gate/boardbox_c03.gif" width="20" height="20"></td>
                <td class=b_viewbox_B>&nbsp;</td>
                <td><img src="/images/gate/boardbox_c04.gif" width="20" height="20"></td>
              </tr>
              <tr> 
                <td height=8></td>
                <td height=8></td>
                <td height=8></td>
              </tr>
              <tr> 
                <td colspan="3"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="100%" align="right" valign="middle"><a href="javascript:replyPage()" target="_self" onfocus=this.blur()><img src="/images/gate/btn_reply.gif" width="50" height="22" border="0"></a></td>
                      <td width="1%">&nbsp;</td>
                      <td width="50" align="right" valign="middle"><a href="javascript:updatePage()" target="_self" onfocus=this.blur()><img src="/images/gate/btn_modify.gif" width="50" height="22"border="0"></a></td>
                      <td width="1%">&nbsp;</td>
                      <td width="50" align="right" valign="middle"><a href="javascript:deleteAction()" target="_self" onfocus=this.blur()><img src="/images/gate/btn_delete.gif" width="50" height="22" border="0"></a></td>
                      <td width="1%">&nbsp;</td>
                      <td width="74" align="right" valign="middle"><a href="javascript:selectList()" target="_self" onfocus=this.blur()><img src="/images/gate/btn_list.gif" width="74" height="22" border="0"></a></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
          <td width="10">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25" height="50">&nbsp;</td>
          <td height="50">&nbsp;</td>
          <td width="25" height="50">&nbsp;</td>
          <td width="775" height="50">&nbsp;</td>
          <td height="50">&nbsp;</td>
        </tr>
        <tr> 
          <td width="25">&nbsp;</td>
          <td colspan="3"><img src="/images/gate/O_copyright.gif" width="370" height="41"></td>
          <td>&nbsp;</td>
        </tr>
      </table></td>
    <td class=O_bgline></td>
  </tr>
</table>
</form>
</body>
</html>
