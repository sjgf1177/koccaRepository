<%
//**********************************************************
//  1. 제      목: 강사운영현황
//  2. 프로그램명: za_TutorStatus_L.jsp
//  3. 개      요: 강사운영현황
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 노희성 2004. 12. 17
//  7. 수      정: 
//**********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.budget.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>

<%
	response.setHeader("Content-Disposition", "inline; filename=TutorInfo.xls");   
	response.setHeader("Content-Description", "JSP Generated Data");
	
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String  v_tutorgubun   = box.getStringDefault("s_tutorgubun","ALL");//강사구분(I:사내강사,O:사외강사,C:교육기관)
    String  v_srchsubjnm = box.getStringDefault("p_srchsubjnm","");		//교과명
    
    String  v_subjclass   = box.getStringDefault("p_subjclass","ALL");    //강의분야
    String  v_tutorname   = box.getStringDefault("p_tutorname","");    //강사명


%>

<html>
<head>
<title>강사운영현황</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<table border="0" cellspacing="0" cellpadding="0" >
  <tr>
    <td align="center" valign="top">
     
      <!----------------- 과정 관리 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out" border=1>
        <tr> 
          <td class="table_title"><b>NO</b></td>
          <td class="table_title"><b>강사구분</b></td>
          <td class="table_title"><b>강사명/교육기관명</b></td>
          <td class="table_title"><b>현직</b></td>
          <td class="table_title"><b>전화</b></td>
          <td class="table_title"><b>이동전화</b></td>
          <td class="table_title"><b>강의분야</b></td>
          <td class="table_title"><b>과정</b></td>
          <td class="table_title"><b>주관부서</b></td>
          <td class="table_title" colspan=2><b>과정기간</b></td>
          <td class="table_title"><b>교과목</b></td>
          <td class="table_title"><b>평점</b></td>
          <td class="table_title"><b>차수당강의시간</b></td>
          <td class="table_title"><b>시간당강사료</b></td>
          <td class="table_title"><b>입력자 사번</b></td>
          <td class="table_title"><b>입력자 성명</b></td>
          <td class="table_title"><b>입력시간</b></td>
        </tr>
        <%  ArrayList  list = (ArrayList)request.getAttribute("tutorStatusList");
		int listsize = list.size();
		double execpercent = 0.00d;
	    for (int i=0; i<list.size(); i++) {
	        DataBox dbox = (DataBox)list.get(i); 
			execpercent = dbox.getDouble("d_execpercent");
			%>
        <tr> 
          <td class="table_01"><%=listsize-i%></td>
          <td class="table_02_1"><%=dbox.getString("d_tutorgubunnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_name")%></td>
          <td class="table_02_1"><%=dbox.getString("d_compnm")%></td>
          <td class="table_02_1">&nbsp;<%=dbox.getString("d_phone")%></td>
          <td class="table_02_1">&nbsp;<%=dbox.getString("d_handphone")%></td>
          <td class="table_02_1"><%=dbox.getString("d_subjclassnm")%></td>
          <td class="table_02_2"><%=dbox.getString("d_subjnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_inusercompnm")%></td>
          <td class="table_02_1"><%=dbox.getString("d_edustart")%></td>
          <td class="table_02_1"><%=dbox.getString("d_eduend")%></td>
          <td class="table_02_2"><%=dbox.getString("d_lessonnm")%></td>
          <td class="table_02_1"><%=dbox.getDouble("d_grade")%></td>
          <td class="table_02_1"><%=dbox.getInt("d_eduhr")%></td>
          <td class="table_02_1"><%=dbox.getInt("d_price")%></td>
          <td class="table_02_1">&nbsp;<%=dbox.getString("d_inuserid")%></td>
          <td class="table_02_1"><%=dbox.getString("d_inusername")%></td>
          <td class="table_02_1">&nbsp;<%=FormatDate.getFormatDate(dbox.getString("d_indate"),"yyyy/MM/dd hh:mm:ss")%></td>
        </tr>
        <%  } %>
      </table>
      <!----------------- 과정 관리 끝 ----------------->
      <br>
    </td>
  </tr>
</table>

</body>
</html>
