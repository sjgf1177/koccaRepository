<%
//**********************************************************
//  1. 제      목: 수료증 
//  2. 프로그램명:  za_SuryoJeungFrame.jsp
//  3. 개      요: 수료증 Frame
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005. 7. 15 이연정
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
<%@ page import = "com.credu.scorm.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    request.setAttribute("box", box);
    Vector v_checks  = box.getVector("p_checks");
	Enumeration em   = v_checks.elements();
	String v_serno = "";

	while(em.hasMoreElements()){
  		v_serno += (String)em.nextElement()+"|";
	}
	
	
	String  ss_grcode          = box.getString("s_grcode");          //교육주관
    String  ss_gyear           = box.getString("s_gyear");          //교육주관
    String  ss_subjseq         = box.getString("s_subjseq");          //과정 차수
    String  ss_subjcourse         = box.getString("s_subjcourse");          //과정 차수
    String  ss_company         = box.getString("s_company");          //과정 차수
    String  ss_companynm       = box.getString("p_companynm");          //과정 차수

%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title></title>
</head>
    <frameset  rows="50,*" border=0 frameborder=0 framespacing=0>
        <frame name="header" src="/learn/admin/goyong/za_PopupHeader.jsp" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" >
        <frame name="bottom" src="/learn/admin/goyong/za_SuryoJeung_P.jsp?serno=<%= v_serno %>&p_companynm=<%=ss_companynm%>&s_company=<%= ss_company %>&s_grcode=<%= ss_grcode %>&s_gyear=<%= ss_gyear %>&s_subjseq=<%= ss_subjseq %>&s_subjcourse=<%= ss_subjcourse %>" marginwidth="0" marginheight="0" scrolling="auto" frameborder="0" >
    </frameset>
</html>