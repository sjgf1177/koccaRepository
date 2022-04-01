
<%
//**********************************************************
//  1. 제      목: 운영자자료실
//  2. 프로그램명 : za_PdsAdmin_L.jsp
//  3. 개      요: 운영자자료실 리스트
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2005. 1. 2
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr"%>
<%@ page errorPage="/learn/library/error.jsp"%>
<%@ page import="java.util.*"%>
<%@ page import="com.credu.homepage.*"%>
<%@ page import="com.credu.library.*"%>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    int v_pageno    = box.getInt("p_pageno");
    int v_pagesize 	= box.getInt("p_pagesize");

    String v_event_seq  = box.getString("p_event_seq");
    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");
    String v_sdate     	= box.getString("p_searchSdate");
    String v_edate     	= box.getString("p_searchEdate");


    DataBox info = (DataBox)request.getAttribute("info");
    ArrayList<DataBox> countList = (ArrayList)request.getAttribute("countList");
    ArrayList<DataBox> list = (ArrayList)request.getAttribute("list");
%>


<html>
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">



<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/CSS" href="/css/jquery-ui-1.10.4.css">
<script type="text/javascript" src="/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/script/jquery-ui-1.10.4.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
	/**
	 * 페이지 초기화 함수
	 */
	$(document).ready( function() {
		$("#p_sdate").datepicker({
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
	        dayNamesMin: ["월", "화", "수", "목", "금", "토", "일"], // 요일의 한글 형식.
	        monthNamesShort: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"], // 월의 한글 형식.
	
	        onClose: function( selectedDate ) {
	            $("#p_edate" ).datepicker( "option", "minDate", selectedDate );
	        }
	    });
	    $("#p_edate").datepicker({
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
	        dayNamesMin: ["월", "화", "수", "목", "금", "토", "일"], // 요일의 한글 형식.
	        monthNamesShort: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"], // 월의 한글 형식.
	
	        onClose: function( selectedDate ) {
	            $("#p_sdate" ).datepicker( "option", "maxDate", selectedDate );
	        }
	    });
	});

    //이벤트 목록으로
    function selectList() {
        document.form1.action = "/servlet/controller.homepage.OpenEventServlet";
        document.form1.p_process.value = "eventList";
        document.form1.p_pageno.value = "1";
        document.form1.target = "_self";
        document.form1.submit();
    }
    
    function detailPage(){
        document.form1.action = "/servlet/controller.homepage.OpenEventServlet";
        document.form1.p_process.value = "detailPage";
        document.form1.submit();
    }
    
    // 엑셀다운로드
    function whenExcel(){
    	document.form1.action = "/servlet/controller.homepage.OpenEventServlet";
        document.form1.p_process.value = "excel";
        document.form1.submit();
    }
    
