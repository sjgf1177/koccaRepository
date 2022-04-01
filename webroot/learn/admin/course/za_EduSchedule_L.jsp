<%@ page contentType="text/html;charset=euc-kr" %>
<%@ page errorPage="/learn/library/error.jsp" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.text.DecimalFormat" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.credu.library.DataBox" %>
<%@ page import="com.credu.library.FormatDate" %>
<%@ page import="com.credu.library.PageUtil" %>
<%@ page import="com.credu.library.RequestBox" %>
<%@ page import="com.credu.library.RequestManager" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    if ( box == null ) {
        box = RequestManager.getBox(request);
    }

    String v_process = box.getString("p_process");

    int v_tabseq = box.getInt("p_tabseq");

    int v_pageno = box.getInt("p_pageno");
    int v_pagesize = box.getInt("p_pagesize");

    if (v_pageno == 0)  {
        v_pageno = 1;
    }

    int row = Integer.parseInt(conf.getProperty("page.bulletin.row"));

    if (v_pagesize == 0) {
        v_pagesize = 10;
    }

    int v_totalrowcount =  0;
    int v_dispnum = 0;           // 총게시물수
    int v_totalpage = 0;           // 게시물총페이지수

    String v_searchtext = box.getString("p_searchtext");
    String v_search = box.getString("p_search");

    GregorianCalendar calendar = new GregorianCalendar();
    DateFormat df = new SimpleDateFormat("yyyy");
    
    Date dt = new Date();
    String currentYear = df.format(dt);

    String v_year = box.getStringDefault("p_year", currentYear);
    String v_month = box.getStringDefault("p_month", "01");

    String ss_grcode = box.getStringDefault("s_grcode", "N000001");          //교육그룹
    String ss_gyear = box.getStringDefault("s_gyear", currentYear);           //년도
    String ss_grseq = box.getString("s_grseq");           //교육차수
    String ss_grseqnm = box.getString("s_grseqnm");         //교육차수명
    String ss_upperclass = box.getString("s_upperclass");      //과정대분류
    String ss_middleclass = box.getString("s_middleclass");     //과정중분류
    String ss_lowerclass = box.getString("s_lowerclass");      //과정소분류

    String v_subj = "";
    String v_subjnm = "";
    String v_edustart = "";
    String v_eduend = "";
    String v_propstart = "";
    String v_propend = "";
    String v_biyong = "";
    String v_studentlimit = "";
    String v_status = "";

    ArrayList list = (ArrayList)request.getAttribute("selectList");       // 공지리스트
    DecimalFormat dFormat = new DecimalFormat();
