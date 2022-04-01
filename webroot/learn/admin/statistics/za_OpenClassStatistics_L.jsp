<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Notice_L.jsp
//  3. 개      요: 열린강좌 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Calendar" %>

<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.FormatDate" %>
<%@ page import="com.credu.library.RequestBox" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet" scope="page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String searchCondition = box.getStringDefault("searchCondition", "year");
    String searchStartDate = box.getString("searchStartDate");
    String searchEndDate = box.getString("searchEndDate");

    String subTitle = (searchCondition.equals("year")) ? "년도별 통계" : "기간별 통계";

    DataBox dbox = null;

    // ArrayList<DataBox> viewCountTotalList = (ArrayList<DataBox>)request.getAttribute("selectViewCountTotList");
    ArrayList<DataBox> viewCountList = (ArrayList<DataBox>)request.getAttribute("selectViewCountList");
    ArrayList<DataBox> viewCountTotalListInPeriod = (ArrayList<DataBox>)request.getAttribute("selectViewCountTotalListInPeriod");

    Calendar oCalendar = Calendar.getInstance( );

    int currentYear = oCalendar.get(Calendar.YEAR);
%>
<html>
<head>
<title>열린강좌 관리</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/CSS" href="/css/jquery-ui-1.10.4.css">
<script type="text/javascript" src = "/script/cresys_lib.js"></script>
<script type="text/javascript" src="/script/jquery-1.10.2.js"></script>
<script type="text/javascript" src="/script/jquery-ui-1.10.4.js"></script>
<script type="text/javascript">
<!--
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
            dayNamesMin: ["월", "화", "수", "목", "금", "토", "일"], // 요일의 한글 형식.
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
            dayNamesMin: ["월", "화", "수", "목", "금", "토", "일"], // 요일의 한글 형식.
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

        $("#oProcess").val("selectOpenClassStatisticsList");
        $("#oOpenClassSearchForm").attr("action", "/servlet/controller.statistics.OpenClassStatisticServlet").submit();
    }

    /**
     *
     */
    function fnSetSearchCondition(searchCondition) {
        if ( searchCondition == "year" ) {
            $("#oYearSearchTable").show();
            $("#oPeriodSearchTable").hide();
        } else {
            $("#oYearSearchTable").hide();
            $("#oPeriodSearchTable").show();
        }
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

        $("#oProcess").val("selectOpenClassStatisticsExcel");
        $("#oOpenClassSearchForm").attr("action", "/servlet/controller.statistics.OpenClassStatisticServlet").submit();
    }
//-->
</script>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">


<table width="1000" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center" valign="top">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td><h3>열린강좌 통계</h3></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" /></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <!-- 검색 조건 시작 //-->
            <form id="oOpenClassSearchForm" name="openClassSearchForm" method="get">
                <input type="hidden" id="oProcess" name="p_process" value="" />
            <table cellspacing="0" cellpadding="1" class="form_table_out">
                <tr>
                    <td bgcolor="#C6C6C6" align="center">

                        <table cellspacing="0" cellpadding="0" class="form_table_bg" >
                            <tr>
                                <td height="7"></td>
                            </tr>
                            <tr>
                                <td align="center">

                                    <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                                        <colgroup>
                                            <col width="9%" /></col>
                                            <col width="30%" /></col>
                                            <col width="50%" /></col>
                                            <col width="*" /></col>
                                        </colgroup>
                                        <tr>
                                            <th align="left" valign="middle">검색범위</th>
                                            <td>
                                                <select id="oSearchCondition" name="searchCondition" onchange="fnSetSearchCondition(this.value);">
                                                    <option value="year" <%= box.getString("searchCondition").equals("year") ? "selected=\"selected\"" : "" %>>년도별</option>
                                                    <option value="period" <%= box.getString("searchCondition").equals("period") ? "selected=\"selected\"" : "" %>>기간별</option>
                                                    <option value="daily" <%= box.getString("searchCondition").equals("daily") ? "selected=\"selected\"" : "" %>>일별</option>
                                                </select>
                                            </td>
                                            <td>
                                                <table width="98%" id="oYearSearchTable" <%= searchCondition.equals("year") ? "style=\"display:block;\"" : "style=\"display:none;\"" %>>
                                                    <colgroup>
                                                        <col width="20%"></col>
                                                        <col width="80%"></col>
                                                    </colgroup>
                                                    <tr>
                                                        <th>조회년도</th>
                                                        <td>
                                                            <select id="oSearchYear" name="searchYear">
