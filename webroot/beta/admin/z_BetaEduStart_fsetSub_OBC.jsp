<%
//**********************************************************
//  1. 제      목: OBC MasterForm - Sub Frameset for Control size of Tree-Frame
//  2. 프로그램명 : z_EduStart_fsetSub_OBC.jsp
//  3. 개      요: OBC MasterForm - Sub Frameset for Control size of Tree-Frame
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 07
//  7. 수      정:
//*********************************************************** 
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.beta.*" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }
	String	p_subj		= box.getSession("s_subj");
	String	p_year		= box.getSession("s_year");
	String	p_subjseq	= box.getSession("s_subjseq");
	
	BetaMasterFormData	da	=(BetaMasterFormData)request.getAttribute("BetaMasterFormData");
	String	v_contenttype	= da.getContenttype();

	String	v_base_url		= "/servlet/controller.beta.BetaEduStart?p_process=";
	String	v_url_tree		= v_base_url+"tree";
	String	v_url_bott		= v_base_url+"bott";
	String	v_url_gong		= "/servlet/controller.study.SubjGongStudyServlet?p_process=select&p_subj="
							+ p_subj+"&p_year="+p_year+"&p_subjseq="+p_subjseq+"&p_contenttype="+v_contenttype+"&p_ispreview=Y";
	
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<!-- Don't Modify!. CopyRights. Credu.Corp.  written by LeeSuMin -->
</head>

	<frameset rows="*" cols="190,1*" frameborder="YES" border="2" framespacing="0">
		<frame src="<%=v_url_tree%>"  	  name="etree" scrolling="AUTO" frameborder="YES">
		<frameset rows="1*,34" cols="*" frameborder="0" border="0" framespacing="0">
			<frame src="<%=v_url_gong%>"  name="ebody" scrolling="AUTO" frameborder="0">
			<frame src="<%=v_url_bott%>"  name="ebott" scrolling="NO" frameborder="0">
		</frameset>
	</frameset>
	<noframes><body bgcolor="#FFFFFF">
	</body></noframes>
</html>
