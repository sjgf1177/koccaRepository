
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

    int v_dispnum   = 0;
    int v_totalpage = 0;
    
    int v_totalrowcount = 0;

    int v_pageno    = box.getInt("p_pageno");
    int v_pagesize 	= box.getInt("p_pagesize");

    String v_searchtext = box.getString("p_searchtext");
    String v_search     = box.getString("p_search");


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
	    $("#p_searchSdate").datepicker({
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
	            $("#p_searchEdate" ).datepicker( "option", "minDate", selectedDate );
	        }
	    });
	    $("#p_searchEdate").datepicker({
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
	            $("#p_searchSdate" ).datepicker( "option", "maxDate", selectedDate );
	        }
	    });
	    
	    $('#p_searchtext').keypress(function(){
	    	if(event.keyCode == 13){
	    		selectList();
	    	}
	    });
	
	    searchChange();
	});

    //리스트 조회
    function selectList() {
        document.form1.action = "/servlet/controller.homepage.OpenEventServlet";
        document.form1.p_process.value = "eventList";
        document.form1.p_pageno.value = "1";
        document.form1.target = "_self";
        document.form1.submit();
    }
    
    //페이지 이동
    function goPage(pageNum) {
	    document.form1.action = "/servlet/controller.homepage.OpenEventServlet";
	    document.form1.p_process.value = "eventList";
	    document.form1.p_pageno.value = pageNum;
        document.form1.target = "_self";
	    document.form1.submit();
    }
    
    //리스트보기 갯수 변경
    function pagesize(pageSize) {
        document.form1.p_pagesize.value = pageSize;
        selectList();
    }
    
    
    
    //입력화면 이동
    function insertPage() {
        document.form1.action = "/servlet/controller.homepage.OpenEventServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
    
    //대상자 목록페이지
    function detailPage(seq){
        document.form1.p_event_seq.value = seq;
        document.form1.action = "/servlet/controller.homepage.OpenEventServlet";
        document.form1.p_process.value = "detailPage";
        document.form1.submit();
    }
    
    //수정페이지
    function updatePage(seq){
    	document.form1.p_event_seq.value = seq;
        document.form1.action = "/servlet/controller.homepage.OpenEventServlet";
        document.form1.p_process.value = "updatePage";
        document.form1.submit();
    }

    function searchChange(){
    	var v_search = document.form1.p_search.value;
    	if(v_search == "title"){
    		$('#titleLayer').show();
    		$('#dateLayer').hide();
    	}else{
    		$('#titleLayer').hide();
    		$('#dateLayer').show();
    	}
    }
//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
	<form name="form1" method="post">
		<input type="hidden" name="p_process" 	value=""> 
		<input type="hidden" name="p_event_seq" value=""> 
		<input type="hidden" name="p_pageno" 	value="<%=v_pageno %>"> 
		<input type="hidden" name="p_pagesize" 	value="<%=v_pagesize %>"> 


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
							<td align="right" width="85%">
								<select name="p_search" onchange="javascript:searchChange();">
									<option value='title' <% if (v_search.equals("title")) out.print("selected"); %>>이벤트명</option>
									<option value='date' <% if (v_search.equals("date")) out.print("selected"); %>>이벤트기간</option>
								</select> 
								<span id="titleLayer">
									<input id="p_searchtext" name="p_searchtext" type="text" class="input" value='<%=v_searchtext %>'>
								</span>
								<span id="dateLayer">
									<input type="text" id="p_searchSdate" name="p_searchSdate" value="<%= box.getString("p_searchSdate") %>" class="input date" maxlength="10" size="12" /> 
									~ 
									<input type="text" id="p_searchEdate" name="p_searchEdate" value="<%= box.getString("p_searchEdate") %>" class="input date" maxlength="10" size="12" />
									※ 이벤트 시작일 기준
								</span>
							</td>
							<td align="right">
								<a href="javascript:selectList();"><img src="/images/admin/button/b_go.gif" border="0"></a> 
								<%if(BulletinManager.isAuthority(box, box.getString("p_canAppend"))) {%> 
									<a href="javascript:insertPage();"><img src="/images/admin/button/btn_add.gif" border="0"></a> 
								<%}%>
							</td>
						</tr>
					</table> 
					<br> 
					
					<!----------------- List 시작 ----------------->
					<table class="table_out" cellspacing="1" cellpadding="5">
		                <tr>
		                    <td colspan="6" class="table_top_line"></td>
		                </tr>
		                <tr>
		                    <td class="table_title" width="5%"><b>NO</b></td>
		                    <td class="table_title"><b>이벤트명</b></td>
		                    <td class="table_title" width="20%"><b>이벤트기간</b></td>
		                    <td class="table_title" width="12%"><b>당첨대상강좌수</b></td>
		                    <td class="table_title" width="10%"><b>사용여부</b></td>
		                    <td class="table_title" width="10%"><b>대상자관리</b></td>
		                </tr>
		                <%if(list != null && list.size() > 0){%>
		                	<%
		                		for(DataBox dbox : list){
		                			v_totalrowcount = dbox.getInt("d_tot_cnt");
		                			v_totalpage = dbox.getInt("d_totalpage");
		                	%>
	                				<tr>
	                					<td class="table_02_1" ><%=dbox.getString("d_event_seq")%></td>
	                					<td class="table_02_2" >
	                						<a href="#none" onclick="updatePage('<%=dbox.getString("d_event_seq")%>');"><%=dbox.getString("d_event_title")%></a>
	                					</td>
	                					<td class="table_02_1" >
	                						<%=dbox.getString("d_start_date")%> ~ <%=dbox.getString("d_end_date")%>
	                					</td>
	                					<td class="table_02_1" ><%=dbox.getString("d_study_limit")%></td>
	                					<td class="table_02_1" ><%=dbox.getString("d_use_yn")%></td>
	                					<td class="table_02_1" >
	                						<a href="#none" onclick="detailPage('<%=dbox.getString("d_event_seq")%>');"><img src="/images/admin/button/btn_checkok.gif"></a>
	                					</td>
	                				</tr>
		                	<%
		                		}
		                	%>
		                <%}else{%>
		                	<tr>
		                		<td class="table_02_1" colspan="6">조회된 데이터가 없습니다.</td>
		                	</tr>
		                <%}%>
		            </table>
					<!----------------- List 종료 ----------------->
					
					<!----------------- total 시작 ----------------->
		            <br>
		            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
		                <tr>
		                    <td align="right" valign="absmiddle">
		                        <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
		                    </td>
		                </tr>
		            </table>
		            <!----------------- total 끝 ----------------->
				</td>
			</tr>
		</table>


		<%@ include file="/learn/library/getJspName.jsp"%>
	</form>
</body>
</html>
