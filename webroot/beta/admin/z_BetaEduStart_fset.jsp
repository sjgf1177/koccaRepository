<%
/**
 * file name : za_MasterForm_U.jsp
 * date      : 2003/08/13
 * programmer: LeeSuMin
 * function  : 마스터폼 정보수정화면
 */
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

    String	p_process	= box.getString("p_process");
	
    String  p_subj   	= box.getString("p_subj");
    String  p_year   	= box.getString("p_year");
    String  p_subjseq  	= box.getString("p_subjseq");
    String	c_gadmin	= box.getSession("gadmin");
    
    box.setSession("s_subj",	p_subj);
    box.setSession("s_year",	p_year);
    box.setSession("s_subjseq",	p_subjseq);
    
    //학습권한 얻기 (Y/P/N)
    String	v_eduauth = EduEtc1Bean.get_eduAuth(box);
    
    box.setSession("s_eduauth",v_eduauth);	//교육간 권한 세션 설정(P:콘텐츠 조회만 가능, 입력불가)
    
    if(v_eduauth.equals("N")){
%>
	<html><head><title>Sorry</title></head><body></body></html>
	<script>
	alert("[<%=box.getSession("gadmin")%>][<%=box.getSession("s_subj")%>]죄송합니다. 학습권한이 없으므로 학습창을 닫습니다.\n\n문의사항은 운영자에게 연락하여 주십시오");
	window.close();
	</script>
<%    
    }
    
	BetaMasterFormData	da	=(BetaMasterFormData)request.getAttribute("BetaMasterFormData");
	//String	v_ismfbranch 	= da.getIsmfbranch();
	//String 	v_subjnm		= da.getSubjnm();
	String	v_dir			= da.getDir();
	String	v_contenttype	= da.getContenttype();
	String	v_mftype		= da.getMftype();		//Frame Structure
	String	v_iscentered	= da.getIscentered();
	int		v_width			= da.getWidth();
	int		v_height		= da.getHeight();
	
	String	v_base_url		= "/servlet/controller.beta.BetaEduStart?p_process=";
	String	v_url_up		= v_base_url+"fup";
	String	v_url_menu		= v_base_url+"fmenu";
	String	v_url_gong		= "/servlet/controller.study.SubjGongStudyServlet?p_process=select&p_subj="
							+ p_subj+"&p_year="+p_year+"&p_subjseq="+p_subjseq+"&p_ispreview=Y";
	
	String 	v_spaceUrl		= "";					//중앙정렬시 여백HTML 위치.
	
	if(v_iscentered.equals("Y"))	v_spaceUrl = EduEtc1Bean.make_eduURL(p_subj,"DOC")+"/docs/menuimg/space.html";
