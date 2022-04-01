<%
//**********************************************************
//  1. 제      목: 정규·상시 과정별 통계
//  2. 프로그램명 : za_CourseStatistics_L.jsp
//  3. 개      요: 정규·상시 과정별 통계
//  4. 환      경: JDK 1.7
//  5. 버      젼: 1.0
//  6. 작      성: 김기종 2016. 6. 10
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr"%>
<%@page errorPage="/learn/library/error.jsp"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.text.*"%>
<%@ page import="com.credu.library.DataBox"%>
<%@ page import="com.credu.library.FormatDate"%>
<%@ page import="com.credu.library.RequestBox"%>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select"%>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String searchCondition = box.getStringDefault("searchCondition", "year");
    String searchGubun = box.getStringDefault("searchGubun", "ALL");
    String searchDateGubun = box.getStringDefault("searchDateGubun", "eduend");
    String searchStartDate = box.getString("searchStartDate");
    String searchEndDate = box.getString("searchEndDate");
    String chkStep = box.getString("chkStep");

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
<title>열린강좌 관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/CSS" href="/css/jquery-ui-1.10.4.css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/script/jquery-ui-1.10.4.js"></script>
<script type="text/javascript">
    /**
     * 페이지 초기화 함수
     */
    $(document).ready( function() {

        $(document).title = "검색어 통계 | 통계 | 한국콘텐츠아카데미 관리자";

        $("#oSearchStartDate").datepicker({
            showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
            buttonImage: "/images/portal/ico/ico_calendar.gif", // 버튼 이미지
            buttonText: "검색 시작일자",
            buttonImageOnly: true, // 버튼에 있는 이미지만 표시한다.
            changeMonth: true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
            changeYear: true, // 년을 바꿀 수 있는 셀렉트 박스를 표시한다.
            // minDate: "-7d", // 현재날짜로부터 100년이전까지 년을 표시한다.
            maxDate: "0d", // 현재날짜로부터 100년이전까지 년을 표시한다.
            nextText: "다음 달", // next 아이콘의 툴팁.
            prevText: "이전 달", // prev 아이콘의 툴팁.
            numberOfMonths: [1,1], // 한번에 얼마나 많은 월을 표시할것인가. [2,3] 일 경우, 2(행) x 3(열) = 6개의 월을 표시한다.
            stepMonths: 1, // next, prev 버튼을 클릭했을때 얼마나 많은 월을 이동하여 표시하는가.
            yearRange: "-5:", // 년도 선택 셀렉트박스를 현재 년도에서 이전, 이후로 얼마의 범위를 표시할것인가.
            showButtonPanel: true, // 캘린더 하단에 버튼 패널을 표시한다.
            currentText: "오늘" , // 오늘 날짜로 이동하는 버튼 패널
            closeText: "닫기",  // 닫기 버튼 패널
            dateFormat: "yymmdd", // 텍스트 필드에 입력되는 날짜 형식.
            showAnim: "slideDown", //애니메이션을 적용한다.
            showMonthAfterYear: true , // 월, 년순의 셀렉트 박스를 년,월 순으로 바꿔준다.
            dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"], // 요일의 한글 형식.
            monthNamesShort: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"], // 월의 한글 형식.

            onClose: function( selectedDate ) {
                $("#oSearchEndDate" ).datepicker( "option", "minDate", selectedDate );
            }
        });

        $("#oSearchEndDate").datepicker({
            showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
            buttonImage: "/images/portal/ico/ico_calendar.gif", // 버튼 이미지
            buttonText: "검색 종료일자",
            buttonImageOnly: true, // 버튼에 있는 이미지만 표시한다.
            changeMonth: true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
            changeYear: true, // 년을 바꿀 수 있는 셀렉트 박스를 표시한다.
            // minDate: "-7d", // 현재날짜로부터 100년이전까지 년을 표시한다.
            maxDate: "0d",
            nextText: "다음 달", // next 아이콘의 툴팁.
            prevText: "이전 달", // prev 아이콘의 툴팁.
            numberOfMonths: [1,1], // 한번에 얼마나 많은 월을 표시할것인가. [2,3] 일 경우, 2(행) x 3(열) = 6개의 월을 표시한다.
            stepMonths: 1, // next, prev 버튼을 클릭했을때 얼마나 많은 월을 이동하여 표시하는가.
            yearRange: "-15:", // 년도 선택 셀렉트박스를 현재 년도에서 이전, 이후로 얼마의 범위를 표시할것인가.
            showButtonPanel: true, // 캘린더 하단에 버튼 패널을 표시한다.
            currentText: "오늘" , // 오늘 날짜로 이동하는 버튼 패널
            closeText: "닫기",  // 닫기 버튼 패널
            dateFormat: "yymmdd", // 텍스트 필드에 입력되는 날짜 형식.
            showAnim: "slideDown", //애니메이션을 적용한다.
            showMonthAfterYear: true , // 월, 년순의 셀렉트 박스를 년,월 순으로 바꿔준다.
            dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"], // 요일의 한글 형식.
            monthNamesShort: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"], // 월의 한글 형식.

            onClose: function( selectedDate ) {
                $("#oSearchStartDate" ).datepicker( "option", "maxDate", selectedDate );
            }
        });
    });

    /**
     * 검색조건에 설정된 기준별로 통계자료 목록을 조회한다.
     */
    function fnOpenClassStatisticsList() {
        var searchCondtion = $("#oSearchCondition").val();
        if ( searchCondtion != "year" ) {
            if ( $("#oSearchStartDate").val() == "" ) {
                alert("조회 시작일을 입력 혹은 선택하세요");
                $("#oSearchStartDate").focus();
                return;
            } else if ( $("#oSearchEndDate").val() == "" ) {
                alert("조회 종료일을 입력 혹은 선택하세요");
                $("#oSearchEndDate").focus();
                return;
            }
        }

        $("#oAction").val("go");
        $("#oProcess").val("selectCourseStatisticList");
        $('#oOpenClassSearchForm').attr({
        	action : '?',
        	target : '_self'
        }).submit();
    }
    
    function goExcel(){
    	var searchCondtion = $("#oSearchCondition").val();
        if ( searchCondtion != "year" ) {
            if ( $("#oSearchStartDate").val() == "" ) {
                alert("조회 시작일을 입력 혹은 선택하세요");
                $("#oSearchStartDate").focus();
                return;
            } else if ( $("#oSearchEndDate").val() == "" ) {
                alert("조회 종료일을 입력 혹은 선택하세요");
                $("#oSearchEndDate").focus();
                return;
            }
        }

        $("#oAction").val("go");
        $("#oProcess").val("selectCourseStatisticExcel");
        $('#oOpenClassSearchForm').attr({
        	action : '?',
        	target : '_self'
        }).submit();
    }
    //goExcelLowData
    function goExcelLowData(){
    	var searchCondtion = $("#oSearchCondition").val();
        if ( searchCondtion != "year" ) {
            if ( $("#oSearchStartDate").val() == "" ) {
                alert("조회 시작일을 입력 혹은 선택하세요");
                $("#oSearchStartDate").focus();
                return;
            } else if ( $("#oSearchEndDate").val() == "" ) {
                alert("조회 종료일을 입력 혹은 선택하세요");
                $("#oSearchEndDate").focus();
                return;
            }
        }

        $("#oAction").val("go");
        $("#oProcess").val("selectCourseStatisticExcelLowData");
        $('#oOpenClassSearchForm').attr({
        	action : '?',
        	target : '_self'
        }).submit();
    }
    

