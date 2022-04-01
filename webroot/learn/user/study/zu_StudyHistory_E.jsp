<%
//**********************************************************
//  1. 제      목: 개인교육이력
//  2. 프로그램명: zu_StudyHistory_E.jsp
//  3. 개      요: 개인교육이력 엑셀
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) box = RequestManager.getBox(request);

    String  s_userid = box.getSession("userid");
	String  s_resno  = box.getSession("resno");

%>
<HTML>
<HEAD>
<TITLE></TITLE>
</HEAD>


<BODY leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<!--  뷰어를 다운로드 받는 부분 -->
<!-- 공통파일 시작-->
<%@ include file="/learn/library/oz.jsp"%>
<!-- 공통파일 끝-->
   <param name="connection.reportname" value="study/StudyHistory.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="StudyHistory">
   <param name="odi.StudyHistory.pcount" value="2">
   <param name="odi.StudyHistory.args1" value="userid=<%=s_userid%>">
   <param name="odi.StudyHistory.args2" value="resno=<%=s_resno%>">
</OBJECT>


</body>
</HTML>

