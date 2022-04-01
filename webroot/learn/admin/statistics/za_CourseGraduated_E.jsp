<%
//**********************************************************
//  1. 제      목: 정규(상시) 수료기준+만족도 통계 엑셀다운로드
//  2. 프로그램명 : za_CourseGraduated_E.jsp
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
	response.setHeader("Content-Disposition", "inline; filename=za_CourseGraduated_E.xls");
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

    ArrayList<DataBox> selectList = (ArrayList<DataBox>)request.getAttribute("selectCourseGraduatedList");

    int sulCnt = 0;
    double distcodeAvg = 0;
%>
<html>
<head>
<title>정규·상시 과정별 만족도 통계</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
				
				
				<table class="table_out" cellspacing="1" cellpadding="5" border="1">
	                <tr>
				        <td align="center" height="30" colspan="7"><font size="3"><b>정규 B2C 과정별 수료·만족도 통계</b></font></td>
				    </tr>
	                <tr>
				        <td align="center" height="30" colspan="7"><font size="3"><b>수강생의 과정 수료일 기준 : <%= searchStartDate %> ~ <%= searchEndDate %></b></font></td>
				    </tr>
	                <tr>
	                    <td class="table_title" width="10%"><b>분야</b></td>
	                    <td class="table_title" width="10%"><b>과정코드</b></td>
	                    <td class="table_title" width="*"><b>과정명</b></td>
	                    <td class="table_title" width="10%"><b>제작년도</b></td>
	                    <td class="table_title" width="10%"><b>수료</b></td>
	                    <td class="table_title" width="10%"><b>만족도참여자</b></td>
	                    <td class="table_title" width="10%"><b>만족도평균</b></td>
	                </tr>
	                
<%!
		public class AreaInfo { //분야별 정보 클래스
			public int gradCnt = 0;
	
			public int sulSum = 0;
			public int sulCnt = 0;
			public int sulKind = 0;
		}
%>	                
<%
        if ( selectList != null && selectList.size() > 0 ) {
        	
            dbox = null;
            
            Map<String, AreaInfo> mapArea = new HashMap<String, AreaInfo>();
            mapArea.put( "K0", new AreaInfo() );
            mapArea.put( "B0", new AreaInfo() );
            mapArea.put( "G0", new AreaInfo() );

            int tot_gradCnt = 0;
           	int tot_sulCnt = 0;
           	int tot_sulKind = 0;
           	int tot_sulSum = 0;

	           	for(int i = 0; i < selectList.size(); i++) {
	                dbox = (DataBox)selectList.get(i);
	                // 분야 구분
	                String area = dbox.getString("d_area");
	                AreaInfo dArea = (AreaInfo)mapArea.get(area);
	                dArea.gradCnt += dbox.getInt("d_isgraduated_b2c");
	                dArea.sulCnt += dbox.getInt("d_sul_cnt");
	                dArea.sulSum += dbox.getInt("d_distcode_avg");
	                if ( dbox.getInt("d_sul_cnt") > 0 ) dArea.sulKind ++;
	                // 전체
	                tot_gradCnt += dbox.getInt("d_isgraduated_b2c");
	                tot_sulCnt += dbox.getInt("d_sul_cnt");
	                tot_sulSum += dbox.getInt("d_distcode_avg");
	                if ( dbox.getInt("d_sul_cnt") > 0 ) tot_sulKind++;
	            }
%>
				<tr>
	                <td class="table_02" colspan="4">총 합</td>
	                <td class="table_02" ><%= tot_gradCnt %></td>
	                <td class="table_02" ><%= tot_sulCnt %></td>
	                <td class="table_02" ><%= (tot_sulKind!=0?tot_sulSum/tot_sulKind:0) %></td>
	            </tr>
	            <% AreaInfo dArea = new AreaInfo(); %>
				<tr>
					<% dArea = (AreaInfo)mapArea.get("B0"); %>
	                <td class="table_02" colspan="2">방송영상</td>
	                <td class="table_02" colspan="2">방송영상 합계</td>
	                <td class="table_02" ><%= dArea.gradCnt %></td>
	                <td class="table_02" ><%= dArea.sulCnt %></td>
	                <td class="table_02" ><%= (dArea.sulKind!=0?dArea.sulSum/dArea.sulKind:0) %></td>
	                <%-- <td class="table_02" ><%= sulSum_B0 %></td> --%>
	            </tr>
				<tr>
					<% dArea = (AreaInfo)mapArea.get("G0"); %>
	                <td class="table_02" colspan="2">게임개발</td>
	                <td class="table_02" colspan="2">게임개발 합계</td>
	                <td class="table_02" ><%= dArea.gradCnt %></td>
	                <td class="table_02" ><%= dArea.sulCnt %></td>
	                <td class="table_02" ><%= (dArea.sulKind!=0?dArea.sulSum/dArea.sulKind:0) %></td>
	            </tr>
				<tr>
					<% dArea = (AreaInfo)mapArea.get("K0"); %>
	                <td class="table_02" colspan="2">문화콘텐츠</td>
	                <td class="table_02" colspan="2">문화콘텐츠 합계</td>
	                <td class="table_02" ><%= dArea.gradCnt %></td>
	                <td class="table_02" ><%= dArea.sulCnt %></td>
	                <td class="table_02" ><%= (dArea.sulKind!=0?dArea.sulSum/dArea.sulKind:0) %></td>
	            </tr>
            
<%
	            dbox = null;
	            for(int i = 0; i < selectList.size(); i++) {
	                dbox = (DataBox)selectList.get(i);
%>
				<tr>
	                <td class="table_02_1" ><%=dbox.getString("d_areanm")%></td>
	                <td class="table_02_1" ><%=dbox.getString("d_subj")%></td>
	                <td class="table_02_2" ><%=dbox.getString("d_subjnm")%></td>
	                <td class="table_02_1" ><%=dbox.getString("d_crdate")%></td>
	                <td class="table_02_1" ><%=dbox.getString("d_isgraduated_b2c")%></td>
	                <td class="table_02_1" ><%=dbox.getString("d_sul_cnt")%></td>
	                <td class="table_02_1" ><%=dbox.getString("d_distcode_avg")%> </td>
	            </tr>
<%            
           		}
            
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
