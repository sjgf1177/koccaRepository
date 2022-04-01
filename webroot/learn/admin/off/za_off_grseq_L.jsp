<%
//**********************************************************
//  1. 제	  목: 차수 조회화면
//  2. 프로그램명: za_off_grseq_L.jsp
//  3. 개	  요: 차수 조회화면
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: swchoi 2009.11.18
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
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");

	//DEFINED in relation to select START
	String  ss_year = box.getString("s_year");	  //연도
	String  ss_subjyear = box.getString("s_subjyear");	  //과정년도
	if( ss_year.equals("") ) {
		ss_year = Integer.toString((new java.util.Date().getYear()) + 1900);
	}
	String  ss_subj = box.getString("s_subjcode");	  		//과정
	String  ss_grseq = box.getString("s_grseq");	  //차수
	String  ss_upperclass = box.getStringDefault("s_upperclass", "S01");	  //과정대분류
	String  ss_middleclass = box.getStringDefault("s_middleclass", "ALL");	//과정중분류
	String  ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");	  //과정소분류
	String  ss_action	= box.getString("s_action");
	String s_subjsearchkey = box.getString("s_subjsearchkey");
	String ss_subjseq	= box.getString("s_subjseq");
    String v_gyear  = box.getStringDefault("p_gyear", FormatDate.getDate("yyyy"));
	//DEFINED in relation to select END
	ArrayList list	  = null;
	if(ss_action.equals("go")){	//go button 선택시만 list 출력
		list = (ArrayList)request.getAttribute("resultList");
	}
	String s_gadmin = box.getSession("gadmin");
	String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
	String isAll = !v_gadmin.equals("A") ? "false" : "true";

	String  v_orderType	= box.getStringDefault("p_orderType"," asc"); //정렬순서

%>
<html>
<head>
<title><%=v_gadmin%> 차수관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#p_propstart1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_propend1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_edustart1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_eduend1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_billbegindt1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_billenddt1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_startcanceldate1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_endcanceldate1").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});

// 등록 페이지로
function InsertSubjPage() {
    var code=$("#p_subjcode1").val();
    var combobox_Code=$("#s_subjcode").val();

    if(combobox_Code=="" && code=="")
    {
        alert("과정명을 선택하시거나 과정코드를 입력 하여 주십시오.");
        return;
    }
    else if(combobox_Code!="" && code!="")
    {
        alert("과정명 혹은 과정코드중 하나만 선택 하여 주십시오.");
        return;
    }
	document.form1.p_process.value = 'updatePage';
	document.form1.action="/servlet/controller.off.OffRecruitAdminServlet";
	document.form1.p_subj.value  = $("#s_subjcode").val();
	document.form1.submit();
}

//수정 
function UpdateSubjPage(subj, year, subjseq, seq) {
	document.form1.p_process.value = 'updatePage';
	$("#u_subjseq").val(subjseq);
	$("#u_year").val(year);
	$("#u_seq").val(seq);
	document.form1.action="/servlet/controller.off.OffGrseqAdminServlet";
	document.form1.p_subj.value  = subj;
	document.form1.submit();
}
//삭제 
function deleteSubjSeq(subj, year, subjseq, seq) {
	document.form1.p_process.value = 'delete';
	$("#u_subjseq").val(subjseq);
	$("#u_year").val(year);
	$("#u_seq").val(seq);
	document.form1.action="/servlet/controller.off.OffGrseqAdminServlet";
	document.form1.p_subj.value  = subj;
//	confirm("삭제하시겠습니까?");
	if(confirm("정말 삭제하시겠습니까?"))document.form1.submit();
}

function fnInsert() {
	document.inputForm.p_process.value = 'addSeq';
	document.inputForm.action="/servlet/controller.off.OffGrseqAdminServlet";
	getDate("p_propstart");
	getDate("p_propend");
	getDate("p_edustart");
	getDate("p_eduend");
	getDate("p_billbegindt");
	getDate("p_billenddt");
	getDate("p_startcanceldate");
	getDate("p_endcanceldate");
	document.inputForm.submit();
}

// 검색
function whenSelection(ss_action) {
	if (ss_action=="go")	{
		top.ftop.setPam();
	}
	if($("#s_subjcode").val().length<2) {
		alert("과정명을 선택하여야 합니다.");
		return;
	}
		document.form1.s_action.value = ss_action;
		document.form1.p_process.value = 'listPage';
		document.form1.submit();

}

