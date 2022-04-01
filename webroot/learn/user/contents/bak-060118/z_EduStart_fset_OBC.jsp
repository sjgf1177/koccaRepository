<%
//**********************************************************
//  1. 제      목: OBC MasterForm - main FrameSet 
//  2. 프로그램명: z_EduStart_fset_OBC.jsp
//  3. 개      요: OBC MasterForm - main FrameSet 
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 06
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

    String	p_process	= box.getString("p_process");    
    String  p_subj   	= box.getString("p_subj");
    String  p_year   	= box.getString("p_year");
    String  p_subjseq  	= box.getString("p_subjseq");
    String	c_gadmin	= box.getSession("gadmin");
    String  v_sitegubun = box.getString("p_sitegubun");
    String  v_ispreview = box.getString("p_ispreview"); //스콤 맛보기 여부
    String  v_review    = box.getString("p_review");    // 복습


    box.setSession("s_subj",	p_subj);
    box.setSession("s_year",	p_year);
    box.setSession("s_subjseq",	p_subjseq);

    //학습권한 얻기 (Y/P/N)
    String  v_eduauth = "";

    if (v_ispreview.equals("Y")) {          // 맛보기 일경우
        box.setSession("s_eduauth","P");    //교육간 권한 세션 설정(P:콘텐츠 조회만 가능, 입력불가)
        v_eduauth = "P";
    } else {
        if (v_review.equals("Y")) {
        box.setSession("s_eduauth","P");    //교육간 권한 세션 설정(P:콘텐츠 조회만 가능, 입력불가)
        v_eduauth = "P";
        } else {
            box.setSession("s_eduauth",v_eduauth);  //교육간 권한 세션 설정(P:콘텐츠 조회만 가능, 입력불가)
            v_eduauth = EduEtc1Bean.get_eduAuth(box);
        }
    }

    if(v_eduauth.equals("N")){
%>
	<html><head><title>Sorry</title></head><body></body></html>
	<script>
	alert("[<%=box.getSession("gadmin")%>][<%=box.getSession("s_subj")%>]죄송합니다. 학습권한이 없으므로 학습창을 닫습니다.\n\n문의사항은 운영자에게 연락하여 주십시오");
	window.close();
	</script>
<%    
    }

	MasterFormData	da	=(MasterFormData)request.getAttribute("MasterFormData");
	//String	v_ismfbranch 	= da.getIsmfbranch();
	//String 	v_subjnm		= da.getSubjnm();
	String	v_dir			= da.getDir();
	String	v_contenttype	= da.getContenttype();
	
	String	v_mftype		= da.getMftype();		//Frame Structure
	String	v_iscentered	= da.getIscentered();
	int		v_width			= da.getWidth();
	int		v_height		= da.getHeight();
	
	String	v_base_url		= "/servlet/controller.contents.EduStart?p_process=";
	String	v_url_up		= v_base_url+"fup";
	String	v_url_menu		= v_base_url+"fmenu";
	String	v_url_tree		= v_base_url+"tree";
	String	v_url_bott		= v_base_url+"bott";
	String	v_url_fsetsub	= v_base_url+"fsetsub";
	String	v_url_gong		= "/servlet/controller.study.SubjGongStudyServlet?p_process=select&p_subj="
							+ p_subj+"&p_year="+p_year+"&p_subjseq="+p_subjseq;
							
	
	String 	v_spaceUrl		= "";					//중앙정렬시 여백HTML 위치.
	
	if(v_iscentered.equals("Y"))	v_spaceUrl = EduEtc1Bean.make_eduURL(p_subj,"DOC")+"docs/menuimg/space.html";
	
