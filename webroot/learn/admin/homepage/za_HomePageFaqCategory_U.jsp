
<%
//**********************************************************
//  1. 제      목: HomePage FAQ Category 수정
//  2. 프로그램명 : za_HomePageFaqCategory_U.jsp
//  3. 개      요: HomePage FAQ Category 수정
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 신선철 2005. 1. 1
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType="text/html;charset=MS949"%>
<%@page errorPage="/learn/library/error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.credu.homepage.*"%>
<%@ page import="com.credu.library.*"%>
<%@ page import="com.credu.course.*"%>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    
	DataBox dbox = (DataBox)request.getAttribute("selectHomePageFaqCategory");

	String  v_faqcategory    = "";
    String  v_faqcategorynm  = "";
   
    String s_userid = box.getSession("userid");

	String  v_grcode	  = box.getString("p_grcode");
	String  v_grcodenm	  = box.getString("p_grcodenm");
	v_faqcategory = dbox.getString("d_faqcategory");
	v_faqcategorynm = dbox.getString("d_faqcategorynm");
	
	// 교육그룹
    ArrayList list1 = (ArrayList)request.getAttribute("GrcodeList");

%>

<html>
<head>
<title>Untitled Document</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="STYLESHEET" type="text/css" href="/css/admin_style.css">
<SCRIPT LANGUAGE="javascript">
function updateAction() {
	if(document.form1.p_faqcategorynm.value == ""){
		alert("카테고리명을 입력하세요!");
		document.form1.p_faqcategorynm.focus();
		return;
	}

	if (confirm("저장하시겠습니까?")) {
		document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
		document.form1.p_process.value = "update";
		document.form1.submit();
	}else{
		return;
	}
}

function deleteAction() {
	if (confirm("삭제하시겠습니까?")) {
		document.form1.action = "/servlet/controller.homepage.HomePageFaqCategoryAdminServlet";
		document.form1.p_process.value = "delete";
		document.form1.submit();
	}
	else {
		return;
	}
}

</script>

</head>

<body topmargin=0 leftmargin=0>
	<form name="form1" method="post">
		<input type="hidden" name="p_process" value=""> <input type="hidden" name="p_faqcategory" value="<%=v_faqcategory%>">

		<table width="1300" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td align="center">

					<table width="1260" border="0" cellspacing="0" cellpadding="0" class=page_title>
						<tr>
							<td><img src="/images/admin/homepage/h_title01.gif"></td>
							<td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
						</tr>
					</table> <br>
					<table width="1260" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td width="10%" class=txt_input align="left">★ 교육그룹 :</td>
							<td width="35%" align="left"><%=v_grcodenm%></td>
							<td width="55%"></td>
						</tr>
					</table> <br>
					<table width="970" border="0" cellpadding="0" cellspacing="1" class="table_out">

						<tr>
							<td class="table_title" width="25%">FAQ 카테고리코드</td>
							<td class="table_02_2" width="75%"><%=v_faqcategory%></td>
						</tr>
						<tr>
							<td class="table_title">FAQ 카테고리명</td>
							<td class="table_02_2"><input name="p_faqcategorynm" type="text" class="input" style="width: 300" value="<%=v_faqcategorynm%>"></td>
						</tr>
						<tr class="table_02_2">
							<td height="25" class="table_title"><strong>대상교육그룹</strong></td>
							<td height="25" class="table_02_2">
							<%
							int j = 1;
							for(int i=0;i<list1.size();i++){
								EduGroupData edudata = (EduGroupData)list1.get(i);
							%> 
								<div style="width:25%;float: left">
									<input name="p_grcode" type=radio value="<%=edudata.getGrcode()%>" <%=v_grcode.equals(edudata.getGrcode()) ? "checked" : "" %>><%=edudata.getGrcodenm()%>&nbsp; 
								</div>
							<%
								if(j%4==0){
							%>
									<br/>
							<%
								}
								j++;
							}
							%>
							</td>
						</tr>
					</table>
					<table width="1260" border="0" cellspacing="0" cellpadding="0">
						<tr>
							<td height=6></td>
							<td height=6></td>
							<td height=6></td>
							<td align="right" height=6></td>
							<td width="12" height=6></td>
							<td align="right" height=6></td>
						</tr>

						<tr>
							<td width="1113" align="right"></td>
							<td width="45" align="right">
								<a href="javascript:updateAction()" onfocus=this.blur()> <img src="/images/admin/button/btn_save.gif" border="0"></a>
							</td>
							<td width="14">&nbsp;</td>
							<td width="32" align="right"><a href="javascript:deleteAction()" onfocus=this.blur()> <img src="/images/admin/button/btn_del.gif" border="0"></a></td>
							<td width="12">&nbsp;</td>
							<td width="44" align="right"><a href="javascript:history.back();" onfocus=this.blur()> <img src="/images/admin/button/btn_list.gif" border="0"></a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</form>
	<%@ include file="/learn/library/getJspName.jsp"%>
</body>
</html>