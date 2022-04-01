<%
//**********************************************************
//  1. 제      목: 튜터링커뮤니티 커뮤니티 통계
//  2. 프로그램명 : communityStatisList.jsp
//  3. 개      요: 커뮤니티 통계
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2013.07.17
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
	RequestBox box = null;
	box = (RequestBox)request.getAttribute("requestbox");
	
	out.print(box);
	
	//템플릿 설정 관련
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name;
    
    String	v_process	= box.getStringDefault("p_process","detailView");
	String	v_pageno	= box.getStringDefault("p_pageno","0");
	String	v_pagesize	= box.getStringDefault("p_pagesize","0");
	String	v_tgubun	= box.getStringDefault("s_tgubun", "ALL");
	String	v_area		= box.getStringDefault("s_area", "ALL");
	String	v_sdate		= box.getStringDefault("s_sdate",FormatDate.getDate("yyyyMMdd"));
	String	v_edate		= box.getStringDefault("s_edate",FormatDate.getDate("yyyyMMdd"));
	
	String	v_tabseq	= box.getString("p_tabseq");
	String	v_seq		= box.getString("p_seq");
	String	v_types		= box.getString("p_types");
	String	v_subj		= box.getString("p_subj");
	String	v_subjseq	= box.getString("p_subjseq");
	String	v_year		= box.getString("p_year");
	String	v_lesson	= box.getString("p_lesson");
    
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
function register_tutorcommunity(){
	document.form1.p_content.value = document.form1.Wec.MIMEValue;
    document.form1.p_content.value.replace("&","&amp;");
    
	document.form1.p_process.value = "register";
	document.form1.submit();
}
</script>

</head>

<body id="tutor" bgcolor="#FFFFFF" text="#000000">
<form name="form1" action="/servlet/controller.tutorcommunity.TutorCommunityStatisServlet">
	<input type = "text" name = "p_process"	value = "<%=v_process %>" />
    <input type = "text" name = "p_pageno"	value = "<%=v_pageno %>"/>
    <input type = "text" name = "p_pagesize"	value = "<%=v_pagesize %>"/>
    <input type = "text" name = "s_tgubun"	value = "<%=v_tgubun %>"/>
    <input type = "text" name = "s_area"		value = "<%=v_area %>"/>
    <input type = "text" name = "s_sdate"		value = "<%=v_sdate %>"/>
    <input type = "text" name = "s_edate"		value = "<%=v_edate %>"/>
    
    <input type = "text" name = "p_tabseq"	value = "<%=v_tabseq %>"/>
    <input type = "text" name = "p_seq"		value = "<%=v_seq %>"/>
    <input type = "text" name = "p_types"	value = "<%=v_types %>"/>
    <input type = "text" name = "p_subj"	value = "<%=v_subj %>"/>
    <input type = "text" name = "p_subjseq"	value = "<%=v_subjseq %>"/>
    <input type = "text" name = "p_year"	value = "<%=v_year %>"/>
    <input type = "text" name = "p_lesson"	value = "<%=v_lesson %>"/>
    
    <input type = "hidden" name = "p_content"	value = "" />

<table width="100%" border="0" cellspacing="0" cellpadding="0" >
	<tr>
		<td align="center" valign="top">
		<!----------------- title 시작 ----------------->
			<table width="100%" border="0" cellspacing="0" cellpadding="0" class="page_title">
				<tr> 
					<td align="left"><img src="/images/admin/tutorcommunity/lms_title_commstat.gif"></td>
				</tr>
			</table>
		<!----------------- title 끝 ----------------->
		</td>
	</tr>
	
	<tr>
		<td>
			<table class="table_out" cellspacing="1" cellpadding="5" border="0" width="100%">
				<tr>
					<td colspan="2" class="table_top_line_1"></td>
				</tr>
				<tr>
					<td class="table_title" width="12%">제목</td>
					<td class="table_02_2" width="88%"><input type="text" name="p_title" value="" size="100"/></td>
				</tr>
				<tr class="table_02_2">
					<td class="table_title">내용</td>
					<td class="table_02_2">
						<!-- 나모 액티브 스퀘어  시작 -->
		                <script language="javascript" src="/script/user_patch.js"></script>
		                <script language="javascript" for="Wec" event="OnInitCompleted()">document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";</script>
		                <script language="javascript">object_namoActivepatch('100%','485');</script>
		                <!-- 나모 액티브 스퀘어  종료 -->
                	</td>
				</tr>
			</table>
		</td>
	</tr>
</table>
</form>
<br/>
<!----------------- 등록, 취소 버튼 시작 ----------------->
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr> 
		<td align="center">
			<a href="javascript:register_tutorcommunity()"><img src="/images/admin/button/btn_register.gif" border="0"></a>&nbsp;
			<a href="javascript:history.back(-1);"><img src="/images/admin/button/btn_cancel.gif" border="0"></a>&nbsp;
		</td>
	</tr>
</table>
<!----------------- 등록, 취소 버튼 끝 ----------------->
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>