%>
<html>
<head>
<title>Education Start</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<!-- Don't Modify!. CopyRights. Credu.Corp.  written by LeeSuMin -->
</head>
<script language="javascript">
	var f_min = false;
	
	function resizeWindow() {
		w = <%=v_width%>;
		h = <%=v_height%>;
		var v_w = '', v_h='';

		if (w==99999)	v_w = screen.availWidth;
		else if(w==0)	v_w = 1024;
		else			v_w = w;
		if (h==99999)	v_h = screen.availHeight;
		else if(h==0)	v_h = screen.availHeight;
		else			v_h = h;
		if (parseInt(navigator.appVersion)>3) {
		   top.moveTo(0,0);
		   top.resizeTo(v_w, v_h);
		}
	}

	function switchTreeFrameSize(){

		if (f_min){
			f_min = false;
			top.etree_fset.document.body.cols = "190,*";
		}else{
			f_min = true;
			top.etree_fset.document.body.cols = "60,*";
		}

	}
	resizeWindow();
</script>
<%	if (v_iscentered.equals("Y")){		%>
		<frameset rows="*,768,*" cols="*,1024,*" scrolling="NO" frameborder="NO" border="0" framespacing="0">
			<frame src="<%=v_spaceUrl%>" scrolling="NO">
			<frame src="<%=v_spaceUrl%>" scrolling="NO">
			<frame src="<%=v_spaceUrl%>" scrolling="NO">
			<frame src="<%=v_spaceUrl%>" scrolling="NO">
<%	}	%>

<%	if (v_mftype.equals("ND") || v_mftype.equals("NU")){	%>
			<frameset rows="0,0,50,1*,44" cols="*" border="1" FRAMESPACING="0" TOPMARGIN="0" LEFTMARGIN="0" MARGINHEIGHT="0" MARGINWIDTH="0">
				<frame src=""  name="ememo" scrolling="NO" frameborder="0" BORDER="0">
	  			<frame src=""  name="echk" scrolling="NO" frameborder="0" BORDER="0">
	  			<frame src="<%=v_url_up%>"  	name="etop" scrolling="NO" frameborder="0" BORDER="0">
	  			<frame src="<%=v_url_fsetsub%>" name="etree_fset" scrolling="AUTO"  FRAMEBORDER="0" BORDER="0">
	  			<frame src="<%=v_url_menu%>"  	name="emenu" scrolling="NO" frameborder="0">
			</frameset>

<%	}else {					%>	
			<frameset rows="0,0,50,44,1*" cols="*" frameborder="0" border="0" framespacing="0">
				<frame src=""  name="ememo" scrolling="NO">
	  			<frame src=""  name="echk" scrolling="NO" frameborder="0">
	  			<frame src="<%=v_url_up%>"  	name="etop" scrolling="NO" frameborder="0">
	  			<frame src="<%=v_url_menu%>"  	name="emenu" scrolling="NO" frameborder="0">
	  			<frameset rows="*" cols="150,1*" frameborder="0" border="0" framespacing="0">
		  			<frame src="<%=v_url_tree%>"  name="etree" scrolling="AUTO" frameborder="0">
		  			<frameset rows="1*,34" cols="*" frameborder="0" border="0" framespacing="0">
		  				<frame src="<%=v_url_gong%>"  name="ebody" scrolling="AUTO" frameborder="0">
		  				<frame src="<%=v_url_bott%>"  name="ebott" scrolling="NO" frameborder="0">
		  			</frameset>
		  		</frameset>
			</frameset>

<%	}	%>
<script language='javascript'>
alert('<%=v_url_tree%>');
</script>
<%	if (v_iscentered.equals("Y")){		%>
			<frame src="<%=v_spaceUrl%>" scrolling="NO">
			<frame src="<%=v_spaceUrl%>" scrolling="NO">
			<frame src="<%=v_spaceUrl%>" scrolling="NO">
			<frame src="<%=v_spaceUrl%>" scrolling="NO">
		</frameset>
<%	}	%>


<noframes>
<body bgcolor="#FFFFFF"><jsp:include page="/incmenu/edu_footer.jsp" flush="true">
	<jsp:param name="sUrl" value="<%=request.getServletPath().toString()%>"/>
</jsp:include></body>
</noframes>
</html>
