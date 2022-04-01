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

<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.FormatDate" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.PageUtil" %>
<%@ page import="com.credu.library.StringManager" %>

<jsp:useBean id="conf" class="com.credu.library.ConfigSet"  scope="page" />
<%

    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");

    //String  ss_grcode   = box.getString("s_grcode");            //교육그룹
    String  s_gadmin = box.getSession("gadmin");
    String  v_gadmin = StringManager.substring(s_gadmin, 0, 1);
    String  isAll = !v_gadmin.equals("A") ? "false" : "true";
    //String  v_selGroup  = box.getStringDefault("p_selGroup", "ALL");

    //int v_tabseq          = box.getInt("p_tabseq");

    int v_pageno = box.getInt("p_pageno");
    int v_pagesize = box.getInt("p_pagesize");
    v_pagesize = ( v_pagesize == 0 ) ? 20 : v_pagesize;
    int v_totalrowcount =  0;

    String v_searchtext = box.getString("p_searchtext");
    String v_search = box.getString("p_search");

    int v_seq = 0;              // 일련번호
    int v_cnt = 0;              // 조회수

    String v_lecnm = "";
    String v_toturnm = "";
    String v_vodurl = "";
    String v_tutorcareer = "";
    String v_lectime = "";
    String v_genre = "";
    String v_openyn = "";
    String v_userid = "";
    String v_name="";
    String v_indate = "";
    String v_viewcnt = "";
    String v_useyn = "";

    int v_dispnum = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    DataBox dbox = null;

    ArrayList<DataBox> list = (ArrayList<DataBox>)request.getAttribute("selectList");
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
    /*
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
    */


    /**
     * 목록에서 페이지 이동 - 사용하지 않는 것으로 추정됨
     */
    function go(index) {
         document.form1.p_pageno.value = index;
         document.form1.action = "/servlet/controller.infomation.GoldClassAdminServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }

    /**
     * 목록에서 페이지 이동
     */
    function goPage(pageNum) {
         document.form1.p_pageno.value = pageNum;
         document.form1.action = "/servlet/controller.infomation.GoldClassAdminServlet";
         document.form1.p_process.value = "selectList";
         document.form1.submit();
    }

    /**
     * 상세정보 화면으로 이동
     */
    function view(seq, pageNo) {
        document.form1.action = "/servlet/controller.infomation.GoldClassAdminServlet";
        document.form1.p_seq.value = seq;
        document.form1.p_pageno.value = pageNo;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

    /**
     * 등록 화면으로 이동
     */
    function insert() {
        document.form1.action = "/servlet/controller.infomation.GoldClassAdminServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }

    /**
     * 열린강좌 검색
     */
    function search() {
        document.form1.action = "/servlet/controller.infomation.GoldClassAdminServlet";
        document.form1.p_pageno.value = "";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    /**
     * 페이지 사이즈 설정
     */
    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.action = "/servlet/controller.infomation.GoldClassAdminServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    /**
     * 열린강좌 인기관리 팝업창을 연다.
     */
    function fnOpenClassHitfn() {

        var url = "/servlet/controller.infomation.GoldClassAdminServlet";
        var param = "?p_process=ManageOpenClassPage&p_hit_yn=Y&p_recom_yn=N";

        var new_Open = window.open(url + param, "openclassWindow", 'scrollbars=yes,width=1000,height=600,resizable=no');
    }

    /**
     * 열린강좌 추천관리 팝업창을 연다.
     */
    function fnOpenClassRecomfn() {

        var url = "/servlet/controller.infomation.GoldClassAdminServlet";
        var param = "?p_process=ManageOpenClassPage&p_hit_yn=N&p_recom_yn=Y";

        var new_Open = window.open(url + param, "openclassWindow", 'scrollbars=yes,width=1000,height=600,resizable=no');
    }
    
    //자막 파일로 추가
    function excelAdd() {
    	
    	var url = "/servlet/controller.infomation.GoldClassAdminServlet";
    	
        window.self.name = "winInsertPage";
        farwindow = window.open(url, "openWinAdd", "toolbar=no,location=no,directories=no,status=yes,menubar=no,scrollbars=yes,resizable=yes,copyhistory=no, width = 520, height = 220, top=0, left=0");
        document.form1.target = "openWinAdd"
        document.form1.p_process.value = "subtitleUpdatePage";
        document.form1.p_action.value = "go";
        document.form1.submit();

        farwindow.window.focus();
        document.form1.target = window.self.name;
    }

//-->
</script>
</head>

<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0">
<form name="form1" method="get">
    <input type="hidden" name="p_process" value="<%= v_process %>" />
    <input type="hidden" name="p_pageno" value="" />
    <input type="hidden" name="p_pagesize" value="<%=v_pagesize%>" />
    <input type="hidden" name="p_seq" value="" />
    <input type="hidden" name="p_hit_yn" value="" />
    <input type="hidden" name="p_recom_yn" value="" />
    <input type="hidden" name="p_action" value="" />


<table width="1000" border="0" cellspacing="0" cellpadding="0">
    <tr>
        <td align="center" valign="top">
            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
                <tr>
                    <td><img src="/images/admin/portal/s.1_19.gif" /></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" /></td>
                </tr>
            </table>
            <!-- title 끝 //-->

            <br />
            <!-- form 시작 //-->
            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td width="250">
                        <input type="button" value="인기관리" onclick="javascript:fnOpenClassHitfn();"/>
                        <input type="button" value="추천관리" onclick="javascript:fnOpenClassRecomfn();"/>
                    </td>
                    <td width="670" align="right">
                        <select name="p_search" >
                            <option value='lecnm'   <% if (v_search.equals("lecnm")) out.println("selected"); %>>강의명</option>
                            <option value='tutornm' <% if (v_search.equals("tutornm")) out.println("selected"); %>>강사명</option>
                        </select>
                        <input name="p_searchtext" type="text" class="input" value="<%=v_searchtext%>">
                    </td>

                    <td width="240" align="right" valign="middle">
                        <a href='javascript:search()'><img src="/images/admin/button/btn_inquiry.gif" border="0" /></a>
                        <a href='javascript:insert()'><img src="/images/admin/button/btn_add.gif" border="0" /></a>
                        <a href="javascript:excelAdd()"><img src="/images/admin/button/btn_fileplus.gif" alt="자막 파일로추가" /></a>
                    </td>
                </tr>
                <tr>
                    <td height="3"></td>
                </tr>
            </table>
            <!-- form 끝 //-->


            <!-- 열린강좌 관리 시작  //-->
            <table class="table_out" cellspacing="1" cellpadding="5">
                <tr>
                    <td colspan="7" class="table_top_line"></td>
                </tr>
                <tr>
                    <td class="table_title" width="5%" height="25"><b>NO</b></td>
                    <td class="table_title"><b>강의명</b></td>
                    <td class="table_title" width="10%"><b>강사명</b></td>
                    <td class="table_title" width="10%"><b>작성자</b></td>
                    <td class="table_title" width="10%"><b>등록일</b></td>
                    <td class="table_title" width="10%"><b>사용여부</b></td>
                    <td class="table_title" width="5%" ><b>조회수</b></td>
                </tr>
<%

            for(int i = 0; i < list.size(); i++) {

                dbox = (DataBox)list.get(i);

                v_seq = dbox.getInt("d_seq");
                v_lecnm = dbox.getString("d_lecnm");
                v_toturnm = dbox.getString("d_tutornm");
                v_vodurl = dbox.getString("d_vodurl");
                v_tutorcareer = dbox.getString("d_tutorcareer");
                v_lectime = dbox.getString("d_lectime");
                v_genre = dbox.getString("d_genre");
                v_openyn = dbox.getString("d_openyn");
                v_userid = dbox.getString("d_userid");
                v_name=dbox.getString("d_name");
                v_indate = dbox.getString("d_indate");
                v_viewcnt = dbox.getString("d_viewcnt");
                v_useyn = dbox.getString("d_useyn");

                v_dispnum = dbox.getInt("d_dispnum");
                v_totalpage = dbox.getInt("d_totalpage");
                v_totalrowcount = dbox.getInt("d_totalrowcount");
%>
                <tr>
                    <td class="table_01"><%= v_openyn.equals("Y") ? "*" : v_dispnum%></td>
                    <td <%=v_openyn.equals("Y") ? "class='table_02_6'" : "class='table_02_2'"%>><a href="javascript:view(<%=v_seq%>, <%= v_pageno %>)"><%=v_lecnm%></a></td>
                    <td <%=v_openyn.equals("Y") ? "class='table_02_5'" : "class='table_02_1'"%>><%=v_toturnm%></td>
                    <td <%=v_openyn.equals("Y") ? "class='table_02_5'" : "class='table_02_1'"%>><%=v_name%></td>
                    <td <%=v_openyn.equals("Y") ? "class='table_02_5'" : "class='table_02_1'"%>><%=FormatDate.getFormatDate(v_indate,"yyyy/MM/dd")%></td>
                    <td <%=v_openyn.equals("Y") ? "class='table_02_5'" : "class='table_02_1'"%>><%=v_useyn%></td>
                    <td <%=v_openyn.equals("Y") ? "class='table_02_5'" : "class='table_02_1'"%>><%=v_viewcnt%></td>
                </tr>
<%
        }
%>

            </table>
            <!-- 열린강좌 목록 끝  //-->

            <!-- total 시작 //-->
            <br />
            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="right" valign="absmiddle">
                        <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, 0, v_pagesize, v_totalrowcount) %>
                    </td>
                </tr>
            </table>
            <!-- total 끝 //-->

            <br />
        </td>
    </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
