<%
//**********************************************************
//  1. 제	  목: 과목 조회화면
//  2. 프로그램명: za_off_grseq_L.jsp
//  3. 개	  요: 과목 조회화면
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
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");

	//DEFINED in relation to select START
	String  ss_year = box.getString("s_year");	  //연도
	String  ss_subj = box.getString("s_subjcode");	  		//과정
	String  ss_subjseq = box.getString("s_subjseq");	  //차수
	String  ss_upperclass = box.getStringDefault("s_upperclass", "S01");	  //과정대분류
	String  ss_middleclass = box.getStringDefault("s_middleclass", "ALL");	//과정중분류
	String  ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");	  //과정소분류
	String  ss_action	= box.getString("s_action");
	String s_subjsearchkey = box.getString("s_subjsearchkey");
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
<title>과목관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
<SCRIPT LANGUAGE="JavaScript">
<!--
function fnInsert(subj, year, subjseq, term, lecture) {
	$("#p_subj").val(subj);
	$("#p_year").val(year);
	$("#p_subjseq").val(subjseq);
	$("#p_term").val(term);
	$("#p_lecture").val(lecture);
/*    window.self.name = "TermMain";
    open_window("termPopup","","30","30","1050","600");	
    document.form1.target = "termPopup";//*/
    document.form1.action='/servlet/controller.off.OffSubjectAdminServlet';
    document.form1.p_process.value = 'insertPage';
    document.form1.submit();

    document.form1.target = window.self.name;
}
function DeleteSubject() {
	  if (confirm("과목정보를 삭제 하시겠습니까?")) {
	    document.form1.p_process.value = 'delete';
	    document.form1.submit();
	  }
	}