%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script type="text/javascript" src="/script/jquery-1.3.2.min.js"></script>
<!--link rel="stylesheet" type="text/css" href="/css/portal/base.css" /-->
<script type="text/javascript">
<!--

    function go(index) {
        document.form1.p_pageno.value = index;
        document.form1.action = "/servlet/controller.course.EduScheduleServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function goPage(pageNum) {
        document.form1.p_pageno.value = pageNum;
        document.form1.action = "/servlet/controller.course.EduScheduleServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function whenSelection(p_action) {
        document.form1.p_action.value = "go";
        document.form1.p_process.value = "selectList";
        document.form1.action = "/servlet/controller.course.EduScheduleServlet";
        document.form1.submit();
    }

    // 선택공지등록
    function insert() {
        document.form1.action = "/servlet/controller.course.EduScheduleServlet";
        document.form1.p_process.value = "insertPage";
        document.form1.submit();
    }

    // 상세보기
    function view(seq) {
        document.form1.action = "/servlet/controller.course.EduScheduleServlet";
        document.form1.p_seq.value      = seq;
        document.form1.p_process.value = "selectView";
        document.form1.submit();
    }

    function whenOrder(column) {
        if (document.form1.p_orderType.value == " asc") {
            document.form1.p_orderType.value = " desc";
        } else {
            document.form1.p_orderType.value = " asc";
        }

        document.form1.p_orderColumn.value = column;
        whenSelection("go");
    }

    function search() {
        document.form1.action = "/servlet/controller.course.EduScheduleServlet";
        document.form1.p_process.value = "selectList";
        document.form1.p_action.value = "go";
        document.form1.submit();
    }

    function pagesize(pageSize) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_pagesize.value = pageSize;
        document.form1.action = "/servlet/controller.course.EduScheduleServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }

    function changeMonth(month) {
        document.form1.target = "_self";
        document.form1.p_pageno.value = 1;
        document.form1.p_month.value = month;
        document.form1.action = "/servlet/controller.course.EduScheduleServlet";
        document.form1.p_process.value = "selectList";
        document.form1.submit();
    }


    $(function() {
        $("#oGrcode").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.grYearList&param=" + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetGrYear
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oGyear").bind("change", function(){
            var grcode = $("#oGrcode").val();
            var param = "type=sqlID&sqlID=selectBox.grSeqList&param=" + grcode + "," + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetGrSeq
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oUpperclass").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.subjAttMiddleList&param=" + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetMiddleClass
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });

        $("#oMiddleclass").bind("change", function(){
            var param = "type=sqlID&sqlID=selectBox.subjAttLowerList&param=" + $("#oUpperclass").val() + "," + $(this).val();
            $.ajaxSetup({cache:false});
            $.ajax({
                    type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnSetLowerClass
                ,   complete : function(arg1, arg2) {
                        // alert("complete : " + arg1);
                    }
                ,   error :  function(arg1, arg2) {
                        // alert("error : " + arg1);
                    }
            });
        });


    });

    /**
     * 교육 그룹별 연도 selectbox 지정
     */
    function fnSetGrYear( result ) {
        $("#oGyear").empty();
        $("#oGrseq").empty();
        $("#oGyear").append("<option value=\"\">-- 선택 --</option>");
        $("#oGrseq").append("<option value=\"\">-- 전체 --</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oGyear").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

    /**
     * 교육 그룹 연도별 교육차수 selectbox 지정
     */
    function fnSetGrSeq( result ) {
        $("#oGrseq").empty();
        $("#oGrseq").append("<option value=\"\">-- 전체 --</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oGrseq").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });
        }
    }

    /**
     * 검색조건에서 대분류 선택시 callback function
     */
    function fnSetMiddleClass( result ) {
        $("#oMiddleclass").empty();
        $("#oMiddleclass").append("<option value=\"\">-- 전체 --</option>");
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">-- 전체 --</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oMiddleclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

    /**
     * 검색조건에서 중분류 선택시 callback function
     */
    function fnSetLowerClass( result ) {
        $("#oLowerclass").empty();
        $("#oLowerclass").append("<option value=\"\">-- 전체 --</option>");

        if ( result.selectBoxList.length > 0 ) {
            $.each( result.selectBoxList, function() {
                $("#oLowerclass").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
            });

        }
    }

//-->
</script>
</head>

<body bgcolor="#ffffff" text="#000000" topmargin="0" leftmargin="0"  bgcolor="white">
<form name="form1" method="post" action="">
    <input type="hidden" name="p_process" />
    <input type="hidden" name="p_action" />
    <input type="hidden" name="p_pagesize" value="<%=v_pagesize%>" />
    <input type="hidden" name="p_pageno"   value="<%=v_pageno %>" />
    <input type="hidden" name="p_tabseq" value=<%=v_tabseq %> />
    <input type="hidden" name="p_seq" value="" / >
    <input type="hidden" name="p_year" value="<%=v_year %>" />
    <input type="hidden" name="p_month" value="<%= v_month %>" />


<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663"  bgcolor="white">
    <tr>
        <td align="center" valign="top" bgcolor="white">

            <!-- title 시작 //-->
            <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title" bgcolor="white">
                <tr>
                    <td><img src="/images/admin/portal/s.1_34.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!-- title 끝 //-->
            <br />
            <!-- 검색 form 시작 //-->
            <table cellspacing="0" cellpadding="1" class="form_table_out">
                <tr>
                    <td bgcolor="#C6C6C6" align="center">
                        <table cellspacing="0" cellpadding="4" class="form_table_bg" >
                            <colgroup>
                                <col style="width:8%;" />
                                <col style="width:27%;" />
                                <col style="width:8%;" />
                                <col style="width:14%;" />
                                <col style="width:8%;" />
                                <col style="width:27%;" />
                                <col style="width:8%;" />
                            </colgroup>
                            <tr>
                                <td width="7%" class="_tdT">교육그룹</td>
                                <td width="15%" class="_tdS">
                                    <kocca:selectBox name="s_grcode" id="oGrcode" optionTitle="-- 전체 --" type="sqlID" sqlID="selectBox.grcodeList" selectedValue="<%= ss_grcode %>" isLoad="true" />
                                </td>
                                <td width="7%" class="_tdT">연도</td>
                                <td width="12%" class="_tdS">
<%
    if ( !ss_gyear.equals("") ) {
%>
                                    <kocca:selectBox name="s_gyear" id="oGyear" optionTitle="-- 선택 --" type="sqlID" param="<%= ss_grcode %>" sqlID="selectBox.grYearList" selectedValue="<%= ss_gyear %>" isLoad="true" />
<%
    } else {
%>
                                    <select name="s_gyear" id="oGyear">
                                        <option value="">-- 선택 --</option>
                                    </select>
<%
    }
%>
                                </td>
                                <td width="7%" class="_tdT">교육차수</td>
                                <td width="27%" class="_tdS">
<%
    if ( !ss_grseq.equals("") ) {
%>
                                    <kocca:selectBox name="s_grseq" id="oGrseq" optionTitle="-- 전체 --" type="sqlID" param="<%= ss_grcode + ',' + ss_gyear %>" sqlID="selectBox.grSeqList" selectedValue="<%= ss_grseq %>" isLoad="true" />
<%
    } else {
%>
                                    <select name="s_grseq" id="oGrseq">
                                        <option value="">-- 전체 --</option>
                                    </select>
<%
    }
%>

                                </td>
                                <td rowspan="2" align="center" valign="middle">
                                    <a href="javascript:search();"><img src="/images/admin/button/btn_inquiry.gif" border="0"></a>
                                </td>
                            </tr>
                            <tr>
                                <td class="_tdT">대분류</td>
                                <td class="_tdS">
                                    <kocca:selectBox name="s_upperclass" id="oUpperclass" optionTitle="-- 전체 --" type="sqlID" sqlID="selectBox.subjAttUpperList" selectedValue="<%= ss_upperclass %>" isLoad="true" />
                                </td>
                                <td class="_tdT">중분류</td>
                                <td class="_tdS">
<%
    if ( !ss_middleclass.equals("") ) {
%>
                                    <kocca:selectBox  name="s_middleclass" id="oMiddleclass" optionTitle="-- 전체 --" type="sqlID" sqlID="selectBox.subjAttMiddleList" param="<%= ss_upperclass %>" selectedValue="<%= ss_middleclass %>" isLoad="true" />
<%
    } else {
%>
                                    <select name="s_middleclass" id="oMiddleclass">
                                        <option value="">-- 전체 --</option>
                                    </select>
<%
    }
%>
                                </td>
                                <td class="_tdT">소분류</td>
                                <td class="_tdS">
<%
    if ( !ss_lowerclass.equals("") ) {
%>
                                    <kocca:selectBox name="s_lowerclass" id="oLowerclass" optionTitle="-- 전체 --" type="sqlID" sqlID="selectBox.subjAttLowerList" param="<%= ss_upperclass + ',' + ss_middleclass %>" selectedValue="<%= ss_lowerclass %>" isLoad="true" />
<%
    } else {
%>
                                    <select name="s_lowerclass" id="oLowerclass">
                                        <option value="">-- 전체 --</option>
                                    </select>
<%
    }
%>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <!-- 검색 form 끝 //-->
            <br/>

            <table width="97%" border="0" cellspacing="1" cellpadding="5">
                <tr>
                    <td colspan="13" height="1" bgcolor="#0075ad"></td>
                </tr>
                <tr>
                    <td class="table_title" height="25"><b><%= ss_gyear %>년    </b></td>
                    <td class='table_02_1' onclick="changeMonth('01');" <%= v_month.equals("01") ? "style='background-color:#8C939F;'" : "" %> style="cursor:pointer;">1월 </td>
                    <td class='table_02_1' onclick="changeMonth('02');" <%= v_month.equals("02") ? "style='background-color:#8C939F;'" : "" %> style="cursor:pointer;">2월 </td>
                    <td class='table_02_1' onclick="changeMonth('03');" <%= v_month.equals("03") ? "style='background-color:#8C939F;'" : "" %> style="cursor:pointer;">3월  </td>
                    <td class='table_02_1' onclick="changeMonth('04');" <%= v_month.equals("04") ? "style='background-color:#8C939F;'" : "" %> style="cursor:pointer;">4월  </td>
                    <td class='table_02_1' onclick="changeMonth('05');" <%= v_month.equals("05") ? "style='background-color:#8C939F;'" : "" %> style="cursor:pointer;">5월  </td>
                    <td class='table_02_1' onclick="changeMonth('06');" <%= v_month.equals("06") ? "style='background-color:#8C939F;'" : "" %> style="cursor:pointer;">6월 </td>
                    <td class='table_02_1' onclick="changeMonth('07');" <%= v_month.equals("07") ? "style='background-color:#8C939F;'" : "" %> style="cursor:pointer;">7월  </td>
                    <td class='table_02_1' onclick="changeMonth('08');" <%= v_month.equals("08") ? "style='background-color:#8C939F;'" : "" %> style="cursor:pointer;">8월 </td>
                    <td class='table_02_1' onclick="changeMonth('09');" <%= v_month.equals("09") ? "style='background-color:#8C939F;'" : "" %> style="cursor:pointer;">9월  </td>
                    <td class='table_02_1' onclick="changeMonth('10');" <%= v_month.equals("10") ? "style='background-color:#8C939F;'" : "" %> style="cursor:pointer;">10월 </td>
                    <td class='table_02_1' onclick="changeMonth('11');" <%= v_month.equals("11") ? "style='background-color:#8C939F;'" : "" %> style="cursor:pointer;">11월 </td>
                    <td class='table_02_1' onclick="changeMonth('12');" <%= v_month.equals("12") ? "style='background-color:#8C939F;'" : "" %> style="cursor:pointer;">12월 </td>
                </tr>
                <tr>
                    <td colspan="13" height="1" bgcolor="#0075AD"></td>
                </tr>
            </table>
            <br/>

            <!-- 목록 시작 //-->
            <table class="table_out" cellspacing="1" cellpadding="5">
                <tr>
                    <td class="table_top_line" colspan="6" ></td>
                </tr>
                <tr>
                    <td class="table_title" width="35%" height="25"><b>교육과정명</b></td>
                    <td class="table_title" width="20%"><b>교육기간</b></td>
                    <td class="table_title" width="20%"><b>수강신청기간</b></td>
                    <td class="table_title" width="10%"><b>수강료</b></td>
                    <td class="table_title" width="8%"><b>정원</b></td>
                    <td class="table_title" width="7%"><b>수강신청</b></td>
                </tr>
<%
        for(int i = 0; i < list.size(); i++) {
            DataBox dbox = (DataBox)list.get(i);

            v_subj = dbox.getString("d_subj");
            v_subjnm = dbox.getString("d_subjnm");
            v_edustart = dbox.getString("d_edustart");
            v_eduend = dbox.getString("d_eduend");
            v_propstart = dbox.getString("d_propstart");
            v_propend = dbox.getString("d_propend");
            v_biyong = dbox.getString("d_biyong");
            v_studentlimit = dbox.getString("d_studentlimit");
            v_status = dbox.getString("d_status");

            v_biyong    = dFormat.format(Integer.parseInt(v_biyong));

            if(v_status.equals("Y")) {
                v_status = "진행";
            } else {
                v_status = "마감";
            }

            v_dispnum = dbox.getInt("d_dispnum");
            v_totalpage = dbox.getInt("d_totalpage");
            v_totalrowcount = dbox.getInt("d_totalrowcount");
%>
                <tr>
                    <td class="table_02_1" height="25"><%= v_subjnm%></td>
                    <td class="table_02_1"><%=FormatDate.getFormatDate(v_edustart, "yyyy/MM/dd")+"-"+FormatDate.getFormatDate(v_eduend, "yyyy/MM/dd")%></td>
                    <td class="table_02_1"><%=FormatDate.getFormatDate(v_propstart, "yyyy/MM/dd")+"-"+FormatDate.getFormatDate(v_eduend, "yyyy/MM/dd")%></td>
                    <td class="table_02_1"><%= v_biyong%></td>
                    <td class="table_02_1"><%= v_studentlimit %> </td>
                    <td class="table_02_1"><%=v_status%> </td>
                </tr>
<%
        }

        if(list.size() == 0){ %>
                <tr>
                    <td class="table_02_1" colspan="6">등록된 내용이 없습니다</td>
                </tr>
<%  } %>
            </table>
            <!-- 목록 끝 //-->
            <br />
            <!-- paging 시작 //-->
            <br />
            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="right" valign="absmiddle">
                        <%= PageUtil.printPageSizeList(v_totalpage, v_pageno, row, v_pagesize, v_totalrowcount) %>
                    </td>
                </tr>
            </table>
            <!-- paging 끝 //-->
            <br />
        </td>
    </tr>
</table>
<%@ include file = "/learn/library/getJspName.jsp" %>
</form>
</body>
</html>
