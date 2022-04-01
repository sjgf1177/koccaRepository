<%
//**********************************************************
//  1. 제		목: 수강생관리
//  2. 프로그램명: za_off_grseq_L.jsp
//  3. 개		요: 수강생관리
//  4. 환		경: JDK 1.5
//  5. 버		젼: 1.0
//  6. 작		성: swchoi 2009.11.18
//  7. 수		정: 
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
<%!
	private final String NULL = "";
	private String chkAcceptResult(ArrayList<DataBox> acceptResultList, String p_acceptResult) {
		if(p_acceptResult.length() == 2 && (p_acceptResult.charAt(0) == 'N' || p_acceptResult.charAt(1) == 'Y' || p_acceptResult.charAt(1) == 'N')) {
			return SelectBoxBean.getSelectedString(acceptResultList, "p_acceptResult", p_acceptResult, NULL);
		}
		else
			return SelectBoxBean.getSelectBoxString(acceptResultList, "p_acceptResult", p_acceptResult, NULL);
	}
%>
<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");

	//DEFINED in relation to select START
	String  ss_year = box.getStringDefault("s_year", FormatDate.getDate("yyyy"));	//연도
	String  ss_subj = box.getString("s_subjcode");											//과정
	String  ss_subjseq = box.getString("s_subjseq");											//과정차수
	String  ss_subjyear = box.getString("s_subjyear");	  //과정년도
	if( ss_year.equals("") ) {
		ss_year = Integer.toString((new java.util.Date().getYear()) + 1900);
	}
	String  ss_upperclass = box.getStringDefault("s_upperclass", "S01");				//과정대분류
	String  ss_middleclass = box.getStringDefault("s_middleclass", "ALL");			//과정중분류
	String  ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");				//과정소분류
	String  ss_action	= box.getString("s_action");
	String s_subjsearchkey = box.getString("s_subjsearchkey");
	//DEFINED in relation to select END
	ArrayList list		= null;
	ArrayList<DataBox> acceptResultList = null;
	if(ss_action.equals("go")){	//go button 선택시만 list 출력
		list = (ArrayList)request.getAttribute("resultList");
		acceptResultList = (ArrayList<DataBox>) request.getAttribute("acceptResultList");
	}

	String  v_orderType	= box.getStringDefault("p_orderType"," asc"); //정렬순서
%>
<html>
<head>
<title> 차수관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script> 
<SCRIPT LANGUAGE="JavaScript">
//삭제 
function whenStudentDelete() {
    var count=0;
    $("[name='p_checks']").each(function(){
       var chk_truer=$(this).attr("checked")
        if(chk_truer)
        {
            count++;
            return false;
        }
    });

    if(count==0)
    {
        alert("삭제하실 데이타를 선택 하여 주십시오.");
        return;
    }

    if(!confirm("정말 삭제하시겠습니까?")) return;
	document.form1.p_process.value = 'delete';
	document.form1.action="/servlet/controller.off.OffStudentManageAdminServlet";
	document.form1.submit();
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
	if(document.form1.all['p_checks'] == '[object]') {
		if (document.form1.p_checks.length > 0) {
			for (i=0; i<document.form1.p_checks.length; i++) {
			document.form1.p_checks[i].checked = o.checked;
			}
		} else {
			document.form1.p_checks.checked = o.checked;
		}
	}
}


//전체 옵션선택 버튼
function whenChangeAll(idx){
	if(!confirm("변경가능한 승인상태가 모두 변경됩니다.\n\n계속하시겠습니까?")) return;

	for(var i=0;i<document.form1.length;i++){
		if(document.form1.elements[i].type=="select-one"){
			document.form1.elements[i].selectedIndex=idx;
		}
	}
	alert("[확인]버튼을 누르셔야만 저장됩니다");
}
function whenApprovalProcess() {
	document.form1.p_process.value="insert";
	document.form1.submit();
}

