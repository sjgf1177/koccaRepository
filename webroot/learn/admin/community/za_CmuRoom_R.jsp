<%
//**********************************************************
//  1. 제      목: 커뮤니티 공지사항보기
//  2. 프로그램명 : za_CmuNotice_R.jsp
//  3. 개      요: 커뮤니티 공지사항보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.07.01 
//  7. 수      정: 2005.07.01 
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage   = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.io.*" %>
<%@ page import = "com.credu.community.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />


<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process    = box.getString("p_process");
    
    int     v_pageno     = box.getInt("p_pageno");
    String  s_userid     = box.getSession("userid");
    String  s_username   = box.getSession("name");
    ArrayList list       = (ArrayList)request.getAttribute("list");
    
    DataBox dbox = (DataBox)list.get(0);
	String	v_tmp_intro  = dbox.getString("d_intro");
	int  v_countMember =	dbox.getInt("d_countmember");
	int  v_countBrd	=	dbox.getInt("d_countbrd");

%>  


<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<script src="/script/cafe_select.js" language="javascript"></script>
<SCRIPT language="javascript">
<!--

//리스트로 이동
function wf_listOK() {
    document.form1.action = "/servlet/controller.community.CommunityAdminRoomServlet";
    document.form1.p_process.value = "listPage";
    document.form1.submit();
}

//-->
</SCRIPT>
</head>
<body topmargin=0 leftmargin=0 >
<form name = "form1"     enctype = "multipart/form-data"   method = "post">
<input type = "hidden" name = "p_process"     value="">
<input type = "hidden" name = "p_userid"      value = "">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
 <tr>
  <td align="center"> 
   <!----------------- title 시작 ----------------->
   <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
    <tr> 
     <td><img src="../../../images/admin/homepage/tit_comm_man.gif"></td>
     <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
    </tr>
   </table>
   <br>

   <table width="970"  border="0"  cellpadding="0" cellspacing="1" class="table_out">
    <tr> 
      <td width="96" class="table_title"  width="10%">커뮤니티명</td>
      <td class="table_02_2" width="40%"><%=dbox.getString("d_cmu_nm")%></td>
	  <td width="96" class="table_title"  width="10%">마스터</td>
      <td class="table_02_2" ><%=dbox.getString("d_kor_name")%></td>
	</tr>
	<tr>
      <td class="table_title" width="10%">회원수</td>
      <td class="table_02_2" width="40%"><%=v_countMember%> 명</td>
	  <td class="table_title" width="10%">게시판수</td>
      <td class="table_02_2" width="40%"><%=v_countBrd%> 개</td>
    </tr>
    <tr> 
      <td class="table_title">신청일</td>
      <td class="table_02_2"><%=FormatDate.getFormatDate(dbox.getString("d_register_dte"), "yyyy/MM/dd")%></td>
      <td class="table_title">개설일</td>
      <td class="table_02_2"><%=FormatDate.getFormatDate(dbox.getString("d_accept_dte"), "yyyy/MM/dd")%></td>
    </tr>
    <tr> 
      <td class="table_title" >분류</td>
      <td valign="top" class="table_02_2" >
		<%=dbox.getString("d_type_l_nm")%> ==> <%=dbox.getString("d_type_m_nm")%></td>
      <td class="table_title" >공개여부</td>
      <td valign="top" class="table_02_2" >
		<%String v_display_fg="공개";
           if("1".equals(dbox.getString("d_display_fg"))) v_display_fg="공개";
           if("2".equals(dbox.getString("d_display_fg"))) v_display_fg="비공개";%> <%=v_display_fg%>
		</td>
    </tr>
	<tr>
		<td class="table_title" >소개 </td>
		<td colspan="3" valign="top" class="table_02_2"><%=v_tmp_intro%></td>
	</tr>
  </table>
  <br>
    <table width="970" border="0" cellspacing="0" cellpadding="0">
     <tr align="center"> 
      <td>
       <!--a href="javascript:uf_updateOK();" onfocus=this.blur()><img src="/images/admin/button/btn_modify.gif" border = "0">
       <a href="javascript:uf_deleteOK();" onfocus=this.blur()><img src="/images/admin/button/btn_del.gif" border = "0"-->

       <a href="javascript:history.back();" onfocus=this.blur()><img src="/images/admin/button/btn_list.gif" border = "0">
       </td>
     </tr>
    </table></td>
  </tr>
</table>
</form>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
