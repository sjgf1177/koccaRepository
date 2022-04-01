<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

	String  v_selCategory = box.getString("p_selCategory");
	String  v_selState = box.getString("p_selState");
	String  v_searchtext	= box.getString("p_searchtext");
	
	String  v_process	= box.getString("p_process");
	String  v_year	= box.getString("p_year");
	String  v_hsubjnm	= box.getString("p_hsubjnm");


	//DEFINED in relation to select START
	String  ss_year		= box.getString("s_year");					//연도
	String  ss_subj		= box.getString("s_subjcode");				//과정
	String  ss_subjseq		= box.getString("s_subjseq");					//차수
	String  ss_subjyear = box.getString("s_subjyear");	  //과정년도
	if( ss_subjyear.equals("") ) {
		ss_subjyear = Integer.toString((new java.util.Date().getYear()) + 1900);
	}
	String  ss_upperclass	= box.getStringDefault("s_upperclass", "S01");  //과정대분류
	String  ss_middleclass  = box.getStringDefault("s_middleclass", "ALL"); //과정중분류
	String  ss_lowerclass	= box.getStringDefault("s_lowerclass", "ALL");  //과정소분류
	String  ss_subjterm = box.getStringDefault("s_subjterm","ALL");
	String  ss_action		= box.getString("s_action");
	String  s_subjsearchkey = box.getString("s_subjsearchkey");
	String  v_gyear		= box.getStringDefault("p_gyear", FormatDate.getDate("yyyy"));
	//DEFINED in relation to select END

%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
<SCRIPT LANGUAGE="JavaScript">
<!--
function insert_check() {
  action("insertFileToDB");
}
function preview() {
  action("previewFileToDB");
}
function action(p_process) {
	if (!chkParam()) {
		return;
	}

  document.form1.action = "/servlet/controller.off.OffBatchRecordsAdminServlet";
  document.form1.p_process.value = p_process;	//insertFileToDB
  document.form1.submit();
}
function whenSelection(p_action) {
	whenExcel();
}

// 검색조건 체크
function chkParam() {
  if(blankCheck(document.form1.p_file.value)){
		alert("DB로 가져갈 파일을 선택해 주세요");
		return false;
	}
  if (document.form1.p_file.value.length > 0 ){
		var data = document.form1.p_file.value;
		data = data.toUpperCase(data);
		if (data.indexOf(".XLS") < 0) {
		alert("DB로 입력되는 파일종류는 XLS 파일만 가능합니다.");
		return false;
		}
	}

  return true;
}

//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
	<form name="form1" method="post" action="/servlet/controller.off.OffBatchRecordsAdminServlet" enctype = "multipart/form-data">
	<input type="hidden" name="p_process" >
	<input type="hidden" name="p_action">
	<input type="hidden" name="p_userid"	id="p_userid"	value="">
<script type="text/javascript">
<!--
function whenExcel() {
	window.self.name = "proposeExcel";
	document.form1.target = 'mainFrame';
	$("#isExcel").val("true");
	document.form1.p_action.value = "go";
	document.form1.p_process.value = 'FileToDBPage';
	document.form1.submit();
	$("#isExcel").val("false");
	document.form1.target = window.self.name;
}
-->
</script>
<input type="hidden" name="isExcel" id="isExcel" value="false">