//정렬
function whenOrder(column) {
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

	document.form1.s_action.value = "go";
	document.form1.p_process.value = 'listPage';	
	document.form1.p_orderColumn.value = column;
	
	document.form1.submit();
}
function setInputData(p_subj, p_year, p_subjnm, p_subjseq, p_studentlimit, p_propstart, p_propend, p_edustart, p_eduend
								,p_billbegindt, p_billenddt, p_startcanceldate, p_endcanceldate, p_seq) {
	$("#p_subj").val(p_subj);
	$("#p_year").val(p_year);
	$("#p_subjnm").val(p_subjnm);
	$("#p_subjseq").val(p_subjseq*1+$("#p_addSubjSeq").val()*1);
	$("#p_studentlimit").val(p_studentlimit);
	setDate("p_propstart", p_propstart);
	setDate("p_propend",p_propend);
	setDate("p_edustart",p_edustart);
	setDate("p_eduend",p_eduend);
	setDate("p_billbegindt",p_billbegindt);
	setDate("p_billenddt",p_billenddt);
	setDate("p_startcanceldate",p_startcanceldate);
	setDate("p_endcanceldate",p_endcanceldate);
	if ($("#p_addSubjSeq").val()=='1') {
		$("#p_seq").val(1);
	}
	else {
		$("#p_seq").val(p_seq*1+$("#p_addSeq").val()*1);
	}
}

function subjSearch() {
	changes_subjcode($("#s_year").val(),$("#s_upperclass").val(),$("#s_middleclass").val(),$("#s_lowerclass").val());
}
function subjSelected(){
	if (document.all.s_subjcode.value.length > 0) {
		 $('#insertButton').show();
	}
	else $('#insertButton').hide();
}

function whenShow(InputType) {
	if($("#lastSubjSeq").val()*1 == 0) {InsertSubjPage(); return;}
	$("#listForm").hide();
	if (InputType == '1') {
		$("#p_addSubjSeq").val(1);
		$("#p_addSeq").val(0);
		getSubj($('#s_subjcode').val());
	}
	else {
		$("#p_addSubjSeq").val(0);
		$("#p_addSeq").val(1);
		getSubj(InputType);
	}
	$("#detailForm").fadeIn(1300);
}
function fnCancel(){
	$('#detailForm').hide();
	$('#listForm').fadeIn(1300);
}

