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

	String  v_selBillStatus = box.getString("p_selBillStatus");
	String  v_selBillMethod = box.getString("p_selBillMethod");
	
	String  v_billstart = box.getString("p_billstart");
    String  v_billend   = box.getString("p_billend");

    String  v_selMemberType = box.getString("p_selMemberType");
    
    String  v_searchtext    = box.getString("p_searchtext");
    
    String  v_process   = box.getString("p_process");
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_hsubjnm   = box.getString("p_hsubjnm");


  //DEFINED in relation to select START
    String  ss_year         = box.getString("s_year");                      //연도
    String  ss_subj         = box.getString("s_subjcode");                  //과정
    String ss_subjseq = box.getString("s_subjseq");
    String  ss_grseq        = box.getString("s_grseq");                     //차수
	String  ss_subjyear = box.getString("s_subjyear");	  //과정년도
    String  ss_upperclass   = box.getStringDefault("s_upperclass", "ALL");  //과정대분류
    String  ss_middleclass  = box.getStringDefault("s_middleclass", "ALL"); //과정중분류
    String  ss_lowerclass   = box.getStringDefault("s_lowerclass", "ALL");  //과정소분류
    String  ss_action       = box.getString("s_action");
    String  s_subjsearchkey = box.getString("s_subjsearchkey");
    String  v_gyear         = box.getStringDefault("p_gyear", FormatDate.getDate("yyyy"));
    //DEFINED in relation to select END

    String  v_orderType    = box.getStringDefault("p_orderType"," asc"); //정렬순서

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
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#p_sdate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_edate").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
    function whenSelection(p_action) {

		var st_date = make_date(document.form1.p_sdate.value);
	    var ed_date = make_date(document.form1.p_edate.value);
	
	    document.form1.p_billstart.value  = st_date;
	    document.form1.p_billend.value    = ed_date;
		if(checkNotNull($('#s_subjcode').val(), '과정') || checkNotNull($('#s_subjseq').val(), '차수')) {//<font color="red">★</font>
			return;
		}
		document.form1.p_action.value = "go";
		document.form1.p_process.value = "selectList";
		document.form1.action = "/servlet/controller.off.OffBillAdminServlet";
    	document.form1.submit();
    }

    // 선택공지등록
    function insert() {
        document.form1.action = "/servlet/controller.off.OffBillAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }

    // 상세보기
    function view(seq) {
        document.form1.action = "/servlet/controller.off.OffBillAdminServlet";
        document.form1.p_seq.value      = seq;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

function whenOrder(column) {
	if (document.form1.p_orderType.value == " asc") {
		document.form1.p_orderType.value = " desc";
	} else {
		document.form1.p_orderType.value = " asc";
	}

    document.form1.p_orderColumn.value = column;
    whenSelection("go");
}

function goSubjectListPage() {
  
  document.form1.action = "/servlet/controller.off.OffBillAdminServlet";
  document.form1.p_process.value = "selectAll";
  document.form1.p_action.value = "go";
  document.form1.submit();
}

function excelDown() {
    document.form1.target = "_self";
    document.form1.action='/servlet/controller.off.OffBillAdminServlet';
    document.form1.p_process.value = 'excelDown';
    document.form1.submit();
}

function selectBillStatus(p) {
    document.form1.p_selBillStatus.value = p;
    whenSelection(p);
}

function selectBillMethod(p) {
    document.form1.p_selBillMethod.value = p;
    whenSelection(p);
}

-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

    <form name="form1" method="post" action="">
    <input type="hidden" name="p_process"   value="">
    <input type="hidden" name="p_action"    value="">
    <input type="hidden" name="isTerm"      value="true">
    <input type="hidden" name="p_addSubjSeq"id="p_addSubjSeq"  value="0">
    <input type="hidden" name="p_addSeq"    id="p_addSeq"       value="0">
    <input type="hidden" name="lastSubjSeq" id="lastSubjSeq"    value="0">
    <input type="hidden" name="p_subj"      id="p_subj"         value="">
    <input type="hidden" name="p_subjnm"    value="">
    <input type="hidden" name="s_action"    value="">
    <input type="hidden" name="p_orderColumn">
    <input type="hidden" name="p_orderType" value="<%=v_orderType%>">
    <input type="hidden" name="u_year"      id="u_year"         value="">
    <input type="hidden" name="u_totalterm" id="u_totalterm"    value="">
    <input type="hidden" name="u_subjseq"   id="u_subjseq"      value="">
	
	<input type="hidden" name="p_selBillStatus" value="<%=v_selBillStatus%>">
	<input type="hidden" name="p_selBillMethod" value="<%=v_selBillMethod%>">
	
	<input type="hidden" name="p_billstart" value="<%=v_billstart%>">
    <input type="hidden" name="p_billend"   value="<%=v_billend%>">

<table id="listForm" width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/portal/s.1_31.gif" ></td>
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
												과정년도 <kocca_select:select name="s_subjyear" sqlNum="off.year.subj"  param=" "
													onChange="fnSearchBox()" attr=" " selectedValue="<%= ss_subjyear %>" isLoad="true" all="true" />
												<font color="red">★</font>과정명 <kocca_select:select name="s_subjcode" sqlNum="off.subj"
													param="<%= ss_year %>"  param2="<%= ss_upperclass %>"  param3="<%= ss_middleclass %>"  param4="<%= ss_lowerclass %>" param5="<%= ss_subjyear %>"
													onChange="changes_subjseq($('#s_year').val(),this.value);" attr=" " selectedValue="<%= ss_subj %>" isLoad="true" all="no" />
												차수 <kocca_select:select name="s_subjseq" sqlNum="off.subjseq" param="<%= ss_year %>"  param2="<%= ss_subj %>"
													onChange="" attr=" " selectedValue="<%= ss_subjseq %>" isLoad="true" all="true" />
												과정검색 <input type="text" name="s_subjsearchkey" size="17" onkeypress="if(event.keyCode=='13') Main_subjcourse();" value="<%=s_subjsearchkey%>">
											</td>
										</tr>
                                        <tr>
                                            <td align="left">결제상태&nbsp;
              <kocca_select:select                                       
                    name="p_selBillStatus1"                                         
                    sqlNum="0001"                                    
                    param="0096"             
                    onChange="selectBillStatus(this.value);"                         
                    attr= "결제상태"                                        
                    selectedValue="<%=v_selBillStatus%>"                         
                    isLoad="true"                                           
                    type="1"                                                
                    styleClass=""                                
                    all="true" />     
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                                      결제방법&nbsp;
                    <kocca_select:select                                       
                    name="p_selBillmethod1"                                         
                    sqlNum="0001"                                    
                    param="0097"             
                    onChange="selectBillMethod(this.value);"                         
                    attr= "결제방법"                                        
                    selectedValue="<%=v_selBillMethod%>"                         
                    isLoad="true"                                           
                    type="1"                                                
                    styleClass=""                                
                    all="true" />
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  
                결제일&nbsp;
                   <input name="p_sdate" id="p_sdate" class="datepicker_input1" type="text" size="10" value='<%=FormatDate.getFormatDate(v_billstart, "yyyy/MM/dd") %>'>
                   ~
                   <input name="p_edate" id="p_edate" class="datepicker_input1" type="text" size="10" value='<%=FormatDate.getFormatDate(v_billend, "yyyy/MM/dd") %>'>
                   ID 또는 이름&nbsp;<input name="p_searchtext" type="text" value="<%=v_searchtext %>">
                </td>
                                           </td>
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
            <a href="javascript:excelDown()"><img src="/images/admin/button/btn_excelprint.gif" border="0"></a>
          </td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 추가 버튼 끝 ----------------->
      <!----------------- 과정 관리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr>
          <td colspan="11" class="table_top_line"></td>
        </tr>
        <tr>
          <td class="table_title" width="5%" height="25"><b>NO</b></td>
            <td class="table_title" width="25%"><b>과정명</b></td>
            <td class="table_title" width="7%"><b>ID</b></td>
            <td class="table_title" width="8%"><b>성명</b></td>
            <td class="table_title" width="10%"><b>
                <kocca_select:select                                       
                    name="p_selMemberType"                                         
                    sqlNum="0001"                                    
                    param="0029"             
                    onChange="whenSelection('go');"                         
                    attr= "회원구분"                                        
                    selectedValue="<%=v_selMemberType%>"                         
                    isLoad="true"                                           
                    type="3"                                                
                    styleClass="table_title"                                
                    all="true" />  </b>
            </td>
            <td class="table_title" width="7%"><b>
                <kocca_select:select                                       
                    name="p_selBillStatus2"                                         
                    sqlNum="0001"                                    
                    param="0096"             
                    onChange="selectBillStatus(this.value);"                         
                    attr= "결제상태"                                        
                    selectedValue="<%=v_selBillStatus%>"                         
                    isLoad="true"                                           
                    type="3"                                                
                    styleClass="table_title"                                
                    all="true" /></b>      
            </td>
            <td class="table_title" width="7%"><b>결재금액</b></td>
            <td class="table_title" width="10%"><b>
                <kocca_select:select                                       
                    name="p_selBillmethod2"                                         
                    sqlNum="0001"                                    
                    param="0097"             
                    onChange="selectBillMethod(this.value);"                         
                    attr= "결제방법"                                        
                    selectedValue="<%=v_selBillMethod%>"                         
                    isLoad="true"                                           
                    type="3"                                                
                    styleClass="table_title"                                
                    all="true" />
            </b></td>
            <td class="table_title" width="8%"><b>결재자</b></td>
            <td class="table_title" width="10%"><b>결재일</b></td>
            <td class="table_title" width="8%"><b>결재취소</b></td>
        </tr>
        <tr><td colspan=11 class="table_02_1">등록된 과정이 없습니다.</td></tr>  
      </table>
      <!----------------- 과정 관리 끝 ----------------->
      <br>
      <!----------------- 저장, 삭제, 리스트 버튼 시작 ----------------->
        <table class="table1" cellspacing="0" cellpadding="0">
          <tr>
            <td align="center"><%= PageUtil.printPageSizeList(1, 1, 0, 10, 1) %></td>
          </tr>
        </table>
      <!----------------- 저장, 삭제, 리스트 버튼 끝 ----------------->
    </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</form>
</body>
</html>
