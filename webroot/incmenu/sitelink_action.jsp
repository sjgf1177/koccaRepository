<%-- 
	제  목 : 관련 사이트로 링크
	파일명 : sitelink_action.jsp
	작성일 : 2004.12.01
	작성자 : 이성하[cospm@hanafos.com]
	설  명 : 
	수  정 : 세션 체크하여 처리 할것. 수정할 것
--%> 

<%@ page language="java" contentType="text/html; charset=euc-kr" %>

<%@ page import="com.daumsoft.util.SBUtil" %>
<%@ page import="com.daumsoft.sitelink.SiteLinkManager" %>
<%@ page import="com.daumsoft.sitelink.SiteLinkEntity" %>

<%
	int iSL_NO = Integer.parseInt(request.getParameter("slNo"));

	SiteLinkEntity sle = new SiteLinkEntity();
	SiteLinkManager slMan = SiteLinkManager.getInstance();
	
	sle = slMan.selSiteLinkInfo(iSL_NO);

	String sSiteUrl = sle.getM_sSiteUrl();	// 사이트 기본 Url
	String sSiteLinkPage = "";				// 사이트 링크페이지
	String sSsnEmpNo = "";					// 사번
	String sSsnName = "";					// 성명
	String sSsnRrn = "";					// 주민등록번호
	String sSsnId = "";						// 아이디
	String[] sPvs = null;					// Param&Value
%>

<html>
<body>
<form name="SL_FORM" >

<%
	// 세션 조건 체크 할것
	if (iSL_NO == 0) {
		sSiteLinkPage = sle.getM_sSiteLinkPage();
		sSiteUrl = sSiteUrl + sSiteLinkPage;

		sSsnEmpNo = sle.getM_sSsnEmpNo();
		sSsnName = sle.getM_sSsnName();
		sSsnRrn = sle.getM_sSsnRrn();
		sSsnId = sle.getM_sSsnId();
	    sPvs = sle.getM_sParamValues(sle.getM_sParamValue(), "|");	
%>

<input type="hidden" name="<%=sSsnEmpNo%>" value="세션">
<input type="hidden" name="<%=sSsnName%>" value="세션">
<input type="hidden" name="<%=sSsnRrn%>" value="세션">
<input type="hidden" name="<%=sSsnId%>" value="세션">

<%
		if (sPvs != null) {
			for (int i=0; i < sPvs.length; i++) {
				String[] sPv = SBUtil.tokenizingByDelimeter(sPvs[i], "_");
%>

<input type="hidden" name="<%=sPv[0]%>" value="<%=sPv[1]%>">

<%
			}
		}
	}
%>

</form>
</body>
</html>


<script>    
		document.SL_FORM.action = '<%=sSiteUrl%>';
		document.SL_FORM.submit();
</script>