</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">


	<table width="1000" border="0" cellspacing="0" cellpadding="0">
		<tr>
			<td align="center" valign="top">
				<!-- title 시작 //-->
				<table width="97%" border="0" cellspacing="0" cellpadding="0" class="">
					<tr>
						<td><h3>B2C정규과정 참여통계 - 2017.07.28</h3></td>
						<td align="right"><img src="/images/admin/common/sub_title_tail.gif" /></td>
					</tr>
				</table> 
				<!-- title 끝 //--> 
				
				<!-- 검색 조건 시작 //-->
				<form id="oOpenClassSearchForm" name="openClassSearchForm" method="get">
					<input type="hidden" id="oProcess" name="p_process" value="" />
					<input type="hidden" id="oAction" name="p_action" value="" />
					<table cellspacing="0" cellpadding="1" class="form_table_out">
						<tr>
							<td bgcolor="#C6C6C6" align="center">

								<table cellspacing="0" cellpadding="0" class="form_table_bg">
									<tr>
										<td height="7"></td>
									</tr>
									<tr>
										<td align="center">

											<table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
												<colgroup>
													<col width="10%" /></col>
													<col width="*" /></col>
												</colgroup>
												<tr>
													<th style="display:none">구분</th>
													<td style="display:none">
														<select id="searchGubun" name="searchGubun">
															<option value="B2C" selected >B2C</option>
														</select>
													</td>
													<th style="display:none">기간기준</th>
													<td style="display:none">
														<select id="searchDateGubun" name="searchDateGubun">
															<option value="edustart" <%=searchDateGubun.equals("edustart") ? "selected" : ""%>>학습시작일</option>
															<option value="eduend" <%=searchDateGubun.equals("eduend") ? "selected" : ""%>>학습종료일</option>
														</select>
													</td>
													<th>진도율</th>
													<td>
														<label style="cursor:pointer;">
														0% 포함 <input name="chkStep" <%= (!"".equals(chkStep)?"checked":"") %> type="checkbox" value="null">
														</label>
													</td>
													<th>수강생의 학습참여 시작일 기준</th>
													<td>
														<input type="text" id="oSearchStartDate" name="searchStartDate" value="<%= box.getString("searchStartDate") %>" class="input" maxlength="10" size="12" /> 
														~ 
														<input type="text" id="oSearchEndDate" name="searchEndDate" value="<%= box.getString("searchEndDate") %>" class="input" maxlength="10" size="12" />
													</td>
													<td width="10%" align="right"><a href="javascript:fnOpenClassStatisticsList('go')"><img src="/images/admin/button/b_go.gif" border="0" align="absmiddle"></a></td>
												</tr>
											</table>
										</td>
									</tr>
									<tr>
										<td height="9"></td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</form> 
				<!-- 검색 조건 끝 //--> 
				
				
				<!----------------- 엑셀출력 버튼 시작 ----------------->
                <table cellpadding="0" cellspacing="0" class="table1">
	                <tr>
                 		<td align="right">
                 			<a href="javascript:goExcel('go')" class="c blueArrowBtn" style="vertical-align: middle; display: inline-block;"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>
                 			<a href="javascript:goExcelLowData('go')" class="c blueArrowBtn" style="color:#666;display: inline-block;vertical-align: middle; padding: 1px 2px; margin: 3px; border: 1px solid #cccccc; border-radius: 2px;">
                 				<span style="display:inline-block; width: 8px; height: 8px; overflow: hidden; position: relative; vertical-align: middle;">
                 					<img src="/images/admin/button/btn_excelprint.gif"  border="0" style="position:absolute; display: block; z-index: -1; margin-left: -4px; margin-top: -7px;">
                 				</span>
                 				<span style=" vertical-align: middle;">
                 					엑셀출력LowData
                 				</span>
               				</a>
               			</td>
	                </tr>
	                <tr>
                  		<td height="3"></td>
	                </tr>
                </table>
                <!----------------- 엑셀출력 버튼 끝 ----------------->
				
				
				<table class="table_out" cellspacing="1" cellpadding="5" style="margin-left:15px;">
	                <tr>
	                    <td colspan="17" class="table_top_line"></td>
	                </tr>
	                <tr>
	                    <td class="table_title" width="20%"><b>분야</b></td>
	                    <td class="table_title" width="15%"><b>과정코드</b></td>
	                    <td class="table_title" width="*"><b>과정명</b></td>
	                    <td class="table_title" width="10%"><b>참여인원</b></td>
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
                <td class="table_02" colspan="3">총 합</td>
                <td class="table_02" ><%= tot_studentCnt %></td>
                <!-- <td class="table_02" ><%= tot_stoldCnt %> </td> -->
            </tr>
			<tr>
                <td class="table_02" colspan="2">방송영상</td>
                <td class="table_02" >방송영상 합계</td>
                <td class="table_02" ><%= studentCnt_B0 %></td>
                <!-- <td class="table_02" ><%= stoldCnt_B0 %> </td> -->
            </tr>
			<tr>
                <td class="table_02" colspan="2">게임개발</td>
                <td class="table_02" >게임개발 합계 </td>
                <td class="table_02" ><%= studentCnt_G0 %></td>
                <!-- <td class="table_02" ><%= stoldCnt_G0 %> </td> -->
            </tr>
			<tr>
                <td class="table_02" colspan="2">문화콘텐츠</td>
                <td class="table_02" >문화콘텐츠 합계 </td>
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
			<table class="table_out" cellspacing="1" cellpadding="5" style="margin-left:15px;">
				<colgroup>
					<col width="20%"/>
					<col width="15%"/>
					<col width="*"/>
					<col width="10%"/>
					<!-- <col width="10%"/> -->
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
	                <td class="table_02_2" >
	                	<%=dbox.getString("d_subjnm")%>
	                	<!-- 
	                	<a href="/servlet/controller.statistics.CourseStatisticNewServlet?p_process=selectCourseStatisticView" target="_blank"><button style="cursor:pointer;">상세조회</button></a>
	                	 -->
                	</td>
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

<%@ include file="/learn/library/getJspName.jsp"%>
</body>
</html>
