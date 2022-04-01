<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.*" %>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if(box == null) { box = RequestManager.getBox(request); }
	
	// 난이도 목록
	ArrayList<DataBox> lvCodeList = (ArrayList<DataBox>) request.getAttribute("lvCodeList");
	
	//교육체계도와 연결된 정규과정 목록
	ArrayList<DataBox> subjSelect = (ArrayList<DataBox>) request.getAttribute("subjSelect");	
	
	//교육체계도와 연결된 열린 목록
	ArrayList<DataBox> goldSelect = (ArrayList<DataBox>) request.getAttribute("goldSelect");	
	
	//교육체계도와 연결된 정규과정 목록 카운트
	String subjSelectCnt = (String) request.getAttribute("subjSelectCnt");	
	
	//교육체계도와 연결된 열린 목록 카운트
	String goldSelectCnt = (String) request.getAttribute("goldSelectCnt");	
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

	});
	
	//선택된 과정 저장
	function fnCmdLvSave(){	
		if(confirm("저장하시겠습니까?")){
			$("#p_process").val("courseLvSave");
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
	<table width="1200" border="0" cellspacing="0" cellpadding="0" height="663">
		<tr>
			<td align="center" valign="top">
				<!-- title 영역 s -->
				<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
					<tr>
						<td style="text-align:left;"><img src="/images/admin/course/c_title11.gif" ></td> <!-- 상단 메뉴명 -->
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
							<button id="oRegBtn" name="btn" class="btn_reg" onclick="fnCmdLvSave(); return false;">저장</button>
						</td>
					</tr>
					<tr><td height="3"></td></tr>
				</table>
				<!-- 버튼 영역 e -->
	
				<form name="form1" id="form1" method="post" action="/servlet/controller.course.EduSystemChartServlet">
					<input type="hidden" name="p_process" id="p_process" value="" />
					<input type="hidden" name="p_chartcode" id="p_chartcode" value="<%=box.getString("p_chartcode") %>" />
					
					<!-- 수정 영역 s -->
					<table width="100%">
						<tr>
							<td style="width= 50%; display: ;">
								<span>정규과정 (<%=subjSelectCnt %>)</span>
								<table cellspacing="1" cellpadding="5" class="table_out" summary="교육체계도 정규과정 난이도">
									<caption style="display: none;">교육체계도 정규과정 난이도</caption>
									<colgroup>
										<col width="12%" />
										<col width="" />
							<%
								if(lvCodeList != null && lvCodeList.size() > 0){
									for(DataBox data : lvCodeList){
							%>
										<col width="8%" />
							<%			
									}
								}
							%>							
									</colgroup>
									<thead>
										<th scope="col" class="table_title">과정코드</th>
										<th scope="col" class="table_title">과정코드명</th>
							<%
								if(lvCodeList != null && lvCodeList.size() > 0){
									for(DataBox data : lvCodeList){
							%>
										<th scope="col" class="table_title"><%=data.getString("d_codenm") %></th>
							<%			
									}
								}
							%>	
									</thead>
									<tbody>
										
					<%
						if(subjSelect != null && subjSelect.size() > 0){
							for(DataBox selectdata : subjSelect){
					%>
										<tr>
											<td class="table_02_1">
												<%=selectdata.getString("d_subj") %>
											</td>
											<td class="table_02_2">
												<%=selectdata.getString("d_subjnm") %>
											</td>			
							<%																																																		
								if(lvCodeList != null && lvCodeList.size() > 0){
									for(DataBox data : lvCodeList){
										if(selectdata.getString("d_lvcode").equals(data.getString("d_code"))){
							%>
											<td class="table_02_1">
												<input type="radio" name="<%=selectdata.getString("d_subj") %>_lv" checked="checked" value="<%=data.getString("d_code") %>" />
											</td>
							<%		
										}else{
							%>
											<td class="table_02_1">
												<input type="radio" name="<%=selectdata.getString("d_subj") %>_lv" value="<%=data.getString("d_code") %>" />
											</td>
							<%												
										}
									}
								}
							%>	
											<input type="hidden" name="p_scd" value="<%=selectdata.getString("d_subj") %>" />
										</tr>
					<%			
							}
						}
					%>							
									</tbody>
								</table>
							</td>
							<td style="width= 50%; display: inline;">
								<span>열린강좌 (<%=goldSelectCnt %>)</span>
								<table cellspacing="1" cellpadding="5" class="table_out" summary="교육체계도 열린강좌 난이도">
									<caption style="display: none;">교육체계도 열린강좌 난이도</caption>
									<colgroup>
										<col width="12%" />
										<col width="" />
							<%
								if(lvCodeList != null && lvCodeList.size() > 0){
									for(DataBox data : lvCodeList){
							%>
										<col width="8%" />
							<%			
									}
								}
							%>							
									</colgroup>
									<thead>
										<th scope="col" class="table_title">과정코드</th>
										<th scope="col" class="table_title">과정코드명</th>
							<%
								if(lvCodeList != null && lvCodeList.size() > 0){
									for(DataBox data : lvCodeList){
							%>
										<th scope="col" class="table_title"><%=data.getString("d_codenm") %></th>
							<%			
									}
								}
							%>	
									</thead>
									<tbody>
										
					<%
						if(goldSelect != null && goldSelect.size() > 0){
							for(DataBox selectdata : goldSelect){
					%>
										<tr>
											<td class="table_02_1">
												<%=selectdata.getString("d_seq") %>
											</td>
											<td class="table_02_2">
												<%=selectdata.getString("d_lecnm") %>
											</td>		
							<%																																																		
								if(lvCodeList != null && lvCodeList.size() > 0){
									for(DataBox data : lvCodeList){
										if(selectdata.getString("d_lvcode").equals(data.getString("d_code"))){
							%>
											<td class="table_02_1">
												<input type="radio" name="<%=selectdata.getString("d_seq") %>_lv" checked="checked" value="<%=data.getString("d_code") %>" />
											</td>
							<%		
										}else{
							%>
											<td class="table_02_1">
												<input type="radio" name="<%=selectdata.getString("d_seq") %>_lv" value="<%=data.getString("d_code") %>" />
											</td>
							<%												
										}
									}
								}
							%>					
											<input type="hidden" name="p_gcd" value="<%=selectdata.getString("d_seq") %>" />												
										</tr>
					<%			
							}
						}
					%>							
									</tbody>
								</table>					
							</td>
						</tr>
					</table>
					<!-- 수정 영역 e -->
				</form>
			</td>
		</tr>
		<tr>
			<td><%@ include file = "/learn/library/getJspName.jsp" %></td>
		</tr>
	</table>
</body>
</html>
