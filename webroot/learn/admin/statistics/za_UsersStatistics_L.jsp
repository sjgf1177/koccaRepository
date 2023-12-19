<%
    //**********************************************************
//  1. 제      목: 종합 통계(회원)
//  2. 프로그램명: za_UsersStatistics_L.jsp
//  3. 개      요: 회원 통계 조회
//  4. 환      경: JDK 1.7
//  5. 버      젼: 1.0
//  6. 작      성: 2022.09.28
//**********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.library.*" %>

<%@ taglib uri="/tags/KoccaTaglib" prefix="kocca" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String v_edu_type = box.getString("p_edu_type");
    String v_grcode = box.getString("p_grcode");
    String v_sdt = box.getString("p_sdt");
    String v_edt = box.getString("p_edt");
    String v_sex = "";
    String v_sex_all = box.getString("p_sex_all");
    String[] sexArr = box.getStringArray("p_sex");
    String v_age = "";
    String v_age_all = box.getString("p_age_all");
    String[] ageArr = box.getStringArray("p_age");
    String v_loc = "";
    String v_loc_all = box.getString("p_loc_all");
    String[] locArr = box.getStringArray("p_loc");
    String v_job = "";
    String v_job_all = box.getString("p_job_all");
    String[] jobArr = box.getStringArray("p_job");
    String v_state = box.getString("p_state");
    String v_sort = box.getString("p_sort");

    if(sexArr != null) {
        for (int z = 0; z < sexArr.length; z++) {
            v_sex += sexArr[z];
        }
    }

    if(ageArr != null) {
        for (int z = 0; z < ageArr.length; z++) {
            if(v_age.equals("")) {
                v_age = SQLString.Format(ageArr[z]);
            } else {
                v_age += "," + SQLString.Format(ageArr[z]);
            }
        }
    }

    if(locArr != null) {
        for (int z = 0; z < locArr.length; z++) {
            if(v_loc.equals("")) {
                v_loc = SQLString.Format(locArr[z]);
            } else {
                v_loc += "," + SQLString.Format(locArr[z]);
            }
        }
    }

    if(jobArr != null) {
        for (int z = 0; z < jobArr.length; z++) {
            if(v_job.equals("")) {
                v_job = SQLString.Format(jobArr[z]);
            } else {
                v_job += "," + SQLString.Format(jobArr[z]);
            }
        }
    }

    String v_today =  FormatDate.getDate("yyyy-MM-dd");

    if(v_sdt.length() < 1) {
        v_sdt = v_today;
    }

    if(v_edt.length() < 1) {
        v_edt = v_today;
    }

    String ss_action = box.getString("p_action");

    int v_pageno = box.getInt("p_pageno");
    int v_pagesize = box.getInt("p_pagesize");
    int v_total	= 0;
    int i = 0;
    int v_totalpage = 0;
    int v_totalrowcount = 0;

    ArrayList list = null;
    list = (ArrayList)request.getAttribute("selectUsersStatisticList");

    ArrayList gr_list = null;
    gr_list = (ArrayList)request.getAttribute("selectGrcodeList");

    ArrayList cd_list = null;
    cd_list = (ArrayList)request.getAttribute("selectListCode");

    ArrayList job_list = null;
    job_list = (ArrayList)request.getAttribute("selectJobList");

    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    if (v_pagesize == 0)  v_pagesize = 100;