<%
        for( int startYear = currentYear; startYear >= 2012 ; startYear-- ) {
%>
                                                                <option value="<%= startYear %>" <%= (box.getInt("searchYear") == startYear) ? " selected" : "" %>><%= startYear %></option>
<%
        }
%>
                                                            </select>
                                                        </td>
                                                    </tr>
                                                </table>
                                                <table width="98%" id="oPeriodSearchTable"  <%= !searchCondition.equals("year") ? "style=\"display:block;\"" : "style=\"display:none;\"" %>>
                                                    <colgroup>
                                                        <col width="20%"></col>
                                                        <col width="80%"></col>
                                                    </colgroup>
                                                    <tr>
                                                        <th>조회기간</th>
                                                        <td>
                                                            <input type="text" id="oSearchStartDate" name="searchStartDate" value="<%= box.getString("searchStartDate") %>" class="input" maxlength="10" size="12" /> ~
                                                            <input type="text" id="oSearchEndDate" name="searchEndDate" value="<%= box.getString("searchEndDate") %>" class="input" maxlength="10" size="12" />
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                            <td width="10%" align="right">
                                                <a href = "javascript:fnOpenClassStatisticsList('go')"><img src = "/images/admin/button/b_go.gif" border = "0" align="absmiddle"></a>
                                            </td>
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
            		<td align="right"><a href="javascript:goExcel('go')" class="c"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a></td>
	            </tr>
	            <tr>
             		<td height="3"></td>
	            </tr>
            </table>
            <!----------------- 엑셀출력 버튼 끝 ----------------->

<%
    if ( searchCondition.equals("year") || searchCondition.equals("period") ) {
%>
            <table class="table_out" cellspacing="1" cellpadding="5" style="margin-left:15px;">
                <tr>
                    <td colspan="17" class="table_top_line"></td>
                </tr>
                <tr>
                    <td colspan="17" style="font-weight:bold; font-size:15pt; background-color:#abcdef;"><%= subTitle %></td>
                </tr>
                <tr>
                    <td class="table_title" width="3%"><b>seq</b></td>
                    <td class="table_title" width="10%"><b>구분1</b></td>
                    <td class="table_title" width="8%"><b>구분2</b></td>
                    <td class="table_title"><b>과정명</b></td>
                    <td class="table_title" width="4%"><b>1월</b></td>
                    <td class="table_title" width="4%"><b>2월</b></td>
                    <td class="table_title" width="4%"><b>3월</b></td>
                    <td class="table_title" width="4%"><b>4월</b></td>
                    <td class="table_title" width="4%"><b>5월</b></td>
                    <td class="table_title" width="4%"><b>6월</b></td>
                    <td class="table_title" width="4%"><b>7월</b></td>
                    <td class="table_title" width="4%"><b>8월</b></td>
                    <td class="table_title" width="4%"><b>9월</b></td>
                    <td class="table_title" width="4%"><b>10월</b></td>
                    <td class="table_title" width="4%"><b>11월</b></td>
                    <td class="table_title" width="4%"><b>12월</b></td>
                    <td class="table_title" width="4%"><b>합계</b></td>
                </tr>
<%
        if ( viewCountTotalListInPeriod != null && viewCountTotalListInPeriod.size() > 0 ) {
            dbox = null;
            String styleTotal = "";
            for(int i = 0; i < viewCountTotalListInPeriod.size(); i++) {
                dbox = (DataBox)viewCountTotalListInPeriod.get(i);
                styleTotal = dbox.getString("d_seq").equals("99999") ? "style=\"font-weight:bold;\"" : "";
%>
                <tr>
                    <td class="table_01" <%= styleTotal %>><%= dbox.getString("d_seq").equals("99999") ? "*" : dbox.getString("d_seq") %></td>
                    <td class="table_01" <%= styleTotal %>><%= dbox.getString("d_areanm") %></td>
                    <td class="table_01" <%= styleTotal %>><%= dbox.getString("d_lecture_clsnm") %></td>
                    <td class="table_02_1" align="left;" <%= styleTotal %>><%= dbox.getString("d_lecnm") %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_jan")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_feb")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_mar")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_apr")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_may")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_jun")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_jul")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_aug")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_sep")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_oct")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_nov")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_dec")) %></td>
                    <td class="table_02_1" <%= styleTotal %>><%= new java.text.DecimalFormat("##,###,##0").format(dbox.getInt("d_tot_cnt")) %></td>
                </tr>
