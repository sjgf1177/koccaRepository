<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import="com.credu.scorm.DateUtil" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.common.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%
//이 페이지를 볼수 있는 권한 : ../inc/top_menu.jsp에서 권한을 검증하여 에러가 있으면 session에러 페이지로 보낸다.
//반드시 수정해줘야 함.
int pageAuth = 5;
RequestBox box = (RequestBox)request.getAttribute("requestbox");
DataBox resultData = (DataBox) request.getAttribute("resultData");

//DEFINED in relation to select START
String  ss_year = box.getString("s_year");	  //연도
String  ss_subj = box.getString("s_subjcode");	  		//과정
String  ss_subjseq = box.getString("s_subjseq");	  //학기
String  ss_subjyear = box.getString("s_subjyear");	  //과정년도
if( ss_year.equals("") ) {
	ss_year = Integer.toString((new java.util.Date().getYear()) + 1900);
}
String  ss_upperclass = box.getStringDefault("s_upperclass", "S01");	  //과정대분류
String  ss_middleclass = box.getStringDefault("s_middleclass", "ALL");	//과정중분류
String  ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");	  //과정소분류
String  ss_action	= box.getString("s_action");
String s_subjsearchkey = box.getString("s_subjsearchkey");
String v_gyear  = box.getStringDefault("p_gyear", FormatDate.getDate("yyyy"));
String  v_orderType	= box.getStringDefault("p_orderType"," asc"); //정렬순서

if(resultData == null) resultData = new DataBox("resultData");
List<DataBox> resultList = (List<DataBox>) resultData.getObject("resultList");
if(resultList==null) resultList = new ArrayList();
final String D_USERID = "d_userid";
final String D_NAME = "d_name";
final String D_LECTURE = "d_lecture";
final String D_LECTURENM = "d_lecturenm";
%>
<html>
<head>
<title>오프라인 |성적관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>

<script language="javascript">
// 검색
function whenSelection(ss_action) {
	if (ss_action=="go")	{
		top.ftop.setPam();
	}
	if(checkNotNull($('#s_subjcode').val(), '과정') || checkNotNull($('#s_subjseq').val(), '차수')) {//<font color="red">★</font>
		return;
	}
		document.form1.s_action.value = ss_action;
		document.form1.p_process.value = 'selectOneTerm';
		document.form1.submit();
}
function subjSearch() {
	changes_subjcode($("#s_year").val(),$("#s_upperclass").val(),$("#s_middleclass").val(),$("#s_lowerclass").val());
}

