<%
//**********************************************************
//  1. 제      목: 과정별 클래스 조회
//  2. 프로그램명 : za_SubjClass_E.jsp
//  3. 개      요: 과정별 클래스 조회
//  4. 환      경: JDK 1.3
//  5. 버      젼: 0.1
//  6. 작      성: 정상진
//***********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.study.*" %>
<%//@ page language="java" contentType="application/vnd.ms-excel;name=My_Excel;charset=euc-kr" %>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%	
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_SubjClass_E.xls");
	response.setHeader("Content-Description", "JSP Generated Data");    
    RequestBox box = null;

    box = (RequestBox)request.getAttribute("requestbox");
	ArrayList  list = (ArrayList)request.getAttribute("ClassListExcel");

%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

      <!----------------- 단일클래스구성, 복수클래스 구성 끝 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out" border="1">
        <tr>
          <td colspan="8" class="table_top_line"></td>
        </tr>
        <!----------------- 복수클래스구성 시작 ----------------->
        <tr>
          <td class="table_title">과정</td>
          <td class="table_title">과정차수</td>
          <td class="table_title"><b>교육기간</b></td>
          <td class="table_title"><b>교육생수</b></td>
          <td class="table_title"><b>클래스수</b></td>
          <td class="table_title"><b>미지정 교육생수</b></td>
          <td class="table_title"><b>가용 강사수</b></td>
          <td class="table_title"><b>클래스 수</b></td>
        </tr>

<%  

    ClassListData data = null;
if(list.size() != 0){		//내용이 있다면

    for (int i=0; i<list.size(); i++) {

        data  = (ClassListData)list.get(i);

        //if (data.getStudentcnt() > 0) { %>
        <tr>
          <td class="table_02_2"><%=data.getSubjnm()%></td>
          <td class="table_02_1"><%=StringManager.cutZero(data.getSubjseqgr())%></td>
          <td class="table_02_1"><%=FormatDate.getFormatDate(data.getEdustart(),"yyyy/MM/dd")%>~<%=FormatDate.getFormatDate(data.getEduend(),"yyyy/MM/dd")%></td>
          <td class="table_02_1"><%=data.getStudentcnt()%></td>
          <td class="table_02_1"><%=data.getClasscnt()%></td>
          <td class="table_02_1"><%=data.getNoassignstudentcnt()%></td>
          <td class="table_02_1"><%=data.getAvailabletutorcnt()%></td>
          <td class="table_02_1"><%=data.getClasscnt()%></td>
        </tr>
<%      }
 }else{  
 %>
        <tr>
          <td colspan="8" class="table_top_line">검색된 내용이 없습니다.</td>
        </tr>
<%
 }
%>

      <!----------------- 복수클래스구성 끝 ----------------->
      </table>

</body>
</html>