<%
            }
        } else {
%>
                <tr>
                    <td class="table_01" colspan="17">조회된 자료가 없습니다.</td>
                </tr>
<%
        }
%>
            </table>

<%
    }

    if( searchCondition.equals("daily") ) {
%>
            <table class="table_out" cellspacing="1" cellpadding="5" style="margin-left:15px;">
                <tr>
                    <td colspan="6" class="table_top_line"></td>
                </tr>
                <tr>
                    <td colspan="6"style="font-weight:bold; font-size:15pt; background-color:#abcdef;">일별 조회 건수</td>
                </tr>
                <tr>
                    <td class="table_title" width="5%" height="25"><b>seq</b></td>
                    <td class="table_title" width="10%"><b>구분1</b></td>
                    <td class="table_title" width="10%"><b>구분2</b></td>
                    <td class="table_title"><b>과정명</b></td>
                    <td class="table_title" width="10%"><b>날짜</b></td>
                    <td class="table_title" width="10%"><b>조회수</b></td>
                </tr>
<%
        String c_seq = "";
        String c_lecnm = "";
        String c_viewdate = "";
        String c_cnt = "";

        if ( viewCountList != null && viewCountList.size() > 0 ) {
            for(int i = 0; i < viewCountList.size(); i++) {

                DataBox cbox = (DataBox)viewCountList.get(i);
                c_seq = cbox.getString("d_seq");
                c_lecnm = cbox.getString("d_lecnm");
                c_viewdate = cbox.getString("d_viewdate");
                c_cnt = cbox.getString("d_cnt");
%>
                <tr>
                    <td class="table_01"><%=c_seq.equals("0") ? "*" : c_seq%></td>
                    <td <%=c_seq.equals("0") ? "class='table_02_5' style='font-weight:bold;'" : "class='table_02_1'"%>><%= cbox.getString("d_areanm") %></td>
                    <td <%=c_seq.equals("0") ? "class='table_02_5' style='font-weight:bold;'" : "class='table_02_1'"%>><%= cbox.getString("d_lecture_clsnm") %></td>
                    <td <%=c_seq.equals("0") ? "class='table_02_5' style='font-weight:bold;'" : "class='table_02_1'"%>><%=c_seq.equals("0") ? "합계" : c_lecnm%></td>
                    <td <%=c_seq.equals("0") ? "class='table_02_5' style='font-weight:bold;'" : "class='table_02_1'"%>><%=FormatDate.getFormatDate(c_viewdate,"yyyy/MM/dd")%></td>
                    <td <%=c_seq.equals("0") ? "class='table_02_5' style='font-weight:bold;'" : "class='table_02_1'"%>><%=c_cnt%></td>
                </tr>
<%
            }
        } else {
%>
                <tr>
                    <td class="table_01" colspan="6">조회된 자료가 없습니다.</td>
                </tr>
<%
        }
%>
            </table>

        </td>
    </tr>
</table>
<%
    }
%>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
