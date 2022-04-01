<%
//**********************************************************
//  1. 제      목: 열린강좌 일괄관리
//  2. 프로그램명 : za_GoldClassGroup_L.jsp
//  3. 개      요: 열린강좌 일괄관리
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 김기종 2016.12.07
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>

<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.FormatDate" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.PageUtil" %>
<%@ page import="com.credu.library.StringManager" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet"  scope="page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");

%>
<html>
<head>
<title>열린강좌 운영관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/CSS" href="/css/jquery-ui-1.10.4.css">
<script type="text/javascript" src = "/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/script/jquery-ui-1.10.4.js"></script>
<script type="text/javascript">

	var action = false;
    $(document).ready( function() {

    });
    
    
    /**
    * 강좌관리 페이지로 이동
    */
    function fnList(){
    	$('#p_process').val('list');
    	$("#form1").attr({
    		action : '/servlet/controller.infomation.GoldClassGroupServlet',
    		target : '_self'
    	}).submit();
    }
    
    /**
    * 엑셀 업로드 페이지 이동
    */
    function excelUpload(){
    	$('#p_process').val('uploadPage');
    	$("#form1").attr({
    		action : '/servlet/controller.infomation.GoldClassGroupServlet',
    		target : '_self'
    	}).submit();
    }
    
 	// 등록 & 미리보기
	function fnInsert(type){
		
		if(action){
			alert("처리중입니다. 잠시만 기다려주세요.");
			return;
		}
		
		var data = $('#p_file').val().toUpperCase();
		if(data == ""){
			alert("엑셀파일을 등록하세요.");
			return;
		}
		
		if(data.indexOf(".XLS") < 0 || data.indexOf(".XLSX") > 0){
			alert("DB로 입력되는 파일종류는 xls 파일만 가능합니다.");
            return;
		}
		
		$('#p_type').val(type);
		$('#p_process').val('upload');
		action = true;
		$('#form1').attr({
			action : '/servlet/controller.infomation.GoldClassGroupServlet',
			target : '_self'
		}).submit();
	}

</script>
</head>

<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">
<form id="form1" name="form1" method="post" enctype="multipart/form-data">
    <input type="hidden" id="p_process" name="p_process" 	value="<%= v_process %>" />
    <input type="hidden" id="p_type" 	name="p_type" 		value="" />


	<table width="1000" border="0" cellspacing="0" cellpadding="0">
	    <tr>
	        <td align="center" valign="top">
	            <!-- title 시작 //-->
	            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
	                <tr>
	                    <td><img src="/images/admin/portal/s.1_19.gif" /></td>
	                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" /></td>
	                </tr>
	            </table>
	            <!-- title 끝 //-->
	
	            <br />
	            
	            <table width="97%" class="table_out" cellspacing="1" cellpadding="0">
					<colgroup>
						<col width="20%" height="25">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<td colspan="14" class="table_top_line"></td>
						</tr>
						<tr>
							<td class="table_title"><b>엑셀파일등록</b></td>
							<td class="table_02_2">
								<input type="file" id="p_file" name="p_file"/>
							</td>
						</tr>
					</tbody>
				</table>
	            <br />
				
				<br/>
	            <table width="97%"  class="table_out" cellspacing="0" cellpadding="0">
					<tr>
						<td class="table_02_2" style="padding-left:0px;">
							<a href="/upload/goldclass/goldclassSample.xls"><b>샘플양식 다운로드</b></a>
						</td>
						<td class="table_02_2" style="padding-left:0px; text-align: right">
							<a href="#none" onclick="fnInsert('Ins');"><img src="/images/admin/button/btn_register.gif" border="0"></a>
				        	<a href="#none" onclick="fnInsert('Pre');"><img src="/images/admin/button/btn_preview.gif" border="0"></a>
				        	<a href="#none" onclick="fnList();"><img src="/images/admin/button/btn_list.gif" border="0"></a>
						</td>
					</tr>
					<tr>
						<td class="table_02_2" style="padding-left:0px;" colspan="2">
							<img alt="샘플파일" src="/images/open_class/goldclassSample.jpg" style="margin: 0; width: 100%;"/>
						</td>
					</tr>
				</table>
	            
	        </td>
	    </tr>
	</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
