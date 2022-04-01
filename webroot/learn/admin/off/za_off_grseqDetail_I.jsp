<%
//**********************************************************
//  1. 제	  목: 학기등록 화면
//  2. 프로그램명: za_off_grseqDetail_I.jsp
//  3. 개	  요: 학기등록 화면
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: swchoi 2009.11.23
//  7. 수	  정: 
//**********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	
    DataBox data = (DataBox)request.getAttribute("resultData");
	List list = (List)data.getObject("resultList");
	DataBox l_data = null;
%>
<html>
<head>
<title>집합과정 수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>

<SCRIPT LANGUAGE="JavaScript">
<!--
//수정 시작
function updateSubject() {
	document.form2.s_action.value = "go";
	document.form2.p_process.value = 'detailInsert';
	document.form2.submit();
}


-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="">
<div id="existSubjSeqDiv"></div>
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>

<form name="form2" enctype="multipart/form-data" method = "POST" action="/servlet/controller.off.OffGrseqAdminServlet">
	<input type="hidden" name="p_process"	 value="">
	<input type="hidden" name="s_action">
	<input type="hidden" name="p_termtotal" value="<%= data.getInt("d_termtotal") %>">

	<input type="hidden" name="p_subj"	value="<%= data.get("d_subj") %>">

<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
	<td align="center" valign="top">
	  <!----------------- title 시작 ----------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
		  <td><img src="/images/admin/portal/s.1_off02.gif" ></td>
		  <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	  </table>
	  <!----------------- title 끝 ----------------->
	  <%=box.getString("p_warnmsg")%><br>
	  <br>
	  <!----------------- 과정 등록/수정/삭제/상세보기 (사이버) 시작 ----------------->
		<table cellspacing="1" cellpadding="5" class="table_out" border="0">
		  <tr> 
			<td colspan="5" class="table_top_line"></td>
		  </tr>
		  <tr> 
			<td height="25" class="table_01"><b>과정명</b></td>
			<td class="table_02_2"><%= data.get("d_subjnm") %></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>년도</b></td>
			<td class="table_02_2"><input type="text" name="p_year" id="p_year" value="<%= data.get("d_year") %>" readonly class="input" size="4" maxlength=4 onChange="existSubjSeq();"/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>차수</b></td>
			<td class="table_02_2"><input type="text" name="p_subjseq" id="p_subjseq" value="<%= data.get("d_subjseq") %>" readonly class="input" size="4" maxlength=4/><input type="hidden" name="p_seq" id="p_seq" value="1" readonly class="input" size="4" maxlength=4/></td>
		  </tr>
<% for(int i = 0; i < data.getInt("d_termtotal"); i++) {
		if(list!=null && i < list.size()) l_data = (DataBox) list.get(i);
		else l_data=data;%>
			<tr>
			  <td height="25" class="table_01" width="25%"><b><%= i+1 %>학기 교육 기간</b></td>
			  <td class="table_02_2">   
				<input name="p_termstart<%= i %>" id="p_termstart<%= i %>" type="text" class="input" size="10" maxlength=10  value="<%= l_data.get("d_termstart") %>" OnClick="MiniCal(this)" readonly>
				일&nbsp;&nbsp;~
				<input name="p_termend<%= i %>" id="p_termend<%= i %>" type="text" class="input" size="10" maxlength=10  value="<%= l_data.get("d_termend") %>" OnClick="MiniCal(this)" readonly>
				일
              	<input type=hidden id="p_term<%= i %>" name="p_term<%= i %>" value="<%= i+1 %>">
			  </td>
			</tr>
<%	} %>
		</table>

	   </td></tr><tr><td align="center" valign="top"><br>
	  <br>
	  <!----------------- 등록, 리스트 버튼 시작 ----------------->
	  <table border="0" cellspacing="0" cellpadding="0">
		<tr>
		  <td align="center"><a href="javascript:updateSubject()" tabindex=68><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
		  <td width=8></td>
		  <td align="center"><a href="javascript:self.close()" tabindex=69><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
            <td align="center" style="display=none">
                <a href="javascript:DeleteSubject()" tabindex=75><img src="/images/admin/button/btn_del.gif" border="0"></a></td>
		</tr>
	  </table>
	  <!----------------- 등록, 리스트 버튼 끝 ----------------->
	  <br>
		</td>
	</tr>
</table>

<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
