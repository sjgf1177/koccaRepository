
<%
//**********************************************************
//  1. 제	  목: 교육차수 과정 일괄수정 화면
//  2. 프로그램명: za_Grseq_Assign_U.jsp
//  3. 개	  요: 교육차수 과정 일괄수정 화면
//  4. 환	  경: JDK 1.4
//  5. 버	  젼: 1.0
//  6. 작	  성: 노희성 2004. 11. 11
//  7. 수	  정:
//**********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page errorPage="/learn/library/error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.credu.system.*"%>
<%@ page import="com.credu.library.*"%>
<%@ page import="com.credu.course.*"%>
<%@ page import="com.credu.common.*"%>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select"%>
<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca"%>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	if (box == null) {
		box = RequestManager.getBox(request);
	}
	String v_grcode = box.getString("p_grcode");
	String v_gyear = box.getString("p_gyear");
	String v_grseq = box.getString("p_grseq");
	box.put("s_grcode", v_grcode);
	box.put("s_gyear", v_gyear);
	box.put("s_grseq", v_grseq);

	String  v_orderType	= box.getStringDefault("p_orderType"," asc"); //정렬순서

	String v_classname	= "";
	String v_subj		 = "";
	String v_year		 = "";
	String v_subjseq	  = "";
	String v_subjnm	   = "";

	String v_propstart	= "";
	String v_propend	  = "";
	String v_edustart	 = "";
	String v_eduend	   = "";
	String v_edulimit	 = "";
	String v_isclosed	 = "";
	DataBox dbox = null;
	ArrayList  list = (ArrayList)request.getAttribute("SubjCourseList");
	ArrayList  courseList = (ArrayList)request.getAttribute("courseList");
	String  ss_upperclass = box.getString("s_upperclass");	  //과정대분류
	String  ss_middleclass = box.getString("s_middleclass");	//과정중분류
	String  ss_lowerclass = box.getString("s_lowerclass");	//과정소분류
	String  s_subjsearchkey = box.getString("s_subjsearchkey");	//과정중분류
	String  ss_subjcourse = box.getString("s_subjcourse");	//과정
	GrseqBean grseqbean= new GrseqBean();
%>

<html>
<head>
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
<script language="javascript" src="/script/cresys_lib.js"></script>
<script language="JavaScript">
$(document).ready(function(){
	$('.datepicker_input1').datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200});
	
	$('input[name=s_subjsearchkey]').focus();
	
	self.focus();
});
<!--


// 일일 최대학습량 저장
function whenEdulimit() {
	var cnt_chked=0;

	dateChk();
	for(var i=0;i<document.form1.length;i++){
		if(document.form1.elements[i].name=="p_chk" && document.form1.elements[i].checked==true)	cnt_chked++;
	}

	if (cnt_chked==0){
		alert("선택된 과정정보가 없습니다");
		return;
	}
	document.form1.p_process.value="updateEdulimit";
	document.form1.submit();
}

// 수강신청기간 저장
function whenPropose() {
	var cnt_chked=0;

	dateChk();
	for(var i=0;i<document.form1.length;i++){
		if(document.form1.elements[i].name=="p_chk" && document.form1.elements[i].checked==true)	cnt_chked++;
	}

	if (cnt_chked==0){
		alert("선택된 과정정보가 없습니다");
		return;
	}
	document.form1.p_process.value="updatePropose";
	document.form1.submit();
}


// 교육기간 저장
function whenEdu() {
	var cnt_chked=0;

	dateChk();
	for(var i=0;i<document.form1.length;i++){
		if(document.form1.elements[i].name=="p_chk" && document.form1.elements[i].checked==true)	cnt_chked++;
	}

	if (cnt_chked==0){
		alert("선택된 과정정보가 없습니다");
		return;
	}
	document.form1.p_process.value="updateEdu";
	document.form1.submit();
}

//각종 저장
function whenSave(type) {
	var cnt_chked=0;

	for(var i=0;i<document.form1.length;i++){
		if(document.form1.elements[i].name=="p_chk" && document.form1.elements[i].checked==true)	cnt_chked++;
	}
	if (cnt_chked==0){
		alert("선택된 과정정보가 없습니다");
		return;
	}
	if($('#p_studentlimit').val() == "" || parseInt($('#p_studentlimit').val()) * 0 != 0){
		alert("정원을 잘못 입력하셨습니다.");
		return;
	}
	document.form1.p_savetype.value=type;
	document.form1.p_process.value="updateOther";
	document.form1.submit();
}

