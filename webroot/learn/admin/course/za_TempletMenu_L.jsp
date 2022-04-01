<%
//**********************************************************
//  1. 제	  목: 템플릿관리메뉴 리스트
//  2. 프로그램명 : za_TempletMenu_L.jsp
//  3. 개	  요: 템플릿관리메뉴 리스트
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: swchoi 2009. 11. 02
//  7. 수	  정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	String  v_process   = box.getString("p_process");
	String s_grcode = box.getString("s_grcode");

	ArrayList list = (ArrayList)request.getAttribute("selectList");
	boolean isRoot = box.get("s_gubun")==null||"".equals(box.get("s_gubun"));
%>
<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
//관심버튼 클릭
function whenSimpleSave(grtype,gubun,menuid) {
	$.post("/servlet/controller.common.AjaxServlet"
			, {	  sqlNum:"homepageSubMenu.updateUse",rerurnFunction:"resultSimpleSave"
				, p_grtype:grtype
				, p_gubun:gubun
				, p_menuid:menuid
			}
			, function(data) {
				$("#ajaxDiv").html(data);
			});
}
function resultSimpleSave(data) {
	sub_list('ALL','','','','','','','','','','','','');
}
//-->
</SCRIPT>
<SCRIPT LANGUAGE="JavaScript">
<!--
	function sub_list(d_grtype,d_gubun,d_menuid,d_kind,d_position,d_orders,d_menuname,d_menuurl,d_menuimg,d_menuoverimg,d_useyn, isnew) {
		document.form1.action = "/servlet/controller.course.TempletMenuAdminServlet";
	document.form1.p_process.value = "select";
	document.form1.s_gubun.value = d_gubun;
	document.form1.s_grtype.value = d_grtype;
	document.form1.s_kind.value = d_kind;
	document.form1.s_position.value = d_position;
		document.form1.submit();
	}

	function view(d_grtype,d_gubun,d_menuid,d_kind,d_position,d_orders,d_menuname,d_menuurl,d_menuimg,d_menuoverimg,d_useyn, isnew) {
		 $("#detailForm").fadeIn(1000);
		 $('#title2').hide();
		var f = document.inputForm;
		f.p_grtype.value			= d_grtype;
		f.p_gubun.value			= d_gubun;
		f.p_menuid.value			= d_menuid;
		if(d_kind=='MB') {
			 f.p_kind[0].checked=true;
			 $("#mainInput").show();
		}
		else {
			 f.p_kind[1].checked=true;
			 $("#mainInput").hide();
		}
		f.p_position.value			= d_position;
		f.p_orders.value			= d_orders;
		f.p_menuname.value	= d_menuname;
		f.p_menuurl.value			= d_menuurl;
		if(d_useyn=='Y') f.p_useyn[0].checked=true;
		else f.p_useyn[1].checked=true;
		$("#isnew").val(isnew);
	}
	function swapMain(v) {
		if(v=='MB') {
			 $("#mainInput").show();
		}
		else {
			document.inputForm.p_position.value='C';
			 $("#mainInput").hide();
		}
	}

	function insert() {
		document.inputForm.action = "/servlet/controller.course.TempletMenuAdminServlet";
		document.inputForm.p_process.value = $("#isnew").val() == "YES" ? "insert" :"update";
		document.inputForm.submit();
	}
	function fnDelete() {
		document.inputForm.action = "/servlet/controller.course.TempletMenuAdminServlet";
		document.inputForm.p_process.value = "delete";
		document.inputForm.submit();
	}


//-->
</SCRIPT>

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form id='detailForm' name="inputForm" method="post" style="display:none"  enctype = "multipart/form-data">

	<input type = "hidden" name = "p_process" value = "">
	<input id="isnew" type = "hidden" name = "isnew" value = "">
