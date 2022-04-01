<%
//**********************************************************
//  1. 제      목: 리포트
//  2. 프로그램명: za_Report3_E.jsp
//  3. 개      요: 리포트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정은년 
//  7. 수      정:
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.complete.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");			
    if (box == null) box = RequestManager.getBox(request);
        
    String p_etestsubj = box.getString("p_etestsubj");
    String p_year      = box.getString("p_year");


    String v_param  = " etestsubj='"+p_etestsubj+"' and year='"+p_year+"' ";
    //String v_param2 = " a.etestsubj='"+p_etestsubj+"' and a.year='"+p_year+"' ";
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
   <param name="connection.reportname" value="etest/MemberScore.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">   
   <param name="odi.odinames" value="MemberScore">
   <param name="odi.MemberScore.pcount" value="1">
   <param name="odi.MemberScore.args1" value="param=<%=v_param%>">
</OBJECT>
</body>
</html>
