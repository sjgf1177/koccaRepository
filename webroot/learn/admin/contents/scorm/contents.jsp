<%
//**********************************************************
//  1. 제      목: 콘텐츠 화면
//  2. 프로그램명 : za_SCO_I.jsp
//  3. 개      요: 콘텐츠 화면
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 김기수 2004. 11. 24
//  7. 수      정:
//***********************************************************
%>

<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.scorm.*" %>
<%@ page import = "com.credu.contents.*" %>

<%  
   	RequestBox box = null;	
    box = (RequestBox)request.getAttribute("requestbox");
	
    if (box == null) {
    		box = RequestManager.getBox(request); 
		}
	String	oid	= box.getString("oid");		
%>

<SCRIPT LANGUAGE="JAVASCRIPT">
<!--
var API = null;

/******************************************************
  먼저 API를 호출한후 콘텐츠로 이동해야 함
******************************************************/
function initAPI(){	
	API = etop;		
	ebody.document.location.href="/learn/admin/contents/scorm/contents_view.jsp?oid=<%=oid%>";
}
//-->
</SCRIPT>

<frameset rows="0,*" cols="*" border="1" FRAMESPACING="0" TOPMARGIN="0" LEFTMARGIN="0" MARGINHEIGHT="0" MARGINWIDTH="0" ONLOAD="initAPI();">
	<frame src="DummyApi.jsp"  name="etop" id="etop" scrolling="NO" frameborder="0" BORDER="0">	
	<frame src="Empty.html"  name="ebody" scrolling="NO" frameborder="0" BORDER="0">
</frameset>