//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
	<form name="form1" method="post">
		<input type="hidden" name="p_process" 		value=""> 
		<input type="hidden" name="p_event_seq" 	value="<%=v_event_seq%>"> 
		<input type="hidden" name="p_pageno" 		value="<%=v_pageno%>"> 
		<input type="hidden" name="p_pagesize" 		value="<%=v_pagesize%>"> 
		<input type="hidden" name="p_searchtext" 	value="<%=v_searchtext%>"> 
		<input type="hidden" name="p_search" 		value="<%=v_search%>"> 
		<input type="hidden" name="p_searchSdate" 	value="<%=v_sdate%>"> 
		<input type="hidden" name="p_searchEdate" 	value="<%=v_edate%>"> 


		<table width="1000" border="0" cellspacing="0" cellpadding="0" height="600">
			<tr>
				<td align="center" valign="top">
					<!----------------- title 시작 ----------------->
					<table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
						<tr>
							<td><img src="/images/admin/portal/s.1_41.gif"></td>
							<td align="right"><img src="/images/admin/common/sub_title_tail.gif"></td>
						</tr>
					</table> 
					<!----------------- title 끝 -----------------> 
					<br>
					
					
					<table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
						<tr valign="middle">
							<td align="right">
								<a href="javascript:selectList();"><img src="/images/admin/button/btn_return.gif" border="0"></a> 
							</td>
						</tr>
					</table> 
					<br/> 
					
					<!----------------- 이벤트정보 시작 ----------------->
					<table cellspacing="1" cellpadding="5" class="table_out">
						<colgroup>
							<col width="15%"/>
							<col width="50%"/>
							<col width="15%"/>
							<col width="20%"/>
						</colgroup>
						<tr>
							<td colspan="4" class="table_top_line"></td>
						</tr>
						<tr>
							<td class="table_title"><b>이벤트명</b></td>
							<td class="table_02_2"><%=info.getString("d_event_title")%></td>
							<td class="table_title"><b>사용여부</b></td>
							<td class="table_02_2"><%=info.getString("d_use_yn")%></td>
						</tr>
						<tr>
							<td class="table_title"><b>이벤트기간</b></td>
							<td class="table_02_2"><%=info.getString("d_start_date")%> ~ <%=info.getString("d_end_date")%></td>
							<td class="table_title"><b>당첨대상 강좌수</b></td>
							<td class="table_02_2"><%=info.getString("d_study_limit")%></td>
						</tr>
					</table>
					<br/>
					<!----------------- 이벤트정보 종료 ----------------->
					 
					<!----------------- List 시작 ----------------->
					<table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
						<tr valign="middle">
							<td align="right" width="95%">
								이벤트 이전 기준일
								<input type="text" id="p_sdate" name="p_sdate" value="<%= box.getString("p_sdate") %>" class="input date" maxlength="10" size="12" /> 
								~ 
								<input type="text" id="p_edate" name="p_edate" value="<%= box.getString("p_edate") %>" class="input date" maxlength="10" size="12" />
							</td>
							<td align="right">
								<a href="javascript:detailPage();"><img src="/images/admin/button/b_go.gif" border="0"></a> 
							</td>
						</tr>
					</table> 
					<table class="table_out" cellspacing="1" cellpadding="5">
		                <tr>
		                    <td colspan="8" class="table_top_line"></td>
		                </tr>
		                <tr>
		                    <td class="table_title" width="5%"><b>강좌코드</b></td>
		                    <td class="table_title"><b>강좌명</b></td>
		                    <td class="table_title" width="15%"><b><%=box.getDate("p_sdate", ".")%> ~ <%=box.getDate("p_edate", ".")%></b></td>
		                    <td class="table_title" width="15%"><b>이벤트기간(전체)</b></td>
		                    <td class="table_title" width="15%"><b>이벤트기간(로그인)</b></td>
		                </tr>
		                <%if(countList != null && countList.size() > 0){%>
		                	<%
		                		int prev_total = 0;
		                		int event_total = 0;
		                		int login_total = 0;
		                		for(DataBox dbox : countList){
		                			prev_total += dbox.getInt("d_prev_count");
		                			event_total += dbox.getInt("d_event_all_count");
		                			login_total += dbox.getInt("d_login_count");
		                	%>
	                				<tr>
	                					<td class="table_02_1"><%=dbox.getString("d_seq")%></td>
	                					<td class="table_02_2"><%=dbox.getString("d_lecnm")%></td>
	                					<td class="table_02_1"><%=dbox.getString("d_prev_count")%></td>
	                					<td class="table_02_1"><%=dbox.getString("d_event_all_count")%></td>
	                					<td class="table_02_1"><%=dbox.getString("d_login_count")%></td>
	                				</tr>
		                	<%
		                		}
		                		if(countList != null && countList.size() > 0){
		                	%>
	                				<tr>
	                					<td class="table_01" colspan="2"><b>합  계</b></td>
	                					<td class="table_01"><b><%=prev_total%></b></td>
	                					<td class="table_01"><b><%=event_total%></b></td>
	                					<td class="table_01"><b><%=login_total%></b></td>
	                				</tr>
		                	<%
		                		}
		                	%>
		                <%}else{%>
		                	<tr>
		                		<td class="table_02_1" colspan="8">조회된 데이터가 없습니다.</td>
		                	</tr>
		                <%}%>
		            </table>
		            <br/>
					<!----------------- List 종료 ----------------->
					 
					 
					<table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
						<tr valign="middle">
							<td align="right">
								<a href="javascript:whenExcel();"><img src="/images/admin/button/btn_excell_print.gif" border="0"></a> 
							</td>
						</tr>
					</table> 
					
					<!----------------- List 시작 ----------------->
					<table class="table_out" cellspacing="1" cellpadding="5">
		                <tr>
		                    <td colspan="9" class="table_top_line"></td>
		                </tr>
		                <tr>
		                    <td class="table_title" width="3%"><b>No</b></td>
		                    <td class="table_title" width="5%"><b>아이디</b></td>
		                    <td class="table_title" width="5%"><b>이름</b></td>
		                    <td class="table_title" width="5%"><b>이메일</b></td>
		                    <td class="table_title" width="0%"><b>전화번호</b></td>
		                    <td class="table_title" width="5%"><b>강좌코드</b></td>
		                    <td class="table_title"><b>강좌명</b></td>
		                    <td class="table_title" width="8%"><b>학습일시</b></td>
		                    <td class="table_title" width="5%"><b>학습횟수</b></td>
		                </tr>
		                <%if(list != null && list.size() > 0){%>
		                	<%
		                		int v_row = 1;
		                		int v_row_cnt = 1;
		                		String v_userid = "";
		                		String v_name 	= "";
		                		String v_email	= "";
		                		String v_handphone = "";
		                		String v_seq	= "";
		                		String v_userid_tmp = "";
		                		String v_lecnm	= "";
		                		for(DataBox dbox : list){
		                			v_row_cnt = dbox.getInt("d_row_cnt");
		                			v_userid	= dbox.getString("d_userid");
		                			v_name		= dbox.getString("d_name");
		                			v_email		= dbox.getString("d_email");
		                			v_handphone	= dbox.getString("d_handphone");
		                			v_seq		= dbox.getString("d_seq");
		                			v_lecnm 	= dbox.getString("d_lecnm");
		                	%>
	                				<tr>
	                					<%if(!v_userid_tmp.equals(v_userid)){%>
	                					<%v_userid_tmp = v_userid;%>
	                					<td class="table_02_1" rowspan="<%=v_row_cnt%>"><%=v_row++%></td>
	                					<td class="table_02_1" rowspan="<%=v_row_cnt%>"><%=v_userid%></td>
	                					<td class="table_02_1" rowspan="<%=v_row_cnt%>"><%=v_name%></td>
	                					<td class="table_02_2" rowspan="<%=v_row_cnt%>"><%=v_email%></td>
	                					<td class="table_02_1" rowspan="<%=v_row_cnt%>"><%=v_handphone%></td>
	                					<%}%>
	                					<td class="table_02_1"><%=v_seq%></td>
	                					<td class="table_02_2"><%=v_lecnm%></td>
	                					<td class="table_02_1"><%=dbox.getString("d_study_date")%></td>
	                					<td class="table_02_1"><%=dbox.getString("d_study_count")%></td>
	                				</tr>
		                	<%
		                		}
		                	%>
		                <%}else{%>
		                	<tr>
		                		<td class="table_02_1" colspan="9">조회된 데이터가 없습니다.</td>
		                	</tr>
		                <%}%>
		            </table>
					<!----------------- List 종료 ----------------->
					
				</td>
			</tr>
		</table>


		<%@ include file="/learn/library/getJspName.jsp"%>
	</form>
</body>
</html>
