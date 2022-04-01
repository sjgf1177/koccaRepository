<%
//**********************************************************
//  1. 제      목: APIFRAME
//  2. 프로그램명: APIFRAME.jsp
//  3. 개      요: APIFRAME 
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 김기수 2004. 12. 06
//  7. 수      정:
//*********************************************************** 
%>

<%@ page contentType="text/html; charset=euc-kr"  %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.common.*" %>

<%
	 
	/*---------------------------------------
	  캐쉬 제거 및 한글처리
	 ---------------------------------------*/		
	response.setHeader("cache-control", "no-cache"); 
	response.setHeader("expires", "0"); 
	response.setHeader("pragma", "no-cache");
	
	request.setCharacterEncoding("euc-kr");

	RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
	
	/*---------------------------------------
	   변수 및 파라미터값
	 ---------------------------------------*/	
	String p_oid = request.getParameter("p_oid");
	String p_lesson = request.getParameter("p_lesson");
	String p_url = request.getParameter("url");

	String  p_userid    = box.getSession("userid");    
    String  s_name      = box.getSession("name");
    String  p_subj      = box.getSession("s_subj");
    String  p_year      = box.getSession("s_year");
    String  p_subjseq   = box.getSession("s_subjseq");

	String commit_value = "get";	
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>
<HEAD>
<TITLE></TITLE>
<SCRIPT LANGUAGE="JAVASCRIPT">

var API = null;

/******************************************************
  먼저 API를 호출한후 콘텐츠로 이동해야 함
******************************************************/
function initAPI(){	
	
	API = lmsApiFrame;
	lmsContFrame.document.location.href="<%=p_url%>";	
}
</SCRIPT>
</HEAD>

<frameset rows="*,150" cols="*" framespacing="0" frameborder="NO" border="0" onload="initAPI();" >
	<!-- Contents Frame: 처음은 빈페이지로 로딩 -->
	<frame src="z_Loading.html" name="lmsContFrame" scrolling="auto" noresize>
	
	<!-- API Frame: Api가 존재 -->
	<frame src="API.jsp?commit_value=<%=commit_value%>&userid=<%=p_userid%>&p_oid=<%=p_oid%>&p_lesson=<%=p_lesson%>&p_subj=<%=p_subj%>&p_year=<%=p_year%>&p_subjseq=<%=p_subjseq%>" name="lmsApiFrame" scrolling="no" noresize >
</frameset>

<noframes> 
<BODY>
</BODY>
</noframes> 
</HTML>