$(document).ready(function()	{
	existSubjSeq();
});
function existSubjSeq() {
	$.post("/servlet/controller.common.TagServlet"
			, {sqlNum:"off.existSubjSeq", param:$("#s_subjcode").val(), param2:$("#s_year").val(), type:5,rerurnFunction:"existSubjSeqReturn",objNm:"existSubjSeqValue"}
			, function(data) {
				$("#existSubjSeqDiv").html(data);
			});
}
function existSubjSeqReturn(k, v) {
	$("#lastSubjSeq").val(k);
}
function getSubj(subj) {
	$.post("/servlet/controller.common.TagServlet"
			, {sqlNum:"off.getSubjSeq", param:subj, param2:$("#s_year").val(), type:6,rerurnFunction:"afterGetSubj",objNm:"existSubjSeqValue"}
			, function(data) {
				$("#existSubjSeqDiv").html(data);
			});
}
function afterGetSubj(data) {
	setInputData(data.d_subj, data.d_year, data.d_subjnm, data.d_subjseq, data.d_studentlimit, data.d_propstart, data.d_propend, data.d_edustart, data.d_eduend, data.d_billbegindt, data.d_billenddt, data.d_startcanceldate, data.d_endcanceldate, data.d_seq);
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id="existSubjSeqDiv"></div>

<table id='detailForm' width="1000" border="0" cellspacing="0" cellpadding="0" style="display:none">
  <tr>
	<td align="center" valign="top">
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
		  <td><img src="/images/admin/portal/s.1_off01.gif" ></td>
		  <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	  </table>
<form name="inputForm" method="post">
		<!----------------- 입력폼 시작 ----------------->
	<input type="hidden" name="p_subj" id="p_subj" value=""/>
	<input type="hidden" name="p_process"  value="">
		<table class="table_out" cellspacing="1" cellpadding="5">
		  <tr>
			<td colspan="4" class="table_top_line"></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>년도</b></td>
			<td class="table_02_2" colspan=3><input type="text" name="p_year" id="p_year" value="" class="input" size="4" maxlength="4"/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>차수-과정명</b></td>
			<td class="table_02_2" colspan=3><input type="text" name="p_subjnm" id="p_subjnm" value="" class="input" size="50"/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>차수</b></td>
			<td class="table_02_2"><input type="text" name="p_subjseq" id="p_subjseq" value="" readonly class="input" size="2" maxlength="2"/></td>
			 <td height="25" class="table_01" width="25%"><b>회차</b></td>
			 <td class="table_02_2"><input type="text" name="p_seq" id="p_seq" value="" readonly class="input" size="2" maxlength="2"/></td>
		  </tr>
		  <tr>
			<td height="25" class="table_01" width="25%"><b>교육정원</b></td>
			<td class="table_02_2" colspan=3><input type="text" name="p_studentlimit" id="p_studentlimit" value="" class="input" size="4" maxlength="4"/></td>
		  </tr>
			<tr>
			  <td height="25" class="table_01" width="25%"><b>수강신청기간</b></td>
			  <td class="table_02_2" colspan=3>
				 <input name="p_propstart" id="p_propstart" type="hidden" value="" >
				 <input name="p_propstart1" id="p_propstart1" type="text" class="datepicker_input1" size="10" value="">
				일&nbsp;&nbsp;
				<select name="p_propstart2"id="p_propstart2">
				  <%=FormatDate.getDateOptions(0,23,0)%>
				</select>
				시 &nbsp;~&nbsp;&nbsp;
				 <input name="p_propend" id="p_propend" type="hidden" value="" >
				<input name="p_propend1"id="p_propend1" type="text" class="datepicker_input1" size="10" value="">
				일&nbsp;
				<select name="p_propend2"id="p_propend2">
				  <%=FormatDate.getDateOptions(0,23,23)%>
				</select>
				시&nbsp;
			  </td>
			</tr>
			<tr>
			  <td height="25" class="table_01" width="25%"><b>학습 기간</b></td>
			  <td class="table_02_2" colspan=3>   
				 <input name="p_edustart" id="p_edustart" type="hidden" value="" >
				<input name="p_edustart1" id="p_edustart1" type="text" class="datepicker_input1" size="10" value="">
				일&nbsp;&nbsp;
				<select name="p_edustart2"id="p_edustart2">
				  <%=FormatDate.getDateOptions(0,23,0)%>
				</select>
				시 &nbsp;~&nbsp;&nbsp;
				 <input name="p_eduend" id="p_eduend" type="hidden" value="" >
				<input name="p_eduend1" id="p_eduend1" type="text" class="datepicker_input1" size="10" value="">
				일&nbsp;&nbsp;
				<select name="p_eduend2" id="p_eduend2">
				  <%=FormatDate.getDateOptions(0,23,23)%>
				</select>
				시&nbsp;
			  </td>
			</tr>
			<tr>
			  <td height="25" class="table_01" width="25%"><b>결제 기간</b></td>
			  <td class="table_02_2" colspan=3>   
				 <input name="p_billbegindt" id="p_billbegindt" type="hidden" value="" >
				<input name="p_billbegindt1" id="p_billbegindt1" type="text" class="datepicker_input1" size="10" value="">
				일&nbsp;&nbsp;
				<select name="p_billbegindt2" id="p_billbegindt2">
				  <%=FormatDate.getDateOptions(0,23,0)%>
				</select>
				시 &nbsp;~&nbsp;&nbsp;
				 <input name="p_billenddt" id="p_billenddt" type="hidden" value="" >
				<input name="p_billenddt1" id="p_billenddt1" type="text" class="datepicker_input1" size="10" value="">
				일&nbsp;&nbsp;
				<select name="p_billenddt2" id="p_billenddt2">
				  <%=FormatDate.getDateOptions(0,23,23)%>
				</select>
				시&nbsp;
			  </td>
			</tr>
			 <tr>
			   <td class="table_01" height="25"><b>수강신청취소기간</b></td>
			   <td class="table_02_2" colspan=3>
				 <input name="p_startcanceldate" id="p_startcanceldate" type="hidden" value="" >
			   	<input name="p_startcanceldate1" id="p_startcanceldate1" type="text" class="datepicker_input1" size="10" value="" onMouseover="if(this.value=='') this.value=p_propstart1.value"> 일&nbsp;&nbsp;
				<select name="p_startcanceldate2" id="p_startcanceldate2">
				  <%=FormatDate.getDateOptions(0,23,0)%>
				</select>
				시 &nbsp;~&nbsp;&nbsp;
				 <input name="p_endcanceldate" id="p_endcanceldate" type="hidden" value="" >
				<input name="p_endcanceldate1" id="p_endcanceldate1" type="text" class="datepicker_input1" size="10" value="" onMouseover="if(this.value=='') this.value=p_propend1.value"> 일&nbsp;&nbsp;
				<select name="p_endcanceldate2" id="p_endcanceldate2">
				  <%=FormatDate.getDateOptions(0,23,23)%>
				</select>
				시&nbsp;
			   </td>
			 </tr>
		</table>
		<!----------------- 입력폼 끝 ----------------->
		<br>
		<table><tr>
			<td align="center"><a href="javascript:fnInsert();"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
			<td align="center"><a href="javascript:fnCancel()"><img src="/images/admin/button/btn_cancel.gif" border="0"></a></td>
		</tr></table>
</form>
	  </td>
  </tr>
</table>
		<form name="form1" method="post" action="/servlet/controller.off.OffGrseqAdminServlet">
		  <input type="hidden" name="p_process"  value="">
		  <input type="hidden" name="isSeq"	 value="true">
		  <input type="hidden" name="p_addSubjSeq" id="p_addSubjSeq" value="0">
		  <input type="hidden" name="p_addSeq" id="p_addSeq" value="0">
		  <input type="hidden" id="lastSubjSeq" name="lastSubjSeq"	 value="0">
		  <input type="hidden" name="p_subj"	 value="">
		  <input type="hidden" name="p_subjnm"	value="">
		  <input type="hidden" name="s_action"  value="">
		  <input type="hidden" name="p_orderColumn">
		  <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
		  <input type="hidden" id="u_year" name="u_year"  value="">
		  <input type="hidden" id="u_seq" name="u_seq"  value="">
		  <input type="hidden" id="u_subjseq" name="u_subjseq"  value="">
<table id="listForm" width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
	<td align="center" valign="top">
	  <!----------------- title 시작 ----------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
		  <td><img src="/images/admin/portal/s.1_off01.gif" ></td>
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
														$("#s_year").val()
														);
												isFirst++;
											}
											function fnSearchBoxs_middleclass() {
												if(isFirst>1) 
												changes_lowerclass($("#s_upperclass").val(), $("#s_middleclass").val());
												isFirst++;
											}
											</script>
												<font color="red">★</font>연도 <kocca_select:select name="s_year" sqlNum="off.year"  param=" "
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
											<td rowspan=2 width="10%" align="right"><%@ include file="/learn/admin/include/za_GoButton.jsp" %><!-- whenSelection('go') --></td>
										</tr>
										<tr>
											<td>
												<%--과정년도 <kocca_select:select name="s_subjyear" sqlNum="off.year.subj"  param=" "--%>
													<%--onChange="fnSearchBox()" attr=" " selectedValue="<%= ss_subjyear %>" isLoad="true" all="true" />--%>
												<font color="red">★</font>과정명 <kocca_select:select name="s_subjcode" sqlNum="off.subj"
													param="<%= ss_year %>"  param2="<%= ss_upperclass %>"  param3="<%= ss_middleclass %>"  param4="<%= ss_lowerclass %>" param5="<%= ss_year %>"
													onChange="subjSelected();changes_subjseq($('#s_year').val(),this.value);setTimeout('change_subjseq()', 400);" attr=" " selectedValue="<%= ss_subj %>" isLoad="true" all="no"/>
												차수 <kocca_select:select name="s_subjseq" sqlNum="off.subjseq" param="<%= ss_year %>"  param2="<%= ss_subj %>"
													onChange="" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
												과정검색 <input type="text" name="s_subjsearchkey" size="17" onkeypress="if(event.keyCode=='13') whenSelection('go');" value="<%=s_subjsearchkey%>">
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
	  <!----------------- 추가 버튼 시작 ----------------->
	  <%--<table id="insertButton" cellpadding="0" cellspacing="0" class="table1" <%= ss_subj.trim().length()>0?"":"style=\"display='none'\"" %>>--%>
        <table id="insertButton" cellpadding="0" cellspacing="0" class="table1">
		<tr>
		  <td align="right" height="20" style="vertical-align:middle">
              <%--<img src="/images/admin/button/btn_beforecopy.gif" border="0" onClick="existSubjSeq();$('#p_addSubjSeq').val(1);whenShow(1);" style='CURSOR:hand' alt=''>--%>
              과정코드<input type="text" name="p_subjcode1" value="" maxlength="7" size="9" id="p_subjcode1">
              <img src="/images/admin/button/btn_new.gif" border="0" onClick="InsertSubjPage();" style='CURSOR:POINTER;' alt='' >
              </td>
		</tr>
	  </table>
	  <!----------------- 추가 버튼 끝 ----------------->
	  <!----------------- 과정 관리 시작 ----------------->
	  <table cellspacing="1" cellpadding="5" class="table_out">
		<tr>
		  <td colspan="<%if (v_gadmin.equals("H")) out.print("9"); else out.print("12");%>" class="table_top_line"></td>
		</tr>
		<tr>
		  <td class="table_title" width="34"><b>NO</b></td>
		  <td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">과정명</a></td>
		  <td class="table_title" width="34">차수</td>
		  <td class="table_title" width="34">회차</td>
		  <td class="table_title"><a href="javascript:whenOrder('p_propend')" class="e">수강신청기간</a></td>
		  <td class="table_title"><a href="javascript:whenOrder('p_eduend')" class="e">교육기간</a></td>
		  <td class="table_title"><a href="javascript:whenOrder('p_biyong')" class="e">수강료</a></td>
		  <td class="table_title">모집정원</td>
		  <td class="table_title">기능</td>
		</tr>
