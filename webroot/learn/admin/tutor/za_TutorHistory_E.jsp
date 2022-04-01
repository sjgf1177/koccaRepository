<%
//**********************************************************
//  1. 제      목: TUTOR HISTORY EXCEL
//  2. 프로그램명: za_TutorHistory_E.jsp
//  3. 개      요: 강사 이력 엑셀
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 
//  7. 수      정:
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.tutor.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel; name=My_Excel; Charset=KSC5601" %> 
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%	response.setHeader("Content-Disposition", "inline; filename=TutorHistory.xls");   
	response.setHeader("Content-Description", "JSP Generated Data");
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String v_process        = box.getString("p_process");    
    String v_userid         = "";   
    String v_name           = "";
    String v_comp           = "";
    String v_dept           = "";
    String v_handphone      = "";
    String v_email          = "";
    String v_isgubun        = "";
    String v_subj           = "";
    String v_subjnm         = "";
    String v_isgubun_value  = "";
    String v_sdesc          = "";
    String v_lectlevel      = "";
    int    v_lecture        =  0;    
    int     i               =  0;
    //DEFINED class&variable END

    ArrayList list = (ArrayList)request.getAttribute("tutorHistoryList");
%>
<html>
<head>
<title>강사 이력 Excel보기</title>
</head>

<body>

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
  <tr>
    <td align="center" valign="top"> 
        <!----------------- 강사조회 시작 ----------------->
        <table cellspacing="1" cellpadding="0" border="1">
          <tr> 
            <td align="center"><b>No</b></td>
            <td align="center"><b>강사명</b></td>
            <td align="center"><b>주민등록번호</b></td>
            <td align="center"><b>회사</b></td>
            <td align="center"><b>부서</b></td>
            <td align="center"><b>연락처</b></td>
            <td align="center"><b>E-mail</b></td>
            <td align="center"><b>과정명</b></td>
            <td align="center"><b>강좌</b></td>
            <td align="center"><b>구분</b></td>
            <td align="center"><b>등급</b></td>
          </tr>
        <%                                                                                                                                             
            for(i = 0; i < list.size(); i++) {
                TutorData data  = (TutorData)list.get(i);        
                v_userid        = data.getUserid();       
                v_name          = data.getName();         
                v_comp          = data.getComp();         
                v_dept          = data.getDept();         
                v_handphone     = data.getHandphone();    
                v_email         = data.getEmail();        
                v_isgubun       = data.getIsgubun();      
                v_subj          = data.getSubj();         
                v_subjnm        = data.getSubjnm();       
                v_sdesc         = data.getSdesc();        
                v_lectlevel     = data.getLectlevel();    
                v_lecture       = data.getLecture();                                   
                if( v_handphone.length() <= 3 )     {	v_handphone = "";       }
                if(v_isgubun.equals("1")){
                    v_isgubun_value = "사내강사";
                }else if(v_isgubun.equals("2")){
                    v_isgubun_value = "사외강사";
                }                              
        %>                    
                    
          <tr> 
            <td><%= list.size() - i %></td>
            <td>
                <a href = "javascript:tutor_select('<%=v_userid%>')"><%=v_name%></a>
            </td>
            <td><%=v_userid%></td>
            <td><%=v_comp%></td>
            <td><%=v_dept%></td>
            <td><%=v_handphone%></td>
            <td><%=v_email%></td>
            <td><%=v_subjnm%></td>
            <td><%=v_sdesc%></td>
            <td><%=v_isgubun_value%></td>
            <td><%=v_lectlevel%></td>
          </tr>
          <%}%>          
        </table>
        <!----------------- 강사조회 끝 ----------------->
      </td>
  </tr>
  <tr><td><%@ include file = "/learn/library/getJspName.jsp" %></td></tr>     
</table>
</body>
</html>
