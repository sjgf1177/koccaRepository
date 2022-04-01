<%
//**********************************************************
//  1. 제      목: 리포트
//  2. 프로그램명: za_SulmunFiveScaleSubj_E.jsp
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
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");			
    
    String v_grcode = box.getString("s_grcode");
    String v_year   = box.getString("s_gyear");
    String v_subjseqgr = box.getString("s_grseq"); 
    String v_param = "s_grcode="+v_grcode+"&s_gyear="+v_year+"&s_grseq="+v_subjseqgr;		 
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
   <param name="connection.reportname" value="reports/SulmunFiveScaleSubj.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="SulmunFiveScale">
   <param name="odi.SulmunFiveScale.pcount" value="1">
   <param name="odi.SulmunFiveScale.args1" value="sulmun_url=http://218.144.185.176/learn/admin/course/za_SulmunFiveScale.jsp?<%=v_param%>">
</OBJECT>
</body>
</html>