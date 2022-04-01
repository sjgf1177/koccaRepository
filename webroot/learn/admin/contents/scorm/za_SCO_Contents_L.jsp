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
    	box = RequestManager.getBox(request);    }

        ConfigSet conf = new ConfigSet();
		String  s_scorealpath = conf.getProperty("dir.scorealpath");
        
		SCOData data;
		String	oid	= box.getString("oid");
		String p_content = box.getString("p_content");
		String v_scotitle="";
		String v_sdesc="";
		String v_starting="";
		String v_maxtimeallowed ="";
		String v_prerequisites ="";
		String v_timelimitaction ="";
		String v_datafromlms ="";
		int v_masteryscore = 0;
		String v_savepath = "";

     if (p_content.equals("2") ) {
		data = (SCOData)request.getAttribute("SCOData");
		v_scotitle = data.getScoTitle();
		v_sdesc = data.getSdesc();
		v_starting = data.getStarting();
		v_maxtimeallowed = data.getMaxtimeallowed();
		if ( v_maxtimeallowed.equals("") ){
			v_maxtimeallowed = "-";
		}
		v_prerequisites = data.getPrerequisites();
		if ( v_prerequisites.equals("") ){
			v_prerequisites = "-";
		}
		v_timelimitaction = data.getTimelimitaction();
		if ( v_timelimitaction.equals("") ){
			v_timelimitaction = "-";
		}
        v_datafromlms = data.getDatafromlms(); 
		if ( v_datafromlms.equals("") ){
			v_datafromlms = "-";
		}
        v_masteryscore = data.getMasteryscore();	
	 }	  
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

	<script language="javascript">
	function start(oid){	
			document.form1.p_process.value = "viewPage";			
			document.form1.p_oid.value = oid;
			document.form1.p_content.value = "1";
			document.form1.submit();
	} 
    </script>

    </head>
    <% if ( p_content.equals("1") ) { %>
    <body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" onload="start('<%=oid%>')" >
	<% } else { %>
	<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" >
	<% } %>
	<FORM name="form1" method="post" action="/servlet/controller.contents.SCOServlet">
          <input type="hidden" name="p_process"  value="">          
          <input type="hidden" name="p_oid"   value="">
		  <input type="hidden" name="p_content"   value="">
	
	</FORM>

<%	if(p_content.equals("2")) { %>

 <table width="95%" border="0" cellspacing="0" cellpadding="0" >
 <tr><td height=30></td></tr>
 <tr>
    <td align="center" valign="top">

		<TABLE width="600" border="0" cellpadding="0" cellspacing="1" bgcolor="b3b3b3" class=table_4>
		<tr height="25">
			<td width="150" bgcolor="#d5d5d5" align="center" class="table_title">콘텐츠제목</td>
			<td width="450" bgcolor="#FFFFFF">&nbsp;<%=v_scotitle%></td>
		</tr>
		<tr height="25">
			<td width="150" bgcolor="#d5d5d5" align="center" class="table_title">학습자료명</td>
			<td width="450" bgcolor="#FFFFFF">&nbsp;<%=v_sdesc%></td>
		</tr>		
		<tr height="25">
			<td bgcolor="#d5d5d5" align="center" class="table_title">초기파일명</td>
			<td bgcolor="#FFFFFF">&nbsp;<%=v_starting%></td>
		</tr>
		<tr height="25">
			<td bgcolor="#d5d5d5" align="center" class="table_title">통과점수</td>
			<td bgcolor="#FFFFFF">&nbsp;<%=v_masteryscore%>점</td>
		</tr>
		 <tr height='25'>  	<td bgcolor='#d5d5d5' align='center' class="table_title">선수학습</td>    
		 <td bgcolor='#FFFFFF'>&nbsp;<%=v_prerequisites%></td>   </tr>
		 
		 <tr height='25'>  	<td bgcolor='#d5d5d5' align='center' class="table_title">학습시간</td> 
		 <td bgcolor='#FFFFFF'>&nbsp;<%=v_maxtimeallowed%></td>   </tr> 
		 
		 <tr height='25'>  	<td bgcolor='#d5d5d5' align='center' class="table_title">학습시간초과이벤트</td> 
		 <td bgcolor='#FFFFFF'>&nbsp;<%=v_timelimitaction%></td>   </tr> 
		 <tr height='25'>  	<td bgcolor='#d5d5d5' align='center' class="table_title">개요</td>    
		 <td bgcolor='#FFFFFF'>&nbsp;<%=v_datafromlms%></td>   </tr> 
		</TABLE>
	</td>
 </tr>
 <tr>
 <td align="center" valign="top">
	 <TABLE width="600" border="0" cellpadding="0" cellspacing="1" bgcolor="b3b3b3" class=table_4>
	 <tr height="25"><td>
	 <iframe name="ObjectFrame" style="border:0;width:100%;height:500" src="/learn/admin/contents/scorm/view.jsp?v_savepath=<%=v_savepath%>" scrolling=yes  framespacing=20 frameborder=0 marginheight=0 topmargin=0 leftmargin=0></iframe>
	 </td></tr></table>
 </td>
 </tr>

 <% } %>
 </table>
</body>
</html>