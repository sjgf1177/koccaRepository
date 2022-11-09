<%
    //**********************************************************
//  1. ��      ��: ���� ���(�����ο�)
//  2. ���α׷���: za_EduUsersStatistics_L.jsp
//  3. ��      ��: �����ο� ��� ��ȸ
//  4. ȯ      ��: JDK 1.7
//  5. ��      ��: 1.0
//  6. ��      ��: 2022.10.21
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

    String v_edu_type = box.getString("p_edu_type_eu");
    String v_grcode = box.getString("p_grcode_eu");
    String v_year = box.getString("p_year_eu");
    String v_seq = box.getString("p_seq_eu");
    String v_subj = box.getString("p_subj_eu");
    String v_g1 = box.getString("p_g1_eu");
    String v_g2 = box.getString("p_g2_eu");
    String v_g3 = box.getString("p_g3_eu");
    String v_lv = box.getString("p_lv_eu");
    String v_lv_type = box.getString("p_lv_type");
    String v_out = box.getString("p_out_eu");
    String v_txt = box.getString("p_txt_eu");
    String v_learn = box.getString("p_learn_eu");
    String v_graduated = box.getString("p_graduated_eu");
    String v_sul = box.getString("p_sul_eu");
    String v_gubun = "0110";
    String v_gubun_lv = "0121";
    String v_lv2 = "2";
    String v_lv3 = "3";

    if(v_edu_type.equals("C")) {
        v_grcode = "N000001";
    }

    String v_sdt = box.getString("p_sdt_eu");
    String v_edt = box.getString("p_edt_eu");
    String v_sex = "";
    String v_sex_all = box.getString("p_sex_eu_all");
    String[] sexArr = box.getStringArray("p_sex_eu");
    String v_age = "";
    String v_age_all = box.getString("p_age_eu_all");
    String[] ageArr = box.getStringArray("p_age_eu");
    String v_loc = "";
    String v_loc_all = box.getString("p_loc_eu_all");
    String[] locArr = box.getStringArray("p_loc_eu");
    String v_job = "";
    String v_job_all = box.getString("p_job_eu_all");
    String[] jobArr = box.getStringArray("p_job_eu");
    String v_sort = box.getString("p_sort_eu");

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
    String _gm1 = "";
    String _gm2 = "";
    String _gm3 = "";
    String _lvnm = "";

    ArrayList list = null;
    list = (ArrayList)request.getAttribute("selectEduUsersStatisticList");

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
            if($("#p_sdt_eu").val() == "") {
                alert("���� ���ڸ� �Է��� �ּ���.");
                $("#p_sdt_eu").focus();
                return;
            }

            if($("#p_edt_eu").val() == "") {
                alert("���� ���ڸ� �Է��� �ּ���.");
                $("#p_edt_eu").focus();
                return;
            }

            var st_dt = $("#p_sdt_eu").val().replaceAll("-","");
            var ed_dt = $("#p_edt_eu").val().replaceAll("-","");

            if(st_dt > ed_dt){
                alert("�������� �����Ϻ��� Ů�ϴ�.");
                return;
            }

            $("#loding-box").show();
            document.form1.target = "_self";
            document.form1.p_action.value  = p_action;
            document.form1.p_process.value = 'selectEduUsersStatisticList';
            document.form1.p_sort_eu.value = $("#sort").val();
            document.form1.submit();
        }

        // ������ �̵�
        function goPage(pageNum) {
            if($("#p_sdt_eu").val() == "") {
                alert("���� ���ڸ� �Է��� �ּ���.");
                $("#p_sdt_eu").focus();
                return;
            }

            if($("#p_edt_eu").val() == "") {
                alert("���� ���ڸ� �Է��� �ּ���.");
                $("#p_edt_eu").focus();
                return;
            }

            var st_dt = $("#p_sdt_eu").val().replaceAll("-","");
            var ed_dt = $("#p_edt_eu").val().replaceAll("-","");

            if(st_dt > ed_dt){
                alert("�������� �����Ϻ��� Ů�ϴ�.");
                return;
            }

            $("#loding-box").show();
            document.form1.target = "_self";
            document.form1.p_pageno.value = pageNum;
            document.form1.p_action.value = "go";
            document.form1.action = '/servlet/controller.statistics.SynthesizeStatisticServlet';
            document.form1.p_process.value = "selectEduUsersStatisticList";
            document.form1.p_sort_eu.value = $("#sort").val();
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
            document.getElementById("p_grcode_eu").disabled = true;
            document.getElementById("p_year_eu").disabled = true;
            document.getElementById("p_seq_eu").disabled = true;
            document.getElementById("p_subj_eu").disabled = true;

            $("#loding-box").hide();
            $("#p_sdt_eu").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
            $("#p_edt_eu").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})

            if($("#p_edu_type_eu").val() == "B") {
                document.getElementById("p_grcode_eu").disabled = false;
                document.getElementById("p_year_eu").disabled = false;
                document.getElementById("p_seq_eu").disabled = false;
                document.getElementById("p_subj_eu").disabled = false;
            } else if($("#p_edu_type_eu").val() == "C") {
                document.getElementById("p_year_eu").disabled = false;
                document.getElementById("p_seq_eu").disabled = false;
                document.getElementById("p_subj_eu").disabled = false;
            }

            $("#p_edu_type_eu").change(function(){
                $("#p_grcode_eu").val("");
                $("#p_year_eu").val("");
                $("#p_seq_eu").val("");
                $("#p_subj_eu").val("");
                type = "G";
                fnAjaxSelect("");

                if($("#p_edu_type_eu").val() == "B"){
                    document.getElementById("p_grcode_eu").disabled = false;
                    document.getElementById("p_year_eu").disabled = false;
                    document.getElementById("p_seq_eu").disabled = false;
                    document.getElementById("p_subj_eu").disabled = false;
                } else if($("#p_edu_type_eu").val() == "C") {
                    document.getElementById("p_grcode_eu").disabled = true;
                    document.getElementById("p_year_eu").disabled = false;
                    document.getElementById("p_seq_eu").disabled = false;
                    document.getElementById("p_subj_eu").disabled = false;
                    fnAjaxSelect("N000001");
                } else {
                    document.getElementById("p_grcode_eu").disabled = true;
                    document.getElementById("p_year_eu").disabled = true;
                    document.getElementById("p_seq_eu").disabled = true;
                    document.getElementById("p_subj_eu").disabled = true;
                }
            });

            if($("#p_edu_type_eu").val() == "C") {
                if ($("#p_year_eu").val().length < 1) {
                    type = "G";
                    fnAjaxSelect("N000001");
                }

                if ($("#p_year_eu").val().length > 0 && $("#p_seq_eu").val().length < 1) {
                    type = "Y";
                    fnAjaxSelect($("#p_year_eu").val());
                }

                if ($("#p_year_eu").val().length > 0 && $("#p_seq_eu").val().length > 0 && $("#p_subj_eu").val().length < 1) {
                    type = "S";
                    fnAjaxSelect($("#p_seq_eu").val());
                }
            } else {
                if ($("#p_grcode_eu").val().length > 0 && $("#p_year_eu").val().length < 1) {
                    type = "G";
                    fnAjaxSelect($("#p_grcode_eu").val());
                }

                if ($("#p_grcode_eu").val().length > 0 && $("#p_year_eu").val().length > 0 && $("#p_seq_eu").val().length < 1) {
                    type = "Y";
                    fnAjaxSelect($("#p_year_eu").val());
                }

                if ($("#p_grcode_eu").val().length > 0 && $("#p_year_eu").val().length > 0 && $("#p_seq_eu").val().length > 0 && $("#p_subj_eu").val().length < 1) {
                    type = "S";
                    fnAjaxSelect($("#p_seq_eu").val());
                }
            }

            if($("#p_g1_eu").val().length > 0 && $("#p_g2_eu").val().length < 1) {
                type2 = "2";
                fnAjaxSelect2($("#p_g1_eu").val());
                fnAjaxLvGubunCode();
            }

            if($("#p_g1_eu").val().length > 0 && $("#p_g2_eu").val().length > 0 && $("#p_g3_eu").val().length < 1) {
                type2 = "3";
                fnAjaxSelect2($("#p_g2_eu").val());
                fnAjaxLvGubunCode();
            }

            if($("#p_g1_eu").val().length > 0 && $("#p_g2_eu").val().length > 0 && $("#p_g3_eu").val().length > 0) {
                fnAjaxLvGubunCode();
            }

            $("#p_sex_eu_a").click(function() {
                if($("#p_sex_eu_a").is(":checked")) {
                    $("input[name=p_sex_eu]").attr("checked", "checked");
                } else {
                    $("input[name=p_sex_eu]").removeAttr("checked", "checked");
                }
            });

            $("input[name=p_sex_eu]").click(function() {
                var total = $("input[name=p_sex_eu]").length;
                var checked = $("input[name=p_sex_eu]:checked").length;

                if(total != checked) {
                    $("#p_sex_eu_a").removeAttr("checked", "checked");
                } else {
                    $("#p_sex_eu_a").attr("checked", "checked");
                }
            });

            $("#p_age_eu_a").click(function() {
                if($("#p_age_eu_a").is(":checked")) {
                    $("input[name=p_age_eu]").attr("checked", "checked");
                } else {
                    $("input[name=p_age_eu]").removeAttr("checked", "checked");
                }
            });

            $("input[name=p_age_eu]").click(function() {
                var total = $("input[name=p_age_eu]").length;
                var checked = $("input[name=p_age_eu]:checked").length;

                if(total != checked) {
                    $("#p_age_eu_a").removeAttr("checked", "checked");
                } else {
                    $("#p_age_eu_a").attr("checked", "checked");
                }
            });

            $("#p_loc_eu_a").click(function() {
                if($("#p_loc_eu_a").is(":checked")) {
                    $("input[name=p_loc_eu]").attr("checked", "checked");
                } else {
                    $("input[name=p_loc_eu]").removeAttr("checked", "checked");
                }
            });

            $("input[name=p_loc_eu]").click(function() {
                var total = $("input[name=p_loc_eu]").length;
                var checked = $("input[name=p_loc_eu]:checked").length;

                if(total != checked) {
                    $("#p_loc_eu_a").removeAttr("checked", "checked");
                } else {
                    $("#p_loc_eu_a").attr("checked", "checked");
                }
            });

            $("#p_job_eu_a").click(function() {
                if($("#p_job_eu_a").is(":checked")) {
                    $("input[name=p_job_eu]").attr("checked", "checked");
                } else {
                    $("input[name=p_job_eu]").removeAttr("checked", "checked");
                }
            });

            $("input[name=p_job_eu]").click(function() {
                var total = $("input[name=p_job_eu]").length;
                var checked = $("input[name=p_job_eu]:checked").length;

                if(total != checked) {
                    $("#p_job_eu_a").removeAttr("checked", "checked");
                } else {
                    $("#p_job_eu_a").attr("checked", "checked");
                }
            });

            $("#p_grcode_eu").bind("change", function(){
                type = "G";
                fnAjaxSelect($(this).val());
            });

            $("#p_year_eu").bind("change", function(){
                type = "Y";
                fnAjaxSelect($(this).val());
            });

            $("#p_seq_eu").bind("change", function(){
                type = "S";
                fnAjaxSelect($(this).val());
            });

            $("#p_g1_eu").bind("change", function(){
                type2 = "2";
                fnAjaxSelect2($(this).val());
                fnAjaxLvGubunCode();
            });

            $("#p_g2_eu").bind("change", function(){
                type2 = "3";
                fnAjaxSelect2($(this).val());
            });
        });

        function fnSetDate(v) {
            var endDate = new Date($("#p_edt_eu").val());
            var newDate = new Date($("#p_edt_eu").val());
            var year = "";
            var month = "";
            var day = "";

            $("#p_sdt_eu").attr("readonly", true);
            $("#p_edt_eu").attr("readonly", true);

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

            $("#p_sdt_eu").val(year + "-" + month + "-" + day);
        }

        function fnWriteDate() {
            $("#p_sdt_eu").attr("readonly", false);
            $("#p_edt_eu").attr("readonly", false);
        }

        //���� ���
        function goExcel() {
            document.form1.target = "_self";
            document.form1.action = '/servlet/controller.statistics.SynthesizeStatisticServlet';
            document.form1.p_process.value = "selectEduUsersStatisticListExcel";
            document.form1.submit();
        }

        // �� ����
        function select_tab(sel){
            document.form1.target = "_self";

            if (sel == "1") {
                document.form1.p_process.value = "selectUsersStatisticList";
            } else if (sel == "3") {
                document.form1.p_process.value = "selectEduResultStatisticList";
            }

            document.form1.action = '/servlet/controller.statistics.SynthesizeStatisticServlet';
            document.form1.submit();
        }

        function fnAjaxSelect(v) {
            var param = "";
            var grcode = $("#p_grcode_eu").val();
            var gyear = $("#p_year_eu").val();

            if($("#p_edu_type_eu").val() == "C") {
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
                el = "p_year_eu";
                $("#p_year_eu").empty();
                $("#p_year_eu").append("<option value=\"\">-- ���� --</option>");
                $("#p_seq_eu").empty();
                $("#p_seq_eu").append("<option value=\"\">-- ���� --</option>");
            } else if (type == "Y") {
                el = "p_seq_eu";
                $("#p_seq_eu").empty();
                $("#p_seq_eu").append("<option value=\"\">-- ���� --</option>");
            } else if (type == "S") {
                el = "p_subj_eu";
            }

            $("#p_subj_eu").empty();
            $("#p_subj_eu").append("<option value=\"\">-- ���� --</option>");

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
                $("#p_g2_eu").empty();
                $("#p_g2_eu").append("<option value=\"\">-- ��з� --</option>");
                el = "p_g2_eu"
            } else if (type2 == "3") {
                el = "p_g3_eu"
            }

            $("#p_g3_eu").empty();
            $("#p_g3_eu").append("<option value=\"\">-- �Һз� --</option>");

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
                ,   data : "type=sqlID&sqlID=code.list.0006&param=0110," + $("#p_g1_eu").val() + ",1"
                ,   success : function( res ) {
                    if ( res.selectBoxList != null && res.selectBoxList.length > 0 ) {
                        $("#p_lv_type").val(res.selectBoxList[0].d_code);
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
            alert("123 : " + $("#p_lv_type").val());
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
                $("#p_lv_eu").empty();
                $("#p_lv_eu").append("<option value=\"\">-- ���̵� --</option>");

                if ( result.selectBoxList != null && result.selectBoxList.length > 0 ) {
                    $.each( result.selectBoxList, function() {
                        $("#p_lv_eu").append("<option value=\"" + this.d_code + "\">" + this.d_codenm + "</option>");
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
                                <td rowspan="3" class="blue_butt_left"></td>
                                <td class="blue_butt_top"></td>
                                <td rowspan="3" class="blue_butt_right"></td>
                            </tr>
                            <tr>
                                <td class="blue_butt_middle">�����ο�</td>
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
                                <td class="black_butt_middle"><a href="javascript:select_tab('3')" class="c">��������</a></td>
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
                                        <input type="hidden" name="p_process"  value="">
                                        <input type="hidden" name="p_action"   value="">
                                        <input type="hidden" name="p_lv_type" id="p_lv_type" value="">
                                        <input type="hidden" name="p_sort_eu" id="p_sort_eu" value="">
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
                                                                            <select id="p_edu_type_eu" name="p_edu_type_eu">
                                                                                <option value="">-- �������� --</option>
                                                                                <option value="C" <% if (v_edu_type.equals("C")) {%> selected <%}%>>B2C</option>
                                                                                <option value="B" <% if (v_edu_type.equals("B")) {%> selected <%}%>>B2B</option>
                                                                            </select>

                                                                            <select id="p_grcode_eu" name="p_grcode_eu">
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
                                                                                    <kocca:selectBox name="p_year_eu" id="p_year_eu" optionTitle="-- ����2 --" type="sqlID" param="<%= v_grcode %>" sqlID="selectBox.grYearList" selectedValue="<%= v_year %>" isLoad="true" />
                                                                            <%
                                                                                } else {
                                                                            %>
                                                                                    <select name="p_year_eu" id="p_year_eu">
                                                                                        <option value="">-- ���� --</option>
                                                                                    </select>
                                                                            <%
                                                                                }
                                                                            %>

                                                                            <%
                                                                                if ( !v_seq.equals("") ) {
                                                                            %>
                                                                                    <kocca:selectBox name="p_seq_eu" id="p_seq_eu" optionTitle="-- ���� --" type="sqlID" param="<%= v_grcode + ',' + v_year %>" sqlID="selectBox.grSeqList" selectedValue="<%= v_seq %>" isLoad="true" />
                                                                            <%
                                                                                } else {
                                                                            %>
                                                                                    <select name="p_seq_eu" id="p_seq_eu">
                                                                                        <option value="">-- ���� --</option>
                                                                                    </select>
                                                                            <%
                                                                                }
                                                                            %>

                                                                            <%
                                                                                if ( !v_seq.equals("") ) {
                                                                            %>
                                                                                    <kocca:selectBox name="p_subj_eu" id="p_subj_eu" optionTitle="-- ���� --" type="sqlID" param="<%= v_grcode + ',' + v_year + ',' + v_seq  %>" sqlID="selectBox.subjListAll" selectedValue="<%= v_subj %>" isLoad="true" />
                                                                            <%
                                                                                } else {
                                                                            %>
                                                                                    <select name="p_subj_eu" id="p_subj_eu">
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
                                                                            <select id="p_g1_eu" name="p_g1_eu">
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
                                                                                    <kocca:selectBox name="p_g2_eu" id="p_g2_eu" optionTitle="-- ��з� --" type="sqlID" sqlID="code.list.0004" param="<%= v_gubun + ',' + v_g1 + ',' + v_lv2 %>" selectedValue="<%= v_g2 %>" isLoad="true" />

                                                                            <%
                                                                                } else {
                                                                            %>
                                                                                    <select name="p_g2_eu" id="p_g2_eu">
                                                                                        <option value="">-- ��з� --</option>
                                                                                    </select>
                                                                            <%
                                                                                }
                                                                            %>

                                                                            <%
                                                                                if ( !v_g2.equals("") ) {
                                                                            %>
                                                                                    <kocca:selectBox name="p_g3_eu" id="p_g3_eu" optionTitle="-- �Һз� --" type="sqlID" sqlID="code.list.0004" param="<%= v_gubun + ',' + v_g2 + ',' + v_lv3 %>" selectedValue="<%= v_g3 %>" isLoad="true" />

                                                                            <%
                                                                                } else {
                                                                            %>
                                                                                    <select name="p_g3_eu" id="p_g3_eu">
                                                                                        <option value="">-- �Һз� --</option>
                                                                                    </select>
                                                                            <%
                                                                                }
                                                                            %>

                                                                            <%
                                                                                if ( !v_g1.equals("") ) {
                                                                            %>
                                                                                    <kocca:selectBox name="p_lv_eu" id="p_lv_eu" optionTitle="-- ���̵� --" type="sqlID" sqlID="code.list.0004" param="<%= v_gubun_lv + ',' + v_lv_type + ',' + v_lv2 %>" selectedValue="<%= v_lv %>" isLoad="true" />

                                                                            <%
                                                                                } else {
                                                                            %>
                                                                                    <select name="p_lv_eu" id="p_lv_eu">
                                                                                        <option value="">-- ���̵� --</option>
                                                                                    </select>
                                                                            <%
                                                                                }
                                                                            %>

                                                                            <select name="p_out_eu" id="p_out_eu">
                                                                                <option value="">-- �������� --<%=v_gubun_lv%>/<%=v_lv_type%>/<%=v_lv2%></option>
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
                                                                            <font color="red">��</font>��û����
                                                                        </td>
                                                                        <td class="_tdS" colspan="5">
                                                                            <button type="button" onclick="fnSetDate('0')">����</button>
                                                                            <button type="button" onclick="fnSetDate('1')">1����</button>
                                                                            <button type="button" onclick="fnSetDate('3')">3����</button>
                                                                            <button type="button" onclick="fnSetDate('6')">6����</button>
                                                                            <button type="button" onclick="fnSetDate('12')">1��</button>
                                                                            <button type="button" onclick="fnWriteDate()">�����Է�</button>
                                                                            <input name="p_sdt_eu" id="p_sdt_eu" value="<%= v_sdt %>" type="text" class="datepicker_input1" size="10" style="text-align: center" readonly /> �� ~
                                                                            <input name="p_edt_eu" id="p_edt_eu" value="<%= v_edt %>" vtype="text" class="datepicker_input1" size="10" style="text-align: center" readonly /> ��
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            ����
                                                                        </td>
                                                                        <td class="_tdS">
                                                                            <input type="checkbox" id="p_sex_eu_a" name="p_sex_eu_all" class="needinputClass" value="A" <% if (v_sex_all.equals("A")) {%> checked <%}%>><label for="p_sex_eu_a">��ü</label>
                                                                            <input type="checkbox" id="p_sex_eu_m" name="p_sex_eu" class="needinputClass" value="M" <% if (v_sex.indexOf("M") > -1) {%> checked <%}%>><label for="p_sex_eu_m">����</label>
                                                                            <input type="checkbox" id="p_sex_eu_w" name="p_sex_eu" class="needinputClass" value="W" <% if (v_sex.indexOf("W") > -1) {%> checked <%}%>><label for="p_sex_eu_w">����</label>
                                                                            <input type="checkbox" id="p_sex_eu_n" name="p_sex_eu" class="needinputClass" value="N" <% if (v_sex.indexOf("N") > -1) {%> checked <%}%>><label for="p_sex_eu_n">��Ȯ��</label>
                                                                        </td>
                                                                        <td class="_tdT">
                                                                            ���ɴ�
                                                                        </td>
                                                                        <td class="_tdS" colspan="3">
                                                                            <input type="checkbox" id="p_age_eu_a" name="p_age_eu_all" class="needinputClass" value="A" <% if (v_age_all.equals("A")) {%> checked <%}%>><label for="p_age_eu_a">��ü</label>
                                                                            <input type="checkbox" id="p_age_eu_1" name="p_age_eu" class="needinputClass" value="10" <% if (v_age.indexOf("10") > -1) {%> checked <%}%>><label for="p_age_eu_1">10��</label>
                                                                            <input type="checkbox" id="p_age_eu_2" name="p_age_eu" class="needinputClass" value="20" <% if (v_age.indexOf("20") > -1) {%> checked <%}%>><label for="p_age_eu_2">20��</label>
                                                                            <input type="checkbox" id="p_age_eu_3" name="p_age_eu" class="needinputClass" value="30" <% if (v_age.indexOf("30") > -1) {%> checked <%}%>><label for="p_age_eu_3">30��</label>
                                                                            <input type="checkbox" id="p_age_eu_4" name="p_age_eu" class="needinputClass" value="40" <% if (v_age.indexOf("40") > -1) {%> checked <%}%>><label for="p_age_eu_4">40��</label>
                                                                            <input type="checkbox" id="p_age_eu_5" name="p_age_eu" class="needinputClass" value="50" <% if (v_age.indexOf("50") > -1) {%> checked <%}%>><label for="p_age_eu_5">50��</label>
                                                                            <input type="checkbox" id="p_age_eu_6" name="p_age_eu" class="needinputClass" value="60" <% if (v_age.indexOf("60") > -1) {%> checked <%}%>><label for="p_age_eu_6">60�� �̻�</label>
                                                                            <input type="checkbox" id="p_age_eu_0" name="p_age_eu" class="needinputClass" value="00" <% if (v_age.indexOf("00") > -1) {%> checked <%}%>><label for="p_age_eu_0">��Ȯ��</label>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            ����
                                                                        </td>
                                                                        <td colspan="5" class="_tdS">
                                                                            <input type="checkbox" id="p_loc_eu_a" name="p_loc_eu_all" class="needinputClass" value="A" <% if (v_loc_all.equals("A")) {%> checked <%}%>><label for="p_loc_eu_a">��ü</label>
                                                                            <%
                                                                                DataBox c_dbox = null;

                                                                                if( cd_list.size() != 0 ) {		// �˻��� ������ �ִٸ�
                                                                                    v_total = cd_list.size();

                                                                                    for(i = 0; i < v_total; i++) {
                                                                                        c_dbox = (DataBox)cd_list.get(i);
                                                                            %>
                                                                            <input type="checkbox" id="p_loc_eu_<%= c_dbox.getString("d_code") %>" name="p_loc_eu" class="needinputClass" value="<%= c_dbox.getString("d_code") %>" <% if (v_loc.indexOf(c_dbox.getString("d_code")) > -1) {%> checked <%}%>><label for="p_loc_eu_<%= c_dbox.getString("d_code") %>"><%= c_dbox.getString("d_codenm") %></label>
                                                                            <%
                                                                                    }
                                                                                }
                                                                            %>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            ������
                                                                        </td>
                                                                        <td colspan="5" class="_tdS">
                                                                            <input type="checkbox" id="p_job_eu_a" name="p_job_eu_all" class="needinputClass" value="A" <% if (v_job_all.equals("A")) {%> checked <%}%>><label for="p_job_eu_a">��ü</label>
                                                                            <%
                                                                                DataBox j_dbox = null;

                                                                                if( job_list.size() != 0 ) {		// �˻��� ������ �ִٸ�
                                                                                    v_total = job_list.size();

                                                                                    for(i = 0; i < v_total; i++) {
                                                                                        j_dbox = (DataBox)job_list.get(i);
                                                                            %>
                                                                            <input type="checkbox" id="p_job_eu_<%= j_dbox.getString("d_cd") %>" name="p_job_eu" class="needinputClass" value="<%= j_dbox.getString("d_cd") %>" <% if (v_job.indexOf(j_dbox.getString("d_cd")) > -1) {%> checked <%}%>><label for="p_job_eu_<%= j_dbox.getString("d_cd") %>"><%= j_dbox.getString("d_nm") %></label>
                                                                            <%
                                                                                    }
                                                                                }
                                                                            %>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            �н�����
                                                                        </td>
                                                                        <td class="_tdS">
                                                                            <select id="p_learn_eu" name="p_learn_eu" style="width: 100%;">
                                                                                <option value="">-- ��ü --</option>
                                                                                <option value="Y" <% if (v_learn.equals("Y")) {%> selected <%}%>>�н�</option>
                                                                                <option value="N" <% if (v_learn.equals("N")) {%> selected <%}%>>���н�</option>
                                                                            </select>
                                                                        </td>
                                                                        <td class="_tdT">
                                                                            ���Ῡ��
                                                                        </td>
                                                                        <td class="_tdS">
                                                                            <select id="p_graduated_eu" name="p_graduated_eu" style="width: 100%;">
                                                                                <option value="">-- ��ü --</option>
                                                                                <option value="Y" <% if (v_graduated.equals("Y")) {%> selected <%}%>>����</option>
                                                                                <option value="N" <% if (v_graduated.equals("N")) {%> selected <%}%>>�̼���</option>
                                                                            </select>
                                                                        </td>
                                                                        <td class="_tdT">
                                                                            �������� ����
                                                                        </td>
                                                                        <td class="_tdS">
                                                                            <select id="p_sul_eu" name="p_sul_eu" style="width: 100%;">
                                                                                <option value="">-- ��ü --</option>
                                                                                <option value="Y" <% if (v_sul.equals("Y")) {%> selected <%}%>>����</option>
                                                                                <option value="N" <% if (v_sul.equals("N")) {%> selected <%}%>>������</option>
                                                                            </select>
                                                                        </td>
                                                                    </tr>
                                                                    <tr>
                                                                        <td class="_tdT">
                                                                            ������/�ڵ�/ID/����
                                                                        </td>
                                                                        <td class="_tdS" colspan="3">
                                                                            <input name="p_txt_eu" id="p_txt_eu" value="<%= v_txt %>" type="text" style="width: 100%;" />
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
                                                    <option value="ND" <% if (v_sort.equals("ND")) {%> selected <%}%>>�ֱ� ��û���� ��</option>
                                                    <option value="OD" <% if (v_sort.equals("OD")) {%> selected <%}%>>������ ��û���� ��</option>
                                                    <option value="NM" <% if (v_sort.equals("NM")) {%> selected <%}%>>���� ������ ��</option>
                                                </select>
                                            </td>
                                            <% if( ss_action.equals("go") ){  %>
                                            <td align="right"><a href="javascript:goExcel()" class="c"><img src="/images/admin/button/btn_excelprint.gif"  border="0"></a></td>
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
                                            <td colspan="25" class="table_top_line"></td>
                                        </tr>
                                        <tr>
                                            <td class="table_title">No</td>
                                            <td class="table_title">�����׷�</td>
                                            <td class="table_title">������</td>
                                            <td class="table_title">����</td>
                                            <td class="table_title">�帣</td>
                                            <td class="table_title">��з�</td>
                                            <td class="table_title">�Һз�</td>
                                            <td class="table_title">���̵�</td>
                                            <td class="table_title">���̵�</td>
                                            <td class="table_title">����</td>
                                            <td class="table_title">����</td>
                                            <td class="table_title">�������</td>
                                            <td class="table_title">����</td>
                                            <td class="table_title">����</td>
                                            <td class="table_title">���ɴ�</td>
                                            <td class="table_title">��û����</td>
                                            <td class="table_title">�н�����</td>
                                            <td class="table_title">���Ῡ��</td>
                                            <td class="table_title">������������</td>
                                            <td class="table_title">��������</td>
                                            <td class="table_title">���</td>
                                            <td class="table_title">���</td>
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
                                            <td class="table_02_2 tdT2">[<%= dbox.getString("d_edu_type") %>] <%= dbox.getString("d_grcodenm") %></td>
                                            <td class="table_02_2 tdT2">[<%= dbox.getString("d_subj") %>] <%= dbox.getString("d_subjnm") %></td>
                                            <td class="table_02_2 tdT2">[<%= dbox.getString("d_year") %>] <%= dbox.getString("d_grseqnm") %></td>
                                            <td class="table_02_1 tdT2"><%= _gm1 %></td>
                                            <td class="table_02_1 tdT2"><%= _gm2 %></td>
                                            <td class="table_02_1 tdT2"><%= _gm3 %></td>
                                            <td class="table_02_1 tdT2"><%= _lvnm %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_user_id") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_user_nm") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_sex_nm") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_brthdy") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_region") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_job_nm") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_age2") %></td>
                                            <td class="table_02_1 tdT2" style="padding: 0 7px;"><%= dbox.getString("d_appdt") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_learn_yn") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_graduated_yn") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_suleach2_yn") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_suldt") %></td>
                                            <td class="table_02_2 tdT2"><%= dbox.getString("d_answers") %></td>
                                            <td class="table_02_1 tdT2"><%= dbox.getString("d_distcode1_avg") %></td>
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
