<%
//**********************************************************
//  1. 제      목 : 검색어 통계 조회
//  2. 프로그램명 : za_SearchKeyword_L.jsp
//  3. 개      요 : 기간별 상위 검색어 목록을 조회한다.
//  4. 환      경 : JDK 1.3
//  5. 버      젼 : 1.0
//  6. 작      성 : 2014-04-18
//  7. 수      정 :
//***********************************************************
%>
<%@page contentType="text/html;charset=euc-kr" %>
<%@page errorPage="/learn/library/error.jsp" %>

<%@page import="java.util.*" %>
<%@page import="com.credu.homepage.*" %>
<%@page import="com.credu.library.*" %>
<%@page import="com.credu.common.*" %>
<%@page import="com.credu.course.*" %>
<%@page import="com.credu.system.*" %>
<%@page import="com.credu.scorm.DateUtil" %>

<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String processType = box.getString("processType");
    String searchStartDate = box.getString("searchStartDate");
    String searchEndDate = box.getString("searchEndDate");

    if ( searchStartDate != null && !searchStartDate.equals("")) {
        searchStartDate = searchStartDate.substring(0, 4) + "/" + searchStartDate.substring(4, 6) + "/" + searchStartDate.substring(6, 8);
    }
    if ( searchEndDate != null && !searchEndDate.equals(""))  {
        searchEndDate =  searchEndDate.substring(0, 4) + "/" + searchEndDate.substring(4, 6) + "/" + searchEndDate.substring(6, 8);
    }

    String searchKeyword = "";
    String rnk = "", cnt = "";
    int sort_seq = 0;
    String Keyword = "";

    processType = (processType == null || processType.equals("")) ? "selectSearchWordList" : processType;

    int v_dispnum = 0;      // 총게시물수
    int v_totalpage = 0;    // 게시물총페이지수

    ArrayList list = (ArrayList)request.getAttribute("searchKeywordList");
    ArrayList keylist = (ArrayList)request.getAttribute("selectKeywordList");

    int pageNo = box.getInt("pageNo");
    int pageSize = box.getInt("pageSize");
    int totalRowCnt = 0;
    int totalPage = 0;
