<%
//**********************************************************
//  1. 제	목: 입과 FileToDB
//  2. 프로그램명 : za_BatchFileToDB_P.jsp
//  3. 개	요: 입과 FileToDB 등록
//  4. 환	경: JDK 1.3
//  5. 버	젼: 0.1
//  6. 작	성: 이창훈 2004.11.20
//  7. 수	정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "jxl.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.complete.FinishBean" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

%>
<html>
<head>
<title>입과FileToDB</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
function golistPage() {
  document.form1.action = "/servlet/controller.propose.AcceptServlet";
  document.form1.p_process.value = "proposeFileToDB";
  document.form1.submit();
}
//-->
</SCRIPT>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="post" >
  <input type="hidden" name="p_process" value="">
  <input type="hidden" name="s_year"  value="<%=box.get("s_year")%>">
  <input type="hidden" name="s_upperclass"  value="<%=box.get("s_upperclass")%>">
  <input type="hidden" name="s_middleclass"  value="<%=box.get("s_middleclass")%>">
  <input type="hidden" name="s_lowerclass"  value="<%=box.get("s_lowerclass")%>">
  <input type="hidden" name="s_subjcode"  value="<%=box.get("s_subjcode")%>">
  <input type="hidden" name="s_subjseq"  value="<%=box.get("s_subjseq")%>">
</form>
<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
	<td align="center" valign="top">
			<!----------------- title 시작----------------->
		<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
		<tr>
		<td><img src="/images/admin/propose/p_title06.gif" ></td>
		<td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
		</tr>
	</table>
	<!----------------- title 끝 ----------------->
	<br>
	<br>

	<!----------------- 교육정보시작 ----------------->
	<table cellspacing="0" cellpadding="1" class="form_table_out">
		<tr>
		<td bgcolor="#C6C6C6" align="center">
			<table cellspacing="0" cellpadding="0" class="form_table_bg" >
			<tr>
				<td height="15"></td>
			</tr>
			<tr>
				<td align="center" valign="middle">
				<table cellspacing="0" cellpadding="0" width="99%" class="form_table">
					<tr>
					<td>
						<b><font size="3">
							&nbsp;&nbsp;년도:<%=box.get("s_year")%>
							&nbsp;&nbsp;&nbsp;&nbsp;과정:<%=box.get("s_subjnm")%>
							&nbsp;&nbsp;&nbsp;&nbsp;차수:<%=box.get("s_subjseq")%>
							</font></b>
					</td>
					</tr>
				</table>
				</td>
			</tr>
			<tr>
				<td height="15"></td>
			</tr>
			</table>
		</td>
		</tr>
	</table>
	<br><br>
	<br>
	<!---------------------------------------------------------------------------->

<%

%>

	<!----------------- 결과정보 시작 ----------------->

	<table>
		<tr>
		<td>
			<font color=red>ERROR건수가 1건이상 발생하면 일괄처리가 자동취소됩니다.</font>
		</td>
		</tr>
	</table>

	<table cellspacing="1" cellpadding="5" class="table_out">
		<tr>
			<td colspan="15" class="table_top_line"></td>
		</tr>
		<tr>
			<td class="table_title" colspan="8">ERROR : <%=errcnt%>건</td>
		</tr>
		<tr>
			<td class="table_title" width="10%">줄번호</td>
			<td class="table_title" width="20%">사번</td>
			<td class="table_title" width="15%">입력차수</td>
			<td class="table_title" >MESSAGE</td>
		</tr>
		<%
			if(errcnt != 0){
			out.print(v_errString);
			}else{
		%>
			<tr>
			<td class="table_02_1" colspan="8">검색된 Error가 없습니다.</td>
		</tr>
			<%}%>
		</tr>
	</table>
	<!----------------- 결과 정보 출력 끝 ----------------->
	<BR><BR>
	<!----------------- 결과정보 시작 ----------------->
	<table cellspacing="1" cellpadding="5" class="table_out">
		<tr>
			<td colspan="15" class="table_top_line"></td>
		</tr>
		<tr>
			<td class="table_title" colspan="8">정상입력</td>
		</tr>
		<tr>
			<td class="table_title" width="10%">줄번호</td>
			<td class="table_title" width="20%">사번</td>
			<td class="table_title" width="15%">입력차수</td>
			<td class="table_title" >MESSAGE</td>
		</tr>
		<%=v_okString%>
	</table>
	<!----------------- 결과 정보 출력 끝 ----------------->
	<br>
	<br>
	<!----------------- 에러메세지 시작 ----------------->
	<table>
		<tr>
		<td>
			<font color=blue><b>(엑셀양식 Error)<b></font>
		</td>
		</tr>
		<tr>
		<td>
			<font color=red>1. 차수 ALL선택후 차수를 입력하지 않은경우 Error처리 됩니다.</font><br>
		</td>
		</tr>
		<tr>
		<td>
			<font color=red>&nbsp;&nbsp;-->차수입력후 다시 시도하여 주십시오.</font><br>
		</td>
		</tr>
		<tr>
		<td>
			<font color=red>2. 엑셀양식에 보이지 않는 문자나 기호가 들어가 있을 수 있습니다.</font><br>
		</td>
		</tr>
		<tr>
		<td>
			<font color=red>&nbsp;&nbsp;--> 입력한 영역<b>(사번,차수,과정)</b>만 지정하고 복사<b>[Ctrl+C]</b>후 새엑셀파일을 열어 붙여넣기<b>[Ctrl+V]</b>를 하여 <br>
							&nbsp;&nbsp;&nbsp;&nbsp;새이름으로 저장한후 다시시도 하여 주십시오.</font><br>
		</td>
		</tr>
	</table>
	<br>
	<!----------------- 등록, 미리보기 버튼 시작 ----------------->
	<table width="97%" border="0" cellpadding="0" cellspacing="0">
		<tr>
		<td align="center" ><a href="javascript:golistPage()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
		</tr>
	</table>
	<!----------------- 등록, 미리보기 버튼 끝 ----------------->
	<br>
	<br>
	</td>
  </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>