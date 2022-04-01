<%
//**********************************************************
//  1. 제      목: SCORM MasterForm - Sub Frameset for Control size of Tree-Frame
//  2. 프로그램명 : z_EduStart_fsetSub_SCORM.jsp
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
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
    	box = RequestManager.getBox(request);
    }
	String	p_subj		= box.getSession("s_subj");
	String	p_year		= box.getSession("s_year");
	String	p_subjseq	= box.getSession("s_subjseq");
	String  v_sitegubun = box.getString("p_sitegubun");
	String  v_ispreview = box.getString("p_ispreview"); //스콤 맛보기 여부
	

	MasterFormData	da	=(MasterFormData)request.getAttribute("MasterFormData");
	String	v_contenttype	= da.getContenttype();
	
	String	v_base_url		= "/servlet/controller.contents.EduStart?p_ispreview="+v_ispreview+"&p_process=";
	String	v_url_tree		= v_base_url+"tree";
	String	v_url_bott		= v_base_url+"bott";
	String	v_url_gong		= "/servlet/controller.study.SubjGongStudyServlet?p_process=select&p_subj="
							+ p_subj+"&p_year="+p_year+"&p_subjseq="+p_subjseq+"&p_contenttype="+v_contenttype+"&p_sitegubun="+v_sitegubun+"&p_ispreview="+v_ispreview;
							
	System.out.println("fsetSub_SCORM v_url_gong = "+v_url_gong);
	
	
%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<!-- Don't Modify!. CopyRights. Credu.Corp.  written by LeeSuMin -->
</head>

	<frameset rows="*" cols="190,1*" frameborder="YES" border="2" framespacing="0">
		<frame src="<%=v_url_tree%>"  	  name="etree" scrolling="AUTO" frameborder="YES">
		<frame src="<%=v_url_gong%>"  name="ebody" scrolling="AUTO" frameborder="0" allowfullscreen="true">
	</frameset>
	<noframes><body bgcolor="#FFFFFF">
	</body></noframes>

</html>