//대상자 추가
function whenStudentAdd() {

    var frm = document.form1;

    if (document.form1.s_subjcode.value == 'ALL' || document.form1.s_subjcode.value == '') {
      alert("과정을 선택하세요.");
      return ;
    }

    if (document.form1.s_subjseq.value=='ALL') {
        alert("차수를 선택하세요.");
        return;
    }

    frm.p_newyear.value = frm.s_year.value;
    frm.p_newsubj.value = frm.s_subjcode.value;
    frm.p_newsubjseq.value = frm.s_subjseq.value;

    window.self.name = "AddStudent";

    open_window("openAddStudent","","100","100","700","600","","","","yes","no");
    document.form1.target = "openAddStudent";
    document.form1.action='/servlet/controller.off.OffStudentManageAdminServlet';
    document.form1.p_process.value = 'studentListPage';
    document.form1.submit();
    document.form1.target = window.self.name;
}
function whenExcel() {
	if(checkNotNull($('#s_subjcode').val(), '과정') || checkNotNull($('#s_subjseq').val(), '차수')) {//<font color="red">★</font>
		return;
	}
	document.form1.target = 'mainFrame';
	$("#isExcel").val("true");
	document.form1.s_action.value = "go";
	document.form1.p_process.value = 'listPage';
	document.form1.submit();
	$("#isExcel").val("false");
	document.form1.target = '_self';
}
// SMS 전송
function whenSMS() {

  if (!hasCheckedBox(document.form1.p_checks)) {
		alert('SMS를 발송할 학습자를 선택하세요');
		return;
  }
  ff =document.form1;

  ff.p_msubjnm.value=$("#s_subjcode").text();
  ff.p_mseqgrnm.value=$("#s_subjseq").text();
  ff.p_msubj.value=$("#s_subjcode").val();
  ff.p_myear.value=$("#s_year").val();
  ff.p_msubjseq.value=$("#s_subjseq").val();
  ff.p_touch.value = "09";

  window.self.name = "StudentMemberList";
  open_window("openSMS","","100","100","620","354");
  document.form1.target = "openSMS";
  document.form1.action='/servlet/controller.study.StudentStatusAdminServlet';
  document.form1.p_process.value = 'SendSMS';
  document.form1.submit();
  document.form1.target = window.self.name;
}
// 메일발송
function whenFreeMail() {
  if (!hasCheckedBox(document.form1.p_checks)) {
		alert('메일을 발송할 학습자를 선택하세요');
		return;
  }

  ff =document.form1;

  ff.p_msubjnm.value=$("#s_subjcode").text();
  ff.p_mseqgrnm.value=$("#s_subjseq").text();
  ff.p_msubj.value=$("#s_subjcode").val();
  ff.p_myear.value=$("#s_year").val();
  ff.p_msubjseq.value=$("#s_subjseq").val();


  window.self.name = "StudentMemberList";
  open_window("openFreeMail","","10","10","800","650");
  document.form1.target = "openFreeMail";
  document.form1.action='/servlet/controller.study.StudyTotalStatusServlet';
  document.form1.p_process.value = 'SendFreeMail';
  document.form1.submit();
  document.form1.target = window.self.name;
}


function goSuRoyJeung() {
	if (confirm("정말 출력하시겠습니까?")) {
		if(document.form1.all['p_checks'] == '[object]') {
			if (document.form1.p_checks.length > 0) {
				for (i=0; i<document.form1.p_checks.length; i++) {
					if (document.form1.p_checks[i].checked)  {
						var va = document.form1.p_checks[i].value.split(",");
						suRoyJeung(va[1],va[2],va[3],va[0], i);
					}
				}
			} else {
				if (document.form1.p_checks.checked)  {
					var va = document.form1.p_checks.value.split(",");
					suRoyJeung(va[1],va[2],va[3],va[0], 0);
				}
			}
		}
	}
	else alert("취소하셨습니다");
}
//수료증 출력
function suRoyJeung(subj,year,subjseq,userid, popindex){
	window.self.name = "SuryoJeung";
	//var new_Open = window.open("","openSuryoJeungPrint",'scrollbars=no,width=601,height=850,scrollbars=no,resizable=NO');
	var new_Open = window.open("","openSuryoJeungPrint"+popindex,'scrollbars=yes,width=830,height=920,scrollbars=yes,resizable=NO');
	document.form1.target = "openSuryoJeungPrint"+popindex;
	document.form1.action = "/servlet/controller.polity.DiplomaAdminServlet";
	document.form1.p_subj.value = subj;
	document.form1.p_year.value = year;
	document.form1.p_scsubjseq.value = subjseq;
	document.form1.p_userid.value = userid;
	document.form1.p_process.value = "DiplomaOffPrint";
	document.form1.submit();
	document.form1.target = window.self.name;
}