%>
<!doctype html>
<html lang="ko">
<head>
<title>검색어 통계 | 분석/통계 | 한국콘텐츠아카데미 관리자</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/CSS" href="/css/jquery-ui-1.10.4.css">
<script type="text/javascript" src = "/script/cresys_lib.js"></script>
<script type="text/javascript" src="/js/jquery/1.8.3/jquery.js"></script>
<script type="text/javascript" src="/js/jqueryui/1.10.0/jquery-ui.js"></script>
<SCRIPT type="text/javascript">
<!--

    function fnSearchKeywordList() {
        if ( $("#oSearchStartDate").val() == "" ) {
            alert("시작일자를 선택하세요.");
            $("#oSearchStartDate").focus();
            return false;
        }

        if ( $("#oSearchEndDate").val() == "" ) {
            alert("시작일자를 선택하세요.");
            $("#oSearchEndDate").focus();
            return false;
        }

        // $("#oSearchForm").action = "";
        $("#oSearchForm").submit();
    }

    /*
    function fnSearchKeywordDetail( keyword ) {
        $.ajax(
            type:   "POST",
            url:    "/servlet/controller.homepage.MainStatusticServlet";
        );
    }
    */

    function go(index) {
        $("#oPageNo").val(index);
        $("#oProcessType").val("selectSearchWordList");
        $("#oSearchForm").attr("action", "/servlet/controller.homepage.SearchWordAdminServlet").attr("target", "_self").submit();
    }

    function goPage(pageNo) {
        $("#oPageNo").val(pageNo);
        $("#oProcessType").val("selectSearchWordList");
        $("#oSearchForm").attr("action", "/servlet/controller.homepage.SearchWordAdminServlet").attr("target", "_self").submit();
    }

    function pagesize(pageSize) {
        $("#oPageNo").val(1);
        $("#oPageSize").val( pageSize );
        $("#oProcessType").val("selectSearchWordList");
        $("#oSearchForm").attr("action", "/servlet/controller.homepage.SearchWordAdminServlet").attr("target", "_self").submit();
    }

    $(document).ready( function() {

        $(document).title = "검색어 통계 | 통계 | 한국콘텐츠아카데미 관리자";

        // 달력 초기화
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
            yearRange: "-15:", // 년도 선택 셀렉트박스를 현재 년도에서 이전, 이후로 얼마의 범위를 표시할것인가.
            showButtonPanel: true, // 캘린더 하단에 버튼 패널을 표시한다.
            currentText: "오늘" , // 오늘 날짜로 이동하는 버튼 패널
            closeText: "닫기",  // 닫기 버튼 패널
            dateFormat: "yy/mm/dd", // 텍스트 필드에 입력되는 날짜 형식.
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
            dateFormat: "yy/mm/dd", // 텍스트 필드에 입력되는 날짜 형식.
            showAnim: "slideDown", //애니메이션을 적용한다.
            showMonthAfterYear: true , // 월, 년순의 셀렉트 박스를 년,월 순으로 바꿔준다.
            dayNamesMin: ["월", "화", "수", "목", "금", "토", "일"], // 요일의 한글 형식.
            monthNamesShort: ["1월","2월","3월","4월","5월","6월","7월","8월","9월","10월","11월","12월"], // 월의 한글 형식.

            onClose: function( selectedDate ) {
                $("#oSearchStartDate" ).datepicker( "option", "maxDate", selectedDate );
            }
        });

    });

    function fnAppend(act, obj, s_key){
        var keytable =$("#oKeytable tbody"), lastrow = keytable.find("tr:last-child");
        var tr = $(obj).parent().parent();
        var rcnt = $("#oKeytable tbody tr").length - 1;

        var list ="";

        if(act == "add"){
            if( rcnt == 0 ){
                list = "<tr>";
                list += "    <td class=\"table_01\">1</td>";
                list += "    <td style=\"background-color:#ffffff;\"><input type=\"text\" id=\"os_keyword\" name = \"s_keyword\"  value=\"\" /></td>";
                list += "    <td style=\"background-color:#ffffff; text-align:center;\"><input type=\"button\" onclick=\"javascript:fnAppend('remove',this);\" value=\"제거\"/></td>";
                list += "    <td style=\"background-color:#ffffff; text-align:center;\"><a href=\"javascript:fnChangeVal(1, 'up');\">▲</a>&nbsp;&nbsp;<a href=\"javascript:fnChangeVal(1, 'down');\">▼</a></td>";
                list += "</tr>";
                keytable.append(list);
                $("#oKeytable tr:last-child td:eq(1) :input").val(s_key);
                $("#oKeytable tr:last-child td:eq(0)").text(rcnt+1);
            // } else if (rcnt >= 7){
            //     alert("검색어는 7개 이상 등록할 수 없습니다.");
            //     return;
            } else {

                for(var i = 0; i <rcnt; i++){
                    var i_key = $("#oKeytable input[name='s_keyword']").eq(i).val();
                    if(i_key == s_key){
                        alert("검색어가 중복되었습니다.");
                        return;
                    }
                }

                keytable.append(lastrow.clone());
                $("#oKeytable tr:last-child td:eq(1) :input").val(s_key);
                $("#oKeytable tr:last-child td:eq(0)").text(rcnt+1);

                $("#oKeytable tr:last-child td:eq(3)").html("<a href=\"javascript:fnChangeVal(" + (rcnt+1) + ", 'up');\">▲</a>&nbsp;&nbsp;<a href=\"javascript:fnChangeVal("+ (rcnt+1) +", 'down');\">▼</a>");

            }
        } else {
            var currIdx = $(tr).find("td").eq(0).text();

            tr.remove();

            for( var k = currIdx ; k <= rcnt ; k++ ) {
                var tempObj = $("#oKeytable tr").eq(k);
                tempObj.find("td").eq(0).text(k);
                tempObj.find("td").eq(3).html("<a href=\"javascript:fnChangeVal(" + (k) + ", 'up');\">▲</a>&nbsp;&nbsp;<a href=\"javascript:fnChangeVal("+ (k) +", 'down');\">▼</a>");
            }
        }

    }

    function fnChangeVal(val, act){
        var rcnt =$("#oKeytable tbody tr").length - 1;
        $("#oKeytable tr:last-child td:eq(1) :input").val();

        var tempValue = "";
        var orgValue = $("#oKeytable input[name='s_keyword']").eq(val - 1).val();

        var maxLength = $("#oKeytable input[name='s_keyword']").length;

        if ( val > 1 && act == "up") {
            tempValue = $("#oKeytable input[name='s_keyword']").eq(val - 2).val();
            $("#oKeytable input[name='s_keyword']").eq(val - 2).val( orgValue );
            $("#oKeytable input[name='s_keyword']").eq(val - 1).val( tempValue );
        } else if ( val < maxLength && act == "down" ) {
            tempValue = $("#oKeytable input[name='s_keyword']").eq(val).val();
            $("#oKeytable input[name='s_keyword']").eq(val - 1).val( tempValue );
            $("#oKeytable input[name='s_keyword']").eq(val).val( orgValue );
        }
    }

    function fnkeywordReg() {
        if ($("#oKeytable input[name='s_keyword']").length > 0 ){
            if (confirm('정말 등록 하시겠습니까?')) {
                document.keywordRegform.action = "/servlet/controller.homepage.SearchWordAdminServlet";
                document.keywordRegform.processType.value="insertKeywordList";
                document.keywordRegform.submit();
            }
        }else{
            alert("노출 될 키워드를 등록하세요.");
            return;
        }

    }


