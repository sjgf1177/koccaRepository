<%
//**********************************************************
//  1. 제      목: 에러 처리 화면
//  2. 프로그램명: printSystemErrorMessage.jsp
//  3. 개      요: System Error 처리 화면
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이정한 2003. 4. 9
//  7. 수      정: 이정한 2003. 4. 9
//***********************************************************
%>

<%@ page isErrorPage="true" contentType="text/html;charset=euc-kr"%>

<%
        String errMsg = request.getParameter("errMsg");
		out.print(errMsg);
		if( errMsg == null ){
			errMsg = "";
		} else {
		  out.print(errMsg);
		}
		
%>


<HTML>
<HEAD>
<TITLE></TITLE>
<LINK rel="stylesheet" href="/css/style_main.css" type="text/css">
<SCRIPT src="/script/cresys.js" language="JavaScript"></SCRIPT>


</HEAD>

<BODY topmargin="0" leftmargin="0">

<TABLE cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
	
	<tr valign="top">
		<td>
			<TABLE cellpadding="0" cellspacing="0" border="0" width="100%" height="100%">
				<tr valign="top">
					<td width="200" >
						<!-- 왼쪽 메뉴 시작 -->
						<TABLE cellpadding="0" cellspacing="0" border="0" width="200">
							<tr>
								<td align="right" style="padding-right:1px">
									<!--IMG src="/images/common/left_title_login.gif" border="0"-->
								</td>
							</tr>
						</TABLE>
						<!-- 왼쪽 메뉴 끝 -->
					</td>
					<td>
						<!-- 검색 시작 -->
						<%//@include file="/incmenu/search_knowledge.jsp"%>
						<!-- 검색 끝 -->
						<!-- 본문 시작 -->
						<table width="628" align="center" height="100%" border="0" cellpadding="0" cellspacing="0">
						  <!--
						  <tr> 
						    <td width="19" height="28" align="left" valign="top"><img src="/images/admin/portal/bullet01.gif"></td>
						    <td width="767" valign="top" class="title">시스템 오류</td>
						  </tr>
						  <tr> 
						    <td height="1" colspan="2" bgcolor="#CCCCCC"><img src="/images/admin/portal/blank.gif" width="1" height="1"></td>
						  </tr>
						  <tr> 
						    <td height="3" colspan="2" bgcolor="#F0F0F0"><img src="/images/admin/portal/blank.gif" width="1" height="1"></td>
						  </tr>
						-->
						  <tr>
						    <td align="center" width="18" style="padding-left:20px">
						    <img src="/images/common/system_error.gif" border="0">
						
						    <!--내용부분 끝 --></td>
						  </tr>
						</table>
						<!-- 본문 끝 -->
					</td>
				</tr>
			</TABLE>
		</td>
	</tr>
	<tr height="51">
		<td valign="top">
			<!-- FOOTER 시작 -->
			<jsp:include page="/incmenu/footer.jsp" flush="true">
				<jsp:param name="sUrl" value="<%=request.getServletPath().toString()%>"/>
			</jsp:include>
			<!-- FOOTER 끝 -->
		</td>
	</tr>
</TABLE>
</BODY>
</HTML>