function goGraduated( grd ) {

	if (!hasCheckedBox(document.form1.p_checks)) {
		alert('대상을 선택하세요.');
		return;
	}

	var v_subj = null;
	var v_subjseq = null;
	var v_year = null;
	var v_userid = '';
	var grdnm = null;
	var cnt = 1;

	if( grd == 'Y') {
		grdnm = "수료";
	} else {
		grdnm = "미수료";
	}

	if( confirm( grdnm + "처리 하시겠습니까?" ) ) {
        v_subj="";
        v_userid="";

        $("[name='p_checks']:checked").each(function(){
            var r=$(this).val().split(",");
            v_userid+=r[0]+"|";
            if(v_subj=="")
            {
                v_subj = r[1];
                v_subjseq = r[3];
                v_year = r[2];
            }
        });

        if(v_userid!="")
        {
//		if(document.form1.all['p_checks'] == '[object]') {
//			if (document.form1.p_checks.length > 0) {
//				for (i=0; i<document.form1.p_checks.length; i++) {
//					if (document.form1.p_checks[i].checked)  {
//
//						var va = document.form1.p_checks[i].value.split(",");
//
//						v_userid += va[0];
//
//						if( i != document.form1.p_checks.length -1 ) {
//							v_userid += "|";
//						}
//
//						if( cnt == 1 ) {
//							v_subj = va[1];
//							v_subjseq = va[3];
//							v_year = va[2];
//						}
//
//						cnt++;
//					}
//				}

				document.form1.action = "/servlet/controller.off.OffStudentManageAdminServlet";
				document.form1.p_process.value = "updategraduated";
				document.form1.p_subj.value = v_subj;
				document.form1.p_subjseq.value = v_subjseq;
				document.form1.p_year.value = v_year;
				document.form1.p_userid.value = v_userid;
				document.form1.p_graduated.value = grd;

				document.form1.submit();
//        }
		}
	}
}

//신고자 출력
function whenApplicantPrint()
{
    if (!hasCheckedBox(document.form1.p_checks)) {
		alert('대상을 선택하세요.');
		return;
	}
    
    open_window("ApplicantPrintWin","","10","10","800","650");
    document.form1.target = "ApplicantPrintWin";
    document.form1.p_process.value = 'ApplicantPrint';
    document.form1.action='/servlet/controller.off.OffStudentManageAdminServlet';
    document.form1.submit();
}

//수료자 출력
function whenCompletePrint()
{
   if (!hasCheckedBox(document.form1.p_checks)) {
		alert('대상을 선택하세요.');
		return;
	}

    open_window("CompletePrintWin","","10","10","800","650");
    document.form1.target = "CompletePrintWin";
    document.form1.p_process.value = 'CompletePrint';
    document.form1.action='/servlet/controller.off.OffStudentManageAdminServlet';
    document.form1.submit();
}
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id="existSubjSeqDiv"></div>
<div id=minical OnClick="this.style.display='none';" oncontextmenu='return false'
ondragstart='return false' onselectstart='return false'
style="background : buttonface; margin: 5; margin-top: 2;border-top: 1
solid buttonhighlight;border-left: 1 solid buttonhighlight;border-right: 1
solid buttonshadow;border-bottom: 1 solid buttonshadow;width:155;display:none;position: absolute; z-index: 99">
</div>

<form name="form1" method="post" action="/servlet/controller.off.OffStudentManageAdminServlet">
	<input type="hidden" name="p_process"  value="">
	<input type="hidden" name="p_year"	 value="">
	<input type="hidden" name="p_subj"	 value="">
	<input type="hidden" name="p_subjseq"	 value="">
	<input type="hidden" name="p_scsubjseq"	 value="">
	<input type="hidden" name="p_subjnm"	value="">
	<input type="hidden" name="p_userid"	value="">
	<input type="hidden" name="p_kind"	value="">
	<input type="hidden" name="p_grcode"	value="N000001">
    <input type='hidden' name='p_kind'      value="">
	<input type="hidden" name="isExcel" id="isExcel" value="false">
	<input type="hidden" name="s_action"  value="">
	<input type="hidden" name="p_orderColumn">
	<input type="hidden" name="p_orderType" value="<%=v_orderType%>">
	
	<input type="hidden" name="p_newyear"     value="">
    <input type="hidden" name="p_newsubj"     value="">
    <input type="hidden" name="p_newsubjseq"  value="">

<!-- SMS용 변수 -->
	<input type="hidden" name="p_touch"  value="">
	<input type="hidden" name="p_msubjnm"  value="">
	<input type="hidden" name="p_mseqgrnm"  value="">
	<input type="hidden" name="p_msubj"  value="">
	<input type="hidden" name="p_myear"  value="">
	<input type="hidden" name="p_msubjseq"  value="">
	<input type="hidden" name="p_offlinemail" value='true'>
<!-- 수료/미수료 -->
	<input type="hidden" name="p_graduated"  value="Y">