//-->
</SCRIPT>
</head>

<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">

<table width="1000" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center" valign="top" style="background-color:#ffffff;" colspan="2">
            <!----------------- title 시작 ----------------->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td align="left"><h2>검색어 관리</h2></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!----------------- title 끝 ----------------->
            <br>
            <!----------------- form 시작 ----------------->

            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0" >
                <tr>
                    <td align="center" style="padding:1px; background-color: #bebebe;">
                        <!-- 조건검색 시작 //-->
                        <table cellspacing="1" cellpadding="4" width="100%" style="border-style:none solid; background-color: #eef1f2;">
                            <tr>
                                <td>
                                    <form name="searchForm" id="oSearchForm" method="get">
                                        <input type="hidden" id="oProcessType" name="processType" value="<%= processType %>" />
                                        <input type="hidden" id="oPageNo" name="pageNo" value="" />
                                        <input type="hidden" id="oPageSize" name="pageSize" value="<%= pageSize %>" />
                                        <input type="hidden" id="oSeq" name="seq" value="" />

                                    <table border="0" cellspacing="0" cellpadding="0" width="99%" class="">
                                        <colgroup>
                                            <col width="8%"/>
                                            <col width="25%"/>
                                            <col width="*"/>
                                        </colgroup>
                                        <tr>
                                            <th style="text-align:left; padding-left:8px;">검색기간</td>
                                            <td style="text-align:left; padding-left:8px;">
                                                <input type="text" id="oSearchStartDate" name="searchStartDate" value="<%= searchStartDate %>" class="input" maxlength="10" size="10" /> ~
                                                <input type="text" id="oSearchEndDate" name="searchEndDate" value="<%= searchEndDate %>" class="input" maxlength="10" size="10" />
                                            </td>
                                            <td align="left">
                                                <a href="javascript:fnSearchKeywordList()"><img src="/images/admin/button/b_go.gif" border="0" align="absmiddle" alt="조회" /></a>
                                            </td>
                                        </tr>
                                    </table>
                                    </form>

                                </td>
                            </tr>
                        </table>
                        <!-- 조건검색 끝 //-->
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td width="460" style="padding-top:8px; padding-left:14px; vertical-align:top; background-color:#ffffff;">

            <!----------------- 추가 버튼 시작
            <table cellpadding="0" cellspacing="0" class="table1">
                <tr>
                    <td align="right" height="20">
                        <a href="javascript:InsertSubjPage()"><img src="/images/admin/button/btn_cyberplus.gif" border="0"></a>
                    </td>
                </tr>
                <tr>
                    <td height="3"></td>
                </tr>
            </table>
            추가 버튼 끝 ----------------->

            <!-- 검색어 관리 시작 //-->
            <table cellspacing="1" cellpadding="5" border="0" style="border-style: none;background-color: #76ABE0; ">
                <colgroup>
                    <col width="60" />
                    <col width="240" />
                    <col width="80" />
                    <col width="80" />
                </colgroup>
                <tr>
                    <td class="table_title">순위</td>
                    <td class="table_title">검색어</td>
                    <td class="table_title">검색건수</td>
                    <td class="table_title">관리</td>
                </tr>