// 날자형식변환
function dateChk(){
	ff = document.form1;

	if(ff.p_propstart1.value != null){
		ff.p_propstart.value=make_date(ff.p_propstart1.value)+ff.p_propstart2.options[ff.p_propstart2.selectedIndex].value;
	}
	if(ff.p_propend1.value != null){
		ff.p_propend.value=make_date(ff.p_propend1.value)+ff.p_propend2.options[ff.p_propend2.selectedIndex].value;
	}

	if(ff.p_edustart1.value != null){
		ff.p_edustart.value=make_date(ff.p_edustart1.value)+ff.p_edustart2.options[ff.p_edustart2.selectedIndex].value;
	}
	if(ff.p_eduend1.value != null){
		ff.p_eduend.value=make_date(ff.p_eduend1.value)+ff.p_eduend2.options[ff.p_eduend2.selectedIndex].value;
	}


	return true;
}

// 정렬
function whenOrder(column) {
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

	document.form1.p_orderColumn.value = column;
	document.form1.p_process.value = "updatePageSubjCourse";
	document.form1.submit();

}

// select 박스 선택후 검색
function whenSelection(ss_action) {
	document.form1.p_process.value="updatePageSubjCourse";

	document.form1.submit();
}


// 날자 삭제(달력)
function whenclick(caldate) {
	caldate.value = "";
}

// 전체선택 / 취소
function chkeckall(){
	var checked = $('input:checkbox[name=p_chkeckall]').attr('checked');
	$('input:checkbox[name=p_chk]').attr('checked', checked);
}

//select box 설정
$(function() {
	$("#oUpperclass").bind("change", function(){
      var param = "type=sqlID&sqlID=selectBox.subjAttMiddleList&param=" + $(this).val();
      $.ajaxSetup({cache:false});
      $.ajax({
              type : "get"
          ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
          ,   dataType : "json"
          ,   data : param
          ,   success : fnSetMiddleClass
          ,   complete : function(arg1, arg2) {
                  // alert("complete : " + arg1);
              }
          ,   error :  function(arg1, arg2) {
                  // alert("error : " + arg1);
              }
      });
  });

  $("#oMiddleclass").bind("change", function(){
      var param = "type=sqlID&sqlID=selectBox.subjAttLowerList&param=" + $("#oUpperclass").val() + "," + $(this).val();
      $.ajaxSetup({cache:false});
      $.ajax({
              type : "get"
          ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
          ,   dataType : "json"
          ,   data : param
          ,   success : fnSetLowerClass
          ,   complete : function(arg1, arg2) {
                  // alert("complete : " + arg1);
              }
          ,   error :  function(arg1, arg2) {
                  // alert("error : " + arg1);
              }
      });
  });
});

/**
* 검색조건에서 대분류 선택시 callback function
*/
function fnSetMiddleClass( result ) {
  $("#oMiddleclass").empty();
  $("#oMiddleclass").append("<option value=\"\">== 전체 ==</option>");
  $("#oLowerclass").empty();
  $("#oLowerclass").append("<option value=\"\">== 전체 ==</option>");

  if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
      $.each( result.selectBoxList, function() {
          $("#oMiddleclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
      });

  }
}


/**
* 검색조건에서 중분류 선택시 callback function
*/
function fnSetLowerClass( result ) {
  $("#oLowerclass").empty();
  $("#oLowerclass").append("<option value=\"\">== 전체 ==</option>");

  if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
      $.each( result.selectBoxList, function() {
          $("#oLowerclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
      });

  }
}
//-->

	function searchSubjnmKeyEvent(e){
		if (e.keyCode =='13'){  goSearchSubjnm();  }
	}
		
	function goSearchSubjnm(){
		if (document.form1.s_grcode.value == 'ALL' || document.form1.s_grcode.value == '----') {
			alert("교육그룹을 선택하세요.");
			return;
		}
		$('#s_subjcourse').val("ALL");
		document.form1.p_process.value = 'updatePageSubjCourse';
		document.form1.submit();
	}

