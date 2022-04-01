<%
//**********************************************************
//  1. 제      목: OBC MasterForm Menu Frame
//  2. 프로그램명 : z_EduStart_fmenu_OBC.jsp
//  3. 개      요: OBC MasterForm Menu Frame
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

    String	s_userid	= box.getSession("userid");    
    String	s_name	= box.getSession("name");
    String  s_subj   	= box.getSession("s_subj");
    String  s_year   	= box.getSession("s_year");
    String  s_subjseq  	= box.getSession("s_subjseq");
	  String	s_gadmin	= box.getSession("gadmin");
    if(s_gadmin==null || s_gadmin.equals(""))	s_gadmin="A1";
    
	MasterFormData	da	=(MasterFormData)request.getAttribute("MasterFormData");
	String 	v_dir		= da.getDir();
	String	v_mftype 	= da.getMftype();
    int		v_imgw = 80, v_imgh = 44;				// 메뉴이미지 크기
    if (v_mftype.equals("NR")||v_mftype.equals("NL")) {
    	v_imgw = 100;		
		v_imgh = 30;
	}
		
	String	v_base_url		= "/servlet/controller.contents.EduStart?p_process=";
	String	v_imgURL	= EduEtc1Bean.make_eduURL(s_subj,"DOC")+"docs/menuimg";	//image Base URL
	
	ArrayList  list = (ArrayList)request.getAttribute("MfSubjList");
    MfSubjData x = null;
    

%>
<html>
	<head>
	<title>Education...</title>
	<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
	<!-- CopyRight. Credu.Corp.  written by LeeSuMin -->
	<script language=javascript>
		function out(){
			parent.out();
		}
		function uWin(url){
			window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=AUTO,resizable=yes,width=600,height=600").focus();
		}
		function upWin(url,w,h){
			window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,width="+w+",height="+h+" ").focus();
		}
<%	if (s_year.equals("PREV")){	%>
		function uWin(url){
			alert("맛보기에서는 지원되지 않습니다.");

		}
		function upWin(url,w,h){
			alert("맛보기에서는 지원되지 않습니다.");
		}
<%	} else	{					%>
		function uWin(url){

window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=AUTO,resizable=yes,width=600,height=600,left=0,top=0").focus();
		}
		function upWin(url,w,h){
			window.open(url,"","toolbar=no,statusbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=YES,resizable=yes,left=0,top=0,width="+w+",height="+h+" ").focus();
		}
<%	} 							%>
			
	</script>
	</head>

	<body background="<%=v_imgURL%>/docs/menuimg/mblank.gif" leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
	<table border="0" cellpadding="0" cellspacing="0" align="LEFT" valign="TOP" leftmargin="0" topmargin="0">

<%	if (v_mftype.equals("NU")||v_mftype.equals("ND")) {	%>
	<TR align=center>
<%	}	%>	
	<%  for (int i=0; i<20; i++) {
			if (v_mftype.equals("NR")||v_mftype.equals("NL")||v_mftype.equals("WL")) {	%><TR><%			}
			if (i < list.size()){
            	x  = (MfSubjData)list.get(i); %>
            	<TD width=<%=v_imgw%> height=<%=v_imgh%>><%=makeMenuLink(x.getMenu(),x,v_imgURL, box, v_imgw, v_imgh)%></TD>
	<%      }else{		%>
				<TD width=<%=v_imgw%> height=<%=v_imgh%>><img src="<%=v_imgURL%>/mblank.gif" border=0 width=<%=v_imgw%> height=<%=v_imgh%>></TD>
	<%      }			
			if (v_mftype.equals("NR")||v_mftype.equals("NL")||v_mftype.equals("WL")) {	%><TR><%			}
		}				
	if (v_mftype.equals("NU")||v_mftype.equals("ND")) {	%>	
	</TR>
<%	}	%>	
	</TABLE>
	<form name="next">
	<input type=hidden  name="now"  value="00">
	</form>
	</body>
</html>

<%!
private 	String	makeMenuLink(String p_menu, MfSubjData m, String p_imgURL, RequestBox box, int p_imgw, int p_imgh){
    String  s_subj   	= box.getSession("s_subj");
    String  s_year   	= box.getSession("s_year");
    String  s_subjseq  	= box.getSession("s_subjseq");
	String results = "", v_link="";
    results = "<img name='m"+p_menu+"' src='"+p_imgURL+"/m"+p_menu+".gif' alt='"+m.getMenunm()+"' border=0 width="+p_imgw+" height="+p_imgh+">";
	
	if(!m.getPgm().equals("N")){
       	v_link = m.getPgm();
       	if (v_link.lastIndexOf("p_process")==-1)	v_link+="?";
       	
       	if(m.getPgram1().equals("p_subj"))	
       		v_link = v_link + "&p_subj="+s_subj;
       	if(m.getPgram2().equals("p_year"))	v_link = v_link + "&p_year="+s_year;
    	if(m.getPgram3().equals("p_subjseq"))	v_link = v_link + "&p_subjseq="+s_subjseq;
    	if(m.getPgmtype().equals("html"))		v_link = p_imgURL+"/../"+v_link;
    	
    	//if(!m.getPgmtype().equals("blank")){
    	if (s_year.equals("2000")) {
    		results = "<a  onmouseover=\"document.m"+p_menu+".src='"+p_imgURL+"/m"+p_menu+"u.gif'\" " 
        		+ " onmouseout=\"document.m"+p_menu+".src='"+p_imgURL+"/m"+p_menu+".gif'\" "
         		+ " href=\"javascript:alert('사용할 수 없는 메뉴입니다.');\">"+results+"</a>";
    	} else {
    		results = "<a  onmouseover=\"document.m"+p_menu+".src='"+p_imgURL+"/m"+p_menu+"u.gif'\" " 
        		+ " onmouseout=\"document.m"+p_menu+".src='"+p_imgURL+"/m"+p_menu+".gif'\" "
         		+ " href=\"javascript:upWin('"+v_link+"',800,600);\">"+results+"</a>";
        }
        //}
    }
    return	results;
}
%>
