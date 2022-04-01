<%
//**********************************************************
//  1. 제      목: 외주관리 시스템 회사별게시판 상세화면
//  2. 프로그램명: zu_CpCompany_R.jsp
//  3. 개      요: 외주관리 시스템 회사별게시판 상세화면
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

String v_title = "", v_content = "", v_name = "", v_userid = "", v_indate = "";

int v_seq = box.getInt("p_seq");
String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
String v_select = box.getString("p_select"); 
int v_pageno = box.getInt("p_pageno");

Vector v_realfileVector = null;
Vector v_savefileVector = null;
String v_realmotion = "";
String v_savemotion = "";

DataBox dbox = (DataBox)request.getAttribute("selectPds");

v_userid   = dbox.getString("d_userid");

v_name   = dbox.getString("d_name");

v_indate    = dbox.getString("d_indate");
v_title    = dbox.getString("d_title");
v_seq      = dbox.getInt("d_seq");
v_content = dbox.getString("d_content");
//------------------------------------------------------------------------------------------------------
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>외주관리시스템자유게시판</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/gate_style.css">
	<script language = "javascript">
            function selectList() {
            	document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
            	document.form1.p_process.value = "";     
            	document.form1.submit();	
            }            
            function updatePage() {
            	document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
            	document.form1.p_process.value = "updatePage";
            	document.form1.submit();
            }    
            function deleteAction() {
                if (confirm("정말로 삭제하시겠습니까?")) {
                    document.form1.action = "/servlet/controller.cp.CpCompanyServlet";
                    document.form1.p_process.value = "delete";
                    document.form1.submit();
                }
                else {
                    return;
                }
            }
	</script>
</head>

<body leftmargin=0 topmargin=0>
  
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td><%@include file="zu_Include.jsp"%></td>
  </tr>
</table>
<table width="950" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td width="907" bgcolor="EFEFEA">
	<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000" codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0" width="830" height="120">
              <param name="movie" value="/images/gate/O_sub01.swf">
			  <param name=wmode value=transparent>
              <param name="quality" value="high">
              <embed src="/images/gate/O_sub02.swf" quality="high" pluginspage="http://www.macromedia.com/go/getflashplayer" type="application/x-shockwave-flash" width="830" height="120"></embed></object>
			  </td>
    <td class=O_bgline></td>
  </tr>
  <tr>
    <td>&nbsp;</td>
    <td class=O_bgline></td>
  </tr>
  <tr>
    <td><table width="949" border="0" cellspacing="0" cellpadding="0">
	 <form name = "form1" method = "post">
     <input type = "hidden" name = "p_seq"     value = "<%= v_seq %>">
     <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
     <input type = "hidden" name = "p_select"  value = "<%= v_select %>">
     <input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">
     <input type = "hidden" name = "p_process"  value = "">
     <input type = "hidden" name = "p_userid" value = "<%= v_userid %>">
        <tr> 
          <td width="25">&nbsp;</td>
          <td width="85" valign="top"><img src="/images/gate/O_title03.gif" width="85" height="80"></td>
          <td width="25">&nbsp;</td>
          <td width="775"><table width="775" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="11"><img src="/images/gate/O_boardview_t.gif" width="11" height="65"></td>
                <td width="754" class=O_boardview_title><table width="100%" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                     
					  <td width="11%" height="29" class = "table-title" align = "center">날짜</td>
                      <td width="1%"><img src="/images/gate/L_board_Tg.gif" width="1" ></td>
                      <td class = "table-content" width = "61%" align = "left" style="padding-left:10">&nbsp;&nbsp;<%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
					 
                                        
					  
                     <td width="1%"><img src="/images/gate/L_board_Tg.gif" width="1" ></td>
                      <td class = "table-title" width = "9%" align = "center">작성자</td>
                       <td width="1%"><img src="/images/gate/L_board_Tg.gif" width="1" ></td>
                      <td class = "table-content" width = "16%" align = "left">&nbsp;&nbsp;<%= v_name %></td>
					  
                                        
                    </tr>
                    <tr> 
					<td class = "table-title"  align = "center">제목</td>
                      <td><img src="/images/gate/L_board_Tg.gif" width="1" ></td>
                      <td class = "table-content"  align = "left" style="padding-left:10">&nbsp;&nbsp;<%= v_title %></td>
                      <td>&nbsp;</td>
                      
					  <td class=b_titleview>&nbsp;</td>
                      <td>&nbsp;</td>
                      <td></td>
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
                      <td><table border = "0" cellpadding = "4" cellspacing = "0" width = "100%">
                    <tr>
                    <td width = "100%"><%= StringManager.replace(v_content,"&amp;","&") %>
					
					</td>
                    </tr>
                    </table> 
					</td>
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
              <tr align="right"> 
                <td colspan="3"><a href="O_m01.jsp" target="_self" onfocus=this.blur()>
				<a href = "javascript:updatePage()"><img src="/images/gate/btn_modify.gif" width="50" height="22" border="0"></a>
				<a href = "javascript:deleteAction()"><img src="/images/gate/btn_delete.gif" width="50" height="22" border="0"></a>
				<a href = "javascript:selectList()"><img src="/images/gate/btn_list.gif" width="74" height="22" border="0"></a></td>
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
		</form>
      </table></td>
    <td class=O_bgline></td>
  </tr>
  
</table>

</body>
</html>