<%
        if ( list.size() > 0 ) {
            DataBox dbox = null;
            for(int i = 0; i < list.size(); i++) {

                dbox = (DataBox)list.get(i);

                rnk = dbox.getString("d_rnk");
                searchKeyword = dbox.getString("d_search_word");
                cnt = dbox.getString("d_cnt");

                if ( i == list.size() - 1 ) {
                    totalRowCnt = dbox.getInt("totalRowCnt");
                    totalPage = dbox.getInt("totalPage");
                }
%>
            <tr>
                <td class="table_01"><%= rnk %></td>
                <td style="background-color:#ffffff;"><%= searchKeyword %></td>
                <td style="background-color:#ffffff; text-align:center;"><%= cnt %></td>
                <td style="background-color:#ffffff; text-align:center;"><input type="button" onclick="javascript:fnAppend('add',this,'<%= searchKeyword %>');" value="추가"/></td>
            </tr>
<%
            }
        } else {
%>
            <tr>
                <td class="table_01" colspan="3">조회된 목록이 없습니다.</td>
            </tr>
<%
        }
%>

            </table>
            <!-- 검색어 관리 끝 //-->

            <br />

            <table width="460" cellspacing="1" cellpadding="2" border="0">
                <tr>
                    <td align="center" width="300">
                        <%= PageUtil.printPageList(totalPage, pageNo, pageSize) %>
                    </td>
                    <td align="right">
                        총 <%= totalRowCnt %>건
                        <select id="oSelectPageSize" name="selectPageSize" onchange="pagesize(this.value);">
                            <option value="10" <%= (pageSize == 10) ? "selected='selected'" : "" %>>10개씩 보기</option>
                            <option value="20" <%= (pageSize == 20) ? "selected='selected'" : "" %>>20개씩 보기</option>
                            <option value="30" <%= (pageSize == 30) ? "selected='selected'" : "" %>>30개씩 보기</option>
                            <option value="50" <%= (pageSize == 50) ? "selected='selected'" : "" %>>50개씩 보기</option>
                            <option value="100" <%= (pageSize == 100) ? "selected='selected'" : "" %>>100개씩 보기</option>
                        </select>
                    </td>
                </tr>
            </table>

        </td>
        <td width="460" style="padding-top:8px; vertical-align:top; background-color:#ffffff;">

            <form id="oKeywordform" name="keywordRegform" method="post">
                <input type="hidden" id="oProcessType" name="processType" value="" />
                <input type="hidden" id="oSearchStartDate" name="searchStartDate" value="<%= searchStartDate %>" />
                <input type="hidden" id="oSearchEndDate"   name="searchEndDate" value="<%= searchEndDate %>" />
                <table id="oKeytable" cellspacing="1" cellpadding="5" border="0" style="border-style: none;background-color: #76ABE0; ">
                    <colgroup>
                        <col width="60" />
                        <col width="240" />
                        <col width="80" />
                        <col width="80" />
                    </colgroup>
                    <tr>
                        <td class="table_title">순서</td>
                        <td class="table_title">노출 검색어</td>
                        <td colspan="2" class="table_title">관리</td>
                    </tr>
<%
        if ( keylist !=null && keylist.size() > 0 ) {
            DataBox kdbox = null;

            for(int i = 0; i < keylist.size(); i++) {

                kdbox = (DataBox)keylist.get(i);

                sort_seq = kdbox.getInt("d_sort_seq");
                Keyword = kdbox.getString("d_keyword");

%>
                    <tr>
                        <td class="table_01"><%=sort_seq%></td>
                        <td style="background-color:#ffffff;"><input type="text" id="os_keyword" name = "s_keyword" value="<%=Keyword%>" /></td>
                        <td style="background-color:#ffffff; text-align:center;"><input type="button" onclick="javascript:fnAppend('remove',this, '');" value="제거"/></td>
                        <td style="background-color:#ffffff; text-align:center;">
                            <a href="javascript:fnChangeVal(<%=sort_seq%>, 'up');">▲</a>&nbsp;&nbsp;<a href="javascript:fnChangeVal(<%=sort_seq%>, 'down');">▼</a>
                        </td>
                    </tr>
<%
            }
        }
%>

                </table>
            </form>
        </td>
    </tr>
    <tr>
        <td colspan="2"></td>
    </tr>
    <tr>
        <td colspan="2" align="center">
            <input type="button" onclick="javascript:fnkeywordReg();" value="적용" />
        </td>
    </tr>
</table>


<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>

