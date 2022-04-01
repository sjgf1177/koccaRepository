
<%
//**********************************************************
//  1. 제      목: 템플릿 관리화면
//  2. 프로그램명: za_Templet_L.jsp
//  3. 개      요: 템플릿 관리화면 수정
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2005. 06. 21
//  7. 수      정: 
//**********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page errorPage="/learn/library/error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="java.text.*"%>
<%@ page import="com.credu.system.*"%>
<%@ page import="com.credu.library.*"%>
<%@ page import="com.credu.templet.*"%>
<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    String v_process  = box.getString("p_process");
    String v_grcode   = box.getString("p_grcode");
    String v_grcodenm = box.getString("p_grcodenm");
    String v_grtype   = box.getString("p_grtype");

    String v_type =  (String)request.getAttribute("Grcodetype");
    if (v_type.equals("")) {
        if (v_grtype.equals("KOCCA")) v_type = "A";
        else                          v_type = "B";
    }
%>


<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/admin_style.css" rel="stylesheet" type="text/css">
<SCRIPT LANGUAGE="JavaScript">
<!--

// 수정
function update() {
    document.form1.action="/servlet/controller.templet.TempletServlet"
    document.form1.p_process.value = 'updateManager';
    document.form1.submit();
}

// 다음단계 (메인)
function movenext() {
    document.form1.action="/servlet/controller.templet.TempletServlet"
    document.form1.p_process.value = 'updateMainPage';
    document.form1.submit();
}

// 리스트로 이동
function movelist(){
    document.form1.action="/servlet/controller.templet.TempletServlet"
    document.form1.p_process.value = 'listPage';
    document.form1.submit();
}
//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
	<form name="form1" method="post">
		<input type="hidden" name="p_process" value="<%=v_process%>"> <input type="hidden" name="p_grcode" value="<%=v_grcode%>"> <input type="hidden" name="p_grtype" value="<%=v_grtype%>">

		<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
			<tr>
				<td align="center" valign="top">
					<!----------------- title 시작 ----------------->
					<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
						<tr>
							<td><img src="../../../images/admin/course/c_title01_1.gif"></td>
							<td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
						</tr>
					</table> <!----------------- title 끝 -----------------> <br> <br> <!----------------- 교육그룹 시작 ----------------->

					<table class="table_out" cellspacing="1" cellpadding="5">
						<tr>
							<td colspan="2" class="table_top_line"></td>
						</tr>
						<tr>
							<td width="*" class="table_title"><b>교육그룹 업체</b></td>
							<td width="83%" height="25" colspan="7" class="table_02_2"><%=v_grcodenm%></td>
						</tr>

						<tr>
							<td align="center" class="table_title">Template Type</td>
							<td class="table_02_1">
								<table width="100%" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="33%"><input type="radio" id="p_typeA" name="p_type" value="A" <%if (v_type.equals("A")) {%> checked <% } %>><label for="p_typeA"> Type A</label></td>
										<td width="33%"><input type="radio" id="p_typeB" name="p_type" value="B" <%if (v_type.equals("B")) {%> checked <% } %>><label for="p_typeB"> Type B</label></td>
<%-- 										<td width="33%"><input type="radio" id="p_typeC" name="p_type" value="C" <%if (v_type.equals("C")) {%> checked <% } %>><label for="p_typeC"> Type C</label></td> --%>
									</tr>
									<tr>
										<td colspan="3">&nbsp;</td>
									</tr>
									<tr>
										<td><img alt="typeA" src="/images/portal/asp/typeA.jpg" width="220px;"></td>
										<td><img alt="typeB" src="/images/portal/asp/typeB.jpg" width="220px;"></td>
<!-- 										<td><img alt="typeC" src="/images/portal/asp/typeC.jpg" width="220px;"></td> -->
									</tr>
									<!-- 
									<tr>
										<td colspan="3">&nbsp;</td>
									</tr>
									<tr>
										<td width="33%"><input type="radio" id="p_typeD" name="p_type" value="D" <%if (v_type.equals("D")) {%> checked <% } %>><label for="p_typeD"> Type D</label></td>
										<td width="33%"><input type="radio" id="p_typeE" name="p_type" value="E" <%if (v_type.equals("E")) {%> checked <% } %>><label for="p_typeE"> Type E</label></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td colspan="3">&nbsp;</td>
									</tr>
									<tr>
										<td><img alt="typeD" src="/images/portal/asp/typeD.jpg" width="220px;"></td>
										<td><img alt="typeE" src="/images/portal/asp/typeE.jpg" width="220px;"></td>
										<td>&nbsp;</td>
									</tr>
									 -->
								</table>
							</td>
						</tr>
					</table> <!----------------- 교육그룹 끝 -----------------> <br>
					<table width="97%" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height="10"></td>
						</tr>
						<tr>
							<td align="center">
								<table width="197" border="0" cellspacing="0" cellpadding="0">
									<tr>
										<td width="59"><a href="javascript:update();"><img src="/images/admin/button/btn_register.gif" border="0"></a></td>
										<!--td width="59"><a href="javascript:movenext()"><img src="/images/admin/button/btn_next.gif" width="55" height="18" border="0"></a></td-->
										<td width="79"><a href="javascript:movelist();"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>

	<%@ include file="/learn/library/getJspName.jsp"%>
</body>
</html>
