<%
//**********************************************************
//  1. 제      목: 콘텐츠설문
//  2. 프로그램명: ContentResult_E.jsp
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
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");			
    if (box == null) box = RequestManager.getBox(request);
    String v_grcode = box.getString("p_grcode");
    String v_gyear  = box.getString("p_gyear");        
    String v_subj   = box.getString("p_subj");
    String v_subjseq= box.getString("p_subjseq"); 

    //String  v_yeslearn = conf.getProperty("yeslearn.url.value");
    
    String v_param = "";
    v_param =  "http://218.144.185.176"+"/learn/admin/research/OZ_SulmunContentsResult.jsp?p_grcode="+v_grcode+"&p_gyear="+v_gyear+"&p_subj="+v_subj+"&p_subjseq="+v_subjseq;    
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
   <param name="connection.reportname" value="reaserch/sulmuncontents.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">     
   <param name="odi.odinames" value="sulmuncontents">
   <param name="odi.sulmuncontents.pcount" value="1">
   <param name="odi.sulmuncontents.args1" value="sulmunl_url=<%=v_param%>">
</OBJECT>
</body>
</html>



