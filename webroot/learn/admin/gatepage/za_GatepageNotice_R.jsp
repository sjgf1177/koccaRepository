<%
//**********************************************************
//  1. 제      목: 게이트시스템 공지사항 관리 상세화면
//  2. 프로그램명: zu_GatepageNotice_R.jsp
//  3. 개      요: 게이트시스템 공지사항 관리 상세화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 박준현 2004. 12. 18
//  7. 수      정: 박준현 2004. 12. 18
//***********************************************************
%>

<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.gatepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
RequestBox box = (RequestBox)request.getAttribute("requestbox");

String v_title = "", v_content = "", v_name = "", v_userid = "", v_indate = "", v_types = "";

int v_seq = box.getInt("p_seq");
String v_searchtext = box.getString("p_searchtext");            //  서버에 저장되있는 파일수 
String v_select = box.getString("p_select"); 
int v_pageno = box.getInt("p_pageno");

Vector v_realfileVector = null;
Vector v_savefileVector = null;
String v_realmotion = "";
String v_savemotion = "";

DataBox dbox = (DataBox)request.getAttribute("selectPds");

v_userid   = dbox.getString("d_aduserid");
v_name   = dbox.getString("d_adname");
v_indate    = dbox.getString("d_addate");
v_title    = dbox.getString("d_adtitle");
v_seq      = dbox.getInt("d_seq");
v_content = dbox.getString("d_adcontent");
v_realfileVector = (Vector)dbox.getObject("d_realfile");
v_savefileVector = (Vector)dbox.getObject("d_savefile");
v_realmotion  = dbox.getString("d_realmotion");
v_savemotion  = dbox.getString("d_savemotion");
v_types = dbox.getString("d_types");

String s_gubun = "";

s_gubun = box.getString("p_gubun");
System.out.println("상세보기pages_gubun*********************  " + s_gubun);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>GateSystem공지사항</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script language = "javascript">
<!--
            function selectList() {
            	document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
				document.form1.p_process.value = "selectList";     
            	document.form1.submit();	
            }            
            function updatePage() {
            	document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
				document.form1.p_process.value = "updatePage";
            	document.form1.submit();
            }    
            function deleteAction() {
                if (confirm("정말로 삭제하시겠습니까?")) {
                    document.form1.action = "/servlet/controller.gatepage.GatePageNoticeServlet";
					document.form1.p_process.value = "delete";
                    document.form1.submit();
                }
                else {
                    return;
                }
            }
//-->
	</script>
</head>

<body topmargin=0 leftmargin=0>
<form name = "form1" method = "post">
	 <input type = "hidden" name = "p_seq"     value = "<%= v_seq %>">
     <input type = "hidden" name = "p_searchtext"  value = "<%= v_searchtext %>">
     <input type = "hidden" name = "p_select"  value = "<%= v_select %>">
     <input type = "hidden" name = "p_pageno"  value = "<%= v_pageno %>">
     <input type = "hidden" name = "p_process"  value = "">
     <input type = "hidden" name = "p_userid" value = "<%= v_userid %>">
	 <input type = "hidden" name = "p_gubun" value = "<%=s_gubun%>">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td align="center">
	
	<table width="970" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/gatepage/cp_title06.gif"></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
        </tr>
      </table>
	   
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
	  <tr>
          <td colspan="7" class="table_top_line"></td>
        </tr>

        <tr> 
          <td width="88" class="table_title">제목</td>
          <td width="1"><img src="/images/gate/L_board_Tg.gif" width="1"></td>
          <td colspan="5" bgcolor="F7F7F7" style="padding-left:10"><%= v_title %></td>
        </tr>
        <tr> 
          <td colspan="17" align="center" class=b_guide></td>
        </tr>
        <tr> 
          <td class="table_title">날짜</td>
          <td width="1"><img src="/images/gate/L_board_Tg.gif" width="1"></td>
          <td width="655" bgcolor="F7F7F7"  style="padding-left:10"><%= FormatDate.getFormatDate(v_indate, "yyyy/MM/dd") %></td>
          <td width="1"><img src="/images/gate/L_board_Tg.gif" width="1"></td>
          <td width="105" class="table_title">파일</td>
          <td width="1"><img src="/images/gate/L_board_Tg.gif" width="1"></td>
          <td width="119" align="center" bgcolor="F7F7F7">					  
		   <%   for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
				   String v_realfile = (String)v_realfileVector.elementAt(i);
				   String v_savefile = (String)v_savefileVector.elementAt(i);
					 if(v_realfile != null) {  %>
				 &nbsp;<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
				 <%= v_realfile%>
				 </a><br>
				 <input type = "hidden" name = "p_savefile"  value = "<%= v_savefile%>">
		 <%        }    
				}   %></td>
        </tr>
		 <tr> 
          <td colspan="17" align="center" class=b_guide></td>
        </tr>
      </table> 
      <br>
      <table width="970" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="20"><img src="/images/gate/boardbox_c01.gif" width="20" height="20"></td>
          <td width="924" class=b_viewbox_T>&nbsp;</td>
          <td width="26"><img src="/images/gate/boardbox_c02.gif" width="20" height="20"></td>
        </tr>
        <tr> 
          <td class=b_viewbox_L>&nbsp;</td>
          <td align="center"><table width="95%" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="7%" height=8></td>
              </tr>
              <tr> 
                <td><%= StringManager.replace(v_content,"&amp;","&") %></p>
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
          <td colspan="3"><table  border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td><a href = "javascript:updatePage()"><img src="/images/admin/button/btn_modify.gif" border="0"></a></td>
				<td width=8></td>
                <td><a href = "javascript:deleteAction()"><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
				<td width=8></td>
                <td><a href = "javascript:selectList()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
				<td width=10></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
</table>
<br>
</body>
</html>
