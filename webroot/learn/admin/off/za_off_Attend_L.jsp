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
String  ss_subjterm = box.getString("s_subjterm");	  //학기
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
List<DataBox> detail = (List<DataBox>) resultData.getObject("keyList");
List<DataBox> dateList = (List<DataBox>) resultData.getObject("dateList");
if(detail==null) detail = new ArrayList();
if(dateList==null) dateList = new ArrayList();
DataBox detailEntity = null;
StringBuffer sb1 = new StringBuffer();
StringBuffer sb2 = new StringBuffer();
final String DATE_TD = "<td width=\"50\" height=\"10\" align=\"center\" class=\"table_title\">";
final String HEADER_TD = "<td width=\"50\" height=\"10\" align=\"center\" class=\"table_01\">";
final String DETAIL_TD = "<td width=\"50\" height=\"20\" align=\"center\" class=\"table_02_2\" onclick=\"showDetail('";
//final String DETAIL_LINK = "');\">";
//final String DETAIL_LINK_CLOSE = "</a>";
final String DETAIL_LINK_CLOSE = "');\" id='attendCell";
final String DETAIL_LINK_CLOSE2 = "' style=\"align:center\"><img style='width:10px,height:10px' src='/images/admin/portal/stat_";
final String TD_CLOSE = "</td>\n";
final String TD_CLOSE2 = ".gif'/></td>\n";
final String TR = "<tr>\n";
final String TR_CLOSE = "</tr>\n";
final String DIV = "', '";
final String D_USERID = "d_userid";
final String D_STUDENTNO = "d_studentno";
final String D_NAME = "d_name";
final String D_ATTEND = "d_attend";
final String D_ABSENT = "d_absent";
final String D_LATE = "d_late";
final String D_YYMMDD = "d_yyyymmdd";
final String NONE = "4";
for(DataBox entity : dateList) {
	sb1.append(DATE_TD);
	sb1.append(entity.get("d_mmdd"));
	sb1.append(TD_CLOSE);
	sb2.append(DATE_TD);
	sb2.append(entity.get("d_day"));
	sb2.append(TD_CLOSE);
}
%>
<html>
<head>
<title>오프라인 | 출석관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>