<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
	<td align="center" valign="top">
	  <!----------------- title 시작 ----------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
		  <td><img src="/images/admin/portal/s.1_03.gif" ></td>
		  <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	  </table>
	  <!----------------- title 끝 ----------------->

		<!----------------- 입력폼 시작 ----------------->
		<table class="table_out" cellspacing="1" cellpadding="5">
		  <tr>
			<td colspan="4" class="table_top_line"></td>
		  </tr>
		  <tr style="display:'none'">
			<td height="25" class="table_01" width="25%"><b>기관코드</b></td>
			<td class="table_02_2"><input type="text" name="p_grtype" value=""/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>메뉴구분</b></td>
			<td class="table_02_2"><input type="text" name="p_gubun" value=""/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>메뉴코드</b></td>
			<td class="table_02_2"><input type="text" name="p_menuid" value="" readonly/>*시스템자동부여</td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>메뉴순서</b></td>
			<td class="table_02_2"><input type="text" name="p_orders" value=""/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>메뉴명</b></td>
			<td class="table_02_2"><input type="text" name="p_menuname" value="" style="width:95%"/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>관련URL</b></td>
			<td class="table_02_2"><input type="text" name="p_menuurl" value="" style="width:95%"/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>사용여부</b></td>
			<td class="table_02_2"><input type="radio" name="p_useyn" value="Y" checked/>사용&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="p_useyn" value="N"/>미사용</td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>메인/서브구분</b></td>
			<td class="table_02_2"><input type="radio" name="p_kind" value="MB" checked onclick="swapMain(this.value);"/>메인&nbsp;&nbsp;&nbsp;&nbsp;<input type="radio" name="p_kind" value="SB" onclick="swapMain(this.value);"/>서브</td>
		  </tr>
		  <tr id="mainInput">
			<td height="25" class="table_01" width="25%"><b>화면배치</b></td>
			<td class="table_02_2">
				 <kocca_select:select name="p_position" sqlNum="menuPosition"  param=" "
					onChange="" attr=" style='width:300px'" selectedValue="" isLoad="true" all="none" isCheckBox="N" />
			</td>
		  </tr>
		  <tr id="subInput">
			<td height="25" class="table_01" width="25%" rowspan="6"><b>메뉴이미지</b></td>
			<td class="table_02_2">Standard Image<input name="p_menuimg" type="file" class="input" size="60" /></td>
		  </tr>
		  <tr id="subInput2">
			<td class="table_02_2">Rollover Image <input name="p_menuoverimg" type="file" class="input" size="60" /></td>
		  </tr>
		  <tr id="subInput2">
			<td class="table_02_2">보조1 <input name="p_subimg" type="file" class="input" size="60" /></td>
		  </tr>
		  <tr id="subInput2">
			<td class="table_02_2">보조2 <input name="p_suboverimg" type="file" class="input" size="60" /></td>
		  </tr>
		  <tr id="subInput2">
			<td class="table_02_2">FLASH <input name="P_flashfilename" type="file" class="input" size="60" /></td>
		  </tr>
		  <tr id="subInput2">
			<td class="table_02_2">MENU HOME <input name="p_menuhomeimg" type="file" class="input" size="60" /></td>
		  </tr>
		</table>
		<!----------------- 입력폼 끝 ----------------->
	  </td>
  </tr>
</table>
<table width="1000"><tr>
	<td align="center">
		<a href="javascript:insert();"><img alt="저장" src="/images/admin/button/btn_save.gif" border=0></a>&nbsp;&nbsp;
		<a href="javascript:fnDelete();"><img alt="삭제" src="/images/admin/button/btn_del.gif" border=0></a>&nbsp;&nbsp;
		<a href="javascript:fnCancelinput()"><img alt="취소" src="/images/admin/button/btn_cancel.gif" border=0></a>
		<script type="text/javascript">
			function fnCancelinput() {
				$('#title2').show();
				$('#detailForm').fadeOut(750);
			}
		</script>
	</td>
</tr></table>
</form><form name="form1" method="post">
	<input type = "hidden" name = "p_process" value = "<%= v_process %>"/>
	<input type = "hidden" name = "s_grtype"  value = "<%=box.get("s_grtype") %>"/>
	<input type = "hidden" name = "s_gubun"  value = "<%=box.get("s_gubun") %>"/>
	<input type = "hidden" name = "s_kind"  value = "<%=box.get("s_kind") %>"/>
	<input type = "hidden" name = "s_position"  value = "<%=box.get("s_position") %>"/>


<table width="1000" border="0" cellspacing="0" cellpadding="0">
  <tr>
	<td align="center" valign="top">
	  <!----------------- title 시작 ----------------->
	  <table id="title2" width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
		  <td><img src="/images/admin/portal/s.1_02.gif" ></td>
		  <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	  </table>
	  <!----------------- title 끝 ----------------->
	  <br>
		<table class="form_table_out" cellspacing="0" cellpadding="1">
		  <tr> 
			<td align="center"> 
			  <table class="form_table_bg" cellspacing="0" cellpadding="0">
				<tr> 
				  <td height="7" colspan="4"></td>
				</tr>
				<tr valign="middle">
				  <td>
				  메뉴명 <input type="text" name="s_menuname" value=""/> &nbsp;&nbsp;&nbsp;<a href="#00"><img alt="조회" src="/images/admin/button/b_go.gif" border=0></a></td>
				  <td align="right"> </td>
				  <td width="160" align="right"style="padding-left=10;padding-right=10"> 
					<a href="javascript:view('<%=box.get("s_grtype").length()==0? "ALL" : box.get("s_grtype") %>','<%=box.get("s_gubun") %>','<%=box.get("s_gubun").length()==0?"00":"" %>','<%=box.get("s_kind") %>','<%=box.get("s_position") %>','','','','','','','YES')"><img src="/images/admin/button/btn_add.gif" border=0></a>
				  </td>
				</tr>
				<tr> 
				  <td height="7" colspan="4"></td>
				</tr>
			  </table>
			</td>
		  </tr>
		</table>
		<br>
