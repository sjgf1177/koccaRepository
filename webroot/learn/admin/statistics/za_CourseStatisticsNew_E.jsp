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
	response.setHeader("Content-Disposition", "inline; filename=za_CourseStatisticsNew_E.xls");
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

    ArrayList<DataBox> selectCourseStatisticList = (ArrayList<DataBox>)request.getAttribute("selectCourseStatisticList");
    
    int tot_studentCnt = 0;
    int tot_stoldCnt = 0;
    
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

				<table class="table_out" cellspacing="1" cellpadding="4" border="1">
	                <tr>
				        <td align="center" height="30" colspan="5"><font size="3"><b>정규 B2C 과정별 참여 통계</b></font></td>
				    </tr>
	                <tr>
				        <td align="center" height="30" colspan="5"><font size="3"><b>수강생의 학습참여 시작일 기준 : <%= searchStartDate %> ~ <%= searchEndDate %></b></font></td>
				    </tr>
	                <tr>
	                    <td class="table_title" width="15%"><b>분야</b></td>
	                    <td class="table_title" width="10%"><b>과정코드</b></td>
	                    <td class="table_title" width="*"><b>과정명</b></td>
	                    <td class="table_title" width="10%"><b>제작년도</b></td>
	                    <td class="table_title" width="10%"><b>참여</b></td>
	                    <!-- <td class="table_title" width="10%"><b>수료</b></td>  -->
	                </tr>
                
                
<%
        if ( selectCourseStatisticList != null && selectCourseStatisticList.size() > 0 ) {
            dbox = null;
            for(int i = 0; i < selectCourseStatisticList.size(); i++) {
                dbox = (DataBox)selectCourseStatisticList.get(i);
                if("K0".equals( dbox.getString("d_area") )){
                	if(searchGubun.equals("ALL")){
	                	studentCnt_K0 += (dbox.getInt("d_student_cnt_b2b") + dbox.getInt("d_student_cnt_b2c"));
	                	stoldCnt_K0 += (dbox.getInt("d_isgraduated_b2b") + dbox.getInt("d_isgraduated_b2c"));
                	}else{
	                	studentCnt_K0 += dbox.getInt("d_student_cnt_"+searchGubun.toLowerCase());
	                	stoldCnt_K0 += dbox.getInt("d_isgraduated_"+searchGubun.toLowerCase());
                	}
                }
                
                if("B0".equals( dbox.getString("d_area") )){
                	if(searchGubun.equals("ALL")){
	                	studentCnt_B0 += (dbox.getInt("d_student_cnt_b2b") + dbox.getInt("d_student_cnt_b2c"));
	                	stoldCnt_B0 += (dbox.getInt("d_isgraduated_b2b") + dbox.getInt("d_isgraduated_b2c"));
                	}else{
	                	studentCnt_B0 += dbox.getInt("d_student_cnt_"+searchGubun.toLowerCase());
	                	stoldCnt_B0 += dbox.getInt("d_isgraduated_"+searchGubun.toLowerCase());
                	}
                }
                
                if("G0".equals( dbox.getString("d_area") )){
                	if(searchGubun.equals("ALL")){
	                	studentCnt_G0 += (dbox.getInt("d_student_cnt_b2b") + dbox.getInt("d_student_cnt_b2c"));
	                	stoldCnt_G0 += (dbox.getInt("d_isgraduated_b2b") + dbox.getInt("d_isgraduated_b2c"));
                	}else{
	                	studentCnt_G0 += dbox.getInt("d_student_cnt_"+searchGubun.toLowerCase());
	                	stoldCnt_G0 += dbox.getInt("d_isgraduated_"+searchGubun.toLowerCase());
                	}
                }
                
                
                tot_studentCnt += (dbox.getInt("d_student_cnt_b2b") + dbox.getInt("d_student_cnt_b2c"));
                tot_stoldCnt += (dbox.getInt("d_isgraduated_b2b") + dbox.getInt("d_isgraduated_b2c"));
                
                
            }
%>
			<tr>
                <td class="table_02" colspan="4">총 합</td>
                <td class="table_02" ><%= tot_studentCnt %></td>
                <!-- <td class="table_02" ><%= tot_stoldCnt %> </td> -->
            </tr>
			<tr>
                <td class="table_02" colspan="2">방송영상</td>
                <td class="table_02" colspan="2">방송영상 합계</td>
                <td class="table_02" ><%= studentCnt_B0 %></td>
                <!-- <td class="table_02" ><%= stoldCnt_B0 %> </td> -->
            </tr>
			<tr>
                <td class="table_02" colspan="2">게임개발</td>
                <td class="table_02" colspan="2">게임개발 합계 </td>
                <td class="table_02" ><%= studentCnt_G0 %></td>
                <!-- <td class="table_02" ><%= stoldCnt_G0 %> </td> -->
            </tr>
			<tr>
                <td class="table_02" colspan="2">문화콘텐츠</td>
                <td class="table_02" colspan="2">문화콘텐츠 합계 </td>
                <td class="table_02" ><%= studentCnt_K0 %></td>
                <!-- <td class="table_02" ><%= stoldCnt_K0 %> </td> -->
            </tr>
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
            
<%
        if ( selectCourseStatisticList != null && selectCourseStatisticList.size() > 0 ) {
%>
			<table class="table_out" cellspacing="1" cellpadding="5" border="1">
				<colgroup>
					<col width="15%"/>
					<col width="10%"/>
					<col width="*"/>
					<col width="10%"/>
					<col width="10%"/>
				</colgroup>
<%
            dbox = null;
            for(int i = 0; i < selectCourseStatisticList.size(); i++) {
                dbox = (DataBox)selectCourseStatisticList.get(i);
              	if(searchGubun.equals("ALL")){
	               	studentCnt = (dbox.getInt("d_student_cnt_b2b") + dbox.getInt("d_student_cnt_b2c"));
	               	stoldCnt = (dbox.getInt("d_isgraduated_b2b") + dbox.getInt("d_isgraduated_b2c"));
              	}else{
	               	studentCnt = dbox.getInt("d_student_cnt_"+searchGubun.toLowerCase());
	               	stoldCnt = dbox.getInt("d_isgraduated_"+searchGubun.toLowerCase());
              	}
%>
				<tr>
	                <td class="table_02_1" ><%=dbox.getString("d_areanm")%></td>
	                <td class="table_02_1" ><%=dbox.getString("d_subj")%></td>
	                <td class="table_02_2" ><%=dbox.getString("d_subjnm")%></td>
	                <td class="table_02_1" ><%=dbox.getString("d_crdate")%></td>
	                <td class="table_02_1" ><%= studentCnt %></td>
	                <!-- <td class="table_02_1" ><%= stoldCnt %> </td> -->
	            </tr>
<%
                
            }
%>
            </table>
<%            
        }
%>






</body>
</html>