//정렬
function whenOrder(column) {
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

	document.form1.s_action.value = "go";
	document.form1.p_process.value = 'selectOneTerm';	
	document.form1.p_orderColumn.value = column;
	
	document.form1.submit();
}
function scrolling(obj){ 
    document.getElementById("leftDiv").scrollTop = obj.scrollTop; 
    document.getElementById("headDiv").scrollLeft = obj.scrollLeft; 
} 
function showDetail(p_userid) {
	whenSimpleLoad(p_userid);
	$('#detailView').fadeIn(350);
	var x = event.clientX + document.body.scrollLeft-140;
	var y = event.y + document.body.scrollTop - 40;
	document.getElementById("detailView").style.pixelLeft = x;
	document.getElementById("detailView").style.pixelTop = y;
}
$(function(){
	  // Document is ready
	$("#detailView").hover(
			function () {
			},
			function () {
				$(this).fadeOut(250);
			}
	);
});
//출석상태조회
function whenSimpleLoad(p_userid) {
	$("#p_userid").val(p_userid);
	$.post("/servlet/controller.common.TagServlet"
			, {	  sqlNum:"off.gradeOneTermSelect",rerurnFunction:"readData", type:6
				, p_subjcode:"<%= ss_subj %>"
				, p_year:"<%= ss_year %>"
				, p_subjseq:"<%= ss_subjseq %>"
				, p_userid:p_userid
			}
			, function(data) {
				$("#ajaxDiv").html(data);
			});
}
//출석상태저장
function whenSimpleSave(type) {
	if(type==1) {
		/*	삭제 후 인서트	*/
		$.post("/servlet/controller.common.AjaxServlet"
				, {	  sqlNum:"lecture.gradeOneTerm.insert", deleteSqlNum:"lecture.gradeOneTerm.delete", rerurnFunction:"ready"
					, p_subjcode:"<%= ss_subj %>"
					, p_subj:"<%= ss_subj %>"
					, p_year:"<%= ss_year %>"
					, p_subjseq:"<%= ss_subjseq %>"
					, p_term:1
					, p_userid:$("#p_userid").val()
					, p_tstep:$("#p_tstep").val()
					, p_mtest:$("#p_mtest").val()
					, p_ftest:$("#p_ftest").val()
					, p_htest:$("#p_htest").val()
					, p_report:$("#p_report").val()
					, p_etc1:'0'
					, p_etc2:'0'
				}
				, function(data) {
					$("#ajaxDiv").html(data);
				});
	}
	else {
		if(!confirm("해당 내용으로 수료처리 하시겠습니까?")) return;
		document.form1.s_action.value = 'go';
		document.form1.p_process.value = 'updateOneTerm';
		document.form1.submit();
	}
}
function readData(data) {
	$("#p_userid").val(data.d_userid);

	$("#t_name").html(data.d_name);
	$("#t_userid").html(data.d_userid);

	$("#p_tstep").val(data.d_tstep);
	$("#p_mtest").val(data.d_mtest);
	$("#p_ftest").val(data.d_ftest);
	$("#p_htest").val(data.d_htest);
	$("#p_report").val(data.d_report);
}
function ready() {
	$("#detailView").fadeOut(250);
	return;
}
function reload() {
	whenSelection('go');
}

//전체 옵션선택 버튼
function whenChangeAll(idx, selectedText){
	if(!confirm("변경가능한 수료상태가 모두 ["+selectedText+"]로 변경됩니다.\n\n계속하시겠습니까?")) return;

	for(var i=0;i<document.form1.length;i++){
		if(document.form1.elements[i].type=="select-one" && document.form1.elements[i].name=="p_isgraduated"){
			document.form1.elements[i].selectedIndex=idx;
		}
	}
	alert("[수료여부저장]버튼을 누르셔야만 저장됩니다");
}

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.off.OffExamAdminServlet">

<div id="ajaxDiv"></div>

  <input type="hidden" name="p_process"  value="">
  <input type="hidden" name="s_action"  value="">
  <input type="hidden" name="p_orderColumn">
  <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
  <input type="hidden" id="p_subj" name="p_subj"  value="<%= ss_subj %>">
  <input type="hidden" id="p_year" name="p_year"  value="<%= ss_year %>">
  <input type="hidden" id="p_subjseq" name="p_subjseq"  value="<%= ss_subjseq %>">
  <input type="hidden" id="isArray" name="isArray"  value="true">
  
<div id="detailView" style="position: absolute; z-index: 99;display=none">
<input type="hidden" class="input" name="p_userid" id="p_userid" size="10" maxlength="10" value="">
<table width="300" border="0" cellspacing="0" cellpadding="0" height="300">
<tr>
	<td align="center" valign="top">
		<table cellspacing="1" cellpadding="5" class="table_out" border="0">
		  <tr> 
			<td colspan="2" class="table_top_line"></td>
		  </tr>
		  <tr> 
			<td class="table_01">성명</td>
			<td class="table_02_1" id="t_name"></td>
		  </tr>
		  <tr> 
			<td class="table_01">ID</td>
			<td class="table_02_1" id="t_userid">ID</td>
		  </tr>
		  <tr> 
			<td class="table_01">진도율(<%= resultData.get("d_wstep") %>%)</td>
			<td class="table_02_1" >
				 <input type="text" class="input" name="p_tstep" id="p_tstep" size="3" maxlength="3" value=""> 점
			</td>
		  </tr>
		  <tr> 
			<td class="table_01">중간평가(<%= resultData.get("d_wmtest") %>%)</td>
			<td class="table_02_1" >
				 <input type="text" class="input" name="p_mtest" id="p_mtest" size="3" maxlength="3" value=""> 점
			</td>
		  </tr>
		  <tr> 
			<td class="table_01">최종평가(<%= resultData.get("d_wftest") %>%)</td>
			<td class="table_02_1" >
				 <input type="text" class="input" name="p_ftest" id="p_ftest" size="3" maxlength="3" value=""> 점
			</td>
		  </tr>
		  <tr> 
			<td class="table_01">형성평가(<%= resultData.get("d_whtest") %>%)</td>
			<td class="table_02_1" >
				 <input type="text" class="input" name="p_htest" id="p_htest" size="3" maxlength="3" value=""> 점
			</td>
		  </tr>
		  <tr> 
			<td class="table_01">리포트(<%= resultData.get("d_wreport") %>%)</td>
			<td class="table_02_1" >
				 <input type="text" class="input" name="p_report" id="p_report" size="3" maxlength="3" value=""> 점
			</td>
		  </tr>
		  <tr> 
			<td colspan="2" class="table_top_line"></td>
		  </tr>
		  <tr> 
			<td class="table_01" colspan="2"><button onclick="whenSimpleSave(1);">수정</button></td>
		  </tr>
		 </table>
	</td>