%>
<html>
<head>
    <title>종합통계(회원)</title>
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link rel="stylesheet" href="/css/admin_style.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
    <link rel="stylesheet" type="text/css" href="/css/2023/admin.css"> <!-- 커스텀 css 추가-->
    <script language = "javascript" src = "/script/cresys_lib.js"></script>
    <script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
    <script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
    <script type="text/javascript" src="/script/effects.core.js"></script>
    <script type="text/javascript" src="/script/effects.blind.js"></script>
    <script type="text/javascript" src="/script/effects.drop.js"></script>
    <script type="text/javascript" src="/script/effects.explode.js"></script>
    <script type="text/javascript" src="/script/ui.datepicker.js"></script>

    <script language="JavaScript">
        //레이어창 열기
        function fnlayerpopup(){
            $(this).click(function (){
                //$('html, body').css("overflow","hidden");
                $('.sub_layer_statistics, .opacity_layer_bg01').addClass('on');
            });
        }

        // 레이어창 닫기
        function layerClose(){
            $(this).click(function (){
                //$('html, body').css('overflow','auto');
                $('.sub_layer_statistics, .opacity_layer_bg01').removeClass('on');
            });
        }

        //<!--
        // 조회 검색
        function whenSelection(p_action) {
            if($("#p_sdt").val() == "") {
                alert("시작 일자를 입력해 주세요.");
                $("#p_sdt").focus();
                return;
            }

            if($("#p_edt").val() == "") {
                alert("종료 일자를 입력해 주세요.");
                $("#p_edt").focus();
                return;
            }

            var st_dt = $("#p_sdt").val().replaceAll("-","");
            var ed_dt = $("#p_edt").val().replaceAll("-","");

            if(st_dt > ed_dt){
                alert("시작일이 종료일보다 큽니다.");
                return;
            }

            $("#loding-box").show();
            document.form1.target = "_self";
            document.form1.p_action.value = p_action;
            document.form1.p_process.value = 'selectUsersStatisticList';
            document.form1.p_sort.value = $("#sort").val();
            document.form1.submit();
        }

        // 페이지 이동
        function goPage(pageNum) {
            if($("#p_sdt").val() == "") {
                alert("시작 일자를 입력해 주세요.");
                $("#p_sdt").focus();
                return;
            }

            if($("#p_edt").val() == "") {
                alert("종료 일자를 입력해 주세요.");
                $("#p_edt").focus();
                return;
            }

            var st_dt = $("#p_sdt").val().replaceAll("-","");
            var ed_dt = $("#p_edt").val().replaceAll("-","");

            if(st_dt > ed_dt){
                alert("시작일이 종료일보다 큽니다.");
                return;
            }

            $("#loding-box").show();
            document.form1.target = "_self";
            document.form1.p_pageno.value = pageNum;
            document.form1.p_action.value = "go";
            document.form1.action = '/servlet/controller.statistics.SynthesizeStatisticServlet';
            document.form1.p_process.value = "selectUsersStatisticList";
            document.form1.p_sort.value = $("#sort").val();
            document.form1.submit();
        }

        function pagesize(pageSize) {
            document.form1.target = "_self";
            document.form1.p_pageno.value = 1;
            document.form1.p_pagesize.value = pageSize;
            document.form1.p_action.value = "go";
            document.form1.action = '/servlet/controller.statistics.SynthesizeStatisticServlet';
            document.form1.p_process.value = "selectUsersStatisticList";
            document.form1.submit();
        }

        $(document).ready(function() {
            document.getElementById("p_grcode").disabled = true;
            $("#loding-box").hide();
            $("#p_sdt").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
            $("#p_edt").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})

            if($("#p_edu_type").val() == "B") {
                document.getElementById("p_grcode").disabled = false;
            }

            $("#p_edu_type").change(function(){
                if($("#p_edu_type").val() == "B"){
                    document.getElementById("p_grcode").disabled = false;
                } else {
                    $("#p_grcode").val("");
                    document.getElementById("p_grcode").disabled = true;
                }
            });

            $("#p_sex_a").click(function() {
                if($("#p_sex_a").is(":checked")) {
                    $("input[name=p_sex]").attr("checked", "checked");
                } else {
                    $("input[name=p_sex]").removeAttr("checked", "checked");
                }
            });

            $("input[name=p_sex]").click(function() {
                var total = $("input[name=p_sex]").length;
                var checked = $("input[name=p_sex]:checked").length;

                if(total != checked) {
                    $("#p_sex_a").removeAttr("checked", "checked");
                } else {
                    $("#p_sex_a").attr("checked", "checked");
                }
            });

            $("#p_age_a").click(function() {
                if($("#p_age_a").is(":checked")) {
                    $("input[name=p_age]").attr("checked", "checked");
                } else {
                    $("input[name=p_age]").removeAttr("checked", "checked");
                }
            });

            $("input[name=p_age]").click(function() {
                var total = $("input[name=p_age]").length;
                var checked = $("input[name=p_age]:checked").length;

                if(total != checked) {
                    $("#p_age_a").removeAttr("checked", "checked");
                } else {
                    $("#p_age_a").attr("checked", "checked");
                }
            });

            $("#p_loc_a").click(function() {
                if($("#p_loc_a").is(":checked")) {
                    $("input[name=p_loc]").attr("checked", "checked");
                } else {
                    $("input[name=p_loc]").removeAttr("checked", "checked");
                }
            });

            $("input[name=p_loc]").click(function() {
                var total = $("input[name=p_loc]").length;
                var checked = $("input[name=p_loc]:checked").length;

                if(total != checked) {
                    $("#p_loc_a").removeAttr("checked", "checked");
                } else {
                    $("#p_loc_a").attr("checked", "checked");
                }
            });

            $("#p_job_a").click(function() {
                if($("#p_job_a").is(":checked")) {
                    $("input[name=p_job]").attr("checked", "checked");
                } else {
                    $("input[name=p_job]").removeAttr("checked", "checked");
                }
            });

            $("input[name=p_job]").click(function() {
                var total = $("input[name=p_job]").length;
                var checked = $("input[name=p_job]:checked").length;

                if(total != checked) {
                    $("#p_job_a").removeAttr("checked", "checked");
                } else {
                    $("#p_job_a").attr("checked", "checked");
                }
            });

            $("#sort").change(function() {
                whenSelection('go');
            });
        });

        function fnSetDate(v) {
            var endDate = new Date($("#p_edt").val());
            var newDate = new Date($("#p_edt").val());
            var year = "";
            var month = "";
            var day = "";

            newDate.setMonth(newDate.getMonth() - v);

            year = newDate.getFullYear();
            month = newDate.getMonth() + 1;
            day = newDate.getDate();

            if(day != endDate.getDate()) {
                if(month == 0) {
                    year -= 1;
                }

                month = (month + 11) % 12;
                day = new Date(year, month, 0).getDate();
            }

            month = ("0" + month).slice(-2);
            day = ("0" + day).slice(-2);

            $("#p_sdt").val(year + "-" + month + "-" + day);
        }

        //엑셀 출력
        function goExcel() {
            if($("#downMemo").val() == "") {
                alert("엑셀 다운 사유를 입력하세요.");
                $("#downMemo").focus();

                return;
            }

            $("#p_memo").val($("#downMemo").val());

            document.form1.target = "_self";
            document.form1.action = '/servlet/controller.statistics.SynthesizeStatisticServlet';
            document.form1.p_process.value = "selectUsersStatisticListExcel";
            document.form1.submit();

            layerClose(); // 레이어창 닫힘



        }

        // 탭 선택
        function select_tab(sel){
            document.form1.target = "_self";

            if (sel == "2") {
                document.form1.p_process.value = "selectEduUsersStatisticList";
            } else if (sel == "3") {
                document.form1.p_process.value = "selectEduResultStatisticList";
            }

            document.form1.action = '/servlet/controller.statistics.SynthesizeStatisticServlet';
            document.form1.submit();
        }
    </script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<div id="loding-box" style="display: none;">
    <img src='/images/img/loading51.gif' style='position: fixed; top:50%; left:570px; width: 50px;'/>
