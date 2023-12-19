<%
    //**********************************************************
//  1. ��      ��: ���� ���(��������)
//  2. ���α׷���: za_EduResultStatistics_L.jsp
//  3. ��      ��: �������� ��� ��ȸ
//  4. ȯ      ��: JDK 1.7
//  5. ��      ��: 1.0
//  6. ��      ��: 2022.11.07
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

    String v_edu_type = box.getString("p_edu_type_er");
    String v_grcode = box.getString("p_grcode_er");
    String v_year = box.getString("p_year_er");
    String v_seq = box.getString("p_seq_er");
    String v_subj = box.getString("p_subj_er");
    String v_g1 = box.getString("p_g1_er");
    String v_g2 = box.getString("p_g2_er");
    String v_g3 = box.getString("p_g3_er");
    String v_lv = box.getString("p_lv_er");
    String v_lv_type = box.getString("p_lv_type_er");
    String v_out = box.getString("p_out_er");
    String v_txt = box.getString("p_txt_er");
    String v_dt_type = box.getString("p_dt_type_er");
    String v_gubun = "0110";
    String v_gubun_lv = "0121";
    String v_lv2 = "2";
    String v_lv3 = "3";

    if(v_edu_type.equals("C")) {
        v_grcode = "N000001";
    }

    String v_sdt = box.getString("p_sdt_er");
    String v_edt = box.getString("p_edt_er");
    String v_avg = "";
    String v_avg_all = box.getString("p_avg_er_all");
    String[] avgArr = box.getStringArray("p_avg_er");

    if(avgArr != null) {
        for (int z = 0; z < avgArr.length; z++) {
            if(v_avg.equals("")) {
                v_avg = SQLString.Format(avgArr[z]);
            } else {
                v_avg += "," + SQLString.Format(avgArr[z]);
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
    String _gm1 = "";
    String _gm2 = "";
    String _gm3 = "";
    String _lvnm = "";

    ArrayList list = null;
    list = (ArrayList)request.getAttribute("selectEduResultStatisticList");

    ArrayList gr_list = null;
    gr_list = (ArrayList)request.getAttribute("selectGrcodeList");

    ArrayList cd_list = null;
    cd_list = (ArrayList)request.getAttribute("selectListCode");

    ArrayList job_list = null;
    job_list = (ArrayList)request.getAttribute("selectJobList");

    ArrayList g1_list = null;
    g1_list = (ArrayList)request.getAttribute("selectG1ListCode");

    if (v_pageno == 0)  v_pageno = 1;
    int row= Integer.parseInt(conf.getProperty("page.bulletin.row"));
    if (v_pagesize == 0)  v_pagesize = 100;
%>
<html>
<head>
    <title>�������(�����ο�)</title>
    <meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
    <link rel="stylesheet" href="/css/admin_style.css" type="text/css">
    <link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
    <script language = "javascript" src = "/script/cresys_lib.js"></script>
    <script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
    <script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
    <script type="text/javascript" src="/script/effects.core.js"></script>
    <script type="text/javascript" src="/script/effects.blind.js"></script>
    <script type="text/javascript" src="/script/effects.drop.js"></script>
    <script type="text/javascript" src="/script/effects.explode.js"></script>
    <script type="text/javascript" src="/script/ui.datepicker.js"></script>

    <script language="JavaScript">
        <!--
        // ��ȸ �˻�

        var type = "";
        var type2 = "";
        function whenSelection(p_action) {
            if($("#p_dt_type_er").val() != "") {
                if($("#p_sdt_er").val() == "") {
                    alert("���� ���ڸ� �Է��� �ּ���.");
                    $("#p_sdt_er").focus();
                    return;
                }

                if($("#p_edt_er").val() == "") {
                    alert("���� ���ڸ� �Է��� �ּ���.");
                    $("#p_edt_er").focus();
                    return;
                }

                var st_dt = $("#p_sdt_er").val().replaceAll("-","");
                var ed_dt = $("#p_edt_er").val().replaceAll("-","");

                if(st_dt > ed_dt){
                    alert("�������� �����Ϻ��� Ů�ϴ�.");
                    return;
                }
            }

            $("#loding-box").show();
            document.form1.target = "_self";
            document.form1.p_action.value  = p_action;
            document.form1.p_process.value = 'selectEduResultStatisticList';
            document.form1.submit();
        }

        // ������ �̵�
        function goPage(pageNum) {
            if($("#p_dt_type_er").val() != "") {
                if($("#p_sdt_er").val() == "") {
                    alert("���� ���ڸ� �Է��� �ּ���.");
                    $("#p_sdt_er").focus();
                    return;
                }

                if($("#p_edt_er").val() == "") {
                    alert("���� ���ڸ� �Է��� �ּ���.");
                    $("#p_edt_er").focus();
                    return;
                }

                var st_dt = $("#p_sdt_er").val().replaceAll("-","");
                var ed_dt = $("#p_edt_er").val().replaceAll("-","");

                if(st_dt > ed_dt){
                    alert("�������� �����Ϻ��� Ů�ϴ�.");
                    return;
                }
            }

            $("#loding-box").show();
            document.form1.target = "_self";
            document.form1.p_pageno.value = pageNum;
            document.form1.p_action.value = "go";
            document.form1.action = '/servlet/controller.statistics.SynthesizeStatisticServlet';
            document.form1.p_process.value = "selectEduResultStatisticList";
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
            document.getElementById("p_grcode_er").disabled = true;
            document.getElementById("p_year_er").disabled = true;
            document.getElementById("p_seq_er").disabled = true;
            document.getElementById("p_subj_er").disabled = true;

            $("#loding-box").hide();
            $("#p_sdt_er").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
            $("#p_edt_er").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})

            if($("#p_edu_type_er").val() == "B") {
                document.getElementById("p_grcode_er").disabled = false;
                document.getElementById("p_year_er").disabled = false;
                document.getElementById("p_seq_er").disabled = false;
                document.getElementById("p_subj_er").disabled = false;
            } else if($("#p_edu_type_er").val() == "C") {
                document.getElementById("p_year_er").disabled = false;
                document.getElementById("p_seq_er").disabled = false;
                document.getElementById("p_subj_er").disabled = false;
            }

            $("#p_edu_type_er").change(function(){
                $("#p_grcode_er").val("");
                $("#p_year_er").val("");
                $("#p_seq_er").val("");
                $("#p_subj_er").val("");
                type = "G";
                fnAjaxSelect("");

                if($("#p_edu_type_er").val() == "B"){
                    document.getElementById("p_grcode_er").disabled = false;
                    document.getElementById("p_year_er").disabled = false;
                    document.getElementById("p_seq_er").disabled = false;
                    document.getElementById("p_subj_er").disabled = false;
                } else if($("#p_edu_type_er").val() == "C") {
                    document.getElementById("p_grcode_er").disabled = true;
                    document.getElementById("p_year_er").disabled = false;
                    document.getElementById("p_seq_er").disabled = false;
                    document.getElementById("p_subj_er").disabled = false;
                    fnAjaxSelect("N000001");
                } else {
                    document.getElementById("p_grcode_er").disabled = true;
                    document.getElementById("p_year_er").disabled = true;
                    document.getElementById("p_seq_er").disabled = true;
                    document.getElementById("p_subj_er").disabled = true;
                }
            });

            if($("#p_edu_type_er").val() == "C") {
                if ($("#p_year_er").val().length < 1) {
                    type = "G";
                    fnAjaxSelect("N000001");
                }

                if ($("#p_year_er").val().length > 0 && $("#p_seq_er").val().length < 1) {
                    type = "Y";
                    fnAjaxSelect($("#p_year_er").val());
                }

                if ($("#p_year_er").val().length > 0 && $("#p_seq_er").val().length > 0 && $("#p_subj_er").val().length < 1) {
                    type = "S";
                    fnAjaxSelect($("#p_seq_er").val());
                }
            }else{
                if ($("#p_grcode_er").val().length > 0 && $("#p_year_er").val().length < 1) {
                    type = "G";
                    fnAjaxSelect($("#p_grcode_er").val());
                }

                if ($("#p_grcode_er").val().length > 0 && $("#p_year_er").val().length > 0 && $("#p_seq_er").val().length < 1) {
                    type = "Y";
                    fnAjaxSelect($("#p_year_er").val());
                }

                if ($("#p_grcode_er").val().length > 0 && $("#p_year_er").val().length > 0 && $("#p_seq_er").val().length > 0 && $("#p_subj_er").val().length < 1) {
                    type = "S";
                    fnAjaxSelect($("#p_seq_er").val());
                }
            }

            if($("#p_g1_er").val().length > 0 && $("#p_g2_er").val().length < 1) {
                type2 = "2";
                fnAjaxSelect2($("#p_g1_er").val());
                fnAjaxLvGubunCode();
            }

            if($("#p_g1_er").val().length > 0 && $("#p_g2_er").val().length > 0 && $("#p_g3_er").val().length < 1) {
                type2 = "3";
                fnAjaxSelect2($("#p_g2_er").val());
                fnAjaxLvGubunCode();
            }

            if($("#p_g1_er").val().length > 0 && $("#p_g2_er").val().length > 0 && $("#p_g3_er").val().length > 0) {
                fnAjaxLvGubunCode();
            }

            if ($("#p_dt_type_er").val() != "") {
                $(".dt_type").attr("disabled", false);
                $("#p_sdt_er").attr("disabled", false);
                $("#p_edt_er").attr("disabled", false);
                $(".ui-datepicker-trigger").css("display", "inline-block");
            } else {
                $(".dt_type").attr("disabled", true);
                $("#p_sdt_er").attr("disabled", true);
                $("#p_edt_er").attr("disabled", true);
                $(".ui-datepicker-trigger").css("display", "none");
            }

            $("#p_avg_er_a").click(function() {
                if($("#p_avg_er_a").is(":checked")) {
                    $("input[name=p_avg_er]").attr("checked", "checked");
                } else {
                    $("input[name=p_avg_er]").removeAttr("checked", "checked");
                }
            });

            $("input[name=p_avg_er]").click(function() {
                var total = $("input[name=p_avg_er]").length;
                var checked = $("input[name=p_avg_er]:checked").length;

                if(total != checked) {
                    $("#p_avg_er_a").removeAttr("checked", "checked");
                } else {
                    $("#p_avg_er_a").attr("checked", "checked");
                }
            });

            $("#p_grcode_er").bind("change", function(){
                type = "G";
                fnAjaxSelect($(this).val());
            });

            $("#p_year_er").bind("change", function(){
                type = "Y";
                fnAjaxSelect($(this).val());
            });

            $("#p_seq_er").bind("change", function(){
                type = "S";
                fnAjaxSelect($(this).val());
            });

            $("#p_g1_er").bind("change", function(){
                type2 = "2";
                fnAjaxSelect2($(this).val());
                fnAjaxLvGubunCode();
            });

            $("#p_g2_er").bind("change", function(){
                type2 = "3";
                fnAjaxSelect2($(this).val());
            });

            $("#p_dt_type_er").bind("change", function(){
                if ($(this).val() != "") {
                    $(".dt_type").attr("disabled", false);
                    $("#p_sdt_er").attr("disabled", false);
                    $("#p_edt_er").attr("disabled", false);
                   $(".ui-datepicker-trigger").css("display", "inline-block");
                } else {
                    $(".dt_type").attr("disabled", true);
                    $("#p_sdt_er").attr("disabled", true);
                    $("#p_edt_er").attr("disabled", true);
                    $(".ui-datepicker-trigger").css("display", "none");
                }
            });
        });

        function fnSetDate(v) {
            var endDate = new Date($("#p_edt_er").val());
            var newDate = new Date($("#p_edt_er").val());
            var year = "";
            var month = "";
            var day = "";

            $("#p_sdt_er").attr("readonly", true);
            $("#p_edt_er").attr("readonly", true);

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

            $("#p_sdt_er").val(year + "-" + month + "-" + day);
        }

        function fnWriteDate() {
            $("#p_sdt_er").attr("readonly", false);
            $("#p_edt_er").attr("readonly", false);
        }

        //���� ���
        function goExcel() {
            if($("#downMemo").val() == "") {
                alert("���� �ٿ� ������ �Է��ϼ���.");
                $("#downMemo").focus();

                return;
            }

            $("#p_memo").val($("#downMemo").val());

            document.form1.target = "_self";
            document.form1.action = '/servlet/controller.statistics.SynthesizeStatisticServlet';
            document.form1.p_process.value = "selectEduResultStatisticListExcel";
            document.form1.submit();
        }

        // �� ����
        function select_tab(sel){
            document.form1.target = "_self";

            if (sel == "1") {
                document.form1.p_process.value = "selectUsersStatisticList";
            } else if (sel == "2") {
                document.form1.p_process.value = "selectEduUsersStatisticList";
            }

            document.form1.action = '/servlet/controller.statistics.SynthesizeStatisticServlet';
            document.form1.submit();
        }

        function fnAjaxSelect(v) {
            var param = "";
            var grcode = $("#p_grcode_er").val();
            var gyear = $("#p_year_er").val();

            if($("#p_edu_type_er").val() == "C") {
                grcode = "N000001";
            }

            if (type == "G") {
                param = "type=sqlID&sqlID=selectBox.grYearList&param=" + v;
            } else if (type == "Y") {
                param = "type=sqlID&sqlID=selectBox.grSeqList&param=" + grcode + "," + v;
            } else if (type == "S") {
                param = "type=sqlID&sqlID=selectBox.subjListAll&param=" + grcode + "," + gyear + "," + v;
            }

            $.ajaxSetup({cache:false});
            $.ajax({
                type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnAjaxData
                ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
                ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                }
            });
        }

        function fnAjaxData( result ) {
            var el = "";

            if (type == "G") {
                el = "p_year_er";
                $("#p_year_er").empty();
                $("#p_year_er").append("<option value=\"\">-- ���� --</option>");
                $("#p_seq_er").empty();
                $("#p_seq_er").append("<option value=\"\">-- ���� --</option>");
            } else if (type == "Y") {
                el = "p_seq_er";
                $("#p_seq_er").empty();
                $("#p_seq_er").append("<option value=\"\">-- ���� --</option>");
            } else if (type == "S") {
                el = "p_subj_er";
            }

            $("#p_subj_er").empty();
            $("#p_subj_er").append("<option value=\"\">-- ���� --</option>");

            if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
                $.each( result.selectBoxList, function() {
                    $("#" + el).append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
                });
            }
        }

        function fnAjaxSelect2(v) {
            var param = "type=sqlID&sqlID=code.list.0004&param=0110" + "," + v + "," + type2;

            $.ajaxSetup({cache:false});
            $.ajax({
                type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnAjaxData2
                ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
                ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                }
            });
        }

        function fnAjaxData2( result ) {
            var el = "";

            if (type2 == "2") {
                $("#p_g2_er").empty();
                $("#p_g2_er").append("<option value=\"\">-- ��з� --</option>");
                el = "p_g2_er"
            } else if (type2 == "3") {
                el = "p_g3_er"
            }

            $("#p_g3_er").empty();
            $("#p_g3_er").append("<option value=\"\">-- �Һз� --</option>");

            if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
                $.each( result.selectBoxList, function() {
                    $("#" + el).append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
                });
            }
        }

        function fnAjaxLvGubunCode() {
            $.ajaxSetup({cache:false});
            $.ajax({
                type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : "type=sqlID&sqlID=code.list.0006&param=0110," + $("#p_g1_er").val() + ",1"
                ,   success : function( res ) {
                    if ( res.selectBoxList != null && res.selectBoxList.length > 0 ) {
                        $("#p_lv_type_er").val(res.selectBoxList[0].d_code);
                        fnAjaxSelect3(res.selectBoxList[0].d_code);
                    }
                }
                ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
                ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                }
            });
        }

        function fnAjaxSelect3(v) {
            var param = "type=sqlID&sqlID=code.list.0004&param=0121," + v + ",2";

            $.ajaxSetup({cache:false});
            $.ajax({
                type : "get"
                ,   url : "/learn/admin/common/za_GetSelectBoxAjaxResult.jsp"
                ,   dataType : "json"
                ,   data : param
                ,   success : fnAjaxData3
                ,   complete : function(arg1, arg2) {
                    // alert("complete : " + arg1);
                }
                ,   error :  function(arg1, arg2) {
                    // alert("error : " + arg1);
                }
            });
        }

        function fnAjaxData3( result ) {
            if ('<%= v_lv %>' == "") {
                $("#p_lv_er").empty();
                $("#p_lv_er").append("<option value=\"\">-- ���̵� --</option>");

                if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
                    $.each( result.selectBoxList, function() {
                        $("#p_lv_er").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
                    });
                }
            }
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
            <!----------------- title ���� ----------------->
            <table width="100%" border="0" cellspacing="0" cellpadding="0" class=page_title>
                <tr>
                    <td><img src="/images/admin/statistics/tit_sum_d6.gif" ></td>
                    <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
                </tr>
            </table>
            <!----------------- title �� ----------------->
            <br>
            <table cellspacing="0" cellpadding="0" class="table1" style="width: 100%;">
                <tr>
                    <td width="125">
                        <table cellspacing="0" cellpadding="0" class="s_table">
                            <tr>
                                <td rowspan="3" class="black_butt_left"></td>
                                <td class="black_butt_top"></td>
                                <td rowspan="3" class="black_butt_right"></td>
                            </tr>
                            <tr>
                                <td class="black_butt_middle"><a href="javascript:select_tab('1')" class="c">ȸ��</a></td>
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
                                <td rowspan="3" class="black_butt_top"></td>
                            </tr>
                            <tr>
                                <td class="black_butt_middle"><a href="javascript:select_tab('2')" class="c">�����ο�</a></td>
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
                                <td rowspan="3" class="blue_butt_left"></td>
                                <td class="blue_butt_top"></td>
                                <td rowspan="3" class="blue_butt_right"></td>
                            </tr>
                            <tr>
                                <td class="blue_butt_middle">��������</td>
                            </tr>
                            <tr>
                                <td class="blue_butt_bottom"></td>
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
                                        <input type="hidden" name="p_process"  value="">
                                        <input type="hidden" name="p_action"   value="">
                                        <input type="hidden" name="p_lv_type_er" id="p_lv_type_er" value="">
                                        <input type="hidden" name="p_sort_er" id="p_sort_er" value="">
                                        <input type="hidden" name="p_memo" id="p_memo" value="">
                                        <tr>
                                            <td bgcolor="#C6C6C6" align="center">
                                                <table class="form_table_bg" style="width: 1200px; float:left;">
                                                    <tr>
                                                        <td height="7"></td>
                                                    </tr>
                                                    <tr>
                                                        <td align="center">
                                                            <table border="0" width="99%" class="form_table">
                                                                <colgroup>
                                                                    <col width="13%">
                                                                    <col width="21%">
                                                                    <col width="12%">
                                                                    <col width="21%">
                                                                    <col width="12%">
                                                                    <col width="21%">
                                                                </colgroup>
                                                                <tbody>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            ��������
                                                                        </td>
                                                                        <td class="_tdS" colspan="5">
                                                                            <select id="p_edu_type_er" name="p_edu_type_er">
                                                                                <option value="">-- �������� --</option>
                                                                                <option value="C" <% if (v_edu_type.equals("C")) {%> selected <%}%>>B2C</option>
                                                                                <option value="B" <% if (v_edu_type.equals("B")) {%> selected <%}%>>B2B</option>
                                                                            </select>

                                                                            <select id="p_grcode_er" name="p_grcode_er">
                                                                                <option value="">-- �����׷� --</option>
                                                                                <%
                                                                                    DataBox g_dbox = null;

                                                                                    if( gr_list.size() != 0 ) {		// �˻��� ������ �ִٸ�
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
                                                                            <%
                                                                                if ( !v_year.equals("") ) {
                                                                            %>
                                                                                    <kocca:selectBox name="p_year_er" id="p_year_er" optionTitle="-- ����2 --" type="sqlID" param="<%= v_grcode %>" sqlID="selectBox.grYearList" selectedValue="<%= v_year %>" isLoad="true" />
                                                                            <%
                                                                                } else {
                                                                            %>
                                                                                    <select name="p_year_er" id="p_year_er">
                                                                                        <option value="">-- ���� --</option>
                                                                                    </select>
                                                                            <%
                                                                                }
                                                                            %>

                                                                            <%
                                                                                if ( !v_seq.equals("") ) {
                                                                            %>
                                                                                    <kocca:selectBox name="p_seq_er" id="p_seq_er" optionTitle="-- ���� --" type="sqlID" param="<%= v_grcode + ',' + v_year %>" sqlID="selectBox.grSeqList" selectedValue="<%= v_seq %>" isLoad="true" />
                                                                            <%
                                                                                } else {
                                                                            %>
                                                                                    <select name="p_seq_er" id="p_seq_er">
                                                                                        <option value="">-- ���� --</option>
                                                                                    </select>
                                                                            <%
                                                                                }
                                                                            %>

                                                                            <%
                                                                                if ( !v_seq.equals("") ) {
                                                                            %>
                                                                                    <kocca:selectBox name="p_subj_er" id="p_subj_er" optionTitle="-- ���� --" type="sqlID" param="<%= v_grcode + ',' + v_year + ',' + v_seq  %>" sqlID="selectBox.subjListAll" selectedValue="<%= v_subj %>" isLoad="true" />
                                                                            <%
                                                                                } else {
                                                                            %>
                                                                                    <select name="p_subj_er" id="p_subj_er">
                                                                                        <option value="">-- ���� --</option>
                                                                                    </select>
                                                                            <%
                                                                                }
                                                                            %>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                       <td class="_tdT">
                                                                           ī�װ�
                                                                       </td>
                                                                        <td class="_tdS" colspan="5">
                                                                            <select id="p_g1_er" name="p_g1_er">
                                                                                <option value="">-- �帣 --</option>
                                                                                <%
                                                                                    DataBox g1_dbox = null;

                                                                                    if( g1_list.size() != 0 ) {		// �˻��� ������ �ִٸ�
                                                                                        v_total = g1_list.size();

                                                                                        for(i = 0; i < v_total; i++) {
                                                                                            g1_dbox = (DataBox)g1_list.get(i);

                                                                                            if( g1_dbox.getString("d_cd").equals(v_g1)) {
                                                                                %>
                                                                                <option value="<%= g1_dbox.getString("d_cd") %>" selected><%= g1_dbox.getString("d_nm") %></option>
                                                                                <%
                                                                                } else {
                                                                                %>
                                                                                <option value="<%= g1_dbox.getString("d_cd") %>"><%= g1_dbox.getString("d_nm") %></option>
                                                                                <%
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                %>
                                                                            </select>

                                                                            <%
                                                                                if ( !v_g1.equals("") ) {
                                                                            %>
                                                                                    <kocca:selectBox name="p_g2_er" id="p_g2_er" optionTitle="-- ��з� --" type="sqlID" sqlID="code.list.0004" param="<%= v_gubun + ',' + v_g1 + ',' + v_lv2 %>" selectedValue="<%= v_g2 %>" isLoad="true" />

                                                                            <%
                                                                                } else {
                                                                            %>
                                                                                    <select name="p_g2_er" id="p_g2_er">
                                                                                        <option value="">-- ��з� --</option>
                                                                                    </select>
                                                                            <%
                                                                                }
                                                                            %>

                                                                            <%
                                                                                if ( !v_g2.equals("") ) {
                                                                            %>
                                                                                    <kocca:selectBox name="p_g3_er" id="p_g3_er" optionTitle="-- �Һз� --" type="sqlID" sqlID="code.list.0004" param="<%= v_gubun + ',' + v_g2 + ',' + v_lv3 %>" selectedValue="<%= v_g3 %>" isLoad="true" />

                                                                            <%
                                                                                } else {
                                                                            %>
                                                                                    <select name="p_g3_er" id="p_g3_er">
                                                                                        <option value="">-- �Һз� --</option>
                                                                                    </select>
                                                                            <%
                                                                                }
                                                                            %>

                                                                            <%
                                                                                if ( !v_g1.equals("") ) {
                                                                            %>
                                                                                    <kocca:selectBox name="p_lv_er" id="p_lv_er" optionTitle="-- ���̵� --" type="sqlID" sqlID="code.list.0004" param="<%= v_gubun_lv + ',' + v_lv_type + ',' + v_lv2 %>" selectedValue="<%= v_lv %>" isLoad="true" />

                                                                            <%
                                                                                } else {
                                                                            %>
                                                                                    <select name="p_lv_er" id="p_lv_er">
                                                                                        <option value="">-- ���̵� --</option>
                                                                                    </select>
                                                                            <%
                                                                                }
                                                                            %>

                                                                            <select name="p_out_er" id="p_out_er">
                                                                                <option value="">-- �������� --</option>
                                                                                <option value="C01" <% if ( v_out.equals("C01")) { %> selected <% } %>>����������</option>
                                                                                <option value="C02" <% if ( v_out.equals("C02")) { %> selected <% } %>>�������� ����</option>
                                                                                <option value="C03" <% if ( v_out.equals("C03")) { %> selected <% } %>>��Ȯ�� ������</option>
                                                                                <option value="C04" <% if ( v_out.equals("C04")) { %> selected <% } %>>��Ȯ�ΰ��� ����</option>
                                                                                <option value="C05" <% if ( v_out.equals("C05")) { %> selected <% } %>>��Ȯ�ΰ��� �� �������� ����</option>
                                                                            </select>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            ��������
                                                                        </td>
                                                                        <td class="_tdS" colspan="5">
                                                                            <select name="p_dt_type_er" id="p_dt_type_er">
                                                                                <option value="">-- ��ü --</option>
                                                                                <option value="S" <% if ( v_dt_type.equals("S")) { %> selected <% } %>>���� ������</option>
                                                                                <option value="E" <% if ( v_dt_type.equals("E")) { %> selected <% } %>>���� ������</option>
                                                                            </select>
                                                                            <button class="dt_type" type="button" onclick="fnSetDate('0')">����</button>
                                                                            <button class="dt_type" type="button" onclick="fnSetDate('1')">1����</button>
                                                                            <button class="dt_type" type="button" onclick="fnSetDate('3')">3����</button>
                                                                            <button class="dt_type" type="button" onclick="fnSetDate('6')">6����</button>
                                                                            <button class="dt_type" type="button" onclick="fnSetDate('12')">1��</button>
                                                                            <button class="dt_type" type="button" onclick="fnWriteDate()">�����Է�</button>
                                                                            <input name="p_sdt_er" id="p_sdt_er" value="<%= v_sdt %>" type="text" class="datepicker_input1" size="10" style="text-align: center" readonly /> �� ~
                                                                            <input name="p_edt_er" id="p_edt_er" value="<%= v_edt %>" vtype="text" class="datepicker_input1" size="10" style="text-align: center" readonly /> ��
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            ������/�ڵ�
                                                                        </td>
                                                                        <td class="_tdS" colspan="3">
                                                                            <input name="p_txt_er" id="p_txt_er" value="<%= v_txt %>" type="text" style="width: 100%;" />
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            �������
                                                                        </td>
                                                                        <td colspan="5" class="_tdS">
                                                                            <input type="checkbox" id="p_avg_er_a" name="p_avg_er_all" class="needinputClass" value="A" <% if (v_avg_all.equals("A")) {%> checked <%}%>><label for="p_avg_er_a">��ü</label>
                                                                            <input type="checkbox" id="p_avg_er_1" name="p_avg_er" class="needinputClass" value="1" <% if (v_avg.indexOf("1") > -1) {%> checked <%}%>><label for="p_avg_er_1">0~1</label>
                                                                            <input type="checkbox" id="p_avg_er_2" name="p_avg_er" class="needinputClass" value="2" <% if (v_avg.indexOf("2") > -1) {%> checked <%}%>><label for="p_avg_er_2">1~2</label>
                                                                            <input type="checkbox" id="p_avg_er_3" name="p_avg_er" class="needinputClass" value="3" <% if (v_avg.indexOf("3") > -1) {%> checked <%}%>><label for="p_avg_er_3">2~3</label>
                                                                            <input type="checkbox" id="p_avg_er_4" name="p_avg_er" class="needinputClass" value="4" <% if (v_avg.indexOf("4") > -1) {%> checked <%}%>><label for="p_avg_er_4">3~4</label>
                                                                            <input type="checkbox" id="p_avg_er_5" name="p_avg_er" class="needinputClass" value="5" <% if (v_avg.indexOf("5") > -1) {%> checked <%}%>><label for="p_avg_er_5">4~5</label>
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
                                            <% if( ss_action.equals("go") ){  %>
                                            <td align="right">
                                                <input type="text" id="downMemo">
                                                <input type="button" id="btnDown" value="Excel" onclick="goExcel()">
                                                <a href="javascript:alert('�˾� ����.');" class="c"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a>
                                            </td>
                                            <% } else { %>
                                            <td align="right"><a href="javascript:alert('��ȸ �� ������ ����ϼ���.');" class="c"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a></td>
                                            <% } %>
                                        </tr>
                                        <tr>
                                            <td height="3"></td>
                                        </tr>
                                    </table>
                                    <div style="overflow-x: scroll; width: 1200px;">
                                    <table cellspacing="1" cellpadding="0" class="box_table_out" style="width: 105%; white-space: nowrap;">
                                        <tr>
                                            <td colspan="17" class="table_top_line"></td>
                                        </tr>
                                        <tr>
                                            <td class="table_title">No</td>
                                            <td class="table_title">��������</td>
                                            <td class="table_title">�����׷�</td>
                                            <td class="table_title">������</td>
                                            <td class="table_title">����</td>
                                            <td class="table_title">����������</td>
                                            <td class="table_title">����������</td>
                                            <td class="table_title">�帣</td>
                                            <td class="table_title">��з�</td>
                                            <td class="table_title">�Һз�</td>
                                            <td class="table_title">���̵�</td>
                                            <td class="table_title">��û�� ��</td>
                                            <td class="table_title">�н��� ��</td>
                                            <td class="table_title">������ ��</td>
                                            <td class="table_title">������ ��</td>
                                            <td class="table_title">������</td>
                                            <td class="table_title">�������</td>
                                        </tr>
                                        <%
                                            DataBox dbox = null;

                                            if(ss_action.equals("go") ){	//go button �����϶����
                                                if( list.size() != 0 ){		// �˻��� ������ �ִٸ�
                                                    v_total = list.size();

                                                    for(i = 0; i < v_total; i++) {
                                                        dbox = (DataBox)list.get(i);

                                                        v_totalpage = dbox.getInt("d_totalpage");
                                                        v_totalrowcount = dbox.getInt("d_totalrowcount");

                                                        if(dbox.getString("d_upperclass").equals("X01")){
                                                            _gm1 = "����";
                                                            _gm2 = "";
                                                            _gm3 = "";
                                                            _lvnm = "";
                                                        }else{
                                                            _gm1 = dbox.getString("d_gnm1");
                                                            _gm2 = dbox.getString("d_gnm2");
                                                            _gm3 = dbox.getString("d_gnm3");
                                                            _lvnm = dbox.getString("d_lvnm");
                                                        }
                                        %>
                                        <tr>
                                            <td class="table_02_1 tdT2"><%= v_totalrowcount - v_pagesize * v_pageno + v_pagesize - i %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_edu_type") %></td>
                                            <td class="table_02_2 tdT2"><%= dbox.getString("d_grcodenm") %></td>
                                            <td class="table_02_2 tdT2">[<%= dbox.getString("d_subj") %>] <%= dbox.getString("d_subjnm") %></td>
                                            <td class="table_02_2 tdT2">[<%= dbox.getString("d_year") %>] <%= dbox.getString("d_grseqnm") %></td>
                                            <td class="table_02_2 tdT2"><%= dbox.getString("d_sdt") %></td>
                                            <td class="table_02_2 tdT2"><%= dbox.getString("d_edt") %></td>
                                            <td class="table_02_1 tdT2"><%= _gm1 %></td>
                                            <td class="table_02_1 tdT2"><%= _gm2 %></td>
                                            <td class="table_02_1 tdT2"><%= _gm3 %></td>
                                            <td class="table_02_1 tdT2"><%= _lvnm %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getInt("d_ap_cnt") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getInt("d_st_cnt") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getInt("d_gr_cnt") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getInt("d_su_cnt") %></td>
                                            <td class="table_02_1 tdT2"><%= String.format("%.1f", (double)dbox.getInt("d_gr_cnt") / (double)dbox.getInt("d_st_cnt") * 100) %></td>
                                            <td class="table_02_1 tdT2"><%= String.format("%.1f", (double)dbox.getInt("d_dis_sum") / (double)dbox.getInt("d_su_cnt") / 20) %></td>
                                        </tr>
                                        <%
                                                    }
                                                } else {
                                        %>
                                        <tr>
                                            <td align="center" bgcolor="#F7F7F7" height="50" colspan="25">�˻��� �����Ͱ� �����ϴ�</td>
                                        </tr>
                                        <%
                                                }
                                            }
                                        %>
                                    </table>
                                    </div>
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
<style>
    ._tdT{text-align: right;}
    .tdT2{padding: 0 7px;}
</style>
</body>
</html>
