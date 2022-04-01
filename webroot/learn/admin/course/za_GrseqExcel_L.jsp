
<%
//**********************************************************
//  1. 제      목: 교육차수 리스트 조회화면
//  2. 프로그램명: za_Grseq_L.jsp
//  3. 개      요: 교육차수 리스트 조회화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 11. 11
//  7. 수      정:
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

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca"%>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select"%>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String v_grcode = box.getString("p_grcode");               //교육그룹
    String v_gyear = box.getString("p_gyear");                //년도

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
    Date dt = new Date();
    int currYear = Integer.parseInt( sdf.format(dt) );

%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<style>
th {
	font-size: 12px;
}
</style>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript">

	var action = false;
	$(document).ready(function(){
	});
	
	// 목록으로
	function fnList(){
		$('#p_process').val('listPage');
		$('#form1').attr({
			action : '/servlet/controller.course.GrseqServlet',
			target : '_self'
		}).submit();
	}
	
	// 등록 & 미리보기
	function fnInsert(type){
		
		if(action){
			alert("처리중입니다. 잠시만 기다려주세요.");
			return;
		}
		
		if($('#p_grcode').val() == ""){
			alert("교육그룹을 선택하세요.");
			$('#p_grcode').focus();
			return;
		}
		if($('#p_gyear').val() == ""){
			alert("연도를 선택하세요.");
			$('#p_gyear').focus();
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
		$('#p_process').val('excelInsert');
		action = true;
		$('#form1').attr({
			action : '/servlet/controller.course.GrseqServlet',
			target : '_self'
		}).submit();
	}

</script>
</head>
<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">
	<form id="form1" name="form1" method="post" enctype="multipart/form-data">
		<input type="hidden" id="p_process" name="p_process" /> 
		<input type="hidden" id="p_type" name="p_type" /> 

		<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
			<tr>
				<td align="center" valign="top">
					<!-- title 시작 //-->
					<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
						<tr>
							<td><img src="/images/admin/course/c_title02.gif" alt="asdf"></td>
							<td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
						</tr>
					</table> 
					<!-- title 끝 //-->
					<br /> 
					
					
					<table width="970" class="table_out" cellspacing="1" cellpadding="0">
						<colgroup>
							<col width="20%" height="25">
							<col width="*">
						</colgroup>
						<tbody>
							<tr>
								<td colspan="14" class="table_top_line"></td>
							</tr>
							<tr>
								<td class="table_title"><font color="red">★</font><b>교육그룹</b></td>
								<td class="table_02_2">
									<kocca:selectBox name="p_grcode" id="p_grcode" optionTitle="-- 선택 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= v_grcode %>" isLoad="true" />
								</td>
							</tr>
							<tr>
								<td class="table_title"><font color="red">★</font><b>연도</b></td>
								<td class="table_02_2">
									<select name="p_gyear" id="p_gyear">
										<option value="">-- 선택 --</option>
										<%for(int i=currYear+1; i>=currYear; i--){%>
											<option value="<%=i%>"><%=i%></option>
										<%}%>
									</select>
								</td>
							</tr>
							<tr>
								<td class="table_title"><b>교육차수명</b></td>
								<td class="table_02_2">
									<input type="text" id="p_grseqnm" name="p_grseqnm" maxlength="50" class="input" style="width:400px;"/>
								</td>
							</tr>
							<tr>
								<td class="table_title"><b>상시/정규</b></td>
								<td class="table_02_2">
									<select name="p_isalways">
										<option value="Y"> 상시</option>
									 	<option value="N"> 정규</option>
									</select>
								</td>
							</tr>
							<tr>
								<td class="table_title"><b>엑셀파일등록</b></td>
								<td class="table_02_2">
									<input type="file" id="p_file" name="p_file"/>
								</td>
							</tr>
						</tbody>
					</table>
					<table width="970" cellspacing="1" cellpadding="0">
						<tr>
							<td height="5"></td>
						</tr>
						<tr>
							<td>
								<a href="/upload/grseq/grseqSample.xls"><b>샘플양식 다운로드</b></a>
							</td>
					        <td align="right" colspan="2">
					        	<a href="#none" onclick="fnInsert('Ins');"><img src="/images/admin/button/btn_register.gif" border="0"></a>
					        	<a href="#none" onclick="fnInsert('Pre');"><img src="/images/admin/button/btn_preview.gif" border="0"></a>
					        	<a href="#none" onclick="fnList();"><img src="/images/admin/button/btn_list.gif" border="0"></a>
					        </td>
					    </tr>
					</table>
					<br/>
					
				</td>
			</tr>
		</table>
	</form>
	<%@ include file="/learn/library/getJspName.jsp"%>
</body>
</html>