// 검색
function whenSelection(ss_action) {
	if (ss_action=="go")	{
		top.ftop.setPam();
	}
	if(checkNotNull($('#s_subjcode').val(), '과정') || checkNotNull($('#s_subjseq').val(), '차수')) {//<font color="red">★</font>
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


//선택단추 실행시
function chkeckall(o){
    if(o.checked){
      whenAllSelect();
    }
    else{
      whenAllSelectCancel();
    }
}

function fnSetInfo(subj, year, subjseq, term, lecture) {
	$("#p_subj").val(subj);
	$("#p_year").val(year);
	$("#p_subjseq").val(subjseq);
	$("#p_term").val(term);
	$("#p_lecture").val(lecture);
}

//전체선택
function whenAllSelect() {
  if(document.form1.all['d_lecture'] == '[object]') {
	if (document.form1.d_lecture.length > 0) {
	  for (i=0; i<document.form1.d_lecture.length; i++) {
		document.form1.d_lecture[i].checked = true;
	  }
	} else {
	  document.form1.d_lecture.checked = true;
	}
  }
}

//전체선택취소
function whenAllSelectCancel() {
  if(document.form1.all['d_lecture'] == '[object]') {
	if (document.form1.d_lecture.length > 0) {
	  for (i=0; i<document.form1.d_lecture.length; i++) {
		document.form1.d_lecture[i].checked = false;
	  }
	} else {
	  document.form1.d_lecture.checked = false;
	}
  }
}

function subjSearch() {
	changes_subjcode($("#s_year").val(),$("#s_upperclass").val(),$("#s_middleclass").val(),$("#s_lowerclass").val());
}

function subjSelected() {
	if (document.all.s_subjcode.value.length > 0) {
		 $('#insertButton').show();
	}
	else $('#insertButton').hide();
}

-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="subjSelected();">
<form name="form1" method="post" action="/servlet/controller.off.OffSubjectAdminServlet">
  <input type="hidden" name="p_process"  value="">
  <input type="hidden" id="p_subj"	name="p_subj"	 value="">
  <input type="hidden" id="p_subjseq"	name="p_subjseq"	 value="">
  <input type="hidden" id="p_year"	name="p_year"	 value="">
  <input type="hidden" id="p_lecture"	name="p_lecture"	 value="">
  <input type="hidden" id="p_term"	name="p_term"	 value="">
  <script type="text/javascript">
<!--
function whenExcel() {
	window.self.name = "proposeExcel";
	document.form1.target = 'mainFrame';
	$("#isExcel").val("true");
	document.form1.s_action.value = "go";
	document.form1.p_process.value = 'listPage';
	document.form1.submit();
	$("#isExcel").val("false");
	document.form1.target = window.self.name;
}
-->
</script>
<input type="hidden" name="isExcel" id="isExcel" value="false">

  <input type="hidden" name="s_action"  value="">
  <input type="hidden" name="p_orderColumn">
  <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
<table id="listForm" width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
	<td align="center" valign="top">
	  <!----------------- title 시작 ----------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
		  <td><img src="/images/admin/portal/s.1_off03.gif" ></td>
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
														$("#s_lowerclass").val()
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
												<font color="red">★</font>과정명 <kocca_select:select name="s_subjcode" sqlNum="off.subj.terms"
													param="<%= ss_year %>"  param2="<%= ss_upperclass %>"  param3="<%= ss_middleclass %>"  param4="<%= ss_lowerclass %>"
													onChange="subjSelected();changes_subjseq($('#s_year').val(),this.value);setTimeout('change_subjseq()', 400);" attr=" " selectedValue="<%= ss_subj %>" isLoad="true" all="no" />
												차수 <kocca_select:select name="s_subjseq" sqlNum="off.subjseq" param="<%= ss_year %>"  param2="<%= ss_subj %>"
													onChange="" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
												과정검색 <input type="text" name="s_subjsearchkey" size="17" onkeypress="if(event.keyCode=='13') Main_subjcourse();" value="<%=s_subjsearchkey%>">
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
	  <table id="insertButton" cellpadding="0" cellspacing="0" class="table1" <%= ss_subj.trim().length()>0?"":"style=\"display='none'\"" %>>
		<tr>
			<td>&nbsp;</td>
		  <td align="center" height="20" width="90"><img alt="'과목등록" style='CURSOR:hand' src="/images/admin/button/btn_add.gif"  onClick="fnInsert($('#s_subjcode').val(), $('#s_year').val(), $('#s_subjseq').val());" ></td>
		  <td align="center" height="20" width="90"><img style='CURSOR:hand' alt="삭제" src="/images/admin/button/btn_del.gif" onClick="DeleteSubject();"/></td>
		  <td align="right" height="20" width="90"><img style='CURSOR:hand' src="/images/admin/button/btn_excelprint.gif" alt="엑셀출력" onclick="whenExcel()"/></td>
		</tr>
		<tr>
		  <td height="3"></td>
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
		  <td class="table_title" width="34">학기</td>
		  <td class="table_title">과목명</td>
		  <td class="table_title">학점</td>
		  <td class="table_title">담당교수</td>
		  <td class="table_title">사용여부</td>
		  <td class="table_title">선택<input type="checkbox" name="p_chkeckall" onClick="javascript:chkeckall(this)"></td>
		</tr>
<%  if(ss_action.equals("go") && list!= null){	//go button 선택시만 list 출력

	DataBox data = null;
	for (int i=0; i<list.size(); i++) {
		data  = (DataBox)list.get(i); 
%>
		<tr id="<%=data.get("d_subjnm")%>">
		  <td class="table_01"><%=String.valueOf(i+1)%></td>
		  <td class="table_01" ><%=data.get("d_subjnm")%></td>
		  <td class="table_02_1"><%=data.get("d_subjseq")%></td>
		  <td class="table_02_1"><%=data.get("d_termstart")%>~<%=data.get("d_termend")%></td>
		  <td class="table_02_1"><a href="javascript:fnInsert('<%=data.get("d_subj")%>','<%=data.get("d_year")%>','<%=data.get("d_subjseq")%>','<%=data.get("d_term")%>', '<%= data.get("d_lecture") %>');" class='b'><%=data.get("d_lecturenm")%></a></td>
		  <td class="table_02_1"><%=data.get("d_point")%></td>
		  <td class="table_02_1"><%=data.get("d_tutornm")%></td>
		  <td class="table_02_1"><%=data.get("d_isuse")%></td>
		  <td class="table_02_1"><input type="checkbox" name="d_lecture" id="d_lecture" value="<%= data.get("d_lecture") %>" onclick="fnSetInfo('<%=data.get("d_subj")%>','<%=data.get("d_year")%>','<%=data.get("d_subjseq")%>','<%=data.get("d_term")%>', '<%= data.get("d_lecture") %>');"/></td>
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
