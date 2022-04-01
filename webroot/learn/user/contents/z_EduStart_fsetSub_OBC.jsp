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
	
	String	p_ispreview	= box.getString("p_ispreview"); // 맛보기
	String  p_review    = box.getString("p_review");    // 복습
	
	MasterFormData	da	=(MasterFormData)request.getAttribute("MasterFormData");
	String	v_contenttype	= da.getContenttype();

	String	v_base_url		= "/servlet/controller.contents.EduStart?p_process=";
	String	v_url_tree		= v_base_url+"tree&p_ispreview="+p_ispreview+"&p_review="+p_review;
	String	v_url_bott		= v_base_url+"bott&p_ispreview="+p_ispreview;

	String	v_url_gong		= v_base_url+"fmain&p_ispreview="+p_ispreview
								+ "&p_subj=" + p_subj+"&p_year="+p_year+"&p_subjseq="+p_subjseq;
	//String	v_url_gong		= "/servlet/controller.study.SubjGongStudyServlet?p_process=select&p_subj="
	//						+ p_subj+"&p_year="+p_year+"&p_subjseq="+p_subjseq+"&p_contenttype="+v_contenttype+"&p_ispreview="+p_ispreview;

%>

<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<frameset cols="200,*" border="0" frameborder="NO" framespacing="0" bordercolor="#6699CC">
	<frame src="<%=v_url_tree%>" name="etree" scrolling="AUTO">
	<frameset rows="*,40" frameborder="NO" framespacing="0" border="0" >
	  <frame src="<%=v_url_gong%>" name="ebody" scrolling="AUTO" allowfullscreen="true">
	  <frame src="<%=v_url_bott%>" name="ebott" scrolling="NO">
	</frameset>
</frameset>
<noframes><body bgcolor="#FFFFFF">
</body></noframes>
</html>