</tr>
</table>
</div>
<table id="listForm" width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
	<td align="center" valign="top">
	  <!----------------- title 시작 ----------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
		  <td><img src="/images/admin/portal/s.1_off07.gif" ></td>
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
				  <table width="99%" cellspacing="0" cellpadding="0">
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
											function fnSearchBoxs_subjterm() {
												if(isFirst>2)
													changes_subjterm($('#s_subjcode').val(), $('#s_year').val(), $('#s_subjseq').val());
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
												<font color="red">★</font>과정명 <kocca_select:select name="s_subjcode" sqlNum="off.subj.oneTerm"
													param="<%= ss_year %>"  param2="<%= ss_upperclass %>"  param3="<%= ss_middleclass %>"  param4="<%= ss_lowerclass %>" param5="<%= ss_year %>"
													onChange="changes_subjseq($('#s_year').val(),this.value);setTimeout('change_subjseq()', 400);" attr=" " selectedValue="<%= ss_subj %>" isLoad="true" all="no" />
												<font color="red">★</font>차수 <kocca_select:select name="s_subjseq" sqlNum="off.subjseq" param="<%= ss_year %>"  param2="<%= ss_subj %>"
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
	  </table>
	  <br>
		<!----------------- 추가 버튼 시작 ----------------->
		<!----------------- 모두승인, 모두승인취소, 확인 버튼 시작 ----------------->
<% if(resultData != null && resultData.size() > 0){	%>
		<table width="97%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td width="1%"><img src="/images/admin/common/icon.gif" ></td>
			<td>
				[교육기간]
					<%= resultData.getDate("d_edustart") %>~<%= resultData.getDate("d_eduend") %><br/>
				[수료기준]
					총점 : <%= resultData.getInt("d_gradscore") %>
					진도율 : <%= resultData.getInt("d_gradstep") %>
					중간평가 : <%= resultData.getInt("d_gradexam") %>
					최종평가 : <%= resultData.getInt("d_gradftest") %>
					형성평가 : <%= resultData.getInt("d_gradhtest") %>
					리포트 : <%= resultData.getInt("d_gradreport") %><br/>
				[수료현황]
					수료 : <%= resultData.getInt("d_isgraduatedy") %>명
					미수료 : <%= resultData.getInt("d_isgraduatedn") %>명
			</td>
			<td width="8"></td>
			<td align="right" height="20"><a href="javascript:whenChangeAll(0, '미수료')"><img src="/images/admin/portal/btn_all_confirm_cancel.gif" border="0"></a></td>
			<td align="right" height="20"><a href="javascript:whenChangeAll(1, '수료')"><img src="/images/admin/portal/btn_all_confirm.gif" border="0"></a></td>
			<td align="right" height="20"><a href="javascript:whenSimpleSave(2)"><img src="/images/admin/portal/s.2_02102.gif" border="0"></a></td>
		  </tr>
		  <tr>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
			<td height="3"></td>
		  </tr>
		</table>
		<!----------------- 모두승인, 모두승인취소, 확인 버튼 끝 ----------------->
<%	}	%>
	  <!----------------- 과정 관리 시작 ----------------->
	  
	  <table width="100%" cellspacing="1" cellpadding="5" class="table_out">
			<tr>
			  <td colspan="11" class="table_top_line"></td>
			</tr>
			  <tr>
			  	<td width="40" height="20" align="center" class="table_title">No</td>
			   	<td width="70" align="center" class="table_title">ID</td>
			  	<td width="70" align="center" class="table_title">성명</td>
				<td width="120" align="center" class="table_title">설문응시여부</td>
			  	<td width="70" align="center" class="table_title">총점</td>
			  	<td width="70" align="center" class="table_title">출석률<br/>(<%= resultData.get("d_wstep") %>%)</td>
			  	<td width="70" align="center" class="table_title">중간평가<br/>(<%= resultData.get("d_wmtest") %>%)</td>
			  	<td width="70" align="center" class="table_title">최종평가<br/>(<%= resultData.get("d_wftest") %>%)</td>
			  	<td width="70" align="center" class="table_title">형성평가<br/>(<%= resultData.get("d_whtest") %>%)</td>
			  	<td width="70" align="center" class="table_title">리포트<br/>(<%= resultData.get("d_wreport") %>%)</td>
			  	<td align="center" class="table_title">수료여부</td>
			  </tr>
   <% if(resultData != null && resultData.size() > 0){
	   int i = 1;%> 
			<!--본문 테이블 -->
	<%	for(DataBox resultBox : resultList) {	 %>
			  <tr>
				<td height="20" align="center" class="table_02_1"><%= i++ %></td>
			   	<td align="center" class="table_02_1"><input type="hidden" name="p_userid" id="p_userid" value="<%= resultBox.get("d_userid") %>"/><%= resultBox.get("d_userid") %></td>
			  	<td align="center" class="table_02_1"><%= resultBox.get("d_name") %></td>
				<td align="center" class="table_02_1"><%= resultBox.get("d_isgraduated") %></td>
			  	<td align="center" class="table_02_1"><a onClick="showDetail('<%= resultBox.get("d_userid") %>')" href="#')"><%= resultBox.get("d_score") %></a></td>
			  	<td align="center" class="table_02_1"><a onClick="showDetail('<%= resultBox.get("d_userid") %>')" href="#')"><%= resultBox.get("d_tstep") %></a><br/>(<%= resultBox.get("d_avtstep") %>)</td>
			  	<td align="center" class="table_02_1"><a onClick="showDetail('<%= resultBox.get("d_userid") %>')" href="#')"><%= resultBox.get("d_mtest") %></a><br/>(<%= resultBox.get("d_avmtest") %>)</td>
			  	<td align="center" class="table_02_1"><a onClick="showDetail('<%= resultBox.get("d_userid") %>')" href="#')"><%= resultBox.get("d_ftest") %></a><br/>(<%= resultBox.get("d_avftest") %>)</td>
			  	<td align="center" class="table_02_1"><a onClick="showDetail('<%= resultBox.get("d_userid") %>')" href="#')"><%= resultBox.get("d_htest") %></a><br/>(<%= resultBox.get("d_avhtest") %>)</td>
			  	<td align="center" class="table_02_1"><a onClick="showDetail('<%= resultBox.get("d_userid") %>')" href="#')"><%= resultBox.get("d_report") %></a><br/>(<%= resultBox.get("d_avreport") %>)</td>
			  	<td align="center" class="table_02_1"><select name="p_isgraduated" id="p_isgraduated"><option value="N">미수료</option><option value="Y" <%= resultBox.getSelected("d_isgraduated") %>>수료</option></select></td>
			  </tr>
	<%	}
		 }else{ %>
			  <tr height="20">
			  <td colspan="11" align="center" class="table_01">성적 정보가 없습니다.</td>
			  </tr>  
	<% } %>
		  </table>
			<!--본문 테이블 끝 -->
		 
	  <!----------------- 과정 관리 끝 ----------------->
	  
	  <br>
	</td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>

