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

	ConfigSet conf = new ConfigSet();
	String  s_scorealpath = conf.getProperty("dir.scorealpath");
	
    if (box == null) {
    	box = RequestManager.getBox(request);    }

		String	oid	= box.getString("oid");		
		String v_starting="";		
		String v_savepath = "";
        
		SCOBean bean = new SCOBean();   
		v_starting = bean.SelectStarting(oid);    	 	 
	 
		v_savepath= s_scorealpath + v_starting ;	
   %>

  <html>
    <head>
    <title></title>
	<META HTTP-EQUIV="Pragma" CONTENT="No-Cache">
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link rel="stylesheet" href="/css/admin_style.css" type="text/css">
    <script language = "javascript" src = "/script/cresys_lib.js"></script>
    <script language="JavaScript" src="/js/default.js"></script>

	
    </head>
    
	<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" >
	
	<FORM name="form1" method="post" action="/servlet/controller.contents.SCOServlet">
          <input type="hidden" name="p_process"  value="">          
          <input type="hidden" name="p_oid"   value="">
		  <input type="hidden" name="p_content"   value="">	
	</FORM>	

 <table width="95%" border="0" cellspacing="0" cellpadding="0" >
 <tr>
 <td align="center" valign="top">
	 <TABLE width="600" border="0" cellpadding="0" cellspacing="1" bgcolor="b3b3b3" class=table_4>
	 <tr height="25"><td>
	 <iframe name="ObjectFrame" style="border:0;width:100%;height:500" src="/learn/admin/contents/scorm/view.jsp?v_savepath=<%=v_savepath%>" scrolling=yes  framespacing=20 frameborder=0 marginheight=0 topmargin=0 leftmargin=0></iframe>
	 </td></tr></table>
 </td>
 </tr>
 </table>

</body>
</html>