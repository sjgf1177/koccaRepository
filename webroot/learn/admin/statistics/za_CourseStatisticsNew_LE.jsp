<%
//**********************************************************
//  1. 제      목: 정규·상시 과정별 통계
//  2. 프로그램명 : za_CourseStatistics_E.jsp
//  3. 개      요: 정규·상시 과정별 통계
//  4. 환      경: JDK 1.7
//  5. 버      젼: 1.0
//  6. 작      성: 김기종 2016. 6. 10
//  7. 수      정:
//***********************************************************
%>
<%@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%	
	response.setContentType("application/vnd.ms-text");
	response.setHeader("Content-Disposition", "inline; filename=za_CourseStatisticsNew_LE.xls");
	response.setHeader("Content-Description", "JSP Generated Data");
%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.research.*" %>
<%@ page import = "com.credu.library.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%	
	RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String searchCondition = box.getStringDefault("searchCondition", "year");
    String searchGubun = box.getStringDefault("searchGubun", "ALL");
    String searchDateGubun = box.getStringDefault("searchDateGubun", "eduend");
    String searchStartDate = box.getString("searchStartDate");
    String searchEndDate = box.getString("searchEndDate");

    DataBox dbox = null;

    ArrayList<DataBox> selectList = (ArrayList<DataBox>)request.getAttribute("selectCourseStatisticLowDataList");
    
    int studentCnt_G0 = 0;
    int studentCnt_B0 = 0;
    int studentCnt_K0 = 0;
    int stoldCnt_G0 = 0;
    int stoldCnt_B0 = 0;
    int stoldCnt_K0 = 0;
    
    int studentCnt = 0;
    int stoldCnt = 0;

%>

<html>
<head>
<title>정규·상시 과정별 통계  Excel보기</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

				<table class="table_out" cellspacing="1" cellpadding="11" border="1">
	                <tr>
				        <td align="center" height="30" colspan="11"><font size="3"><b>정규 B2C 과정별 참여 LowData 통계</b></font></td>
				    </tr>
	                <tr>
				        <td align="center" height="30" colspan="11"><font size="3"><b>수강생의 학습참여 시작일 기준 : <%= searchStartDate %> ~ <%= searchEndDate %></b></font></td>
				    </tr>
	                <tr>
	                    <td class="table_title" width="5%"><b>번호</b></td>
	                    <td class="table_title" width="*"><b>회원아이디</b></td>
	                    <td class="table_title" width="6%"><b>회원성함</b></td>
	                    <td class="table_title" width="*"><b>과정명</b></td>
	                    <td class="table_title" width="8%"><b>과정코드</b></td>
	                    <td class="table_title" width="5%"><b>과정년도</b></td>
	                    <td class="table_title" width="10%"><b>과정차수</b></td>
	                    <td class="table_title" width="15%"><b>학습시작일</b></td>
	                    <td class="table_title" width="15%"><b>학습종료일</b></td>
	                    <td class="table_title" width="15%"><b>수료여부</b></td>
	                    <td class="table_title" width="5%"><b>진도율</b></td>
	                </tr>
                
                
<%
        if ( selectList != null && selectList.size() > 0 ) {
        	
            dbox = null;
            for(int i = 0; i < selectList.size(); i++) {
                dbox = (DataBox)selectList.get(i);
                
                String grad = dbox.getString("d_ISGRADUATED");
%>
                
					<tr>
						<td class="table_02_1" ><%= i+1 %></td>
						<td class="table_02_2" ><%=dbox.getString("d_USERID")%></td>
						<td class="table_02_2" ><%=dbox.getString("d_USERNAME")%></td>
						<td class="table_02_1" ><%=dbox.getString("d_SUBJNM")%></td>
						<td class="table_02_1" ><%=dbox.getString("d_SUBJ")%></td>
						<td class="table_02_1" ><%=dbox.getString("d_YEAR")%></td>
						<td class="table_02_1" ><%=dbox.getString("d_SUBJSEQ")%></td>
						<td class="table_02_1" ><%=dbox.getString("d_EDUSTART")%></td>
						<td class="table_02_1" ><%=dbox.getString("d_EDUEND")%></td>
						<td class="table_02_1" ><%=( "Y".equals(grad) ? grad + " (수료일:"+dbox.getString("d_DATE_GRAD")+")":grad )%> </td>
						<td class="table_02_1" ><%=dbox.getString("d_TSTEP")%></td>
	                </tr>
				<%-- 
				<tr>
	                <td class="table_02_1" ><%=dbox.getString("d_areanm")%></td>
	                <td class="table_02_1" ><%=dbox.getString("d_subj")%></td>
	                <td class="table_02_2" ><%=dbox.getString("d_subjnm")%></td>
	                <td class="table_02_1" ><%= studentCnt %></td>
	                <!-- <td class="table_02_1" ><%= stoldCnt %> </td> -->
	            </tr>
	             --%>
<%
                
				
            }
%>

<%            
        } else {
%>
                <tr>
                    <td class="table_02" colspan="17">조회된 자료가 없습니다.</td>
                </tr>
<%
        }
%>
            </table>

</body>
</html>