<!-- 종료 -->
<table id="listForm" width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
	<td align="center" valign="top">
		<!----------------- title 시작 ----------------->
		<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
			<td><img src="/images/admin/portal/s.1_off05.gif" ></td>
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
													onChange="changes_subjseq($('#s_year').val(),this.value);setTimeout('change_subjseq()', 400);" attr=" " selectedValue="<%= ss_subj %>" isLoad="true" all="no" />
												차수 <kocca_select:select name="s_subjseq" sqlNum="off.subjseq" param="<%= ss_year %>"  param2="<%= ss_subj %>"
													onChange="" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="no" />
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
		</table><br/>
		<!----------------- 추가 버튼 시작 ----------------->
		<!----------------- 모두승인, 모두승인취소, 확인 버튼 시작 ----------------->
		<table width="97%" cellpadding="4" cellspacing="0" border="0">
			<tr>
				<td class="sub_title"><img src="/images/admin/common/icon.gif" style="vertical-align: middle;">과정기준조회</td>
				<td align="right">
					<a href="javascript:whenSMS()"><img src="/images/admin/button/btn_sms.gif" border="0"></a>
					<a href="javascript:whenFreeMail()"><img src="/images/admin/button/btn_mail.gif" border="0"></a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:whenStudentAdd()"><img src="/images/admin/button/btn_peopleplus.gif" border="0"></a>
					<a href="javascript:whenStudentDelete()"><img src="/images/admin/button/btn_del.gif" border="0"></a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
					<a href="javascript:whenApplicantPrint()"><img src="/images/admin/button/btn_applicant.gif" border="0"></a>
					<a href="javascript:whenCompletePrint()"><img src="/images/admin/button/btn_complete.gif" border="0"></a>
					<a href="javascript:goSuRoyJeung()" class="board_btn2"><img src='/images/admin/button/btn_suryoprint.gif' border='0'></a>&nbsp;&nbsp;&nbsp;
					<a href="javascript:goGraduated('Y')"><img src="/images/admin/button/btn_graduated_y.gif" border="0"></a>
					<a href="javascript:goGraduated('N')"><img src="/images/admin/button/btn_graduated_n.gif" border="0"></a>
				</td>
			</tr>
		</table>
		<!----------------- 모두승인, 모두승인취소, 확인 버튼 끝 ----------------->

		<!----------------- 과정 관리 시작 ----------------->
		<table cellspacing="1" cellpadding="5" class="table_out">
		<tr>
			<td colspan="13" class="table_top_line"></td>
		</tr>
		<tr>
			<td class="table_title" width="34"><b>NO</b></td>
			<td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">과정명</a></td>
			<td class="table_title" width="34"><a href="javascript:whenOrder('subjseq')" class="e">차수</a></td>
			<td class="table_title"><a href="javascript:whenOrder('userid')" class="e">ID</a></td>
			<td class="table_title"><a href="javascript:whenOrder('name')" class="e">성명</a></td>
			<td class="table_title">성별</td>
			<td class="table_title">학번</td>
			<td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">입학일자</a></td>
			<td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">교육기간</a></td>
			<td class="table_title">상태</td>
			<td class="table_title">선택<input type="checkbox" name="p_chkeckall" onClick="javascript:chkeckall(this)"></td>
		</tr>
<%  if(ss_action.equals("go") && list!= null){	//go button 선택시만 list 출력
	DataBox data = null;
	String acceptResult = null;
	for (int i=0; i<list.size(); i++) {
		data  = (DataBox)list.get(i);
%>
		<tr id="<%=data.get("d_subjnm")%>">
			<td class="table_01">
				<%=String.valueOf(i+1)%>
			</td>
			<td class="table_01"><%=data.get("d_subjnm") %></td>
			<td class="table_01"><%=data.get("d_subjseq") %></td>
			<td class="table_01"><%=data.get("d_userid") %></td>
			<td class="table_01"><%=data.get("d_name") %></td>
			<td class="table_01"><%=data.get("d_sex") %></td>
			<td class="table_01"><%=data.get("d_isterm").equals("N") ? "-" : data.get("d_studentno") %></td>
			<td class="table_01"><%=data.getDate("d_confirmdate") %></td>
			<td class="table_01"><%=data.getDate("d_edustart") %>~<%=data.getDate("d_eduend") %></td>
			<td class="table_01"><%=data.get("d_stustatusnm").equals("") ? "미처리" : data.get("d_stustatusnm")%></td>
			<td class="table_02_1"><input type="checkbox" name="p_checks" value="<%=data.get("d_userid") %>,<%=data.get("d_subj") %>,<%=data.get("d_year") %>,<%=data.get("d_subjseq") %>,<%=data.get("d_grcode") %>"></td>
<!-- 			<td class="table_02_1"><input type="checkbox" name="p_checks" id="p_checks" value="<%= data.get("d_studentno") %>"/></td> -->
		</tr>
<%  } %>

		<% if (list.size()==0) {%>
			<tr><td colspan=13 class="table_02_1">검색된 결과가 없습니다.</td></tr>  
		<% } %>
<%} %>
</form>
		</table>
		<!----------------- 과정 관리 끝 ----------------->
		<br>
	</td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</body>
</html>
