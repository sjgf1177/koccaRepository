<%
/**
 * file name : za_SuryoJeungFrame.jsp
 * date      : 2004/12/30
 * programmer: 
 * function  : 수료증 Frame
 */
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
    String v_subj = box.getString("p_subj");
    String v_subjseq = box.getString("p_subjseq");
    String v_year = box.getString("p_year");
    String v_userid =  box.getString("p_userid");

	//while(em.hasMoreElements()){
  	//	v_serno += (String)em.nextElement()+"|";
	//}
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
	<title>수료증출력</title>
</head>
    <frameset  rows="50,*" border=0 frameborder=0 framespacing=0>
      <frame name="header" src="/learn/user/study/zu_SuryoJeung_Header.jsp" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" >
      <frame name="bottom" src="/servlet/controller.study.MyClassServlet?p_process=SuryoJeungPage&p_subj=<%=v_subj%>&p_subjseq=<%=v_subjseq%>&p_year=<%=v_year%>&p_userid=<%=v_userid%>" marginwidth="0" marginheight="0" scrolling="auto" frameborder="0">
      <!--frame name="bottom" src="/learn/user/study/zu_SuryoJeung_P.jsp?p_subj=<%=v_subj%>&p_subjseq=<%=v_subjseq%>&p_year=<%=v_year%>" marginwidth="0" marginheight="0" scrolling="auto" frameborder="0"-->
    </frameset>
</html>