<%
//**********************************************************
//  1. 제		목: 차수 조회화면
//  2. 프로그램명: za_off_grseq_L.jsp
//  3. 개		요: 차수 조회화면
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
	
    GregorianCalendar   calendar    = new GregorianCalendar();
    String currentYear = Integer.toString(calendar.get(Calendar.YEAR));

	//DEFINED in relation to select START
	String  ss_year = box.getStringDefault("s_year", currentYear);		//연도
	String  ss_subj = box.getString("s_subjcode");				//과정
	String  ss_grseq = box.getString("s_grseq");		//차수
	String  ss_subjyear = box.getString("s_subjyear");	  //과정년도
	if( ss_year.equals("") ) {
		ss_year = Integer.toString((new java.util.Date().getYear()) + 1900);
	}
	String  ss_upperclass = box.getStringDefault("s_upperclass", "S01");		//과정대분류
	String  ss_middleclass = box.getStringDefault("s_middleclass", "ALL");	//과정중분류
	String  ss_lowerclass = box.getStringDefault("s_lowerclass", "ALL");		//과정소분류
	String  ss_subjseq  = box.getStringDefault("s_subjseq", "ALL");     // 과정 차수   
	
	
	String  ss_action	= box.getString("s_action");
	String s_subjsearchkey = box.getString("s_subjsearchkey");
	String v_gyear  = box.getStringDefault("p_gyear", FormatDate.getDate("yyyy"));
	//DEFINED in relation to select END
	ArrayList list		= null;
	ArrayList<DataBox> acceptResultList = null;
	if(ss_action.equals("go")){	//go button 선택시만 list 출력
		list = (ArrayList)request.getAttribute("resultList");
		acceptResultList = (ArrayList<DataBox>) request.getAttribute("acceptResultList");
	}
	String s_gadmin = box.getSession("gadmin");
	String v_gadmin = StringManager.substring(s_gadmin, 0, 1);
	String isAll = !v_gadmin.equals("A") ? "false" : "true";

	String  v_orderType	= box.getStringDefault("p_orderType"," asc"); //정렬순서
%>
<html>
<head>
<title> 차수관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script> 
<SCRIPT LANGUAGE="JavaScript">
<!--