</div>
<table width="1200" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
            <!----------------- title 시작 ----------------->
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td><img src="/images/admin/statistics/tit_sum_d6.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!----------------- title 끝 ----------------->
            <br>
            <table cellspacing="0" cellpadding="0" class="table1" style="width: 100%;">
                <tr>
                    <td width="125">
                        <table cellspacing="0" cellpadding="0" class="s_table">
                            <tr>
                                <td rowspan="3" class="blue_butt_left"></td>
                                <td class="blue_butt_top"></td>
                                <td rowspan="3" class="blue_butt_right"></td>
                            </tr>
                            <tr>
                                <td class="blue_butt_middle">회원</td>
                            </tr>
                            <tr>
                                <td class="blue_butt_bottom"></td>
                            </tr>
                        </table>
                    </td>
                    <td width="2"></td>
                    <td width="125" height="23" align="center" valign="middle">
                        <table cellspacing="0" cellpadding="0" class="s_table">
                            <tr>
                                <td rowspan="3" class="black_butt_left"></td>
                                <td class="black_butt_top"></td>
                                <td rowspan="3" class="black_butt_right"></td>
                            </tr>
                            <tr>
                                <td class="black_butt_middle"><a href="javascript:select_tab('2')" class="c">교육인원</a></td>
                            </tr>
                            <tr>
                                <td class="black_butt_bottom"></td>
                            </tr>
                        </table>
                    </td>
                    <td width="2"></td>
                    <td width="125" height="23" align="center" valign="middle">
                        <table cellspacing="0" cellpadding="0" class="s_table">
                            <tr>
                                <td rowspan="3" class="black_butt_left"></td>
                                <td class="black_butt_top"></td>
                                <td rowspan="3" class="black_butt_right"></td>
                            </tr>
                            <tr>
                                <td class="black_butt_middle"><a href="javascript:select_tab('3')" class="c">교육성과</a></td>
                            </tr>
                            <tr>
                                <td class="black_butt_bottom"></td>
                            </tr>
                        </table>
                    </td>
                    <td>&nbsp;</td>
                </tr>
            </table>
            <table cellspacing="0" cellpadding="0" class="table_out" style="width: 100%;">
                <tr>
                    <td bgcolor="#636563">
                        <table cellspacing="0" cellpadding="0" class="s_table">
                            <tr>
                                <td bgcolor="#FFFFFF" align="center" valign="top">
                                    <table cellspacing="0" cellpadding="1" class="form_table_out" style="width: 100%;">
                                        <form id="form1" name="form1" method="post" action="/servlet/controller.statistics.SynthesizeStatisticServlet">
                                        <input type="hidden" name="p_pageno" value="<%=v_pageno%>">
                                        <input type="hidden" name="p_pagesize" value="<%=v_pagesize%>">
                                        <input type="hidden" name="p_process" value="">
                                        <input type="hidden" name="p_action" value="">
                                        <input type="hidden" name="p_sort" value="">
                                        <input type="hidden" name="p_memo" id="p_memo" value="">
                                        <tr>
                                            <td bgcolor="#C6C6C6" align="center">
                                                <table class="form_table_bg" >
                                                    <tr>
                                                        <td height="7"></td>
                                                    </tr>
                                                    <tr>
                                                        <td align="center">
                                                            <table border="0" width="99%" class="form_table">
                                                                <colgroup>
                                                                    <col width="10%">
                                                                    <col width="30%">
                                                                    <col width="5%">
                                                                    <col width="55%">
                                                                </colgroup>
                                                                <tbody>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            교육구분
                                                                        </td>
                                                                        <td class="_tdS">
                                                                            <select id="p_edu_type" name="p_edu_type" style="width: 28%;">
                                                                                <option value="">-- 교육구분 --</option>
                                                                                <option value="C" <% if (v_edu_type.equals("C")) {%> selected <%}%>>B2C</option>
                                                                                <option value="B" <% if (v_edu_type.equals("B")) {%> selected <%}%>>B2B</option>
                                                                            </select>

                                                                            <select id="p_grcode" name="p_grcode" style="width: 70%;">
                                                                                <option value="">-- 교육그룹 --</option>
                                                                                <%
                                                                                    DataBox g_dbox = null;

                                                                                    if( gr_list.size() != 0 ) {		// 검색된 내용이 있다면
                                                                                        v_total = gr_list.size();

                                                                                        for(i = 0; i < v_total; i++) {
                                                                                            g_dbox = (DataBox)gr_list.get(i);

                                                                                            if( g_dbox.getString("d_grcode").equals(v_grcode)) {
                                                                                %>
                                                                                    <option value="<%= g_dbox.getString("d_grcode") %>" selected><%= g_dbox.getString("d_grcodenm") %></option>
                                                                                <%
                                                                                            } else {
                                                                                %>
                                                                                    <option value="<%= g_dbox.getString("d_grcode") %>"><%= g_dbox.getString("d_grcodenm") %></option>
                                                                                <%
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </select>
                                                                        </td>
                                                                        <td class="_tdT">
                                                                            <font color="red">★</font>기간
                                                                        </td>
                                                                        <td class="_tdS">
                                                                            <button type="button" onclick="fnSetDate('0')">오늘</button>
                                                                            <button type="button" onclick="fnSetDate('1')">1개월</button>
                                                                            <button type="button" onclick="fnSetDate('3')">3개월</button>
                                                                            <button type="button" onclick="fnSetDate('6')">6개월</button>
                                                                            <button type="button" onclick="fnSetDate('12')">1년</button>
                                                                            <input name="p_sdt" id="p_sdt" value="<%= v_sdt %>" type="text" class="datepicker_input1" size="10" style="text-align: center" readonly /> 일 ~
                                                                            <input name="p_edt" id="p_edt" value="<%= v_edt %>" vtype="text" class="datepicker_input1" size="10" style="text-align: center" readonly /> 일
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            성별
                                                                        </td>
                                                                        <td class="_tdS">
                                                                            <input type="checkbox" id="p_sex_a" name="p_sex_all" class="needinputClass" value="A" <% if (v_sex_all.equals("A")) {%> checked <%}%>><label for="p_sex_a">전체</label>
                                                                            <input type="checkbox" id="p_sex_m" name="p_sex" class="needinputClass" value="M" <% if (v_sex.indexOf("M") > -1) {%> checked <%}%>><label for="p_sex_m">남자</label>
                                                                            <input type="checkbox" id="p_sex_w" name="p_sex" class="needinputClass" value="W" <% if (v_sex.indexOf("W") > -1) {%> checked <%}%>><label for="p_sex_w">여자</label>
                                                                            <input type="checkbox" id="p_sex_n" name="p_sex" class="needinputClass" value="N" <% if (v_sex.indexOf("N") > -1) {%> checked <%}%>><label for="p_sex_n">미확인</label>
                                                                        </td>
                                                                        <td class="_tdT">
                                                                            연령대
                                                                        </td>
                                                                        <td class="_tdS">
                                                                            <input type="checkbox" id="p_age_a" name="p_age_all" class="needinputClass" value="A" <% if (v_age_all.equals("A")) {%> checked <%}%>><label for="p_age_a">전체</label>
                                                                            <input type="checkbox" id="p_age_1" name="p_age" class="needinputClass" value="10" <% if (v_age.indexOf("10") > -1) {%> checked <%}%>><label for="p_age_1">10대</label>
                                                                            <input type="checkbox" id="p_age_2" name="p_age" class="needinputClass" value="20" <% if (v_age.indexOf("20") > -1) {%> checked <%}%>><label for="p_age_2">20대</label>
                                                                            <input type="checkbox" id="p_age_3" name="p_age" class="needinputClass" value="30" <% if (v_age.indexOf("30") > -1) {%> checked <%}%>><label for="p_age_3">30대</label>
                                                                            <input type="checkbox" id="p_age_4" name="p_age" class="needinputClass" value="40" <% if (v_age.indexOf("40") > -1) {%> checked <%}%>><label for="p_age_4">40대</label>
                                                                            <input type="checkbox" id="p_age_5" name="p_age" class="needinputClass" value="50" <% if (v_age.indexOf("50") > -1) {%> checked <%}%>><label for="p_age_5">50대</label>
                                                                            <input type="checkbox" id="p_age_6" name="p_age" class="needinputClass" value="60" <% if (v_age.indexOf("60") > -1) {%> checked <%}%>><label for="p_age_6">60대 이상</label>
                                                                            <input type="checkbox" id="p_age_0" name="p_age" class="needinputClass" value="00" <% if (v_age.indexOf("00") > -1) {%> checked <%}%>><label for="p_age_0">미확인</label>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            지역
                                                                        </td>
                                                                        <td colspan="3" class="_tdS">
                                                                            <input type="checkbox" id="p_loc_a" name="p_loc_all" class="needinputClass" value="A" <% if (v_loc_all.equals("A")) {%> checked <%}%>><label for="p_loc_a">전체</label>
                                                                            <%
                                                                                DataBox c_dbox = null;

                                                                                if( cd_list.size() != 0 ) {		// 검색된 내용이 있다면
                                                                                    v_total = cd_list.size();

                                                                                    for(i = 0; i < v_total; i++) {
                                                                                        c_dbox = (DataBox)cd_list.get(i);
                                                                            %>
                                                                            <input type="checkbox" id="p_loc_<%= c_dbox.getString("d_code") %>" name="p_loc" class="needinputClass" value="<%= c_dbox.getString("d_code") %>" <% if (v_loc.indexOf(c_dbox.getString("d_code")) > -1) {%> checked <%}%>><label for="p_loc_<%= c_dbox.getString("d_code") %>"><%= c_dbox.getString("d_codenm") %></label>
                                                                            <%
                                                                                    }
                                                                                }
                                                                            %>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            직업군
                                                                        </td>
                                                                        <td colspan="3" class="_tdS">
                                                                            <input type="checkbox" id="p_job_a" name="p_job_all" class="needinputClass" value="A" <% if (v_job_all.equals("A")) {%> checked <%}%>><label for="p_job_a">전체</label>
                                                                            <%
                                                                                DataBox j_dbox = null;

                                                                                if( job_list.size() != 0 ) {		// 검색된 내용이 있다면
                                                                                    v_total = job_list.size();

                                                                                    for(i = 0; i < v_total; i++) {
                                                                                        j_dbox = (DataBox)job_list.get(i);
                                                                            %>
                                                                            <input type="checkbox" id="p_job_<%= j_dbox.getString("d_cd") %>" name="p_job" class="needinputClass" value="<%= j_dbox.getString("d_cd") %>" <% if (v_job.indexOf(j_dbox.getString("d_cd")) > -1) {%> checked <%}%>><label for="p_job_<%= j_dbox.getString("d_cd") %>"><%= j_dbox.getString("d_nm") %></label>
                                                                            <%
                                                                                    }
                                                                                }
                                                                            %>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            활동상태
                                                                        </td>
                                                                        <td class="_tdS">
                                                                            <select id="p_state" name="p_state" style="width: 100%;">
                                                                                <option value="">-- 전체 --</option>
                                                                                <option value="7d" <% if (v_state.equals("7d")) {%> selected <%}%>>최근 접속 7일 이내</option>
                                                                                <option value="1m" <% if (v_state.equals("1m")) {%> selected <%}%>>최근 접속 1개월 이내</option>
                                                                                <option value="3m" <% if (v_state.equals("3m")) {%> selected <%}%>>최근 접속 3개월 이내</option>
                                                                                <option value="6m" <% if (v_state.equals("6m")) {%> selected <%}%>>최근 접속 6개월 이내</option>
                                                                                <option value="1y" <% if (v_state.equals("1y")) {%> selected <%}%>>최근 접속 1년 이내</option>
                                                                                <option value="2y" <% if (v_state.equals("2y")) {%> selected <%}%>>최근 접속 2년 이내</option>
                                                                                <option value="3y" <% if (v_state.equals("3y")) {%> selected <%}%>>최근 접속 3년 이내</option>
                                                                                <option value="3h" <% if (v_state.equals("3h")) {%> selected <%}%>>최근 접속 3년 초과</option>
                                                                                <option value="n" <% if (v_state.equals("n")) {%> selected <%}%>>접속 이력 없음</option>
                                                                            </select>
                                                                        </td>
                                                                        <td colspan="2" class="_tdT">
                                                                            <%@ include file="/learn/admin/include/za_GoButton.jsp" %>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td height="9"></td>
                                                                    </tr>
                                                                </tbody>
                                                            </table>
                                                        </td>
                                                    </tr>
                                                </table>
                                            </td>
                                        </tr>
                                        </form>
                                    </table>
                                    <br>
                                    <table cellpadding="0" cellspacing="0" class="table1" style="width:100%;">
                                        <tr>
                                            <td>
                                                <select id="sort" name="sort">
                                                    <option value="ND" <% if (v_sort.equals("ND")) {%> selected <%}%>>최근 가입일자 순</option>
                                                    <option value="OD" <% if (v_sort.equals("OD")) {%> selected <%}%>>오래된 가입일자 순</option>
                                                    <option value="NM" <% if (v_sort.equals("NM")) {%> selected <%}%>>성명 가나다 순</option>
                                                </select>
                                            </td>
                                            <td align="right">
                                            <% if( ss_action.equals("go") ){  %>

                                                <%--<a href="javascript:fnlayerpopup();" class="c"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>--%>
                                                <span onclick="fnlayerpopup();" class="c" style="cursor: pointer;"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></span>
                                            <% } else { %>
                                                <a href="javascript:alert('조회 후 엑셀을 출력하세요.');" class="c"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>
                                            <% } %>
                                            </td>
                                        </tr>
                                        <tr>
                                            <td height="3"></td>
                                        </tr>
                                    </table>
                                    <table cellspacing="1" cellpadding="0" class="box_table_out" style="width: 100%;">
                                        <tr>
                                            <td colspan="16" class="table_top_line"></td>
                                        </tr>
                                        <tr>
                                            <td class="table_title" width="4%">No</td>
                                            <td class="table_title" width="6%">회원구분</td>
                                            <td class="table_title" width="3%">교육<br>구분</td>
                                            <td class="table_title" width="20%">소속</td>
                                            <td class="table_title" width="8%">아이디</td>
                                            <td class="table_title" width="5%">성명</td>
                                            <td class="table_title" width="4%">성별</td>
                                            <td class="table_title" width="7%">생년월일</td>
                                            <td class="table_title" width="4%">지역</td>
                                            <td class="table_title" width="6%">직업군</td>
                                            <td class="table_title" width="3%">연령</td>
                                            <td class="table_title" width="7%">가입일</td>
                                            <td class="table_title" width="11%">최근접속일</td>
                                            <td class="table_title" width="4%">신청<br>과정(수)</td>
                                            <td class="table_title" width="4%">학습<br>과정(수)</td>
                                            <td class="table_title" width="4%">수료<br>과정(수)</td>
                                        </tr>
                                        <%
                                            DataBox dbox = null;

                                            if(ss_action.equals("go") ){	//go button 선택일때라면
                                                if( list.size() != 0 ){		// 검색된 내용이 있다면
                                                    v_total = list.size();

                                                    for(i = 0; i < v_total; i++) {
                                                        dbox = (DataBox)list.get(i);

                                                        v_totalpage = dbox.getInt("d_totalpage");
                                                        v_totalrowcount = dbox.getInt("d_totalrowcount");
                                        %>
                                        <tr>
                                            <td class="table_02_1"><%= v_totalrowcount - v_pagesize * v_pageno + v_pagesize - i %></td>
                                            <td class="table_02_1"><%= dbox.getString("d_mber_nm") %></td>
                                            <td class="table_02_1"><%= dbox.getString("d_edu_type") %></td>
                                            <td class="table_02_2"><%= dbox.getString("d_grcodenm") %></td>
                                            <td class="table_02_1"><%= dbox.getString("d_user_id") %></td>
                                            <td class="table_02_1"><%= dbox.getString("d_user_nm") %></td>
                                            <td class="table_02_1"><%= dbox.getString("d_sex_nm") %></td>
                                            <td class="table_02_1"><%= dbox.getString("d_brthdy") %></td>
                                            <td class="table_02_1"><%= dbox.getString("d_region") %></td>
                                            <td class="table_02_1"><%= dbox.getString("d_job_nm") %></td>
                                            <td class="table_02_1"><%= dbox.getString("d_age2") %>대</td>
                                            <td class="table_02_1"><%= dbox.getString("d_indt") %></td>
                                            <td class="table_02_1"><%= dbox.getString("d_lslgdt") %></td>
                                            <td class="table_02_1"><%= dbox.getInt("d_pro_cnt") %></td>
                                            <td class="table_02_1"><%= dbox.getInt("d_edu_cnt") %></td>
                                            <td class="table_02_1"><%= dbox.getInt("d_gra_cnt") %></td>
                                        </tr>
                                        <%
                                                    }
                                                } else {
                                        %>
                                        <tr>
                                            <td align="center" bgcolor="#F7F7F7" height="50" colspan="15">검색된 데이터가 없습니다</td>
                                        </tr>
                                        <%
                                                }
                                            }
                                        %>
                                    </table>
                                    <br>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
            </table>
            <br>
            <table width="97%" height="26" border="0" cellpadding="0" cellspacing="0">
                <tr>
                    <td align="right" valign="absmiddle">
                        <%= PageUtil.printPageSizeList2(v_totalpage, v_pageno, row, v_pagesize, v_totalrowcount, 100, 1000, 100) %>
                    </td>
                </tr>
            </table>
            <br>
        </td>
    </tr>
    <tr>
        <td><%@ include file = "/learn/library/getJspName.jsp" %></td>
    </tr>
</table>

<!-- layer -->
<div class="opacity_layer_bg01"></div>
<div class="layer_wrap sub_layer_statistics">
    <div class="layer_top">
        <button type="button" title="닫기" class="btn_layerClose" onclick="layerClose();">닫기</button>
        <h5>다운로드 사유</h5>
    </div>
    <div class="content_box">
        <input type="text" id="downMemo" placeholder="다운로드 사유를 입력하세요.">
    </div>
    <div class="double_btn_box">
        <span title="확인" class="btn_layerClose" onclick="goExcel();">확인</span>
        <span title="닫기" class="btn_layerClose" onclick="layerClose();">닫기</span>
    </div>

</div>

<style>
    ._tdT{text-align: right;}
</style>
</body>
</html>
