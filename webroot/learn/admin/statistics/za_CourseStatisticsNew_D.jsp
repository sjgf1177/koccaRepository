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
    

</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">


	팝업페이지

</body>
</html>