// 검색
function whenSelection(ss_action) {
	if (ss_action=="go")	{
		top.ftop.setPam();
	}
	if(checkNotNull($('#s_subjcode').val(), '과정') || checkNotNull($('#s_subjseq').val(), '차수')) {//<font color="red">★</font>
		return;
	}		document.form1.s_action.value = ss_action;
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

function whenExcel() {
	document.form1.target = 'mainFrame';
	$("#isExcel").val("true");
	document.form1.s_action.value = "go";
	document.form1.p_process.value = 'listPage';
	document.form1.submit();
	$("#isExcel").val("false");
	document.form1.target = '_self';
}

-->
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
<table id="listForm" width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
	<td align="center" valign="top">
	<form name="form1" method="post" action="/servlet/controller.off.OffCancelProposeAdminServlet">
	<input type="hidden" name="p_process"  value="">
	<input type="hidden" name="isSeq"	 value="true">
	<input type="hidden" name="p_addSubjSeq" id="p_addSubjSeq" value="0">
	<input type="hidden" name="p_addSeq" id="p_addSeq" value="0">
	<input type="hidden" id="lastSubjSeq" name="lastSubjSeq"	 value="0">
	<input type="hidden" name="p_subj"	 value="">
	<input type="hidden" name="p_subjnm"	value="">
	<input type="hidden" name="s_action"  value="">
<script type="text/javascript">
<!--
function whenExcel() {
	document.form1.target = 'mainFrame';
	$("#isExcel").val("true");
	document.form1.s_action.value = "go";
	document.form1.p_process.value = 'listPage';
	document.form1.submit();
	$("#isExcel").val("false");
	document.form1.target = '_self';
}
-->
</script>
<input type="hidden" name="isExcel" id="isExcel" value="false">
	<input type="hidden" name="p_orderColumn">
	<input type="hidden" name="p_orderType" value="<%=v_orderType%>">
	<input type="hidden" id="u_year" name="u_year"  value="">
	<input type="hidden" id="u_seq" name="u_seq"  value="">
	<input type="hidden" id="u_subjseq" name="u_subjseq"  value="">
	
		<!----------------- title 시작 ----------------->
		<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr> 
			<td><img src="/images/admin/portal/s.1_001.gif" ></td>
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
							function fnSearchBoxs_subjterm() {
								if(isFirst>2)
									changes_subjterm($('#s_subjcode').val(), $('#s_year').val(), $('#s_subjseq').val());
									isFirst++;
							}
							</script>
												<font color="red">★</font>연도 <kocca_select:select name="s_year" sqlNum="off.year"  param=" "
													onChange="changes_middleclass(this.value);" attr=" " selectedValue="<%= ss_year %>" isLoad="true" all="no" />
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
		<!----------------- 모두승인, 모두승인취소, 확인 버튼 시작 ----------------->
		<table width="97%" border="0" cellspacing="0" cellpadding="0">
		  <tr>
			<td align="right" height="20">&nbsp;</td>
			<td width="8"></td>
			<td align="right" height="20" width="70"><a href="javascript:whenExcel()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a></td>
		  </tr>
		  <tr>
			<td height="3" colspan=5></td>
		  </tr>
		</table>
		<!----------------- 모두승인, 모두승인취소, 확인 버튼 끝 ----------------->
</form>
<form name="ff" method="post" action="/servlet/controller.off.OffCancelProposeAdminServlet">

		<!----------------- 과정 관리 시작 ----------------->
		<table cellspacing="1" cellpadding="5" class="table_out">
		<tr>
			<td colspan="13" class="table_top_line"></td>
		</tr>
		<tr>
			<td class="table_title" width="34"><b>NO</b></td>
			<td class="table_title"><a href="javascript:whenOrder('subjnm')" class="e">과정명</a></td>
			<td class="table_title" width="34"><a href="javascript:whenOrder('subjnseq')" class="e">차수</a></td>
			<td class="table_title"><a href="javascript:whenOrder('userid')" class="e">ID</a></td>
			<td class="table_title"><a href="javascript:whenOrder('name')" class="e">성명</a></td>
			<td class="table_title">회원구분</td>
			<td class="table_title">결제상태</td>
			<td class="table_title"><a href="javascript:whenOrder('price')" class="e">결제액</a></td>
			<td class="table_title">결제방법</td>
			<td class="table_title"><a href="javascript:whenOrder('refunddate')" class="e">취소일</a></td>
			<td class="table_title"><a href="javascript:whenOrder('a.canceldate')" class="e">취소요청일</a></td>
		</tr>
<%  if(ss_action.equals("go") && list!= null){	//go button 선택시만 list 출력
	DataBox data = null;
	String acceptResult = null;
	for (int i=0; i<list.size(); i++) {
		data  = (DataBox)list.get(i);
%>
		<tr id="<%=data.get("d_subjnm")%>">
			<td class="table_01">
				<input type="hidden" name="p_userid" value="<%=data.get("d_userid") %>"/>
				<input type="hidden" name="p_subj" value="<%=data.get("d_subj") %>"/>
				<input type="hidden" name="p_subjseq" value="<%=data.get("d_subjseq") %>"/>
				<input type="hidden" name="p_year" value="<%=data.get("d_year") %>"/>
				<input type="hidden" name="p_seq" value="<%=data.get("d_seq") %>"/>
				<input type="hidden" name="p_process" value=""/>
				<%=String.valueOf(i+1)%>
			</td>
			<td class="table_01"><%=data.get("d_subjnm") %></td>
			<td class="table_01"><%=data.get("d_subjseq") %></td>
			<td class="table_01"><%=data.get("d_userid") %></td>
			<td class="table_01"><%=data.get("d_name") %></td>
			<td class="table_01"><%=data.get("d_membergubunnm") %></td>
			<td class="table_01"><%=data.get("d_resultcode") %></td>
			<td class="table_01"><%=data.getCommaInt("d_price") %></td>
			<td class="table_01"><%=data.get("d_paymethod") %></td>
			<td class="table_01"><%=data.get("d_refunddate") %></td>
			<td class="table_01"><%=data.getDate("d_canceldate") %></td>
		</tr>
<%  } %>

		<% if (list.size()==0) {%>
			<tr><td colspan=13 class="table_02_1">검색된 결과가 없습니다.</td></tr>  
		<% } %>
<%} %>
		</table>
</form>
		<!----------------- 과정 관리 끝 ----------------->
		<br>
	</td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</body>
</html>
