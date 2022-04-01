<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: 수강신청 취소 신청
//  2. 프로그램명: gu_ProposeCancelApply_I.jsp
//  3. 개      요: 수강신청 취소 신청
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.15
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.research.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_tid		= box.getString("p_tid");
	String v_paymethod	= box.getString("p_paymethod");

    String	v_subj		= "";
    String	v_subjnm	= "";
    String	v_subjnms	= "";

    String v_userid = box.getSession("userid");
    
    ArrayList list1     = null;
    list1 = (ArrayList)request.getAttribute("SubjnmList");
    
	for(int i = 0; i < list1.size(); i++)
	{
		DataBox dbox = (DataBox)list1.get(i);
	   
		v_subjnm	= dbox.getString("d_subjnm");

		v_subjnms	+= v_subjnm;
		
		if (i != list1.size() - 1) {
			v_subjnms += " ,";
		}

	}
	
%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>수강신청 취소요청</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script type="text/javascript" src="/script/cresys_lib.js"></script>
</head>
<SCRIPT LANGUAGE="JavaScript">
<!--
function cancelApply() {

	<% if ( "FreePay|BankBook|VCard|Card".indexOf(v_paymethod) < 0 ) { %>
    if (blankCheck(document.all.p_refundbank.value)) {
        alert("은행명을 입력하세요!");
        document.all.p_refundbank.focus();
        return;
    }                
    if (blankCheck(document.all.p_refundaccount.value)) {
        alert("계좌번호를 입력하세요!");
        document.all.p_refundaccount.focus();
        return;
    }                
    if (blankCheck(document.all.p_refundname.value)) {
        alert("예금주를 입력하세요!");
        document.all.p_refundname.focus();
        return;
    }
    <% } %>
    if (blankCheck(document.all.p_cancelreason.value)) {
        alert("취소사유를 입력하세요!");
        document.all.p_cancelreason.focus();
        return;
    }                
	
	if(confirm('수강신청을 취소 하시겠습니까?')){
	    document.form2.action = "/servlet/controller.study.MyClassServlet";
		document.form2.p_process.value = "ProposeCancelApply";
		document.form2.submit();
	}
}
-->
</SCRIPT>
<body id="popup"><!-- popup size : 550* -->
<form name="form2" method="post" action="/servlet/controller.research.SulmunCommonUserServlet">
    <input type="hidden" name="p_tid"    	value="<%= v_tid %>">
    <input type="hidden" name="p_process"  	value="ProposeCancelApply">
	<div id="pop_header">
		<h1><img src="/images/portal/myclass/pop_h1_tit3.gif" alt="수강신청 취소요청" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap" class="message_top">
			<p class="pop_txt_q"><span class="ac"><%= v_subjnms %> </span> 과정을 <strong>수강신청 취소요청</strong> 하시겠습니까? <br />환불계좌 및 취소사유를 입력해주세요</p>
			<table class="view">
			<colgroup><col width="25%" /><col width="75%" /></colgroup>
			<% if ( "FreePay|BankBook|VCard|Card".indexOf(v_paymethod) < 0 )  { %>
			<tr>
				<th>은행명</th>
				<td class="end"><input type="text" name="p_refundbank" class="inbox" style="width:99%;" /></td>
			</tr>
			<tr>
				<th>계좌번호</th>
				<td class="end"><input type="text" name="p_refundaccount" class="inbox" style="width:99%;" /></td>
			</tr>
			<tr>
				<th>예금주</th>
				<td class="end"><input type="text" name="p_refundname" class="inbox" style="width:99%;" /></td>
			</tr>
			<% } else { %>
				<input type="hidden" name="p_refundbank" value="" />
				<input type="hidden" name="p_refundaccount" value="" />
				<input type="hidden" name="p_refundname" value="" />
			<% } %>
			<tr>
				<th class="va_t">취소사유</th>
				<td class="in end"><textarea name="p_cancelreason" style="width:99%; height:60px;"></textarea></td>
			</tr>
			</table>
		</div>
	</div>
	<div id="pop_footer">
		<p class="f_btn"><a href="javascript:cancelApply()" class="btn_gr"><span>확인</span></a><a href="javascript:window.close()" class="btn_gr"><span>취소</span></a></p>
	</div>
</form>
</body>
</html>