<div id="ajaxDiv"></div>

		<!----------------- List 시작 ----------------->
		<table class="table_out" cellspacing="1" cellpadding="5">
		  <tr>
			<td colspan="4" class="table_top_line"></td>
		  </tr>
		  <tr>
			<td height="25" class="table_title"><b>기관코드</b></td>
			<td class="table_title"><b>메뉴구분</b></td>
			<% if (!isRoot) {  %><td class="table_title"><b>메뉴코드</b></td><% }%>
			<td class="table_title"><b>메인/서브구분</b></td>
			<% if (!isRoot) {  %><td class="table_title"><b>화면배치</b></td>
			<td class="table_title"><b>메뉴순서</b></td><% }%>
			<td class="table_title"><b>메뉴명</b></td>
<!-- 			<td class="table_title" width="20%"><b>관련URL</b></td> -->
			<td class="table_title"><b>메뉴이미지</b></td>
			<td class="table_title"><b>메뉴이미지2</b></td>
			<% if (isRoot) {  %><td class="table_title"><b>하위코드</b></td><% }%>
			<td class="table_title"><b>사용여부</b></td>
		  </tr>
<%
			for(int i = 0; i < list.size(); i++) {

				DataBox data  = (DataBox)list.get(i);
%>
		  <tr>
			<td height="25" class="table_01"><%=data.get("d_grtype")%></td>
			<td class="table_02_2"><a href="javascript:view('<%=data.get("d_grtype")%>',
							'<%=data.get("d_gubun")%>','<%=data.get("d_menuid")%>','<%=data.get("d_kind")%>',
							'<%=data.get("d_position")%>','<%=data.get("d_orders")%>','<%=data.get("d_menuname")%>',
							'<%=data.get("d_menuurl")%>','<%=data.get("d_menuimg")%>','<%=data.get("d_menuoverimg")%>','<%=data.get("d_useyn")%>','NO')"><%=data.get("d_gubun")%></a></td>
			<% if (!isRoot) {  %><td class="table_02_1"><%=data.get("d_menuid")%></td><% }%>
			<td class="table_02_1"><%=data.get("d_kindnm")%></td>
			<% if (!isRoot) {  %><td class="table_02_1"><%=data.get("d_position")%></td>
			<td class="table_02_1"><%=data.get("d_orders")%></td><% }%>
			<td class="table_02_1"><%=data.get("d_menuname")%></td>
<!-- 			<td class="table_02_1"><%=data.get("d_menuurl")%></td> -->
			<td class="table_02_1"><%=data.get("d_menuimg")%></td>
			<td class="table_02_1"><%=data.get("d_menuoverimg")%></td>
			<% if (isRoot) {  %><td class="table_03_1">
			<a href="javascript:sub_list('<%=data.get("d_grtype")%>',
							'<%=data.get("d_gubun")%>','<%=data.get("d_menuid")%>','<%=data.get("d_kind")%>',
							'<%=data.get("d_position")%>','<%=data.get("d_orders")%>','<%=data.get("d_menuname")%>',
							'<%=data.get("d_menuurl")%>','<%=data.get("d_menuimg")%>','<%=data.get("d_menuoverimg")%>','<%=data.get("d_useyn")%>','NO')"><img src="/images/admin/button/b_downcodeb.gif" align="absmiddle" border="0"></a></td><% }%>
			<td class="table_02_1"><a href="javascript:whenSimpleSave('<%=data.get("d_grtype")%>','<%=data.get("d_gubun")%>','<%=data.get("d_menuid")%>')"><%=data.get("d_useyn")%></a></td>
		  </tr>

<%		}	%>
		</table>
		<!----------------- List 끝 ----------------->
	  </td>
  </tr>
</table>

<% if (box.get("s_gubun")!=null&&!"".equals(box.get("s_gubun"))) {  %><table width="1000"><tr><td align="center"><a href="javascript:sub_list('','','','','','','','','','','','');"><img src="/images/admin/button/up_b.gif" alt="이전으로" style="border=none"/></a></td></tr></table><% }%>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
