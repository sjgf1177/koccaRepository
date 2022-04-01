<%
//**********************************************************
//  1. 제      목: 성적우수자 리포트
//  2. 프로그램명: za_PremiumStudent_E
//  3. 개      요: 성적우수자 리포트
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
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  ss_grcode      = box.getString("s_grcode");           //교육그룹
    String  ss_gyear       = box.getString("s_gyear");            //년도
    String  ss_grseq       = box.getString("s_grseq");            //교육차수
    String  ss_grseqnm     = box.getString("s_grseqnm");          //교육차수명
    String  ss_upperclass  = box.getString("s_upperclass");       //과정대분류
    String  ss_middleclass = box.getString("s_middleclass");      //과정중분류
    String  ss_lowerclass  = box.getString("s_lowerclass");       //과정소분류

    String  ss_uclass      = box.getStringDefault("s_uclass","ALL");        //과정분류
            
    
    String  ss_subjcourse  = box.getString("s_subjcourse");       //과정&코스
    
    String  ss_subjseq     = box.getString("s_subjseq");          //과정 차수
    
    String  ss_company     = box.getString("s_company");          //회사
    String  ss_edustart    = box.getString("s_start");            //교육시작일
    String  ss_eduend      = box.getString("s_end");              //교육종료일
            
    String v_param = " and C.grcode='"+ss_grcode+"' and C.year='"+ss_gyear+"' ";    
    if(!ss_grseq.equals("ALL")) {
        v_param = v_param + " and C.grseq = '"+ss_grseq+"' ";    
    } 			 		 
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
   <param name="connection.reportname" value="reports/PremiumStudent.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="PremiumStudentL,PremiumStudentW">
   <param name="odi.PremiumStudentL.pcount" value="1">
   <param name="odi.PremiumStudentL.args1" value="<%=v_param%>">
   <param name="odi.PremiumStudentW.pcount" value="1">
   <param name="odi.PremiumStudentW.args1" value="<%=v_param%>">
</OBJECT>
</body>
</html>



