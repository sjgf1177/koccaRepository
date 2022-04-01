<%
//**********************************************************
//  1. 제      목: 이테스트
//  2. 프로그램명: za_ETestResultList_E.jsp
//  3. 개      요: 결과
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
    String v_grcode = box.getString("s_grcode");
    String v_gyear  = box.getString("s_gyear");        
    String v_etestsubj   = box.getString("s_etestsubj");
   
    //String v_param = "http://10.40.37.65:9999/learn/admin/etest/OZ_ETestResultList.jsp";
    
    String v_param = "s_grcode="+v_grcode+"&s_gyear="+v_gyear+"&s_etestsubj="+v_etestsubj;    
 
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
   <param name="connection.reportname" value="etest/result_1.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">     
   <param name="odi.odinames" value="result_1">
   <param name="odi.result_1.pcount" value="1">
   <param name="odi.result_1.args1" value="param=<%=v_param%>">
</OBJECT>
</body>
</html>