<%  if(ss_action.equals("go") && list!= null){	//go button 선택시만 list 출력

	DataBox data = null;
	for (int i=0; i<list.size(); i++) {
		data  = (DataBox)list.get(i); 

%>
		<tr id="<%=data.get("d_subjnm")%>">
		  <td class="table_01"><%=String.valueOf(i+1)%></td>
		  <td class="table_02_1" ><a href="javascript:UpdateSubjPage('<%=data.get("d_subj")%>','<%=data.get("d_year")%>','<%=data.get("d_subjseq")%>','<%=data.get("d_seq")%>');" class='b'><%=data.get("d_subjnm")%></a></td>
		  <td class="table_02_1"><%=data.get("d_subjseq")%></td>
		  <td class="table_02_1"><%=data.get("d_seq")%></td>
		  <td class="table_02_1"><%=data.getDate("d_propstart")%>~<%=data.getDate("d_propend")%></td>
		  <td class="table_02_1"><%=data.getDate("d_edustart")%>~<%=data.getDate("d_eduend")%></td>
		  <td class="table_02_1"><%=data.getCommaInt("d_biyong")%></td>
		  <td class="table_02_1"><%=data.getCommaInt("d_studentlimit")%></td>
		  <td class="table_02_2" id="<%=data.get("d_subj")%>" align="center">
		  	<a href="javascript:$('#p_addSubjSeq').val('0');$('#lastSubjSeq').val('<%=data.get("d_subjseq")%>');whenShow('<%=data.get("d_subj")%>');" class='b'><img style="border:none;align:abmiddle" src="/images/admin/portal/add.jpg" alt="추가모집"/></a>
		  	<% if(data.get("d_pcnt").equals("0") ) { %><a href="javascript:deleteSubjSeq('<%=data.get("d_subj")%>','<%=data.get("d_year")%>','<%=data.get("d_subjseq")%>','<%=data.get("d_seq")%>')" tabindex=75><img src="/images/admin/button/btn_del.gif" border="0"><%	} %></a>
		  </td>
		</tr>
<%  } %>

	  <% if (list.size()==0) {%>
			<tr><td colspan=9 class="table_02_1">등록된 과정이 없습니다.</td></tr>  
		<% } %>
<%} %>
	  </table>
	  <!----------------- 과정 관리 끝 ----------------->
	  <br>
	</td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