</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

	<form name="form1" method="post" action="/servlet/controller.course.GrseqServlet">
		<input type="hidden" name="p_process" value="assignSave"> <input type="hidden" name="p_savetype" value=""> <input type="hidden" name="s_grcode" value="<%=v_grcode%>"> <input type="hidden" name="p_grcode" value="<%=v_grcode%>"> <input type="hidden" name="p_gyear" value="<%=v_gyear%>"> <input type="hidden" name="p_grseq" value="<%=v_grseq%>"> <input type="hidden" name="p_orderColumn"> <input type="hidden" name="p_orderType" value="<%=v_orderType%>">


		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center" valign="top">
					<!----------------- title 시작 ----------------->
					<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
						<tr>
							<td><img src="/images/admin/course/c_title02.gif"></td>
							<td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
						</tr>
					</table> 
					<!----------------- title 끝 -----------------> 
					<br>
					<br> 
					<!----------------- 교육그룹, (대분류, 중분류, 소분류, 과정) 검색 ----------------->
					<table class="table_out" cellspacing="1" cellpadding="5">
						<tr>
							<td colspan="2" class="table_top_line"></td>
						</tr>
						<tr>
							<td width="16%" height="25" class="table_title"><b>교육그룹</b></td>
							<td class="table_02_3" width="81%"><b><%=GetCodenm.get_grcodenm(v_grcode)%></b></td>
						</tr>
						<tr>
							<td class="table_title" height="25"><b>교육차수</b></td>
							<td class="table_02_1"><b><%=GetCodenm.get_grseqnm(v_grcode, v_gyear, v_grseq)%> (<%=v_gyear%>년 <%=v_grseq%>차)</b></td>
						</tr>

						<tr>
							<td colspan=2 align="center" class="table_02_1">
								<table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
									<tr>
										<td width="100%" colspan="3">
											대분류 
											<kocca:selectBox name="s_upperclass" id="oUpperclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%= ss_upperclass %>" isLoad="true" /> 
											
											중분류 
											<%if ( !ss_upperclass.equals("") ) {%> 
												<kocca:selectBox name="s_middleclass" id="oMiddleclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttMiddleList" param="<%= ss_upperclass %>" selectedValue="<%= ss_middleclass %>" isLoad="true" /> 
											<%} else {%> 
												<select name="s_middleclass" id="oMiddleclass">
														<option value="">== 전체 ==</option>
												</select> 
											<%}%> 
											
											소분류 
											<%if ( !ss_middleclass.equals("") ) {%> 
												<kocca:selectBox name="s_lowerclass" id="oLowerclass" optionTitle="== 전체 ==" type="sqlID" sqlID="selectBox.subjAttLowerList" param="<%= ss_upperclass + ',' + ss_middleclass %>" selectedValue="<%= ss_lowerclass %>" isLoad="true" /> 
											<%} else {%> 
												<select name="s_lowerclass" id="oLowerclass">
													<option value="">== 전체 ==</option>
												</select> 
											<%}%>
										</td>
									</tr>
									<tr>
										<td height="5" colspan=3></td>
									</tr>
									<tr>
										<td align="left" width="30%">
											<input type="hidden" name="p_returnurlkey" value="<%=request.getRequestURI()%>">
											과정검색&nbsp; <input type="text" name="s_subjsearchkey" size="17" onkeypress="searchSubjnmKeyEvent(event)" value="<%=box.getString("s_subjsearchkey")%>">
											<a href="javascript:goSearchSubjnm()"><img src="/images/admin/button/search3_butt.gif" border="0" align="absmiddle"></a>
										</td>
										<td>&nbsp;&nbsp;
											<select id="s_subjcourse" name="s_subjcourse">
												<option value="ALL">-- 전체 --</option>
												<%if(courseList != null && courseList.size() > 0){%>
													<%
													for(int i=0; i<courseList.size(); i++){
														dbox = (DataBox)courseList.get(i);
													%>
														<option value="<%=dbox.getString("d_subjcourse")%>" <%=ss_subjcourse.equals(dbox.getString("d_subjcourse")) ? "selected" : ""%>>[<%=dbox.getString("d_subjcourse")%>]<%=dbox.getString("d_subjcoursenm")%></option>
													<%
													}
													%>
												<%}%>
											</select>
											&nbsp;<%@ include file="/learn/admin/include/za_GoButton.jsp"%>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table> <br>

					<table class="table_out" cellspacing="1" cellpadding="5">
						<!--
			<tr>
			  <td align="center" class="table_02_1" width ="30%">1일 최대학습량</td>
			  <td class="table_02_2"><input name="p_edulimit" type="text" class="input" size="3" maxlength="3" value="0">%</td>
			  <td align="center" class="table_02_1" width ="20%">
				<a href="javascript:whenEdulimit()"><img src="/images/admin/button/btn_save.gif" align="absmiddle" border="0"></a>
			  </td>
			</tr>
