<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.*" %>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if(box == null) { box = RequestManager.getBox(request); }
	
	//교육체계분류코드 정보
	DataBox data = (DataBox)request.getAttribute("data");
	if(data == null) { data = new DataBox("data"); }
%>
<!doctype html>
<html>
<head>
<title>교육체계도 관리 - 과정 - 관리자 - 한국콘텐츠아카데미</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<script type="text/javascript">
	$(document).ready(function(){
		
		//숫자 체크
		isNumber("isNumber");
		
	});
	
	//분류 목록 화면
	function fnCmdListPage(){
		
		$("#p_process").val("listPage");
		$("#form1").attr({
					action:"/servlet/controller.course.EduSystemChartServlet",
					method:"post",
					target:"_self"
					});
		$("#form1").submit();
	}
	
	//분류 저장
	function fnCmdUpdate(){
	
		if($.trim($("#p_chartname").val()) == ""){
			alert("분류코드명을 입력해주세요!");
			$("#p_chartname").focus();
			return;
		}
		if($.trim($("#p_chartorder").val()) == ""){
			alert("분류코드 순서를 입력해주세요!");
			$("#p_chartorder").focus();
			return;
		}
		
		//숫자 체크
		isNumber("isNumber");
		
		if(confirm("저장하시겠습니까?")){
			$("#p_process").val("update");
			$("#form1").attr({
						action:"/servlet/controller.course.EduSystemChartServlet",
						method:"post",
						target:"_self"
						});
			$("#form1").submit();
		}
	}
	
	
	//분류 삭제
	function fnCmdDelete(){
		
		if(confirm("삭제하시겠습니까?")){
			$("#p_process").val("delete");
			$("#form1").attr({
						action:"/servlet/controller.course.EduSystemChartServlet",
						method:"post",
						target:"_self"
						});
			$("#form1").submit();
		}
	}
	
	
	//숫자체크
	function isNumber(textId){
		var text = "";
		$("." + textId).bind("keydown keyup focusout ",function(e){
			if ((e.which && e.keyCode) &&
				(e.which != 8) && (e.keyCode != 8) &&
				(e.which != 37) && (e.keyCode != 37) &&
				(e.which != 39) && (e.keyCode != 39) &&
				(e.which != 46) && (e.keyCode != 46)) {
  			 	$(this).val( $(this).val().replace(/[^0-9]/gi,"") * 1 );
			}else{
				if($.trim($(this).val()) == ""){
					$(this).val("0");
				}
			}
  		});
	}
	

</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
<tr>
<td align="center" valign="top">


	<!-- content 영역 s -->
	<!-- content 영역 s -->
	<!-- content 영역 s -->
	<!-- content 영역 s -->
	<!-- content 영역 s -->



	<!-- title 영역 s -->
	<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr>
			<td style="text-align:left;"><img src="/images/admin/course/c_title01.gif" ></td> <!-- 상단 메뉴명 -->
			<td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	</table>
	<!-- title 영역 e -->
	<br />
	
	<!-- 버튼 영역 s -->
	<table cellpadding="0" cellspacing="0" class="table1">
		<tr>
			<td align="right" height="20">
				<button id="oRegBtn" name="btn" class="btn_reg" onclick="fnCmdListPage(); return false;">목록</button>
				<button id="oRegBtn" name="btn" class="btn_reg" onclick="fnCmdUpdate(); return false;">저장</button>
				<button id="oRegBtn" name="btn" class="btn_reg" onclick="fnCmdDelete(); return false;">삭제</button>
			</td>
		</tr>
		<tr><td height="3"></td></tr>
	</table>
	<!-- 버튼 영역 e -->
	
	
	<form name="form1" id="form1" method="post" action="/servlet/controller.course.EduSystemChartServlet">
		<input type="hidden" name="p_process" id="p_process" value="" />
		<input type="hidden" name="p_chartcode" id="p_chartcode" value="<%=box.getString("p_chartcode") %>" />
		<input type="hidden" name="p_codegubun" id="p_codegubun" value="<%=box.getString("p_codegubun") %>" />
		
		
		<!-- 수정 영역 s -->
		<table cellspacing="1" cellpadding="5" class="table_out" summary="교육체계도 분류 수정">
			<caption style="display: none;">교육체계도 분류 수정</caption>
			<colgroup>
					<col width="20%" />
					<col width="*" />
				</colgroup>
			<tbody>
				<tr>
					<th scope="row" class="table_title">상위 분류구분</th>
					<td class="table_02_2">[<%=data.getString("d_upperchartcode") %>] <%=data.getString("d_upperchartname") %></td>
				</tr>
				<tr>
					<th scope="row" class="table_title">&#42;분류코드</th>
					<td class="table_02_2"><%=data.getString("d_chartcode") %></td>
				</tr>
				<tr>
					<th scope="row" class="table_title">&#42;분류코드명</th>
					<td class="table_02_2"><input type="text" name="p_chartname" id="p_chartname" value="<%=data.getString("d_chartname") %>" style="width: 80%;" /></td>
				</tr>
				<tr>
					<th scope="row" class="table_title">&#42;분류코드순서</th>
					<td class="table_02_2"><input type="text" name="p_chartorder" id="p_chartorder" value="<%=data.getString("d_chartorder") %>" style="width: 10%;" class="isNumber" /></td>
				</tr>
				<tr>
					<th scope="row" class="table_title">&#42;사용여부</th>
					<td class="table_02_2">
						<input type="radio" name="p_useyn" id="p_useyn_y" value="Y" <%="Y".equals(data.getString("d_useyn"))?"checked='checked'":"" %> /><label for="p_useyn_y">Y</label>
						<input type="radio" name="p_useyn" id="p_useyn_n" value="N" <%="N".equals(data.getString("d_useyn"))?"checked='checked'":"" %> /><label for="p_useyn_n">N</label>
					</td>
				</tr>
				<tr>
					<th scope="row" class="table_title">개요</th>
					<td class="table_02_2">
						<textarea name="p_summary" id="p_summary" rows="8" cols="100" ><%= data.getString("d_summary") %></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row" class="table_title">직업군</th>
					<td class="table_02_2">
						<textarea name="p_jobarea" id="p_jobarea" rows="8" cols="100" ><%= data.getString("d_jobarea") %></textarea>
					</td>
				</tr>
				<tr>
					<th scope="row" class="table_title">필요역량</th>
					<td class="table_02_2">
						<textarea name="p_capacity" id="p_capacity" rows="8" cols="100" ><%= data.getString("d_capacity") %></textarea>
					</td>
				</tr>
			</tbody>
		</table>
		<!-- 수정 영역 e -->
		
	</form>
	<br />
	
	
	
	
	
	
	<!-- content 영역 e -->
	<!-- content 영역 e -->
	<!-- content 영역 e -->
	<!-- content 영역 e -->
	<!-- content 영역 e -->
	
	
</td>
</tr>
<tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>
</table>
</body>
</html>