<script language="javascript">
// 검색
function whenSelection(ss_action) {
	if (ss_action=="go")	{
		top.ftop.setPam();
	}
	if(checkNotNull($('#s_subjcode').val(), '과정') || checkNotNull($('#s_subjseq').val(), '차수') || checkNotNull($('#s_subjterm').val(), '차수') ) {//<font color="red">★</font>
		return;
	}
		document.form1.s_action.value = ss_action;
		document.form1.p_process.value = 'select';
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
	document.form1.p_process.value = 'select';	
	document.form1.p_orderColumn.value = column;
	
	document.form1.submit();
}
function scrolling(obj){ 
    document.getElementById("leftDiv").scrollTop = obj.scrollTop; 
    document.getElementById("headDiv").scrollLeft = obj.scrollLeft; 
} 
function showDetail(p_userid, p_yyyymmdd) {
	whenSimpleLoad(p_userid, p_yyyymmdd);
	$('#detailView').fadeIn(350);
	  x = event.clientX + document.body.scrollLeft-140;
	  y = event.y + document.body.scrollTop - 40;
		document.getElementById("detailView").style.pixelTop = y;
		document.getElementById("detailView").style.pixelLeft = x;
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
function whenSimpleLoad(p_userid, p_yyyymmdd) {
	$("#p_userid").val(p_userid);
	$.post("/servlet/controller.common.TagServlet"
			, {	  sqlNum:"off.attendSelect",rerurnFunction:"readData", type:6
				, p_subjcode:"<%= ss_subj %>"
				, p_year:"<%= ss_year %>"
				, p_subjseq:"<%= ss_subjseq %>"
				, p_term:"<%= ss_subjterm %>"
				, p_userid:p_userid
				, p_attendday:p_yyyymmdd
			}
			, function(data) {
				$("#ajaxDiv").html(data);
			});
}
//출석상태저장
function whenSimpleSave() {
	for (p_scoreIndex = 0; p_scoreIndex < document.form1.p_score.length; p_scoreIndex++) {
		if (document.form1.p_score[p_scoreIndex].checked) $('#p_scorevalue').val(document.form1.p_score[p_scoreIndex].value + '');
	}
	$.post("/servlet/controller.common.AjaxServlet"
			, {	  checkSqlNum:"attend.exist", sqlNum:"attend.insert", elseSqlNum:"attend.update", rerurnFunction:"ready"
				, p_subj:"<%= ss_subj %>"
				, p_year:"<%= ss_year %>"
				, p_subjseq:"<%= ss_subjseq %>"
				, p_term:"<%= ss_subjterm %>"
				, p_userid:$("#p_userid").val()
				, p_attendday:$("#p_attendday").val()
				, p_attendtime1:$("#p_attendtime1").val()
				, p_attendtime2:$("#p_attendtime2").val()
				, p_score:$('#p_scorevalue').val()
			}
			, function(data) {
				$("#ajaxDiv").html(data);
			});
}
function readData(data) {
	$("#t_name").html(data.d_name);
	$("#p_attendday").val(data.d_attendday);
	$("#p_yyyymmdd").val(data.d_attendday);
	$("#t_yyyymmdd").html(data.d_attendday.substring(0, 4) + '/' + data.d_attendday.substring(4, 6) + '/' + data.d_attendday.substring(6, 8));
	$("#p_attendtime1").val(data.d_attendtime1);
	$("#p_attendtime2").val(data.d_attendtime2);
	for (p_scoreIndex = 0; p_scoreIndex < document.form1.p_score.length; p_scoreIndex++) {
		if (document.form1.p_score[p_scoreIndex].value == data.d_score) document.form1.p_score[p_scoreIndex].checked = true;
	}
}
function ready() {
	$('#attendCell'+$("#p_userid").val()+$("#p_attendday").val()).html("<img style='width:10px,height:10px' src='/images/admin/portal/stat_" + $('#p_scorevalue').val()+".gif'/>");
	$("#detailView").fadeOut(250);
	return;
}
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" action="/servlet/controller.off.OffAttendAdminServlet">

<div id="ajaxDiv"></div>

  <input type="hidden" name="p_process"  value="">
  <input type="hidden" id="p_scorevalue" name="p_scorevalue" value="">
  <input type="hidden" name="s_action"  value="">
  <input type="hidden" name="p_orderColumn">
  <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
<div id="detailView" style="position: absolute; z-index: 99;display=none">
<input type="hidden" class="input" name="p_subj" id="p_subj" size="10" maxlength="10" value="<%= ss_subj %>">
<input type="hidden" class="input" name="p_year" id="p_year" size="10" maxlength="10" value="<%= ss_year %>">
<input type="hidden" class="input" name="p_subjseq" id="p_subjseq" size="10" maxlength="10" value="<%= ss_subjseq %>">
<input type="hidden" class="input" name="p_userid" id="p_userid" size="10" maxlength="10" value="">
<input type="hidden" class="input" name="p_term" id="p_term" size="10" maxlength="10" value="<%= ss_subjterm %>">
<input type="hidden" class="input" name="p_attendday" id="p_attendday" size="10" maxlength="10" value="">
<table width="300" border="0" cellspacing="0" cellpadding="0" height="300">
<tr>
	<td align="center" valign="top">
		<table cellspacing="1" cellpadding="5" class="table_out" border="0">
		  <tr> 
			<td colspan="5" class="table_top_line"></td>
		  </tr>
		  <tr> 
			<td class="table_01">성명</td>
			<td class="table_02_2" id="t_name"></td>
		  </tr>
		  <tr> 
			<td class="table_01">일자<input type="hidden" name="p_yyyymmdd" id="p_yyyymmdd"></td>
			<td class="table_02_2" id="t_yyyymmdd">YYYY/MM/DD</td>
		  </tr>
		  <tr> 
			<td class="table_01">시간</td>
			<td class="table_02_2" >
				<input type="text" class="input" id="p_attendtime1" name="p_attendtime1" size="10" maxlength="2" value="09">시
				<input type="text" class="input" id="p_attendtime2" name="p_attendtime2"  size="10" maxlength="2" value="00"> 분
			</td>
		  </tr>
		  <tr> 
			<td class="table_01">출석체크</td>
			<td class="table_02_2" >
				 <kocca_select:select name="p_score" sqlNum="off.attendType"  param=" " type="2.2"
					onChange="" attr=" " selectedValue=" " isLoad="true" all="none" />
			</td>
		  </tr>
		  <tr> 
			<td colspan="5" class="table_top_line"></td>
		  </tr>
		  <tr> 
			<td class="table_01" colspan="5"><button onclick="whenSimpleSave();">수정</button></td>
		  </tr>
		 </table>
	</td>
</tr>
</table>
</div>
<table id="listForm" width="900" border="0" cellspacing="0" cellpadding="0">
  <tr>
	<td align="center" valign="top">
	  <!----------------- title 시작 ----------------->
	  <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
		  <td><img src="/images/admin/portal/s.1_off06.gif" ></td>
		  <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	  </table>
	  <!----------------- title 끝 ----------------->
	  <br>
	  <!----------------- form 시작 ----------------->
	  <table cellspacing="0" align="center" cellpadding="1" class="form_table_out">
			  <tr>
				<td align="center" width="97%">
				  <table cellspacing="0" cellpadding="0" class="form_table_bg">
					<tr>
					  <td>
						<!------------------- 조건검색 시작 ------------------------->
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
								<font color="red">★</font>과정명 <kocca_select:select name="s_subjcode" sqlNum="off.subj"
									param="<%= ss_year %>"  param2="<%= ss_upperclass %>"  param3="<%= ss_middleclass %>"  param4="<%= ss_lowerclass %>" param5="<%= ss_year %>"
									onChange="changes_subjseq($('#s_year').val(),this.value);setTimeout('change_subjseq()', 400);" attr=" " selectedValue="<%= ss_subj %>" isLoad="true" all="no" />
								<font color="red">★</font>차수 <kocca_select:select name="s_subjseq" sqlNum="off.subjseq" param="<%= ss_year %>"  param2="<%= ss_subj %>"
									afterScript="fnSearchBoxs_subjterm"
									onChange="changes_subjterm($('#s_subjcode').val(), $('#s_year').val(), this.value)" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="none" />
								<font color="red">★</font>학기 <kocca_select:select name="s_subjterm" sqlNum="off.termlist" param="<%= ss_subj %>"  param2="<%= ss_year %>"  param3="<%= ss_subjseq %>"
									onChange="" attr=" " selectedValue="<%= ss_subjterm %>" isLoad="true" all="none" />
								과정검색 <input type="text" name="s_subjsearchkey" size="17" onkeypress="if(event.keyCode=='13') Main_subjcourse();" value="<%=s_subjsearchkey%>">
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
				<td height="7"></td>
			  </tr>
			</table>
	  <br>
	  <!----------------- 추가 버튼 끝 ----------------->
	  <!----------------- 과정 관리 시작 ----------------->
	  

   <% if(detail != null && detail.size() > 0){ %> 
	
			<!--본문 테이블 -->
			<table width="100%" cellspacing="1" cellpadding="5" class="table_out">
				<tr>
				  <td colspan="11" class="table_top_line"></td>
				</tr>
			  <tr>
			  	<td width="40" height="20" rowspan="2" align="center" class="table_title">No</td>
			   	<td width="70" rowspan="2" align="center" class="table_title">학번</td>
			  	<td width="70" rowspan="2" align="center" class="table_title">이름</td>
				<td width="120" colspan="3"  align="center" class="table_title">출결현황(종합)</td>
			   	<td rowspan="2">
			   	  <div id="headDiv" style="overflow-x:hidden;width:640;">  
			   	  <table width="<%=dateList.size()*50 %>" border=1 cellpadding="0" cellspacing="0" bordercolorlight="#FFFFFF" bordercolordark="white">
			   	   <tr><!-- 월(MM)/일(DD) -->
			   	   		<%= sb1.toString() %>
			   	   </tr>
			   	   <tr><!-- 요일 -->
			   	   		<%= sb2.toString() %>
			   	   </tr>
			   	   </table>
			   	  </div> 
			   	</td>
			  </tr>
			  <tr>
			  	<td width="40" align="center" class="table_title">출석</td>
			  	<td width="40" align="center" class="table_title">결석</td>
			   	<td width="40" align="center" class="table_title">지각</td>
			  </tr>			  
			  <tr>
				<td colspan="6" valign="top">
				<div id="leftDiv" style="overflow-y:hidden;height:300;width:300;">
				<table border=1 cellpadding="0" cellspacing="0" bordercolorlight="#FFFFFF" bordercolordark="white">
<%			  	sb1.delete(0, sb1.length());
					for(int i = 0; i < detail.size();i++) { 
					detailEntity = detail.get(i);
					sb1.append(TR);
					for(DataBox entity : dateList) {
						sb1.append(DETAIL_TD);
						sb1.append(detailEntity.get(D_USERID));
						sb1.append(DIV);
						sb1.append(entity.get(D_YYMMDD));
						sb1.append(DETAIL_LINK_CLOSE);
						sb1.append(detailEntity.get(D_USERID));
						sb1.append(entity.get(D_YYMMDD));
						sb1.append(DETAIL_LINK_CLOSE2);
						sb1.append(resultData.get(detailEntity.get(D_USERID) + entity.get(D_YYMMDD), NONE));
						sb1.append(TD_CLOSE2);
					}
					sb1.append(TR_CLOSE);
					%>
				  <tr>
				  	<td width="40" height="20" align="center" class="table_01"><%=i+1 %></td>
				  	<td width="70" height="20" align="center" class="table_01"><%=detailEntity.get(D_STUDENTNO) %></td>
				  	<td width="70" height="20" align="center" class="table_01"><%=detailEntity.get(D_NAME) %></td>
				  	<td width="40" height="20" align="center" class="table_01" id="attend<%=detailEntity.get(D_USERID)%>"><%=detailEntity.get(D_ATTEND) %></td>
				  	<td width="40" height="20" align="center" class="table_01" id="absent<%=detailEntity.get(D_USERID)%>"><%=detailEntity.get(D_ABSENT) %></td>
				  	<td width="40" height="20" align="center" class="table_01" id="late<%=detailEntity.get(D_USERID)%>"><%=detailEntity.get(D_LATE) %></td>
				  </tr>
			  	<% } %>
			  	 </table>
			  	 </div> 
				</td>
				<td>
				<div id="mainDiv" onscroll="scrolling(this)" style="height:315;width:640;;overflow-x:hidden;overflow-x:scroll;overflow-y:scroll;"> 
				<table width="<%=dateList.size()*50 %>" border=1 cellpadding="0" cellspacing="0" bordercolorlight="#FFFFFF" bordercolordark="white">
					<%= sb1.toString() %>
			  	  </table>
			  	  </div>
				</td>   
			  </tr>
		  </table>
			<!--본문 테이블 끝 -->
	 
	<% }else{ %>
	 	<!--본문 테이블 -->
			<table width="100%" cellspacing="1" cellpadding="5" class="table_out">
				<tr>
				  <td colspan="11" class="table_top_line"></td>
				</tr>
			  <tr>
			  	<td width="40" height="20" rowspan="2" align="center" class="table_title">No</td>
			   	<td width="70" rowspan="2" align="center" class="table_title">학번</td>
			  	<td width="70" rowspan="2" align="center" class="table_title">이름</td>
				<td width="120" colspan="3"  align="center" class="table_title">출결현황(종합)</td>
			  </tr>
			  <tr height="20">
			  	<td width="40" align="center" class="table_title">출석</td>
			  	<td width="40" align="center" class="table_title">결석</td>
			   	<td width="40" align="center" class="table_title">지각</td>
			  </tr>   
			  <tr height="20">
			  <td colspan="10" align="center" class="table_01">출석 정보가 없습니다.</td>
			  </tr>  
		  </table>
			<!--본문 테이블 끝 -->
		 
	<% } %>
	  
	  <!----------------- 과정 관리 끝 ----------------->
</form>
</body>
</html>

