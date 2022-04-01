<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.credu.library.*" %>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");
	if(box == null) { box = RequestManager.getBox(request); }

	//교육체계도 목록
	ArrayList<DataBox> resultList = (ArrayList<DataBox>) request.getAttribute("resultList");

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
	
	
	//과정연결 화면
	function fnCmdSubjCoursePage(chartcode){
	
		$("#p_chartcode").val(chartcode);
		$("#p_process").val("subjcourse");
		$("#form1").attr({
					action:"/servlet/controller.course.EduSystemChartServlet",
					method:"post",
					target:"_self"
					});
		$("#form1").submit();
	}
	
	//분류 등록 화면
	function fnCmdInsertPage(codegubun){
	
		$("#p_codegubun").val(codegubun);
		$("#p_process").val("insertPage");
		$("#form1").attr({
					action:"/servlet/controller.course.EduSystemChartServlet",
					method:"post",
					target:"_self"
					});
		$("#form1").submit();
	}
	
	//분류 수정 화면
	function fnCmdUpdatePage(chartcode){
		
		if(chartcode != null && chartcode != "0"){
			$("#p_chartcode").val(chartcode);
			$("#p_process").val("updatePage");
			$("#form1").attr({
						action:"/servlet/controller.course.EduSystemChartServlet",
						method:"post",
						target:"_self"
						});
			$("#form1").submit();
		}else{
			return;
		}
	
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
	
	<form name="form1" id="form1" method="post" action="/servlet/controller.course.EduSystemChartServlet">
		<input type="hidden" name="p_process" id="p_process" value="" />
		<input type="hidden" name="p_codegubun" id="p_codegubun" value="" />
		<input type="hidden" name="p_chartcode" id="p_chartcode" value="" />
		
		
	</form>
	
	
	<!-- 검색 영역 s -->
<!-- 	<table cellspacing="0" cellpadding="1" class="form_table_out"> -->
	
			
			
<!-- 			<tr> -->
<!-- 				<td bgcolor="#C6C6C6" align="center"> -->
<!-- 					<table cellspacing="0" cellpadding="0" class="form_table_bg"> -->
<!-- 						<tr><td height="7" width="99%"></td></tr> -->
<!-- 						<tr> -->
<!-- 							<td align="center" width="99%" valign="middle"> -->
<!-- 								<table width="99%" cellspacing="0" cellpadding="0" class="form_table"> -->
<!-- 									<tr> -->
<!-- 										<td> -->
<!-- 											<table cellspacing="0" cellpadding="0" width="99%"> -->
<!-- 												<colgroup> -->
<!-- 													<col width="10%"> -->
<!-- 													<col width="*"> -->
<!-- 												</colgroup> -->
<!-- 												<tr> -->
<!-- 													<td class="_tdT">교육그룹</td> -->
<!-- 													<td class="_tdS" style="text-align:left; padding-left:4px;"> -->
<!-- 														selectbox -->
<!-- 													</td> -->
<!-- 												</tr> -->
<!-- 											</table> -->
<!-- 										</td> -->
<!-- 									</tr> -->
<!-- 								</table> -->
<!-- 							</td> -->
<!-- 						</tr> -->
<!-- 						<tr><td height="7" width="99%"></td></tr> -->
<!-- 					</table> -->
<!-- 				</td> -->
<!-- 			</tr> -->
			
<!-- 		</form> -->
		
<!-- 	</table> -->
	<!-- 검색 영역 e -->
<!-- 	<br /> -->
	
	
	<!-- 버튼 영역 s -->
<!-- 	<table cellpadding="0" cellspacing="0" class="table1"> -->
<!-- 		<tr> -->
<!-- 			<td align="right" height="20"> -->
<!-- 				<a href="#" onclick="return false;">대분류 추가</a> -->
<!-- 			</td> -->
<!-- 		</tr> -->
<!-- 		<tr><td height="3"></td></tr> -->
<!-- 	</table> -->
	<!-- 버튼 영역 e -->
	
	
	<!-- 목록 영역 s -->
	<table cellspacing="1" cellpadding="5" class="table_out" summary="교육체계도 분류 목록">
		<caption style="display: none;">교육체계도 분류 목록</caption>
		<colgroup>
				<col width="15%" />
				<col width="15%" />
				<col width="20%" />
<!-- 				<col width="20%" />
				<col width="*" /> -->
			</colgroup>
		<tbody>
			<tr>
			
				<!-- *************************************************************************************************** -->
				<!-- 1,2depth 메뉴는 사용자 화면에서 보여지는 코드와 달라지지 않도록 수정할 수 없도록 함(등록이나 수정이 이루어질 경우 사용자화면에서 동일 작업 필요) -->
				<!-- *************************************************************************************************** -->
				<td class="table_title">1depth
 					<button name="btn" class="btn_basic" onclick="fnCmdInsertPage('1'); return false;">추가</button>
				</td>
				<td class="table_title">2depth
 					<button name="btn" class="btn_basic" onclick="fnCmdInsertPage('2'); return false;">추가</button>
				</td>
				
				<!-- <td class="table_title">3depth <button name="btn" class="btn_basic" onclick="fnCmdInsertPage('3'); return false;">추가</button></td>
				<td class="table_title">4depth <button name="btn" class="btn_basic" onclick="fnCmdInsertPage('4'); return false;">추가</button></td>
				<td class="table_title">5depth <button name="btn" class="btn_basic" onclick="fnCmdInsertPage('5'); return false;">추가</button></td> -->
			</tr>
			<%
				String achart = "";
				String bchart = "";
/* 				String cchart = "";
				String dchart = "";
				String echart = ""; */
				
				if(resultList != null && resultList.size() > 0){
					for(int i = 0; i < resultList.size(); i++){
						DataBox data = (DataBox) resultList.get(i);
						
			%>
			<tr>
						<%
							if(!achart.equals(data.getString("d_codea"))){
								achart = data.getString("d_codea");
						%>
							<td class="table_01" <%=data.getInt("d_acnt")>1?"rowspan=\""+data.getInt("d_acnt")+"\"":"" %>>
								<a href="#" onclick="fnCmdUpdatePage('<%=data.getString("d_codea") %>'); return false;"><%=!"".equals(data.getString("d_codenamea"))?data.getString("d_codenamea"):"-" %></a>
								<%-- <%=!"".equals(data.getString("d_codenamea"))?data.getString("d_codenamea"):"-" %> --%>
							</td>
						<%		
							}
						%>
						<%-- <%
							if(!bchart.equals(data.getString("d_codeb")) ){
								bchart = data.getString("d_codeb");
						%>
							<td class="table_02_1" <%=data.getInt("d_bcnt")>1?"rowspan=\""+data.getInt("d_bcnt")+"\"":"" %>>
								<a href="#" onclick="fnCmdUpdatePage('<%=data.getString("d_codeb") %>'); return false;"><%=!"".equals(data.getString("d_codenameb"))?data.getString("d_codenameb"):"-" %></a>
								<%=!"".equals(data.getString("d_codenameb"))?data.getString("d_codenameb"):"-" %>
							</td>
						<%		
							}
						%> --%>
						
						<%-- <%
							if(!cchart.equals(data.getString("d_codec"))){
								cchart = data.getString("d_codec");
						%>
							<td class="table_02_1" <%=data.getInt("d_ccnt")>1?"rowspan=\""+data.getInt("d_ccnt")+"\"":"" %>>
								<a href="#" onclick="fnCmdUpdatePage('<%=data.getString("d_codec") %>'); return false;"><%=!"".equals(data.getString("d_codenamec"))?data.getString("d_codenamec"):"-" %></a>
							</td>
						<%		
							}
						%>
						<%
							if(!dchart.equals(data.getString("d_coded"))){
								dchart = data.getString("d_coded");
						%>
							<td class="table_02_1" <%=data.getInt("d_dcnt")>1?"rowspan=\""+data.getInt("d_dcnt")+"\"":"" %>>
								<a href="#" onclick="fnCmdUpdatePage('<%=data.getString("d_coded") %>'); return false;"><%=!"".equals(data.getString("d_codenamed"))?data.getString("d_codenamed"):"-" %></a>
							</td>
						<%		
							}
						%> --%>
						<%
							if(!bchart.equals(data.getString("d_codeb"))){
								bchart = data.getString("d_codeb");
						%>
								<%if(!"".equals(data.getString("d_codenameb"))){ %>
									<td class="table_02_1" <%=data.getInt("d_bcnt")>1?"rowspan=\""+data.getInt("d_bcnt")+"\"":"" %>>
										<a href="#" onclick="fnCmdUpdatePage('<%=data.getString("d_codeb") %>'); return false;"><%=data.getString("d_codenameb") %></a>
									</td>
									<td class="table_02_1">
										<button name="btn" class="btn_basic" onclick="fnCmdSubjCoursePage('<%=data.getString("d_codeb") %>'); return false;">과정연결</button>
									</td>
								<%}else{ %>
								<td class="table_02_1" <%=data.getInt("d_bcnt")>1?"rowspan=\""+data.getInt("d_bcnt")+"\"":"" %>>-</td>
								<td class="table_02_1">
									<button name="btn" class="btn_basic" onclick="fnCmdSubjCoursePage('<%=data.getString("d_codea") %>'); return false;">과정연결</button>
								</td>
								<%} %>								
						<%		
							}
						%>
			</tr>
			<%			
					}
				}else{
			%>
			<tr><td colspan="3" class="table_02_2">해당 목록이 없습니다</td></tr>
			<%		
				}
			%>
		</tbody>
	</table>
	<!-- 목록 영역 e -->
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
