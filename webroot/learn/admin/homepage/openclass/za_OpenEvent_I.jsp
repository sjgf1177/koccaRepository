
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

    ArrayList<DataBox> list = (ArrayList)request.getAttribute("list");
    ArrayList<DataBox> checkList = (ArrayList)request.getAttribute("checkList");
    
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
		$("#p_start_date").datepicker({
	        showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
	        buttonImage: "/images/portal/ico/ico_calendar.gif", // 버튼 이미지
	        buttonText: "검색 시작일자",
	        buttonImageOnly: true, // 버튼에 있는 이미지만 표시한다.
	        changeMonth: true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
	        changeYear: true, // 년을 바꿀 수 있는 셀렉트 박스를 표시한다.
	        // minDate: "-7d", // 현재날짜로부터 100년이전까지 년을 표시한다.
	        maxDate: "100d", // 현재날짜로부터 100년이전까지 년을 표시한다.
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
	            $("#p_end_date" ).datepicker( "option", "minDate", selectedDate );
	        }
	    });
	    $("#p_end_date").datepicker({
	        showOn: "both", // 버튼과 텍스트 필드 모두 캘린더를 보여준다.
	        buttonImage: "/images/portal/ico/ico_calendar.gif", // 버튼 이미지
	        buttonText: "검색 시작일자",
	        buttonImageOnly: true, // 버튼에 있는 이미지만 표시한다.
	        changeMonth: true, // 월을 바꿀수 있는 셀렉트 박스를 표시한다.
	        changeYear: true, // 년을 바꿀 수 있는 셀렉트 박스를 표시한다.
	        // minDate: "-7d", // 현재날짜로부터 100년이전까지 년을 표시한다.
	        maxDate: "100d", // 현재날짜로부터 100년이전까지 년을 표시한다.
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
	            $("#p_start_date" ).datepicker( "option", "maxDate", selectedDate );
	        }
	    });
		
		
		$('#p_search_event').keypress(function(){
			if(event.keyCode == 13){
				searchOpenclass();
			}
		});
		
		$('#p_seq option').prop('selected', true);
	});

    //이벤트 목록으로
    function selectList() {
        document.form1.action = "/servlet/controller.homepage.OpenEventServlet";
        document.form1.p_process.value = "eventList";
        document.form1.p_pageno.value = "1";
        document.form1.target = "_self";
        document.form1.submit();
    }
    
    //이벤트 등록
    function whenInsert(){
    	
    	if($.trim($('#p_event_title').val()) == ""){
    		alert("이벤트명을 입력하세요.");
    		return;
    	}
    	if($('#p_start_date').val() == "" || $('#p_end_date').val() == ""){
    		alert("이벤트기간을 입력하세요.");
    		return;
    	}
    	
    	var limit = parseInt($.trim($('#p_study_limit').val()));
    	if(limit * 0 != 0){
    		alert("당첨대상 강좌수를 입력하세요.");
    		return;
    	}
    	
    	if ( $("#p_seq option").size() == 0) {
            alert("등록된 강좌가 없습니다.");
            return;
        }
    	
    	$('#p_seq option').prop('selected', true);
        document.form1.action = "/servlet/controller.homepage.OpenEventServlet";
        document.form1.p_process.value = "insert";
        document.form1.submit();
    }
    
    // 강좌검색
    function searchOpenclass(){
    	$('#p_seq option').prop('selected', true);
    	document.form1.action = "/servlet/controller.homepage.OpenEventServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }
    
    // 강좌 추가
    function fnSendToClassify() {
        if ( ($("#p_search_seq option:selected").length) == 0) {
            alert("추가할 강좌를 선택하세요.");
            return false;
        }

        $("#p_search_seq option:selected").each( function() {
            $("#p_seq").append($(this));
        });
    }
    
    // 강좌 제거
    function fnRemoveFromClassify() {
        if ( ($("#p_seq option:selected").length) ==0 ) {
            alert("제거할 강좌를 선택하세요.");
            return false;
        }

        $("#p_seq option:selected").each( function() {
            $("#p_search_seq").append($(this));
        });
    }
    
//-->
</SCRIPT>

</head>


<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
	<form name="form1" method="post">
		<input type="hidden" name="p_process" 		value=""> 
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
								<a href="javascript:whenInsert();"><img src="/images/admin/button/btn_apply.gif" border="0"></a> 
							</td>
						</tr>
					</table> 
					<br/> 
					
					<!----------------- 이벤트정보 시작 ----------------->
					<table cellspacing="1" cellpadding="5" class="table_out">
						<colgroup>
							<col width="20%"/>
							<col width="*"/>
						</colgroup>
						<tr>
							<td colspan="2" class="table_top_line"></td>
						</tr>
						<tr>
							<td class="table_title"><b>이벤트명</b></td>
							<td class="table_02_2">
								<input type="text" class="input" id="p_event_title" name="p_event_title" value="<%=box.getString("p_event_title")%>" style="width:80%;"/>
							</td>
						</tr>
						<tr>
							<td class="table_title"><b>이벤트기간</b></td>
							<td class="table_02_2">
								<input type="text" id="p_start_date" name="p_start_date" value="<%=box.getString("p_start_date")%>" class="input date" readonly="readonly" maxlength="10" size="12" /> 
								~ 
								<input type="text" id="p_end_date" name="p_end_date" value="<%=box.getString("p_end_date")%>" class="input date" readonly="readonly" maxlength="10" size="12" />
							</td>
						</tr>
						<tr>
							<td class="table_title"><b>당첨대상 강좌수</b></td>
							<td class="table_02_2">
								<input type="text" class="input" id="p_study_limit" name="p_study_limit" value="<%=box.getString("p_study_limit")%>"/>
							</td>
						</tr>
						<tr>
							<td class="table_title"><b>사용여부</b></td>
							<td class="table_02_2">
								<input type="radio" id="p_use_y" name="p_use_yn" value="Y" checked="checked"/><label for="p_use_y">Y</label>
								<input type="radio" id="p_use_n" name="p_use_yn" value="N" /><label for="p_use_n">N</label>
							</td>
						</tr>
					</table>
					<br/>
					<!----------------- 이벤트정보 종료 ----------------->
					
					<!-- 등록 화면 시작 //-->
		            <table cellspacing="1" cellpadding="5" class="table_out">
		                <colgroup>
		                    <col width="45%;" />
		                    <col width="10%;" />
		                    <col width="*;" />
		                </colgroup>
		                <tr>
		                    <td colspan="3" class="table_top_line"></td>
		                </tr>
		                <tr>
		                    <td class="table_title">
		                    	<input type="text" class="input" id="p_search_event" name="p_search_event" value="<%=box.getString("p_search_event") %>" style="width:60%;ime-mode:active;"/>
		                    	<a href="javascript:searchOpenclass();"><img src="/images/admin/button/b_go.gif" align="absmiddle"  border="0"></a>
		                    </td>
		                    <td class="table_title">보내기/<br/>제거하기</td>
		                    <td class="table_title">등록된 강좌</td>
		                </tr>
		                <tr>
		                    <td class="table_01">
		                        <select multiple id="p_search_seq" size="20" style="font-size:10pt; width:420px; background-color:#efefef;" ondblclick="fnSendToClassify();">
		                        <%if(list != null && list.size() > 0){%>
		                        	<%for(DataBox dbox : list){%>
		                        		<option value="<%= dbox.getString("d_seq") %>"><%= dbox.getString("d_lecnm") %></option>
		                        	<%}%>
		                        <%}%>
		                        </select>
		                    </td>
		                    <td class="table_02_1">
		                    	<input type="button" onclick="fnSendToClassify();" value="&gt;&gt;"/>
		                    	<br/><br/>
		                    	<input type="button" onclick="fnRemoveFromClassify();" value="&lt;&lt;"/>
		                    </td>
		                    <td class="table_01">
		                        <select multiple id="p_seq" name="p_seq" size="20" style="font-size:10pt; width:420px; background-color:#ffffb3;" ondblclick="fnRemoveFromClassify();">
		                        <%if(checkList != null && checkList.size() > 0){%>
		                        	<%for(DataBox dbox : checkList){%>
		                        		<option value="<%= dbox.getString("d_seq") %>"><%= dbox.getString("d_lecnm") %></option>
		                        	<%}%>
		                        <%}%>
		                        </select>
		                    </td>
		                </tr>
		            </table>
					
					
				</td>
			</tr>
		</table>


		<%@ include file="/learn/library/getJspName.jsp"%>
	</form>
</body>
</html>