%>
<html>
<head>
<title>Education Start</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<!-- CopyRights. Credu.Corp.  written by LeeSuMin -->
</head>
<script language="javascript">
	function resizeWindow() {
		w = <%=v_width%>;
		h = <%=v_height%>;
		var v_w = '', v_h='';
		
		if (w==99999)	v_w = 'screen.availWidth';
		else			v_w = w;
		if (h==99999)	v_h = 'screen.availHeight';
		else			v_h = h;
		
		if (parseInt(navigator.appVersion)>3) {
		   top.moveTo(0,0);
		   top.resizeTo(v_w, v_h);
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

<%	if (v_mftype.equals("ND")){	%>
			<frameset rows="0,1,29,1*,45" cols="*" frameborder="NO" border="0" framespacing="0">
				<frame src=""  name="ememo" scrolling="NO" frameborder="NO">
		  		<frame src=""  name="echk" scrolling="NO" frameborder="NO">
		  		<frame src="<%=v_url_up%>"  name="etop" scrolling="NO" frameborder="NO">
		  		<frame src="<%=v_url_gong%>"  name="ebody" scrolling="AUTO" frameborder="NO">
		  		<frame src="<%=v_url_menu%>"  name="emenu" scrolling="NO" frameborder="NO">
			</frameset>
<%	}else if (v_mftype.equals("ED")){		%>	
			<frameset rows="0,0,0,1*,45" cols="*" frameborder="NO" border="0" framespacing="0">
				<frame src=""  name="ememo" scrolling="NO" frameborder="NO">
		  		<frame src=""  name="echk" scrolling="NO" frameborder="NO">
		  		<frame src="<%=v_url_up%>"  name="etop" scrolling="NO" frameborder="NO">
		  		<frame src="<%=v_url_gong%>"  name="ebody" scrolling="AUTO" frameborder="NO">
		  		<frame src="<%=v_url_menu%>"  name="emenu" scrolling="NO" frameborder="NO">
			</frameset>
<%	}else if (v_mftype.equals("NU")){		%>	
			<frameset rows="0,1,29,45,1*" cols="*" frameborder="NO" border="0" framespacing="0">
				<frame src=""  name="ememo" scrolling="NO" frameborder="NO">
		  		<frame src=""  name="echk" scrolling="NO" frameborder="NO">
		  		<frame src="<%=v_url_up%>"  name="etop" scrolling="NO" frameborder="NO">
		  		<frame src="<%=v_url_menu%>"  name="emenu" scrolling="NO" frameborder="NO">
		  		<frame src="<%=v_url_gong%>"  name="ebody" scrolling="AUTO" frameborder="NO">
			</frameset>			
<%	}else if (v_mftype.equals("WL")){		%>	
		<frameset rows="0,0,0,1*" cols="*" frameborder="NO" border="0" framespacing="0">
			<frame src=""  name="ememo" scrolling="NO" frameborder="NO">
	  		<frame src=""  name="echk" scrolling="NO" frameborder="NO">
	  		<frame src="<%=v_url_up%>"  name="etop" scrolling="NO" frameborder="NO">
	  		<frameset rows="*" cols="100,1*" frameborder="NO" border="0" framespacing="0">
	  			<frame src="<%=v_url_menu%>"  name="emenu" scrolling="NO" frameborder="NO">
	  			<frame src="<%=v_url_gong%>"  name="ebody" scrolling="AUTO" frameborder="NO">
	  		</frameset>
		</frameset>
<%	}else if (v_mftype.equals("NR")){		%>
		<frameset rows="0,0,29,1*" cols="*" frameborder="NO" border="0" framespacing="0">
	  		<frame src=""  name="ememo" scrolling="NO" frameborder="NO">
	  		<frame src=""  name="echk" scrolling="NO" frameborder="NO">
	  		<frame src="<%=v_url_up%>"  name="etop" scrolling="NO" frameborder="NO">
	  		<frameset rows="*" cols="1*,100" frameborder="NO" border="0" framespacing="0">
	  			<frame src="<%=v_url_gong%>"  name="ebody" scrolling="AUTO" frameborder="NO">
	  			<frame src="<%=v_url_menu%>"  name="emenu" scrolling="NO" frameborder="NO">
	  		</frameset>
		</frameset>
<%	}else if (v_mftype.equals("NL")){	%>
		<frameset rows="0,0,29,1*" cols="*" frameborder="NO" border="0" framespacing="0">
			<frame src=""  name="ememo" scrolling="NO" frameborder="NO">
	  		<frame src=""  name="echk" scrolling="NO" frameborder="NO">
	  		<frame src="<%=v_url_up%>"  name="etop" scrolling="NO" frameborder="NO">
	  		<frameset rows="*" cols="100,1*" frameborder="NO" border="0" framespacing="0">
	  			<frame src="<%=v_url_menu%>"  name="emenu" scrolling="NO" frameborder="NO">
	  			<frame src="<%=v_url_gong%>"  name="ebody" scrolling="AUTO" frameborder="NO">
	  		</frameset>
		</frameset>
<%	}else {		%>
			<frameset rows="0,1,50,1*,45" cols="*" frameborder="NO" border="0" framespacing="0">
				<frame src=""  name="ememo" scrolling="NO" frameborder="NO">
		  		<frame src=""  name="echk" scrolling="NO" frameborder="NO">
		  		<frame src="<%=v_url_up%>"  name="etop" scrolling="NO" frameborder="NO">
		  		<frame src="<%=v_url_gong%>"  name="ebody" scrolling="AUTO" frameborder="NO">
		  		<frame src="<%=v_url_menu%>"  name="emenu" scrolling="NO" frameborder="NO">
			</frameset>
<%}%>

<%	if (v_iscentered.equals("Y")){		%>
			<frame src="<%=v_spaceUrl%>" scrolling="NO">
			<frame src="<%=v_spaceUrl%>" scrolling="NO">
			<frame src="<%=v_spaceUrl%>" scrolling="NO">
			<frame src="<%=v_spaceUrl%>" scrolling="NO">
		</frameset>
<%	}	%>


<noframes>
<body bgcolor="#FFFFFF"></body>
</noframes>
</html>
