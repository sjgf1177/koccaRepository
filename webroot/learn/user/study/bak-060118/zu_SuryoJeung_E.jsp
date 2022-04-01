<%
//**********************************************************
//  1. 제      목: 수료증
//  2. 프로그램명: zu_SuryoJeung_E.jsp
//  3. 개      요: 수료증 엑셀
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

    String  v_userid   = box.getString("p_userid");
	String  v_subj     = box.getString("p_subj");
	String  v_year     = box.getString("p_year");
	String  v_subjseq  = box.getString("p_subjseq");

%>
<HTML>
<HEAD>
<TITLE></TITLE>
</HEAD>


<BODY leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<!--  뷰어를 다운로드 받는 부분 -->
<!-- 공통파일 시작-->
<%@ include file="/learn/library/oz1.jsp"%>
<!-- 공통파일 끝-->
   <param name="connection.reportname" value="study/SuryoJeung.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="SuryoJeung">
   <param name="odi.SuryoJeung.pcount" value="4">
   <param name="odi.SuryoJeung.args1" value="userid=<%=v_userid%>">
   <param name="odi.SuryoJeung.args2" value="subj=<%=v_subj%>">
   <param name="odi.SuryoJeung.args3" value="year=<%=v_year%>">
   <param name="odi.SuryoJeung.args4" value="subjseq=<%=v_subjseq%>">
</OBJECT>


</body>
</HTML>