-->
						<tr>
							<td align="center" class="table_02_1" width="30%">수강신청시작일시<br>수강신청종료일시
							</td>
							<td class="table_02_2"><input name="p_propstart1" id="p_propstart1" type="text" class="datepicker_input1" size="10" value=""> 일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <select name="p_propstart2">
									<%=FormatDate.getDateOptions(0,23,0)%>
							</select> 시&nbsp; <a href="javascript:whenclick(document.form1.p_propstart1)"> <img src="/images/admin/button/btn_del.gif" align="absmiddle" border="0"></a> <input type=hidden name="p_propstart" value=""> <br> <input name="p_propend1" id="p_propend1" type="text" class="datepicker_input1" size="10" value=""> 일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <select name="p_propend2">
									<%=FormatDate.getDateOptions(0,23,23)%>
							</select> 시&nbsp; <a href="javascript:whenclick(document.form1.p_propend1)"> <img src="/images/admin/button/btn_del.gif" align="absmiddle" border="0"></a> <input type=hidden name="p_propend" value=""></td>
							<td align="center" class="table_02_1" width="20%"><a href="javascript:whenPropose()"><img src="/images/admin/button/btn_save.gif" align="absmiddle" border="0"></a></td>
						</tr>
						<tr>
							<td align="center" class="table_02_1" width="30%">학습 시작일시<br>학습 종료일시
							</td>
							<td class="table_02_2"><input name="p_edustart1" id="p_edustart1" type="text" class="datepicker_input1" size="10" value=""> 일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <select name="p_edustart2">
									<%=FormatDate.getDateOptions(0,23,0)%>
							</select> 시&nbsp; <a href="javascript:whenclick(document.form1.p_edustart1)"> <img src="/images/admin/button/btn_del.gif" align="absmiddle" border="0"></a> <input type=hidden name="p_edustart" value=""> <br> <input name="p_eduend1" id="p_eduend1" type="text" class="datepicker_input1" size="10" value=""> 일&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <select name="p_eduend2">
									<%=FormatDate.getDateOptions(0,23,23)%>
							</select> 시&nbsp; <a href="javascript:whenclick(document.form1.p_eduend1)"> <img src="/images/admin/button/btn_del.gif" align="absmiddle" border="0"></a> <input type=hidden name="p_eduend" value=""></td>
							<td align="center" class="table_02_1" width="20%"><a href="javascript:whenEdu()"><img src="/images/admin/button/btn_save.gif" align="absmiddle" border="0"></a></td>
						</tr>


						<tr>
							<td align="center" class="table_02_1" width="30%">사전설문<br>사후설문
							</td>
							<td class="table_02_2"><%=grseqbean.selectSulPaper("p_sulpaper1",v_grcode, "",1)%> <br /> 설문기간 : <input type="text" name="p_sulstartdate1" id="p_sulstartdate1" class="datepicker_input1" size="10" value=""> 일 ~ <input type="text" name="p_sulenddate1" id="p_sulenddate1" class="datepicker_input1" size="10" value=""> 일 <br> <%=grseqbean.selectSulPaper("p_sulpaper2",v_grcode, "",1)%><br /> 설문기간 : <input type="text" name="p_sulstartdate2" id="p_sulstartdate2" class="datepicker_input1" size="10" value=""> 일 ~ <input type="text" name="p_sulenddate2" id="p_sulenddate2" class="datepicker_input1" size="10" value=""> 일</td>
							<td align="center" class="table_02_1" width="20%"><a href="javascript:whenSave(1);"><img src="/images/admin/button/btn_save.gif" align="absmiddle" border="0"></a></td>
						</tr>
						<tr>
							<td align="center" class="table_02_1" width="30%">수강신청 취소기간</td>
							<td class="table_02_2"><input name="p_startcanceldate" id="p_startcanceldate" type="text" class="datepicker_input1" size="10" value="" onMouseover="if(this.value=='') this.value=p_propstart1.value"> 일 - <input name="p_endcanceldate" id="p_endcanceldate" type="text" class="datepicker_input1" size="10" value="" onMouseover="if(this.value=='') this.value=p_propend1.value"> 일</td>
							<td align="center" class="table_02_1" width="20%"><a href="javascript:whenSave(2);"><img src="/images/admin/button/btn_save.gif" align="absmiddle" border="0"></a></td>
						</tr>
						<tr>
							<td align="center" class="table_02_1" width="30%">자동승인여부</td>
							<td class="table_02_2"><input type="radio" name="p_autoconfirm" value="Y">예 &nbsp;&nbsp;&nbsp; <input type="radio" name="p_autoconfirm" value="N"> 아니오</td>
							<td align="center" class="table_02_1" width="20%"><a href="javascript:whenSave(3);"><img src="/images/admin/button/btn_save.gif" align="absmiddle" border="0"></a></td>
						</tr>
						<tr>
							<td align="center" class="table_02_1" width="30%">운영담당자</td>
							<td class="table_02_2">
								<%-- <kocca_select:select name="p_charger" sqlNum="adminList"  param=" "
					onChange="" attr=" " selectedValue="" isLoad="true" all="false" /> --%>
							</td>
							<td align="center" class="table_02_1" width="20%"><a href="javascript:whenSave(4);"><img src="/images/admin/button/btn_save.gif" align="absmiddle" border="0"></a></td>
						</tr>
						<tr>
							<td align="center" class="table_02_1" width="30%">정원</td>
							<td class="table_02_2">
								<input type="text" id="p_studentlimit" name="p_studentlimit"/>
							</td>
							<td align="center" class="table_02_1" width="20%"><a href="javascript:whenSave(5);"><img src="/images/admin/button/btn_save.gif" align="absmiddle" border="0"></a></td>
						</tr>
					</table> <br>

					<table width="97%" border="0" cellspacing="1" cellpadding="5">
						<tr>
							<td align=right class=dir_txt>
								<!--(삭제시 교육차수관리 화면이용)<!-->
							</td>
						</tr>
					</table>

					<table class="table_out" cellspacing="1" cellpadding="5">
						<tr>
							<td colspan="9" class="table_top_line"></td>
						</tr>
						<tr>
							<td width="6%" height="25" class="table_title"><b>NO</b></td>
							<td width="15%" class="table_title"><a href="javascript:whenOrder('classname')" class="e"><b>과정분류</b></a></td>
							<td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e"><b>과정명</b></a></td>
							<td class="table_title" width="10%"><a href="javascript:whenOrder('subj')" class="e"><b>코드</b></a></td>
							<td class="table_title" width="18%"><b>수강신청기간</b></td>
							<td class="table_title" width="18%"><b>교육기간</b></td>
							<!--			<td class="table_title" width="6%"><b>일일진도제한</b></td> -->
							<td class="table_title" width="6%"><input type="checkbox" name="p_chkeckall" onClick="javascript:chkeckall()"></td>
						</tr>
						<%


		for (int i=0; i<list.size(); i++) {
			dbox  = (DataBox)list.get(i);
			v_classname	= dbox.getString("d_classname");
			v_subj		 = dbox.getString("d_subjcourse");
			v_year		 = dbox.getString("d_scyear");
			v_subjseq	  = dbox.getString("d_scsubjseq");
			v_subjnm	   = dbox.getString("d_subjcoursenm");
			v_propstart	= dbox.getString("d_propstart");
			v_propend	  = dbox.getString("d_propend");
			v_edustart	 = dbox.getString("d_edustart");
			v_eduend	   = dbox.getString("d_eduend");
			v_edulimit	 = dbox.getString("d_edulimit");
			v_isclosed	 = dbox.getString("d_isclosed");

			if (v_propstart.length() >= 8) v_propstart = FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd");
			if (v_propend.length() >= 8)   v_propend = FormatDate.getFormatDate(v_propend,"yyyy/MM/dd");
			if (v_edustart.length() >= 8)  v_edustart = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
			if (v_eduend.length() >= 8)	v_eduend = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");
					  
%>
						<tr>
							<td align="center" bgcolor="#EEEEEE" height="25"><%=i+1%></td>
							<td class="table_02_1"><b><%=v_classname%></b></td>
							<td class="table_02_2"><b><%=v_subjnm%></b></td>
							<td class="table_02_1"><b><%=v_subj%> </b></td>
							<td class="table_02_1"><%=v_propstart%>~ <%=v_propend%></td>
							<td class="table_02_1"><%=v_edustart%>~ <%=v_eduend%></td>
							<!--			<td class="table_02_1" ><%=v_edulimit%></td> -->
							<td class="table_02_1">
								<%if(!v_isclosed.equals("Y")){%><input type="checkbox" name="p_chk" value="<%=v_subj%>,<%=v_year%>,<%=v_subjseq%>">
								<%}else{%><font color=red>수료처리완료<%}%></font>
							</td>
						</tr>
						<%
		}
%>
					</table> <!----------------- 교육차수 과정지정 끝 -----------------> <br> <!----------------- 저장, 취소 버튼 시작 ----------------->
					<table border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td align="center"><a href="javascript:window.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
						</tr>
					</table> <!----------------- 저장, 취소 버튼 끝 -----------------> <br>
				</td>
			</tr>
		</table>

	</form>
	<%@ include file="/learn/library/getJspName.jsp"%>
</body>
</html>








