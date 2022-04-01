<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.*" %>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if(box == null) { box = RequestManager.getBox(request); }
	
	
	//B2C 전체정규과정 목록
	ArrayList<DataBox> subjAll = (ArrayList<DataBox>) request.getAttribute("subjAll");
	
	//B2C 전체열린강좌 목록
	ArrayList<DataBox> goldAll = (ArrayList<DataBox>) request.getAttribute("goldAll");
	
	//교육체계도와 연결된 과정 목록
	ArrayList<DataBox> subjSelect = (ArrayList<DataBox>) request.getAttribute("subjSelect");
	
	

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

	//jQuery Ready
	$(document).ready(function(){
		
		var mvalue = "subjSelect";
		var unmvalue = "subjAll";
		
		$("#moveRight_"+mvalue).click(function (){
			optionSelMove(mvalue, unmvalue);
		});
		
		$("#moveLeft_"+mvalue).click(function (){
			optionAllMove(mvalue, unmvalue);
		});
		
		$("#moveRightAll_"+mvalue).click(function (){
			optionSelMoveAll(mvalue, unmvalue);
		});
		
		$("#moveLeftAll_"+mvalue).click(function (){
			optionAllMoveAll(mvalue, unmvalue);
		});
		
	});

	//선택된 그룹으로 이동
	function optionSelMove(mvalue, unvalue){
		$("#"+unvalue+" option:selected").each(function() {
			var opt = $(this).clone();
			$(this).remove();
			opt.removeAttr("selected");
			$("#"+mvalue).append(opt);
		});
	}
	
	//선택되지 않은 그룹으로 이동
	function optionAllMove(mvalue, unvalue){
		$("#"+mvalue+" option:selected").each(function() {
			var opt = $(this).clone();
			$(this).remove();
			opt.removeAttr("selected");
			$("#"+unvalue).append(opt);
		});
	}
	
	//선택된 그룹으로 전체 이동
	function optionSelMoveAll(mvalue, unvalue){
		$("#"+unvalue+" option").each(function() {
			$(this).attr("selected","selected");
		});
		optionSelMove(mvalue, unvalue);
	}
	
	//선택되지 않은 그룹으로 전체 이동
	function optionAllMoveAll(mvalue, unvalue){
		$("#"+mvalue+" option").each(function() {
			$(this).attr("selected","selected");
		});
		
		optionAllMove(mvalue, unvalue);
	}
	
	
	//선택된 과정 저장
	function fnCmdUpdate(){
		
		$("#subjSelect > option").each(function() {
			$(this).attr("selected","selected");
		});
	
		
		if(confirm("저장하시겠습니까?")){
			$("#p_process").val("subjcourseUpdate");
			$("#form1").attr({
						action:"/servlet/controller.course.EduSystemChartServlet",
						method:"post",
						target:"_self"
						});
			$("#form1").submit();
		}
	}
	
	//목록 
	function fnCmdListPage(){
		
		$("#p_process").val("listPage");
		$("#form1").attr({
					action:"/servlet/controller.course.EduSystemChartServlet",
					method:"post",
					target:"_self"
					});
		$("#form1").submit();
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
			</td>
		</tr>
		<tr><td height="3"></td></tr>
	</table>
	<!-- 버튼 영역 e -->
	
	
	<form name="form1" id="form1" method="post" action="/servlet/controller.course.EduSystemChartServlet">
		<input type="hidden" name="p_process" id="p_process" value="" />
		<input type="hidden" name="p_chartcode" id="p_chartcode" value="<%=box.getString("p_chartcode") %>" />
		
		
		
		<!-- 수정 영역 s -->
		<table cellspacing="1" cellpadding="5" class="table_out" summary="교육체계도 과정연결">
			<caption style="display: none;">교육체계도 과정연결</caption>
			<colgroup>
				<col width="47%" />
				<col width="6%" />
				<col width="47%" />
			</colgroup>
			<thead>
				<th scope="col" class="table_title">전체과정 목록</th>
				<th scope="col" class="table_title"></th>
				<th scope="col" class="table_title">선택과정 목록</th>
			</thead>
			<tbody>
				<tr>
					<td class="table_02_2">
						<span style="width:98%;">정규과정</span>
						<select id="subjAll" name="subjAll" style="width:98%; height:250px" multiple="multiple">
							<%
								if(subjAll != null && subjAll.size() > 0){
									for(DataBox alldata : subjAll){
							%>
							<option value="<%=alldata.getString("d_subj") %>">[<%=alldata.getString("d_subj") %>]<%=alldata.getString("d_subjnm") %></option>
							<%			
									}
								}
							%>
						
						</select>
						<br/>
						<br/>
						<span style="width:98%;">열린강좌</span>
						<select id="goldAll" name="goldAll" style="width:98%; height:250px" multiple="multiple">
							<%
								if(goldAll != null && goldAll.size() > 0){
									for(DataBox alldata : goldAll){
							%>
							<option value="<%=alldata.getString("d_seq") %>">[<%=alldata.getString("d_seq") %>]<%=alldata.getString("d_lecnm") %></option>
							<%			
									}
								}
							%>
						
						</select>						
					</td>
					<td class="table_02_3">
						<button type="button" id="moveRight_subjSelect" name="btn" class="btn_reg" onclick="return false;">&#62;</button><br />
						<button type="button" id="moveLeft_subjSelect" name="btn" class="btn_reg" onclick="return false;">&#60;</button><br />
						<br />
						<button type="button" id="moveRightAll_subjSelect" name="btn" class="btn_reg" onclick="return false;">&#62;&#62;</button><br />
						<button type="button" id="moveLeftAll_subjSelect" name="btn" class="btn_reg" onclick="return false;">&#60;&#60;</button><br />
					</td>
					<td class="table_02_2">
						<select id="subjSelect" name="subjSelect" style="width:98%; height:550px" multiple="multiple">
							<%
								if(subjSelect != null && subjSelect.size() > 0){
									for(DataBox selectdata : subjSelect){
							%>
							<option value="<%=selectdata.getString("d_subj") %>">[<%=selectdata.getString("d_subj") %>]<%=selectdata.getString("d_subjnm") %></option>
							<%			
									}
								}
							%>
						</select>
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