<table id="listForm" width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
	<td align="center" valign="top">
	<!----------------- title 시작 ----------------->
	<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
		<td><img src="/images/admin/portal/s.1_33.gif" ></td>
		<td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	</table>
	<!----------------- title 끝 ----------------->
	<br>
	<!----------------- form 시작 ----------------->
	<table cellspacing="0" cellpadding="1" class="form_table_out">

		
		<tr>
		<td bgcolor="#C6C6C6" align="center">
			<table cellspacing="0" cellpadding="0" class="form_table_bg">
			<tr>
				<td height="7" width="99%"></td>
			</tr>
			<tr>
				<td align="center" width="99%" valign="middle">
				<table width="99%" cellspacing="0" cellpadding="0" class="form_table">
					<tr>
					<td>
						<!------------------- 조건검색 시작 ------------------------->
						<table cellspacing="0" cellpadding="0" width="99%">
							<tr>
								<td>
									<table border="0" cellspacing="0" cellpadding="0" width="99%">
										<tr>
											<td>
											<script type="text/javascript">
											var isFirst = 0;
											function fnSearchBox() {
												if(isFirst>1) 
												changes_subjcode(
														$("#s_year").val(),
														$("#s_upperclass").val(),
														$("#s_middleclass").val(),
														$("#s_lowerclass").val(),
														$("#s_subjyear").val()
														);
												isFirst++;
											}
											function fnSearchBoxs_middleclass() {
												if(isFirst>1)
												changes_lowerclass($("#s_upperclass").val(), $("#s_middleclass").val());
												isFirst++;
											}
											function fnSearchBoxs_subjterm() {
												if(isFirst>2)
													changes_subjterm($('#s_subjcode').val(), $('#s_year').val(), $('#s_subjseq').val());
													isFirst++;
											}
											</script>
												연도 <kocca_select:select name="s_year" sqlNum="off.year"  param=" "
													onChange="fnSearchBox();" attr=" " selectedValue="<%= ss_year %>" isLoad="true" all="none" />
												대분류 <kocca_select:select name="s_upperclass" sqlNum="off.0002"  param=" "
													onChange="changes_middleclass(this.value);" attr=" " selectedValue="<%= ss_upperclass %>" isLoad="true" all="true" />
												중분류 <kocca_select:select name="s_middleclass" sqlNum="off.0003"  param="<%= ss_upperclass %>"
													onChange="changes_lowerclass(s_upperclass.value, this.value);"
													afterScript="fnSearchBoxs_middleclass"
													attr=" " selectedValue="<%= ss_middleclass %>" isLoad="true" all="true" />
												소분류 <kocca_select:select name="s_lowerclass" sqlNum="off.0004"  param="<%= ss_upperclass %>"  param2="<%= ss_middleclass %>"
													afterScript="fnSearchBox"
													onChange="fnSearchBox()" attr=" " selectedValue="<%=ss_lowerclass %>" isLoad="true" all="true" />
											</td>
											<td rowspan=3 width="10%" align="right"><a href="javascript:whenExcel()"><img src="/images/admin/button/btn_course_numexcel.gif" border="0"></a></td>
										</tr>
										<tr>
											<td>
												과정년도 <kocca_select:select name="s_subjyear" sqlNum="off.year.subj"  param=" "
													onChange="fnSearchBox()" attr=" " selectedValue="<%= ss_subjyear %>" isLoad="true" all="true" />
												과정명 <kocca_select:select name="s_subjcode" sqlNum="off.subj"
													param="<%= ss_year %>"  param2="<%= ss_upperclass %>"  param3="<%= ss_middleclass %>"  param4="<%= ss_lowerclass %>" param5="<%= ss_subjyear %>"
													onChange="changeisTerm(this.value,$('#s_subjseq').val(),$('#s_year').val());changes_subjseq($('#s_year').val(),this.value);setTimeout('change_subjseq()', 400);" attr=" " selectedValue="<%= ss_subj %>" isLoad="true" all="no" />
												차수 <kocca_select:select name="s_subjseq" sqlNum="off.subjseq" param="<%= ss_year %>"  param2="<%= ss_subj %>"
													onChange="fnSearchBoxs_subjterm()" afterScript="fnSearchBoxs_subjterm" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
												학기 <kocca_select:select name="s_subjterm" sqlNum="off.termlist" param="<%= ss_subj %>"  param2="<%= ss_year %>"  param3="<%= ss_subjseq %>"
													onChange="" attr=" " selectedValue="<%= ss_subjterm %>" isLoad="true" all="none" />
												<kocca_select:select name="isTerm" sqlNum="off.isTerm" param="<%= ss_subj %>"  param2="<%= ss_year %>"  param3="<%= ss_subjseq %>"
													onChange="" attr=" style='display:none' "  isLoad="true" all="none" />
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
						<!-------------------- 조건검색 끝 ---------------------------->
						</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="7" width="99%"></td>
			</tr>
			</table>
		</td>
		</tr>
	</table>
	<br>
	<!----------------- 추가 버튼 시작 ----------------->
	<table id="insertButton" cellpadding="0" cellspacing="0" class="table1" >
		<tr>
		<td align="right" height="20">
			<input name="p_file" type="FILE" class="input" size="145">
		</td>
		</tr>
		<tr>
		<td height="3"></td>
		</tr>
	</table>
	<br>
	<table width="97%" border="0" cellpadding="0" cellspacing="0">
		<tr>
		<td width="928" height="20" align="right"><a href="javascript:action('insertFileToDB');"><img src="/images/admin/button/btn_add.gif"  border="0"></a></td>
		<td width=10></td>
		<td align="right" width="32"><a href="javascript:alert('준비중입니다.');"><img src="/images/admin/button/btn_preview.gif" border="0"></a></td>
		</tr>
	</table>
	<!----------------- 추가 버튼 끝 ----------------->
	<!----------------- 과정 관리 시작 ----------------->
	<br>
	<table width="97%" border="0" cellspacing="0" cellpadding="0">
		<tr>
		<td align='left'>샘플파일</td>
		</tr>
		<tr>
		<td><img src="/images/admin/portal/Sample0000001.JPG" border="0"></td>
		</tr>
	</table>
	
	<!----------------- 과정 관리 끝 ----------------->
	<br>
	<!----------------- 저장, 삭제, 리스트 버튼 시작 ----------------->
	<!----------------- 저장, 삭제, 리스트 버튼 끝 ----------------->
	</td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>